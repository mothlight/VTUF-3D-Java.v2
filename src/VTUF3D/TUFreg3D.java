		// !
		// _____________________________________________________________________________________
		// !
		// ! VTUF-3D model
		// !
		// !
		// -------------------------------------------------------------------------------------
		//
		// ! This model is written primarily in Fortran 77 but uses some Fortran
		// 2003. Therefore
		// ! a Fortran 2003 compiler is required.
		// !
		// !
		// !
		// !
		// -------------------------------------------------------------------------------------
		// ! Original references:
		// !
		// ! Krayenhoff ES, Voogt JA (2007) A microscale three-dimensional urban
		// energy balance
		// ! model for studying surface temperatures. Boundary-Layer Meteorol
		// 123:433-461
		// !
		// ! Krayenhoff ES (2005) A micro-2scale 3-D urban energy balance model
		// for studying
		// ! surface temperatures. M.Sc. Thesis, University of Western Ontario,
		// London, Canada
		// !
		// -------------------------------------------------------------------------------------
		// !
		// ! *** This model is for research and teaching purposes only ***
		// !
		// !
		// -------------------------------------------------------------------------------------
		// !
		// ! Last updated:
		// ! September 2011 by Scott Krayenhoff
		// ! 2013-2016, modified heavily by Kerry Nice
		// !
		// !
		// _____________________________________________________________________________________



package VTUF3D;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import VTUF3D.Utilities.Common;
import VTUF3D.Utilities.MaespaDataFile;
import VTUF3D.Utilities.Namelist;

public class TUFreg3D
{
	//added these to restart crashed runs at a certain point. 
	// and number the output with the new values
	protected static boolean restartedRun = false;
	protected static int restartedRunStartTimestep = 0;
//	protected static int restartedRunNumber = 0;
	
	VTUF3DUtil util = new VTUF3DUtil();
	private String[] args;
	
	public static long startTime = System.currentTimeMillis();
	
	//to shift one indexed arrays to zero
	public final static int ONE = 0;
	public final static int TWO = 1;
	public final static int THREE = 2;
	public final static int FOUR = 3;
	public final static int FIVE = 4;
	public final static int SIX = 5;
	
	//TODO, try to shift these to zero based, but is probably a bit painful 
	public final static int FACE_ONE = 1;
	public final static int FACE_TWO = 2;
	public final static int FACE_THREE = 3;
	public final static int FACE_FOUR = 4;
	public final static int FACE_FIVE = 5;
	public final static int FACE_SIX = 6;
	
	// ! constants:
	public final static double sigma = 5.67e-8;
	public final static double cpair = 1004.67;
	public final static double vK = 0.4;
	
	OutputResults outputResults = new OutputResults();

	public static void main(String[] args)
	{
		TUFreg3D vtuf = new TUFreg3D();
		vtuf.args = args;
		vtuf.run();
	}

	public void run()
	{		
		MaespaConfigTreeMapState treeMapFromConfig; 

		int DIFFERENTIALSHADINGDIFFUSE;

		//TODO disabling some output for Urban Plumber
		boolean writeKabs = false;
		boolean writeKl = false;
		boolean writeLabs = false;
		boolean writeKrefl = false;
		boolean writeLrefl = false;
		boolean writeTsfc = true;
		boolean writeEnergyBalances = true;
		boolean writeLdown = false;
		boolean writeTmrt = true;
		boolean writeUtci = true;

		// !!KN, initializing it because it gets used below before any value is set if ldn is not calculated
		double Ldn_fact = 1.0; 

        String rootDirectory = args[0];
        if (rootDirectory == null || rootDirectory.trim().equals(""))
        {
        	System.out.println("no root directory");
        	System.exit(1);
        }
		OverallConfiguration overall = new OverallConfiguration(rootDirectory);
		treeMapFromConfig = overall.readMaespaTreeMapFromConfig(rootDirectory);
		treeMapFromConfig.rootDirectory = rootDirectory;
		DIFFERENTIALSHADINGDIFFUSE = treeMapFromConfig.usingDiffShading;

		if (treeMapFromConfig.usingDiffShading == 0)
		{
			System.out.println("DIFFERENTIALSHADING100PERCENT");
		}

		HashMap<String, ArrayList<MaespaDataResults>> maespaDataArray = overall.mapTrees(treeMapFromConfig);
		int[][] treeXYMap = overall.getTreeXYMap();
		int[][] treeXYTreeMap = overall.getTreeXYTreeMap();

		//TODO replace these with online versions
		HashMap<String, MaespaDataFile> maespaTestflxData = overall.readMaespaTestflxData(maespaDataArray);
		HashMap<String, HashMap<String, Namelist>> namelists = overall.readNamelists(treeMapFromConfig);

		double[][] treeXYMapSunlightPercentageTotal = new double[treeMapFromConfig.width][treeMapFromConfig.length];

		ParametersDat parameters = overall.readParametersDat();
		// //! MAIN PARAMETER AND INITIAL CONDITION INPUT FILE
		// //! read in the input file values
		// //! output file recording inputs:

		// ! model/integration parameters
		int vfcalc = parameters.vfcalc;
		int yd = parameters.yd;
		int year = parameters.year;
		double outpt_tm = parameters.outpt_tm;
		double Tthreshold = parameters.Tthreshold;
		boolean facet_out = parameters.facet_out;
		boolean matlab_out = parameters.matlab_out;
		boolean sum_out = parameters.sum_out;
		
		//now you can restart a crashed run
		restartedRun = parameters.restartedRun;
		restartedRunStartTimestep = parameters.restartedRunStartTimestep;
//		restartedRunNumber = parameters.restartedRunNumber;
		
		// ! radiative parameters
		double dalb = parameters.dalb;
		double albr = parameters.albr;
		double albs = parameters.albs;
		double albw = parameters.albw;
		double emisr = parameters.emisr;
		double emiss = parameters.emiss;
		double emisw = parameters.emisw;
		int cloudtype = parameters.cloudtype;

		// ! conduction parameters
		double IntCond = parameters.IntCond;
//		double Intresist = parameters.Intresist;
		double uc = parameters.uc;
		int numlayers = parameters.numlayers;

		double[] htcap = new double[numlayers];
		double[] thick = new double[numlayers];
		double[] tlayerp = new double[numlayers];
		double[] htcapr = parameters.htcapr;
		double[] htcaps = parameters.htcaps;
		double[] htcapw = parameters.htcapw;

		// ! convection parameters
		double z0 = parameters.z0;
		double lambdaf = parameters.lambdaf;
		double zrooffrc = parameters.zrooffrc;
		double z0roofm = parameters.z0roofm;
		double z0roadm = parameters.z0roadm;
		double z0roofh = parameters.z0roofh;
		double z0roadh = parameters.z0roadh;
		double moh = parameters.moh;
		double rw = parameters.rw;
		
		// ! domain geometry
		//this is obsolete now, calculated from the domains later on now
		double buildht_m = parameters.buildht_m;
		double zref = parameters.zref;
		int minres = parameters.minres;



		// ! loop parameters all
		double stror_in = parameters.stror_in;
		double strorint = parameters.strorint;
		double strormax = parameters.strormax;

		double xlat_in = parameters.xlat_in;
		double xlatint = parameters.xlatint;
		double xlatmax = parameters.xlatmax;
		int numlp = parameters.numlp;
		double[] lpin = parameters.lpin;
		int numbhbl = parameters.numbhbl;
		double[] bh_o_bl = parameters.bh_o_bl;

		boolean calcz0 = false;
		if (z0 < 0.)
		{
			calcz0 = true;
		}

		if (vfcalc == 0 && (numlp > 1 || numbhbl > 1))
		{
			System.out.println("must turn on calculation of view factors (i.e.vfcalc=1)if more than one lambdap or H/L ratio is chosen");
			// (i.e.vfcalc=1)if more than one lambdap or H/L ratio is chosen'
			System.out.println("vfcalc, numlp =" + " " + vfcalc + " " + numlp);
			System.exit(1);
		}

		if (Math.abs(xlat_in) > 90.0 || Math.abs(xlatmax) > 90.0)
		{
			System.out.println("one of xlat_in or xlatmax is greater than 90 or lessthan -90, xlat_in, xlatmax =" + " "
					+ xlat_in + " " + xlatmax);
			System.exit(1);
		}
		if (xlatint < 1e-9)
		{
			System.out.println("xlatint must be greater than 0, xlatint=" + " " + xlatint);
			System.out.println("if you do not want to simulate more than one latitude,set xlat_in=xlatmax");
			System.exit(1);
		}



//		outputResults.writeInputs(overall, vfcalc, yd, deltat, outpt_tm, Tthreshold,
//				facet_out, matlab_out, sum_out, dalb, albr, albs, albw, emisr, emiss, emisw, cloudtype,
//				IntCond, Intresist, uc, numlayers, thickr, lambdar, htcapr,
//				thicks, lambdas, htcaps, thickw, lambdaw, htcapw, z0, lambdaf, zrooffrc,
//				z0roofm, z0roadm, z0roofh, z0roadh, moh, rw, buildht_m, zref, minres,
//				Tsfcr, Tsfcs, Tsfcw, Tintw, Tints, Tfloor, Tbuild_min,
//				stror_in, strorint, strormax, xlat_in, xlatint,  xlatmax, numlp, lpin, numbhbl, bh_o_bl);

		
	

		
		VTUF3DLoop loop = new VTUF3DLoop();
		loop.loop(numlp, numbhbl, minres, vfcalc, lpin, treeMapFromConfig, buildht_m, zref, overall, numlayers,htcapr, htcaps, htcapw, uc, util, namelists, treeXYMap,
				z0roofh, z0roofm, z0roadh, z0roadm, moh, albs, emiss, treeXYTreeMap, 
				albr, emisr, albw, emisw,
				lambdaf, calcz0, z0, xlat_in, xlatmax, stror_in, strormax, facet_out, bh_o_bl, 
				yd, outpt_tm,				
				thick, IntCond,				
				Ldn_fact, cloudtype, treeXYMapSunlightPercentageTotal, maespaTestflxData,
				dalb,
				rw, zrooffrc,
				DIFFERENTIALSHADINGDIFFUSE, maespaDataArray,
				Tthreshold,
				tlayerp, htcap,
				sum_out, matlab_out, writeTsfc, writeKl, writeKabs, writeKrefl, writeLabs, writeLrefl, writeLdown,  writeTmrt, writeUtci, 
				writeEnergyBalances,  strorint,  xlatint,  year, restartedRunStartTimestep, rootDirectory, parameters);

	}

}








