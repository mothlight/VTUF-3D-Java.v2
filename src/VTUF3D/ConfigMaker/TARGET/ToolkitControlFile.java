package VTUF3D.ConfigMaker.TARGET;

import VTUF3D.ConfigMaker.Common;

public class ToolkitControlFile
{

	Common common = new Common();
	public static final String linefeed = "\n";
	
	public String getControlFile()
	{
		
//		String file = "#---------------------------------------------------------------------------------------------------------" + '\n' + 
//				"####### "
//				+ configTitle
//				+ " Main Control File #######" + '\n' + 
//				"##### 30m GRID SITES   #####" + '\n' + 
//				"" + '\n' + 
//				"#---------------------------------------------------------------------------------------------------------" + '\n' + 
//				"####### INPUTS #######" + '\n' + 
//				"site_name = \""
//				+ getSiteName()
//				+ "\"									             # site name (string)" + '\n' + 
//				"run_name   = \""
//				+ getRunName()
//				+ "\"                                               # run name (string)" + '\n' + 
//				"inpt_met_file =  \""
//				+ getMetFileName()
//				+ ".csv\"				       # input meteorolgical file (i.e. forcing file)" + '\n' + 
//				"inpt_lc_file  =  \""
//				+ getLcFileName()
//				+ ".csv\"                                    #  input land cover data file" + '\n' + 
//				"date_fmt = '%d/%m/%Y %H:%M'                                                      # format of datetime in input met files" + '\n' + 
//				"timestep = "
//				+ getTimestep()
//				+ "                                                                    # time step (minutes)" + '\n' + 
//				"#---------------------------------------------------------------------------------------------------------" + '\n' + 
//				"# dates " + '\n' + 
//				"#---------------------------------------------------------------------------------------------------------" + '\n' + 
//				"date1A="
//				+ getDate1a()
//				+ "									# year,month,day,hour	#start date for simulation (should be a minimum of 24 hours prior to date1)" + '\n' + 
//				"date1 ="
//				+ getDate1()
//				+ "									# year,month,day,hour	## the date/time for period of interest (i.e. before this will not be saved)" + '\n' + 
//				"date2 ="
//				+ getDate2()
//				+ " 									# year,month,day,hour	# end date for validation period										             " + '\n' + 
//				"######################" + '\n' + 
//				"" + '\n' + 
//				"##### Validation Info ####" + '\n' + 
//				"val_ta = 'N'                                                                    ## generate validation plots for Ta?" + '\n' + 
//				"val_ts = 'N'                                                                    ## generate validation plots for Ts?" + '\n' + 
//				"gis_plot = 'N'                                                                  ## generated GIS validation plots?" + '\n' + 
//				"inpt_obs_file =  'Preston_obs_30min.csv'							      ## oberved AWS data (for validation)" + '\n' + 
//				"inpt_grid_file = '30mGrid.shp'                                                  ## input grid shapefile" + '\n' + 
//				"radius=30m                                                                      ## grid resoultion " + '\n' + 
//				"date1Ts1=2011,2,16,14									            ## year,month,day,hour # start date/time for obs Ts data " + '\n' + 
//				"date1Ts2=2011,2,16,16									            ## year,month,day,hour # end   date/time for obs Ts data" + '\n' + 
//				"date2Ts1=2011,2,15,2									           ## year,month,day,hour # start date/time for obs Ts data " + '\n' + 
//				"date2Ts2=2011,2,15,3									           ## year,month,day,hour # end   date/time for obs Ts data" + '\n' + 
//				"Ts_prd1 = 'day'										           # names for Ts test periods" + '\n' + 
//				"Ts_prd2 = 'night'						                                  # names for Ts test periods" + '\n' + 
//				"STa = '01','02','03','04','05','06','07','08','09','10','11','12','13','16','17','18','19','20','21','22','23','24','25','26','27','28','29','30'					                                  ## names for Ts test periods" + '\n' + 
//				"########################" + '\n' + 
//				"" + '\n' + 
//				"" + '\n' + 
//				"mod_ldwn = '"
//				+ getModLdwn()
//				+ "'                                                      # used modelled ldown" + '\n' + 
//				"use_obs_ws   	= '"
//				+ getUsObsWs()
//				+ "'									# trigger to activate use of observed ws inputs" + '\n' ;
		
		
		
		String file = "#---------------------------------------------------------------------------------------------------------" + linefeed + 
			"####### Urban Plumber "
			+ getSiteName()
			+ " Main Control File #######" + linefeed + 
			"" + linefeed + 
			"#---------------------------------------------------------------------------------------------------------" + linefeed + 
			"####### INPUTS #######" + linefeed + 
			"site_name="
			+ getSiteName()
			+ "				     # site name (string)" + linefeed + 
			"run_name="
			+ getSiteName()
			+ "                              # run name (string)" + linefeed + 
			"inpt_met_file="
			+ getMetFileName()
			+ "	# input meteorolgical file (i.e. forcing file)" + linefeed + 
			"inpt_lc_file="
			+ getLcFileName()
			+ "                       #  input land cover data file" + linefeed + 
			"output_dir="
			+ getOutputDir()
			+ "                # directory output will be saved in" + linefeed + 
			"date_fmt=%d/%m/%Y %H:%M                              # format of datetime in input met files" + linefeed + 
			"timestep=1800S                                       # define in seconds " + linefeed + 
			"include roofs=Y   " + linefeed + 
			"#---------------------------------------------------------------------------------------------------------" + linefeed + 
			"# dates " + linefeed + 
			"#---------------------------------------------------------------------------------------------------------" + linefeed + 
			"SpinUp="
			+ getDate1a()
			+ "					# year,month,day,hour	#start date for simulation (should be a minimum of 24 hours prior to date1)" + linefeed + 
			"StartDate ="
			+ getDate1()
			+ "				# year,month,day,hour	## the date/time for period of interest (i.e. before this will not be saved)" + linefeed + 
			"EndDate ="
			+ getDate2()
			+ " 					# year,month,day,hour	# end date for validation period" + linefeed + 
			"######################" + linefeed + 
			"" + linefeed + 
			"" + linefeed + 
			"individualNetcdfFiles=false" + linefeed + 
			"mod_ldwn=N             # use modelled ldown" + linefeed + 
			"lat="
			+ getLat()
			+ linefeed + 
			"domainDim=1,1" + linefeed + 
			"latEdge="
			+ getLat()
			+ linefeed + 
			"lonEdge="
			+ getLon() 
			+ linefeed + 
			"latResolution=.00004294" + linefeed + 
			"lonResolution=.0021849" + linefeed + 
			"### disabled output options are Fid,Utb,TsurfWall,TsurfCan,TsurfHorz,Ucan,Utb,Tsurfwall,TsurfCan,TsurfHorz,Ucan,Pet" + linefeed + 
			"disableOutput=Utb,Ucan,Pet" + linefeed ;
		
		//String runDirectoryBase = "/home/kerryn/Documents/Work/Toolkit2-Runs/";
		
//		String runDir = runDirectoryBase
//				+ runName
//				+ "/controlfiles/"
//				+ runName
//				+ "/";
//		common.createDirectory(runDir);
//		common.writeFile(file, runDir + "/" + runName + ".txt" );	
		
		return file;
	}
	
//	private String configTitle ="CoM Gipps St";
//	private String siteName="CoMGipps";
//	private String runName="CoMGipps-single";
//	private String metFileName="CoMGippsStOpn_grid";
//	private String lcFileName="100x100m-stations_LC";
//	private String timestep="30";
//	private String date1a="2004,2,14,0";
//	private String date1="2004,2,15,0";
//	private String date2="2004,2,16,18";
	
	private String configTitle ;
	private String siteName;
	private String runName;
	private String metFileName;
	private String lcFileName;
	private String timestep;
	private String date1a;
	private String date1;
	private String date2;
	private String modLdwn;
	private String usObsWs;
	private double lat;
	private double lon;
	private String outputDir;


	
	public ToolkitControlFile()
	{
		super();
		setModLdwn("N");
		setUsObsWs("N");
		
	}

	public String getConfigTitle()
	{
		return configTitle;
	}

	public void setConfigTitle(String configTitle)
	{
		this.configTitle = configTitle;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getRunName()
	{
		return runName;
	}

	public void setRunName(String runName)
	{
		this.runName = runName;
	}

	public String getMetFileName()
	{
		return metFileName;
	}

	public void setMetFileName(String metFileName)
	{
		this.metFileName = metFileName;
	}

	public String getLcFileName()
	{
		return lcFileName;
	}

	public void setLcFileName(String lcFileName)
	{
		this.lcFileName = lcFileName;
	}

	public String getTimestep()
	{
		return timestep;
	}

	public void setTimestep(String timestep)
	{
		this.timestep = timestep;
	}

	public String getDate1a()
	{
		return date1a;
	}

	public void setDate1a(String date1a)
	{
		this.date1a = date1a;
	}

	public String getDate1()
	{
		return date1;
	}

	public void setDate1(String date1)
	{
		this.date1 = date1;
	}

	public String getDate2()
	{
		return date2;
	}

	public void setDate2(String date2)
	{
		this.date2 = date2;
	}

	public String getModLdwn()
	{
		return modLdwn;
	}

	public void setModLdwn(String modLdwn)
	{
		this.modLdwn = modLdwn;
	}

	public String getUsObsWs()
	{
		return usObsWs;
	}

	public void setUsObsWs(String usObsWs)
	{
		this.usObsWs = usObsWs;
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

	public String getOutputDir()
	{
		return outputDir;
	}

	public void setOutputDir(String outputDir)
	{
		this.outputDir = outputDir;
	}


	
}
