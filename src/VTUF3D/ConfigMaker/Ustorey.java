package VTUF3D.ConfigMaker;

public class Ustorey
{

	public String sample = "******* Sample USTOREY File *******" + '\n' +
			"" + '\n' +
			"&CONTROL" + '\n' +
			"NOUSPOINTS = 1" + '\n' +
			"X0 = 0" + '\n' +
			"XMAX = 1" + '\n' +
			"Y0 = 0" + '\n' +
			"YMAX = 1" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&ALLHTCROWN" + '\n' +
			"NODATES = 1" + '\n' +
			"VALUES = 0.01" + '\n' +
			"DATES = '01/01/98'" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&ALLUSLAI" + '\n' +
			"NODATES = 1		" + '\n' +															
			"VALUES = 0.01" + '\n' +
			"DATES = '01/01/98'" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&LIA			" + '\n' +		
			"ELP = 1.0			" + '\n' +		
			"NALPHA = 5				" + '\n' +	
			"/					" + '\n' +
			"" + '\n' +
			"&PHYCON" + '\n' +
			"C4FRAC = 0.5" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&JMAXPARS" + '\n' +
			"IECO = 0" + '\n' +
			"AJQ = 0.054" + '\n' +
			"eavj = 43790" + '\n' +
			"edvj = 200000" + '\n' +
			"delsj = 644.4338" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&VCMAXPARS" + '\n' +
			"eavc = 51560" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&BEWDYPARS" + '\n' +
			"JMAX25 = 17.1" + '\n' +
			"VCMAX25 = 12.5" + '\n' +
			"UNMIN =0.0" + '\n' +
			"ABSRP = 0.85" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&CICA" + '\n' +
			"CICARAT = 0.7" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&ALLFOLN" + '\n' +
			"NODATES = 1" + '\n' +
			"VALUES =  1.0" + '\n' +
			"DATES = '01/01/98'" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&BBGS" + '\n' +
			"G0 = 0.03" + '\n' +
			"G1 = 6" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&RDPARS" + '\n' +
			"RD = 0.36" + '\n' +
			"RDK = 0.0693" + '\n' +
			"RDT = 20" + '\n' +
			"/" + '\n' +
			"" + '\n' +
			"&USC4PARS" + '\n' +
			"VCMAXC4 = 40" + '\n' +
			"CICAC4 = 0.5" + '\n' +
			"EAVCC4 = 67294.0" + '\n' +
			"EDVCC4 = 144568.0" + '\n' +
			"DELSCC4 = 472.0" + '\n' +
			"/" + '\n' +
			""	;

	public String getSample()
	{
		return sample;
	}

	public void setSample(String sample)
	{
		this.sample = sample;
	}	
	
}
