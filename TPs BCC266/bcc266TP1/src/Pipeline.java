
public class Pipeline {
	
	public static Instruction load(Instruction[] instructions, int PC){
		if(PC>=0)
			return instructions[PC];
		else return null;
	}
	
	public static int decode(Instruction oneInstruction){
		if(oneInstruction!=null) return oneInstruction.getOpCode();
		else return -1;
	}
	
	public static void execute(DataMemory memory, int opcode, Instruction oneInst){
		switch (opcode){
			case -1: {
				//imprimir a memória de dados indica que o código acabou
				memory.print();
				break;
			}
			//SUM
			case 0: {
				int[] MAR = memory.getData(oneInst.getAddressOne());
				int[] MAR1 = memory.getData(oneInst.getAddressTwo());
				//efetua a soma
				int[] SUM = new int[MAR.length];
				for(int i=0; i<SUM.length;i++){
					SUM[i] = MAR[i]+MAR1[i];
				}
				
				//insere na memória
				memory.putData(SUM, oneInst.getAddressThree());
				System.out.println("somou: " + SUM);
				break;
			}
			
			//SUB
			case 1: {
				int[] MAR = memory.getData(oneInst.getAddressOne());
				int[] MAR1 = memory.getData(oneInst.getAddressTwo());
				//efetua a soma
				int[] SUB = new int[MAR.length];
				for(int i=0; i<SUB.length;i++){
					SUB[i] = MAR[i]-MAR1[i];
				}
				
				//insere na memória
				memory.putData(SUB, oneInst.getAddressThree());
				System.out.println("sub: " + SUB);
				break;
			}
			
			//inserindo um dado na memoria
			case 2: {
				int[] PUT = new int[1];
				PUT[0] = oneInst.getAddressOne();
				memory.putData(PUT, oneInst.getAddressTwo());
			}
		}
	}

}
