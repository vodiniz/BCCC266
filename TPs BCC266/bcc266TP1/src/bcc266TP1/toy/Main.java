package bcc266TP1.toy;

import java.util.Random;

public class Main {
	
	int[] memoria;
	int[][] instrucoes;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Main();
		
	}
	
	public Main(){
		//simula uma calculadora com 2 programas (um aleatório e outro para multiplicação)
		criaMemoria(1000);
		criaProgramaMultiplicacao(9, 99);
		rodaMaquina();
		System.out.println();
		System.out.println();
		System.out.println("comecou programa aleatorio!!");
		criaProgramaAleatorio(100, 1000);		
		rodaMaquina();
		
	}
	
	//o que é instrução?
	//Ex. opcode:address1:address2:address3
	// somar: 1
	// subtrair: 2
	// atribuir valor a memoria: 3
	//imprimir o conteudo de um endereço de memória: 4
	//terminar: -1
	void criaProgramaAleatorio(int capacidade, int capacidadeMemoriaDados){
		int opcode, address1,address2,address3;
		instrucoes = new int[capacidade][4];
		Random random = new Random();
		for(int i=0; i<capacidade-1;i++){
			opcode = random.nextInt(4)+1;
			address1 = random.nextInt(capacidadeMemoriaDados);
			address2 = random.nextInt(capacidadeMemoriaDados);
			address3 = random.nextInt(capacidadeMemoriaDados);
			instrucoes[i][0] = opcode;
			instrucoes[i][1] =address1;
			instrucoes[i][2] =address2;
			instrucoes[i][3] =address3;
		}
		//adicionando a ultima instrução do programa aleatório
		instrucoes[capacidade-1][0] = -1;
		instrucoes[capacidade-1][1] = -1;
		instrucoes[capacidade-1][2] = -1;
		instrucoes[capacidade-1][3] = -1;
	}
	
	void criaProgramaMultiplicacao(int multiplicando, int multiplicador){
		instrucoes = new int[multiplicador+3][4];
		
		//atribuindo o multiplicando a 1a posicao da memoria
		instrucoes[0][0] = 3;
		instrucoes[0][1] = multiplicando;
		instrucoes[0][2] = 0;
		instrucoes[0][3] = -1;
		
		//atribuindo o multiplicando a 2a posicao da memoria
		instrucoes[1][0] = 3;
		instrucoes[1][1] = multiplicando;
		instrucoes[1][2] = 1;
		instrucoes[1][3] = -1;
		
		//fazendo as somas sucessivas
		for(int i=0; i<multiplicador; i++){
			instrucoes[i+2][0] = 1;
			instrucoes[i+2][1] = 0;
			instrucoes[i+2][2] = 1;
			instrucoes[i+2][3] = 1;
		}
		
		//imprimindo o conteudo da posicao 1 da memoria
		instrucoes[multiplicador+1][0] = 4;
		instrucoes[multiplicador+1][1] = 1;
		instrucoes[multiplicador+1][2] = -1;
		instrucoes[multiplicador+1][3] = -1;
		
		//inserindo a instrução HALT para terminar o programa de mult
		instrucoes[multiplicador+2][0] = -1;
		instrucoes[multiplicador+2][1] = -1;
		instrucoes[multiplicador+2][2] = -1;
		instrucoes[multiplicador+2][3] = -1;
	}
	
	private void criaMemoria(int capacidade){
		memoria = new int[capacidade];
		Random random = new Random();
		for(int i=0; i<capacidade; i++){
			memoria[i] = random.nextInt(100);
		}
	}
	
	private void rodaMaquina(){
		
		int opcode=0;
		int pc = 0;
		int add1, add2, add3;
		
		while(opcode!=-1){
			opcode = instrucoes[pc][0];
			add1 = instrucoes[pc][1];
			add2 = instrucoes[pc][2];
			add3 = instrucoes[pc][3];
			
			//realizando SUM e SUB
			
			switch (opcode){
			case 1:{
				int soma = memoria[add1]+memoria[add2];
				System.out.println("soma: " + soma);
				memoria[add3] = soma;
				break;
			}
			case 2:{
				int sub = memoria[add1]-memoria[add2];
				System.out.println("sub: " + sub);
				memoria[add3] = sub;
				break;
			}
			case 3:{
				memoria[add2]=add1;
				System.out.println("atribuiu o valor a memoria: " + add1);
				break;
			}
			case 4:{
				System.out.println("conteudo da memoria: " + memoria[add1]);
				break;
			}
			case -1:{
				System.out.println("acabou o programa....");				
			}
			}
			
			pc++;
		}
	}

}
