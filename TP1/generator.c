#include "generator.h"
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <string.h>

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
        instructions[i+2].info2 = 1; //Posição do resultado da multiplicaçao
        instructions[i+2].info3 = 1; //Posição do resultado da multiplicaçao
    }

    //Inserindo a última instrução do programa que não faz nada que presta
    instructions[multiplier+2].opcode = -1;
    instructions[multiplier+2].info1 = -1;
    instructions[multiplier+2].info2 = -1;
    instructions[multiplier+2].info3 = -1;

    return instructions;
}


Instruction* generateDivisionInstructions(int dividend, int divisor){


    Instruction* instructions = (Instruction*) malloc((4 + dividend/divisor) * sizeof(Instruction));

    instructions[0].opcode = 0;
    instructions[0].info1 = dividend; //Conteúdo a ser salvo na RAM
    instructions[0].info2 = 0; //Posição da RAM
    
    instructions[1].opcode = 0;
    instructions[1].info1 = divisor; //Conteúdo a ser salvo na RAM
    instructions[1].info2 = 1; //Posição da RAM


    int result = 0; // sempre somando 1 ao resultado, para saber quando parar, tambem serve como um indice para manter a contagem


    // uma sugestão para o futuro, seria adicionar uma operação de soma, e um valor inicial de 0 em alguma
    // posição da RAM para fazer uma contagem mais interessante do resultado, e não depender da variável


    //loop enquanto der para dividir o número
    while ((result + 1) * divisor <= dividend){

        instructions[result + 2].opcode = 2; //Opcode para subtração
        instructions[result + 2].info1 = 0; //Posição do dividendo
        instructions[result + 2].info2 = 1; //Posição do divisor
        instructions[result + 2].info3 = 0; //Posição do resultado da divisão
        result++;
    }


    //salvando o resultado na RAM
    instructions[result + 2].opcode = 0;
    instructions[result + 2].info1 = result;    //Conteúdo a ser salvo na RAM
    instructions[result + 2].info2 = 2;         //Posição da RAM



    //inserindo a ultima instrucao do programa que nao faz nada que presta

    instructions[result + 3].opcode = -1;
    instructions[result + 3].info1 = -1;
    instructions[result + 3].info2 = -1;
    instructions[result + 3].info3 = -1;

    return instructions;
}

Instruction* generateModInstructions(int dividend, int divisor){


    Instruction* instructions = (Instruction*) malloc((4 + dividend/divisor) * sizeof(Instruction));

    instructions[0].opcode = 0;
    instructions[0].info1 = dividend; //Conteúdo a ser salvo na RAM
    instructions[0].info2 = 0; //Posição da RAM
    
    instructions[1].opcode = 0;
    instructions[1].info1 = divisor; //Conteúdo a ser salvo na RAM
    instructions[1].info2 = 1; //Posição da RAM



    // sempre somando 1 ao resultado, para saber quando parar, tambem serve como um indice para manter a contagem
    int result = 0;

    //loop enquanto der para dividir o número
    while ((result + 1) * divisor <= dividend){

        instructions[result + 2].opcode = 2; //Opcode para subtração
        instructions[result + 2].info1 = 0; //Posição do dividendo
        instructions[result + 2].info2 = 1; //Posição do divisor
        instructions[result + 2].info3 = 0; //Posição do resultado da divisão
        result++;
    }


    //inserindo a ultima instrucao do programa que nao faz nada que presta
    
    instructions[result + 2].opcode = -1;
    instructions[result + 2].info1 = -1;
    instructions[result + 2].info2 = -1;
    instructions[result + 2].info3 = -1;

    return instructions;
}




Instruction* generatePotentiationInstructions(int base, int exponent){


    //matriz de instruções, vulgo vetor de vetor, porque a potenciação são várias multiplicações.
    //cada linha da matriz representa uma instrução de uma multiplicação
    Instruction **instructionMatrix;
    Instruction *instructions;


    //alocação da matriz de instruções, que em a^b sempre teremos b - 1 linhas
    instructionMatrix = (Instruction**) malloc( (exponent - 1) * sizeof(Instruction));
 

    instructionMatrix[0] = generateMultiplicationInstructions(base, base);

    // loop de cada linha de instrução para gerar elas
    for ( int i = 1; i < exponent - 1; i++){

        // nas proximas iterações, o valor inicial da multiplicação já está na memoria RAM, isso desde que
        //não esqueçamos de substituir os comandos de salvar valor na memória ( opcode = 0), por isso não fará diferença
        // o segundo parametro da função.
        instructionMatrix[i] = generateMultiplicationInstructions(base , 0);
    }


    // simples loop para imprimir os opcode da matriz para finalidade de teste.
    // for (int i = 0; i < exponent -1;i++){
    //     for ( int j = 0; j <3 + base; j++){
    //         printf("%d   ",instructionMatrix[i][j].opcode);
    //     }
    //     printf("\n");
    // }



    // alocação das instruções finais que serão retornadas no fim da função.
    // a alocação se dá por 
    //4 + base * (exponent - 1) + 2 * (exponent - 2))  * sizeof(Instruction))
    // em que temos 3 operações que sempre vão aparecer, o 0 0 nas duas primeiras instruções
    // e -1 para indicar o fim das instruções
    // base * (exponent -1) é a quantidade de somas ( opcode 1) que podemos encontrar no meio da matriz.
    // essas serão as quantidades de somas necessárias
    // 2 * ( exponent - 2 ) serão o números de operações auxiliares ( opcode 3 e 0) para a manipulaçao necessária da Ram


    // printf("tamanho do malloc: %d\n", 3 + base * (exponent - 1) + 2 * (exponent - 2));

    instructions = (Instruction*) malloc((3 + base * (exponent - 1) + 2 * (exponent - 2))  * sizeof(Instruction)); // ALOCAÇÃO TÁ UM POUQUINHO ERRADA. CONFERIR

    instructions[0].opcode = 0;
    instructions[0].info1 = base; //Conteúdo a ser salvo na RAM
    instructions[0].info2 = 0; //Posição da RAM
    
    instructions[1].opcode = 0;
    instructions[1].info1 = 0; //Coloca 0 (elemento neutro da soma)
    instructions[1].info2 = 1; //Posição da RAM


    int index_counter = 2; // INDICE DO VETOR DE INSTRUÇÕES FINAL


    //loop para percorrer a matriz de isntruções e copiar o valor das instruções para nosso vetor final
    for (int i = 0; i < exponent -1;i++){
        for ( int j = 2; j < 3 + base - 1; j++){

            instructions[index_counter].opcode = instructionMatrix[i][j].opcode;
            instructions[index_counter].info1 = instructionMatrix[i][j].info1; 
            instructions[index_counter].info2 = instructionMatrix[i][j].info2; 
            instructions[index_counter].info3 = instructionMatrix[i][j].info3; 
            index_counter++;

        }
        // como no final de cada linha devemos ter as operações auxioliares para modificar
        // valores na RAM, precisamos inserilas depois de cada sequencia de soma
        //exceto se for a ultima linha da matriz, assim
        // ai não devemos inserir nem o 3 nem o 0
        if (i >= exponent - 2){
            {};
        } else {

            instructions[index_counter].opcode = 3;
            instructions[index_counter].info1 = 1; 
            instructions[index_counter].info2 = 0; 
            index_counter++;

            instructions[index_counter].opcode = 0;
            instructions[index_counter].info1 = 0; 
            instructions[index_counter].info2 = 1; 
            index_counter++;
        }


    }


    instructions[index_counter].opcode = -1;
    instructions[index_counter].info1 = -1;
    instructions[index_counter].info2 = -1;
    instructions[index_counter].info3 = -1;


    //imprimindo o opcode das instruções finais para motivo de teste
    // printf(" Instruções Finais\n");

    // for( int i = 0; i < 5000; i++){
    //     printf("%d  ", instructions[i].opcode);
    // }
    // printf("\n");


    for (int i = 0; i < exponent - 1; i++){
        free(instructionMatrix[i]);
    }
    free(instructionMatrix);


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
