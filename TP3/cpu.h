#ifndef CPU_H
#define CPU_H

#include "instruction.h"
#include "memory.h"

typedef struct {
    Instruction* instructions;
    RAM ram;
    Cache l1; // cache L1
    Cache l2; // cache L2
    Cache l3; // cache L3
    HD hd;
    int hitL1, hitL2, hitL3, hitRAM, hitHD;
    int missL1, missL2, missL3, missRAM;
    int totalCost;
    int interruption;
} Machine;

void start(Machine*, Instruction*, int*);
void stop(Machine*);
void run(Machine*);
void printMemories(Machine*);


MemoryBlock readHDblocks(File *hd);

#endif // !CPU_H