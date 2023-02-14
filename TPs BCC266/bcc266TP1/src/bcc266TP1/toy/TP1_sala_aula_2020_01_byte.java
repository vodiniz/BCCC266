package bcc266TP1.toy;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Random;

public class TP1_sala_aula_2020_01_byte {
	//byte = 8 bits
	// 1 byte = 2^8 = 256
	// (int) byte = 0 ... 255
	//[] byte => [] int => em cada posição vai variar de 0 a 255
	// pq 4 bytes? 4 x 8 = 32 bits = 2^32 =~ 4bi => -2bi a +2bi e o 0
	
	byte[][][] memoriaInstrucoes;
	byte[][] RAM = new byte[100][];
		
	//RAMvelha[0] = 39
	//RAM[0] = RAM[0][0];RAM[0][1];RAM[0][2];RAM[0][3]
	
	
	public static void main(String[] args){
		new TP1_sala_aula_2020_01_byte();
	}
	
	TP1_sala_aula_2020_01_byte(){
		
		// número 39 em binário
		//3 9 / 2 = 1 9  resta  1 
		//1 9 / 2 = 9  resta  1 
		//9 / 2 = 4  resta  1 
		//4 / 2 = 2  resta  0 
		//2 / 2 = 1  resta  0
		//1 / 2 = 0  resta 1 (critério de parada)
		//3 9 = 1 0 0 1 1 1
		
		// somar 39 + 39 = 78
		// 78
		//7 8 / 2 = 3 9  resta  0 
		//3 9 / 2 = 1 9  resta  1 
		//1 9 / 2 = 9  resta  1 
		//9 / 2 = 4  resta  1 
		//4 / 2 = 2  resta  0 
		//2 / 2 = 1  resta  0
		//1 / 2 = 0  resta 1 (critério de parada)
		//7 8 = 1 0 0 1 1 1 0
		
		//  1 0 0 1 1 1
		//+
		//  1 0 0 1 1 1
		//1 0 0 1 1 1 0
		
		montarRam();
		
		montarInstrucoesProgramaAleatorio();
		
		montarInstrucoesProgramaMultiplicacao(512, 1024);
		System.out.println("Resultado multiplicação: " + bytesToInt(RAM[1]));
		
		montarInstrucoesProgramaDivisao(30, 3);		
		System.out.println("Resultado divisão: " + bytesToInt(RAM[3]));
		
	}
	
		
	void maquinaInterpretada(byte[][] umaInstrucao){
		int opcode = bytesToInt(umaInstrucao[0]);
		switch (opcode){
			//levar para RAM
			case 0:{
				RAM[bytesToInt(umaInstrucao[2])] = umaInstrucao[1]; 
				break;
			}
			//trazer da RAM
			case 3:{
				umaInstrucao[1]=RAM[bytesToInt(umaInstrucao[2])]; 
				break;
			}
			case 1:{
				//somar
				int end1 = bytesToInt(umaInstrucao[1]);
				int end2 = bytesToInt(umaInstrucao[2]);
				//buscar na RAM
				byte[] conteudoRam1 = RAM[end1];
				byte[] conteudoRam2 = RAM[end2];
				int soma = bytesToInt(conteudoRam1)+bytesToInt(conteudoRam2);
				//salvando resultado na RAM
				int end3 = bytesToInt(umaInstrucao[3]);
				RAM[end3] = intToBytes(soma);
				break;
			}
			case 2:{
				//subtrair
				int end1 = bytesToInt(umaInstrucao[1]);
				int end2 = bytesToInt(umaInstrucao[2]);
				//buscar na RAM
				byte[] conteudoRam1 = RAM[end1];
				byte[] conteudoRam2 = RAM[end2];
				int sub = bytesToInt(conteudoRam1)-bytesToInt(conteudoRam2);
				//salvando resultado na RAM
				int end3 = bytesToInt(umaInstrucao[3]);
				RAM[end3] = intToBytes(sub);
				break;
			}
		}
	}
	
	void maquinaTraduzida(){
		//registradores
		int PC =0;
		int opcode = Integer.MAX_VALUE;//maior inteiro possível 2^31 -1 =~ 2bi
		while(opcode!=-1 && PC<memoriaInstrucoes.length){
			byte[][] umaInstrucao = memoriaInstrucoes[PC];
			
			maquinaInterpretada(umaInstrucao);
			
			PC++;
		}
		
	}
	
	byte[] intToBytes( final int i ) {
		//4 bytes = 32 bits = inteiro de -2bi a +2bi
	    ByteBuffer bb = ByteBuffer.allocate(4); 
	    bb.putInt(i); 
	    return bb.array();
	}
	
	int bytesToInt(final byte[] bytes){
		return new BigInteger(bytes).intValue();
	}

	void montarRam(){
		Random r = new Random();
		for(int i=0; i<100; i++){
			RAM[i] = intToBytes(r.nextInt(1000));
		}		
	}
	
	void montarInstrucoesProgramaAleatorio(){
		//01|22|13|45 => isto é uma instrução
		//02|33|12|01 => isto é outra instrução
				
		//0 => salvar na memória
		//3 => trazer da memória
		//1 => opcode => somar
		//2 => opcode => subtrair
		//-1 => halt
		
		memoriaInstrucoes= new byte[10][4][4];
		byte[][] umaInstrucao=null;
		Random r = new Random();
		for (int i=0; i<9; i++){
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] = intToBytes(r.nextInt(3));//0, 1, 2
			umaInstrucao[1] = intToBytes(r.nextInt(100)); //0 ... 99
			umaInstrucao[2] = intToBytes(r.nextInt(100)); //0 ... 99
			umaInstrucao[3] = intToBytes(r.nextInt(100)); //0 ... 99
			
			memoriaInstrucoes[i] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que nao faz nada que presta
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] = intToBytes(-1);
		umaInstrucao[1] = intToBytes(-1);
		umaInstrucao[2] = intToBytes(-1);
		umaInstrucao[3] = intToBytes(-1);
		
		memoriaInstrucoes[9] = umaInstrucao;
		
		maquinaTraduzida();
	}
	
	void montarInstrucoesProgramaMultiplicacao(int multiplicando, int multiplicador){
		//0 => salvar na memória
		//3 => trazer da memória
		//1 => opcode => somar
		//2 => opcode => subtrair
		//-1 => halt
		
		// 3 x 4 = 3 + 3 + 3 + 3 
		
		memoriaInstrucoes= new byte[multiplicador+3][4][4];
		
		byte[][] umaInstrucao=null;		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(multiplicando);
		umaInstrucao[2] = intToBytes(0);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[0] = umaInstrucao;//vetor de 4 posicoes
		//RAM[0] = 3
		
		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(0);
		umaInstrucao[2] = intToBytes(1);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[1] = umaInstrucao;
		//RAM[1] = 12
		
		for(int i=0; i<multiplicador;i++){
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] =intToBytes(1);
			umaInstrucao[1] = intToBytes(0);
			umaInstrucao[2] = intToBytes(1);
			umaInstrucao[3] = intToBytes(1);
			memoriaInstrucoes[i+2] = umaInstrucao;
		}
		
		//inserindo a ultima instrucao do programa que nao faz nada que presta
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(-1);
		umaInstrucao[1] = intToBytes(-1);
		umaInstrucao[2] = intToBytes(-1);
		umaInstrucao[3] = intToBytes(-1);
		
		memoriaInstrucoes[multiplicador+2] = umaInstrucao;
		
		maquinaTraduzida();
	}
	
	void montarInstrucoesProgramaDivisao(int dividendo, int divisor){
		//0 => salvar na memória
		//3 => trazer da memória
		//1 => opcode => somar
		//2 => opcode => subtrair
		//-1 => halt
		
		// 12 / 3 = (12-3); (9-3); (6-3); (3-3); (0-3) => 4
		// 15 / 4 = (15-4); (11-4); (7-4); (3-4) => 3
		
		//monto um programa apenas para levar os dados para RAM
		memoriaInstrucoes= new byte[5][4][4];
		
		byte[][] umaInstrucao=null;		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(divisor);
		umaInstrucao[2] = intToBytes(0);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[0] = umaInstrucao;//vetor de 4 posicoes
		//RAM[0] = divisor
		
		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(dividendo);
		umaInstrucao[2] = intToBytes(1);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[1] = umaInstrucao;
		//RAM[1] = dividendo
		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(1);
		umaInstrucao[2] = intToBytes(2);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[2] = umaInstrucao;
		//RAM[2] = 1
		//representa uma constante de incremento
				
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(0);
		umaInstrucao[1] = intToBytes(0);
		umaInstrucao[2] = intToBytes(3);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[3] = umaInstrucao;
		//RAM[3] = 0
		//representa quantas subtrações foram feitas
		//representa o resultado da divisão
		
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(-1);
		umaInstrucao[1] = intToBytes(-1);
		umaInstrucao[2] = intToBytes(-1);
		umaInstrucao[3] = intToBytes(-1);		
		memoriaInstrucoes[4] = umaInstrucao;
		
		maquinaTraduzida();
		
		//trazer da RAM[0]
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(3);
		umaInstrucao[1] = intToBytes(-1);
		umaInstrucao[2] = intToBytes(0);
		umaInstrucao[3] = intToBytes(-1);		
		maquinaInterpretada(umaInstrucao);
		int ram0 = bytesToInt(umaInstrucao[1]);
		
		//trazer da RAM[1]
		umaInstrucao = new byte[4][4];
		umaInstrucao[0] =intToBytes(3);
		umaInstrucao[1] = intToBytes(-1);
		umaInstrucao[2] = intToBytes(1);
		umaInstrucao[3] = intToBytes(-1);		
		maquinaInterpretada(umaInstrucao);
		int ram1 = bytesToInt(umaInstrucao[1]);
		
		while(ram1>=ram0){
			//subtrair
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] =intToBytes(2);
			umaInstrucao[1] = intToBytes(1);
			umaInstrucao[2] = intToBytes(0);
			umaInstrucao[3] = intToBytes(1);		
			maquinaInterpretada(umaInstrucao);
			
			//somar
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] =intToBytes(1);
			umaInstrucao[1] = intToBytes(2);
			umaInstrucao[2] = intToBytes(3);
			umaInstrucao[3] = intToBytes(3);		
			maquinaInterpretada(umaInstrucao);
			
			//trazer da RAM[0]
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] =intToBytes(3);
			umaInstrucao[1] = intToBytes(-1);
			umaInstrucao[2] = intToBytes(0);
			umaInstrucao[3] = intToBytes(-1);		
			maquinaInterpretada(umaInstrucao);
			ram0 = bytesToInt(umaInstrucao[1]);
			
			//trazer da RAM[1]
			umaInstrucao = new byte[4][4];
			umaInstrucao[0] =intToBytes(3);
			umaInstrucao[1] = intToBytes(-1);
			umaInstrucao[2] = intToBytes(1);
			umaInstrucao[3] = intToBytes(-1);		
			maquinaInterpretada(umaInstrucao);
			ram1 = bytesToInt(umaInstrucao[1]);
		}
				
	}

}
