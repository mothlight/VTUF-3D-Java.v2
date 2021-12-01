package VTUF3D;

import java.util.ArrayList;
import java.util.TreeMap;

import VTUF3D.Utilities.Common;
import VTUF3D.Utilities.MaespaDataFile;
import VTUF3D.Utilities.Namelist;

public class MaespaTreeConfiguration
{
	Common common = new Common();

	public static void main(String[] args)
	{
		String rootDirectory = "/home/kerryn/Documents/Work/VTUF-Runs/PrestonBase/PrestonBase8/";
		MaespaTreeConfiguration treesData = new MaespaTreeConfiguration();
		ArrayList<MaespaDataResults>  results = treesData.readMaespaHRWatDataFiles(1, 5, 1, rootDirectory);
		System.out.println(results.toString());

	}
	
	
	  public ArrayList<MaespaDataResults> readMaespaHRWatDataFiles(int treeConfigLocation, double width1, int diffShadingType, String rootDirectory)
	    {

//		  String rootDirectory = "./";
//	      USE maestcom
//	      use MaespaConfigState, only : maespaConfigTreeMapState,maespaDataResults
//	      IMPLICIT NONE
//	    	int diffShadingType;
//	      int treeState;
//	      int treeConfigLocation;
//	      int IOERROR;
//	      int HRFLXDAT_FILE ;
//	      int WATBALDAT_FILE ;
	//      int USPARDAT_FILE
//	      int TREESDAT_FILE;
//	      int STRDAT_FILE;
//	      int linesToSkip;
//	      int hrlinesToSkip;
	//      int usparlinesToSkip
	      double timeis;
	      double  dummy;
	      int  n;
//	      int i;
	      int nr_lines, nr_elements;
	      int  hr_nr_lines;
	//      int  uspar_nr_lines
//	      TYPE(maespaDataResults),allocatable,dimension(:)  maespaData;
//	      double width1;
	      double width2,hours;
	      String  treeConfigLocationStr;
	      String  diffShadingTypeStr;
	      String  watbalfilestr;
	      String  hrflxfilestr;
	//      character(len=200)  usparfilestr  
	      String  treesdatfilestr;  
	      String  strdatfilestr  ;
	      boolean loadUspar;
	      double area;
	      double areaOfTree;
	      double newArea;
	      double COEFFT,EXPONT,WINTERC, BCOEFFT,BEXPONT,BINTERC, RCOEFFT,REXPONT,RINTERC,FRFRAC;
	      
//	      String DATES(maxdate);
	      int NODATES;
	      double VALUES;
	      double HTCROWN,HTTRUNK,DIAM,RADIUS;
	      double WBIOM, deltaQVeg; 
	      
	      double SHAPE;
	      String CSHAPE;

	      boolean SHAPEREAD;
	      
	      double mVeg, cVeg, deltaTveg, deltaTime;

	      loadUspar = true;
	      width2=width1;
	      hours=1.0;
	      area = width1*width2;
	      newArea = 1.0;
	                       
	     
	     // cVeg = 2928 J/kg K, taken from Oliphant (2004)
	     cVeg = 2928;
	     deltaTime = hours * 60 * 60;
	     
	     
	     treeConfigLocationStr = treeConfigLocation+"";
	     diffShadingTypeStr = diffShadingType+"";
	     
	     watbalfilestr = rootDirectory + treeConfigLocationStr + "/" + diffShadingTypeStr + "/watbal.dat";

	     hrflxfilestr = rootDirectory + treeConfigLocationStr + "/" + diffShadingTypeStr + "/hrflux.dat";

	     
	    // Input file with data on tree position and size
	    treesdatfilestr = rootDirectory + treeConfigLocationStr + "/" + diffShadingTypeStr + "/trees.dat";

	    
	    strdatfilestr = rootDirectory + treeConfigLocationStr + "/" + diffShadingTypeStr + "/str1.dat";

		Namelist strdatfilestrnamelist = new Namelist(strdatfilestr);
		COEFFT = strdatfilestrnamelist.getDoubleValue("allom", "coefft");
		EXPONT = strdatfilestrnamelist.getDoubleValue("allom", "expont");
		WINTERC = strdatfilestrnamelist.getDoubleValue("allom", "winterc");
 

		BCOEFFT = strdatfilestrnamelist.getDoubleValue("allomb", "bcoefft");
		BEXPONT = strdatfilestrnamelist.getDoubleValue("allomb", "bexpont");
		BINTERC = strdatfilestrnamelist.getDoubleValue("allomb", "binterc");
 
		RCOEFFT = strdatfilestrnamelist.getDoubleValue("allomr", "rcoefft");
		REXPONT = strdatfilestrnamelist.getDoubleValue("allomr", "rexpont");
		RINTERC = strdatfilestrnamelist.getDoubleValue("allomr", "rinterc");
		FRFRAC = strdatfilestrnamelist.getDoubleValue("allomr", "frfrac");


	    
	    // Get green crown height of each tree

	    Namelist treesdatfilestrnamelist = new Namelist(treesdatfilestr);
	    HTCROWN = treesdatfilestrnamelist.getDoubleValue("allhtcrown", "values");

	    
	  		    
	    // Get trunk length of each tree
	    HTTRUNK = treesdatfilestrnamelist.getDoubleValue("allhttrunk", "values");
	        
	    // Get diameter of each tree
	    DIAM = treesdatfilestrnamelist.getDoubleValue("alldiam", "values");
	    
	    // get radius of tree
	    RADIUS = treesdatfilestrnamelist.getDoubleValue("allradx", "values");
	    
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
	        double[] DOY = HRFLXDAT_FILE.getDataArrayForVariable("DOY");
	        double[] HOUR = HRFLXDAT_FILE.getDataArrayForVariable("HOUR");
	        
	        // watbal starts at hour 0 where hrflux starts at 1. Skip the first line for watbal
	        ArrayList<MaespaDataResults> maespaDataForTimesteps = new ArrayList<MaespaDataResults>();
//	        System.out.println(treeConfigLocation + " " + diffShadingType);
	        for (int i=0;i<hrTHM.length;i++)
	        {
	        	MaespaDataResults item = new MaespaDataResults();
	        	
	        	item.canopystore=canopystore[i+1];
	        	item.evapstore=evapstore[i+1];
	        	item.drainstore=drainstore[i+1];
	        	item.et=et[i+1];
	        	item.soilevap=soilevap[i+1];
	        	item.qh=qh[i+1];
	        	item.qe=qe[i+1];
	        	item.qn=qn[i+1];
	        	item.qc=qc[i+1];
	        	item.rnet=rnet[i+1];
	        	item.soilt1=soilt1[i+1];
	        	
	        	item.hrTHM=hrTHM[i];
	        	item.TCAN=TCAN[i];
	        	item.DOY=DOY[i];
	        	item.HOUR=HOUR[i];
//	        	System.out.println(i + " " + item.TCAN);

	        	item.qeCalc5 = common.convertMMETToLEWm2(et[i+1],newArea,newArea,hours)/areaOfTree 
	        			+ common.convertMMETToLEWm2(soilevap[i+1],newArea,newArea,hours)/areaOfTree  
		        		+ common.convertMMETToLEWm2(canopystore[i+1],newArea,newArea,hours)/areaOfTree 
		        		+ common.convertMMETToLEWm2(evapstore[i+1],newArea,newArea,hours)/areaOfTree ;
//System.out.println(treeConfigLocation + " " + diffShadingType + " " +
//		i + " " + et[i+1] + " " + soilevap[i+1] + " " + canopystore[i+1] + " " + evapstore[i+1] + " " + item.qeCalc5);	        	
	        	maespaDataForTimesteps.add(item);
	        	
	        }
  
	        return maespaDataForTimesteps;
	    }
	      


}
