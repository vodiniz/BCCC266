#ifndef MEMORY_H
#define MEMORY_H

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "constants.h"

typedef struct {
    int words[WORDS_SIZE]; // PALAVRA DE 4 "BITS"
} MemoryBlock; 

typedef struct {
    MemoryBlock* blocks; // LINHA NA MEMORIA RAM, 
    int size;   // TAMANHO DA MEMORIA RAM
    int ramHit;
} RAM;

typedef struct {
    MemoryBlock block; // BLOCO DE MEMORIA, QUE CONTEM 1 VETOR DE PALAVRAS, O BLOCO REPRESENTA CACHE L1 L2 L3 RAM E HD
    int tag; /* Address of the block in memory RAM */
    bool updated;
    int cost; // custo de acesso a CACHE/RAM
    int cacheHit; // ACHOU INFORMACAO NA CACHE
    int timesUsed;
    int timeOnCache;
} Line;

typedef struct {
    Line* lines; // CACHE TEM ALGUMAS PALAVRAS
    int size; // E UM TAMANHO
} Cache;

typedef struct {
    char fileName[FILE_STR_TAM]; //salvar o nome do arquivo
    FILE *file;    
} File;

typedef struct {
    int size; //tamanho do hc
    File *files; //arquivos

} HD;




void startCache(Cache*, int);
void stopCache(Cache*);

void startRAM(RAM*, int);
void stopRAM(RAM*);

void startHD(HD*, int);
void stopHD(HD*);



#endif // !MEMORY_H