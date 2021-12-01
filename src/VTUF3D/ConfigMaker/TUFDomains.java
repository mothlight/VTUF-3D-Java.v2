package VTUF3D.ConfigMaker;

import java.util.TreeMap;

public class TUFDomains
{

	String domain;
	final public static int BUILDING_HEIGHTS=1;
	final public static int VEGETATION_HEIGHTS=2;
	final public static int VEGETATION_TYPES=3;
	final public static int TREES=4;
	
//	private String dirLocation = "/home/kerryn/workspace/TUF3D_Graphs/src/au/edu/monash/ges/tuf3d/config/DomainFiles/";
//	private String filename = "PrestonBase8";
	
	private String dirLocation ;
	private String filename ;

	public TUFDomains(String domain, 
			String dirLocation, String filename)
	{
		super();
		this.domain = domain;
		this.dirLocation = dirLocation;
		this.filename = filename;
	}
	
	public TreeMap<Integer, int[]> getDomains()
	{
		//System.out.println("domain="+domain);
		TreeMap<Integer, int[]> domainValues = new TreeMap<Integer, int[]>();

//		if(domain.contains("AU-Preston")
//				)  // need to modify this to reflect 46% buildings (already there), 18.5% grass (already there)
			       // trees from 7.5% to 17.5% (adding 10.0%), roads from 27% to 17% (down 10.0%)	
			       // this adds a mix of brushbox and olive to PrestonBrushboxDiff4
//		{				
			WriteDomainCSV write = new WriteDomainCSV();
			int[] startingCentralHeightArray = write.readCSV(filename, TUFDomains.BUILDING_HEIGHTS, dirLocation);
			domainValues.put(BUILDING_HEIGHTS, startingCentralHeightArray);
			int[] startingCentralHeightVegArray = write.readCSV(filename, TUFDomains.VEGETATION_HEIGHTS, dirLocation);
			domainValues.put(VEGETATION_HEIGHTS, startingCentralHeightVegArray);
			int[] startingCentralHeightVegTypeArray = write.readCSV(filename, TUFDomains.VEGETATION_TYPES, dirLocation);
			domainValues.put(VEGETATION_TYPES, startingCentralHeightVegTypeArray);
			int[] startingCentralTreesArray = write.readCSV(filename, TUFDomains.TREES, dirLocation);
			domainValues.put(TREES, startingCentralTreesArray);	
//		}
	return domainValues;
}
//	public static double getForcingHeight(String dataTable)
//	{
//		int forcingHeight = 4;
//		if (dataTable.equals("Preston"))
//		{
//			forcingHeight = 40;
//		}
//		return forcingHeight;
//	}
}
