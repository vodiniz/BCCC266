#include "mmu.h"

#include <stdio.h>

bool canOnlyReplaceBlock(Line line) {
    // Or the block is empty or
    // the block is equal to the one in memory RAM and can be replaced
    if (line.tag == INVALID_ADD || (line.tag != INVALID_ADD && !line.updated))
        return true;
    return false;
}

int memoryCacheMapping(int address, Cache* cache) {
    return address % cache->size;
}

void updateMachineInfos(Machine* machine, Line* line) {
    switch (line->cacheHit) {
        case 1:
            machine->hitL1 += 1;
            break;

        case 2:
            machine->hitL2 += 1;
            machine->missL1 += 1;
            break;
        
        case 3:
            machine->hitRAM += 1;
            machine->missL1 += 1;
            machine->missL2 += 1;
            break;
    }
    machine->totalCost += line->cost;
}

Line* MMUSearchOnMemorys(Address add, Machine* machine) {
    // Strategy => write back
    
    // Direct memory map
    int l1pos = memoryCacheMapping(add.block, &machine->l1);
    int l2pos = memoryCacheMapping(add.block, &machine->l2);

    Line* cache1 = machine->l1.lines;
    Line* cache2 = machine->l2.lines;
    MemoryBlock* RAM = machine->ram.blocks;

    if (cache1[l1pos].tag == add.block) { 
        /* Block is in memory cache L1 */
        cache1[l1pos].cost = COST_ACCESS_L1;
        cache1[l1pos].cacheHit = 1;
    } else if (cache2[l2pos].tag == add.block) { 
        /* Block is in memory cache L2 */
        cache2[l2pos].tag = add.block;
        cache2[l2pos].updated = false;
        cache2[l2pos].cost = COST_ACCESS_L1 + COST_ACCESS_L2;
        cache2[l2pos].cacheHit = 2;
        // !Can be improved?
        updateMachineInfos(machine, &(cache2[l2pos]));
        return &(cache2[l2pos]);
    } else { 
        /* Block only in memory RAM, need to bring it to cache and manipulate the blocks */
        l2pos = memoryCacheMapping(cache1[l1pos].tag, &machine->l2); /* Need to check the position of the block that will leave the L1 */
        if (!canOnlyReplaceBlock(cache1[l1pos])) { 
            /* The block on cache L1 cannot only be replaced, the memories must be updated */
            if (!canOnlyReplaceBlock(cache2[l2pos])) 
                /* The block on cache L2 cannot only be replaced, the memories must be updated */
                RAM[cache2[l2pos].tag] = cache2[l2pos].block;
            cache2[l2pos] = cache1[l1pos];
        }
        cache1[l1pos].block = RAM[add.block];
        cache1[l1pos].tag = add.block;
        cache1[l1pos].updated = false;
        cache1[l1pos].cost = COST_ACCESS_L1 + COST_ACCESS_L2 + COST_ACCESS_RAM;
        cache1[l1pos].cacheHit = 3;
    }
    updateMachineInfos(machine, &(cache1[l1pos]));
    return &(cache1[l1pos]);
}
