package tp.xt;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;
import javax.swing.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Component;


class XT
{
	File xmlFile;
	String xmlContent;
	RandomAccessFile fin;
	RandomAccessFile fin1;
	RandomAccessFile fin2;
	//***[03/05/2007]
	File CheckQueryFile;//
	RandomAccessFile fin3;
	//end
	static HyphenPT hypnPT;
	XMLObjects xmlObj;
	XMLObjects xmlObj1;
	Hashtable appProp= null;
	String pii;
	static String doi;
	static String copyrightLineForEnd;
	static String jid="";;
	String aid;
	String articleNo;
	static boolean isArticleNumberPresent = false;
	static boolean acanWorkflow = true;
	String bibStyle="";
	String piiStyle="";
	String fntStyle="";
	String ackStyle="";
	static String modelStyle="";
	static int sectionNo=0;
	static int insertToc;
	static Vector antiTocJid;
	static Hashtable plusTable = null;
	static Hashtable Guli_Table = null;//16/02/2008
	static Hashtable New_DOI_Table = null;//22/03/2012
	static Hashtable FMSModel = null;//16/02/2008
	static Vector Spanish_Reference_Jid;
	static Vector Spanish_Reference_Jid_5APA;
	static String modelFilePath="";
	static String doctopics	 = "";
	static String stage="";
	static String stageDesc="";
	static String onlineversiontype="";
	static String isDuckling="";
	static Vector subitems;
	static String orgpit = "";
	static boolean checkDuckling=true;
	static boolean check_biography=false;
	static boolean pearreviewreport=false;
	//Added By Ravi [10/01/2007]
	static String checkStage="";
	//Added By Ravi [10/01/2007]
	//for query tag deletation
	static String rename_main_xml_file="";
	static String model6rhtitle="";
	static String articleType="";
	static Vector thumbnailJid;
	static Vector stampJid;
	static Vector stampJid_Remove;
	static Vector webJid;
	static String titlequery="";
	ArrayList checkSpanishJidList=new ArrayList();
	public boolean checkModelDModelE=false;
	
	static String refFootnote = "";
	static boolean bodyflage=false;
	static boolean isTabCaption=false;
	static boolean isENFI=false;//06-12-2010
	static boolean isTabCapFirstPara=true;
	static boolean check_GulliverJid_For_NewMathSize =false;//05/02/2009
	static String isFMSPC = "No";
	static boolean isTEESXml=false;//03-09-2011
	static boolean isCMEInOrder=false;//27-01-2012
	static String databasePath="";
	static String isDatabaseAvailable="";
	static boolean isDatabaseNotRequired=false;
	static String isOrderPathAvailable="";
	static String isToBeParsed="";
	static String web="";//arvind [To make the S300-web-draft]-25-04-07
	static  Vector OneCol_Journal;//Arvind [To read the One_Column_Journal from the property file]-25-04-07
	static String onecol_Journal_Path="";//arvind [To read the One_Column_Journal from the property file]-25-04-07
	static String copyrightValinXML="";//arvind [To read the One_Column_Journal from the property file]-25-04-07
	static String copyrightValinOrder="";//arvind [To read the One_Column_Journal from the property file]-25-04-07
	public static String cmmment_rhtitle="";//30.07.2010
	public static boolean isBodyimRef=false;//30-10-2012
	static String FMS="";
	static boolean check_FMC=false;
	static boolean FMC_comment=false;
	static boolean DPC_comment=false;
	static boolean NOFMC_comment=false;
	static boolean TOCrequired=false;
	static boolean CClicense=false;
	static boolean CClicenseDummy=false;
	static boolean DucklingOrderStatus=false;
	static boolean isDandD=false;
	boolean DucklingOrderStatusForDND=false;
	static String  label_Eappended="";
	static boolean eappend=false;
	static boolean once=false;
	boolean flag=false;
	static boolean runningTitleQuery=false;
	static String ForRunninglable="";
	public static boolean forpalworVal=false;
	public static boolean forPalwor=false;
static Hashtable IsCutOffCR=new Hashtable();
public static boolean book_review_err=false;//06/03/2009
public static boolean book_review_otherref=false;//06/03/2009
public static boolean fmcforall=false;
public static boolean isTableGulliverCutOff=false;
public static boolean isNewDTDCutOff=true;
public static Hashtable S250ABP =null;//09-04-2010
public static Vector QueryVector ;//04-08-2011
public static String ppe = "";
static String serverstatus="";
 static StringBuffer  articleHead_Compact= null;
 tp.xt.XTLogger xt_log=tp.xt.XTLogger.getInstance();
	public XT()
	{
		
	}
	/*public XT(File xmlFile)
	{
		this.xmlFile = xmlFile;	
	}*/
	public XT(File xmlFile)throws IOException
	{
		jid          = "";
		aid          = "";
		articleNo	 = "";
		pii          = "";
		doi          = "";		
		antiTocJid=new Vector();
		Spanish_Reference_Jid=new Vector();
		Spanish_Reference_Jid_5APA=new Vector();
		hypnPT = new HyphenPT();		
		/*antiTocJid.addElement("CEJ");
		antiTocJid.addElement("HLC");
		antiTocJid.addElement("NBA");
		antiTocJid.addElement("YSMIM");
		antiTocJid.addElement("YICCN");
        antiTocJid.addElement("YCECA");
        antiTocJid.addElement("AENJ");
		antiTocJid.addElement("MIMM");
		antiTocJid.addElement("PHYST");
		antiTocJid.addElement("IMLET");
		antiTocJid.addElement("TRSTMH");
		antiTocJid.addElement("INHE");
		
		antiTocJid.addElement("RUMIN");
		antiTocJid.addElement("UCT");
		antiTocJid.addElement("PREVET");
		antiTocJid.addElement("ANTAGE");
		antiTocJid.addElement("AUCC");
		antiTocJid.addElement("RESPNB");
		antiTocJid.addElement("POSTEC");//[20/11/2007] change request by TPMS
		antiTocJid.addElement("LUNG");//[28/05/2008] change request by TPMS
		antiTocJid.addElement("JRI");// added by mukesh on 25-09-08 request by TPMS.
		antiTocJid.addElement("NANTOD");// added by mukesh on 31-10-08 request by TPMS.
		antiTocJid.addElement("ECOENG");// added by Ravi on 23-12-08 request by TPMS.
		antiTocJid.addElement("YSCBI");// added by Ravi on 27-01-09 request by TPMS.
		antiTocJid.addElement("EURR");// added by Ravi on 17-04-09 request by TPMS.
		antiTocJid.addElement("ARR");// added by Ravi on 18-02-10 request by TPMS.
		antiTocJid.addElement("CLAE");// added by Ravi on 18-02-10 request by TPMS.
		antiTocJid.addElement("PREVET");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("GAIPOS");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("JGEC");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("NCI");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("YSEIZ");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("YJFCA");// added by Ravi on 19-02-10 request by TPMS.
		antiTocJid.addElement("JNC");// added by Ravi on 07-04-10 request by TPMS.
		antiTocJid.addElement("PIO");// added by Ravi on 07-04-10 request by TPMS.
		antiTocJid.addElement("YSMIM");// added by Ravi on 31-05-10 request by TPMS.
		antiTocJid.addElement("JARE");// added by Ravi on 18-06-10 request by TPMS.
		antiTocJid.addElement("MAMBIO");// added by Ravi on 08-07-10 request by TPMS.
		antiTocJid.addElement("UFUG");// added by Ravi on 30-08-10 request by TPMS.
		antiTocJid.addElement("EJOP");// added by Ravi on 23-11-10 request by TPMS.
		antiTocJid.addElement("YDLD");// added by Ravi on 23-11-10 request by TPMS.
		antiTocJid.addElement("EIMC");// added by Ravi on 23-11-10 request by TPMS.
		antiTocJid.addElement("HIVAR");// added by Ravi on 07-03-11 request by TPMS.
		antiTocJid.addElement("ALLER");// added by Ravi on 18-03-11 request by TPMS.
		antiTocJid.addElement("HEAP");// added by Ravi on 05-05-11 request by TPMS.
		*/
		articleHead_Compact= new StringBuffer();

		bibStyle = new String();
		this.xmlFile = xmlFile;

		plusTable=new Hashtable();
		Guli_Table=new Hashtable();//16/02/2008
		New_DOI_Table=new Hashtable();
		FMSModel=new Hashtable();
		model6rhtitle="";
		articleType="";
		
		webJid=new Vector();
		webJid.addElement("ABS");
		webJid.addElement("ADV");
		webJid.addElement("ANN");
		webJid.addElement("CAL");
		webJid.addElement("CON");
		webJid.addElement("EDB");
		webJid.addElement("IND");
		webJid.addElement("LIT");
		webJid.addElement("MIS");
		webJid.addElement("NWS");
		webJid.addElement("OCN");
		webJid.addElement("PNT");
		webJid.addElement("PUB");

		thumbnailJid=new Vector();
		thumbnailJid.addElement("ABS");
		thumbnailJid.addElement("ADD");
		thumbnailJid.addElement("ADV");
		thumbnailJid.addElement("ANN");
		thumbnailJid.addElement("BRV");
		thumbnailJid.addElement("CAL");
		thumbnailJid.addElement("CNF");
		thumbnailJid.addElement("CON");
		thumbnailJid.addElement("EDB");
		thumbnailJid.addElement("EDI");
		thumbnailJid.addElement("EXM");
		thumbnailJid.addElement("IND");
		thumbnailJid.addElement("LIT");
		thumbnailJid.addElement("MIS");
		thumbnailJid.addElement("NWS");
		thumbnailJid.addElement("OCN");
		thumbnailJid.addElement("PNT");
		thumbnailJid.addElement("PRP");
		thumbnailJid.addElement("PRV");
		thumbnailJid.addElement("PUB");
		thumbnailJid.addElement("REQ");
		//thumbnailJid.addElement("PGL");

		stampJid=new Vector();
		stampJid_Remove=new Vector();
		stampJid.addElement("ADD");
		stampJid.addElement("CNF");
		stampJid.addElement("COR");
		stampJid.addElement("DIS");
		stampJid.addElement("EDI");
		stampJid.addElement("ERR");
		if(!jid.equalsIgnoreCase("VACUN"))
		{
			stampJid.addElement("EXM");
		}
		stampJid.addElement("FLA");
		stampJid.addElement("PRP");
		stampJid.addElement("PRV");
		stampJid.addElement("REQ");
		stampJid.addElement("REV");
		stampJid.addElement("SCO");
		stampJid.addElement("SSU");
		stampJid.addElement("BRV");
		stampJid.addElement("PGL");
		stampJid.addElement("CRP");
		stampJid.addElement("MIC");
		//System.out.println("---->>"+jid);
		//stampJid.addElement("NWS");
										
		modelFilePath = databasePath+"model.dbf";//original
		//modelFilePath = databasePath+"model1.dbf";

		//if(System.getenv("FMS_SERVER_140906") != null)
			//isFMSPC = System.getenv("FMS_SERVER_140906");
		RandomAccessFile raf=null;
		forPalwor=getTagValueForPalwor(xmlFile,"ce:author-group");
		String line = new String();
		try
		{
			raf = new RandomAccessFile(databasePath + "onecolumn.dbf","r");
			while((line=raf.readLine())!=null)
			{
				String temp=line;
				
				if(temp.indexOf("STAMPJID=",0)!=-1)
				{
					//System.out.println("stampJid line------>>"+temp);
					temp=temp.substring(temp.indexOf("STAMPJID=",0)+"STAMPJID=".length(),temp.length());
					
					stampJid_Remove.addElement(temp);
				}
				if(temp.indexOf("ANTITOCJID=",0)!=-1)
				{
					//System.out.println("stampJid line------>>"+temp);
					temp=temp.substring(temp.indexOf("ANTITOCJID=",0)+"ANTITOCJID=".length(),temp.length());
					
					antiTocJid.addElement(temp);
				}

				if(temp.indexOf("SPANISH_REFERENCE_JID=",0)!=-1)
				{
					//System.out.println("stampJid line------>>"+temp);
					temp=temp.substring(temp.indexOf("SPANISH_REFERENCE_JID=",0)+"SPANISH_REFERENCE_JID=".length(),temp.length());
					
					Spanish_Reference_Jid.addElement(temp);
				}
				if(temp.indexOf("SPANISH_REFERENCE_JID_5APA=",0)!=-1)//Added by Vivek on 04-07-2013
				{
					//System.out.println("SPANISH_REFERENCE_JID_5APA------>>"+temp);
					temp=temp.substring(temp.indexOf("SPANISH_REFERENCE_JID_5APA=",0)+"SPANISH_REFERENCE_JID_5APA=".length(),temp.length());
					
					Spanish_Reference_Jid_5APA.addElement(temp);
				}
				
				
				//System.out.println("line------>>"+OneCol_Journal);
			}
			raf.close();
			//System.out.println("antiTocJid------>>"+antiTocJid);
			//System.out.println("webJid------>>"+webJid);
			//System.out.println("thumbnailJid------>>"+thumbnailJid);
			//System.out.println("stampJid------>>"+stampJid);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		

	}
public String getCopyright(String copyrType, String copyrYear, String tag)throws java.io.IOException//Under Development 14-06-2011
	{
		String copyRightLine="";
		String ccltext="";
		String CopyrightinXML="";
		String copyrightCutOff="";
		Properties properties =new Properties();
		Properties findCutOff =new Properties();
		Vector v = new Vector();
		v.addElement((String)"JOURNAL");
		v.addElement((String)"UNKNOWN");
		v.addElement((String)"FULL-TRANSFER");
		v.addElement((String)"SOCIETY");
		v.addElement((String)"JOINT");
		v.addElement((String)"US-GOV");
		v.addElement((String)"CROWN");
		v.addElement((String)"LIMITED-TRANSFER");
		v.addElement((String)"OTHER");
		v.addElement((String)"FREE-OF-COPYRIGHT");
		v.addElement((String)"NO-TRANSFER");
		int index = v.indexOf((String)copyrType);

		String temp_aid=aid;//check for duckling item.
		findCutOff.load(new FileInputStream(databasePath+"\\CopyRightCutOff.dbf"));
		copyrightCutOff=findCutOff.getProperty(jid,"0");
		
		if(temp_aid.indexOf(".",0)!=-1)
		{
			temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
		}
		ReadForEextra rfe= new ReadForEextra(jid,temp_aid,stage);
		String status=rfe.GetCopyrightStatus();
		//String cclstatus=rfe.GetCCLicenseStatus();
		copyrightValinOrder=status;
		if(status.equalsIgnoreCase("000"))
			return status;
		try{
			if(!tag.endsWith("/>"))
			CopyrightinXML= xmlObj.extractData("</CE:COPYRIGHT>", true);
			//System.out.println("jid ::> "+jid);
			//System.out.println("cutOffAidVal ::> "+copyrightCutOff);
			if(copyrightCutOff.equals("0"))
			{
				//System.out.println("Source of Copyright :: "+databasePath+"\\global_copyright_information.ini");
				properties.load(new FileInputStream(databasePath+"\\global_copyright_information.ini"));//live
			}
			else
			{
				if(Integer.parseInt(copyrightCutOff) <= Integer.parseInt(aid))
				{
					System.out.println("Copyright DBF Name::"+databasePath+"\\global_copyright_informationException.ini");
					properties.load(new FileInputStream(databasePath+"\\global_copyright_informationException.ini"));//live
				}
				else
				{
					//System.out.println("Source of Copyright :: "+databasePath+"\\global_copyright_information.ini");
					properties.load(new FileInputStream(databasePath+"\\global_copyright_information.ini"));	
				}
			}
			if(articleType.isEmpty()){
				articleType = "EN";
			}
			//properties.load(new FileInputStream("D:\\PROJECTS\\XT-LIVE_ID\\global_copyright_information.ini"));//test
			ccltext=getCCCopyright(articleType);//28-01-2014

			if(properties.getProperty(jid+"."+copyrType+"."+articleType)!=null)
			{
				if(jid.equalsIgnoreCase("ARTERI") && (Integer.parseInt(temp_aid)<271))//Updated on 27-01-2014
				{
					jid="ARTERIOLD";
					copyRightLine=properties.getProperty(jid+"."+copyrType+"."+articleType);
					//System.out.println("Copyright line before cut-off number.");
					jid="ARTERI";
				}
				else
				{
					copyRightLine=properties.getProperty(jid+"."+copyrType+"."+articleType);//28-01-2014
				}
				//System.out.println("copyRightLine--->>"+copyRightLine);
			}
			if(copyRightLine.length()>0)
			{
				//copyRightLine=copyRightLine.replaceAll("<YEAR>",copyrYear);

				
					if(CopyrightinXML.endsWith("."))
					{
						CopyrightinXML=CopyrightinXML.substring(0,CopyrightinXML.length()-1);
					}
					StringBuffer s=new StringBuffer(copyRightLine);
					int spos=0;
					spos=s.indexOf("<YEAR>",0);
					if(spos!=-1)
					{
						s=s.delete(spos,spos+"<YEAR>".length());
						s=s.insert(spos,copyrYear);
						copyRightLine=s.toString();	
					}
					s=new StringBuffer(copyRightLine);
					spos=0;
					spos=s.indexOf("<FROMXML>",0);
					if(spos!=-1)
					{
						s=s.delete(spos,spos+"<FROMXML>".length());
						s=s.insert(spos,CopyrightinXML);
						copyRightLine=s.toString();	
					}
					//copyRightLine=copyRightLine.replaceAll("<FROMXML>",CopyrightinXML);					
					copyRightLine=copyRightLine;
				if(ccltext.length()>0)
				{
					//copyRightLine=properties.getProperty(jid+"."+copyrType+"."+articleType);
					copyRightLine=copyRightLine.substring(0,copyRightLine.length()-1);
					copyRightLine=copyRightLine+" \\CCLcopyright{"+ccltext+"}}";//28-01-2014
					//if(!CClicenseDummy)
					//{
						copyRightLine=removeTextforCCLicence(copyRightLine);
						if(copyrightLineForEnd==null || copyrightLineForEnd.isEmpty()){
							copyrightLineForEnd=copyRightLine;
//							System.out.println("copyrightLineForEnd--1-->"+copyrightLineForEnd);
						}
						
					//}
				}
				//System.out.println("copyRightLine===>>"+copyRightLine);

				if(!(once))
				{
					switch (index)
					{
						case 1:copyRightLine+="\r\n\\copyrightStatus{001}";
							   break;
						case 2:copyRightLine+="\r\n\\copyrightStatus{002a}";
							   break;
						case 3:copyRightLine+="\r\n\\copyrightStatus{002b}";
							   break;
						case 4:copyRightLine+="\r\n\\copyrightStatus{002c}";
							   break;
						case 5:copyRightLine+="\r\n\\copyrightStatus{003}";
							   break;
						case 6:copyRightLine+="\r\n\\copyrightStatus{004}";
							   break;
						case 7:copyRightLine+="\r\n\\copyrightStatus{005}";
							   break;
						case 8:copyRightLine+="\r\n\\copyrightStatus{006}";
							   break;
						case 9:copyRightLine+="\r\n\\copyrightStatus{007}";
							   break;		
						case 10:copyRightLine+="\r\n\\copyrightStatus{007}";
						   break;		
					}
				once=true;
				}
			}
			else
			{
				copyRightLine=getCopyright_Live(copyrType, copyrYear, tag);
				if(ccltext.length()>0)
				{
					StringBuffer s=new StringBuffer(copyRightLine);
					if(s.indexOf("}\r\n\\copyrightStatus",0)!=-1)
					{
						int tt=s.indexOf("}\r\n\\copyrightStatus",0);
						s=s.insert(tt," \\CCLcopyright{"+ccltext+"}");
						copyRightLine=s.toString();
						//if(!CClicenseDummy)
						//{
							copyRightLine=removeTextforCCLicence(copyRightLine);
							if(copyrightLineForEnd==null || copyrightLineForEnd.isEmpty()){
								copyrightLineForEnd=copyRightLine;
//								System.out.println("copyrightLineForEnd--2-->"+copyrightLineForEnd);
							}
						//}
					}
					else
					{
						copyRightLine=copyRightLine.substring(0,copyRightLine.length()-1);
						copyRightLine=copyRightLine+" \\CCLcopyright{"+ccltext+"}}";//28-01-2014
						//if(!CClicenseDummy)
						//{
							copyRightLine=removeTextforCCLicence(copyRightLine);
							if(copyrightLineForEnd==null || copyrightLineForEnd.isEmpty()){
								copyrightLineForEnd=copyRightLine;
//								System.out.println("copyrightLineForEnd--3-->"+copyrightLineForEnd);
							}
						//}
						//System.out.println ("NEW copyRightLine===>"+copyRightLine);
					}
				}
				//System.out.println("copyRightLine===>>"+copyRightLine);
			}
			
		}catch (IOException e) {

			copyRightLine="";
			e.printStackTrace();
			System.out.println("[ERROR]: PROBLEM IN READING COPY RIGHT LINE IN FILE. PLEASE CONTECT TO R&D TEAM..");
			System.exit(0);
    	
		}
		//System.out.println("copyRightLine--->>"+copyRightLine);
		return copyRightLine;
	}

public String removeTextforCCLicence(String cprt)//28-01-2014
	{
		String copyRightLine=cprt;
		String removetext="";
		File dbPath=new File(databasePath+"\\CCLicense.dbf");
		RandomAccessFile dbfile = null;

		try{
			dbfile = new RandomAccessFile(dbPath,"r");
			String line=null;
			while((line = dbfile.readLine()) != null)
			{
				if(line.startsWith("<RemoveText>"))
				{
					removetext = line;
					removetext=removetext.replaceAll("<RemoveText><([A-Z]+)>=","");
					copyRightLine=copyRightLine.replaceAll(removetext,"\\\\allright{"+removetext+"}");
					copyRightLine=copyRightLine.replaceAll("  "," ");
					//System.out.println("Removetext=====>"+removetext);
				}
			}
		}
		catch (IOException e)
		{
			try{dbfile.close();}catch(Exception exp){exp.printStackTrace();}
			
			e.printStackTrace();
			System.out.println("[ERROR]: PROBLEM IN READING CC LICENSE COPY RIGHT LINE IN FILE. PLEASE CONTECT TO R&D TEAM..");
			System.exit(0);
		}
		//System.out.println("copyRightLine--->>"+copyRightLine);
		return copyRightLine;
	}

public String getCCCopyright(String lang)throws java.io.IOException//28-01-2014
	{
		String copyRightLine="";
		String CopyrightinXML="";
		String cclversion="3.0";
		
		Properties cclcutoff =new Properties();//28-01-2014
		FileInputStream cclno = new FileInputStream(databasePath+"\\CCLicenseCutOff.dbf");//28-01-2014
		Properties cclppt =new Properties();//28-01-2014
		//FileInputStream ccldbf = new FileInputStream(databasePath+"\\CCLicense.dbf");//28-01-2014
		FileInputStream ccldbf = new FileInputStream(databasePath+"\\CCLicenseNEW.dbf");//28-01-2014

		String temp_aid=aid;//check for duckling item.
		if(temp_aid.indexOf(".",0)!=-1)
		{
			temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
		}
		ReadForEextra rfe= new ReadForEextra(jid,temp_aid,stage);
		String status=rfe.GetCopyrightStatus();
		String cclstatus=rfe.GetCCLicenseStatus();
		if(cclstatus.indexOf("4.0")!=-1){
			cclstatus=cclstatus.replaceAll("-4.0", "");
			cclversion="4.0";
		}else if(cclstatus.indexOf("3.0")!=-1){
			cclstatus=cclstatus.replaceAll("-3.0", "");
			cclversion="3.0";
		}
		copyrightValinOrder=status;
		if(status.equalsIgnoreCase("000"))
			return status;
		try{
			//***********************************************************************
			//Checking CCLicense cut-off and CCLicense text//28-01-2014
			cclcutoff.load(cclno);
			cclno.close();
			cclppt.load(ccldbf);
			ccldbf.close();
			
			if(cclcutoff.getProperty(jid)!=null)
			{
				if(Integer.parseInt(temp_aid)>=Integer.parseInt(cclcutoff.getProperty(jid)))
				{
					CClicense=true;
				}
			}
			//System.out.println("CCLICENSE CUT-OFF===>"+CClicense);
			if(CClicense)
			{
				//System.out.println("cclstatus===>"+cclstatus);
				if(cclstatus.equalsIgnoreCase("NotFound"))
				{
					CClicenseDummy=true;
				}
				//System.out.println("CCLICENSE Status in order===>\""+cclstatus+"\"");
				if(CClicenseDummy)
				{
					if(cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">")!=null)
					{
						//System.out.println("JID WISE CCLICENSE text from CCLicense.dbf===>"+cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">"));
						copyRightLine=cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">");
					}
					else if(cclppt.getProperty(cclstatus+"<"+lang+">")!=null)
					{
						//System.out.println("CCLICENSE text from CCLicense.dbf===>"+cclppt.getProperty(cclstatus+"<"+lang+">"));
						copyRightLine=cclppt.getProperty(cclstatus+"<"+lang+">");
					}
					else
					{
						System.out.println("**********************************************************");
						System.out.println("CCLICENSE text not found for "+cclstatus+"<"+lang+"> in CCLicense.dbf");
						System.out.println("**********************************************************");
					}
				}
				else
				{
					if(cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">")!=null)
					{
						//System.out.println("JID WISE CCLICENSE text from CCLicense.dbf===>"+cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">"));
						copyRightLine=cclppt.getProperty(jid+"-"+cclstatus+"<"+lang+">");
					}
					else if(cclppt.getProperty(cclstatus+"<"+lang+">")!=null)
					{
						//System.out.println("CCLICENSE text from CCLicense.dbf===>"+cclppt.getProperty(cclstatus+"<"+lang+">"));
						copyRightLine=cclppt.getProperty(cclstatus+"<"+lang+">");
					}
					else
					{
						System.out.println("**********************************************************");
						System.out.println("CCLICENSE text not found for "+cclstatus+"<"+lang+"> in CCLicense.dbf");
						System.out.println("**********************************************************");
					}
				}
			}
			//***********************************************************************
		}catch (IOException e) {

			copyRightLine="";
			e.printStackTrace();
			System.out.println("[ERROR]: PROBLEM IN READING CC LICENSE COPY RIGHT LINE IN FILE. PLEASE CONTECT TO R&D TEAM..");
			System.exit(0);
		}
		copyRightLine=copyRightLine.replaceAll("\\[version\\]", cclversion);
		//System.out.println("copyRightLine--->>"+copyRightLine);
		return copyRightLine;
	}

	public String getCopyright_Live(String copyrType, String copyrYear, String tag)throws java.io.IOException
	{
		String dbfsPath= databasePath;
		String line = new String();
		String copy = null;
		String ccltext="";
		File copyPath=new File(dbfsPath+"copyright.dbf");
		//System.out.println("tag---"+tag);
		System.out.println("articleType :: "+articleType);
		if(articleType.isEmpty()){
			articleType="EN";
		}
		RandomAccessFile dbf = new RandomAccessFile(copyPath,"r");
   		while((line = dbf.readLine()) != null)
   		{
     		if(line.startsWith(jid+"<"))
     		{
				copy = line;
				break;
     		}
    	}
		dbf.close();
		if (copy==null)
		{
			System.out.println("ERROR [XT]: Copyright Information not Found For '"+jid+"' and Language "+articleType);
			System.exit(0);
		}
    	
		//System.out.println("1 getcopyright---");
		return splitCopyRights(copy, copyrType, copyrYear, tag);
	}
//****************************************
/*
*[Change Request By Subrata's Mail On Dated 18/12/2006]
*[Added By Ravi 18/12/2006]
*Adding this Function is to check Journal is Franch or Not
*
*/
	public int getInt(String str){
		int number = -1;
		try {
			number = (int) Float.parseFloat(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
	
	public static int getInteger(String str){
		int number = -1;
		try {
			number = (int) Float.parseFloat(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return number;
	}
	public boolean isTejJid(String jid, String aid) throws IOException{
		//System.out.println("Checking jid and aid "+jid+"_"+aid+" in LemonsJID.dbf");
		Properties prop = new Properties();
		prop.load(new FileInputStream(databasePath+"TeJJournal.dbf"));
		int cutoff = getInt(prop.getProperty(jid,"0"));
		if(!prop.getProperty(jid,"").equals("")){
			if(cutoff <= getInt(aid)){
				//System.out.println("Item is under LemonsJID cutoff ::> "+jid+"_"+aid);
				return true;
			}else{
				//System.out.println("Item is not under LemonsJID cutoff ::> "+jid+"_"+aid);
			}
		}else{
			//System.out.println("JID "+jid+" not found in "+databasePath+"LemonsJID.dbf");
		}
		return false;
	}
	public boolean isLemansJid(String jid, String aid) throws IOException{
		//System.out.println("Checking jid and aid "+jid+"_"+aid+" in LemonsJID.dbf");
		Properties prop = new Properties();
		prop.load(new FileInputStream(databasePath+"LemonsJID.dbf"));
		int cutoff = getInt(prop.getProperty(jid,"0"));
		if(!prop.getProperty(jid,"").equals("")){
			if(cutoff <= getInt(aid)){
				//System.out.println("Item is under LemonsJID cutoff ::> "+jid+"_"+aid);
				return true;
			}else{
				//System.out.println("Item is not under LemonsJID cutoff ::> "+jid+"_"+aid);
			}
		}else{
			//System.out.println("JID "+jid+" not found in "+databasePath+"LemonsJID.dbf");
		}
		return false;
	}
	
	public static boolean isPPEworkflow(String jid, String aid) throws IOException{
		System.out.println("Checking jid and aid "+jid+"_"+aid+" in PPEcutoff.ini");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Properties prop = new Properties();
		prop.load(new FileInputStream(databasePath+"PPEcutoff.ini"));
		int cutoff = getInteger(prop.getProperty(jid,"0"));
		if(!prop.getProperty(jid,"").equals("")){
			if(cutoff <= getInteger(aid)){
				System.out.println("Item is under PPEcutoff cutoff ::> "+jid+"_"+aid);
				return true;
			}else{
				System.out.println("Item is not under PPEcutoff cutoff ::> "+jid+"_"+aid);
			}
		}else{
			System.out.println("JID "+jid+" not found in "+databasePath+"PPEcutoff.ini");
		}
		return false;
	}

	public boolean Check_Journal_For_Franch(String FJid) throws IOException
	{
		
		String line="";
		boolean check_french=false;
		File Franch_copyPath=new File(databasePath+"Franch.dbf");
		RandomAccessFile Franch_dbf = new RandomAccessFile(Franch_copyPath,"r");
   		while((line = Franch_dbf.readLine()) != null)
   		{
			if(line.equalsIgnoreCase(FJid))
			{
				check_french=true;
				break;
			}
			else
			{
				check_french=false;
			}
		}
		
		return check_french;
	}

//***************************************
	private String splitCopyRights(String copy, String copyrType, String copyrYear, String tag)throws java.io.IOException
	{
		Vector vCopy = new Vector();
		StringTokenizer str = new StringTokenizer(copy,"<<>>");

		while(str.hasMoreElements())
		{
			vCopy.addElement((String)str.nextToken());
				
		}
		
		return extractCopyRight(vCopy, copyrType, copyrYear, tag);
	}

	private String extractCopyRight(Vector vCopy, String copyrType, String copyrYear, String tag)throws java.io.IOException
	{
		//System.out.println("extractCopyRight1---------------------"+vCopy);
		String type= copyrType;
		String year = copyrYear;
		Vector v = new Vector();
		//System.out.println("extractCopyRight2");
		v.addElement((String)"JOURNAL");
		v.addElement((String)"UNKNOWN");
		v.addElement((String)"FULL-TRANSFER");
		v.addElement((String)"SOCIETY");
		v.addElement((String)"JOINT");
		v.addElement((String)"US-GOV");
		v.addElement((String)"CROWN");
		v.addElement((String)"LIMITED-TRANSFER");
		v.addElement((String)"OTHER");
		v.addElement((String)"FREE-OF-COPYRIGHT");
		v.addElement((String)"NO-TRANSFER");
		int index = v.indexOf((String)type);
		String copyRightdbf="";
		if(index != 9 && index != 10)
			copyRightdbf =(String) vCopy.elementAt(index);
//		System.out.println(index+" "+type+" extractCopyRight : "+copyRightdbf);
		//System.in.read();
/*
*[Change Request By Subrata's Mail On Dated 18/12/2006]
*[Added By Ravi 18/12/2006]
*Adding this Function is to check Journal is Franch or Not
*
*/
	/*	boolean check_french_Journal=Check_Journal_For_Franch(jid);
		if(check_french_Journal==true)
		{
			articleType="FR";
		}
	*/
//end	


//******************************************
/*String AD=aid;
boolean Check_Resupply_For_S200=false;
if(isOrderPathAvailable.equalsIgnoreCase("yes")){
	if(AD.indexOf(".",0)!=-1)
	{
		AD=AD.substring(0,AD.indexOf(".",0));
	}
	ReadForEextra Readfmc= new ReadForEextra(jid.toUpperCase(),AD,stage);
	if(stage.equalsIgnoreCase("S200"))
	{
		Check_Resupply_For_S200=Readfmc.readOrderFileForResupply(jid.toUpperCase(),AD,stage);
	}
	
//System.out.println(Readfmc.readOrderFileForResupply(jid.toUpperCase(),AD,stage)+" Check_Resupply_For_S200 : "+Check_Resupply_For_S200+" stage "+stage);
}*/
String temp_aid=aid;//check for duckling item.//14/04/2009

if(temp_aid.indexOf(".",0)!=-1)
{
	temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
}
//****************************************

//************************************************
//System.out.println("stage : "+stage);
ReadForEextra rfe= new ReadForEextra(jid,temp_aid,stage);
String status=rfe.GetCopyrightStatus();
copyrightValinOrder=status;
//System.out.println("status : "+status);
if(status.equalsIgnoreCase("000"))
	return status;
//*******************************************************
		String  copyRightLine = new String("\\copyrightline{");
		//System.out.println("extractCopyRight4");
		String orgCopyright= "";

		if(!tag.endsWith("/>"))
			orgCopyright= xmlObj.extractData("</CE:COPYRIGHT>", true);
			//System.out.println("extractCopyRight >>index>> "+index);
			//System.out.println("extractCopyRight >status>>> "+status);
			//System.out.println("extractCopyRight >>type>> "+type);
		if (index==1)
		{
			//System.out.println("extractCopyRight 5");
			//Unknown
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
				{
					//copyRightLine+="\\copyright~L`Encéphale, Paris, 2007.";
					copyRightLine+="\\copyright~"+copyRightdbf+", "+year+".";
				}
				else if(jid.equals("REAURG"))
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la  Société de réanimation de langue française.";
				}
				else if( jid.equals("PRPS"))
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société française de psychologie.";
				}
				else if(jid.equals("PSFR"))
				{
					//copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour l'Société française de psychologie.";
					//
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société française de psychologie.";

				}
				else if(jid.equals("ALTER"))
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour l'Association ALTER.";
				}
				else if(jid.equals("ANICOM"))
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour l'AFVAC.";
				}
				else if(jid.equals("JTCC"))//18/02/2008
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour l'Association française de thérapie comportementale et cognitive.";
																						  
				}
				else if(jid.equals("REVRHU"))//27/11/2008
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société Française de Rhumatologie.";
																						  
				}
				else if(jid.equals("CND"))//27/11/2008
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société française de nutrition.";
																						  
				}
				else if(jid.equals("BONSOI"))//11/02/2009
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société Française de Rhumatologie.";
																						  
				}
				else if(jid.equals("MONRHU"))//11/12/2009
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+" pour la Société française de rhumatologie.";
																						  
				}
				else if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS pour la Société nationale française de médecine interne (SNFMI).";
					}
				else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS pour la Société française de radiothérapie oncologique (SFRO).";
					}
				else if(jid.equals("CANRAD"))
					{
						copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS pour la Société Française de médecine d'urgence.";
					}
				else if(jid.equals("PEDPUE"))
					{
						copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS.";
					}
					else if(jid.equals("RMR"))
					{
						copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS pour la SPLF.";
					}
				
				else
				{
					copyRightLine+="\\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf;//Bhavesh
					
				}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
					copyRightLine+="\\copyright~"+year+"~Publicado por "+copyRightdbf;
				else if(jid.equals("FARMA"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEFH.";
				else if(jid.equals("ARBRES"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEPAR.";
				else if(jid.equals("ARBR"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEPAR.";
				else if(jid.equals("APUNTS"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Consell Català de l'Esport. Generalitat de Catalunya.";
				else if(jid.equals("GACETA"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SESPAS.";
				else if(jid.equals("ANPEDIA"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Pediatría.";
				else if(jid.equals("OFTAL"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Sociedad Española de Oftalmología.";
				else if(jid.equals("ENDONU"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEEN.";
				else if(jid.equals("SEMREU"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SER.";
				else if(jid.equals("RECOT"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SECOT.";
				else if(jid.equals("REGG"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEGG.";
				else if(jid.equals("ACURO"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de AEU.";
				else if(jid.equals("ACUROE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de AEU.";
				else if(jid.equals("CALI"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SECA.";
				else if(jid.equals("RX"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SERAM.";
				else if(jid.equals("FT"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Fisioterapeutas.";
				else if(jid.equals("HIPERT"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEHLELHA.";
				else if(jid.equals("RIAM"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Revista Iberoamericana de Micología.";
				else if(jid.equals("CARCOR"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SAC.";
				else if(jid.equals("CEDE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de ACEDE.";
				else if(jid.equals("DIALIS"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEDYT.";
				else if(jid.equals("RPPNEU"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Pneumologia.";
				else if(jid.equals("PATOL"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEAP y SEC.";
				else if(jid.equals("LABCLI"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de AEBM, AEFA y SEQC.";
				else if(jid.equals("RPSM"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEP y SEPB.";
				else if(jid.equals("PSIQ"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U.";
				else if(jid.equals("GASTRO"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U.";
				else if(jid.equals("DIAPRE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Diagnóstico Prenatal.";
				else if(jid.equals("RIFK"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Fisioterapeutas.";
				else if(jid.equals("ANGIO"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEACV.";
				else if(jid.equals("MAXILO"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SECOM.";
				else if(jid.equals("IHE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Historia Económica.";
				else if(jid.equals("NEUARG"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Sociedad Neurológica Argentina.";
				else if(jid.equals("RPEMD"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Estomatologia e Medicina Dentária.";
				else if(jid.equals("INMUNO"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de Sociedad Española de Inmunología.";
				else if(jid.equals("FARMAE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. en nombre de SEFH.";
				else
					copyRightLine+="\\copyright~"+year+"~Publicado por "+copyRightdbf;
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+="\\copyright~"+year+"~Pubblicato da "+copyRightdbf;
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+="\\copyright~"+year+"~Publicat per Elsevier España, S.L.U. en nom del Consell Català de l'Esport. Generalitat de Catalunya.";
			}
			else if(articleType.equalsIgnoreCase("PT"))
			{
				if(jid.equals("RPPNEU"))
					copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Pneumologia.";
				else if(jid.equals("RPPNEN"))
					copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Pneumologia.";
				else if(jid.equals("RPEMD"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Estomatologia e Medicina Dentária.";
				else
				copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Asociación Española de Diagnóstico Prenatal.";
			}
			else if(articleType.equalsIgnoreCase("PT"))
			{
				if(jid.equals("PULMOE"))
					copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Pneumologia.";
				else if(jid.equals("PULMOE"))
					copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Pneumologia.";
				else if(jid.equals("PULMOE"))
					copyRightLine+="\\copyright~"+year+"~Publicado por Elsevier España, S.L.U. em nome da Sociedade Portuguesa de Estomatologia e Medicina Dentária.";
				else
				copyRightLine+="\\copyright~"+year+" Publicado por Elsevier España, S.L.U. em nome da Asociación Española de Diagnóstico Prenatal.";
			}
			else
			{
				if(jid.equals("RETAIL"))
				{
					copyRightLine+="\\copyright~"+year+" "+copyRightdbf;
				}
				else if(jid.equals("CERI")) // 07-01-2005
				{
					copyRightLine+="\\copyright~"+year+"~Published by Elsevier Ltd and Techna Group S.r.l.";
				}
				else if(jid.equals("INHE")) // 14-04-2009
				{
					copyRightLine+="\\copyright~"+year+"~Published by Elsevier Ltd on behalf of Royal Society of Tropical Medicine and Hygiene.";
				}
				else if(jid.equals("PALEVO") || jid.equals("CRAS2A"))
					copyRightLine += "\\copyright~" + year + "~Published by Elsevier Masson SAS on behalf of l'Académie des sciences.";
				else if(jid.equals("ALTER"))
				{
					copyRightLine+="\\copyright~"+year+ "~Published by Elsevier Masson SAS on behalf of Association ALTER.";
				}
				else if(jid.equals("CND"))
				{
					copyRightLine+="\\copyright~"+year+ "~Published by Elsevier Masson SAS on behalf of Société française de nutrition.";
				}
				else if(jid.equals("BONSOI"))
				{
					copyRightLine+="\\copyright~"+year+ "~Published by Elsevier Masson SAS on behalf of the Société Française de Rhumatologie.";
				}
				//added by debottam for RPOR 03-12-09
				else if(jid.equals("RPOR"))
				{
					copyRightLine+="\\copyright~"+year+"~ "+copyRightdbf;
				}
				else if(jid.equals("MONRHU"))//11/12/2009
				{
					copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of the Société française de rhumatologie.";
																						  
				}
				else
				{
					if(jid.equals("ENCEP"))
					{
						//copyRightLine+="\\copyright~L`Encéphale, Paris, 2007.";
						copyRightLine+="\\copyright~"+copyRightdbf+", "+year+".";
					}
					else if(jid.equals("REAURG"))
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of Société de réanimation de langue française.";
					}																		
					else if(jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of Société française de psychologie.";

					}
					else if(jid.equals("ANICOM"))
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of AFVAC.";
					}
					else if(jid.equals("JTCC"))//18/02/2008
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of Association française de thérapie comportementale et cognitive.";
					}
					else if(jid.equals("ALTER"))//18/02/2008
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of Association ALTER.";
					}
					else if(jid.equals("REVRHU"))//27/11/2008
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf+" on behalf of Société Française de Rhumatologie.";
																							  
					}
					else if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="\\copyright~"+year+"~Published by Elsevier Masson SAS on behalf of the Société nationale française de médecine interne (SNFMI).";
					}
					else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="\\copyright~"+year+"~ Published by Elsevier Masson SAS on behalf of the Société française de radiothérapie oncologique (SFRO).";
					}
					else if(jid.equals("PEDPUE"))
					{
						copyRightLine+="\\copyright~"+year+"~ Published by Elsevier Masson SAS.";
					}
					else if(jid.equals("RMR"))
					{
						copyRightLine+="\\copyright~"+year+"~Published by Elsevier Masson SAS on behalf of SPLF.";
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+="\\copyright~"+year+" "+copyRightdbf;
					}
					else if(jid.equals("RDF"))
					{
						copyRightLine+="\\copyright~"+year+"~Production and hosting by "+copyRightdbf;
					}
					else
					{
						copyRightLine+="\\copyright~"+year+"~Published by "+copyRightdbf;
					}
				}
			}
		}
		else if (index==2)
		{
			//System.out.println("extractCopyRight6");
		//Full-Transfer
			if(!jid.equals("ENCEP"))
			{
				copyRightLine+="\\copyright~"+year+"~";
			}
			//copyRightLine+="\\copyright~"+year+"~";
			if(!orgCopyright.endsWith("."))
				orgCopyright+=".";
			if(articleType.equalsIgnoreCase("FR"))
			{
				
				if(jid.equals("ENCEP"))
				{
					copyRightLine+="\\copyright~";

					//copyRightLine+="L`Encéphale, Paris, 2007.";
					copyRightLine+=orgCopyright+", "+year+".";;
				}//MEDPAL
				else if(jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
				{

					if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="Publié par Elsevier Masson SAS pour la Société nationale française de médecine interne (SNFMI).";
					}
					else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Publié par Elsevier Masson SAS. Tous droits réservés.";
					}
					else
					{
						copyRightLine+=orgCopyright+" Tous droits réservés.";
					}
				}
				else if (jid.equals("REAURG"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				else if (jid.equals("PSFR")|| jid.equals("PRPS"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				//RCO
				else if(jid.equals("RCO"))
				{
					//copyRightLine+=orgCopyright+" et Association Sociétéde Néphrologie. Tous droits réservés.";
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
				else if(jid.equals("DOULER")|| jid.equals("PHARMA")|| jid.equals("PNEUMO"))
				{
					
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
				else if(jid.equals("ALTER"))
				{
					//copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					copyRightLine+=" Publié par "+orgCopyright+" Tous droits réservés.";
				}
				else if(jid.equals("JFO")||jid.equals("ANNDEROLD")||jid.equals("FANDER")||jid.equals("ACVDSP")||jid.equals("JEMEP")||jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE")||jid.equals("NPG")||jid.equals("PUROL")||jid.equals("ACVD")||jid.equals("ETIQE")||jid.equals("SAGF"))
				{
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
				else if(jid.equals("PEDPUE"))
				{
					//copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
				else
				{
					//System.out.println(">>>>>>>>>>>>>>>>>>>>>>> "+orgCopyright);
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				copyRightLine+="~Elsevier Ltd. Todos los derechos reservados.";
				else if(jid.equals("PSIQ"))
				copyRightLine+="Elsevier España, S.L.U. Todos los derechos reservados.";
				else if(jid.equals("IHE"))
				copyRightLine+="~Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Historia Económica.";
				else if(jid.equals("JVAC"))
				copyRightLine+="~Elsevier Ltd. Todos los derechos reservados.";
				else
				copyRightLine+=orgCopyright+" Todos los derechos reservados.";
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+=orgCopyright+" Tutti i diritti riservati.";
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+=orgCopyright+" Tots els drets reservats.";
			}
			else if(articleType.equalsIgnoreCase("PT"))
			{
				if(jid.equals("RPPNEU"))
					copyRightLine+="\\copyright~"+year+" Sociedade Portuguesa de Pneumologia. Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				else if(jid.equals("JVAC"))
					copyRightLine+="\\copyright~"+year+" Elsevier Ltd. Todos os direitos reservados.";
			}
			else
			{
				if(jid.equals("ENCEP"))
				{
					copyRightLine+="\\copyright~";
					//copyRightLine += "L`Encéphale, Paris, 2007.";
					copyRightLine += orgCopyright+", "+year+".";;
				}
				else if(jid.equals("CERI"))
					copyRightLine+="Elsevier Ltd and "+orgCopyright+" "+copyRightdbf;
				else if(jid.equals("SSI"))
					copyRightLine+="by "+orgCopyright+" "+copyRightdbf;
				else if(jid.equals("NEUCLI") || jid.equals("GEOBIO") || jid.equals("ANTHRO") || jid.equals("ANNPAL") || jid.equals("REVMIC") || jid.equals("ENCEPH")|| jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
				{
					//System.out.println("jid ravi  "+jid);
					if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="Société nationale française de médecine interne (SNFMI). Published by Elsevier Masson SAS. All rights reserved.";

					}
					else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Published by Elsevier Masson SAS. All rights reserved.";
					}
					else
					{
						copyRightLine+=orgCopyright+" All rights reserved.";
					}
				}
				else if (jid.equals("REAURG"))
				{
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
				}
				else if (jid.equals("PSFR")|| jid.equals("PRPS"))
				{
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
				}
				else if(jid.equals("ALTER"))
				{
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
				}
				else if(jid.equals("JFO")||jid.equals("ANNDEROLD")||jid.equals("FANDER")||jid.equals("ACVDSP")||jid.equals("JEMEP")||jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE")||jid.equals("NPG")||jid.equals("PUROL")||jid.equals("ACVD"))
				{
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else if(jid.equals("RCO")||jid.equals("ETIQE")|| jid.equals("PNEUMO"))
				{
					//copyRightLine+=orgCopyright+" and Association Sociétéde Néphrologie All rights reserved.";
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else if(jid.equals("DOULER")|| jid.equals("PHARMA")||jid.equals("SAGF"))
				{
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else if(jid.equals("RSSM"))
				{
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf;
				}
				else if((jid.equals("QUAECO"))||(jid.equals("COLEGN")))
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf;	
				else if(jid.equals("JIPH"))// Added by mukesh on 22-09-08
				{
					copyRightLine+=orgCopyright+"King Saud Bin Abdulaziz University for Health Sciences. Published by "+copyRightdbf;
				}
				else if(jid.equals("TRSTMH"))// Added by mukesh on 20-11-08
				{
					//System.out.println("copyRightdbf--444444444---"+copyRightdbf);
					//System.out.println("orgCopyright--444444444---"+orgCopyright);
					copyRightLine+=orgCopyright+"Royal Society of Tropical Medicine and Hygiene. Published by Elsevier Ltd. "+copyRightdbf;
				}
				//added by debottam for RPOR 03-12-09
				else if(jid.equals("RPOR")){
					copyRightLine+="\\copyright~"+year+"~"+copyRightdbf;
				}
				else if(jid.equals("PEDPUE")){
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else if(jid.equals("COMM")){
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else if(jid.equals("JARE"))
					{
						copyRightLine+=copyRightdbf;
					}
				else
				{
					copyRightLine+=orgCopyright+" "+copyRightdbf;
					
				}
			}
		}
		else if (index==3)
		{
			//System.out.println("extractCopyRight 7");
			//Society
			if(!orgCopyright.endsWith("."))
				if(!jid.equals("ENCEP"))
				{
					orgCopyright+=".";
					
				}
			if(!jid.equals("ENCEP"))
			{
				copyRightLine+="\\copyright~"+year+"~";
			
			}
			
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
				{
					copyRightLine+="\\copyright~";

					//copyRightLine+="L`Encéphale, Paris, 2007.";
					copyRightLine+=orgCopyright+", "+year+".";
					//System.out.println("orgCopyright-------3- ` - ' - "+orgCopyright);
				}
				else if(jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")||  jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
				{
					if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="Société nationale française de médecine interne (SNFMI). Publié par Elsevier Masson SAS. Tous droits réservés.";
					}
					else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Publié par Elsevier Masson SAS. Tous droits réservés.";
					}
					else
					copyRightLine+=orgCopyright+" Tous droits réservés.";
				}
				else if (jid.equals("REAURG"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				else if (jid.equals("ANICOM"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+" Tous droits réservés.";
				}
				else if (jid.equals("BONSOI"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				else if (jid.equals("PSFR")|| jid.equals("PRPS"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				else if(jid.equals("ALTER")|| jid.equals("PRATAN")|| jid.equals("JFO")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE") ||jid.equals("NPG")||jid.equals("JTCC")||jid.equals("JBCT"))
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
				}
				else if(jid.equals("RCO"))
				{
					//copyRightLine+=orgCopyright+" et Association Sociétéde Néphrologie. Tous droits réservés.";
					copyRightLine+=orgCopyright+"Tous droits réservés.";
				}
				else if(jid.equals("REVRHU"))//27/11/2008
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+" Tous droits réservés.";
																							  
					}
				else if(jid.equals("CND"))//27/11/2008
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+" Tous droits réservés.";
																							  
					}
				else if(jid.equals("MONRHU"))//11/12/2009
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+" Tous droits réservés.";
				}
				else if(jid.equals("RMR"))//11/12/2009
				{
					copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+" Tous droits réservés.";
				}
				else if(jid.equals("PALEVO"))//11/12/2009
				{
					copyRightLine+=orgCopyright+" Publié par Elsevier Masson SAS. Tous droits réservés.";
				}
				else if(jid.equals("JEUREA"))
				{
					copyRightLine+=orgCopyright+" Publié par Elsevier Masson SAS. Tous droits réservés.";
				}
				else
				{
					//System.out.println("MMMMMMMMMMMMMM "+orgCopyright+"nnnnnnnnnnn "+copyRightdbf);
					copyRightLine+=orgCopyright+" Publi\\'{e} par "+copyRightdbf;
					
				}
				
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				{
					copyRightLine+=orgCopyright+" Todos los derechos reservados.";
				}
				else if(jid.equals("NRL"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("FARMA"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ARBRES"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ARBR"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("APUNTS"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("GACETA"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ANPEDIA"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. en nombre de Asociación Española de Pediatría.";
				}
				else if(jid.equals("OFTAL"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ENDONU"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("SEMREU"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RECOT"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("HIPERT"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("REGG"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ACURO"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ANPEDI"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("CALI"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("FT"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RX"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RIAM"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("CEDE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("DIALIS"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("CARCOR"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("LABCLI"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("PATOL"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RPPNEU"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("RPSM"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("DIAPRE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RIFK"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ANGIO"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("MAXILO"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("IHE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("NEUARG"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("ACUROE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("RPEMD"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("INMUNO"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("FARMAE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
				else if(jid.equals("REMN"))
				{
					copyRightLine+=copyRightdbf+" y "+orgCopyright+" Todos los derechos reservados.";
				}
				else
				{
					copyRightLine+=orgCopyright+" y "+copyRightdbf+" Todos los derechos reservados.";
				}


			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+=orgCopyright+"  Pubblicato da "+copyRightdbf;
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+=orgCopyright+" Publicat per Elsevier España, S.L.U. Tots els drets reservats.";
			}
			else if(articleType.equalsIgnoreCase("PT"))
			{
				if(jid.equals("DIAPRE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("RPPNEU"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("RPPNEN")||jid.equals("PULMOE"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
				else if(jid.equals("RPEMD"))
				{
					copyRightLine+=orgCopyright+" Publicado por Elsevier España, S.L.U. Todos os direitos reservados.";
				}
			}
			else
			{
				if(jid.equals("QUAECO"))
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf;			
				//added by debottam for RPOR 03-12-09
				else if(jid.equals("RPOR"))
					{
						copyRightLine+=copyRightdbf;
					}
				else if(jid.equals("CERI"))
					copyRightLine+="Elsevier Ltd and "+orgCopyright+" "+copyRightdbf;
				else if(jid.equals("PALEVO") || jid.equals("CRAS2A"))
	                copyRightLine += orgCopyright + " Published by Elsevier Masson SAS. All rights reserved.";
				else if(jid.equals("NEUCLI") || jid.equals("GEOBIO") || jid.equals("ANTHRO") || jid.equals("ANNPAL")|| jid.equals("ENCEPH")|| jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
				{
					if(jid.equals("REVMED")&&Integer.parseInt(temp_aid)>=3577)
					{
						copyRightLine+="Société nationale française de médecine interne (SNFMI). Published by Elsevier Masson SAS. All rights reserved.";

					}
					else if(jid.equals("CANRAD")&&Integer.parseInt(temp_aid)>=2481)
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Published by Elsevier Masson SAS. All rights reserved.";
					}
					else
					copyRightLine+=orgCopyright+" All rights reserved.";
				}
				else
				{
					if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						
						copyRightLine += orgCopyright+", "+year+".";;
						//System.out.println("orgCopyright---[`][']----3-"+orgCopyright);
					}
					else if (jid.equals("REAURG"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if (jid.equals("ANICOM"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+"  All rights reserved.";
					}
					else if (jid.equals("BONSOI"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+".  All rights reserved.";
					}
					else if (jid.equals("JJCC")|| jid.equals("JTCC"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if (jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("ALTER")|| jid.equals("PRATAN")|| jid.equals("JFO")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE")||jid.equals("NPG"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}

					else if(jid.equals("RCO"))
					{
						//copyRightLine+=orgCopyright+" and Association Sociétéde Néphrologie All rights reserved.";
						copyRightLine+=orgCopyright+". All rights reserved.";
					}
					else if (jid.equals("JSAMS")|| jid.equals("YBJOM"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf;
					}
					else if(jid.equals("REVRHU"))//27/11/2008
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+" All rights reserved.";
																							  
					}
					else if(jid.equals("CND"))//27/11/2008
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+" All rights reserved.";
																							  
					}
					else if(jid.equals("INHE"))//14/04/2009
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+" All rights reserved.";
																							  
					}
					else if(jid.equals("MONRHU"))//11/12/2009
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+" All rights reserved.";
																							  
					}
					else if(jid.equals("RMR"))//11/12/2009
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+" All rights reserved.";
																							  
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+=copyRightdbf;
					}
					else if(jid.equals("RDF"))
					{
						copyRightLine += orgCopyright+" Production and hosting by "+copyRightdbf;
					}
					else if(jid.equals("REMN"))
					{
						copyRightLine+=copyRightdbf+" and "+orgCopyright+" All rights reserved.";
					}
					else
					{
						copyRightLine += orgCopyright+" Published by "+copyRightdbf;

						
					}
					//copyRightLine += orgCopyright+" Published by "+copyRightdbf;
				}
			}
		}
		else if (index==4)
		{
			//System.out.println("extractCopyRight 7");
			

			//Joint
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
						//System.out.println("orgCopyright-------3- ` - ' - "+orgCopyright);
					}
					else if(jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
					{
						copyRightLine+=orgCopyright+" Tous droits réservés.";
					}
					else if (jid.equals("REAURG"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if (jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if(jid.equals("ALTER")|| jid.equals("PRATAN")|| jid.equals("JFO")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE")||jid.equals("NPG"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if(jid.equals("RCO"))
					{
						copyRightLine+=orgCopyright+" et Association Sociétéde Néphrologie. Tous droits réservés.";
					}
					else if(jid.equals("JRADIO"))
					{
						copyRightLine+="\\copyright~"+year+"~ Elsevier Masson SAS et Éditions françaises de radiologie. Tous droits réservés.";
					}
					else
					{
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+" "+copyRightdbf;
					}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(!orgCopyright.endsWith("."))
				{
					orgCopyright=orgCopyright+".";
				}
				if(jid.equals("JVAC"))
				copyRightLine+=orgCopyright+" Todos los derechos reservados.";
				else if(jid.equals("PSIQ"))
					copyRightLine+="~Publicado por Elsevier España, S.L.U.";
				else if(jid.equals("ENDONU"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Publicado por Elsevier España, S.L.U. en nombre de SEEN.";
				}
				else if(jid.equals("ENFI"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SEEIUC. Todos los derechos reservados.";
				}
				else if(jid.equals("SEMERG"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SEMERGEN. Todos los derechos reservados.";
				}
				else if(jid.equals("ARTERI"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SEA. Todos los derechos reservados.";
				}
				else if(jid.equals("RH"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SERMEF. Todos los derechos reservados.";
				}
				else if(jid.equals("REMN"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SEMNIM. Todos los derechos reservados.";
				}
				else if(jid.equals("REMNGL"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y SEMNIM. Todos los derechos reservados.";
				}
				else if(jid.equals("GASTRO"))
				{
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. y AEEH y AEG. Todos los derechos reservados.";
				}
				else
					copyRightLine+="\\copyright~"+year+"~"+orgCopyright+" Todos los derechos reservados.";
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+=orgCopyright+" Pubblicato da "+copyRightdbf;
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+=orgCopyright+" Tots els drets reservats.";
			}
			else
			{
				if(!orgCopyright.endsWith("."))
					orgCopyright+=".";
				if(jid.equals("CERI"))
					copyRightLine+="\\copyright~"+year+"~"+"Elsevier Ltd and "+orgCopyright+" "+copyRightdbf;			
				else if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright;
						//System.out.println("orgCopyright-------3- ` - ' - "+orgCopyright);
					}
					else if(jid.equals("NEUCLI") || jid.equals("GEOBIO") || jid.equals("ANTHRO") || jid.equals("ANNPAL")|| jid.equals("ENCEPH")|| jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("DIABET")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("SCISPO")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("CANRAD")|| jid.equals("ERAP")|| jid.equals("NEURAD")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
					{
						copyRightLine+=orgCopyright+" All rights reserved.";
					}
					//added by debottam for RPOR 03-12-09
					else if(jid.equals("RPOR")){
						copyRightLine+="\\copyright~"+year+"~"+copyRightdbf;
					}
					else if (jid.equals("REAURG"))
					{
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+" Published by "+copyRightdbf+".  All rights reserved.";
					}
					else if (jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("ALTER")|| jid.equals("PRATAN")|| jid.equals("JFO")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("GCB")||jid.equals("CLINRE")||jid.equals("NPG"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("RCO"))
					{
						copyRightLine+=orgCopyright+" and Association Sociétéde Néphrologie All rights reserved.";
					}
					else if(jid.equals("JRADIO"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier Masson SAS and Éditions françaises de radiologie. All rights reserved.";
					}
					else if(jid.equals("RSSM"))
					{
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+" Published by "+copyRightdbf;
					}
					else if(jid.equals("AD"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and AEDV. All rights reserved.";
					}
					else if(jid.equals("ARTERI"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and SEA. All rights reserved.";
					}
					else if(jid.equals("MEDIN"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and SEMICYUC. All rights reserved.";
					}
					else if(jid.equals("ENFI"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and SEEIUC. All rights reserved.";
					}
					else if(jid.equals("SEMERG"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and SEMERGEN. All rights reserved.";
					}
					else if(jid.equals("REMN"))
					{
						copyRightLine+="\\copyright~"+year+"~Elsevier España, S.L.U. and SEMNIM. All rights reserved.";
					}
					else if(jid.equals("RH"))
					{
						copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. and SERMEF. All rights reserved.";
					}
					else if(jid.equals("REMNGL"))
					{
						copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. and SEMNIM. All rights reserved.";
					}
					else if(jid.equals("GASTRO"))
					{
						copyRightLine+="\\copyright~"+year+"~"+"Elsevier España, S.L.U. and AEEH y AEG. All rights reserved.";
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+=copyRightdbf;
					}
					else if((jid.equals("QUAECO"))||(jid.equals("COLEGN")))
					copyRightLine+=orgCopyright+" Published by "+copyRightdbf;		
					else
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+" "+copyRightdbf;
			}
		}
		else if (index==5)
		{
			//System.out.println("extractCopyRight 8");
			
			//US-GOV
			if(articleType.equalsIgnoreCase("FR"))
			{
				//System.out.println("extractCopyRight 9"+orgCopyright);
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					else if(jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")|| jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("ANDO")|| jid.equals("MEDPAL"))
					{
						copyRightLine+=orgCopyright+" Tous droits réservés.";
					}
					else if(jid.equals("NEURAD"))
					{
						copyRightLine+="Publi\\'{e} par Elsevier Masson SAS.";
					}
					else if(jid.equals("ERAP"))
					{
						copyRightLine+="Publi\\'{e} par Elsevier Masson SAS.";
					}
					else if(jid.equals("CANRAD"))
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Publi\\'{e} par Elsevier Masson SAS. Tous droits réservés";
					}
					else if (jid.equals("SCISPO"))
					{
						copyRightLine+="Publi\\'{e} par Elsevier Masson SAS.";
					}
					else if (jid.equals("REAURG"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if (jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if(jid.equals("ALTER")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("NPG"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf+". Tous droits réservés.";
					}
					else if(jid.equals("JFO"))
					{
						copyRightLine+=orgCopyright+" Publié par "+copyRightdbf;
					}
					else if(jid.equals("PRATAN"))
					{
						copyRightLine+="Publi\\'{e} par Elsevier Masson SAS.";
					}
					else if(jid.equals("RCO"))
					{
						copyRightLine+=orgCopyright+" et Association Sociétéde Néphrologie. Tous droits réservés.";
					}
					else if(jid.equals("ANDO"))
					{
						copyRightLine+="Publi\\'{e} par "+copyRightdbf;
					}

					else
					{
						copyRightLine+="Publi\\'{e} par "+copyRightdbf;
					}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				copyRightLine+=orgCopyright+" Todos los derechos reservados.";
				else if(jid.equals("PSIQ"))
					copyRightLine+="~Publicado por Elsevier España, S.L.U.";
				else if(jid.equals("ENDONU"))
				{
					copyRightLine+="Publicado por Elsevier España, S.L.U. en nombre de SEEN. Todos los derechos reservados.";
				}
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+=orgCopyright+" Pubblicato da "+copyRightdbf;
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+="Publicat per Elsevier España, S.L.U. en nom del Consell Català de l'Esport. Generalitat de Catalunya.";
			}
			else
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					else if (jid.equals("REAURG"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if (jid.equals("PSFR")|| jid.equals("PRPS"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("NEUCLI") || jid.equals("GEOBIO") || jid.equals("ANTHRO") || jid.equals("ANNPAL")|| jid.equals("ENCEPH")|| jid.equals("STOMAX")|| jid.equals("ANNPLA")|| jid.equals("JGYN")|| jid.equals("REVRHU")|| jid.equals("SEXOL")|| jid.equals("REVMED")|| jid.equals("SOCTRA")|| jid.equals("JTS")|| jid.equals("EVOPSY")||  jid.equals("NUTCLI")|| jid.equals("ANCAAN")|| jid.equals("MORPHO")|| jid.equals("RBMRET")|| jid.equals("IRBM")|| jid.equals("IMMBIO")|| jid.equals("JMV")|| jid.equals("JDMV")|| jid.equals("BANM")|| jid.equals("MEDDRO")|| jid.equals("NEUADO")|| jid.equals("NEUCHI")|| jid.equals("MEDMAL")|| jid.equals("MEDPAL"))
					{
						copyRightLine+=orgCopyright+" All rights reserved.";
					}
					else if(jid.equals("NEURAD"))
					{
						copyRightLine+="Published by Elsevier Masson SAS.";
					}
					else if(jid.equals("ERAP"))
					{
						copyRightLine+="Published by Elsevier Masson SAS.";
					}
					else if(jid.equals("ALTER")|| jid.equals("ANNDEROLD")|| jid.equals("FANDER")|| jid.equals("ACVDSP")|| jid.equals("JEMEP")|| jid.equals("MSOM")||jid.equals("NPG"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("JFO"))
					{
						copyRightLine+=orgCopyright+" Published by "+copyRightdbf;
					}
					else if(jid.equals("SCISPO"))
					{
						copyRightLine+="Published by Elsevier Masson SAS.";
					}
					else if(jid.equals("PRATAN"))
					{
						copyRightLine+="Published by Elsevier Masson  SAS.";
					}
					else if(jid.equals("CANRAD"))
					{
						copyRightLine+="Société française de radiothérapie oncologique (SFRO). Published by Elsevier Masson SAS. All rights reserved.";
					}
					else if(jid.equals("RCO"))
					{
						copyRightLine+=orgCopyright+" and Association Sociétéde Néphrologie All rights reserved.";
					}
					else if(jid.equals("ANDO"))
					{
						copyRightLine+="Published by "+copyRightdbf;
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+=copyRightdbf;
					}
					else if(jid.equals("RPOR"))
					{
						copyRightLine+=copyRightdbf;
					}
					else if(jid.equals("PSEP"))
					{
						copyRightLine+="Published by Elsevier B.V. on behalf of The Institution of Chemical Engineers.";
					}
					else if(jid.equals("HM"))
					{
						copyRightLine+="Published by Elsevier Ltd.";
					}
					else if(jid.equals("MSA"))
					{
						copyRightLine+="Published by Elsevier B.V.";
					}
					else if(jid.equals("MSA"))
					{
						copyRightLine+="Published by Elsevier B.V.";
					}
					else if(jid.equals("PCD"))
					{
						copyRightLine+="Published by Elsevier Ltd on behalf of Primary Care Diabetes Europe.";
					}
					else if(jid.equals("JVAC"))
					{
						copyRightLine+="Published by Elsevier Ltd.";
					}
					else if(jid.equals("PEP"))
					{
						copyRightLine+="Published by Elsevier Inc.";
					}
					else if(jid.equals("VIRUS"))
					{
						copyRightLine+="Published by Elsevier B.V.";
					}
					else if(jid.equals("JAAP"))
					{
						copyRightLine+="Published by Elsevier B.V.";
					}
					else if(jid.equals("COMM"))
					{
						copyRightLine+="\\copyright~"+year+" Published by Elsevier Ireland Ltd.";
					}
					else if(jid.equals("JAG"))
					{
						copyRightLine+="\\copyright~"+year+" Published by  Elsevier B.V.";
					}
					else if(jid.equals("JSAMS"))
					{
						copyRightLine+="\\copyright~"+year+" Published by Elsevier Ltd on behalf of Sports Medicine Australia.";
					}
					else
					{
						if(orgCopyright.length()>0)
						{
							//copyRightLine+="\\copyright~"+year+" "+orgCopyright+" Published by "+copyRightdbf;
							copyRightLine+=orgCopyright+" Published by "+copyRightdbf;
						}
						else
						{
							//copyRightLine+="\\copyright~"+year+" Published by "+copyRightdbf;
							copyRightLine+="Published by "+copyRightdbf;
							//System.out.println("copyRightdbf--->>"+copyRightdbf);
						}
					}
					
					/*else if(jid.equals("RETAIL"))
					{
						System.out.println("------33---------- "+orgCopyright);
						System.out.println("------44---------- "+copyRightdbf);
						copyRightLine+="Published by "+copyRightdbf;
					}*/
					
					/*else
				     {
						copyRightLine+="Published by Elsevier GmbH on behalf of Department of Forest Economics, SLU Umeå, Sweden.";
					 }*/
					 //System.out.println("------33---------- "+orgCopyright);
			}

		}
		else if (index==6)
		{
			//System.out.println("extractCopyRight 9");
			
			//Crown
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					 else if(jid.equals("BJMSU"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf;
					}
					else if(jid.equals("PRATAN"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS. Tous droits réservés.";
					}
					else if(jid.equals("GCB")||jid.equals("CLINRE"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Publi\\'{e} par Elsevier Masson SAS. Tous droits réservés.";
					}
					else
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Publi\\'{e} par "+copyRightdbf+". Tous droits réservés.";
					}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				copyRightLine+="Crown Copyright \\copyright~"+year+" "+orgCopyright+" Todos los derechos reservados.";
				else if(jid.equals("PSIQ"))
					copyRightLine+="Crown Copyright \\copyright~"+year+"~Publicado por Elsevier España, S.L.U.";
				else if(jid.equals("ENDONU"))
				{
					copyRightLine+="Crown Copyright \\copyright~"+year+" Publicado por Elsevier España, S.L.U. en nombre de SEEN. Todos los derechos reservados.";
				}
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+="Crown Copyright \\copyright~"+year+" "+orgCopyright+" Tutti i diritti riservati.";
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+="Crown Copyright \\copyright~"+year+" "+orgCopyright+" Tots els drets reservats.";
			}
			else
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					 else if(jid.equals("BJMSU"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier Ltd on behalf of British Association of Urological Surgeons. All rights reserved.";
					}
					else if(jid.equals("QUAECO"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier B.V. on behalf of the Board of Trustees of the University of Illinois. All rights reserved.";

					}
					else if(jid.equals("HM"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier Ltd. All rights reserved.";

					}
					else if(jid.equals("MSA"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier B.V. All rights reserved.";
					}
					else if(jid.equals("PRATAN"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier Masson  SAS. All rights reserved.";

					}
					else if(jid.equals("GCB")||jid.equals("CLINRE"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier Masson  SAS. All rights reserved.";

					}
					else if(jid.equals("PSEP"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by Elsevier B.V. on behalf of The Institution of Chemical Engineers. All rights reserved.";
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+" "+copyRightdbf;
					}
					else if(jid.equals("RPOR"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+" "+copyRightdbf;
					}
					else if(jid.equals("PCD"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~ Elsevier Ltd on behalf of Primary Care Diabetes Europe. All rights reserved.";
					}
					else if(jid.equals("PEP"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~ Published by Elsevier Inc. All rights reserved.";
					}
					else if(jid.equals("VIRUS"))
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~ Published by Elsevier B.V. All rights reserved.";
					}
					else
					{
						copyRightLine+="Crown Copyright \\copyright~"+year+"~Published by "+copyRightdbf;
					}
					
			}

		}
		else if (index==7)
		{
			//System.out.println("extractCopyRight 10");

			//Limited-Transfer
			copyRightLine+="\\copyright~"+year+" ";
			if(!orgCopyright.endsWith(".")&&orgCopyright.length()>0)
				orgCopyright+=".";
			
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
				else if(jid.equals("PRATAN"))
					{
						copyRightLine+=" Elsevier Masson SAS. Tous droits réservés.";;
					}
				else if(jid.equals("PALEVO"))//11/12/2009
				{
					copyRightLine+=orgCopyright+" Publi\\'{e} par~Elsevier Masson SAS. Tous droits réservés.";
				}
				else if(jid.equals("BONSOI"))
				{
					copyRightLine+=orgCopyright+" Publi\\'{e} par~Elsevier Masson SAS. Tous droits réservés.";
				}
				else
				{
					copyRightLine+="~"+orgCopyright+" Publi\\'{e} par~"+copyRightdbf;
				}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				copyRightLine+=orgCopyright+" Todos los derechos reservados.";
				else if(jid.equals("ENDONU"))
				{
					copyRightLine+=" SEES. Publicado por Elsevier España, S.L.U. Todos los derechos reservados.";
				}
			}
			else if(articleType.equalsIgnoreCase("IT"))
			{
				copyRightLine+="~"+orgCopyright+" Tutti i diritti riservati.";
			}
			else if(articleType.equalsIgnoreCase("CA"))
			{
				copyRightLine+="~"+orgCopyright+" Tots els drets reservats.";
			}
			else
			{
				//System.out.println("-------------------------------------");
				if (((orgCopyright.indexOf("Elsevier")!=-1)||(orgCopyright.indexOf("Exceorta Medica")!=-1)||
				   (orgCopyright.indexOf("The Lancat")!=-1))&&(orgCopyright.indexOf(" and ")==-1))
				{
					
					copyRightLine+=orgCopyright;
					if(copyRightLine.endsWith("."))
						copyRightLine+=" All rights reserved.";
					else
						copyRightLine+=". All rights reserved.";

				}
				else if(jid.equals("TRSTMH"))// Added by Ravi on 11-05-09
					{
						//System.out.println("copyRightdbf--444444444---"+copyRightdbf);
						//System.out.println("orgCopyright--444444444---"+orgCopyright);
						copyRightLine+=orgCopyright+" Published by Elsevier Ltd. All rights reserved.";
					}
				else if(jid.equals("PRATAN"))// Added by Ravi on 11-05-09
					{
						//System.out.println("copyRightdbf--444444444---"+copyRightdbf);
						//System.out.println("orgCopyright--444444444---"+orgCopyright);
						copyRightLine+=" Elsevier Masson SAS. All rights reserved.";;
					}
				
				else if(jid.equals("HM"))// Added by Ravi on 11-05-09
					{
						copyRightLine+=" Elsevier Ltd. All rights reserved.";;
					}
				else if(jid.equals("MSA"))
				{
					copyRightLine+=" Elsevier B.V. All rights reserved.";;
				}
				else if(jid.equals("HLC"))
					{
						copyRightLine+="~"+orgCopyright+" Published by~"+copyRightdbf;
					}
				else if (orgCopyright.indexOf(" and ")!=-1)
				{
					copyRightLine+=orgCopyright;
					if(copyRightLine.endsWith("."))
						copyRightLine+=" All rights reserved.";
					else
						copyRightLine+=". All rights reserved.";
					//System.out.println("copyRightdbf--444444444---"+copyRightdbf);
				}
				else if(jid.equals("PSEP"))
					{
						copyRightLine+=" The Institution of Chemical Engineers. Published by Elsevier B.V. All rights reserved.";
					}
				else if(jid.equals("PEP"))
					{
						copyRightLine+=" Elsevier Inc. All rights reserved.";
					}
				else if(jid.equals("VIRUS"))
				{
					copyRightLine+=" Elsevier B.V. All rights reserved.";
				}
				else
				{
					
					if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright;
					}
					else if(jid.equals("JJCC"))
					{
						copyRightLine+="~"+orgCopyright+" Published by~"+copyRightdbf+". All rights reserved.";
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+=copyRightdbf;
						//System.out.println("copyRightLine "+copyRightLine);
					}
					else if(jid.equals("COMM"))
					{
						copyRightLine+=copyRightdbf;
						//System.out.println("copyRightLine "+copyRightLine);
					}
					else if(jid.equals("JAG"))
					{
						copyRightLine+=copyRightdbf;
						//System.out.println("copyRightLine "+copyRightLine);
					}
					else if(jid.equals("PCD"))
					{
						copyRightLine+="~ Primary Care Diabetes Europe. Published by Elsevier Ltd. All rights reserved.";
					}
					else
					{
						
						//copyRightLine+="~"+orgCopyright+" Published by~"+copyRightdbf;
						if(orgCopyright.length()>0)
						{
							copyRightLine+="~"+orgCopyright+" Published by~"+copyRightdbf;
							//System.out.println("extractCopyRight 11"+copyRightLine);
						}
						else
							copyRightLine+="~"+copyRightdbf;
						
					}
				}
			}
		}
		else if (index==8)
		{			//System.out.println("extractCopyRight 11");
			//OTHER
			if(!orgCopyright.endsWith("."))
				orgCopyright+=".";
			if(articleType.equalsIgnoreCase("FR"))
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					else
					{
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+"~Publi\\'{e} par "+copyRightdbf;
					}
			}
			else if(articleType.equalsIgnoreCase("ES"))
			{
				if(jid.equals("JVAC"))
				copyRightLine+=orgCopyright+" Todos los derechos reservados.";
			}
			else
			{
				if(jid.equals("ENCEP"))
					{
						copyRightLine+="\\copyright~";
						//copyRightLine += "L`Encéphale, Paris, 2007.";
						copyRightLine += orgCopyright+", "+year+".";;
					}
					else if(jid.equals("JARE"))
					{
						copyRightLine+="\\copyright~"+year+"~"+copyRightdbf;
					}
					else if(jid.equals("RPOR"))
					{
						copyRightLine+="\\copyright~"+year+"~"+copyRightdbf;
					}
					else
					{
						copyRightLine+="\\copyright~"+year+"~"+orgCopyright+"~Published by "+copyRightdbf;
					}
			}
		}
		else if (index==9)
		{			
			//System.out.println("extractCopyRight 12");
			System.out.println("[TexConversion] ** Copyright Type : No-Transfer");
		}
		type = "";
		copyRightLine+="}";
		if(copyRightLine.indexOf("NONE")!=-1)
		{
			copyRightLine="";
			System.out.println();
			System.out.println("**************************************************************");
			System.out.println("                  NO COPYRIGHT INFORMATION");
			System.out.println("**************************************************************");
		}
		if(index==1 && copyRightLine.indexOf("Techna Group S.r.l.")!=-1 && jid.equals("CERI") && Integer.parseInt(temp_aid)<1996)
		{
			copyRightLine=copyRightLine.replaceAll("Techna Group S.r.l.","Techna S.r.l.");
			
		}
		//System.out.println("extractCopyRight1 2 last");
		if(!(once))
		{
			
		switch (index)
		{
			
			case 1:copyRightLine+="\r\n\\copyrightStatus{001}";
				   break;
			case 2:copyRightLine+="\r\n\\copyrightStatus{002a}";
				   break;
			case 3:copyRightLine+="\r\n\\copyrightStatus{002b}";
				   break;
			case 4:copyRightLine+="\r\n\\copyrightStatus{002c}";
				   break;
			case 5:copyRightLine+="\r\n\\copyrightStatus{003}";
				   break;
			case 6:copyRightLine+="\r\n\\copyrightStatus{004}";
				   break;
			case 7:copyRightLine+="\r\n\\copyrightStatus{005}";
				   break;
			case 8:copyRightLine+="\r\n\\copyrightStatus{006}";
				   break;
			case 9:copyRightLine+="\r\n\\copyrightStatus{007}";
				   break;		
			case 10:copyRightLine+="\r\n\\copyrightStatus{007}";
			   break;		
		}
		once=true;
		}
		//System.out.println("extractCopyRight1 last");
		return copyRightLine;
	}

	public String processItemInfo()throws IOException
	{
		System.out.println("running processItemInfo()...");
		String articleThread = "";
		String copyright	 = "" ;		
		String preprint		 = "";
		String copyrightline = "";
		String tag           = "";		
		String refdoi        = "";
		String refpii        = "";
		String  EnCR = new String("");
		String  FrCR = new String("");
		String  ThirdLangCR = new String("");
		//String  SpCR = new String("");
		boolean refFlag= false;
		//boolean isfirstrefpii= false;
		boolean isfirstrefpii= true; //07-01-2005
		//boolean isfirstrefdoi= false;
		boolean isfirstrefdoi= true; //07-01-2005
		IsCutOffCR=GetCopyRightCutOff();	

		while (!tag.equals("</ITEM-INFO>"))
		{
			//System.out.println("==========>>"+tag);
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
					
				if (tag.equals("<JID>"))
				{
					jid = xmlObj.extractData("</JID>", true).toUpperCase();
				}
				else if (tag.equals("<AID>"))
				{
					aid = xmlObj.extractData("</AID>", true).toUpperCase();
				}
				else if (tag.equals("<CE:ARTICLE-NUMBER>"))
				{
					articleNo = xmlObj.extractData("</CE:ARTICLE-NUMBER>", true);
					isArticleNumberPresent = true;
				}
				else if (tag.equals("<CE:PII>"))
				{
					String temppii = xmlObj.extractData("</CE:PII>", true);
					
					//fin.read();
					String temp_doi="";
					String tempTag=tag;
					if(jid.equalsIgnoreCase("RPPNEN")||jid.equalsIgnoreCase("PULMOE")||jid.equalsIgnoreCase("RCAE")||jid.equalsIgnoreCase("REMNGL")||xmlObj.jid.equals("REMNIE")||jid.equalsIgnoreCase("NRLENG")||jid.equalsIgnoreCase("ARBR")||jid.equalsIgnoreCase("ADENGL")||jid.equalsIgnoreCase("ACUROE")||jid.equalsIgnoreCase("RPPEDE")||jid.equalsIgnoreCase("FARMAE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("REPCE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("RECOTE")||jid.equalsIgnoreCase("RPSMEN")||jid.equalsIgnoreCase("ENDOEN")||jid.equalsIgnoreCase("ENDIEN")||jid.equalsIgnoreCase("OTOENG")||jid.equalsIgnoreCase("MEDINE")||jid.equalsIgnoreCase("BMHIME")||jid.equalsIgnoreCase("RGMXEN")||jid.equalsIgnoreCase("REUMAE")||jid.equalsIgnoreCase("OFTALE")||jid.equalsIgnoreCase("RCENG")||jid.equalsIgnoreCase("ABD")||jid.equalsIgnoreCase("JPED")||jid.equalsIgnoreCase("RPPED")||jid.equalsIgnoreCase("RBOE")||jid.equalsIgnoreCase("ANPEDE")||jid.equalsIgnoreCase("GASTRE")||jid.equalsIgnoreCase("ENFCLE"))
					{
						long filePointer= fin.getFilePointer();
						while((!tempTag.equals("</CE:DOI>")))
						{
							ch= (char)fin.read();
							if (ch=='<')
							{
								tempTag= xmlObj.getTag().toUpperCase();
								
								if (tempTag.equals("<CE:DOI>"))
								{
									//System.out.println("tempTag-->>"+tempTag);
									temp_doi= xmlObj.extractData("</CE:DOI>", true);
									break;
								}
							}
						}
						fin.seek(filePointer);
					}
					//System.out.println("temp_doi===>>"+temp_doi);
					if (refFlag==true)
					{
						if(isfirstrefpii== false)
							refpii+= ", ";
						//Commented on 02-01-2015, condition removed for TEJ Journal
						/*if(jid.equalsIgnoreCase("RPPNEN"))
						{
							if(temp_doi.indexOf(".rppnen.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else*/ 
						if(jid.equalsIgnoreCase("REMNGL"))
						{
							if(temp_doi.indexOf(".remngl.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCAE"))
						{
							if(temp_doi.indexOf(".rcae.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("REMNIE"))
						{
							if(temp_doi.indexOf(".remnie.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("REPCE"))
						{
							if(temp_doi.indexOf(".repce.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RXENG"))
						{
							if(temp_doi.indexOf(".rxeng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RECOTE"))
						{
							if(temp_doi.indexOf(".recote.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RPSMEN"))
						{
							if(temp_doi.indexOf(".rpsmen.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ENDOEN"))
						{
							if(temp_doi.indexOf(".endoen.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ENDIEN"))
						{
							if(temp_doi.indexOf(".endien.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						//Date 12-07-2022 As per Pramod this condition not required
						/*else if(jid.equalsIgnoreCase("NRLENG"))
						{
							if(temp_doi.indexOf(".nrleng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("ARBR"))
						{
							if(temp_doi.indexOf(".arbr.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ADENGL"))
						{
							if(temp_doi.indexOf(".adengl.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("ACUROE"))
						{
							if(temp_doi.indexOf(".acuroe.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("FARMAE"))
						{
							if(temp_doi.indexOf(".farmae.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RXENG"))
						{
							if(temp_doi.indexOf(".rxeng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("OTOENG"))
						{
							if(temp_doi.indexOf(".otoeng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("REUMAE"))
						{
							if(temp_doi.indexOf(".reumae.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("MEDINE"))
						{
							if(temp_doi.indexOf(".medine.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("BMHIME"))
						{
							if(temp_doi.indexOf(".bmhime.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("REDARE"))
						{
							if(temp_doi.indexOf(".redare.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RBRE"))
						{
							if(temp_doi.indexOf(".rbre.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJANES"))
						{
							if(temp_doi.indexOf(".bjanes.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJANE"))
						{
							if(temp_doi.indexOf(".bjane.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJAN"))
						{
							if(temp_doi.indexOf(".bjan.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RGMXEN"))
						{
							if(temp_doi.indexOf(".rgmxen.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("OFTALE"))
						{
							if(temp_doi.indexOf(".oftale.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("CIRENG"))
						{
							if(temp_doi.indexOf(".cireng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCENG"))
						{
							if(temp_doi.indexOf(".rceng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("JPED"))
						{
							if(temp_doi.indexOf(".jped.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ABD"))
						{
							if(temp_doi.indexOf(".abd.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						/* Commented on 12-02-2016, Mail by TeXR&D
						 * else if(jid.equalsIgnoreCase("RPPED"))
						{
							if(temp_doi.indexOf(".rpped.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("RBOE"))
						{
							if(temp_doi.indexOf(".rboe.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ANPEDE"))
						{
							if(temp_doi.indexOf(".anpede.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("SDCAT"))
						{
							if(temp_doi.indexOf(".sdcat.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("SDENG"))
						{
							if(temp_doi.indexOf(".sdeng.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("SD"))
						{
							if(temp_doi.indexOf(".sd.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("MAXILE"))
						{
							if(temp_doi.indexOf(".maxile.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("PSICOE"))
						{
							if(temp_doi.indexOf(".psicod.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("MEDCLE"))
						{
							if(temp_doi.indexOf(".medcle.",0)!=-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("GASTRE"))
						{
//							if(temp_doi.indexOf(".gastro.",0)==-1)
							if(temp_doi.indexOf(".gastrohep.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("CIRCEN"))
						{
							if(temp_doi.indexOf(".circir.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("NEFROE"))
						{
							if(temp_doi.indexOf(".nefroe.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCREUE"))
						{
							if(temp_doi.indexOf(".rcreu.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("ARTERE"))
						{
							if(temp_doi.indexOf(".artere.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("ENFCLE"))
						{
							if(temp_doi.indexOf(".enfcle.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("ENFIE"))
						{
							if(temp_doi.indexOf(".enfie.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("EIMCE"))
						{
							if(temp_doi.indexOf(".eimce.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else if(jid.equalsIgnoreCase("SEDENG"))
						{
							if(temp_doi.indexOf(".sedene.",0)==-1)
							{
								refpii+= temppii;
								isfirstrefpii= false;
							}
						}
						else
						{
							refpii+= temppii;
							isfirstrefpii= false;
						}
						//System.out.println("refpii-->>"+refpii+"ggggg");
						if(refpii.endsWith(", "))
						{
							refpii=refpii.trim();
							refpii=refpii.substring(0,refpii.length()-1);
						}
						//refpii+= temppii;
						//isfirstrefpii= false;
					}
					else
					{
						pii=temppii;
					}
					temppii="";
					
				}
				else if (tag.equals("<CE:DOI>"))
				{
					//Avinandan eclare var String
					//temp
					String temp = xmlObj.extractData("</CE:DOI>", true);
					if (refFlag==true)
					{
						if(isfirstrefdoi== false)
							refdoi+= ", ";
						//System.out.println("--------------JID:"+jid);
						
						//Commented on 02-01-2015, removed TEJ condition.
						/*if(jid.equalsIgnoreCase("RPPNEN"))
						{

							if(temp.indexOf(".rppnen.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
								//System.out.println("2--------------DIO:"+temp);
							}
							
						}
						else*/ 
						if(jid.equalsIgnoreCase("REMNGL"))
						{
							if(temp.indexOf(".remngl.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCAE"))
						{
							if(temp.indexOf(".rcae.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("REMNIE"))
						{
							if(temp.indexOf(".remnie.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("REPCE"))
						{
							if(temp.indexOf(".repce.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RXENG"))
						{
							if(temp.indexOf(".rxeng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ENDOEN"))
						{
							if(temp.indexOf(".endoen.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ENDIEN"))
						{
							if(temp.indexOf(".endien.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RPSMEN"))
						{
							if(temp.indexOf(".rpsmen.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RECOTE"))
						{
							if(temp.indexOf(".recote.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						//Date 12-07-2022 As per Pramod this condition not required
						/*else if(jid.equalsIgnoreCase("NRLENG"))
						{
							if(temp.indexOf(".nrleng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("ARBR"))
						{
							if(temp.indexOf(".arbr.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ADENGL"))
						{
							if(temp.indexOf(".adengl.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("ACUROE"))
						{
							if(temp.indexOf(".acuroe.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("FARMAE"))
						{
							if(temp.indexOf(".farmae.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RXENG"))
						{
							if(temp.indexOf(".rxeng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("OTOENG"))
						{
							if(temp.indexOf(".otoeng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("MEDINE"))
						{
							if(temp.indexOf(".medine.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("BMHIME"))
						{
							if(temp.indexOf(".bmhime.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("REDARE"))
						{
							if(temp.indexOf(".redare.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ANPEDE"))
						{
							if(temp.indexOf(".anpede.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RBRE"))
						{
							if(temp.indexOf(".rbre.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJANES"))
						{
							if(temp.indexOf(".bjanes.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJANE"))
						{
							if(temp.indexOf(".bjane.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("BJAN"))
						{
							if(temp.indexOf(".bjan.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RGMXEN"))
						{
							if(temp.indexOf(".rgmxen.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("REUMAE"))
						{
							if(temp.indexOf(".reumae.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("OFTALE"))
						{
							if(temp.indexOf(".oftale.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("CIRENG"))
						{
							if(temp.indexOf(".cireng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCENG"))
						{
							if(temp.indexOf(".rceng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("JPED"))
						{
							if(temp.indexOf(".jped.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ABD"))
						{
							if(temp.indexOf(".abd.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						/* Commented on 12-02-2016, Mail by TeXR&D
						 * else if(jid.equalsIgnoreCase("RPPED"))
						{
							if(temp.indexOf(".rpped.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("RBOE"))
						{
							if(temp.indexOf(".rboe.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("SDCAT"))
						{
							if(temp.indexOf(".sdcat.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("SDENG"))
						{
							if(temp.indexOf(".sdeng.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("SD"))
						{
							if(temp.indexOf(".sd.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("MAXILE"))
						{
							if(temp.indexOf(".maxile.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("PSICOE"))
						{
							if(temp.indexOf(".psicod.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("MEDCLE"))
						{
							if(temp.indexOf(".medcle.",0)!=-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("GASTRE"))
						{
//							if(temp.indexOf(".gastro.",0)==-1)//29-06-2017
							if(temp.indexOf(".gastrohep.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("CIRCEN"))
						{
							if(temp.indexOf(".circir.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("NEFROE"))
						{
							if(temp.indexOf(".nefroe.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("RCREUE"))
						{
							if(temp.indexOf(".rcreu.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("ARTERE"))
						{
							if(temp.indexOf(".artere.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						/*else if(jid.equalsIgnoreCase("ENFCLE"))
						{
							if(temp.indexOf(".enfcle.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}*/
						else if(jid.equalsIgnoreCase("ENFIE"))
						{
							if(temp.indexOf(".enfie.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("EIMCE"))
						{
							if(temp.indexOf(".eimce.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else if(jid.equalsIgnoreCase("SEDENG"))
						{
							if(temp.indexOf(".sedene.",0)==-1)
							{
								refdoi+= temp;
								isfirstrefdoi= false;
							}
						}
						else
						{
							refdoi+= temp;
							isfirstrefdoi= false;
						}
					}
					else
					{
						doi=temp;
					}
					if(refdoi.endsWith(", "))
					{
						refdoi=refdoi.trim();
						refdoi=refdoi.substring(0,refdoi.length()-1);
					}
				}
				else if (tag.startsWith("<CE:COPYRIGHT "))
				{
					long fp1=fin.getFilePointer();
					String altlang="";
					String altlangtwo="";
					String statTag1=tag;
					//System.out.println("statTag1 in upper while  ==>>"+statTag1);
					while((!statTag1.equals("</HEAD>")))
					{
						//System.out.println("statTag1 in side while  ==>>"+statTag1);
						//System.in.read();
							ch= (char)fin.read();	
							if (ch=='<')
							{
								statTag1= xmlObj.getTag().toUpperCase();
								if(statTag1.startsWith("<CE:ABSTRACT "))
								{
									if (statTag1.indexOf("XML:LANG=\"")>0)
									{
										int spos=0;
										int epos=0;
										spos=statTag1.indexOf("XML:LANG=\"",0);
										if(spos!=-1)
										{
											epos=statTag1.indexOf("\">",0);
											if(epos!=-1)
											{
												//altlang=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
												//altlangtwo added on 14-08-2013
												if(altlang.equals(""))
												{
													altlang=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
												}
												else
												{
													altlangtwo=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
												}
												//System.out.println("altlang=====> "+altlang);
												//System.out.println("altlangtwo=====> "+altlangtwo);
											}
										}
									}
								}
								else
								{
									if(statTag1.startsWith("<CE:ALT-TITLE "))
									{
										if (statTag1.indexOf("XML:LANG=\"")>0)
										{
											int spos=0;
											int epos=0;
											spos=statTag1.indexOf("XML:LANG=\"",0);
											if(spos!=-1)
											{
												epos=statTag1.indexOf("\">",0);
												if(epos!=-1)
												{
													//altlang=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
													//altlangtwo added on 14-08-2013
													if(altlang.equals(""))
													{
														altlang=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
													}
													else

													{
														altlangtwo=statTag1.substring(spos+"XML:LANG=\"".length(),epos);
													}
													if(jid.equals("JPEDP") || jid.equals("ABDP") || jid.equals("RPPED") || jid.equals("RMTA") || jid.equals("BJORLP"))
													{
														if(altlang.equalsIgnoreCase(articleType))
														{
															altlang=altlangtwo;
														}
													}
													/*if(jid.equals("RPPED"))
													{
														if(altlang.equalsIgnoreCase(articleType))
														{
															altlang=altlangtwo;
														}
													}*/
													//System.out.println("altlang=====> "+altlang);
												}
											}
										}
									}
								}
							}

							if(statTag1.equals("</SIMPLE-HEAD>"))
								break;
							if(statTag1.equals("</BOOK-REVIEW-HEAD>"))
								break;
					}


				fin.seek(fp1);
					int ind= tag.indexOf("TYPE=\"")+6;
					String copyrType = xmlObj.getAttributeValue(tag, "TYPE");
					//System.out.println("1 ===================================================>");
					copyrightValinXML=copyrType;
					ind= tag.indexOf("YEAR=\"")+6;
					String copyrYear = xmlObj.getAttributeValue(tag, "YEAR");
					//added by avinandan on 10-8-4
					
					//if((copyrType.equals("OTHER"))&&(tag.endsWith("/>")))
					System.out.println("copyrType ::> "+copyrType);
					if((copyrType.equals("OTHER")) || (copyrType.equals("FREE-OF-COPYRIGHT")) || (copyrType.equals("NO-TRANSFER")))
					{
						System.out.println("FREE-OF-COPYRIGHT CASE============>"+copyrType);
//						if(tag.endsWith("/>")){
						if(tag.endsWith("/>") || copyrType.equals("FREE-OF-COPYRIGHT")){//Updated on 01-12-2016 for open/close tag of FREE-OF-COPYRIGHT  
							if(jid.equals("YICCN") && xmlObj.pit.equalsIgnoreCase("MIS"))
								copyrightline="\\copyrightline{\\copyright~"+copyrYear+"~Australian Critical Care. Reproduced with permission.}";
							else
								copyrightline="\\copyrightline{}";
							if(copyrType.equals("NO-TRANSFER"))
								copyrightline+="\r\n\\copyrightStatus{007}";
							else
								copyrightline+="\r\n\\copyrightStatus{000}";

							copyrightValinXML="NO COPYRIGHTSLINE";
							String copyline = getCopyright(copyrType, copyrYear, tag);
							if(copyline.equalsIgnoreCase("000"))
							{
								if(articleType.equalsIgnoreCase("FR"))
								{
									copyrightline+="\r\n\\ENGLISHcopyrightline{}";
									copyrightline+="\r\n\\FRENCHcopyrightline{}";
								}
								if(articleType.equalsIgnoreCase("IT"))
								{
									copyrightline+="\r\n\\ENGLISHcopyrightline{}";
									copyrightline+="\r\n\\ITALIANcopyrightline{}";
								}
								if(articleType.equalsIgnoreCase("PT"))
								{
									copyrightline+="\r\n\\ENGLISHcopyrightline{}";
									copyrightline+="\r\n\\PORTUGUESEcopyrightline{}";
								}
							}
						}
						else
						{
							/*
							copyrightline="\\copyrightline{}";
							copyrightline+="\r\n\\copyrightStatus{000}";
							*/
							copyrightline = getCopyright(copyrType, copyrYear, tag);
							String tempLang=articleType;//added by Vivek on 16-02-13
							System.out.println("xmlObj.check_MassonJid :: "+xmlObj.check_MassonJid);
							System.out.println("tempLang :: "+tempLang);
							System.out.println("altlang :: "+altlang);
							
							if(xmlObj.check_MassonJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("fr"))
							{
								System.out.println("check_MassonJid+fr");
								EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
								System.out.println("1 Language changed from "+articleType+" to FR.");
								articleType="FR";
								FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\FRENCHcopyrightline");
							}
							else if(xmlObj.check_MassonJid==true && tempLang.equalsIgnoreCase("fr")&& altlang.equalsIgnoreCase("en"))
							{
								System.out.println("check_MassonJid+fr");
								FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\FRENCHcopyrightline");
								System.out.println("1 Language changed from "+articleType+" to EN");
								articleType="EN";
								EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							}
							else if(xmlObj.check_MassonJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase(""))
							{
								System.out.println("check_MassonJid+en");
								EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
								if(!jid.equalsIgnoreCase("STLM") && !jid.equalsIgnoreCase("ANNDER") && !jid.equalsIgnoreCase("JOCLIM") && !jid.equalsIgnoreCase("DEMAN") && !jid.equalsIgnoreCase("SODA") && !jid.equalsIgnoreCase("LIVER")){
									System.out.println("2 Language changed from "+articleType+" to FR..");
									articleType="FR";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\FRENCHcopyrightline");
								}
							}
							else if(xmlObj.check_SpanishJid==true)
							{
								System.out.println("tempLang=====>"+tempLang);
								System.out.println("altlang======>"+altlang);
								System.out.println("altlangtwo===>"+altlangtwo);
								if(tempLang.equalsIgnoreCase("pt")&& altlang.equalsIgnoreCase("en"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
									articleType="EN";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									articleType="PT";
								}
								else if(tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("pt"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									articleType="PT";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
									articleType="EN";
								}
								else if(tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("es"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									articleType="ES";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
									articleType="EN";
								}
								else if(tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase("en"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
									articleType="EN";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									articleType="ES";
								}
								else if(tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase("pt"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
									articleType="PT";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
									articleType="ES";
								}
								else if(tempLang.equalsIgnoreCase("pt")&& altlang.equalsIgnoreCase("es"))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
									articleType="ES";
									FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
									articleType="PT";
								}
								else if(tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase(""))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									System.out.println("EnCR :: "+EnCR);
								}
								else if(tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase(""))
								{
									EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
								}
								if(!altlangtwo.equals("") && !altlang.equalsIgnoreCase(altlangtwo))
								{
									if(altlangtwo.equals("EN"))
									{
										articleType="EN";
										ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
									}
									if(altlangtwo.equals("PT"))
									{
										articleType="PT";
										ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
									}
									if(altlangtwo.equals("ES"))
									{
										articleType="ES";
										ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
									}
								}
							}
						}
						//System.out.println("copyrightline : "+copyrightline);
					}
					else
					{
						//String ccltext=getCCCopyright(articleType);//28-01-2014
						copyrightline = getCopyright(copyrType, copyrYear, tag);//28-01-2014						
						//copyrightline = getCopyright(copyrType, copyrYear, tag)+"\\CCLcopyright{"+ccltext+"}";//28-01-2014
						String tempLang=articleType;//added by ravi on 01-11-08 request by TPMS
						System.out.println("articleType "+tempLang);
						System.out.println("altlang "+altlang);
						
						if(xmlObj.check_SpanishJid==true)
						{
							if(!altlangtwo.equals(""))
							{
								if(altlangtwo.equals("EN"))
								{
									articleType="EN";
									ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
								}
								if(altlangtwo.equals("PT"))
								{
									articleType="PT";
									ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
								}
								if(altlangtwo.equals("ES"))
								{
									articleType="ES";
									ThirdLangCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
								}
							}
						}

						if(xmlObj.check_MassonJid==true)
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							System.out.println("3 Language changed from "+articleType+" to FR...");
							articleType="FR";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\FRENCHcopyrightline");
						}
						
						
						if(xmlObj.check_ItalianJid==true)
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="IT";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ITALIANcopyrightline");
						}

						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase("en"))
						{
							articleType="ES";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase("pt"))
						{
							articleType="ES";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							articleType="PT";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							//System.out.println("11copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("pt"))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="PT";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("fr"))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							System.out.println("4 Language changed from "+articleType+" to FR....");
							articleType="FR";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\FRENCHcopyrightline");
							//System.out.println("22copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("es"))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="ES";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
						}
						if(xmlObj.check_PortugueseJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("pt"))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="PT";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_PortugueseJid==true && tempLang.equalsIgnoreCase("pt")&& altlang.equalsIgnoreCase("es"))
						{
							articleType="PT";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							articleType="ES";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_PortugueseJid==true && tempLang.equalsIgnoreCase("pt")&& altlang.equalsIgnoreCase("en"))
						{
							articleType="PT";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_PortugueseJid==true && tempLang.equalsIgnoreCase("pt")&& altlang.equalsIgnoreCase(""))
						{
							articleType="PT";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase(""))
						{
							articleType="ES";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_PortugueseJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase(""))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="PT";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\PORTUGUESEcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_SpanishJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase(""))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="ES";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						//***************************************[12-08-2011]********************************************//
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("ca")&& altlang.equalsIgnoreCase("en"))
						{
							articleType="CA";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\CATALANcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("en")&& altlang.equalsIgnoreCase("ca"))
						{
							articleType="EN";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							articleType="CA";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\CATALANcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("ca")&& altlang.equalsIgnoreCase("es"))
						{
							articleType="CA";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\CATALANcopyrightline");
							articleType="ES";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase("ca"))
						{
							articleType="ES";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							articleType="CA";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\CATALANcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("ca")&& altlang.equalsIgnoreCase(""))
						{
							articleType="CA";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\CATALANcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						if(xmlObj.check_CatalanJid==true && tempLang.equalsIgnoreCase("es")&& altlang.equalsIgnoreCase(""))
						{
							articleType="ES";
							EnCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\SPANISHcopyrightline");
							articleType="EN";
							FrCR=getCopyright(copyrType, copyrYear, new String(tag)).replaceFirst("\\\\copyrightline","\\\\ENGLISHcopyrightline");
							//System.out.println("copyrightline-IT--"+FrCR);
						}
						//****************************************[12-08-2011]*******************************************//
						//System.out.println("copyrightline-IT--"+FrCR);
						articleType=tempLang;
					}

					//end mark
					//commented by avinandan on
					//10-8-04
					//copyrightline= getCopyright(copyrType, copyrYear, tag);
					//end mark
				
				}
				else if (tag.startsWith("<CE:PREPRINT"))//18-06-2010
				{
					//No out put required in TeX pegination
					//System.out.println("uuuuuuuuuuuuuuu");
				}
				else if (tag.equals("<CE:ARTICLE-THREAD>"))
				{
					articleThread= xmlObj.extractData("</CE:ARTICLE-THREAD", true);
				}
				else if (tag.equals("<CE:REFERS-TO-DOCUMENT>"))
				{
					refFlag= true;
				}
				else if (tag.startsWith("<CE:REFERS-TO-DOCUMENT "))//28-08-2012 JADTD520 Updation
				{
					refFlag= true;
				}
				/*else if (tag.startsWith("<CE:DOCTOPIC "))
				{
					String roleAtt= xmlObj.getAttributeValue(tag, "ROLE").toLowerCase();
					doctopics= xmlObj.extractData("</CE:DOCTOPIC>", true);
					//System.out.println("------------doctopics : "+doctopics);
					if(roleAtt.equals("cme"))
					{
						doctopics="";
					}
					
					//System.out.println("------------roleAtt : "+roleAtt);
				}*/
				else if (tag.equals("<CE:DOCTOPICS>"))
				{
					String roleAtt="";
					while (!tag.equals("</CE:DOCTOPICS>"))
					{
						ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							//System.out.println("tag "+tag);
							if (tag.startsWith("<CE:DOCTOPIC "))
							{
								roleAtt= xmlObj.getAttributeValue(tag, "ROLE").toLowerCase();
								doctopics= xmlObj.extractData("</CE:DOCTOPIC>", true);
								//System.out.println("------------doctopics : "+doctopics);
								if(roleAtt.equals("cme"))
								{
									doctopics="";
								}
							}
							else if (tag.equals("<CE:DOCTOPIC>"))
							{
								
								doctopics= xmlObj.extractData("</CE:DOCTOPIC>", true);
								
							}
							{
							}
							//String roleAtt= xmlObj.getAttributeValue(tag, "ROLE").toLowerCase();

						}
					}
					
					/*String roleAtt= xmlObj.getAttributeValue(tag, "ROLE").toLowerCase();
					doctopics= xmlObj.extractData("</CE:DOCTOPICS>", true);
					if(doctopics.equals("CME"))
					{
						doctopics="";
					}*/
					//System.out.println("------------roleAtt : "+roleAtt);
				}
				
				else if (tag.equals("<CE:PREPRINT>"))
				{
					preprint= xmlObj.extractData("</CE:PREPRINT>", true);
				}
				
				else if (tag.equals("</ITEM-INFO>"))
				{
					
					break;
				}
			}
			
		}
		
		//*******************************************************************
		//**************************************
		XT chekModelDAndModelEStatus=new XT();
		checkModelDModelE=chekModelDAndModelEStatus.chekJidAid(jid.trim().toUpperCase(), aid.trim().toUpperCase());
		//Added on 25-09-2020
		if(chekModelDAndModelEStatus.isPPEworkflow(jid,aid)){
			ppe = "NewLayout,";
			System.out.println("ppe :: "+ ppe);
		}
			
//loadGulliverModel();//16/02/2008
//String Ivee="";
//boolean check_GulliverJid=false;
//Ivee=sb.toString();
String cutOffAid="";
XMLObjects xj =new XMLObjects(jid.trim().toUpperCase(),aid.trim().toUpperCase(),serverstatus);
xj.makeGulliverJidList();

//*********************[19/03/2009]**************************
//xj.TableGulliverJidCutoffList();
//System.out.println(jid+"1 TablecutOffAid : "+xj.GuliverCutOff+ "Guliver_aid : "+aid);

//System.out.println("tt_aid=====> "+AD);
		String PalevocutOffAid="";
		if(xj.GuliverCutOff.get(jid)!=null)
		{
			PalevocutOffAid=xj.GuliverCutOff.get(jid).toString();
			String tt_aid=aid;

			if(tt_aid.indexOf(".")!=-1)
			{
				tt_aid=tt_aid.substring(0,tt_aid.indexOf("."));
				//System.out.println("tt_aid=====> "+tt_aid);
			}
			
			//System.out.println("cutOffAid : "+tt_aid+ "Guliver_aid : ");
			try{
				if(Integer.parseInt(PalevocutOffAid.trim())<= Integer.parseInt(tt_aid)){
					//System.out.println("1 TablecutOffAid : Guliver_aid : "+tt_aid);
					isTableGulliverCutOff=true;
				
				}
				else
				{
					isTableGulliverCutOff=false;
				}
			}catch(Exception e)
			{
				{
					System.out.println("[ERROR] Number format Exception "+e.toString());
					//return false;
				}
			}

			//System.out.println("1 isTableGulliverCutOff : "+isTableGulliverCutOff);
		}
	


//***********************[END 19/03/2009]*************************
		//******************************************************************
		/**
		* 16/02/2008
		*/
		//Guli_Table
		if(Guli_Table.containsKey(jid.toUpperCase()))
		{
			String cutOff=(String)Guli_Table.get(jid);
			String duckaid=aid;
			if(duckaid.indexOf(".")!=-1)
				duckaid=duckaid.substring(0,duckaid.indexOf("."));
				
			if(Integer.parseInt(duckaid.trim()) >= Integer.parseInt(cutOff.trim()))
			{
				modelFilePath = databasePath + "GulliverModels.dbf";
			}
			else if(plusTable.containsKey(jid.toUpperCase()))
			{
				cutOff=(String)plusTable.get(jid);
				duckaid=aid;
				if(duckaid.indexOf(".")!=-1)
					duckaid=duckaid.substring(0,duckaid.indexOf("."));
					
				if(Integer.parseInt(duckaid) >= Integer.parseInt(cutOff))
				{
					//14/04/2008
					modelFilePath = databasePath + "ModelPlus.dbf";//original
					//modelFilePath = databasePath + "ModelPlus1.dbf";//temp
				}
			}
		}//16/02/2008
		else if(plusTable.containsKey(jid.toUpperCase()))
		{
			String cutOff=(String)plusTable.get(jid);
			String duckaid=aid;
			if(duckaid.indexOf(".")!=-1)
				duckaid=duckaid.substring(0,duckaid.indexOf("."));
				
			if(Integer.parseInt(duckaid) >= Integer.parseInt(cutOff))
			{
				//14/04/2008
				modelFilePath = databasePath + "ModelPlus.dbf";//original
				//modelFilePath = databasePath + "ModelPlus1.dbf";//temp
			}
		}
		
		 getStyles();
		xmlObj.bibStyle=bibStyle;


		StringBuffer articleHead= new StringBuffer();
		//StringBuffer articleHead_Compact= new StringBuffer();
		String vipjid=""; 


		File vip=new File(databasePath + "vip.dbf");
		Vector viplist=new Vector();
		RandomAccessFile vipdbf = new RandomAccessFile(vip,"r");
   		while((vipjid = vipdbf.readLine()) != null)
   		{
			viplist.add(vipjid);
    	}
    	
		vipdbf.close();
		
		
       /*
		* Added By Arvind [25_04_2007]
		* Change Request By: Vivek
		* Change Point : 1.Please use new package in tex file after usepackage{NewTwoHyphenCont} usepackage{FigTop}
		*				 2.No need of onecolumn in documentclass option globally only specific journals required 
		*				   onecolumn option. OneColumn journals list: JAMM JEBO EDUREV ACN AIP TSC CLIPOL EARCHI 
		*				   GEOD INFBEH MATBEH MATCOM PEVA PUBREL WAMOT PRO 
		*				 3.Stage="S100-draft" and Stage="S200-draft" Stage="S300-Web draft" should be used in
		*				   documentclass option. 
		*/	

		boolean is_col_jou=false;
		is_col_jou=XT.OneCol_Journal.contains(jid.trim());
		//System.out.println("is_col_jou--->"+is_col_jou+"xmlObj.pit==> "+check_GulliverJid_For_NewMathSize);
		//boolean isstage_300=false;
		//String isstage="";
		if(stage.equalsIgnoreCase("S300"))
		{
			XT.web="-Web";
		}
//check_GulliverJid_For_NewMathSize
		if(jid.equalsIgnoreCase("JHE")&&check_GulliverJid_For_NewMathSize==true)//11/02/2009
		{
			is_col_jou=false;
		}
		if(is_col_jou) 	
		{
			if((thumbnailJid.contains(xmlObj.pit))||(isFMSPC.equalsIgnoreCase("Yes")))
			{
				articleHead.append("\\documentclass["+ppe+"colorlinks=true,ignfigs,auto,Thumbnail,onecolumn,"+"Stage=\""+stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+FMS+"}\r\n");
				
			}
			else
			{
				articleHead.append("\\documentclass["+ppe+"colorlinks=true,ignfigs,auto,onecolumn,"+"Stage=\""+stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+FMS+"}\r\n");
			
			}
		
		}
		else
		{
			if(thumbnailJid.contains(xmlObj.pit))
			//	articleHead.append("\\documentclass[colorlinks=true,ignfigs,auto,Thumbnail,onecolumn]{XT-MOD"+modelStyle+"}\r\n");
				articleHead.append("\\documentclass["+ppe+"colorlinks=true,ignfigs,auto,Thumbnail,"+"Stage=\""+stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+FMS+"}\r\n");
			
			else if(isFMSPC.equalsIgnoreCase("Yes"))
			//	articleHead.append("\\documentclass[colorlinks=true,ignfigs,auto,Thumbnail,onecolumn]{XT-MOD"+modelStyle+"FMS}\r\n");
				articleHead.append("\\documentclass["+ppe+"colorlinks=true,ignfigs,auto,Thumbnail,"+"Stage=\""+stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+"FMS}\r\n");
	
			else
			//	articleHead.append("\\documentclass[colorlinks=true,ignfigs,auto,onecolumn]{XT-MOD"+modelStyle+"}\r\n");
		  		articleHead.append("\\documentclass["+ppe+"colorlinks=true,ignfigs,auto,"+"Stage=\""+stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+FMS+"}\r\n");
        }

		try {
			if(isLemansJid(jid,aid)){
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "Lemans,");
			}
		} catch (Exception e) {
			System.out.println("Not able to check cutoff from "+databasePath+"\\LemonsJID.dbf");
			e.printStackTrace();
			System.exit(0);
		}
		
		try {
			if(isTejJid(jid,aid)){
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "TeJJournal,");
			}
		} catch (Exception e) {
			System.out.println("Not able to check cutoff from "+databasePath+"\\TeJJournal.dbf");
			e.printStackTrace();
			System.exit(0);
		}
		
		
		
		///////////////////////////////////////////[25-04-2007]////////////////////////////////////////////
		//if(stampJid.contains(xmlObj.pit))//old
		/**
		* Date : [04/10/2007]
		* Modify By: Ravi
		* Cnange Point : STAMP word not add with journal REVRHU
		* Request By : TPMS.
		*/
		//System.out.println("articleHead : "+articleHead);
		//System.in.read();
		if(stampJid.contains(xmlObj.pit)&& !stampJid_Remove.contains(jid))
		{	
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "STAMP,");
		}
		/*
		if(stampJid.contains(xmlObj.pit)&& !jid.equalsIgnoreCase("REVRHU"))
		{	
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "STAMP,");
		}*/

		if(jid.equalsIgnoreCase("ADENGL")||jid.equalsIgnoreCase("OTOENG")||jid.equalsIgnoreCase("ARBR")||jid.equalsIgnoreCase("FARMAE")|jid.equalsIgnoreCase("REUMAE"))//08-09-2011
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "AUComma,");


		if(isTableGulliverCutOff==false){//06/11/2009
		//System.out.println("-------------------------------------------------------------"+isTableGulliverCutOff);
		if(jid.equalsIgnoreCase("PALEVO") && xmlObj.pit.equalsIgnoreCase("EDI"))
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "EDITORIAL,");
		}
		if(jid.equalsIgnoreCase("NEUCLI") && xmlObj.pit.equalsIgnoreCase("BRV"))
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "NODOI,");
		//Added By Ravi [20/11/2006 Vivek change for NODOI]
		if((jid.equalsIgnoreCase("REVMIC") ) && (( xmlObj.pit.equalsIgnoreCase("BRV")) || ( xmlObj.pit.equalsIgnoreCase("CNF")) ))
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "NODOI,");
		if(jid.equalsIgnoreCase("ANNPAL") && xmlObj.pit.equalsIgnoreCase("EDI"))
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "NODOI,");

/*		
//************************************Marke tag added *********[12/09/2007]**************
System.out.println("HeadGroup.marker_check_first : "+HeadGroup.marker_check_first);
if(HeadGroup.marker_check_first==true)
{
	articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "Marker,");
	for(int i=0; i< HeadGroup.marker_name.size();i++)
	{
		if(i>1)
		{
			articleHead.append("\\OtherMarkerlogo{"+HeadGroup.marker_name.get(i).toString()+"}\r\n");
		}
		else
		{
			articleHead.append("\\Markerlogo{"+HeadGroup.marker_name.get(i).toString()+"}\r\n");
		}

	}
	HeadGroup.marker_name.clear();

	HeadGroup.marker_check_first=false;
}
//*****************************************************************************************

*/
		//articleHead.append("\\usepackage{HyphenCont}\r\n");//old [18/11/2006]
		if(modelStyle.startsWith("-"))
		{
			int in=0;
			int out=0;
			in=articleHead.indexOf("-MOD",0);
			if(in !=-1)
			{
				out=articleHead.indexOf("-",in+"-MOD".length());
				if(out !=-1)
				{
					articleHead.delete(in,out);
				}
			}
			
		}
		//if(!modelStyle.startsWith("-MODD"))
		articleHead.append("\\usepackage{NewTwoHyphenCont}\r\n"); //Added By Ravi [18/11/2006 Change By Vivek]
		/*
		* Added By Arvind [25_04_2007]
		* Change Request By: Vivek
		* Change Point : 1.Please use new package in tex file after usepackage{NewTwoHyphenCont} usepackage{FigTop}
		*				 2.No need of onecolumn in documentclass option globally only specific journals required 
		*				   onecolumn option. OneColumn journals list: JAMM JEBO EDUREV ACN AIP TSC CLIPOL EARCHI 
		*				   GEOD INFBEH MATBEH MATCOM PEVA PUBREL WAMOT PRO 
		*				 3.Stage="S100-draft" and Stage="S200-draft" Stage="S300-Web draft" should be used in
		*				   documentclass option. 
		*/
		articleHead.append("\\usepackage{FigTop}\r\n"); //Added By ARvind 25-04-07
		System.out.println("checkModelDModelE==========>"+checkModelDModelE);
		if(checkModelDModelE)
		{
			if(xmlObj.pit.equalsIgnoreCase("COR")||xmlObj.pit.equalsIgnoreCase("CNF"))
			{
				articleHead.append("\\usepackage{COR_CNF}\r\n");
			}
			if(xmlObj.pit.equalsIgnoreCase("CAL"))
			{
				articleHead.append("\\usepackage{CAL_MODD}\r\n");
			}
			if(xmlObj.pit.equalsIgnoreCase("EXM"))
			{
				articleHead.append("\\usepackage{EXM_NEW}\r\n");
			}
			if(xmlObj.pit.equalsIgnoreCase("LIT"))
			{
				articleHead.append("\\usepackage{LIT_MODD_new}\r\n");
			}
			
		}
		
		//*************************************************
		/**
		* Date : 01/11/2007
		* Modify By: Ravi
		* Change Point: New TombStone PIT (according art502.dtd) has been added
		* Change Request By : TPMS
		*/
		//System.out.println("----->>"+XT.S250ABP.get("stage").toString());
		//***************[09-04-2010]*****************
		//New Changes for S250 ABP
		//System.out.println("1111stage "+stage);
		
		if((stage.equalsIgnoreCase("S100")||stage.equalsIgnoreCase("S200")||stage.equalsIgnoreCase("S250")||stage.equalsIgnoreCase("S250RESUPPLY"))&&(( xmlObj.pit.equalsIgnoreCase("DUP"))||( xmlObj.pit.equalsIgnoreCase("RET"))||( xmlObj.pit.equalsIgnoreCase("REM"))))
		{
			/**
			* Date : 05/06/2008
			* Modify By: Ravi
			* Change Point:if modlestyle is MODDFrench or MODEFrench then use \\usepackage{TombstoneD}
			* Change Request By : TPMS
			*/
			//articleHead.append("\\usepackage{Tombstone}\r\n");//old
			//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
			if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
			{
				articleHead.append("\\usepackage{TombstoneD}\r\n");
			}
			else
			{
				articleHead.append("\\usepackage{Tombstone}\r\n");
			}
		}
		else if((stage.equalsIgnoreCase("S300"))&&(( xmlObj.pit.equalsIgnoreCase("REM"))))
		{
			/**
			* Date : 05/06/2008
			* Modify By: Ravi
			* Change Point:if modlestyle is MODDFrench or MODEFrench then use \\usepackage{TombstoneD}
			* Change Request By : TPMS
			*/
			//articleHead.append("\\usepackage{Tombstone}\r\n");//old
			//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
			if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
			{
				articleHead.append("\\usepackage{TombstoneD}\r\n");
			}
			else
			{
				articleHead.append("\\usepackage{Tombstone}\r\n");
			}
		}

		/****************/
		/**
		*Date : 06-08-2011
		*Change Point : New SD Logo will use after a cutoff aid
		*Change Request By: TPMS
		*Modify By : Ravi
		*/
		String temp_aid=aid;//check for duckling item.

		if(temp_aid.indexOf(".",0)!=-1)
		{
			temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
		}
		if(Get_SD_LOGO_CutOff(jid,temp_aid))
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "SDLogo,");
		}
		//System.out.println("@@@@@@@@@@@@@@@@@@@@@  "+NewDOI(jid, temp_aid));
		if(NewDOI(jid, temp_aid))//22-03-2012
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "NewDOI,");
		}
		if(NewDOIChange(jid,temp_aid)){//20-09-2017
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "NEWDOIChange,");
		}
		if(CClicense)//28-01-2014
		{	
			if(CClicenseDummy)
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "CCline,");
			else
				articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "CCline,CClinePrint,");
		}
		ReadForEextra rof= new ReadForEextra(jid.toUpperCase(),aid,stage);
		if(rof.readOrderFileForItemGroup() && rof.itemGroupValue.equalsIgnoreCase("IG000005") && jid.equalsIgnoreCase("PISC"))//10-09-2015//Jid added on 21-11-2015
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "ItemGroup,");
		}
		if(rof.readOrderFileForItemGroup() && rof.itemGroupValue.equalsIgnoreCase("IG000006") && jid.equalsIgnoreCase("PISC"))//21-11-2015
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "ItemGroupSix,");
		}
		/****************/


		//*************************************************
		/**
		*Added [22/11/2007]
		*Added By Ravi
		* Change Point : Usepackage command in case of specific PIT.
		* Change Request By : TPMS
		*/
		//System.out.println("88888888888 modelStyle "+modelStyle);
		//if(( xmlObj.pit.equalsIgnoreCase("CAL"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015
		if(( xmlObj.pit.equalsIgnoreCase("CAL"))&&((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))			
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "CAL,nopicture,");
			articleHead.append("\\usepackage{CAL}\r\n");
		}
		//else if((xmlObj.pit.equalsIgnoreCase("EXM"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015
		else if((xmlObj.pit.equalsIgnoreCase("EXM"))&&((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))			
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "EXM,nopicture,");
			articleHead.append("\\usepackage{EXM}\r\n");
		}
		else if((xmlObj.pit.equalsIgnoreCase("EXM"))&&(jid.equalsIgnoreCase("VACUN")))
		{
			articleHead.append("\\usepackage{VACUN_EXM}\r\n");
		}
		//else if(( xmlObj.pit.equalsIgnoreCase("LIT"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))
		else if(( xmlObj.pit.equalsIgnoreCase("LIT")))
		{//mukesh on 31-10-08
			
			
			if(xmlObj.check_MassonJid==true)
			{
				//System.out.println("No MODD or MODE----------------"+xmlObj.check_MassonJid);
				//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")) || (fmcforall))//08-04-2015
				if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")) || (fmcforall))
				{
					//articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "LIT,nopicture,MISC,");
					articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "LIT,nopicture,");
					if(!checkModelDModelE)
						articleHead.append("\\usepackage{LIT_MODD}\r\n");
					//System.out.println("No MODD or MODE");
				}else{
					articleHead.append("\\usepackage{LIT_STD}\r\n");
					
				}
				
			}
			//System.out.println("No MODD or MODE");
		}
		if(xmlObj.check_MassonJid==true)
		{
			if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")) || (fmcforall))
			{
				if(( xmlObj.pit.equalsIgnoreCase("NWS"))){
					articleHead.append("\\usepackage{NWS_MODD_new}\r\n");
				}
			}
		}
		//System.out.println("11111111111111");
		/**
		*Added [29/11/2007]
		*Added By Ravi
		* Change Point : In case of Journal GCB "nopicture"is added in documentclass.
		* Change Request By : TPMS
		*/
		/**
		*Added [09/06/2008]
		*Added By Ravi
		* Change Point : Please remove option nopicture in documentclas in case of JID GCB.
		* Change Request By : TPMS
		*/
		/*else if(jid.equalsIgnoreCase("GCB"))
		{
			articleHead.insert(articleHead.indexOf("\\documentclass[") + "\\documentclass[".length(), "nopicture,");
		}*/
		
//************************************************************
/**
* Date : 24/01/2008
* Added By Ravi 
*Modify Point : insert usepackage{DandD} if item is duckling and in not MASSON or 
				usepackage{DandDFrench} if item is duckling and in MASSON
*Change Request By : TPMS

*/		
String ad22=this.aid;
int in_pos1=ad22.indexOf(".",0);
if(in_pos1 !=-1)
{
		ad22=ad22.substring(0,in_pos1);
}
if(!stage.equalsIgnoreCase("S300"))
{
	//System.out.println("----------------> "+stage);
	ReadForEextra duckRFE= new ReadForEextra(this.jid.toUpperCase(),ad22,stage);
	System.out.println("isOrderPathAvailable===>> "+isOrderPathAvailable);
	if(isOrderPathAvailable.equalsIgnoreCase("yes"))
	{
		DucklingOrderStatus=	duckRFE.checkDuckling();
		isDandD=DucklingOrderStatus;
	}
	System.out.println("DucklingOrderStatus===>> "+DucklingOrderStatus);
}
//System.out.println("xmlObj.check_MassonJid : "+xmlObj.check_MassonJid+" DucklingOrderStatus : "+DucklingOrderStatus);
if(xmlObj.check_MassonJid==true && DucklingOrderStatus==true)
//	articleHead.append("\\usepackage{DandDFrench}\r\n"); 
	{
		//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
		if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
		{
			articleHead.append("\\usepackage{ABS_MODD}\r\n"); 
		}
		else 
		{
			
			articleHead.append("\\usepackage{ABS_STD}\r\n");
		}

	}
else if(xmlObj.check_MassonJid==false && DucklingOrderStatus==true)
	articleHead.append("\\usepackage{DandD}\r\n"); 

//////////////// Added by mukesh on 09-09-08 Request by TPMS

//******************************************************************
		/**
		*Added [20/03/2007]
		*Added By Arvind
		* Change Point : voffset and hoffset not required in tex file.
		* Change Request By : Vivek
		*/
		//articleHead.append("\\voffset-5pc\r\n");
		//articleHead.append("\\hoffset-4pc");
		//end

		if(ppe.length()>0){
			articleHead.append("\\global\\NewLayouttrue\r\n"); //Added on 25-09-2020
		}
		articleHead.append("\r\n\\begin{document}\r\n");
		if(jid.equalsIgnoreCase("CRAS2A"))//26/08/2006
			articleHead.append("\\jid{CRASTOA}\r\n");
		else
			articleHead.append("\\jid{"+jid+"}\r\n");
		if(XT.S250ABP.size()>0){
		String firstpage=XT.S250ABP.get("firstpage").toString();
		if(firstpage.length()>0 && firstpage !=null)
		articleHead.append("\\firstpage{"+firstpage+"}\r\n");
		else
		articleHead.append("\\firstpage{}\r\n");

		String coverdate=XT.S250ABP.get("cover_year").toString();
			if(coverdate.length()>0 && coverdate !=null)
			{
				int s=articleHead.indexOf("\\firstpage",0);
				if(s!=0)
				{
					articleHead.insert(s,"\\coverdate{"+coverdate+"}\r\n");
				}
			}

		}
		else
			articleHead.append("\\firstpage{}\r\n");
		if(viplist.contains(jid))
			articleHead.append("%\\lastpage{}\r\n");
		else
		{
			if(XT.S250ABP.size()>0){
			String lastpage=XT.S250ABP.get("lastpage").toString();
			if(lastpage.length()>0 && lastpage !=null)
				articleHead.append("\\lastpage{"+lastpage+"}\r\n");
			else
			articleHead.append("\\lastpage{}\r\n");
			}
			else
			articleHead.append("\\lastpage{}\r\n");
		}
		//System.out.println("1----XT.S250ABP "+XT.S250ABP);
		if(XT.S250ABP.size()>0){
		String voluame=XT.S250ABP.get("volume").toString();
		if(voluame.length()>0 && voluame !=null)
		articleHead.append("\\jvol{"+voluame+"}\r\n");
		else
		articleHead.append("\\jvol{}\r\n");
		}
		else{
			if(isAcanWorkflow(jid, aid)){
				articleHead.append("\\jvol{"+getVolFromAcanList(jid, aid)+"}\r\n");
			}else{
				articleHead.append("\\jvol{}\r\n");
			}
		}

//*******************
if(XT.S250ABP.size()>0){
	if(XT.S250ABP.get("stage").toString().equalsIgnoreCase("S250")||XT.S250ABP.get("stage").toString().equalsIgnoreCase("S250RESUPPLY"))
	{
		String year="";
		String month="";
		String day="";
		String date="";
		if(XT.S250ABP.get("year")!=null)
		year=XT.S250ABP.get("year").toString();
		if(XT.S250ABP.get("month")!=null)
		month=XT.S250ABP.get("month").toString();
		if(XT.S250ABP.get("day")!=null)
		day=XT.S250ABP.get("day").toString();
		if(year.length()>0 && year !=null)
		year=year;
		if(month.length()>0 && month !=null)
		{
			if(XT.articleType.equals("EN"))
				month=createMonth(Integer.parseInt(month));
			if(XT.articleType.equals("FR"))
				month=createMonth_French(Integer.parseInt(month));
			if(XT.articleType.equals("ES"))
				month=createMonth_Spanish(Integer.parseInt(month));
			if(XT.articleType.equals("IT"))
				month=createMonth_Italian(Integer.parseInt(month));
			
			//System.out.println("month " +month);
		}
		if(XT.S250ABP.get("day")!=null)
		day=Integer.parseInt(XT.S250ABP.get("day").toString())+"";
		if(day.length()>0 && day !=null)
			date=day+" ";
		if(month.length()>0 && month !=null)
			date+=month+" ";
		if(year.length()>0 && year !=null)
			date+=year;
		if(date.trim().length()>0)
		articleHead.append("\\pubdate{"+date+"}\r\n");
	}
}
//*****************************************Added by Adwait
			
		/*if(jid.equalsIgnoreCase("ENDOMX")&&(xmlObj.pit.equalsIgnoreCase("PRV")))
		{
			xmlObj.pit="FLA";
		}*/
		if(XT.book_review_err==true)
		{
				articleHead.append("\\docsubtype{ERR}\r\n");
		}
		else
		articleHead.append("\\docsubtype{"+xmlObj.pit+"}\r\n");
		System.out.println("Article Language :: "+XT.articleType);
		if(XT.articleType.equals("FR"))
			articleHead.append("\\journaltype{FRENCH}\r\n");
		if(XT.articleType.equals("ES"))
			articleHead.append("\\journaltype{SPANISH}\r\n");
		if(XT.articleType.equals("IT"))
			articleHead.append("\\journaltype{ITALIAN}\r\n");
		if(XT.articleType.equals("DE"))
			articleHead.append("\\journaltype{GERMAN}\r\n");
		if(XT.articleType.equals("EN"))
			articleHead.append("\\journaltype{ENGLISH}\r\n");
		if(XT.articleType.equals("CA"))
			articleHead.append("\\journaltype{CATALAN}\r\n");
		if(XT.articleType.equals("PT"))
			articleHead.append("\\journaltype{PORTUGUESE}\r\n");
		//articleHead.append("\\jid{"+jid+"}\r\n");
		articleHead.append("\\aid{"+aid+"}\r\n");
		if(!articleNo.isEmpty()){
			articleHead.append("\\articleID{"+articleNo+"}\r\n");
		}
		if(jid.equals("AMEEVA"))
		{
			articleHead.append("\\jnum{}\r\n");			
		}
		if(jid.equalsIgnoreCase("HLC")) // check in .tex file requested by Pagination-Tex
		{

			articleHead.append("%%%%\\fpoddskip\r\n%%%%%\\fpevenskip\r\n");
		}
//System.out.println("11------------------------------------------------------>"+modelStyle);
		
		String checkSpanish="";
		InputStream readSpanishFile=new FileInputStream(XT.databasePath + "SpanishJID.DBF");
		BufferedReader fin= new BufferedReader(new InputStreamReader(readSpanishFile));
		String lineStr="";
		while ((lineStr=fin.readLine())!=null)
		{
			if(lineStr.equalsIgnoreCase(jid)){
				if(xmlObj.pit.equalsIgnoreCase("PGL"))
					xmlObj.pit="FLA";
				break;
			}
			checkSpanishJidList.add(lineStr.trim());
		}
		
		/*if(checkModelDModelE)
		{
			if(xmlObj.pit.equalsIgnoreCase("EXM"))
				xmlObj.pit="FLA";			
		}*/
		
if(((xmlObj.pit.equalsIgnoreCase("brv") || (xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xmlObj.pit.equalsIgnoreCase("EDI")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))) ||xmlObj.pit.equalsIgnoreCase("PGL")|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&&(!jid.equalsIgnoreCase("NEUARG")&&!jid.equalsIgnoreCase("RAMB")&&!jid.equalsIgnoreCase("RCA")&&!jid.equalsIgnoreCase("RCAE")&&!jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || xmlObj.pit.equalsIgnoreCase("CNF") )&&(modelStyle.toLowerCase().startsWith("-mod7")||modelStyle.startsWith("7")))
		{
			articleHead_Compact.append("\\issnnum{"+pii.substring(1, 5)+pii.substring(5, 10)+"}\r\n");
			//System.out.println("11------------------------------------------------------>"+modelStyle);
		}
		else
		{
			articleHead.append("\\issnnum{"+pii.substring(1, 5)+pii.substring(5, 10)+"}\r\n");
			//System.out.println("22------------------------------------------------------>"+modelStyle);
		}
		
		/* Copyright line is a single line placed at the bottom of the page flush left. */
		
		//System.in.read();
		if(((xmlObj.pit.equalsIgnoreCase("brv") || (xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xmlObj.pit.equalsIgnoreCase("EDI")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))) ||xmlObj.pit.equalsIgnoreCase("PGL")|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&&(!jid.equalsIgnoreCase("NEUARG")&&!jid.equalsIgnoreCase("RAMB")&&!jid.equalsIgnoreCase("RCA")&&!jid.equalsIgnoreCase("RCAE")&&!jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || xmlObj.pit.equalsIgnoreCase("CNF") )&&(modelStyle.startsWith("-Mod7")||modelStyle.startsWith("7")))
		{
			String copy=copyrightline;
			String coprstatus="";
			//System.out.println("------------------------------------------------------>"+copyrightline);
			if(copyrightline.indexOf("\\copyrightStatus{",0)!=-1)
			{
				copy=copyrightline.substring(0,copyrightline.indexOf("\\copyrightStatus{",0));
				coprstatus=copyrightline.substring(copyrightline.indexOf("\\copyrightStatus{",0),copyrightline.length());
				copyrightline=copy;
			}
			
			articleHead_Compact.append(copyrightline+"\r\n");
			articleHead.append(coprstatus+"\r\n");
		}
		else
		{
			if(CClicense)//28-01-2014
			{
				String botcopyrightline=copyrightline;
				botcopyrightline=botcopyrightline.replaceFirst("\\\\copyrightline","\\\\botcopyrightline");
				System.out.println("botcopyrightline ::> "+botcopyrightline);
				botcopyrightline=botcopyrightline.substring(0,botcopyrightline.indexOf("\\copyrightStatus"));
				articleHead.append(botcopyrightline);
			}
			//System.out.println("222222222------------------------------------------------------>"+copyrightline);
			articleHead.append(copyrightline+"\r\n");
		}
		String tempAid;//added by mukesh on 01-11-08 TPMS
		String ActualAid;
		if(IsCutOffCR.containsKey(jid.toString()))
		{
			tempAid=IsCutOffCR.get(jid.toString()).toString();
			ActualAid=new String(aid);
			//System.out.println("AID-------"+aid);
			
			if(ActualAid.indexOf(".")!=-1)
			{
				ActualAid=ActualAid.substring(0,aid.indexOf("."));
				//System.out.println("tempAid-------"+ActualAid);
			}
			if(Integer.parseInt(ActualAid) >=(Integer.parseInt(tempAid)))
			{
				articleHead.append(EnCR+"\r\n");
				articleHead.append(FrCR+"\r\n");
				if(!ThirdLangCR.equals(""))
				{
					articleHead.append(ThirdLangCR+"\r\n");
				}
			}
		}
		articleHead.append("\\PIInumber{"+pii+"}\r\n");		
		
		if(doi.length()>0)
		{//-Mod7IChemE
			if((modelStyle.equals("7"))||(modelStyle.equals("-Mod7IChemE"))||(modelStyle.equals("-PIO")))//abhay 01/08/2006
				articleHead.append("\\aiplDOInumber{"+doi+"}\r\n");
			if(((xmlObj.pit.equalsIgnoreCase("brv") || (xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xmlObj.pit.equalsIgnoreCase("EDI")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))) || xmlObj.pit.equalsIgnoreCase("PGL")|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&&(!jid.equalsIgnoreCase("NEUARG")&&!jid.equalsIgnoreCase("RAMB")&&!jid.equalsIgnoreCase("RCA")&&!jid.equalsIgnoreCase("RCAE")&&!jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || xmlObj.pit.equalsIgnoreCase("CNF") )&&(modelStyle.startsWith("-Mod7")||modelStyle.startsWith("7")))
			{	
				articleHead_Compact.append("\\lDOInumber{"+doi+"}\r\n");
				
			}
			else
			{
				articleHead.append("\\lDOInumber{"+doi+"}\r\n");
			}
		}
		if(refpii.length()>0)
		{
			if(refpii.indexOf(",")!=-1)
				articleHead.append("\\RefersPiis{"+refpii+"}\r\n"); 
			else
				articleHead.append("\\referspii{"+refpii+"}\r\n"); 
		}
	
		if(refdoi.length()>0)
		{
			if(refdoi.indexOf(",")!=-1)
			{
				String [] count_refdoi=refdoi.split(",");
				/**
				*Modify Date : 29/09/2007
				*Modify By Ravi
				*Change Point : add new tex command [\\WDOI] with doi for hyperlink
				*/
				//System.out.println("count_refdoi : "+count_refdoi.length);
				for(int i=0;i<count_refdoi.length;i++)
				{
					if(i==0)
					{
						if(NewDOI(jid, temp_aid))//22-03-2012
						{
							refdoi="\\NewWDOI{"+count_refdoi[i].trim()+"}";
						}
						else
						refdoi="\\WDOI{"+count_refdoi[i].trim()+"}";
					}
					else if(i>0)
					{
						if(NewDOI(jid, temp_aid))//22-03-2012
						{
							refdoi+=", \\NewWDOI{"+count_refdoi[i].trim()+"}";
						}
						else
						refdoi+=", \\WDOI{"+count_refdoi[i].trim()+"}";
					}
				}
				articleHead.append("\\RefersDois{"+refdoi+"}\r\n"); 
			}
			else
			{
				if(NewDOI(jid, temp_aid))//22-03-2012
				{
					articleHead.append("\\refersdoi{\\NewWDOI{"+refdoi+"}}\r\n"); 
				}
				else
					articleHead.append("\\refersdoi{\\WDOI{"+refdoi+"}}\r\n"); 
			}
		}
		/*if(refpii.length()>0 && refdoi.length()>0)
		{
			articleHead.append("\\refersdoi{\\hypertarget{REFERSTO}{}\\hspace*{-4.5pt}$^{\\openstar}$\\hskip3pt doi of original article: "+refdoi+"}\r\n"); //21-12-2004			
			articleHead.append("\\referspii{"+refpii+"}\r\n"); //21-12-2004			
		}
		else if(refdoi.length()>0 && refpii.length()<=0)
		{
			articleHead.append("\\refersdoi{\\hypertarget{REFERSTO}{}\\hspace*{-4.5pt}$^{\\openstar}$\\hskip3pt doi of original article: "+refdoi+"}\r\n"); //21-12-2004			
		}
		else if(refpii.length()>0 && refdoi.length()<=0)
		{
			articleHead.append("\\referspii{\\hypertarget{REFERSTO}{}\\hspace*{-4.5pt}$^{\\openstar}$\\hskip3pt pii of original article: "+refpii+"}\r\n"); //21-12-2004			
		}*/
	//	System.out.println("articleHead.toString()==> "+articleHead.toString());
		return articleHead.toString();
	}
private String createMonth_Spanish(int Month)
    {
        
		String month="";
		switch (Month) {
            case 1:  month="enero"; break;
            case 2:  month="febrero"; break;
            case 3:  month="marzo"; break;
            case 4:  month="abril"; break;
            case 5:  month="mayo"; break;
            case 6:  month="junio"; break;
            case 7:  month="julio"; break;
            case 8:  month="agosto"; break;
            case 9:  month="septiembre"; break;
            case 10: month="octubre"; break;
            case 11: month="noviembre"; break;
            case 12: month="diciembre"; break;
            default: month="";break;
        }
		return month;
    }
	private String createMonth_Italian(int Month)
    {
        
		String month="";
		switch (Month) {
            case 1:  month="gennaio"; break;
            case 2:  month="febbraio"; break;
            case 3:  month="marzo"; break;
            case 4:  month="aprile"; break;
            case 5:  month="maggio"; break;
            case 6:  month="giugno"; break;
            case 7:  month="luglio"; break;
            case 8:  month="agosto"; break;
            case 9:  month="settembre"; break;
            case 10: month="ottobre"; break;
            case 11: month="novembre"; break;
            case 12: month="dicembre"; break;
            default: month="";break;
        }
		return month;
    }

private String createMonth_French(int Month)
    {
        
		String month="";
		switch (Month) {
            case 1:  month="janvier"; break;
            case 2:  month="f\\'{e}vrier"; break;
            case 3:  month="mars"; break;
            case 4:  month="avril"; break;
            case 5:  month="mai"; break;
            case 6:  month="juin"; break;
            case 7:  month="juillet"; break;
            case 8:  month="ao\\^{u}t"; break;
            case 9:  month="septembre"; break;
            case 10: month="octobre"; break;
            case 11: month="novembre"; break;
            case 12: month="d\\'{e}cembre"; break;
            default: month="";break;
        }
		return month;
    }
    private String  createMonth(int Month)
    {
		String month="";
		switch (Month) {
            case 1:  month="January"; break;
            case 2:  month="February"; break;
            case 3:  month="March"; break;
            case 4:  month="April"; break;
            case 5:  month="May"; break;
            case 6:  month="June"; break;
            case 7:  month="July"; break;
            case 8:  month="August"; break;
            case 9:  month="September"; break;
            case 10: month="October"; break;
            case 11: month="November"; break;
            case 12: month="December"; break;
            default: month="";break;
        }
		return month;

    }

public String NewChageForCommentTag(String stTag)
	{
		String sb=stTag;
		String str="";
		String label="";
		Vector CmtTagVec= new Vector();
		CmtTagVec.add("THEMATIC");
		CmtTagVec.add("EVENT");
		CmtTagVec.add("DNP");
		CmtTagVec.add("HEADING");
		CmtTagVec.add("QUESTION");
		CmtTagVec.add("RESPONCE");
		CmtTagVec.add("COMMENT");
		CmtTagVec.add("AU");
		CmtTagVec.add("AU-TITLE");
		CmtTagVec.add("AU-DETAILS");
		CmtTagVec.add("MAINAU");
		CmtTagVec.add("COORDINATERS");
		CmtTagVec.add("ABS-NO");
		CmtTagVec.add("ABS-TITLE");
		CmtTagVec.add("ABS-AU");
		CmtTagVec.add("ABS-AU-DETAILS");
		CmtTagVec.add("ABS-REFERENCE");
		CmtTagVec.add("ABS-KEYWORDS");
		CmtTagVec.add("BOOK-DETAILS");
		CmtTagVec.add("SUB-THEMATIC");
		CmtTagVec.add("DATE");
		CmtTagVec.add("PLACE");
		CmtTagVec.add("EVENT");
		CmtTagVec.add("EVENT");
		CmtTagVec.add("APPLY-DASHRULE");
		CmtTagVec.add("ABS-DOI");
		CmtTagVec.add("AU-DOI");
		CmtTagVec.add("ALT-ABS-TITLE");
		CmtTagVec.add("TIME");
		CmtTagVec.add("THEME");
		CmtTagVec.add("BOOK-TITLE");
		CmtTagVec.add("CATCH-LINE");
		CmtTagVec.add("INTERVENTION-TEXT");
		CmtTagVec.add("MODERATORS");
		CmtTagVec.add("MAINAFF");
		CmtTagVec.add("LIT-TITLE");
		CmtTagVec.add("LIT-AU");
		CmtTagVec.add("LIT-AFF");
		CmtTagVec.add("MAIN-AFFILIATION");
		CmtTagVec.add("VITAEHEADING");
		CmtTagVec.add("REFERENCE");
		CmtTagVec.add("IPA");
		CmtTagVec.add("RIGHT-ALIGN");
		CmtTagVec.add("TITLE-NOTE");
		CmtTagVec.add("COPYRIGHT-LINE");
		CmtTagVec.add("LICENSETEXT");
		CmtTagVec.add("CCLNK");
		CmtTagVec.add("CE:COPYRIGHT-LINE");
		CmtTagVec.add("CENTER-ALIGN");
		CmtTagVec.add("NOTE");
		//<!--<ce:copyright-line uniqueId="001" xml:lang="en" license="0">&copy; 2015 Elsevier B.V. All rights reserved.</ce:copyright-line>-->
		
		//System.out.println("sb : "+sb);
		int pos_query=0;
		int end_e_pos=0;
		if(sb.indexOf("<!--<ce:copyright-line",0)!=-1)
		{
			sb=sb.replaceAll("<!--<ce:copyright-line ([^<>]+)>([^<>]*)</ce:copyright-line>-->","");
		}
		if(sb.indexOf("<!--<copyright-line",0)!=-1)
		{
			sb=sb.replaceAll("<!--<copyright-line ([^<>]+)>(.*?)</copyright-line>-->","");
		}
		/*if(sb.indexOf("<licenseText>",0)!=-1)
		{
			sb=sb.replaceAll("<licenseText>","\\\\CCLcopyright{");
		}
		if(sb.indexOf("</licenseText>",0)!=-1)
		{
			sb=sb.replaceAll("</licenseText>","}");
		}
		if(sb.indexOf("<cclnk ",0)!=-1)
		{
			sb=sb.replaceAll("<cclnk type=\"([^\"]+)\">","(\\\\mychar\\\\url{");
		}
		if(sb.indexOf("</cclnk>",0)!=-1)
		{
			sb=sb.replaceAll("</cclnk>","}{}{})");
		}*/
		
		//Addaed by mukesh on 23-09-08 for self  comment tags Request by TPMS
		if(sb.indexOf("<!--<CitationBoxDOI>",0)!=-1)
		{
			sb=sb.replaceAll("<!--<CitationBoxDOI>([^<>]*)</CitationBoxDOI>-->","ThomsonDigitalMiddle_CitationBoxDOI ThomsonDigitalOpenBrace $1 ThomsonDigitalCloseBrace \r\n");
		}
		if(sb.indexOf("<!--<comment_rule/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<comment_rule/>-->","thomcomment:commentrule");
		}
		if(sb.indexOf("<!--<ABS-DocH>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<ABS-DocH>-->([^<>]*)<!--</ABS-DocH>-->","ThomsonDigitalMiddle_ABSDocH ThomsonDigitalOpenBrace $1 ThomsonDigitalCloseBrace \r\n");
		}
		if(sb.indexOf("<!--<OneColumn>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<OneColumn>-->","ThomsonDigitalMiddle_begin ThomsonDigitalOpenBrace OneColumn ThomsonDigitalCloseBrace \r\n");
		}
		if(sb.indexOf("<!--</OneColumn>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--</OneColumn>-->","ThomsonDigitalMiddle_end ThomsonDigitalOpenBrace OneColumn ThomsonDigitalCloseBrace");
		}
		if(sb.indexOf("<!--<TwoColumn>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<TwoColumn>-->","ThomsonDigitalMiddle_begin ThomsonDigitalOpenBrace TwoColumn ThomsonDigitalCloseBrace \r\n");
		}
		if(sb.indexOf("<!--</TwoColumn>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--</TwoColumn>-->","ThomsonDigitalMiddle_end ThomsonDigitalOpenBrace TwoColumn ThomsonDigitalCloseBrace");
		}

		if(sb.indexOf("<!--<Ack>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<Ack>-->","ThomsonDigitalMiddle_begin ThomsonDigitalOpenBrace Ack ThomsonDigitalCloseBrace \r\n");
		}
		if(sb.indexOf("<!--</Ack>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--</Ack>-->","ThomsonDigitalMiddle_end ThomsonDigitalOpenBrace Ack ThomsonDigitalCloseBrace");
		}

		if(sb.indexOf("<!--math-to-text-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--math-to-text-->","");
		}
		if(sb.indexOf("<!--/math-to-text-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--/math-to-text-->","");
		}
		if(sb.indexOf("<!--<Start-item/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<Start-item/>-->","thomcomment:StartItem:newline");
		}
		if(sb.indexOf("<!--<Body/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<Body/>-->","thomcomment:Body:newline");
		}
		if(sb.indexOf("<!--<Logo-ici/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<Logo-ici/>-->","thomcomment:Logoicitrue");
		}
		if(sb.indexOf("<!--<FMC/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<FMC/>-->","");
			FMC_comment=true;
		}
		if(sb.indexOf("<!--<NO FMC/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<NO FMC/>-->","");
			NOFMC_comment=true;
			check_FMC=false;
			FMC_comment=false;
			DPC_comment=false;
		}
		if(sb.indexOf("<!--<DPC/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<DPC/>-->","");
			DPC_comment=true;
		}
		if(sb.indexOf("<!--<TOC/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<TOC/>-->","");
			TOCrequired=true;
			System.out.println("XML contains <!--<TOC/>-->");
			System.out.println("TOC required in this item.");
		}
		if(sb.indexOf("<!--<MisTxt>",0)!=-1 &&sb.indexOf("</MisTxt>-->",0)!=-1)
		{
			
			sb=sb.replaceAll("<!--<MisTxt>([^<]+)</MisTxt>-->","ThomsonDigitalMiddle_MisTxtThomsonDigitalOpenBrace$1ThomsonDigitalCloseBrace");
			
		}
		if(sb.indexOf("<!--<smallfont/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<smallfont/>-->","-ssmmaallll-ffoonntt-");
		}
		if(sb.indexOf("<!--<parabreak/>-->",0)!=-1)
		{
			sb=sb.replaceAll("<!--<parabreak/>-->","\n\n");
		}


		StringBuffer sb2=new StringBuffer(sb);
		pos_query=0;
		cmmment_rhtitle="";
		/*if(sb2.indexOf("<!--<RunningTitle>",pos_query)!= -1)
		{
			pos_query=sb2.indexOf("<!--<RunningTitle>",pos_query);
			int epos_query=sb2.indexOf("</RunningTitle>-->",pos_query+1);
			cmmment_rhtitle=sb2.substring(pos_query+"<!--<RunningTitle>".length(),epos_query);
			sb2=sb2.delete(pos_query,epos_query+"</RunningTitle>-->".length());
		}*/		
		if(sb2.indexOf("<!--<RunningTitle>",pos_query)!= -1)
		{
			pos_query=sb2.indexOf("<!--<RunningTitle>",pos_query);
			sb2=sb2.insert(pos_query,"RTOPEN************");
			pos_query=0;
			pos_query=sb2.indexOf("<!--<RunningTitle>",pos_query);
			sb2=sb2.delete(pos_query,pos_query+"<!--<RunningTitle>".length());
			int epos_query=sb2.indexOf("</RunningTitle>-->",pos_query+1);
			sb2=sb2.insert(epos_query+"<!--<RunningTitle>".length(),"************RTCLOSE");
			epos_query=0;
			epos_query=sb2.indexOf("</RunningTitle>-->",pos_query+1);
			sb2=sb2.delete(epos_query,epos_query+"</RunningTitle>-->".length());
			//cmmment_rhtitle=sb2.substring(pos_query+"<!--<RunningTitle>".length(),epos_query);
			//sb2=sb2.delete(pos_query,epos_query+"</RunningTitle>-->".length());
			//System.out.println("RunningTitle lable : "+cmmment_rhtitle);
		}
		
		
		while(((pos_query=sb2.indexOf("<!--<SmallText>-->",pos_query))!= -1)&&(pos_query<=sb2.length()))
		{
//			StringBuffer sb1=new StringBuffer(sb);	
			int epos_query=sb2.indexOf("<!--</SmallText>-->",pos_query+1);
			String lable=sb2.substring(pos_query+"<!--<SmallText>-->".length(),epos_query);
			//System.out.println("lable : "+lable);
			//try{System.in.read();}catch(Exception e){}
			sb2=sb2.delete(pos_query,epos_query+"<!--</SmallText>-->".length());
			sb2=sb2.insert(pos_query,"thomson_begin_SmallText_close"+lable+"thomson_end_SmallText_close");
			/*if(CmtTagVec.contains(lable.toUpperCase()))
			{
				sb=sb.replaceFirst("<!--</"+lable+">-->","ThomsonDigitalCloseBrace");
			}
			
			else*/
			{
				pos_query=epos_query;
			}
			
		}
		sb=sb2.toString();
		pos_query=0;
		end_e_pos=0;
		while(((pos_query=sb.indexOf("<!--</",pos_query))!= -1)&&(pos_query<=sb.length()))
		{
			
			int epos_query=sb.indexOf(">-->",pos_query+1);
			String lable=sb.substring(pos_query+"<!--</".length(),epos_query);
			//System.out.println("sb : "+sb);
			//System.out.println("lable : "+lable);
			//try{System.in.read();}catch(Exception e){}
			if(CmtTagVec.contains(lable.toUpperCase()))
			{
				sb=sb.replaceFirst("<!--</"+lable+">-->","ThomsonDigitalCloseBrace");
			}
			
			else
			{
				pos_query=epos_query;
			}
			
		}
	//**********

		pos_query=0;
		end_e_pos=0;
		//System.out.println("serverstatus : "+serverstatus);
		while(((pos_query=sb.indexOf("<!--<",pos_query))!= -1)&&(pos_query<=sb.length()))
		{
			int epos_query=sb.indexOf(">-->",pos_query+1);
			String lable=sb.substring(pos_query+"<!--<".length(),epos_query);
			String lable1=lable;
			if(CmtTagVec.contains(lable.toUpperCase()))
			{
				if(lable.indexOf("-",0)!=0)//18/12/2007
				{
					//lable1=lable1.replaceFirst("-","");
					lable1=lable1.replaceAll("-","");
				}//18/12/2007
				sb=sb.replaceFirst("<!--<"+lable+">-->","ThomsonDigitalMiddle_"+lable1+"ThomsonDigitalOpenBrace");
			}
			else
			{
				//System.out.println("1 --------------->"+lable);
				if(!(lable.startsWith("AQText1")||lable.startsWith("AQText4")||lable.startsWith("AQText5")||lable.startsWith("AQText6")||lable.startsWith("TEES-XML")||lable.startsWith("STAGE ")))
				{
					if(serverstatus.length()>0){
						xt_log.info("[ERROR]= Comment Tag <!--<"+lable+">--> not handled !!");
						pos_query=epos_query;
						//break;
						System.exit(0);
					}
					else{
					//	System.out.println("--------------->"+lable);
					JOptionPane.showMessageDialog(null,"Comment Tag <!--<"+lable+">--> not handled !!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					pos_query=epos_query;
					//break;
					System.exit(0);
					}
				}
				else
				{
					StringBuffer sb1= new StringBuffer(sb);
					int s=0;
					int e=0;
					if(sb1.indexOf("<!--<AQText5>",0)!=-1)
					{
						while((s=sb1.indexOf("<!--<AQText5>",s))!=-1)
						{
							//s=sb1.indexOf("<!--<AQText5>",e);
							//if(s!=-1)
							{
								e=sb1.indexOf("</AQText5>-->",s);
								if(e!=-1)
								{
									sb1=sb1.delete(s,e+"</AQText5>-->".length());
								}
							}
							//System.out.println("s "+s);
						}
					}
					
					s=0;
					e=0;
					if(sb1.indexOf("<!--<AQText6>",0)!=-1)
					{
						while((s=sb1.indexOf("<!--<AQText6>",s))!=-1)
						{
							e=sb1.indexOf("</AQText6>-->",s);
							if(e!=-1)
							{
								sb1=sb1.delete(s,e+"</AQText6>-->".length());
							}
						}
					}
					s=0;
					e=0;
					if(sb1.indexOf("<!--<AQText1>",0)!=-1)
					{
						
						while((s=sb1.indexOf("<!--<AQText1>",s))!=-1)
						{
							e=sb1.indexOf("</AQText1>-->",s);
							if(e!=-1)
							{
								sb1=sb1.delete(s,e+"</AQText1>-->".length());
							}
						}
					}
					sb=sb1.toString();
					sb=sb.replaceAll("<!--<AQText4/>-->","");
					/*//System.out.println("--------------->"+lable);
					
					sb=sb.replaceAll("<!--<AQText5>([^<]+)</AQText5>-->","");
					sb=sb.replaceAll("<!--<AQText6>([^<]+)</AQText6>-->","");
					sb=sb.replaceAll("<!--<AQText1>([^<]+)</AQText1>-->","");
					sb=sb.replaceFirst("<!--<"+lable+">-->","");
					*/
					pos_query=epos_query;
					//break;
				}
			}
		}
		
		pos_query=0;
		end_e_pos=0;
		//System.out.println("serverstatus : "+serverstatus);
		while(((pos_query=sb.indexOf("<!-- <",pos_query))!= -1)&&(pos_query<=sb.length()))
		{
			int epos_query=sb.indexOf("> -->",pos_query+1);
			String lable=sb.substring(pos_query+"<!-- <".length(),epos_query);
			String lable1=lable;
			if(CmtTagVec.contains(lable.toUpperCase()))
			{
				if(lable.indexOf("-",0)!=0)//18/12/2007
				{
					//lable1=lable1.replaceFirst("-","");
					lable1=lable1.replaceAll("-","");
				}//18/12/2007
				sb=sb.replaceFirst("<!-- <"+lable+"> -->","ThomsonDigitalMiddle_"+lable1+"ThomsonDigitalOpenBrace");
			}
			else
			{
				//System.out.println("1 --------------->"+lable);
				if(!(lable.startsWith("AQText1")||lable.startsWith("AQText4")||lable.startsWith("AQText5")||lable.startsWith("AQText6")||lable.startsWith("STAGE ")))
				{
					if(serverstatus.length()>0){
						xt_log.info("[ERROR]= Comment Tag <!--<"+lable+">--> not handled !!");
						pos_query=epos_query;
						//break;
						System.exit(0);
					}
					else{
					//	System.out.println("--------------->"+lable);
					JOptionPane.showMessageDialog(null,"Comment Tag <!--<"+lable+">--> not handled !!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					pos_query=epos_query;
					//break;
					System.exit(0);
					}
				}
				else
				{
					StringBuffer sb1= new StringBuffer(sb);
					int s=0;
					int e=0;
					if(sb1.indexOf("<!--<AQText5>",0)!=-1)
					{
						s=sb1.indexOf("<!--<AQText5>",e);
						if(s!=-1)
						{
							e=sb1.indexOf("</AQText5>-->",s);
							if(e!=-1)
							{
								sb1=sb1.delete(s,e+"</AQText5>-->".length());
							}
						}
					}
					
					s=0;
					e=0;
					if(sb1.indexOf("<!--<AQText6>",0)!=-1)
					{
						s=sb1.indexOf("<!--<AQText6>",e);
						if(s!=-1)
						{
							e=sb1.indexOf("</AQText6>-->",s);
							if(e!=-1)
							{
								sb1=sb1.delete(s,e+"</AQText6>-->".length());
							}
						}
					}
					s=0;
					e=0;
					if(sb1.indexOf("<!--<AQText1>",0)!=-1)
					{
						s=sb1.indexOf("<!--<AQText1>",e);
						if(s!=-1)
						{
							e=sb1.indexOf("</AQText1>-->",s);
							if(e!=-1)
							{
								sb1=sb1.delete(s,e+"</AQText1>-->".length());
							}
						}
					}
					sb=sb1.toString();
					sb=sb.replaceAll("<!-- <AQText4/> -->","");
					//System.out.println("--------------->"+lable);
					/*
					
					sb=sb.replaceAll("<!-- <AQText5>([^<]+)</AQText5> -->","");
					sb=sb.replaceAll("<!-- <AQText6>([^<]+)</AQText6> -->","");
					sb=sb.replaceAll("<!-- <AQText1>([^<]+)</AQText1> -->","");
					sb=sb.replaceFirst("<!-- <"+lable+"> -->","");
					*/
					pos_query=epos_query;
					//break;
				}
			}
		}
		return sb;
	}

public String NewChageForColorTag(String cont)
	{
		String sb=cont;
		String str="";
		String label="";
				

		int pos_query=0;
		int end_e_pos=0;
		while(((pos_query=sb.indexOf("<!--<Color>-->",pos_query))!= -1))
		{
			
			int epos_query=sb.indexOf("<!--</Color>-->",pos_query+1);
			String lable=sb.substring(pos_query+"<!--<Color>-->".length(),epos_query);
			//sb=sb.replaceFirst("<!--<Color>-->"+lable+"<!--</Color>-->","ThomsonDigitalColorStart"+lable+"ThomsonDigitalColorEnd");
			sb=sb.replaceFirst("<!--<Color>-->(.*?)<!--</Color>-->","ThomsonDigitalColorStart$1ThomsonDigitalColorEnd");
			//added by mukesh on 10-11-08(TPMS)
			//System.out.println("lable---------"+lable);
		
		}
	
		return sb;
	}
//*************************************************************************************************
public String NewChageForNoIndent(String stTag)
	{
		String sb=stTag;
		Vector CmtTagVec= new Vector();
		CmtTagVec.add("noindent");
		for(int i=0 ; i< CmtTagVec.size();i++)
		{
			sb=ConvertNoIndent(sb,CmtTagVec.get(i).toString());
		}

		return sb;
	}
public String ConvertNoIndent(String stTag,String Tval)
	{
		StringBuffer sb=new StringBuffer(stTag);
		String str="";
		String label="";
		
		int pos_query=0;
		int epos_query=0;
		while(((pos_query=sb.indexOf("<!--<"+Tval,epos_query))!= -1)&&(pos_query<=sb.length()))
		{
			
			epos_query=sb.indexOf("/>-->",pos_query);
			String lable=sb.substring(pos_query+"<!--<".length(),epos_query);
			//System.out.println("lable : "+lable);
			//try{System.in.read();}catch(Exception e){}
			//if(CmtTagVec.contains(lable.toUpperCase()))
			{
				sb=sb.delete(pos_query,epos_query+"/>-->".length());
				sb=sb.insert(pos_query,"ThomsonBackS"+Tval+" ");
				//sb=sb.replaceFirst("<!--<"+lable+"/>-->","RAVI ");
			}
			//else
			{
				//pos_query=epos_query;
			}
			
		}
		//System.out.println("Tval : "+Tval);
			
		//sb=sb.replaceAll("<!--<"+Tval+"/>-->","\\\\"+Tval+" ");
		//sb=sb.replaceAll("<!--<"+Tval+"/>-->","\\\\RAVI ");
	//try{System.in.read();}catch(Exception e){}
		return sb.toString();
	}

//********************************************************************************************************

public String removeMFCCommentTag(String content)
{
	StringBuffer sb=new StringBuffer(content);
	int pos_query=0;
	int epos_query=0;
	while(pos_query!= -1)		{

			pos_query=sb.indexOf("<!-- mfc-out",epos_query);
			if(pos_query!=-1)
			{
				epos_query=sb.indexOf("]] -->",pos_query);
				if(epos_query!=-1)
				{
					String lable=sb.substring(pos_query,epos_query);
					//System.out.println("lable : "+lable);
					sb=sb.delete(pos_query,epos_query+"]] -->".length());
				}
			}
		}
		
		return sb.toString();
}
public static void main(String args[])throws java.io.IOException
{
	main_Status(args);
}
	public static boolean main_Status(String args[])throws java.io.IOException
	{
		
		//System.out.println("args[0]"+args[0]);
		//System.out.println("args[1]"+args[1]);
		//System.out.println("args[2]"+args[2]);
		//System.out.println("args[3]"+args[3]);
		//System.out.println("args[4]"+args[4]);
		//System.out.println("args[5]"+args[5]);
		
		//Read for Volumn/Issue No.[11/07/2007]
		String read=null;
		//end
		//String lable1="";
		Hashtable appP= null;
		boolean checkModelDModelEVal=false;
		Hashtable figInfo= null;
		int cur_jpr_biog=0;		
		XT chekModelDAndModelEStatus=new XT();
		System.out.println("*********************************************************");
		System.out.println("**     XT [XML to Tex] Ver. 7.4 [30-08-2007]           **");
		System.out.println("**         Developed by : Thomson Digital              **");
		System.out.println("*********************************************************");
		ResourceBundle rbs=ResourceBundle.getBundle("xt");
		databasePath=rbs.getString("databasePath");
		
		String systemIP="";
		try{
			InetAddress local= InetAddress.getLocalHost();
			systemIP=local.getHostAddress();
			System.out.println("Local hostname is: " + local.getHostAddress());
		}
			catch (UnknownHostException e){
				System.out.println("Can't detect localhost : " + e);
		}
		
		if(systemIP.startsWith("10.10.2"))
			isDatabaseNotRequired=true;
		
		isDatabaseAvailable=rbs.getString("isDatabaseAvailable");
		isOrderPathAvailable=rbs.getString("isOrderPathAvailable");
		if(isOrderPathAvailable.equalsIgnoreCase("no"))
		{
			isOrderPathAvailable=rbs.getString("ORDERPATHFORFMS");
		}

			
		isToBeParsed=rbs.getString("ischeckparse");
		XT server_Xt=new XT();
		if(args.length==6)
		{
			if(args[5].trim().equals("SERVER"))
			serverstatus="SERVER";
			checkStage=args[3];
			//System.out.println("checkStage : "+checkStage);
		}
 	   /*
		* Added By Arvind [25_04_2007]
		* Change Request By: Vivek
		* Change Point : 1.Please use new package in tex file after usepackage{NewTwoHyphenCont} usepackage{FigTop}
		*				 2.No need of onecolumn in documentclass option globally only specific journals required 
		*				   onecolumn option. OneColumn journals list: JAMM JEBO EDUREV ACN AIP TSC CLIPOL EARCHI 
		*				   GEOD INFBEH MATBEH MATCOM PEVA PUBREL WAMOT PRO 
		*				 3.Stage="S100-draft" and Stage="S200-draft" Stage="S300-Web draft" should be used in
		*				   documentclass option. 
		*/	

	//	onecol_Journal_Path=rbs.getString("LIST_OF_ONECOLUMN_JOURNAL_PATH");
		//XT.onecol_Journal_List(rbs.getString("LIST_OF_ONECOLUMN_JOURNAL_PATH"));
		
		//XT.onecol_Journal_List();
		
		///////////////////////////////////[25-04-2007]////////////////////////////////////////

		if(args[1].length()>0)
		{
			if(!(new File(args[1])).exists())
			{
				if(serverstatus.length()>0)
				{
					server_Xt.xt_log.info("[ERROR]=  Application Properties Not Found");
					return false;
				}
				else{
				System.out.println("[ERROR] : Application Properties Not Found");
				System.exit(0);
				}
			}
			try
			{
				appP= new ReadConfSettings(args[1]).getSettings();
								
			}
			catch(Exception exp)
			{
				if(serverstatus.length()>0)
				{
					server_Xt.xt_log.info("[ERROR]=  Unable To Get Application Properties");
					return false;
				}
				else{
				System.out.println("[ERROR] : Unable To Get Application Properties");
				System.exit(0);
				}
			}
		}
		try
		{
			figInfo = (new TiffInfo(appP.get("tiffinfo-file").toString())).getInfo();
			//System.out.println("figInfo : "+figInfo);
		}
		catch(Exception exp)
		{
			if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Unable To Get Figures Information");
					server_Xt.xt_log.info("[ERROR] Check "+appP.get("tiffinfo-file").toString());
					return false;
				}
				else
				{
					System.out.println("[ERROR] : Unable To Get Figures Information");
					System.out.println("[ERROR] : Check "+appP.get("tiffinfo-file").toString());
					System.exit(0);
				}
		}
		String sw1=new File(args[0]).getAbsolutePath();
		if(!(new File(args[0])).exists())
		{
			   if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Source File '"+sw1+"' Not Found");
					server_Xt.xt_log.info("[ERROR] Source File '"+args[0]+"' Not Found");
					return false;
				}
				else
				{
				System.out.println("[ERROR] : Source File '"+sw1+"' Not Found");
				System.out.println("[ERROR] : Source File '"+args[0]+"' Not Found");
				System.exit(0);
				}
		}
		
		String driver="";
		String url="";
		String onlineVersionQuery="";
		String ducklingQuery="";
		String anirepQuery="";
		String stoneQuery = "";
		String RSVP_Stage_Query = "";
		
		boolean DuckCheck=false;//[03/01/2006 Added by Ravi checking duckling]

		if(args.length>=3)//Change by bhavesh for multirow
			stage=args[2].toUpperCase().trim();
		System.out.println("isDatabaseAvailable::"+isDatabaseAvailable+" args.length="+args.length);
	   if(isDatabaseAvailable.equalsIgnoreCase("yes"))
		{
			if(args.length>=3)
			{
				stage=args[2].toUpperCase().trim();
				//stage=args[2].trim();
				
				if(stage.equalsIgnoreCase("S100"))
					stageDesc="S100";
				else if(stage.equalsIgnoreCase("S200"))
					stageDesc="S200";
				else if(stage.equalsIgnoreCase("RSVP")){//Updated on 31-08-2016
					stage="S200";
					stageDesc="S200";
				}else if(stage.equalsIgnoreCase("P100"))
					stageDesc="P100";
				else if(stage.equalsIgnoreCase("S300"))
					stageDesc="S300";
				else if(stage.equalsIgnoreCase("S100RESUPPLY"))
					stageDesc="S100RESUPPLY";
				else if(stage.equalsIgnoreCase("S200RESUPPLY"))
					stageDesc="S200RESUPPLY";
				else if(stage.equalsIgnoreCase("P100RESUPPLY"))
					stageDesc="P100RESUPPLY";
				else if(stage.equalsIgnoreCase("S300RESUPPLY"))
					stageDesc="S300RESUPPLY";
				else
					stageDesc="";
				
				//System.in.read();
				
				if(args.length==4)
				{
					if(args[3].trim().equals("true"))
						checkDuckling=true;
					else if(args[3].trim().equals("false"))
						checkDuckling=false;
					
						
				}
				//[03/01/2006 Added by Ravi checking duckling]
				if(args.length==5)
				{
					DuckCheck =true;
					
					//System.out.println("DuckCheck in side if  -->"+DuckCheck);
					//System.in.read();
				}
				if(args.length==6)
				{
					//FMS ="FMS";
					//System.out.println("FMS Article...");
					//System.in.read();
				}
				if(stage.equalsIgnoreCase("S300"))
				{
				}
			}
			else
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Stage Not Provided [S100/S200/P100/S300] !!! Pass Stage as 2nd parameter!!!");
					return false;
				}
				else{
					
					JOptionPane.showMessageDialog(null,"Stage Not Provided [S100/S200/P100/S300] !!! Pass Stage as 2nd parameter!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				
				}
			}
			
			if(stageDesc.length() == 0)
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Stage XXX Not Provided [S100/S200/P100/S300] !!! Pass Stage as 2nd parameter!!!");
					return false;
				}
				else{
				JOptionPane.showMessageDialog(null,"Stage XXX Not Provided [S100/S200/P100/S300] !!! Pass Stage as 2nd parameter!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
				}
			}
			driver=appP.get("driver").toString();
			url=appP.get("url").toString();				
			onlineVersionQuery=appP.get("onlineversion-query").toString();
			ducklingQuery=appP.get("duckling-query").toString();
			anirepQuery=appP.get("anirep-si").toString();
			stoneQuery=appP.get("stone").toString();
			RSVP_Stage_Query=appP.get("RSVP_Stage").toString();
			//System.out.println("RSVP_Stage_Query : "+RSVP_Stage_Query);
		}
		/*
		* This below condition is use for all stage 
		* if any problem use xtold.bat .
		* Commented by Ravi as per discuss with Vivek Dubey [10/01/2007]
		*/
		if(args.length==5)
		{
			checkStage=args[4];
			//System.out.println("checkStage====> "+checkStage+"  args.length==> "+args.length);
			//System.in.read();
		}
		if(args.length==4)
		{
			checkStage=args[3];
			//System.out.println("else checkStage====> "+checkStage+"  args.length==> "+args.length);
			//System.in.read();
		}
	
		if(args.length==6)
		{
			//FMS ="FMS";
			//System.out.println("FMS Article...");
			//System.in.read();
		}
	
		XT xt   = new XT(new File(args[0]));
		
		//****************************************************************************
		/*System.out.println("args[0] : "+args[0]);
			CopyFile cf=new CopyFile();
			cf.checkForFile(args[0], "Ravi"+args[0]);*/
		//****************************************************************************
		/**
		 *Added By : Ravi 
		 *Date : [20/06/2007]
		 *Change Point : Validate input xml file against dtd.
		 *
		 **/
		String path=new File(args[0]).getCanonicalPath();
		//System.out.println("before parsing : "+isToBeParsed);
//		book_review_err
//************[06/03/2009]***************New Check for book-review pit=err and Exam pit=err
Sax sax = new Sax();
try{
	book_review_err=sax.getValue(path);

}catch(Exception e){}
//********************************************************************************
		CheckXmlParser dom = new CheckXmlParser();
		//System.out.println("DuckCheck "+DuckCheck);
		if(DuckCheck==false)
		{
			if(isToBeParsed.equalsIgnoreCase("YES")) //mukesh to check parsing instruction
			{
				if(dom.parseXML(path))
				{
					//System.out.println("after parsing: "+isToBeParsed);
				}
				else
				{
					if(serverstatus.equals("SERVER"))
					{
						if(dom.error.indexOf(".dtd",0)!=-1)
						{
							server_Xt.xt_log.info("[ERROR] Parsing Error: Invalid Xml File.Message:  "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+" Contact to S/W Dept.");
						}
						else
						server_Xt.xt_log.info("[ERROR] Parsing Error: Invalid Xml File.Message:  "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+" Revert to COPY EDITING dept.");
						return false;
					}
					else{
						JOptionPane.showMessageDialog(null," Parsing Error: Invalid Xml File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nRevert to COPY EDITING dept.");
						System.exit(1);	
					}
					//cf.checkForFile("Ravi"+args[0], args[0]);
					
				}
			}
		}
		//end
		
		//************************************************
//[Added By Ravi 03/05/2007]
//Change Request By Subrata. 
//Change Point: deleting query tag in xml file
		
xt.fin3     = new RandomAccessFile(xt.xmlFile, "rw");
String s4="";
StringBuffer sb=new StringBuffer();

do
  {
	if((s4 = xt.fin3.readLine()) == null)
      {  break;
      }
	else
	  {
		sb.append(s4);
	  }
  }
while(true);

xt.fin3.close();

/**********[03-09-2011]************************/
if(sb.indexOf("<!--<TEES-XML>-->",0)!=-1)
{
	isTEESXml=true;
}
else
{
	isTEESXml=false;
}
//System.out.println("TEES XML status :: "+isTEESXml);
/*************[03-09-2011]********************/

//******************************[Check e-extra in xml [11/07/2007] ]************************************

int spos_1=0;
int epos_1=0;
String JD="";
String AD="";
//<jid>
while(spos_1!= -1)
{
	spos_1=sb.indexOf("<jid>",epos_1);
	if(spos_1!=-1)
	{
		epos_1=sb.indexOf("</jid>",spos_1+5);
		if(epos_1 != -1)
		{
			JD=sb.substring(spos_1+"<jid>".length(),epos_1);
			//System.out.println("JID : "+JD);
		}
	}
}


	if(JD.equalsIgnoreCase("ACA"))
	{
		if(sb.indexOf("<ce:biography id=\"",0)!=-1)
		{
			check_biography=true;
		}
	}

spos_1=0;
epos_1=0;
while(spos_1!= -1)
{
	spos_1=sb.indexOf("<aid>",epos_1);
	if(spos_1!=-1)
	{
		epos_1=sb.indexOf("</aid>",spos_1+5);
		if(epos_1 != -1)
		{
			AD=sb.substring(spos_1+"<aid>".length(),epos_1);
			//System.out.println("AID : "+AD);
		}
	}
}

if((JD.equalsIgnoreCase("JMRTEC") && Integer.parseInt(AD)>=53))
{
	if(sb.indexOf("<ce:biography id=\"",0)!=-1)
	{
		check_biography=true;
	}
}


String mfccommentTag=xt.removeMFCCommentTag(sb.toString());
/*
String resus_temp=sb.toString();

resus_temp=resus_temp.replaceAll("<ce:section id=\"sec([0-9]+)\"><ce:section ","<ce:section id=\"sec$1\"><ce:section-title>\\Dummy_Section_Title</ce:section-title><ce:section ");
resus_temp=resus_temp.replaceAll("<ce:section id=\"sec([0-9]+)\"><ce:section ","<ce:section id=\"sec$1\"><ce:section-title>\\Dummy_Section_Title</ce:section-title><ce:section ");

sb=new StringBuffer(resus_temp);
*/
XT.onecol_Journal_List(AD);


//***************[09-04-2010]*****************
//New Changes for S250 ABP
//System.out.println("stage here :"+stage);
xt.S250ABP =new Hashtable();
if(stage.equalsIgnoreCase("S200")){
String tempad="";
if(AD.indexOf(".",0)!=-1)
	tempad=AD.substring(0,AD.indexOf(".",0));
else
tempad=AD;
ReadForEextra re= new ReadForEextra(JD,tempad,stage);
xt.S250ABP=re.getStage(JD,tempad);

if(xt.S250ABP.get("stage")!=null)
stage=xt.S250ABP.get("stage").toString();
}
//*************************************

	if(JD.equalsIgnoreCase("BODYIM"))
	{
		String tempad="";
		if(AD.indexOf(".",0)!=-1)
			tempad=AD.substring(0,AD.indexOf(".",0));
		else
			tempad=AD;
		if(Integer.parseInt(tempad)>493)
			isBodyimRef=true;

	}
if(JD.equalsIgnoreCase("BJMSU"))
{
String tempad="";
if(AD.indexOf(".",0)!=-1)
	tempad=AD.substring(0,AD.indexOf(".",0));
else
tempad=AD;
ReadForEextra re1= new ReadForEextra(JD,tempad,stage);
xt.isCMEInOrder=re1.readOrderFileCME();
//System.out.println("aaaaaaaaS250ABP "+xt.isCMEInOrder);
}
Sax sax1 = new Sax();
try{
	
	//System.out.println("path2::>>"+path);
	book_review_otherref=sax1.getBRV(path);
	//System.out.println("aaaaaaaaaabook_review_otherrefsax "+book_review_otherref);
	String brref=sb.toString();
	//System.out.println("aaaaaaaaaabook_review_otherrefsax "+book_review_otherref);
	String first="";
	String last="";
	if(brref.indexOf("</book-review-head>",0)!=-1)
	{
		   first=brref.substring(0,brref.indexOf("</book-review-head>",0));
		   last=brref.substring(brref.indexOf("</book-review-head>",0),brref.length());
	}
	if(book_review_otherref){
		if(first.indexOf("<!--<NewLine/>-->",0)!=-1){
			//System.out.println("book_review_otherrefsax "+book_review_otherref);
			first=first.replaceAll("<!--<NewLine/>-->","ThomsonDigitalCloseBrace\r\nThomsonDigitalMiddle_miscTitleThomsonDigitalOpenBrace");
			//sb=new StringBuffer(first+last);
		}
	}
	//System.out.println("book_review_otherrefsax "+first);
	if(book_review_otherref==false)
	{
		if(brref.indexOf("<body>",0)!=-1)
		{
			first=brref.substring(0,brref.indexOf("<body>",0));
			last=brref.substring(brref.indexOf("<body>",0),brref.length());
			if(first.indexOf("<!--<NewLine/>-->",0)!=-1){
				
				first=first.replaceAll("<!--<NewLine/>-->","ThomsonDigitalMiddle_NewLine ");
				//sb=new StringBuffer(first+last);
			}
		}
		
	}
	
	
		if(last.indexOf("<!--<NewLine/>-->",0)!=-1){
			last=last.replaceAll("<!--<NewLine/>-->","ThomsonDigitalMiddle_NewLine ");
			
		}
		if(first.length()>0 && last.length()>0)
			sb=new StringBuffer(first+last);
		//System.out.println("1 XT Calling .... "+sb);
	
}catch(Exception e){
System.err.println("------"+e.getMessage());
}

if(JD.equalsIgnoreCase("JCV"))//15/01/2010
{
	if(AD.indexOf(".",0)!=-1)
	{
		AD=AD.substring(0,AD.indexOf(".",0));
	}
	ReadForEextra re= new ReadForEextra(JD,AD,stage);
	if(re.readOrderFileForFMC_JCV())
	{
		//System.out.println("1");
		if(re.getInforForJCV(sb.toString()))
		{
		}else
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR] Role tag not found in para for VIROQAS style wrong xml revert to copyediting.");
					return false;
			}
			else{
				JOptionPane.showMessageDialog(null,"Error: Role tag not found in para for VIROQAS style wrong xml revert to copyediting.","Error",JOptionPane.YES_OPTION);
				System.exit(0);
				}
		}
	}
}
if(JD.length()>0 && AD.length()>0)
		{
		XMLObjects xj =new XMLObjects(JD.trim().toUpperCase(),AD.trim().toUpperCase(),serverstatus);
		//xt.MassionJid=JD.trim().toUpperCase();//04/12/12/2007
	//System.out.println("1 xt.MassionJid : "+xt.MassionJid);
		}
if( JD.length()<=0)
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR] Error: MIssing JID in xml file.");
					return false;
			}
			else{
				JOptionPane.showMessageDialog(null,"Error: MIssing JID in xml file.","Error",JOptionPane.YES_OPTION);
				System.exit(0);
				}
		}
if(AD.length()<=0)
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR] Error: MIssing AID in xml file.");
					return false;
			}
			else{
			JOptionPane.showMessageDialog(null,"Error: MIssing AID in xml file.","Error",JOptionPane.YES_OPTION);
			System.exit(0);
			}
		}

	String RSVP_Text="";
	if(stage.equalsIgnoreCase("S300"))
		{
		  if(isDatabaseAvailable.equalsIgnoreCase("yes"))
			{
				Connect c1=new Connect();
				try
					{
						if(c1.dbConnect(driver,url))
						{
							RSVP_Text = c1.RSVPQuery(RSVP_Stage_Query, JD, AD);
							//System.out.println("RSVP_Text : "+RSVP_Text);
						}
						c1.cleanupDbObjects();
					}
					catch(java.lang.ClassNotFoundException e) {			
						System.err.print("ClassNotFoundException: "); 
						System.err.println(e.getMessage());
					}
					catch(SQLException ex) {			
						System.err.println("SQLException: " + ex.getMessage());
						c1.cleanupDbObjects();
					}
			}
		}
/*if(!RSVP_Text.equalsIgnoreCase("RSVP"))
	{System.out.println("Volume No./ Issue No. : "+stage);
		if(stage.equalsIgnoreCase("S300"))
		{
			
			InputStreamReader console=new InputStreamReader(System.in);
			BufferedReader reader=new BufferedReader(console);
			
			//String read=null;
			do{
				System.out.print("PLEASE ENTER VOLUME NO./ ISSUE NO. : ");
				read=reader.readLine();
				if(!read.equals(""))
				{
					//System.out.println("Volume No./ Issue No. : "+read);
					break;
				}
			
			}while(true);
		}
	}*/
//*******************************************************************
	
int pos_query=0;
int end_e_pos=0;
int mpos=0;
boolean check_No_Query=false;
rename_main_xml_file=sb.toString();
QueryVector=new Vector();//04-08-2011
while((pos_query=sb.indexOf("<!--<query id=\"",mpos))!= -1)
{
	int epos_query=sb.indexOf("\">",pos_query+1);
	
	String lable="";
	mpos=sb.indexOf("</query>-->",epos_query);
	if(mpos != -1)
	{
		//lable=sb.substring(epos_query+"\">".length(),mpos).toUpperCase();
		lable=sb.substring(epos_query+"\">".length(),mpos);
		QueryVector.add(lable);
		//if(lable.equalsIgnoreCase("Please check the short title that has been created, or suggest an alternative of fewer than 80 characters including spaces")||lable.equalsIgnoreCase("Por favor, compruebe que la versi&oacute;n abreviada del t&iacute;tulo que se ha creado para la cabecera de las p&aacute;ginas es correcta, o proporcione uno de un m&aacute;ximo de 80 caracteres (incluyendo espacios)")||lable.equalsIgnoreCase("Por favor verifique se a vers&atilde;o abreviada do t&iacute;tulo gerada para os cabe&ccedil;alhos &eacute; adequada, ou em alternativa forne&ccedil;a um t&iacute;tulo no m&aacute;ximo com 80 caracteres (incluindo espa&ccedil;os)."))
		if(lable.equalsIgnoreCase("Please check the short title that has been created, or suggest an alternative of fewer than 80 characters including spaces")||lable.equalsIgnoreCase("Please check the short title that has been created, or suggest an alternative of fewer than 80 characters including spaces.")||lable.equalsIgnoreCase("Por favor, compruebe que la versi&oacute;n abreviada del t&iacute;tulo que se ha creado para la cabecera de las p&aacute;ginas es correcta, o proporcione uno de un m&aacute;ximo de 80 caracteres (incluyendo espacios)")||lable.equalsIgnoreCase("Por favor, compruebe que la versi&oacute;n abreviada del t&iacute;tulo que se ha creado para la cabecera de las p&aacute;ginas es correcta, o proporcione uno de un m&aacute;ximo de 80 caracteres (incluyendo espacios).")||lable.equalsIgnoreCase("Por favor verifique se a vers&atilde;o abreviada do t&iacute;tulo gerada para os cabe&ccedil;alhos &eacute; adequada, ou em alternativa forne&ccedil;a um t&iacute;tulo no m&aacute;ximo com 80 caracteres (incluindo espa&ccedil;os).")||lable.equalsIgnoreCase("Por favor verifique se a vers&atilde;o abreviada do t&iacute;tulo gerada para os cabe&ccedil;alhos &eacute; adequada, ou em alternativa forne&ccedil;a um t&iacute;tulo no m&aacute;ximo com 80 caracteres (incluindo espa&ccedil;os)"))
		{
			int epos_query1=sb.indexOf("\">",pos_query+1);
			ForRunninglable=sb.substring(pos_query+"<!--<query id=\"".length(),epos_query);
			runningTitleQuery=true;
		}
		lable=lable.toUpperCase();
		if(lable.indexOf("<!--<QUERY ID=\"")!=-1)
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR] Missing </query> Tag in xml. Please revert this to Copy Editor to update xml file.");
					return false;
			}
			else{
			JOptionPane.showMessageDialog(null,"1 Error: Missing </query> Tag in xml.\n Please revert this to Copy Editor to update xml file.","Error",JOptionPane.YES_OPTION);
			System.exit(0);
			}
		}
		if((lable.indexOf("NO QUERIES.",0)!=-1))
		{
			check_No_Query=true;
			break;
		}
		else if(lable.indexOf("NO QUERIES",0)!=-1)
		{
			check_No_Query=true;
			break;
		}
		else
		{
			check_No_Query=false;
		}
	}
	else
	{
		if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR] Missing </query> Tag in xml. Please revert this to Copy Editor to update xml file.");
					return false;
			}
			else{
		JOptionPane.showMessageDialog(null,"2 Error: Missing </query> Tag in xml.\n Please revert this to Copy Editor to update xml file.","Error",JOptionPane.YES_OPTION);
		System.exit(0);
			}
	}

}

	if(check_No_Query==true)
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR]Xml file containing \"No Quries\" text."+
			"Note:  Xml file should not contain query (\" No Queries\").           Please update xml file.");
					return false;
			}
			else{
			JOptionPane.showMessageDialog(null,"Error: Xml file containing \"No Quries\" text.\n"+
			"Note:  Xml file should not contain query (\" No Queries\").\n            Please update xml file.","Error",JOptionPane.YES_OPTION);
			System.out.println("No Queries found in xml.");
			System.exit(0);
			}

		}
//************************[PDF Diff ]***********************
//Date : 26/03/2008
/*
temp=temp.replaceAll("ThomsonDiff\\\\_","\\\\");
			temp=temp.replaceAll("ThomsonDiffOpenBrace","{");
			temp=temp.replaceAll("ThomsonDiffCloseBrace","}");
*/
pos_query=0;
end_e_pos=0;
rename_main_xml_file=sb.toString();
//System.out.println("rename_main_xml_file "+rename_main_xml_file);
//rename_main_xml_file=rename_main_xml_file.replaceAll("<ce:link locator=\"fx([0-9]+)\"/><!--<Diff id=\"([0-9]+)\"/>--></ce:figure></ce:display>","<ce:link locator=\"fx$1\"/></ce:figure></ce:display><!--<Diff id=\"$2\"/>-->");
//rename_main_xml_file=rename_main_xml_file.replaceAll("<!--<Diff id=\"([0-9]+)\"/>-->([0-9]+)</sb:last-page>","$2</sb:last-page><!--<Diff id=\"$1\"/>-->");
rename_main_xml_file=rename_main_xml_file.replaceAll("<!--<Diff id=\"([0-9]+)\"/>--><body>","<body><ce:salutation><!--<Diff id=\"$1\"/>-->");


//System.out.println("rename_main_xml_file "+rename_main_xml_file);
sb=new StringBuffer();
sb=sb.append(rename_main_xml_file);
while((pos_query=sb.indexOf("<!--<Diff id=\"",pos_query))!= -1)
{
	int epos_query=sb.indexOf("\"/>-->",pos_query+1);
	String lable=sb.substring(pos_query+"<!--<Diff id=\"".length(),epos_query);
	sb=sb.replace(pos_query,epos_query+"\"/>-->".length(),"ThomsonDiff_ThomsonDiffOpenBrace"+lable+"ThomsonDiffCloseBrace");
	
}
//System.out.println("1 XT Calling .... "+sb);
//***********************************************************
pos_query=0;
end_e_pos=0;
rename_main_xml_file=sb.toString();
//System.out.println("check_No_Query : "+check_No_Query);
if(check_No_Query==false)
{
	/*if(JD.equalsIgnoreCase("SAA")||JD.equalsIgnoreCase("SNB")||JD.equalsIgnoreCase("RECYCL")||JD.equalsIgnoreCase("ENDEND"))
	{
		while((pos_query=sb.indexOf("<!--<query id=\"Q",pos_query))!= -1)
		{
			int epos_query=sb.indexOf("\">",pos_query+1);
			String lable=sb.substring(pos_query+"<!--<query id=\"Q".length(),epos_query);
			int s1=sb.indexOf("</query>-->",pos_query+1);
			if(s1 !=-1)
			{
				sb=sb.replace(pos_query,s1+"</query>-->".length(),"\\protect\\qtoa{"+lable+"}");
				//System.out.println("sbbbbbbbbbbbbbbbbb   :"+sb);
			}
			else
			{
				JOptionPane.showMessageDialog(null,"3 Error: Missing </query> Tag in xml.\n Please revert this to Copy Editor to update xml file.","Error",JOptionPane.YES_OPTION);
				System.exit(0);
			}
		}
	}
	else*/
	{		
		while((pos_query=sb.indexOf("<!--<query id=\"",pos_query))!= -1)
		{
			int epos_query=sb.indexOf("\">",pos_query+1);
			String lable=sb.substring(pos_query+"<!--<query id=\"".length(),epos_query);			
			int s1=sb.indexOf("</query>-->",pos_query+1);
			if(s1 !=-1)
			{		
				if(runningTitleQuery==true)
				{
					sb=sb.replace(pos_query,s1+"</query>-->".length(),"");
					titlequery="\\protect\\qtoa{"+ForRunninglable+"}";
					runningTitleQuery=false;
					ForRunninglable="";
				}
				else
				{
					sb=sb.replace(pos_query,s1+"</query>-->".length(),"\\protect\\qtoa{"+lable+"}");
					
				}
			}
			else
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR]Missing </query> Tag in xml. Please revert this to Copy Editor to update xml file.");
						return false;
				}
				else{
				JOptionPane.showMessageDialog(null,"3 Error: Missing </query> Tag in xml.\n Please revert this to Copy Editor to update xml file.","Error",JOptionPane.YES_OPTION);
				System.exit(0);
				}
			}
		}
	}
}
else
{
	while((pos_query=sb.indexOf("<!--<query id=\"",pos_query))!= -1)
	{
		int s1=sb.indexOf("</query>-->",pos_query+1);
		sb=sb.replace(pos_query,s1+"</query>-->".length(),"");
	
	}
}
//<!--<city/>-->
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--<city/>-->",pos_query))!= -1)
{	
	sb=sb.replace(pos_query,pos_query+"<!--<city/>-->".length(),"");
}


//<!--<city>-->
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--<city>-->",pos_query))!= -1)
{
	int epos_query=sb.indexOf("<!--</city>-->",pos_query+1);
	if(epos_query!=-1){
		String lable=sb.substring(pos_query+"<!--<city>-->".length(),epos_query);
		int s1=sb.indexOf("<!--</city>-->",pos_query+1);
		sb=sb.replace(pos_query,s1+"<!--</city>-->".length(),lable);
	}else{
		int sp = sb.indexOf("<!--<city>-->",pos_query);
		sb=sb.replace(pos_query,sp+"<!--</city>-->".length(),"");
		System.out.println("***************************************************************************");
		System.out.println("Closing tag of <!--</city>--> is missing or incorrect in XML, Please check.");
		System.out.println("***************************************************************************");
		System.exit(0);
	}
}
//<!--<state>-->
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--<state>-->",pos_query))!= -1)
{
	int epos_query=sb.indexOf("<!--</state>-->",pos_query+1);
	String lable=sb.substring(pos_query+"<!--<state>-->".length(),epos_query);
	int s1=sb.indexOf("<!--</state>-->",pos_query+1);
	sb=sb.replace(pos_query,s1+"<!--</state>-->".length(),lable);

}

//<!--<country>-->
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--<country>-->",pos_query))!= -1)
{
	int epos_query=sb.indexOf("<!--</country>-->",pos_query+1);
	String lable=sb.substring(pos_query+"<!--<country>-->".length(),epos_query);
	int s1=sb.indexOf("<!--</country>-->",pos_query+1);
	sb=sb.replace(pos_query,s1+"<!--</country>-->".length(),lable);

}
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--<text-color>-->",pos_query))!= -1)
{
	sb=sb.replace(pos_query,pos_query+"<!--<text-color>-->".length(),"thomsonbeginTextColor");

}
pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<!--</text-color>-->",pos_query))!= -1)
{
	sb=sb.replace(pos_query,pos_query+"<!--</text-color>-->".length(),"thomsonendTextColor");

}

//*********************************************
//<ce:para role="question">
/**
	*[18/12/2007]
	* Added By Ravi
	* Change Point : Quel{} is added in case of ce:para role="question"
	* Change Request By : TPMS.
	*/
if(sb.indexOf("<ce:para id=\"",0)!=-1)
{
	
	String tqrole=sb.toString();
	tqrole=tqrole.replaceAll("<ce:para id=\"par([0-9]+)\" role=\"question\">","<ce:para role=\"question\">");
	sb=new StringBuffer(tqrole);
	
}


pos_query=0;
end_e_pos=0;
while((pos_query=sb.indexOf("<ce:para role=\"question\">",pos_query))!= -1)
{
	
	int epos_query=sb.indexOf("</ce:para>",pos_query);

	if(epos_query !=-1)
	{
		String label=sb.substring(pos_query+"<ce:para role=\"question\">".length(),epos_query);
		label="<ce:para>TPStartQuel"+label+"TPEndQuel</ce:para>";
		
		sb=sb.delete(pos_query,epos_query+"</ce:para>".length());
		sb=sb.insert(pos_query,label);
	}
	
}

//*********************************************


pos_query=0;
end_e_pos=0;
int m_pos=0;
//***************************************************

String Temp_Sb=xt.NewChageForNoIndent(sb.toString());
//****************************************************

//System.out.println("Temp_Sb :111: "+Temp_Sb);
//Converting Pagination Instructions
Pattern p1 = Pattern.compile("<!--([A-Z]+)##([^<>]+)-->",Pattern.DOTALL);
Matcher m1 = p1.matcher(Temp_Sb);
int pInsCount = 0;
while(m1.find())
{
	pInsCount++;
	Temp_Sb = Temp_Sb.replaceFirst("<!--([A-Z]+)##([^<>]+)-->", "\\\\instruction{Instruction "+pInsCount+"}{$2}");
}
	
//Temp_Sb=Temp_Sb.replaceAll("<!--([A-Z]+)##([^<>]+)-->", "\\\\floatanchor{$2}");
//System.out.println("Temp_Sb :222: "+Temp_Sb);

//******************[05/01/2008]*********************
Temp_Sb=xt.NewChageForColorTag(Temp_Sb);
//******************[21/11/2007]*********************
if(Temp_Sb.indexOf("<!--<cochrane_review>-->")!=-1){
	Temp_Sb = Temp_Sb.replaceFirst("<!--<cochrane_review>-->", "\r\n\\\\begin{Cochrane}\r\n");
	Temp_Sb = Temp_Sb.replaceAll("<!--<cochrane_review>-->", "");
}
if(Temp_Sb.indexOf("<!--</cochrane_review>-->")!=-1){
	int spos = Temp_Sb.lastIndexOf("<!--</cochrane_review>-->");
	String Temp_Sb_first = Temp_Sb.substring(0, spos);
	String Temp_Sb_last = Temp_Sb.substring(spos, Temp_Sb.length());
	Temp_Sb_last = Temp_Sb_last.replace("<!--</cochrane_review>-->", "\r\n\\end{Cochrane}\r\n");
	Temp_Sb = Temp_Sb_first+Temp_Sb_last; 
	Temp_Sb = Temp_Sb.replaceAll("<!--</cochrane_review>-->", "");
}
Temp_Sb=xt.NewChageForCommentTag(Temp_Sb);

//System.out.println("Temp_Sb "+Temp_Sb);
//System.in.read();
//System.out.println("1 XT Calling .... "+sb);

sb=new StringBuffer(Temp_Sb);
//***************************************************
while((pos_query=sb.indexOf("<ce:inter-ref xlink:href=\"omim:",pos_query))!= -1)
{
	int epos_query=sb.indexOf("\">",pos_query+1);
	if(epos_query != -1)
	{
		String lable="";
		String label1=sb.substring(pos_query+"<ce:inter-ref xlink:href=\"omim:".length(),epos_query);
		//System.out.println("label1==> "+label1);
		m_pos=sb.indexOf("</ce:inter-ref>",epos_query);
		if(m_pos != -1)
		{
			lable=sb.substring(epos_query+"\">".length(),m_pos);
			if(lable.equals(label1))
			{
				if(lable.length()!= 6 )
				{
					if(serverstatus.equals("SERVER"))
					{
						if(dom.error.indexOf(".dtd",0)!=-1)
						{
							XTLogger.info("[ERROR] Parsing Error: Invalid Xml File.Message:  "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+" Contact to S/W Dept.");
						}
						else
							XTLogger.info("[ERROR] <ce:inter-ref xlink:href=\"omim:"+label1+"\">"+lable+"</ce:inter-ref> Must be in SIX digits. Revert to COPY EDITING DEPT.");
							return false;
					}
					else{
					JOptionPane.showMessageDialog(null,"<ce:inter-ref xlink:href=\"omim:"+label1+"\">"+lable+"</ce:inter-ref>\nMust be in SIX digits. Revert to COPY EDITING DEPT.","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
					}
				}
			}
			else
			{
				if(serverstatus.equals("SERVER"))
					{
						if(dom.error.indexOf(".dtd",0)!=-1)
						{
							server_Xt.xt_log.info("[ERROR] Parsing Error: Invalid Xml File.Message:  "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+" Contact to S/W Dept.");
						}
						else
						server_Xt.xt_log.info("[ERROR] Mismatch :  <ce:inter-ref xlink:href=\"omim:\""+label1+"\">\""+lable+"</ce:inter-ref> Revert to COPY EDITING DEPT.");
							return false;
					}
					else{
				JOptionPane.showMessageDialog(null,"Mismatch :  <ce:inter-ref xlink:href=\"omim:"+label1+"\">"+lable+"</ce:inter-ref>\nRevert to COPY EDITING DEPT.","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
					}
			}
			sb=sb.replace(pos_query,m_pos+"</ce:inter-ref>".length(),"\\MIM{"+lable+"}");
		}
	}

}

//***********************05/03/2009******************
String brTag=sb.toString();
brTag=brTag.replaceAll("<ce:br/>","thomsontablenewline ");
//System.out.println("1 XT Calling .... "+sb);

sb=new StringBuffer(brTag);
//System.out.println("2 XT Calling .... "+sb);

//-----------------------------------------------------
//****************************************
//fmcforall
if(isOrderPathAvailable.equalsIgnoreCase("yes")){
	if(AD.indexOf(".",0)!=-1)
	{
		AD=AD.substring(0,AD.indexOf(".",0));
	}
				ReadForEextra Readfmc= new ReadForEextra(JD.toUpperCase(),AD,stage);
					fmcforall=false;
					//System.out.println("RAVI....");
					if(stage.equalsIgnoreCase("S300"))
					{
						if(RSVP_Text.equalsIgnoreCase("RSVP"))
						{
							read="FFF";
						}
						if(NOFMC_comment)
						{
							fmcforall=false;
						}
						else
						{
							fmcforall=Readfmc.readOrderFileForFMC(read);
						}
					}
					else
					{
						//System.out.println("RAVI....");
						if(NOFMC_comment)
						{
							fmcforall=false;
						}
						else
						{
							fmcforall=Readfmc.readOrderFileForFMC();
						}
						//System.out.println("check_eextra RAVI...."+check_eextra);
					}
					
					//System.out.println("\nfmcforall  ravi   : "+fmcforall);

}

//**************[handled Italic Vee in Guliver Model]**************************
/*System.out.println("111111111111111");
if(sb.indexOf(" <ce:italic>v</ce:italic> ",0)!=-1)
		{
	System.out.println("22222222222222222");
			int pos=0;
			while((pos=sb.indexOf(" <ce:italic>v</ce:italic> ",pos))!= -1)
			{
				pos=sb.indexOf(" <ce:italic>v</ce:italic> ",pos);
				sb=sb.delete(pos,pos+" <ce:italic>v</ce:italic> ".length());
				sb=sb.insert(pos," GuliverItalicVee ");
			}
		}
*/

//*****************************
//System.out.println("XT Calling .... "+sb);
//******************************************************************************

Hashtable has2 = new Hashtable();
has2 = GetNewDTDCutOff();
String aid_temp1="";
String jid_temp1=JD.toUpperCase().trim();

//
// System.out.println(jid_temp+"----------0-------"+has1);
if(has2.containsKey(jid_temp1.toString()))
{
	aid_temp1=has2.get(jid_temp1).toString();
	//System.out.println("----------1-------"+aid_temp);
	//if(Integer.parseInt(xt.xmlObj.aid.toUpperCase().trim())<=Integer.parseInt(aid_temp))
	String t_aid=AD.trim();

	if(t_aid.indexOf(".")!=-1)
	{
		t_aid=t_aid.substring(0,t_aid.indexOf("."));
		//System.out.println("t_aid=====> "+t_aid);
	}
	if(Integer.parseInt(t_aid)>=Integer.parseInt(aid_temp1.trim()))
	{
		isNewDTDCutOff=true;
	}
}

//********************************************************************************
//*****************************


//**************************************

loadGulliverModel();//16/02/2008
String Ivee="";
boolean check_GulliverJid=false;
Ivee=sb.toString();
//System.out.println("Iveeee:::>>>"+Ivee);
String cutOffAid="";
XMLObjects xj =new XMLObjects(JD.trim().toUpperCase(),AD.trim().toUpperCase(),serverstatus);
xj.makeGulliverJidList();

//*********************[19/03/2009]**************************
xj.TableGulliverJidCutoffList();
//System.out.println(JD+"1 TablecutOffAid : "+xj.TableGuliverCutOff+ "Guliver_aid : "+AD);

//System.out.println("tt_aid=====> "+AD);
		String TablecutOffAid="";
		if(xj.TableGuliverCutOff.get(JD)!=null)
		{
			TablecutOffAid=xj.TableGuliverCutOff.get(JD).toString();
			String tt_aid=AD;

			if(tt_aid.indexOf(".")!=-1)
			{
				tt_aid=tt_aid.substring(0,tt_aid.indexOf("."));
				//System.out.println("tt_aid=====> "+tt_aid);
			}
			
			//System.out.println("ssssssssssssscutOffAid : "+tt_aid+ "Guliver_aid : "+TablecutOffAid);
			try{
				if(Integer.parseInt(TablecutOffAid.trim())<= Integer.parseInt(tt_aid)){
					//System.out.println("sssssssssssssss1 TablecutOffAid : "+TablecutOffAid+ "Guliver_aid : "+AD);
					isTableGulliverCutOff=true;
				
				}
				else
				{
					isTableGulliverCutOff=false;
				}
			}catch(Exception e)
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Number format Exception "+e.toString());
					return false;
				}
			}

			//System.out.println("1 isTableGulliverCutOff : "+isTableGulliverCutOff);
		}
	


//***********************[END 19/03/2009]*************************

//System.out.println(AD+"1 cutOffAid : "+cutOffAid+ "Guliver_aid : "+xj.GulliverJidList);
 if(xj.GulliverJidList.contains(JD))
		{
		
	 //System.out.println("2 cutOffAid : "+cutOffAid+ "Guliver_aid : "+xj.GuliverCutOff);
			cutOffAid=xj.GuliverCutOff.get(JD).toString();
			String tt_aid=AD;

			if(tt_aid.indexOf(".")!=-1)
			{
				tt_aid=tt_aid.substring(0,tt_aid.indexOf("."));
				//System.out.println("tt_aid=====> "+tt_aid);
			}
			//System.out.println("cutOffAid : "+cutOffAid+ "Guliver_aid : "+Guliver_aid);
			try{
			if(Integer.parseInt(cutOffAid.trim())<= Integer.parseInt(tt_aid)){
			//System.out.println("1 cutOffAid : "+cutOffAid+ "Guliver_aid : "+AD);
			check_GulliverJid=true;
			check_GulliverJid_For_NewMathSize=true;//05/02/2009
			
			}
			}catch(Exception e)
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR] Number format Exception "+e.toString());
					return false;
				}
				
			}
			
		}
//System.out.println("-----------------------------------"+check_GulliverJid);
if(check_GulliverJid==true){
Ivee=Ivee.replaceAll(" <ce:italic>v</ce:italic> "," thomsonGulliverItalicvee ");
Ivee=Ivee.replaceAll("([a-z A-Z])<ce:italic>v</ce:italic>([a-z A-Z])","$1thomsonGulliverItalicvee$2");
Ivee=Ivee.replaceAll("<ce:italic>v</ce:italic>([0-9])","thomsonGulliverItalicvee$1");
Ivee=Ivee.replaceAll("([+ - * /])<ce:italic>v</ce:italic>([+ - * /])","$1thomsonGulliverItalicvee$2");
Ivee=Ivee.replaceAll("([^a-z A-Z 0-9])<ce:italic>v</ce:italic>([^a-z A-Z 0-9])","$1thomsonGulliverItalicvee$2");
//Ivee=Ivee.replaceAll("<ce:italic>v</ce:italic>([&.*?;])","\\\\GulliverItalicvee$1");
Ivee=Ivee.replaceAll("<ce:italic>v</ce:italic>(<ce:sup>.*?</ce:sup>)","thomsonGulliverItalicvee$1");
Ivee=Ivee.replaceAll("<ce:italic>v</ce:italic>(<ce:sub>.*?</ce:sub>)","thomsonGulliverItalicvee$1");
Ivee=Ivee.replaceAll("([\\( \\[ \\{])<ce:italic>v</ce:italic>","$1thomsonGulliverItalicvee");
Ivee=Ivee.replaceAll("<ce:italic>v</ce:italic>([\\( \\[ \\{])","thomsonGulliverItalicvee$1");
Ivee=Ivee.replaceAll("([a-z])<ce:italic>v</ce:italic>","$1thomsonGulliverItalicvee");
Ivee=Ivee.replaceAll("([0-9])<ce:italic>v</ce:italic>","$1thomsonGulliverItalicvee");
Ivee=Ivee.replaceAll("(&alpha;|&beta;|&gamma;|&delta;|&epsiv;|&zeta;|&eta;|&theta;|&iota;|&kappa;|&lambda;|&mu;|&nu;|&xi;|&z.omicr;|&pi;|&rho;|&sigmav;|&sigma;|&tau;|&upsi;|&phiv;|&chi;|&psi;|&omega;)<ce:italic>v</ce:italic>","$1thomsonGulliverItalicvee");

//---------------------------bold and italic both
Ivee=Ivee.replaceAll(" <ce:italic><ce:bold>v</ce:bold></ce:italic> "," thomsonGulliverBoldItalicvee ");
Ivee=Ivee.replaceAll("([a-z A-Z])<ce:italic><ce:bold>v</ce:bold></ce:italic>([a-z A-Z])","$1thomsonGulliverBoldItalicvee$2");
Ivee=Ivee.replaceAll("<ce:italic><ce:bold>v</ce:bold></ce:italic>([0-9])","thomsonGulliverBoldItalicvee$1");
Ivee=Ivee.replaceAll("([+ - * /])<ce:italic><ce:bold>v</ce:bold></ce:italic>([+ - * /])","$1thomsonGulliverBoldItalicvee$2");
Ivee=Ivee.replaceAll("([^a-z A-Z 0-9])<ce:italic><ce:bold>v</ce:bold></ce:italic>([^a-z A-Z 0-9])","$1thomsonGulliverBoldItalicvee$2");
//Ivee=Ivee.replaceAll("<ce:italic><ce:bold>v</ce:bold></ce:italic>([&.*?;])","thomsonGulliverBoldItalicvee$1");
Ivee=Ivee.replaceAll("<ce:italic><ce:bold>v</ce:bold></ce:italic>(<ce:sup>.*?</ce:sup>)","thomsonGulliverBoldItalicvee$1");
Ivee=Ivee.replaceAll("<ce:italic><ce:bold>v</ce:bold></ce:italic>(<ce:sub>.*?</ce:sub>)","thomsonGulliverBoldItalicvee$1");
Ivee=Ivee.replaceAll("([\\( \\[ \\{])<ce:italic><ce:bold>v</ce:bold></ce:italic>","$1thomsonGulliverBoldItalicvee");
Ivee=Ivee.replaceAll("<ce:italic><ce:bold>v</ce:bold></ce:italic>([\\( \\[ \\{])","thomsonGulliverBoldItalicvee$1");
Ivee=Ivee.replaceAll("([a-z])<ce:italic><ce:bold>v</ce:bold></ce:italic>","$1thomsonGulliverBoldItalicvee");
Ivee=Ivee.replaceAll("(&alpha;|&beta;|&gamma;|&delta;|&epsiv;|&zeta;|&eta;|&theta;|&iota;|&kappa;|&lambda;|&mu|&nu;|&xi;|&z.omicr;|&pi;|&rho;|&sigmav;|&sigma;|&tau;|&upsi;|&phiv;|&chi;|&psi;|&omega;)<ce:italic><ce:bold>v</ce:bold></ce:italic>","$1thomsonGulliverBoldItalicvee");
Ivee=Ivee.replaceAll("([0-9])<ce:italic><ce:bold>v</ce:bold></ce:italic>","$1thomsonGulliverBoldItalicvee");

}
//System.out.println("XT Calling .... "+sb);
sb=new StringBuffer(Ivee);
File f_check_query= new File(args[0].substring(0,args[0].lastIndexOf("."))+".xml");
RandomAccessFile raf= new RandomAccessFile(f_check_query, "rw");
//System.out.println("Check File :::>>>"+f_check_query);

String HyPT=hypnPT.checkPThyphen(sb.toString());
System.out.println("Portuguese Hyphen process running...");
if(HyPT==null)
{
	System.out.println("\n[ERROR]: Problem in converting &hyphen; in \"PT\" language.\n");
	System.exit(0);
}
else
{
	//raf.writeBytes(sb.toString());
	raf.writeBytes(HyPT);
}
System.out.println("Portuguese Hyphen process finished...");
raf.close();
//System.in.read();	
		loadPlusModel();
		ExtrSecTitl bkm   = new ExtrSecTitl(args[0], databasePath + "pit.dbf");
		//System.out.println("11[Ravi] "+databasePath);
		bkm.processML();
		//System.out.println("[Ravi] "+databasePath);
		ArrayList bkmList= bkm.bkmList;
		//System.out.println("[Ravi] "+bkmList);
		xt.fin     = new RandomAccessFile(xt.xmlFile, "r");
		//System.out.println("figInfo : "+figInfo);
		xt.xmlObj  = new XMLObjects(xt.fin, figInfo, bkmList);
		
	
	
	
		//[Added By Ravi 21/12/2006]
		//Change Request By Vivek. 
		//Change Point: Abbr must be come at end of body sections
		xt.fin1     = new RandomAccessFile(xt.xmlFile, "r");
		//xt.fin2     = new RandomAccessFile(xt.xmlFile, "r");
		xt.fin2     = new RandomAccessFile(getALTEComponent(xt.xmlFile), "r");
		xt.xmlObj1  = new XMLObjects(xt.fin1, figInfo, bkmList);
		//end

		/*for(int bb=0; bb<bkmList.size(); bb++)
			System.out.println(bkmList.get(bb));*/
			
	
			
		String tag = "";
		StringBuffer artContents= new StringBuffer();
		HeadGroup xmlHead= new HeadGroup(xt.xmlObj);
		String ExecSum="";

        if(DuckCheck==false)
		{
	
			/*String line="";
			while((line=xt.fin2.readLine())!= null)
			{
				
				char ch1= (char)xt.fin1.read();
				if (ch1=='<')
				{
					tag= xt.xmlObj1.getTag().toUpperCase();				
				}
				System.out.println("tag "+tag);
				System.in.read();
				if (tag.equals("</CE:SECTIONS>"))
				{
					sectionNo++;
				}
				if(tag.equals("<CE:APPENDICES>") || tag.equals("<CE:ACKNOWLEDGMENT>"))
				{
					break;
				}
			}*/
			String line="";
			//while((line=xt.fin2.readLine())!= null)
		if(JD.equalsIgnoreCase("YDLD"))
		{
			//System.out.println("JD "+JD);
			
			do
			{
				
				char ch1= (char)xt.fin2.read();
				if (ch1=='<')
				{
					//tag= xt.xmlObj1.getTag().toUpperCase();				
					tag+=ch1;
				}
				else
				{
					tag+=ch1;
				}
				tag=tag.toUpperCase();
				//System.out.println("tag "+tag);
				//System.in.read();
				if (tag.equals("</CE:SECTIONS>"))
				{
					sectionNo++;
				}
				//</simple-article>
				//14-11-2007
				//MODIFIED BY RANJAN
				if(tag.equalsIgnoreCase("</BOOK-REVIEW>") || tag.equals("<CE:APPENDICES>") || tag.startsWith("<CE:ACKNOWLEDGMENT")|| tag.equals("</ARTICLE>")|| tag.equals("</SIMPLE-ARTICLE>"))
				{
					break;
				}
				if(ch1=='>')
				{
					tag="";
				}
			}while(true);
			
		}
			//System.out.println("sectionNo "+sectionNo);
			//System.in.read();
		}


		tag="";
		xt.fin2.close();
	//	System.out.println(":closed:");
//********************************************************

		while (!tag.equals("</ARTICLE>") && !tag.equals("</SIMPLE-ARTICLE>") && !tag.equals("</BOOK-REVIEW>")&& !tag.equals("</EXAM>"))
		{			
			char ch= (char)xt.fin.read();
			//System.out.print(ch);
			if (ch=='<')
			{
				tag= xt.xmlObj.getTag().toUpperCase();				
			}
			//System.out.println("tag==>"+tag);
			//System.in.read();
			if (tag.equals("<BOOK-REVIEW>") || tag.startsWith("<BOOK-REVIEW ")|| tag.startsWith("<ARTICLE")|| tag.startsWith("<SIMPLE-ARTICLE")|| tag.startsWith("<EXAM"))
			{		
				
				if(tag.startsWith("<ARTICLE"))
					xt.xmlObj.pit= xt.xmlObj.getAttributeValue(tag, "DOCSUBTYPE");
				else if(tag.startsWith("<SIMPLE-ARTICLE"))
					xt.xmlObj.pit= xt.xmlObj.getAttributeValue(tag, "DOCSUBTYPE");
				else if(tag.startsWith("<BOOK-REVIEW"))
				{
					xt.xmlObj.pit= xt.xmlObj.getAttributeValue(tag, "DOCSUBTYPE");
					if(xt.xmlObj.pit.equalsIgnoreCase("ERR"))
					{
						xt.xmlObj.pit="BRV";
						
					}
				}
				else if(tag.startsWith("<EXAM"))
				{
					xt.xmlObj.pit= xt.xmlObj.getAttributeValue(tag, "DOCSUBTYPE");
					if(xt.xmlObj.pit.equalsIgnoreCase("ERR"))
					{
						xt.xmlObj.pit="EXM";
						
					}
				}
				else
					xt.xmlObj.pit= xt.xmlObj.getAttributeValue(tag, "DOCSUBTYPE");
				
			}
			else if (tag.equals("<ITEM-INFO>"))
			{
				System.out.print("Processing Item Info \n");
				artContents.append(xt.processItemInfo());
				//System.out.print("Processing Item Info 1");
				/*if(tag.startsWith("CE:Glyph"))
					{
						System.out.println("tag  ----> " +tag);
						System.in.read();
					}
					*/
				//if(artContents.indexOf("\\refersdoi{")!=-1 || artContents.indexOf("\\referspii{")!=-1)  // 21-12-2004
					//xmlHead.chkRefersto=true;  // 21-12-2004
				xt.xmlObj.jid= xt.jid;
				xt.xmlObj.aid= xt.aid;
				xt.xmlObj.articleNo= xt.articleNo;
				xt.xmlObj.fntStyle=xt.fntStyle;
				//System.out.println("ModelStyle : "+xt.modelStyle);
				//System.out.println("PIT-"+xt.xmlObj.pit);
				
				if(xt.jid.equalsIgnoreCase("FUSPRU")&& xt.xmlObj.pit.equalsIgnoreCase("mis"))
				{
					if(jid.equalsIgnoreCase("FUSPRU"))//05-07-2010
					{
					String ad1=xt.aid;
					int in_pos=ad1.indexOf(".",0);
					if(in_pos !=-1)
					{
						ad1=ad1.substring(0,in_pos);
					}
					ReadForEextra rf_FUSPRU= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					
					if(rf_FUSPRU.readOrderFileForFUSPRU()==false)
						{
							xt.xmlObj.pit="COR";
						}
					}
					
				}
				//String spanishCheck=xt.makeSpanishJidList();
				String checkSpanish="";
				InputStream readSpanishFile=new FileInputStream(XT.databasePath + "SpanishJID.DBF");
				BufferedReader fin= new BufferedReader(new InputStreamReader(readSpanishFile));
				String lineStr="";
				while ((lineStr=fin.readLine())!=null)
				{
					if(lineStr.equalsIgnoreCase(jid)){
						if(xt.xmlObj.pit.equalsIgnoreCase("PGL"))
							xt.xmlObj.pit="FLA";
						break;
					}					
				}				
				/*Iterator itr=checkSpanishJidList.iterator();
				while(itr.hasNext())
				{
					checkSpanish=(String)itr.next();	
					if(xt.jid.equalsIgnoreCase(checkSpanish))
					{
						xt.xmlObj.pit="FLA";
						System.out.println("checkSpanish found true ");
					}
				}*/	
				
				if(xt.jid.equalsIgnoreCase("AGMET")||xt.jid.equalsIgnoreCase("EJRH"))
				{
					String diffRemove=getTagValue(xt.xmlFile,"ce:dochead");
					diffRemove=diffRemove.replaceAll("<ce:textfn>", "");
					diffRemove=diffRemove.replaceAll("</ce:textfn>", "");
					//System.out.println(" diffRemove:: "+diffRemove);
					diffRemove=diffRemove.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
					diffRemove=diffRemove.replaceAll("\\\\protect\\\\qtoa\\{Q([0-9]+)\\}","");
					//System.out.println(" diffRemove:: "+diffRemove);
					if(xt.xmlObj.pit.equalsIgnoreCase("PRP")&& (diffRemove.equalsIgnoreCase("Peer Review Report")))
					{
						//System.out.println("Dochead \"Peer Review Report\" found");
						xt.xmlObj.pit="EDI";
						pearreviewreport=true;
					}
				}
				
				if(xt.jid.equalsIgnoreCase("ARBRES") || xt.jid.equalsIgnoreCase("ARBR")){
					String diffRemove=getTagValue(xt.xmlFile,"ce:dochead");
					diffRemove=diffRemove.replaceAll("<ce:textfn>", "");
					diffRemove=diffRemove.replaceAll("</ce:textfn>", "");
					//System.out.println(" diffRemove:: "+diffRemove);
					diffRemove=diffRemove.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
					diffRemove=diffRemove.replaceAll("\\\\protect\\\\qtoa\\{Q([0-9]+)\\}","");
					//System.out.println(" diffRemove:: "+diffRemove);
					if(xt.xmlObj.pit.equalsIgnoreCase("COR")&& (diffRemove.toLowerCase().startsWith("carta cient") && diffRemove.toLowerCase().endsWith("fica")))
					{
						System.out.println(xt.jid+", COR and Carta científica found, item to be converted as COMPACT item");
					}else{
						System.out.println(xt.jid+" and PIT COR found and dochead is not as \"Carta científica\", item to be converted as FULL-LENGTH item");
						xt.xmlObj.pit="FLA";
					}
				}

				
				//Condition added on 30-05-2015 for pit CRP to COR in conversion for list of Spanish JIDs and docheads
				if(xt.xmlObj.pit.equalsIgnoreCase("CRP"))
				{
					System.out.println("PIT \"CRP\" found for jid \""+xt.jid+"\"");
					String docheadWithoutQueryAndDiff=getTagValue(xt.xmlFile,"ce:dochead");
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.replaceAll("<ce:textfn>", "");
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.replaceAll("</ce:textfn>", "");
					System.out.println("docheadWithQuery:: "+docheadWithoutQueryAndDiff);
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.replaceAll("ThomsonDiff\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.replaceAll("\\\\protect\\\\qtoa\\{Q([0-9]+)\\}","");
					System.out.println("docheadWithoutQuery:: "+docheadWithoutQueryAndDiff);
					docheadWithoutQueryAndDiff=docheadWithoutQueryAndDiff.toLowerCase();
					
					if(((xt.jid.equalsIgnoreCase("ACMX") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("ACMX") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("RGMX") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RGMX") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("RGMXEN") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("RARD") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RCHIPE") && docheadWithoutQueryAndDiff.equals("brief communication"))) ||
					   ((xt.jid.equalsIgnoreCase("RCHIPE") && docheadWithoutQueryAndDiff.startsWith("comunicaci") && docheadWithoutQueryAndDiff.endsWith("n breve"))) ||
					   //((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.startsWith("carta cient&iacute;fico-cl&iacute;nica"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.indexOf("fico-cl")!=-1 && docheadWithoutQueryAndDiff.endsWith("nica"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.equals("case and research letter"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.equals("case for diagnosis"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.startsWith("casos para el diagn") && docheadWithoutQueryAndDiff.endsWith("stico"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.startsWith("dermatoscopia pr") && docheadWithoutQueryAndDiff.endsWith("ctica"))) ||
					   ((xt.jid.equalsIgnoreCase("AD") && docheadWithoutQueryAndDiff.equals("practical dermoscopy"))) ||
					   ////Jid ADENGL added on 12-02-2022
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.indexOf("fico-cl")!=-1 && docheadWithoutQueryAndDiff.endsWith("nica"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("case and research letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("case for diagnosis"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.startsWith("casos para el diagn") && docheadWithoutQueryAndDiff.endsWith("stico"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.startsWith("dermatoscopia pr") && docheadWithoutQueryAndDiff.endsWith("ctica"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("practical dermoscopy"))) ||
					   ((xt.jid.equalsIgnoreCase("ALLER") && docheadWithoutQueryAndDiff.equals("research letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ANPEDI") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   //((xt.jid.equalsIgnoreCase("AVDIAB") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   //((xt.jid.equalsIgnoreCase("AVDIAB") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("CARCOR") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("CIRUGI") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("EIMC") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("EIMC") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("EIMCE") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("EIMCE") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDONU") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDONU") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDINU") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDINU") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("GASTRO") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("GASTRO") && docheadWithoutQueryAndDiff.equals("scientific letters"))) ||
					   ((xt.jid.equalsIgnoreCase("GASTRE") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("MEDCLI") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("MEDIN") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("MEDIN") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("NEFRO") && docheadWithoutQueryAndDiff.startsWith("carta al director: casos cl") && docheadWithoutQueryAndDiff.endsWith("nicos breves"))) ||
					   ((xt.jid.equalsIgnoreCase("NEFRO") && docheadWithoutQueryAndDiff.startsWith("carta al director: comunicaciones breves de investigaci") && docheadWithoutQueryAndDiff.indexOf("n o experiencias cl")!=-1 && docheadWithoutQueryAndDiff.endsWith("nicas"))) ||
					   ((xt.jid.equalsIgnoreCase("OPTOM") && docheadWithoutQueryAndDiff.equals("scientific letters"))) ||
					   ((xt.jid.equalsIgnoreCase("BJPT") && docheadWithoutQueryAndDiff.equals("scientific letters"))) ||
					   ((xt.jid.equalsIgnoreCase("PIEL") && docheadWithoutQueryAndDiff.startsWith("carta cl") && docheadWithoutQueryAndDiff.endsWith("nica"))) ||
					   ((xt.jid.equalsIgnoreCase("PIEL") && docheadWithoutQueryAndDiff.startsWith("caso para el diagn") && docheadWithoutQueryAndDiff.endsWith("stico"))) ||
					   ((xt.jid.equalsIgnoreCase("PIEL") && docheadWithoutQueryAndDiff.startsWith("caso para el diagn") && docheadWithoutQueryAndDiff.indexOf("stico. soluci")!=-1 && docheadWithoutQueryAndDiff.endsWith("n"))) ||
					   ((xt.jid.equalsIgnoreCase("RECESP") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RECOT") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("REGG") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RPSM") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RX") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("SEMERG") && docheadWithoutQueryAndDiff.startsWith("carta cl") && docheadWithoutQueryAndDiff.endsWith("nica"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("case and research letters"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("case for diagnosis"))) ||
					   ((xt.jid.equalsIgnoreCase("ADENGL") && docheadWithoutQueryAndDiff.equals("practical dermoscopy"))) ||
					   ((xt.jid.equalsIgnoreCase("ANPEDE") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("CIRENG") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDOEN") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ENDIEN") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||					   
					   ((xt.jid.equalsIgnoreCase("MEDCLE") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("NEFROE") && docheadWithoutQueryAndDiff.equals("letters to the editor - brief case reports"))) ||
					   ((xt.jid.equalsIgnoreCase("NEFROE") && docheadWithoutQueryAndDiff.equals("letters to the editor - brief papers about basic research or clinical experiences"))) ||
					   ((xt.jid.equalsIgnoreCase("REC") && docheadWithoutQueryAndDiff.equals("scientific letter"))) ||
					   ((xt.jid.equalsIgnoreCase("ARBR") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
//					   ((xt.jid.equalsIgnoreCase("ARBRES") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||//Condition changed for ARBRES on 25-10-2021
					   ((xt.jid.equalsIgnoreCase("ARBRES") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica")) && xt.xmlObj.pit.equalsIgnoreCase("COR")) ||
					   ((xt.jid.equalsIgnoreCase("OPRESP") && docheadWithoutQueryAndDiff.startsWith("carta cient") && docheadWithoutQueryAndDiff.endsWith("fica"))) ||
					   ((xt.jid.equalsIgnoreCase("RPSMEN") && docheadWithoutQueryAndDiff.equals("scientific letter")))// ||
					   //((xt.jid.equalsIgnoreCase("CIRCV") && docheadWithoutQueryAndDiff.startsWith("caso cl") && docheadWithoutQueryAndDiff.endsWith("nico"))) ||
					   //((xt.jid.equalsIgnoreCase("CIRCV") && docheadWithoutQueryAndDiff.equals("Case report")))
					   )
					{
						System.out.println("Dochead \""+docheadWithoutQueryAndDiff+"\" found");
						xt.xmlObj.pit="COR";
//						artContents.append("\r\n%%CRPTOCOR\r\n");
						if(artContents.indexOf("\\docsubtype{CRP}")!=-1){
							artContents.insert(artContents.indexOf("\\docsubtype{CRP}") + "\\docsubtype{CRP}".length(), "\r\n%%CRPTOCOR");
						}
						pearreviewreport=true;
					}
				}
				if(xt.jid.equalsIgnoreCase("JARMAC")){
					if(xt.xmlObj.pit.equalsIgnoreCase("COR")){
						orgpit = "COR";
						xt.xmlObj.pit="FLA";
					}else if(xt.xmlObj.pit.equalsIgnoreCase("EDI")){
						orgpit = "EDI";
						xt.xmlObj.pit="FLA";
					}else if(xt.xmlObj.pit.equalsIgnoreCase("CRP")){
						orgpit = "CRP";
						xt.xmlObj.pit="FLA";
					}
				}
				if(xt.jid.equalsIgnoreCase("RCCL")){//Added on 23-01-2019
					if(xt.xmlObj.pit.equalsIgnoreCase("CRP")){
						orgpit = "CRP";
						xt.xmlObj.pit="COR";
					}
				}
				if(xt.jid.equalsIgnoreCase("ABD")){//Added on 23-01-2019
					if(xt.xmlObj.pit.equalsIgnoreCase("SCO")){
						String docheadWithoutQueryAndDiff=getTagValue(xt.xmlFile,"ce:dochead");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("<ce:textfn>", "");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("</ce:textfn>", "");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
						System.out.println("docheadWithoutQueryAndDiff :: "+docheadWithoutQueryAndDiff);
						if(docheadWithoutQueryAndDiff.equalsIgnoreCase("Research Letter") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Case Letter")){
							orgpit = "SCO";
							xt.xmlObj.pit="COR";
						}
					}
				}
				if(xt.jid.equalsIgnoreCase("ABDP")){//Added on 23-01-2019
					if(xt.xmlObj.pit.equalsIgnoreCase("SCO")){
						String docheadWithoutQueryAndDiff=getTagValue(xt.xmlFile,"ce:dochead");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("<ce:textfn>", "");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("</ce:textfn>", "");
						docheadWithoutQueryAndDiff = docheadWithoutQueryAndDiff.replaceAll("ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "");
						System.out.println("docheadWithoutQueryAndDiff :: "+docheadWithoutQueryAndDiff);
						if(docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta - Caso cl&iacute;nico") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta-Caso cl&iacute;nico") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta &ndash; Caso cl&iacute;nico") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta&ndash;Caso cl&iacute;nico") || 
							docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta - Investiga&ccedil;&atilde;o") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta-Investiga&ccedil;&atilde;o") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta &ndash; Investiga&ccedil;&atilde;o") || docheadWithoutQueryAndDiff.equalsIgnoreCase("Carta&ndash;Investiga&ccedil;&atilde;o")){
							orgpit = "SCO";
							xt.xmlObj.pit="COR";
						}
					}
				}
/*				if(xt.jid.equalsIgnoreCase("YDLD"))//Condition blocked on 04-01-2018 by TeXR&D, as the journal is fully standard now.
				{
					if(!xt.xmlObj.pit.equalsIgnoreCase("COR")&&!xt.xmlObj.pit.equalsIgnoreCase("BRV"))
						xt.xmlObj.pit="FLA";					
			
				}
*/
				if(xt.jid.equalsIgnoreCase("BIOET")&&(xt.xmlObj.pit.equalsIgnoreCase("EDI")))
				{
					xt.xmlObj.pit="FLA";
				}
				if(xt.jid.equalsIgnoreCase("ZEFQ")&&( xt.xmlObj.pit.equalsIgnoreCase("MIS")||xt.xmlObj.pit.equalsIgnoreCase("ABS")||xt.xmlObj.pit.equalsIgnoreCase("DIS")||xt.xmlObj.pit.equalsIgnoreCase("ANN")||xt.xmlObj.pit.equalsIgnoreCase("CNF")||xt.xmlObj.pit.equalsIgnoreCase("NWS")||xt.xmlObj.pit.equalsIgnoreCase("PRP")||xt.xmlObj.pit.equalsIgnoreCase("PUB")||xt.xmlObj.pit.equalsIgnoreCase("SCO")||xt.xmlObj.pit.equalsIgnoreCase("SSU")||xt.xmlObj.pit.equalsIgnoreCase("LIT")||xt.xmlObj.pit.equalsIgnoreCase("ERR")))
				{
					xt.xmlObj.pit="EDI";
				}
				if(xt.jid.equalsIgnoreCase("ENDOMX")&&(xt.xmlObj.pit.equalsIgnoreCase("PRV")))
				{
					xt.xmlObj.pit="FLA";
				}
				if(chekModelDAndModelEStatus.chekJidAid(jid, xt.aid) &&(xt.xmlObj.pit.equalsIgnoreCase("EXM")))
				{
					orgpit = "EXM";
					xt.xmlObj.pit="FLA";
				}
				if(xt.xmlObj.pit.equalsIgnoreCase("PRO"))
				{
					xt.xmlObj.pit="FLA";
				}
				if(xt.xmlObj.pit.equalsIgnoreCase("INS"))
				{
					xt.xmlObj.pit="EDI";
				}
				if(xt.xmlObj.pit.equalsIgnoreCase("EOC"))
				{
					xt.xmlObj.pit="EDI";
				}
				
				if((xt.jid.equalsIgnoreCase("ENFI")||xt.jid.equalsIgnoreCase("ENFIE"))&&(xt.xmlObj.pit.equalsIgnoreCase("EXM")))//06-12-2010
				{
					isENFI=true;
					xt.xmlObj.pit="FLA";
				}
				if(xt.xmlObj.pit.equalsIgnoreCase("MIS"))
				{
					//if((xt.modelStyle.equals("-MODDFrench"))||(xt.modelStyle.equals("-MODEFrench")))//08-04-2015
					if((xt.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(xt.modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
					{
						//JOptionPane.showMessageDialog(null,"IT IS MIS ARTICLE. SHOULD BE PAGINATED IN PAGINATION ONLY.","XT - Message", JOptionPane.INFORMATION_MESSAGE);
						//[INFORMATION]: IT IS MIS ARTICLE. PLEASE CHECK IT SHOULD BE PAGINATED IN PAGINATION OR IN COVERS.
						if(serverstatus.equals("SERVER"))
						{
							server_Xt.xt_log.info("[INFORMATION]: IT IS MIS ARTICLE. PLEASE CHECK IT SHOULD BE PAGINATED IN PAGINATION OR IN COVERS.");
							//return false;
						}
						else
						{
							int c=JOptionPane.showConfirmDialog(null,"IT IS MIS ARTICLE. SHOULD BE PAGINATED IN PAGINATION ONLY.\n\nDo you want to continue..\n\nPress \"OK\" button if \"YES\"\n\n","XT - Message", JOptionPane.OK_CANCEL_OPTION);

							//System.out.println("ch: "+ch);
							if(c!=0)
							System.exit(0);
						}
					}
					else
					{
						System.out.println("serverstatus::"+serverstatus);
						if(serverstatus.equals("SERVER"))
						{
							server_Xt.xt_log.info("[INFORMATION]: IT IS MIS ARTICLE. PLEASE CHECK IT SHOULD BE PAGINATED IN PAGINATION OR IN COVERS.");
							//return false;
						}
						else{
							int c=JOptionPane.showConfirmDialog(null,"IT IS A MIS ARTICLE. CHECK FOR COVERS.\n\nDo you want to continue..\n\nPress \"OK\" button if \"YES\"\n\n","XT - Message", JOptionPane.OK_CANCEL_OPTION);

							//System.out.println("ch: "+ch);
							if(c!=0)
							System.exit(0);
						}
					}
				}		
				
				
				//check e extra	in xml file.
				//System.out.println("XT Calling .... \n"+isOrderPathAvailable);
				//System.out.println("After change pit Value :: "+xt.xmlObj.pit);
				String ad2=xt.aid;
					//System.out.println("XT Calling .... "+ad1);
					int in_pos1=ad2.indexOf(".",0);
					if(in_pos1 !=-1)
					{
						ad2=ad2.substring(0,in_pos1);
					}
				ReadForEextra duckRFE= new ReadForEextra(xt.jid.toUpperCase(),ad2,stage);
				//System.out.println("isOrderPathAvailable: "+isOrderPathAvailable);
				if(isOrderPathAvailable.equalsIgnoreCase("yes"))
				{
					if(!stage.equalsIgnoreCase("S300"))
					xt.DucklingOrderStatus=	duckRFE.checkDuckling();
					
						/*String temp_resupply="";
						if(isDatabaseAvailable.equalsIgnoreCase("yes"))
						{
							Connect c2=new Connect();
							try
								{
									if(c2.dbConnect(driver,url))
									{
										temp_resupply =c2.CheckStageForResupply(xt.jid.toUpperCase(),xt.aid,stage);
										System.out.println("temp_resupply : "+temp_resupply);
									}

									c2.cleanupDbObjects();
								}
								catch(java.lang.ClassNotFoundException e) {			
									System.err.print("ClassNotFoundException: "); 
									System.err.println(e.getMessage());
								}
								catch(SQLException ex) {			
									System.err.println("SQLException: " + ex.getMessage());
									c2.cleanupDbObjects();
								}
						}
						if(temp_resupply.equalsIgnoreCase("yes"))
						{
							stage=stage+"RESUPPLY";
						}
						*/
						
					StringBuffer chceck_eExtra_File = new StringBuffer(sb.toString());
					String ad1=xt.aid;
					//System.out.println("XT Calling .... "+chceck_eExtra_File);
					int in_pos=ad1.indexOf(".",0);
					if(in_pos !=-1)
					{
						ad1=ad1.substring(0,in_pos);
					}
					
					ReadForEextra rfe= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					boolean check_eextra=false;
					if(stage.equalsIgnoreCase("S300"))
					{
						if(RSVP_Text.equalsIgnoreCase("RSVP"))
						{
							read="FFF";
						}
						check_eextra=rfe.readOrderFileForEextra(read);
					}
					else
					{
						//System.out.println("RAVI....");
						check_eextra=rfe.readOrderFileForEextra();
						//System.out.println("check_eextra RAVI...."+check_eextra);
					}
					label_Eappended=rfe.label;
					xt.eappend=check_eextra;
					//System.out.println("E-appended/e-extra ...."+xt.eappend);
					//System.out.println("label Eappended : "+chceck_eExtra_File);
					if(check_eextra==true)
					{
						int in=0;
						in=chceck_eExtra_File.indexOf("view=\"extended\"",0);
						if(in!=-1)
						{}
						else
						{
							if(serverstatus.equals("SERVER"))
							{
								if(dom.error.indexOf(".dtd",0)!=-1)
								{
									server_Xt.xt_log.info("[ERROR] Parsing Error: Invalid Xml File.Message:  "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+" Contact to S/W Dept.");
								}
								else
								server_Xt.xt_log.info("[ERROR]: JID: "+xt.jid.toUpperCase()+"           AID: "+xt.aid+" Article is E-Extra but e-extra content not found in xml. Please revert to COPY EDITING dept.");
								return false;
							}
							else{
								//System.out.println("XT Calling .... "+chceck_eExtra_File);
							JOptionPane.showMessageDialog(null,"JID: "+xt.jid.toUpperCase()+"           AID: "+xt.aid+"\nArticle is E-Extra but e-extra content not found in xml.\nPlease revert to COPY EDITING dept.\nSystem is going to exit...");
		    				System.exit(1);
							}
						}
						
					}
				}
				else
				{
					System.out.println("isOrderPathAvailable : "+isOrderPathAvailable);
				}
				//end
				
				
				
				
				
				System.out.println("... Ok");
			
				//if(xt.xmlObj.pit.equalsIgnoreCase("edi") && xt.xmlObj.jid.equalsIgnoreCase("pcd"))//abhay 04/08/2006
				//[Added By Ravi : 19/12/2006]
				//Change request by Vivek
				if(((xt.xmlObj.pit.equalsIgnoreCase("brv") || (xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xt.xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xt.xmlObj.pit.equalsIgnoreCase("EDI"))||(xt.xmlObj.pit.equalsIgnoreCase("PGL")) || (xt.xmlObj.pit.equalsIgnoreCase("REQ")) || (xt.xmlObj.pit.equalsIgnoreCase("EXM")) ||(xt.xmlObj.pit.equalsIgnoreCase("PRP"))||(xt.xmlObj.pit.equalsIgnoreCase("CNF"))||(xt.xmlObj.pit.equalsIgnoreCase("COR")) )&& xt.xmlObj.jid.equalsIgnoreCase("pcd") )
				{
						artContents = new StringBuffer(artContents.toString().replaceFirst("copyrightline\\{", "copyrightlinepcd{"));
				}
				
				//[Added By Ravi : 19/12/2006]
				//Change request by Vivek
				if(((xt.xmlObj.pit.equalsIgnoreCase("brv") || (xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xt.xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xt.xmlObj.pit.equalsIgnoreCase("EDI"))||(xt.xmlObj.pit.equalsIgnoreCase("PGL")) || (xt.xmlObj.pit.equalsIgnoreCase("REQ")) || (xt.xmlObj.pit.equalsIgnoreCase("EXM")) ||(xt.xmlObj.pit.equalsIgnoreCase("PRP"))||(xt.xmlObj.pit.equalsIgnoreCase("CNF"))||(xt.xmlObj.pit.equalsIgnoreCase("COR")) )&& xt.xmlObj.jid.equalsIgnoreCase("pcd") )
				{
					artContents = new StringBuffer(artContents.toString().replaceFirst("\\\\lDOInumber\\{", "\\\\lDOInumberpcd{"));
				}
				//end
				System.out.println("isDatabaseAvailable::>>"+isDatabaseAvailable);
				if(isDatabaseAvailable.equalsIgnoreCase("yes"))
				{
					if(stageDesc.length()>0)
					{

							Connect c=new Connect();

							try{
								if(c.dbConnect(driver,url))
								{
									try{
										
										//System.out.println(stoneQuery+ xt.jid+ xt.aid+ stageDesc);
										//ABHAY TO DO TOMBSTONE
										String tombstone = c.stoneQuery(stoneQuery, xt.jid, xt.aid, stageDesc);
										//System.out.println("tombstone Value::>>"+tombstone);
										if(tombstone.equalsIgnoreCase("RETRACTED") || tombstone.equalsIgnoreCase("DUPLICATE")&&(xt.xmlObj.pit.equalsIgnoreCase("DUP")||xt.xmlObj.pit.equalsIgnoreCase("RET")||xt.xmlObj.pit.equalsIgnoreCase("REM")))
										{
											if(serverstatus.equals("SERVER"))
											{
												server_Xt.xt_log.info("[ERROR]: This article is tombstone (RETRACTED/DUPLICATE). Please donot process it.");
												return false;
											}
											else{
												JOptionPane.showMessageDialog(null,"This article is tombstone (RETRACTED/DUPLICATE). Please donot process it.\nSystem exiting...","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
												System.exit(0);
											}
										}
										
										//System.out.println(xt.jid+" "+xt.aid+" "+stageDesc);
										if(jid.equalsIgnoreCase("ANIREP"))
										{
											String itemdesc = c.ANIREPQuery(anirepQuery, xt.jid, xt.aid, stageDesc);
											if(itemdesc != null && itemdesc.equalsIgnoreCase("ISER 2006 S.I."))
											{
												artContents.append("\r\n\\Please_Check_Layout_for_ABS_and_FOOTNOTE\r\n");
												//System.out.println("ANIREP - ISER 2006 S.I. --> \tPlease_Check_Layout_for_ABS");
												//System.out.println("artContents3 Values::>>"+artContents);
											}
										}

									

										onlineversiontype=c.OVQuery(onlineVersionQuery,xt.jid,xt.aid,stageDesc);
										//24-12-2008
										/*if(xt.xmlObj.jid.equalsIgnoreCase("OTSR"))
										{
											onlineversiontype="e-only";
										}*/
										if(checkDuckling)
										{
											isDuckling=c.DucklingQuery(ducklingQuery,xt.jid,xt.aid,stageDesc);
											//subitems=c.SUBITEMSQuery(subitemsQuery,xt.jid,xt.aid,stageDesc);
											//System.out.println("Duckling-->"+isDuckling);
											//DuckCheck
											//if(isDuckling.equals("Y"))
											//if(xt.DucklingOrderStatus==true&&(xt.aid.indexOf(".",0)==-1)&&DuckCheck==false)
											if(xt.DucklingOrderStatus==true&&(xt.aid.indexOf(".",0)==-1)&&DuckCheck==false&&!xt.jid.equalsIgnoreCase("ENDMAG"))
											{
												if(serverstatus.equals("SERVER"))
												{
													server_Xt.xt_log.info("[ERROR]: Use COMMAND: Duckling JID AID STAGE for Pagination  Else Use COMMAND: DnD JID AID STAGE for CopyEditing!!!");
													return false;
												}
												else{
													JOptionPane.showMessageDialog(null,"Use COMMAND: Duckling JID AID STAGE for Pagination\n Else Use COMMAND: DnD JID AID STAGE for CopyEditing!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
													c.cleanupDbObjects();
													System.exit(0);//My name is ravi 10/09/2008
												}
											}
										}
										c.cleanupDbObjects();
									}
									catch(java.lang.ClassNotFoundException e) {			
										System.err.print("ClassNotFoundException: "); 
										System.err.println(e.getMessage());
									}
									catch(SQLException ex) {			
										System.err.println("SQLException: " + ex.getMessage());
										c.cleanupDbObjects();
									}
									System.out.println("Database Connected");
									//System.out.println("Online Version Type-->"+onlineversiontype);

								}
								else
								{
									if(serverstatus.equals("SERVER"))
									{
										server_Xt.xt_log.info("[ERROR]: Database Not Connected!!!!!!");
										return false;
									}
									else{
										JOptionPane.showMessageDialog(null,"Database Not Connected!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
								}
							}
							catch(java.lang.ClassNotFoundException e) {			
								System.err.print("ClassNotFoundException: "); 
								System.err.println(e.getMessage());
									if(serverstatus.equals("SERVER"))
									{
										server_Xt.xt_log.info("[ERROR]: Database Not Connected!!!!!!");
										return false;
									}
									else{
										JOptionPane.showMessageDialog(null,"Database Not Connected!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
										System.exit(0);
									}
							}
							catch(SQLException ex) {			
								System.err.println("SQLException: " + ex.getMessage());
								if(serverstatus.equals("SERVER"))
									{
										server_Xt.xt_log.info("[ERROR]: Database Not Connected!!!!!!");
										return false;
									}
									else{
										JOptionPane.showMessageDialog(null,"Database Not Connected!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
										c.cleanupDbObjects();
										System.exit(0);
									}
							}						
					}
				}
			}
			else if (tag.equals("<HEAD>") || tag.equals("<SIMPLE-HEAD>") || tag.equals("<BOOK-REVIEW-HEAD>"))
			{
				System.out.println("Processing Head      ");
				artContents.append(xmlHead.getHeadContents());
				
				//Added on 14-08-2019 Graphical abstract for PHARMA
				if(xt.jid.equals("PHARMA")){
					String gaContent = artContents.toString();
					gaContent = gaContent.replaceAll("RTOPEN(.*)RTCLOSE", "");
					System.out.println("##################################################");
					System.out.println("gaContent :: "+gaContent);
					System.out.println("##################################################");
					if(gaContent.indexOf("\\startabstract{")!=-1){
						String gafirst;
						String galast;
						gafirst = gaContent.substring(0,gaContent.indexOf("\\startabstract{"));
						galast = gaContent.substring(gaContent.indexOf("\\makechaptertitle"),gaContent.length());
//						System.out.println("gafirst :: "+gafirst);
//						System.out.println("galast :: "+galast);
						String gAbstract = "\\GAbs{%\r\n"+HeadGroup.finalgraphabstractcontent_graphical+"}\r\n";
						gaContent = gafirst+"\n\n"+gAbstract+"\n\n"+galast;
						if(gaContent.indexOf("\\end{document}")!=-1){
//							System.out.println("\\end{document} found");
						}else{
//							System.out.println("\\end{document} not found and added.");
							gaContent+="\r\n\\end{document}";
						}
//						System.out.println("gAbstract :: "+ gAbstract);
					}
					if(HeadGroup.finalgraphabstractcontent_graphical.length()>0){
						RandomAccessFile grFile= new RandomAccessFile(xt.aid+"g.tex", "rw");
						grFile.writeBytes(gaContent);
						grFile.close();
					}
				}
				//Till here
				
				//System.out.println("artContents4 Values::>>"+artContents);
				/*if(artContents.indexOf("\\miscTitle{")!=-1 && artContents.indexOf("\\makechaptertitle")!=-1)
				{
					//added by mukesh on 13-10-08
					StringBuffer arrangeMiscTitle= new StringBuffer();
					String temp="";
					arrangeMiscTitle=new StringBuffer(artContents.toString());
					while(arrangeMiscTitle.toString().indexOf("\\miscTitle{")!=-1)
					{
						String xyz="";
						System.out.println("ggggggggg "+arrangeMiscTitle.toString().indexOf("\n"));
						xyz=arrangeMiscTitle.substring(arrangeMiscTitle.indexOf("\\miscTitle{"),arrangeMiscTitle.indexOf("\n",arrangeMiscTitle.indexOf("\\miscTitle{")));
						//System.out.println("XYZ  :"+xyz);
						artContents=artContents.delete(artContents.toString().indexOf(xyz),artContents.toString().indexOf(xyz)+xyz.length());
						temp=temp+xyz;
						arrangeMiscTitle=arrangeMiscTitle.replace(arrangeMiscTitle.toString().indexOf("\\miscTitle{"),arrangeMiscTitle.toString().indexOf("\\miscTitle{")+11,"\\DoneMisc{");
						//arrangeMiscTitle= new StringBuffer(arrangeMiscTitle.toString().replaceFirst("\\miscTitle{","\\DoneMisc"));
					}
					artContents=artContents.insert(artContents.indexOf("\\makechaptertitle")+17,"\n"+temp);
				}*/
				// mukesh 16-09-08 onecolumn required if title matches-for YICCN
				//System.out.print("artContents:::::::::::"+arrangeMiscTitle);
				//System.out.println("Mukesh   :"+HeadGroup.yiccnTitle);
				//System.out.println("----------------------------"+xt.xmlObj.jid.equals("YICCN"));
				if(!(HeadGroup.yiccnTitle) && (xt.xmlObj.jid.equals("YICCN")))
				{
					//System.out.println("HeadGroup.yiccnTitle is   "+HeadGroup.yiccnTitle);
					artContents=new StringBuffer(artContents.toString().replaceFirst(",onecolumn",""));
					//System.out.println("artContents5 Values::>>"+artContents);
				}
				
				//*************************************************
					/**Date:[28/05/2007]
					  *Added By : Ravi
					  * Change Request by: Vivek
				      * Change Point :  use "PageBalance" as an option of \documentclass 
					  * if the below condition are true:
						*Stage="S100-draft"
						*Model 3 to 7 including plus models
						*Article should not be in onecolumn.
				    */
					//System.out.println("modelStyle==> "+modelStyle);
					//System.in.read();
				//if(!modelStyle.equalsIgnoreCase("-YBJOM"))
				if(!modelStyle.substring(0,1).equalsIgnoreCase("-"))
				{
					if(!xt.xmlObj.jid.equals("RAMD"))
					{
						if((Integer.parseInt(modelStyle.substring(0,1))>2)&&(stage.equalsIgnoreCase("S100")))
						{
							String pagebalance=artContents.toString();
								   pagebalance=pagebalance.substring(0,pagebalance.indexOf("\\usepackage{",0));
								   //onecolumn.
								if(pagebalance.indexOf("onecolumn",0)== -1)
								{
									int in= 0;
									in= artContents.indexOf("Stage",0);
									if(in != -1)
									{
										artContents.insert(in,"PageBalance,");
									}
	
								}
							//System.out.println("modelStyle==============> "+modelStyle.substring(0,1)+"\n"+pagebalance);
						}
					}
			    }

//**********************************
/*
	Date: 27/11/2008
	Modyfy by:Ravi Shekhar
	Change point : If Gulliver item has cutoff point then use NewHeadMargin in document class
	Change Request By : TPMS
*/
Hashtable has = new Hashtable();
has = GetGulliverCutOff();
String aid_temp="";
String jid_temp=xt.xmlObj.jid.toUpperCase().trim();

//GetNewDTDCutOff
//System.out.println(jid_temp+"----------0-------"+has);
if(has.containsKey(jid_temp.toString()))
{
	aid_temp=has.get(jid_temp).toString();
	//System.out.println("----------1-------"+aid_temp);
	//if(Integer.parseInt(xt.xmlObj.aid.toUpperCase().trim())<=Integer.parseInt(aid_temp))
	String t_aid=xt.xmlObj.aid.toUpperCase().trim();

	if(t_aid.indexOf(".")!=-1)
	{
		t_aid=t_aid.substring(0,t_aid.indexOf("."));
		//System.out.println("t_aid=====> "+t_aid);
	}
	if(Integer.parseInt(t_aid)>=Integer.parseInt(aid_temp.trim()))
	{
		//System.out.println("----------2-------"+aid_temp);
		artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "NewHeadMargin,");
		//System.out.println("artContents6 Values::>>"+artContents);
	}
}
if(pearreviewreport){
	artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "POSTREVIEW,");
}
if(check_GulliverJid_For_NewMathSize==true)
{
	check_GulliverJid_For_NewMathSize=false;
	artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "NewMathSize,");
	
	//System.out.println("artContents6 Values::>>"+artContents);
}

if((xt.xmlObj.jid.equals("MEDIN"))&&(xt.xmlObj.pit.equalsIgnoreCase("SCO"))&&(HeadGroup.artDochead.equalsIgnoreCase("Im{\\ASaacute}genes en Medicina Intensiva")||HeadGroup.artDochead.equalsIgnoreCase("Imagen en Medicina Intensiva")||HeadGroup.artDochead.equalsIgnoreCase("Images in Intensive Medicine")))//04/11/2010
{
	//System.out.println("1 tbCap : "+HeadGroup.artDochead);
	artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "onecolumn,");
	
	//System.out.println("artContents7 Values::>>"+artContents);
}
//System.out.println("============>>"+isCMEInOrder);
if(isCMEInOrder)//27-01-2012
{
	artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "CMELogo,");
	artContents.insert(artContents.indexOf("\\makechaptertitle") + "\\makechaptertitle".length()+1, "\r\n\\Titlefootnote[\\CMEiconFN]{To answer online CME questions relating to this paper, please visit the journal website at \\mychar\\url{http://www.bjmsu.com/}{www.bjmsu.com}{}  and follow links to `CME'.}\r\n");
}
//*****************************************************


//************************************Marke tag added *********[12/09/2007]**************
if(xt.xmlObj.jid.equals("REMNGL")||xt.xmlObj.jid.equals("REMNIE"))
{
	int spos=0;
	int epos=0;
	
	spos=artContents.indexOf("\\documentclass[",epos);
	if(spos!=-1)
	{
		epos=artContents.indexOf("STAMP,",spos);
		if(epos!=-1)
		{
			artContents=artContents.delete(epos,epos+"STAMP,".length());
		}
	}
}

if(xt.jid.equalsIgnoreCase("YBJOM"))
{
	String add2=xt.aid;
	//System.out.println("XT Calling .... "+ad1);
	int in_pos1=add2.indexOf(".",0);
	if(in_pos1 !=-1)
	{
		add2=add2.substring(0,in_pos1);
	}
	ReadForEextra red= new ReadForEextra(xt.jid.toUpperCase(),add2,stage);
	//System.out.println("isOrderPathAvailable: "+isOrderPathAvailable);
	if(isOrderPathAvailable.equalsIgnoreCase("yes"))
	{
		if(!stage.equalsIgnoreCase("S300"))
		if(red.readOrderFileForOBSForYBJOM())
		{
			if(artContents.indexOf("\\begin{document}",0)!=-1)
			{
				artContents.insert(artContents.indexOf("\\begin{document}",0),"\\Journal_Oracle_Item\r\n");
			}
		}
	}

}

if(XT.S250ABP.size()>0){
//System.out.println("===========>>"+XT.S250ABP);
if(XT.S250ABP.get("firstpage")!=null)
{
	String firstpage=XT.S250ABP.get("firstpage").toString();
	if(XT.S250ABP.get("s250-sequence-number").toString()!=null && XT.S250ABP.get("s250-sequence-number").toString().length()>0)
	{
		String s250_sequence_number=XT.S250ABP.get("s250-sequence-number").toString();

		if(firstpage.length()>0){
			if((s250_sequence_number!=null && !s250_sequence_number.isEmpty()) && (Integer.parseInt(s250_sequence_number)==1 && Integer.parseInt(firstpage)!=1 && (XT.S250ABP.get("issfrom").equals("1")||(XT.S250ABP.get("supp")!=null && XT.S250ABP.get("issfrom").equals("0")))))
			{
				if(XT.S250ABP.get("issfrom").equals("1"))
				{
					artContents.insert(artContents.indexOf("\\begin{document}",0),"\\Check_Sequence_Number\r\n"+"\\Check_Sequence_Number [INFORMATION]: Sequence no. of this item is "+s250_sequence_number+".\n\\Check_Sequence_Number This is the First item of Volume/Issue "+XT.S250ABP.get("volume")+"("+XT.S250ABP.get("issfrom")+")\n\\Check_Sequence_Number The first page should be 1 only.\n\\Check_Sequence_Number But the first page in order is "+firstpage+".\n\n\\Check_Sequence_Number WRONG XML ORDER\n\n\\Check_Sequence_Number PLEASE CHECK WITH PPC PERSON.\r\n\r\n");
				}
				else if(XT.S250ABP.get("supp")!=null)
				{
					artContents.insert(artContents.indexOf("\\begin{document}",0),"\\Check_Sequence_Number\r\n"+"\\Check_Sequence_Number [INFORMATION]: Sequence no. of this item is "+s250_sequence_number+".\n\\Check_Sequence_Number This is the First item of Volume/Supplimentry "+XT.S250ABP.get("volume")+"("+XT.S250ABP.get("supp")+")\n\\Check_Sequence_Number The first page should be 1 only.\n\\Check_Sequence_Number But the first page in order is "+firstpage+".\n\n\\Check_Sequence_Number WRONG XML ORDER\n\n\\Check_Sequence_Number PLEASE CHECK WITH PPC PERSON.\r\n\r\n");
				}

				artContents.insert(artContents.indexOf("\\Check_Sequence_Number\r\n",0),"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n");
				artContents.insert(artContents.indexOf("\r\n\\begin{document}",0),"%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\r\n\r\n");
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[INFORMATION]: Sequence no. of this item is "+s250_sequence_number+". This is the First item of Volume "+XT.S250ABP.get("volume")+" The first page should be 1 only. But the first page in order is "+firstpage+". WRONG XML ORDER. PLEASE CHECK WITH PPC PERSON.");
					//return false;
				}
				else
				{
					int c=-1;
					if(XT.S250ABP.get("issfrom").equals("1"))
					{
						c=JOptionPane.showConfirmDialog(null,"Sequence no. of this item is "+s250_sequence_number+".\nThis is the First item of Volume/Issue "+XT.S250ABP.get("volume")+"("+XT.S250ABP.get("issfrom")+")\nThe first page should be 1 only.\nBut the first page in order is "+firstpage+".\n\nWRONG XML ORDER.\n\nPLEASE CHECK WITH PPC PERSON. \n\nDo you want to continue..\n\nPress \"OK\" button if \"YES\"\n\n","XT - Message", JOptionPane.OK_CANCEL_OPTION);
					}
					else if(XT.S250ABP.get("supp")!=null)
					{
						c=JOptionPane.showConfirmDialog(null,"Sequence no. of this item is "+s250_sequence_number+".\nThis is the First item of Volume/Supplimentry "+XT.S250ABP.get("volume")+"("+XT.S250ABP.get("supp")+")\nThe first page should be 1 only.\nBut the first page in order is "+firstpage+".\n\nWRONG XML ORDER.\n\nPLEASE CHECK WITH PPC PERSON. \n\nDo you want to continue..\n\nPress \"OK\" button if \"YES\"\n\n","XT - Message", JOptionPane.OK_CANCEL_OPTION);
					}
					//System.out.println("ch: "+ch);
					if(c!=0)
						System.exit(0);
				}
			}
		}
	}
	
}



}
/**
*Modify Date :[12/09/2007]
* Change Point : Below function is use for excrating Marker tag
*Change By : Ravi
*/


if(HeadGroup.marker_check_first==true)
{
	artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "Marker,");
	for(int i=0; i< HeadGroup.marker_name.size();i++)
	{
		if(i>0)
		{
			artContents.insert(artContents.indexOf("\\begin{document}"), "\\OtherMarkerlogo{Marker-"+HeadGroup.marker_name.get(i).toString()+"}\r\n");
		}
		else
		{
			artContents.insert(artContents.indexOf("\\begin{document}"), "\\Markerlogo{Marker-"+HeadGroup.marker_name.get(i).toString()+"}\r\n");
		}

	}
	HeadGroup.marker_name.clear();
	HeadGroup.marker_check_first=false;
}
//*****************************************************************************************
if(jid.equalsIgnoreCase("DAD"))
{
	String temp_aid=xt.aid;//check for duckling item.

			if(temp_aid.indexOf(".",0)!=-1)
			{
				temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
			}
			ReadForEextra rfe= new ReadForEextra(jid,temp_aid,stage);

	String DADColumn=rfe.readOrderFileForDAD();
	if(DADColumn.equalsIgnoreCase("three column"))
	{
		artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "CPDDnonSC,Three,threecolumn,");
		artContents.insert(artContents.indexOf("\\begin{document}") , "\\usepackage{TP-threecolumn}\r\n");
	}
	else if(DADColumn.equalsIgnoreCase("two column"))
	{
		artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "CPDDSC,STAMP,");
		
	}
}


					//end
				//*************************************************

				/**
					*Added By Arvind [09/05/2007
					*Change Request By: Vivek;
					* Change Point: In Journal DAD if Dochead contains "News and Views" "CPDD News and Views" then
					* \\usepackage{TP-threecolumn} and \\documentclass[Three, threecolumn
					* will Addec.
				*/
				//System.out.println("xmlHead.dad_check_doc_head  "+xmlHead.dad_check_doc_head);
				if(xmlHead.dad_check_doc_head==true&&(xt.xmlObj.jid.equals("PROTIS")))
				{
					artContents.insert(15,"NWS,");
				}
				else if(xmlHead.dad_check_doc_head==true)
				{
					//System.out.println("2. dad_check_doc_head "+xmlHead.dad_check_doc_head);
					int io= artContents.indexOf("\\usepackage",0);
					if(io != -1)
					{
						artContents.insert(io,"\\usepackage{TP-threecolumn}\n");
						artContents.insert(15,"Three,threecolumn,");
					}

				}
				//06/12/2007
				if((xt.xmlObj.jid.equals("PHYST"))&&(xt.xmlObj.pit.equalsIgnoreCase("brv")))//06/12/2007
					{
						int io= artContents.indexOf("\\usepackage",0);
						if(io != -1)
						{
							artContents.insert(io,"\\usepackage{TP-threecolumn}\n");
							artContents.insert(15,"Three,threecolumn,");
						}
						
					}

				//end
				System.out.println(".. Ok");
				xt.xmlObj.specialLink=false;
			}
			//avinandan added on 23-8-04
			else if (tag.startsWith("<CE:EXAM-QUESTIONS"))
			{
				//ExamBodyGroup xmlExam= new
				//ExamBodyGroup(xt.xmlObj);
				BodyGroup xmlBody= new BodyGroup(xt.xmlObj);
				artContents.append("\r\n"+xmlBody.processSections("</CE:EXAM-QUESTIONS>"));
				//System.out.println(".. Ok");
			}
			else if (tag.startsWith("<CE:EXAM-ANSWERS"))
			{
				System.out.print("Processing Exam Answer Body      ");
				//ExamBodyGroup xmlExam= new ExamBodyGroup(xt.xmlObj);
				//artContents.append("\r\n"+xmlExam.getExamBodyContents());
				BodyGroup xmlBody= new BodyGroup(xt.xmlObj);
				artContents.append("\r\n"+xmlBody.processSections("</CE:EXAM-ANSWERS>"));
				//System.out.println(".. Ok");
			}
			//end mark
			/*else if (tag.equals("<BODY>"))
			{
				cur_jpr_biog=artContents.length();
				System.out.print("Processing Body      ");
				insertToc=artContents.length();
				BodyGroup xmlBody= new BodyGroup(xt.xmlObj);
				artContents.append("\r\n"+xmlBody.getBodyContents());				
				
				System.out.println(".. Ok");
			}*/
			else if (tag.startsWith("<BODY"))
			{
//				artContents.append("\\articleBody");
				bodyflage=true;
				//sectionNo=0;
				String bodyView= xt.xmlObj.getAttributeValue(tag, "VIEW");								
				cur_jpr_biog=artContents.length();
				//System.out.print("Processing Body      ");
				insertToc=artContents.length();
				BodyGroup xmlBody= new BodyGroup(xt.xmlObj);
				if(xt.xmlObj.jid.equals("PROTIS"))
					artContents.append("\r\n"+HeadGroup.TempCorAbb);
				/*if(xt.xmlObj.jid.equals("SJPAIN"))//17-03-2011
					artContents.append("\r\n\r\n\\clearpage\r\n");*/
				if(check_biography==true)
				{
					artContents.append("\r\n\\biographytag");
				}
				
				if(bodyView.equals("EXTENDED"))
				{
					artContents.append("\r\n\\begin{extra}"+xmlBody.getBodyContents()+"\r\n\\end{extra}");			
					
				}
				else
				{	
					artContents.append("\r\n"+xmlBody.getBodyContents());
				}
				
				if((xt.xmlObj.pit.equalsIgnoreCase("COR")) && xt.xmlObj.jid.equalsIgnoreCase("HLC"))//bahvesh 10/08/2006
				{
					//artContents.append("\r\n\r\n\\begin{flushright}");
					artContents.append("\r\n\r\n\\begin{miscflushright}");
					if(HeadGroup.middleData.indexOf("<HISTORY>") != -1)
					{
						int pos=0;
						String dedicated=HeadGroup.middleData.substring(0,HeadGroup.middleData.indexOf("<HISTORY>"));
						HeadGroup.middleData=HeadGroup.middleData.substring(HeadGroup.middleData.lastIndexOf("</HISTORY>")+10)+"\r\n"+HeadGroup.middleData.substring(HeadGroup.middleData.indexOf("<HISTORY>")+"<HISTORY>".length(),HeadGroup.middleData.lastIndexOf("</HISTORY>"));
						if(HeadGroup.middleData.indexOf("\\correspondingauthor{") != -1)
							pos=HeadGroup.middleData.indexOf("\\correspondingauthor{");
						else if(HeadGroup.middleData.indexOf("\\email{") != -1)
							pos=HeadGroup.middleData.indexOf("\\email{");
						
						StringBuffer temp=new StringBuffer(HeadGroup.middleData);
						HeadGroup.middleData=temp.insert(pos,dedicated).toString();
					}
					HeadGroup.middleData=HeadGroup.middleData.replaceAll("(\r\n\r\n)","\r\n");
					HeadGroup.middleData=HeadGroup.middleData.replaceAll("(\r\n\r\n\r\n)","\r\n");
			//		System.out.println("HeadGroup.middleData  --- > "+HeadGroup.middleData );
					//artContents.append(HeadGroup.middleData + "\\end{flushright}\r\n");
					artContents.append(HeadGroup.middleData + "\\end{miscflushright}\r\n");
				}

				//System.out.println(".. Ok");
				//System.out.println("artContents value:: "+artContents);
				//Added By : Ravi [19/03/2007]
				//*********************
				//System.out.println("sssssssbr==> "+xmlBody.sbr);
				if((xt.xmlObj.jid.equalsIgnoreCase("TRSTMH")||xt.xmlObj.jid.equals("INHE")))
					{
						if(xmlBody.isackcheck==false)
						{
						//temp+=sbr.toString();
						artContents.append(xmlBody.sbr.toString());
						//System.out.println("sssssssbr==> "+xmlBody.sbr);
						//System.out.println("artContents==> "+artContents);
						}
					}
					//(xt.xmlObj.jid.equalsIgnoreCase("JJBE") )
					//else if((xt.xmlObj.jid.equalsIgnoreCase("JJBE") ))
					else if((xt.xmlObj.jid.equalsIgnoreCase("JJBE"))||(xt.xmlObj.jid.equalsIgnoreCase("CIRCIR"))||(xt.xmlObj.jid.equalsIgnoreCase("ANTAGE"))||(xt.jid.equalsIgnoreCase("GAIPOS"))||(xt.jid.equalsIgnoreCase("YSEIZ")))
					{
						//temp+=sbr.toString();
						if(xmlBody.isackcheck==false)
						{
							artContents.append(xmlBody.sbr.toString());
						}
					}
					/*if(xt.xmlObj.jid.equalsIgnoreCase("IHE"))
					{
						if(xmlBody.isackcheck==false)
						{
						//temp+=sbr.toString();
						artContents.append(xmlBody.sbr.toString());
						//System.out.println("sssssssbr==> "+xmlBody.sbr);
						}
					}*/
					xmlBody.sbr=new StringBuffer("");
					//System.out.println("sbr==> "+xmlBody.sbr);
									//********************
									//end
			}
			else if (tag.equals("<TAIL>") || tag.equals("<SIMPLE-TAIL>"))
			{
				System.out.print("Processing Tail      ");
				TailGroup xmlTail= new TailGroup(xt.xmlObj);
				artContents.append(xmlTail.getTailContents());

				//System.out.println("------->>" +XT.refFootnote);
				if(artContents.indexOf("\r\n\\end{notes}") != -1)
					artContents.insert(artContents.indexOf("\r\n\\end{notes}"),refFootnote);
								
				//System.out.println(".. Ok");
			}
			else if (tag.startsWith("<TAIL ") || tag.startsWith("<SIMPLE-TAIL "))
			{
				System.out.print("Processing e-extra Tail      ");
				String tailView= xt.xmlObj.getAttributeValue(tag, "VIEW");								
				TailGroup xmlTail= new TailGroup(xt.xmlObj);
				if(tailView.equals("EXTENDED"))
					artContents.append("\r\n\\begin{extra}"+xmlTail.getTailContents()+"\r\n\\end{extra}");
				else
					artContents.append(xmlTail.getTailContents());
				//int d=artContents.indexOf("\\section{");				
				//System.out.println(".. Ok");
			}
			else if (tag.startsWith("<CE:FLOATS"))
			{
				//System.out.println("Processing Floats    ");
				artContents.append(xmlHead.processFloats());
				//System.out.println(".. Ok");
			}
			else if (tag.equals("</ARTICLE>") || tag.equals("</SIMPLE-ARTICLE>")|| tag.equals("</EXAM>"))
			{
				break;
			}
		}
		
		
	
		//Added by Avinandan
		if(xt.xmlObj.jid.equals("CHIABU"))
		{
			while(xmlHead.abstractOtherLang.toString().indexOf("\\begin{abstract}") != -1)
			{
				int s = xmlHead.abstractOtherLang.toString().indexOf("\\begin{abstract}");
				int e = s + "\\begin{abstract}".length();
				xmlHead.abstractOtherLang = xmlHead.abstractOtherLang.replace(s, e, "\\begin{Abstract}");
			}
			while(xmlHead.abstractOtherLang.toString().indexOf("\\end{abstract}") != -1)
			{
				int s = xmlHead.abstractOtherLang.toString().indexOf("\\end{abstract}");
				int e = s + "\\end{abstract}".length();
				xmlHead.abstractOtherLang = xmlHead.abstractOtherLang.replace(s, e, "\\end{Abstract}");
			}
			////////	

			if(xmlHead.abstractOtherLang.toString().length()>0)
				artContents.append("\r\n"+xmlHead.abstractOtherLang.toString());
		}
		//end mark
		/////////////////SECLAN APPENDIX CASE////////////////////
		if((xt.xmlObj.jid.equals("SECLAN") || xt.xmlObj.jid.equals("CHIABU")) && BodyGroup.append_seclan.length()>0)
		{
			artContents.append(BodyGroup.append_seclan);//18-01-2005
			BodyGroup.append_seclan="";
		}
		/////////////////SECLAN APPENDIX CASE////////////////////
		if(cur_jpr_biog > 0 && TailGroup.JprBiog.length()>0 && xt.jid.equals("JPR") && Integer.parseInt(xt.aid)>=53)
		{
			artContents.insert(cur_jpr_biog,TailGroup.JprBiog+"\r\n");
			TailGroup.JprBiog="";
			cur_jpr_biog=0;
		}
		if((xt.xmlObj.pit.equalsIgnoreCase("REV") && !XT.antiTocJid.contains(xt.xmlObj.jid))||XT.TOCrequired==true)
		{			
			///BodyGroup.tableContents+="\r\n\\end{description}\r\n";
			/**
			*[18/12/2007]
			* Added By: Ravi
			* Change Point : In case of PIT=REV and JID=DDES then TOC will appear
			* Change Request By: TPMS.
			*/
//			if((xt.xmlObj.check_MassonJid==true)&&(xt.xmlObj.jid.equalsIgnoreCase("DDES")))//1812/2007
			if((xt.xmlObj.check_MassonJid==true)&&(xt.xmlObj.jid.equalsIgnoreCase("DDES")||xt.xmlObj.jid.equalsIgnoreCase("SMED")))//14/01/2022
			{
				// mukesh 25-09-08
				//\item{\hskip18pt}Biographies\dotfill\quad00
				//TailGroup xmlTail= new TailGroup(xt.xmlObj);
				if(TailGroup.isBiography)
				{
					//BodyGroup.tableContents+="\\item{\\hskip18pt}Biographies\\dotfill\\quad00";
					BodyGroup.tableContents+="\\item{\\hskip18pt}Biographies\\dotfill\\planref{PL"+BodyGroup.planref+"}";
				}
				BodyGroup.tableContents+="\r\n\\end{Textoc}\r\n";
				System.out.println("Processing Table Of Contents....");	
				artContents.insert(insertToc,"\r\n"+BodyGroup.tableContents);
				insertToc=0;
				BodyGroup.tableContents="";
				xt.xmlObj.check_MassonJid=false;
			}
			else if ((xt.xmlObj.check_MassonJid==false))
			{
				// mukesh 25-09-08
				//\item{\hskip18pt}Biographies\dotfill\quad00
				//TailGroup xmlTail= new TailGroup(xt.xmlObj);
				if(TailGroup.isBiography)
				{
					if((xt.xmlObj.jid.equals("SNB")) || (xt.xmlObj.jid.equals("SNA")) || (xt.xmlObj.jid.equals("ONCH"))){
						//BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Biographies\\dotfill\\quad00";//old //21/03/2009
						if(TailGroup.temp_biography==false)
						{
							//BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Biography\\dotfill\\quad00";//Updated on 11-05-2015 for TOC auto pagerange
							BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Biography\\dotfill\\planref{PLBiography}";
						}
						else if(TailGroup.temp_biography==true){
							//BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Biographies\\dotfill\\quad00";//Updated on 11-05-2015 for TOC auto pagerange
							BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Biography\\dotfill\\planref{PLBiography}";
						}
					}
				}
				/*if(TailGroup.isReference)
				{
					if(TailGroup.temp_Reference==false)
					{
						BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}Reference\\dotfill\\quad00";
					}
					else if(TailGroup.temp_Reference==true)
					{
						BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}References\\dotfill\\quad00";
					}
				}*/
				BodyGroup.tableContents+="\r\n\\end{Textoc}\r\n";

				System.out.println("Processing Table Of Contents....");	
				//**********************************************

				/**
				* Added By : Ravi
				* Date: 03/07/2008
				* Modify point : In case of ONCH and CCR journal, toc matter will appear before \\startabstract{%
				* Request By : TPMS [Vivek]
				*/
					if((xt.xmlObj.jid.equalsIgnoreCase("ONCH"))||(xt.xmlObj.jid.equalsIgnoreCase("CCR")))
					{
						int in=artContents.indexOf("\\startabstract{%",0);
						if(in !=-1)
						{
							artContents.insert(in,"\r\n\\starttoc{%"+BodyGroup.tableContents.substring(0,BodyGroup.tableContents.length()-2)+"}\r\n\r\n");
						}
						else
						{
							artContents.insert(insertToc,"\r\n"+BodyGroup.tableContents);
						}
					}
					else
						{
							artContents.insert(insertToc,"\r\n"+BodyGroup.tableContents);
						}
					//artContents.insert(insertToc,"\r\n"+BodyGroup.tableContents);//old
				//***************************************************
				
				insertToc=0;
				BodyGroup.tableContents="";
			}
		}
		if((xt.xmlObj.pit.equalsIgnoreCase("brv") || xt.xmlObj.pit.equalsIgnoreCase("PRV")) && xt.xmlObj.jid.equalsIgnoreCase("COCOMP"))
		{
			//Do Nothing
		}
		
		else if((xt.xmlObj.pit.equalsIgnoreCase("COR")) && xt.xmlObj.jid.equalsIgnoreCase("JALCOM"))
		{
			//Do Nothing
		}
		else if(((xt.xmlObj.pit.equalsIgnoreCase("brv")) || ( xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ")))||((xt.xmlObj.pit.equalsIgnoreCase("DIS")||xt.xmlObj.pit.equalsIgnoreCase("EDI"))&&jid.equalsIgnoreCase("JPHYS"))||(xt.xmlObj.pit.equalsIgnoreCase("EDI")&& (HeadGroup.AFJU_Editorial_comment==false)&&(!jid.equalsIgnoreCase("JASCERNOTREQ")))||(xt.xmlObj.pit.equalsIgnoreCase("PGL")) || (xt.xmlObj.pit.equalsIgnoreCase("REQ")) || (xt.xmlObj.pit.equalsIgnoreCase("EXM")&&(!jid.equalsIgnoreCase("NEUARG")&&!jid.equalsIgnoreCase("RAMB")&&!jid.equalsIgnoreCase("RCA")&&!jid.equalsIgnoreCase("RCAE")&&!jid.equalsIgnoreCase("VACUN"))) || (xt.xmlObj.pit.equalsIgnoreCase("PRP"))|| (xt.xmlObj.pit.equalsIgnoreCase("CNF")&&!xt.xmlObj.jid.equalsIgnoreCase("JCV"))||(xt.xmlObj.pit.equalsIgnoreCase("COR"))  || (xt.xmlObj.jid.equals("DNAREP") && (xmlHead.artDochead.equalsIgnoreCase("Classics in DNA Repair") || xmlHead.artDochead.equalsIgnoreCase("Historical reflections") || xmlHead.artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xt.xmlObj.jid.equalsIgnoreCase("HLC"))
		{
			if(xt.xmlObj.jid.equals("DNAREP") && (xmlHead.artDochead.equalsIgnoreCase("Classics in DNA Repair") || xmlHead.artDochead.equalsIgnoreCase("Historical reflections") || xmlHead.artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))
			{
				artContents.append("\r\n\\begin{HotDNA}");
			}
			else
			{
				//artContents.append("\r\n\\begin{flushright}");//old
			/* [24/01/2007]
			 * Added By  : Ravi
			 * Change Request By : Vivek
			 * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
			 * dochead is Presidential Address in any where in dochead.
			 *
			*/
			//	if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (xmlHead.artDochead.indexOf("Presidential Address")!= -1))
				if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((xmlHead.artDochead.indexOf("Presidential Address ")!= -1) || xmlHead.artDochead.endsWith("Presidential Address")))
				{
				}
				/* [05/03/2007]
				* Added By  : Ravi
				* Change Request By : Vivek
			 	* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary"
				 */
				else if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("IMLET"))&& (xmlHead.artDochead.equals("Editorial Commentary")))
				{
				}
				else
				{
					//artContents.append("\r\n\\begin{flushright}");
					if((xt.xmlObj.pit.equalsIgnoreCase("PGL"))  && xt.xmlObj.check_MassonJid==true)
					{
					}
					else
					{
						if(!(xt.xmlObj.pit.equalsIgnoreCase("EDI") && xt.xmlObj.jid.equalsIgnoreCase("JPHYS")))
						{
							artContents.append("\r\n\\begin{miscflushright}");
							//System.out.println("-----------------------------------------");
						}
					}
				}
				//end
				
			}
			if(HeadGroup.endData.indexOf("<HISTORY>")!=-1)
			{
				int pos=0;
				String dedicated=HeadGroup.endData.substring(0,HeadGroup.endData.indexOf("<HISTORY>"));
				HeadGroup.endData=HeadGroup.endData.substring(HeadGroup.endData.lastIndexOf("</HISTORY>")+10)+"\r\n"+HeadGroup.endData.substring(HeadGroup.endData.indexOf("<HISTORY>")+"<HISTORY>".length(),HeadGroup.endData.lastIndexOf("</HISTORY>"));
				if(HeadGroup.endData.indexOf("\\correspondingauthor{") != -1)
					pos=HeadGroup.endData.indexOf("\\correspondingauthor{");
				else if(HeadGroup.endData.indexOf("\\email{") != -1)
					pos=HeadGroup.endData.indexOf("\\email{");
				
				StringBuffer temp=new StringBuffer(HeadGroup.endData);
				HeadGroup.endData=temp.insert(pos,dedicated).toString();
			}
			//System.out.println("\nenddata: "+HeadGroup.endData);
			HeadGroup.endData=HeadGroup.endData.replaceAll("(\r\n\r\n)","\r\n");
			HeadGroup.endData=HeadGroup.endData.replaceAll("(\r\n\r\n\r\n)","\r\n");
			artContents.append("\r\n"+HeadGroup.endData);
			//System.in.read();
			if(xt.xmlObj.jid.equals("DNAREP") && (xmlHead.artDochead.equalsIgnoreCase("Classics in DNA Repair") || xmlHead.artDochead.equalsIgnoreCase("Historical reflections") || xmlHead.artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))
				artContents.append("\r\n\\end{HotDNA}");//22.02.2006
			else
				{
					//artContents.append("\r\n\\end{flushright}");//old
					/* [24/01/2007]
					 * Added By  : Ravi
					 * Change Request By : Vivek
			 		 * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
			 		 * dochead is Presidential Address in any where in dochead.
			 		 *
			         */
					//	if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (xmlHead.artDochead.indexOf("Presidential Address")!= -1))
						if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((xmlHead.artDochead.indexOf("Presidential Address ")!= -1) || xmlHead.artDochead.endsWith("Presidential Address")))
						{
						}
						/* [05/03/2007]
							* Added By  : Ravi
							* Change Request By : Vivek
			 				* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary"
				         */
						else if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("IMLET"))&& (xmlHead.artDochead.equals("Editorial Commentary")))
						{
						}
						else
						{
							//artContents.append("\r\n\\end{flushright}");
							if(xt.xmlObj.pit.equalsIgnoreCase("PGL")&& xt.xmlObj.check_MassonJid==true)
							{
							}else
							{
								if(((xt.xmlObj.pit.equalsIgnoreCase("brv") || (xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xt.xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))|| (xt.xmlObj.pit.equalsIgnoreCase("EDI")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))) ||xt.xmlObj.pit.equalsIgnoreCase("PGL")|| (xt.xmlObj.pit.equalsIgnoreCase("REQ")) || (xt.xmlObj.pit.equalsIgnoreCase("EXM")&&(!jid.equalsIgnoreCase("NEUARG")&&!jid.equalsIgnoreCase("RAMB")&&!jid.equalsIgnoreCase("RCA")&&!jid.equalsIgnoreCase("RCAE")&&!jid.equalsIgnoreCase("VACUN"))) ||(xt.xmlObj.pit.equalsIgnoreCase("PRP"))||(xt.xmlObj.pit.equalsIgnoreCase("COR")) || xt.xmlObj.pit.equalsIgnoreCase("CNF") )&&(modelStyle.startsWith("-Mod7")||modelStyle.startsWith("7")))
								{
									artContents.append("\r\n"+articleHead_Compact.toString()+"\r\n\\end{miscflushright}");
								}
								else
								{
									if(!(xt.xmlObj.pit.equalsIgnoreCase("EDI") && xt.xmlObj.jid.equalsIgnoreCase("JPHYS")))
										artContents.append("\r\n\\end{miscflushright}");
								}
							}
						}
					//end
				
				}
			

			if(xt.xmlObj.jid.equalsIgnoreCase("NEUCLI") && xt.xmlObj.pit.equalsIgnoreCase("BRV"))
				artContents.append("\r\n\\misccopyright");
				//Added By Ravi [20/11/2006 Vivek \misccopyright]
//			if((jid.equalsIgnoreCase("REVMIC") ) && ( xt.xmlObj.pit.equalsIgnoreCase("BRV") ||  xt.xmlObj.pit.equalsIgnoreCase("CNF") ))//Updated on 15-09-2018
			if((jid.equalsIgnoreCase("REVMIC") || jid.equalsIgnoreCase("ACCPM") || jid.equalsIgnoreCase("DIABET") || jid.equalsIgnoreCase("EJTD") || jid.equalsIgnoreCase("EURGER") || jid.equalsIgnoreCase("EURPSY") || jid.equalsIgnoreCase("GEOBIO") || jid.equalsIgnoreCase("GOFS") || jid.equalsIgnoreCase("HANSUR") || jid.equalsIgnoreCase("INAN") || jid.equalsIgnoreCase("JOGOH") || jid.equalsIgnoreCase("JORMAS") || jid.equalsIgnoreCase("MEDNUC") || jid.equalsIgnoreCase("MYCMED") || jid.equalsIgnoreCase("RETRAM") || jid.equalsIgnoreCase("ARCPED")) && ( xt.xmlObj.pit.equalsIgnoreCase("BRV") ||  xt.xmlObj.pit.equalsIgnoreCase("CNF")))
				artContents.append("\r\n\\misccopyright");
			else if(xt.xmlObj.jid.equalsIgnoreCase("ANNPAL") && xt.xmlObj.pit.equalsIgnoreCase("EDI"))
				artContents.append("\r\n\\misccopyright");
			else if(xt.xmlObj.check_CatalanJid==true)
			{
				artContents.append("\r\n\\CATAcopyright");
			}

			if((xt.xmlObj.pit.equalsIgnoreCase("brv") || (xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ")))))
				artContents.insert(15,"MISC,BookReview,");	
			else if(xt.xmlObj.jid.equals("DNAREP") && (xmlHead.artDochead.equalsIgnoreCase("Classics in DNA Repair") || xmlHead.artDochead.equalsIgnoreCase("Historical reflections") || xmlHead.artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))
				artContents.insert(15,"HotTopic,");//22.02.2006
			else
			{
				//artContents.insert(15,"MISC,");//old
				/* [24/01/2007]
					 * Added By  : Ravi
					 * Change Request By : Vivek
			 		 * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
			 		 * dochead is Presidential Address in any where in dochead.
			 		 *
			         */
						//if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (xmlHead.artDochead.indexOf("Presidential Address")!= -1))
							if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((xmlHead.artDochead.indexOf("Presidential Address ")!= -1) || xmlHead.artDochead.endsWith("Presidential Address")))
						{}
						else
						{
							/**
							*Date:[05/03/2007]
							*Added By : Ravi
							* Change Request by: Vivek
							* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
							*/
							if((xt.xmlObj.pit.equalsIgnoreCase("EDI"))  && (xt.xmlObj.jid.equalsIgnoreCase("IMLET"))&& (xmlHead.artDochead.equals("Editorial Commentary")))
							{
									artContents.insert(15,"commentary,");
							}
							else
								{
									if(xt.xmlObj.pit.equalsIgnoreCase("PGL")&& xt.xmlObj.check_MassonJid==true)
									{
									}
									else{
										
										if(xt.xmlObj.pit.equalsIgnoreCase("COR")&&(modelStyle.equalsIgnoreCase("6PlusSpanish")||modelStyle.equalsIgnoreCase("5PlusGSpanish")))
										{
											artContents.insert(15,"COR,");
											//System.out.println("11-----------------------------------------"+modelStyle);
										}
										else
										{
											if(!(xt.xmlObj.pit.equalsIgnoreCase("EDI")&&xt.jid.equalsIgnoreCase("JPHYS")))
												artContents.insert(15,"MISC,");
										}
									//System.out.println("11-----------------------------------------");
									}
									
								}
						}
						//end
			}
			HeadGroup.endData="";
		}
		else if(xt.xmlObj.jid.equalsIgnoreCase("PROTIS")&&HeadGroup.dad_check_doc_head==true)
		{
			artContents.append("\r\n\r\n\\begin{miscflushright}\r\n"+HeadGroup.endData+"\r\n\\end{miscflushright}\r\n");
			//System.out.println("2222222HeadGroup.endData=========>>"+artContents);
		}
		else if(xt.xmlObj.pit.equalsIgnoreCase("COR") && xt.xmlObj.jid.equalsIgnoreCase("HLC"))
		{
			artContents.insert(15,"MISC,");
			//System.out.println("-----------------------------------------");
		}

		/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
		//System.out.println("headddddddddddddd   "+xmlHead.artDochead);
		else if((xt.xmlObj.pit.equalsIgnoreCase("dis"))&&(xt.xmlObj.jid.equalsIgnoreCase("YBJOM"))&& (xmlHead.artDochead.equals("INTERESTING CASE")))
		{
			//*********************************
					int s=0;
					int e=0;
					s=artContents.indexOf("\\documentclass[STAMP",s);
					e=s+"\\documentclass[STAMP".length();
				artContents.insert(e+1,"MISC,");
				//Added by mukesh on 23-09-08 for removal of STAMP and auto
				artContents=new StringBuffer(artContents.toString().replaceFirst("STAMP,",""));
				artContents=new StringBuffer(artContents.toString().replaceFirst("auto,",""));
			//**********************************
			//artContents.append("\r\n\\begin{flushright}");
			artContents.append("\r\n\\begin{miscflushright}");
			HeadGroup.endData=HeadGroup.endData.replaceAll("(\r\n\r\n)","\r\n");
			HeadGroup.endData=HeadGroup.endData.replaceAll("(\r\n\r\n\r\n)","\r\n");
			artContents.append("\r\n"+HeadGroup.endData);
			//artContents.append("\r\n\\end{flushright}");
			artContents.append("\r\n\\end{miscflushright}");
			//System.out.println("artContents--> "+artContents);
			//System.in.read();

		}
		if(((xt.xmlObj.jid.equals("RAMB")||xt.xmlObj.jid.equals("RCA")||xt.xmlObj.jid.equals("RCAE")||xt.xmlObj.jid.equals("NEUARG"))&&(xt.xmlObj.pit.equalsIgnoreCase("EXM"))))//25/05/2011
		{
			//System.out.println("1 tbCap : "+artContents);
			
			//artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), "onecolumn,");
			/*if(artContents.indexOf("MISC,STAMP,",0)!=-1)
			artContents=	artContents.delete(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), artContents.indexOf("MISC,STAMP,")+"MISC,STAMP,".length());
			else if(artContents.indexOf("MISC,NewDOI,STAMP,",0)!=-1)
			{
				artContents=	artContents.delete(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(), artContents.indexOf("MISC,NewDOI,STAMP,")+"MISC,NewDOI,STAMP,".length());
				artContents=artContents.insert(artContents.indexOf("\\documentclass[") + "\\documentclass[".length(),"NewDOI, ");
			}*/
			
			if(artContents.indexOf("MISC,",0)!=-1)
			{
				int spos=artContents.indexOf("\\documentclass[") ;
				if(spos!=-1)
				{
					int epos=artContents.indexOf("MISC,",spos);
					if(epos !=-1)
					{
						artContents=	artContents.delete(epos,epos+"MISC,".length());
					}
				}
				
			}
			if(artContents.indexOf("STAMP,",0)!=-1)
			{
				int spos=artContents.indexOf("\\documentclass[") ;
				if(spos!=-1)
				{
					int epos=artContents.indexOf("STAMP,",spos);
					if(epos !=-1)
					{
						artContents=	artContents.delete(epos,epos+"STAMP,".length());
					}
				}
				
			}
			


		}
		if(HeadGroup.AFJU_Editorial_comment)
		{
			if(artContents.indexOf("MISC,",0)!=-1)
			{
				int spos=artContents.indexOf("\\documentclass[") ;
				if(spos!=-1)
				{
					int epos=artContents.indexOf("MISC,",spos);
					if(epos !=-1)
					{
						artContents=	artContents.delete(epos,epos+"MISC,".length());
					}
				}
				
			}
		}
		/*else if(xt.xmlObj.check_MassonJid && xt.xmlObj.pit.equalsIgnoreCase("LIT"))//mukesh on 31-10-08 request by TPMS
		{	if(modelStyle.equalsIgnoreCase("-MODDFrench") || modelStyle.equalsIgnoreCase("-MODEFrench"))
			{
				int beginInd;
				beginInd=artContents.indexOf("\\begin{");
				if(beginInd!=-1)
				{
					//if(xt.xmlObj.pit.equalsIgnoreCase("EXM"))
					{artContents.insert(beginInd-1,"\\usepackage{LIT_MODD}\r\n");}
				}
			}
			else
			{
				int beginInd;
				beginInd=artContents.indexOf("\\begin{");
				if(beginInd!=-1)
				{
					//if(xt.xmlObj.pit.equalsIgnoreCase("EXM"))
					{artContents.insert(beginInd-1,"\\usepackage{LIT_STD}\r\n");}
				}
			}
		}*/

		//end
		////////////////EXTRANET////////////////////////////////
		
		if(HeadGroup.miscjamm.length() > 0 && !(xt.xmlObj.jid.equalsIgnoreCase("OFTAL")||xt.modelStyle.startsWith("7Spanish")))//bhavesh 10/08/2006
		{
			//artContents.append("\r\n\\begin{flushright}\r\n{"+ HeadGroup.miscjamm +"}\r\n\\end{flushright}");
			artContents.append("\r\n\\begin{miscflushright}\r\n{"+ HeadGroup.miscjamm +"}\r\n\\end{miscflushright}");
		}
		
		if(isTableGulliverCutOff==false){//06/11/2009
		if(jid.equalsIgnoreCase("PALEVO") && xt.xmlObj.pit.equalsIgnoreCase("EDI"))//abhay 26/08/2006
			artContents.append("\r\n\\misccopyright");
		}
	//**********************************************
	if(isOrderPathAvailable.equalsIgnoreCase("yes"))
				{
						
					//StringBuffer chceck_eExtra_File = new StringBuffer(sb.toString());
					String ad1=xt.aid;
					//System.out.println("XT Calling .... "+ad1);
					int in_pos=ad1.indexOf(".",0);
					if(in_pos !=-1)
					{
						ad1=ad1.substring(0,in_pos);
					}
					
					ReadForEextra rfFMC= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					check_FMC=false;
					if(stage.equalsIgnoreCase("S300"))
					{
						if(RSVP_Text.equalsIgnoreCase("RSVP"))
						{
							read="FFF";
						}
						if(NOFMC_comment)
						{
							check_FMC=false;
							FMC_comment=false;
							DPC_comment=false;
						}
						else
						{
							check_FMC=rfFMC.readOrderFileForFMC(read);
						}

					}
					else
					{
						//System.out.println("RAVI....");
						if(NOFMC_comment)
						{
							check_FMC=false;
							FMC_comment=false;
							DPC_comment=false;
						}
						else
						{
							check_FMC=rfFMC.readOrderFileForFMC();
						}
						//System.out.println("check_eextra RAVI...."+check_FMC);
					}
					
					//System.out.println("\ncheck_FMC : "+check_FMC);
					ReadForEextra rfSCISPO= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					if(xt.xmlObj.MassonJidList.contains(jid) && (check_FMC==true||rfSCISPO.readOrderFileFor_DPC()==true)&&FMC_comment==false&&DPC_comment==false&&NOFMC_comment==false)
					{
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
						System.out.println("**************************");
						System.out.println("WARNING FOR \"FMC\" or \"DPC\"");
						System.out.println("**************************");
						System.out.println("***************************************************************************");
						System.out.println("XMLOrder contains \"FMC\" or \"DPC\" instruction in remark section\nIf item is FMC please add comment tag <!--<FMC/>--> or <!--<DPC/>--> in XML\nIf item is not FMC please add comment tag <!--<NO FMC/>--> in XML\nTeX file not generated...");
						System.out.println("***************************************************************************");
						System.out.println("***************************************************************************\n\n\n\n\n\n\n\n\n");
						System.exit(0);
					}
					//if(check_FMC==true)
					if(NOFMC_comment)
					{
						check_FMC=false;
						FMC_comment=false;
						DPC_comment=false;
					}
					if(FMC_comment==true && NOFMC_comment==false)
					{
						if((jid.equalsIgnoreCase("JFO"))||(jid.equalsIgnoreCase("MLONG"))||jid.equalsIgnoreCase("LONGEV")||jid.equalsIgnoreCase("RMR")||(jid.equalsIgnoreCase("ANNDEROLD"))||(jid.equalsIgnoreCase("FANDER"))||(jid.equalsIgnoreCase("ACVDSP"))||(jid.equalsIgnoreCase("JEMEP"))||jid.equalsIgnoreCase("JRADIO")||jid.equalsIgnoreCase("JRDIA")||jid.equalsIgnoreCase("DIII")||(jid.equalsIgnoreCase("TRACLI"))||(jid.equalsIgnoreCase("MSOM"))||(jid.equalsIgnoreCase("GCB"))||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))||(jid.equalsIgnoreCase("JTS"))||(jid.equalsIgnoreCase("NEUADO"))||(jid.equalsIgnoreCase("CND"))||(jid.equalsIgnoreCase("JGYN"))||(jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))||(jid.equalsIgnoreCase("SCISPO"))||(jid.equalsIgnoreCase("ANORL"))||(jid.equalsIgnoreCase("AFORL"))||(jid.equalsIgnoreCase("PUROL")))//03/01/2008
						{
							int beginInd;
							artContents.insert(15,"MODDPlus,");
							//mukesh 24-10-08 request by TPMS
							// As usepackage required when MODDPlus is inserted
							//System.out.println("22222222222222");
							beginInd=artContents.indexOf("\\begin{");//Added by mukesh on 29-10-08
							if(beginInd!=-1)
							{
								if(xt.xmlObj.pit.equalsIgnoreCase("ABS") && artContents.indexOf("\\usepackage{ABS_MODD}")==-1)
								{
									
									artContents.insert(beginInd-1,"\\usepackage{ABS_MODD}\r\n");
									int u=artContents.indexOf("\\usepackage{ABS_STD}",0);
									//System.out.println("-----------------------> "+artContents);
									//System.in.read();
									if(u !=-1)
									{
										artContents.delete(u,u+"\\usepackage{ABS_STD}".length());
									}
																		
								}
								if(xt.xmlObj.pit.equalsIgnoreCase("LIT")&& artContents.indexOf("\\usepackage{LIT_MODD}")==-1)
								{
									artContents.insert(beginInd-1,"\\usepackage{LIT_MODD}\r\n");
								}
								if(xt.xmlObj.pit.equalsIgnoreCase("EXM")&& artContents.indexOf("\\usepackage{EXM}")==-1)
								{
									artContents.insert(beginInd-1,"\\usepackage{EXM}\r\n");
								}
							}
							if(jid.equalsIgnoreCase("GCB")||(jid.equalsIgnoreCase("CLINRE"))||(jid.equalsIgnoreCase("CLIREX"))||(jid.equalsIgnoreCase("JGYN")||(jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman")))||(jid.equalsIgnoreCase("SCISPO"))||(jid.equalsIgnoreCase("ANORL"))||(jid.equalsIgnoreCase("AFORL")))//added on //29/03/2008
							{
								int spos=0;
								spos=artContents.indexOf("MOD6PlusFrench",0);
								if(spos!=-1)
								{
									artContents.delete(spos,spos+"MOD6PlusFrench".length());
									artContents.insert(spos,"MODDFrench");
								}
							}
							else if(jid.equalsIgnoreCase("JTS") ||jid.equalsIgnoreCase("NEUADO")||jid.equalsIgnoreCase("TRACLI"))
							{
								int spos=0;
								spos=artContents.indexOf("MOD5PlusFrench",0);
								//System.out.println("spos : "+spos);
								//System.in.read();
								if(spos!=-1)
								{
									artContents.delete(spos,spos+"MOD5PlusFrench".length());
									artContents.insert(spos,"MODDFrench");
									if( xt.xmlObj.pit.equalsIgnoreCase("LIT"))
									{
										if(artContents.indexOf("\\usepackage",0)!=-1)
										{
											//artContents.insert(artContents.indexOf("\\usepackage",0),"\\usepackage{LIT}\r\n"); 
											//removed by mukesh on 11-11-08(JAGDISH)
										}
									}
									//System.out.println("artContents : "+artContents);
									//System.in.read();
								}
							}
						}
						/*else if(jid.equalsIgnoreCase("GCB"))//03/01/2008// block on 29/03/2008
						{
							int spos=0;
							spos=artContents.indexOf("MOD6PlusFrench",0);
							if(spos!=-1)
							{
								artContents.delete(spos,spos+"MOD6PlusFrench".length());
								artContents.insert(spos,"MODDFrench");
							}
						}*/ //end block
						//System.out.println("artContents : "+artContents);
						//System.in.read();
					}
				}
				else
				{
					System.out.println("isOrderPathAvailable : "+isOrderPathAvailable);
				}
				//end

		//System.out.println("MUKESH:"+isDandD);
		//if(!isDandD)
		if(xt.DucklingOrderStatus==false)
		{
		//if((xt.xmlObj.pit.equalsIgnoreCase("ABS"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015
		if((xt.xmlObj.pit.equalsIgnoreCase("ABS"))&&((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))
		{
			int ipos=0;
			ipos=artContents.indexOf("\n",0);
			if(ipos !=-1)
			{
				artContents.insert(ipos+1,"\\usepackage{ABS-DandD}\r\n");
			}
		}
		//System.out.println("HELLL "+modelStyle+" >> "+xmlObj.pit+" >> "+jid+" >> "+aid+" >> "+xmlObj.MassonJidList.contains(jid));
		//if(xt.xmlObj.MassonJidList.contains(jid) && (modelStyle.endsWith("-MODDFrench") || modelStyle.endsWith("-MODEFrench")) && xt.xmlObj.pit.equals("ABS"))//08-04-2015
		if(xt.xmlObj.MassonJidList.contains(jid) && (modelStyle.toUpperCase().startsWith("-MODDFRENCH") || modelStyle.toUpperCase().startsWith("-MODEFRENCH")) && xt.xmlObj.pit.equals("ABS"))
	   	{
			int in=artContents.indexOf("\\usepackage{ABS-DandD}",0);
			if(in !=-1)
			{
				artContents.delete(in,in+"\\usepackage\\{ABS-DandD\\}".length());
				
				artContents.insert(in,"\\usepackage{ABS_MODD}\r\n");
				
			}
	   		
	   	}
	   	//else if(xt.xmlObj.MassonJidList.contains(jid) && (!modelStyle.endsWith("-MODDFrench") && !modelStyle.endsWith("-MODEFrench")) && xt.xmlObj.pit.equals("ABS"))//08-04-2015
	   	else if(xt.xmlObj.MassonJidList.contains(jid) && (!modelStyle.toUpperCase().startsWith("-MODDFRENCH") && !modelStyle.toUpperCase().startsWith("-MODEFRENCH")) && xt.xmlObj.pit.equals("ABS"))
		{
			int in=artContents.indexOf("\\usepackage{",0);
			if(in !=-1)
			{	//System.out.println("-------------------------> "+check_FMC);
					String ad1=xt.aid;
					ReadForEextra rfSCISPO= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					if(xt.xmlObj.MassonJidList.contains(jid) && (check_FMC==true||rfSCISPO.readOrderFileFor_DPC()==true)&&FMC_comment==false&&DPC_comment==false&&NOFMC_comment==false)
					{
						System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
						System.out.println("**************************");
						System.out.println("WARNING FOR \"FMC\" or \"DPC\"");
						System.out.println("**************************");
						System.out.println("***************************************************************************");
						System.out.println("XMLOrder contains \"FMC\" or \"DPC\" instruction in remark section\nIf item is FMC please add comment tag <!--<FMC/>--> or <!--<DPC/>--> in XML\nIf item is not FMC please add comment tag <!--<NO FMC/>--> in XML\nTeX file not generated...");
						System.out.println("***************************************************************************");
						System.out.println("***************************************************************************\n\n\n\n\n\n\n\n\n");
						System.exit(0);
					}
					if(NOFMC_comment)
					{
						check_FMC=false;
						FMC_comment=false;
						DPC_comment=false;
					}

				if(check_FMC==false||FMC_comment==false)
				artContents.insert(in,"\\usepackage{ABS_STD}\r\n");
			}
		}
		}
	   	//*************
		/**
		* Date : 02/06/2008
		* Added By : Ravi
		* Chage Point : Please insert \\usepackage{CAL_STD}in case of masson JID except model D and Model E having PIT=LIT
		* Change Request By : TPMS {Vivek}
		*/

		//if(xt.xmlObj.MassonJidList.contains(jid) && (!modelStyle.endsWith("-MODDFrench") && !modelStyle.endsWith("-MODEFrench")) && xt.xmlObj.pit.equals("CAL"))//08-04-2015
		if(xt.xmlObj.MassonJidList.contains(jid) && (!modelStyle.toUpperCase().startsWith("-MODDFRENCH") && !modelStyle.toUpperCase().startsWith("-MODEFRENCH")) && xt.xmlObj.pit.equals("CAL"))
		{
			int in=artContents.indexOf("\\usepackage{",0);
			if(in !=-1)
			{
				artContents.insert(in,"\\usepackage{CAL_STD}\r\n");
			}
		}
		

	//**********************************************
		/**
		*[18/12/2007]
		* Added By: Ravi
		* Change Point : In case of PIT=LIT and Model=-MODDFrench then Author move to the end of file
		* Change Request By: TPMS.
		*/
		//if((xt.xmlObj.pit.equalsIgnoreCase("LIT"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015
		if((xt.xmlObj.pit.equalsIgnoreCase("LIT"))&&((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))
		{
			//HeadGroup.Move_Author_tag
			/**
			* Date : 22/02/2008
			* Added By : Ravi
			*Change Point: Incase of model  -MODDFrench and pit =LIT then author information should be in original position.
			*Change Request By : TPMS.
			*/
			//artContents.append("\r\n\r\n"+HeadGroup.Move_Author_tag);//
			//HeadGroup.Move_Author_tag="";
		}

		//end 18/12/2007

		
//if((xt.xmlObj.pit.equalsIgnoreCase("COR"))&&((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015

//\\usepackage{Cor} commented on 23-10-2015 due to duplicacy of cor and cor_cnf 
/*if((xt.xmlObj.pit.equalsIgnoreCase("COR"))&&((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))
		{
			int ipos=0;
			ipos=artContents.indexOf("\n",0);
			if(ipos !=-1)
			{
				artContents.insert(ipos+1,"\\usepackage{Cor}\r\n");
			}
		}*/

//Added by mukesh on 04-10-08 request by TPMS.
//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
		{
			//System.out.println("artContents-------------"+artContents);
			int ipos=0;
			ipos=artContents.indexOf("\\documentclass[",0);
			if(ipos !=-1)
			{
				artContents.insert(ipos+"\\documentclass[".length(),"MODDNEW,");
			}
		}
if(jid.equalsIgnoreCase("PEPI"))//05-07-2010
{
String ad1=xt.aid;
int in_pos=ad1.indexOf(".",0);
if(in_pos !=-1)
{
	ad1=ad1.substring(0,in_pos);
}
ReadForEextra rfPEPI= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
if(rfPEPI.readOrderFileForPEPI())
	{
		int ppos=0;
			ppos=artContents.indexOf("\\documentclass[",0);
			if(ppos !=-1)
			{
				artContents.insert(ppos+"\\documentclass[".length(),"SpecialIssue,");
			}
	}
}
if(jid.equalsIgnoreCase("SCISPO"))//12-07-2012
{
String ad1=xt.aid;
int in_pos=ad1.indexOf(".",0);
if(in_pos !=-1)
{
	ad1=ad1.substring(0,in_pos);
}
//ReadForEextra rfSCISPO= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
ReadForEextra rfSCISPO= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
if(xt.xmlObj.MassonJidList.contains(jid) && (check_FMC==true||rfSCISPO.readOrderFileFor_DPC()==true)&&FMC_comment==false&&DPC_comment==false&&NOFMC_comment==false)
{
	System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n");
	System.out.println("**************************");
	System.out.println("WARNING FOR \"FMC\" or \"DPC\"");
	System.out.println("**************************");
	System.out.println("***************************************************************************");
	System.out.println("XMLOrder contains \"FMC\" or \"DPC\" instruction in remark section\nIf item is FMC please add comment tag <!--<FMC/>--> or <!--<DPC/>--> in XML\nIf item is not FMC please add comment tag <!--<NO FMC/>--> in XML\nTeX file not generated...");
	System.out.println("***************************************************************************");
	System.out.println("***************************************************************************\n\n\n\n\n\n\n\n\n");
	System.exit(0);
}
if(NOFMC_comment)
{
	check_FMC=false;
	FMC_comment=false;
	DPC_comment=false;
}
//if(rfSCISPO.readOrderFileFor_DPC())
if(DPC_comment==true && NOFMC_comment==false)
	{
	//System.out.println("TRUE");
		int ppos=0;
			ppos=artContents.indexOf("\\documentclass[",0);
			if(ppos !=-1)
			{
				artContents.insert(ppos+"\\documentclass[".length(),"MODDPlus,DPC,");
			}
	}
	
}
if(jid.equalsIgnoreCase("JRDIA"))//17-11-2011
{
	int ppos=0;
	int psos=0;
	ppos=artContents.indexOf("\\documentclass[",0);
	if(ppos !=-1)
	{
		if(artContents.indexOf("STAMP,")!=-1)
		{
			psos=artContents.indexOf("STAMP,",ppos);
			artContents.delete(psos,psos+"STAMP,".length());
		}
	}
	
}

//System.out.println("xt.xmlObj.pit : "+xt.xmlObj.pit);
if((jid.equalsIgnoreCase("ENFI")||jid.equalsIgnoreCase("ENFIE"))&& isENFI)//06-12-2010
{
		int ipos=0;
		ipos=artContents.indexOf("\\usepackage{",0);
		if(ipos !=-1)
		{
			artContents.insert(ipos,"\\usepackage{ENFE_EXM}\r\n");
			isENFI=false;
			
		}
}
if(jid.equalsIgnoreCase("NSL"))//06-07-2010
{
String ad1=xt.aid;
int in_pos=ad1.indexOf(".",0);
if(in_pos !=-1)
{
	ad1=ad1.substring(0,in_pos);
}
ReadForEextra rfPEPI= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
if(rfPEPI.readOrderFileForNSL())
	{
		int ppos=0;
			ppos=artContents.indexOf("\\begin{document}",0);
			if(ppos !=-1)
			{
				artContents.insert(ppos,"\\pagecheckfalse\r\n");
			}
	}
}

//****************************
 if(jid.equalsIgnoreCase("EVOPSY")||jid.equalsIgnoreCase("AFORL")||jid.equalsIgnoreCase("RPPNEN-REMOVED")||jid.equalsIgnoreCase("RCAE")||jid.equalsIgnoreCase("ARBR")||jid.equalsIgnoreCase("ACUROE")||jid.equalsIgnoreCase("RPPEDE")||jid.equalsIgnoreCase("ADENGL")||jid.equalsIgnoreCase("REUMAE")||jid.equalsIgnoreCase("FARMAE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("REPCE")||jid.equalsIgnoreCase("OTOENG")||jid.equalsIgnoreCase("MEDINE")||jid.equalsIgnoreCase("BMHIME")||jid.equalsIgnoreCase("RGMXEN")||jid.equalsIgnoreCase("ENFCLE"))
	{
		int i=artContents.indexOf("\\documentclass[",0);
		if(i!=-1)
		{
			int m=artContents.indexOf("STAMP,",0);
			if(m !=-1)
			{
				//artContents.delete(i+"\\documentclass[".length(),m+"STAMP,".length());
				String s=artContents.toString();
				s=s.replaceFirst("STAMP,","");
				artContents=new StringBuffer(s);
			}
		}
	}

/**
* Date : 12/01/2009
* Modify by : Ravi
* Change Point : For  Journal ROCT, do not put STAMP option in documentclass in S100 as well as S200. 
* Change : TPMS
*/	//if(jid.equalsIgnoreCase("RCOT")&&(xt.xmlObj.pit.equalsIgnoreCase("EDI")||xt.xmlObj.pit.equalsIgnoreCase("COR")||xt.xmlObj.pit.equalsIgnoreCase("FLA")||xt.xmlObj.pit.equalsIgnoreCase("REV")||xt.xmlObj.pit.equalsIgnoreCase("SCO")))
	if(jid.equalsIgnoreCase("RCOT")||(jid.equalsIgnoreCase("JCHIRV")))
	{
		int i=artContents.indexOf("\\documentclass[",0);
		if(i!=-1)
		{
			int m=artContents.indexOf("STAMP,",0);
			if(m !=-1)
			{
				
				String s=artContents.toString();
				s=s.replaceFirst("STAMP,","");
				artContents=new StringBuffer(s);
			}
		}
	}


if((jid.equalsIgnoreCase("OPTOM")||jid.equalsIgnoreCase("BJPT"))&&(xt.xmlObj.pit.equalsIgnoreCase("COR")))
	{
		int i=artContents.indexOf("\\documentclass[",0);
		if(i!=-1)
		{
			int m=artContents.indexOf("COR,",0);
			if(m !=-1)
			{
				
				String s=artContents.toString();
				s=s.replaceFirst("COR,","MISC,");
				artContents=new StringBuffer(s);
			}
		}
	}

if(modelStyle.startsWith("7Spanish")&&(xt.xmlObj.pit.equalsIgnoreCase("COR")))
	{
		int i=artContents.indexOf("\\documentclass[",0);
		if(i!=-1)
		{
			int m=artContents.indexOf("MISC,",0);
			if(m !=-1)
			{
				
				String s=artContents.toString();
				s=s.replaceFirst("MISC,","COR,");
				artContents=new StringBuffer(s);
			}
		}
	}

checkModelDModelEVal=chekModelDAndModelEStatus.chekJidAid(jid, xt.aid);
if(checkModelDModelEVal && (xt.xmlObj.pit.equalsIgnoreCase("EXM")))
{
	int i=artContents.indexOf("\\documentclass[",0);
	if(i!=-1)
	{
		int m=artContents.indexOf("MISC,",0);
		if(m !=-1)
		{
			String s=artContents.toString();
			s=s.replaceFirst("MISC,","EXM,");
			artContents=new StringBuffer(s);
		}
	}
}else if(checkModelDModelEVal && (orgpit.equalsIgnoreCase("EXM")))//Condition for PIT EXM (PI EXM already changed to FLA)
{
	int i=artContents.indexOf("\\documentclass[",0);
	if(i!=-1)
	{
		String s=artContents.toString();
		s=s.replaceFirst("\\\\documentclass\\[","\\\\documentclass\\[EXM,");
		artContents=new StringBuffer(s);
	}
}


//*****************************	
	if(xt.DucklingOrderStatus==true && xt.xmlObj.MassonJidList.contains(jid))
		artContents.append("\r\n"+HeadGroup.DucklingTitleFootnote+"\r\n\r\n\\end{document}");
	else
		if(checkModelDModelEVal && (xt.xmlObj.pit.equalsIgnoreCase("COR")||(xt.xmlObj.pit.equalsIgnoreCase("CNF"))))
		{	
			artContents.append("\r\n\\begin{Endfootnote}");			
			artContents.append("\r\n\\lDOInumber{"+doi+"}");
			artContents.append("\r\n"+copyrightLineForEnd+"");
			artContents.append("\r\n\\end{Endfootnote}");
			artContents.append("\r\n\\end{document}");
			
		}
		else
		{
			artContents.append("\r\n\r\n\\end{document}");
		}
		
		////Check for Executive Summary for RETAIl///////
		String strRetail=artContents.toString();
//		System.out.println("strRetail ::::::::::::::::::::: "+strRetail);
//		strRetail=strRetail.replaceAll(regex, replacement)
		int exsumpos;
		exsumpos = strRetail.toLowerCase().indexOf("\\\\[10pt]{\\bf{executive summary}}\\\\[5pt]");
		if(exsumpos==-1){
			String newstrRetail = strRetail;
			newstrRetail = newstrRetail.replaceAll("\\\\\\\\\\[10pt\\]\\{\\\\bf\\{ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceExecutive summary\\}\\}\\\\\\\\\\[5pt\\]", "\\\\\\\\[10pt]{\\\\bf{Executive summary}}\\\\\\\\[5pt]");
			newstrRetail = newstrRetail.replaceAll("\\\\\\\\\\[10pt\\]\\{\\\\bf\\{Executive ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesummary\\}\\}\\\\\\\\\\[5pt\\]", "\\\\\\\\[10pt]{\\\\bf{Executive summary}}\\\\\\\\[5pt]");
			exsumpos = newstrRetail.toLowerCase().indexOf("\\\\[10pt]{\\bf{executive summary}}\\\\[5pt]");
//			exsumpos = newstrRetail.toLowerCase().indexOf("\\\\[10pt]{\\bf{executive summary}}\\\\[5pt]");
			if(exsumpos!=-1){
				System.out.println("Executive Summary found.");
			}else{
				System.out.println("Executive Summary not found.");
			}
		}
		if(exsumpos!=-1 && (strRetail.indexOf("\\begin{acknowledge")!=-1 || strRetail.indexOf("\\begin{thebibliography}")!=-1 ) && xt.jid.equals("RETAIL"))
		{
			//System.out.println("Entered");
			int esum=0;
/*			if(artContents.toString().toLowerCase().indexOf("\\section{uncited reference}")!=-1 )
			{System.out.println("1");
				esum=artContents.toString().toLowerCase().indexOf("\\section{uncited reference}") ;
			}
*/			if(artContents.toString().toLowerCase().indexOf("\\section{",exsumpos)!=-1 )
			{System.out.println("1");
				esum=artContents.toString().toLowerCase().indexOf("\\section{",exsumpos) ;
			}
			else if(artContents.indexOf("\\begin{acknowledge")!=-1 )
			{System.out.println("2");
				esum=artContents.indexOf("\\begin{acknowledge") ;
			}
			else if(artContents.indexOf("\\begin{appendix")!=-1 )
			{System.out.println("3");
				esum=artContents.indexOf("\\begin{appendix") ;
			}
			else if (artContents.indexOf("\\begin{thebibliography}")!=-1 )
			{System.out.println("4");
				esum=artContents.indexOf("\\begin{thebibliography}");
			}

			//System.out.println(artContents);
			System.out.println(exsumpos);
			System.out.println(esum);
//			int execInd=strRetail.toLowerCase().indexOf("\\\\[10pt]{\\bf{executive summary}}\\\\[5pt]");
			//System.out.println(artContents.length());

//			if(execInd>esum)
			if(exsumpos>esum)
			{
				System.out.println("\n\nPLEASE CHECK POSITION OF EXECUTIVE SUMMARY IN XML.\n");
			}
//			ExecSum=artContents.substring(execInd,esum);
			ExecSum=artContents.substring(exsumpos,esum);
//			System.out.println("ExecSum:"+ExecSum);
		}
		
		if(ExecSum.length()>0)
		{
			artContents=artContents.replace(artContents.indexOf(ExecSum),artContents.indexOf(ExecSum)+ExecSum.length(),"\r\n\n");
			ExecSum="\r\n\n\\clearthispage\r\n\n"+ExecSum;
			artContents=artContents.insert(artContents.indexOf("\r\n\r\n\\end{document}"),ExecSum);
			ExecSum="";
		}
	
		
		String temp= artContents.toString();
		if ((xt.xmlObj.jid.equals("EMT") && xt.xmlObj.pit.equals("PNT"))) // 30-11-2004
		{			
			///temp=temp.replaceFirst("\\[draft,colorlinks=true","\\[PNT,draft,colorlinks=true");		
			temp=temp.replaceFirst("documentclass\\[colorlinks=true","documentclass\\[PNT,colorlinks=true");		
		}

		

//\dochead{
		int abc = -1;
		int abc1 = -1;
		int abc2 = -1;
		if(temp.indexOf("\\DOInumber{",0) != -1)
			abc1=temp.indexOf("\\DOInumber{",0);
		else if(temp.indexOf("\\lDOInumber{",0) != -1)
			abc1=temp.indexOf("\\lDOInumber{",0);
		
		if(temp.indexOf("\\title{",0) != -1)
			abc=temp.indexOf("\\title{",0);
		else if(temp.indexOf("\\makechaptertitle{",0) != -1)
			abc=temp.indexOf("\\makechaptertitle{",0);
		StringBuffer tt1=new StringBuffer(temp);
		
		if(((xt.xmlObj.pit.equalsIgnoreCase("brv") || (xt.xmlObj.pit.equalsIgnoreCase("PRV")&&(!jid.equalsIgnoreCase("JASCERNOTREQ"))))||(xt.xmlObj.pit.equalsIgnoreCase("DIS")&&jid.equalsIgnoreCase("JPHYS"))||(xt.xmlObj.pit.equalsIgnoreCase("EDI")) ||xt.xmlObj.pit.equalsIgnoreCase("PGL")|| (xt.xmlObj.pit.equalsIgnoreCase("REQ")) || (xt.xmlObj.pit.equalsIgnoreCase("EXM")&&!jid.equalsIgnoreCase("VACUN")) ||(xt.xmlObj.pit.equalsIgnoreCase("PRP"))||(xt.xmlObj.pit.equalsIgnoreCase("COR")) || xt.xmlObj.pit.equalsIgnoreCase("CNF") )&&(modelStyle.startsWith("-Mod7")||modelStyle.startsWith("7")))
			{
				if(temp.indexOf("\\begin{document}",0) != -1)
					abc2=temp.indexOf("\\begin{document}",0);
				if(abc2!=-1)
				{
					tt1=tt1.insert(tt1.indexOf("\n",abc2)+1,"\\PdfInformation\r\n");
				}
				temp=tt1.toString();
			}
		else if(abc!=-1)
		{
			
			
			tt1=tt1.insert(tt1.indexOf("\n",abc1)+1,"\\PdfInformation");
			//if(doctopics.length()>0 && checkDuckling==true)//old 23/01/2008
			/**
				Date : [23/01/2008]
				Change By : Ravi
				Change Point : ce:doctopic matter not appearing in tex file.
				Change Request By: TPMS.
				
			*/
			if(doctopics.length()>0 )//new
			{
				//	tt=tt.insert(tt.indexOf("\n",abc)+1,"\\doctopics{"+doctopics+"}");

				if(xt.DucklingOrderStatus==false)
				{
					if(tt1.indexOf("\\dochead{",0)!=-1)
					{
						tt1=tt1.insert(tt1.indexOf("\\dochead{",0),"\\doctopics{"+doctopics+"}\n");
					}
					else 
					if(tt1.indexOf("\\title{",0)!=-1)
					{
						tt1=tt1.insert(tt1.indexOf("\\title{",0),"\\doctopics{"+doctopics+"}\n");
					}
				}
				else if(xt.DucklingOrderStatus==true && xt.xmlObj.MassonJidList.contains(jid))
				{
					if(tt1.indexOf("\\dochead{",0)!=-1)
					{
						tt1=tt1.insert(tt1.indexOf("\\dochead{",0),"\\doctopics{"+doctopics+"}\n");
					}
					else 
					{
						if(tt1.indexOf("\\title{",0)!=-1)
						{
							tt1=tt1.insert(tt1.indexOf("\\title{",0),"\\doctopics{"+doctopics+"}\n");
						}
					}

					if(tt1.indexOf("\\startabstract{",0)!=-1)
					{

						tt1=tt1.insert(tt1.indexOf("\\startabstract{",0),HeadGroup.DucklingCorrAuthor+"\n");
					}
					else if(tt1.indexOf("\\makechaptertitle",0)!=-1)
					{

						tt1=tt1.insert(tt1.indexOf("\\makechaptertitle",0),HeadGroup.DucklingCorrAuthor+"\n");
					}

				}
				else
				{
					if(tt1.indexOf("\\dochead{",0)!=-1)
					{
						tt1=tt1.insert(tt1.indexOf("\\dochead{",0),"\\doctopics{"+doctopics+"}\n");
					}
					else if(tt1.indexOf("\\title{",0)!=-1)
					{
						tt1=tt1.insert(tt1.indexOf("\\title{",0),"\\doctopics{"+doctopics+"}\n");
					}
				}
				//System.out.println("====> "+tt1);
				//System.in.read();
			}
			else if(xt.DucklingOrderStatus==true && xt.xmlObj.MassonJidList.contains(jid))//24-04-2012
			{
				if(tt1.indexOf("\\absfontsize",0)!=-1)
				{
					
					tt1=tt1.insert(tt1.indexOf("\\absfontsize",0),HeadGroup.DucklingCorrAuthor+"\n");
				}else if(tt1.indexOf("\\makechaptertitle",0)!=-1)//"else if" part added on 03-10-2015 as the email was missing from the TeX file 
				{
					tt1=tt1.insert(tt1.indexOf("\\makechaptertitle",0)+"\\makechaptertitle".length(),"\n"+HeadGroup.DucklingCorrAuthor+"\n");
				}
			}
			System.out.println();	
			temp=tt1.toString();			
		}
		else
		{
			
			
			tt1=tt1.insert(tt1.indexOf("\n",abc1)+1,"\\PdfInformation");
			if(doctopics.length()>0 )
			{
				tt1=tt1.insert(tt1.indexOf("\n",abc1)+1,"\\doctopics{"+doctopics+"}");
			}
			temp=tt1.toString();
		}
			
//****************
if(eappend==true)//08/02/2008
		{
			StringBuffer s=new StringBuffer(temp);
			int i=0;
			i=s.indexOf("\\lastpage{}",0);
			if(i!=-1)
			{
				s.insert(i+"\\lastpage{}".length()+2,"\\Efirstpage{}\r\n%\\Elastpage{}\r\n");
				temp=s.toString();
			}

		}
//***************

System.out.println("isDatabaseNotRequired==>"+isDatabaseNotRequired);

		if(isDatabaseAvailable.equalsIgnoreCase("yes") || isDatabaseNotRequired)
		{
			//System.out.println("my name onlineversiontype : "+label_Eappended);
			//label_Eappended
			//if(onlineversiontype.equalsIgnoreCase("e-extra") && temp.indexOf("\\begin{extra}") ==-1 && temp.indexOf("\\begin{antiextra}") ==-1)
			// "label_Eappended" added by mukesh on 14-11-08

			if(label_Eappended.equalsIgnoreCase("e-extra") && temp.indexOf("\\begin{extra}") ==-1 && temp.indexOf("\\begin{antiextra}") ==-1)
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR]: E-EXTRA ARTICLE!!! WRONG XML  E-EXTRA CODING!!! FILE WILL NOT BE CONVERTED!!!");
					return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"E-EXTRA ARTICLE!!! WRONG XML  E-EXTRA CODING!!! FILE WILL NOT BE CONVERTED!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					//System.out.println("System exiting...."+temp.indexOf("\\begin{extra}")+"  "+temp.indexOf("\\begin{antiextra}"));
					System.exit(0);
				}
			}

			//else if((!onlineversiontype.equalsIgnoreCase("e-extra")&&!onlineversiontype.equalsIgnoreCase("")) && (temp.indexOf("\\begin{extra}") !=-1))
			// "label_Eappended" added by mukesh on 14-11-08
			else //if((!label_Eappended.equalsIgnoreCase("e-appended"))&&(!label_Eappended.equalsIgnoreCase("e-only"))&&(!label_Eappended.equalsIgnoreCase("e-extra")&&!label_Eappended.equalsIgnoreCase("")) && (temp.indexOf("\\begin{extra}") !=-1))//blocked on 10-09-2010
			if((!label_Eappended.equalsIgnoreCase("e-appended"))&&(!label_Eappended.equalsIgnoreCase("e-extra")&&!label_Eappended.equalsIgnoreCase("")) && (temp.indexOf("\\begin{extra}") !=-1))
			{
				//System.out.println("Ravi 1 onlineversiontype : "+onlineversiontype);
				String tem_aid=xt.aid;
				if(tem_aid.indexOf(".",0)!=-1)
				{
					tem_aid=tem_aid.substring(0,tem_aid.indexOf(".",0));
				}
				ReadForEextra red= new ReadForEextra(xt.jid,tem_aid,stage);
				String mmcCount=red.GetEComponentInffo();
				int i=0;
				if((label_Eappended.equalsIgnoreCase("print")||label_Eappended.equalsIgnoreCase("e-only")) && (temp.indexOf("\\begin{extra}") !=-1)&&Integer.parseInt(mmcCount)>0)//20/06/2009
				{
					//System.out.println("----------------->"+i);
					//System.out.println("System exiting...."+temp.indexOf("\\begin{extra}")+"  "+temp.indexOf("\\end{extra}"));
					//System.out.println("System exiting...."+temp.indexOf("\\begin{extra}")+"  "+temp.indexOf("\\end{extra}"));
					while(temp.indexOf("\\begin{extra}") !=-1 && temp.indexOf("\\end{extra}") !=-1)
					{

						int x=temp.indexOf("\\begin{extra}");
						int y=temp.indexOf("\\end{extra}");
						temp=temp.substring(0,x)+temp.substring(y+"\\end{extra}".length());
						//i=y+"\\end{extra}".length();
						i++;
						//System.out.println("----------------->"+i);
					}
					temp=temp.replaceAll("\\\\begin\\{extra\\}","");
					temp=temp.replaceAll("\\\\end\\{extra\\}","");
					temp=temp.replaceAll("\\\\begin\\{antiextra\\}","");
					temp=temp.replaceAll("\\\\end\\{antiextra\\}","");
					
				}
				else{
						if(serverstatus.equals("SERVER"))
						{
							server_Xt.xt_log.info("[ERROR]: PRINT ARTICLE!!! WRONG XML E-EXTRA CODING!!! FILE WILL NOT BE CONVERTED!!!");
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"PRINT ARTICLE!!! WRONG XML E-EXTRA CODING!!! FILE WILL NOT BE CONVERTED!!!","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("System exiting....");
							System.exit(0);
						}
				}
				
			}
		}
		
	if(xt.jid.equals("NEURAD")||xt.jid.equals("PUROL"))
	{
		
			String ad1=xt.aid;
					
			//System.out.println("XT Calling .... "+ad1);
			int in_pos=ad1.indexOf(".",0);
			if(in_pos !=-1)
			{
				ad1=ad1.substring(0,in_pos);
			}
			ReadForEextra rfe= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
			if(xt.jid.equals("NEURAD"))
			{
				if(rfe.readOrderFile_NEURAD_AND_PUROL())
				{
					int i=0;
					StringBuffer s=new StringBuffer(temp);
					i=s.indexOf("\\begin{document}",0);
					if(i!=-1)
					{
						s=s.insert(i,"\\SpIssuetrue\r\n ");
						temp=s.toString();
					}
				}
			}
			if(xt.jid.equals("PUROL"))
				{
					if(rfe.readOrderFile_NEURAD_AND_PUROL())
					{
						int i=0;
						StringBuffer s=new StringBuffer(temp);
						s.insert(s.indexOf("\\documentclass[") + "\\documentclass[".length(), "PurolPP,");
						temp=s.toString();
					}
				}
			
		
	}

	//***************************************************[23/06/2008]****************************
		if(xt.jid.equals("ONCH")){
			if(isOrderPathAvailable.equalsIgnoreCase("yes")){
						
					String ad1=xt.aid;
					
					//System.out.println("XT Calling .... "+ad1);
					int in_pos=ad1.indexOf(".",0);
					if(in_pos !=-1)
					{
						ad1=ad1.substring(0,in_pos);
					}
					
					ReadForEextra rfe= new ReadForEextra(xt.jid.toUpperCase(),ad1,stage);
					boolean chek_Onch=false;
					if(stage.equalsIgnoreCase("S300"))
					{
						if(RSVP_Text.equalsIgnoreCase("RSVP"))
						{
							read="FFF";
						}
						chek_Onch=rfe.readOrderFileForOnch(read);
					}
					else
					{
						//System.out.println("RAVI....");
						chek_Onch=rfe.readOrderFileForOnch();
						//System.out.println("check_eextra RAVI...."+check_eextra);
					}

					label_Eappended=rfe.label;
					if(chek_Onch==true)
						{
							//temp;
							//System.out.println("check_eextra RAVI...."+temp);
							int i=0;
							StringBuffer s=new StringBuffer(temp);
							i=s.indexOf("\\end{document}",0);
							if(i!=-1)
							{
								s=s.insert(i,"\\StartMethodology\r\n ");
								temp=s.toString();
							}
						}
			}
	}
	//******************************************************************************************
		/////////////////e-extra and e-only case/////////////////////////////////

			//System.out.println("isceTitleFound: "+xmlHead.isceTitleFound);
			//		System.out.println("modelStyle==> "+modelStyle);
					if(xmlHead.isceTitleFound==false)
					{
						if(xt.xmlObj.pit.equalsIgnoreCase("BRV"))
						{
							//if(modelStyle.equalsIgnoreCase("-MODDFrench")||modelStyle.equalsIgnoreCase("-MODEFrench"))//08-04-2015
							if(modelStyle.toUpperCase().startsWith("-MODDFRENCH")||modelStyle.toUpperCase().startsWith("-MODEFRENCH"))
							{
								
								
								int in=artContents.indexOf("\\makechaptertitle",0);
								if(in !=-1)
								{
									StringBuffer sbcetitle=new StringBuffer(temp);
									sbcetitle=sbcetitle.insert(in+"\\makechaptertitle".length()+18,"\\miscTitle{}\r\n");
									temp=sbcetitle.toString();
								}
								//System.out.println("artContents==> "+temp);
								//\\makechaptertitle
							}
						}
					}
					
					StringBuffer sbcetitle=new StringBuffer(temp);
					int in1=sbcetitle.indexOf("\\jid{",0);
					if(in1 != -1)
					{
							sbcetitle.insert(in1,"\\referencestyle{"+xt.xmlObj.bibStyle+"}\r\n");
					}
					temp=sbcetitle.toString();

					//******************************************
					if(xt.jid.equals("CND")&& (temp.indexOf("\\docsubtype{ANN}",0)!=-1)&&(temp.indexOf("\\title{Actualit{\\ASeacute}s}",0)!=-1))
					{
						sbcetitle=new StringBuffer(temp);
						 int in=sbcetitle.indexOf("\\usepackage{",0);
						 if(in !=-1)
						{
							 sbcetitle.insert(in,"\\usepackage{ANN_MODD}\r\n");
						}
						temp=sbcetitle.toString();
					}
					//******************************************

//******************************************************************************

Hashtable has1 = new Hashtable();
has1 = GetNewDTDCutOff();
String aid_temp="";
String jid_temp=xt.xmlObj.jid.toUpperCase().trim();

//
// System.out.println(jid_temp+"----------0-------"+has1);
if(has1.containsKey(jid_temp.toString()))
{
	aid_temp=has1.get(jid_temp).toString();
	//System.out.println("----------1-------"+aid_temp);
	//if(Integer.parseInt(xt.xmlObj.aid.toUpperCase().trim())<=Integer.parseInt(aid_temp))
	String t_aid=xt.xmlObj.aid.toUpperCase().trim();

	if(t_aid.indexOf(".")!=-1)
	{
		t_aid=t_aid.substring(0,t_aid.indexOf("."));
		//System.out.println("t_aid=====> "+t_aid);
	}
	if(Integer.parseInt(t_aid)>=Integer.parseInt(aid_temp.trim()))
	{
		StringBuffer newDTD=new StringBuffer(temp);
		//System.out.println(t_aid+"----------2-------"+aid_temp);
		newDTD.insert(newDTD.indexOf("\\documentclass[") + "\\documentclass[".length(), "NewDTD,");
		temp=newDTD.toString();
	}
}
//***********************[08-04-2010]******************************************************************
//ABP S250 stage has been added
if(S250ABP.get("stage")!=null)
{
	//System.out.println("qqqqqqqqqqqqqq");
	if(S250ABP.get("stage").toString().equalsIgnoreCase("s250")||S250ABP.get("stage").toString().equalsIgnoreCase("s250resupply"))
	{
		StringBuffer tempsb=new StringBuffer(temp);
		int spos=0;
		int epos=0;
		spos=tempsb.indexOf("STAMP",epos);
		if(spos !=-1)
		{
			epos=tempsb.indexOf(",",spos);
			if(epos!=-1)
			{
				tempsb=tempsb.delete(spos,epos+1);
			}
		}

		spos=0;
		epos=0;
		spos=tempsb.indexOf("auto",epos);
		if(spos !=-1)
		{
			epos=tempsb.indexOf(",",spos);
			if(epos!=-1)
			{
				tempsb=tempsb.delete(spos,epos+1);
			}
		}
		spos=0;
		epos=0;
		spos=tempsb.toString().toLowerCase().indexOf("s250-draft",epos);
		//System.out.println("11111");
		if(spos !=-1)
		{
			//System.out.println("2222222222222");
			epos=tempsb.indexOf("\"]",spos);
			if(epos!=-1)
			{
				//System.out.println("333333333333333");
				tempsb=tempsb.delete(spos,epos);
				tempsb.insert(spos, "S300-Web-draft");
			}
		}
		temp=tempsb.toString();

	}
}



if(isArticleNumberPresent){
	if(isAcanWorkflow(xt.xmlObj.jid, xt.xmlObj.aid)){
		//Updation for <article-number> in documentclass
		StringBuffer tempsb=new StringBuffer(temp);
		int spos=0;
		int epos=0;
		spos=tempsb.indexOf("STAMP",epos);
		if(spos !=-1)
		{
			epos=tempsb.indexOf(",",spos);
			if(epos!=-1)
			{
				tempsb=tempsb.delete(spos,epos+1);
			}
		}
		spos=0;
		epos=0;
		spos=tempsb.indexOf("auto",epos);
		if(spos !=-1)
		{
			epos=tempsb.indexOf(",",spos);
			if(epos!=-1)
			{
				tempsb=tempsb.delete(spos,epos+1);
			}
		}
		temp=tempsb.toString();
	}
}

//********************************************************************************************
//********************************************************************************

		
		String abr="";
//System.out.println("temp "+temp);
//System.in.read();
//13/02/2008
StringBuffer abrEappend=new StringBuffer();
StringBuffer mainEappend=new StringBuffer();
if(label_Eappended.equalsIgnoreCase("e-appended"))
		{
			if(serverstatus.equals("SERVER"))
			{
				server_Xt.xt_log.info("[ERROR]: This is an E-APPENDED ARTICLE. Please check for the correctness and Paginate Both main and abr tex files.!!!");
				//return false;
			}
			else{
					JOptionPane.showMessageDialog(null,"This is an E-APPENDED ARTICLE.\n Please check for the correctness and Paginate Both main and abr tex files.","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
				}
			System.out.println("Processing COMPACT e-appended case....");
			StringBuffer Temp_Eappend=new StringBuffer(temp);
			if(Temp_Eappend.indexOf("\\begin{extra}") !=-1 && Temp_Eappend.indexOf("\\end{extra}") !=-1 )
			{
				while(Temp_Eappend.indexOf("\\begin{extra}") !=-1 && Temp_Eappend.indexOf("\\end{extra}") !=-1)
				{
					int x=Temp_Eappend.indexOf("\\begin{extra}",0);
					int y=Temp_Eappend.indexOf("\\end{extra}",0);
					if(x !=-1 && y!=-1)
					{
					//System.out.println(x+"---"+y);	
					abrEappend.append(Temp_Eappend.substring(x+"\\begin{extra}".length(),y));
					mainEappend.append(Temp_Eappend.substring(0,x));
					Temp_Eappend.delete(0,y+"\\end{extra}".length());
					}	
					
					
				}
				mainEappend.append(temp.substring(temp.lastIndexOf("\\end{extra}"),temp.length()));
				while(mainEappend.indexOf("\\end{extra}")!=-1)
				{
					int in=mainEappend.indexOf("\\end{extra}",0);
					if(in!=-1)
					{
						mainEappend.delete(in,in+"\\end{extra}".length());
					}
				}
			}
		
		}
/*else if(label_Eappended.equalsIgnoreCase("print") && (temp.indexOf("\\begin{extra}") !=-1 && temp.indexOf("\\end{extra}") !=-1 ))
		{//temp
			//JOptionPane.showMessageDialog(null,"This is an E-APPENDED ARTICLE.\n Please check for the correctness and Paginate Both main and abr tex files.","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
			//System.out.println("Processing COMPACT e-appended case....");
			StringBuffer Temp_Eappend=new StringBuffer(temp);
			if(Temp_Eappend.indexOf("\\begin{extra}") !=-1 && Temp_Eappend.indexOf("\\end{extra}") !=-1 )
			{
				while(Temp_Eappend.indexOf("\\begin{extra}") !=-1 && Temp_Eappend.indexOf("\\end{extra}") !=-1)
				{
					int x=Temp_Eappend.indexOf("\\begin{extra}",0);
					int y=Temp_Eappend.indexOf("\\end{extra}",0);
					if(x !=-1 && y!=-1)
					{
					//System.out.println(x+"---"+y);	
					abrEappend.append(Temp_Eappend.substring(x+"\\begin{extra}".length(),y));
					mainEappend.append(Temp_Eappend.substring(0,x));
					Temp_Eappend.delete(0,y+"\\end{extra}".length());
					}	
					
					
				}
				mainEappend.append(temp.substring(temp.lastIndexOf("\\end{extra}"),temp.length()));
				while(mainEappend.indexOf("\\end{extra}")!=-1)
				{
					int in=mainEappend.indexOf("\\end{extra}",0);
					if(in!=-1)
					{
						mainEappend.delete(in,in+"\\end{extra}".length());
					}
				}
			}
			temp=mainEappend.toString();
			temp=temp.replaceAll("\\\\begin\\{antiextra\\}","");
			temp=temp.replaceAll("\\\\end\\{antiextra\\}","");
		}
*/
	//	else//below condition is added for mmc is appear in print version//10/03/2008
		//else if(!label_Eappended.equalsIgnoreCase("e-appended")&&!label_Eappended.equalsIgnoreCase("print"))
		else if(!label_Eappended.equalsIgnoreCase("e-appended")&&!label_Eappended.equalsIgnoreCase("print")&&!label_Eappended.equalsIgnoreCase("e-only"))
		{
			if(temp.indexOf("\\begin{extra}") !=-1 && temp.indexOf("\\end{extra}") !=-1 && temp.indexOf("\\begin{antiextra}") !=-1 && temp.indexOf("\\end{antiextra}") !=-1)
			{

				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR]: This is an E-EXTRA ARTICLE. Please check for the correctness and Paginate Both main and abr tex files.!!!");
					//return false;
				}
				else{
				JOptionPane.showMessageDialog(null,"This is an E-EXTRA ARTICLE.\n Please check for the correctness and Paginate Both main and abr tex files.","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
				}
				System.out.println("Processing COMPACT e-extra case...");
				abr=temp;

				//int i=0;
				//System.out.println("abr====>"+abr);
				while(abr.indexOf("\\begin{extra}") !=-1 && abr.indexOf("\\end{extra}") !=-1)
				{
					int x=abr.indexOf("\\begin{extra}");
					int y=abr.indexOf("\\end{extra}");
					abr=abr.substring(0,x)+abr.substring(y+"\\end{extra}".length());
					//i=y+"\\end{extra}".length();

				}
				temp=temp.replaceAll("\\\\begin\\{extra\\}","");
				temp=temp.replaceAll("\\\\end\\{extra\\}","");

				abr=abr.replaceAll("\\\\begin\\{antiextra\\}","");
				abr=abr.replaceAll("\\\\end\\{antiextra\\}","");

				while(temp.indexOf("\\begin{antiextra}") !=-1 && temp.indexOf("\\end{antiextra}") !=-1)
				{
					int x=temp.indexOf("\\begin{antiextra}");
					int y=temp.indexOf("\\end{antiextra}");
					temp=temp.substring(0,x)+temp.substring(y+"\\end{antiextra}".length());
					//i=y+"\\end{extra}".length();
				}
			}
			else if(temp.indexOf("\\begin{extra}") !=-1 && temp.indexOf("\\end{extra}") !=-1)
			{
				if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR]: This is an E-EXTRA ARTICLE. Please check for the correctness and Paginate Both main and abr tex files.!!!");
					//return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"This is an E-EXTRA ARTICLE.\n Please check for the correctness and Paginate Both main and abr tex files.","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
					}
				System.out.println("Processing e-extra case....");
				abr=temp;

				
				
				while(abr.indexOf("\\begin{extra}") !=-1 && abr.indexOf("\\end{extra}") !=-1)
				{
					//System.out.println(abr.indexOf("\\begin{extra}"));
					//System.out.println(abr.indexOf("\\end{extra}"));
					int x=abr.indexOf("\\begin{extra}",0);
					int y=abr.indexOf("\\end{extra}",0);
					//System.out.println(abr);
					//System.in.read();
					//String abr1=abr.substring(0,x)+abr.substring(y+"\\end{extra}".length());
					String abr1=abr.substring(x,y+"\\end{extra}".length());
					//System.out.println(abr1);
					//System.in.read();
					StringBuffer abr_temp=new StringBuffer(abr);
					abr_temp=abr_temp.delete(x,y+"\\end{extra}".length());
					abr=abr_temp.toString();
					//i=y+"\\end{extra}".length();
					//abr=abr.replaceFirst("\\\\begin\\{extra\\}","");
					//abr=abr.replaceFirst("\\\\end\\{extra\\}","");


				}
				//abr=abr.replaceFirst("\\\\begin\\{extra\\}","");
				//abr=abr.replaceFirst("\\\\end\\{extra\\}","");

				temp=temp.replaceAll("\\\\begin\\{extra\\}","");
				temp=temp.replaceAll("\\\\end\\{extra\\}","");

			}
			else if(temp.indexOf("\\begin{antiextra}") !=-1 && temp.indexOf("\\end{antiextra}") !=-1)
			{	//ATH-9185 S200 Acc. to client only compact-standard should go to web e-extra case - 11 oct 2005
				//Reverted back to old- 11 oct 2005
				/*if(onlineversiontype.equalsIgnoreCase("e-extra"))
				{
					JOptionPane.showMessageDialog(null,"This is an E-EXTRA ARTICLE.\n Please check for the correctness and Paginate Both main and abr tex files.","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
					System.out.println("Processing COMPACT e-extra case....");
					abr=temp;


					abr=abr.replaceAll("\\\\begin\\{antiextra\\}","");
					abr=abr.replaceAll("\\\\end\\{antiextra\\}","");

					while(temp.indexOf("\\begin{antiextra}") !=-1 && temp.indexOf("\\end{antiextra}") !=-1)
					{
						int x=temp.indexOf("\\begin{antiextra}");
						int y=temp.indexOf("\\end{antiextra}");
						temp=temp.substring(0,x)+temp.substring(y+"\\end{antiextra}".length());
						//i=y+"\\end{extra}".length();

					}
				}
				else
				{*/

					temp=temp.replaceAll("\\\\begin\\{antiextra\\}","");
					temp=temp.replaceAll("\\\\end\\{antiextra\\}","");
				//}
				
			}
		}
		/*else if(label_Eappended.equalsIgnoreCase("print"))
		{
			temp=temp.replaceAll("\\\\begin\\{antiextra\\}","");
			temp=temp.replaceAll("\\\\end\\{antiextra\\}","");
		}*/
		//**************
//13/02/2008
if(label_Eappended.equalsIgnoreCase("e-appended"))
{
	//System.out.println("----------------------------------------------------------");
	//abr=mainEappend.toString()+"\r\n"+abrEappend.toString();
	abr=mainEappend.toString();
	temp=mainEappend.toString();
}
//***************
		if(abr.length()>0)
		{
			if(label_Eappended.equalsIgnoreCase("e-appended"))
			{
				abr=abr.replaceFirst("\\\\documentclass\\[","\\\\documentclass[Abridge,");
				abr=abr.replaceFirst("\n","\n%\\\\xEextraPage{}\r\n");
				if(abr.indexOf("\r\n\\startabstract{")!=-1)
				{
					abr=abr.replaceFirst("\\}\r\n\\\\makechaptertitle","\r\n\\\\begin{CiteGuide}\r\n,\r\n\\\\end{CiteGuide}\\}\r\n\\\\makechaptertitle");
				}
				else
				{
					abr=abr.replaceFirst("\\\\makechaptertitle","\r\n\\\\begin{CiteGuide}\r\n,\r\n\\\\end{CiteGuide}\r\n\\\\makechaptertitle");
				}
				if(abr.indexOf("\\begin{CiteGuide}\r\n")!=-1)
				{
					abr=abr.substring(0,abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length())+HeadGroup.eextraAuthor+HeadGroup.eextraTitle+abr.substring(abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length());
				}
			}
			else if(label_Eappended.equalsIgnoreCase("e-extra"))
			{
				abr=abr.replaceFirst("\\\\documentclass\\[","\\\\documentclass[Abridge,");
				abr=abr.replaceFirst("\n","\n%\\\\xEextraPage{}\r\n");
				if(abr.indexOf("\r\n\\startabstract{")!=-1)
				{
					abr=abr.replaceFirst("\\}\r\n\\\\makechaptertitle","\r\n\\\\begin{CiteGuide}\r\n,\r\n\\\\end{CiteGuide}\\}\r\n\\\\makechaptertitle");
				}
				else
				{
					abr=abr.replaceFirst("\\\\makechaptertitle","\r\n\\\\begin{CiteGuide}\r\n,\r\n\\\\end{CiteGuide}\r\n\\\\makechaptertitle");
				}
				/*if(abr.indexOf("\\begin{CiteGuide}\r\n")!=-1)
				{
					abr=abr.substring(0,abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length())+HeadGroup.eextraAuthor+HeadGroup.eextraTitle+abr.substring(abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length());
				}
				*/
					if(abr.indexOf("\\begin{CiteGuide}\r\n")!=-1)
					{
						//System.out.println("Ravi modelStyle--> "+modelStyle);
						if(modelStyle.equalsIgnoreCase("5PlusGSpanish")||modelStyle.equalsIgnoreCase("6PlusSpanish")||modelStyle.equalsIgnoreCase("6PlusSpanishC")||modelStyle.equalsIgnoreCase("-NRL")||modelStyle.startsWith("7Spanish"))
						{
							String comma=", ";
							if(HeadGroup.Spanish_eextraAuthor.endsWith("et al"))
							{
								comma="., ";
							}
							abr=abr.substring(0,abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length())+HeadGroup.Spanish_eextraAuthor+comma+HeadGroup.eextraTitle+abr.substring(abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length());
						}
						else
						{
							abr=abr.substring(0,abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length())+HeadGroup.eextraAuthor+HeadGroup.eextraTitle+abr.substring(abr.indexOf("\\begin{CiteGuide}\r\n")+"\\begin{CiteGuide}\r\n".length());
						}
					}
			}



			File abrF;
			//ravi_
			//isDuckling

			if(xt.DucklingOrderStatus==true)
			 abrF= new File(args[0].substring(0,args[0].lastIndexOf("."))+"-abr.tex");
			else
				abrF= new File(args[0].substring(6,args[0].lastIndexOf("."))+"-abr.tex");
			if(abrF.exists())
				abrF.delete();
			RandomAccessFile abrfin= new RandomAccessFile(abrF, "rw");
			// mukesh for removal of diff on 06-09-08
			abr=abr.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace","\\\\Diff{");
						abr=abr.replaceAll("ThomsonDiff_ThomsonDiffOpenBrace","\\\\Diff{");
			abr=abr.replaceAll("ThomsonDiffCloseBrace","}");
			abr=abr.replaceAll("thomsonbeginTextColor","\\\\begin{TextColor}");
			abr=abr.replaceAll("thomsonendTextColor","\\\\end{TextColor}");

		if(abr.indexOf("RTOPEN************",0)!=-1)
		{
				int pos_query1=0;
			 int epos_query=0;
			StringBuffer tt=new StringBuffer(abr);
			//System.out.println(temp);
			int count=1;
			while(tt.indexOf("RTOPEN************",pos_query1)!=-1)
			{
			 
			 pos_query1=tt.indexOf("RTOPEN************",pos_query1);
			 epos_query=tt.indexOf("************RTCLOSE",pos_query1);
			 
			 tt=tt.delete(pos_query1,epos_query+"************RTCLOSE".length());
			 
			}
			 abr=tt.toString();
		}


			StringBuffer sg1=new StringBuffer(abr);
		 int po1=0;
		 //\(\backslash\)qtoq\{
		 	while((po1=sg1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po1+1))!=-1)
		 	{
		 		int pt1=sg1.indexOf("\\}",po1+1);
		 		if(pt1 !=-1)
		 		{
		 			sg1=sg1.replace(pt1,pt1	+2,"}");
		 			
		 		}
				//System.out.println("Ravi");
		 		sg1=sg1.replace(po1,po1+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
		 	}
			abr=sg1.toString();
			System.out.println("-abr--> "+abr);
//***********<ce:br/>********************
abr=abr.replaceAll("thomsontablenewline ","\\\\tablenewline ");

//***************************************


			//System.out.println("----------"+abr.indexOf("\\\\\n\n"));
			//abr=abr.replaceAll("\\\\\\\\"+"\n\n","\\\\\\\\"+"\n");
			abrfin.writeBytes(abr);
			abrfin.close();
			System.out.println("File \""+args[0]+":Abridged Version\" Converted Successfully.");
		}
		if((label_Eappended.equalsIgnoreCase("print")||label_Eappended.equalsIgnoreCase("e-only"))&& (temp.indexOf("\\begin{antiextra}") !=-1 && temp.indexOf("\\end{antiextra}")!=-1))
		{
			String first="";
			String second="";
			String last="";
			int spos=0;
			int epos=0;
			while (spos!=-1&&epos!=-1)
			{
				spos=temp.indexOf("\\begin{antiextra}",epos);
				if(spos!=-1)
				{
					epos=temp.indexOf("\\end{antiextra}",spos);
					if(epos!=-1)
					{
						first=temp.substring(0,spos)	;
						second=temp.substring(spos,epos+"\\end{antiextra}".length())	;
						last=temp.substring(epos+"\\end{antiextra}".length(),temp.length())	;
						second=second.replaceAll("\\\\begin\\{antiextra\\}","");
						second=second.replaceAll("\\\\end\\{antiextra\\}","");
						StringBuffer bf=new StringBuffer(second);
						int mpos1=0;
						int mpos2=0;
						if(bf.indexOf("\\epsfbox{mmc1.eps}}",0)!=-1)
						{
							mpos1=bf.indexOf("\\epsfbox{mmc1.eps}}",0);
							if(bf.indexOf(".eps}}",mpos1)!=-1)
							{
								mpos2=bf.indexOf("eps}}",0);
								bf=bf.delete(mpos1-4,mpos2+".eps}}".length());
								//bf=bf.delete(mpos1,mpos2+".eps}}".length());
								//bf=bf.insert(mpos1,"}");
							}
							//bf=bf.insert(mpos1,"%");
						}
						second=bf.toString();
						temp=first+second+last;
					}
				}
			}
			//temp=temp.replaceAll("\\\\begin\\{antiextra\\}","");
			//temp=temp.replaceAll("\\\\end\\{antiextra\\}","");
			//Below condition added on 24-10-2016 to remove extra content from TeX file in case of <ce:para view="extended">
			if(label_Eappended.equalsIgnoreCase("print") && temp.indexOf("\\begin{extra}") !=-1){
				while(temp.indexOf("\\begin{extra}") !=-1 && temp.indexOf("\\end{extra}") !=-1)
				{
					int x=temp.indexOf("\\begin{extra}");
					int y=temp.indexOf("\\end{extra}");
					temp=temp.substring(0,x)+temp.substring(y+"\\end{extra}".length());
					System.out.println("REMOVING EXTRA CONTENT...");
				}
				temp=temp.replaceAll("\\\\begin\\{extra\\}","");
				temp=temp.replaceAll("\\\\end\\{extra\\}","");
			}
		}
//***************************************************************************

//**************************************************************************

		File tmpF;
		//System.out.println(xmlHead.abstractGraphical);
		//System.out.println(xmlHead.abstractGraphicalWithResearchHighlight);
		if(abr.length()>0)
		{
			if(!label_Eappended.equalsIgnoreCase("e-appended"))
			{
				temp=temp.replaceFirst("\\\\documentclass\\[","\\\\documentclass[special,");
				
			}
			temp=temp.replaceFirst("\n","\n\\\\Spchar{xxx.e}\r\n");
			if(label_Eappended.equalsIgnoreCase("e-appended"))
			{
				temp=temp.replaceFirst("\\\\Spchar\\{xxx.e\\}","\\\\usepackage{Eappended}\r\n");
				StringBuffer s2=new StringBuffer(temp);
				int in=s2.indexOf("\\end{document}",0);
				if(in!=-1)
				{
					s2.insert(in,"\r\n\\EappendedPagestyle\r\n\r\n\\begin{Eappended}\r\n"+abrEappend+"\r\n\\end{Eappended}\r\n");
					temp=s2.toString();
				}
				
			}
			//tmpF= new File((xt.xmlFile.getName()).substring(0, (xt.xmlFile.getName()).indexOf(".")) +"-main.tex");
			if(xt.DucklingOrderStatus==true)
				tmpF= new File(args[0].substring(0,args[0].lastIndexOf("."))+"-main.tex");
			else
				tmpF= new File(args[0].substring(6,args[0].lastIndexOf("."))+"-main.tex");
		}
		else
		{
			//System.out.println("xt.DucklingOrderStatus====>"+xt.DucklingOrderStatus);
			//System.out.println("2222args[0]========>>"+args[0]);
			if(xt.DucklingOrderStatus==true)
			{
				//commented by debottam tmpF= new File(args[0].substring(0,args[0].lastIndexOf("."))+".tex");
			//tmpF= new File(args[0].substring(6,args[0].lastIndexOf("."))+".tex");
			
			tmpF= new File(args[0].substring(0,args[0].lastIndexOf("."))+".tex");
			//System.out.println("tmpF=======>>"+tmpF.getAbsolutePath());
			}
			else
			tmpF= new File(args[0].substring(6,args[0].lastIndexOf("."))+".tex");
		}
		if(tmpF.exists())
			tmpF.delete();
//label Eappended
		//if(onlineversiontype.equalsIgnoreCase("e-only"))
		if(label_Eappended.equalsIgnoreCase("e-only"))
		{
//			JOptionPane.showMessageDialog(null,"ARTICLE TYPE---->"+onlineversiontype,"ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
			if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[Information]: ARTICLE TYPE---->"+label_Eappended);
					//return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"ARTICLE TYPE---->"+label_Eappended,"ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
				}
			temp=temp.replaceFirst("\\\\documentclass\\[","\\\\documentclass[special,");
			temp=temp.replaceFirst("\n","\n\\\\Spchar{xxx.e}\r\n");
		}
		//else if(onlineversiontype.equalsIgnoreCase("e-extra"))
		else if(label_Eappended.equalsIgnoreCase("e-extra"))
		{
			//JOptionPane.showMessageDialog(null,"ARTICLE TYPE---->"+onlineversiontype,"ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
			if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[Information]: ARTICLE TYPE---->"+label_Eappended);
					//return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"ARTICLE TYPE---->"+label_Eappended,"ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
				}
		}

//copyrightValinOrder
		
		Hashtable hs=new Hashtable();

		hs.put("NO COPYRIGHTSLINE","000");
		hs.put("UNKNOWN","001");
		hs.put("FULL-TRANSFER","002");
		hs.put("SOCIETY","002");
		hs.put("JOINT","002");
		hs.put("US-GOV","003");
		hs.put("CROWN","004");
		hs.put("LIMITED-TRANSFER","005");
		hs.put("OTHER","006");
		hs.put("NO-TRANSFER","007");
		//System.out.println("Copy Right Status in XML-->"+copyrightValinXML);
		copyrightValinXML=	hs.get(copyrightValinXML).toString();
		//System.out.println("Model-->"+modelStyle);
		System.out.println("Copy Right Status in XML-->"+copyrightValinXML);
		System.out.println("Copy Right Status in Order -->"+copyrightValinOrder);
		/*if(!copyrightValinXML.equalsIgnoreCase(copyrightValinOrder))
		{
			if(serverstatus.equals("SERVER"))
				{
					server_Xt.xt_log.info("[ERROR]: Copy Right Status in Order is mismatch with XML copyright status. Please Check..");
					return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"[ERROR]: Copy Right Status in Order is mismatch with XML copyright status. Please Check..","ALERT INFORMATION!!!", JOptionPane.INFORMATION_MESSAGE);
					System.exit(0);
				}
		}*/
		//System.out.println("cmmment_rhtitle "+cmmment_rhtitle);
		/*if(cmmment_rhtitle.length()>0)
		{
			StringBuffer tt=new StringBuffer(temp);
			tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
			temp=tt.toString();
		}*/
		//System.out.println("TEMP==>"+temp);
		if(temp.indexOf("RTOPEN************",0)!=-1)
		{
				int pos_query1=0;
			 int epos_query=0;
			StringBuffer tt=new StringBuffer(temp);
			//System.out.println(temp);
			int count=1;
			if(tt.indexOf("RTOPEN************",pos_query1)!=-1)
			{
				if(tt.indexOf("************RTCLOSE",pos_query1)!=-1)
				{
					while(tt.indexOf("RTOPEN************",pos_query1)!=-1)
					{
						pos_query1=tt.indexOf("RTOPEN************",pos_query1);
						epos_query=tt.indexOf("************RTCLOSE",pos_query1);
						if(count==2)
						XT.cmmment_rhtitle=tt.substring(pos_query1+"RTOPEN************".length(),epos_query);
						count++;
						tt=tt.delete(pos_query1,epos_query+"************RTCLOSE".length());
					}
				}			
			}
			//Added on 05-09-2018, mail by R&D/Georgette
			if(((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))) && (xt.xmlObj.pit.equalsIgnoreCase("ABS")||xt.xmlObj.pit.equalsIgnoreCase("BPH")||xt.xmlObj.pit.equalsIgnoreCase("BRV")||xt.xmlObj.pit.equalsIgnoreCase("CAL")||xt.xmlObj.pit.equalsIgnoreCase("CNF")||xt.xmlObj.pit.equalsIgnoreCase("COR")||xt.xmlObj.pit.equalsIgnoreCase("EDI")||xt.xmlObj.pit.equalsIgnoreCase("EXM")||xt.xmlObj.pit.equalsIgnoreCase("IND")||xt.xmlObj.pit.equalsIgnoreCase("LIT")||xt.xmlObj.pit.equalsIgnoreCase("NWS")||xt.xmlObj.pit.equalsIgnoreCase("PRP"))){
				if(HeadGroup.artDochead.length() > 0){
					tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+HeadGroup.artDochead+"}\r\n\n\n");
				}else{
					tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
				}
			}else{
				tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
			}
//			tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
			 temp=tt.toString();
		}
		else if(modelStyle.toLowerCase().indexOf("6") != -1 || jid.equalsIgnoreCase("HLC")|| jid.equalsIgnoreCase("NRL")||jid.equals("NRLENG")|| jid.equalsIgnoreCase("PROTIS")||jid.equalsIgnoreCase("APRIM")||jid.equalsIgnoreCase("EJPSY")||jid.equalsIgnoreCase("REPOD")||jid.equalsIgnoreCase("RPTOB")||jid.equalsIgnoreCase("RAA")||jid.equalsIgnoreCase("RCHIC")||jid.equalsIgnoreCase("RCHIRA")||jid.equalsIgnoreCase("RPRH")||jid.equalsIgnoreCase("RCHOT")||jid.equalsIgnoreCase("RIEM")||jid.equalsIgnoreCase("CC")||jid.equalsIgnoreCase("EDUMED")||jid.equalsIgnoreCase("CIRGEN")||jid.equalsIgnoreCase("MAGIS")||jid.equalsIgnoreCase("EJFB")||jid.equalsIgnoreCase("EJEPS")||jid.equalsIgnoreCase("ANPSIC")||jid.equalsIgnoreCase("MINCOM")||jid.equalsIgnoreCase("RCHIPE")||jid.equalsIgnoreCase("RCCOT")||jid.equalsIgnoreCase("UROCO")||jid.equalsIgnoreCase("CIRCIR")||jid.equalsIgnoreCase("CIRCEN")||jid.equalsIgnoreCase("RIPS")||jid.equalsIgnoreCase("ACCI")||jid.equalsIgnoreCase("MEI")||jid.equalsIgnoreCase("MEDRE")||jid.equalsIgnoreCase("REU")||jid.equalsIgnoreCase("UROMX")||jid.equalsIgnoreCase("ANCV")||jid.equalsIgnoreCase("ACUP")||jid.equalsIgnoreCase("HGMX")||jid.equalsIgnoreCase("BMHIMX")||jid.equalsIgnoreCase("RARD")||jid.equalsIgnoreCase("RCCAR")||jid.equalsIgnoreCase("PIRO")||jid.equalsIgnoreCase("BJORL")||jid.equalsIgnoreCase("BJORLP")||jid.equalsIgnoreCase("ENDMAG")||jid.equalsIgnoreCase("RCCAN")||jid.equalsIgnoreCase("IJCHP")||jid.equalsIgnoreCase("MEXOFT")||jid.equalsIgnoreCase("RAM")||jid.equalsIgnoreCase("BJAN")||jid.equalsIgnoreCase("BJANE")||jid.equalsIgnoreCase("BJANES")||jid.equalsIgnoreCase("CESJEF")||jid.equalsIgnoreCase("SEDENE")||jid.equalsIgnoreCase("SEDENG")||jid.equalsIgnoreCase("RGMX")||jid.equalsIgnoreCase("RLFA")||jid.equalsIgnoreCase("CESJEF")||jid.equals("ANGIO")||jid.equals("RIFK")||jid.equals("REDAR")||jid.equals("REDARE")||jid.equals("REMLE")||jid.equalsIgnoreCase("DIALIS")||jid.equals("RPSM")||jid.equals("AVDIAB")||jid.equals("MEDIPA")||jid.equals("IMADI")||jid.equals("ANDROL")||jid.equals("ACMX")||jid.equals("INFECT")||jid.equals("REML")||jid.equals("PATOL")||jid.equals("FT")||jid.equals("RECOT")||jid.equals("SEMERG")||jid.equals("CALI")||jid.equals("JHQR")||jid.equals("ENDONU")||jid.equals("ENDINU")||jid.equals("SENOL")||jid.equalsIgnoreCase("ABD")||jid.equalsIgnoreCase("JPED")||jid.equalsIgnoreCase("RPPED")||jid.equalsIgnoreCase("AD")||jid.equalsIgnoreCase("ADENGL")||jid.equalsIgnoreCase("ACURO")||jid.equalsIgnoreCase("ACUROE")||jid.equalsIgnoreCase("RICMA")||jid.equalsIgnoreCase("RPPEDE")||jid.equals("ANPEDI")||jid.equals("ANPEDE")||jid.equals("RCE")||jid.equals("RCENG")||jid.equals("OTORRI")|| jid.equals("GASTRO")|| jid.equals("GASTRE")||jid.equalsIgnoreCase("GINE")||jid.equals("APUNTS")||jid.equals("RPPNEU")||jid.equals("LABCLI")||jid.equals("HIPERT")||jid.equals("ARTERI")||jid.equals("ARTERE")||jid.equals("RH")||jid.equals("ENFI")||jid.equals("ENFIE")||jid.equals("ENFCLI")||jid.equals("ENFCLE")||jid.equalsIgnoreCase("MEDIN")||jid.equalsIgnoreCase("FARMA")||jid.equalsIgnoreCase("FARMAE")||jid.equalsIgnoreCase("RXENG")||jid.equalsIgnoreCase("REPCE")||jid.equalsIgnoreCase("OTOENG")||jid.equalsIgnoreCase("MEDINE")||jid.equalsIgnoreCase("BMHIME")||jid.equalsIgnoreCase("RGMXEN")||jid.equalsIgnoreCase("RX")||jid.equalsIgnoreCase("RCE")||jid.equalsIgnoreCase("TEKHNE")||jid.equalsIgnoreCase("ALLER")||jid.equalsIgnoreCase("GAMO")||jid.equalsIgnoreCase("RMU")||jid.equalsIgnoreCase("OPTOM")||jid.equalsIgnoreCase("BJPT")||jid.equalsIgnoreCase("REPC")||jid.equals("JPG")||jid.equals("JPGE"))
		{	//	System.out.println("XT.model6rhtitle-->"+XT.model6rhtitle);
			StringBuffer tt=new StringBuffer(temp);

			//tt=tt.insert(tt.indexOf("\\lDOInum"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
			if(tt.indexOf("\\copyrightStatus",0)!=-1){
				//Added on 05-09-2018, mail by R&D/Georgette
				if(((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))) && (xt.xmlObj.pit.equalsIgnoreCase("ABS")||xt.xmlObj.pit.equalsIgnoreCase("BPH")||xt.xmlObj.pit.equalsIgnoreCase("BRV")||xt.xmlObj.pit.equalsIgnoreCase("CAL")||xt.xmlObj.pit.equalsIgnoreCase("CNF")||xt.xmlObj.pit.equalsIgnoreCase("COR")||xt.xmlObj.pit.equalsIgnoreCase("EDI")||xt.xmlObj.pit.equalsIgnoreCase("EXM")||xt.xmlObj.pit.equalsIgnoreCase("IND")||xt.xmlObj.pit.equalsIgnoreCase("LIT")||xt.xmlObj.pit.equalsIgnoreCase("NWS")||xt.xmlObj.pit.equalsIgnoreCase("PRP"))){
					if(HeadGroup.artDochead.length() > 0){
						tt=tt.insert(tt.indexOf("\\copyrightStatus",0),"\n\n\\rhtitle{"+HeadGroup.artDochead+"}\r\n\n\n");
					}else{
						tt=tt.insert(tt.indexOf("\\copyrightStatus",0),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
					}
				}else{
					tt=tt.insert(tt.indexOf("\\copyrightStatus",0),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");			
				}
				//tt=tt.insert(tt.indexOf("\\copyrightStatus",0),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");			
				temp=tt.toString();
			}
			//System.out.println("1---XT.model6rhtitle-->"+XT.model6rhtitle);
		}

//10/03/2008
        //else if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench"))|| jid.equalsIgnoreCase("ZOOGA"))//08-04-2015
        else if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH"))|| jid.equalsIgnoreCase("ZOOGA"))
		{		//System.out.println("XT.model6rhtitle-->"+XT.model6rhtitle);
			StringBuffer tt=new StringBuffer(temp);

			//tt=tt.insert(tt.indexOf("\\lDOInum"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
			if(tt.indexOf("\\copyrightStatus",0)!=-1){
				//Added on 05-09-2018, mail by R&D/Georgette
				if(((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))) && (xt.xmlObj.pit.equalsIgnoreCase("ABS")||xt.xmlObj.pit.equalsIgnoreCase("BPH")||xt.xmlObj.pit.equalsIgnoreCase("BRV")||xt.xmlObj.pit.equalsIgnoreCase("CAL")||xt.xmlObj.pit.equalsIgnoreCase("CNF")||xt.xmlObj.pit.equalsIgnoreCase("COR")||xt.xmlObj.pit.equalsIgnoreCase("EDI")||xt.xmlObj.pit.equalsIgnoreCase("EXM")||xt.xmlObj.pit.equalsIgnoreCase("IND")||xt.xmlObj.pit.equalsIgnoreCase("LIT")||xt.xmlObj.pit.equalsIgnoreCase("NWS")||xt.xmlObj.pit.equalsIgnoreCase("PRP"))){
					if(HeadGroup.artDochead.length() > 0){
						tt=tt.insert(tt.indexOf("\\copyrightStatus"),"\n\n\\rhtitle{"+HeadGroup.artDochead+"}\r\n\n\n");
					}else{
						tt=tt.insert(tt.indexOf("\\copyrightStatus"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
					}
				}else{
					tt=tt.insert(tt.indexOf("\\copyrightStatus"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
				}
//				tt=tt.insert(tt.indexOf("\\copyrightStatus"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
			}else
			{
				if(((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))) && (xt.xmlObj.pit.equalsIgnoreCase("ABS")||xt.xmlObj.pit.equalsIgnoreCase("BPH")||xt.xmlObj.pit.equalsIgnoreCase("BRV")||xt.xmlObj.pit.equalsIgnoreCase("CAL")||xt.xmlObj.pit.equalsIgnoreCase("CNF")||xt.xmlObj.pit.equalsIgnoreCase("COR")||xt.xmlObj.pit.equalsIgnoreCase("EDI")||xt.xmlObj.pit.equalsIgnoreCase("EXM")||xt.xmlObj.pit.equalsIgnoreCase("IND")||xt.xmlObj.pit.equalsIgnoreCase("LIT")||xt.xmlObj.pit.equalsIgnoreCase("NWS")||xt.xmlObj.pit.equalsIgnoreCase("PRP"))){
					if(HeadGroup.artDochead.length() > 0){
						tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+HeadGroup.artDochead+"}\r\n\n\n");
					}else{
						tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
					}
				}else{
					tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
				}
//				tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.model6rhtitle+"}\r\n\n\n");
			}
			temp=tt.toString();
		}
		//System.out.println("1 Model-->"+modelStyle);
		//temp = temp.replaceAll("\r\n\r\n\r\n","\r\n");
		
		
		if(articleType.equalsIgnoreCase("FR"))
		{
			temp=temp.replaceAll("[ ]+"," ");
			temp=temp.replaceAll("[ ]\\,[ ]","\\, ");
			temp=temp.replaceAll("[ ]\\.[ ]","\\. ");
			//temp=temp.replaceAll(";[ ]","\\\\,; ");
			temp=temp.replaceAll("![ ]","\\\\,! ");
			//temp=temp.replaceAll("\\?[ ]","\\\\,\\? ");//block by Ravi As this is not required in tex conversion//23/02/2009
			//temp=temp.replaceAll(":[ ]","~: ");
			temp=temp.replaceAll("[ ]+"," ");
			temp=temp.replaceAll("[ ]\\\\,","\\\\,");
			temp=temp.replaceAll("\\\\,[ ]","\\\\,");
			temp=temp.replaceAll("[ ]~","~");
			temp=temp.replaceAll("~[ ]","~");
			temp=temp.replaceAll("\\\\,\\\\,","\\\\,");
			temp=temp.replaceAll("[~]+","~");
			
		}
		//System.out.println("2 Model-->"+modelStyle);

		//************************************************
		//[Added By Ravi 03/05/2007]
		//Change Request By Subrata. 
		//Change Point: deleting query tag in xml file
		 StringBuffer sg=new StringBuffer(temp);
		 int po=0;
		 //\(\backslash\)qtoq\{
		 	while((po=sg.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po+1))!=-1)
		 	{
		 		int pt=sg.indexOf("\\}",po+1);
		 		if(pt !=-1)
		 		{
		 			sg=sg.replace(pt,pt+2,"}");
		 			
		 		}
				//System.out.println("Ravi");
		 		sg=sg.replace(po,po+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
		 	}
			temp=sg.toString();
			
			temp=temp.replaceAll(" ThomsonDigitalOpenBrace ","{");
			temp=temp.replaceAll(" ThomsonDigitalCloseBrace","}");

			temp=temp.replaceAll("ThomsonDigitalMiddle\\\\_","\\\\");
			temp=temp.replaceAll("ThomsonDigitalOpenBrace","{");
			temp=temp.replaceAll("ThomsonDigitalCloseBrace","}");
			
			//***********************[PDF Diff]**************************
			//Date: 26/03/2008

			
			temp=temp.replaceAll("ThomsonDiffOpenBrace","{");
			temp=temp.replaceAll("ThomsonDiffCloseBrace","}");
			temp=temp.replaceAll("ThomsonDiff\\\\_","\\\\Diff");
			temp=temp.replaceAll("ThomsonDiff\\_","\\\\Diff");

			temp=temp.replaceAll("thomsondiff\\\\_thomsondiffopenbrace","\\\\Diff{");
			temp=temp.replaceAll("thomsondiffclosebrace","}");
			temp=temp.replaceAll("thomsonbeginTextColor","\\\\begin{TextColor}");
			temp=temp.replaceAll("thomsonendTextColor","\\\\end{TextColor}");

			//sb2=sb2.insert(pos_query,"thomson_begin_SmallText_close"+lable+"thomson_end_SmallText_close");
			temp=temp.replaceAll("thomson\\\\_begin\\\\_SmallText\\\\_close","\n\\\\begin{SmallText}");
			temp=temp.replaceAll("thomson\\\\_end\\\\_SmallText\\\\_close","\n\\\\end{SmallText}");

			//temp=temp.replaceAll("\\*-\\*-\\*-\\*-\\*-\\*-\\*","");//30-09-2010
			//************************************************************
			
			/**
			 *[05/01/2008]
			 *Added By : Ravi
			 *Change Point:  Color tag handled
			 *Change Request By: TPMS
			 **/
			 /* handle diff tag in book mark
				Date : 11/09/2008
			*/
			 int i=0;
			 int ii=0;
			 int iii=0;
			 StringBuffer sbtemp=new StringBuffer(temp);
			while(i!=-1)
			{
				i=sbtemp.indexOf("\\addbookmark{}{",ii);
				
				if(i!=-1)
				{
					ii=sbtemp.indexOf("}",i+19);
					if(ii!=-1)
					{
						String t=sbtemp.substring(i,ii+1);
						
						if(t.indexOf("\\Diff{",0)!=-1)
						{
							iii=sbtemp.indexOf("\\Diff{",i);
							sbtemp=sbtemp.delete(iii,ii+1);
						}
					}
				}
				//System.out.println("1 ravi ");
			}
			//System.out.println("Ravi");
			temp=sbtemp.toString();

			//end handle diff tag in book mark
			temp=temp.replaceAll("ThomsonBackS","\\\\");
			temp=temp.replaceAll("ThomsonDigitalColorStart","\\\\JIDColor{");
			temp=temp.replaceAll("ThomsonDigitalColorEnd","}");


			//mukesh_GulliverItalicvee
			temp=temp.replaceAll("thomsonGulliverItalicvee","{\\\\GulliverItalicvee}");
			temp=temp.replaceAll("thomsonGulliverBoldItalicvee","{\\\\GulliverBoldItalicvee}");
			temp=temp.replaceAll("thomcomment:","\\\\");
			temp=temp.replaceAll(":newline","\n");

			//end 
			//[Added By Ravi 11/07/2007]
			//Change Request By Vivek through TPMS. 
			//Change Point: deleting \\(\\backslash\\)MIM\\{ tag in xml file
			StringBuffer sg1=new StringBuffer(temp);
			int po1=0;
		 
		 	while((po1=sg1.indexOf("\\(\\backslash\\)MIM\\{",po1+1))!=-1)
		 	{
		 		int pt=sg1.indexOf("\\}",po1+1);
		 		if(pt !=-1)
		 		{
		 			sg1=sg1.replace(pt,pt+2,"}");
		 			
		 		}
		 		sg1=sg1.replace(po1,po1+"\\(\\backslash\\)MIM\\{".length(),"\\MIM{");
				//System.out.println("----Ravi");
		 	}
			//System.out.println("Ravi");
		 	temp=sg1.toString();
		//**********************
	/**
	*[18/12/2007]
	* Added By Ravi
	* Change Point : Quel{} is added in case of ce:para role="question"
	* Change Request By : TPMS.
	*/
			temp=temp.replaceAll("TPStartQuel","\\\\Quel{");
			temp=temp.replaceAll("TPEndQuel","}");
		//************************************************
			if(!titlequery.equals(""))
			{
				titlequery=titlequery.replaceAll("\\\\", "\\\\\\\\");
				temp=temp.replaceAll("\\\\rhtitle\\{", "\\\\rhtitle\\{"+titlequery);
				titlequery="";

			}

//***********<ce:br/>********************
temp=temp.replaceAll("thomsontablenewline","\\\\tablenewline");

//***************************************

		//[Added By Ravi 03/05/2007]
		//Change Request By Subrata. 
		//Change Point: deleting query tag in xml file
		//System.out.println(temp);
		
		if(temp.indexOf("miscTitle{",0)!=-1)
		{//System.out.println("1");
			if(temp.indexOf("Alttitle{",0)!=-1)
			{
				//System.out.println("2");
			if((xt.xmlObj.pit.equalsIgnoreCase("brv"))&&( xt.modelStyle.startsWith("7Spanish")))//07-09-2010
				{
				}
			else if((xt.xmlObj.pit.equalsIgnoreCase("brv"))&& checkModelDModelEVal)//07-09-2010
				{
				}
				else
				temp=temp.replaceFirst("Alttitle\\{","miscAlttitle\\{");
				//System.out.println("3");
			}
		}

		//finalgraphabstractcontent_Highlight
		Hashtable hightLightHash=new Hashtable();//10-06-2011
		hightLightHash=xt.GetHighLightCutOff();//10-06-2011
		String temp_aid=xt.xmlObj.aid;
		String t_aid="";
		boolean check_highlight=false;
		if(temp_aid.indexOf(".",0)!=-1)
		{
			temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
		}
		if(hightLightHash.containsKey(jid.toString()))
		{
			t_aid=hightLightHash.get(jid).toString();
			if(Integer.parseInt(temp_aid)>=Integer.parseInt(t_aid.trim()))
			{
				check_highlight=true;
			}
		}
		if(check_highlight)
		{
			HeadGroup.finalgraphabstractcontent_graphical=HeadGroup.finalgraphabstractcontent_graphical.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace","\\\\Diff{");
			HeadGroup.finalgraphabstractcontent_graphical=HeadGroup.finalgraphabstractcontent_graphical.replaceAll("ThomsonDiffCloseBrace","}");
			StringBuffer s=new StringBuffer(temp);
			int io=s.indexOf("\\startabstract{",0);
			if(HeadGroup.finalgraphabstractcontent_graphical.length()>0)
			{
				if(io!=-1)
				{
					if(!jid.equalsIgnoreCase("PHARMA")){//Added on 14-08-2019 Graphical abstract for PHARMA
						s=s.insert(io,"\\GAbs{%\r\n"+HeadGroup.finalgraphabstractcontent_graphical+"}\r\n");
					}
				}
			}
			temp=s.toString();
			//finalgraphabstractcontent_research
			if(HeadGroup.finalgraphabstractcontent_research.length()>0)
			{
				//System.out.println("HeadGroup.finalgraphabstractcontent_research===>>"+HeadGroup.finalgraphabstractcontent_research);
				HeadGroup.finalgraphabstractcontent_research=HeadGroup.finalgraphabstractcontent_research.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace","\\\\Diff{");
				HeadGroup.finalgraphabstractcontent_research=HeadGroup.finalgraphabstractcontent_research.replaceAll("ThomsonDiffCloseBrace","}");
				s=new StringBuffer(temp);
				io=s.indexOf("\\startabstract{",0);
				if(io!=-1)
				{
					
					//System.out.println("HeadGroup.finalgraphabstractcontent_research===>>"+HeadGroup.finalgraphabstractcontent_research);
					s=s.insert(io,"\\ResH{%\r\n"+HeadGroup.finalgraphabstractcontent_research+"}\r\n");
				}
				else
				{
					int rh=s.indexOf("\\makechaptertitle",0);
					if(rh!=-1)
					{
						s=s.insert(rh,"\\ResH{%\r\n"+HeadGroup.finalgraphabstractcontent_research.trim()+"\r\n");
					}
				}
			}
			s.insert(s.indexOf("\\documentclass[") + "\\documentclass[".length(), "GARH,");
			temp=s.toString();


			
		}
		temp=temp.replaceAll("thomsonGulliverItalicvee","{\\\\GulliverItalicvee}");
		temp=temp.replaceAll("\\(\\\\backslash\\\\\\)begin\\\\\\{Cochrane\\\\\\}","begin{Cochrane}");
		temp=temp.replaceAll("\\(\\\\backslash\\\\\\)end\\\\\\{Cochrane\\\\\\}","end{Cochrane}");
		//System.out.println("111xmlObj.jid===>>"+jid);
		//System.out.println("111xmlObj.aid===>>"+xt.xmlObj);
		
		//System.in.read();
		File f= new File(args[0].substring(0,args[0].lastIndexOf("."))+".xml");
		RandomAccessFile ra= new RandomAccessFile(f, "rw");
		ra.writeBytes(rename_main_xml_file);
		ra.close();
		//System.out.println("111xmlObj.jid===>>"+jid);
//end for query tag [03/05/2007]
//*************************************************************
		//System.out.println(HeadGroup.finalgraphabstractcontent_main);
		//28/01/2008
		/*if(HeadGroup.finalgraphabstractcontent_main.length()>0)
		{
			HeadGroup.finalgraphabstractcontent_main=HeadGroup.finalgraphabstractcontent_main.substring(HeadGroup.finalgraphabstractcontent_main.indexOf("\\begin{abstract}",0),HeadGroup.finalgraphabstractcontent_main.indexOf("\\end{abstract}",0)+"\\end{abstract}".length());
			HeadGroup.finalgraphabstractcontent_main="\r\n\r\n%% Below text is a Graphical Abstract part\r\n%% Never print in main pdf file.\r\n%% This is for stripins creations only\r\n%% This text should be placed after end of the document\r\n\r\n\\begin{graphicalabstract}\r\n"+HeadGroup.finalgraphabstractcontent_main+"\\end{graphicalabstract}";
			temp+=HeadGroup.finalgraphabstractcontent_main;
			HeadGroup.finalgraphabstractcontent_main="";
		}*/
		////28/01/2008
		RandomAccessFile fin= new RandomAccessFile(tmpF, "rw");
		fin.writeBytes(temp);
		fin.close();
		
		//***********************************[30-09-2010]***********
		
		fin= new RandomAccessFile(tmpF, "rw");
		String L="";
		StringBuffer sg_resus=new StringBuffer();
		while((L=fin.readLine())!=null)
		{
			
			if(L.indexOf("\\Dummay_Section_Title",0)==-1)
			{
				sg_resus.append(L+"\r\n");
			}
		}
		fin.close();
		fin= new RandomAccessFile(tmpF, "rw");
		fin.writeBytes(sg_resus.toString());
		fin.close();
		
		//***********************************[30-09-2010]***********
		xt.lineBreak(tmpF);System.out.println();
		System.out.println("File \""+args[0]+"\" Converted Successfully...\n\n");
		System.exit(0);
		return true;
	}//end of main()
	public static String readxml(File xmlFile) throws IOException{
		String value="";
//		XT xt = new XT();
		RandomAccessFile fin4 = new RandomAccessFile(xmlFile, "rw");
		String s4="";
		int ecomIndex=0;
		StringBuffer sbtext=new StringBuffer();
		String str="";
		while((str=fin4.readLine())!=null){
			sbtext.append(str);
		}
		fin4.close();
		return sbtext.toString();
	}
	
	
	public static String getTagValue(File xmlFile, String tag) throws IOException
	{
		String tagValue="";
		String value="";
		RandomAccessFile fin4 = new RandomAccessFile(xmlFile, "rw");
		String s4="";
		int ecomIndex=0;
		StringBuffer sbtext=new StringBuffer();
		String str="";
		while((str=fin4.readLine())!=null){
			sbtext.append(str);
			//System.out.println("sbtext===>"+sbtext.toString());
		}
		fin4.close();
		value=sbtext.toString();
		Pattern p1 = Pattern.compile("<"+tag+"(>| ([^<>]+)>)(.*?)</"+tag+">",Pattern.DOTALL);
		Matcher m1 = p1.matcher(value);
		if(m1.find()){
			tagValue=m1.group(3);
			//System.out.println(tag+" found");
		}else{
			//System.out.println(tag+" not found");
		}
		return  tagValue;
	}
	
	public static boolean getTagValueForPalwor(File xmlFile, String tag) throws IOException
	{
		String tagValue="";
		String value="";
		RandomAccessFile fin4 = new RandomAccessFile(xmlFile, "rw");
		String s4="";
		int ecomIndex=0;
		StringBuffer sbtext=new StringBuffer();
		String str="";
		while((str=fin4.readLine())!=null){
			sbtext.append(str);
			//System.out.println("sbtext===>"+sbtext.toString());
		}
		fin4.close();
		value=sbtext.toString();
		Pattern p1 = Pattern.compile("<"+tag+"(>| ([^<>]+)>)(.*?)</"+tag+">",Pattern.DOTALL);
		Matcher m1 = p1.matcher(value);
		if(m1.find())
		{
			tagValue=m1.group(3);
			int checkLIT = tagValue.indexOf("<!--<LIT-Au>-->");
			if(checkLIT != -1)
			{
				forpalworVal=true;
			}
			else
			{
				forpalworVal=false;
			}
		}
		else
		{
			//System.out.println(tag+" not found");
		}
		return  forpalworVal;
	}
	
	public static File getALTEComponent(File fname) throws IOException
	{
		String value="";
		int ecomIndex=0;
		RandomAccessFile fin4 = new RandomAccessFile(fname, "rw");
		String s4="";
		StringBuffer sb=new StringBuffer();

		do
		  {
			if((s4 = fin4.readLine()) == null)
		      {  break;
		      }
			else
			  {
				sb.append(s4);
				//System.out.println("  sb ::: "+sb);
			  }
		  }
		while(true);
		fin4.close();
		
		value=sb.toString();
		ecomIndex = value.indexOf("ce:e-component");
		if(ecomIndex != -1)
		{
		//ecomIndex = value.indexOf("ce:e-component", ecomIndex);
		value=value.replaceAll("<ce:e-component ", "<ce:figure ");
		value=value.replaceAll("</ce:e-component>", "</ce:figure>");
		
		int altIndex = value.indexOf("ce:alt-e-component");
		if (altIndex != -1)
			{
				value=value.replaceAll("<ce:caption.*?>(.*?)<ce:alt-e-component(.*?)>","");
				value=value.replaceAll("</ce:alt-e-component>","");				
				
				PrintWriter out= new PrintWriter(new BufferedWriter(new FileWriter(fname)));
				
				out.write(value, 0, value.length());
				out.flush();            
				out.close();				
				
			}
		}
		return  fname;
	}

	public void lineBreak(File fname)
	{
		StringBuffer str = new StringBuffer();
		String str1 = new String();
		int tbl = 1,fig =1,bib=1,eq=0;
		int dollCount=0;
		String dolChar = new String();
		String prevLine = new String();
		String nextLine = new String();
		long filePointer = 0;

		try
		{
			RandomAccessFile fin = new RandomAccessFile(fname,"r");

			while((str1 = fin.readLine())!=null)
			{
				str1= xmlObj.replaceStr(str1, "\\) }}", "\\)}}");
				str1= xmlObj.replaceStr(str1, "`` ", "``");
				str1= xmlObj.replaceStr(str1, "\\\\rm\\{\\{\\\\it", "\\\\rm\\{{\\\\rm");//changed 0n 16-may-2005;
//				str1= xmlObj.replaceStr(str1, "'' ", "''");
//				str1= xmlObj.replaceStr(str1, "' ", "'");
				str1= xmlObj.replaceStr(str1, "}  ", "} ");
				str1= xmlObj.replaceStr(str1, "` ", "`");
				//System.out.println("str1---- "+str1);
				//str1= xmlObj.replaceStr(str1, "\\\\\\\\"+"\n\n","\\\n");
				if(str1.startsWith("\\begin{equation}") || str1.startsWith("\\begin{eqnarray}"))
				{
					eq=1;
				}
				else if(str1.startsWith("\\end{equation}") || str1.startsWith("\\end{eqnarray}"))
				{
					eq=0;
				}
				if(str1.indexOf("\\prime} ^{\\prime")>0)
				{
					while(str1.indexOf("\\prime} ^{\\prime")>0)
					{
						int length=str1.indexOf("\\prime} ^{\\prime");
						str1 = str1.substring(0,length)+"\\prime\\prime"+str1.substring(length+"\\prime} ^{\\prime".length());
					}
				}
				if(str1.indexOf("\\prime}\\)\\(^{\\prime")>0)
				{
					while(str1.indexOf("\\prime}\\)\\(^{\\prime")>0)
					{
						int length=str1.indexOf("\\prime}\\)\\(^{\\prime");
						str1 = str1.substring(0,length)+"\\prime\\prime"+str1.substring(length+"\\prime}\\)\\(^{\\prime".length());
					}
				}
				if(str1.length()==0)
				{
					filePointer = fin.getFilePointer();
					if(prevLine.startsWith("\\begin{"))
					{
						str1="";
					}
					else
					{
						nextLine = fin.readLine();
						if(nextLine.startsWith("\\end{"))
						{
							str1="";
						}
						fin.seek(filePointer);

					}
				}

				if(str1.length() > 75)
				{
					str1 = line(str1);
				}

				prevLine = str1;
				/*while(str1.indexOf("\\)\\(") > 0)
				{
					int dolIndex =0;
					dolIndex = str1.indexOf("\\)\\(");
					str1 = str1.substring(0,dolIndex)+str1.substring(dolIndex+2);
				}*/
				str.append(str1+"\r\n");
			}
			fin.close();
			RandomAccessFile fout = new RandomAccessFile(fname,"rw");
			//System.out.println("---------"+str.indexOf("\\\\\r\n\r\n"));
			//if((modelStyle.equalsIgnoreCase("-MODDFrench"))||(modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
			if((modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
			{
				str=new StringBuffer(new String(str.toString()).replaceAll("\\\\\\\\"+"\r\n\r\n\r\n","\\\\\\\\"+"\r\n"));
				str=new StringBuffer(new String(str.toString()).replaceAll("\\\\\\\\"+"\r\n\r\n","\\\\\\\\"+"\r\n"));
			}
			fout.writeBytes(str.toString());
			fout.close();
		}
		catch(IOException io)
		{
			System.out.println("Error in breaking line in lineBreak() function"+io.getMessage());
		}
	}

	private String line(String str1)
	{
		String t = new String();
		int k =0;
		while(true)
		{
			k =str1.indexOf(" ",65);
			if(k >0)
			{
				if (str1.length() ==k+1)
				{
					t = t+ str1;
					break;
				}
				t = t + str1.substring(0,k)+"\r\n";
				str1 = str1.substring(k);
			}
			else
			{
				t = t+ str1;
				break;
			}
		}
		return t;
	}

	void modelDBFRead() throws IOException
	{
		RandomAccessFile mDBF = new RandomAccessFile(modelFilePath,"r");
		String mdbLine = new String();
		String line = new String();
		while((mdbLine=mDBF.readLine())!=null)
		{
			if(mdbLine.startsWith(jid+";") == true)
			{
				line = mdbLine;
			}
		}

		if(line.length()>0)
		{
			line=line.substring(line.indexOf(";")+1);
			line=line.substring(line.indexOf(";")+1);
			line=line.substring(0,line.indexOf(";"));

			bibStyle = line;
			//System.out.println("bibStyle===>"+bibStyle);
		}
		else
		{
			System.out.println("ERROR [XT]: Refernece [Bibliography] Information not Found For '"+jid+"'");
			System.exit(0);
		}

		mDBF.close();
	}

	public void getStyles()throws FileNotFoundException, IOException
	{
		RandomAccessFile mDBF = new RandomAccessFile(modelFilePath,"r");
		if (!(new File(modelFilePath)).exists())
		{
			System.out.println("ERROR [TexConversion] :: One or More Source Files Missing");
			System.exit(0);
		}
		System.out.println("Check jid "+jid+" in : "+modelFilePath);
		BufferedReader br=new BufferedReader(new FileReader(modelFilePath));
		String lineStr="";
		boolean found=false;
		while ((lineStr=br.readLine())!=null)
		{
			if(lineStr.startsWith(jid+";"))
			{
				StringTokenizer token=new StringTokenizer(lineStr,";");
				found=true;
				token.nextToken();
				int i=1;
				do
				{
					String tokenStr=token.nextToken();
					if(i==1)
					{
						modelStyle=tokenStr;
						//*****************************[Adding FMS work with modleStyle 23/03/2009]*******************
						loadFMSModelStyle();
						
						ArrayList arModel=new ArrayList();
						
						ArrayList ArrJid=new ArrayList();
						Enumeration e = FMSModel.keys();
						while(e.hasMoreElements())
						{
							String key = (String)(e.nextElement());
								ArrJid.add(key);
						}
						if(ArrJid.contains(jid))
						{
							String Temp_Model_Name=FMSModel.get(jid).toString();
							if(Temp_Model_Name.indexOf("##",0)!=-1)
							{
								String [] arrTemp=Temp_Model_Name.split("##");
								for(int con=0; con < arrTemp.length; con++)
								{
									arModel.add(arrTemp[con]);
								}
							}
							else
							{
								arModel.add(Temp_Model_Name);
							}
							if((arModel !=null && arModel.size()>0)&& (arModel.contains(modelStyle)))
							{
								modelStyle=modelStyle+"FMS";
							}
						}
						//**************[END]********[Adding FMS work with modleStyle 23/03/2009]*************************
					}
					if(i==2)
					{
						bibStyle=tokenStr;
						//commented on 27.11.2014 request from TexR&D
						/*if(jid.equalsIgnoreCase("CHROMA") && Integer.parseInt(aid)>355466)
						{
							ReadForEextra rof= new ReadForEextra(jid.toUpperCase(),aid,stage);
							if(rof.readOrderFileForItemGroupDescription())
							{
								System.out.println("Reference style 1a to be applied for this <item-group> and <item-group-description>");
								bibStyle="1a";
							}
						}*/
						//System.out.println("bibStyle : "+bibStyle);
					}
					if(i==3)
						ackStyle=tokenStr;
					if(i==4)
						fntStyle=tokenStr;
					if(i==5)
						piiStyle=tokenStr;
					i++;
				}while(token.hasMoreElements());
				//System.out.println("FONT STYLE"+fntStyle);
				//System.exit(0);
			}
		}
		//System.out.println("modelFilePath : "+modelFilePath+"\nbibStyle : "+bibStyle);
		//System.in.read();

		if (!found)
		{
			System.out.println("ERROR [TexConversion] :: Journal Information Not Found");
			System.exit(0);
		}
	}
	
	   /*
		* Added By Arvind [25_04_2007]
		* Change Request By: Vivek
		* Change Point : 1.Please use new package in tex file after usepackage{NewTwoHyphenCont} usepackage{FigTop}
		*				 2.No need of onecolumn in documentclass option globally only specific journals required 
		*				   onecolumn option. OneColumn journals list: JAMM JEBO EDUREV ACN AIP TSC CLIPOL EARCHI 
		*				   GEOD INFBEH MATBEH MATCOM PEVA PUBREL WAMOT PRO 
		*				 3.Stage="S100-draft" and Stage="S200-draft" Stage="S300-Web draft" should be used in
		*				   documentclass option. 

		*/
	//public static void onecol_Journal_List(String onecol_Journal_Path) 
	public static void onecol_Journal_List(String tempaid) 
	{
		
		OneCol_Journal=new Vector();
	

		String t_aid=tempaid.toUpperCase().trim();

		if(t_aid.indexOf(".")!=-1)
		{
			t_aid=t_aid.substring(0,t_aid.indexOf("."));
			
		}
		//System.out.println("t_aid=====> "+t_aid);

			/*
			OneCol_Journal.addElement("JAMM");
			OneCol_Journal.addElement("JEBO");
			OneCol_Journal.addElement("EDUREV");
			OneCol_Journal.addElement("ACN");
			//OneCol_Journal.addElement("AIP");
			OneCol_Journal.addElement("TSC");
			OneCol_Journal.addElement("CLIPOL");
			if(Integer.parseInt(t_aid)< 479)
			OneCol_Journal.addElement("EARCHI");
			//OneCol_Journal.addElement("GEOD");//Blocked by Ravi dated [08/06/2007] By Vivek
			OneCol_Journal.addElement("INFBEH");
			OneCol_Journal.addElement("MATBEH");
			OneCol_Journal.addElement("MATCOM");
			OneCol_Journal.addElement("PEVA");
			OneCol_Journal.addElement("PUBREL");
			OneCol_Journal.addElement("WAMOT");
			//OneCol_Journal.addElement("PRO");//Blocked by Ravi dated [23/11/2007] By Vivek
			//OneCol_Journal.addElement("COMPAG");//Blocked by Ravi dated [23/11/2007] By Vivek
			//OneCol_Journal.addElement("SON");//Blocked by Ravi dated [23/11/2007] By Vivek
			//OneCol_Journal.addElement("SOCECO");//Added on[26/07/2007] Requested By TPMS
			OneCol_Journal.addElement("JOI");//Added on[03/08/2007] Requested By TPMS
			OneCol_Journal.addElement("JFD");//Added on[03/08/2007] Requested By TPMS
			OneCol_Journal.addElement("MATECO");//Added on[20/08/2007] Requested By TPMS
			//OneCol_Journal.addElement("YMARE");//Added on[27/08/2007] Requested By TPMS
			// YMARE removed by mukesh on 15-10-08 request by TPMS
			//STRECO revoved by Mukesh on 02-09-08  Requested By TPMS
			//OneCol_Journal.addElement("STRECO");//Added on[18/09/2007] Requested By TPMS
			OneCol_Journal.addElement("ACCFOR");//Added on[20/10/2007] Requested By TPMS
			OneCol_Journal.addElement("LINEDU");//Added on[19/11/2007] Requested By TPMS
			OneCol_Journal.addElement("JHE");//Added on[20/11/2007] Requested By TPMS
			OneCol_Journal.addElement("ACCAUD");//Added on[23/01/2008] Requested By TPMS
			OneCol_Journal.addElement("CHIABU");//Added on[17/03/2008] Requested By TPMS
			//OneCol_Journal.addElement("BC");//Added on[08/08/2008] Requested By TPMS
			OneCol_Journal.addElement("YICCN");//Added on[16/09/2008] Added by mukesh Requested By TPMS
			OneCol_Journal.addElement("COCOMP");//Added on[23-09-2008] Added by mukesh Requested By TPMS
			OneCol_Journal.addElement("YCPAC");//Added on[23-06-2009] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("ANIFEE");//Added on[01-10-2009] Added by Ravi Shekhar Requested By TPMS

			OneCol_Journal.addElement("ASIECO");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("IJIR");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("JFTR");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("JIJER");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("RASD");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("RIDD");//Added on[19-02-2010] Added by Ravi Shekhar Requested By TPMS

			OneCol_Journal.addElement("POLSOC");//Added on[20-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("RIOB");//Added on[20-02-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("YCSLA");//Added on[01-05-2010] Added by Ravi Shekhar Requested By TPMS
			OneCol_Journal.addElement("YLMOT");//Added on[11-10-2010] Added by Ravi Shekhar Requested By TPMS
*/
		////////////////////////////////////////

		RandomAccessFile OneCol=null;
		String line = new String();
		try
		{
			OneCol = new RandomAccessFile(databasePath + "onecolumn.dbf","r");
			while((line=OneCol.readLine())!=null)
			{
				String temp=line;
				if(temp.indexOf("ONECOLUMN=",0)!=-1)
				{
					//System.out.println("line------>>"+temp);
					temp=temp.substring(temp.indexOf("ONECOLUMN=",0)+"ONECOLUMN=".length(),temp.length());
					
					OneCol_Journal.addElement(temp);
				}
				
				//System.out.println("line------>>"+OneCol_Journal);
			}
			OneCol.close();
			//System.out.println("line------>>"+OneCol_Journal);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	///////////////////[25-04-2007]////////////////////////
	
	//GulliverModels.dbf//16/02/2008
	public static void loadGulliverModel() throws IOException
	{
		RandomAccessFile plusList = new RandomAccessFile(databasePath + "GulliverList.dbf","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			if(plusLine.indexOf("<-->") != -1)
			{					
				String str[]=plusLine.split("<-->");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				Guli_Table.put(str[0].toUpperCase(),str[1]);
			}
		}
				plusList.close();
	}
public static boolean NewDOI(String jid,String aid) //22-03-2012
	{

		RandomAccessFile NewDOIList;	
		String DoiLine = new String();
		try
		{
		NewDOIList = new RandomAccessFile(databasePath + "NewDOI.dbf","r");	
		while((DoiLine=NewDOIList.readLine())!=null)
		{
			if(DoiLine.indexOf("<<>>") != -1)
			{					
				String str[]=DoiLine.split("<<>>");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				if(str[0].equalsIgnoreCase(jid)&&Integer.parseInt(str[1])<=Integer.parseInt((aid)))
				{
					return true;
				}
				//New_DOI_Table.put(str[0].toUpperCase(),str[1]);
			}
		}
				NewDOIList.close();
		}
		catch (Exception e)
		{
			System.out.println("[Error]-Unable to read from NEWDOI Cut Off file\n");
			return false;
		}
		return false;
	}

public static boolean NewDOIChange(String jid,String aid) //22-03-2012
{
	Properties prop  = new Properties();
	prop = loadProp(databasePath +File.separator+"NEWDOIChange.dbf");
	try{
		int cutoff = Integer.parseInt(prop.getProperty(jid, "0").trim());
		int itemaid = Integer.parseInt(aid);
		//			if(cutoff<itemaid)
		if(itemaid>cutoff)
		{
			return true;
		}else{
			return false;
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	return false;
}

public static boolean isAcanWorkflow(String jid,String aid) //22-03-2012
{
	Properties prop  = new Properties();
	try{
		prop = loadProp(databasePath +"ACAN_Workflow.dbf");
		if(prop.getProperty(jid, "XXX").equalsIgnoreCase("TRUE")){
			int cutoff = Integer.parseInt(prop.getProperty(jid+"_CUTOFF", "0").trim());
			int itemaid = Integer.parseInt(aid);
			if(itemaid>=cutoff)
			{
				System.out.println(jid+"_"+aid+" is ACAN workflow item, cutoff is "+cutoff);
				if(stage.toUpperCase().startsWith("S200")){
					System.out.println("ACAN ITEM STAGE STARTS WITH "+stage.toUpperCase()+" :: ALLOWED.");
					return true;
				}else{
					System.out.println("ACAN ITEM STAGE IS "+stage.toUpperCase()+" :: NOT ALLOWED ACAN UPDATES.");
					return false;
				}
			}else{
				System.out.println(jid+"_"+aid+" is less than ACAN cutoff number. CUTOFF::"+cutoff+" AID::"+aid);
				return false;
			}
		}else{
			System.out.println("JID "+jid+" not exist in "+databasePath +"ACAN_Workflow.dbf");
			return false;
		}
	}catch(Exception e){
		System.out.println("Not able to check "+jid+"_"+aid+" for ACAN workflow.");
		e.printStackTrace();
	}
	return false;
}

public static String getVolFromAcanList(String jid,String aid) //22-03-2012
{
	String vol = ""; 
	Properties prop  = new Properties();
	try{
		prop = loadProp(databasePath +"ACAN_Workflow.dbf");
		if(prop.getProperty(jid, "XXX").equalsIgnoreCase("TRUE")){
			int cutoff = Integer.parseInt(prop.getProperty(jid+"_CUTOFF", "0").trim());
			int itemaid = Integer.parseInt(aid);
			if(itemaid>=cutoff)
			{
				System.out.println(jid+"_"+aid+" is ACAN workflow item, cutoff is "+cutoff);
				vol = prop.getProperty(jid+"_VOLUME", "");
				System.out.println("ACAN VOLUME NUMBER IS :: "+vol);
				return vol;
			}else{
				System.out.println(jid+"_"+aid+" is less than ACAN cutoff number. CUTOFF::"+cutoff+" AID::"+aid);
			}
		}else{
			System.out.println("JID "+jid+" not exist in "+databasePath +"ACAN_Workflow.dbf");
		}
	}catch(Exception e){
		System.out.println("Not able to check "+jid+"_"+aid+" for ACAN workflow.");
		e.printStackTrace();
	}
	return vol;
}

public static void loadFMSModelStyle() throws IOException
	{//FMSModel
//System.out.println("------------------------>");
		RandomAccessFile plusList = new RandomAccessFile(databasePath + "FMSmodel.dbf","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			
				//FMSModel.add(plusLine.trim());
				if(plusLine.indexOf("<-->") != -1)
				{					
				String str[]=plusLine.split("<-->");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				FMSModel.put(str[0].toUpperCase(),str[1]);
				}
			
		}
		plusList.close();
	}
public static void loadPlusModel() throws IOException
	{

		RandomAccessFile plusList = new RandomAccessFile(databasePath + "plusList.dbf","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			if(plusLine.indexOf("<-->") != -1)
			{					
				String str[]=plusLine.split("<-->");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				plusTable.put(str[0].toUpperCase(),str[1]);
			}
		}
				plusList.close();
	}

public static Hashtable GetCopyRightCutOff()
	{
		Vector vec= new Vector();
		Hashtable CR_CutOff=new Hashtable();
		String supplytextfile=databasePath+"CopyrightLanguage.dbf";
		try
		{
			FileInputStream sup = new FileInputStream(supplytextfile);
			BufferedReader buff = new BufferedReader(new InputStreamReader(sup));
			String arr[];
			for(String s = ""; (s = buff.readLine()) != null;)
			{
				arr=s.split("<<>>");
				CR_CutOff.put(arr[0],arr[1]);
			}
		}
		catch(Exception e)
		{
			System.out.println("[Error]-Unable to read from CopyRight Cut Off file\n");
		}
		return CR_CutOff;
	}

public static boolean Get_SD_LOGO_CutOff(String jid,String aid)
	{
		Vector vec= new Vector();
		boolean check=false;

		String supplytextfile=databasePath+"SDLogo.dbf";
		try
		{
			FileInputStream sup = new FileInputStream(supplytextfile);
			BufferedReader buff = new BufferedReader(new InputStreamReader(sup));
			String arr[];
			for(String s = ""; (s = buff.readLine()) != null;)
			{
				arr=s.split("<<>>");
				//System.out.println("==>>"+arr[0]);
				//System.out.println("==>>"+arr[1]);
				if(arr[0].equalsIgnoreCase(jid)&&Integer.parseInt(arr[1])<=Integer.parseInt(aid))
				{
					check=true;
					break;
				}
			}
			buff.close();
		}
		catch(Exception e)
		{
			System.out.println("[Error] Unable to read from SD LOGO file. AID is not in correte Formate. "+e.getMessage().toUpperCase()+"\nPlease Contect to R&D Team. System exit.");
			e.printStackTrace();
			System.exit(0);
			check=false;
		}
		return check;
	}

public static boolean Get_SmallFont_CutOff(String jid,String aid)
	{
		Vector vec= new Vector();
		boolean check=false;

		String supplytextfile=databasePath+"SmallFont.dbf";
		try
		{
			FileInputStream sup = new FileInputStream(supplytextfile);
			BufferedReader buff = new BufferedReader(new InputStreamReader(sup));
			String arr[];
			for(String s = ""; (s = buff.readLine()) != null;)
			{
				arr=s.split("<<>>");
				//System.out.println("==>>"+arr[0]);
				//System.out.println("==>>"+arr[1]);
				String aidduck=aid;
				if(aidduck.indexOf(".")!=-1)
					aidduck=aidduck.substring(0,aidduck.indexOf("."));

				if(arr[0].equalsIgnoreCase(jid)&&Integer.parseInt(arr[1])<=Integer.parseInt(aidduck))
				{
					check=true;
					break;
				}
			}
			buff.close();
		}
		catch(Exception e)
		{
			System.out.println("[Error] Unable to read "+databasePath+"SmallFont.dbf. AID is not in correct format. "+e.getMessage().toUpperCase()+"\nPlease Contact to R&D Team. System exit.");
			e.printStackTrace();
			System.exit(0);
			check=false;
		}
		return check;
	}

public static Hashtable GetNewDTDCutOff()throws IOException
{
	Hashtable CR_CutOff=new Hashtable();
	return CR_CutOff;
}
public static Hashtable GetNewDTDCutOff_Old()throws IOException
	{
		Vector vec= new Vector();
		Hashtable CR_CutOff=new Hashtable();
		

//*********************************
		RandomAccessFile plusList = new RandomAccessFile(databasePath + "NewDTD.dbf","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			if(plusLine.indexOf("<-->") != -1)
			{					
				String str[]=plusLine.split("<-->");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				CR_CutOff.put(str[0].toUpperCase().trim(),str[1]);
			}
		}
				plusList.close();
//**********************************
		
		return CR_CutOff;
	}
	public static Hashtable GetGulliverCutOff()throws IOException
	{
		Vector vec= new Vector();
		Hashtable CR_CutOff=new Hashtable();
		

//*********************************
		RandomAccessFile plusList = new RandomAccessFile(databasePath + "5G.dbf","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			if(plusLine.indexOf("<-->") != -1)
			{					
				String str[]=plusLine.split("<-->");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				CR_CutOff.put(str[0].toUpperCase().trim(),str[1]);
			}
		}
				plusList.close();
//**********************************
		
		return CR_CutOff;
	}
	
/////////////////////////////////////////////////////////////////////Added by adwait 22.11.2014
	public boolean chekJidAid(String jiid,String aiid)
	{
		RandomAccessFile NewJIDList;	
		String JidList = new String();
		String jid=jiid;
		String aid=aiid;
		try
		{
			NewJIDList = new RandomAccessFile(databasePath + "NEW_Model_D.dbf","r");
			String itemaid=aid;
			if(itemaid.indexOf(".",0)!=-1)
			{
				itemaid=itemaid.substring(0,itemaid.indexOf(".",0));
			}

			while((JidList=NewJIDList.readLine())!=null)
			{
				if(JidList.indexOf("=") != -1)
				{					
					String str[]=JidList.split("=");
					if(str[0].equalsIgnoreCase(jid)&&Integer.parseInt(str[1])<=Integer.parseInt((itemaid)))
					{
						flag = true;
					}
					continue;
				}
				
			}
			NewJIDList.close();
		}
		catch (Exception e)
		{
			System.out.println("[Error]-Unable to read from NEW_Model_D.dbf file\n");
			return false;
		}
		return flag;			
	}
	
	public static Properties loadProp(String name) {
		//System.out.println("name"+name);
		FileReader fReader = null;
		Properties prop = new Properties();
		try{
			fReader = new FileReader(name);	
			prop.load(fReader);
			return prop;
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Not able to load :: "+name);
		}finally{
			//System.out.println("Finally executed in loadProp.");
			if(fReader!=null)
				try {
					fReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return null;
	}

/////////////////////////////////////////////////////////////////////
public static Hashtable GetHighLightCutOff()throws IOException
	{
		Vector vec= new Vector();
		Hashtable CR_CutOff=new Hashtable();
		

//*********************************
		RandomAccessFile plusList = new RandomAccessFile(databasePath + "Highlight_CutOff.txt","r");	
		String plusLine = new String();
		String pLine = new String();
		while((plusLine=plusList.readLine())!=null)
		{
			if(plusLine.indexOf("<<>>") != -1)
			{					
				String str[]=plusLine.split("<<>>");
				//System.out.println(str[0]);
				//System.out.println(str[1]);
				CR_CutOff.put(str[0].toUpperCase().trim(),str[1]);
			}
		}
				plusList.close();
//**********************************
		
		return CR_CutOff;
	}
}