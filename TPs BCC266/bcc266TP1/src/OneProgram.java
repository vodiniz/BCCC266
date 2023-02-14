import java.util.Random;

public class OneProgram {
	
	public static Instruction[] createOneProgramAleatory(int memorySize, int opCodeSize){
		Random r = new Random();
		//o programa a ser criado pode conter entre 1 e 1k instruções
		int numInstructions = r.nextInt(1000);
		Instruction[] allCode = new Instruction[numInstructions];
		
		for(int i=0; i<numInstructions-1;i++){
			Instruction inst = new Instruction();
			int addressOne = r.nextInt(memorySize);
			int addressTwo = r.nextInt(memorySize);
			int addressThree = r.nextInt(memorySize);
			int opCode = r.nextInt(opCodeSize);
			inst.createInstruction(opCode, addressOne, addressTwo, addressThree);
			allCode[i] = inst;
		}
		
		//garantindo que a ultima instrução tenha opcode igual a HALT
		Instruction inst = new Instruction();
		inst.createInstruction(-1, -1, -1, -1);
		allCode[numInstructions-1] = inst;
		
		return allCode;
		
	}
	
	// num1 e num2 sao os valores a serem multiplicados
	public static Instruction[] createOneProgramMultiply(int num1, int num2){
		
		Instruction[] allCode = new Instruction[num2+3];
		//inserindo num1 na memoria na posicao 0
		Instruction inst0 = new Instruction();
		inst0.createInstruction(2, num1, 0, -1);
		allCode[0] = inst0;
		
		//zerando a memoria na posicao 1
		Instruction inst1 = new Instruction();
		inst1.createInstruction(2, 0, 1, -1);
		allCode[1] = inst1;
		
		//fazendo somas sucessivas
		for(int i=0; i<num2;i++){
			Instruction inst = new Instruction();
			inst.createInstruction(0, 0, 1, 1);
			allCode[i+2] = inst;
		}		
		
		//garantindo que a ultima instrução tenha opcode igual a HALT
		Instruction instLast = new Instruction();
		instLast.createInstruction(-1, -1, -1, -1);
		allCode[num2+2] = instLast;
		
		return allCode;
		
	}
	
	
	public static Instruction[] createOneProgramDivide(int num1, int num2){
		return null;
	}

}
