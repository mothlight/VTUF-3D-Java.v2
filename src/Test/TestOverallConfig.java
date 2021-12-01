package Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Test;

import VTUF3D.MaespaConfigTreeMapState;
import VTUF3D.MaespaDataResults;
import VTUF3D.OverallConfiguration;
import VTUF3D.Utilities.MaespaDataFile;

public class TestOverallConfig
{

	double threeDigitDelta = 1e3;
	double twoDigitDelta = 1e2;
	double actual,expected;
	int actualInt, expectedInt;
	int[] actualArray,expectedArray;
	
	@Test
	public void testTreeMapFromConfig()
	{
		String rootDirectory = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmaller/";
		OverallConfiguration overall = new OverallConfiguration(rootDirectory);
		overall.readParametersDat();
		MaespaConfigTreeMapState treeMapFromConfig = overall.readMaespaTreeMapFromConfig(rootDirectory);
		
		actualInt = treeMapFromConfig.numberTreePlots;
		expectedInt = 98;
		assertEquals(expectedInt, actualInt);

		actualInt = treeMapFromConfig.width;
		expectedInt = 14;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.length;
		expectedInt = 14;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.numberTreePlots;
		expectedInt = 98;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.numberBuildingPlots;
		expectedInt = 98;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configPartitioningMethod;
		expectedInt = 17;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.usingDiffShading;
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapCentralArrayLength;
		expectedInt = 4;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapCentralWidth;
		expectedInt = 2;
		assertEquals(expectedInt, actualInt);

		actualInt = treeMapFromConfig.configTreeMapCentralLength;
		expectedInt = 2;
		assertEquals(expectedInt, actualInt);

		actualInt = treeMapFromConfig.configTreeMapX;
		expectedInt = 14;
		assertEquals(expectedInt, actualInt);

		actualInt = treeMapFromConfig.configTreeMapY;
		expectedInt = 14;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapX1;
		expectedInt = 6;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapX2;
		expectedInt = 7;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapY1;
		expectedInt = 6;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapY2;
		expectedInt = 7;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapNumsfcab;
		expectedInt = 20;
		assertEquals(expectedInt, actualInt);
		
		actualInt = treeMapFromConfig.configTreeMapHighestBuildingHeight;
		expectedInt = 2;
		assertEquals(expectedInt, actualInt);
        
		actual = treeMapFromConfig.configTreeMapGridSize;
		expected = 5.;
		assertEquals(expectedInt, actualInt);
        
        int[] xLocation = new int[]
        		{  0  ,0   ,0   ,  0     ,      0   ,        0    ,       0   ,        1    ,       1  ,1   ,1  ,
				1    ,       1    ,       1   ,2    ,       2   ,2   ,2    ,       2     ,      2    ,       2   ,3   ,
				3    ,       3    ,       3   ,3     ,      3   ,3    ,       4     ,      4    ,       4      ,
				4    ,       4    ,       4   ,4   ,5   ,5   ,5   ,5    ,       5      ,
				5    ,       5   ,6    ,       6    ,       6   ,6   ,6     ,      6    ,       6   ,       7   ,
				7    ,       7     ,      7      ,     7     ,      7    ,       7     ,      8    ,       8     ,      8     ,      8      ,     8      ,     8     ,
				8     ,      9    ,       9,9,9,9,9,9, 10, 10, 10, 10, 10, 10, 10, 11, 11, 11, 11, 11, 11, 11, 12, 12, 12, 12, 12, 12, 12, 13, 13, 13, 13, 13, 13, 13};
        actualArray = treeMapFromConfig.xLocation;
        expectedArray = xLocation;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        
        int[] yLocation = new int[]
        		{       0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12, 
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12, 
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12,  
        				0,  2,  4,  6,  8, 10, 12, 
        				0,  2,  4,  6,  8, 10, 12
};
        actualArray = treeMapFromConfig.yLocation;
        expectedArray = yLocation;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] phyfileNumber = new int[]
        		{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 
        				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
};
        actualArray = treeMapFromConfig.phyfileNumber;
        expectedArray = phyfileNumber;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] strfileNumber= new int[]
        		{
        				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
        				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
	
        		};
        actualArray = treeMapFromConfig.strfileNumber;
        expectedArray = strfileNumber;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] treesfileNumber= new int[]
        		{
       				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
       				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
	
       		};
        actualArray = treeMapFromConfig.treesfileNumber;
        expectedArray = treesfileNumber;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] treesHeight= new int[]{     0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
        		0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0
};
        actualArray = treeMapFromConfig.treesHeight;
        expectedArray = treesHeight;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] trees = new int[]
        		{
          				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
          				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1
   	
          		};
        actualArray = treeMapFromConfig.trees;
        expectedArray = trees;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] xBuildingLocation = new int[]{ 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 
        		4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 6, 6, 6, 6, 6, 6, 6, 7, 7, 7, 7, 7, 7, 7, 8, 8, 8, 8, 8, 8, 8, 9, 9, 9, 9, 9, 9, 9,10,
        		10,10,10,10,10,10,11,11,11,11,11,11,11,12,12,12,12,12,12,12,13,13,13,13,13,13,13
};
        actualArray = treeMapFromConfig.xBuildingLocation;
        expectedArray = xBuildingLocation;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] yBuildingLocation = new int[]{ 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,
        		13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,
        		13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13, 1, 3, 5, 7, 9,11,13
};
        actualArray = treeMapFromConfig.yBuildingLocation;
        expectedArray = yBuildingLocation;
        assertTrue(Arrays.equals(expectedArray, actualArray));
        
        int[] buildingsHeight = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,
 				 1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};
        actualArray = treeMapFromConfig.yLocation;
        expectedArray = yLocation;
        assertTrue(Arrays.equals(expectedArray, actualArray));
	}
	
	@Test
	public void testSmaller2TD()
	{
		String rootDirectory = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmaller2/";
		OverallConfiguration overall = new OverallConfiguration(rootDirectory);
		overall.readParametersDat();
		MaespaConfigTreeMapState treeMapFromConfig = overall.readMaespaTreeMapFromConfig(rootDirectory);
		
		HashMap<String,ArrayList<MaespaDataResults>> returnValues = overall.mapTrees(treeMapFromConfig);
		//overall.readAllMaespaData(treeMapFromConfig);
		
		int[][] overallgetTreeXYMap = overall.getTreeXYMap();
		int[][] overallgetTreeXYTreeMap = overall.getTreeXYTreeMap();
		
		HashMap<String,MaespaDataFile> maespaTestflxData = overall.readMaespaTestflxData(returnValues);
		System.out.println(maespaTestflxData.toString());
		
		double value = overall.getTransmissionForTree(1, maespaTestflxData);
		System.out.println(value);
		
		value = overall.getTransmissionForTree(2, maespaTestflxData);
		System.out.println(value);
		
	}
	
	@Test
	public void testSmaller2()
	{
		String rootDirectory = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmaller2/";
		OverallConfiguration overall = new OverallConfiguration(rootDirectory);
		overall.readParametersDat();
		MaespaConfigTreeMapState treeMapFromConfig = overall.readMaespaTreeMapFromConfig(rootDirectory);
		
		HashMap<String,ArrayList<MaespaDataResults>> returnValues = overall.mapTrees(treeMapFromConfig);
		//overall.readAllMaespaData(treeMapFromConfig);
		
		int[][] overallgetTreeXYMap = overall.getTreeXYMap();
		int[][] overallgetTreeXYTreeMap = overall.getTreeXYTreeMap();
//		TreeMap<String,ArrayList<MaespaDataResults>> returnValues
		
		System.out.println(returnValues.toString());
		double x = returnValues.get("1_1").get(0).getTCAN() + 273.15;
		System.out.println(x);
		x = returnValues.get("1_2").get(1).getTCAN() + 273.15;
		System.out.println(x);
		
		expected = 290.059998;
		int item = overallgetTreeXYMap[0][0]+1;
		actual = returnValues.get( item + "_1").get(0).getTCAN() + 273.15;
		System.out.println(actual);
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		expected = 289.889984;
//		item = overallgetTreeXYMap[0][0]+1;
		actual = returnValues.get(  "2_1").get(1).getTCAN() + 273.15;
		System.out.println(actual);
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
		expected = 289.799988;
//		item = overallgetTreeXYMap[0][0]+1;
		actual = returnValues.get(  "1_2").get(3).getTCAN() + 273.15;
		System.out.println(actual);
		assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		
//	      print *,maespaDataArray(treeXYMap(1,1),1)%maespaOverallDataArray(1)%TCAN+273.15
//	       print *,maespaDataArray(2,1)%maespaOverallDataArray(2)%TCAN+273.15
//	       print *,maespaDataArray(1,2)%maespaOverallDataArray(4)%TCAN+273.15
//	       290.059998    
//	       289.889984    
//	       289.799988   
		
		
//		print *,maespaDataArray(treeXYMap(1,1),1)%maespaOverallDataArray(:)%TCAN+273.15
		double[] expectedDouble = new double[]
				{ 290.059998, 289.889984, 289.829987, 289.799988, 289.729980, 289.660004, 289.459991, 289.380005, 289.320007, 289.299988, 289.289978, 289.359985, 
						290.350006, 
						290.179993, 290.199982, 290.029999, 289.779999, 289.589996, 290.000000, 289.829987, 290.119995, 290.820007, 291.250000, 291.369995, 
						291.809998, 291.869995, 292.139984, 292.220001, 292.339996, 292.470001, 292.639984, 292.600006, 292.750000, 292.880005, 292.729980, 
						292.529999, 292.440002, 292.289978, 290.720001, 290.399994, 290.190002, 289.539978, 288.940002, 288.350006, 287.929993, 287.639984, 
						287.410004, 287.139984, 286.859985, 286.609985, 286.489990, 286.350006, 286.109985, 286.009979, 285.959991, 285.899994, 285.559998, 
						285.449982, 285.519989, 285.519989, 286.299988, 286.869995, 287.109985, 286.929993, 287.109985, 287.209991, 287.839996, 288.519989, 
						288.579987, 289.179993, 289.529999, 289.619995, 289.720001, 290.029999, 290.529999, 289.989990, 289.789978, 289.970001, 289.970001, 
						290.179993, 290.619995, 290.859985, 291.199982, 291.639984, 291.889984, 291.539978, 290.269989, 290.100006, 289.869995, 289.320007, 
						288.720001, 288.399994, 288.199982, 287.919983, 287.720001, 287.459991  };  
		item = overallgetTreeXYMap[0][0];
		for (int i=0;i<expectedDouble.length;i++)
		{			
			actual = returnValues.get( item + "_1").get(i).getTCAN() + 273.15;
			expected = expectedDouble[i];
			System.out.println(i + " " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		}
		
		
		
		

		
		expectedDouble = new double[]
				{ 
//						290.059998, 289.889984, 289.829987, 289.799988, 289.729980, 289.660004, 289.459991, 289.380005, 289.320007, 289.299988, 289.289978, 289.359985, 
//						290.350006, 
//						290.179993, 290.199982, 290.029999, 289.779999, 289.589996, 290.000000, 289.829987, 290.119995, 290.820007, 291.250000, 291.369995, 
//						291.809998, 291.869995, 292.139984, 292.220001, 292.339996, 292.470001, 292.639984, 292.600006, 292.750000, 292.880005, 292.729980, 
//						292.529999, 292.440002, 292.289978, 290.720001, 290.399994, 290.190002, 289.539978, 288.940002, 288.350006, 287.929993, 287.639984, 
//						287.410004, 287.139984, 286.859985, 286.609985, 286.489990, 286.350006, 286.109985, 286.009979, 285.959991, 285.899994, 285.559998, 
//						285.449982, 285.519989, 285.519989, 286.299988, 286.869995, 287.109985, 286.929993, 287.109985, 287.209991, 287.839996, 288.519989, 
//						288.579987, 289.179993, 289.529999, 289.619995, 289.720001, 290.029999, 290.529999, 289.989990, 289.789978, 289.970001, 289.970001, 
//						290.179993, 290.619995, 290.859985, 291.199982, 291.639984, 291.889984, 291.539978, 290.269989, 290.100006, 289.869995, 289.320007, 
//						288.720001, 288.399994, 288.199982, 287.919983, 287.720001, 287.459991  
						290.06,
						289.89,
						289.83,
						289.8,
						289.73,
						289.66,
						289.46,
						289.38,
						289.32,
						289.3,
						289.29,
						289.36,
						288.57,
						288.47,
						288.55,
						288.49,
						288.64,
						288.91,
						289.1,
						289.01,
						289.5,
						290.11,
						290.16,
						290.23,
						290.57,
						290.56,
						290.73,
						290.66,
						290.83,
						290.99,
						290.98,
						291.1,
						291.05,
						290.88,
						290.7,
						290.53,
						290.37,
						290.08,
						290.72,
						290.4,
						290.19,
						289.54,
						288.94,
						288.35,
						287.93,
						287.64,
						287.41,
						287.14,
						286.86,
						286.61,
						286.49,
						286.35,
						286.11,
						286.01,
						285.96,
						285.9,
						285.56,
						285.45,
						285.52,
						285.52,
						284.34,
						284.83,
						285.05,
						285.04,
						285.48,
						286.26,
						286.7,
						287.53,
						287.97,
						288.38,
						288.34,
						288.35,
						288.65,
						288.99,
						289.42,
						288.88,
						288.5,
						288.6,
						288.67,
						289.27,
						289.49,
						289.51,
						289.8,
						290.15,
						290.44,
						290.11,
						290.27,
						290.1,
						289.87,
						289.32,
						288.72,
						288.4,
						288.2,
						287.92,
						287.72,
						287.46
						};  
		item = overallgetTreeXYMap[13][12];
		for (int i=0;i<expectedDouble.length;i++)
		{			
			actual = returnValues.get( item + "_1").get(i).getTCAN() + 273.15;
			expected = expectedDouble[i];
			System.out.println(i + " " + actual + " " + expected);
			assertEquals(expected, actual, Math.abs(expected) / threeDigitDelta);
		}
		
		
		
		
		expectedArray = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0
		};
//		expectedArray = new int[]{5,6,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0
//		};
		
		
		int[][] expected2DArray = reformatArray(expectedArray, 14, 14);
		System.out.println(expected2DArray[0][0]);
		System.out.println(expected2DArray[1][0]);
		
		
		
		
		
		item = overallgetTreeXYMap[1][1];
		System.out.println(item);
		
		item = overallgetTreeXYMap[1][2];
		System.out.println(item);
		
		
//		assertTrue(Arrays.equals(expected2DArray, overallgetTreeXYMap));

	}
	
	public int[][] reformatArray(int[] oneDArray, int width, int length)
	{
		int[][] returnArray = new int[width][length];
		int count = 0;
		for (int i=0;i<width;i++)
		{
			for (int j=0;j<length;j++)
			{
				returnArray[j][i]=oneDArray[count];
				count++;
			}
		}
		
		return returnArray;
	}
	
	@Test
	public void testOverall()
	{
		String rootDirectory = "/home/kerryn/git/2018-03-MasterITProject/VTUF3DTesting/PrestonBaseSmaller/";
		OverallConfiguration overall = new OverallConfiguration(rootDirectory);
		overall.readParametersDat();
		MaespaConfigTreeMapState treeMapFromConfig = overall.readMaespaTreeMapFromConfig(rootDirectory);
		
		HashMap<String,ArrayList<MaespaDataResults>> returnValues = overall.mapTrees(treeMapFromConfig);
		//overall.readAllMaespaData(treeMapFromConfig);
		
		int[][] overallgetTreeXYMap = overall.getTreeXYMap();
		int[][] overallgetTreeXYTreeMap = overall.getTreeXYTreeMap();
		
		actualInt = overallgetTreeXYMap[0][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[1][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[2][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[3][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[4][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[5][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[6][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[7][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[8][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[9][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[10][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[11][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[12][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[13][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYMap[0][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[1][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[2][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[3][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[4][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[5][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[6][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[7][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[8][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[9][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[10][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[11][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[12][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[13][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYMap[0][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[1][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[2][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[3][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[4][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[5][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[6][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[7][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[8][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[9][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[10][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[11][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[12][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[13][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYMap[0][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[1][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[2][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[3][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[4][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[5][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[6][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[7][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[8][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[9][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[10][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[11][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[12][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYMap[13][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		
		
		
		
		
		actualInt = overallgetTreeXYTreeMap[0][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[1][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[2][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[3][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[4][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[5][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[6][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[7][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[8][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[9][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[10][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[11][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[12][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[13][0];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYTreeMap[0][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[1][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[2][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[3][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[4][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[5][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[6][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[7][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[8][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[9][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[10][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[11][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[12][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[13][1];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYTreeMap[0][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[1][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[2][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[3][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[4][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[5][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[6][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[7][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[8][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[9][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[10][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[11][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[12][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[13][2];
		expectedInt = 1;
		assertEquals(expectedInt, actualInt);
		
		actualInt = overallgetTreeXYTreeMap[0][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[1][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[2][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[3][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[4][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[5][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[6][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[7][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[8][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[9][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[10][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[11][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[12][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		actualInt = overallgetTreeXYTreeMap[13][3];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		
		
		actualInt = overallgetTreeXYTreeMap[13][13];
		expectedInt = 0;
		assertEquals(expectedInt, actualInt);
		
		
		
		
		
		
		
		

		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
