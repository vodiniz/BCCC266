package bcc266TP1.toy;

import java.util.Random;

public class TP1_sala_de_aula {
	//pequena maquina que consome instruções
	
	int[][] memoriaInstrucoes;
	int[] RAM = new int[100];
	
	public static void main(String[] args){
		new TP1_sala_de_aula();
	}
	
	public TP1_sala_de_aula(){
		montarRam();
		//montarInstrucoesProgramaAleatorio();
		montarInstrucoesProgramaMultiplicacao(512, 1024);
		maquina();
		
		System.out.println("resultado da mult: " + RAM[1]);
	}
	
	void maquina(){
		//registradores
		int PC =0;
		int opcode = Integer.MAX_VALUE;
		while(opcode!=-1){
			int[] umaInstrucao = memoriaInstrucoes[PC];
			opcode = umaInstrucao[0];
			switch (opcode){
				//levar para RAM
				case 0:{
					RAM[umaInstrucao[2]] = umaInstrucao[1];
					break;
				}
				case 1:{
					//somar
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					//buscar na RAM
					int conteudoRam1 = RAM[end1];
					int conteudoRam2 = RAM[end2];
					int soma = conteudoRam1+conteudoRam2;
					//salvando resultado na RAM
					RAM[umaInstrucao[3]] = soma;
					
					System.out.println("somando "+ soma);
					break;
				}
				case 2:{
					//subtrair
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					//buscar na RAM
					int conteudoRam1 = RAM[end1];
					int conteudoRam2 = RAM[end2];
					int sub = conteudoRam1-conteudoRam2;
					//salvando resultado na RAM
					RAM[umaInstrucao[3]] = sub;
					
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
			RAM[i] = r.nextInt(1000);
		}
		
	}
	
	void montarInstrucoesProgramaAleatorio(){
		//01:22:13:45 => isto é uma instrução
		//00 => salvar na memória
		//01 => opcode => somar
		//02 => opcode => subtrair
		//-1 => halt
		
	    //22 => significa um endereço da RAM (10 endereço) 
		//13 => significa 2o endereço
		//45 => significa 3o endereco
		memoriaInstrucoes= new int[10][4];
		int[] umaInstrucao=null;
		Random r = new Random();
		for (int i=0; i<9; i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =r.nextInt(3);
			umaInstrucao[1] = r.nextInt(100);
			umaInstrucao[2] = r.nextInt(100);
			umaInstrucao[3] = r.nextInt(100);
			
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
		//00 => salvar na memória
		//01 => opcode => somar
		//02 => opcode => subtrair
		//-1 => halt
		
		memoriaInstrucoes= new int[multiplicador+3][4];
		
		int[] umaInstrucao=null;		
		umaInstrucao = new int[4];
		umaInstrucao[0] =0;
		umaInstrucao[1] = multiplicando;
		umaInstrucao[2] = 0;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[0] = umaInstrucao;
		
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =0;
		umaInstrucao[1] = 0;
		umaInstrucao[2] = 1;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[1] = umaInstrucao;
		
		for(int i=0; i<multiplicador;i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =1;
			umaInstrucao[1] = 0;
			umaInstrucao[2] = 1;
			umaInstrucao[3] = 1;
			memoriaInstrucoes[i+2] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que nao faz nada que presta
		umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;
		
		memoriaInstrucoes[multiplicador+2] = umaInstrucao;
		
	}

}
