package VTUF3D;

import java.util.HashMap;

import VTUF3D.Utilities.Namelist;

public class Barray_Cube
{
//	public void barray_cube(int bw,int bl,int sw, int sw2,int al,int aw, int bh, int[][] bldht)
//
//
//	{
//
////	INTEGER, INTENT(IN)                      :: bw
////	INTEGER, INTENT(IN)                      :: bl
////	INTEGER, INTENT(IN)                      :: sw
////	INTEGER, INTENT(IN)                      :: sw2
////	INTEGER, INTENT(IN)                      :: al
////	INTEGER, INTENT(IN OUT)                  :: aw
////	INTEGER, INTENT(IN)                      :: bh
////	INTEGER, INTENT(OUT)                     :: bldht(0:al+1,0:aw+1)
//
//
////	! INITIATE ARRAY (bldht) OF GIVEN SIZE
////	INTEGER :: x,y,xa,xc,ya,yb,xd
////
////	! BW - Building width in y (AW) direction
////	! BL - Building length in x (AL) direction
////	! SW - street width in x directioon
////	! SW2 - street width in y direction
////
////	!call open_r_vector("real.parms")
////	!call wrt_r_complete_vector("bldht", ix=bldht(:,0))
////
////	!    call open_r_file("/home/kerryn/workspace/TUF-3DWater/src/au/edu/monash/ges/tuf3d/test/barray_cube_in.rdat", digits=6)
////	!    call wrt_r_matrix("bldht", ix=bldht)
////	!    call close_r_file()
//
//	int y=1;
//
//	while (true)
//	{
//	//	1  CONTINUE;
//	
//		//! now do the loop for one building
//		int x=1;
//		for (int ya=1;ya<bw;ya++)
//		{
//		  if (y > aw) 
//		  {
//	//		  goto 101;
//			  break;
//		  }
//		  while (true)
//		  {
//	//	  5      CONTINUE;
//		  for (int xa=1;xa<bl;xa++)
//		  {
//		    if (x > al) 
//		    {
//	//	    	goto 51;
//		    	break;
//		    }
//		    bldht[x][y]=bh;
//		    x=x+1;
//		  }
//		  
//		//! now comes a street
//		  for (int xc=1;xc<sw;xc++)
//		  {
//		    if (x > al) 
//		    {
//	//	    	GO TO 51;
//		    	break;
//		    }
//		    bldht[x][y]=0;
//		    x=x+1;
//		  }
//		  
//		//! now the pattern repeats
//	//	  goto 5;
//		  }
//	//	  51      CONTINUE;
//		  
//		  x=1;
//		  y=y+1;
//		}
//	
//		//! now insert the street
//		for (int yb=1;yb<sw2;yb++)
//		{
//		  x=1;
//		  if (y > aw) 
//		  {
//	//		  goto 101;
//			  break;
//		  }
//		  for (int xd=1;xd<al+1;xd++)
//		  {
//		    if (x > al) 
//		    {
//	//	    	EXIT;
//		    	return;
//		    }
//		    bldht[x][y]=0;
//		    x=x+1;
//		  }
//	//	  121        CONTINUE;
//		  y=y+1;
//		}
//	
//	//	goto 1
//	}
//
////	101 CONTINUE
//	//! end the height loop
//
//
//
//	return;
//	}
	
	// commented this out and copied to new version. Too much told commented code to clean up
//	  public static TreeMap<String,int[][]> barray_cube(int BW,int BL,int SW, int SW2,int AL,int AW, int BH, int[][] bldht, int[][] veght, 
//			  TreeMap<String,TreeMap<String,Namelist>> namelists, int[][] treeXYMap,
//			  MaespaConfigTreeMapState treeMapFromConfig)
////	  BARRAY_CUBE(BW,BL,SW,SW2,AL,AW,BH,bldht,veght,treeState)
//	  {
//		  TreeMap<String,int[][]> returnValues = new TreeMap<String,int[][]>(); 
//
////	  USE MaespaConfigStateUtils
////	  use MaespaConfigState
////	  use ReadMaespaConfigs
////
////	        implicit none
//
//
////	  ! INITIATE ARRAY (bldht) OF GIVEN SIZE
////	        int X,Y,xa,xc,ya,yb,xd;
////	        int AL,AW,BH,bldht;
////	        int BW,BL,SW,SW2;
//
////	        int veght;
////	        TYPE(maespaConfigTreeMapState) :: treeState 
//	        int calculatedVegHeight;
////	        !INTEGER, EXTERNAL :: IDATE50
////	        !CHARACTER(10) STARTDATE
//	        
//	        int N1, N2   ;   
////	        int loopCount;
//	        int calculatedBuildingHeight;
//	        
////	        bldht = new int[AL+2][AW+2];
////	        veght = new int[AL+2][AW+2];
//
////	        DIMENSION bldht(0:AL+1,0:AW+1)
////	        DIMENSION veght(0:AL+1,0:AW+1)
//
////	  ! BW - Building width in y (AW) direction
////	  ! BL - Building length in x (AL) direction
////	  ! SW - street width in x directioon
////	  ! SW2 - street width in y direction
//
////	        !STARTDATE = '09/02/12'
////	        !ISTART = IDATE50(STARTDATE)  
////	        calculatedVegHeight = 0
////	        !call readMaespaTreeMapFromConfig(treeState)
//
////	  !       y=1
////	  !
////	  !   1  continue
////	  !
////	  !! now do the loop for one building
////	  !        x=1
////	  !        do 110 ya=1,BW
////	  !         if (y.gt.AW) goto 101     
////	  !   5      continue
////	  !         do 10 xa=1,BL
////	  !          if (x.gt.AL) goto 51
////	  !            bldht(x,y)=bh
////	  !            x=x+1
////	  !  10     continue
////	  !
////	  !! now comes a street
////	  !          do 30 xc=1,SW
////	  !            if (x.gt.AL) goto 51
////	  !            bldht(x,y)=0
////	  !            x=x+1
////	  !  30      continue
////	  !
////	  !! now the pattern repeats
////	  !          goto 5
////	  !  51      continue
////	  !
////	  !          x=1
////	  !          y=y+1
////	  ! 110    continue
////	  !
////	  !! now insert the street
////	  !          do 120 yb=1,sw2
////	  !            x=1
////	  !            if (y.gt.AW) goto 101
////	  !              do 130 xd=1,al+1
////	  !                if (x.gt.AL) goto 121
////	  !                bldht(x,y)=0
////	  !                x=x+1
////	  !  130         continue
////	  !  121        continue
////	  !            y=y+1
////	  !  120     continue
////	  !  
////	  !      goto 1
////	  !            
////	  !  101 continue
////	  ! end the height loop  
//	        
//	        N1=treeMapFromConfig.width-1;
//	        N2=treeMapFromConfig.length-1;
//	        System.out.println("N1 " + N1 + " " + N2);
////	  !      print *,N1,N2
//	        
//	        int count = 0;
//	        for (int I = 1;I<=N1;I++)
//	        {
//	          for (int J = 1; J<= N2;J++)
//	          {
//	        	  count = count + 1;
//	        	  calculatedBuildingHeight=OverallConfiguration.getBuildingHeightFromConfig(I, J, treeMapFromConfig);       
//	              bldht[I][J] = calculatedBuildingHeight;
//	              
////	              !! temp hack, make sure trees are not put on buildings
//	              if (calculatedBuildingHeight==0) 
//	              {      
//	            	  calculatedVegHeight=OverallConfiguration.getVegHeight(namelists, I, J, treeXYMap);
////	            	  calculatedVegHeight=getVegHeightFromConfig(I, J, calculatedVegHeight, treeState);
//	                  veght[I][J] = calculatedVegHeight ;
//	              }
//
//	              
////	              !print *,i,j,calculatedVegHeight,calculatedBuildingHeight
//	          }
//	        }
////	        System.out.println("count " + count);
////	        System.exit(1);
//	    
////	  !  do i=15,19
////	  !      do j=15,19
////	  !          print *,i,j,bldht(i,j)
////	  !      end do
////	  !  end do
//	        
////	  !  do i=15,19
////	  !      do j=15,19
////	  !          print *,i,j,veght(i,j)
////	  !      end do
////	  !  end do      
//	      
//	        
//	        returnValues.put("bldht",bldht);
//	        returnValues.put("veght",veght);
//	        
//	        return returnValues;
//	  }
	  
	  
	  
	  public static HashMap<String,int[][]> barray_cube(int BW,int BL,int SW, int SW2,int AL,int AW, int BH, int[][] bldht, int[][] veght, 
			  HashMap<String,HashMap<String,Namelist>> namelists, int[][] treeXYMap,
			  MaespaConfigTreeMapState treeMapFromConfig)
	  {
		  HashMap<String,int[][]> returnValues = new HashMap<String,int[][]>(); 
		  int calculatedVegHeight;    
		  int N1, N2   ;   
		  int calculatedBuildingHeight;
	        
		  N1=treeMapFromConfig.width-1;
		  N2=treeMapFromConfig.length-1;
		  System.out.println("N1 " + N1 + " " + N2);
	        
		  int count = 0;
		  for (int I = 1;I<=N1;I++)
		  {
			  for (int J = 1; J<= N2;J++)
	          	{
				  count = count + 1;
	        	  calculatedBuildingHeight=OverallConfiguration.getBuildingHeightFromConfig(I, J, treeMapFromConfig);       
	              bldht[I][J] = calculatedBuildingHeight;
	              
//	              !! temp hack, make sure trees are not put on buildings
	              if (calculatedBuildingHeight==0) 
	              {      
	            	  calculatedVegHeight=OverallConfiguration.getVegHeight(namelists, I, J, treeXYMap);
	                  veght[I][J] = calculatedVegHeight ;
	              }
	          }
	        }
	        
	        returnValues.put("bldht",bldht);
	        returnValues.put("veght",veght);
	        
	        return returnValues;
	  }
	  
	  
	
}
