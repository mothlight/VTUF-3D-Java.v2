package VTUF3D.ConfigMaker;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class MaespaConfigMetDat
{
	Common common = new Common();
	
	public final String FILENAME_PREFIX = "met";
	public final String FILENAME_SUFFUX = ".dat";
	public final String prestonWeatherTable = "Preston_data";
	
	private String runDirectory;
	private String filename ;
	private String fileText;
	private int year;
	private String runPrefix;
	private String day;
	private int numberOfDays;
	private double lat;
	private double lon;
	private String metFileTitle;
	private int khrsperday;		

	private String startDate;
	private String endDate;
	
    private String numfrc;    
    private String starttime;    
    private String deltatfrc;
    

	
	public static String getMetDatTemplate(String startDate, String endDate, int differtialShadingValue, String lat, String lon)
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
				"tzlong=150		" + '\n' +
				"lonhem='E'		" + '\n' +
				"lathem='S'		" + '\n' +
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
					"tzlong=150		" + '\n' +
					"lonhem='E'		" + '\n' +
					"lathem='S'		" + '\n' +
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
	
	
	public MaespaConfigMetDat(String runDirectory, int year, String runPrefix, String day, int numDays)
	{
		super();
		setDefaults();
		this.runDirectory = runDirectory;		
		this.year = year;
		this.runPrefix = runPrefix;			
		this.filename = generateFilename(runPrefix, year);	
		setDay(day);
		setNumberOfDays(numDays);		
		setNumfrc(common.multiplyString(getNumfrc(), numDays));
		
		calcStartAndEndDates();
		
	}	
	
	public void calcStartAndEndDates()
	{
		String startDay = getDay();

		int startDayInt = new Integer(startDay).intValue();
	
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		 
	     GregorianCalendar gc = new GregorianCalendar();
	     gc.set(GregorianCalendar.DAY_OF_YEAR, startDayInt);
	     gc.set(GregorianCalendar.YEAR, getYear());

	     setStartDate(sdf.format(gc.getTime()));
	     
	     gc.set(GregorianCalendar.DAY_OF_YEAR, startDayInt + getNumberOfDays());
	     setEndDate(sdf.format(gc.getTime()));
		
	}
	
	public MaespaConfigMetDat(String runDirectory, int year, String runPrefix, String day)
	{
		super();
		setDefaults();
		this.runDirectory = runDirectory;		
		this.year = year;
		this.runPrefix = runPrefix;			
		this.filename = generateFilename(runPrefix, year);	
		setDay(day);
		
	}
	private void setDefaults()
	{
		setNumfrc("47");	    
		setStarttime("0.0");
		setDeltatfrc("0.5");

	}
	
	public void writeConfigFile(String inputDirectory)
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

	public String getDay()
	{
		return day;
	}

	public void setDay(String day)
	{
		this.day = day;
	}

	public String getNumfrc()
	{
		return numfrc;
	}

	public void setNumfrc(String numfrc)
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
	
	public int daysBetween(Date d1, Date d2)
	{
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
	
	public int idate50(String year, String julianDay)
	{
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();

	     SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	     try
	     {
	    	 int yearInt = new Integer(year).intValue();
	    	 int julianInt = new Integer(julianDay).intValue();
	    	 
	    	 String since1950 = "01011950";
//	    	 String since1950 = "31121950";
	    	 
		     Date date = sdf.parse(since1950);
		     cal1.setTime(date);
//		     date = sdf.parse(strdate);
		     GregorianCalendar gc = new GregorianCalendar();
//		     gc.setTime(date);
		     gc.set(GregorianCalendar.DAY_OF_YEAR, julianInt);
//		     gc.set(GregorianCalendar.MONTH, GregorianCalendar.JUNE);
		     gc.set(GregorianCalendar.YEAR, yearInt);
		     cal2.setTime(gc.getTime());
	     }
	     catch (ParseException e)
	     {
	    	 e.printStackTrace();
	     }

	    //cal1.set(2008, 8, 1); 
	     //cal2.set(2008, 9, 31);
	     System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
	     
	     return daysBetween(cal1.getTime(), cal2.getTime());
	}
	
	public int idate50(String strdate)
	{
		Calendar cal1 = new GregorianCalendar();
	    Calendar cal2 = new GregorianCalendar();

	     SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");

	     try
	     {
		     Date date = sdf.parse("01011950");
		     cal1.setTime(date);
		     date = sdf.parse(strdate);
		     cal2.setTime(date);
	     }
	     catch (ParseException e)
	     {
	    	 e.printStackTrace();
	     }


	     System.out.println("Days= "+daysBetween(cal1.getTime(),cal2.getTime()));
	     
	     return daysBetween(cal1.getTime(), cal2.getTime());
	}
	
	
//	!**********************************************************************
	public int IDATE50(String STRDATE)
//	! This function translates a string-format date (DD/MM/YY) into the number 
//	! of days since 1/1/1950. Provides a numerical way of handling dates.
//	!**********************************************************************
	{

//	    IMPLICIT NONE
//	    CHARACTER*8 STRDATE
	    int IDAY,IYEAR,IYRD;
//	    INTEGER, EXTERNAL :: IDATEYR

//	    ! Get the day of year and the year number from function IDATEYR.
//	    IDAY = IDATEYR(STRDATE,IYEAR);
	    
	    Date date= new Date();
	    try
		{
			date = new SimpleDateFormat("DD/MM/YY").parse(STRDATE);
		}
		catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	      GregorianCalendar gc = new GregorianCalendar();
	      gc.setTime(date);
	      System.out.println(gc.get(GregorianCalendar.DAY_OF_YEAR));
	    
	    
//	    ! Calculate how many days in full years have passed since 1950.
	    IYRD = 365*(gc.get(GregorianCalendar.YEAR) - 1950);
	    IYRD = IYRD + (gc.get(GregorianCalendar.YEAR) - 1949)/4;
	    int IDATE50 = IYRD + gc.get(GregorianCalendar.DAY_OF_YEAR);
	    
	    return IDATE50;
	}


	
	public String getStartDate()
	{
		return startDate;
	}

	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	public String getEndDate()
	{
		return endDate;
	}

	public void setEndDate(String endDate)
	{
		this.endDate = endDate;
	}

	public double getLat()
	{
		return lat;
	}

	public void setLat(double lat)
	{
		this.lat = lat;
	}

	public double getLon()
	{
		return lon;
	}

	public void setLon(double lon)
	{
		this.lon = lon;
	}

	public String getMetFileTitle()
	{
		return metFileTitle;
	}

	public void setMetFileTitle(String metFileTitle)
	{
		this.metFileTitle = metFileTitle;
	}
	
	public int getKhrsperday()
	{
		return khrsperday;
	}

	public void setKhrsperday(int khrsperday)
	{
		this.khrsperday = khrsperday;
	}

}
