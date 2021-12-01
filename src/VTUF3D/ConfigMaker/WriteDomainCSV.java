package VTUF3D.ConfigMaker;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import au.com.bytecode.opencsv.CSVReader;


//  this class reads the CSV files with the domain layouts
public class WriteDomainCSV
{
//	public final String dirLocation = "/home/kerryn/workspace/TUF3D_Graphs/src/au/edu/monash/ges/tuf3d/config/DomainFiles/";

	public static void main(String[] args)
	{

	}
	
	public int[] readCSV(String name, int variable, String dirLocation)
	{	
		int[] intArray = null;
	    try
		{
	    	String filename = dirLocation + name + "_" + variable + ".csv";
	    	CSVReader buildingHeightsReader = new CSVReader(new FileReader(filename));
			List<String[]> buildingHeightsEntries = buildingHeightsReader.readAll();
			buildingHeightsReader.close();
			
			int width = buildingHeightsEntries.size();
			String[] tempLine = buildingHeightsEntries.get(0);
			String tempLine2 = tempLine[0];
			String[] tempLine3 = tempLine2.split("\\s+");
			int height = tempLine3.length;
			int totalLength = width * height;
			intArray = new int[totalLength];
			
		    int count = 0;
			for (String[] data : buildingHeightsEntries)
			{
				for (String line : data)
				{
					if (line == null || line.trim().equals(""))
					{
						continue;
					}
//					System.out.println(line);
					String lineStrip = line.replaceAll("\"", "").trim();
					String[] lineSplit = lineStrip.split("\\s+");
					for (String item : lineSplit)
					{
						int itemInt = new Integer(item).intValue();
						intArray[count]=itemInt;
						count ++;
					}
				}				
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	    return intArray;
	}
	



	
	
}
