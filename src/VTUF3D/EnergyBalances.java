package VTUF3D;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.TreeMap;

import VTUF3D.Utilities.Common;
import VTUF3D.Utilities.Namelist;



public class EnergyBalances 
{
	
	Common common = new Common();
	

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
	public TreeMap initMainArray(int a1, int a2, int b1, int b2, int bh, int aw2, int al2, boolean[][][][] surf, int iIndex12, int iab,
			double[][] sfc, double[][] sfc_ab, int[] sfc_ab_map_x, int[] sfc_ab_map_y, int[] sfc_ab_map_z, int[] sfc_ab_map_f,
			double albs, double emiss, double albr, double albw, double emisr, double emisw, int[][] treeXYTreeMap, int numlayers, double Tsfcs,
			double[] thicks, double[] lambdaavs, double[] htcaps, double[] thickr, double[] lambdaavr, double[] htcapr,
			double[] thickw, double[] lambdaavw, double[] htcapw, double[] lambdas, double[] lambdar, double[] lambdaw, double Tsfcw, double Tsfcr,
			int numstreet2, int numroof2, int numwall2, int numNwall2, int numSwall2, int numEwall2, int numWwall2,
			double[] lambda_sfc, double[] Tsfc)
	{
		TreeMap returnValues = new TreeMap();
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
	

	//return sfc
	//return mend
	//return vffile, vfppos, vfipos
	public TreeMap viewFactors(int bh, int aw2, int al2, boolean[][][][] surf, double[][] sfc, int vfcalc, int[] mend, int numsfc2,
			int a1, int a2, int b1, int b2, double[][] sfc_ab, int[] vffile, int[] vfppos, int[] vfipos, VTUF3DUtil util,
			boolean[][][] surf_shade, int maxbh, int[] ind_ab, String filename)
	{
		TreeMap vfReturnValues;
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
		
		vfReturnValues = new TreeMap();
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
		
	public TreeMap readDataFromDisk(String filename)
	{	
		TreeMap tree = null;
		
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
			
			tree = (TreeMap) objectInputStream.readObject();
			
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
	
	public void saveDataToDisk(TreeMap treemap, String filename)
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
	
	public double[] calcLdn(double Ktotfrc, double[] Ldnfrc, int restartedRunStartTimestep, double ea, double Ta, double sigma, int cloudtype, boolean calcKdn, boolean calcLdn)
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
		return new double[] {Ldn_fact,Ldn};
	}
	
	public TreeMap getLayerDepths( int numlayers, ParametersDat parameters, int numlayersMinusOne, double Intresist)
	{
		TreeMap layerDepthsReturnValues = new TreeMap();
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
	
	public double[] calculateCanyonAirspace(int aw, int al, int a1, int a2, int b1, int b2, double zH, double patchlen, int[][] bldhti)
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
		return new double[] { lambdapR, canyair};
	}
	
	public TreeMap declareDataStructures(int al2, int aw2, int bh, int al, int aw, double zref, int[][] bldhti, int[][] veghti)
	{
		TreeMap declareStructuresReturn = new TreeMap();
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
	
	public int[] calcAboveZh(int numsfc_ab, double patchlen, double zH, double[][] sfc, double[][] sfc_ab)
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
		return new int[] {numcany,numabovezH};
	}
	
	//return surf_shade, veg_shade, surf, numsfc, numsfc_ab
	public TreeMap convertHeightsToShading(int bh, int aw2, int al2, int al, int aw, int a1, int a2, int b1, int b2, int bl, int bw,
			int[][] bldht, int[][] veght, boolean [][][] surf_shade, boolean [][][] veg_shade,
			boolean [][][][] surf, MaespaConfigTreeMapState treeMapFromConfig)
	{
		TreeMap convertHeightsReturn = new TreeMap();
		
		


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
	public TreeMap createDomainBarrayCube(int al, int aw, int bw, int bl, int sw, int sw2, int bh, int[][] treeXYMap,
			HashMap<String, HashMap<String, Namelist>> namelists, MaespaConfigTreeMapState treeMapFromConfig,
			double patchlen, double zref, double zH)
	{
		TreeMap createDomainBarrayCubeReturnValues = new TreeMap();
		

		
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
	public TreeMap initSubstrateTemperatures(int numsfc2, double[][] sfc_ab, double Tintw, double Tints, int numlayers, double[][] sfc,
			double[] thick, double[] lambda_sfc, int numlayersMinus2, double[] Tsfc,
			int numlayersMinus1, double IntCond)
	{
		TreeMap substrateReturnValues = new TreeMap();
		

		
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
	
	public double[] energyBalance(double httc, int iabCount, int iIndex10, int sixPlusThreeTimesNumlayers, int fivePlusNumlayers, int numlayersMinus1,
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
		
		return new double[] {httcR,Tsfc_R,Trad_R,Rnet_R,Kdn_R,Kup_R,Ldn_R,Lup_R,Qh_R,Qh_R,Qg_R,Qanthro,Qac};
		
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
