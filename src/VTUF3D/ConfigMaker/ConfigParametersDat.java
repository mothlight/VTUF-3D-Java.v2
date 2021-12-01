package VTUF3D.ConfigMaker;

public class ConfigParametersDat
{
	Common common = new Common();
	public final String FILENAME_PREFIX = "parameters";
	public final String FILENAME_PREFIX_RAD_ONLY = "parameters_Radiation";
	public final String FILENAME_SUFFUX = ".dat";

	
	private String runDirectory;
	private String filename ;
	private String fileText;
	private int year;
	private String runPrefix;
	
	
	// model/integration parameters (further integration parameters are found
	// in the atmospheric forcing file (forcing.dat)
	// View Factor Calculations (or read in from file) (0,1) 'must turn on calculation of view factors (i.e.vfcalc=1) if more than one lambdap or H/L ratio is chosen'
	// VFCALC: if vfcalc=0 (means no vf calcs) the file "vfinfo.dat" with
	// the correct view factor info must be in the run directory;
	// vfcalc=1 means exact plane parallel view factor calcs;
	// vfcalc=2 means contour integration view factor calcs; ** note: vfcalc
	// must be 1 or 2 if looping through lambdap is turned on (i.e. numlp>1)
	private String vfcalc; 
	// yd - day of year
	// YD: julian day (affects diurnal evolution of solar angle)	
	private int yd;
	// deltat - time step
	// DELTAT: (seconds) input timestep - will be reduced by the model if needed
	// - a large number (e.g. 999.) will ensure that the model controls the
	// timestep (recommended) - the model is optimized to find the largest
	// timestep (therefore the fastest run time) that is still stable at any
	// given time during the simulation	
	private String deltat;
	
	// outpt_tm time interval
	// OUTPT_TM: (hours) how often model outputs are written
	private String outpt_tm;
	
	//TTHRESHOLD: (deg C) accuracy of Newton's method in solving the patch
	//energy balances - the smaller the number the higher the accuracy
	private String Tthreshold;
		
	// facet_out - write out intra-facet (patch) surface temperatures
	//FACET_OUT: 'T' = write out individual patch sky view factors, surface
	//temperatures, and absorbed/reflected solar radiation flux density,
	//organized by facet and location in the central urban unit;
	//'F' = do not write out these files
	//MATLAB_OUT: 'T' = write out files containing patch vertices and patch faces,
	//as well as patch surface temperature (Tsfc), patch brightness temperature
	//(Tbright), and patch net shortwave (Kstar); these three quantities can then
	//be easily visualized in Matlab with the 'patch' command; 'F' - do not write
	//these files
	//SUM_OUT: 'T' = write out individual patch surface temperatures (Tsfc) and
	//patch brightness temperature (Tbright) organized by TUF3D loop order (facet
	//direction varies slowest, then z, then y, then x) - so as to be easily read
	//in and assigned to the equivalent geometry (in the SUM model for example);
	//'F' = do not write out these files
	private String facet_out,matlab_out,sum_out;	
	
	
	// radiative parameters
	// DALB: (W/m2) accuracy to which the effective albedo/emissivity of the
	//canyon/cavity portion of the domain will be calculated; that is,
	//reflections will continue until the change in albedo between timesteps
	//is less than "dalb"	
	private String dalb;
	// albr,albs,albw - albedo (roof, street, wall) ?
	// ALBR,ALBS,ALBW: albedo of roof, street, and wall patches, respectively
	private String albr,albs,albw;
	// emisr,emiss,emisw - emissivity (roof, street, wall)
	//EMISR,EMISS,EMISW: emissivity of roof, street, and wall patches, respectively
	private String emisr,emiss,emisw;
	// cloudtype - 
	//    0 clear	      
	//    1 cirrus
	//    2 cirrostratus
	//    3  altocumulus
	//    4 altostratus	      
	//    7 cumulonimbus	       
	//    5 stratocumulus/cumulus	       
	//    6 thick stratus (Ns?)	
	//CLOUDTYPE: For shortwave and longwave radiation; 0=clear, and higher values
	//		(to a maximum of 7) are progressively thicker clouds; 1=cirrus, 2= cirrostratus,
	//		3=altocumulus, 4=altostratus, 5=cumulonimbus, 6=stratocumulus, 7=thick stratus
	//		(Ns?) - all assumed to be 100% cloud cover
	private String cloudtype;
	
	// conduction parameters
	// INTCOND: 1=full conduction between deepest layer and ground/building
	//interior, 0=no such conduction...values between 0 and 1 are permitted
	//INTRESIST: resistance to energy exchange between deepest building layers
	//and building interior air (0.123 m2*K/W approximates combined conductive,
	//		radiative, and convective exchange - Masson et al. 2002)
    private String IntCond,Intresist;
	// uc - the explicit/implicit/Crank-Nicholson control (uc=1 is implicit, uc=0 is Forward Euler or explicit)
    // UC: degree of implicitness of solution of tridiagonal matrix for
    //conduction (1=fully implicit, 0=fully explicit, 0.5 is Crank-Nicholson and
    //		is the most accurate, and is still stable - UC<0.5 may be unstable)	
    private String uc;
    // NUMLAYERS: number of layers for conduction in roofs, roads, walls
    private int numlayers;
    //  do k=1,numlayers
    //  read(299,*)thickr(k),lambdar(k),htcapr(k)
    // THICKR(k),LAMBDAR(k),HTCAPR(k): thickness (m), thermal conductivity
    //(W/m/K), and volumetric heat capacity (J/m3/K) of roof layer k, where k=1
    //		is the surface layer, and k=numlayers is the deepest layer
    private String[] thickr;
    private String[] lambdar;
    private String[] htcapr;
    //  do k=1,numlayers
    //  read(299,*)thicks(k),lambdas(k),htcaps(k)
    // THICKS(k),LAMBDAS(k),HTCAPS(k): same as for roofs, but for roads    
    private String[] thicks;
    private String[] lambdas;
    private String[] htcaps;
    //    do k=1,numlayers
    //     read(299,*)thickw(k),lambdaw(k),htcapw(k)
    // THICKW(k),LAMBDAW(k),HTCAPW(k): same as for roofs, but for walls
    private String[] thickw;
    private String[] lambdaw;
    private String[] htcapw;
    
    //  convection parameters
    // Z0: input town (i.e. overall) roughness length - model calculates it
    //according to Macdonald (1998) if values is less than 0
    //LAMBDAF: input town (i.e. overall) frontal area to plan area ratio
    //- model calculates it if values is less than 0, and the model formula
    //depends on wind direction
    //ZROOFFRC: (m) the height above roof level for variables (temp, wind) used
    //to in forcing convection from roofs - model calculates it if the value
    //is negative 
    private String z0,lambdaf,zrooffrc;
    
    // Z0ROOFM,Z0ROADM: (m) roof and road momentum roughness lengths, respectively
    //Z0ROOFH,Z0ROADH: (m) roof and road thermal roughness lengths, respectively
    //- if values are negative model defaults to 1/200 of corresponding
    //momentum roughness lengths - **note that ratio of momentum to thermal
    //roughness lengths should never be smaller than 1/200! **
    //MOH: ratio of momentum to thermal roughness lengths for transfer from
    //individual *surfaces* only
    //RW: wall roughness relative to concrete (rw=1)
    private String z0roofm,z0roadm,z0roofh,z0roadh,moh,rw;
        
    // domain geometry
    //  BUILDHT_M: (m) height of buildings (mean height, if there is variation)
    // ZREF: (m) height of measured forcing data (air temperature, wind speed, etc)
    // - must be greater than 'buildht_m'
    public String buildht_m,zref;
    // MINRES: minimum resolution of any given facet (i.e. roof, road, or wall)
    //- recommended value is 4 or greater (6 is ideal); resolution of all
    //other facets will be adjusted to maintain all geometric ratios while
    //ensuring that all facets have a minimum of 'minres' patches across them
    //in both dimensions (NOTE: THIS IS THE KEY PARAMETER THAT CONTROLS THE
    //ACCURACY OF THE RADIATION SCHEME VS. THE COMPUTATIONAL EXPENSE -
    //minres = 2 will give a quick estimate, minres = 4 tends to be a reasonable
    //balance between speed and accuracy, and minres = 6 tends to give very
    //accurate results but can be very computationally expensive and can require 
    //a lot of memory, relative to the speed and memory of a typical desktop;
    //it is also useful to remember that the minimum resolution for solar
    //radiation absorption is effectively 2*minres - see the BLM paper for an
    //explanation)
    public String minres;

    // initial temperatures
    //TSFCR,TSFCS,TSFCW: (deg C) initial surface temperatures (roofs, roads and
    //		walls, respectively)   
    public String Tsfcr,Tsfcs,Tsfcw;
    //		TINTW: (deg C) constant building internal air temperature (base of roofs
    //		and walls)
    //		TINTS: (deg C) constant deep-ground temperature (base of roads)
    //		TFLOOR: (deg C) constant building internal floor temperature (affects
    //		building internal temperature slightly)
    //		TBUILD_MIN: (deg C) minimum indoor temperature permitted. If this
    //		temperature is sufficiently high relative to the ambient temperature, it
    //		will simulate space heating.    
    public String Tintw,Tints,Tfloor,Tbuild_min;

    // loop parameters
    // (for multiple simulations with the same forcing data,
    //		but with different street orientations, latitudes, lambdap ratios, and
    //		combinations thereof)
    //		STROR_IN,STRORINT,STRORMAX: (degrees from alignment with cardinal
    //		directions) initial, loop interval, and final street orientation
    //		orientation    
	private String stror_in,strorint,strormax;
	//	XLAT_IN,XLATINT,XLATMAX: (degrees) initial, loop interval, and final latitude
	private String xlat_in,xlatint,xlatmax;
	//		NUMLP: number of lambdap ratios to loop through
	//		LPIN(k): the lambdap ratios (from k=1 to k=numlp)
	//		NUMBHBL: number of bh (building height) to bl (building width) ratios to
	//		simulate for each lambdap
	//		BH_O_BL(k): the bh/bl ratios (from l=1 to l=numbhbl)	
	private int numlp;
	//    do k=1,numlp
	//     read(299,*)lpin(k)
	private String[] lpin;

	private int numbhbl;
	//    do l=1,numbhbl
	//     read(299,*)bh_o_bl(l)
	private String[] bh_o_bl;
	
	
	//read(299,*)yd,outpt_tm,start_time,end_time
	private double startTime;
	private double endTime;
	
	
    //read(299,*)lwv,swv
	private String lwv;
	private String swv;
    //read(299,*)frcKdn,frcLdn,frcTsfc
	private String frcKdn;
	private String frcLdn;
	private String frcTsfc;	
    //read(299,*)press
	private double press;
    
	//read(299,*)emiss_var
	private String emiss_var;
    //read(299,*)emissInter,emissNS,emissEW
	private double emissInter;
	private double emissNS;
	private double emissEW;
    
	//read(299,*)numres
	private int numres;
	//read(299,*)resin(k)
	private String[] resin;



	public ConfigParametersDat(String runDirectory, int year, String configNumber, int day
			)
	{
		super();

			setDefaults();

		
		this.runDirectory = runDirectory;		
		this.year = year;
		this.runPrefix = configNumber;			
		this.filename = generateFilename(runPrefix, year);	
		setYd(day);
		
	}
	
	private void setDefaults()
	{
		setVfcalc("1");
		setYd(172);
		setDeltat("999.");
		setOutpt_tm("1.0");
		setTthreshold("0.01");
		setFacet_out("T");
		setMatlab_out("F");
		setSum_out("F");
		setDalb("0.001");
		setAlbr("0.15");
		setAlbs("0.10");
		setAlbw("0.30");
		setEmisr("0.92");
		setEmiss("0.92");
		setEmisw("0.88");
		setCloudtype("0");
		setIntCond("1.0");
		setIntresist("0.123");
		setUc("0.5");
		setNumlayers(4);
		setThickr(new String[]{"0.020", "0.020", "0.010", "0.030"});
		setLambdar(new String[]{"1.20", "1.20", "0.03", "1.50"});		
		setHtcapr(new String[]{"1.75e6", "1.75e6", "0.10e6", "2.25e6"});		
		setThicks(new String[]{"0.020", "0.030", "0.100", "0.500"});
		setLambdas(new String[]{"0.80", "0.80", "0.90", "0.30"});
		setHtcaps(new String[]{"2.00e6", "2.00e6", "1.50e6", "1.25e6"});		
		setThickw(new String[]{"0.020", "0.030", "0.090", "0.020"});
		setLambdaw(new String[]{"1.10", "1.10", "1.10", "0.30"});
		setHtcapw(new String[]{"1.75e6", "2.00e6", "2.00e6", "1.50e6"});
		
		setZ0("-999.");
		setLambdaf("-999.");
		setZrooffrc("-999.");
		
		setZ0roofm("0.05"); 
		setZ0roadm("0.05");
		setZ0roofh("-999.");
		setZ0roadh("-999.");
		setMoh("200.");
		setRw("1.0");
		  
		setBuildht_m("15.0");
		setZref("30.0");
		setMinres("4");
	   
	    setTsfcr("18.0");
	    setTsfcs("23.0");
	    setTsfcw("22.0");
	    
	    setTintw("22.0");
	    setTints("20.0");
	    setTfloor("19.0");
	    setTbuild_min("15.0");
	    
	    setStror_in("0.01");
	    setStrorint("0.01");
	    setStrormax("0.01");
		setXlat_in ("-37.5");
		setXlatint("0.01");
		setXlatmax("-37.5");
		
		setNumlp(1);
		setLpin(new String[]{"0.31"});
		
		setNumbhbl(1);
		setBh_o_bl(new String[]{"1.5"});
				     
	}
	

	
	public void generateFile()
	{

			setFileText(generateConfigFileText(runPrefix, year));

		
	}
	
	private String generateConfigFileText(String runPrefix, int year)
	{
		StringBuilder st = new StringBuilder();
		
		st.append(getVfcalc() + '\n' );
		st.append(getYd() + " " + getDeltat() + " " + getOutpt_tm() + " " + getYear() + '\n' );
		st.append(getTthreshold() + '\n' );
		st.append(getFacet_out() + " " + getMatlab_out() + " " + getSum_out() +  '\n' );
		st.append(getDalb() + '\n' );
		st.append(getAlbr() + " " + getAlbs() + " " + getAlbw() +  '\n' );
		st.append(getEmisr() + " " + getEmiss() + " " + getEmisw() +  '\n' );
		st.append(getCloudtype() + '\n' );
		
		st.append(getIntCond() + " " + getIntresist() + '\n' );
		st.append(getUc() + " " + getNumlayers() + '\n' );
		int countr = 0;		
		for (String item : getThickr())
		{
			st.append(getThickr()[countr] + " " + getLambdar()[countr] + " " + getHtcapr()[countr] +  '\n' );
			countr ++;
		}		
		int counts = 0;		
		for (String item : getThicks())
		{
			st.append(getThicks()[counts] + " " + getLambdas()[counts] + " " + getHtcaps()[counts] +  '\n' );
			counts ++;
		}		
		int countw = 0;		
		for (String item : getThickw())
		{
			st.append(getThickw()[countw] + " " + getLambdaw()[countw] + " " + getHtcapw()[countw] +  '\n' );
			countw ++;
		}	

		st.append(getZ0() + " " + getLambdaf() + " " + getZrooffrc() +  '\n' );
		st.append(getZ0roofm() + " " + getZ0roadm() + " " + getZ0roofh() + " " + getZ0roadh() + " " + getMoh() + " " + getRw() +  '\n' );

		st.append(getBuildht_m() + " " + getZref()  +  '\n' );
		st.append(getMinres() +   '\n' );
		
		
		st.append(getTsfcr() + " " + getTsfcs() + " " + getTsfcw() +  '\n' );
		st.append(getTintw() + " " + getTints() + " " + getTfloor() + " " + getTbuild_min() + '\n' );
		
		
		st.append(getStror_in() + " " + getStrorint() + " " + getStrormax() +  '\n' );
		st.append(getXlat_in() + " " + getXlatint() + " " + getXlatmax() +  '\n' );

		st.append(getNumlp());
		st.append('\n' );
		for (String item : getLpin())
		{
			st.append(item +  '\n' );
		}
		st.append(getNumbhbl() );
		st.append( '\n' );
		for (String item : getBh_o_bl())
		{
			st.append(item +   '\n' );
		}
	    
		
		st.append(    '\n' );
		st.append(    '\n' );
		st.append(    '\n' );
		st.append(    '\n' );
		st.append(    '\n' );
		
		st.append(    "** note: H/W of an individual canyon can be calculated by:" + '\n' );
		st.append(    "   H/W = sqrt(lpin)*bh_o_bl/(1-sqrt(lpin))  (ALTHOUGH, the model will" + '\n' );
		st.append(    "   increase the resolution if it is too low for any given facet, and" + '\n' );
		st.append(    "   this may result in the lambdap, bh_o_bl, or H/W ratios that you want" + '\n' );
		st.append(    "   not being met exactly" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "Parameters in the order that they are read in:" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c model/integration parameters (further integration parameters are found" + '\n' );
		st.append(    "in the atmospheric forcing file (forcing.dat)" + '\n' );
		st.append(    "      read(299,*)vfcalc" + '\n' );
		st.append(    "      read(299,*)yd,deltat,outpt_tm,year,restart,restartTimestep" + '\n' );
		st.append(    "      read(299,*)Tthreshold" + '\n' );
		st.append(    "      read(299,*)facet_out,matlab_out,sum_out" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c radiative parameters" + '\n' );
		st.append(    "      read(299,*)dalb" + '\n' );
		st.append(    "      read(299,*)albr,albs,albw" + '\n' );
		st.append(    "      read(299,*)emisr,emiss,emisw" + '\n' );
		st.append(    "      read(299,*)cloudtype" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c conduction parameters" + '\n' );
		st.append(    "      read(299,*)IntCond,Intresist" + '\n' );
		st.append(    "      read(299,*)uc,numlayers" + '\n' );
		st.append(    "      do k=1,numlayers" + '\n' );
		st.append(    "       read(299,*)thickr(k),lambdar(k),htcapr(k)" + '\n' );
		st.append(    "      enddo" + '\n' );
		st.append(    "      do k=1,numlayers" + '\n' );
		st.append(    "       read(299,*)thicks(k),lambdas(k),htcaps(k)" + '\n' );
		st.append(    "      enddo" + '\n' );
		st.append(    "      do k=1,numlayers" + '\n' );
		st.append(    "       read(299,*)thickw(k),lambdaw(k),htcapw(k)" + '\n' );
		st.append(    "      enddo" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c convection parameters" + '\n' );
		st.append(    "      read(299,*)z0,lambdaf,zrooffrc" + '\n' );
		st.append(    "      read(299,*)z0roofm,z0roadm,z0roofh,z0roadh,moh,rw" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c domain geometry" + '\n' );
		st.append(    "      read(299,*)buildht_m,zref" + '\n' );
		st.append(    "      read(299,*)minres" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c initial temperatures" + '\n' );
		st.append(    "      read(299,*)Tsfcr,Tsfcs,Tsfcw" + '\n' );
		st.append(    "      read(299,*)Tintw,Tints,Tfloor,Tbuild_min" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c loop parameters" + '\n' );
		st.append(    "      read(299,*)stror_in,strorint,strormax" + '\n' );
		st.append(    "      read(299,*)xlat_in,xlatint,xlatmax" + '\n' );
		st.append(    "      read(299,*)numlp" + '\n' );
		st.append(    "      do k=1,numlp" + '\n' );
		st.append(    "       read(299,*)lpin(k)" + '\n' );
		st.append(    "      enddo" + '\n' );
		st.append(    "      read(299,*)numbhbl" + '\n' );
		st.append(    "      do l=1,numbhbl" + '\n' );
		st.append(    "       read(299,*)bh_o_bl(l)" + '\n' );
		st.append(    "      enddo" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "Explanation of the parameters:" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "* note: 'roads' and 'streets' are used interchangeably" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c model/integration parameters" + '\n' );
		st.append(    "VFCALC: if vfcalc=0 (means no vf calcs) the file \"vfinfo.dat\" with" + '\n' );
		st.append(    "the correct view factor info must be in the run directory;" + '\n' );
		st.append(    "vfcalc=1 means exact plane parallel view factor calcs;" + '\n' );
		st.append(    "vfcalc=2 means contour integration view factor calcs; ** note: vfcalc" + '\n' );
		st.append(    "must be 1 or 2 if looping through lambdap is turned on (i.e. numlp>1)" + '\n' );
		st.append(    "YD: julian day (affects diurnal evolution of solar angle)" + '\n' );
		st.append(    "DELTAT: (seconds) input timestep - will be reduced by the model if needed" + '\n' );
		st.append(    " - a large number (e.g. 999.) will ensure that the model controls the" + '\n' );
		st.append(    "timestep (recommended) - the model is optimized to find the largest" + '\n' );
		st.append(    "timestep (therefore the fastest run time) that is still stable at any" + '\n' );
		st.append(    "given time during the simulation" + '\n' );
		st.append(    "OUTPT_TM: (hours) how often model outputs are written" + '\n' );
		st.append(    "year: The year the simulation starts. If this is missing, the model defaults to 2004." + '\n' );		
		st.append(    "restart: New feature to allow restarted runs. If this is '1', it will restart at the restartTimestep. This and the next parameter is optional." + '\n' );
		st.append(    "restartTimestep: Which timestep to restart" + '\n' );		
		st.append(    "TTHRESHOLD: (deg C) accuracy of Newton's method in solving the patch" + '\n' );
		st.append(    "energy balances - the smaller the number the higher the accuracy" + '\n' );
		st.append(    "FACET_OUT: 'T' = write out individual patch sky view factors, surface" + '\n' );
		st.append(    "temperatures, and absorbed/reflected solar radiation flux density," + '\n' );
		st.append(    "organized by facet and location in the central urban unit;" + '\n' );
		st.append(    "'F' = do not write out these files" + '\n' );
		st.append(    "MATLAB_OUT: 'T' = write out files containing patch vertices and patch faces," + '\n' );
		st.append(    "as well as patch surface temperature (Tsfc), patch brightness temperature" + '\n' );
		st.append(    "(Tbright), and patch net shortwave (Kstar); these three quantities can then" + '\n' );
		st.append(    "be easily visualized in Matlab with the 'patch' command; 'F' - do not write" + '\n' );
		st.append(    "these files" + '\n' );
		st.append(    "SUM_OUT: 'T' = write out individual patch surface temperatures (Tsfc) and" + '\n' );
		st.append(    "patch brightness temperature (Tbright) organized by TUF3D loop order (facet" + '\n' );
		st.append(    "direction varies slowest, then z, then y, then x) - so as to be easily read" + '\n' );
		st.append(    "in and assigned to the equivalent geometry (in the SUM model for example);" + '\n' );
		st.append(    "'F' = do not write out these files" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c radiative parameters" + '\n' );
		st.append(    "DALB: (W/m2) accuracy to which the effective albedo/emissivity of the" + '\n' );
		st.append(    "canyon/cavity portion of the domain will be calculated; that is," + '\n' );
		st.append(    "reflections will continue until the change in albedo between timesteps" + '\n' );
		st.append(    "is less than \"dalb\"" + '\n' );
		st.append(    "ALBR,ALBS,ALBW: albedo of roof, street, and wall patches, respectively" + '\n' );
		st.append(    "EMISR,EMISS,EMISW: emissivity of roof, street, and wall patches, respectively" + '\n' );
		st.append(    "CLOUDTYPE: For shortwave and longwave radiation; 0=clear, and higher values" + '\n' );
		st.append(    "(to a maximum of 7) are progressively thicker clouds; 1=cirrus, 2= cirrostratus," + '\n' );
		st.append(    "3=altocumulus, 4=altostratus, 5=cumulonimbus, 6=stratocumulus, 7=thick stratus" + '\n' );
		st.append(    "(Ns?) - all assumed to be 100% cloud cover" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c conduction parameters" + '\n' );
		st.append(    "INTCOND: 1=full conduction between deepest layer and ground/building" + '\n' );
		st.append(    "interior, 0=no such conduction...values between 0 and 1 are permitted" + '\n' );
		st.append(    "INTRESIST: resistance to energy exchange between deepest building layers" + '\n' );
		st.append(    "and building interior air (0.123 m2*K/W approximates combined conductive," + '\n' );
		st.append(    "radiative, and convective exchange - Masson et al. 2002)" + '\n' );
		st.append(    "UC: degree of implicitness of solution of tridiagonal matrix for" + '\n' );
		st.append(    "conduction (1=fully implicit, 0=fully explicit, 0.5 is Crank-Nicholson and" + '\n' );
		st.append(    "is the most accurate, and is still stable - UC<0.5 may be unstable)" + '\n' );
		st.append(    "NUMLAYERS: number of layers for conduction in roofs, roads, walls" + '\n' );
		st.append(    "THICKR(k),LAMBDAR(k),HTCAPR(k): thickness (m), thermal conductivity" + '\n' );
		st.append(    "(W/m/K), and volumetric heat capacity (J/m3/K) of roof layer k, where k=1" + '\n' );
		st.append(    "is the surface layer, and k=numlayers is the deepest layer" + '\n' );
		st.append(    "THICKS(k),LAMBDAS(k),HTCAPS(k): same as for roofs, but for roads" + '\n' );
		st.append(    "THICKW(k),LAMBDAW(k),HTCAPW(k): same as for roofs, but for walls" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "**Note: the chosen conduction parameters put an upper limit on the model" + '\n' );
		st.append(    "time step for stability and accuracy reasons. If you want the model to run" + '\n' );
		st.append(    "faster, and it appears to be limited by its time step (i.e. time step is" + '\n' );
		st.append(    "below 20-30 seconds or so), then you may want to try making the solution" + '\n' );
		st.append(    "method more implicit (increase UC towards 1.0), or increasing the thickness" + '\n' );
		st.append(    "and heat capacity (or decreasing the thermal conductivity) of your thinnest" + '\n' );
		st.append(    "layers" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c convection parameters" + '\n' );
		st.append(    "Z0: input town (i.e. overall) roughness length - model calculates it" + '\n' );
		st.append(    "according to Macdonald (1998) if values is less than 0" + '\n' );
		st.append(    "LAMBDAF: input town (i.e. overall) frontal area to plan area ratio" + '\n' );
		st.append(    " - model calculates it if values is less than 0, and the model formula" + '\n' );
		st.append(    "depends on wind direction" + '\n' );
		st.append(    "ZROOFFRC: (m) the height above roof level for variables (temp, wind) used" + '\n' );
		st.append(    "to in forcing convection from roofs - model calculates it if the value" + '\n' );
		st.append(    "is negative " + '\n' );
		st.append(    "Z0ROOFM,Z0ROADM: (m) roof and road momentum roughness lengths, respectively" + '\n' );
		st.append(    "Z0ROOFH,Z0ROADH: (m) roof and road thermal roughness lengths, respectively" + '\n' );
		st.append(    " - if values are negative model defaults to 1/200 of corresponding" + '\n' );
		st.append(    "momentum roughness lengths - **note that ratio of momentum to thermal" + '\n' );
		st.append(    "roughness lengths should never be smaller than 1/200! **" + '\n' );
		st.append(    "MOH: ratio of momentum to thermal roughness lengths for transfer from" + '\n' );
		st.append(    "individual *surfaces* only" + '\n' );
		st.append(    "RW: wall roughness relative to concrete (rw=1)" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c domain geometry" + '\n' );
		st.append(    "BUILDHT_M: (m) height of buildings (mean height, if there is variation)" + '\n' );
		st.append(    "ZREF: (m) height of measured forcing data (air temperature, wind speed, etc)" + '\n' );
		st.append(    " - must be greater than 'buildht_m'" + '\n' );
		st.append(    "MINRES: minimum resolution of any given facet (i.e. roof, road, or wall)" + '\n' );
		st.append(    " - recommended value is 4 or greater (6 is ideal); resolution of all" + '\n' );
		st.append(    "other facets will be adjusted to maintain all geometric ratios while" + '\n' );
		st.append(    "ensuring that all facets have a minimum of 'minres' patches across them" + '\n' );
		st.append(    "in both dimensions (NOTE: THIS IS THE KEY PARAMETER THAT CONTROLS THE" + '\n' );
		st.append(    "ACCURACY OF THE RADIATION SCHEME VS. THE COMPUTATIONAL EXPENSE -" + '\n' );
		st.append(    "minres = 2 will give a quick estimate, minres = 4 tends to be a reasonable" + '\n' );
		st.append(    "balance between speed and accuracy, and minres = 6 tends to give very" + '\n' );
		st.append(    "accurate results but can be very computationally expensive and can require " + '\n' );
		st.append(    "a lot of memory, relative to the speed and memory of a typical desktop;" + '\n' );
		st.append(    "it is also useful to remember that the minimum resolution for solar" + '\n' );
		st.append(    "radiation absorption is effectively 2*minres - see the BLM paper for an" + '\n' );
		st.append(    "explanation)" + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c initial and constant temperatures" + '\n' );
		st.append(    "TSFCR,TSFCS,TSFCW: (deg C) initial surface temperatures (roofs, roads and" + '\n' );
		st.append(    "walls, respectively)" + '\n' );
		st.append(    "TINTW: (deg C) constant building internal air temperature (base of roofs" + '\n' );
		st.append(    "and walls)" + '\n' );
		st.append(    "TINTS: (deg C) constant deep-ground temperature (base of roads)" + '\n' );
		st.append(    "TFLOOR: (deg C) constant building internal floor temperature (affects" + '\n' );
		st.append(    "building internal temperature slightly)" + '\n' );
		st.append(    "TBUILD_MIN: (deg C) minimum indoor temperature permitted. If this" + '\n' );
		st.append(    "temperature is sufficiently high relative to the ambient temperature, it" + '\n' );
		st.append(    "will simulate space heating." + '\n' );
		st.append(    "" + '\n' );
		st.append(    "c loop parameters (for multiple simulations with the same forcing data," + '\n' );
		st.append(    "but with different street orientations, latitudes, lambdap ratios, and" + '\n' );
		st.append(    "combinations thereof)" + '\n' );
		st.append(    "STROR_IN,STRORINT,STRORMAX: (degrees from alignment with cardinal" + '\n' );
		st.append(    "directions) initial, loop interval, and final street orientation" + '\n' );
		st.append(    "orientation" + '\n' );
		st.append(    "XLAT_IN,XLATINT,XLATMAX: (degrees) initial, loop interval, and final" + '\n' );
		st.append(    "latitude" + '\n' );
		st.append(    "NUMLP: number of lambdap ratios to loop through" + '\n' );
		st.append(    "LPIN(k): the lambdap ratios (from k=1 to k=numlp)" + '\n' );
		st.append(    "NUMBHBL: number of bh (building height) to bl (building width) ratios to" + '\n' );
		st.append(    "simulate for each lambdap" + '\n' );
		st.append(    "BH_O_BL(k): the bh/bl ratios (from l=1 to l=numbhbl)" + '\n' );
		
		
		return st.toString();
	}	
	
	
	
	public void writeConfigFile(String inputDirectory)
	{
		common.createDirectory( inputDirectory);
		common.writeFile(getFileText(),  inputDirectory + "/" + this.filename);
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

	public String getVfcalc()
	{
		return vfcalc;
	}

	public void setVfcalc(String vfcalc)
	{
		this.vfcalc = vfcalc;
	}

	public int getYd()
	{
		return yd;
	}

	public void setYd(int yd)
	{
		this.yd = yd;
	}

	public String getDeltat()
	{
		return deltat;
	}

	public void setDeltat(String deltat)
	{
		this.deltat = deltat;
	}

	public String getOutpt_tm()
	{
		return outpt_tm;
	}

	public void setOutpt_tm(String outpt_tm)
	{
		this.outpt_tm = outpt_tm;
	}

	public String getTthreshold()
	{
		return Tthreshold;
	}

	public void setTthreshold(String tthreshold)
	{
		Tthreshold = tthreshold;
	}

	public String getFacet_out()
	{
		return facet_out;
	}

	public void setFacet_out(String facet_out)
	{
		this.facet_out = facet_out;
	}

	public String getMatlab_out()
	{
		return matlab_out;
	}

	public void setMatlab_out(String matlab_out)
	{
		this.matlab_out = matlab_out;
	}

	public String getSum_out()
	{
		return sum_out;
	}

	public void setSum_out(String sum_out)
	{
		this.sum_out = sum_out;
	}

	public String getFILENAME_PREFIX()
	{
		return FILENAME_PREFIX;
	}

	public String getFILENAME_SUFFUX()
	{
		return FILENAME_SUFFUX;
	}

	public String getDalb()
	{
		return dalb;
	}

	public void setDalb(String dalb)
	{
		this.dalb = dalb;
	}

	public String getAlbr()
	{
		return albr;
	}

	public void setAlbr(String albr)
	{
		this.albr = albr;
	}

	public String getAlbs()
	{
		return albs;
	}

	public void setAlbs(String albs)
	{
		this.albs = albs;
	}

	public String getAlbw()
	{
		return albw;
	}

	public void setAlbw(String albw)
	{
		this.albw = albw;
	}

	public String getEmisr()
	{
		return emisr;
	}

	public void setEmisr(String emisr)
	{
		this.emisr = emisr;
	}

	public String getEmiss()
	{
		return emiss;
	}

	public void setEmiss(String emiss)
	{
		this.emiss = emiss;
	}

	public String getEmisw()
	{
		return emisw;
	}

	public void setEmisw(String emisw)
	{
		this.emisw = emisw;
	}

	public String getCloudtype()
	{
		return cloudtype;
	}

	public void setCloudtype(String cloudtype)
	{
		this.cloudtype = cloudtype;
	}

	public String getIntCond()
	{
		return IntCond;
	}

	public void setIntCond(String intCond)
	{
		IntCond = intCond;
	}

	public String getIntresist()
	{
		return Intresist;
	}

	public void setIntresist(String intresist)
	{
		Intresist = intresist;
	}

	public String getUc()
	{
		return uc;
	}

	public void setUc(String uc)
	{
		this.uc = uc;
	}

	public int getNumlayers()
	{
		return numlayers;
	}

	public void setNumlayers(int numlayers)
	{
		this.numlayers = numlayers;
	}

	public String[] getThickr()
	{
		return thickr;
	}

	public void setThickr(String[] thickr)
	{
		this.thickr = thickr;
	}

	public String[] getLambdar()
	{
		return lambdar;
	}

	public void setLambdar(String[] lambdar)
	{
		this.lambdar = lambdar;
	}

	public String[] getHtcapr()
	{
		return htcapr;
	}

	public void setHtcapr(String[] htcapr)
	{
		this.htcapr = htcapr;
	}

	public String[] getThicks()
	{
		return thicks;
	}

	public void setThicks(String[] thicks)
	{
		this.thicks = thicks;
	}

	public String[] getLambdas()
	{
		return lambdas;
	}

	public void setLambdas(String[] lambdas)
	{
		this.lambdas = lambdas;
	}

	public String[] getHtcaps()
	{
		return htcaps;
	}

	public void setHtcaps(String[] htcaps)
	{
		this.htcaps = htcaps;
	}

	public String[] getThickw()
	{
		return thickw;
	}

	public void setThickw(String[] thickw)
	{
		this.thickw = thickw;
	}

	public String[] getLambdaw()
	{
		return lambdaw;
	}

	public void setLambdaw(String[] lambdaw)
	{
		this.lambdaw = lambdaw;
	}

	public String[] getHtcapw()
	{
		return htcapw;
	}

	public void setHtcapw(String[] htcapw)
	{
		this.htcapw = htcapw;
	}

	public String getZ0()
	{
		return z0;
	}

	public void setZ0(String z0)
	{
		this.z0 = z0;
	}

	public String getLambdaf()
	{
		return lambdaf;
	}

	public void setLambdaf(String lambdaf)
	{
		this.lambdaf = lambdaf;
	}

	public String getZrooffrc()
	{
		return zrooffrc;
	}

	public void setZrooffrc(String zrooffrc)
	{
		this.zrooffrc = zrooffrc;
	}

	public String getZ0roofm()
	{
		return z0roofm;
	}

	public void setZ0roofm(String z0roofm)
	{
		this.z0roofm = z0roofm;
	}

	public String getZ0roadm()
	{
		return z0roadm;
	}

	public void setZ0roadm(String z0roadm)
	{
		this.z0roadm = z0roadm;
	}

	public String getZ0roofh()
	{
		return z0roofh;
	}

	public void setZ0roofh(String z0roofh)
	{
		this.z0roofh = z0roofh;
	}

	public String getZ0roadh()
	{
		return z0roadh;
	}

	public void setZ0roadh(String z0roadh)
	{
		this.z0roadh = z0roadh;
	}

	public String getMoh()
	{
		return moh;
	}

	public void setMoh(String moh)
	{
		this.moh = moh;
	}

	public String getRw()
	{
		return rw;
	}

	public void setRw(String rw)
	{
		this.rw = rw;
	}

	public String getBuildht_m()
	{
		return buildht_m;
	}

	public void setBuildht_m(String buildht_m)
	{
		this.buildht_m = buildht_m;
	}

	public String getZref()
	{
		return zref;
	}

	public void setZref(String zref)
	{
		this.zref = zref;
	}

	public String getMinres()
	{
		return minres;
	}

	public void setMinres(String minres)
	{
		this.minres = minres;
	}

	public String getTsfcr()
	{
		return Tsfcr;
	}

	public void setTsfcr(String tsfcr)
	{
		Tsfcr = tsfcr;
	}

	public String getTsfcs()
	{
		return Tsfcs;
	}

	public void setTsfcs(String tsfcs)
	{
		Tsfcs = tsfcs;
	}

	public String getTsfcw()
	{
		return Tsfcw;
	}

	public void setTsfcw(String tsfcw)
	{
		Tsfcw = tsfcw;
	}

	public String getTintw()
	{
		return Tintw;
	}

	public void setTintw(String tintw)
	{
		Tintw = tintw;
	}

	public String getTints()
	{
		return Tints;
	}

	public void setTints(String tints)
	{
		Tints = tints;
	}

	public String getTfloor()
	{
		return Tfloor;
	}

	public void setTfloor(String tfloor)
	{
		Tfloor = tfloor;
	}

	public String getTbuild_min()
	{
		return Tbuild_min;
	}

	public void setTbuild_min(String tbuild_min)
	{
		Tbuild_min = tbuild_min;
	}

	public String getStror_in()
	{
		return stror_in;
	}

	public void setStror_in(String stror_in)
	{
		this.stror_in = stror_in;
	}

	public String getStrorint()
	{
		return strorint;
	}

	public void setStrorint(String strorint)
	{
		this.strorint = strorint;
	}

	public String getStrormax()
	{
		return strormax;
	}

	public void setStrormax(String strormax)
	{
		this.strormax = strormax;
	}

	public String getXlat_in()
	{
		return xlat_in;
	}

	public void setXlat_in(String xlat_in)
	{
		this.xlat_in = xlat_in;
	}

	public String getXlatint()
	{
		return xlatint;
	}

	public void setXlatint(String xlatint)
	{
		this.xlatint = xlatint;
	}

	public String getXlatmax()
	{
		return xlatmax;
	}

	public void setXlatmax(String xlatmax)
	{
		this.xlatmax = xlatmax;
	}

	public int getNumlp()
	{
		return numlp;
	}

	public void setNumlp(int numlp)
	{
		this.numlp = numlp;
	}

	public String[] getLpin()
	{
		return lpin;
	}

	public void setLpin(String[] lpin)
	{
		this.lpin = lpin;
	}

	public int getNumbhbl()
	{
		return numbhbl;
	}

	public void setNumbhbl(int numbhbl)
	{
		this.numbhbl = numbhbl;
	}

	public String[] getBh_o_bl()
	{
		return bh_o_bl;
	}

	public void setBh_o_bl(String[] bh_o_bl)
	{
		this.bh_o_bl = bh_o_bl;
	}
	


	public double getStartTime()
	{
		return startTime;
	}

	public void setStartTime(double startTime)
	{
		this.startTime = startTime;
	}

	public double getEndTime()
	{
		return endTime;
	}

	public void setEndTime(double endTime)
	{
		this.endTime = endTime;
	}

	public String getLwv()
	{
		return lwv;
	}

	public void setLwv(String lwv)
	{
		this.lwv = lwv;
	}

	public String getSwv()
	{
		return swv;
	}

	public void setSwv(String swv)
	{
		this.swv = swv;
	}

	public String getFrcKdn()
	{
		return frcKdn;
	}

	public void setFrcKdn(String frcKdn)
	{
		this.frcKdn = frcKdn;
	}

	public String getFrcLdn()
	{
		return frcLdn;
	}

	public void setFrcLdn(String frcLdn)
	{
		this.frcLdn = frcLdn;
	}

	public String getFrcTsfc()
	{
		return frcTsfc;
	}

	public void setFrcTsfc(String frcTsfc)
	{
		this.frcTsfc = frcTsfc;
	}

	public double getPress()
	{
		return press;
	}

	public void setPress(double press)
	{
		this.press = press;
	}

	public String getEmiss_var()
	{
		return emiss_var;
	}

	public void setEmiss_var(String emiss_var)
	{
		this.emiss_var = emiss_var;
	}

	public double getEmissInter()
	{
		return emissInter;
	}

	public void setEmissInter(double emissInter)
	{
		this.emissInter = emissInter;
	}

	public double getEmissNS()
	{
		return emissNS;
	}

	public void setEmissNS(double emissNS)
	{
		this.emissNS = emissNS;
	}

	public double getEmissEW()
	{
		return emissEW;
	}

	public void setEmissEW(double emissEW)
	{
		this.emissEW = emissEW;
	}

	public int getNumres()
	{
		return numres;
	}

	public void setNumres(int numres)
	{
		this.numres = numres;
	}

	public String[] getResin()
	{
		return resin;
	}

	public void setResin(String[] resin)
	{
		this.resin = resin;
	}
	
}
