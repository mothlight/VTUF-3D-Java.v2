package VTUF3D;

public class MaespaConfigState
{
//	MODULE MaespaConfigState
//	   
//    //USE maestcom, only : MAXSOILLAY, MAXT, MAXANG, MAXDATE, MAXP, MAXC, MAXHRS, MAXLAY, MAXMET, MAXSP, MAXHISTO
//    use switches
//    USE maindeclarations
//        
//    IMPLICIT NONE
//    
//
//    type maespaDataResults
//        
//        double transmissionPercentage
//        double maespaWatQh
//        double maespaWatQe
//        double maespaWatQn
//        double maespaWatQc
//        double lai
//        double maespaLe
//        int x
//        int y
//        int z
//        int f
//        double timeis
//        int yd_actual
//        double maespaPar
//        double maespaTcan
//        double etInMM
////        double leFromEt
////        double leFromEt2
////        double leFromHrLe
////        double qeCalc
////        double qeCalc2
////        double qeCalc3
////        double qeCalc4
//        double qeCalc5
////        double qhCalc
////        double deltaQveg
//        
//        //double watday
//        //double wathour
//        //double wsoil
//        //double wsoilroot
//        //double ppt
//        double canopystore
//        double evapstore
//        double drainstore
//        //double tfall
//        double et
//        //double etmeas
//        //double discharge
//        //double overflow
//        //double weightedswp
//        //double ktot
//        //double drythick
//        double soilevap
//        //double soilmoist
//        //double fsoil
//        double qh
//        double qe
//        double qn
//        double qc
//        //double rglobund
//        //double rglobabv
//        //double radinterc
//        double rnet
//        //double totlai
//        //double wattair
//        double soilt1
//        //double soilt2
//        //double fracw1
//        //double fracw2
//        //double fracaPAR
//        
//        //double DOY
//        //double Tree
//        //double Spec
//        //double HOUR
//        //double hrPAR
//        //double hrNIR
//        double hrTHM
//        //double hrPs
//        //double hrRf
//        //double hrRmW
//        double hrLE
//        //double LECAN
//        //double Gscan
//        //double Gbhcan
//        //double hrH
//        double TCAN
//        //double ALMAX
//        //double PSIL
//        //double PSILMIN
//        //double CI
//        //double TAIR
//        //double VPD
//        //double PAR
//        //double ZEN
//        //double AZ
//        
//        
////        //uspar.dat
////        double usparday 
////        double usparhour 
////        double usparpoint 
////        double usparX 
////        double usparY 
////        double usparZ 
////        double usparPARbeam 
////        double usparPARdiffuse 
////        double usparPARtotal 
////        double usparAPAR 
////        double usparhrPSus 
////        double usparhrETus
////        double leFromUspar
//        
//    end type maespaDataResults
//    
//    
//    type maesapaTestDataResults
//    
//        double testDAY
//        double testHR
//        double testPT
//        double testX
//        double testY
//        double testZ
//        double testPAR
//        double testFBEAM
//        double testSUNLA
//        double testTD
//        double testTSCAT
//        double testTTOT
//        double testAPARSUN
//        double testAPARSH
//        double testAPAR
//        
//    end type maesapaTestDataResults
    
//    type maespaArrayOfDataResults
//        TYPE(maespaDataResults),allocatable,dimension(:) :: maespaOverallDataArray
//    end type maespaArrayOfDataResults
    
//    type maespaArrayOfTestDataResults
//        TYPE(maesapaTestDataResults),allocatable,dimension(:) :: maespaOverallTestDataArray
//    end type maespaArrayOfTestDataResults
    
    
    
//    type maespaConfigvariablesstate
//// MAXLAY = max number of layers in crowns
//        
//        double WLEAFTABLE(maxdate)
//        int DATESWLEAF(maxdate)
//        int NOWLEAFDATES
//        double WLEAFTABLESPEC(maxdate,MAXSP)
//        int DATESWLEAFSPEC(maxdate,MAXSP)
//        int NOWLEAFDATESSPEC(MAXSP)
//        
//        int DATESLIA(maxdate,maxsp), NOLIADATES(maxsp),DATESLAD(maxdate,maxsp),NOLADDATES(maxsp)
//        double BPT(8,MAXC,MAXSP,maxdate)
//     
//     double TRUNK(maxdate,MAXT),FLT(maxdate,MAXT)
//     double R1(maxdate,MAXT),R2(maxdate,MAXT),R3(maxdate,MAXT)
//     double DIAMA(maxdate,MAXT)
//     double TU(MAXP)
//     int NUMTESTPNT
//        
//        
//    // List of trees for which to do calculations
//    int ITARGETS(MAXT)
//    int ISPECIES(MAXT) // species of each tree, array of ints -> species definitions in confile.dat
//    int ISPECIEST(MAXT)
//    int ISPECIESTUS(MAXT)
//
//    // Tree positions and dimensions - all trees, all dates
//    double DXT1(MAXT)
//    double DYT1(MAXT)
//    double DZT1(MAXT)
//    double DX(MAXT)
//    double DY(MAXT)
//    double DZ(MAXT)
//    double RXTABLE1(maxdate,MAXT)
//    double RYTABLE1(maxdate,MAXT)
//    double RZTABLE1(maxdate,MAXT)
//    double ZBCTABLE1(maxdate,MAXT)
//    double FOLTABLE1(maxdate,MAXT)
//    double DIAMTABLE1(maxdate,MAXT)
//    // Tree positions and dimensions - sorted trees, all dates
//    double RXTABLE(maxdate,MAXT)
//    double RYTABLE(maxdate,MAXT)
//    double RZTABLE(maxdate,MAXT)
//    double ZBCTABLE(maxdate,MAXT)
//    double FOLTABLE(maxdate,MAXT)
//    double TOTLAITABLE(maxdate)  
//    
//    double DIAMTABLE(maxdate,MAXT)
//    int IT(MAXT)
//    // Dates for tree dimensions
//    int DATESX(maxdate)
//    int DATESY(maxdate)
//    int DATESZ(maxdate)
//    int DATEST(maxdate)
//    int DATESLA(maxdate)
//    int DATESD(maxdate)
//    // Tree dimensions on simulation date (by interpolation)
//    double DXT(MAXT)
//    double DYT(MAXT)
//    double DZT(MAXT)
//    double RX(MAXT)
//    double RY(MAXT)
//    double RZ(MAXT)
//    double ZBC(MAXT)
//    double FOLT(MAXT)
//    double DIAM(MAXT)
//    double WEIGHTS(MAXT)
//    double CANOPYDIMS(6)
//    // Positions of grid points, associated volume & leaf area, etc
//    double XL(MAXP)
//    double YL(MAXP)
//    double ZL(MAXP)
//    double VL(MAXP)
//    double DLT(MAXP)
//    double DLI(MAXC,MAXP)
//    int LGP(MAXP)
//    int PPLAY // number of points per layer (in tree crown)
//    double FOLLAY(MAXLAY)
//    // Met data
//    int SOILDATA
//    int USEMEASSW // use measured soil water content
//    double TAIR(MAXHRS) // air temperature - met file
//    double RADABV(MAXHRS,3)
//    double FBEAM(MAXHRS,3) // fraction of incident PAR which is direct beam
//
//    // Physiology inputs by layer
//    double ABSRP(MAXLAY,3)
//    double ARHO(MAXLAY,3)
//    double ATAU(MAXLAY,3)
//    double RHOSOL(3)
//    double JMAXTABLE(maxdate,MAXLAY,MAXC)
//    double VCMAXTABLE(maxdate,MAXLAY,MAXC)
//    double RDTABLE(maxdate,MAXLAY,MAXC)
//    double SLATABLE(maxdate,MAXLAY,MAXC)
//    double AJQTABLE(maxdate,MAXLAY,MAXC)
//    double Q10FTABLE(maxdate)
//    double Q10WTABLE(maxdate)
//    int DATESFQ(maxdate)
//    int DATESWQ(maxdate)
//    int DATESJ(maxdate)
//    int DATESV(maxdate)
//    int DATESRD(maxdate)
//    int DATESSLA(maxdate)
//    int DATESA(maxdate)
//    double JMAX25(MAXLAY,MAXC)
//    double VCMAX25(MAXLAY,MAXC)
//    double RD0(MAXLAY,MAXC)
//    double SLA(MAXLAY,MAXC)
//    double AJQ(MAXLAY,MAXC)
//    double DIFZEN(MAXANG)
//    double DEXT(MAXANG)
//    double ZEN(MAXHRS)
//    double AZ(MAXHRS)
//    double TD(MAXP)
//    double RELDF(MAXP)
//    double DIFDN(MAXP,3)
//    double DIFUP(MAXP,3)
//    double SCLOST(MAXP,3)
//    double DOWNTH(MAXP)
//    double SOILTEMP(MAXSOILLAY)
//
//    // Multi-species       
//    CHARACTER(30) SPECIESNAMES(MAXSP) // array of names of species in stand
//    CHARACTER(30) PHYFILES(MAXSP) // array of names of physiological input files
//    CHARACTER(30) STRFILES(MAXSP) // array of structure input files (overrides str.dat)
//
//    // STR arrays, multi-species versions.
//    double ALPHASPEC(MAXANG,MAXSP)
//    double FALPHASPEC(MAXANG,MAXSP)
//    double BPTSPEC(8,MAXC,MAXSP)
//    double BPTT(8,MAXC,MAXT)
//    double SHAPESPEC(MAXSP)
//    double EXTWINDSPEC(MAXSP)
//    double RANDOMSPEC(MAXSP)
//    double COEFFTSPEC(MAXSP)
//    double EXPONTSPEC(MAXSP)
//    double WINTERCSPEC(MAXSP)
//    double BCOEFFTSPEC(MAXSP)
//    double BEXPONTSPEC(MAXSP)
//    double BINTERCSPEC(MAXSP)
//    double DEXTSPEC(MAXSP,MAXANG)
//    double DEXTT(MAXT,MAXANG)
//    double BEXTSPEC(MAXSP)
//    double BEXTANGSPEC(MAXSP,MAXANG)
//    double BEXTT(MAXT)
//    double RCOEFFTSPEC(MAXSP)
//    double REXPONTSPEC(MAXSP)
//    double RINTERCSPEC(MAXSP)
//    double FRFRACSPEC(MAXSP)
//    int NOAGECSPEC(MAXSP)
//    int NOAGECT(MAXT)
//    int JLEAFSPEC(MAXSP)
//    int JLEAFT(MAXT)
//    int JSHAPESPEC(MAXSP)
//    int NALPHASPEC(MAXSP)
//    int JSHAPET(MAXT)
//    double SHAPET(MAXT)
//    double VPDMINSPEC(MAXSP)
//
//    // PHY arrays, multi-species versions.
//    double ABSRPSPEC(MAXLAY,3,MAXSP)
//    double ARHOSPEC(MAXLAY,3,MAXSP)
//    double ATAUSPEC(MAXLAY,3,MAXSP)
//    double RHOSOLSPEC(3,MAXSP)
//    double PROPPSPEC(MAXC,MAXSP)
//    double PROPCSPEC(MAXC,MAXSP)
//    double PROPPT(MAXC,MAXT)
//    double PROPCT(MAXC,MAXT)
//    double LEAFNSPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double JMAXTABLESPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double VCMAXTABLESPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double RDTABLESPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double SLATABLESPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double AJQTABLESPEC(maxdate,MAXLAY,MAXC,MAXSP)
//    double Q10FTABLESPEC(maxdate,MAXSP)
//    double Q10WTABLESPEC(maxdate,MAXSP)
//    int DATESNSPEC(maxdate,MAXSP)
//    int DATESJSPEC(maxdate,MAXSP)
//    int DATESVSPEC(maxdate,MAXSP)
//    int DATESRDSPEC(maxdate,MAXSP)
//    int DATESSLASPEC(maxdate,MAXSP)
//    int DATESASPEC(maxdate,MAXSP)
//    int DATESFQSPEC(maxdate,MAXSP)
//    int DATESWQSPEC(maxdate,MAXSP)
//    int NOAGEPSPEC(MAXSP)
//    int NSIDESSPEC(MAXSP)
//    double GSREFSPEC(MAXSP)
//    double GSMINSPEC(MAXSP)
//    double PAR0SPEC(MAXSP)
//    double D0SPEC(MAXSP)
//    double VK1SPEC(MAXSP)
//    double VK2SPEC(MAXSP)
//    double VPD1SPEC(MAXSP)
//    double VPD2SPEC(MAXSP)
//    double VMFD0SPEC(MAXSP) 
//    double GSJASPEC(MAXSP)
//    double GSJBSPEC(MAXSP)
//    double T0SPEC(MAXSP)
//    double TREFSPEC(MAXSP)
//    double TMAXSPEC(MAXSP)
//    double SMD1SPEC(MAXSP)
//    double SMD2SPEC(MAXSP)
//    double WC1SPEC(MAXSP)
//    double WC2SPEC(MAXSP)
//    double SWPEXPSPEC(MAXSP)
//    double D0LSPEC(MAXSP)  
//    double GAMMASPEC(MAXSP)
//    double WLEAFSPEC(MAXSP)   
//    double SFSPEC(MAXSP)
//    double PSIVSPEC(MAXSP)
//
//    int NOJDATESSPEC(MAXSP)
//    int NOVDATESSPEC(MAXSP)
//    int NOADATESSPEC(MAXSP)
//    int NOSLADATESSPEC(MAXSP)
//    int NORDATESSPEC(MAXSP)  
//    int NOWQDATESSPEC(MAXSP)
//    int NOFQDATESSPEC(MAXSP)
//    int IECOSPEC(MAXSP)
//    double EAVJSPEC(MAXSP)
//    double EDVJSPEC(MAXSP)
//    double DELSJSPEC(MAXSP)
//    double EAVCSPEC(MAXSP)
//    double EDVCSPEC(MAXSP)
//    double DELSCSPEC(MAXSP)
//    double TVJUPSPEC(MAXSP)
//    double TVJDNSPEC(MAXSP)
//    double THETASPEC(MAXSP)
//    double RTEMPSPEC(MAXSP)
//    double DAYRESPSPEC(MAXSP)
//    double EFFYRFSPEC(MAXSP)
//    double TBELOWSPEC(MAXSP)
//    double EFFYRWSPEC(MAXSP)
//    double RMWSPEC(MAXSP)
//    double RTEMPWSPEC(MAXSP)
//    double COLLASPEC(MAXSP)
//    double COLLKSPEC(MAXSP)
//    double STEMSDWSPEC(MAXSP)
//    double RMWAREASPEC(MAXSP)
//    double STEMFORMSPEC(MAXSP)
//    double Q10RSPEC(MAXSP)
//    double RTEMPRSPEC(MAXSP)
//    double Q10BSPEC(MAXSP)
//    double RTEMPBSPEC(MAXSP)
//    double RMCRSPEC(MAXSP)
//    double RMFRSPEC(MAXSP)
//    double RMBSPEC(MAXSP)
//    double K10FSPEC(MAXSP)
//    double G0TABLESPEC(maxdate,MAXSP)
//    double G1TABLESPEC(maxdate,MAXSP)
//    int NOGSDATESSPEC(MAXSP)
//    int DATESGSSPEC(maxdate,MAXSP)
//    int DATESGS(maxdate)
//    double G0TABLE(maxdate)
//    double G1TABLE(maxdate)
//    double APP
//    double BEAR
//    double BEXT
//    double BINSIZE // size of classes in histogram
//    double BMULT
//    double CO2INC // amount of increase in CO2, climate change scenario
//    double DAYL
//    double DEC
//    int KEEPZEN
//    double DIFSKY // controls distribution of diffuse radiation incident from sky
//    double DMULT2
//    double DT1
//    double DT2
//    double DT3
//    double DT4
//    double EXPAN
//    double EXPTIME
//    double FBEAMOTC // fractional reduction in beam factor of PAR (simulate growth in chamber)
//    double G0 // stomatal conductance when PAR is zero (part of tuzet model)
//    double G1 // slope parameter (part of tuzet model)
//    int ICC
//    int IDAY
//    int IEND
//    int IFLUSH
//    int IOTC
//    int IPROG
//    int IPROGUS
//    int ISIMUS
//    int ISPEC
//    int ISTART
//    int ITERMAX // controls iterations in combined photosynthesis-transpiration model, 0=leaf temp assumed=air temp, >0 iterative method used, the number of iterations
//    int IUSTFILE
//    int MODELGS // which model to calculate stomatal conductance, 2=Ball-Berry, 3=Ball-Berry-Leuning, 4=Ball-Berry-Opti, otherwise Jarvis model 
//    int MODELJM // How JMAX and VCMAX parameters read in, 0=read from file, 1=calculate from leaf content
//    int MODELRD // how RDO parameters read in, 0=read from file, 1=calculate from leaf content 
//    int MODELRW // how wood respiration parameters read in
//    int MODELSS // whether photosynthesis calculation done for sun/shade leaves separately
//    int NAZ // number of azimuth angles calculated 
//    int NEWCANOPY
//    int NEWTUTD
//    int NOADATES
//    int NOAGEP
//    int NODDATES
//    int NOFQDATES
//    int NOGSDATES
//    int NOJDATES
//    int NOLADATES
//    int NOLAY // number of layers in the crown
//    int NORDATES
//    int NOSLADATES
//    int NOTARGETS // number of target trees
//    int NOTDATES
//    int NOTREES // number of target trees, single target tree, see also itargets
//    int NOVDATES
//    int NOWQDATES
//    int NOXDATES
//    int NOYDATES
//    int NOZDATES
//    int NUMPNT
//    int NZEN // number of zenith angles for which diffuse transmittances calculated
//    int NSTEP
//    double PAROTC // reduction of incident PAR (simulate growth in chamber)
//    double PREVTSOIL
//    double Q10W
//    double Q10F
//    double SHADEHT // height of external shading of plot
//    double SOMULT
//    double STOCKING
//    double SUNLA
//    double SWMAX // maximum soil water content
//    double SWMIN // minimum soil water content
//    double TINC // amount of increase in air temperature, climate change scenario
//    double TOTC // increase of air temp (simulate growth in chamber)
//    CHARACTER(len=256) :: VTITLE // program title and version
//    CHARACTER(len=256) :: in_path
//    CHARACTER(len=256) :: out_path
//    int NSPECIES // number of species in the stand
//    double WINDOTC // absolute wind speed (simulate growth in chamber)
//    double XSLOPE // slopes of tree plots in degrees
//    double YSLOPE // slopes of tree plots in degrees
//    double X0 // offset values of tree locations
//    double Y0 // offset values of tree locations
//    double XMAX // max x coordinate of tree locations
//    double YMAX // max y coordinate of tree locations
//    double ZHT // roughness measurement height (m)
//    double Z0HT // roughness length (m)
//    double ZPD // (roughness) zero plane displacement (m)
//    double TOTLAI
//    double WLEAF // effective leaf width (part of tuzet model)
//    double G0SPEC(MAXSP),G1SPEC(MAXSP)
//    double GKSPEC(MAXSP)
//    double XLP(MAXP)
//    double YLP(MAXP)
//    double ZLP(MAXP)
//    double VPARASPEC(MAXSP)
//    double VPARBSPEC(MAXSP)
//    double VPARCSPEC(MAXSP)
//
//    int VFUNSPEC(MAXSP)
//         
//    int IOHRLY    // Controls daily, hourly, and/or layer outp
//    int IOTUTD    // Controls transmittance file output
//    int IOHIST    // Controls histogram output
//    int IORESP    // Controls respiration output
//    int IODAILY   // Controls daily output: FIXED HERE 
//    int IOWATBAL  // Controls water balance output
//    int IOFORMAT  // Dump mode...
//    
//    integer ISUNLA
//    double PLOTAREA
//    int NOALLTREES  
//end type maespaConfigvariablesstate

//    
//type maespaConfigTreeMapState
//        int numberTreePlots
//        int, DIMENSION(:), ALLOCATABLE :: xlocation
//        int, DIMENSION(:), ALLOCATABLE :: ylocation
//        int, DIMENSION(:), ALLOCATABLE :: phyfileNumber
//        int, DIMENSION(:), ALLOCATABLE :: strfileNumber
//        int, DIMENSION(:), ALLOCATABLE :: treesfileNumber
//        int, DIMENSION(:), ALLOCATABLE :: treesHeight
//        int, DIMENSION(:), ALLOCATABLE :: trees
//        
//        int numberBuildingPlots
//        int, DIMENSION(:), ALLOCATABLE :: xBuildingLocation
//        int, DIMENSION(:), ALLOCATABLE :: yBuildingLocation
//        int, DIMENSION(:), ALLOCATABLE :: buildingsHeight
//        int configPartitioningMethod
//        int usingDiffShading
//        
//        int width
//        int length
//        
//        int configTreeMapCentralArrayLength
//        int configTreeMapCentralWidth
//        int configTreeMapCentralLength
//        int configTreeMapX
//        int configTreeMapY
//        int configTreeMapX1
//        int configTreeMapX2
//        int configTreeMapY1
//        int configTreeMapY2
//        int configTreeMapNumsfcab
//        double configTreeMapGridSize
//        int configTreeMapHighestBuildingHeight
//        
//end type maespaConfigTreeMapState
//    

 
    
//END MODULE MaespaConfigState

}
