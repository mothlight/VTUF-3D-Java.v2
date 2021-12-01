package VTUF3D;

import java.util.HashMap;

public class VFCalc
{
	VTUF3DUtil util = new VTUF3DUtil();

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub

	}
	
	public void vfcalc(int vfcalc, double[] fx, double[] fy, double[] fz, double[] fxx, double[] fyy, double[] fzz , int numsfc, int numsfc2, 
			int BH, int b2, int a2, int b1, int a1, boolean[][][][] surf, int aw2, int al2, boolean[][][] surf_shade, int[] ind_ab, int maxbh, double[][] sfc_ab,
			int [] vffile, int[] vfipos, int[] vfppos, double[][] sfc, int numvf ,int[] mend, double vf)
	{
		int numfiles;
//		int numvf;
		int i;
//		double[][] sfc_ab;
//		double[][] sfc;
		double[] vf2;
		double[] vf2j;
//		int[] mend;
		double vx,vy,vz;
		String numc,numc2;
//		int [] vffile, vfipos,vfppos;
		int vfinfoDatRECL = 64; //changed from 32
		double vftot;
		int j=0;
		double vxx, vyy, vzz;
		double vfsum;
		double[] dx, vecti, vectj, vns, vtemp1, vtemp2 ;
		double separat;
//		double vf;
		
		double yyy;
		double magvns;
		double xpinc,ypinc;
		
		
		double vftot5 = 0.;
		double[] vf3,vf3j;
//		int xx,yy,zz;
		int vfiend;
		int p=0;
		double x2=0;
		double y2=0;
		double z2=0;
		
		double[][] corner= new double[4][3] , v = new double[4][3];
		double[] vmag= new double[4];
		double[] vangle = new double[4];
		
		dx = new double[3];
		vecti = new double[3];
		vectj = new double[3];
		vns = new double[3];
		vtemp1 = new double[3];
		vtemp2 = new double[3];
		
//		int x,y,z;
		//!------------------------------------------------------------------
		//!  View Factor Calculations (or read in from file)

		      if (vfcalc==0) 
		      {
		    	  //going to always calculate
//
//		//!  must read in vffile[i],sfc[i][Constants.sfc_evf],vfipos[i],mend[i] etc
//		//!  from file if view factors are already calculated and stored
//		//!  in files
//		       open(unit=vfinfoDat,file="vfinfo.dat",access="DIRECT",recl=vfinfoDatRECL);
//		       read(unit=vfinfoDat,rec=1)numfiles,numvf;
//		       for (int iab=0;iab<numsfc2;iab++)
//		       {
//		        i=sfc_ab[iab][Constants.sfc_ab_i];
//		        read(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc[i][Constants.sfc_evf],sfc[i][Constants.sfc_x],sfc[i][sfc_y],sfc[i][sfc_z];
//		       }
//		       read(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
//		       close(vfinfoDat);
		      }
		      else
		      {

		    	  System.out.println("CALCULATING VIEW FACTORS...");
//		    write(6,*)'CALCULATING VIEW FACTORS...'

		    	  vf2 = new double[numsfc*numsfc2];
		    	  vf2j = new double[numsfc*numsfc2];
//		      allocate(vf2(numsfc*numsfc2))
//		      allocate(vf2j(numsfc*numsfc2))


//		      X=0;
//		      Y=0;
//		      Z=0;
//		      numvf=0;

		//! direction vectors
		      for (int k=0;k<5;k++)
		      {
		       fx[k]=0.;
		       fy[k]=0.;
		       fz[k]=0.;
		      }
		       fx[3-1]=0.5;
		       fx[5-1]=-0.5;
		       fy[2-1]=0.5;
		       fy[4-1]=-0.5;
		       fz[1-1]=0.5;

		       for (int k=0;k<5;k++)
		       {
		       fxx[k]=fx[k];
		       fyy[k]=fy[k];
		       fzz[k]=fz[k];
		       }

		      i=0;

		      double fact2=500000.;

		      int n=11;
//		      numc="(i1)"+ (n-10);
//		      open(unit=n,file="vf"+numc,access="DIRECT",recl=vfRECL);
		      int m=1;
		      p=1;
		      int iab=0;
		           
		      mend = new int[numsfc2];
		      for (int k=0;k<numsfc2;k++)
		      {
		    	  mend[k]=0;
		      }

		//! RUN THROUGH ALL ARRAY POSITIONS AND ONLY PERFORM
		//! CALCULATIONS ON POINTS SEEN
		      // for (int f=0;f<5;f++)
		      for (int f=0;f<5;f++)
		      {
		       if (f==1) 
		       {
		       System.out.println("calculating view factors of horizontal patches...");
//		    write(6,*)"calculating view factors of horizontal patches..."
		       }
		       else if (f==2) 
		       {
		       System.out.println("calculating view factors of north-facing patches...");
//		    write(6,*)"calculating view factors of north-facing patches..."
		       }
		       else if (f==3) 
		       {
		       System.out.println("calculating view factors of east-facing patches...");
//		        write(6,*)"calculating view factors of east-facing patches..."
		       }
		       else if (f==4) 
		       {
		       System.out.println("calculating view factors of south-facing patches...");
//		        write(6,*)"calculating view factors of south-facing patches..."
		       }
		       else if (f==5) 
		       {
		       System.out.println("calculating view factors of west-facing patches...");
//		    write(6,*)"calculating view factors of west-facing patches..."
		       }
		       for (int z=0;z<BH;z++)
		       {
		        for (int y=b1;y<b2;y++)
		        {
		         for (int x=a1;x<a2;x++)
		         {
		     
		          if(!surf[x][y][z][f]) 
		          {
//		        	  goto 41;
		        	  continue;
		          }

		           iab=iab+1;
		           i=(int)sfc_ab[iab][Constants.sfc_ab_i];

		//!  patch surface i center:
                vx = x + fx[f];
		        vy = y + fy[f];
		        vz = z + fz[f];

		          vftot=0.;

		          j=0;

		      if (m>fact2)
		      {
		           mend[iab-1]=m-1;
		           m=1;
//		           close(unit=n);
		       n=n+1;
		       if(n<=19) 
		       {
		        numc="(i1)"+ (n-10);
//		        open(unit=n,file="vf"+numc,access="DIRECT",recl=vfRECL);
		       }
		       else if(n>=20&&n<=109) 
		       {
		        numc2="(i2)"+ (n-10);
//		        open(unit=n,file="vf"+numc2,access="DIRECT",recl=vfRECL);
		       }
		       else
		       {
		    	   System.out.println("need to program in more vf files or increase # vfs");
//		        write(6,*)"need to program in more vf files or increase # vfs";
		        System.out.println("each one can hold");
//		        write(6,*)"each one can hold";
//		        stop
		        System.exit(1);
		       }
		      }

		      vffile[iab]=n;
		      vfipos[iab]=m;

		      vfppos[iab]=p;

//		          do ff=1,5
//		           do zz=0,bh
//		            do yy=1,aw2
//		             do xx=1,al2
		             
  		       for (int ff=0;ff<5;ff++)
		       {
		        for (int zz=0;zz<BH;zz++)
		        {
		         for (int yy=0;yy<aw2;yy++)
		         {
		          for (int xx=0;xx<al2;xx++)
		          {

		              if(!surf[xx][yy][zz][ff]) 
		              {
//		            	  goto 81;
		            	  continue;
		              }

		              vfsum=0.0;

		          j=j+1;

		//!  patch surface j center:
		        vxx = xx + fxx[ff];
		        vyy = yy + fyy[ff];
		        vzz = zz + fzz[ff];
	
	            dx[1-1]=Math.abs(vx-vxx);
	            dx[2-1]=Math.abs(vy-vyy);
	            dx[3-1]=Math.abs(vz-vzz);
	            separat=Math.sqrt(Math.pow((dx[1-1]),2)+Math.pow((dx[2-1]),2)+Math.pow((dx[3-1]),2));

		//!  a surface cannot see itself (no concave surfaces, only flat)
		//!  also, ray tracing (function ray) to determine if the 2 sfcs can
		//!  see each other

		      if(i==j||!util.ray(x,y,z,f,xx,yy,zz,ff,surf_shade,fx,fy,fz,fxx,fyy,fzz,al2,aw2,maxbh)) 
		      {
		           vf=0.;
//		           goto 81;
		           continue;
		      }		          
		          else
		          {

		               if(vfcalc==1) 
		               {
		//!  calculation of exact view factors for PLANE PARALLEL facets ONLY
		                vecti[1-1]=(sfc[i][Constants.sfc_x_vector]);
		                vecti[2-1]=(sfc[i][Constants.sfc_y_vector]);
		                vecti[3-1]=(sfc[i][Constants.sfc_z_vector]);
		                vectj[1-1]=(sfc[j][6]);
		                vectj[2-1]=(sfc[j][7]);
		                vectj[3-1]=(sfc[j][8]);

		                HashMap<String,Double> returnValues = Dotpro.dotpro(vecti,vectj,3);
		             	double dp = returnValues.get("dp");
		             	double g = returnValues.get("g");

		                if(Math.abs(dp)<0.0001)
		                {
		//!  perpendicular
		                   double z1=0.;
		                   double y1=0.;
		//!  patch separation distances in the three dimensions:
		                   for (int k=0;k<3;k++)
		                   {
		                    z1=z1+Math.abs((vecti[k])*dx[k]);
		                    y1=y1+Math.abs((vectj[k])*dx[k]);
		//!                    if(double(vecti(k)).eq.0.0.and.double(vectj(k)).eq.
		//!     &                                            0.0) x2=dx(k)
		                   }
		                   x2=dx[1-1]+dx[2-1]+dx[3-1]-z1-y1;
		                  if(x2<0.1) 
		                  {
		//! use F7, the patches are aligned in one dimension
		                	  vf=(util.F7((1.0),(y1-0.5),(1.0),(z1-0.5),(1.0)));
		                  }
		                  else
		                  {
		//! use F9, the patches aren't aligned in any of the three dimensions
		//!  subtract 0.5 or 1 to get distance to patch edge instead of patch center
		                	  vf=(util.F9((1.0),(x2-1.0),(1.0),(y1-0.50),(1.0),(z1-0.5),(1.0)));
		                  }
		                }
		                else
		                {
		//!  parallel
		                  x2=0.;
		//!  patch separation distances in the three dimensions:
		                  for (int k=0;k<3;k++)
		                  {
		                   if(Math.abs((vecti[k]))<0.1) 
		                   {
		                    if(x2==0.0) 
		                    {
		                    	x2=dx[k]+0.1;
		                    }
		                    z2=dx[k];
		                   }
		                  }
		                  x2=x2-0.1;
		                  yyy=dx[1-1]+dx[2-1]+dx[3-1]-x2-z2;

		                  if(z2==0.0||x2==0.0) 
		                  {
		//! use F3, the patches are aligned in one dimension, or pll if they are
		//! directly opposite
		                   vf=(util.pll((1.0),(yyy),(1.0)));
		      if(z2+x2>0.1) 
		    	  {
		    	  vf=(util.F3((1.0),(yyy),(1.0),(Math.max(x2,z2)-1.0),(1.0)));
		    	  }
		                  }
		                  else
		                  {
		//! use F5, the patches aren't aligned in any of the three dimensions
		      vf=(util.F5((1.0),(x2-1.0),(1.0),(yyy),(1.0),(z2-1.0),(1.0)));
		                  }
		                }

		                if(vf>1.0||vf<0.0) 
		                {
		                System.out.println("vfprobexact"+" "+i+" "+j+" "+vf);
//		                 write(6,*)'vfprobexact',i,j,vf
//		                 stop
		                System.exit(1);
		                }

		                vf=Math.abs(vf);
		               }
		               else
		               {
		//! calculate normal vector of cell face (patch) i
		//! to get a positive answer, the progression of corner points around the patch should be CLOCKWISE

		                vns[1-1] = (fx[f]);
		                vns[2-1] = (fy[f]);
		                vns[3-1] = (fz[f]);
		                magvns = Math.sqrt(Math.pow(vns[1-1],2)+Math.pow(vns[2-1],2)+Math.pow(vns[3-1],2))   ;  

		//! normalize the normal vector from point i
		                vns[1-1] = vns[1-1]/magvns;
		                vns[2-1] = vns[2-1]/magvns;
		                vns[3-1] = vns[3-1]/magvns ;           
		//! Find polygon corners for patch j and define the vectors to these vertices
		//! These are contained in the array v(iv,k) with k=1,3 corresponding to x,y,z respectively
		//! loop through the four corner points of this patch     
		                for (int iv=0;iv<4;iv++)
		                {
		                 xpinc=-0.5;
		                 ypinc=-0.5;
		                 if ((iv>=2)&&(iv<=3)) 
		                 {
		                	 xpinc=0.5 ;
		                 }
		                 if ((iv>=1)&&(iv<=2)) 
		                 {
		                	 ypinc=0.5;
		                 }
		//! set corner points                   
		                 if (ff==1)
		                 {
		                  corner[iv][1-1]=(vxx)+(xpinc);
		                  corner[iv][2-1]=(vyy)+(ypinc);
		                  corner[iv][3-1]=(vzz);
		                 }
		         else if (ff==2)
		         {
		                  corner[iv][1-1]=(vxx)-(xpinc);
		                  corner[iv][2-1]=(vyy);
		                  corner[iv][3-1]=(vzz)+(ypinc);
		         }
		         else if (ff==4)
		         {
		                  corner[iv][1-1]=(vxx)+(xpinc);
		                  corner[iv][2-1]=(vyy);
		                  corner[iv][3-1]=(vzz)+(ypinc);
		         }
		         else if (ff==3)
		         {
		                  corner[iv][1-1]=(vxx);
		                  corner[iv][2-1]=(vyy)+(xpinc);
		                  corner[iv][3-1]=(vzz)+(ypinc);
		         }
		         else if (ff==5)
		         {
		                  corner[iv][1-1]=(vxx);
		                  corner[iv][2-1]=(vyy)-(xpinc);
		                  corner[iv][3-1]=(vzz)+(ypinc);
		         }
		         else
		         {
		        	 System.out.println("PROBLEM, ff not properly defined");
//		          write(6,*)"PROBLEM, ff not properly defined";
//		          stop;
		        	 System.exit(1);
		         }

		//! set vector between point i and corner point of j
		                 v[iv][1-1]=corner[iv][1-1]-(vx);
		                 v[iv][2-1]=corner[iv][2-1]-(vy);
		                 v[iv][3-1]=corner[iv][3-1]-(vz);
		                 vmag[iv]=Math.sqrt(Math.pow(v[iv][1-1],2)		                		 
		                		 +Math.pow(v[iv][2-1],2)		                		 
		                		 +Math.pow(v[iv][3-1],2)		                		 
		                		 )   ;                  
		//!                 write(*,*) 'v(iv,1-3) ',v[iv][1-1],v[iv][2-1],v[iv][3-1]
		                }                   


		//! The following section is common for different surfaces.                  
		//! Find angles between the vectors using the dot product rule: cos angle = u dot v / |u||v|
		//! dotproducts are: x1*x2+y1*y2+z1*z2 where x1,y1,z1 and x2,y2,z2 are two vectors

		//! Calculate the cross products between the vectors. This defines a vector normal
		//! to the plane between the two vectors as required by the view factor calculation.
		                for (int iv=0;iv<4;iv++)
		                {
		                  for (int k=0;k<3;k++)
		                  {
		                    vtemp1[k]=v[iv][k];
		                    if (iv<=3) 
		                    {
		                      vtemp2[k]=v[iv+1][k];
		                    }
		                    else
		                    {
		                      vtemp2[k]=v[1-1][k];		                      
		                    }
		                  }

		//! Use the dot product to define the angle between vectors between two adjacent corners of the patch
		                  //call dotpro(vtemp1,vtemp2,3,dp,g)		                  
		                  HashMap<String,Double> dotproreturnValues = Dotpro.dotpro(vtemp1,vtemp2,3);
			             double	dp = dotproreturnValues.get("dp");
			             double	g = dotproreturnValues.get("g");
		                  
		                  vangle[iv]=g ;
		//!                if ((x.eq.164).and.(y.eq.230).and.(z.eq.9)) then
		//!                  write(*,*) 'ic iv vangle(iv) ',ic,iv,vangle(iv)
		//!                endif                  
		//! Now do the cross product
		                  HashMap crossproReturn = Crosspro.crosspro(vtemp1,vtemp2,3);
		                  double[] cp = (double[])crossproReturn.get("cp");
		                  double mcp = (double)crossproReturn.get("mcp");
		                  
		                  if (mcp<=0) 
		                  {
		                	  System.out.println("warning: mcp <=0");
//		                    write(*,*) 'warning: mcp <=0'
		                    System.out.println("x y z "+" " +x+" " +y+" " +z );
//		                    write(*,*) 'x y z ',x,y,z                    
		                  }
		//!                if ((x.eq.164).and.(y.eq.230).and.(z.eq.9)) then
		//!                  write(*,*) 'crossproduct iv ',iv,(cp(k),k=1,3)
		//!                endif                                    
		//! normalize the cross product by the |vtemp1 x vtemp2|
		                  for (int k=0;k<3;k++)
		                  {
		                    cp[k]=cp[k]/mcp;
		                  }
		                                      
		//! Now find the dot product of the vector normal to the plane between the two vectors and the vector
		//! normal to the plane of surface i. 
//		                  call dotpro(cp,vns,3,dp,g) 
		                  dotproreturnValues = Dotpro.dotpro(cp,vns,3) ;
			             	dp = dotproreturnValues.get("dp");
			             	g = dotproreturnValues.get("g");

		//! Here is the view factor definition (as per Ashdown 1994, eqn 5.6; see also Baum et al. 1989, and
		//! Hottel and Sarofim 1967)                   
		                  vfsum=vfsum+dp*vangle[iv];
		                }
		//! Here is the view factor for this patch (can use this to test for limits); this is the "normal"
		//! view factor definition (i.e. for an entire hemisphere).
		//! The direction in which the corner points are processed matters - it yields a positive or
		//! negative number. Convert negatives to positives. 
		                vf=(vfsum)/(2.*Math.PI);

		//! Taking absolute sum is necessary because relative progression around patch is sometimes clockwise (+)
		//! and sometimes counterclockwise (-) depending on relative position of the patches
		//! These could be pre-defined, but probably easier to take absolute sum here.
		                if (vf<0) 
		                {
		                	vf=Math.abs(vf);
		                }

		//!  exact or contour integration view factors 'if'
		               }

		//!  whether or not patch i sees patch j 'if'
		    		          }
		          
		          if (vf>0.)
		          {
//		        	  write(unit=n,rec=m)ind_ab(j),vf;
		               vftot5=vftot5+vf;
		               numvf=numvf+1;
		               vf2[p]=vf;
		               vf2j[p]=ind_ab[j];
		               p=p+1;
		               m=m+1;
		          }

		              vftot=vftot+vf;

//		81           continue
		    		          }
//		             xx=1;
		    		         }
//		            xx=1;
//		            yy=1;
		    		        }
//		           xx=1;
//		           yy=1;
//		           zz=0;
		    	  }

		           sfc[i][Constants.sfc_evf]=vftot;
//		41       continue
		         }
//		         x=a1;
		        }
//		        x=a1;
//		        y=b1;
		       }
//		       x=a1;
//		       y=b1;
//		       z=0;
		      }

		      vfipos[numsfc2+1] = m;
		      vfppos[numsfc2+1] = p;
		      System.out.println("total number of inter-patch view factors = "+" "+numvf);
//		      write(6,*)"total number of inter-patch view factors = ",numvf;

		//!  close files
//		      close(unit=n);
//		      for (int k=n+1-1;k<numfiles+10;k++)
//		      {
//		       close(unit=k);
//		      }
		      numfiles=n-10;

		      vf3 = new double[numvf];
		      vf3j = new double[numvf];
//		      allocate(vf3(numvf));
//		      allocate(vf3j(numvf));


		//! arrays of view factors
		      for (int k=0;k<numvf;k++)
		      {
		       vf3[k]=vf2[k];
		       vf3j[k]=vf2j[k];
		      }


//		      deallocate(vf2);
//		      deallocate(vf2j);


		      if(iab!=numsfc2) 
		      {
		    	  System.out.println("number of surfaces in view factor calculation wrong");
//		       write(6,*)"number of surfaces in view factor calculation wrong";
		       System.out.println("PROB w/ numsfc2: iab, numsfc2 ="+" "+iab+" "+numsfc2);
//		       write(6,*)"PROB w/ numsfc2: iab, numsfc2 =",iab,numsfc2;
//		       stop;
		       System.exit(1);
		      }

		//!  write file so that view factors need not be recomputed      
//		       open(unit=vfinfoDat,file="vfinfo.dat",access="DIRECT",recl=vfinfoDatRECL)
//		       write(unit=vfinfoDat,rec=1)numfiles,numvf
		       for (int iab2=0;iab2<numsfc2;iab2++)
		       {
		        i=(int)sfc_ab[iab2][Constants.sfc_ab_i];
//		        write(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc[i][Constants.sfc_evf],sfc[i][sfc_x],sfc[i][sfc_y],sfc[i][sfc_z];
		       }
//		       write(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
//		       close(vfinfoDat);

		      }

		//!------------------------------------------------------------------

		      if(vfcalc==0) 
		      {


		    	  vf3 = new double[numvf];
		    	  vf3j = new double[numvf];
//		      allocate(vf3(numvf));
//		      allocate(vf3j(numvf));
//		      vf3=0.;
//		      vf3j=0.;


		      p=1;

		//!  open view factor files
		       for (int iab=0;iab<numsfc2;iab++)
		       {
		        vfppos[iab]=p;
		        if(iab==1||vffile[iab]!=vffile[iab-1])
		        {
//		         close(unit=vf1Dat);
		         if(vffile[iab]<20)
		         {
//		          numc="(i1)"+ vffile[iab]-10);
//		          open(unit=vf1Dat,file="vf"+numc,access="DIRECT",recl=8);
		         }
		         else if(vffile[iab]<110)
		         {
//		          numc2="(i2)"+ (vffile[iab]-10);
//		          open(unit=vf1Dat,file="vf"+numc2,access="DIRECT",recl=8);
		         }
		         else if(vffile[iab]<1010)
		         {
//		          numc3="(i3)"+ (vffile[iab]-10);
//		          open(unit=vf1Dat,file="vf"+numc3,access="DIRECT",recl=8);
		         }
		         else
		         {
		        	 System.out.println("TOO MANY VF FILES");
//		          write(6,*)"TOO MANY VF FILES";
//		          stop;
		        	 System.exit(1);
		         }
		        }

		        if(vfipos[iab+1]==1)
		        {
		         vfiend=mend[iab];
		        }
		        else
		        {
		         vfiend=vfipos[iab+1]-1;
		        }

		//! write the view factors into memory
		        for (int q=vfipos[iab];q<vfiend;q++)
		        {
//		         read(unit=vf1Dat,rec=q)j,vf;
		         vf3[p]=vf;
		         vf3j[p]=j;
		         vftot5=vftot5+vf;
		         p=p+1;
		          if(vf>1.0||vf<0.0)
		          {
		          System.out.println("VF PROB");
//		           write(6,*)"VF PROB"
//		           stop
		          System.exit(1);
		          }
		        }

		      }

		       vfppos[numsfc2+1]=p;

//		      close(unit=vf1Dat);

		      }

		      if (numvf!=p-1) 
		      {
		    	  System.out.println("PROBLEM WITH VFs IN MEM"+" "+ (p-1) +" "+numvf);
//		       write(6,*)"PROBLEM WITH VFs IN MEM",p-1,numvf;
		      }
	}

}
