#ifndef CPU_H
#define CPU_H

#include "instruction.h"
#include "memory.h"

typedef struct {
    Instruction* instructions;
    RAM ram;
    Cache l1; // cache L1
    Cache l2; // cache L2
    int hitL1, hitL2, hit3, hitRAM;
    int missL1, missL2, missl3;
    int totalCost;
} Machine;

void start(Machine*, Instruction*, int*);
void stop(Machine*);
void run(Machine*);
void printMemories(Machine*);

#endif // !CPU_H