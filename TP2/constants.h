#include <stdbool.h>

#define WORDS_SIZE 4
#define INVALID_ADD -1
#define COST_ACCESS_L1 1
#define COST_ACCESS_L2 10
#define COST_ACCESS_L3 100
#define COST_ACCESS_RAM 1000

// 1 MAPEAMENTO DIRETO
// 2  LRU (Least Recently Used)
// 3  LFU (Least Frequently Used)

#define SUBSTITUTION_METHOD 2

// #define PRINT_INTERMEDIATE_RAM
#define PRINT_LOG
