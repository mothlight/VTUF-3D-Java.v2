package VTUF3D;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import VTUF3D.Utilities.BinaryIn;

public class VFInfo
{
//	String rootDirectory = "/home/kerryn/Documents/Work/VTUF-Runs/PrestonBase/PrestonBase8/";
	String rootDirectory = "/home/kerryn/Documents/Work/VTUF-Runs/TestCombinedTreesT1/";
	String file = "vfinfo.dat";
	
	public static void main(String[] args)
	{
		VFInfo vf = new VFInfo();
		vf.test9();

	}
	
	public void test10()
	{
		int width = 4;
		String sep = " ";
//		sep = "\t";
		boolean bigEndian = false;
		String inputFile = rootDirectory + file;
		BinaryIn  in  = new BinaryIn(inputFile);
		
		ArrayList<Character[]> allData = new ArrayList<Character[]>();
		
		
	     while (!in.isEmpty()) 
        {
	    	Character[] data = new Character[width];
			for (int i=0;i<width;i++)
			{
				data[i]=in.readChar();
			}
//			System.out.println(data);
			allData.add(data);
//			long value = in.getLong(data, bigEndian);
			System.out.println(in.getInt(data, bigEndian)  + sep + in.getInt(data, true) + sep + in.getFloat(data, bigEndian) + sep + in.getFloat(data, true));
        }
		

	     //System.out.println(allData.toString());
	}
	
	public void test9()
	{
		int width = 8;
		String sep = " ";
//		sep = "\t";
		boolean bigEndian = false;
		String inputFile = rootDirectory + file;
		BinaryIn  in  = new BinaryIn(inputFile);
		
		ArrayList<Character[]> allData = new ArrayList<Character[]>();
		
		
	     while (!in.isEmpty()) 
        {
	    	Character[] data = new Character[width];
			for (int i=0;i<width;i++)
			{
				data[i]=in.readChar();
			}
//			System.out.println(data);
			allData.add(data);
//			long value = in.getLong(data, bigEndian);
			System.out.println(in.getLong(data, bigEndian)  + sep + in.getLong(data, true) + sep + in.getDouble(data, bigEndian) + sep + in.getDouble(data, true));
        }
		

	     //System.out.println(allData.toString());
	}
	
	public void test8()
	{
		boolean bigEndian = false;
		int numsfc2 = 1000000;
		int byteRead;
		double numfiles;
		double numvf;
		int vffile_iab,vfipos_iab,mend_iab,sfc_i_sfc_evf,sfc_i_sfc_x,sfc_i_sfc_y,sfc_i_sfc_z;
		
		
		String inputFile = rootDirectory + file;
		BinaryIn  in  = new BinaryIn(inputFile);
		
		in.readDouble(bigEndian);
		
		numfiles = in.readDouble(bigEndian);
		numvf = in.readDouble(bigEndian);
		System.out.println(numfiles + " " + numvf);
		
        // read one 8-bit char at a time
        while (!in.isEmpty()) 
        {
            double c = in.readDouble(bigEndian);
            System.out.println(c);
            //out.write(c);
        }
        //out.flush();
	}
	
	public void readVF()
	{
		String inputFile = rootDirectory + file;

		try (InputStream inputStream = new FileInputStream(inputFile);)
		{


			int numsfc2 = 1000000;
			int byteRead;
			int numfiles;
			int numvf;
			int vffile_iab,vfipos_iab,mend_iab,sfc_i_sfc_evf,sfc_i_sfc_x,sfc_i_sfc_y,sfc_i_sfc_z;
			
			numfiles = inputStream.read();
			numvf = inputStream.read();
			System.out.println(numfiles + " " + numvf);
			
			for (int iab=0;iab<numsfc2;iab++)
			{
//				read(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc(i,sfc_evf),sfc(i,sfc_x),sfc(i,sfc_y),sfc(i,sfc_z);
				
//				vffile[iab],vfipos[iab],mend[iab],sfc(i,sfc_evf),sfc(i,sfc_x),sfc(i,sfc_y),sfc(i,sfc_z);
				vffile_iab=inputStream.read();
				if (vffile_iab == -1)
				{
					System.out.println("Mmmbop1");
					break;
				}
				vfipos_iab=inputStream.read();
				if (vfipos_iab == -1)
				{
					System.out.println("Mmmbop2");
					break;
				}
				mend_iab=inputStream.read();
				if (mend_iab == -1)
				{
					System.out.println("Mmmbop3");
					break;
				}
				sfc_i_sfc_evf=inputStream.read();
				if (sfc_i_sfc_evf == -1)
				{
					System.out.println("Mmmbop4");
					break;
				}
				sfc_i_sfc_x=inputStream.read();
				if (sfc_i_sfc_x == -1)
				{
					System.out.println("Mmmbop5");
					break;
				}
				sfc_i_sfc_y=inputStream.read();
				if (sfc_i_sfc_y == -1)
				{
					System.out.println("Mmmbop6");
					break;
				}
				sfc_i_sfc_z=inputStream.read();
				if (sfc_i_sfc_z == -1)
				{
					System.out.println("Mmmbop7");
					break;
				}
				System.out.println(vffile_iab+" "+vfipos_iab+" "+mend_iab+" "+sfc_i_sfc_evf+" "+sfc_i_sfc_x+" "+sfc_i_sfc_y+" "+sfc_i_sfc_z);
			}
			
			//  read(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
			
			
			
			
			

//			while ((byteRead = inputStream.read()) != -1)
//			{
//				System.out.println(byteRead);
//				// outputStream.write(byteRead);
//			}

		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	

	// open(unit=vfinfoDat,file='vfinfo.dat',access='DIRECT',recl=vfinfoDatRECL)
	// read(unit=vfinfoDat,rec=1)numfiles,numvf
	// for (int iab=0;iab<numsfc2;iab++)
	// {
	// i=sfc_ab(iab,sfc_ab_i);
	// read(unit=vfinfoDat,rec=iab+1)vffile[iab],vfipos[iab],mend[iab],sfc(i,sfc_evf),sfc(i,sfc_x),sfc(i,sfc_y),sfc(i,sfc_z);
	// }
	// read(unit=vfinfoDat,rec=numsfc2+2)vfipos(numsfc2+1);
	// close(vfinfoDat)
	}
	
//	public static void main(String[] args) 
//	{
////        if (args.length < 2) {
////            System.out.println("Please provide input and output files");
////            System.exit(0);
////        }
// 
////        String inputFile = args[0];
////        String outputFile = args[1];
//		String inputFile = rootDirectory + file;
// 
// 
//        try (
//            InputStream inputStream = new FileInputStream(inputFile);
////            OutputStream outputStream = new FileOutputStream(outputFile);
//        ) {
// 
//            int byteRead;
// 
//            while ((byteRead = inputStream.read()) != -1) 
//            {
//            	System.out.println(byteRead);
////                outputStream.write(byteRead);
//            }
// 
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }
//    }
	
	public void test()
	{
//      if (args.length < 2) {
//      System.out.println("Please provide input and output files");
//      System.exit(0);
//  }

//  String inputFile = args[0];
//  String outputFile = args[1];
	String inputFile = rootDirectory + file;


  try (
      InputStream inputStream = new FileInputStream(inputFile);
//      OutputStream outputStream = new FileOutputStream(outputFile);
  ) {

      int byteRead;

      while ((byteRead = inputStream.read()) != -1) 
      {
      	System.out.println(byteRead);
//          outputStream.write(byteRead);
      }

  } catch (IOException ex) {
      ex.printStackTrace();
  }
	}

}
