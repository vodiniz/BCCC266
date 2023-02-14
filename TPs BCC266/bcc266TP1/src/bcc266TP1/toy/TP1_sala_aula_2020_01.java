package bcc266TP1.toy;

import java.util.Random;

public class TP1_sala_aula_2020_01 {
	int[][] memoriaInstrucoes;
	int[] memoriaDados = new int[1000];
		
	public static void main(String[] args){
		new TP1_sala_aula_2020_01();
	}
	
	public TP1_sala_aula_2020_01(){
		
		montarRam();
		
		//montarInstrucoesProgramaAleatorio();
		
		//montarInstrucoesProgramaMultiplicacao(138, 124);
		//System.out.println("Resultado multiplicação: " + memoriaDados[1]);
		
		montarInstrucoesProgramaDivisao(347, 8);		
		System.out.println("Resultado divisão: " + memoriaDados[3]);
		
	}
	
		
	void maquinaInterpretada(int[] umaInstrucao){
		int opcode = umaInstrucao[0];
		switch (opcode){
			//SOMAR
			case 0:{
				
				int end1 = umaInstrucao[1];
				int end2 = umaInstrucao[2];
				//buscar na RAM
				int conteudoRam1 = memoriaDados[end1];
				int conteudoRam2 = memoriaDados[end2];
				int soma = conteudoRam1+conteudoRam2;
				//salvando resultado na RAM
				int end3 = umaInstrucao[3];
				memoriaDados[end3] = soma;
				
				System.out.println("somando " + conteudoRam1 + " com " + conteudoRam2 + " e gerando " + soma);
				break;
			}
			
			case 1:{
				//subtrair
				int end1 = umaInstrucao[1];
				int end2 = umaInstrucao[2];
				//buscar na RAM
				int conteudoRam1 = memoriaDados[end1]; //tp2
				int conteudoRam2 = memoriaDados[end2]; //tp2
				int sub = conteudoRam1-conteudoRam2;
				//salvando resultado na RAM
				int end3 = umaInstrucao[3];
				memoriaDados[end3] = sub; //tp2
				System.out.println("subtraindo " + conteudoRam1 + " com " + conteudoRam2 + " e gerando " + sub);
				
				break;
			}
			//levar para memoriaDados
			case 2:{
				
				int content = umaInstrucao[1];
				int end = umaInstrucao[2];
				memoriaDados[end]=content; 
				break;
			}
			//trazer da memoriaDados
			case 3:{
				umaInstrucao[1]=memoriaDados[umaInstrucao[2]]; 
				break;
			}
			
		}
	}
	
	public int[][] compilar(int[][] memoriaInstrucoes){
		//aqui teria o q o GCC faz, que demorou décadas para ser feito eficientemente
		return memoriaInstrucoes;
	}
	
	void maquina(){
	
		int[][] memoriaInstrucoesCompiladas = compilar(memoriaInstrucoes);
						
		int PC =0;
		int opcode = Integer.MAX_VALUE;//maior inteiro possível 2^31 -1 =~ 2bi
		while(opcode!=-1){ 
			int[] umaInstrucao = memoriaInstrucoesCompiladas[PC];
			
			opcode = umaInstrucao[0];
			
			maquinaInterpretada(umaInstrucao);
			
			PC++;
		}
		
	}

	void montarRam(){
		Random r = new Random();
		for(int i=0; i<1000; i++){
			memoriaDados[i] = r.nextInt(100);
		}		
	}
	
	void montarInstrucoesProgramaAleatorio(){
		//01|22|13|45 => isto é uma instrução
		//00|33|12|01 => isto é outra instrução
		//00/15/02/222
		//01/10/13/22
		//02/-199/10/null
		//03/null/10/null
		//04/16/28/null
		
		
		//01 => opcode => o q a instrução deve fazer
		//01 => somar, subtrair, atribuir, levar para a memória, etc...
		
		//22 => endereço de memória
		//22 => conteúdo ou um valor
		
		//13 => endereço de memória ou conteúdo
		//45 => endereço de memória ou conteúdo
		
		//0 => opcode => somar
		//1 => opcode => subtrair
		//-1 => halt -> parar
		//2 => levar para memória
		//3 => retirar da memória
		//4 => atribuição
		
		memoriaInstrucoes= new int[100][4];
		Random r = new Random();
		for (int i=0; i<99; i++){
			int[] umaInstrucao = new int[4];
			umaInstrucao[0] = r.nextInt(2);
			umaInstrucao[1] = r.nextInt(1000);
			umaInstrucao[2] = r.nextInt(1000);
			umaInstrucao[3] = r.nextInt(1000);
			memoriaInstrucoes[i] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que é o HALT -> parar
		int[] umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;
		
		memoriaInstrucoes[99] = umaInstrucao;
		
		maquina();
	}
	
	//estou escrevendo multiplicacao numa lingua chamada CAVE LANGUAGE
	void montarInstrucoesProgramaMultiplicacao(int multiplicando, int multiplicador){
		//0 => somar
		//1 => sub
		//2 => levar para memoriaDados
		//3 => trazer da memoriaDados
		//-1 => halt
		
		// 3 x 400 = 3 + 3 + 3 + 3 + .... + 3 => 400 vezes
		//opcode | add1 | add2 | add3
		
		memoriaInstrucoes= new int[multiplicador+3][4];
		
		int[] umaInstrucao= new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = multiplicando;
		umaInstrucao[2] = 0;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[0] = umaInstrucao;
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = 0;
		umaInstrucao[2] = 1;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[1] = umaInstrucao;
		
		for(int i=0; i<multiplicador;i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =0;
			umaInstrucao[1] = 0;
			umaInstrucao[2] = 1;
			umaInstrucao[3] = 1;
			memoriaInstrucoes[i+2] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que faz o HALT
		umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;
		
		memoriaInstrucoes[multiplicador+2] = umaInstrucao;
		
		maquina();
	}
	
	void montarInstrucoesProgramaDivisao(int dividendo, int divisor){
		//0 => somar
		//1 => sub
		//2 => levar para memoriaDados
		//3 => trazer da memoriaDados
		//-1 => halt
		
		// 12 / 3 = (12-3); (9-3); (6-3); (3-3); (0-3) => 4
		// 15 / 4 = (15-4); (11-4); (7-4); (3-4) => 3
		
		//monto um programa apenas para levar os dados para RAM
		memoriaInstrucoes= new int[5][4];
		
		int[] umaInstrucao=null;		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = divisor;
		umaInstrucao[2] = 0;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[0] = umaInstrucao;
		//memoriaDados[0] = divisor
		
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = dividendo;
		umaInstrucao[2] = 1;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[1] = umaInstrucao;
		//memoriaDados[1] = dividendo
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = 1;
		umaInstrucao[2] = 2;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[2] = umaInstrucao;
		//memoriaDados[2] = 1
		//representa uma variável de incremento
				
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = 0;
		umaInstrucao[2] = 3;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[3] = umaInstrucao;
		//memoriaDados[3] = 0
		//representa quantas subtrações foram feitas
		//representa o resultado da divisão
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =-1;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = -1;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[4] = umaInstrucao;
		
		maquina();
		
		
		//trazer da memoriaDados[0]
		umaInstrucao = new int[4];
		umaInstrucao[0] =3;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = 10;
		umaInstrucao[3] = -1;	
		maquinaInterpretada(umaInstrucao);
		int ram0 = umaInstrucao[1];
		
		
		//trazer da memoriaDados[1]
		umaInstrucao = new int[4];
		umaInstrucao[0] =3;
		umaInstrucao[1] = -1;
		umaInstrucao[2] = 1;
		umaInstrucao[3] = -1;		
		maquinaInterpretada(umaInstrucao);
		int ram1 = umaInstrucao[1];
		
		// 12 / 3 = (12-3); (9-3); (6-3); (3-3); (0-3) => 4
		// 15 / 4 = (15-4); (11-4); (7-4); (3-4) => 3
		
		while(ram1>=ram0){
			//subtrair
			umaInstrucao = new int[4];
			umaInstrucao[0] =1;
			umaInstrucao[1] = 1;
			umaInstrucao[2] = 0;
			umaInstrucao[3] = 1;		
			maquinaInterpretada(umaInstrucao);
			
			//somar
			umaInstrucao = new int[4];
			umaInstrucao[0] =0;
			umaInstrucao[1] = 2;
			umaInstrucao[2] = 3;
			umaInstrucao[3] = 3;		
			maquinaInterpretada(umaInstrucao);
			
			//trazer da memoriaDados[0]
			umaInstrucao = new int[4];
			umaInstrucao[0] =3;
			umaInstrucao[1] = -1;
			umaInstrucao[2] = 0;
			umaInstrucao[3] = -1;		
			maquinaInterpretada(umaInstrucao);
			ram0 = umaInstrucao[1];
			
			//trazer da memoriaDados[1]
			umaInstrucao = new int[4];
			umaInstrucao[0] =3;
			umaInstrucao[1] = -1;
			umaInstrucao[2] = 1;
			umaInstrucao[3] = -1;		
			maquinaInterpretada(umaInstrucao);
			ram1 = umaInstrucao[1];
		}
				
	}

}
