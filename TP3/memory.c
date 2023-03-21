#include <stdio.h>
#include <stdlib.h>
#include <string.h>
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


void startHD(HD* hd, int size){
    
    // char nomeArquivo[50];
    hd->size = size;
    hd->files = malloc (size * sizeof(File));

    for(int i = 0; i < size; i++){
        sprintf(hd->files[i].fileName, "0x0%d.txt", i);
        // strcpy(, nomeArquivo);
        hd->files[i].file = fopen(hd->files[i].fileName, "w");

        fprintf(hd->files[i].file, "%d\n", WORDS_SIZE);


        for (int j=0;j<WORDS_SIZE;j++){

            fprintf(hd->files[i].file, "%d\n", rand() % 100);

        }

        fclose(hd->files[i].file);
    }

}

void stopHD(HD* hd){
    
    free(hd->files);

}