package bcc266TP3.toy;

public class BlocoMemoria {
	
	//BlocoMemoria.palavras[0] => um dado da ram
	
	 int[] palavras;
	 int endBloco;
	 boolean atualizado;
	 int custo;
	 int cacheHit;
	
	public BlocoMemoria(){
		endBloco = -1;
		atualizado = false;
		custo = 0;
		setCacheHit(0);
	}
	
	public int getCusto() {
		return custo;
	}

	public void setCusto(int custoAux) {
		custo = custoAux;
	}
	
	public int[] getPalavras() {
		return palavras;
	}

	public void setPalavras(int[] palavrasAux) {
		palavras = palavrasAux;
	}

	public int getEndBloco() {
		return endBloco;
	}

	public void setEndBloco(int endBlocoAux) {
		endBloco = endBlocoAux;
	}

	public boolean isAtualizado() {
		return atualizado;
	}

	public void setAtualizado() {
		atualizado = true;
	}
	
	public void setDesatualizado() {
		atualizado = false;
	}

	public int getCacheHit() {
		return cacheHit;
	}

	public void setCacheHit(int cacheHitAux) {
		cacheHit = cacheHitAux;
	}	
}
