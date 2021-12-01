package VTUF3D;

import java.util.HashMap;

public class Crosspro
{
    public static HashMap crosspro(double[] v1, double[] v2, int ndim)
    {
    	HashMap returnValues = new HashMap();
    	double[] cp = new double[ndim];
//! subroutine CROSSPRO returns the vector cross product 'CP' and it's magnitude 'MCP'
//! of two vectors v1, v2 with dimensions ndim each, where subscripts x,y,z are associated
//! with indices 1,2,3 respectively
//    INTEGER ndim;
//    REAL(KIND=8) v1(ndim),v2(ndim),CP(ndim),MCP ;

    double mcp=0.0  ;
//! ASSUMING HERE NDIM = 3
    cp[1-1] = (v1[2-1]*v2[3-1]-v1[3-1]*v2[2-1]) ;
    cp[2-1] = (v1[3-1]*v2[1-1]-v1[1-1]*v2[3-1]);
    cp[3-1] = (v1[1-1]*v2[2-1]-v1[2-1]*v2[1-1]);
      
    mcp = Math.sqrt(Math.pow(cp[1-1],2)+Math.pow(cp[2-1],2)+Math.pow(cp[3-1],2))  ;

    returnValues.put("cp", cp);
    returnValues.put("mcp", mcp);
    return returnValues;
    }

}
