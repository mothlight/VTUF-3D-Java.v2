package VTUF3D.ConfigMaker;

//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

public class TUFBldVegHeights
{
	Common common = new Common();
	

	
	private String veght = "1,0,0,0,0,0,0,0,0,0" 
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0"
;
	private String vegtype= "1,0,0,0,0,0,0,0,0,0" 
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0,0,0,0,0,0,0,0"
            + ",0,0,0"
;
	private String bldht = "1,0,0,0,0,0,0,0,0,0" 
	                     + ",0,0,0,0,0,0,0,0,0,0"
	                     + ",0,0,0,0,0,0,0,0,0,0"
	                     + ",0,0,0,0,0,0,0,0,0,0"
	                     + ",0,0,0"
			;

	private String trees;
	
	
	private int bh, bl, bw, sw, sw2, nbuildx, nbuildy, al, aw, a1, a2, b1, b2;
	private double lpactual, hwactual, bhblactual;


	private ArrayList<Integer> calculatedTreeXlocations = new ArrayList<Integer>();
	private ArrayList<Integer> calculatedTreeYlocations = new ArrayList<Integer>();
	private int calculatedNumberOfTrees = 0;
	private int calculatedWidth ;
	private int calculatedLength;
	private int[][] calculatedVeghtIntMatrix ;
	private String calculatedTreeXlocationsStr ;
	private int[][] calculatedVegtypeIntMatrix;
	private int[][] calculatedTreeIntMatrix;
	private String calculatedTreeYlocationsStr ;
	
	private String calculatedPhyConfigNumberStr ;
	private String calculatedStrConfigNumberStr ;
	private String calculatedTreeConfigNumberStr ;
	private String calculatedTreeConfigTreeHeightsStr ;	
	
	private ArrayList<Integer> calculatedBuildingXlocations = new ArrayList<Integer>();
	private ArrayList<Integer> calculatedBuildingYlocations = new ArrayList<Integer>();
	private int calculatedNumberOfBuildings = 0;
	private int[][] calculatedBuildinghtIntMatrix ;
	private String calculatedBuildingXlocationsStr ;
	private String calculatedBuildingYlocationsStr ;
	private String calculatedTreeConfigBuildingHeightsStr;
	
	private String calculatedTreesXLocationsStr;
	private String calculatedTreesYLocationsStr;
	private String calculatedTreeConfigTreesStr;
	
	private double treePercentage ;
	private double grassPercentage;
	private double buildingPercentage ;
	private double streetPercentage;
	private double domainArea ;
	
	
	public double buildingHeightAve  ;
	public double vegetationHeightAve ;	
	public double buildingHeightDomainAve ;
	public double vegetationHeightDomainAve ;
	
	
	ArrayList<Integer> treeNumberXlocations = new ArrayList<Integer>();
	ArrayList<Integer> treeNumberYlocations = new ArrayList<Integer>();
	
	TreeMap<String, Integer> configFileVariations = new TreeMap<String, Integer>();
	public static final int OLIVE_CONFIG_TYPE = 1;
	public static final int GRASS_CONFIG_TYPE = 2;
	public static final int BRUSHBOX_CONFIG_TYPE = 3;
	public static final int OLD_GRASS_CONFIG_TYPE = 4;
	public static final int IRRIGATED_GRASS_CONFIG_TYPE = 5;
	
	
	public TUFBldVegHeights()
	{
		super();
//		init();
	}
	


	public static void main(String[] args)
	{

	}
	
	public void createTUFBuildingVegHeightConfFile(String file, int[][] data)
	{
		StringBuffer sb = new StringBuffer();
	
		int aw = data.length;
		int al = data[0].length; 
		
		sb.append(aw + "\r\n");
		sb.append(al + "\r\n");
		
		for (int i=0;i<aw;i++)
		{
			for (int j=0;j<al;j++)
			{
				sb.append(data[j][i] + ",");
			}
		}
		sb.replace(sb.length()-1, sb.length(), "");
//		try
//		{
			//System.out.println(sb.toString());
			common.writeFile(sb.toString(),file );
//		}
//		catch (IOException e)
//		{			
//			e.printStackTrace();
//		}
		
	}
	
	public void process()
	{
//		ArrayList<Integer> buildingSpacing = calculateSpacing(5,4,77);
		
		//int[][] buildingHeightArray = barray_cube(bw, bl, sw, sw2, al, aw, bh, convertStringTo2DIntArray(bldht, al, aw));
		int[][] buildingHeightArray = barray_cube_replacement(bw, bl, sw, sw2, al, aw, bh);
		//printArray(buildingHeightArray);
		
		int [][] vegetationHeightArray = createVegHeight(buildingHeightArray);
		//printArray(vegetationHeightArray);	
		
		createTUFBuildingVegHeightConfFile("/home/kerryn/workspace/TUF-radiationOnly/data_bldht_gen.rdat", buildingHeightArray);
		createTUFBuildingVegHeightConfFile("/home/kerryn/workspace/TUF-radiationOnly/data_veght_gen.rdat", vegetationHeightArray);
		
	}
	
	public void vegetationArrayPreparation(int[][] buildinghtIntMatrix, int[][] vegheightIntMatrix, int[][] vegTypeIntMatrix, int[][] treesIntMatrix, double gridSizeInMeters)
	{
		StringBuffer calculatedPhyConfigNumberStrBuff = new StringBuffer();
		StringBuffer calculatedStrConfigNumberStrBuff = new StringBuffer();
		StringBuffer calculatedTreeConfigNumberStrBuff = new StringBuffer();
		StringBuffer calculatedTreeConfigTreeHeightBuff = new StringBuffer();
		StringBuffer calculatedTreeConfigBuildingHeightBuff = new StringBuffer();
		StringBuffer calculatedTreeConfigTreesBuff = new StringBuffer();
		
		int configNumber = 1;
		
		StringBuffer bldhtBuffer = new StringBuffer();
		StringBuffer veghtBuffer = new StringBuffer();
		StringBuffer vegTypeBuffer = new StringBuffer();
		StringBuffer treesBuffer = new StringBuffer();
		for (int i=0;i<buildinghtIntMatrix.length;i++)
		{
			for (int j=0;j<buildinghtIntMatrix[0].length;j++)
			{
				bldhtBuffer.append(buildinghtIntMatrix[i][j] + ",");
				//System.out.print(buildinghtIntMatrix[i][j] + ",");
				veghtBuffer.append(vegheightIntMatrix[i][j] + ",");
				vegTypeBuffer.append(vegTypeIntMatrix[i][j] + ",");
				treesBuffer.append(treesIntMatrix[i][j] + ",");
			}			
		}
				
		bldhtBuffer.deleteCharAt(bldhtBuffer.length()-1);
		bldht=bldhtBuffer.toString();
		veghtBuffer.deleteCharAt(veghtBuffer.length()-1);
		veght=veghtBuffer.toString();
		vegTypeBuffer.deleteCharAt(vegTypeBuffer.length()-1);
		vegtype=vegTypeBuffer.toString();
		
		treesBuffer.deleteCharAt(treesBuffer.length()-1);
		trees=treesBuffer.toString();
		
		String[] veghtStrArray = veght.split(",");
		String[] vegtypeStrArray = vegtype.split(",");
		String[] buildinghtStrArray = bldht.split(",");
		String[] treesStrArray = trees.split(",");
		
		ArrayList<Integer> treeXlocations = new ArrayList<Integer>();
		ArrayList<Integer> treeYlocations = new ArrayList<Integer>();
		int numberOfTrees = 0;
		int width = (int)Math.round(  Math.sqrt(veghtStrArray.length) );
		int length = width;
		
		int numberOfGrassGrids = 0;
		int numberOfTreeGrids = 0;
		
		int numberOfBuildings = 0;
		ArrayList<Integer> buildingXlocations = new ArrayList<Integer>();
		ArrayList<Integer> buildingYlocations = new ArrayList<Integer>();
		
		ArrayList<Integer> treeNumberXlocations = new ArrayList<Integer>();
		ArrayList<Integer> treeNumberYlocations = new ArrayList<Integer>();
		
//		int aw = width;
//		int al = height;
		int[][] veghtIntMatrix = new int[width][length];
		int[][] vegtypeIntMatrix = new int[width][length];
		int[][] treeIntMatrix =  new int[width][length];
		
		//int[][] buildinghtIntMatrix = new int[width][length];
		
		
		int count =0;
		double buildingHeightTotal = 0;
		double vegHeightTotal = 0;
		
		for (int i=0;i<width;i++)
		{
			for (int j=0;j<length;j++)
			{
				int tempHeight = new Integer(veghtStrArray[count]).intValue();
				int tempType = new Integer(vegtypeStrArray[count]).intValue();
				int tempBuildingHeight = new Integer(buildinghtStrArray[count]).intValue();
				int tempTree = new Integer(treesStrArray[count]).intValue();
				veghtIntMatrix[i][j]=tempHeight;
				vegtypeIntMatrix[i][j]=tempType;
				treeIntMatrix[i][j]=tempTree;
				//buildinghtIntMatrix[i][j]=tempBuildingHeight;
				count++;	
				
				if (tempHeight>0 || tempType > 0)
				{
					numberOfTrees++;
					if (tempType == GRASS_CONFIG_TYPE || tempType == IRRIGATED_GRASS_CONFIG_TYPE)
					{
						numberOfGrassGrids++;
					}
					else
					{
						numberOfTreeGrids++;
						vegHeightTotal += tempHeight;
					}
					// fortran isn't 0 based
					treeXlocations.add(i);
					treeYlocations.add(j);					
					//check to see if a config exists
					String configTypeHeight = tempHeight + "_" + tempType;					
					if (configFileVariations.containsKey(configTypeHeight))
					{
						//use it after the else
					}
					else
					{
						//create a new config						
						configFileVariations.put(configTypeHeight, configNumber);
						configNumber++;
					}
					Integer keyValue = configFileVariations.get(configTypeHeight);
					//phy and str same
					calculatedPhyConfigNumberStrBuff.append(keyValue + " ");
					calculatedStrConfigNumberStrBuff.append(keyValue + " ");
					//tree has diff height
					calculatedTreeConfigNumberStrBuff.append(keyValue + " ");
					calculatedTreeConfigTreeHeightBuff.append(tempHeight + " ");
					calculatedTreeConfigTreesBuff.append(tempTree + " ");
				}
				
				if (tempBuildingHeight>0)
				{
					numberOfBuildings++;
					buildingHeightTotal += tempBuildingHeight;
					// fortran isn't 0 based
					buildingXlocations.add(i);
					buildingYlocations.add(j);					
					//check to see if a config exists
//					String configTypeHeight = tempHeight + "_" + tempType;					
//					if (configFileVariations.containsKey(configTypeHeight))
//					{
//						//use it after the else
//					}
//					else
//					{
//						//create a new config						
//						configFileVariations.put(configTypeHeight, configNumber);
//						configNumber++;
//					}
//					Integer keyValue = configFileVariations.get(configTypeHeight);
//					//phy and str same
//					calculatedPhyConfigNumberStrBuff.append(keyValue + " ");
//					calculatedStrConfigNumberStrBuff.append(keyValue + " ");
//					//tree has diff height
//					calculatedTreeConfigNumberStrBuff.append(keyValue + " ");
//					calculatedTreeConfigTreeHeightBuff.append(tempHeight + " ");
					calculatedTreeConfigBuildingHeightBuff.append(tempBuildingHeight + " ");
				}
				
				
//				if (tempTree != 0 && tempType > 0)
////				if ( tempType > 0) // mapping positive numbers to trunk location and negative to the overhanging canopy
//				{
//					
//					// fortran isn't 0 based
//					treeNumberXlocations.add(i);
//					treeNumberYlocations.add(j);					
//
//					calculatedTreeConfigTreesBuff.append(tempTree + " ");
//				}
				
				
			}
		}		
		
		setCalculatedLength(length);
		setCalculatedWidth(width);
		setCalculatedNumberOfTrees(numberOfTrees);
		setCalculatedTreeXlocations(treeXlocations);
		setCalculatedTreeYlocations(treeYlocations);
		setCalculatedVeghtIntMatrix(veghtIntMatrix);
		setCalculatedVegtypeIntMatrix(vegtypeIntMatrix);
		setCalculatedTreeIntMatrix(treeIntMatrix);
		setCalculatedTreeXlocationsStr(getStringArrayOfArrayList(treeXlocations));
		setCalculatedTreeYlocationsStr(getStringArrayOfArrayList(treeYlocations));

		setCalculatedPhyConfigNumberStr(calculatedPhyConfigNumberStrBuff.toString());
		setCalculatedStrConfigNumberStr(calculatedStrConfigNumberStrBuff.toString());
		setCalculatedTreeConfigNumberStr(calculatedTreeConfigNumberStrBuff.toString());	
		setCalculatedTreeConfigTreeHeightsStr(calculatedTreeConfigTreeHeightBuff.toString());
		
		setCalculatedNumberOfBuildings(numberOfBuildings);
		setCalculatedBuildingXlocations(buildingXlocations);
		setCalculatedBuildingYlocations(buildingYlocations);
		setCalculatedBuildingXlocationsStr(getStringArrayOfArrayList(buildingXlocations));
		setCalculatedBuildingYlocationsStr(getStringArrayOfArrayList(buildingYlocations));
		setCalculatedBuildinghtIntMatrix(buildinghtIntMatrix);
		setCalculatedTreeConfigBuildingHeightsStr(calculatedTreeConfigBuildingHeightBuff.toString());
		
		setCalculatedTreeNumberXlocations(treeNumberXlocations);
		setCalculatedTreeNumberYlocations(treeNumberYlocations);
		setCalculatedTreeNumberXlocationsStr(getStringArrayOfArrayList(treeNumberXlocations));
		setCalculatedTreeNumberYlocationsStr(getStringArrayOfArrayList(treeNumberYlocations));
		setCalculatedTreeConfigTreesStr(calculatedTreeConfigTreesBuff.toString());
		
		domainArea = width * length;
		//double treePercentage = numberOfTrees / domainArea;
		treePercentage = common.roundToDecimals(numberOfTreeGrids / domainArea, 3);
		grassPercentage = common.roundToDecimals(numberOfGrassGrids / domainArea, 3);
		buildingPercentage = common.roundToDecimals(numberOfBuildings / domainArea, 3);
		streetPercentage = common.roundToDecimals(1 - treePercentage - grassPercentage - buildingPercentage, 3);
		
		buildingHeightAve = buildingHeightTotal / numberOfBuildings * gridSizeInMeters ;
		vegetationHeightAve = vegHeightTotal / numberOfTreeGrids * gridSizeInMeters;
		
		buildingHeightDomainAve = buildingHeightTotal / domainArea * gridSizeInMeters ;
		vegetationHeightDomainAve = vegHeightTotal / domainArea * gridSizeInMeters;
		
		System.out.println("Percentages: ");
		//System.out.println("Trees: " + treePercentage);
		System.out.println("Trees: " + treePercentage);
		System.out.println("Grass: " + grassPercentage);
		System.out.println("Building: " + buildingPercentage);
		System.out.println("Streets: " + streetPercentage);
	
		
		System.out.println("Ave building height: " + buildingHeightAve);
		System.out.println("Ave vegetation height: " + vegetationHeightAve);
		
		System.out.println("Ave domain building height: " + buildingHeightDomainAve);
		System.out.println("Ave domain vegetation height: " + vegetationHeightDomainAve);
	}	
	

	
	public String getStringArrayOfArrayList(ArrayList<Integer> array)
	{
		StringBuffer sb = new StringBuffer();
		
		for (Integer value : array)
		{
			sb.append(value + " ");
		}		
		
		return sb.toString();
	}
	
	

	
	public int[][] createVegHeight(int[][] buildingHeightArray)
	{
		int aw = buildingHeightArray.length;
		int al = buildingHeightArray[0].length;
		int[][] veght = new int[aw][al];
		
		int count =0;
		
		for (int i=0;i<aw;i++)
		{
			for (int j=0;j<al;j++)
			{
				if (buildingHeightArray[i][i] == 0)
				{
					if (count%5 == 0)
					{
						veght[i][j]=7;
					}
					count++;
				}
				
				
			}
		}
		
		return veght;
	}
	
	public ArrayList<Integer> calculateSpacing(int buildingWidth, int streetWidth, int totalWidth)
	{
		int remainingBuilding = 0;
		int remainingStreet = 0;
		
		ArrayList<Integer> buildings = new ArrayList<Integer>();
		for (int i=0;i<totalWidth;i++)
		{
			if (i==0)
			{
				//skip first row
			}
			else if (i==1)
			{
				remainingBuilding = buildingWidth;
				remainingStreet = streetWidth-1;
				buildings.add(new Integer(i));
				remainingBuilding--;
			}
			else
			{
				if (remainingBuilding > 0)
				{
					buildings.add(new Integer(i));
					remainingBuilding--;
				}
				else
				{
					if (remainingStreet > 0)
					{
						remainingStreet--;
					}
					else
					{
						remainingBuilding = buildingWidth;
						remainingStreet = streetWidth-1;
					}
					
				}
			}
			
		}
		return buildings;
	}
	
	public int[][] barray_cube_replacement(int bw, int bl, int sw, int sw2, int al, int aw, int bh)
	{
		
		int[][] bldht = new int[aw][al];
		
		ArrayList<Integer> vertBuildingSpacing =  calculateSpacing(bl,sw2,al);
		ArrayList<Integer> hortzBuildingSpacing =  calculateSpacing(bw,sw,aw);

		for (int i=0;i<aw;i++)
		{	
			
			for (int j=0;j<al;j++)
			{
				if (vertBuildingSpacing.contains(i) && hortzBuildingSpacing.contains(j))
				{
					bldht[i][j]=bh;
				}
			}
		
		}
		return bldht;
	}
	

	
	public void countItems(String items)
	{
		String[] itemsArray = items.split(",");
		int length = itemsArray.length;
		double xy = Math.sqrt(length);
		System.out.println(length +  " " + xy);
	}
	

	
	public void printArrays(String content)
	{
		String[] contents = content.split("\n");
		
		String xStr = contents[0];
		String yStr = contents[1];
		String data = contents[2];
		
		int x = new Integer(xStr).intValue();
		int y = new Integer(yStr).intValue();
		
		String[] dataArray = data.split(",");
		

		int[][] dataIntArray = convert1DArrayTo2D(convert1DStringArrayTo1DIntArray(dataArray), x, y);
		printArray(dataIntArray);

	}
	
	public void printArray(int[][] dataIntArray)
	{
		for (int i=0;i<dataIntArray.length;i++)
		{
			String res = Arrays.toString(dataIntArray[i]);
			System.out.println(res);
		}
	}
	
	public int[][] convertStringTo2DIntArray(String data, int x, int y)
	{		
		return convert1DArrayTo2D(convert1DStringArrayTo1DIntArray(convertStringTo1DIntArray(data)), x, y);
	}
	
	public String[] convertStringTo1DIntArray(String data)
	{
		String[] dataArray = data.split(",");
		return dataArray;
	}
	
	public int[] convert1DStringArrayTo1DIntArray(String[] dataArray)
	{
		int[] dataIntArray = new int[dataArray.length];
		int count = 0;
		for (String item : dataArray)
		{
			dataIntArray[count]=new Integer(dataArray[count]).intValue();
			count ++;
		}
		return dataIntArray;
		
	}
	
	public int[][] convert1DArrayTo2D(int[] dataArray, int x, int y)
	{
		int[][] dataIntArray = new int[x][y];
		int count = 0;
		for (int i=0;i<x;i++)
		{
			for (int j=0;j<y;j++)
			{
				dataIntArray[i][j]=dataArray[count];
				count++;
			}
		}
		return dataIntArray;
	}
	

	
	/**
	 * @param bw Building width in y (AW) direction
	 * @param bl Building length in x (AL) direction
	 * @param sw street width in x direction
	 * @param sw2 street width in y direction
	 * @param al
	 * @param aw
	 * @param bh
	 * @param bldht
	 * @return
	 */
	public int[][] barray_cube(int bw, int bl, int sw, int sw2, int al, int aw,
			int bh, int[][] bldht)
	{

		int x, y;

		// ! BW - Building width in y (AW) direction
		// ! BL - Building length in x (AL) direction
		// ! SW - street width in x directioon
		// ! SW2 - street width in y direction

		y = 1;

		boolean loop1 = true;
		boolean loop5 = true;
		while (loop1) // 1 loop
		{

			// ! now do the loop for one building
			x = 1;

			for (int ya = 1; ya <= bw; ya++)
			{
				if (y > aw)
				{
					loop5 = false;
					loop1 = false;
				}
				else
				{
					loop5 = true;
					loop1 = true;
				}
				while (loop5)
				{
					for (int xa = 1; xa <= bl; xa++)
					{
						if (x > al)
						{
							loop5 = false;
							break;
						}
						else
						{
							loop5 = true;
						}
						if (loop5)
						{
							bldht[x][y] = bh;
							x = x + 1;
						}
					}
					// ! now comes a street
					for (int xc = 1; xc <= sw; xc++)
					{
						if (x > al)
						{
							loop5 = false;
							break;
						}
						else
						{
							loop5 = true;
						}
						if (loop5)
						{
							bldht[x][y] = 0;
							x = x + 1;
						}
					}
					// ! now the pattern repeats
				}
				x = 1;
				y = y + 1;
			}

			// ! now insert the street
			for (int yb = 1; yb <= sw2; yb++)
			{
				x = 1;
				if (y > aw)
				{
					return bldht;
				}
				for (int xd = 1; xd <= al + 1; xd++)
				{
					if (x > al)
					{
						break;
					}
					bldht[x][y] = 0;

					x = x + 1;
				}
				y = y + 1;
			}

			loop5 = true;
		}
		// ! end the height loop
		return bldht;

	}

	public String getVeght()
	{
		return veght;
	}

	public void setVeght(String veght)
	{
		this.veght = veght;
	}

	public String getBldht()
	{
		return bldht;
	}

	public void setBldht(String bldht)
	{
		this.bldht = bldht;
	}

	public int getBh()
	{
		return bh;
	}

	public void setBh(int bh)
	{
		this.bh = bh;
	}

	public int getBl()
	{
		return bl;
	}

	public void setBl(int bl)
	{
		this.bl = bl;
	}

	public int getBw()
	{
		return bw;
	}

	public void setBw(int bw)
	{
		this.bw = bw;
	}

	public int getSw()
	{
		return sw;
	}

	public void setSw(int sw)
	{
		this.sw = sw;
	}

	public int getSw2()
	{
		return sw2;
	}

	public void setSw2(int sw2)
	{
		this.sw2 = sw2;
	}

	public int getNbuildx()
	{
		return nbuildx;
	}

	public void setNbuildx(int nbuildx)
	{
		this.nbuildx = nbuildx;
	}

	public int getNbuildy()
	{
		return nbuildy;
	}

	public void setNbuildy(int nbuildy)
	{
		this.nbuildy = nbuildy;
	}

	public int getAl()
	{
		return al;
	}

	public void setAl(int al)
	{
		this.al = al;
	}

	public int getAw()
	{
		return aw;
	}

	public void setAw(int aw)
	{
		this.aw = aw;
	}

	public int getA1()
	{
		return a1;
	}

	public void setA1(int a1)
	{
		this.a1 = a1;
	}

	public int getA2()
	{
		return a2;
	}

	public void setA2(int a2)
	{
		this.a2 = a2;
	}

	public int getB1()
	{
		return b1;
	}

	public void setB1(int b1)
	{
		this.b1 = b1;
	}

	public int getB2()
	{
		return b2;
	}

	public void setB2(int b2)
	{
		this.b2 = b2;
	}

	public double getLpactual()
	{
		return lpactual;
	}

	public void setLpactual(double lpactual)
	{
		this.lpactual = lpactual;
	}

	public double getHwactual()
	{
		return hwactual;
	}

	public void setHwactual(double hwactual)
	{
		this.hwactual = hwactual;
	}

	public double getBhblactual()
	{
		return bhblactual;
	}

	public void setBhblactual(double bhblactual)
	{
		this.bhblactual = bhblactual;
	}



	public ArrayList<Integer> getCalculatedTreeXlocations()
	{
		return calculatedTreeXlocations;
	}



	public void setCalculatedTreeXlocations(
			ArrayList<Integer> calculatedTreeXlocations)
	{
		this.calculatedTreeXlocations = calculatedTreeXlocations;
	}



	public ArrayList<Integer> getCalculatedTreeYlocations()
	{
		return calculatedTreeYlocations;
	}



	public void setCalculatedTreeYlocations(
			ArrayList<Integer> calculatedTreeYlocations)
	{
		this.calculatedTreeYlocations = calculatedTreeYlocations;
	}



	public int getCalculatedNumberOfTrees()
	{
		return calculatedNumberOfTrees;
	}



	public void setCalculatedNumberOfTrees(int calculatedNumberOfTrees)
	{
		this.calculatedNumberOfTrees = calculatedNumberOfTrees;
	}



	public int getCalculatedWidth()
	{
		return calculatedWidth;
	}



	public void setCalculatedWidth(int calculatedWidth)
	{
		this.calculatedWidth = calculatedWidth;
	}



	public int getCalculatedLength()
	{
		return calculatedLength;
	}



	public void setCalculatedLength(int calculatedLength)
	{
		this.calculatedLength = calculatedLength;
	}



	public int[][] getCalculatedVeghtIntMatrix()
	{
		return calculatedVeghtIntMatrix;
	}



	public void setCalculatedVeghtIntMatrix(int[][] calculatedVeghtIntMatrix)
	{
		this.calculatedVeghtIntMatrix = calculatedVeghtIntMatrix;
	}



	public int[][] getCalculatedVegtypeIntMatrix()
	{
		return calculatedVegtypeIntMatrix;
	}



	public void setCalculatedVegtypeIntMatrix(int[][] calculatedVegtypeIntMatrix)
	{
		this.calculatedVegtypeIntMatrix = calculatedVegtypeIntMatrix;
	}



	public String getCalculatedTreeXlocationsStr()
	{
		return calculatedTreeXlocationsStr;
	}



	public void setCalculatedTreeXlocationsStr(String calculatedTreeXlocationsStr)
	{
		this.calculatedTreeXlocationsStr = calculatedTreeXlocationsStr;
	}



	public String getCalculatedTreeYlocationsStr()
	{
		return calculatedTreeYlocationsStr;
	}



	public void setCalculatedTreeYlocationsStr(String calculatedTreeYlocationsStr)
	{
		this.calculatedTreeYlocationsStr = calculatedTreeYlocationsStr;
	}



	public String getCalculatedPhyConfigNumberStr()
	{
		return calculatedPhyConfigNumberStr;
	}



	public void setCalculatedPhyConfigNumberStr(String calculatedPhyConfigNumberStr)
	{
		this.calculatedPhyConfigNumberStr = calculatedPhyConfigNumberStr;
	}



	public String getCalculatedStrConfigNumberStr()
	{
		return calculatedStrConfigNumberStr;
	}



	public void setCalculatedStrConfigNumberStr(String calculatedStrConfigNumberStr)
	{
		this.calculatedStrConfigNumberStr = calculatedStrConfigNumberStr;
	}



	public String getCalculatedTreeConfigNumberStr()
	{
		return calculatedTreeConfigNumberStr;
	}



	public void setCalculatedTreeConfigNumberStr(
			String calculatedTreeConfigNumberStr)
	{
		this.calculatedTreeConfigNumberStr = calculatedTreeConfigNumberStr;
	}



	public TreeMap<String, Integer> getConfigFileVariations()
	{
		return configFileVariations;
	}



	public void setConfigFileVariations(
			TreeMap<String, Integer> configFileVariations)
	{
		this.configFileVariations = configFileVariations;
	}


	public String getCalculatedTreeConfigTreeHeightsStr()
	{
		return calculatedTreeConfigTreeHeightsStr;
	}



	public void setCalculatedTreeConfigTreeHeightsStr(
			String calculatedTreeConfigTreeHeightsStr)
	{
		this.calculatedTreeConfigTreeHeightsStr = calculatedTreeConfigTreeHeightsStr;
	}



	public String getCalculatedTreeConfigBuildingHeightsStr()
	{
		return calculatedTreeConfigBuildingHeightsStr;
	}



	public void setCalculatedTreeConfigBuildingHeightsStr(
			String calculatedTreeConfigBuildingHeightsStr)
	{
		this.calculatedTreeConfigBuildingHeightsStr = calculatedTreeConfigBuildingHeightsStr;
	}



	public int getCalculatedNumberOfBuildings()
	{
		return calculatedNumberOfBuildings;
	}



	public void setCalculatedNumberOfBuildings(int calculatedNumberOfBuildings)
	{
		this.calculatedNumberOfBuildings = calculatedNumberOfBuildings;
	}



	public int[][] getCalculatedBuildinghtIntMatrix()
	{
		return calculatedBuildinghtIntMatrix;
	}



	public void setCalculatedBuildinghtIntMatrix(
			int[][] calculatedBuildinghtIntMatrix)
	{
		this.calculatedBuildinghtIntMatrix = calculatedBuildinghtIntMatrix;
	}



	public String getCalculatedBuildingXlocationsStr()
	{
		return calculatedBuildingXlocationsStr;
	}



	public void setCalculatedBuildingXlocationsStr(
			String calculatedBuildingXlocationsStr)
	{
		this.calculatedBuildingXlocationsStr = calculatedBuildingXlocationsStr;
	}



	public String getCalculatedBuildingYlocationsStr()
	{
		return calculatedBuildingYlocationsStr;
	}



	public void setCalculatedBuildingYlocationsStr(
			String calculatedBuildingYlocationsStr)
	{
		this.calculatedBuildingYlocationsStr = calculatedBuildingYlocationsStr;
	}



	public ArrayList<Integer> getCalculatedBuildingXlocations()
	{
		return calculatedBuildingXlocations;
	}



	public void setCalculatedBuildingXlocations(
			ArrayList<Integer> calculatedBuildingXlocations)
	{
		this.calculatedBuildingXlocations = calculatedBuildingXlocations;
	}



	public ArrayList<Integer> getCalculatedBuildingYlocations()
	{
		return calculatedBuildingYlocations;
	}



	public void setCalculatedBuildingYlocations(
			ArrayList<Integer> calculatedBuildingYlocations)
	{
		this.calculatedBuildingYlocations = calculatedBuildingYlocations;
	}



	public int[][] getCalculatedTreeIntMatrix()
	{
		return calculatedTreeIntMatrix;
	}



	public void setCalculatedTreeIntMatrix(int[][] calculatedTreeIntMatrix)
	{
		this.calculatedTreeIntMatrix = calculatedTreeIntMatrix;
	}



	public ArrayList<Integer> getCalculatedTreeNumberXlocations()
	{
		return treeNumberXlocations;
	}



	public void setCalculatedTreeNumberXlocations(ArrayList<Integer> treeNumberXlocations)
	{
		this.treeNumberXlocations = treeNumberXlocations;
	}



	public ArrayList<Integer> getCalculatedTreeNumberYlocations()
	{
		return treeNumberYlocations;
	}



	public void setCalculatedTreeNumberYlocations(ArrayList<Integer> treeNumberYlocations)
	{
		this.treeNumberYlocations = treeNumberYlocations;
	}



	public String getCalculatedTreeNumberXlocationsStr()
	{
		return calculatedTreesXLocationsStr;
	}



	public void setCalculatedTreeNumberXlocationsStr(String calculatedTreesXLocationsStr)
	{
		this.calculatedTreesXLocationsStr = calculatedTreesXLocationsStr;
	}



	public String getCalculatedTreeNumberYlocationsStr()
	{
		return calculatedTreesYLocationsStr;
	}



	public void setCalculatedTreeNumberYlocationsStr(String calculatedTreesYLocationsStr)
	{
		this.calculatedTreesYLocationsStr = calculatedTreesYLocationsStr;
	}



	public String getCalculatedTreeConfigTreesStr()
	{
		return calculatedTreeConfigTreesStr;
	}



	public void setCalculatedTreeConfigTreesStr(String calculatedTreeConfigTreesStr)
	{
		this.calculatedTreeConfigTreesStr = calculatedTreeConfigTreesStr;
	}



	public double getTreePercentage()
	{
		return treePercentage;
	}



	public void setTreePercentage(double treePercentage)
	{
		this.treePercentage = treePercentage;
	}



	public double getGrassPercentage()
	{
		return grassPercentage;
	}



	public void setGrassPercentage(double grassPercentage)
	{
		this.grassPercentage = grassPercentage;
	}



	public double getBuildingPercentage()
	{
		return buildingPercentage;
	}



	public void setBuildingPercentage(double buildingPercentage)
	{
		this.buildingPercentage = buildingPercentage;
	}



	public double getStreetPercentage()
	{
		return streetPercentage;
	}



	public void setStreetPercentage(double streetPercentage)
	{
		this.streetPercentage = streetPercentage;
	}



	public double getDomainArea()
	{
		return domainArea;
	}



	public void setDomainArea(double domainArea)
	{
		this.domainArea = domainArea;
	}
	

}
