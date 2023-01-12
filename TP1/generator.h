#ifndef GENERATOR_H
#define GENERATOR_H

#include "cpu.h"

Instruction* generateRandomInstructions(int);
Instruction* generateMultiplicationInstructions(int, int);
Instruction* generatePotentiationInstructions(int base, int exponent);
Instruction* readInstructions(char*, int*);

#endif // !GENERATOR_H