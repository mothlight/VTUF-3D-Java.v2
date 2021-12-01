package VTUF3D;

import java.util.ArrayList;
import java.util.TreeMap;

import VTUF3D.Utilities.MaespaDataFile;
import VTUF3D.Utilities.Namelist;

public class ReadMaespaConfigs
{
	//     
	// File:   ReadMaespaConfigs.f90
	// Author: kerryn
	//
	// Created on 14 March 2014, 12:42 PM
	//


	public static void main(String[] args)
	{
		ReadMaespaConfigs read = new ReadMaespaConfigs();
		read.readMaespaTreeMapFromConfig();
	}
	     
	   public int findTreeFromConfig(int xtestRev,int ytestRev,int ztestRev,MaespaConfigTreeMapState treeState,
			   double timeis,int yd_actual,int treeConfigLocation)
	   {

	    int vegHeight;

	    int phyFile, strFile, treeFile;

	    double ZENlocal;

	      
	    
	    treeConfigLocation = -1;
	   
	    vegHeight = 0; //if the tree location isn't found, then it will be 0 high
	    //first check if the x,y is in the location list
	    
	    for (int loopCount = 1; loopCount <treeState.numberTreePlots;loopCount++)
	    {
	        // this is +1 because of arrays 0 and 1 indexing problem
	        if ( (xtestRev)==treeState.xLocation[loopCount]+1 && (ytestRev)==treeState.yLocation[loopCount]+1 ) 
	        {
	            //print *,'found tree at ',xtestRev,ytestRev
	            vegHeight = treeState.treesHeight[loopCount];
	            treeConfigLocation = loopCount;	                      
	        }

	        
	    }
	    
	    
	     // Read in an array of tree parameters from UFILE.
	// NARRAY is the number of the array to be read (1 = RADX; 2 = RADY;
	// 3 = HTCROWN; 4 = HTTRUNK; 5 = AREALEAF; 6 = DIAM; 7 = LEAFN (for understorey))
	// Either read in values for all trees (NOALLTREES, up to MAXT trees) or
	// read in average values. All values can be given for a series of dates.
	//         // Get radii in x & y directions of each tree
	//    CALL READTREEARRAY(UTREES,1,NOALLTREES,NOXDATES,DATESX,R1)
	//    CALL READTREEARRAY(UTREES,2,NOALLTREES,NOYDATES,DATESY,R2)
	//    // Get green crown height of each tree
	//    CALL READTREEARRAY(UTREES,3,NOALLTREES,NOZDATES,DATESZ,R3)
	//    // Get trunk length of each tree
	//    CALL READTREEARRAY(UTREES,4,NOALLTREES,NOTDATES,DATEST,TRUNK)
	//    // Get diameter of each tree
	//    CALL READTREEARRAY(UTREES,6,NOALLTREES,NODDATES,DATESD,DIAMA)    


//	   end subroutine findTreeFromConfig
	    return treeConfigLocation;
	   }
	   

	   @Deprecated
	 public MaespaConfigTreeMapState readMaespaTreeMapFromConfig()
	 {


		MaespaConfigTreeMapState state = new MaespaConfigTreeMapState();
	    
	    String tmpfilename1;
	    String tmpfilename2;
	    String tmpfilename3;
	    String format_string;
	    int loopcount;

	    int numberTreePlots;
	    int numberBuildingPlots;
	    int width,length;
	    

	    
	    int[] xLocation, yLocation;
	    int[] xBuildingLocation, yBuildingLocation;
	    int[] phyfileNumber,strfileNumber,treesfileNumber, treesHeight,trees;
	    int[] buildingsHeight;
	    int[]  partitioningMethod;
	    int[]  usingDiffShading;
	    
	    int configTreeMapCentralArrayLength;
	    int configTreeMapCentralWidth;
	    int configTreeMapCentralLength;
	    int configTreeMapX;
	    int configTreeMapY;
	    int configTreeMapX1;
	    int configTreeMapX2;
	    int configTreeMapY1;
	    int configTreeMapY2;
	    int configTreeMapNumsfcab;
	    double configTreeMapGridSize;
	    int configTreeMapHighestBuildingHeight;
	    

	    
	    

	    
	    String treemapfilename = "/home/kerryn/Documents/Work/VTUF-Runs/PrestonBase/PrestonBase8/treemap.dat";
	    Namelist treemapNamelist = new Namelist(treemapfilename);
	    
	    
	    numberTreePlots = treemapNamelist.getIntValue("count", "numberTreePlots");
	    state.numberTreePlots=numberTreePlots;
	    

	    
	    numberBuildingPlots = treemapNamelist.getIntValue("buildingcount", "numberBuildingPlots");
	    state.numberBuildingPlots=numberBuildingPlots;
	    

	    

	    xBuildingLocation = new int[numberBuildingPlots];

	    yBuildingLocation = new int[numberBuildingPlots];

	    buildingsHeight = new int[numberBuildingPlots];



	    xLocation = treemapNamelist.getIntArrayValue("location", "xLocation");
	    state.xLocation=xLocation;
	    yLocation = treemapNamelist.getIntArrayValue("location", "yLocation");
	    state.yLocation=yLocation;
	    phyfileNumber = treemapNamelist.getIntArrayValue("location", "phyfileNumber");
	    state.phyfileNumber=phyfileNumber;
	    strfileNumber = treemapNamelist.getIntArrayValue("location", "strfileNumber");
	    state.strfileNumber=strfileNumber;
	    treesfileNumber = treemapNamelist.getIntArrayValue("location", "treesfileNumber");
	    state.treesfileNumber=treesfileNumber;
	    treesHeight = treemapNamelist.getIntArrayValue("location", "treesHeight");
	    state.treesHeight=treesHeight;
	    trees = treemapNamelist.getIntArrayValue("location", "trees");
	    state.trees=trees;
	    
	    


	    xBuildingLocation = treemapNamelist.getIntArrayValue("buildinglocation", "xBuildingLocation");
	    state.xBuildingLocation=xBuildingLocation;
	    yBuildingLocation = treemapNamelist.getIntArrayValue("buildinglocation", "yBuildingLocation");
	    state.yBuildingLocation=yBuildingLocation;
	    buildingsHeight = treemapNamelist.getIntArrayValue("buildinglocation", "buildingsHeight");
	    state.buildingsHeight=buildingsHeight   ; 
	       


	    width = treemapNamelist.getIntValue("domain", "width");
	    state.width=width;
	    length = treemapNamelist.getIntValue("domain", "length");
	    state.length=length ;
	    configTreeMapCentralArrayLength = treemapNamelist.getIntValue("domain", "configTreeMapCentralArrayLength");
	    state.configTreeMapCentralArrayLength=configTreeMapCentralArrayLength;
	    configTreeMapCentralWidth = treemapNamelist.getIntValue("domain", "configTreeMapCentralWidth");
	    state.configTreeMapCentralWidth=configTreeMapCentralWidth;
	    configTreeMapCentralLength = treemapNamelist.getIntValue("domain", "configTreeMapCentralLength");
	    state.configTreeMapCentralLength=configTreeMapCentralLength;
	    configTreeMapX = treemapNamelist.getIntValue("domain", "configTreeMapX");
	    state.configTreeMapX=configTreeMapX;
	    configTreeMapY = treemapNamelist.getIntValue("domain", "configTreeMapY");
	    state.configTreeMapY=configTreeMapY;
	    configTreeMapX1 = treemapNamelist.getIntValue("domain", "configTreeMapX1");
	    state.configTreeMapX1=configTreeMapX1;
	    configTreeMapX2 = treemapNamelist.getIntValue("domain", "configTreeMapX2");
	    state.configTreeMapX2=configTreeMapX2;
	    configTreeMapY1 = treemapNamelist.getIntValue("domain", "configTreeMapY1");
	    state.configTreeMapY1=configTreeMapY1;
	    configTreeMapY2 = treemapNamelist.getIntValue("domain", "configTreeMapY2");
	    state.configTreeMapY2=configTreeMapY2;
	    configTreeMapGridSize = treemapNamelist.getIntValue("domain", "configTreeMapGridSize");
	    state.configTreeMapGridSize=configTreeMapGridSize;
	    configTreeMapNumsfcab = treemapNamelist.getIntValue("domain", "configTreeMapNumsfcab");
	    state.configTreeMapNumsfcab=configTreeMapNumsfcab;
	    configTreeMapHighestBuildingHeight = treemapNamelist.getIntValue("domain", "configTreeMapHighestBuildingHeight");
	    state.configTreeMapHighestBuildingHeight=configTreeMapHighestBuildingHeight;
	    
	    

	    
	    
	    
	    state.configPartitioningMethod=treemapNamelist.getIntValue("runSwitches", "partitioningMethod");
	    state.usingDiffShading=treemapNamelist.getIntValue("runSwitches", "usingDiffShading");
	    
//	    end subroutine readMaespaTreeMapFromConfig
	    return state;
	 }
	    
	    
	       
	   public void getVegHeight(MaespaConfigTreeMapState state,MaespaConfigTreeMapState treeState, int x, int y, int vegHeight)
	   {

	    int phyFile, strFile, treeFile;
  
	    
	    int numTreePlots = treeState.numberTreePlots;
	    
	    vegHeight = 0; //if the tree location isn't found, then it will be 0 high
	    //first check if the x,y is in the location list
	    for (int loopCount = 1; loopCount < numTreePlots ; loopCount++)
	    {
	        if ( x==treeState.xLocation[loopCount] && y==treeState.yLocation[loopCount] ) 
	        {
//	        	System.out.println("yes at "+ " " + loopCount+ " " + x+ " " + y);
//	            print *,'yes at ',loopCount,x,y;
	            //figure out how tall the tree is
	//            print *,treeStates(loopCount)%NOALLTREES
	//            print *,treeStates(loopCount)%NOZDATES
	//            print *,treeStates(loopCount)%DATESZ
	//            print *,treeStates(loopCount)%R3
//	            print *,'';
	        }

	        
	    }
	    
	   }

	    
	    
	    
	    
	    
	   public int getVegHeightFromConfig(int x, int y, int vegHeight, MaespaConfigTreeMapState treeState)
	   {
	    int phyFile, strFile, treeFile;
   	    //only load it once 

	    if (treeState.numberTreePlots<1 || treeState.numberTreePlots>100000000) 
	    { 

	    	treeState = readMaespaTreeMapFromConfig();
	    }
	      
	    vegHeight = 0; //if the tree location isn't found, then it will be 0 high
	    //first check if the x,y is in the location list
	    for (int loopCount = 1;loopCount < treeState.numberTreePlots;loopCount++)
	    {
	        if ( (x-1)==treeState.xLocation[loopCount] && (y-1)==treeState.yLocation[loopCount] ) 
	        {   
	            //figure out how tall the tree is
	            vegHeight = treeState.treesHeight[loopCount];
	        }   
	    }
	    

	    return vegHeight;
	   }
	    
	    
	   public int getBuildingHeightFromConfig(int x, int y, int buildingHeight, MaespaConfigTreeMapState treeState)
	   {

	    
	    //only load it once 
	    if (treeState.numberTreePlots<1 || treeState.numberTreePlots>100000000) 
	    { 
	    	treeState= readMaespaTreeMapFromConfig();
	    }
	    
	    buildingHeight = 0 ;//if the tree location isn't found, then it will be 0 high
	    //first check if the x,y is in the location list
	    for (int loopCount = 1;loopCount < treeState.numberBuildingPlots;loopCount++)
		{
	        if ( (x-1)==treeState.xBuildingLocation[loopCount] && (y-1)==treeState.yBuildingLocation[loopCount] ) 
	        {
	            buildingHeight = treeState.buildingsHeight[loopCount];
	        }
		}
	    return buildingHeight;
	   
//	    end subroutine getBuildingHeightFromConfig  
	   }
	   
	      public double convertmmolsecToWm2(double mmol,double width1,double width2,double hours)
	      {
//	          double mmol;
	          double convertmmolsecToWm2;
//	          double hours;
	          
	          // mmol/sec (which is hrLE from Maespa hrflux)
	          // width1,2 are dimensions of plot (x meters, y meters)
	          // hours - length of time 
	          
	          // 1 mole of water = 18.0152g
	          // 1mm H2O = 1kg/m2
	          // 1mm/day = 2.45 MJ m-2 day-1
	          // mmol/sec *  1 mol/1000mmol * 18.0152g/mol * 1kg/1000g * width1 (m) * width2 (m) * 60sec/1min * 60min/1hour * 24hour/day
	          //    * 2.45 MJ/m2*day * 10E06J/MJ * 1W/1J * 1day/24hour * 1hour/60min * 1hour/60sec 
	          
	          //convertmmolsecToWm2 = mmol /1000 * 18.0152 /1000 * width1 * width2 * 60 * 60 * 24 *2.45 * 10E06 /24 / 60 /60 * hours
	          convertmmolsecToWm2 = mmol * 18.0152 * width1 * width2 * 2.45 * hours;
	          //print *,'mmol,convertmmolsecToWm2',mmol,convertmmolsecToWm2
	          return convertmmolsecToWm2;
	  
//	      end function convertmmolsecToWm2
	   }

	      
	      public double convertMMETToLEWm2(double mm,double width1,double width2,double hours)
	      {
//	          double mm;
	          double convertMMETToLEWm2;
//	          double hours;
	          
	          // width1,2 are dimensions of plot, x meters, y meters
	          // mm is mm of ET
	          //  18.0152ml/mol of water
	          // heat of vaporization 40.7 KJ/mol
	          
	          // time (hours) * mm ET * 1M/1000mm * width1 (m) * width2 (m) 10E+06ml/m^3 * 1 mol/18.0152ml * 40.7 KJ/mol *  1W/1000KJ/sec * 60 sec/1 min * 60 min/hour * hours
	          
	          convertMMETToLEWm2=hours * mm * width1 * width2 / 18.0152 * 40.7 * 60*60 ;
	          //print *,'convertMMETToLEWm2=mm,le',mm,convertMMETToLEWm2
	          return convertMMETToLEWm2;
	          
//	      end function convertMMETToLEWm2
	      }

	
	      
	      //**********************************************************************    
	      public double calculateLWFromTCan(double tcan)
	      {
	          double calculateLWFromTCan;
	          double k;
	          double sigma ;
	          
	          // =(R2+273.16)^4*5.67*(10^-8)
	          k=(tcan+273.16);
	          sigma = 5.67E-08;

	          calculateLWFromTCan=Math.pow(k,4)* sigma;
	          return calculateLWFromTCan;
	          
//	      end function calculateLWFromTCan
	      }
	      
	//**********************************************************************
	    //public ArrayList<MaespaDataResults> readMaespaHRWatDataFiles(int treeConfigLocation, MaespaDataResults maespaData, int width1, int diffShadingType)
	  public ArrayList<MaespaDataResults> readMaespaHRWatDataFiles(int treeConfigLocation, double width1, int diffShadingType)
	    {

//	      int treeState;
//	      int IOERROR;
//	      int TREESDAT_FILE;
//	      int STRDAT_FILE;
//	      int linesToSkip;
//	      int hrlinesToSkip;
//	      double timeis;
//	      double  dummy;
//	      int  n;
//	      int nr_lines, nr_elements;
//	      int  hr_nr_lines;
	      double width2,hours;
	      String  treeConfigLocationStr;
	      String  diffShadingTypeStr;
	      String  watbalfilestr;
	      String  hrflxfilestr;
	      String  treesdatfilestr;  
	      String  strdatfilestr  ;
	      boolean loadUspar;
	      double area;
	      double areaOfTree;
	      double newArea;
//	      double COEFFT,EXPONT,WINTERC, BCOEFFT,BEXPONT,BINTERC, RCOEFFT,REXPONT,RINTERC,FRFRAC;
//	      int NODATES;
//	      double VALUES;
//	      double HTCROWN,HTTRUNK,DIAM;
	      double RADIUS;
//	      double WBIOM, deltaQVeg;
	      double SHAPE;
	      String CSHAPE;
	      boolean SHAPEREAD;	      
//	      double mVeg, cVeg, deltaTveg, deltaTime;

	      loadUspar = true;
	      width2=width1;
	      hours=1.0;
	      area = width1*width2;
	      newArea = 1.0;
	                       
//	     hrlinesToSkip = 35 ;
//	     linesToSkip = 37;    
//	     TREESDAT_FILE = 1258;
//	     STRDAT_FILE = 1259;
	     
	     // cVeg = 2928 J/kg K, taken from Oliphant (2004)
//	     cVeg = 2928;
//	     deltaTime = hours * 60 * 60;
	     
	     

	     treeConfigLocationStr = treeConfigLocation+"";

	     diffShadingTypeStr = diffShadingType+"";
	     
	     watbalfilestr = "./" + treeConfigLocationStr + "/" + diffShadingTypeStr + "/watbal.dat";

	     hrflxfilestr = "./" + treeConfigLocationStr + "/" + diffShadingTypeStr + "/hrflux.dat";
	     
	    // Input file with data on tree position and size
	    treesdatfilestr = "./" + treeConfigLocationStr + "/" + diffShadingTypeStr + "/trees.dat";
	    
	    strdatfilestr = "./" + treeConfigLocationStr + "/" + diffShadingTypeStr + "/str1.dat";
	   
		Namelist strdatfilestrnamelist = new Namelist(strdatfilestr);
//		COEFFT = strdatfilestrnamelist.getDoubleValue("allom", "coefft");
//		EXPONT = strdatfilestrnamelist.getDoubleValue("allom", "expont");
//		WINTERC = strdatfilestrnamelist.getDoubleValue("allom", "winterc");
   


//		BCOEFFT = strdatfilestrnamelist.getDoubleValue("allomb", "bcoefft");
//		BEXPONT = strdatfilestrnamelist.getDoubleValue("allomb", "bexpont");
//		BINTERC = strdatfilestrnamelist.getDoubleValue("allomb", "binterc");
	
   
//		RCOEFFT = strdatfilestrnamelist.getDoubleValue("allomr", "rcoefft");
//		REXPONT = strdatfilestrnamelist.getDoubleValue("allomr", "rexpont");
//		RINTERC = strdatfilestrnamelist.getDoubleValue("allomr", "rinterc");
//		FRFRAC = strdatfilestrnamelist.getDoubleValue("allomr", "frfrac");
	  
	    // Get green crown height of each tree
//	    Namelist treesdatfilestrnamelist = new Namelist(treesdatfilestr);
//	    HTCROWN = strdatfilestrnamelist.getDoubleValue("allhtcrown", "values");

	    
	  		    
	    // Get trunk length of each tree
//	    HTTRUNK = strdatfilestrnamelist.getDoubleValue("allhttrunk", "values");
	        
	    // Get diameter of each tree
//	    DIAM = strdatfilestrnamelist.getDoubleValue("alldiam", "values");
	    
	    // get radius of tree
	    RADIUS = strdatfilestrnamelist.getDoubleValue("allradx", "values");
	    
	    // Modification (RAD), can now print warning when CSHAPE is miss-ty
	    CSHAPE = "EMPTY";
	    SHAPEREAD = false;


	    CSHAPE = strdatfilestrnamelist.getValueTrimQuotes("canopy", "cshape");
	    if (CSHAPE.equals("CONE")) 
	    {
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	        SHAPEREAD = true;
	    }
	    else if (CSHAPE.equals("ELIP")) 
	    {
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	        SHAPEREAD = true;
	    }
	    else if (CSHAPE.equals("PARA")) 
	    {
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	        SHAPEREAD = true;
	    }
	    else if (CSHAPE.equals("ROUND")) 
	    {
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	        SHAPEREAD = true;
	    }
	    else if (CSHAPE.equals("CYL")) 
	    {
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	        SHAPEREAD = true;
	    }
	    else if (CSHAPE.equals("BOX"))
	    {
	        areaOfTree = RADIUS * 2 * RADIUS * 2;
	        SHAPEREAD = true;
	    }

	    else
	    {
	    	CSHAPE="CONE";
	    	System.out.println("WARNING: CROWN SHAPE NOT READ, USING DEFAULT");
	        areaOfTree = RADIUS * RADIUS * Math.PI;
	    }
	    	   	    	    	    
	        MaespaDataFile WATBALDAT_FILE = new MaespaDataFile(watbalfilestr);
	        MaespaDataFile HRFLXDAT_FILE = new MaespaDataFile(hrflxfilestr);
	        	       
	        double[] canopystore = WATBALDAT_FILE.getDataArrayForVariable("canopystore");
	        double[] evapstore = WATBALDAT_FILE.getDataArrayForVariable("evapstore");
	        double[] drainstore = WATBALDAT_FILE.getDataArrayForVariable("drainstore");
	        double[] et = WATBALDAT_FILE.getDataArrayForVariable("et");
	        double[] soilevap = WATBALDAT_FILE.getDataArrayForVariable("soilevap");
	        double[] qh = WATBALDAT_FILE.getDataArrayForVariable("qh");
	        double[] qe = WATBALDAT_FILE.getDataArrayForVariable("qe");
	        double[] qn = WATBALDAT_FILE.getDataArrayForVariable("qn");
	        double[] qc = WATBALDAT_FILE.getDataArrayForVariable("qc");
	        double[] rnet = WATBALDAT_FILE.getDataArrayForVariable("rnet");
	        double[] soilt1 = WATBALDAT_FILE.getDataArrayForVariable("soilt1");
	        
	        double[] hrTHM = HRFLXDAT_FILE.getDataArrayForVariable("hrTHM");
	        double[] TCAN = HRFLXDAT_FILE.getDataArrayForVariable("TCAN");
	        
	        ArrayList<MaespaDataResults> maespaDataForTimesteps = new ArrayList<MaespaDataResults>();
	        for (int i=0;i<canopystore.length;i++)
	        {
	        	MaespaDataResults item = new MaespaDataResults();
	        	
	        	item.canopystore=canopystore[i];
	        	item.evapstore=evapstore[i];
	        	item.drainstore=drainstore[i];
	        	item.et=et[i];
	        	item.soilevap=soilevap[i];
	        	item.qh=qh[i];
	        	item.qe=qe[i];
	        	item.qn=qn[i];
	        	item.qc=qc[i];
	        	item.rnet=rnet[i];
	        	item.soilt1=soilt1[i];
	        	
	        	item.hrTHM=hrTHM[i];
	        	item.TCAN=TCAN[i];
 
	        	item.qeCalc5 = convertMMETToLEWm2(et[i],newArea,newArea,hours)/areaOfTree 
	        			+ convertMMETToLEWm2(soilevap[i],newArea,newArea,hours)/areaOfTree  
		        		+ convertMMETToLEWm2(canopystore[i],newArea,newArea,hours)/areaOfTree 
		        		+ convertMMETToLEWm2(evapstore[i],newArea,newArea,hours)/areaOfTree ;
	        	
	        	maespaDataForTimesteps.add(item);
	        	
	        }        
	      
	        return maespaDataForTimesteps;
	    }
	      
	      

}
