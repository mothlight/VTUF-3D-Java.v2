package Test;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import VTUF3D.Dotpro;

public class Testdotpro
{
	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;

	@Test
	public void test()
	{
		double actual,expected;
		double[] v1; 
		double[] v2;
		int ndim;
		double dp;
		double g;
		
		v1 = new double[3];
		v2 = new double[3];
		v1[0]=0.1;
		v1[1]=0.1;
		v1[2]=0.1;
		
		v2[0]=0.1;
		v2[1]=0.1;
		v2[2]=0.1;
		
		ndim = 3;
		dp =0;
		g=0;
		
		HashMap<String,Double> dotproReturn = Dotpro.dotpro(v1, v2, ndim);
		g=dotproReturn.get("g");
		dp=dotproReturn.get("dp");
		//    3.0000000894069678E-002   1.4901161193847656E-008
		actual = dp;
		expected = 3.0000000894069678E-002;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = g;
		expected = 1.4901161193847656E-008;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		////
		
		v1 = new double[3];
		v2 = new double[3];
		v1[0]=0.1;
		v1[1]=0.2;
		v1[2]=0.3;
		
		v2[0]=0.4;
		v2[1]=0.5;
		v2[2]=0.6;
		
		ndim = 3;
		dp =0;
		g=0;
		
		dotproReturn = Dotpro.dotpro(v1, v2, ndim);
		g=dotproReturn.get("g");
		dp=dotproReturn.get("dp");
		//     0.32000001698732405       0.22572612618258842  
		actual = dp;
		expected = 0.32000001698732405 ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		actual = g;
		expected = 0.22572612618258842  ;
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
	}

}
