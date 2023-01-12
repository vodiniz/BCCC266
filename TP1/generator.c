#include "generator.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

Instruction* generateRandomInstructions(int ramSize) {
    // 01|22|13|45 => isto é uma instrução
    // 02|33|12|01 => isto é outra instrução
            
    // 0 => salvar na memória
    // 1 => opcode => somar
    // 2 => opcode => subtrair
    //-1 => halt
    
    // 22 => significa um endereço da RAM (10 endereço) 
    // 13 => significa 2o endereço
    // 45 => significa 3o endereco
    //ramSize => ESTA FORA DO INTERVALO DE 0 A ramSize DA MEMÓRIA RAM

    Instruction* instructions = (Instruction*) malloc(10 * sizeof(Instruction));

    for (int i=0; i<9; i++){
        instructions[i].opcode = rand() % 4; //0, 1, 2, 3
        instructions[i].info1 = rand() % ramSize; //0 ... RAM_SIZE
        do {
            instructions[i].info2 = rand() % ramSize; //0 ... RAM_SIZE
        } while (instructions[i].info1 == instructions[i].info2);
        instructions[i].info3 = rand() % ramSize; //0 ... RAM_SIZE
    }
    
    //inserindo a ultima instrucao do programa que nao faz nada que presta
    instructions[9].opcode =-1;
    instructions[9].info1 = -1;
    instructions[9].info2 = -1;
    instructions[9].info3 = -1;
    
    return instructions;
}

Instruction* generateMultiplicationInstructions(int multiplier, int multiplying){
    Instruction* instructions = (Instruction*) malloc((3 + multiplier) * sizeof(Instruction));
    //Três instruções extras
        //1 - Salvar o multiplier na memória
        //2- Colocando 0 na posição no resultado na RAM
    instructions[0].opcode = 0;
    instructions[0].info1 = multiplying; //Conteúdo a ser salvo na RAM
    instructions[0].info2 = 0; //Posição da RAM
    
    instructions[1].opcode = 0;
    instructions[1].info1 = 0; //Coloca 0 (elemento neutro da soma)
    instructions[1].info2 = 1; //Posição da RAM

    for (int i = 0; i < multiplier; i++){
        instructions[i+2].opcode = 1; //Opcode para soma
        instructions[i+2].info1 = 0; //Posição do multiplying
        instructions[i+2].info2 = 1; //Posição do resultado da multiplicação
        instructions[i+2].info3 = 1; //Posição do resultado da multiplicação
    }

    //Inserindo a última instrução do programa que não faz nada que presta
    instructions[multiplier+2].opcode = -1;
    instructions[multiplier+2].info1 = -1;
    instructions[multiplier+2].info2 = -1;
    instructions[multiplier+2].info3 = -1;

    return instructions;
}

Instruction* generatePotentiationInstructions(int base, int exponent){

    int totalSums = (int) pow(base, exponent - 1); 

    Instruction* instructions = (Instruction*) malloc((3 + totalSums ) * sizeof(Instruction));
    printf("malloc size: %d\n", 3 + totalSums);

    //Três instruções extras
        //1 - Salvar o exponent na memória
        //2- Colocando 0 na posição no resultado na RAM

    instructions[0].opcode = 0;
    instructions[0].info1 = base; //Conteúdo a ser salvo na RAM
    instructions[0].info2 = 0; //Posição da RAM
    
    instructions[1].opcode = 0;
    instructions[1].info1 = 0; //Coloca 0 (elemento neutro da soma)
    instructions[1].info2 = 1; //Posição da RAM

    for (int i = 0; i < totalSums; i++){

        printf("Instructions[%d]\n", 2 + i);
        instructions[2 + i].opcode = 1; //Opcode para soma
        instructions[2 + i].info1 = 0; //Posição da base
        instructions[2 + i].info2 = 1; //Posição do resultado da potenciação
        instructions[2 + i].info3 = 1; //Posição do resultado da potenciação
    }



    //Inserindo a última instrução do programa que não faz nada que presta
    instructions[2 + totalSums].opcode = -1;
    instructions[2 + totalSums].info1 = -1;
    instructions[2 + totalSums].info2 = -1;
    instructions[2 + totalSums].info3 = -1;

    return instructions;
}

Instruction* readInstructions(char* fileName, int* ramSize) {
    printf("FILE -> %s\n", fileName);
    FILE* file = fopen(fileName, "r"); // Abrindo arquivo no modo leitura
    
    if (file == NULL) {
        printf("Arquivo nao pode ser aberto.\n");
        exit(1);
    }

    int n, i = 0;
    fscanf(file, "%d %d", ramSize, &n);
    Instruction* instructions = (Instruction*) malloc(n * sizeof(Instruction));
    while (i < n) {
        fscanf(file, "%d %d %d %d", &instructions[i].opcode, &instructions[i].info1, &instructions[i].info2, &instructions[i].info3);
        i++;
    }
    fclose(file); // Fechando o arquivo

    return instructions;
}
