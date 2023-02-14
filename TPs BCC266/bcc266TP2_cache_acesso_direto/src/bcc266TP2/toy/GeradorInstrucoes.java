package bcc266TP2.toy;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Random;

public class GeradorInstrucoes {

	int QTD_INS = 10000; // Quantidade de instruções geradas
	int TAM_FOR = 3; // Tamanho do For i1, i2, i3 ..i10
	int TAM_MEM = 1000; // Tamanho da Memoria (Quantidade de Endereços
									// Possiveis)
	int PROB_FOR = 95; // Probabilidade de Ocorrencia do For (%)
	
	public GeradorInstrucoes(){
		int aleatorio;
		// Define seu separador (entre os parametros da sua instrução
		String separador = ":";
		Random r = new Random();
		ArrayList<String> ins = new ArrayList<>();

		// N é a quantidade de Opcodes disponiveis
		int N = 3;
		// instruc é o vetor que conta quantos endereços tem cada Opcode
		//int instruc[] = { 1, 1, 1, 1, 0, 3, 4, 5 };// Instruções numeradas de 0
		int instruc[] = { 6, 6, 6, 6, 6, 6, 6, 6 };											// a N-1

		// Gerando o for (laço de repetição) do programa;
		ArrayList<String> repeticao = new ArrayList<>();
		for (int i = 0; i < TAM_FOR; i++) {
			aleatorio = r.nextInt(N);
			String s = "";
			for (int j = 0; j < instruc[aleatorio]; j++) {
				s += separador + r.nextInt(TAM_MEM);
			}
			repeticao.add(aleatorio + s);
		}

		for (int i = 0; i < QTD_INS;) {
			aleatorio = r.nextInt(100) + 1;
			if (aleatorio <= PROB_FOR && i + TAM_FOR < QTD_INS) {
				i = i + TAM_FOR;
				ins.addAll(repeticao);
			} else {
				i++;
				aleatorio = r.nextInt(N);
				String s = "";
				for (int j = 0; j < instruc[aleatorio]; j++) {
					s += separador + r.nextInt(TAM_MEM);
				}
				ins.add(aleatorio + s);
			}
		}
		try{
			OutputStream os = new FileOutputStream("programa.txt");
			OutputStreamWriter osw = new OutputStreamWriter(os);
			BufferedWriter bw = new BufferedWriter(osw);
			for (int i = 0; i < ins.size(); i++) {
				bw.write(ins.get(i));
				bw.newLine();
			}
			bw.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) throws IOException {
		new GeradorInstrucoes();
	}
}
