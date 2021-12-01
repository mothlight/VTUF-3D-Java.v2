package VTUF3D.ConfigMaker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.TreeMap;

public class MaespaConfigConfileDat
{
	Common common = new Common();
	
	public final String FILENAME_PREFIX = "confile";
	public final String FILENAME_SUFFUX = ".dat";
	public final String prestonWeatherTable = "Preston_data";
	
	private String runDirectory;
	private String filename ;
	private String fileText;
	private int year;
	private String runPrefix;
	private int day;
	private int numberOfDays;
	
	private String startDate;
	private String endDate;
	
	


	
	public MaespaConfigConfileDat(String runDirectory, int year, String configNumber, int day, int numDays)
	{
		super();

		this.runDirectory = runDirectory;		
		this.year = year;
		this.runPrefix = configNumber;			
		this.filename = generateFilename(runPrefix, year);	
		setDay(day);
		setNumberOfDays(numDays);		
		
		calcStartAndEndDates();
		
	}	
	

	
	public void calcStartAndEndDates()
	{
		int startDay = getDay();

		int startDayInt = new Integer(startDay).intValue();
	
		 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
		 
	     GregorianCalendar gc = new GregorianCalendar();
	     gc.set(GregorianCalendar.DAY_OF_YEAR, startDayInt);
	     gc.set(GregorianCalendar.YEAR, getYear());

	     setStartDate(sdf.format(gc.getTime()));
	     
	     gc.add(GregorianCalendar.DAY_OF_YEAR, getNumberOfDays());
//	     gc.set(GregorianCalendar.DAY_OF_YEAR, startDayInt + getNumberOfDays());
	     setEndDate(sdf.format(gc.getTime()));
		
	}
	
	public String generateFile()
	{
		String fileText = generateConfigFileText(runPrefix);
		setFileText(fileText);
		return fileText;
	}
	
	private String configFileTitle = "Bago BB gs ";
	private int configiohrly=1;
	private int configiotutd=9;
	private int configioresp=0;
	private int configiohist=0;
	private int configNotrees =6;
	private int configIsunla=0;
	private int configIsimus=0;
	
	private int configIowatbal =1;
	private int configIopoints =1;
	private String configStartdate ="09/02/12";
	private String configEnddate ="15/05/12";
	private int configItargets = 1;
	private int configNspecies = 1;
	private String configSpeciesnames="O.europaea";
	private String configPhyfiles="phyolive.dat";
	private String configStrfiles="strolive.dat";
	private int configNolay = 6;
	private int configPplay = 12;
	private int configNzen = 5;
	private int configNaz= 11;
	private int configModelgs = 4;
	private int configModeljm = 0;
	private int configModelrd = 0;
	private int configModelss = 0;
	private int configItermax = 100;
	
	private String generateConfigFileText(String runPrefix)
	{
	
		StringBuffer st = new StringBuffer();
		
		st.append(configFileTitle + '\n');
		st.append("" + '\n');
		st.append("&control" + '\n');
		st.append("iohrly = "
				+ configiohrly
				+ "			!Flag: If IOHRLY = 0, no hourly output; if IOHRLY = 1, hourly values; if IOHRLY = 2, values for each layer are output to layflx.dat" + '\n');
		st.append("iotutd = "
				+ configiotutd
				+ "     			!Flag: Calculation of transmittances; IOTUTD = 0, transmittances read from tutd.dat (automatically written at the end of each simulation) If IOTUTD > 0, the transmittances are calculated every TUTDth (1) day" + '\n');
		st.append("ioresp = "
				+ configioresp
				+ "     			!Flag: Print respiration fluxes; IORESP = 1, the file resp.dat is created. Default: IORESP = 0." + '\n');
		st.append("iohist = "
				+ configiohist
				+ "     			!Flag: PAR Histogram printed; Printed if IOHIST = 1. Default: IOHIST = 0." + '\n');
		st.append("iowatbal = "
				+ configIowatbal
				+ "			!Flag: include water balance (if=1)" + '\n');
		st.append("ipoints=" + configIopoints +   '\n');
		st.append("isunla=" + configIsunla +   '\n');
		st.append("isimus=" + configIsimus +   '\n');
		st.append("/" + '\n');
		st.append("" + '\n');
		st.append("&dates" + '\n');
		st.append("startdate = '"
				+ configStartdate
				+ "'		!Specify period of simulation" + '\n');
		st.append("enddate = '"
				+ configEnddate
				+ "'" + '\n');
		st.append("/				" + '\n');
		st.append("" + '\n');
		st.append("&treescon	" + '\n');
		st.append("itargets = "
				+ configItargets
				+ "			!Specify the number of the target tree" + '\n');
		st.append("/" + '\n');
		st.append("" + '\n');
		st.append("&species" + '\n');
		st.append("nspecies = "
				+ configNspecies
				+ "			!Number of Species being modelled" + '\n');
		st.append("speciesnames='"
				+ configSpeciesnames
				+ "'	!Name of species" + '\n');
		st.append("phyfiles='"
				+ configPhyfiles
				+ "'		!Physiology file for species" + '\n');
		st.append("strfiles='"
				+ configStrfiles
				+ "'		!Tree structure file for species" + '\n');
		st.append("/" + '\n');
		st.append("" + '\n');
		st.append("&diffang					" + '\n');
		st.append("nolay = "
				+ configNolay
				+ "			!Number of layers in the crown assumed when calculating radiation interception." + '\n');
		st.append("pplay = "
				+ configPplay
				+ "			!Number of points per layer" + '\n');
		st.append("nzen = "
				+ configNzen
				+ "			!NZEN is the number of zenith angles for which diffuse transmittances are calculated. Def.: NZEN = 5 (maximum MAXANG)." + '\n');
		st.append("naz= "
				+ configNaz
				+ "				!NAZ is the number of azimuth angles for which the calculation is done. Def.: NAZ = 11 (no maximum enforced)." + '\n');
		st.append("/" + '\n');
		st.append("" + '\n');
		st.append("&model" + '\n');
		st.append("modelgs = "
				+ configModelgs
				+ "			!Model to calculate stomatal conductance. MODEL GS=6: Tuzet MODELGS=2: Ball-Berry model (response to RH); MODELGS=3: Ball-Berry-Leuning model (response to VPD); MODELGS=4: the Ball-Berry-Opti model (Medlyn et al. 2011); Model=6: Tuzet (Tuzet NAMELIST needed)" + '\n');
		st.append("modeljm = "
				+ configModeljm
				+ "			!How JMAX and VCMAX parameters read in: MODELJM = 0, read from file; MODELJM = 1 calculated from leaf N content. Default: 0." + '\n');
		st.append("modelrd = "
				+ configModelrd
				+ "			!How Leaf respiration (RDO) parameters read in. MODELRD=0, read from file; MODELRD=1, calculated from leaf N content. Default: 0." + '\n');
		st.append("modelss = "
				+ configModelss
				+ "			!Photosynthesis calculations sun/shade (SS) leaves separately (MODELSS = 0 (Def.); Absorbed radiation is averaged over the foliage in the grid point (MODELSS=1): If MODELSS = 2, calculations for each leaf angle class separately" + '\n');
		st.append("itermax = "
				+ configItermax
				+ "			!ITERMAX controls iterations in the combined photosynthesis-transpiration model. If ITERMAX = 0 (Def.), leaf temperature is assumed = air temperature. If ITERMAX > 0, an iterative method is used to find leaf temperature, photosynthesis, and transpiration. ITERMAX gives the maximum number of iterations (ensuring convergence)" + '\n');
		st.append("/" + '\n');



		
		st.append(    '\n' );
		st.append(    '\n' );
		return st.toString();
	

		
		
	}	
	
	
	public void writeTreeConfigFile(String inputDirectory, int number)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getTreeDataFile(), runDirectory + inputDirectory + "/" + "trees"
				+ number
				+ ".dat");
	}
	
	public void writeTreeConfigFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getTreeDataFile(), runDirectory + inputDirectory + "/" + "trees.dat");
	}
	
	public void writeStrConfigFile(String inputDirectory, int number)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getStrDataFile(), runDirectory + inputDirectory + "/" + "str"
				+ number
				+ ".dat");
	}
	
	public void writeStrConfigFile(String fileText, String inputDirectory, int number)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(fileText, runDirectory + inputDirectory + "/" + "str"
				+ number
				+ ".dat");
	}
	

	
	public void writePhyConfigFile(String inputDirectory, int number)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getPhyDataFile(), runDirectory + inputDirectory + "/" + "phy"
				+ number
				+ ".dat");
	}
	
	public void writePhyConfigFile(String fileStr, String inputDirectory, int number)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(fileStr, runDirectory + inputDirectory + "/" + "phy"
				+ number
				+ ".dat");
	}
	

	
	public void writeWatbalConfigFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getWatbalDataFile(), runDirectory + inputDirectory + "/" + "watbal.dat");
	}
	
	public void writeWatbalConfigFile(String watbalDataFile, String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(watbalDataFile, runDirectory + inputDirectory + "/" + "watbal.dat");
	}
	
	public void writeWatparsConfigFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getWatparsDataFile(), runDirectory + inputDirectory + "/" + "watpars.dat");
	}
	
	public void writeWatparsConfigFile(String watparsDataFile, String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(watparsDataFile, runDirectory + inputDirectory + "/" + "watpars.dat");
	}
	
	public void writePointsConfigFile(String inputDirectory, MaespaConfigConfileDat maespaConfigConfileDat)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getPointsDataFile(maespaConfigConfileDat), runDirectory + inputDirectory + "/" + "points.dat");
	}
	
	
	public void writeConfigFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getFileText(), runDirectory + inputDirectory + "/" + this.filename);
	}
	
	public void writeGenericConfigFile(String metData, String outputName, String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		System.out.println("create directory " + runDirectory + inputDirectory);
		common.writeFile(metData, runDirectory + inputDirectory + "/" + outputName);
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




	public int getNumberOfDays()
	{
		return numberOfDays;
	}

	public void setNumberOfDays(int numberOfDays)
	{
		this.numberOfDays = numberOfDays;
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
	
	private String configStrTitle = "Canopy Structure file for Olea europaea (Olive trees) at Smith Street";
	private String configStrCshape = "ROUND";
	private int configStrLiaElp = 1;
	private int configStrLiaNalpha = 1;
	private int configStrLiaAvgang = 45;
	
	private int configStrLaddJleaf = 0;
	private int configStrLaddNoagec = 1;
	private double configStrLaddRandom = 1.0;
	
	private int configStrAeroExtwind = 0;
	private double configStrAllomCoefft = 91.4114;
	private double configStrAllomExpont = 1.741;
	private double configStrAllomWinterc = 0.0;
	
	private double configStrAllombCoefft = 8.9126;
	private double configStrAllombExpont = 1.341;
	private double configStrAllombWinterc = 0.0;
	
	private int configStrAllomrCoefft = 20;
	private double configStrAllomrExpont = 2.0;
	private double configStrAllomrRinterc = 0.0;
	private double configStrAllomrFrfrac = 0.4;
	
	public String getStrDataFile(int configType)
	{
		String file = "";
		
		if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			file = 
					"Canopy Structure file for Lophostemon Confertus" + '\n' +
					"" + '\n' +
					"&canopy					" + '\n' +
					"cshape = 'ROUND'		!Shape of the canopy			" + '\n' +
					"/					" + '\n' +
					"" + '\n' +
					"Leaf incidence angle" + '\n' +
					"&lia		" + '\n' +
					"elp = 1				!Leaf angle distribution (1=spherical)" + '\n' +
					"nalpha = 1			!Number of leaf angle classes" + '\n' +
					"avgang = 45			!Mean leaf inclination angle (L.Confertus is a Plagiophile tree)" + '\n' +
					"/	 " + '\n' +
					"" + '\n' +
					"Beta distributions for leaf area density" + '\n' +
					"&ladd" + '\n' +
					"jleaf = 0			!**If JLEAF=0, the leaf area density is assumed to be uniform. If JLEAF=1, then LAD follows Beta distribution in vertical; If JLEAF=2 beta distribution in Vertical and horizontal." + '\n' +
					"noagec = 1			!number of age classes" + '\n' +
					"random = 1.0			!**Level of clumping of foliage into shoots" + '\n' +
					"/" + '\n' +					
					"" + '\n' +
					"&aero" + '\n' +
					"extwind = 0			!exponential coefficient for wind speed decline with height (0=default)" + '\n' +
					"/" + '\n' +				
					"" + '\n' +
					"Allometric relationships for biomass: Only used to calculate the woody respiration rate" + '\n' +
					"&allom" + '\n' +
					"coefft = 91.4114" + '\n' +
					"expont = 1.741" + '\n' +
					"winterc = 0.0" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"Branch biomass" + '\n' +
					"&allomb" + '\n' +
					"bcoefft = 8.9126" + '\n' +
					"bexpont = 1.341" + '\n' +
					"binterc = 0.0" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"Total root biomass" + '\n' +
					"&allomr" + '\n' +
					"rcoefft = 20" + '\n' +
					"rexpont = 2.0" + '\n' +
					"rinterc = 0.0" + '\n' +
					"frfrac = 0.4" + '\n' +
					"/" + '\n' ;

		}
		
		return file;
	}
	
	public String getStrDataFile()
	{
		String treeFile = 
				configStrTitle + '\n' +
				"" + '\n' +
				"&canopy	" + '\n' +				
				"cshape = '"
				+ configStrCshape
				+ "'		!Shape of the canopy	" + '\n' +		
				"/				" + '\n' +	
				"" + '\n' +
				"Leaf incidence angle" + '\n' +
				"&lia		" + '\n' +
				"elp = "
				+ configStrLiaElp
				+ "				!Leaf angle distribution (1=spherical)" + '\n' +
				"nalpha = "
				+ configStrLiaNalpha
				+ "			!Number of leaf angle classes" + '\n' +
				"avgang = "
				+ configStrLiaAvgang
				+ "			!Mean leaf inclination angle (PHATTARALERPHONG et al 2006)" + '\n' +
				"/	 " + '\n' +
				"" + '\n' +
				"Beta distributions for leaf area density" + '\n' +
				"&ladd" + '\n' +
				"jleaf = "
				+ configStrLaddJleaf
				+ "			!**If JLEAF=0, the leaf area density is assumed to be uniform. If JLEAF=1, then LAD follows Beta distribution in vertical; If JLEAF=2 beta distribution in Vertical and horizontal." + '\n' +
				"noagec = "
				+ configStrLaddNoagec
				+ "			!number of age classes" + '\n' +
				"random = "
				+ configStrLaddRandom
				+ "			!**Level of clumping of foliage into shoots" + '\n' +
				"/			" + '\n' +		
				"" + '\n' +
				"&aero" + '\n' +
				"extwind = "
				+ configStrAeroExtwind
				+ "			!exponential coefficient for wind speed decline with height." + '\n' +
				"" + '\n' +
				"Allometric relationships for biomass: Only used to calculate the woody respiration rate" + '\n' +
				"&allom" + '\n' +
				"coefft = "
				+ configStrAllomCoefft + '\n' +
				"expont = "
				+ configStrAllomExpont + '\n' +
				"winterc = "
				+ configStrAllomWinterc + '\n' +
				"/" + '\n' +
				"" + '\n' +
				"Branch biomass" + '\n' +
				"&allomb" + '\n' +
				"bcoefft = "
				+ configStrAllombCoefft + '\n' +
				"bexpont = "
				+ configStrAllombExpont + '\n' +
				"binterc = "
				+ configStrAllombWinterc + '\n' +
				"/" + '\n' +
				"" + '\n' +
				"Total root biomass" + '\n' +
				"&allomr" + '\n' +
				"rcoefft = "
				+ configStrAllomrCoefft + '\n' +
				"rexpont = "
				+ configStrAllomrExpont + '\n' +
				"rinterc = "
				+ configStrAllomrRinterc + '\n' +
				"frfrac = "
				+ configStrAllomrFrfrac + '\n' +
				"/" + '\n' ;
	

		return treeFile;
		
		
	}
	
	
	
	public String getConfigStrTitle()
	{
		return configStrTitle;
	}


	public void setConfigStrTitle(String configStrTitle)
	{
		this.configStrTitle = configStrTitle;
	}


	public String getConfigStrCshape()
	{
		return configStrCshape;
	}


	public void setConfigStrCshape(String configStrCshape)
	{
		this.configStrCshape = configStrCshape;
	}


	public int getConfigStrLiaElp()
	{
		return configStrLiaElp;
	}


	public void setConfigStrLiaElp(int configStrLiaElp)
	{
		this.configStrLiaElp = configStrLiaElp;
	}


	public int getConfigStrLiaNalpha()
	{
		return configStrLiaNalpha;
	}


	public void setConfigStrLiaNalpha(int configStrLiaNalpha)
	{
		this.configStrLiaNalpha = configStrLiaNalpha;
	}


	public int getConfigStrLiaAvgang()
	{
		return configStrLiaAvgang;
	}


	public void setConfigStrLiaAvgang(int configStrLiaAvgang)
	{
		this.configStrLiaAvgang = configStrLiaAvgang;
	}


	public int getConfigStrLaddJleaf()
	{
		return configStrLaddJleaf;
	}


	public void setConfigStrLaddJleaf(int configStrLaddJleaf)
	{
		this.configStrLaddJleaf = configStrLaddJleaf;
	}


	public int getConfigStrLaddNoagec()
	{
		return configStrLaddNoagec;
	}


	public void setConfigStrLaddNoagec(int configStrLaddNoagec)
	{
		this.configStrLaddNoagec = configStrLaddNoagec;
	}


	public double getConfigStrLaddRandom()
	{
		return configStrLaddRandom;
	}


	public void setConfigStrLaddRandom(double configStrLaddRandom)
	{
		this.configStrLaddRandom = configStrLaddRandom;
	}


	public int getConfigStrAeroExtwind()
	{
		return configStrAeroExtwind;
	}


	public void setConfigStrAeroExtwind(int configStrAeroExtwind)
	{
		this.configStrAeroExtwind = configStrAeroExtwind;
	}


	public double getConfigStrAllomCoefft()
	{
		return configStrAllomCoefft;
	}


	public void setConfigStrAllomCoefft(double configStrAllomCoefft)
	{
		this.configStrAllomCoefft = configStrAllomCoefft;
	}


	public double getConfigStrAllomExpont()
	{
		return configStrAllomExpont;
	}


	public void setConfigStrAllomExpont(double configStrAllomExpont)
	{
		this.configStrAllomExpont = configStrAllomExpont;
	}


	public double getConfigStrAllomWinterc()
	{
		return configStrAllomWinterc;
	}


	public void setConfigStrAllomWinterc(double configStrAllomWinterc)
	{
		this.configStrAllomWinterc = configStrAllomWinterc;
	}


	public double getConfigStrAllombCoefft()
	{
		return configStrAllombCoefft;
	}


	public void setConfigStrAllombCoefft(double configStrAllombCoefft)
	{
		this.configStrAllombCoefft = configStrAllombCoefft;
	}


	public double getConfigStrAllombExpont()
	{
		return configStrAllombExpont;
	}


	public void setConfigStrAllombExpont(double configStrAllombExpont)
	{
		this.configStrAllombExpont = configStrAllombExpont;
	}


	public double getConfigStrAllombWinterc()
	{
		return configStrAllombWinterc;
	}


	public void setConfigStrAllombWinterc(double configStrAllombWinterc)
	{
		this.configStrAllombWinterc = configStrAllombWinterc;
	}


	public int getConfigStrAllomrCoefft()
	{
		return configStrAllomrCoefft;
	}


	public void setConfigStrAllomrCoefft(int configStrAllomrCoefft)
	{
		this.configStrAllomrCoefft = configStrAllomrCoefft;
	}


	public double getConfigStrAllomrExpont()
	{
		return configStrAllomrExpont;
	}


	public void setConfigStrAllomrExpont(double configStrAllomrExpont)
	{
		this.configStrAllomrExpont = configStrAllomrExpont;
	}


	public double getConfigStrAllomrRinterc()
	{
		return configStrAllomrRinterc;
	}


	public void setConfigStrAllomrRinterc(double configStrAllomrRinterc)
	{
		this.configStrAllomrRinterc = configStrAllomrRinterc;
	}


	public double getConfigStrAllomrFrfrac()
	{
		return configStrAllomrFrfrac;
	}


	public void setConfigStrAllomrFrfrac(double configStrAllomrFrfrac)
	{
		this.configStrAllomrFrfrac = configStrAllomrFrfrac;
	}


	public int getConfigIopoints()
	{
		return configIopoints;
	}


	public void setConfigIopoints(int configIopoints)
	{
		this.configIopoints = configIopoints;
	}



	
	

	
	private String configPhyTitle = "Physiology file for Olea europaea (Olive trees) at Smith Street " + '\n' +'\n' +
			"number of age classes of foliage. Must be same as in str.dat file for NOAGEC " + '\n' +				
			"&noages " + '\n';
	private int configPhyNoagep = 1;
	private double configPhyPhenol = 1.0;
	private int configPhyAbsorpNolayers = 1;	
	private String configPhyRhosol = "0.10 0.30 0.05";
	private String configPhyAtau = "0.093		0.34	0.01 ";
	private String configPhyArho = "0.082		0.49	0.05 ";
	private String configPhyRhosolComment = " ";
	private String configPhyAtauComment = " ";
	private String configPhyArhoComment = "";
	private int configPhyBbgsconNodates = 1;
	private String configPhycondunits = "H2O";
	private double configPhyTuzetg0 = 0.045;
	private double configPhyTuzetg1 = 4.53;
	private double configPhyTuzetsf = 3.2;
	private double configPhyTuzetPsiv = -1.9;
	private int configPhyTuzetNsides = 1;
	private double configPhyTuzetWleaf = 0.0102;
	private int configPhyTuzetGamma = 46;
	private double configPhyMedlyng0 = 0.0213;
	private double configPhyMedlyng1 = 3.018;
	private int configPhyMedlynNsides = 1;
	private String configPhyMedlynNsidesComment = "";
	private double configPhyMedlynWleaf = 0.0102;
	private int configPhyMedlynGamma = 46;
	private int configPhyJmaxconNolayers = 1;
	private int configPhyJmaxconNoages = 1;
	private int configPhyJmaxconNodates = 1;
	private double configPhyJmaxValues = 135.5;
	private String configPhyJmaxDates = "01/03/12";
	private int configPhyVcmaxconNolayers = 1;
	private int configPhyVcmaxconNoages = 1;
	private int configPhyVcmaxconNodates = 1;
	private double configPhyVcmaxValues = 82.7;
	private String configPhyVcmaxDates = "01/03/12";
	private double configPhyJmaxparsTheta = 0.9;
	private int configPhyJmaxparsEavj = 35350;
	private int configPhyJmaxparsEdvj = 200000;
	private double configPhyJmaxparsDelsj = 644.4338;
	private double configPhyJmaxparsAjq = 0.2;
	private int configPhyVcmaxparsEavc = 73680;
	private int configPhyVcmaxparsEdvc = 0;
	private int configPhyRdmaxconNolayers = 1;
	private int configPhyRdmaxconNoages = 1;
	private int configPhyRdmaxconNodates = 1;
	private double configPhyRdmaxValues = 1.12;
	private String configPhyRdmaxDates = "01/03/12";
	private double configPhyRdparsRtemp = 25.0;
	private double configPhyRdparsQ10f = 0.0575;
	private double configPhyRdparsDayresp = 0.6;
	private double configPhyRdparsEffyrf = 0.4;
	private int configPhySlamaxconNolayers = 1;
	private int configPhySlamaxconNoages = 1;
	private int configPhySlamaxconNodates = 1;
	private double configPhySlamaxValues = 5.1;
	private String configPhySlamaxDates = "01/03/12";
	
	
	
	public String getPhyDataFile(int configType)
	{
		String file = "";
		
		if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			file = "Physiology file for Lophostemon Confertus " + '\n' +
					"" + '\n' +
					"Number of age classes of foliage. Must be same as in str.dat file for NOAGEC" + '\n' +
					"" + '\n' +
					"&noages" + '\n' +
					"noagep = 1" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"proportion of leaf area in each age class (only read if NOAGEP>1 or NOAGEC>1). Must sum to 1." + '\n' +
					"" + '\n' +
					"&phenol" + '\n' +
					"prop = 1.0" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"reflectance and transmittance " + '\n' +
					"" + '\n' +
					"&absorp" + '\n' +
					"nolayers = 1				!No of crown layers for relectance and transmittance" + '\n' +
					"rhosol = 0.10	0.05	0.05		!(Soil Type=Asphalt) Soil reflectance in three wavebands (PAR, NIR, thermal) (AKA: albedo, ?, 1-?) (observed, Levinson et al.2007, OKe, 1987)" + '\n' +
					"atau = 0.093	0.34	0.01		!Leaf transmittance 3 wavelengths (PAR, NIR, thermal) (Olive: Baldini et al 1997)" + '\n' +
					"arho = 0.04	0.35	0.05		!Leaf reflectance 3 wavelengths (Lophostemon: PAR and NIR from Autumn, medium level leaf density: Fung-yan 1999)" + '\n' +
					"/	" + '\n' +
					"" + '\n' +
					"Stomatal conductance section: Depends on what MODELGS is selected in Confile.dat. For MODELGS=4, use BBMGS; For MODOLGS=6, us BBTUZ" + '\n' +
					"Controls units for stomtal conductance parameters" + '\n' +
					"" + '\n' +
					"&bbgscon" + '\n' +
					"nodates = 1" + '\n' +
					"condunits = 'H2O'	!Should be H2O for BBMGS, CO2 for BBTUZ" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"" + '\n' +
					"Medlyn model" + '\n' +
					"&bbmgs" + '\n' +
					"g0 = "
					+ "0.01"
					+ "		"
					+ "!residual conductance/minimum stomatal conductance (mmol.m-1.s-1) (Determined from Cemetery Tree)" + '\n' +
					"g1 = "
					+ "3.33"
					+ "		"
					+ "!Slope parameter/coefficient (kPA^0.5) (Determined from Cemetery Tree) (g1 must be for H2O of stomatal conductance)" + '\n' +
					"nsides = "
					+ "1"
					+ "		"
					+ "!no. of sides of the leaf with Stomata (Beardsell and Consodine)" + '\n' +
					"wleaf = "
					+ "0.05"
					+ "		"
					+ "!width of leaf (metres)" + '\n' +
					"gamma = "
					+ "53.06"
					+ "		"
					+ "!CO2 compensation point (CO2 curves)" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&jmaxcon			" + '\n' +
					"nolayers = "
					+ "1"
					+ "		"
					+ "!Number of layers for Jmax values" + '\n' +
					"noages = "
					+ "1"
					+ "		"
					+ "!Number of ages for which leaf N is specified" + '\n' +
					"nodates = "
					+ "1"
					+ "		"
					+ "!Number of dates for Jmax values" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&jmax" + '\n' +
					"values = "
					+ "105.76"
					+ "		"
					+ "!Value for Jmax (maximum rate of electron transport) (umol.m-2.s-1) (CO2 Curves)" + '\n' +
					"dates = '"
					+ "01/03/15"
					+ "'	"
					+ "!Date for which Jmax is specified" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&vcmaxcon" + '\n' +
					"nolayers = "
					+ "1"
					+ "		"
					+ "!Number of layers for VCmax values" + '\n' +
					"noages = "
					+ "1"
					+ "		"
					+ "!Number of ages for which leaf N is specified" + '\n' +
					"nodates = "
					+ "1"
					+ "		"
					+ "!Number of dates for VCmax values" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&vcmax" + '\n' +
					"values = "
					+ "81.6"
					+ "		"
					+ "!Value for VCmax (maximal rate of rubisco activity at 25 degrees) (umol.m-2.s-1) (CO2 curves)" + '\n' +
					"dates = '"
					+ "01/03/15"
					+ "'	"
					+ "!Date for which Jmax is specified" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&jmaxpars" + '\n' +
					"theta ="
					+ "0.61"
					+ "		"
					+ "!Curvature of the light response curve of electron transport (PAR curves)" + '\n' +
					"eavj = "
					+ "35350"
					+ "		"
					+ "!Activation energy of Jmax (KJ.mol-1) (also know as Hj) (Bernacchi et al 2001)" + '\n' +
					"edvj = "
					+ "200000"
					+ "		"
					+ "!Deactivation energy of Jmax (J.mol-1) (Medlyn et al 2005)" + '\n' +
					"delsj = "
					+ "644.4338"
					+ "	"
					+ "!XX Entropy term (KJ.mol-1)" + '\n' +
					"ajq = "
					+ "0.06"
					+ "		"
					+ "!Quantam yield of electron transport (mol.mol-1) (PAR curves)" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&vcmaxpars" + '\n' +
					"eavc = "
					+ "73680"
					+ "		"
					+ "!Activation energy of Vc (J.mol-1) (Also known as Hvc) (Bernacchi et al 2001)" + '\n' +
					"edvc = "
					+ "0"
					+ "		"
					+ "!Deavtivtion energy of VC (J.mol-1)" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"Foliar dark respiration	" + '\n' +
					"&rdcon" + '\n' +
					"nolayers = "
					+ "1"
					+ "		"
					+ "!Number of layers for which Rd is specified" + '\n' +
					"noages = "
					+ "1"
					+ "		"
					+ "!Number of ages for which Rd is specified" + '\n' +
					"nodates = "
					+ "1"
					+ "		"
					+ "!Number of dates for which Rd is specified" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&rd" + '\n' +
					"values = "
					+ "1.29"
					+ "		"
					+ "!Dark respiration (umol.m-2.s-1) (PAR Curves)" + '\n' +
					"dates = '"
					+ "01/03/15"
					+ "' 	"
					+ "!Date for which Rd is specified" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&rdpars" + '\n' +
					"rtemp = "
					+ "25.0"
					+ "		"
					+ "!Temperautre for which Rd is specified (degrees C)" + '\n' +
					"q10f = "
					+ "0.0575"
					+ "		"
					+ "!XX Foliage temperature response exponenet (dimensionless)" + '\n' +
					"dayresp = "
					+ "0.6"
					+ "		"
					+ "!XX Fraction by which drak respiration is inhibited by light " + '\n' +
					"effyrf = "
					+ "0.4"
					+ "		"
					+ "!XX Coefficient of Growth Respiration for woody biomass (g.g-1)" + '\n' +
					"/ " + '\n' +
					"" + '\n' +
					"&slacon" + '\n' +
					"noages = "
					+ "1" + '\n' +
					"nodates = "
					+ "1" + '\n' +
					"nolayers = "
					+ "1" + '\n' +
					"/" + '\n' +
					"" + '\n' +
					"&sla" + '\n' +
					"values = "
					+ "25.3"
					+ "		"
					+ "!Specific leaf area (25.3=Wright and Westoby 2000)" + '\n' +
					"dates = '"
					+ "01/03/12"
					+ "'" + '\n' +
					"/" + '\n' +
					"" + '\n';
		}
		
		return file;
	}
	
	private double configPhybbmgsg0;
	private double configPhybbmgsg1;
	private double configPhybbmgsnsides;
	private double configPhybbmgswleaf;
	private double configPhybbmgsgamma;
	
	private int configPhyjmaxconnolayers;
	private int configPhyjmaxconnoages;
	private int configPhyjmaxconnodates;
	
	private double configPhyjmaxvalues;
	private String configPhyjmaxdates;
	
	private int configPhyvcmaxconnolayers;
	private int configPhyvcmaxconnoages;
	private int configPhyvcmaxconnodates;
	
	private double configPhyvcmaxvalues;
	private String configPhyvcmaxdates;
	
	private double configPhyjmaxparstheta;
	private double configPhyjmaxparseavj;
	private double configPhyjmaxparsedvj;
	private double configPhyjmaxparsdelsj;
	private double configPhyjmaxparsajq;
	
	private double configPhyvcmaxparseavc;
	private double configPhyvcmaxparsedvc;
	private int configPhyrdconnolayers;
	private int configPhyrdconnoages;
	private int configPhyrdconnodates;
	private double configPhyrdvalues;
	private String configPhyrddates;
	
	private double configPhyrdparsrtemp;
	private double configPhyrdparsq10f;
	private double configPhyrdparsdayresp;
	private double configPhyrdparseffyrf;
	
	private int configPhyslaconnoages;
	private int configPhyslaconnodates;
	private int configPhyslaconnolayers;
	
	private double configPhyslavalues;
	private String configPhysladates;
	
	private String configPhySlamaxValuesComment = "!Specific leaf area (3.65=Villalobos et al 1995; 12.4=Antunez et al 2001; 5.1=Mariscal et al 2000" ;
	private String configPhyRdmaxValuesComment = "(Sierra 2012: MSc) (1.79 @ Smith St.)"  ;
	private String configPhyMedlyng0Comment = "(From Smith St. data) (g0 must be for H2O of stomatal conductance)"  ;
	private String configPhyMedlyng1Comment =  "(From Smith St. data) (g1 must be for H2O of stomatal conductance)" ;
	private String configPhyMedlynWleafComment = "";
    private String configPhyMedlynGammaComment = "(Sierra 2012: MSc) (56 @ Smith St.)" ;
    private String configPhyJmaxValuesComment = "(135.5 @ Sierra 2012: MSc) (134 @ Smith St.)"; 
	private String configPhyVcmaxValuesComment = "(82.7 @ Sierra 2012: MSc) (94 @ Smith St.)"; 
    private String configPhyJmaxparsEavjComment = "(Diaz-Espejo et al 2006)" ;
    private String configPhyJmaxparsEdvjComment = "(Medlyn et al 2005)" ;
    private String configPhyVcmaxparsEavcComment = "(Diaz-Espejo et al 2006)" ;
    private String configPhyJmaxparsThetaComment = "(Sierra 2012: MSc)" ;
    private String configPhyJmaxparsAjqComment = "";

	public String getPhyDataFile()
	{
		String file = 
				configPhyTitle +
				"noagep = "
				+ configPhyNoagep + '\n' +
				"/ " + '\n' +
				" " + '\n' +
				"proportion of leaf area in each age class (only read if NOAGEP>1 or NOAGEC>1). Must sum to 1." + '\n' +
				"&phenol " + '\n' +
				"prop = "
				+ configPhyPhenol + '\n' +
				"/ " + '\n' +
				" " + '\n' +
				"reflectance and transmittance" + '\n' +
				"&absorp " + '\n' +
				"nolayers = "
				+ configPhyAbsorpNolayers + "    !No of crown layers for relectance and transmittance" + '\n' +
				"rhosol = "
				+ configPhyRhosol + "    "
						+ "!(Soil Type=Asphalt) Soil reflectance in three wavebands (PAR, NIR, thermal) (AKA: albedo, ?, 1-?) "
						//+ "(observed, Levinson et al.2007, OKe, 1987)" 
						+ configPhyRhosolComment
				+ '\n' +
				"atau = "
				+ configPhyAtau + "   "
						+ "!Leaf transmittance 3 wavelengths (PAR, NIR, thermal) "
						//+ "(Olive: Baldini et al 1997)" 
						+ configPhyAtauComment
				+ '\n' +
				"arho = "
				+ configPhyArho + "   "
						+ "!Leaf reflectance 3 wavelengths "
						//+ "(Olive: Baldini et al 1997)" 
						+ configPhyAtauComment
				+ '\n' +
				"/	 " + '\n' +
				" " + '\n' +
				"Stomatal conductance section: Depends on what MODELGS is selected in Confile.dat. For MODELGS=4, use BBMGS; For MODOLGS=6, us BBTUZ"
			    + '\n' + "Controls units for stomtal conductance parameters" + '\n' +
				"&bbgscon " + '\n' +
				"nodates = "
				+ configPhyBbgsconNodates + '\n' +
				"condunits = '"
				+ configPhycondunits 
				+ "' " + "   !Should be H2O for BBMGS, CO2 for BBTUZ" + '\n' +
				"/ " + '\n' +
				" " + '\n' +
				" " + '\n' +

				"" + '\n' +
				"Medlyn model" + '\n' +
				"&bbmgs" + '\n' +
				"g0 = "
				+ configPhyMedlyng0
				+ "		"
				+ "!residual conductance/minimum stomatal conductance (mmol.m-1.s-1) "
				+ configPhyMedlyng0Comment
				+ '\n' +
				"g1 = "
				+ configPhyMedlyng1
				+ "		"
				+ "!Slope parameter/coefficient (kPA^0.5) "
				+ configPhyMedlyng1Comment
				+ '\n' +
				"nsides = "
				+ configPhyMedlynNsides
				+ "		!no. of sides of the leaf with Stomata " + configPhyMedlynNsidesComment + '\n' +
				"wleaf = "
				+ configPhyMedlynWleaf
				+ "		!width of leaf (metres) "
				+ configPhyMedlynWleafComment + '\n' +
				"gamma = "
				+ configPhyMedlynGamma
				+ "		"
				+ "!CO2 compensation point "
				+ configPhyMedlynGammaComment 
				+ '\n' +
				"/" + '\n' +
				"" + '\n' +
				"&jmaxcon	" + '\n' +		
				"nolayers = "
				+ configPhyJmaxconNolayers
				+ "		!Number of layers for Jmax values" + '\n' +
				"noages = "
				+ configPhyJmaxconNoages
				+ "		!Number of ages for which leaf N is specified" + '\n' +
				"nodates = "
				+ configPhyJmaxconNodates
				+ "		!Number of dates for Jmax values" + '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&jmax" + '\n' +
				"values = "
				+ configPhyJmaxValues
				+ "		"
				+ "!Value for Jmax (maximum rate of electron transport) (umol.m-2.s-1) "
				+ configPhyJmaxValuesComment
				+ '\n' +
				"dates = '"
				+ configPhyJmaxDates
				+ "'	!Date for which Jmax is specified" + '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&vcmaxcon" + '\n' +
				"nolayers = "
				+ configPhyVcmaxconNolayers
				+ "		!Number of layers for VCmax values" + '\n' +
				"noages = "
				+ configPhyVcmaxconNoages
				+ "		!Number of ages for which leaf N is specified" + '\n' +
				"nodates = "
				+ configPhyVcmaxconNodates
				+ "		!Number of dates for VCmax values" + '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&vcmax" + '\n' +
				"values = "
				+ configPhyVcmaxValues
				+ "		"
				+ "!Value for VCmax (maximal rate of rubisco activity at 25 degrees) (umol.m-2.s-1) "
				+ configPhyVcmaxValuesComment
				+ '\n' +
				"dates = '"
				+ configPhyVcmaxDates
				+ "'	!Date for which Jmax is specified" + '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&jmaxpars" + '\n' +
				"theta ="
				+ configPhyJmaxparsTheta
				+ "		"
				+ "!Curvature of the light response curve of electron transport "
				+ configPhyJmaxparsThetaComment 
				+ '\n' +
				"eavj = "
				+ configPhyJmaxparsEavj
				+ "		"
				+ "!Activation energy of Jmax (KJ.mol-1) (also know as Hj) "
				+ configPhyJmaxparsEavjComment 
				+ '\n' +
				"edvj = "
				+ configPhyJmaxparsEdvj
				+ "		"
				+ "!Deactivation energy of Jmax (J.mol-1) "
				+ configPhyJmaxparsEdvjComment 
				+ '\n' +
				"delsj = "
				+ configPhyJmaxparsDelsj
				+ "	!XX Entropy term (KJ.mol-1)" + '\n' +
				"ajq = "
				+ configPhyJmaxparsAjq
				+ "		"
				+ "!Quantam yield of electron transport (default: 0.425) (mol.mol-1)" 
				+ configPhyJmaxparsAjqComment
				+ '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&vcmaxpars" + '\n' +
				"eavc = "
				+ configPhyVcmaxparsEavc
				+ "		"
				+ "!Activation energy of Vc (J.mol-1) (Also known as Hvc) "
				+ configPhyVcmaxparsEavcComment 
				+ '\n' +
				"edvc = "
				+ configPhyVcmaxparsEdvc
				+ "		!Deavtivtion energy of VC (J.mol-1)" + '\n' +
				"/" + '\n' +
				"" + '\n' +
				"Foliar dark respiration	" + '\n' +
				"&rdcon" + '\n' +
				"nolayers = "
				+ configPhyRdmaxconNolayers
				+ "		!Number of layers for which Rd is specified" + '\n' +
				"noages = "
				+ configPhyRdmaxconNoages
				+ "		!Number of ages for which Rd is specified" + '\n' +
				"nodates = "
				+ configPhyRdmaxconNodates
				+ "		!Number of dates for which Rd is specified" + '\n' +
				"/" + '\n' +
				"" + '\n' +
				"&rd" + '\n' +
				"values = "
				+ configPhyRdmaxValues
				+ "		"
				+ "!Dark respiration (umol.m-2.s-1) "
				+ configPhyRdmaxValuesComment
				+ '\n' +
				"dates = '"
				+ configPhyRdmaxDates
				+ "' 	!Date for which Rd is specified" + '\n' +
				"/" + '\n' +
				"" + '\n' +
				"&rdpars" + '\n' +
				"rtemp = "
				+ configPhyRdparsRtemp
				+ "		!Temperautre for which Rd is specified (degrees C)" + '\n' +
				"q10f = "
				+ configPhyRdparsQ10f
				+ "		!XX Foliage temperature response exponenet (dimensionless)" + '\n' +
				"dayresp = "
				+ configPhyRdparsDayresp
				+ "		!XX Fraction by which drak respiration is inhibited by light " + '\n' +
				"effyrf = "
				+ configPhyRdparsEffyrf
				+ "		!XX Coefficient of Growth Respiration for woody biomass (g.g-1)" + '\n' +
				"/ " + '\n' +
				"" + '\n' +
				"&slacon" + '\n' +
				"noages = "
				+ configPhySlamaxconNoages + '\n' +
				"nodates = "
				+ configPhySlamaxconNodates + '\n' +
				"nolayers = "
				+ configPhySlamaxconNolayers + '\n' +
				"/" + '\n' +
				"	" + '\n' +
				"&sla" + '\n' +
				"values = "
				+ configPhySlamaxValues
				+ "		"
				+ configPhySlamaxValuesComment
				+ '\n' +
				"dates = '"
				+ configPhySlamaxDates
				+ "'" + '\n' +
				"/" + '\n' ;

				
				return file;
	}
	
	public String getConfigPhyJmaxparsThetaComment()
	{
		return configPhyJmaxparsThetaComment;
	}


	public void setConfigPhyJmaxparsThetaComment(String configPhyJmaxparsThetaComment)
	{
		this.configPhyJmaxparsThetaComment = configPhyJmaxparsThetaComment;
	}



	
	
	public String getConfigPhyTitle()
	{
		return configPhyTitle;
	}


	public void setConfigPhyTitle(String configPhyTitle)
	{
		this.configPhyTitle = configPhyTitle;
	}


	public int getConfigPhyNoagep()
	{
		return configPhyNoagep;
	}


	public void setConfigPhyNoagep(int configPhyNoagep)
	{
		this.configPhyNoagep = configPhyNoagep;
	}


	public double getConfigPhyPhenol()
	{
		return configPhyPhenol;
	}


	public void setConfigPhyPhenol(double configPhyPhenol)
	{
		this.configPhyPhenol = configPhyPhenol;
	}


	public int getConfigPhyAbsorpNolayers()
	{
		return configPhyAbsorpNolayers;
	}


	public void setConfigPhyAbsorpNolayers(int configPhyAbsorpNolayers)
	{
		this.configPhyAbsorpNolayers = configPhyAbsorpNolayers;
	}


	public String getConfigPhyRhosol()
	{
		return configPhyRhosol;
	}


	public void setConfigPhyRhosol(String configPhyRhosol)
	{
		this.configPhyRhosol = configPhyRhosol;
	}
	public String getConfigPhyRhosolComment()
	{
		return configPhyRhosolComment;
	}


	public void setConfigPhyRhosolComment(String configPhyRhosolComment)
	{
		this.configPhyRhosolComment = configPhyRhosolComment;
	}

	public String getConfigPhyAtau()
	{
		return configPhyAtau;
	}


	public void setConfigPhyAtau(String configPhyAtau)
	{
		this.configPhyAtau = configPhyAtau;
	}
	public String getConfigPhyAtauComment()
	{
		return configPhyAtauComment;
	}


	public void setConfigPhyAtauComment(String configPhyAtauComment)
	{
		this.configPhyAtauComment = configPhyAtauComment;
	}

	public String getConfigPhyArho()
	{
		return configPhyArho;
	}


	public void setConfigPhyArho(String configPhyArho)
	{
		this.configPhyArho = configPhyArho;
	}
	public String getConfigPhyArhoComment()
	{
		return configPhyArhoComment;
	}


	public void setConfigPhyArhoComment(String configPhyArhoComment)
	{
		this.configPhyArhoComment = configPhyArhoComment;
	}

	public int getConfigPhyBbgsconNodates()
	{
		return configPhyBbgsconNodates;
	}


	public void setConfigPhyBbgsconNodates(int configPhyBbgsconNodates)
	{
		this.configPhyBbgsconNodates = configPhyBbgsconNodates;
	}


	public String getConfigPhycondunits()
	{
		return configPhycondunits;
	}


	public void setConfigPhycondunits(String configPhycondunits)
	{
		this.configPhycondunits = configPhycondunits;
	}


	public double getConfigPhyTuzetg0()
	{
		return configPhyTuzetg0;
	}


	public void setConfigPhyTuzetg0(double configPhyTuzetg0)
	{
		this.configPhyTuzetg0 = configPhyTuzetg0;
	}


	public double getConfigPhyTuzetg1()
	{
		return configPhyTuzetg1;
	}


	public void setConfigPhyTuzetg1(double configPhyTuzetg1)
	{
		this.configPhyTuzetg1 = configPhyTuzetg1;
	}


	public double getConfigPhyTuzetsf()
	{
		return configPhyTuzetsf;
	}


	public void setConfigPhyTuzetsf(double configPhyTuzetsf)
	{
		this.configPhyTuzetsf = configPhyTuzetsf;
	}


	public double getConfigPhyTuzetPsiv()
	{
		return configPhyTuzetPsiv;
	}


	public void setConfigPhyTuzetPsiv(double configPhyTuzetPsiv)
	{
		this.configPhyTuzetPsiv = configPhyTuzetPsiv;
	}


	public int getConfigPhyTuzetNsides()
	{
		return configPhyTuzetNsides;
	}


	public void setConfigPhyTuzetNsides(int configPhyTuzetNsides)
	{
		this.configPhyTuzetNsides = configPhyTuzetNsides;
	}


	public double getConfigPhyTuzetWleaf()
	{
		return configPhyTuzetWleaf;
	}


	public void setConfigPhyTuzetWleaf(double configPhyTuzetWleaf)
	{
		this.configPhyTuzetWleaf = configPhyTuzetWleaf;
	}


	public int getConfigPhyTuzetGamma()
	{
		return configPhyTuzetGamma;
	}


	public void setConfigPhyTuzetGamma(int configPhyTuzetGamma)
	{
		this.configPhyTuzetGamma = configPhyTuzetGamma;
	}


	public double getConfigPhyMedlyng0()
	{
		return configPhyMedlyng0;
	}


	public void setConfigPhyMedlyng0(double configPhyMedlyng0)
	{
		this.configPhyMedlyng0 = configPhyMedlyng0;
	}


	public double getConfigPhyMedlyng1()
	{
		return configPhyMedlyng1;
	}


	public void setConfigPhyMedlyng1(double configPhyMedlyng1)
	{
		this.configPhyMedlyng1 = configPhyMedlyng1;
	}


	public int getConfigPhyMedlynNsides()
	{
		return configPhyMedlynNsides;
	}


	public void setConfigPhyMedlynNsides(int configPhyMedlynNsides)
	{
		this.configPhyMedlynNsides = configPhyMedlynNsides;
	}
	
	public String getConfigPhyMedlynNsidesComment()
	{
		return configPhyMedlynNsidesComment;
	}


	public void setConfigPhyMedlynNsidesComment(String configPhyMedlynNsidesComment)
	{
		this.configPhyMedlynNsidesComment = configPhyMedlynNsidesComment;
	}


	public double getConfigPhyMedlynWleaf()
	{
		return configPhyMedlynWleaf;
	}


	public void setConfigPhyMedlynWleaf(double configPhyMedlynWleaf)
	{
		this.configPhyMedlynWleaf = configPhyMedlynWleaf;
	}


	public int getConfigPhyMedlynGamma()
	{
		return configPhyMedlynGamma;
	}


	public void setConfigPhyMedlynGamma(int configPhyMedlynGamma)
	{
		this.configPhyMedlynGamma = configPhyMedlynGamma;
	}


	public int getConfigPhyJmaxconNolayers()
	{
		return configPhyJmaxconNolayers;
	}


	public void setConfigPhyJmaxconNolayers(int configPhyJmaxconNolayers)
	{
		this.configPhyJmaxconNolayers = configPhyJmaxconNolayers;
	}


	public int getConfigPhyJmaxconNoages()
	{
		return configPhyJmaxconNoages;
	}


	public void setConfigPhyJmaxconNoages(int configPhyJmaxconNoages)
	{
		this.configPhyJmaxconNoages = configPhyJmaxconNoages;
	}


	public int getConfigPhyJmaxconNodates()
	{
		return configPhyJmaxconNodates;
	}


	public void setConfigPhyJmaxconNodates(int configPhyJmaxconNodates)
	{
		this.configPhyJmaxconNodates = configPhyJmaxconNodates;
	}


	public double getConfigPhyJmaxValues()
	{
		return configPhyJmaxValues;
	}


	public void setConfigPhyJmaxValues(double configPhyJmaxValues)
	{
		this.configPhyJmaxValues = configPhyJmaxValues;
	}


	public String getConfigPhyJmaxDates()
	{
		return configPhyJmaxDates;
	}


	public void setConfigPhyJmaxDates(String configPhyJmaxDates)
	{
		this.configPhyJmaxDates = configPhyJmaxDates;
	}


	public int getConfigPhyVcmaxconNolayers()
	{
		return configPhyVcmaxconNolayers;
	}


	public void setConfigPhyVcmaxconNolayers(int configPhyVcmaxconNolayers)
	{
		this.configPhyVcmaxconNolayers = configPhyVcmaxconNolayers;
	}


	public int getConfigPhyVcmaxconNoages()
	{
		return configPhyVcmaxconNoages;
	}


	public void setConfigPhyVcmaxconNoages(int configPhyVcmaxconNoages)
	{
		this.configPhyVcmaxconNoages = configPhyVcmaxconNoages;
	}


	public int getConfigPhyVcmaxconNodates()
	{
		return configPhyVcmaxconNodates;
	}


	public void setConfigPhyVcmaxconNodates(int configPhyVcmaxconNodates)
	{
		this.configPhyVcmaxconNodates = configPhyVcmaxconNodates;
	}


	public double getConfigPhyVcmaxValues()
	{
		return configPhyVcmaxValues;
	}


	public void setConfigPhyVcmaxValues(double configPhyVcmaxValues)
	{
		this.configPhyVcmaxValues = configPhyVcmaxValues;
	}


	public String getConfigPhyVcmaxDates()
	{
		return configPhyVcmaxDates;
	}


	public void setConfigPhyVcmaxDates(String configPhyVcmaxDates)
	{
		this.configPhyVcmaxDates = configPhyVcmaxDates;
	}


	public double getConfigPhyJmaxparsTheta()
	{
		return configPhyJmaxparsTheta;
	}


	public void setConfigPhyJmaxparsTheta(double configPhyJmaxparsTheta)
	{
		this.configPhyJmaxparsTheta = configPhyJmaxparsTheta;
	}


	public int getConfigPhyJmaxparsEavj()
	{
		return configPhyJmaxparsEavj;
	}


	public void setConfigPhyJmaxparsEavj(int configPhyJmaxparsEavj)
	{
		this.configPhyJmaxparsEavj = configPhyJmaxparsEavj;
	}


	public int getConfigPhyJmaxparsEdvj()
	{
		return configPhyJmaxparsEdvj;
	}


	public void setConfigPhyJmaxparsEdvj(int configPhyJmaxparsEdvj)
	{
		this.configPhyJmaxparsEdvj = configPhyJmaxparsEdvj;
	}


	public double getConfigPhyJmaxparsDelsj()
	{
		return configPhyJmaxparsDelsj;
	}


	public void setConfigPhyJmaxparsDelsj(double configPhyJmaxparsDelsj)
	{
		this.configPhyJmaxparsDelsj = configPhyJmaxparsDelsj;
	}


	public double getConfigPhyJmaxparsAjq()
	{
		return configPhyJmaxparsAjq;
	}


	public void setConfigPhyJmaxparsAjq(double configPhyJmaxparsAjq)
	{
		this.configPhyJmaxparsAjq = configPhyJmaxparsAjq;
	}


	public int getConfigPhyVcmaxparsEavc()
	{
		return configPhyVcmaxparsEavc;
	}


	public void setConfigPhyVcmaxparsEavc(int configPhyVcmaxparsEavc)
	{
		this.configPhyVcmaxparsEavc = configPhyVcmaxparsEavc;
	}


	public int getConfigPhyVcmaxparsEdvc()
	{
		return configPhyVcmaxparsEdvc;
	}


	public void setConfigPhyVcmaxparsEdvc(int configPhyVcmaxparsEdvc)
	{
		this.configPhyVcmaxparsEdvc = configPhyVcmaxparsEdvc;
	}


	public int getConfigPhyRdmaxconNolayers()
	{
		return configPhyRdmaxconNolayers;
	}


	public void setConfigPhyRdmaxconNolayers(int configPhyRdmaxconNolayers)
	{
		this.configPhyRdmaxconNolayers = configPhyRdmaxconNolayers;
	}


	public int getConfigPhyRdmaxconNoages()
	{
		return configPhyRdmaxconNoages;
	}


	public void setConfigPhyRdmaxconNoages(int configPhyRdmaxconNoages)
	{
		this.configPhyRdmaxconNoages = configPhyRdmaxconNoages;
	}


	public int getConfigPhyRdmaxconNodates()
	{
		return configPhyRdmaxconNodates;
	}


	public void setConfigPhyRdmaxconNodates(int configPhyRdmaxconNodates)
	{
		this.configPhyRdmaxconNodates = configPhyRdmaxconNodates;
	}


	public double getConfigPhyRdmaxValues()
	{
		return configPhyRdmaxValues;
	}


	public void setConfigPhyRdmaxValues(double configPhyRdmaxValues)
	{
		this.configPhyRdmaxValues = configPhyRdmaxValues;
	}


	public String getConfigPhyRdmaxDates()
	{
		return configPhyRdmaxDates;
	}


	public void setConfigPhyRdmaxDates(String configPhyRdmaxDates)
	{
		this.configPhyRdmaxDates = configPhyRdmaxDates;
	}


	public double getConfigPhyRdparsRtemp()
	{
		return configPhyRdparsRtemp;
	}


	public void setConfigPhyRdparsRtemp(double configPhyRdparsRtemp)
	{
		this.configPhyRdparsRtemp = configPhyRdparsRtemp;
	}


	public double getConfigPhyRdparsQ10f()
	{
		return configPhyRdparsQ10f;
	}


	public void setConfigPhyRdparsQ10f(double configPhyRdparsQ10f)
	{
		this.configPhyRdparsQ10f = configPhyRdparsQ10f;
	}


	public double getConfigPhyRdparsDayresp()
	{
		return configPhyRdparsDayresp;
	}


	public void setConfigPhyRdparsDayresp(double configPhyRdparsDayresp)
	{
		this.configPhyRdparsDayresp = configPhyRdparsDayresp;
	}


	public double getConfigPhyRdparsEffyrf()
	{
		return configPhyRdparsEffyrf;
	}


	public void setConfigPhyRdparsEffyrf(double configPhyRdparsEffyrf)
	{
		this.configPhyRdparsEffyrf = configPhyRdparsEffyrf;
	}


	public int getConfigPhySlamaxconNolayers()
	{
		return configPhySlamaxconNolayers;
	}


	public void setConfigPhySlamaxconNolayers(int configPhySlamaxconNolayers)
	{
		this.configPhySlamaxconNolayers = configPhySlamaxconNolayers;
	}


	public int getConfigPhySlamaxconNoages()
	{
		return configPhySlamaxconNoages;
	}


	public void setConfigPhySlamaxconNoages(int configPhySlamaxconNoages)
	{
		this.configPhySlamaxconNoages = configPhySlamaxconNoages;
	}


	public int getConfigPhySlamaxconNodates()
	{
		return configPhySlamaxconNodates;
	}


	public void setConfigPhySlamaxconNodates(int configPhySlamaxconNodates)
	{
		this.configPhySlamaxconNodates = configPhySlamaxconNodates;
	}


	public double getConfigPhySlamaxValues()
	{
		return configPhySlamaxValues;
	}


	public void setConfigPhySlamaxValues(double configPhySlamaxValues)
	{
		this.configPhySlamaxValues = configPhySlamaxValues;
	}


	public String getConfigPhySlamaxDates()
	{
		return configPhySlamaxDates;
	}


	public void setConfigPhySlamaxDates(String configPhySlamaxDates)
	{
		this.configPhySlamaxDates = configPhySlamaxDates;
	}



	
	public String getWatbalDataFile()
	{
		
		String file=
		"Program:    MAESPA: version February 2011    " + '\n' +                                               
		"Water balance parameters:Tumbarumba        " + '\n' +                                     
		"" + '\n' +                                   
		"Half-hourly water and heat balance components." + '\n' +
		"wsoil: total soil water storage                       mm" + '\n' +
		"wsoilroot: soil water storage in rooted zone          mm" + '\n' +
		"ppt : precipitation                                   mm" + '\n' +
		"canopystore : storage of intercepted rain             mm" + '\n' +
		"evapstore : evaporation of wet canopy                 mm" + '\n' +
		"drainstore : drainage of wet canopy                   mm" + '\n' +
		"tfall : throughfall of rain                           mm" + '\n' +
		"et : modelled canopy transpiration                    mm" + '\n' +
		"etmeas: measured ET, if provided in input             mm" + '\n' +
		"discharge: drainage at bottom of profile              mm" + '\n' +
		"overflow: over-land flow                              mm" + '\n' +
		"weightedswp: soil water potential weighted by roots  MPa" + '\n' +
		"totestevap: maximum possible soil water uptake        mm" + '\n' +
		"drythick: thickness of dry surface layer              mm" + '\n' +
		"soilevap: soil evaporation                            mm" + '\n' +
		"soilmoist: measured soil water content      (units vary)" + '\n' +
		"fsoil: soil water modifier function                (0-1)" + '\n' +
		"qh: sensible heat flux                             W m-2" + '\n' +
		"qe: latent heat flux                               W m-2" + '\n' +
		"qn: net radiation                                  W m-2" + '\n' +
		"qc: soil heat transport                            W m-2" + '\n' +
		"rglobund: net radiation underneath canopy          W m-2" + '\n' +
		"rglobabv: net radiation above canopy               W m-2" + '\n' +
		"radinterc: total radiation intercepted by canopy   W m-2" + '\n' +
		"rnet: net radiation above the canopy               W m-2" + '\n' +
		"totlai: leaf area index                           m2 m-2" + '\n' +
		"tair: air temperature                              deg C" + '\n' +
		"soilt1, soilt2: soil T in 1st and 2nd layer        deg C" + '\n' +
		"fracw1,fracw2: water content 1st and 2nd layer    m3 m-3" + '\n' +
		"" + '\n' +                       
		"Columns: day hour wsoil wsoilroot ppt canopystore         evapstore drainstore tfall et etmeas discharge overflow  weightedswp totestevap drythick soilevap                 soilmoist fsoil qh qe qn qc rglobund                     rglobabv radinterc rnet totlai tair soilt1 soilt2        fracw1 fracw2" + '\n' +
		      "0      0     2880.0002      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000      -999.0000" + '\n' +
		      "1      1     2879.9558      1379.9557         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010         0.0426         0.4000         1.0000       112.0625       -58.2401       -40.9305       -12.8922       398.8148       391.5830         0.0000        11.6599         0.0540        17.8030        16.6493       -17.9557         0.3991         0.4000" + '\n' +
		      "1      2     2879.9390      1379.9390         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010         0.0150         0.4000         1.0000       302.1708       -20.5320       -30.2027      -251.4353       396.7081       389.6601         0.0000        22.2554         0.0540        17.3380        14.2320       -40.9898         0.3988         0.4000" + '\n' +
		      "1      3     2879.9373      1379.9371         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       440.1650         0.0000       -21.8662      -418.3004       396.6170       389.8131         0.0000        30.8224         0.0540        17.0930        12.5723       -56.9977         0.3988         0.4000" + '\n' +
		      "1      4     2879.9353      1379.9353         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       547.3217         0.0000       -15.7719      -531.5510       394.9495       388.2570         0.0000        36.7781         0.0540        16.6790        11.0658       -68.2188         0.3988         0.4000" + '\n' +
		      "1      5     2879.9336      1379.9335         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       623.2533         0.0000       -13.1591      -610.0925       391.2906       384.5411         0.0000        38.7849         0.0540        16.2850         9.9017       -76.1455         0.3988         0.4000" + '\n' +
		      "1      6     2879.9319      1379.9318         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       675.3446         0.0000       -11.5170      -663.8278       387.2432       380.3527         0.0000        39.6790         0.0540        15.7600         8.8558       -81.8442         0.3988         0.4000" + '\n' +
		      "1      7     2879.9299      1379.9299         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       711.9743         0.0000       -10.1970      -701.7758       384.0450       376.9905         0.0000        40.3552         0.0540        15.2830         8.0163       -86.0060         0.3988         0.4000" + '\n' +
		      "1      8     2879.9282      1379.9281         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       738.0363         0.0000        -9.0395      -728.9981       381.6118       374.4529         0.0000        41.0434         0.0540        14.8620         7.3403       -89.1037         0.3988         0.4000" + '\n' +
		      "1      9     2879.9265      1379.9264         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       757.5448         0.0000        -8.0966      -749.4484       380.0480       372.8451         0.0000        41.7076         0.0540        14.5740         6.8612       -91.4446         0.3988         0.4000" + '\n' +
		      "1     10     2879.9246      1379.9246         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       771.9373         0.0000        -7.3348      -764.6023       378.7206       371.4753         0.0000        42.2280         0.0540        14.3130         6.4608       -93.2616         0.3988         0.4000" + '\n' +
		      "1     11     2879.9229      1379.9227         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       782.3251         0.0000        -6.7733      -775.5515       377.0875       369.8757         0.0000        42.5780         0.0540        13.9950         6.0459       -94.7287         0.3988         0.4000" + '\n' +
		      "1     12     2879.9211      1379.9210         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       791.8381         0.0000        -6.3796      -785.4569       376.4557       369.2838         0.0000        42.9168         0.0540        13.8900         5.8472       -95.9055         0.3988         0.4000" + '\n' +
		      "1     13     2879.9192      1379.9192         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       796.3486         0.0000        -2.7368      -793.6131       380.0134       372.8360         0.0000        47.0877         0.0540        13.8010         5.7149       -96.8714         0.3988         0.4000" + '\n' +
		      "1     14     2879.9175      1379.9174         0.0000         0.0000         0.0000         0.0000         0.0000         0.0024         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0010        -0.0000         0.4000         1.0000       785.1975         0.0000        17.7554      -802.9514       405.3872       410.7953         0.0000        75.4826         0.0540        13.9210         5.9448            NaN         0.3988         0.4000" + '\n' +
		      "1     15     2877.3298      1377.3297         0.0000         0.0000         0.0000         0.0000         0.0000         0.0060         0.0000         0.0018         0.0000        -0.0106         0.0000         0.0038         2.5859         0.4000         1.0000     -4915.7598     -3374.5085      -305.4946            NaN       462.2493       494.5016         0.0000      -230.7803         0.0540        14.2930        64.2930            NaN         0.3471         0.4000" + '\n' +
		      "1     16     2876.5645      1376.5645         0.0000         0.0000         0.0000         0.0000         0.0000         0.0095         0.0000         0.0018         0.0000        -0.0113         0.0000         0.0048         0.7634         0.3992         1.0000     -4906.8662      -995.7371      -254.0526            NaN       527.8555       589.1164         0.0000      -164.7648         0.0540        14.8140        64.8140            NaN         0.3318         0.4000" + '\n' +
		      "1     17     2875.9541      1375.9540         0.0000         0.0000         0.0000         0.0000         0.0000         0.0107         0.0000         0.0018         0.0000        -0.0117         0.0000         0.0057         0.6087         0.3990         1.0000     -4899.6865      -793.5943      -198.8906            NaN       596.8889       685.4928         0.0000       -94.7905         0.0540        15.2360        65.2360            NaN         0.3197         0.4000" + '\n' +
		      "1     18     2875.4285      1375.4285         0.0000         0.0000         0.0000         0.0000         0.0000         0.0112         0.0000         0.0018         0.0000        -0.0121         0.0000         0.0065         0.5237         0.3988         1.0000     -4896.0693      -682.6453      -220.7077            NaN       573.3159       652.8361         0.0000      -116.9954         0.0540        15.4490        65.4490            NaN         0.3092         0.4000" + '\n' +
		      "1     19     2874.9490      1374.9490         0.0000         0.0000         0.0000         0.0000         0.0000         0.0128         0.0000         0.0018         0.0000        -0.0125         0.0000         0.0071         0.4777         0.3987         1.0000     -4886.4863      -622.2408       -77.4844            NaN       747.3972       881.9310         0.0000        55.1195         0.0540        16.0150        66.0150            NaN         0.2996         0.4000" + '\n' +
		      "1     20     2874.5159      1374.5157         0.0000         0.0000         0.0000         0.0000         0.0000         0.0130         0.0000         0.0018         0.0000        -0.0130         0.0000         0.0077         0.4315         0.3985         1.0000     -4887.9399      -562.1116       -61.8475            NaN       764.9433       903.7397         0.0000        75.7219         0.0540        15.9290        65.9290            NaN         0.2910         0.4000" + '\n' +
		      "1     21     2874.1074      1374.1073         0.0000         0.0000         0.0000         0.0000         0.0000         0.0139         0.0000         0.0018         0.0000        -0.0135         0.0000         0.0083         0.4067         0.3984         1.0000     -4880.2085      -529.6363        32.7350            NaN       880.7523      1042.2982         0.0000       186.1929         0.0540        16.3870        66.3870            NaN         0.2829         0.4000" + '\n' +
		      "1     22     2873.7163      1373.7163         0.0000         0.0000         0.0000         0.0000         0.0000         0.0148         0.0000         0.0018         0.0000        -0.0142         0.0000         0.0089         0.3891         0.3983         1.0000     -4870.9722      -506.4622       138.3020            NaN      1010.4092      1192.8303         0.0000       309.0303         0.0540        16.9360        66.9360            NaN         0.2751         0.4000" + '\n' +
		      "1     23     2873.3428      1373.3428         0.0000         0.0000         0.0000         0.0000         0.0000         0.0157         0.0000         0.0018         0.0000        -0.0149         0.0000         0.0094         0.3718         0.3982         1.0000     -4864.9678      -483.6971       159.2321            NaN      1038.6079      1222.5580         0.0000       333.3810         0.0540        17.2940        67.2940            NaN         0.2677         0.4000" + '\n' +
		      "1     24     2872.9800      1372.9800         0.0000         0.0000         0.0000         0.0000         0.0000         0.0169         0.0000         0.0018         0.0000        -0.0158         0.0000         0.0099         0.3609         0.3981         1.0000     -4855.3389      -469.3264       208.4522            NaN      1102.2897      1302.3726         0.0000       395.9406         0.0540        17.8700        67.8700            NaN         0.2604         0.4000" + '\n' 
		      		;
		return file;
		
	}
	
	private String configWatparsTitle = "Example water balance parameters.";
	private int configWatparskeepwet = 0;
	private int configWatparssimtsoil = 1;
	private int configWatparssimsoilevap = 1;
	private int configWatparsreassignrain = 0;
	private int configWatparsretfunction = 1;
	private int configWatparsequaluptake = 0;
	private int configWatparsusemeaset = 0;
	private int configWatparsusemeassw = 0;
	private int configWatparsusestand=0;	
	private double configWatparsThroughfall = 1.0;	
	private double configWatparsrutterb = 3.7;
	private double configWatparsrutterd = 0.002;
	private double configWatparsmaxstorage = 0.4;
	private double configWatparsthroughfall = 0.6;	
	private double configWatparsexpinf = 1.0;	
	private double configWatparsrootresfrac = 0.4;
	private double configWatparsrootrad = 0.0001;
	private double configWatparsrootdens = 0.5e6;
	private int configWatparsrootmasstot = 1000;
	private int configWatparsnrootlayer = 7;
	private double configWatparsrootbeta=0.9;	
	private double configWatparsminrootwp = -3.0;
	private double configWatparsminleafwp = -10.0;
	private int configWatparsplantk = 3;	
	private double configWatparsbpar = 2.79;
	private double configWatparspsie = -0.00068;
	private double configWatparsksat = 264.3;	
	private int configWatparsnlayer = 10;
	private double configWatparslaythick = 0.1;
	private double configWatparsporefrac = 0.2;
	private double configWatparsdrainlimit = 0.0;
	private double configWatparsfracorganic = 0.0;	
	private double configWatparsinitwater = 0.06;
	private int configWatparssoiltemp = 15;	
	private double configWatparsdrythickmin = 0.01;
	private double configWatparstortpar = 0.66;
	
	public String getWatparsDataFile(int configType)
	{
		String file = "";
		
		if (configType == TUFBldVegHeights.BRUSHBOX_CONFIG_TYPE)
		{
			file = "Soil and water parameters for Lophostemon Confertus tree. Uses a LOAM soil with 3 m depth" + '\n' 
				+ "" + '\n' 
				+ "&watcontrol" + '\n' 
				+ "keepwet = 0		!Soil water stays wet if = 1 (used for testing)" + '\n' 
				+ "simtsoil = 1		!Simulate soil temperature (yes=1) (must do)" + '\n' 
				+ "simsoilevap = 1		!Simulate soil evaporation (yes=1)" + '\n' 
				+ "reassignrain = 0	!Re-assign half hourly rain if only DAILY rainfall (PPT) available (yes=1)" + '\n' 
				+ "wsoilmethod = 2		!If = 1 then use Emax method (unlimited water); if = 2 Use Vol Wat content; if = 4 use exponenetial relationship with SMD1 & SMD2;" + '\n' 
				+ "retfunction = 1		!Water retention curve (1=Campbell curve: parameters in \"soilret\")" + '\n' 
				+ "equaluptake = 0		!water uptake from soil layers (0=based on fine root density and soil water potential)" + '\n' 
				+ "usemeaset = 0		!Use canopy transpiration if = 1; need to add 'ET' to met.dat file" + '\n' 
				+ "usemeassw = 0		!Use measured soil water if = 1; " + '\n' 
				+ "usestand = 0		!If = 1, water used by single trees scaled up to stand; If=0, scaling not done - use for single tree in stand, or BY ITSELF" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Rainfall canopy interception" + '\n' 
				+ "&wattfall" + '\n' 
				+ "rutterb = 3.7		!Drainage coefficient (B parameter in Rutter et al 1975) to calculate canopy drainage (mm)" + '\n' 
				+ "rutterd = 0.002		!Drainage parameter in Rutter et al 1975 (0.002) (dimensionless)" + '\n' 
				+ "maxstorage = 0.4	!Maximum canopy storage of water" + '\n' 
				+ "throughfall = 0.6	!rainfall passing through the canopy" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "&watinfilt" + '\n' 
				+ "expinf = 0.25		!" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil evaporation" + '\n' 
				+ "&soiletpars" + '\n' 
				+ "drythickmin = 0.01	!Minimum thickness of the dry soil layer (m)" + '\n' 
				+ "tortpar = 0.66		!XX Parameter describing tortuosity of the soil: describes diffusion in porous media" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Root parameters" + '\n' 
				+ "&rootpars" + '\n' 
				+ "rootrad = 0.0001	!Average root radius (m)" + '\n' 
				+ "rootdens = 0.5e6	!Root density (g.m-3)" + '\n' 
				+ "rootmasstot = 1000	!Total root biomass (kg.m-2)" + '\n' 
				+ "nrootlayer = 10		!Number of soil layers that are rooted. Together with the LAYTHICK parameter, it determines the rooting depth" + '\n' 
				+ "rootbeta = 0.9		!Beta parameter characterising root distribution (Jackson et al 1996)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Plant parameters" + '\n' 
				+ "&plantpars " + '\n' 
				+ "minrootwp = -3		!Minimum root water potential (MPa) (Default -3)" + '\n' 
				+ "minleafwp = -4		!Minimum leaf water potential (MPa) (not needed if MODELGS=6: Tuzet model) (Stewart and Sands 1996)" + '\n' 
				+ "plantk = 2.7		!leaf specific (total) plant hydraulic conductance (IMPORTANT!!!) (mmol.m-2.s-1.MPa-1) (3=Default) (Taken from reltionship in Hubbard et al 2001 Figure 5 for A=15.3)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil water retention and conductivity (LOAM)" + '\n' 
				+ "&soilret" + '\n' 
				+ "bpar = 5.25		!Empiral coefficient related to clay content of the soil (LOAM) (Duusma et al. 2008)" + '\n' 
				+ "psie = -0.00348		!air entry water potential (MPa) (LOAM) (Duursma et al. 2008)" + '\n' 
				+ "ksat = 19.1		!saturated soil hydaulic conductivity (LOAM) (Duursma et al. 2008) (mol.m-1.s-1.MPa-1)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil layer parameters" + '\n' 
				+ "&laypars" + '\n' 
				+ "nlayer = 10		!number of soil layers in the model" + '\n' 
				+ "laythick = 0.3		!Layer thickness (m)" + '\n' 
				+ "porefrac = 0.43		!Soil porosity (m3.m-3)" + '\n' 
				+ "Drainlimit = 0		!fraction of the pore fraction below which no drainage occurs (fraction 0-1)" + '\n' 
				+ "fracorganic = 0		!Fraction of organic matter" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Initial soil parameters" + '\n' 
				+ "&initpars" + '\n' 
				+ "initwater = 0.3		!Soil water content (m3.m-3)" + '\n' 
				+ "soiltemp = 15		!Soil temperature" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "";
		}
		if (configType == TUFBldVegHeights.OLIVE_CONFIG_TYPE)
		{
			file = "Soil and water parameters for Single tree in Smith St Collingwood" + '\n' 
				+ "" + '\n' 
				+ "&watcontrol" + '\n' 
				+ "keepwet = 0		!Soil water stays wet if = 1 (used for testing)" + '\n' 
				+ "simtsoil = 1		!Simulate soil temperature (yes=1) (must do)" + '\n' 
				+ "simsoilevap = 1		!Simulate soil evaporation (yes=1)" + '\n' 
				+ "reassignrain = 0	!Re-assign half hourly rain if only DAILY rainfall (PPT) available (yes=1)" + '\n' 
				+ "wsoilmethod = 1		!If = 1 then use Emax method (unlimited water); if = 2 Use Vol Wat content; if = 4 use exponenetial relationship with SMD1 & SMD2;" + '\n' 
				+ "retfunction = 1		!Water retention curve (1=Campbell curve: parameters in \"soilret\")" + '\n' 
				+ "equaluptake = 0		!water uptake from soil layers (0=based on fine root density and soil water potential)" + '\n' 
				+ "usemeaset = 0		!Use canopy transpiration if = 1; need to add 'ET' to met.dat file" + '\n' 
				+ "usemeassw = 0		!Use measured soil water if = 1; " + '\n' 
				+ "usestand = 0		!If = 1, water used by single trees scaled up to stand; If=0, scaling not done - use for single tree in stand, or BY ITSELF" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Rainfall canopy interception" + '\n' 
				+ "&wattfall" + '\n' 
				+ "rutterb = 3.7		!Drainage coefficient (B parameter in Rutter et al 1975) to calculate canopy drainage (mm)" + '\n' 
				+ "rutterd = 0.002		!Drainage parameter in Rutter et al 1975 (0.002) (dimensionless)" + '\n' 
				+ "maxstorage = 0.4	!Maximum canopy storage of water" + '\n' 
				+ "throughfall = 0.6	!rainfall passing through the canopy" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "&watinfilt" + '\n' 
				+ "expinf = 0.0		!" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil evaporation" + '\n' 
				+ "&soiletpars" + '\n' 
				+ "drythickmin = 0.01	!Minimum thickness of the dry soil layer (m)" + '\n' 
				+ "tortpar = 0.66		!XX Parameter describing tortuosity of the soil: describes diffusion in porous media" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Root parameters" + '\n' 
				+ "&rootpars" + '\n' 
				+ "rootrad = 0.0001	!Average root radius (m)" + '\n' 
				+ "rootdens = 0.5e6	!Root density (g.m-3)" + '\n' 
				+ "rootmasstot = 2.4	!Total root biomass (kg.m-2)" + '\n' 
				+ "nrootlayer = 7		!Number of soil layers that are rooted. Together with the LAYTHICK parameter, it determines the rooting depth" + '\n' 
				+ "rootbeta = 0.9		!Beta parameter characterising root distribution (Jackson et al 1996)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Plant parameters" + '\n' 
				+ "&plantpars " + '\n' 
				+ "minrootwp = -2.5	!Minimum root water potential (MPa) (Fernandez and Moreno 2008)" + '\n' 
				+ "minleafwp = -10		!Minimum leaf water potential (MPa) (not needed if MODELGS=6: Tuzet model) (Giorio 1999)" + '\n' 
				+ "plantk = 1.8		!leaf specific (total) plant hydraulic conductance (IMPORTANT!!!) (Dichio et al 2013) (=3.21 kg.m-2.s-1.MPa-1 x 10^-5   divide by 1.8 x 10^-5 gives 1.8 mmol.m-2.s-1.MPa-1)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil water retention and conductivity (LOAM)" + '\n' 
				+ "&soilret" + '\n' 
				+ "bpar = 2.79		!Empiral coefficient related to clay content of the soil (Duusma et al. 2008)" + '\n' 
				+ "psie = -0.00068		!air entry water potential (MPa) (LOAM) (Duursma et al. 2008)" + '\n' 
				+ "ksat = 264.3		!saturated soil hydaulic conductivity (LOAM) (Duursma et al. 2008) (mol.m-1.s-1.MPa-1)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil layer parameters" + '\n' 
				+ "&laypars" + '\n' 
				+ "nlayer = 10		!number of soil layers in the model" + '\n' 
				+ "laythick = 0.1		!Layer thickness (m)" + '\n' 
				+ "porefrac = 0.38		!Soil porosity (m3.m-3)" + '\n' 
				+ "Drainlimit = 0		!fraction of the pore fraction below which no drainage occurs (fraction 0-1)" + '\n' 
				+ "fracorganic = 0		!Fraction of organic matter" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Initial soil parameters" + '\n' 
				+ "&initpars" + '\n' 
				+ "initwater = 0.12		!Soil water content (m3.m-3)" + '\n' 
				+ "soiltemp = 15		!Soil temperature" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "";
		}
		if (configType == TUFBldVegHeights.GRASS_CONFIG_TYPE || configType == TUFBldVegHeights.IRRIGATED_GRASS_CONFIG_TYPE)
		{
			file = "Soil and water parameters for turf grass" + '\n' 
				+ "" + '\n' 
				+ "&watcontrol" + '\n' 
				+ "keepwet = 0		!Soil water stays wet if = 1 (used for testing)" + '\n' 
				+ "simtsoil = 1		!Simulate soil temperature (yes=1) (must do)" + '\n' 
				+ "simsoilevap = 1		!Simulate soil evaporation (yes=1)" + '\n' 
				+ "reassignrain = 0	!Re-assign half hourly rain if only DAILY rainfall (PPT) available (yes=1)" + '\n' 
				+ "wsoilmethod = 1		!If = 1 then use Emax method (unlimited water); if = 2 Use Vol Wat content; if = 4 use exponenetial relationship with SMD1 & SMD2;" + '\n' 
				+ "retfunction = 1		!Water retention curve (1=Campbell curve: parameters in \"soilret\")" + '\n' 
				+ "equaluptake = 0		!water uptake from soil layers (0=based on fine root density and soil water potential)" + '\n' 
				+ "usemeaset = 0		!Use canopy transpiration if = 1; need to add 'ET' to met.dat file" + '\n' 
				+ "usemeassw = 0		!Use measured soil water if = 1; " + '\n' 
				+ "usestand = 0		!If = 1, water used by single trees scaled up to stand; If=0, scaling not done - use for single tree in stand, or BY ITSELF" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Rainfall canopy interception" + '\n' 
				+ "&wattfall" + '\n' 
				+ "rutterb = 3.7		!Drainage coefficient (B parameter in Rutter et al 1975) to calculate canopy drainage (mm)" + '\n' 
				+ "rutterd = 0.002		!Drainage parameter in Rutter et al 1975 (0.002) (dimensionless)" + '\n' 
				+ "maxstorage = 0.4	!Maximum canopy storage of water" + '\n' 
				+ "throughfall = 0.6	!rainfall passing through the canopy" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "&watinfilt" + '\n' 
				+ "expinf = 0.0		!" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil evaporation" + '\n' 
				+ "&soiletpars" + '\n' 
				+ "drythickmin = 0.01	!Minimum thickness of the dry soil layer (m)" + '\n' 
				+ "tortpar = 0.66		!XX Parameter describing tortuosity of the soil: describes diffusion in porous media" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Root parameters" + '\n' 
				+ "&rootpars" + '\n' 
				+ "rootrad = 0.0001	!Average root radius (m)" + '\n' 
				+ "rootdens = 0.5e6	!Root density (g.m-3)" + '\n' 
				+ "rootmasstot = 2.4	!Total root biomass (kg.m-2)" + '\n' 
				+ "nrootlayer = 7		!Number of soil layers that are rooted. Together with the LAYTHICK parameter, it determines the rooting depth" + '\n' 
				+ "rootbeta = 0.9		!Beta parameter characterising root distribution (Jackson et al 1996)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Plant parameters" + '\n' 
				+ "&plantpars " + '\n' 
				+ "minrootwp = -2.5	!Minimum root water potential (MPa) (Fernandez and Moreno 2008)" + '\n' 
				+ "minleafwp = -10		!Minimum leaf water potential (MPa) (not needed if MODELGS=6: Tuzet model) (Giorio 1999)" + '\n' 
				+ "plantk = 1.8		!leaf specific (total) plant hydraulic conductance (IMPORTANT!!!) (Dichio et al 2013) (=3.21 kg.m-2.s-1.MPa-1 x 10^-5   divide by 1.8 x 10^-5 gives 1.8 mmol.m-2.s-1.MPa-1)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil water retention and conductivity (LOAM)" + '\n' 
				+ "&soilret" + '\n' 
				+ "bpar = 2.79		!Empiral coefficient related to clay content of the soil (Duusma et al. 2008)" + '\n' 
				+ "psie = -0.00068		!air entry water potential (MPa) (LOAM) (Duursma et al. 2008)" + '\n' 
				+ "ksat = 264.3		!saturated soil hydaulic conductivity (LOAM) (Duursma et al. 2008) (mol.m-1.s-1.MPa-1)" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Soil layer parameters" + '\n' 
				+ "&laypars" + '\n' 
				+ "nlayer = 10		!number of soil layers in the model" + '\n' 
				+ "laythick = 0.1		!Layer thickness (m)" + '\n' 
				+ "porefrac = 0.38		!Soil porosity (m3.m-3)" + '\n' 
				+ "Drainlimit = 0		!fraction of the pore fraction below which no drainage occurs (fraction 0-1)" + '\n' 
				+ "fracorganic = 0		!Fraction of organic matter" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "Initial soil parameters" + '\n' 
				+ "&initpars" + '\n' 
				+ "initwater = "
				+ getConfigWatparsinitwater()
				+ "		!Soil water content (m3.m-3)" + '\n' 
				+ "soiltemp = "
				+ getConfigWatparssoiltemp()
				+ "		!Soil temperature" + '\n' 
				+ "/" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "" + '\n' 
				+ "";
		}
		
		return file;
	}
	
	public String getWatparsDataFile()
	{
		
		String file=
		configWatparsTitle + '\n' 
		+ "" + '\n' 
		+ "&watcontrol" + '\n' 
		+ "keepwet = "
		+ configWatparskeepwet + '\n' 
		+ "simtsoil = "
		+ configWatparssimtsoil + '\n' 
		+ "simsoilevap = "
		+ configWatparssimsoilevap + '\n' 
		+ "reassignrain = "
		+ configWatparsreassignrain + '\n' 
		+ "retfunction = "
		+ configWatparsretfunction + '\n' 
		+ "equaluptake = "
		+ configWatparsequaluptake + '\n' 
		+ "usemeaset = "
		+ configWatparsusemeaset + '\n'
		+ "usemeassw = "
		+ configWatparsusemeassw + '\n' 
		+ "usestand="
		+ configWatparsusestand + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "Throughfall = "
		+ configWatparsThroughfall
		+ " : irrigation is to soil surface" + '\n' 
		+ "&wattfall" + '\n' 
		+ "rutterb = "
		+ configWatparsrutterb + '\n' 
		+ "rutterd = "
		+ configWatparsrutterd + '\n' 
		+ "maxstorage = "
		+ configWatparsmaxstorage + '\n' 
		+ "throughfall = "
		+ configWatparsthroughfall + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "&watinfilt" + '\n' 
		+ "expinf = "
		+ configWatparsexpinf + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "&rootpars" + '\n' 
		+ "rootresfrac = "
		+ configWatparsrootresfrac + '\n' 
		+ "rootrad = "
		+ configWatparsrootrad + '\n' 
		+ "rootdens = "
		+ configWatparsrootdens + '\n' 
		+ "rootmasstot = "
		+ configWatparsrootmasstot + '\n' 
		+ "nrootlayer = "
		+ configWatparsnrootlayer + '\n' 
		+ "rootbeta="
		+ configWatparsrootbeta + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "" + '\n' 
		+ "&plantpars" + '\n' 
		+ "minrootwp = "
		+ configWatparsminrootwp + '\n' 
		+ "minleafwp = "
		+ configWatparsminleafwp + '\n' 
		+ "plantk = "
		+ configWatparsplantk + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "Sandy soil" + '\n' 
		+ "&soilret" + '\n' 
		+ "bpar = "
		+ configWatparsbpar + '\n' 
		+ "psie = "
		+ configWatparspsie + '\n' 
		+ "ksat = "
		+ configWatparsksat + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "&laypars" + '\n' 
		+ "nlayer = "
		+ configWatparsnlayer + '\n' 
		+ "laythick = "
		+ configWatparslaythick + '\n' 
		+ "porefrac = "
		+ configWatparsporefrac + '\n' 
		+ "drainlimit = "
		+ configWatparsdrainlimit + '\n' 
		+ "fracorganic = "
		+ configWatparsfracorganic + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "&initpars" + '\n' 
		+ "initwater = "
		+ configWatparsinitwater + '\n' 
		+ "soiltemp = "
		+ configWatparssoiltemp + '\n' 
		+ "/" + '\n' 
		+ "" + '\n' 
		+ "&soiletpars" + '\n' 
		+ "drythickmin = "
		+ configWatparsdrythickmin + '\n' 
		+ "tortpar = "
		+ configWatparstortpar + '\n' 
		+ "/" + '\n';



		return file;
		
	}
	
	private double configTreex = 0;
	private double configTreey = 0;
	private int configTreexmax = 100;
	private int configTreeymax = 100;
	private int configTreexslope = 0;
	private int configTreeyslope = 0;
	private int configTreebearing = 45;
	private int configTreenotrees = 6;
	private String configTreexycoords =
			"20 40" + '\n' +
			"30 40" + '\n' +
			"40 40" + '\n' +
			"50 40" + '\n' +
			"60 40" + '\n' +
			"70 40" + '\n';
	
	private String configTreeispecies =
			"1" + '\n' +
			"2" + '\n' +
			"1" + '\n' +
			"2" + '\n' +
			"1" + '\n' +
			"2" + '\n' ;
	
	private String configTreeindivradx =
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n';
	
	private String configTreeindivrady =
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n' +
			"5" + '\n';
	
	private String configTreeindivhtcrown =
			"8" + '\n' +
			"8" + '\n' +
			"8" + '\n' +
			"8" + '\n' +
			"8" + '\n' +
			"8" + '\n';	
	
	private String configTreeindivdiam =
			"2			" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n'
			;
	
	private String configTreeindivhttrunk =
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n' +
			"2" + '\n';
	
	private String configTreeindivlarea =
			"90" + '\n' +
			"90" + '\n' +
			"90" + '\n' +
			"90" + '\n' +
			"90" + '\n' +
			"90" + '\n';
	
	private double configTreezht = 30;
	private double configTreezpd = 16;
	private double configTreez0ht = 3;
	
	private int configTreezAllradxNodates = 1;
	private String configTreezAllradxDates = "";
	private double configTreezAllradxValues = 0.5;
	
	private int configTreezAllradyNodates = 1;
	private String configTreezAllradyDates = "";
	private double configTreezAllradyValues = 0.5;
	
	private int configTreezAllhtcrownNodates = 1;
	private String configTreezAllhtcrownDates = "";
	private double configTreezAllhtcrownValues = 1.6;
	private String configTreezAllhtcrownValuesComment = "";

	private int configTreezAlldiamNodates = 1;
	private String configTreezAlldiamDates = "";
	private double configTreezAlldiamValues = 0.02;
	private String configTreezAlldiamValuesComment = "";
	
	private int configTreezAllhttrunkNodates = 1;
	private String configTreezAllhttrunkDates = "";
	private double configTreezAllhttrunkValues = 0.5;
	private String configTreezAllhttrunkValuesComment = "";
	
	private int configTreezAlllareaNodates = 1;
	private String configTreezAlllareaDates = "";
	private double configTreezAlllareaValues = 2.48;
	private String configTreezAlllareaValuesComment = "";
	

	private String configTreeTitle = "sINGLE TREE IN 1x1 metre plot.";
	
	
	public String getTreeDataFile()
	{
		String treeFile = configTreeTitle + '\n' +
				"" + '\n' +
				"&plot			" + '\n' +			
				"x0	=	"
				+ configTreex
				+ "		" + '\n' +		
				"y0	=	"
				+ configTreey
				+ "		" + '\n' +		
				"xmax	=	"
				+ getConfigTreexmax()
				+ "		" + '\n' +		
				"ymax	=	"
				+ configTreeymax
				+ "		" + '\n' +		
				"xslope	=	"
				+ configTreexslope
				+ "		" + '\n' +		
				"yslope	=	"
				+ configTreeyslope
				+ "		" + '\n' +		
				"bearing	=	"
				+ configTreebearing
				+ "		" + '\n' +		
				"notrees	=	"
				+ configTreenotrees + '\n' +
				"/						" + '\n' +
				"" + '\n' +
				//"Ca. standard aerodynamics for canopy of 30m height; Jones 1992." + '\n' +
				"&aerodyn			" + '\n' +
				"zht	=	"
				+ configTreezht 
				+ "			!Height of wind speed measurements"
				+ '\n' +
				"zpd	=	"
				+ configTreezpd
				+ "			!2/3 of the tree crown height (rule of thumb)"
				+ '\n' +
				"z0ht	=	"
				+ configTreez0ht 
				+ "			!1/10 of the tree crown height (rule of thumb)"
				+ '\n' +
				"/		" + '\n' +
				"" + '\n' +
				"&xy" + '\n' +
				"xycoords=" + '\n' +
				configTreexycoords +
				"/" + '\n' +
				"" + '\n' +
				"&speclist" + '\n' +
				"ispecies=" + '\n' +
				configTreeispecies +
				"/" + '\n' +
				"" + '\n' +
				"crown radius in the x-direction (metres)." + '\n' +
				"&allradx				" + '\n' +
				"nodates = "
				+ configTreezAllradxNodates + '\n' +
				"values	= "
				+ configTreezAllradxValues + '\n' +

				"/			" + '\n' +	
				"" + '\n' +
				"crown radius in the y-direction." + '\n' +
				"&allrady				" + '\n' +
				"nodates	=	"
				+ configTreezAllradyNodates
				+ "		" + '\n' +
				"values	="
				+ configTreezAllradyValues + '\n' +

				"/			" + '\n' +	
				"" + '\n' +
				"crown height for each tree." + '\n' +
				"&allhtcrown				" + '\n' +
				"nodates	=	"
				+ configTreezAllhtcrownNodates
				+ "		" + '\n' +
				"values	= "
				+ configTreezAllhtcrownValues + "				"
						+ configTreezAllhtcrownValuesComment + '\n' +

				"/			" + '\n' +	
				"" + '\n' +
				"stem diameter for each tree" + '\n' +
				"&alldiam				" + '\n' +
				"nodates	=	"
				+ configTreezAlldiamNodates
				+ "		" + '\n' +
				"values	="
				+ configTreezAlldiamValues + "				"
						+ configTreezAlldiamValuesComment + '\n' +

				
				"/		" + '\n' +
				"" + '\n' +
				"trunk height" + '\n' +	
				"&allhttrunk		" + '\n' +
				"nodates	=	"
				+ configTreezAllhttrunkNodates + '\n' +
				"values	="
				+ configTreezAllhttrunkValues + "				"
						+ configTreezAllhttrunkValuesComment + '\n' +

				"/		" + '\n' +
				"" + '\n' +
				"leaf area (m2.tree-1)" + '\n' +	
				"&alllarea		" + '\n' +
				"nodates	=	"
				+ configTreezAlllareaNodates + '\n' +
				"values	="
				+ configTreezAlllareaValues + "				"
						+ configTreezAlllareaValuesComment + '\n' +

				"/"  ;
		
		return treeFile;
	}
	
	private String pointsNoPoints = "2";
	private String pointsInputType = "2";
	private String pointsCoords1 = "0.5 0.5 0.8";
	private String pointsCoords2 = "0.5 0.5 0.5";
	private String pointsTransectAngle = "45";
	private String pointsTransectSpacing = "0.2";
	private String pointsZHeight = "1";
	
	public String getPointsDataFile(MaespaConfigConfileDat maespaConfigConfileDat)
	{
		setPointsNoPoints(maespaConfigConfileDat.getPointsNoPoints());
		setPointsInputType(maespaConfigConfileDat.getPointsInputType());
		setPointsCoords1(maespaConfigConfileDat.getPointsCoords1());
		setPointsTransectAngle(maespaConfigConfileDat.getPointsTransectAngle());
		setPointsTransectSpacing(maespaConfigConfileDat.getPointsTransectSpacing());
		setPointsZHeight(maespaConfigConfileDat.getPointsZHeight());
		
		String pointsDataFile = "" + '\n' +
				"" + '\n' +
				"    &CONTROL" + '\n' +
				"    NOPOINTS = "
				+ pointsNoPoints + '\n' +
				"    INPUTTYPE = "
				+ pointsInputType + '\n' +
				"    /" + '\n' +
				"    &XYZ" + '\n' +
				"    COORDS =" + '\n' +
				"    "
				+ pointsCoords1 + '\n' +

				"    /" + '\n' +
				"    &TRANSECT" + '\n' +
				"    ANGLE="
				+ pointsTransectAngle + '\n' +
				"    SPACING="
				+ pointsTransectSpacing + '\n' +
				"    ZHEIGHT="
				+ pointsZHeight + '\n' +
				"    /" + '\n' +
				"" + '\n' +
				"";
		
		
		return pointsDataFile;
	}


	public String getPointsNoPoints()
	{
		return pointsNoPoints;
	}


	public void setPointsNoPoints(String pointsNoPoints)
	{
		this.pointsNoPoints = pointsNoPoints;
	}


	public String getPointsInputType()
	{
		return pointsInputType;
	}


	public void setPointsInputType(String pointsInputType)
	{
		this.pointsInputType = pointsInputType;
	}


	public String getPointsCoords1()
	{
		return pointsCoords1;
	}


	public void setPointsCoords1(String pointsCoords1)
	{
		this.pointsCoords1 = pointsCoords1;
	}


	public String getPointsCoords2()
	{
		return pointsCoords2;
	}


	public void setPointsCoords2(String pointsCoords2)
	{
		this.pointsCoords2 = pointsCoords2;
	}


	public String getPointsTransectAngle()
	{
		return pointsTransectAngle;
	}


	public void setPointsTransectAngle(String pointsTransectAngle)
	{
		this.pointsTransectAngle = pointsTransectAngle;
	}


	public String getPointsTransectSpacing()
	{
		return pointsTransectSpacing;
	}


	public void setPointsTransectSpacing(String pointsTransectSpacing)
	{
		this.pointsTransectSpacing = pointsTransectSpacing;
	}


	public String getPointsZHeight()
	{
		return pointsZHeight;
	}


	public void setPointsZHeight(String pointsZHeight)
	{
		this.pointsZHeight = pointsZHeight;
	}


	public String getConfigTreeTitle()
	{
		return configTreeTitle;
	}


	public void setConfigTreeTitle(String configTreeTitle)
	{
		this.configTreeTitle = configTreeTitle;
	}


	public double getConfigTreezAllradxNodates()
	{
		return configTreezAllradxNodates;
	}


	public void setConfigTreezAllradxNodates(int configTreezAllradxNodates)
	{
		this.configTreezAllradxNodates = configTreezAllradxNodates;
	}


	public String getConfigTreezAllradxDates()
	{
		return configTreezAllradxDates;
	}


	public void setConfigTreezAllradxDates(String configTreezAllradxDates)
	{
		this.configTreezAllradxDates = configTreezAllradxDates;
	}


	public double getConfigTreezAllradxValues()
	{
		return configTreezAllradxValues;
	}


	public void setConfigTreezAllradxValues(double configTreezAllradxValues)
	{
		this.configTreezAllradxValues = configTreezAllradxValues;
	}


	public double getConfigTreezAllradyNodates()
	{
		return configTreezAllradyNodates;
	}


	public void setConfigTreezAllradyNodates(int configTreezAllradyNodates)
	{
		this.configTreezAllradyNodates = configTreezAllradyNodates;
	}


	public String getConfigTreezAllradyDates()
	{
		return configTreezAllradyDates;
	}


	public void setConfigTreezAllradyDates(String configTreezAllradyDates)
	{
		this.configTreezAllradyDates = configTreezAllradyDates;
	}


	public double getConfigTreezAllradyValues()
	{
		return configTreezAllradyValues;
	}


	public void setConfigTreezAllradyValues(double configTreezAllradyValues)
	{
		this.configTreezAllradyValues = configTreezAllradyValues;
	}


	public double getConfigTreezAllhtcrownNodates()
	{
		return configTreezAllhtcrownNodates;
	}


	public void setConfigTreezAllhtcrownNodates(int configTreezAllhtcrownNodates)
	{
		this.configTreezAllhtcrownNodates = configTreezAllhtcrownNodates;
	}


	public String getConfigTreezAllhtcrownDates()
	{
		return configTreezAllhtcrownDates;
	}


	public void setConfigTreezAllhtcrownDates(String configTreezAllhtcrownDates)
	{
		this.configTreezAllhtcrownDates = configTreezAllhtcrownDates;
	}


	public double getConfigTreezAllhtcrownValues()
	{
		return configTreezAllhtcrownValues;
	}


	public void setConfigTreezAllhtcrownValues(double configTreezAllhtcrownValues)
	{
		this.configTreezAllhtcrownValues = configTreezAllhtcrownValues;
	}


	public double getConfigTreezAlldiamNodates()
	{
		return configTreezAlldiamNodates;
	}


	public void setConfigTreezAlldiamNodates(int configTreezAlldiamNodates)
	{
		this.configTreezAlldiamNodates = configTreezAlldiamNodates;
	}


	public String getConfigTreezAlldiamDates()
	{
		return configTreezAlldiamDates;
	}


	public void setConfigTreezAlldiamDates(String configTreezAlldiamDates)
	{
		this.configTreezAlldiamDates = configTreezAlldiamDates;
	}


	public double getConfigTreezAlldiamValues()
	{
		return configTreezAlldiamValues;
	}


	public void setConfigTreezAlldiamValues(double configTreezAlldiamValues)
	{
		this.configTreezAlldiamValues = configTreezAlldiamValues;
	}


	public double getConfigTreezAllhttrunkNodates()
	{
		return configTreezAllhttrunkNodates;
	}


	public void setConfigTreezAllhttrunkNodates(int configTreezAllhttrunkNodates)
	{
		this.configTreezAllhttrunkNodates = configTreezAllhttrunkNodates;
	}


	public String getConfigTreezAllhttrunkDates()
	{
		return configTreezAllhttrunkDates;
	}


	public void setConfigTreezAllhttrunkDates(String configTreezAllhttrunkDates)
	{
		this.configTreezAllhttrunkDates = configTreezAllhttrunkDates;
	}


	public double getConfigTreezAllhttrunkValues()
	{
		return configTreezAllhttrunkValues;
	}


	public void setConfigTreezAllhttrunkValues(double configTreezAllhttrunkValues)
	{
		this.configTreezAllhttrunkValues = configTreezAllhttrunkValues;
	}


	public double getConfigTreezAlllareaNodates()
	{
		return configTreezAlllareaNodates;
	}


	public void setConfigTreezAlllareaNodates(int configTreezAlllareaNodates)
	{
		this.configTreezAlllareaNodates = configTreezAlllareaNodates;
	}


	public String getConfigTreezAlllareaDates()
	{
		return configTreezAlllareaDates;
	}


	public void setConfigTreezAlllareaDates(String configTreezAlllareaDates)
	{
		this.configTreezAlllareaDates = configTreezAlllareaDates;
	}


	public double getConfigTreezAlllareaValues()
	{
		return configTreezAlllareaValues;
	}


	public void setConfigTreezAlllareaValues(double configTreezAlllareaValues)
	{
		this.configTreezAlllareaValues = configTreezAlllareaValues;
	}


	public String getConfigFileTitle()
	{
		return configFileTitle;
	}


	public void setConfigFileTitle(String configFileTitle)
	{
		this.configFileTitle = configFileTitle;
	}


	public int getConfigiohrly()
	{
		return configiohrly;
	}


	public void setConfigiohrly(int configiohrly)
	{
		this.configiohrly = configiohrly;
	}


	public int getConfigiotutd()
	{
		return configiotutd;
	}


	public void setConfigiotutd(int configiotutd)
	{
		this.configiotutd = configiotutd;
	}


	public int getConfigioresp()
	{
		return configioresp;
	}


	public void setConfigioresp(int configioresp)
	{
		this.configioresp = configioresp;
	}


	public int getConfigiohist()
	{
		return configiohist;
	}


	public void setConfigiohist(int configiohist)
	{
		this.configiohist = configiohist;
	}


	public int getConfignotrees()
	{
		return configNotrees;
	}


	public void setConfignotrees(int confignotrees)
	{
		this.configNotrees = confignotrees;
	}


	public double getConfigTreex()
	{
		return configTreex;
	}


	public void setConfigTreex(double configTreex)
	{
		this.configTreex = configTreex;
	}


	public double getConfigTreey()
	{
		return configTreey;
	}


	public void setConfigTreey(double configTreey)
	{
		this.configTreey = configTreey;
	}


	public int getConfigTreexmax()
	{
		return configTreexmax;
	}


	public void setConfigTreexmax(int configTreexmax)
	{
		this.configTreexmax = configTreexmax;
	}


	public int getConfigTreeymax()
	{
		return configTreeymax;
	}


	public void setConfigTreeymax(int configTreeymax)
	{
		this.configTreeymax = configTreeymax;
	}


	public int getConfigTreexslope()
	{
		return configTreexslope;
	}


	public void setConfigTreexslope(int configTreexslope)
	{
		this.configTreexslope = configTreexslope;
	}


	public int getConfigTreeyslope()
	{
		return configTreeyslope;
	}


	public void setConfigTreeyslope(int configTreeyslope)
	{
		this.configTreeyslope = configTreeyslope;
	}


	public int getConfigTreebearing()
	{
		return configTreebearing;
	}


	public void setConfigTreebearing(int configTreebearing)
	{
		this.configTreebearing = configTreebearing;
	}


	public int getConfigTreenotrees()
	{
		return configTreenotrees;
	}


	public void setConfigTreenotrees(int configTreenotrees)
	{
		this.configTreenotrees = configTreenotrees;
	}


	public String getConfigTreexycoords()
	{
		return configTreexycoords;
	}


	public void setConfigTreexycoords(String configTreexycoords)
	{
		this.configTreexycoords = configTreexycoords;
	}


	public String getConfigTreeispecies()
	{
		return configTreeispecies;
	}


	public void setConfigTreeispecies(String configTreeispecies)
	{
		this.configTreeispecies = configTreeispecies;
	}


	public String getConfigTreeindivradx()
	{
		return configTreeindivradx;
	}


	public void setConfigTreeindivradx(String configTreeindivradx)
	{
		this.configTreeindivradx = configTreeindivradx;
	}


	public String getConfigTreeindivrady()
	{
		return configTreeindivrady;
	}


	public void setConfigTreeindivrady(String configTreeindivrady)
	{
		this.configTreeindivrady = configTreeindivrady;
	}


	public String getConfigTreeindivhtcrown()
	{
		return configTreeindivhtcrown;
	}


	public void setConfigTreeindivhtcrown(String configTreeindivhtcrown)
	{
		this.configTreeindivhtcrown = configTreeindivhtcrown;
	}


	public String getConfigTreeindivdiam()
	{
		return configTreeindivdiam;
	}


	public void setConfigTreeindivdiam(String configTreeindivdiam)
	{
		this.configTreeindivdiam = configTreeindivdiam;
	}


	public String getConfigTreeindivhttrunk()
	{
		return configTreeindivhttrunk;
	}


	public void setConfigTreeindivhttrunk(String configTreeindivhttrunk)
	{
		this.configTreeindivhttrunk = configTreeindivhttrunk;
	}


	public String getConfigTreeindivlarea()
	{
		return configTreeindivlarea;
	}


	public void setConfigTreeindivlarea(String configTreeindivlarea)
	{
		this.configTreeindivlarea = configTreeindivlarea;
	}


	public double getConfigTreezht()
	{
		return configTreezht;
	}


	public void setConfigTreezht(double d)
	{
		this.configTreezht = d;
	}


	public double getConfigTreezpd()
	{
		return configTreezpd;
	}


	public void setConfigTreezpd(double d)
	{
		this.configTreezpd = d;
	}


	public double getConfigTreez0ht()
	{
		return configTreez0ht;
	}


	public void setConfigTreez0ht(double d)
	{
		this.configTreez0ht = d;
	}
	
	private int configTreeMapNumberTreePlots = 4;
	private String configTreeMapXLocation="1 5 6 7";	
	private String configTreeMapYLocation="1 6 6 3";
	private String configTreeMapPhyfileNumber="1 1 2 2";
	private String configTreeMapStrfileNumber="1 1 2 2";
	private String configTreeMapTreesFilesNumber="1 1 2 2";
	private String configTreeMapTreesHeight="3 3 3 3";
	private String configTreeMapTrees ;
	
	private int configTreeMapNumberBuildingPlots;
	private String configTreeMapBuildingHeight;
	private String configTreeMapBuildingXLocation;
	private String configTreeMapBuildingYLocation;
	
	private int configTreeMapWidth;
	private int configTreeMapLength;
	
	private int configTreeMapNumsfcab;
	private int configTreeMapHighestBuildingHeight;
	private int configTreeMapCentralArrayLength;
	private int configTreeMapCentralWidth;
	private int configTreeMapCentralLength;
	private int configTreeMapX;
	private int configTreeMapY;
	private int configTreeMapX1;
	private int configTreeMapX2;
	private int configTreeMapY1;
	private int configTreeMapY2;	
	private int configTreeMapDomainLength;
	private int configTreeMapDomainWidth;
	private double configTreeMapGridSize;
	private int configTreeMapUsingDiffShading;
	
	

	
	public String getTreeMapDataFile()
	{	
		String file=
				"&count" + '\n' +
				"numberTreePlots="
				+ configTreeMapNumberTreePlots + '\n' +
				"/" + '\n' +
				
				"&runSwitches" + '\n' +
				"partitioningMethod=" + 17 + '\n' +
				"usingDiffShading=" + getConfigTreeMapUsingDiffShading() + '\n' +
				
				"/" + '\n' +
				"" + '\n' +
				"&location" + '\n' +
				"xLocation="
				+ configTreeMapXLocation + '\n' +
				"yLocation="
				+ configTreeMapYLocation + '\n' +
				"phyfileNumber="
				+ configTreeMapPhyfileNumber + '\n' +
				"strfileNumber="
				+ configTreeMapStrfileNumber + '\n' +
				"treesfileNumber="
				+ configTreeMapTreesFilesNumber + '\n' +
				"treesHeight="
				+ configTreeMapTreesHeight + '\n' +				
				"trees="
				+ configTreeMapTrees + '\n' +				
				"/" + '\n' +
				
				"&buildingcount" + '\n' +
				"numberBuildingPlots=" +
				configTreeMapNumberBuildingPlots + '\n' +
				"/" + '\n' +				
				"&buildinglocation" + '\n' +
				"xBuildingLocation="
				+ configTreeMapBuildingXLocation + '\n' +
				"yBuildingLocation="
				+ configTreeMapBuildingYLocation + '\n' +
				"buildingsHeight="
				+ configTreeMapBuildingHeight + '\n' +
				"/" + '\n' + 
				
				"&domain" + '\n' +
				"width="
				+ configTreeMapWidth + '\n' +
				"length="
				+ configTreeMapLength + '\n' +							
				
				"configTreeMapCentralArrayLength="+getConfigTreeMapCentralArrayLength() + '\n' +	
				"configTreeMapCentralWidth="+getConfigTreeMapCentralWidth() + '\n' +	
				"configTreeMapCentralLength="+getConfigTreeMapCentralLength() + '\n' +	
				"configTreeMapX="+getConfigTreeMapX() + '\n' +	
				"configTreeMapY="+getConfigTreeMapY() + '\n' +	
				"configTreeMapX1="+getConfigTreeMapX1() + '\n' +	
				"configTreeMapX2="+getConfigTreeMapX2() + '\n' +	
				"configTreeMapY1="+getConfigTreeMapY1() + '\n' +	
				"configTreeMapY2="+getConfigTreeMapY2() + '\n' +	
				"configTreeMapGridSize="+getConfigTreeMapGridSize() + '\n' +	
				"configTreeMapNumsfcab="+getConfigTreeMapNumsfcab() + '\n' +	
				"configTreeMapHighestBuildingHeight="+getConfigTreeMapHighestBuildingHeight() + '\n' +	

								
				"/" + '\n'	
				;
		return file;
		
	}
	
	public String getConfigTreeMapTreesHeight()
	{
		return configTreeMapTreesHeight;
	}


	public void setConfigTreeMapTreesHeight(String configTreeMapTreesHeight)
	{
		this.configTreeMapTreesHeight = configTreeMapTreesHeight;
	}


	public void writeTreeMapConfigFile(String inputDirectory)
	{
		common.createDirectory(runDirectory + inputDirectory);
		common.writeFile(getTreeMapDataFile(), runDirectory + inputDirectory + "/" + "treemap.dat");
	}


	public int getConfigTreeMapNumberTreePlots()
	{
		return configTreeMapNumberTreePlots;
	}


	public void setConfigTreeMapNumberTreePlots(int configTreeMapNumberTreePlots)
	{
		this.configTreeMapNumberTreePlots = configTreeMapNumberTreePlots;
	}


	public String getConfigTreeMapXLocation()
	{
		return configTreeMapXLocation;
	}


	public void setConfigTreeMapXLocation(String configTreeMapXLocation)
	{
		this.configTreeMapXLocation = configTreeMapXLocation;
	}


	public String getConfigTreeMapYLocation()
	{
		return configTreeMapYLocation;
	}


	public void setConfigTreeMapYLocation(String configTreeMapYLocation)
	{
		this.configTreeMapYLocation = configTreeMapYLocation;
	}


	public String getConfigTreeMapPhyfileNumber()
	{
		return configTreeMapPhyfileNumber;
	}


	public void setConfigTreeMapPhyfileNumber(String configTreeMapPhyfileNumber)
	{
		this.configTreeMapPhyfileNumber = configTreeMapPhyfileNumber;
	}


	public String getConfigTreeMapStrfileNumber()
	{
		return configTreeMapStrfileNumber;
	}


	public void setConfigTreeMapStrfileNumber(String configTreeMapStrfileNumber)
	{
		this.configTreeMapStrfileNumber = configTreeMapStrfileNumber;
	}


	public String getConfigTreeMapTreesFilesNumber()
	{
		return configTreeMapTreesFilesNumber;
	}


	public void setConfigTreeMapTreesFilesNumber(
			String configTreeMapTreesFilesNumber)
	{
		this.configTreeMapTreesFilesNumber = configTreeMapTreesFilesNumber;
	}
	
	public int getConfigNotrees()
	{
		return configNotrees;
	}


	public void setConfigNotrees(int configNotrees)
	{
		this.configNotrees = configNotrees;
	}


	public int getConfigIowatbal()
	{
		return configIowatbal;
	}


	public void setConfigIowatbal(int configIowatbal)
	{
		this.configIowatbal = configIowatbal;
	}


	public String getConfigStartdate()
	{
		return configStartdate;
	}


	public void setConfigStartdate(String configStartdate)
	{
		this.configStartdate = configStartdate;
	}


	public String getConfigEnddate()
	{
		return configEnddate;
	}


	public void setConfigEnddate(String configEnddate)
	{
		this.configEnddate = configEnddate;
	}


	public int getConfigItargets()
	{
		return configItargets;
	}


	public void setConfigItargets(int configItargets)
	{
		this.configItargets = configItargets;
	}


	public int getConfigNspecies()
	{
		return configNspecies;
	}


	public void setConfigNspecies(int configNspecies)
	{
		this.configNspecies = configNspecies;
	}


	public String getConfigSpeciesnames()
	{
		return configSpeciesnames;
	}


	public void setConfigSpeciesnames(String configSpeciesnames)
	{
		this.configSpeciesnames = configSpeciesnames;
	}


	public String getConfigPhyfiles()
	{
		return configPhyfiles;
	}


	public void setConfigPhyfiles(String configPhyfiles)
	{
		this.configPhyfiles = configPhyfiles;
	}


	public String getConfigStrfiles()
	{
		return configStrfiles;
	}


	public void setConfigStrfiles(String configStrfiles)
	{
		this.configStrfiles = configStrfiles;
	}


	public int getConfigNolay()
	{
		return configNolay;
	}


	public void setConfigNolay(int configNolay)
	{
		this.configNolay = configNolay;
	}


	public int getConfigPplay()
	{
		return configPplay;
	}


	public void setConfigPplay(int configPplay)
	{
		this.configPplay = configPplay;
	}


	public int getConfigNzen()
	{
		return configNzen;
	}


	public void setConfigNzen(int configNzen)
	{
		this.configNzen = configNzen;
	}


	public int getConfigNaz()
	{
		return configNaz;
	}


	public void setConfigNaz(int configNaz)
	{
		this.configNaz = configNaz;
	}


	public int getConfigModelgs()
	{
		return configModelgs;
	}


	public void setConfigModelgs(int configModelgs)
	{
		this.configModelgs = configModelgs;
	}


	public int getConfigModeljm()
	{
		return configModeljm;
	}


	public void setConfigModeljm(int configModeljm)
	{
		this.configModeljm = configModeljm;
	}


	public int getConfigModelrd()
	{
		return configModelrd;
	}


	public void setConfigModelrd(int configModelrd)
	{
		this.configModelrd = configModelrd;
	}


	public int getConfigModelss()
	{
		return configModelss;
	}


	public void setConfigModelss(int configModelss)
	{
		this.configModelss = configModelss;
	}


	public int getConfigItermax()
	{
		return configItermax;
	}


	public void setConfigItermax(int configItermax)
	{
		this.configItermax = configItermax;
	}

	public int getConfigWatparskeepwet()
	{
		return configWatparskeepwet;
	}


	public void setConfigWatparskeepwet(int configWatparskeepwet)
	{
		this.configWatparskeepwet = configWatparskeepwet;
	}


	public int getConfigWatparssimtsoil()
	{
		return configWatparssimtsoil;
	}


	public void setConfigWatparssimtsoil(int configWatparssimtsoil)
	{
		this.configWatparssimtsoil = configWatparssimtsoil;
	}


	public int getConfigWatparssimsoilevap()
	{
		return configWatparssimsoilevap;
	}


	public void setConfigWatparssimsoilevap(int configWatparssimsoilevap)
	{
		this.configWatparssimsoilevap = configWatparssimsoilevap;
	}


	public int getConfigWatparsreassignrain()
	{
		return configWatparsreassignrain;
	}


	public void setConfigWatparsreassignrain(int configWatparsreassignrain)
	{
		this.configWatparsreassignrain = configWatparsreassignrain;
	}


	public int getConfigWatparsretfunction()
	{
		return configWatparsretfunction;
	}


	public void setConfigWatparsretfunction(int configWatparsretfunction)
	{
		this.configWatparsretfunction = configWatparsretfunction;
	}


	public int getConfigWatparsequaluptake()
	{
		return configWatparsequaluptake;
	}


	public void setConfigWatparsequaluptake(int configWatparsequaluptake)
	{
		this.configWatparsequaluptake = configWatparsequaluptake;
	}


	public int getConfigWatparsusemeaset()
	{
		return configWatparsusemeaset;
	}


	public void setConfigWatparsusemeaset(int configWatparsusemeaset)
	{
		this.configWatparsusemeaset = configWatparsusemeaset;
	}


	public int getConfigWatparsusemeassw()
	{
		return configWatparsusemeassw;
	}


	public void setConfigWatparsusemeassw(int configWatparsusemeassw)
	{
		this.configWatparsusemeassw = configWatparsusemeassw;
	}


	public int getConfigWatparsusestand()
	{
		return configWatparsusestand;
	}


	public void setConfigWatparsusestand(int configWatparsusestand)
	{
		this.configWatparsusestand = configWatparsusestand;
	}


	public double getConfigWatparsThroughfall()
	{
		return configWatparsThroughfall;
	}


	public void setConfigWatparsThroughfall(double configWatparsThroughfall)
	{
		this.configWatparsThroughfall = configWatparsThroughfall;
	}


	public double getConfigWatparsrutterb()
	{
		return configWatparsrutterb;
	}


	public void setConfigWatparsrutterb(double configWatparsrutterb)
	{
		this.configWatparsrutterb = configWatparsrutterb;
	}


	public double getConfigWatparsrutterd()
	{
		return configWatparsrutterd;
	}


	public void setConfigWatparsrutterd(double configWatparsrutterd)
	{
		this.configWatparsrutterd = configWatparsrutterd;
	}


	public double getConfigWatparsmaxstorage()
	{
		return configWatparsmaxstorage;
	}


	public void setConfigWatparsmaxstorage(double configWatparsmaxstorage)
	{
		this.configWatparsmaxstorage = configWatparsmaxstorage;
	}


	public double getConfigWatparsthroughfall()
	{
		return configWatparsthroughfall;
	}


	public void setConfigWatparsthroughfall(double configWatparsthroughfall)
	{
		this.configWatparsthroughfall = configWatparsthroughfall;
	}


	public double getConfigWatparsexpinf()
	{
		return configWatparsexpinf;
	}


	public void setConfigWatparsexpinf(double configWatparsexpinf)
	{
		this.configWatparsexpinf = configWatparsexpinf;
	}


	public double getConfigWatparsrootresfrac()
	{
		return configWatparsrootresfrac;
	}


	public void setConfigWatparsrootresfrac(double configWatparsrootresfrac)
	{
		this.configWatparsrootresfrac = configWatparsrootresfrac;
	}


	public double getConfigWatparsrootrad()
	{
		return configWatparsrootrad;
	}


	public void setConfigWatparsrootrad(double configWatparsrootrad)
	{
		this.configWatparsrootrad = configWatparsrootrad;
	}


	public double getConfigWatparsrootdens()
	{
		return configWatparsrootdens;
	}


	public void setConfigWatparsrootdens(double configWatparsrootdens)
	{
		this.configWatparsrootdens = configWatparsrootdens;
	}


	public int getConfigWatparsrootmasstot()
	{
		return configWatparsrootmasstot;
	}


	public void setConfigWatparsrootmasstot(int configWatparsrootmasstot)
	{
		this.configWatparsrootmasstot = configWatparsrootmasstot;
	}


	public int getConfigWatparsnrootlayer()
	{
		return configWatparsnrootlayer;
	}


	public void setConfigWatparsnrootlayer(int configWatparsnrootlayer)
	{
		this.configWatparsnrootlayer = configWatparsnrootlayer;
	}


	public double getConfigWatparsrootbeta()
	{
		return configWatparsrootbeta;
	}


	public void setConfigWatparsrootbeta(double configWatparsrootbeta)
	{
		this.configWatparsrootbeta = configWatparsrootbeta;
	}


	public double getConfigWatparsminrootwp()
	{
		return configWatparsminrootwp;
	}


	public void setConfigWatparsminrootwp(double configWatparsminrootwp)
	{
		this.configWatparsminrootwp = configWatparsminrootwp;
	}


	public double getConfigWatparsminleafwp()
	{
		return configWatparsminleafwp;
	}


	public void setConfigWatparsminleafwp(double configWatparsminleafwp)
	{
		this.configWatparsminleafwp = configWatparsminleafwp;
	}


	public int getConfigWatparsplantk()
	{
		return configWatparsplantk;
	}


	public void setConfigWatparsplantk(int configWatparsplantk)
	{
		this.configWatparsplantk = configWatparsplantk;
	}


	public double getConfigWatparsbpar()
	{
		return configWatparsbpar;
	}


	public void setConfigWatparsbpar(double configWatparsbpar)
	{
		this.configWatparsbpar = configWatparsbpar;
	}


	public double getConfigWatparspsie()
	{
		return configWatparspsie;
	}


	public void setConfigWatparspsie(double configWatparspsie)
	{
		this.configWatparspsie = configWatparspsie;
	}


	public double getConfigWatparsksat()
	{
		return configWatparsksat;
	}


	public void setConfigWatparsksat(double configWatparsksat)
	{
		this.configWatparsksat = configWatparsksat;
	}


	public int getConfigWatparsnlayer()
	{
		return configWatparsnlayer;
	}


	public void setConfigWatparsnlayer(int configWatparsnlayer)
	{
		this.configWatparsnlayer = configWatparsnlayer;
	}


	public double getConfigWatparslaythick()
	{
		return configWatparslaythick;
	}


	public void setConfigWatparslaythick(double configWatparslaythick)
	{
		this.configWatparslaythick = configWatparslaythick;
	}


	public double getConfigWatparsporefrac()
	{
		return configWatparsporefrac;
	}


	public void setConfigWatparsporefrac(double configWatparsporefrac)
	{
		this.configWatparsporefrac = configWatparsporefrac;
	}


	public double getConfigWatparsdrainlimit()
	{
		return configWatparsdrainlimit;
	}


	public void setConfigWatparsdrainlimit(double configWatparsdrainlimit)
	{
		this.configWatparsdrainlimit = configWatparsdrainlimit;
	}


	public double getConfigWatparsfracorganic()
	{
		return configWatparsfracorganic;
	}


	public void setConfigWatparsfracorganic(double configWatparsfracorganic)
	{
		this.configWatparsfracorganic = configWatparsfracorganic;
	}


	public double getConfigWatparsinitwater()
	{
		return configWatparsinitwater;
	}


	public void setConfigWatparsinitwater(double configWatparsinitwater)
	{
		this.configWatparsinitwater = configWatparsinitwater;
	}


	public int getConfigWatparssoiltemp()
	{
		return configWatparssoiltemp;
	}


	public void setConfigWatparssoiltemp(int configWatparssoiltemp)
	{
		this.configWatparssoiltemp = configWatparssoiltemp;
	}


	public double getConfigWatparsdrythickmin()
	{
		return configWatparsdrythickmin;
	}


	public void setConfigWatparsdrythickmin(double configWatparsdrythickmin)
	{
		this.configWatparsdrythickmin = configWatparsdrythickmin;
	}


	public double getConfigWatparstortpar()
	{
		return configWatparstortpar;
	}


	public void setConfigWatparstortpar(double configWatparstortpar)
	{
		this.configWatparstortpar = configWatparstortpar;
	}


	public String getConfigWatparsTitle()
	{
		return configWatparsTitle;
	}


	public void setConfigWatparsTitle(String configWatparsTitle)
	{
		this.configWatparsTitle = configWatparsTitle;
	}


	public int getConfigTreeMapNumberBuildingPlots()
	{
		return configTreeMapNumberBuildingPlots;
	}


	public void setConfigTreeMapNumberBuildingPlots(
			int configTreeMapNumberBuildingPlots)
	{
		this.configTreeMapNumberBuildingPlots = configTreeMapNumberBuildingPlots;
	}


	public String getConfigTreeMapBuildingHeight()
	{
		return configTreeMapBuildingHeight;
	}


	public void setConfigTreeMapBuildingHeight(String configTreeMapBuildingHeight)
	{
		this.configTreeMapBuildingHeight = configTreeMapBuildingHeight;
	}


	public String getConfigTreeMapBuildingXLocation()
	{
		return configTreeMapBuildingXLocation;
	}


	public void setConfigTreeMapBuildingXLocation(
			String configTreeMapBuildingXLocation)
	{
		this.configTreeMapBuildingXLocation = configTreeMapBuildingXLocation;
	}


	public String getConfigTreeMapBuildingYLocation()
	{
		return configTreeMapBuildingYLocation;
	}


	public void setConfigTreeMapBuildingYLocation(
			String configTreeMapBuildingYLocation)
	{
		this.configTreeMapBuildingYLocation = configTreeMapBuildingYLocation;
	}


	public int getConfigTreeMapWidth()
	{
		return configTreeMapWidth;
	}


	public void setConfigTreeMapWidth(int configTreeMapWidth)
	{
		this.configTreeMapWidth = configTreeMapWidth;
	}


	public int getConfigTreeMapLength()
	{
		return configTreeMapLength;
	}


	public void setConfigTreeMapLength(int configTreeMapLength)
	{
		this.configTreeMapLength = configTreeMapLength;
	}


	public int getConfigIsunla()
	{
		return configIsunla;
	}


	public void setConfigIsunla(int configIsunla)
	{
		this.configIsunla = configIsunla;
	}


	public int getConfigTreeMapNumsfcab()
	{
		return configTreeMapNumsfcab;
	}


	public void setConfigTreeMapNumsfcab(int numsfcab)
	{
		this.configTreeMapNumsfcab = numsfcab;
	}


	public int getConfigTreeMapCentralArrayLength()
	{
		return configTreeMapCentralArrayLength;
	}


	public void setConfigTreeMapCentralArrayLength(int centralArrayLength)
	{
		this.configTreeMapCentralArrayLength = centralArrayLength;
	}


	public int getConfigTreeMapCentralWidth()
	{
		return configTreeMapCentralWidth;
	}


	public void setConfigTreeMapCentralWidth(int centralWidth)
	{
		this.configTreeMapCentralWidth = centralWidth;
	}


	public int getConfigTreeMapCentralLength()
	{
		return configTreeMapCentralLength;
	}


	public void setConfigTreeMapCentralLength(int centralLength)
	{
		this.configTreeMapCentralLength = centralLength;
	}


	public int getConfigTreeMapX()
	{
		return configTreeMapX;
	}


	public void setConfigTreeMapX(int x)
	{
		this.configTreeMapX = x;
	}


	public int getConfigTreeMapY()
	{
		return configTreeMapY;
	}


	public void setConfigTreeMapY(int y)
	{
		this.configTreeMapY = y;
	}


	public int getConfigTreeMapX1()
	{
		return configTreeMapX1;
	}


	public void setConfigTreeMapX1(int x1)
	{
		this.configTreeMapX1 = x1;
	}


	public int getConfigTreeMapX2()
	{
		return configTreeMapX2;
	}


	public void setConfigTreeMapX2(int x2)
	{
		this.configTreeMapX2 = x2;
	}


	public int getConfigTreeMapY1()
	{
		return configTreeMapY1;
	}


	public void setConfigTreeMapY1(int y1)
	{
		this.configTreeMapY1 = y1;
	}


	public int getConfigTreeMapY2()
	{
		return configTreeMapY2;
	}


	public void setConfigTreeMapY2(int y2)
	{
		this.configTreeMapY2 = y2;
	}
	
	public int getConfigTreeMapDomainWidth()
	{
		return configTreeMapDomainWidth;
	}


	public void setConfigTreeMapDomainWidth(int configTreeMapDomainWidth)
	{
		this.configTreeMapDomainWidth = configTreeMapDomainWidth;
	}
	
	public int getConfigTreeMapDomainLength()
	{
		return configTreeMapDomainLength;
	}


	public void setConfigTreeMapDomainLength(int configTreeMapDomainLength)
	{
		this.configTreeMapDomainLength = configTreeMapDomainLength;
	}


	public double getConfigTreeMapGridSize()
	{
		return configTreeMapGridSize;
	}


	public void setConfigTreeMapGridSize(double configTreeMapGridSize)
	{
		this.configTreeMapGridSize = configTreeMapGridSize;
	}


	public int getConfigIsimus()
	{
		return configIsimus;
	}


	public void setConfigIsimus(int configIsimus)
	{
		this.configIsimus = configIsimus;
	}


	public int getConfigTreeMapHighestBuildingHeight()
	{
		return configTreeMapHighestBuildingHeight;
	}


	public void setConfigTreeMapHighestBuildingHeight(
			int configTreeMapHighestBuildingHeight)
	{
		this.configTreeMapHighestBuildingHeight = configTreeMapHighestBuildingHeight;
	}


	public String getConfigTreeMapTrees()
	{
		return configTreeMapTrees;
	}


	public void setConfigTreeMapTrees(String configTreeMapTrees)
	{
		this.configTreeMapTrees = configTreeMapTrees;
	}




	public int getConfigTreeMapUsingDiffShading()
	{
		return configTreeMapUsingDiffShading;
	}


	public void setConfigTreeMapUsingDiffShading(int usingDiffShading)
	{
		this.configTreeMapUsingDiffShading = usingDiffShading;
	}
		
	public String getConfigTreezAllhtcrownValuesComment()
	{
		return configTreezAllhtcrownValuesComment;
	}


	public void setConfigTreezAllhtcrownValuesComment(String configTreezAllhtcrownValuesComment)
	{
		this.configTreezAllhtcrownValuesComment = configTreezAllhtcrownValuesComment;
	}
	
	public String getConfigTreezAlldiamValuesComment()
	{
		return configTreezAlldiamValuesComment;
	}


	public void setConfigTreezAlldiamValuesComment(String configTreezAlldiamValuesComment)
	{
		this.configTreezAlldiamValuesComment = configTreezAlldiamValuesComment;
	}


	public String getConfigTreezAllhttrunkValuesComment()
	{
		return configTreezAllhttrunkValuesComment;
	}


	public void setConfigTreezAllhttrunkValuesComment(String configTreezAllhttrunkValuesComment)
	{
		this.configTreezAllhttrunkValuesComment = configTreezAllhttrunkValuesComment;
	}


	public String getConfigTreezAlllareaValuesComment()
	{
		return configTreezAlllareaValuesComment;
	}


	public void setConfigTreezAlllareaValuesComment(String configTreezAlllareaValuesComment)
	{
		this.configTreezAlllareaValuesComment = configTreezAlllareaValuesComment;
	}


	public double getConfigPhybbmgsg0()
	{
		return configPhybbmgsg0;
	}


	public void setConfigPhybbmgsg0(double configPhybbmgsg0)
	{
		this.configPhybbmgsg0 = configPhybbmgsg0;
	}


	public double getConfigPhybbmgsg1()
	{
		return configPhybbmgsg1;
	}


	public void setConfigPhybbmgsg1(double configPhybbmgsg1)
	{
		this.configPhybbmgsg1 = configPhybbmgsg1;
	}


	public double getConfigPhybbmgsnsides()
	{
		return configPhybbmgsnsides;
	}


	public void setConfigPhybbmgsnsides(double configPhybbmgsnsides)
	{
		this.configPhybbmgsnsides = configPhybbmgsnsides;
	}


	public double getConfigPhybbmgswleaf()
	{
		return configPhybbmgswleaf;
	}


	public void setConfigPhybbmgswleaf(double configPhybbmgswleaf)
	{
		this.configPhybbmgswleaf = configPhybbmgswleaf;
	}


	public double getConfigPhybbmgsgamma()
	{
		return configPhybbmgsgamma;
	}


	public void setConfigPhybbmgsgamma(double configPhybbmgsgamma)
	{
		this.configPhybbmgsgamma = configPhybbmgsgamma;
	}


	public int getConfigPhyjmaxconnolayers()
	{
		return configPhyjmaxconnolayers;
	}


	public void setConfigPhyjmaxconnolayers(int configPhyjmaxconnolayers)
	{
		this.configPhyjmaxconnolayers = configPhyjmaxconnolayers;
	}


	public int getConfigPhyjmaxconnoages()
	{
		return configPhyjmaxconnoages;
	}


	public void setConfigPhyjmaxconnoages(int configPhyjmaxconnoages)
	{
		this.configPhyjmaxconnoages = configPhyjmaxconnoages;
	}


	public int getConfigPhyjmaxconnodates()
	{
		return configPhyjmaxconnodates;
	}


	public void setConfigPhyjmaxconnodates(int configPhyjmaxconnodates)
	{
		this.configPhyjmaxconnodates = configPhyjmaxconnodates;
	}


	public double getConfigPhyjmaxvalues()
	{
		return configPhyjmaxvalues;
	}


	public void setConfigPhyjmaxvalues(double configPhyjmaxvalues)
	{
		this.configPhyjmaxvalues = configPhyjmaxvalues;
	}


	public String getConfigPhyjmaxdates()
	{
		return configPhyjmaxdates;
	}


	public void setConfigPhyjmaxdates(String configPhyjmaxdates)
	{
		this.configPhyjmaxdates = configPhyjmaxdates;
	}


	public int getConfigPhyvcmaxconnolayers()
	{
		return configPhyvcmaxconnolayers;
	}


	public void setConfigPhyvcmaxconnolayers(int configPhyvcmaxconnolayers)
	{
		this.configPhyvcmaxconnolayers = configPhyvcmaxconnolayers;
	}


	public int getConfigPhyvcmaxconnoages()
	{
		return configPhyvcmaxconnoages;
	}


	public void setConfigPhyvcmaxconnoages(int configPhyvcmaxconnoages)
	{
		this.configPhyvcmaxconnoages = configPhyvcmaxconnoages;
	}


	public int getConfigPhyvcmaxconnodates()
	{
		return configPhyvcmaxconnodates;
	}


	public void setConfigPhyvcmaxconnodates(int configPhyvcmaxconnodates)
	{
		this.configPhyvcmaxconnodates = configPhyvcmaxconnodates;
	}


	public double getConfigPhyvcmaxvalues()
	{
		return configPhyvcmaxvalues;
	}


	public void setConfigPhyvcmaxvalues(double configPhyvcmaxvalues)
	{
		this.configPhyvcmaxvalues = configPhyvcmaxvalues;
	}


	public String getConfigPhyvcmaxdates()
	{
		return configPhyvcmaxdates;
	}


	public void setConfigPhyvcmaxdates(String configPhyvcmaxdates)
	{
		this.configPhyvcmaxdates = configPhyvcmaxdates;
	}


	public double getConfigPhyjmaxparstheta()
	{
		return configPhyjmaxparstheta;
	}


	public void setConfigPhyjmaxparstheta(double configPhyjmaxparstheta)
	{
		this.configPhyjmaxparstheta = configPhyjmaxparstheta;
	}


	public double getConfigPhyjmaxparseavj()
	{
		return configPhyjmaxparseavj;
	}


	public void setConfigPhyjmaxparseavj(double configPhyjmaxparseavj)
	{
		this.configPhyjmaxparseavj = configPhyjmaxparseavj;
	}


	public double getConfigPhyjmaxparsedvj()
	{
		return configPhyjmaxparsedvj;
	}


	public void setConfigPhyjmaxparsedvj(double configPhyjmaxparsedvj)
	{
		this.configPhyjmaxparsedvj = configPhyjmaxparsedvj;
	}


	public double getConfigPhyjmaxparsdelsj()
	{
		return configPhyjmaxparsdelsj;
	}


	public void setConfigPhyjmaxparsdelsj(double configPhyjmaxparsdelsj)
	{
		this.configPhyjmaxparsdelsj = configPhyjmaxparsdelsj;
	}


	public double getConfigPhyjmaxparsajq()
	{
		return configPhyjmaxparsajq;
	}


	public void setConfigPhyjmaxparsajq(double configPhyjmaxparsajq)
	{
		this.configPhyjmaxparsajq = configPhyjmaxparsajq;
	}


	public double getConfigPhyvcmaxparseavc()
	{
		return configPhyvcmaxparseavc;
	}


	public void setConfigPhyvcmaxparseavc(double configPhyvcmaxparseavc)
	{
		this.configPhyvcmaxparseavc = configPhyvcmaxparseavc;
	}


	public double getConfigPhyvcmaxparsedvc()
	{
		return configPhyvcmaxparsedvc;
	}


	public void setConfigPhyvcmaxparsedvc(double configPhyvcmaxparsedvc)
	{
		this.configPhyvcmaxparsedvc = configPhyvcmaxparsedvc;
	}


	public int getConfigPhyrdconnolayers()
	{
		return configPhyrdconnolayers;
	}


	public void setConfigPhyrdconnolayers(int configPhyrdconnolayers)
	{
		this.configPhyrdconnolayers = configPhyrdconnolayers;
	}


	public int getConfigPhyrdconnoages()
	{
		return configPhyrdconnoages;
	}


	public void setConfigPhyrdconnoages(int configPhyrdconnoages)
	{
		this.configPhyrdconnoages = configPhyrdconnoages;
	}


	public int getConfigPhyrdconnodates()
	{
		return configPhyrdconnodates;
	}


	public void setConfigPhyrdconnodates(int configPhyrdconnodates)
	{
		this.configPhyrdconnodates = configPhyrdconnodates;
	}


	public double getConfigPhyrdvalues()
	{
		return configPhyrdvalues;
	}


	public void setConfigPhyrdvalues(double configPhyrdvalues)
	{
		this.configPhyrdvalues = configPhyrdvalues;
	}


	public String getConfigPhyrddates()
	{
		return configPhyrddates;
	}


	public void setConfigPhyrddates(String configPhyrddates)
	{
		this.configPhyrddates = configPhyrddates;
	}


	public double getConfigPhyrdparsrtemp()
	{
		return configPhyrdparsrtemp;
	}


	public void setConfigPhyrdparsrtemp(double configPhyrdparsrtemp)
	{
		this.configPhyrdparsrtemp = configPhyrdparsrtemp;
	}


	public double getConfigPhyrdparsq10f()
	{
		return configPhyrdparsq10f;
	}


	public void setConfigPhyrdparsq10f(double configPhyrdparsq10f)
	{
		this.configPhyrdparsq10f = configPhyrdparsq10f;
	}


	public double getConfigPhyrdparsdayresp()
	{
		return configPhyrdparsdayresp;
	}


	public void setConfigPhyrdparsdayresp(double configPhyrdparsdayresp)
	{
		this.configPhyrdparsdayresp = configPhyrdparsdayresp;
	}


	public double getConfigPhyrdparseffyrf()
	{
		return configPhyrdparseffyrf;
	}


	public void setConfigPhyrdparseffyrf(double configPhyrdparseffyrf)
	{
		this.configPhyrdparseffyrf = configPhyrdparseffyrf;
	}


	public int getConfigPhyslaconnoages()
	{
		return configPhyslaconnoages;
	}


	public void setConfigPhyslaconnoages(int configPhyslaconnoages)
	{
		this.configPhyslaconnoages = configPhyslaconnoages;
	}


	public int getConfigPhyslaconnodates()
	{
		return configPhyslaconnodates;
	}


	public void setConfigPhyslaconnodates(int configPhyslaconnodates)
	{
		this.configPhyslaconnodates = configPhyslaconnodates;
	}


	public int getConfigPhyslaconnolayers()
	{
		return configPhyslaconnolayers;
	}


	public void setConfigPhyslaconnolayers(int configPhyslaconnolayers)
	{
		this.configPhyslaconnolayers = configPhyslaconnolayers;
	}


	public double getConfigPhyslavalues()
	{
		return configPhyslavalues;
	}


	public void setConfigPhyslavalues(double configPhyslavalues)
	{
		this.configPhyslavalues = configPhyslavalues;
	}


	public String getConfigPhysladates()
	{
		return configPhysladates;
	}


	public void setConfigPhysladates(String configPhysladates)
	{
		this.configPhysladates = configPhysladates;
	}
	public String getConfigPhySlamaxValuesComment()
	{
		return configPhySlamaxValuesComment;
	}


	public void setConfigPhySlamaxValuesComment(String configPhySlamaxValuesComment)
	{
		this.configPhySlamaxValuesComment = configPhySlamaxValuesComment;
	}
	public String getConfigPhyMedlyng1Comment()
	{
		return configPhyMedlyng1Comment;
	}


	public void setConfigPhyMedlyng1Comment(String configPhyMedlyng1Comment)
	{
		this.configPhyMedlyng1Comment = configPhyMedlyng1Comment;
	}


	public String getConfigPhyMedlynWleafComment()
	{
		return configPhyMedlynWleafComment;
	}


	public void setConfigPhyMedlynWleafComment(String configPhyMedlynWleafComment)
	{
		this.configPhyMedlynWleafComment = configPhyMedlynWleafComment;
	}


	public String getConfigPhyMedlynGammaComment()
	{
		return configPhyMedlynGammaComment;
	}


	public void setConfigPhyMedlynGammaComment(String configPhyMedlynGammaComment)
	{
		this.configPhyMedlynGammaComment = configPhyMedlynGammaComment;
	}


	public String getConfigPhyRdmaxValuesComment()
	{
		return configPhyRdmaxValuesComment;
	}


	public void setConfigPhyRdmaxValuesComment(String configPhyRdmaxValuesComment)
	{
		this.configPhyRdmaxValuesComment = configPhyRdmaxValuesComment;
	}


	public String getConfigPhyMedlyng0Comment()
	{
		return configPhyMedlyng0Comment;
	}


	public void setConfigPhyMedlyng0Comment(String configPhyMedlyng0Comment)
	{
		this.configPhyMedlyng0Comment = configPhyMedlyng0Comment;
	}
	public String getConfigPhyJmaxValuesComment()
	{
		return configPhyJmaxValuesComment;
	}


	public void setConfigPhyJmaxValuesComment(String configPhyJmaxValuesComment)
	{
		this.configPhyJmaxValuesComment = configPhyJmaxValuesComment;
	}


	public String getConfigPhyVcmaxValuesComment()
	{
		return configPhyVcmaxValuesComment;
	}


	public void setConfigPhyVcmaxValuesComment(String configPhyVcmaxValuesComment)
	{
		this.configPhyVcmaxValuesComment = configPhyVcmaxValuesComment;
	}
	public String getConfigPhyJmaxparsEavjComment()
	{
		return configPhyJmaxparsEavjComment;
	}


	public void setConfigPhyJmaxparsEavjComment(String configPhyJmaxparsEavjComment)
	{
		this.configPhyJmaxparsEavjComment = configPhyJmaxparsEavjComment;
	}


	public String getConfigPhyJmaxparsEdvjComment()
	{
		return configPhyJmaxparsEdvjComment;
	}


	public void setConfigPhyJmaxparsEdvjComment(String configPhyJmaxparsEdvjComment)
	{
		this.configPhyJmaxparsEdvjComment = configPhyJmaxparsEdvjComment;
	}


	public String getConfigPhyVcmaxparsEavcComment()
	{
		return configPhyVcmaxparsEavcComment;
	}


	public void setConfigPhyVcmaxparsEavcComment(String configPhyVcmaxparsEavcComment)
	{
		this.configPhyVcmaxparsEavcComment = configPhyVcmaxparsEavcComment;
	}
	public String getConfigPhyJmaxparsAjqComment()
	{
		return configPhyJmaxparsAjqComment;
	}


	public void setConfigPhyJmaxparsAjqComment(String configPhyJmaxparsAjqComment)
	{
		this.configPhyJmaxparsAjqComment = configPhyJmaxparsAjqComment;
	}
}
