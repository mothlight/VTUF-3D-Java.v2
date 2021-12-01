package VTUF3D.Utilities;

public class MulticoreTest
{

	public static void main(String[] args)
	{
		MulticoreTest t = new MulticoreTest();
		t.test();

	}
	
	public void test()
	{
		int availProc = Runtime.getRuntime().availableProcessors();
		System.out.println(availProc);
	}

}
