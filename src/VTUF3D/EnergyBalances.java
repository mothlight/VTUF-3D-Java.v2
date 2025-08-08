package VTUF3D;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;


import VTUF3D.Utilities.Common;
import VTUF3D.Utilities.Namelist;



public class EnergyBalances 
{
	
	Common common = new Common();
	VTUF3DUtil util = new VTUF3DUtil();

	// double[] currentRnet
	// double[] currentQh
	// double[] currentQe
	// double[] currentQg
	// double[] Tsfc
	// double Rnet_tot Qh_tot Qe_tot Qg_tot
	// double leFromEt5
	// double simpelQe
	public HashMap energyBalanceForVegetation(int[][] treeXYMap, int[] sfc_ab_map_x, int[] sfc_ab_map_y, int iabCount, double timeis, int diffShadingValueUsed,
			double[] Tsfc, HashMap<String, ArrayList<MaespaDataResults>> maespaDataArray, int[][] treeXYTreeMap, double leFromEt5, double simpelQe,
			double[][] sfc, double[][] sfc_ab, int iIndex10, double patchlen, double zH, double Rnet, double httc, double Tconv,
			 double[] currentRnet, double[] currentQh, double[] currentQe, double[] currentQg, double Rnet_tot, double Qh_tot, double Qe_tot, double Qg_tot,
			 int sixPlusThreeTimesNumlayers, double[] lambda_sfc)
	{
		
		if (treeXYMap[sfc_ab_map_x[iabCount]-1][sfc_ab_map_y[iabCount]-1] != 0)
		{
			// ! print
			// *,'----------------------------------------'
			int tempTimeis = (int) (timeis * 2);
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
		
		HashMap energyBalanceForVegetationReturn = new HashMap();
		
		energyBalanceForVegetationReturn.put("currentRnet",currentRnet);
		energyBalanceForVegetationReturn.put("currentQh",currentQh);
		energyBalanceForVegetationReturn.put("currentQe",currentQe);
		energyBalanceForVegetationReturn.put("currentQg",currentQg);
		energyBalanceForVegetationReturn.put("Tsfc",Tsfc);
		energyBalanceForVegetationReturn.put("Rnet_tot",Rnet_tot);
		energyBalanceForVegetationReturn.put("Qh_tot",Qh_tot);
		energyBalanceForVegetationReturn.put("Qe_tot",Qe_tot);
		energyBalanceForVegetationReturn.put("Qg_tot",Qg_tot);
		energyBalanceForVegetationReturn.put("leFromEt5",leFromEt5);
		energyBalanceForVegetationReturn.put("simpelQe",simpelQe);

		return energyBalanceForVegetationReturn;
		
	}
	
	// Tsfc_N Trad_N Rnet_N Kdn_N Kup_N Ldn_N Lup_N Qh_N Qg_N TNsun TNsh numNsun numNsh
	// Qanthro Qac
	public HashMap wallEnergyBalance(double[] Tsfc, int iabCount, double[][] sfc, int iIndex10, int sixPlusThreeTimesNumlayers, int numlayersMinus1, int fivePlusNumlayers,
			double Rnet, double httc, double Tconv, double Tintw, double[] tots, double[] reflts, double[] totl, double[] refltl, double[] lambda_sfc,
			double[][] sfc_ab, double[] thickw, double[] lambdaavw,
			double Tsfc_DIR, double Trad_DIR, double Rnet_DIR, double Kdn_DIR, double Kup_DIR, double Ldn_DIR, double Lup_DIR, double Qh_DIR, double Qg_DIR,
			double Qanthro, double Qac, double TDIRsun, double TDIRsh, int numDIRsun, int numDIRsh)
	{
		Tsfc_DIR = Tsfc_DIR + Tsfc[iabCount];
		Trad_DIR = Trad_DIR + Math.pow(((1. / TUFreg3D.sigma) * (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4)+ refltl[iabCount])), (0.25));
		Rnet_DIR = Rnet_DIR + Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
		Kdn_DIR = Kdn_DIR + tots[iabCount];
		Kup_DIR = Kup_DIR + reflts[iabCount];
		Ldn_DIR = Ldn_DIR + totl[iabCount];
		Lup_DIR = Lup_DIR + refltl[iabCount] + sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
		Qh_DIR = Qh_DIR + httc * (Tsfc[iabCount] - Tconv);
		Qg_DIR = Qg_DIR + lambda_sfc[iabCount] * (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
		Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers]) * lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
		Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw) * lambdaavw[numlayersMinus1] * 2. / thickw[numlayersMinus1]);
		if (sfc[iIndex10][Constants.sfc_sunlight_fact] > 3.5)
		{
			TDIRsun = TDIRsun + Tsfc[iabCount];
			numDIRsun = numDIRsun + 1;
		}
		else if (sfc[iIndex10][Constants.sfc_sunlight_fact] < 0.5)
		{
			TDIRsh = TDIRsh + Tsfc[iabCount];
			numDIRsh = numDIRsh + 1;
		}
		
		HashMap wallEnergyBalanceReturn = new HashMap();
		
		wallEnergyBalanceReturn.put("Qanthro",Qanthro);
		wallEnergyBalanceReturn.put("Qac",Qac);
		wallEnergyBalanceReturn.put("Tsfc_DIR",Tsfc_DIR);
		wallEnergyBalanceReturn.put("Trad_DIR",Trad_DIR);
		wallEnergyBalanceReturn.put("Rnet_DIR",Rnet_DIR);
		wallEnergyBalanceReturn.put("Kdn_DIR",Kdn_DIR);
		wallEnergyBalanceReturn.put("Kup_DIR",Kup_DIR);
		wallEnergyBalanceReturn.put("Ldn_DIR",Ldn_DIR);
		wallEnergyBalanceReturn.put("Lup_DIR",Lup_DIR);
		wallEnergyBalanceReturn.put("Qh_DIR",Qh_DIR);
		wallEnergyBalanceReturn.put("Qg_DIR",Qg_DIR);
		wallEnergyBalanceReturn.put("TDIRsun",TDIRsun);
		wallEnergyBalanceReturn.put("TDIRsh",TDIRsh);
		wallEnergyBalanceReturn.put("numDIRsun",numDIRsun);
		wallEnergyBalanceReturn.put("numDIRsh",numDIRsh);		

		return wallEnergyBalanceReturn;
		
	}
	
	// tlayer tlayerp lambdaav htcap thick A B D R denom gam
	public HashMap<String,double[]> conductionLoop(int numsfc2, double Tintw, double[][] sfc_ab, double Tints, double[][] sfc, int numlayers, double[] tlayer,
			double[] tlayerp, double[] lambdaav, double[] htcap, double[] thick, double[] A, double[] B, double[] D, double[] R, double[] gam, double[] denom,
			int numlayersMinus1, double uc, double[] Tsfc, double deltat, double[] lambda_sfc, int numlayersMinus2, double IntCond)
	{
		
		
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

		HashMap<String,double[]> conductionLoopReturn = new HashMap<String,double[]>();
		conductionLoopReturn.put("tlayer",tlayer);
		conductionLoopReturn.put("tlayerp",tlayerp);
		conductionLoopReturn.put("lambdaav",lambdaav);
		conductionLoopReturn.put("htcap",htcap);
		conductionLoopReturn.put("thick",thick);
		conductionLoopReturn.put("A",A);
		conductionLoopReturn.put("B",B);
		conductionLoopReturn.put("D",D);
		conductionLoopReturn.put("R",R);
		conductionLoopReturn.put("denom",denom);
		conductionLoopReturn.put("gam",gam);
		
		return conductionLoopReturn;
		
	}
	

	//return iab
	//return sfc
	//return sfc_ab
	//return sfc_ab_map_x
	//return sfc_ab_map_y
	//return sfc_ab_map_z
	//return sfc_ab_map_f
	//return numstreet2
	//return numroof2
	//return numwall2
	//return numNwall2, numSwall2, numEwall2, numWwall2
	//return lambda_sfc
	//return Tsfc
	public HashMap initMainArray(int a1, int a2, int b1, int b2, int bh, int aw2, int al2, boolean[][][][] surf, int iIndex12, int iab,
			double[][] sfc, double[][] sfc_ab, int[] sfc_ab_map_x, int[] sfc_ab_map_y, int[] sfc_ab_map_z, int[] sfc_ab_map_f,
			double albs, double emiss, double albr, double albw, double emisr, double emisw, int[][] treeXYTreeMap, int numlayers, double Tsfcs,
			double[] thicks, double[] lambdaavs, double[] htcaps, double[] thickr, double[] lambdaavr, double[] htcapr,
			double[] thickw, double[] lambdaavw, double[] htcapw, double[] lambdas, double[] lambdar, double[] lambdaw, double Tsfcw, double Tsfcr,
			int numstreet2, int numroof2, int numwall2, int numNwall2, int numSwall2, int numEwall2, int numWwall2,
			double[] lambda_sfc, double[] Tsfc)
	{
		HashMap returnValues = new HashMap();
		for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++)  
		{
			for (int z = 0; z <= bh; z++)
			{
				for (int y = 0; y <= aw2; y++)
				{
					for (int x = 0; x <= al2; x++)
					{
						if (surf[x][y][z][f])
						{
							iIndex12 = iIndex12 + 1;
							sfc[iIndex12][Constants.sfc_in_array] = 1.;

							//  if the patch is in the central urban
							// unit, sfc[i][sfc_in_array]=2.
							if (x >= a1 && x <= a2 && y >= b1 && y <= b2)
							{
								iab = iab + 1;
								// ! print *,'central urban unit
								// x,y,z,f,a1,a2,b1,b2,i,iab',x,y,z,f,a1,a2,b1,b2,i,iab
								sfc[iIndex12][Constants.sfc_in_array] = 2.;
								sfc_ab[iab][Constants.sfc_ab_i] = iIndex12;
								sfc_ab[iab][Constants.sfc_ab_f] = f;
								sfc_ab[iab][Constants.sfc_ab_z] = z;
								sfc_ab[iab][Constants.sfc_ab_y] = y;
								sfc_ab[iab][Constants.sfc_ab_x] = x;
								sfc_ab_map_x[iab] = x;
								sfc_ab_map_y[iab] = y;
								sfc_ab_map_z[iab] = z;
								sfc_ab_map_f[iab] = f;
							}

							//  set the roof, wall, and road albedos and emissivities
							//  and temperatures, and thermal properties and thicknesses
							if (f == TUFreg3D.FACE_ONE && z == 0) 
							{
								sfc[iIndex12][Constants.sfc_surface_type] = 2.;
								sfc[iIndex12][Constants.sfc_albedo] = albs;
								sfc[iIndex12][Constants.sfc_emiss] = emiss;
								// if this is a Maespa vegetation surface, set different albedo/emissivity
								if (treeXYTreeMap[x-1][y-1] > 0)
								{
									// !print *,'surface i=',i,' is vegetation'
									sfc[iIndex12][Constants.sfc_albedo] = Constants.vegetationAlbedo;
									sfc[iIndex12][Constants.sfc_emiss] = Constants.vegetationEmissivity;
								}
								sfc[iIndex12][Constants.sfc_x_vector] = 0.;
								sfc[iIndex12][Constants.sfc_y_vector] = 0.;
								sfc[iIndex12][Constants.sfc_z_vector] = 1.;
								if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
								{
									numstreet2 = numstreet2 + 1;
									for (int k = 0; k < numlayers; k++)
									{
										sfc_ab[iab][k + 3 * numlayers + 5] = thicks[k];
										sfc_ab[iab][k + numlayers + 5] = lambdaavs[k];
										sfc_ab[iab][k + 2 * numlayers + 5] = htcaps[k];
									}
									lambda_sfc[iab] = lambdas[TUFreg3D.ONE];
									Tsfc[iab] = Tsfcs;
								}
							}
							else if (f == TUFreg3D.FACE_ONE && z > 0) 
							{
								sfc[iIndex12][Constants.sfc_surface_type] = 1.;
								sfc[iIndex12][Constants.sfc_albedo] = albr;
								sfc[iIndex12][Constants.sfc_emiss] = emisr;
								sfc[iIndex12][Constants.sfc_x_vector] = 0.;
								sfc[iIndex12][Constants.sfc_y_vector] = 0.;
								sfc[iIndex12][Constants.sfc_z_vector] = 1.;
								if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
								{
									numroof2 = numroof2 + 1;
									for (int k = 0; k < numlayers; k++)
									{
										sfc_ab[iab][k + 3 * numlayers + 5] = thickr[k];
										sfc_ab[iab][k + numlayers + 5] = lambdaavr[k];
										sfc_ab[iab][k + 2 * numlayers + 5] = htcapr[k];
									}
									lambda_sfc[iab] = lambdar[TUFreg3D.ONE];
									Tsfc[iab] = Tsfcr;
								}
							}
								else
								{
									sfc[iIndex12][Constants.sfc_surface_type] = 3.;
								sfc[iIndex12][Constants.sfc_albedo] = albw;
								sfc[iIndex12][Constants.sfc_emiss] = emisw;
								sfc[iIndex12][Constants.sfc_z_vector] = 0.;
								if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
								{
									numwall2 = numwall2 + 1;
									for (int k = 0; k < numlayers; k++)
									{
										sfc_ab[iab][k + 3 * numlayers + 5] = thickw[k];
										sfc_ab[iab][k + numlayers + 5] = lambdaavw[k];
										sfc_ab[iab][k + 2 * numlayers + 5] = htcapw[k];
									}
									Tsfc[iab] = Tsfcw;
									lambda_sfc[iab] = lambdaw[TUFreg3D.ONE];
								}
								if (f == TUFreg3D.FACE_TWO) 
								{
									if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
									{
										numNwall2 = numNwall2 + 1;
									}
									sfc[iIndex12][Constants.sfc_x_vector] = 0.;
									sfc[iIndex12][Constants.sfc_y_vector] = 1.;
								}
								else if (f == TUFreg3D.FACE_THREE) 
								{
									if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
									{
										numEwall2 = numEwall2 + 1;
									}
									sfc[iIndex12][Constants.sfc_x_vector] = 1.;
									sfc[iIndex12][Constants.sfc_y_vector] = 0.;
								}
								else if (f == TUFreg3D.FACE_FOUR) 
								{
									if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
									{
										numSwall2 = numSwall2 + 1;
									}
									sfc[iIndex12][Constants.sfc_x_vector] = 0.;
									sfc[iIndex12][Constants.sfc_y_vector] = -1.;
								}
								else if (f == TUFreg3D.FACE_FIVE)
								{
									if (sfc[iIndex12][Constants.sfc_in_array] > 1.5)
									{
										numWwall2 = numWwall2 + 1;
									}
									sfc[iIndex12][Constants.sfc_x_vector] = -1.;
									sfc[iIndex12][Constants.sfc_y_vector] = 0.;
								}
								else
								{
									System.out.println("PROBL w/ sfc(i, ) assignment");
									System.exit(1);
								}
								
							}
						}
					}
				}
			}
		}
		
		returnValues.put("iab", iab);
		returnValues.put("sfc", sfc);
		returnValues.put("sfc_ab", sfc_ab);
		returnValues.put("sfc_ab_map_x", sfc_ab_map_x);
		returnValues.put("sfc_ab_map_y", sfc_ab_map_y);
		returnValues.put("sfc_ab_map_z", sfc_ab_map_z);
		returnValues.put("sfc_ab_map_f", sfc_ab_map_f);
		returnValues.put("numstreet2", numstreet2);
		returnValues.put("numroof2", numroof2);
		returnValues.put("numwall2", numwall2);
		returnValues.put("numNwall2", numNwall2);
		returnValues.put("numSwall2", numSwall2);
		returnValues.put("numEwall2", numEwall2);
		returnValues.put("numWwall2", numWwall2);
		returnValues.put("lambda_sfc", lambda_sfc);
		returnValues.put("Tsfc", Tsfc);
		//return iab
		//return sfc
		//return sfc_ab
		//return sfc_ab_map_x
		//return sfc_ab_map_y
		//return sfc_ab_map_z
		//return sfc_ab_map_f
		//return numstreet2
		//return numroof2
		//return numwall2
		//return numNwall2, numSwall2, numEwall2, numWwall2
		//return lambda_sfc
		//return Tsfc
		
		return returnValues;
	}
	
	//return zen, ralt, press, Kdir, Kdif, Ktot, alb_sfc, abs_aero, Ktotfrc, DR1F, Kbeam
	public HashMap<String,Double> calculateSunAnglesAndSolar(double xlat, double timeis, int yd, double zen, double ralt, double Ta, double Td, double press,
			double Kdir, double Kdif, double Ktot, double alb_sfc, int cloudtype, double abs_aero, double Ktotfrc , boolean calcKdn, double DR1F,
			OverallConfiguration overall, int yd_actual  )
	{
		HashMap<String,Double> sunAngleCalcReturn = new HashMap<String,Double>();
				
		double Kbeam;
		//  -------------------------------------------
		//  Solar angle and incoming shortwave (direct & diffuse) routines
		double LAT = xlat * Math.PI / 180.;
		double TM = (timeis % 24.);
		
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
		clrskyreturn = null;
		
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
		
		sunAngleCalcReturn.put("zen", zen);
		sunAngleCalcReturn.put("ralt", ralt);
		sunAngleCalcReturn.put("press", press);
		sunAngleCalcReturn.put("Kdir", Kdir);
		sunAngleCalcReturn.put("Kdif", Kdif);
		sunAngleCalcReturn.put("Kdif", Kdif);
		sunAngleCalcReturn.put("Ktot", Ktot);
		sunAngleCalcReturn.put("alb_sfc", alb_sfc);
		sunAngleCalcReturn.put("abs_aero", abs_aero);
		sunAngleCalcReturn.put("Ktotfrc", Ktotfrc);
		sunAngleCalcReturn.put("DR1F", DR1F);
		sunAngleCalcReturn.put("Kbeam", Kbeam);
		sunAngleCalcReturn.put("az", az);
		sunAngleCalcReturn.put("TM", TM);
		
		sunAngleCalcReturn.put("Kdir_NoAtm", Kdir_NoAtm);
		sunAngleCalcReturn.put("Kdir_Calc", Kdir_Calc);
		sunAngleCalcReturn.put("Kdif_Calc", Kdif_Calc);
		
		return sunAngleCalcReturn;
		
	}
	
	// return vfsum2
	public HashMap zeroithLongwaveReflection(double dalb, double lambdapR, int numsfc2, double[][] sfc_ab, double[] refll, double[] absbl, double[] reflpl,
			double[][] sfc, double Ldn, double[] refltl, double[] Tsfc, int[] vfppos, HashMap<Integer, Double> vf3,
			HashMap<Integer, Integer> vf3j, double avg_cnt)
	{
		HashMap zeroithLongwaveReflectionReturn = new HashMap();
	//  ---------------------------------
		//  LONGWAVE ONLY (solar has already been done in previous Tsfc-Lup  iteration)
		//  RADIATION INITIALIZATION

		//  zeroth longwave reflection (i.e. emission)
		double vfsum2 = 0.;

		for (int iabCount = 0; iabCount < numsfc2; iabCount++)
		{
			int iIndex4 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
			refltl[iabCount] = 0.;
			refll[iabCount] = sfc[iIndex4][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
			absbl[iabCount] = 0.;
			vfsum2 = vfsum2 + (1. - sfc[iIndex4][Constants.sfc_evf]);
		}
		//  MULTIPLE REFLECTION
		HashMap reflectionLoopReturn= reflectionLoop(dalb, lambdapR, numsfc2, sfc_ab, refll, absbl, reflpl,
				sfc, Ldn, refltl, Tsfc, vfppos, vf3, vf3j, avg_cnt );									
		double Lup=(double) reflectionLoopReturn.get("Lup");
		double Lup_refl_old=(double) reflectionLoopReturn.get("Lup_refl_old");
		double Lup_refl=(double) reflectionLoopReturn.get("Lup_refl");
		double Lemit5=(double) reflectionLoopReturn.get("Lemit5");
		refll=(double[]) reflectionLoopReturn.get("refll");
		absbl=(double[]) reflectionLoopReturn.get("absbl");
		reflpl=(double[]) reflectionLoopReturn.get("reflpl");
		refltl=(double[]) reflectionLoopReturn.get("refltl");									
		double refldiff=(double) reflectionLoopReturn.get("refldiff");
		reflectionLoopReturn=null;		
		
		for (int iabCount = 0; iabCount < numsfc2; iabCount++)
		{
			int iIndex5 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
			refltl[iabCount] = refltl[iabCount] - sfc[iIndex5][Constants.sfc_evf] * refll[iabCount];
			absbl[iabCount] = absbl[iabCount] + sfc[iIndex5][Constants.sfc_evf] * refll[iabCount];
		}
		
		zeroithLongwaveReflectionReturn.put("Lup", Lup );
		zeroithLongwaveReflectionReturn.put("Lup_refl_old", Lup_refl_old );
		zeroithLongwaveReflectionReturn.put("Lup_refl", Lup_refl );
		zeroithLongwaveReflectionReturn.put("Lemit5", Lemit5 );
		zeroithLongwaveReflectionReturn.put("refll", refll );
		zeroithLongwaveReflectionReturn.put("absbl", absbl );
		zeroithLongwaveReflectionReturn.put("reflpl", reflpl );
		zeroithLongwaveReflectionReturn.put("refltl", refltl );	
		zeroithLongwaveReflectionReturn.put("refldiff", refldiff );
		zeroithLongwaveReflectionReturn.put("vfsum2", vfsum2 );
		return zeroithLongwaveReflectionReturn;
	}
	
	// refls, refll, reflps, reflpl, absbl, refltl, absbs, reflts, Lup_refl, Lemit5, Kup_refl, Kup, Lup, Kup_refl_old
	public HashMap mainReflectionLoop(int k, double refldiff, double dalb, double lambdapR, int numsfc_ab, double[][] sfc, double[][] sfc_ab,
			double[] refls, double[] refll, double[] reflps, double[] reflpl, double[] absbl, double Ldn, double[] refltl, int numsfc2,
			double[] Tsfc, double[] absbs, double[] reflts, double Lup_refl, double Lemit5,
			HashMap<Integer, Double> vf3, HashMap<Integer, Integer> vf3j, int[] vfppos, double avg_cnt, double Lup_refl_old,
			double Kup_refl, double Kup, double Lup, double Kup_refl_old, double Kdir, double Kdif)
	{
		HashMap mainReflectionReturn = new HashMap();

	//  MAIN reflection loop: does at least 2 shortwave and 1 longwave reflection, and goes until change in
		// both overall albedo and overall (1-emis) are less than dalb multiplied by a
		// factor that recognizes that there is little or no multiple reflection at roof level and above (lambdapR is  lambdap at roof level)
		while (k < 2 || refldiff >= dalb * (1. - lambdapR))
		{
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
						Lup_refl = Lup_refl - sfc[iIndex7][Constants.sfc_emiss] * (1. - sfc[iIndex7][Constants.sfc_evf]) * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
						Lemit5 = Lemit5 + sfc[iIndex7][Constants.sfc_emiss] * sfc[iIndex7][Constants.sfc_evf] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
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
					vfOpen = vf3.get(pCount);
					int jab = vf3j.get(pCount);
					absbs[iabCount] = absbs[iabCount] + vfOpen * reflps[jab] * (1. - sfc[iIndex8][Constants.sfc_albedo]);

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

		}
		mainReflectionReturn.put("refls",refls);
		mainReflectionReturn.put("refll",refll);
		mainReflectionReturn.put("reflps",reflps);
		mainReflectionReturn.put("reflpl",reflpl);
		mainReflectionReturn.put("absbl",absbl);
		mainReflectionReturn.put("refltl",refltl);
		mainReflectionReturn.put("absbs",absbs);
		mainReflectionReturn.put("reflts",reflts);
		mainReflectionReturn.put("Lup_refl",Lup_refl);
		mainReflectionReturn.put("Lemit5",Lemit5);
		mainReflectionReturn.put("Kup_refl",Kup_refl);
		mainReflectionReturn.put("Kup_refl",Kup_refl);
		mainReflectionReturn.put("Kup",Kup);
		mainReflectionReturn.put("Lup",Lup);
		mainReflectionReturn.put("Kup_refl_old",Kup_refl_old);
		
		return mainReflectionReturn;
	}
	
	// Fm ustar wstar Ccan Bcan Acan Uwrite Twrite Ucanpy
	public HashMap logWindProfile(double lambdapR, double Tsfc_R, int numroof2, double Tcan, double zref, double zd, double Ta, double Ua, 
			double Fm, double ustar, double Qhcan, double rhocan, double zH, double z0, double moh, double wstar, double lambdaf, double Ccan,
			double Acan, double Bcan, double[] Uwrite, double[] Twrite, double Tlog_fact)
	{
		HashMap logWindProfileReturn = new HashMap();
		

		
		double Tzd = lambdapR * Tsfc_R / (1.0*numroof2) + (1. - lambdapR) * Tcan;
		double Ri = util.SFC_RI(zref - zd, Ta, Tzd, Ua);
		HashMap<String, Double> cdReturn = VTUF3DUtil.CD(Ri, zref - zd, z0, z0 / moh);
		Fm = cdReturn.get("Fm");
		double cdtown = cdReturn.get("cd_out");
		ustar = Math.sqrt(cdtown) * Ua;
		double Qhcan_kin = Math.max(0., Qhcan / rhocan / TUFreg3D.cpair);
		wstar = Math.pow((9.806 / Tcan * Qhcan_kin * zH), (1. / 3.));

		
		// BISECTION METHOD FOR U PROFILE!!!
		double bp = ustar / TUFreg3D.vK / Math.sqrt(Fm);
		double bm = zH - zd;
		// The following is what Masson uses (but his model is an area average), so
		// I've replaced it with an equivalent 3-D expression
		double bn = -2. * lambdaf / (1. - lambdapR) / 4.;
		double bq = z0;

		checkUtopTooLarge(ustar, zH, zd, z0, Fm, Ua, lambdaf, lambdapR);

		double CL = 0.01;
		double CR = CL + 0.1;
		double FR = bp * Math.exp(-CR * zH) / bm / CR - bp * Math.log(bm / bq) * (1. - Math.exp(bn))
				/ (Math.exp(CR * zH) - Math.exp(CR * zH / 2.));
		while (FR >= 1.e-20)
		{
			CR = CR + 0.1;
			FR = bp * Math.exp(-CR * zH) / bm / CR - bp * Math.log(bm / bq) * (1. - Math.exp(bn)) / (Math.exp(CR * zH) - Math.exp(CR * zH / 2.));
			
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
		}
		Ccan = (CR + CL) / 2.;
		

		// constants for the canyon wind profile (Ccan also)
		Bcan = bp * Math.exp(-Ccan * zH) / bm / Ccan;
		Acan = -Bcan * Math.exp(Ccan * zH) + bp * Math.log(bm / bq);

		for (int iii = 0; iii < (int) Math.round(zH - 0.5); iii++)
		{
			double zzz = 1.0*iii;
			double Ucantst = Acan + Bcan * Math.exp(Ccan * zzz);
			if (Ucantst > Ua || Ucantst < 0.)
			{
				double Ucan = Double.NaN;
				System.out.println("bad Ucan at z=" + " " + zzz + " " + Ucan);
				System.exit(1);
			}
		}

		for (int iii = 0; iii < (int) Math.round(zref - 0.5); iii++)
		{
			double zzz = 1.0*iii;
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
		
		
		logWindProfileReturn.put("Fm",Fm);
		logWindProfileReturn.put("ustar",ustar);
		logWindProfileReturn.put("wstar",wstar);
		logWindProfileReturn.put("Ccan",Ccan);
		logWindProfileReturn.put("Bcan",Bcan);
		logWindProfileReturn.put("Acan",Acan);
		logWindProfileReturn.put("Uwrite",Uwrite);
		logWindProfileReturn.put("Twrite",Twrite);
		logWindProfileReturn.put("Ucanpy",Ucanpy);
		        
		
		return logWindProfileReturn;
	}
	
	// httc, Thorz, zhorz, Uhorz, rhohorz
	public HashMap<String,Double> calcHorz(int numsfc2, double[][] sfc_ab, double[][] sfc, double patchlen, double zH, double z0, double zd, double ustar,
			double Tcan, double Tlog_fact, double wstar, double Fm, double Acan, double Bcan, double Ccan, double rw, double zref, double httc,
			double press, double rhocan, double zrooffrc, double Lroof, double Ta, double rhohorz, double z0roofm, double z0roofh,
			double z0roadm, double z0roadh, double[] Tsfc, int iIndex10, double Thorz, double zhorz, double Uhorz, int iabCount)
	{
		HashMap<String,Double> calcHorzReturn = new HashMap<String,Double>();
				
		if (sfc[iIndex10][Constants.sfc_surface_type] > 2.5)
		{
			double Ueff;
			double Ucan;
			// ! WALLS - convection coefficients
			double zwall = (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen;
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
				// ! roofs:
				// ! Harman et al. 2004 approach: 0.1*average roof length
				double Ri3 = util.SFC_RI(zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen, Thorz, Tsfc[iabCount], Uhorz);
				if ((sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
				{
					HashMap<String, Double> htcReturn2 = util.HTC(Ri3, Math.sqrt(Math.pow(Uhorz, 2) + Math.pow(wstar, 2)), zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen, z0roofm, z0roofh);
					httc = htcReturn2.get(VTUF3DUtil.HTTC_OUT_INDEX);
					double Fh = htcReturn2.get(VTUF3DUtil.FH_INDEX);
				}
				else
				{
					HashMap<String, Double> htcReturn3 =  util.HTC(Ri3, Uhorz, zhorz - (sfc[iIndex10][Constants.sfc_z] - 0.5) * patchlen,  z0roofm, z0roofh);
					httc = htcReturn3.get(VTUF3DUtil.HTTC_OUT_INDEX);
					double Fh = htcReturn3.get(VTUF3DUtil.FH_INDEX);
				}
			}
			else
			{
				// streets: Harman et al. 2004 approach:  0.1*average building height
				double Ri4 = util.SFC_RI(0.1 * zH, Thorz, Tsfc[iabCount], Uhorz);
				HashMap<String, Double> htcReturn4 = util.HTC(Ri4, Math.sqrt(Math.pow(Uhorz, 2) + Math.pow(wstar, 2)), 0.1 * zH, z0roadm, z0roadh);
				httc = htcReturn4.get(VTUF3DUtil.HTTC_OUT_INDEX);
				double Fh = htcReturn4.get(VTUF3DUtil.FH_INDEX);
			}
		httc = httc * TUFreg3D.cpair * rhohorz;
		}
		
		calcHorzReturn.put("httc",httc);
		calcHorzReturn.put("Thorz",Thorz);
		calcHorzReturn.put("zhorz",zhorz);
		calcHorzReturn.put("Uhorz",Uhorz);
		calcHorzReturn.put("rhohorz",rhohorz);
		return calcHorzReturn;
	}
	
	// Tdiffmax, httc, Tsfc, Trad
	public HashMap solvePatchTsfcNewton(int iabCount, double[] Tsfc, double[][] sfc, int iIndex10, double httc, double Rnet, double Tconv,
			double[][] sfc_ab, int sixPlusThreeTimesNumlayers, double[] lambda_sfc, double Tdiffmax, double[] refltl, double[] Trad)
	{
		HashMap solvePatchReturn = new HashMap();
		double Tnew = Tsfc[iabCount];
		double Told = Tnew + 999.;

		// ITERATION to solve individual patch Tsfc[i] by Newton's method----
		int patchItrCount = 0;
		int httcRetries = 0;
		while (Math.abs(Tnew - Told) > 0.001)
		{
			Told = Tnew;
			double Fold = sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Told, 4)
					+ (httc + lambda_sfc[iabCount] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers]) * Told - Rnet - httc * Tconv
					- lambda_sfc[iabCount] * sfc_ab[iabCount][Constants.sfc_ab_layer_temp] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
			double Fold_prime = 4. * sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Told, 3) + httc
					+ lambda_sfc[iabCount] * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
			Tnew = -Fold / Fold_prime + Told;
			if (Double.isNaN(Tnew))
			{
				System.out.println();
			}
//			System.out.println(patchItrCount + " " + Tnew + " " + Told + " " + Fold + " " + Fold_prime);
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
					System.out.println(	 "|  " + iabCount + " " + Told+ " "+ httc + " "+ Rnet + " "+ Tconv+ " "+ sfc_ab[iabCount][Constants.sfc_ab_layer_temp] );
					System.out.println("||| " + sfc[iIndex10][Constants.sfc_emiss] + " " + TUFreg3D.sigma + " " +  lambda_sfc[iabCount] + " " +  sfc_ab[iabCount][sixPlusThreeTimesNumlayers] + " " +  Tnew );
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
		
		solvePatchReturn.put("Tdiffmax",Tdiffmax);
		solvePatchReturn.put("httc",httc);
		solvePatchReturn.put("Tsfc",Tsfc);
		solvePatchReturn.put("Trad",Trad);
		return solvePatchReturn;
	}
	
	public void checkUtopTooLarge(double ustar, double zH, double zd, double z0, double Fm, double Ua, double lambdaf, double lambdapR)
	{
		
		if (ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) > Ua
				|| ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm)
						* Math.exp(-2. * lambdaf / (1. - lambdapR) / 4.) > ustar / TUFreg3D.vK
								* Math.log((zH - zd) / z0) / Math.sqrt(Fm))
		{
			System.out.println("Utop larger than Ua, or Ucan larger than Utop");
			System.exit(1);
		}
	}
	
	
	// absbs absbl tots totl reflts refltl Kup Lup
	public HashMap remainingReflections(int numsfc2, double[][] sfc, double[][] sfc_ab, double[] absbs, double[] absbl, 
			double[] refls, double[] refll, double[] tots, double[] totl, double[] reflts, double[] refltl, double Kup, double Lup)
	{		
		HashMap remainingReflectionsReturn = new HashMap();
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

			refltl[iabCount] = refltl[iabCount] - sfc[iIndex9][Constants.sfc_evf] * refll[iabCount];
			absbl[iabCount] = absbl[iabCount] + sfc[iIndex9][Constants.sfc_evf] * refll[iabCount];
			Kup = Kup + (1. - sfc[iIndex9][Constants.sfc_evf]) * refls[iabCount];
			Lup = Lup + (1. - sfc[iIndex9][Constants.sfc_evf]) * refll[iabCount];	
		}
		remainingReflectionsReturn.put("absbs",absbs);
		remainingReflectionsReturn.put("absbl",absbl);
		remainingReflectionsReturn.put("tots",tots);
		remainingReflectionsReturn.put("totl",totl);
		remainingReflectionsReturn.put("reflts",reflts);
		remainingReflectionsReturn.put("refltl",refltl);
		remainingReflectionsReturn.put("Kup",Kup);
		remainingReflectionsReturn.put("Lup",Lup);
      
		return remainingReflectionsReturn;
	}
	
	// Kdn_grid, vfsum2, refll, absbl, absbs, refls, reflts, svfe_store, lpin, bh_o_bl, Kdn_ae_store, Kdn_diff, nKdndiff, Kdn_re_store
	public HashMap solarAndLongwaveReflections(double az, double stror, double ralt, int numsfc_ab, double[][] sfc, double[][] sfc_ab,
			double Kdn_grid, boolean first_write, double[] refll, double[] absbl, double Ktot, double[] Tsfc,
			double Kdif, double Kbeam, double[] reflts, double avg_cnt, double svfe_store,
			OverallConfiguration overall, double[] bh_o_bl, int lpiter, int bhiter, double xlat,
			double wavelenx, double waveleny, double Kdir, double Kdn_ae_store, double Kdn_diff, double nKdndiff, double Kdn_re_store,
			double[] absbs, double[] refls, double[] lpin)
	{
		HashMap solarAndLongwaveReturn = new HashMap();

		// the unit vector pointing from the surface towards the sun
		double[] angsun = new double[3];
		double[] angsfc = new double[3];
		double angdif = az - stror;
		if (angdif < 0.)
		{
			angdif = az + (360. - stror);
		}
		angsun[TUFreg3D.ONE] = util.sind(angdif) * util.cosd(ralt);
		angsun[TUFreg3D.TWO] = util.cosd(angdif) * util.cosd(ralt);
		angsun[TUFreg3D.THREE] = util.sind(ralt);

		// ! first solar absorption and reflection, and zeroth longwave reflection (i.e. emission)
		double solarin = 0.;
		Kdn_grid = 0.;
		double nKgrid = 0;
		double vfsum2 = 0.;

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

				absbs[iabCount] = absbs[iabCount] + (1. - sfc[iIndex6][Constants.sfc_albedo]) * Kbeam * Math.cos((g)) * sfc[iIndex6][Constants.sfc_sunlight_fact] / 4.;
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
		
		solarAndLongwaveReturn.put("Kdn_grid", Kdn_grid );
		solarAndLongwaveReturn.put("vfsum2", vfsum2 );
		solarAndLongwaveReturn.put("refll", refll );
		solarAndLongwaveReturn.put("absbl", absbl );
		solarAndLongwaveReturn.put("absbs", absbs );
		solarAndLongwaveReturn.put("absbs", absbs );
		solarAndLongwaveReturn.put("refls", refls );
		solarAndLongwaveReturn.put("refls", refls );
		solarAndLongwaveReturn.put("reflts", reflts );
		solarAndLongwaveReturn.put("svfe_store", svfe_store );
		solarAndLongwaveReturn.put("lpin", lpin );
		solarAndLongwaveReturn.put("lpin", lpin );
		solarAndLongwaveReturn.put("bh_o_bl", bh_o_bl );
		solarAndLongwaveReturn.put("Kdn_ae_store", Kdn_ae_store );
		solarAndLongwaveReturn.put("Kdn_diff", Kdn_diff );
		solarAndLongwaveReturn.put("nKdndiff", nKdndiff );
		solarAndLongwaveReturn.put("Kdn_re_store", Kdn_re_store );
		return solarAndLongwaveReturn;
	}
	
	//return Lup, Lup_refl_old, Lup_refl, Lemit5, refll, absbl, reflpl, refltl, Tsfc
	public HashMap reflectionLoop(double dalb, double lambdapR, int numsfc2, double[][] sfc_ab, double[] refll, double[] absbl, double[] reflpl,
			double[][] sfc, double Ldn, double[] refltl, double[] Tsfc, int[] vfppos, HashMap<Integer, Double> vf3,
			HashMap<Integer, Integer> vf3j, double avg_cnt )
	{
		HashMap reflectionLoopReturn = new HashMap();

		
		//  MULTIPLE REFLECTION
		double Lup = 0.;
		double Lup_refl = 0.;
		double Lup_refl_old = 0.;
		double refldiff = 1.1;
		Lup_refl = 0.;
		double Lemit5 = 0.;
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
//					vf = vf3[pCount];
					vfOpen = vf3.get(pCount);
//					jab = vf3j[pCount];
					int jab = vf3j.get(pCount);
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
		
		reflectionLoopReturn.put("Lup", Lup );
		reflectionLoopReturn.put("Lup_refl_old", Lup_refl_old );
		reflectionLoopReturn.put("Lup_refl", Lup_refl );
		reflectionLoopReturn.put("Lemit5", Lemit5 );
		reflectionLoopReturn.put("refll", refll );
		reflectionLoopReturn.put("absbl", absbl );
		reflectionLoopReturn.put("reflpl", reflpl );
		reflectionLoopReturn.put("refltl", refltl );	
		reflectionLoopReturn.put("refldiff", refldiff );
		return reflectionLoopReturn;
	}

	//return sfc
	//return mend
	//return vffile, vfppos, vfipos
	public HashMap viewFactors(int bh, int aw2, int al2, boolean[][][][] surf, double[][] sfc, int vfcalc, int[] mend, int numsfc2,
			int a1, int a2, int b1, int b2, double[][] sfc_ab, int[] vffile, int[] vfppos, int[] vfipos, 
			boolean[][][] surf_shade, int maxbh, int[] ind_ab, String filename)
	{
		HashMap vfReturnValues;
		vfReturnValues = readDataFromDisk(filename);
		if (vfReturnValues == null)
		{}
		else
		{
			return vfReturnValues;
		}
		
		int numfiles;
		double vftot5= 0.;
		int numvf = 0;
		double[][] v = new double[4][3];
		double[] vmag = new double[4];
		double[] vangle = new double[4];
		double[] cp = new double[3];
		double[][] corner = new double[4][3];
		double magvns;
		double xpinc, ypinc;
		double mcp;
		double yyy;
		double z2 = 0;
		double x2;
		double vf;
		double y1, z1;
		double separat;
		double vxx, vyy, vzz;
		double vfsum;
		String numc, numc2, numc3;
		int j=0;
		double vftot;
		double vx, vy, vz;
		int p = 0;
		double[] vns = new double[3];
		double[] vtemp1 = new double[3];
		double[] vtemp2 = new double[3];
		double[] vecti = new double[3];
		double[] vectj = new double[3];

		double[] dx = new double[3];
		double[] fx = new double[5], fy = new double[5], fz = new double[5], fxx = new double[5], fyy = new double[5],
				fzz = new double[5];
		HashMap<Integer,Double> vf2;
		HashMap<Integer,Double> vf3;
		HashMap<Integer,Integer> vf3j;
		HashMap<Integer,Integer> vf2j;
		
		//  direction vectors
		for (int k = 0; k < 5; k++)
		{
			fx[k] = 0.;
			fy[k] = 0.;
			fz[k] = 0.;
		}
		fx[TUFreg3D.THREE] = 0.5;
		fx[TUFreg3D.FIVE] = -0.5;
		fy[TUFreg3D.TWO] = 0.5;
		fy[TUFreg3D.FOUR] = -0.5;
		fz[TUFreg3D.ONE] = 0.5;

		int iIndex2 = 0-1;//for array zero indexing
		for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++) 
		{
			for (int z = 0; z < bh; z++)
			{
				for (int y = 1; y <= aw2; y++)
				{
					for (int x = 1; x <= al2; x++)
					{
						if (!surf[x][y][z][f])
						{
							// goto 284
							continue;
						}
						iIndex2 = iIndex2 + 1;

						//  patch i surface center:
						sfc[iIndex2][Constants.sfc_x] = 1.0*x + fx[f-1];
						sfc[iIndex2][Constants.sfc_y] = 1.0*y + fy[f-1];
						sfc[iIndex2][Constants.sfc_z] = 1.0*z + fz[f-1];

						// 284 continue
					}
				}
			}
		}

		System.out.println("------------------------------------------");

//		vf2 = new double[numsfc * numsfc2];
		vf2 = new HashMap<Integer,Double>();
//		vf2j = new int[numsfc*numsfc2];
		vf2j = new HashMap<Integer,Integer>();

		dx = new double[3];
		vecti = new double[3];
		vectj = new double[3];
		vns = new double[3];
		vtemp1 = new double[3];
		vtemp2 = new double[3];

		// int x,y,z;
		// ------------------------------------------------------------------
		//  View Factor Calculations (or read in from file)
System.out.println("++++++++++++++++++++++++start vfcalc=" + (System.currentTimeMillis() - TUFreg3D.startTime)/1000./60. );		
		if (vfcalc == 0)
		{
			// going to always calculate
			//
			// //! must read in
			// vffile[i],sfc[i][Constants.sfc_evf],vfipos[i],mend[i] etc
			// //! from file if view factors are already calculated and
			// stored
			// //! in files
			// open(unit=vfinfoDat,file="vfinfo.dat",access="DIRECT",recl=vfinfoDatRECL);
			// read(unit=vfinfoDat,rec=1)numfiles,numvf;
			// for (int iab=0;iab<numsfc2;iab++)
			// {
			// i=sfc_ab[iab][Constants.sfc_ab_i];
			// read(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc[i][Constants.sfc_evf],sfc[i][Constants.sfc_x],sfc[i][sfc_y],sfc[i][sfc_z];
			// }
			// read(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
			// close(vfinfoDat);
		}
		else
		{

			System.out.println("CALCULATING VIEW FACTORS...");
			// write(6,*)'CALCULATING VIEW FACTORS...'

			// vf2 = new double[numsfc*numsfc2];
			// vf2j = new int[numsfc*numsfc2];
			// allocate(vf2(numsfc*numsfc2))
			// allocate(vf2j(numsfc*numsfc2))

			// X=0;
			// Y=0;
			// Z=0;
			// numvf=0;

			//  direction vectors
			for (int k = 0; k < 5; k++)
			{
				fx[k] = 0.;
				fy[k] = 0.;
				fz[k] = 0.;
			}
			fx[TUFreg3D.THREE] = 0.5;
			fx[TUFreg3D.FIVE] = -0.5;
			fy[TUFreg3D.TWO] = 0.5;
			fy[TUFreg3D.FOUR] = -0.5;
			fz[TUFreg3D.ONE] = 0.5;

			for (int k = 0; k < 5; k++)
			{
				fxx[k] = fx[k];
				fyy[k] = fy[k];
				fzz[k] = fz[k];
			}

			

			double fact2 = 500000.;

			int n = 11;
			// numc="(i1)"+ (n-10);
			// open(unit=n,file="vf"+numc,access="DIRECT",recl=vfRECL);
			int m = 1;
			p = 1-1;
			int iab = 0-1;//zero indexed arrays

			mend = new int[numsfc2];
			for (int k = 0; k < numsfc2; k++)
			{
				mend[k] = 0;
			}

			//  RUN THROUGH ALL ARRAY POSITIONS AND ONLY PERFORM CALCULATIONS ON POINTS SEEN
			for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++) 
			{
				if (f == TUFreg3D.FACE_ONE)
				{
					System.out.println("calculating view factors of horizontal patches...");	
				}
				else if (f == TUFreg3D.FACE_TWO) 
				{
					System.out.println("calculating view factors of north-facing patches...");
				}
				else if (f == TUFreg3D.FACE_THREE) 
				{
					System.out.println("calculating view factors of east-facing patches...");
				}
				else if (f == TUFreg3D.FACE_FOUR) 
				{
					System.out.println("calculating view factors of south-facing patches...");
				}
				else if (f == TUFreg3D.FACE_FIVE) 
				{
					System.out.println("calculating view factors of west-facing patches...");
				}
				int iIndex11 = 0-1;//zero indexed arrays
				for (int z = 0; z <= bh; z++)
				{
					System.out.println("starting loop z="+z + " of " + bh);
					for (int y = b1; y <= b2; y++)
					{
						System.out.println("starting loop y="+y + " of " + b2);
						for (int x = a1; x <= a2; x++)
						{
//							System.out.println("starting x="+x);
							if (!surf[x][y][z][f])
							{
								// goto 41;
								continue;
							}
							iab = iab + 1;
							iIndex11 = (int) sfc_ab[iab][Constants.sfc_ab_i];

							//  patch surface i center:
							vx = 1.*x + fx[f-1];
							vy = 1.*y + fy[f-1];
							vz = 1.*z + fz[f-1];

							vftot = 0.;

							j = 0-1;//zero indexed arrays

							if (m > fact2)
							{
								mend[iab - 1] = m - 1;
								m = 1;
								// close(unit=n);
								n = n + 1;
								if (n <= 19)
								{
									numc = "(i1)" + (n - 10);
									// open(unit=n,file="vf"+numc,access="DIRECT",recl=vfRECL);
								}
								else if (n >= 20 && n <= 109)
								{
									numc2 = "(i2)" + (n - 10);
									// open(unit=n,file="vf"+numc2,access="DIRECT",recl=vfRECL);
								}
								else
								{
									System.out.println("need to program in more vf files or increase # vfs");
									// more vf files or increase # vfs";
									System.out.println("each one can hold");
									// stop
									System.exit(1);
								}
							}

							vffile[iab] = n;
							vfipos[iab] = m;
							vfppos[iab] = p;

							for (int ff = TUFreg3D.FACE_ONE; ff <= TUFreg3D.FACE_FIVE; ff++) 
							{
								for (int zz = 0; zz <= bh; zz++)
								{
									for (int yy = 0; yy <= aw2; yy++)
									{
										for (int xx = 0; xx <= al2; xx++)
										{
											if (!surf[xx][yy][zz][ff])
											{
												// goto 81;
												continue;
											}
											

											vfsum = 0.0;

											j = j + 1;

											// patch surface j center:
											vxx = 1.*xx + fxx[ff-1];
											vyy = 1.*yy + fyy[ff-1];
											vzz = 1.*zz + fzz[ff-1];

											dx[TUFreg3D.ONE] = Math.abs(vx - vxx);
											dx[TUFreg3D.TWO] = Math.abs(vy - vyy);
											dx[TUFreg3D.THREE] = Math.abs(vz - vzz);
											separat = Math.sqrt(Math.pow((dx[TUFreg3D.ONE]), 2) + Math.pow((dx[TUFreg3D.TWO]), 2) + Math.pow((dx[TUFreg3D.THREE]), 2));

											//  a surface cannot see itself (no concave surfaces, only flat)
											//  also, ray tracing (function ray) to determine if the 2 sfcs can see each other
											if (iIndex11 == j || 
													!util.ray(x, y, z, f, xx, yy, zz, ff, surf_shade, fx, fy, fz, fxx, fyy, fzz, al2, aw2, maxbh))
											{
												vf = 0.;
												// goto 81;
												continue;
											}
											else
											{

												if (vfcalc == 1)
												{
													// calculation of exact view factors for PLANE PARALLEL facets ONLY
													vecti[TUFreg3D.ONE] = 1.*(sfc[iIndex11][Constants.sfc_x_vector]);
													vecti[TUFreg3D.TWO] = 1.*(sfc[iIndex11][Constants.sfc_y_vector]);
													vecti[TUFreg3D.THREE] = 1.*(sfc[iIndex11][Constants.sfc_z_vector]);
													vectj[TUFreg3D.ONE] = 1.*(sfc[j][6-1]);
													vectj[TUFreg3D.TWO] = 1.*(sfc[j][7-1]);
													vectj[TUFreg3D.THREE] = 1.*(sfc[j][8-1]);

													HashMap<String, Double> returnValues = Dotpro.dotpro(vecti, vectj, 3);
													double dp, g;
													dp = returnValues.get("dp");
													g = returnValues.get("g");

													if (Math.abs(dp) < 0.0001)
													{
														// 
														// perpendicular
														z1 = 0.;
														y1 = 0.;
														// patch separation distances in the three dimensions:
														for (int k = 0; k < 3; k++)
														{
															z1 = z1 + Math.abs((1.*vecti[k]) * dx[k]);
															y1 = y1 + Math.abs((1.*vectj[k]) * dx[k]);
															// 
															// if(double(vecti(k)).eq.0.0.and.double(vectj(k)).eq.
															// ! & 0.0)
															// x2=dx(k)
														}
														x2 = dx[TUFreg3D.ONE] + dx[TUFreg3D.TWO] + dx[TUFreg3D.THREE] - z1 - y1;
														if (x2 < 0.1)
														{
															// use F7, the patches are aligned in one dimension
															vf = util.F7(1.0, (y1 - 0.5), 1.0, (z1 - 0.5), 1.0);
														}
														else
														{
															// use F9, the patches aren't aligned in any of the three dimensions
															// subtract 0.5 or 1 to get distance to patch edge instead of patch center
															vf = util.F9(1.0, (x2 - 1.0), 1.0, (y1 - 0.50), 1.0, (z1 - 0.5), 1.0);
														}
													}
													else
													{
														//  parallel
														x2 = 0.;
														// patch separation distances in the three dimensions:
														for (int k = 0; k < 3; k++)
														{
															if (Math.abs((1.*vecti[k])) < 0.1)
															{
																if (x2 == 0.0)
																{
																	x2 = dx[k] + 0.1;
																}
																z2 = dx[k];
															}
														}
														x2 = x2 - 0.1;
														yyy = dx[TUFreg3D.ONE] + dx[TUFreg3D.TWO] + dx[TUFreg3D.THREE] - x2 - z2;

														if (z2 == 0.0 || x2 == 0.0)
														{
															// use F3, the patches are aligned in one dimension, or pll if they are
															// directly opposite
															vf = util.pll(1.0, yyy, 1.0);
															if (z2 + x2 > 0.1)
															{
																vf = util.F3(1.0, yyy, 1.0, (Math.max(x2, z2) - 1.0), 1.0);
															}
														}
														else
														{
															// use F5, the patches aren't aligned in any of the three dimensions
															vf = util.F5(1.0, (x2 - 1.0), 1.0, yyy, 1.0, (z2 - 1.0), 1.0);
														}
													}

													if (vf > 1.0 || vf < 0.0)
													{
														System.out.println("vfprobexact" + " " + iIndex11 + " " + j + " " + vf);
														// write(6,*)'vfprobexact',i,j,vf
														// stop
														System.exit(1);
													}

													vf = Math.abs(vf);
												}
												else
												{
													// calculate normal vector of cell face (patch) i
													// to get a positive answer, the progression of corner points around the patch should be CLOCKWISE
													vns[TUFreg3D.ONE] = 1.*fx[f-1];
													vns[TUFreg3D.TWO] = 1.*fy[f-1];
													vns[TUFreg3D.THREE] = 1.*fz[f-1];
													magvns = Math.sqrt( Math.pow(vns[TUFreg3D.ONE], 2) + Math.pow(vns[TUFreg3D.TWO], 2) + Math.pow(vns[TUFreg3D.THREE], 2));

													// normalize the normal vector from point i
													vns[TUFreg3D.ONE] = vns[TUFreg3D.ONE] / magvns;
													vns[TUFreg3D.TWO] = vns[TUFreg3D.TWO] / magvns;
													vns[TUFreg3D.THREE] = vns[TUFreg3D.THREE] / magvns;
													// Find polygon corners for patch j and define the vectors to these vertices
													// These are contained in the array v(iv,k) with k=1,3 corresponding to x,y,z respectively
													// loop through the four corner points of this patch    
													for (int iv = 0; iv < 4; iv++)
													{
														xpinc = -0.5;
														ypinc = -0.5;
														if ((iv >= 2) && (iv <= 3))
														{
															xpinc = 0.5;
														}
														if ((iv >= 1) && (iv <= 2))
														{
															ypinc = 0.5;
														}
														//  set corner points
														if (ff == 1)
														{
															corner[iv][TUFreg3D.ONE] = 1.0* vxx + xpinc;
															corner[iv][TUFreg3D.TWO] = 1.0* vyy + ypinc;
															corner[iv][TUFreg3D.THREE] = 1.0* vzz;
														}
														else if (ff == 2)
														{
															corner[iv][TUFreg3D.ONE] = 1.0* (vxx) - (xpinc);
															corner[iv][TUFreg3D.TWO] = 1.0* (vyy);
															corner[iv][TUFreg3D.THREE] = 1.0* (vzz) + (ypinc);
														}
														else if (ff == 4)
														{
															corner[iv][TUFreg3D.ONE] = 1.0* (vxx) + (xpinc);
															corner[iv][TUFreg3D.TWO] = 1.0* (vyy);
															corner[iv][TUFreg3D.THREE] = 1.0* (vzz) + (ypinc);
														}
														else if (ff == 3)
														{
															corner[iv][TUFreg3D.ONE] = 1.0* (vxx);
															corner[iv][TUFreg3D.TWO] = 1.0* (vyy) + (xpinc);
															corner[iv][TUFreg3D.THREE] = 1.0* (vzz) + (ypinc);
														}
														else if (ff == 5)
														{
															corner[iv][TUFreg3D.ONE] = 1.0* (vxx);
															corner[iv][TUFreg3D.TWO] = 1.0* (vyy) - (xpinc);
															corner[iv][TUFreg3D.THREE] = 1.0* (vzz) + (ypinc);
														}
														else
														{
															System.out.println(
																	"PROBLEM, ff not properly defined");
															// write(6,*)"PROBLEM,
															// ff not
															// properly
															// defined";
															// stop;
															System.exit(1);
														}

														// set vector between point i and corner point of j
														v[iv][TUFreg3D.ONE] = corner[iv][TUFreg3D.ONE] - 1.0*vx;
														v[iv][TUFreg3D.TWO] = corner[iv][TUFreg3D.TWO] - 1.0*vy;
														v[iv][TUFreg3D.THREE] = corner[iv][TUFreg3D.THREE] - 1.0*vz;
														vmag[iv] = Math.sqrt(Math.pow(v[iv][TUFreg3D.ONE], 2) + Math.pow(v[iv][TUFreg3D.TWO], 2) 
															+ Math.pow(v[iv][TUFreg3D.THREE], 2));
														// ! write(*,*)
														// 'v(iv,1-3)
														// ',v[iv][1-1],v[iv][2-1],v[iv][3-1]
													}

													// The following section is common for different surfaces.                  
													// Find angles between the vectors using the dot product rule: cos angle = u dot v / |u||v|
													// dotproducts are: x1*x2+y1*y2+z1*z2 where x1,y1,z1 and x2,y2,z2 are two vectors

													// Calculate the cross products between the vectors. This defines a vector normal
													// to the plane between the two vectors as required by the view factor calculation.
													for (int iv = 0; iv < 4; iv++)
													{
														for (int k = 0; k < 3; k++)
														{
															vtemp1[k] = v[iv][k];
															if (iv <= 3)
															{
																vtemp2[k] = v[iv + 1][k];
															}
															else
															{
																vtemp2[k] = v[TUFreg3D.ONE][k];
															}
														}

														// Use the dot product to define the angle between vectors between two adjacent corners of the patch
														HashMap<String, Double> dotproreturnValues = Dotpro.dotpro(vtemp1, vtemp2, 3);
														double dp, g;
														dp = dotproreturnValues.get("dp");
														g = dotproreturnValues.get("g");

														vangle[iv] = g;

														// Now do the cross product
														HashMap crossproReturn = Crosspro.crosspro(vtemp1, vtemp2, 3);
														cp = (double[]) crossproReturn.get("cp");
														mcp = (double) crossproReturn.get("mcp");

														if (mcp <= 0)
														{
															System.out.println("warning: mcp <=0");
															// write(*,*)
															// 'warning:
															// mcp <=0'
															System.out.println( "x y z " + " " + x + " " + y + " " + z);
															// write(*,*)
															// 'x y z
															// ',x,y,z
														}
														//  normalize the cross product by the |vtemp1 x vtemp2|
														for (int k = 0; k < 3; k++)
														{
															cp[k] = cp[k] / mcp;
														}

														// Now find the dot product of the vector normal to the plane between the two vectors and the vector
														// normal to the plane of surface i.
														dotproreturnValues = Dotpro.dotpro(cp, vns, 3);
														dp = dotproreturnValues.get("dp");
														g = dotproreturnValues.get("g");

														// Here is the view factor definition (as per Ashdown 1994, eqn 5.6; see also Baum et al. 1989, and
														// Hottel and Sarofim 1967)     
														vfsum = vfsum + dp * vangle[iv];
													}
													// Here is the view factor for this patch (can use this to test for limits); this is the "normal"
													// view factor definition (i.e. for an entire hemisphere).
													// The direction in which the corner points are processed matters - it yields a positive or
													// negative number. Convert negatives to positives. 
													vf = 1.*vfsum / (2. * Math.PI);

													// Taking absolute sum is necessary because relative progression around patch is sometimes clockwise (+)
													// and sometimes counterclockwise (-) depending on relative position of the patches
													// These could be pre-defined, but probably easier to take absolute sum here.
													if (vf < 0)
													{
														vf = Math.abs(vf);
													}

													// exact or contour integration view factors 'if'
												}

												// whether or not patch i sees patch j 'if'
											}

											if (vf > 0.)
											{
												// write(unit=n,rec=m)ind_ab(j),vf;
												vftot5 = vftot5 + vf;
												numvf = numvf + 1;
//												vf2[p] = vf;
												vf2.put(p,vf);
//												vf2j[p] = ind_ab[j];
												vf2j.put(p,ind_ab[j]) ;
												p = p + 1;
												m = m + 1;
											}
											else
											{
											}

											vftot = vftot + vf;

											// 81 continue
										}
										// xx=1;
									}
									// xx=1;
									// yy=1;
								}
								// xx=1;
								// yy=1;
								// zz=0;
							}

							sfc[iIndex11][Constants.sfc_evf] = vftot;
							// 41 continue
						}
						// x=a1;
					}
					// x=a1;
					// y=b1;
				}
				// x=a1;
				// y=b1;
				// z=0;
			}

			vfipos[numsfc2 + 1-1] = m;
			vfppos[numsfc2 + 1-1] = p;
			System.out.println("total number of inter-patch view factors = " + " " + numvf);

			numfiles = n - 10;

			if (iab+1 != numsfc2)
			{
				System.out.println("number of surfaces in view factor calculation wrong");
				System.out.println("PROB w/ numsfc2: iab, numsfc2 =" + " " + iab + " " + numsfc2);
				System.exit(1);
			}

			//  write file so that view factors need not be recomputed
			// open(unit=vfinfoDat,file="vfinfo.dat",access="DIRECT",recl=vfinfoDatRECL)
			// write(unit=vfinfoDat,rec=1)numfiles,numvf
//			for (int iabCount = 0; iabCount < numsfc2; iabCount++)
//			{
//				i = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
//				// write(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc[i][Constants.sfc_evf],sfc[i][sfc_x],sfc[i][sfc_y],sfc[i][sfc_z];
//			}
			// write(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
			// close(vfinfoDat);

		}

		// Move this section outside of the if so that vf3 and vf3j scope remains for the later use
//		vf3 = new double[numvf];
		vf3 = new HashMap<Integer,Double>();
//		vf3j = new int[numvf];
		vf3j = new HashMap<Integer,Integer>();

		//  arrays of view factors
		for (int k = 0; k < numvf; k++)
		{
//			vf3[k] = vf2[k];
			vf3.put(k,vf2.get(k));
//			vf3j[k] = vf2j[k];
			vf3j.put(k, vf2j.get(k));
		}
		
		//don't need anymore, clear out memory
		vf2 = null;
		vf2j = null;
		
		vfReturnValues = new HashMap();
		vfReturnValues.put("sfc", sfc);
		vfReturnValues.put("mend", mend);
		vfReturnValues.put("vffile", vffile);
		vfReturnValues.put("vfppos", vfppos);
		vfReturnValues.put("vfipos", vfipos);
		vfReturnValues.put("vf3", vf3);
		vfReturnValues.put("vf3j", vf3j);
		vfReturnValues.put("numvf", numvf);
		vfReturnValues.put("p", p);
		saveDataToDisk(vfReturnValues, filename);		
		return vfReturnValues;
	}
	
//	public TreeMap readBinaryDataFromDisk(String filename)
//	{	
//		TreeMap tree = null;
//		
//		if (common.verifyFileExists(filename))
//		{}
//		else
//		{
//			return tree;
//		}
//		
//		try
//		{
//			FileInputStream fileInputStream;
//			fileInputStream = new FileInputStream(filename);
//			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//			
//			tree = (TreeMap) objectInputStream.readObject();
//			
//			objectInputStream.close();
//			fileInputStream.close();
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//		catch (ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		return tree;
//	}
//	
//	public void saveBinaryDataToDisk(TreeMap treemap, String filename)
//	{
//		try
//		{
//			FileOutputStream fileOutputStream = new FileOutputStream(filename);
//			DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
//			
//			objectOutputStream.writeObject(treemap);
//			
//			
//			dataOutputStream.close();
//			fileOutputStream.close();
//		}
//		catch (FileNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}
		
	public HashMap readDataFromDisk(String filename)
	{	
		HashMap tree = null;
		
		if (common.verifyFileExists(filename))
		{}
		else
		{
			return tree;
		}
		
		try
		{
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(filename);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			
			tree = (HashMap) objectInputStream.readObject();
			
			objectInputStream.close();
			fileInputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return tree;
	}
	
	public void saveDataToDisk(HashMap treemap, String filename)
	{
		try
		{
			FileOutputStream fileOutputStream = new FileOutputStream(filename);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(treemap);
			objectOutputStream.close();
			fileOutputStream.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public HashMap<String,Double> calcLdn(double Ktotfrc, double[] Ldnfrc, int restartedRunStartTimestep, double ea, double Ta, double sigma, int cloudtype, boolean calcKdn, boolean calcLdn)
	{
		double Ldn_fact=1;
		double Ldn=0;
		
		
		
		if (Ktotfrc < -90.)
		{
			calcKdn = true;
		}
		// this fixes a bug. Before, it was always calculating since calcLdn wasn't initialized
		
		if (Ldnfrc[restartedRunStartTimestep] < 0.)
		{
			calcLdn = true;
			// ! Prata's clear sky formula (QJRMS 1996)
			Ldn = (1. - (1. + 46.5 * ea / Ta) * Math.exp(-(Math.pow((1.2 + 3. * 46.5 * ea / Ta), (0.5))))) * sigma * Math.pow(Ta, 4);

			// ! Sellers (1965) modification of Ldown based on cloud type (cloud base height) in Oke (1987)
			if (cloudtype == 0)
			{
				// ! clear:
				Ldn_fact = 1.00;
			}
			else if (cloudtype == 1)
			{
				// ! cirrus:
				Ldn_fact = 1.04;
			}
			else if (cloudtype == 2)
			{
				// ! cirrostratus:
				Ldn_fact = 1.08;
			}
			else if (cloudtype == 3)
			{
				// ! altocumulus:
				Ldn_fact = 1.17;
			}
			else if (cloudtype == 4)
			{
				// ! altostratus:
				Ldn_fact = 1.20;
			}
			else if (cloudtype == 7)
			{
				// ! cumulonimbus:
				Ldn_fact = 1.21;
			}
			else if (cloudtype == 5)
			{
				// ! stratocumulus/cumulus:
				Ldn_fact = 1.22;
			}
			else if (cloudtype == 6)
			{
				// ! thick stratus (Ns?):
				Ldn_fact = 1.24;
			}
			else
			{
				System.out.println("cloudtype must be between 0 and 7, cloudtype = " + " " + cloudtype);
			}

			Ldn = Ldn * Ldn_fact;
			System.out.println("Ldown calc Prata & Sellers, Ldown (W/m2) = " + " " + Ldn);
		}
		
		if (!calcLdn)
		{
			System.out.println("Ldown (W/m2) = " + " " + Ldn);
			System.out.println("Ldown (W/m2) = " + " " + Ldn);
		}

		if (calcKdn)
		{
			System.out.println("Kdown (W/m2) = to be calculated");
			System.out.println("Kdown (W/m2) = to be calculated");
		}
		else
		{
			System.out.println("Kdown (W/m2) = " + " " + Ktotfrc);
			System.out.println("Kdown (W/m2) = " + " " + Ktotfrc);
		}
		
		HashMap<String,Double> calcLdnReturn = new HashMap<String,Double>();
		calcLdnReturn.put("Ldn_fact", Ldn_fact);
		calcLdnReturn.put("Ldn", Ldn);
		return calcLdnReturn;
	}
	
	public HashMap getLayerDepths( int numlayers, ParametersDat parameters, int numlayersMinusOne, double Intresist)
	{
		HashMap layerDepthsReturnValues = new HashMap();
		double[] depthr = new double[numlayers];
		double[] depths = new double[numlayers];
		double[] depthw = new double[numlayers];	
		double[] thickr = parameters.thickr;
		double[] thicks = parameters.thicks;
		double[] thickw = parameters.thickw;
		
		double[] lambdaavr = new double[numlayers];
		double[] lambdaavs = new double[numlayers];
		double[] lambdaavw = new double[numlayers];
		double[] lambdar = parameters.lambdar;
		double[] lambdas = parameters.lambdas;
		double[] lambdaw = parameters.lambdaw;
		
		
		
		//  Layer depths for three sfcs
		depthr[TUFreg3D.ONE] = thickr[TUFreg3D.ONE] / 2.;
		double thick_totr = thickr[TUFreg3D.ONE];
		for (int k = 1; k < numlayers; k++)
		{
			depthr[k] = depthr[k - 1] + (thickr[k - 1] + thickr[k]) / 2.;
			thick_totr = thick_totr + thickr[k];
		}
		depths[TUFreg3D.ONE] = thicks[TUFreg3D.ONE] / 2.;
		double thick_tots = thicks[TUFreg3D.ONE];
		for (int k = 1; k < numlayers; k++)
		{
			depths[k] = depths[k - 1] + (thicks[k - 1] + thicks[k]) / 2.;
			thick_tots = thick_tots + thicks[k];
		}
		depthw[TUFreg3D.ONE] = thickw[TUFreg3D.ONE] / 2.;
		double thick_totw = thickw[TUFreg3D.ONE];
		for (int k = 1; k < numlayers; k++)
		{
			depthw[k] = depthw[k - 1] + (thickw[k - 1] + thickw[k]) / 2.;
			thick_totw = thick_totw + thickw[k];
		}
		
		
		// Determine inter-layer thermal conductivities
		for (int k = 0; k < numlayersMinusOne; k++)
		{
			lambdaavr[k] = (thickr[k] + thickr[k + 1]) / (thickr[k] / lambdar[k] + thickr[k + 1] / lambdar[k + 1]);
		}
		// adding additional resistance (0.123 W/m2/K) at building interiors
		lambdaavr[numlayersMinusOne] = thickr[numlayersMinusOne] / 2. / (Intresist + thickr[numlayersMinusOne] / 2. / lambdar[numlayersMinusOne]);
		for (int k = 0; k < numlayersMinusOne; k++)
		{
			lambdaavs[k] = (thicks[k] + thicks[k + 1]) / (thicks[k] / lambdas[k] + thicks[k + 1] / lambdas[k + 1]);
		}
		lambdaavs[numlayersMinusOne] = lambdas[numlayersMinusOne];
		for (int k = 0; k < numlayersMinusOne; k++)
		{
			lambdaavw[k] = (thickw[k] + thickw[k + 1]) / (thickw[k] / lambdaw[k] + thickw[k + 1] / lambdaw[k + 1]);
		}
		// adding additional resistance (0.123 W/m2/K) at building interiors
		lambdaavw[numlayersMinusOne] = thickw[numlayersMinusOne] / 2. / (Intresist + thickw[numlayersMinusOne] / 2. / lambdaw[numlayersMinusOne]);
		
		layerDepthsReturnValues.put("depthr", depthr);
		layerDepthsReturnValues.put("depths", depths);
		layerDepthsReturnValues.put("depthw", depthw);
		layerDepthsReturnValues.put("thickr", thickr);
		layerDepthsReturnValues.put("thicks", thicks);
		layerDepthsReturnValues.put("thickw", thickw);
		layerDepthsReturnValues.put("thick_totr", thick_totr);
		layerDepthsReturnValues.put("thick_tots", thick_tots);
		layerDepthsReturnValues.put("thick_totw", thick_totw);
		
		
		layerDepthsReturnValues.put("lambdaavr", lambdaavr);
		layerDepthsReturnValues.put("lambdaavs", lambdaavs);
		layerDepthsReturnValues.put("lambdaavw", lambdaavw);
		layerDepthsReturnValues.put("lambdar", lambdar);
		layerDepthsReturnValues.put("lambdas", lambdas);
		layerDepthsReturnValues.put("lambdaw", lambdaw);
		
		return layerDepthsReturnValues;
	}
	
	public HashMap<String,Double> calculateCanyonAirspace(int aw, int al, int a1, int a2, int b1, int b2, double zH, double patchlen, int[][] bldhti)
	{
		double lambdapR = 0.;
		double canyair = 0.;
		for (int y = 0; y < aw; y++)
		{
			for (int x = 0; x < al; x++)
			{
				//  determine the total air volume below zH in the central urban unit
				if (x >= a1 && x <= a2 && y >= b1 && y <= b2)
				{
					canyair = canyair + Math.max(0., zH - (bldhti[x][y]) * patchlen);
					if ((1.0*bldhti[x][y]) * patchlen >= zH - 0.01)
					{
						lambdapR = lambdapR + 1.;
					}
				}
			}
		}
		lambdapR = lambdapR / ((1.0*a2 - a1 + 1) * (b2 - b1 + 1));
		
		HashMap<String,Double> calculateCanyonAirspaceReturn = new HashMap<String,Double>();
		
		calculateCanyonAirspaceReturn.put("lambdapR", lambdapR);
		calculateCanyonAirspaceReturn.put("canyair", canyair);

		return calculateCanyonAirspaceReturn;
	}
	
	public HashMap declareDataStructures(int al2, int aw2, int bh, int al, int aw, double zref, int[][] bldhti, int[][] veghti)
	{
		HashMap declareStructuresReturn = new HashMap();
		//  now declare:
		int numtrees2=0;
		int numtreetops2=0;
		int[][] veght = new int[al2 + 2][aw2 + 2];
		int[][] bldht = new int[al2 + 2][aw2 + 2];
		boolean[][][] surf_shade = new boolean[al2 + 2][aw2 + 2][bh + 2];
		boolean[][][] veg_shade = new boolean[al2 + 1][aw2 + 1][bh + 2];
		boolean[][][][] surf = new boolean[al2+1][aw2+1][bh+1][5+1];
		double[] Uwrite = new double[(int) Math.round(zref - 0.5)];
		double[] Twrite = new double[(int) Math.round(zref - 0.5)];

		for (int x = 0; x < al + 2; x++)
		{
			for (int y = 0; y < aw + 2; y++)
			{
				bldht[x][y] = 0;
				veght[x][y] = 0;
			}
		}

		//  here, copy the bldhti array to bldht then deallocate bldhti array
		for (int y = 0; y < aw2+1; y++)
		{
			for (int x = 0; x < al2+1; x++)
			{
				bldht[x][y] = bldhti[x][y];
				veght[x][y] = veghti[x][y];
				// ! also add up the number of tree surfaces (4 walls * tree height) + 1 roof
				if (veght[x][y] > 0)
				{
					numtrees2 = numtrees2 + (4 * veght[x][y]);
					numtreetops2 = numtreetops2 + 1;
				}
			}
		}
		declareStructuresReturn.put("numtrees2", numtrees2);
		declareStructuresReturn.put("numtreetops2", numtreetops2);
		declareStructuresReturn.put("veght", veght);
		declareStructuresReturn.put("bldht", bldht);
		declareStructuresReturn.put("surf_shade", surf_shade);
		declareStructuresReturn.put("veg_shade", veg_shade);
		declareStructuresReturn.put("surf", surf);
		declareStructuresReturn.put("Uwrite", Uwrite);
		declareStructuresReturn.put("Twrite", Twrite);
		
		return declareStructuresReturn;
	}
	
	public double calcZ0(boolean calcz0, double zH, double zd, double lambdaf, double z0)
	{
		if (calcz0)
		{
			//  Macdonald's method for z0
			z0 = zH * (1. - zd / zH) * Math.exp(-Math.pow((0.5 * 1.2 / Math.pow((0.4), 2) * (1. - zd / zH) * lambdaf), (-0.5)));
		}
		return z0;
	}
	
	public double calculateFrontalArea(boolean calclf, double Udirdom, int numEwall2, int numNwall2, int numstreet2, int numroof2, int numSwall2, int numWwall2,
			double lambdaf)
	{
		
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
		return lambdaf;
	}
	
	public HashMap<String,Double> interpolateForcing(double[] timefrc, int timefrc_index, double timeis, int numfrc, double deltatfrc, boolean calcLdn, double Ldn_fact,
			double[] Kdnfrc, double[] Ldnfrc, double[] Tafrc, double[] eafrc, double[] Uafrc, double[] Pressfrc, double[] Udirfrc, double stror, double Td)
	{	
		if (timefrc[timefrc_index] <= timeis)
		{
			timefrc_index = Math.min(numfrc + 1, timefrc_index + 1);
		}
		double Ktotfrc = Kdnfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
				* (Kdnfrc[timefrc_index] - Kdnfrc[timefrc_index - 1]);
		double Ldn = Ldnfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
				* (Ldnfrc[timefrc_index] - Ldnfrc[timefrc_index - 1]);
		double Ta = Tafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
				* (Tafrc[timefrc_index] - Tafrc[timefrc_index - 1]);
		Ta = Ta + 273.15;
		double ea = eafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
				* (eafrc[timefrc_index] - eafrc[timefrc_index - 1]);
		double Ua = Math.max(0.1, Uafrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1])
				/ deltatfrc * (Uafrc[timefrc_index] - Uafrc[timefrc_index - 1]));
		double Udir = Udirfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
				* (Udirfrc[timefrc_index] - Udirfrc[timefrc_index - 1]);
		double press = Pressfrc[timefrc_index - 1] + (timeis - timefrc[timefrc_index - 1]) / deltatfrc
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
		double Udirdom = Udir - stror;
		if (Udirdom < 0.)
		{
			Udirdom = Udir + (360. - stror);
		}
		
		HashMap<String,Double> interpolateForcingReturn = new HashMap<String,Double>();
		interpolateForcingReturn.put("Ktotfrc",Ktotfrc);
		interpolateForcingReturn.put("Ldn",Ldn);
		interpolateForcingReturn.put("Ta",Ta);
		interpolateForcingReturn.put("ea",ea);
		interpolateForcingReturn.put("Ua",Ua);
		interpolateForcingReturn.put("Udir",Udir);
		interpolateForcingReturn.put("press",press);
		interpolateForcingReturn.put("Td",Td);
		interpolateForcingReturn.put("Udirdom",Udirdom);
	
		return interpolateForcingReturn;
	}
	
	public HashMap<String,Integer> calcAboveZh(int numsfc_ab, double patchlen, double zH, double[][] sfc, double[][] sfc_ab)
	{
		int numabovezH = 0;
		int numcany = 0;
		for (int iabCount = 0; iabCount < numsfc_ab; iabCount++)
		{
			int i = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
			if (sfc[i][Constants.sfc_in_array] > 1.5)
			{
				int iIndex3 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];
				if ((sfc[iIndex3][Constants.sfc_z] - 0.5) * patchlen < zH - 0.01)
				{
					numcany = numcany + 1;
				}
				else
				{
					numabovezH = numabovezH + 1;
				}
			}
		}
		
		HashMap<String,Integer> calcAboveZhReturn = new HashMap<String,Integer>();
		calcAboveZhReturn.put("numcany",numcany);
		calcAboveZhReturn.put("numabovezH",numabovezH);
	
		return calcAboveZhReturn;
	}
	
	//return surf_shade, veg_shade, surf, numsfc, numsfc_ab
	public HashMap convertHeightsToShading(int bh, int aw2, int al2, int al, int aw, int a1, int a2, int b1, int b2, int bl, int bw,
			int[][] bldht, int[][] veght, boolean [][][] surf_shade, boolean [][][] veg_shade,
			boolean [][][][] surf, MaespaConfigTreeMapState treeMapFromConfig)
	{
		HashMap convertHeightsReturn = new HashMap();

		//  steps:
		//  make surf_shade array from bldht array

		//  faces: 1=up, 2=north, 3=east, 4=south, 5=west (subject to rotation up to 90 degrees, of course)
		// ! KN, added 6=vegetation

		//  general conversion from building height array to shading
		//  array of cells; true=street&building interior; false=ambient air:
		// if z height =0, could be street or building
		// z > 1 will be a building
		int numsfc = 0;
		int count = 0;
		for (int z = 0; z <= bh + 1; z++)
		{
			for (int y = 0; y <= aw2 + 1; y++)
			{
				for (int x = 0; x <= al2 + 1; x++)
				{
					surf_shade[x][y][z] = false;
					count ++;
					if (bldht[x][y] >= z)
					{
						surf_shade[x][y][z] = true;
					}
					if (veght[x][y] > z)
					{
						veg_shade[x][y][z] = true;
					}
				}
			}
		}
		System.out.println("general conversion count "+ count);

		// general conversion from shading array to surface
		// array (no parameter values yet though):

		// initialize array to contain no faces; surf=T means it is a surface patch,
		// surf=F means it is nothing (e.g. border between 2 building interior
		// cells or border between 2 ambient air cells)
		for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++) 
		{
			for (int z = 0; z <= bh; z++)
			{
				for (int y = 1; y <= aw2; y++)
				{
					for (int x = 1; x <= al2; x++)
					{
						surf[x][y][z][f] = false;
					}
				}
			}
		}
		// streets
		{
			int z = 0; // only in scope in this section
			for (int y = 1; y <= aw2; y++)
			{
				for (int x = 1; x <= al2; x++)
				{
					count ++;							
					surf[x][y][z][TUFreg3D.FACE_ONE] = true;  
					if (bldht[x][y] > 0)
					{
						surf[x][y][z][TUFreg3D.FACE_ONE] = false;  
					}
					else
					{
						numsfc = numsfc + 1;
					}
				} 
			}
		}
		// roofs and walls
		{
			//these are the faces
			// faces: 1=up, 2=north, 3=east, 4=south, 5=west
			int f = TUFreg3D.FACE_ONE; // only in scope in this section  
			for (int z = 1; z <= bh; z++)
			{
				for (int y = 1; y < aw2; y++)
				{
					for (int x = 1; x < al2; x++)
					{
						if (surf_shade[x][y][z])
						{
							f = TUFreg3D.FACE_ONE;  
							if (!surf_shade[x][y][z + 1] || z == bh)
							{
								surf[x][y][z][f] = true;
								numsfc = numsfc + 1;
							}
							f = TUFreg3D.FACE_TWO;  
							if ((y != aw) && (!surf_shade[x][y + 1][z]) )
							{
								surf[x][y][z][f] = true;
								numsfc = numsfc + 1;
							}
							f = TUFreg3D.FACE_THREE; 
							if ((x != al)  && !(surf_shade[x + 1][y][z]) )
							{
								surf[x][y][z][f] = true;
								numsfc = numsfc + 1;
							}
							f = TUFreg3D.FACE_FOUR; 
							if ( (y != 1) && (!surf_shade[x][y - 1][z]) )
							{
								surf[x][y][z][f] = true;
								numsfc = numsfc + 1;
							}
							f = TUFreg3D.FACE_FIVE;  
							if ((x != 1) && (!surf_shade[x - 1][y][z]) )
							{
								surf[x][y][z][f] = true;
								numsfc = numsfc + 1;
							}
						}
					}
				}
			}
		}

		System.out.println("------------------------------------------");
		System.out.println("number of patches (domain) = " + " " + numsfc);
		System.out.println(a2 + " " + a1 + " " + b2 + " " + b1 + " " + bh + " " + bl + " " + bw);
		int numsfc_ab = (a2 - a1 + 1) * (b2 - b1 + 1) + bh * 2 * (bl + bw);
		System.out.println("numsfc_ab2" + " " + numsfc_ab);
		// ! KN replace formula with value from config file
		numsfc_ab = treeMapFromConfig.configTreeMapNumsfcab; 
		System.out.println("number of patches (central urban unit) = " + " " + numsfc_ab);

		// ! KN haven't found a good way to count inner surfaces during the config process, so recount them here
		{
			int iIndex16=0; //only in htis scope
			int iabCount = 0; // only declare in this scope
			for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++)  
			{
				for (int z = 0; z <= bh; z++)
				{
					for (int y = 0; y <= aw2; y++)
					{
						for (int x = 0; x <= al2; x++)
						{
							if (surf[x][y][z][f])
							{

								iIndex16 = iIndex16 + 1;
								// ! print *,i
								if (x >= a1 && x <= a2 && y >= b1 && y <= b2)
								{
									// !print
									// *,'x,y,z,f,a1,a2,b1,b2,i,surf(x,y,z,f)',x,y,z,f,a1,a2,b1,b2,i,surf(x,y,z,f)

									iabCount = iabCount + 1;
									// ! print *,'iab',iab
								}
							}
						}
					}
				}
			}
			// ! stop
			if (iabCount > 0)
			{
				numsfc_ab = iabCount;
			}
			System.out.println("fixed number of patches (central urban unit) = " + " " + numsfc_ab);
		}
		
		
		convertHeightsReturn.put("surf_shade", surf_shade);
		convertHeightsReturn.put("veg_shade", veg_shade);
		convertHeightsReturn.put("surf", surf);
		convertHeightsReturn.put("numsfc", numsfc);
		convertHeightsReturn.put("numsfc_ab", numsfc_ab);
		
		return convertHeightsReturn;
	}
	
	//return bldhti, veghti, maxbh, numroof, bldht_tot
	public HashMap createDomainBarrayCube(int al, int aw, int bw, int bl, int sw, int sw2, int bh, int[][] treeXYMap,
			HashMap<String, HashMap<String, Namelist>> namelists, MaespaConfigTreeMapState treeMapFromConfig,
			double patchlen, double zref, double zH)
	{
		HashMap createDomainBarrayCubeReturnValues = new HashMap();
		

		
		// Create the domain (call barray_cube)
		int[][] bldhti = new int[al + 2][aw + 2];
		int[][] veghti = new int[al + 2][aw + 2];

		for (int x = 0; x < al + 2; x++)
		{
			for (int y = 0; y < aw + 2; y++)
			{
				bldhti[x][y] = 0;
				veghti[x][y] = 0;
			}
		}

		HashMap<String, int[][]> barray_returnValues = Barray_Cube.barray_cube(bw, bl, sw, sw2, al, aw, bh, bldhti, veghti, namelists, treeXYMap, treeMapFromConfig);
		bldhti = barray_returnValues.get("bldht");
		veghti = barray_returnValues.get("veght");

		int maxbh = 0;
		int numroof = 0;
		double bldht_tot = 0.;
		int count2 =0;
		for (int y = 1; y < aw+1; y++)
		{
			for (int x = 1; x < al+1; x++)
			{
				count2  = count2 + 1;
				
				if (bldhti[x][y] > 0)
				{
					bldht_tot = bldht_tot + bldhti[x][y];
					if (bldhti[x][y] > maxbh)
					{
						maxbh = bldhti[x][y];
					}
					numroof = numroof + 1;
				}
			}
		}
		System.out.println("maxbh,zref,zh" + " " + maxbh + " " + zref + " " + zH);
		if ((1.0*maxbh) * patchlen > zref - 0.1 * zH)
		{
			System.out.println("zref must be at least 0.1*zH above highest roof");
			System.out.println(
					"maxbh, zref, 0.1*zH (all in m) = " + " " + maxbh * patchlen + " " + zref + " " + 0.1 * zH);
			System.exit(1);
		}
		
		createDomainBarrayCubeReturnValues.put("bldhti", bldhti);
		createDomainBarrayCubeReturnValues.put("veghti", veghti);
		createDomainBarrayCubeReturnValues.put("maxbh", maxbh);
		createDomainBarrayCubeReturnValues.put("numroof", numroof);
		createDomainBarrayCubeReturnValues.put("bldht_tot", bldht_tot);
		return createDomainBarrayCubeReturnValues;
	}
	
	public int[] matchGrids(int a1, int a2, int b1, int b2, int bh, int aw2, int al2, boolean[][][][] surf, MaespaConfigTreeMapState treeMapFromConfig,
			double[][] sfc_ab, int numsfc2, boolean found, int iIndex, int numsfc)
	{
		int[] ind_ab = new int[numsfc];
		for (int f = TUFreg3D.FACE_ONE; f <= TUFreg3D.FACE_FIVE; f++) 
		{
			for (int z = 0; z <= bh; z++)
			{
				for (int y = 1; y <= aw2; y++)
				{
					for (int x = 1; x <= al2; x++)
					{
						if (surf[x][y][z][f])
						{
							iIndex = iIndex + 1;									
							for (int iabCount = 0; iabCount < numsfc2; iabCount++)
							{
								if (Math.abs((int) (treeMapFromConfig.configTreeMapX1 - sfc_ab[iabCount][Constants.sfc_ab_x])) 
										== ( (x - 1) % treeMapFromConfig.configTreeMapCentralWidth))
								{
									if (Math.abs((int) (treeMapFromConfig.configTreeMapY1 - sfc_ab[iabCount][Constants.sfc_ab_y])) 
											== ( (y - 1) % treeMapFromConfig.configTreeMapCentralLength))
									{
										// KN match these up by the grid instead now
										ind_ab[iIndex] = iabCount;
										// goto 329;
										//iabCount = numsfc2;
										found = true;
										break;
									}
								}
							}
							if (found)
							{
								found = false;
							}
							else
							{
								System.out.println("an i did not find an iab,i=" + " " + iIndex);
								System.exit(1);
								// 329 continue
							}

						}
					}
				}
			}
		}
		return ind_ab;
	}
	
	//return A, B, D, R, lambdaav, gam, denom, sfc_ab, tlayer
	public HashMap initSubstrateTemperatures(int numsfc2, double[][] sfc_ab, double Tintw, double Tints, int numlayers, double[][] sfc,
			double[] thick, double[] lambda_sfc, int numlayersMinus2, double[] Tsfc,
			int numlayersMinus1, double IntCond)
	{
		HashMap substrateReturnValues = new HashMap();
		

		
		double[] A = new double[numlayers];
		double[] B = new double[numlayers];
		double[] D = new double[numlayers];
		double[] R = new double[numlayers];
		double[] lambdaav = new double[numlayers];
		double[] gam = new double[numlayers];
		double[] tlayer = new double[numlayers];
		double[] denom = new double[numlayers];
		
		//  INTITIAL SUBSTRATE TEMPERATURE PROFILES SUCH THAT
		// Gin=Gout for each layer (i.e. a nonlinear initial T profile)
		for (int iabCount = 0; iabCount < numsfc2; iabCount++)
		{
			int iIndex3 = (int) sfc_ab[iabCount][Constants.sfc_ab_i];

			//  implicit initial T profile assuming Gin=Gout for each
			//  layer, based on the input Tsfc and the input Tint/Tg

			//  roofs and walls
			double Tint = Tintw;
			//  streets
			if (Math.abs(sfc[iIndex3][Constants.sfc_surface_type] - 2.) < 0.5)
			{
				Tint = Tints;
			}

			//  first calculate the thermal conductivities between layer centers by adding
			//  thermal conductivities (or resistivities) in series

			for (int k = 0; k < numlayers; k++)
			{
				lambdaav[k] = sfc_ab[iabCount][k + numlayers + 5];
				thick[k] = sfc_ab[iabCount][k + 3 * numlayers + 5];
			}

			//  surface matrix values:
			double lambd_o_thick = lambdaav[TUFreg3D.ONE] / (thick[TUFreg3D.ONE] + thick[TUFreg3D.TWO]);
			A[TUFreg3D.ONE] = 0.;
			B[TUFreg3D.ONE] = 2. * ((lambd_o_thick) + lambda_sfc[iabCount] / thick[TUFreg3D.ONE]);
			D[TUFreg3D.ONE] = -2. * lambd_o_thick;
			R[TUFreg3D.ONE] = Tsfc[iabCount] * lambda_sfc[iabCount] / thick[TUFreg3D.ONE] * 2.;

			//  interior matrix values:
			for (int k = 1; k < numlayersMinus1; k++)
			{
				lambd_o_thick = lambdaav[k - 1] / (thick[k - 1] + thick[k]);
				double lambd_o_thick2 = lambdaav[k] / (thick[k] + thick[k + 1]);
				A[k] = -2. * lambd_o_thick;
				B[k] = 2. * (lambd_o_thick + lambd_o_thick2);
				D[k] = -2. * lambd_o_thick2;
				R[k] = 0.;
			}

			//  values for conduction between innermost layer and inner air:
			lambd_o_thick = lambdaav[numlayersMinus2] / (thick[numlayersMinus2] + thick[numlayersMinus1]);
			A[numlayersMinus1] = -2. * lambd_o_thick;
			B[numlayersMinus1] = 2. * (lambd_o_thick + lambdaav[numlayersMinus1] / thick[numlayersMinus1] * IntCond);
			D[numlayersMinus1] = 0.;
			R[numlayersMinus1] = 2. * lambdaav[numlayersMinus1] * Tint / thick[numlayersMinus1] * IntCond;

			//  TRIDIAGONAL MATRIX SOLUTION FROM JACOBSON, p.
			// 166
			gam[TUFreg3D.ONE] = -D[TUFreg3D.ONE] / B[TUFreg3D.ONE];
			tlayer[TUFreg3D.ONE] = R[TUFreg3D.ONE] / B[TUFreg3D.ONE];

			for (int k = 1; k < numlayers; k++)
			{
				denom[k] = B[k] + A[k] * gam[k - 1];
				tlayer[k] = (R[k] - A[k] * tlayer[k - 1]) / denom[k];
				gam[k] = -D[k] / denom[k];
			}

			// do k=numlayers-1,1,-1
			for (int k = numlayers-2; k >= 0;)
			{
				tlayer[k] = tlayer[k] + gam[k] * tlayer[k + 1];
				k--;
			}

			for (int k = 0; k < numlayers; k++)
			{
				sfc_ab[iabCount][k + 5] = tlayer[k];
			}

		}
		
		substrateReturnValues.put("A", A);
		substrateReturnValues.put("B", B);
		substrateReturnValues.put("D", D);
		substrateReturnValues.put("R", R);
		substrateReturnValues.put("lambdaav", lambdaav);
		substrateReturnValues.put("gam", gam);
		substrateReturnValues.put("denom", denom);
		substrateReturnValues.put("tlayer", tlayer);
		substrateReturnValues.put("sfc_ab", sfc_ab);
		return substrateReturnValues;
	}
	
	public HashMap<String,Double> energyBalance(double httc, int iabCount, int iIndex10, int sixPlusThreeTimesNumlayers, int fivePlusNumlayers, int numlayersMinus1,
			double[] Tsfc, double[][] sfc, double[] refltl, double[] tots, double[] reflts, double[] lambda_sfc,
			double[] lambdaavr, double[] thickr, double[][] sfc_ab, double[] totl,
			double Rnet, double Tconv, double Tintw,
			double httcR, double Tsfc_R, double Trad_R, double Rnet_R, double Kdn_R, double Kup_R, double Ldn_R, double Lup_R,
			double Qh_R, double Qg_R, double Qanthro, double Qac)
	{
		httcR = httcR + httc;
		Tsfc_R = Tsfc_R + Tsfc[iabCount];
		Trad_R = Trad_R + Math.pow(((1. / TUFreg3D.sigma) * (sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4) + refltl[iabCount])), (0.25));
		Rnet_R = Rnet_R + Rnet - sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
		Kdn_R = Kdn_R + tots[iabCount];
		Kup_R = Kup_R + reflts[iabCount];
		Ldn_R = Ldn_R + totl[iabCount];
		Lup_R = Lup_R + refltl[iabCount] + sfc[iIndex10][Constants.sfc_emiss] * TUFreg3D.sigma * Math.pow(Tsfc[iabCount], 4);
		Qh_R = Qh_R + httc * (Tsfc[iabCount] - Tconv);
		Qg_R = Qg_R + lambda_sfc[iabCount] * (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2. / sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
		Qanthro = Qanthro + Math.max(0., (Tintw - sfc_ab[iabCount][fivePlusNumlayers]) * lambdaavr[numlayersMinus1] * 2. / thickr[numlayersMinus1]);
		Qac = Qac + Math.max(0., (sfc_ab[iabCount][fivePlusNumlayers] - Tintw) * lambdaavr[numlayersMinus1] * 2. / thickr[numlayersMinus1]);
		
		HashMap<String,Double> energyBalanceReturn = new HashMap<String,Double>();
		energyBalanceReturn.put("httcR",httcR);
		energyBalanceReturn.put("Tsfc_R",Tsfc_R);
		energyBalanceReturn.put("Trad_R",Trad_R);
		energyBalanceReturn.put("Rnet_R",Rnet_R);
		energyBalanceReturn.put("Kdn_R",Kdn_R);
		energyBalanceReturn.put("Kup_R",Kup_R);
		energyBalanceReturn.put("Ldn_R",Ldn_R);
		energyBalanceReturn.put("Lup_R",Lup_R);
		energyBalanceReturn.put("Qh_R",Qh_R);
		energyBalanceReturn.put("Qh_R",Qh_R);
		energyBalanceReturn.put("Qg_R",Qg_R);
		energyBalanceReturn.put("Qanthro",Qanthro);
		energyBalanceReturn.put("Qac",Qac);
		return energyBalanceReturn;
		
	}
	
//	public void test()
//	{
//		httcT = httcT + httc;
//		Trad_T = Trad_T + Math.pow(((1. / sigma) * (sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4) + refltl[iabCount])),(0.25));
//		Rnet_T = Rnet_T + Rnet - sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4);
//		Kdn_T = Kdn_T + tots[iabCount];
//		Kup_T = Kup_T + reflts[iabCount];
//		Ldn_T = Ldn_T + totl[iabCount];
//		Lup_T = Lup_T + refltl[iabCount]+ sfc[iIndex10][Constants.sfc_emiss] * sigma * Math.pow(Tsfc[iabCount], 4);
//		Qh_T = Qh_T + httc * (Tsfc[iabCount] - Tconv);
//		Qg_T = Qg_T + lambda_sfc[iabCount] * (Tsfc[iabCount] - sfc_ab[iabCount][Constants.sfc_ab_layer_temp]) * 2./ sfc_ab[iabCount][sixPlusThreeTimesNumlayers];
//		Qdeep = Qdeep + (sfc_ab[iabCount][fivePlusNumlayers] - Tints) * lambdaavs[numlayersMinus1] * 2. / thicks[numlayersMinus1];
//	}

}
