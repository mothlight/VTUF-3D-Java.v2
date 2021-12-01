package VTUF3D;

import java.util.ArrayList;
import java.util.TreeMap;

public class OutputClass
{

	
	boolean ywrite; boolean first_write; double timeis; double outpt_tm; double deltat; int timewrite; boolean last_write; double ralt; double Kdn_diff; double nKdndiff;
	OverallConfiguration overall; double az; double Kdif; double Kdir; double Ktot;double Tsfc_R; int numroof2; double Tsfc_T; int numstreet2; double Tsfc_N; int numNwall2; 
	double Tsfc_S; int numSwall2; double Tsfc_E; int numEwall2; double Tsfc_W; int numWwall2; boolean facet_out; int BH; int aw2; int al2; double[][] sfc; 
	double lpactual; double bh; int bl; int bw; double hwactual; double xlat; double stror; int yd_actual; double Rnet_tot; double Aplan; double Qh_tot; double Qh_abovezH; double Qhtop; 
	double lambdapR; double Qg_tot; double Qhcan; double Rnet_R; double Qg_R; double ustar; double vK; double zH; double zd; double z0; double Fm; double lambdaf; double Acan; double Bcan; 
	double Ccan; double patchlen; double wstar; double Kup; double Ldn; double Lup; double Kdir_Calc; double Kdif_Calc; double Lup_R; double zen; double Kdir_NoAtm; double Kdn_grid; double Qe_tot; 
	double Kup_R; double Qh_R; double Rnet_T; double Qh_T; double Qg_T; double Rnet_N; double Qh_N; double Qg_N; double Rnet_S; double Qh_S; double Qg_S; double Rnet_E; double Qh_E; double Qg_E; double Rnet_W; 
	double Qh_W; double Qg_W; double Kdn_S; double Kup_S; double Ldn_S; double Lup_S; double Kdn_E; double Kup_E; double Ldn_E; double Lup_E; double Kdn_N; double Kup_N; double Ldn_N; double Lup_N; double Kdn_W; 
	double Kup_W; double Ldn_W; double Kdn_R; double Ldn_R; double Kdn_T; double Kup_T; double Ldn_T; double Lup_T; double Lup_W; int numwall2; double Tsfc_cplt; double Tsfc_bird; double Ta; double Tintw; 
	double httcT; double httcW; double Trad_R; double Tcan; double Trad_W; double Trad_E; double Trad_S; double Trad_N; double Trad_T; double TTsun; double numTsun; double TTsh; double numTsh; double TNsun; 
	double numNsun; double TNsh; double numNsh; double TSsun; double numSsun; double TSsh; double numSsh; double TEsun; double numEsun; double TEsh; double numEsh; double TWsun; double numWsun; 
	double TWsh; double numWsh; int yd; 
	int counter2; double Kuptot_avg; double Luptot_avg; double Rntot_avg; double Qhtot_avg; double Qgtot_avg; double Qanthro_avg; double Qac_avg; double Qdeep_avg; double Qtau_avg; 
	double TR_avg; double TT_avg; double TN_avg; double TS_avg; double TE_avg; double TW_avg; double Qetot_avg; boolean[][][][] surf; int[] ind_ab; 
	double[] Tsfc; double[] Trad; double[] absbs; double[] reflts; int time_out; int lptowrite; double[] lpin; int lpiter; String lpwrite; String lpwrite1; int bhbltowrite; 
	double[] bh_o_bl; int bhiter; String bhblwrite; String bhblwrite2; String bhblwrite1; String strorwrite; 
	String strorwrite1; String latwrite; String latwrite1; String latwrite2; String ydwrite; String ydwrite1; String ydwrite2; int tempTimeis; boolean sum_out; 
	String time1; String time2; String time3; int numsfc2; double sw; double sw2; 
	boolean writeTsfc; boolean writeKl; boolean writeKabs; boolean writeKrefl; boolean writeLabs; boolean writeLrefl; boolean writeLdown; 
	boolean writeTmrt; boolean writeUtci; boolean writeEnergyBalances; boolean newlp; boolean newbhbl; int numsfc; boolean matlab_out; 
	double[] tots; double[] totl; double[] refltl; double[] Ldnfrc; double[] absbl; int[] sfc_ab_map_x; int[] sfc_ab_map_y; int diffShadingValueUsed; int[][] treeXYMap; 
	double[] eafrc; double[] Uafrc; double[] currentRnet; double[] currentQh; double[] currentQe; double[] currentQg; boolean frcwrite;
	TreeMap<String,ArrayList<MaespaDataResults>> maespaDataArray;
}
