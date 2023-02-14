
public class CalculatorMachine {
	public static void main(String[] args) {		
		new CalculatorMachine();
	}
	
	public CalculatorMachine(){
		int opcode=0;
		int PC = 0;
		
		DataMemory memory = new DataMemory();
		memory.createMemory(1000, 1);
		
		
		//cria apenas instruções com opcode entre 0 e o valor almejado
		//note q o opcode para terminar é sempre -1
		//Instruction[] allCode = OneProgram.createOneProgramAleatory(1000, 2);
		
		//fazendo multiplicacao
		Instruction[] allCode = OneProgram.createOneProgramMultiply(99, 900);
		
		while(opcode!=-1){
			Instruction oneInst = Pipeline.load(allCode, PC);
			
			opcode = Pipeline.decode(oneInst);
			
			Pipeline.execute(memory, opcode, oneInst);
			
			PC++;
		}
		
	}	

}
