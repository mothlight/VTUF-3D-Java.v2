package VTUF3D.Utilities;


public class TestVariousCommonUtils
{
	
	Common common = new Common();

	public static void main(String[] args)
	{
		TestVariousCommonUtils t = new TestVariousCommonUtils();
//		t.testPadLeft();
//		t.testPadLeft2();
		t.testRound();
	}
	
	public void testPadLeft()
	{
		long startTime = System.currentTimeMillis();
		int loops = 10000000;
		int numberPadCharacters = 10;
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		
		for (int i=0;i<loops;i++)
		{
			int hourPad = 0; 
			String hourPadStr = common.padLeftSlow(hourPad, numberPadCharacters, 'a');
//			System.out.println(hourPadStr);
		}
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		
		for (int i=0;i<loops;i++)
		{
			int hourPad2 = 0;
			String hourPadStr2 = common.padLeft(hourPad2, numberPadCharacters, 'a');
//			System.out.println(hourPadStr2);
		}

		
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
	}
	
	public void testPadLeft2()
	{
		long startTime = System.currentTimeMillis();
		int loops = 10000000;
		int numberPadCharacters = 2;
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		
		for (int i=0;i<loops;i++)
		{
			String hourPad = "0"; 
			String hourPadStr = common.padLeftSlow(hourPad, numberPadCharacters, 'a');
//			System.out.println(hourPadStr);
		}
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		
		for (int i=0;i<loops;i++)
		{
			String hourPad2 = "0";
			String hourPadStr2 = common.padLeft(hourPad2, numberPadCharacters, 'a');
//			System.out.println(hourPadStr2);
		}

		
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
	}
	
	public void testRound()
	{
		long startTime = System.currentTimeMillis();
		int loops = 1000000000;
		double d = 1.123456789;
		int c = 2;
		
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		for (int i=0;i<loops;i++)
		{
			d += 0.001001;
			double value = common.roundToDecimals(d, c);
//			System.out.println(value);
		}
		
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
		d = 1.123456789;
		for (int i=0;i<loops;i++)
		{
			d += 0.001001;
			double value2 = common.roundToDecimalsSlow(d, c);
//			System.out.println(value2);
		}
	
		
		
		System.out.println("++++++++++++++++++++++++start=" + (System.currentTimeMillis() - startTime)/1000. );	
	}
	
	

	
	


}
