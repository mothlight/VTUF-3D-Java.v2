package VTUF3D;

import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeMap;

import VTUF3D.Utilities.Common;

public class UrbanPlumberOutput
{

	//to shift one indexed arrays to zero
	public final static int ONE = 0;
	public final static int TWO = 1;
	public final static int THREE = 2;
	public final static int FOUR = 3;
	public final static int FIVE = 4;
	public final static int SIX = 5;
	
	Common common = new Common();
	String noValue = "-9999.";
	
	
	public static void main(String[] args)
	{
		

	}
	
	
//	# This file forms part of the Urban-PLUMBER benchmarking evaluation project for urban areas. Copyright 2020.
//	# example (partial) model output in local standard time
//	#   YYYY    DOY.00       DOY      HHMM      W/m2      W/m2      W/m2      W/m2      W/m2      W/m2      W/m2      W/m2      W/m2  
//	    Year   Dectime       Day      Time    NetRad      SWup      LWup      QHup      QEup    SWdown    LWdown       dQS     Qanth  
//	    2004    1.4167         1      1000  625.5300  120.9671  431.4429   66.4826  113.9582    862.81    315.13     0.000    11.529
	
	public void output( int time_out, boolean first_write, 	
			OverallConfiguration overall,  
			double[] tots, double[] totl, double[] reflts, double[] refltl, double[] absbs,  double timeis, 
			double Ldn,  double Tcan, double ea,  double Ua, 
			double Aplan, double Rnet_tot, double Kup, double Lup, double Qh_tot, double Qe_tot, double Kdn_grid, double Qg_tot, double yd, int year,
			double Tsfc_R, double Tsfc_T, double Tsfc_N, double Tsfc_S, double Tsfc_E, double Tsfc_W ,
			int numroof2, int numstreet2, int numNwall2, int numSwall2, int numEwall2, int numWwall2,
			double Kdir, double Kdif)
	{
		
		String formattedTime;
//		double gridUtci;
//		double[][] vertex;
//		int[][] face;
//		double maespaTcanForTmrtCalc;
//		double maespasoiltForTmrtCalc; 
//		double ldownForTmrtCalc; 
//		double lupForTmrtCalc; 
//		double taForUtciCalc;
//		double solarForTmrtCalc;
//		double gridTmrt;
//		double xpinc;
//		double ypinc;
//		int numvertex;
//		int jab;
		String linefeed = "\n";
		String tab = "\t";
		
		String urbanPlumberOutputFile = null;
		urbanPlumberOutputFile = "UrbanPlumber.out";
		int decimalPoints = 4;
		
		formattedTime =  common.padLeft( time_out, 6, '0') ;

		if (first_write)
		{
			
			String header = "# This file forms part of the Urban-PLUMBER benchmarking evaluation project for urban areas. Copyright 2020." + linefeed +
							"# example (partial) model output in local standard time"+ linefeed +
							"#YYYY"+tab+"DOY.00"+tab+"DOY"+tab+"HHMM"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2"+tab+"W/m2" 
							+tab+"K"
							+tab+"K"
							+tab+"K"
							+tab+"K"
							+ linefeed +
							"Year"
							+ tab
							+ "Dectime"
							+ tab
							+ "Day"
							+ tab
							+ "Time"
							+ tab
							+ "NetRad"
							+ tab
							+ "SWup"
							+ tab
							+ "LWup"
							+ tab
							+ "QHup"
							+ tab
							+ "QEup"
							+ tab
							+ "SWdown"
							+ tab
							+ "LWdown"
							+ tab
							+ "dQS"
							+ tab
							+ "Qanth"
							
							+ tab
							+ "RoofSurfT"
							+ tab
							+ "RoadSurfT"
							+ tab
							+ "WallSurfT"
							+ tab
							+ "TairCanyon"
							
							;
			overall.writeOutput(urbanPlumberOutputFile, header, true);
			
			
			
			//TODO, taking out the padding section
			
			
			//also pad out file with no-data starting at 1993
//			Forcing files include the following time metadata, with values for the first site (Preston) shown:
//				• time_coverage_start: first date and time of forcing in UTC:
//				 (1993-01-01 00:00:00)
//				• time_coverage_end: last date and time of forcing in UTC:
//				 (2004-11-28 13:00:00)
//				• time_analysis_start: first date and time of analysis period in UTC: (2003-08-12 03:30:00)
//				• local_utc_offset_hours: local standard time offset from UTC:
//				 (10.0)
//				• timestep_interval_seconds: timestep interval in seconds:
//				 (1800)
//				• timestep_number_spinup: timestep number during spinup:
//				 (186007)
//				• timestep_interval_seconds: timestep number during analysis:
//				 (22772)

			// currentSimulationTime is when the modelling starts, pad out time starting with timeCoverageStartLong
			
			Calendar timeCoverageStart = Calendar.getInstance();
			timeCoverageStart.set(1992, 6, 0, 0, 0);
			long timeCoverageStartLong = timeCoverageStart.getTimeInMillis();

			Calendar currentSimulationTimeCal = Calendar.getInstance();			
			currentSimulationTimeCal.set(year, 0, 0, 0, 0);
			int dayOfYear = (int) Math.round(yd);
			currentSimulationTimeCal.set(Calendar.DAY_OF_YEAR, dayOfYear);
			long currentSimulationTime = currentSimulationTimeCal.getTimeInMillis();
			
			boolean usePadding = false;
//			while (timeCoverageStartLong < currentSimulationTime-1000*30*60)
			while (usePadding)
			{				
//			    int dayPad = timeCoverageStart.get(Calendar.DAY_OF_MONTH);
//			    String dayPadStr = common.padLeft(day+"", 2, '0');
//			    int monthPad = timeCoverageStart.get(Calendar.MONTH) + 1;
//			    String monthPadStr = common.padLeft(month+"", 2, '0');
			    int yearPad = timeCoverageStart.get(Calendar.YEAR);
			    int hourPad = timeCoverageStart.get(Calendar.HOUR_OF_DAY);
			    String hourPadStr = common.padLeft(hourPad, 2, '0');
			    int minutePad = timeCoverageStart.get(Calendar.MINUTE);
			    String minutePadStr = common.padLeft(minutePad, 2, '0');
			    int dayOfYearPad = timeCoverageStart.get(Calendar.DAY_OF_YEAR);
				
				int YearPad = yearPad;
				double decHourMinutePad = (hourPad*60. + minutePad)/1440.0;
				double DectimePad = dayOfYearPad + decHourMinutePad;
				int DayPad = dayOfYearPad;
				String TimePad = hourPadStr + minutePadStr;

				String outputLine = YearPad
						+ tab
						+ DectimePad
						+ tab
						+ DayPad
						+ tab
						+ TimePad
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						+ tab
						+ noValue
						;
				overall.writeOutput(urbanPlumberOutputFile, outputLine, false);	
				
				timeCoverageStartLong += 1000*60*30;
				timeCoverageStart.setTimeInMillis(timeCoverageStartLong);
			}

		}
		
//		boolean isLeapYear = Year.isLeap(year);
		
			
//		int Year; // YYYY 
//		double Dectime; //  DOY.00  
//		long Day; //  DOY 
//		long Time; // HHMM
		double NetRad;  // W/m2    
		double SWup;  // W/m2
		double LWup;  // W/m2    
		double QHup;  // W/m2    
		double QEup;  // W/m2    
		double SWdown;  // W/m2    
		double LWdown;  // W/m2    
		double dQS;  // W/m2    
		double Qanth;  // W/m2    
		
		double RoofSurfT; //Tsfc roof
		double RoadSurfT; //Tsfc road
//		double Tsfc_N_ave; //Tsfc wall N
//		double Tsfc_S_ave; //Tsfc wall S
//		double Tsfc_E_ave; //Tsfc wall E
//		double Tsfc_W_ave; //Tsfc wall W
		double WallSurfT; 
		double TairCanyon;
		
		RoofSurfT = common.roundToDecimals(Tsfc_R / (1.0*numroof2), decimalPoints);  
		RoadSurfT = common.roundToDecimals(Tsfc_T / (1.0*numstreet2), decimalPoints);  
		WallSurfT =common.roundToDecimals(( (Tsfc_N / (1.0*numNwall2) ) 
				 + (Tsfc_S / (1.0*numSwall2) ) 
				 + (Tsfc_E / (1.0*numEwall2) ) 
				 + (Tsfc_W / (1.0*numWwall2) ))
				 	/ 4.0, decimalPoints);  
		TairCanyon = common.roundToDecimals(Tcan, decimalPoints);  
		
		
//		+ "\t" + common.roundToDecimals((Tsfc_R / (1.0*numroof2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_T / (1.0*numstreet2) - 273.15)  , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_N / (1.0*numNwall2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_S / (1.0*numSwall2) - 273.15)  , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_E / (1.0*numEwall2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_W / (1.0*numWwall2) - 273.15)  , 3)
		
//		yd_actual  //the julian day
//		timeis   // time of day in hours (but also counting from starting day)
//		yd       // starting day of year
		
		
		
		Calendar currentSimulationTimeCal = Calendar.getInstance();			
		currentSimulationTimeCal.set(year, 0, 1, 0, 0);
		int dayOfYear = (int) Math.round(yd);
		currentSimulationTimeCal.set(Calendar.DAY_OF_YEAR, dayOfYear);		
		long currentSimulationTime = currentSimulationTimeCal.getTimeInMillis();
		currentSimulationTime = currentSimulationTime + Math.round(timeis * 1000 * 60 * 60);
		
		
		Calendar currentSimulationTimeOffsetCal = Calendar.getInstance();	
		currentSimulationTimeOffsetCal.setTimeInMillis(currentSimulationTime);
	    int yearPad = currentSimulationTimeOffsetCal.get(Calendar.YEAR);
	    int hourPad = currentSimulationTimeOffsetCal.get(Calendar.HOUR_OF_DAY);
	    String hourPadStr = common.padLeft(hourPad, 2, '0');
	    int minutePad = currentSimulationTimeOffsetCal.get(Calendar.MINUTE);
	    String minutePadStr = common.padLeft(minutePad, 2, '0');
	    int dayOfYearPad = currentSimulationTimeOffsetCal.get(Calendar.DAY_OF_YEAR);
		
		int YearPad = yearPad;
		double decHourMinutePad = (hourPad*60. + minutePad)/1440.0;
		double DectimePad = dayOfYearPad + decHourMinutePad;
		int DayPad = dayOfYearPad;
		String TimePad = hourPadStr + minutePadStr;
		

		
//		double daysElapsed = timeis / 24.0;
//		System.out.println("daysElapsed=" + daysElapsed + " " + timeis);
//		// this will allow 2 year simulations to be run, but this won't work for more than 2 years
//		if (isLeapYear)
//		{
//			if (daysElapsed > 366)
//			{
//				year = year + 1;
//			}
//		}
//		else
//		{
//			if (daysElapsed > 365)
//			{
//				year = year + 1;
//			}
//		}
//		
//		Year = year; 
//		Dectime = common.roundToDecimals(yd + daysElapsed , decimalPoints);  
//		Day = Math.round(Math.floor(Dectime)); 
//		double hourminute = Dectime - Day;
//		long hourminute24 = Math.round(24.0*hourminute);
//		long hour = hourminute24*100;
//		long hourminute48 = Math.round(48.0*hourminute);  // if it is 20, then 1000, if 21 then 1030
//		if (isEven(hourminute48))
//		{}
//		else
//		{
//			hour = hour + 30;
//		}
//		
//		Time = hour;
////		Time = hourminute24 * 100 + (hourminute48 - (hourminute24*2))*30;
//		
////		double hourMinute24 = 24.0*hourminute;
////		double hourMinute48 = 48.0*hourminute;
////		Time = hourminute24 * 100 + (hourminute48 - (hourminute24*2))*30;
				
		NetRad = common.roundToDecimals(Rnet_tot / Aplan , decimalPoints);  
		SWup = common.roundToDecimals(Kup , decimalPoints) ;  
		LWup = common.roundToDecimals(Lup , decimalPoints);    
		QHup = common.roundToDecimals(Qh_tot / Aplan , decimalPoints);   
		QEup = common.roundToDecimals(Qe_tot / Aplan, decimalPoints);   
//		SWdown = common.roundToDecimals(Kdn_grid , decimalPoints); 
		SWdown = common.roundToDecimals(Kdir + Kdif , decimalPoints); 
		LWdown = common.roundToDecimals(Ldn , decimalPoints) ;  
		dQS = common.roundToDecimals(Qg_tot / Aplan , decimalPoints);  
		Qanth = 0;  //VTUF-3D does not calculate this
		

		
		String outputLine = YearPad
				+ tab
				+ DectimePad
				+ tab
				+ DayPad
				+ tab
				+ TimePad
				+ tab
				+ NetRad
				+ tab
				+ SWup
				+ tab
				+ LWup
				+ tab
				+ QHup
				+ tab
				+ QEup
				+ tab
				+ SWdown
				+ tab
				+ LWdown
				+ tab
				+ dQS
				+ tab
				+ Qanth
				
				+ tab
				+ RoofSurfT
				+ tab
				+ RoadSurfT
				+ tab
				+ WallSurfT
				+ tab
				+ TairCanyon
				;
		overall.writeOutput(urbanPlumberOutputFile, outputLine, false);

		
		
//		overall.writeOutput(Constants.EnergyBalanceOverallOut, 
//				common.roundToDecimals(lpactual , 3) + "\t" + 
//				common.roundToDecimals((2. * bh) / (1.0*bl + bw) , 3) + "\t" 
//				+ common.roundToDecimals(hwactual , 3) + "\t" 
//				+ common.roundToDecimals(xlat , 3) + "\t" 
//				+ common.roundToDecimals(stror , 3) + "\t"
//				+ common.roundToDecimals(yd_actual , 3) + "\t" 
//				+ common.roundToDecimals(amodTime , 3) + "\t" 
//				+ common.roundToDecimals(timeis , 3) + "\t" 
//				+  + "\t"
//				+  + "\t" 
//				+ common.roundToDecimals(Qh_abovezH / Aplan + Qhtop * (1. - lambdapR) , 3) + "\t"
//				+  + "\t" 
//				+ common.roundToDecimals(Qg_tot / Aplan + (Qhcan - Qhtop) * (1. - lambdapR), 3) + "\t" 
//				+ common.roundToDecimals((Rnet_tot / Aplan - lambdapR * Rnet_R / (1.0*numroof2)) / (1. - lambdapR), 3) + "\t" 
//				+ common.roundToDecimals(Qhtop , 3) + "\t" 
//				+ common.roundToDecimals(Qhcan , 3) + "\t"
//				+ common.roundToDecimals((Qg_tot / Aplan - lambdapR * Qg_R / (1.0*numroof2)) / (1. - lambdapR) + (Qhcan - Qhtop) , 3) + "\t"
//				+ common.roundToDecimals(ustar / vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) * Math.exp(-2. * lambdaf / (1. - lambdapR) / 4.), 3) + "\t" 
//				+ common.roundToDecimals(ustar / vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) , 3) + "\t" 
//				+ common.roundToDecimals(Acan + Bcan * Math.exp(Ccan * patchlen / 2.) , 3) + "\t" 
//				+ common.roundToDecimals(wstar , 3) + "\t" 
//				+ common.roundToDecimals(Kdir + Kdif , 3) + "\t"
//				+ + "\t" + 
//				+ "\t" + 
//				 + "\t" + 
//				common.roundToDecimals(Kdir_Calc , 3) + "\t" + 
//				common.roundToDecimals(Kdif_Calc , 3) + "\t" + 
//				common.roundToDecimals(Kdir , 3) + "\t" + 
//				common.roundToDecimals(Kdif , 3) + "\t" + 
//				common.roundToDecimals((Kup - lambdapR * Kup_R / (1.0*numroof2)) / (1. - lambdapR)	, 3) + "\t" + 
//				common.roundToDecimals((Lup - lambdapR * Lup_R / (1.0*numroof2)) / (1. - lambdapR) , 3) + "\t" + 
//				common.roundToDecimals(az , 3) + "\t"
//				+ common.roundToDecimals(zen , 3) + "\t" + 
//				common.roundToDecimals(Math.max(Kdir_NoAtm, 0.) , 3) + "\t" + 
//				 + "\t" + 
//				
//				);

		
		
		
		
		
//		+ "\t" + common.roundToDecimals((Tsfc_R / (1.0*numroof2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_T / (1.0*numstreet2) - 273.15)  , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_N / (1.0*numNwall2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_S / (1.0*numSwall2) - 273.15)  , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_E / (1.0*numEwall2) - 273.15) , 3)
//		+ "\t" + common.roundToDecimals((Tsfc_W / (1.0*numWwall2) - 273.15)  , 3)
//		+ "\t" + common.roundToDecimals((Tcan - 273.15)  , 3)
		
		
		
		
		
		
		
	}
	
	boolean isEven(long num)
	{
		return ((num % 2) == 0 );
	}

}
