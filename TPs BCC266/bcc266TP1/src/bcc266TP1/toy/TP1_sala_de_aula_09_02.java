package bcc266TP1.toy;

import java.util.Random;

public class TP1_sala_de_aula_09_02 {
	int[] RAM = new int[100];
	int[][] memoriaInstrucoes; //aqui mora o programa que vai rodar na porcaria da minha maquina

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new TP1_sala_de_aula_09_02();
	}
	
	public TP1_sala_de_aula_09_02(){
		
		montarMemoriaDados();
		//montarMeuPrimeiroPrograma();
		montarProgramaMultiplicacao(77, 333);
		maquina();
		
		System.out.println("resultado " + RAM[1]);
		
		
	}

	
	void montarMemoriaDados() {
		// TODO Auto-generated method stub
		Random r = new Random();
		for(int i=0; i<100; i++){
			RAM[i] = r.nextInt(10000000);
		}
	}
	
	void montarMeuPrimeiroPrograma() {
		//01:22:13:45 => isto é uma instrução na lingua CAVE LANGUAGE
		//0 => opcode => somar
		//1 => opcode => subtrair
		//-1 => halt
		memoriaInstrucoes= new int[1000][4];		
		int[] umaInstrucao=null;
			
		Random r = new Random();
		for (int i=0; i<999; i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =r.nextInt(2);
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
		
		memoriaInstrucoes[999] = umaInstrucao;
		
	}
	
	public void montarProgramaMultiplicacao(int multiplicando, int multiplicador){
		//0 => opcode => somar
		//1 => opcode => subtrair
		//2 => salvar na memória
		//-1 => halt
		
		//ex: 333 x 48		
		memoriaInstrucoes= new int[multiplicador+3][4];
		
		int[] umaInstrucao=null;		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;//LEVAR PARA ram
		umaInstrucao[1] = multiplicando;
		umaInstrucao[2] = 0;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[0] = umaInstrucao;		
		//RAM[MULTIPLICANDO, LIXO1, LIXO2, LIXO...., LIXO99]
		
		
		umaInstrucao = new int[4];
		umaInstrucao[0] =2;
		umaInstrucao[1] = 0;
		umaInstrucao[2] = 1;
		umaInstrucao[3] = -1;		
		memoriaInstrucoes[1] = umaInstrucao;
		//RAM[MULTIPLICANDO, 0, LIXO1, LIXO2...., LIXO99]
		
		for(int i=0; i<multiplicador;i++){
			umaInstrucao = new int[4];
			umaInstrucao[0] =0;
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
					//somar
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					int end3 = umaInstrucao[3];
					//buscar na RAM
					int conteudoRam1 = RAM[end1];
					int conteudoRam2 = RAM[end2];
					int soma = conteudoRam1+conteudoRam2;
					//salvando resultado na RAM
					RAM[end3] = soma;
					
					System.out.println("somando "+ soma);
					break;
				}
				case 1:{
					//subtrair
					int end1 = umaInstrucao[1];
					int end2 = umaInstrucao[2];
					int end3 = umaInstrucao[3];
					//buscar na RAM
					int conteudoRam1 = RAM[end1];
					int conteudoRam2 = RAM[end2];
					int sub = conteudoRam1-conteudoRam2;
					//salvando resultado na RAM
					RAM[end3] = sub;
					
					System.out.println("subtraindo "+ sub);
					
					break;
				}
				case 2:{
					RAM[umaInstrucao[2]] = umaInstrucao[1];
					//levando para a RAM no endereço umainstrucao[2]
					//o valor umaInstrucao[1]
					break;
				}
				
			}
			
			PC++;
		}
		
	}
	
	
}
