package VTUF3D.ConfigMaker;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;



public class ConfigurationMaker
{
	public boolean DEBUG = false;
	String runDirectory = "/home/kerryn/git/2020-06-UrbanPlumber/VTUF-3D/AU-Preston2004";
    private double gridSizeInMeters=5;
    private String forcingSource="Preston";
	String latForMaespa = "37	47	0" ;
	String lonForMaespa = "144	58	0" ;
	double lat = -37.7;	
	int year = 2004;	
	int start = 2;
	int end = 60;
	String sourceDirectory = "/home/kerryn/workspace/TUF-3DRadiationOnly2/";
	String sourceExe = "TUF3Dradiation";
	
	String maespaExeDir = "/home/kerryn/workspace/TUF-3DRadiationOnly2/maespa/";
	String maespaExe = "maespa.out";
	
	double forcingHeight = 40;
	String configDomainsDirectory = "/home/kerryn/workspace/TUF3D_Graphs/src/au/edu/monash/ges/tuf3d/config/DomainFiles/";
	String configDomainFilename = "PrestonBase8";

	Common common = new Common();
	final static int ARRAYENDER = 1;
	public int[][] buildingHeightArray;
	public int[][] vegHeightArray;
	public int[][] vegTypeArray;
	public int[][] treesArray;
	
//	public int[] buildingHeightArray1D;
//	public int[] vegHeightArray1D;
//	public int[] vegTypeArray1D;
//	public int[] treesArray1D;
	
	public int configTreeMapCentralArrayLength;
	public int configTreeMapCentralWidth;
	public int configTreeMapCentralLength;
	public int configTreeMapX;
	public int configTreeMapY;
	public int configTreeMapX1;
	public int configTreeMapX2;
	public int configTreeMapY1;
	public int configTreeMapY2;
	public double configTreeMapGridSize;
	public int configTreeMapNumsfcab;	
	public double buildingHeightAverage;
	public double treeHeightAverage;
	
	ArrayList<String[]> testFlxData;
	ArrayList<String[]> hrFlxData;
	ArrayList<String[]> dayFlxData;
	ArrayList<String[]> respData;
	ArrayList<String[]> watbalData;
	ArrayList<String[]> watbaldayData;
	ArrayList<String[]> sunlaData;
	ArrayList<TreeMap<String,String[]>> layflxData;
	
	final int testFlxDAY=0;
	final int testFlxHR=1;
	final int testFlxPT=2;  
	final int testFlxX=3;  
	final int testFlxY=4;  
	final int testFlxZ=5;  
	final int testFlxPAR=6;  
	final int testFlxFBEAM=7;   
	final int testFlxSUNLA=8;  
	final int testFlxTD=9;   
	final int testFlxTSCAT=10;  
	final int testFlxTTOT=11; 
	final int testFlxAPARSUN=12; 
	final int testFlxAPARSH=13; 
	final int testFlxAPAR=14;
	
	final int testHrDOY=0; 
	final int testHrTree=1; 
	final int testHrSpec=2; 
	final int testHrHOUR=3; 
	final int testHrhrPAR=4; 
	final int testHrhrNIR=5; 
	final int testHrhrTHM=6; 
	final int testHrhrPs=7; 
	final int testHrhrRf=8; 
	final int testHrhrRmW=9; 
	final int testHrhrLE=10; 
	final int testHrLECAN=11; 
	final int testHrGscan=12; 
	final int testHrGbhcan=13; 
	final int testHrhrH=14; 
	final int testHrTCAN=15; 
	final int testHrALMAX=16; 
	final int testHrPSIL=17; 
	final int testHrPSILMIN=18; 
	final int testHrCI=18; 
	final int testHrTAIR=19; 
	final int testHrVPD=20; 
	final int testHrPAR=21; 
	final int testHrZEN=22; 
	final int testHrAZ=23;
	
	final int watbalDay=0;
	final int watbalHour=1;
	final int watbalWsoil=2;
	final int watbalWsoilroot=3;
	final int watbalPpt=4;
	final int watbalCanopystore=5;
	final int watbalEvapstore=6;
	final int watbalDrainstore=7;
	final int watbalTfall=8;
	final int watbalEt=9;
	final int watbalEtmeas=10;
	final int watbalDischarge=11;
	final int watbalOverflow=12;
	final int watbalWeightedswp=13;
	final int watbalKtot=14;
	final int watbalDrythick=15;
	final int watbalSoilevap=16;
	final int watbalSoilmoist=17;
	final int watbalFsoil=18;
	final int watbalQh=19;
	final int watbalQe=20;
	final int watbalQn=21;
	final int watbalQc=22;
	final int watbalRglobund=23;
	final int watbalRglobabv=24;
	final int watbalRadinterc=25;
	final int watbalRnet=26;
	final int watbalTotlai=27;
	final int watbalTair=28;
	final int watbalSoilt1=29;
	final int watbalSoilt2=30;
	final int watbalFracw1=31;
	final int watbalFracw2=32;
	final int watbalFracaPAR=33;
	
	final int MAXHRS=95;
	final int sunlaDOY=0;
	final int sunlaHour=1;
	final int sunlaTree=2;
	final int sunlaIPT=3;
	final int sunlaSUNLA=4;
	final int sunlaAREA=5;
	final int sunlaBEXT=6;
	final int sunlaFBEAM=7;
	final int sunlaZEN=8;
	final int sunlaABSRPPAR=9+MAXHRS;
	final int sunlaABSRPNIR=10+MAXHRS;
	final int sunlaABSRPTH=11+MAXHRS;
	final int sunlaBFPAR=12+MAXHRS;
	final int sunlaDFPAR=13+MAXHRS;
	final int sunlaBFNIR=14+MAXHRS;
	final int sunlaDFNIR=15+MAXHRS;
	final int sunlaDFTHR=16+MAXHRS;
	final int sunlaSCLOSTPAR=17+MAXHRS;
	final int sunlaSCLOSTNIR=18+MAXHRS;
	final int sunlaSCLOSTTH=19+MAXHRS;
	final int sunlaDOWNTH=20+MAXHRS;
	final int sunlaPARABV=21+MAXHRS;
	final int sunlaNIRABV=22+MAXHRS;
	final int sunlaTHRABV=23+MAXHRS;
	
	final String LAYFLX_LAI = "LAYFLX_LAI";
	final String LAYFLX_JMAX = "LAYFLX_JMAX";
	final String LAYFLX_VCMAX = "LAYFLX_VCMAX";
	final String LAYFLX_DAY = "LAYFLX_DAY";
	final String LAYFLX_HOUR = "LAYFLX_HOUR";
	final String LAYFLX_PPAR = "LAYFLX_PPAR";
	final String LAYFLX_PPS = "LAYFLX_PPS";
	final String LAYFLX_PTRANSP = "LAYFLX_PTRANSP";
	
	final int LAYFLX_DAY_DAY=1;
	final int LAYFLX_DAY_HOUR=3;
	
    public final String USTOREY_FILE_NAME = "ustorey.dat";
    public int highestBuildingHeight;
	protected Set<String> configVariationsSetValues;
	
	

	public static void main(String[] args)
	{
		ConfigurationMaker c = new ConfigurationMaker();
		
//		#PrestonBase8  # Run with modified differential (100% and diffuse only) on and change number of trees to Andy's published values, 50/50 mix of brush and olive
//		CreateMaespaRunAndProcess.runDirectoryPrestonBase8=/home/kerryn/Documents/Work/VTUF-Runs/PrestonBase/PrestonBase8
//		CreateMaespaRunAndProcess.startPrestonBase8=40
//		CreateMaespaRunAndProcess.endPrestonBase8=70
//		CreateMaespaRunAndProcess.doubleDomainPrestonBase8=false
//		CreateMaespaRunAndProcess.noTreesPrestonBase8=false
//		CreateMaespaRunAndProcess.gridSizePrestonBase8=5
//		CreateMaespaRunAndProcess.forcingPrestonBase8=Preston
//		CreateMaespaRunAndProcess.yearPrestonBase8=2004
		

		
		c.create();

	}
	
	public void create()
	{
		String configNumber="";
		

		common.createDirectory(runDirectory);
	
		
		createStreetsEtc(runDirectory, gridSizeInMeters);
		process(start, 
				end, 
				runDirectory, forcingSource, year, gridSizeInMeters, configNumber);
		
		String targetDirectory = runDirectory;
		String source = sourceDirectory + sourceExe;
		String target = targetDirectory + "/" + sourceExe;
		common.createSymlink(source, target );

	}
	
	final String linefeed = "\n";
	
	public void outputStats(TUFBldVegHeights vegHeights, String runDirectory)
	{
		String stats =  "Percentages: " + linefeed +
						"Trees: " + vegHeights.getTreePercentage()+ linefeed +
						"Grass: " + vegHeights.getGrassPercentage()+ linefeed +
						"Building: " + vegHeights.getBuildingPercentage()+ linefeed +
						"Streets: " + vegHeights.getStreetPercentage() + linefeed +
						"VegHt: " + (vegHeights.vegetationHeightAve ) + linefeed +
						"BldHt: " + (vegHeights.buildingHeightAve ) + linefeed +
						"VegDomainHt: " + (vegHeights.vegetationHeightDomainAve ) + linefeed +
						"BldDomainHt: " + (vegHeights.buildingHeightDomainAve ) + linefeed 
						;
		common.writeFile(stats, runDirectory + "domain_stats.txt");
	}
	
	public void process(int day, 
			int end, 
			String runDirectory, String dataTable, int year, double gridSizeInMeters, String configNumber)
	{
		int numDays = end - day;
		String inputDirectory = "/.";		
//		inputDirectory = runDirectory + "/.";
		int numberOfTrees = 1;
		
		TUFBldVegHeights vegHeights = new TUFBldVegHeights();
		vegHeights.vegetationArrayPreparation(getBuildingHeightArray(),getVegHeightArray(),getVegTypeArray(),getTreesArray(), gridSizeInMeters);
		outputStats(vegHeights, runDirectory);
	
		ConfigParametersDat  configParametersDat = new ConfigParametersDat(runDirectory, year, configNumber, day
				);
		configParametersDat.setYd(
				day
				);
		configParametersDat.setPress(1013.3);
		configParametersDat.setEmiss_var("F");
		configParametersDat.setEmissInter(0.90);
		configParametersDat.setEmissNS(0.90);
		configParametersDat.setEmissEW(0.90); 
		configParametersDat.setCloudtype("0");
		configParametersDat.setNumres(1);
		String[] resinValue = {"2"};
		configParametersDat.setResin(resinValue);
		configParametersDat.setStror_in("0.01");
		configParametersDat.setStrorint("0.01");
		configParametersDat.setStrormax("0.01");
		configParametersDat.setXlat_in ("" + lat);
		configParametersDat.setXlatint("0.01");
		configParametersDat.setXlatmax("" + lat);
		configParametersDat.setTthreshold("10.0");
		configParametersDat.setMatlab_out("T");
		configParametersDat.setSum_out("F");
		configParametersDat.setMinres("2");	   
		configParametersDat.setTsfcr("18.0");
		configParametersDat.setTsfcs("23.0");
		configParametersDat.setTsfcw("22.0");
		configParametersDat.setXlat_in (lat + "");
		configParametersDat.setXlatint("0.01");
		configParametersDat.setXlatmax(lat + "");	
		configParametersDat.setBuildht_m(getBuildingHeightAverage()  + "" );
		
		//zref is the forcing height. But it also needs to be higher than the highest building
		double tempZref = common.roundToDecimals(getHighestBuildingHeight() * gridSizeInMeters * 1.1, 1);
		
//		double forcingHeight = TUFDomains.getForcingHeight(dataTable);

		if (forcingHeight > tempZref)
		{
			tempZref = forcingHeight;
		}
		
		configParametersDat.setZref(tempZref + "");		
		configParametersDat.generateFile();

//		configParametersDat.writeConfigFile(inputDirectory);
		configParametersDat.writeConfigFile(runDirectory);
		
		//right now, it will just start on 2003 day 1 and use all the forcing data to the end of the Urban Plumber dataset
		//so we don't know how many days of data yet
		ConfigForcingDat configForcingDat = new ConfigForcingDat(runDirectory, year, configNumber, day, numDays);
		configForcingDat.generateFile(dataTable);		

		configForcingDat.writeConfigForcingFile(inputDirectory);
//		int numberOfForcingSteps = configForcingDat.getNumfrc();
		ArrayList<double[]> loadedMetData = configForcingDat.loadedMetData;
		
//		numDays = (int) Math.round(numberOfForcingSteps/48.0);

		
		TreeMap<String, Integer> configFileVariations = vegHeights.getConfigFileVariations();		
		MaespaConfigConfileDat maespaConfigConfileDat = new MaespaConfigConfileDat(runDirectory, year, configNumber, day, numDays);
		
		//create treemap.dat file

		maespaConfigConfileDat.setConfigStartdate(maespaConfigConfileDat.getStartDate());
		maespaConfigConfileDat.setConfigEnddate(maespaConfigConfileDat.getEndDate());
		
		maespaConfigConfileDat.setConfigTreeMapCentralArrayLength(configTreeMapCentralArrayLength);
		maespaConfigConfileDat.setConfigTreeMapCentralWidth(configTreeMapCentralWidth);
		maespaConfigConfileDat.setConfigTreeMapCentralLength(configTreeMapCentralLength);
		maespaConfigConfileDat.setConfigTreeMapX(configTreeMapX);
		maespaConfigConfileDat.setConfigTreeMapY(configTreeMapY);		
		maespaConfigConfileDat.setConfigTreeMapX1(configTreeMapX1);
		maespaConfigConfileDat.setConfigTreeMapY1(configTreeMapY1);		
		maespaConfigConfileDat.setConfigTreeMapX2(configTreeMapX2);
		maespaConfigConfileDat.setConfigTreeMapY2(configTreeMapY2);		

		maespaConfigConfileDat.setConfigTreeMapNumsfcab(configTreeMapNumsfcab);			
		
		maespaConfigConfileDat.setConfigTreeMapNumberTreePlots(vegHeights.getCalculatedNumberOfTrees());
		maespaConfigConfileDat.setConfigTreeMapXLocation(vegHeights.getCalculatedTreeXlocationsStr());		
		maespaConfigConfileDat.setConfigTreeMapYLocation(vegHeights.getCalculatedTreeYlocationsStr());
		
		maespaConfigConfileDat.setConfigTreeMapPhyfileNumber(vegHeights.getCalculatedPhyConfigNumberStr());
		maespaConfigConfileDat.setConfigTreeMapStrfileNumber(vegHeights.getCalculatedStrConfigNumberStr());
		maespaConfigConfileDat.setConfigTreeMapTreesFilesNumber(vegHeights.getCalculatedTreeConfigNumberStr());
		maespaConfigConfileDat.setConfigTreeMapTreesHeight(vegHeights.getCalculatedTreeConfigTreeHeightsStr());
		
		
		maespaConfigConfileDat.setConfigTreeMapTrees(vegHeights.getCalculatedTreeConfigTreesStr());
		
		maespaConfigConfileDat.setConfigTreeMapNumberBuildingPlots(vegHeights.getCalculatedNumberOfBuildings());
		maespaConfigConfileDat.setConfigTreeMapBuildingXLocation(vegHeights.getCalculatedBuildingXlocationsStr());		
		maespaConfigConfileDat.setConfigTreeMapBuildingYLocation(vegHeights.getCalculatedBuildingYlocationsStr());
		maespaConfigConfileDat.setConfigTreeMapBuildingHeight(vegHeights.getCalculatedTreeConfigBuildingHeightsStr());
		maespaConfigConfileDat.setConfigTreeMapGridSize(gridSizeInMeters);
		
		maespaConfigConfileDat.setConfigTreeMapWidth(vegHeights.getCalculatedWidth());
		maespaConfigConfileDat.setConfigTreeMapLength(vegHeights.getCalculatedLength());
		maespaConfigConfileDat.setConfigTreeMapHighestBuildingHeight(getHighestBuildingHeight() +1 );   //add 1 for good measure 
		maespaConfigConfileDat.setConfigTreeMapUsingDiffShading(1);
		
		
		maespaConfigConfileDat.writeTreeMapConfigFile(inputDirectory);
		

		
		int count = 1;
		Set<String> configVariationsSet = configFileVariations.keySet();

		for (String keyValue : configVariationsSet)
		{
			Integer configVariationValue =  configFileVariations.get(keyValue);
			String[] splitKeyValues = keyValue.split("_");
			System.out.println(keyValue + " " + configVariationValue);
			int treesNumber = configVariationValue;
			int phyFileNumber = configVariationValue;
			int strFileNumber = configVariationValue;
			int configType = new Integer(splitKeyValues[1]).intValue();
			int heightInGrids = new Integer(splitKeyValues[0]).intValue();
			double heightInMeters = heightInGrids * gridSizeInMeters;
			

			String newSubdirectory = count + "";
			
			
			//adding differential tree shading, first with diffuse only, second with 50% direct, third with 100% direct
			// now modifying to use 1 minute solar values and only two cases, 100% and diffuse only
			for (int differtialShadingValue=1;differtialShadingValue<3;differtialShadingValue++)
			{
			
				String configDirectory = inputDirectory  + "/" + newSubdirectory + "/" + differtialShadingValue;
				System.out.println("configNumber value=" + configNumber);

				writeTreeConfigFiles(maespaConfigConfileDat, configDirectory, treesNumber, phyFileNumber, strFileNumber, configType, heightInMeters, runDirectory, gridSizeInMeters,year,dataTable);
				generateTreeConfigs(maespaConfigConfileDat, configDirectory, numberOfTrees, configFileVariations, vegHeights, dataTable, configNumber, configType, 
						differtialShadingValue, loadedMetData, latForMaespa, lonForMaespa);
				
				String source = maespaExeDir + maespaExe;
				String target = runDirectory + "/" + newSubdirectory + "/" + differtialShadingValue + "/" + maespaExe;		
				common.createSymlink(source, target);
				
				String runSubDirectory = "/" + newSubdirectory + "/" + differtialShadingValue ;		
				runModel(runSubDirectory, runDirectory);
				
				//also create a symlink to one of the Maespa confiles in the root directory
				String rootConfigDirectory = runDirectory;
				String rootConfigDirectoryFile = rootConfigDirectory + "/" + "confile.dat";
//				String targetConfigDirectory = configDirectory;
				String targetConfigDirectoryFile = runDirectory + "/" + newSubdirectory + "/" + differtialShadingValue + "/" + "confile.dat";
				common.createSymlink(targetConfigDirectoryFile, rootConfigDirectoryFile);	
			}		
			count ++;
		}
	}
	
	public void createStreetsEtc(String runDirectory, double gridSizeInMeters)
	{	
		int count = 0;
						
		System.out.println("2 runDirectory="+runDirectory);
		TUFDomains domains = new TUFDomains(runDirectory, configDomainsDirectory, configDomainFilename);
		TreeMap<Integer, int[]> domainValues = domains.getDomains();
		int[] startingCentralHeightArray = domainValues.get(TUFDomains.BUILDING_HEIGHTS);
		int[] startingCentralHeightVegArray = domainValues.get(TUFDomains.VEGETATION_HEIGHTS);
		int[] startingCentralHeightVegTypeArray = domainValues.get(TUFDomains.VEGETATION_TYPES);
		int[] startingCentralTreesArray = domainValues.get(TUFDomains.TREES);
		
//		setVegTypeArray1D(startingCentralHeightVegTypeArray);
//		setVegHeightArray1D(startingCentralHeightVegArray);
//		setBuildingHeightArray1D(startingCentralHeightArray);
//		setTreesArray1D(startingCentralTreesArray);
		
		configTreeMapCentralArrayLength = startingCentralHeightArray.length;
		configTreeMapCentralWidth = (int)Math.sqrt(configTreeMapCentralArrayLength); //20
		configTreeMapCentralLength = (int)Math.sqrt(configTreeMapCentralArrayLength); //20
		configTreeMapX=configTreeMapCentralWidth * 7 ; //138
		configTreeMapY=configTreeMapCentralLength * 7 ; //138
		configTreeMapX1 = configTreeMapCentralWidth*3;
		configTreeMapX2 = configTreeMapX1 + configTreeMapCentralWidth -1;
		configTreeMapY1 = configTreeMapCentralLength*3;
		configTreeMapY2 = configTreeMapY1 + configTreeMapCentralLength -1;
		// this is width*height which gives up surfaces, then add sides of buildings
		configTreeMapNumsfcab = (configTreeMapCentralWidth * configTreeMapCentralLength) + (4 * 2 * 2);
		
		//FORTRAN does arrays [y,x]
		int[][] buildingHeightArray = new int[configTreeMapY][configTreeMapX];
		int[][] vegHeightArray = new int[configTreeMapY][configTreeMapX];
		int[][] vegTypeArray = new int[configTreeMapY][configTreeMapX];
		int[][] treesArray = new int[configTreeMapY][configTreeMapX];
		
		double buildingHeightTotal = 0;
		double treeHeightTotal = 0;
		int numberOfTreeGrids = 0;
		int numberOfBuildingAndTreeGrids = 0;
		highestBuildingHeight = 0;
		
//		int loopCount2=0;
		for (int i1=0;i1<configTreeMapX/configTreeMapCentralWidth+1;i1++)
		{
			for (int j1=0;j1<configTreeMapX/configTreeMapCentralLength+1;j1++)
			{
				int xStart = i1*configTreeMapCentralWidth;
				int xLimit = (i1*configTreeMapCentralWidth)+configTreeMapCentralWidth;
				for (int i=xStart;i<xLimit;i++)
				{
					int yStart = j1*configTreeMapCentralLength;
					int yLimit = (j1*configTreeMapCentralLength)+configTreeMapCentralLength;
					for (int j=yStart;j<yLimit;j++)
					{
						if (i>=configTreeMapX)
						{
							count++;
							continue;
						}
						if (j>=configTreeMapY)
						{
							count++;
							continue;
						}
//						loopCount2++;
						//System.out.println(i + " " + j + " "  + " loopCount2=" + loopCount2);
						buildingHeightArray[j][i] = startingCentralHeightArray[count];
						vegHeightArray[j][i] = startingCentralHeightVegArray[count];
						vegTypeArray[j][i] = startingCentralHeightVegTypeArray[count];
						treesArray[j][i] = startingCentralTreesArray[count];
						count++;
						
						if (buildingHeightArray[j][i] > 0)
						{
							buildingHeightTotal += buildingHeightArray[j][i] * gridSizeInMeters;
							numberOfBuildingAndTreeGrids++;
							if (buildingHeightArray[j][i] > highestBuildingHeight)
							{
								highestBuildingHeight = buildingHeightArray[j][i];
							}
						}
						if (vegHeightArray[j][i] > 0)
						{
							buildingHeightTotal += vegHeightArray[j][i] * gridSizeInMeters;	
							numberOfBuildingAndTreeGrids++;
							if (vegHeightArray[j][i] > highestBuildingHeight)
							{
								highestBuildingHeight = vegHeightArray[j][i];
							}
							
							treeHeightTotal += vegHeightArray[j][i] * gridSizeInMeters;	
							numberOfTreeGrids++;
						}
					}					
				}
				count = 0;
			}
		}
		setBuildingHeightAverage(common.roundToDecimals(buildingHeightTotal/numberOfBuildingAndTreeGrids, 2) );
		setHighestBuildingHeight(highestBuildingHeight);

		setBuildingHeightArray(buildingHeightArray);
		setVegHeightArray(vegHeightArray);
		setVegTypeArray(vegTypeArray);
		setTreesArray(treesArray);
		
		setTreeHeightAverage(common.roundToDecimals(treeHeightTotal/numberOfTreeGrids, 2) );
		System.out.println(("Tree height average=" + getTreeHeightAverage()));
		System.out.println(("Building height average=" + getBuildingHeightAverage()));
	}
	
	public void runModel(String target, String runDirectory)
	{
		final String command = "./maespa.out";
		try 
		{
			  runCommand2(command,target, runDirectory);
		} 
		catch (Exception error) 
		{
			error.printStackTrace();
		}
	}
	
	private void runCommand2(String command, String target, String runDirectory)
	{
		ProcessBuilder pb = new ProcessBuilder(command);
		Map<String, String> env = pb.environment();
		pb.directory(new File(runDirectory + target));
		File log = new File("log");
		pb.redirectErrorStream(true);
		pb.redirectOutput(Redirect.appendTo(log));
		Process p;
		try
		{
			p = pb.start();
			assert pb.redirectInput() == Redirect.PIPE;
			assert pb.redirectOutput().file() == log;
			assert p.getInputStream().read() == -1;
			try
			{
				p.waitFor();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		catch (IOException e)
		{			
			e.printStackTrace();
		}

	}

	
	public void writeTreeConfigFiles(MaespaConfigConfileDat maespaConfigConfileDat, String inputDirectory, int treesNumber,
			int phyFileNumber, int strFileNumber, int configType, double height, String runDirectory, double gridSizeInMeters, int year, String dataTable)
	{	
		//default to 4 meters
//		double forcingHeight = TUFDomains.getForcingHeight(dataTable);
		
		String twoDigitYear = new Integer(year).toString().substring(2, 4);
		int xmax = (int)gridSizeInMeters;
		int ymax = (int)gridSizeInMeters;
		double xy = gridSizeInMeters/2;
		
//		double leafAreaIndex =  maespaConfigConfileDat.getConfigTreezAlllareaValues();
		double trunkHeight = common.roundToDecimals(height * .25, 2);
		double crownHeight = common.roundToDecimals(height * .75, 2);
		double crownRadiusX = common.roundToDecimals(gridSizeInMeters * 0.5, 2);
		double crownRadiusY = common.roundToDecimals(gridSizeInMeters * 0.5, 2);
//		String trunkHeightStr = String.format("%.2f", trunkHeight);
//		String crownHeightStr = String.format("%.2f", crownHeight);
		
		// using relationships from  http://www.academicjournals.org/journal/AJB/article-abstract/96974C726212 and 10.1093/treephys/tps127
		double stemDiam = 0.05;
		if (trunkHeight + crownHeight > 7)
		{
			stemDiam = ((trunkHeight + crownHeight)-6.74)/14.4;
		}
		
		// if zpd and z0ht are 0, it crashes
		if (crownHeight == 0)
		{
			crownHeight = 0.5;
		}
		
		String phyConfigFileStr = "";
		String strConfigFileStr = "";
		
//		double oliveZht = 4.0;
//		double oliveZpd = 1.6;
//		double oliveZ0ht = 3.0;
//		double brushboxZht = 10.0;
//		double brushboxZpd = 3.89;
//		double brushboxZ0ht = 0.583;
		
		//calculate these instead
		//this is the forcing height
		//double oliveZht = height + 1;
		double zht =forcingHeight;
		// 2/3 of tree crown
		//double oliveZpd = oliveZht * 2/3;
		double zpd = crownHeight * 2/3;
		// 1/10 of tree crown
		//double oliveZ0ht = oliveZht * 1/10;
		double z0ht = crownHeight * 1/10;
		
//		double brushboxZht = height + 1;
//		double brushboxZpd = brushboxZht * 2/3;
//		double brushboxZ0ht = brushboxZht * 1/10;
		
//		//olive 4.0, brushbox 10
//		double zht = 10 * heightMultiples;
//		//olive 1.6, brushbox 3.89
//		double zpd = 3.89 * heightMultiples;
//		//olive 3.0, brushbox 0.583
//		double z0ht = 0.583 * heightMultiples;
		
		if (configType == TUFBldVegHeights.OLIVE_CONFIG_TYPE)
		{
//			int xmax = (int)gridSizeInMeters;
//			int ymax = (int)gridSizeInMeters;
//			double xy = gridSizeInMeters/2;
			
			double leafAreaIndex = 2.48;
			// multiply LAI by the total area of the tree canopy
			double canopyArea = (crownRadiusX * crownRadiusY) * 3.1415 ;
			double totalLeafAreaIndex = leafAreaIndex * canopyArea;
			//create trees.dat file
			maespaConfigConfileDat.setConfigTreex(0);	
			maespaConfigConfileDat.setConfigTreey(0);	
			maespaConfigConfileDat.setConfigTreexmax(xmax);	
			maespaConfigConfileDat.setConfigTreeymax(ymax);	
			maespaConfigConfileDat.setConfigTreexslope(0);
			maespaConfigConfileDat.setConfigTreeyslope(0);
			maespaConfigConfileDat.setConfigTreebearing(0);
			maespaConfigConfileDat.setConfigTreenotrees(1);
			maespaConfigConfileDat.setConfigTreezht(zht);
			maespaConfigConfileDat.setConfigTreezpd(zpd);
			maespaConfigConfileDat.setConfigTreez0ht(z0ht);
			maespaConfigConfileDat.setConfigTreeispecies("1");
			maespaConfigConfileDat.setConfigTreexycoords(xy
					+ " "
					+ xy);						
			maespaConfigConfileDat.setConfigTreeTitle("Single olive tree IN 1x1 metre plot.");
			maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradxDates("");
			maespaConfigConfileDat.setConfigTreezAllradxValues(crownRadiusX);		
			maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradyDates("");
			maespaConfigConfileDat.setConfigTreezAllradyValues(crownRadiusY);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
			maespaConfigConfileDat.setConfigTreezAllhtcrownValues(crownHeight);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownValuesComment(" ");	
			maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
			maespaConfigConfileDat.setConfigTreezAlldiamDates("");
			maespaConfigConfileDat.setConfigTreezAlldiamValues(stemDiam);		
			maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
			maespaConfigConfileDat.setConfigTreezAllhttrunkValues(trunkHeight);		
			maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
			maespaConfigConfileDat.setConfigTreezAlllareaDates("");
			maespaConfigConfileDat.setConfigTreezAlllareaValues(common.roundToDecimals(totalLeafAreaIndex, 2));
			maespaConfigConfileDat.setConfigTreezAllhttrunkValuesComment(" ");	
			maespaConfigConfileDat.setConfigTreezAlldiamValuesComment(" ");	
			
			maespaConfigConfileDat.setConfigTreezAlllareaValuesComment("!Total leaf area then is = LAI * tree area (radius^2*pi). LAI="
					+ leafAreaIndex
					+ "; "
					+ crownRadiusX
					+ "^2*pi="
					+ common.roundToDecimals(canopyArea, 2)
					+ "; => Total LA="
					+ common.roundToDecimals(totalLeafAreaIndex, 2)
					);	
					
			//create phy file	for olive		
			maespaConfigConfileDat.setConfigPhyTitle("Physiology file for Olea europaea (Olive trees) at Smith Street. "
					+ "This is the new file using parameters derived from RESPONSE CURVES and the g0 and g1 from the East side streets, rather than the west side streets. " + '\n' +'\n' +
					"number of age classes of foliage. Must be same as in str.dat file for NOAGEC " + '\n' +				
					"&noages " + '\n');
			maespaConfigConfileDat.setConfigPhyNoagep(1);
			maespaConfigConfileDat.setConfigPhyPhenol(1.0);
			maespaConfigConfileDat.setConfigPhyAbsorpNolayers(1);	
			maespaConfigConfileDat.setConfigPhyRhosol("0.10	0.05	0.05");
			maespaConfigConfileDat.setConfigPhyRhosolComment("(observed, Levinson et al.2007, Oke, 1987)");
			maespaConfigConfileDat.setConfigPhyAtau("0.01	0.28	0.01");
			maespaConfigConfileDat.setConfigPhyAtauComment("(Olive: Baldini et al 1997) (Adaxial side of leaf)");
			maespaConfigConfileDat.setConfigPhyArho("0.08	0.42	0.05");
			maespaConfigConfileDat.setConfigPhyArhoComment("(Olive: Baldini et al 1997) (Adaxial side of leaf)");
			maespaConfigConfileDat.setConfigPhyBbgsconNodates(1);
			maespaConfigConfileDat.setConfigPhycondunits("H2O");
			maespaConfigConfileDat.setConfigPhyTuzetg0(0.045);
			maespaConfigConfileDat.setConfigPhyTuzetg1(4.53);
			maespaConfigConfileDat.setConfigPhyTuzetsf(3.2);
			maespaConfigConfileDat.setConfigPhyTuzetPsiv(-1.9);
			maespaConfigConfileDat.setConfigPhyTuzetNsides(2);
			maespaConfigConfileDat.setConfigPhyTuzetWleaf(0.0102);
			maespaConfigConfileDat.setConfigPhyTuzetGamma(46);
			maespaConfigConfileDat.setConfigPhyMedlyng0(0.03);
			maespaConfigConfileDat.setConfigPhyMedlyng0Comment("(From Smith St. data) Coutts2014a")  ;
			maespaConfigConfileDat.setConfigPhyMedlyng1(2.615);
			maespaConfigConfileDat.setConfigPhyMedlyng1Comment("(From Smith St. data) Coutts2014a")  ;
			maespaConfigConfileDat.setConfigPhyMedlynNsides(1);
			maespaConfigConfileDat.setConfigPhyMedlynNsidesComment("(Fernandez 1997)");
			maespaConfigConfileDat.setConfigPhyMedlynWleaf(0.0102);
			maespaConfigConfileDat.setConfigPhyMedlynWleafComment("");
			maespaConfigConfileDat.setConfigPhyMedlynGamma(55);
			maespaConfigConfileDat.setConfigPhyMedlynGammaComment("(CO2 curves) Coutts2014a");
			maespaConfigConfileDat.setConfigPhyJmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyJmaxValues(112.4);
			maespaConfigConfileDat.setConfigPhyJmaxValuesComment("(CO2 curves) Coutts2014a");			
			maespaConfigConfileDat.setConfigPhyJmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyVcmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyVcmaxValues(81.18);
			maespaConfigConfileDat.setConfigPhyVcmaxValuesComment("(CO2 curves) Coutts2014a");			
			maespaConfigConfileDat.setConfigPhyVcmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyJmaxparsTheta(0.62);
			maespaConfigConfileDat.setConfigPhyJmaxparsThetaComment("(PAR curves) Coutts2014a");				
			maespaConfigConfileDat.setConfigPhyJmaxparsEavj(35350);
			maespaConfigConfileDat.setConfigPhyJmaxparsEdvj(200000);
			maespaConfigConfileDat.setConfigPhyJmaxparsDelsj(644.4338);
			maespaConfigConfileDat.setConfigPhyJmaxparsAjq(0.19);
			maespaConfigConfileDat.setConfigPhyJmaxparsAjqComment("(PAR curves) (PSICO2=Absorb*8*0.5) Coutts2014a");
			maespaConfigConfileDat.setConfigPhyVcmaxparsEavc(73680);
			maespaConfigConfileDat.setConfigPhyVcmaxparsEdvc(0);
			maespaConfigConfileDat.setConfigPhyRdmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyRdmaxValues(0.94);
			maespaConfigConfileDat.setConfigPhyRdmaxValuesComment("(CO2 curves) Coutts2014a");	
			
			maespaConfigConfileDat.setConfigPhyRdmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyRdparsRtemp(25.0);
			maespaConfigConfileDat.setConfigPhyRdparsQ10f(0.0575);
			maespaConfigConfileDat.setConfigPhyRdparsDayresp(0.6);
			maespaConfigConfileDat.setConfigPhyRdparsEffyrf(0.4);
			maespaConfigConfileDat.setConfigPhySlamaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNoages(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNodates(1);
			maespaConfigConfileDat.setConfigPhySlamaxValues(5.1);
			maespaConfigConfileDat.setConfigPhySlamaxDates("01/03/12");		
			maespaConfigConfileDat.setConfigPhySlamaxValuesComment("!Specific leaf area (5.1=Mariscal et al 2000) (m2.kg-1) Specific leaf area (SLA) is the one-sided area of a fresh leaf, divided by its oven-dry mass");
			
			
			//write str files	for olive	
			maespaConfigConfileDat.setConfigStrTitle("Canopy Structure file for Olea europaea (Olive trees) at Smith Street");
			maespaConfigConfileDat.setConfigStrCshape("ROUND");
			maespaConfigConfileDat.setConfigStrLiaElp(1);
			maespaConfigConfileDat.setConfigStrLiaNalpha(1);
			maespaConfigConfileDat.setConfigStrLiaAvgang(45);
			
			maespaConfigConfileDat.setConfigStrLaddJleaf(0);
			maespaConfigConfileDat.setConfigStrLaddNoagec(1);
			maespaConfigConfileDat.setConfigStrLaddRandom(1.0);
			
			maespaConfigConfileDat.setConfigStrAeroExtwind(0);
			maespaConfigConfileDat.setConfigStrAllomCoefft(91.4114);
			maespaConfigConfileDat.setConfigStrAllomExpont(1.741);
			maespaConfigConfileDat.setConfigStrAllomWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllombCoefft(8.9126);
			maespaConfigConfileDat.setConfigStrAllombExpont(1.341);
			maespaConfigConfileDat.setConfigStrAllombWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllomrCoefft(20);
			maespaConfigConfileDat.setConfigStrAllomrExpont(2.0);
			maespaConfigConfileDat.setConfigStrAllomrRinterc(0.0);
			maespaConfigConfileDat.setConfigStrAllomrFrfrac(0.4);	
			
			phyConfigFileStr = maespaConfigConfileDat.getPhyDataFile();
			strConfigFileStr = maespaConfigConfileDat.getStrDataFile();
			
		}
		
		else if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			//incoming variables are tree height and grid size.
			// height, gridSizeInMeters
			
			//so first find x,y center of grid
//			double gridSize = gridSizeInMeters;
//			int xmax = (int)gridSize;
//			int ymax = (int)gridSize;
//			double xy = gridSize/2;
			
//			double gridMultiples = gridSizeInMeters / 5;
//			double heightMultiples = height / 5;
			
//			crownRadiusX = 2.5 * heightMultiples;
//			crownRadiusY = 2.5 * heightMultiples;
			
			// all the measurements area for a 5x5m grid. Need to scale to actual grid size
			double leafAreaIndex = 2.0;
//			if (maespaConfigConfileDat.isHalfLai())
//			{
//				System.out.println ("half LAI");
//				leafAreaIndex = leafAreaIndex / 2;
//			}
			// multiply LAI by the total area of the tree canopy

			double canopyArea = (crownRadiusX * crownRadiusY) * Math.PI ; 
			double totalLeafAreaIndex = leafAreaIndex * canopyArea;
			//create trees.dat file

//			double zht = 10 * heightMultiples;
//			double zpd = 3.89 * heightMultiples;
//			double z0ht = 0.583 * heightMultiples;
			
//			crownHeight = 5.83 * heightMultiples;
//			trunkHeight = 1.6 * heightMultiples;	
//			double treeDiam = 0.18 * heightMultiples;	
			
			//config for brushbox
						
			maespaConfigConfileDat.setConfigTreex(0);	
			maespaConfigConfileDat.setConfigTreey(0);	
			maespaConfigConfileDat.setConfigTreexmax(xmax);	
			maespaConfigConfileDat.setConfigTreeymax(ymax);	
			maespaConfigConfileDat.setConfigTreexslope(0);
			maespaConfigConfileDat.setConfigTreeyslope(0);
			maespaConfigConfileDat.setConfigTreebearing(0);
			maespaConfigConfileDat.setConfigTreenotrees(1);
			maespaConfigConfileDat.setConfigTreezht(zht);
			maespaConfigConfileDat.setConfigTreezpd(zpd);
			maespaConfigConfileDat.setConfigTreez0ht(z0ht);
			maespaConfigConfileDat.setConfigTreeispecies("1");
			maespaConfigConfileDat.setConfigTreexycoords(xy
					+ " "
					+ xy);						
			maespaConfigConfileDat.setConfigTreeTitle("single Lophostemon Confertuse in 5x5 m plot. Scaled to cemetery tree");
			maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradxDates("");
			maespaConfigConfileDat.setConfigTreezAllradxValues(crownRadiusX);		
			maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradyDates("");
			maespaConfigConfileDat.setConfigTreezAllradyValues(crownRadiusY);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
			maespaConfigConfileDat.setConfigTreezAllhtcrownValues(crownHeight);		
			maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
			maespaConfigConfileDat.setConfigTreezAlldiamDates("");
			maespaConfigConfileDat.setConfigTreezAlldiamValues(stemDiam);		
			maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
			maespaConfigConfileDat.setConfigTreezAllhttrunkValues(trunkHeight);		
			maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
			maespaConfigConfileDat.setConfigTreezAlllareaDates("");
			maespaConfigConfileDat.setConfigTreezAlllareaValues(totalLeafAreaIndex);
					
			phyConfigFileStr = maespaConfigConfileDat.getPhyDataFile(configType);
			
			strConfigFileStr = maespaConfigConfileDat.getStrDataFile(configType);
			
		}
		
		else if (configType == TUFBldVegHeights.OLD_GRASS_CONFIG_TYPE)
		{
			//this is grass
			double gridSize = gridSizeInMeters;
//			int xmax = (int)gridSize;
//			int ymax = (int)gridSize;
//			double xy = gridSize/2;
			
			double leafAreaIndex = 1.47;
			// multiply LAI by the total area of the tree canopy
			double canopyArea = gridSize*gridSize ;
			double totalLeafAreaIndex = leafAreaIndex * canopyArea;		
			
			crownHeight = 0.1;
			trunkHeight = 0.1;

			//create trees.dat file  for old grass type
			maespaConfigConfileDat.setConfigTreex(0);	
			maespaConfigConfileDat.setConfigTreey(0);	
			maespaConfigConfileDat.setConfigTreexmax(xmax);	
			maespaConfigConfileDat.setConfigTreeymax(ymax);	
			maespaConfigConfileDat.setConfigTreexslope(0);
			maespaConfigConfileDat.setConfigTreeyslope(0);
			maespaConfigConfileDat.setConfigTreebearing(0);
			maespaConfigConfileDat.setConfigTreenotrees(1);
			maespaConfigConfileDat.setConfigTreezht(zht);
			maespaConfigConfileDat.setConfigTreezpd(zpd);
			maespaConfigConfileDat.setConfigTreez0ht(z0ht);
			maespaConfigConfileDat.setConfigTreeispecies("1");
			maespaConfigConfileDat.setConfigTreexycoords(xy
					+ " "
					+ xy);						
			maespaConfigConfileDat.setConfigTreeTitle("Grass filling grid square.");
			maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradxDates("");
			maespaConfigConfileDat.setConfigTreezAllradxValues(crownRadiusX);		
			maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradyDates("");
			maespaConfigConfileDat.setConfigTreezAllradyValues(crownRadiusY);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
			maespaConfigConfileDat.setConfigTreezAllhtcrownValues(crownHeight);		
			maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
			maespaConfigConfileDat.setConfigTreezAlldiamDates("");
			maespaConfigConfileDat.setConfigTreezAlldiamValues(0.02);		
			maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
			maespaConfigConfileDat.setConfigTreezAllhttrunkValues(trunkHeight);		
			maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
			maespaConfigConfileDat.setConfigTreezAlllareaDates("");
			maespaConfigConfileDat.setConfigTreezAlllareaValues(totalLeafAreaIndex);
								
			//create phy file	 for old grass type		
			maespaConfigConfileDat.setConfigPhyTitle("Physiology file for grass (based on Olea europaea (Olive trees) at Smith Street )" + '\n' +'\n' +
					"number of age classes of foliage. Must be same as in str.dat file for NOAGEC " + '\n' +				
					"&noages " + '\n');
			maespaConfigConfileDat.setConfigPhyNoagep(1);
			maespaConfigConfileDat.setConfigPhyPhenol(1.0);
			maespaConfigConfileDat.setConfigPhyAbsorpNolayers(1);	
			maespaConfigConfileDat.setConfigPhyRhosol("0.10 0.30 0.05");
			maespaConfigConfileDat.setConfigPhyAtau("0.093		0.34	0.01");
			maespaConfigConfileDat.setConfigPhyArho("0.082		0.49	0.05");
			maespaConfigConfileDat.setConfigPhyBbgsconNodates(1);
			maespaConfigConfileDat.setConfigPhycondunits("H2O");
			maespaConfigConfileDat.setConfigPhyTuzetg0(0.045);
			maespaConfigConfileDat.setConfigPhyTuzetg1(4.53);
			maespaConfigConfileDat.setConfigPhyTuzetsf(3.2);
			maespaConfigConfileDat.setConfigPhyTuzetPsiv(-1.9);
			maespaConfigConfileDat.setConfigPhyTuzetNsides(1);
			maespaConfigConfileDat.setConfigPhyTuzetWleaf(0.0102);
			maespaConfigConfileDat.setConfigPhyTuzetGamma(46);
			maespaConfigConfileDat.setConfigPhyMedlyng0(0.0213);
			maespaConfigConfileDat.setConfigPhyMedlyng1(3.018);
			maespaConfigConfileDat.setConfigPhyMedlynNsides(1);
			maespaConfigConfileDat.setConfigPhyMedlynWleaf(0.0102);
			maespaConfigConfileDat.setConfigPhyMedlynGamma(46);
			maespaConfigConfileDat.setConfigPhyJmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyJmaxValues(135.5);
			maespaConfigConfileDat.setConfigPhyJmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyVcmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyVcmaxValues(82.7);
			maespaConfigConfileDat.setConfigPhyVcmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyJmaxparsTheta(0.9);
			maespaConfigConfileDat.setConfigPhyJmaxparsEavj(35350);
			maespaConfigConfileDat.setConfigPhyJmaxparsEdvj(200000);
			maespaConfigConfileDat.setConfigPhyJmaxparsDelsj(644.4338);
			maespaConfigConfileDat.setConfigPhyJmaxparsAjq(0.2);
			maespaConfigConfileDat.setConfigPhyVcmaxparsEavc(73680);
			maespaConfigConfileDat.setConfigPhyVcmaxparsEdvc(0);
			maespaConfigConfileDat.setConfigPhyRdmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyRdmaxValues(1.12);
			maespaConfigConfileDat.setConfigPhyRdmaxDates("01/03/12");
			maespaConfigConfileDat.setConfigPhyRdparsRtemp(25.0);
			maespaConfigConfileDat.setConfigPhyRdparsQ10f(0.0575);
			maespaConfigConfileDat.setConfigPhyRdparsDayresp(0.6);
			maespaConfigConfileDat.setConfigPhyRdparsEffyrf(0.4);
			maespaConfigConfileDat.setConfigPhySlamaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNoages(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNodates(1);
			maespaConfigConfileDat.setConfigPhySlamaxValues(5.1);
			maespaConfigConfileDat.setConfigPhySlamaxDates("01/03/12");		
			
			phyConfigFileStr = maespaConfigConfileDat.getPhyDataFile();
			
			//write str files	 for old grass type	
			maespaConfigConfileDat.setConfigStrTitle("Physiology file for grass (based on Olea europaea (Olive trees) at Smith Street )");
			maespaConfigConfileDat.setConfigStrCshape("BOX");
			maespaConfigConfileDat.setConfigStrLiaElp(1);
			maespaConfigConfileDat.setConfigStrLiaNalpha(1);
			maespaConfigConfileDat.setConfigStrLiaAvgang(45);
			
			maespaConfigConfileDat.setConfigStrLaddJleaf(0);
			maespaConfigConfileDat.setConfigStrLaddNoagec(1);
			maespaConfigConfileDat.setConfigStrLaddRandom(1.0);
			
			maespaConfigConfileDat.setConfigStrAeroExtwind(0);
			maespaConfigConfileDat.setConfigStrAllomCoefft(91.4114);
			maespaConfigConfileDat.setConfigStrAllomExpont(1.741);
			maespaConfigConfileDat.setConfigStrAllomWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllombCoefft(8.9126);
			maespaConfigConfileDat.setConfigStrAllombExpont(1.341);
			maespaConfigConfileDat.setConfigStrAllombWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllomrCoefft(20);
			maespaConfigConfileDat.setConfigStrAllomrExpont(2.0);
			maespaConfigConfileDat.setConfigStrAllomrRinterc(0.0);
			maespaConfigConfileDat.setConfigStrAllomrFrfrac(0.4);	
			
			strConfigFileStr = maespaConfigConfileDat.getStrDataFile();
			
			//also create ustorey.dat for old grass type
			Ustorey ustorey = new Ustorey();
			String ustoreyStr = ustorey.getSample();
			System.out.println("write ustorey.dat " + runDirectory + inputDirectory + "/" + USTOREY_FILE_NAME);
			common.createDirectory(runDirectory + inputDirectory);
			common.writeFile(ustoreyStr, runDirectory + inputDirectory + "/" + USTOREY_FILE_NAME);	
			maespaConfigConfileDat.setConfigIsimus(1);
			
		}
		else if (configType == TUFBldVegHeights.GRASS_CONFIG_TYPE  || configType == TUFBldVegHeights.IRRIGATED_GRASS_CONFIG_TYPE)
		{
			//this is grass
			double gridSize = gridSizeInMeters;
//			int xmax = (int)gridSize;
//			int ymax = (int)gridSize;
//			double xy = gridSize/2;
			
			double leafAreaIndex = 7.13;
			// multiply LAI by the total area of the tree canopy
			double canopyArea = gridSize*gridSize ;
			double totalLeafAreaIndex = leafAreaIndex * canopyArea;		
			
			crownHeight = 0.1;
			trunkHeight = 0.1;

			//create trees.dat file for new grass type
			maespaConfigConfileDat.setConfigTreex(0);	
			maespaConfigConfileDat.setConfigTreey(0);	
			maespaConfigConfileDat.setConfigTreexmax(xmax);	
			maespaConfigConfileDat.setConfigTreeymax(ymax);	
			maespaConfigConfileDat.setConfigTreexslope(0);
			maespaConfigConfileDat.setConfigTreeyslope(0);
			maespaConfigConfileDat.setConfigTreebearing(0);
			maespaConfigConfileDat.setConfigTreenotrees(1);

			maespaConfigConfileDat.setConfigTreeispecies("1");
			maespaConfigConfileDat.setConfigTreexycoords(xy
					+ " "
					+ xy);	
			
			maespaConfigConfileDat.setConfigTreezht(4.0);
			maespaConfigConfileDat.setConfigTreezpd(0.066);
			maespaConfigConfileDat.setConfigTreez0ht(0.02);
			maespaConfigConfileDat.setConfigTreeTitle("Grass layer as a box tree on the ground covering the plot area.");
			maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradxDates("");
			maespaConfigConfileDat.setConfigTreezAllradxValues(crownRadiusX);		
			maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
			maespaConfigConfileDat.setConfigTreezAllradyDates("");
			maespaConfigConfileDat.setConfigTreezAllradyValues(crownRadiusY);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
			maespaConfigConfileDat.setConfigTreezAllhtcrownValues(0.2);		
			maespaConfigConfileDat.setConfigTreezAllhtcrownValuesComment("!Height of the grass (blade length from Simmonds et al 2011)");	
			maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
			maespaConfigConfileDat.setConfigTreezAlldiamDates("");
			maespaConfigConfileDat.setConfigTreezAlldiamValues(0.2);	
			maespaConfigConfileDat.setConfigTreezAlldiamValuesComment("!NA for grass as no trunk (trunk height =0)");	
			maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
			maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
			maespaConfigConfileDat.setConfigTreezAllhttrunkValues(0.01);	
			maespaConfigConfileDat.setConfigTreezAllhttrunkValuesComment("!No trunk.");	
			maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
			maespaConfigConfileDat.setConfigTreezAlllareaDates("");
			maespaConfigConfileDat.setConfigTreezAlllareaValues(totalLeafAreaIndex);
			maespaConfigConfileDat.setConfigTreezAlllareaValuesComment("!Total leaf area for GRASS is for BOX shaped 'tree'then is = LAI * plt area. LAI=7.13 (avg. from Bijoor et al 2014); area=25; => Total LA=178.25");	
			// new values from Andy
//			maespaConfigConfileDat.setConfigTreezht(30);
//			maespaConfigConfileDat.setConfigTreezpd(16);
//			maespaConfigConfileDat.setConfigTreez0ht(3);			
//			maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
//			maespaConfigConfileDat.setConfigTreezAllradxDates("");
//			maespaConfigConfileDat.setConfigTreezAllradxValues(0.5);			
//			maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
//			maespaConfigConfileDat.setConfigTreezAllradyDates("");
//			maespaConfigConfileDat.setConfigTreezAllradyValues(0.5);			
//			maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
//			maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
//			maespaConfigConfileDat.setConfigTreezAllhtcrownValues(1.6);			
//			maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
//			maespaConfigConfileDat.setConfigTreezAlldiamDates("");
//			maespaConfigConfileDat.setConfigTreezAlldiamValues(0.02);			
//			maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
//			maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
//			maespaConfigConfileDat.setConfigTreezAllhttrunkValues(0.5);			
//			maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
//			maespaConfigConfileDat.setConfigTreezAlllareaDates("");
//			maespaConfigConfileDat.setConfigTreezAlllareaValues(2.48);
//			maespaConfigConfileDat.setConfigTreeTitle("sINGLE TREE IN 1x1 metre plot.");
			
								
			//create phy file		 for new grass type	
			maespaConfigConfileDat.setConfigPhyTitle("Physiology file for Turf grass (mostly tall fescue)" + '\n' +'\n' +
					"This is the new file using parameters derived from RESPONSE CURVES and the g0 and g1 from the East side streets, rather than the west side streets. " + '\n' +				
					"&noages " + '\n');
			maespaConfigConfileDat.setConfigPhyNoagep(1);
			maespaConfigConfileDat.setConfigPhyPhenol(1.0);
			maespaConfigConfileDat.setConfigPhyAbsorpNolayers(1);	
			maespaConfigConfileDat.setConfigPhyRhosol("0.10	0.05	0.05		!(Soil Type=Asphalt) Soil reflectance in three wavebands (PAR, NIR, thermal) (observed, Levinson et al.2007, OKe, 1987)");
			maespaConfigConfileDat.setConfigPhyAtau(" 0.05 0.45 0.01	!Leaf transmittance 3 wavelengths (PAR, NIR, thermal) C3 grasses, from Katjacnik et al.(2014)");
			maespaConfigConfileDat.setConfigPhyArho("0.05	0.65	0.08		!Leaf reflectance 3 wavelengths C3 grasses, from Katjacnik et al.(2014)");
			maespaConfigConfileDat.setConfigPhyBbgsconNodates(1);
			maespaConfigConfileDat.setConfigPhycondunits("H2O");
			maespaConfigConfileDat.setConfigPhyTuzetg0(0.045);
			maespaConfigConfileDat.setConfigPhyTuzetg1(4.53);
			maespaConfigConfileDat.setConfigPhyTuzetsf(3.2);
			maespaConfigConfileDat.setConfigPhyTuzetPsiv(-1.9);
			maespaConfigConfileDat.setConfigPhyTuzetNsides(2);
			maespaConfigConfileDat.setConfigPhyTuzetWleaf(0.0102);
			maespaConfigConfileDat.setConfigPhyTuzetGamma(46);
			maespaConfigConfileDat.setConfigPhyMedlyng0(0.00);
			maespaConfigConfileDat.setConfigPhyMedlyng0Comment("(De Kauwe, 2015)");
			maespaConfigConfileDat.setConfigPhyMedlyng1(5.25);
			maespaConfigConfileDat.setConfigPhyMedlyng1Comment("(C3 grasses, from De Kauwe, 2015) (g1 must be for H2O of stomatal conductance)");
			maespaConfigConfileDat.setConfigPhyMedlynNsides(2);
			maespaConfigConfileDat.setConfigPhyMedlynWleaf(0.006);
			maespaConfigConfileDat.setConfigPhyMedlynWleafComment("(6mm) (Rademacher and Nelson 2001)");
			maespaConfigConfileDat.setConfigPhyMedlynGamma(57);
			maespaConfigConfileDat.setConfigPhyMedlynGammaComment(" (Brown and Morgan, 1980 @ 25 degrees)");
			maespaConfigConfileDat.setConfigPhyJmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyJmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyJmaxValues(80.95);
			maespaConfigConfileDat.setConfigPhyJmaxValuesComment("(Tall Fescue from Yu et al 2011)");
			maespaConfigConfileDat.setConfigPhyJmaxDates("01/03/"
					+ twoDigitYear);
			maespaConfigConfileDat.setConfigPhyVcmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyVcmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyVcmaxValues(36.14);
			maespaConfigConfileDat.setConfigPhyVcmaxValuesComment("(Tall Fescue from Yu et al 2011)");
			maespaConfigConfileDat.setConfigPhyVcmaxDates("01/03/"
					+ twoDigitYear);
			maespaConfigConfileDat.setConfigPhyJmaxparsTheta(0.7);
			maespaConfigConfileDat.setConfigPhyJmaxparsThetaComment("");
			maespaConfigConfileDat.setConfigPhyJmaxparsEavj(65300);
			maespaConfigConfileDat.setConfigPhyJmaxparsEavjComment("(Bernacchi et al 2001)");
			maespaConfigConfileDat.setConfigPhyJmaxparsEdvj(200000);
			maespaConfigConfileDat.setConfigPhyJmaxparsEdvjComment("(Medlyn et al 2005)");
			maespaConfigConfileDat.setConfigPhyJmaxparsDelsj(644.4338);
			maespaConfigConfileDat.setConfigPhyJmaxparsAjq(0.05);
			maespaConfigConfileDat.setConfigPhyJmaxparsAjqComment("(PAR curves) (PSICO2=Absorb*8*0.5)");
			maespaConfigConfileDat.setConfigPhyVcmaxparsEavc(73680);
			maespaConfigConfileDat.setConfigPhyVcmaxparsEavcComment("(Bernacchi et al 2001)");
			maespaConfigConfileDat.setConfigPhyVcmaxparsEdvc(0);
			maespaConfigConfileDat.setConfigPhyRdmaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNoages(1);
			maespaConfigConfileDat.setConfigPhyRdmaxconNodates(1);
			maespaConfigConfileDat.setConfigPhyRdmaxValues(0.6);
			maespaConfigConfileDat.setConfigPhyRdmaxValuesComment("(estimated for Tall Fescue from Yu et al.2011)");
			maespaConfigConfileDat.setConfigPhyRdmaxDates("01/03/"
					+ twoDigitYear);
			maespaConfigConfileDat.setConfigPhyRdparsRtemp(25.0);
			maespaConfigConfileDat.setConfigPhyRdparsQ10f(0.0575);
			maespaConfigConfileDat.setConfigPhyRdparsDayresp(0.6);
			maespaConfigConfileDat.setConfigPhyRdparsEffyrf(0.4);
			maespaConfigConfileDat.setConfigPhySlamaxconNolayers(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNoages(1);
			maespaConfigConfileDat.setConfigPhySlamaxconNodates(1);
			maespaConfigConfileDat.setConfigPhySlamaxValues(23.16);
			maespaConfigConfileDat.setConfigPhySlamaxValuesComment("!Specific leaf area (m2.kg-1) Average from Table 1 in Bijoor et al 2014 for 3 turfgrasses.");
			maespaConfigConfileDat.setConfigPhySlamaxDates("01/03/"
					+ twoDigitYear);		
			
			phyConfigFileStr = maespaConfigConfileDat.getPhyDataFile();
			
			//write str files		for new grass type
			maespaConfigConfileDat.setConfigStrTitle("Physiology file for grass (based on Olea europaea (Olive trees) at Smith Street )");
			maespaConfigConfileDat.setConfigStrCshape("BOX");
			maespaConfigConfileDat.setConfigStrLiaElp(1);
			maespaConfigConfileDat.setConfigStrLiaNalpha(1);
			maespaConfigConfileDat.setConfigStrLiaAvgang(45);
			
			maespaConfigConfileDat.setConfigStrLaddJleaf(0);
			maespaConfigConfileDat.setConfigStrLaddNoagec(1);
			maespaConfigConfileDat.setConfigStrLaddRandom(1.0);
			
			maespaConfigConfileDat.setConfigStrAeroExtwind(0);
			maespaConfigConfileDat.setConfigStrAllomCoefft(91.4114);
			maespaConfigConfileDat.setConfigStrAllomExpont(1.741);
			maespaConfigConfileDat.setConfigStrAllomWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllombCoefft(8.9126);
			maespaConfigConfileDat.setConfigStrAllombExpont(1.341);
			maespaConfigConfileDat.setConfigStrAllombWinterc(0.0);
			
			maespaConfigConfileDat.setConfigStrAllomrCoefft(20);
			maespaConfigConfileDat.setConfigStrAllomrExpont(2.0);
			maespaConfigConfileDat.setConfigStrAllomrRinterc(0.0);
			maespaConfigConfileDat.setConfigStrAllomrFrfrac(0.4);	
			
			strConfigFileStr = maespaConfigConfileDat.getStrDataFile();
			
			//also create ustorey.dat for new grass type
			Ustorey ustorey = new Ustorey();
			String ustoreyStr = ustorey.getSample();
			System.out.println("write ustorey.dat " + runDirectory + inputDirectory + "/" + USTOREY_FILE_NAME);
			common.createDirectory(runDirectory + inputDirectory);
			common.writeFile(ustoreyStr, runDirectory + inputDirectory + "/" + USTOREY_FILE_NAME);	
			maespaConfigConfileDat.setConfigIsimus(1);
			
		}
		else
		{
			//Other types, not configured yet.
		}
		
		
		maespaConfigConfileDat.writeTreeConfigFile(inputDirectory);

		// now they are different directories so they are always tree 1
		maespaConfigConfileDat.writePhyConfigFile(phyConfigFileStr, inputDirectory, 1);
		maespaConfigConfileDat.writeStrConfigFile(strConfigFileStr, inputDirectory, 1);
	}
	
	
	public int[][] getBuildingHeightArray()
	{
		return buildingHeightArray;
	}

	public void setBuildingHeightArray(int[][] buildingHeightArray)
	{
		this.buildingHeightArray = buildingHeightArray;
	}

	public int[][] getVegHeightArray()
	{
		return vegHeightArray;
	}

	public void setVegHeightArray(int[][] vegHeightArray)
	{
		this.vegHeightArray = vegHeightArray;
	}

	public int[][] getVegTypeArray()
	{
		return vegTypeArray;
	}

	public void setVegTypeArray(int[][] vegTypeArray)
	{
		this.vegTypeArray = vegTypeArray;
	}

//	public int[] getBuildingHeightArray1D()
//	{
//		return buildingHeightArray1D;
//	}
//
//	public void setBuildingHeightArray1D(int[] buildingHeightArray1D)
//	{
//		this.buildingHeightArray1D = buildingHeightArray1D;
//	}
//
//	public int[] getVegHeightArray1D()
//	{
//		return vegHeightArray1D;
//	}
//
//	public void setVegHeightArray1D(int[] vegHeightArray1D)
//	{
//		this.vegHeightArray1D = vegHeightArray1D;
//	}
//
//	public int[] getVegTypeArray1D()
//	{
//		return vegTypeArray1D;
//	}
//
//	public void setVegTypeArray1D(int[] vegTypeArray1D)
//	{
//		this.vegTypeArray1D = vegTypeArray1D;
//	}

	public double getConfigTreeMapGridSize()
	{
		return configTreeMapGridSize;
	}

	public void setConfigTreeMapGridSize(double configTreeMapGridSize)
	{
		this.configTreeMapGridSize = configTreeMapGridSize;
	}

	public double getBuildingHeightAverage()
	{
		return buildingHeightAverage;
	}

	public void setBuildingHeightAverage(double buildingHeightAverage)
	{
		this.buildingHeightAverage = buildingHeightAverage;
	}
	
	public double getTreeHeightAverage()
	{
		return treeHeightAverage;
	}

	public void setTreeHeightAverage(double treeHeightAverage)
	{
		this.treeHeightAverage = treeHeightAverage;
	}

	public int getHighestBuildingHeight()
	{
		return highestBuildingHeight;
	}

	public void setHighestBuildingHeight(int highestBuildingHeight)
	{
		this.highestBuildingHeight = highestBuildingHeight;
	}

	public String getRunDirectory()
	{
		return runDirectory;
	}

	public void setRunDirectory(String runDirectory)
	{
		this.runDirectory = runDirectory;
	}

	public String getForcingSource()
	{
		return forcingSource;
	}

	public void setForcingSource(String forcingSource)
	{
		this.forcingSource = forcingSource;
	}

//	public int[] getTreesArray1D()
//	{
//		return treesArray1D;
//	}
//
//	public void setTreesArray1D(int[] treesArray1D)
//	{
//		this.treesArray1D = treesArray1D;
//	}

	public int[][] getTreesArray()
	{
		return treesArray;
	}

	public void setTreesArray(int[][] treesArray)
	{
		this.treesArray = treesArray;
	}

	public void generateTreeConfigs(MaespaConfigConfileDat maespaConfigConfileDat, String inputDirectory, int numberOfTrees,
			TreeMap<String, Integer> configFileVariations, TUFBldVegHeights vegHeights, String dataTable, String configNumber,
			int configType, int differtialShadingValue, ArrayList<double[]> loadedMetData, String lat, String lon)
	{		
//		String lat = "37	47	0" ;
//		String lon = "144	58	0" ;

		
		
		//create confile.dat
		maespaConfigConfileDat.setConfigFileTitle("Single tree test");
		maespaConfigConfileDat.setConfigiohrly(1);
		maespaConfigConfileDat.setConfigiotutd(9);		
		maespaConfigConfileDat.setConfigioresp(0);
		maespaConfigConfileDat.setConfigiohist(0);
		maespaConfigConfileDat.setConfigIsunla(0);
		
		maespaConfigConfileDat.setConfigIowatbal(1);
		maespaConfigConfileDat.setConfigIopoints(1);

		maespaConfigConfileDat.setConfigStartdate(maespaConfigConfileDat.getStartDate());
		maespaConfigConfileDat.setConfigEnddate(maespaConfigConfileDat.getEndDate());
		
		maespaConfigConfileDat.setConfigItargets(1);
		maespaConfigConfileDat.setConfigNspecies(1);
		maespaConfigConfileDat.setConfigSpeciesnames("O.europaea");
		if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			maespaConfigConfileDat.setConfigSpeciesnames("L.confertus");
			maespaConfigConfileDat.setConfigFileTitle("Single tree L.confertus");
		}
		if (configType == TUFBldVegHeights.OLIVE_CONFIG_TYPE)
		{
			maespaConfigConfileDat.setConfigSpeciesnames("O.europaea");
			maespaConfigConfileDat.setConfigFileTitle("Single tree Olea Europaea in 1 x 1 m plot");
		}
		if (configType == TUFBldVegHeights.GRASS_CONFIG_TYPE  || configType == TUFBldVegHeights.IRRIGATED_GRASS_CONFIG_TYPE)
		{
			maespaConfigConfileDat.setConfigSpeciesnames("Turf grass");
			maespaConfigConfileDat.setConfigFileTitle("Single tree Grass in 1 x 1 m plot");
		}
		maespaConfigConfileDat.setConfigPhyfiles("phy1.dat");
		maespaConfigConfileDat.setConfigStrfiles("str1.dat");
		maespaConfigConfileDat.setConfigNolay(6);
		maespaConfigConfileDat.setConfigPplay(12);
		maespaConfigConfileDat.setConfigNzen(5);
		maespaConfigConfileDat.setConfigNaz(11);
		maespaConfigConfileDat.setConfigModelgs(4);
		maespaConfigConfileDat.setConfigModeljm(0);
		maespaConfigConfileDat.setConfigModelrd(0);
		maespaConfigConfileDat.setConfigModelss(0);
		maespaConfigConfileDat.setConfigItermax(100);

		maespaConfigConfileDat.setConfignotrees(numberOfTrees);
	
		maespaConfigConfileDat.generateFile();		
		maespaConfigConfileDat.writeConfigFile(inputDirectory);
		
		maespaConfigConfileDat.setConfigWatparsTitle("Example water balance parameters.");
		maespaConfigConfileDat.setConfigWatparskeepwet(0);
		maespaConfigConfileDat.setConfigWatparssimtsoil(1);
		maespaConfigConfileDat.setConfigWatparssimsoilevap(1);
		maespaConfigConfileDat.setConfigWatparsreassignrain(0);
		maespaConfigConfileDat.setConfigWatparsretfunction(1);
		maespaConfigConfileDat.setConfigWatparsequaluptake(0);
		maespaConfigConfileDat.setConfigWatparsusemeaset(0);
		maespaConfigConfileDat.setConfigWatparsusemeassw(0);
		maespaConfigConfileDat.setConfigWatparsusestand(0);	
		maespaConfigConfileDat.setConfigWatparsThroughfall(1.0);	
		maespaConfigConfileDat.setConfigWatparsrutterb(3.7);
		maespaConfigConfileDat.setConfigWatparsrutterd(0.002);
		maespaConfigConfileDat.setConfigWatparsmaxstorage(0.4);
		maespaConfigConfileDat.setConfigWatparsthroughfall(0.6);	
		maespaConfigConfileDat.setConfigWatparsexpinf(1.0);	
		maespaConfigConfileDat.setConfigWatparsrootresfrac(0.4);
		maespaConfigConfileDat.setConfigWatparsrootrad(0.0001);
		maespaConfigConfileDat.setConfigWatparsrootdens(0.5e6);
		maespaConfigConfileDat.setConfigWatparsrootmasstot(1000);
		maespaConfigConfileDat.setConfigWatparsnrootlayer(7);
		maespaConfigConfileDat.setConfigWatparsrootbeta(0.9);	
		maespaConfigConfileDat.setConfigWatparsminrootwp(-3.0);
		maespaConfigConfileDat.setConfigWatparsminleafwp(-10.0);
		maespaConfigConfileDat.setConfigWatparsplantk(3);	
		maespaConfigConfileDat.setConfigWatparsbpar(2.79);
		maespaConfigConfileDat.setConfigWatparspsie(-0.00068);
		maespaConfigConfileDat.setConfigWatparsksat(264.3);	
		maespaConfigConfileDat.setConfigWatparsnlayer(10);
		maespaConfigConfileDat.setConfigWatparslaythick(0.1);
		maespaConfigConfileDat.setConfigWatparsporefrac(0.2);
		maespaConfigConfileDat.setConfigWatparsdrainlimit(0.0);
		maespaConfigConfileDat.setConfigWatparsfracorganic(0.0);	
		maespaConfigConfileDat.setConfigWatparsinitwater(0.06);
		maespaConfigConfileDat.setConfigWatparssoiltemp(15);
			
		maespaConfigConfileDat.setConfigWatparsdrythickmin(0.01);
		maespaConfigConfileDat.setConfigWatparstortpar(0.66);
		
		String watparsConfigFile = maespaConfigConfileDat.getWatparsDataFile();
		if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			watparsConfigFile = maespaConfigConfileDat.getWatparsDataFile(configType);
		}
		if (configType == TUFBldVegHeights.OLIVE_CONFIG_TYPE)
		{
			watparsConfigFile = maespaConfigConfileDat.getWatparsDataFile(configType);
		}
		if (configType == TUFBldVegHeights.GRASS_CONFIG_TYPE || configType == TUFBldVegHeights.IRRIGATED_GRASS_CONFIG_TYPE)
		{
			watparsConfigFile = maespaConfigConfileDat.getWatparsDataFile(configType);
		}

		maespaConfigConfileDat.writeWatbalConfigFile(inputDirectory);		
		maespaConfigConfileDat.writeWatparsConfigFile(watparsConfigFile, inputDirectory);
		


		//first generate the header to the forcing data file
		String fileTemplate = MaespaConfigMetDat.getMetDatTemplate(maespaConfigConfileDat.getStartDate(), maespaConfigConfileDat.getEndDate(), 
				differtialShadingValue, lat, lon);



		// cache met files since they take a while to generate
		MaespaSingleTreeMetData metData = null;
//		if (differtialShadingValue == MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE && cachedMetFileDiffuse == null )
//		{
		metData = new MaespaSingleTreeMetData(fileTemplate, maespaConfigConfileDat.getYear(), maespaConfigConfileDat.getDay(), 
					maespaConfigConfileDat.getNumberOfDays() + 1,dataTable, runDirectory, configNumber, differtialShadingValue, configType, loadedMetData);

		
		String outputName = "met.dat";		
		maespaConfigConfileDat.writeGenericConfigFile(metData.getMetDataFileContents(), outputName, inputDirectory);
		
		//generate points file
		double gridMiddle = maespaConfigConfileDat.getConfigTreeMapGridSize()/2;
		
		maespaConfigConfileDat.setPointsCoords1(gridMiddle
				+ " "
				+ gridMiddle
				+ " 0.1");
		//maespaConfigConfileDat.setPointsCoords2("0.5 0.5 0.5");
		maespaConfigConfileDat.setPointsNoPoints("1");		
		maespaConfigConfileDat.setPointsInputType("1");
		maespaConfigConfileDat.setPointsTransectAngle("45");
		maespaConfigConfileDat.setPointsTransectSpacing("0.2");	
		maespaConfigConfileDat.setPointsZHeight("1");
		maespaConfigConfileDat.writePointsConfigFile(inputDirectory, maespaConfigConfileDat);
	}
	
}
