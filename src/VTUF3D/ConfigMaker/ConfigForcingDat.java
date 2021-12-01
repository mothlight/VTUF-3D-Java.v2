package VTUF3D.ConfigMaker;

import java.util.ArrayList;

public class ConfigForcingDat
{

	ArrayList<double[]> loadedMetData;
	Common common = new Common();
	
	public final String FILENAME_PREFIX = "forcing";
	public final String FILENAME_SUFFUX = ".dat";
	public final String prestonWeatherTable = "Preston_data";
	
	private String runDirectory;
	private String filename ;
	private String fileText;
	private int year;
	private String runPrefix;
	private int day;
	private int numberOfDays;
	public final static String PRESTON_DATA = "Preston";
	public String tab = "\t";
	public String linefeed = "\n";

	public static final int MILLIS = 0;
	public static final int KDOWN = 1;
	public static final int LDOWN = 2;
	public static final int TEMP = 3;
	public static final int EAFRC = 4;
	public static final int WINDSPEED = 5;
	public static final int WINDDIR = 6;
	public static final int PRESSURE = 7;
	public static final int RH = 8;
	public static final int PPT = 9;
	
	
	//NUMFRC: The number of forcing timesteps over which the model will run,
	//i.e. total model integration time divided by the timestep of the input
	//forcing data (** note: there must be numfrc+1 rows of forcing data,
	//because data from the start time and end time both much be present)
    private int numfrc;
    //STARTTIME: The time at the start of the simulation (in hours from
    //midnight) in local mean solar time (i.e., in a time zone such that
    //	the smallest solar zenith angle occurs exactly at noon)
    private String starttime;
    //DELTATFRC: (hours) the timestep of the forcing data    
    private String deltatfrc;
    
//    do k=1,numfrc+1
//     read(981,*)Kdnfrc(k),Ldnfrc(k),Tafrc(k),eafrc(k),Uafrc(k),
//   &            Udirfrc(k),Pressfrc(k)
//     if(Kdnfrc(k).ge.-90.) Kdnfrc(k)=max(Kdnfrc(k),0.)
//     timefrc(k)=starttime+real(k-1)*deltatfrc
//    enddo
//    
//    KDNFRC(k): (W/m2) downwelling shortwave radiation flux density;
//    if Kdnfrc(1)<-90.0 then the model will use its internal solar model
//    to calculate downwelling shortwave (Iqbal/Stull)
//    LDNFRC(k): (W/m2) downwelling longwave radiation flux density;
//    if Ldnfrc(1)<0.0 then the model will use its internal longwave model
//    to calculate downwelling shortwave (Prata)
//    TAFRC(k): (deg C) air temperature at 'zref' (see input file - .dat)
//    *EAFRC(k): (mb) water vapour pressure at 'zref'
//    UAFRC(k): (m/s) wind SPEED (not velocity) at 'zref'
//    UDIRFRC(k): direction from which the wind is blowing at 'zref',
//    in degrees from north
//    *PRESSFRC(k): (mb) air pressure near the surface
//
//    * these values only have a very minor impact on simulation results,
//    and they can be roughly approximated with little influence on model
//    ouput
//    The UDIRFRC(k) are only important for calculating lambdaf (frontal area index)
//    and z0 - for momentum and heat exchange. Thus, if you prescribe these values
//    in parameters.dat, the values for Udirfrc will have no impact on the
//    simulation. 


	public ConfigForcingDat(String runDirectory, int year, String configNumber, int day, int numDays)
	{
		super();
		setDefaults();
		this.runDirectory = runDirectory;		
		this.year = year;
		this.runPrefix = configNumber;			
		this.filename = generateFilename(runPrefix, year);	
		setDay(day);
		setNumberOfDays(numDays);		
//		setNumfrc(common.multiplyString(getNumfrc(), numDays));
		setNumfrc(getNumfrc()*numDays); // I think this is the problem in setDefaults, using half hourly to get total timesteps.
		
	}	
	

	private void setDefaults()
	{
		setNumfrc(48);	  // I can't remember why this is 48 but it gets reset in the constructor, I think I mixed up how many per day and how many total
		setStarttime("0.0");
		setDeltatfrc("0.5");

	}
	
	public String generateFile(String dataTable)
	{
		String fileText = generateConfigFileText(runPrefix, dataTable);
		setFileText(fileText);
		return fileText;
	}
	

	
	private String generateConfigFileText(String runPrefix, String dataTable)
	{
		UrbanPlumberMetReformat u = new UrbanPlumberMetReformat();
		loadedMetData = u.processForVTUF3D();
		
		ArrayList<double[]> loadedMetDataForModellingPeriod = new ArrayList<double[]>();		
		StringBuilder st = new StringBuilder();
		
		// start at year and day
		int forcingCount = 0;
		boolean foundStartDate=false;
		boolean foundEndDate = false;
		for (double[] data : loadedMetData)
		{
		
			// reformattedDatetime,Kd,Ld,Ta,RH,WS,WD,P
			long millis = Math.round(data[ConfigForcingDat.MILLIS]);
			double kdown = data[ConfigForcingDat.KDOWN];
			double ldown = data[ConfigForcingDat.LDOWN];
			double temp = data[ConfigForcingDat.TEMP];
			double eaFrc = data[ConfigForcingDat.EAFRC];
			double windSpd = data[ConfigForcingDat.WINDSPEED];
			double windDir = data[ConfigForcingDat.WINDDIR];
			double pressure = data[ConfigForcingDat.PRESSURE];
			
			int[] yearDay = u.getStartYearDay(millis);
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
			
			if (foundEndDate)
			{
				continue;
			}
			else
			{
				if  (forcingCount > getNumfrc())
				{
					foundEndDate = true;
					continue;
				}
					
			}
			
			String formattedTime = u.getFormattedDatetime(millis);
			
	
			String line = formattedTime + " " + kdown + tab 
					+ ldown + tab
					+ temp + tab
					+ eaFrc + tab
					+ windSpd + tab
					+ windDir + tab
					+ pressure ;
//			System.out.println(line);
			
			st.append(kdown + tab 
					+ ldown + tab
					+ temp + tab
					+ eaFrc + tab
					+ windSpd + tab
					+ windDir + tab
					+ pressure );
			st.append(linefeed);
			loadedMetDataForModellingPeriod.add(data);
			forcingCount++;
		}
		
		st.append(    linefeed );
		st.append(    linefeed );
		

		st.append("	      read(981,*)numfrc,starttime,deltatfrc" + linefeed );
		st.append("	        do k=1,numfrc+1" + linefeed );
		st.append("	         read(981,*)Kdnfrc(k),Ldnfrc(k),Tafrc(k),eafrc(k),Uafrc(k)," +  linefeed );
		st.append("	       &            Udirfrc(k),Pressfrc(k)" +  linefeed );
		st.append("	         if(Kdnfrc(k).ge.-90.) Kdnfrc(k)=max(Kdnfrc(k),0.)" +  linefeed );
		st.append("	         timefrc(k)=starttime+real(k-1)*deltatfrc" +  linefeed );
		st.append("	        enddo" +  linefeed );
		st.append("	        " +  linefeed );
		st.append("	        " +  linefeed );
		st.append("	      NUMFRC: The number of forcing timesteps over which the model will run," +  linefeed );
		st.append("	      i.e. total model integration time divided by the timestep of the input" +  linefeed );
		st.append("	      forcing data (** note: there must be numfrc+1 rows of forcing data," +  linefeed );
		st.append("	      because data from the start time and end time both much be present)" +  linefeed );
		st.append("	      STARTTIME: The time at the start of the simulation (in hours from" +  linefeed );
		st.append("	      midnight) in local mean solar time (i.e., in a time zone such that" +  linefeed );
		st.append("	      the smallest solar zenith angle occurs exactly at noon)" +  linefeed );
		st.append("	      DELTATFRC: (hours) the timestep of the forcing data" +  linefeed );
		st.append("	      KDNFRC(k): (W/m2) downwelling shortwave radiation flux density;" +  linefeed );
		st.append("	      if Kdnfrc(1)<-90.0 then the model will use its internal solar model" +  linefeed );
		st.append("	      to calculate downwelling shortwave (Iqbal/Stull)" +  linefeed );
		st.append("	      LDNFRC(k): (W/m2) downwelling longwave radiation flux density;" +  linefeed );
		st.append("	      if Ldnfrc(1)<0.0 then the model will use its internal longwave model" +  linefeed );
		st.append("	      to calculate downwelling shortwave (Prata)" +  linefeed );
		st.append("	      TAFRC(k): (deg C) air temperature at 'zref' (see input file - .dat)" +  linefeed );
		st.append("	      *EAFRC(k): (mb) water vapour pressure at 'zref'" +  linefeed );
		st.append("	      UAFRC(k): (m/s) wind SPEED (not velocity) at 'zref'" +  linefeed );
		st.append("	      UDIRFRC(k): direction from which the wind is blowing at 'zref'," +  linefeed );
		st.append("	      in degrees from north" +  linefeed );
		st.append("	      *PRESSFRC(k): (mb) air pressure near the surface" +  linefeed );
		st.append("	      " +  linefeed );
		st.append("	      * these values only have a very minor impact on simulation results," +  linefeed );
		st.append("	      and they can be roughly approximated with little influence on model" +  linefeed );
		st.append("	      ouput" +  linefeed );
		st.append("	      The UDIRFRC(k) are only important for calculating lambdaf (frontal area index)" +  linefeed );
		st.append("	      and z0 - for momentum and heat exchange. Thus, if you prescribe these values" +  linefeed );
		st.append("	      in parameters.dat, the values for Udirfrc will have no impact on the" +  linefeed );
		st.append("	      simulation." +  linefeed );
		st.append("	      " +  linefeed );

		//we now know how many forcing steps
		setNumfrc(forcingCount-1);
		
		st.insert(0, getNumfrc() + " " + getStarttime() + " " + getDeltatfrc() + " " + linefeed);

		
		loadedMetData = loadedMetDataForModellingPeriod;
		return st.toString();
	}	
	
	public void writeConfigForcingFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getFileText(), runDirectory + inputDirectory + "/" + this.filename);
	}
	
	private String generateFilename(String runPrefix, int year)
	{
		return FILENAME_PREFIX + FILENAME_SUFFUX;
	}
	
	
	public String getRunDirectory()
	{
		return runDirectory;
	}

	public void setRunDirectory(String runDirectory)
	{
		this.runDirectory = runDirectory;
	}

	public String getFilename()
	{
		return filename;
	}

	public void setFilename(String filename)
	{
		this.filename = filename;
	}

	public String getFileText()
	{
		return fileText;
	}

	public void setFileText(String fileText)
	{
		this.fileText = fileText;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear(int year)
	{
		this.year = year;
	}

	public String getRunPrefix()
	{
		return runPrefix;
	}

	public void setRunPrefix(String runPrefix)
	{
		this.runPrefix = runPrefix;
	}

	public int getDay()
	{
		return day;
	}

	public void setDay(int day)
	{
		this.day = day;
	}

	public int getNumfrc()
	{
		return numfrc;
	}

	public void setNumfrc(int numfrc)
	{
		this.numfrc = numfrc;
	}

	public String getStarttime()
	{
		return starttime;
	}

	public void setStarttime(String starttime)
	{
		this.starttime = starttime;
	}

	public String getDeltatfrc()
	{
		return deltatfrc;
	}

	public void setDeltatfrc(String deltatfrc)
	{
		this.deltatfrc = deltatfrc;
	}

	public int getNumberOfDays()
	{
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays)
	{
		this.numberOfDays = numberOfDays;
	}

}
