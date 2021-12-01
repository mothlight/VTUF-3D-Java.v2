package VTUF3D.ConfigMaker;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Common
{



	public static final int z_Topo = 1;
	public static final int T_Surface_K = 2;
	public static final int T_Surface_Diff = 3;
	public static final int T_Surface_Change = 4;
	public static final int Q_Surface = 5;
	public static final int uv_above_Surface = 6;
	public static final int Sensible_heat_flux = 7;
	public static final int Exchange_Coeff_Heat = 8;
	public static final int Latent_heat_flux = 9;
	public static final int Soil_heat_Flux = 10;
	public static final int Sw_Direct_Radiation = 11;
	public static final int Sw_Diffuse_Radiation = 12;
	public static final int Lambert_Factor = 13;
	public static final int Longwave_Radiation_Budget = 14;
	public static final int Longwave_Rad_from_vegetation = 15;
	public static final int Longwave_Rad_from_environment = 16;
	public static final int Water_Flux = 17;
	public static final int Sky_View_Faktor = 18;
	public static final int Building_Height = 19;
	public static final int Surface_Albedo = 20;
	public static final int Deposition_Speed = 21;
	public static final int Mass_Deposed = 22;

	public static final String RUN_DATE = "run_date";
	public static final String RUN_DESC = "run_desc";
	public static final String RUN_NAME = "run_name";
	public static final String ENVI_VERSION = "envi_version";
	public static final String ENVI_VERSION3 = "3";
	public static final String ENVI_VERSION4 = "4";
	public static final String DATA_DIR = "data_dir";

	public static final String RECEPTOR_ID = "receptor_id";
	public static final String RECEPTOR_X = "x";
	public static final String RECEPTOR_Y = "y";
	public static final String RECEPTOR_FILENAME = "filename";
	public static final String RECEPTOR_PATH = "path";
	
	public static int DEFAULT_ROUNDING_PRECISION = 2;
	
	public static final String comma = ",";
	public static final String tab = "\t";
	public static final String linefeed = "\n";
	public static final String underscore = "_";
	

	public void writeFile(String text, String filename)
	{	
		FileOutputStream out; // declare a file output object
		PrintStream p; // declare a print stream object

		try
		{
			out = new FileOutputStream(filename);
			p = new PrintStream(out);
			p.println(text);
			p.close();
		} catch (Exception e)
		{
			System.err.println("Error writing to file");
			e.printStackTrace();
		}

	}
	
	public String multiplyString(String number, int times)
	{
		Integer numberInt = new Integer(number).intValue();
		Integer newNumber = numberInt * times;
		return newNumber.toString();
		
		
	}
	
	public String shortenYearTo2Digits( int year)
	{		
		String shortedYearStr = new Integer(year).toString();
		if (shortedYearStr.length() == 4)
		{
			shortedYearStr = shortedYearStr.substring(2, 4);
		}		
		return shortedYearStr;		
	}
	
	public String increaseYearTo4Digits(String year)
	{
		if (year.length() == 4)
		{
			return year;
		}
		
		if (year.length() == 2)
		{
			Integer yearInt = new Integer(year).intValue();
			if (yearInt < 50)
			{
				year = "20" + year ;
			}
			else
			{
				year = "19" + year;
			}
			
		}
//		else if (year.length() == 1)
//		{
//			Integer yearInt = new Integer(year).intValue();
//			if (yearInt < 50)
//			{
//				year = "200" + yearInt ;
//			}
//			else
//			{
//				year = "19" + yearInt;
//			}
//		}		
		else
		{
			return year;
		}
		
		return year;
		
		
	}
	

	
	
	
	public String getHostname()
	{
		String localHostname = "";
		try 
		{
		    InetAddress addr = InetAddress.getLocalHost();

		    // Get IP Address
		    byte[] ipAddr = addr.getAddress();

		    // Get hostname
		    localHostname = addr.getHostName();
		} 
		catch (UnknownHostException e) 
		{
		}
		return localHostname;

	}
	

	
	public String getMonthForMonthInt(int month)
	{
		String monthStr = "";
		switch (month) {
		  case 1:
			  monthStr = "Jan";
		    break;
		  case 2:	
			  monthStr = "Feb";
		    break;
		  case 3:		   
			  monthStr = "Mar";
		    break;
		  case 4:		   
			  monthStr = "Apr";
		    break;
		  case 5:		   
			  monthStr = "May";
		    break;
		  case 6:		   
			  monthStr = "Jun";
		    break;
		  case 7:		   
			  monthStr = "Jul";
		    break;
		  case 8:		   
			  monthStr = "Aug";
		    break;		    
		  case 9:		   
			  monthStr = "Sep";
		    break;
		  case 10:		   
			  monthStr = "Oct";
		    break;
		  case 11:		   
			  monthStr = "Nov";
		    break;
		  case 12:		   
			  monthStr = "Dec";
		    break;		    
		    
		  default: 
		  
		}
		
		return monthStr;
	}
	

	public String getHourFromFraction(String fraction)
	{
		double fractionDouble = new Double(fraction).doubleValue();
		double hour = fractionDouble * 24;
		long roundHour = Math.round(hour);

//		long hourLong = (long) (hour % 1);
		
		return new Long(roundHour).toString();
		

	}
	
	public int getWeekOfYearFromDayAndMonth(String yearStr, String dayStr, String monthStr)
	{
		int month = new Integer(monthStr).intValue() - 1;
		int day = new Integer(dayStr).intValue();
		int year = new Integer(yearStr).intValue();	

	    return getWeekOfYearFromDayAndMonth(year, day, month);
	}	
	
	public int getWeekOfYearFromDayAndMonth(int year, int day, int month)
	{

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_MONTH, day);	    
	    //calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);	    
	    
	    int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return weekOfYear;
	}		
	
	public int getDayOfYearFromDayAndMonth(String yearStr, String dayStr, String monthStr)
	{
		int month = new Integer(monthStr).intValue() - 1;
		int day = new Integer(dayStr).intValue();
		int year = new Integer(yearStr).intValue();	

	    return getDayOfYearFromDayAndMonth(year, day, month);
	}		
	
	public int getDayOfYearFromDayAndMonth(int year, int day, int month)
	{

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.MONTH, month);
	    calendar.set(Calendar.DAY_OF_MONTH, day);	    
	    //calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);	    
	    
	    int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return dayOfYear;
	}	

	public int getDayOfMonthFromDayOfYear(int year, int dayOfYear)
	{

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);
	    int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return dayOfMonth;
	}
	
	public int getMonthFromDayOfYear(int year, String dayOfYearStr)
	{
		
		Integer dayOfYear = new Integer(dayOfYearStr).intValue(); 

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);
	    int month = calendar.get(Calendar.MONTH) + 1;

	    //System.out.println("dayOfYear=" + dayOfYear + " month=" + month);
	    
	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return month;
	}	
	
	public String getMonthStrFromDayOfYear(int year, String dayOfYearStr)
	{
		
		Integer dayOfYear = new Integer(dayOfYearStr).intValue(); 

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);
	    int month = calendar.get(Calendar.MONTH) + 1;
	    String monthStr = new Integer(month).toString();

	    //System.out.println("dayOfYear=" + dayOfYear + " month=" + month);
	    
	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return monthStr;
	}		

	public int getMonthFromDayOfYear(int year, int dayOfYear)
	{

	    Calendar calendar = Calendar.getInstance();
	    calendar.set(Calendar.DAY_OF_YEAR, dayOfYear);
	    calendar.set(Calendar.YEAR, year);
	    int month = calendar.get(Calendar.MONTH) + 1;

	    //System.out.println("dayOfYear=" + dayOfYear + " month=" + month);
	    
	    //System.out.println("Day of year " + dayOfYear + " = " + calendar.getTime());
	    return month;
	}
	
	//20040011500 to 2004
	public int getYearFromTimecode(String timecode)
	{
		int year = 0;
		
		String yearStr = timecode.substring(0, 4);
		try
		{
			year = new Integer(yearStr).intValue();	
		}
		catch (NumberFormatException e)
		{
			year = 0;
		}		
		
		return year;
	}
	
	//20040011500 to month
	public int getMonthFromTimecode(String timecode)
	{
		int year = getYearFromTimecode(timecode);
		int month = 0;
		int dayOfYear = 0;
		
		String dayOfYearStr = timecode.substring(4, 7);
		try
		{
			dayOfYear = new Integer(dayOfYearStr).intValue();	
		}
		catch (NumberFormatException e)
		{
			month = 0;
		}	
		month = getMonthFromDayOfYear(year, dayOfYear);
		
		return month;
	}	
	
	//20040011500 to day
	public int getDayOfMonthFromTimecode(String timecode)
	{
		int year = getYearFromTimecode(timecode);
		int dayOfMonth = 0;
		int dayOfYear = 0;
		
		String dayOfYearStr = timecode.substring(4, 7);
		try
		{
			dayOfYear = new Integer(dayOfYearStr).intValue();	
		}
		catch (NumberFormatException e)
		{
			dayOfMonth = 0;
		}	
		dayOfMonth = getDayOfMonthFromDayOfYear(year, dayOfYear);
		
		return dayOfMonth;
	}	
	
	//20040011500 to 15
	public int getHourFromTimecode(String timecode)
	{		
		int time;		
		String timeStr = timecode.substring(7, 9);
		try
		{
			time = new Integer(timeStr).intValue();	
		}
		catch (NumberFormatException e)
		{
			time = 0;
		}			
		return time;
	}
	
	double roundTwoDecimals(double d) 
	{
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
	}
	
	long round0Decimal (double d)
	{
		return Math.round(d);
	}
	
	public double roundToDecimals(double d, int c) 
	{
		int temp=(int)((d*Math.pow(10,c)));
		return (((double)temp)/Math.pow(10,c));
	}



	
	
	public String convertTimesToTimeDecimal(String time)
	{	
		//1330 to 13.5
		String hour = time.substring(0, 2);
		String minute = time.substring(2, 4);
		if (minute.equals("00"))
		{
			minute = "";
		}
		else
		{
			minute = ".5";
		}
		
		return hour + minute;
	}
	
	public String convertTimeToTimecode(String timecode)
	{
		// 20040011230 to 1.5416666667
		//String year = timecode.substring(0, 4);
		String day = timecode.substring(4, 7);
		String time = timecode.substring(7, 11);
		
		String convertTime = convertTimesToTimeDecimal(time);
		double convertTimeDouble = new Double(convertTime).doubleValue();
		double timecodeFraction = convertTimeDouble / 24;
		return day + timecodeFraction;
	}
	
	public String padLeft(int str, int size, char padChar)
	{
		//StringBuilder padded = new StringBuilder(str);
		String padded = new Integer(str).toString();
		while (padded.length() < size)
		{
			padded = padChar + padded;
			//padded.append(padChar);
		}
		//return padded.toString();
		return padded;
	}	

	public String padLeft(String str, int size, char padChar)
	{
		//StringBuilder padded = new StringBuilder(str);
		String padded = str;
		while (padded.length() < size)
		{
			padded = padChar + padded;
			//padded.append(padChar);
		}
		//return padded.toString();
		return padded;
	}

	public String padRight(String str, int size, char padChar)
	{
		StringBuilder padded = new StringBuilder(str);
		//String padded = str;
		while (padded.length() < size)
		{
			//padded = padChar + padded;
			padded.append(padChar);
		}
		return padded.toString();
		//return padded;
	}


	

	
	public void createSymlink(String source, String target)
	{
		Runtime rt=Runtime.getRuntime();
		Process result=null;
		String exe=new String("ln"+" -s "+source+" "+target);
		try
		{
			result=rt.exec(exe);
			
			result.waitFor();
			
			String s;
			BufferedReader stdInput = new BufferedReader(new 
		             InputStreamReader(result.getInputStream()));

		    BufferedReader stdError = new BufferedReader(new 
		             InputStreamReader(result.getErrorStream()));

		        // read the output from the command
		    //System.out.println("Here is the standard output of the command:\n");
//		    while ((s = stdInput.readLine()) != null) 
//		    {
//		            System.out.println(s);
//		    }
//
//		    // read any errors from the attempted command
//		    //System.out.println("Here is the standard error of the command (if any):\n");
//		    while ((s = stdError.readLine()) != null) 
//		    {
//		            System.out.println(s);
//		    }
			
			
			
		} catch (IOException e)
		{			
			e.printStackTrace();
		} catch (InterruptedException e) 
		{			
			e.printStackTrace();
		}
	}
	

	
	public void runR(String runDirectory, String rScript, String imageName)
	{
		String rFilename = imageName +
				".r";
		String scriptFilename = "run.sh";
		String scriptStr = "cd " + runDirectory + '\n' + "/usr/bin/R CMD BATCH --no-save " + rFilename + "\n";
		createDirectory(runDirectory);
		writeFile(scriptStr, runDirectory + scriptFilename);
		writeFile(rScript, runDirectory + rFilename);
		
		
		Runtime rt=Runtime.getRuntime();		
		Process result=null;
		//String exe=new String("wine " + exeStr);
		//String[] exe={new String("/bin/sh " + exeStr + "exe.sh"),exeStr};
		String exe=new String("/bin/sh " + runDirectory + scriptFilename);
		try
		{
			
			result=rt.exec(exe);
			result.waitFor();
			
			String s;
			BufferedReader stdInput = new BufferedReader(new 
		             InputStreamReader(result.getInputStream()));

		    BufferedReader stdError = new BufferedReader(new 
		             InputStreamReader(result.getErrorStream()));

		        // read the output from the command
		    //System.out.println("Here is the standard output of the command:\n");
		    while ((s = stdInput.readLine()) != null) 
		    {
		            System.out.println(s);
		    }

		    // read any errors from the attempted command
		    //System.out.println("Here is the standard error of the command (if any):\n");
		    while ((s = stdError.readLine()) != null) 
		    {
		            System.out.println(s);
		    }
			
			
			
			
			//System.out.println(result.getOutputStream());
		} catch (Exception e)
		{			
			e.printStackTrace();
		}
	}
	
	/**
	 * @param runDirectory
	 * @param rScript
	 */
	@Deprecated
	public void runR(String runDirectory, String rScript)
	{
		String rFilename = "run.r";
		String scriptFilename = "run.sh";
		String scriptStr = "cd " + runDirectory + '\n' + "/usr/bin/R CMD BATCH " + rFilename + "\n";
		createDirectory(runDirectory);
		writeFile(scriptStr, runDirectory + scriptFilename);
		writeFile(rScript, runDirectory + rFilename);
		
		
		Runtime rt=Runtime.getRuntime();		
		Process result=null;
		//String exe=new String("wine " + exeStr);
		//String[] exe={new String("/bin/sh " + exeStr + "exe.sh"),exeStr};
		String exe=new String("/bin/sh " + runDirectory + scriptFilename);
		try
		{
			
			result=rt.exec(exe);
			result.waitFor();
			
			String s;
			BufferedReader stdInput = new BufferedReader(new 
		             InputStreamReader(result.getInputStream()));

		    BufferedReader stdError = new BufferedReader(new 
		             InputStreamReader(result.getErrorStream()));

		        // read the output from the command
		    //System.out.println("Here is the standard output of the command:\n");
		    while ((s = stdInput.readLine()) != null) 
		    {
		            System.out.println(s);
		    }

		    // read any errors from the attempted command
		    //System.out.println("Here is the standard error of the command (if any):\n");
		    while ((s = stdError.readLine()) != null) 
		    {
		            System.out.println(s);
		    }
			
			
			
			
			//System.out.println(result.getOutputStream());
		} catch (Exception e)
		{			
			e.printStackTrace();
		}
	}	
	
	public void runWineExe(String runDirectory, String exeStr)
	{
		String filename = "run.sh";
		String scriptStr = "cd " + runDirectory + '\n' + "wine " + exeStr + "\n";
		createDirectory(runDirectory);
		writeFile(scriptStr, runDirectory + filename);
		
		
		Runtime rt=Runtime.getRuntime();		
		Process result=null;
		//String exe=new String("wine " + exeStr);
		//String[] exe={new String("/bin/sh " + exeStr + "exe.sh"),exeStr};
		String exe=new String("/bin/sh " + runDirectory + "run.sh");
		try
		{
			
			result=rt.exec(exe);
			result.waitFor();
			
			String s;
			BufferedReader stdInput = new BufferedReader(new 
		             InputStreamReader(result.getInputStream()));

		    BufferedReader stdError = new BufferedReader(new 
		             InputStreamReader(result.getErrorStream()));

		        // read the output from the command
		    System.out.println("Here is the standard output of the command:\n");
		    while ((s = stdInput.readLine()) != null) 
		    {
		            System.out.println(s);
		    }

		    // read any errors from the attempted command
		    System.out.println("Here is the standard error of the command (if any):\n");
		    while ((s = stdError.readLine()) != null) 
		    {
		            System.out.println(s);
		    }
			
			
			
			
			//System.out.println(result.getOutputStream());
		} catch (Exception e)
		{			
			e.printStackTrace();
		}
	}	



	public boolean createDirectory(String directory)
	{
//	    // Create one directory
//	    boolean success = (new File(directory)).mkdir();
//	    if (success) {
//	      System.out.println("Directory: " + directory + " created");
//	    }


	    // Create multiple directories
	    boolean success = (new File(directory)).mkdirs();
	    if (success) {
	      System.out.println("Directories: " + directory + " created");
	    }

	    return success;


	}
	
	public boolean isFile(String filename)
	{
		File file = new File(filename);
		return file.exists();
	}

	
	public boolean isDirectory(String directory)
	{
		File file = new File(directory);
		return file.isDirectory();
	}

	@SuppressWarnings("unchecked")
	public String[] getDirectoryList(String directory)
	{
		FilenameFilter filter = new FilenameFilter()
		{
			public boolean accept(File dir, String name)
			{
				boolean accept = true;
				if (name.contains("##check"))
				{
					accept = false;
				} else if (name.startsWith("."))
				{
					accept = false;
				}
				else if (name.contains("##Check"))
				{
					accept = false;
				}
				return accept;
			}
		};


		File dir = new File(directory);

		File files[] = dir.listFiles(filter);
		Arrays.sort( files, new Comparator<File>()
		{
//		     public int compare(final Object o1, final Object o2) {
//		       return new Long(((File)o1).lastModified()).compareTo
//		             (new Long(((File) o2).lastModified()));
//		      }

			@Override
			public int compare(File o1, File o2)
			{
				return new Long((o1).lastModified()).compareTo
	             (new Long(( o2).lastModified()));
			}
		});

		String[] fileNames = new String[files.length];
		int count = 0;
		for (File file : files)
		{
			fileNames[count] = file.getName();
			//System.out.println(fileNames[count]);
			count ++;
		}


//		//String[] children = dir.list();
//		if (files == null)
//		{
//			// Either dir does not exist or is not a directory
//		} else
//		{
//			for (int i = 0; i < children.length; i++)
//			{
//				// Get filename of file or directory
//				String filename = children[i];
//			}
//		}
//
//		children = dir.list(filter);
//		return children;
		return fileNames;

	}
	
	public ArrayList<String> readFileFromUrl(String fileUrl)
	{
		ArrayList<String> returnValue = new ArrayList<String>();
		URL url = null;
		try
		{
			url = new URL(fileUrl);
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		BufferedReader in = null;
		try
		{
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		String inputLine;
		try
		{
			while ((inputLine = in.readLine()) != null)
			{
				returnValue.add(inputLine);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			in.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return returnValue;
	}
	
	public static String[] splitOnWhitespace(String line)
	{
		return line.split("\\s+");
	}
	
	public int getIntFromArray(String[] array, int item)
	{
		return Integer.parseInt(array[item]);
	}
	public double getDoubleFromArray(String[] array, int item)
	{
		return Double.parseDouble(array[item]);
	}
	
//	public void sortByValue()
//	{
//		SortedSet<Map.Entry<String,Integer>> sortedValues = entriesSortedByValues(d);
//	    Iterator<Entry<String, Integer>> itr = sortedValues.iterator(); 
//	    while (itr.hasNext())
//	    {
//	    	Entry<String, Integer> item = itr.next();
//	    	String colorStr = item.getKey();
//	    	double[] color = getColorFromKey(colorStr);
//	    	commonColors.add(color);
//	    }
//	    Collections.reverse(commonColors);
//	}
	
	public <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) 
	{
	    Comparator<K> valueComparator =  new Comparator<K>() 
	    {
	        public int compare(K k1, K k2) 
	        {
	            int compare = map.get(k2).compareTo(map.get(k1));
	            if (compare == 0) return 1;
	            else return compare;
	        }
	    };
	    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
	    sortedByValues.putAll(map);
	    return sortedByValues;
	}
	
	public static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValues(Map<K,V> map) 
	{
	    SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(
	        new Comparator<Map.Entry<K,V>>() 
	        {
	            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) 
	            {
	                int res = e1.getValue().compareTo(e2.getValue());
	                return res != 0 ? res : 1;
	            }
	        }
	    );
	    sortedEntries.addAll(map.entrySet());
	    return sortedEntries;
	}
	
   public ArrayList<String> readLargerTextFileAlternateToArray(String aFileName) 
   {
	   ArrayList<String> returnValue = new ArrayList<String>();
	   if (aFileName.contains("http"))
	   {
		   return readFileFromUrl(aFileName);
	   }
	    Path path = Paths.get(aFileName);
	    try (BufferedReader reader = Files.newBufferedReader(path, ENCODING))
	    {
	      String line = null;
	      while ((line = reader.readLine()) != null) 
	      {
	    	  if (line.trim().equals(""))
	    	  {
	    		  continue;
	    	  }
	    	  returnValue.add(line);
	      }      
	    }
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	    return returnValue;
	  }
   
   public ArrayList<String> readLargerTextFileAlternateToArray(String aFileName, boolean throwException) 
   {
	   ArrayList<String> returnValue = new ArrayList<String>();
	   if (aFileName.contains("http"))
	   {
		   return readFileFromUrl(aFileName);
	   }
	    Path path = Paths.get(aFileName);
	    try (BufferedReader reader = Files.newBufferedReader(path, ENCODING))
	    {
	      String line = null;
	      while ((line = reader.readLine()) != null) 
	      {
	    	  if (line.trim().equals(""))
	    	  {
	    		  continue;
	    	  }
	    	  returnValue.add(line);
	      }      
	    }
		catch (IOException e)
		{			
			if (throwException)
			{
				e.printStackTrace();
			}
		}
	    return returnValue;
	  }
	   
   public ArrayList<String> readLargerTextFileAlternateToArray(String aFileName, int lines) 
   {
	   int count = 0;
	   ArrayList<String> returnValue = new ArrayList<String>();
	   if (aFileName.contains("http"))
	   {
		   return readFileFromUrl(aFileName);
	   }
	    Path path = Paths.get(aFileName);
	    try (BufferedReader reader = Files.newBufferedReader(path, ENCODING))
	    {
	      String line = null;
	      while ((line = reader.readLine()) != null) 
	      {
	    	  if (line.trim().equals(""))
	    	  {
	    		  continue;
	    	  }
	    	  returnValue.add(line);
	    	  count ++;
	    	  if (count > lines)
	    	  {
	    		  break;
	    	  }
	      }      
	    }
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	    return returnValue;
	  }

	public Common()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void appendFile(String text, String filename)
	{
		BufferedWriter bw = null;

		try
		{
			// APPEND MODE SET HERE
			bw = new BufferedWriter(new FileWriter(filename, true));
			bw.write(text);
			bw.newLine();
			bw.flush();
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{ // always close the file
			if (bw != null)
				try
				{
					bw.close();
				}
				catch (IOException ioe2)
				{
					// just ignore it
				}
		} // end try/catch/finally

	}
	
	public String percentOfStrRounded(String number, double percent, int decimals)
	{
		Double doubleNumber = new Double(number).doubleValue();
		double returnValue = doubleNumber * percent;
		returnValue = roundToDecimals(returnValue,2);
		return "" + returnValue;
	}
	
	public double convertSpecHumidityToRH(double qair, double temp, double press)
	{
	
//	##' Convert specific humidity to relative humidity
//	##'
//	##' converting specific humidity into relative humidity
//	##' NCEP surface flux data does not have RH
//	##' from Bolton 1980 The computation of Equivalent Potential Temperature 
//	##' url{http://www.eol.ucar.edu/projects/ceop/dm/documents/refdata_report/eqns.html}
//	##' @title qair2rh
//	##' @param qair specific humidity, dimensionless (e.g. kg/kg) ratio of water mass / total air mass
//	##' @param temp degrees C
//	##' @param press pressure in mb
//	##' @return rh relative humidity, ratio of actual water mixing ratio to saturation mixing ratio
//	##' @export
//	##' @author David LeBauer
//	qair2rh <- function(qair, temp, press = 1013.25){
	    double es =  6.112 * Math.exp((17.67 * temp)/(temp + 243.5));
	    double e = qair * press / (0.378 * qair + 0.622);
	    double rh = e / es;
	    if (rh > 1)
	    {
	    	rh = 1;
	    }
	    if (rh < 0)	    	
	    {
	    	rh = 0;
	    }
//	    rh[rh > 1] <- 1;
//	    rh[rh < 0] <- 0;
	    return(rh);
	}
	
	public float convertSpecHumidityToRH(float qair, float temp, float press)
	{
	
//	##' Convert specific humidity to relative humidity
//	##'
//	##' converting specific humidity into relative humidity
//	##' NCEP surface flux data does not have RH
//	##' from Bolton 1980 The computation of Equivalent Potential Temperature 
//	##' url{http://www.eol.ucar.edu/projects/ceop/dm/documents/refdata_report/eqns.html}
//	##' @title qair2rh
//	##' @param qair specific humidity, dimensionless (e.g. kg/kg) ratio of water mass / total air mass
//	##' @param temp degrees C
//	##' @param press pressure in mb
//	##' @return rh relative humidity, ratio of actual water mixing ratio to saturation mixing ratio
//	##' @export
//	##' @author David LeBauer
//	qair2rh <- function(qair, temp, press = 1013.25){
		double es =  6.112 * Math.exp((17.67 * temp)/(temp + 243.5));
		double e = qair * press / (0.378 * qair + 0.622);
		Double rh = e / es;
	    if (rh > 1)
	    {
	    	rh = 1.;
	    }
	    if (rh < 0)	    	
	    {
	    	rh = 0.;
	    }
//	    rh[rh > 1] <- 1;
//	    rh[rh < 0] <- 0;
	    float rhFloat = rh.floatValue();
	    return rhFloat;
	}
	
	public double calcWindSpeed(double U, double V)
	{
		return Math.sqrt( U*U + V*V  );
	}
	public double calcWindDir(double U, double V)
	{
		return Math.atan(V/U);
	}	
	
	public double calcWindDirDegrees2(double U, double V)
	{
		return (180.+Math.toDegrees(Math.atan2(U,V)) % 360);
	}
	
	public double calcWindDirDegrees(double U, double V)
	{
		double windAbs = calcWindSpeed(U,V);
		double windDirTrigTo = Math.atan2(U/windAbs, V/windAbs);
		double windDirTrigToDegrees =  windDirTrigTo * 180.0/Math.PI;
		return 90.0 - windDirTrigToDegrees;
	}	
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
   public String readLargerTextFileAlternate(String aFileName) 
   {	   
	   StringBuilder sb = new StringBuilder();
	    Path path = Paths.get(aFileName);
	    try (BufferedReader reader = Files.newBufferedReader(path, ENCODING))
	    {
	      String line = null;
	      while ((line = reader.readLine()) != null) 
	      {
	    	  sb.append(line + '\n');
	      }      
	    }
		catch (IOException e)
		{			
			e.printStackTrace();
		}
	    return sb.toString();
	  }
	   
	   public String readTextFileToString(String filename)
	   {
		   return readLargerTextFileAlternate(filename);
	   }
	
	public String addDayToDay(String day, int additionalDays)
	{
		Integer startingDay = new Integer(day).intValue();
		Integer nextDay = startingDay + additionalDays;
		return nextDay.toString();
	}
	
	public String averageListOfStrings(ArrayList<String> data)
	{
		String averageStr = "0";
		if (data == null || data.size() == 0)
		{
			return averageStr;
		}			
		
		double total = 0.0;
		Double average = 0.0;
		
		for (String item : data)
		{
			Double itemDouble;
			try
			{
				itemDouble = new Double(item).doubleValue();
			}
			catch (Exception e)
			{
				return "0";
			}
			total = total + itemDouble;
		}
		average = total / data.size();
		average = roundTwoDecimals(average);
		averageStr = average.toString();
		
		return averageStr;
		
	}
	
	public String getAverageOf2DoubleStrings(String item1, String item2)
	{
		Double item1Double = new Double(item1).doubleValue();
		Double item2Double = new Double(item2).doubleValue();
		Double average =  roundToDecimals(  ((item1Double + item2Double) / 2), 2);		
		
		return average.toString();
	}


	public void CalculateH(double t1, double rh1, double t2)
	{

			t1 = t1 + 273.0;
			t2 = t2 + 273.0;

			double	p0, deltaH, R;
			p0 = 7.5152E8;
			deltaH = 42809;
			R = 8.314;

			double sat_p1, sat_p2, vapor, rh2, dew;
			sat_p1 = p0 * Math.exp(-deltaH/(R*t1));
			sat_p2 = p0 * Math.exp(-deltaH/(R*t2));
			vapor = sat_p1 * rh1/100;
			rh2 = (vapor/sat_p2)*100;
			dew = -deltaH/(R*Math.log(vapor/p0)) - 273;

			//vapor = Math.round(vapor*10)/10;
			//rh2   = Math.round(rh2*10)/10;
			//dew   = Math.round(dew*10)/10;

			System.out.println("rh2=" + rh2);
			System.out.println("dew=" + dew);
			System.out.println("vapor=" + vapor);




//			rh2text   = rh2.toString();
//			dewtext   = dew.toString();
//			vaportext = vapor.toString();

	}
	
	public String convertHpaToKpa(String kpaValue)
	{
		String returnValue = "101.30" ;
		
		try 
		{
			Double kpaDouble = new Double(kpaValue).doubleValue();
			kpaDouble = kpaDouble * 0.1;
			kpaDouble = roundToDecimals(kpaDouble, 2);
			returnValue = kpaDouble.toString();
		}
		catch (Exception e)
		{
			
		}
		return returnValue; 
		
		
	}

	public void CalculateH(double t1, double rh1)
	{

			t1 = t1 + 273.0;
			//t2 = t2 + 273.0;

			double	p0, deltaH, R;
			p0 = 7.5152E8;
			deltaH = 42809;
			R = 8.314;

			double sat_p1, sat_p2, vapor, rh2, dew;
			sat_p1 = p0 * Math.exp(-deltaH/(R*t1));
			//sat_p2 = p0 * Math.exp(-deltaH/(R*t2));
			vapor = sat_p1 * rh1/100;
			//rh2 = (vapor/sat_p2)*100;
			dew = -deltaH/(R*Math.log(vapor/p0)) - 273;

			//vapor = Math.round(vapor*10)/10;
			//rh2   = Math.round(rh2*10)/10;
			//dew   = Math.round(dew*10)/10;

			//System.out.println("rh2=" + rh2);
			System.out.println("dew=" + dew);
			System.out.println("vapor=" + vapor);




//			rh2text   = rh2.toString();
//			dewtext   = dew.toString();
//			vaportext = vapor.toString();

	}

	public double CalculateRH(double tempC, double vapor)
	{
		double rh1=0;
		double tempK = tempC + 273.0;

		double p0 = 7.5152E8;
		double deltaH = 42809;
		double R = 8.314;

		double sat_p1 = 7.5152E8 * Math.exp(-42809/(8.314*tempK));

		rh1 = 100 * vapor / sat_p1;

		//vapor = sat_p1 * rh1/100;

//		double sat_p1 = p0 * Math.exp(-deltaH/(R*tempK));
//		vapor = sat_p1 * rh1/100;

		//double dew = -deltaH/(R*Math.log(vapor/p0)) - 273;

		//System.out.println("dew=" + dew);
		System.out.println("vapor=" + vapor);
		System.out.println("rh1=" + rh1);

		return rh1;

	}

	public double CalculateRH2(double tempC, double vapor)
	{

//		double tempK = tempC + 273.0;

//		double p0 = 7.5152E8;
//		double deltaH = 42809;
//		double R = 8.314;

//		double sat_p1 = 7.5152E8 * Math.exp(-42809/(8.314*(tempC + 273.0)));

		double rh = 100 * vapor / (7.5152E8 * Math.exp(-42809/(8.314*(tempC + 273.0))));

		//vapor = sat_p1 * rh1/100;

//		double sat_p1 = p0 * Math.exp(-deltaH/(R*tempK));
//		vapor = sat_p1 * rh1/100;

		//double dew = -deltaH/(R*Math.log(vapor/p0)) - 273;

		//System.out.println("dew=" + dew);
		//System.out.println("vapor=" + vapor);
		//System.out.println("rh1=" + rh);

		return rh;

	}

	public double CalculateVaporPressure(double t_hmp, double rh_hmp)
	{

		double A_0 = 6.107800;
		double A_1 = 4.436519e-1;
		double A_2 = 1.428946e-2;
		double A_3 = 2.650648e-4;
		double A_4 = 3.031240e-6;
		double A_5 = 2.034081e-8;
		double A_6 = 6.136821e-11;

		rh_hmp = rh_hmp*0.01;

//		// 'Find the HMP45C vapor pressure, in kPa, using a sixth order polynomial (Lowe, 1976).
//		double e_sat = 0.1*(A_0+t_hmp*(A_1+t_hmp*(A_2+t_hmp*(A_3+t_hmp*(A_4+t_hmp*(A_5+t_hmp*A_6))))));

		double e_sat = CalculateVaporPressurekPa(t_hmp);

		double e = e_sat*rh_hmp;

		//hmp in this case just refers to the instrument taking the temperature (t_hmp) and humidity (rh_hmp) measurements

		return e;
	}

	public double CalculateRHFromVapor(double t_hmp, double e)
	{
		double e_sat = CalculateVaporPressurekPa(t_hmp);

		double rh_hmp = e/e_sat ;

		//double e = e_sat*rh_hmp;

		return rh_hmp * 100;
	}

	public double CalculateRHFromVapor2(double t_hmp, double e)
	{
		return e/(0.1*(6.107800+t_hmp*(4.436519e-1+t_hmp*(1.428946e-2+t_hmp*(2.650648e-4+t_hmp*(3.031240e-6+t_hmp*(2.034081e-8+t_hmp*6.136821e-11)))))))*100;
	}


	public double CalculateVaporPressurekPa(double t_hmp)
	{
		double A_0 = 6.107800;
		double A_1 = 4.436519e-1;
		double A_2 = 1.428946e-2;
		double A_3 = 2.650648e-4;
		double A_4 = 3.031240e-6;
		double A_5 = 2.034081e-8;
		double A_6 = 6.136821e-11;

		// 'Find the HMP45C vapor pressure, in kPa, using a sixth order polynomial (Lowe, 1976).
		double e_sat = 0.1*(A_0+t_hmp*(A_1+t_hmp*(A_2+t_hmp*(A_3+t_hmp*(A_4+t_hmp*(A_5+t_hmp*A_6))))));

		return e_sat;
	}


	

	public String removeIllegalCharacters(String str)
	{
		return str.replaceAll("/", "_");
	}

  


}
