
public final class Instruction {
		
	private int opCode;
	private int addressOne;
	private int addressTwo;
	private int addressThree;
	
	public void createInstruction(int opCodeAux, int addressOneAux, int addressTwoAux, int addressThreeAux){
		opCode = opCodeAux;
		addressOne = addressOneAux;
		addressTwo = addressTwoAux;
		addressThree = addressThreeAux;
	}

	public int getOpCode() {
		return opCode;
	}

	public int getAddressOne() {
		return addressOne;
	}

	public int getAddressTwo() {
		return addressTwo;
	}

	public int getAddressThree() {
		return addressThree;
	}
	
	

}
