package VTUF3D.ConfigMaker;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.TreeMap;

public class UrbanPlumberPhase2ConfigMaker
{

	String latForMaespa = "37	47	0" ;
	String lonForMaespa = "144	58	0" ;
	String maespaNorthSouth = "S";
	String maespaEastWest = "E";
	String maespaTzLong = "150";	
	String maespaStartDate ="31/12/03";
	String maespaEndDate ="31/12/03";
	
	Common common = new Common();
	public String sitesDirectory = "/home/kerryn/git/2021-05-UrbanPlumberPhase2/UrbanPlumberPhase2/Urban-PLUMBER_sites_txt/";
	public String runDirectory = "/tmp/runs3/";
	String scriptFilename = "run_model4.sh";		
	String scriptFile = runDirectory + scriptFilename;
	
	String maespaExeDir = "/home/kerryn/workspace/TUF-3DRadiationOnly2/maespa/";
	String maespaExe = "maespa.out";
	String sourceDirectory = "/home/kerryn/workspace/TUF-3DRadiationOnly2/";
	String sourceExe = "TUF3Dradiation";
	Random randomx = new Random(5);
	Random randomy = new Random(66);
	private double gridSizeInMeters=5;
	
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
	
	public static final String LATITUDE = "latitude";
	public static final String LONGITUDE = "longitude";
	public static final String GROUND_HEIGHT = "ground_height";
	public static final String MEASUREMENT_HEIGHT_ABOVE_GROUND = "measurement_height_above_ground";
	public static final String IMPERVIOUS_AREA_FRACTION = "impervious_area_fraction";
	public static final String TREE_AREA_FRACTION = "tree_area_fraction";
	public static final String GRASS_AREA_FRACTION = "grass_area_fraction";
	public static final String BARE_SOIL_AREA_FRACTION = "bare_soil_area_fraction";
	public static final String WATER_AREA_FRACTION = "water_area_fraction";
	public static final String ROOF_AREA_FRACTION = "roof_area_fraction";
	public static final String ROAD_AREA_FRACTION = "road_area_fraction";
	public static final String OTHER_PAVED_AREA_FRACTION = "other_paved_area_fraction";
	public static final String BUILDING_MEAN_HEIGHT = "building_mean_height";
	public static final String TREE_MEAN_HEIGHT = "tree_mean_height";
	public static final String ROUGHNESS_LENGTH_MOMENTUM = "roughness_length_momentum";
	public static final String DISPLACEMENT_HEIGHT = "displacement_height";
	public static final String CANYON_HEIGHT_WIDTH_RATIO = "canyon_height_width_ratio";
	public static final String WALL_TO_PLAN_AREA_RATIO = "wall_to_plan_area_ratio";
	public static final String AVERAGE_ALBEDO_AT_MIDDAY = "average_albedo_at_midday";
	public static final String RESIDENT_POPULATION_DENSITY = "resident_population_density";
	public static final String ANTHROPOGENIC_HEAT_FLUX_MEAN = "anthropogenic_heat_flux_mean";
	public static final String TOPSOIL_CLAY_FRACTION = "topsoil_clay_fraction";
	public static final String TOPSOIL_SAND_FRACTION = "topsoil_sand_fraction";
	public static final String TOPSOIL_BULK_DENSITY = "topsoil_bulk_density";
	public static final String BUILDING_HEIGHT_STANDARD_DEVIATION = "building_height_standard_deviation";
	public static final String ROUGHNESS_LENGTH_MOMENTUM_MAC = "roughness_length_momentum_mac";
	public static final String DISPLACEMENT_HEIGHT_MAC = "displacement_height_mac";
	public static final String ROUGHNESS_LENGTH_MOMENTUM_KANDA = "roughness_length_momentum_kanda";
	public static final String DISPLACEMENT_HEIGHT_KANDA = "displacement_height_kanda";
	public final String USTOREY_FILE_NAME = "ustorey.dat";
	
	
	public static final int MET_DATE = 0;
	public static final int MET_KD = 1;
	public static final int MET_LD = 2;
	public static final int MET_TA = 3;
	public static final int MET_QAIR = 4;
	public static final int MET_WS = 5;
	public static final int MET_WD = 6;
	public static final int MET_P = 7;
	public static final int MET_RH = 8;	
	public static final int MET_RAINF = 9;
	
	double minuteToMillisecond = 60000.0;
	double thirtyMinuteMilli = (30.0*minuteToMillisecond);
	double dayMilli = minuteToMillisecond * 60.0 * 24.0;

	public static void main(String[] args)
	{
		UrbanPlumberPhase2ConfigMaker u = new UrbanPlumberPhase2ConfigMaker();
		u.process();

	}
	
	
	public void process()
	{
		// blank out the script, each run will be appended to this file
		common.createDirectory(runDirectory);
		common.writeFile("", scriptFile);	
		
		ArrayList<String[]> siteDataFiles = new ArrayList<String[]>();
		String[] sitesDirList = common.getDirectoryList(sitesDirectory);
		for (String siteFile : sitesDirList)
		{
////			//TODO get rid of this, but for now, just generate Preston
			if (siteFile.contains("Preston"))
			{}
			else
			{
				continue;
			}
			
			if (siteFile.endsWith(".csv"))
			{
			}
			else
			{
				continue;
			}
			String siteForcing = sitesDirectory + siteFile.replace(".csv", ".txt").replace("sitedata", "metforcing");
			boolean isSiteForcing = common.isFile(siteForcing);
			
			String siteData = sitesDirectory + siteFile;			
			boolean isSiteData = common.isFile(siteData);
			
			System.out.println(siteForcing + " " + isSiteForcing + " " + siteData + " " + isSiteData);	
			siteDataFiles.add(new String[]{siteForcing, siteData});
		}
	
		for (String[] siteFiles : siteDataFiles)
		{
			TreeMap<String,Double> siteData = readSiteData(siteFiles[1]);
			ArrayList<double[]> metData = urbanPlumberMetReformat(siteFiles[0]);
			TreeMap<String,String> metadata = getMetDataMetadata(siteFiles[0]);
			System.out.println(metadata.toString());
			
			double latitude = siteData.get(LATITUDE);
			double longitude = siteData.get(LONGITUDE);
			double measurement_height_above_ground = siteData.get(MEASUREMENT_HEIGHT_ABOVE_GROUND);
//			double impervious_area_fraction = siteData.get(IMPERVIOUS_AREA_FRACTION);
			double tree_area_fraction = siteData.get(TREE_AREA_FRACTION);
			double grass_area_fraction = siteData.get(GRASS_AREA_FRACTION);
			double bare_soil_area_fraction = siteData.get(BARE_SOIL_AREA_FRACTION);
			double water_area_fraction = siteData.get(WATER_AREA_FRACTION);
			double roof_area_fraction = siteData.get(ROOF_AREA_FRACTION);
			double road_area_fraction = siteData.get(ROAD_AREA_FRACTION);
			double other_paved_area_fraction = siteData.get(OTHER_PAVED_AREA_FRACTION);
			double building_mean_height = siteData.get(BUILDING_MEAN_HEIGHT);
			double tree_mean_height = siteData.get(TREE_MEAN_HEIGHT);
			
			String siteName = metadata.get("sitename");
			String time_analysis_start = metadata.get("time_analysis_start");  // 1998-01-01 00:00:00
			String local_offset = metadata.get("local_utc_offset_hours");  // 1.0
			String timestep_interval_seconds = metadata.get("timestep_interval_seconds");  // 3600.0
			
			double localOffset = Double.parseDouble(local_offset);
			
			// if there are hourly, interpolate to 30 minutes
			metData =  interpolateMetData(metData, timestep_interval_seconds, localOffset, time_analysis_start);
//TODO, take this out, reducing met data length so the simulations are shorter for testing purposes
metData = new ArrayList<double[]>(metData.subList(0, 200));
			
			metData = trimIncompleteDaysFromMet(metData);
			
			//also calculate first day (of year) and total days for VTUF-3D
			String[] maespaFormattedDates = formatMetDatesToMaespa(metData);
			
			// 31/12/03  //  DD/MM/YY
			maespaStartDate = maespaFormattedDates[0] + "/" + maespaFormattedDates[1] + "/" + maespaFormattedDates[2] ;
			maespaEndDate =   maespaFormattedDates[3] + "/" + maespaFormattedDates[4] + "/" + maespaFormattedDates[5] ;
			
			double vegHt = tree_mean_height;
			double bldHt = building_mean_height;
			double building = roof_area_fraction*100;
			double road = (road_area_fraction + other_paved_area_fraction)*100;
			double tree = (tree_area_fraction + water_area_fraction)*100;
			double grass = (grass_area_fraction + bare_soil_area_fraction)*100;
			double total = building + road + tree + grass;
			double forcingHeight = measurement_height_above_ground;
			
			int start= Integer.parseInt(maespaFormattedDates[6]);  
			int end= Integer.parseInt(maespaFormattedDates[7]);
			
			//will get values like:
//			1,latitude,-37.8265,degrees_north,Coutts et al. (2007a),https://doi.org/10.1016/j.atmosenv.2006.08.030,,,,,,
			//if negative, set S, otherwise N
//			2,longitude,145.099,degrees_east,Coutts et al. (2007a),https://doi.org/10.1016/j.atmosenv.2006.08.030,,,,,,
			// if negative, set W, otherwise E
			
			latForMaespa = Math.abs(Math.round(latitude))
					+ "	0	0" ;
			lonForMaespa = Math.abs(Math.round(longitude))
					+ "	0	0" ;
			maespaNorthSouth = "N";
			if (latitude < 0)
			{
				maespaNorthSouth = "S";
			}
			maespaEastWest = "E";
			if (longitude < 0)
			{
				maespaEastWest = "W";
			}
			maespaTzLong = Math.round((latitude/15.0))*15 + "";
			
			generateConfigurationForRun(runDirectory, vegHt, bldHt, building, road, tree, grass, start, end, siteName, latitude, forcingHeight, metData);
			

			
			//TODO get rid of this.
//			break;
		}
	}

	
//	{acknowledgements = Contains modified Copernicus Climate Change Service Information (ERA5 hourly data on single levels). 
//	Data from replica hosted by NCI Australia. With thanks to all involved in collecting, processing and hosting observational data, 
//	comment = Missing forcing filled with PL-Narutowicza tower site where available. Precipitation from IMGW Łódź Lublinek. Subset of available years included here., 
//	conventions = ALMA+CF.rev13, 
//	date_created = 2021-05-20 16:11:12, 
//	featureType = timeSeries, 
//	local_utc_offset_hours = 1.0, 
//	long_sitename = Lipowa Street, Łódź, Poland, 
//	observations_contact = Wlodzimierz Pawlak: wlodzimierz.pawlak@geo.uni.lodz.pl, Krzysztof Fortuniak: krzysztof.fortuniak@geo.uni.lodz.pl, 
//	observations_reference = Fortuniak, Pawlak and Siedlecki (2013): https://doi.org/10.1007/s10546-012-9762-1; Pawlak, Fortuniak, Siedlecki (2011): https://doi.org/10.1002/joc.2247; Offerle, Grimmond, Fortuniak, Pawlak (2006): https://doi.org/10.1175/JAM2319.1, 
//	other_references = ERA5: Copernicus Climate Change Service (C3S) (2017): https://cds.climate.copernicus.eu/cdsapp#!/home NCI Australia: http://doi.org/10.25914/5f48874388857, 
//	project_contact = Mathew Lipson: m.lipson@unsw.edu.au, Sue Grimmond: c.s.grimmond@reading.ac.uk, Martin Best: martin.best@metoffice.gov.uk, 
//	sitename = PL-Lipowa, 
//	summary = Observed and ERA5-derived surface meteorological data for Lipowa Street, Łódź, Poland. Data is for use by registered participants of Urban-PLUMBER in this project only. Do not distribute. All times in UTC., 
//	time_analysis_start = 2008-01-01 00:00:00, 
//	time_coverage_end = 2012-12-31 23:00:00, 
//	time_coverage_start = 1998-01-01 00:00:00, 
//	time_shown_in = UTC, 
//	timestep_interval_seconds = 3600.0, 
//	timestep_number_analysis = 43848, 
//	timestep_number_spinup = 87648, 
//	title = URBAN-PLUMBER forcing data for PL-Lipowa, 
//	units = SWdown: W/m2, LWdown: W/m2, Wind_E: m/s, Wind_N: m/s, PSurf: Pa, Tair: K, Qair: 1, Rainf: kg/m2/s, Snowf: kg/m2/s, version = v1}
	
	public void generateConfigurationForRun(String baseDir, double vegHt, double bldHt, double building, double road, double tree, double grass, int start, int end, String runName,
			double latitude, double forcingHeight, ArrayList<double[]> metData)
	{	
//		String baseDir = "/tmp/";

		
			int grassInt = (int) Math.round(grass);
			int roadInt = (int) Math.round(road);
			int buildingInt = (int) Math.round(building);
			int treeInt = (int) Math.round(tree);
			
			System.out.println(runName);
			String configDir = baseDir + runName + "/";
			createDomainFromGrassAndRoadAndBuildingAndTrees(configDir, roadInt, buildingInt, treeInt, bldHt, vegHt, runName);
			makeConfig(runName, baseDir, start, end, latitude, forcingHeight, metData);
			updateScriptFile(runName);
			
	}

	
	public void makeConfig(String runName, String baseDir, int start, int end, double latitude, double forcingHeight, ArrayList<double[]> metData)
	{
		String runDirectory = baseDir + runName + "/";		
		create(runDirectory, start, end, runName, latitude, forcingHeight, metData);
	}
//	
	public void create(String runDirectory, int start, int end, String runName, double latitude, double forcingHeight, ArrayList<double[]> metData)
	{
		String configNumber="";
		common.createDirectory(runDirectory);	
		double millisDouble = metData.get(0)[MET_DATE];
		long millis = Math.round(millisDouble);
		int year = getYearForMillis(millis);
	
	
		ConfigurationMaker configMaker = new ConfigurationMaker();
		configMaker.runDirectory = runDirectory;		
		configMaker.configDomainsDirectory = runDirectory;
		configMaker.configDomainFilename = runName;
		configMaker.year = year;
		configMaker.createStreetsEtc(runDirectory, gridSizeInMeters);
		process(start, end, runDirectory, gridSizeInMeters, configNumber, latitude, forcingHeight, runName, metData
				, configMaker, year
				);		
		String targetDirectory = runDirectory;
		String source = sourceDirectory + sourceExe;
		String target = targetDirectory + "/" + sourceExe;
		common.createSymlink(source, target );

	}

	public void process(int day, int end, String runDirectory, double gridSizeInMeters, String configNumber, 
			double lat, double forcingHeight, String runName, ArrayList<double[]> metData
			, ConfigurationMaker configMaker, int year
			)
	{
		//TODO, do I still need the year? Or dataTable?
//		int year = 2004;
		String dataTable = null;
		
		
		int numDays = end - day;
		int numberOfTrees = 1;
		
		TUFBldVegHeights vegHeights = new TUFBldVegHeights();
		vegHeights.vegetationArrayPreparation(configMaker.getBuildingHeightArray(),configMaker.getVegHeightArray(),configMaker.getVegTypeArray(),configMaker.getTreesArray(), gridSizeInMeters);
		outputStats(vegHeights, runDirectory);
	
		ConfigParametersDat  configParametersDat = new ConfigParametersDat(runDirectory, year, configNumber, day );
		configParametersDat.setYd( day );
		configParametersDat.setYear(year);
		configParametersDat.setPress(1013.3);
		configParametersDat.setEmiss_var("F");
		configParametersDat.setEmissInter(0.90);
		configParametersDat.setEmissNS(0.90);
		configParametersDat.setEmissEW(0.90); 
		configParametersDat.setCloudtype("0");
		configParametersDat.setNumres(1);
		configParametersDat.setOutpt_tm("0.5");
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
		configParametersDat.setBuildht_m(configMaker.highestBuildingHeight  + "" );
		
		//zref is the forcing height. But it also needs to be higher than the highest building
		double tempZref = common.roundToDecimals(configMaker.highestBuildingHeight * gridSizeInMeters * 1.1, 1);
		
		if (forcingHeight > tempZref)
		{
			tempZref = forcingHeight;
		}
		
		configParametersDat.setZref(tempZref + "");		
		configParametersDat.generateFile();

		configParametersDat.writeConfigFile(runDirectory);
		
		//right now, it will just start on 2003 day 1 and use all the forcing data to the end of the Urban Plumber dataset
		//so we don't know how many days of data yet
//		ConfigForcingDat configForcingDat = new ConfigForcingDat(runDirectory, year, configNumber, day, numDays);
//		generateFile(dataTable);		
		
		String metDataText = generateConfigFileText(runName, metData);

		String FILENAME_PREFIX = "forcing";
		String FILENAME_SUFFUX = ".dat";
		String filename = FILENAME_PREFIX + FILENAME_SUFFUX;
		writeConfigForcingFile(runDirectory, metDataText, filename);
//		ArrayList<double[]> loadedMetData = configForcingDat.loadedMetData;
		
		TreeMap<String, Integer> configFileVariations = vegHeights.getConfigFileVariations();		
		MaespaConfigConfileDat maespaConfigConfileDat = new MaespaConfigConfileDat(runDirectory, year, configNumber, day, numDays);
		
		//create treemap.dat file

		maespaConfigConfileDat.setConfigStartdate(maespaConfigConfileDat.getStartDate());
		maespaConfigConfileDat.setConfigEnddate(maespaConfigConfileDat.getEndDate());
		
		maespaConfigConfileDat.setConfigTreeMapCentralArrayLength(configMaker.configTreeMapCentralArrayLength);
		maespaConfigConfileDat.setConfigTreeMapCentralWidth(configMaker.configTreeMapCentralWidth);
		maespaConfigConfileDat.setConfigTreeMapCentralLength(configMaker.configTreeMapCentralLength);
		maespaConfigConfileDat.setConfigTreeMapX(configMaker.configTreeMapX);
		maespaConfigConfileDat.setConfigTreeMapY(configMaker.configTreeMapY);		
		maespaConfigConfileDat.setConfigTreeMapX1(configMaker.configTreeMapX1);
		maespaConfigConfileDat.setConfigTreeMapY1(configMaker.configTreeMapY1);		
		maespaConfigConfileDat.setConfigTreeMapX2(configMaker.configTreeMapX2);
		maespaConfigConfileDat.setConfigTreeMapY2(configMaker.configTreeMapY2);		

		maespaConfigConfileDat.setConfigTreeMapNumsfcab(configMaker.configTreeMapNumsfcab);			
		
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
		maespaConfigConfileDat.setConfigTreeMapHighestBuildingHeight(configMaker.highestBuildingHeight );   //add 1 for good measure 
		maespaConfigConfileDat.setConfigTreeMapUsingDiffShading(1);
		
		common.writeFile(maespaConfigConfileDat.getTreeMapDataFile(), runDirectory+"treemap.dat");
//		maespaConfigConfileDat.writeTreeMapConfigFile(runDirectory);
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
			
				String configDirectory = runDirectory  +  newSubdirectory + "/" + differtialShadingValue + "/";
				System.out.println("configNumber value=" + configNumber);

				writeTreeConfigFiles(maespaConfigConfileDat, configDirectory, treesNumber, phyFileNumber, strFileNumber, 
						configType, heightInMeters, runDirectory,gridSizeInMeters,year,dataTable, forcingHeight);
				generateTreeConfigs(maespaConfigConfileDat, configDirectory, numberOfTrees, configFileVariations, vegHeights, dataTable, configNumber, configType, 
						differtialShadingValue, metData, latForMaespa, lonForMaespa, maespaNorthSouth, maespaEastWest, maespaTzLong);
				
				String source = maespaExeDir + maespaExe;
				String target = runDirectory + "/" + newSubdirectory + "/" + differtialShadingValue + "/" + maespaExe;		
				common.createSymlink(source, target);
				
				String runSubDirectory = "/" + newSubdirectory + "/" + differtialShadingValue ;	
				//not going to run anymore, this is run in the overall script to run the model
//				runModel(runSubDirectory, runDirectory);
				
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
//	

	public void writeConfigForcingFile(String inputDirectory, String fileText, String filename)
	{
		common.createDirectory(inputDirectory);
		common.writeFile(fileText, inputDirectory + "/" + filename);
	}
	
	private String generateConfigFileText(String runPrefix, ArrayList<double[]> loadedMetData)
	{
//		UrbanPlumberMetReformat u = new UrbanPlumberMetReformat();
//		loadedMetData = u.processForVTUF3D();
		
		
		ArrayList<double[]> loadedMetDataForModellingPeriod = new ArrayList<double[]>();		
		StringBuilder st = new StringBuilder();
		
		// start at year and day
		int forcingCount = 0;
		boolean foundStartDate=false;
		boolean foundEndDate = false;
		for (double[] data : loadedMetData)
		{
		
			// reformattedDatetime,Kd,Ld,Ta,RH,WS,WD,P
			long millis = Math.round(data[MET_DATE]);
			double kdown = data[MET_KD];
			double ldown = data[MET_LD];
			double temp = data[MET_TA];
			double eaFrc = data[MET_QAIR];
			double windSpd = data[MET_WS];
			double windDir = data[MET_WD];
			double pressure = data[MET_P];
			
			double rh = data[MET_RH];
			double rainf = data[MET_RAINF];
			
			
//			int[] yearDay = u.getStartYearDay(millis);
//			if (foundStartDate)
//			{}
//			else if ( yearDay[0] == year && yearDay[1]==day)
//			{
//				foundStartDate = true;
//			}
//			else
//			{
//				continue;
//			}
//			
//			if (foundEndDate)
//			{
//				continue;
//			}
//			else
//			{
//				if  (forcingCount > getNumfrc())
//				{
//					foundEndDate = true;
//					continue;
//				}
//					
//			}
			
			String formattedTime = getFormattedDatetime(millis);
			
	
			String line = formattedTime + " " + kdown + Common.tab 
					+ ldown + Common.tab
					+ temp + Common.tab
					+ eaFrc + Common.tab
					+ windSpd + Common.tab
					+ windDir + Common.tab
					+ pressure ;
//			System.out.println(line);
			
			st.append(kdown + Common.tab 
					+ ldown + Common.tab
					+ temp + Common.tab
					+ eaFrc + Common.tab
					+ windSpd + Common.tab
					+ windDir + Common.tab
					+ pressure );
			st.append(Common.linefeed);
			loadedMetDataForModellingPeriod.add(data);
			forcingCount++;
		}
		
		st.append(    Common.linefeed );
		st.append(    Common.linefeed );
		

		st.append("	      read(981,*)numfrc,starttime,deltatfrc" + Common.linefeed );
		st.append("	        do k=1,numfrc+1" + Common.linefeed );
		st.append("	         read(981,*)Kdnfrc(k),Ldnfrc(k),Tafrc(k),eafrc(k),Uafrc(k)," +  Common.linefeed );
		st.append("	       &            Udirfrc(k),Pressfrc(k)" +  Common.linefeed );
		st.append("	         if(Kdnfrc(k).ge.-90.) Kdnfrc(k)=max(Kdnfrc(k),0.)" +  Common.linefeed );
		st.append("	         timefrc(k)=starttime+real(k-1)*deltatfrc" +  Common.linefeed );
		st.append("	        enddo" +  Common.linefeed );
		st.append("	        " +  Common.linefeed );
		st.append("	        " +  Common.linefeed );
		st.append("	      NUMFRC: The number of forcing timesteps over which the model will run," +  Common.linefeed );
		st.append("	      i.e. total model integration time divided by the timestep of the input" +  Common.linefeed );
		st.append("	      forcing data (** note: there must be numfrc+1 rows of forcing data," +  Common.linefeed );
		st.append("	      because data from the start time and end time both much be present)" +  Common.linefeed );
		st.append("	      STARTTIME: The time at the start of the simulation (in hours from" +  Common.linefeed );
		st.append("	      midnight) in local mean solar time (i.e., in a time zone such that" +  Common.linefeed );
		st.append("	      the smallest solar zenith angle occurs exactly at noon)" +  Common.linefeed );
		st.append("	      DELTATFRC: (hours) the timestep of the forcing data" +  Common.linefeed );
		st.append("	      KDNFRC(k): (W/m2) downwelling shortwave radiation flux density;" +  Common.linefeed );
		st.append("	      if Kdnfrc(1)<-90.0 then the model will use its internal solar model" +  Common.linefeed );
		st.append("	      to calculate downwelling shortwave (Iqbal/Stull)" +  Common.linefeed );
		st.append("	      LDNFRC(k): (W/m2) downwelling longwave radiation flux density;" +  Common.linefeed );
		st.append("	      if Ldnfrc(1)<0.0 then the model will use its internal longwave model" +  Common.linefeed );
		st.append("	      to calculate downwelling shortwave (Prata)" +  Common.linefeed );
		st.append("	      TAFRC(k): (deg C) air temperature at 'zref' (see input file - .dat)" +  Common.linefeed );
		st.append("	      *EAFRC(k): (mb) water vapour pressure at 'zref'" +  Common.linefeed );
		st.append("	      UAFRC(k): (m/s) wind SPEED (not velocity) at 'zref'" +  Common.linefeed );
		st.append("	      UDIRFRC(k): direction from which the wind is blowing at 'zref'," +  Common.linefeed );
		st.append("	      in degrees from north" +  Common.linefeed );
		st.append("	      *PRESSFRC(k): (mb) air pressure near the surface" +  Common.linefeed );
		st.append("	      " +  Common.linefeed );
		st.append("	      * these values only have a very minor impact on simulation results," +  Common.linefeed );
		st.append("	      and they can be roughly approximated with little influence on model" +  Common.linefeed );
		st.append("	      ouput" +  Common.linefeed );
		st.append("	      The UDIRFRC(k) are only important for calculating lambdaf (frontal area index)" +  Common.linefeed );
		st.append("	      and z0 - for momentum and heat exchange. Thus, if you prescribe these values" +  Common.linefeed );
		st.append("	      in parameters.dat, the values for Udirfrc will have no impact on the" +  Common.linefeed );
		st.append("	      simulation." +  Common.linefeed );
		st.append("	      " +  Common.linefeed );

		//we now know how many forcing steps
//		setNumfrc(forcingCount-1);
		int numFrc = forcingCount-1;
//		setStarttime("0.0");
//		setDeltatfrc("0.5");
		double startTime = 0.0;
		double deltatfrc = 0.5;
		
		st.insert(0, numFrc + " " + startTime + " " + deltatfrc + " " + Common.linefeed);

		
		loadedMetData = loadedMetDataForModellingPeriod;
		return st.toString();
	}	
	
	public int getYearForMillis(long millis)
	{		
		String timezone = "00";
		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"
				+ timezone
				+ ":00"));
		localCalendar.setTimeInMillis(millis);
		
		int newYear = localCalendar.get(Calendar.YEAR) ;
	    return newYear;
	}
	
	public String getFormattedDatetime(long millis)
	{		
		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+10:00"));
		localCalendar.setTimeInMillis(millis);
		
		int newYear = localCalendar.get(Calendar.YEAR) ;
	    int newMonth = localCalendar.get(Calendar.MONTH) + 1;
	    int newDay = localCalendar.get(Calendar.DAY_OF_MONTH) ;
	    int newHour = localCalendar.get(Calendar.HOUR_OF_DAY) ;
	    int newMinute = localCalendar.get(Calendar.MINUTE) ;
	    
	    String newMinuteStr = common.padLeft(newMinute, 2, '0');
	    String newHourStr = common.padLeft(newHour, 2, '0');
	    String newMonthStr = common.padLeft(newMonth, 2, '0');
	    String newDayStr = common.padLeft(newDay, 2, '0');

	    String reformattedTime = newHourStr + ":" + newMinuteStr;
	    String reformattedDate = newDayStr + "/" + newMonthStr + "/" + newYear;
	    return reformattedDate + " " + reformattedTime;
	}
	
	public String getFormattedDatetime(long millis, String timezone)
	{		
		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"
				+ timezone
				+ ":00"));
		localCalendar.setTimeInMillis(millis);
		
		int newYear = localCalendar.get(Calendar.YEAR) ;
	    int newMonth = localCalendar.get(Calendar.MONTH) + 1;
	    int newDay = localCalendar.get(Calendar.DAY_OF_MONTH) ;
	    int newHour = localCalendar.get(Calendar.HOUR_OF_DAY) ;
	    int newMinute = localCalendar.get(Calendar.MINUTE) ;
	    
	    String newMinuteStr = common.padLeft(newMinute, 2, '0');
	    String newHourStr = common.padLeft(newHour, 2, '0');
	    String newMonthStr = common.padLeft(newMonth, 2, '0');
	    String newDayStr = common.padLeft(newDay, 2, '0');

	    String reformattedTime = newHourStr + ":" + newMinuteStr;
	    String reformattedDate = newDayStr + "/" + newMonthStr + "/" + newYear;
	    return reformattedDate + " " + reformattedTime;
	}
	
	public TreeMap<Integer, int[]> getDomains(String runDirectory, String runName)
	{	
		TreeMap<Integer, int[]> domainValues = new TreeMap<Integer, int[]>();				
//		WriteDomainCSV write = new WriteDomainCSV();
		int[] startingCentralHeightArray = readCSV(runName, TUFDomains.BUILDING_HEIGHTS, runDirectory);		
		int[] startingCentralHeightVegArray = readCSV(runName, TUFDomains.VEGETATION_HEIGHTS, runDirectory);		
		int[] startingCentralHeightVegTypeArray = readCSV(runName, TUFDomains.VEGETATION_TYPES, runDirectory);		
		int[] startingCentralTreesArray = readCSV(runName, TUFDomains.TREES, runDirectory);
		
		domainValues.put(TUFDomains.BUILDING_HEIGHTS, startingCentralHeightArray);
		domainValues.put(TUFDomains.VEGETATION_HEIGHTS, startingCentralHeightVegArray);
		domainValues.put(TUFDomains.VEGETATION_TYPES, startingCentralHeightVegTypeArray);
		domainValues.put(TUFDomains.TREES, startingCentralTreesArray);	

		return domainValues;
	}
	
	public int[] readCSV(String name, int variable, String dirLocation)
	{
		String filename = dirLocation + name + "_" + variable + ".csv";
		ArrayList<Integer> data = new ArrayList<Integer>();
		ArrayList<String> fileContents = common.readLargerTextFileAlternateToArray(filename);
		for (String line : fileContents)
		{
			
			String[] items = Common.splitOnWhitespace(line.trim());
			for (String item : items)
			{
				String value = item.replaceAll("\"", "").trim();
				int intValue = Integer.parseInt(value);
				data.add(intValue);
			}
			
		}
		int[] returnData = new int[data.size()];
		for (int i=0;i<data.size();i++)
		{
			returnData[i]=data.get(i);
		}
		return returnData;
	}
	

	
	

	

	public void processDomain(ArrayList<int[][]> domainData, String dir, String runName)
	{
		int[][] buildingHts = domainData.get(0);
		int[][] vegetationHts = domainData.get(1); 
		int[][] vegetationTypes = domainData.get(2); 
		int[][] treeLocations = domainData.get(3);
		common.createDirectory(dir);
		String outputFile1 = dir + runName + "_1.csv";
		String outputFile2 = dir + runName + "_2.csv";
		String outputFile3 = dir + runName + "_3.csv";
		String outputFile4 = dir + runName + "_4.csv";
		createDomainFiles(buildingHts, vegetationHts, vegetationTypes, treeLocations, outputFile1, outputFile2, outputFile3, outputFile4);
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
				if (j!=HEIGHT-1)
				{
					sb1.append(Common.tab);
				}
				else
				{
					sb1.append(Common.linefeed);
				}
				
				sb2.append("\"" + vegetationHts[i][j] + "\"");
				if (j!=HEIGHT-1)
				{
					sb2.append(Common.tab);
				}
				else
				{
					sb2.append(Common.linefeed);
				}
				
				sb3.append("\"" + vegetationTypes[i][j] + "\"");
				if (j!=HEIGHT-1)
				{
					sb3.append(Common.tab);
				}
				else
				{
					sb3.append(Common.linefeed);
				}
				
				sb4.append("\"" + trees[i][j] + "\"");
				if (j!=HEIGHT-1)
				{
					sb4.append(Common.tab);
				}
				else
				{
					sb4.append(Common.linefeed);
				}
			}
		}
//		System.out.println(sb1.toString());
		String sb1Str = sb1.toString();
		String sb2Str = sb2.toString();
		String sb3Str = sb3.toString();
		String sb4Str = sb4.toString();
		
		//adding a linefeed at the end, cut that off
		sb1Str = sb1Str.substring(0, sb1Str.length()-1);
		sb2Str = sb2Str.substring(0, sb2Str.length()-1);
		sb3Str = sb3Str.substring(0, sb3Str.length()-1);
		sb4Str = sb4Str.substring(0, sb4Str.length()-1);
		
		common.writeFile(sb1Str, outputFile1);
		common.writeFile(sb2Str, outputFile2);
		common.writeFile(sb3Str, outputFile3);
		common.writeFile(sb4Str, outputFile4);
	}

	public void updateScriptFile(String runName)
	{
		String text = 
				 "BASEDIR='" + runDirectory
				 + "'" + Common.linefeed 
				+ "" + Common.linefeed 
	
				+ "RUN='"
				+ runName
				+ "'" + Common.linefeed 
				+ "" + Common.linefeed 
				
				+ "if [ -d $BASEDIR/$RUN'/1/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/1/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/1/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/1/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ "if [ -d $BASEDIR/$RUN'/2/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/2/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/2/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/2/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed

				+ "if [ -d $BASEDIR/$RUN'/3/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/3/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/3/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/3/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ "if [ -d $BASEDIR/$RUN'/4/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/4/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/4/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/4/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ "if [ -d $BASEDIR/$RUN'/5/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/5/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/5/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/5/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ "if [ -d $BASEDIR/$RUN'/6/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/6/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/6/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/6/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ "if [ -d $BASEDIR/$RUN'/7/1' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/7/1" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				+ "if [ -d $BASEDIR/$RUN'/7/2' ] " + Common.linefeed
				+ "then"  + Common.linefeed
				+ "  cd $BASEDIR/$RUN/7/2" + Common.linefeed
				+ "  ./maespa.out" + Common.linefeed
				+ "fi" + Common.linefeed
				
				+ Common.linefeed
				+ "cd $BASEDIR/$RUN" + Common.linefeed 
//				+ "./TUF3Dradiation" + Common.linefeed
				+"java -cp /home/kerryn/git/VTUF-3D-Java.v2/bin/ VTUF3D.TUFreg3D $BASEDIR/$RUN/" + Common.linefeed
//				+ "zip out.zip *.out" + Common.linefeed
				+ "find . -mindepth 1 -maxdepth 1 -iname '*.out' | zip -@ out.zip" + Common.linefeed
				+ "#rm *.out" + Common.linefeed
				+ "cd .." + Common.linefeed
				+ Common.linefeed;			
		common.appendFile(text, scriptFile);
	}
//	
	
	


	

		public String getFormattedDatetime(String yearStr, String monthStr, String dayStr, String hourStr, String minuteStr)
		{
			int year = new Integer(yearStr).intValue();
			int month = new Integer(monthStr).intValue()-1;
			int day = new Integer(dayStr).intValue();
			int hour = new Integer(hourStr).intValue();
			int minute = new Integer(minuteStr).intValue();
			
		    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		    calendar.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("UTC").getRawOffset());
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, month);
		    calendar.set(Calendar.DAY_OF_MONTH, day);
		    calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, minute);

			long millis = calendar.getTimeInMillis();
			
			
			Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+10:00"));
			localCalendar.setTimeInMillis(millis);
			
			int newYear = localCalendar.get(Calendar.YEAR) ;
		    int newMonth = localCalendar.get(Calendar.MONTH) + 1;
		    int newDay = localCalendar.get(Calendar.DAY_OF_MONTH) ;
		    int newHour = localCalendar.get(Calendar.HOUR_OF_DAY) ;
		    int newMinute = localCalendar.get(Calendar.MINUTE) ;
		    
		    String newMinuteStr = common.padLeft(newMinute, 2, '0');
		    String newHourStr = common.padLeft(newHour, 2, '0');
		    String newMonthStr = common.padLeft(newMonth, 2, '0');
		    String newDayStr = common.padLeft(newDay, 2, '0');

		    String reformattedTime = newHourStr + ":" + newMinuteStr;
		    String reformattedDate = newDayStr + "/" + newMonthStr + "/" + newYear;
		    return reformattedDate + " " + reformattedTime;
		}
	
		public TreeMap<String,Double> readSiteData(String siteFile)
		{
			TreeMap<String,Double> siteData = new TreeMap<String,Double>();
			
			ArrayList<String> fileContents = common.readLargerTextFileAlternateToArray(siteFile);
			for (String line : fileContents)
			{
				if (line.startsWith("id"))
				{
					continue;
				}
				String[] splitLine = line.split(Common.comma);
				String parameter = splitLine[1].trim();
				String valueStr = splitLine[2].trim();
				double value = Double.parseDouble(valueStr);
				siteData.put(parameter, value);
				
			}
			
			return siteData;
		}
//		
		public TreeMap<String,String> getMetDataMetadata(String metFile)
		{
			TreeMap<String,String> metadata = new TreeMap<String,String>();
			ArrayList<String> metFileContents = common.readLargerTextFileAlternateToArray(metFile,100);
			for (String line : metFileContents)
			{
				if (line.startsWith("#"))
				{
					
				}
				else
				{
					continue;
				}
				line = line.replaceFirst("#", "").trim();
				String[] lineSplit = line.split("=");
				if (lineSplit == null || lineSplit.length < 2)
				{
					continue;
				}
				String parameter = lineSplit[0].trim();
				String value = lineSplit[1].trim();
				metadata.put(parameter, value);
			}
			return metadata;
		}
//
		public ArrayList<double[]> urbanPlumberMetReformat(String metFile)
		{
			
			ArrayList<double[]> metData = new ArrayList<double[]>();
			
			
			ArrayList<String> metFileContents = common.readLargerTextFileAlternateToArray(metFile);
			for (String line : metFileContents)
			{
				if (line.startsWith("#"))
				{
					continue;
				}
				//        0        1      2        3        4      5         6         7         8            9            10      11        12          13         14         15      16        17       18        19
				//  #     Date     Time   SWdown  LWdown  Wind_E  Wind_N     PSurf    Tair      Qair         Rainf         Snowf  SWdown_qc  LWdown_qc  Wind_E_qc  Wind_N_qc  Tair_qc  PSurf_qc  Qair_qc  Rainf_qc  Snowf_qc
//				# units = SWdown: W/m2, LWdown: W/m2, Wind_E: m/s, Wind_N: m/s, PSurf: Pa, Tair: K, Qair: kg/kg, Rainf: kg/m2/s, Snowf: kg/m2/s
//						# quality control ([var]_qc) flags: 0=observed, 1=gapfilled_from_obs, 2=gapfilled_from_era5, 3=missing
				
				String[] lineSplit = Common.splitOnWhitespace(line);
				String date = lineSplit[0];  /// 1993-01-01
				String time = lineSplit[1];  /// 00:00:00
				String Tair = lineSplit[7];  /// 298.71
				String Qair = lineSplit[8];  /// 0.013658
				String Wind_E = lineSplit[4];/// 2.01
				String Wind_N = lineSplit[5];/// -3.50
				String PSurf = lineSplit[6]; /// 99838.5
				String SWdown = lineSplit[2];//OK for Kd
				String LWdown = lineSplit[3];//OK for Ld
				
				String Rainf = lineSplit[9];//
				
				double PSurfValue = new Double(PSurf).doubleValue();
				double P = PSurfValue / 100.0;
				double Kd = new Double(SWdown).doubleValue();
				double Ld = new Double(LWdown).doubleValue();
				double TairValue = new Double(Tair).doubleValue();
				double Ta = TairValue - 273.15;
				double QairValue = new Double(Qair).doubleValue();
				double RH = common.convertSpecHumidityToRH(QairValue,Ta,P)*100;
				double UValue = new Double(Wind_E).doubleValue();
				double VValue = new Double(Wind_N).doubleValue();
				double WS = common.calcWindSpeed(UValue, VValue);
				double WD = common.calcWindDirDegrees2(UValue, VValue);
				double RainfValue = new Double(Rainf).doubleValue();
				
				String[] timeSplit = time.split(":");
				String hour = timeSplit[0];
				String minute = timeSplit[1];
				String[] dateSplit = date.split("-");
				String year = dateSplit[0];
				String month = dateSplit[1];
				String day = dateSplit[2];
				
				long reformattedDatetime = getFormattedDatetimeLong(year, month, day, hour, minute);

				Ta = common.roundToDecimals(Ta, 3);
				RH = common.roundToDecimals(RH, 3);
				WS = common.roundToDecimals(WS, 3);
				P = common.roundToDecimals(P, 3);
				
				// TARGET needs:
//				datetime,Ta,RH,WS,P,Kd,Ld
//				24/02/2012 0:00, 22.3, 49,  9,1016.2,0.0,378.86
				
//				String outputLine = reformattedDatetime 
////						reformattedDate + " " + reformattedTime 
//						+ Common.comma + Ta + Common.comma + RH + Common.comma + WS + Common.comma + P + Common.comma + Kd + Common.comma + Ld;
				
//				System.out.println(outputLine);
//				sb.append(outputLine + Common.linefeed);
				
				double[] dataLine = new double[]{reformattedDatetime,Kd,Ld,Ta,QairValue*100.0,WS,WD,P,RH,RainfValue};
				metData.add(dataLine);
				
			}
//			return sb.toString();
			
//			common.writeFile(sb.toString(), prestonMetOutput);
			return metData;
		} 
		

		
		
		
		public long getFormattedDatetimeLong(String yearStr, String monthStr, String dayStr, String hourStr, String minuteStr)
		{
			int year = new Integer(yearStr).intValue();
			int month = new Integer(monthStr).intValue()-1;
			int day = new Integer(dayStr).intValue();
			int hour = new Integer(hourStr).intValue();
			int minute = new Integer(minuteStr).intValue();
			
		    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		    calendar.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("UTC").getRawOffset());
		    calendar.set(Calendar.YEAR, year);
		    calendar.set(Calendar.MONTH, month);
		    calendar.set(Calendar.DAY_OF_MONTH, day);
		    calendar.set(Calendar.HOUR_OF_DAY, hour);
		    calendar.set(Calendar.MINUTE, minute);
			long millis = calendar.getTimeInMillis();
			return millis;
		}
		
		public ArrayList<int[][]> getAllGrass()
		{
			int[][] vegetationTypes = new int[WIDTH][HEIGHT];	
			vegetationTypes = fillArray(vegetationTypes, TUFBldVegHeights.GRASS_CONFIG_TYPE);
			vegetationTypes[0][0] = 0;
			
			int[][] buildingHts = new int[WIDTH][HEIGHT];
			buildingHts[0][0] = 1;
			
			int[][] vegetationHts = new int[WIDTH][HEIGHT];
//			vegetationHts[0][0] = 1;
			
			int[][] treeLocations = new int[WIDTH][HEIGHT];
//			trees[0][0] = 1;
			
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
	//	
	//	
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
				count ++;
				if (count > neededArea)
				{
					ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
					returnValue.add(buildingHts);
					returnValue.add(vegetationHts);
					returnValue.add(vegetationTypes);
					returnValue.add(treeLocations);		
					return returnValue;
				}
				
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
					count ++;
					if (count > neededArea)
					{
						ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
						returnValue.add(buildingHts);
						returnValue.add(vegetationHts);
						returnValue.add(vegetationTypes);
						returnValue.add(treeLocations);		
						return returnValue;
					}
					
				}
				
				right = right + 1;
				for (int i=0;i<WIDTH;i++)
				{
					buildingHts[i][right] = 0;
					vegetationHts[i][right] = 0;
					vegetationTypes[i][right] = 0;
					treeLocations[i][right] = 0;
					count ++;
					if (count > neededArea)
					{
						ArrayList<int[][]> returnValue = new ArrayList<int[][]>();
						returnValue.add(buildingHts);
						returnValue.add(vegetationHts);
						returnValue.add(vegetationTypes);
						returnValue.add(treeLocations);		
						return returnValue;
					}
				
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
//			
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
//			
			public ArrayList<int[][]> addTreesToGrass(ArrayList<int[][]> domainData, int percent, double vegHt)
			{

				int loops = 0;
				int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
				vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
				
				int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
				
//				int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//				vegetationHts_temp = fillArray(vegetationHts_temp, 1);
				
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
//						vegetationHts[x][y] = vegetationHts_temp[x][y];
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

			public ArrayList<int[][]> addBuildingsToGrass(ArrayList<int[][]> domainData, int percent, double bldHt)
			{
				Random randomx = new Random(5);
				Random randomy = new Random(66);
				int loops = 0;
				int[][] vegetationTypes_temp = new int[WIDTH][HEIGHT];	
//				vegetationTypes_temp = fillArray(vegetationTypes_temp, TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE);
//				vegetationTypes_temp[0][0] = 0;
				
//				int[][] buildingHts_temp = new int[WIDTH][HEIGHT];
////				buildingHts_temp[0][0] = 1;
//				buildingHts_temp = fillArray(buildingHts_temp, 1);
				
				int[][] vegetationHts_temp = new int[WIDTH][HEIGHT];
//				vegetationHts_temp = fillArray(vegetationHts_temp, 1);
//				vegetationHts_temp[0][0] = 0;
				
				int[][] treeLocations_temp = new int[WIDTH][HEIGHT];
//				treeLocations_temp = fillArraySequential(treeLocations_temp, 300);
//				treeLocations_temp[0][0] = 0;
				
				int neededArea = (int) Math.round(TOTAL_AREA * (percent/100.)) - 1;
						
				int gridSize = WIDTH*HEIGHT;
				double aveHt = bldHt;
				double treePercent = percent;
				int items = (int)Math.round(gridSize * treePercent /100.0);
				
				System.out.println("calc buildings");
				int[] bldHeightsForAverages = getHeightsForAverage(aveHt,items,gridSize);
					
//				int[] bldHeightsForAverages = getHeightsForAverage(bldHt,neededArea,WIDTH*HEIGHT);
				
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
					
//					System.out.println(x + " " + y + " " + count );
					
					if (vegetationTypes[x][y] == TUFBldVegHeights.GRASS_CONFIG_TYPE)
					{
//						buildingHts[x][y] = buildingHts_temp[x][y];
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

			// start with all grass. fill the middle with road then replace grass with trees
			public void createDomainFromGrassAndRoadAndBuildingAndTrees(String dir, int roadPercent, int buildingPercent, int treePercent, double buildingHt, double vegHt, String runName)
			{
				ArrayList<int[][]> domainData = new ArrayList<int[][]>();
				domainData = getAllGrass();		
				domainData = addRoad(domainData,roadPercent);
				domainData = addTreesToGrass(domainData, treePercent,vegHt);
				domainData = addBuildingsToGrass(domainData, buildingPercent,buildingHt);
				processDomain(domainData,dir, runName);
			}
		//	
		//	

			public void outputStats(TUFBldVegHeights vegHeights, String runDirectory)
			{
				String stats =  "Percentages: " + Common.linefeed +
								"Trees: " + vegHeights.getTreePercentage()+ Common.linefeed +
								"Grass: " + vegHeights.getGrassPercentage()+ Common.linefeed +
								"Building: " + vegHeights.getBuildingPercentage()+ Common.linefeed +
								"Streets: " + vegHeights.getStreetPercentage() + Common.linefeed +
								"VegHt: " + (vegHeights.vegetationHeightAve ) + Common.linefeed +
								"BldHt: " + (vegHeights.buildingHeightAve ) + Common.linefeed +
								"VegDomainHt: " + (vegHeights.vegetationHeightDomainAve ) + Common.linefeed +
								"BldDomainHt: " + (vegHeights.buildingHeightDomainAve ) + Common.linefeed 
								;
				common.writeFile(stats, runDirectory + "domain_stats.txt");
			}
			
			
			public void writeTreeConfigFiles(MaespaConfigConfileDat maespaConfigConfileDat, String inputDirectory, int treesNumber,
					int phyFileNumber, int strFileNumber, int configType, double height, String runDirectory, double gridSizeInMeters, int year, String dataTable,
					double forcingHeight)
			{	
				//default to 4 meters
//				double forcingHeight = TUFDomains.getForcingHeight(dataTable);
				
				String twoDigitYear = new Integer(year).toString().substring(2, 4);
				int xmax = (int)gridSizeInMeters;
				int ymax = (int)gridSizeInMeters;
				double xy = gridSizeInMeters/2;
				
//				double leafAreaIndex =  maespaConfigConfileDat.getConfigTreezAlllareaValues();
				
				double trunkHeight = common.roundToDecimals(Math.max(0.25, height * .25), 2);
				double crownHeight = common.roundToDecimals(Math.max(0.75, height * .75), 2);
				double crownRadiusX = common.roundToDecimals(gridSizeInMeters * 0.5, 2);
				double crownRadiusY = common.roundToDecimals(gridSizeInMeters * 0.5, 2);
//				String trunkHeightStr = String.format("%.2f", trunkHeight);
//				String crownHeightStr = String.format("%.2f", crownHeight);
				
				// using relationships from  http://www.academicjournals.org/journal/AJB/article-abstract/96974C726212 and 10.1093/treephys/tps127
				double stemDiam = 0.05;
				if (trunkHeight + crownHeight > 7)
				{
					stemDiam = ((trunkHeight + crownHeight)-6.74)/14.4;
				}
				
				String phyConfigFileStr = "";
				String strConfigFileStr = "";
				
//				double oliveZht = 4.0;
//				double oliveZpd = 1.6;
//				double oliveZ0ht = 3.0;
//				double brushboxZht = 10.0;
//				double brushboxZpd = 3.89;
//				double brushboxZ0ht = 0.583;
				
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
				
//				double brushboxZht = height + 1;
//				double brushboxZpd = brushboxZht * 2/3;
//				double brushboxZ0ht = brushboxZht * 1/10;
				
//				//olive 4.0, brushbox 10
//				double zht = 10 * heightMultiples;
//				//olive 1.6, brushbox 3.89
//				double zpd = 3.89 * heightMultiples;
//				//olive 3.0, brushbox 0.583
//				double z0ht = 0.583 * heightMultiples;
				
				if (configType == TUFBldVegHeights.OLIVE_CONFIG_TYPE)
				{
//					int xmax = (int)gridSizeInMeters;
//					int ymax = (int)gridSizeInMeters;
//					double xy = gridSizeInMeters/2;
					
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
					maespaConfigConfileDat.setConfigTreexycoords(xy + " " + xy);						
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
//					double gridSize = gridSizeInMeters;
//					int xmax = (int)gridSize;
//					int ymax = (int)gridSize;
//					double xy = gridSize/2;
					
//					double gridMultiples = gridSizeInMeters / 5;
//					double heightMultiples = height / 5;
					
//					crownRadiusX = 2.5 * heightMultiples;
//					crownRadiusY = 2.5 * heightMultiples;
					
					// all the measurements area for a 5x5m grid. Need to scale to actual grid size
					double leafAreaIndex = 2.0;
//					if (maespaConfigConfileDat.isHalfLai())
//					{
//						System.out.println ("half LAI");
//						leafAreaIndex = leafAreaIndex / 2;
//					}
					// multiply LAI by the total area of the tree canopy

					double canopyArea = (crownRadiusX * crownRadiusY) * Math.PI ; 
					double totalLeafAreaIndex = leafAreaIndex * canopyArea;
					//create trees.dat file

//					double zht = 10 * heightMultiples;
//					double zpd = 3.89 * heightMultiples;
//					double z0ht = 0.583 * heightMultiples;
					
//					crownHeight = 5.83 * heightMultiples;
//					trunkHeight = 1.6 * heightMultiples;	
//					double treeDiam = 0.18 * heightMultiples;	
					
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
//					int xmax = (int)gridSize;
//					int ymax = (int)gridSize;
//					double xy = gridSize/2;
					
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
					System.out.println("write ustorey.dat " +  inputDirectory +  USTOREY_FILE_NAME);
					common.createDirectory(runDirectory + inputDirectory);
					common.writeFile(ustoreyStr,  inputDirectory + USTOREY_FILE_NAME);	
					maespaConfigConfileDat.setConfigIsimus(1);
					
				}
				else if (configType == TUFBldVegHeights.GRASS_CONFIG_TYPE  || configType == TUFBldVegHeights.IRRIGATED_GRASS_CONFIG_TYPE)
				{
					//this is grass
					double gridSize = gridSizeInMeters;
//					int xmax = (int)gridSize;
//					int ymax = (int)gridSize;
//					double xy = gridSize/2;
					
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
//					maespaConfigConfileDat.setConfigTreezht(30);
//					maespaConfigConfileDat.setConfigTreezpd(16);
//					maespaConfigConfileDat.setConfigTreez0ht(3);			
//					maespaConfigConfileDat.setConfigTreezAllradxNodates(1);
//					maespaConfigConfileDat.setConfigTreezAllradxDates("");
//					maespaConfigConfileDat.setConfigTreezAllradxValues(0.5);			
//					maespaConfigConfileDat.setConfigTreezAllradyNodates(1);
//					maespaConfigConfileDat.setConfigTreezAllradyDates("");
//					maespaConfigConfileDat.setConfigTreezAllradyValues(0.5);			
//					maespaConfigConfileDat.setConfigTreezAllhtcrownNodates(1);
//					maespaConfigConfileDat.setConfigTreezAllhtcrownDates("");
//					maespaConfigConfileDat.setConfigTreezAllhtcrownValues(1.6);			
//					maespaConfigConfileDat.setConfigTreezAlldiamNodates(1);
//					maespaConfigConfileDat.setConfigTreezAlldiamDates("");
//					maespaConfigConfileDat.setConfigTreezAlldiamValues(0.02);			
//					maespaConfigConfileDat.setConfigTreezAllhttrunkNodates(1);
//					maespaConfigConfileDat.setConfigTreezAllhttrunkDates("");
//					maespaConfigConfileDat.setConfigTreezAllhttrunkValues(0.5);			
//					maespaConfigConfileDat.setConfigTreezAlllareaNodates(1);
//					maespaConfigConfileDat.setConfigTreezAlllareaDates("");
//					maespaConfigConfileDat.setConfigTreezAlllareaValues(2.48);
//					maespaConfigConfileDat.setConfigTreeTitle("sINGLE TREE IN 1x1 metre plot.");
					
										
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
					System.out.println("write ustorey.dat " + inputDirectory + USTOREY_FILE_NAME);
					common.createDirectory(inputDirectory);
					common.writeFile(ustoreyStr, inputDirectory  + USTOREY_FILE_NAME);	
					maespaConfigConfileDat.setConfigIsimus(1);
					
				}
				else
				{
					//Other types, not configured yet.
				}
				
				common.createDirectory(inputDirectory);
				common.writeFile(maespaConfigConfileDat.getTreeDataFile(), inputDirectory+ "trees.dat");
//				maespaConfigConfileDat.writeTreeConfigFile(inputDirectory);
				
				// now they are different directories so they are always tree 1
				common.writeFile(phyConfigFileStr, inputDirectory + "/" + "phy" + 1 + ".dat");
//				maespaConfigConfileDat.writePhyConfigFile(phyConfigFileStr, inputDirectory, 1);
				common.writeFile(strConfigFileStr, inputDirectory + "/" + "str" + 1 + ".dat");
//				maespaConfigConfileDat.writeStrConfigFile(strConfigFileStr, inputDirectory, 1);
			}	
			
			
			public void generateTreeConfigs(MaespaConfigConfileDat maespaConfigConfileDat, String inputDirectory, int numberOfTrees,
					TreeMap<String, Integer> configFileVariations, TUFBldVegHeights vegHeights, String dataTable, String configNumber,
					int configType, int differtialShadingValue, ArrayList<double[]> loadedMetData, String lat, String lon, String northSouth, String eastWest, String tzlong)
			{		
//				String lat = "37	47	0" ;
//				String lon = "144	58	0" ;

				
				
				//create confile.dat
				maespaConfigConfileDat.setConfigFileTitle("Single tree test");
				maespaConfigConfileDat.setConfigiohrly(1);
				maespaConfigConfileDat.setConfigiotutd(9);		
				maespaConfigConfileDat.setConfigioresp(0);
				maespaConfigConfileDat.setConfigiohist(0);
				maespaConfigConfileDat.setConfigIsunla(0);
				
				maespaConfigConfileDat.setConfigIowatbal(1);
				maespaConfigConfileDat.setConfigIopoints(1);

				maespaConfigConfileDat.setConfigStartdate(maespaStartDate);
				maespaConfigConfileDat.setConfigEnddate(maespaEndDate);
				
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
			
				String confileDatStr = maespaConfigConfileDat.generateFile();		
//				maespaConfigConfileDat.writeConfigFile(inputDirectory);
				common.writeFile(confileDatStr, inputDirectory + "confile.dat");
				
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

				common.writeFile(maespaConfigConfileDat.getWatbalDataFile(), inputDirectory + "/" + "watbal.dat");
//				maespaConfigConfileDat.writeWatbalConfigFile(inputDirectory);		
				common.writeFile(watparsConfigFile, inputDirectory + "/" + "watpars.dat");
//				maespaConfigConfileDat.writeWatparsConfigFile(watparsConfigFile, inputDirectory);
				

//				String startDate ="31/12/03";
//				String endDate ="31/12/03";
//				int timesteps = loadedMetData.size();
//				int days = timesteps/48;
//				
//				double[] firstDay = loadedMetData.get(0);
//				double[] lastDay = loadedMetData.get(loadedMetData.size()-1);
//				
//				long firstMillis = Math.round(firstDay[ConfigForcingDat.MILLIS]);
//				int[] firstDateData = getStartYearDay(firstMillis);
//				long lastMillis = Math.round(lastDay[ConfigForcingDat.MILLIS]);
//				int[] lastDateData = getStartYearDay(lastMillis);
				
				//first generate the header to the forcing data file
				String fileTemplate = getMetDatTemplate(maespaStartDate, maespaEndDate, 
						differtialShadingValue, lat, lon,
						northSouth, eastWest, tzlong);



				// cache met files since they take a while to generate
//				MaespaSingleTreeMetData metData = null;
//				if (differtialShadingValue == MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE && cachedMetFileDiffuse == null )
//				{
//				metData = new MaespaSingleTreeMetData(fileTemplate, maespaConfigConfileDat.getYear(), maespaConfigConfileDat.getDay(), 
//							maespaConfigConfileDat.getNumberOfDays() + 1,dataTable, runDirectory, configNumber, differtialShadingValue, configType, loadedMetData);

				String maespaMet = generateMaespaMetConfigFile(fileTemplate, differtialShadingValue, loadedMetData);
				
				String outputName = "met.dat";		
				writeGenericConfigFile(maespaMet, outputName, inputDirectory);
				
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
				common.writeFile(maespaConfigConfileDat.getPointsDataFile(maespaConfigConfileDat), inputDirectory + "/" + "points.dat");
//				maespaConfigConfileDat.writePointsConfigFile(inputDirectory, maespaConfigConfileDat);
			}	
			
			public static String getMetDatTemplate(String startDate, String endDate, int differtialShadingValue, String lat, String lon, String northSouth, String eastWest, String tzlong)
			{
		
				
				String fileTemplate = "Met file for SINGLE TREE IN 4x4 with full radiation " + '\n' +
						"" + '\n' +
						"&environ		" + '\n' +
						"difsky = 0.0" + '\n' +
						"SWMIN = 0.05" + '\n' +
						"SWMAX = 0.36" + '\n' +
						"/		" + '\n' +
						"" + '\n' +
						"&latlong		" + '\n' +
						"lat="
						+ lat
						+ '\n' +
						"long="
						+ lon
						+ '\n' +
						"tzlong="
						+ tzlong
						+ "		" + '\n' +
						"lonhem='"
						+ eastWest
						+ "'		" + '\n' +
						"lathem='"
						+ northSouth
						+ "'		" + '\n' +
						"/		" + '\n' +
						"" + '\n' +
						"&metformat		" + '\n' +
						"dayorhr=1												" + '\n' +
						"khrsperday=48" + '\n' +
						"nocolumns=9" + '\n' +
						"startdate='"
						+ startDate
						+ "'												" + '\n' +
						"enddate='"
						+ endDate
						+ "'												" + '\n' +
						"columns=	'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'	" + '\n' +
						"/												" + '\n' +
						"" + '\n' +
						"DATA STARTS" + '\n' ; 
				
				if (differtialShadingValue == MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE)
				{	
					fileTemplate = "Met file for SINGLE TREE IN 4x4 with diffuse radiation only " + '\n' +
							"" + '\n' +
							"&environ		" + '\n' +
							"difsky = 0.0" + '\n' +
							"SWMIN = 0.05" + '\n' +
							"SWMAX = 0.36" + '\n' +
							"/		" + '\n' +
							"" + '\n' +
							"&latlong		" + '\n' +
							"lat="
							+ lat
							+ '\n' +
							"long="
							+ lon
							+ '\n' +
							"tzlong="
							+ tzlong
							+ "		" + '\n' +
							"lonhem='"
							+ eastWest
							+ "'		" + '\n' +
							"lathem='"
							+ northSouth
							+ "'		" + '\n' +
							"/		" + '\n' +
							"" + '\n' +
							"&metformat		" + '\n' +
							"dayorhr=1												" + '\n' +
							"khrsperday=48" + '\n' +
							"nocolumns=10" + '\n' +
							"startdate='"
							+ startDate
							+ "'												" + '\n' +
							"enddate='"
							+ endDate
							+ "'												" + '\n' +
							"columns=	'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT' 'FBEAM'	" + '\n' +
							"/												" + '\n' +
							"" + '\n' +
							"DATA STARTS" + '\n' ; 
				}

				
				return fileTemplate;
			}			
			
			public void writeGenericConfigFile(String metData, String outputName, String inputDirectory)
			{
				common.createDirectory( inputDirectory);				
				common.writeFile(metData,  inputDirectory + "/" + outputName);
			}
			
			private String generateMaespaMetConfigFile(String fileTemplate, int differtialShadingValue, ArrayList<double[]> metData)
			{
				
//				ConfigForcingDat configForcingDat = new ConfigForcingDat(runDirectory, year, runPrefix, day, numberOfDays);
//				ArrayList<TreeMap<String,String>> dataForDay = configForcingDat.getWeatherData(dataTable);
				
				StringBuilder st = new StringBuilder();
				st.append(fileTemplate);
				
				//'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'	
				boolean foundStartDate = false;
				for (double[] data : metData)
				{
					long millis = Math.round(data[MET_DATE]);
					double kdown = data[MET_KD];
					double ldown = data[MET_LD];
					double temp = data[MET_TA];
					double eaFrc = data[MET_QAIR];
					double windSpd = data[MET_WS];
					double windDir = data[MET_WD];
					double pressure = data[MET_P];
					double rainf = data[MET_RAINF];
					double rh = data[MET_RH];
					
					Date date = new Date(millis);
					String dateStr = date.toGMTString();
					
					
					
//					int[] yearDay = getStartYearDay(millis);
//					if (foundStartDate)
//					{}
//					else if ( yearDay[0] == year && yearDay[1]==day)
//					{
//						foundStartDate = true;
//					}
//					else
//					{
//						continue;
//					}
					
					
					
					//rainf is in kg/m2/s, convert to mm/30 minutes
					double ppt = convertRainfToMM(rainf, 30);
					
//					int[] dateData = getStartYearDay(millis);
//					int dayOfYear = dateData[1];
					
					Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));  //TODO make sure this is right
					localCalendar.setTimeInMillis(millis);	
//					int month = localCalendar.get(Calendar.MONTH)+1 ;
				    int dayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
//				    int year = localCalendar.get(Calendar.YEAR);
					
					
					double rad = kdown;
					
					String ca = "450";
					
					double Tc = new Double(temp).doubleValue();
					double RH = new Double(rh).doubleValue();
					
					double dewPoint = (Tc - (14.55 + 0.114 * Tc) * (1 - (0.01 * RH)) - 
							                Math.pow(((2.5 + 0.007 * Tc) * (1 - (0.01 * RH))) , 3)
											//((2.5 + 0.007 * Tc) * (1 - (0.01 * RH))) ^ 3 
											- (15.9 + 0.117 * Tc) * 
											Math.pow((1 - (0.01 * RH)), 14)
											//(1 - (0.01 * RH)) ^ 14
											) ;
					
					dewPoint = common.roundToDecimals(dewPoint, 2);
					
					//differential shading
					//changing to 20% for total diffuse
					if (differtialShadingValue == MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE)
					{
						//rad = "0.";
//						rad = tufCommon.percentOfStrRounded(rad, CONFIG_TYPE_DIFFUSE_PERCENT, ROUND_TO_PRECISION);
						
//						if (diffuseIrradiance.equals(""))
//						{
//							rad = common.percentOfStrRounded(rad, CONFIG_TYPE_DIFFUSE_PERCENT, ROUND_TO_PRECISION);
//						}
//						else
//						{
//							rad = diffuseIrradiance;
//						}
						rad = rad * MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE_PERCENT;
					}
					//this is no longer used
//					//changing to 60%
//					if (differtialShadingValue == CONFIG_TYPE_50_PERCENT)
//					{
//						rad = tufCommon.percentOfStrRounded(rad, CONFIG_TYPE_50_PERCENT_PERCENT, ROUND_TO_PRECISION);
//					}
					//otherwise it is 100% radiation
					
					rad = common.roundToDecimals(rad, 2);
					
					double fbeam = 0.01;
					if (differtialShadingValue == MaespaSingleTreeMetData.CONFIG_TYPE_DIFFUSE)
					{
						st.append(dayOfYear + Common.tab 
								+ rad + Common.tab
								+ temp + Common.tab
								+ pressure + Common.tab
								+ rh + Common.tab
								+ dewPoint + Common.tab
								+ windSpd + Common.tab
								+ ca + Common.tab
								+ ppt + Common.tab
								+ fbeam
//								+ Common.tab + dateStr  //TODO  take these out
								);
					}
					else
					{
						st.append(dayOfYear + Common.tab 
							+ rad + Common.tab
							+ temp + Common.tab
							+ pressure + Common.tab
							+ rh + Common.tab
							+ dewPoint + Common.tab
							+ windSpd + Common.tab
							+ ca + Common.tab
							+ ppt 
//							+ Common.tab + dateStr  //TODO  take these out
							);
					}
					st.append(Common.linefeed);
				}
				
				return st.toString();
			}	
		public int[] getStartYearDay(long millis)
		{
			Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+10:00"));
			localCalendar.setTimeInMillis(millis);	
			int month = localCalendar.get(Calendar.MONTH)+1 ;
		    int dayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
		    int year = localCalendar.get(Calendar.YEAR);

		    return new int[]{year,dayOfYear,month};
		}				
		public double convertRainfToMM(double rainf, int timestep)
		{
			//30 minutes in seconds // assume that timestep is 30 minutes
			int sec = 60*timestep;
			double mm = rainf * sec;		
			return common.roundToDecimals(mm, 2);
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
		
		public String[] formatMetDatesToMaespa(ArrayList<double[]> metData)
		{
			Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00")); 

			double[] firstLine = metData.get(0);
			double[] lastLine = metData.get(metData.size()-1);
			long firstMilli = Math.round(firstLine[0]);		
			localCalendar.setTimeInMillis(firstMilli);	
		    int firstDay = localCalendar.get(Calendar.DAY_OF_MONTH);
		    int firstDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
		    int firstMonth = localCalendar.get(Calendar.MONTH)+1;
		    int firstYear = localCalendar.get(Calendar.YEAR);
		    int firstYear2Digit;
		    if (firstYear < 2000)
		    {
		    	firstYear2Digit = firstYear - 1900;
		    }
		    else
		    {
		    	firstYear2Digit = firstYear - 2000;
		    }
			
		    long lastMilli = Math.round(lastLine[0]);
			localCalendar.setTimeInMillis(lastMilli);	
		    int lastDay = localCalendar.get(Calendar.DAY_OF_MONTH);
		    int lastMonth = localCalendar.get(Calendar.MONTH)+1;
		    int lastYear = localCalendar.get(Calendar.YEAR);
		    int lastYear2Digit;
		    if (lastYear < 2000)
		    {
		    	lastYear2Digit = lastYear - 1900;
		    }
		    else
		    {
		    	lastYear2Digit = lastYear - 2000;
		    }
		    
		    long milliDuration = lastMilli - firstMilli;
		    double daysDuration = milliDuration / dayMilli;
		    long numberOfDays = Math.round(Math.floor(daysDuration));
		     
		    return new String[]{common.padLeft(firstDay, 2, '0'), common.padLeft(firstMonth, 2, '0'), common.padLeft(firstYear2Digit, 2, '0'), 
		    		common.padLeft(lastDay, 2, '0'), common.padLeft(lastMonth, 2, '0'), common.padLeft(lastYear2Digit, 2, '0') ,
		    		firstDayOfYear+"", numberOfDays+""};
		}
		
		public String[] formatMetDatesToMaespa(String start, String end)
		{
			// 2012-12-31 23:00:00 to 
			// 31/12/03  //  DD/MM/YY
			
			String[] startSplit1 = start.split(" ");
			String[] startSplit2 = startSplit1[0].split("-");
			String yearStart = startSplit2[0];
			String monthStart = startSplit2[1];
			String dayStart = startSplit2[2];
			String year2CharStart = yearStart.substring(2,4);
			
			String[] endSplit1 = end.split(" ");
			String[] endSplit2 = endSplit1[0].split("-");
			String yearEnd = endSplit2[0];
			String monthEnd = endSplit2[1];
			String dayEnd = endSplit2[2];
			String year2CharEnd = yearEnd.substring(2,4);
			
			return new String[]{dayStart, monthStart, year2CharStart, dayEnd, monthEnd, year2CharEnd };
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
	
		public int[] getBuildingHeightArray1D()
		{
			return buildingHeightArray1D;
		}
	
		public void setBuildingHeightArray1D(int[] buildingHeightArray1D)
		{
			this.buildingHeightArray1D = buildingHeightArray1D;
		}
	
		public int[] getVegHeightArray1D()
		{
			return vegHeightArray1D;
		}
	
		public void setVegHeightArray1D(int[] vegHeightArray1D)
		{
			this.vegHeightArray1D = vegHeightArray1D;
		}
	
		public int[] getVegTypeArray1D()
		{
			return vegTypeArray1D;
		}
	
		public void setVegTypeArray1D(int[] vegTypeArray1D)
		{
			this.vegTypeArray1D = vegTypeArray1D;
		}
	
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
	
		public int[] getTreesArray1D()
		{
			return treesArray1D;
		}
	
		public void setTreesArray1D(int[] treesArray1D)
		{
			this.treesArray1D = treesArray1D;
		}
	
		public int[][] getTreesArray()
		{
			return treesArray;
		}
	
		public void setTreesArray(int[][] treesArray)
		{
			this.treesArray = treesArray;
		}
		private String forcingSource="Preston";
	    private int highestBuildingHeight;
		private int[][] buildingHeightArray;
		private int[][] vegHeightArray;
		private int[][] vegTypeArray;
		private int[][] treesArray;
		
		private int[] buildingHeightArray1D;
		private int[] vegHeightArray1D;
		private int[] vegTypeArray1D;
		private int[] treesArray1D;
		
//		private int configTreeMapCentralArrayLength;
//		private int configTreeMapCentralWidth;
//		private int configTreeMapCentralLength;
//		private int configTreeMapX;
//		private int configTreeMapY;
		int configTreeMapX1;
		int configTreeMapX2;
		int configTreeMapY1;
		int configTreeMapY2;
		private double configTreeMapGridSize;
//		private int configTreeMapNumsfcab;	
		private double buildingHeightAverage;
		private double treeHeightAverage;		
		
		public ArrayList<double[]> trimIncompleteDaysFromMet(ArrayList<double[]> metData)
		{
			int firstTrimPoint = 0;
			int lastTrimPoint = metData.size();
			
			Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00")); 

			double[] firstLine = metData.get(0);
			double[] lastLine = metData.get(metData.size()-1);
			long firstMilli = Math.round(firstLine[0]);		
			localCalendar.setTimeInMillis(firstMilli);	
		    int firstDay = localCalendar.get(Calendar.DAY_OF_YEAR);
			long lastMilli = Math.round(lastLine[0]);
			localCalendar.setTimeInMillis(lastMilli);	
		    int lastDay = localCalendar.get(Calendar.DAY_OF_YEAR);
		    
			for (int i=0;i<48;i++)
			{
				double[] nextLine = metData.get(i);
				long nextMilli = Math.round(nextLine[0]);
				localCalendar.setTimeInMillis(nextMilli);	
			    int nextDay = localCalendar.get(Calendar.DAY_OF_YEAR);
			    if (nextDay != firstDay)
			    {
			    	firstTrimPoint = i;
			    	break;
			    }
			}
		    

			return new ArrayList<double[]>(metData.subList(firstTrimPoint, lastTrimPoint));
		}


		public ArrayList<double[]> interpolateMetData(ArrayList<double[]> metData, String timestep_interval_seconds, double localOffset, String time_analysis_start)
		{
			
			String[] startTimeSplit = time_analysis_start.split(" ");
				
			String date = startTimeSplit[0];  /// 1993-01-01
			String[] dateSplit = date.split("-");
			String yearStr = dateSplit[0];
			String monthStr = dateSplit[1];
			String dayStr = dateSplit[2];
			Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+0:00"));
			localCalendar.set(Integer.parseInt(yearStr), Integer.parseInt(monthStr)-1, Integer.parseInt(dayStr));
			//start the met data 7 days before analysis start
			long milliOfStart = localCalendar.getTimeInMillis() - (7 * Math.round(dayMilli));  
			//TODO testing, change this back
//			milliOfStart = 0l;
			
			int metDataSize = metData.size();
//			metDataSize = 100;  //TODO reset this
		
			double localOffsetMilli = (localOffset*60.0*minuteToMillisecond);
			
			ArrayList<double[]> newMetData = new ArrayList<double[]>();
			if (timestep_interval_seconds.startsWith("1800"))
			{
				for (int i=0;i<metDataSize;i++)
				{
					double[] dataLine = metData.get(i);
					if (dataLine[0]+localOffsetMilli > milliOfStart)
					{
						dataLine[0]=dataLine[0] + localOffsetMilli;
						newMetData.add(dataLine);
					}
				
				}

			}
			// if every hour, interpolate an extra 30 minutes between
			else if (timestep_interval_seconds.startsWith("3600"))
			{
				for (int i=0;i<metDataSize;i++)
				{
			
					

					if (i >= metDataSize-1)
					{
						double[] nextDataLine = metData.get(i);		
						if (metData.get(i)[0] + localOffsetMilli > milliOfStart)
						{
					
							newMetData.add( new double[]{ 
									metData.get(i)[0] + localOffsetMilli,
									metData.get(i)[1],
									metData.get(i)[2],
									metData.get(i)[3],
									metData.get(i)[4],
									metData.get(i)[5],
									metData.get(i)[6],
									metData.get(i)[7],
									metData.get(i)[8],
									metData.get(i)[9]							
								}
							);
							

							nextDataLine[0]=nextDataLine[0] + thirtyMinuteMilli  + localOffsetMilli;
	 
							newMetData.add( new double[]{ 
									metData.get(i)[0] + thirtyMinuteMilli  + localOffsetMilli,
									metData.get(i)[1],
									metData.get(i)[2],
									metData.get(i)[3],
									metData.get(i)[4],
									metData.get(i)[5],
									metData.get(i)[6],
									metData.get(i)[7],
									metData.get(i)[8],
									metData.get(i)[9]							
								}
							);
						}

					}
					else
					{

						if (metData.get(i)[0] + localOffsetMilli > milliOfStart)
						{
							newMetData.add( new double[]{ 
									metData.get(i)[0] + localOffsetMilli,
									metData.get(i)[1],
									metData.get(i)[2],
									metData.get(i)[3],
									metData.get(i)[4],
									metData.get(i)[5],
									metData.get(i)[6],
									metData.get(i)[7],
									metData.get(i)[8],
									metData.get(i)[9]							
								}
							);
							

							newMetData.add( new double[]{ 
									metData.get(i)[0] + thirtyMinuteMilli  + localOffsetMilli,
									(metData.get(i)[1]+metData.get(i+1)[1])/2.,
									(metData.get(i)[2]+metData.get(i+1)[2])/2.,
									(metData.get(i)[3]+metData.get(i+1)[3])/2.,
									(metData.get(i)[4]+metData.get(i+1)[4])/2.,
									(metData.get(i)[5]+metData.get(i+1)[5])/2.,
									(metData.get(i)[6]+metData.get(i+1)[6])/2.,
									(metData.get(i)[7]+metData.get(i+1)[7])/2.,
									(metData.get(i)[8]+metData.get(i+1)[8])/2.,
									(metData.get(i)[9]+metData.get(i+1)[9])/2.							
								}
							);
						}
					
					}
				}
			}
			return newMetData;
		}
				
}
