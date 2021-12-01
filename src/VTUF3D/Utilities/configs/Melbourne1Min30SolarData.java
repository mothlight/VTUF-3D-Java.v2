package VTUF3D.Utilities.configs;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import VTUF3D.Utilities.Common;



public class Melbourne1Min30SolarData
{
	Common common = new Common();
	
	public static String GLOBAL_IRRADIANCE = "Mean_global_irradiance_over_1_minute_in_W_sq_m";
	public static String DIRECT_IRRADIANCE = "Mean_direct_irradiance_over_1_minute_in_W_sq_m";
	public static String DIFFUSE_IRRADIANCE = "Mean_diffuse_irradiance_over_1_minute_in_W_sq_m";
	
	private static ArrayList<TreeMap<String,String>> returnData;
	
//	private static Melbourne1Min30SolarData instance = null;
	 
//	private Melbourne1Min30SolarData() 
//	{
//	      // Exists only to defeat instantiation.
//	}
//	public static Melbourne1Min30SolarData getInstance(int year, String dayOfYear, Connection conn) 
//	{
//	     if(instance == null) 
//	     {
//	        instance = new Melbourne1Min30SolarData(year, dayOfYear, conn);
//	     }
//	     return instance;
//	}
	
	public Melbourne1Min30SolarData(int year, String dayOfYear, Connection conn)  
	{
	     super();
	     returnData = getMelbourneSolarDataForDay(year, dayOfYear, conn);
	}
	
	public ArrayList<TreeMap<String,String>> getMelbourneSolarDataForDay()
	{
		return returnData;
	}
	
	private ArrayList<TreeMap<String,String>> getMelbourneSolarDataForDay(int year, String dayOfYear, Connection conn)
	{
		ArrayList<TreeMap<String,String>>  data = new ArrayList<TreeMap<String,String>> ();
		//  2003	224	1330	20032241330	200308
		// year  dayofyear time  timecode month
		
//		int year = common.getYearFromTimecode(timecode);
//		int month = common.getMonthFromTimecode(timecode);
//		int day = common.getDayOfMonthFromTimecode(timecode);
//		int hour = common.getHourFromTimecode(timecode);
		int day = common.getDayOfMonthFromDayOfYear(year, dayOfYear);
		int month = common.getMonthFromDayOfYear(year, dayOfYear);
		
		String query = "select Year,MM,DD,HH24,MI,Mean_global_irradiance_over_1_minute_in_W_sq_m,Mean_direct_irradiance_over_1_minute_in_W_sq_m,Mean_diffuse_irradiance_over_1_minute_in_W_sq_m from Observations "
				+ "where Year ="
				+ year
				+ " and MM = "
				+ month
				+ " and DD="
				+ day
//				+ " and HH24="
//				+ hour
				+ " and (MI="
				+ "0" 
				+ " or MI="
				+ "30 )" 
				+ " order by HH24 ";	
		System.out.println(query);
		try
		{
			Statement stat = conn.createStatement();
			//Statement MelSolarStat = melSolarConn.createStatement();

			ResultSet rs = stat.executeQuery(query);
			while (rs.next())
			{
				TreeMap<String,String> item = new TreeMap<String,String>();
				String globalIrradiance = rs.getString(GLOBAL_IRRADIANCE);
				String directIrradiance = rs.getString(DIRECT_IRRADIANCE);
				String diffuseIrradiance = rs.getString(DIFFUSE_IRRADIANCE);
				
				//some data is missing in the dataset. Just use global for those missing values
				if (diffuseIrradiance == null || diffuseIrradiance.trim().equals(""))
				{
//					System.out.println(diffuseIrradiance + " equals 0");
					diffuseIrradiance = globalIrradiance;
				}
				
				item.put(GLOBAL_IRRADIANCE, globalIrradiance);
				item.put(DIRECT_IRRADIANCE, directIrradiance);
				item.put(DIFFUSE_IRRADIANCE, diffuseIrradiance);
				
//				System.out.println(item.toString());
				
				data.add(item);
			}
			rs.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return data;
	}
	public int getDayOfMonthFromDayOfYear(int year, String dayOfYear)
	{

		int dayOfYearInt = new Integer(dayOfYear).intValue();
	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYearInt);
	    calendar.set(Calendar.YEAR, year);
	    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return dayOfMonth;
	}
}
