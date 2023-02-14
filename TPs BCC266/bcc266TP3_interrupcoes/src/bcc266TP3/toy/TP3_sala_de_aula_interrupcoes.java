package bcc266TP3.toy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TP3_sala_de_aula_interrupcoes {
	
		final int tamanhoHD= 10000; // 40k words
		final int tamanhoRam=1000;// quantidade de blocos => 4k palavras
		final int tamanhoCache1 = 32; // quantidade de blocos => 32 palavras
		final int tamanhoCache2 = 64; // quantidade de blocos => 64 palavras
		final int tamanhoPrograma = 10001;//qde de instruções
		final int tamanhoProgramaTI = 101;//qde de instruções tratador de interrupções
		final int qdePalavrasBloco = 4;
		
		Instrucao[] memoriaInstrucoes;
		BlocoMemoria[] RAM = new BlocoMemoria[tamanhoRam];
		BlocoMemoria[] cache1 = new BlocoMemoria[tamanhoCache1];
		BlocoMemoria[] cache2 = new BlocoMemoria[tamanhoCache2];
		
		List<String> pilhaInterrupções = new LinkedList<String>();
		
		int custo=0;
		//caches 1 e 2 Hit e miss
		int missC1 = 0;
		int hitC1 = 0;
		int missC2 = 0;
		int hitC2 = 0;
			
		Random random = new Random();
				
		public static void main(String[] args){
			new TP3_sala_de_aula_interrupcoes();
		}
		
		public TP3_sala_de_aula_interrupcoes(){
			montarRam();
			montarCacheVazia(tamanhoCache1, cache1);
			montarCacheVazia(tamanhoCache2, cache2);
			montarInstrucaoGerador("programa", tamanhoPrograma);
			
			maquina(0, "programa");
			
			System.out.println("terminou");
		}
		
		

		void maquina(int PC, String programa){
						
			int opcode = Integer.MAX_VALUE;
			Random r = new Random();
						
			while(opcode!=-1){
				Instrucao umaInstrucao = memoriaInstrucoes[PC];
				opcode = umaInstrucao.getOpcode();
								
				//TP3
				if(opcode!=-1){
					BlocoMemoria dadoMemoriaAdd1 = MMU.buscarNasMemorias(umaInstrucao.getAdd1(), RAM, cache1, cache2);
					BlocoMemoria dadoMemoriaAdd2 = MMU.buscarNasMemorias(umaInstrucao.getAdd2(), RAM, cache1, cache2);
					BlocoMemoria dadoMemoriaAdd3 = MMU.buscarNasMemorias(umaInstrucao.getAdd3(), RAM, cache1, cache2);
					
					//incrementando custos
					custo += dadoMemoriaAdd1.getCusto();
					custo += dadoMemoriaAdd2.getCusto();
					custo += dadoMemoriaAdd3.getCusto();
					
					//validando hits e misses
					if(dadoMemoriaAdd1.getCacheHit()==1){
						hitC1++;
					}else if(dadoMemoriaAdd1.getCacheHit()==2){
						missC1++;
						hitC2++;
					}else if(dadoMemoriaAdd1.getCacheHit()==3){
						missC1++;
						missC2++;					
					}
					if(dadoMemoriaAdd2.getCacheHit()==1){
						hitC1++;
					}else if(dadoMemoriaAdd2.getCacheHit()==2){
						missC1++;
						hitC2++;
					}else if(dadoMemoriaAdd2.getCacheHit()==3){
						missC1++;
						missC2++;					
					}
					if(dadoMemoriaAdd3.getCacheHit()==1){
						hitC1++;
					}else if(dadoMemoriaAdd3.getCacheHit()==2){
						missC1++;
						hitC2++;
					}else if(dadoMemoriaAdd3.getCacheHit()==3){
						missC1++;
						missC2++;					
					}
					
					System.out.println("Custo até o momento do programa em execução: " + custo);
					System.out.println("Hits e Misses até o momento - C1 hit | C1 miss | C2 hit | C2 miss: " + hitC1+ " | "+ missC1 + " | "+hitC2 + " | "+ missC2);
					
					switch (opcode){
						//levar para cache1 dados externos
						case 0:{
							System.out.println("Levando dados para cache e gerando uma interrupção para isto !!! ");
							//gerar 1 ou n interrupções no barramento
							//salvar na pilha de interrupcoes
							
							if(programa.equals("programa")){
								int seed = r.nextInt(3);
								for (int i=0; i<seed; i++)
									pilhaInterrupções.add("Interrupcao a ser tratada - dispositivo teclado");
							}											
							break;
						}
						case 1:{
							//somar
							int conteudo1 = dadoMemoriaAdd1.getPalavras()[umaInstrucao.getAdd1().getEndPalavra()];
							int conteudo2 = dadoMemoriaAdd2.getPalavras()[umaInstrucao.getAdd2().getEndPalavra()];
							int soma = conteudo1+conteudo2;
							
							//salvando resultado na cache1
							dadoMemoriaAdd3.getPalavras()[umaInstrucao.getAdd3().getEndPalavra()] = soma;
							
							dadoMemoriaAdd3.setAtualizado();
							
							System.out.println("somando "+ soma);
							break;
						}
						case 2:{
							//subtrair
							int conteudo1 = dadoMemoriaAdd1.getPalavras()[umaInstrucao.getAdd1().getEndPalavra()];
							int conteudo2 = dadoMemoriaAdd2.getPalavras()[umaInstrucao.getAdd2().getEndPalavra()];
							int sub = conteudo1-conteudo2;
							
							//salvando resultado na cache1
							dadoMemoriaAdd3.getPalavras()[umaInstrucao.getAdd3().getEndPalavra()] = sub;
							
							dadoMemoriaAdd3.setAtualizado();
							
							System.out.println("subtraindo "+ sub);
							
							break;
						}
					}
					
					PC++;
				}//end if
				
				//retiram da pilha uma interrupção
				if(programa.equals("programa") && !pilhaInterrupções.isEmpty()){
					pilhaInterrupções.remove(0);//removendo uma interrupção para ser tratada
					System.out.println();
					System.out.println("INICIO - TRATADOR DE INTERRUPCAO");
					montarInstrucaoGerador("tratador_interrupcao", tamanhoProgramaTI);
					maquina(0, "tratador_interrupcao");
					System.out.println("FIM - TRATADOR DE INTERRUPCAO");
					System.out.println();
					
					montarInstrucaoGerador("programa", tamanhoPrograma);
				}
								
			}//end while
			
			System.out.println("Custo total do programa: " + custo);
			System.out.println("Hits e Misses do programa - C1 hit | C1 miss | C2 hit | C2 miss: " + hitC1+ " | "+ missC1 + " | "+hitC2 + " | "+ missC2);
			System.out.println("Interrupcoes ainda não tratadas: " + pilhaInterrupções.size());
			
		}
		
				
		private void montarCacheVazia(int tamanho, BlocoMemoria[] qqCache){
			for(int i=0; i<tamanho; i++){
				BlocoMemoria aux = new BlocoMemoria();
				//forçando cache estar vazia
				aux.setEndBloco(Integer.MIN_VALUE);
				
				qqCache[i] = aux;
				
			}
			
		}
		
		private void montarRam(){
						
			Random r = new Random();
			for(int i=0; i<tamanhoRam; i++){
				BlocoMemoria aux = new BlocoMemoria();
				aux.setEndBloco(i);
				int[] palavras = new int[qdePalavrasBloco];
				for(int j=0;j<qdePalavrasBloco; j++){
					palavras[j] = r.nextInt(1000000);
				}
				aux.setPalavras(palavras);
				RAM[i] = aux;	
				
			}			
		}
		
		private void montarInstrucaoGerador(String nome, int tamanho) {
			
			memoriaInstrucoes=null;
			memoriaInstrucoes= new Instrucao[tamanho];
						
			//ler do arquivo uma linha
			try{
				File f = new File (nome+".txt");
				FileReader fis = new FileReader(f);
				BufferedReader br = new BufferedReader(fis);
				
				String linha = null;
				Instrucao umaInstrucao = null;
				int index=0;
				
				while ((linha = br.readLine())!=null){
					String [] palavras = linha.split(":");
					umaInstrucao = new Instrucao();
					
					umaInstrucao.setOpcode(Integer.parseInt(palavras[0]));
					
					Endereco e1 = new Endereco();
					int e1_endBloco = Integer.parseInt(palavras[1]);
					int e1_endPalavra = Integer.parseInt(palavras[2]);
					e1_endPalavra = e1_endPalavra%4; //mod 4
					e1.setEndBloco(e1_endBloco);
					e1.setEndPalavra(e1_endPalavra);
					umaInstrucao.setAdd1(e1);
					
					Endereco e2 = new Endereco();
					int e2_endBloco = Integer.parseInt(palavras[3]);
					int e2_endPalavra = Integer.parseInt(palavras[4]);
					e2_endPalavra = e2_endPalavra%4; //mod 4
					e2.setEndBloco(e2_endBloco);
					e2.setEndPalavra(e2_endPalavra);
					umaInstrucao.setAdd2(e2);
					
					Endereco e3 = new Endereco();
					int e3_endBloco = Integer.parseInt(palavras[5]);
					int e3_endPalavra = Integer.parseInt(palavras[6]);
					e3_endPalavra = e3_endPalavra%4; //mod 4
					e3.setEndBloco(e3_endBloco);
					e3.setEndPalavra(e3_endPalavra);
					umaInstrucao.setAdd3(e3);
					
					memoriaInstrucoes[index] = umaInstrucao;
					index++;
				}
				fis.close();
				br.close();
				
				//inserindo a ultima instrucao do programa que faz o halt
				umaInstrucao = new Instrucao();
				umaInstrucao.setOpcode(-1);
							
				memoriaInstrucoes[tamanho-1] = umaInstrucao;
				
			}catch (Exception e){
				e.printStackTrace();
			}
			
		}		

}
