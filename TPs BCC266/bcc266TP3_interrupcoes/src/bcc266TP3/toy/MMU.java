package bcc266TP3.toy;

public class MMU {
	
	
	public static BlocoMemoria buscarNasMemorias(Endereco e, BlocoMemoria[] RAM, BlocoMemoria[] cache1, BlocoMemoria[] cache2){
		
		//mapeamento direto a memoria
		int posicaoCache1 = e.getEndBloco()%cache1.length;
		int posicaoCache2 = e.getEndBloco()%cache2.length;
		int custo =0;
		
		if(cache1[posicaoCache1].getEndBloco()==e.getEndBloco()){
			custo+=10;
			cache1[posicaoCache1].setCusto(custo);
			cache1[posicaoCache1].setCacheHit(1);
			//cache1[posicaoCache1].setAtualizado();
			return cache1[posicaoCache1];
		}else{
			
			if(cache2[posicaoCache2].getEndBloco()==e.getEndBloco()){	
				custo +=110;
				cache2[posicaoCache2].setCacheHit(2);
				//cache2[posicaoCache2].setAtualizado();
				return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);
			}else{
				//buscar na ram E O REPASSAR A C2 E DEPOIS A C1
				custo +=1110;
				if(!cache2[posicaoCache2].isAtualizado()){
					cache2[posicaoCache2] = RAM[e.getEndBloco()];
					cache2[posicaoCache2].setCacheHit(3);
					return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);
					
				}else{
					RAM[cache2[posicaoCache2].getEndBloco()] = cache2[posicaoCache2];
					RAM[cache2[posicaoCache2].getEndBloco()].setDesatualizado();//virar false
					
					cache2[posicaoCache2] = RAM[e.getEndBloco()];
					cache2[posicaoCache2].setCacheHit(3);
					return testaCache1Cache2(posicaoCache1, posicaoCache2, cache1, cache2, custo);
				}
				
			}			
		}	
		
	}
	
	private static BlocoMemoria testaCache1Cache2(int posicaoCache1, int posicaoCache2, BlocoMemoria[] cache1, BlocoMemoria[] cache2, int custo){
		/*if(!cache1[posicaoCache1].isAtualizado()){
			cache1[posicaoCache1] = cache2[posicaoCache2];	
			//cache2[posicaoCache2] = //bloco vazio
		}else{
			BlocoMemoria aux = cache1[posicaoCache1];	
			cache1[posicaoCache1] = cache2[posicaoCache2];
			cache2[posicaoCache2] = aux;								
		}*/
		
		BlocoMemoria aux = cache1[posicaoCache1];	
		cache1[posicaoCache1] = cache2[posicaoCache2];
		cache2[posicaoCache2] = aux;
		
		cache1[posicaoCache1].setCusto(custo);
		
		return cache1[posicaoCache1]; 
	}	

}
