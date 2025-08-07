package VTUF3D;

import VTUF3D.Utilities.Common;

public class Output 
{
	Common common = new Common();

	public void outputFacets(boolean facet_out, int bh, int aw2, int al2, double[][] sfc, boolean[][][][] surf, int[] ind_ab, 
			double[] Tsfc, double[] Trad, double[] reflts, double[] absbs, double timeis, OverallConfiguration overall  )
	{
		int jab;
	//  write out intra-facet (patch) surface temperatures
		if (facet_out)
		{
			int iIndex15 = 0;
			for (int f = 0; f < 5; f++)
			{
				for (int z = 0; z < bh; z++)
				{
					for (int y = 0; y < aw2; y++)
					{
						for (int x = 0; x < al2; x++)
						{
							if (surf[x][y][z][f])
							{
								iIndex15 = iIndex15 + 1;
								jab = ind_ab[iIndex15];
								if (sfc[iIndex15][Constants.sfc_in_array] > 1.5)
								{
									overall.writeOutput(Constants.TsfcSolarSVF_Patch_yd,
											timeis + " " + f + " " + z + " " + y + " " + x + " "
													+ (1. - sfc[iIndex15][Constants.sfc_evf]) + " "
													+ (Tsfc[jab] - 273.15) + " "
													+ (Trad[jab] - 273.15) + " " + absbs[jab]
													+ " " + reflts[jab]);
								}
							}
						}
					}
				}
			}
		}
		
	}
	
	
	public void outputTbrightTsfc(boolean sum_out, int time_out, String ydwrite, String lpwrite, String bhblwrite, String latwrite2,
			String strorwrite, OverallConfiguration overall, int numsfc2, int bh, int bl, int sw, int sw2, int bw, int al2,
			int aw2, double lpactual, double xlat, double stror, double patchlen, double ralt, int yd,
			boolean[][][][] surf, int[] ind_ab, double[] Tsfc, double[] Trad, double[][] sfc)
	{
		String Tsfc_yd_outFile;
		String Tbright_yd_outFile;
		if (sum_out)
		{

			if (time_out < 1000.)
			{
				String time1 = common.padLeft( time_out, 3, '0') ;
				if (time_out == 0)
				{
					time1 = "000";
				}
				Tsfc_yd_outFile = "Tsfc_yd" + ydwrite + "_lp" + lpwrite + "_bhbl" + bhblwrite
						+ "_lat" + latwrite2 + "_stror" + strorwrite + "_tim0" + time1 + ".out";
				Tbright_yd_outFile = "Tbright_yd" + ydwrite + "_lp" + lpwrite + "_bhbl"
						+ bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite + "_tim0"
						+ time1 + ".out";
			}
			else if (time_out < 10000)
			{

				String time2 = common.padLeft( time_out, 4, '0') ;
				Tsfc_yd_outFile = "Tsfc_yd" + ydwrite + "_lp" + lpwrite + "_bhbl" + bhblwrite
						+ "_lat" + latwrite2 + "_stror" + strorwrite + "_tim" + time2 + ".out";
				Tbright_yd_outFile = "Tbright_yd" + ydwrite + "_lp" + lpwrite + "_bhbl"
						+ bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite + "_tim" + time2 + ".out";

			}
			else
			{
				if (time_out < 100000)
				{

					String time3 = common.padLeft( time_out, 5, '0') ;
					Tsfc_yd_outFile = "Tsfc_yd" + ydwrite + "_lp" + lpwrite + "_bhbl"
							+ bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite + "_tim" + time3 + ".out";
					Tbright_yd_outFile = "Tbright_yd" + ydwrite + "_lp" + lpwrite + "_bhbl"
							+ bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite + "_tim" + time3 + ".out";

				}
				else
				{
					Tsfc_yd_outFile = "";
					Tbright_yd_outFile = "";
					System.out.println("coded to only write output up to hour 999");

					System.exit(1);
				}
			}

			//  metadata at the top of output files
			overall.writeOutput(Tsfc_yd_outFile, numsfc2 + " " + lpactual + " " + xlat + " " + stror);	
			overall.writeOutput(Tbright_yd_outFile, numsfc2 + " " + lpactual + " " + xlat + " " + stror);				
			overall.writeOutput(Tsfc_yd_outFile, bh + " " + bl + " " + bw + " " + sw + " " + sw2);				
			overall.writeOutput(Tbright_yd_outFile, bh + " " + bl + " " + bw + " " + sw + " " + sw2);				
			overall.writeOutput(Tsfc_yd_outFile, al2 + " " + aw2 + " " + patchlen + " " + yd + " " + ralt);						
			overall.writeOutput(Tbright_yd_outFile, al2 + " " + aw2 + " " + patchlen + " " + yd + " " + ralt);				

			int iIndex13 = 0;

			for (int f = 0; f < 5; f++)
			{
				for (int z = 0; z < bh; z++)
				{
					for (int y = 0; y < aw2; y++)
					{
						for (int x = 0; x < al2; x++)
						{
							if (surf[x][y][z][f])
							{
								iIndex13 = iIndex13 + 1;
								int jab = ind_ab[iIndex13];
								if (sfc[iIndex13][Constants.sfc_in_array] > 1.5)
								{
									overall.writeOutput(Tsfc_yd_outFile, "" + Tsfc[jab]);
					
									overall.writeOutput(Tbright_yd_outFile, "" + Trad[jab]);
				
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void outputTimeAverages(OverallConfiguration overall, double lpactual, int bh, int bl, int bw, double hwactual, double xlat, double stror,
			int yd, double timeis, double outpt_tm, double amodTime, int counter2, double Kuptot_avg, double Luptot_avg,
			double Rntot_avg, double Qhtot_avg, double Qgtot_avg, double Qanthro_avg, double Qac_avg, double Qdeep_avg, 
			double Qtau_avg, double TR_avg, double TT_avg, double TN_avg, double TS_avg, double TE_avg, double TW_avg)
	{
		overall.writeOutput(Constants.energybalancetsfctimeaverage_out,
				lpactual 
				+ "\t" + common.roundToDecimals((2 * bh) / (1.0*bl + bw) , 3)
				+ "\t" + common.roundToDecimals(hwactual , 3)
				+ "\t" + common.roundToDecimals( xlat , 3)
				+ "\t" + common.roundToDecimals(stror , 3)
				+ "\t" +common.roundToDecimals( yd + (int) ((timeis - outpt_tm / 2.) / 24.) , 3)
				+ "\t" + common.roundToDecimals(((timeis - outpt_tm / 2.) % 24.) , 3)
				+ "\t" + common.roundToDecimals((timeis - outpt_tm / 2.), 3)
				+ "\t" + common.roundToDecimals(amodTime , 3)
				+ "\t" + common.roundToDecimals(timeis , 3)
				+ "\t" + common.roundToDecimals((Kuptot_avg / (1.0*counter2)), 3)
				+ "\t" + common.roundToDecimals((Luptot_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((Rntot_avg / (1.0*counter2)), 3)
				+ "\t" + common.roundToDecimals((Qhtot_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((Qgtot_avg / (1.0*counter2)), 3)
				+ "\t" + common.roundToDecimals((Qanthro_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((Qac_avg / (1.0*counter2)), 3)
				+ "\t" + common.roundToDecimals((Qdeep_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((Qtau_avg / (1.0*counter2)), 3)
				+ "\t" + common.roundToDecimals((TR_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((TT_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((TN_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((TS_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((TE_avg / (1.0*counter2)) , 3)
				+ "\t" + common.roundToDecimals((TW_avg / (1.0*counter2)), 3));
	}
	
	public void outputEnergyBalanceOverallOut(OverallConfiguration overall, double lpactual, int bh, int bl, int bw, double hwactual, double xlat, double stror,
			double yd_actual, double amodTime, double timeis, double Rnet_tot, double Qh_tot, double Qh_abovezH, double Aplan, double Qg_tot,
			double ustar, double Qhtop, double Qhcan, double Bcan, double Acan, double wstar, double lambdapR, double Rnet_R,
			int numroof2, double Qg_R, double zH, double zd, double z0, double Fm, double lambdaf, double patchlen, double Ccan, double Kdir,
			double Kdif, double Kup, double Ldn, double Lup, double Kdir_Calc, double Kdif_Calc, double Kup_R, double Lup_R, double az,
			double zen, double Kdir_NoAtm, double Kdn_grid, double Qe_tot,
			double Qh_R, double Qh_T, double Rnet_T, double Qg_T, double Rnet_N, int numstreet2, int numNwall2, int numSwall2,
			int numEwall2, int numWwall2, double Qh_N, double Qg_N, double Rnet_S, double Qh_S, double Qg_S, double Rnet_E, double Qh_E,
			double Qg_E, double Rnet_W, double Qh_W, double Qg_W, int numwall2,
			double Kdn_S, double Kup_S, double Ldn_S, double Lup_S,
			double Kdn_E, double Kup_E, double Ldn_E, double Lup_E,
			double Kdn_N, double Kup_N, double Ldn_N, double Lup_N,
			double Kdn_W, double Kup_W, double Ldn_W, double Lup_W,
			double Kdn_R, double Kdn_T, double Ldn_R, double Kup_T,
			double Ldn_T, double Lup_T,
			double Tsfc_cplt, double Tsfc_bird,
			double Tsfc_R, double Tsfc_T, double Tsfc_N, double Tsfc_S, double Tsfc_E, double Tsfc_W, double Tcan, double Ta, double Tintw, 
			double httcR, double httcT, double httcW, double Trad_R, double Trad_T, double Trad_N, double Trad_S, double Trad_E, double Trad_W,
			double TTsun, double TTsh, double TNsun, double TNsh, double TSsun, double TSsh, double TEsun, double TEsh, double TWsun, double TWsh, 
			int numTsun, int numTsh, int numNsun, int numNsh, int numSsun, int numSsh, int numEsun, int numEsh, int numWsun, int numWsh)
	{
		//  WRITE OUTPUT
		overall.writeOutput(Constants.EnergyBalanceOverallOut, 
				common.roundToDecimals(lpactual , 3) + "\t" + 
				common.roundToDecimals((2. * bh) / (1.0*bl + bw) , 3) + "\t" 
				+ common.roundToDecimals(hwactual , 3) + "\t" 
				+ common.roundToDecimals(xlat , 3) + "\t" 
				+ common.roundToDecimals(stror , 3) + "\t"
				+ common.roundToDecimals(yd_actual , 3) + "\t" 
				+ common.roundToDecimals(amodTime , 3) + "\t" 
				+ common.roundToDecimals(timeis , 3) + "\t" 
				+ common.roundToDecimals(Rnet_tot / Aplan , 3) + "\t"
				+ common.roundToDecimals(Qh_tot / Aplan , 3) + "\t" 
				+ common.roundToDecimals(Qh_abovezH / Aplan + Qhtop * (1. - lambdapR) , 3) + "\t"
				+ common.roundToDecimals(Qg_tot / Aplan , 3) + "\t" 
				+ common.roundToDecimals(Qg_tot / Aplan + (Qhcan - Qhtop) * (1. - lambdapR), 3) + "\t" 
				+ common.roundToDecimals((Rnet_tot / Aplan - lambdapR * Rnet_R / (1.0*numroof2)) / (1. - lambdapR), 3) + "\t" 
				+ common.roundToDecimals(Qhtop , 3) + "\t" 
				+ common.roundToDecimals(Qhcan , 3) + "\t"
				+ common.roundToDecimals((Qg_tot / Aplan - lambdapR * Qg_R / (1.0*numroof2)) / (1. - lambdapR) + (Qhcan - Qhtop) , 3) + "\t"
				+ common.roundToDecimals(ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) * Math.exp(-2. * lambdaf / (1. - lambdapR) / 4.), 3) + "\t" 
				+ common.roundToDecimals(ustar / TUFreg3D.vK * Math.log((zH - zd) / z0) / Math.sqrt(Fm) , 3) + "\t" 
				+ common.roundToDecimals(Acan + Bcan * Math.exp(Ccan * patchlen / 2.) , 3) + "\t" 
				+ common.roundToDecimals(wstar , 3) + "\t" 
				+ common.roundToDecimals(Kdir + Kdif , 3) + "\t"
				+ common.roundToDecimals(Kup , 3) + "\t" + 
				common.roundToDecimals(Ldn , 3) + "\t" + 
				common.roundToDecimals(Lup , 3) + "\t" + 
				common.roundToDecimals(Kdir_Calc , 3) + "\t" + 
				common.roundToDecimals(Kdif_Calc , 3) + "\t" + 
				common.roundToDecimals(Kdir , 3) + "\t" + 
				common.roundToDecimals(Kdif , 3) + "\t" + 
				common.roundToDecimals((Kup - lambdapR * Kup_R / (1.0*numroof2)) / (1. - lambdapR)	, 3) + "\t" + 
				common.roundToDecimals((Lup - lambdapR * Lup_R / (1.0*numroof2)) / (1. - lambdapR) , 3) + "\t" + 
				common.roundToDecimals(az , 3) + "\t"
				+ common.roundToDecimals(zen , 3) + "\t" + 
				common.roundToDecimals(Math.max(Kdir_NoAtm, 0.) , 3) + "\t" + 
				common.roundToDecimals(Kdn_grid , 3) + "\t" + 
				common.roundToDecimals(Qe_tot / Aplan, 3)
				);
		
		
		
		overall.writeOutput(Constants.energybalancefacets_out, lpactual 
				+ "\t" + common.roundToDecimals((2 * bh) / (1.0*bl + bw)  , 3)
				+ "\t" + common.roundToDecimals(hwactual  , 3)
				+ "\t" + common.roundToDecimals(xlat  , 3)
				+ "\t" + common.roundToDecimals(stror  , 3)
				+ "\t" + common.roundToDecimals(yd_actual  , 3)
				+ "\t" + common.roundToDecimals(amodTime  , 3)
				+ "\t" + common.roundToDecimals(timeis  , 3)
				+ "\t" + common.roundToDecimals(Rnet_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Qh_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Qg_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Rnet_T / (1.0*numstreet2) , 3)
				+ "\t" + common.roundToDecimals(Qh_T / (1.0*numstreet2)  , 3)
				+ "\t" + common.roundToDecimals(Qg_T / (1.0*numstreet2)  , 3)
				+ "\t" + common.roundToDecimals(Rnet_N / (1.0*numNwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qh_N / (1.0*numNwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qg_N / (1.0*numNwall2) , 3)
				+ "\t" + common.roundToDecimals(Rnet_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qh_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qg_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Rnet_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qh_E / (1.0*numEwall2) , 3)
				+ "\t" + common.roundToDecimals(Qg_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Rnet_W / (1.0*numWwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qh_W / (1.0*numWwall2)  , 3)
				+ "\t" + common.roundToDecimals(Qg_W / (1.0*numWwall2) , 3));

		overall.writeOutput(Constants.RadiationBalanceFacetsOut, lpactual 
				+ "\t" + common.roundToDecimals((2 * bh) / (1.0*bl + bw) , 3) 
				+ "\t" + common.roundToDecimals(hwactual  , 3)
				+ "\t" + common.roundToDecimals(xlat  , 3)
				+ "\t" + common.roundToDecimals(stror  , 3)
				+ "\t" + common.roundToDecimals(yd_actual  , 3)
				+ "\t" + common.roundToDecimals(amodTime  , 3)
				+ "\t" + common.roundToDecimals(timeis  , 3)
				+ "\t" + common.roundToDecimals(Kdn_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kup_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Ldn_S / (1.0*numSwall2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_S / (1.0*numSwall2) , 3)
				+ "\t" + common.roundToDecimals(Kdn_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kup_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Ldn_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_E / (1.0*numEwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kdn_N / (1.0*numNwall2) , 3)
				+ "\t" + common.roundToDecimals(Kup_N / (1.0*numNwall2)  , 3)
				+ "\t" + common.roundToDecimals(Ldn_N / (1.0*numNwall2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_N / (1.0*numNwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kdn_W / (1.0*numWwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kup_W / (1.0*numWwall2) , 3)
				+ "\t" + common.roundToDecimals(Ldn_W / (1.0*numWwall2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_W / (1.0*numWwall2)  , 3)
				+ "\t" + common.roundToDecimals(Kdn_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Kup_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Ldn_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_R / (1.0*numroof2)  , 3)
				+ "\t" + common.roundToDecimals(Kdn_T / (1.0*numstreet2)  , 3)
				+ "\t" + common.roundToDecimals(Kup_T / (1.0*numstreet2) , 3)
				+ "\t" + common.roundToDecimals(Ldn_T / (1.0*numstreet2)  , 3)
				+ "\t" + common.roundToDecimals(Lup_T / (1.0*numstreet2) , 3));
		

		
		overall.writeOutput(Constants.tsfcfacets_out, lpactual 
				+ "\t" + common.roundToDecimals(((2 * bh) / (1.0*bl + bw)) , 3)
				+ "\t" + common.roundToDecimals(hwactual  , 3)
				+ "\t" + common.roundToDecimals(xlat  , 3)
				+ "\t" + common.roundToDecimals(stror  , 3)
				+ "\t" + common.roundToDecimals(yd_actual  , 3)
				+ "\t" + common.roundToDecimals(amodTime , 3)
				+ "\t" + common.roundToDecimals(timeis  , 3)
				+ "\t" + common.roundToDecimals((Tsfc_cplt / (numroof2 + numwall2 + numstreet2) - 273.15) , 3)
				+ "\t" + common.roundToDecimals((Tsfc_bird / Aplan - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Tsfc_R / (1.0*numroof2) - 273.15) , 3)
				+ "\t" + common.roundToDecimals((Tsfc_T / (1.0*numstreet2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Tsfc_N / (1.0*numNwall2) - 273.15) , 3)
				+ "\t" + common.roundToDecimals((Tsfc_S / (1.0*numSwall2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Tsfc_E / (1.0*numEwall2) - 273.15) , 3)
				+ "\t" + common.roundToDecimals((Tsfc_W / (1.0*numWwall2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Tcan - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Ta - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Tintw - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((httcR / (1.0*numroof2))  , 3)
				+ "\t" + common.roundToDecimals((httcT / (1.0*numstreet2))  , 3)
				+ "\t" + common.roundToDecimals((httcW / (1.0*numwall2))  , 3)
				+ "\t" + common.roundToDecimals((Trad_R / (1.0*numroof2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Trad_T / (1.0*numstreet2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Trad_N / (1.0*numNwall2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Trad_S / (1.0*numSwall2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Trad_E / (1.0*numEwall2) - 273.15)  , 3)
				+ "\t" + common.roundToDecimals((Trad_W / (1.0*numWwall2) - 273.15) , 3));
		

		
		overall.writeOutput(Constants.tsfcfacetssunshade_out,
				lpactual + "\t" + common.roundToDecimals(((2 * bh) / (1.0*bl + bw))   , 3)
				+ "\t" + common.roundToDecimals(hwactual   , 3)
				+ "\t" + common.roundToDecimals(xlat   , 3)
				+ "\t" + common.roundToDecimals(stror   , 3)
				+ "\t" + common.roundToDecimals(yd_actual   , 3)
				+ "\t" + common.roundToDecimals(amodTime   , 3)
				+ "\t" + common.roundToDecimals(timeis   , 3)
				+ "\t" + common.roundToDecimals((TTsun / Math.max(0.01, (1.0*numTsun)) - 273.15)  , 3) 
				+ "\t" + common.roundToDecimals((TTsh / Math.max(0.01, (1.0*numTsh)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TNsun / Math.max(0.01, (1.0*numNsun)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TNsh / Math.max(0.01, (1.0*numNsh)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TSsun / Math.max(0.01, (1.0*numSsun)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TSsh / Math.max(0.01, (1.0*numSsh)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TEsun / Math.max(0.01, (1.0*numEsun)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TEsh / Math.max(0.01, (1.0*numEsh)) - 273.15)   , 3)
				+ "\t" + common.roundToDecimals((TWsun / Math.max(0.01, (1.0*numWsun)) - 273.15)  , 3) 
				+ "\t" + common.roundToDecimals((TWsh / Math.max(0.01, (1.0*numWsh)) - 273.15)  , 3));
	}
	
	
}
