package VTUF3D;

import java.util.ArrayList;
//import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.TreeMap;

//import Simpel.ETo;
import Simpel.SimpelConstants;
//import Simpel.SimpelModelTimestep;
import Simpel.SimpelSurface;
import VTUF3D.Utilities.Common;
import VTUF3D.Utilities.MaespaDataFile;
import VTUF3D.Utilities.Namelist;

public class VTUF3DLoop
{
//	//to shift one indexed arrays to zero
//	public final static int ONE = 0;
//	public final static int TWO = 1;
//	public final static int THREE = 2;
//	public final static int FOUR = 3;
//	public final static int FIVE = 4;
//	public final static int SIX = 5;
	
	Common common = new Common();
	Output output = new Output();
	EnergyBalances energyBalances = new EnergyBalances();
	OutputResults outputResults = new OutputResults();


	public static void main(String[] args)
	{
		
	}
	
	public void loop(int numlp, int numbhbl, int minres, int vfcalc, double[] lpin, MaespaConfigTreeMapState treeMapFromConfig, double buildht_m, 
			double zref, OverallConfiguration overall, int numlayers, 
			double[] htcapr, double[] htcaps, double[] htcapw, 
			double uc, VTUF3DUtil util, HashMap<String, HashMap<String, Namelist>> namelists, int[][] treeXYMap, 
			double z0roofh, double z0roofm, double z0roadh, double z0roadm, double moh, 
			double albs, double emiss, int[][] treeXYTreeMap, double albr, double emisr, double albw, double emisw, 
			double lambdaf, boolean calcz0, double z0, double xlat_in, double xlatmax, double stror_in, double strormax, boolean facet_out, 
			double[] bh_o_bl,
			int yd, double outpt_tm, 
			double[] thick, double IntCond, 
			double Ldn_fact, int cloudtype, double[][] treeXYMapSunlightPercentageTotal, 
			HashMap<String, MaespaDataFile> maespaTestflxData, 
			double dalb, double rw, double zrooffrc, 
			int DIFFERENTIALSHADINGDIFFUSE, HashMap<String, ArrayList<MaespaDataResults>> maespaDataArray, 
			double Tthreshold, double[] tlayerp, double[] htcap, 
			boolean sum_out, boolean matlab_out, boolean writeTsfc, boolean writeKl, boolean writeKabs, boolean writeKrefl, boolean writeLabs, 
			boolean writeLrefl, boolean writeLdown, boolean writeTmrt, boolean writeUtci, boolean writeEnergyBalances, double strorint, double xlatint, int year, 
			int restartedRunStartTimestep, String rootDirectory, ParametersDat parameters)
	{

		// ! initial temperatures
		double TsfcrC = parameters.Tsfcr;
		double TsfcsC = parameters.Tsfcs;
		double TsfcwC = parameters.Tsfcw;
		double TintwC = parameters.Tintw;
		double TintsC = parameters.Tints;
		double TfloorC = parameters.Tfloor;
		double Tbuild_min = parameters.Tbuild_min;
		double Tsfcr = TsfcrC + 273.15;
		double Tsfcs = TsfcsC + 273.15;
		double Tsfcw = TsfcwC + 273.15;
		double Tintw = TintwC + 273.15;
		double Tints = TintsC + 273.15;
		double Tfloor = TfloorC + 273.15;
		
		double deltat = parameters.deltat;
		
		// ! ATMOSPHERIC FORCING
		ForcingData forcing = new ForcingData(rootDirectory);
		forcing.readForcingData();
		int numfrc = forcing.getNumfrc();
		double starttime = forcing.getStarttime();
		double deltatfrc = forcing.getDeltatfrc();
		double dta_starttime = forcing.getStarttime();

		double[] Pressfrc = forcing.getPressfrc();
		double[] Udirfrc = forcing.getUdirfrc();
		double[] Kdnfrc = forcing.getKdnfrc();
		double[] Ldnfrc = forcing.getLdnfrc();
		double[] Tafrc = forcing.getTafrc();
		double[] eafrc = forcing.getEafrc();
		double[] Uafrc = forcing.getUafrc();
		double[] timefrc = forcing.getTimefrc();

		// ! Initial values:
		double press = Pressfrc[restartedRunStartTimestep];
		double Udir = Udirfrc[restartedRunStartTimestep];
		double Ktotfrc = Kdnfrc[restartedRunStartTimestep];
		double Ldn = Ldnfrc[restartedRunStartTimestep];
		double Ta = Tafrc[restartedRunStartTimestep] + 273.15;
		double ea = eafrc[restartedRunStartTimestep];
		double Ua = Math.max(0.1, Uafrc[restartedRunStartTimestep]);
		System.out.println("initial forcing data:");
		System.out.println("temperature (C), vapour pressure (mb) = " + " " + Ta + " " + ea);
		System.out.println("wind speed (m/s), wind direction (degrees) = " + " " + Ua + " " + Udir);
		System.out.println("pressure (mb) = " + " " + press);
		System.out.println("initial forcing data:");
		System.out.println("temperature (C), vapour pressure (mb) = " + " " + Ta + " " + ea);
		System.out.println("wind speed (m/s), wind direction (degrees) = " + " " + Ua + " " + Udir);
		System.out.println("pressure (mb) = " + " " + press);
		
		boolean calclf = false;
		boolean frcwrite = true;
		outputResults.openOutputFiles(overall, numfrc, starttime, deltatfrc, calclf, frcwrite);
	
		// ! initialization
		int tthresholdLoops = 0;
		int numberOfExtraTthresholdLoops = 3;
		double Kdn_diff = 0.;
		double nKdndiff = 0;
		int badKdn = 0;
		double svfe_store = 0.;
		double Kdn_ae_store = 0.;
		// !!KN, initializing it because it gets used below before any value is set
		double zH = 0; 
		
		// !print *,'Ldn,Ldn_fact,calcKdn,calcLdn',Ldn,Ldn_fact,calcKdn,calcLdn
		// ! assume initial Tcan!!!
//		double Tcan = Ta + 0.5;

		double timeis = starttime;
		double timeend = starttime + deltatfrc * (1.0*numfrc-1);
		double dta_timeend = timeend;

		// ! number of times output will be written in Matlab output section:
		int numout = (int) ((timeend - starttime) / outpt_tm) + 1;
		
		double httc=0;
//		int numsfc_ab;
		int numsfc2, jab;
		int timewrite = 0;
//		int numtrees2 = 0;
//		int numtreetops2 = 0;
		boolean ywrite = false;
		int nKgrid;
		int timefrc_index;
		int numTsun = 0, numTsh = 0, numNsun = 0, numNsh = 0, numSsun = 0, numSsh = 0, numEsun = 0;
		int numEsh = 0, numWsun = 0, numWsh = 0;
		int bl, bw, sw;
		double stror;
		double ypos = 0, Kdir = 0;
		double solarin, vfsum2;
		double Lup_refl_old;
		double Lemit5;
		double Kup_refl;
		double Kup_refl_old;
		double Ktot = 0;
		double abs_aero = 0;
		double angdif;
		double Kbeam;
//		double Qhcan;
		double Udirdom;
		double rhoa = 0;
//		double rhocan = 0;
//		double Cairavg;
//		double Tsfc_R;
		double cdtown, Fm = 0, ustar = 0, Qhcan_kin, wstar = 0;
		double Ccan = 0;
		double Bcan = 0, Acan = 0, zzz, Ucantst;
		double Qh_tot = 0, Rnet_tot = 0, Tsfc_cplt = 0;
		double Tsfc_bird = 0, Tsfc_N = 0, Tsfc_S = 0, Tsfc_E = 0, Tsfc_W = 0, Tsfc_T = 0, zwall;
		double rhohorz = 0, Rnet, Qg_tot = 0;
		double Tconv;
		double Tnew, Told, Fold, Fold_prime, Tdiffmax, Qhtop;
		double Qhcantmp = 0;
		double Rnet_R = 0, Qh_R = 0, Qg_R = 0, Rnet_T = 0, Qh_T = 0, Qg_T = 0, Rnet_N = 0, Qh_N = 0, Qg_N = 0;
		double Rnet_S = 0, Qh_S = 0, Qg_S = 0, Rnet_E = 0, Qh_E = 0, Qg_E = 0, Rnet_W = 0, Qh_W = 0, Qg_W = 0;		
		double Qe_tot = 0;
		double Qetot_avg = 0;
		double leFromEt5;
		int diffShadingValueUsed = 0;
		double diffShadingCalculatedValue;
		double Kdn_R = 0, Kup_R = 0, Ldn_R = 0, Lup_R = 0, Kdn_T = 0, Kup_T = 0, Ldn_T = 0, Lup_T = 0;
		double Kdn_N = 0, Kup_N = 0, Ldn_N = 0, Lup_N = 0, Kdn_S = 0, Kup_S = 0, Ldn_S = 0, Lup_S = 0;
		double Kdn_E = 0, Kup_E = 0, Ldn_E = 0, Lup_E = 0, Kdn_W = 0, Kup_W = 0, Ldn_W = 0, Lup_W = 0;
//		double thick_totr, thick_tots, thick_totw;
		double Emit_W, Absbl_W;
		double Absbs_W;
		double Qh_abovezH = 0, httcR = 0, httcW = 0, httcT = 0;
		double Qanthro = 0, Qanthro_avg = 0, Qtau_avg = 0, Qac = 0, Qac_avg = 0, Qdeep = 0, Qdeep_avg = 0;
		double Rntot_avg = 0, Qhtot_avg = 0, Qgtot_avg = 0, TR_avg = 0, TT_avg = 0;
		double Trad_R = 0, Trad_T = 0, Trad_N = 0, Trad_S = 0, Trad_E = 0, Trad_W = 0;
		double TN_avg = 0, TS_avg = 0, TE_avg = 0, TW_avg = 0;
		double Tp = 0;
		double TTsun = 0, TTsh = 0, TNsun = 0, TNsh = 0, TSsun = 0, TSsh = 0, TEsun = 0, TEsh = 0, TWsun = 0;
		double TWsh = 0;
		double zen = 0;
		double  Kup = 0;
		double Lup = 0;
		double Kdn_re_store = 0;
		double Kdn_grid = 0, Kdif = 0, DR1F = 0, Kuptot_avg = 0, Luptot_avg = 0;
		double[] angsun = new double[3];
		double[] angsfc = new double[3];

		int tempTimeis;
		String outputDebugStr;
		int numlayersMinus1 = numlayers-1;
		int numlayersMinus2 = numlayers-2;
		int sixPlusThreeTimesNumlayers = 6 + (3 * numlayers)-1;
		int fivePlusNumlayers = 5 + numlayers-1;
		
		
		
		
		
		SimpelSurface irrigatedGrassSimpelSurface = new SimpelSurface();
		TreeMap<String,Double> defaultSoilProperties = (TreeMap<String, Double>) SimpelConstants.Soil.clone();
		double lat=xlat_in;
		double lon=145;//TODO config files
		double meridian=0.0;
		double elevation=93.0;
		double windSpeedHeight=2.0;
		double timestep=defaultSoilProperties.get("Timestep");
		double fieldcapacity=defaultSoilProperties.get("Field Capacity %");
		double wiltingpoint=defaultSoilProperties.get("Permanent Wilting Point %");
		double startofreduction=defaultSoilProperties.get("Start of Reduction %");
		double rootdepth=defaultSoilProperties.get("Root Depth");
		double initvaluesoil=defaultSoilProperties.get("Init-Value Soil %");
		double landuse=defaultSoilProperties.get("Land use");
		double minlai=defaultSoilProperties.get("Minimum LAI");
		double maxlai=defaultSoilProperties.get("Maximum LAI");
		double vegetationfraction=defaultSoilProperties.get("Vegetation Fraction");
		double layerthickness=defaultSoilProperties.get("Layer Thickness");
		double drainagecoeff=defaultSoilProperties.get("Drainage Coeff. b");
		double maxdrainagerate=defaultSoilProperties.get("Max. Drainage Rate");
		double caplitter=defaultSoilProperties.get("Cap. Litter");
		double initvaluelitter=defaultSoilProperties.get("Init-Value Litter");
		double litterreductionfactor=defaultSoilProperties.get("Litter Reduction factor");
		double directrunofffactor=defaultSoilProperties.get("Direct runoff factor");	
		double gluglacoeff=defaultSoilProperties.get("Glugla coeff.");		
		irrigatedGrassSimpelSurface.initSurface(lat, lon, meridian, elevation, windSpeedHeight,
				 timestep,  fieldcapacity, wiltingpoint, startofreduction, rootdepth, initvaluesoil, landuse,
				 minlai, maxlai, vegetationfraction, layerthickness, drainagecoeff, maxdrainagerate,
				 caplitter, initvaluelitter, litterreductionfactor, directrunofffactor, gluglacoeff);
		HashMap<Integer,TreeMap<Integer,Double>> allSimpelPreviousTimesteps = new HashMap<Integer,TreeMap<Integer,Double>>();
		
		double calcRH = common.CalculateRHFromVapor(Ta, ea);
		Date currentSimuationTime = common.getCurrentSimulationDate(year,  yd, timeis);
		//TODO add simpelQE
		//TODO configure irrigation amounts, times
		double irrigationTime = 13.;
		double irrigationAmount =4.0;
		double simpelQe = irrigatedGrassSimpelSurface.runTimestep(Ta, calcRH, Ktotfrc, Ua, currentSimuationTime, irrigationTime, irrigationAmount, timeis);								
		System.out.println("simpelQe "+ (simpelQe)) ;
		
		//will write out vf data on first run, will reload on later runs
		String vfSavedData = overall.rootDirectory + "/" + "vf.ser";
		
		boolean calcKdn = false;
		boolean calcLdn = false; 
		double[] calcLndReturn = energyBalances.calcLdn(Ktotfrc, Ldnfrc, restartedRunStartTimestep, ea, Ta, TUFreg3D.sigma, cloudtype, calcKdn, calcLdn);
		Ldn_fact = calcLndReturn[0];
		Ldn = calcLndReturn[1];
		
		double Td = (4880.357 - 29.66 * Math.log(ea)) / (19.48 - Math.log(ea));
		int numlayersMinusOne = numlayers - 1 ;

		//  MAIN LOOP THROUGH BUILDING GEOMETRIES (lp and bhbl)
		for (int lpiter = 0; lpiter < numlp; lpiter++)
		{
			for (int bhiter = 0; bhiter < numbhbl; bhiter++)
			{
				// ! KN, taking out resolution stuff, since the buildings are built from the config files
				int minres_bh = minres; 
								
				boolean newlp = true;
				boolean newbhbl = true;
				vfcalc = 1;
				//  NOTE that these formulae assume that bl=bw (i.e. buildings with square footprints)
				//  AND that sw=sw2 (street widths are equal in both directions)

				if (lpin[lpiter] > 0.25) // ! KN taking out because specified in config now
				{
					sw = minres_bh;
					bl = (int) Math.round((1.0*sw) * Math.sqrt(lpin[lpiter]) * (Math.sqrt(lpin[lpiter]) + 1.) / (1. - lpin[lpiter]));
				}
				else
				{
					bl = minres_bh;
					sw = (int) Math.round((1.0*bl) * (1. / Math.sqrt(lpin[lpiter]) - 1.));
				}

				bw = bl;
				int bh = treeMapFromConfig.configTreeMapHighestBuildingHeight;
				//this used be from parameters.dat, but now is calculated from the domain
				buildht_m = bh;

				double patchlen = treeMapFromConfig.configTreeMapGridSize;
				System.out.println("------------------------------------------");
				System.out.println("patch length (m) = " + " " + patchlen);
				System.out.println("building height (m) = " + " " + buildht_m);
				System.out.println("building height (patches) = " + " " + bh);
				System.out.println("reference or forcing height (m) = " + " " + zref);
				overall.writeOutput(Constants.inputs_store_out, "patchlen,buildht_m,bh,zref");
				overall.writeOutput(Constants.inputs_store_out, patchlen + " " + buildht_m + " " + bh + " " + zref);
			
				int sw2 = sw;

				//  The following expressions control the size of the domain, which must be large
				//  enough so that the radiation is properly calculated (i.e., so that
				//  building walls and street in the central urban unit don't  see past the
				//  edge of the domain below roof level) - the expression currently used was
				//  arrived at by educated guess (essentially, either large sw or large bh
				//  relative to bl or bw is a problem, and requires a larger domain)
				int nbuildx = (int) Math.round(2. * (1.0*bh) / ((1.0*bl) + (sw)) * 5. / Math.sqrt((1.0*bh) / (sw)));
				int nbuildy = (int) Math.round(2. * (1.0*bh) / ((1.0*bw) + (sw2)) * 5. / Math.sqrt((1.0*bh) / (sw2)));
				if ((nbuildx % 2) == 0)
				{
					nbuildx = nbuildx + 1;
				}
				if ((nbuildy % 2) == 0)
				{
					nbuildy = nbuildy + 1;
				}

				overall.writeOutput(Constants.inputs_store_out, "nbuildx,nbuildy");
				overall.writeOutput(Constants.inputs_store_out, nbuildx + " " + nbuildy);
			
				//  Dimensions of the domain
				int aw = Math.max(bw * 5 + sw2 * 4, nbuildy * bw + (nbuildy - 1) * sw2);
				int al = Math.max(bl * 5 + sw * 4, nbuildx * bl + (nbuildx - 1) * sw);

				System.out.println("________________________________");
				System.out.println("number of buildings across domain in x, y directions:" + " " + Math.max(5, nbuildx)
						+ " " + Math.max(5, nbuildy));
			
				//  Defining the central 'urban unit' from which output is derived
				int b1 = (int) Math.round(Math.max(2., ((1.0*nbuildy) - 1.) / 2.) * (1.0*bw))
						+ (int) Math.round((Math.max(2., ((1.0*nbuildy) - 1.) / 2.) - 0.4999) * (1.0*sw2)) + 1;
				int b2 = b1 + bw + sw2 - 1;
				int a1 = (int) Math.round(Math.max(2., ((1.0*nbuildx) - 1.) / 2.) * (1.0*bl))
						+ (int) Math.round((Math.max(2., ((1.0*nbuildx) - 1.) / 2.) - 0.4999) * (1.0*sw)) + 1;
				int a2 = a1 + bl + sw - 1;

				aw = treeMapFromConfig.configTreeMapX;
				al = treeMapFromConfig.configTreeMapY;
				a1 = treeMapFromConfig.configTreeMapX1;
				a2 = treeMapFromConfig.configTreeMapX2;
				b1 = treeMapFromConfig.configTreeMapY1;
				b2 = treeMapFromConfig.configTreeMapY2;
				patchlen = treeMapFromConfig.configTreeMapGridSize;

				System.out.println("aw,al,a1,a2,b1,b2,patchlen,buildht_m,sw,bh,bl" + " " + aw + " " + al + " " + a1
						+ " " + a2 + " " + b1 + " " + b2 + " " + patchlen + " " + buildht_m + " " + sw + " " + bh + " " + bl);
		
				//  Geometric ratios of the central 'urban unit'
				double lpactual = 1.0 * bl * bw / (bl + sw) / (bw + sw2);
				double hwactual = 1.0 * bh * 2. / (sw + sw2);
				double bhblactual = 1.0 * bh * 2. / (bl + bw);
				System.out.println("building height, length, street width (patches) = " + " " + bh + " " + bl + " " + sw);
				System.out.println("domain dimension in x, y (patches) = " + " " + al + " " + aw);
				System.out.println("urban unit start & end in x (patches) = " + " " + a1 + " " + a2);
				System.out.println("urban unit start & end in y (patches) = " + " " + b1 + " " + b2);
				overall.writeOutput(Constants.inputs_store_out, "bh,bl,sw,lpactual,bhblactual,hwactual,aw,al,a1,a2");
				overall.writeOutput(Constants.inputs_store_out, bh + " " + bl + " " + sw + " " + lpactual + " "
						+ bhblactual + " " + hwactual + " " + aw + " " + al + " " + a1 + " " + a2);

				//  Initial atmospheric values:
				press = Pressfrc[TUFreg3D.restartedRunStartTimestep];
				Udir = Udirfrc[TUFreg3D.restartedRunStartTimestep];
				Ktotfrc = Kdnfrc[TUFreg3D.restartedRunStartTimestep];
				Ldn = Ldnfrc[TUFreg3D.restartedRunStartTimestep];
				Ta = Tafrc[TUFreg3D.restartedRunStartTimestep] + 273.15;
				ea = eafrc[TUFreg3D.restartedRunStartTimestep];
				Ua = Math.max(0.1, Uafrc[TUFreg3D.restartedRunStartTimestep]);
				
				double Intresist = parameters.Intresist;
				TreeMap layerDepthsReturnValues = energyBalances.getLayerDepths( numlayers, parameters, numlayersMinusOne, Intresist);
				double[] depthr=(double[]) layerDepthsReturnValues.get("depthr");
				double[] depths=(double[]) layerDepthsReturnValues.get("depths");
				double[] depthw=(double[]) layerDepthsReturnValues.get("depthw");
				double[] thickr=(double[]) layerDepthsReturnValues.get("thickr");
				double[] thicks=(double[]) layerDepthsReturnValues.get("thicks");
				double[] thickw=(double[]) layerDepthsReturnValues.get("thickw");
				double thick_totr=(double) layerDepthsReturnValues.get("thick_totr");
				double thick_tots=(double) layerDepthsReturnValues.get("thick_tots");
				double thick_totw=(double) layerDepthsReturnValues.get("thick_totw");
				double[] lambdaavr=(double[]) layerDepthsReturnValues.get("lambdaavr");
				double[] lambdaavs=(double[]) layerDepthsReturnValues.get("lambdaavs");
				double[] lambdaavw=(double[]) layerDepthsReturnValues.get("lambdaavw");
				double[] lambdar=(double[]) layerDepthsReturnValues.get("lambdar");
				double[] lambdas=(double[]) layerDepthsReturnValues.get("lambdas");
				double[] lambdaw=(double[]) layerDepthsReturnValues.get("lambdaw");
				layerDepthsReturnValues = null;

				deltat = util.calcUC(uc, numlayers, thickr, lambdaavr, htcapr, thicks, lambdaavs, htcaps, thickw, lambdaavw, htcapw, lambdar, lambdas, lambdaw, deltat);

				// Various settings and calculations
				boolean solar_refl_done = false;

				int par = 12;
				int par_ab = 5 + 4 * numlayers;

				double ralt = 90. - zen;

				// so that output will be written at the final timestep
				timeend = timeend + 1.5 * deltat / 3600.;

				// for Matlab visualization output
				boolean first_write = true;
				
				////////////////////
//				// Create the domain (call barray_cube)				
				 TreeMap createDomainBarrayCubeReturnValues= energyBalances.createDomainBarrayCube(al, aw, bw, bl, sw, sw2, bh, treeXYMap,
							namelists, treeMapFromConfig, patchlen, zref, zH);
				 int[][] bldhti = (int[][]) createDomainBarrayCubeReturnValues.get("bldhti");
				 int[][] veghti = (int[][]) createDomainBarrayCubeReturnValues.get("veghti");
				 int maxbh= (int) createDomainBarrayCubeReturnValues.get("maxbh");
				 int numroof = (int) createDomainBarrayCubeReturnValues.get("numroof");
				 double bldht_tot = (double) createDomainBarrayCubeReturnValues.get("bldht_tot");
				 createDomainBarrayCubeReturnValues = null;
				///////////////////////////

				zH = (bldht_tot) / (1.0*numroof);

				//  in metres:
				zH = zH * patchlen;

				//  thermal roughness lengths if not specified:
				if (z0roofh < 0.)
				{
					z0roofh = z0roofm / moh;
				}
				if (z0roadh < 0.)
				{
					z0roadh = z0roadm / moh;
				}

				if (z0roofh / z0roofm < (1. / 210.) || z0roadh / z0roadm < (1. / 210.))
				{
					System.out.println("'Problem; ratio too small: z0roof(h/m), z0road(h/m) = " + " "
							+ z0roofh / z0roofm + " " + z0roadh / z0roadm);
					System.exit(1);
				}

				double dTcan_old = 0.;

				double[] calcAirspaceReturn = energyBalances.calculateCanyonAirspace(aw, al, a1, a2, b1, b2, zH, patchlen, bldhti);
				double lambdapR = calcAirspaceReturn[0];
				double canyair = calcAirspaceReturn[1];

				int al2 = al;
				int aw2 = aw;

//				//  now declare:
//				int[][] veght = new int[al2 + 2][aw2 + 2];
//				int[][] bldht = new int[al2 + 2][aw2 + 2];
//				boolean[][][] surf_shade = new boolean[al2 + 2][aw2 + 2][bh + 2];
//				boolean[][][] veg_shade = new boolean[al2 + 1][aw2 + 1][bh + 2];
//				boolean[][][][] surf = new boolean[al2+1][aw2+1][bh+1][5+1];
//				double[] Uwrite = new double[(int) Math.round(zref - 0.5)];
//				double[] Twrite = new double[(int) Math.round(zref - 0.5)];
//
//				for (int x = 0; x < al + 2; x++)
//				{
//					for (int y = 0; y < aw + 2; y++)
//					{
//						bldht[x][y] = 0;
//						veght[x][y] = 0;
//					}
//				}
//
//				//  here, copy the bldhti array to bldht then deallocate bldhti array
//				for (int y = 0; y < aw2+1; y++)
//				{
//					for (int x = 0; x < al2+1; x++)
//					{
//						bldht[x][y] = bldhti[x][y];
//						veght[x][y] = veghti[x][y];
//						// ! also add up the number of tree surfaces (4 walls * tree height) + 1 roof
//						if (veght[x][y] > 0)
//						{
//							numtrees2 = numtrees2 + (4 * veght[x][y]);
//							numtreetops2 = numtreetops2 + 1;
//						}
//					}
//				}
				
				TreeMap declareStructuresReturn = energyBalances.declareDataStructures(al2, aw2, bh, al, aw, zref, bldhti, veghti);
				int numtrees2=(int) declareStructuresReturn.get("numtrees2");
				int numtreetops2=(int) declareStructuresReturn.get("numtreetops2");
				int[][] veght=(int[][]) declareStructuresReturn.get("veght");
				int[][] bldht=(int[][]) declareStructuresReturn.get("bldht");
				boolean[][][] surf_shade=(boolean[][][]) declareStructuresReturn.get("surf_shade");
				boolean[][][] veg_shade=(boolean[][][]) declareStructuresReturn.get("veg_shade");
				boolean[][][][] surf=(boolean[][][][]) declareStructuresReturn.get("surf");
				double[] Uwrite=(double[]) declareStructuresReturn.get("Uwrite");
				double[] Twrite=(double[]) declareStructuresReturn.get("Twrite");
				declareStructuresReturn = null;
				bldhti=null;
				veghti=null;

				TreeMap convertHeightsReturn = energyBalances.convertHeightsToShading(bh, aw2, al2, al, aw, a1, a2, b1, b2, bl, bw,
						bldht, veght, surf_shade, veg_shade, surf, treeMapFromConfig);				
				surf_shade=(boolean[][][]) convertHeightsReturn.get("surf_shade");
				veg_shade=(boolean[][][]) convertHeightsReturn.get("veg_shade");
				surf=(boolean[][][][]) convertHeightsReturn.get("surf");
				int numsfc=(int) convertHeightsReturn.get("numsfc");
				int numsfc_ab=(int) convertHeightsReturn.get("numsfc_ab");
				convertHeightsReturn=null;
				
				double[][] sfc_ab = new double[numsfc_ab][par_ab];
				int[] sfc_ab_map_x = new int[numsfc_ab];
				int[] sfc_ab_map_y = new int[numsfc_ab];
				int[] sfc_ab_map_z = new int[numsfc_ab];
				int[] sfc_ab_map_f = new int[numsfc_ab];
//				double[][] sfc = new double[numsfc][par];
//				int[] ind_ab = new int[numsfc];
				int[] vffile = new int[numsfc_ab];
				int[] vfppos = new int[numsfc_ab + 1];
				int[] vfipos = new int[numsfc_ab + 1];
				int[] mend = new int[numsfc_ab];
//				double[] refl_emist = new double[numsfc_ab];
				double[] absbs = new double[numsfc_ab];
				double[] absbl = new double[numsfc_ab];
				double[] tots = new double[numsfc_ab];
				double[] totl = new double[numsfc_ab];
				double[] refls = new double[numsfc_ab];
				double[] refll = new double[numsfc_ab];
				double[] reflts = new double[numsfc_ab];
				double[] refltl = new double[numsfc_ab];
				double[] reflps = new double[numsfc_ab];
				double[] reflpl = new double[numsfc_ab];
				double[] Tsfc = new double[numsfc_ab];
				double[] Trad = new double[numsfc_ab];
				double[] lambda_sfc = new double[numsfc_ab];
				double[] Qh = new double[numsfc_ab];
				double[] Qe = new double[numsfc_ab];

				double[] currentRnet = new double[numsfc_ab];
				double[] currentQe = new double[numsfc_ab];
				double[] currentQh = new double[numsfc_ab];
				double[] currentQg = new double[numsfc_ab];

				//  SFC_AB ARRAY (second dimension) - central urban unit; only patches to have 'history'
				//  1: i (sfc array)
				//  2: f (sfc array)
				//  3: z (sfc array)
				//  4: y (sfc array)
				//  5: x (sfc array)
				//  6 to 5+numlayers: layer temperatures (starting with layer closest to surface)
				//  5+numlayers+1 to 5+2*numlayers: layer thermal conductivities (avg)
				//  5+2*numlayers+1 to 5+3*numlayers: layer heat capacities
				//  5+3*numlayers+1 to 5+4*numlayers: layer thicknesses

				//  SFC ARRAY (second dimension) - all patches in the domain
				//  1: surface type (1=roof,2=street,3=wall)
				//  2: sunlit fraction (0 to 4 out of 4)
				//  3: albedo
				//  4: emissivity
				//  5: environment view factor (1-SVF)
				//  6: component of surface's normal vector pointing in x-direction
				//  7: component of surface's normal vector pointing in y-direction
				//  8: component of surface's normal vector pointing in z-direction
				//  9: 0-not in initial array, 1-in initial input array, 2-in area
				//  of interest for calculations (generally where output will come from
				//  10: x-value of patch center
				//  11: y-value of patch center
				//  12: z-value of patch center
				
				outputResults.writeInputs(overall, vfcalc, yd, deltat, outpt_tm, Tthreshold,
						facet_out, matlab_out, sum_out, dalb, albr, albs, albw, emisr, emiss, emisw, cloudtype,
						IntCond, Intresist, uc, numlayers, thickr, lambdar, htcapr,
						thicks, lambdas, htcaps, thickw, lambdaw, htcapw, z0, lambdaf, zrooffrc,
						z0roofm, z0roadm, z0roofh, z0roadh, moh, rw, buildht_m, zref, minres,
						Tsfcr, Tsfcs, Tsfcw, Tintw, Tints, Tfloor, Tbuild_min,
						stror_in, strorint, strormax, xlat_in, xlatint,  xlatmax, numlp, lpin, numbhbl, bh_o_bl);

//				iij = 1;

				//  POPULATE THE MAIN PARAMETER ARRAY (SFC)
				//  ideally this would be up with the initial surf array assignment
				//  to reduce looping, but the sfc array is not defined yet at that point
				int numroof2 = 0;
				int numstreet2 = 0;
				int numwall2 = 0;
				int numNwall2 = 0;
				int numSwall2 = 0;
				int numEwall2 = 0;
				int numWwall2 = 0;
				numtrees2 = 0;
				numtreetops2 = 0;
				int iIndex12 = 0-1; //start with -1 for 0 index arrays
				int iab = 0-1; //start with -1 for 0 index arrays
				double[][] sfc = new double[numsfc][par];
				sfc[1][9] = 0.;
				double avg_cnt = ((a2 - a1 + 1.) * (b2 - b1 + 1.));
				canyair = canyair / (1.0*avg_cnt) / (1. - lambdapR);
				System.out.println("bh,aw2,al2" + " " + bh + " " + aw2 + " " + al2);
				// print *,'bh,aw2,al2',bh,aw2,al2;
	
				TreeMap initMainArrayReturn = energyBalances.initMainArray(a1, a2, b1, b2, bh, aw2, al2, surf, iIndex12, iab,
						sfc, sfc_ab, sfc_ab_map_x, sfc_ab_map_y, sfc_ab_map_z, sfc_ab_map_f,
						albs, emiss, albr, albw, emisr, emisw, treeXYTreeMap, numlayers, Tsfcs,
						thicks, lambdaavs, htcaps, thickr, lambdaavr, htcapr,
						thickw, lambdaavw, htcapw, lambdas, lambdar, lambdaw, Tsfcw, Tsfcr,
						numstreet2, numroof2, numwall2, numNwall2, numSwall2, numEwall2, numWwall2,
						lambda_sfc, Tsfc);
				
				iab=(int)initMainArrayReturn.get("iab");
				sfc=(double[][])initMainArrayReturn.get("sfc");
				sfc_ab=(double[][])initMainArrayReturn.get("sfc_ab");
				sfc_ab_map_x=(int[])initMainArrayReturn.get("sfc_ab_map_x");
				sfc_ab_map_y=(int[])initMainArrayReturn.get("sfc_ab_map_y");
				sfc_ab_map_z=(int[])initMainArrayReturn.get("sfc_ab_map_z");
				sfc_ab_map_f=(int[])initMainArrayReturn.get("sfc_ab_map_f");
				numstreet2=(int)initMainArrayReturn.get("numstreet2");
				numroof2=(int)initMainArrayReturn.get("numroof2");
				numwall2=(int)initMainArrayReturn.get("numwall2");
				numNwall2=(int)initMainArrayReturn.get("numNwall2");
				numSwall2=(int)initMainArrayReturn.get("numSwall2");
				numEwall2=(int)initMainArrayReturn.get("numEwall2");
				numWwall2=(int)initMainArrayReturn.get("numWwall2");
				lambda_sfc=(double[])initMainArrayReturn.get("lambda_sfc");
				Tsfc=(double[])initMainArrayReturn.get("Tsfc");
				initMainArrayReturn = null;
				/////////////////////////////////////////////////////

				//started index with -1
				numsfc2 = iab+1;
				if (numsfc2 != numsfc_ab)
				{
					System.out.println("number of patches in the central urban unit incorrect");
					System.out.println("numsfc2!=numsfc_ab" + " " + numsfc2 + " " + numsfc_ab);
					System.exit(1);
				}

				// building + street widths in each horizontal dimension
				double wavelenx = (1.0*bl + sw);
				double waveleny = (1.0*bw + sw2);
				//  KN domain is not so regular now
				wavelenx = (1.0*treeMapFromConfig.configTreeMapCentralWidth / 2); 
				waveleny = (1.0*treeMapFromConfig.configTreeMapCentralLength / 2);

				//  Match each patch not in the central urban unit with its corresponding patch
				//  in the central urban unit. Its temperature will then evolve  according to that
				//  patch in the central urban unit. This is the optimization that allows this version 
				//  of the model to run much more quickly.
				boolean found = false;
				int iIndex = 0-1;//so can index by 0
				
				int[] ind_ab = energyBalances.matchGrids(a1, a2, b1, b2, bh, aw2, al2, surf, treeMapFromConfig,
						sfc_ab, numsfc2, found, iIndex, numsfc);
				////////////////////////////////////////

				numroof2 = Math.max(1, numroof2);
				numstreet2 = Math.max(1, numstreet2);
				numwall2 = Math.max(1, numwall2);
				numNwall2 = Math.max(1, numNwall2);
				numSwall2 = Math.max(1, numSwall2);
				numEwall2 = Math.max(1, numEwall2);
				numWwall2 = Math.max(1, numWwall2);

				//  For roof heat transfer - find average roof length
				double HW_avg2 = numwall2*1.0 / numstreet2*1.0 / 2.;
				double Lroof = zH / patchlen / HW_avg2 * lambdapR / (1. - lambdapR) * 2.;

				double lambdac = (1.0 * numwall2 + numroof2 + numstreet2 + numtrees2 + numtreetops2) / (numroof2 + numstreet2 + numtreetops2);

				//  from Macdonald, displacement height:
				double zd = zH * (1. + Math.pow(4.43, (-(lambdapR + lpactual) / 2.)) * ((lambdapR + lpactual) / 2. - 1.));

				//  frontal index:
				if (lambdaf < 0.0 || calclf)
				{
					lambdaf = (1.0*numwall2 + numtrees2) / 4. / (1.0*numstreet2 + numroof2 + numtreetops2);
					calclf = true;
					System.out.println("lambdaf will be calculated by the model = " + " " + lambdaf);
				}

				//  z0:
				if (calcz0)
				{
					//  Macdonald's method for z0 (estimate lambdaf for now - both it and z0
					//  will be calculated for all future timesteps)
					z0 = zH * (1. - zd / zH) * Math.exp( -1 * Math.pow((0.5 * 1.2 / Math.pow((0.4), 2) * (1. - zd / zH) * lambdaf), (-0.5)));
					System.out.println("domain z0 will be calculated by the model (m) = " + " " + z0);
				}

				//  for radiation (multiple refl by atm of sfc reflected solar back to sfc)
				//  (just an estimate - it has only a minor impact, and will be replaced with
				//  the actual overall surface albedo after the first timestep)
				double alb_sfc = (albr * (1.0*numroof2) + albs * (1.0*numstreet2)) / (1.0*numroof2 + numstreet2);

				System.out.println("zH,zd,z0 = " + " " + zH + " " + zd + " " + z0);
				System.out.println("lambdap,lambdac,lambdaf = " + " " + lambdapR + " " + lambdac + " " + lambdaf);
				System.out.println("H/L, H/W ratios = " + " " + bhblactual + " " + hwactual);
				System.out.println("numroof2,numstreet2,numwall2,numNwall2,numSwall2,numWwall2,numEwall2" + " "
						+ numroof2 + " " + numstreet2 + " " + numwall2 + " " + numNwall2 + " " + numSwall2 + " "
						+ numWwall2 + " " + numEwall2);

				overall.writeOutput(Constants.inputs_store_out, "zH,zd,z0,lambdapR,lambdac,lambdaf");
				overall.writeOutput(Constants.inputs_store_out,
						zH + " " + zd + " " + z0 + " " + lambdapR + " " + lambdac + " " + lambdaf);
				overall.writeOutput(Constants.inputs_store_out, "Lroof,HW_avg2,al2,aw2");
				overall.writeOutput(Constants.inputs_store_out, Lroof + " " + HW_avg2 + " " + al2 + " " + aw2);
				
				TreeMap vfReturnValues = energyBalances.viewFactors(bh, aw2, al2, surf, sfc, vfcalc, mend, numsfc2,
						a1, a2, b1, b2, sfc_ab, vffile, vfppos, vfipos, util,
						surf_shade, maxbh, ind_ab, vfSavedData);				
				sfc=(double[][]) vfReturnValues.get("sfc");
				mend=(int[]) vfReturnValues.get("mend");
				vffile=(int[]) vfReturnValues.get("vffile");
				vfppos=(int[]) vfReturnValues.get("vfppos");
				vfipos=(int[]) vfReturnValues.get("vfipos");
				HashMap<Integer,Double> vf3=(HashMap<Integer, Double>) vfReturnValues.get("vf3");
				HashMap<Integer,Integer> vf3j=(HashMap<Integer, Integer>) vfReturnValues.get("vf3j");
				int numvf=(int) vfReturnValues.get("numvf");
				int p=(int) vfReturnValues.get("p");
				vfReturnValues = null;
				

				// ------------------------------------------------------------------


				if (numvf != p )
				{
					System.out.println("PROBLEM WITH VFs IN MEM" + " " + (p ) + " " + numvf);
				}

				//  -----------------------------------
				// Latitude and street orientation loops (because geometry and view factors
				// need not be re-computed for new latitudes or street orientations)
				// New forcing data may be advisable for new latitudes, however

				double xlat = xlat_in;
				while (xlat <= xlatmax)
				{
					String latwrite2;
					stror = stror_in;
					while (stror <= strormax)
					{						
						outputResults.outputFacetOut(facet_out, overall, xlat, stror, patchlen, yd, lpin, bh_o_bl, lpiter, bhiter);

						overall.writeOutput(Constants.inputs_store_out, "________________________________________");
		
						// changing this so that the runs can be restarted at a later timestep
//						timeis = dta_starttime;
						timeis = TUFreg3D.restartedRunStartTimestep;
						timeend = dta_timeend;
						System.out.println("------------------------------------------");
						System.out.println( "simulation start time, end time (h) = " + " " + timeis + " " + dta_timeend);
		
						starttime = timeis;
						//  number of times output will be written in Matlab output section:
						numout = (int) ((timeend - starttime) / outpt_tm)
								+ Math.min(100, Math.max(1, (int) (1. / outpt_tm)));

						first_write = true;
						boolean last_write = false;

						Tsfc = util.initTsfc(Tsfc, surf, bh, aw2, al2, a2, b1, b2, a1, sfc, Tsfcs, Tsfcr, Tsfcw);
						
						TreeMap substrateReturnValues = energyBalances.initSubstrateTemperatures(numsfc2, sfc_ab, Tintw, Tints, numlayers, sfc,
								thick, lambda_sfc, numlayersMinus2, Tsfc, numlayersMinus1, IntCond);						
						double[] A = (double[]) substrateReturnValues.get("A");
						double[] B = (double[]) substrateReturnValues.get("B");
						double[] D = (double[]) substrateReturnValues.get("D");
						double[] R = (double[]) substrateReturnValues.get("R");
						double[] lambdaav = (double[]) substrateReturnValues.get("lambdaav");
						double[] gam = (double[]) substrateReturnValues.get("gam");
						double[] tlayer = (double[]) substrateReturnValues.get("tlayer");
						double[] denom = (double[]) substrateReturnValues.get("denom");
						sfc_ab= (double[][]) substrateReturnValues.get("sfc_ab");
						substrateReturnValues=null;

						//  INITIALIZATION BEFORE TIME INTEGRATION
//						int numabovezH = 0;
//						int numcany = 0;
//						for (int iabCount = 0; iabCount < numsfc_ab; iabCount++)
//						{
//							int i = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
//							if (sfc[i][Constants.sfc_in_array] > 1.5)
//							{
//								int iIndex3 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
//								if ((sfc[iIndex3][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
//								{
//									numcany = numcany + 1;
//								}
//								else
//								{
//									numabovezH = numabovezH + 1;
//								}
//							}
//						}
						int[] calcAboveReturn = energyBalances.calcAboveZh(numsfc_ab, patchlen, zH, sfc, sfc_ab);
						int numcany = calcAboveReturn[0];
						int numabovezH = calcAboveReturn[1];

						//  initial values:
						double Tcan = Tafrc[TUFreg3D.restartedRunStartTimestep] + 273.15 + 0.5;
						System.out.println(Tcan + " " + Tafrc[TUFreg3D.restartedRunStartTimestep]);
						double Qhcan = 0.;
//						Qecan = 0.;
						double Tsfc_R = Tsfcr * (1.0*numroof2);
						double rhocan = press * 100. / 287.04 / Tcan;

						// This is the average heat capacity of air per m2 below zH (and only
						// for the fraction of the plan area for which building heights < zH)
						// for 3-D geometries: (canyair is the height of the air column below
						// zH if all the buildings below zH were put into one massive building of height
						// much lower than zH, of course - covering the all vertical columns
						// up to zH for which building height < zH in the entire area of interest)
						// units of Cairavg: J/K/m2/unit square of area for which building height<0 or street
						double Cairavg = canyair * rhocan * TUFreg3D.cpair;

						// So that Tcan changes will not be larger than 0.1C (unstable?)
						// in one timestep (there are often instabilities in the ICs)...will
						// attempt to increase it later on
						double deltat2 = 0.1 * Cairavg * (1.0*avg_cnt) / ((1.0*numcany)
								* Math.max(Math.abs(Tcan - Tsfcs), Math.abs(Tcan - Tsfcw)) * (7.8 + 4.2 * Ua));
						double deltat_cond = deltat;
						deltat = Math.min(deltat, deltat2);
						System.out.println("new time step for Tcan stability (s) = " + " " + deltat);

						timeis = timeis + deltat / 3600.;

						// starting runs in the middle
//						timefrc_index = 0;
						timefrc_index = 0;		
						if (timeis > 0)
						{
							timefrc_index =  (int)Math.round(timeis/deltatfrc);
						}
						
						int counter2 = 0;
						int tim = 1;

						System.out.println("------------------------------------------");
						System.out.println("lambdap=" + " " + lpactual + " " + " H/L=" + " " + bhblactual + " " + " lat=" + " " + xlat + " " + " stror=" + " " + stror);
				
						//  plan area in patches
						double Aplan = (1.0*numroof2 + numstreet2);

						


						
						//  START OF MAIN TIME
						// LOOP----------------------------------------
						int counter = 0;
						while (timeis <= timeend)
						{
//System.out.println("++++++++++++++++++++++++start next while main time=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60 );								
							// do 309 while (timeis<=timeend)
							// !print *,'start 309'
							//  try to increase the timestep for the first 2 hours of simulation
							//  because often the disequilibrium of the ICs causes the above two
							//  tests to reduce the timestep drastically in the early going
							if (counter > 25)
							{
								if (deltat < 8.0 && 3. * deltat < deltat_cond)
								{
									timeis = timeis - deltat / 3600.;
									deltat = deltat * 3.;
									System.out.println("INCREASING TIMESTEP BY 200% TO:" + " " + deltat);
									// write(6,*)'INCREASING TIMESTEP BY 200%
									// TO:',deltat;
									counter = 0;
									// goto 937;
									timeis = timeis + deltat / 3600.;
									ywrite = true;
									continue;
								}
								//  try to increase the timestep every so often throughout the simulation
								//  fast increase if the timestep is small:
								else if (timeis < starttime + 2.0 && 1.5 * deltat < deltat_cond)
								{
									timeis = timeis - deltat / 3600.;
									deltat = deltat * 1.5;
									System.out.println("INCREASING TIMESTEP BY 50% TO:" + " " + deltat);
									counter = 0;
									// goto 937;
									timeis = timeis + deltat / 3600.;
									ywrite = true;
									continue;
								}
							}
							//  slower increase otherwise:
							if (counter > 100 && 1.3 * deltat < deltat_cond)
							{
								timeis = timeis - deltat / 3600.;
								counter = 0;
								deltat = deltat * 1.3;
								System.out.println("INCREASING TIMESTEP BY 30% TO:" + " " + deltat);
								// goto 937;
								timeis = timeis + deltat / 3600.;
								ywrite = true;
								continue;
							}
							//  INTERPOLATE FORCING DATA

							if (timefrc[timefrc_index] <= timeis)
							{
								timefrc_index = Math.min(numfrc + 1, timefrc_index + 1);
							}
							Ktotfrc = Kdnfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (Kdnfrc[timefrc_index] - Kdnfrc[timefrc_index - 1]);
							Ldn = Ldnfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (Ldnfrc[timefrc_index] - Ldnfrc[timefrc_index - 1]);
							Ta = Tafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (Tafrc[timefrc_index] - Tafrc[timefrc_index - 1]);
							Ta = Ta + 273.15;
							ea = eafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (eafrc[timefrc_index] - eafrc[timefrc_index - 1]);
							Ua = Math.max(0.1, Uafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1])
									/ deltatfrc * (Uafrc[timefrc_index] - Uafrc[timefrc_index - 1]));
							Udir = Udirfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (Udirfrc[timefrc_index] - Udirfrc[timefrc_index - 1]);
							press = Pressfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
									* (Pressfrc[timefrc_index] - Pressfrc[timefrc_index - 1]);
							//  Prata's formula (QJRMS 1996)
							if (calcLdn)
							{
								Ldn = (1. - (1. + 46.5 * ea / Ta)
										* Math.exp(-(Math.pow((1.2 + 3. * 46.5 * ea / Ta), (0.5))))) * TUFreg3D.sigma
										* Math.pow(Ta, 4);
								Td = (4880.357 - 29.66 * Math.log(ea)) / (19.48 - Math.log(ea));
								Ldn = Ldn * Ldn_fact;
							}

							Udir = (Udir % 360.);
							//  wind direction relative to the domain
							Udirdom = Udir - stror;
							if (Udirdom < 0.)
							{
								Udirdom = Udir + (360. - stror);
							}

							//  calculate frontal area index, taking into account the wind direction
							if (calclf)
							{
								if (Udirdom < 180.)
								{
									if (Udirdom < 90.)
									{
										lambdaf = (util.sind(Udirdom) * 1.0*numEwall2 + util.cosd(Udirdom) * 1.0*numNwall2) / (1.0*numstreet2 + numroof2);
									}
									else
									{
										lambdaf = (util.sind(Udirdom - 90.) * 1.0*numSwall2 + util.cosd(Udirdom - 90.) * 1.0*numEwall2) / (1.0*numstreet2 + numroof2);
									}
								}
								else if (Udirdom < 270.)
								{
									lambdaf = (util.sind(Udirdom - 180.) * 1.0*numWwall2 + util.cosd(Udirdom - 180.) * 1.0*numSwall2) / (1.0*numstreet2 + numroof2);
								}
								else
								{
									lambdaf = (util.sind(Udirdom - 270.) * 1.0*numNwall2 + util.cosd(Udirdom - 270.) * 1.0*numWwall2) / (1.0*numstreet2 + numroof2);
								}
							}

							if (calcz0)
							{
								//  Macdonald's method for z0
								z0 = zH * (1. - zd / zH) * Math.exp(-Math.pow((0.5 * 1.2 / Math.pow((0.4), 2) * (1. - zd / zH) * lambdaf), (-0.5)));
							}

							//  canyon-atm exchange:
							double Ri = util.SFC_RI(zref - zH + z0, Ta, Tcan, Ua);
							HashMap<String, Double> htcReturn = util.HTC(Ri, Ua, zref - zH + z0, z0, z0);
							double Fh = htcReturn.get("Fh");
							double httc_top = htcReturn.get("httc_out");
							double Tlog_fact = 0.74 * httc_top * (Tcan - Ta) / Math.pow(TUFreg3D.vK, 2) / Fh;

							//  -------------------------------------------
							//  Solar angle and incoming shortwave (direct & diffuse) routines
							double LAT = xlat * Math.PI / 180.;
							double TM = (timeis % 24.);
							int yd_actual = yd + (int) (timeis / 24.);
							yd_actual = (yd_actual % 365);
							//  SUNPOS calculates the solar angles
							HashMap<String, Double> sunposReturn = util.SUNPOS(yd_actual, TM, LAT);
							double zeni = sunposReturn.get("ZEN");
							double AZIM = sunposReturn.get("AZIM");
							double CZ = sunposReturn.get("CZ");
							double INOT = sunposReturn.get("INOT");
							double CA = sunposReturn.get("CA");
							double az = AZIM * 180. / Math.PI;
							zen = zeni * 180. / Math.PI;
							ralt = 90. - zen;

							double Ta_sol = Ta - 273.15;
							double Td_sol = Td - 273.15;
							//  CLRSKY accounts for attenuation by and multiple reflection with the atmosphere
							//  It essentially calculates direct and diffuse shortwave reaching the surface
							//  There is also a basic cloud parameterization in it
							HashMap<String, Double> clrskyreturn = util.CLRSKY(CZ, press / 10., zeni, Ta_sol, Td_sol,
									INOT, Kdir, Kdif, Ktot, CA, yd_actual, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
							CZ = clrskyreturn.get(VTUF3DUtil.CZ_INDEX);
							press = clrskyreturn.get(VTUF3DUtil.PRESS_INDEX);
							zeni = clrskyreturn.get(VTUF3DUtil.ZEN_INDEX);
							Ta_sol = clrskyreturn.get(VTUF3DUtil.AIR_INDEX);
							Td_sol = clrskyreturn.get(VTUF3DUtil.DEW_INDEX);
							INOT = clrskyreturn.get(VTUF3DUtil.INOT_INDEX);
							Kdir = clrskyreturn.get(VTUF3DUtil.DR1_INDEX);
							Kdif = clrskyreturn.get(VTUF3DUtil.DF1_INDEX);
							Ktot = clrskyreturn.get(VTUF3DUtil.GL1_INDEX);
							CA = clrskyreturn.get(VTUF3DUtil.CA_INDEX);
							alb_sfc = clrskyreturn.get(VTUF3DUtil.alb_sfc_INDEX);
							abs_aero = clrskyreturn.get(VTUF3DUtil.abs_aero_INDEX);
							Ktotfrc = clrskyreturn.get(VTUF3DUtil.Ktotfrc_INDEX);
							DR1F = clrskyreturn.get(VTUF3DUtil.DR1F_INDEX);
							
							double Kdir_NoAtm = INOT * Math.cos(zeni);
							double Kdir_Calc = Kdir;
							double Kdif_Calc = Kdif;

							// to allow the solar radiation routine to calc solar radiation amounts if they are not input
							if (!calcKdn)
							{
								if (Ktotfrc > 0.)
								{
									//  average of solar scheme DF/Ktot and that calculated from the Orgill/Hollands param			
									Kdif = (Ktotfrc - DR1F + Ktotfrc * Kdif / (Ktot + 1.e-9)) / 2.;
									Kdir = Ktotfrc - Kdif;
								}
								else
								{
									Kdif = 0.;
									Kdir = 0.;
								}
							}

							Ktot = Kdir + Kdif;

							//  SO THAT KBEAM (I.E. FLUX DENSITY PERP TO SUN) DOES NOT GET TOO BIG
							//  FOR LOW SUN ANGLES (IN CASE OBSERVED KDN AND CALCULATED KDN DO NOT AGREE EXACTLY)
							if (!calcKdn && (Kdir - Kdir_Calc) / Math.max(1.e-9, Kdir_Calc) > 0.15 && ralt < 10.0)
							{
								double kDirPrev = Kdir;
								Kbeam = Math.min(INOT * Kdir_Calc / Math.max(1.e-9, Kdir_NoAtm), Kdir / Math.max(1.e-9, util.sind(ralt)));
if (Kbeam > 10000)
{
	Kbeam = 0.0;
}														
								Kdir = Kbeam * util.sind(ralt);
								Kdif = Ktotfrc - Kdir;
							}
							else
							{
								Kbeam = Kdir / Math.max(1.e-9, util.sind(ralt));
//System.out.println("Kbeam2 " + Kbeam+ " " + ralt);
if (Kbeam > 10000)
{
	Kbeam = 0.0;
}								
								if (Kbeam > 1390.)
								{
									System.out.println("KBEAM unreasonable; Kbeam,Kdir,ralt,sind(ralt) = " + " " + Kbeam
											+ " " + Kdir + " " + ralt + " " + util.sind(ralt));
	
									overall.writeOutput(Constants.inputs_store_out,
											"KBEAM unreasonable; Kbeam,Kdir,ralt,sind(ralt) = " + " " + Kbeam + " "
													+ Kdir + " " + ralt + " " + util.sind(ralt));
			
									if (Kbeam > 1370.0 * 2.0 || Ktot > 1370.)
									{
										System.out.println(
												"KBEAM or KTOT unreasonable; Ktot,Kbeam,Kdir,ralt,sind(ralt) = " + " "
														+ Ktot + " " + Kbeam + " " + Kdir + " " + ralt + " "
														+ util.sind(ralt));
						
										overall.writeOutput(Constants.inputs_store_out,
												"KBEAM or KTOT unreasonable; Ktot,Kbeam,Kdir,ralt,sind(ralt) = " + " "
														+ Ktot + " " + Kbeam + " " + Kdir + " " + ralt + " "
														+ util.sind(ralt));
						
										System.exit(1);
									}
								}
							}
							// !treeXYMapSunlightPercentageTotal=0.
							// !treeXYMapSunlightPercentagePoints=0.

							if (Ktot > 1.0E-3)
							{
//System.out.println("++++++++++++++++++++++++start Shade=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60. );	
								//  Solar shading of patches
								// -----------------------------------------
								//TODO figure out how to replace TestflxData, variable TD (total transission) with online Maespa
										HashMap shadeReturn = Shade.shade(stror, az, ralt, ypos, surf, surf_shade, al2, aw2,
										maxbh, par, sfc, numsfc, a1, a2, b1, b2, numsfc2, sfc_ab, par_ab, veg_shade,
										timeis, yd_actual, treeXYMapSunlightPercentageTotal, treeXYMap,
										maespaTestflxData);
								sfc = (double[][]) shadeReturn.get("sfc");
								sfc_ab = (double[][]) shadeReturn.get("sfc_ab");
								treeXYMapSunlightPercentageTotal = (double[][]) shadeReturn.get("treeXYMapSunlightPercentageTotal");
//System.out.println("++++++++++++++++++++++++end Shade=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60. );	
							}
							
							
							
							System.out.println(timeis + " " + outpt_tm + " " + deltat + " " + timewrite);
							if (
//									(timeis == 0) ||
//									(
											(timeis % outpt_tm) * 3600.0 < deltat && (int) (timeis * 100.) != timewrite
//									)
							   )
							{
								System.out.println("after met at " + TM);
								//TODO put soil module in here, to run each hour
								
//								 // run Simpel for the timestep
//				        		HashMap<Integer,Double> simpelMetInput = new HashMap<Integer,Double>();
////				        		String[] InputStr = new String[] {"20.1.2021.0","20","0","63.7493333333333","13.49","0.255833333333333","0","0"};
//				        		simpelMetInput.put(SimpelConstants.INPUT_P, 0.); //TODO, no precipitation in forcing data yet
//				        		simpelMetInput.put(SimpelConstants.INPUT_T14, Ta);
//				        								        		
//				        		double calcRH = common.CalculateRHFromVapor(Ta, ea);
//				        		
//				        		simpelMetInput.put(SimpelConstants.INPUT_R14, calcRH);		
//				        		simpelMetInput.put(SimpelConstants.INPUT_K_DOWN, Ktotfrc);
//				        								        	   
//				        		long month = Math.round(yd_actual/30.);//TODO set the actual month
//				        		simpelMetInput.put(SimpelConstants.INPUT_DOY, yd_actual*1.0);
//				        		simpelMetInput.put(SimpelConstants.INPUT_MONTH, month*1.0);
//				        		simpelMetInput.put(SimpelConstants.INPUT_HOUR, TM*1.0);
//				        		
////				        		System.out.println("inputhour "+common.roundTwoDecimals(TM));
//				        		if (simpelMetInput.get(SimpelConstants.INPUT_HOUR) == 13) //TODO, set from property file, what time, how much irrigation
//				        		{
//				        			simpelMetInput.put(SimpelConstants.INPUT_IRR, 4.0);
//				        		}
//				        				
//				        		//TODO, for now just using a single iabCount
//				        		int iabCount = 0;
//				        		//ok if this is null for the first time, will be filled in the timestep function
//				        		TreeMap<Integer,Double> simpelPreviousTimestepValues = allSimpelPreviousTimesteps.get(iabCount);
//				        		
//				        		ETo eto = new ETo();						        		
////				        		The latitude of the met station (dec deg) 
//				        		double lat=-37.5;
////				        		The longitude of the met station (dec deg) (only needed if calculating ETo hourly)
//				        		double lon=145;
////				        		The longitude of the center of the time zone (dec deg) (only needed if calculating ETo hourly).
//				        		double TZ_lon=145;
////				        		Elevation of the met station above mean sea level (m) 
//				        		double z_msl=500;
////				        		The height of the wind speed measurement (m). Default is 2 m.
//				        		double z_u=2;
////				        		Wind speed at height z (m/s), set to NaN to calculate
//				        		double U_z=Double.NaN;
////				        		Albedo. Should be 0.23 for the reference crop.
//				        		double alb = 0.23;
////				        		Day of Year
//				        		int Day = yd_actual;		
////				        		Time frequency string of the input and output. The minimum frequency is hours (H) and the maximum is month (M).
//				        		int freq=ETo.HOURLY;
////				        		Time of day
//				        		int hour = (int) Math.round(TM);		
////				        		Incoming shortwave radiation (MJ/m2)
//				        		double R_s_hourly = Ktotfrc * 60. * 60. * 1E-6;  
////				        		Actual Vapour pressure derrived from RH
//				        		double e_a_hourly = ea;  
////				        		Mean Temperature (deg C)
//				        		double T_mean_hourly = Ta;
//				        		// if no incoming shortwave, then nighttime
//				        		boolean daytime = true;
//				        		if (Ktotfrc < 50)
//				        		{
//				        			daytime = false;
//				        		}
//				        		
//				        		double Rnet_simpel = absbl[iabCount] + absbs[iabCount];
//				        		
//				        		double etoValue = eto.eto_fao_hourly(freq, lat, Day, lon, TZ_lon, z_msl, e_a_hourly, R_s_hourly, T_mean_hourly, z_u, U_z, alb, hour, daytime, Rnet_simpel);
//
//				        		double[][] simpelReturnValues = simpel.SIMPLE_function(simpelMetInput, SimpelConstants.Landuse, SimpelConstants.LAI_model, 
//				        														SimpelConstants.Soil, simpelPreviousTimestepValues, etoValue);    		
//				        		simpelPreviousTimestepValues = simpel.setPreviousValues(simpelReturnValues);
//				        		allSimpelPreviousTimesteps.put(iabCount,simpelPreviousTimestepValues);
//				        		
//				        		double simpelETA = simpelReturnValues[0][SimpelConstants.ETA_TOTAL];
//				        		double simpelQe = simpel.qeFromETA2(simpelETA);
//				        		System.out.println("             ++++++ eto "+ common.roundTwoDecimals(etoValue) + " " +  common.roundTwoDecimals(simpelETA )
//				        				+ " " + common.roundTwoDecimals(simpelQe)
//				        				+ " " + common.roundTwoDecimals(Ktotfrc)
//				        				+ " " + common.roundTwoDecimals(Rnet_simpel)) ;
								
								calcRH = common.CalculateRHFromVapor(Ta, ea);
								currentSimuationTime = common.getCurrentSimulationDate(year,  yd, timeis);
								//TODO add simpelQE
								//TODO configure irrigation amounts, times
								irrigationTime = 13.;
								irrigationAmount =4.0;
					           	simpelQe = irrigatedGrassSimpelSurface.runTimestep(Ta, calcRH, Ktotfrc, Ua, 
					           			currentSimuationTime, irrigationTime, irrigationAmount, timeis);								
				        		System.out.println("eto "+ (simpelQe)) ;
				        		
//				        		simpelQe=0;	    						        		
				        		// end Simpel	
				        		System.exit(1);
								
							}

							for (int iabCount = 0; iabCount < numsfc_ab; iabCount++)
							{
								absbs[iabCount] = 0.;
								refls[iabCount] = 0.;
								reflts[iabCount] = 0.;
								refltl[iabCount] = 0.;
							}
														
							//  CONTINUATION POINT FOR Tsfc-Lup balance iterations (below)--------
							boolean tsfcLupBalanceContinue = true;
							while (tsfcLupBalanceContinue)
							{
								// 898 continue
								// !print *,'after 898'
								Tdiffmax = 0.;

								if (solar_refl_done || Ktot <= 0.)
								{
									//  ---------------------------------
									//  LONGWAVE ONLY (solar has already been done in previous Tsfc-Lup  iteration)
									//  RADIATION INITIALIZATION

									//  zeroth longwave reflection (i.e. emission)
									vfsum2 = 0.;

									for (int iabCount = 0; iabCount < numsfc2; iabCount++)
									{
										int iIndex4 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
										refltl[iabCount] = 0.;
										refll[iabCount] = sfc[iIndex4][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										absbl[iabCount] = 0.;
										vfsum2 = vfsum2 + (1. - sfc[iIndex4][Constants.sfc_evf]);
									}
									//  MULTIPLE REFLECTION
									Lup = 0.;
									double Lup_refl = 0.;
									Lup_refl_old = 0.;
									double refldiff = 1.1;
									Lup_refl = 0.;
									Lemit5 = 0.;
									int k = 0;
									//  MAIN reflection loop: does at least 1 longwave reflection, and goes until change in
									// overall (1-emis) is less than dalb multiplied by a factor that recognizes that there is
									// little or no multiple reflection at roof level and above (lambdapR is lambdap at roof level)
									while ((k < 2) || (refldiff >= dalb * (1. - lambdapR)))
									{
										k = k + 1;
										if (k > 20) // otherwise, we seem to get trapped in this loop
										{
											// exit ;
											break;
										}

										// save reflected values from last reflection
										for (int iabCount = 0; iabCount < numsfc2; iabCount++)
										{
											int iIndex5 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
											reflpl[iabCount] = refll[iabCount];
											refll[iabCount] = 0.;
											if (k == 1)
											{
												absbl[iabCount] = sfc[iIndex5][Constants.sfc_emiss] * (1. - sfc[iIndex5][Constants.sfc_evf]) * Ldn;
												if (absbl[iabCount] > 2000.)
												{
													System.out.println("1,iab,absbl[iab]" + " " + iabCount + " " + absbl[iabCount]);
												}
												refll[iabCount] = (1. - sfc[iIndex5][Constants.sfc_emiss]) * (1. - sfc[iIndex5][Constants.sfc_evf]) * Ldn;
												Lup_refl = Lup_refl - sfc[iIndex5][Constants.sfc_emiss] * (1. - sfc[iIndex5][Constants.sfc_evf]) * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
												Lemit5 = Lemit5 + sfc[iIndex5][Constants.sfc_emiss] * sfc[iIndex5][Constants.sfc_evf] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
												refltl[iabCount] = 0.;
											}
										}
										// open view factor files
										for (int iabCount = 0; iabCount < numsfc2; iabCount++)
										{
											double vfOpen;
											int iIndex5 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
											// do p=vfppos[iab],vfppos[iab+1]-1
											for (int pCount = vfppos[iabCount]; pCount < vfppos[iabCount + 1]  ; pCount++)
											{
//												vf = vf3[pCount];
												vfOpen = vf3.get(pCount);
//												jab = vf3j[pCount];
												jab = vf3j.get(pCount);
												absbl[iabCount] = absbl[iabCount] + vfOpen * reflpl[jab] * sfc[iIndex5][Constants.sfc_emiss];
												if (absbl[iabCount] > 2000.)
												{
													// write(6,*)"2,iab,absbl[iab]",iab,absbl[iab]
												}
												refll[iabCount] = refll[iabCount] + vfOpen * reflpl[jab] * (1. - sfc[iIndex5][Constants.sfc_emiss]);
											}

											if (sfc[iIndex5][Constants.sfc_in_array] > 1.5)
											{
												Lup = Lup + (1. - sfc[iIndex5][Constants.sfc_evf]) * reflpl[iabCount];
												Lup_refl = Lup_refl + (1. - sfc[iIndex5][Constants.sfc_evf]) * reflpl[iabCount];
											}

											
										}

										for (int iabCount = 0; iabCount < numsfc2; iabCount++)
										{
											refltl[iabCount] = refltl[iabCount] + refll[iabCount];
										}

										refldiff = (Lup_refl - Lup_refl_old) / (1.0*avg_cnt) / (Ldn + Lemit5 / (1.0*avg_cnt));

										Lup_refl_old = Lup_refl;
										// 313 continue
									}
									for (int iabCount = 0; iabCount < numsfc2; iabCount++)
									{
										int iIndex5 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
										refltl[iabCount] = refltl[iabCount] - sfc[iIndex5][Constants.sfc_evf] * refll[iabCount];
										absbl[iabCount] = absbl[iabCount] + sfc[iIndex5][Constants.sfc_evf] * refll[iabCount];
									}
									// ------------------------------------
								}
								else
								{
									// SOLAR and LONGWAVE (solar has NOT already been done in previous Tsfc-Lup iteration)
									// RADIATION INITIALIZATION

									// the unit vector pointing from the surface towards the sun
									angdif = az - stror;
									if (angdif < 0.)
									{
										angdif = az + (360. - stror);
									}
									angsun[TUFreg3D.ONE] = util.sind(angdif) * util.cosd(ralt);
									angsun[TUFreg3D.TWO] = util.cosd(angdif) * util.cosd(ralt);
									angsun[TUFreg3D.THREE] = util.sind(ralt);

									// ! first solar absorption and reflection, and zeroth longwave reflection (i.e. emission)
									solarin = 0.;
									Kdn_grid = 0.;
									nKgrid = 0;
									vfsum2 = 0.;

									for (int iabCount = 0; iabCount < numsfc_ab; iabCount++)
									{
										int iIndex6 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
										refll[iabCount] = sfc[iIndex6][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										absbl[iabCount] = 0.;
										if (first_write)
										{
											vfsum2 = vfsum2 + (1. - sfc[iIndex6][Constants.sfc_evf]);
										}
										if (Ktot > 1.0e-3)
										{
											absbs[iabCount] = (1. - sfc[iIndex6][Constants.sfc_albedo]) * Kdif * (1. - sfc[iIndex6][Constants.sfc_evf]);
//System.out.println("absbs[iabCount]1 " + absbs[iabCount] + " " + timeis);
											refls[iabCount] = sfc[iIndex6][Constants.sfc_albedo] * Kdif * (1. - sfc[iIndex6][Constants.sfc_evf]);

											Kdn_grid = Kdn_grid + Kdif * (1. - sfc[iIndex6][Constants.sfc_evf]);
											nKgrid = nKgrid + 1;
											if (sfc[iIndex6][Constants.sfc_in_array] > 1.5)
											{
												solarin = solarin + Kdif * (1. - sfc[iIndex6][Constants.sfc_evf]);
											}
										}
										// ! if patch is at least partly sunlit:
										if (sfc[iIndex6][Constants.sfc_sunlight_fact] > 0.5)
										{
											angsfc[TUFreg3D.ONE] = sfc[iIndex6][Constants.sfc_x_vector];
											angsfc[TUFreg3D.TWO] = sfc[iIndex6][Constants.sfc_y_vector];
											angsfc[TUFreg3D.THREE] = sfc[iIndex6][Constants.sfc_z_vector];
											//  if we stay with plane parallel surfaces, the following dot product
											//  need only be computed 3-4 times (roof/street plus 2-3 sunlit walls)
											// call dotpro(angsun,angsfc,3,dp,g)
											HashMap<String, Double> returnValues = Dotpro.dotpro(angsun, angsfc, 3);
											double dp, g;
											dp = returnValues.get("dp");
											g = returnValues.get("g");

											absbs[iabCount] = absbs[iabCount] + (1. - sfc[iIndex6][Constants.sfc_albedo])
													* Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;
//System.out.println("absbs[iabCount]2 " + absbs[iabCount]);
											refls[iabCount] = refls[iabCount] + sfc[iIndex6][Constants.sfc_albedo] * Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;

											Kdn_grid = Kdn_grid + Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;

											if (sfc[iIndex6][Constants.sfc_in_array] > 1.5)
											{
												solarin = solarin + Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;
											}
										}
										else
										{
											absbs[iabCount] = 0.;
											refls[iabCount] = 0.;
										}
										reflts[iabCount] = refls[iabCount];
									}
									if (Math.abs(vfsum2 - (1.0*avg_cnt)) / (1.0*avg_cnt) > 0.05 && first_write)
									{
										System.out.println("patch sky view factor sum > 5% inaccurate");
										System.out.println("value = " + " " + vfsum2 + " " + "should be = " + " " + avg_cnt);
										System.exit(1);
									}
									if (first_write)
									{
										double svferror = 100. * Math.abs(vfsum2 - (1.0*avg_cnt)) / (1.0*avg_cnt);
										if (svferror > svfe_store)
										{
											svfe_store = svferror;
										}
										System.out.println("ABSOLUTE VALUE OF RELATIVE SKY VIEW FACTOR ERROR ->" + " " + svferror + " " + "%");

										overall.writeOutput(Constants.inputs_store_out,
												"-----lambdap,H/L,latitude,streetdir" + " " + lpin[lpiter] + " "
														+ bh_o_bl[bhiter] + " " + xlat + " " + stror + " " + "-----");
										overall.writeOutput(Constants.inputs_store_out,
												"ABSOLUTE VALUE OF RELATIVE SVF ERROR ->" + " " + svferror + " "
														+ "% (for the central urban unit)");
										System.out.println("------------------------------------------");
									}

									// compare input Kdn (wrong due to raster grid causing too many
									// or too few patches to be sunlit - representing patches by their center)
									// the resolution for only the shading routine could be increased to help deal with this problem
									Kdn_grid = Kdn_grid / ((wavelenx * waveleny));
									if (Kdir + Kdif > 0.0)
									{
										Kdn_diff = Kdn_diff + 100. * Math.abs(Kdn_grid - Kdir - Kdif) / (Kdir + Kdif + 1.e-9);
										nKdndiff = nKdndiff + 1;
									}
									if (Math.abs(Kdn_grid - Kdir - Kdif) > Kdn_ae_store)
									{
										Kdn_ae_store = Math.abs(Kdn_grid - Kdir - Kdif);
										Kdn_re_store = Math.abs(Kdn_grid - Kdir - Kdif) / (Kdir + Kdif + 1.e-9);
									}
			
									// MULTIPLE REFLECTION
									//  do the same number of reflections for  both solar and longwave, doing the long- and short-wave
									// reflections together is for efficiency reasons: view factors then
									// only have to be read in once instead of twice
									Kup = 0.;
									Lup = 0.;
									Kup_refl = 0.;
									double Lup_refl = 0.;
									Kup_refl_old = 0.;
									Lup_refl_old = 0.;
									double refldiff = 1.1;
									Lup_refl = 0.;
									Lemit5 = 0.;
									int k = 0;

									//  MAIN reflection loop: does at least 2 shortwave and 1 longwave reflection, and goes until change in
									// both overall albedo and overall (1-emis) are less than dalb multiplied by a
									// factor that recognizes that there is little or no multiple reflection at roof level and above (lambdapR is  lambdap at roof level)
									while (k < 2 || refldiff >= dalb * (1. - lambdapR))
									{
										// do 314 while
										// (k<2||refldiff>=dalb*(1.-lambdapR))
										k = k + 1;

										// save reflected values from last reflection
										for (int iabCount = 0; iabCount < numsfc_ab; iabCount++)
										{
											int iIndex7 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
											reflps[iabCount] = refls[iabCount];
											reflpl[iabCount] = refll[iabCount];
											refls[iabCount] = 0.;
											refll[iabCount] = 0.;
											if (k == 1)
											{
												absbl[iabCount] = sfc[iIndex7][Constants.sfc_emiss] * (1. - sfc[iIndex7][Constants.sfc_evf]) * Ldn;
												refll[iabCount] = (1. - sfc[iIndex7][Constants.sfc_emiss]) * (1. - sfc[iIndex7][Constants.sfc_evf]) * Ldn;
												if (sfc[iIndex7][Constants.sfc_in_array] > 1.5)
												{
													Lup_refl = Lup_refl - sfc[iIndex7][Constants.sfc_emiss] * (1. - sfc[iIndex7][Constants.sfc_evf]) * TUFreg3D.sigma
															* Math.pow(Tsfc[iabCount], 4);
													Lemit5 = Lemit5 + sfc[iIndex7][Constants.sfc_emiss] * sfc[iIndex7][Constants.sfc_evf]
																	* TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
												}
												refltl[iabCount] = 0.;
											}
										}

										// open view factor files
										for (int iabCount = 0; iabCount < numsfc2; iabCount++)
										{
											double vfOpen;
											int iIndex8 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
											for (int pCount = vfppos[iabCount]; pCount < vfppos[iabCount + 1] ; pCount++)
											{
//												vf = vf3[pCount];
												vfOpen = vf3.get(pCount);
//												jab = vf3j[pCount];
												jab = vf3j.get(pCount);
												absbs[iabCount] = absbs[iabCount] + vfOpen * reflps[jab] * (1. - sfc[iIndex8][Constants.sfc_albedo]);
//if (iabCount==0) System.out.println("absbs[iabCount]3 " + absbs[iabCount]);
												refls[iabCount] = refls[iabCount] + vfOpen * reflps[jab] * sfc[iIndex8][Constants.sfc_albedo];
												absbl[iabCount] = absbl[iabCount] + vfOpen * reflpl[jab] * sfc[iIndex8][Constants.sfc_emiss];
												refll[iabCount] = refll[iabCount] + vfOpen * reflpl[jab] * (1. - sfc[iIndex8][Constants.sfc_emiss]);
											}

											if (sfc[iIndex8][Constants.sfc_in_array] > 1.5)
											{
												Kup = Kup + (1. - sfc[iIndex8][Constants.sfc_evf]) * reflps[iabCount];
												Lup = Lup + (1. - sfc[iIndex8][Constants.sfc_evf]) * reflpl[iabCount];
												Lup_refl = Lup_refl + (1. - sfc[iIndex8][Constants.sfc_evf]) * reflpl[iabCount];
												Kup_refl = Kup_refl + (1. - sfc[iIndex8][Constants.sfc_evf]) * reflps[iabCount];
											}

										}

										for (int iabCount = 0; iabCount < numsfc2; iabCount++)
										{
											reflts[iabCount] = reflts[iabCount] + refls[iabCount];
											refltl[iabCount] = refltl[iabCount] + refll[iabCount];
										}

										// parameter that determines whether or not to do another reflection
										refldiff = Math.max( (Lup_refl - Lup_refl_old) / (1.0*avg_cnt) / (Ldn + Lemit5 / (1.0*avg_cnt)),
												(Kup_refl - Kup_refl_old) / (1.0*avg_cnt) / Math.max(1.e-9, (Kdir + Kdif)));

										Lup_refl_old = Lup_refl;
										Kup_refl_old = Kup_refl;

										// 314 continue
									}
									// !print *,'after 314'
									solar_refl_done = true;

								}

								alb_sfc = Math.min(albr * lpactual + albs * (1. - lpactual), Kup / (1.0*avg_cnt) / Math.max(1.e-9, (Kdir + Kdif)));
//System.out.println("alb_sfc " + alb_sfc);

								// remaining reflected radiation is partitioned by assuming that sfcs with
								// larger environmental view factors will absorb an amount of this radiation
								// proportional to their total view of other surfaces (approx.), and the
								// remainder will leave the system (to the sky)
								for (int iabCount = 0; iabCount < numsfc2; iabCount++)
								{
									int iIndex9 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
									tots[iabCount] = reflts[iabCount] + absbs[iabCount];
									totl[iabCount] = refltl[iabCount] + absbl[iabCount];
									reflts[iabCount] = reflts[iabCount] - sfc[iIndex9][Constants.sfc_evf] * refls[iabCount];
									absbs[iabCount] = absbs[iabCount] + sfc[iIndex9][Constants.sfc_evf] * refls[iabCount];
//if (iabCount==0) System.out.println("absbs[iabCount]4 " + absbs[iabCount]);
									refltl[iabCount] = refltl[iabCount] - sfc[iIndex9][Constants.sfc_evf] * refll[iabCount];
									absbl[iabCount] = absbl[iabCount] + sfc[iIndex9][Constants.sfc_evf] * refll[iabCount];
									Kup = Kup + (1. - sfc[iIndex9][Constants.sfc_evf]) * refls[iabCount];
									Lup = Lup + (1. - sfc[iIndex9][Constants.sfc_evf]) * refll[iabCount];
									
									
								}

								//
								// -------------------------------------------------------------
								// CONVECTION and Tsfc
//System.out.println("++++++++++++++++++++++++start convection=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60.);	
								rhoa = press * 100. / 287.04 / Ta;
								rhocan = press * 100. / 287.04 / Tcan;
								// this is the average heat capacity of air per m2 below zH
								// for 3-D geometries: (canyair is the height of the air column below
								// zH if all the buildings were put into one massive building of height
								// much lower than zH, of course - covering the entire area of interest)
								Cairavg = canyair * rhocan * TUFreg3D.cpair;

								// momentum transfer, log wind profile
								double Tzd = lambdapR * Tsfc_R / (1.0*numroof2) + (1. - lambdapR) * Tcan;
								Ri = util.SFC_RI(zref - zd, Ta, Tzd, Ua);
								HashMap<String, Double> cdReturn = VTUF3DUtil.CD(Ri, zref - zd, z0, z0 / moh);
								Fm = cdReturn.get("Fm");
								cdtown = cdReturn.get("cd_out");
								ustar = Math.sqrt(cdtown) * Ua;
								Qhcan_kin = Math.max(0., Qhcan / rhocan / TUFreg3D.cpair);
								wstar = Math.pow((9.806 / Tcan * Qhcan_kin * zH), (1. / 3.));

								// BISECTION METHOD FOR U PROFILE!!!
								double bp = ustar / TUFreg3D.vK / Math.sqrt(Fm);
								double bm = zH - zd;
								// The following is what Masson uses (but his model is an area average), so
								// I've replaced it with an equivalent 3-D expression
								double bn = -2. * lambdaf / (1. - lambdapR) / 4.;
								double bq = z0;

								if (ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) > Ua
										|| ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm)
												* Math.exp(-2. * lambdaf / (1. - lambdapR) / 4.) > ustar / TUFreg3D.vK
														* Math.log((zH - zd) / z0) / Math.sqrt(Fm))
								{
									System.out.println("Utop larger than Ua, or Ucan larger than Utop");
									// write(6,*)"Utop larger than Ua, or Ucan
									// larger than Utop";
									// stop;
									System.exit(1);
								}

								double CL = 0.01;
								double CR = CL + 0.1;
								double FR = bp * Math.exp(-CR * zH) / bm / CR - bp * Math.log(bm / bq) * (1. - Math.exp(bn))
										/ (Math.exp(CR * zH) - Math.exp(CR * zH / 2.));
								while (FR >= 1.e-20)
								{
									CR = CR + 0.1;
									FR = bp * Math.exp(-CR * zH) / bm / CR - bp * Math.log(bm / bq)
											* (1. - Math.exp(bn)) / (Math.exp(CR * zH) - Math.exp(CR * zH / 2.));
									// 957 continue
								}

								while (CR - CL > 0.001)
								{
									double Cmid = (CR + CL) / 2.;
									double Fmid = bp * Math.exp(-Cmid * zH) / bm / Cmid - bp * Math.log(bm / bq) * (1. - Math.exp(bn)) / (Math.exp(Cmid * zH) - Math.exp(Cmid * zH / 2.));
									if (Fmid > 0.)
									{
										CL = Cmid;
									}
									else if (Fmid < 0.)
									{
										CR = Cmid;
									}
									else if (Fmid == 0.)
									{
										Ccan = Cmid;
										// goto 959;
										break;
									}
									else
									{
										System.out.println("problem in bisection method");
										System.exit(1);
									}
									// 958 continue
								}
								Ccan = (CR + CL) / 2.;
								// 959 continue
						
								// constants for the canyon wind profile (Ccan also)
								Bcan = bp * Math.exp(-Ccan * zH) / bm / Ccan;
								Acan = -Bcan * Math.exp(Ccan * zH) + bp * Math.log(bm / bq);

								for (int iii = 0; iii < (int) Math.round(zH - 0.5); iii++)
								{
									zzz = 1.0*iii;
									Ucantst = Acan + Bcan * Math.exp(Ccan * zzz);
									if (Ucantst > Ua || Ucantst < 0.)
									{
										double Ucan = Double.NaN;
										System.out.println("bad Ucan at z=" + " " + zzz + " " + Ucan);
										System.exit(1);
									}
								}

								for (int iii = 0; iii < (int) Math.round(zref - 0.5); iii++)
								{
									zzz = 1.0*iii;
									if (zzz < zH)
									{
										Uwrite[iii] = Acan + Bcan * Math.exp(Ccan * zzz);
										Twrite[iii] = Tcan;
									}
									else
									{
										Uwrite[iii] = ustar / TUFreg3D.vK * Math.log((zzz - zd) / z0) / Math.sqrt(Fm);
										Twrite[iii] = Tcan - Tlog_fact / Uwrite[iii] * Math.pow((Math.log((zzz - zH + z0) / z0)), 2);
									}
								}

								double Ucanpy = Acan + Bcan * Math.exp(Ccan * zH / 2.);

								// Loop throught the patches in the central urban unit and calculate
								// net radiation, convection at each patch, and solve the energy balance
								Tp = 0.; Trad_R = 0.; Trad_T = 0.; Trad_N = 0.; Trad_S = 0.; Trad_E = 0.; Trad_W = 0.;
								httcT = 0.; httcW = 0.; httcR = 0.; Absbs_W = 0.; Absbl_W = 0.; Emit_W = 0.; Qg_T = 0.; Rnet_T = 0.; Qh_T = 0.;
								Qg_N = 0.; Rnet_N = 0.; Qh_N = 0.; Qg_S = 0.; Rnet_S = 0.; Qh_S = 0.;
								Qg_E = 0.; Rnet_E = 0.; Qh_E = 0.; Qg_W = 0.; Rnet_W = 0.; Qh_W = 0.; Qg_R = 0.;
								Rnet_R = 0.; Qh_R = 0.; Qg_tot = 0.; Rnet_tot = 0.; Qh_tot = 0.; Qhcantmp = 0.; Qh_abovezH = 0.; Qe_tot = 0.; 
//								Qecantmp = 0.;
								Qanthro = 0.;Qac = 0.;Qdeep = 0.;Tsfc_cplt = 0.;Tsfc_bird = 0.;Tsfc_R = 0.;Tsfc_N = 0.;Tsfc_S = 0.;Tsfc_E = 0.;Tsfc_W = 0.;
								Tsfc_T = 0.;TTsun = 0.;TTsh = 0.;TNsun = 0.;TNsh = 0.;TSsun = 0.;TSsh = 0.;TEsun = 0.;TEsh = 0.;TWsun = 0.;TWsh = 0.;
								numTsun = 0;numTsh = 0;numNsun = 0;numNsh = 0;numSsun = 0;numSsh = 0;numEsun = 0;numEsh = 0;numWsun = 0;numWsh = 0;Kdn_R = 0.;
								Kup_R = 0.;Ldn_R = 0.;Lup_R = 0.;Kdn_T = 0.;Kup_T = 0.;Ldn_T = 0.;Lup_T = 0.;Kdn_N = 0.;Kup_N = 0.;Ldn_N = 0.;Lup_N = 0.;
								Kdn_S = 0.;Kup_S = 0.;Ldn_S = 0.;Lup_S = 0.;Kdn_E = 0.;Kup_E = 0.;Ldn_E = 0.;Lup_E = 0.;Kdn_W = 0.;Kup_W = 0.;Ldn_W = 0.;Lup_W = 0.;

//								iij = 1;

								double Thorz=0;
								double zhorz=0;
								double Uhorz=0;
								for (int iabCount = 0; iabCount < numsfc2; iabCount++)
								{
									int iIndex10 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
									int y = (int) sfc_ab[iabCount][Constants.sfc_ab_y];
									int x = (int) sfc_ab[iabCount][Constants.sfc_ab_x];
								
									if (sfc[iIndex10][Constants.sfc_surface_type] > 2.5)
									{
										double Ueff;
										double Ucan;
										// ! WALLS - convection coefficients
										zwall = (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen;
										if (zwall >= zH)
										{
											Ucan = ustar / TUFreg3D.vK * Math.log((zwall - zd) / z0) / Math.sqrt(Fm);
											Ueff = Math.sqrt(Math.pow(Ucan, 2) + Math.pow(wstar, 2));
											// ! for Tconv in Newton's method for Tsfc below:
											Thorz = Tcan - Tlog_fact / Ucan * Math.pow((Math.log((zwall - zH + z0) / z0)), 2);
										}
										else
										{
											Ucan = Acan + Bcan * Math.exp(Ccan * zwall);
											Ueff = Math.sqrt(Math.pow(Ucan, 2) + Math.pow(wstar, 2));
										}
										httc = rw * (11.8 + 4.2 * Ueff) - 4.;
									}
									else
									{										
										// ! STREETS & ROOFS - convection coefficients
										// ! use the windspeed 0.5*patchlen above the surface (changed to Harman:
										// ! 0.1*average roof length)

										// ! streets:
										zhorz = 0.1 * zH;

										// ! roofs:
										if (sfc[iIndex10][Constants.sfc_surface_type] < 1.5)
										{
											zhorz = Math.min(zref, (sfc[iIndex10][Constants.sfc_z] - 0.5 + 0.1 * Lroof) * patchlen);											
											if (zrooffrc > 0.)
											{
												zhorz = Math.min(zref, (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen + zrooffrc);
											}
										}

										// ! assume wstar is not relevant for roofs above zH
										if (zhorz > zH)
										{
											Uhorz = ustar / TUFreg3D.vK * Math.log((zhorz - zd) / z0) / Math.sqrt(Fm);
											Thorz = Tcan - Tlog_fact / Uhorz * Math.pow((Math.log((zhorz - zH + z0) / z0)), 2);
											rhohorz = press * 100. / 287.04 / Thorz;

											if (Math.max(Math.abs(Thorz - Tcan), Math.abs(Thorz - Ta)) > Math.abs(Tcan - Ta) + 0.01)
											{
												System.out.println("Thorz outside of Ta,Tcan range, Thorz,i=" + " " + Thorz + " " + iIndex10);
												System.exit(1);
											}
										}
										else
										{
											// ! effective canyon wind is only for HTC calc, not Ri calc too!
											Uhorz = Acan + Bcan * Math.exp(Ccan * zhorz);
											Thorz = Tcan;
											rhohorz = rhocan;
										}
									if (sfc[iIndex10][Constants.sfc_surface_type] < 1.5)
									{
//										double aaaa;
										// ! roofs:
										// ! Harman et al. 2004 approach: 0.1*average roof length
										Ri = util.SFC_RI(zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen, Thorz, Tsfc[iabCount], Uhorz);
										if ((sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
										{
											HashMap<String, Double> htcReturn2 = util.HTC(Ri,
													Math.sqrt(Math.pow(Uhorz, 2) + Math.pow(wstar, 2)),
													zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen, z0roofm, z0roofh);
											httc = htcReturn2.get(VTUF3DUtil.HTTC_OUT_INDEX);
											Fh = htcReturn2.get(VTUF3DUtil.FH_INDEX);
//											aaaa = 1.;
										}
										else
										{
											HashMap<String, Double> htcReturn3 = 
													util.HTC(Ri, Uhorz, zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen, 
															z0roofm, z0roofh);
											httc = htcReturn3.get(VTUF3DUtil.HTTC_OUT_INDEX);
											Fh = htcReturn3.get(VTUF3DUtil.FH_INDEX);
//											aaaa = 2.;
										}
									}
									else
									{
										// streets: Harman et al. 2004 approach:  0.1*average building height
										Ri = util.SFC_RI(0.1 * zH, Thorz, Tsfc[iabCount], Uhorz);
										HashMap<String, Double> htcReturn4 = util.HTC(Ri,
												Math.sqrt(Math.pow(Uhorz, 2) + Math.pow(wstar, 2)), 0.1 * zH, z0roadm,
												z0roadh);
										httc = htcReturn4.get(VTUF3DUtil.HTTC_OUT_INDEX);
										Fh = htcReturn4.get(VTUF3DUtil.FH_INDEX);
									}
									httc = httc * TUFreg3D.cpair * rhohorz;
								}

								// This is actually Kdown-Kup+eps*Ldown (the Lup term is calculated in the iteration below)
								Rnet = absbl[iabCount] + absbs[iabCount];
//if (iabCount ==0) System.out.println("Rnet1 " + Rnet + " " + absbl[iabCount] +" " + absbs[iabCount]);

								Tconv = Tcan;
								//adding to initialize i (replaced all the i with iIndex

								if ((sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen + 0.001 >= zH)
								{
									Tconv = Thorz;
								}

								if (Math.abs(Tsfc[iabCount] - Tconv) > 60.)
								{
//									System.out.println("iab,Tsfc[iab],Tconv" + " " + iabCount + " " + Tsfc[iabCount] + " " + Tconv);
									// write(6,*)"iab,Tsfc[iab],Tconv",iab,Tsfc[iab],Tconv;
									// stop
//									System.exit(1);
								}
								if (Rnet>3000.0 || Rnet<-500.0) 
								{
									System.out.println("Rnet is too big, Rnet = " + Rnet);
//									System.out.println("Problem is at patch x,y,z,f =" + sfc[iIndex10][Constants.sfc_evf]
//											+ " " + sfc[iIndex10][Constants.sfc_emiss]
//											+ " " + sfc[iIndex10][Constants.sfc_albedo]
//											+ " " + sfc[iIndex10][Constants.sfc_sunlight_fact]);
									
//									System.out.println(iabCount + " " + sfc[iIndex10][Constants.sfc_albedo] + " " + Kbeam + " " + " " + sfc[iIndex10][Constants.sfc_sunlight_fact] );									

//									absbs[iabCount] = absbs[iabCount] + (1. - sfc[iIndex6][Constants.sfc_albedo])
//											* Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;
									
									
//									System.out.println(absbl[iabCount] +" "+ absbs[iabCount]);
//									System.exit(1);
								}
								// KN, changing this to let Rnet be a little
								// bigger
								// ! write(6,*)'Rnet is too big, Rnet = ',Rnet
								// ! write(6,*)'Problem is at patch x,y,z,f =
								// ',sfc[i][Constants.sfc_evf],sfc[i][Constants.sfc_emiss],sfc[i][Constants.sfc_albedo],sfc[i][Constants.sfc_sunlight_fact]
								// !endif
								// !if (Rnet>2000.0.or.Rnet<-1000.0) then
								// ! write(6,*)'Rnet is too big, Rnet = ',Rnet
								// ! write(6,*)'Problem is at patch x,y,z,f =
								// ',sfc[i][Constants.sfc_evf],sfc[i][Constants.sfc_emiss],sfc[i][Constants.sfc_albedo],sfc[i][Constants.sfc_sunlight_fact]
								// !endif

								// ! stop

								Tnew = Tsfc[iabCount];

								Told = Tnew + 999.;
//System.out.println("++++++++++++++++++++++++start Tsfc newton=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60. );	
								// ITERATION to solve individual patch Tsfc[i] by Newton's method----
								int patchItrCount = 0;
								int httcRetries = 0;
								while (Math.abs(Tnew - Told) > 0.001)
								{
//									if (iabCount == 399 || iabCount == 398)
//									{
//										System.out.println(	 "|  " + iabCount + " " + 	
//											Told+ " "+
//											httc + " "+
//											Rnet + " "+
//											Tconv+ " "+
//											sfc_ab[iabCount][Constants.sfc_ab_layer_temp] 									
//										);
//										System.out.println("||| " + sfc[iIndex10][Constants.sfc_emiss] + " " +
//												sigma + " " + 
//												lambda_sfc[iabCount] + " " + 
//												sfc_ab[iabCount][sixPlusThreeTimesNumlayers] + " " + 
//												Tnew
//														);
//									}
									Told = Tnew;
									Fold = sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Told, 4)
											+ (httc + lambda_sfc[iabCount] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers]) * Told - Rnet - httc * Tconv
											- lambda_sfc[iabCount] * sfc_ab[iabCount][Constants.sfc_ab_layer_temp] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
									Fold_prime = 4. * sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Told, 3) + httc
											+ lambda_sfc[iabCount] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
									Tnew = -Fold / Fold_prime + Told;
									if (Double.isNaN(Tnew))
									{
										System.out.println();
									}
//									System.out.println(patchItrCount + " " + Tnew + " " + Told + " " + Fold + " " + Fold_prime);
									//  fails with 0 200.50485379623788 291.15 255.04996909525653 2.813718988570301
									patchItrCount++;
									if (patchItrCount > 40)
									{
										System.out.println("too many iterations in Tsfc");
										System.out.println("modifying httc " + httc );
										if (httc < 0)
										{
											httc = httc + 0.5;
										}
										else
										{
											httc = httc - 0.5;
										}
										patchItrCount = 0;
										if (httcRetries > 10)
										{
											System.out.println("Too many httc retries");
											
											System.out.println(	 "|  " + iabCount + " " + 	
											Told+ " "+
											httc + " "+
											Rnet + " "+
											Tconv+ " "+
											sfc_ab[iabCount][Constants.sfc_ab_layer_temp] 									
										);
										System.out.println("||| " + sfc[iIndex10][Constants.sfc_emiss] + " " +
												TUFreg3D.sigma + " " + 
												lambda_sfc[iabCount] + " " + 
												sfc_ab[iabCount][sixPlusThreeTimesNumlayers] + " " + 
												Tnew
														);
										System.out.println(patchItrCount + " " + Tnew + " " + Told + " " + Fold + " " + Fold_prime);
											
											System.exit(1);
										}
										httcRetries ++;
									}
									
									// 899 continue
								}
								if (Math.abs(Tnew - Tsfc[iabCount]) > Tdiffmax)
								{
									Tdiffmax = Math.abs(Tnew - Tsfc[iabCount]);
								}
								if (Double.isNaN(Tnew))
								{
									System.out.println();
								}
								Tsfc[iabCount] = Tnew;

								Trad[iabCount] = Math.pow(((1. / TUFreg3D.sigma)
										* (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4) + refltl[iabCount])),
										(0.25));

								// ! STORE OUTPUT: (only the chosen subdomain)
								if (sfc[iIndex10][Constants.sfc_in_array] > 1.5)
								{
									// overall energy balance (per unit plan area):
									// !print *,sfc_ab_map_x[iab],sfc_ab_map_y[iab],sfc_ab_map_z[iab],sfc_ab_map_f[iab],timeis,yd_actual
									leFromEt5 = 0;

									diffShadingValueUsed = Constants.DIFFERENTIALSHADING100PERCENT;
									if (Ktot > 1.0E-3)
									{									
										diffShadingCalculatedValue = treeXYMapSunlightPercentageTotal[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1];
										if (diffShadingCalculatedValue >= .50)
										{
											diffShadingValueUsed = Constants.DIFFERENTIALSHADING100PERCENT;
											outputDebugStr = "100%";
										}
										if (diffShadingCalculatedValue < .50)
										{
											diffShadingValueUsed = DIFFERENTIALSHADINGDIFFUSE;
											outputDebugStr = "0%";
										}
									}

									if (treeMapFromConfig.usingDiffShading == 0)
									{
										diffShadingValueUsed = Constants.DIFFERENTIALSHADING100PERCENT;
										// stop;
										System.exit(1);
									}

									if (treeXYMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] != 0)
									{
										// ! print
										// *,'----------------------------------------'
										tempTimeis = (int) (timeis * 2);
										if (tempTimeis < 1)
										{
											tempTimeis = 1;
										}

										//TODO figure out how to replace this with online Maespa
										String key = treeXYMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] + "_" + diffShadingValueUsed;
										Tsfc[iabCount] = maespaDataArray.get(key).get(tempTimeis-1).getTCAN() + 273.15;
										if (Double.isNaN(Tsfc[iabCount]))
										{
											System.out.println();
										}
										// only use LE from the trunk grid square
										if (treeXYTreeMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] > 0) 
										{
											//TODO figure out how to replace this with online Maespa
											leFromEt5 = maespaDataArray.get(key).get(tempTimeis-1).getQeCalc5() ;
											System.out.println("le=" + leFromEt5 + " " + simpelQe);
										}
									}
									
					               

									if (treeXYTreeMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] > 0)
									{
//										 // run Simpel for the timestep
//						        		HashMap<Integer,Double> simpelMetInput = new HashMap<Integer,Double>();
////						        		String[] InputStr = new String[] {"20.1.2021.0","20","0","63.7493333333333","13.49","0.255833333333333","0","0"};
//						        		simpelMetInput.put(SimpelConstants.INPUT_P, 0.); //TODO, no precipitation in forcing data yet
//						        		simpelMetInput.put(SimpelConstants.INPUT_T14, Ta);
//						        								        		
//						        		double calcRH = common.CalculateRHFromVapor(Ta, ea);
//						        		
//						        		simpelMetInput.put(SimpelConstants.INPUT_R14, calcRH);		
//						        		simpelMetInput.put(SimpelConstants.INPUT_K_DOWN, Ktotfrc);
//						        								        	   
//						        		long month = Math.round(yd_actual/30.);//TODO set the actual month
//						        		simpelMetInput.put(SimpelConstants.INPUT_DOY, yd_actual*1.0);
//						        		simpelMetInput.put(SimpelConstants.INPUT_MONTH, month*1.0);
//						        		simpelMetInput.put(SimpelConstants.INPUT_HOUR, TM*1.0);
//						        		
////						        		System.out.println("inputhour "+common.roundTwoDecimals(TM));
//						        		if (simpelMetInput.get(SimpelConstants.INPUT_HOUR) == 13) //TODO, set from property file, what time, how much irrigation
//						        		{
//						        			simpelMetInput.put(SimpelConstants.INPUT_IRR, 4.0);
//						        		}
//						        								        		
//						        		//ok if this is null for the first time, will be filled in the timestep function
//						        		TreeMap<Integer,Double> simpelPreviousTimestepValues = allSimpelPreviousTimesteps.get(iabCount);
//						        		
//						        		ETo eto = new ETo();						        		
////						        		The latitude of the met station (dec deg) 
//						        		double lat=-37.5;
////						        		The longitude of the met station (dec deg) (only needed if calculating ETo hourly)
//						        		double lon=145;
////						        		The longitude of the center of the time zone (dec deg) (only needed if calculating ETo hourly).
//						        		double TZ_lon=145;
////						        		Elevation of the met station above mean sea level (m) 
//						        		double z_msl=500;
////						        		The height of the wind speed measurement (m). Default is 2 m.
//						        		double z_u=2;
////						        		Wind speed at height z (m/s), set to NaN to calculate
//						        		double U_z=Double.NaN;
////						        		Albedo. Should be 0.23 for the reference crop.
//						        		double alb = 0.23;
////						        		Day of Year
//						        		int Day = yd_actual;		
////						        		Time frequency string of the input and output. The minimum frequency is hours (H) and the maximum is month (M).
//						        		int freq=ETo.HOURLY;
////						        		Time of day
//						        		int hour = (int) Math.round(TM);		
////						        		Incoming shortwave radiation (MJ/m2)
//						        		double R_s_hourly = Ktotfrc * 60. * 60. * 1E-6;  
////						        		Actual Vapour pressure derrived from RH
//						        		double e_a_hourly = ea;  
////						        		Mean Temperature (deg C)
//						        		double T_mean_hourly = Ta;
//						        		// if no incoming shortwave, then nighttime
//						        		boolean daytime = true;
//						        		if (Ktotfrc < 50)
//						        		{
//						        			daytime = false;
//						        		}
//						        		
//						        		double etoValue = eto.eto_fao_hourly(freq, lat, Day, lon, TZ_lon, z_msl, e_a_hourly, R_s_hourly, T_mean_hourly, z_u, U_z, alb, hour, daytime);
//
//						        		double[][] simpelReturnValues = simpel.SIMPLE_function(simpelMetInput, SimpelConstants.Landuse, SimpelConstants.LAI_model, 
//						        														SimpelConstants.Soil, simpelPreviousTimestepValues, etoValue);    		
//						        		simpelPreviousTimestepValues = simpel.setPreviousValues(simpelReturnValues);
//						        		allSimpelPreviousTimesteps.put(iabCount,simpelPreviousTimestepValues);
//						        		
//						        		double simpelETA = simpelReturnValues[0][SimpelConstants.ETA_TOTAL];
//						        		double simpelQe = simpel.qeFromETA2(simpelETA);
//						        		System.out.println("eto "+ common.roundTwoDecimals(etoValue) + " " +  common.roundTwoDecimals(simpelETA )+ " " + common.roundTwoDecimals(simpelQe)) ;
////						        		System.out.println("eto "+ common.roundTwoDecimals(etoValue) + " " +  common.roundTwoDecimals(simpelETA )+ " " + (simpelQe)) ;
//						        		
////						        		simpelQe=0;	    						        		
//						        		// end Simpel	
										
						        		if ((sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
										{
//						        			System.out.println("canyon");
//						        			System.out.println("simpel="+common.roundTwoDecimals(simpelQe) + " " + "maespa=" + common.roundTwoDecimals(leFromEt5));
										}
										
										
																		
										// this rnet value would have been calculated using the vegetation alb/emis
										currentRnet[iabCount] = Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);

										currentQh[iabCount] = (httc * (Tsfc[iabCount] - Tconv))- leFromEt5;
										currentQe[iabCount] = leFromEt5;
										currentQg[iabCount] = (Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4))
												- (httc * (Tsfc[iabCount] - Tconv)) ;

										Rnet_tot = Rnet_tot + currentRnet[iabCount];
										Qh_tot = Qh_tot + currentQh[iabCount];
										Qe_tot = Qe_tot + currentQe[iabCount];
										// !! calculate Qg as a residual from
										// rnet
										Qg_tot = Qg_tot + currentQg[iabCount];
									}
				
									if (treeXYTreeMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] > 0)
									{
//										continue;
										// this isn't a Maespa surface then, so use the normal TUF method
									}
									else
									{											
										currentRnet[iabCount] = Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										currentQe[iabCount] = leFromEt5;

										currentQh[iabCount] = (httc * (Tsfc[iabCount] - Tconv));										
										currentQg[iabCount] = (lambda_sfc[iabCount] * (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers]);
										Rnet_tot = Rnet_tot + currentRnet[iabCount];
										Qh_tot = Qh_tot + currentQh[iabCount];
										Qe_tot = Qe_tot + currentQe[iabCount];
										Qg_tot = Qg_tot + currentQg[iabCount];
									}
									// ! canyon only:
									if ((sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
									{
										Qhcantmp = Qhcantmp + httc * (Tsfc[iabCount] - Tconv);
									}
									else
									{
										Qh_abovezH = Qh_abovezH + httc * (Tsfc[iabCount] - Tconv);
									}

									// for evolution of internal building temperature:
									if (sfc[iIndex10][Constants.sfc_surface_type] > 2.5)
									{
										// wall internal T
										Tp = Tp + sfc_ab[iabCount][fivePlusNumlayers];
									}
									else if (sfc[iIndex10][Constants.sfc_surface_type] < 1.5)
									{
										// ! roof internal T; also add internal of floor (user-defined)
										Tp = Tp + sfc_ab[iabCount][fivePlusNumlayers] + Tfloor;
									}

									// ! Surface temperatures and energy balance components.
									//  Averaging patch values to get facet-average values
									//  complete (per unit total area)
									Tsfc_cplt = Tsfc_cplt + Tsfc[iabCount];
									//  bird's eye view sfc T
									if (sfc[iIndex10][Constants.sfc_surface_type] < 2.5)
									{
										Tsfc_bird = Tsfc_bird + Tsfc[iabCount];
									}
									
									//  roof sfc T and energy balance
									if (sfc[iIndex10][Constants.sfc_surface_type] < 1.5)
									{
										double[] roofEnergyBalanceReturn = energyBalances.energyBalance(httc, iabCount, iIndex10, sixPlusThreeTimesNumlayers, 
												fivePlusNumlayers, numlayersMinus1,
												Tsfc, sfc, refltl, tots, reflts, lambda_sfc, lambdaavr, thickr, sfc_ab,  totl, Rnet, Tconv, Tintw,
												httcR, Tsfc_R, Trad_R,  Rnet_R, Kdn_R, Kup_R, Ldn_R, Lup_R, Qh_R, Qg_R, Qanthro, Qac);
//										httcR = httcR + httc;
//										Tsfc_R = Tsfc_R + Tsfc[iabCount];
//										Trad_R = Trad_R + Math.pow(((1. / sigma)
//												* (sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4)
//														+ refltl[iabCount])),
//												(0.25));
//										Rnet_R = Rnet_R + Rnet
//												- sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4);
//										Kdn_R = Kdn_R + tots[iabCount];
//										Kup_R = Kup_R + reflts[iabCount];
//										Ldn_R = Ldn_R + totl[iabCount];
//										Lup_R = Lup_R + refltl[iabCount]
//												+ sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4);
//
//										Qh_R = Qh_R + httc * (Tsfc[iabCount] - Tconv);
//
//										Qg_R = Qg_R + lambda_sfc[iabCount]
//												* (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
//												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
//										Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers])
//												* lambdaavr[numlayersMinus1] * 2. / thickr[numlayersMinus1]);
//										Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw)
//												* lambdaavr[numlayersMinus1] * 2. / thickr[numlayersMinus1]);
										
										httcR = roofEnergyBalanceReturn[0];
										Tsfc_R = roofEnergyBalanceReturn[1];
										Trad_R = roofEnergyBalanceReturn[2];
										Rnet_R = roofEnergyBalanceReturn[3];
										Kdn_R = roofEnergyBalanceReturn[4];
										Kup_R = roofEnergyBalanceReturn[5];
										Ldn_R = roofEnergyBalanceReturn[6];
										Lup_R = roofEnergyBalanceReturn[7];
										Qh_R = roofEnergyBalanceReturn[8];
										Qg_R = roofEnergyBalanceReturn[9];
										Qanthro = roofEnergyBalanceReturn[10];
										Qac = roofEnergyBalanceReturn[11];
									}

									//  street energy balance (sfc T calc below)
									if (sfc[iIndex10][Constants.sfc_surface_type] > 1.5
											&& sfc[iIndex10][Constants.sfc_surface_type] < 2.5)
									{

										httcT = httcT + httc;
										Trad_T = Trad_T + Math.pow(((1. / TUFreg3D.sigma)
												* (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4)
														+ refltl[iabCount])),
												(0.25));
								
										Rnet_T = Rnet_T + Rnet
												- sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Kdn_T = Kdn_T + tots[iabCount];
										Kup_T = Kup_T + reflts[iabCount];
										Ldn_T = Ldn_T + totl[iabCount];
										Lup_T = Lup_T + refltl[iabCount]
												+ sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Qh_T = Qh_T + httc * (Tsfc[iabCount] - Tconv);

										Qg_T = Qg_T + lambda_sfc[iabCount]
												* (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
										Qdeep = Qdeep + (sfc_ab[iabCount][fivePlusNumlayers] - Tints) * lambdaavs[numlayersMinus1] * 2.
												/ thicks[numlayersMinus1];
										
										if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
										{
											TTsun = TTsun + Tsfc[iabCount];
											numTsun = numTsun + 1;
										}
										else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
										{
											TTsh = TTsh + Tsfc[iabCount];
											numTsh = numTsh + 1;
										}
									}
									if (sfc[iIndex10][Constants.sfc_surface_type] > 2.5)
									{
										httcW = httcW + httc;
									}
									//  N wall sfc T and energy balance
									if (sfc[iIndex10][Constants.sfc_y_vector] > 0.5)
									{

										Tsfc_N = Tsfc_N + Tsfc[iabCount];
										Trad_N = Trad_N + Math.pow(((1. / TUFreg3D.sigma)
												* (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4)
														+ refltl[iabCount])),
												(0.25));
										Rnet_N = Rnet_N + Rnet
												- sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Kdn_N = Kdn_N + tots[iabCount];
										Kup_N = Kup_N + reflts[iabCount];
										Ldn_N = Ldn_N + totl[iabCount];
										Lup_N = Lup_N + refltl[iabCount]
												+ sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Qh_N = Qh_N + httc * (Tsfc[iabCount] - Tconv);
										Qg_N = Qg_N + lambda_sfc[iabCount]
												* (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
										Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers])
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw)
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
										{
											TNsun = TNsun + Tsfc[iabCount];
											numNsun = numNsun + 1;
										}
										else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
										{
											TNsh = TNsh + Tsfc[iabCount];
											numNsh = numNsh + 1;
										}
									}
									//  S wall sfc T and energy balance
									if (sfc[iIndex10][Constants.sfc_y_vector] < -0.5)
									{

										Tsfc_S = Tsfc_S + Tsfc[iabCount];
										Trad_S = Trad_S + Math.pow(((1. / TUFreg3D.sigma)
												* (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4)
														+ refltl[iabCount])),
												(0.25));
										Rnet_S = Rnet_S + Rnet
												- sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Kdn_S = Kdn_S + tots[iabCount];
										Kup_S = Kup_S + reflts[iabCount];
										Ldn_S = Ldn_S + totl[iabCount];
										Lup_S = Lup_S + refltl[iabCount]
												+ sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Qh_S = Qh_S + httc * (Tsfc[iabCount] - Tconv);
										Qg_S = Qg_S + lambda_sfc[iabCount]
												* (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
										Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers])
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw)
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
										{
											TSsun = TSsun + Tsfc[iabCount];
											numSsun = numSsun + 1;
										}
										else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
										{
											TSsh = TSsh + Tsfc[iabCount];
											numSsh = numSsh + 1;
										}
									}
									//  E wall sfc T and energy balance
									if (sfc[iIndex10][Constants.sfc_x_vector] > 0.5)
									{

										Tsfc_E = Tsfc_E + Tsfc[iabCount];
										Trad_E = Trad_E + Math.pow(((1. / TUFreg3D.sigma)
												* (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4)
														+ refltl[iabCount])),
												(0.25));
										Rnet_E = Rnet_E + Rnet
												- sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Kdn_E = Kdn_E + tots[iabCount];
										Kup_E = Kup_E + reflts[iabCount];
										Ldn_E = Ldn_E + totl[iabCount];
										Lup_E = Lup_E + refltl[iabCount]
												+ sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Qh_E = Qh_E + httc * (Tsfc[iabCount] - Tconv);
										Qg_E = Qg_E + lambda_sfc[iabCount]
												* (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];

										Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers])
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw)
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
										{
											TEsun = TEsun + Tsfc[iabCount];
											numEsun = numEsun + 1;
										}
										else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
										{
											TEsh = TEsh + Tsfc[iabCount];
											numEsh = numEsh + 1;
										}
									}
									//  W wall sfc T and energy balance
									if (sfc[iIndex10][Constants.sfc_x_vector] < -0.5)
									{

										Tsfc_W = Tsfc_W + Tsfc[iabCount];

										Trad_W = Trad_W + Math.pow(((1. / TUFreg3D.sigma) * (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4) 
												+ refltl[iabCount])), (0.25));
										Absbs_W = Absbs_W + absbs[iabCount];
										Absbl_W = Absbl_W + absbl[iabCount];
										Emit_W = Emit_W + sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Rnet_W = Rnet_W + Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Kdn_W = Kdn_W + tots[iabCount];
										Kup_W = Kup_W + reflts[iabCount];
										Ldn_W = Ldn_W + totl[iabCount];
										Lup_W = Lup_W + refltl[iabCount] + sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
										Qh_W = Qh_W + httc * (Tsfc[iabCount] - Tconv);

										Qg_W = Qg_W + lambda_sfc[iabCount] * (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2.
												/ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
										Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers])
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw)
												* lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
										if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
										{
											TWsun = TWsun + Tsfc[iabCount];
											numWsun = numWsun + 1;
										}
										else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
										{
											TWsh = TWsh + Tsfc[iabCount];
											numWsh = numWsh + 1;
										}	
									}
								}
								}

								//  END OF ITERATIVE TSFC LOOP

								//  BUT, UNLESS EQUILIBRIUM ACHIEVED IN TERMS OF LONGWAVE EXCHANGE AND TSFC, GO BACK AND DO IT AGAIN (as in Arnfield)
								tsfcLupBalanceContinue = false;
								if (Tdiffmax > Tthreshold)
								{
									// ! adding this to do some extra loops but
									// not to limit the number so it isn't an
									// endless loop
									if (tthresholdLoops < numberOfExtraTthresholdLoops)
									{
										tthresholdLoops = tthresholdLoops + 1;
										// print *,'goto 898
										// Tdiffmax,Tthreshold,tthresholdLoops',Tdiffmax,Tthreshold,tthresholdLoops
										// goto 898;
										tsfcLupBalanceContinue = true;
									}
									else
									{
										tthresholdLoops = 0;
									}
								}
							} // goto 898 replacement

							Kup = Kup / (1.0*avg_cnt);
							Lup = Lup / (1.0*avg_cnt);

							solar_refl_done = false;

							//  update internal building air temperature:
							// (Masson et al. 2002)
							//  86400 is the number of seconds in a day
							Tintw = Tintw * (86400. - deltat) / 86400.
									+ Tp / (numwall2 + 2. * numroof2) * deltat / 86400.;
							//  put minimum on internal building temperature
							Tintw = Math.max(Tintw, 273.15 + Tbuild_min);

							Qhcan = Qhcantmp / (1.0*numroof2 + numstreet2) / (1. - lambdapR);

							//  canyon-atm exchange:
							Ri = util.SFC_RI(zref - zH + z0, Ta, Tcan, Ua);
							HashMap<String, Double> htcReturn8 = util.HTC(Ri, Ua, zref - zH + z0, z0, z0);
							httc_top = htcReturn8.get("httc_out");
							Fh = htcReturn8.get("Fh");
							Qhtop = TUFreg3D.cpair * rhoa * httc_top * (Tcan - Ta);

							//  Checking for oscillations: (0.05 is, from experience, a number that
							//  cuts off oscillations early enough without reacting to normal changes
							//  in canyon temperature)
							//  Here I'm assuming that the canyon temperature cannot be unstable at
							//  timesteps of 1-2 seconds or less...if this is removed, the simulation
							//  sometimes reaches a timestep of 0 simply because dTcan_old is so big
							//  relative to the other term - this is particularly a problem right after
							//  the forcing causes the canyon temperature to reverse trend
							if (Math.abs(deltat * (Qhcan - Qhtop) / Cairavg - dTcan_old) > 0.05 && deltat > 2.)
							{

								timeis = timeis - deltat / 3600.;
								deltat = deltat * 5. / 8.;
								counter = 10;
								System.out.println("Oscill. Tcan, starting over with 5/8*deltat=" + " " + deltat);
								dTcan_old = 5. / 8. * dTcan_old;
								// goto 937;
								timeis = timeis + deltat / 3600.;
								ywrite = true;
								continue;
							}

							counter = counter + 1;

							//  NEW Tcan:
							Tcan = Tcan + deltat / Cairavg * (Qhcan - Qhtop);  

							dTcan_old = deltat / Cairavg * (Qhcan - Qhtop);  
							//  WRITE OUTPUT
							if (frcwrite)
							{
								overall.writeOutput(Constants.forcing_dat,
										lpactual + " " + (2. * bh) / (1.0*bl + bw) + " " + hwactual + " " + stror + " "
												+ timeis + " " + Kdir + " " + Kdif + " " + Ldn + " " + Ta + " " + ea
												+ " " + Ua + " " + Udir + " " + press + " " + az + " " + zen);
							}

							//  street sfc T
							Tsfc_T = Tsfc_bird - Tsfc_R;

							//  to output averages (every outpt_tm time  interval)
							counter2 = counter2 + 1;
							Kuptot_avg = Kuptot_avg + Kup;
							Luptot_avg = Luptot_avg + Lup;
							Rntot_avg = Rntot_avg + Rnet_tot / Aplan;
							Qhtot_avg = Qhtot_avg + Qh_tot / Aplan;
							Qetot_avg = Qetot_avg + Qe_tot / Aplan; 
							Qgtot_avg = Qgtot_avg + Qg_tot / Aplan; 
							Qanthro_avg = Qanthro_avg + Qanthro / Aplan; 
							Qac_avg = Qac_avg + Qac / Aplan;
							Qdeep_avg = Qdeep_avg + Qdeep / Aplan;
							Qtau_avg = Qtau_avg + rhoa * ustar * ustar;
							TR_avg = TR_avg + Tsfc_R / (1.0*numroof2) - 273.15;
							TT_avg = TT_avg + Tsfc_T / (1.0*numstreet2) - 273.15;
							TN_avg = TN_avg + Tsfc_N / (1.0*numNwall2) - 273.15;
							TS_avg = TS_avg + Tsfc_S / (1.0*numSwall2) - 273.15;
							TE_avg = TE_avg + Tsfc_E / (1.0*numEwall2) - 273.15;
							TW_avg = TW_avg + Tsfc_W / (1.0*numWwall2) - 273.15;

							//  Conduction Loop
							for (int iabCount = 1 - 1; iabCount < numsfc2; iabCount++)
							{

								//  CONDUCTION - combination of Arnfield (198X), Masson (2000), Jacobson (1999)
								//  thermal conductivities (in W/K/m2) are added in series instead of
								//  plain averaging, Tsfc calculated iteratively above acts as the surface
								//  boundary condition

								//  roofs and walls
								double Tint = Tintw;
								//  streets
								//add to initialize i (after changing to iIndex)
								int i = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
								if (Math.abs(sfc[i][Constants.sfc_surface_type] - 2.) < 0.5)
								{
									Tint = Tints;
								}

								//  first calculate the thermal conductivities between layer centers by adding
								//  thermal conductivities (or resistivities) in series
								for (int k = 0; k < numlayers; k++)
								{
									tlayer[k] = sfc_ab[iabCount][k + 5];
									tlayerp[k] = tlayer[k];
									lambdaav[k] = sfc_ab[iabCount][k + numlayers + 5];
									htcap[k] = sfc_ab[iabCount][k + 2 * numlayers + 5];
									thick[k] = sfc_ab[iabCount][k + 3 * numlayers + 5];

								}

								//  surface matrix values:
								double lambd_o_thick = lambdaav[TUFreg3D.ONE] / (thick[TUFreg3D.ONE] + thick[TUFreg3D.TWO]);
								A[TUFreg3D.ONE] = 0.;
								B[TUFreg3D.ONE] = thick[TUFreg3D.ONE] * htcap[TUFreg3D.ONE] / deltat + 2. * uc * (lambd_o_thick);
								D[TUFreg3D.ONE] = -2. * uc * lambd_o_thick;
								R[TUFreg3D.ONE] = -2. * (1. - uc) * lambd_o_thick * (tlayerp[TUFreg3D.ONE] - tlayerp[TUFreg3D.TWO])
										+ tlayerp[TUFreg3D.ONE] * thick[TUFreg3D.ONE] * htcap[TUFreg3D.ONE] / deltat
										+ (Tsfc[iabCount] - tlayerp[TUFreg3D.ONE]) * lambda_sfc[iabCount] / thick[TUFreg3D.ONE] * 2.;

								//  what I have done above is make the surface boundary condition
								//  "QGsfc" completely explicit, as written below, even though the
								//  conduction can have any level of implicitness, it must conform
								//  to this explicit boundary condition - prior, I had this BC in
								//  the uc and 1-uc brackets to make the BC dependent on the implicitness
								//  but then since the Tsfc solution assumes explicit conduction at
								//  the sfc (i.e. BC using tlayerp(1)), this  would mean a loss or gain
								//  of energy, since the condution solution would assume a different
								//  amount of energy being conducted than the Tsfc solution

								//  interior matrix values:
								for (int k = 1; k < numlayersMinus1; k++)
								{
									lambd_o_thick = lambdaav[k - 1] / (thick[k - 1] + thick[k]);
									double lambd_o_thick2 = lambdaav[k] / (thick[k] + thick[k + 1]);
									A[k] = -2. * uc * lambd_o_thick;
									B[k] = thick[k] * htcap[k] / deltat + 2. * uc * (lambd_o_thick + lambd_o_thick2);
									D[k] = -2. * uc * lambd_o_thick2;
									R[k] = -2. * (1. - uc) * (lambd_o_thick * (tlayerp[k] - tlayerp[k - 1])
											+ lambd_o_thick2 * (tlayerp[k] - tlayerp[k + 1]))
											+ tlayerp[k] * thick[k] * htcap[k] / deltat;
								}

								//  values for conduction (+ convection + radiation - Masson et al 2002)
								//  between innermost layer and inner air
								lambd_o_thick = lambdaav[numlayersMinus2] / (thick[numlayersMinus2] + thick[numlayersMinus1]);
								A[numlayersMinus1] = -2. * uc * lambd_o_thick;
								B[numlayersMinus1] = thick[numlayersMinus1] * htcap[numlayersMinus1] / deltat
										+ 2. * uc * (lambd_o_thick + lambdaav[numlayersMinus1] / thick[numlayersMinus1] * IntCond);
								D[numlayersMinus1] = 0.;
								R[numlayersMinus1] = -2. * (1. - uc)
										* (lambd_o_thick * (tlayerp[numlayersMinus1] - tlayerp[numlayersMinus2])
												+ lambdaav[numlayersMinus1] * tlayerp[numlayersMinus1] / thick[numlayersMinus1] * IntCond)
										+ 2. * lambdaav[numlayersMinus1] * Tint / thick[numlayersMinus1] * IntCond
										+ tlayerp[numlayersMinus1] * thick[numlayersMinus1] * htcap[numlayersMinus1] / deltat;

								//  TRIDIAGONAL MATRIX SOLUTION FROM JACOBSON, p. 166
								gam[TUFreg3D.ONE] = -D[TUFreg3D.ONE] / B[TUFreg3D.ONE];
								tlayer[TUFreg3D.ONE] = R[TUFreg3D.ONE] / B[TUFreg3D.ONE];

								for (int k = 2 - 1; k < numlayers; k++)
								{
									denom[k] = B[k] + A[k] * gam[k - 1];
									tlayer[k] = (R[k] - A[k] * tlayer[k - 1]) / denom[k];
									gam[k] = -D[k] / denom[k];
								}

								for (int k = numlayersMinus2; k > 0-1; k--)
								{
									// do k=numlayers-1,1,-1
									tlayer[k] = tlayer[k] + gam[k] * tlayer[k + 1];
								}

								for (int k = 0; k < numlayers; k++)
								{
									sfc_ab[iabCount][k + 5] = tlayer[k];
								}

							}
							// 324 continue
							// 349 continue

							 
							double amodTime = (timeis % 24.);
							UTCI utci = new UTCI();

							// ------------------------------------------------------------------
							//  VISUALIZATION - output for Matlab							
							if (ywrite && (first_write
									|| ((timeis % outpt_tm) * 3600.0 < deltat && (int) (timeis * 100.) != timewrite)
									|| last_write))
							{

								ywrite = false;
								timewrite = (int) (timeis * 100.);

								System.out.println("------------------------------------------");
								System.out.println("TIME (hours) = " + " " + timeis + " of " + timeend);
								System.out.println("TIMESTEP (s) = " + " " + deltat);
								System.out.println("" + (timeis % outpt_tm) * 3600. + " " + deltat);
								if (ralt < 0.)
								{
									System.out.println("NIGHTTIME: solar azimuth, elevation angles = " + " " + az + " " + ralt);
								}
								else
								{
									System.out.println("DAYTIME: solar azimuth, elevation angles = " + " " + az + " " + ralt);
								}

								if (ralt > 0.0)
								{
									System.out.println("average relative Kdown absorptionerror = " + " "
											+ Kdn_diff / ((nKdndiff) + 1.e-9) + " " + "%");
								}
								if (Kdn_diff / ((nKdndiff) + 1.e-9) > 5.0 && nKdndiff > 10)
								{
									System.out.println("time average relative Kdn error = " + " "
											+ Kdn_diff / ((nKdndiff) + 1.e-9) + " " + "%");
									overall.writeOutput(Constants.inputs_store_out,
											"-------------------------------------");
									overall.writeOutput(Constants.inputs_store_out,
											"time, time average relative Kdn error = " + " " + timeis + " "
													+ Kdn_diff / ((nKdndiff) + 1.e-9) + " " + "%");
								}
								Kdn_diff = 0.;
								nKdndiff = 0;
								System.out.println("Kdif,Kdir,Kdown(total) = " + " " + Kdif + " " + Kdir + " " + Ktot);
								System.out.println("time,Troof,Tstreet,Tnorth,Tsouth,Teast,Twest" + " " + timeis + " "
										+ Tsfc_R / (numroof2) + " " + Tsfc_T / (numstreet2) + " " + Tsfc_N / (numNwall2)
										+ " " + Tsfc_S / (numSwall2) + " " + Tsfc_E / (numEwall2) + " "
										+ Tsfc_W / (numWwall2));

//								//  WRITE OUTPUT								
								output.outputEnergyBalanceOverallOut(overall, lpactual, bh, bl, bw, hwactual, xlat, stror,
										yd_actual, amodTime, timeis, Rnet_tot, Qh_tot, Qh_abovezH, Aplan, Qg_tot,
										ustar, Qhtop, Qhcan, Bcan, Acan, wstar, lambdapR, Rnet_R,
										numroof2, Qg_R, zH, zd, z0, Fm, lambdaf, patchlen, Ccan, Kdir,
										Kdif, Kup, Ldn, Lup, Kdir_Calc, Kdif_Calc, Kup_R, Lup_R, az,
										zen, Kdir_NoAtm, Kdn_grid, Qe_tot, Qh_R, Qh_T, Rnet_T, Qg_T, Rnet_N, numstreet2, numNwall2, numSwall2,
										numEwall2, numWwall2, Qh_N, Qg_N, Rnet_S, Qh_S, Qg_S, Rnet_E, Qh_E,
										Qg_E, Rnet_W, Qh_W, Qg_W, numwall2, Kdn_S, Kup_S, Ldn_S, Lup_S, Kdn_E, Kup_E, Ldn_E, Lup_E,
										Kdn_N, Kup_N, Ldn_N, Lup_N, Kdn_W, Kup_W, Ldn_W, Lup_W,
										Kdn_R, Kdn_T, Ldn_R, Kup_T, Ldn_T, Lup_T, Tsfc_cplt, Tsfc_bird,
										Tsfc_R, Tsfc_T, Tsfc_N, Tsfc_S, Tsfc_E, Tsfc_W, Tcan, Ta, Tintw, 
										httcR, httcT, httcW, Trad_R, Trad_T, Trad_N, Trad_S, Trad_E, Trad_W,
										TTsun, TTsh, TNsun, TNsh, TSsun, TSsh, TEsun, TEsh, TWsun, TWsh, 
										numTsun, numTsh, numNsun, numNsh, numSsun, numSsh, numEsun, numEsh, numWsun, numWsh);

								//  to output time averages
								if (!first_write)
								{
									output.outputTimeAverages(overall, lpactual, bh, bl, bw, hwactual, xlat, stror, yd, timeis, outpt_tm, amodTime, counter2, Kuptot_avg, Luptot_avg,
											Rntot_avg, Qhtot_avg, Qgtot_avg, Qanthro_avg, Qac_avg, Qdeep_avg, Qtau_avg, TR_avg, TT_avg, TN_avg, TS_avg, TE_avg, TW_avg);
									
									counter2 = 0; Kuptot_avg = 0.; Luptot_avg = 0.; Rntot_avg = 0.; Qhtot_avg = 0.; Qetot_avg = 0.; Qgtot_avg = 0.;
									Qanthro_avg = 0.; Qac_avg = 0.; Qdeep_avg = 0.; Qtau_avg = 0.; TR_avg = 0.; TT_avg = 0.; TN_avg = 0.; TS_avg = 0.; TE_avg = 0.; TW_avg = 0.;
								}

								//  write out intra-facet (patch) surface temperatures								
								output.outputFacets(facet_out, bh, aw2, al2, sfc, surf, ind_ab, Tsfc, Trad, reflts, absbs, timeis, overall  );

								int time_out = ((int) Math.round(timeis * 10.)) * 10;
								int lptowrite = (int) Math.round(lpin[lpiter] * 100.);
								String lpwrite = common.padLeft( lptowrite, 2, '0') ;
								int bhbltowrite = (int) Math.round(bh_o_bl[bhiter] * 100.);
								String bhblwrite = common.padLeft( bhbltowrite, 3, '0') ;
								String strorwrite = common.padLeft( (int) Math.round(stror), 2, '0') ;
								String latwrite = common.padLeft( (int) Math.round(Math.abs(xlat)), 3, '0') ;

								if (xlat >= 0.)
								{
									latwrite2 = latwrite + "N";
								}
								else
								{
									latwrite2 = latwrite + "S";
								}
								String ydwrite = common.padLeft( yd, 3, '0') ;

								tempTimeis = (int) (timeis * 2);
								if (tempTimeis < 1)
								{
									tempTimeis = 1;
								}
								System.out.println("TUF/Maespa, timeis" + " " + timeis + " " + tempTimeis);


								output.outputTbrightTsfc(sum_out, time_out, ydwrite, lpwrite, bhblwrite, latwrite2,
										strorwrite, overall, numsfc2, bh, bl, sw, sw2, bw, al2, aw2, lpactual, xlat, stror, patchlen, ralt, yd,
										surf, ind_ab, Tsfc, Trad, sfc);

								
								outputResults.outputMatlab(matlab_out, time_out, first_write, writeTsfc, writeKl, 
										writeKabs, writeKrefl, writeLabs, writeLrefl, writeLdown, writeTmrt, 
										writeUtci, writeEnergyBalances, lpwrite, bhblwrite, ydwrite, latwrite2, 
										strorwrite, newlp, newbhbl, numsfc, iab, bh, aw2, al2, surf, sfc, overall,  
										ind_ab, Tsfc, tots, totl, reflts, refltl, absbs, timeis, Ldn, treeXYMap, 
										sfc_ab_map_x, sfc_ab_map_y, absbl, diffShadingValueUsed, tempTimeis, Tcan, 
										ea, Ua,  zen, Acan, Bcan, Ccan, patchlen, currentRnet, currentQh, currentQe, 
										currentQg, utci, maespaDataArray);		

								UrbanPlumberOutput outputUrbanPlumber = new UrbanPlumberOutput();
								outputUrbanPlumber.output( time_out, first_write, overall, tots, totl, reflts, refltl, 
										absbs, timeis, Ldn, Tcan, ea, Ua, Aplan, Rnet_tot, Kup, Lup, Qh_tot, Qe_tot, 
										Kdn_grid, Qg_tot, yd, year, Tsfc_R, Tsfc_T, Tsfc_N, Tsfc_S, Tsfc_E, Tsfc_W, 
										numroof2, numstreet2, numNwall2, numSwall2, numEwall2, numWwall2, Kdir, Kdif);	

								first_write = false;
								//  whether or not it is a timestep to write outputs
							}
							if (last_write)
							{
								last_write = false;
								frcwrite = false;
								// goto 351;
								return;
							}
							timeis = timeis + deltat / 3600.;

							if ((timeis % outpt_tm) >= outpt_tm - 3.5 * deltat / 3600.)
							{
								ywrite = true;
							}
							//// 309 continue
						}
						//
						// if(ywrite)
						// {
						// last_write=true;
						// //!! KN had to comment this out because compiler crashes
						// //! goto 349
						// }
						//// 351 continue
						// last_write=false;
						// frcwrite=false;
						stror = stror + strorint;
						//  this is the enddo for the street orientation iteration
					}
					xlat = xlat + xlatint;
					//  this is the enddo for the latitude iteration
				}
				//  this is the enddo for the bh iteration
			}
			//  this is the enddo for the lp iteration
		}
		System.out.println("------------------------------------------");
		System.out.println("absolute value of relative sky view factor error(maximum of all simulations) was:" + " "
				+ svfe_store + " " + "%(averaged over the central urban unit)");
		System.out.println("------------------------------------------");
		System.out.println("absolute value of absolute received Kdown error(maximum of all simulations) was:" + " "
				+ Kdn_ae_store + " "
				+ " W/m2(averaged over the central urban unit) and the absolutevalue of the relative received Kdown error at this timestep was:"
				+ " " + 100. * Kdn_re_store + " " + "%");
		System.out.println("------------------------------------------");
		System.out.println("Received solar radiation was at least 10 W/m2AND 5.0% in error during " + " " + badKdn + " "
				+ " time steps over thecourse of the simulation(s)");
		if (badKdn > 0)
		{
			System.out.println(
					"...you may need to increase the resolution;the file Inputs_Store.out will tell you which simulations (if you performed more than one)suffered the most from a lack of resolution");
		}
	}

}
