package VTUF3D.Utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

import VTUF3D.MaespaDataResults;
import VTUF3D.MaespaTreeConfiguration;

import VTUF3D.Utilities.configs.Melbourne1Min30SolarData;






public class MaespaAnalysis
{
	
	public static String YEAR = "Year";
	public static String DAY_OF_YEAR = "Day_of_year";
	public static String TIME = "Time";
	public static String TIMECODE = "Timecode";
	public static String MONTH = "Month";
	public static String WEEK = "Week";
	public static String KDOWN = "Kdown";
	public static String KUP = "Kup";
	public static String LDOWN = "Ldown";
	public static String LUP = "Lup";
	public static String NET = "NET";
	public static String QH = "QH";
	public static String QE = "QE";
	public static String QG = "QG";
	public static String FLUX_VALIDITY = "Flux_validity";
	public static String CO2_FLUX_FINAL = "CO2_flux_final";
	public static String CO2_FLUX_VALIDITY = "CO2_flux_validity";
	public static String TEMP = "Temp";
	public static String E_A = "e_a";
	public static String WIND_SPD = "Wind_spd";
	public static String WIND_DIR = "Wind_dir";
	public static String PRESSURE = "Pressure";
	public static String PRECIP = "Precip";
	public static String ANTHROP = "Anthrop";
	public static String TAU = "tau";
	public static String SOIL_MOISTURE = "Soil_moisture";
	public static String DEEP_SOIL_TEMP = "Deep_soil_temp";
	public static String RH = "RH";
	public static String EXT_WATER = "extwater";
	
	public static String STATION_NUMBER = "\"Station Number\"";	
	public static String YEAR_MONTH_DAY = "\"Year Month Day Hours Minutes in YYYY MM DD HH24 MI format in Local standard time\"";
	public static String PRECIPITATION = "\"Precipitation in last 10 minutes in mm\"";
	public static String AIR_TEMPERATURE = "\"Air Temperature in degrees C\"";
	public static String RELATIVE_HUMIDITY = "\"Relative humidity in percentage %\"";
	public static String VAPOUR_PRESSURE = "\"Vapour pressure in hPa\"";
	public static String WIND_SPEED = "\"Wind speed in km/h\"";
	public static String WIND_DIRECTION = "\"Wind direction in degrees true\"";
	public static String MEAN_SEA_LEVEL_PRESSURE = "\"Mean sea level pressure in hPa\"";
	
	public static String STATION_NUMBER_COL = "Station Number";
	public static String YEAR_MONTH_DAY_COL = "Year Month Day Hours Minutes in YYYY MM DD HH24 MI format in Local standard time";
	public static String PRECIPITATION_COL = "Precipitation in last 10 minutes in mm";
	public static String AIR_TEMPERATURE_COL = "Air Temperature in degrees C";
	public static String RELATIVE_HUMIDITY_COL = "Relative humidity in percentage %";
	public static String VAPOUR_PRESSURE_COL = "Vapour pressure in hPa";
	public static String WIND_SPEED_COL = "Wind speed in km/h";
	public static String WIND_DIRECTION_COL = "Wind direction in degrees true";
	public static String MEAN_SEA_LEVEL_PRESSURE_COL = "Mean sea level pressure in hPa";
	
	public static String MELBOURNE_AIRPORT_STATION = " 23034";
	public static String ADELAIDE_AIRPORT_STATION = " 86282";
	
	public static String GLOBAL_IRRADIANCE = "Mean_global_irradiance_over_1_minute_in_W_sq_m";
	public static String DIRECT_IRRADIANCE = "Mean_direct_irradiance_over_1_minute_in_W_sq_m";
	public static String DIFFUSE_IRRADIANCE = "Mean_diffuse_irradiance_over_1_minute_in_W_sq_m";
	
	Common common = new Common();

	public static void main(String[] args)
	{
		MaespaAnalysis ma = new MaespaAnalysis();
//		ma.process();
		ma.getBomData();

	}
	
	public void getBomData()
	{
		int searchYear = 2004;
		String dayOfYear = "1";
		int numberOfDays = 364;
		boolean isDiffuse = false;
		String metDataFileStr = generateMaespaMetConfigFile(searchYear, dayOfYear, numberOfDays,isDiffuse);
		System.out.println(metDataFileStr);		
		String rootDirectory = "/home/kerryn/git/2018-09-MaespaResults/10/2/";
		String file = "met.dat";
		common.writeFile(metDataFileStr, rootDirectory + file);
		
		
		rootDirectory = "/home/kerryn/git/2018-09-MaespaResults/10/1/";
		isDiffuse = true;
		metDataFileStr = generateMaespaMetConfigFile(searchYear, dayOfYear, numberOfDays,isDiffuse);
		System.out.println(metDataFileStr);
		file = "met.dat";
		common.writeFile(metDataFileStr, rootDirectory + file);
	}
	

	
	public Connection getBomObservationsSqlite3Connection()
	{
		Connection conn = null;
		try
		{			
			Class.forName("org.sqlite.JDBC").newInstance();
			conn = DriverManager
					.getConnection("jdbc:sqlite:/" +							
							"/home/kerryn/Documents/Work/Data/BOMMelbourneAdelaideObservations/BOMObservations.sqlite3" );
		} catch (Exception e)
		{
			e.printStackTrace();			
		}

		return conn;
	}
	
	public ArrayList<TreeMap<String,String>> getBOMDataForDay(int year, String dayOfYear, Connection conn, String station, Connection melSolarConn)
	{
		ArrayList<TreeMap<String,String>>  data = new ArrayList<TreeMap<String,String>> ();
		//  2017 06 24 14 00
		// year  dayofyear time  timecode month
		
//		int year = common.getYearFromTimecode(timecode);
//		int month = common.getMonthFromTimecode(timecode);
//		int day = common.getDayOfMonthFromTimecode(timecode);
//		int hour = common.getHourFromTimecode(timecode);
		int day = common.getDayOfMonthFromDayOfYear(year, dayOfYear);
		int month = common.getMonthFromDayOfYear(year, dayOfYear);
		
		String dayStr = common.padLeft(day, 2, '0');
		String monthStr = common.padLeft(month, 2, '0');
		
	
		
	String query = 	
		" select " 
	+ " " + STATION_NUMBER + "," +	
	 " " + YEAR_MONTH_DAY + "," +
	 " " + PRECIPITATION + "," +
	 " " + AIR_TEMPERATURE + "," +
	 " " + RELATIVE_HUMIDITY + "," +
	 " " + VAPOUR_PRESSURE + "," +
	 " " + WIND_SPEED + "," +
	 " " + WIND_DIRECTION + "," +
	 " " + MEAN_SEA_LEVEL_PRESSURE + " " +

	 " from BOMObservations where  "
	 + STATION_NUMBER
	 + "='"
	 + station
	 + "'  " +
	" and ("
	+ YEAR_MONTH_DAY
	+ "  like '"
	+ year
	+ " "
	+ monthStr
	+ " "
	+ dayStr
	+ " "
	+ "%% 00"
	+ "' or " 
	+ YEAR_MONTH_DAY
	+ "  like '"
	+ year
	+ " "
	+ monthStr
	+ " "
	+ dayStr
	+ " "
	+ "%% 30"
	+ "' ) " 
	+ " order by "
	+ YEAR_MONTH_DAY
	+ " ";


	Melbourne1Min30SolarData solarClass = new Melbourne1Min30SolarData(year,day+"",melSolarConn);
	ArrayList<TreeMap<String,String>> solarDataArray = solarClass.getMelbourneSolarDataForDay();
	
	System.out.println(query);
	int count = 0;
	try
	{
		Statement stat = conn.createStatement();
		//Statement MelSolarStat = melSolarConn.createStatement();

		ResultSet rs = stat.executeQuery(query);
		while (rs.next())
		{
			TreeMap<String,String> item = new TreeMap<String,String>();
			String stationNumber = rs.getString(STATION_NUMBER_COL);
			String yearMonthDay = rs.getString(YEAR_MONTH_DAY_COL);
			String precipitation = rs.getString(PRECIPITATION_COL);
			String airTemperature = rs.getString(AIR_TEMPERATURE_COL);
			String relativeHumidity = rs.getString(RELATIVE_HUMIDITY_COL);
			String vapourPressure = rs.getString(VAPOUR_PRESSURE_COL);
			String windSpeed = rs.getString(WIND_SPEED_COL);
			String windDirection = rs.getString(WIND_DIRECTION_COL);
			String meanSeaLevelPressure = rs.getString(MEAN_SEA_LEVEL_PRESSURE_COL);
			
			item.put(STATION_NUMBER, stationNumber);
			item.put(YEAR_MONTH_DAY, yearMonthDay);
			item.put(PRECIPITATION, precipitation);
			item.put(AIR_TEMPERATURE, airTemperature);
			item.put(RELATIVE_HUMIDITY, relativeHumidity);
			item.put(VAPOUR_PRESSURE, vapourPressure);
			item.put(WIND_SPEED, windSpeed);
			item.put(WIND_DIRECTION, windDirection);
			item.put(MEAN_SEA_LEVEL_PRESSURE, meanSeaLevelPressure);
			
			TreeMap<String,String> solarData = solarDataArray.get(count);
			String globalIrradiance = solarData.get(GLOBAL_IRRADIANCE);
			String directIrradiance = solarData.get(DIRECT_IRRADIANCE);
			String diffuseIrradiance = solarData.get(DIFFUSE_IRRADIANCE);		

			item.put(GLOBAL_IRRADIANCE, globalIrradiance);
			item.put(DIRECT_IRRADIANCE, directIrradiance);
			item.put(DIFFUSE_IRRADIANCE, diffuseIrradiance);
			
			item.put(DAY_OF_YEAR, dayOfYear);
			
			data.add(item);
			count ++;
		}
		rs.close();
	} 
	catch (Exception e)
	{
		e.printStackTrace();
	}
	while (data.size() < 48)
	{
		System.out.println("Not enough data");
//		System.out.println(data.toString());
		data = addToData(data);
	}
	return data;
	}
	
	public void process()
	{
		
		MaespaTreeConfiguration treeConfig = new MaespaTreeConfiguration();
		TreeMap<String,ArrayList<MaespaDataResults>> returnValues = new TreeMap<String,ArrayList<MaespaDataResults>>();
		String rootDirectory = "/home/kerryn/git/2018-09-MaespaResults/";

        double gridSize=5;
        int treeFilesNumber=1;
        int diffShadingType =1;

        int  nr_points;
        int treeConfigLocation;
        String treeConfigLocationStr;
        String pointsfilestr;
   
        treeConfigLocation=1      ; 
        treeConfigLocationStr=treeConfigLocation+"";
        pointsfilestr = rootDirectory + treeConfigLocationStr + "/" + "1" + "/points.dat";
    	
		Namelist pointsnamelist = new Namelist(pointsfilestr);      
        nr_points = pointsnamelist.getIntValue("CONTROL", "NOPOINTS");
        

        MaespaDataFile data = readMaespaTestDataFiles(nr_points, treeConfigLocation, rootDirectory);
        double[] tdData = data.getDataArrayForVariable("TD");
        System.out.println("");
		
		ArrayList<MaespaDataResults> maespaHRWatData = treeConfig.readMaespaHRWatDataFiles(treeFilesNumber, gridSize, diffShadingType, rootDirectory);
		int count = 0;
		for (MaespaDataResults result : maespaHRWatData)
		{
			double tcan = result.getTCAN();
			double et = result.getEt();
			double soilevap = result.getSoilevap();
			double canopystore = result.getCanopystore();
			double evapstore = result.getEvapstore();
			double qe = result.getQeCalc5();
			double doy = result.getDOY();
			double hour = result.getHOUR();
			
			System.out.println(doy + "  " + hour + " " +
					tcan + " " + et + " " + soilevap + " " + canopystore + " " 
					+ evapstore  + " " + qe
					+ " " + tdData[count%2]);
			count ++;
		}
		


		
		
		
		
		
		
	}
    public MaespaDataFile readMaespaTestDataFiles(int nr_points, int treeConfigLocation, String rootDirectory)
    {

		 int linesToSkip;
		 String   treeConfigLocationStr;
		 String  testflxfilestr;    
		 treeConfigLocationStr=treeConfigLocation+"";
		 testflxfilestr = rootDirectory + treeConfigLocationStr+ "/1" + "/testflx.dat";         
		 linesToSkip = 21;         
		 MaespaDataFile data = new MaespaDataFile(testflxfilestr, linesToSkip);

 
		 return data;
    }
    
//    public void generateMaespaMet()
//    {
////    	String dataTable= "Preston";
//   
//    	String startDate="DUMMY VALUE";
//    	String endDate="DUMMY VALUE";
////    	String runDirectory;
////    	String runPrefix;
//    	
//		String lat = "37	47	0" ;
//		String lon = "144	58	0" ;
////		if (dataTable.equals("AdelaideAirport_35"))
////		{
////			lat = "34 9 0 ";
////			lon = "138 6 0";
////		}
//		
//		String fileTemplate = "Met file for SINGLE TREE IN 4x4 " + '\n' +
//				"" + '\n' +
//				"&environ		" + '\n' +
//				"difsky = 0.0" + '\n' +
//				"SWMIN = 0.05" + '\n' +
//				"SWMAX = 0.36" + '\n' +
//				"/		" + '\n' +
//				"" + '\n' +
//				"&latlong		" + '\n' +
//				"lat="
//				+ lat
//				+ '\n' +
//				"long="
//				+ lon
//				+ '\n' +
//				"tzlong=150		" + '\n' +
//				"lonhem='E'		" + '\n' +
//				"lathem='S'		" + '\n' +
//				"/		" + '\n' +
//				"" + '\n' +
//				"&metformat		" + '\n' +
//				"dayorhr=1												" + '\n' +
//				"khrsperday=48" + '\n' +
//				"nocolumns=9" + '\n' +
//				"startdate='"
//				+ startDate
//				+ "'												" + '\n' +
//				"enddate='"
//				+ endDate
//				+ "'												" + '\n' +
//				"columns=	'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'	" + '\n' +
//				"/												" + '\n' +
//				"" + '\n' +
//				"DATA STARTS" + '\n' ; 
//    	
//    	int year = 2004;
//    	String day = "1";
//    	int numberOfDays = 365;
////		MaespaSingleTreeMetData metData = new MaespaSingleTreeMetData(
////				fileTemplate, 
////				year, 
////				day, 
////				numberOfDays,
////				dataTable, runDirectory, runPrefix);
//		String outputName = "met.dat";	
//		String metDataContents = generateMaespaMetConfigFile(year, day, numberOfDays);
//		String fileContents = fileTemplate + metDataContents;
//
//
//    }
    
	private String generateMaespaMetConfigFile(int year, String day, int numberOfDays, boolean diffuse)
	{  
		String diffuseStr = "";
		if (diffuse)			
		{
			diffuseStr = '\t' + "0.01";
		}
		//  startdate='09/02/04'	
		//  enddate='10/03/04'	

    	String startDate=common.getMaespaYearMonthDayStrFromDate(year, day);
    	String endDate=common.getMaespaYearMonthDayStrFromDate(year, common.addDayToDay(day, numberOfDays-1));

		String lat = "37	47	0" ;
		String lon = "144	58	0" ;
		String colNumbers="";
		String colHeader = "";
		if (diffuse)
		{
			colNumbers = "10";
			colHeader = " 'FBEAM'	";
		}
		else
		{
			colNumbers = "9";
			colHeader = " ";
		}

		String fileTemplate = "Met file for SINGLE TREE IN 4x4 " + '\n' +
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
				"tzlong=150		" + '\n' +
				"lonhem='E'		" + '\n' +
				"lathem='S'		" + '\n' +
				"/		" + '\n' +
				"" + '\n' +
				"&metformat		" + '\n' +
				"dayorhr=1												" + '\n' +
				"khrsperday=48" + '\n' +
				"nocolumns="
				+ colNumbers + '\n' +
				"startdate='"
				+ startDate
				+ "'												" + '\n' +
				"enddate='"
				+ endDate
				+ "'												" + '\n' +
				"columns=	'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'"
				+ colHeader
				+ "" + '\n' +
				"/												" + '\n' +
				"" + '\n' +
				"DATA STARTS" + '\n' ; 
    	
//    	int year = 2004;
//    	String day = "1";
//    	int numberOfDays = 365;
//		MaespaSingleTreeMetData metData = new MaespaSingleTreeMetData(
//				fileTemplate, 
//				year, 
//				day, 
//				numberOfDays,
//				dataTable, runDirectory, runPrefix);
		String outputName = "met.dat";	
//		String metDataContents = generateMaespaMetConfigFile(year, day, numberOfDays);
		
		
		
		
		
		ArrayList<TreeMap<String,String>> dataForYear = new ArrayList<TreeMap<String,String>>();
		System.out.println("getWeatherData=" + year + " " + day + " " + numberOfDays);
			

		Connection bomConnection = getBomObservationsSqlite3Connection();
		Connection melSolarConn = getMelbourne1Min30SolarSqlite3Connection();
//		int searchYear = 2016;
//		String startDay = "33";
			
		

		
		
		for (int i=0;i<numberOfDays;i++)
		{	

			
			String currentDay = common.addDayToDay(day, i);
			
//			ArrayList<TreeMap<String,String>> tempData = null;
//			tempData = getPrestonData(year, currentDay);
			
			ArrayList<TreeMap<String,String>> tempData = getBOMDataForDay(year, currentDay, bomConnection, MELBOURNE_AIRPORT_STATION, melSolarConn);
			
			

			
//			System.out.println(tempData.toString());
			
			dataForYear.addAll(tempData);				
		}
		

		
		StringBuffer st = new StringBuffer();
		
		//'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'	
		
		String pressurePrev = "";
		String radPrev = "";
		String rhPrev = "";
		String tempPrev = "";
		String windSpdPrev = "";
		String pptPrev = "";

		for (TreeMap<String,String> dataForDay : dataForYear)
		{

			String dayOfYear = "";
//			String month = "";
//			String week = "";
			String rad = "";
			String rh = "";
			String temp = "";
//			String eaFrc = "";
			String windSpd = "";
			String ppt = "";
			String pressure = "";
			
				dayOfYear = dataForDay.get(DAY_OF_YEAR);
//				month = dataForDay.get(PrestonWeatherData.MONTH);
//				week = dataForDay.get(PrestonWeatherData.WEEK);
//				rad = dataForDay.get(PrestonWeatherData.KDOWN);
				rh = dataForDay.get(RELATIVE_HUMIDITY);
				temp = dataForDay.get(AIR_TEMPERATURE);
//				eaFrc = dataForDay.get(PrestonWeatherData.E_A);
				windSpd = dataForDay.get(WIND_SPEED);
				ppt = dataForDay.get(PRECIPITATION);
				pressure = dataForDay.get(MEAN_SEA_LEVEL_PRESSURE);
				rad = dataForDay.get(DIRECT_IRRADIANCE);
				
				if (pressure == null || pressure.trim().equals("")|| pressure.trim().equals("null"))
				{
					pressure = pressurePrev;
				}
				else
				{
					pressurePrev = pressure;
				}
				if (rad == null || rad.trim().equals("") || rad.trim().equals("null"))
				{
					rad = radPrev;
				}
				else
				{
					radPrev = rad;
				}
				if (rh == null || rh.trim().equals("") || rh.trim().equals("null"))
				{
					rh = rhPrev;
				}
				else
				{
					rhPrev = rh;
				}
				if (temp == null || temp.trim().equals("") || temp.trim().equals("null"))
				{
					temp = tempPrev;
				}
				else
				{
					tempPrev = temp;
				}
				if (windSpd == null || windSpd.trim().equals("") || windSpd.trim().equals("null"))
				{
					windSpd = windSpdPrev;
				}
				else
				{
					windSpdPrev = windSpd;
				}
				if (ppt == null || ppt.trim().equals("") || ppt.trim().equals("null"))
				{
					ppt = pptPrev;
				}
				else
				{
					pptPrev = ppt;
				}
				
//				item.put(STATION_NUMBER, stationNumber);
//				item.put(YEAR_MONTH_DAY, yearMonthDay);
//				item.put(PRECIPITATION, precipitation);
//				item.put(AIR_TEMPERATURE, airTemperature);
//				item.put(RELATIVE_HUMIDITY, relativeHumidity);
//				item.put(VAPOUR_PRESSURE, vapourPressure);
//				item.put(WIND_SPEED, windSpeed);
//				item.put(WIND_DIRECTION, windDirection);
//				item.put(MEAN_SEA_LEVEL_PRESSURE, meanSeaLevelPressure);
	
			
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
//			String newLine = dayOfYear + '\t' 
//					+ rad + '\t'
//					+ temp + '\t'
//					+ pressure + '\t'
//					+ rh + '\t'
//					+ dewPoint + '\t'
//					+ windSpd + '\t'
//					+ ca + '\t'
//					+ ppt +
//					diffuseStr;
//			System.out.println(newLine);
			
			st.append(dayOfYear + '\t' 
					+ rad + '\t'
					+ temp + '\t'
					+ pressure + '\t'
					+ rh + '\t'
					+ dewPoint + '\t'
					+ windSpd + '\t'
					+ ca + '\t'
					+ ppt +
					diffuseStr);
			st.append('\n');

		}

	
		
//		return ;
		
		String fileContents = fileTemplate + st.toString();
		return fileContents;
	}	
	
	

	public ArrayList<TreeMap<String,String>>  addToData(ArrayList<TreeMap<String,String>>  data )
	{
		
		TreeMap<String,String> dupItem = data.get( data.size()-1  );
		data.add(dupItem);
		
		return data;
	}
	
	public Connection getMelbourne1Min30SolarSqlite3Connection()
	{
		Connection conn = null;
		try
		{			
			Class.forName("org.sqlite.JDBC").newInstance();
			conn = DriverManager
					.getConnection("jdbc:sqlite:/" +							
							"/home/kerryn/Documents/Work/Data/Melbourne-1MinSolar/Melbourne1MinSolar_30Minutes.sqlite3" );
		} catch (Exception e)
		{
			e.printStackTrace();			
		}

		return conn;
	}
}
