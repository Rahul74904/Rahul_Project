/*****************************************************************************
 ****************************** JAI GOLU DEV *********************************
 ************************* Shri Ganeshay Namah *******************************
 ************** Begin : 17 March 2003 10:12 A.M.******************************/

package tp.xt;
import java.util.regex.*;
import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

class XMLObjects
{

	boolean protectCheck;
	static boolean isTextBox=false;//abhay
	//static boolean isauthor=false;
	//static boolean isfootnote=false;
	static boolean isDisplayTextBox;//23-12-2010
	String endTAG = new String();
	boolean debug= false;
	String bibStyle="";
	ArrayList figuresInfo, tablesInfo, textBoxInfo;
	Hashtable figSchem; //added by avinandan for fig/sce placement
	int figNo   = 0;
	int tblNo   = 0;
	int tbNo   = 0;
	int mno=1;
	Hashtable figInfo;
	Hashtable mathEntityList;
	ArrayList MassonJidList;//04/12/2007
	ArrayList ItalianJidList;//04/12/2007
	ArrayList SpanishJidList;//04/12/2007
	ArrayList PortugueseJidList;//04/12/2007
	ArrayList CatalanJidList;//12/08/2011
		
	public ArrayList GulliverJidList;
	public static boolean check_MassonJid=false;
	public static boolean check_ItalianJid=false;
	public static boolean check_SpanishJid=false;
	public static boolean check_PortugueseJid=false;
	public static boolean check_CatalanJid=false;
	public static boolean check_GulliverJid=false;
	Hashtable textEntityList;
	String mathvariant;
	String mmlStyle;
	String mmlTag;
	int bkmCount=0;
	public Hashtable GuliverCutOff;
	public Hashtable TableGuliverCutOff;
	public boolean checkMassonInlineFig=false;
	static String checkServer;
	//int temptable=0;
	String CapLang="";
	public String footTagval="";

	boolean italicFlag;
	boolean italicBold;
	boolean boldFlag;
	boolean underlineFlag;
	boolean Strikethrough;
	boolean booInterRef=false;
	//Begin <ce:item-info> group
	RandomAccessFile fin;
	public String deviation;//added for ref. style for books-28-1-2005
	public String jid;
	public String aid;
	public String articleNo;
	public String pit;
	String fntStyle="";
	public StringBuffer footnote; //Added by Avinandan for footstyle = Y article
	boolean displayStyle;
	static String MassionJid="";
	static String Guliver_aid="";
	//End <ce:item-info> group
	ReplaceEntity replEnt;
	boolean specialLink;
	boolean mathFlag;
	boolean mathFlagINF;
	boolean infFlag;
	boolean sinfFlag;
	boolean supFlag;
	boolean rmboxFlag;
	public boolean textbox_body=false;
	static boolean copyrightfirst=true;
	static long copyrightseek=-1;
	boolean checkQRCodeId=false;
	boolean checkQRFigCheck=false;
	int QRCount=0;
	String QRRef111="";
	//MMLConv mt;
	//MML2Tex mt;
	NewMML2Tex mt;
	ArrayList bkmList;
	StringBuffer abrKwd = new StringBuffer();//declare by avinandan
	public String figureValue="";
	public String InlinefigureValueGCB="";//15-06-2010
	//String thobkTblId="";
	//boolean tblrefThobk=false;
	public static String undefinedEntities="";
	static Vector upperCaseSecJid;
	XT xt= new XT();
	BodyGroup bg=new BodyGroup();
	tp.xt.XTLogger xt_log=tp.xt.XTLogger.getInstance();
	
	
	XMLObjects()
	{
	}

	XMLObjects(String jd, String ad, String status)
	{
		GulliverJidList= new ArrayList();
		GuliverCutOff=new Hashtable();
		TableGuliverCutOff=new Hashtable();
		MassionJid=jd;
		Guliver_aid=ad;
		checkServer=status;
		if(Guliver_aid.indexOf(".",0)!=-1)
		{
			Guliver_aid=Guliver_aid.substring(0,Guliver_aid.indexOf(".",0));
			//System.out.println("Guliver_aid : "+Guliver_aid);
		}
		//System.out.println("111 xt.MassionJid : "+MassionJid);
	}
	
	XMLObjects(RandomAccessFile fin, Hashtable figInfo, ArrayList bkmList)throws IOException
	{
		infFlag= false;
		sinfFlag= false;
		supFlag= false;
		rmboxFlag=false;
		this.bkmList= bkmList;
		//System.out.println("***********************"+bkmList.size());
		mmlTag="";
		italicFlag     = false;
		italicBold     = false;
		figSchem=new Hashtable();
		boldFlag       = false;
		underlineFlag  = false;
		Strikethrough =false;
		this.jid       = "";
		this.deviation = "";
		this.aid       = "";
		this.articleNo = "";
		pit		= "";
		this.fntStyle  ="";
		this.fin       = fin;
		this.figInfo   = figInfo;
		mathFlag       = false;
		mathFlagINF	   = false;
		mathEntityList = new Hashtable();
		MassonJidList  = new ArrayList();//04/12/2007
		ItalianJidList = new ArrayList();//04/12/2007
		SpanishJidList = new ArrayList();//04/12/2007
		CatalanJidList = new ArrayList();//12/08/2011

		PortugueseJidList= new ArrayList();//04/12/2007
		GulliverJidList= new ArrayList();
		GuliverCutOff=new Hashtable();
		TableGuliverCutOff=new Hashtable();
		textEntityList = new Hashtable();
		mathvariant    = "";
		displayStyle   = false;
		mmlStyle       = "";
		mt             = new NewMML2Tex(this);
		figuresInfo    = new ArrayList();
		tablesInfo     = new ArrayList();
		textBoxInfo    = new ArrayList();
		//new change for Franch article 
		//[22/08/2007]
		
		makeMassonJidList();
		makeItalianJidList();
		makeSpanishJidList();
		makePortugueseJidList();
		makeCatalanJidList();//12-08-2011
		makeGulliverJidList();
		//System.out.println("xt.MassionJid : "+MassionJid);
			//try{System.in.read();}catch(Exception e){}
		//if(xt.MassionJid.equalsIgnoreCase("FR"))
		if(MassonJidList.contains(MassionJid))
		{
			check_MassonJid=true;
			makeEntityList();
			makeEntityListFranch();
			
		}
		//else if(ItalianJidList.contains(MassionJid))
		if(ItalianJidList.contains(MassionJid))
		{
			check_ItalianJid=true;
			makeEntityList();
			makeEntityListFranch();
		}
		//else if(SpanishJidList.contains(MassionJid))
		if(SpanishJidList.contains(MassionJid))
		{
			check_SpanishJid=true;
			makeEntityList();
			makeEntityListFranch();
		}
		//else if(PortugueseJidList.contains(MassionJid))
		if(PortugueseJidList.contains(MassionJid))
		{
			check_PortugueseJid=true;
			makeEntityList();
			makeEntityListFranch();
		}
		if(CatalanJidList.contains(MassionJid))//12-08-2011
		{
			check_CatalanJid=true;
			makeEntityList();
			makeEntityListFranch();
		}
		
		//else if(GulliverJidList.contains(MassionJid))
		if(GulliverJidList.contains(MassionJid))
		{
			String cutOffAid=GuliverCutOff.get(MassionJid).toString();
			//System.out.println("cutOffAid : "+cutOffAid+ "Guliver_aid : "+Guliver_aid);
			if(Integer.parseInt(cutOffAid.trim())<= Integer.parseInt(Guliver_aid.trim())){
			//System.out.println("1 cutOffAid : "+cutOffAid+ "Guliver_aid : "+Guliver_aid);
			check_GulliverJid=true;
			makeEntityList();
			makeEntityListGulliver();
			}	
			else
			{
				makeEntityList();
			}
		}
		else
		{
			makeEntityList();
		}
		
		specialLink= false;
		protectCheck=false;
		footnote=new StringBuffer();
		//thobkTblId="";
		//tblrefThobk=false;
		upperCaseSecJid=new Vector();
		upperCaseSecJid.add("AMEEVA");
		upperCaseSecJid.add("CHEHEA");
		////upperCaseSecJid.add("NEUTOX");//on CE Feedback dated 16-Aug 2005
		upperCaseSecJid.add("ORGDYN");
		upperCaseSecJid.add("YPRRV");

	}

	public String getTag() throws IOException
	{
		char         ch  = 0;
		StringBuffer tag = new StringBuffer("<");
		while((ch = (char) fin.read()) !='>')
		{
			tag.append(ch);
		}
		tag.append(">");
		//return tag.toString().toUpperCase();
		return tag.toString();
	}

	public String extractData(String tag, boolean ignoreP)throws IOException
	{
		//System.out.println("extractData("+tag+") called");
		String       eTag     = "";
		StringBuffer tagValue = new StringBuffer();
		String paraView="";
		XT modXt=new XT();
		boolean modelDText=modXt.chekJidAid(jid, aid);	
		//if(tag.equals("</CE:COPYRIGHT>"))
		//System.out.println("reached here");
		if(tag.equals("</CE:COPYRIGHT>")&&copyrightfirst){

				//System.out.println("1");
				copyrightseek=fin.getFilePointer();
				copyrightfirst=false;
		}else if(tag.equals("</CE:COPYRIGHT>")&&!copyrightfirst){
				//System.out.println("2");
				fin.seek(copyrightseek);
		}
		while(!eTag.equals(tag))
		{
			char ch= (char)fin.read();
//			System.out.print("MMMMM"+ch);
			if (ch=='<')
			{
				String eTag1 = getTag();
				eTag= eTag1.toUpperCase();
			
				if(eTag.equals(tag))
					break;

				if (debug==true)
				{
				}
				if (eTag.equals("<CE:SIMPLE-PARA>") || eTag.startsWith("<CE:SIMPLE-PARA")) // 04-01-2005
				{
					if(ignoreP== false)
						tagValue.append("\r\n\r\n");

					HeadGroup xmlHead= new HeadGroup(this);
						
					if(xmlHead.figureCaptionPara==true && xmlHead.figureCaptionFirstPara==false)
					{
						if((jid.equalsIgnoreCase("PALEVO") || jid.equalsIgnoreCase("REVRHU")|| jid.equalsIgnoreCase("QUIP")||jid.equalsIgnoreCase("DIABET")||jid.equalsIgnoreCase("ERAP")||jid.equalsIgnoreCase("HYA")||jid.equalsIgnoreCase("STMAT")||jid.equalsIgnoreCase("CYA")||jid.equalsIgnoreCase("DF")||jid.equalsIgnoreCase("RESU")||jid.equalsIgnoreCase("ANTRO")||jid.equalsIgnoreCase("ANDO")||jid.equalsIgnoreCase("REVAL")||jid.equalsIgnoreCase("MEDMAL")||jid.equalsIgnoreCase("NEUCHI")||(jid.equalsIgnoreCase("NEUADO") && XT.fmcforall==false)||jid.equalsIgnoreCase("MEDDRO")||jid.equalsIgnoreCase("RBMRET")||jid.equalsIgnoreCase("IRBM")||jid.equalsIgnoreCase("CANRAD")||jid.equalsIgnoreCase("SCIPSO")|| jid.equalsIgnoreCase("NUTCLI")||jid.equalsIgnoreCase("ANCAAN")||jid.equalsIgnoreCase("CHIMAI") ||jid.equalsIgnoreCase("JTS")|| jid.equalsIgnoreCase("REVMED")||jid.equalsIgnoreCase("SCOMS")||jid.equalsIgnoreCase("MONRHU")||jid.equalsIgnoreCase("PATBIO")||jid.equalsIgnoreCase("BIOPHA") || jid.equalsIgnoreCase("ANNPAL")|| jid.equalsIgnoreCase("ALTER")|| jid.equalsIgnoreCase("EXMATH")|| jid.equalsIgnoreCase("DDES")|| jid.equalsIgnoreCase("SMED")|| jid.equalsIgnoreCase("PSFR")|| jid.equalsIgnoreCase("EHN")|| jid.equalsIgnoreCase("EHMCM")|| jid.equalsIgnoreCase("PRPS")|| jid.equalsIgnoreCase("SOCTRA")|| jid.equalsIgnoreCase("EVOPSY") || jid.equalsIgnoreCase("NEUCLI") || jid.equalsIgnoreCase("ENCEP")|| jid.equalsIgnoreCase("SEXOL")||jid.equalsIgnoreCase("OTSR")||jid.equalsIgnoreCase("JOCLIM")||jid.equalsIgnoreCase("SODA")||jid.equalsIgnoreCase("LIVER")||jid.equalsIgnoreCase("DEMAN")||jid.equalsIgnoreCase("STLM")||jid.equalsIgnoreCase("ANNDER")||jid.equalsIgnoreCase("RCOT")||jid.equalsIgnoreCase("RCO")||jid.equalsIgnoreCase("JVS")||jid.equalsIgnoreCase("JCHIRV")||jid.equalsIgnoreCase("JCHIR")||jid.equalsIgnoreCase("ANNPAT")||jid.equalsIgnoreCase("FEMME")||jid.equalsIgnoreCase("CND")||jid.equalsIgnoreCase("MEDPAL")||jid.equalsIgnoreCase("EURTEL")||jid.equalsIgnoreCase("REVHOM")||jid.equalsIgnoreCase("JCCO")||jid.equalsIgnoreCase("JEUREA")||jid.equalsIgnoreCase("IMMBIO")||jid.equalsIgnoreCase("MORPHO")||jid.equalsIgnoreCase("JMV")||jid.equalsIgnoreCase("JDMV")||jid.equalsIgnoreCase("BANM")|| jid.equalsIgnoreCase("REAURG")||jid.equalsIgnoreCase("JFO")||jid.equalsIgnoreCase("MLONG")||jid.equalsIgnoreCase("LONGEV")||jid.equalsIgnoreCase("RMR")||jid.equalsIgnoreCase("PEDPUE")||jid.equalsIgnoreCase("ANNDEROLD")||jid.equalsIgnoreCase("FANDER")||jid.equalsIgnoreCase("ACVDSP")||jid.equalsIgnoreCase("THERAP")||jid.equalsIgnoreCase("JEMEP")||jid.equalsIgnoreCase("TOXAC")||jid.equalsIgnoreCase("ONCOHP")||jid.equalsIgnoreCase("JRADIO")||jid.equalsIgnoreCase("JRDIA")||jid.equalsIgnoreCase("DIII")||(jid.equalsIgnoreCase("TRACLI"))||jid.equalsIgnoreCase("MSOM") ||jid.equalsIgnoreCase("GCB")||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))||jid.equalsIgnoreCase("NEUADO")||jid.equalsIgnoreCase("PRATAN")||jid.equalsIgnoreCase("ANICOM")||jid.equalsIgnoreCase("VETCLI")||jid.equalsIgnoreCase("NRL")||jid.equals("NRLENG")||jid.equals("ENDONU")||jid.equals("ENDINU")||jid.equals("SENOL")||jid.equalsIgnoreCase("APRIM")||jid.equalsIgnoreCase("EJPSY")||jid.equalsIgnoreCase("REPOD")||jid.equalsIgnoreCase("RPTOB")||jid.equalsIgnoreCase("RAA")||jid.equalsIgnoreCase("RCHIC")||jid.equalsIgnoreCase("RCHIRA")||jid.equalsIgnoreCase("RPRH")||jid.equalsIgnoreCase("RCHOT")||jid.equalsIgnoreCase("RIEM")||jid.equalsIgnoreCase("CC")||jid.equalsIgnoreCase("EDUMED")||jid.equalsIgnoreCase("CIRGEN")||jid.equalsIgnoreCase("MAGIS")||jid.equalsIgnoreCase("EJFB")||jid.equalsIgnoreCase("EJEPS")||jid.equalsIgnoreCase("ANPSIC")||jid.equalsIgnoreCase("MINCOM")||jid.equalsIgnoreCase("RCHIPE")||jid.equalsIgnoreCase("RCCOT")||jid.equalsIgnoreCase("UROCO")||jid.equalsIgnoreCase("CIRCIR")||jid.equalsIgnoreCase("CIRCEN")||jid.equalsIgnoreCase("RIPS")||jid.equalsIgnoreCase("ACCI")||jid.equalsIgnoreCase("MEI")||jid.equalsIgnoreCase("MEDRE")||jid.equalsIgnoreCase("REU")||jid.equalsIgnoreCase("UROMX")||jid.equalsIgnoreCase("ANCV")||jid.equalsIgnoreCase("ACUP")||jid.equalsIgnoreCase("HGMX")||jid.equalsIgnoreCase("BMHIMX")||jid.equalsIgnoreCase("RARD")||jid.equalsIgnoreCase("RCCAR")||jid.equalsIgnoreCase("PIRO")||jid.equalsIgnoreCase("REIMKE")||jid.equalsIgnoreCase("SJME")||jid.equalsIgnoreCase("EQ")||jid.equalsIgnoreCase("RLP")||jid.equalsIgnoreCase("RMTA")||jid.equalsIgnoreCase("RCCAN")||jid.equalsIgnoreCase("BJORL")||jid.equalsIgnoreCase("BJORLP")||jid.equalsIgnoreCase("ENDMAG")||jid.equalsIgnoreCase("IJCHP")||jid.equalsIgnoreCase("MEXOFT")||jid.equalsIgnoreCase("RAM")||jid.equalsIgnoreCase("BJAN")||jid.equalsIgnoreCase("BJANE")||jid.equalsIgnoreCase("BJANES")||jid.equalsIgnoreCase("CESJEF")||jid.equalsIgnoreCase("SEDENE")||jid.equalsIgnoreCase("SEDENG")||jid.equalsIgnoreCase("RGMX")||jid.equalsIgnoreCase("RLFA")||jid.equalsIgnoreCase("CESJEF")||jid.equals("ANGIO")||jid.equals("RIFK")||jid.equals("REDAR")||jid.equals("REDARE")||jid.equals("REMLE")||jid.equalsIgnoreCase("DIALIS")||jid.equals("RPSM")||jid.equals("AVDIAB")||jid.equals("MEDIPA")||jid.equals("IMADI")||jid.equals("ANDROL")||jid.equals("ACMX")||jid.equals("INFECT")||jid.equals("REML")||jid.equals("PATOL")||jid.equals("FT")||jid.equals("RECOT")||jid.equals("SEMERG")||jid.equals("CALI")||jid.equals("JHQR")||jid.equals("REUMA")||jid.equals("REUMAE")||jid.equals("PSIQ")||jid.equals("REMN")||jid.equals("REMNIM")||jid.equals("REMNGL")||jid.equals("REMNIE")||jid.equals("REGG")||jid.equals("PBJ")||jid.equals("RIBA")||jid.equals("APPR")||jid.equals("MEDCLI")||jid.equals("MEDCLE")||jid.equals("APJ")||jid.equals("PSE")||jid.equals("CLYSA")||jid.equals("RPTO")||jid.equals("GEMREV")||jid.equals("ESPE")||jid.equals("AULA")||jid.equals("EJPAL")||jid.equals("BJP")||jid.equals("JEFAS")||jid.equals("ESTGER")||jid.equals("RAMD")||jid.equals("RCSAR")||jid.equals("PSI")||jid.equals("ANYES")||jid.equals("JIK")||jid.equals("CIRCV")||jid.equals("RPEDM")||jid.equals("CEDE")||jid.equals("BRQ")||jid.equals("RIMNI")||jid.equals("SRFE")||jid.equals("IHE")||jid.equals("REDEE")||jid.equals("REDEEN")||jid.equals("IEDEE")||jid.equals("IEDEEN")||jid.equals("SEMREU")||jid.equals("MCP")||jid.equals("RIAM")||jid.equals("EIMC")||jid.equals("PSICOD")||jid.equals("EIMCE")||jid.equals("PSICOE")||jid.equals("GACETA")||jid.equals("ARBRES")||jid.equals("OPRESP")||jid.equals("ARBR")||jid.equalsIgnoreCase("ABD")||jid.equalsIgnoreCase("JPED")||jid.equalsIgnoreCase("AD")||jid.equalsIgnoreCase("ADENGL")||jid.equalsIgnoreCase("ACURO")||jid.equalsIgnoreCase("ACUROE")||jid.equalsIgnoreCase("RICMA")||jid.equalsIgnoreCase("RPPEDE")||jid.equals("ANPEDI")||jid.equals("ANPEDE")||jid.equals("RCE")||jid.equals("RCENG")||jid.equalsIgnoreCase("FARMA")||jid.equalsIgnoreCase("FARMAE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("REPC")||jid.equals("JPG")||jid.equals("JPGE")||jid.equalsIgnoreCase("REPCE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("RECOTE")||jid.equalsIgnoreCase("RPSMEN")||jid.equalsIgnoreCase("ENDOEN")||jid.equalsIgnoreCase("ENDIEN")||jid.equalsIgnoreCase("OTOENG")||jid.equalsIgnoreCase("MEDINE")||jid.equalsIgnoreCase("BMHIME")||jid.equalsIgnoreCase("RGMXEN")||jid.equalsIgnoreCase("RX")||jid.equalsIgnoreCase("RCE")||jid.equalsIgnoreCase("TEKHNE")||jid.equalsIgnoreCase("ALLER")||jid.equalsIgnoreCase("GAMO")||jid.equalsIgnoreCase("OPTOM")||jid.equalsIgnoreCase("BJPT")||jid.equals("OTORRI")||jid.equals("GASTRO")||jid.equals("GASTRE")||jid.equalsIgnoreCase("GINE")||jid.equals("APUNTS")||jid.equals("RPPNEU")||jid.equals("RPPNEN")||jid.equals("PULMOE")||jid.equals("LABCLI")||jid.equals("HIPERT")||jid.equals("ARTERI")||jid.equals("ARTERE")||jid.equals("RH")||jid.equals("ENFI")||jid.equals("ENFIE")||jid.equals("ENFCLI")||jid.equals("ENFCLE")||jid.equalsIgnoreCase("MEDIN")||jid.equalsIgnoreCase("ENDOMX")|| jid.equalsIgnoreCase("JGYN")||jid.equalsIgnoreCase("AFJU")||(jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))||jid.equalsIgnoreCase("AFORL")||jid.equalsIgnoreCase("ANORL")||jid.equalsIgnoreCase("JTCC")||jid.equalsIgnoreCase("JBCT") || jid.equalsIgnoreCase("ETIQE")|| jid.equalsIgnoreCase("PUROL")|| jid.equalsIgnoreCase("NPG")|| jid.equalsIgnoreCase("PHARMA")|| jid.equalsIgnoreCase("SAGF")|| jid.equalsIgnoreCase("PNEUMO")|| jid.equalsIgnoreCase("DOULER")|| jid.equalsIgnoreCase("ACVD")|| jid.equalsIgnoreCase("BONSOI")||(jid.equalsIgnoreCase("BIONUT"))||(jid.equalsIgnoreCase("BIOMAG"))|| jid.equalsIgnoreCase("CULHER") || jid.equalsIgnoreCase("REVMIC") || jid.equalsIgnoreCase("ACCPM") || jid.equalsIgnoreCase("DIABET") || jid.equalsIgnoreCase("EJTD") || jid.equalsIgnoreCase("EURGER") || jid.equalsIgnoreCase("EURPSY") || jid.equalsIgnoreCase("GEOBIO") || jid.equalsIgnoreCase("GOFS") || jid.equalsIgnoreCase("HANSUR") || jid.equalsIgnoreCase("INAN") || jid.equalsIgnoreCase("JOGOH") || jid.equalsIgnoreCase("JORMAS") || jid.equalsIgnoreCase("MEDNUC") || jid.equalsIgnoreCase("MYCMED") || jid.equalsIgnoreCase("RETRAM") || jid.equalsIgnoreCase("ARCPED") || (jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(jid).toString())<=Integer.parseInt(aid)))) && HeadGroup.isFloats == true)//abhay 26/08/2006
							tagValue.append("\\\\\r\n");
						else
						{
							//tagValue.append("\r\n\r\n");//15-03-2011
							tagValue.append("\\\\\r\n");
						}
					}
					xmlHead.figureCaptionFirstPara = false;
				}
				else if (eTag.equals("<CE:INLINE-FIGURE>"))
				{
					checkQRFigCheck=true;
					tagValue.append(processInlineFigure());
										
				}
				/*else if (eTag.startsWith("<SA:AFFILIATION"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}
				else if (eTag.startsWith("<SA:ORGANIZATION"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}
				else if (eTag.startsWith("<SA:ADDRESS-LINE"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}
				else if (eTag.startsWith("<SA:STATE"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}
				else if (eTag.startsWith("<SA:POSTAL-CODE"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}
				else if (eTag.startsWith("<SA:COUNTRY"))//28-08-2012 JADTD520 Updation
				{
					//Ignore Tag for output
					tagValue.append(" ");
				}*/

				else if (eTag.startsWith("<CE:GLYPH "))
				{
					tagValue.append(replaceGlyph(eTag));
				}
				else if (eTag.equals("<CE:ITALIC>"))
				{
					//System.out.println("<<I>>");
					//Avinandan added till end mark
					String tempTag="";					
					long fpos=0;
					fpos=fin.getFilePointer();
					char ch1=(char)fin.read();					
					if(ch1=='<')
					{
						tempTag=getTag().toUpperCase();						
						if(tempTag.equals("<CE:BOLD>"))
						{
							
							italicBold=true;
							tempTag=extractData("</CE:ITALIC>", true);
							// "(tempTag.indexOf("\\rm")!=-1)" added by mukesh on 25-09-08
							// as wrong output generated for $entity inside bold italic.
							// Request by TPMS.
							if(tempTag.indexOf("\\bf{")!=-1)
							{
								tempTag=tempTag.replaceAll("\\\\bf\\{","\\\\bi\\{");
								//System.out.println("tempTag--------------\n"+tempTag);
							}
							//if((tempTag.startsWith("\\("))&&(tempTag.endsWith("\\)}}")))
							//if((tempTag.startsWith("\\("))&&(tempTag.endsWith("\\)}}")) && (tempTag.indexOf("\\rm")!=-1))//commented on 01-03-2013 by Vivek to handle diff tag
							if((tempTag.startsWith("\\(")||tempTag.startsWith("ThomsonDiff\\_ThomsonDiffOpenBrace"))&&(tempTag.endsWith("\\)}}")) && (tempTag.indexOf("\\rm")!=-1))
							{
								tempTag=tempTag.replaceAll("\\\\rm","\\\\bm \\\\");
								tempTag=tempTag.replaceAll("\\)}}",")");
								tagValue.append(tempTag);
							}
							else
							{
								//tagValue.append("{\\it{\\bi{"+tempTag+"}");//changed by rajeev for italic bold
								tagValue.append("{{\\bi{"+tempTag+"}");//\\it removed on 18-10-2015//
								//System.out.println("tempTag"+tempTag);
							}
							italicBold=false;
						}
						else
						{
							fin.seek(fpos);
							italicFlag = false;
							//System.out.println("eTag-----------"+eTag);
							tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}");// UPDATED NEELS 22-02-04 to close BOLD
							italicFlag= false;
						}
					}
					else
					{
						
						fin.seek(fpos);
						if(mathFlag==true && (infFlag==true || supFlag==true))// 11-10-2004-rajeev
								mathvariant="";
						italicFlag = false;
						tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}");// UPDATED NEELS 22-02-04 to close BOLD
						if(mathFlag==true && (infFlag==true || supFlag==true))// 11-10-2004-rajeev
								mathvariant="NORMAL";
						italicFlag= false;
						//System.out.println("tagValue=22=> "+tagValue);
					}
					//End mark and commented below
					//three line
					//italicFlag = false;
					//tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}");// UPDATED NEELS 22-02-04 to close BOLD
					//italicFlag = false;
				}
				else if (eTag.equals("</CE:ITALIC>"))
				{
					//System.out.println("Enetered");
					/*if(mathFlag==true && (infFlag==true || supFlag==true))// 11-10-2004-rajeev
								mathvariant="NORMAL";*/
					if(infFlag==false)
						tagValue.append("}}");
				}
				else if (eTag.equals("</CE:BOLD>"))
				{
					
					tagValue.append("}}");
					boldFlag = false;
				}
				else if (eTag.equals("<CE:BOLD>"))
				{
					
					//Avinandan added till end mark
					String tempTag="";
					long fpos=0;
					fpos=fin.getFilePointer();
					char ch1=(char)fin.read();
					if(ch1=='<')
					{
						tempTag=getTag().toUpperCase();
						if(tempTag.equals("<CE:ITALIC>"))
						{
							italicBold=true;
							tempTag=extractData("</CE:BOLD>", true);
							// "(tempTag.indexOf("\\rm")!=-1)" added by mukesh on 25-09-08
							// as wrong output generated for $entity inside bold italic.
							// Request by TPMS.
							if(tempTag.indexOf("\\it{")!=-1)
							{
								tempTag=tempTag.replaceAll("\\\\it\\{","\\\\bi\\{");
								//System.out.println("tempTag--------------\n"+tempTag);
							}
							
							//if((tempTag.startsWith("\\("))&&(tempTag.endsWith("\\)}}")))
							if((tempTag.startsWith("\\("))&&(tempTag.endsWith("\\)}}")) && (tempTag.indexOf("\\rm")!=-1))
							{
								tempTag=tempTag.replaceAll("\\\\rm","\\\\bm \\\\it");
								tempTag=tempTag.replaceAll("\\)}}",")");
								tagValue.append(tempTag);
								//System.out.println("tempTag............"+tempTag);
							}
							else
							{
								//tagValue.append("{\\bf{\\bi{"+tempTag+"}");//changed by rajeev for bolditalic
								tagValue.append("{{\\bi{"+tempTag+"}");//\\bf removed on 18-10-2015
								//System.out.println("tempTag............"+tempTag);
							}
							italicBold=false;
						}
						else
						{
							fin.seek(fpos);
							boldFlag= true;
							//System.out.println("eTag............."+eTag);
							tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); // UPDATED NEELS 22-02-04 to close italic
							
							boldFlag= false;
							
						}
					}
					else
					{
						fin.seek(fpos);
						boldFlag= true;
						//tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); //old 
						/**
						*Date : 16/05/2008 
						* 
						* Change By: Ravi
						* Change Point : In journal TRSTMH, bold text in other ref should be seperate with \\BrvOtherRef{{
						* Change Request By: TPMS (Vivek)
						* Check On : xml file no. 866.xml S100
						*/
						if((tag.equalsIgnoreCase("</CE:TEXTREF>"))&&(XT.jid.equalsIgnoreCase("TRSTMH")||XT.jid.equals("INHE"))&&(pit.equalsIgnoreCase("BRV")))
						{
							tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}\r\n\\BrvOtherRef{"); //22/08/2008
						}
						else
						{
							tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); 
						}
						boldFlag= false;
					}
					//End mark and commented below
					//three lines
					//boldFlag= true;
					//tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); // UPDATED NEELS 22-02-04 to close italic
					//boldFlag= false;
				}
				//Deo Kishor
				else if (eTag.equals("<CE:UNDERLINE>"))
				{
					underlineFlag= true;
					tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); 
					underlineFlag= false;
				}
				//end mark
				else if (eTag.equals("</CE:UNDERLINE>"))
				{
					tagValue.append("}}");
					underlineFlag = false;
				}
				else if (eTag.equals("<CE:DOCTOPICS>"))
				{
					
					tagValue.append(extractData("</CE:DOCTOPICS>", true)); 
					//System.out.println("tagValue : "+tagValue);
				}

				//abhay 01/07/2006
				else if(eTag.startsWith("<CE:LIST-ITEM"))
				{
					tagValue.append("\r\n\\item[]");
					tagValue.append(extractData("</CE:LIST-ITEM>", true)); 
				}
				//else if (eTag.startsWith("<CE:LINK LOCATOR"))//08-09-2015 update due to Locator position changed in dtd540
				else if (eTag.startsWith("<CE:LINK "))
				{
					//System.out.println(eTag);
					String tempfigLoc="";
					//tempfigLoc= eTag.substring(eTag.indexOf("<CE:LINK LOCATOR=\"")+18, eTag.indexOf("\"/>")).toUpperCase();
					tempfigLoc= getAttributeValue(tag, "LOCATOR");
					tagValue.append("{%\r\n\\epsfbox{"+tempfigLoc.toLowerCase()+".eps}}\r\n");
				}//end

				//avinandan added on 23-8-04
				else if (eTag.equals("<CE:CROSS-OUT>"))
				{
					Strikethrough= true;
					tagValue.append("{\\strike{"+getStartTagValue(eTag)+extractData(getEndtag(eTag), true)+"}}"); 
					Strikethrough= false;
				}
				else if (eTag.equals("</CE:CROSS-OUT>"))
				{
					tagValue.append("}}");
					Strikethrough = false;
				}
				//end mark
				//else if (eTag.equals("</CE:ITALIC>"))
				//	tagValue.append("}}");
				else if (eTag.equals("<CE:SUP>"))
				{
					mathFlag= false;
					tagValue.append(getStartTagValue(eTag)+extractData(getEndtag(eTag), true));// UPDATED NEELS 22-02-04 to close Math
					mathFlag= true;
					tagValue.append(geteEndTagValue("</CE:SUP>"));
					mathFlag= false;
					supFlag=false;
				}
				else if (eTag.equals("<MML:MATH>") || eTag.startsWith("<MML:MATH "))
				{
					mathFlag= true;
					String mmlimg="";
					mathvariant="";
					if(eTag.startsWith("<MML:MATH "))
					{
						mmlimg= getAttributeValue(eTag, "ALTIMG").toLowerCase();
					}
					if(protectCheck==true)
					{
						tagValue.append("\\protect\\begin{inlinestripns}{"+mmlimg+"}\\("+mt.processMML()+"\\)\\protect\\end{inlinestripns}");
					}
					else
					{//System.out.println("-----------------");
						tagValue.append("\\begin{inlinestripns}{"+mmlimg+"}\\("+mt.processMML()+"\\)\\end{inlinestripns}");
						//System.out.println("tagValue=22=> "+mt.processMML());
					}
					



					mathFlag= false;
					mathvariant="";
				}
				else if (eTag.startsWith("<CE:SUP "))
				{
					String supLoc= getAttributeValue(eTag, "LOC");
					if(supLoc.equals("PRE"))
						tagValue.append("{}"+getStartTagValue("<CE:SUP>")+extractData(getEndtag(eTag), true));
					if(supLoc.equals("POST"))
					{
						tagValue.append(getStartTagValue("<CE:SUP>") + extractData(getEndtag(eTag), true));
//						System.out.println(tagValue);
					}
					tagValue.append(geteEndTagValue("</CE:SUP>"));
					mathFlag= false;

				}
				else if (eTag.startsWith("<CE:INF "))
				{
					String supLoc= getAttributeValue(eTag, "LOC");
					if(supLoc.equals("PRE"))
					{
						tagValue.append("{}"+getStartTagValue("<CE:INF>")+extractData(getEndtag(eTag), true));
					}
					tagValue.append(geteEndTagValue("</CE:INF>"));
					mathFlag= false;
					rmboxFlag=false;

				}
				else if (eTag.equals("</CE:SUP>"))
				{
					tagValue.append(geteEndTagValue(eTag));
					mathFlag= false;
					supFlag=false;
				}
				else if (eTag.equals("<CE:INF>"))
				{
					mathFlag= false;
					//System.out.println("--------------->"+getStartTagValue(eTag));
					tagValue.append(getStartTagValue(eTag));

					//System.out.println("TT : "+tagValue);
					String t0 = new String();
					t0 = extractData(getEndtag(eTag), true);
					tagValue.append(t0);
					//System.out.println("Inf. value-->"+t0);
					//try{System.in.read();}catch(Exception e){}
					mathFlag= true;
					tagValue.append(geteEndTagValue("</CE:INF>"));
					//System.out.println("tagValue-->"+tagValue);
					//try{System.in.read();}catch(Exception e){}
					infFlag=false;
					rmboxFlag=false;
					
					mathFlagINF=true;
				}
				else if (eTag.equals("</CE:INF>"))
				{
					tagValue.append(geteEndTagValue(eTag));
					//System.out.println("---------------------------------");
					rmboxFlag=false;
					mathFlag= false;
					infFlag=false;
				}
				else if (eTag.startsWith("<CE:CROSS-REF REFID"))
				{
					if(tagValue.toString().endsWith("}}}\\)}{}"))//{{\rmbox{a}}}\)}{}
					{
						tagValue.insert(tagValue.lastIndexOf("}}}\\)}{}"),",");
						//System.out.println(tagValue+"\n");//System.in.read();
					}
					tagValue.append(processCrossRef(eTag));
				}
				else if (eTag.startsWith("<CE:CROSS-REFS REFID"))
					tagValue.append(processCrossRef(eTag));
				else if (eTag.startsWith("<CE:CROSS-REF "))//28-08-2012 JADTD520 Updation
				{
					if(tagValue.toString().endsWith("}}}\\)}{}"))//{{\rmbox{a}}}\)}{}
					{
						tagValue.insert(tagValue.lastIndexOf("}}}\\)}{}"),",");
						//System.out.println(tagValue+"\n");//System.in.read();
					}
					tagValue.append(processCrossRef(eTag));
				}
				else if (eTag.startsWith("<CE:CROSS-REFS "))//28-08-2012 JADTD520 Updation
					tagValue.append(processCrossRef(eTag));
				else if (eTag.startsWith("<CE:HSP "))
				{
					String spaceValue= getAttributeValue(eTag, "SP");
					
					if(!spaceValue.equals("0.12"))//06/11/2007
					tagValue.append(spaceTag(spaceValue));
				}
				else if (eTag.equals("<CE:DISPLAYED-QUOTE>") || eTag.startsWith("<CE:DISPLAYED-QUOTE")) // 04-01-2005
					tagValue.append(processQuote());
				else if (eTag.startsWith("<CE:LIST"))
					tagValue.append(processList());
				else if (eTag.startsWith("<CE:DISPLAY"))
				{
					isDisplayTextBox=true;//23-12-2010
					tagValue.append(processDisplayObjects());
					isDisplayTextBox=false;//23-12-2010
				}
				else if (eTag.startsWith("<CE:INTER-REF "))
				{
					//System.out.println("Hello XMLObjects.java extractData()...");
					//System.out.println("::eTag :: "+eTag);
					String ref1= getAttributeValue(eTag1, "XLINK:HREF".toLowerCase());
					String versionUrl= getAttributeValue(eTag1, "VERSIONURL".toLowerCase());
					System.out.println("VERSIONURL::"+versionUrl);
					String versionDate= getAttributeValue(eTag1, "VERSIONDATE".toLowerCase());
					versionDate = updateVersionFormat(versionDate);
					System.out.println("VERSIONDATE::"+versionDate);
					String refQRId= getAttributeValue(eTag1, "id".toLowerCase());
					QRRef111=ref1;
					if(refQRId.indexOf("QR",0)!=-1)
					{
						checkQRCodeId=true;
					}
					//System.out.println("xmlobj-->"+ref1);
					String ref= ref1.toLowerCase();
					String urlStr= extractData("</CE:INTER-REF>", true);

					ref=ref.replaceAll("%", "\\\\%");
					ref1=ref1.replaceAll("%", "\\\\%");
					String url="";
					if(bibStyle.equals("3") || bibStyle.equals("3a")|| bibStyle.equals("6") || bibStyle.equals("4")) 
					/*{
						if(ref.startsWith("doi:") && BiblographyContents.otherinterref==true)
						{
							//System.out.println("ref1---------> "+ref1);
							if(ref1.indexOf("&#x00026;",0)!=-1)
							{
								ref1=ref1.replaceAll("&#x00026;","%26");
								if(!versionUrl.isEmpty() && !versionDate.isEmpty()){//Attribute
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
								}else{
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";
								}
							}
							else{
//								url= "\\mychar\\url{"+ref1+"}{\\underline{"+urlStr+"}}{}";
								if(!versionUrl.isEmpty() && !versionDate.isEmpty()){//Attribute
									url= "\\mychar\\url{"+ref1+"}{\\underline{"+urlStr+"}}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
								}else{
									url= "\\mychar\\url{"+ref1+"}{\\underline{"+urlStr+"}}{}";
								}
							}
						}
						else
						{
							//System.out.println("qqqqqref1---------> "+ref1);
							if(ref1.indexOf("&#x",0)!=-1)
							{
								ref1=ref1.replaceAll("&#x00026;","%26");
								if(!versionUrl.isEmpty() && !versionDate.isEmpty()){//Attribute
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
								}else{
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";
								}
							}
							else{
//								url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";//Updated on 30-01-2017 for new dtd 5.50 to handle new attributes VERSIONURL and VERSIONDATE
								if(!versionUrl.isEmpty() && !versionDate.isEmpty()){//Attribute
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
								}else{
									url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";
								}
							}
							//System.out.println("qqqqqref1---------> "+url);
						}
					}*/
						
						
						
						if(ref.startsWith("doi:") && BiblographyContents.otherinterref==true)
						{
						    String doiUrl = ref1;

						    if(doiUrl.startsWith("doi:"))
						    {
						        doiUrl = doiUrl.replaceFirst("doi:", "doi.org/");
						    }



						    System.out.println("DEBUG ref1      = [" + ref1 + "]");
						    System.out.println("DEBUG doiUrl    = [" + doiUrl + "]");

						    if(doiUrl.indexOf("&#x00026;",0)!=-1)
						    {
						        doiUrl = doiUrl.replaceAll("&#x00026;","%26");

						        if(!versionUrl.isEmpty() && !versionDate.isEmpty())
						        {
						            url = "\\mychar\\url{" + doiUrl + "}{" + urlStr + "}{} "
						                + "(\\mychar\\url{" + versionUrl + "}{web archive link}{} "
						                + versionDate + ")";
						        }
						        else
						        {
						            url = "\\mychar\\url{" + doiUrl + "}{" + urlStr + "}{}";
						        }
						    }
						    else
						    {
						        if(!versionUrl.isEmpty() && !versionDate.isEmpty())
						        {
						            url = "\\mychar\\url{" + doiUrl + "}{\\underline{" + urlStr + "}}{} "
						                + "(\\mychar\\url{" + versionUrl + "}{web archive link}{} "
						                + versionDate + ")";
						        }
						        else
						        {
						            url = "\\mychar\\url{" + doiUrl + "}{\\underline{" + urlStr + "}}{}";
						        }
						    }

						    System.out.println("DEBUG FINAL URL = [" + url + "]");
						}

						
						
						
					else
					{
						//Added By Ravi [11/12/2006 Change Request By Vivek]
						//Change Point : for Ref Style 5 Contain url in comment[ no . (dot) required]
						if((bibStyle.startsWith("5"))||(bibStyle.startsWith("IChemE")))
						{
							booInterRef=true;
						}
						//end
						url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";//Updated on 30-01-2017 for new dtd 5.50 to handle new attributes VERSIONURL and VERSIONDATE
						if(!versionUrl.isEmpty() && !versionDate.isEmpty()){//Attribute
							url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
						}else{
							url= "\\mychar\\url{"+ref1+"}{"+urlStr+"}{}";
						}
					}
					
					
					
					System.out.println("DEBUG ref1 = [" + ref1 + "]");
					System.out.println("DEBUG ref  = [" + ref + "]");
					System.out.println("DEBUG urlStr = [" + urlStr + "]");
					System.out.println("DEBUG url BEFORE DOI replace = [" + url + "]");


					
					if(ref.startsWith("http") || ref.startsWith("mailto") || ref.startsWith("ftp"))
					{
							tagValue.append(url);						
					}
					//avinandan added on 1-9-04
					/*else if(ref.startsWith("doi:"))
					{
						url=url.replaceFirst("doi:", "http://doi.org/");
						tagValue.append(url);
					}*/
					
					else if(ref.startsWith("doi:"))
					{
					    url=url.replaceFirst("doi:", "doi.org/");
					    tagValue.append(url);
					    
					    System.out.println("DEBUG url after DOI replace rahul = [" + url + "]");
					}


					
					

					
					//end mark
					else
						tagValue.append(urlStr);

					//System.out.println("tagValue---------> "+tagValue);
				}
				else if (eTag.startsWith("<CE:INTER-REFS-TEXT"))
				{
					String urlStr= extractData("</CE:INTER-REFS-TEXT>", true);
					tagValue.append(urlStr);
				}
				else if (eTag.startsWith("<CE:INTER-REF-TITLE"))
				{
					String urlStr= extractData("</CE:INTER-REF-TITLE>", true);
				}
				else if (eTag.startsWith("<CE:COLLAB-AFF>"))
				{
					tagValue.append(" ");
				}
				else if(eTag.startsWith("<CE:ENUNCIATION"))
				{
					tagValue.append(processEnun(eTag));
				}
				else if(eTag.startsWith("<CE:DEF-LIST"))
				{
					tagValue.append(processDefList());
				}
				else if(eTag.startsWith("<CE:VSP"))
				{
					String hsp= getAttributeValue(eTag, "SP").toLowerCase ();
					if(eTag.equals("<CE:VSP/>"))
					{
						tagValue.append("\\\\"+"\n");//added by mukesh on 24-11-08
						//System.out.println("CAUGHT");
					}
					else if(hsp.equals("0.5")||hsp.equals(".5"))
					{
						tagValue.append("\\\\[5pt]");
					}
					else if(hsp.equals("1.0")||hsp.equals("1"))
					{
						tagValue.append("\\\\[10pt]");
					}
					else if(hsp.equals("1.5"))
					{
						tagValue.append("\\\\[15pt]");
					}
					else
					{
						System.out.println("[ERROR]: VSP "+hsp+" not define. Please contact to R&D Team.");
						System.exit(0);
					}
//					tagValue.append(processDefList());
				}
				else if (eTag.startsWith("<CE:FLOAT-ANCHOR"))
				{
					String floatRef= getAttributeValue(eTag, "REFID");
					try
					{
						if(floatRef.startsWith("FIG") || floatRef.startsWith("IMG") || floatRef.startsWith("GRA") || floatRef.startsWith("SC")  || floatRef.startsWith("PLA") || floatRef.startsWith("PHO") || floatRef.startsWith("EXH") || floatRef.startsWith("TBFIG") || floatRef.startsWith("PL") || floatRef.startsWith("UPI")  || floatRef.startsWith("TRE")  || floatRef.startsWith("DIA")  || floatRef.startsWith("ILL")  || floatRef.startsWith("PIC")  || floatRef.startsWith("CHA")  || floatRef.startsWith("CDE")  || floatRef.startsWith("CLE") || floatRef.startsWith("GPH")|| floatRef.startsWith("ABB")|| floatRef.startsWith("F0")) // 04-01-2005
						{
							//tagValue.append("\r\n"+(String)figuresInfo.get(figNo++));
							//commented by
							//Avinandan
							//	tagValue.append((String)figuresInfo.get(figNo++));
							//added by
							//avinanda till
							//mark
							if(figSchem.containsKey(floatRef))
							{
								tagValue.append((String)figSchem.get(floatRef));
								//System.out.println("tagValue--- > "+tagValue);
							}
							else
							{
								tagValue.append("\r\n\r\n<FIG/SCHEM NOT FOUND IN TABLE>\r\n\r\n");
							}
							//end mark
						}
					else if(floatRef.startsWith("TBL"))
					{
						//System.out.println ("floatRef : "+floatRef+" tablesInfo. "+tablesInfo.size());
						tagValue.append((String)tablesInfo.get(tblNo++));
					}
					else if(floatRef.startsWith("TB"))
						tagValue.append("\r\n"+(String)textBoxInfo.get(tbNo++));

					}
					catch(Exception e)
					{
					}

				}
				else if (eTag.startsWith("<CE:FOOTNOTE"))
				{
					if(fntStyle.equals("Y"))
					{
						footnote.append("\r\n" + processSimpleFootnote(eTag));
						System.out.println("::footnote 111>> "+footnote);
					}
					else
					{
						if(modelDText)
						{
							//tagValue=tagValue;
							if(isTextBox)
							{
								footTagval=processSimpleFootnote(eTag);
							}
							else
							{
								tagValue.append(processSimpleFootnote(eTag));
							}
						}
						else
						{
							tagValue.append(processSimpleFootnote(eTag));
						}
					}
				}
				else if(eTag.startsWith("<CE:TEXTBOX "))//28-08-2012 JADTD520 Updation
				{
					isTextBox = true;
					tagValue.append(processTextBox(eTag));
					isTextBox = false;
				}
				else if(eTag.startsWith("<CE:TEXTBOX"))
				{
					isTextBox = true;
					tagValue.append(processTextBox(eTag));
					isTextBox = false;
				}
				else if(eTag.equals("<CE:SMALL-CAPS>"))
				{
					tagValue.append("{\\sc{");
				}				
				else if(eTag.equals("<CE:SANS-SERIF>"))
				{
					tagValue.append("{\\sf{");
				}
				else if(eTag.equals("</CE:SANS-SERIF>"))
				{
					tagValue.append("}}");
				}
				else if(eTag.equals("<CE:MONOSPACE>"))
				{
					tagValue.append("{\\tt{");
				}
				else if(eTag.equals("</CE:MONOSPACE>"))
				{
					tagValue.append("}}");
				}
				else if(eTag.equals("</CE:SMALL-CAPS>"))
				{
					tagValue.append("}}");
				}
				else if(eTag.equals("<TB:LEFT-BORDER/>"))
				{
					tagValue.append("<LEFT>");
				}
				else if(eTag.equals("<TB:RIGHT-BORDER/>"))
				{
					tagValue.append("<RIGHT>");
				}
				else if(eTag.equals("<TB:TOP-BORDER/>"))
				{
					tagValue.append("<TOP>");
				}
				else if(eTag.equals("<TB:BOTTOM-BORDER/>"))
				{
					tagValue.append("<BOTTOM>");
				}
				else if (eTag.startsWith("<CE:INTRA-REF "))//28-08-2012 JADTD520 Updation
				{
					String ref1= getAttributeValue(eTag1, "XLINK:HREF".toLowerCase());
					//System.out.println("xmlobj-->"+ref1);
					
					String PiiStr= extractData("</CE:INTRA-REF>", true);
					tagValue.append("\\intraref{"+ref1+"}{"+PiiStr+"}");
				}
				else//28-08-2012 JADTD520 Updation
				{
					if(!eTag.startsWith("</"))
					{
						//System.out.println("\r\n************************************************************");
						//System.out.println("ERROR: TAG "+eTag+" IS NOT HANDLED IN XT. PLEASE CONTECT TO TEX R&D");
						//System.out.println("************************************************************");
					/*	try
						{
							Thread.sleep(1000);
						}
						catch (Exception e){}*/
					}
				}
				
			}
			else
			{
				if(ch == '^' || ch == '{' || ch == '}' || ch == '_' || ch == '$' || ch == '%' || ch == '#' || ch == '"')
				{
					tagValue.append("\\"+ch);
				}
				else if((ch=='-') && (mathFlag==true))
					tagValue.append("\\hbox{-}");
				else if(ch== '\\')
					tagValue.append("\\(\\backslash\\)");
				else if (ch== '&')
				{
					
						
					//mathFlag=false;

					if(mathFlagINF==true)
					{
						mathFlag=false;
						mathFlagINF=false;
					}
					String entity= findEntity();
					//System.out.println("entity : "+entity);
					//System.in.read();
					tagValue.append(entity);
					
				}
				//added by Ravi [Change by Usmani mail date 06/11/2006]
				else if((XT.jid.equalsIgnoreCase("RETAIL")) && (ch== '.'))
				{
					//tagValue.append(',');
					tagValue.append(ch);
					
				}
				//here end
		
				else
				{
						tagValue.append(ch);
					
				}	
				
			}
		}	
		
		//System.out.println("+tagValue------ "+tagValue+"+");
		//System.in.read();
		//System.out.println("indexOfindexOfindexOf "+tagValue.indexOf("\\\\\n"));
		//tagValue=new StringBuffer(new String(tagValue.toString()).replaceAll("\\\\\\\\"+"\n\n\n","\\\n"));
		//tagValue=new StringBuffer(new String(tagValue.toString()).replaceAll("\\\\\\\\"+"\n\n","\\\n"));
		//System.out.println("tagValue.toString()>>> "+tagValue.toString());
		return tagValue.toString();
	}
	public String updateVersionFormat(String vrDate){
//		String versionDate = vrDate;
		String versionDate = vrDate;
		int day = 0;
		int month = 0;
		int year = 0;
		String months[] = {"\\MonthNotDefine","January","February","March","April","May","June","July","August","September","October","November","December"};
		if(versionDate.indexOf("-")!=-1){
			String dates[] = versionDate.split("-");
			year=Integer.parseInt(dates[0]);
			month=Integer.parseInt(dates[1]);
			day=Integer.parseInt(dates[2]);
			versionDate = day+" "+months[month]+" "+year;
		}
		return versionDate;
	}

	public String processDisplayObjects()throws java.io.IOException
	{
		StringBuffer displayText= new StringBuffer();
		String tag       = "";
		String equationNo= "";
		String eqid      = "";
		String queryStr="";
		while (!tag.equals("</CE:DISPLAY>"))
		{

			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				
				if(tag.startsWith("<CE:TABLE"))
				{
					if(XT.stage.equalsIgnoreCase("s100"))
					{//System.out.println("wwwwwwwwwwwwwwwwwwwww------------------------------------------------");
						ProcessCalsMultiTbl tbl=new ProcessCalsMultiTbl(this);
						String ttttttttt = tbl.processDisplayTable(tag);
						ttttttttt = ttttttttt.replaceAll("\\\\begin\\{tabular","\r\n\\\\begin{tabular");
						//displayText.append(ttttttttt);	
						//System.out.println("ttttttttt- "+ttttttttt);
						//System.in.read();
						//added by mukesh on 24-11-08 request by TPMS
						if((xt.modelStyle.equalsIgnoreCase("-moddfrench"))||(xt.modelStyle.equalsIgnoreCase("-modefrench")))
						{
							if((ttttttttt.indexOf("\\begin{table}",0))!=-1)
							{
								ttttttttt = ttttttttt.replaceAll("\\\\begin\\{table\\}","\r\n\\\\begin{texttable}");
								ttttttttt = ttttttttt.replaceAll("\\\\end\\{table\\}","\\\\end{texttable}");
							}else{
								//ttttttttt = ttttttttt.replaceAll("\\\\begin\\{tabular","\\\\begin{texttable} \n\\\\tbl{}{% \n\\\\begin{tabular");
								//ttttttttt = ttttttttt.replaceAll("\\\\end\\{tabular\\*}","\\\\end{tabular*}} \n\\\\end{texttable}");
								StringBuffer s=new StringBuffer(ttttttttt);
								s=s.insert(0,"\r\n\\begin{texttable} \n\\tbl{}{%");
								s=s.insert(s.length(),"}\n\\end{texttable}");
								ttttttttt=s.toString();
							}
							displayText.append(ttttttttt);	
						}else
						{
							displayText.append(ttttttttt);	
						}
					}
					else
					{		
							//System.out.println("------------------------------------------------");
							ProcessCalsMultiTbl tbl=new ProcessCalsMultiTbl(this);
							//ProcessCalsTbl tbl=new ProcessCalsTbl(this);
							String ttttttttt = tbl.processDisplayTable(tag);
							ttttttttt = ttttttttt.replaceAll("\\\\begin\\{tabular","\r\n\\\\begin{tabular");
							//displayText.append(ttttttttt);
							if((xt.modelStyle.equalsIgnoreCase("-moddfrench"))||(xt.modelStyle.equalsIgnoreCase("-modefrench")))
							{
								/*ttttttttt = ttttttttt.replaceAll("\\\\begin\\{tabular","\\\\begin{texttable} \n\\\\tbl{}{% \n\\\\begin{tabular");
								/System.out.println("indexxxx "+ttttttttt.indexOf("\\end{tabular*}"));
								ttttttttt = ttttttttt.replaceAll("\\\\end\\{tabular\\*}","\\\\end{tabular*}} \n\\\\end{texttable}");
								//\begin{texttable} \tbl{}{% \begin{tabular*}
								//\end{tabular*}
								//\end{tabular*}} \end{texttable}*/
								if((ttttttttt.indexOf("\\begin{table}",0))!=-1)
								{
									ttttttttt = ttttttttt.replaceAll("\\\\begin\\{table\\}","\r\n\\\\begin{texttable}");
									ttttttttt = ttttttttt.replaceAll("\\\\end\\{table\\}","\\\\end{texttable}");
								}else{
									//ttttttttt = ttttttttt.replaceAll("\\\\begin\\{tabular","\\\\begin{texttable} \n\\\\tbl{}{% \n\\\\begin{tabular");
									//ttttttttt = ttttttttt.replaceAll("\\\\end\\{tabular\\*}","\\\\end{tabular*}} \n\\\\end{texttable}");
									StringBuffer s=new StringBuffer(ttttttttt);
									s=s.insert(0,"\r\n\\begin{texttable} \n\\tbl{}{%");
									s=s.insert(s.length(),"}\n\\end{texttable}");
									ttttttttt=s.toString();
								}


								displayText.append(ttttttttt);	
							}else
							{
								displayText.append(ttttttttt);	
							}
							
					}
				}
				else if (tag.startsWith("<CE:LABEL>"))
				{
					mathFlag=false;
					equationNo= extractData("</CE:LABEL>", true);
					mathFlag=true;
				}
				else if (tag.startsWith("<CE:FORMULA"))
				{
					mathFlag=true;//rajeev-6oct
					eqid= getAttributeValue(tag, "ID");
					//System.out.println("eqid==> "+eqid);
				
				}				
				else if (tag.startsWith("</CE:FORMULA>"))
				{
					
					
					
					if(displayText.indexOf("\\begin{equation}")==-1 && displayText.indexOf("\\[")==-1)
					{
						
						if(equationNo.length()>0 && eqid.length()>0)
						{						
							displayText=displayText.insert(0,"\r\n\\begin{equation}\\tag{"+getHypertarget(eqid)+equationNo+"}\r\n");
							displayText=displayText.insert(displayText.length(),"\\end{equation}\r\n");						
						}
						else if(equationNo.length()>0)
						{						
							displayText=displayText.insert(0,"\r\n\\begin{equation}\\tag{"+equationNo+"}\r\n");
							displayText=displayText.insert(displayText.length(),"\\end{equation}\r\n");						
						}
						else
						{
							displayText=displayText.insert(0,"\r\n\\[\r\n");
							displayText=displayText.insert(displayText.length(),"\\]\r\n");						
						}
						

					}
					
					mathFlag=false;
				}
				else if (tag.equals("<MML:MATH>") || tag.startsWith("<MML:MATH "))
				{
					
					mathFlag= true;
					String mmlimg="";
					mathvariant="";
					if(tag.startsWith("<MML:MATH "))
						mmlimg= getAttributeValue(tag, "ALTIMG").toLowerCase();
					if (equationNo.length()>0)
					{

						if(protectCheck==true)
							displayText.append("\r\n\\protect\\begin{inlinestripns}{"+mmlimg+"}\r\n\\begin{equation}\\tag{");
						else
							displayText.append("\r\n\\begin{inlinestripns}{"+mmlimg+"}\r\n\\begin{equation}\\tag{");
						if(eqid.length()>0)
							displayText.append(getHypertarget(eqid));
						if(protectCheck==true)
						{
							displayText.append(equationNo+"}\r\n"+mt.processMML()+"\r\n\\end{equation}\r\n\\protect\\end{inlinestripns}");
							//System.out.println("tagValue=22=> "+mt.processMML());
						}else{
							displayText.append(equationNo+"}\r\n"+mt.processMML()+"\r\n\\end{equation}\r\n\\end{inlinestripns}");
							//System.out.println("tagValue=33=> "+mt.processMML());
						}
					}
					else
					{
						if(protectCheck==true)
						{	
							displayText.append("\r\n\\protect\\begin{inlinestripns}{"+mmlimg+"}\r\n\\[\r\n"+mt.processMML()+"\r\n\\]\r\n\\protect\\end{inlinestripns}");
							//System.out.println("tagValue=44=> "+mt.processMML());
						}
						else{
								displayText.append("\r\n\\begin{inlinestripns}{"+mmlimg+"}\r\n\\[\r\n"+mt.processMML()+"\r\n\\]\r\n\\end{inlinestripns}");
								//System.out.println("tagValue=55=> "+mt.processMML());
						}
					}
					
					mathFlag= false;
					mathvariant="";
				}
				else if (tag.equals("<CE:CHEM>"))
				{
//					mathFlag= true;
					debug= true;
					// Math env. not required (17-May-203)
					// displayText.append("\\("+extractData("</CE:CHEM>", true)+"\\)");
					///String tttt=processDisplayChemFormula(equationNo, eqid);
					///System.out.println("Equation-->"+tttt);
					displayText.append(processDisplayChemFormula(equationNo, eqid));
					debug= false;
					
//					mathFlag= false;
				}
				else if (tag.startsWith("<CE:FIGURE"))
				{
					
								
					if(((HeadGroup.artDochead.equalsIgnoreCase("Technique chirurgicale")|| HeadGroup.artDochead.equalsIgnoreCase("Point technique")|| HeadGroup.artDochead.equalsIgnoreCase("Geste de base")||HeadGroup.artDochead.equalsIgnoreCase("Technical point")||HeadGroup.artDochead.equalsIgnoreCase("Basic maneuver")||HeadGroup.artDochead.equalsIgnoreCase("Surgical technique"))&& pit.equalsIgnoreCase("SCO")&&( jid.equalsIgnoreCase("JCHIR")|| jid.equalsIgnoreCase("JCHIRV")|| jid.equalsIgnoreCase("JVS")))&& BodyGroup.firstSection==true){
						figureValue=processFigure(tag);
						
						//ModleBySevtion.append(secTitle+"}");
					}
					else if((HeadGroup.artDochead.equalsIgnoreCase("Student corner"))&& pit.equalsIgnoreCase("SCO")&&( jid.equalsIgnoreCase("GCB")||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))))//15-06-2010
						{
							figureValue=processFigure(tag);
						}
					else{
					displayText.append(processFigure(tag));
					}
					//System.out.println("displayText==> "+displayText);
					//System.in.read();
					
				}
				//else if (tag.startsWith("<CE:LINK LOCATOR"))//08-09-2015 update due to Locator position changed in dtd540
				else if (tag.startsWith("<CE:LINK "))
				{
					String tempfigLoc="";
					//tempfigLoc= tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"/>")).toUpperCase();
					tempfigLoc= getAttributeValue(tag, "LOCATOR");
					displayText.append("{%\r\n\\epsfbox{"+tempfigLoc.toLowerCase()+".eps}}\r\n");
				}
				else if(tag.startsWith("<CE:TEXTBOX "))//28-08-2012 JADTD520 Updation
				{
					isTextBox = true;
					isDisplayTextBox=true;//23-12-2010
					displayText.append(processTextBox(tag));
					isTextBox = false;
				}
				else if(tag.startsWith("<CE:TEXTBOX"))
				{
					isTextBox = true;
					isDisplayTextBox=true;//23-12-2010
					displayText.append(processTextBox(tag));
					isTextBox = false;
				}
			}
/**
* Added By : Ravi 
* [24/05/2007]
* Change Point : Handle query tag in math eqation
*/
			else
			{
				queryStr+=ch;				
			}
		}
		int mn = displayText.indexOf("\\end{equation}",0);
		if(mn !=-1)
		{
			displayText=displayText.insert(mn-2,queryStr);
		}
		queryStr="";
		//end
		return displayText.toString();
	}

	public String processDisplayChemFormula(String equationNo, String eqid)throws java.io.IOException
	{
		StringBuffer chemFormulaContents= new StringBuffer();
		String tag= "";
		boolean mmlFlag=false;
		String mmlimg="";
		boolean rmFlag=true;
		rmboxFlag=false;
		/*Avinanda commented
		if(equationNo.length()>0)
			chemFormulaContents.append("\r\n\\begin{equation}{("+equationNo+")}\r\n");
		else
			chemFormulaContents.append("\\(");
			*/
		
		mathFlag=true;
		//mathFlag=false;
		while(!tag.equals("</CE:CHEM>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if(tag.equals("<CE:INF>"))
				{
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=true;
					}
					chemFormulaContents.append("{_{");
					if(rmFlag==true)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
				}
				else if(tag.equals("</CE:INF>"))
				{
					chemFormulaContents.append("}}");
					rmboxFlag=false;
					infFlag=false;
					if(rmFlag==false)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
					else if(rmFlag==true)
					{
						//chemFormulaContents.append("}}{\\rm{");//updated on 02-12-2013, requirement by R&D/SQA for APCATB/12849 equation (4) and (5)
						chemFormulaContents.append("}}{\\rm{");
						rmFlag=true;
					}
				}
				else if(tag.equals("<CE:SMALL-CAPS>"))
				{
					chemFormulaContents.append("{\\sc{");
				}
				else if(tag.equals("</CE:SMALL-CAPS>"))
				{
					chemFormulaContents.append("}}");
				}
				else if (tag.equals("<CE:UNDERLINE>")) //rajeev
				{
					underlineFlag= true;
					chemFormulaContents.append(getStartTagValue(tag)+extractData(getEndtag(tag), true)+"}}"); 
					underlineFlag= false;
				}
				//end mark
				else if (tag.equals("</CE:UNDERLINE>"))
				{
					chemFormulaContents.append("}}");
					underlineFlag = false;
				}
				else if(tag.equals("<CE:ITALIC>"))
				{
					//chemFormulaContents.append("{\\rmbox{\\it{");
					//mathFlag=true;
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=false;
					}
					mathvariant="ITALIC";
				}
				else if(tag.equals("</CE:ITALIC>"))
				{
					//mathFlag=false;
					mathvariant="";
					if(rmFlag==false)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
				}
				else if(tag.equals("<CE:BOLD>"))
				{
					chemFormulaContents.append("{\\bf{");
				}
				else if(tag.equals("</CE:BOLD>"))
				{
					chemFormulaContents.append("}}");
				}				
				//avinandan added else if <mml:math
				else if (tag.equals("<MML:MATH>") || tag.startsWith("<MML:MATH "))
				{
					mmlFlag=true;
					mathvariant="";
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=false;
					}
					if(tag.startsWith("<MML:MATH "))
						mmlimg= getAttributeValue(tag, "ALTIMG").toLowerCase();
					if(protectCheck==true)
					{	
						chemFormulaContents.append(mt.processMML());
						//System.out.println("tagValue=66=> "+mt.processMML());
					}
					else{
							chemFormulaContents.append(mt.processMML());
							//System.out.println("tagValue=77=> "+mt.processMML());
					}

					
					mathvariant="";
					if(rmFlag==false)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=false;
					}
				}
				else if (tag.startsWith("<CE:HSP "))
				{
					String spaceValue= getAttributeValue(tag, "SP");
					if(!spaceValue.equals("0.12"))//06/11/2007
					chemFormulaContents.append(spaceTag(spaceValue));
				}
				else if (tag.equals("<CE:SUP>"))
				{
					//mathFlag= true;
					supFlag=true;
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=true;
					}
					chemFormulaContents.append("{^{");
					if(rmFlag==true)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
				}
				else if (tag.startsWith("<CE:SUP "))
				{
					//mathFlag= true;
					/*if(rmFlag==true)
					{
						chemFormulaContents.append("}");
						rmFlag=false;
					}*/
					String supLoc= getAttributeValue(tag, "LOC");
					if(supLoc.equals("PRE"))
					{
						supFlag=true;
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=true;
					}
					chemFormulaContents.append("{{}^{");
					if(rmFlag==true)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
					}
					/*	chemFormulaContents.append("{}"+getStartTagValue("<CE:SUP>")+extractData(getEndtag(tag), true));
					chemFormulaContents.append(geteEndTagValue("</CE:SUP>"));*/
			
				}
				else if (tag.startsWith("<CE:INF "))
				{
					//mathFlag= true;
					if(rmFlag==true)
					{
						chemFormulaContents.append("}}");
						rmFlag=false;
					}
					String supLoc= getAttributeValue(tag, "LOC");
					if(supLoc.equals("PRE"))
					{
						chemFormulaContents.append("{}"+getStartTagValue("<CE:INF>")+extractData(getEndtag(tag), true));
					}
					chemFormulaContents.append(geteEndTagValue("</CE:INF>"));
					rmboxFlag=false;
				}
				else if (tag.startsWith("<CE:GLYPH "))
				{
					chemFormulaContents.append("\\rmbox{"+replaceGlyph(tag)+"}");
				}
				//else if (tag.startsWith("<CE:LINK LOCATOR"))//08-09-2015 update due to Locator position changed in dtd540
				else if (tag.startsWith("<CE:LINK "))
				{
					String tempfigLoc="";
					//tempfigLoc= tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"/>")).toUpperCase();
					tempfigLoc= getAttributeValue(tag, "LOCATOR");
					chemFormulaContents.append("{%\r\n\\epsfbox{"+tempfigLoc.toLowerCase()+".eps}}\r\n");		

				}
				else if (tag.equals("</CE:SUP>"))
				{
			/*		if(mathFlag==true)
						chemFormulaContents.append("}}}");
					else */
						chemFormulaContents.append("}}");
					supFlag=false;
					if(rmFlag==false)
					{
						chemFormulaContents.append("{\\rm{");
						rmFlag=true;
					}
					else if(rmFlag==true)
					{
						chemFormulaContents.append("}}{\\rm{");
						rmFlag=true;
					}
				}
			}
			else
			{
				if(ch == '^' || ch == '{' || ch == '}' || ch == '_' || ch == '$' || ch == '%' || ch == '#' || ch == '"')
					chemFormulaContents.append("\\"+ch);
				else if(ch== '\\')
					chemFormulaContents.append("\\backslash");
				else if(ch=='-') 
					chemFormulaContents.append("\\hbox{-}");
				else if (ch== '&')
				{
					String chemEnt=findEntity();
					if(chemEnt.equals("'"))
						chemFormulaContents.append("\\hbox{"+chemEnt+"}");		
					else
						chemFormulaContents.append(chemEnt);		
				}
				else
					chemFormulaContents.append(ch);
			}

		}
		/*Avinandan Commented
		if(equationNo.length()>0)
			chemFormulaContents.append("\r\n\\end{equation}");
		else
			chemFormulaContents.append("\\)");
		return chemFormulaContents.toString();
*/
		//Avinandan addede this code
		
		
		mathFlag=false;		
		if(equationNo.length()>0)
		{
			
			if(mmlFlag==true)
			{
				if(chemFormulaContents.toString().startsWith("}}"))
					chemFormulaContents.delete(0,2);
				if(chemFormulaContents.toString().endsWith("{\\rm{"))		
					chemFormulaContents.delete(chemFormulaContents.lastIndexOf("{\\rm{"),chemFormulaContents.length());
				if(!chemFormulaContents.toString().startsWith("{\\rm"))
					chemFormulaContents.insert(0,"{\\rm{");
				if(mmlimg.length()>0)
				{
					if(eqid.length()>0)//rajeev //for generating targets of chemical equations
						return "\r\n\\protect\\begin{inlinestripns}{"+mmlimg+"}\r\n\\begin{equation}\\tag{"+getHypertarget(eqid)+equationNo+"}\r\n" + chemFormulaContents.toString()+"\r\n\\end{equation}\r\n\\protect\\end{inlinestripns}";
					else
						return "\r\n\\protect\\begin{inlinestripns}{"+mmlimg+"}\r\n\\begin{equation}\\tag{"+equationNo+"}\r\n" + chemFormulaContents.toString()+"\r\n\\end{equation}\r\n\\protect\\end{inlinestripns}";
				}
				else
				{
					if(eqid.length()>0)//rajeev
						return "\r\n\\begin{equation}\\tag{"+getHypertarget(eqid)+equationNo+"}\r\n" + chemFormulaContents.toString()+"\r\n\\end{equation}\r\n";
					else
						return "\r\n\\begin{equation}\\tag{"+equationNo+"}\r\n" + chemFormulaContents.toString()+"\r\n\\end{equation}\r\n";
				}
			}
			else
			{
				if(eqid.length()>0)//rajeev
					return "\r\n\\begin{equation}\\tag{"+getHypertarget(eqid)+equationNo+"}\r\n{\\rm{" + chemFormulaContents.toString()+"}}\r\n\\end{equation}\r\n";
				else
					return "\r\n\\begin{equation}\\tag{"+equationNo+"}\r\n{\\rm{" + chemFormulaContents.toString()+"}}\r\n\\end{equation}\r\n";
			}
		}
		else
		{
			if(mmlFlag==true)
			{
				if(chemFormulaContents.toString().startsWith("}}"))
					chemFormulaContents.delete(0,2);
				if(chemFormulaContents.toString().endsWith("{\\rm{"))		
					chemFormulaContents.delete(chemFormulaContents.lastIndexOf("{\\rm{"),chemFormulaContents.length());
				if(mmlimg.length()>0)
				{
					return "\r\n\\protect\\begin{inlinestripns}{"+mmlimg+"}\r\n\\[\r\n" + chemFormulaContents.toString()+"\r\n\\]\r\n\\protect\\end{inlinestripns}\r\n";
				}
				else
				{
					return "\r\n\\[\r\n" + chemFormulaContents.toString()+"\r\n\\]\r\n";
				}
			}
			else
			{
				return "\r\n\\[\r\n{\\rm{" + chemFormulaContents.toString()+"}}\r\n\\]\r\n";
			}
		}

	}


	/*
	* Function to generate coding for Inline Figures.
	*/
	public String processInlineFigure()throws IOException
	{
		String tag           = "";
		String figLoc        = "";
		String inlineFigInfo = "";

		while (!tag.equals("</CE:INLINE-FIGURE>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				//if (tag.startsWith("<CE:LINK LOCATOR"))//08-09-2015 update due to Locator position changed in dtd540
				if (tag.startsWith("<CE:LINK "))
				{
					//figLoc= tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"/>")).toUpperCase();
					figLoc= getAttributeValue(tag, "LOCATOR");
					/*if (figInfo.containsKey(new String(figLoc)))
					{
						String figH, figW;
						figH= figW= "";
						String tfig= new String(new String(figInfo.get(figLoc)+""));
						figW= tfig.substring(0, tfig.indexOf("/"));
						figH= tfig.substring(tfig.indexOf("/")+1);
						inlineFigInfo="\\insertFX{"+figW+"in}{"+figH+"in}{"+figLoc+".tif}";
					}
					else*/
					{
						//Conmmented by
						//Avinandan
						//inlineFigInfo="\r\n{%\r\n\\epsfbox{"+figLoc+".eps}";
						//Added by avinandan

						//inlineFigInfo="{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n";//original

						if(check_MassonJid==true)
						{
							if(figLoc.toLowerCase().startsWith("picto"))
							{
								inlineFigInfo="}\r\n\\pictogram{"+figLoc.toLowerCase();
							}
							else
							{
								inlineFigInfo="{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n";
							}
						}
						else
						{
							if(checkQRCodeId)
							{
								inlineFigInfo+="\r\n\\begin{QRcode}";
								inlineFigInfo+="\r\n\\centerline";
								inlineFigInfo+="{%\r\n\\inserttiff{"+figLoc.toLowerCase()+".tif}}";
								inlineFigInfo+="\r\n\\caption{{"+QRRef111+"}}" ;
								inlineFigInfo+="\r\n\\end{QRcode}";
							}
							else
							{
								inlineFigInfo="{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n";
							}
							
							
						}


					}
				}
			}
		}
		return inlineFigInfo;
	}

	public String processCrossRef(String etag)throws java.io.IOException
	{
		String tag= etag;
		StringBuffer crossRefInfo= new StringBuffer();
		String endTag= "";
		int ind= tag.indexOf("REFID=\"")+7;
		/*if(xmlObj.jid.equals("THOBK"))
		{
			thobkTblId=tag.substring(ind, tag.indexOf(" ",ind));
			if(thobkTblId.startsWith("tbl"))
				tblrefThobk=true;
		}*/
		if (tag.startsWith("<CE:CROSS-REFS")||tag.startsWith("<CE:CROSS-REFS "))//28-08-2012 JADTD520 Updation
		{
			//System.out.println ("tag: "+tag);
			if(specialLink== true)
				crossRefInfo.append("\\protect\\specialhyperlink{"+jid+aid+tag.substring(ind, tag.indexOf(" ",ind))+"}{");
			else if(protectCheck==true)
				crossRefInfo.append("\\protect\\hyperlink{"+jid+aid+tag.substring(ind, tag.indexOf(" ",ind))+"}{");
			else
			{
				if(tag.indexOf(" ",ind)!=-1)
				crossRefInfo.append("\\hyperlink{"+jid+aid+tag.substring(ind, tag.indexOf(" ",ind))+"}{");
				else
					crossRefInfo.append("\\hyperlink{"+jid+aid+tag.substring(ind, tag.indexOf("\">",ind))+"}{");
			}
			endTag= "</CE:CROSS-REFS>";
			//System.out.println ("crossRefInfo----------->"+crossRefInfo);
		}
		else
		{
			if(specialLink== true)
				crossRefInfo.append("\\protect\\specialhyperlink{"+jid+aid+tag.substring(ind, tag.indexOf("\">"))+"}{");
			else if(protectCheck==true)
				crossRefInfo.append("\\protect\\hyperlink{"+jid+aid+tag.substring(ind, tag.indexOf("\">"))+"}{");
			else
			{
				crossRefInfo.append("\\hyperlink{"+jid+aid+tag.substring(ind, tag.indexOf("\">"))+"}{");
				//System.out.println("crossRefInfo------------"+crossRefInfo);
			}
			endTag= "</CE:CROSS-REF>";
			
		}
		
		//Added by avinandan for unbreakable space in
		//table/figure/scheme citation
		String crossRefText=extractData(endTag, true);
		/*if(tblrefThobk==true)
		{
			crossRefText=crossRefText.replaceFirst("\\^\\{\\{\\rmbox","\\^{{\\Footid");
			tblrefThobk=false;
		}*/
		if((crossRefText.toUpperCase().startsWith("FIG"))||(crossRefText.toUpperCase().startsWith("TABLE"))||(crossRefText.toUpperCase().startsWith("SCHEME")))
		{
			crossRefText=crossRefText.replaceAll(" ","~");
			crossRefInfo.append(crossRefText);
			//System.out.println ("crossRefText-------------"+crossRefText);
		}
		else
		{
			crossRefInfo.append(crossRefText);
			//System.out.println("crossRefInfo--------------"+crossRefInfo);
		}
		 //end mark
		//commented by avinandan 
		//crossRefInfo.append(extractData(endTag, true));
		//end mark
		crossRefInfo.append("}{}");
//		System.out.println("CRoss Ref :\t" + crossRefInfo.toString());
		return crossRefInfo.toString();
	}

	public String getAbstractLanguage(String lan)
	{
		String temp = new String();
		LinkedList list = new LinkedList();
		list.add("DE");
		list.add("EN");
		list.add("ES");
		list.add("FR");
		list.add("PT");
		list.add("RU");
		list.add("IT");
		list.add("CA");
		int index = list.indexOf(lan);
		switch(index)
		{
			case 0 :
				temp = "GERMAN";
				break;
			case 1 :
				temp = "ENGLISH";
				break;
			case 2 :
				temp = "SPANISH";
				break;
			case 3 :
				temp = "FRENCH";
				break;
			case 4 :
				temp = "PORTUGUESE";
				break;
			case 5 :
				temp = "RUSSIAN";
				break;
			case 6 :
				temp = "ITALIAN";
				break;
			case 7 :
				temp = "CATALAN";//12-08-2011
				break;
			default :
				temp = "ENGLISH";
				break;
		}
		return temp;
	}

	public String spaceTag(String spaceValue) throws java.io.IOException
	{
		if(spaceValue.equals("1"))
		{
			spaceValue ="\\quad ";
		}
		else if(spaceValue.equals("1.0"))
		{
			spaceValue ="\\quad ";
		}
		else if(spaceValue.startsWith("0.5"))
		{
			spaceValue ="\\enspace ";
		}
		else if(spaceValue.equals("0.25"))
		{
			spaceValue ="\\,";
		}
		else if(spaceValue.equals("0.10"))
		{
			spaceValue ="\\,";
		}
		else if(spaceValue.equals("0.30"))
		{
			spaceValue ="\\,";
		}
		else if(spaceValue.equals("0.16"))//28-06-2010
		{
			spaceValue ="\\,";
		}
		else if(spaceValue.equals("2"))
		{
			spaceValue ="\\qquad  ";
		}
		else if(spaceValue.equals("2.0"))
		{
			spaceValue ="\\qquad  ";
		}
		else if(spaceValue.equals("3"))
		{
			spaceValue ="\\qquad\\quad  ";
		}
		else if(spaceValue.equals("3.0"))
		{
			spaceValue ="\\qquad\\quad  ";
		}
		else if(spaceValue.equals("3.5"))
		{
			spaceValue ="\\qquad\\quad\\enspace ";
		}
		else
		{
			System.out.println("[ERROR]: Space value "+spaceValue+" not define in conversion. Please contect R&D TeX team.");
			System.exit(0);
		}
		return spaceValue;
	}

	public String processList()throws java.io.IOException
	{
		/*
		* The element ce:list is used to capture free-format lists.
		* The element ce:list provides a way to capture lists, where the labels are left entirely to
		* the user.
		* A ce:list has an optional number or label (ce:label) and an optional section title
		* (ce:section-title). It has an optional id attribute so that it can become the target of a
		* cross-reference.
		* A list consists of one or more list items, ce:list-item. Each list item can have a ce:label,
		* containing the list item’s label, and consists of one ore more paragraphs, ce:para. If the
		* ce:label element is absent, then the item is indented, and the result is a “tab list”.
		*
		* A ce:list-item can have an id so that it can become the target of a cross-reference.
		*/
		StringBuffer listContents= new StringBuffer();
		String tag      = "";
		String listItem = "";
		String listLbl  = "";
		String listLblLast  = "";
		boolean firstP  = true;

		listContents.append("\r\n\\begin{enumerate}[]");
		while (!tag.equals("</CE:LIST>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.startsWith("<CE:LIST-ITEM"))
				{
					/* The element ce:list-item is used to capture list items within ce:list.*/
					firstP= true;
				}
				else if (tag.equals("</CE:LIST-ITEM>"))
				{
					listContents.append("\r\n\\item["+listLbl+"]"+listItem);					
					listLbl = "";
					//listItem="";
				}
				else if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					listContents.append("\r\n\\listhead{"+extractData("</CE:SECTION-TITLE>", true)+"}");
				}
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					if(firstP==true)
						listItem= extractData("</CE:PARA>", true);
					else
						listItem+= "\r\n\r\n"+extractData("</CE:PARA>", true);
					
					firstP= false;
				}
				else if (tag.equals("<CE:LABEL>"))
				{
					listLbl= extractData("</CE:LABEL>", true);
					listLblLast = listLbl;
				}
				else if (tag.startsWith("<CE:LIST"))
				{
					processList();
				}
			}
		}
		listContents.append("\r\n\\end{enumerate}");
		//Avinandan added
		String listText=listContents.toString();
		
		if(listLblLast.length()>0)
		{			
			if(listLblLast.indexOf("\\")!=-1)
				listLblLast=listLblLast.replaceAll("\\\\","\\\\\\\\");			
			listText=listText.replaceAll("\\\\begin\\{enumerate\\}\\[\\]", "\\\\begin{enumerate}["+listLblLast+"]");
			
		}
		//listContents=null;
		return listText;
		//end mark
		//commented by avinandan
		//return listContents.toString();
		//end mark
	}

	public String findEntity()throws java.io.IOException
	{
		StringBuffer xmlEntity= new StringBuffer();
		String texCode= "";
		char ch='\0';
		xmlEntity.append('&');
		while((ch= (char)fin.read())!=';')
			xmlEntity.append(ch);
		xmlEntity.append(ch);
		// first check greek characters here
		texCode=replace(xmlEntity.toString(), textEntityList);
		//System.out.println(xmlEntity.toString()+"::::"+texCode+":::ravi");
		//System.in.read();
		return texCode;
	}

	public String getAttributeValue(String tag, String attribute)
	{
//		System.out.println("TAG :: "+tag);
//		System.out.println("ATT :: "+attribute);
		String attributeValue= "";
		if (tag.indexOf(" "+attribute)>0)
		{
			attributeValue= "";
			for(int i=tag.indexOf(" "+attribute)+attribute.length(); i<tag.length(); i++)
			{
				char ch= tag.charAt(i);
				if (ch=='\"')
				{
					i++;
					while ((ch= tag.charAt(i++))!='\"')
						attributeValue+= ch;
					break;
				}
			}
		}
		return attributeValue;
	}

	public String getEndtag(String tag)
	{
		String endTag= "";
		endTag+="</";
		for(int i=1; i<tag.length(); i++)
		{
			char ch= tag.charAt(i);
			if(ch==' ' || ch=='>' || ch=='/')
				break;
			else
				endTag+=ch;
		}
		endTag+='>';
		//System.out.println("=============>"+endTag);
		return endTag;
	}
	public String getStartTagValue(String tag)throws java.io.IOException
	{
		//System.out.println("tag----> "+tag);;
		//System.in.read();
		StringBuffer tagValue= new StringBuffer();
		if (tag.equals("<CE:ITALIC>"))
		{
			if(boldFlag==true)
				tagValue.append("{\\bi{");
			else
				tagValue.append("{\\it{");
			italicFlag= true;
		}
		else if (tag.equals("<CE:BOLD>"))
		{
			//System.out.println("italicFlag-------------"+italicFlag);
			if(italicFlag==true)
				tagValue.append("{\\bi{");
			else
				tagValue.append("{\\bf{");
			boldFlag= true;
		}
		else if (tag.equals("<CE:UNDERLINE>"))
		{
			tagValue.append("{\\underline{");
			underlineFlag= true;
		}
		else if (tag.equals("<CE:SUP>"))
		{
			supFlag= true;
			if (mathFlag==true)
				tagValue.append("^{");
			else
			{
				tagValue.append("\\(^{");
				rmboxFlag=false;
				mathFlag= true;		//abhay 		
			}
			tagValue.append(getFont("</CE:SUP>"));
		}
		else if (tag.equals("<CE:INF>"))
		{
			if(infFlag==true)
			{
				mathFlag=false;
				sinfFlag=true;
			}
			infFlag= true;
			if(mathFlag==true)
				tagValue.append("_{");
			//added by avinandan on 4-8-4
			else if(italicFlag==true)
			{
				tagValue.append("\\(_{");
				rmboxFlag=false;
				mathFlag= true;
				mathvariant="ITALIC";
			}
			//end mark
			else
			{
				tagValue.append("\\(_{");
				rmboxFlag=false;
				mathFlag= true;
				mathvariant="NORMAL";
			}
			tagValue.append(getFont("</CE:INF>"));
		}
		else if (tag.startsWith("<CE:CROSS-REF REFID"))
		{
			tagValue.append(processCrossRef(tag));
		}
		else if (tag.startsWith("<CE:CROSS-REFS REFID"))
			tagValue.append(processCrossRef(tag));
		else if (tag.startsWith("<CE:CROSS-REF "))//28-08-2012 JADTD520 Updation
		{
			tagValue.append(processCrossRef(tag));
		}
		else if (tag.startsWith("<CE:CROSS-REFS "))//28-08-2012 JADTD520 Updation
			tagValue.append(processCrossRef(tag));
		return tagValue.toString();
	}

	public String getFont(String endTag) throws IOException
	{
		long filePos= fin.getFilePointer();
		String supStr= "";
		String etag= "";
		String font="";
		while (!etag.equals(endTag))
		{
			char ch=(char) fin.read();
			if (ch=='<')
			{
				etag= getTag().toUpperCase();
				supStr+=etag;
			}
			else
				supStr+=ch;
		}
		//System.out.println("HHH : "+supStr);
		//System.exit(0);
		if(supStr.startsWith("<CE:BOLD><CE:ITALIC>"))
		{
			//font="{\\bf{";
		}
		else if ((supStr.startsWith("<CE:ITALIC>")) && (supStr.endsWith("</CE:INF>")))
		{
			font="{\\rmbox{";
			rmboxFlag=true;			
		}
		else if ((supStr.startsWith("<CE:ITALIC>")) && (supStr.endsWith("</CE:SUP>")))
		{
			font="{\\rmbox{";
			rmboxFlag=true;			
		}
		else if (supStr.startsWith("<CE:ITALIC>"))// && (!supStr.endsWith("</CE:INF>")))
		{
			//italicFlag = true;
			//font="{\\it{";
			font="{{";
			//mathvariant="ITALIC";//

		}
		else if(endTag.equals("</CE:INF>") || endTag.equals("</CE:SUP>")) /// SUBRATA TAKE CARE THIS PROBLEM LATER :: $\IT\IT\IT{}$
		{
			if(italicFlag == true)
			{
				font="{{";
			}
			else if(italicBold == true)
			{
				font="{\\bm{";
			}
			else if(boldFlag == true)
			{
				font="{{";
			}
			else
			{
				font="{\\rmbox{";
				rmboxFlag=true;
			}
		}
		else
		{
			font="{\\rm{";
			rmboxFlag=true;
		}
		fin.seek(filePos);
		//System.out.println("HHH : "+font);
		//System.exit(0);
		return font;
	}

	public String geteEndTagValue(String eTag)
	{
		StringBuffer tagValue= new StringBuffer();
		String tag= eTag;
		if (tag.equals("</CE:SUP>"))
		{
			if(mathFlag==true)
				tagValue.append("}}}\\)");
			else
				tagValue.append("}}}");
			if(supFlag== true && mathFlag==true)
				mathFlag= false;
			supFlag= false;
		}
		else if (tag.equals("</CE:INF>"))
		{
			//System.out.println("mathFlag  "+mathFlag);
			if(mathFlag==true)
				tagValue.append("}}}\\)");
			else
				tagValue.append("}}}");
			if(italicFlag==true)
				mathvariant="NORMAL";
			if(infFlag== true && mathFlag==true)
				mathFlag= false;
			infFlag= false;
			if(sinfFlag==true)
			{
				sinfFlag=false;
				infFlag=true;
				mathFlag=true;
			}
		}
		return tagValue.toString();
	}

	public String getHypertarget(String idLink)
	{
		//System.out.println("-----------"+jid+aid+idLink.toUpperCase());
		//try{System.in.read();}catch(Exception e){}
		if(idLink.length()>0)
			return "\\hypertarget{"+jid+aid+idLink.toUpperCase()+"}{}";
		else
			return idLink;
	}

	public void makeEntityList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "xmlentconv.dbf");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			StringTokenizer stTokens=new StringTokenizer(lineStr,";");
			int cnt=1;
			String ent="";
			while (stTokens.hasMoreTokens())
			{
				String st= stTokens.nextToken();
				if(cnt==1)
					ent= st+";";
				else if (cnt==2)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						mathEntityList.put(new String(ent), new String(st));
				}
				else if (cnt==3)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						textEntityList.put(new String(ent), new String(st));
				}
				cnt++;
			}
		}
	}
//***************************************[22/08/2007]***************************************************
public void makeEntityListFranch()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "Asscixmlentconv.dbf");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			StringTokenizer stTokens=new StringTokenizer(lineStr,";");
			int cnt=1;
			String ent="";
			while (stTokens.hasMoreTokens())
			{
				String st= stTokens.nextToken();
				if(cnt==1)
				{
					ent= st+";";
					//System.out.println("ent : "+ent);
					//System.in.read();
				}
				else if (cnt==2)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						mathEntityList.put(new String(ent), new String(st));
				}
				else if (cnt==3)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						textEntityList.put(new String(ent), new String(st));
				}
				cnt++;
			}
		}
		
	}

public void makeEntityListGulliver()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "GulliverXmlentconv.dbf");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			StringTokenizer stTokens=new StringTokenizer(lineStr,";");
			int cnt=1;
			String ent="";
			while (stTokens.hasMoreTokens())
			{
				String st= stTokens.nextToken();
				if(cnt==1)
				{
					ent= st+";";
					//System.out.println("ent : "+ent);
					//System.in.read();
				}
				else if (cnt==2)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						mathEntityList.put(new String(ent), new String(st));
				}
				else if (cnt==3)
				{
					//if (st.length()>0)
					if (!st.equals(" "))
						textEntityList.put(new String(ent), new String(st));
				}
				cnt++;
			}
		}
		
	}

//
//********************************************************************************************


//*****************************************[04/12/2007]*****************************************
public void makeMassonJidList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "MassonJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			MassonJidList.add(lineStr.trim());
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}
public void makeCatalanJidList()throws java.io.IOException//12-08-2011
	{
		InputStream in=new FileInputStream(XT.databasePath + "CatalanJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			CatalanJidList.add(lineStr.trim());
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}
	
public void makeItalianJidList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "ItalianJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			ItalianJidList.add(lineStr.trim());
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}
public void makeSpanishJidList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "SpanishJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			SpanishJidList.add(lineStr.trim());
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}
	public void makePortugueseJidList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "PortugueseJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			PortugueseJidList.add(lineStr.trim());
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}
	
public void makeGulliverJidList()throws java.io.IOException
	{
		InputStream in=new FileInputStream(XT.databasePath + "GulliverList.dbf");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			String GulliverJid=lineStr;
			String [] Arr_Gulliver=GulliverJid.split("<-->");
			//System.out.println(Arr_Gulliver[1]+"---------> "+Arr_Gulliver[0]);
			GulliverJidList.add(Arr_Gulliver[0]);
			GuliverCutOff.put(Arr_Gulliver[0],Arr_Gulliver[1]);
		}
		//System.out.println("MassonJidList : "+MassonJidList);
		//	try{System.in.read();}catch(Exception e){}
	}

public void TableGulliverJidCutoffList()throws java.io.IOException//19/03/2009
	{
		InputStream in=new FileInputStream(XT.databasePath + "Table.dbf");
		BufferedReader fin= new BufferedReader(new InputStreamReader(in));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			String GulliverJid=lineStr;
			String [] Arr_Gulliver=GulliverJid.split("<-->");
			//System.out.println(Arr_Gulliver[0]+"---------> "+Arr_Gulliver[1]);
			
			TableGuliverCutOff.put(Arr_Gulliver[0],Arr_Gulliver[1]);
		}
		
	}
//**********************************************************************************************
	public String replace(String mmlEnt, Hashtable entList)
	{
//		System.out.println("mmlEnt====>"+mmlEnt);
		//XT server_Xt=new XT();
		String filterStr=mmlEnt;
		/*if(filterStr.indexOf("Delta")!=-1)
		{
			System.out.println("Mathvariant"+mathvariant);
			System.out.println("MathFlag"+mathFlag);
			System.out.println("SupFlag"+supFlag);
			System.out.println("rmboxFlag"+rmboxFlag);
			System.exit(0);
		}*/
		
		//System.out.println("mathFlag======>"+mathFlag);
		//System.out.println("mathvariant====================>"+mathvariant);
		//System.out.println("entList==> "+entList);
		//try{	System.in.read();}catch(Exception e){}
		if (entList.containsKey(new String(mmlEnt)))
		{//System.out.println("mmlEnt"+mmlEnt)
			filterStr= ((String)entList.get(new String(mmlEnt)));//.trim();
			//System.out.println("filterStr   "+filterStr);
			if (filterStr.startsWith("##"))
			{
				//Upper Case Greek Characters
				if (mathFlag==true)
				{
					if(mathvariant.equals("BOLD"))
						filterStr= "{\\bm{\\it"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}}";
					else if(mathvariant.equals("BOLD-NORMAL"))
						filterStr= "{\\bm{\\rm"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}}";
					else if(mathvariant.equals("NORMAL"))
						filterStr= "{\\rm"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}";
					else
						filterStr= "{\\it"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}";
						//UPPER CASE GREEK LETTER SHOULD BE IN ROMAN DEFAULT
				}
				else
				{
					if(mathvariant.equals("BOLD") || (boldFlag== true))
						filterStr= "{\\bm{\\rm"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}}";
					else if(mathvariant.equals("BOLD-ITALIC"))
						filterStr= "{\\bm{\\it"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}}";
					else if(mathvariant.equals("ITALIC") || (italicFlag== true))
						filterStr= "{\\it"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}";
					else
						filterStr= "{\\rm"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}";
					filterStr= "\\("+filterStr+"\\)";
				}
			}
			else if (filterStr.startsWith("#"))
			{
				//Lower Case Greek Characters
//				System.out.println(mathvariant+"::mathvariant::"+filterStr);
				if (mathFlag==true)
				{
					if(mathvariant.equals("BOLD"))
						filterStr= "{\\bm{\\it"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}}";
					else if(mathvariant.equals("BOLD-NORMAL"))
						filterStr= "{\\bm{\\rm"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}}";
					else if(mathvariant.equals("NORMAL"))
						filterStr= "{\\rm"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}";
					else
						filterStr= "{\\it"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}";
				}
				else
				{
					if((mathvariant.equals("BOLD")) || (boldFlag== true))
						filterStr= "{\\bm{\\rm"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}}";
					else if(mathvariant.equals("BOLD-ITALIC"))
						filterStr= "{\\bm{\\it"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}}";
					else if(mathvariant.equals("ITALIC") || (italicFlag== true))
						filterStr= "{\\it"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}";
					else if(mathvariant.equals("NORMAL"))
						filterStr= "{\\rm"+filterStr.substring(filterStr.indexOf("##\\")+3)+"}";
					else
						filterStr= "{\\rm"+filterStr.substring(filterStr.indexOf("#\\")+2)+"}";
					filterStr= "\\("+filterStr+"\\)";
				}
			}
			else if (filterStr.startsWith("%"))
			{
				//System.out.println("1bbbbbbbbbbbbb  "+filterStr);;
				filterStr= filterStr.substring(1);
				if (mathFlag== true)
				{
					
					filterStr= filterStr;
					//System.out.println("1  "+filterStr);;
				}
				else
				{
					filterStr= "\\("+filterStr+"\\)";
					//System.out.println("2 "+filterStr);
				}
			}
			else
			{

				
				filterStr= filterStr;
				//System.out.println("3 "+filterStr);
				
				
			}
			//try{System.in.read();}catch(Exception e){}
			//System.out.println("DefinedEntities===>"+mmlEnt+" ");
		}
		else
		{			
			if(checkServer.length()>0)
			{
				System.out.println("\n\n********************************************************************************\n\n");
				System.out.println("\t\t[ "+mmlEnt+"; ] is not defined in the ENTITY.DBF file");			
				System.out.println("\n\n\t\t\t\t Please Contact TeX R&D Team \n\n");
				System.out.println("\n\n********************************************************************************\n\n");
				xt_log.info("\t\t[ "+mmlEnt+"; ] is not defined in the ENTITY.DBF file");				
				System.exit(0);
			}
			else
			{
				System.out.println("\n\n********************************************************************************\n\n");
				System.out.println("\t\t[ "+mmlEnt+"; ] is not defined in the ENTITY.DBF file");			
				System.out.println("\n\n\t\t\t\t Please Contact TeX R&D Team \n\n");
				System.out.println("\n\n********************************************************************************\n\n");
				System.exit(0);
			}
			
			/*
			if(undefinedEntities.equalsIgnoreCase(""))
			{
				undefinedEntities=mmlEnt;
			}
			else
			{
				undefinedEntities+=", "+mmlEnt;
			}
			filterStr= "UNDEFINEDENTITY";//((String)entList.get(new String(mmlEnt)));//.trim();
			System.out.println("undefinedEntities===>"+mmlEnt+" ");
			*/
		}
		
		/*
		System.out.println("undefinedEntities===>"+undefinedEntities);
		if(!undefinedEntities.equalsIgnoreCase(""))
		{
			System.out.println("\n\n********************************************************************************\n\n");
			System.out.println("\t\t[ "+undefinedEntities+" ] is not defined in the V:\\DATABASE\\XMLENTCONV.DBF file");
			System.out.println("\n\n\t\t\t\t Please Contact TeX R&D Team \n\n");
			System.exit(0);
		}*/

		if((rmboxFlag==true && supFlag==true)||(rmboxFlag==true && infFlag==true))//rajeev-9-oct-removing dollar within dollar
		{
			if(!filterStr.startsWith("\\("))
			{
				filterStr="\\("+filterStr+"\\)";
			}
						
		}
		
		/*
		//////////////////////////////////////////////////////////////////////////////////////////
		if(!undefinedEntities.equalsIgnoreCase(""))
		{
			System.out.println("\n\n********************************************************************************\n\n");
			System.out.println("\t\t[ "+undefinedEntities+" ] is not defined in the V:\\DATABASE\\XMLENTCONV.DBF file");
			System.out.println("\n\n\t\t\t\t Please Contact TeX R&D Team \n\n");
			System.exit(0);
		}
		//////////////////////////////////////////////////////////////////////////////////////////
		*/
		
		
		//System.out.println("dddddddddddddddd filterStr : "+filterStr);
		return filterStr;
	}

	public String replaceStr(String source, String toStr, String replStr)
	{
		CharSequence inputStr = source;
		String patternStr     = toStr;
		String replacementStr = replStr;
		Pattern pattern = Pattern.compile(patternStr);
		Matcher matcher = pattern.matcher(inputStr);
		String output = matcher.replaceAll(replacementStr);
		return output;
	}


	public String processEnun(String etag)throws IOException
	{
		String enunID= getAttributeValue(etag, "ID");
		String tag           = "";
		String enunLbl       = "";
		StringBuffer enunString    = new StringBuffer();
		boolean firstPara= true;
		String enunTitl="";

		while (!tag.equals("</CE:ENUNCIATION>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>"))
				{
					enunLbl=extractData("</CE:LABEL>", true);
				}
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					if(firstPara== false)
						enunString.append("\r\n\r\n");
					firstPara= false;
					enunString.append(extractData("</CE:PARA>", true));
				}
				else if(tag.startsWith("<CE:SECTION-TITLE"))
				{
					enunTitl= extractData("</CE:SECTION-TITLE>", true);
				}
			}
		}
		String enunType= "";
		for(int i=0; i<enunLbl.length(); i++)
		{
			char ch= enunLbl.charAt(i);
			if(ch==' ')
				break;
			enunType+=ch;
		}
		enunType= enunType.toLowerCase();
		String hyperlinkid="";
		//avinandan added on 14-9-04
		if(enunID.length()>0)
			hyperlinkid= getHypertarget(enunID);
		//end mark
		//avinandan commented
		//hyperlinkid= getHypertarget(enunID);
		//end mark

//********************
// thomsondiff\_thomsondiffopenbrace78thomsondiffclosebracet
//System.out.println("0 enunType : "+enunType);
if(enunType.indexOf("thomsondiff",0)!=-1)
		{
			enunType=enunType.replaceAll("thomsondiff\\\\_thomsondiffopenbrace([0-9]+)thomsondiffclosebrace","");
			//System.out.println("1 enunType : "+enunType);
		}
//*********************

		if(enunType.equalsIgnoreCase("equation"))
		{
			enunType=enunType+"enun";
		}
		enunType=enunType.replaceAll("h([0-9]+)", "hypothesis");
		
		String temp="\r\n\\begin{"+enunType+"}{";
		//avinandan added on 14-9-04
		if(hyperlinkid.length()>0)
			temp+= hyperlinkid+enunLbl;
		else
			temp+= enunLbl;
		//end mark
		//avinandan commented
		/*if(hyperlinkid.length()>0)
			temp+= enunLbl;*/
		//end mark
		temp+="}{"+enunTitl+"}"+enunString+"\r\n\\end{"+enunType+"}";
		//System.out.println("2 enunType : "+enunType);
		//System.in.read();
		return temp;
	}

	public String biography(String etag)throws IOException
	{
		String biogID       = getAttributeValue(etag, "ID");
		String tag          = "";
		String linkId       = "";
		StringBuffer bioStr = new StringBuffer("\r\n\\begin{vt}");
		boolean firstPara   = true;
		String biogView="";
		biogView = getAttributeValue(etag, "VIEW");

		while (!tag.equals("</CE:BIOGRAPHY>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.startsWith("<CE:LINK"))
				{
					linkId= getAttributeValue(tag, "LOCATOR");
					//Avinandan Added
					if(linkId.length() > 0)
						bioStr.append("\r\n[{%\r\n\\epsfbox{"+linkId.toLowerCase()+".eps}}%\r\n]%\r\n");
					//bioStr.append("%");
				}
				else if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
				{
					bioStr.append(extractData("</CE:SIMPLE-PARA>", true));
					//if(firstPara== false)//Comment by bhavesh
						bioStr.append("\r\n\r\n");
						
					firstPara= false;
				}
				else if(tag.equals("</CE:BIOGRAPHY>"))
					break;
			}
		}
		/*Avinandan Commented
		if(linkId.length() > 0)
			bioStr.append("["+linkId+".eps]");
		bioStr.append("%");*/
		bioStr.append("\r\n\\end{vt}\r\n");
		if(biogView.equals("EXTENDED"))
			return "\r\n\\begin{extra}"+bioStr.toString()+"\r\n\\end{extra}";
		else
			return bioStr.toString();
	}

	public String processDefList()throws IOException
	{
		String tag = "";
		StringBuffer processedData= new StringBuffer();
		boolean descFound= false;
		processedData.append("\r\n\\begin{deflist}[]");
		while (!tag.equals("</CE:DEF-LIST>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.startsWith("<CE:DEF-TERM"))
				{
					descFound= false;
					processedData.append("\r\n\\defitem{"+extractData("</CE:DEF-TERM>", true)+"}");
				}
				else if (tag.equals("<CE:DEF-DESCRIPTION>"))
				{
					descFound= true;
					processedData.append("\\defterm{"+extractData("</CE:DEF-DESCRIPTION>", true)+"}");
				}
				else if (tag.startsWith("<CE:DEF-DESCRIPTION "))//28-08-2012 JADTD520 Updation
				{
					descFound= true;
					processedData.append("\\defterm{"+extractData("</CE:DEF-DESCRIPTION>", true)+"}");
				}
				else if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					processedData.append("\\deftitle{"+extractData("</CE:SECTION-TITLE>", true)+"}");
				}
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					if (descFound== true)
					{
						processedData.append(extractData("</CE:PARA>", true));
					}
					else
					{
						processedData.append("{}");
					}
				}
			}
		}
		processedData.append("\r\n\\end{deflist}");
		return processedData.toString();
	}



	public String processQuote()throws IOException
	{
		String tag = "";
		StringBuffer processedData= new StringBuffer();
		boolean descFound= false;
		boolean fstPara=true;
		processedData.append("\r\n\\begin{quote}");
		while (!tag.equals("</CE:DISPLAYED-QUOTE>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
				{
					if(fstPara)
						processedData.append(extractData("</CE:SIMPLE-PARA>", true));
					else
						processedData.append("\r\n\n"+extractData("</CE:SIMPLE-PARA>", true));
					fstPara=false;
				}
				else if(tag.startsWith("<CE:SOURCE"))//28-08-2012 JADTD520 Updation
				{
					processedData.append("\r\n\n\\quotesource{"+extractData("</CE:SOURCE>", true)+"}\r\n");
				}
			}
		}
		processedData.append("\r\n\\end{quote}");
		return processedData.toString();
	}
	public String processSimpleFootnote(String eTag)throws IOException
	{
		String tag = "";
		StringBuffer processedData= new StringBuffer();
		boolean descFound= false;
		String fnLbl="";
		String fnTxt="";
		//processedData.append("\\footnote["+getHypertarget(getAttributeValue(eTag,
		//"ID").toLowerCase())+"]{");
		//Avinandan added till end mark
		if(fntStyle.equals("Y"))
		{
			
			processedData.append("\\item["+getHypertarget(getAttributeValue(eTag, "ID").toUpperCase())+"{");
		}
		else
		{
			if(XT.checkDuckling)
			{
				processedData.append("\\footnote["+getHypertarget(getAttributeValue(eTag, "ID").toUpperCase())+"{");
				
			}
			else
			{
				processedData.append("\\begin{Ducknote}{b}\\ddnote{"+getHypertarget(getAttributeValue(eTag, "ID").toUpperCase())+"{");
			}
		}
		//end mark and commented below lines
		//processedData.append("\\footnote["+getHypertarget(getAttributeValue(eTag, "ID").toUpperCase())+"{");
		while (!tag.equals("</CE:FOOTNOTE>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>"))
				{
					fnLbl= extractData("</CE:LABEL>", true);
				}
				else if (tag.startsWith("<CE:NOTE-PARA"))
				{
					if(fnTxt.length()>0)
					{
						fnTxt+= "\r\n\r\n"+extractData("</CE:NOTE-PARA>", true);
					}
					else
					{
						fnTxt= extractData("</CE:NOTE-PARA>", true);
					}
				}
			}
		}
		if(XT.checkDuckling)
			processedData.append(fnLbl+"}]{"+fnTxt+"}");
		else
			processedData.append(fnLbl+"}}{"+fnTxt+"}\\end{Ducknote}");
		//System.out.println(processedData.toString());
		return processedData.toString();
	}

	public String processTextBox(String etag)throws IOException
	{
		textbox_body=false;
		String tag= etag;
		//System.out.println("tag : "+tag);		
		String linkID= getHypertarget(getAttributeValue(tag, "ID"));
		String rol = getAttributeValue(tag, "ROLE");
		//System.out.println("rol : "+rol);
		//System.out.println("linkID : "+linkID);
		//System.in.read();
		//HeadGroup hg=new HeadGroup(this);		
		StringBuffer processedData= new StringBuffer();
		boolean firstPara= true;
		
		HeadGroup xmlHead= new HeadGroup(this);
		xmlHead.getModelStyle();
		//String forAddAuthor=xmlHead.getHeadContents();
		//System.out.println("forAddAuthor : "+xmlHead.modelStyle);
		if(xmlHead.modelStyle.toLowerCase().endsWith("plus"))
		{
			if(linkID.length()>0 && isDisplayTextBox==false)
			{
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}");
			}
			else
			{
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}[h]");
			}
		}
		else if((xmlHead.modelStyle.toLowerCase().endsWith("-moddfrench"))||(xmlHead.modelStyle.toLowerCase().endsWith("-modefrench")))
		{
			if(linkID.length()>0 && isDisplayTextBox==false)
			{
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}");
			}
			else
			{
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}[h]");
			}
		}
		else
		{
			//System.out.println("111111"+rol);
			if(linkID.length()>0 && isDisplayTextBox==false)
			{
				//System.out.println("22222"+rol);
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}");
			}
			else
			{   //System.out.println("33333333"+rol);
				if(rol.equalsIgnoreCase("PULL-QUOTE"))
				{processedData.append("\\begin{pullquote}");}
				else if(rol.equalsIgnoreCase("CME"))//21-10-2011
				{processedData.append("\\begin{cmetextbox}[h]");}
				else
				processedData.append("\\begin{textbox}[h]{");
			}
		}
				
		String tbLbl= "";
		StringBuffer tbTxt= new StringBuffer();
		String forAuthAffiVal="";
		String tbCap= "";
		String AlternametTbCap= "";
		String TextBoxSourceTag="";
		String TextBoxLegendTag="";
		String TextBoxKeywords="";//28-08-2012 JADTD520 Updation		
		boolean IsTextBoxKeywords=false;//28-08-2012 JADTD520 Updation
		//HeadGroup hg=new HeadGroup();
		//String forAddAuthor=hg.processAuthorGroup();
		//System.out.println(" :::forAddAuthor ::>> "+forAddAuthor);
		//boolean forCheckAuthor=HeadGroup.authorValAdd;
		//System.out.println("::forCheckAuthor "+forCheckAuthor);
		//System.out.println(" :::forAddAuthor ::>> "+forAddAuthor);
		//XT tempXt = new XT(this.fin);
		XT modXt=new XT();
		boolean modelDText=modXt.chekJidAid(jid, aid);	
		
		while (!tag.equals("</CE:TEXTBOX>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>"))
					tbLbl= extractData("</CE:LABEL>", true);
				//AlternametTbCap
				else if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))
				{
					//tbCap= extractData("</CE:CAPTION>", true);
					//*****************************************************
			//System.out.println("tag : "+tag);
						String tempTag=tag;
						//String CapLang="";
						int countTablePara=0;
						while (!tag.equals("</CE:CAPTION>"))
						{
							if(tag.startsWith("<CE:CAPTION "))
							{
										CapLang=new String(getAttributeValue(tag, "XML:LANG"));
							}

							char ch1= (char)fin.read();
							if (ch1=='<')
							{
								tag= getTag().toUpperCase();
								if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))
								{
									++countTablePara;
									if(countTablePara==1)
									{
										if(!tempTag.equals("<CE:CAPTION>")&&(tempTag.startsWith("<CE:CAPTION ")&&!CapLang.equals("")))
										{
											if(CapLang.length()>0)//28-08-2012 JADTD520 Updation
												AlternametTbCap+= "\r\n\\BoxSubcaption{\\"+CapLang+"{"+ extractData("</CE:SIMPLE-PARA>", true)+"}}";
											else//28-08-2012 JADTD520 Updation
												tbCap+= "\r\n"+extractData("</CE:SIMPLE-PARA>", true);
												//System.out.println("tbCap===>>"+tbCap);

										}
										else
										tbCap+= "\r\n"+extractData("</CE:SIMPLE-PARA>", true);
										//System.out.println("1 tbCap : "+tbCap);
									}
									else if(countTablePara==2)
									{
										if(!tempTag.equals("<CE:CAPTION>")&&(tempTag.startsWith("<CE:CAPTION ")&&!CapLang.equals("")))
										{
											AlternametTbCap+= "\r\n\\BoxSubcaption{\\"+CapLang+"{"+ extractData("</CE:SIMPLE-PARA>", true)+"}}";
										}
										else
										tbCap+="\r\n\\AltCaption{"+ extractData("</CE:SIMPLE-PARA>", true)+"}";
										//System.out.println("2. tbCap : "+tbCap);
									}
									else if(countTablePara > 2)
									{
										if(!tempTag.equals("<CE:CAPTION>")&&tempTag.startsWith("<CE:CAPTION "))
										{
											AlternametTbCap+= "\r\n\\BoxSubcaption{\\"+CapLang+"{"+ extractData("</CE:SIMPLE-PARA>", true)+"}}";
										}
										else
										tbCap+= "\r\n"+extractData("</CE:SIMPLE-PARA>", true);
										//System.out.println("3 tbCap : "+tbCap);
									}
									
									//System.in.read();
								}
							}
						}
						//System.out.println("AlternametTbCap : "+AlternametTbCap);
					//******************************************************
				
				}
				else if(tag.startsWith("<CE:LEGEND>"))
				{
					TextBoxLegendTag=extractData("</CE:LEGEND>", true);
					
				}
				else if(tag.startsWith("<CE:SOURCE>"))
				{
					TextBoxSourceTag=extractData("</CE:SOURCE>", true);
					
				}
				else if(tag.startsWith("<CE:KEYWORDS "))//28-08-2012 JADTD520 Updation
				{
					
					TextBoxKeywords=xmlHead.processKeywords(tag);
					IsTextBoxKeywords=true;
					//TextBoxKeywords="";
					//System.out.println("TextBoxKeywords : "+TextBoxKeywords);
					
				}

				
				//added by Avinandan on 11-8-04
			/*	else if (tag.startsWith("<CE:COPYRIGHT"))
				{
					int ind= tag.indexOf("TYPE=\"")+6;
					String copyrType = getAttributeValue(tag, "TYPE");
					ind= tag.indexOf("YEAR=\"")+6;
					String copyrYear = getAttributeValue(tag, "YEAR");

					if((copyrType.equals("OTHER"))&&(tag.endsWith("/>")))
					{
						tbTxt.append("\\copyrightline{}");
					}
					else
					{
						tbTxt.append(tempXt.getCopyright(copyrType, copyrYear, tag));
					}
				}*/
				else if (tag.equals("<CE:TEXTBOX-HEAD>"))
				{
					//HeadGroup xmlHead= new
					//HeadGroup(new XMLObjects(this.fin, this.figInfo, this.bkmList));
					//HeadGroup xmlHead= new HeadGroup(this);
					if(modelDText)
					{
						forAuthAffiVal=xmlHead.getHeadContents().toString();
						//forAuthAffiVal=tbTxt.append(xmlHead.getHeadContents()).toString();
						
					}
					else
					{
						tbTxt.append(xmlHead.getHeadContents());
						
					}
					//System.out.println(".. Ok");
					specialLink=false;
				}
				else if (tag.equals("<CE:TEXTBOX-BODY>"))//[11/12/2006]
				{
					//System.out.print("Processing TextBox Body      ");
					textbox_body=true;
					BodyGroup xmlBody= new BodyGroup(this);
					//System.out.println("------->"+xmlBody.getBodyContents());
					tbTxt.append("\r\n"+xmlBody.getBodyContents());		
					
				}
				else if (tag.equals("<CE:TEXTBOX-TAIL>"))
				{
					//System.out.print("Processing TextBox Tail      ");
					TailGroup xmlTail= new TailGroup(this);
					tbTxt.append(xmlTail.getTailContents());
					//System.out.println(".. Ok");
				}
				//end mark
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					if(firstPara== false)
						tbTxt.append("\r\n\r\n");
					tbTxt.append(extractData("</CE:PARA>", true));
					firstPara= false;
					
				}
			}
		}
		
		
		//tex= tex+"\\bfseries "+no+":
		//"+caption+"}"+link+"%\r\n\r\n"+text+"\r\n\\end{textbox}";

//		HeadGroup xmlHead= new HeadGroup(this);
//System.out.println("tbLbl : "+tbLbl+"tbCap : "+tbCap+"xmlHead.modelStyle : "+xmlHead.modelStyle+" AlternametTbCap : "+AlternametTbCap);
		if(xmlHead.modelStyle.toLowerCase().endsWith("plus")||jid.equalsIgnoreCase("TRSTMH")||jid.equalsIgnoreCase("INHE"))
		{
			//AlternametTbCap
			/*if((tbLbl.length()>0) && (tbCap.length()>0))
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}");
			else if(tbLbl.length()>0)
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}");
			else if(tbCap.length()>0)
				processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}");
				
				*/
				
				if((tbLbl.length()>0) && (tbCap.length()>0)&& (AlternametTbCap.length()>0))
					processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}"+AlternametTbCap);
				else if(tbLbl.length()>0 && (AlternametTbCap.length()>0))
					processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}"+AlternametTbCap);
				else if(tbCap.length()>0 && (AlternametTbCap.length()>0))
					processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}"+AlternametTbCap);
				else if((tbLbl.length()>0) && (tbCap.length()>0))
					processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}");
				else if(tbLbl.length()>0)
					processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}");
				else if(tbCap.length()>0)
					processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}");


			//else
				//processedData.append("}");

		}
		else if((xmlHead.modelStyle.toLowerCase().endsWith("-moddfrench"))||(xmlHead.modelStyle.toLowerCase().endsWith("-modefrench")))
		{
			
			/*if((tbLbl.length()>0) && (tbCap.length()>0))
			{	//processedData.append(tbCap + "}");
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}");
			}
			else if(tbLbl.length()>0)
			{
				//System.out.println("tbLbl : "+tbLbl);
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}");
			}
			else if(tbCap.length()>0)
				processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}");
			*/
			if((tbLbl.length()>0) && (tbCap.length()>0)&& (AlternametTbCap.length()>0))
			{	//processedData.append(tbCap + "}");
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}"+AlternametTbCap);
				
			}
			else if(tbLbl.length()>0 && (AlternametTbCap.length()>0))
			{
				//System.out.println("tbLbl : "+tbLbl);
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}"+AlternametTbCap);
			}
			else if(tbCap.length()>0 && (AlternametTbCap.length()>0))
				processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}"+AlternametTbCap);
			
			
			else if((tbLbl.length()>0) && (tbCap.length()>0))
			{	//processedData.append(tbCap + "}");
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}"+tbCap+"}");
			}
			else if(tbLbl.length()>0)
			{
				//System.out.println("tbLbl : "+tbLbl);
				processedData.append("\r\n\\caption{\\figno{"+tbLbl + "}}");
			}
			else if(tbCap.length()>0)
				processedData.append("\r\n\\caption{\\figno{}" + tbCap + "}");
		}
		else
		{
			/*if(tbLbl.length()>0)
				processedData.append("\\bfseries \\noindent "+tbLbl);//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
			if((tbLbl.length()>0) && (tbCap.length()>0))
				processedData.append(": "+tbCap+"}");
			else if(tbCap.length()>0)
				processedData.append("\\bfseries \\noindent "+tbCap+"}");//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
				*/
				
				if((tbLbl.length()>0) && (tbCap.length()>0) && (AlternametTbCap.length()>0))
				{
					if(jid.equalsIgnoreCase("JPHYS"))
						processedData.append("[]{{\\bfseries \\noindent "+tbLbl+".} "+tbCap+"}"+AlternametTbCap);
					/*else if(CapLang.equalsIgnoreCase("fr"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}"+AlternametTbCap);*/
					else if(XT.articleType.equalsIgnoreCase("fr"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}"+AlternametTbCap);
						else
						processedData.append("{\\bfseries \\noindent "+tbLbl+": "+tbCap+"}"+AlternametTbCap);
					/*else if(jid.equalsIgnoreCase("SOCTRA"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}"+AlternametTbCap);
					else
						processedData.append("{\\bfseries \\noindent "+tbLbl+": "+tbCap+"}"+AlternametTbCap);*/
				}
				else if(tbLbl.length()>0 && (AlternametTbCap.length()>0))
				{
					if(jid.equalsIgnoreCase("JPHYS"))
						processedData.append("[]{{\\bfseries \\noindent "+tbLbl+"}"+AlternametTbCap);//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
					else
						processedData.append("{\\bfseries \\noindent "+tbLbl+AlternametTbCap);//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
				}
				else if(tbCap.length()>0 && (AlternametTbCap.length()>0))
				processedData.append("{\\bfseries \\noindent "+tbCap+"}"+AlternametTbCap);//modify by bhavesh on 21/08/06 add
				else if((tbLbl.length()>0) && (tbCap.length()>0))
				{
					if(jid.equalsIgnoreCase("JPHYS"))
						processedData.append("[]{{\\bfseries \\noindent "+tbLbl+".} "+tbCap+"}");
					/*else if(CapLang.equalsIgnoreCase("fr"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}");*/
					else if(XT.articleType.equalsIgnoreCase("fr"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}");
						else
						processedData.append("{\\bfseries \\noindent "+tbLbl+": "+tbCap+"}");
					/*else if(jid.equalsIgnoreCase("SOCTRA"))
						processedData.append("{\\bfseries \\noindent "+tbLbl+" : "+tbCap+"}");
					else
						processedData.append("{\\bfseries \\noindent "+tbLbl+": "+tbCap+"}");*/
				}
				else if(tbLbl.length()>0)
				{
					if(jid.equalsIgnoreCase("JPHYS"))
						processedData.append("[]{\\bfseries \\noindent "+tbLbl+"}");//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
					else
						processedData.append("{\\bfseries \\noindent "+tbLbl+"}");//modify by bhavesh on 21/08/06 add \noindent in nonplus text box
				}
				else if(tbCap.length()>0)
					processedData.append("{\\bfseries \\noindent "+tbCap+"}");//modify by bhavesh on 21/08/06 add
				else
				{
					if(rol.equalsIgnoreCase("PULL-QUOTE")){}//modify by Ravi on 06/03/09 
					else
					{
						processedData.append("}");
					}
				}
				//AlternametTbCap="";
				//tbLbl="";
				//tbCap="";
				
				
				
				
		}
		/* //Blocked by Vivek on 29-01-2013 as no keywords required in textbox output
		if(IsTextBoxKeywords)
			tbTxt.append("\r\n\\keywords{%\r\n"+TextBoxKeywords+"}");
		*/		
		if(rol.equalsIgnoreCase("PULL-QUOTE"))
		{processedData.append(linkID+"%\r\n"+tbTxt+"\r\n\\end{pullquote}");}
		else if(rol.equalsIgnoreCase("CME"))//21-10-2011
		{processedData.append(linkID+"%\r\n"+tbTxt+"\r\n\\end{cmetextbox}");}
		else
		{
			if(TextBoxLegendTag.length()>0)
			{				
					System.out.println("::TextBoxLegendTag::>> "+TextBoxLegendTag);	
					processedData.append(linkID+"%\r\n"+tbTxt+"\r\n\\BoxLegend{"+TextBoxLegendTag+"}\r\n\\end{textbox}");
						
			}
			else if(TextBoxSourceTag.length()>0)
			{				
					System.out.println("::TextBoxSourceTag::>> "+TextBoxSourceTag);	
					processedData.append(linkID+"%\r\n"+tbTxt+"\r\n\\Boxsource{"+TextBoxSourceTag+"}\r\n\\end{textbox}");
						
			}
			else
				if(modelDText)
				{					
					processedData.append(linkID+"%\r\n"+tbTxt+"\r\n");
					if(!forAuthAffiVal.equalsIgnoreCase(""))
					{
						//processedData.append(linkID+"%\r\n"+tbTxt+"");						
						processedData.append("\r\n\\begin{boxmiscflushright}");
						processedData.append("\r\n"+forAuthAffiVal+"");
						processedData.append("\r\n\\end{boxmiscflushright}");
						//forAuthAffiVal="";
					}
					if(!footTagval.equalsIgnoreCase(""))
					{
						footTagval=footTagval.replaceAll("footnote", "textboxfootnote");
						processedData.append("\r\n"+footTagval+"");
						processedData.append("\r\n\\end{textbox}");
						//footTagval="";						
					}
					else
					{
						processedData.append("\\end{textbox}");				
					}
				}
				else
				{
					processedData.append(linkID+"%\r\n"+tbTxt+"\r\n\\end{textbox}");
				}	
				
		}
		//tbTxt=null;
		//TextBoxSourceTag="";
		//System.out.println("section_title_aenj===> "+BodyGroup.section_title_aenj);
		
		BodyGroup.section_title_aenj=BodyGroup.section_title_aenj.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
		StringBuffer sg=new StringBuffer(BodyGroup.section_title_aenj);
		 int po=0;
		 while((po=sg.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po+1))!=-1)
		 {
		 	int pt=sg.indexOf("\\}",po+1);
		 	sg=sg.replace(po,pt+"\\}".length(),"");
		 }
		 BodyGroup.section_title_aenj=sg.toString();
		 forAuthAffiVal=new String();
		 footTagval=new String();
		//System.out.println("section_title_aenj===> "+BodyGroup.section_title_aenj);
		/*
		* Condition commented as per R&D mail dated on 29-10-2013 and JIRA ID EJ-551
		if(textbox_body==true && jid.equalsIgnoreCase("AENJ")&& HeadGroup.artDochead.equalsIgnoreCase("Research paper")&&(BodyGroup.section_title_aenj.toLowerCase().startsWith("what is known")||BodyGroup.section_title_aenj.toLowerCase().startsWith("what is already known")||BodyGroup.section_title_aenj.toLowerCase().startsWith("what this paper adds")))//24-09-2010
		{
			textbox_body=false;
			String temp=processedData.toString();
			temp=temp.replaceAll("\\\\begin\\{textbox\\}","\\\\begin{plainbox}");
			temp=temp.replaceAll("\\\\end\\{textbox\\}","\\\\end{plainbox}");
			
			processedData=new StringBuffer(temp);

		}
		*/
		//System.in.read();
		return processedData.toString();
	}
	public String replaceGlyph(String tag) 
	{
		/*
		This function is used to replace GLYPH's coding with equv. tex coding.
		@created : Abhishek on 7th Feb 03
		*/
		String glyphcode= getAttributeValue(tag, "NAME");
		String glyphTex="";
		if(glyphcode.equals("SBND"))
			glyphTex="{\\zsbnd}";
		else if (glyphcode.equals("RAD"))
			glyphTex="\\(^{\\bullet}\\)";
		else if (glyphcode.equals("DBND"))
			glyphTex="{$\\zdbnd$}";
		else if (glyphcode.equals("CAMB"))
		{
			//glyphTex="\\epsfbox{t:/TeX-Data-Bank/grideps/bcu.eps}";//\\epsfbox{t:/TeX-Data-Bank/grideps/bcu.eps}
			glyphTex="\\camb";//\\epsfbox{t:/TeX-Data-Bank/grideps/bcu.eps}
		}
		else if (glyphcode.equals("LOZFL"))
			glyphTex="{$\\lozenge$}";
		else if (glyphcode.equals("LOZFR"))
			glyphTex="{\\Bhg}";
		else if (glyphcode.equals("TBND"))
			glyphTex="{$\\ztbnd$}";
		//Avinandan added till endmark
		else if (glyphcode.equals("LBOND2"))//gaurav
		{
			//glyphTex="{\\epsfbox{langlebond.eps}}";//blocked on 23-02-2012
			glyphTex="{\\inserttiff{0.059in}{0.102in}{langlebond.tif}}";
		}
		//2222222222
		else if (glyphcode.equals("RBOND2"))
			glyphTex="{%\r\n\\inserttiff{0.058in}{0.102in}{ranglebond.tif}}\r\n";
			//glyphTex="{\\epsfbox{ranglebond.eps}}";
		else if (glyphcode.equals("LOZF"))
					glyphTex="{\\FilledDiamond}";
		else if (glyphcode.equals("S"))
					glyphTex="{\\Bij}";
		else if (glyphcode.equals("SQFNE"))
					glyphTex="{\\Bpf}";
		else if (glyphcode.equals("DBND6"))
					glyphTex="{$=$}";
		else if (glyphcode.equals("RBOND3"))
					glyphTex="{\\Btp}";
		else if (glyphcode.equals("LBOND3"))
					glyphTex="{\\Bup}";
		else if (glyphcode.equals("SQFT"))
					glyphTex="{\\Bvf}";
		else if (glyphcode.equals("SQFSW"))
					glyphTex="{\\sqfsw}";
		else if (glyphcode.equals("TBND6"))
					glyphTex="{\\tbnd}";
		else if (glyphcode.equals("SQFB"))
					glyphTex="{\\Bwf}";
		else if (glyphcode.equals("QBND"))
					glyphTex="{\\qbnd}";
		else if (glyphcode.equals("QBND6"))
					glyphTex="{\\qbndsx}";
		else if (glyphcode.equals("PENT"))
					glyphTex="{\\pent}";
		else if (glyphcode.equals("NSMID"))
					glyphTex="{\\epsfbox{I:/grideps/7e.eps}}";
		else if (glyphcode.equals("HENG"))
					glyphTex="{\\GridPhi}";
		else if (glyphcode.equals("LBD2TD"))
					glyphTex="{\\Bthreen}";
		else if (glyphcode.equals("PDBDTD"))
					glyphTex="{$\\Bothree$}";
		else if (glyphcode.equals("LBD2BD"))
					glyphTex="{\\Bfourn}";
		else if (glyphcode.equals("DLCORN"))
					glyphTex="{\\inserttiff{0.056in}{0.083in}{I:/grideps/5d.tif}}";
		else if (glyphcode.equals("DRCORN"))
					glyphTex="{\\epsfbox{I:/grideps/5e.eps}}";
		else if (glyphcode.equals("RBD2TD"))
					glyphTex="{$\\Bfiven$}";
		else if (glyphcode.equals("PTBDTD"))
					glyphTex="{$\\Bofive$}";
		else if (glyphcode.equals("SMID"))
					glyphTex="{$\\smallvert$}";
		else if (glyphcode.equals("SMID"))
					glyphTex="{\\epsfbox{I:/grideps/6e.eps}}";
		else if (glyphcode.equals("RBD2BD"))
					glyphTex="{\\Bsixn}";
		else if (glyphcode.equals("PTBDBD"))
					glyphTex="{$\\Bosix$}";
		else if (glyphcode.equals("SPAR"))
					glyphTex="{$\\smallVert$}";
		else if (glyphcode.equals("NSPAR"))
					glyphTex="{\\epsfbox{I:/grideps/7e.eps}}";
		else if (glyphcode.equals("HERMA"))
					glyphTex="{\\BHeight}";
		else if (glyphcode.equals("PDBOND"))
					glyphTex="{$\\Boeght$}";
		else if (glyphcode.equals("RESMCK"))
					glyphTex="{$\\KC$}";
		else if (glyphcode.equals("RISFLA"))
					glyphTex="{\\Psixe}";
		else if (glyphcode.equals("JNODOT"))
					glyphTex="{$\\jnodot$}";
		else if (glyphcode.equals("NCURT"))
					glyphTex="{$\\NGg$}";
		else if (glyphcode.equals("TCURT"))
					glyphTex="{$\\TG$}";
		else if (glyphcode.equals("PSLASH"))
					glyphTex="{\\slash\\slash}";
		else if (glyphcode.equals("TRISLA"))
					glyphTex="{\\slash\\slash\\slash}";
		else if (glyphcode.equals("REFHRL"))
					glyphTex="{$\\RK$}";
		else if (glyphcode.equals("BTMLIG"))
					glyphTex="{$\\xs$}";
		else if (glyphcode.equals("HBAR"))
					glyphTex="{$\\wc$}";
		else if (glyphcode.equals("GGRAVE"))
					glyphTex="{$\\ta$}";
		else if (glyphcode.equals("HRIS"))
					glyphTex="{\\smash{\\raise-.935pt\\hbox{$\\xxx$}}\\hskip-2pt\\'{}}";		
		else if (glyphcode.equals("LRIS"))
					glyphTex="{\\`{}\\hskip-2pt\\smash{\\raise-.935pt\\hbox{$\\xxx$}}}";		
		else if (glyphcode.equals("HT"))
					glyphTex="{\\Peighta}";		
		else if (glyphcode.equals("CTL"))
					glyphTex="{\\Peightb}";		
		else if (glyphcode.equals("SBW"))
					glyphTex="{$\\wb$}";
		else if (glyphcode.equals("TRNOMEG"))
					glyphTex="{$\\OJ$}";
		//Added By Ravi [29/12/2006]
		else if (glyphcode.equals("BIGDOT"))
					glyphTex="{$\\zbigdot$}";
		//end
//1111111111
		else if (glyphcode.equals("RBOND2"))
			glyphTex="{%\r\n\\inserttiff{0.058in}{0.102in}{ranglebond.tif}}\r\n";
		//glyphTex="{\\epsfbox{ranglebond.eps}}";
		if (glyphTex.length()==0)
		{
			System.out.println("Glyph code for "+glyphcode+" not defind");
			System.exit(0);
		}
		return glyphTex;
	}

	public String processFigure(String tage)throws java.io.IOException
	{
		//System.out.println("tage===>> "+tage);
		String figId   = "";
		String label   = "";
		String caption = "";
		String figLoc  = "";
		StringBuffer figInformation= new StringBuffer();
		String tag= tage;
		String figCopy="";
		String figKeyword="";//28-08-2012 JADTD520 Updation
		String IsfigKeyword="";//28-08-2012 JADTD520 Updation

		String temp_ravi="";
		while (!tag.equals("</CE:FIGURE>"))
		{
			
			char ch= (char)fin.read();
			//System.out.println("ch--> "+ch);
			//System.in.read();
			temp_ravi=ch+"";
			int i=0;
			if(temp_ravi.startsWith("T"))
			{
				while(!temp_ravi.endsWith("DiffCloseBrace<"))
				{
					ch=(char)fin.read();
					temp_ravi+=ch;
					++i;
				}
				if(temp_ravi.length()>0)
				{
					temp_ravi=temp_ravi.substring(0,temp_ravi.length()-1);
				}
				
			}
			if(temp_ravi.length()>0 &&temp_ravi.equalsIgnoreCase("<"))//04-02-2011
			{
				temp_ravi="";
			}
			//System.out.println("temp_ravi ravi"+temp_ravi);
			if (ch=='<')
			{
				tag= getTag().toUpperCase();
				
				//if (tag.startsWith("<CE:LINK LOCATOR"))//08-09-2015 update due to Locator position changed in dtd540
				if (tag.startsWith("<CE:LINK "))
				{
					//figLoc= tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"/>")).toUpperCase();
					figLoc= getAttributeValue(tag, "LOCATOR");
					//System.out.println("figLoc "+figLoc);
				}
				else if (tag.equals("<CE:LABEL>")||tag.startsWith("<CE:LABEL"))
					label= extractData("</CE:LABEL>", true);
				else if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))
					if(caption.length()>0){
						if(label.length()>0){
							caption+= "}\n\\altcaption{\\figno{"+label+"}"+extractData("</CE:CAPTION>", true);
						}else{
							caption+= "}\n\\altcaption{"+extractData("</CE:CAPTION>", true);	
						}
					}else{
						caption= extractData("</CE:CAPTION>", true);
					}
			}
			
			
			//System.in.read();
		//}//old [19/05/2007]
		//By Arvind For Multiple fx1
		
		if (figInfo.containsKey(new String(figLoc)))
		{
			
			String figH, figW;
			figH= figW= "";
			String tfig= new String(new String(figInfo.get(figLoc)+""));
			figW= tfig.substring(0, tfig.indexOf("/"));
			figH= tfig.substring(tfig.indexOf("/")+1);
			//Commented by Avinandan
			//figInformation.append("\\TIFFfigurebox{"+figW+"}{"+figH+"}{}["+figLoc+".tif]");
			//Added by Avinandan

			
			if(((HeadGroup.artDochead.equalsIgnoreCase("Technique chirurgicale")|| HeadGroup.artDochead.equalsIgnoreCase("Point technique")|| HeadGroup.artDochead.equalsIgnoreCase("Geste de base")||HeadGroup.artDochead.equalsIgnoreCase("Technical point")||HeadGroup.artDochead.equalsIgnoreCase("Basic maneuver")||HeadGroup.artDochead.equalsIgnoreCase("Surgical technique"))&& pit.equalsIgnoreCase("SCO")&&( jid.equalsIgnoreCase("JCHIR")|| jid.equalsIgnoreCase("JCHIRV")|| jid.equalsIgnoreCase("JVS")))&& BodyGroup.firstSection==true){
				
				//figInformation.append("\\epsfbox{"+figLoc.toLowerCase()+".eps}}");			//20.05.2010
				if(label.length()>0)
				{
					String temp_label=label;
					temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
					temp_label=temp_label.replaceAll("\\\\","");
					temp_label=temp_label.replaceAll("\\(","");
					temp_label=temp_label.replaceAll("\\)","");
					temp_label=temp_label.replaceAll("backslash","");
					temp_label=temp_label.replaceAll("Q([0-9]+)","");
					temp_label=temp_label.replaceAll("qtoa","");
					temp_label=temp_label.replaceAll("protect","");
					temp_label=temp_label.replaceAll("\\{","");
					temp_label=temp_label.replaceAll("\\}","");
					if(temp_ravi.length()>0)
					figInformation.append("\\AltTextLB{"+temp_label+"}\r\n{%\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\nravi"+temp_ravi+"\r\n"+"}");			
					else
						figInformation.append("\\AltTextLB{"+temp_label+"}\r\n{%\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");			
				}
				else
				{

					if(temp_ravi.length()>0)
					figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");			
					else
					figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");			
				}
				
				
			}
			else if((HeadGroup.artDochead.equalsIgnoreCase("Student corner"))&& pit.equalsIgnoreCase("SCO")&&(jid.equalsIgnoreCase("GCB")||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))))//15-06-2010
					{
						if(label.length()>0)
						{
							String temp_label=label;
							temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
							temp_label=temp_label.replaceAll("\\\\","");
							temp_label=temp_label.replaceAll("\\(","");
							temp_label=temp_label.replaceAll("\\)","");
							temp_label=temp_label.replaceAll("backslash","");
							temp_label=temp_label.replaceAll("Q([0-9]+)","");
							temp_label=temp_label.replaceAll("qtoa","");
							temp_label=temp_label.replaceAll("protect","");
							temp_label=temp_label.replaceAll("\\{","");
							temp_label=temp_label.replaceAll("\\}","");
							if(temp_ravi.length()>0)
								figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
							else
								figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	
								}
							else
							{
								if(temp_ravi.length()>0)
								figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
								else
									figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	
							}
					}
				else{
					//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n");			//20.05.2010
					if(label.length()>0)
					{
						String temp_label=label;
						temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
						temp_label=temp_label.replaceAll("\\\\","");
						temp_label=temp_label.replaceAll("\\(","");
						temp_label=temp_label.replaceAll("\\)","");
						temp_label=temp_label.replaceAll("backslash","");
						temp_label=temp_label.replaceAll("Q([0-9]+)","");
						temp_label=temp_label.replaceAll("qtoa","");
						temp_label=temp_label.replaceAll("protect","");
						temp_label=temp_label.replaceAll("\\{","");
						temp_label=temp_label.replaceAll("\\}","");
						if(temp_ravi.length()>0)
						figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");			
						else
						figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");			
					}
					else
					{
						
						if(temp_ravi.length()>0)
						figInformation.append("\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");			
						else
							figInformation.append("\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");			
					}
				}
		}
		else
		{
			//Commented by Avinandan
			//figInformation.append("\\figurebox{}{}{}["+figLoc+".eps]%\r\n");
			//Added by Avinandan
			
			/**
			* Added By Ravi [26/02/2007]
			* Change Request By : Vivek
			* Change Point : Para not required without para tag in case of FX. JPC_7581
			*/
			//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n");//old
			//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}");//old//blocked by arvind [19/05/2007]
			if(tag.equals("</CE:FIGURE>"))
			{
			}
			else
			{
				if(figLoc.length()>0){
					if(((HeadGroup.artDochead.equalsIgnoreCase("Technique chirurgicale")|| HeadGroup.artDochead.equalsIgnoreCase("Point technique")|| HeadGroup.artDochead.equalsIgnoreCase("Geste de base")||HeadGroup.artDochead.equalsIgnoreCase("Technical point")||HeadGroup.artDochead.equalsIgnoreCase("Basic maneuver")||HeadGroup.artDochead.equalsIgnoreCase("Surgical technique"))&& pit.equalsIgnoreCase("SCO")&&(jid.equalsIgnoreCase("JCHIR")||jid.equalsIgnoreCase("JCHIRV")||jid.equalsIgnoreCase("JVS")))&& BodyGroup.firstSection==true)
						{
						//figInformation.append("\\epsfbox{"+figLoc.toLowerCase()+".eps}}");	
						if(label.length()>0)
						{
							String temp_label=label;
							temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
							temp_label=temp_label.replaceAll("\\\\","");
							temp_label=temp_label.replaceAll("\\(","");
							temp_label=temp_label.replaceAll("\\)","");
							temp_label=temp_label.replaceAll("backslash","");
							temp_label=temp_label.replaceAll("Q([0-9]+)","");
							temp_label=temp_label.replaceAll("qtoa","");
							temp_label=temp_label.replaceAll("protect","");
							temp_label=temp_label.replaceAll("\\{","");
							temp_label=temp_label.replaceAll("\\}","");
							if(temp_ravi.length()>0)
							figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
							else
								figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	
						}
						else
						{
							if(temp_ravi.length()>0)
							figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
							else
								figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	
						}
						//System.out.println("-----------------"+figLoc);
					}
					else if((HeadGroup.artDochead.equalsIgnoreCase("Student corner"))&& pit.equalsIgnoreCase("SCO")&&(jid.equalsIgnoreCase("GCB")||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))))//15-06-2010
					{
						if(label.length()>0)
						{
							String temp_label=label;
							temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
							temp_label=temp_label.replaceAll("\\\\","");
							temp_label=temp_label.replaceAll("\\(","");
							temp_label=temp_label.replaceAll("\\)","");
							temp_label=temp_label.replaceAll("backslash","");
							temp_label=temp_label.replaceAll("Q([0-9]+)","");
							temp_label=temp_label.replaceAll("qtoa","");
							temp_label=temp_label.replaceAll("protect","");
							temp_label=temp_label.replaceAll("\\{","");
							temp_label=temp_label.replaceAll("\\}","");
							if(temp_ravi.length()>0)
								figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
							else
								figInformation.append("\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	
								}
							else
							{
								if(temp_ravi.length()>0)
								figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");	
								else
								figInformation.append("\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");	

							}
					}
					else
					{

						//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}");//20.05.2010
						//System.out.println("label---> "+label);
						//System.out.println("temp_ravi--------> "+temp_ravi);
						if(label.length()>0 && caption.length()>0)
						{
							String temp_label=label;
							temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
							temp_label=temp_label.replaceAll("\\\\","");
							temp_label=temp_label.replaceAll("\\(","");
							temp_label=temp_label.replaceAll("\\)","");
							temp_label=temp_label.replaceAll("backslash","");
							temp_label=temp_label.replaceAll("Q([0-9]+)","");
							temp_label=temp_label.replaceAll("qtoa","");
							temp_label=temp_label.replaceAll("protect","");
							temp_label=temp_label.replaceAll("\\{","");
							temp_label=temp_label.replaceAll("\\}","");
							if(temp_ravi.length()>0)
							figInformation.append("\r\n\\begin{displayfigure}\r\n\\centerline{%\r\n\\AltTextLB{"+temp_label+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");
							else
								figInformation.append("\r\n\\begin{displayfigure}\r\n\\centerline{%\r\n\\AltTextLB{"+temp_label+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");
						}
						else if(label.length()>0)
						{
							String temp_label=label;
							temp_label=temp_label.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-1]+)ThomsonDiffCloseBrace","");
							temp_label=temp_label.replaceAll("\\\\","");
							temp_label=temp_label.replaceAll("\\(","");
							temp_label=temp_label.replaceAll("\\)","");
							temp_label=temp_label.replaceAll("backslash","");
							temp_label=temp_label.replaceAll("Q([0-9]+)","");
							temp_label=temp_label.replaceAll("qtoa","");
							temp_label=temp_label.replaceAll("protect","");
							temp_label=temp_label.replaceAll("\\{","");
							temp_label=temp_label.replaceAll("\\}","");
							if(temp_ravi.length()>0)
							figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");
							else
								figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");
						}
						else
						{
							if(caption.length()>0)
							{
								if(temp_ravi.length()>0)
								figInformation.append("\r\n\\begin{displayfigure}\r\n\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");
								else
									figInformation.append("\r\n\\begin{displayfigure}\r\n\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");
							}
							else
							{
								if(temp_ravi.length()>0)
								figInformation.append("\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n"+temp_ravi+"\r\n"+"}");
								else
									figInformation.append("\\centerline{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}");

							}
							
						}
						
						
					}
				}
				
			}
			//end
		}
		//System.out.println("figInformation=====> "+bg.firstSection);
		//System.in.read();
		//[19/05/2007]
		//By Arvind For Multiple fx1
	}
		String tt=figInformation.toString();
		//System.out.println(tt);
		tt=tt.replaceAll("ravi<",temp_ravi);
		figInformation= new StringBuffer();
		figInformation=figInformation.append(tt);
		if(label.length()>0&&caption.length()>0)
		{
			figInformation.append("\\caption{\\figno{"+label+"}"+caption+"}%\r\n\\end{displayfigure}\r\n");
		}
		else if(label.length()>0)
			figInformation.append("\\caption{\\figno{"+label+"}");
		else if(caption.length()>0)
			figInformation.append("\\caption{"+caption+"}%\r\n\\end{displayfigure}\r\n");
		else
			figInformation.append("\r\n");	
		//System.out.println("----"+caption);
		return figInformation.toString();
	}

	public String getNextTag()throws java.io.IOException
	{
		long filePointer= fin.getFilePointer();
		fin.read();
		String tag= getTag().toUpperCase();
		fin.seek(filePointer);
		return tag;
	}




}
