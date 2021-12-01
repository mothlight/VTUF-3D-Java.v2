package Test;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.Test;

import VTUF3D.VTUF3DUtil;

public class Testcd
{
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	double eightDigitDelta = 1e8;
	
	@Test
	public void test()
	{
		double actual,expected;
	    double Ri,zref,zd,z0,moh,cdtown,Fm,cd_out;


	    Ri=1.;
	    zref=1.;
	    zd=1.;
	    z0=1.;
	    z0=1.;
	    moh=1.;
//	    cdtown=1.;
//	    Fm=1.;
	    
	    HashMap<String,Double> returnValues=VTUF3DUtil.CD(Ri,zref-zd,z0,z0/moh);
	    Fm=  returnValues.get("Fm");
	    cd_out=returnValues.get("cd_out" );
	    //    0.00000000       3.07787023E-02
	    actual = cd_out;
	    expected = 0.00000000;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
	    actual = Fm;
		expected = 3.07787023E-02;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
	    Ri=1.1;
	    zref=1.4;
	    zd=1.3;
	    z0=1.2;
	    z0=1.05;
	    moh=1.11;
//	    cdtown=1.;
//	    Fm=1.;
	    
	    returnValues=VTUF3DUtil.CD(Ri,zref-zd,z0,z0/moh);
	    Fm=  returnValues.get("Fm");
	    cd_out=returnValues.get("cd_out" );
	    //      7.60161376E-04   2.62681600E-02
	    actual = cd_out;
	    expected = 7.60161376E-04;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
	    actual = Fm;
		expected = 2.62681600E-02;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);



	}
	
	@Test
	public void testclrsky()
	{
		VTUF3DUtil vtest = new VTUF3DUtil();
		double actual,expected;
	    double Ri,zref,zd,z0,moh,cdtown,Fm,cd_out;
	    
	    
	    double CZ=0.546029091;
	    double press=99.5500031;
	    double zeni=0.993179321;
	    double Ta_sol=16.1162109;
	    double Td_sol=-17.2545624;
	    double INOT=1403.12183  ;
	    double Kdir = 0.0;
	    double Kdif = 0.0;
	    double Ktot = 0.0;
	    double CA = 0.0;
	    int yd_actual = 40;
	    double alb_sfc = 0.118000001;
	    int cloudtype = 0;
	    double abs_aero = 0.0;
	    double Ktotfrc = 32.5234222;
	    double DR1F = 0.0;
	    
	    HashMap<String,Double> clrskyreturn=vtest.CLRSKY(CZ, press , zeni, Ta_sol, Td_sol,
				INOT, Kdir, Kdif, Ktot, CA, yd_actual, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
	    CZ = clrskyreturn.get("CZ");
		press = clrskyreturn.get("PRESS");
		zeni = clrskyreturn.get("ZEN");
		Ta_sol = clrskyreturn.get("AIR");
		Td_sol = clrskyreturn.get("DEW");
		INOT = clrskyreturn.get("INOT");
		Kdir = clrskyreturn.get("DR1");
		Kdif = clrskyreturn.get("DF1");
		Ktot = clrskyreturn.get("GL1");
		CA = clrskyreturn.get("CA");
		alb_sfc = clrskyreturn.get("alb_sfc");
		abs_aero = clrskyreturn.get("abs_aero");
		Ktotfrc = clrskyreturn.get("Ktotfrc");
		DR1F = clrskyreturn.get("DR1F");
		
		actual = Kdif;
		expected = 144.988281;
//		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		
		actual = Kdir;
		expected = 389.844543;
//		assertEquals(expected, actual, Math.abs(expected) / eightDigitDelta);
		
	}
	
	@Test
	public void testsunpos()
	{
		VTUF3DUtil vtest = new VTUF3DUtil();
		double actual,expected;
	    double Ri,zref,zd,z0,moh,cdtown,Fm,cd_out;
		
		int yd_actual=79;
		double TM=11.968515096586088;
		double LAT=0.022938862358961472;
		


		
		
		
		
		
		 int JDAY=yd_actual;
		   double PI=Math.PI;
		    double HR_RAD=15.*PI/180.;
		   double   THETA = (JDAY-1)*(2.*PI)/365.;
		    double  THETA_INOT=THETA;
		    if(LAT < 0) 
		    {
		    	THETA=( (THETA+PI) % (2.*PI) );
		     LAT=Math.abs(LAT);
		    } 
		     double DEC = 0.006918-0.399912*Math.cos(THETA)+0.070257*Math.sin(THETA)-0.006758*Math.cos(2*THETA)+0.000907*Math.sin(2*THETA)-0.002697*Math.cos(3*THETA)+0.00148*Math.sin(3*THETA);
		    double HL=TM*HR_RAD;
		    double  CZ = (Math.sin(LAT)*Math.sin(DEC))-(Math.cos(LAT)*Math.cos(DEC)*Math.cos(HL));
		    double  ZEN=Math.acos(CZ);
		    
		    double a1 = (Math.sin(DEC)-Math.sin(LAT)*CZ)/(Math.cos(LAT)*Math.sin(ZEN));
		    
		   double CA = Math.max(-1.,Math.min(1.,a1));
		
		
		
		
		
		
		HashMap<String, Double> sunposReturn = vtest.SUNPOS(yd_actual, TM, LAT);
		double zeni = sunposReturn.get("ZEN");
		double AZIM = sunposReturn.get("AZIM");
		double CZre = sunposReturn.get("CZ");
		double INOT = sunposReturn.get("INOT");
		double CAre = sunposReturn.get("CA");
		double az = AZIM * 180. / Math.PI;
		double zen = zeni * 180. / Math.PI;
		double ralt = 90. - zen;
		
		
		
		
		
//	    double CZ=0.546029091;
	    double press=99.5500031;
//	    double zeni=0.993179321;
	    double Ta_sol=16.1162109;
	    double Td_sol=-17.2545624;
//	    double INOT=1403.12183  ;
	    double Kdir = 0.0;
	    double Kdif = 0.0;
	    double Ktot = 0.0;
//	    double CA = 0C.0;
//	    int yd_actual = 40;
	    double alb_sfc = 0.118000001;
	    int cloudtype = 0;
	    double abs_aero = 0.0;
	    double Ktotfrc = 32.5234222;
	    double DR1F = 0.0;
		HashMap<String,Double> clrskyreturn=vtest.CLRSKY(CZ, press , zeni, Ta_sol, Td_sol,
				INOT, Kdir, Kdif, Ktot, CA, yd_actual, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
		
	}
	
	
	

}
