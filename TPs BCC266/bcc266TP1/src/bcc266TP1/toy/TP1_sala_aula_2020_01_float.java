package bcc266TP1.toy;

import java.util.Random;

public class TP1_sala_aula_2020_01_float {
	int[][] memoriaInstrucoes;
	float[] RAM = new float[100];
	//RAM[45] = 3,14
	
	public static void main(String[] args){
		new TP1_sala_aula_2020_01_float();
	}
	
	TP1_sala_aula_2020_01_float(){
		montarRam();
		//montarInstrucoesProgramaAleatorio();
		montarInstrucoesProgramaMultiplicacao(512, 1024);
		maquina();
		
		imprimir();
	}
	
	void imprimir(){
		for(int i=0; i<100; i++){
			System.out.println("RAM " + i + " igual a: " + RAM[i]);
		}		
	}
	
	void maquina(){
		//registradores
		int PC =0;
		int opcode = Integer.MAX_VALUE;//maior inteiro possível 2^31 -1 =~ 2bi
		while(opcode!=-1){
			int[] umaInstrucao = memoriaInstrucoes[PC];
			opcode = umaInstrucao[0];
			switch (opcode){
				//levar para RAM
				case 0:{
					RAM[umaInstrucao[2]] = (float)umaInstrucao[1]; 
					// 0|33|12|-1 => o 33 eh o valor e não o endereço apenas qdo o opcode é 0
					System.out.println("levando para RAM o valor: " + umaInstrucao[1]);
					break;
				}
				case 1:{
					//somar
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					//buscar na RAM
					float conteudoRam1 = RAM[end1];
					float conteudoRam2 = RAM[end2];
					float soma = conteudoRam1+conteudoRam2;
					//salvando resultado na RAM
					int end3 = umaInstrucao[3];
					RAM[end3] = soma;
					
					System.out.println("somando "+ soma);
					break;
				}
				case 2:{
					//subtrair
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					//buscar na RAM
					float conteudoRam1 = RAM[end1];
					float conteudoRam2 = RAM[end2];
					float sub = conteudoRam1-conteudoRam2;
					//salvando resultado na RAM
					int end3 = umaInstrucao[3];
					RAM[end3] = sub;
					
					System.out.println("subtraindo "+ sub);
					
					break;
				}
			}
			
			PC++;
		}
		
	}

	void montarRam(){
		Random r = new Random();
		for(int i=0; i<100; i++){
			RAM[i] = r.nextFloat();
		}		
	}
	
	void montarInstrucoesProgramaAleatorio(){
		//01|22|13|45 => isto é uma instrução
		//02|33|12|01 => isto é outra instrução
				
		//0 => salvar na memória
		//1 => opcode => somar
		//2 => opcode => subtrair
		//-1 => halt
		
	    //22 => significa um endereço da RAM (10 endereço) 
		//13 => significa 2o endereço
		//45 => significa 3o endereco
		//101 => ESTÁ FORA DO INTERVALO DE 0 A 99 DA MEMÓRIA RAM
		memoriaInstrucoes= new int[10][4];
		int[] umaInstrucao=null;
		Random r = new Random();
		for (int i=0; i<9; i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] = r.nextInt(3);//0, 1, 2
			umaInstrucao[1] = r.nextInt(100); //0 ... 99
			umaInstrucao[2] = r.nextInt(100); //0 ... 99
			umaInstrucao[3] = r.nextInt(100); //0 ... 99
			
			memoriaInstrucoes[i] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que nao faz nada que presta
		umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;
		
		memoriaInstrucoes[9] = umaInstrucao;
		
		
	}
	
	public void montarInstrucoesProgramaMultiplicacao(int multiplicando, int multiplicador){
		//0 => salvar na memória
		//1 => opcode => somar
		//2 => opcode => subtrair
		//-1 => halt
		
		memoriaInstrucoes= new int[multiplicador+3][4];
		//matriz de mutiplicador + 3 linhas e 4 colunas
		//pq 4 colunas? resp = col 1=>opcode; col 2=>add1; col 3=>add2; col 4=>add3
		//Ex. 35 x 18 = 35 + 35 + 35 + ... + 35 } 18 vezes
		//para 35 x 18 tem q ter no mínimo 18 instruções do tipo SOMA
		//EXEMPLO DE INSTRUÇÃO: -1 | -1 | -1 | -1 
		//outro exemplo de instrução: 2 | 26 | 32 | 12
		
		// 2^3 = 2 x 2 x 2 
		
		//PA, PG, FIB, EXP

		
		int[] umaInstrucao=null;		
		umaInstrucao = new int[4];
		umaInstrucao[0] =0; //opcode igual a 0 => levar para RAM
		umaInstrucao[1] = multiplicando; // valor a ser levado para RAM => multiplicando
		umaInstrucao[2] = 0; // o end onde vai ser armazenado o valor levado para RAM
		umaInstrucao[3] = -1; // o end que não serve para nada		
		memoriaInstrucoes[0] = umaInstrucao; // representa a 1a instrução do programa
		
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =0; // opcode igual a 0 => levar para RAM
		umaInstrucao[1] = 0; // valor a ser levado para RAM
		umaInstrucao[2] = 1; // posição da RAM onde vai ser armazenado o valor
		umaInstrucao[3] = -1; // end que nao serve para nada - LIXO		
		memoriaInstrucoes[1] = umaInstrucao; // representa 2a instrução do programa
		
		//RAM[0] = multiplicando
		//RAM[1] = 0
		
		//RAM[1] = 35
		//RAM[1] = 70
		//RAM[1] = 105
		//etc... 18x
		
		for(int i=0; i<multiplicador;i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =1; // OPCODE IGUAL A 1 => SOMAR
			umaInstrucao[1] = 0; // end1 igual a 0 => RAM[0]
			umaInstrucao[2] = 1; // end2 igual a 1 => RAM[1]
			umaInstrucao[3] = 1; // end3 igual a 1 => RAM[1]
			memoriaInstrucoes[i+2] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que nao faz nada que presta
		umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;
		
		memoriaInstrucoes[multiplicador+2] = umaInstrucao;
		
		//para 18 x 35 memoria de instrucao vai ter 21 linhas
		
	}

}
