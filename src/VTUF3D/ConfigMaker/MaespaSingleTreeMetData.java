package VTUF3D.ConfigMaker;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;



public class MaespaSingleTreeMetData
{
	Common common = new Common();
	private String data;
	private String metDataFileContents;
	public final String prestonWeatherTable = "Preston_data";
	public static final int CONFIG_TYPE_DIFFUSE = 1;
	public static final int CONFIG_TYPE_50_PERCENT = 2;
	public static final int CONFIG_TYPE_100_PERCENT = 3;
	
	public static final double CONFIG_TYPE_DIFFUSE_PERCENT = 0.20;
	public static final double CONFIG_TYPE_50_PERCENT_PERCENT = 0.60;
	public static final double CONFIG_TYPE_100_PERCENT_PERCENT = 1.00;
	public final int ROUND_TO_PRECISION = 2;
	
	private int configType = -9999;
	private ArrayList<double[]> loadedMetData;
	String tab = "\t";
	String linefeed = "\n";
	

	public MaespaSingleTreeMetData(String fileTemplate, int year, int day, int numberOfDays, String dataTable, String runDirectory, String configNumber, int differtialShadingValue, int configType,
			ArrayList<double[]> loadedMetData)
	{
		super();
		this.configType = configType;
		this.loadedMetData = loadedMetData;
		this.data = generateMaespaMetConfigFile(year, day, numberOfDays, dataTable, runDirectory, configNumber, differtialShadingValue);
		this.setMetDataFileContents(fileTemplate + this.data);
	}


	public double convertRainfToMM(double rainf, int timestep)
	{
		//30 minutes in seconds // assume that timestep is 30 minutes
		int sec = 60*timestep;
		double mm = rainf * sec;		
		return common.roundToDecimals(mm, 2);
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
	
	private String generateMaespaMetConfigFile(int year, int day, int numberOfDays, String dataTable, String runDirectory, 
			String runPrefix, int differtialShadingValue)
	{
		
		ConfigForcingDat configForcingDat = new ConfigForcingDat(runDirectory, year, runPrefix, day, numberOfDays);
//		ArrayList<TreeMap<String,String>> dataForDay = configForcingDat.getWeatherData(dataTable);
		
		StringBuilder st = new StringBuilder();
		
		//'DATE'	'RAD'	'TAIR'	'PRESS'	'RH%'	'TDEW'	'WIND'	'CA'	'PPT'	
		boolean foundStartDate = false;
		for (double[] data : loadedMetData)
		{
			long millis = Math.round(data[ConfigForcingDat.MILLIS]);
			double kdown = data[ConfigForcingDat.KDOWN];
			double ldown = data[ConfigForcingDat.LDOWN];
			double temp = data[ConfigForcingDat.TEMP];
			double eaFrc = data[ConfigForcingDat.EAFRC];
			double windSpd = data[ConfigForcingDat.WINDSPEED];
			double windDir = data[ConfigForcingDat.WINDDIR];
			double pressure = data[ConfigForcingDat.PRESSURE];
			double rainf = data[ConfigForcingDat.PPT];
			double rh = data[ConfigForcingDat.RH];
			
			
			
			int[] yearDay = getStartYearDay(millis);
			if (foundStartDate)
			{}
			else if ( yearDay[0] == year && yearDay[1]==day)
			{
				foundStartDate = true;
			}
			else
			{
				continue;
			}
			
			
			
			//rainf is in kg/m2/s, convert to mm/30 minutes
			double ppt = convertRainfToMM(rainf, 30);
			
			int[] dateData = getStartYearDay(millis);
			
			int dayOfYear = dateData[1];
			double rad = kdown;

			
//			if (dataTable.equals(ConfigForcingDat.PRESTON_DATA))
//			{
//				dayOfYear = data.get(PrestonWeatherData.DAY_OF_YEAR);
//				month = data.get(PrestonWeatherData.MONTH);
//				week = data.get(PrestonWeatherData.WEEK);
//				rad = data.get(PrestonWeatherData.KDOWN);
//				rh = data.get(PrestonWeatherData.RH);
//				temp = data.get(PrestonWeatherData.TEMP);
//				eaFrc = data.get(PrestonWeatherData.E_A);
//				windSpd = data.get(PrestonWeatherData.WIND_SPD);
//				ppt = data.get(PrestonWeatherData.PRECIP);
//				pressure = data.get(PrestonWeatherData.PRESSURE);
				
//				globalIrradiance = data.get(Melbourne1MinSolarData.GLOBAL_IRRADIANCE);
//				directIrradiance = data.get(Melbourne1MinSolarData.DIRECT_IRRADIANCE);
//				diffuseIrradiance = data.get(Melbourne1MinSolarData.DIFFUSE_IRRADIANCE);
//				rad = globalIrradiance;
				
//			}
			


			
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
			if (differtialShadingValue == CONFIG_TYPE_DIFFUSE)
			{
				//rad = "0.";
//				rad = tufCommon.percentOfStrRounded(rad, CONFIG_TYPE_DIFFUSE_PERCENT, ROUND_TO_PRECISION);
				
//				if (diffuseIrradiance.equals(""))
//				{
//					rad = common.percentOfStrRounded(rad, CONFIG_TYPE_DIFFUSE_PERCENT, ROUND_TO_PRECISION);
//				}
//				else
//				{
//					rad = diffuseIrradiance;
//				}
				rad = rad * CONFIG_TYPE_DIFFUSE_PERCENT;
			}
			//this is no longer used
//			//changing to 60%
//			if (differtialShadingValue == CONFIG_TYPE_50_PERCENT)
//			{
//				rad = tufCommon.percentOfStrRounded(rad, CONFIG_TYPE_50_PERCENT_PERCENT, ROUND_TO_PRECISION);
//			}
			//otherwise it is 100% radiation
			
			rad = common.roundToDecimals(rad, 2);
			
			double fbeam = 0.01;
			if (differtialShadingValue == CONFIG_TYPE_DIFFUSE)
			{
				st.append(dayOfYear + tab 
						+ rad + tab
						+ temp + tab
						+ pressure + tab
						+ rh + tab
						+ dewPoint + tab
						+ windSpd + tab
						+ ca + tab
						+ ppt + tab
						+ fbeam);
			}
			else
			{
				st.append(dayOfYear + tab 
					+ rad + tab
					+ temp + tab
					+ pressure + tab
					+ rh + tab
					+ dewPoint + tab
					+ windSpd + tab
					+ ca + tab
					+ ppt );
			}
			st.append(linefeed);
		}
		
		return st.toString();
	}	
	


	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

	public String getMetDataFileContents()
	{
		return metDataFileContents;
	}

	public void setMetDataFileContents(String metDataFileContents)
	{
		this.metDataFileContents = metDataFileContents;
	}

	public int getConfigType()
	{
		return configType;
	}

	public void setConfigType(int configType)
	{
		this.configType = configType;
	}
	


}
