package VTUF3D;

import java.util.ArrayList;

import VTUF3D.Utilities.Common;

public class ForcingData
{

	Common common = new Common();
	String rootDirectory;
	String filename = "forcing.dat";
	
	private int numfrc;
	private double starttime;
	private double deltatfrc;
	private double[] Kdnfrc;
	private double[] Ldnfrc;
	private double[] Tafrc;
	private double[] eafrc;
	private double[] Uafrc;
	private double[] Udirfrc;
	private double[] Pressfrc;
	private double[] timefrc;
	
	public static void main(String[] args)
	{

		String rootDirectory ="/home/kerryn/Documents/Work/VTUF-Runs/PrestonBase/PrestonBase8/";
		ForcingData forc = new ForcingData(rootDirectory);
		forc.readForcingData();
		

	}
	public ForcingData(String rootDirectory)
	{
		super();
		this.rootDirectory = rootDirectory;
	}
	
//    read(981,*)numfrc,starttime,deltatfrc
//    do k=1,numfrc+1
//     read(981,*)Kdnfrc(k),Ldnfrc(k),Tafrc(k),eafrc(k),Uafrc(k), Udirfrc(k),Pressfrc(k)
//     if(Kdnfrc(k).ge.-90.) Kdnfrc(k)=max(Kdnfrc(k),0.)
//     timefrc(k)=starttime+real(k-1)*deltatfrc
//    enddo
	public void readForcingData()
	{
		ArrayList<String> forcingDataStrs=common.readTextFileToArray(rootDirectory + filename);
		int count = 0;
		for (String line : forcingDataStrs)
		{
			if (count ==0)
			{
				double[] doubleLine = common.splitDoubleString(line);
				this.numfrc = (int)doubleLine[0];
				this.starttime = doubleLine[1];
				this.deltatfrc = doubleLine[2];
				
				this.Kdnfrc = new double[this.numfrc];				
				this.Ldnfrc = new double[this.numfrc];
				this.Tafrc = new double[this.numfrc];
				this.eafrc = new double[this.numfrc];
				this.Uafrc = new double[this.numfrc];
				this.Udirfrc = new double[this.numfrc];
				this.Pressfrc = new double[this.numfrc];
				this.timefrc = new double[this.numfrc];
			}
			else
			{
//				System.out.println(count + " " + line);
				double[] doubleLine = common.splitDoubleString(line);
				this.Kdnfrc[count-1] = doubleLine[0];
				if(this.Kdnfrc[count-1]>=-90.) 
				{
					this.Kdnfrc[count-1]=Math.max(this.Kdnfrc[count-1],0.);
				}
				this.Ldnfrc[count-1] = doubleLine[1];
				this.Tafrc[count-1] = doubleLine[2];
				this.eafrc[count-1] = doubleLine[3];
				this.Uafrc[count-1] = doubleLine[4];
				this.Udirfrc[count-1] = doubleLine[5];
				this.Pressfrc[count-1] = doubleLine[6];
				this.timefrc[count-1]=starttime+(count-1)*this.deltatfrc;
			}			
			count ++;
			if (count > this.numfrc)
			{
				break;
			}
		}
	}
	public Common getCommon()
	{
		return common;
	}
	public void setCommon(Common common)
	{
		this.common = common;
	}
	public String getRootDirectory()
	{
		return rootDirectory;
	}
	public void setRootDirectory(String rootDirectory)
	{
		this.rootDirectory = rootDirectory;
	}
	public String getFilename()
	{
		return filename;
	}
	public void setFilename(String filename)
	{
		this.filename = filename;
	}
	public int getNumfrc()
	{
		return numfrc;
	}
	public void setNumfrc(int numfrc)
	{
		this.numfrc = numfrc;
	}
	public double getStarttime()
	{
		return starttime;
	}
	public void setStarttime(double starttime)
	{
		this.starttime = starttime;
	}
	public double getDeltatfrc()
	{
		return deltatfrc;
	}
	public void setDeltatfrc(double deltatfrc)
	{
		this.deltatfrc = deltatfrc;
	}
	public double[] getKdnfrc()
	{
		return Kdnfrc;
	}
	public void setKdnfrc(double[] kdnfrc)
	{
		Kdnfrc = kdnfrc;
	}
	public double[] getLdnfrc()
	{
		return Ldnfrc;
	}
	public void setLdnfrc(double[] ldnfrc)
	{
		Ldnfrc = ldnfrc;
	}
	public double[] getTafrc()
	{
		return Tafrc;
	}
	public void setTafrc(double[] tafrc)
	{
		Tafrc = tafrc;
	}
	public double[] getEafrc()
	{
		return eafrc;
	}
	public void setEafrc(double[] eafrc)
	{
		this.eafrc = eafrc;
	}
	public double[] getUafrc()
	{
		return Uafrc;
	}
	public void setUafrc(double[] uafrc)
	{
		Uafrc = uafrc;
	}
	public double[] getUdirfrc()
	{
		return Udirfrc;
	}
	public void setUdirfrc(double[] udirfrc)
	{
		Udirfrc = udirfrc;
	}
	public double[] getPressfrc()
	{
		return Pressfrc;
	}
	public void setPressfrc(double[] pressfrc)
	{
		Pressfrc = pressfrc;
	}
	public double[] getTimefrc()
	{
		return timefrc;
	}
	public void setTimefrc(double[] timefrc)
	{
		this.timefrc = timefrc;
	}

	
}
