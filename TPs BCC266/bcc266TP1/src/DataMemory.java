public class DataMemory {
	
	int[][] simpleMemory;
	int memorySize;
	
	public int getMemorySize() {
		return memorySize;
	}

	public void createMemory(int lines, int colunms){
		if(lines<=0 && colunms<0){
			System.out.println("cannot create the memory with data");
		}else{
			memorySize=lines;
			simpleMemory = new int[lines][colunms];
			//Random r = new Random();
						
			for(int i=0; i<lines;i++){
				simpleMemory[i] = new int[colunms];
				for(int j=0; j<colunms; j++){
					simpleMemory[i][j] = -1;//r.nextInt(1000000);
				}
			}
		}
		
	}
	
	public int[] getData (int address){
		if(address>simpleMemory.length) return null;
		else return simpleMemory[address];		
	}
	
	public void print(){
		for(int i=0; i<simpleMemory.length; i++){
			for(int j=0; j<simpleMemory[i].length; j++){
				System.out.print(simpleMemory[i][j]+ "|");
			}
			System.out.println();
		}
	}
	
	public void putData(int[] data, int address){
		if(address>=0) simpleMemory[address] = data;
	}	
	
}
