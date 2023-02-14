
public class MainFuckVector {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new MainFuckVector();
	}
	
	public MainFuckVector(){
		MyFuckVector<Integer> vet1 = new MyFuckVector<Integer>();
		vet1.initialize(1087654390);
		for(int i=0; i<1087654390; i++)
			vet1.put(i, i);
		
		for(int i=0;i<1087654390;i++)
			System.out.println("pos "+ i + " content: " + vet1.get(i));
	}

}
