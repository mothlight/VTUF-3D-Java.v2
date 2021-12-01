package VTUF3D.ConfigMaker.TARGET;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.TreeMap;

import VTUF3D.ConfigMaker.UrbanPlumberPhase2ConfigMaker;
import VTUF3D.Utilities.Common;

public class UrbanPlumberPhase2TargetConfigGen
{
	
	UrbanPlumberPhase2ConfigMaker urbanPlumber = new UrbanPlumberPhase2ConfigMaker();
	String tab = "\t";
	String linefeed = "\n";
	String comma = ",";
	Common common = new Common();
//	String prestonMet = "/home/kerryn/git/2020-06-UrbanPlumber/AU-Preston/AU-Preston_metforcing_v1.txt";
//	String prestonMetOutput = "/home/kerryn/git/2020-06-UrbanPlumber/AU-Preston/Preston/input/Preston/MET/AU_Preston.csv";
	
	double minuteToMillisecond = 60000.0;
	double thirtyMinuteMilli = (30.0*minuteToMillisecond);
	double dayMilli = minuteToMillisecond * 60.0 * 24.0;
	
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
	
	public String sitesDirectory = "/home/kerryn/git/2021-05-UrbanPlumberPhase2/UrbanPlumberPhase2/Urban-PLUMBER_sites_txt/";
	public String runsBaseDirectory = "/home/kerryn/git/2021-05-UrbanPlumberPhase2/UrbanPlumberPhase2/TARGET/runs2/";
	String scriptFilename = "run_model4.sh";	
	String scriptFile = runsBaseDirectory + scriptFilename;
	
	public static void main(String[] args)
	{
		UrbanPlumberPhase2TargetConfigGen u = new UrbanPlumberPhase2TargetConfigGen();
		u.process();
//		u.processTargetMet();

	}
	
	public void process()
	{
		// blank out the script, each run will be appended to this file
		common.createDirectory(runsBaseDirectory);
		String scriptHeader = 		
				"cd /home/kerryn/git/2020-06-UrbanPlumber/OutputToSubimt/TARGET_Preston/TARGET/src" + linefeed + 
				"sh compile_code.sh" + linefeed + linefeed;
		common.writeFile(scriptHeader, scriptFile);
		
		
		ArrayList<String[]> siteDataFiles = new ArrayList<String[]>();
		String[] sitesDirList = common.getDirectoryList(sitesDirectory);
		for (String siteFile : sitesDirList)
		{	
////			//TODO get rid of this
//			if (siteFile.contains("Sunset"))
//			{
//				continue;
//			}
//			else
//			{
////				continue;
//			}
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
			TreeMap<String,Double> siteData = urbanPlumber.readSiteData(siteFiles[1]);
			ArrayList<double[]> metData = urbanPlumber.urbanPlumberMetReformat(siteFiles[0]);
			TreeMap<String,String> metadata = urbanPlumber.getMetDataMetadata(siteFiles[0]);
			System.out.println(metadata.toString());
			
			double latitude = siteData.get(LATITUDE);
			double longitude = siteData.get(LONGITUDE);
			double measurement_height_above_ground = siteData.get(MEASUREMENT_HEIGHT_ABOVE_GROUND);
			double impervious_area_fraction = siteData.get(IMPERVIOUS_AREA_FRACTION);
			double tree_area_fraction = siteData.get(TREE_AREA_FRACTION);
			double grass_area_fraction = siteData.get(GRASS_AREA_FRACTION);
			double bare_soil_area_fraction = siteData.get(BARE_SOIL_AREA_FRACTION);
			double water_area_fraction = siteData.get(WATER_AREA_FRACTION);
			double roof_area_fraction = siteData.get(ROOF_AREA_FRACTION);
			double road_area_fraction = siteData.get(ROAD_AREA_FRACTION);
			double other_paved_area_fraction = siteData.get(OTHER_PAVED_AREA_FRACTION);
			double building_mean_height = siteData.get(BUILDING_MEAN_HEIGHT);
			double tree_mean_height = siteData.get(TREE_MEAN_HEIGHT);
			double average_albedo_at_midday = siteData.get(AVERAGE_ALBEDO_AT_MIDDAY);
			double anthropogenic_heat_flux_mean = siteData.get(ANTHROPOGENIC_HEAT_FLUX_MEAN);
			double canyon_height_width_ratio = siteData.get(CANYON_HEIGHT_WIDTH_RATIO);
			
			String siteName = metadata.get("sitename");
			String time_coverage_start = metadata.get("time_coverage_start");  // 1998-01-01 00:00:00
			String time_analysis_start = metadata.get("time_analysis_start");  // 1998-01-01 00:00:00
			String time_coverage_end = metadata.get("time_coverage_end");  // 2012-12-31 23:00:00
			String time_in = metadata.get("time_shown_in");  // UTC
			String local_offset = metadata.get("local_utc_offset_hours");  // 1.0
			String timestep_interval_seconds = metadata.get("timestep_interval_seconds");  // 3600.0
			
			double localOffset = Double.parseDouble(local_offset);
			
			// if there are hourly, interpolate to 30 minutes
			metData =  urbanPlumber.interpolateMetData(metData, timestep_interval_seconds, localOffset, time_analysis_start);
			metData = urbanPlumber.trimIncompleteDaysFromMet(metData);
			
			String metDataFileContents = processTargetMet(metData);
		
			String[] startAndEndDates = getStartAndEndDates(metData);
			
//			double vegHt = tree_mean_height;
//			double bldHt = building_mean_height;
//			double building = roof_area_fraction*100;
//			double road = (road_area_fraction + other_paved_area_fraction)*100;
//			double tree = (tree_area_fraction + water_area_fraction)*100;
//			double grass = (grass_area_fraction + bare_soil_area_fraction)*100;
//			double total = building + road + tree + grass;
//			double forcingHeight = measurement_height_above_ground;
			
			ToolkitControlFile tc = new ToolkitControlFile();
//			runsBaseDirectory = "/home/kerryn/git/2021-05-UrbanPlumberPhase2/UrbanPlumberPhase2/TARGET/runs/";
			String runDirectory = runsBaseDirectory + siteName + "/";
			String outputDir = runDirectory  + "output/";
			String lcFileName = runDirectory + siteName + "_LC.csv";
			String metFileName = runDirectory + siteName + "_met.csv";
			String controlFileName = runDirectory + siteName + "_control.txt";
			
			tc.setDate1(startAndEndDates[0]);
			tc.setDate1a(startAndEndDates[0]);
			tc.setDate2(startAndEndDates[1]);
			tc.setLat(latitude);
			tc.setLcFileName(lcFileName);
			tc.setLon(longitude);
			tc.setMetFileName(metFileName);
			tc.setOutputDir(outputDir);
			tc.setRunName(siteName);
			tc.setSiteName(siteName);
			String controlFileContent = tc.getControlFile();
			
			common.createDirectory(runDirectory);
			common.createDirectory(outputDir);
			
			//Land cover
//			FID,roof,road,watr,conc,Veg,dry,irr,H,W
//			0,0.445,0.13,0,0.045,0.225,0.005,0.15,6.05,14.4
			
		
			
			double H = Math.max(building_mean_height, tree_mean_height);	
			double W = H/canyon_height_width_ratio;
			double roofFraction=roof_area_fraction;
			double roadFraction=road_area_fraction;
			double waterFraction=water_area_fraction;
			double concreteFraction=other_paved_area_fraction;
			double vegetationFraction=tree_area_fraction;
			double dryFraction=bare_soil_area_fraction;
			double irrigatedFraction=grass_area_fraction;
			//land cover fractions like 0.445
			String landCoverFileContent = "FID,roof,road,watr,conc,Veg,dry,irr,H,W" + linefeed
					+ "0,"
					+ roofFraction
					+ ","
					+ roadFraction
					+ ","
					+ waterFraction
					+ ","
					+ concreteFraction
					+ ","
					+ vegetationFraction
					+ ","
					+ dryFraction
					+ ","
					+ irrigatedFraction
					+ ","
					+ H
					+ ","
					+ W 
					+ linefeed;
			
			common.writeFile(controlFileContent, controlFileName);
			common.writeFile(landCoverFileContent, lcFileName);
			common.writeFile(metDataFileContents, metFileName);
			
			String script = 

			"cd /home/kerryn/git/2020-06-UrbanPlumber/OutputToSubimt/TARGET_Preston/TARGET/src" + linefeed + 
			"java -cp /home/kerryn/git/Target_Java/netcdfAll-4.6.11.jar:. Target.RunToolkit "
			+ controlFileName
			+ linefeed 
			+ linefeed;
			
			common.appendFile(script, scriptFile);
			
		}
	}
	
	public String[] getStartAndEndDates(ArrayList<double[]> metData)
	{
		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00")); 

		double[] firstLine = metData.get(0);
		double[] lastLine = metData.get(metData.size()-1);
		long firstMilli = Math.round(firstLine[0]);		
		localCalendar.setTimeInMillis(firstMilli);	
	    int firstDay = localCalendar.get(Calendar.DAY_OF_MONTH);
//	    int firstDayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
	    int firstMonth = localCalendar.get(Calendar.MONTH)+1;
	    int firstYear = localCalendar.get(Calendar.YEAR);
	    int firstHour = localCalendar.get(Calendar.HOUR_OF_DAY);

		
	    long lastMilli = Math.round(lastLine[0]);
		localCalendar.setTimeInMillis(lastMilli);	
	    int lastDay = localCalendar.get(Calendar.DAY_OF_MONTH);
	    int lastMonth = localCalendar.get(Calendar.MONTH)+1;
	    int lastYear = localCalendar.get(Calendar.YEAR);
	    int lastHour = localCalendar.get(Calendar.HOUR_OF_DAY);

	    String start = common.padLeft(firstYear, 4, '0') + comma + common.padLeft(firstMonth, 2, '0') + comma + common.padLeft(firstDay, 2, '0') + comma + firstHour;
	    String end = common.padLeft(lastYear, 4, '0') + comma + common.padLeft(lastMonth, 2, '0') + comma + common.padLeft(lastDay, 2, '0') + comma + lastHour;
	     
	    return new String[]{start,end};
	}
	
	public String getStartDates(String startTime)
	{
		 // 1998-01-01 00:00:00
		// and return 2004,11,30,0 
		String[] dateTime = startTime.split(" ");
		String time = dateTime[1];
		String date = dateTime[0];
		String[] timeSplit = time.split(":");
		String hour = timeSplit[0];
		String minute = timeSplit[1];
//		String reformattedTime = timeSplit[0] + ":" + timeSplit[1];
		String[] dateSplit = date.split("-");
		String year = dateSplit[0];
		String month = dateSplit[1];
		String day = dateSplit[2];
		return year + comma + month + comma + day + comma + hour;
	}

	public String processTargetMet(ArrayList<double[]> metData)
	{
		StringBuffer sb = new StringBuffer();
		String header = "datetime,Ta,RH,WS,P,Kd,Ld";
		sb.append(header + linefeed);
		
//		ArrayList<String> metFileContents = common.readLargerTextFileAlternateToArray(prestonMet);
		for (double[] line : metData)
		{
	
//			double[] dataLine = new double[]{reformattedDatetime,Kd,Ld,Ta,QairValue*100.0,WS,WD,P,RH,RainfValue};
			
			long millis = Math.round(line[MET_DATE]);
			double Kd = line[MET_KD];
			double Ld = line[MET_LD];
			double Ta = line[MET_TA];
//			double eaFrc = line[MET_QAIR];
			double WS = line[MET_WS];
//			double windDir = line[MET_WD];
			double P = line[MET_P];			
			double RH = line[MET_RH];
//			double rainf = line[MET_RAINF];
			
			// TARGET needs:
//			datetime,Ta,RH,WS,P,Kd,Ld
//			18/01/1993 0:00, 22.3, 49,  9,1016.2,0.0,378.86
			String reformattedDatetime = getTargetFormattedDatetime(millis, "+00");
			String outputLine = reformattedDatetime + comma + Ta + comma + RH + comma + WS + comma + P + comma + Kd + comma + Ld;

//			System.out.println(outputLine);
			sb.append(outputLine + linefeed);
		}
		
//		common.writeFile(sb.toString(), prestonMetOutput);
		return sb.toString();
	}
	
	public String getTargetFormattedDatetime(long millis, String timezone)
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

	public static long getUTCDateFromLocal(long localDate) 
	{
	  TimeZone tz = TimeZone.getDefault();
	  long gmtOffset = tz.getOffset(localDate);
	  return localDate + gmtOffset;
	}
}
