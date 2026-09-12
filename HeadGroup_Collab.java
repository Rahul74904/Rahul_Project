package tp.xt;
import java.io.*;
import javax.swing.*;

import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.*;
import java.util.regex.*;
import java.util.StringTokenizer;


class HeadGroup_Collab
{
	XMLObjects xmlObj;	
	RandomAccessFile fin;
	int    figNo   = 1;
	ArrayList auNames      = new ArrayList();

	ArrayList auNamesRhauthor      = new ArrayList();
	StringBuffer authorCorrespondences =  new StringBuffer();
	public static String checkCorres="";
	StringBuffer authorFootnotes=new StringBuffer();
	StringBuffer abstractGraphical=new StringBuffer();//Declare by Avinandan
	StringBuffer abstractGraphicalWithResearchHighlight=new StringBuffer();//Declare by Avinandan

	StringBuffer marge_abstractGraphical=new StringBuffer();//10-06-2011
	StringBuffer marge_abstractGraphicalWithResearchHighlight=new StringBuffer();//10-06-2011
	StringBuffer Move_botton_author_Group_Info  = new StringBuffer();
	StringBuffer absAuthor = new StringBuffer();//Declare by Avinandan
	StringBuffer abstractOtherLang=new StringBuffer();//Declare by Avinandan
	String modelStyle=""; //Declared by Avinandan
	boolean artFootnoteStatus=false;
	boolean initials_check=false;
	String ead                         = "";
	String url                         = "";
	String reference = new String("");
	String presented = new String();
	String misc = new String();
	BiblographyContents bib = null;
	int emailCount=1;
	int UrlCount=1;
	int countAuthor=0;
	static int tableCount=0;//21-01-2012
	static String endData="";//28-1-2005
	static boolean dad_check_doc_head=false;//09/05/2007
	
	static String middleData="";
	static String miscjamm="";
	static boolean isFloats=false;
	static boolean isPalevoAbsFound = false;
	static boolean isAbsFound = false;
	static boolean isDucklingCorrAuthor = false;
	static boolean isceTitleFound = false;
	public static String finalgraphabstractcontent_main="";//28/01/2009
	public static String finalgraphabstractcontent_graphical="";//10-06-2011
	public static String finalgraphabstractcontent_research="";//10-06-2011
	public static String DucklingCorrAuthor="";//22-10-2011
	public static String DucklingTitleFootnote="";//22-10-2011
	//********************[12/09/2007]

	static boolean marker_check_first=false;
	static ArrayList marker_name=new ArrayList();
	static int marker_counter=0;
	//**************************[12/09/2007]
	//boolean chkRefersto=false;  // 21-12-2004
	static String artDochead= "";
	static String subartDochead= "";
	String stereoData ="";
	String altTitle= "";
	String altSubTitle= "";//Added By Ravi
	static String absLangGlobal;
	
	String kwdLang= "";
	static String TempCorAbb="";//Added By Ravi 01/11/2006
	//String tempClass="";
	String TempTitleEntity="";
	String tempCountTitle="";
	static String eextraTitle="";
	static String eextraAuthor="";
	static String Spanish_eextraAuthor="";
	static boolean figureCaptionPara=false;
	static boolean figureCaptionFirstPara=true;
	String Mtitle="";//Added By Ravi [28/12/2006]
	int coutAltTitle=0;
	static String countAbs="";
	static int count_abs=0;
	static int count_keywords=0;
	static int count_JAL_keywords=0;
	public static String Move_Author_tag="";//18/12/2007
	public boolean check_bold_in_title_physt=false;
	static boolean yiccnTitle=false;
	static boolean checkKeyword=false;//20/01/2009
	public static StringBuffer artAbsPIO      = new StringBuffer();//
	public static StringBuffer artKwdFranch_MLA      = new StringBuffer();//23/06/2011
	public static boolean AFJU_Editorial_comment=false;//31-07-2012
	public boolean isAuthorAbs=false;//28-08-2012 JADTD520 Updation
	public boolean isEditorAbs=false;//28-08-2012 JADTD520 Updation
	XT xt=new XT();
	tp.xt.XTLogger xt_log=tp.xt.XTLogger.getInstance();
	HeadGroup_Collab(XMLObjects xmlObj)throws java.io.IOException
	{
		this.xmlObj = xmlObj;
		fin         = xmlObj.fin;		
		//chkRefersto = false;  // 21-12-2004
		bib = new BiblographyContents(xmlObj);	
		
	}

	public String processDisplayFigure(String tage)throws java.io.IOException
	{
		String figId   = "";
		StringBuffer subFig =new StringBuffer(); //declare by Avinandan 8-8-4
		String label   = "";
		String caption = "";
		String figLoc  = "";
		Vector figCol  = new Vector();
		StringBuffer figInformation= new StringBuffer();
		String tag= tage;
		String cpyRtLine = new String();
		String figCopy="";
		String ftype="";
		String fyear="";
		String QryStr="";
		StringBuffer FigCapLang=new StringBuffer();//06/03/2009
		String figKeyword="";//28-08-2012 JADTD520 Updation
		boolean IsfigKeyword=false;//28-08-2012 JADTD520 Updation
		
		if (tag.indexOf("ID=")>0)
		{
			figId= tag.substring(tag.indexOf("ID=\"")+4, tag.indexOf("\">"));
			//System.out.println("figId " +figId);
			//System.in.read();
		}
		
		while (!tag.equals("</CE:FIGURE>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				//System.out.println("tag "+tag);
				if (tag.equals("<CE:LABEL>")||tag.startsWith("<CE:LABEL"))
					label= xmlObj.extractData("</CE:LABEL>", true);
				//else if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))//28-08-2012 JADTD520 Updation
				else if (tag.equals("<CE:CAPTION>"))
				{
					figureCaptionPara=true;
					figureCaptionFirstPara=true;
					caption= xmlObj.extractData("</CE:CAPTION>", true);
					FigCapLang.append("\\caption{\\figno{"+label+QryStr+"}"+caption+"}%");
					figureCaptionPara=false;
					figureCaptionFirstPara=true;
					//System.out.println("FigCapLang " +FigCapLang);
					//System.out.println("caption " +caption);
					//System.out.println("label " +label);
					//System.out.println("QryStr " +QryStr);
					//System.in.read();
					
				}
				else if (tag.startsWith("<CE:CAPTION "))
				{
					figureCaptionPara=true;
					figureCaptionFirstPara=true;
					caption= xmlObj.extractData("</CE:CAPTION>", true);
					figureCaptionPara=false;
					figureCaptionFirstPara=true;
					String lang=xmlObj.getAttributeValue(tag, "XML:LANG");
					//System.out.println("XT.jid "+XT.jid);
					//FigCapLang.append("\\FigSubcaption{\\"+xmlObj.getAttributeValue(tag, "XML:LANG")+"{"+caption+"}}%\r\n");
					if(lang.equals(""))
					{
						FigCapLang.append("\\caption{\\figno{"+label+QryStr+"}"+caption+"}%");
						figureCaptionPara=false;
						figureCaptionFirstPara=true;
					}
					else
					{
						if(XT.jid.equals("PALEVO"))//25/11/2009
						{
							Hashtable palivoHT=new Hashtable();
							palivoHT.put("Figure","Figure");
							palivoHT.put("Fig.","Fig.");
							palivoHT.put("Figs.","Figs.");
							palivoHT.put("Scheme","Sch{\\ASeacute}ma");
							palivoHT.put("Plate","Planche");
							palivoHT.put("Planche","Plate");
							palivoHT.put("Sch{\\ASeacute}ma","Scheme");
							String []a=label.split(" ");
							//System.out.println("label----------> "+label);
							String capPalivo=palivoHT.get(a[0]).toString();
							FigCapLang.append("\r\n\\altfigno{"+capPalivo+" "+a[1]+"}\r\n");
						}
						FigCapLang.append("\r\n\\FigSubcaption{\\"+lang+"{"+caption+"}}%\r\n");
					}
					//System.out.println(caption);
					//System.out.println("caption=== > "+xmlObj.getAttributeValue(tag, "XML:LANG"));
					//System.in.read();
				}
				else if (tag.startsWith("<CE:SOURCE>"))
				{
					FigCapLang.append("\r\n\\Figuresource{"+xmlObj.extractData("</CE:SOURCE>", true)+"}\r\n");
				}
				else if (tag.startsWith("<CE:LINK ")){//04-07-2015
					String linkLocator= xmlObj.getAttributeValue(tag, "LOCATOR");
					figCol.add(linkLocator);
				}
				/*else if (tag.startsWith("<CE:LINK LOCATOR"))
				{
//					figCol.add(tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"")).toUpperCase());//04-07-2015
					int locator = tag.indexOf("<CE:LINK LOCATOR=\"")+18;
					figCol.add(tag.substring(locator, tag.indexOf("\"", locator)).toUpperCase());
				}
				else if (tag.startsWith("<CE:LINK "))
				{
					//figCol.add(tag.substring(tag.indexOf("<CE:LINK ")+18, tag.indexOf("\"/>")).toUpperCase());
					int spos=0;
					int mpos=0;
					int epos=0;
					spos=tag.indexOf("<CE:LINK",mpos);
					if(spos!=-1)
					{
							mpos=tag.indexOf("LOCATOR=\"",epos);	
							if(mpos!=-1)
							{
								//epos=tag.indexOf("\"/>",epos);//04-07-2015
								epos=tag.indexOf("\"",mpos+9);
								if(epos!=-1)
								{
									String temp=tag.substring(mpos+"LOCATOR=\"".length(),epos);
									//System.out.println("--------->"+temp);
									figCol.add(temp);
								}
							}
					}

					//String temp=
					
				}*/
				else if (tag.startsWith("<CE:FIGURE"))
				{
					
					subFig.append(xmlObj.processFigure(tag));
				}
				else if (tag.startsWith("<CE:COPYRIGHT"))
				{
					ftype= xmlObj.getAttributeValue(tag, "TYPE");
					fyear= xmlObj.getAttributeValue(tag, "YEAR");
					XMLObjects.copyrightfirst=true;
					cpyRtLine = xmlObj.extractData("</CE:COPYRIGHT>", true);
					XMLObjects.copyrightfirst=false;
					//System.out.println("1----------------------------");
							
						
				}
				else if (tag.startsWith("<CE:KEYWORDS "))
				{
					figKeyword= processKeywords(tag);
					IsfigKeyword=true;
					//System.out.println("1----------------------------"+figKeyword);
							
						
				}
				else
				{
					if(FigCapLang.indexOf("\\caption{\\figno{",0)==-1)
					FigCapLang.append("\\caption{\\figno{"+label+QryStr+"}"+caption+"}%\r\n");
				}
			}
			/**
			*Added By Ravi
			*Date: [28/05/2007]
			*Change Request: Subrata
			* Change Point : Query tag handled
			*/
			else
			{
				QryStr+=ch;
				//System.out.println("QryStr==> "+QryStr);
				//System.in.read();
			}
			//end
		}
		
		figInformation.append("\\begin{figure}%FIGURE"+(figNo++)+"\r\n\\hypertarget{"+xmlObj.jid+xmlObj.aid+figId.toUpperCase()+"}{}\r\n");
		
		//System.out.println("====> "+figCol);
		//System.out.println("====> "+xmlObj.figInfo);
	for(int i=0;i<figCol.size();i++)
	{
		//System.out.println("2----------------------------");
		figLoc =(String)figCol.get(i);		
		if (xmlObj.figInfo.containsKey(new String(figLoc)))
		{
			String figH, figW;
			figH= figW= "";
			String tfig= new String(new String(xmlObj.figInfo.get(figLoc)+""));
			figW= tfig.substring(0, tfig.indexOf("/"));
			figH= tfig.substring(tfig.indexOf("/")+1);
			//Commented by Avinandan
			//figInformation.append("\\TIFFfigurebox{"+figW+"}{"+figH+"}{}["+figLoc+".tif]%\r\n");
			//Added by Avinandan
			//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n");//20.05.2010
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
			figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+QryStr+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}\r\n");
			//System.out.println("00000000000000000000000000000000000000000");
		}
		else
		{			
			//Commented by Avinandan
			//figInformation.append("\\figurebox{}{}{}["+figLoc+".eps]%\r\n");
			//Added by Avinandan
			//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n");//20.05.2010
//			ThomsonDiff\_ThomsonDiffOpenBrace1ThomsonDiffCloseBrace
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
//protect\\\\(\\backslash\\\\)qtoa\\{Q([0-9]+)\\\\}
			//System.out.println("---------->"+temp_label);
			figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+QryStr+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}\r\n");
			
		}
		//System.out.println("====> "+figInformation);
	}
	//System.out.println("3----------------------------");
		//figInformation.append("\\caption{\\figno{"+label+"}"+caption+"}%\r\n");//old
		   /*Added By Ravi
			*Date: [28/05/2007]
			*Change Request: Subrata
			* Change Point : Query tag handled
			*/
			figInformation.append(FigCapLang.toString()+"\r\n");
			//System.out.println("FigCapLang==> "+FigCapLang);
			//System.in.read();
			//figInformation.append("\\caption{\\figno{"+label+QryStr+"}"+caption+"}%\r\n");//old //06/03/2009
			QryStr="";
			
			//end
		if(ftype.length() > 0 && fyear.length() > 0 && cpyRtLine.length() > 0 )
		{
			//figInformation.append("\\FIGCOPYRIGHT_MISSING\r\n");
			figInformation.append("\r\n\\FigCopyright{\\copyright~"+fyear+"~"+cpyRtLine+"}\r\n");
			ftype="";
			fyear="";
			cpyRtLine="";
		}
		else if(ftype.length() > 0 && fyear.length() > 0)
		{
			//figInformation.append("\\FIGCOPYRIGHT_MISSING\r\n");
			figInformation.append("\r\n\\FigCopyright{\\copyright~"+fyear+"}\r\n");
			ftype="";
			fyear="";
			cpyRtLine="";
		}
		
		
		
		if(subFig.toString().length()>0)
			figInformation.append(subFig.toString()+"\r\n");
		/* //Blocked by Vivek on 29-01-2013 as no keywords required in figure output
		if(IsfigKeyword)//28-08-2012 JADTD520 Updation
			figInformation.append("\r\n\\keywords{%\r\n"+figKeyword+"}\r\n");
		*/
		figInformation.append("\\end{figure}");	
		//System.out.println("figInformation.toString() === > "+figInformation.toString());
		//System.in.read();
		return figInformation.toString();
	}
	//Added by avinandan on 1-9-04
	public String processEComponent(String tage)throws java.io.IOException
	{
		String figId   = "";
		String label   = "";
		StringBuffer figInformation= new StringBuffer();
		String tag= tage;
		if (tag.indexOf("ID=")>0)
		{
			figId= tag.substring(tag.indexOf("ID=\"")+4, tag.indexOf("\">"));
		}
		while (!tag.equals("</CE:E-COMPONENT>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if(tag.equals("<CE:ALT-E-COMPONENT>")||tag.startsWith("<CE:ALT-E-COMPONENT "))//28-08-2012 JADTD520 Updation
				{
					figInformation.append(processEAltComponent(tag, figId, label));
				}
				else if (tag.equals("<CE:LABEL>"))
					label= xmlObj.extractData("</CE:LABEL>", true);
				
			}
				
		}
		return figInformation.toString();
	}
	public String processEAltComponent(String tage, String figId, String lbl)throws java.io.IOException
	{
		StringBuffer subFig =new StringBuffer(); //declare by Avinandan 8-8-4
		String caption = "";
		String label=lbl;
		String figLoc  = "";
		Vector figCol  = new Vector();
		StringBuffer figInformation= new StringBuffer();
		String tag= tage;
		String cpyRtLine = new String();
		while (!tag.equals("</CE:ALT-E-COMPONENT>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>")||tag.startsWith("<CE:LABEL>"))
					label= xmlObj.extractData("</CE:LABEL>", true);
				else if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))
					caption= xmlObj.extractData("</CE:CAPTION>", true);
				else if (tag.startsWith("<CE:LINK LOCATOR"))
				{
					//figCol.add(tag.substring(tag.indexOf("<CE:LINK LOCATOR=\"")+18, tag.indexOf("\"/>")).toUpperCase());//04-07-2015
					String linkLocator= xmlObj.getAttributeValue(tag, "LOCATOR");
					figCol.add(linkLocator);
				}
				else if (tag.startsWith("<CE:COPYRIGHT"))
				{
					cpyRtLine = caption= xmlObj.extractData("</CE:COPYRIGHT>", true);
				}
			}
		}
		figInformation.append("\r\n\\begin{figure}\r\n\\hypertarget{"+xmlObj.jid+xmlObj.aid+figId.toUpperCase()+"}{}\r\n");
		//System.out.println ("Total Tiff : "+xmlObj.figInfo.size ());
		for(int i=0;i<figCol.size();i++)
		{
			figLoc =(String)figCol.get(i);
			if (xmlObj.figInfo.containsKey(new String(figLoc)))
			{
				String figH, figW;
				figH= figW= "";
				String tfig= new String(new String(xmlObj.figInfo.get(figLoc)+""));
				figW= tfig.substring(0, tfig.indexOf("/"));
				figH= tfig.substring(tfig.indexOf("/")+1);
			//Commented by Avinandan
			//figInformation.append("\\TIFFfigurebox{"+figW+"}{"+figH+"}{}["+figLoc+".tif]%\r\n");
			//Added by Avinandan
				//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}r\n");//20.05.2010
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
				figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}\r\n");
			}
			else
			{
			//Commented by Avinandan
			//figInformation.append("\\figurebox{}{}{}["+figLoc+".eps]%\r\n");
			//Added by Avinandan
				//figInformation.append("\\centerline{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n");//20.05.2010
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
				figInformation.append("\\centerline{%\r\n\\AltTextLB{"+temp_label+"}{%\r\n\\epsfbox{"+figLoc.toLowerCase()+".eps}}\r\n}\r\n");
			}
		}
		figInformation.append("\\caption{\\figno{"+label+"}"+caption+"}%\r\n");
		if(cpyRtLine.length() > 0)
		{
			figInformation.append("("+cpyRtLine+")");
		}
		if(subFig.toString().length()>0)
			figInformation.append(subFig.toString()+"\r\n");
		figInformation.append("\\end{figure}");
		return figInformation.toString();
	}
	//end mark

	public String processFloats()throws java.io.IOException
	{
		isFloats = true;
		StringBuffer floatsInfo= new StringBuffer();
		String tag= "";
		while (!tag.equals("</CE:FLOATS>"))
		{
			char ch= (char)fin.read();
			//System.out.println("----->>"+ch);
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				//System.out.println("************************ tag: "+tag);
				if (tag.startsWith("<CE:FIGURE"))
				{
					String fId="";//avinandan added
					String ttmp= processDisplayFigure(tag);
					//System.out.println("ttmp "+ttmp);
					//commented by Avinandan
					//xmlObj.figuresInfo.add(ttmp);
					//added by avinandan
					fId=tag.substring(tag.indexOf("ID=\"")+4, tag.indexOf("\">"));
					//System.out.println("fId "+fId);
					xmlObj.figSchem.put(fId, ttmp);
					//System.out.println("xmlObj.figSchem "+xmlObj.figSchem);
					//end mark
				}
				else if (tag.startsWith("<CE:E-COMPONENT"))
				{
					String fId="";//avinandan added
					String ttmp= processEComponent(tag);
					//commented by Avinandan
					//xmlObj.figuresInfo.add(ttmp);
					//added by avinandan
					if(tag.indexOf("ID=\"")!=-1)
					{
					fId=tag.substring(tag.indexOf("ID=\"")+4, tag.indexOf("\">"));
					xmlObj.figSchem.put(fId, ttmp);
					}
					//end mark
				}
				//Change by bhavesh for multirow on 07/09/06
				else if (tag.startsWith("<CE:TABLE "))
				{
					//System.out.println("************************ tag: "+XT.stage);
					/*
						* This below function is use for all stage 
						* If Any Problem open commented part.
						* Commented by Ravi as per discuss with Vivek Dubey [01/12/2006]
					*/
						//	ProcessCalsMultiTbl tbp= new ProcessCalsMultiTbl(xmlObj);
						//	xmlObj.tablesInfo.add(tbp.processDisplayTable(tag));

						//end
						
					//	if(XT.stage.equalsIgnoreCase("s100"))//old[10/01/2007]
					/*
						* This below function is use for all stage 
						*  if any problem use xtold.bat.
						* Commented by Ravi as per discuss with Vivek Dubey [10/012/2007]
					*/
						if(XT.checkStage.equalsIgnoreCase("xtmain"))
						{
							ProcessCalsMultiTbl tbp= new ProcessCalsMultiTbl(xmlObj);
							tableCount++;
							xmlObj.tablesInfo.add(tbp.processDisplayTable(tag));
						}
						else
						{
							//ProcessCalsMultiTbl tbp= new ProcessCalsMultiTbl(xmlObj);
							ProcessCalsTbl tbp= new ProcessCalsTbl(xmlObj);
							xmlObj.tablesInfo.add(tbp.processDisplayTable(tag));
						}		
					
				}
				else if (tag.startsWith("<CE:TEXTBOX "))//28-08-2012 JADTD520 Updation
				{
					String ttmp= xmlObj.processTextBox(tag);
					xmlObj.textBoxInfo.add(ttmp);
					//xmlObj.isTextBox = false;
				}
				else if (tag.startsWith("<CE:TEXTBOX"))
				{
					
					String ttmp= xmlObj.processTextBox(tag);
					xmlObj.textBoxInfo.add(ttmp);
					
				}
			}
		}
		//System.exit(0);
		isFloats = false;
		return floatsInfo.toString();
	}
public String GetVal(int st)throws java.io.IOException
	{
		String sd="";
		if(st==1)
		{
			sd="";
		}
		else if(st==2)
		{
			sd="one";
		}
		else if(st==3)
		{
			sd="two";
		}
		
return sd;

	}
	public String processAbstract(String etag)throws java.io.IOException
	{
		
		/*
		* An abstract is a short summary of the article. It consists of an optional title, one or more
		* abstract-sections, and an optional figure. It has three attributes, id (required), class and
		* xml:lang. For each combination of class and xml:lang, only one abstract may exist in
		* the document.
		*
		* The language of the abstract, when different from the language of the article, should be
		* specified in the xml:lang attribute. It can have the values English (en), French (fr), Ger-man
        * (de), Portuguese (pt), Russian (ru), and Spanish (es).
        *
		* The type of abstract is specified by the class attribute.
        * 1. author (default) is used for abstracts supplied by the author.
		* 2. editor is used for abstracts supplied by the editor.
        * 3. graphical is used for graphical abstracts. Only these abstracts may contain the optional ce:figure.
		* 4. teaser is used for short "teaser" abstracts that attract the attention of the reader.
		*/
		Hashtable hightLightHash=new Hashtable();//10-06-2011
		hightLightHash=xt.GetHighLightCutOff();//10-06-2011
		String temp_aid=xmlObj.aid;
		String t_aid="";
		boolean check_highlight_graphical=false;
		if(temp_aid.indexOf(".",0)!=-1)
		{
			temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
		}
		if(hightLightHash.containsKey(xmlObj.jid.toString()))
		{
			t_aid=hightLightHash.get(xmlObj.jid).toString();
			if(Integer.parseInt(temp_aid)>=Integer.parseInt(t_aid.trim()))
			{
				check_highlight_graphical=true;
			}
		}
		//System.out.println("xmlObj.jid===>>"+xmlObj.jid);
		//System.out.println("xmlObj.aid===>>"+xmlObj.aid);
		//System.out.println("hightLightHash===>>"+hightLightHash);
			
		StringBuffer abstractInfo= new StringBuffer();
		String tag= etag;

		String absLang = "";
		String absContains = "";
		String absClass    = "";
		boolean checkRHTitle=false;
		boolean checkGATitle=false;
		int ind=0;
		if((ind= tag.indexOf("XML:LANG=\"")) > 0)
		{
			absLang= tag.substring(tag.indexOf("XML:LANG=\"")+10, tag.indexOf("\"", ind+10));
			absLangGlobal = absLang;
			
		}
		else
		{
			absLang = XT.articleType;
			absLangGlobal = absLang;
		}

		//System.out.println(absLang);

		if ((ind= tag.indexOf("CLASS=\""))>0)
		{
			absClass= tag.substring(tag.indexOf("CLASS=\"")+7, tag.indexOf("\"", ind+7));
			if (absClass.length()>0)
			{
				/*
				 Avinandan Commented
				while (!tag.equals("</CE:ABSTRACT>"))
				{
					char ch= (char)fin.read();
					if (ch=='<')
					{
						tag= xmlObj.getTag().toUpperCase();
					}
				}
				return "";
*/
				//Avinandan Added till mark
				//System.out.println("=============>>"+absClass);
				if(absClass.equals("AUTHOR-HIGHLIGHTS")||absClass.equals("EDITOR-HIGHLIGHTS"))//28-08-2012 JADTD520 Updation
				{

					absClass="GRAPHICAL";
				}
				if(absClass.equals("GRAPHICAL"))
				{
					abstractGraphical.append("\r\n\\begin{abstract}\r\n");
					
					if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT")))
					{
						abstractGraphicalWithResearchHighlight.append("\r\n\\begin{abstract}\r\n");
					}
					boolean fstP=false;
					while (!tag.equals("</CE:ABSTRACT>"))
					{
						char ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.startsWith("<CE:ABSTRACT-SEC"))
							{
					// contains a section within the abstract.
								fstP       = true;
							}
							else if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
							{
								
								if(fstP==false)
								{
									abstractGraphical.append("\r\n\r\n");
									if(check_highlight_graphical)
									{
										marge_abstractGraphical.append("\r\n\r\n");//10-06-2011
										//marge_abstractGraphicalWithResearchHighlight.append("\r\n\r\n");//10-06-2011
									}
									
									abstractGraphicalWithResearchHighlight.append("\r\n\r\n");
								}
								String abs=xmlObj.extractData("</CE:SIMPLE-PARA>", true);
								if(check_highlight_graphical)//10-06-2011
								{
									abs=abs.replaceAll("centerline","GAfigure");//10-06-2011
									//System.out.println("abstractGraphical "+abs);
								}
								if(checkRHTitle==true){
									if((xmlObj.jid.equalsIgnoreCase("APCATA")||xmlObj.jid.equalsIgnoreCase("CPLETT")))
										{
											abstractGraphical.append(abs);
											if(check_highlight_graphical)
											{
												marge_abstractGraphical.append(abs);//10-06-2011
											}
											
										}
									else
									{
										if(check_highlight_graphical)
										{
											//String []arr=abs.split("\\(\\bullet\\)");
											int in=0;
											int out=0;
											//System.out.println("11arr==========>>"+abs);
											StringBuffer s=new StringBuffer(abs);
											//System.out.println("sssssssssss"+s);
											boolean b=false;
											if(abs.startsWith("ThomsonDiff"))
											{
												b=true;
											}
											while(in <=abs.length() && in !=-1)
											{
												
												in=s.indexOf("\\(\\bullet\\) ",in);
												if(in!=-1)
												{
													if(b)
													{
														s=s.insert(in,"\r\n\\RHentry{")	;
														in=in+"\r\n\\RHentry{\\(\\bullet\\) ".length();
														b=false;
													}
													else
													{
														s=s.insert(in,"}\r\n\\RHentry{")	;
														in=in+"}\r\n\\RHentry{\\(\\bullet\\) ".length();
													}
												}
												//System.out.println("in:::::::"+in);
											}
											in=0;
											while(in <=abs.length() && in !=-1)
											{
												in=s.indexOf("\\(\\blacktriangleright\\) ",in);
												if(in!=-1)
												{
													if(b)
													{
														s=s.insert(in,"\r\n\\RHentry{")	;
														in=in+"\r\n\\RHentry{\\(\\blacktriangleright\\) ".length();
														b=false;
													}
													else
													{
														s=s.insert(in,"}\r\n\\RHentry{")	;
														in=in+"}\r\n\\RHentry{\\(\\blacktriangleright\\) ".length();
													}
												}
											}
											abs=s.toString();
											
											if(abs.startsWith("}"))
											{
												abs=abs.substring(1,abs.length());
											}
											else
											abs=abs.substring(0,abs.length());
											abs=abs+"}";
											//abs=abs.replaceAll("}}\r\n\\\\RHentry\\{","sdfdfsdf}\r\n\\RHentry{");
											
											//abs=abs.replaceFirst("\\(\\\\bullet\\)","\r\n}\\(\\dddddddddbullet\\){");
											marge_abstractGraphicalWithResearchHighlight.append(abs);
										}
										abstractGraphicalWithResearchHighlight.append(abs);
									}
								}
								else
								{
									abstractGraphical.append(abs);
									if(check_highlight_graphical)//10-06-2011
									{
										marge_abstractGraphical.append(abs);//10-06-2011
									}
									//System.out.println("abstractGraphical "+abstractGraphical);
									//System.in.read();
									if((xmlObj.jid.equalsIgnoreCase("APCATA")||xmlObj.jid.equalsIgnoreCase("CPLETT")))
										{
											abstractGraphical.append("\\Reasearchspace\r\n");
										}
									
								}
								
								
								//abstractGraphical.append(xmlObj.extractData("</CE:SIMPLE-PARA>", true));
								//System.out.println("abstractGraphical : "+abstractGraphical);
								fstP= false;
							}
							else if (tag.startsWith("<CE:SECTION-TITLE"))
							{
								String absSecTitl  = xmlObj.extractData("</CE:SECTION-TITLE>", true);
								String tempabsSecTitl=absSecTitl;
								if(absSecTitl.indexOf("ThomsonDiff",0)!=-1)
								{
									absSecTitl=absSecTitl.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
								}
								//System.out.println("absSecTitl "+absSecTitl);

								/*if(absSecTitl.equalsIgnoreCase("Research highlights")||absSecTitl.equalsIgnoreCase("Research highlight"))
								{
									System.out.println("\n\n******************************************************");
									System.out.println("\n[ERROR]: RESEARCH HIGHLIGHTS TEXT FOUND IN XML.");
									System.out.println("[ERROR]: ONLY HIGHLIGHTS ALLOWED FROM 1st MARCH 2011 ONWARDS IN XML. \n         PLEASE SEND IT TO COPY EDITOR.");
									System.out.println("[ERROR]: SYSTEM IS GOING TO EXIT.");
									System.out.println("\n******************************************************\n\n");
									System.exit(0);

								}
								*/
								if(absSecTitl.equalsIgnoreCase("Research highlights")||absSecTitl.equalsIgnoreCase("Research highlight")||absSecTitl.equalsIgnoreCase("highlights")||absSecTitl.equalsIgnoreCase("highlight")||absSecTitl.equalsIgnoreCase("Points essentiels"))
								{
									checkRHTitle=true;
									if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT"))){
									//abstractGraphicalWithResearchHighlight.append(absSecTitl);
									String temrh=abstractGraphicalWithResearchHighlight.toString();
									temrh=temrh.replaceFirst("\\\\title\\{Graphical Abstract}","\\\\title{"+tempabsSecTitl+"}");
									abstractGraphicalWithResearchHighlight=new StringBuffer(temrh);
									if(check_highlight_graphical)
										{
											//marge_abstractGraphicalWithResearchHighlight.append(temrh);
										}
									}

								}
								if(absSecTitl.equalsIgnoreCase("Graphical abstract"))
								{
									checkGATitle=true;
								}

								//abstractGraphical.append("\r\n\\abstractsection{"+absSecTitl+"}\r\n");
								
							}
							else if (tag.startsWith("<CE:FIGURE")) // Figure coding in graphical is outside ce:simple-para - 27-12-2004
							{
								String figinfo=xmlObj.processFigure(tag);
								
								//abstractGraphical.append(xmlObj.processFigure(tag));
								abstractGraphical.append(figinfo);
								
								if(check_highlight_graphical)//10-06-2011
								{
									figinfo=figinfo.replaceAll("centerline","GAfigure");
									marge_abstractGraphical.append(figinfo);//10-06-2011
								}
								//System.out.println("figinfo--->>"+figinfo);
								//abstractGraphicalWithResearchHighlight.append(figinfo);
								
							}
						}
						else
						{
							
							
							abstractGraphical.append(ch);
							if(check_highlight_graphical)//10-06-2011
							{
								marge_abstractGraphical.append(ch);//10-06-2011
							}
							
						}
					}
					//System.out.println("marge_abstractGraphical===>>"+marge_abstractGraphical);
					//abstractGraphical.append("\r\n\\end{abstract}\r\n\\end{GRAabstract}\r\n\\end{frontmatter}\r\n\\end{document}");
					abstractGraphical.append("\r\n\\end{abstract}\r\n\\end{GRAabstract}\r\n\r\n\\end{document}");
					if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT"))){
					abstractGraphicalWithResearchHighlight.append("\r\n\\end{abstract}\r\n\\end{GRAabstract}\r\n\r\n\\end{document}");
					}
					
					
					/*int start,end;
					start=abstractGraphical.lastIndexOf("\\documentclass[");
					end=abstractGraphical.lastindexOf("auto,onecolumn]");
					end=end-1;
					if(start>=0 && end >0)
						abstractGraphical=abstractGraphical.replace(start,end,"Stage=\"S100-GA\",onecolumn,auto");*/
					
					String finalgraphabstractcontent=abstractGraphical.toString();
					finalgraphabstractcontent=finalgraphabstractcontent.replaceFirst("\\\\authors","");
					String finalgraphabstractcontentRH="";
					if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT")))
					{
						finalgraphabstractcontentRH=abstractGraphicalWithResearchHighlight.toString();
						finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceFirst("\\\\authors","");
					}
					
					String finalgraphabstractcontent_Highlight="";//10-06-2011
					if(check_highlight_graphical)//10-06-2011
					{
						finalgraphabstractcontent_Highlight=marge_abstractGraphical.toString();
						finalgraphabstractcontent_Highlight=finalgraphabstractcontent_Highlight.replaceFirst("\\\\authors","");
						StringBuffer sg1=new StringBuffer(finalgraphabstractcontent_Highlight);
						 int po=0;
							while((po=sg1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po+1))!=-1)
							{
								int pt=sg1.indexOf("\\}",po+1);
								if(pt !=-1)
								{
									sg1=sg1.replace(pt,pt+2,"}");
									//System.out.println("Ravi");
								}
								sg1=sg1.replace(po,po+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
							}
							finalgraphabstractcontent_Highlight=sg1.toString();
							finalgraphabstractcontent_Highlight=finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace","\\\\Diff{");
							finalgraphabstractcontent_Highlight=finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiff_ThomsonDiffOpenBrace","\\\\Diff{");
							finalgraphabstractcontent_Highlight=finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiffCloseBrace","}");
							finalgraphabstractcontent_graphical=finalgraphabstractcontent_Highlight;
					}

					String Research_finalgraphabstractcontent_Highlight="";//10-06-2011
					if(check_highlight_graphical)//10-06-2011
					{
						Research_finalgraphabstractcontent_Highlight=marge_abstractGraphicalWithResearchHighlight.toString();
						//System.out.println("Research_finalgraphabstractcontent_Highlight "+Research_finalgraphabstractcontent_Highlight);
						Research_finalgraphabstractcontent_Highlight=Research_finalgraphabstractcontent_Highlight.replaceFirst("\\\\authors","");
						Research_finalgraphabstractcontent_Highlight=Research_finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiff_ThomsonDiffOpenBrace","\\\\Diff{");
						Research_finalgraphabstractcontent_Highlight=Research_finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace","\\\\Diff{");
						Research_finalgraphabstractcontent_Highlight=Research_finalgraphabstractcontent_Highlight.replaceAll("ThomsonDiffCloseBrace","}");
						StringBuffer sg1=new StringBuffer(Research_finalgraphabstractcontent_Highlight);
						 int po=0;
							while((po=sg1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po+1))!=-1)
							{
								int pt=sg1.indexOf("\\}",po+1);
								if(pt !=-1)
								{
									sg1=sg1.replace(pt,pt+2,"}");
									//System.out.println("Ravi");
								}
								sg1=sg1.replace(po,po+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
							}
							Research_finalgraphabstractcontent_Highlight=sg1.toString();
							
							finalgraphabstractcontent_research=Research_finalgraphabstractcontent_Highlight;
					}
					
					//absFin.writeBytes(abstractGraphical.toString());

				//************************************************
				//[Added By Ravi 03/05/2007]
				//Change Request By Subrata. 
				//Change Point: deleting query tag in xml file
				 StringBuffer sg=new StringBuffer(finalgraphabstractcontent);
				 int po=0;
				 //\(\backslash\)qtoq\{
					while((po=sg.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po+1))!=-1)
					{
						int pt=sg.indexOf("\\}",po+1);
						if(pt !=-1)
						{
							sg=sg.replace(pt,pt+2,"}");
							//System.out.println("Ravi");
						}
						sg=sg.replace(po,po+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
					}
					finalgraphabstractcontent=sg.toString();

					 StringBuffer sgRH=new StringBuffer(finalgraphabstractcontentRH);
				 int po1=0;
				 //\(\backslash\)qtoq\{
					while((po1=sgRH.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",po1+1))!=-1)
					{
						int pt1=sgRH.indexOf("\\}",po1+1);
						if(pt1 !=-1)
						{
							sgRH=sgRH.replace(pt1,pt1+2,"}");
							//System.out.println("Ravi");
						}
						sgRH=sgRH.replace(po1,po1+"\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{".length(),"\\protect\\qtoa{");
					}
					finalgraphabstractcontentRH=sgRH.toString();
					//System.out.println("finalgraphabstractcontent         ::::::"+finalgraphabstractcontent);
				//**********************
		//************************************************
		/**
		* Handled Diff tag in graphical Abstract
		*Date :20/05/2008
		* Added By : Ravi 
		*Request By :TPMS {Vivek}
		*/
			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("ThomsonDiffOpenBrace","{");
			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("ThomsonDiffCloseBrace","}");
			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("ThomsonDiff\\\\_","\\\\Diff");
			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("ThomsonDiff\\_","\\\\Diff");

			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("thomsondiff\\\\_thomsondiffopenbrace","\\\\Diff{");
			finalgraphabstractcontent=finalgraphabstractcontent.replaceAll("thomsondiffclosebrace","}");


			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("ThomsonDiffOpenBrace","{");
			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("ThomsonDiffCloseBrace","}");
			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("ThomsonDiff\\\\_","\\\\Diff");
			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("ThomsonDiff\\_","\\\\Diff");

			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("thomsondiff\\\\_thomsondiffopenbrace","\\\\Diff{");
			finalgraphabstractcontentRH=finalgraphabstractcontentRH.replaceAll("thomsondiffclosebrace","}");
		//****************************************************
		
		if(XT.S250ABP.size()>0)
		{
			//System.out.println("finalgraphabstractcontentRH :: "+finalgraphabstractcontentRH);
			String coverdate=XT.S250ABP.get("cover_year").toString();
			if(coverdate.length()>0 && coverdate !=null)
			{
				StringBuffer sb=new StringBuffer(finalgraphabstractcontentRH);
				 int s=sb.indexOf("\\aid",0);
				if(s!=-1)
				{
					sb.insert(s,"\\coverdate{"+coverdate+"}\r\n");
					finalgraphabstractcontentRH=sb.toString();
				}

				sb=new StringBuffer(finalgraphabstractcontent);
				 s=sb.indexOf("\\aid",0);
				if(s!=-1)
				{
					sb.insert(s,"\\coverdate{"+coverdate+"}\r\n");
					finalgraphabstractcontent=sb.toString();
				}
			}
		}

if(finalgraphabstractcontent.indexOf("RTOPEN************",0)!=-1)
		{
				int pos_query1=0;
			 int epos_query=0;
			StringBuffer tt=new StringBuffer(finalgraphabstractcontent);
			//System.out.println(temp);
			int count=1;
			while(tt.indexOf("RTOPEN************",pos_query1)!=-1)
			{
			 
			 pos_query1=tt.indexOf("RTOPEN************",pos_query1);
			 epos_query=tt.indexOf("************RTCLOSE",pos_query1);
			 //if(count==2)
			 XT.cmmment_rhtitle=tt.substring(pos_query1+"RTOPEN************".length(),epos_query);
			 count++;
			 tt=tt.delete(pos_query1,epos_query+"************RTCLOSE".length());
			 
			}
			 tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
			 finalgraphabstractcontent=tt.toString();
		}
if(finalgraphabstractcontent.length()>0)
{
	String temp_aid1=xmlObj.aid;//check for duckling item.

		if(temp_aid1.indexOf(".",0)!=-1)
		{
			temp_aid1=temp_aid1.substring(0,temp_aid1.indexOf(".",0));
		}
		if(XT.Get_SD_LOGO_CutOff(xmlObj.jid,temp_aid1))
		{
			StringBuffer sb=new StringBuffer(finalgraphabstractcontent);
			sb.insert(sb.indexOf("\\documentclass[") + "\\documentclass[".length(), "SDLogo,");
			finalgraphabstractcontent=sb.toString();
		}
}

if(finalgraphabstractcontentRH.length()>0)
{
	String temp_aid1=xmlObj.aid;//check for duckling item.

		if(temp_aid1.indexOf(".",0)!=-1)
		{
			temp_aid1=temp_aid1.substring(0,temp_aid1.indexOf(".",0));
		}
		if(XT.Get_SD_LOGO_CutOff(xmlObj.jid,temp_aid1))
		{
			StringBuffer sb=new StringBuffer(finalgraphabstractcontentRH);
			sb.insert(sb.indexOf("\\documentclass[") + "\\documentclass[".length(), "SDLogo,");
			finalgraphabstractcontentRH=sb.toString();
		}
}
	//System.out.println("============>>"+XT.Get_SD_LOGO_CutOff(xmlObj.jid,temp_aid1));
//System.out.println("==========>>"+XT.S250ABP);
//XT.articleType
/*XT.loadFMSModelStyle();
	System.out.println(modelStyle+"------------------>FMSModel ");
	if(XT.FMSModel.contains(XT.modelStyle))
	{		//System.out.println("------------------>bibStyle "+modelStyle);
		finalgraphabstractcontent=finalgraphabstractcontent.replaceFirst(modelStyle,modelStyle+"FMS");
	}
	*/

if(finalgraphabstractcontentRH.indexOf("RTOPEN************",0)!=-1)
		{
				int pos_query1=0;
			 int epos_query=0;
			StringBuffer tt=new StringBuffer(finalgraphabstractcontentRH);
			//System.out.println(temp);
			int count=1;
			while(tt.indexOf("RTOPEN************",pos_query1)!=-1)
			{
			 
			 pos_query1=tt.indexOf("RTOPEN************",pos_query1);
			 epos_query=tt.indexOf("************RTCLOSE",pos_query1);
			 if(count==2)
			 XT.cmmment_rhtitle=tt.substring(pos_query1+"RTOPEN************".length(),epos_query);
			 count++;
			 tt=tt.delete(pos_query1,epos_query+"************RTCLOSE".length());
			 
			}
			 tt=tt.insert(tt.indexOf("\\aid{"),"\n\n\\rhtitle{"+XT.cmmment_rhtitle+"}\r\n\n\n");
			 finalgraphabstractcontentRH=tt.toString();
		}

					if(check_highlight_graphical==false)//10-06-2011
					{
						if(checkGATitle==true){
							RandomAccessFile absFin= new RandomAccessFile(xmlObj.aid+"g.tex", "rw");
							absFin.writeBytes(finalgraphabstractcontent);
							absFin.close();
							checkGATitle=false;
						}
						if(checkRHTitle==true)
						{
							if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT"))){
								System.out.println ("Creating ResearchHighlights TeX file.");
								RandomAccessFile absFinRH= new RandomAccessFile(xmlObj.aid+"r.tex", "rw");
								absFinRH.writeBytes(finalgraphabstractcontentRH);
								absFinRH.close();
							}
							checkRHTitle=false;
							
						}
						

						finalgraphabstractcontent_main=finalgraphabstractcontent;//28/01/2008
						return "";
					}
					else//10-06-2011
					return "";
				}
				else
				{
					if(absClass.equals("EDITOR"))
					{
						abstractInfo.append("\r\n\n");
					}
				}
			}
		}

		//System.out.println("ravi-------------->"+xmlObj.jid+"--"+xmlObj.getAbstractLanguage(absLang));
		//if(xmlObj.jid.equalsIgnoreCase("NEUCLI") &&(absLang.equals("EN") && XT.articleType.equals("FR")) || (absLang.equals("FR") && XT.articleType.equals("EN")))
		//Added by Ravi [06/11/2006]//JGYN 
//		if((xmlObj.jid.equalsIgnoreCase("NEUCLI")||xmlObj.jid.equalsIgnoreCase("ENCEP")||xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("REAURG")||xmlObj.jid.equalsIgnoreCase("JGYN")) &&((absLang.equals("EN") && XT.articleType.equals("FR")) || (absLang.equals("FR") && XT.articleType.equals("EN"))))

		//if((xmlObj.jid.equalsIgnoreCase("NEUCLI")||xmlObj.jid.equalsIgnoreCase("ENCEP")||xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("REAURG")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("MSOM")||xmlObj.jid.equalsIgnoreCase("GCB")||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD"))||xmlObj.jid.equalsIgnoreCase("MSOM")) ||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true)))
		if((xmlObj.jid.equalsIgnoreCase("NEUCLI")||(xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)<796)||xmlObj.jid.equalsIgnoreCase("OTSR")||xmlObj.jid.equalsIgnoreCase("JOCLIM")||xmlObj.jid.equalsIgnoreCase("SODA")||xmlObj.jid.equalsIgnoreCase("LIVER")||xmlObj.jid.equalsIgnoreCase("DEMAN")||xmlObj.jid.equalsIgnoreCase("STLM")||xmlObj.jid.equalsIgnoreCase("ANNDER")||xmlObj.jid.equalsIgnoreCase("RCOT")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("JVS")||xmlObj.jid.equalsIgnoreCase("JCHIRV")||xmlObj.jid.equalsIgnoreCase("JCHIR")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("FEMME")||xmlObj.jid.equalsIgnoreCase("CND")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("EURTEL")||xmlObj.jid.equalsIgnoreCase("REVHOM")||xmlObj.jid.equalsIgnoreCase("JCCO")||xmlObj.jid.equalsIgnoreCase("JEUREA")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("JDMV")||xmlObj.jid.equalsIgnoreCase("BANM")||xmlObj.jid.equalsIgnoreCase("REAURG")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("MLONG")||xmlObj.jid.equalsIgnoreCase("LONGEV")||xmlObj.jid.equalsIgnoreCase("RMR")||xmlObj.jid.equalsIgnoreCase("PEDPUE")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("FANDER")||xmlObj.jid.equalsIgnoreCase("ACVDSP")||xmlObj.jid.equalsIgnoreCase("JEMEP")||xmlObj.jid.equalsIgnoreCase("TOXAC")||xmlObj.jid.equalsIgnoreCase("ONCOHP")||xmlObj.jid.equalsIgnoreCase("JRADIO")||xmlObj.jid.equalsIgnoreCase("JRDIA")||xmlObj.jid.equalsIgnoreCase("DIII")||(xmlObj.jid.equalsIgnoreCase("TRACLI")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("MSOM")||xmlObj.jid.equalsIgnoreCase("GCB")||(xmlObj.jid.equalsIgnoreCase("CLINRE"))||(xmlObj.jid.equalsIgnoreCase("CLIREX"))||(xmlObj.jid.equalsIgnoreCase("NEUADO")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("VETCLI")||xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("AFJU")||xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX")||xmlObj.jid.equalsIgnoreCase("AFORL")||xmlObj.jid.equalsIgnoreCase("ANORL")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("JBCT")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("THERAP")||xmlObj.jid.equalsIgnoreCase("SEXOL")) ||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid))))
		{
			//System.out.println("if XT.fmcforall :"+XT.fmcforall);
			//System.out.println("==============absLang :"+absLang+" ---------"+xmlObj.getAbstractLanguage(absLang));
			String st12=GetVal(count_abs);
			//System.out.println("XT.plusTable :"+XT.plusTable.get(xmlObj.jid).toString());
			abstractInfo.append("\\begin{abstract"+st12+"}[LANGUAGE=\""+xmlObj.getAbstractLanguage(absLang)+"\"]");
			
		}
		// 
		//else if((xmlObj.jid.equalsIgnoreCase("QUIP"))&& (xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN")))
		else if((xmlObj.jid.equalsIgnoreCase("QUIP")))
		{
			String st12=GetVal(count_abs);
			//System.out.println("==============absLang :"+absLang+" ---------"+xmlObj.getAbstractLanguage(absLang));
			abstractInfo.append("\\begin{abstract"+st12+"}[LANGUAGE=\""+xmlObj.getAbstractLanguage(absLang)+"\"]");
		}
		else if((xmlObj.jid.equalsIgnoreCase("SCOMS")))
		{
			String st12=GetVal(count_abs);
			//System.out.println("==============absLang :"+absLang+" ---------"+xmlObj.getAbstractLanguage(absLang));
			abstractInfo.append("\r\n\\begin{abstract"+st12+"}[LANGUAGE=\""+xmlObj.getAbstractLanguage(absLang)+"\"]\r\n");
		}
		else
		{
			//System.out.println("else XT.fmcforall :"+XT.fmcforall);
			abstractInfo.append("\\begin{abstract}[LANGUAGE=\""+xmlObj.getAbstractLanguage(absLang)+"\"]");
		
		}

		boolean sectionFound= false;
		boolean sectionTitlFound= false;
		boolean firstP= true;
		boolean abs_title=false;
		while (!tag.equals("</CE:ABSTRACT>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.startsWith("<CE:ABSTRACT-SEC"))
				{
					// contains a section within the abstract.
					sectionFound = true;
					firstP       = true;
					sectionTitlFound= false;
				}
				if (tag.equals("</CE:ABSTRACT-SEC>"))
				{
					sectionFound = false;
					//System.out.println("22222222222222222");
				}
				else if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
				{
					if(firstP==false)
						abstractInfo.append("\r\n\r\n");
					abstractInfo.append(xmlObj.extractData("</CE:SIMPLE-PARA>", true));
					firstP= false;
				}
				else if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					String absSecTitl  = xmlObj.extractData("</CE:SECTION-TITLE>", true);
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH") && (xmlObj.jid.equalsIgnoreCase("NEUCLI")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)<796) || xmlObj.jid.equalsIgnoreCase("REAURG")|| xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("OTSR")||xmlObj.jid.equalsIgnoreCase("JOCLIM")||xmlObj.jid.equalsIgnoreCase("SODA")||xmlObj.jid.equalsIgnoreCase("LIVER")||xmlObj.jid.equalsIgnoreCase("DEMAN")||xmlObj.jid.equalsIgnoreCase("STLM")||xmlObj.jid.equalsIgnoreCase("ANNDER")||xmlObj.jid.equalsIgnoreCase("RCOT")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("JVS")||xmlObj.jid.equalsIgnoreCase("JCHIRV")||xmlObj.jid.equalsIgnoreCase("JCHIR")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("FEMME")||xmlObj.jid.equalsIgnoreCase("CND")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("EURTEL")||xmlObj.jid.equalsIgnoreCase("REVHOM")||xmlObj.jid.equalsIgnoreCase("JCCO")||xmlObj.jid.equalsIgnoreCase("JEUREA")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("JDMV")||xmlObj.jid.equalsIgnoreCase("BANM")||xmlObj.jid.equalsIgnoreCase("GCB")||(xmlObj.jid.equalsIgnoreCase("CLINRE"))||(xmlObj.jid.equalsIgnoreCase("CLIREX"))||(xmlObj.jid.equalsIgnoreCase("NEUADO")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("VETCLI")||xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX")|| xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("AFJU")||(xmlObj.jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))||xmlObj.jid.equalsIgnoreCase("AFORL")||xmlObj.jid.equalsIgnoreCase("ANORL")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("JBCT")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("MLONG")||xmlObj.jid.equalsIgnoreCase("LONGEV")||xmlObj.jid.equalsIgnoreCase("RMR")||xmlObj.jid.equalsIgnoreCase("PEDPUE")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("FANDER")||xmlObj.jid.equalsIgnoreCase("ACVDSP")||xmlObj.jid.equalsIgnoreCase("THERAP")||xmlObj.jid.equalsIgnoreCase("JEMEP")||xmlObj.jid.equalsIgnoreCase("TOXAC")||xmlObj.jid.equalsIgnoreCase("ONCOHP")||xmlObj.jid.equalsIgnoreCase("JRADIO")||xmlObj.jid.equalsIgnoreCase("JRDIA")||xmlObj.jid.equalsIgnoreCase("DIII")||(xmlObj.jid.equalsIgnoreCase("TRACLI")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("MSOM")||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid)))))
					{
						abstractInfo.append("\r\n\\fabstractsection{"+absSecTitl.trim()+"}\r\n");
					}
					else if((xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN")))
					{
						if(xmlObj.jid.equalsIgnoreCase("QUIP"))
						{
							if(!sectionFound)
							{
								abstractInfo.append("\r\n\\abstracttitle{"+absSecTitl+"}\r\n");
							}
							else
							{
								abstractInfo.append("\r\n\\itabstractsection{"+absSecTitl.trim()+"}\r\n");
							}
						}
						else
						{
							abstractInfo.append("\r\n\\itabstractsection{"+absSecTitl.trim()+"}\r\n");
						}
					}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH"))
					{
						abstractInfo.append("\r\n\\fabstractsection{"+absSecTitl.trim()+"}\r\n");
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("JECS"))
							abstractInfo.append("\r\n\\abstractsection{"+absSecTitl+"}\r\n\\noindent ");
						else
						{
							if(sectionFound)
							{
								abstractInfo.append("\r\n\\abstractsection{"+absSecTitl+"}\r\n");

								//sectionFound=false;
							}else
							{
								if((xmlObj.jid.equalsIgnoreCase("FUSPRU"))||(xmlObj.jid.equalsIgnoreCase("QUIP")))
								abstractInfo.append("\r\n\\abstracttitle{"+absSecTitl+"}\r\n");
								else
									abstractInfo.append("\r\n\\abstractsection{"+absSecTitl+"}\r\n");
								//System.out.println("----------------------------------");
							}
						}
					}
					if((xmlObj.jid.equalsIgnoreCase("PALEVO"))&& altTitle.length() > 1 && ((XT.articleType.equals("FR") && absLang.equals("EN")) || (XT.articleType.equals("EN") && absLang.equals("FR"))) )
					{
						//System.out.println("1 altTitle -- > "+altTitle);
						if(XT.isTableGulliverCutOff==false){//06/11/2009
						altTitle = altTitle.replaceFirst("\r\n","");
						altTitle = altTitle.replaceFirst("Alttitle","FAlttitle");
						isPalevoAbsFound = true;
						}

						//abhay 15/09/2006
						if((XT.articleType.equalsIgnoreCase("FR") && absLangGlobal.equalsIgnoreCase("EN")) || (XT.articleType.equalsIgnoreCase("EN") && absLangGlobal.equalsIgnoreCase("FR")))
						{
							//abstractInfo.append(altTitle +"\r\n");//old
							/**
							*Added By Ravi [23/02/2007]
							*Change Request By : Vivek
							*Change Point: Closing brace missing in \FAlttitle PALEVO
							*/
							if(XT.isTableGulliverCutOff==false){//06/11/2009
							abstractInfo.append(altTitle +"}\r\n");
							//System.out.println("1 abstractInfo -- > "+abstractInfo);
							}
							//System.out.println("abstractInfo -- > "+abstractInfo);
							//System.in.read();
						}
						else
							abstractInfo.append(altTitle +"}\r\n");

					}
					//*********[20/11/2007]*****************
					else if(((xmlObj.jid.equalsIgnoreCase("DIABET"))||(xmlObj.jid.equalsIgnoreCase("BONSOI"))||(xmlObj.jid.equalsIgnoreCase("BIONUT"))||(xmlObj.jid.equalsIgnoreCase("BIOMAG"))||(xmlObj.jid.equalsIgnoreCase("CULHER")))&& altTitle.length() > 1 && (absLang.equals("FR"))) 
					{
						altTitle = altTitle.replaceFirst("\r\n","");
						altTitle = altTitle.replaceFirst("Alttitle","FAlttitle");
						abstractInfo.append(altTitle +"}\r\n");
						altTitle="";
					}
					else if(((xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG"))||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT"))&& altTitle.length() > 1 && count_abs >1) 
					{
						if(abstractInfo.indexOf("\\abstractsection{",0)!=-1)
						{
							int r=abstractInfo.indexOf("\\abstractsection{",0);
							abstractInfo=abstractInfo.insert(r,altTitle +"}\r\n");
							altTitle="";
						}
						//System.out.println("abstractInfo"+abstractInfo);
						/*altTitle = altTitle.replaceFirst("\r\n","");
						altTitle = altTitle.replaceFirst("Alttitle","Alttitle");
						abstractInfo.append(altTitle +"}\r\n");
						altTitle="";
						*/
						
					}
					else if(((xmlObj.jid.equalsIgnoreCase("MLA")))&& altTitle.length() > 1 && count_abs >1) 
					{
						if(abstractInfo.indexOf("\\abstractsection{",0)!=-1)
						{
							int r=abstractInfo.indexOf("\\abstractsection{",0);
							int s=abstractInfo.indexOf("}",r);
							abstractInfo=abstractInfo.insert(s+2,altTitle +"}");
							altTitle="";
						}
						//System.out.println("abstractInfo"+abstractInfo);
						/*altTitle = altTitle.replaceFirst("\r\n","");
						altTitle = altTitle.replaceFirst("Alttitle","Alttitle");
						abstractInfo.append(altTitle +"}\r\n");
						altTitle="";
						*/
						
					}
					//**************************************
				}
			}
		}
		//01-04-2010


		if(XT.isTableGulliverCutOff==false){//06/11/2009
		if((xmlObj.jid.equalsIgnoreCase("PALEVO")) && absLang.equals("FR"))
			abstractInfo.append(" \\Frabsline");
		else if((xmlObj.jid.equalsIgnoreCase("PALEVO")) && absLang.equals("EN"))
			abstractInfo.append(" \\Engabsline");
		}
//System.out.println("XT.modelStyle-----------"+XT.fmcforall);
		//if((xmlObj.jid.equalsIgnoreCase("PALEVO") ||xmlObj.jid.equalsIgnoreCase("REVMED")||xmlObj.jid.equalsIgnoreCase("SCISPO")||xmlObj.jid.equalsIgnoreCase("REVRHU")||xmlObj.jid.equalsIgnoreCase("DIABET")||xmlObj.jid.equalsIgnoreCase("ERAP")||xmlObj.jid.equalsIgnoreCase("ANDO")||xmlObj.jid.equalsIgnoreCase("MEDMAL")||xmlObj.jid.equalsIgnoreCase("NEUCHI")||xmlObj.jid.equalsIgnoreCase("NEUADO")||xmlObj.jid.equalsIgnoreCase("MEDDRO")||xmlObj.jid.equalsIgnoreCase("CANRAD")||xmlObj.jid.equalsIgnoreCase("RBMRET")||xmlObj.jid.equalsIgnoreCase("SCIPSO")||xmlObj.jid.equalsIgnoreCase("NUTCLI")||xmlObj.jid.equalsIgnoreCase("ANCAAN")||xmlObj.jid.equalsIgnoreCase("JTS") || xmlObj.jid.equalsIgnoreCase("ANNPAL")|| xmlObj.jid.equalsIgnoreCase("ALTER")|| xmlObj.jid.equalsIgnoreCase("PSFR")|| xmlObj.jid.equalsIgnoreCase("PRPS")|| xmlObj.jid.equalsIgnoreCase("SOCTRA") || xmlObj.jid.equalsIgnoreCase("EVOPSY") || xmlObj.jid.equalsIgnoreCase("REVMIC")) && absLang.equals("FR") && XT.articleType.equals("FR"))
		if((xmlObj.jid.equalsIgnoreCase("PALEVO") ||xmlObj.jid.equalsIgnoreCase("REVMED")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)>795) ||xmlObj.jid.equalsIgnoreCase("SCOMS")||xmlObj.jid.equalsIgnoreCase("MONRHU")||xmlObj.jid.equalsIgnoreCase("PATBIO")||xmlObj.jid.equalsIgnoreCase("BIOPHA")||xmlObj.jid.equalsIgnoreCase("REVRHU")||xmlObj.jid.equalsIgnoreCase("DIABET")||xmlObj.jid.equalsIgnoreCase("ERAP")||xmlObj.jid.equalsIgnoreCase("HYA")||xmlObj.jid.equalsIgnoreCase("STMAT")||xmlObj.jid.equalsIgnoreCase("CYA")||xmlObj.jid.equalsIgnoreCase("DF")||xmlObj.jid.equalsIgnoreCase("RESU")||xmlObj.jid.equalsIgnoreCase("ANTRO")||xmlObj.jid.equalsIgnoreCase("RMB")||xmlObj.jid.equalsIgnoreCase("REGE")||xmlObj.jid.equalsIgnoreCase("RAUSP")||xmlObj.jid.equalsIgnoreCase("RAUSPM")||xmlObj.jid.equalsIgnoreCase("AIPPRR")||xmlObj.jid.equalsIgnoreCase("BIOET")||xmlObj.jid.equalsIgnoreCase("JART")||xmlObj.jid.equalsIgnoreCase("JBHSI")||xmlObj.jid.equalsIgnoreCase("RAI")||xmlObj.jid.equalsIgnoreCase("ANDO")||xmlObj.jid.equalsIgnoreCase("REVAL")||xmlObj.jid.equalsIgnoreCase("MEDMAL")||xmlObj.jid.equalsIgnoreCase("NEUCHI")||xmlObj.jid.equalsIgnoreCase("MEDDRO")||xmlObj.jid.equalsIgnoreCase("CANRAD")||xmlObj.jid.equalsIgnoreCase("OFTAL")||XT.modelStyle.startsWith("7Spanish")||xmlObj.jid.equalsIgnoreCase("RBMRET")||xmlObj.jid.equalsIgnoreCase("IRBM")||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())>Integer.parseInt(xmlObj.aid)))||xmlObj.jid.equalsIgnoreCase("NUTCLI")||xmlObj.jid.equalsIgnoreCase("ANCAAN")||xmlObj.jid.equalsIgnoreCase("CHIMAI")|| xmlObj.jid.equalsIgnoreCase("ANNPAL")|| xmlObj.jid.equalsIgnoreCase("ALTER")|| xmlObj.jid.equalsIgnoreCase("EXMATH")|| xmlObj.jid.equalsIgnoreCase("DDES")|| xmlObj.jid.equalsIgnoreCase("SMED")|| xmlObj.jid.equalsIgnoreCase("PSFR")|| xmlObj.jid.equalsIgnoreCase("EHN")|| xmlObj.jid.equalsIgnoreCase("EHMCM")|| xmlObj.jid.equalsIgnoreCase("PRPS")|| xmlObj.jid.equalsIgnoreCase("SOCTRA") || xmlObj.jid.equalsIgnoreCase("EVOPSY") || xmlObj.jid.equalsIgnoreCase("REVMIC") || xmlObj.jid.equalsIgnoreCase("ACCPM") || xmlObj.jid.equalsIgnoreCase("DIABET") || xmlObj.jid.equalsIgnoreCase("EJTD") || xmlObj.jid.equalsIgnoreCase("EURGER") || xmlObj.jid.equalsIgnoreCase("EURPSY") || xmlObj.jid.equalsIgnoreCase("GEOBIO") || xmlObj.jid.equalsIgnoreCase("GOFS") || xmlObj.jid.equalsIgnoreCase("HANSUR") || xmlObj.jid.equalsIgnoreCase("INAN") || xmlObj.jid.equalsIgnoreCase("JOGOH") || xmlObj.jid.equalsIgnoreCase("JORMAS") || xmlObj.jid.equalsIgnoreCase("MEDNUC") || xmlObj.jid.equalsIgnoreCase("MYCMED") || xmlObj.jid.equalsIgnoreCase("RETRAM") || xmlObj.jid.equalsIgnoreCase("ARCPED") || xmlObj.jid.equalsIgnoreCase("BONSOI")||(xmlObj.jid.equalsIgnoreCase("BIONUT"))||(xmlObj.jid.equalsIgnoreCase("BIOMAG"))|| xmlObj.jid.equalsIgnoreCase("CULHER")|| xmlObj.jid.equalsIgnoreCase("MLA")))
		{
			String tempAid;//added by mukesh on 01-11-08 TPMS
			//System.out.println("1------fffffffffff-----"+GetVal(count_abs));
			if(XT.IsCutOffCR.containsKey(xmlObj.jid.toString()))//mukesh on 01-11-08 TPMS
			{
							
				String st12=GetVal(count_abs);
				
				tempAid=XT.IsCutOffCR.get(xmlObj.jid.toString()).toString();
				if(Integer.parseInt(xmlObj.aid) >=(Integer.parseInt(tempAid)))
				{
					//System.out.println("2-----------"+st12);
					//System.out.println("xmlObj.getAbstractLanguage(absLang)-----------"+xmlObj.getAbstractLanguage(absLang));
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH"))
					{
						abstractInfo.append("\r\n\\FRcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
					{
						abstractInfo.append("\r\n\\ITcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("SPANISH"))
					{
						abstractInfo.append("\r\n\\SPcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("PORTUGUESE"))
					{
						abstractInfo.append("\r\n\\PORTcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else{abstractInfo.append("\r\n\\ENGcopyright\r\n\\end{abstract}");}
				}
				else{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
					{
						abstractInfo.append("\r\n\\ITcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else
					{
						abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");//abhay 12/09/2006
					}
					//System.out.println("Inserted   -2222--");
				}
			}
			else
				{			
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
					{
						abstractInfo.append("\r\n\\ITcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("SPANISH"))
					{
						abstractInfo.append("\r\n\\SPcopyright\r\n\\end{abstract}");
						//System.out.println("-----------"+st12);
					}
					else{
						abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");//abhay 12/09/2006
					}
			//System.out.println("Inserted   ---");
			}
		//System.out.println("1 abstractInfo -- > "+abstractInfo);
		//System.in.read();
		}
		else if(((xmlObj.jid.equalsIgnoreCase("NEUADO") && XT.fmcforall==false) ||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==false))||(( xmlObj.jid.equalsIgnoreCase("TRACLI"))&&(XT.fmcforall==false))))
		{
			//abstractInfo.append("\r\n\\Frenchcopyright\r\n%\\end{abstract}");
			//System.out.println("22-----------Ravi ");
			if(XT.IsCutOffCR.containsKey(xmlObj.jid.toString()))//mukesh on 01-11-08 TPMS
			{
				String st12=GetVal(count_abs);
				//System.out.println("22-----------"+st12);
				String tempAid="";
				tempAid=XT.IsCutOffCR.get(xmlObj.jid.toString()).toString();
				if(Integer.parseInt(xmlObj.aid) >=(Integer.parseInt(tempAid)))
				{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH"))
					{abstractInfo.append("\r\n\\FRcopyright\r\n\\end{abstract}");}
					else{abstractInfo.append("\r\n\\ENGcopyright\r\n\\end{abstract}");}
				}else{
				abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");
				//System.out.println("Inserted   --222222222222222222222222-");
				}
			}
			else{
			abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");
			//System.out.println("Inserted   --222222222222222222222222-");
			}
		//System.out.println("sssssssssabstractInfo -- > "+abstractInfo);
		//System.in.read();	
		}
		else if((xmlObj.jid.equalsIgnoreCase("PALEVO") ||xmlObj.jid.equalsIgnoreCase("REVMED")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)>795) ||xmlObj.jid.equalsIgnoreCase("SCOMS") ||xmlObj.jid.equalsIgnoreCase("MONRHU")||xmlObj.jid.equalsIgnoreCase("PATBIO")||xmlObj.jid.equalsIgnoreCase("BIOPHA")||xmlObj.jid.equalsIgnoreCase("REVRHU")||xmlObj.jid.equalsIgnoreCase("DIABET")||xmlObj.jid.equalsIgnoreCase("ERAP")||xmlObj.jid.equalsIgnoreCase("HYA")||xmlObj.jid.equalsIgnoreCase("STMAT")||xmlObj.jid.equalsIgnoreCase("CYA")||xmlObj.jid.equalsIgnoreCase("DF")||xmlObj.jid.equalsIgnoreCase("RESU")||xmlObj.jid.equalsIgnoreCase("ANTRO")||xmlObj.jid.equalsIgnoreCase("RMB")||xmlObj.jid.equalsIgnoreCase("REGE")||xmlObj.jid.equalsIgnoreCase("RAUSP")||xmlObj.jid.equalsIgnoreCase("RAUSPM")||xmlObj.jid.equalsIgnoreCase("AIPPRR")||xmlObj.jid.equalsIgnoreCase("BIOET")||xmlObj.jid.equalsIgnoreCase("JART")||xmlObj.jid.equalsIgnoreCase("JBHSI")||xmlObj.jid.equalsIgnoreCase("RAI")||xmlObj.jid.equalsIgnoreCase("RAI")||xmlObj.jid.equalsIgnoreCase("ANDO")||xmlObj.jid.equalsIgnoreCase("REVAL")||xmlObj.jid.equalsIgnoreCase("MEDMAL")||xmlObj.jid.equalsIgnoreCase("NEUCHI")||(xmlObj.jid.equalsIgnoreCase("NEUADO") && XT.fmcforall==false)||xmlObj.jid.equalsIgnoreCase("MEDDRO")||xmlObj.jid.equalsIgnoreCase("CANRAD")||xmlObj.jid.equalsIgnoreCase("RBMRET")||xmlObj.jid.equalsIgnoreCase("IRBM")||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())>Integer.parseInt(xmlObj.aid)))||xmlObj.jid.equalsIgnoreCase("NUTCLI")||xmlObj.jid.equalsIgnoreCase("ANCAAN")||xmlObj.jid.equalsIgnoreCase("CHIMAI") ||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==false))||(( xmlObj.jid.equalsIgnoreCase("TRACLI"))&&(XT.fmcforall==false)) || xmlObj.jid.equalsIgnoreCase("ANNPAL")|| xmlObj.jid.equalsIgnoreCase("ALTER")|| xmlObj.jid.equalsIgnoreCase("EXMATH")|| xmlObj.jid.equalsIgnoreCase("DDES")|| xmlObj.jid.equalsIgnoreCase("SMED") || xmlObj.jid.equalsIgnoreCase("PSFR") || xmlObj.jid.equalsIgnoreCase("EHN") || xmlObj.jid.equalsIgnoreCase("EHMCM") || xmlObj.jid.equalsIgnoreCase("PRPS")|| xmlObj.jid.equalsIgnoreCase("SOCTRA")|| xmlObj.jid.equalsIgnoreCase("EVOPSY") || xmlObj.jid.equalsIgnoreCase("REVMIC") || xmlObj.jid.equalsIgnoreCase("ACCPM") || xmlObj.jid.equalsIgnoreCase("DIABET") || xmlObj.jid.equalsIgnoreCase("EJTD") || xmlObj.jid.equalsIgnoreCase("EURGER") || xmlObj.jid.equalsIgnoreCase("EURPSY") || xmlObj.jid.equalsIgnoreCase("GEOBIO") || xmlObj.jid.equalsIgnoreCase("GOFS") || xmlObj.jid.equalsIgnoreCase("HANSUR") || xmlObj.jid.equalsIgnoreCase("INAN") || xmlObj.jid.equalsIgnoreCase("JOGOH") || xmlObj.jid.equalsIgnoreCase("JORMAS") || xmlObj.jid.equalsIgnoreCase("MEDNUC") || xmlObj.jid.equalsIgnoreCase("MYCMED") || xmlObj.jid.equalsIgnoreCase("RETRAM") || xmlObj.jid.equalsIgnoreCase("ARCPED") || xmlObj.jid.equalsIgnoreCase("BONSOI")||(xmlObj.jid.equalsIgnoreCase("BIONUT"))||(xmlObj.jid.equalsIgnoreCase("BIOMAG"))|| xmlObj.jid.equalsIgnoreCase("CULHER")) && absLang.equals("EN") && XT.articleType.equals("EN"))
		{
			//abstractInfo.append("\r\n\\Frenchcopyright\r\n%\\end{abstract}");
			//System.out.println("22-----------Ravi ");
			if(XT.IsCutOffCR.containsKey(xmlObj.jid.toString()))//mukesh on 01-11-08 TPMS
			{
				String st12=GetVal(count_abs);
				//System.out.println("22-----------"+st12);
				String tempAid="";
				tempAid=XT.IsCutOffCR.get(xmlObj.jid.toString()).toString();
				if(Integer.parseInt(xmlObj.aid) >=(Integer.parseInt(tempAid)))
				{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH"))
					{abstractInfo.append("\r\n\\FRcopyright\r\n\\end{abstract}");}
					else{abstractInfo.append("\r\n\\ENGcopyright\r\n\\end{abstract}");}
				}else{
				abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");
				//System.out.println("Inserted   --222222222222222222222222-");
				}
			}
			else{
			abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");
			//System.out.println("Inserted   --222222222222222222222222-");
			}
		//System.out.println("sssssssssabstractInfo -- > "+abstractInfo);
		//System.in.read();	
		}
		//else if(xmlObj.jid.equalsIgnoreCase("NEUCLI") && (absLang.equals("EN") && XT.articleType.equals("FR")) || (absLang.equals("FR") && XT.articleType.equals("EN")))
		//Added by Ravi [06/11/2006]//REAURG
//		else if((xmlObj.jid.equalsIgnoreCase("NEUCLI")|| xmlObj.jid.equalsIgnoreCase("ENCEP") || xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")|| xmlObj.jid.equalsIgnoreCase("REAURG") || xmlObj.jid.equalsIgnoreCase("JGYN")) && ((absLang.equals("EN") && XT.articleType.equals("FR")) || (absLang.equals("FR") && XT.articleType.equals("EN"))))
		else if((xmlObj.jid.equalsIgnoreCase("NEUCLI")||xmlObj.jid.equalsIgnoreCase("QUIP")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)<796)|| xmlObj.jid.equalsIgnoreCase("SCOMS") || xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("OTSR")||xmlObj.jid.equalsIgnoreCase("JOCLIM")||xmlObj.jid.equalsIgnoreCase("SODA")||xmlObj.jid.equalsIgnoreCase("LIVER")||xmlObj.jid.equalsIgnoreCase("DEMAN")||xmlObj.jid.equalsIgnoreCase("STLM")||xmlObj.jid.equalsIgnoreCase("ANNDER")||xmlObj.jid.equalsIgnoreCase("RCOT")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("JVS")||xmlObj.jid.equalsIgnoreCase("JCHIRV")||xmlObj.jid.equalsIgnoreCase("JCHIR")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("FEMME")||xmlObj.jid.equalsIgnoreCase("CND")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("EURTEL")||xmlObj.jid.equalsIgnoreCase("REVHOM")||xmlObj.jid.equalsIgnoreCase("JCCO")||xmlObj.jid.equalsIgnoreCase("JEUREA")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("JDMV")||xmlObj.jid.equalsIgnoreCase("BANM")|| xmlObj.jid.equalsIgnoreCase("REAURG")||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("MLONG")||xmlObj.jid.equalsIgnoreCase("LONGEV")||xmlObj.jid.equalsIgnoreCase("RMR")||xmlObj.jid.equalsIgnoreCase("PEDPUE")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("FANDER")||xmlObj.jid.equalsIgnoreCase("ACVDSP")||xmlObj.jid.equalsIgnoreCase("THERAP")||xmlObj.jid.equalsIgnoreCase("JEMEP")||xmlObj.jid.equalsIgnoreCase("TOXAC")||xmlObj.jid.equalsIgnoreCase("ONCOHP")||xmlObj.jid.equalsIgnoreCase("JRADIO")||xmlObj.jid.equalsIgnoreCase("JRDIA")||xmlObj.jid.equalsIgnoreCase("DIII")||(xmlObj.jid.equalsIgnoreCase("TRACLI")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("MSOM")||xmlObj.jid.equalsIgnoreCase("GCB")||(xmlObj.jid.equalsIgnoreCase("CLINRE"))||(xmlObj.jid.equalsIgnoreCase("CLIREX"))||(xmlObj.jid.equalsIgnoreCase("NEUADO")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("VETCLI")||xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX") || xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("AFJU")||xmlObj.jid.equalsIgnoreCase("AFORL")||xmlObj.jid.equalsIgnoreCase("ANORL")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("JBCT")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD") ||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("MSOM")||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid)))))
		{	
			//System.out.println("xmlObj.getAbstract[ "+absLang+" ]Language(absLang): "+xmlObj.getAbstractLanguage(absLang));
			
			String st12=GetVal(count_abs);
			//System.out.println("3333------------------"+st12);
			if(XT.IsCutOffCR.containsKey(xmlObj.jid.toString()))//mukesh on 01-11-08 TPMS
			{
				String tempAid="";
				tempAid=XT.IsCutOffCR.get(xmlObj.jid.toString()).toString();
				if(Integer.parseInt(xmlObj.aid) >=(Integer.parseInt(tempAid)))
				{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("FRENCH"))
					{abstractInfo.append("\r\n\\FRcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
					{abstractInfo.append("\r\n\\ITcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("SPANISH"))
					{abstractInfo.append("\r\n\\SPcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("PORTUGUESE"))
					{abstractInfo.append("\r\n\\PORTcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("CATALAN"))
					{abstractInfo.append("\r\n\\CATAcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else{abstractInfo.append("\r\n\\ENGcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
				}else
					{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
						{
							abstractInfo.append("\r\n\\ITaliancopyright\r\n\\end{abstract"+st12+"}\r\n");
						}
						else
						{
							abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract"+st12+"}\r\n");
						}
					//System.out.println("abstractInfo -3333- > "+abstractInfo);
				}
			}else
				{
					if(xmlObj.getAbstractLanguage(absLang).equalsIgnoreCase("ITALIAN"))
					{abstractInfo.append("\r\n\\ITcopyright"+st12+"\r\n\\end{abstract"+st12+"}\r\n");}
					else
					{abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract"+st12+"}\r\n");}
			//System.out.println("abstractInfo -- > "+abstractInfo);
			}
			
		//System.in.read();	
		}
		/*else if((xmlObj.jid.equalsIgnoreCase("NEUCLI")|| xmlObj.jid.equalsIgnoreCase("ENCEP") || xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")|| xmlObj.jid.equalsIgnoreCase("REAURG")|| xmlObj.jid.equalsIgnoreCase("JGYN")) && (absLang.equals("EN") && XT.articleType.equals("EN")) || (absLang.equals("FR") && XT.articleType.equals("FR")))
			abstractInfo.append("\r\n\\Frenchcopyright\r\n\\end{abstract}");*/ //blocked 07/11/2007

		else
			abstractInfo.append("\r\n\\end{abstract}");


//System.out.println("abstractInfo \n"+abstractInfo);
//System.in.read();
		return abstractInfo.toString();
	}

public String getHistoryDate(String dtTag)throws java.io.IOException
	{
		//System.out.println("dtTag--->>"+dtTag);
		String months[]={"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
		/**
		* Modyfi Date : [07/08/2007]
		* Modify By : Ravi
		* Change Point: In Franch atricle the History date for the month "Janvier" the charactor "J" should be in lower case
		* Change Request By : TPMS dated on [07/08/2007]
		*/
		//String FRmonths[]={"Janvier", "f\\'{e}vrier", "mars", "avril", "mai", "juin", "juillet", "ao\\^{u}t", "septembre", "octobre", "novembre", "d\\'{e}cembre"};//old
		String FRmonths[]={"janvier", "f\\'{e}vrier", "mars", "avril", "mai", "juin", "juillet", "ao\\^{u}t", "septembre", "octobre", "novembre", "d\\'{e}cembre"};
		String ITmonths[]={"gennaio", "febbraio", "marzo", "aprile", "maggio", "giugno", "luglio", "agosto", "settembre", "ottobre", "novembre", "dicembre"};
		String ESmonths[]={"enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"};//30-03-2010
		String DEmonths[]={". Januar", ". Februar", ". März", ". April", ". Mai", ". Juni", ". Juli", ". August", ". September", ". Oktober", ". November", ". Dezember"};//26-04-2010
		String CAmonths[]={"gener", "febrer", "març", "abril", "maig", "juny", "juliol", "agost", "setembre", "octubre", "novembre", "desembre"};//18-01-2011
		String PTmonths[]={"janeiro", "fevereiro", "março", "abril", "maio", " junho", "julho", "agosto", "setembro", "outubro", "novembro", "dezembro"};//11-02-2011
		//end
		String dateTag= dtTag;
		int ind=-1;
		String day="";
		String TempArticleLanguage="";//02-07-2010
		if (xmlObj.jid.equalsIgnoreCase("DCJWKP"))//02-07-2010
		{
			TempArticleLanguage=XT.articleType;
			XT.articleType="EN";
		}
		if(dateTag.indexOf("DAY=\"")!=-1) // 04-01-2005
		{
			ind= dateTag.indexOf("DAY=\"")+5;
			day=	dateTag.substring(ind, dateTag.indexOf("\"", ind)).trim();
		}

		ind= dateTag.indexOf("MONTH=\"")+7;
		String mon=	dateTag.substring(ind, dateTag.indexOf("\"", ind)).trim();

		ind= dateTag.indexOf("YEAR=\"")+6;
		String yr=	dateTag.substring(ind, dateTag.indexOf("\"", ind)).trim();

		String dtType= dateTag.substring(dateTag.indexOf("<CE:DATE-")+9, dateTag.indexOf(" ")).toLowerCase();

		if(XT.articleType.equalsIgnoreCase("FR"))
		{
			String temp="";
			if(dtType.equalsIgnoreCase("revised"))
				temp=("\\"+dtType+"{le "+day+" "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			else
				temp=("\\"+dtType+"{"+day+" "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			
			if(day.length()>0)  
			{
				//return ("\\"+dtType+"{"+day+" "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");//blocked 29/01/2008
				/**
				*Date : 29/01/2008
				* Added By : Ravi
				* Change Point : In case of model is -MODDFrench then received/accept date will add
				*                \\st or \\er with day as per main language
				* Change request By : TPMS
				*/
				//
				//if(XT.modelStyle.equalsIgnoreCase("-MODDFrench") && day.equals("1"))
				if((xmlObj.check_MassonJid==true) && day.equals("1"))//16/02/2008
				{
					if(dtType.equalsIgnoreCase("revised"))
						return ("\\"+dtType+"{le "+day+"\\er "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
					else
						return ("\\"+dtType+"{"+day+"\\er "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
				
				}
				else
				{
					if(dtType.equalsIgnoreCase("revised"))
						return ("\\"+dtType+"{le "+day+" "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
					else
						return ("\\"+dtType+"{"+day+" "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
				}
			}
			else
			{
				if(dtType.equalsIgnoreCase("revised"))
					return ("\\"+dtType+"{le "+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
				else
					return ("\\"+dtType+"{"+FRmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			}
		}
		else if(XT.articleType.equalsIgnoreCase("IT"))
		{
			String temp=("\\"+dtType+"{"+day+" "+ITmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			
			if(day.length()>0)  
			{
				if((xmlObj.check_MassonJid==true) && day.equals("1"))//16/02/2008
				{
					return ("\\"+dtType+"{"+day+"\\er "+ITmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
				
				}
				else
					return ("\\"+dtType+"{"+day+" "+ITmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			}
			else
				return ("\\"+dtType+"{"+ITmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
		}
		else if(XT.articleType.equalsIgnoreCase("DE"))
		{
			String temp=("\\"+dtType+"{"+day+""+DEmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			
			if(day.length()>0)  
			{
				if((xmlObj.check_MassonJid==true) && day.equals("1"))//16/02/2008
				{
					return ("\\"+dtType+"{"+day+"\\er "+DEmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
				
				}
				else
					return ("\\"+dtType+"{"+day+""+DEmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			}
			else
				return ("\\"+dtType+"{"+DEmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
		}
		else if(XT.articleType.equalsIgnoreCase("PT"))
		{
			String temp=("\\"+dtType+"{"+day+" de "+PTmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
			
			if(day.length()>0)  
			{
				return ("\\"+dtType+"{"+day+" de "+PTmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
			}
			else
				return ("\\"+dtType+"{ de "+PTmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
		}
		else if(XT.articleType.equalsIgnoreCase("ES"))
		{
			String temp=("\\"+dtType+"{"+day+" "+ESmonths[Integer.parseInt(mon)-1]+" "+yr+"}");
			
			if(day.length()>0)  
			{
				if((xmlObj.check_MassonJid==true) && day.equals("1"))//16/02/2008
				{
					return ("\\"+dtType+"{"+day+"\\er de "+ESmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
				
				}
				else
					return ("\\"+dtType+"{"+day+" de "+ESmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
			}
			else
				return ("\\"+dtType+"{ de "+ESmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
		}
		else if(XT.articleType.equalsIgnoreCase("CA"))
		{
			String temp=("\\"+dtType+"{"+day+" de "+CAmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
			
			if(day.length()>0)  
			{
					return ("\\"+dtType+"{"+day+" de "+CAmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
			}
			else
				return ("\\"+dtType+"{ de "+CAmonths[Integer.parseInt(mon)-1]+" de "+yr+"}");
		}
		else	//else if(XT.articleType.equalsIgnoreCase("EN"))
		{
			if (xmlObj.jid.equalsIgnoreCase("DCJWKP"))//02-07-2010
			{
				XT.articleType=TempArticleLanguage;
			}
			String temp=("\\"+dtType+"{"+day+" "+months[Integer.parseInt(mon)-1]+" "+yr+"}");
			if(day.length()>0)  
			{
				//return ("\\"+dtType+"{"+day+"\\ "+months[Integer.parseInt(mon)-1]+" "+yr+"}");//blocked 29/01/2008
				/**
				*Date : 29/01/2008
				* Added By : Ravi
				* Change Point : In case of model is -MODDFrench then received/accept date will add
				*                \\st or \\er with day as per main language
				* Change request By : TPMS
				*/
				
				//if(XT.modelStyle.equalsIgnoreCase("-MODDFrench")&& day.equals("1"))
				if((xmlObj.check_MassonJid==true)&& day.equals("1"))//16/02/2008
					return ("\\"+dtType+"{"+day+"\\st "+months[Integer.parseInt(mon)-1]+" "+yr+"}");
				else if(xmlObj.jid.equalsIgnoreCase("PROTIS"))//24-06-2010
					return ("\\"+dtType+"{"+months[Integer.parseInt(mon)-1]+" "+day+", "+yr+"}");
				else
					return ("\\"+dtType+"{"+day+" "+months[Integer.parseInt(mon)-1]+" "+yr+"}");
			}
			else
				return ("\\"+dtType+"{"+months[Integer.parseInt(mon)-1]+" "+yr+"}");
		}
	}
	

	public String processArticleFootnote()throws java.io.IOException
	{
		boolean FNlblFlag=false;
		String tag= "";
		StringBuffer atlfn= new StringBuffer();
		boolean first=false;
		while (!tag.equals("</CE:ARTICLE-FOOTNOTE>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				
				if (tag.equals("<CE:LABEL>"))
				{	
					String temp=xmlObj.extractData("</CE:LABEL>", true);
					tempCountTitle=temp;
					//Added By Ravi [06/12/2006 ]
				//	tempCountTitle=tempCountTitle.replaceAll("\\\\\\(\\[A-Za-z]+\\\\\\)","$1");
					tempCountTitle=tempCountTitle.replaceAll("\\\\\\(","");
					tempCountTitle=tempCountTitle.replaceAll("\\\\\\)","");

					if(TempTitleEntity.equals(""))
					{
							TempTitleEntity="{"+tempCountTitle+"}";
					}
					else
					{
							TempTitleEntity+=", {"+tempCountTitle+"}";
					}
				//end
					//System.out.println("tempCountTitle ==> "+tempCountTitle);
					//System.in.read();
					if(temp.indexOf("\\\\(") != 0)
					{
						temp=temp.replaceAll("\\\\\\(","");
						temp=temp.replaceAll("\\\\\\)","");
					}
			
			

					atlfn.append("["+temp+"]");
					FNlblFlag=true;
				}

				else if (tag.startsWith("<CE:NOTE-PARA"))
				{
					if(first==true)
						atlfn.append("\\newline\r\n");

					if(FNlblFlag)
					{
						atlfn.append("{");
						FNlblFlag=false;
					}
					else
					{
						atlfn.append("{");
					}
					atlfn.append(xmlObj.extractData("</CE:NOTE-PARA>", true));
					first=true;
					
				}
			}
		}
		return atlfn.toString();
	}

/**
*Modify Date :[12/09/2007]
* Change Point : Below function is use for excrating Marker tag
*Change By : Ravi
*/

//********************************************************************
public void processMarkerTag()throws java.io.IOException
	{
		
		

		String tag= "";
		
		while (!tag.equals("</CE:MARKERS>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				
				if (tag.startsWith("<CE:MARKER"))
				{
					marker_check_first=true;
					marker_name.add(xmlObj.getAttributeValue(tag, "NAME"));

				}
			}
		}
		
	}
//*********************************************************************





	public String getHeadContents()throws java.io.IOException
	{
		String tag="";
		StringBuffer artInfo     = new StringBuffer();
		StringBuffer artHeadInfo = new StringBuffer();
		StringBuffer artAbs      = new StringBuffer();
		//StringBuffer artAbsPIO      = new StringBuffer();//
		StringBuffer artKwd      = new StringBuffer();
		StringBuffer artKwdFranch      = new StringBuffer();//20/01/2009

		StringBuffer artFootnote = new StringBuffer();
		StringBuffer authorInfo  = new StringBuffer();;
		//StringBuffer Move_botton_author_Group_Info  = new StringBuffer();
		String historyDates= "";
		String artTitle= "";
		String RPPNEN_artTitle= "";//ravi 14-7-2011
		String subTitle="";//bhavesh 17/08/06
		String localLang="";//20/01/2009
		altTitle= "";
		String bookReview="";
		int count_author_group=1; //01/03/2008
		boolean firstKwd   = true;
		int count_trsth=1; //22/08/2008
		while (!tag.equals("</HEAD>") && !tag.equals("</SIMPLE-HEAD>") && !tag.equals("</BOOK-REVIEW-HEAD>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				//System.out.println("head tag : "+tag);
				/*if (tag.equals("<ITEM-INFO>"))
				{
					artHeadInfo.append(processItemInfo());
				}*/
				/*else if (tag.equals("<CE:FLOATS>"))
				{
					artInfo.append(processFloats());
				}*/
				if (tag.equals("<CE:ARTICLE-FOOTNOTE>"))
				{
					if(artFootnoteStatus==true)
					{
						artFootnote.append("}\r\n\\Titlefootnote"+processArticleFootnote());
					}
					else
					{
						artFootnote.append(processArticleFootnote());
						artFootnoteStatus=true;
					}
					//System.out.println("artFootnote====>>"+artFootnote);
				}
				
				/**
				*Modify Date :[12/09/2007]
				* Change Point : Below function is use for excrating Marker tag
				*Change By : Ravi
				*/
				else if(tag.equals("<CE:MARKERS>"))
				{	
					processMarkerTag();
				}
				//end 
				//added by avinandan on 11-08-04
				else if(tag.equals("</CE:TEXTBOX-HEAD>"))
				{
					break;
				}
				//end mark
				else if (tag.startsWith("<SB:REFERENCE"))
				{
					//System.out.println("TAGGGGGG   "+reference);
					
					if(reference.length()>0)
					{
						reference+="\\\\\r\n";
					}
					//reference+=bib.sbReference(true);
					//Avinandan Changes
					bookReview = xmlObj.bibStyle;
					if(xmlObj.pit.equalsIgnoreCase("BRV") && !xmlObj.jid.equalsIgnoreCase("SOCSCI"))
					{
						xmlObj.bibStyle = "brvhead";
						/*
							brvhead --> It is a new ref. style coded due to new cap guide 1.4 in whiuch client 
							changed the ref. style in head section in BRV only.
						*/
						reference += bib.sbReference(true);
						
						//System.out.println("\nbrvhead: "+reference);
					}
					else
					{
						xmlObj.bibStyle="1";
						reference += bib.sbReference(true);
						
						
					}

				/*	if(xmlObj.jid.equals("PHYST") || xmlObj.jid.equals("DRUPOL")|| xmlObj.jid.equals("SOCSCI") || xmlObj.jid.equals("TRSTMH"))
					{
						if(reference.indexOf(bib.physt_brv) != -1 )
						{
							if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
							{
								reference=bib.physt_brv+"}}\r\n\n\\noindent Edited by "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());
							}
							else
							{
								reference=bib.physt_brv+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());
							}
							reference=reference.replaceAll("\\,\\,",",");
							reference=reference.replaceAll("\\, \\,",",");
							reference=reference.replaceAll("  "," ");
							reference=reference.replaceAll(" \\.","\\.");
							reference=reference.replaceAll("  \\.","\\.");
							reference=reference.replaceAll("\\.\\.",".");
						}
					}
				*/
					if(xmlObj.jid.equals("PHYST") || xmlObj.jid.equals("DRUPOL")|| xmlObj.jid.equals("SOCSCI") || xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						if(reference.indexOf(bib.physt_brv) != -1 )
						{
							if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
							{
								//reference=bib.physt_brv+"}}\r\n\n\\noindent Edited by "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());//old
								/*
								*Added By Ravi [22/02/2007]
								*Change Request By : Vivek
								* Change Point : Space below misctitle text \vspace*{12pt} SOCSCI journal
								*/
								String AuEdt="";
								AuEdt=reference.substring(0,reference.indexOf(bib.physt_brv));
								if(AuEdt.indexOf("(Ed.)")==-1)//added by mukesh
								{
									reference=bib.physt_brv+"}}\r\n\n\\noindent By "+AuEdt+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"\\vspace*{12pt}";
								}
								else{
									AuEdt=AuEdt.replaceAll(" \\(Ed\\.\\)","");
									reference=bib.physt_brv+"}}\r\n\n\\noindent Edited by "+AuEdt+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"\\vspace*{12pt}";
								}
								//System.out.println("Refferences    "+reference);
								//System.out.println("11111------>    "+reference.substring(0,reference.indexOf(bib.physt_brv))+"End");
								//System.out.println("22222------>    "+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"End");
								//reference=bib.physt_brv+"}}\r\n\n\\noindent Edited by "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"\\vspace*{12pt}";

							}
							else
							{
								if(xmlObj.jid.equals("PHYST"))
								{
										//Added By Ravi [11/11/2006][Vivek]
										String Combine_Title_Edition="";
										String tr=reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());
										//System.out.println("reference : "+reference);
										StringBuffer ph=new StringBuffer(bib.physt_brv);
										if(bib.physt_brv.endsWith("}}"))
										{
											int in=ph.lastIndexOf("}}");
											if(in !=-1)
											{
												//Added by Mukesh on 03-09-08
												if(bib.Move_Edition_In_Book_Review.toString().length()>0)
												{
													ph=ph.insert(in, "{\\bf{, "+bib.Move_Edition_In_Book_Review+"}}");
												}
											}
										}
										//System.out.println("bib.TempAutoth.length() "+bib.TempAutoth.length());
										if(bib.TempAutoth.length()>0)
											{
												//Added by Mukesh on 03-09-08// EDITION to appear in bold
												//Combine_Title_Edition=ph.toString()+", "+bib.TempAutoth;
												
												Combine_Title_Edition=ph.toString()+"{\\bf{, "+bib.TempAutoth+"}}";
											}
											else
											{
												int in=ph.lastIndexOf("}}");
												if(in !=-1){
													Combine_Title_Edition=ph.insert(in,", ").toString();
												}
											}
											if(Combine_Title_Edition.startsWith("{\\bf{"))
											{
												check_bold_in_title_physt=true;
											}
											else
											{
												check_bold_in_title_physt=false;
											}
										if(tr.indexOf(',',0) != -1)
										{
											//System.out.println("bib.physt_brv -->"+tr);
										//	System.in.read();
											
										//  reference=bib.physt_brv+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length());
											
											/**
											* Date : [29/03/2008]
											* Added By : Ravi 
											* Change Point : noindent required when Jid is PHYST
											* Change Request By : TPMS
											*/
											//reference=Combine_Title_Edition+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length())+"\n\\vspace*{12pt}";//old blocked //[29/03/2008]
											//System.out.println("Combine_Title_Edition : "+Combine_Title_Edition);

											if(check_bold_in_title_physt==true)
											{
												//System.out.println("Combine_Title_Edition8 "+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()));
												//System.out.println("reference: "+reference+"\n\n\n");
												//System.out.println("bib.physt_brv: "+bib.physt_brv);
												//System.out.println("1 reference: "+reference);
												//System.out.println("1 Combine_Title_Edition: "+Combine_Title_Edition);
												reference=Combine_Title_Edition+" "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
												check_bold_in_title_physt=false;
												//System.out.println("reference: "+reference);
											}
											else
											{

												reference=Combine_Title_Edition+",}} "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
											}
											//reference=Combine_Title_Edition+",}} "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
											//System.out.println("Combine_Title_Edition -->"+Combine_Title_Edition);
										}
										else
										{
											if(check_bold_in_title_physt==true)
											{
												reference=Combine_Title_Edition+", "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
												check_bold_in_title_physt=false;
											}
											else
											{
												reference=Combine_Title_Edition+",}} "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
											}
											//reference=Combine_Title_Edition+",}} "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim()+"}\n";
											//reference=Combine_Title_Edition+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"\n\\vspace*{12pt}";//old blocked //[29/03/2008]

										}
								}
								else
								{
									String bibr=reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());
									if(bibr.startsWith(", "))
									{
										bibr=bibr.substring(2,bibr.length());
										//19/05/2008
										if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
										{
											reference=bib.physt_brv+", "+bib.Move_Edition_In_Book_Review+"}}\r\n\\BrvOtherRef{\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+bibr+"}";
										}
										else
										reference=bib.physt_brv+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+bibr;
									}
																		
									else{
										if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
										{
											reference=bib.physt_brv+", "+bib.Move_Edition_In_Book_Review+"}}\r\n\\BrvOtherRef{\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length())+"}";
										}
										else
										reference=bib.physt_brv+"}}\r\n\n\\noindent "+reference.substring(0,reference.indexOf(bib.physt_brv))+reference.substring(reference.indexOf(bib.physt_brv)+(bib.physt_brv).length());
								   }
									
								}
							}
							
							reference=reference.replaceAll("\\,\\,",",");
							reference=reference.replaceAll("\\, \\,",",");
							reference=reference.replaceAll("  "," ");
							reference=reference.replaceAll(" \\.","\\.");
							reference=reference.replaceAll("  \\.","\\.");
							reference=reference.replaceAll("\\.\\.",".");
						}
					}
					if(xmlObj.jid.equals("EURR"))
					{
						//System.out.println("EURR bib.physt_brv ---> " + bib.physt_brv);

						if(reference.indexOf(bib.physt_brv) !=- 1)
						{
							//System.out.println("bib.physt_brv -->"+bib.physt_brv);
							//System.out.println(" ssssssssssss reference -->"+reference);
							/**
							*Modify Date : 29/09/2007
							*Modify By Ravi
							*Change Point : Remove comma if it start with
							*/
							reference=bib.physt_brv+"}}\r\n\n\\noindent {\\bf{"+(reference.substring(0,reference.indexOf(bib.physt_brv))).trim()+reference.substring(reference.indexOf(bib.physt_brv)+1+(bib.physt_brv).length()).trim();
							reference=reference.replaceAll("\\,\\,",",");
							reference=reference.replaceAll("\\, \\,",",");
							reference=reference.replaceAll("  "," ");
							reference=reference.replaceAll(" \\.","\\.");
							reference=reference.replaceAll("\\.\\.",".");
							//System.out.println("reference -->"+reference);
							//System.in.read();
						}
					}
					
					reference=reference.replaceAll("\\,\\,",",");
					reference=reference.replaceAll("\\, \\,",",");
					reference=reference.replaceAll("  "," ");
					reference=reference.replaceAll(" \\.","\\.");
					reference=reference.replaceAll("  \\.","\\.");
					reference=reference.replaceAll(" \\;","\\;");
					reference=reference.replaceAll("\\.\\.",".");

					if(xmlObj.jid.equals("PHYST") && reference.endsWith(".")) //By Nisha
					{
						reference=reference.substring(0,reference.length()-1);
					}

					xmlObj.bibStyle=bookReview;
				}

				else if (tag.startsWith("<CE:OTHER-REF"))
				{
					//System.out.println("AAA: "+reference);
					if(reference.length()>0)
					{

						if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))//22/08/2008
						{
							reference+="\r\n\\miscTitle";
						}
						else
						{
							reference+="}\r\n\\miscTitle{";
						}
						
					}
					reference+=bib.processOtherRef();
					
					if(xmlObj.jid.equals("PHYST") || xmlObj.jid.equals("DRUPOL") || xmlObj.jid.equals("SOCSCI")  || xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						//javax.swing.JOptionPane.showMessageDialog(null,"MISCtitle IN BOLD FACE WITH SEPARATE LINE..REST AS ROMAN...","ALERT INFORMATION!!!", javax.swing.JOptionPane.INFORMATION_MESSAGE);
						if(xmlObj.jid.equals("TRSTMH")|| xmlObj.jid.equals("PHYST")||xmlObj.jid.equals("INHE"))
						{
							
								reference+="}";
							
						}
						else
						{
							reference+="}}";
						}
						
					}
					else if(xmlObj.jid.equals("EURR"))
					{
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] MISCtitle IN BOLD FACE WITH SEPARATE LINE..REST AS ALSO BOLD...");
							
						}
						else{
							javax.swing.JOptionPane.showMessageDialog(null,"MISCtitle IN BOLD FACE WITH SEPARATE LINE..REST AS ALSO BOLD...","ALERT INFORMATION!!!", javax.swing.JOptionPane.INFORMATION_MESSAGE);
						}
						reference+="}}";
					}
				}
				else if (tag.startsWith("<CE:ALT-TITLE "))
				{
					/*System.out.println(XT.articleType);
					System.out.println(absLangGlobal);
					if((XT.articleType.equalsIgnoreCase("FR") && absLangGlobal.equalsIgnoreCase("EN")) || (XT.articleType.equalsIgnoreCase("EN") && absLangGlobal.equalsIgnoreCase("FR")))
					{
						//Bhavesh 16/8/06
						altTitle = "\r\n\\Alttitle{" + xmlObj.extractData("</CE:ALT-TITLE>", true) + "";
					}
					else*/
						//altTitle = "\r\n\\Alttitle{" + xmlObj.extractData("</CE:ALT-TITLE>", true) + "}";//[18/12/2006]
						String altTitleTemp = xmlObj.extractData("</CE:ALT-TITLE>", true) ;
						RPPNEN_artTitle=altTitleTemp;//14-04-2011
						coutAltTitle++;
						if(coutAltTitle==2)
						{
							altTitle += "}\r\n\\AlttitleTwo{" +altTitleTemp ;
						}
						else
						{
							altTitle = "\r\n\\Alttitle{" +altTitleTemp ;
						}
						//altTitle = "\r\n\\Alttitle{" + xmlObj.extractData("</CE:ALT-TITLE>", true) ;
						//System.out.println("altTitle : "+altTitle);
						//System.in.read();

				}
				//[18/12/2006][Added By Ravi Change By Vivek]
				else if(tag .startsWith("<CE:ALT-SUBTITLE"))
				{
					altSubTitle= xmlObj.extractData("</CE:ALT-SUBTITLE>", true) ;//old //02/02/2008
					

					//if((XT.modelStyle.equalsIgnoreCase("-MODDFrench"))||(XT.modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
					if((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
							//MODD and MODE french removed by mukesh on 18-10-08
							//to make this case true for all masson jid.
							//if(xmlObj.check_MassonJid)
							{
								altTitle+=". "+altSubTitle+"";//old //02/02/2008
								//System.out.println("YESSSSSSSSSSSSSS "+altSubTitle);
							}
							else
							{
								altTitle+="\\\\\n"+altSubTitle+"";//old //02/02/2008
								//System.out.println("NOOOOOOOOOOOOOOO "+altSubTitle);
							}

					//altTitle+="\\\\"+altSubTitle;
					altSubTitle="";
				}
				//end
				else if (tag.equals("<CE:ABSTRACT>") || tag.startsWith("<CE:ABSTRACT "))
				{
					///Added by Avinandan till mark
					isAbsFound=true;//21-05-2011
					String absLang="";
					int ind=tag.indexOf("CLASS=\"GRAPHICAL\"");
					if(ind > 0)
					{
						processGraphicalAbstract(artTitle);
					}
					else //28-08-2012 JADTD520 Updation
					{
						ind=tag.indexOf("CLASS=\"AUTHOR-HIGHLIGHTS\"");
						if(ind > 0)
						{
							isAuthorAbs=true;
							if(isEditorAbs)
							{
								System.out.println("[ERROR]: EDITOR-HIGHLIGHTS AND AUTHOR-HIGHLIGHTS BOTH PRESENT IN XML. CANNOT PROCESS. PLEASE CONTECT TO COPY-EDITING DEPARTMENT.\r\n System exits.....");
								System.exit(0);
							}
							else
							processGraphicalAbstract(artTitle);
						}
						else 
						{
							ind=tag.indexOf("CLASS=\"EDITOR-HIGHLIGHTS\"");
							if(ind > 0)
							{
								isEditorAbs=true;
								if(isAuthorAbs)
								{
									System.out.println("[ERROR]: AUTHOR-HIGHLIGHTS AND EDITOR-HIGHLIGHTS BOTH PRESENT IN XML. CANNOT PROCESS. PLEASE CONTECT TO COPY-EDITING DEPARTMENT.\r\n System exits.....");
									System.exit(0);
								}
								else
								processGraphicalAbstract(artTitle);
							}
						}
					}//28-08-2012 JADTD520 Updation
					//Added by Avinandan
					if(xmlObj.jid.equals("CHIABU"))
					{
						ind=0;
						if ((ind= tag.indexOf("XML:LANG=\""))>0)
						{
							absLang= tag.substring(tag.indexOf("XML:LANG=\"")+10, tag.indexOf("\"", ind+10));
						}
						//System.out.println("hvhv"+absLang);
						
						if(absLang.equals(""))//abhay 04/10/2006
							absLang = XT.articleType;
						
						absLang=xmlObj.getAbstractLanguage(absLang);
						//System.out.println("hvhv"+absLang);
						if(!absLang.equals("ENGLISH"))
						{
							abstractOtherLang.append(processAbstract(tag));
						}
						else
							artAbs.append(processAbstract(tag));
					}
					else
					{
						/*if((artAbs.lastIndexOf("\\end{abstract}") != -1) && (tag.equals("<CE:ABSTRACT>")))
						{
							if(!xmlObj.jid.equals("IMMBIO"))//06/11/2007
							{
								artAbs.insert(artAbs.lastIndexOf("\\end{abstract}"),"\r\n\\Frenchcprt\r\n%");
								
							}
						}*/
						
							++count_abs;
							//System.out.println("count_abs "+count_abs);
							if(count_abs==1 &&xmlObj.jid.equals("PIO"))
							{
								artAbs.append(processAbstract(tag));
							}
							else if(count_abs>1 &&xmlObj.jid.equals("PIO"))
							{
								artAbsPIO.append(processAbstract(tag));
							}
							else if(count_abs>1 &&xmlObj.jid.equals("MLA"))
							{
								artAbsPIO.append(processAbstract(tag));
							}
							else
							{
								artAbs.append(processAbstract(tag));
							}
							//System.out.println("------------------------\n"+artAbsPIO);
							/**
							*[20/11/2007]
							*Added % remarks when more then abstract in Diabet 
							*/
							if(artAbs.indexOf("\\end{abstract}\\begin{abstract",0)!=-1)
							{
								//System.out.println("------------------------");
								int abs_pos=artAbs.indexOf("\\end{abstract}\\begin{abstract",0);
								artAbs.insert(abs_pos,"%");
							}
							//end
					}

					if(isPalevoAbsFound)//abhay 26/08/2006
					{
						altTitle = "";
						isPalevoAbsFound = false;
					}
					
				}
				else if (tag.startsWith("<CE:KEYWORDS"))
				{
					
					//added by avinandan on 27-7-04
					String tempClass=xmlObj.getAttributeValue(tag, "CLASS");
					//tempClass=xmlObj.getAttributeValue(tag, "CLASS");
					//System.out.println("1 -----"+tempClass);
					if(tempClass.equals("ABR"))
					{
						//System.out.println("1 ---xmlObj.jid--"+xmlObj.jid);
						//System.out.println("1 --XT.modelStyle---: "+XT.modelStyle);
						//if(!xmlObj.jid.equals("YDLD")) 
						if(xmlObj.jid.equals("PROTIS"))
						{
							//xmlObj.abrKwd.append("\r\n\\Abbreviation{");
							TempCorAbb="\r\n\\Abbreviation{";
							tempClass=processKeywords(tag);
							tempClass=tempClass.replaceAll("\\\\sep",";");
							if(tempClass.endsWith("."))
							{
								//xmlObj.abrKwd.append(tempClass+"}");
								TempCorAbb+=tempClass+"}";
							}
							else
							{
								//xmlObj.abrKwd.append(tempClass+".}");
								TempCorAbb+=tempClass+".}";
							}
							//System.out.println("tempClass  -- >  "+tempClass);
							//System.in.read();
						}
						else if((!xmlObj.jid.equals("YDLD")) && (!XT.modelStyle.equals("7")||!XT.modelStyle.equals("-PIO")|| !XT.modelStyle.equals("-Mod7IChemE")))//Ravi 01/11/2006
						{
							xmlObj.abrKwd.append("\r\n\\renewcommand{\\thefootnote}{}\r\n\\footnotetext{\r\n");
							tempClass=processKeywords(tag);
							//System.out.println("tempClass  -- >  "+tempClass);
							//System.in.read();
							tempClass=tempClass.replaceAll("\\\\sep",";");
							if(tempClass.endsWith("."))
							{
								xmlObj.abrKwd.append(tempClass+"}");
							}
							else
							{
								xmlObj.abrKwd.append(tempClass+".}");
							}
						}//-Mod7IChemE
						else if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO")))//Ravi 01/11/2006
						{
						/*
							xmlObj.abrKwd.append("\r\n\\Abbreviation{\r\n");
							tempClass=processKeywords(tag);
							tempClass=tempClass.replaceAll("\\\\sep",";");
							xmlObj.abrKwd.append(tempClass+"}");
						*/
						//Added By Ravi 01/11/2006
							TempCorAbb="\r\n\\Abbreviation{\r\n";
							tempClass=processKeywords(tag);
							tempClass=tempClass.replaceAll("\\\\sep",";");
							if(tempClass.endsWith("."))
							{
								TempCorAbb+=tempClass+"}";
							}
							else
							{
								TempCorAbb+=tempClass+".}";
							}
							//System.out.println("TempCorAbb  -- >  "+TempCorAbb);
							//System.in.read();
													
						}
						else
						{
							xmlObj.abrKwd.append("\r\n\\ABBR{");
							tempClass=processKeywords(tag);
							tempClass=tempClass.replaceAll("\\\\sep",";");
							xmlObj.abrKwd.append(tempClass+"}");
							//System.out.println("tempClass  -- >  "+tempClass);
							//System.in.read();
						}
					}
					else if(tempClass.equalsIgnoreCase("INCHIKEY"))//22-01-2014
					{
						//Keywords class="inchikey" not to be printed on PDF
						//System.out.println("INCHIKEY FOUND");
						//System.in.read();
					}
					else
					{ 
						if(tempClass.equalsIgnoreCase("KEYWORD"))
						++count_keywords;//07/11/2007
						if(tempClass.equalsIgnoreCase("JEL"))
						++count_JAL_keywords;
						//checkKeyword  xmlObj.jid
						String cutOffaid=getKeywordCutOff(xmlObj.jid);
						//System.out.println(" count_keywords "+count_keywords);
						//System.in.read();
//						artKwd.append(processKeywords(tag));//old blocked.
						if(cutOffaid.trim().length()>0 && XT.fmcforall==false){
							//System.out.println("count_keywords "+count_keywords);
							//commented by debottam if(Integer.parseInt(xmlObj.aid)>=Integer.parseInt(cutOffaid))
							if(Integer.parseInt(xmlObj.aid.substring(0,xmlObj.aid.indexOf(".")==-1?xmlObj.aid.length():xmlObj.aid.indexOf(".")))>=Integer.parseInt(cutOffaid))
							{
								//System.out.println("1");
								if(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid)))//System.out.println("2");
								{
									artKwd.append(processKeywords(tag));
								}else
								{
								int ind=0;
								if ((ind= tag.indexOf("XML:LANG=\""))>0)
								{
									localLang= tag.substring(tag.indexOf("XML:LANG=\"")+10, tag.indexOf("\"", ind+10));
								}
								//System.out.println("1 localLang "+localLang);
								if(localLang.equals(""))
									localLang = XT.articleType;
								//System.out.println("2 localLang "+localLang);
								//System.in.read();
								if(localLang.equalsIgnoreCase("FR"))//20/01/2009
									{
										artKwdFranch.append(processKeywords(tag));
										//System.out.println("222222222222222222222222 artKwdFranch "+artKwdFranch);
									}
									
								else if(localLang.equalsIgnoreCase("IT"))
								{
									//System.out.println("2 localLang "+localLang);
									artKwd.append(processKeywords(tag));
									//System.out.println("222222222222222222222222 artKwd "+artKwd);

								}
								
								else{
									//System.out.println("33333333333333333333333333333tag "+tag);
									artKwd.append(processKeywords(tag));
									
								}
								
									checkKeyword=true;
									localLang="";

									//System.out.println(" checkKeyword "+checkKeyword);
									//System.in.read();
									if (artAbs.length()==0)
									{
										checkKeyword=false;
									}
									
								}
							}
							else
							{
								//System.out.println("3");
								artKwd.append(processKeywords(tag));
							}
						}
						else
							{
							//System.out.println("4");
							String llang="";
								if(xmlObj.jid.equalsIgnoreCase("MLA"))
								{
									int ind=0;
									if ((ind= tag.indexOf("XML:LANG=\""))>0)
									{
										llang= tag.substring(tag.indexOf("XML:LANG=\"")+10, tag.indexOf("\"", ind+10));
									}
									//System.out.println("1 localLang "+llang);
									if(llang.equals(""))
										llang = XT.articleType;
									//System.out.println("2 localLang "+llang);
									//System.in.read();
									if(llang.equalsIgnoreCase("DE"))//20/01/2009
										{
											artKwdFranch.append(processKeywords(tag));
											//System.out.println("222222222222222222222222 artKwdFranch "+artKwdFranch);
											artKwdFranch_MLA.append(artKwdFranch.toString());//23-06-2011
										}
										else
										{
											artKwd.append(processKeywords(tag));
										}
										//System.out.println("33333333333333333333333333333 artKwd "+artKwd);
								}
								else
								{
									artKwd.append(processKeywords(tag));
								}
								//System.out.println("33333333333333333333333333333 artKwd "+artKwd);
							}
							//System.out.println("33333333333333333333333333333 artKwd "+artKwd);
							//System.out.println("33333333333333333333333333333 artKwdFranch "+artKwdFranch);
							//System.out.println(" artKwd "+artKwd);
							//System.in.read();
					}
					//System.out.println("222222223");
					//end mark
					//commneted by avinandan
					//artKwd.append(processKeywords(tag));
				}
				
				//abhay 01/07/2006
				else if (tag.equalsIgnoreCase("<CE:STEREOCHEM>"))
				{
					stereoData += processStereoChem(tag);
				}//end
				else if (tag.startsWith("<CE:STEREOCHEM "))//28-08-2012 JADTD520 Updation
				{
					stereoData += processStereoChem(tag);
				}//end
				else if (tag.startsWith("<CE:DATE-RECEIVED") || tag.startsWith("<CE:DATE-ACCEPTED") || tag.startsWith("<CE:DATE-REVISED"))
				{
					historyDates+= getHistoryDate(tag)+"\r\n";
					//System.out.println("historyDates---->>"+getHistoryDate(tag));
				}
				else if (tag.equals("<CE:TITLE>")||tag.startsWith("<CE:TITLE "))
				{
					artTitle= xmlObj.extractData("</CE:TITLE>", true);
					//System.out.println("artTitle->"+artTitle);
					String Temp_artTitle=artTitle;
					if(Temp_artTitle.indexOf("ThomsonDiff")!=-1)
					Temp_artTitle=Temp_artTitle.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					//System.out.println("Temp_artTitle->"+Temp_artTitle);
					if(Temp_artTitle.toLowerCase().startsWith("editorial comment")&& xmlObj.pit.equalsIgnoreCase("EDI")&& xmlObj.jid.equalsIgnoreCase("AFJU"))//31-07-2012
					{
						
						AFJU_Editorial_comment=true;
					}
					//System.out.println("AFJU_Editorial_comment=========================>>"+AFJU_Editorial_comment);
					isceTitleFound=true;

					if(artTitle.toUpperCase().startsWith("RETRACTED") || artTitle.toUpperCase().startsWith("DUPLICATE")&&(xmlObj.pit.equalsIgnoreCase("DUP")||xmlObj.pit.equalsIgnoreCase("RET")||xmlObj.pit.equalsIgnoreCase("REM")))
					{
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] This article is tombstone (RETRACTED/DUPLICATE).  Please donot process it.");
							return "";
						}
						else{
						JOptionPane.showMessageDialog(null,"This article is tombstone (RETRACTED/DUPLICATE).  Please donot process it.\nSystem exiting...","ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
						System.exit(0);
						}
					}
					//mukesh 16-09-08 checking title of YICCN
					if((xmlObj.jid.equals("YICCN")) && artTitle.equalsIgnoreCase("Chest X-ray quiz"))
					{yiccnTitle=true;}
					
					//System.out.println("mukesh artTitle"+artTitle+"value   "+yiccnTitle);

					/*if((artFootnote.length()>0) && artFootnote.indexOf("\\titlefootnote")>0)
						artTitle+= "\\(^{{\\openstar}, {\\openstar}{\\openstar}}\\)";					
					else if((artFootnote.length()>0))  
					{
						artTitle+= "\\(^{\\openstar}\\)";						
					}*/
					/*if((chkRefersto))  // 21-12-2004
					{
						artTitle+= "\\(^{\\protect\\specialhyperlink{REFERSTO}{\\openstar}}\\)";
						chkRefersto=false;  // 21-12-2004
					}*/
					eextraTitle=artTitle;
				}
				else if (tag.equals("<CE:SUBTITLE>")||tag.startsWith("<CE:SUBTITLE "))
				{
					subTitle=artTitle;
					//artTitle+= "\\\\"+xmlObj.extractData("</CE:SUBTITLE>", true);//old //02/02/2008
					/**
					* Date : 02/02/2008
					* Added By : Ravi 
					* Change Point : Subtitle will runon if model style will be -MODDFrench
					* Change Request By : TPMS
					*/
					String sTempSubTitle=xmlObj.extractData("</CE:SUBTITLE>", true);//03/09/2008 New line add

					//if((XT.modelStyle.equalsIgnoreCase("-MODDFrench"))||(XT.modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015
					if((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
					//MODD and MODE french removed by mukesh on 18-10-08
					//to make this case true for all masson jid.
					//if(xmlObj.check_MassonJid)
					{
						//artTitle+= ". "+xmlObj.extractData("</CE:SUBTITLE>", true);//03/09/2008 commented
						artTitle+= ". "+sTempSubTitle;
						//System.out.println(" MODDFrench : and MODEFrench"+sTempSubTitle);
					}
					else
					{
						//artTitle+= "\\\\"+xmlObj.extractData("</CE:SUBTITLE>", true);//03/09/2008 commented
						//CHIABU-DIS Condition removed on 05-09-2016
						if(xmlObj.jid.equals("ZEFQ")||(xmlObj.jid.equals("CHIABU") && xmlObj.pit.equalsIgnoreCase("D-I-S")))//30-10-2013
						{
							artTitle+= "}\r\n \\subtitle{"+sTempSubTitle;
						}
						else
						artTitle+= "\\\\"+sTempSubTitle;
						//System.out.println(" NO  MODDFrench : and NO  MODEFrench"+sTempSubTitle);
					}
					/*
					*Added by Mukesh on 03-09-08
					*Requirement: subtitle will appear with title in /STtitle
					*Request by TPMS(Manju)
					*
					*/
					if(artTitle.length()>0 &&subTitle.length()>0)
					{
						subTitle+=". "+sTempSubTitle;
					}
					//end
					//System.out.println("artTitle "+artTitle);
					//System.out.println("subTitle "+subTitle);
					eextraTitle=artTitle;
				}
				else if (tag.equals("<CE:DOCHEAD>")||tag.startsWith("<CE:DOCHEAD "))
				{					
					//artDochead= xmlObj.extractData("</CE:DOCHEAD>", true);//old 28/02/2008

					//*****************************************************
						int countTablePara=0;
						while (!tag.equals("</CE:DOCHEAD>"))
						{
							char ch1= (char)xmlObj.fin.read();
							if (ch1=='<')
							{
								tag= xmlObj.getTag().toUpperCase();
								if (tag.equals("<CE:TEXTFN>")||tag.startsWith("<CE:TEXTFN"))
								{
									++countTablePara;
									if(countTablePara==1)
									{
										artDochead = xmlObj.extractData("</CE:TEXTFN>", true);
										//System.out.println("1 tbCap : "+tbCap);
									}
									else if(countTablePara==2)
									{
										subartDochead= xmlObj.extractData("</CE:TEXTFN>", true);
										//System.out.println("2. tbCap : "+tbCap);
									}
									else if(countTablePara > 2)
									{
										artDochead= xmlObj.extractData("</CE:TEXTFN>", true);
										//System.out.println("3 tbCap : "+tbCap);
									}
									
									//System.in.read();
								}
							}
						}
					//******************************************************
					
					/**
					*Added By Arvind [09/05/2007
					*Change Request By: Vivek;
					* Change Point: In Journal DAD if Dochead contains "News and Views" "CPDD News and Views" then
					* \\usepackage{TP-threecolumn} and \\documentclass[Three, threecolumn
					* will Addec.
					*/
					//System.out.println("artDochead==> "+artDochead);
					if((xmlObj.jid.equals("DAD"))&&(artDochead.equalsIgnoreCase("News and Views")||artDochead.equalsIgnoreCase("CPDD News and Views")))
					{
						dad_check_doc_head=true;
					}
					if((xmlObj.jid.equals("PROTIS"))&&(artDochead.equalsIgnoreCase("Protist News")))
					{
						dad_check_doc_head=true;
					}
					
					//end
				}
				else if (tag.equals("<CE:AUTHOR-GROUP>")||tag.startsWith("<CE:AUTHOR-GROUP "))//28-08-2012 JADTD520 updation tag.startsWith("<CE:AUTHOR-GROUP ")
				{
					absAuthor.append("\r\n\\authors\r\n");
					String procAuthor=processAuthorGroup();
					//authorInfo.append("\r\n"+processAuthorGroup());
					//System.out.println ("procAuthor----------------"+procAuthor);
					authorInfo.append("\r\n"+procAuthor);
					if(count_author_group>0 && xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
					{
						String s=Move_botton_author_Group_Info.toString();
						Move_botton_author_Group_Info=new StringBuffer();
						//System.out.println ("11111111procAuthor----------------"+Move_botton_author_Group_Info);
						 Move_botton_author_Group_Info.append("\\authorgroup{%\r\n"+procAuthor+"}\r\n");//01/03/2008
						 //System.out.println ("11111111procAuthor----------------"+procAuthor);
						 Move_botton_author_Group_Info.append(s);
					}
					else if(count_author_group>0)
					{
						
						Move_botton_author_Group_Info.append("\\authorgroup{%\r\n"+procAuthor+"}\r\n");//01/03/2008
					}
					
					//else
					//	Move_botton_author_Group_Info.append(procAuthor);//01/03/2008
					count_author_group++;
					//System.out.println("authorInfo==> "+authorInfo);
					//System.in.read();
					//abhay 17/07/2006
					if(countAuthor == 1 && xmlObj.jid.equalsIgnoreCase("physt") && (xmlObj.pit.equalsIgnoreCase("cor") || xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("edi")))
					{
						if(authorInfo.indexOf("\\(^{") != -1 && authorInfo.indexOf("}\\)") != -1)
						{
							//System.out.println(authorInfo);
							authorInfo.delete(authorInfo.indexOf("\\(^{"), authorInfo.indexOf("}\\)")+"}\\)".length());
						}
					}
					if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
					{
						if(authorInfo.indexOf("\\(^{") != -1 && authorInfo.indexOf("}\\)") != -1)
						{
							//System.out.println(authorInfo);
							authorInfo.delete(authorInfo.indexOf("\\(^{"), authorInfo.indexOf("}\\)")+"}\\)".length());
						}
					}
				}
				else if (tag.equals("<CE:PRESENTED>"))
				{
					presented = presented +"\r\n\\presentedby{"+xmlObj.extractData("</CE:PRESENTED>", true)+"}";
				}
				else if (tag.equals("<CE:DEDICATION>"))
				{
					presented = presented +"\r\n\\dedicated{"+xmlObj.extractData("</CE:DEDICATION>", true)+"}";
				}
				else if (tag.startsWith("<CE:DEDICATION "))//28-08-2012 JADTD520 Updation
				{
					presented = presented +"\r\n\\dedicated{"+xmlObj.extractData("</CE:DEDICATION>", true)+"}";
				}
				else if (tag.equals("<CE:MISCELLANEOUS>")||tag.startsWith("<CE:MISCELLANEOUS "))//28-08-2012 JADTD520 Updation
				{
					if(xmlObj.jid.equals("JAMM"))//bhavesh
					{
						miscjamm = xmlObj.extractData("</CE:MISCELLANEOUS>", true);
						//System.out.println("===>>"+miscjamm);
					}
					else if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
					{
						Move_botton_author_Group_Info.append("\r\n\\misc{"+xmlObj.extractData("</CE:MISCELLANEOUS>", true)+"}");
						/*if(Move_botton_author_Group_Info.indexOf("\\author",0)!=-1)
						{
							System.out.println("Move_botton_author_Group_Info===>>"+Move_botton_author_Group_Info);
							Move_botton_author_Group_Info=Move_botton_author_Group_Info.insert(Move_botton_author_Group_Info.indexOf("\\author",0),"\r\n\\misc{"+xmlObj.extractData("</CE:MISCELLANEOUS>", true)+"}\r\n");
						}
						else
						{
							Move_botton_author_Group_Info.append("\r\n\\misc{"+xmlObj.extractData("</CE:MISCELLANEOUS>", true)+"}");
						}*/
					}
					else if((xmlObj.jid.equals("OFTAL")||XT.modelStyle.startsWith("7Spanish"))&& xmlObj.pit.equalsIgnoreCase("prv"))
						misc = "\r\n\\misc{"+xmlObj.extractData("</CE:MISCELLANEOUS>", true)+"}";
					else
						misc = "\r\n\\misc{"+xmlObj.extractData("</CE:MISCELLANEOUS>", true)+"}";
				}
			}
		}
		/**
		*[21/11/2007]
		*Added by Ravi
		* Change Point : Remove  \\Frenchcopyright at end of abstract.
		*/
		//if((!XT.modelStyle.equalsIgnoreCase("-MODDFrench"))&&(!XT.modelStyle.equalsIgnoreCase("-MODEFrench"))&&(!XT.modelStyle.startsWith("-MOD6"))&&(!XT.modelStyle.startsWith("6Plus")))//08-04-2014
		if((!XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))&&(!XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))&&(!XT.modelStyle.startsWith("-MOD6"))&&(!XT.modelStyle.startsWith("6Plus")))//6PLUS added by mukesh on 07-11-08
		{
		//System.out.println("-----> "+XT.modelStyle);
		//if((!XT.modelStyle.equalsIgnoreCase("-MODDFrench"))){	
		if((artAbs.lastIndexOf("\\Frenchcopyright\r\n\\end{abstract}")!=-1)&&(artAbs.lastIndexOf("\\Frenchcopyright\r\n\\end{abstractone}")==-1))
		{
			
			int del_frcopy=artAbs.lastIndexOf("\\Frenchcopyright");
			if(del_frcopy>0)
			{
				artAbs.delete(del_frcopy,del_frcopy+"\\Frenchcopyright\r\n".length());
				//System.out.println("--mike---> "+XT.modelStyle);
			}
		
		}
	   }
			//[18/12/2006][Added By Ravi Change By Vivek]
					if(altSubTitle.length()>0)
						{
							//altTitle+="\\\\\n"+altSubTitle+"";//old //02/02/2008
							/**
							* Date : 02/02/2008
							* Added By : Ravi 
							* Change Point: In case of  -MODDFrench model the ce:alt-subtitle should be runon.
							* Change Request By : TPMS
							*/
							//if((XT.modelStyle.equalsIgnoreCase("-MODDFrench"))||(XT.modelStyle.equalsIgnoreCase("-MODEFrench")))//08-04-2015	
							if((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH")))
							//MODD and MODE french removed by mukesh on 18-10-08
							//to make this case true for all masson jid.
							//if(xmlObj.check_MassonJid)
							{
								altTitle+=". "+altSubTitle+"";//old //02/02/2008
								//System.out.println("YESSSSSSSSSSSSSS "+altSubTitle);
							}
							else
							{
								altTitle+="\\\\\n"+altSubTitle+"";//old //02/02/2008
								//System.out.println("NOOOOOOOOOOOOOOO "+altSubTitle);
							}
						}
						else
						{
							altTitle+="";
						}
		//end
		String temp= artHeadInfo+"\r\n";
		String runAuthors= "";
		String STrunAuthors= "";
		String auNamerhauthor="";
		if (auNames.size()>0)
		{
			//System.out.println("xmlObj.jid :"+xmlObj.jid+"   "+"\n\nxmlObj.bibStyle :"+xmlObj.bibStyle);
			//if there is more then 3 authors then : first authtor name + et al.
			if (auNames.size()>=3)
			{
				String auName= (String)auNames.get(0);
				
				//////////////////to be done //abhay 25/09/2006
				String fn1 = "";
				String sn1 = "";
				String fullfirstname = "";
				//System.out.println("auName: "+auName);
				String[] aun1 = auName.split(";");
				if(initials_check==true)
				{
					fn1 = aun1[0];
					//System.out.println("----------------"+fn1);
					
				}
				/*else if(xmlObj.jid.equalsIgnoreCase("IMR"))
					fn1 = aun1[0];*/
				else
				fn1 = runEmailAuthor(aun1[0]);
				
				sn1 = aun1[1];
				fullfirstname=aun1[0];
				//System.out.println("fn1: "+fn1+" sn1 "+sn1+"aun1[0] :"+aun1[0]);
				//System.out.println("Mike: "+xmlObj.bibStyle);
				if(xmlObj.bibStyle.startsWith("1"))
				{
					if(fn1.equals(""))
						STrunAuthors += sn1 +" et al.";
					else
					STrunAuthors += fn1 +" "+ sn1 +" et al.";
				}
				else if(xmlObj.bibStyle.equals("2"))
				{
					if(fn1.equals(""))
						STrunAuthors += sn1 +" et al.";
					else
					STrunAuthors += sn1 +", "+ fn1 +" et al.";
				}
			//	else if(xmlObj.bibStyle.startsWith("3") || xmlObj.bibStyle.equals("4"))//old
				else if(xmlObj.bibStyle.equals("3") ||xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || xmlObj.bibStyle.equals("4"))//Added on[08/12/2006]
				{
					//STrunAuthors += sn1 +" "+ fn1.replaceAll("\\.","") +" et al."; //old [16/11/2006]
					//added by Ravi  [16/11/2006 by Vivek]
					//STrunAuthors += sn1 +" "+ fn1.replaceAll("\\.","") +", et al.";//10/07/2008//old
					if(fn1.equals(""))
						STrunAuthors += sn1 +", et al";
					else
					{
						if(initials_check==true)
						{
							STrunAuthors += sn1 +" "+ fn1 +", et al";
							//initials_check=false;
						}
						else
						STrunAuthors += sn1 +" "+ fn1.replaceAll("\\.","") +", et al";
					}
					
				}
				//added by Ravi  [08/12/2006 by Vivek]
				else if(xmlObj.bibStyle.equals("3a-Jecs"))
				{
					if(fn1.equals(""))
						STrunAuthors += sn1 +" et al.";
					else
					STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",".").trim() +" et al.";
					//STrunAuthors += sn1 +", "+ fn1 +" et al.";
					//System.out.println("STrunAuthors===> "+STrunAuthors);
					//System.in.read();

				}
				//end
			
				else if(((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE"))) && !(xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail"))
					&& !(xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					//System.out.println("Found   :");
					//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +" et al.";//old
					/* [03/01/2007]
					* Added By : Ravi
					* Change request By : Vivek
					* Chage Point : Comma will come before et al.
					*/
					if(xmlObj.bibStyle.startsWith("5"))
					{
						if(fn1.equals(""))
							STrunAuthors += sn1  +", et al";
						else
						STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", et al";
					}
					else
					{
						if(fn1.equals(""))
							STrunAuthors += sn1  +", et al.";
						else
						STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", et al.";
					}
				}
				// Added by mukesh on 09-09-08 for RETAIL
				else if((xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				{
					//System.out.println("Caught 1:"+fullfirstname);
					STrunAuthors += sn1 +", "+ fullfirstname.trim() +", et al";
				}
				//Added by mukesh on 18-09-08 for COCOMP
				else if((xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					//System.out.println("Caught 1:"+fullfirstname);
					STrunAuthors += sn1 +", "+ fullfirstname.replaceAll("\\.",". ").trim() +", et al";
				}
				else
				{
					if(fn1.equals(""))
						STrunAuthors += sn1 +" et al.";
					else
					STrunAuthors += fn1 +" "+ sn1 +" et al.";
				}//mukesh on 07-01-09
				//////////////
				/**
				* Date : [20/08/2007]
				* Modify By : Ravi
				* Change Point : Comma before et al. in stauthor for any ref. style
				* Change Request : Through TPMS [customer feedback mail date 18/08/2007]
				*/

				//System.out.println("STrunAuthors===> "+STrunAuthors);
				//System.in.read();
				if(STrunAuthors.endsWith("et al."))
				{
				
					if(!STrunAuthors.endsWith(", et al."))
					{
						int spos=0;
							spos=STrunAuthors.indexOf(" et al.");
							if(spos!=-1)
							{
								StringBuffer sp=new StringBuffer(STrunAuthors);
								sp=sp.insert(spos,",");
								STrunAuthors=sp.toString();
							}
					}
				}
			  //end
				String tempN ="";
				if(initials_check==true)
				{
					auName=(String)auNamesRhauthor.get(0);
					tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
					auName=auName.substring(auName.indexOf(";")+1);
				}
				else if(xmlObj.jid.equalsIgnoreCase("IMR"))
				{
					tempN =auName.substring(0, auName.indexOf(";"));
					auName=auName.substring(auName.indexOf(";")+1);
				}
				else
				{
					tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
					auName=auName.substring(auName.indexOf(";")+1);
				}
				if(!xmlObj.jid.equalsIgnoreCase("HLC"))
					runAuthors= tempN+" ";
				//System.out.println("runAuthors :"+runAuthors);
				//runAuthors+=auName+" et al.";//old block on 12-05-2010
				if(xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("ARBIES")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE"))
				{
					if(XT.articleType.equalsIgnoreCase("PT")||XT.articleType.equalsIgnoreCase("EN"))//07-07-2011
					{
						if(xmlObj.jid.equals("MEXOFT"))
							runAuthors+=auName+" et al";
						else
							runAuthors+=auName+" et al.";
					}
					else
						runAuthors+=auName+" et al";
					//System.out.println(" XT.articleType---------------------------------"+runAuthors+" "+ XT.articleType);
				}
				else if(xmlObj.jid.equals("IMR"))//29-10-2012 Condition for IMR journal dot is not required at the end of et al .
				{
					runAuthors+=auName+" et al";
				}
				else
				{
					runAuthors+=auName+" et al.";
				}
				
				
				
			}
			else if (auNames.size()==1)
			{
				String auName= (String)auNames.get(0);
				//////////////////to be done //abhay 25/09/2006
				String fn1 = "";
				String sn1 = "";
				String fullfirstname = "";
				//System.out.println("AIP: "+xmlObj.bibStyle);
				String[] aun1 = auName.split(";");
				if(initials_check==true)
				{
					fn1 = aun1[0];
				}
				else
				fn1 = runEmailAuthor(aun1[0]);
				sn1 = aun1[1];
				fullfirstname=aun1[0];
				
				if(xmlObj.bibStyle.startsWith("1"))
				{
					STrunAuthors += fn1 +" "+ sn1;
				}
				else if(xmlObj.bibStyle.equals("2"))
				{
					STrunAuthors += sn1 +", "+ fn1;
					
				}
			//	else if(xmlObj.bibStyle.startsWith("3") || xmlObj.bibStyle.equals("4"))//old
				else if(xmlObj.bibStyle.equals("3") ||xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || xmlObj.bibStyle.equals("4"))//Added on[08/12/2006]
				{
					if(initials_check==true)
					{
						STrunAuthors += sn1 +" "+ fn1.trim();
					}
					else
					{
					if(fn1.endsWith("."))
						{
							fn1=fn1.substring(0,fn1.length()-1);
						}
						fn1=fn1.replaceAll("\\.","");//06/02/2010
					//STrunAuthors += sn1 +" "+ fn1.replaceAll("\\.","");
					STrunAuthors += sn1 +" "+ fn1.trim();
					}
					//System.out.println("STrunAuthors===> "+STrunAuthors);
					//System.in.read();
				}
				//added by Ravi  [08/12/2006 by Vivek]
				else if(xmlObj.bibStyle.equals("3a-Jecs"))
				{
					if(fn1.endsWith("."))
						{
							fn1=fn1.substring(0,fn1.length()-1);
						}
					//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",".").trim();
					STrunAuthors += sn1 +", "+ fn1.trim();
				}
				//end

				//else if((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")) && !(xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				else if(((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE"))) && !(xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail"))
						&& !(xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					if(xmlObj.bibStyle.startsWith("5"))
					{
						//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\."," ").trim();
						//System.out.println("--------------------"+fn1);
						if(fn1.endsWith("."))
						{
							fn1=fn1.substring(0,fn1.length()-1);
						}
						//System.out.println("--------------------"+fn1);
						//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim();
						if(fn1.trim().length()>0)
						STrunAuthors += sn1 +", "+ fn1.trim();
						else
						STrunAuthors += sn1 ;
					}
					else
					{
						if(fn1.endsWith("."))
						{
							fn1=fn1.substring(0,fn1.length()-1);
						}
						//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim();
						if(fn1.trim().length()>0)
						STrunAuthors += sn1 +", "+ fn1.trim();
						else
						STrunAuthors += sn1 ;
					}
					//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim();//old//10/08/2008
				}
				//else if((xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
 				else if((xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				{
					//System.out.println("Caught  2:"+fullfirstname);
					STrunAuthors += sn1 +", "+ fullfirstname.trim();
				}
				//Added by mukesh on 18-09-08 for COCOMP
				else if((xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					//System.out.println("Caught  2:"+fullfirstname);
					if(fullfirstname.endsWith("."))
						{
							fullfirstname=fullfirstname.substring(0,fullfirstname.length()-1);
						}
					//STrunAuthors += sn1 +", "+ fullfirstname.replaceAll("\\.",". ").trim();
					STrunAuthors += sn1 +", "+ fullfirstname.trim();
				}
				else
				{
					STrunAuthors += fn1 +" "+ sn1;
				}
				//////////////
				String tempN="";
				if(initials_check==true)
				{
					auName=(String)auNamesRhauthor.get(0);
					tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
					auName=auName.substring(auName.indexOf(";")+1);	
				}
				else
				{
					tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
					auName=auName.substring(auName.indexOf(";")+1);
				}
				if(!xmlObj.jid.equalsIgnoreCase("HLC"))
					runAuthors= tempN+" ";
				runAuthors+=auName;
				//System.out.println("runAuthors :"+runAuthors);
			}
			else
			{
				String auName= (String)auNames.get(0);
				String auName2 = (String)auNames.get(1);
				
				
				//////////////////to be done //abhay 25/09/2006
				String fn1 = "";
				String sn1 = "";
				String fn2 = "";
				String sn2 = "";
				String fullname1 = "";
				String fullname2 = "";
				//System.out.println("\nAIP1: "+auName);
				//System.out.println("AIP2: "+auName2);
				String[] aun1 = auName.split(";");
				String[] aun2 = auName2.split(";");
				
				if(initials_check==true)
				{
					fn1 = aun1[0];
					sn1 = aun1[1];
					fn2 = aun2[0];
					sn2 = aun2[1];
				}
				else
				{

				fn1 = runEmailAuthor(aun1[0]);
				sn1 = aun1[1];
				fn2 = runEmailAuthor(aun2[0]);
				sn2 = aun2[1];
				}
				fullname1=aun1[0];
				fullname2=aun2[0];
				//System.out.println("111111--------------"+xmlObj.bibStyle);
				if(xmlObj.bibStyle.startsWith("1"))
				{
					STrunAuthors += fn1 +" "+ sn1 +", "+ fn2 +" "+ sn2;
				}
				else if(xmlObj.bibStyle.equals("2"))
				{
					/**
					*Added By : Ravi [23/02/2007]
					*Change Request : Vivek
					* Change Point : Comma not required after last name in case of no first name NED
					*/
					
					if((fn1.length() > 0) && (fn2.length()>0))
					{
						STrunAuthors += sn1 +", "+ fn1 +", "+ sn2 +", "+ fn2;
					}
					else if((fn1.length() == 0) && (fn2.length()>0))
					{
						STrunAuthors += sn1 +", "+ sn2 +", "+ fn2;
						
					}
					else if((fn1.length() > 0) && (fn2.length()==0))
					{
						STrunAuthors += sn1 +", "+ fn1+", "+ sn2 ;
					}
					else if((fn1.length()== 0) && (fn2.length()==0))
					{
						STrunAuthors += sn1 +", "+ sn2 ;
					}
					else
					{
						STrunAuthors += sn1 +", "+ fn1 +", "+ sn2 ;
					}

					
				}
				//else if(xmlObj.bibStyle.startsWith("3") || xmlObj.bibStyle.equals("4"))
				else if(xmlObj.bibStyle.equals("3") ||xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || xmlObj.bibStyle.equals("4"))//Added on[08/12/2006]
				{
					if(initials_check==true)
					{
						STrunAuthors += sn1 +" "+ fn1 +", "+ sn2 +" "+ fn2;
						//System.out.println("2222--------------"+STrunAuthors);
					}
					else
					{
						
						STrunAuthors += sn1 +" "+ fn1.replaceAll("\\.","") +", "+ sn2 +" "+ fn2.replaceAll("\\.","");
						//System.out.println("333--------------"+STrunAuthors);
					}
				}
				//added by Ravi  [08/12/2006 by Vivek]
				else if(xmlObj.bibStyle.equals("3a-Jecs"))
				{
					if(fn2.endsWith("."))
					{
						fn2=fn2.substring(0,fn2.length()-1);
					}
					//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",".").trim() +", "+ sn2 +", "+ fn2.replaceAll("\\.",".").trim();
					STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",".").trim() +", "+ sn2 +", "+ fn2.trim();
				}
				//end
				//else if((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
				//else if((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")) && !(xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				  else if(((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE"))) && !(xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")) && !(xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					/**
					*Added By : Ravi [23/02/2007]
					*Change Request : Vivek
					* Change Point : Comma not required after last name in case of no first name NED
					*/
					if(xmlObj.bibStyle.startsWith("5"))
					{
						if((fn1.length() > 0) && (fn2.length()>0))
						{
							//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fn2.replaceAll("\\.",". ").trim();
							//System.out.println("fn2 : "+fn2);
							if(fn2.endsWith("."))
							{
								fn2=fn2.substring(0,fn2.length()-1);
							}
							//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fn2.replaceAll("\\."," ").trim();
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fn2.trim();
						}
						else if((fn1.length() == 0) && (fn2.length()>0))
						{
							
							//STrunAuthors += sn1 +", "+ sn2 +", \\& "+ fn2.replaceAll("\\."," ").trim();//31/08/2009
							if(fn2.endsWith("."))
							{
								fn2=fn2.substring(0,fn2.length()-1);
							}
							STrunAuthors += sn1 +", "+ sn2 +", \\& "+ fn2.replaceAll("\\."," ").trim();
						}
						else if((fn1.length() > 0) && (fn2.length()==0))
						{
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
						}
						else if((fn1.length()== 0) && (fn2.length()==0))
						{
							STrunAuthors += sn1 +", \\& "+ sn2 ;
						}
						else
						{
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
						}
					}
					else
					{
						if((fn1.length() > 0) && (fn2.length()>0))
						{
							if(fn2.endsWith("."))
							{
								fn2=fn2.substring(0,fn2.length()-1);
							}
							//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", "+ sn2 +", "+ fn2.replaceAll("\\.",". ").trim();
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", "+ sn2 +", "+ fn2.trim();
						}
						else if((fn1.length() == 0) && (fn2.length()>0))
						{
							if(fn2.endsWith("."))
							{
								fn2=fn2.substring(0,fn2.length()-1);
							}
							//STrunAuthors += sn1 +", "+ sn2 +", "+ fn2.replaceAll("\\.",". ").trim();
							STrunAuthors += sn1 +", "+ sn2 +", "+ fn2.trim();
							
						}
						else if((fn1.length() > 0) && (fn2.length()==0))
						{
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", "+ sn2 ;
						}
						else if((fn1.length()== 0) && (fn2.length()==0))
						{
							STrunAuthors += sn1 +", "+ sn2 ;
						}
						else
						{
							STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", "+ sn2 ;
						}
					}
				}
				//else if((xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				  else if((xmlObj.jid.equalsIgnoreCase("RETAIL") && xmlObj.bibStyle.equalsIgnoreCase("5-Retail")))
				{
					
					if((fullname1.length() > 0) && (fullname2.length()>0))
						{
							//STrunAuthors += sn1 +", "+ fn1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fn2.replaceAll("\\.",". ").trim();
							//STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fullname2.replaceAll("\\."," ").trim();
							STrunAuthors += sn1 +", "+ fullname1.trim() +", and "+ sn2 +", "+ fullname2.trim();
						}
						else if((fullname1.length() == 0) && (fullname2.length()>0))
						{
							//STrunAuthors += sn1 +", "+ sn2 +", \\& "+ fn2.replaceAll("\\.",". ").trim();
							//STrunAuthors += sn1 +", "+ sn2 +", \\& "+ fullname2.replaceAll("\\."," ").trim();
							STrunAuthors += sn1 +", "+ sn2 +", and "+ fullname2.trim();
							
						}
						else if((fullname1.length() > 0) && (fullname2.length()==0))
						{
							//STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
							STrunAuthors += sn1 +", "+ fullname1.trim() +", and "+ sn2 ;
						}
						else if((fullname1.length()== 0) && (fullname2.length()==0))
						{
							//STrunAuthors += sn1 +", \\& "+ sn2 ;
							STrunAuthors += sn1 +", and "+ sn2 ;
						}
						else
						{
							//STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
							STrunAuthors += sn1 +", "+ fullname1.trim() +", and "+ sn2 ;
						}
						//System.out.println("hfhhfhfh"+STrunAuthors); mukesh 09-09-08
				}
				// added by mukesh on 18-09-08 for COCOMP
				else if((xmlObj.jid.equalsIgnoreCase("COCOMP") && xmlObj.bibStyle.equalsIgnoreCase("5")))
				{
					
					if((fullname1.length() > 0) && (fullname2.length()>0))
						{
							STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 +", "+ fullname2.trim();
						}
						else if((fullname1.length() == 0) && (fullname2.length()>0))
						{
							STrunAuthors += sn1 +", "+ sn2 +", \\& "+ fullname2.replaceAll("\\.",". ").trim();
						}
						else if((fullname1.length() > 0) && (fullname2.length()==0))
						{
							STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
						}
						else if((fullname1.length()== 0) && (fullname2.length()==0))
						{
							STrunAuthors += sn1 +", \\& "+ sn2 ;
						}
						else
						{
							STrunAuthors += sn1 +", "+ fullname1.replaceAll("\\.",". ").trim() +", \\& "+ sn2 ;
						}
						//System.out.println("hfhhfhfh"+STrunAuthors); mukesh 09-09-08
				}
				else
				{
					STrunAuthors += fn1 +" "+ sn1 +", "+ fn2 +" "+ sn2;
				}
				//////////////
				
				String tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
				auName=auName.substring(auName.indexOf(";")+1);
				if(!xmlObj.jid.equalsIgnoreCase("HLC"))
					runAuthors= tempN+" ";
				
				if(xmlObj.jid.equalsIgnoreCase("HLC")||xmlObj.jid.equalsIgnoreCase("PROTIS"))
				{
				runAuthors+=auName+" and ";
				}
				else
				{
					runAuthors+=auName+", ";
				}
				//System.out.println("initials_check===> "+initials_check);
				//System.in.read();
				if(initials_check==true)
				{
					auName= (String)auNamesRhauthor.get(1);
					//System.out.println("auName===> "+auName);
				}
				else
				auName= (String)auNames.get(1);
				
				tempN =runEmailAuthor(auName.substring(0, auName.indexOf(";")));
				auName=auName.substring(auName.indexOf(";")+1);
				//System.out.println("tempN---->"+tempN);
				//System.out.println("auName---->"+auName);
				if(!xmlObj.jid.equalsIgnoreCase("HLC"))
					runAuthors+= tempN+" "+auName;
				else
					runAuthors+=" "+auName;
				
				
			}
			//System.out.println(" AIP STrunAuthors---->"+STrunAuthors);

			Spanish_eextraAuthor=STrunAuthors;
		}		
		/*
		* Running headlines are the same for left- and right-hand pages and are centred.
		* They consist of author name
		* (initials followed by surname; two authors, separated by a comma; three or more authors,
		* first author followed by ‘et al.’) and the journal title, separated from the authors by ‘ / ’.
		* Example: M. Oluwole Jr., R.P. Mills III / Int. J. Pediatr. Otorhinolaryngol. 54 (2000) 117-123
		* Journal name to be given in full, except where it is too long: then abbreviate as in citations.
		*/

		if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN")) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")&&!xmlObj.jid.equalsIgnoreCase("jalcom")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))||(xmlObj.jid.equalsIgnoreCase("PROTIS")&&HeadGroup_Collab.dad_check_doc_head==true)) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			//JALCOM added by mukesh on 07-01-09&& (xmlObj.pit.equalsIgnoreCase("COR")&&!xmlObj.jid.equalsIgnoreCase("jalcom"))
		{
			//if(artDochead.length() > 0)//old-13-07-2011
			if(artDochead.length() > 0 && (!xmlObj.jid.equalsIgnoreCase("JAMM")&&(xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")||xmlObj.pit.equalsIgnoreCase("EDI")||xmlObj.pit.equalsIgnoreCase("PGL")||xmlObj.pit.equalsIgnoreCase("REQ")||(xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))||xmlObj.pit.equalsIgnoreCase("PRP")||xmlObj.pit.equalsIgnoreCase("COR")||xmlObj.pit.equalsIgnoreCase("CNF"))))
			{
				
				temp+= "\\rhauthor{"+artDochead+"}\r\n";
				//System.out.println("11111111111111111");
				if(XT.stampJid.contains(xmlObj.pit))//abhay 26/07/2006
				{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(STrunAuthors);
								//	System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
					                {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//STrunAuthors=q1.toString();
										}
									}
									STrunAuthors=q1.toString();

								//*************************************************
						temp += "\\STauthor{"+STrunAuthors+"}\r\n";
						//System.out.println("temp 111"+temp);
					
				}
			}
			else if((runAuthors.length()>0 && xmlObj.check_SpanishJid==true)&& (!xmlObj.jid.equalsIgnoreCase("JAMM")&&(xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")||xmlObj.pit.equalsIgnoreCase("EDI")||xmlObj.pit.equalsIgnoreCase("PGL")||xmlObj.pit.equalsIgnoreCase("REQ")||(xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))||xmlObj.pit.equalsIgnoreCase("PRP")||xmlObj.pit.equalsIgnoreCase("COR")||xmlObj.pit.equalsIgnoreCase("CNF"))))
			{
				//System.out.println("22222222222222222222");
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								 StringBuffer q1= new StringBuffer(runAuthors);
									
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//runAuthors=q1.toString();
										}
									}
									runAuthors=q1.toString();
								//*************************************************
						temp+= "\\rhauthor{"+runAuthors+"}\r\n";
					//temp+= "\\rhauthor{"+artTitle+"}\r\n";//old

				if(XT.stampJid.contains(xmlObj.pit))//abhay 26/07/2006
				{
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									 q1= new StringBuffer(STrunAuthors);
									//System.out.println("q1==> "+q1);
									 in= 0;
									 ot=0;
									 while(in != -1)
					                 {
									   in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//STrunAuthors=q1.toString();
										}
									 }
									 STrunAuthors=q1.toString();
			

								//*************************************************
					temp += "\\STauthor{"+STrunAuthors+"}\r\n";
					//System.out.println("temp 222"+temp);
					}
			}


			//else if(artTitle.length()>0)//old-13-07-2011
			else if(artTitle.length()>0 && (!xmlObj.jid.equalsIgnoreCase("JAMM")&&(xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")||xmlObj.pit.equalsIgnoreCase("EDI")||xmlObj.pit.equalsIgnoreCase("PGL")||xmlObj.pit.equalsIgnoreCase("REQ")||(xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))||xmlObj.pit.equalsIgnoreCase("PRP")||xmlObj.pit.equalsIgnoreCase("COR")||xmlObj.pit.equalsIgnoreCase("CNF"))))
			{
				//System.out.println("22222222222222222222");
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								 String art=artTitle;
									StringBuffer q1= new StringBuffer(art);
									//System.out.println("q1==> "+q1);
									int  in= 0;
									 int ot=0;
									while(in != -1)
				                    {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//art=q1.toString();
										}
									}
									art=q1.toString();
							temp+= "\\rhauthor{"+art+"}\r\n";

								//*************************************************
					//temp+= "\\rhauthor{"+artTitle+"}\r\n";//old

				if(XT.stampJid.contains(xmlObj.pit))//abhay 26/07/2006
				{
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									 q1= new StringBuffer(STrunAuthors);
									//System.out.println("q1==> "+q1);
									 in= 0;
									 ot=0;
									 while(in != -1)
					                 {
									   in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//STrunAuthors=q1.toString();
										}
									 }
									 STrunAuthors=q1.toString();
			

								//*************************************************
					temp += "\\STauthor{"+STrunAuthors+"}\r\n";
					//System.out.println("temp 222"+temp);
					}
			}//13-07-2011 : New condition is added for JAMM. Condition: If JID=JAMM and doc sub type is compact then instead of dochead, author will be in there.
			else if(runAuthors.length()>0 && (xmlObj.jid.equalsIgnoreCase("JAMM")&&(xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")||xmlObj.pit.equalsIgnoreCase("EDI")||xmlObj.pit.equalsIgnoreCase("PGL")||xmlObj.pit.equalsIgnoreCase("REQ")||(xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))||xmlObj.pit.equalsIgnoreCase("PRP")||xmlObj.pit.equalsIgnoreCase("COR")||xmlObj.pit.equalsIgnoreCase("CNF"))))
			{
				//System.out.println("333333333333333");
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(runAuthors);
									
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//runAuthors=q1.toString();
										}
									}
									runAuthors=q1.toString();
								//*************************************************
				temp+= "\\rhauthor{"+runAuthors+"}\r\n";

				if(XT.stampJid.contains(xmlObj.pit))//abhay 26/07/2006
				{
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									q1= new StringBuffer(STrunAuthors);
									//System.out.println("q1==> "+q1);
									 in= 0;
									 ot=0;
									 while(in != -1)
					                {
											in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
											
											if(in != -1)
											{
												ot=q1.indexOf("}",in+1)	;
												if(ot != -1)
												{
													q1.delete(in,ot+1);
												}
												//STrunAuthors=q1.toString();
											}
									}
									STrunAuthors=q1.toString();
								//*************************************************
					temp += "\\STauthor{"+STrunAuthors+"}\r\n";
					//System.out.println("\n\n\ntemp 333"+STrunAuthors);
					
				}
			}
		}
		else
		{
			if(runAuthors.length()>0)
			{
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(runAuthors);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//runAuthors=q1.toString();
										}
									}
									runAuthors=q1.toString();
								//*************************************************
				temp+= "\\rhauthor{"+runAuthors+"}\r\n";
				//System.out.println("================================="+runAuthors);
				if(XT.stampJid.contains(xmlObj.pit)||((xmlObj.jid.equalsIgnoreCase("DAD")) && (xmlObj.pit.equalsIgnoreCase("NWS"))))//abhay 26/07/2006
				{
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									q1= new StringBuffer(STrunAuthors);
									//System.out.println("q1==> "+q1);
									 in= 0;
									 ot=0;
									 while(in != -1)
					                {
											in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
											
											if(in != -1)
											{
												ot=q1.indexOf("}",in+1)	;
												if(ot != -1)
												{
													q1.delete(in,ot+1);
												}
												//STrunAuthors=q1.toString();
											}
									}
									STrunAuthors=q1.toString();
								//*************************************************
					temp += "\\STauthor{"+STrunAuthors+"}\r\n";
					//System.out.println("\n\n\ntemp 333"+STrunAuthors);
					
				}
			}
			else
			{
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(artDochead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
				                    {
											in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
											
											if(in != -1)
											{
												ot=q1.indexOf("}",in+1)	;
												if(ot != -1)
												{
													q1.delete(in,ot+1);
												}
												//artDochead=q1.toString();
											}
									}
									artDochead=q1.toString();
			

								//*************************************************
				temp+= "\\rhauthor{"+artDochead+"}\r\n";

				if(XT.stampJid.contains(xmlObj.pit))//abhay 26/07/2006
				{
					//*****************[New Requirement]***************
					/**
					 *Added By : Ravi 
					 *Date : 21/05/2007
					 *Change Point : Deleting Query Tag.
					 **/
						q1= new StringBuffer(STrunAuthors);
						//System.out.println("q1==> "+q1);
						in= 0;
						ot=0;
						while(in != -1)
					    {
							in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
							
							if(in != -1)
							{
								ot=q1.indexOf("}",in+1)	;
								if(ot != -1)
								{
									q1.delete(in,ot+1);
								}
								//STrunAuthors=q1.toString();
							}
						}
						STrunAuthors=q1.toString();
					//*************************************************
					temp += "\\STauthor{"+STrunAuthors+"}\r\n";
					//System.out.println("temp 444"+temp);
					
				}
			}
		}

		if (artTitle.length()>0)
		{
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) || ((xmlObj.pit.equalsIgnoreCase("COR") && !xmlObj.jid.equalsIgnoreCase("JALCOM")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV"))))
			{
				StringBuffer q1= new StringBuffer();
				int in= 0;
				int ot=0;
				if((artTitle.indexOf("\\begin{inlinestripns}") != -1) || (altTitle.indexOf("\\begin{inlinestripns}") != -1))
				{
					if(XT.stampJid.contains(xmlObj.pit) && !(xmlObj.bibStyle.equalsIgnoreCase("1a")))//abhay 26/07/2006
					{
						if(subTitle.length()>0)
						{
								temp+= "\\STtitle{\\protect "+subTitle+"}\r\n";//bhavesh 07/08/06
						}
						else
						{
							temp+= "\\STtitle{\\protect "+artTitle+"}\r\n";//bhavesh 07/08/06	
							
						}
						
					}
					
						
					
					temp +=  "\\miscTitle{\\protect "+artTitle;
					XT.model6rhtitle+="\\protect "+artTitle;
				}
				else
				{

					if(XT.stampJid.contains(xmlObj.pit) && !(xmlObj.bibStyle.equalsIgnoreCase("1a")))//abhay 26/07/2006
					{
						if(subTitle.length()>0)//bhavesh 17/08/06
						{
							//*****************[New Requirement]***************
							/**
							 *Added By : Ravi 
							 *Date : 21/05/2007
							 *Change Point : Deleting Query Tag.
							 **/
							 String art=subTitle;
								q1= new StringBuffer(art);
								//System.out.println("q1==> "+art);
								in= 0;
								ot=0;
								while(in != -1)
							    {
									in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
									
									if(in != -1)
									{
										ot=q1.indexOf("}",in+1)	;
										if(ot != -1)
										{
											q1.delete(in,ot+1);
										}
										//art=q1.toString();
									}
								}
								art=q1.toString();
							temp+= "\\STtitle{"+art+"}\r\n";
							//temp+= "\\STtitle{"+artTitle+"}\r\n";
							//System.out.println("subTitle--------- "+artTitle);
							
							//*************************************************
							//temp+= "\\STtitle{"+subTitle+"}\r\n";//old
							
						}
						else//bhavesh 17/08/06
						{
							//*****************[New Requirement]***************
							/**
							 *Added By : Ravi 
							 *Date : 21/05/2007
							 *Change Point : Deleting Query Tag.
							 **/
							 String art=artTitle;
								q1= new StringBuffer(art);
								//System.out.println("artTitle==> "+art);
								in= 0;
								ot=0;
								while(in != -1)
							   {
									in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
									
									if(in != -1)
									{
										ot=q1.indexOf("}",in+1)	;
										if(ot != -1)
										{
											q1.delete(in,ot+1);
										}
										//art=q1.toString();
									}
							   }
							   art=q1.toString();
								temp+= "\\STtitle{"+art+"}\r\n";	
							//System.out.println("222222222subTitle--------- "+artTitle);
							//*************************************************
						//	temp+= "\\STtitle{"+artTitle+"}\r\n";//old
							
						}
				}
					//Added By Ravi [22/12/2006]
					//System.out.println("Ravi "+xt.modelStyle);
					if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
					{
						
						temp +=  "\\title{"+artTitle;
					}
					else if((xmlObj.pit.equalsIgnoreCase("brv"))&&( xt.modelStyle.startsWith("7Spanish")))//07-09-2010
					{
						temp +=  "\\title{"+artTitle;
					}
					else
					{
						temp +=  "\\miscTitle{"+artTitle;
						
					}
					//System.out.println("temp 1211 --> "+temp);
					//System.in.read();
					/**
					*[18/04/2007]
					*Added By: Arvind
					* Change Point : Titlefootnote symbol {openstar} should be also with miscTitle
					* Change Request By : Vivek
					*/
					
					if(artFootnote.length()>0)
					{
						temp +=  "\\(^{"+TempTitleEntity+"}\\)";
					}
					//end

					XT.model6rhtitle += artTitle;
					
				}
				
			}
			/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
			else if(xmlObj.pit.equalsIgnoreCase("dis") &&xmlObj.jid.equalsIgnoreCase("YBJOM")  &&artDochead.equals("INTERESTING CASE") )
			{
				//temp +=  "\\miscTitle{"+artTitle;
					Mtitle=  "\\miscTitle{"+artTitle;
			}
			//end
			else
			{
				if((artTitle.indexOf("\\begin{inlinestripns}") != -1) || (altTitle.indexOf("\\begin{inlinestripns}") != -1))
				{
					if(XT.stampJid.contains(xmlObj.pit) && !(xmlObj.bibStyle.equalsIgnoreCase("1a")))//abhay 26/07/2006
					{

						if(subTitle.length()>0)
							temp+= "\\STtitle{\\protect "+subTitle+"}\r\n";//bhavesh 17/08/06
						else
							temp+= "\\STtitle{\\protect "+artTitle+"}\r\n";//bhavesh 17/08/06
					}
						
					
					temp+= "\\title{\\protect "+artTitle;
					
					XT.model6rhtitle+="\\protect "+artTitle;
					//System.out.println("artTitle=> "+artTitle);
					//System.in.read();
				}
				else
				{
					
					String art="";
					if((XT.stampJid.contains(xmlObj.pit)||((xmlObj.jid.equalsIgnoreCase("DAD")) && (xmlObj.pit.equalsIgnoreCase("NWS")))) && !(xmlObj.bibStyle.equalsIgnoreCase("1a")))//abhay 26/07/2006
					{
						if(subTitle.length()>0)//bhavesh 17/08/06
							{
								//temp+= "\\STtitle{"+artTitle+"}\r\n";//old
								/* Added By Ravi [27/12/2006]
								    Change request By : Vivek
								    Change Point : No sub title allow with stitle
								*/


								//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								     art=subTitle;
									StringBuffer q1= new StringBuffer(art);
									//System.out.println("q1==> "+q1);
									//System.in.read();
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//art=q1.toString();
										}
									}
									art=q1.toString();
								temp+= "\\STtitle{"+art+"}\r\n";

								//System.out.println("1subTitle--------- "+artTitle);
								//*************************************************


								//temp+= "\\STtitle{"+subTitle+"}\r\n";//old
								
								
								//end
						}
						else//bhavesh 17/08/06
						{
							//System.out.println("artTitle ==> "+artTitle);
							/*Added By Ravi [05/03/2007 ]
							* Change Request By : Vivek 
							* Change Point: dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary"
							*/
							//temp+= "\\STtitle{"+artTitle+"}\r\n";old
							if(xmlObj.jid.equalsIgnoreCase("IMLET") &&artDochead.equals("Editorial Commentary") && xmlObj.pit.equalsIgnoreCase("EDI"))
							{
								temp+= "\\STtitle{"+artDochead+"}\r\n";
								
							}
							else
							{
								//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								     art=artTitle;
									StringBuffer q1= new StringBuffer(art);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//art=q1.toString();
										}
									}
									art=q1.toString();
									if(art.indexOf("ThomsonDiff",0)!=-1){
									art=art.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
									}
									if(xmlObj.jid.equalsIgnoreCase("RPPNEN")||xmlObj.jid.equalsIgnoreCase("PULMOE"))
									{
										
											temp+= "\\STtitle{"+RPPNEN_artTitle+"}\r\n";
									}
									else
									temp+= "\\STtitle{"+art+"}\r\n";
								//System.out.println("Subtitle   "+art);
								//System.out.println("ARTTTTTTTTT  --  "+art);
								//System.out.println("ravi "+art);
								//*************************************************

								//temp+= "\\STtitle{"+artTitle+"}\r\n";
							//	System.out.println("artTitle==> "+artTitle);
							}
						//end
						}
					}

					//*******************************************************************************
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								     art=artTitle;
									StringBuffer q1= new StringBuffer(art);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//art=q1.toString();
										}
									}
									art=q1.toString();
									if(art.indexOf("ThomsonDiff",0)!=-1){
									art=art.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
									}

					//*******************************************************************************
					temp += "\\title{"+artTitle;
					//System.out.println("artTitle=> "+artTitle);
					//System.in.read();
					/*Added By Ravi [21/02/2007 ]
					* Change Request By : Vivek 
					* Change Point: Footnote citation should be convert at the end of Title instead of Alttitle 
					*/
					
					/*if(!TempTitleEntity.equals(""))
					{
						temp+="\\(^{"+TempTitleEntity+"}\\)";
						TempTitleEntity="";
					}*/
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
					//XT.model6rhtitle += artTitle;
					XT.model6rhtitle += art;
					//System.out.println("temp 1211 --> "+artTitle);
					//System.in.read();
					//*************************************************
				}
					/**
					 *Added By : Ravi 
					 *Date : 20/09/2007
					 *Change Point : openster with title 
					 */
				if(!TempTitleEntity.equals(""))
					{
						temp+="\\(^{"+TempTitleEntity+"}\\)";
						TempTitleEntity="";
					}
			}
			//System.out.println("artDochead==> "+artTitle);
		}
		if( !(artTitle.length() > 1) && !(xmlObj.bibStyle.equalsIgnoreCase("1a")) )// abhay 23/09/2006
		{
			
			//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								/*     String art=artTitle;
									StringBuffer q1= new StringBuffer(art);
								//	System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//art=q1.toString();
										}
									}
									art=q1.toString();
								temp+= "\\STtitle{"+art+"}\r\n";
								//*************************************************
								*/
			if(XT.stampJid.contains(xmlObj.pit))
			{
				temp += "\\STtitle{"+ artDochead +"}\r\n";
				//System.out.println("----------------------");;
			}

			
			
		}
		
		if(altTitle.length()>0)
		{
			temp+= "}"+altTitle;//Bhavesh 16/8/06
			
			//XT.model6rhtitle+= "\\\\"+altTitle;
		}
		if (artTitle.length()>0)
		{

			//blocked by Ravi [Due to hard code]
			/*
			///added by rajeev as art footnote is coming in between arttitle and subtitle
			if((artFootnote.length()>0) && artFootnote.indexOf("\\Titlefootnote")>0)
				temp+= "\\(^{{\\openstar}, {\\openstar}{\\openstar}}\\)";					
			else if((artFootnote.length()>0))  
				temp+= "\\(^{\\openstar}\\)";			
			*/
		
			/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
			if(xmlObj.pit.equalsIgnoreCase("dis") &&xmlObj.jid.equalsIgnoreCase("YBJOM")  &&artDochead.equals("INTERESTING CASE") )
			{			
				//temp+= "\r\n";
			}
			else
			{
				//System.out.println("=============>>"+XT.isCMEInOrder);
				if(XT.isCMEInOrder)
					temp+= "\\CMEiconTitle}\r\n";
				else
					temp+= "}\r\n";
			}
			//end
			
			//System.out.println("*************** : "+xmlObj.bkmList.get(xmlObj.bkmCount));
			if(xmlObj.bkmList.size() > 0 && xmlObj.bkmList.size()> xmlObj.bkmCount)
			{
				//System.out.println(xmlObj.bkmList);
				//temp+= "\\addbookmark"+xmlObj.bkmList.get((xmlObj.bkmCount))+"\r\n";//old
				//*******************************************************
				
				//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
								 //System.out.println("xmlObj.bkmList==>>"+xmlObj.bkmList.size());
								 //System.out.println("xmlObj.bkmCount==>>"+xmlObj.bkmCount);
								 String addbookmark=xmlObj.bkmList.get((xmlObj.bkmCount)).toString();
									StringBuffer q1= new StringBuffer(addbookmark);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\protect\\qtoa{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											addbookmark=q1.toString();
										}
									}			
									addbookmark=q1.toString();
									//System.out.println("addbookmark     ::::::"+addbookmark);
									if(addbookmark.indexOf("ThomsonDiff")!=-1){
									addbookmark=addbookmark.replaceAll("ThomsonDiff\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
									}
									//System.out.println("addbookmark : "+addbookmark);
				//*************************************************
				
				temp+= "\\addbookmark"+addbookmark+"\r\n";
				xmlObj.bkmCount++;
			}
			//System.out.println(temp);

		}

		if(presented.length()>0)
		{
			
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+=presented+"\r\n";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+=presented+"\r\n";
			else if(((xmlObj.pit.equalsIgnoreCase("brv")) || (xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			{
				endData+= presented+"\r\n";
				
			}
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData += presented+"\r\n";

			/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
			else if(xmlObj.pit.equalsIgnoreCase("dis") && xmlObj.jid.equalsIgnoreCase("YBJOM") && artDochead.equals("INTERESTING CASE"))
			{
				endData+= presented+"\r\n";
				
			}
			//end
			else
			{
				temp+=presented+"\r\n";
				
				
			}
		}
		if(misc.length()>0)
		{		//System.out.println("presented : "+presented);
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+=misc+"\r\n";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+=misc+"\r\n";
			else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			{
				endData+= misc+"\r\n";
				
			}
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData += misc+"\r\n";
			/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
			else if(xmlObj.pit.equalsIgnoreCase("dis") && xmlObj.jid.equalsIgnoreCase("YBJOM") && artDochead.equals("INTERESTING CASE"))
			{
				endData+= misc+"\r\n";
			}
			//end
			else
				temp+=misc+"\r\n";
				
		}
		//Avinandan commented for book review
		/*if(reference.length()>0)
		{
			temp+="\r\n";
			temp+=reference+"\r\n";
			}*/
		//end mark and added line in end of this function
		if(artDochead.length()>0)
		{			
			//Feedback by CE dated 13-oct-2004
			if((artFootnote.length()>0) && artFootnote.indexOf("\\Titlefootnote")>0 && artTitle.length()==0 && artDochead.equals("Book review"))
						artDochead+= "\\(^{{\\openstar}, {\\openstar}{\\openstar}}\\)";
			else if(artFootnote.length()>0 && artTitle.length()==0 && artDochead.equals("Book review"))
						artDochead+= "\\(^{\\openstar}\\)";	
			//temp+= "\\dochead{"+artDochead+"}\r\n";//old block date 24/01/2008
			/**
			* Date : 24/01/2008
			* Modifiy By : Ravi
			* Change point: dochead move above to title
			* Change Request By : TPMS
			*/
			String move_dochead="";
			if(subartDochead.length()>0)
			{
				move_dochead="\\subdochead{"+subartDochead+"}\r\n";
				move_dochead+="\\dochead{"+artDochead+"}\r\n";
			}
			else
			{
				move_dochead="\\dochead{"+artDochead+"}\r\n";
			}
			StringBuffer dh= new StringBuffer(temp);

			int spos=dh.indexOf("\\title{",0);
			if(spos !=-1)
			{
				dh=dh.insert(spos,move_dochead);
				temp=dh.toString();
			}
			else
			{
				
				if(subartDochead.length()>0)
				{
					temp+= "\\subdochead{"+subartDochead+"}\r\n";
				}
				temp+= "\\dochead{"+artDochead+"}\r\n";
			}
			//end 
			//System.out.println("===> "+temp);
			//System.in.read();
			//Feedback by PAGINATION dated 13-oct-2004
			String ddd=artDochead.toUpperCase();
			if(xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || ddd.startsWith("HOT TOPICS IN DNA REPAIR")))
			{				
				//temp+="\r\n\\MoveAffiliationsAtEndOfArticle \n\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n%\tMove Affiliations and Authors at end of Article\t\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n\n";			
			}
			else if(xmlObj.jid.equals("BC") && artDochead.equalsIgnoreCase("CELL IN FOCUS"))
			{				
				temp+="\r\n\\MoveCellFactsInsideAbstracts \n\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n% Move Cells Facts TextBox at end of Abstracts\t\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n\n";			
			}
		}
		
		if(historyDates.length()>0 || presented.length()>0 || misc.length()>0)
		{
			if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
			{
				//endData += historyDates+"\\history\r\n\r\n";
				
			}
			else if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+= historyDates+"\\history\r\n\r\n";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+= historyDates+"\\history\r\n\r\n";
			else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV"))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			{
				/**
				*Date:[05/03/2007]
				*Added By : Ravi
				* Change Request by: Vivek
				* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
				*/
				//endData+= "<HISTORY>"+historyDates+"</HISTORY>"+"\r\n";//old
				 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
						{
								//temp+= "<HISTORY>"+historyDates+"</HISTORY>"+"\r\n";
								temp+= historyDates+"\\history\r\n\r\n";
						}
						else
						{
							//endData+=  "<HISTORY>"+historyDates+"</HISTORY>"+"\r\n";
							
							endData+=  "<HISTORY>"+historyDates+"\\history\r\n\r\n</HISTORY>"+"\r\n";
						}
						//end
			}
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData += "<HISTORY>"+historyDates+"</HISTORY>"+"\r\n";
			else
			{
				
				temp+= historyDates+"\\history\r\n\r\n";
				//System.out.println("historyDates : "+historyDates);
			}
		}
//System.out.println("authorInfo.length() : "+authorInfo.length());
		if(authorInfo.length()>0)
		{
			
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+= authorInfo+"\r\n";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+= authorInfo+"\r\n";
			else if(xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))
			{
				if(authorInfo.indexOf("\\author") != authorInfo.lastIndexOf("\\author"))
					endData += authorInfo.substring(0,authorInfo.indexOf("}\r\n\\affiliation")) + " are in the" + authorInfo.substring(authorInfo.indexOf("}\r\n\\affiliation")) + "\r\n";
				else
					endData += authorInfo.substring(0,authorInfo.indexOf("}\r\n\\affiliation")) + " is in the" + authorInfo.substring(authorInfo.indexOf("}\r\n\\affiliation")) + "\r\n";

				temp+= authorInfo.substring(0,authorInfo.indexOf("\\affiliation"));
			}
			else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV"))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
				{
					//endData += authorInfo+"\r\n"; //old
					/*    [24/01/2007]
						* Added By  : Ravi
						* Change Request By : Vivek
						* Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						* dochead is Presidential Address in any where in dochead.
						*
					*/
				//	if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address ")!= -1))
					if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
					{
						temp += authorInfo+"\r\n";
					}
					else
					{
						//endData += authorInfo+"\r\n"; //old
						/**
						*Date:[05/03/2007]
						*Added By : Ravi
						* Change Request by: Vivek
						* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
						*/
						 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
						{
								temp+=authorInfo+"\r\n";
						}
						else
						{
							
							//endData+= authorInfo+"\r\n";
							
							endData+=Move_botton_author_Group_Info+"\r\n";
							//System.out.println("authorInfo : "+authorInfo);
							//System.out.println("endData : "+endData);
							//System.out.println("Move_botton_author_Group_Info : "+Move_botton_author_Group_Info);
							//System.in.read();
						}
						//end
					}
					//end
				
				}
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData += authorInfo+"\r\n"; 
			//System.out.println("middleData :"+middleData);
			/*[28/12/2006]
			* Added By  : Ravi
			* Change Request By : Vivek
			* Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
			* (case sensitive and spelling should be same as mentioned) 
			* the article should be treated as compact type (MISC option).
			*/
			else if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
			{
				endData += authorInfo+"\r\n"; 
				
			}
			//end
			else
			{
				
				if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
				{
					endData+=Move_botton_author_Group_Info+"\r\n";
					//System.out.println("---------------------------------------ravi--->>"+Move_botton_author_Group_Info);;
				}
				else
				{
					temp+= authorInfo+"\r\n";
				}
				//System.out.println("---------------------------------------ravi--->>");;
			}

		}
		//if (artAbs.length()>0 || artKwd.length()>0)//old blocked 20/01/2009
		if (artAbs.length()>0 || artKwd.length()>0||artKwdFranch.length()>0)
		{
			temp+=  "\r\n\\startabstract{%\r\n";
		}


		if (artAbs.length()>0)
		{
			
			temp+= artAbs+"\r\n";
		}
		//System.out.println(" rrrrrrrrRavi temp : "+temp);
		//System.out.println(" rrrrrrrrRavi temp : "+artKwd);
		//System.out.println("Ravi artKwd : "+artKwd+"------------------");
		//System.out.println("Ravi artKwdFranch : "+artKwdFranch+"------------------");
		//System.in.read();
		//if(artKwd.length() > 0)//old blocked 20/01/2009
		if(artKwd.length() > 0||artKwdFranch.length()>0)
		{
			
			//artKwdFranch
			if((xmlObj.jid.equalsIgnoreCase("NEUCLI")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)<796)|| xmlObj.jid.equalsIgnoreCase("QUIP")|| xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("OTSR")||xmlObj.jid.equalsIgnoreCase("JOCLIM")||xmlObj.jid.equalsIgnoreCase("SODA")||xmlObj.jid.equalsIgnoreCase("LIVER")||xmlObj.jid.equalsIgnoreCase("DEMAN")||xmlObj.jid.equalsIgnoreCase("STLM")||xmlObj.jid.equalsIgnoreCase("ANNDER")||xmlObj.jid.equalsIgnoreCase("RCOT")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("JVS")||xmlObj.jid.equalsIgnoreCase("JCHIRV")||xmlObj.jid.equalsIgnoreCase("JCHIR")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("FEMME")||xmlObj.jid.equalsIgnoreCase("CND")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("EURTEL")||xmlObj.jid.equalsIgnoreCase("REVHOM")||xmlObj.jid.equalsIgnoreCase("JCCO")||xmlObj.jid.equalsIgnoreCase("JEUREA")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("JDMV")||xmlObj.jid.equalsIgnoreCase("BANM")|| xmlObj.jid.equalsIgnoreCase("REAURG")||xmlObj.jid.equalsIgnoreCase("GCB")||(xmlObj.jid.equalsIgnoreCase("CLINRE"))||(xmlObj.jid.equalsIgnoreCase("CLIREX"))||(xmlObj.jid.equalsIgnoreCase("NEUADO")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("VETCLI")||xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("SEMREU")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX") || xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("AFJU")||(xmlObj.jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))||xmlObj.jid.equalsIgnoreCase("AFORL")||xmlObj.jid.equalsIgnoreCase("ANORL")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("JBCT")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD") ||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("MLONG")||xmlObj.jid.equalsIgnoreCase("LONGEV")||xmlObj.jid.equalsIgnoreCase("RMR")||xmlObj.jid.equalsIgnoreCase("PEDPUE")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("FANDER")||xmlObj.jid.equalsIgnoreCase("ACVDSP")||xmlObj.jid.equalsIgnoreCase("THERAP")||xmlObj.jid.equalsIgnoreCase("JEMEP")||xmlObj.jid.equalsIgnoreCase("TOXAC")||xmlObj.jid.equalsIgnoreCase("ONCOHP")||xmlObj.jid.equalsIgnoreCase("JRADIO")||xmlObj.jid.equalsIgnoreCase("JRDIA")||xmlObj.jid.equalsIgnoreCase("DIII")||(xmlObj.jid.equalsIgnoreCase("TRACLI")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("MSOM")||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid)))))
			{
				String Tempkey="";
				/*if(XT.articleType.equals("FR"))
					Tempkey=artKwdFranch.toString();
				else*/
				   Tempkey=artKwd.toString();
				
				if(Tempkey.startsWith("}"))
				{
					Tempkey=Tempkey.substring(1,Tempkey.length());

				}
				//System.out.println("Tempkey : "+Tempkey);
				//System.in.read();
				temp+= Tempkey+"}";
			}
			else
			{
			//System.out.println("Tempkey : "+artKwdFranch);
			
			//temp+= "\r\n\\keywords{%"+artKwd+"}";//old blocked 20/01/2009
			
			if(checkKeyword==true ){
				StringBuffer s=new StringBuffer(temp);
				checkKeyword=false;
					if(artKwd.length() > 0)
					{
						int i=s.indexOf("\\ENGcopyright\r\n%\\end{abstract}",0);
						//System.out.println("1 Ravi temp : "+i+" uuuu"+s);
						if(i!=-1)
						{
							s=s.insert(i+"\\ENGcopyright\r\n".length(),"\r\n\\keywords{%"+artKwd+"}\r\n");
							//System.out.println("1 Ravi temp : ");
						}
						else
						{
							if(artKwdFranch.length() > 0)
							{

								i=s.indexOf("\\FRcopyright\r\n%\\end{abstract}",0);
								//System.out.println("12 Ravi temp : "+i);
								if(i!=-1)
								{
									s=s.insert(i+"\\FRcopyright\r\n".length(),"\\keywords{%"+artKwdFranch+"}\r\n");
									
								}
								else
								{
									if(artKwdFranch.length() > 0&& artKwd.length() > 0)//19/02/2009
									{
										i=s.indexOf("\\FRcopyright\r\n\\end{abstract}",0);
										if(i!=-1 && (s.indexOf("\\ENGcopyright\r\n\\end{abstract}",0)==-1))
										{
											s=s.insert(i+"\\FRcopyright\r\n".length(),"\r\n\\keywords{%"+artKwdFranch+"}\r\n");
											s=s.insert(s.indexOf("\\end{abstract}",0)+"\\end{abstract}\r\n".length(),"\\keywords{%"+artKwd+"}\r\n");
											//System.out.println("1 Ravi temp : "+s);
										}
										else
										{
											i=s.indexOf("\\ENGcopyright\r\n\\end{abstract}",0);
											if(i!=-1)
											{
												s=s.insert(i+"\\ENGcopyright\r\n".length(),"\r\n\\keywords{%"+artKwd+"}\r\n");
												s=s.insert(s.indexOf("\\end{abstract}",0)+"\\end{abstract}\r\n".length(),"\r\n\\keywords{%"+artKwdFranch+"}\r\n");
												//System.out.println("111 Ravi temp : "+s);
											}
										}
									}
								}
							}
							else
							{
								i=s.indexOf("\\ENGcopyright\r\n\\end{abstract}",0);
								//System.out.println("13 Ravi temp : ");
								if(i!=-1)
								{
									s=s.insert(i+"\\ENGcopyright\r\n".length(),"\r\n\\keywords{%"+artKwd+"}");
									//System.out.println("13 Ravi temp : ");
								}
							}
						}
					}
					
					if(artKwdFranch.length() > 0)
					{
						int i=s.indexOf("\\FRcopyright\r\n\\end{abstract}",0);
						if(i!=-1)
						{
							s=s.insert(i+"\\FRcopyright\r\n\\end{abstract}".length(),"\r\n\\keywords{%"+artKwdFranch+"}");
							//System.out.println("23 Ravi temp : ");
						}
						else
						{
						
							if(artKwd.length() > 0)
							{
								 i=s.indexOf("\\ENGcopyright\r\n\\end{abstract}",0);
								if(i!=-1)
								{
									s=s.insert(i+"\\ENGcopyright\r\n\\end{abstract}".length(),"\r\n\\keywords{%"+artKwd+"}");
									//System.out.println("222 Ravi temp : ");
								}
							}
							else
							{
									 i=s.indexOf("\\FRcopyright\r\n%\\end{abstract}",0);
									if(i!=-1)
									{
										s=s.insert(i+"\\FRcopyright\r\n".length(),"\\keywords{%"+artKwdFranch+"}\r\n");
										//System.out.println("2 Ravi temp : ");
									}
							}
						}
					}
					

					temp=s.toString();
			 }
			 else if(checkKeyword==false)
				{
				 //artKwdFranch
					 //System.out.println("Ravi temp : "+artKwd);
					 //System.out.println("1 Ravi temp : "+artKwdFranch);
					 if(XT.articleType.equalsIgnoreCase("EN"))
					 {
						if(artKwd.length()>0)
							temp+= "\r\n\\keywords{%"+artKwd+"}";
						if(artKwdFranch.length()>0)
							temp+= "\r\n\\keywords{%"+artKwdFranch+"}";
					 }
					 else if(XT.articleType.equalsIgnoreCase("FR"))
					 {
						if(artKwdFranch.length()>0)
							temp+= "\r\n\\keywords{%"+artKwdFranch+"}";
						if(artKwd.length()>0)
							temp+= "\r\n\\keywords{%"+artKwd+"}";
						
					 }
					 else if(XT.articleType.equalsIgnoreCase("DE"))
					 {
						if(artKwdFranch.length()>0)
							temp+= "\r\n\\keywords{%"+artKwdFranch+"}";
						if(artKwd.length()>0)
							temp+= "\r\n\\keywords{%"+artKwd+"}";
						
					 }
					 else if(XT.articleType.equalsIgnoreCase("IT"))
					 {
						// System.out.println("\n\nartKwdFranch:"+artKwdFranch+":mmmmmmmmmmmmmmmmmm\n\n");
						 //System.out.println("\n\nartKwd:"+artKwd+":hhhhhhhhhhhhhhh\n\n");
						if(artKwdFranch.length()>0)
						 {
							temp+= "\r\n\\keywords{%"+artKwdFranch+"}";
						
							//temp+=artKwdFranch;
						}
						if(artKwd.length()>0)
						 {
							temp+= "\r\n\\keywords{%"+artKwd+"}";
							
							//temp+=artKwd;
						}
						
					 }
					 else
					{
						 if(artKwd.length()>0)
							temp+= "\r\n\\keywords{%"+artKwd+"}";
						if(artKwdFranch.length()>0)
							temp+= "\r\n\\keywords{%"+artKwdFranch+"}";
						
					}

				}
			 else
				{
				 temp+= "\r\n\\keywords{%"+artKwd+"}";
				//System.out.println("\n\nartKwdFranch:"+temp);
				}
				 //System.out.println("Ravi temp : "+temp);
				
			}
			//System.out.println("artKwd "+artKwd+" length "+artKwd.length());
			//System.in.read();
		
			//temp+= "\r\n\\keywords{%"+artKwd+"}";
		}
		if(artAbs.length()>0  || artKwd.length()>0 || artKwdFranch.length()>0)
		{	//System.out.println("11111artKwd "+artKwd+" length "+artKwd.length());		
			temp+= "}";
		}
		
		temp+= "\r\n\\makechaptertitle";
		
		if(isAbsFound==false)
		{
			int start_position=0;
			int last_position=0;
			String label="";
			StringBuffer buffer=new StringBuffer();
			//System.out.println("temp::"+temp);
			if(temp.indexOf("\\miscTitle{",0)!=-1)
			{
				start_position=temp.indexOf("\\miscTitle{",0);
				if(start_position!=-1)
				{
					CommanMethod cm=new CommanMethod();
					last_position=cm.GetBraceMatch(temp,"\\miscTitle{", start_position);
					if(last_position>0)
					{
						last_position=last_position+start_position;
						label=temp.substring(start_position,last_position);
						buffer=new StringBuffer(temp);
						buffer=buffer.delete(start_position,last_position);
						temp=buffer.toString();
						temp+="\r\n"+label;
					}
					//System.out.println("start_position::"+start_position);
					//System.out.println("last_position::"+(last_position+start_position));
					//System.out.println("label::"+label);
				}
			}
			start_position=0;
			last_position=0;
			label="";
			if(temp.indexOf("\\miscTitle{",0)!=-1)
			{
				if(temp.indexOf("\\Alttitle{",0)!=-1)
				{
					start_position=temp.indexOf("\\Alttitle{",0);
					if(start_position!=-1)
					{
						CommanMethod cm=new CommanMethod();
						last_position=cm.GetBraceMatch(temp,"\\Alttitle{", start_position);
						if(last_position>0)
						{
							last_position=last_position+start_position;
							label=temp.substring(start_position,last_position);
							buffer=new StringBuffer(temp);
							buffer=buffer.delete(start_position,last_position);
							temp=buffer.toString();
							temp+="\r\n"+label;
						}
						//System.out.println("start_position::"+start_position);
						//System.out.println("last_position::"+(last_position+start_position));
						//System.out.println("label::"+label);
					}
				}
			}
		}
		/*[09/06/2008]
		 * Added By  : Ravi
		 * Change Request By : TPMS [Vivek]
		 * Change Point :Please use \\absfontsize after \\makechatertitle in case of all Masson Journals in PIT ABS
		*/
		if(xmlObj.pit.equalsIgnoreCase("abs")&& xmlObj.check_MassonJid==true)
		{
			temp+= "\r\n\\absfontsize";
		//System.out.println("temp "+temp);
		//System.in.read();
		}
		if(xmlObj.pit.equalsIgnoreCase("lit")&& xmlObj.check_MassonJid==true)//added by mukesh on 01-11-08 request by JAGDISH
		{
			//System.out.println("YYYYYYYYYYYYYYY  "+XT.fmcforall);	
			//if((!XT.modelStyle.equalsIgnoreCase("-MODEFrench")) && (!(XT.modelStyle.equalsIgnoreCase("-MODDFrench"))) && !(XT.fmcforall))//08-04-2015
			if((!XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH")) && (!(XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))) && !(XT.fmcforall))
			{
				//System.out.println("iffffffYYYYYYYYYYYYYYY  "+XT.modelStyle);	
				temp+= "\r\n\\LITFONT";	
			}
			else
			{
				//System.out.println("elseeeeeeeeeYYYYYYYYYYYYYYY  "+XT.modelStyle);	
			}
		//System.out.println("temp "+temp);
		//System.in.read();
		}
		/*[28/12/2006]
		 * Added By  : Ravi
		 * Change Request By : Vivek
		 * Change Point : In case of jid YBJOM, docsubtype="dis" and dochead is INTERESTING CASE in all caps
		 * (case sensitive and spelling should be same as mentioned) 
		 * the article should be treated as compact type (MISC option).
		*/
		 if(xmlObj.pit.equalsIgnoreCase("dis") &&xmlObj.jid.equalsIgnoreCase("YBJOM")  &&artDochead.equals("INTERESTING CASE") )
			{
				 temp += "\n"+Mtitle+"}";
			}
		//end
		
		if(xmlObj.jid.equalsIgnoreCase("HLC"))
		{
			temp += "\r\n\\Check_BigLetPar\r\n";
		}
		if(xmlObj.pit.equalsIgnoreCase("d-i-s") &&xmlObj.jid.equalsIgnoreCase("CHIABU"))//CHIABU-DIS Condition removed on 05-09-2016
		{
			temp += "\r\n\\Check_BigLetPar\r\n";
		}

		if((xmlObj.pit.equalsIgnoreCase("REV") && !XT.antiTocJid.contains(xmlObj.jid))||XT.TOCrequired==true)
		{
			//System.out.println("check_MassonJid : "+xmlObj.check_MassonJid);
			if((xmlObj.check_MassonJid==true)&&(xmlObj.jid.equalsIgnoreCase("DDES")||xmlObj.jid.equalsIgnoreCase("SMED")))
			{
				temp+="\r\n\\tableOfContents\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n%\tTable Of Content\t\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n";
				//xmlObj.check_MassonJid=false;
			}
			else if(xmlObj.check_GulliverJid==true)
			{
				temp+="\r\n\\tableOfContents\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n%\tTable Of Content\t\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n";
				//xmlObj.check_MassonJid=false;
			}
			//
			else if(xmlObj.check_MassonJid==false)
			{
				temp+="\r\n\\tableOfContents\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n%\tTable Of Content\t\n%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%\n";
				//xmlObj.check_MassonJid=false;
			}
		}
		if((xmlObj.abrKwd.length()>0) && (!xmlObj.jid.equals("YDLD")))
			temp+=xmlObj.abrKwd;

		//ABHAY 01/07/2006
		if(stereoData.length() > 0)
		{
			processStereoFile(artTitle);
		}


		if(!XT.checkDuckling)
			temp+="\r\n\\begin{Ducknote}{b}";
		if(artFootnote.length()>0)
		{
			if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid))
				DucklingTitleFootnote = "\r\n\r\n\\Titlefootnote"+artFootnote+"}\r\n";
			else
				temp+= "\r\n\r\n\\Titlefootnote"+artFootnote+"}\r\n";
		}
			
		if(authorCorrespondences.length ()>0)
		{
			
			if (!(xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))) // 30-11-2004
			{
				
				if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
				{
					
					endData += authorCorrespondences+"\r\n"; 
					
				}
				else if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
					temp+="\r\n"+authorCorrespondences;
				else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
					temp+="\r\n"+authorCorrespondences;
				else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
				{
					
					/**
						if((xmlObj.pit.equalsIgnoreCase("CNF")) && (xmlObj.jid.equalsIgnoreCase("JVC")))//20/03/2008
							{System.out.println("----------------------------------------");
								temp+="\r\n"+authorCorrespondences;}
							else
					
					*/
					
					//	endData+="\r\n"+authorCorrespondences;//old
					/*    [24/01/2007]
						* Added By  : Ravi
						* Change Request By : Vivek
						* Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						* dochead is Presidential Address in any where in dochead.
						*
					*/
					//if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
					if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
					{
						temp+="\r\n"+authorCorrespondences;
					}
					else
					{
						//endData+="\r\n"+authorCorrespondences;//old
						/**
						*Date:[05/03/2007]
						*Added By : Ravi
						* Change Request by: Vivek
						* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
						*/
						 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
						{
								temp+=authorCorrespondences+"\r\n";
						}
						else
						{
							
							    endData+= authorCorrespondences+"\r\n";
						}
						//end
					}
					//end
				}
				else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
					middleData += "\r\n"+authorCorrespondences; 
					//Added By Ravi 01/11/2006
					//-Mod7IChemE
				/*else if(xmlObj.jid.equals("PROTIS"))
				{
					temp+="\r\n"+authorCorrespondences+"\r\n"+TempCorAbb;
				System.out.println("TempCorAbb  1443 --- > "+TempCorAbb);
					System.in.read();
				}*/
				else if(((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO"))) && ead.length ()==0)
				{
					temp+="\r\n"+authorCorrespondences+"\r\n"+TempCorAbb;
					
					//System.out.println("TempCorAbb  1443 --- > "+TempCorAbb);
					//System.in.read();
				}
				//end
				else
				{
					//-Mod7IChemE
				    if(((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO"))) && ead.length ()>0)
					{
						//temp+="\r\n"+authorCorrespondences+"\r\n"+TempCorAbb;
						temp+="\r\n"+authorCorrespondences;
					//System.out.println("temp  1443 --- > "+temp);
					//System.in.read();
					}
					else
					{
						temp+="\r\n"+authorCorrespondences;
					}
					//System.out.println("TempCorAbb  1443 --- > "+TempCorAbb);
					//System.in.read();
				}
					
					
					
			}
		}
		//in case of author correspondences and email id not given in xml
		//[21/06/2007] Ravi
		else if(authorCorrespondences.length ()<= 0 && ead.length ()<=0)
		{//-Mod7IChemE
			if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO")))
			{
				temp+="\r\n"+TempCorAbb;

			}
		}
		
		if(ead.length ()>0)
		{
			//System.out.println("....email{"+ead);	
			if(countAuthor==1)
			{
				ead=ead.substring(0, ead.indexOf(" ("));
			}
			if(ead.endsWith("\\DnDName{"))
			{
				ead=ead.substring(0,ead.indexOf(" \\DnDName{",0));
			}
			//System.out.println("....email{"+ead);
			//System.out.println("....emailCount "+emailCount);
			if(emailCount>1)
			{
				if(!(xmlObj.jid.equals("AMEEVA")|| (xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))//30-11-04 
				{
					//System.out.println("ead-"+ead);
					if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
					{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
				
					endData+= "\r\n\\emails{"+ead+"}";
					}
					else if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
					{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
				
							endData+= "\r\n\\emails{"+ead+"}";
					}
					else if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
					{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
						temp+= "\r\n\\emails{"+ead+"}";
					}
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
					{//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						             {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									 }
									 ead=q1.toString();

				//*************************************************
						temp+= "\r\n\\emails{"+ead+"}";
					}
					else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
						{
							//endData+="\r\n\\emails{"+ead+"}";//old
							/* [24/01/2007]
							 * Added By  : Ravi
							 * Change Request By : Vivek
						     * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						     * dochead is Presidential Address in any where in dochead.
						     *
					         */
							//	if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
								if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
								{
									//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();

				//*************************************************
									temp+="\r\n\\emails{"+ead+"}";
								}
								else
								{
									//endData+="\r\n\\emails{"+ead+"}";//old
									/**
									*Date:[05/03/2007]
									*Added By : Ravi
									* Change Request by: Vivek
									* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
									*/
									 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
									{
										 //*****************[New Requirement]***************
											/**
											 *Added By : Ravi 
											 *Date : 21/05/2007
											 *Change Point : Deleting Query Tag.
											 **/
												StringBuffer q1= new StringBuffer(ead);
												//System.out.println("q1==> "+q1);
												int in= 0;
												int ot=0;
												while(in != -1)
										        {
													in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
													
													if(in != -1)
													{
														ot=q1.indexOf("}",in+1)	;
														if(ot != -1)
														{
															q1.delete(in,ot+1);
														}
														//ead=q1.toString();
													}
												}
												ead=q1.toString();

										//*************************************************
											temp+="\r\n\\emails{"+ead+"}";
									}
									else
									{
										//*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
											//System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											while(in != -1)
										   {
												in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
												
												if(in != -1)
												{
													ot=q1.indexOf("}",in+1)	;
													if(ot != -1)
													{
														q1.delete(in,ot+1);
													}
													//ead=q1.toString();
												}
										   }
										   ead=q1.toString();
					

										//*************************************************
										endData+= "\r\n\\emails{"+ead+"}";
									}
									//end
								}
								//end
						}
						
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
					{//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

							//*************************************************
						middleData+="\r\n\\emails{"+ead+"}";
					}
					else
					{	
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
						if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid)&& isDucklingCorrAuthor)
							DucklingCorrAuthor+= "\r\n\\emails{"+ead+"}";
						else if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid)&& isDucklingCorrAuthor==false)
							temp+= "\r\n\\emails{"+ead+"}";
						else
						temp+= "\r\n\\emails{"+ead+"}";
						//System.out.println("============="+DucklingCorrAuthor);
					}
				}

			}
			else
			{
				
				if(xmlObj.jid.equals("COCOMP"))
				{
					//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
					                {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
					temp+= "\r\n\\semail{"+ead+"}";
				}
				else if(!(xmlObj.jid.equals("AMEEVA")|| (xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))//30-11-04
				{
					//System.out.println("ead-"+ead);
					
					if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
					{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

								//*************************************************
						temp+= "\r\n\\email{"+ead+"}";
					}
					if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR") )|| (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC")&&dad_check_doc_head==false)
					{
						//endData+="\r\n\\email{"+ead+"}";//old
							/* [24/01/2007]
							 * Added By  : Ravi
							 * Change Request By : Vivek
						     * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						     * dochead is Presidential Address in any where in dochead.
						     *
					         */
							//	if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
								if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
								{
									//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
									{
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

								//*************************************************
									temp+="\r\n\\email{"+ead+"}";
								}
								else
								{
									//endData+="\r\n\\email{"+ead+"}";//old
									/**
									*Date:[05/03/2007]
									*Added By : Ravi
									* Change Request by: Vivek
									* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
									*/
									 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
									{
										 //*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
											//System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											while(in != -1)
										   {
												in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
												
												if(in != -1)
												{
													ot=q1.indexOf("}",in+1)	;
													if(ot != -1)
													{
														q1.delete(in,ot+1);
													}
													//ead=q1.toString();
												}
										     }
											 ead=q1.toString();
					

										//*************************************************
											temp+="\r\n\\email{"+ead+"}";
									}
									else
									{
										 //*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
											//System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											while(in != -1)
										    {
												in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
												
												if(in != -1)
												{
													ot=q1.indexOf("}",in+1)	;
													if(ot != -1)
													{
														q1.delete(in,ot+1);
													}
													//ead=q1.toString();
												}
											}
											ead=q1.toString();
					

										//*************************************************
										
										endData+="\r\n\\email{"+ead+"}";
									}
									//end
								}
								//end
					}	
					else if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
					{
						//*****************[New Requirement]***************
						/**
						 *Added By : Ravi 
						 *Date : 21/05/2007
						 *Change Point : Deleting Query Tag.
						 **/
							StringBuffer q1= new StringBuffer(ead);
							//System.out.println("q1==> "+q1);
							int in= 0;
							int ot=0;
							while(in != -1)
							{
								in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
								
								if(in != -1)
								{
									ot=q1.indexOf("}",in+1)	;
									if(ot != -1)
									{
										q1.delete(in,ot+1);
									}
									//ead=q1.toString();
								}
							}
							ead=q1.toString();
	

						//*************************************************
						
						
						
						if(endData.indexOf("\\authorfootnote",0)!=-1)
						{
							//Move_botton_author_Group_Info=Move_botton_author_Group_Info.insert(Move_botton_author_Group_Info.indexOf("\\authorfootnote",0),"\r\n\\email{"+ead+"}");
							//endData="";
							StringBuffer s=new StringBuffer(endData);
							s=s.insert(s.indexOf("\\authorfootnote",0),"\\email{"+ead+"}\r\n");
							endData=s.toString();
							//System.out.println("Move_botton_author_Group_Info===>>"+endData);
						}
						else
						{
							endData+="\r\n\\email{"+ead+"}";
						}
						
						//System.out.println("22Move_botton_author_Group_Info===>>"+Move_botton_author_Group_Info);
					 }
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
					{
							 //*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
											//System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											while(in != -1)
						                    {
												in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
												
												if(in != -1)
												{
													ot=q1.indexOf("}",in+1)	;
													if(ot != -1)
													{
														q1.delete(in,ot+1);
													}
													//ead=q1.toString();
												}
											}
											ead=q1.toString();
					

										//*************************************************
						middleData+="\r\n\\email{"+ead+"}";
					}
					//ead.length ()==0
					//else if((XT.modelStyle.equals("7") )&& !ead.equals("") )//added by Ravi 01/11/2006
					//-Mod7IChemE
					else if(((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO")) )&& ead.length ()!=0 )//added by Ravi 01/11/2006
					{ 
						//*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
											//System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0);
											
											if(in != -1)
											{
												ot=q1.indexOf("}",in+1)	;
												if(ot != -1)
												{
													q1.delete(in,ot+1);
												}
												ead=q1.toString();
											}
					

										//*************************************************
						//temp+= "\r\n\\email{"+ead+"}"+"\r\n"+TempCorAbb;
						temp+= "\r\n\\email{"+ead+"}"+"\r\n";
						
					}
					//else if((XT.modelStyle.equals("7") )&& ead.equals("") )//added by Ravi 01/11/2006
					//	temp+= "\r\n"+TempCorAbb;
						//end
					else
					{
						if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
						{
						//*****************[New Requirement]***************
								/**
								 *Added By : Ravi 
								 *Date : 21/05/2007
								 *Change Point : Deleting Query Tag.
								 **/
									StringBuffer q1= new StringBuffer(ead);
									//System.out.println("q1==> "+q1);
									int in= 0;
									int ot=0;
									while(in != -1)
						            {
										in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",in);
										
										if(in != -1)
										{
											ot=q1.indexOf("}",in+1)	;
											if(ot != -1)
											{
												q1.delete(in,ot+1);
											}
											//ead=q1.toString();
										}
									}
									ead=q1.toString();
			

				//*************************************************
						endData+= "\r\n\\email{"+ead+"}";
					}
					else
						{
						//*****************[New Requirement]***************
										/**
										 *Added By : Ravi 
										 *Date : 21/05/2007
										 *Change Point : Deleting Query Tag.
										 **/
											StringBuffer q1= new StringBuffer(ead);
										//	System.out.println("q1==> "+q1);
											int in= 0;
											int ot=0;
											in= q1.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0);
											
											if(in != -1)
											{
												ot=q1.indexOf("}",in+1)	;
												if(ot != -1)
												{
													q1.delete(in,ot+1);
												}
												ead=q1.toString();
											}
					

										//*************************************************
						if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid)&& isDucklingCorrAuthor)
							DucklingCorrAuthor+= "\r\n\\email{"+ead+"}";
						else if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid)&& isDucklingCorrAuthor==false)
							temp+= "\r\n\\email{"+ead+"}";
						else
						temp+= "\r\n\\email{"+ead+"}";
						//System.out.println("Rrrrrrrrrrrrrrrrrrr"+temp);
					}
					
					//System.out.println("....email{"+ead);	
					//System.in.read();
					}
				}
				
			}
			//in case of author correspondences and email id both are given in xml
		//[21/06/2007] Ravi
			 if(authorCorrespondences.length ()>0 && ead.length ()>0)
				{
				 //-Mod7IChemE
					if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO")))
					{
						temp+="\r\n"+TempCorAbb;
					}
				}
			else if(authorCorrespondences.length ()<=0 && ead.length ()>0)
				{
					if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO")))
					{
						temp+="\r\n"+TempCorAbb;
					}
				}
		}
		
	/*	if(url.length ()>0)
		{
			//Added by avinandan on 9-9-04
			if(countAuthor==1 && url.indexOf(" (")!=-1)
			{
				url=url.substring(0, url.indexOf(" ("));
			}
			//end mark
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+= "\r\n\\eurl{"+url+"}";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+= "\r\n\\eurl{"+url+"}";
			else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR") || xmlObj.pit.equalsIgnoreCase("CNF")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
				endData+=   "\r\n\\eurl{"+url+"}";
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData+=   "\r\n\\eurl{"+url+"}";
			else
			{
				temp+= "\r\n\\eurl{"+url+"}";
			}
		}
*/
// Added By Ravi [23/11/2006 by Vivek]

if(url.length ()>0)
		{
			//System.out.println("....email{"+ead);	
			if(countAuthor==1)
			{
				url=url.substring(0, url.indexOf(" ("));
			}
			//System.out.println("....email{"+ead);
			if(UrlCount>1)
			{
				if(!(xmlObj.jid.equals("AMEEVA")|| (xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))//30-11-04 
				{
					if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
						temp+= "\r\n\\eurls{"+url+"}";
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
						temp+= "\r\n\\eurls{"+url+"}";
					else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
					{
						//endData+="\r\n\\eurls{"+url+"}";//old
							/* [24/01/2007]
							 * Added By  : Ravi
							 * Change Request By : Vivek
						     * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						     * dochead is Presidential Address in any where in dochead.
						     *
					         */
							//	if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
								if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
								{
									
									endData+="\r\n\\eurls{"+url+"}";
								}
								else
								{
									//endData+="\r\n\\eurls{"+url+"}";//old
									/**
									*Date:[05/03/2007]
									*Added By : Ravi
									* Change Request by: Vivek
									* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
									*/
									 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
									{
											temp+="\r\n\\eurls{"+url+"}";
									}
									else
									{
										
											endData+="\r\n\\eurls{"+url+"}";
									}
									//end
								}
								//end
					}
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
						middleData+="\r\n\\eurls{"+url+"}";
					else
					{	
						temp+= "\r\n\\eurls{"+url+"}";
						
					}
				}
			}
			else
			{
				
				if(xmlObj.jid.equals("COCOMP"))
				{
					temp+= "\r\n\\seurl{"+url+"}";
				}
				else if(!(xmlObj.jid.equals("AMEEVA")|| (xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))//30-11-04
				{
					if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
						temp+= "\r\n\\eurl{"+url+"}";
					if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
						{
							//endData+="\r\n\\eurl{"+url+"}";//old
							/* [24/01/2007]
							 * Added By  : Ravi
							 * Change Request By : Vivek
						     * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						     * dochead is Presidential Address in any where in dochead.
						     *
					         */
								//if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
									if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
								{
									temp+="\r\n\\eurl{"+url+"}";
								}
								else
								{
									//endData+="\r\n\\eurl{"+url+"}";//old
									/**
									*Date:[05/03/2007]
									*Added By : Ravi
									* Change Request by: Vivek
									* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
									*/
									 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
									{
											temp+="\r\n\\eurl{"+url+"}";
									}
									else
									{
										endData+="\r\n\\eurl{"+url+"}";
									}
									//end
								}
								//end
								
						}
						//-Mod7IChemE
					else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
						middleData+="\r\n\\eurl{"+url+"}";
					else if(((XT.modelStyle.equals("7") )||(XT.modelStyle.equals("-Mod7IChemE") )||(XT.modelStyle.equals("-PIO")))&& !url.equals("") )//added by Ravi 01/11/2006
						temp+= "\r\n\\eurl{"+url+"}"+"\r\n"+TempCorAbb;
					//else if((XT.modelStyle.equals("7") )&& ead.equals("") )//added by Ravi 01/11/2006
					//	temp+= "\r\n"+TempCorAbb;
						//end
					else
						temp+= "\r\n\\eurl{"+url+"}";

				//	System.out.println("....email{"+ead);	
				}
				
			}
		}
if(historyDates.length()>0 || presented.length()>0 || misc.length()>0)
		{
			if((xmlObj.pit.equalsIgnoreCase("DIS")) && (xmlObj.jid.equalsIgnoreCase("YBJOM") )&& (artDochead.equals("INTERESTING CASE") ))
			{
				endData +="\r\n"+ historyDates+"\\history\r\n\r\n";
				
			}
		}
if(historyDates.length()>0 || presented.length()>0 || misc.length()>0)
		{
			if(XT.modelStyle.equals("-Mod7IChemE") )//13/05/2008
			{
				temp +="\\History\r\n";
				//System.out.println("Kutte ....................."+temp);
			}
		}
		//Avinandan added authorFootnotes if block
		if(authorFootnotes.length()>0)
		{
			if((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV")) && xmlObj.jid.equalsIgnoreCase("COCOMP"))
				temp+= "\r\n"+authorFootnotes;
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("JALCOM"))
				temp+= "\r\n"+authorFootnotes;
			else if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			{
				//endData+=   "\r\n"+authorFootnotes;//old
							/* [24/01/2007]
							 * Added By  : Ravi
							 * Change Request By : Vivek
						     * Change Point : In case of jid SOCSCI, docsubtype="EDI" and
						     * dochead is Presidential Address in any where in dochead.
						     *
					         */
							//if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& (artDochead.indexOf("Presidential Address")!= -1))
							if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("SOCSCI"))&& ((artDochead.indexOf("Presidential Address ")!= -1) || artDochead.endsWith("Presidential Address")))
							{
								temp+=   "\r\n"+authorFootnotes;	
							}
							else
							{
								//endData+=   "\r\n"+authorFootnotes;//old
								/**
									*Date:[05/03/2007]
									*Added By : Ravi
									* Change Request by: Vivek
									* Change Point : dochead in \\rhauthor and \\STtitle. if jid=IMLET 	dochead="Editorial Commentary" 
									*/
									 if((xmlObj.pit.equalsIgnoreCase("EDI"))  && (xmlObj.jid.equalsIgnoreCase("IMLET"))&& (artDochead.equals("Editorial Commentary")))
									{
											temp+="\r\n"+authorFootnotes;
									}
									else
									{
										endData+="\r\n"+authorFootnotes;
									}
									//end
							}
							//end
			}
			else if(xmlObj.pit.equalsIgnoreCase("COR") && xmlObj.jid.equalsIgnoreCase("HLC"))
				middleData+=   "\r\n"+authorFootnotes;
			else
				temp+= "\r\n"+authorFootnotes;
		}
		if(!XT.checkDuckling)
			temp+="\r\n\\end{Ducknote}";
		if(reference.length()>0)  ////EXTRANET
		{
			reference=reference.trim();
			
			temp+="\r\n";
			
			if(xmlObj.jid.equals("PHYST") || xmlObj.jid.equals("DRUPOL") ||xmlObj.jid.equals("SOCSCI") || xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
			{
				/**
				*Date : 29/03/2008
				* Added By : Ravi
				* Change Point: noindent not required if jid is PHYST
				* Change Request By : TPAMS
				*/
				if(xmlObj.jid.equals("PHYST"))
				{
					if(reference.startsWith("{\\bf{"))
					{
						temp+="\r\n\\miscTitle{"+reference+"\r\n";
					}
					else
					{
						temp+="\r\n\\miscTitle{"+reference+"\r\n";
					}
					//System.out.println("reference--------------> "+reference);
					//temp+="\r\n\\miscTitle{{\\bf{"+reference+"\r\n";
				}
				else if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))//22/0/2008
				{
					if(reference.startsWith("{\\bf{"))
					temp+="\r\n\\BRVhead{%\r\n\\miscTitle"+reference+"}\r\n";
					else
						temp+="\r\n\\BRVhead{%\r\n\\miscTitle{\\bf{"+reference+"}\r\n";
					//System.out.println(reference);
				}
				else
				{temp+="\r\n\\miscTitle{\\bf{"+reference+"\r\n";}
				//temp+="\r\n\\miscTitle{\\bf{"+reference+"\r\n";//old //29/03/2008
				
			}			//29/03/2008
			else if(xmlObj.jid.equals("EURR"))
			{
				//System.out.println("EURR reference-->" + reference);
				temp+="\r\n\\miscTitle{\\bfseries{\\itshape "+reference+"}}\r\n";			
			}
			else if(xmlObj.jid.equals("COCOMP"))
			{
				if(temp.indexOf("\\makechaptertitle")!=-1)
					temp=temp.substring(0,temp.indexOf("\\makechaptertitle"))+"\\title{"+reference+"}\r\n"+temp.substring(temp.indexOf("\\makechaptertitle"));
				else
					temp+="\\title{"+reference+"}\r\n";
			}
			else
			{
				//commented by Ravi 19/10/2006 [ Due to misctitle by Manju]
				/*//change in case of JID LINEDU--title should be moved with reference of bok review.
				if(xmlObj.jid.equals("LINEDU") && temp.indexOf("\\miscTitle{")!=-1)
				{
					temp=temp.substring(0,temp.indexOf("\\miscTitle{"))+temp.substring(temp.indexOf(artTitle)+artTitle.length()+1);
					temp+="\r\n\\miscTitle{"+artTitle+"}";

				}
				*/
				temp+="\r\n\\miscTitle{"+reference+"}\r\n";
				//System.out.println("temp 1211 --> "+temp);
					//System.in.read();
				
			}

		if(isAbsFound==false)
		{
			int start_position=0;
			int last_position=0;
			String label="";
			StringBuffer buffer=new StringBuffer();
			if(temp.indexOf("\\miscTitle{",0)!=-1)
			{
				if(temp.indexOf("\\Alttitle{",0)!=-1)
				{
					start_position=temp.indexOf("\\Alttitle{",0);
					if(start_position!=-1)
					{
						CommanMethod cm=new CommanMethod();
						last_position=cm.GetBraceMatch(temp,"\\Alttitle{", start_position);
						if(last_position>0)
						{
							last_position=last_position+start_position;
							label=temp.substring(start_position,last_position);
							buffer=new StringBuffer(temp);
							buffer=buffer.delete(start_position,last_position);
							temp=buffer.toString();
							temp+="\r\n"+label;
						}
					}
				}
			}
		}


			//temp+="\\vspace*{12pt}\r\n";

			/*if(!(artTitle.length() > 0))// abhay 04/08/2006
			{
				if(XT.stampJid.contains(xmlObj.pit))
					temp += "\\STtitle{"+ bib.ebTitle +"}\r\n";
			}*/
		}

		xmlObj.specialLink= false;

		//System.out.println("\r\n:"+endData+":");
		//System.out.println("\r\n:"+temp+":");
		//System.in.read();
		
		return temp;
	}

	public String getNextTag()throws java.io.IOException
	{
		long filePointer= fin.getFilePointer();
		fin.read();
		String tag= xmlObj.getTag().toUpperCase();
		fin.seek(filePointer);
		return tag;
	}

	public String processKeywords(String tag)throws java.io.IOException
	{
		int ind= 0;
		StringBuffer keywords= new StringBuffer();
		String kwdClass= "";
		kwdLang= "";
		if ((ind= tag.indexOf("CLASS=\""))>0)
		{
			kwdClass= tag.substring(tag.indexOf("CLASS=\"")+7, tag.indexOf("\"", ind+7));
		}
		if ((ind= tag.indexOf("XML:LANG=\"")) > 0)
			kwdLang = tag.substring(tag.indexOf("XML:LANG=\"")+10, tag.indexOf("\"", ind+10));
		else
			kwdLang = XT.articleType;

		String kwdTitle   = "";
		String absSecTitl = "";
		int kwdCount      = 0;
		boolean firstKwd  = true;

		//keywords.append("\r\n\\keywords{%");
		//if(xmlObj.jid.equalsIgnoreCase("NEUCLI") && (kwdLang.equals("EN") && XT.articleType.equals("FR")) || (kwdLang.equals("FR") && XT.articleType.equals("EN")))
		// added by Ravi [ 06/11/2006]
		//if((xmlObj.jid.equalsIgnoreCase("NEUCLI")|| xmlObj.jid.equalsIgnoreCase("ENCEP")|| xmlObj.jid.equalsIgnoreCase("SEXOL")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")|| xmlObj.jid.equalsIgnoreCase("REAURG") || xmlObj.jid.equalsIgnoreCase("JGYN")) && ((kwdLang.equals("EN") && XT.articleType.equals("FR")) || (kwdLang.equals("FR") && XT.articleType.equals("EN"))))
		if((xmlObj.jid.equalsIgnoreCase("NEUCLI")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)<796)|| xmlObj.jid.equalsIgnoreCase("SEXOL")|| xmlObj.jid.equalsIgnoreCase("QUIP")||xmlObj.jid.equalsIgnoreCase("OTSR")||xmlObj.jid.equalsIgnoreCase("JOCLIM")||xmlObj.jid.equalsIgnoreCase("SODA")||xmlObj.jid.equalsIgnoreCase("LIVER")||xmlObj.jid.equalsIgnoreCase("DEMAN")||xmlObj.jid.equalsIgnoreCase("STLM")||xmlObj.jid.equalsIgnoreCase("ANNDER")||xmlObj.jid.equalsIgnoreCase("RCOT")||xmlObj.jid.equalsIgnoreCase("RCO")||xmlObj.jid.equalsIgnoreCase("JVS")||xmlObj.jid.equalsIgnoreCase("JCHIRV")||xmlObj.jid.equalsIgnoreCase("JCHIR")||xmlObj.jid.equalsIgnoreCase("ANNPAT")||xmlObj.jid.equalsIgnoreCase("FEMME")||xmlObj.jid.equalsIgnoreCase("CND")||xmlObj.jid.equalsIgnoreCase("MEDPAL")||xmlObj.jid.equalsIgnoreCase("EURTEL")||xmlObj.jid.equalsIgnoreCase("REVHOM")||xmlObj.jid.equalsIgnoreCase("JCCO")||xmlObj.jid.equalsIgnoreCase("JEUREA")||xmlObj.jid.equalsIgnoreCase("NEURAD")||xmlObj.jid.equalsIgnoreCase("IMMBIO")||xmlObj.jid.equalsIgnoreCase("MORPHO")||xmlObj.jid.equalsIgnoreCase("JMV")||xmlObj.jid.equalsIgnoreCase("JDMV")||xmlObj.jid.equalsIgnoreCase("BANM")|| xmlObj.jid.equalsIgnoreCase("REAURG") ||xmlObj.jid.equalsIgnoreCase("GCB")||(xmlObj.jid.equalsIgnoreCase("CLINRE"))||(xmlObj.jid.equalsIgnoreCase("CLIREX"))||(xmlObj.jid.equalsIgnoreCase("NEUADO")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("PRATAN")||xmlObj.jid.equalsIgnoreCase("ANICOM")||xmlObj.jid.equalsIgnoreCase("VETCLI")||xmlObj.jid.equalsIgnoreCase("NRL")||xmlObj.jid.equals("NRLENG")||xmlObj.jid.equalsIgnoreCase("APRIM")||xmlObj.jid.equalsIgnoreCase("EJPSY")||xmlObj.jid.equalsIgnoreCase("REPOD")||xmlObj.jid.equalsIgnoreCase("RPTOB")||xmlObj.jid.equalsIgnoreCase("RAA")||xmlObj.jid.equalsIgnoreCase("RCHIC")||xmlObj.jid.equalsIgnoreCase("RCHIRA")||xmlObj.jid.equalsIgnoreCase("RPRH")||xmlObj.jid.equalsIgnoreCase("RCHOT")||xmlObj.jid.equalsIgnoreCase("RIEM")||xmlObj.jid.equalsIgnoreCase("CC")||xmlObj.jid.equalsIgnoreCase("EDUMED")||xmlObj.jid.equalsIgnoreCase("CIRGEN")||xmlObj.jid.equalsIgnoreCase("MAGIS")||xmlObj.jid.equalsIgnoreCase("EJFB")||xmlObj.jid.equalsIgnoreCase("EJEPS")||xmlObj.jid.equalsIgnoreCase("ANPSIC")||xmlObj.jid.equalsIgnoreCase("MINCOM")||xmlObj.jid.equalsIgnoreCase("RCHIPE")||xmlObj.jid.equalsIgnoreCase("RCCOT")||xmlObj.jid.equalsIgnoreCase("UROCO")||xmlObj.jid.equalsIgnoreCase("SD")||xmlObj.jid.equalsIgnoreCase("SDCAT")||xmlObj.jid.equalsIgnoreCase("SDENG")||xmlObj.jid.equalsIgnoreCase("CIRCIR")||xmlObj.jid.equalsIgnoreCase("CIRCEN")||xmlObj.jid.equalsIgnoreCase("EII")||xmlObj.jid.equalsIgnoreCase("RIPS")||xmlObj.jid.equalsIgnoreCase("ACCI")||xmlObj.jid.equalsIgnoreCase("MEI")||xmlObj.jid.equalsIgnoreCase("MEDRE")||xmlObj.jid.equalsIgnoreCase("REU")||xmlObj.jid.equalsIgnoreCase("UROMX")||xmlObj.jid.equalsIgnoreCase("ANCV")||xmlObj.jid.equalsIgnoreCase("ACUP")||xmlObj.jid.equalsIgnoreCase("HGMX")||xmlObj.jid.equalsIgnoreCase("BMHIMX")||xmlObj.jid.equalsIgnoreCase("RARD")||xmlObj.jid.equalsIgnoreCase("RCCAR")||xmlObj.jid.equalsIgnoreCase("PIRO")||xmlObj.jid.equalsIgnoreCase("REIMKE")||xmlObj.jid.equalsIgnoreCase("SJME")||xmlObj.jid.equalsIgnoreCase("EQ")||xmlObj.jid.equalsIgnoreCase("RLP")||xmlObj.jid.equalsIgnoreCase("RMTA")||xmlObj.jid.equalsIgnoreCase("BJORL")||xmlObj.jid.equalsIgnoreCase("BJORLP")||xmlObj.jid.equalsIgnoreCase("ENDMAG")||xmlObj.jid.equalsIgnoreCase("RCCAN")||xmlObj.jid.equalsIgnoreCase("IJCHP")||xmlObj.jid.equalsIgnoreCase("MEXOFT")||xmlObj.jid.equalsIgnoreCase("RAM")||xmlObj.jid.equalsIgnoreCase("BJAN")||xmlObj.jid.equalsIgnoreCase("BJANE")||xmlObj.jid.equalsIgnoreCase("BJANES")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equalsIgnoreCase("SEDENE")||xmlObj.jid.equalsIgnoreCase("SEDENG")||xmlObj.jid.equalsIgnoreCase("RGMX")||xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("CESJEF")||xmlObj.jid.equals("ANGIO")||xmlObj.jid.equals("RIFK")||xmlObj.jid.equals("REDAR")||xmlObj.jid.equals("REDARE")||xmlObj.jid.equals("REMLE")||xmlObj.jid.equalsIgnoreCase("DIALIS")||xmlObj.jid.equals("RPSM")||xmlObj.jid.equals("AVDIAB")||xmlObj.jid.equals("MEDIPA")||xmlObj.jid.equals("IMADI")||xmlObj.jid.equals("ANDROL")||xmlObj.jid.equals("ACMX")||xmlObj.jid.equals("INFECT")||xmlObj.jid.equals("REML")||xmlObj.jid.equals("PATOL")||xmlObj.jid.equals("FT")||xmlObj.jid.equals("RECOT")||xmlObj.jid.equals("SEMERG")||xmlObj.jid.equals("CALI")||xmlObj.jid.equals("JHQR")||xmlObj.jid.equals("ENDONU")||xmlObj.jid.equals("ENDINU")||xmlObj.jid.equals("SENOL")||xmlObj.jid.equals("REUMA")||xmlObj.jid.equals("REUMAE")||xmlObj.jid.equals("PSIQ")||xmlObj.jid.equals("REMN")||xmlObj.jid.equals("REMNIM")||xmlObj.jid.equals("REMNGL")||xmlObj.jid.equals("REMNIE")||xmlObj.jid.equals("REGG")||xmlObj.jid.equals("PBJ")||xmlObj.jid.equals("RIBA")||xmlObj.jid.equals("APPR")||xmlObj.jid.equals("MEDCLI")||xmlObj.jid.equals("MEDCLE")||xmlObj.jid.equals("APJ")||xmlObj.jid.equals("PSE")||xmlObj.jid.equals("CLYSA")||xmlObj.jid.equals("RPTO")||xmlObj.jid.equals("GEMREV")||xmlObj.jid.equals("ESPE")||xmlObj.jid.equals("AULA")||xmlObj.jid.equals("EJPAL")||xmlObj.jid.equals("BJP")||xmlObj.jid.equals("RBE")||xmlObj.jid.equals("JEFAS")||xmlObj.jid.equals("ESTGER")||xmlObj.jid.equals("RAMD")||xmlObj.jid.equals("RCSAR")||xmlObj.jid.equals("PSI")||xmlObj.jid.equals("ANYES")||xmlObj.jid.equals("JIK")||xmlObj.jid.equals("CIRCV")||xmlObj.jid.equals("RPEDM")||xmlObj.jid.equals("CEDE")||xmlObj.jid.equals("BRQ")||xmlObj.jid.equals("RIMNI")||xmlObj.jid.equals("SRFE")||xmlObj.jid.equals("IHE")||xmlObj.jid.equals("REDEE")||xmlObj.jid.equals("REDEEN")||xmlObj.jid.equals("IEDEE")||xmlObj.jid.equals("IEDEEN")||xmlObj.jid.equals("MCP")||xmlObj.jid.equals("PRPS")||xmlObj.jid.equals("RIAM")||xmlObj.jid.equals("EIMC")||xmlObj.jid.equals("PSICOD")||xmlObj.jid.equals("EIMCE")||xmlObj.jid.equals("PSICOE")||xmlObj.jid.equals("GACETA")||xmlObj.jid.equals("ARBRES")||xmlObj.jid.equals("OPRESP")||xmlObj.jid.equals("ARBR")||xmlObj.jid.equalsIgnoreCase("ABD")||xmlObj.jid.equalsIgnoreCase("ABDP")||xmlObj.jid.equalsIgnoreCase("JPED")||xmlObj.jid.equalsIgnoreCase("RPPED")||xmlObj.jid.equalsIgnoreCase("AD")||xmlObj.jid.equalsIgnoreCase("JPEDP")||xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("ACURO")||xmlObj.jid.equalsIgnoreCase("ACUROE")||xmlObj.jid.equalsIgnoreCase("RICMA")||xmlObj.jid.equalsIgnoreCase("RPPEDE")||xmlObj.jid.equals("ANPEDI")||xmlObj.jid.equals("ANPEDE")||xmlObj.jid.equals("RCE")||xmlObj.jid.equals("RCENG")||xmlObj.jid.equalsIgnoreCase("FARMA")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equalsIgnoreCase("REPCE")||xmlObj.jid.equalsIgnoreCase("RXENG")||xmlObj.jid.equalsIgnoreCase("RECOTE")||xmlObj.jid.equalsIgnoreCase("RPSMEN")||xmlObj.jid.equalsIgnoreCase("ENDOEN")||xmlObj.jid.equalsIgnoreCase("ENDIEN")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("MEDINE")||xmlObj.jid.equalsIgnoreCase("BMHIME")||xmlObj.jid.equalsIgnoreCase("RGMXEN")||xmlObj.jid.equalsIgnoreCase("RX")||xmlObj.jid.equalsIgnoreCase("RCE")||xmlObj.jid.equalsIgnoreCase("TEKHNE")||xmlObj.jid.equalsIgnoreCase("ALLER")||xmlObj.jid.equalsIgnoreCase("GAMO")||xmlObj.jid.equalsIgnoreCase("RMU")||xmlObj.jid.equalsIgnoreCase("OPTOM")||xmlObj.jid.equalsIgnoreCase("BJPT")||xmlObj.jid.equals("OTORRI")||xmlObj.jid.equals("GASTRO")||xmlObj.jid.equals("GASTRE")||xmlObj.jid.equalsIgnoreCase("GINE")||xmlObj.jid.equals("APUNTS")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPPNEN")||xmlObj.jid.equals("PULMOE")||xmlObj.jid.equals("LABCLI")||xmlObj.jid.equals("HIPERT")||xmlObj.jid.equals("ARTERI")||xmlObj.jid.equals("ARTERE")||xmlObj.jid.equals("RH")||xmlObj.jid.equals("ENFI")||xmlObj.jid.equals("ENFIE")||xmlObj.jid.equals("ENFCLI")||xmlObj.jid.equals("ENFCLE")||xmlObj.jid.equalsIgnoreCase("MEDIN")||xmlObj.jid.equalsIgnoreCase("ENDOMX")|| xmlObj.jid.equalsIgnoreCase("JGYN")||xmlObj.jid.equalsIgnoreCase("AFJU")||(xmlObj.jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))||xmlObj.jid.equalsIgnoreCase("AFORL")||xmlObj.jid.equalsIgnoreCase("ANORL")||xmlObj.jid.equalsIgnoreCase("JTCC")||xmlObj.jid.equalsIgnoreCase("JBCT")||xmlObj.jid.equalsIgnoreCase("ETIQE")||xmlObj.jid.equalsIgnoreCase("PUROL")||xmlObj.jid.equalsIgnoreCase("NPG")||xmlObj.jid.equalsIgnoreCase("PHARMA")||xmlObj.jid.equalsIgnoreCase("SAGF")||xmlObj.jid.equalsIgnoreCase("PNEUMO")||xmlObj.jid.equalsIgnoreCase("DOULER")||xmlObj.jid.equalsIgnoreCase("ACVD") ||xmlObj.jid.equalsIgnoreCase("JFO")||xmlObj.jid.equalsIgnoreCase("MLONG")||xmlObj.jid.equalsIgnoreCase("LONGEV")||xmlObj.jid.equalsIgnoreCase("RMR")||xmlObj.jid.equalsIgnoreCase("PEDPUE")||xmlObj.jid.equalsIgnoreCase("ANNDEROLD")||xmlObj.jid.equalsIgnoreCase("FANDER")||xmlObj.jid.equalsIgnoreCase("ACVDSP")||xmlObj.jid.equalsIgnoreCase("THERAP")||xmlObj.jid.equalsIgnoreCase("JEMEP")||xmlObj.jid.equalsIgnoreCase("TOXAC")||xmlObj.jid.equalsIgnoreCase("ONCOHP")||xmlObj.jid.equalsIgnoreCase("JRADIO")||xmlObj.jid.equalsIgnoreCase("JRDIA")||xmlObj.jid.equalsIgnoreCase("DIII")||(xmlObj.jid.equalsIgnoreCase("TRACLI")&&(XT.fmcforall==true))||xmlObj.jid.equalsIgnoreCase("MSOM")||(xmlObj.jid.equalsIgnoreCase("JTS")&&(XT.fmcforall==true))||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())<=Integer.parseInt(xmlObj.aid)))))
		{
			//07/11/2007
			String val=GetVal(count_keywords);
			
				String val1=GetVal(count_JAL_keywords);
			//System.out.println("Val "+val);
			//keywords.append("}\r\n\\keywordsone{%");
			if(kwdClass.equalsIgnoreCase("KEYWORD"))
			{
				if(xmlObj.jid.equalsIgnoreCase("MLA")&&XT.modelStyle.equalsIgnoreCase("6PlusGerman"))
					keywords.append("\r\n\\keywords{%");
				//else if(xmlObj.jid.equalsIgnoreCase("ENDEND"))//28-08-2012 JADTD520 Updation
				//	keywords.append("\r\n\\keywords{%");
				else
					keywords.append("}\r\n\\keywords"+val+"{%");
			}
			else if(kwdClass.equalsIgnoreCase("JEL"))
			keywords.append("}\r\n\\JELKey"+val1+"{%");
			//System.out.println("@@@@@@@@@@keywords---------------- "+count_keywords);
		}
		while (!tag.equals("</CE:KEYWORDS>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					kwdTitle= xmlObj.extractData("</CE:SECTION-TITLE>", true);
					//if(keywords.length()>0)
					//keywords.append("\r\n");
					//added by avinandan
					if(kwdClass.equals("ABR"))
					{
						if(!xmlObj.jid.equals("YDLD"))
						{
							if(xmlObj.jid.equals("PROTIS"))
							{
								keywords.append("{\\bf{"+kwdTitle+":~}}\r\n");
							}
							else
							keywords.append("{\\it{"+kwdTitle+":~}}\r\n");
						}
					}
					else if(kwdClass.equalsIgnoreCase("INCHIKEY"))//22-01-2014
					{
						//Keywords class="inchikey" not to be printed on PDF
						//System.out.println("INCHIKEY FOUND");
						//System.in.read();
					}
					else
					{	
						if(kwdLang.equalsIgnoreCase("FR"))
							keywords.append("\r\n\\FKWDtitle{"+kwdTitle+" }\r\n");
						else if(kwdLang.equalsIgnoreCase("IT"))
							keywords.append("\r\n\\ITKWDtitle{"+kwdTitle+" }\r\n");
						else
							keywords.append("\r\n\\KWDtitle{"+kwdTitle+"}\r\n");
					}
					//end mark
					//commanted by avinandan
					//keywords.append("\\KWDtitle{"+kwdTitle+"}\r\n");
					kwdCount=0;
					
				}
				else if (tag.equals("<CE:KEYWORD>")||tag.startsWith("<CE:KEYWORD "))//28-08-2012 JADTD520 Updation
				{
					kwdCount++;
					if (kwdCount==1 )
					{
						if(firstKwd == false)
						{
							if(kwdLang.equals("FR"))
								keywords.append("\\fsep ");
							else
								keywords.append("\\sep ");
						}
					}
					if(kwdCount>1)
						keywords.append(", ");
					firstKwd= false;
				}
				else if (tag.equals("</CE:KEYWORD>"))
				{
					kwdCount--;
				}
				else if (tag.equals("</CE:KEYWORDS>")){}
				else
				{
//					System.out.println(tag);
					String eTag = xmlObj.getEndtag(tag);
//					System.out.println(eTag);//System.exit(0);
					keywords.append(xmlObj.extractData(eTag,false));
					//System.out.println("keywords-----> "+keywords);//System.exit(0);

				}

				/*
				else if (tag.equals("<CE:TEXT>"))
				{
				}
				else if (tag.equals("<CE:ITALIC>"))
				{
					keywords.append("{\\it{");
				}
				else if (tag.equals("</CE:ITALIC>"))
				{
					keywords.append("}}");
				}
				else if (tag.equals("<CE:BOLD>"))
				{
					keywords.append("{\\bf{");
				}
				else if (tag.equals("</CE:BOLD>"))
				{
					keywords.append("}}");
				}*/
			}
			else
			{
				if(ch == '^' || ch == '{' || ch == '}' || ch == '_' || ch == '$' || ch == '%' || ch == '#' || ch == '"')
					keywords.append("\\"+ch);
				else if(ch== '\\')
					keywords.append("\\backslash");
				else if (ch== '&')
				{
					keywords.append(xmlObj.findEntity());
				}
				else
					keywords.append(ch);
			}
		}
		//keywords.append("}");
		//System.exit(0);
		//System.out.println("222 keywords : "+keywords);
		//System.in.read();
		return keywords.toString();
	}

	public String processAuthorGroup() throws java.io.IOException
	{
		/* Processing Author Group*/
		String tag                         = "";
		StringBuffer authorInfo            =  new StringBuffer();
		StringBuffer collabInfo            =  new StringBuffer();
		StringBuffer collabAffiliations    =  new StringBuffer();
		StringBuffer authorAffiliations    =  new StringBuffer();
		StringBuffer collabInfo_ce_Text            =  new StringBuffer();//28-08-2012 JADTD520 Updation
		boolean check_author_colb=false;//06/08/2008
		String author_colb="";//06/08/2008
		//countAuthor=0; 

		//******************[08-09-2011]**************************
					int author_count=0;
					if(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE"))//08-09-2011		
					{
						String tempTag=tag;
						long filePointer= fin.getFilePointer();
						while((!tempTag.equals("</CE:AUTHOR-GROUP>")))
							{
								char ch= (char)fin.read();
								if (ch=='<')
								{
									tempTag= xmlObj.getTag().toUpperCase();
									
									if (tempTag.startsWith("<CE:AUTHOR"))
									{
										++author_count;
									}
								}
							}
							fin.seek(filePointer);
					}
						//System.out.println("Author Count====>>"+author_count);;
					//******************[08-09-2011]**************************

		boolean cutPaste = false;
		Vector authors = new Vector();
		Hashtable affs = new Hashtable();
		if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
			cutPaste = true;
		
		String tempead1 = "";
		int colcount=0;//07-05-2012
		while (!tag.equals("</CE:AUTHOR-GROUP>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if(tag.startsWith("<CE:COLLABORATION"))
				{
					/*
					check_author_colb=true;

					 long filePointer=fin.getFilePointer();//28-08-2012 JADTD520 updation
						String statTag1=tag;
						boolean check_nextTag=false;
						while((!statTag1.equals("</CE:COLLABORATION>")))
						{
							ch= (char)fin.read();	
							if (ch=='<')
							{
								statTag1= xmlObj.getTag().toUpperCase();
								if(statTag1.startsWith("<CE:TEXT"))
								{
									
								}
								else if(statTag1.startsWith("<CE:AUTHOR-GROUP"))
								{
									check_nextTag=true;
									//code is required for JADTD520 updation 
								}
							}
							
						}
						fin.seek(filePointer);*/
						//28-08-2012 JADTD520 updation Till here
					colcount++;
					String temp=xmlObj.extractData("</CE:COLLABORATION>", true);
					//author_colb="\r\n\\authorcollabauthor{"+temp+"}";//old block on 07-05-2012
					if(colcount>1)
					author_colb+=", "+temp;
					else
					author_colb+=temp;
					collabInfo.append("\r\n\\collabauthor{"+temp+"}");
					
					//collabInfo.append("\r\n\\collabauthor{"+xmlObj.extractData("</CE:COLLABORATION>", true)+"}");//old//06/08/2008
					
					//end mark
				}
				else if(tag.startsWith("<CE:TEXT"))//28-08-2012 JADTD520 updation
				{
					collabInfo_ce_Text.append(xmlObj.extractData("</CE:TEXT>", true)+" ");
				}
				else if (tag.startsWith("<CE:AUTHOR"))
				{
					countAuthor++;
					String initials       = "";//27-12-2010
					String givenName       = "";
					String suffixName      = "";
					String degree          = "";
					String prevDegree      = "";//avinandan declare on 14-8-04
					String surName         = "";
					String AltAuthorName         = "";//28-08-2012 JADTD520 Updation
					String authRoles       = "";
					String auAff           = "";
					String auCor           = "";
					String auFnt           = "";
					int degStatus          =0;
					String order = "";//rajeev
					
					String crossRefType= "";
					String crossRefTypeAff= "";
					String crossRefValue= "";
					String locator="";
					//Added by mukesh for preserving sequence of affiliation
					//Added on 22-09-08
					//Through request of TPMS.
					/*LinkedList vr=new LinkedList();
					int affctr=0;
					int auctr=0;
					int fnctr=0;*/
					LinkedHashMap vr=new LinkedHashMap();
					int au_cont=1;
					int fnt_cont=1;
					int cor_cont=1;
					
					String Temp_Aff_Val="";//11/05/2009
					String Temp_cor_val="";//11/05/2009
					while (!tag.equals("</CE:AUTHOR>"))
					{
						ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:INITIALS>"))//27-12-2010
							{
								initials= xmlObj.extractData("</CE:INITIALS>", true);
								//System.out.println("initials==="+initials);

								//System.in.read();
								initials_check=true;
							}
							else if (tag.equals("<CE:GIVEN-NAME>"))
							{
								if(surName.length()>0)
									order="21";
								/*if(initials.length()>0)//30-12-2010
								{
									givenName= initials;
								}
								else*/
								givenName= xmlObj.extractData("</CE:GIVEN-NAME>", true);
								//System.out.println("givenName==="+givenName);
								//System.in.read();

								if(degStatus==0)
									degStatus=1;
							}
							else if (tag.equals("<CE:SUFFIX>"))
							{
								suffixName= xmlObj.extractData("</CE:SUFFIX>", true);
							}
							else if (tag.equals("<CE:DEGREES>"))
							{
								degree= xmlObj.extractData("</CE:DEGREES>", true);
								if(degStatus==1)
									degStatus=2;
								//Avinandan
								//added
								else if(degStatus==0)
								{
									prevDegree=degree;
									degree="";
								}
								//end
								//mark
							}
							//Avinandan Added
							else if (tag.equals("<CE:ROLES>"))
							{
								authRoles= xmlObj.extractData("</CE:ROLES>", true);
								if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
									authRoles="{\\it{"+authRoles+"}}";
								authRoles="\\AuRole{"+authRoles+"}";
								//System.out.println("BBBB "+authRoles);
							}
							else if (tag.equals("<CE:SURNAME>"))
							{								
								surName= xmlObj.extractData("</CE:SURNAME>", true);
							}
							else if (tag.equals("<CE:ALT-NAME>"))
							{								
								AltAuthorName= "\\altauthor{"+xmlObj.extractData("</CE:ALT-NAME>", true)+"}";
								//System.out.println("AltAuthorName===>>"+AltAuthorName);
							}
							else if (tag.startsWith("<CE:E-ADDRESS"))
							{
								
								String eaddType= xmlObj.getAttributeValue(tag, "TYPE");
								String emailCont = new String();
								
								emailCont = xmlObj.extractData("</CE:E-ADDRESS>", true);
								//System.out.println("--------------"+emailCont);
								if(eaddType.equals("EMAIL")||eaddType.equals(""))
								{

									if(ead.length ()>0)
									{
										ead+=", ";
										emailCount++;
									}
									
									ead += "\\WEML{" + emailCont + "}";//for web pdf

									String tempead="";
									
									/*if(initials.length()>0)	//30-12-2010							
									{
										//tempead=" ("+runEmailAuthor(initials)+" "+surName;
										tempead=" ("+ initials+" "+surName;
									}
									else*/
									if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid))
									{tempead=" \\DnDName{ ("+runEmailAuthor(givenName)+" "+surName;}
									else
										tempead=" ("+runEmailAuthor(givenName)+" "+surName;
									//System.out.println("tempead==> "+tempead);
									//System.in.read();
									if(suffixName.length() > 0)
									{
										tempead+=" "+suffixName;
									}
									if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid))
									tempead+=")}";
									else
										tempead+=")";
									if(ead.indexOf(emailCont+""+tempead)!=-1)
									{
										ead+=tempead;
									}
									else if(ead.indexOf(tempead)!=-1 && (tempead1.indexOf(givenName+surName) != -1))
									{		
										
										ead=ead.substring(0, ead.indexOf(tempead))+ead.substring(ead.indexOf(tempead)+tempead.length());
										ead+=tempead;
									}
									else
										ead+=tempead;

									//System.out.println("ead==> "+ead);
									//System.in.read();
									//end
									//mark
								}
								//old[url]
								/*
								else if(eaddType.equals("URL"))
								{
									if(url.length ()>0)
									{
										url+=", ";
									}
									url+=emailCont;
									//System.out.println(ead);
									if(ead.length()==0)
									{
										String tempurl=" ("+runEmailAuthor(givenName)+" "+surName;
										if(suffixName.length() > 0)
										{
											tempurl+=" "+suffixName;
										}
										tempurl+=")";
										if(url.indexOf(tempurl)>0)
										{
											url=url.substring(0, url.indexOf(tempurl))+url.substring(url.indexOf(tempurl)+tempurl.length());
											url+=tempurl;
										}
										else
											url+=tempurl;
									}
								}
								*/
								//Added by Ravi [23/11/2006 by Vivek]
								else if(eaddType.equals("URL"))
								{
									if(url.length ()>0)
									{
										url+=", ";
										UrlCount++;
									}
									url+="\\url{"+emailCont+"}{"+emailCont+"}{}";
									//url+="\\url{"+url+"}{"+url+"}{}";
									//System.out.println("====>>"+url);

								//	if(ead.length()==0)
									//{
										//String tempurl=" ("+runEmailAuthor(givenName)+" "+surName;//old
										String tempurl="";
										/*if(initials.length()>0)	//30-12-2010							
										{
											//tempurl=" ("+runEmailAuthor(initials)+" "+surName;
											tempurl=" ("+initials+" "+surName;
										}
										else*/
											tempurl=" ("+runEmailAuthor(givenName)+" "+surName;
										if(suffixName.length() > 0)
										{
											tempurl+=" "+suffixName;
										}
										tempurl+=")";
								
									if(url.indexOf(emailCont+""+tempurl)!=-1)
									{
										url+=tempurl;
									}
									else if(url.indexOf(tempurl)!=-1 && (tempurl.indexOf(givenName+surName) != -1))
									{										
										url=url.substring(0, url.indexOf(tempurl))+url.substring(url.indexOf(tempurl)+tempurl.length());
										url+=tempurl;
									}
									else
										url+=tempurl;

									
								}
								tempead1 = tempead1+givenName+surName+" ";
								//System.out.println("url===> "+url);
								//System.in.read();
							}
							else if (tag.startsWith("<CE:CROSS-REF "))//28-08-2012 JADTD520 Updation ||tag.startsWith("<CE:CROSS-REF ")
							{
								int ind= tag.indexOf("REFID=\"")+7;
								crossRefType= tag.substring(ind, tag.indexOf("\">"));
								
								//abhay
								if(crossRefTypeAff.length() > 0)
									crossRefTypeAff += "!"+crossRefType;
								else
									crossRefTypeAff += crossRefType;
								
								crossRefValue= "";
								while (!tag.equals("</CE:CROSS-REF>"))
								{
									ch= (char)fin.read();
									if (ch=='<')
									{
										tag= xmlObj.getTag().toUpperCase();
										if (tag.equals("<CE:SUP>") || tag.equals("</CE:SUP>"));//ignore sup tag
									}
									else
										crossRefValue+= ch;
								}
								//****************************************[31/05/2008] Diff tag Handling 
									//System.out.println("crossRefValue : "+crossRefValue);
									String DiffHandle="";
									String ee="";
									String first="";
									
									//System.out.println ("crossRefValue-----------------"+crossRefValue);
									if(crossRefValue.indexOf("ThomsonDiff_ThomsonDiffOpenBrace",0)!=-1)
									{
										int im=0;
										int in=0;
										
											in=crossRefValue.indexOf("ThomsonDiff_ThomsonDiffOpenBrace",0);
										if(in !=-1)
										{
											im=crossRefValue.indexOf("ThomsonDiffCloseBrace",0);
											if(in !=-1)
											{
												first=crossRefValue.substring(0,in);
												DiffHandle=crossRefValue.substring(in,im+"ThomsonDiffCloseBrace".length());
												ee=crossRefValue.substring(im+"ThomsonDiffCloseBrace".length(),crossRefValue.length());
												crossRefValue= first+ee;
											}
										}
									}
								//************************************* 
								if (crossRefType.startsWith("AFF"))
								{
									if(auAff.length()>0)
										auAff+=",";
									if(crossRefValue.startsWith("&"))
									{
										crossRefValue= xmlObj.replace(crossRefValue, xmlObj.mathEntityList);
										
									}
									//xmlObj.textEntityList);
									Temp_Aff_Val=crossRefValue;//11/05/2009
									//	System.out.println("Temp_Aff_Val : "+Temp_Aff_Val);
									
									if(!auAff.endsWith(","))
									{
										if(!xmlObj.jid.equals("PROTIS"))//23-06-2010
										auAff+="\\,";
									}
									auAff+= "\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}"+DiffHandle+"{"+crossRefValue+"}"; //18-01-2005
									//System.out.println ("auAff-----------------"+auAff);
										//vr.add("aff-"+(affctr++));
										vr.put("au"+au_cont++,"\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}"+DiffHandle+"{"+crossRefValue+"}");
								}
								if (crossRefType.startsWith("COR"))
								{
									if(auCor.length()>0)
										auCor+=",";
									
									auCor+= "\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}{";//18-01-2005
									String crossLinkValue= crossRefValue;
									//System.out.println("crossLinkValue------------->"+crossLinkValue);
									
									if(crossRefValue.startsWith("&"))
									{
										int semi=0,amp=0;
										String tempCrossRefValue="";
										//System.out.println ("crossRefValue-----------------"+crossRefValue);
										while(crossRefValue.indexOf(";",semi) != -1)
										{
											amp=crossRefValue.indexOf("&",amp) + 1;
											semi=crossRefValue.indexOf(";",semi) + 1;
											tempCrossRefValue+= xmlObj.replace(crossRefValue.substring(amp-1,semi), xmlObj.mathEntityList);//textEntityList);
										}
										crossRefValue=tempCrossRefValue;
										//System.out.println ("tempCrossRefValue-----------------"+tempCrossRefValue);
									}
										auCor+=DiffHandle+ "{\\ensuremath{"+crossRefValue+"}}";
										Temp_cor_val+="{\\ensuremath{"+crossRefValue+"}}";//11/05/2009
									auCor+="}";
									
										//vr.add("au-"+(auctr++));
										vr.put("cor"+cor_cont++,"\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}{"+DiffHandle+ "{\\ensuremath{"+crossRefValue+"}}}");
									//System.out.println ("auCor-----------------"+crossRefValue);
								}
								if (crossRefType.startsWith("FN"))
								{
									if(auFnt.length()>0)
										auFnt+=",";
									if(crossRefValue.startsWith("&"))
										crossRefValue= xmlObj.replace(crossRefValue, xmlObj.mathEntityList);//textEntityList);
									auFnt+= "\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}{"+crossRefValue+"}";//18-01-2005
									//System.out.println ("auFnt-----------------"+auFnt);
										//vr.add("fn-"+(fnctr++));
										vr.put("fnt"+fnt_cont++,"\\protect\\specialhyperlink{"+xmlObj.jid+xmlObj.aid+crossRefType+"}{"+crossRefValue+"}");
								}
							}
							else if (tag.startsWith("<CE:LINK"))
							{
								locator= xmlObj.getAttributeValue(tag, "LOCATOR");
								//System.out.println("locator : "+locator);
							}
						}
					}

					
					

					if(suffixName.length() > 0)
					{
						//auNames.add(givenName+";"+surName+" "+suffixName);
						if(initials.length()>0)//27-12-2010
						{
							auNames.add(initials+";"+surName+" "+suffixName);
							auNamesRhauthor.add(givenName+";"+surName+" "+suffixName);
						}
						else
							auNames.add(givenName+";"+surName+" "+suffixName);
					}
					else
					{
						if(initials.length()>0)//27-12-2010
						{
							auNames.add(initials+";"+surName);
							auNamesRhauthor.add(givenName+";"+surName);
						}
						else
						auNames.add(givenName+";"+surName);
						//System.out.println("auNames==> "+auNames);
						//System.in.read();
					}
					if(authorInfo.length()>0)
					{
						authorInfo.append("\r\n");
						Move_Author_tag="\r\n";//18/12/2007
					}
					authorInfo.append("\\cauthor{");

					Move_Author_tag+="\\cauthor{";//18/12/2007
					if(absAuthor.indexOf("\\cauthor{")>0)
					{
						absAuthor.append(",\r\n\\cauthor{");//Avinandan
					}
					else
					{
						absAuthor.append("\r\n\\cauthor{");//Avinandan
					}
					if((degree.length()>0) &&(degStatus!=2))
					{
						authorInfo.append(degree);
						Move_Author_tag+=degree;//18/12/2007
						absAuthor.append(degree);//avinandan
					}
					//avinandan added on 14-8-04
					if(prevDegree.length()>0)
					{
						authorInfo.append(prevDegree+" ");
						Move_Author_tag+=prevDegree+" ";//18/12/2007
						absAuthor.append(prevDegree+" ");//avinandan
						eextraAuthor+=prevDegree+" ";
					}
					//end mark
					if(order=="21")
					{
						if(surName.length()>0)
						{
							authorInfo.append(surName+" ");
							Move_Author_tag+=surName+" ";//18/12/2007
							absAuthor.append("\\snm{"+surName+"} ");//avinandan
							eextraAuthor+=surName;
						}
						if(givenName.length()>0)
						{
							authorInfo.append(givenName);
							Move_Author_tag+=givenName;//18/12/2007
							absAuthor.append("\\fnm{"+givenName+"}"); //avinandan
							eextraAuthor+=" "+givenName;
						}
						
					}
					else
					{
						if(givenName.length()>0)
						{
							//authorInfo.append(givenName+" "+);
							if(XT.stage.equalsIgnoreCase("S100")&&(XT.QueryVector.contains("Please confirm that given names and surnames have been identified correctly.")||XT.QueryVector.contains("The author names have been tagged as given names and surnames (surnames are highlighted in teal color). Please confirm if they have been identified correctly.")||XT.QueryVector.contains("Por favor, confirme que nombre (givenname) y apellido/s (surname) est&aacute;n identificados correctamente.")||XT.QueryVector.contains("Merci de v&eacute;rifier que les pr&eacute;noms et les noms ont &eacute;t&eacute; correctement identifi&eacute;s.")||XT.QueryVector.contains("Por favor, confirme se o/s nome/s pr&oacute;prio/s (givenname) e apelido/s (surname) est&atilde;o identificados corretamente.")||XT.QueryVector.contains("Prosz&eogon; potwierdzi&cacute; poprawno&sacute;&cacute; nazwisk.")||XT.QueryVector.contains("Please confirm that given name and surname are correctly identified. The different colors indicate whether tagged as first or last name. Please note that proper identification is key for correct indexing of the article.")||
									XT.QueryVector.contains("Por favor, confirme que nombre (givenname) y apellido/s (surname) est&aacute;n identificados correctamente. Los colores distintos indican si se ha etiquetado como nombre o apellido. Tenga en cuenta que la adecuada identificaci&oacute;n es fundamental para la correcta indexaci&oacute;n del art&iacute;culo.")||
									XT.QueryVector.contains("Por favor verifique se o nome pr&oacute;prio e apelido(s) est&atilde;o identificados corretamente. As diferentes cores sinalizam que foram etiquetados como nome ou apelido. Tenha em aten&ccedil;&atilde;o que a identifica&ccedil;&atilde;o adequada &eacute; essencial para uma indexa&ccedil;&atilde;o correta do artigo.")))//02-08-2011
							{
								//System.out.println("---->>"+givenName);;
								String query="";
								String DiffVal="";
								String first="";
								if(givenName.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0)!=-1)
								{
									int spos=0;
									int epos=0;
									
									spos=givenName.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",epos);
									if(spos!=-1)
									{
										epos=givenName.indexOf("\\}",spos);
										if(epos!=-1)
										{
											//query=givenName.substring(spos,epos+"\\}".length());
											//givenName=givenName.substring(0,spos);
											first=givenName.substring(0,spos);
											query=givenName.substring(spos,epos+"\\}".length());
											givenName=givenName.substring(epos+"\\}".length(),givenName.length());
											givenName=first+givenName;
										}
									}
								}
								if(givenName.indexOf("ThomsonDiff\\_ThomsonDiffOpenBrace",0)!=-1)
								{
									int spos=0;
									int epos=0;
									first="";
									spos=givenName.indexOf("ThomsonDiff\\_ThomsonDiffOpenBrace",epos);
									if(spos!=-1)
									{
										epos=givenName.indexOf("ThomsonDiffCloseBrace",spos);
										if(epos!=-1)
										{
											first=givenName.substring(0,spos);
											DiffVal=givenName.substring(spos,epos+"ThomsonDiffCloseBrace".length());
											givenName=givenName.substring(epos+"ThomsonDiffCloseBrace".length(),givenName.length());
											givenName=first+givenName;
										}
									}
								}
								authorInfo.append(DiffVal+"\\tdfnm{"+givenName+"}"+query+" ");//02-08-2011
							}
							else
								authorInfo.append(givenName+" ");
							Move_Author_tag+=givenName+" ";//18/12/2007
							absAuthor.append("\\fnm{"+givenName+"}"); //avinandan
							eextraAuthor+=givenName;
						}
						if(surName.length()>0)
						{
							//System.out.println("---->>"+XT.QueryVector);;
							//authorInfo.append(surName);
							
							if(XT.stage.equalsIgnoreCase("S100")&&(XT.QueryVector.contains("Please confirm that given names and surnames have been identified correctly.")||XT.QueryVector.contains("The author names have been tagged as given names and surnames (surnames are highlighted in teal color). Please confirm if they have been identified correctly.")||XT.QueryVector.contains("Por favor, confirme que nombre (givenname) y apellido/s (surname) est&aacute;n identificados correctamente.")||XT.QueryVector.contains("Merci de v&eacute;rifier que les pr&eacute;noms et les noms ont &eacute;t&eacute; correctement identifi&eacute;s.")||XT.QueryVector.contains("Por favor, confirme se o/s nome/s pr&oacute;prio/s (givenname) e apelido/s (surname) est&atilde;o identificados corretamente.")||XT.QueryVector.contains("Prosz&eogon; potwierdzi&cacute; poprawno&sacute;&cacute; nazwisk.")||XT.QueryVector.contains("Please confirm that given name and surname are correctly identified. The different colors indicate whether tagged as first or last name. Please note that proper identification is key for correct indexing of the article.")||
									XT.QueryVector.contains("Por favor, confirme que nombre (givenname) y apellido/s (surname) est&aacute;n identificados correctamente. Los colores distintos indican si se ha etiquetado como nombre o apellido. Tenga en cuenta que la adecuada identificaci&oacute;n es fundamental para la correcta indexaci&oacute;n del art&iacute;culo.")||
									XT.QueryVector.contains("Por favor verifique se o nome pr&oacute;prio e apelido(s) est&atilde;o identificados corretamente. As diferentes cores sinalizam que foram etiquetados como nome ou apelido. Tenha em aten&ccedil;&atilde;o que a identifica&ccedil;&atilde;o adequada &eacute; essencial para uma indexa&ccedil;&atilde;o correta do artigo.")))//02-08-2011
							{
								//System.out.println("surName---->>"+surName);;
								String query="";
								String DiffVal="";
								String first="";
								if(surName.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0)!=-1)
								{
									int spos=0;
									int epos=0;
									
									spos=surName.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",epos);
									if(spos!=-1)
									{
										epos=surName.indexOf("\\}",spos);
										if(epos!=-1)
										{
											first=surName.substring(0,spos);
											query=surName.substring(spos,epos+"\\}".length());
											surName=surName.substring(epos+"\\}".length(),surName.length());
											surName=first+surName;
										}
									}
								}
								
								if(surName.indexOf("ThomsonDiff\\_ThomsonDiffOpenBrace",0)!=-1)
								{
									int spos=0;
									int epos=0;
									first="";
									spos=surName.indexOf("ThomsonDiff\\_ThomsonDiffOpenBrace",epos);
									if(spos!=-1)
									{
										epos=surName.indexOf("ThomsonDiffCloseBrace",spos);
										if(epos!=-1)
										{
											first=surName.substring(0,spos);
											DiffVal=surName.substring(spos,epos+"ThomsonDiffCloseBrace".length());
											surName=surName.substring(epos+"ThomsonDiffCloseBrace".length(),surName.length());
											surName=first+surName;
										}
									}
								}
								//System.out.println("surName---->>"+surName);;
								//System.out.println("query---->>"+DiffVal);;
								authorInfo.append(DiffVal+"\\tdsnm{"+surName+"}"+query);//02-08-2011
							}
							else
								authorInfo.append(surName);
							Move_Author_tag+=surName;//18/12/2007
							absAuthor.append("\\snm{"+surName+"}");//avinandan
							eextraAuthor+=" "+surName;
						}
					}
					if(suffixName.length()>0)
					{
						authorInfo.append("\\jr{"+suffixName+"}");
						Move_Author_tag+="\\jr{"+suffixName+"}";//18/12/2007
						absAuthor.append("\\jr{"+suffixName+"}");
						eextraAuthor+=" "+"\\jr{"+suffixName+"}";
					}
					if((degree.length()>0) && (degStatus==2))
					{
						authorInfo.append("\\Degs{"+degree+"}");
						Move_Author_tag+="\\Degs{"+degree+"}";//18/12/2007
						absAuthor.append(" "+degree);
						eextraAuthor+=" "+"\\Degs{"+degree+"}";
					}
					if(authRoles.length()>0)
					{
						if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&& AFJU_Editorial_comment==false)||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR")) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
						{
							if(authorInfo.lastIndexOf("\\cauthor{") != -1)
								authorInfo.insert(authorInfo.lastIndexOf("\\cauthor{"),authRoles);
						}
						else
						{
							authorInfo.append(" "+authRoles);
							Move_Author_tag+=" "+authRoles;//18/12/2007
							absAuthor.append(" "+authRoles);//11/05/2009
							eextraAuthor+=" "+authRoles;
						}

					}
					if(eextraAuthor.length()>0)
						eextraAuthor+=", ";
					

					if(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE"))//08-09-2011
					{
						if(countAuthor!=author_count)
						{
							authorInfo.append("{\\aucomma}");
						}
					}

					if (auAff.length()>0 || auCor.length()>0 || auFnt.length()>0)
					{
						authorInfo.append("\\(^{");
						//System.out.println ("Move_Author_tag----------"+Move_Author_tag);
						//absAuthor.append("\\(^{");
						Move_Author_tag+="\\(^{";//18/12/2007
					}

					//System.out.println("auAffs------------\n"+auFnt);
					/*String[] auAffs=auAff.replaceAll("\\\\,","").split(",");
					String[] auCors=auCor.replaceAll("\\\\,","").split(",");
					String[] auFnts=auFnt.replaceAll("\\\\,","").split(",");*/
					//System.out.println("auAffs--11111------\n"+auFnt );
					//Added by Mukesh on 22-09-08 for sequenced affian
					int index_=Move_Author_tag.length();
					int index_1=authorInfo.length();
					
					if(xmlObj.check_MassonJid)
					{
						Set hs=vr.entrySet();
						Iterator ie=hs.iterator();
						boolean checkau=false;
						while(ie.hasNext()){
							
					
					      //System.out.println (String.valueOf(vr.get(i)));
							Map.Entry me=(Map.Entry)ie.next();
							
							if(me.getKey().toString().startsWith("au"))
							{
								//if (auAff.length()>0)
								//{
									checkau=true;
									authorInfo.append(me.getValue().toString());
									Move_Author_tag+=me.getValue().toString();//18/12/2007
									
								//}
							}
							if(me.getKey().toString().startsWith("cor"))
							{
								//if (auCor.length()>0)
								//{
									/*if (auAff.length()>0)
									{
										authorInfo.append(",");
										Move_Author_tag+=",";//18/12/2007
									}
									else              //24-2-2005
									{
										authorInfo.append("\\,");
										Move_Author_tag+="\\,";//18/12/2007
									}*/
									authorInfo.append(me.getValue().toString());
									Move_Author_tag+=me.getValue().toString();//18/12/2007
									
									
								//}
							}
	
							if(me.getKey().toString().startsWith("fnt"))
							{
								//if (auFnt.length()>0)
								///{
									/*if (auAff.length()>0 || auCor.length()>0)
									{
										authorInfo.append(",");
										Move_Author_tag+=",";//18/12/2007
									}
									else
									{
										authorInfo.append("\\,");
										Move_Author_tag+="\\,";//18/12/2007
									}*/
									authorInfo.append(me.getValue().toString());
									Move_Author_tag+=me.getValue().toString();//18/12/2007
									
								//}
							}
							
							if(ie.hasNext()&&!Move_Author_tag.endsWith(",")){
								authorInfo.append(",");
								Move_Author_tag+=",";
								
							}
					    }
						
						if(Move_Author_tag.length()>0){
							//System.out.println("3333333333");
							if(!(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE")))//08-09-2011
							{
								authorInfo.insert(index_1,"\\,");
								Move_Author_tag=Move_Author_tag.substring(0,index_)+"\\,"+Move_Author_tag.substring(index_,Move_Author_tag.length());
							}
							

								//Move_Author_tag="\\,"+Move_Author_tag;
						}

					}
					else
					{
						
						if (auAff.length()>0)
						{
							if(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE"))//08-09-2011
							{
								if(auAff.startsWith("\\,"))
								{
									auAff=auAff.substring(2,auAff.length());
								}
							}

							authorInfo.append(auAff);
							Move_Author_tag+=auAff;//18/12/2007
							//absAuthor.append(Temp_Aff_Val);//11/05/2009
							//System.out.println("authorInfo"+auAff);
						}
						if (auCor.length()>0)
						{
							if (auAff.length()>0)
							{ 
								authorInfo.append(",");
								
								Move_Author_tag+=",";//18/12/2007
								
							}
							else              //24-2-2005
							{
								
								if(!(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE")))//08-09-2011
								{
									authorInfo.append("\\,");
									Move_Author_tag+="\\,";//18/12/2007
									//System.out.println("1111111"+authorInfo);
								}
								
							}
							
							authorInfo.append(auCor);
							//System.out.println("2222222"+auCor);
							Move_Author_tag+=auCor;//18/12/2007
							absAuthor.append("{\\(^{"+Temp_cor_val+"}\\)}");//Asper new DTD 510 //11/05/2009
						}
						if (auFnt.length()>0)
						{
							if (auAff.length()>0 || auCor.length()>0)
							{
								authorInfo.append(",");
								Move_Author_tag+=",";//18/12/2007
								
							}
							else
							{
								//System.out.println("2222222");
								if(!(xmlObj.jid.equalsIgnoreCase("ADENGL")||xmlObj.jid.equalsIgnoreCase("OTOENG")||xmlObj.jid.equalsIgnoreCase("ARBR")||xmlObj.jid.equalsIgnoreCase("FARMAE")||xmlObj.jid.equalsIgnoreCase("REUMAE")))//08-09-2011
								{
									authorInfo.append("\\,");
									Move_Author_tag+="\\,";//18/12/2007
								}
								
							}
							authorInfo.append(auFnt);
							Move_Author_tag+=auFnt;//18/12/2007
							
						}
					}
					//System.out.println("authorInfo===>>"+authorInfo);
					if (auAff.length()>0 || auCor.length()>0 || auFnt.length()>0)
					{
						authorInfo.append("}\\)");
						Move_Author_tag+="}\\)";//18/12/2007
						//absAuthor.append("}\\)");//11/05/2009
					}
					authorInfo.append(AltAuthorName);//28-08-2012 JADTD520 Updation
					authorInfo.append("}<BR>");
					//System.out.println("authorInfo "+authorInfo);
					Move_Author_tag+="}";//18/12/2007
					absAuthor.append("}");
					//System.out.println ("\nabsAuthor--- "+absAuthor.toString()+"\nMove_Author_tag------- "+Move_Author_tag+"\nauthorInfo----- "+authorInfo.toString());
					
					//abhay
					/*if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR") || xmlObj.pit.equalsIgnoreCase("CNF")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
					{
						authors.addElement(authorInfo+"<>"+crossRefTypeAff);
						authorInfo = new StringBuffer();
					}*/
					if(locator.length()>0)
					{
						//authorInfo.append("\r\n\\authorpicture{%\r\n\\epsfbox{"+locator.toLowerCase()+".eps}}");//20.05.2010
						authorInfo.append("\r\n\\authorpicture{%\r\n\\AltTextULB{%\r\n\\epsfbox{"+locator.toLowerCase()+".eps}}\r\n}\r\n");
						//locator
					}
				}
				else if (tag.startsWith("<CE:COLLAB-AFF>"))
				{
					collabAffiliations.append("\\collabaffiliation{"+xmlObj.extractData("</CE:COLLAB-AFF>", true)+"}");
				}
				/*else if (tag.startsWith("<CE:AFFILIATION"))
				{
				}*/
				else if (tag.startsWith("<CE:AFFILIATION"))
				{
					String affId= "";
					if (tag.indexOf("ID=\"")>0)
					{
						int ind= tag.indexOf("ID=\"")+4;
						affId= tag.substring(ind, tag.indexOf("\"",ind));
					}
					String affLbl   = "";
					String affValue = "";
					while (!tag.equals("</CE:AFFILIATION>"))
					{
						ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:LABEL>"))
								affLbl= xmlObj.extractData("</CE:LABEL>", true);
							else if (tag.equals("<CE:TEXTFN>")||tag.startsWith("<CE:TEXTFN"))
								affValue= xmlObj.extractData("</CE:TEXTFN>", true);
						}
					}
					if(authorAffiliations.length()>0)
						authorAffiliations.append("\r\n");
					authorAffiliations.append("<cut>\\affiliation");
					if(affId.length()>0)
					{
						
						if((xmlObj.pit.equalsIgnoreCase("COR") || xmlObj.pit.equalsIgnoreCase("BRV") ||  xmlObj.pit.equalsIgnoreCase("PRV")||  xmlObj.pit.equalsIgnoreCase("PRP") ||  xmlObj.pit.equalsIgnoreCase("EDI")||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false) ||  (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) ||  (xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))))
						{
							if((xmlObj.pit.equalsIgnoreCase("COR")) && (xmlObj.jid.equalsIgnoreCase("JALCOM")))
							{
									if(affLbl.length()>0)
									authorAffiliations.append("["+affId+"]");
							}
							if((xmlObj.pit.equalsIgnoreCase("EDI")) && (xmlObj.jid.equalsIgnoreCase("HLC")))// added by mukesh on 22-10-08 request by TPMS
							{
									if(affLbl.length()>0)
									authorAffiliations.append("["+affId+"]");
							}
							if(AFJU_Editorial_comment||((xmlObj.pit.equalsIgnoreCase("EXM")) && (xmlObj.jid.equalsIgnoreCase("NEUARG")||xmlObj.jid.equalsIgnoreCase("RAMB")||xmlObj.jid.equalsIgnoreCase("RCA")||xmlObj.jid.equalsIgnoreCase("RCAE"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))))
							{
									if(affLbl.length()>0)
									authorAffiliations.append("["+affId+"]");
							}
						
						}
						else
						{
							if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
							{
							}
							else if(affLbl.length()>0)
							authorAffiliations.append("["+affId+"]");
							
						}
					}
					if(affLbl.length()>0)
						authorAffiliations.append("{"+affLbl+"}");
					if(affId.length() <= 0 && (xmlObj.pit.equalsIgnoreCase("COR")) && (xmlObj.jid.equalsIgnoreCase("JALCOM")))
					{authorAffiliations.append("{");}
					else if((affId.length() <= 0 && AFJU_Editorial_comment)||(affId.length() <= 0 && ((xmlObj.pit.equalsIgnoreCase("EXM")) && (xmlObj.jid.equalsIgnoreCase("NEUARG")||xmlObj.jid.equalsIgnoreCase("RAMB")||xmlObj.jid.equalsIgnoreCase("RCA")||xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ")))))
					{authorAffiliations.append("{");}
					else if(affId.length() <= 0 && (xmlObj.pit.equalsIgnoreCase("COR")|| xmlObj.pit.equalsIgnoreCase("PRP")|| xmlObj.pit.equalsIgnoreCase("REQ") || xmlObj.pit.equalsIgnoreCase("BRV") ||  xmlObj.pit.equalsIgnoreCase("PRV")||  xmlObj.pit.equalsIgnoreCase("EDI")||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)||  (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) ||  (xmlObj.pit.equalsIgnoreCase("EXM")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))))
					{authorAffiliations.append("{}{");}
					else
					{
						authorAffiliations.append("{");
					}
					if(xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))// 30-11-04
					{
						
						if(authorInfo.length()>0 && authorInfo.indexOf("\\cauthor{")!=-1)
						{
							String tem=authorInfo.toString();
							if(tem.endsWith("<BR>"))
							{
								authorInfo=new StringBuffer();
								authorInfo.append(tem.substring(0,tem.length()-4));
							}
							authorAffiliations.append(authorInfo.substring(8,authorInfo.length()-1)+", ");
						}
					}

					if(affId.length()>0)
						authorAffiliations.append(xmlObj.getHypertarget(affId));
					
					authorAffiliations.append(affValue);
					
					if((ead.length ()>0) && xmlObj.jid.equals("AMEEVA"))
					{
						authorAffiliations.append("; {Email: "+ead+"}");
						
					}
					if(!((xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))
						authorAffiliations.append("}");
					authorAffiliations.append("</cut>");
					
				}
				else if (tag.startsWith("<CE:CORRESPONDENCE"))
				{
					isDucklingCorrAuthor=true;
					int ind= tag.indexOf("ID=\"")+4;
					String corId= tag.substring(ind, tag.indexOf("\">"));
					corId = corId.toUpperCase();
					String corLbl   = "";
					String corValue = "";
					while (!tag.equals("</CE:CORRESPONDENCE>"))
					{
						ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:LABEL>"))
								corLbl= xmlObj.extractData("</CE:LABEL>", true);
							else if (tag.startsWith("<CE:TEXT"))
								corValue= xmlObj.extractData("</CE:TEXT>", true);							
						}
					}
					if(authorCorrespondences.length()>0)
						authorCorrespondences.append("\r\n");					

					if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")&&AFJU_Editorial_comment==false) ||(xmlObj.pit.equalsIgnoreCase("PGL")&& xmlObj.check_MassonJid==false)|| (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")&& (!xmlObj.jid.equalsIgnoreCase("NEUARG")&&!xmlObj.jid.equalsIgnoreCase("RAMB")&&!xmlObj.jid.equalsIgnoreCase("RCA")&&!xmlObj.jid.equalsIgnoreCase("RCAE")&&!xmlObj.jid.equalsIgnoreCase("VACUN"))) ||(xmlObj.pit.equalsIgnoreCase("EDI")&& (!xmlObj.jid.equalsIgnoreCase("JASCERNOTREQ"))) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||((xmlObj.pit.equalsIgnoreCase("COR")  && !xmlObj.jid.equalsIgnoreCase("JALCOM"))) || (xmlObj.pit.equalsIgnoreCase("CNF")&&!xmlObj.jid.equalsIgnoreCase("JCV")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
					{
							authorCorrespondences.append("\\Correspondingauthor{\\hypertarget{"+xmlObj.jid+xmlObj.aid+corId+"}{}{$^{"+corLbl+"\\,}$}"+corValue+"}");
								//System.out.println("Ravi ---> "+authorCorrespondences);
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
						{
							Move_botton_author_Group_Info.append("\\Correspondingauthor{\\hypertarget{"+xmlObj.jid+xmlObj.aid+corId+"}{}"+corValue+"}\r\n");
						}
						else
						{
							//DucklingCorrAuthor
							//System.out.println("xt.DucklingOrderStatus===>>"+XT.DucklingOrderStatus);
							
							authorCorrespondences=authorCorrespondences.append("\\Correspondingauthor{\\hypertarget{"+xmlObj.jid+xmlObj.aid+corId+"}{}"+corValue+"}");														
							//authorInfo.append("\r\n\\"+authorCorrespondences);							
						
						}
							
							//System.out.println("corValue ---> "+corValue);
							//System.in.read();
					/*		//**********************************
						//	if(XT.modelStyle.equals("7"))
							{
								xmlObj.abrKwd.append("\r\n\\Abbreviation{\r\n");
								//tempClass=processKeywords(tag);
								System.out.println("TempCorAbb -- before ----> "+TempCorAbb);
								//tempClass=tempClass.replaceAll("\\\\sep",";");
								xmlObj.abrKwd.append(tempClass+"}");
								System.out.println("tempClass ----> "+tempClass);
							}
							
							*/
							//*********************************
					}
					
					//abhay 17/07/2006
					if(countAuthor == 1 && xmlObj.jid.equalsIgnoreCase("physt") && (xmlObj.pit.equalsIgnoreCase("cor") || xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("edi")))
					{
						if(authorCorrespondences.indexOf("\\hypertarget{") != -1 && authorCorrespondences.indexOf("\\,}$}") != -1)
						{
							//System.out.println(authorCorrespondences);
							authorCorrespondences.delete(authorCorrespondences.indexOf("\\hypertarget{"), authorCorrespondences.indexOf("\\,}$}")+"\\,}$}".length());
							authorCorrespondences.insert(authorCorrespondences.indexOf("\\Correspondingauthor{")+20,"[]");
						}
					}
					else if(corLbl.length() > 0)
					{
						if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
						{
							//System.out.println("Move_botton_author_Group_Info==>> "+Move_botton_author_Group_Info);
							if(Move_botton_author_Group_Info.indexOf("\\Correspondingauthor{",0)!=-1)
							Move_botton_author_Group_Info.insert(Move_botton_author_Group_Info.indexOf("\\Correspondingauthor{")+20,"["+corLbl+"]");
						}
						else
						{
							//System.out.println("===>>"+xmlObj.MassonJidList);
							//System.out.println("JID===>>"+xmlObj.jid);
							authorCorrespondences.insert(authorCorrespondences.indexOf("\\Correspondingauthor{")+20,"["+corLbl+"]");
							checkCorres=authorCorrespondences.toString();
							if(XT.DucklingOrderStatus==true && xmlObj.MassonJidList.contains(xmlObj.jid))
							{
								//System.out.println("===>>"+xmlObj.MassonJidList);
								//System.out.println("JID===>>"+xmlObj.jid);
								DucklingCorrAuthor=authorCorrespondences.toString();
								
								authorCorrespondences=new StringBuffer();
							}
							//System.out.println("DucklingCorrAuthor===>>"+DucklingCorrAuthor);
							
						}
					}
					
					if((ead.length ()>0) && ((xmlObj.jid.equals("EMT") && xmlObj.pit.equals("PNT"))))
					{
						authorAffiliations.append(", \\hypertarget{"+xmlObj.jid+xmlObj.aid+corId+"}{}"+corValue+" {\\textit{E-mail address}}: "+ead+"}");
						//System.out.println("eadddddd "+ead);
						
					}
				}
				else if (tag.startsWith("<CE:FOOTNOTE"))
				{
					int ind= tag.indexOf("ID=\"")+4;
					String fnId= tag.substring(ind, tag.indexOf("\">"));
					fnId = fnId.toUpperCase();
					String fnLbl   = "";
					String fnValue = "";
					boolean fst=true;
					while (!tag.equals("</CE:FOOTNOTE>"))
					{
						ch= (char)fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:LABEL>"))
								fnLbl= xmlObj.extractData("</CE:LABEL>", true);
							else if (tag.startsWith("<CE:NOTE-PARA")) // 16-12-2004
							{
								if(fst)
									fnValue= xmlObj.extractData("</CE:NOTE-PARA>", true);
								else
									fnValue=fnValue+"\\newline\r\n"+ xmlObj.extractData("</CE:NOTE-PARA>", true);
								fst=false;
							}
						}
					}
					//System.out.println("Move_botton_author_Group_Info::::"+Move_botton_author_Group_Info);
					//System.out.println("dad_check_doc_head::::"+dad_check_doc_head);
					if(authorFootnotes.length()>0)
						authorFootnotes.append("\r\n");
						//System.out.println("xmlObj.jid"+xmlObj.jid);
						//System.out.println("artDochead"+artDochead);
						
						if(xmlObj.jid.equalsIgnoreCase("PROTIS")&&dad_check_doc_head==true)
						{
							//System.out.println("Move_botton_author_Group_Info===>>"+Move_botton_author_Group_Info);
							Move_botton_author_Group_Info.append("\r\n\\authorfootnote{"+fnLbl+"}{\\hypertarget{"+xmlObj.jid+xmlObj.aid+fnId+"}{}"+fnValue+"}");
							//System.out.println("22222222222222");
						}
						else
						{
							
							authorFootnotes.append("\\authorfootnote{"+fnLbl+"}{\\hypertarget{"+xmlObj.jid+xmlObj.aid+fnId+"}{}"+fnValue+"}");
						}
					
				}

			}
		}
		String temp="";
		//temp= authorInfo.toString();//old //18/12/2007
		/**
		* [18/12/2007]
		* Added By : Ravi
		* Change Point : If PIT = LIT and MODEL = -MODDFrench then authoe move to the end of file
		* Change request By : TPMS
		*/
		//if(( xmlObj.pit.equalsIgnoreCase("LIT"))&&((XT.modelStyle.equalsIgnoreCase("-MODDFrench"))||(XT.modelStyle.equalsIgnoreCase("-MODEFrench"))))//08-04-2015
		if(( xmlObj.pit.equalsIgnoreCase("LIT"))&&((XT.modelStyle.toUpperCase().startsWith("-MODDFRENCH"))||(XT.modelStyle.toUpperCase().startsWith("-MODEFRENCH"))))
		{
			/**
			* Date : 22/02/2008
			* Added By : Ravi
			*Change Point: Incase of model  -MODDFrench and pit =LIT then author information should be in original position.
			*Change Request By : TPMS.
			*/
			temp= authorInfo.toString();
		}
		else
		{

			temp= authorInfo.toString();
			//System.out.println("authorInfo temp "+authorInfo);
		}
		//System.out.println("=========>>"+authorInfo);
		//end 18/12/2007
		/*Blocked by :Ravi [21.02.2007]
		* \collabauthor will come after author
		* Change Request By : Vivek
		
		if(authorAffiliations.length()>0)
			temp+= "\r\n"+authorAffiliations;
		*/

		//abhay
		/*if(((xmlObj.pit.equalsIgnoreCase("brv") || xmlObj.pit.equalsIgnoreCase("PRV"))||(xmlObj.pit.equalsIgnoreCase("EDI")) || (xmlObj.pit.equalsIgnoreCase("REQ")) || (xmlObj.pit.equalsIgnoreCase("EXM")) ||(xmlObj.pit.equalsIgnoreCase("PRP"))||(xmlObj.pit.equalsIgnoreCase("COR") || xmlObj.pit.equalsIgnoreCase("CNF")) || (xmlObj.jid.equals("DNAREP") && (artDochead.equalsIgnoreCase("Classics in DNA Repair") || artDochead.equalsIgnoreCase("Historical reflections") || artDochead.toUpperCase().startsWith("HOT TOPICS IN DNA REPAIR")))) && !xmlObj.jid.equalsIgnoreCase("HLC"))
		{
			//System.out.println("\n\n====>"+authors);
			//System.out.println("\n\n---->"+affs);
			temp = "";
			String aff = "";
			String prevAff = "";
			
			for (int i=0; i < authors.size(); i++)
			{
			    String[] authAff = authors.get(i).toString().split("<>");
			    
			    if(authAff[1].indexOf("!") != -1)
			    {
			    	authAff[1] = authAff[1].replaceAll("!COR([0-9]+)","");
			    	authAff[1] = authAff[1].replaceAll("COR([0-9]+)!","");	
			    }
			    
			    prevAff = aff;
			    aff = authAff[1];//AFF?
			    //System.out.println(aff);
			    
			    if(aff.equals(prevAff) || prevAff.equals(""))
			    {
			    	temp += authAff[0] + "\r\n";
			    }
			    else
			    {
			    	temp += affs.get(prevAff).toString()+"\r\n\r\n";
			    	temp += authAff[0] + "\r\n";
			    }
			}
			temp += affs.get(prevAff).toString()+"\r\n\r\n";
		}*/

		/*
		*Data : 06/08/2008
		* Change Point : Below collebration tag handled is blocked as collebration contain will come with last author
		* Change Request : TPMS [Vivek] Example jid RESUS aid3563
		*/
		if(check_author_colb==true)
		{
			//author_colb
			//check_author_colb=true;
			
			int spos=0;
			int epos=0;
			spos=temp.lastIndexOf("\\cauthor{");
			//System.out.println("tempppp "+temp);
			if(spos !=-1)
			{
				epos=temp.indexOf("<BR>",spos);
				if(epos !=-1)
				{
					//System.out.println("tempppp "+temp);
					StringBuffer sb=new StringBuffer(temp);
					//sb=sb.insert(epos-1,author_colb);//old block on 07-05-2012
					sb=sb.insert(epos-1,"\r\n\\authorcollabauthor{"+collabInfo_ce_Text+author_colb+"}");//new 07-05-2012
					temp=sb.toString();
					
				}
			}
			else if(collabInfo.length ()>0)
			{
				//temp+= "\r\n"+collabInfo;//old block on 07-05-2012
				temp+= "\r\n\\authorcollabauthor{"+collabInfo_ce_Text+author_colb+"}";//new 07-05-2012
			}
			
			check_author_colb=false;
		}
		else if(collabInfo.length ()>0)
		{
			//temp+= "\r\n"+collabInfo;//old block on 07-05-2012
			temp+= "\r\n\\authorcollabauthor{"+collabInfo_ce_Text+author_colb+"}";//new 07-05-2012
		}
		//System.out.println("tempppp "+temp);
		temp=temp.replaceAll("<BR>","");

		//***********************[End of the collebration tag handling]***********
		/*
		*Data : 06/08/2008
		* Change Point : Below collebration tag handled is blocked as collebration contain will come with last author
		* Change Request : TPMS [Vivek] Example jid RESUS aid3563
		if(collabInfo.length ()>0)
			temp+= "\r\n"+collabInfo;
		*/
		
		
		
		/*Added by :Ravi [21.02.2007]
		* Collabauthor shoud be convert before affiliation (MISC type) 
		* Change Request By : Vivek
		*/
		if(authorAffiliations.length()>0)
			temp+= "\r\n"+authorAffiliations;
			//System.out.println("authorAffiliations:: "+authorAffiliations);
		//end
		if(collabAffiliations.length ()>0)
			temp+= "\r\n"+collabAffiliations;
		//System.out.println("authorAffiliations---> "+authorAffiliations);
		//System.out.println("collabAffiliations---> "+collabAffiliations);
		//System.out.println("temp---> "+temp);
		//System.out.println("absAuthor---> "+absAuthor);
		//System.in.read();
		if((xmlObj.jid.equalsIgnoreCase("APCATA")||xmlObj.jid.equalsIgnoreCase("CPLETT")))
		{
			//absAuthor.append("\r\n"+authorAffiliations);
			absAuthor=new StringBuffer();
			absAuthor.append("\r\n"+temp);
		}
		//System.out.println("temp---> "+temp);
		return temp;
	}

	public String getShortName(String nm)
	{
		String name= "";
		StringTokenizer tokens= new StringTokenizer(nm, " ");

		while(tokens.hasMoreTokens())
		{
			String token= tokens.nextToken();
			//System.out.println("token : "+token);
			if(token.indexOf (".")>0)
				name+= token.substring(0, token.lastIndexOf("."));
			else if(name.length()>0)
				name+= token.charAt(0)+".";
			else
				name+= token+".";
			//name+= token.charAt(0)+".";
		}
		return name;
	}
///////////////////////////////////////////////////////////////////////
	private String runEmailAuthor(String fnm) throws IOException
	{	
		//System.out.println("fnm : "+fnm);
		//System.in.read();
		/*
		Date : 03/02/2009
		Modyfy By : Ravi
		Remarks : Diff handle in author name initials.
		*/
		String temp="";
		String temp1="";
		String temp2="";
		if(fnm.indexOf("ThomsonDiff",0)!=-1)
		{
			temp=fnm.substring(fnm.indexOf("ThomsonDiff",0)+"ThomsonDiff\\_ThomsonDiffOpenBrace".length(),fnm.indexOf("ThomsonDiffCloseBrace",0));
			fnm=fnm.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)","");
			fnm=fnm.replaceAll("ThomsonDiffCloseBrace","");
			temp="ThomsonDiff\\_ThomsonDiffOpenBrace"+temp+"ThomsonDiffCloseBrace";
		}
		//end
			//System.out.println("temp : "+temp);
			//System.out.println("fnm : "+fnm);
		//System.in.read();
		StringTokenizer str = new StringTokenizer(fnm , " ");
		String runA = new String();
		LinkedList l = new LinkedList();
		String t = new String();
		while(str.hasMoreTokens())
		{
			l.add(str.nextToken());
			
		}
		for(int i=0;i<l.size();i++)
		{
			t =(String) l.get(i);
			//System.out.println("t->"+t);
			if(t.indexOf("\\.") > 0)
			{
				//System.out.println("Name->"+t);
				runA = runA + getInitial(t);
			}
			else if(t.indexOf(".") > 0)
			{
					
					//runA = runA+t; //old
					
					
					
					//Added By Ravi [by Usmani mail 30/11/2006]
					//Change Reason :if Author name contain "-" 
					 if(t.indexOf("-") != -1)
						{
							runA = runA+ getHypenInitial(t);
						}
						else
						{
							runA = runA+t;
						}
						
			}
			else if(t.startsWith("(") && t.endsWith(")"))
			{

			}
			else if(t.indexOf("-") > 0)
			{
				runA = runA + getHypenInitial(t);
			//	System.out.println("runA==> "+runA);
			//	try{System.in.read();}catch(Exception e){}
			}
			else
			{
				//if(!t.equals("\\&"))
				runA = runA + getInitial(t);
			}
		}
		/*
		Date : 03/02/2009
		Modyfy By : Ravi
		Remarks : Diff handle in author name initials.
		*/
		if(temp.length()>0)
		{
			runA=temp+runA;
		}
		//end
		return runA;
	}


	private String getHypenInitial(String tname)
	{
		StringTokenizer str = new StringTokenizer(tname , "-");
		String runA = new String();
		LinkedList l = new LinkedList();
		String t = new String();
		
		while(str.hasMoreTokens())
		{
			l.add(str.nextToken());
		}
		for(int i=0;i<l.size();i++)
		{
			t =(String) l.get(i);
			//As per discussion with CQS this small case hyphen author Initial has been commented.29-Aug-2005
			//System.out.println(t);
			//boolean b = Pattern.matches("[a-z]([^,])+", t);///changed on 13 june 2005
			//System.out.println(b);
			//if(!b)
			//{
				//System.out.println("t  "+t);
				t = getInitial(t);
				
				if(runA.length() > 0 && t.length()>0)
				{
					runA = runA +"-"+t;
				}
				else
				{
					runA = t;
				}

			/*}
			else
				t="";*/
			
		}
		return runA;
	}

private String getInitial(String tname)
{
	boolean moreInitial=false;
	String x = new String();	
	if(tname.startsWith("{\\"))
	{
		//Avinandan commented
		//x=tname.substring(0,tname.indexOf("}}"))+"}}";
		//Avinandan added on 25-8-04
		if(tname.indexOf("}}")!=-1)
			x=tname.substring(0,tname.indexOf("}}"))+"}}";
		else
			x=tname.substring(0,tname.indexOf("}"))+"}";
		//end mark
	}
	else if(tname.startsWith("\\"))
	{
		//Avinandan commented
		//x=tname.substring(0,tname.indexOf("}}"))+"}}";
		//Avinandan added on 25-8-04
		if(tname.indexOf("}}")!=-1)
			x=tname.substring(0,tname.indexOf("}}"))+"}}";
		else
			x=tname.substring(0,tname.indexOf("}"))+"}";
		//end mark
	}
	else
	{
		if(tname.indexOf(".",0)!=-1)
		{
			//String[] ar=tname.split(".");
			String t="";
			StringTokenizer str = new StringTokenizer(tname , ".");
			LinkedList l = new LinkedList();
			while(str.hasMoreTokens())
			{
				l.add(str.nextToken());
			}
			for(int i=0;i<l.size();i++)
			{
				t =(String) l.get(i);
				
				x += t.substring(0,1)+".";
				//System.out.println("x "+x);
				moreInitial=true;
			}
		}
		else
		{
			x = tname.substring(0,1);
		}
		//x = tname.substring(0,1);
	}
	if(moreInitial==false)
	{
		x+=".";
	}
	return x;
}
//processGraphicalAbstract() declare by Avinandan
private void processGraphicalAbstract(String artTitle)throws FileNotFoundException, IOException
{
	getModelStyle();
	String newGR=getNewGraphicalLayout(xmlObj.jid, Integer.parseInt(xmlObj.aid));
//	abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",auto,onecolumn,Thumbnail]{XT-MOD"+modelStyle+"}\r\n");+"Stage=\""+stage+XT.web+"-draft\""+
	//abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",auto,onecolumn,Thumbnail"+"Stage=\""+XT.stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");
	if(XT.FMS.length()>0)
	{
//		abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn,"+"Stage=\""+XT.stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");
		abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");
	}
	else
	{
//		abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn,Thumbnail,"+"Stage=\""+XT.stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+"}\r\n");
		abstractGraphical.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn]{XT-MOD"+modelStyle+"}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn]{XT-MOD"+modelStyle+"}\r\n");
		//System.out.println("abstractGraphical : "+abstractGraphical);
		//System.in.read();
	}

	if(modelStyle.toLowerCase().endsWith("plus"))
	{
		if(newGR.length()<1){
			abstractGraphical.append("\\usepackage{graphabstractPlus}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{graphabstractPlus}\r\n");
		}else{
			abstractGraphical.append("\\usepackage{"+newGR+"}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{"+newGR+"}\r\n");
		}
	}
	else if(modelStyle.toLowerCase().endsWith("plusg"))//04/03/2008
	{
		if(newGR.length()<1){
			abstractGraphical.append("\\usepackage{graphabstractPlusG}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{graphabstractPlusG}\r\n");
		}else{
			abstractGraphical.append("\\usepackage{"+newGR+"}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{"+newGR+"}\r\n");
		}
	}
	else if((modelStyle.equals("7"))||(modelStyle.equals("-Mod7IChemE"))||(modelStyle.equals("-PIO")))
	{
		if(newGR.length()<1){
			abstractGraphical.append("\\usepackage{graphabstractMod7}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{graphabstractMod7}\r\n");
		}else{
			abstractGraphical.append("\\usepackage{"+newGR+"}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{"+newGR+"}\r\n");			
		}
	}
	else
	{
		if(newGR.length()<1){
			abstractGraphical.append("\\usepackage{graphabstract}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{graphabstract}\r\n");
		}else{
			abstractGraphical.append("\\usepackage{"+newGR+"}\r\n");
			abstractGraphicalWithResearchHighlight.append("\\usepackage{"+newGR+"}\r\n");
		}
	}
	/*if(!modelStyle.toLowerCase().endsWith("plusg"))//blocked on 12-01-2012 as discussed with Vivek
	{
		abstractGraphical.append("\\voffset-5pc\r\n");
		abstractGraphical.append("\\hoffset-4pc");
		abstractGraphicalWithResearchHighlight.append("\\voffset-5pc\r\n");
		abstractGraphicalWithResearchHighlight.append("\\hoffset-4pc");
	}*/
	abstractGraphical.append("\r\n\\begin{document}\r\n");
	abstractGraphical.append("\\firstpage{}\r\n");
	abstractGraphical.append("\\lastpage{}\r\n");
	abstractGraphical.append("\\jvol{}\r\n");
	abstractGraphical.append("\\jid{"+xmlObj.jid+"}\r\n");
	abstractGraphical.append("\\aid{"+xmlObj.aid+"}\r\n");
	if(!xmlObj.articleNo.isEmpty()){
		System.out.println("xmlObj.articleNo :4:> "+xmlObj.articleNo);
		abstractGraphical.append("\\articleID{"+xmlObj.articleNo+"}\r\n");
	}

	if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT")))
	{
		abstractGraphicalWithResearchHighlight.append("\r\n\\begin{document}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\firstpage{}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\lastpage{}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\jvol{}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\jid{"+xmlObj.jid+"}\r\n");
		abstractGraphicalWithResearchHighlight.append("\\aid{"+xmlObj.aid+"}\r\n");
		if(!xmlObj.articleNo.isEmpty()){
			System.out.println("xmlObj.articleNo :5:> "+xmlObj.articleNo);
			abstractGraphicalWithResearchHighlight.append("\\articleID{"+xmlObj.articleNo+"}\r\n");
		}
	}

	//abstractGraphical.append("\r\n\\begin{frontmatter}\r\n\\title{Graphical Abstract}\r\n\\begin{GRAabstract}");
	if(xmlObj.jid.equalsIgnoreCase("SUPFLU"))
	{
		abstractGraphical.append("\r\n\\title{}\r\n\\makechaptertitle\r\n\\begin{GRAabstract}");
		abstractGraphicalWithResearchHighlight.append("\r\n\\title{}\r\n\\makechaptertitle\r\n\\begin{GRAabstract}");
	}
	else
	{
		abstractGraphical.append("\r\n\\title{Graphical Abstract}\r\n\\makechaptertitle\r\n\\begin{GRAabstract}");
		if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT"))){
		abstractGraphicalWithResearchHighlight.append("\r\n\\title{Graphical Abstract}\r\n\\makechaptertitle\r\n\\begin{GRAabstract}");
		}
	}
	if(absAuthor.length()>1)
	{
		abstractGraphical.append(absAuthor.toString());
		if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT")))
		{
			abstractGraphicalWithResearchHighlight.append(absAuthor.toString());
		}
	}
	if(artTitle.length()>0)
	{
		abstractGraphical.append("\r\n\\atl{"+artTitle+"}\r\n");
		if((!xmlObj.jid.equalsIgnoreCase("APCATA")||!xmlObj.jid.equalsIgnoreCase("CPLETT")))
		{
			abstractGraphicalWithResearchHighlight.append("\r\n\\atl{"+artTitle+"}\r\n");
		}
	}

}//end of processGraphicalAbstract()
//getModelStyle() declared by Avinandan
public String getKeywordCutOff(String CuttOffJid)throws FileNotFoundException, IOException//20/01/2009
	{
	
	String CuttOffAid="";
	//	XT.databasePath
	String path=XT.databasePath+"\\KeywordsList.dbf";
String line="";
	RandomAccessFile mDBF = new RandomAccessFile(new File(path),"r");
	while((line = mDBF.readLine()) != null)
   		{
			String []a=line.split("<-->");

			if(a[0].equalsIgnoreCase(CuttOffJid))
			{
				CuttOffAid=a[1];
				break;
			}
			
		}
		//System.out.println("------->>> "+CuttOffAid);
		return CuttOffAid;
	}

	public String getNewGraphicalLayout(String jid, int aid)
	{
		System.out.println("Checking cutoff for NewGraphicalLayout.");
		String newsty="";
		Properties prop=new Properties();
		File NewGR=new File(XT.databasePath+"\\NewGraphicalLayout.dbf");
		if(NewGR.exists()){
			prop=loadProp(XT.databasePath+"\\NewGraphicalLayout.dbf");
			String propval=prop.getProperty(jid, "");
			if(!propval.equals("")){
				String provalue[] = propval.split("<>");
				if(propval.length()>1){
					int cutOff;
					try {
						cutOff = Integer.parseInt(provalue[0]);
						if(aid >= cutOff){
							newsty = provalue[1];
						}
					} catch (NumberFormatException e) {
						System.out.println("CutOff no. in "+XT.databasePath+"\\NewGraphicalLayout.dbf is in wrong format.");
						e.printStackTrace();
					}
				}else{
					System.out.println("Problem in getting value from "+XT.databasePath+"\\NewGraphicalLayout.dbf, Please check the format of the value for jid "+jid);
				}
			}else{
				System.out.println("JID not exist in "+XT.databasePath+"\\NewGraphicalLayout.dbf");
			}
		}else{
			System.out.println(XT.databasePath+"\\NewGraphicalLayout.dbf not exist.");
		}
		System.out.println("NewGraphicalLayout status ::"+newsty);
		return newsty;
	}
	
	public Properties loadProp(String arg) {
		Properties p=new Properties();
		File file = new File(arg);
		if(file.exists())
		{
			try
			{
				//log.info("arg==========>>"+arg);
				FileInputStream fs=new FileInputStream(new File(arg));
				p.load(fs);
				fs.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
				System.err.println("Failed to open Property file "+arg);
				return p;
			}
		}
		else
		{
			//System.err.println("File "+file.getName()+" not exist !!!");
		}
		return p;
	}

public void getModelStyle()throws FileNotFoundException, IOException
{
	RandomAccessFile mDBF = new RandomAccessFile(XT.modelFilePath,"r");
	if (!(new File(XT.modelFilePath)).exists())
	{
		System.out.println("ERROR [TexConversion] :: One or More Source Files Missing");
		System.exit(0);
	}
	BufferedReader br=new BufferedReader(new FileReader(XT.modelFilePath));
	String lineStr="";
	boolean found=false;
	while ((lineStr=br.readLine())!=null)
	{		
		if(lineStr.startsWith(xmlObj.jid.trim()+";"))
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
					

					
					//System.out.println("modelStyle ==>  "+modelStyle+"\n XT.modelFilePath "+XT.modelFilePath+"\n xmlObj.jid"+xmlObj.jid+"\n lineStr : "+lineStr);
				}
				i++;
			}while(token.hasMoreElements());
//				System.out.println("FONT STYLE"+fntStyle);
//				System.exit(0);
		}
	}
	if (!found)
	{
		System.out.println("ERROR [TexConversion] :: Journal Information Not Found");
		System.exit(0);
	}
}//end of getModelStyle()


	public String processStereoChem(String tag)throws java.io.IOException //abhay 01/07/2006
	{
		StringBuffer stereo = new StringBuffer();
		stereo.append("\r\n\\begin{StereoChem}");
	if(absAuthor.length()>1)//get live on 16/05/2009
		{
			String Temp=absAuthor.toString();
				Temp=Temp.replaceAll("\\\\authors","");
				stereo.append(Temp);
			//stereoContents.append(absAuthor.toString());//11/05/2009 Blocked
			
		}//end
		while (!tag.equals("</CE:STEREOCHEM>"))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equalsIgnoreCase("<ce:compound-struct>"))
				{
					String eTag = xmlObj.getEndtag(tag);
					stereo.append("\r\n\\strfx{");
					stereo.append(xmlObj.extractData(eTag,true));
					stereo.append("}");
				}
				else if (tag.equalsIgnoreCase("<ce:compound-name>"))
				{
					String eTag = xmlObj.getEndtag(tag);
					stereo.append("\r\n\\compound{");
					stereo.append(xmlObj.extractData(eTag,true));
					stereo.append("}");
				}
				else if (tag.equalsIgnoreCase("<ce:compound-formula>"))
				{
					String eTag = xmlObj.getEndtag(tag);
					stereo.append("\r\n\\formula{");
					stereo.append(xmlObj.extractData(eTag,true));
					stereo.append("}");
				}
				else if (tag.equalsIgnoreCase("<ce:compound-info>"))
				{
					String eTag = xmlObj.getEndtag(tag);
					stereo.append("\r\n\\compoundtext{");
					stereo.append(xmlObj.extractData(eTag,true));
					stereo.append("}");
				}
			}
			else
			{
				if(ch == '^' || ch == '{' || ch == '}' || ch == '_' || ch == '$' || ch == '%' || ch == '#' || ch == '"')
					stereo.append("\\"+ch);
				else if(ch== '\\')
					stereo.append("\\backslash");
				else if (ch== '&')
				{
					stereo.append(xmlObj.findEntity());
				}
				else
					stereo.append(ch);
			}
		}
		stereo.append("\r\n\\end{StereoChem}\r\n");
		return stereo.toString();
	}

	public void processStereoFile(String artTitle)throws FileNotFoundException, IOException//abhay 03/07/2006
	{
		StringBuffer stereoContents = new StringBuffer();
		getModelStyle();

	//	stereoContents.append("\\documentclass[Stage=\"S100-GA\",auto,onecolumn]{XT-MOD"+modelStyle+"}\r\n");//Commented by arvind [25-4-07]
	//	stereoContents.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn"+"Stage=\""+XT.stage+XT.web+"-draft\""+"]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");//Commented by arvind [25-4-07]
		stereoContents.append("\\documentclass[Stage=\"S100-GA\",Thumbnail,auto,onecolumn]{XT-MOD"+modelStyle+XT.FMS+"}\r\n");//Commented by arvind [25-4-07]

		String newGR=getNewGraphicalLayout(xmlObj.jid, Integer.parseInt(xmlObj.aid));
		if(newGR.length()<1)
			stereoContents.append("\\usepackage{graphabstractPlusG}\r\n");
		else
			stereoContents.append("\\usepackage{"+newGR+"}\r\n");
		//stereoContents.append("\\voffset-5pc\r\n");
		//stereoContents.append("\\hoffset-4pc");
		stereoContents.append("\\usepackage{tiffv2,stfloats}\r\n");
		
		stereoContents.append("\r\n\\begin{document}\r\n");
		stereoContents.append("\\firstpage{}\r\n");
		stereoContents.append("\\lastpage{}\r\n");
		stereoContents.append("\\jvol{}\r\n");
		stereoContents.append("\\jid{"+xmlObj.jid+"}\r\n");
		stereoContents.append("\\aid{"+xmlObj.aid+"}\r\n");
		if(!xmlObj.articleNo.isEmpty()){
			System.out.println("xmlObj.articleNo :6:> "+xmlObj.articleNo);
			stereoContents.append("\\articleID{"+xmlObj.articleNo+"}\r\n");
		}
		stereoContents.append("\r\n\\title{Stereochemistry Abstracts}\r\n\\makechaptertitle\r\n");
		//System.out.println("absAuthor : "+absAuthor);
		/*if(absAuthor.length()>1)//blocked on 16/05/2009
		{

			String Temp=absAuthor.toString();
				Temp=Temp.replaceAll("\\\\authors","");
				stereoContents.append(Temp);
			//stereoContents.append(absAuthor.toString());//11/05/2009 Blocked
			
		}*/
		stereoContents.append(stereoData);

		stereoContents.append("\r\n\r\n\\end{document}\r\n");

		if(new File(xmlObj.aid+"s.tex").exists())
			new File(xmlObj.aid+"s.tex").delete();

		RandomAccessFile st = new RandomAccessFile(xmlObj.aid+"s.tex", "rw");
		st.writeBytes(stereoContents.toString());
		st.close();
	}

}

