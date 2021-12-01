package VTUF3D;

import java.util.ArrayList;
import java.util.HashMap;

import VTUF3D.Utilities.Common;

public class OutputResults
{

	//to shift one indexed arrays to zero
	public final static int ONE = 0;
	public final static int TWO = 1;
	public final static int THREE = 2;
	public final static int FOUR = 3;
	public final static int FIVE = 4;
	public final static int SIX = 5;
	
	public final static String linefeed = "\n";
	
	Common common = new Common();
	
	
	//TODO copying this to same method to cut out lots of stuff and convert to cached string buffers
//	public void outputMatlab(boolean matlab_out, int time_out, boolean first_write, boolean writeTsfc, boolean writeKl, 
//			boolean writeKabs, boolean writeKrefl, boolean writeLabs, boolean writeLrefl, boolean writeLdown, boolean writeTmrt, 
//			boolean writeUtci, boolean writeEnergyBalances,  String lpwrite, String bhblwrite, String ydwrite, String latwrite2, 
//			String strorwrite,  boolean newlp, boolean newbhbl, int numsfc, int i, int bh, int aw2, int al2, 
//			boolean[][][][] surf,  double[][] sfc, OverallConfiguration overall,  int[] ind_ab, double[] Tsfc, 
//			double[] tots, double[] totl, double[] reflts, double[] refltl, double[] absbs,  double timeis, 
//			double Ldn, int[][] treeXYMap, int[] sfc_ab_map_x, int[] sfc_ab_map_y, double[] absbl, 
//			int diffShadingValueUsed, int tempTimeis,  double Tcan, double ea,  double Ua, 
//			double zen,  double Acan, double Bcan, double Ccan, double patchlen, double[] currentRnet, 
//			double[] currentQh, double[] currentQe, double[] currentQg, UTCI utci, 
//			HashMap<String, ArrayList<MaespaDataResults>>maespaDataArray)
//	{
//		String formattedTime;
////		String time2;
////		String time3;
//		double gridUtci;
//		double[][] vertex;
//		int[][] face;
//		double maespaTcanForTmrtCalc;
//		double maespasoiltForTmrtCalc; 
//		double ldownForTmrtCalc; 
//		double lupForTmrtCalc; 
//		double taForUtciCalc;
//		double solarForTmrtCalc;
//		double gridTmrt;
//		double xpinc;
//		double ypinc;
//		int numvertex;
////		int timefrc_index_for_ldown;
//		int jab;
//			
//		// ! postprocessing for Matlab visualization...
//		if (matlab_out)
//		{
//			String vertices_toMatlab_outFile = null;
//			String faces_toMatlab_outFile = null;
//			String toMatlab_Tsfc_yd_outFile = null;
//			String ToMatlabKLTotOutFile = null;
//			String toMatlab_Kabs_yd_outFile = null;
//			String toMatlab_Krefl_yd_outFile = null;
//			String toMatlab_Labs_yd_outFile = null;
//			String toMatlab_Lrefl_yd_outFile = null;
//			String toMatlab_Ldown_yd_outFile = null; 
//			String toMatlab_Tmrt_yd_outFile = null;
//			String toMatlab_Utci_yd_outFile = null; 
//			String toMatlab_EnergyBalancesFile = null;
//			
//			
////			if (time_out < 1000.)
////			{
//				formattedTime =  common.padLeft( time_out, 6, '0') ;
////			}
//
//			
//			
//			
//			
////			if (time_out < 1000.)
////			{
////				time1 =  common.padLeft( time_out, 3, '0') ;
////				
//
//				if (first_write)
//				{
//					vertices_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
//							+ "_vertices_toMatlab.out";
//					faces_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
//							+ "_faces_toMatlab.out";
//				}
//				if (writeTsfc)
//				{
//					toMatlab_Tsfc_yd_outFile = "toMatlab_Tsfc_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeKl)
//				{
//					ToMatlabKLTotOutFile = "toMatlab_KL_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeKabs)
//				{
//					toMatlab_Kabs_yd_outFile = "toMatlab_Kabs_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeKrefl)
//				{
//					toMatlab_Krefl_yd_outFile = "toMatlab_Krefl_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeLabs)
//				{
//					toMatlab_Labs_yd_outFile = "toMatlab_Labs_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeLrefl)
//				{
//					toMatlab_Lrefl_yd_outFile = "toMatlab_Lrefl_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeLdown)
//				{
//					toMatlab_Ldown_yd_outFile = "toMatlab_Ldown_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeTmrt)
//				{
//					toMatlab_Tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeUtci)
//				{
//					toMatlab_Utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
//				}
//				if (writeEnergyBalances)
//				{
//					toMatlab_EnergyBalancesFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
//							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//							+ strorwrite + "_tim" + formattedTime + ".out";
//				}
////			}
////			else if (time_out < 10000)
////			{
////				time2 = common.padLeft( time_out, 4, '0');
////				if (first_write)
////				{
////					vertices_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
////							+ "_vertices_toMatlab.out";
////					faces_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
////							+ "_faces_toMatlab.out";
////				}
////
////				if (writeTsfc)
////				{
////					toMatlab_Tsfc_yd_outFile = "toMatlab_Tsfc_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeKl)
////				{
////					ToMatlabKLTotOutFile = "toMatlab_KL_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeKabs)
////				{
////					toMatlab_Kabs_yd_outFile = "toMatlab_Kabs_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeKrefl)
////				{
////					toMatlab_Krefl_yd_outFile = "toMatlab_Krefl_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeLabs)
////				{
////					toMatlab_Labs_yd_outFile = "toMatlab_Labs_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeLrefl)
////				{
////					toMatlab_Lrefl_yd_outFile = "toMatlab_Lrefl_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeLdown)
////				{
////					toMatlab_Ldown_yd_outFile = "toMatlab_Ldown_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeTmrt)
////				{
////					toMatlab_Tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeUtci)
////				{
////					toMatlab_Utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp" + lpwrite
////							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
////							+ "_tim" + time2 + ".out";
////				}
////				if (writeEnergyBalances)
////				{
////					toMatlab_EnergyBalancesFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
////							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////							+ strorwrite + "_tim" + time2 + ".out";
////				}
////			}
////			else
////			{
////
////				if (time_out < 100000)
////				{
////					time3 = common.padLeft( time_out, 5, '0');
////					if (first_write)
////					{
////						vertices_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
////								+ "_vertices_toMatlab.out";
////						faces_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
////								+ "_faces_toMatlab.out";
////					}
////
////					if (writeTsfc)
////					{
////						toMatlab_Tsfc_yd_outFile = "toMatlab_Tsfc_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeKl)
////					{
////						ToMatlabKLTotOutFile = "toMatlab_KL_yd" + ydwrite + "_lp" + lpwrite
////								+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeKabs)
////					{
////						toMatlab_Kabs_yd_outFile = "toMatlab_Kabs_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeKrefl)
////					{
////						toMatlab_Krefl_yd_outFile = "toMatlab_Krefl_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeLabs)
////					{
////						toMatlab_Labs_yd_outFile = "toMatlab_Labs_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeLrefl)
////					{
////						toMatlab_Lrefl_yd_outFile = "toMatlab_Lrefl_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeLdown)
////					{
////						toMatlab_Ldown_yd_outFile = "toMatlab_Ldown_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeTmrt)
////					{
////						toMatlab_Tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeUtci)
////					{
////						toMatlab_Utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp"
////								+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
////								+ strorwrite + "_tim" + time3 + ".out";
////					}
////					if (writeEnergyBalances)
////					{
////						toMatlab_EnergyBalancesFile = "toMatlab_EnergyBalances_yd" + ydwrite
////								+ "_lp" + lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2
////								+ "_stror" + strorwrite + "_tim" + time3 + ".out";
////					}
////				}
////				else
////				{
////					System.out.println("coded to only write output up to hour 999");
////					System.exit(1);
////				}
////			}
//
//			if (first_write && (newlp || newbhbl))
//			{
//				vertex = new double[numsfc * 2][3];
//				face = new int[numsfc][4];
//		
//				newlp = false;
//				newbhbl = false;
//				
//				overall.deleteOldOutput(vertices_toMatlab_outFile);
//				overall.deleteOldOutput(faces_toMatlab_outFile);
//				
//			}
//			else
//			{
//				vertex = null;
//				face = null;
//			}
//
//			numvertex = 0;
//			i = -1;
//
//
//			for (int f = 1; f <= 5; f++)
//			{
////				for (int z = 1; z < bh; z++)
//				for (int z = 0; z < bh; z++)
//				{
////					for (int y = 1; y < aw2; y++)
//					for (int y = 0; y <= aw2; y++)
//					{
////						for (int x = 1; x < al2; x++)
//						for (int x = 0; x <= al2; x++)
//						{
////System.out.println("    "+i + " " + x + " " + y + " " + z + " " + f + " "  + numvertex);
//							if (surf[x][y][z][f])
//							{
//								double XT = 0, YT = 0, ZT = 0;
//								i = i + 1;
//								if (first_write)
//								{
//									for (int iv = 1; iv <= 4; iv++)
//									{
////System.out.println(i + " " + x + " " + y + " " + z + " " + f + " " + iv + " " + numvertex);
//										xpinc = -0.5;
//										ypinc = -0.5;
//										if ((iv >= 2) && (iv <= 3))
//										{
//											xpinc = 0.5;
//										}
//										if ((iv >= 1) && (iv <= 2))
//										{
//											ypinc = 0.5;
//										}
//										if (f == 5)
//										{
//											XT = sfc[i][Constants.sfc_x];
//											YT = sfc[i][Constants.sfc_y] + xpinc;
//											ZT = sfc[i][Constants.sfc_z] + ypinc;
//										}
//										else if (f == 3)
//										{
//											XT = sfc[i][Constants.sfc_x];
//											YT = sfc[i][Constants.sfc_y] + xpinc;
//											ZT = sfc[i][Constants.sfc_z] + ypinc;
//										}
//										else if (f == 4)
//										{
//											XT = sfc[i][Constants.sfc_x] + xpinc;
//											YT = sfc[i][Constants.sfc_y];
//											ZT = sfc[i][Constants.sfc_z] + ypinc;
//										}
//										else if (f == 2)
//										{
//											XT = sfc[i][Constants.sfc_x] + xpinc;
//											YT = sfc[i][Constants.sfc_y];
//											ZT = sfc[i][Constants.sfc_z] + ypinc;
//										}
//										else if (f == 1)
//										{
//											XT = sfc[i][Constants.sfc_x] + xpinc;
//											YT = sfc[i][Constants.sfc_y] + ypinc;
//											ZT = sfc[i][Constants.sfc_z];
//										}
//										else
//										{
//											System.out.println("PROBLEM with wall orientation");
//
//										}
//										boolean goto48 = false;
//										// ! test if the  current  vertex  already exists
//										for (int k = 0; k <= numvertex+1; k++)
//										{
////System.out.println("k " + k + " " + vertex[k][ONE] + " " + vertex[k][TWO] + " " + vertex[k][THREE] + 
////		" " + XT + " " + YT + " " + ZT);
////											if (Math.abs(XT - vertex[k][ONE]) < 0.01)
//											if (XT == vertex[k][ONE])
//											{
////												if (Math.abs(YT - vertex[k][TWO]) < 0.01)
//												if (YT == vertex[k][TWO])	
//												{
////													if (Math.abs(ZT - vertex[k][THREE]) < 0.01)
//													if (ZT == vertex[k][THREE]) 
//													{
//														face[i][iv-1] = k;
//														// goto 48;
//														goto48 = true;
//
//														break; // replace the  goto  48
//													}
//												}
//											}
//										}
//										if (!goto48)
//										{
//											// ! the current vertex is a  new one - add  it
//											numvertex = numvertex + 1;
//											vertex[numvertex][ONE] = XT;
//											vertex[numvertex][TWO] = YT;
//											vertex[numvertex][THREE] = ZT;
//											overall.writeOutput(vertices_toMatlab_outFile, XT + " " + YT + " " + ZT );
//											// write(vertices_toMatlab_out,*)XT+" "+YT+" "+ZT;
//											face[i][iv-1] = numvertex;
//										}
//					
//										// 48 continue
//									}
//									String facesStr = face[i][0] + " " + face[i][1] + " "
//											+ face[i][2] + " " + face[i][3];
//									// overall.writeOutput(faces_toMatlab_outFile,
//									// (face(i,iv),iv=1,4));
//									overall.writeOutput(faces_toMatlab_outFile, facesStr);
//									// write(faces_toMatlab_out,*)(face(i,iv),iv=1,4);
//								}
//								jab = ind_ab[i];
//
//								if (writeTsfc)
//								{
//									overall.writeOutput(toMatlab_Tsfc_yd_outFile,
//											"" + (Tsfc[jab] - 273.15) );
//
//								}
//								if (writeKl)
//								{
//									overall.writeOutput(ToMatlabKLTotOutFile,
//											tots[jab] + " " + totl[jab] 
//													+ " " + reflts[jab]
//													+ " " + (refltl[jab] 
//															+ (sfc[jab][Constants.sfc_emiss]
//															* Constants.sigma
//															* Math.pow(Tsfc[jab], 4))) );
//
//
//								}
//
//								if (writeKabs)
//								{
//									overall.writeOutput(toMatlab_Kabs_yd_outFile,
//											"" + absbs[jab]);
//
//								}
//								if (writeKrefl)
//								{
//									overall.writeOutput(toMatlab_Krefl_yd_outFile,
//											"" + reflts[jab]);
//
//								}
//								if (writeLabs)
//								{
//									overall.writeOutput(toMatlab_Labs_yd_outFile,
//											"" + absbl[jab]);
//
//								}
//								if (writeLrefl)
//								{
//									overall.writeOutput(toMatlab_Lrefl_yd_outFile,
//											"" + refltl[jab]);
//
//								}
//
////								timefrc_index_for_ldown = (int) (timeis * 2) + 1;
////								if (timefrc_index_for_ldown < 1)
////								{
////									timefrc_index_for_ldown = 1;
////								}
////								if (writeLdown)
////								{
////									overall.writeOutput(toMatlab_Ldown_yd_outFile,
////											"" + Ldnfrc[timefrc_index_for_ldown]);
////
////								}
//								if (writeLdown)
//								{
//									overall.writeOutput(toMatlab_Ldown_yd_outFile,
//									"" + Ldn);
//								}
//								// !! because tmrt is needed for utci, no option for only utci
//								if (writeTmrt)
//								{
//									{
//										// !! this version uses grid values of ldown
//										// ! use forcing ldown instead
//
//										// ! if this is  a tree grid, use tcan and soil temp for ldown and lup
//										if (treeXYMap[sfc_ab_map_x[jab]][sfc_ab_map_y[jab]] != 0)
//										{
//
//											String key = treeXYMap[sfc_ab_map_x[jab]][sfc_ab_map_y[jab]]
//													+ "_" + diffShadingValueUsed;
//											maespaTcanForTmrtCalc = maespaDataArray.get(key)
//													.get(tempTimeis).getTCAN() + 273.15;
//											maespasoiltForTmrtCalc = maespaDataArray.get(key)
//													.get(tempTimeis).getSoilt1() + 273.15;
//
//
//											ldownForTmrtCalc = sfc[jab][Constants.sfc_emiss]
//													* Constants.sigma
//													* Math.pow(maespaTcanForTmrtCalc, 4);
//											lupForTmrtCalc = sfc[jab][Constants.sfc_emiss]
//													* Constants.sigma
//													* Math.pow(maespasoiltForTmrtCalc, 4);
////											if (Ldnfrc[timefrc_index_for_ldown] > 0)
//											if (Ldn > 0)	
//											{
////												ldownForTmrtCalc = Ldnfrc[timefrc_index_for_ldown];
//												ldownForTmrtCalc = Ldn;
//											}
//											taForUtciCalc = maespasoiltForTmrtCalc - 273.15;
//											solarForTmrtCalc = absbs[jab];
//
//										}
////										else if (Ldnfrc[timefrc_index_for_ldown] > 0)
//										else if (Ldn > 0)
//										{
////											ldownForTmrtCalc = Ldnfrc[timefrc_index_for_ldown];
//											ldownForTmrtCalc = Ldn;
//											lupForTmrtCalc = refltl[jab]
//													+ sfc[jab][Constants.sfc_emiss]
//															* Constants.sigma
//															* Math.pow(Tsfc[jab], 4);
//											taForUtciCalc = Tcan - 273.15;
//											solarForTmrtCalc = absbs[jab] + reflts[jab];
//										}
//										else
//										{
//
//											ldownForTmrtCalc = totl[jab];
//											lupForTmrtCalc = refltl[jab]
//													+ sfc[jab][Constants.sfc_emiss]
//															* Constants.sigma
//															* Math.pow(Tsfc[jab], 4);
//											taForUtciCalc = Tcan - 273.15;
//											solarForTmrtCalc = absbs[jab] + reflts[jab];
//										}
//									}
//
//									gridTmrt = utci.getTmrtForGrid(taForUtciCalc,
////											eafrc[timefrc_index_for_ldown],
//											ea,
////											Uafrc[timefrc_index_for_ldown], solarForTmrtCalc,
//											Ua, solarForTmrtCalc,
//											zen, Tsfc[jab] - 273.15, ldownForTmrtCalc,
//											lupForTmrtCalc);
//
//									if (gridTmrt > 100 || gridTmrt < -20)
//									{
//										System.out.println(
//												"gridTmrt,Tafrc[timefrc_index_for_ldown],eafrc[timefrc_index_for_ldown],Uafrc[timefrc_index_for_ldown],absbs[jab]+reflts[jab], zen,Tsfc[jab]-273.15"
//														+ " " + gridTmrt + " " + (Tcan - 273.15)
////														+ " " + eafrc[timefrc_index_for_ldown]
//														+ " " + ea
////														+ " " + Uafrc[timefrc_index_for_ldown]
//														+ " " + Ua
//														+ " " + absbs[jab] + reflts[jab] + " "
//														+ zen + " " + (Tsfc[jab] - 273.15) + " "
//														+ totl[jab] + " "
//														+ (refltl[jab]
//																+ sfc[jab][Constants.sfc_emiss]
//																		* Constants.sigma
//																		* Math.pow(Tsfc[jab],
//																				4)));
//	
//										System.exit(1);
//									}
//									gridUtci = utci.getUTCIForGrid(taForUtciCalc,
//											Acan + Bcan * Math.exp(Ccan * patchlen / 2.),
////											eafrc[timefrc_index_for_ldown], gridTmrt);	
//											ea, gridTmrt);	
//																										
//									String toMatlab_tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
//									
//									overall.writeOutput(toMatlab_tmrt_yd_outFile,
//											"" + gridTmrt);
//										
//									String toMatlab_utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
//									overall.writeOutput(toMatlab_utci_yd_outFile,
//											"" + gridUtci);
//
//								}
//
//								if (writeEnergyBalances)
//								{
//									String toMatlab_EnergyBalances_yd_outFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
//									
//
//									overall.writeOutput(toMatlab_EnergyBalances_yd_outFile,
//											currentRnet[jab] + " " + currentQh[jab] + " "
//													+ currentQe[jab] + " " + currentQg[jab]);
//								}
//							}
//						}
//
//					}
//				}
//			}
//			// ! whether or not to write Matlab files
//		}
//	}
	
	
	
	
	public void outputMatlab(boolean matlab_out, int time_out, boolean first_write, boolean writeTsfc, boolean writeKl, 
			boolean writeKabs, boolean writeKrefl, boolean writeLabs, boolean writeLrefl, boolean writeLdown, boolean writeTmrt, 
			boolean writeUtci, boolean writeEnergyBalances,  String lpwrite, String bhblwrite, String ydwrite, String latwrite2, 
			String strorwrite,  boolean newlp, boolean newbhbl, int numsfc, int i, int bh, int aw2, int al2, 
			boolean[][][][] surf,  double[][] sfc, OverallConfiguration overall,  int[] ind_ab, double[] Tsfc, 
			double[] tots, double[] totl, double[] reflts, double[] refltl, double[] absbs,  double timeis, 
			double Ldn, int[][] treeXYMap, int[] sfc_ab_map_x, int[] sfc_ab_map_y, double[] absbl, 
			int diffShadingValueUsed, int tempTimeis,  double Tcan, double ea,  double Ua, 
			double zen,  double Acan, double Bcan, double Ccan, double patchlen, double[] currentRnet, 
			double[] currentQh, double[] currentQe, double[] currentQg, UTCI utci, 
			HashMap<String, ArrayList<MaespaDataResults>>maespaDataArray)
	{
		String formattedTime;
		double gridUtci;
		double[][] vertex;
		int[][] face;
		double maespaTcanForTmrtCalc;
		double maespasoiltForTmrtCalc; 
		double ldownForTmrtCalc; 
		double lupForTmrtCalc; 
		double taForUtciCalc;
		double solarForTmrtCalc;
		double gridTmrt;
		double xpinc;
		double ypinc;
		int numvertex;
		int jab;
			
		// ! postprocessing for Matlab visualization...
		if (matlab_out)
		{
			String vertices_toMatlab_outFile = null;
			String faces_toMatlab_outFile = null;
			String toMatlab_Tsfc_yd_outFile = null; //TODO
			String ToMatlabKLTotOutFile = null;
			String toMatlab_Kabs_yd_outFile = null;
			String toMatlab_Krefl_yd_outFile = null;
			String toMatlab_Labs_yd_outFile = null;
			String toMatlab_Lrefl_yd_outFile = null;
			String toMatlab_Ldown_yd_outFile = null; 
//			String toMatlab_Tmrt_yd_outFile = null;
//			String toMatlab_Utci_yd_outFile = null; 
//			String toMatlab_EnergyBalancesFile = null;
			
			String toMatlab_tmrt_yd_outFile = null;
			String toMatlab_utci_yd_outFile = null;
			String toMatlab_EnergyBalances_yd_outFile = null;
			
			StringBuilder vertices_toMatlab_outFileSB = new StringBuilder();
			StringBuilder faces_toMatlab_outFileSB = new StringBuilder();
			StringBuilder toMatlab_Tsfc_yd_outFileSB = new StringBuilder();
			StringBuilder ToMatlabKLTotOutFileSB = new StringBuilder();
			StringBuilder toMatlab_Kabs_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_Krefl_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_Labs_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_Lrefl_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_Ldown_yd_outFileSB = new StringBuilder(); 
//			StringBuilder toMatlab_Tmrt_yd_outFileSB = new StringBuilder();
//			StringBuilder toMatlab_Utci_yd_outFileSB = new StringBuilder(); 
//			StringBuilder toMatlab_EnergyBalancesFileSB = new StringBuilder();
			
			StringBuilder toMatlab_tmrt_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_utci_yd_outFileSB = new StringBuilder();
			StringBuilder toMatlab_EnergyBalances_yd_outFileSB = new StringBuilder();
			

			formattedTime =  common.padLeft( time_out, 6, '0') ;

				if (first_write)
				{
					vertices_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
							+ "_vertices_toMatlab.out";
					faces_toMatlab_outFile = "lp" + lpwrite + "_bhbl" + bhblwrite
							+ "_faces_toMatlab.out";
				}
				if (writeTsfc)
				{
					toMatlab_Tsfc_yd_outFile = "toMatlab_Tsfc_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeKl)
				{
					ToMatlabKLTotOutFile = "toMatlab_KL_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeKabs)
				{
					toMatlab_Kabs_yd_outFile = "toMatlab_Kabs_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeKrefl)
				{
					toMatlab_Krefl_yd_outFile = "toMatlab_Krefl_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeLabs)
				{
					toMatlab_Labs_yd_outFile = "toMatlab_Labs_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeLrefl)
				{
					toMatlab_Lrefl_yd_outFile = "toMatlab_Lrefl_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeLdown)
				{
					toMatlab_Ldown_yd_outFile = "toMatlab_Ldown_yd" + ydwrite + "_lp" + lpwrite
							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
							+ "_tim0" + formattedTime + ".out";
				}
				if (writeTmrt)
				{
//					toMatlab_Tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
					
					toMatlab_tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp"
							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
							+ strorwrite + "_tim" + formattedTime + ".out";					
				}
				if (writeUtci)
				{
//					toMatlab_Utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp" + lpwrite
//							+ "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror" + strorwrite
//							+ "_tim0" + formattedTime + ".out";
					
					toMatlab_utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp"
							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
							+ strorwrite + "_tim" + formattedTime + ".out";
				}
				if (writeEnergyBalances)
				{
//					toMatlab_EnergyBalancesFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
//							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//							+ strorwrite + "_tim" + formattedTime + ".out";
					
					toMatlab_EnergyBalances_yd_outFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
							+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
							+ strorwrite + "_tim" + formattedTime + ".out";
				}
	

			if (first_write && (newlp || newbhbl))
			{
				vertex = new double[numsfc * 2][3];
				face = new int[numsfc][4];
		
				newlp = false;
				newbhbl = false;
				
				overall.deleteOldOutput(vertices_toMatlab_outFile);
				overall.deleteOldOutput(faces_toMatlab_outFile);
				
			}
			else
			{
				vertex = null;
				face = null;
			}

			numvertex = 0;
			i = -1;


			for (int f = 1; f <= 5; f++)
			{
				for (int z = 0; z < bh; z++)
				{
					for (int y = 0; y <= aw2; y++)
					{
						for (int x = 0; x <= al2; x++)
						{
							if (surf[x][y][z][f])
							{
								double XT = 0, YT = 0, ZT = 0;
								i = i + 1;
								if (first_write)
								{
									for (int iv = 1; iv <= 4; iv++)
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
										if (f == 5)
										{
											XT = sfc[i][Constants.sfc_x];
											YT = sfc[i][Constants.sfc_y] + xpinc;
											ZT = sfc[i][Constants.sfc_z] + ypinc;
										}
										else if (f == 3)
										{
											XT = sfc[i][Constants.sfc_x];
											YT = sfc[i][Constants.sfc_y] + xpinc;
											ZT = sfc[i][Constants.sfc_z] + ypinc;
										}
										else if (f == 4)
										{
											XT = sfc[i][Constants.sfc_x] + xpinc;
											YT = sfc[i][Constants.sfc_y];
											ZT = sfc[i][Constants.sfc_z] + ypinc;
										}
										else if (f == 2)
										{
											XT = sfc[i][Constants.sfc_x] + xpinc;
											YT = sfc[i][Constants.sfc_y];
											ZT = sfc[i][Constants.sfc_z] + ypinc;
										}
										else if (f == 1)
										{
											XT = sfc[i][Constants.sfc_x] + xpinc;
											YT = sfc[i][Constants.sfc_y] + ypinc;
											ZT = sfc[i][Constants.sfc_z];
										}
										else
										{
											System.out.println("PROBLEM with wall orientation");

										}
										boolean goto48 = false;
										// ! test if the  current  vertex  already exists
										for (int k = 0; k <= numvertex+1; k++)
										{
											if (XT == vertex[k][ONE])
											{
												if (YT == vertex[k][TWO])	
												{
													if (ZT == vertex[k][THREE]) 
													{
														face[i][iv-1] = k;
														// goto 48;
														goto48 = true;

														break; // replace the  goto  48
													}
												}
											}
										}
										if (!goto48)
										{
											// ! the current vertex is a  new one - add  it
											numvertex = numvertex + 1;
											vertex[numvertex][ONE] = XT;
											vertex[numvertex][TWO] = YT;
											vertex[numvertex][THREE] = ZT;
											vertices_toMatlab_outFileSB.append(XT + " " + YT + " " + ZT + linefeed);
//											overall.writeOutput(vertices_toMatlab_outFile, XT + " " + YT + " " + ZT );
											face[i][iv-1] = numvertex;
										}
					
									}
									String facesStr = face[i][0] + " " + face[i][1] + " "
											+ face[i][2] + " " + face[i][3];
									faces_toMatlab_outFileSB.append(facesStr + linefeed);
//									overall.writeOutput(faces_toMatlab_outFile, facesStr);
								}
								jab = ind_ab[i];

								if (writeTsfc)
								{
									toMatlab_Tsfc_yd_outFileSB.append( "" + (Tsfc[jab] - 273.15) + linefeed );
//									overall.writeOutput(toMatlab_Tsfc_yd_outFile, "" + (Tsfc[jab] - 273.15) );

								}
								if (writeKl)
								{
									ToMatlabKLTotOutFileSB.append(tots[jab] + " " + totl[jab] 
											+ " " + reflts[jab]
											+ " " + (refltl[jab] 
													+ (sfc[jab][Constants.sfc_emiss]
													* Constants.sigma
													* Math.pow(Tsfc[jab], 4))) + linefeed);
//									overall.writeOutput(ToMatlabKLTotOutFile,
//											tots[jab] + " " + totl[jab] 
//													+ " " + reflts[jab]
//													+ " " + (refltl[jab] 
//															+ (sfc[jab][Constants.sfc_emiss]
//															* Constants.sigma
//															* Math.pow(Tsfc[jab], 4))) );


								}

								if (writeKabs)
								{
									toMatlab_Kabs_yd_outFileSB.append(absbs[jab] + linefeed);
//									overall.writeOutput(toMatlab_Kabs_yd_outFile, "" + absbs[jab]);

								}
								if (writeKrefl)
								{
									toMatlab_Krefl_yd_outFileSB.append(reflts[jab] + linefeed);
//									overall.writeOutput(toMatlab_Krefl_yd_outFile, "" + reflts[jab]);

								}
								if (writeLabs)
								{
									toMatlab_Labs_yd_outFileSB.append(absbl[jab] + linefeed);
//									overall.writeOutput(toMatlab_Labs_yd_outFile, "" + absbl[jab]);

								}
								if (writeLrefl)
								{
									toMatlab_Lrefl_yd_outFileSB.append(refltl[jab] + linefeed);
//									overall.writeOutput(toMatlab_Lrefl_yd_outFile, "" + refltl[jab]);

								}

								if (writeLdown)
								{
									toMatlab_Ldown_yd_outFileSB.append(Ldn + linefeed);
//									overall.writeOutput(toMatlab_Ldown_yd_outFile, "" + Ldn);
								}
								// !! because tmrt is needed for utci, no option for only utci
								if (writeTmrt)
								{
									{
										// !! this version uses grid values of ldown
										// ! use forcing ldown instead
										// ! if this is  a tree grid, use tcan and soil temp for ldown and lup
										if (treeXYMap[sfc_ab_map_x[jab]][sfc_ab_map_y[jab]] != 0)
										{

											String key = treeXYMap[sfc_ab_map_x[jab]][sfc_ab_map_y[jab]]
													+ "_" + diffShadingValueUsed;
											maespaTcanForTmrtCalc = maespaDataArray.get(key)
													.get(tempTimeis).getTCAN() + 273.15;
											maespasoiltForTmrtCalc = maespaDataArray.get(key)
													.get(tempTimeis).getSoilt1() + 273.15;


											ldownForTmrtCalc = sfc[jab][Constants.sfc_emiss]
													* Constants.sigma
													* Math.pow(maespaTcanForTmrtCalc, 4);
											lupForTmrtCalc = sfc[jab][Constants.sfc_emiss]
													* Constants.sigma
													* Math.pow(maespasoiltForTmrtCalc, 4);
											if (Ldn > 0)	
											{
												ldownForTmrtCalc = Ldn;
											}
											taForUtciCalc = maespasoiltForTmrtCalc - 273.15;
											solarForTmrtCalc = absbs[jab];

										}
										else if (Ldn > 0)
										{
											ldownForTmrtCalc = Ldn;
											lupForTmrtCalc = refltl[jab]
													+ sfc[jab][Constants.sfc_emiss]
															* Constants.sigma
															* Math.pow(Tsfc[jab], 4);
											taForUtciCalc = Tcan - 273.15;
											solarForTmrtCalc = absbs[jab] + reflts[jab];
										}
										else
										{

											ldownForTmrtCalc = totl[jab];
											lupForTmrtCalc = refltl[jab]
													+ sfc[jab][Constants.sfc_emiss]
															* Constants.sigma
															* Math.pow(Tsfc[jab], 4);
											taForUtciCalc = Tcan - 273.15;
											solarForTmrtCalc = absbs[jab] + reflts[jab];
										}
									}

									gridTmrt = utci.getTmrtForGrid(taForUtciCalc,
											ea,
											Ua, solarForTmrtCalc,
											zen, Tsfc[jab] - 273.15, ldownForTmrtCalc,
											lupForTmrtCalc);

									if (gridTmrt > 100 || gridTmrt < -20)
									{
										System.out.println(
												"gridTmrt,Tafrc[timefrc_index_for_ldown],eafrc[timefrc_index_for_ldown],Uafrc[timefrc_index_for_ldown],absbs[jab]+reflts[jab], zen,Tsfc[jab]-273.15"
														+ " " + gridTmrt + " " + (Tcan - 273.15)
														+ " " + ea
														+ " " + Ua
														+ " " + absbs[jab] + reflts[jab] + " "
														+ zen + " " + (Tsfc[jab] - 273.15) + " "
														+ totl[jab] + " "
														+ (refltl[jab]
																+ sfc[jab][Constants.sfc_emiss]
																		* Constants.sigma
																		* Math.pow(Tsfc[jab],
																				4)));
	
										System.exit(1);
									}
									gridUtci = utci.getUTCIForGrid(taForUtciCalc,
											Acan + Bcan * Math.exp(Ccan * patchlen / 2.),
											ea, gridTmrt);	
																										
//									String toMatlab_tmrt_yd_outFile = "toMatlab_tmrt_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
//									
//									String toMatlab_utci_yd_outFile = "toMatlab_utci_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
//									
//									String toMatlab_EnergyBalances_yd_outFile = "toMatlab_EnergyBalances_yd" + ydwrite + "_lp"
//											+ lpwrite + "_bhbl" + bhblwrite + "_lat" + latwrite2 + "_stror"
//											+ strorwrite + "_tim" + formattedTime + ".out";
									
									toMatlab_tmrt_yd_outFileSB.append(gridTmrt + linefeed);
//									overall.writeOutput(toMatlab_tmrt_yd_outFile, "" + gridTmrt);
									toMatlab_utci_yd_outFileSB.append(gridUtci + linefeed);
//									overall.writeOutput(toMatlab_utci_yd_outFile, "" + gridUtci);

								}

								if (writeEnergyBalances)
								{
									toMatlab_EnergyBalances_yd_outFileSB.append(currentRnet[jab] + " " + currentQh[jab] + " " + currentQe[jab] + " " + currentQg[jab] + linefeed);
//									overall.writeOutput(toMatlab_EnergyBalances_yd_outFile,
//											currentRnet[jab] + " " + currentQh[jab] + " "
//													+ currentQe[jab] + " " + currentQg[jab]);
								}
							}
						}

					}
				}
			}
			// ! whether or not to write Matlab files
			
			if (first_write)
			{
				overall.writeOutput(vertices_toMatlab_outFile, vertices_toMatlab_outFileSB.toString() );
				overall.writeOutput(faces_toMatlab_outFile, faces_toMatlab_outFileSB.toString());
			}
			if (writeTsfc)
			{
				overall.writeOutput(toMatlab_Tsfc_yd_outFile, toMatlab_Tsfc_yd_outFileSB.toString());
			}
			if (writeKl)
			{
				overall.writeOutput(ToMatlabKLTotOutFile,ToMatlabKLTotOutFileSB.toString());
			}
			if (writeKabs)
			{
				overall.writeOutput(toMatlab_Kabs_yd_outFile, toMatlab_Kabs_yd_outFileSB.toString());
			}
			if (writeKrefl)
			{
				overall.writeOutput(toMatlab_Krefl_yd_outFile, toMatlab_Krefl_yd_outFileSB.toString());
			}
			if (writeLabs)
			{
				overall.writeOutput(toMatlab_Labs_yd_outFile, toMatlab_Labs_yd_outFileSB.toString());
			}
			if (writeLrefl)
			{
				overall.writeOutput(toMatlab_Lrefl_yd_outFile, toMatlab_Lrefl_yd_outFileSB.toString());
			}
			if (writeLdown)
			{
				overall.writeOutput(toMatlab_Ldown_yd_outFile, toMatlab_Ldown_yd_outFileSB.toString());
			}
			if (writeTmrt)
			{
				overall.writeOutput(toMatlab_tmrt_yd_outFile, toMatlab_tmrt_yd_outFileSB.toString());
			}
			if (writeUtci)
			{
				overall.writeOutput(toMatlab_utci_yd_outFile, toMatlab_utci_yd_outFileSB.toString());
			}
			if (writeEnergyBalances)
			{
				overall.writeOutput(toMatlab_EnergyBalances_yd_outFile, toMatlab_EnergyBalances_yd_outFileSB.toString());
			}
		}
		
	}
	
	

}
