package Test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import VTUF3D.VTUF3DUtil;

public class testpll
{
	double sixDigitDelta = 1e6;
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	double actual,expected;
	VTUF3DUtil util = new VTUF3DUtil();
	HashMap<String,Double> returnValues;
	
	@Test
	public void testhtc()
	{
		double Ri;double u;double z;double z0m;double z0h;double httc_out;double Fh;
		
		Ri=-1.0;
		u=2.0;
		z=5.0;
		z0m=4.;
		z0h=3.;
		httc_out=0;
		Fh=0;
		
		returnValues = util.HTC(Ri, u, z, z0m, z0h);
		
		Fh = returnValues.get("Fh");
		httc_out = returnValues.get("httc_out");
		
		actual = Fh;
		expected =  0.488617748  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		
		actual = httc_out;
		expected =   4.24344778  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		
//		  -1.00000000       2.00000000       5.00000000       4.00000000       3.00000000       4.24344778      0.488617748  
		
		
		Ri=1.0;
		u=2.0;
		z=5.0;
		z0m=4.;
		z0h=3.;
		httc_out=0;
		Fh=0;
		
		returnValues = util.HTC(Ri, u, z, z0m, z0h);
		
		Fh = returnValues.get("Fh");
		httc_out = returnValues.get("httc_out");
		
		actual = Fh;
		expected =  1.34450356E-02  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		
		actual = httc_out;
		expected =  0.116764702  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		// 0.116764702       1.34450356E-02
	}
	
	@Test
	public void testclearsky()
	{
		double CZ;double PRESS;double ZEN;double AIR;double DEW;double INOT;double DR1;double DF1;
		double GL1;double CA;
		int JDAY;
		double alb_sfc;
		int cloudtype;
		double abs_aero;double Ktotfrc;double DR1F;
		  
		CZ=-0.966966391;
		PRESS=101.;
		ZEN=2.88384438;
		AIR=22.2;
		DEW=20.0;
		INOT= 1410.25256;
		DR1=0.0;
		DF1=0.0;
		GL1=0.0;
		CA=0.302228034;
		JDAY = 22;
		alb_sfc=0.5;
		cloudtype=0;
		abs_aero=0;
		Ktotfrc=0.1;
		DR1F=0.0;
		
		returnValues = util.CLRSKY(CZ, PRESS, ZEN, AIR, DEW, INOT, DR1, DF1, GL1, CA, JDAY, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
		//  -0.966966391       101.000000       2.88384438       22.2000008       20.0000000       1410.25256       0.00000000    0.00000000       0.00000000      0.302228034              22  0.500000000               0   0.00000000      0.100000001       0.00000000  
		CZ=returnValues.get("CZ");
		actual = CZ;
		expected = -0.966966391       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		PRESS=returnValues.get("PRESS");
		actual = PRESS;
		expected =  101.000000      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		ZEN=returnValues.get("ZEN");
		actual = ZEN;
		expected =   2.88384438      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		AIR=returnValues.get("AIR");
		actual = AIR;
		expected =   22.2000008       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DEW=returnValues.get("DEW");
		actual = DEW;
		expected =  20.0000000       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		INOT=returnValues.get("INOT");
		actual = INOT;
		expected =  1410.25256       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1=returnValues.get("DR1");
		actual = DR1;
		expected =  0.00000000    ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DF1=returnValues.get("DF1");
		actual = DF1;
		expected =  0.00000000  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		GL1=returnValues.get("GL1");
		actual = GL1;
		expected =       0.00000000      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		CA=returnValues.get("CA");
		actual = CA;
		expected =  0.302228034               ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("JDAY", JDAY);
		alb_sfc=returnValues.get("alb_sfc");
		actual = alb_sfc;
		expected =   0.500000000 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("cloudtype", cloudtype);
		abs_aero=returnValues.get("abs_aero");
		actual = abs_aero;
		expected =             0.00000000   ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		Ktotfrc=returnValues.get("Ktotfrc");
		actual = Ktotfrc;
		expected =     0.100000001   ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1F=returnValues.get("DR1F");
		actual = DR1F;
		expected =      0.00000000 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		
		
		
		
		CZ=0.881551206;
		PRESS=101.;
		ZEN=0.491658300;
		AIR=22.2;
		DEW=20.0;
		INOT= 1410.25256;
		DR1=0.0;
		DF1=0.0;
		GL1=0.0;
		CA=-8.82562324E-02;
		JDAY = 22;
		alb_sfc=0.5;
		cloudtype=0;
		abs_aero=0;
		Ktotfrc=0.1;
		DR1F=0.0;
		
		returnValues = util.CLRSKY(CZ, PRESS, ZEN, AIR, DEW, INOT, DR1, DF1, GL1, CA, JDAY, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
		//    0.881551206       101.000000      0.491658300       22.2000008       20.0000000       1410.25256       719.361572       219.328400       938.689941      -8.82562324E-02          22  0.500000000               0   63.0295448      0.100000001       4.30643559E-06
 
		CZ=returnValues.get("CZ");
		actual = CZ;
		expected = 0.881551206      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		PRESS=returnValues.get("PRESS");
		actual = PRESS;
		expected =  101.000000      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		ZEN=returnValues.get("ZEN");
		actual = ZEN;
		expected =   0.491658300         ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		AIR=returnValues.get("AIR");
		actual = AIR;
		expected =      22.2000008      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DEW=returnValues.get("DEW");
		actual = DEW;
		expected =        20.0000000          ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		INOT=returnValues.get("INOT");
		actual = INOT;
		expected =      1410.25256         ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1=returnValues.get("DR1");
		actual = DR1;
		expected =     719.361572       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DF1=returnValues.get("DF1");
		actual = DF1;
		expected =     219.328400     ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		GL1=returnValues.get("GL1");
		actual = GL1;
		expected =         938.689941     ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		CA=returnValues.get("CA");
		actual = CA;
		expected =       -8.82562324E-02                    ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("JDAY", JDAY);
		alb_sfc=returnValues.get("alb_sfc");
		actual = alb_sfc;
		expected =       0.500000000          ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("cloudtype", cloudtype);
		abs_aero=returnValues.get("abs_aero");
		actual = abs_aero;
		expected =                    63.0295448    ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		Ktotfrc=returnValues.get("Ktotfrc");
		actual = Ktotfrc;
		expected =      0.100000001       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1F=returnValues.get("DR1F");
		actual = DR1F;
		expected =      4.30643559E-06 ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);	
		
		
		
		CZ=0.881551206;
		PRESS=101.;
		ZEN=0.491658300;
		AIR=22.2;
		DEW=20.0;
		INOT= 1410.25256;
		DR1=0.0;
		DF1=0.0;
		GL1=0.0;
		CA=-8.82562324E-02;
		JDAY = 22;
		alb_sfc=0.5;
		cloudtype=6;
		abs_aero=0;
		Ktotfrc=0.1;
		DR1F=0.0;
		
		returnValues = util.CLRSKY(CZ, PRESS, ZEN, AIR, DEW, INOT, DR1, DF1, GL1, CA, JDAY, alb_sfc, cloudtype, abs_aero, Ktotfrc, DR1F);
		//     0.881551206       101.000000      0.491658300       22.2000008       20.0000000       1410.25256       34.0493164       353.802887       387.852203      -8.82562324E-02          22  0.500000000               6   63.0295448      0.100000001       4.30643559E-06

 
		CZ=returnValues.get("CZ");
		actual = CZ;
		expected = 0.881551206      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		PRESS=returnValues.get("PRESS");
		actual = PRESS;
		expected =  101.000000      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		ZEN=returnValues.get("ZEN");
		actual = ZEN;
		expected =   0.491658300         ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		AIR=returnValues.get("AIR");
		actual = AIR;
		expected =      22.2000008      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DEW=returnValues.get("DEW");
		actual = DEW;
		expected =        20.0000000          ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		INOT=returnValues.get("INOT");
		actual = INOT;
		expected =      1410.25256         ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1=returnValues.get("DR1");
		actual = DR1;
		expected =     34.0493164      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DF1=returnValues.get("DF1");
		actual = DF1;
		expected =     353.802887      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		GL1=returnValues.get("GL1");
		actual = GL1;
		expected =     387.852203         ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		CA=returnValues.get("CA");
		actual = CA;
		expected =       -8.82562324E-02                       ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("JDAY", JDAY);
		alb_sfc=returnValues.get("alb_sfc");
		actual = alb_sfc;
		expected =              0.500000000          ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
//		    returnValues.put("cloudtype", cloudtype);
		abs_aero=returnValues.get("abs_aero");
		actual = abs_aero;
		expected =                               63.0295448      ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		Ktotfrc=returnValues.get("Ktotfrc");
		actual = Ktotfrc;
		expected =        0.100000001        ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);	
		DR1F=returnValues.get("DR1F");
		actual = DR1F;
		expected =           4.30643559E-06 ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);	
		
		
	}
	
	@Test
	public void testsunpos()
	{
		HashMap<String,Double> returnValues ;
		int JDAY;
		double TM, LAT, ZEN, AZIM, CZ, INOT, CA;
		
		JDAY = 1;
		TM=1;
		LAT=1;
		ZEN=1;
		AZIM=1;
		CZ=1;
		INOT=1;
		CA=1;
		returnValues=util.SUNPOS(JDAY, TM, LAT);		
		ZEN=returnValues.get("ZEN");
		AZIM=returnValues.get("AZIM");
		CZ=returnValues.get("CZ");
		INOT=returnValues.get("INOT");
		CA=returnValues.get("CA");		
		actual = ZEN;
		expected = 2.51456738 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = AZIM;
		expected = 0.417933017 ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);		
		actual = CZ;
		expected = -0.809776425 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = INOT;
		expected = 1412.82959 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = CA;
		expected = 0.913929820 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
//        1   1.00000000       1.00000000       2.51456738      0.417933017     -0.809776425       1412.82959      0.913929820 
		
		
		JDAY = 22;
		TM=11.;
		LAT=-35.;

		returnValues=util.SUNPOS(JDAY, TM, LAT);		
		ZEN=returnValues.get("ZEN");
		AZIM=returnValues.get("AZIM");
		CZ=returnValues.get("CZ");
		INOT=returnValues.get("INOT");
		CA=returnValues.get("CA");		
		actual = ZEN;
		expected =  2.88384438     ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = AZIM;
		expected = 1.87782550  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = CZ;
		expected = -0.966966391  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = INOT;
		expected = 1410.25256  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = CA;
		expected = 0.302228034 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
//                      22   11.0000000      -35.0000000       2.88384438       1.87782550     -0.966966391       1410.25256      0.302228034  
		
		JDAY = 62;
		TM=12.;
		LAT=-42.;

		returnValues=util.SUNPOS(JDAY, TM, LAT);		
		ZEN=returnValues.get("ZEN");
		AZIM=returnValues.get("AZIM");
		CZ=returnValues.get("CZ");
		INOT=returnValues.get("INOT");
		CA=returnValues.get("CA");		
		actual = ZEN;
		expected =  2.12874293     ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = AZIM;
		expected = 3.14099479   ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);		
		actual = CZ;
		expected = -0.529445350  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = INOT;
		expected = 1389.48682  ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);		
		actual = CA;
		expected = 0.999999821 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
//	      62   12.0000000      -42.0000000       2.12874293       3.14099479     -0.529445350       1389.48682      0.999999821
	}

	@Test
	public void testpll()
	{
		
		double xa,ya,za;
		double pll;
		
		xa=0.1;
		ya=0.2;
		za=0.3;
		util = new VTUF3DUtil();
		pll=util.pll(xa, ya, za);		
		actual = pll;
		expected = 0.14641457966549548 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=0.99;
		ya=0.98;
		za=0.97;

		pll=util.pll(xa, ya, za);		
		actual = pll;
		expected =0.19980242912895416 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=0.0;
		ya=0.0;
		za=0.0;

		pll=util.pll(xa, ya, za);		
		actual = pll;
		expected =0.0 ;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	@Test
	public void testf2()
	{
		double x,y,z1,z2,f2return;
		x=0.1;
		y=0.1;
		z1=0.1;
		z2=0.1;
		f2return = util.F2(x,y,z1,z2);
		actual = f2return;
		expected =8.6050489152327270E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x=0.99;
		y=0.98;
		z1=0.97;
		z2=0.96;
		f2return = util.F2(x,y,z1,z2);
		actual = f2return;
		expected = 8.6572344600630027E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
	}
	@Test
	public void testf3()
	{
		double xa,ya,z1a,z2a,z3a,f3return;
		xa=0.99;
		ya=0.98;
		z1a=0.97;
		z2a=0.96;
		z3a=0.95;
		f3return = util.F3(xa,ya,z1a,z2a,z3a);
		actual = f3return;
		expected =1.5829913979807597E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=0.99;
		ya=0.98;
		z1a=0.97;
		z2a=0.0;
		z3a=0.95;
		f3return = util.F3(xa,ya,z1a,z2a,z3a);
		actual = f3return;
		expected =8.6227959229739984E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		

		
	}
	
	@Test
	public void testf4()
	{
		double xa,ya,z1a,z2a,z3a,f4return;
		xa=0.99;
		ya=0.98;
		z1a=0.97;
		z2a=0.96;
		z3a=0.95;
		f4return = util.F4(xa,ya,z1a,z2a,z3a);
		actual = f4return;
		expected =4.2871608648314290E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=1.99;
		ya=1.98;
		z1a=1.97;
		z2a=1.96;
		z3a=1.95;
		f4return = util.F4(xa,ya,z1a,z2a,z3a);
		actual = f4return;
		expected =4.3105261053549448E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	
	@Test
	public void testf5()
	{
		double x1a,x2a,x3a,ya,z1a,z2a,z3a,f5return;
		x1a=1.99;
		x2a=1.98;
		x3a=1.97;
		ya=1.96;
		z1a=1.95;
		z2a=1.94;
		z3a=1.93;
		f5return = util.F5(x1a,x2a,x3a,ya,z1a,z2a,z3a);
		actual = f5return;
		expected =4.4374517984902759E-003;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x1a=0.01;
		x2a=0.02;
		x3a=0.03;
		ya=0.04;
		z1a=0.05;
		z2a=0.06;
		z3a=0.07;
		f5return = util.F5(x1a,x2a,x3a,ya,z1a,z2a,z3a);
		actual = f5return;
		expected =8.0420749393686819E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	
	@Test
	public void testper()
	{
		double x,y,z,perreturn;
		x=0.1;
		y=0.1;
		z=0.1;

		perreturn = util.per(x, y, z);
		actual = perreturn;
		expected =0.20004377607540316;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x=0.0;
		y=0.1;
		z=0.1;

		perreturn = util.per(x, y, z);
		actual = perreturn;
		expected =0.0;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	
	@Test
	public void testf7()
	{
		double xa,y1a,y2a,z1a,z2a,f7return;
		xa=0.01;
		y1a=0.02;
		y2a=0.03;
		z1a=0.04;
		z2a=0.05;
		f7return = util.F7(xa,y1a,y2a,z1a,z2a);
		actual = f7return;
		expected =1.3083403998807527E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=1.01;
		y1a=1.02;
		y2a=1.03;
		z1a=1.04;
		z2a=1.05;
		f7return = util.F7(xa,y1a,y2a,z1a,z2a);
		actual = f7return;
		expected =3.2806605979083386E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		xa=1.01;
		y1a=0.0;
		y2a=1.03;
		z1a=1.04;
		z2a=1.05;
		f7return = util.F7(xa,y1a,y2a,z1a,z2a);
		actual = f7return;
		expected =3.2102696290052232E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	
	@Test
	public void testf8()
	{
		double x1,x2,y,z,f8return;
		x1=1.01;
		x2=0.0;
		y=1.03;
		z=1.04;
		f8return = util.F8(x1,x2,y,z);
		actual = f8return;
		expected =0.0000000000000000;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		

		x1=0.2;
		x2=0.3;
		y=0.4;
		z=0.5;
		f8return = util.F8(x1,x2,y,z);
		actual = f8return;
		expected =6.6845529070288706E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}
	
	@Test
	public void testf9()
	{
		double x1a,x2a,x3a,y1a,y2a,z1a,z2a,f9return;
		x1a=0.1;
		x2a=0.1;
		x3a=0.1;
		y1a=0.1;
		y2a=0.1;
		z1a=0.1;
		z2a=0.1;
		f9return = util.F9(x1a,x2a,x3a,y1a,y2a,z1a,z2a);
		actual = f9return;
		expected =1.0056881208823795E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x1a=1.1;
		x2a=1.2;
		x3a=1.3;
		y1a=1.4;
		y2a=1.5;
		z1a=1.6;
		z2a=1.7;
		f9return = util.F9(x1a,x2a,x3a,y1a,y2a,z1a,z2a);
		actual = f9return;
		expected =1.3527142488224887E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x1a=1.1;
		x2a=0.0;
		x3a=1.3;
		y1a=1.4;
		y2a=1.5;
		z1a=1.6;
		z2a=1.7;
		f9return = util.F9(x1a,x2a,x3a,y1a,y2a,z1a,z2a);
		actual = f9return;
		expected =2.4657988690420241E-002;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
		
		x1a=1.1;
		x2a=1.2;
		x3a=0.0;
		y1a=1.4;
		y2a=1.5;
		z1a=1.6;
		z2a=1.7;
		f9return = util.F9(x1a,x2a,x3a,y1a,y2a,z1a,z2a);
		actual = f9return;
		expected =0.0;
		assertEquals(expected, actual, Math.abs(expected) / sixDigitDelta);
	}

}
