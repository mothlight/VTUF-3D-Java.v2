package VTUF3D.ConfigMaker;

import java.util.ArrayList;
import java.util.Random;

public class MakeConfigsForClusters_heatwave
{
	String clusterDir = "/home/kerryn/git/2020-05-EGU/umap/";
	String clusterFileName = "clusters_2000_5_stats.csv";
	String clusterMel5Dir = "/home/kerryn/git/2020-05-EGU/";
	String clusterMel5FileName = "MelbourneFeatures5-clustering.csv";
	
	Common common = new Common();
	String tab = "\t";
	String linefeed = "\n";
	
	Random randomx = new Random(5);
	Random randomy = new Random(66);
	
	public final static int WIDTH = 20;
	public final static int HEIGHT = 20;
	public final static int TOTAL_AREA = WIDTH*HEIGHT;
	
	public final static int CLUSTER = 0;
	public final static int BLD = 4;
	public final static int VEG_HT = 8;
	public final static int BLD_HT = 12;
	public final static int CONCRETE = 16;
	public final static int ROAD = 20;
	public final static int GRASS = 24;
	public final static int TREE = 28;
	public final static int WATER = 32;
	public final static int BARE = 36;
	public final static int TSURF = 40;
	public final static int TAIR = 44;
	
	ArrayList<Integer> clusters = new ArrayList<Integer>();
	ArrayList<Double> buildings = new ArrayList<Double>();
	ArrayList<Double> vegetationHeights = new ArrayList<Double>();
	ArrayList<Double> buildingHeights = new ArrayList<Double>();
	ArrayList<Double> concretes = new ArrayList<Double>();
	ArrayList<Double> roads = new ArrayList<Double>();
	ArrayList<Double> grasses = new ArrayList<Double>();
	ArrayList<Double> trees = new ArrayList<Double>();
	ArrayList<Double> waters = new ArrayList<Double>();
	ArrayList<Double> bares = new ArrayList<Double>();
	ArrayList<Double> tsurfs = new ArrayList<Double>();
	ArrayList<Double> tairss = new ArrayList<Double>();
	
	String runDirectory = "/home/kerryn/git/2020-06-UrbanPlumber/VTUF-3D/AU-Preston2004";
    private double gridSizeInMeters=5;
    private String forcingSource="Preston";
	String latForMaespa = "37	47	0" ;
	String lonForMaespa = "144	58	0" ;
	double lat = -37.7;	
	int year = 2004;	
	int start = 42;
	int end = 46;
	String sourceDirectory = "/home/kerryn/workspace/TUF-3DRadiationOnly2/";
	String sourceExe = "TUF3Dradiation";
	
	String maespaExeDir = "/home/kerryn/workspace/TUF-3DRadiationOnly2/maespa/";
	String maespaExe = "maespa.out";
	
	final static int ALL_ROAD = 1;
	final static int ALL_GRASS = 2;
	final static int ALL_TREE = 3;
	final static int ALL_BUILDING = 4;
	final static int GRASS_ROAD50_50 = 5;
	
	double forcingHeight = 40;
//	String configDomainsDirectory = "/home/kerryn/workspace/TUF3D_Graphs/src/au/edu/monash/ges/tuf3d/config/DomainFiles/";
//	String configDomainFilename = "PrestonBase8";

	String scriptFilename = "run_model_heatwave.sh";		
	String baseDir = "/media/kerryn/87d9469d-56aa-4a1f-a62d-5f03d7599bbf/Data/VTUF-3D/MCZTests4/";
	String scriptFile = baseDir + scriptFilename;

	public static void main(String[] args)
	{
		MakeConfigsForClusters_heatwave mc = new MakeConfigsForClusters_heatwave();
//		mc.process();
		
//		int gridSize = 200;
//		double aveHt = 2.0;
//		double treePercent = 10;
////		int items = 100;
//		int items = (int)Math.round(gridSize * treePercent /100.0);
//		
//		int[] heights = mc.getHeightsForAverage(aveHt,items,gridSize);

		
		mc.generateClusters();
//		mc.generateClustersFromMelbourne5();
		
	}
	
	public void generateClustersFromMelbourne5()
	{	
		//  0   1       2       3   4   5   6
		//  1	VH6.0	BH1.0	B10	R20	T50	G30


		int count = 0;
		ArrayList<String> clusterDataFileContents = common.readLargerTextFileAlternateToArray(clusterMel5Dir + clusterMel5FileName);
		for (String line : clusterDataFileContents)
		{
			String[] lineSplit = Common.splitOnWhitespace(line);
			System.out.println(line);
			count ++;
			if (count < 15869)
			{
				continue;
			}
			
			String numInClusterStr = lineSplit[0];
			String vegHtStr = lineSplit[1];
			String bldHtStr = lineSplit[2];
			String buildingStr = lineSplit[3];
			String roadStr = lineSplit[4];
			String treeStr = lineSplit[5];
			String grassStr = lineSplit[6];
			
			int numInCluster = Integer.parseInt(numInClusterStr);
			double vegHt = Double.parseDouble(vegHtStr.replace("VH", ""));
			double bldHt = Double.parseDouble(bldHtStr.replace("BH", ""));
			double building = Double.parseDouble(buildingStr.replace("B", ""));
			double road = Double.parseDouble(roadStr.replace("R", ""));
			double tree = Double.parseDouble(treeStr.replace("T", ""));
			double grass = Double.parseDouble(grassStr.replace("G", ""));
			
//			int cluster = common.getIntFromArray(lineSplit, 0);
//			double building = common.getDoubleFromArray(lineSplit, 4);
//			double vegHt = common.getDoubleFromArray(lineSplit, 8);
//			double bldHt = common.getDoubleFromArray(lineSplit, 12);
//			double concrete = common.getDoubleFromArray(lineSplit, 16);
//			double road = common.getDoubleFromArray(lineSplit, 20);
//			double grass = common.getDoubleFromArray(lineSplit, 24);
//			double tree = common.getDoubleFromArray(lineSplit, 28);
//			double water = common.getDoubleFromArray(lineSplit, 32);
//			double bare = common.getDoubleFromArray(lineSplit, 36);
			
			int grassInt = (int) Math.round(grass);
			int roadInt = (int) Math.round(road);
			int buildingInt = (int) Math.round(building);
			int treeInt = (int) Math.round(tree);
			
//			int bareInt = (int) Math.round(bare);
//			int waterInt = (int) Math.round(water);
			
			int vegHtInt = (int) Math.round(vegHt);
			int bldHtInt = (int) Math.round(bldHt);
			
			String runName = "Cluster_" + numInCluster
					+ "_Grass_"
					+ grassInt
					+ "_Road_"
					+ roadInt
					+ "_Building_"
					+ buildingInt
					+ "_Tree_"
					+ treeInt
					
//					+ "_Bare_"
//					+ bareInt
					
//					+ "_Water_"
//					+ waterInt
					
					+ "_VegHt_"
					+ vegHtInt
					
					+ "_BldHt_"
					+ bldHtInt
					
					;
			System.out.println(runName);
			String configDir = baseDir + runName + "/";
			createDomainFromGrassAndRoadAndBuildingAndTrees(configDir, roadInt, buildingInt, treeInt, bldHt, vegHt);
			makeConfig(runName, baseDir);
			updateScriptFile(runName);
			
		}
	}
	
	
	public void generateClusters()
	{	
		// 0        1       2       3       4            5      6           7           8           9           10          11          12          13
		// Cluster	count	Bld_min	Bld_max	Bld_mean	Bld_sd	VegHt_min	VegHt_max	VegHt_mean	VegHt_sd	BldHt_min	BldHt_max	BldHt_mean	BldHt_sd	
		//14            15              16              17           18          19         20          21      22           23         24           25
		//Concrete_min	Concrete_max	Concrete_mean	Concrete_sd	Road_min	Road_max	Road_mean	Road_sd	Grass_min	Grass_max	Grass_mean	Grass_sd	
		//26        27          28          29      30          31          32          33          34          35          36          37
		//Tree_min	Tree_max	Tree_mean	Tree_sd	Water_min	Water_max	Water_mean	Water_sd	Bare_min	Bare_max	Bare_mean	Bare_sd	
		//Tsurf_min	Tsurf_max	Tsurf_mean	Tsurf_sd	Tair_min	Tair_max	Tair_mean	Tair_sd

		
		ArrayList<String> clusterDataFileContents = common.readLargerTextFileAlternateToArray(clusterDir + clusterFileName);
		for (String line : clusterDataFileContents)
		{
			if (line.startsWith("Cluster"))
			{
				continue;
			}
			String[] lineSplit = Common.splitOnWhitespace(line);
			int cluster = common.getIntFromArray(lineSplit, 0);
			double building = common.getDoubleFromArray(lineSplit, 4);
			double vegHt = common.getDoubleFromArray(lineSplit, 8);
			double bldHt = common.getDoubleFromArray(lineSplit, 12);
			double concrete = common.getDoubleFromArray(lineSplit, 16);
			double road = common.getDoubleFromArray(lineSplit, 20);
			double grass = common.getDoubleFromArray(lineSplit, 24);
			double tree = common.getDoubleFromArray(lineSplit, 28);
			double water = common.getDoubleFromArray(lineSplit, 32);
			double bare = common.getDoubleFromArray(lineSplit, 36);
			
			int grassInt = (int) Math.round(grass+bare+water);
			int roadInt = (int) Math.round(road+concrete);
			int buildingInt = (int) Math.round(building);
			int treeInt = (int) Math.round(tree);
			
			int bareInt = (int) Math.round(bare);
			int waterInt = (int) Math.round(water);
			
			int vegHtInt = (int) Math.round(vegHt);
			int bldHtInt = (int) Math.round(bldHt);
			
			String runName = "Cluster_" + cluster
					+ "_Grass_"
					+ grassInt
					+ "_Road_"
					+ roadInt
					+ "_Building_"
					+ buildingInt
					+ "_Tree_"
					+ treeInt
					
					+ "_Bare_"
					+ bareInt
					
					+ "_Water_"
					+ waterInt
					
					+ "_VegHt_"
					+ vegHtInt
					
					+ "_BldHt_"
					+ bldHtInt
					
					;
			System.out.println(runName);
			String configDir = baseDir + runName + "/";
//			createDomainFromGrassAndRoadAndBuildingAndTrees(configDir, roadInt, buildingInt, treeInt, bldHt, vegHt);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
			
		}
	}
	

	
	public void process()
	{

		String runName = "100Road";
		String configDir = baseDir + runName + "/";
		int domainType = ALL_ROAD;		
//		mc.createDomain(configDir, domainType);
//		mc.makeConfig(runName, baseDir);
		
//		runName = "100Grass";
//		configDir = baseDir + runName + "/";
//		domainType = ALL_GRASS;		
//		mc.createDomain(configDir, domainType);
//		mc.makeConfig(runName, baseDir);
		
//		runName = "100Tree";
//		configDir = baseDir + runName + "/";
//		domainType = ALL_TREE;		
//		mc.createDomain(configDir, domainType);
//		mc.makeConfig(runName, baseDir);
		
//		runName = "100Building";
//		configDir = baseDir + runName + "/";
//		domainType = ALL_BUILDING;		
//		mc.createDomain(configDir, domainType);
//		mc.makeConfig(runName, baseDir);
		
//		runName = "50_50_Grass_Road";
//		configDir = baseDir + runName + "/";
//		domainType = GRASS_ROAD50_50;		
//		mc.createDomain(configDir, domainType);
//		mc.makeConfig(runName, baseDir);
		
//		//now add grass/road mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int grass = 100;
//			int road = i;
//			grass = grass - road;
//			System.out.println(grass + " " + road );
//			
//			runName = "Grass_"
//					+ grass
//					+ "_Road_"
//					+ road;
//			configDir = baseDir + runName + "/";	
//			createDomainFromGrassAndRoad(configDir, road);
//			makeConfig(runName, baseDir);
//			
//			updateScriptFile(runName);
//			
//		}
//		
//		//next a grass/tree mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int grass = 100;
//			int tree = i;
//			grass = grass - tree;
//			System.out.println(grass + " " + tree );
//			
//			runName = "Grass_"
//					+ grass
//					+ "_Tree_"
//					+ tree;
//			configDir = baseDir + runName + "/";
//			createDomainFromGrassAndTree(configDir, tree);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
//		}
//		
//		//next a grass/building mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int grass = 100;
//			int building = i;
//			grass = grass - building;
//			System.out.println(grass + " " + building );
//			
//			runName = "Grass_"
//					+ grass
//					+ "_Building_"
//					+ building;
//			configDir = baseDir + runName + "/";	
//			createDomainFromGrassAndBuilding(configDir, building);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
//		}
//		
//		//next a road/building mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int road = 100;
//			int building = i;
//			road = road - building;
//			System.out.println(road + " " + building );
//			
//			runName = "Road_"
//					+ road
//					+ "_Building_"
//					+ building;
//			configDir = baseDir + runName + "/";	
//			createDomainFromRoadAndBuilding(configDir, building);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
//		}
//		
//		//next a road/tree mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int road = 100;
//			int tree = i;
//			road = road - tree;
//			System.out.println(road + " " + tree );
//			
//			runName = "Road_"
//					+ road
//					+ "_Tree_"
//					+ tree;
//			configDir = baseDir + runName + "/";	
//			createDomainFromRoadAndTree(configDir, tree);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
//		}
//		
//		//next a tree/building mix
//		for (int i=5;i<100;i=i+5)
//		{
//			System.out.println(i);
//			int tree = 100;
//			int building = i;
//			tree = tree - building;
//			System.out.println(tree + " " + building );
//			
//			runName = "Tree_"
//					+ tree
//					+ "_Building_"
//					+ building;
//			configDir = baseDir + runName + "/";	
//			createDomainFromTreeAndBuilding(configDir, building);
//			makeConfig(runName, baseDir);
//			updateScriptFile(runName);
//		}
//		
//		//next a grass and road/tree mix
//		for (int i=5;i<100;i=i+5)
//		{
//			for (int j=5;j<100;j=j+5)
//			{
////				System.out.println(i + " " + j) ;
//				int grass = 100;
//				int road = i;//what it should be is road + tree can't be more than 95%
//				int tree = j;
//				grass = grass - tree - road;
//				if (road + tree > 95)
//				{
//					continue;
//				}
//				System.out.println(grass + " " + road + " " + tree );
//				
//				runName = "Grass_"
//						+ grass
//						+ "_Road_"
//						+ road
//						+ "_Tree_"
//						+ tree;
//				configDir = baseDir + runName + "/";
//				createDomainFromGrassAndRoadAndTree(configDir, road, tree);
//				makeConfig(runName, baseDir);
//				updateScriptFile(runName);
//			}
//
//		}
		
		
//		//next a grass and road/building mix
//		for (int i=5;i<100;i=i+5)
//		{
//			for (int j=5;j<100;j=j+5)
//			{
////				System.out.println(i + " " + j) ;
//				int grass = 100;
//				int road = i;//what it should be is road + tree can't be more than 95%
//				int building = j;
//				grass = grass - building - road;
//				if (road + building > 95)
//				{
//					continue;
//				}
//				System.out.println(grass + " " + road + " " + building );
//				
//				runName = "Grass_"
//						+ grass
//						+ "_Road_"
//						+ road
//						+ "_Building_"
//						+ building;
//				configDir = baseDir + runName + "/";
//				createDomainFromGrassAndRoadAndBuilding(configDir, road, building);
//				makeConfig(runName, baseDir);
//				updateScriptFile(runName);
//			}
//		}		
		
//		//next a grass and building/tree mix
//		for (int i=5;i<100;i=i+5)
//		{
//			for (int j=5;j<100;j=j+5)
//			{
////				System.out.println(i + " " + j) ;
//				int grass = 100;
//				int building = i;
//				int tree = j;
//				grass = grass - tree - building;
//				if (building + tree > 95)
//				{
//					continue;
//				}
//				System.out.println(grass + " " + building + " " + tree );
//				
//				runName = "Grass_"
//						+ grass
//						+ "_Building_"
//						+ building
//						+ "_Tree_"
//						+ tree;
//				configDir = baseDir + runName + "/";
//				createDomainFromGrassAndBuildingAndTree(configDir, building, tree);
//				makeConfig(runName, baseDir);
//				updateScriptFile(runName);
//			}
//		}	
		
		//finally, start with grass then add mixes of road/buildings/trees
		for (int i=5;i<100;i=i+5)
		{
			for (int j=5;j<100;j=j+5)
			{
				for (int k=5;k<100;k=k+5)
				{
					int grass = 100;
					int road = i;
					int building = j;
					int trees = k;
					grass = grass - building - road - trees;
					if (road + building + trees > 95)
					{
						continue;
					}
					System.out.println(grass + " " + road + " " + building + " " + trees );
					
					runName = "Grass_"
							+ grass
							+ "_Road_"
							+ road
							+ "_Building_"
							+ building
							+ "_Tree_"
							+ trees
							;
					configDir = baseDir + runName + "/";
					createDomainFromGrassAndRoadAndBuildingAndTrees(configDir, road, building, trees);
					makeConfig(runName, baseDir);
					updateScriptFile(runName);
				}

			}
		}	

	}
	
	// start with all grass. fill the middle with road then replace grass with trees
	public void createDomainFromGrassAndRoadAndBuildingAndTrees(String dir, int roadPercent, int buildingPercent, int treePercent, double buildingHt, double vegHt)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();		
		domainData = addRoad(domainData,roadPercent);
		domainData = addTreesToGrass(domainData, treePercent,vegHt);
		domainData = addBuildingsToGrass(domainData, buildingPercent,buildingHt);
		processDomain(domainData,dir);
	}
	
	// start with all grass. fill the middle with road then replace grass with trees
	public void createDomainFromGrassAndRoadAndBuildingAndTrees(String dir, int roadPercent, int buildingPercent, int treePercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();		
		domainData = addRoad(domainData,roadPercent);
		domainData = addTreesToGrass(domainData, treePercent);
		domainData = addBuildingsToGrass(domainData, buildingPercent);
		processDomain(domainData,dir);
	}
	
	
	public void updateScriptFile(String runName)
	{
		String text = "cd "
				+ runName + linefeed
				+ "./TUF3Dradiation" + linefeed
				+ "zip out.zip *.out" + linefeed
				+ "rm *.out" + linefeed
				+ "cd .." + linefeed
				+ linefeed;			
		common.appendFile(text, scriptFile);
	}
	
	public void makeConfig(String runName, String baseDir)
	{
		ConfigurationMaker c = new ConfigurationMaker();
		c.runDirectory = baseDir + runName + "/";		
		c.configDomainsDirectory = c.runDirectory;
		c.configDomainFilename = "PrestonBase8";
		c.start=42;
		c.end=47;	
		c.create();
	}
	
	// start with all grass. fill the middle with road then replace grass with trees
	public void createDomainFromGrassAndRoadAndTree(String dir, int roadPercent, int treePercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();		
		domainData = addRoad(domainData,roadPercent);
		domainData = addTreesToGrass(domainData, treePercent);
		processDomain(domainData,dir);
	}
	
	
	public void createDomainFromGrassAndBuildingAndTree(String dir, int buildingPercent, int treePercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();	
		domainData = addBuildingsToGrass(domainData, buildingPercent);		
		domainData = addTreesToGrass(domainData,treePercent);		
		processDomain(domainData,dir);
	}
	
	// start with all grass. fill the middle with road then replace grass with buildings
	public void createDomainFromGrassAndRoadAndBuilding(String dir, int roadPercent, int buildingPercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();		
		domainData = addRoad(domainData,roadPercent);
		domainData = addBuildingsToGrass(domainData, buildingPercent);
		processDomain(domainData,dir);
	}
	
	public void createDomainFromGrassAndTree(String dir, int treePercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();		
		domainData = addTrees(domainData,treePercent);
		processDomain(domainData,dir);
	}
	
	public void createDomainFromGrassAndRoad(String dir, int roadPercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();
		
		domainData = addRoad(domainData,roadPercent);
		
		processDomain(domainData,dir);
	}
	
	public void createDomainFromGrassAndBuilding(String dir, int buildingPercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllGrass();	
		domainData = addBuildings(domainData,buildingPercent);
		processDomain(domainData,dir);
	}
	
	public void createDomainFromRoadAndBuilding(String dir, int buildingPercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllRoad();	
		domainData = addBuildings(domainData,buildingPercent);
		processDomain(domainData,dir);
	}
	
	public void createDomainFromTreeAndBuilding(String dir, int buildingPercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllTree();	
		domainData = addBuildings(domainData,buildingPercent);
		processDomain(domainData,dir);
	}
	
	public void createDomainFromRoadAndTree(String dir, int treePercent)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		domainData = getAllRoad();	
		domainData = addTrees(domainData,treePercent);
		processDomain(domainData,dir);
	}
	
	public void createDomain(String dir, int domainType)
	{
		ArrayList<int[][]> domainData = new ArrayList<int[][]>();
		
		if (domainType == ALL_ROAD)
		{
			domainData = getAllRoad();
		}
		if (domainType == ALL_GRASS)
		{
			domainData = getAllGrass();
		}
		if (domainType == ALL_TREE)
		{
			domainData = getAllTree();
		}
		if (domainType == ALL_BUILDING)
		{
			domainData = getAllBuilding();
		}
		
		if (domainType == GRASS_ROAD50_50)
		{
			domainData = getAllGrass();
			domainData = addRoad(domainData,50);
		}
		
		processDomain(domainData,dir);
	}
	
	public void processDomain(ArrayList<int[][]> domainData, String dir)
	{
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		common.createDirectory(dir);
		String outputFile1 = dir + "PrestonBase8" + "_1.csv";
		String outputFile2 = dir + "PrestonBase8" + "_2.csv";
		String outputFile3 = dir + "PrestonBase8" + "_3.csv";
		String outputFile4 = dir + "PrestonBase8" + "_4.csv";
		createDomainFiles(buildingHts, vegetationHts, vegetationTypes, treeLocations, outputFile1, outputFile2, outputFile3, outputFile4);
	}
	
	public ArrayList<int[][]> addBuildings(ArrayList<int[][]> domainData, int percent)
	{
		
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
//		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
//		vegetationTypes_temp[0][0] = 0;
		
		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
		buildingHts_temp = fillArray(buildingHts_temp, 1);
//		buildingHts_temp[0][0] = 1;
		
		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
//		vegetationHts_temp[0][0] = 0;
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
//		treeLocations_temp = fillArraySequential(treeLocations_temp);
//		treeLocations_temp[0][0] = 0;
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.));
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees down the middle
		int count = 0;
//		int center = WIDTH/2;
		
		
//		for (int i=0;i<WIDTH;i++)
//		{
//			buildingHts[i][center] = 0;
//			vegetationHts[i][center] = 0;
//			vegetationTypes[i][center] = 0;
//			treeLocations[i][center] = 0;
//			if (count > neededArea)
//			{
//				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
//				returnValue.add(buildingHts);
//				returnValue.add(vegetationHts);
//				returnValue.add(vegetationTypes);
//				returnValue.add(treeLocations);		
//				return returnValue;
//			}
//			count ++;
//		}
		int left = 0;
		int right = WIDTH;

		
		while (true)
		{
			
			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][left] = buildingHts_temp[i][left];
				vegetationHts[i][left] = vegetationHts_temp[i][left];
				vegetationTypes[i][left] = vegetationTypes_temp[i][left];
				treeLocations[i][left] = treeLocations_temp[i][left];
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}
			left = left + 1;

			right = right - 1;
			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][right] = buildingHts_temp[i][right];
				vegetationHts[i][right] = vegetationHts_temp[i][right];
				vegetationTypes[i][right] = vegetationTypes_temp[i][right];
				treeLocations[i][right] = treeLocations_temp[i][right];
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}
			
			
		
		}
	}
	
	public int[] getHeightsForAverage(double aveHt, int items, int totalGrids)
	{
		int[] heightsForAverages = new int[items];
		
		double totalHeights = aveHt * items /gridSizeInMeters ;
		double heightPerItem = totalHeights / items;
		int heightPerItemFloor = (int)Math.round(Math.floor(heightPerItem));
		if (heightPerItemFloor == 0)
		{
			heightPerItemFloor = 1;
		}
		double remainingAllocatedHeight = totalHeights;	
		
		//allocate heights to all grids
		for (int i=0;i<items;i++)
		{
			heightsForAverages[i] = heightPerItemFloor;
			remainingAllocatedHeight -= heightPerItemFloor;
			if (remainingAllocatedHeight < 1)
			{
				break;
			}
		}
		//use up extra heights
		for (int i=0;i<remainingAllocatedHeight;i++)
		{
			int randomGrid = randomx.nextInt(items);
			heightsForAverages[randomGrid] = heightsForAverages[randomGrid] + 1;
		}
		
		//calculate actual average
		double heightTotal = 0;
		for (int i=0;i<items;i++)
		{
			heightTotal += heightsForAverages[i] * gridSizeInMeters;
		}
		double calculatedAve = heightTotal / items ;
		System.out.println("calculated=" +  calculatedAve + " intended=" + aveHt);
		
		return shuffleArray(heightsForAverages);
	}
	
	public int[] shuffleArray(int[] array)
	{
		int index;
		Random random = new Random();
		for (int i=array.length-1;i>0;i--)
		{
			index = random.nextInt(i+1);
			if (index != i)
			{
				array[index] ^= array[i];
				array[i] ^= array[index];
				array[index] ^= array[i];
			}
		}
		return array;
	}
	
	public ArrayList<int[][]> addTreesToGrass(ArrayList<int[][]> domainData, int percent, double vegHt)
	{

		int loops = 0;
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
		
		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
		
//		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
		treeLocations_temp = fillArraySequential(treeLocations_temp, 300);
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.)) - 1;
		
		int gridSize = WIDTH*HEIGHT;
		double aveHt = vegHt;
		double treePercent = percent;
		int items = (int)Math.round(gridSize * treePercent /100.0);
		
		System.out.println("calc trees");
		int[] treeHeightsForAverages = getHeightsForAverage(aveHt,items,gridSize);
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees scattered through the grass.
		// endlessly iterate (by 7) through area, replace grass with tree
		int count = 0;
		int i=1;
		int j=1;
		while (true)
		{	
			loops ++;			
			if (loops > 10000)
			{
				System.out.println("giving up, too many loops");
				count = neededArea+1;
			}
		
			if (count > neededArea)
			{
				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
				returnValue.add(buildingHts);
				returnValue.add(vegetationHts);
				returnValue.add(vegetationTypes);
				returnValue.add(treeLocations);		
				return returnValue;
			}
									
			i += randomx.nextInt(4);
			j += randomy.nextInt(3);
			
			if (i>=WIDTH)
			{
				i = i - WIDTH;
			}
			if (j>=HEIGHT)
			{
				j = j - HEIGHT;
			}
			
			int x = i;
			int y = j;
						
			if (vegetationTypes[x][y] == TUFBldVegHeights.GRASS_CONFIG_TYPE)
			{
				buildingHts[x][y] = buildingHts_temp[x][y];
//				vegetationHts[x][y] = vegetationHts_temp[x][y];
				vegetationHts[x][y] = treeHeightsForAverages[count];
				vegetationTypes[x][y] = vegetationTypes_temp[x][y];
				treeLocations[x][y] = treeLocations_temp[x][y];

				count ++;
			}
			else
			{
				continue;
			}
		}
	}
	
	public ArrayList<int[][]> addTreesToGrass(ArrayList<int[][]> domainData, int percent)
	{
		Random randomx = new Random(5);
		Random randomy = new Random(66);
		int loops = 0;
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
//		vegetationTypes_temp[0][0] = 0;
		
		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
//		buildingHts_temp[0][0] = 1;
		
		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
//		vegetationHts_temp[0][0] = 0;
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
		treeLocations_temp = fillArraySequential(treeLocations_temp, 300);
//		treeLocations_temp[0][0] = 0;
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.)) - 1;
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees scattered through the grass.
		// endlessly iterate (by 7) through area, replace grass with tree
		int count = 0;
		int i=1;
		int j=1;
		while (true)
		{	
			loops ++;
			
			if (loops > 10000)
			{
				System.out.println("giving up, too many loops");
				count = neededArea+1;
			}
		
			if (count > neededArea)
			{
				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
				returnValue.add(buildingHts);
				returnValue.add(vegetationHts);
				returnValue.add(vegetationTypes);
				returnValue.add(treeLocations);		
				return returnValue;
			}
			
			
			
			i += randomx.nextInt(4);
			j += randomy.nextInt(3);
			
			if (i>=WIDTH)
			{
				i = i - WIDTH;
			}
			if (j>=HEIGHT)
			{
				j = j - HEIGHT;
			}
			
			int x = i;
			int y = j;
			
//			System.out.println(x + " " + y + " " + count );
			
			if (vegetationTypes[x][y] == TUFBldVegHeights.GRASS_CONFIG_TYPE)
			{
				buildingHts[x][y] = buildingHts_temp[x][y];
				vegetationHts[x][y] = vegetationHts_temp[x][y];
				vegetationTypes[x][y] = vegetationTypes_temp[x][y];
				treeLocations[x][y] = treeLocations_temp[x][y];

				count ++;
			}
			else
			{
				continue;
			}
		}
	}
	
	public ArrayList<int[][]> addBuildingsToGrass(ArrayList<int[][]> domainData, int percent, double bldHt)
	{
		Random randomx = new Random(5);
		Random randomy = new Random(66);
		int loops = 0;
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
//		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
//		vegetationTypes_temp[0][0] = 0;
		
//		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
////		buildingHts_temp[0][0] = 1;
//		buildingHts_temp = fillArray(buildingHts_temp, 1);
		
		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
//		vegetationHts_temp[0][0] = 0;
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
//		treeLocations_temp = fillArraySequential(treeLocations_temp, 300);
//		treeLocations_temp[0][0] = 0;
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.)) - 1;
				
		int gridSize = WIDTH*HEIGHT;
		double aveHt = bldHt;
		double treePercent = percent;
		int items = (int)Math.round(gridSize * treePercent /100.0);
		
		System.out.println("calc buildings");
		int[] bldHeightsForAverages = getHeightsForAverage(aveHt,items,gridSize);
			
//		int[] bldHeightsForAverages = getHeightsForAverage(bldHt,neededArea,WIDTH*HEIGHT);
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees scattered through the grass.
		// endlessly iterate (by 7) through area, replace grass with building
		int count = 0;
		int i=1;
		int j=1;
		while (true)
		{	
			loops ++;
			
			if (loops > 10000)
			{
				System.out.println("giving up, too many loops");
				count = neededArea+1;
			}
		
			if (count > neededArea)
			{
				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
				returnValue.add(buildingHts);
				returnValue.add(vegetationHts);
				returnValue.add(vegetationTypes);
				returnValue.add(treeLocations);		
				return returnValue;
			}

			i += randomx.nextInt(4);
			j += randomy.nextInt(3);
			
			if (i>=WIDTH)
			{
				i = i - WIDTH;
			}
			if (j>=HEIGHT)
			{
				j = j - HEIGHT;
			}
			
			int x = i;
			int y = j;
			
//			System.out.println(x + " " + y + " " + count );
			
			if (vegetationTypes[x][y] == TUFBldVegHeights.GRASS_CONFIG_TYPE)
			{
//				buildingHts[x][y] = buildingHts_temp[x][y];
				buildingHts[x][y] = bldHeightsForAverages[count];
				vegetationHts[x][y] = vegetationHts_temp[x][y];
				vegetationTypes[x][y] = vegetationTypes_temp[x][y];
				treeLocations[x][y] = treeLocations_temp[x][y];

				count ++;
			}
			else
			{
				continue;
			}
		}
	}
	
	public ArrayList<int[][]> addBuildingsToGrass(ArrayList<int[][]> domainData, int percent)
	{
		Random randomx = new Random(5);
		Random randomy = new Random(66);
		int loops = 0;
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
//		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
//		vegetationTypes_temp[0][0] = 0;
		
		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
//		buildingHts_temp[0][0] = 1;
		buildingHts_temp = fillArray(buildingHts_temp, 1);
		
		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
//		vegetationHts_temp[0][0] = 0;
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
//		treeLocations_temp = fillArraySequential(treeLocations_temp, 300);
//		treeLocations_temp[0][0] = 0;
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.)) - 1;
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees scattered through the grass.
		// endlessly iterate (by 7) through area, replace grass with building
		int count = 0;
		int i=1;
		int j=1;
		while (true)
		{	
			loops ++;
			
			if (loops > 10000)
			{
				System.out.println("giving up, too many loops");
				count = neededArea+1;
			}
		
			if (count > neededArea)
			{
				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
				returnValue.add(buildingHts);
				returnValue.add(vegetationHts);
				returnValue.add(vegetationTypes);
				returnValue.add(treeLocations);		
				return returnValue;
			}

			i += randomx.nextInt(4);
			j += randomy.nextInt(3);
			
			if (i>=WIDTH)
			{
				i = i - WIDTH;
			}
			if (j>=HEIGHT)
			{
				j = j - HEIGHT;
			}
			
			int x = i;
			int y = j;
			
//			System.out.println(x + " " + y + " " + count );
			
			if (vegetationTypes[x][y] == TUFBldVegHeights.GRASS_CONFIG_TYPE)
			{
				buildingHts[x][y] = buildingHts_temp[x][y];
				vegetationHts[x][y] = vegetationHts_temp[x][y];
				vegetationTypes[x][y] = vegetationTypes_temp[x][y];
				treeLocations[x][y] = treeLocations_temp[x][y];

				count ++;
			}
			else
			{
				continue;
			}
		}
	}
	
	public ArrayList<int[][]> addTrees(ArrayList<int[][]> domainData, int percent)
	{
		
		int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
		vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
		vegetationTypes_temp[0][0] = 0;
		
		int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
		buildingHts_temp[0][0] = 1;
		
		int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
		vegetationHts_temp = fillArray(vegetationHts_temp, 1);
		vegetationHts_temp[0][0] = 0;
		
		int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
		treeLocations_temp = fillArraySequential(treeLocations_temp, 1);
		treeLocations_temp[0][0] = 0;
		
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.));
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add trees down the middle
		int count = 0;
		int center = WIDTH/2;
		
		
//		for (int i=0;i<WIDTH;i++)
//		{
//			buildingHts[i][center] = 0;
//			vegetationHts[i][center] = 0;
//			vegetationTypes[i][center] = 0;
//			treeLocations[i][center] = 0;
//			if (count > neededArea)
//			{
//				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
//				returnValue.add(buildingHts);
//				returnValue.add(vegetationHts);
//				returnValue.add(vegetationTypes);
//				returnValue.add(treeLocations);		
//				return returnValue;
//			}
//			count ++;
//		}
		int left = center;
		int right = center;

		
		while (true)
		{
			left = left - 1;
			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][left] = buildingHts_temp[i][left];
				vegetationHts[i][left] = vegetationHts_temp[i][left];
				vegetationTypes[i][left] = vegetationTypes_temp[i][left];
				treeLocations[i][left] = treeLocations_temp[i][left];
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}

			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][right] = buildingHts_temp[i][right];
				vegetationHts[i][right] = vegetationHts_temp[i][right];
				vegetationTypes[i][right] = vegetationTypes_temp[i][right];
				treeLocations[i][right] = treeLocations_temp[i][right];
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}
			right = right + 1;
			
		
		}
	}
	
	public ArrayList<int[][]> addRoad(ArrayList<int[][]> domainData, int percent)
	{
		int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.));
		
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		
		//add road down the middle
		int count = 0;
		int center = WIDTH/2;
		
		for (int i=0;i<WIDTH;i++)
		{
			buildingHts[i][center] = 0;
			vegetationHts[i][center] = 0;
			vegetationTypes[i][center] = 0;
			treeLocations[i][center] = 0;
			if (count > neededArea)
			{
				ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
				returnValue.add(buildingHts);
				returnValue.add(vegetationHts);
				returnValue.add(vegetationTypes);
				returnValue.add(treeLocations);		
				return returnValue;
			}
			count ++;
		}
		int left = center;
		int right = center;
		
		while (true)
		{
			left = left - 1;
			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][left] = 0;
				vegetationHts[i][left] = 0;
				vegetationTypes[i][left] = 0;
				treeLocations[i][left] = 0;
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}
			
			right = right + 1;
			for (int i=0;i<WIDTH;i++)
			{
				buildingHts[i][right] = 0;
				vegetationHts[i][right] = 0;
				vegetationTypes[i][right] = 0;
				treeLocations[i][right] = 0;
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				count ++;
			}
		}
		

		
//		int[][] vegetationTypes = new int[20][20];	
//		vegetationTypes = fillArray(vegetationTypes, TUFBldVegHeights.GRASS_CONFIG_TYPE);
//		vegetationTypes[0][0] = 0;
		
//		int[][] buildingHts = new int[20][20];
//		buildingHts[0][0] = 1;
		
//		int[][] vegetationHts = new int[20][20];
//		vegetationHts[0][0] = 1;
		
//		int[][] treeLocations = new int[20][20];
//		trees[0][0] = 1;
		
//		ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
//		returnValue.add(buildingHts);
//		returnValue.add(vegetationHts);
//		returnValue.add(vegetationTypes);
//		returnValue.add(treeLocations);		
//		return returnValue;
	}
	
	public ArrayList<int[][]> getAllGrass()
	{
		int[][] vegetationTypes = new int[WIDTH][HEIGHT];	
		vegetationTypes = fillArray(vegetationTypes, TUFBldVegHeights.GRASS_CONFIG_TYPE);
		vegetationTypes[0][0] = 0;
		
		int[][] buildingHts = new int[WIDTH][HEIGHT];
		buildingHts[0][0] = 1;
		
		int[][] vegetationHts = new int[WIDTH][HEIGHT];
//		vegetationHts[0][0] = 1;
		
		int[][] treeLocations = new int[WIDTH][HEIGHT];
//		trees[0][0] = 1;
		
		ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
		returnValue.add(buildingHts);
		returnValue.add(vegetationHts);
		returnValue.add(vegetationTypes);
		returnValue.add(treeLocations);
		
		return returnValue;
	}
	
	public ArrayList<int[][]> getAllTree()
	{
		int[][] vegetationTypes = new int[WIDTH][HEIGHT];	
		vegetationTypes = fillArray(vegetationTypes, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
		vegetationTypes[0][0] = 0;
		
		int[][] buildingHts = new int[WIDTH][HEIGHT];
		buildingHts[0][0] = 1;
		
		int[][] vegetationHts = new int[WIDTH][HEIGHT];
		vegetationHts = fillArray(vegetationHts, 1);
		vegetationHts[0][0] = 0;
		
		int[][] treeLocations = new int[WIDTH][HEIGHT];
		treeLocations = fillArraySequential(treeLocations, 1);
		treeLocations[0][0] = 0;
		
		ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
		returnValue.add(buildingHts);
		returnValue.add(vegetationHts);
		returnValue.add(vegetationTypes);
		returnValue.add(treeLocations);
		
		return returnValue;
	}
	
	public int[][] fillArraySequential(int[][] array, int start)
	{
		int count = start;
		for (int i=0;i<array.length;i++)
		{
			for (int j=0;j<array[0].length;j++)
			{
				array[i][j]=count;
				count ++;
			}
		}
		return array;
	}
	
	
	public int[][] fillArray(int[][] array, int fillValue)
	{
		for (int i=0;i<array.length;i++)
		{
			for (int j=0;j<array[0].length;j++)
			{
				array[i][j]=fillValue;
			}
		}
		return array;
	}
	
	public ArrayList<int[][]> getAllRoad()
	{
		int[][] buildingHts = new int[WIDTH][HEIGHT];
		buildingHts[0][0] = 1;
		
		int[][] vegetationHts = new int[WIDTH][HEIGHT];
//		vegetationHts[0][0] = 1;
		
		int[][] vegetationTypes = new int[WIDTH][HEIGHT];
//		vegetationTypes[0][0] = 1;
		vegetationTypes[19][0] = TUFBldVegHeights.GRASS_CONFIG_TYPE;
		
		
		int[][] treeLocations = new int[WIDTH][HEIGHT];
//		trees[0][0] = 1;
		
		ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
		returnValue.add(buildingHts);
		returnValue.add(vegetationHts);
		returnValue.add(vegetationTypes);
		returnValue.add(treeLocations);
		
		return returnValue;
	}
	
	public ArrayList<int[][]> getAllBuilding()
	{
		int[][] buildingHts = new int[WIDTH][HEIGHT];
		buildingHts = fillArray(buildingHts, 1);
		buildingHts[19][0] = 0;
		
		int[][] vegetationHts = new int[WIDTH][HEIGHT];
//		vegetationHts[0][0] = 1;
		
		int[][] vegetationTypes = new int[WIDTH][HEIGHT];
//		vegetationTypes[0][0] = 1;
		vegetationTypes[19][0] = TUFBldVegHeights.GRASS_CONFIG_TYPE;
		
		
		int[][] treeLocations = new int[WIDTH][HEIGHT];
//		trees[0][0] = 1;
		
		ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
		returnValue.add(buildingHts);
		returnValue.add(vegetationHts);
		returnValue.add(vegetationTypes);
		returnValue.add(treeLocations);
		
		return returnValue;
	}
	
	
	public void createDomainFiles(int[][] buildingHts, int[][] vegetationHts, int[][] vegetationTypes, int[][] trees,
			String outputFile1, String outputFile2, String outputFile3, String outputFile4)
	{
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		for (int i=0;i<WIDTH;i++)
		{
			for (int j=0;j<HEIGHT;j++)
			{
				sb1.append("\"" + buildingHts[i][j] + "\"");
				if (j!=19)
				{
					sb1.append(tab);
				}
				else
				{
					sb1.append(linefeed);
				}
				
				sb2.append("\"" + vegetationHts[i][j] + "\"");
				if (j!=19)
				{
					sb2.append(tab);
				}
				else
				{
					sb2.append(linefeed);
				}
				
				sb3.append("\"" + vegetationTypes[i][j] + "\"");
				if (j!=19)
				{
					sb3.append(tab);
				}
				else
				{
					sb3.append(linefeed);
				}
				
				sb4.append("\"" + trees[i][j] + "\"");
				if (j!=19)
				{
					sb4.append(tab);
				}
				else
				{
					sb4.append(linefeed);
				}
			}
		}
//		System.out.println(sb1.toString());
		
		common.writeFile(sb1.toString(), outputFile1);
		common.writeFile(sb2.toString(), outputFile2);
		common.writeFile(sb3.toString(), outputFile3);
		common.writeFile(sb4.toString(), outputFile4);
	}
}
	
//	public void process()
//	{
//		
//		readClusterFile();
//		
//		String[] types = new String[]{"buildings","vegHt","bldHt","concrete","road","grass","tree","water","bare","tsurf","tair"};
//		ArrayList<ArrayList<Double>> arrays = new ArrayList<ArrayList<Double>>();
//		arrays.add(buildings);
//		arrays.add(veget