#include "cpu.h"
#include "generator.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int main(int argc, char**argv) {

    srand(time(NULL));   // Inicializacao da semente para os numeros aleatorios.
    int rand1, rand2;

    if (argc != 3) {
        printf("Numero de argumentos invalidos! Sao 3.\n");
        printf("Linha de execucao: ./exe TIPO_INSTRUCAO [TAMANHO_RAM|ARQUIVO_DE_INSTRUCOES]\n");
        printf("\tExemplo 1 de execucao: ./exe random 10\n");
        printf("\tExemplo 3 de execucao: ./exe file arquivo_de_instrucoes.txt\n");
        return 0;
    }

    int ramSize;
    Machine machine;
    Instruction *instructions;

    if (strcmp(argv[1], "random") == 0) {
        ramSize = atoi(argv[2]);
        instructions = generateRandomInstructions(ramSize);
    } else if (strcmp(argv[1], "randomMultiplication") == 0) {
        ramSize = atoi(argv[2]);
        instructions = generateMultiplicationInstructions(rand() % 100 + 1,rand() % 100 + 1);
    } else if ( strcmp(argv[1], "randomPotentiation") == 0){
        ramSize = atoi(argv[2]);
        instructions = generatePotentiationInstructions(rand() % 30 + 1, rand() % 20 + 1);
    } else if ( strcmp(argv[1], "randomDivision") == 0){
        ramSize = atoi(argv[2]);
        instructions = generateDivisionInstructions(rand() % 15000 + 1, rand() % 300 + 1);
    } else if ( strcmp(argv[1], "randomMod") == 0){
        ramSize = atoi(argv[2]);
        instructions = generateModInstructions(rand() % 15000 + 1, rand() % 300 + 1);
    } else if ( strcmp(argv[1], "example") == 0){
        ramSize = atoi(argv[2]);
    } 
    
    
    else if (strcmp(argv[1], "file") == 0) {
        instructions = readInstructions(argv[2], &ramSize);
    } 
    else {
        printf("Opcao invalida.\n");
        return 0;
    }
    

    printf("Iniciando a maquina...\n");


    if ( strcmp(argv[1], "example") == 0){

        printf("Realizando operações básicas aleatórias.\n");
        instructions = generateRandomInstructions(ramSize);
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");
        
        printf("--------------------\n");

        rand1 = rand() % 100 + 1;
        rand2 = rand() % 100 + 1;
        printf("Realizando uma Multiplicação aleatória de %d e %d.\n", rand1, rand2);
        instructions = generateMultiplicationInstructions(rand1, rand2);
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");
        
        printf("--------------------\n");


        rand1 = rand() % 30 + 1;
        rand2 = rand() % 20 + 2;
        printf("Realizando uma Potenciação aleatória de %d elevado a %d.\n", rand1, rand2);
        instructions = generatePotentiationInstructions(rand1, rand2);
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");
        
        printf("--------------------\n");


        rand1 = rand() % 15000 + 1;
        rand2 = rand() % 300 + 1;
        printf("Realizando uma divisão aleatória de %d e %d.\n", rand1, rand2);
        instructions = generateDivisionInstructions(rand1, rand2);
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");
        
        printf("--------------------\n");



        rand1 = rand() % 15000 + 1;
        rand2 = rand() % 300 + 1;
        printf("Realizando o módulo de uma divisão aleatóriade %d e %d.\n", rand1, rand2);
        instructions = generateModInstructions(rand1, rand2);
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");
        
        printf("--------------------\n");
        

    } else {
        start(&machine, instructions, ramSize);
        printRAM(&machine);
        run(&machine);
        printRAM(&machine);
        stop(&machine);
        printf("Finalizando a maquina...\n");

    }


    return 0;
}