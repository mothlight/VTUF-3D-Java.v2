package VTUF3D;

import java.util.HashMap;

public class Dotpro
{
	public static HashMap<String, Double> dotpro(double[] v1, double[] v2, int ndim)
	{
		HashMap<String, Double> returnValues = new HashMap<String, Double>();
		// ! subroutine dotpro returns the angle 'g' (in radians) and the
		// dotproduct 'dp' of two vectors v1, v2 with dimensions ndim each
		// double[] v1(ndim),v2(ndim);
		// double g,dp;
		double v1mag, v2mag, dparg;
		double dp = 0.;
		v1mag = 0.;
		v2mag = 0.;
		for (int j = 0; j < ndim; j++)
		{
			dp = dp + v1[j] * v2[j];
			v1mag = v1mag + Math.pow(v1[j], 2);
			v2mag = v2mag + Math.pow(v2[j], 2);
		}

		v1mag = Math.sqrt(v1mag);
		v2mag = Math.sqrt(v2mag);

		dparg = dp / (v1mag * v2mag);

		if (Math.abs(dparg) > 1)
		{
			System.out.println("warning acos argument > 1");
			// write(*,*) 'warning acos argument > 1';
			System.out.println("arg = " + " " + dparg + " " + dp + " " + v1mag
					+ " " + v2mag);
			// write(*,*) 'arg = ', dparg,dp,v1mag,v2mag;
			if (dparg < -1)
			{
				dparg = -1.0;
			}
			if (dparg > 1)
			{
				dparg = 1.0;
			}
		}
		double g = Math.acos(dparg);

		returnValues.put("g", g);
		returnValues.put("dp", dp);

		return returnValues;
	}

}
