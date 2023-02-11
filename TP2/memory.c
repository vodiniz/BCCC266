#include "memory.h"


//ALOCA DINAMICAMENTE O TAMANHO DA RAM, BASEADO NO PARAMETRO PASSADO
void startRAM(RAM* ram, int size) {
    ram->blocks = (MemoryBlock*) malloc(sizeof(MemoryBlock) * size);
    ram->size = size;

    // INICIA CADA VALOR DA PALAVRA COM UM BLOCO DE VALOR ALEATORIO
    for (int i=0;i<size;i++) {
        for (int j=0;j<WORDS_SIZE;j++)
            ram->blocks[i].words[j] = rand() % 100;            
    }
}

// DESALOCA A RAM
void stopRAM(RAM *ram) {
    free(ram->blocks);
}


// ALOCA DINAMICAMENTE A CACHE COM UM TAMANHO ESPECIFICO
void startCache(Cache* cache, int size) {
    cache->lines = (Line*) malloc(sizeof(Line) * size);
    cache->size = size;

    // INICIA A CACHE COM UM VALOR INVÁLIDO ( -1)
    for (int i=0;i<size;i++){
        cache->lines[i].tag = INVALID_ADD;
        cache->lines[i].timeOnCache = 0;
        cache->lines[i].timesUsed = 0;
    }
}


//DESALOCA DINAMICAMENTE A CACHE
void stopCache(Cache *cache) {
    free(cache->lines);
}
