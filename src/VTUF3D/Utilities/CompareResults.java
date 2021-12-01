package VTUF3D.Utilities;

import java.util.ArrayList;

public class CompareResults
{
	Common common = new Common();

	public static void main(String[] args)
	{
		CompareResults compare = new CompareResults();
//		compare.compare1();
		compare.compare2();
//		compare.compare3();

	}
	
	public void compare1()
	{
		String file = "EnergyBalance_Facets.out";
		
		String fortranDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/fortran/";
		String javaDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/java/";
		ArrayList<String> fortranFileArray = common.readTextFileToArray(fortranDir + file);
		ArrayList<String> javaFileArray = common.readTextFileToArray(javaDir + file);
		String[] itemLabels = new String[]{};
		
		
		for (int i=0;i<javaFileArray.size();i++)
		{
			String javaLine = javaFileArray.get(i).trim();
			
			if (i==0)
			{
				itemLabels = javaLine.split(",");
				continue;
			}
			String fortranLine = fortranFileArray.get(i).trim();
			String[] fortranLineSplit = fortranLine.split("\\s+");
			
			String[] javaLineSplit = javaLine.split("\\s+");
			
			for (int j=0;j<javaLineSplit.length;j++)
			{
				if (j==1)
				{
					continue;
				}
				String fortranItem = fortranLineSplit[j];
				String javaItem = javaLineSplit[j];
				double fortranDouble = new Double(fortranItem).doubleValue();
				double javaDouble = new Double(javaItem).doubleValue();
				
				if (compare(fortranDouble,javaDouble))
				{
					
				}
				else
				{
					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
				}
				
//				double THRESHOLD = Math.abs(fortranDouble * 0.01);
//				if (Math.abs(fortranDouble - javaDouble) < THRESHOLD)
//				{
//					
//				}
//				else
//				{
//					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
//				}
				
			}
						
		}
	}
	
	public boolean compare(double fortranDouble, double javaDouble)
	{
		boolean equal = false;
		double THRESHOLD = Math.abs(fortranDouble * 0.05);
		if (Math.abs(fortranDouble - javaDouble) <= THRESHOLD)
		{
			equal = true;
		}
		return equal;
	}
	
	public void compare2()
	{
		String file = "EnergyBalance_Overall.out";
		
		String fortranDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/fortran/";
		String javaDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/java/";
		ArrayList<String> fortranFileArray = common.readTextFileToArray(fortranDir + file);
		ArrayList<String> javaFileArray = common.readTextFileToArray(javaDir + file);
		String[] itemLabels = new String[]{};
		
		
		for (int i=0;i<javaFileArray.size();i++)
		{
			String javaLine = javaFileArray.get(i).trim();
			
			if (i==0)
			{
				itemLabels = javaLine.split(",");
				continue;
			}
			String fortranLine = fortranFileArray.get(i).trim();
			String[] fortranLineSplit = fortranLine.split("\\s+");
			
			String[] javaLineSplit = javaLine.split("\\s+");
			
			for (int j=0;j<javaLineSplit.length;j++)
			{
				if (j==1)
				{
					continue;
				}
				String fortranItem = fortranLineSplit[j];
				String javaItem = javaLineSplit[j];
				double fortranDouble = new Double(fortranItem).doubleValue();
				double javaDouble = new Double(javaItem).doubleValue();
				
				if (compare(fortranDouble,javaDouble))
				{
					
				}
				else
				{
					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
				}
				
//				double THRESHOLD = Math.abs(fortranDouble * 0.01);
//				if (Math.abs(fortranDouble - javaDouble) < THRESHOLD)
//				{
//					
//				}
//				else
//				{
//					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
//				}
			}
						
		}
	}
	
	public void compare3()
	{
		String file = "EnergyBalance_Tsfc_TimeAverage.out";
		
		String fortranDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/fortran/";
		String javaDir = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmall/java/";
		ArrayList<String> fortranFileArray = common.readTextFileToArray(fortranDir + file);
		ArrayList<String> javaFileArray = common.readTextFileToArray(javaDir + file);
		String[] itemLabels = new String[]{};
		
		
		for (int i=0;i<javaFileArray.size();i++)
		{
			String javaLine = javaFileArray.get(i).trim();
			
			if (i==0)
			{
				itemLabels = javaLine.split(",");
				continue;
			}
			String fortranLine = fortranFileArray.get(i).trim();
			String[] fortranLineSplit = fortranLine.split("\\s+");
			
			String[] javaLineSplit = javaLine.split("\\s+");
			
			for (int j=0;j<javaLineSplit.length;j++)
			{
				if (j==1)
				{
					continue;
				}
				String fortranItem = fortranLineSplit[j];
				if (fortranItem.contains("*"))
				{
					fortranItem ="-999.99";
				}
				String javaItem = javaLineSplit[j];
				double fortranDouble = new Double(fortranItem).doubleValue();
				double javaDouble = new Double(javaItem).doubleValue();
				
				if (compare(fortranDouble,javaDouble))
				{
					
				}
				else
				{
					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
				}
				
//				double THRESHOLD = Math.abs(fortranDouble * 0.01);
//				if (Math.abs(fortranDouble - javaDouble) < THRESHOLD)
//				{
//					
//				}
//				else
//				{
//					System.out.println(i + " " + j + " " + itemLabels[j] + " "+ fortranDouble + " " + javaDouble);
//				}
			}
						
		}
	}

}
