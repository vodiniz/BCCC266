#ifndef MMU_H
#define MMU_H

#include "memory.h"
#include "instruction.h"
#include "cpu.h"

int findOnCache(int address, Cache* cache);
Line* MMUSearchOnMemorys(Address, Machine*);


#endif // !MMU_H