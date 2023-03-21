#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <math.h>

#include "cpu.h"
#include "mmu.h"
#include "constants.h"
#include "generator.h"

void start(Machine* machine, Instruction* instructions, int* memoriesSize) {
    startRAM(&machine->ram, memoriesSize[0]);
    startCache(&machine->l1, memoriesSize[1]);
    startCache(&machine->l2, memoriesSize[2]);
    startCache(&machine->l3, memoriesSize[3]);
    startHD(&machine->hd, memoriesSize[4]);
    machine->interruption = 0;

    machine->instructions = instructions;
    machine->hitL1 = 0;
    machine->hitL2 = 0;
    machine->hitL3 = 0;
    machine->hitRAM = 0;
    machine->hitHD = 0;

    machine->missL1 = 0;
    machine->missL2 = 0;
    machine->missL3 = 0;
    machine->hitRAM = 0;
    machine->totalCost = 0;
}

void stop(Machine* machine) {
    free(machine->instructions);
    stopRAM(&machine->ram);
    stopCache(&machine->l1);
    stopCache(&machine->l2);
    stopCache(&machine->l3);
    stopHD(&machine->hd);

}

void executeInstruction(Machine* machine, int PC) {


    Instruction instruction = machine->instructions[PC];
    // Registers
    int word1, word2;

    // Addresses to be consulted
    Address add1 = instruction.add1;
    Address add2 = instruction.add2;
    Address add3 = instruction.add3;
    
    // Line find in memory
    Line* line;
    
    switch (instruction.opcode) {
        case -1:
            printf("  > Ending execution.\n");
            break;
        case 0: // Taking information to RAM
            line = MMUSearchOnMemorys(add1, machine); /* Searching block on memories */
            word1 = line->block.words[add1.word];
            #ifdef PRINT_LOG
                printf("  > MOV BLOCK[%d.%d.%d](%4d) > ", line->cacheHit, add1.block, add1.word, line->block.words[add1.word]);
            #endif
            
            line = MMUSearchOnMemorys(add2, machine); /* Searching block on memories */
            #ifdef PRINT_LOG
                printf("BLOCK[%d.%d.%d](%4d|", line->cacheHit, add2.block, add2.word, line->block.words[add2.word]);
            #endif

            line->block.words[add2.word] = word1;
            line->updated = true;
            #ifdef PRINT_LOG
                printf("%4d).\n", line->block.words[add2.word]);
            #endif
            break;
        case 1: // Sum
            line = MMUSearchOnMemorys(add1, machine); /* Searching block on memories */
            word1 = line->block.words[add1.word];
            #ifdef PRINT_LOG
                printf("  > SUM BLOCK[%d.%d.%d](%4d)", line->cacheHit, add1.block, add1.word, line->block.words[add1.word]);
            #endif

            line = MMUSearchOnMemorys(add2, machine); /* Searching block on memories */
            word2 = line->block.words[add2.word];
            #ifdef PRINT_LOG
                printf(" + BLOCK[%d.%d.%d](%4d)", line->cacheHit, add2.block, add2.word, line->block.words[add2.word]);
            #endif

            line = MMUSearchOnMemorys(add3, machine); /* Searching block on memories */
            #ifdef PRINT_LOG
                printf(" > BLOCK[%d.%d.%d](%4d|", line->cacheHit, add3.block, add3.word, line->block.words[add3.word]);
            #endif

            line->updated = true;
            line->block.words[add3.word] = word2 + word1;
            #ifdef PRINT_LOG
                printf("%4d).\n", line->block.words[add3.word]);
            #endif
            break;
        case 2: // Subtract
            line = MMUSearchOnMemorys(add1, machine); /* Searching block on memories */
            word1 = line->block.words[add1.word];
            #ifdef PRINT_LOG
                printf("  > SUB BLOCK[%d.%d.%d](%4d)", line->cacheHit, add1.block, add1.word, line->block.words[add1.word]);
            #endif

            line = MMUSearchOnMemorys(add2, machine); /* Searching block on memories */
            word2 = line->block.words[add2.word];
            #ifdef PRINT_LOG
                printf(" - BLOCK[%d.%d.%d](%4d)", line->cacheHit, add2.block, add2.word, line->block.words[add2.word]);
            #endif

            line = MMUSearchOnMemorys(add3, machine); /* Searching block on memories */
            #ifdef PRINT_LOG
                printf(" > BLOCK[%d.%d.%d](%4d|", line->cacheHit, add3.block, add3.word, line->block.words[add3.word]);
            #endif

            line->updated = true;
            line->block.words[add3.word] = word2 - word1;

            #ifdef PRINT_LOG
                printf("%4d).\n", line->block.words[add3.word]);
            #endif

            break;

        default:
            printf("Invalid instruction.\n");            
    }
    #ifdef PRINT_INTERMEDIATE_RAM
        printMemories(machine);
    #endif

    int interruption_chance = rand() % 10;

    if(interruption_chance == 1 && !machine->interruption){
        printf("Tratando interrupções\n");
        machine->interruption = 1;
        Instruction *original_instructions = machine->instructions;
        Instruction *interruption_instructions = generateRandomInstructions(machine->ram.size);
        machine->instructions = interruption_instructions;
        run(machine);
        machine->instructions = original_instructions;
        free(interruption_instructions);
        printf("Interrupções tratadas.\n");
        machine->interruption = 0;
    }

}

void run(Machine* machine) {    
    int PC = 0; // Program Counter
    while(machine->instructions[PC].opcode != -1) {
        executeInstruction(machine, PC++);
        printf("\tL1:(%6d, %6d) | L2:(%6d, %6d) | L3:(%6d, %6d) | RAM:(%6d, %6d) | HD:(%6d) | COST: %d\n", 
            machine->hitL1, machine->missL1, 
            machine->hitL2, machine->missL2,
            machine->hitL3, machine->missL3,
            machine->hitRAM, machine->missRAM,
            machine->hitHD,
             machine->totalCost);
    }
}

void printc(char *text, int fieldWidth) {
    int padlenLeft = floor((fieldWidth - strlen(text)) * 0.5);
    int padlenRight = ceil((fieldWidth - strlen(text)) * 0.5);
    printf("|%*s%s%*s|", padlenLeft, "", text, padlenRight, "");
}

void printcolored(int n, bool updated) {
    if (updated)
        printf("\x1b[1;37;42m[%5d]\x1b[0m", n); // Green
    else
        printf("\x1b[1;37;41m[%5d]\x1b[0m", n); // Red
}

void printMemories(Machine* machine) {
    printf("\x1b[0;30;47m     ");
    printc("HD", WORDS_SIZE * 8 - 1);
    printc("RAM", WORDS_SIZE * 8 - 1);
    printc("Cache L3", WORDS_SIZE * 8 + 6);
    printc("Cache L2", WORDS_SIZE * 8 + 6);
    printc("Cache L1", WORDS_SIZE * 8 + 6);
    printf("\x1b[0m\n");



    MemoryBlock hd_block;



    for (int i=0;i<machine->hd.size;i++) {
        printf("\x1b[0;30;47m%5d|\x1b[0m", i);
        hd_block = readHDblocks(&machine->hd.files[i]);

        for (int j=0;j<WORDS_SIZE;j++)
            printf(" %5d |", hd_block.words[j]);


        if(i < machine->ram.size){
            printf("|");
            for (int j=0;j<WORDS_SIZE;j++)
                printf(" %5d |", machine->ram.blocks[i].words[j]);

            if (i < machine->l3.size) {
                printf("|");
                printcolored(machine->l3.lines[i].tag, machine->l3.lines[i].updated);
                for (int j=0;j<WORDS_SIZE;j++)
                        printf(" %5d |", machine->l3.lines[i].block.words[j]);

                if (i < machine->l2.size) {
                    printf("|");
                    printcolored(machine->l2.lines[i].tag, machine->l2.lines[i].updated);
                    for (int j=0;j<WORDS_SIZE;j++)
                        printf(" %5d |", machine->l2.lines[i].block.words[j]);
                    if (i < machine->l1.size) {
                        printf("|");
                        printcolored(machine->l1.lines[i].tag, machine->l1.lines[i].updated);
                        for (int j=0;j<WORDS_SIZE;j++)
                            printf(" %5d |", machine->l1.lines[i].block.words[j]);
                    }
                }
            }

        }

        printf("\n");
    }


}


MemoryBlock readHDblocks(File *hd){

    MemoryBlock block;

    hd->file = fopen(hd->fileName, "r");

    int words_size;
    fscanf(hd->file, "%d", &words_size);

    for(int i = 0; i < WORDS_SIZE; i++){
        fscanf(hd->file, "%d", &block.words[i]);
    }

    fclose(hd->file);
    return block;

}