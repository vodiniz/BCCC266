
public class MyFuckVector<Obj> {
	
	private Obj[][] smallVectors;
	
	@SuppressWarnings("unchecked")
	public void initialize (int numberOfItens){
		int rest = numberOfItens%1000;
		int div = numberOfItens/1000;
		
		if(rest==0){
			smallVectors = (Obj[][]) new Object[div][1000];
		}else{
			smallVectors = (Obj[][]) new Object[div+1][1000];
		}
	}
	
	public boolean put(Obj item, int position){
		try{
			int div = position/1000;
			int rest = position%1000;
			
			smallVectors[div][rest]= item;
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean remove(int position){
		return put(null, position);
	}
	
	public Obj get(int position){
		try{
			int div = position/1000;
			int rest = position%1000;
			
			return smallVectors[div][rest];
			
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
