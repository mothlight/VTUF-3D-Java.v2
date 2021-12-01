package VTUF3D.ConfigMaker;

//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;


public class UrbanPlumberMetReformat
{
	String tab = "\t";
	String linefeed = "\n";
	String comma = ",";
	Common common = new Common();
	String prestonMet = "/home/kerryn/git/2020-06-UrbanPlumber/AU-Preston/AU-Preston_metforcing_v1.txt";
	String prestonMetOutput = "/home/kerryn/git/2020-06-UrbanPlumber/AU-Preston/Preston/input/Preston/MET/AU_Preston.csv";
	
	public static void main(String[] args)
	{
		UrbanPlumberMetReformat u = new UrbanPlumberMetReformat();
		u.process();

	}
	
	public ArrayList<double[]> processForVTUF3D()
	{
		ArrayList<double[]> metData = new ArrayList<double[]>();

		
		
		
//		StringBuilder sb = new StringBuilder();
//		String header = "datetime,Ta,RH,WS,P,Kd,Ld";
//		sb.append(header + linefeed);
		
		ArrayList<String> metFileContents = common.readLargerTextFileAlternateToArray(prestonMet);
		for (String line : metFileContents)
		{
			if (line.startsWith("#"))
			{
				continue;
			}
			//        0        1      2        3        4      5         6         7         8            9            10      11        12          13         14         15      16        17       18        19
			//  #     Date     Time   SWdown  LWdown  Wind_E  Wind_N     PSurf    Tair      Qair         Rainf         Snowf  SWdown_qc  LWdown_qc  Wind_E_qc  Wind_N_qc  Tair_qc  PSurf_qc  Qair_qc  Rainf_qc  Snowf_qc
//			# units = SWdown: W/m2, LWdown: W/m2, Wind_E: m/s, Wind_N: m/s, PSurf: Pa, Tair: K, Qair: kg/kg, Rainf: kg/m2/s, Snowf: kg/m2/s
//					# quality control ([var]_qc) flags: 0=observed, 1=gapfilled_from_obs, 2=gapfilled_from_era5, 3=missing
			
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
//			String reformattedTime = timeSplit[0] + ":" + timeSplit[1];
			String[] dateSplit = date.split("-");
			String year = dateSplit[0];
			String month = dateSplit[1];
			String day = dateSplit[2];
//			String reformattedDate = day + "/" + month + "/" + year;
			
			long reformattedDatetime = getFormattedDatetimeLong(year, month, day, hour, minute);

			Ta = common.roundToDecimals(Ta, 3);
			RH = common.roundToDecimals(RH, 3);
			WS = common.roundToDecimals(WS, 3);
			P = common.roundToDecimals(P, 3);
			
			// TARGET needs:
//			datetime,Ta,RH,WS,P,Kd,Ld
//			24/02/2012 0:00, 22.3, 49,  9,1016.2,0.0,378.86
			
//			String outputLine = reformattedDatetime 
////					reformattedDate + " " + reformattedTime 
//					+ comma + Ta + comma + RH + comma + WS + comma + P + comma + Kd + comma + Ld;
			
//			System.out.println(outputLine);
//			sb.append(outputLine + linefeed);
			
			double[] dataLine = new double[]{reformattedDatetime,Kd,Ld,Ta,QairValue*100.0,WS,WD,P,RH,RainfValue};
			metData.add(dataLine);
			
//			st.append(kdown + '\t' 
//			+ ldown + '\t'
//			+ temp + '\t'
//			+ eaFrc + '\t'
//			+ windSpd + '\t'
//			+ windDir + '\t'
//			+ pressure );
			
//			1410 0.0 0.5
//			-0.975  352.33  16.907  1.587   4.242   211.52  995.2
//			-1.494  346.5   16.739  1.595   2.979   208.0   994.8
//			-1.202  348.51  16.676  1.583   3.791   206.14  996.3
//			-1.064  353.68  16.646  1.563   3.985   197.4   995.8

			
			
		}	
		return metData;
	}
	public void process()
	{
		StringBuilder sb = new StringBuilder();
		String header = "datetime,Ta,RH,WS,P,Kd,Ld";
		sb.append(header + linefeed);
		
		ArrayList<String> metFileContents = common.readLargerTextFileAlternateToArray(prestonMet);
		for (String line : metFileContents)
		{
			if (line.startsWith("#"))
			{
				continue;
			}
			//        0        1      2        3        4      5         6         7         8            9            10      11        12          13         14         15      16        17       18        19
			//  #     Date     Time   SWdown  LWdown  Wind_E  Wind_N     PSurf    Tair      Qair         Rainf         Snowf  SWdown_qc  LWdown_qc  Wind_E_qc  Wind_N_qc  Tair_qc  PSurf_qc  Qair_qc  Rainf_qc  Snowf_qc
//			# units = SWdown: W/m2, LWdown: W/m2, Wind_E: m/s, Wind_N: m/s, PSurf: Pa, Tair: K, Qair: kg/kg, Rainf: kg/m2/s, Snowf: kg/m2/s
//					# quality control ([var]_qc) flags: 0=observed, 1=gapfilled_from_obs, 2=gapfilled_from_era5, 3=missing
			
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
			
			
			
			double PSurfValue = new Double(PSurf).doubleValue();
			double P = PSurfValue / 100.0;
			String Kd = SWdown;
			String Ld = LWdown;
			double TairValue = new Double(Tair).doubleValue();
			double Ta = TairValue - 273.15;
			double QairValue = new Double(Qair).doubleValue();
			double RH = common.convertSpecHumidityToRH(QairValue,Ta,P)*100;
			double UValue = new Double(Wind_E).doubleValue();
			double VValue = new Double(Wind_N).doubleValue();
			double WS = common.calcWindSpeed(UValue, VValue);
			double windDir = common.calcWindDir(UValue, VValue);
			
			String[] timeSplit = time.split(":");
			String hour = timeSplit[0];
			String minute = timeSplit[1];
//			String reformattedTime = timeSplit[0] + ":" + timeSplit[1];
			String[] dateSplit = date.split("-");
			String year = dateSplit[0];
			String month = dateSplit[1];
			String day = dateSplit[2];
//			String reformattedDate = day + "/" + month + "/" + year;
			
			String reformattedDatetime = getFormattedDatetime(year, month, day, hour, minute);

			Ta = common.roundToDecimals(Ta, 3);
			RH = common.roundToDecimals(RH, 3);
			WS = common.roundToDecimals(WS, 3);
			P = common.roundToDecimals(P, 3);
			
			// TARGET needs:
//			datetime,Ta,RH,WS,P,Kd,Ld
//			24/02/2012 0:00, 22.3, 49,  9,1016.2,0.0,378.86
			
			String outputLine = reformattedDatetime 
//					reformattedDate + " " + reformattedTime 
					+ comma + Ta + comma + RH + comma + WS + comma + P + comma + Kd + comma + Ld;
			
//			System.out.println(outputLine);
			sb.append(outputLine + linefeed);
		}
		
		common.writeFile(sb.toString(), prestonMetOutput);
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
//		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+10:00"));
//		localCalendar.setTimeInMillis(millis);
//		
//		int newYear = localCalendar.get(Calendar.YEAR) ;
//	    int newMonth = localCalendar.get(Calendar.MONTH) + 1;
//	    int newDay = localCalendar.get(Calendar.DAY_OF_MONTH) ;
//	    int newHour = localCalendar.get(Calendar.HOUR_OF_DAY) ;
//	    int newMinute = localCalendar.get(Calendar.MINUTE) ;
//	    
//	    String newMinuteStr = common.padLeft(newMinute, 2, '0');
//	    String newHourStr = common.padLeft(newHour, 2, '0');
//	    String newMonthStr = common.padLeft(newMonth, 2, '0');
//	    String newDayStr = common.padLeft(newDay, 2, '0');
//
//	    String reformattedTime = newHourStr + ":" + newMinuteStr;
//	    String reformattedDate = newDayStr + "/" + newMonthStr + "/" + newYear;
//	    return reformattedDate + " " + reformattedTime;
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
	
	public int[] getStartYearDay(long millis)
	{
		Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+10:00"));
		localCalendar.setTimeInMillis(millis);
		
		int newYear = localCalendar.get(Calendar.YEAR) ;
//	    int newMonth = localCalendar.get(Calendar.MONTH) + 1;
//	    int newDay = localCalendar.get(Calendar.DAY_OF_MONTH) ;
//	    int newHour = localCalendar.get(Calendar.HOUR_OF_DAY) ;
//	    int newMinute = localCalendar.get(Calendar.MINUTE) ;
	    int dayOfYear = localCalendar.get(Calendar.DAY_OF_YEAR);
	    
//	    String newMinuteStr = common.padLeft(newMinute, 2, '0');
//	    String newHourStr = common.padLeft(newHour, 2, '0');
//	    String newMonthStr = common.padLeft(newMonth, 2, '0');
//	    String newDayStr = common.padLeft(newDay, 2, '0');
//
//	    String reformattedTime = newHourStr + ":" + newMinuteStr;
//	    String reformattedDate = newDayStr + "/" + newMonthStr + "/" + newYear;
//	    return reformattedDate + " " + reformattedTime;
	    
	    return new int[]{newYear,dayOfYear};
	}
	
	public String getFormattedDatetime(long millis)
	{
//		int year = new Integer(yearStr).intValue();
//		int month = new Integer(monthStr).intValue()-1;
//		int day = new Integer(dayStr).intValue();
//		int hour = new Integer(hourStr).intValue();
//		int minute = new Integer(minuteStr).intValue();
//		
//	    Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//	    calendar.set(Calendar.ZONE_OFFSET, TimeZone.getTimeZone("UTC").getRawOffset());
//	    calendar.set(Calendar.YEAR, year);
//	    calendar.set(Calendar.MONTH, month);
//	    calendar.set(Calendar.DAY_OF_MONTH, day);
//	    calendar.set(Calendar.HOUR_OF_DAY, hour);
//	    calendar.set(Calendar.MINUTE, minute);
//
//		long millis = calendar.getTimeInMillis();
//		
		
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
