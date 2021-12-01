package Test;

import static org.junit.Assert.*;

import org.junit.Test;

import VTUF3D.VTUF3DUtil;

public class testsfc_ri
{

	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	VTUF3DUtil util = new VTUF3DUtil();
	
	@Test
	public void test()
	{
		double actual,expected;
		double zH,Thorz,Tsfc,Uhorz,Ri;
		zH=10;
		Thorz=25.2;
		Tsfc=30.0;
		Uhorz=1.0;
		

		Ri=util.SFC_RI(0.1*zH,Thorz,Tsfc,Uhorz);
		actual = Ri;
		expected = -1.70192337;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		zH=12.2;
		Thorz=25.4;
		Tsfc=30.3;
		Uhorz=1.3;
		

		Ri=util.SFC_RI(0.1*zH,Thorz,Tsfc,Uhorz);
		actual = Ri;
		expected = -1.24245095;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
	}

}
