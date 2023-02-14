package bcc266TP2.toy;

public class Instrucao {
	Endereco add1;
	Endereco add2;
	Endereco add3;
	int opcode;
	
	public Endereco getAdd1() {
		return add1;
	}
	public void setAdd1(Endereco add1Aux) {
		add1 = add1Aux;
	}
	public Endereco getAdd2() {
		return add2;
	}
	public void setAdd2(Endereco add2Aux) {
		add2 = add2Aux;
	}
	public Endereco getAdd3() {
		return add3;
	}
	public void setAdd3(Endereco add3Aux) {
		add3 = add3Aux;
	}
	public int getOpcode() {
		return opcode;
	}
	public void setOpcode(int opcodeAux) {
		opcode = opcodeAux;
	}
	

}
