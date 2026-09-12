package tp.xt;
import java.io.*;
import java.util.*;
import java.util.regex.*; 

//---------------------------------------------------------------------------------------------------------
class global
{
	public static String cont_avail="no";
	public static String pub_for_ref5="";
	public static String pub_for_ref5Manage="";
	public static String dkj_edited_book="";
	public static String dkj_book_series="";
	public static String dkj_bSer="";
	public static String dkj_pub="";
	public static String dkj_date="";
	public static String for_editor="";
	public static String isbn1="";
	public static String bookpage="";
}
//-------------------------------------------------------------------------------------------------------------
class BiblographyContents
{
	XMLObjects xmlObj;
	String bibDate;
	String artTitle,transTitle ;
	String artComment;
	String vnr;
	String journalTitle;
	boolean ATendwithQ=false;
	boolean artTitleExist= false;
	boolean authorExist= false;
	boolean isMultipleEditors= false;
	boolean isContribution= false;
	boolean isbooktitle=false;
	boolean isbookpage=false;
	boolean isbookpagewithedition=false;
	boolean isVolinBookSeries=false;
	String pubforsoceco="";
	String hostType="";
	String tempLabel=""; // 15-07-2004	
	String tempDateForSYAPM=""; // 15-07-2004	
	String tempDateForJASCER=""; // 30-03-2013	
	boolean nolabel = false;

	boolean isEditor=false; //29-07-04
	String bsTitle=""; // 3-8-04
	String bsVol=""; //3-08-04
	String secTitle = new String();
	String ebTitle=""; // 6-8-04
	String bTitle=""; // 6-8-04
	boolean mulHost = false; // 14-9-04
	String mulhostDate="";//14-9-04
	String physt_brv="";//20-10-04
	String socs_brv="";//25-11-04
	String check="";   //variables used for label problem in ref. style-5
	String check1="";
	String check2="";
	String resusaid="";
	Vector booksJid;//28-01-2005
	boolean lblsame=false;
	static boolean otherinterref=false;
	boolean	firstAuth=false;
	boolean	firstAuthSoceco=false;
	public static boolean temp_Reference=false;
	String movetitleforZEFQ="";
	String tempone="";
	String tempbb="";
	String TempAutoth="";
	String TempAutother="";
	boolean intref=false;
	boolean boldoi=false;
	String edtion1="";
	String VolSr="";
	boolean boledi=false;
	boolean bolBookSer=false;
	int countTitle=0;
	boolean checkTitle=false;
	String TitleForGlobel="";
	String Match_Ref_year="";//14/04/2008
	String Match_Ref_year1="";//14/04/2008
	String Move_Edition_In_Book_Review="";
	int count_Author;
	int count_Author_zefq;
	int total_Author_zefq;
	boolean retail_author=false;
	boolean zefq_editor=false;
	ArrayList Retail_arr=new ArrayList();
	ArrayList Prev_Retail_arr=new ArrayList();
	boolean isinfbeh_vol=false;
	ArrayList checkSpanishJidList=new ArrayList();
	//getBibTitle
	boolean isSbEdition =false;//29/01/2009
//	public int global_cutOff_aid_for_JSAMS=706;//16-04-2012
	public int global_cutOff_aid_for_JSAMS=715;//19-05-2012
	public int global_cutOff_aid_for_OSI=0;//21-06-2012
	public boolean ellipsis = false;
	public boolean ellipsisED = false;
	
	BiblographyContents()
	{
		
	}
	BiblographyContents(XMLObjects xmlObj) throws IOException
	{
		//System.out.println("ref style: "+xmlObj.bibStyle);
		this.xmlObj  = xmlObj;
		bibDate      = "";
		artTitle     = "";
		artComment   = "";
		vnr			 = "";
		journalTitle = "";
		transTitle ="";
		secTitle="";
		physt_brv="";
		socs_brv="";
		
		resusaid="";

		booksJid=new Vector();
		/*if(xmlObj.jid.equals("ENDEND"))
		{
			boolean ans=false;
			while(ans==false)
			{
				BufferedReader aa=new BufferedReader(new InputStreamReader(System.in));
				System.out.println("Please Enter Reference Style[1 or 3a only]: ");
				xmlObj.bibStyle=(String)aa.readLine();
				if((xmlObj.bibStyle.equals("1")) || (xmlObj.bibStyle.equals("3a")))
					{
						ans=true;
					}
			}
		}*/
		
		/*if(xmlObj.jid.equals("YBJOM") && Integer.parseInt(xmlObj.aid) < 2132)
		{
			xmlObj.bibStyle="3a";
		}*/
		booksJid.addElement("SSI");
		booksJid.addElement("SSCH");
		booksJid.addElement("AFEC");
		booksJid.addElement("MWP");
		if(booksJid.contains(xmlObj.jid))
			xmlObj.deviation="BOOKS";
	}
	//-----------------------------------------------------------------------------------------------------------------
	//Starting Bibliography from here. It is called for complete reference work ....
	public String artBibilography(String tagType) throws IOException
	{
		int bibCount = 0;
		String sTag = new String();
		String authBib = new String();
		String Diff_secTitle= new String();
		boolean further=false;
		boolean furtherSec=false;
		boolean Check_Diff=false;
		int bib_count=0;
		StringBuffer tempBib= new StringBuffer();
		if(tagType.equals("CE:FURTHER-READING"))
			further=true;
		boolean fpara=true;
		try
		{
			do
			{				
				char ch= (char)xmlObj.fin.read();
				if(ch == '<')
				{
					sTag = xmlObj.getTag().toUpperCase();

					if(sTag.startsWith("<CE:BIB-REFERENCE"))
					{
						nolabel = false;
						ellipsis = false;
						fpara=true;						
						artTitleExist= false;
						authorExist=false;

						if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw"))  // 2-9-04 [Ref. Style 5- Label problem of same author names and same year of two references
						{
							String ref5="";
							ref5=bibTag(xmlObj.getAttributeValue(sTag, "ID"));
							//System.out.println(sTag+"ref5 --> "+ref5);
							//System.in.read();
							if (lblsame==true)
							{
								
								if (authBib.lastIndexOf(check)>0)
								{
									String st1="";
									String st2="";
									st1=authBib.substring(0, authBib.lastIndexOf(check));
									st2=authBib.substring(authBib.lastIndexOf(check));								
									st2=st2.replaceFirst("\\(([0-9][0-9][0-9][0-9])\\)\\.", "("+check2+").");
									authBib=st1+st2;
									//System.out.println("check2 --> "+check2);
									//System.out.println("st2 --> "+st2);
									//System.in.read();
								
								}
									
								lblsame=false;
							}
							authBib = authBib + ref5;
						//	System.out.println("authBib --> "+authBib);
						//	System.in.read();

						}	
						else if ((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
						{
							String ref5="";
							
							ref5=bibTag(xmlObj.getAttributeValue(sTag, "ID"));
							//System.out.println("ref5 --> "+ref5+"check :"+check);
							//System.in.read();
							if (lblsame==true)
							{
								if (authBib.lastIndexOf(check)>0)
								{
									String st1="";
									String st2="";
									st1=authBib.substring(0, authBib.lastIndexOf(check));
									st2=authBib.substring(authBib.lastIndexOf(check));								
									st2=st2.replaceFirst("\\(([0-9][0-9][0-9][0-9])\\)\\.", "("+check2+").");
									authBib=st1+st2;
									//System.out.println("authBib --> "+authBib);
									//System.in.read();
								}
								lblsame=false;
							}
							authBib = authBib + ref5;
							//System.out.println("authBib --> "+authBib);
						//	System.in.read();

						}
						else
						{
							authBib = authBib + bibTag(xmlObj.getAttributeValue(sTag, "ID"));
							//System.out.println("authBib --> "+authBib);
							//System.in.read();
						}
						bibCount++;
						
						//System.out.println("bibCount  --> "+bibCount);
						//System.in.read();
						if(bibCount>1)//26/03/2009
						{
							temp_Reference=true;
						}
						
					}					
					else if(sTag.startsWith("<CE:FURTHER-READING-SEC"))
					{	
						furtherSec=true;
						fpara=true;
					}
					else if(sTag.equals("<CE:SECTION-TITLE>")||sTag.startsWith("<CE:SECTION-TITLE"))
					{
						
						fpara=true;
						secTitle = xmlObj.extractData("</CE:SECTION-TITLE>", true);
						Diff_secTitle=secTitle;
						//System.out.println("Diff_secTitle-->>>>>"+Diff_secTitle);
						if(Diff_secTitle.indexOf("ThomsonDiff",0) !=-1)
						{
							int in=0;
							int inn=0;
							in=Diff_secTitle.indexOf("ThomsonDiff",0);

							if(in !=-1)
							{
								inn=Diff_secTitle.indexOf("ThomsonDiffCloseBrace",in);
								if(inn !=-1)
								{
									Diff_secTitle=Diff_secTitle.substring(inn+"ThomsonDiffCloseBrace".length(),Diff_secTitle.length());
									Check_Diff=true;
								}

							}
							
						}
						if(Diff_secTitle.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0) !=-1)
						{
							int in=0;
							int inn=0;
							StringBuffer sb=new StringBuffer(Diff_secTitle);
							in=sb.indexOf("\\(\\backslash\\)protect\\(\\backslash\\)qtoa\\{",0);

							if(in !=-1)
							{
								inn=sb.indexOf("\\}",in);
								if(inn !=-1)
								{
									sb=sb.delete(in,inn+"\\}".length());
									Diff_secTitle=sb.toString();
									Check_Diff=true;
								}

							}
							
						}
						//System.out.println("2 Diff_secTitle-->>>>>"+Diff_secTitle);
						XT.refFootnote += xmlObj.footnote;
						//System.out.println("----------" +Diff_secTitle);
						//R{\ASeacute}f{\ASeacute}rences
						//if (secTitle.toLowerCase().equalsIgnoreCase("reference") || secTitle.toLowerCase().equalsIgnoreCase("references") || secTitle.toLowerCase().equalsIgnoreCase("r\\'{e}f\\'{e}rences") || secTitle.toLowerCase().equalsIgnoreCase("r\\'{e}f\\'{e}rence")|| secTitle.toLowerCase().equalsIgnoreCase("r{\\aseacute}f{\\aseacute}rences")|| secTitle.toLowerCase().equalsIgnoreCase("r{\\aseacute}f{\\aseacute}rence"))
						//System.out.println("Reference-Section1::"+Diff_secTitle);
						String refSectionTitle = Diff_secTitle;
						refSectionTitle=refSectionTitle.replaceAll("\\\\hyperlink\\{([^\\}]+)\\}\\{\\\\\\(\\^\\{\\{\\\\rmbox\\{([^\\}]+)\\}\\}\\}\\\\\\)\\}\\{\\}", "");
//						System.out.println("Reference-Section1::"+refSectionTitle);
						if(refSectionTitle.indexOf("\\footnote[")!=-1){
							refSectionTitle=refSectionTitle.substring(0, refSectionTitle.indexOf("\\footnote["));
//							System.out.println("Reference-Section2::"+refSectionTitle);
						}
						BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}"+refSectionTitle+"\\dotfill\\planref{PL"+BodyGroup.planref+"}";
						//BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}"+Diff_secTitle+"\\dotfill\\planref{PL"+BodyGroup.planref+"}";
						if (Diff_secTitle.toLowerCase().equalsIgnoreCase("reference") || Diff_secTitle.toLowerCase().equalsIgnoreCase("references") || Diff_secTitle.toLowerCase().equalsIgnoreCase("r\\'{e}f\\'{e}rences") || Diff_secTitle.toLowerCase().equalsIgnoreCase("r\\'{e}f\\'{e}rence")|| Diff_secTitle.toLowerCase().equalsIgnoreCase("r{\\aseacute}f{\\aseacute}rences")|| Diff_secTitle.toLowerCase().equalsIgnoreCase("r{\\aseacute}f{\\aseacute}rence"))
						{
							//BodyGroup.tableContents+="\r\n\\item{\\hskip18pt}"+Diff_secTitle+"\\dotfill\\quad00";//Updated on 11-05-2015 for TOC auto pagerange
							if(further==true)
							{
								secTitle="\\refnameFurther{"+secTitle+"}\\endrefnameFurther";
								further=false;
							}
							else if(furtherSec==true)
              			    {
								  secTitle="\r\n\\subsubsection{"+secTitle+"}\r\n\n";
								  furtherSec=false;
							}
							else
							{
								if(Check_Diff==false)
								secTitle="";
							}

						}
						else
						{
						  //System.out.println("Section-->"+secTitle);
						  if(further==true)
						  {
							secTitle="\\refnameFurther{"+secTitle+"}\\endrefnameFurther";
							further=false;
						  }
						  else if(furtherSec==true)
						  {
							  secTitle="\r\n\\subsubsection{"+secTitle+"}\r\n\n";
							  furtherSec=false;
						  }
						  else
						  {
							  //System.out.println("Section-->"+secTitle);
							  //
							  //if(secTitle.toLowerCase().startsWith("references"))
							  if(Diff_secTitle.toLowerCase().startsWith("references"))
							  {
									secTitle="\r\n\\section{"+secTitle+"}";
							  }
							  else
									secTitle="\r\n\\subsubsection{"+secTitle+"}\r\n\n";
						  }
						}
						if(Check_Diff==true)
						{		
							tempBib.append("\\refname{"+secTitle+"}\r\n");
							secTitle="";
						}
						authBib+=secTitle;
						//System.out.println("----------" +tempBib);
						//System.in.read();
						secTitle="";
					}
					else if(sTag.startsWith("<CE:PARA")) // 07-01-2005
					{
						String paraView=xmlObj.getAttributeValue(sTag, "VIEW");
						/*if(bibCount == 0 && secTitle.length()>0)
						{
							secTitle=secTitle.replaceFirst("subsubsection","section");
							authBib+=secTitle;

						}*/
						if(fpara==false)
							authBib+="\r\n\n";					

						if(paraView.equals("EXTENDED"))
							authBib+= "\r\n\\begin{extra}"+xmlObj.extractData("</CE:PARA>", true)+"\r\n\\end{extra}";
						else if(paraView.equals("COMPACT-STANDARD"))
							authBib+= "\r\n\\begin{antiextra}"+xmlObj.extractData("</CE:PARA>", true)+"\r\n\\end{antiextra}";
						else						
							authBib+= xmlObj.extractData("</CE:PARA>", true);

						fpara=false;

					}
				}
				
			}while(!sTag.equals("</"+tagType+">"));
			///System.out.println("Author->"+authBib);
			//StringBuffer tempBib= new StringBuffer();
			System.out.println(" ");
			System.out.println("********************************************************");
			System.out.println("\tJID                  : "+xmlObj.jid);
			System.out.println("\tAID                  : "+xmlObj.aid);
			System.out.println("\tRef Style            : "+xmlObj.bibStyle);
			System.out.println("\tLast Modify          : "+"31-03-2010");
			System.out.println("********************************************************");
			//Ref-Style:[5-Manage for MANAGE]
			if ((xmlObj.bibStyle).equals("2")||(xmlObj.bibStyle).equals("4")||(xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB"))
			{
				if (xmlObj.jid.equals("PUBREL"))
				{
					tempBib.append("\r\n\n\\checknewsgroup\n\n");
				}

				if(xmlObj.pit.equalsIgnoreCase("REV"))
					tempBib.append("\r\n\\begin{thebibliography}{}\\label{PL"+BodyGroup.planref+"}");
				else
					tempBib.append("\r\n\\begin{thebibliography}{}");
			}
			else if ((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
			{
				if(xmlObj.pit.equalsIgnoreCase("REV"))
					tempBib.append("\r\n\\begin{thebibliography}{}\\label{PL"+BodyGroup.planref+"}");
				else
					tempBib.append("\r\n\\begin{thebibliography}{}");
			}
			else if ((xmlObj.bibStyle).equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea") || (xmlObj.bibStyle).equals("yijom-ns")){
				if(xmlObj.pit.equalsIgnoreCase("REV"))
					tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}\\label{PL"+BodyGroup.planref+"}");
				else
					tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}");
			}else{
				if(xmlObj.jid.equals("RESUS") && xmlObj.aid.indexOf(".") != -1)//abhay 01/08/2006 fro duckling
					resusaid = xmlObj.aid.substring(0, xmlObj.aid.indexOf("."));
				else
					resusaid = xmlObj.aid;

				if(xmlObj.jid.equals("RESUS") && (Integer.parseInt(resusaid)) >= 2739){
					if((Integer.parseInt(resusaid)) > 3720){
						if(xmlObj.pit.equalsIgnoreCase("REV")){
							if ((xmlObj.bibStyle).equals("3a"))
								tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+"}\\label{PL"+BodyGroup.planref+"}");
							else
								tempBib.append("\r\n\\begin{thebibliography}{["+bibCount+"]}\\label{PL"+BodyGroup.planref+"}");
						}else{
							if ((xmlObj.bibStyle).equals("3a"))
								tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+"}");
							else
								tempBib.append("\r\n\\begin{thebibliography}{["+bibCount+"]}");
						}
					}else{
						if(xmlObj.pit.equalsIgnoreCase("REV")){
							tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}\\label{PL"+BodyGroup.planref+"}");
						}else{
							tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}");
						}
					}
				}else if(xmlObj.jid.equals("NRL")||xmlObj.jid.equals("NRLENG")){
					if(xmlObj.pit.equalsIgnoreCase("REV"))
						tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}\\label{PL"+BodyGroup.planref+"}");
					else
						tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}");
				}else{
					//System.out.println("------------------------\n"+HeadGroup.artAbsPIO);
					if(xmlObj.jid.equals("PIO")){
						tempBib.append("\r\n\\begin{PIOstartabstract}\r\n");
						tempBib.append(HeadGroup.artAbsPIO+"\r\n");
						HeadGroup.artAbsPIO=new StringBuffer();
						tempBib.append("\r\n\\end{PIOstartabstract}\r\n");
					}
					else if(xmlObj.jid.equals("MLA")){
						tempBib.append("\r\n\\begin{MLAstartabstract}\r\n");
						//System.out.println("HeadGroup.artAbsPIO:: "+HeadGroup.artAbsPIO);
						if(HeadGroup.artAbsPIO.indexOf("\\ENGcopyright",0)!=-1)
						{
							int s=0;
							int e=0;
							s=HeadGroup.artAbsPIO.indexOf("\\ENGcopyright",e);
							if(s!=-1)
							{
								e=HeadGroup.artAbsPIO.indexOf("\\end{abstract}",s);
								if(e!=-1)
								{
									HeadGroup.artAbsPIO=HeadGroup.artAbsPIO.delete(s,e);
								}
							}

						}
						tempBib.append(HeadGroup.artAbsPIO+"\r\n");
						tempBib.append(HeadGroup.artKwdFranch_MLA+"}\r\n");
						//System.out.println("HeadGroup.artAbsPIO:: "+HeadGroup.artAbsPIO);
						HeadGroup.artAbsPIO=new StringBuffer();
						HeadGroup.artKwdFranch_MLA=new StringBuffer();
						tempBib.append("\r\n\\end{MLAstartabstract}\r\n");
					}
					if ((xmlObj.bibStyle).equals("3a")){
						if(xmlObj.pit.equalsIgnoreCase("REV"))
							tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}\\label{PL"+BodyGroup.planref+"}");
						else
							tempBib.append("\r\n\\begin{thebibliography}{"+bibCount+".}");
					}else{
						if(xmlObj.pit.equalsIgnoreCase("REV"))
							tempBib.append("\r\n\\begin{thebibliography}{["+bibCount+"]}\\label{PL"+BodyGroup.planref+"}");
						else
							tempBib.append("\r\n\\begin{thebibliography}{["+bibCount+"]}");
					}
				}
			}
			//ravi
      		tempBib.append(authBib +"\\end{thebibliography}");
			if (authBib.indexOf("\\bibitem")!=-1) //07-01-2005
			{				
	    		authBib= tempBib.toString();
				//System.out.println(authBib);
				int x=authBib.indexOf("\\bibitem");
				int y=-1;
				if(x!=-1)
				{
					y=authBib.indexOf("\\bibitem",x+1);
				}
				if(y==-1 && authBib.indexOf("\\refnameFurther{")==-1)
				{
					//System.out.println("Language : "+XT.articleType);
					if(XT.articleType.equalsIgnoreCase("fr"))
						authBib="\r\n\\refname{R{\\ASeacute}f{\\ASeacute}rences}"+authBib;
					else
						authBib="\r\n\\refname{Reference}"+authBib;
					
				}
				

			}
			else
			{
				authBib= tempBib.toString();
			}
	 	}
		catch(Exception exp)
		{
			XTLogger.getInstance().info("[ERROR] ."+exp.toString());
			exp.printStackTrace();
			System.exit(0);
		}
		//System.out.println("BEFORE --------- authBib-----> "+authBib);
		authBib=authBib.replaceAll("[ ]+;",";");		
		authBib=authBib.replaceAll("\\{\\\\it\\{\\}\\}","");
		authBib=authBib.replaceAll("\\, \\,",",");
		authBib=authBib.replaceAll("  "," ");
		authBib=authBib.replaceAll(" \\.","\\.");
		authBib=authBib.replaceAll("  \\.","\\.");
		authBib=authBib.replaceAll(" \\;","\\;");
		authBib=authBib.replaceAll("\\,\\,",",");
		authBib=authBib.replaceAll("\\.\\.","\\.");
		authBib=authBib.replaceAll("\\?\\.","\\?");
		authBib=authBib.replaceAll("\\.\\}\\}\\.","\\.\\}\\}");
		authBib=authBib.replaceAll("\\?}}\\.","\\?}}");
		authBib=authBib.replaceAll("[ ]+"," ");


		if(authBib.indexOf("\\refnameFurther{")!=-1)
		{
			authBib="\r\n\n\\refname{"+authBib.substring(authBib.indexOf("\\refnameFurther{")+"\\refnameFurther{".length(),authBib.indexOf("\\endrefnameFurther"))+"\r\n"+authBib.substring(0,authBib.indexOf("\\refnameFurther{"))+authBib.substring(authBib.indexOf("\\endrefnameFurther")+"\\endrefnameFurther".length());
		}
		authBib = authBib.replaceAll("\\\\refname\\{\\}","");

//		System.out.println("LAST --------- authBib-----> "+authBib);
		//System.in.read();
		return authBib;
	}
	//--------------------------------------------------------------------------------------
	
	private String bibTag(String id) throws IOException
	{
		String sTag = new String();
		String eTag = new String();
		String bib = new String();
		String no = new String();
		String sbRefID  = "";//Updated for sbRefID Web 6.4 updation 20-03-2013 by Vivek
		boolean first = false;
		boolean bibFlag = false;
		String temp = new String();
		String link = xmlObj.getHypertarget(id);
		//System.out.println("\n\n\n link--  >  "+link);
		//	System.in.read();
		/*Hashtable ht=new Hashtable();
		do
		{
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				stTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<CE:LABEL>"))
				{
					String lbl=xmlObj.extractData("</CE:LABEL>", true);
					String auths=xmlObj.extractData("</SB:AUTHORS>", true);
					lbl=lbl.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					auths=auths.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					ht.put(lbl,auths);
				}
			}
		}
		while (!sTag.equals("</CE:BIB-REFERENCE>"));
		System.out.println("Hashtable: "+ht);*/
		do
		{
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				
				if(sTag.equals("<CE:LABEL>"))
				{
					tempLabel=""; // 15-07-2004
					no = xmlObj.extractData("</CE:LABEL>", true);
//					System.out.println(" no :::: "+no);
                 	tempLabel=no.substring(no.lastIndexOf(',')+2);  // 15-07-2004	
					tempLabel=tempLabel.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					//System.out.println("tempLabel "+tempLabel);
					String temp1="";  // 05-09-08  mukesh
					temp1=no;
					//temp1=temp1.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					if(temp1.indexOf(',')!=-1)
					{
						temp1=temp1.substring(0,temp1.lastIndexOf(','));//04/09/2008
					}
					
					//if(!no.substring(0,no.length()-1).trim().equals(Match_Ref_year.trim()))//14/04/2008
					//System.out.println(temp1+" ---- "+Match_Ref_year);
					//System.in.read();
					

					if(!temp1.trim().equals(Match_Ref_year.trim()))//14/04/2008
					{
						//Match_Ref_year=no.substring(0,no.length()-1).trim();
						Match_Ref_year=temp1;
						retail_author=true;
						count_Author=1;
						
					}
					else
					{
						retail_author=true;
						++count_Author;
					}
					
					//end //14/04/2008
                 	//System.out.println("tempLabel : "+tempLabel+" count_Author : "+count_Author);
					//System.out.println("1 Match_Ref_year : "+Match_Ref_year+"\n1 no.substring(0,no.length()-1) : "+no.substring(0,no.length()-1));
				}
				else if(sTag.startsWith("<SB:REFERENCE"))
				{
					//Updated for sbRefID Web 6.4 updation 20-03-2013 by Vivek
					sbRefID=xmlObj.getAttributeValue(sTag, "ID");
					//System.out.println("\nsbRefID==>"+xmlObj.getAttributeValue(sTag, "ID"));
					//End of Web 6.4 updation
					journalTitle="";    
					if (bibFlag)
					{
						if (temp.endsWith(".")|| temp.endsWith("?")||temp.endsWith(","))
						{
							temp=temp.substring(0,temp.length()-1);
						}
						if(temp.endsWith(":"))
						{	
							//bib = bib +temp+"\\\\\r\n";//old
							//[Added By Ravi 21/12/2006]
							//Change Request By Vivek
							bib = bib +temp+"\\newline\r\n";
						}
						else
						{
							//bib = bib +temp+";\\\\\r\n";//old
							//[Added By Ravi 21/12/2006]
							//Change Request By Vivek
							bib = bib +temp+";\\newline\r\n";
						}
					}
					else
						bibFlag = true;
					
					temp = sbReference(first);
					//System.out.println("\n\n\n temp--  >  "+temp);
					//System.in.read();
					temp=temp.replaceAll(", ,",",");// 20-11-04
					if ((xmlObj.bibStyle).equals("1"))
					{
						temp=temp.substring(0,temp.length()-1);
						//System.out.println("temp-----"+temp);
					}
					first = true;
				}
				else if(sTag.startsWith("<CE:OTHER-REF"))
				{
					if (bibFlag)
					{
						if(temp.endsWith(":"))
							bib = bib +temp+"\\\\\r\n";
						else
							bib = bib +temp+";\\\\\r\n";
					}
					else
					{
						bibFlag = true;
					}					
					temp = processOtherRef();	
					//System.out.println("temp====>>"+temp);
				}
				else if(sTag.equals("<CE:NOTE>"))
				{
					if (temp.length()>0)
					{
						temp=temp.replaceAll("  "," ");					
						if(temp.endsWith(" "))
							temp=temp.substring(0,temp.length()-1);					


						if(!temp.endsWith("."))
							temp=temp+".";

					}
					bib = bib +temp+ noteTag();
					temp="";
				}
			}
			
		}while(!sTag.equals("</CE:BIB-REFERENCE>"));
		//System.out.println("\n\n\n Prev_Retail_arr--  >  "+Prev_Retail_arr.size());
		//System.out.println("\n\n\n Retail_arr--  >  "+Retail_arr.size());
		//System.in.read();
		if(Prev_Retail_arr.size()==Retail_arr.size())
					{
						ArrayList ar=new ArrayList();
						if(Prev_Retail_arr.containsAll(Retail_arr))
						{
							//System.out.println("1 if I am here--  >  "+Prev_Retail_arr+"\n Retail_arr "+Retail_arr);
						}
						else
						{
							//System.out.println("2 if I am here--  >  "+Prev_Retail_arr+"\n Retail_arr "+Retail_arr);
							ar.addAll(Prev_Retail_arr);
							Prev_Retail_arr.clear();
							Prev_Retail_arr.addAll(Retail_arr);
							ar.clear();
							
						}
						//Prev_Retail_arr.clear();
						
						
					}
					else
					{
						Prev_Retail_arr.clear();
						Prev_Retail_arr.addAll(Retail_arr);
						//System.out.println("I am here----------------->");
					}
					Retail_arr.clear();
					//System.in.read();	
		bib = bib + temp;
		bib = bib.trim();
		//System.out.println("\n\n\n bib--  >  "+bib);
		//System.in.read();
		if(bib.startsWith(". In: ") || bib.startsWith(". in: "))
		{
			bib = bib.substring(6);
		}

		if (!temp.endsWith("."))
		{
			if(temp.endsWith(".)"));
			if (temp.startsWith("\\mychar\\url{") && temp.endsWith("}{}")) // 29-07-04
			{
				bib+="";
			}
			else
			{
				//bib+=".";//old
				//Added By Ravi [11/12/2006 Change Request By Vivek]
				//Change Point : for Ref Style 5 Contain url in comment[ no . (dot) required]
				if((xmlObj.booInterRef==true) &&( xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
				{
					bib+="";
					xmlObj.booInterRef=false;
				}
				else if((intref==true ||boldoi==true) && (xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
				{
					bib+="";
					intref=false;
					boldoi=false;
				}
				else
				{
					//System.out.println("temp--> "+temp);
					//System.in.read();
					/*if(bib.lastIndexOf("qtoa\\{") != -1)
					{
						bib+="";
					}
					else
					{
						bib+=".";
					}
					*/
					/*java.util.regex.Pattern pat=java.util.regex.Pattern.compile("qtoa\\\\\\{[^}]+\\}$");
					java.util.regex.Matcher mat=pat.matcher(bib);
					if(!mat.find())	*/
					int st=bib.indexOf("qtoa\\{");// Added by mukesh on 18-09-08 as extra dot appears in case of qtoa{
					if(st!=-1)
					{
						st=bib.indexOf("}",st);
						//System.out.println (st+" >>> "+bib.trim().length());
						if(st!=bib.trim().length()-1)
							bib+=".";
					}
					else
					{
						if(xmlObj.jid.equals("PROTIS"))
						{

						}
						else
						{
							
							if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)&& bib.endsWith("}{}"))
							{
								//19-10-2011
								//When a reference finished with an electronic link, dot at the end is not allowed for Spanish Reference Journal style.
							}
							else
							bib+=".";
							//System.out.println (" >>> "+bib);
							//System.in.read();
						}
						
					}
					
					
				}
				//end
			//System.out.println(bib);
			}
		}

		if (bib.endsWith(",."))
		{

			bib= bib.substring(0,bib.length()-2)+".";
		}
		if ((xmlObj.bibStyle).equals("DDT-NS") && bib.endsWith("."))
		{
			bib= bib.substring(0,bib.length()-1);
		}

		if(xmlObj.jid.equals("RESUS") && xmlObj.aid.indexOf(".") != -1)//abhay 01/08/2006 for duckling
			resusaid = xmlObj.aid.substring(0, xmlObj.aid.indexOf("."));
		else
			resusaid = xmlObj.aid;

		 if (xmlObj.bibStyle.equals("3a")||xmlObj.bibStyle.equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea") || (xmlObj.bibStyle).equals("yijom-ns") ||xmlObj.bibStyle.equals("6") || (xmlObj.jid.equals("RESUS") && ((Integer.parseInt(resusaid)) >= 2739 && (Integer.parseInt(resusaid)) < 3721)))//Gaurav 20-08-04.//Resus updated on 24-01-2018
			{
				//System.out.println("22no=====>>"+no);
				if(no.endsWith(".")|| no.endsWith(". "))
				{
					System.out.println("CRITICAL ERROR---REMOVE DOT FROM <CE:LABEL> IN Ref Sty. 3a & 6. & RESUS(Ref.6)");
					System.exit(0);
				}
				if(no.equals(""))
					bib ="\r\n\\bibitem{}\r\n"+link+bib+"\r\n";
				else
					bib ="\r\n\\bibitem{"+no+".}\r\n"+link+bib+"\r\n";
			}
			else
			  {
					if(xmlObj.jid.equals("NRL"))
					{
						no=no+".";
					}
					if(xmlObj.jid.equals("APPR")){
						bib ="\r\n\\bibitem{"+no+".}\r\n"+link+bib+"\r\n";
					}else{
						bib ="\r\n\\bibitem{"+no+"}\r\n"+link+bib+"\r\n";
					}
				//System.out.println("bib --> "+bib);
				//System.in.read();
				}
		 link="";
		bib=secTitle+bib;
		secTitle="";
		//System.in.read();

		if(!sbRefID.equals(""))
		{
			if((bib.indexOf("\\url{"))==-1 && (bib.indexOf("\\doi{"))==-1 && (bib.indexOf("\\newtextdoi{"))==-1 && (bib.indexOf("\\newdoi{"))==-1)
			{
				boolean refno=false;
				String bibno=no;
				//System.out.println("No==>\""+bibno+"\"");
				bibno=bibno.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
				//System.out.println("No==>"+bibno);
				if(no.equals("")){
					nolabel=true;
					refno=false;
				}else{
					if(bibno.startsWith("[")||bibno.startsWith("("))
					{
						refno=Character.isDigit(bibno.charAt(1));
					}
					else
					{
						refno=Character.isDigit(bibno.charAt(0));
					}
				}
		
				if(refno)
				{
					bib ="\r\n\\linkRefId{"+sbRefID.toLowerCase()+"}\\global\\linkoccurtrue"+bib.trim();
				}
				else
				{
					if(no!=null)
					{
						Pattern p1 = Pattern.compile("([0-9][0-9][0-9][0-9])");
						Matcher m1 = p1.matcher(no);
						String sbyear="";
						if(m1.find())
						{
							sbyear = m1.group(1);
							//System.out.println("NO==> "+no);
							Pattern p2 = Pattern.compile(sbyear);
							Matcher m2 = p2.matcher(bib);
							if(m2.find())
							{
								//System.out.println("bib1111 "+bib);
								//System.out.println("Year "+sbyear+" found in reference");
								bib=bib.replaceFirst("\\. \\.","\\.");
								bib=bib.replaceFirst(sbyear,"<<\\\\sbyear>>");
								if(xmlObj.bibStyle.equals("4"))
								{
									//bib=bib.replaceFirst(sbyear+"([^ ]+) ",sbyear+"$1\r\n \\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n ");
									bib=bib.replaceFirst("et al\\.","et al<<\\\\REFDOT>>");
									//bib=bib.replaceFirst("\\.","\\.\r\n \\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n ");//15-06-2013
									//bib=bib.replaceFirst("\\.","\\.\r\n \\\\linkRefsubId{"+sbRefID.toLowerCase()+"}");
									bib=bib.replaceFirst("\\.","\\.\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
									
//									if(xmlObj.jid.equals("PROTIS")){
//										bib=bib.replaceFirst("\\(([0-9][0-9][0-9][0-9])([a-z]?)\\)","\\($1$2\\)\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
//									}else{
//										bib=bib.replaceFirst("\\.","\\.\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
//									}


//									int dotInd = 0;
//									int paranthInd = 0;
//									if(bib.indexOf(".")!=-1){
//										dotInd = bib.indexOf(".");
//									}
//									if(bib.indexOf(")")!=-1){
//										paranthInd = bib.indexOf(")");
//									}
//									if(dotInd>paranthInd){
//										bib=bib.replaceFirst("\\(([0-9][0-9][0-9][0-9])([a-z]?)\\)","\\($1$2\\)\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
//									}else{
//										bib=bib.replaceFirst("\\.","\\.\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
//									}
										
									
									bib=bib.replaceFirst("<<\\\\REFDOT>>","\\.");
									System.out.println("BIB :: "+bib);
								}
								else
								{
									//bib=bib.replaceFirst(sbyear+"([^ ]+) ",sbyear+"$1\r\n \\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n ");//15-06-2013
									//bib=bib.replaceFirst(sbyear+"([^ ]+) ",sbyear+"$1\r\n \\\\linkRefsubId{"+sbRefID.toLowerCase()+"}");
									bib=bib.replaceFirst(sbyear+"([^ ]+) ",sbyear+"$1\\\\linkRefsubId{"+sbRefID.toLowerCase()+"}\r\n");
								}
								bib=bib.replaceFirst("<<\\\\sbyear>>",sbyear);
								bib=bib.replaceFirst("<<\\\\REFDOT>>","\\.");
								//System.out.println("bib2222 "+bib);
							}
						}
						bib ="\r\n\\global\\linkoccurtrue"+bib.trim();
					}
				}

				if(bib!=null)
				{
					if((bib.indexOf("\\linkRefId")!=-1)||(bib.indexOf("\\global\\linkoccurtrue")!=-1))
					{
						bib+="\\global\\linkoccurfalse\n";
					}
				}
				else
				{
					System.out.println("\n\n\n bib ====>  ((("+bib+")))");
				}
				//System.out.println("\n\n\n bib ====>  ((("+bib+")))");
			}
		}
		return bib;
	}
//-------------------------------------------------------------------------------------------
	
	public String processOtherRef()throws IOException
	{
		String tag      = "";
		String otherRef = "";
		String otherRefLabel = "";
		String url="";
		while (!tag.equals("</CE:OTHER-REF>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>"))
				{
					otherRefLabel= xmlObj.extractData("</CE:LABEL>", true);
				}
				else if (tag.equals("<CE:TEXTREF>")||tag.startsWith("<CE:TEXTREF"))
				{
					otherinterref=true;
					if(otherRefLabel.length()>0)
					{
					      otherRef+=otherRefLabel+" ";
					}

					otherRef= otherRef+xmlObj.extractData("</CE:TEXTREF>", true);
					//System.out.println("otherRef : "+otherRef);
					//System.in.read();
					otherinterref=false;
				}
			}
		}
		
		return otherRef;
	}
	//-------------------------------------------------------------------------------------------------------
	public String sbReference(boolean first) throws IOException
	{
		//count_Author=0;
		global.pub_for_ref5="";    // 27-07-04
		global.pub_for_ref5Manage="";
		String sTag = new String();		
		String bb = new String();
		boolean comment_after_host = false;//[17-07-04 ----- Rajeev &Gaurav ]
		String firstComment=""; // 27-07-04
		isEditor=false; //29-07-04
		artTitle="";
		bsTitle="";
		bsVol="";
		ebTitle="";
		bTitle="";
		artComment   = "";
		artTitleExist=false;
		authorExist= false; // 31-08-04
		global.cont_avail="no";
		int hostCount = 0;
		isMultipleEditors= false;
		isbookpage=false;
		isbookpagewithedition=false;
		isVolinBookSeries=false;
		global.bookpage="";
		mulHost = false;
		mulhostDate="";
		physt_brv="";
		int count_comment=0;
		boolean noContib = false;//abhay
		check=""; // variable for Ref. Style 5- Label problem. 2-9-04
		do
		{
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				sTag= xmlObj.getTag().toUpperCase();				
			     //-------------------------------------------------------------------------------------
				//sb:reference
				//[ce:label -> sb:comment -> sb:contribution -> sb:comment -> sb:host -> sb:comment]
				//-------------------------------------------------------------------------------------
				if(sTag.equals("<CE:LABEL>"))
				{					
					if(first)
					{
						bb = bb +xmlObj.extractData("</CE:LABEL>", true)+" ";
					}
					else
					{
						bb = bb +xmlObj.extractData("</CE:LABEL>", true)+" ";
						first = true;
					}
					
				}
				//---------------------------------------------------------------------------
				else if(sTag.equals("<SB:CONTRIBUTION>")||sTag.startsWith("<SB:CONTRIBUTION "))
				{
					bb = bb + firstComment + " ";  // 27-07-04
					global.cont_avail="yes";
					isContribution = true;
					
					bb = bb + sbContribution();
					bb=bb.trim();
					if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
					{
						bb+=" ";
					}
					//System.out.println("11:"+bb+":"+count_Author);
					//System.in.read();
                   	firstComment = "";	// 27-07-04
					
					noContib = true;
				}
				//---------------------------------------------------------------------------------------
				else if(sTag.equals("<SB:HOST>"))
				{
					if(firstComment.length() > 0 && noContib == false && comment_after_host == false)
					{
						bb = bb + firstComment + " ";
						firstComment = "";
					}
					
					isContribution=false;
					hostCount++;
					isMultipleEditors=false;
					sTag = bibHost();
					//System.out.println("sTag -->"+sTag);
					//System.in.read();
					if(checkTitle==false)
					{
						if((xmlObj.jid.equals("PUBREL"))&&(xmlObj.pit.equalsIgnoreCase("BRV"))&&(xmlObj.bibStyle.equals("brvhead")))
						{
						int in =0;
						in = bb.lastIndexOf(",");
						String firstpart="";
						String lastpart="";
						if(in != -1)
						{
							firstpart=bb.substring(0,in);
							lastpart=bb.substring(in,bb.length());
							bb="{\\bi{"+firstpart.trim()+"}}"+lastpart;
							//System.out.println(" firstpart --> "+firstpart);
							//System.out.println(" lastpart --> "+lastpart);
							//System.in.read();

						}
						//System.out.println(" else bb --> "+bb);
						//System.in.read();
						}
					}

					if(sTag.startsWith(" ,"))
					{
							
						sTag = sTag.substring(1);
						//System.out.println("sTag -->"+sTag);
					//System.in.read();
					
					}
					else if(sTag.startsWith(" ?, in:")||sTag.startsWith("?, in:"))//05-08-2004
					{
						if(bb.endsWith(","))
						{
							sTag = sTag.substring(3);
						}
						else
						{
							sTag = sTag.substring(2);
						}
					
					}
					else if(sTag.startsWith("L, "))
					{
						sTag = sTag.substring(1);
					}
					//System.out.println("mulHost===>>"+mulHost);
					if(mulHost==true)
					{
				
						if(sTag.startsWith(", "))
						{
							sTag = sTag.substring(1);
						}
						else if(!sTag.startsWith(" "))
						{
							sTag = " "+sTag;
						}
						if(bb.endsWith(".  "))
						{
							bb= bb.substring(0,bb.length()-3);
						}
					}
					if((bb.endsWith(",")||bb.endsWith(", ")) && sTag.startsWith(","))
					{
							sTag = sTag.substring(1);
								
					}
					if(bb.endsWith(".") && sTag.startsWith(".")) // [05-08-04]
					{
							sTag = sTag.substring(1);
					}
					if ((bb.endsWith("?")) && sTag.startsWith(",")) // Rajeev-- 20-8-2004--On Sangita FeedBack For SNB 8009--
					{
						sTag=sTag.substring(1);						
					}
					if((xmlObj.bibStyle.startsWith("5") ||( xmlObj.bibStyle.startsWith("IChemE")))&& sTag.startsWith(", "))
					{
						sTag=sTag.substring(1,sTag.length());
						bb = bb + sTag;
						
					}
					else
					{
						//tempDateForSYAPM

						if(xmlObj.jid.equals("SYAPM"))
						{
							bb=bb.replaceAll("<SYAPM>",tempDateForSYAPM+" ");
							bb = bb + sTag;
							tempDateForSYAPM="";
						}
						else if(xmlObj.jid.equals("PROTIS"))
						{
							//System.out.println("tempDateForSYAPM "+tempDateForSYAPM);
							bb=bb.replaceAll("<PROTIS>",tempDateForSYAPM);
							bb=bb.replaceAll("\\{ndash}","{\\\\ndash}");
							bb = bb + sTag;
							tempDateForSYAPM="";
						}
						else
						{
							//bb = bb +" "+sTag;
							bb = bb + sTag;
						}

						//bb = bb + sTag;//old
						//System.out.println(" else bb --> "+bb+"\nsTag : "+sTag);
						//System.in.read();
					}
					//bb = bb + sTag;
                	comment_after_host=true; //[17-07-04 ----- Rajeev]
					//System.out.println("bb --> "+bb);
					//System.in.read();
                	
			}
   //-------------------------------------------------------------------------------------------------
			else if(sTag.equals("<SB:COMMENT>"))
			{
				String artComment_temp_diff="";
				String temp="";
				++count_comment;
				temp=xmlObj.extractData("</SB:COMMENT>", true);
				artComment_temp_diff=temp;
				artComment_temp_diff=artComment_temp_diff.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
				//System.out.println("temp -->"+temp_diff);
				//System.in.read();
				firstComment = temp;  // 27-07-04 
				//noContib = false; // Added By ravi 01/11/2006
				
				//-------------------------------------------------------------------------------------------------	
				if (xmlObj.bibStyle.equals("1")|| xmlObj.bibStyle.equals("1a")|| xmlObj.bibStyle.equals("1b")) 
				{
				
					if(isContribution == true)
					{
						artComment = temp;
						if(comment_after_host == false)
						{	
				    		if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}
								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+", "+artComment; 
								}

								else
								{
									bb=bb+", "+artComment; 
								}
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
					else
					{
						artComment = temp;
						if(comment_after_host==true)
						{	
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+", "+artComment; 
								}

								else
								{
									bb=bb+", "+artComment; 
								}
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
				}//end of ref style 1	
///////////////////////////////////////////////////////////////////////////////////////

				else if (xmlObj.bibStyle.equals("brvhead"))
				{
					//System.out.println("MMMMMMMMMMM");
					/*
						if(isContribution == true)
						{
							artComment = temp;
							bb=bb+". "+artComment;
							artComment="";
							temp="";
							isContribution = false;
						}
						else
						{
							artComment = temp;
							if(comment_after_host==true)
							{	
								bb=bb+". "+artComment;
								artComment="";
							}
							temp="";
							isContribution = false;
						}
						if(global.isbn1.length() > 0)//abhay 19.5.2006
						{
								bb += ", "+"ISBN: "+global.isbn1;
								global.isbn1 = "";
						}
					*/
				//added By ravi [10/11/2006 change by Vivek]
				if(xmlObj.jid.equals("PHYST"))
				{
			
					if(isContribution == true)
					{
						//System.out.println("MMMMMMMMMMM");
						artComment = temp;
						bb=bb.trim()+", "+artComment;
						
						artComment="";
						temp="";
						isContribution = false;
					}
					else
					{
						
						artComment = temp;
						if(comment_after_host==true)
						{
							//System.out.println("KKKKKKKKKK"+artComment);
							bb=bb+", "+artComment;
							//System.out.println("bbbbbbbb"+bb);
							artComment="";
						}
						temp="";
						isContribution = false;
					}
				//System.out.println("KKKKKKKKKK"+artComment);
					if(global.isbn1.length() > 0)
					{
						//System.out.println("JJJJJJJJJJJJJ");
							//bb += ", "+"ISBN: "+global.isbn1;//old//27/11/2007
							/**
							* Date : 27/11/2007
							* Added By : Ravi 
							* Change Point : In Book Review PHYST ISBN should be without colon
							* Change Request : TMPS.
							*/
							bb += ", "+"ISBN "+global.isbn1;
							//end
							global.isbn1 = "";
					}
					
				}
				else
				{
						if(isContribution == true)
						{
							artComment = temp;
							bb=bb+". "+artComment;
							artComment="";
							temp="";
							isContribution = false;
							//System.out.println("bb--------- "+bb);
						}
						else
						{
							artComment = temp;
							if(comment_after_host==true)
							{	
								bb=bb+". "+artComment;
								artComment="";
							}
							temp="";
							isContribution = false;
							//System.out.println("bb--------- "+bb);
						}
					if(global.isbn1.length() > 0)//abhay 19.5.2006
					{
							bb += ", "+"ISBN: "+global.isbn1;
					
							global.isbn1 = "";
					}
					
				}
					
				}

		//------------------------------------------------------------------
				else if (((xmlObj.bibStyle).equals("2")) )
				{
					if(isContribution == true)
					{
						artComment = temp;
						if (bb.endsWith("."))
						{
							bb= bb.substring(0,bb.length()-1);
							bb=bb+", "+artComment; 
						}
						else
						{
							bb=bb+", "+artComment; 
						}
						temp="";
						isContribution = false;
					}
					else
					{
							artComment = temp;
							if(comment_after_host==true)
							{	
								if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
								{
									if (bb.endsWith("."))
									{
										bb=bb.substring(0,bb.length()-1);
										bb=bb+" "+artComment; 
									}
									else
									{
										bb=bb+" "+artComment; 
									}
								}
								else
								{	
									if (bb.endsWith("."))
									{
										bb=bb.substring(0,bb.length()-1);
							     		bb=bb+", "+artComment; 
									}
									else
									{
										bb=bb+", "+artComment; 
									}
								}
								artComment="";
						}
						temp="";
						isContribution = false;
					}
					artComment = "";
				}
//-------------------------------------------------------------------
				else if((xmlObj.bibStyle).equals("3-ZEFQ") )
				{
					//System.out.println("1 "+temp);
					if(isContribution == true)
					{
						//System.out.println("2 ");
						artComment = temp;
						temp="";
						if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
						{
							//System.out.println("3 ");
							if (bb.endsWith("."))
							{
								bb= bb.substring(0,bb.length()-1);
								bb=bb+" "+artComment; 
							}
							else
							{
								bb=bb+" "+artComment; 
							}
						}
						else
						{//System.out.println("4 ");
							if (bb.endsWith("."))
							{
								bb= bb.substring(0,bb.length()-1);
								bb=bb+", "+artComment; 
							}
							else
							{
								bb = bb.trim();
								bb=bb+", "+artComment;
							}
						}
						artComment="";//19-07-04
						isContribution = false;
					}
					else
					{//System.out.println("5 ");
						artComment = temp;
						if(comment_after_host==true)
						{	//System.out.println("6 ");
						    if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{//System.out.println("7 ");
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}
							/*
								Date  : 31/01/2008
								Remarks : Due to diff tag, comment is not appreaing in tex file
							*/
							else if(artComment.startsWith("ThomsonDiff") || artComment.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{//System.out.println("7 ");
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}		
							else 
							{//System.out.println("8 ");
								if (artComment.charAt(0)>=65 &&  artComment.charAt(0)<=96)//Gaurav -11/13/04
								{
										bb=bb+". "+artComment;
								}
								else if (bb.endsWith("."))
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+" "+artComment; 
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment; 
									}
								}
								else
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+". "+artComment; 
									}
									else
									{										
										bb=bb+", "+artComment; 
									}
								}
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
				}

		//-------------------------------------------------------------------
	//-------------------------------------------------------------------
				else if((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6"))
				{
					//System.out.println("1 "+temp);
					if(isContribution == true)
					{
						//System.out.println("2 ");
						artComment = temp;
						temp="";
						if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
						{
							//System.out.println("3 ");
							if (bb.endsWith("."))
							{
								bb= bb.substring(0,bb.length()-1);
								bb=bb+" "+artComment; 
							}
							else
							{
								bb=bb+" "+artComment; 
							}
						}
						else
						{//System.out.println("4 ");
							if (bb.endsWith("."))
							{
								bb= bb.substring(0,bb.length()-1);
								bb=bb+", "+artComment; 
							}
							else
							{
								bb = bb.trim();
								bb=bb+", "+artComment;
							}
						}
						artComment="";//19-07-04
						isContribution = false;
					}
					else
					{//System.out.println("5 ");
						artComment = temp;
						if(comment_after_host==true)
						{	//System.out.println("6 ");
						    if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{//System.out.println("7 ");
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}
							/*
								Date  : 31/01/2008
								Remarks : Due to diff tag, comment is not appreaing in tex file
							*/
							//else if(artComment_temp_diff.startsWith("ThomsonDiff") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							else if(artComment_temp_diff.startsWith("ThomsonDiff") || artComment_temp_diff.startsWith("("))//Check artComment without diff tag//10-12-2013
							{//System.out.println("7 ");
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}		
							else 
							{//System.out.println("8 ");
								if (artComment_temp_diff.charAt(0)>=65 &&  artComment_temp_diff.charAt(0)<=96)//Gaurav -11/13/04
								{
										bb=bb+". "+artComment;
										//System.out.println("artComment_temp_diff===>"+artComment_temp_diff);
										//System.out.println("artComment=============>"+artComment);
								}
								else if (bb.endsWith("."))
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+" "+artComment; 
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment; 
									}
								}
								else
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										if(xmlObj.jid.equals("IMR"))
										{
											bb=bb+", "+artComment; 
										}
										else
											bb=bb+". "+artComment; 
									}
									else
									{										
										bb=bb+", "+artComment; 
									}
								}
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
					//System.out.println("------------->> "+bb);
				}

		//-------------------------------------------------------------------
				else if (xmlObj.bibStyle.equals("yijom-ns"))
				{
					if(isContribution == true)
					{
						artComment = temp;
						temp="";
						isContribution = false;
					}
					else
					{
						artComment = temp;
						if(comment_after_host==true)
						{	
						    if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}																
							else 
							{
								if (artComment.charAt(0)>=65 &&  artComment.charAt(0)<=96)
								{
										bb=bb+". "+artComment;
								}
								else if (bb.endsWith("."))
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+" "+artComment; 
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment; 
									}
								}
								else
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+". "+artComment; 
									}
									else
									{										
										bb=bb+""+artComment;  //Gaurav-- 4-10-2004--Based on Kavita Article 613. 
									}
								}
							}
							artComment="";//19-07-04
						}
					temp="";
					isContribution = false;
					}
				}
			//----------------------------------------------------------------------------------------
				else if ((xmlObj.bibStyle).equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea") )
				{
					
					if(isContribution == true)
					{
						artComment = temp;
						temp="";
						isContribution = false;
					}
					else
					{
						artComment = temp;
						if(comment_after_host==true)
						{	
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}

								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (bb.endsWith("."))
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+" "+artComment; 
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment; 
									}									
								}
								else
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+", "+artComment;//16-08-04(Gaurav for jecs5036 According to Ajay--) 
									}
									else
									{										
										bb=bb+", "+artComment; 
									}
								}
							}
							
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
				}
				//-----------------------------------------------------------------------------------
				else if ((xmlObj.bibStyle).equals("4"))
				{
					if(isContribution == true)
					{
						artComment = temp;
						temp="";
						bb=bb+" "+artComment; 
						isContribution = false;
					}
					else
					{
						artComment = temp;
						if(comment_after_host==true)
						{	
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith("."))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}
								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (bb.endsWith("."))
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										bb=bb+" "+artComment; 
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment; 
									}									
								}
								else
								{
									if (artComment.startsWith("p. ") || artComment.startsWith("pp. "))
									{
										if(xmlObj.jid.equals("PROTIS"))//05-07-2010
										{
											bb=bb+", "+artComment;
										}
										else
										bb=bb+". "+artComment;
									}
									else
									{										
										bb=bb+", "+artComment; 
									}
								}
							}
							
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
				}
				//-------------------------------------------------------------------------------------
				//Kishor [19/06/2004]
				else if (((xmlObj.bibStyle).equals("5"))||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
				{
					
				//System.out.println("hhhhhhhhhhhhhhh");
					if(isContribution == true)
					{
						artComment = temp;
						
						
						tempone=temp;	
						temp="";
						//bb = bb + " " + artComment; 
						isContribution = false;
						
					}
					else
					{
						artComment = temp;
						
						
						if(comment_after_host==true)
						{								
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								
								if (bb.endsWith("..")) //13-08-04--Gaurav & Rajeev
								{
									bb= bb.substring(0,bb.length()-2);
									bb=bb+" "+artComment; 
								}
								else if (bb.endsWith(".") || bb.endsWith(",")) //13-08-04--Gaurav & Rajeev
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}
								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (artComment.charAt(0)>=65 &&  artComment.charAt(0)<=96)
								{
										if (bb.endsWith("."))											
											bb=bb+" "+artComment;
										else
											bb=bb+". "+artComment;
								}
								else if (bb.endsWith("."))
								{
									if(xmlObj.bibStyle.equals("5-BookB"))
									{ 
									   bb=bb+" "+artComment;
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+", "+artComment;
										
									}
								
								}
								else
								{
									
									if(xmlObj.bibStyle.equals("5-BookB"))
									{
										bb=bb+". "+artComment; 
									}
									else
									{
									//	bb=bb+", "+artComment;// old Ravi [08/11/2006][usmani mail 08/11/2006]
									
									//added by Ravi [08/11/2006][usmani mail 08/11/2006 change in ref style 5]
										if(artComment.indexOf("pp.",0) != -1)
										{
											int spos=-1;
											int mpos=-1;
											int epos=-1;
											StringBuffer tempR = new StringBuffer();
												spos=bb.indexOf("}.",0);
												if(spos != -1)
												{
													epos=bb.indexOf("}} {\\it{",spos);
													if(epos != -1)
													{
													
														//tempR.append(bb.substring(0,epos+"}} {\\it{".length()));
														tempR.append(bb.substring(0,epos));
														mpos=bb.indexOf("}},",epos+"}} {\\it{".length());
														
														//tempR.append("("+bb.substring(epos+"}} {\\it{".length(),mpos)+", "+artComment+").}}");
														
														tempR.append("}} ("+bb.substring(epos+"}} {\\it{".length(),mpos)+", "+artComment+").");
														
														tempR.append(bb.substring(mpos+3,bb.length()));
														
													}
													else
													{
															tempR.append(bb.substring(0,spos+1));
		
															tempR.append(" ("+artComment+").");
															tempR.append(bb.substring(spos+2,bb.length()));
															//System.out.println("tempR else  -->"+tempR);
														//	System.in.read();
													}
												
													bb=tempR.toString();
												}
												else
												{
													bb=bb+", "+artComment;
												}
												
									
										}
										else
										{
											bb=bb+", "+artComment;
										}
										 
									
									}
								}
								
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
					//artComment="";//19-07-04
					
				}
//***************************************************************************
				else if ((xmlObj.bibStyle).equals("5-Retail"))
				{
					
				
					if(isContribution == true)
					{
						artComment = temp;
					
						
						tempone=temp;	
						temp="";
						//bb = bb + " " + artComment; 
						isContribution = false;
						
					}
					else
					{
						artComment = temp;
						
						
						if(comment_after_host==true)
						{								
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								
								if (bb.endsWith(".") || bb.endsWith(",")) //13-08-04--Gaurav & Rajeev
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
									
								}
								else
								{
									bb=bb+" "+artComment; 
									//System.out.println("2 bb : "+bb+"\nartComment : "+artComment+"count_comment : "+count_comment);
								//System.in.read();
								}
								
							}
							else
							{
								if (artComment.charAt(0)>=65 &&  artComment.charAt(0)<=96)
								{
										if (bb.endsWith("."))											
											bb=bb+" "+artComment;
										else
											bb=bb+". "+artComment;

								
								}
								else if (bb.endsWith("."))
								{
									if(xmlObj.bibStyle.equals("5-BookB"))
									{ 
									   bb=bb+" "+artComment;
									}
									else
									{
										bb= bb.substring(0,bb.length()-1);
										bb=bb+"  "+artComment;
										
									}
								
								}
								else
								{
									
									if(xmlObj.bibStyle.equals("5-BookB"))
									{
										bb=bb+". "+artComment; 
									}
									else
									{
									//	bb=bb+", "+artComment;// old Ravi [08/11/2006][usmani mail 08/11/2006]
									
									//added by Ravi [08/11/2006][usmani mail 08/11/2006 change in ref style 5]
										if(artComment.indexOf("pp.",0) != -1)
										{
											int spos=-1;
											int mpos=-1;
											int epos=-1;
											StringBuffer tempR = new StringBuffer();
												spos=bb.indexOf("}.",0);
												if(spos != -1)
												{
													epos=bb.indexOf("}} {\\it{",spos);
													if(epos != -1)
													{
													
														//tempR.append(bb.substring(0,epos+"}} {\\it{".length()));
														tempR.append(bb.substring(0,epos));
														mpos=bb.indexOf("}},",epos+"}} {\\it{".length());
														
														//tempR.append("("+bb.substring(epos+"}} {\\it{".length(),mpos)+", "+artComment+").}}");
														
														tempR.append("}} ("+bb.substring(epos+"}} {\\it{".length(),mpos)+", "+artComment+").");
														
														tempR.append(bb.substring(mpos+3,bb.length()));
														
													}
													else
													{
															tempR.append(bb.substring(0,spos+1));
		
															tempR.append(" ("+artComment+").");
															tempR.append(bb.substring(spos+2,bb.length()));
															//System.out.println("tempR else  -->"+tempR);
														//	System.in.read();
													}
												
													bb=tempR.toString();
												}
												else
												{
													bb=bb+" "+artComment;
													
												}
												
									
										}
										else
										{
											bb=bb+" "+artComment;
										}
										 
									
									}
								}
								
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
					//artComment="";//19-07-04
					//System.out.println(" else bb --> "+bb+"\nartComment : "+artComment);
					//System.in.read();
				}

//***************************************************************************

				else if ((xmlObj.bibStyle).equals("DDT-NS"))
				{
					
					if(isContribution == true)
					{
						artComment = temp;						
						temp="";
						bb=bb+" "+artComment;
						isContribution = false;
					}
					else
					{
						artComment = temp;						
						if(comment_after_host==true)
						{								
							if(artComment_temp_diff.startsWith("[") || artComment_temp_diff.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
							{
								if (bb.endsWith(".")||bb.endsWith(","))
								{
									bb= bb.substring(0,bb.length()-1);//
									bb=bb+" "+artComment; 
								}
								else if (bb.endsWith(". ")||bb.endsWith(", "))
								{
									bb= bb.substring(0,bb.length()-2);
									bb=bb+" "+artComment; 
								}
								else
								{
									bb=bb+" "+artComment; 
								}
							}
							else
							{
								if (bb.endsWith(".")||bb.endsWith(","))
								{
									bb= bb.substring(0,bb.length()-1);
									bb=bb+" "+artComment; 
								}
								else if (bb.endsWith(". ")||bb.endsWith(", "))
								{
									bb= bb.substring(0,bb.length()-2);
									bb=bb+" "+artComment; 
								}
								else
								{
									bb=bb+" "+artComment; 
								}
							}
							artComment="";//19-07-04
						}
						temp="";
						isContribution = false;
					}
					artComment="";//19-07-04
				
				}
			//----------------------------------------------------------------------------------
				else if (((xmlObj.bibStyle).equals("5-Asw")) && (isContribution == true))
				{
					artComment = temp;
					
					temp="";
					isContribution = false;
				}
				//----------------------------------------------------------------------------------
				else if (((xmlObj.bibStyle).equals("5-Manage")) && (isContribution == true))
				{
					artComment = temp;
					temp="";
					isContribution = false;
				}
				//---------------------------------------------------------------------------------
				else if ((xmlObj.bibStyle).equals("Old_6") && isContribution == true)
				{
					artComment = temp;
					temp="";
					isContribution = false;
				}
				else
				{
				}
				
		//-----------------------------------------------------------------------------------------
					if(comment_after_host==true)
					{
					
						//Added By Ravi [11/12/2006 Change Request By Vivek]
						//Change Point : for Ref Style 5 [dot before comment]
						if((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
						{
							if(temp.indexOf("\\mychar\\url{",0) != -1)
							{
								
								temp=". "+temp;
							}
							else
							{
								temp=" "+temp;
							}
						}
						//end
						/*if(bb.endsWith(";")||bb.endsWith("."))// removed by mukesh on 06-11-08 (DDES_85)
						{
							bb = bb.substring(0,bb.length()-1);
							System.out.println("bb---------2222-----"+bb);
						}*/
					
						if(hostCount>0)
						{						
							if(isElementHost()== true)
							{								
								bb = bb +". "+temp+" ";
								mulHost = true;
								artTitle=""; // 14-9-04
								artTitleExist=false;
								authorExist=false;
								
							}
							else
							{
								bb = bb +" "+temp+" ";
								//System.out.println("bb -- > "+bb);
								//System.out.println("xmlObj.booInterRef -- > "+xmlObj.booInterRef);
								//System.in.read();
							}
						}
						else
						{
							bb = bb +" "+temp+" ";
						}
					}
					
				}///end of sb:comment
				//System.out.println("-------------->>"+bb);
			}
		}
		while(!sTag.equals("</SB:REFERENCE>"));
		int index=0;
		
		while(true)
		{
			index = bb.indexOf("</SB:COMMENT>");

			if(index==-1)
			{
				break;
			}
			bb = bb.substring(0,index)+bb.substring(index+"</SB:COMMENT>".length());
		}
		while(true)
		{
			index = bb.indexOf("<SB:COMMENT>");
			if(index==-1)
			{
				break;
			}
			bb = bb.substring(0,index)+bb.substring(index+"<SB:COMMENT>".length());
			//System.out.println("<sb:comment  :  --> "+bb);
		}
//System.out.println("bb : "+bb);
//System.in.read();
		if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE"))  // Ref. Style 5- Label problem : 2-9-04
		{
					
						Pattern pattern = Pattern.compile("\\(([0-9][0-9][0-9][0-9])\\)\\.");
						Matcher matcher = pattern.matcher(bb);
						boolean mat=matcher.find();
						if (mat==true)
						{						
							//check=bb.substring(1, bb.indexOf("). ")+2);//block on 07/08/2009
							check=bb.substring(0, bb.indexOf("). ")+2);
							check=check.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
							//System.out.println("tempLabel "+tempLabel);
							//System.out.println("check "+check+" \n\n check1 "+check1);
							//System.in.read();
							if (check.indexOf("(Ed.).")>0 || check.indexOf("(Eds.).")>0)
							{
								check=bb.substring(1, bb.indexOf("). ", bb.indexOf("). ")+1)+2);
							}
							if (check1.length()>0 && check1.compareTo(check)==0)
							{
								bb=bb.replaceFirst("\\(([0-9][0-9][0-9][0-9])\\)\\.", "("+tempLabel+").");								
								lblsame=true;
								check1=check;
								//System.out.println("1111111tempLabel "+tempLabel);
								//System.in.read();
								return bb; 
							}
							check2=tempLabel;  // here check2 holds previous typesetted ref. label year
							check1=check;  // here check1 holds previous typesetted ref. author names + date
							//check1=check1.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
							//check2=check2.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
							
						}
		}
		else if ((xmlObj.bibStyle).equals("5-Retail")) //14/04/2008
		{
					
						Pattern pattern = Pattern.compile("\\(([0-9][0-9][0-9][0-9])\\)\\.");
						Matcher matcher = pattern.matcher(bb);
						boolean mat=matcher.find();
						if (mat==true)
						{						
							check=bb.substring(1, bb.indexOf("). ")+2);
						//	System.out.println("check "+check+"    \n\n check1"+check1);
						//	System.in.read();
							if (check.indexOf("(Ed.).")>0 || check.indexOf("(Eds.).")>0)
							{
								check=bb.substring(1, bb.indexOf("). ", bb.indexOf("). ")+1)+2);
							}
							if (check1.length()>0 && check1.compareTo(check)==0)
							{
								bb=bb.replaceFirst("\\(([0-9][0-9][0-9][0-9])\\)\\.", "("+tempLabel+").");								
								lblsame=true;
								check1=check;
							//	System.out.println("tempLabel "+tempLabel);
							//	System.in.read();
								return bb; 
							}
							check2=tempLabel;  // here check2 holds previous typesetted ref. label year
							check1=check;  // here check1 holds previous typesetted ref. author names + date
							
						}
		}
		if ((xmlObj.bibStyle).equals("1")||(xmlObj.bibStyle).equals("1b"))
		{
			
			if (!bb.endsWith(".") || !bb.endsWith(". "))
			{
				bb.trim();
				bb+=".";
			}
		}
		else if((xmlObj.bibStyle).equals("brvhead"))
		{
			if(global.isbn1.length() > 0)//abhay 22.5.2006
			{
					bb += ", "+"ISBN: "+global.isbn1;
					
					global.isbn1 = "";
			}
			if(bb.endsWith(".") && !bb.endsWith("pp."))
			{
				bb = bb.substring(0, bb.length()-1);
			}
		}
		//System.out.println("\nsbReference(): "+bb);
		//System.in.read();
		
		return bb;
	}// end of sbReference()
	//---------------------------------------------------------------------------------------------------

	private String sbContribution() throws IOException
	{
		boolean first = false;
		String sTag = new String();
		String con = new String();
		total_Author_zefq=0;
		transTitle="";
		artTitle="";
		TempAutoth="";
		do
		{
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				sTag= xmlObj.getTag().toUpperCase();

				if(sTag.equals("<SB:AUTHORS>"))
				{					
					authorExist=true;
					boledi=true;//[19/12/2006]
					
					con = con + authorsTag(first);
					if ((xmlObj.bibStyle).equals("IChemE"))//01/12/2007
					{
						con+=",";
					}
					//System.out.println("con --- > "+con);
					//System.in.read();
					TempAutother=con;
					first = true;
				}
				else if(sTag.equals("<SB:TITLE>"))
				{
					
					artTitle = getBibTitle("</SB:TITLE>");// 07/11/2006
					if(xmlObj.jid.equals("RETAIL"))//13-08-2010
					{
						isbooktitle=true;
					}
					//System.out.println("artTitle : "+artTitle);
					//System.in.read();
					movetitleforZEFQ=artTitle;
					TitleForGlobel=artTitle;
					physt_brv=artTitle;
					socs_brv=artTitle;
					artTitleExist= true;
				}
				else if(sTag.equals("<SB:TRANSLATED-TITLE>"))
				{
					transTitle = getBibTitle("</SB:TRANSLATED-TITLE>");
				}
				else if(sTag.equals("<SB:COMMENT>"))//Comment inside the contribution
				{
					artComment = xmlObj.extractData("</SB:COMMENT>", true);
	
				}
			}
			
		}while(!sTag.equals("</SB:CONTRIBUTION>"));
		
		//--------------------------------------------------------------------------------------				
		if ((xmlObj.bibStyle).equals("1")||(xmlObj.bibStyle).equals("1b"))
		{			

			if (xmlObj.jid.equals("SYAPM")){
				con=con+"<SYAPM>"+artTitle;
			}else
			{
				con=con+artTitle;
			}
			//con=con+artTitle;//old
			
			
			if (artTitle.endsWith("?"))
			{
				ATendwithQ=true;
			}
			//
			if(transTitle.length()>0 && artTitle.length()>0)
			{
				con+=" ("+transTitle+")";
			}
			else if(transTitle.length()>0)
			{
				con+=" "+transTitle;
			}

			artTitle = "";
			transTitle="";
			artComment = "";
		}
		else if ((xmlObj.bibStyle).equals("brvhead"))
		{
					
			//blocked by ravi [11/11/2006]
		/*	if(artTitle.length() > 0)//abhay 19.05.2006
				{
					con = artTitle +", " +con;//[10/11/2006]
					//System.out.println("con --- > "+con);
					//System.in.read();
				}
			
		*/	
			if(xmlObj.jid.equals("PHYST"))
			{
				//System.out.println("con --- > "+con);
				if(artTitle.length() > 0)
					{
						con = artTitle +", ";//[10/11/2006]
						//System.out.println("con 6 --- > "+con);
						//System.in.read();
					}
			}
			else
			{
				if(artTitle.length() > 0)//abhay 19.05.2006
					{
						/*if((XT.modelStyle.equals("-MODDFrench"))||(XT.modelStyle.equals("-MODEFrench")))
						{con = con+" "+artTitle+"." ;
						//System.out.println("con 6 --- > "+con);
						//System.out.println("artTitle --- > "+artTitle);
						//System.in.read();
						}
						else*/  //commented by mukesh on 30-09-08 as per change request(TPMS)
						//System.out.println("con 6 --- >\n "+con);
						//System.out.println("artTitle --- >\n "+artTitle);
						if(xmlObj.jid.equals("CARP"))//08-06-2010
						{
							con =  con.trim()+", " +artTitle;//08-06-2010
						}
						else
						{
							con = artTitle +", " +con;//[10/11/2006]
						}
						
						//System.out.println("con 11 --- >\n "+artTitle);
							
					}
			}
				
	
			if (artTitle.endsWith("?"))
			{
				ATendwithQ=true;
			}
			if(transTitle.length()>0 && artTitle.length()>0)
			{
				con+=" ("+transTitle+")";
			}
			else if(transTitle.length()>0)
			{
				con+=" "+transTitle;
				//System.out.println("transTitle --- >\n "+transTitle);
			}

			artTitle = "";
			transTitle="";
			artComment = "";
		}
		//------------------------------------------------------------------------------------------------------
		if ((xmlObj.bibStyle).equals("1a"))
		{			
			con = con + artTitle;
			if (artTitle.endsWith("?"))
			{
				ATendwithQ=true;
			}
			if(transTitle.length()>0 && artTitle.length()>0)
			{
				con+=" ("+transTitle+")";
			}
			else if(transTitle.length()>0)
			{
				con+=" "+transTitle;
			}

			// con+=artComment;
			artTitle = "";
			transTitle="";
			artComment = "";
		}		
		//-----------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("2"))
		{
		       con = con;
				//System.out.println("con===>[["+con+"]]");
			   //con = con+artTitle;
			   //artTitle = "";
		}
		//------------------------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			//con = con+". ";
			if(con.length()>0)
			{
				if(con.endsWith("et al."))
					con = con+", ";
				else
					con = con+", ";
			}
			
		}
		//--------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a") || (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
		{
			//con = con+". ";
			if(xmlObj.jid.equals("NRL")||xmlObj.jid.equals("NRLENG"))
			{
				if(con.length()>0)
				{
					if(con.endsWith("et al."))
						con = con+" ";
					else
						con = con+". ";
				}
				//System.out.println("---------------------------");
			}
			else
			{
				//System.out.println("---------------------------"+con);
				if(con.length()>0)
				{
					if(con.endsWith("et al."))
						con = con+" ";
					else
						con = con+". ";
				}
			}
			
		}
		//--------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("3a-Jecs"))
		{
			if(con.length()>0)
			{
				if(con.endsWith("et al."))
					con = con+" ";
				else
					con = con+", ";
			}
		}
		else if((xmlObj.bibStyle).equals("chea"))
		{
			if(con.length()>0)
			{
				if(con.endsWith("."))
					con = con+" ";	
				else
					con=con+". ";
			}
		}
		//--------------------------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("4"))
		{
			if(con.length()>0)
			{
				if(xmlObj.jid.equalsIgnoreCase("PROTIS"))//23-06-2010
				{
					if(con.endsWith("et al."))
						con = "{\\bf "+con+"} <PROTIS>";
					else
						con = "{\\bf "+con+"} <PROTIS>";
					//System.out.println("--------------> "+con);
				}
				else
				{
					if(con.endsWith("et al."))
						con = con+" ";
					else
						con = con+". ";
				}
			}
			
		}
		//--------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
		{
			con = con;
			//System.out.println("con------------> "+con);
			
		}
		else if ((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
		{
			con = con;
			//System.out.println("con------------> "+con);
			
		}
		//--------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("5-Asw"))
		{
			con = con;

		}
		//-----------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("5-Manage"))
		{
			con = con;
			//con=con+artTitle+artComment;
		}
		//--------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("Old_6"))
		{
			if(con.length()>0)
			{
				if(con.endsWith("et al."))
					con = con+" ";
				else
					con = con+". ";
			}
		}
		//--------------------------------------------------------------------------------------
	//System.out.println("\nsbContribution(): [["+con+"]]");
	//System.in.read();
		return con;
	}// end of sbContribution()
	//--------------------------------------------------------------------------------------
	
	public String getBibTitle(String title)throws IOException
	{
		StringBuffer bibTitle= new StringBuffer();
		String tag ="";
		while (!tag.equals(title))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<SB:MAINTITLE>"))
				{
					//bibTitle.append(xmlObj.extractData("</SB:MAINTITLE>", true));//old
					/**
					* Date              : [20/09/2007]
					* Modify By         : Ravi
					* Change Point      : In case of PUBREL, title should be in bold Italic
					* Change Request By : TPMS
					*/
					
					if (xmlObj.bibStyle.equals("brvhead")&&xmlObj.jid.equalsIgnoreCase("PUBREL"))
					{
						bibTitle.append("{\\bi{"+xmlObj.extractData("</SB:MAINTITLE>", true)+"}}");
					}
					else if (xmlObj.bibStyle.equals("brvhead")&&xmlObj.jid.equalsIgnoreCase("PHYST"))
					{
						bibTitle.append("{\\bf{"+xmlObj.extractData("</SB:MAINTITLE>", true)+"}}");
						//System.out.println("\nbibTitle : "+bibTitle);
						//System.in.read()
					}
					else
					{
						bibTitle.append(xmlObj.extractData("</SB:MAINTITLE>", true));
						//System.out.println("bibTitle : "+bibTitle);
						//System.in.read();
					}
					//end Modify

					//System.out.println("bibtitle: "+bibTitle);
				}
				else if (tag.equals("<SB:SUBTITLE>"))  // [Imp. Note: CE: Feedback [Ajay & Usmani. In any case Title & Subtitle would be separated by "." instead of ":". In case of colon in MSS they would give it in BT]-27/07/04
				{
					if(!bibTitle.toString().endsWith(".")&&!bibTitle.toString().endsWith("?")&&!bibTitle.toString().endsWith("!"))
					{
						//Kishor [19/06/2004]
						if (xmlObj.bibStyle.equals("1")||(xmlObj.bibStyle).equals("1b")|| xmlObj.bibStyle.equals("2")|| xmlObj.bibStyle.equals("3")|| xmlObj.bibStyle.equals("6")|| xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea")|| xmlObj.bibStyle.equals("4") || (xmlObj.bibStyle).equals("yijom-ns")) //26-07-04
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}

						else if (xmlObj.bibStyle.equals("brvhead"))
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}
						else if (((xmlObj.bibStyle).equals("5"))||(xmlObj.bibStyle).equals("IChemE")|| ((xmlObj.bibStyle).equals("5-Asw"))||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}
						else if (((xmlObj.bibStyle).equals("5-Retail")))//14/04/2008
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));

						}
						else if ((xmlObj.bibStyle).equals("5-Manage"))
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}
						else if (((xmlObj.bibStyle).equals("Old_6")))
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}
						else
						{
							bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						}
					}
					else
					{
						bibTitle.append(" "+xmlObj.extractData("</SB:SUBTITLE>", true));
					}

				}
			}
		}
		return bibTitle.toString();
	}

	//----------------------------------------------------------------------------------------------------
	
	private String authorsTag(boolean first) throws IOException
	{
		String authors = new String();
		String sTag = new String();
		String temp = new String();
		String collab_text = new String();
		boolean firstAuthor=false;
		boolean oneauthcheck=false;
		boolean twoauthcheck=true;
		boolean morethanauthcheck=true;
		count_Author_zefq=0;
		int ch = 0;
		int auths = 0;	
		
		firstAuth=true;
		firstAuthSoceco=true;
		int author_count=0;
		long filePointer= xmlObj.fin.getFilePointer();
		String tempTag=sTag;
		
		while((!tempTag.equals("</SB:AUTHORS>")))
		{
			ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tempTag= xmlObj.getTag().toUpperCase();
				//System.out.println("tempTag-->>"+tempTag);
				if (tempTag.equals("<SB:AUTHOR>"))
				{
					//System.out.println("tempTag-->>"+tempTag);
					author_count++;
					//System.out.println("TGROUP_count-->>"+TGROUP_count);
				}
			}
		}
		xmlObj.fin.seek(filePointer);
		//System.out.println("author_count-->>"+author_count);
		do{
			ch =(char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag= xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:AUTHOR>"))
				{
					if (firstAuthor)
					{
						if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
						{
							authors = authors + temp+", ";
							
						}
						else if ((xmlObj.bibStyle).equals("DDT-NS"))
						{
							authors = authors + temp+" ";
						}
						else if ((xmlObj.bibStyle).equals("chea"))
						{
							authors = authors + temp+"; ";
						}
						else if(xmlObj.jid.equalsIgnoreCase("BODYIM")&&XT.isBodyimRef)
						{
							/*
							 *Block commented on 26-11-2016 to apply ellipsis in references 
							if(auths>6 && author_count>7)
							{	if(auths==7)
								temp="\\(\\ldots\\)";
								else
									temp="";

								if(auths==7)
									authors = authors.substring(0,authors.length()-1) + temp;
								else
									authors = authors + temp+" ";
							}
							else if(auths>2 && author_count<8)
							{
								////temp="";
								//Below condition commented on 29-05-2013 by Vivek [Request by PDD/TeXR&D] 
								//if(auths==3)
								//{
								//	//System.out.println("authors==>>"+authors);
								//	//System.out.println("temp==>>"+temp);
								//	authors = authors +"\\& " +temp+" ";
								//}
								//Below line added on 29-05-2013 by Vivek [Request by PDD/TeXR&D]
								authors = authors + temp+", ";
							}*/
							if(ellipsis)//DTD 540 updation 01-08-2015
							{
								//System.out.println("<SB:ELLIPSIS> found in reference:"+ellipsis);
								//System.out.println("Adding ldots...");
								authors = authors + temp+", \\(\\ldots\\) ";
							}
							else
							{
								authors = authors + temp+", ";
							}
						}
						else if(ellipsis)//DTD 540 updation 01-08-2015
						{
							//System.out.println("<SB:ELLIPSIS> found in reference:"+ellipsis);
							//System.out.println("Adding ldots...");
							authors = authors + temp+", \\(\\ldots\\) ";
						}
						else
						{
							authors = authors + temp+", ";
							
						}
					}
					else
					{
						firstAuthor = true;
					}
					//System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyyy");
					++count_Author_zefq;
					temp = bibAuthorList("</SB:AUTHOR>");
					temp=temp.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
					Retail_arr.add(temp);
					//System.out.println("authors==> "+authors);
					//System.in.read();
					auths++;
				}
				else if(sTag.equals("<SB:ET-AL/>"))
				{
						if ((xmlObj.bibStyle).equals("DDT-NS"))
					    {
							authors = authors + temp +" ";
						    temp = "{\\it{et al.}}";
					    }
					    //[28/12/2006]
						//Added By Ravi 
						//Change Request : Subrata Das [mail date : 28/12/2006]
						//Change Point: In case of PHYST journal et al should in italic
						else if(xmlObj.jid.equals("PHYST"))
						{
							authors = authors + temp +", ";
						    temp = "{\\it{et al.}}";
						}
					//end
					  else
					  {
						String duckaid=xmlObj.aid;
						if(duckaid.indexOf(".")!=-1)
							duckaid=duckaid.substring(0,duckaid.indexOf("."));
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						  {
							  authors = authors + temp +" ";
							  temp = "et al.";
						  }
						else
						  {
							if(xmlObj.jid.equals("BRACHY"))//17-08-2017
							{
								authors = authors + temp +", ";
								temp = "{\\it{et al.}}";
							}
							else if(!xmlObj.jid.equals("BODYIM"))
							{
								authors = authors + temp +", ";
								temp = "et al.";
							}
							else if(xmlObj.jid.equals("BODYIM"))// &&XT.isBodyimRef==false)
							{
								//authors = authors + temp +", ";
								authors = authors +"\\& "+ temp +", ";
								temp = "et al.";
							}

						  }
					  }
						
				}
				else if(sTag.equals("<SB:ELLIPSIS/>"))//For new DTD 540 01-08-2015
				{
					ellipsis = true;
				}
				else if(sTag.equals("<SB:COLLABORATION>"))
				{
					//System.out.println("AAAA "+temp);
					if(temp.length()>0)
					{
						if(xmlObj.jid.equalsIgnoreCase("ANTAGE"))
							authors = authors + temp+"; ";
						else
						{
							authors = authors + temp+", ";
						}

						temp="";
					}
					
					//Kishor [19/06/2004]-Collaboration inside the authors
					collab_text=xmlObj.extractData("</SB:COLLABORATION>", true);

					if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB"))
					{
						collab_text=collab_text+".";
						if (authors.length()>0) // 07-08-04 [Rajeev & Gaurav- for collaboration ]
						{
							authors = authors + "\\& "+collab_text;		
							//System.out.println("authors ==> "+authors);
							//System.in.read();
						}
						else
						{
							authors = authors + collab_text;	
							//System.out.println("else authors ==> "+authors);
						}
					}
					else if ((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
					{
						collab_text=collab_text+".";
						if (authors.length()>0) // 07-08-04 [Rajeev & Gaurav- for collaboration ]
						{
							authors = authors + "\\& "+collab_text;		
							//System.out.println("authors ==> "+authors);
							//System.in.read();
						}
						else
						{
							authors = authors + collab_text;	
							//System.out.println("else authors ==> "+authors);
						}
					}
					//-----------------------------
					else if ((xmlObj.bibStyle).equals("DDT-NS"))//Gaurav Based on DDSTR--11/25/04
					{
							if (authors.length()>0)
							{
							collab_text=collab_text+"";//Gaurav Based on ddstr --11/26/04
							}
							else
							{
								collab_text=collab_text+".";
							}
						if (authors.length()>0) // 07-08-04 [Rajeev & Gaurav- for collaboration ]
						{
							authors = authors + " "+collab_text;					
						}
						else
						{
							authors = authors + collab_text;					
						}
					}				
					//----------------------------------

					else if ( (xmlObj.bibStyle).equals("yijom-ns"))
					{						
						if (collab_text.length()>0)
						{
							collab_text=collab_text.substring(0,1)+"{\\sc{"+collab_text.substring(1)+"}}";
						}
						authors = authors + collab_text;					
					}
					else
					{
						authors = authors + collab_text;
					}
												
					temp="";					
				}
			}			
		}while(!sTag.equals("</SB:AUTHORS>"));
		
		//System.out.println("authors==========>>"+authors);
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		if ((xmlObj.bibStyle).equals("1")||(xmlObj.bibStyle).equals("1a")||(xmlObj.bibStyle).equals("1b"))
		{
			if (xmlObj.jid.equals("JASCER"))
			{
				if(authors.endsWith(", "))
				{
					authors=authors.substring(0,authors.length()-2);
				}
				//System.out.println("temp==>"+temp);
				if(!temp.endsWith("et al."))
				{
					if(auths>1)
					authors = authors +" and "+ temp;
					else
					authors = authors +" "+ temp;
				}
				else
				authors = authors+ " "+ temp;
			}
			else
				authors = authors + temp;
			 if (xmlObj.jid.equals("SOCSCI")) 
			{
				authors += "; ";
				
			}
			else if (xmlObj.jid.equals("SYAPM"))
			{
				authors += " ";
			}
			else
			{
				authors += ", ";
			}
			
		}
		else if ((xmlObj.bibStyle).equals("brvhead"))
		{
			authors = authors + temp;
			
			//authors += ". ";//abhay 21/06/2006 //block by ravi [10/11/2006 changed by vivek]
			//Added by Ravi [10/11/2006]
			if(xmlObj.jid.equals("PHYST"))
			{
				authors += ", ";
			}
			else if(xmlObj.jid.equals("CARP"))//08-06-2010
			{
				authors += " ";//08-06-2010
			}
			else
			{
				authors += ". ";
			}
			
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("2"))
		{
			if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
			{
				//System.out.println("temp==>"+temp);
				if(!temp.endsWith("et al."))
				{
					if(auths>1)
					{
						if(authors.endsWith(", "))
						{
							authors=authors.substring(0,authors.length()-2);
						}
						authors = authors +" and "+ temp;
					}
					else
					{
						authors = authors +" "+ temp;
					}
				}
				else
				authors = authors+ " "+ temp;
				if(!authors.endsWith(","))
				{
					authors += ", ";
				}
				//System.out.println("authors======>[["+authors+"]]");
			}
			else
			{
				authors = authors + temp;
				authors += ", ";
			}
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			if(authors.length()>0)
			{
				//System.out.println("count_Author_zefq--->>>"+count_Author_zefq);
				if(count_Author_zefq==2)
				{
					authors=authors.substring(0,authors.trim().length()-1);
					authors = authors + " and "+temp;
				}
				else if(count_Author_zefq > 2)
					authors = authors + " and "+temp;
			}
			else
				authors = authors +temp;
			count_Author_zefq=0;
			
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("3")||(xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6"))
		{
			authors = authors + temp;
			
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ( (xmlObj.bibStyle).equals("yijom-ns"))
		{			
			authors = authors + temp;			
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("3a-Jecs"))
		{
			if (auths ==  1)//Guarav--In case of Single Author with et al , (jecs 5063.--29-08-04..).
			{
				if (temp.equals("et al."))
				{
					if (authors.endsWith(", "))
					{
						authors= authors.substring(0,authors.length()-2);
					}
					temp = "{\\it{et al.}}";	
					//
					authors = authors +" "+ temp;
				}
			else
			   {
					authors = authors +temp;
			  }
		}
			
			else if (auths >1)//More author then - and - before last author.
			{
				if (temp.equals("et al."))
				{
					if (authors.endsWith(", "))
					{
						authors= authors.substring(0,authors.length()-2);
					}
					temp = "{\\it{et al.}}";	
					//
					authors = authors +" "+ temp;
				}
				else
				{
					if (authors.endsWith(", "))
					{
						authors= authors.substring(0,authors.length()-2);
						authors=authors+" ";
					}
					authors = authors +"and "+ temp;
				}
			}
			else
			{
			authors = authors +temp;
			}
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("chea"))
		{			
			if (auths ==  1)//Guarav--In case of Single Author with et al .jecs 5063.--29-08-04..
			{
				if (temp.equals("et al."))
				{				
					authors = authors +", "+ temp;
				}
				else
			   {
					authors = authors +temp;
								
				}
				
		}
		if (auths >1)//More author then - and - before last author.
		{
			if (temp.equals("et al."))
			{
					if (authors.endsWith(", "))
					{
						authors= authors.substring(0,authors.length()-2);
					}					
					authors = authors +"; "+ temp;
			}
			else
			{
				if ( authors.endsWith(",")||authors.endsWith(";"))  
				 {
					   authors=authors.substring(0,authors.length()-1);				      
				  }
				  if ( authors.endsWith(", ")||authors.endsWith("; "))  
				 {
					   authors=authors.substring(0,authors.length()-2);				      
				  }				 
			}
			
		}
	}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	else if ((xmlObj.bibStyle).equals("4"))
	{
			authors = authors + temp;
	} 
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
	else if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
	{
			
			if (auths >1)//More author then & before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					if (temp.length()>0)
					{
						if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
						{
							if (authors.endsWith(", "))
							{
								authors= authors.substring(0,authors.length()-2);
								authors=authors+" ";
							}
							authors = authors +"and "+ temp;							
						}
						else if((xmlObj.bibStyle).equals("5-BookB"))
						{
							authors = authors +"and "+ temp;
						}
						else
						{
							if((xmlObj.bibStyle).equals("IChemE"))
							{
								if(authors.endsWith(", "))
								{
									authors=authors.substring(0,authors.length()-2)+" ";
								}
								authors = authors +"and "+ temp;
							}
							else
							{
								//System.out.println("authors==> "+authors+"ddddddd");
								/*if(authors.endsWith(", "))//blocked on 18-11-2011
								{
									authors=authors.substring(0,authors.length()-2)+" ";
								}*/
								//if((xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("REDEE")||xmlObj.jid.equalsIgnoreCase("IEDEE")||xmlObj.jid.equalsIgnoreCase("RCSAR"))&&XT.articleType.equalsIgnoreCase("ES"))//19-10-2012 Added condition for above jid and language spanish.
								if(XT.Spanish_Reference_Jid_5APA.contains(xmlObj.jid) && XT.articleType.equalsIgnoreCase("ES"))//Instead of jid variable used from onecolumn.dbf on 04-03-2013
								{
									if(authors.endsWith(", "))
									{
										authors=authors.substring(0,authors.length()-2)+" ";
									}
									//authors = authors +" y "+ temp;// Updated on 23-09-2015 for jid RLP,SUMPSI,SUMNEG
									if(xmlObj.jid.equalsIgnoreCase("RLP") || xmlObj.jid.equalsIgnoreCase("SUMPSI") || xmlObj.jid.equalsIgnoreCase("SUMNEG") || xmlObj.jid.equalsIgnoreCase("JIK")){
										authors = authors +" \\& "+ temp;
									}else{
										if(xmlObj.jid.equalsIgnoreCase("PSICOD")){//Added on 24-06-2019, comma (,) added before y in authors in each references.//EXAMPLE : PSICOD StyleSheet 
											authors = authors.trim() +", y "+ temp;
										}else{
											authors = authors +" y "+ temp;
										}
									}	
								}
								else
								{
									/*
									if(xmlObj.jid.equalsIgnoreCase("BODYIM") &&XT.isBodyimRef)
									{
										if(auths>3 && author_count<8)
										{
											//authors = authors; //This line commented and below line added on 29-05-2013 by Vivek [Request by PDD/TeXR&D]
											authors = authors +"\\& "+ temp;
										}
										else
											authors = authors +"\\& "+ temp;
									}
									else*/
										authors = authors +"\\& "+ temp;
								}
							}
							//authors = authors +"\\& "+ temp;//01/12/2007
							//System.out.println("authors==> "+authors+"ddddddddd\n temp "+temp);
							//System.in.read();
							//[02/01/2007]
						/*	if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
							{
								authors = authors +" "+ temp;
							}
							else
							{
								authors = authors +"\\& "+ temp;
							}
						*/
						}
					}					
					
					if((!authors.endsWith(".")) && (!xmlObj.jid.equalsIgnoreCase("RETAIL")))  // 26-07-04 Acc. to client feedback on Cocomp/2005
				   	{
				      authors +=".";				
				   	}				
				}

			}
			else
			{			       
				authors = authors +temp;								
				//if ( ! authors.endsWith(".")) 
				if((!authors.endsWith(".")) && (!xmlObj.jid.equalsIgnoreCase("RETAIL")))
				{
				      authors +=".";				
				}				
			}
		}
		else if ((xmlObj.bibStyle).equals("5-Retail"))
		{
			
			if (auths >1)//More author then & before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					if (temp.length()>0)
					{
						if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
						{
							if (authors.endsWith(", "))
							{
								authors= authors.substring(0,authors.length()-2);
								authors=authors+" ";
							}
							//authors = authors +"and "+ temp;//14/04/2008
							
							if(count_Author>1)//14/04/2008
							{
								String abc_author="";
								//new modification
								//if(Prev_Retail_arr.size()==Retail_arr.size())//05/09/2008  mukesh
								if(Prev_Retail_arr.size()==Retail_arr.size()&& Retail_arr.containsAll(Prev_Retail_arr))
								{
									//System.out.println("Prev_Retail_arr : "+Prev_Retail_arr+"\n temp "+temp+"\n Retail_arr "+Retail_arr);
									//System.in.read();
									
									for(int i=0;i<Prev_Retail_arr.size();i++)
									{
										if(Prev_Retail_arr.get(i).toString().equals(Retail_arr.get(i).toString()))
										{
											if(i==(Prev_Retail_arr.size()-1))
											{
												if(abc_author.endsWith(", "))
												{
													abc_author=abc_author.substring(0,abc_author.length()-2);
												}
												abc_author +=" and {\\dashrule}";
												//System.out.println("\n \n 1 ravi authors: "+authors);
											}
											else
											{
												abc_author+="{\\dashrule}, ";
												//System.out.println("\n \n 2 ravi authors: "+authors);
											}
										}
										else
										{
											if(i==(Prev_Retail_arr.size()-1))
											{
												if(abc_author.endsWith(", "))
												{
													abc_author=abc_author.substring(0,abc_author.length()-2);
												}
												abc_author +=" and "+Retail_arr.get(i).toString();
												//System.out.println("\n \n 1 ravi Prev_Retail_arr : "+Retail_arr.get(i).toString());
											}
											else
											{
												abc_author+=Retail_arr.get(i).toString()+", ";
												//System.out.println("\n \n 2 ravi Prev_Retail_arr : "+Retail_arr.get(i).toString());
											}
											
										}
									}
									authors=abc_author;
								}
								else
								{
									authors = authors +"and "+ temp;
									//System.out.println("\n \n 3 ravi authors: "+authors);
								}
								//end
								//authors = authors +"and {\\dashrule}";//live
								//System.out.println("authors : "+authors+"\n temp "+temp);
								//System.in.read();

							}
							else
								authors = authors +"and "+ temp;

							
								//end //14/04/2008
						}
						else if((xmlObj.bibStyle).equals("5-BookB"))
						{
							authors = authors +"and "+ temp;
						}
						else
						{
							if((xmlObj.bibStyle).equals("IChemE"))
							{
								if(authors.endsWith(", "))
								{
									authors=authors.substring(0,authors.length()-2)+" ";
								}
								authors = authors +"and "+ temp;
							}
							else
							{
								authors = authors +"\\& "+ temp;
							}
							//authors = authors +"\\& "+ temp;//01/12/2007
							//System.out.println("authors==> "+authors+"\n temp "+temp);
							//System.in.read();
							//[02/01/2007]
						/*	if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
							{
								authors = authors +" "+ temp;
							}
							else
							{
								authors = authors +"\\& "+ temp;
							}
						*/
						}
					}					
					
					if((!authors.endsWith(".")) && (!xmlObj.jid.equalsIgnoreCase("RETAIL")))  // 26-07-04 Acc. to client feedback on Cocomp/2005
				   	{
				      authors +=".";				
				   	}				
				}

			}
			else
			{			       
				//05/09/2008 my name is Mukesh
				if(Prev_Retail_arr.size()==Retail_arr.size()&& Retail_arr.containsAll(Prev_Retail_arr))
				{
					authors = authors +"{\\dashrule}";;
				}
				else
				{
					authors = authors +temp;
				}
				//authors = authors +temp;								
				//if ( ! authors.endsWith(".")) 
				if((!authors.endsWith(".")) && (!xmlObj.jid.equalsIgnoreCase("RETAIL")))
				{
				      authors +=".";				
				}				
			}
		}
		//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("DDT-NS"))
		{
			//Gaurav & Rajeev
			if (auths >1)//More author then & before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					authors = authors +" and "+ temp;
										
					if ( ! authors.endsWith("."))  // 26-07-04 Acc. to client feedback on Cocomp/2005
				   {
				      authors +=".";				
				   }				
				}

			}
			else
			{			       
				authors = authors +temp;								
				if ( ! authors.endsWith(".")) 
				{
				      authors +=" ";				
				}				
			}
		}
		//---------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("5-Asw"))
		{
			//Kishor [18/06/2004]
			if (auths >1)//More author then & before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					authors = authors +"\\& "+ temp;
					if ( ! authors.endsWith("."))  // 26-07-04 Acc. to client feedback on Cocomp/2005
				   {
				      authors +=".";				
				   }
				}
			}
			else
			{			       
				authors = authors +temp;								
				if ( ! authors.endsWith(".")) 
				{
				      authors +=".";				
				}				
			}
		}
		//---------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("5-Manage"))//5 with deviation like MANAGE
		{
			//Kishor [30/06/2004]
			if (auths >1)//More author then & before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					authors = authors +"\\& "+ temp;
					if ( ! authors.endsWith("."))  // 26-07-04 Acc. to client feedback on Cocomp/2005
				   {
				      authors +=".";				
				   }
				}
			}
			else
			{			       
				authors = authors +temp;								
				if ( ! authors.endsWith(".")) 
				{
				      authors +=".";				
				}				
			}
		}
		//---------------------------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("Old_6"))
		{
			authors = authors + temp;
		}
		
		if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
		{
			int firstIn=authors.indexOf(",");
			int lastIn=authors.lastIndexOf(",");//old
		//	int lastIn=authors.indexOf(",",firstIn+1);
	//	if(auths>1)
	//	{
		
			if(firstIn != -1)
			{
				
				//System.out.println("authors--> "+authors+"firstIn-->"+firstIn+"lastIn ==>"+lastIn+"auths->"+auths);
				//System.in.read();
				/*
				*Added By Ravi [22/02/2007]
				*Change Request By : Vivek
				* Change Point : "and" between first name and last name not required IN JOURNAL SOCSCI
				*/
				if ((xmlObj.bibStyle).equals("1"))
				{
						if(firstIn == lastIn)
						{
							authors=authors.replaceFirst(","," and");
						}
						else if(lastIn > firstIn)
						{
							StringBuffer tempAuth= new StringBuffer(authors);
						//	if(auths==1)
							tempAuth.insert(lastIn+1," and");
							authors=tempAuth.toString();
						}
				}
				

			}
	//	}

		}
		//System.out.println("abhay Ref: "+authors);
		return authors;
	}
	//--------------------------------------------------------------------------------------

	private String noteTag() throws IOException
	{
		String tag = new String();
		String note = new String();
		while(!tag.equals("</CE:NOTE>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch == '<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
				{
					note = note +"\r\n\n"+xmlObj.extractData("</CE:SIMPLE-PARA>", true);
					
				}
			}
		}
		return note;
	}
	//--------------------------------------------------------------------------------------

	private String bibHost() throws IOException
	{
		String host = new String();
		String eTag = new String();
		String sTag = new String();
		String page = new String();
		String sbArticleNumber = new String();
		String doi = new String();
		int ch = 0;
		global.dkj_edited_book="";
		global.dkj_book_series="";
		while(!sTag.equals("</SB:HOST>"))
		{
			ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag= xmlObj.getTag().toUpperCase();

				if(sTag.equals("<SB:ISSUE>"))//Journal case
				{
					hostType= sTag;

					host = host +bibIssue();
					//System.out.println("host 12  --> "+host);
					//System.in.read();
				}
				else if(sTag.equals("<SB:BOOK>"))//Book Case
				{
					isbookpage=true;
					//checkTitle=true;
					hostType= sTag;

					if(xmlObj.bibStyle.equals("1a")) // 23-07-04 [Gaurav/rajeev when CT & BT coming sequentially then for removing space]
					{
						host= host+""+bibBook();
						//System.out.println("host 12  --> "+host);
					//System.in.read();
					}
					else
					{
						//System.out.println("host 22121  --> "+host);
						host= host+" "+bibBook();
						//System.out.println("host 121  --> "+host);
						//System.in.read();
					}
			
				}
				else if(sTag.toUpperCase().startsWith("<SB:BOOK "))//Book Case With class attribute= report////28-08-2012 JADTD520 Updation
				{
					isbookpage=true;
					//checkTitle=true;
					hostType= sTag;

					if(xmlObj.bibStyle.equals("1a")) // 23-07-04 [Gaurav/rajeev when CT & BT coming sequentially then for removing space]
					{
						host= host+""+bibBook();
						//System.out.println("host 12  --> "+host);
					//System.in.read();
					}
					else
					{
						//System.out.println("host 22121  --> "+host);
						host= host+" "+bibBook();
						//System.out.println("host 121  --> "+host);
						//System.in.read();
					}
			
				}
				else if(sTag.equals("<SB:EDITED-BOOK>"))//Edited Book Case
				{
					//checkTitle=true;
					global.dkj_edited_book="yes";
					hostType= sTag;
					sTag=bibEditedBook();
					//System.out.println("sTag 12  -->[["+sTag+"]]");
					host= host+sTag;
					
					//System.in.read();

				}
				else if(sTag.equals("<SB:E-HOST>"))//Inter ref
				{
					hostType= sTag;
					host= host+eHost();
					
				}
				else if(sTag.equals("<SB:COMMENT>"))//Comment
				{
					
				}
				else if(sTag.equals("<CE:DOI>"))
				{
					//System.out.println("host==========>>"+host);
					doi = xmlObj.extractData("</CE:DOI>", true);
					boldoi=true;
				}
				else if(sTag.equals("<SB:PAGES>"))
				{
					page = bibPages();
					//System.out.println("page --> "+page+" --- isbookpage="+isbookpage);
					if(isbookpage)
					{
						//bookpage=page;
						global.bookpage=page;
						isbookpagewithedition=true;
						//System.out.println("global.bookpage  --> "+global.bookpage);
						//System.in.read();
						//page=page.replaceAll("\\\\","\\\\\\\\");
						//host=host.replaceFirst("<pageinbookreference>",", pp. "+page);
						int spos=0;
						int epos=0;
						StringBuffer sb=new StringBuffer(host);
//						System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
						if(sb.indexOf("<><pageinbookreference>") !=-1)
						{
							spos=sb.indexOf("<><pageinbookreference>",0);
							//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
							if(spos !=-1)
							{
								epos=spos+23;
								if(epos !=-1)
								{
									sb=sb.delete(spos,epos);
									if(!page.equals(""))
									{
										sb=sb.insert(spos," pp. "+page);
									}
									host=sb.toString();
								}
							}
						}
						else if(sb.indexOf("<pageinbookreference>") !=-1)
						{
							spos=sb.indexOf("<pageinbookreference>",0);
							//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
							if(spos !=-1)
							{
								epos=spos+21;
								if(epos !=-1)
								{
									sb=sb.delete(spos,epos);
									if(!page.equals(""))
									{
										sb=sb.insert(spos,", pp. "+page);
									}
									host=sb.toString();
								}
							}
						}
						//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
					}
					//isbookpage=false;
					//System.out.println("HOST::>"+host);
				}
				else if(sTag.equals("<SB:ARTICLE-NUMBER>"))//For new DTD 540 01-08-2015
				{
					//System.out.println("SB:ARTICLE-NUMBER==========>>"+host);
					sbArticleNumber = xmlObj.extractData("</SB:ARTICLE-NUMBER>", true);
					if((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a")){
						host=host.trim()+", {"+sbArticleNumber+"}";
					}else if((xmlObj.bibStyle).equals("2") || (xmlObj.bibStyle).equals("5") || (xmlObj.bibStyle).equals("5-Asw") || (xmlObj.bibStyle).equals("5-Retail") || (xmlObj.bibStyle).equals("7") || (xmlObj.bibStyle).equals("8") || (xmlObj.bibStyle).equals("9")){
						if((xmlObj.bibStyle).equals("5")){
							host=host+", {Article "+sbArticleNumber+"}";
						}else{
							host=host+", {"+sbArticleNumber+"}";
						}
					}else if((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a") || (xmlObj.bibStyle).equals("4") || (xmlObj.bibStyle).equals("6")){
						host=host+":{"+sbArticleNumber+"}";
					}else{
						host=host+", {"+sbArticleNumber+"}";
					}
					//System.out.println("HOST::>"+host);
				}
				else
				{
					//host=host.replaceFirst("<pageinbookreference>","");
					int spos=0;
					int epos=0;
					StringBuffer sb=new StringBuffer(host);
					if(sb.indexOf("<><pageinbookreference>") !=-1)
					{
						spos=sb.indexOf("<><pageinbookreference>",0);
						//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
						if(spos !=-1)
						{
							epos=spos+23;
							if(epos !=-1)
							{
								sb=sb.delete(spos,epos);
								if(!page.equals(""))
								{
									sb=sb.insert(spos," pp. "+page);
								}
								host=sb.toString();
							}
						}
					}
					else if(sb.indexOf("<pageinbookreference>") !=-1)
					{
						spos=sb.indexOf("<pageinbookreference>",0);
						//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
						if(spos !=-1)
						{
							epos=spos+21;
							if(epos !=-1)
							{
								sb=sb.delete(spos,epos);
								if(!page.equals(""))
								{
									sb=sb.insert(spos,", pp. "+page);
								}
								host=sb.toString();
							}
						}
					}
					//System.out.println("SB===>"+sb+"<==>"+spos+" and "+epos);
				}
			}
		}
		//------------------------------------------------------------------------------
		if((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a")|| (xmlObj.bibStyle).equals("1b"))
		{
			//System.out.println("bibHost()1: "+host);
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (xmlObj.jid.equals("SYAPM"))
				{
					if(page.length()>0)
						host+=page;
					vnr="";

				}
				else
				{
					if(page.length()>0)
						host+=" "+page;
						if (xmlObj.jid.equals("JASCER"))
						host+=" "+tempDateForJASCER;
					vnr="";
				}
				//System.out.println("tempDateForJASCER==>"+tempDateForJASCER);
				//System.in.read();
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (xmlObj.jid.equals("SYAPM"))
				{
					if (page.length()>0)
					{
						if(page.startsWith(","))
						page=page.substring(2,page.length());
						if(page.indexOf("ndash")>0)
							host+=", pp. "+page;
						else
							host+=", p. "+page;
						vnr="";

						
					}
				}
				else if(xmlObj.jid.equals("JASCER"))
				{
					if (page.length()>0)
					{
						if(page.indexOf("ndash")>0)
							host+=" pp. "+page;
						else
							host+=" p. "+page;
						vnr="";
					}
				}
				else
				{
					if (page.length()>0)
					{
						if(page.indexOf("ndash")>0)
							host+=", pp. "+page;
						else
							host+=", p. "+page;
						vnr="";
					}
				}
			}
			else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))//updated in Ref_Style (1,1a,1b) on 03-05-2013 for MFC Guide Version 3.0
			{
				if(!page.equals(""))
				{
					host+=", pp. "+page;
					//System.out.println("host=====> "+host);	
				}
				else
				{
					host+=page;
				}
			}
			else
			{
				host+=page;
				//System.out.println("host=====> "+host);	
			}
		//System.out.println("bibHost()2: "+host);	
		}
		else if((xmlObj.bibStyle).equals("brvhead"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if(page.length()>0)
					host+=" "+page;
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (page.length()>0)
				{
					//if(page.indexOf("ndash")>0)
						host+=". "+page+" pp." ;
					//else
					//	host+=". p. "+page;
					vnr="";
				}
			}
			else
			{
				host += page;
			}
			
		}
		//--------------------------------------------------------------------------------------		
		else if((xmlObj.bibStyle).equals("2"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if(page.length()>0)
					host+=", "+page;				
				if (mulHost==true && mulhostDate.length()>0)
				{
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				//System.out.println("host===>"+host);
				if (page.length()>0)
				{
					if(page.indexOf("ndash")>0)
					{
						if(host.endsWith(", "))        //26-07-04
						 {
						host=host.substring(0,host.length()-2);
						 }
						if(host.endsWith(".")||host.endsWith(","))        //26-07-04
						 {
						host=host.substring(0,host.length()-1);
						 }

						if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
							host+=", "+page;
						else
							host+=", pp. "+page;
					}
					else
					{
						if(host.endsWith(", "))        //26-07-04
						 {
						host=host.substring(0,host.length()-2);
						 }
						if(host.endsWith(".") || host.endsWith(","))        //26-07-04
						{
						host=host.substring(0,host.length()-1);
						 }
						if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
							host+=", "+page;
						else
							host+=", p. "+page;
					}
				}
				if (mulHost==true && mulhostDate.length()>0)
				{
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				if(pubforsoceco.length()>0)
				{
					//System.out.println("pubforsoceco===>"+pubforsoceco);
					if(host.endsWith("."))
						host+=" "+pubforsoceco+".";
					else
						host+=". "+pubforsoceco+".";
				}


			}
			else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))//updated in Ref_Style (2) on 03-05-2013 for MFC Guide Version 3.0
			{
				//host+=page;
				if(!page.equals(""))
				{
					host+=", pp. "+page;
					//System.out.println("host=====> "+host);	
				}
				else
				{
					host+=page;
				}
				if (mulHost==true && mulhostDate.length()>0)
				{
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				//System.out.println("host=====> "+host);	
			}
			else
			{
				//if(!((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")))//27-12-2013
				if(!(xmlObj.jid.equals("SOCECO")||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE")))//27-12-2013
				{
					host+=page;
				}
				else if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) < 1341))//27-12-2013
				{
					System.out.println("\nCondition for SOCECO\n");
					host+=page;
					System.out.println("host=======>"+host);
				}
				if (mulHost==true && mulhostDate.length()>0)
				{
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				if(pubforsoceco.length()>0)//27-12-2013 for SOCECO, JBECON and JBEE only
				{
					System.out.println("host===>"+host);
					if(host.endsWith("."))
						host+=" "+pubforsoceco+".";
					else
						host+=". "+pubforsoceco+".";
					pubforsoceco="";
				}

				//System.out.println("host=====> "+host);	
			}			
		}
		//--------------------------------------------------------------------------------------		
		else  if((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						host+=": p."+page;
					}
					else
					{
						host+=": p. "+page;
					}
				}
				else
				{
					host+="";//		Gaurav--11/13/04
					//host+=".";//aaaaaaaaa

				}

				vnr="";
			}//7/20/04
			else if (hostType.equals("<SB:EDITED-BOOK>"))
			{
				if (page.length()>0)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						host+=": "+page;
					}
					else
					{						
							host+=" p. "+page;					
					}
				}
				vnr="";	
			}
			else
			{				
					host+=page;
							
			}		
		}
		//--------------------------------------------------------------------------------------
		//--------------------------------------------------------------------------------------		
		else  if((xmlObj.bibStyle).equals("3")||(xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						host+=": "+page;
					}
					else
					{
						host+=":"+page;
					}
				}
				else
				{
					host+="";//		Gaurav--11/13/04
					//host+=".";//aaaaaaaaa

				}

				vnr="";
			}//7/20/04
			else if (hostType.equals("<SB:EDITED-BOOK>"))
			{
				if (page.length()>0)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						host+=": "+page;
					}
					else
					{	
						if(xmlObj.bibStyle.equals("6"))
						{
							host+=":"+page;
						}
						else
							host+=" p. "+page;					
					}
				}
				vnr="";	
			}
			else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))//updated in Ref_Style (3,3a,6) on 03-05-2013 for MFC Guide Version 3.0
			{
				if((xmlObj.bibStyle).equals("6"))
				{
					if(!page.equals(""))
					{
						host+=":"+page;
					}
					else
					{
						host+=page;
					}
				}
				else
				{
					if(!page.equals(""))
					{
						host+=". p. "+page;
					}
					else
					{
						host+=page;
					}
				}
			}		
			else
			{				
					host+=page;
			}		
		}
		//--------------------------------------------------------------------------------------		
		else  if((xmlObj.bibStyle).equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
					host+=", "+page;
				else
					host+=".";
				vnr="";
			}
			else if (hostType.equals("<SB:EDITED-BOOK>"))
			{
				//System.exit(0);
				if (page.length()>0)
				{
					if (host.endsWith("."))
					{
						host= host.substring(0,host.length()-1);
						if (page.indexOf("ndash")>=0)
						{
							host+=", pp. "+page;	
						}
						else
						{
							host+=", p. "+page;
						}
					}
					else
					{
							if (page.indexOf("ndash")>=0)
							{
								host+=" pp. "+page;
							}
							else
							{
								host+=" p. "+page;
							}
					}
				}
				vnr="";	
			}
			else
			{
				host+=page;
			}		
		}
		//--------------------------------------------------------------------------------------		
		else  if((xmlObj.bibStyle).equals("4"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if(xmlObj.jid.equals("PROTIS"))//05-07-2010
				{
					if (page.length()>0)
					host+=":"+page;
					//else
					//	host+=":";
					vnr="";
				}
				else
				{
					if (page.length()>0)
						host+=":"+page;
					else
						host+=".";
					vnr="";
				}
			}
			else if (hostType.equals("<SB:EDITED-BOOK>"))
			{
				if (page.length()>0)
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						host+=" pp "+page;
					}
					else
					host+=" p. "+page;
				}
				vnr="";	
				//System.out.println("1 host====> "+host);
			}
			else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))//updated in Ref_Style (4) on 03-05-2013 for MFC Guide Version 3.0
			{
				if(!page.equals(""))
				{
					host+=". p. "+page;
				}
				else
				{
					host+=page;
					//System.out.println("2 page====> "+page);
				}
			}
			else
			{
				host+=page;
				//System.out.println("2 page====> "+page);
			}
			//System.out.println("host====> "+host);
		}
		//--------------------------------------------------------------------------------------		
		//Kishor [19/06/2004]
		else if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
		{
			//System.out.println("global.pub_for_ref5===>"+global.pub_for_ref5);
			//System.out.println("hostType===>"+hostType);
			//System.in.read();
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if (host.endsWith(","))
					{
						host+=" "+page+".";
						
					}
					else
					{
						if(xmlObj.bibStyle.equals("5-BookB"))
						{
							host+="{\\bf{,}} "+page+".";
						}
						else
						{
							if (xmlObj.jid.equals("WORBUS"))// Gaurav -21-08-04----Style 5 with Deviation
							{
								host+=": "+page+".";
							}
							else if (xmlObj.jid.equals("RETAIL"))// Gaurav -21-08-04----Style 5 with Deviation
							{
								//host+=", "+page+".";//old
								//Added by Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
								host+=" "+page+".";
								//System.out.println("host --> "+host);
								//System.in.read();
							}
							else
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									host+=": "+page+".";
								}
								else
								{
									host+=", "+page+".";
								}
								//host+=", "+page+".";//01/12/2007
								//System.out.println("host---> "+host);
								//System.in.read();
								
							}
						}
						
					}
				}
				if (mulHost==true && mulhostDate.length()>0)
				{
					if(host.endsWith("."))
						host=host.substring(0,host.length()-1);
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				
				if (page.length()>0 && xmlObj.jid.equals("RETAIL"))
				{
					host+=", "+page+".";					
				}
				
				else if (page.length()>0)
				{
					
					if (page.indexOf("ndash")>=0)
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							if(xmlObj.bibStyle.equals("5-BookB"))
							{

								if (host.endsWith(", ")||host.endsWith(". "))
								{
									host+=" pp. "+page+",";
								}
								else
								{
									host+=", pp. "+page+",";
								}
								
							}							
							else
							{
							//	host+=" (pp. "+page+").";//old
							/*[ref style 5: 
							    EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
							
						 	   if(edtion1.length()>0)
								{
								  if(VolSr.length()>0)
									{
										
										host+=" ("+edtion1+", "+VolSr+", pp. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", pp. "+page+").";
										edtion1="";
									}
									
								}
								else
								{
									host+=" (pp. "+page+").";
								}
								
							}
						}
						else if (host.endsWith("ed.). "))  // 27-07-04 in case of edition + pages
						{							
							host = host.substring(0, host.length()-3)+",";
							host+=" pp. "+page+").";
						}
						else
						{
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								if (host.endsWith(", ")||host.endsWith(". ")||host.endsWith(",")||host.endsWith("."))
								{
									host+=" pp. "+page+",";
								}
								else
								{
									if (host.endsWith(" "))   //Gaurav & Rajeev --06-08-04--
									{
										host=host.substring(0,host.length()-1);
									}
									host+=", pp. "+page+",";
								}
							}
							else
							{
								
							//	host+=" (pp. "+page+").";//old
							/*[ref style 5: 
							    EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
						 	   if(edtion1.length()>0)
								{
									
								  if(VolSr.length()>0)
									{
										host+=" ("+edtion1+", "+VolSr+", pp. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", pp. "+page+").";
										edtion1="";
									}
								}
								else
								{
									if(xmlObj.bibStyle.equals("IChemE"))
									{
										host+=" "+global.pub_for_ref5+",";
										host+=" pp. "+page+".";
									}
									else
									{
										host+=" (pp. "+page+").";
									}
									//host+=" (pp. "+page+").";//01/12/2007
								}
								//System.out.println("host==>"+host);
								//System.in.read();
							}
						}
					}
					else
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								host+=" p. "+page+",";
							}
							else
							{
								//	host+=" (p. "+page+").";//old
								/*[ref style 5: 
								EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
								*/
						
								if(edtion1.length()>0)
								{
									if(VolSr.length()>0)
									{
										host+=" ("+edtion1+", "+VolSr+", p. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", p. "+page+").";
										edtion1="";
									}
								}
								else
								{
									host+=" (p. "+page+").";
								}
							}
						}
						else if (host.endsWith("ed.). "))  // 27-07-04 in case of edition + pages
						{
							host = host.substring(0, host.length()-3)+",";
							host+=" p. "+page+").";
						}
						else
						{
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								host+=" p. "+page+",";
							}
							else
							{
			
								host+=" (p. "+page+").";
							}
						}
					}
				}
				
				if (global.pub_for_ref5.length()>0)
				{
					
					if(xmlObj.bibStyle.equals("5-BookB"))
					{
		
						if (host.endsWith(".") || host.endsWith(". ") || host.endsWith("? "))
						{
							host+=" "+global.pub_for_ref5;
						}
						else
						{
							if (host.endsWith(" "))
							{
								host= host.substring(0,host.length()-1);
							}
							if (host.endsWith("?}")||host.endsWith(","))
							{
								host+=" "+global.pub_for_ref5;							
							}
							else
							{
								host+=", "+global.pub_for_ref5;
							}						
						}
					}
					else //[ref. style 5]
					{
						
						if (host.endsWith(".") || host.endsWith(". ") || host.endsWith("? "))
						{
							/**
								* Date : 26/11/2009
								* Requirement: Publication and location are missing in Edited Book eg.(CHRED/409/S100/Ref1)
								* Dot added at end of publisher name
							*/
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								host+=" "+global.pub_for_ref5+".";
							}
							else
							host+=" "+global.pub_for_ref5;
							//System.out.println("global.pub_for_ref5===>"+global.pub_for_ref5);
							//System.out.println("host===>"+host);
							//System.in.read();
							//System.out.println("global.pub_for_ref5===>"+global.pub_for_ref5);
							//System.in.read();
						}
						else
						{
							if (host.endsWith(" "))
							{
								host= host.substring(0,host.length()-1);
							}
							if (host.endsWith("?}"))
							{
								host+=" "+global.pub_for_ref5;							
								
							}
							else
							{
								host+=". "+global.pub_for_ref5;
								
							}	
							
						}
					}
					if (mulHost==true && mulhostDate.length()>0)
					{
						if(host.endsWith("."))
							host=host.substring(0,host.length()-1);
						host+=" ("+mulhostDate+")";
						mulhostDate="";
					}
				}//end of globalpubref
				vnr="";
			}
			else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))
			{
				if(!(isbookpagewithedition))
				{
					host+=page;
				}
			}
			else
			{
				host+=page;
				if (mulHost==true && mulhostDate.length()>0)
				{
					if(host.endsWith("."))
						host=host.substring(0,host.length()-1);
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
			}
		}
		else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
		{
			
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if (host.endsWith(","))
					{
						host+=" "+page+".";
						
					}
					else
					{
						if(xmlObj.bibStyle.equals("5-BookB"))
						{
							host+="{\\bf{,}} "+page+".";
						}
						else
						{
							if (xmlObj.jid.equals("WORBUS"))// Gaurav -21-08-04----Style 5 with Deviation
							{
								host+=": "+page+".";
							}
							else if (xmlObj.jid.equals("RETAIL"))// Gaurav -21-08-04----Style 5 with Deviation
							{
								//host+=", "+page+".";//old
								//Added by Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
								host+=" "+page+".";
								//System.out.println("host --> "+host);
								//System.in.read();
							}
							else
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									host+=": "+page+".";
								}
								else
								{
									host+=", "+page+".";
								}
								//host+=", "+page+".";//01/12/2007
								//System.out.println("host---> "+host);
								//System.in.read();
								
							}
						}
						
					}
				}
				if (mulHost==true && mulhostDate.length()>0)
				{
					if(host.endsWith("."))
						host=host.substring(0,host.length()-1);
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
					
				
				if (page.length()>0 && xmlObj.jid.equals("RETAIL"))
				{
					host+=", "+page+".";					
				}
				
				else if (page.length()>0)
				{
					
					if (page.indexOf("ndash")>=0)
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							if(xmlObj.bibStyle.equals("5-BookB"))
							{

								if (host.endsWith(", ")||host.endsWith(". "))
								{
									host+=" pp. "+page+",";
								}
								else
								{
									host+=", pp. "+page+",";
								}
								
							}							
							else
							{
							//	host+=" (pp. "+page+").";//old
							/*[ref style 5: 
							    EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
							
						 	   if(edtion1.length()>0)
								{
								  if(VolSr.length()>0)
									{
										
										host+=" ("+edtion1+", "+VolSr+", pp. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", pp. "+page+").";
										edtion1="";
									}
									
								}
								else
								{
									host+=" (pp. "+page+").";
								}
								
							}
						}
						else if (host.endsWith("ed.). "))  // 27-07-04 in case of edition + pages
						{							
							host = host.substring(0, host.length()-3)+",";
							host+=" pp. "+page+").";
						}
						else
						{
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								if (host.endsWith(", ")||host.endsWith(". ")||host.endsWith(",")||host.endsWith("."))
								{
									host+=" pp. "+page+",";
								}
								else
								{
									if (host.endsWith(" "))   //Gaurav & Rajeev --06-08-04--
									{
										host=host.substring(0,host.length()-1);
									}
									host+=", pp. "+page+",";
								}
							}
							else
							{
								
							//	host+=" (pp. "+page+").";//old
							/*[ref style 5: 
							    EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
						 	   if(edtion1.length()>0)
								{
									
								  if(VolSr.length()>0)
									{
										host+=" ("+edtion1+", "+VolSr+", pp. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", pp. "+page+").";
										edtion1="";
									}
								}
								else
								{
									if(xmlObj.bibStyle.equals("IChemE"))
									{
										host+=" "+global.pub_for_ref5+",";
										host+=" pp. "+page+".";
									}
									else
									{
										host+=" (pp. "+page+").";
									}
									//host+=" (pp. "+page+").";//01/12/2007
								}
								//System.out.println("host==>"+host);
								//System.in.read();
							}
						}
					}
					else
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								host+=" p. "+page+",";
							}
							else
							{
								//	host+=" (p. "+page+").";//old
								/*[ref style 5: 
								EBT consists of page number and edition;
								and page number is tagged, it should be
								set after edited book title within parentheses
								including edition followed by comma]
								[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
								*/
						
								if(edtion1.length()>0)
								{
									if(VolSr.length()>0)
									{
										host+=" ("+edtion1+", "+VolSr+", p. "+page+").";
										VolSr="";
										edtion1="";
									}
									else
									{
										host+=" ("+edtion1+", p. "+page+").";
										edtion1="";
									}
								}
								else
								{
									host+=" (p. "+page+").";
								}
							}
						}
						else if (host.endsWith("ed.). "))  // 27-07-04 in case of edition + pages
						{
							host = host.substring(0, host.length()-3)+",";
							host+=" p. "+page+").";
						}
						else
						{
							if(xmlObj.bibStyle.equals("5-BookB"))
							{
								host+=" p. "+page+",";
							}
							else
							{
			
								host+=" (p. "+page+").";
							}
						}
					}
				}
				
				if (global.pub_for_ref5.length()>0)
				{
					if(xmlObj.bibStyle.equals("5-BookB"))
					{
		
						if (host.endsWith(".") || host.endsWith(". ") || host.endsWith("? "))
						{
							host+=" "+global.pub_for_ref5;
						}
						else
						{
							if (host.endsWith(" "))
							{
								host= host.substring(0,host.length()-1);
							}
							if (host.endsWith("?}")||host.endsWith(","))
							{
								host+=" "+global.pub_for_ref5;							
							}
							else
							{
								host+=", "+global.pub_for_ref5;
							}						
						}
					}
					else //[ref. style 5]
					{					
						if (host.endsWith(".") || host.endsWith(". ") || host.endsWith("? "))
						{
							
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								
							}
							else
							host+=" "+global.pub_for_ref5;
							
							//System.out.println("global.pub_for_ref5===>"+global.pub_for_ref5);
							//System.in.read();
						}
						else
						{
							if (host.endsWith(" "))
							{
								host= host.substring(0,host.length()-1);
							}
							if (host.endsWith("?}"))
							{
								host+=" "+global.pub_for_ref5;							
								
							}
							else
							{
								host+=". "+global.pub_for_ref5;
								
								
							}						
						}
					}
					if (mulHost==true && mulhostDate.length()>0)
					{
						if(host.endsWith("."))
							host=host.substring(0,host.length()-1);
						host+=" ("+mulhostDate+")";
						mulhostDate="";
					}
				}//end of globalpubref
				vnr="";
			}
			else
			{
				host+=page;
				if (mulHost==true && mulhostDate.length()>0)
				{
					if(host.endsWith("."))
						host=host.substring(0,host.length()-1);
					host+=" ("+mulhostDate+")";
					mulhostDate="";
				}
			}
		}
		//--------------------------------------------------------------------------------------		
		//
		else if((xmlObj.bibStyle).equals("DDT-NS"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if (host.endsWith(","))
					{
						host+=" "+page+".";
					}
					else
					{						
							host+=", "+page+".";			
						
					}
				}
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (page.length()>0)
				{
					
					if (page.indexOf("ndash")>=0)
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							host+=" pp. "+page+",";
						}						
						else
						{
						host+=" pp. "+page+",";
						}
					}
					else
					{
						if (host.endsWith("}}."))  // 27-07-04 in case of Volume + pages
						{
							host = host.substring(0, host.length()-1);
							host+=" p. "+page+",";
						}						
						else
						{
									
						host+=" p. "+page+",";
						}
					}
				}	
							
				if (global.pub_for_ref5.length()>0)
				{
					
					if (host.endsWith(",") || host.endsWith(", ") || host.endsWith("? "))
					{
						host+=" "+global.pub_for_ref5;
						
					}
					else
					{
						if (host.endsWith(" "))
						{
							host= host.substring(0,host.length()-1);
						}
						if (host.endsWith("?}"))
						{
							host+=" "+global.pub_for_ref5;							
						
						}
						else
						{
							host+=", "+global.pub_for_ref5;
							
						}						
					}
				}
				vnr="";
			}
			else
			{
				host+=page;
			}
		}
		
		//--------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("5-Asw"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
				{
					if (host.endsWith(","))
					{
						host+=" "+page+".";
					}
					else
					{
						host+=", "+page+".";
					}
				}
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (page.length()>0)
				{
					if (page.indexOf("ndash")>=0)
					{
					//	host+=" (pp. "+page+").";//old
						/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
						*/
						
						if(edtion1.length()>0)
						{
							if(VolSr.length()>0)
							{
								host+=" ("+edtion1+", "+VolSr+", pp. "+page+").";
								VolSr="";
								edtion1="";
							}
							else
							{
								host+=" ("+edtion1+", pp. "+page+").";
								edtion1="";
							}
						}
						else
						{
							host+=" (pp. "+page+").";
						}
						
					}
					else
					{
						
					//	host+=" (p. "+page+").";//old
						/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
						*/
						
						if(edtion1.length()>0)
						{
							if(VolSr.length()>0)
							{
								host+=" ("+edtion1+", "+VolSr+", p. "+page+").";
								VolSr="";
								edtion1="";
							}
							else
							{
								host+=" ("+edtion1+", p. "+page+").";
								edtion1="";
							}
						}
						else
						{
							host+=" (p. "+page+").";
						}
						
					}
				}
				if (global.pub_for_ref5.length()>0) //22-07-04
				{
					if (host.endsWith(".") || host.endsWith(". "))
					{
						host+=" "+global.pub_for_ref5;
					
						//System.out.println("0 host=> "+host);
						//System.in.read();
					}
					else
					{
						if (host.endsWith(" "))
						{
							host= host.substring(0,host.length()-1);
						}						
						host+=". "+global.pub_for_ref5;
						
					}
				}
				//System.out.println(VolSr+"1 host ===> "+host);
				vnr="";
			}
			else
			{
				host+=page;
				//System.out.println("2 host ===> "+host);
			}
			//System.out.println("3 host ===> "+host);
			//System.in.read();
		}
		//----------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("5-Manage"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
					host+=" "+page+".";
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (page.length()>0)
				{	
					if (host.endsWith(" "))
					{
						host= host.substring(0,host.length()-1);
						host=host+"{\\it :}";
					}
					host+=" "+page+".";
				}
				
				if (global.pub_for_ref5Manage.length()>0) //16-08-04(Gaurav For Manage Article)
				{
					if (host.endsWith(".") || host.endsWith(". "))
					{
						host+=" "+global.pub_for_ref5Manage;
					}
					else
					{
						if (host.endsWith(" "))
						{
							host= host.substring(0,host.length()-1);
						}						
						host+=". "+global.pub_for_ref5Manage;
					}
				}
				
				vnr="";
			}
			else
			{
				host+=page;
			}
			
		}
		//--------------------------------------------------------------------------------------		
		else  if((xmlObj.bibStyle).equals("Old_6"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if (page.length()>0)
					host+=":"+page;
				else
					host+=".";

				vnr="";
			}
			else if (hostType.equals("<SB:EDITED-BOOK>"))
			{
				
				if ( (global.cont_avail.equals("yes")) && 
					 (global.dkj_edited_book.equals("yes")) && 
					 (global.dkj_book_series.equals("yes"))
				   )
				{
					host=host+global.dkj_pub+"; "+global.dkj_date+":"+page+"."+global.dkj_bSer;
				}
				else
				{
					if (page.length()>0)
					{
						if (host.endsWith("."))
						{
							host= host.substring(0,host.length()-1);
						}
						host+=":"+page;
					}
					vnr="";	
				}
			}
			else
			{
				host+=page;
			}		
		}
		//--------------------------------------------------------------------------------------		
		else
		{
			host+=page;
		}
		//--------------------------------------------------------------------------------------		
		if(doi.length()>0)
		{
			
			String duckaid=xmlObj.aid;
			if(duckaid.indexOf(".")!=-1)
				duckaid=duckaid.substring(0,duckaid.indexOf("."));
			if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
				{

					if(host.endsWith(","))
					{
						host=host.substring(0,host.length()-1);	
						host=host+".";
					}
					if(!host.endsWith("."))
					{
						host=host+".";
					}
				}
			if(!host.endsWith(",")|| !host.endsWith("."))
			{
				//System.out.println("host===>>"+host+":::");
				if(host.endsWith(" "))
				{
					host=host.substring(0,host.length()-1);
				}
				//host=host.trim();
				if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
				{
				}
				else
				host+=",";
			}
			String temp_aid=xmlObj.aid;
			//System.out.println("1111 "+temp_aid);
			if(temp_aid.indexOf(".",0)!=-1)
				temp_aid=temp_aid.substring(0,temp_aid.indexOf(".",0));
			//System.out.println("222222 "+temp_aid);
			boolean isNewDOI=XT.NewDOI(xmlObj.jid, temp_aid);
			if((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("4"))
			{
				if(isNewDOI)//22-03-2012
				{
					if(xmlObj.jid.equalsIgnoreCase("IMR")||xmlObj.jid.equalsIgnoreCase("RESUS"))
					{
						host+=" \\newtextdoi{"+doi+"}";
					}
					else
					{
						//Updated on 06-10-2018, mail by PDD to remove underline in reference style 3,3a,4 and 6
//						host+=" \\underline{\\newtextdoi{"+doi+"}}";
						host+=" \\newtextdoi{"+doi+"}";
					}
				}else{
//					host+=" \\underline{doi:\\textdoi{"+doi+"}}";//Updated on 06-10-2018, mail by PDD to remove underline in reference style 3,3a,4 and 6
					host+=" {doi:\\textdoi{"+doi+"}}";
				}
			}
			else if((xmlObj.bibStyle).equals("3-ZFEQ"))
			{
				if(isNewDOI){//22-03-2012
					host+=" \\underline{\\newtextdoi{"+doi+"}}";
				}else{
					host+=" \\underline{doi:\\textdoi{"+doi+"}}";
				}
			}
			else
			{
				//host+=" doi:"+doi; //old
			
				//[Added By Ravi 14/12/2006]
				//[Change in ref style 5 by Vivek ]
				//[Comment : before doi there should be full stop]
				if((xmlObj.bibStyle.startsWith("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
				{
					host=host.substring(0,host.length()-1);
					if(isNewDOI)//22-03-2012
					{
						host+=" \\newtextdoi{"+doi+"}";
					}
					else
					host+=" doi:\\textdoi{"+doi+"}";
				}
				else
				{
					if(isNewDOI)//22-03-2012
					{
						host+=" \\newtextdoi{"+doi+"}";
					}
					else
					host+=" doi:\\textdoi{"+doi+"}";
				}
				
			
			}
			doi="";	
			
		}
		vnr="";
		page="";
		
		//System.out.println("bibHost(): "+host);
		//System.in.read();
		return host;
	}//end of bibHost()
	//--------------------------------------------------------------------------------------
	
	private String bibIssue() throws IOException
	{
		String issue = new String();
		String sTag = new String();
		String edit = new String();
		String title = new String();
		//String ttile=new String();
		String conf = new String();
		String ser = new String();
		String issnr = new String();
		String date = new String();
		boolean isMultipleEditor = false;
		int ch = 0;
		while(!sTag.equals("</SB:ISSUE>"))
		{
			ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag= xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:EDITORS>"))
				{
					edit = edit + sbEditors(); 
					
					isMultipleEditor=isMultipleEditors;
					isMultipleEditors=false;
				}
				else if(sTag.equals("<SB:TITLE>"))
				{
					title = getBibTitle("</SB:TITLE>");
					//System.out.println("title 1 -->"+title)	;
					if(physt_brv.length()>0)
						{
							physt_brv+=". "+title;
							//System.out.println("physt_brv 1 -->"+physt_brv)	;
						//	System.in.read();
						} //Gaurav For Book Review SOCSCI--11/25/04
					else
					{
						physt_brv=title;
						//System.out.println("physt_brv 1 -->"+physt_brv)	;
					}  //Gaurav For Book Review SOCSCI--11/25/04

				}				
				else if(sTag.equals("<SB:CONFERENCE>"))
				{
					conf = xmlObj.extractData("</SB:CONFERENCE>", true);
					//System.out.println("con-----sb-----"+conf);
				}
				else if(sTag.equals("<SB:SERIES>"))
				{
					ser = bibSeries();
					//System.out.println("ser : "+ser);
				}
				else if(sTag.equals("<SB:ISSUE-NR>"))
				{
					if((xmlObj.bibStyle).equals("1") && xmlObj.jid.equalsIgnoreCase("INFMAN"))
					{
						ser = ser.replaceAll("\\(([0-9]+)\\)","$1");
					}
					issnr = xmlObj.extractData("</SB:ISSUE-NR>", true);
					//System.out.println("issnr============>>"+issnr);
				}
				else if(sTag.equals("<SB:DATE>"))
				{
					if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
					{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
						//System.out.println("date == > "+date);
						//System.in.read();
					}
					/*else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
					{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
						//System.out.println("date == > "+date);
						//System.in.read();
					}*/
					else
					{
						

						date = xmlObj.extractData("</SB:DATE>", true);
						if(tempLabel.length()>0) 
						{
								if(tempLabel.startsWith(date))
								date=tempLabel;
								tempLabel="";
						}						
					}
				}
			}
		}
		//Combine All the data according to the Journal Specification
		//ser=jtitle+vol
		//----------------------------------------------------------------------------
		if((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a")|| (xmlObj.bibStyle).equals("1b"))
		{
			//System.out.println("issue11===>"+issue);
			if(edit.length()>0)
			{	if(authorExist== true)
				{ 
					if(xmlObj.jid.equals("SYAPM"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							issue=". En: ";
						else
						issue=". In: ";
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							issue=", en: ";
						else
						{
							if(!xmlObj.jid.equals("JASCER"))
								issue=", in: ";
						}
					}
				}
				if(xmlObj.jid.equals("JASCER"))
				{
					if(title.length()>0)
					{
						if(edit.length()>0)//20-08-04 Gaurav-- Based on MASPEC 12785---
						{
							issue+=", in "+title;
							
						}
						else
						{
							issue+=", in "+title; 
						}
						//System.out.println("title===>"+title);
					}
				}
				else
				{
					issue+=edit;
					if(isMultipleEditor==true)
					{
					   if (global.cont_avail.equals("yes") && title.length()==0)//26-07-04----Gaurav & Rajeev
						{
						   issue+=" (Eds.)";
						}
						else
						{
							issue+=" (Eds.), ";
						}
						
					}
					else
					{
						issue+=" (Ed.), ";
					}
				}
			}
			//System.out.println("issue11===>"+issue);
			if(xmlObj.jid.equals("JASCER"))
			{
				if(!edit.equals(""))
				{
					//System.out.println("edit===>"+edit);
					edit=", Ed. by "+edit;
				}
				issue+=edit;
			}
			else
			{
				if(title.length()>0)
				{
					if(edit.length()>0)//20-08-04 Gaurav-- Based on MASPEC 12785---
					{
						issue+=title;
						
					}
					else
					{
						if(xmlObj.jid.equals("SYAPM"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								issue+=". En: "+title; 
							else
							issue+=". In: "+title; 
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								issue+=", en: "+title; 
							else
							issue+=", in: "+title; 
						}
					}
					
				}
			}
			if(conf.length()>0)
			{
				issue+=", "+conf;
			}
			if (journalTitle.length()>0)
			{
				if(artTitleExist== true || title.length()>0)
				{	//Kishor[Article Title end with ? then no comma][7/07/2004]
					if (ATendwithQ==true)
					{
						issue+=" ";
						ATendwithQ=false;
					}
					else
					{
						
						if(xmlObj.jid.equals("SYAPM"))
						{
							issue+=". ";
						}
						else
						{
							issue+=", ";
						}
					}
				}
				else
				{
					issue+=" ";
				}
				journalTitle ="";
			}
			issue+=ser;
			if(issnr.length()>0)
				issue+=" ("+issnr+")";
			if(xmlObj.jid.equals("SYAPM"))
			{
				tempDateForSYAPM="("+date+")";
			}
			else if(xmlObj.jid.equals("JASCER"))
			{
				tempDateForJASCER="("+date+")";
			}
			else
			{
				issue+=" ("+date+")";
			}

						
				

		//System.out.println("issue22===>"+issue);
			
		}
		//---------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("brvhead"))
		{
			if(edit.length()>0)
			{	if(authorExist== true)
				{ 
					if(XT.articleType.equalsIgnoreCase("ES"))
						issue=", en: ";
					else
					issue=", in: ";
				}
				issue+=edit;

				if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
				{
					if(isMultipleEditor==true)
					{
					   if (global.cont_avail.equals("yes") && title.length()==0)
						{
						   issue+=" (Eds)";
						}
						else
						{
							issue+=" (Eds). ";
						}
						
					}
					else
					{
						issue+=" (Ed). ";
					}
				}
				else
				{
					if(isMultipleEditor==true)
					{
					   if (global.cont_avail.equals("yes") && title.length()==0)
						{
						   issue+=" (Eds.)";
						}
						else
						{
							issue+=" (Eds.). ";
						}
						
					}
					else
					{
						issue+=" (Ed.). ";
					}
				}
			}
			if(title.length()>0)
			{
				if(edit.length()>0)
				{
					issue += title;
				}
				else
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						issue += ", en: "+title; 
					else
					issue += ", in: "+title; 
				}
				
			}
			if(conf.length()>0)
			{
				issue += ", " + conf;
			}
			if (journalTitle.length()>0)
			{
				if(artTitleExist== true || title.length()>0)
				{
					if (ATendwithQ==true)
					{
						issue+=" ";
						ATendwithQ=false;
					}
					else
					{
						issue+=", ";
					}
				}
				else
				{
					issue+=" ";
				}
				journalTitle ="";
			}
			issue+=ser;
			if(issnr.length()>0)
				issue+=" ("+issnr+")";
				//issue+=" ("+date+")";//old 16/05/2008
				if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
				issue+=", "+date;
				else
				issue+=" ("+date+")";

		}
		//----------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("2"))
		{
			//System.out.println("transTitle "+transTitle);
			if (mulHost==true)
			{
				mulhostDate=date;
				date="";
			}
			if(authorExist == true)
			{
				issue = issue+" " + date+".";

				if (artTitle.length() > 0)
				{
					if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&!artTitle.endsWith("?{''}"))
					{						
							if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
							{
								if(global.dkj_edited_book.equals("yes"))
								{}
								else
								{
									artTitle = artTitle +".";
								}
							}
							else
								artTitle = artTitle +".";
					}
					issue = issue +" "+ artTitle;
					if(transTitle.length()>0)
					{
						if (issue.endsWith("."))
						{
							issue = issue.substring(0,issue.length()-1);
						}
						issue = issue +" ("+ transTitle+").";
						
					}

					artTitle = "";
					transTitle="";
				}
				else if(transTitle.length()>0)
				{
					issue = issue + transTitle;
					
					transTitle="";
				}
				if(edit.length()>0)
				{
					if(artTitleExist== true)
					{
						if(issue.endsWith("."))
						{
							issue=issue.substring(0,issue.length()-1);
						}
					if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))	
						issue+=", "; //26-07-04
					else
						issue+=". ";
					}
					issue+=edit;
					if(isMultipleEditor==true)
					{
						issue+=" (Eds.), ";
						
					}
					else
					{
						issue+=" (Ed.), ";
					}
					//System.out.println("-----------------------------------");
				}
			}
			else
			{
				if(edit.length()>0)
				{
					issue+=edit;
					if(isMultipleEditor==true)
					{
						issue+=" (Eds.), ";
						
					}
					else
					{
						issue+=" (Ed.), ";
					}
				}

				//abhay 14/10/2006
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?"))
				{						
					//artTitle = artTitle +".";
				}
				//issue = issue +" "+ artTitle;

				//Condition updated where Article title to be appeared in case of no Author and no Editor.
				//09-09-2013
				if(authorExist==false && edit.length()==0)
				{
					if(artTitle.length()>0){
						issue = issue +" "+ artTitle+".,";
					}
				}
				//END

				if (mulHost==false)
				{
					issue = issue + " " +date+".";
				}
			}
			if (artComment.length() > 0)
			{
				if(artComment.endsWith(")"))
				{
					issue= issue+artComment+". ";//Gaurav --11/22/04--Based On new Requierment.
				}
				else
				{
					issue= issue+artComment+", ";
				}
				//issue= issue+artComment+", ";
				artComment="";
			}
			if(title.length()>0)
			{
				if(conf.length()>0) //26-07-04
				{
					issue= issue+" "+title+", ";
				}
				else
				{
					issue= issue+" "+title+". ";
				}				
			}
			if(conf.length()>0)
			{
				issue= issue+" "+conf+". "; //26-07-04
			}

		  /*if (journalTitle.length()>0)
			{
				issue+=" "+journalTitle;
				if(!journalTitle.endsWith("."))
					issue+=". ";
				journalTitle ="";
			}
		  */
			issue+=" "+ser;
			if(issnr.length()>0)
			{
				if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
				{
					issue+="("+issnr+")";
				}
				else
				{
					issue+=" ("+issnr+")";
				}
			}
			//System.out.println("1 ---------> "+issue);
		}
		//----------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			if (artTitle.length()>0)
			{
				issue+=" {\\it{"+artTitle+"}} ";
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&& !artTitle.endsWith("?")&&!artTitle.endsWith("!"))
					issue+=".";
				issue+=" ";
				artTitle="";
			}
			//if only ediors in journal reference then handled.

			/*if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						issue+=" (Eds). ";
					}
					else
					{
						issue+=", editors. ";
					}
				}
				else
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						issue+=" (Ed.). ";
					}
					else
					{
						issue+=", editor. ";
					}
				}
			}*/
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
				if (journalTitle.endsWith("."))
				{
					journalTitle=journalTitle.substring(0, journalTitle.length()-1);
				}
				 
			//	 System.out.println("666666666666666journalTitle "+journalTitle);
				issue+=journalTitle;
				issue+=", ";
				journalTitle="";
			}

			if(date.length()>0)
			{
				
				issue+=date;
			}
			if(vnr.length()>0 && ((xmlObj.bibStyle).equals("3-ZEFQ")))
			{
				vnr="{\\bf{"+vnr+"}}";
			}
			if(vnr.length()>0)
			{
				issue+=". ";
				issue+=vnr;
			}
			else
			{
				if (issnr.length()>0) // 27-07-04
				{
					issue+=";";
				}				
			}
			if(issnr.length()>0)
			{	
				issue=issue+"("+issnr+")";
			}
		}
	//----------------------------------------------------------------------------
		//----------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
		{
			//System.out.println("---------->>"+artTitle);
			if (artTitle.length()>0)
			{
				if((xmlObj.bibStyle).equals("3a"))//29-08-2011
				{
					if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//29-08-2011
					{
						issue+=" "+artTitle;
					}
					else
					{
						//issue+=" {\\it{"+artTitle+"}}";
						issue+=" "+artTitle;
					}
				}
				else
				{
					if(xmlObj.jid.equals("OSI"))//21-10-2011
					{
						//issue+=" {\\it{"+artTitle+"}}";//12-03-2012
						issue+=" "+artTitle;//12-03-2012
					}
					else
						issue+=" "+artTitle;
				}
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&& !artTitle.endsWith("?")&&!artTitle.endsWith("!"))
				{
					issue+=".";
				}
				issue+=" ";
				artTitle="";
			}
			if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						issue+=" (Eds). ";
					}
					else
					{
						issue+=", editors. ";
					}
				}
				else
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						issue+=" (Ed.). ";
					}
					else
					{
						issue+=", editor. ";
					}
				}
			}
			//System.out.println("title------->>"+title);
			//System.out.println("journalTitle------->>"+journalTitle);
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
				if (journalTitle.endsWith("."))
				{
					if(xmlObj.jid.equals("NRL")||xmlObj.jid.equals("NRLENG"))
					{
						journalTitle=journalTitle;
						//System.out.println("qqqqqqqqqqqqqqq "+journalTitle);
					}
					if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
					{
						journalTitle=journalTitle;
						//System.out.println("qqqqqqqqqqqqqqq "+journalTitle);
					}
					else
					journalTitle=journalTitle.substring(0, journalTitle.length()-1);
					//System.out.println("qqqqqqqqqqqqqqq "+journalTitle);
				}
				 if((xmlObj.bibStyle).equals("3a") || (xmlObj.bibStyle).equals("6"))
				 {
					 //if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||XT.modelStyle.startsWith("7Spanish"))//16-07-2011
					 if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//08-12-2015//added condition for ref style not 6 to apply else if condition
					 {
						 if (!journalTitle.endsWith("."))
						  journalTitle=journalTitle+".";
					 }
					 else if(xmlObj.bibStyle.equals("6"))
					 {
						 if (!journalTitle.endsWith(".")&& xmlObj.jid.equals("IMR"))
							journalTitle = "{\\it{"+journalTitle+"}}";
						 else if (!journalTitle.endsWith("."))
							journalTitle = "{\\it{"+journalTitle+"}}.";
						 else
							 journalTitle = "{\\it{"+journalTitle+"}}";
					 }
					 else
					 {
						 journalTitle = "{\\it{"+journalTitle+"}}";
						//System.out.println("----------->>"+XT.articleType);
					 //System.out.println("qqqqqqqqqqqqqqq "+journalTitle);
					 }
				 }
//					System.out.println("journalTitle: "+journalTitle);
				 if(xmlObj.jid.equals("NRL")||xmlObj.jid.equals("NRLENG"))
					issue+=journalTitle+".";
				 else if(xmlObj.jid.equals("OSI")&&xmlObj.bibStyle.equals("3"))//ravi 15-04-2011
					issue+="{\\it{"+journalTitle+"}}";
				 else
					issue+=journalTitle;
				 //System.out.println("----------------------->>"+issue);
				issue+=" ";
				journalTitle="";
			}
//			System.out.println("bibIssue(): "+issue);

			if(date.length()>0)
			issue+=date;
			//System.out.println("vnr---> "+vnr);
			if(vnr.length()>0 && ((xmlObj.bibStyle).equals("3a")||(xmlObj.bibStyle).equals("yijom-ns")|| (xmlObj.bibStyle).equals("6")))
			{
				//if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish"))//16-07-2011
				if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//Condition added on 08-12-2015 to  ignore ref style 6
				{
					vnr=vnr;
				}
				else if(xmlObj.bibStyle.equals("6"))
				{
					vnr=vnr;
				}
				else
				{
					String duckaid=xmlObj.aid;
					if(duckaid.indexOf(".")!=-1)
						duckaid=duckaid.substring(0,duckaid.indexOf("."));
					
					if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
					{
						vnr=" "+vnr;
					}
					else
					vnr="{\\bf{"+vnr+"}}";
				}
			}
			if(vnr.length()>0)
			{
				if((xmlObj.bibStyle).equals("yijom-ns"))
				{
					issue+=": ";
				}
				else
				{
					issue+=";";
				}
				issue+=vnr;
			}
			else
			{
				if (issnr.length()>0) // 27-07-04
				{
					if((xmlObj.bibStyle).equals("yijom-ns"))
					{
						issue+=": ";
					}
					else
					{
						issue+=";";
					}
				}				
			}
			if(issnr.length()>0)
			{				
				if((xmlObj.bibStyle).equals("yijom-ns")||xmlObj.jid.equals("CLCHIR"))
				{
					issue=issue+"({\\bf{"+issnr+"}})";
				}
				else
				{
					//if(((issnr.toLowerCase().startsWith("suppl"))||(issnr.toLowerCase().startsWith("supl"))||(issnr.indexOf("Pt ",0)!=-1))&&( XT.Spanish_Reference_Jid.contains(xmlObj.jid)||XT.modelStyle.startsWith("7Spanish")))//16-07-2011
					if(((issnr.toLowerCase().indexOf("suppl",0)!=-1)||(issnr.toLowerCase().indexOf("supl",0)!=-1)||(issnr.indexOf("Pt ",0)!=-1))&&( XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6"))))//16-07-2011
					{
						issue=issue+" "+issnr;
					}
					else
					issue=issue+"("+issnr+")";
				}
			}
		}
	//----------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("3a-Jecs"))
		{
			if (artTitle.length()>0)
			{
				issue+=" "+artTitle;
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&&!artTitle.endsWith("?")&&!artTitle.endsWith("!"))
					issue+=".";
				issue+=" ";
				artTitle="";
			}
			if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					issue+=", ed. ";
				}
				else
				{
					issue+=", ed. ";
				}
			}
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
						
				journalTitle = "{\\it{"+journalTitle+"}},";
				issue+=journalTitle;
				issue+=" ";
				journalTitle="";
			}

			if(date.length()>0)
			{
				issue+=date;
			}
			if(vnr.length()>0)
			{
				vnr="{\\bf{"+vnr+"}}";
			}
			if(vnr.length()>0)
			{
				issue+=", ";
				issue+=vnr;
			}
			//vnr="";
			if(issnr.length()>0)
				issue=issue+"("+issnr+")";
		}
		//------------------------------------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("chea"))
		{
			if (artTitle.length()>0)
			{
				issue+=" "+artTitle;
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&&!artTitle.endsWith("?")&&!artTitle.endsWith("!"))
					issue+=".";
				issue+=" ";
				artTitle="";
			}
			if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					issue+=", Eds. ";
				}
				else
				{
					issue+=", Ed. ";
				}
			}
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
						
				journalTitle = "{\\it{"+journalTitle+"}}";
				issue+=journalTitle;
				issue+=" ";
				journalTitle="";
			}

			if(date.length()>0)
			{
				date="{\\bf{"+date+"}},";
				issue+=date;
			}
			if(vnr.length()>0)
			{
				vnr="{\\it{"+vnr+"}}";
			}
			if(vnr.length()>0)
			{
				issue+=" ";
				issue+=vnr;
			}
			if(issnr.length()>0)
				issue=issue+" ("+issnr+")";
		}
		//-------------------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("4"))
		{
			
			if (artTitle.length()>0)
			{
				issue+=" "+artTitle;
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&&!artTitle.endsWith("?")&&!artTitle.endsWith("!"))
					issue+=".";
				issue+=" ";
				artTitle="";
			}
			if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					issue+=", editors. ";
				}
				else
				{
					issue+=", editor. ";
				}
			}
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
				if (journalTitle.endsWith("."))
				{
					journalTitle=journalTitle.substring(0, journalTitle.length()-1);
				}
								
				issue+=journalTitle;
				issue+=" ";
				journalTitle="";
			}

			if(date.length()>0)
			{
				if(xmlObj.jid.equals("PROTIS"))
				{
					tempDateForSYAPM="("+date+")";
					//System.out.println("--------> "+issue);
				}
				else
				{
					issue+=date;
				}
			}
			
			if(vnr.length()>0)
			{
				
				if(xmlObj.jid.equals("PROTIS"))
				{
					issue+=" ";
					issue+="{\\bf{"+vnr+"}}";
				}
				else
				{issue+=";";
					issue+=vnr;
				}
			}
			if(issnr.length()>0)
			{
				if(xmlObj.jid.equals("PROTIS"))
				{
					issue=issue+"{\\bf{("+issnr+")}}";
				}
				else
				{
					issue=issue+"("+issnr+")";
				}
			}
				//System.out.println("issue--------->>"+issue);
		}//end of 4
		//----------------------------------------------------------------------------
		//Kishor [18/06/2004]
		else if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
		{
			if (mulHost==true)
			{
				mulhostDate=date;
				
				date="";
			}
			if(global.cont_avail.equals("yes"))
			{				
				if(date.length()>0)
				{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							issue+=" "+date+", ";
						}
					else
						{
						issue+=" ("+date+"). ";
						}
						//issue+=" ("+date+"). ";//01/12/2007
						//System.out.println("date : "+date);
				}
			
				/*if(date.length()>0)
				{
						issue+=tempbb;
						issue+=" ("+date+"). ";
						issue+=tempone;
						tempone="";
						tempbb="";
						//tempone
				}
				*/
			//	System.out.println("issue --- > "+issue+" Date ----> "+date);
			//	System.in.read();
				if (artTitle.length() > 0)
				{
					if (xmlObj.jid.equalsIgnoreCase("RETAIL"))
					{
						artTitle = " ``"+artTitle+",''";
						//System.out.println(" artTitle 1 -- > "+artTitle);
						//System.in.read();
						
					}
					else if (artTitle.endsWith("?"))
					{
						artTitle = " "+artTitle;
					}
					else if(!artTitle.endsWith("."))
						artTitle = " "+artTitle +".";
					//
					issue = issue + artTitle +" ";
					//Kishor[19/06/2004]-Translated Title:Xml coding for TT will come after AT
					if(transTitle.length()>0)
					{
						if (issue.endsWith(". "))
						{
							issue = issue.substring(0,issue.length()-2);
						}
						issue = issue +" ["+ transTitle+"].";
						
					}
					artTitle = "";
					transTitle="";
				}

				if(edit.length()>0) // 11-11-04
				{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=" (Eds.). ";
						
						}
						else
						{
							issue+=" (Ed.). ";
						}
				}
				/*if (journalTitle.length()>0)
				{
					if(!journalTitle.endsWith("."))
						issue+=journalTitle+". ";
					journalTitle ="";
				}*/
							
			}
			else
			{
				
				if(edit.length()>0)
				{
					if((xmlObj.bibStyle).equals("5-BookB"))
					{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=", eds. ";
						}
						else
						{
							issue+=", ed. ";
						}
					}
					else
					{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=" (Eds.). ";
							
						}
						else
						{
							issue+=" (Ed.). ";
						}
					}
				}
				//Date
				if(date.length()>0)
				{
						issue+=" ("+date+"). ";
					
				}
			}
			//
			if (artComment.length() > 0)
			{
				issue= issue+artComment+", ";
				
				artComment="";
			}
			if(title.length()>0)
			{
				issue= issue+" "+title+". "; // 27-07-04
			}
			if(conf.length()>0)//14-08-04 (Gaurav--Based on Cap Guide --CE:Ajay)
			{
				//issue= issue+" "+conf+", ";
			}
			issue+=" "+ser;
	
			if(issnr.length()>0)
			{				
				if (issue.endsWith(","))
					issue+=" ("+issnr+")";			
				else if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
					issue+=" ("+issnr+")";
				else
					issue+="("+issnr+")";
				//System.out.println("\n\n issnr  "+issnr);
				//System.in.read();
			}
			//System.out.println("\n\n issnr  "+issue);
		}//End-Ref-style [5]
		else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
		{
			if (mulHost==true)
			{
				mulhostDate=date;
				
				date="";
			}
			if(global.cont_avail.equals("yes"))
			{				
				if(date.length()>0)
				{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							issue+=" "+date+", ";
						}
					else
						{
						//issue+=" ("+date+"). ";//14/04/2008
						//issue+=" ("+date+"), ";
						issue+=" ("+date+"), ";
						}
						//issue+=" ("+date+"). ";//01/12/2007
						//System.out.println("date : "+date);
				}
			
				/*if(date.length()>0)
				{
						issue+=tempbb;
						issue+=" ("+date+"). ";
						issue+=tempone;
						tempone="";
						tempbb="";
						//tempone
				}
				*/
			//	System.out.println("issue --- > "+issue+" Date ----> "+date);
			//	System.in.read();
				if (artTitle.length() > 0)
				{
					if (xmlObj.jid.equalsIgnoreCase("RETAIL"))
					{
						artTitle = " ``"+artTitle+",''";
						//System.out.println(" artTitle 1 -- > "+artTitle);
						//System.in.read();
						
					}
					else if (artTitle.endsWith("?"))
					{
						artTitle = " "+artTitle;
					}
					else if(!artTitle.endsWith("."))
						artTitle = " "+artTitle +".";
					//
					//System.out.println("ravi artTitle : "+artTitle);
					//System.in.read();
					issue = issue + artTitle +" ";
					//Kishor[19/06/2004]-Translated Title:Xml coding for TT will come after AT
					if(transTitle.length()>0)
					{
						if (issue.endsWith(". "))
						{
							issue = issue.substring(0,issue.length()-2);
						}
						issue = issue +" ["+ transTitle+"].";
					}
					artTitle = "";
					transTitle="";
				}

				if(edit.length()>0) // 11-11-04
				{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=" (Eds.). ";
						
						}
						else
						{
							issue+=" (Ed.). ";
						}
				}
				/*if (journalTitle.length()>0)
				{
					if(!journalTitle.endsWith("."))
						issue+=journalTitle+". ";
					journalTitle ="";
				}*/
							
			}
			else
			{
				
				if(edit.length()>0)
				{
					if((xmlObj.bibStyle).equals("5-BookB"))
					{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=", eds. ";
						}
						else
						{
							issue+=", ed. ";
						}
					}
					else
					{
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=" (Eds.). ";
							
						}
						else
						{
							issue+=" (Ed.). ";
						}
					}
				}
				//Date
				if(date.length()>0)
				{
						issue+=" ("+date+"). ";
					
				}
			}
			//
			if (artComment.length() > 0)
			{
				issue= issue+artComment+", ";
				
				artComment="";
			}
			if(title.length()>0)
			{
				issue= issue+" "+title+". "; // 27-07-04
			}
			if(conf.length()>0)//14-08-04 (Gaurav--Based on Cap Guide --CE:Ajay)
			{
				//issue= issue+" "+conf+", ";
			}
			issue+=" "+ser;
	
			if(issnr.length()>0)
			{				
				if (issue.endsWith(","))
					issue+=" ("+issnr+")";			
				else if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
					issue+=" ("+issnr+"),";
				else
					issue+="("+issnr+")";
				//System.out.println("\n\n issnr  "+issnr);
				//System.in.read();
			}
			else if(ser.length()>0&&(xmlObj.jid.equalsIgnoreCase("RETAIL")))
			{
				issue+=", ";
			}
		}
		//----------------------------------------------------------------------------
		//Gaurav & Rajeev [3/08/2004]
		else if((xmlObj.bibStyle).equals("DDT-NS"))
		{
			if(global.cont_avail.equals("yes"))
			{				
				if(date.length()>0)
					issue+=" ("+date+") ";
				if (artTitle.length() > 0)
				{
					if (artTitle.endsWith("?"))
					{
						artTitle = " "+artTitle;
					}
					else if(!artTitle.endsWith("."))
						artTitle = " "+artTitle +".";
					//
					issue = issue + artTitle +" ";
					//Kishor[19/06/2004]-Translated Title:Xml coding for TT will come after AT
					if(transTitle.length()>0)
					{
						if (issue.endsWith(". "))
						{
							issue = issue.substring(0,issue.length()-2);//Kishor Help
						}
						issue = issue +" ["+ transTitle+"].";
					}
					artTitle = "";
					transTitle="";
				}
				if (journalTitle.length()>0)
				{
					if(!journalTitle.endsWith("."))
						issue+=journalTitle+" ";
					journalTitle ="";
				}
					
			}
			else
			{
				if(edit.length()>0)
				{				
						issue+=edit;
						if(isMultipleEditor==true)
						{
							issue+=" eds ";
						}
						else
						{
							issue+=" ed. ";
						}					
				}
				//Date
				if(date.length()>0)
					issue+=" ("+date+") ";
			}
			//
			if (artComment.length() > 0)
			{
				issue= issue+artComment+", ";
				artComment="";
			}
			if(title.length()>0)
			{
				issue= issue+" "+title+" "; // 27-07-04
			}
			if(conf.length()>0)
			{
				issue= issue+" "+conf+" ";
			}
			issue+=" "+ser;

			if(issnr.length()>0)
				if (issue.endsWith(","))
					issue+=" ("+issnr+")";			
				else
					issue+="("+issnr+")";			
		}//End-Ref-style [DDT]
		
		//----------------------------------------------------------------------------------
		//Kishor [18/06/2004]
		else if((xmlObj.bibStyle).equals("5-Asw"))
		{
			if(global.cont_avail.equals("yes"))
			{				
				if(date.length()>0)
					issue+=" ("+date+"). ";
				if (artTitle.length() > 0)
				{
					if (artTitle.endsWith("?"))
					{
						artTitle = " "+artTitle;
					}
					else if(!artTitle.endsWith("."))
						artTitle = " "+artTitle +".";
					//
					issue = issue + artTitle +" ";
					//Kishor[19/06/2004]-Translated Title:Xml coding for TT will come after AT
					if(transTitle.length()>0)
					{
						if (issue.endsWith(". "))
						{
							issue = issue.substring(0,issue.length()-2);//Kishor Help
						}
						issue = issue +" ["+ transTitle+"].";
					}
					artTitle = "";
					transTitle="";
				}
				if (journalTitle.length()>0)
				{
					if(!journalTitle.endsWith("."))
						issue+=journalTitle+". ";
					journalTitle ="";
				}
				
			}
			else
			{
				if(edit.length()>0)
				{
					issue+=edit;
					if(isMultipleEditor==true)
					{
						issue+=" (Eds.). ";
						
					}
					else
					{
						issue+=" (Ed.). ";
					}
				}
				//Date
				if(date.length()>0)
					issue+=" ("+date+"). ";
			}
			//
			if (artComment.length() > 0)
			{
				issue= issue+artComment+", ";
				artComment="";
			}
			if(title.length()>0)
			{
				issue= issue+" "+title+". "; // 27-07-04
			}
			if(conf.length()>0)//14-08-04 (Gaurav--Based on Cap Guide --CE:Ajay)
			{
				//issue= issue+" "+conf+", ";
			}
			issue+=" "+ser;
			if(issnr.length()>0)
				if (issue.endsWith(","))
					issue+=" (+"+issnr+")";			
				else
					issue+=" ("+issnr+")"; // Rajeev & Gaurav [19-07-04] Space between Vol . no. & issue no.
			//System.out.println(issue);
		}//End-Ref-style [5]
		//----------------------------------------------------------------------------------------------
		//Kishor [30/06/2004]
		else if((xmlObj.bibStyle).equals("5-Manage"))
		{
			if(global.cont_avail.equals("yes"))
			{				
				if(date.length()>0)
					issue+=" "+date+". ";
				if (artTitle.length() > 0)
				{
					if (artTitle.endsWith("?"))
					{
						artTitle = " "+artTitle;
					}
					else if(!artTitle.endsWith("."))
						artTitle = " "+artTitle +".";
					//
					issue = issue + artTitle +" ";
					//Translated Title:Xml coding for TT will come after AT
					if(transTitle.length()>0)
					{
						if (issue.endsWith(". "))
						{
							issue = issue.substring(0,issue.length()-2);
						}
						issue = issue +" ["+ transTitle+"].";
					}
					artTitle = "";
					transTitle="";
				}
				if (journalTitle.length()>0)
				{
					if(!journalTitle.endsWith("."))
						issue+=journalTitle+". ";
					journalTitle ="";
				}
						
			}
			else
			{
				if(edit.length()>0)
				{
					issue+=edit;
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							issue+=" (Eds.), ";
						}
						else
						{
							issue+=" (Eds.). ";
						}
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{
							issue+=" (Ed.), ";
						}
						else
						{
							issue+=" (Ed.). ";
						}
					}
				}
				//Date
				if(date.length()>0)
					issue+=" "+date+". ";
			}
			//
			if (artComment.length() > 0)
			{
				issue= issue+artComment+", ";
				artComment="";
			}
			if(title.length()>0)
			{
				issue= issue+" "+title+", ";
			}
			if(conf.length()>0)//14-08-04 (Gaurav--Based on Cap Guide --CE:Ajay)
			{
				//issue= issue+" "+conf+", ";
			}
			issue+=" "+ser;
			if(issnr.length()>0)
			{	if (issue.endsWith(","))
					issue+=" ("+issnr+"):";			
				else if (issue.endsWith(":"))
				{	issue = issue.substring(0,issue.length()-1);
					issue+="("+issnr+"):";
				}
				else
					issue+="("+issnr+"):";			
			}
		}//End-Ref-style [5-Manage]
		//----------------------------------------------------------------------------------
		else  if((xmlObj.bibStyle).equals("Old_6"))
		{
			if (artTitle.length()>0)
			{
				issue+=" "+artTitle;
				
				if(transTitle.length()>0)
				{
					issue+=" ["+transTitle+"].";
				}
				else if(!artTitle.endsWith(".")&&!artTitle.endsWith("?")&&!artTitle.endsWith("!"))
					issue+=".";
				issue+=" ";
				artTitle="";
			}
			if(authorExist==true && edit.length()>0)
			{
			}
			else if(edit.length()>0)
			{
				issue+= edit;
				if(isMultipleEditor==true)
				{
					issue+=", eds. ";
				}
				else
				{
					issue+=", ed. ";
				}
			}
			if(title.length()>0)
				issue+=title+". ";

			if (journalTitle.length()>0)
			{
				if(issue.length()==0)
				{
					issue=" ";
				}
				if (journalTitle.endsWith("."))
				{
					journalTitle=journalTitle.substring(0, journalTitle.length()-1);
				}
				journalTitle="{\\it{"+journalTitle+"}.}";
				issue+=journalTitle;
				issue+=" ";
				journalTitle="";
			}

			if(date.length()>0)
				issue+=date;
			if(vnr.length()>0)
			{
				issue+=";";
				issue+=vnr;
			}
			if(issnr.length()>0)
				issue=issue+"("+issnr+")";
		}//end of 6
		//-------------------------------------------------------------------------------------------
		else
		{
			issue = journalTitle+issnr+ser+date;
		}
		//System.out.println("bibIssue(): "+issue);
		//System.in.read();
		return issue;
	}// end of bibIssue()
	//-----------------------------------------------------------------------------------------
	
	private String bibSeries() throws IOException
	{
		String series = new String();
		String sTag = new String();
		String trtitle = new String();
		while(!sTag.equals("</SB:SERIES>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:TITLE>"))
				{
					journalTitle = getBibTitle("</SB:TITLE>");
					//System.out.println("----------->>"+journalTitle);
					
					if((xmlObj.jid.equals("PUBREL"))&&xmlObj.pit.equalsIgnoreCase("BRV")&&(xmlObj.bibStyle.equals("brvhead")))
					{
						journalTitle="{\\bi{"+journalTitle+"}}";
						checkTitle=true;
					}
					
					/*//if(title.length>0)
						{
							System.out.println("journalTitle:"+journalTitle+":");
							//TitleForGlobel
						}
						*/
					if(physt_brv.length()>0)
					{
							physt_brv+=". "+journalTitle;
							//System.out.println("physt_brv 2 -->"+physt_brv)	;
							//System.in.read();
						
					} //Gaurav For Book Review SOCSCI--11/25/04
					else
					{	physt_brv=journalTitle; //Gaurav For Book Review SOCSCI--11/25/04
						//System.out.println("journalTitle-----> "+physt_brv);
					}
				}
				else if(sTag.equals("<SB:TRANSLATED-TITLE>"))
				{
					trtitle = getBibTitle("</SB:TRANSLATED-TITLE>");
					physt_brv+="["+trtitle+"]";

				}
				else if(sTag.equals("<SB:VOLUME-NR>"))
				{
					if((xmlObj.bibStyle).equals("1") && xmlObj.jid.equalsIgnoreCase("INFMAN"))
						vnr = "(" + xmlObj.extractData("</SB:VOLUME-NR>", true) + ")";
					else
					{
						vnr = xmlObj.extractData("</SB:VOLUME-NR>", true);
						isVolinBookSeries=true;
						if(xmlObj.jid.equals("INFBEH"))
						{
							isinfbeh_vol=true;
						}
					}
					//System.out.println("volume-nr ====> "+vnr+"  hostType :: "+hostType);
					//System.in.read();
				}
			}
		}
		
		//-----------------------------------------------------------------------------------
		if(hostType.equals("<SB:ISSUE>"))
		{
			//----------------------------------------------------------------------------
			if ((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a") || (xmlObj.bibStyle).equals("1b"))
			{
				if (journalTitle.length()>0)
				{					
					if(xmlObj.jid.equals("JASCER"))
					series=journalTitle+",";
					else
					series=journalTitle;
					if (vnr.length()>0)
					{
						if(xmlObj.jid.equals("JASCER"))
						series+=" "+vnr+",";
						else
						series+=" "+vnr;
					}
				}
				//System.out.println("series11111111=======>"+series);
			}
			else if ((xmlObj.bibStyle).equals("brvhead"))
			{
				if (journalTitle.length()>0)
				{					
					series=journalTitle;					
					if (vnr.length()>0)
					{
						series+=" "+vnr;
					}
				}
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("2"))
			{
				if (journalTitle.length()>0)
				{			
					series=journalTitle;				
					if (vnr.length()>0)
					{
						series+=" "+vnr;
					}
					else
					{
						series="{"+series+"}";//28-01-2005 to protect abb word
					}
				}
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3-ZEFQ"))
			{
				journalTitle = removeJrnlDot(journalTitle);
				//series="\\it{"+journalTitle+"}";
				
			}
			//----------------------------------------------------------------------------
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
			{
				if(xmlObj.jid.equals("NRL")||xmlObj.jid.equals("NRLENG"))
				{
					series = journalTitle+".";
					//System.out.println("journalTitle ===> "+journalTitle);
				}
				else{
					//System.out.println("journalTitle Before ===>"+journalTitle);
					if(xmlObj.jid.equals("BRACHY")){//[17-08-2017]
						journalTitle = "{\\it{"+removeJrnlDot(journalTitle)+"}}";
					}else{
						journalTitle = removeJrnlDot(journalTitle);	
					}
					//System.out.println("journalTitle After  ===>"+journalTitle);
				}
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3a-Jecs")||(xmlObj.bibStyle).equals("chea"))
			{
				//Don't remove dots from the series title
				//journalTitle = removeJrnlDot(journalTitle);
				//System.out.println("Issue-Series-Title------------["+journalTitle+"]");
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("4"))//Kishor [22/06/2004]
			{
				if(!xmlObj.jid.equals("PROTIS"))//Ravi [09-07-2010]
				{
					journalTitle = removeJrnlDot(journalTitle);
					//System.out.println("journalTitle ===> "+journalTitle);
				}
				//journalTitle = removeJrnlDot(journalTitle);//old
				//journalTitle = ". ";
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))//Kishor [21/06/2004] - When parent is <SB:ISSUE>
			{
				
				//System.out.println("1. series : "+series);
				//System.out.println("1. journalTitle : "+journalTitle);
				if(journalTitle.length()>0)
				{
					if (journalTitle.endsWith("?"))
					{
						series = "{\\it{"+journalTitle+"}}";
					}
					else
					{
						if(xmlObj.bibStyle.equals("DDT-NS")||(xmlObj.bibStyle).equals("5-BookB"))
						{
							series = "{\\it{"+journalTitle+"}}";
						}
						else
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")) && (vnr.length()==0))//wwwwww
							if (xmlObj.deviation.equals("BOOKS") && (vnr.length()==0))//wwwwww
							{
								series = "{\\it{"+journalTitle+"}}";
							}
							else
							{
								//series = "{\\it{"+journalTitle+"}},";
								
								//Added By Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
								if( xmlObj.jid.equalsIgnoreCase("RETAIL"))
								{
									series = "{\\it{"+journalTitle+",}}";
									
								}
								else if( xmlObj.jid.equalsIgnoreCase("ZOOGA"))//12-08-2010
								{
									series = ""+journalTitle+",";
									
								}
								else
								{
									if((xmlObj.bibStyle).equals("IChemE"))
									{
										series = ""+journalTitle+",";
									}
									else
									{
										if( xmlObj.jid.equalsIgnoreCase("INFBEH")&&vnr.length()>0)
										{
											series = "{\\it{"+journalTitle+": "+vnr+".}},";
											vnr="";
										}
										else
										series = "{\\it{"+journalTitle+"}},";
									}
									//series = "{\\it{"+journalTitle+"}},";//01/12/2007
									//System.out.println("1. series : "+series);
									//System.in.read();
								}
								
							}
							
						}
					}
				}
			
				if (vnr.length()>0)
				{
					if(xmlObj.bibStyle.equals("5-BookB"))
					{
						series = series +" " +"{\\bf{" + vnr +"}}";
					}
					else if(xmlObj.bibStyle.equals("DDT-NS") || xmlObj.jid.equalsIgnoreCase("RETAIL"))
					{
						series = series +" " + vnr;
						
					}
					else
					{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							series = series +" " + vnr +"";
						}
						else if(xmlObj.jid.equalsIgnoreCase("WORBUS"))
						{
							series = series +" " + vnr +"";
							//System.out.println("1. vnr : "+vnr);
						}
						else if( xmlObj.jid.equalsIgnoreCase("ZOOGA"))//12-08-2010
						{
							series = series +" " + vnr +"";
						}
						else
						{
							series = series +" " +"{\\it{" + vnr +"}}";
						}
						//series = series +" " +"{\\it{" + vnr +"}}";//01/12/2007
						
						//System.out.println("2. series : "+series);
						//System.in.read();
					}
			
					
				}
				journalTitle ="";
			}
			else if ((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
			{
				
				
				if(journalTitle.length()>0)
				{
					if (journalTitle.endsWith("?"))
					{
						series = "{\\it{"+journalTitle+"}}";
					}
					else
					{
						if(xmlObj.bibStyle.equals("DDT-NS")||(xmlObj.bibStyle).equals("5-BookB"))
						{
							series = "{\\it{"+journalTitle+"}}";
						}
						else
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")) && (vnr.length()==0))//wwwwww
							if (xmlObj.deviation.equals("BOOKS") && (vnr.length()==0))//wwwwww
							{
								series = "{\\it{"+journalTitle+"}}";
							}
							else
							{
								//series = "{\\it{"+journalTitle+"}},";
								
								//Added By Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
								if( xmlObj.jid.equalsIgnoreCase("RETAIL"))
								{
									series = "{\\it{"+journalTitle+",}}";
									
								}
								else
								{
									if((xmlObj.bibStyle).equals("IChemE"))
									{
										series = ""+journalTitle+",";
									}
									else
									{
										series = "{\\it{"+journalTitle+"}},";
									}
									//series = "{\\it{"+journalTitle+"}},";//01/12/2007
									//System.out.println("1. series : "+series);
									//System.in.read();
								}
								
							}
							
						}
					}
				}
			
				if (vnr.length()>0)
				{
					if(xmlObj.bibStyle.equals("5-BookB"))
					{
						series = series +" " +"{\\bf{" + vnr +"}}";
					}
					else if(xmlObj.bibStyle.equals("DDT-NS") || xmlObj.jid.equalsIgnoreCase("RETAIL"))
					{
						series = series +" " + vnr;
						
					}
					else
					{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							series = series +" " + vnr +"";
						}
						else
						{
							series = series +" " +"{\\it{" + vnr +"}}";
						}
						//series = series +" " +"{\\it{" + vnr +"}}";//01/12/2007
						
						//System.out.println("2. series : "+series);
						//System.in.read();
					}
			
					
				}
				journalTitle ="";
			}
			//----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("5-Asw"))//Kishor [21/06/2004] - When parent is <SB:ISSUE>
			{//System.out.println("vnr "+vnr+" isSbEdition : "+isSbEdition);
				if(journalTitle.length()>0)
				{
					if (journalTitle.endsWith("?"))
					{
						series = "{\\it{"+journalTitle+"}}";
					}
					else
					{
						series = "{\\it{"+journalTitle+"}},";
					}
				}
				if (vnr.length()>0)
				{
					series = series +" " +"{\\it{" + vnr +"}}";
				}
				journalTitle ="";
			}
			//-------------------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("5-Manage"))
			{
				if(journalTitle.length()>0)
				{
					if (journalTitle.endsWith("?"))
					{
						series = "{\\it{"+journalTitle+"}}";
					}
					else
					{
						series = "{\\it{"+journalTitle+"}},";
					}
				}
				if (vnr.length()>0)
				{
					series = series +" " + vnr+":";
				}
				journalTitle ="";
			}
			//----------------------------------------------------------------------------
			else
				series =journalTitle;
			//System.out.println("--------------------------->>"+series);
		}
		//---------------------------------------------------------------------------------
		else if(hostType.equals("<SB:EDITED-BOOK>") || hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK "))
		{
			//---------------------------------------------------------------------------
			if ((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a")|| (xmlObj.bibStyle).equals("1b"))
			{
				if (journalTitle.length()>0)
				{				
					series=" "+journalTitle;				
										
					if (vnr.length()>0)
					{
						series+=", "+vnr;
					}
				}
			}
			else if ((xmlObj.bibStyle).equals("brvhead"))
			{
				if (journalTitle.length()>0)
				{				
					series=" "+journalTitle;				
										
					if (vnr.length()>0)
					{
						series+=", "+vnr;
					}
				}
			}
			//-----------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("2"))
			{
				if (journalTitle.length()>0)
				{					
						series=" "+journalTitle;				
									
					if (vnr.length()>0)
					{
						series+=", "+vnr+".";
					}
				}
			}
			//---------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3-ZEFQ"))
			{
				if (journalTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3-ZEFQ"))
					{
						if(trtitle.length()>0)
							journalTitle=" {\\it "+journalTitle+" ["+trtitle+"]}";
						else
							journalTitle=" {\\it{"+journalTitle+"}}";
					}
						series=journalTitle;

					if (vnr.length()>0)
					{
						if(xmlObj.bibStyle.equals("yijom-ns"))
						{
							series+=". "+vnr;   
						}
						else
						{
							series+=", "+vnr;   
						}
					}
				}
				//System.out.println("---------------------"+series);
			}
			//-------------------------------------------------------------------------------------------
			//---------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
			{
				if (journalTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
					{
						if(trtitle.length()>0)
						{
							if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
							{
								journalTitle=journalTitle+" ["+trtitle+"]";
							}
							else
								journalTitle="{\\it{"+journalTitle+" ["+trtitle+"]}}";
						}
						else
						{
							if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
							{
								journalTitle=journalTitle;
							}
							else
								journalTitle="{\\it{"+journalTitle+"}}";
						}
					}
					if( xmlObj.jid.equalsIgnoreCase("OSI")&&Integer.parseInt(xmlObj.aid)>global_cutOff_aid_for_OSI)//21-06-2012
						journalTitle="{\\it{"+journalTitle+"}}";

						series=journalTitle;

					if (vnr.length()>0)
					{
						if(xmlObj.bibStyle.equals("yijom-ns"))
						{
							series+=". "+vnr;   
						}
						else
						{
							if(xmlObj.bibStyle.equals("6"))
								series+=". "+vnr;
							else
								series+=", "+vnr;
						}
					}
				}
			}
			//-------------------------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("3a-Jecs"))
			{
				if (journalTitle.length()>0)
				{
					if(trtitle.length()>0)
						journalTitle="{\\it "+journalTitle+" ["+trtitle+"]}";
					else
						journalTitle="{\\it "+journalTitle+"}";
					
					series=journalTitle;
					//
					if (vnr.length()>0)
					{
						series+=", {\\it "+vnr+"}";
					}
				}
			}
			//------------------------------------------------------------------------------------------
			else if ((xmlObj.bibStyle).equals("chea"))
			{
				if (journalTitle.length()>0)
				{
					journalTitle="{\\it "+journalTitle+"}";
					series=journalTitle;
					//
					if (vnr.length()>0)
					{
						series+=", {\\it "+vnr+"};";
					}
				}
			}
			//----------------------------------------------------------------------------------
			//Kishor [22/06/2004]
			else if ((xmlObj.bibStyle).equals("4"))
			{
				if (journalTitle.length()>0)
				{
					/*if(xmlObj.bibStyle.equals("4"))//26-07-04--Gaurav & Rajeev---
					{
						journalTitle="{\\it "+journalTitle+"}";
					}*/
						series=journalTitle;

					if (vnr.length()>0)
					{
						if(xmlObj.jid.equals("PROTIS"))
						{
							series+=" {\\bf{"+vnr+"}}";
						}
						else
						series+=", "+vnr;
					}
				}
			}
			//----------------------------------------------------------------------------------
			//Kishor [21/06/2004]-When parent is <SB:EDITED-BOOK> or <SB:BOOK>
			else if ((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
			{
				if(journalTitle.length()>0) 
				{
					//old		
					//series = "{\\it{"+journalTitle+":}}";
					//
					if(XT.jid.equalsIgnoreCase("RETAIL"))
					{
						series = "{\\it{"+journalTitle+",}}";
					
					}
					else
					{
						//series = "{\\it{"+journalTitle+":}}";//old Ravi [08/11/2006 by Usmani]
						if(XT.jid.equalsIgnoreCase("ZOOGA"))//31-08-2011
						series = journalTitle;
						else
						{
							if (vnr.length()>0 && XT.jid.equalsIgnoreCase("INFBEH"))//16-09-2011
							{
								series = "{\\it{"+journalTitle+" : "+vnr+".}}";
							}
							else
							series = "{\\it{"+journalTitle+"}}";//Ravi [08/11/2006 by Usmani ref. style 5]
							//System.out.println("series -->"+series)	;
						}
					}
					if ((xmlObj.bibStyle).equals("5-BookB"))
					{
						bsTitle= "{\\it{"+journalTitle+"}}";
					}
					else
					{
						if(XT.jid.equalsIgnoreCase("ZOOGA"))//31-08-2011
						bsTitle= journalTitle+".";
						else
						bsTitle= "{\\it{"+journalTitle+".}}";
					}
				}
				if (vnr.length()>0)
				{
					if ((xmlObj.bibStyle).equals("5-BookB"))
					{
						series = series +" " +"{\\bf{" + vnr +"}}.";
						bsVol="{\\bf{" + vnr +"}}";
					}
					
					else
					{
						//series = series + " " + vnr + ",";
						//old
						//series = series +" " +"{\\it{" + vnr +"}},";
						if(XT.jid.equalsIgnoreCase("RETAIL"))
						{
							series = series + " " + vnr + ",";
						}
						else
						{
							//series = series +" " +"{\\it{" + vnr +"}},";//old
							/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
							if(bolBookSer=true)
							{
								VolSr=vnr;
								bolBookSer=false;
								//System.out.println("VolSr ::::: "+VolSr);
								if(isVolinBookSeries)
								{
									series=series+" ("+VolSr+")";
								}
							}
							else
							{
								series = series +" " +"{\\it{" + vnr +"}},";
							}
							
						}
						bsVol=vnr;
					}
					

				}
				journalTitle ="";
				//System.out.println("hostType ::::: "+hostType+" :: "+series);
			}//end for style 5
			else if ((xmlObj.bibStyle).equals("5-Retail"))
			{
				if(journalTitle.length()>0) 
				{
					//old		
					//series = "{\\it{"+journalTitle+":}}";
					//
					if(XT.jid.equalsIgnoreCase("RETAIL"))
					{
						series = "{\\it{"+journalTitle+",}}";
					
					}
					else
					{
						//series = "{\\it{"+journalTitle+":}}";//old Ravi [08/11/2006 by Usmani]
						series = "{\\it{"+journalTitle+"}}";//Ravi [08/11/2006 by Usmani ref. style 5]
						//	System.out.println("series -->"+series)	;
					}
					if ((xmlObj.bibStyle).equals("5-BookB"))
					{
						bsTitle= "{\\it{"+journalTitle+"}}";
					}
					else
					{
						bsTitle= "{\\it{"+journalTitle+".}}";
					}
				}
				if (vnr.length()>0)
				{
					if ((xmlObj.bibStyle).equals("5-BookB"))
					{
						series = series +" " +"{\\bf{" + vnr +"}}.";
						bsVol="{\\bf{" + vnr +"}}";
					}
					
					else
					{
						//series = series + " " + vnr + ",";
						//old
						//series = series +" " +"{\\it{" + vnr +"}},";
						if(XT.jid.equalsIgnoreCase("RETAIL"))
						{
							series = series + " " + vnr + ",";
						}
						else
						{
							//series = series +" " +"{\\it{" + vnr +"}},";//old
							/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
							*/
							if(bolBookSer=true)
							{
								VolSr=vnr;
								bolBookSer=false;
							}
							else
							{
								series = series +" " +"{\\it{" + vnr +"}},";
							}
							
						}
						bsVol=vnr;
					}
					

				}
				journalTitle ="";
				
			}
			//------------------------------------------------------------------------------
			//----------------------------------------------------------------------------------
			// [21/06/2004]-When parent is <SB:EDITED-BOOK> or <SB:BOOK>
			else if ((xmlObj.bibStyle).equals("DDT-NS"))
			{
				if(journalTitle.length()>0) 
				{
					series = "({\\it{"+journalTitle+"}})";
					bsTitle= "({\\it{"+journalTitle+"}})";
				}
				if (vnr.length()>0)
				{
						series = series +" " +vnr;
						bsVol=vnr;
				}
					
				journalTitle ="";
				}//end for style DDT
			//------------------------------------------------------------------------------
			
			else if ((xmlObj.bibStyle).equals("5-Asw"))
			{
				if(journalTitle.length()>0)
					series = "{\\it{"+journalTitle+":}}";
				if (vnr.length()>0)
				{
					//series = series +" " +"{\\it{" + vnr +"}}.";//old
					/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
					*/
					if(bolBookSer=true)
					{
						VolSr=vnr;
						bolBookSer=false;
					}
					else
					{
						series = series +" " +"{\\it{" + vnr +"}},";
					}
				}
				
				journalTitle ="";
			}//end for style 5
			//------------------------------------------------------------------------------
			//Kishor
			else if ((xmlObj.bibStyle).equals("5-Manage"))
			{
				if(journalTitle.length()>0)
					series = "{\\it{"+journalTitle+":}}";
				if (vnr.length()>0)
				{
					series = series +" " + vnr +".";
				}
				journalTitle ="";
			}
			//------------------------------------------------------------------------------
			//Kishor [21/06/2004]-When parent is <SB:EDITED-BOOK> or <SB:BOOK>
			else if ((xmlObj.bibStyle).equals("Old_6"))
			{
				if(journalTitle.length()>0)
					if (global.cont_avail.equals("yes"))
					{
						series = journalTitle+"; ";
					}
					else
					{	
						series = "{\\it{"+journalTitle+"}}. ";
					}
				if (vnr.length()>0)
				{
					series = series +" " + vnr;
				}
				journalTitle ="";
			}//end for style 5
			//------------------------------------------------------------------------------
			else
			{
				series =journalTitle;
			}
//			System.out.println("hostType: "+hostType+" :: "+series);
		}
		trtitle="";
		//System.out.println("bibSeries(): "+series);
		return series;
	}
	//-------------------------------------------------------------------------------------

	private String articleNumber() throws IOException
	{
		String arNo = new String();
		String sTag = new String();
		if(sTag.equals("</SB:ARTICLE-NUMBER>"))
		{
			arNo = xmlObj.extractData("</SB:ARTICLE-NUMBER>", true);
		}
		if (!(xmlObj.bibStyle).equals("5")){
			System.out.println("As per DTD540 isntructions, <SB:ARTICLE-NUMBER> to be used for reference style 5 only.");
		}
		return arNo;
	}
	
	private String bibPages() throws IOException
	{
		String pages = new String();
		String sTag = new String();
		String temp1=new String();
		String temp2=new String();
		String DiffFirst="";
		String DiffLast="";
		while(!sTag.equals("</SB:PAGES>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:FIRST-PAGE>"))
				{
					temp1 = xmlObj.extractData("</SB:FIRST-PAGE>", true);
				}
				else if(sTag.equals("<SB:LAST-PAGE>"))
				{
					temp2 = xmlObj.extractData("</SB:LAST-PAGE>", true);
				}
			}
		}

		if(temp2.indexOf("ThomsonDiffCloseBrace",0)!=-1)
		{
			int spos=0; int epos=0;
			spos=temp2.indexOf("ThomsonDiff",epos);
			if(spos!=-1)
			{
				epos=temp2.indexOf("ThomsonDiffCloseBrace",spos);
				if(epos!=-1)
				{
					DiffLast=temp2.substring(spos,epos+"ThomsonDiffCloseBrace".length());
					StringBuffer s=new StringBuffer(temp2);
					s=s.delete(spos,epos+"ThomsonDiffCloseBrace".length());
					//System.out.println("DiffLast "+DiffLast);
					//System.out.println("s "+s);
					temp2=s.toString();
				}
			}
		}
		if(temp1.indexOf("ThomsonDiffCloseBrace",0)!=-1)
		{
			int spos=0; int epos=0;
			spos=temp1.indexOf("ThomsonDiff",epos);
			if(spos!=-1)
			{
				epos=temp1.indexOf("ThomsonDiffCloseBrace",spos);
				if(epos!=-1)
				{
					DiffFirst=temp1.substring(spos,epos+"ThomsonDiffCloseBrace".length());
					StringBuffer s=new StringBuffer(temp1);
					s=s.delete(spos,epos+"ThomsonDiffCloseBrace".length());
					//System.out.println("DiffFirst "+DiffFirst);
					//System.out.println("s "+s);
					temp1=s.toString();
				}
			}
		}
		//System.out.println("temp22 "+temp2);

		//------------------------------------------------------------------------------------------------
		if ((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			if (temp1.length() >0 && temp2.length() >0)
			{
				//If both numbers having same length
				if (temp1.length() == temp2.length())
				{
					for (int i =0;i<temp1.length();i++)
					{
						if (temp1.charAt(i) != temp2.charAt(i))
						{
							temp2 = temp2.substring(i,temp2.length());
							break;
						}
					}
				}
				else 
				{
					boolean flag = true;
					for (int i =0;i<temp1.length();i++)
					{
						if (temp1.charAt(i) != temp2.charAt(i))
						{
							//temp2 = temp2.substring(0,temp2.length());//i -> 0 //old

							//Added by Ravi [06/12/2006 Change request by Vivek]
							temp2 = temp2.substring(i,temp2.length());//i -> 0
							flag = false;
							break;
						}
					}
					if (flag)
					{
						temp2 = temp2.substring(temp1.length()-1,temp2.length());

					}

				}
				pages = DiffFirst+temp1+"{\\ndash}"+temp2+DiffLast;
			}
			else
			{
			  	pages = DiffFirst+temp1;
			}
		}
		//-------------------------------------------------------------------------------------
		//------------------------------------------------------------------------------------------------
		if ((xmlObj.bibStyle).equals("3") || (xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6")||(xmlObj.bibStyle).equals("chea"))
		{
			if (XT.jid.equalsIgnoreCase("BRACHY"))//17-08-2017
			{
				if(temp1.length() >0)
					pages=DiffFirst+temp1;
				if(temp2.length() >0)
					pages = pages +"{\\ndash}"+temp2+DiffLast;
			}
			else if (temp1.length() >0 && temp2.length() >0)
			{
				//If both numbers having same length
				
					if(!(xmlObj.bibStyle.equals("6")|| XT.jid.equalsIgnoreCase("JSAMS")))
					{
					
						if (temp1.length() == temp2.length())
						{
							for (int i =0;i<temp1.length();i++)
							{
								if (temp1.charAt(i) != temp2.charAt(i))
								{
									temp2 = temp2.substring(i,temp2.length());
									break;
								}
							}
						}
						else 
						{
							boolean flag = true;

							for (int i =0;i<temp1.length();i++)
							{
								//System.out.println("charat i value : "+i);
							//System.out.println("temp1 ::>>>>>> "+temp1);
							//System.out.println("temp2 ::>>>>>> "+temp2);
								if (temp1.charAt(i) != temp2.charAt(i))
								{
									//temp2 = temp2.substring(0,temp2.length());//i -> 0 //old

									//Added by Ravi [06/12/2006 Change request by Vivek]
									temp2 = temp2.substring(i,temp2.length());//i -> 0
									flag = false;
									break;
								}
							}
							if (flag)
							{
								temp2 = temp2.substring(temp1.length()-1,temp2.length());

							}

						}
					}
					else if(XT.jid.equalsIgnoreCase("IMR"))
					{
						if (temp1.length() == temp2.length())
						{
							for (int i =0;i<temp1.length();i++)
							{
								if (temp1.charAt(i) != temp2.charAt(i))
								{
									temp2 = temp2.substring(i,temp2.length());
									break;
								}
							}
						}
						else 
						{
							boolean flag = true;
							for (int i =0;i<temp1.length();i++)
							{
								if (temp1.charAt(i) != temp2.charAt(i))
								{
									//temp2 = temp2.substring(0,temp2.length());//i -> 0 //old

									//Added by Ravi [06/12/2006 Change request by Vivek]
									temp2 = temp2.substring(i,temp2.length());//i -> 0
									flag = false;
									break;
								}
							}
							if (flag)
							{
								temp2 = temp2.substring(temp1.length()-1,temp2.length());

							}

						}
					}
					

				
				pages = DiffFirst+temp1+"{\\ndash}"+temp2+DiffLast;
				//System.out.println("===========>>"+pages);
			}
			else
			{
			  	pages = DiffFirst+temp1;
			}
		}
		//-------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("3a-Jecs"))
		{
			if(temp1.length() >0)
				pages=DiffFirst+temp1;
			if(temp2.length() >0)
				pages = pages +"{\\ndash}"+temp2+DiffLast;
		}
		//-------------------------------------------------------------------------------------
		//Kishor [23/06/2004]
		else if ((xmlObj.bibStyle).equals("4"))
		{
			if (temp1.length() >0 && temp2.length() >0)
			{
				//If both numbers having same length
				if (temp1.length() == temp2.length())
				{
					if(XT.jid.equalsIgnoreCase("PROTIS"))
					{
						temp2 = temp2;
					}
					else
					{
						for (int i =0;i<temp1.length();i++)
						{
							if (temp1.charAt(i) != temp2.charAt(i))
							{
								temp2 = temp2.substring(i,temp2.length());
								break;
							}
						}
					}
				}
				else //String length is not equal
				{
					boolean flag = true;
					if(XT.jid.equalsIgnoreCase("PROTIS"))
					{
						temp2 = temp2;
					}
					else
					{
						for (int i =0;i<temp1.length();i++)
						{
							if (temp1.charAt(i) != temp2.charAt(i))
							{
								temp2 = temp2.substring(i,temp2.length());
								flag = false;
								break;
							}
						}
						if (flag)
						{
							temp2 = temp2.substring(temp1.length()-1,temp2.length());

						}
					}
					
				}
				pages = DiffFirst+temp1+"{\\ndash}"+temp2+DiffLast;
			}
			else
			{
				pages = DiffFirst+temp1;
			}
		}
		else if ((xmlObj.bibStyle).equals("5-Retail"))
		{
			String ttemp2="";
			
			if(temp1.length()==temp2.length())
			{
				
				boolean check=false;
				for(int i=0;i<temp1.length();i++)
				{
					char pc1=temp1.charAt(i);
					char pc2=temp2.charAt(i);
					if(pc1 !=pc2)
						{
						   for(int j=i;j<temp1.length();j++)
							{
							//ttemp2+=pc2+"";
								ttemp2+=temp2.charAt(j)+"";
								check=true;
							}
							i=temp1.length();
						}
					/*
					if(temp1.length() >0)
						pages=DiffFirst+temp1;
					if(temp2.length() >0)
						pages = pages +"{\\ndash}"+ttemp2+DiffLast;
					*/
				}
				if(check)
				{
					if(temp1.length() >0)
						pages=DiffFirst+temp1;
					if(temp2.length() >0)
						pages = pages +"{\\ndash}"+ttemp2+DiffLast;
				}
			}
			else
			{
				if(temp1.length() >0)
					pages=DiffFirst+temp1;
				if(temp2.length() >0)
					pages = pages +"{\\ndash}"+temp2+DiffLast;
			}
		}
		//-------------------------------------------------------------------------------------
		else
		{
			
			if(XT.jid.equalsIgnoreCase("SYAPM"))
			{
				if(temp1.length() >0)
				pages=DiffFirst+temp1.trim();
			if(temp2.length() >0)
				pages = pages +"{\\ndash}"+temp2+DiffLast;
				pages=", "+pages;
			}
			else
			{
				if(temp1.length() >0)
					pages=DiffFirst+temp1;
				if(temp2.length() >0)
					pages = pages +"{\\ndash}"+temp2+DiffLast;
			}
		}
		//System.out.println("bibPages(): "+pages);
		return pages;
	}//end of bibPages()
	//-------------------------------------------------------------------------------------

	private String bibAuthorList(String tag) throws IOException
	{
		String temp = new String();
		String sTag = new String();
		String eTag = new String();
		String fnm = new String();
		String snm = new String();
		String jnm = new String();
		String jr = new String();
		do
		{
			char ch =(char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<CE:GIVEN-NAME>"))
				{
					fnm = xmlObj.extractData("</CE:GIVEN-NAME>", true);
				}
				else if(sTag.equals("<CE:SURNAME>"))
				{
					snm = xmlObj.extractData("</CE:SURNAME>", true);
				}
				else if(sTag.equals("<CE:SUFFIX>"))
				{
					jr = xmlObj.extractData("</CE:SUFFIX>", true).trim();
					
				}
			}
		}while(!sTag.equals(tag));
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		if((xmlObj.bibStyle).equals("1") || (xmlObj.bibStyle).equals("1a")|| (xmlObj.bibStyle).equals("1b"))
		{

			//
			if(xmlObj.jid.equalsIgnoreCase("SYAPM"))
			{
				temp += snm;
				if(snm.length()>0)
					temp += ", ";
				temp += fnm;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
				if(jr.length()>0)
					temp += ", ";
				temp+=jr;
			}
			else
			{
				temp += fnm;
				if(snm.length()>0)
					temp += " ";
				temp += snm;
				if(jnm.length()>0)
					temp += " ";
				temp+=jnm;
				if(jr.length()>0)
					temp += " ";
				temp+=jr;
			}
		}
		else if((xmlObj.bibStyle).equals("brvhead"))
		{
			temp += fnm;
			if(snm.length()>0)
				temp += " ";
			temp += snm;
			if(jnm.length()>0)
				temp += " ";
			temp+=jnm;
			if(jr.length()>0)
				temp += " ";
			temp+=jr;
		}
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("2"))
		{
			if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
			{
				if(firstAuthSoceco)
				{
					temp+=snm;
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					if(jr.length()>0)
						temp += ", ";
					temp+=jr;
					//System.out.println("firstAuth=======>"+firstAuth);
					//System.out.println("TEMP=======>"+temp);
					firstAuthSoceco=false;
				}
				else
				{
					temp+=fnm;
					if(snm.length()>0)
						temp += " ";
					temp += snm;
					if(jr.length()>0)
						temp += ", ";
					temp+=jr;
					//System.out.println("TEMP=======>"+temp);
				}
			}
			else
			{
				temp+=snm;
				if(jr.length()>0)
					temp += " ";
				temp+=jr;
				if(fnm.length()>0)
				{
					//JARMAP condition closed on  01-06-2016, discussed with Usmani/Afsana
					/*if(xmlObj.jid.equals("JARMAP"))
					{
						temp += " ";
						fnm=fnm.replaceAll("\\.","");
					}
					else*/
						temp += ", ";
				}
				temp += fnm;
			}
			
				/*String tempFnm=fnm;//Gaurav--27-10-04--Based oN Kavita request For Pragma 2272--
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
						dot=false;
					
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
			//2222222*/
		}
		//-------------------------------------------------------------------------------------
		
		else if((xmlObj.bibStyle).equals("3-ZEFQ"))
		{
			String tempFnm=fnm;
			//fnm="";
			int i=0;
			//System.out.println("tempFnm "+tempFnm);
			/*while(i!=tempFnm.length())
			{
				char tmpCh=(char)tempFnm.charAt(i);
				
				if(tempFnm.startsWith("{\\.{"))//06-08-2004(Rajeev  & Gaurav--{\.{I}})
				{
					fnm+=tmpCh;
				}
				else
				{
					if (tmpCh!='.')
					{
						fnm+=tmpCh;
					}
				}
				i++;
			}
			if(fnm.endsWith("."))
			{
				fnm=fnm.substring(0,fnm.length()-1);
			}*/
			if(zefq_editor==true)
			{
				if(fnm.length()>0)
				{
					if(!fnm.endsWith("."))
					{
						fnm=fnm+". ";
					}
					temp += fnm;
				}
					temp += " ";
				temp += snm;
				
				
				if(jr.length()>0)
				{
					temp+=", "+jr;
					jr="";
				}
				if(jnm.length()>0)
					temp += " ";
			}
			

			if(count_Author_zefq==1){
				temp += snm+", ";
			
			if(fnm.length()>0)
			{
				if(!fnm.endsWith("."))
				{
					fnm=fnm+". ";
				}
				temp += fnm;
			}
			if(jr.length()>0)
			{
				temp+=" "+jr;
				jr="";
			}
			if(jnm.length()>0)
				temp += " ";
			  //temp+=jr;

			}
			
			else if(count_Author_zefq > 1)
			{

			if(fnm.length()>0)
			{
				if(!fnm.endsWith("."))
				{
					fnm=fnm+". ";
				}
				temp += fnm;
			}
				temp += " ";
			temp += snm;
			
			
			if(jr.length()>0)
			{
				temp+=", "+jr;
				jr="";
			}
			if(jnm.length()>0)
				temp += " ";
			  //temp+=jr;

			}
			//System.out.println("----------------------->"+count_Author_zefq);
			//System.out.println("----------------------->"+temp);
		}
		//-------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("3")||(xmlObj.bibStyle).equals("3a")|| (xmlObj.bibStyle).equals("6"))
		{
			fnm = fnm.replaceAll("\\\\epsfbox\\{([^\\.]+)\\.eps}}", "\\\\epsfbox\\{$1_eps}}");
			fnm = fnm.replaceAll("\\\\inserttiff\\{([^\\}]+)\\}\\{([^\\}]+)\\}\\{([^\\.]+)\\.tif\\}","\\\\inserttiff\\{$1\\}\\{$2\\}\\{$3_tif\\}");//Added on 09-11-2015 to retain the dot before tif
			String tempFnm=fnm;
			fnm="";
			int i=0;
			//System.out.println("tempFnm "+tempFnm);
			while(i!=tempFnm.length())
			{
				char tmpCh=(char)tempFnm.charAt(i);
				
				if(tempFnm.startsWith("{\\.{"))//06-08-2004(Rajeev  & Gaurav--{\.{I}})
				{
					fnm+=tmpCh;
					System.out.println("AAAAAAAA==>"+fnm);
				}
				else
				{
					if (tmpCh!='.')
					{
						fnm+=tmpCh;
					}
				}
				i++;
			}
			if(fnm.endsWith("."))
			{
				fnm=fnm.substring(0,fnm.length()-1);
			}
			fnm = fnm.replaceAll("\\\\epsfbox\\{([^_]+)_eps}}", "\\\\epsfbox\\{$1\\.eps}}");
			fnm = fnm.replaceAll("\\\\inserttiff\\{([^\\}]+)\\}\\{([^\\}]+)\\}\\{([^_]+)_tif\\}","\\\\inserttiff\\{$1\\}\\{$2\\}\\{$3\\.tif\\}");
			//System.out.println("FNM===>"+fnm);
			temp += snm;	
			checkSpanishJidList=chekJid();
			if(checkSpanishJidList.contains(xmlObj.jid))
			{
				if(fnm.length()>0)
					temp += " ";
				temp += fnm;			
				if(jnm.length()>0)
					temp += " ";
				
				if(jr.length()>0)
				{
					if(jr.endsWith("."))
					{
						jr = jr.substring(0,jr.length()-1);
					}
					temp+=" "+jr;
					jr="";
				}
			}
			else
			{
				if(jr.length()>0)
				{
					if(jr.endsWith("."))
					{
						jr = jr.substring(0,jr.length()-1);
					}
					temp+=" "+jr;
					jr="";
				}
	
				if(fnm.length()>0)
					temp += " ";
				temp += fnm;			
				if(jnm.length()>0)
					temp += " ";
				  //temp+=jr;
			}
		}
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("yijom-ns"))
		{
			if((global.cont_avail.equals("yes")) && (global.for_editor.equals("yes"))&& authorExist==true)
			{
				String tempFnm=fnm;
				fnm="";
				int i=0;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
				
					if(tempFnm.startsWith("{\\.{"))//06-08-2004(Rajeev  & Gaurav--{\.{I}})
					{
						fnm+=tmpCh;
					}
					else
					{
						if (tmpCh!='.')
						{
							fnm+=tmpCh;
						}
					}
					i++;
				}
				if(fnm.endsWith("."))
				{
					fnm=fnm.substring(0,fnm.length()-1);
				}
				temp += snm;
				if(jr.length()>0)
				{
					if(jr.endsWith("."))
					{
						jr = jr.substring(0,jr.length()-1);
					}
					temp+=" "+jr;
					jr="";
				}
				if(fnm.length()>0)
					temp += " ";
				temp += fnm;
				if(jnm.length()>0)
					temp += " ";
				
			}
			else 
			{
				String tempFnm=fnm;
				fnm="";
				int i=0;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
				
					if(tempFnm.startsWith("{\\.{"))//06-08-2004(Rajeev  & Gaurav--{\.{I}})
					{
						fnm+=tmpCh;
					}
					else
					{
						if (tmpCh!='.')
						{
							fnm+=tmpCh;
						}
					}
					i++;
				}
				if(fnm.endsWith("."))
				{
					fnm=fnm.substring(0,fnm.length()-1);
				}

				String tempSnm=snm;
				snm="";
				int m=0;
				while(m!=tempSnm.length())
				{
					char tmpSn=(char)tempSnm.charAt(m);
					if (m==0)
					{
						snm+=tmpSn;
					}
					else if (m==1)
					{
						snm+="{\\sc{"+tmpSn;
					}
					else
					{
						snm+=tmpSn;
					}						
					m++;
				}
				snm+="}}";			
				temp += snm;
				if(jr.length()>0)
				{
					if(jr.endsWith("."))
					{
						jr = jr.substring(0,jr.length()-1);
					}
					temp+=" {\\sc{"+jr+"}}";
					jr="";
				}
				if(fnm.length()>0)
					temp += " ";
				temp += fnm;
				if(jnm.length()>0)
					temp += " ";
			}
		}
		//-------------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("3a-Jecs"))
		{
			if((global.cont_avail.equals("yes")) && (global.for_editor.equals("yes")))
			{
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						if(fnm.endsWith("."))//jecs-5315
							fnm+= " "+tmpCh;
						else
							fnm+= tmpCh;
						
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				fnm=fnm.replaceAll("\\. \\-",".-");
				
				temp += fnm;							
				if(snm.length()>0)
					temp += " ";
				temp += snm;			
				
				if(jr.length()>0)
					temp += " "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
			else
			{
				temp += snm;
				if(jr.length()>0)
					temp += " "+jr;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						if(fnm.endsWith("."))//jecs-5315
							fnm+= " "+tmpCh;
						else
							fnm+= tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{					
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);					
				}
				fnm=fnm.replaceAll("\\. \\-",".-");
		
				if(fnm.length()>0)
					temp += ", ";
				temp += fnm;						
				
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;			
			}		
		}
		else if((xmlObj.bibStyle).equals("chea"))
		{
				temp += snm;
				if(jr.length()>0)
					temp += " "+jr;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
		
				if(fnm.length()>0)
					temp += ", ";
				temp += fnm;						
				
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;			
			//}		
		}
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("4"))
		{
			String tempFnm=fnm;
			fnm="";
			int i=0;
			while(i!=tempFnm.length())
			{
				char tmpCh=(char)tempFnm.charAt(i);
				if (tmpCh!='.')
				{
					fnm+=tmpCh;
				}
				i++;
			}
			temp += snm;
			/*
			if(jr.length()>0)
			{
				if(jr.endsWith("."))
				{
					jr = jr.substring(0,jr.length()-1);
				}
				temp+=" "+jr;
				jr="";
			}*/
			if(fnm.length()>0)
				temp += " ";
			temp += fnm;
			if(jnm.length()>0)
				temp += " ";
			if(jr.length()>0)//Change position of jr in Ref_Style (4) on 03-05-2013 for MFC Guide Version 3.0
			{
				if(jr.endsWith("."))
				{
					jr = jr.substring(0,jr.length()-1);
				}
				temp+=" "+jr;
				jr="";
			}
		}
		//-------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-BookB"))
		{
			
			if((global.cont_avail.equals("yes")) && (global.for_editor.equals("yes"))&& authorExist==true)
			{
				String tempFnm=fnm;
				//System.out.println("tempFnm--------> "+tempFnm);
				//System.in.read();
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					
					char tmpCh=(char)tempFnm.charAt(i);
					//System.out.println("tmpCh "+tmpCh);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							fnm+=tmpCh;
						}
						else
						{
							fnm+= " "+tmpCh;
						}
						//fnm+= " "+tmpCh;//01/12/2007
						dot=false;//Gaurav-- 20-09-04--Based On Ruchika FeedBack for Cocomp 2007---
					
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				if((xmlObj.bibStyle).equals("IChemE"))
				{
					temp += snm+",";
				}
				else
				{
					temp += fnm;
				}
				//temp += fnm;//01/12/2007
				if(snm.length()>0)
				{
					
					temp += " ";
				}
				if((xmlObj.bibStyle).equals("IChemE"))
				{
					temp += fnm;
				}
				else
				{
					temp += snm;
				}
		    	//temp += snm;//01/12/2007

				if(jr.length()>0)
				{
						temp += " "+jr;
						
				}
						
				if(jnm.length()>0)
					temp += " ";
				temp+=jnm;
				
			}
			else if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
			{
				//old	
				/*if(firstAuth)
				{
					temp += snm;
					
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					firstAuth=false;

				}
				else
				{
					temp += fnm;
					if(snm.length()>0)
						temp += " ";
					temp += snm;
				}
				*/
				//Added by Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
				if(firstAuth)
				{
					temp += snm;
					
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					firstAuth=false;
					if(jr.length()>0)
						temp +=" "+jr;
					
				}
				else
				{
					temp += fnm;
					if(snm.length()>0)
						temp += " ";
					temp += snm;
					if(jr.length()>0)
						temp +=" "+jr;
				}


			}
			else
			{
				temp += snm;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{						
						if(fnm.endsWith(".")) //Gaurav---27-08-04--Based on Ruchika Feedback  for RETAIL 139.
						{
							if((xmlObj.bibStyle).equals("IChemE"))
							{
								fnm+=tmpCh;
							}
							else
							{
								fnm+= " "+tmpCh;
							}
							//fnm+= " "+tmpCh;//01/12/2007
						}
						else
						{
							fnm+= tmpCh;
						}
											
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
		
				if(fnm.length()>0)
				{
					temp += ", ";
					
				}
				temp += fnm;			
				
				if(jr.length()>0)
					temp += ", "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
			temp=temp.replaceAll("\\. -",".-");
			
		}
		else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
		{
			
			if((global.cont_avail.equals("yes")) && (global.for_editor.equals("yes"))&& authorExist==true)
			{
				String tempFnm=fnm;
				//System.out.println("tempFnm--------> "+tempFnm);
				//System.in.read();
				String Ttemp="";//14/04/2008
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					
					char tmpCh=(char)tempFnm.charAt(i);
					//System.out.println("tmpCh "+tmpCh);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						if((xmlObj.bibStyle).equals("IChemE"))
						{
							fnm+=tmpCh;
						}
						else
						{
							fnm+= " "+tmpCh;
						}
						//fnm+= " "+tmpCh;//01/12/2007
						dot=false;//Gaurav-- 20-09-04--Based On Ruchika FeedBack for Cocomp 2007---
					
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				if((xmlObj.bibStyle).equals("IChemE"))
				{
					temp += snm+",";
				}
				else
				{
					//temp += fnm;//14/04/2008
					temp += snm;
				}
				//temp += fnm;//01/12/2007
				if(snm.length()>0)
				{
					
					temp += " ";
				}
				if((xmlObj.bibStyle).equals("IChemE"))
				{
					temp += fnm;
				}
				else
				{
					//temp += snm;//14/04/2008
					temp += fnm;
				}
		    	//temp += snm;//01/12/2007

				if(jr.length()>0)
				{
						temp += " "+jr;
						
				}
						
				if(jnm.length()>0)
					temp += " ";
				temp+=jnm;
				
			}
			else if(xmlObj.jid.equalsIgnoreCase("RETAIL"))
			{
				//old	
				/*if(firstAuth)
				{
					temp += snm;
					
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					firstAuth=false;

				}
				else
				{
					temp += fnm;
					if(snm.length()>0)
						temp += " ";
					temp += snm;
				}
				*/
				//Added by Ravi 06/11/2006 [Given by Usmani mail dated : 06/11/2006]
				if(firstAuth)
				{
					/*temp += snm;
					
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					firstAuth=false;
					if(jr.length()>0)
						temp +=" "+jr;
					*/
					
					if(count_Author==1){//14/04/2008
					temp += snm;
					
					if(fnm.length()>0)
						temp += ", ";
					temp += fnm;
					firstAuth=false;
					if(jr.length()>0)
						temp +=" "+jr;
					//count_Author=0;
					//System.out.println("temp : "+temp);
					}
					else if(count_Author > 1){
						if(Prev_Retail_arr.size()==Retail_arr.size())//05/09/2008
						{
							temp += "{\\dashrule}";
						}else
						{
							temp += snm;
					
							if(fnm.length()>0)
								temp += ", ";
							temp += fnm;
							firstAuth=false;
							if(jr.length()>0)
								temp +=" "+jr;
						}
					//end 05/09/2008
					/*if(fnm.length()>0)
						temp += ", ";
					temp +="{\\dashrule}";*/
					firstAuth=false;
					/*if(jr.length()>0)
						temp +=" "+"\\dashrule";
						*/
					//count_Author=0;
					}
					//end //14/04/2008
					//System.out.println("temp : "+temp);
				}
				else
				{
					temp += fnm;
					if(snm.length()>0)
						temp += " ";
					temp += snm;
					if(jr.length()>0)
						temp +=" "+jr;
					//System.out.println("editor temp : "+temp);
				}


			}
			else
			{
				temp += snm;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{						
						if(fnm.endsWith(".")) //Gaurav---27-08-04--Based on Ruchika Feedback  for RETAIL 139.
						{
							if((xmlObj.bibStyle).equals("IChemE"))
							{
								fnm+=tmpCh;
							}
							else
							{
								fnm+= " "+tmpCh;
							}
							//fnm+= " "+tmpCh;//01/12/2007
						}
						else
						{
							fnm+= tmpCh;
						}
											
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
		
				if(fnm.length()>0)
				{
					temp += ", ";
					
				}
				temp += fnm;			
				
				if(jr.length()>0)
					temp += ", "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
			temp=temp.replaceAll("\\. -",".-");
			
		}
		//----------------------------------------------------------------------------------------------------------
		//----------------------------------------------------------------------------------------------------------
		//Kishor [22/06/2004]
		else if((xmlObj.bibStyle).equals("DDT-NS"))
		{
			{
				temp += snm;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
		
				if(fnm.length()>0)
					temp += ", ";
				temp += fnm;			
				
				if(jr.length()>0)
					temp += ", "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;			
			}
		}
		
		//---------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("5-Asw"))
		{
			if((global.cont_avail.equals("yes")) && (global.for_editor.equals("yes")))
			{
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				//
				temp += fnm;			
				if(snm.length()>0)
					temp += " ";
				temp += snm;			
				//
				if(jr.length()>0)
					temp += " "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
			else
			{
				temp += snm;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
		
				if(fnm.length()>0)
					temp += ", ";
				temp += fnm;			
				
				if(jr.length()>0)
					temp += ", "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;			
			}
		}
		//---------------------------------------------------------------------------------
		//Kishor [30/06/2004]
		else if((xmlObj.bibStyle).equals("5-Manage"))
		{
			if ( (global.cont_avail.equals("yes")) && (global.for_editor.equals("yes")) )
			{
			    String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				temp += fnm;
				//Kishor[19/06/2004]
				if(snm.length()>0)
					temp += " ";
				temp += snm;			
				
				if(jr.length()>0)
					temp += " "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
			else
			{
				temp += snm;
				String tempFnm=fnm;
				fnm="";
				int i=0;
				boolean dot=false;
				while(i!=tempFnm.length())
				{
					char tmpCh=(char)tempFnm.charAt(i);
					if (tmpCh=='.')
						dot=true;
					if (tmpCh!='.' && dot==true)
					{
						fnm+= " "+tmpCh;
					}
					else
						fnm+= tmpCh;
					i++;
				}
				while ((i=fnm.indexOf(" - "))>0)
				{
					fnm = fnm.substring(0,i)+"-"+fnm.substring(i+3);
				}
				//Kishor[19/06/2004]
				if(fnm.length()>0)
					temp += ", ";
				temp += fnm;			
				
				if(jr.length()>0)
					temp += ", "+jr;
				if(jnm.length()>0)
					temp += ", ";
				temp+=jnm;
			}
		}
		//---------------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("Old_6"))
		{
			String tempFnm=fnm;
			fnm="";
			int i=0;
			while(i!=tempFnm.length())
			{
				char tmpCh=(char)tempFnm.charAt(i);
				if (tmpCh!='.')
				{
					fnm+=tmpCh;
				}
				i++;
			}
			temp += snm;
			if(jr.length()>0)
			{
				if(jr.endsWith("."))
				{
					jr = jr.substring(0,jr.length()-1);
				}
				temp+=" "+jr;
				jr="";
			}
			if(fnm.length()>0)
				temp += " ";
			temp += fnm;
			if(jnm.length()>0)
				temp += " ";
		}
		//-------------------------------------------------------------------------------------

//		System.out.println("qqqqqqqqqq temp : "+temp);
		//System.in.read();
		return temp;
	}
	//------------------------------------------------------------------------------------------

	private String sbEditors() throws IOException
	{
		count_Author_zefq=0;
		String editors = new String();
		String sTag = new String();
		String editor = new String();
		String etAl = new String();
		String last_editor=new String();
		int edit_cnt;
		edit_cnt=0;
		//-----------------------------------------------------------------------------
		//Kishor [18/06/2004] 
		global.for_editor="yes";
		firstAuthSoceco=true;

		while(!sTag.equals("</SB:EDITORS>"))
		{	
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:EDITOR>"))
				{
					if (edit_cnt!=0)
					{
						if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
						{
							editor = editor+", "+last_editor;
						}else if (xmlObj.bibStyle.equals("chea"))
						{					
								editor = editor+"; "+last_editor;														
						}
						else if(ellipsisED)//DTD 540 updation 01-08-2015
						{
							//System.out.println("<SB:ELLIPSIS> found in reference:"+ellipsis);
							//System.out.println("Adding ldots...");
							editor = editor + last_editor+", \\(\\ldots\\)";
							ellipsisED = false;
						}
						else
						{
							editor = editor+", "+last_editor;
							//System.out.println("22222222 editor : "+editor+" last_editor : "+last_editor);
						}
					}
					edit_cnt=edit_cnt+1;
					if(editor.length()>0)
					{
						isMultipleEditors=true;
					}
					zefq_editor=true;
					//++count_Author_zefq;
					last_editor=bibAuthorList("</SB:EDITOR>");
					//System.out.println("last_editor : "+last_editor);
					//System.out.println("22222222 editor : "+editor+" last_editor : "+last_editor);
					
				}
				else if(sTag.equals("<SB:ET-AL/>"))
				{
					etAl = "et al.";
					isMultipleEditors=true;
				}
				else if(sTag.equals("<SB:ELLIPSIS/>"))//For new DTD 540 01-08-2015
				{
					ellipsisED = true;
				}
				else if(sTag.equals("</SB:EDITORS>")) 
				{
					//Kishor [19/06/2004]
					if ((xmlObj.bibStyle.equals("5")||xmlObj.bibStyle.startsWith("IChemE")) && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{
							if (xmlObj.jid.equals("RETAIL"))  // [Requested by Mrs. Sagarika Ghosh- 																																				Matbeh journal should follow Standard APA style with no deviation -29 Sept. 2004]
								editor = editor+" and "+last_editor;
							else
							{
								//editor = editor+", \\& "+last_editor;//17/08/2006 bhavesh//old
								/*
								 *Added By Ravi [Change Request By Rajiven ]
								 *[Mail Date : 19/12/2006]
								 *Change Point : References starting with 
								 *author(s), CT, editors (two editors), etc.,
								 *then no comma is allowed between editors
								 *
								*/
								if(boledi==true)
								{
									if(xmlObj.bibStyle.startsWith("IChemE"))
									{
										editor = editor+" and "+last_editor;
									}
									else
									{
										/**
										* Date : 22/04/2009
										* Modification Point: Update due to quaterly feedback receive
										* Change Point: Comman add before \\& in case of Multi editors
										* Change By : Ravi Shekhar
										* Change Request By : TPMS(Vivek)
										*/
										//editor = editor+" \\& "+last_editor;//old//22/04/2009
										//if((xmlObj.jid.equalsIgnoreCase("RLFA")||xmlObj.jid.equalsIgnoreCase("REDEE")||xmlObj.jid.equalsIgnoreCase("IEDEE")||xmlObj.jid.equalsIgnoreCase("RCSAR"))&&XT.articleType.equalsIgnoreCase("ES"))//19-10-2012 Added condition for above jid and language spanish.
										//if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)&&XT.articleType.equalsIgnoreCase("ES"))//Instead of jid variable used from onecolumn.dbf on 04-03-2013
										//if(XT.articleType.equalsIgnoreCase("ES"))//Condition added for XT.Spanish_Reference_Jid_5APA on 05-12-2013
										if(XT.articleType.equalsIgnoreCase("ES") && (!xmlObj.jid.equalsIgnoreCase("RLP") && !xmlObj.jid.equalsIgnoreCase("SUMPSI") && !xmlObj.jid.equalsIgnoreCase("SUMNEG") && !xmlObj.jid.equalsIgnoreCase("JIK")))//Exception JID added on 23-09-2015
										{
											editor = editor+" y "+last_editor;
										}
										else
											editor = editor+", \\& "+last_editor;
										//System.out.println("I am Here : "+editor);
										//System.in.read();
									}
									//editor = editor+" \\& "+last_editor;//01/12/2007
									boledi=false;
								}
								else
								{
									if(xmlObj.bibStyle.startsWith("IChemE"))
									{
										editor = editor+" and "+last_editor;
									}
									else
									{
										//if((XT.Spanish_Reference_Jid.contains(xmlObj.jid)||XT.Spanish_Reference_Jid_5APA.contains(xmlObj.jid)) && XT.articleType.equalsIgnoreCase("ES"))//Condition added for XT.Spanish_Reference_Jid_5APA on 05-12-2013
										if((XT.Spanish_Reference_Jid.contains(xmlObj.jid) || XT.Spanish_Reference_Jid_5APA.contains(xmlObj.jid)) && XT.articleType.equalsIgnoreCase("ES") && (!xmlObj.jid.equalsIgnoreCase("RLP") && !xmlObj.jid.equalsIgnoreCase("SUMPSI") && !xmlObj.jid.equalsIgnoreCase("SUMNEG") && !xmlObj.jid.equalsIgnoreCase("JIK")))//Exception JID added on 23-09-2015
										{
											editor = editor+", y "+last_editor;
										}
										else{
											editor = editor+", \\& "+last_editor;
										}
									}
									//editor = editor+", \\& "+last_editor;//01/12/2007
								}
								
							}
						}
						else
						{
							if ((edit_cnt==1))//16-08-04(Gaurav---when <SB:ET-AL/> is added  Only For Single Editor)
							{
								editor = last_editor;
							}
							else
							{
								//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC"))) // 04-05-2004
								if(xmlObj.deviation.equals("BOOKS"))
									editor = editor+" \\& "+last_editor;
								else if (xmlObj.jid.equals("RETAIL"))
									editor = editor+" and "+last_editor;
								else{
									//if((XT.Spanish_Reference_Jid.contains(xmlObj.jid)||XT.Spanish_Reference_Jid_5APA.contains(xmlObj.jid))&&XT.articleType.equalsIgnoreCase("ES"))//Condition added for XT.Spanish_Reference_Jid_5APA on 05-12-2013
									//if(XT.articleType.equalsIgnoreCase("ES"))//Condition added for XT.Spanish_Reference_Jid_5APA on 05-12-2013
									if(XT.articleType.equalsIgnoreCase("ES") && (!xmlObj.jid.equalsIgnoreCase("RLP") && !xmlObj.jid.equalsIgnoreCase("SUMPSI") && !xmlObj.jid.equalsIgnoreCase("SUMNEG") && !xmlObj.jid.equalsIgnoreCase("JIK")))//Exception JID added on 23-09-2015
									{
										editor = editor+", y "+last_editor;
									}
									else
										editor = editor+", \\& "+last_editor;
								}
							}
						}
							
					}
					if ((xmlObj.bibStyle.equals("5-Retail")) && (isMultipleEditors==true))
					{
						//System.out.println("editor: "+editor+" last_editor : "+last_editor);
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{
							if (xmlObj.jid.equals("RETAIL"))  // [Requested by Mrs. Sagarika Ghosh- 																																				Matbeh journal should follow Standard APA style with no deviation -29 Sept. 2004]
								editor = editor+" and "+last_editor;
							else
							{
								//editor = editor+", \\& "+last_editor;//17/08/2006 bhavesh//old
								/*
								 *Added By Ravi [Change Request By Rajiven ]
								 *[Mail Date : 19/12/2006]
								 *Change Point : References starting with 
								 *author(s), CT, editors (two editors), etc.,
								 *then no comma is allowed between editors
								 *
								*/
								if(boledi==true)
								{
									if(xmlObj.bibStyle.startsWith("IChemE"))
									{
										editor = editor+" and "+last_editor;
									}
									else
									{
										editor = editor+" \\& "+last_editor;
									}
									//editor = editor+" \\& "+last_editor;//01/12/2007
									boledi=false;
								}
								else
								{
									if(xmlObj.bibStyle.startsWith("IChemE"))
									{
										editor = editor+" and "+last_editor;
									}
									else
									{
										editor = editor+", \\& "+last_editor;
										//System.out.println("editor: "+editor+" last_editor : "+last_editor);
									}
									//editor = editor+", \\& "+last_editor;//01/12/2007
								}
								
							}
						}
						else
						{
							if ((edit_cnt==1))//16-08-04(Gaurav---when <SB:ET-AL/> is added  Only For Single Editor)
							{
								editor = last_editor;
								//System.out.println("333 editor : "+editor+" last_editor : "+last_editor);
							}
							else
							{
								//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC"))) // 04-05-2004
								if(xmlObj.deviation.equals("BOOKS"))
									editor = editor+" \\& "+last_editor;
								else if (xmlObj.jid.equals("RETAIL"))
									editor = editor+" and "+last_editor;
								else
									editor = editor+", \\& "+last_editor;
								
							}
						}
					}

					else if (((xmlObj.bibStyle).equals("DDT-NS")) && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{
							editor = editor+" and "+last_editor;
						}
						else if ((edit_cnt==1) )//&& (global.cont_avail.equals("yes")))//Gaurav based on DDSTR--11/25/04
						{
							editor = editor+""+last_editor;
						}
						else
						{
							editor = editor+" and "+last_editor;//Gaurav Based on New Requirment. --11/27/04
						}
					}
					else if (((xmlObj.bibStyle).equals("5-BookB")) && (isMultipleEditors==true))
					{						
							editor = editor+", and "+last_editor;						
					}
					else if ((xmlObj.jid.equals("JASCER")) && (isMultipleEditors==true))
					{						
							editor = editor+" and "+last_editor;						
					}
					else if(((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))&& (isMultipleEditors==true))//27-12-2013
					{						
							editor = editor+" and "+last_editor;						
					}
					else if ((xmlObj.bibStyle.equals("5-Asw")) && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{
							editor = editor+" \\& "+last_editor;
						}
						else
						{
							editor = editor+", \\& "+last_editor;
						}
					}
					else if ((xmlObj.bibStyle.equals("5-Manage")) && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{
							editor = editor+" \\& "+last_editor;
						}
						else
						{
							editor = editor+", \\& "+last_editor;
						}
					}
					else if ((xmlObj.bibStyle.equals("3a-Jecs")) && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{							
							editor = editor+" and "+last_editor;
						}
						else
						{
							if(edit_cnt==1) // [23-07-04 Gaurav & Rajeev -  In case of Single editor]
							{
								editor = editor+" "+last_editor;
							}
							else
							{
								editor = editor+" and "+last_editor;
							}							
						}
					}
					else if ((xmlObj.bibStyle).equals("chea") && (isMultipleEditors==true))
					{
						if ((edit_cnt==2) && (global.cont_avail.equals("yes")))
						{							
							editor = editor+"; "+last_editor;
						}
						else
						{
							if(edit_cnt==1) // [23-07-04 Gaurav & Rajeev -  In case of Single editor]
							{
								editor = editor+" "+last_editor;
							}
							else
							{
								editor = editor+"; "+last_editor;
							}							
						}
					}
					else if (((xmlObj.bibStyle.equals("3-ZEFQ"))))
					{
						if(editor.trim().length()>0)
						editor = editor+" and "+last_editor;
						else
							editor = editor+" "+last_editor;
					}
					else
					{
						if(editor.indexOf("\\&",0)==-1)
						{
							if(editor.indexOf(" y ",0)!=-1)
							{
							}
							else
							editor = editor+", "+last_editor;
						}
						//else
						//System.out.println("Ravi : "+editor+" last_editor : "+last_editor);	
					}
				}
			}
		}
		if(xmlObj.jid.equals("BODYIM"))
		{
			int spos=0;
			int epos=0;
			spos=editor.lastIndexOf(", \\&");
			if(spos !=-1)
			{
				//System.out.println("111--------------------->>"+editor);
				StringBuffer s=new StringBuffer(editor);
				s=s.delete(spos,spos+1);
				//s=s.insert(spos,"");
				editor=s.toString();
			}
			//System.out.println("222--------------------->>"+editor);
				
		}
		zefq_editor=false;
//----------------------------------------------------------------------------------------------------------
		//Kishor [Based on Feedback[JVAC]-30/06/2004]
		//If editor ends with et al. then before et al. comma is required.
		//System.out.println(editors+" etAl "+etAl);	
		if (etAl.length()>0)
		{
			if (xmlObj.bibStyle.equals("3a-Jecs"))
			{
				editors = editor +" "+"{\\it{"+etAl+"}}";
			}
			else if( xmlObj.bibStyle.equals("DDT-NS"))//Gaurav Based on DDSTR 70--11/27/04
			{
					editors = editor +" "+"{\\it{"+etAl+"}}";
			}
			else if (xmlObj.bibStyle.equals("5-Retail"))//14/04/2008
			{
				//System.out.println("222 editors : "+editors+" last_editor : "+last_editor);
					editors = editor.trim() +" "+ etAl;
					//System.out.println("333 editors : "+editors+" last_editor : "+last_editor);
			}
			else
			{
				editors = editor +", "+ etAl;//Ex:Ref-Style 3[JVAC]
			
			}
		}
		else
		{
			editors = editor + etAl;
			//System.out.println(editors+" etAl "+etAl);	
		}
		//-------------------------------------------
		if(editors.startsWith(", ")||editors.startsWith("; "))
		{
			editors=editors.substring(2);
		}
		//Kishor [21/06/2004]//Feedback
		//We are passing Ediotrs format with [In] based on style and condition.
		if ((authorExist==true) && (xmlObj.bibStyle.equals("2")))
		{
			if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
			{
				if(XT.articleType.equalsIgnoreCase("ES"))
					editors=" en: "+editors;
				else
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						editors=" en: "+editors;
					else
					editors=" in: "+editors;
				}
			}
			else
			{
				if(XT.articleType.equalsIgnoreCase("ES"))
					editors=" En: "+editors;
				else
				{
					if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
					{
						editors=" In "+editors;
					}
					else
					{
						editors=" In: "+editors;
					}
				}

			}
		}

		else if ((authorExist==true) && ((xmlObj.bibStyle.equals("5"))||( xmlObj.bibStyle.startsWith("IChemE"))))
		{
			if (artTitle.length()>0) // 27-07-04
			{
				/*if (xmlObj.jid.equals("MATBEH") && Integer.parseInt(xmlObj.aid)<=112)
					editors=" In: "+editors;
				else*/
				//if (xmlObj.jid.equals("ANXDIS")||((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC"))))
				if(xmlObj.deviation.equals("BOOKS") || xmlObj.jid.equals("ANXDIS"))
				{
					if(XT.articleType.equalsIgnoreCase("ES")){
						editors=" En: "+editors;
					}else{
						editors=" In: "+editors;
					}
				}
				else if(xmlObj.jid.equalsIgnoreCase("RETAIL")){
					editors=""+editors;				
				}else{
					if(XT.articleType.equalsIgnoreCase("ES")){
						editors=" En "+editors;
					}else{
						editors=" In "+editors;
					}
				}
			}
			
		}
		else if ((authorExist==true) && ((xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS")))
		{
			if (artTitle.length()>0) // 27-07-04
			{

				editors=" ("+editors;
			}
			
		}
		
		else if ((authorExist==true) && (xmlObj.bibStyle.equals("5-Asw")))
		{
			if(XT.articleType.equalsIgnoreCase("ES"))
				editors=" En: "+editors;
			else
			editors=" In: "+editors;
		}
		else if ((authorExist==true) && (xmlObj.bibStyle.equals("5-Manage")))
		{
			editors=" In "+editors;
		}
		else if ((authorExist==true) && ((xmlObj.bibStyle.equals("3-ZEFQ"))))
		//else if (((xmlObj.bibStyle.equals("3-ZEFQ"))))
		{
			if(artTitleExist==true)  //Gaurav & Rajeev [20-7-04] when no article title & single editor.
			{
				editors=editors;
			}
			else
			{		
				if(XT.articleType.equalsIgnoreCase("ES"))
					editors=" En: "+editors; 
				else
				editors=" In: "+editors; 
			}
		}
		else if ((authorExist==true) && ((xmlObj.bibStyle.equals("3")) || (xmlObj.bibStyle.equals("3a"))|| xmlObj.bibStyle.equals("6") || (xmlObj.bibStyle).equals("yijom-ns")))
		{
			if(artTitleExist==true)  //Gaurav & Rajeev [20-7-04] when no article title & single editor.
			{
				editors=editors;
			}
			else
			{		
				if(XT.articleType.equalsIgnoreCase("ES"))
					editors=" En: "+editors; 
				else
				editors=" In: "+editors; 
			}
		}
		else
		{
			editors=editors;
		}
		//
		global.for_editor="no";
		
		if(xmlObj.pit.equalsIgnoreCase("BRV") && xmlObj.jid.equalsIgnoreCase("SOCSCI"))
		{
			//System.out.println(editors);			
			int firstIn=editors.indexOf(",");
			int lastIn=editors.lastIndexOf(",");
			if(firstIn != -1)
			{
				if(firstIn == lastIn)
				{
					editors=editors.replaceFirst(","," and");
				}
				else if(lastIn > firstIn)
				{
					StringBuffer tempEds= new StringBuffer(editors);
					tempEds.insert(lastIn+1," and");
					editors=tempEds.toString();
				}

			}
			//
		}
		//System.out.println("editors====> : "+editors);
		//System.in.read();
		return editors;
	}
	//-------------------------------------------------------------------------------------

	private String bibBook() throws IOException
	{
		String book    = new String();
		String sTag    = new String();
		String title   = new String();
		String edition = new String();
		String bSeries = new String();
		String date    = new String();
		String pub     = new String();
		String isbn    = new String();
        while(!sTag.equals("</SB:BOOK>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:TITLE>"))
				{
					title = getBibTitle("</SB:TITLE>");	
					//System.out.println("title 3 -->"+title)	;
					//System.in.read();
					bTitle=title;
					
					if(physt_brv.length()>0)//08/11/2006 Ravi
					{
						
							physt_brv+=". "+title;
							//System.out.println("physt_brv 3 -->"+physt_brv)	;
							//System.in.read();
					} //Gaurav For Book Review SOCSCI--11/25/04
					else
					{	physt_brv=title; //Gaurav For Book Review SOCSCI--11/25/04
						//System.out.println("physt_brv 3 -->"+physt_brv)	;
						//System.in.read();
					}
				}
				else if(sTag.equals("<SB:EDITION>"))
				{
					edition = edition + xmlObj.extractData("</SB:EDITION>", true);
					TempAutoth=edition;
					//System.out.println("edition 5520 -->"+edition);
					//System.in.read();
				}
				else if(sTag.equals("<SB:BOOK-SERIES>"))
				{
					bSeries = bibBookSeries();
					//System.out.println("RRRR "+bSeries);
					//System.in.read();
				}
				else if(sTag.equals("<SB:DATE>"))
				{
					if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
					{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5]
					}
					else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
					{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5]
					}
					else if(xmlObj.jid.equals("SYAPM"))
					{
						
						tempDateForSYAPM= xmlObj.extractData("</SB:DATE>", true);
						//System.out.println("111111111111111111111111111111 "+tempDateForSYAPM);
					}
					/*else if(xmlObj.jid.equals("PROTIS"))
					{
						
						tempDateForSYAPM= xmlObj.extractData("</SB:DATE>", true);
						//System.out.println("111111111111111111111111111111 "+tempDateForSYAPM);
					}*/
					else
					{
						date = xmlObj.extractData("</SB:DATE>", true);
						//System.out.println("tempLabel===>"+tempLabel);
						if(tempLabel.length()>0)  // 15-07-2004
						{
							if(tempLabel.startsWith(date))
								date=tempLabel;
							tempLabel="";
						}
						//System.out.println("datedatedatedatedate "+date);

					}
				}
				else if(sTag.equals("<SB:PUBLISHER>"))
				{
					pub = sbPublisher();
					//System.out.println("uuuuuuuuuuuuuu ---->"+pub);
					//System.in.read();
				}

				else if(sTag.equals("<SB:ISBN>"))
				{
					isbn = xmlObj.extractData("</SB:ISBN>", true);
				}
			}
		}
		//System.out.println("uuuuuuuuuuuuuu ---->"+pub);
		//System.in.read();
		//Combined the data according to Journal
		//-------------------------------------------------------------------------------------
		if (xmlObj.bibStyle.equals("1")||(xmlObj.bibStyle).equals("1b")) 
		{
			book+= artTitle;
			
			if(title.length()>0)
			{
				if (bSeries.length()>0)
				{
					if (xmlObj.jid.equals("SYAPM"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=". En: "+title;
						else
						book+=". In: "+title;
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=", en: "+title;
						else
						book+=", in: "+title;
					}
										
				}
				else
				{
					if(global.cont_avail.equals("yes"))  
					{
						if (xmlObj.jid.equals("SYAPM"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								book+=". En: "+title;
							else
							book+=". In: "+title;
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								book+=", en: "+title;
							else
							book+=", in: "+title;
						}
					}                // [27-07-04 : CE:Feedback: in: between CT & BT when no editor]
					else
						book+=" "+title;

				}
			}
			//System.out.println("--------------->>"+book);
			if(bSeries.length()>0)
			{
				if(book.length()>0)
					book+=", "+bSeries;//Feedback: BT, BST....//Kishor[5/07/2004] Gaurav--11/22/04--
				else
					book+=bSeries;
				
			}
			if(edition.length()>0 && bSeries.length()==0)
			//System.out.println("book1111===>"+book);
				if (xmlObj.jid.equals("JASCER")) 
				book+=", "+edition+", ";
				else
				book+=", "+edition+", "; // Modified by Gaurav Based On New requirment.
			if(edition.length()>0 && bSeries.length()>0)
				book+=", "+edition+", "; // Modified by Gaurav Based On New requirment.
			if(pub.length()>0)
			{
				if(edition.length()>0)
					book+=pub;
				else
					 if (xmlObj.jid.equals("SOCSCI")) 
				{
					book+=" "+pub;
				}
				else
				{
					book+=", "+pub;
				}
			}	
			
			if(date.length()>0)
			{
				if(xmlObj.jid.equals("JASCER"))
				{
					date="("+date+")";
				}
				if(xmlObj.jid.equals("JASCER"))
				{
					book+=" ";
				}
				else
				{
					if(!book.endsWith(", "))
						book+=", ";
				}
				book+=date;
			}
			//System.out.println("book2222===>"+book);

			if(book.startsWith(", in:") && authorExist == true)
			{
				book = "?"+book;
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
			
		}
		else if (xmlObj.bibStyle.equals("brvhead")) 
		{
			
			book+= artTitle;
			
			if(title.length()>0)
			{
				if (bSeries.length()>0)
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						book+=", en: "+title;
					else
					book+=", in: "+title;
										
				}
				else
				{
					//System.out.println("global.cont_avail==>"+global.cont_avail);
					/**
					*Added By : Ravi
					*Date : [29/06/2007]
					*Modify Point: In book Review journal "in" word not come between author and title. as per marking in pdf file
					*Request By : TPMS Change Request.
					*/
					/*if(global.cont_avail.equals("yes"))  
						book+=", in: "+title;
					else
						book+=" "+title;
						*/
					if(global.cont_avail.equals("yes"))  
					{	
						//book+=", "+title;
						book+=" "+title;
					}
					else
						book+=" "+title;
					//end
					
				}
			}
			if(bSeries.length()>0)
			{
				if(book.length()>0)
					book+=", "+bSeries;
				else
					book+=bSeries;
				
			}
			
			//blocked by Ravi [11/11/2006 change due to Vivek]
			/*
			if(edition.length()>0 && bSeries.length()==0)
				book+=", "+edition+", ";
			if(edition.length()>0 && bSeries.length()>0)
				book+=", "+edition+", "; 
				
			*/
			//Added by Ravi [11/11/2006 change due to Vivek]
			//TempAutoth
		//	System.out.println("book -- > "+book);
			if(xmlObj.jid.equals("PHYST"))
			{
				if(edition.length()>0 && bSeries.length()==0)
					{
						book+=", "+TempAutother;
						TempAutother="";
					}
				if(edition.length()>0 && bSeries.length()>0)
					{
						book+=", "+TempAutother;
						TempAutother="";
					}
				if(edition.length()==0 && bSeries.length()==0)
					{
						book+=", "+TempAutother;
					
						TempAutother="";
					}
			}
			else
			{
					if(edition.length()>0 && bSeries.length()==0)
						book+=", "+edition+", ";
					if(edition.length()>0 && bSeries.length()>0)
						book+=", "+edition+", "; 
			}
			
			//System.out.println("book ---->"+book.trim());
			if(pub.length()>0)
			{
				
				if(book.trim().length()==1 && book.trim().equalsIgnoreCase(","))//09/11/2009
				{
					book="";
				}
				if(edition.length()>0)
					book += pub;
				else
				{
					if(book.length() > 0)//abhay 21/06/2006
						book += ", " + pub;
					else
						book += pub;
				}
				//System.out.println("---->"+book);
			}	
			//System.out.println("1 book ---->"+book);
			if(date.length()>0)
			{
				//book += " (" + date + ")"; 
				//added by ravi [10/11/2006 change by Vivek]
				if(xmlObj.jid.equals("PHYST"))
				{
					book += " " + date;
				}
				else
				{
					if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						book += ", " + date;
					}
					else if(xmlObj.jid.equals("CARP"))//08-06-2010
					{
						book += ", " + date;//08-06-2010
					}
					else
					{
						book += " (" + date + ")";
					}
				}
				
			}

			if(book.startsWith(", in:") && authorExist == true)
			{
				book = "?" + book;
			}
			if(isbn.length() > 0)
			{
					//book+=", "+"ISBN: "+isbn;
					global.isbn1 = isbn;
					isbn="";
			}
			
		}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("1a"))
		{
		    book+= artTitle;
			if(title.length()>0)
			{
				if(global.cont_avail.equals("yes")&&(bSeries.length()>0))
				{
					
				book+=", "+title;//24-07-04 Gaurav(When CT and BT is Given and  with no Editors)
				}
				 else if(global.cont_avail.equals("yes")&&(bSeries.length()==0))
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						book+=",  en: "+title;
					else
					book+=",  in: "+title;
				}

				else
				{
					book+=title;	
				}
			}
			if(bSeries.length()>0)
			{
				if (book.length()==0)
				{
					book+=bSeries;
				}
				else
				{
					book+=", "+bSeries;//Feedback: BT, BST....//Kishor/Gaurav[5/07/2004]
				}
			}
			if(edition.length()>0)
				book+=", "+edition+", "; // Modified by Neels for test article 943
			if(pub.length()>0)
			{
				if(edition.length()>0)
					book+=pub;
				else
				{
					if(xmlObj.jid.equals("CHIABU")) //17-11-04--Rajeev
						book+="; "+pub;
					else
						book+=", "+pub;
				}
			}			
			if(date.length()>0)
			{
				if(!book.endsWith(", "))
					book+=", ";
				book+=date;
			}

			if(book.startsWith(", in:") && authorExist == true)
			{
				book = "?"+book;
			}

			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}

		}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("2"))
		{
			if(date.length()>0)
			{
				 if(global.cont_avail.equals("yes"))
				{
					book+=date;
				    book+=". ";
				}			
			}
			
			if(artTitle.length()>0)
			{
				//book+= artTitle+".";
				book+= artTitle+"";
			}
			if(transTitle.length()>0)
			{
				//book+= transTitle;//old
				if(artTitle.length()>0)
					book+=" ("+transTitle+")";//25-06-2010
				else
					book+="("+transTitle+")";//25-06-2010
			}
			book+=".";//25-06-2010
			if(artComment.length()>0)
			{
				if(artComment.endsWith("]") || artComment.endsWith(")") )
					book+=" "+artComment+".";
				else
					book+=" "+artComment+"";
				artComment="";
			}
			if(title.length()>0)
			{
				if(artTitle.length()>0)
				{
					if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						if(book.endsWith("."))
						{
							book=book.substring(0,book.length()-1)+",";
						}
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" en: "+title+".";
						else
						book+=" in: "+title+".";
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" En: "+title+".";
						else
						book+=" In: "+title+".";
					}
				}
				else
				{
					book+=" "+title+".";
				}
			}
				//book+=" "+title+".";
			if(edition.length()>0 && bSeries.length()==0)
			{
				if(book.endsWith("."))
				{
					book = book.substring(0,book.length()-1);
				}
				book+=", "+edition;
				if(!edition.endsWith("."))
				{
					book+=".";
				}
			}

			if(bSeries.length()>0)
			{
				
				book+=" "+bSeries;
				if(edition.length()>0 )
				{
				//book = book.substring(0,book.length()-1);
				book+=", "+edition;	
				//book+=" "+edition;//Gaurav --11/17/04--Based On new requierment.
				bSeries="";
				}
			}
		/*	if(edition.length()>0 && bSeries.length()>0)
			{
				book+=", dddddddd "+edition;//Gaurav 11/15/04 Based on New Requirment..

			}*/

			if(pub.length()>0)
			{
				if(edition.length()>0)
					book+=" "+pub;
				else if(!book.endsWith("."))
					book+=", "+pub;
				else
					book+=" "+pub;
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("3-ZEFQ"))
		{
			//book+=". ";
			if(artTitle.length()>0)
			{
				/*if(xmlObj.bibStyle.equals("3a"))
				{				
						if (title.length()>0 ||journalTitle.length()>0)
						{
							//System.out.println("Journal title --- yes -----["+journalTitle+"]");
							//article title should be roman
						}
						else
						{
							artTitle="{\\it "+artTitle+"}";
						}
						
				}*/ 
				
				book+="{\\it{"+artTitle+"}}";
				if(transTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
						transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					if(artTitle.endsWith("?"))//19-07-04(Gaurav & Rajeev--When artTitle endsWith ?----)
					{
					book+="";
					}
					else
					{
						book+=".";
					}
				}
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
						transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			artComment="";
    		
	    	if(title.length()>0)
			{
				title+=".";
				if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
				{
					title="{\\it{"+title+"}}";
				}
				if (bSeries.length()>0 ) // [Gaurav & Rajeev --when CT & BT appear without PT]
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						book+=" En: "+title;
					else
					book+=" In: "+title;
				}
				else
				{
					if(global.cont_avail.equals("yes"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" En: "+title;
						else
						book+=" In: "+title;
					}
					else        //26-07-04
					{
                          book+=title;
					}

				}
			}
			if(edition.length()>0 )//Gaurav--11/16/04--Based On New requirment.
			{
				if(bSeries.length()>0 )
					book+=" "+bSeries+", "+edition+" ";
				else
					book+=" "+edition+" ";
			}
			else
			{
				if(bSeries.length()>0 )
					book+=" "+bSeries;
			}

			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				book+=" "+date+", ";
			}

			if(edition.length()>0)
				book+=pub;
			else
			{
				if(vnr.length()>0)//21-07-04(Gaurav & Rajeev--When dot  after volume number without edition-------)
				{
					if (xmlObj.bibStyle.equals("yijom-ns"))
					{
						book+=": "+pub;
					}
					else
					{
						book+=". "+pub;
					}
				}
				else
				{
				          book+=" "+pub;
				}
			}
			
			/*if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				if (xmlObj.bibStyle.equals("yijom-ns"))
				{
					book+=" "+date;
				}
				else
				{
					if (book.endsWith(".") && pub.length()==0)
						book=book.substring(0,book.length()-1);
					book+="; "+date;
				}
			}*/
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("3")||xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
		{
			//book+=". ";
			//System.out.println("---------------------->>"+artTitle);;
			if(artTitle.length()>0)
			{
				if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
				{				
						if (title.length()>0 ||journalTitle.length()>0)
						{
							//System.out.println("Journal title --- yes -----["+journalTitle+"]");
							//article title should be roman
						}
						else//block on 05-08-2011
						{
							if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
							{
							}
							else
							artTitle="{\\it "+artTitle+"}";
							//System.out.println("Journal title --- yes -----"+artTitle);
						}
						
				} 
				if(xmlObj.bibStyle.equals("3")&& xmlObj.jid.equals("OSI"))//ravi 15-04-2011
				{				
						if (title.length()>0 ||journalTitle.length()>0)
						{
							//System.out.println("Journal title --- yes -----["+journalTitle+"]");
							//article title should be roman
						}
						else
						{

							artTitle="{\\it "+artTitle+"}";
						}
						
				} 
				//System.out.println("title --- yes -----["+title+"]");
				book+=" "+artTitle;
				if(transTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
						transTitle="{\\it "+transTitle+"}";
					//book+=" "+transTitle;//old
					book+=" ["+transTitle+"]";//25-06-2010
				}
				//System.out.println("111book ::"+book);
				if(artComment.length()>0)
				{
					if(xmlObj.jid.equals("JSAMS")&&Integer.parseInt(xmlObj.aid)>global_cutOff_aid_for_JSAMS)
					{
						
					}
					else
					book+=" "+artComment+".";
				}
				else
				{
					if(artTitle.endsWith("?"))//19-07-04(Gaurav & Rajeev--When artTitle endsWith ?----)
					{
					book+="";
					}
					else
					{
						if(xmlObj.jid.equals("JSAMS")&&Integer.parseInt(xmlObj.aid)>global_cutOff_aid_for_JSAMS)
							book+=",";
						else
						book+=".";
					}
				}
				
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
						transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			artComment="";
    		
	    	if(title.length()>0)
			{
				title+=".";
				if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
				{
					if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
					{
						title=title;
					}
					else
					title="{\\it{"+title+"}}";
				}
				if(xmlObj.jid.equals("OSI")&&Integer.parseInt(xmlObj.aid)>global_cutOff_aid_for_OSI)//21-10-2011
				{
					title="{\\it{"+title+"}}";
				}
				if (bSeries.length()>0 ) // [Gaurav & Rajeev --when CT & BT appear without PT]
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						book+=" En: "+title;
					else if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
						book+=" Em: "+title;
					else
					book+=" In: "+title;
				}
				else
				{
					if(global.cont_avail.equals("yes"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" En: "+title;
						else if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
							book+=" Em: "+title;
						else
						book+=" In: "+title;
					}
					else        //26-07-04
					{
                          book+=title;
						  
					}

				}
				
			}
			if(edition.length()>0 )//Gaurav--11/16/04--Based On New requirment.
			{
				if(xmlObj.jid.equals("HEAP"))
				{
					if(bSeries.length()>0 )
						book+=" "+bSeries+", "+edition+" ";
					else
					{
						//System.out.println("------------>>>"+book);
						if(book.endsWith("."))
						{
							book=book.substring(0,book.length()-1);
						}
						
						book+=", "+edition+" ";
						//System.out.println("------------>>>"+book);
					}
				}
				else
				{
					if(bSeries.length()>0 )
						book+=" "+bSeries+", "+edition+" ";
					else
						book+=" "+edition+" ";
				}
			}
			else
			{
				if(bSeries.length()>0 )
					book+=" "+bSeries;
			}

			/*if(bSeries.length()>0 && edition.length()>0)//Gaurav--11/16/04--Based On New requirment.11/16/04
			{
				book+=" "+bSeries;
				book+=", "+edition+" ";
			}*/

			if(edition.length()>0)
				book+=pub;
			else
			{
				if(vnr.length()>0)//21-07-04(Gaurav & Rajeev--When dot  after volume number without edition-------)
				{
					if (xmlObj.bibStyle.equals("yijom-ns"))
					{
						book+=": "+pub;
					}
					else
					{
						book+=". "+pub;
					}
				}
				else
				{
				          book+=" "+pub;
				}
			}
			
			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				if (xmlObj.bibStyle.equals("yijom-ns"))
				{
					book+=" "+date;
				}
				else
				{
					if (book.endsWith(".") && pub.length()==0)
						book=book.substring(0,book.length()-1);
					if(xmlObj.jid.equals("JSAMS")&&Integer.parseInt(xmlObj.aid)>global_cutOff_aid_for_JSAMS)
						book+=", "+date;
					else
					book+="; "+date;
				}
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
			//System.out.println("book ::"+book);
		}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("3a-Jecs"))
		{
			//book+=". ";
			if(artTitle.length()>0)
			{
				if (title.length()>0 || journalTitle.length()>0)
				{

				}
				else
				{
					artTitle="{\\it "+artTitle+"}";
				}
								
				book+=" "+artTitle;
				if(transTitle.length()>0)
				{
					transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			artComment="";

			if(title.length()>0)
			{
				title="{\\it{"+title+"}}.";
				book+=" "+title;
			}
			//-------------------------------------------------------
			if(edition.length()>0)
			{
				if (book.endsWith("."))
				{
					book = book.substring(0,book.length()-1);
					book+=" ({\\it{"+edition+"}}). ";
				}
				else
				{
					book+=" ({\\it{"+edition+"}}). ";
				}
				
			}
			//-------------------------------------------------------
			
			if(bSeries.length()>0)
			{
				if ((bSeries.startsWith(" In ")) && (global.cont_avail.equals("no")))
				{
					bSeries = bSeries.substring(3);
					book+=" "+bSeries;
				}
				else
				{
					book+=" "+bSeries;
				}
				
			}
			if(edition.length()>0)
				book+=pub;
			else
				if (book.endsWith(".")||book.endsWith(". "))
				{
					book+=" "+pub;
				}
				else
				{
					book+=". "+pub;
				}
				
			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				book+=", "+date;
								
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------
		else if ((xmlObj.bibStyle).equals("chea"))
		{
			if(artTitle.length()>0)
			{
				if (title.length()>0 || journalTitle.length()>0)
				{

				}
				else
				{
					artTitle="{\\it "+artTitle+"}";
				}
								
				book+=" "+artTitle;
				if(transTitle.length()>0)
				{
					transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+";";
				}
				else
				{
					book+=";";
				}
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+";";
				}
				else
				{
					book+=";";
				}
			}
			artComment="";

			if(title.length()>0)
			{
				title="{\\it{"+title+"}};";
				book+=" "+title;
			}
			//-------------------------------------------------------
			if(edition.length()>0)
			{
				if (book.endsWith(";"))
				{
					book = book.substring(0,book.length()-1);
					book+=", "+edition+"; ";
				}
				else
				{
					book+=", "+edition+"; ";
				}
				
			}
			//-------------------------------------------------------
			
			if(bSeries.length()>0)
			{
				if ((bSeries.startsWith(" In ")) && (global.cont_avail.equals("no")))
				{
					bSeries = bSeries.substring(3);
					book+=" "+bSeries;
				}
				else
				{					
					if (artTitle.length()>0 && book.endsWith(artTitle+";"))
					{
						book=book.substring(0,book.length()-1)+",";
					}
					book+=" "+bSeries;
				}
				
			}
			if(edition.length()>0)
				book+=pub;
			else
			{
				if (book.endsWith(";")||book.endsWith("; "))
				{
					book+=" "+pub;
				}
				else
				{
					book+="; "+pub;
				}
			}
						
			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				book+=", "+date;
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("4"))
		{
			if(artTitle.length()>0)
			{
				artTitle=" "+artTitle;
				book+=" "+artTitle;
				if(transTitle.length()>0)
				{
					//transTitle="{\\it "+transTitle+"}";//old
					transTitle="[{\\it "+transTitle+"}]";//25-06-2010
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					transTitle=" "+transTitle;
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{

					book+=".";
				}
			}
			artComment="";

			if(title.length()>0)
			{
				//System.out.println("title "+title);
				title+=".";
				//System.out.println("title "+title);
			  if (artTitle.length()>0)//01-09-04---Gaurav on Swati feedback for NSM 3783..
				{
					
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						if(isEditor==true)
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								book+=" En: "+title;
							else
							book+=" In: "+title;
						}
						else
						{
							book+=" "+title;
						}
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" En: "+title;
						else
						book+=" In: "+title;
					}
				}
				else
				{
                     book+=" "+title;
				}
				
			}
			if(edition.length()>0 && bSeries.length()==0)
				book+=" "+edition+"";

			if(bSeries.length()>0)
			{
				book+=" "+bSeries;
				if(edition.length()>0)
                book+=", "+edition;
                   
			}

			if(edition.length()>0)
				book+=" "+pub;
			else if (bSeries.length()>0)
			{
				if(xmlObj.jid.equals("PROTIS"))//09-07-2010
				{
					book+=", "+pub;
				}
				else
					book+=". "+pub;//Gaurav--11/20/04--zzzzzzzzz
			}
			else
				book+=" "+pub;
               //System.out.println("book "+book);

			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				if(book.endsWith(".") && edition.length()==0)//Gaurav -11/15/04-Based on new Requirment.
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tempDateForSYAPM="("+date+")";
					}
					else
					{
					book=book.substring(0,book.length()-1);
					book+="; "+date;//Gaurav -11/15/04-Based on new Requirment.
					}


				}
				else
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tempDateForSYAPM="("+date+")";
					}
					else
					book+="; "+date;
				}
				//System.out.println("book "+book);
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
	
			else if (xmlObj.bibStyle.equals("5-Retail"))//14/04/2008
			{
			
				
			if (global.cont_avail.equals("no"))
			{
				if (xmlObj.jid.equals("RETAIL"))								
				{
					book+="{\\it{"+title+"}}, ";
				}
				else
				{	
					book+="{\\it{"+title+"}}. ";
					
				}
				if(date.length()>0)
					book+="("+date+")";
				book+=", ";
				
			}
			else
			{
				
				if(date.length()>0)
					book+="("+date+")";
				book+=", ";			
				
				
				if(transTitle.length()>0)
				{					
					book+="{\\it{"+transTitle+"}} ";
					
					
				}
				
				//
				if(title.length()>0)
				{
					if (artTitleExist==true && bSeries.length()==0)
					{
						
						//PT without editors but continue with contribution title.
						if (xmlObj.jid.equals("RETAIL"))								
							{
								book+=" ``"+artTitle+",''";
								//System.out.println(" artTitle 2 -- > "+artTitle);
								//System.in.read();
								
							}
						else
							{
								book+=" "+artTitle+"."; 
							//	System.out.println(" artTitle 3 -- > "+artTitle);
							//	System.in.read();
							} // [28-07-04 --Gaurav & Rajeev-- Case of Missing Article Title]
						
						if (xmlObj.jid.equals("RETAIL"))														
						{	
							book+=" in {\\it "+title+"} ";	
								
						}
						else
							{
								book+=" In {\\it "+title+"} ";	//08/11/2006 Ravi
							//	System.out.println(" book -- > "+book);
							//	System.in.read();
							}
					}	
					else
					{
						book+=" {\\it "+title+"} ";
					}
					
				}

				else//No BT THEN contribution title should be BT ->See Cap Guide Example 9
				{
					//book=book+"{\\it{"+artTitle+"}} ";//old Ravi [06/11/2006 by Usmani's Mail on 06/11/2006]
					//added by Ravi [06/11/2006 by Usmani's Mail on 06/11/2006]
					if(XT.jid.equalsIgnoreCase("RETAIL"))
					{
						book=book+"{\\it{"+artTitle+",}} ";
					}
					else
					{
						book=book+"{\\it{"+artTitle+"}} ";
					}
					//System.out.println(" book 2 -- > "+book);
					//System.in.read();	


				}
				if(artComment.length()>0)
				{
						

					book+=" "+artComment+".";//26-07-04(Gaurav & Rajeev)
					artComment="";
				}
			
			}

			if(edition.length()>0)
			{
				if ((xmlObj.bibStyle).equals("5-BookB"))
				{
					book+=" "+edition+", ";
				}
				else if (xmlObj.jid.equals("RETAIL"))								
				{
					book+=" "+edition+" ";	
				
				}			
				else
				{
					book+="("+edition+"). ";//make change
				}
			
			}
			else
			{
				if ((!book.endsWith(". ")) && (!book.endsWith(".")))
				{
					if (book.endsWith(" "))
					{
						book=book.substring(0,book.length()-1);
					}
					if(book.endsWith("?}}")|| book.endsWith("?}} ")) // [23-07-04 - Gaurav & Rajeev]
					{
						book+=" ";
					}
					else if(XT.jid.equalsIgnoreCase("RETAIL"))//Added by Ravi [requirement by Usmani mail date 06/11/2006]
					{
						//book+=", ";//14/04/2008
						//System.out.println("book --------------------------->"+book);
						//System.in.read();
					
					}
					else
					{
						book+=". ";//old [08/11/2006 Ravi]
						//book+="  ";
						
					}
				}
				else
				{
					book+=" ";
				}
			//System.out.println("book --------------------------->"+book);
			//System.in.read();			
			}
			if(bSeries.length()>0)//26-07-04(Gaurav & Rajeev)
			{
				book+=" "+bSeries+" ";
			}
			if (book.endsWith(".}}. "))
			{
					book=book.substring(0,book.length()-2)+" ";
			}
			
			if(pub.length()>0)
			{
				if(xmlObj.bibStyle.equals("5-BookB"))
				{
					 if(book.endsWith(". "))
					{
						book=book.substring(0,book.length()-2);
					//	book+=", "+pub;//old			
						//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
						//[15/12/2006]
						book+=", "+pub+".";
					}
					else
					{
						//book+=pub;//old
						//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
						//[15/12/2006]
						book+=pub+".";
					}
				}
				else
				{
					//book+=pub;//old
					//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
					//[15/12/2006]
					book+=pub+".";
				}
			
			}	
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
			//System.out.println(" book -- > "+book);
			//		System.in.read();
	}
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("5")||( xmlObj.bibStyle.startsWith("IChemE"))||(xmlObj.bibStyle).equals("5-BookB"))//Kishor [19/06/2004]
		{
			//System.out.println("1--------------------");
				
			if (global.cont_avail.equals("no"))
			{
				if (xmlObj.jid.equals("RETAIL"))								
					book+="{\\it{"+title+"}}, ";
				else
				{	
					if (xmlObj.jid.equals("ZOOGA"))//31-08-2011
						book+= title;
					else
						book+="{\\it{"+title+"}}. ";
					
				}
				if(date.length()>0)
					book+="("+date+")";
				book+=". ";
				
			}
			else
			{
				//System.out.println("2---------------------");
				if(date.length()>0)
					book+="("+date+")";
				book+=". ";			
				
				
				if(transTitle.length()>0)
				{					
					//book+="[{\\it{"+transTitle+"}}] ";
					if (xmlObj.jid.equals("ZOOGA"))//31-08-2011
					book+="("+transTitle+") ";
					else
					book+="({\\it{"+transTitle+"}}) ";
					
					
				}
				
				//
				if(title.length()>0)
				{
					if (artTitleExist==true && bSeries.length()==0)
					{
						
						//PT without editors but continue with contribution title.
						if (xmlObj.jid.equals("RETAIL"))								
							{
								book+=" ``"+artTitle+",''";
								//System.out.println(" artTitle 2 -- > "+artTitle);
								//System.in.read();
								
							}
						else
							{
								book+=" "+artTitle+"."; 
							//	System.out.println(" artTitle 3 -- > "+artTitle);
							//	System.in.read();
							} // [28-07-04 --Gaurav & Rajeev-- Case of Missing Article Title]
						
						if (xmlObj.jid.equals("RETAIL"))														
						{	
							book+=" in {\\it "+title+"} ";	
								
						}
						else
						{
							if (xmlObj.jid.equals("ZOOGA"))//31-08-2011
							book+=" In "+title+" ";	
							else
								book+=" In {\\it "+title+"} ";	//08/11/2006 Ravi
							//	System.out.println(" book -- > "+book);
							//	System.in.read();
						}
					}	
					else
					{
						if (xmlObj.jid.equals("ZOOGA"))//31-08-2011
							book+=" "+title+" ";
						else
							book+=" {\\it "+title+"} ";
					}
					
				}

				else//No BT THEN contribution title should be BT ->See Cap Guide Example 9
				{
					//book=book+"{\\it{"+artTitle+"}} ";//old Ravi [06/11/2006 by Usmani's Mail on 06/11/2006]
					//added by Ravi [06/11/2006 by Usmani's Mail on 06/11/2006]
					
					if(XT.jid.equalsIgnoreCase("RETAIL"))
					{
						book=book+"{\\it{"+artTitle+"}}, ";
					}
					else if(XT.jid.equalsIgnoreCase("ZOOGA"))//12-04-2010
					{
						book=book+artTitle+" ";
					}
					else
					{
						book=book+"{\\it{"+artTitle+"}} ";
					}
					//System.out.println(" book 2 -- > "+book);
					//System.in.read();	


				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";//26-07-04(Gaurav & Rajeev)
					artComment="";
				}
			
			}

			if(edition.length()>0)
			{
				if ((xmlObj.bibStyle).equals("5-BookB"))
				{
					book+=" "+edition+", ";
				}
				else if (xmlObj.jid.equals("RETAIL"))								
				{
					book+=" "+edition+" ";	
				
				}			
				else
				{
					if(xmlObj.bibStyle.equals("5"))
					{
						book+="("+edition+"<pageinbookreference>). ";//make change
					}
					else
					{
						book+="("+edition+"). ";//make change
					}
				}
			}
			else
			{
				if ((!book.endsWith(". ")) && (!book.endsWith(".")))
				{
					if (book.endsWith(" "))
					{
						book=book.substring(0,book.length()-1);
					}
					if(book.endsWith("?}}")|| book.endsWith("?}} ")) // [23-07-04 - Gaurav & Rajeev]
					{
						book+=" ";
					}
					else if(XT.jid.equalsIgnoreCase("RETAIL"))//Added by Ravi [requirement by Usmani mail date 06/11/2006]
					{
						book+=", ";
						//System.out.println("book --------------------------->"+book);
						//System.in.read();
					
					}
					else
					{
						book+=". ";//old [08/11/2006 Ravi]
						//book+="  ";
						
					}
				}
				else
				{
					book+=" ";
				}
				// 24-10-2015
				// Condition added to display page-range in book reference without <sb:edition> tag.
				if(xmlObj.bibStyle.equals("5")){
					book+="<><pageinbookreference>. ";
				}
			//System.out.println("book --------------------------->"+book);
			//System.in.read();			
			}
			if(bSeries.length()>0)//26-07-04(Gaurav & Rajeev)
			{
				book+=" "+bSeries+" ";
			}
			if (book.endsWith(".}}. "))
			{
					book=book.substring(0,book.length()-2)+" ";
			}
			
			if(pub.length()>0)
			{
				if(xmlObj.bibStyle.equals("5-BookB"))
				{
					 if(book.endsWith(". "))
					{
						book=book.substring(0,book.length()-2);
					//	book+=", "+pub;//old			
						//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
						//[15/12/2006]
						book+=", "+pub+".";
					}
					else
					{
						//book+=pub;//old
						//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
						//[15/12/2006]
						book+=pub+".";
					}
				}
				else
				{
					//book+=pub;//old
					//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
					//[15/12/2006]
					book+=pub+".";
				}
			
			}	
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
	}
		//-------------------------------------------------------------------------------------
	else if ((xmlObj.bibStyle).equals("DDT-NS"))// Gaurav & Rajeev (3-08-2004)
		{
		
			if (global.cont_avail.equals("no"))
			{
				book+="{\\it{"+title+"}} ";
				if(date.length()>0)
					book+="("+date+")";
				book+=" ";
			}
			else
			{
				if(date.length()>0)
					book+="("+date+")";
				book+=" ";			
				

				if(transTitle.length()>0)
				{					
					book+="{\\it{"+transTitle+"}} ";
				}
				
				//
				if(title.length()>0)
				{
					if (artTitleExist==true && bSeries.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						book+=" "+artTitle+".";  // [28-07-04 --Gaurav & Rajeev-- Case of Missing Article Title]
						book+=" In {\\it "+title+"} ";	
					}	
					else
					{
						book+=" {\\it "+title+"} ";
					}
					
				}

				else//No BT THEN contribution title should be BT ->See Cap Guide Example 9
				{
					book=book+"{\\it{"+artTitle+"}} ";
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+" ";//26-07-04(Gaurav & Rajeev)
					artComment="";
				}
			}

			if(edition.length()>0)
			{
				book+="("+edition+") ";
			}
			else
			{
				if ((!book.endsWith(". ")) && (!book.endsWith(".")))
				{
					if (book.endsWith(" "))
					{
						book=book.substring(0,book.length()-1);
					}
					if(book.endsWith("?}}")|| book.endsWith("?}} ")) // [23-07-04 - Gaurav & Rajeev]
					{
						book+=" ";
					}
					else
					{
						book+=" ";
					}
				}
				else
				{
					book+=" ";
				}			
			}
			if(bSeries.length()>0)//26-07-04(Gaurav & Rajeev)
			{
				book+=" "+bSeries+" ";
			}
			if (book.endsWith(".}}. "))
			{
					book=book.substring(0,book.length()-5)+"}}, ";
			}
			if (pub.length()>0)
			{
				if (book.endsWith(" "))
				{
					book=book.substring(0,book.length()-1)+", ";
				}
				book+=pub;		
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		
		
		}
			
		//-------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("5-Asw"))//Kishor [19/06/2004]
		{
				
			if (global.cont_avail.equals("no"))
			{
				book+="{\\it{"+title+"}}. ";
				if(date.length()>0)
					book+="("+date+")";
				book+=". ";
			}
			else
			{
				if(date.length()>0)
					book+="("+date+")";
				book+=". ";				
				if(transTitle.length()>0)
				{
					book+="{\\it{"+transTitle+"}} ";
				}
				
				//
				if(title.length()>0)
				{
					if (artTitleExist==true && bSeries.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						book+=" "+artTitle+".";  // [28-07-04 --Gaurav & Rajeev-- Case of Missing Article Title]
						if(XT.articleType.equalsIgnoreCase("ES"))
							book+=" En: {\\it "+title+"} ";	
						else
						book+=" In: {\\it "+title+"} ";	
					}	
					else
					{
						book+=" {\\it "+title+"} ";
					}
					
				}
				else//No BT THEN contribution title should be BT ->See Cap Guide Example 9
				{
					book=book+"{\\it{"+artTitle+"}} ";
				}
					if(artComment.length()>0)
				{
					book+=" "+artComment+".";//26-07-04(Gaurav & Rajeev)
					artComment="";
				}
			}
			if(edition.length()>0)
			{
				book+="("+edition+"). ";
				
			}
			else
			{
				if ((!book.endsWith(". ")) && (!book.endsWith(".")))
				{
					if (book.endsWith(" "))
					{
						book=book.substring(0,book.length()-1);
					}
					book+=". ";
				}
				else
				{
					book+=" ";
				}			
			}
			//book+=pub;//old
			//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
			//[15/12/2006]
				book+=pub+".";	
			
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("5-Manage"))//Kishor
		{
		
			if (global.cont_avail.equals("no"))
			{
				book+="{\\it{"+title+"}}. ";
				if(date.length()>0)
					book+=date;
				book+=". ";
			}
			else
			{
				if(date.length()>0)
					book+=date;
				book+=". ";				
				if(transTitle.length()>0)
				{
					book+="{\\it{"+transTitle+"}} ";
				}
				
				//
				if(title.length()>0)
				{
					if (artTitleExist==true && bSeries.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						book+=" "+artTitle+".";  // [28-07-04 --Gaurav & Rajeev-- Case of Missing Article Title]
						book+=" In {\\it "+title+"} ";	
					}	
					else
					{
						book+=" {\\it "+title+"} ";
					}
				}
				else//No BT THEN contribution title should be BT ->See Cap Guide Example 9
				{
					if (artTitle.length()>0)
					{
						book=book+"{\\it{"+artTitle+"}} ";
					}
				}
					if(artComment.length()>0)
				{
					book+=" "+artComment+".";//26-07-04(Gaurav & Rajeev)
					artComment="";
				}
			}
			if(edition.length()>0)
			{
				book+="("+edition+"). ";
			}
			else
			{
			  if ((!book.endsWith(". ")) && (!book.endsWith(".")))
				{
					if (book.endsWith(" "))
					{
						book=book.substring(0,book.length()-1);
					}
					book+=". ";
				}
				else
				{
					book+=" ";
				}
			}
			//book+=pub;//old
			//[comment for Change: ref sy=tyle 5 publisher name should be end with fullstop]
			//[15/12/2006]
			book+=pub+".";		
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//----------------------------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("6"))
		{
			//book+=". ";
			if(artTitle.length()>0)
			{
				artTitle="{\\it "+artTitle+"}";
				book+=" "+artTitle;
				if(transTitle.length()>0)
				{
					transTitle="[{\\it "+transTitle+"}]";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			else if(transTitle.length()>0)
			{
				if(transTitle.length()>0)
				{
					transTitle="{\\it "+transTitle+"}";
					book+=" "+transTitle;
				}
				if(artComment.length()>0)
				{
					book+=" "+artComment+".";
				}
				else
				{
					book+=".";
				}
			}
			artComment="";
			
			if(title.length()>0)
			{
				title="{\\it{"+title+"}.}";
				book+=" "+title;
			}
			if(edition.length()>0)
				book+=" "+edition+" ";

			if(bSeries.length()>0)
			{
				book+=" "+bSeries;
			}
			
			if(edition.length()>0)
				book+=pub;
			else
				book+=" "+pub;
			if(date.length()>0)
			{
				if(book.endsWith(" "))
				{
					book = book.substring(0,book.length()-1);
				}
				book+="; "+date;
			}
			if(isbn.length()>0)  // 29-07-04
			{
					book+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
		}
		//-------------------------------------------------------------------------------------
		artTitle="";
		title="";
		//System.out.println("book --------------------------->"+book);
		//System.in.read();
		return book;
	}
	//-----------------------------------------------------------------------------------

	private String sbPublisher() throws IOException
	{
		String pub = new String();
		String sTag = new String();
		String name = new String();
		String loc = new String();
		while(!sTag.equals("</SB:PUBLISHER>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:NAME>"))
				{
					name  = xmlObj.extractData("</SB:NAME>", true);
				}
				else if(sTag.equals("<SB:LOCATION>"))
				{
					loc = xmlObj.extractData("</SB:LOCATION>", true);
				}
			}
		}
		//Combined the data according to the Journal
		//--------------------------------------------------------------------------------
		if (xmlObj.bibStyle.equals("1")||(xmlObj.bibStyle).equals("1a")||(xmlObj.bibStyle).equals("1b"))
		{
			if(xmlObj.jid.equals("SOCSCI"))
			{
				pub+= loc;
					if(loc.length()>0)
					pub+=": "+name;
				else
					pub+=" "+name;
			}
			else
			{
			pub+= name;
			if(loc.length()>0)
				pub+=", "+loc;
			}
		}
		else if (xmlObj.bibStyle.equals("brvhead"))
		{
			/*if((XT.modelStyle.equals("-MODDFrench"))||(XT.modelStyle.equals("-MODEFrench")))
			{
				
				if(loc.length() > 0)
					pub +=loc+": ";
				pub += name+"; ";
			}
			else*/// commented by mukesh on 14-10-08
			{
					pub += name;
				if(loc.length() > 0)
					pub += ", "+loc;
			}
			//added by Ravi [10/11/2006 change by Vivek]
			if(xmlObj.jid.equals("PHYST"))
			{
				pub +=",";
				//System.out.println("pub-->"+pub);
			}	
				
		}
		//--------------------------------------------------------------------------------
		else if (xmlObj.bibStyle.equals("2"))
		{		
			//System.out.println("pub============>"+pub);
			pub+= name;
			if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
			{
				if(loc.length()>0)
				{
					pub=loc+": "+pub;
				}
				pubforsoceco=pub;
				if(global.dkj_edited_book.equals("yes"))
					pub="";
			}
			else
			{
				if(loc.length()>0)
				{
					pub+=", "+loc;
				}
			}
	
		}
		//--------------------------------------------------------------------------------
		if (xmlObj.bibStyle.equals("3-ZEFQ"))
		{	
			pub+= loc;
			if(loc.length()>0)
			{
				
				pub+=": "+name;
			}
			else
				pub+=" "+name;

			//System.out.println("0000000000"+pub);
		}
		//--------------------------------------------------------------------------------
		//--------------------------------------------------------------------------------
		if (xmlObj.bibStyle.equals("3")||xmlObj.bibStyle.equals("3a") || xmlObj.bibStyle.equals("6")|| (xmlObj.bibStyle).equals("yijom-ns"))
		{
			//System.out.println("loc "+loc);
			//System.out.println("name "+name);
			
			pub+= loc;
			if(loc.length()>0)
			{
				String duckaid=xmlObj.aid;
				if(duckaid.indexOf(".")!=-1)
					duckaid=duckaid.substring(0,duckaid.indexOf("."));
				if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
				{
					pub+=", "+name;
				}
				else
				pub+=": "+name;
			}
			else
				pub+=" "+name;
			//System.out.println("pub "+pub);
		}
		//--------------------------------------------------------------------------------		
		if (xmlObj.bibStyle.equals("3a-Jecs"))
		{	
			pub+= name;
			if(loc.length()>0)//Gaurav --29-08-04---When Location is  given..
			{
				pub+=", "+loc;
			}
		}
		//--------------------------------------------------------------------------------		
		if (xmlObj.bibStyle.equals("chea"))
		{	
			pub+= name;
			if(loc.length()>0)//Gaurav --29-08-04---When Location is  given..
			{
				pub+=": "+loc;
			}
		}
		//--------------------------------------------------------------------------------		
		//Kishor [21/06/2004]
		else if (xmlObj.bibStyle.equals("4"))
		{	
			if(xmlObj.jid.equals("PROTIS"))//05-07-2010
			{
					pub+= name;
				if(loc.length()>0)
					pub+=", "+loc;
				
			}
			else
			{
				pub+= loc;
				if(loc.length()>0)
					pub+=": "+name;
				else
					pub+=" "+name;
			}
		}
		//---------------------------------------------------------------------------------
		//Kishor [19/06/2004]
		else if ((xmlObj.bibStyle.equals("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
		{	
			if(( xmlObj.bibStyle.startsWith("IChemE")))
			{
				//pub+= loc;
				if(name.endsWith("."))
				{
					name=name.substring(0,name.length()-1);
				}
				//System.out.println("name "+name);
				//System.in.read();
				if(loc.length()>0)
				{				
					//	pub+=": "+name;//old[14/12/2006]
						pub+="("+name+", ";
				}
				else
				{
						//pub+=" "+name;//old[14/12/2006]
						pub+="("+name+")";	
				}
				if(loc.length()>0)
				{
					pub+= loc+")";
				}
			}
			else
			{
				pub+= loc;
				if(name.endsWith("."))
				{
					name=name.substring(0,name.length()-1);
				}
				//System.out.println("name "+name);
				//System.in.read();
				if(loc.length()>0)
				{				
					//	pub+=": "+name;//old[14/12/2006]
						pub+=": "+name+".";
				}
				else
				{
						//pub+=" "+name;//old[14/12/2006]
						pub+=" "+name+".";	
				}
			}
						
		}
		else if ((xmlObj.bibStyle.equals("5-Retail")))//14/04/2008
		{	
			if(( xmlObj.bibStyle.startsWith("IChemE")))
			{
				//pub+= loc;
				if(name.endsWith("."))
				{
					name=name.substring(0,name.length()-1);
				}
				//System.out.println("name "+name);
				//System.in.read();
				if(loc.length()>0)
				{				
					//	pub+=": "+name;//old[14/12/2006]
						pub+="("+name+", ";
				}
				else
				{
						//pub+=" "+name;//old[14/12/2006]
						pub+="("+name+")";	
				}
				if(loc.length()>0)
				{
					pub+= loc+")";
				}
			}
			else
			{
				pub+= " "+loc;//14/04/2008
				if(name.endsWith("."))
				{
					name=name.substring(0,name.length()-1);
				}
				//System.out.println("name "+name);
				//System.in.read();
				if(loc.length()>0)
				{				
					//	pub+=": "+name;//old[14/12/2006]
						//pub+=": "+name+".";
						pub+=": "+name+"";
				}
				else
				{
						//pub+=" "+name;//old[14/12/2006]
						//pub+=" "+name+".";	
						pub+=" "+name+"";	
				}
			}
						
		}
//------------------------------------------------------------------------------------------------------
//[2-08-2004] Gaurav & Rajeev
		else if ((xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
		{
				pub+= name;
				if(loc.length()>0)
					pub+=", "+loc;
				else
					pub+=" "+loc;			
		}
		
		//----------------------------------------------------------------------------------------------
		//Kishor [19/06/2004]
		else if (xmlObj.bibStyle.equals("5-Asw"))
		{	
			pub+= loc;
			if(loc.length()>0)
				pub+=": "+name;
			else
				pub+=" "+name;
		}
		//------------------------------------------------------------------------------------------------		
		//Kishor 
		else if (xmlObj.bibStyle.equals("5-Manage"))
		{	
			pub+= loc;
			if(loc.length()>0)
				pub+=": "+name;
			else
				pub+=" "+name;
		}
		//---------------------------------------------------------------------------------------------------		
		if (xmlObj.bibStyle.equals("old_6"))
		{	
			pub+= loc;
			if(loc.length()>0)
				pub+=": "+name;
			else
				pub+=" "+name;
		}
		//System.out.println("pub : ===> "+pub);
		//System.in.read();
		return pub;
	}
	//-----------------------------------------------------------------------------------------------------------

	private String bibEditedBook() throws IOException
	{
		String editedBook = new String();
		String eTag = new String();
		String sTag = new String();
		String editors = new String();
		String edition = new String();
		String title = new String();
		String ttitle = new String();
		String con = new String();
		String bSer = new String();
		Vector date = new Vector();
		String pages = new String();
		String pub = new String();		
		String dkj_temp1=new String();
		String dkj_temp2=new String();
		String isbn = new String();
		String BookVol=new String();//16-09-2011
		boolean isMultipleEditor =false;
		
		int ch = 0;
		
		while(!sTag.equals("</SB:EDITED-BOOK>"))
		{		
			ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				//System.out.println("==============>>"+sTag);
				if(sTag.equals("<SB:EDITORS>"))
				{			
					isEditor=true; //29-07-04
					editors = sbEditors();
					//System.out.println("editors: "+editors);
					//System.in.read();
					isMultipleEditor=isMultipleEditors;
					isMultipleEditors=false;					
				}
				else if(sTag.equals("<SB:EDITION>"))
				{isSbEdition=true;//	29/01/2009	
					edition = xmlObj.extractData("</SB:EDITION>", true);
					Move_Edition_In_Book_Review=edition;//19/05/2008
					//System.out.println("edition : "+edition);
					//System.in.read();
				}
				else if(sTag.equals("<SB:TITLE>"))
				{
					title = getBibTitle("</SB:TITLE>");

					//System.out.println("title: "+title);

					ebTitle=title;
					if(physt_brv.length()>0)
						{
							physt_brv+=". "+ebTitle;
							//System.out.println("physt_brv 4 -->"+physt_brv)	;
							//System.in.read();
						} //Gaurav For Book Review SOCSCI--11/25/04
					else
						physt_brv=ebTitle; //Gaurav For Book Review SOCSCI--11/25/04
				}
				else if(sTag.equals("<SB:TRANSLATED-TITLE>"))
				{
					ttitle = getBibTitle("</SB:TRANSLATED-TITLE>");
					ebTitle+=" ["+ttitle+"]";
					physt_brv+=ebTitle;
					//System.out.println("physt_brv--->>"+physt_brv);;
				}
				else if(sTag.equals("<SB:CONFERENCE>"))
				{
					con = xmlObj.extractData("</SB:CONFERENCE>", true);
					//System.out.println("con----222------"+con);
				}
				else if(sTag.equals("<SB:BOOK-SERIES>"))
				{	//Edited Book Series Title [BookSeries inside EditedBook]
					global.dkj_book_series="yes";
					bolBookSer=true;
					bSer = bibBookSeries();
					//System.out.println("bSer bibBookSeries()  --> "+bSer);
					//System.in.read();
				}
				else if(sTag.equals("<SB:DATE>"))
				{
					String  temp= new String();
					if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
					{
						date.add( xmlObj.extractData("</SB:DATE>", true)); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5]
					
					}
					/*else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
					{
						date.add( xmlObj.extractData("</SB:DATE>", true)); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5]
						
					}*/
					else
					{
					//date.add( xmlObj.extractData("</SB:DATE>", true));


					temp=xmlObj.extractData("</SB:DATE>", true);
					if(tempLabel.length()>0)  // 15-07-2004
					{	
						if(tempLabel.startsWith(temp))
						{
							date.add(tempLabel);
						}
						else
						{
							date.add(temp);
						
						}
						 tempLabel="";
					}
					else
					{
						date.add(temp);
					}
						//System.out.println("bSer bibBookSeries()  --> "+date);
					//System.in.read();
				 }
			}
				else if(sTag.equals("<SB:PAGES>"))
				{
					pages = bibPages();
				}
				else if(sTag.equals("<SB:PUBLISHER>"))
				{
					pub = sbPublisher();
					//System.out.println("pub : "+pub);
					//System.in.read();
				}
				
				else if(sTag.equals("<SB:ISBN>"))
				{
					isbn = xmlObj.extractData("</SB:ISBN>", true);
				}
				
				
			}
		}
		//System.out.println(xmlObj.bibStyle+"title  --> "+title);
		//System.in.read();
		int index=0;
		String tmp="";
		
		//-----------------------------------------------------------------------------
		if(xmlObj.bibStyle.equals("1")||(xmlObj.bibStyle).equals("1a")||(xmlObj.bibStyle).equals("1b"))
		{
			if (xmlObj.jid.equals("JASCER"))
			{
				if (title.length()>0)
				{
					if(global.dkj_edited_book.equals("yes"))
					{
						if(isEditor)
						{
							tmp+=" in "+title;
						}
						else
						{
							tmp+=" "+title;	
						}
					}
					else
					tmp+=" in "+title;	
				}
			}
			if(editors.length()>0)
			{
				
				if(artTitleExist== true)
				{
					if (xmlObj.jid.equals("SYAPM"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=".  En: ";
						else
						tmp+=".  In: ";
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=",  en: ";
						else
						if (xmlObj.jid.equals("JASCER"))
						tmp+=",  Ed. by ";
						else
						tmp+=",  in: ";
					}
				}
				else if(authorExist==true)
				{
					if(global.cont_avail.equals("no"))//23-07-04(Gaurav & Rajeev)
					{
						tmp+=" ";
					}
					else
					{
						if (xmlObj.jid.equals("SYAPM"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" En: ";
							else
							tmp+=" In: ";
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" en: ";
							else
							{
								if (xmlObj.jid.equals("JASCER"))
								tmp+=",  Ed. by ";
								else
								tmp+=" in: ";
							}
						}
					}
					
				}
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					if (xmlObj.jid.equals("JASCER"))
					tmp+=",";
					else
					tmp+=" (Eds.),";
					
				}
				else
				{
					if (xmlObj.jid.equals("SOCSCI"))
					{
						tmp+=" (Ed.);";
					}
					else
					{
						if (xmlObj.jid.equals("JASCER"))
						tmp+=",";
						else
						tmp+=" (Ed.),";
					}
					//tmp+=" (Ed.),";
				}
				//System.out.println(xmlObj.bibStyle+"1111111111111");
			}

			if(editors.length()==0 && title.length() > 0 && artTitleExist== true)
			{
				///System.out.println(xmlObj.bibStyle+"2222222222");
				
				if (tmp.endsWith("?"))
				{
					if (xmlObj.jid.equals("SYAPM"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=" En:";
						else
						tmp+=" In:";
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=" en:";
						else
						{
							if (xmlObj.jid.equals("JASCER"))
							tmp+=",  Ed. by ";
							else
							tmp+=" in:";
						}
					}
				}
				else
				{
					if (xmlObj.jid.equals("SYAPM"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+="L. En:";
						else
						tmp+="L. In:";
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+="L, en:";
						else
						{
							if (!xmlObj.jid.equals("JASCER"))
							tmp+="L, in:";
						}
					}
				}
			}
			
			if (!xmlObj.jid.equals("JASCER"))
			{
				if (title.length()>0)
					tmp+=" "+title;	
			}
			if (ttitle.length()>0)
				tmp+=" ("+ttitle+")";	
			
			if(con.length()>0)
			{
				tmp+=", "+con;
			}
			if(edition.length()>0 && bSer.length()==0)
			{
				tmp+=", "+edition+"";
			}

			if(bSer.length()>0)
			{				
			   tmp+=bSer;			  
			}
			if(edition.length()>0 && bSer.length()>0)
			{
				tmp+=", "+edition+"";
			}

			if(pub.length()>0)
			{
				if (xmlObj.jid.equals("SOCSCI"))//Gaurav--11/25/04...
				{
					tmp+=" "+pub;
				}
				else
				{
						tmp+=", "+pub;
				}
				
			}	
			//tempDateForSYAPM
			if(xmlObj.jid.equals("SYAPM"))
			{
				if(date.size()==2)
				{
					tempDateForSYAPM=" ("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+") ";
				}
				else
				{
					tempDateForSYAPM =" ("+date.get(0).toString()+") ";
				}
				
			}
			else
			{
				if(xmlObj.jid.equals("JASCER"))
				{
					if(date.size()==2)
					{
						tmp+=" ("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+")";
					}
					else
					{
						if(global.dkj_edited_book.equals("yes"))
						{
							if(isEditor)
							{
								tmp+=" ("+date.get(0).toString()+")";
							}
							else
							{
								tmp+=", ("+date.get(0).toString()+")";
							}
						}
						else
							tmp+=" ("+date.get(0).toString()+")";
					}
				}
				else
				{
					if(date.size()==2)
					{
						tmp+=", "+date.get(0).toString()+"{\\ndash}"+date.get(1).toString();
					}
					else
					{
						tmp+=", "+date.get(0).toString();
					}
				}
			}
			if(isbn.length()>0)  // 29-07-04
				{
						tmp+=", "+"ISBN {"+isbn+"}";
						isbn="";
				}
				//System.out.println(xmlObj.bibStyle+"1111111111111"+tmp);

		}
		else if(xmlObj.bibStyle.equals("brvhead"))
		{
			if (title.length()>0)
				tmp += title + ", ";	
			if(editors.length()>0)
			{
				if(artTitleExist== true)
				{
					tmp+=", ";
				}
				else if(authorExist==true)
				{
					if(global.cont_avail.equals("no"))
					{
						tmp+=" ";
					}
					else
					{
						tmp+=" ";
					}
					
				}
				tmp+= editors;
				if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
				{
					if(isMultipleEditor==true)
					{
						tmp+=" (Eds).";
						//System.out.println("TMPPPPP  :"+tmp);
					}
					else
					{
						tmp+=" (Ed).";
					}
				}
				else if(xmlObj.jid.equals("PHYST")) //mukesh 09-09-08
				{
					if(isMultipleEditor==true)
					{
						tmp+=" (Eds.),";//mukesh 05-09-08 added 
					}
					else
					{
						tmp+=" (Ed.),";
					}
				}
				else
				{
					if(isMultipleEditor==true)
					{
						tmp+=" (Eds.).";
					}
					else
					{
						tmp+=" (Ed.).";
					}
				}
				//System.out.println("----------------[]-------------------");
			}

			if(editors.length()==0 && title.length() > 0 && artTitleExist== true)
			{
				
				if (tmp.endsWith("?"))
				{
					tmp+=" ";
				}
				else
				{
					tmp+=", ";
				}
			}
			
			if(edition.length()>0 && bSer.length()==0)
			{
				if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
				{
					//Donot code here
				}
				else if(xmlObj.jid.equals("PHYST"))
				{
					tmp+=", ";
				}
				else
				tmp+=", "+edition+"";
			}

			if(bSer.length()>0)
			{				
			   tmp+=bSer;			  
			}
			if(edition.length()>0 && bSer.length()>0)
			{
				tmp+=", "+edition+"";
			}

			if(pub.length()>0)
			{
				tmp += " "+pub;
				//System.out.println("mike--->"+tmp);
			}			
			if(date.size()==2)
			{
				if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
				{
					tmp+=", "+date.get(0).toString()+"{\\ndash}"+date.get(1).toString();
				}
				else
				{
				tmp+=" ("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+")";
				}
			}
			else
			{
				//Added by Ravi [11/11/2006 by vivek]
				if(xmlObj.jid.equals("PHYST"))
				{
					tmp+=", "+date.get(0).toString();
				}
				else
				{
					if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						tmp+=", "+date.get(0).toString();
					}
					else
					{
						tmp+=" ("+date.get(0).toString()+")";
					}
				}
				//System.out.println("--->"+tmp);
			}
			if(isbn.length()>0)
			{
					//tmp+=": "+"ISBN "+isbn;//abhay 19.5.2006
					global.isbn1 = isbn;
					isbn="";
			}
			
			//System.out.println("\nBBBB --- "+tmp);
			//System.out.println("\nBBBB --- "+global.isbn1);
		}
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("2"))
		{
			
			if(date.size()==1)
				sTag=date.get(0).toString()+".";
			else
				sTag=date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+".";

			if(authorExist==true)
			{
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&artTitle .length()>0)
				{					
					artTitle = artTitle +".";
					tmp+=" "+sTag+" "+artTitle;
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
				}

				if(artTitleExist== true)
				{
					//Kishor [21/06/2004]
					//tmp+=" KKK-In: ";
				}
				else if(authorExist==true)
				{
					//tmp+=" In: ";
				}
				if(editors.length()>0)
				{
					if((xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE")) && title.length()>0 && tmp.endsWith("."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
						
						tmp=tmp+", ";
					}

					tmp+= editors;
					/**
					*Added By Ravi 
					*Date : [15/06/2007]
					*Change Point: In TRSTMH journal Eds or Ed will without dot.
					*Change Request point: Through TPMS by R&D Dept.

						if(isMultipleEditor==true)//old
						{
							tmp+=" (Eds.),";
						}
						else
						{
							tmp+=" (Ed.),";
						}
					
					*/
					if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
					{
						if(isMultipleEditor==true)
						{
							tmp+=" (Eds),";
							
						}
						else
						{
							tmp+=" (Ed),";
						}
					}
					else
					{
						if(isMultipleEditor==true)
						{
							tmp+=" (Eds.),";
						}
						else
						{
							tmp+=" (Ed.),";
						}
					}
					//System.out.println("tmp==> "+tmp);
					//System.in.read();
				}
			}
			else if(editors.length()>0)
			{

				tmp+=editors;
				if(isMultipleEditor==true)
				{
					tmp+=" (Eds.), ";
					//System.out.println("Tmp     :"+tmp);
					
				}
				else
				{
					tmp+=" (Ed.), ";
				}
				tmp+=sTag;
			}
			else
			{
				if(bSer.length()>0)
				{
					if(bSer.startsWith(" In: "))
						bSer=bSer.substring(" In: ".length());
					if(bSer.startsWith(" in: ") && (xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE")))
						bSer=bSer.substring(" in: ".length());
					index =bSer.indexOf("<DATE>");
					if(bSer.indexOf("<DATE>",0)!=-1)
					bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
				}
			}
			if(bSer.length()>0)
			{
				index =bSer.indexOf("<DATE>");
				if(index!=-1)
				{
					bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
				}
				
			}
			
		/*	//System.out.println("artTitle: "+artTitle);
			if (artTitle.length()>0)//abhay 14/10/2006
			{
				tmp += artTitle;
			}
		*/
	
		//System.out.println("artTitle: "+artTitle);
			if (artTitle.length()>0) //Modify by Ravi 19/10/2006
			{
					if(tmp.equals(""))
						{
							tmp += artTitle;
						}
				
			}
			if(title.length()>0)
			{
				if((editors.length()==0) && global.cont_avail.equals("yes"))//21-07-04(Rajeev & Gaurav----[in case Of Proceeding--]
				{
					if (artTitle.length()>0)
					{
						if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
						{
							if(tmp.endsWith("."))
								tmp=tmp.substring(0,tmp.length()-1)+",";
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" en: "+title+",";
							else
							tmp+=" in: "+title+",";
							if(ttitle.length()>0)
							tmp+=" ("+ttitle+")";
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" En: "+title+", ";
							else
							tmp+=" In: "+title+", ";
							if(ttitle.length()>0)
							tmp+=" ("+ttitle+"), ";

							//tmp+=", "; 
							//System.out.println("eeeeetmp: "+tmp);
						}

					}
					else
					{
						tmp+=title+" ";
						if(ttitle.length()>0)
							tmp+=" ("+ttitle+")";
						tmp+=", "; 
					}
				
				}
				else
				{
					if (title.endsWith("?")||title.endsWith(".")) // 5-08-04
					{
						tmp+=" "+title;	
						if(ttitle.length()>0)
							tmp+=" ("+ttitle+")";
					}
					else
					{
						tmp+=" "+title+" ";		
						if(ttitle.length()>0)
							tmp+=" ("+ttitle+")";
						if((xmlObj.jid.equals("SOCECO") && (Integer.parseInt(xmlObj.aid)) >= 1341)||xmlObj.jid.equals("JBECON")||xmlObj.jid.equals("JBEE"))//27-12-2013
						{
							if(global.dkj_edited_book.equals("yes"))
							{
								tmp=tmp.trim();
							}
							else
							{
								tmp+=". ";
							}
						}
						else
							tmp+=". ";
					}
				}
			}
			if(edition.length()>0 && bSer.length()==0)
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
				}
				tmp+=", "+edition;
				if(!edition.endsWith("."))
				{
					tmp+=".";
				}
			}
			if(con.length()>0)
			{
			
				if(pub.length()>0)
				{
				tmp+=" "+con+". "; //26-07-04
				}
				else
				{
				   tmp+=" "+con;
				}

			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
			}
			if(edition.length()>0 && bSer.length()>0)
			{
				
						tmp = tmp.substring(0,tmp.length()-1);
						tmp+=", "+edition;//Gaurav --11/20/04

			}
			//System.out.println("pub===>"+pub);
			if(pub.length()>0)
			{
				if (pub.endsWith("."))
				{
					pub="{"+pub+"}"; // 13-9-04 [to prevent the missing dot in case of pub ending with abbreviated word]
				}	
				//System.out.println("===>>"+tmp+"@@@@");
				if(tmp.endsWith(", "))
				tmp=tmp.trim();
				if(tmp.endsWith(","))
				{
					tmp=tmp.substring(0,tmp.length()-1);
	   			    tmp+=". "+pub;
				}
				else if(tmp.endsWith("."))
				{
					tmp+=" "+pub;
				}
				else 
				{
					tmp+=". "+pub;
				}
				
			}
			if(isbn.length()>0)  // 29-07-04
			{
					tmp+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
 		// System.out.println("TEMP===>>"+tmp);
		}
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("3-ZEFQ"))
		{
			if(artTitle.length()>0)
			{
				if (artTitle.endsWith("?"))
				{
					tmp+=" "+artTitle;				
				}
				else
				{
					tmp+=" {\\it{"+artTitle+"}}. ";				
				}
			}

			if(editors.length()>0 && authorExist==true)
			{
				//************************************
				if(title.length()>0)
				{
					
					if(xmlObj.bibStyle.equals("3-ZEFQ"))
					{
							title="in {\\it "+title+"}";
					}
					
					if (title.endsWith("?"))	//Gaurav Based on Bhavna article for Heap-1659.--11/30/04
					{
					tmp+=title+" ";
					}
					else
					{
							tmp+=title+". ";
					}
					
				}
				//*************************************
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", Editors. ";
				}
				else
				{
					tmp+=", Editor. ";
				}
			}
			else if(editors.length()>0)
			{
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", Editors. ";
				}
				else
				{
					tmp+=", Editor. ";
				}
				//************************************
				if(title.length()>0)
				{
					
					if(xmlObj.bibStyle.equals("3-ZEFQ"))
					{
							title=" {\\it "+title+"}";
					}
					
					if (title.endsWith("?"))	//Gaurav Based on Bhavna article for Heap-1659.--11/30/04
					{
					tmp+=title+" ";
					if(ttitle.length()>0)
							tmp+=" ["+ttitle+"]";
					}
					else
					{
							tmp+=title+". ";
							if(ttitle.length()>0)
							tmp+=" ["+ttitle+"]";
					}
					
				}
				//*************************************
			}

			
			
			tmp+=date.get(0).toString()+", ";
			
			if(bSer.length()>0 && tmp.length()>0)
			{
				if (tmp.endsWith("."))
				{
					tmp+=" ";
				}
				if(vnr.length()>0)
				{
					tmp+=bSer+". ";
				}
				else
				{
					tmp+=bSer+". ";
				}
				
			}
			else if(bSer.length()>0)
			{
				if(bSer.startsWith(" In: "))
				{
					bSer = bSer.substring(4);
				}				
				if(vnr.length()>0)
				{
					tmp+=bSer+". ";
				}
				else
				{
					tmp+=bSer+". ";
				}
				
			}
			if(edition.length()>0)//kkkk
			{
				if(vnr.length()>0) 
				{
					tmp = tmp.substring(0,tmp.length()-2);
					tmp+=", "+edition+ " ";				
				}
				else
				{
					tmp+=" "+edition+ " ";				
				}
			}
			


			if(pub.length()>0)
			{
				if ((xmlObj.bibStyle).equals("yijom-ns"))
				{
					tmp+=pub+" ";
				}
				else
				{
					tmp+=pub+". ";
				}
			}
			
			
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith(". "))
					tmp+=" "+"ISBN {"+isbn+"}";
				else
					tmp+=", "+"ISBN {"+isbn+"}";

				isbn="";
			}
		}
		//-----------------------------------------------------------------------------
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("3")||xmlObj.bibStyle.equals("3a") || xmlObj.bibStyle.equals("6")|| (xmlObj.bibStyle).equals("yijom-ns"))
		{
			if(artTitle.length()>0)
			{
			if (artTitle.endsWith("?"))
				{
					tmp+=" "+artTitle;				
				}
				else
				{
					String duckaid=xmlObj.aid;
					if(duckaid.indexOf(".")!=-1)
						duckaid=duckaid.substring(0,duckaid.indexOf("."));
					if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
					{
						tmp+=" "+artTitle+",";	
					}
					else
					tmp+=" "+artTitle+".";				
				}
			}
			if(editors.length()>0 && authorExist==true)
			{
				if(artTitleExist== true)
				{
					//if(xmlObj.jid.equals("NRL")&& XT.articleType.equalsIgnoreCase("ES"))
					if(XT.articleType.equalsIgnoreCase("ES"))//04-11-2010
					{
						tmp+=" En: ";
					}
					else
					{
						String duckaid=xmlObj.aid;
						if(duckaid.indexOf(".")!=-1)
							duckaid=duckaid.substring(0,duckaid.indexOf("."));
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						{
							tmp+=" in ";
						}
						else
						tmp+=" In: ";
					}
					artTitle="";
					
				}
				if(!xmlObj.jid.equalsIgnoreCase("JSAMS"))//16-04-2012
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=", eds: ";
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("IMR"))
						{
							tmp+=", editors. ";
						}
						else if ((xmlObj.bibStyle).equals("6"))
							tmp+=", eds. ";
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))//26-05-2011
								tmp+=", editores. ";
							else if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
								tmp+=", editores";
							else
							{
								if(!xmlObj.jid.equalsIgnoreCase("JSAMS"))//16-04-2012
								tmp+=", editors. ";
							}
						}
					}
				}
				else
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=", ed. ";
					}
					else
					{

						if(xmlObj.jid.equalsIgnoreCase("IMR"))
						{
							tmp+=", editor. ";
						}
						else if ((xmlObj.bibStyle).equals("6"))
							tmp+=", ed. ";
						else
						{
							if(!xmlObj.jid.equalsIgnoreCase("JSAMS"))//16-04-2012
							tmp+=", editor. ";
						}
					}

				}
			}
			else if(editors.length()>0)
			{
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=" (Eds). ";
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("IMR"))
						{
							tmp+=", editors. ";
						}
						else if(xmlObj.bibStyle.equals("6"))
						{
							tmp+=", eds. ";
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))//26-05-2011
								tmp+=", editores. ";
							else if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
								tmp+=", editores. ";
							else
							{
								if(!xmlObj.jid.equalsIgnoreCase("JSAMS"))//16-04-2012
								tmp+=", editors. ";
							}
						}
					}
				}
				else
				{
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=" (Ed.). ";
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("IMR"))
						{
							tmp+=", editor. ";
						}
						else if(xmlObj.bibStyle.equals("6"))
						{
							tmp+=", ed. ";
						}
						else
						{
							if(!xmlObj.jid.equalsIgnoreCase("JSAMS"))//16-04-2012
							tmp+=", editor. ";
						}
					}
				}
			}


			if(title.length()>0)
			{
				if(xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6"))
				{
					if(XT.Spanish_Reference_Jid.contains(xmlObj.jid)||(XT.modelStyle.startsWith("7Spanish") && !xmlObj.bibStyle.equals("6")))//16-07-2011
					{
						//System.out.println("--->>>"+XT.Spanish_Reference_Jid);
						//title=title;//blocked as space required before tilte 05-08-2011
						title=" "+title;
					}
					else
					{
						title="{\\it "+title+"}";
					}
						
				}
				if(editors.length()==0)
				{
					if(tmp.endsWith("."))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=" En: ";
						else
						{
							String duckaid=xmlObj.aid;
							if(duckaid.indexOf(".")!=-1)
								duckaid=duckaid.substring(0,duckaid.indexOf("."));
							if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
							{
								tmp+=" in ";
							}
							else
							tmp+=" In: ";
						}
					}
					else
					{
						if (tmp.endsWith("?"))
						{
							tmp+=" " ;// 25-08-04
						}
						else
						{
							if (tmp.length()>0)
							{
								String duckaid=xmlObj.aid;
								if(duckaid.indexOf(".")!=-1)
									duckaid=duckaid.substring(0,duckaid.indexOf("."));
								if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
								{
									tmp+=", ";
								}
								else
								tmp+=". ";		//[12-08-04(According to Nisha in Physt-57]--Gaurav
							}
						}
					}
				}
				if (title.endsWith("?"))	//Gaurav Based on Bhavna article for Heap-1659.--11/30/04
				{
					tmp+=title+" ";
					if(ttitle.length()>0)
						tmp+="["+ttitle+"]";

				}
				else
				{
						String duckaid=xmlObj.aid;
						if(duckaid.indexOf(".")!=-1)
							duckaid=duckaid.substring(0,duckaid.indexOf("."));
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						{
							tmp+=title;
						}
						else if(xmlObj.jid.equalsIgnoreCase("OSI"))
						{
							tmp+="{\\it{"+title+"}}";
						}
						else
						tmp+=title+" ";
						if(ttitle.length()>0)
							tmp+=" ["+ttitle+"]";
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						{
							tmp+=", "; 
						}
						else
						tmp+=". "; 
				}
				
			}
			if ((xmlObj.bibStyle).equals("yijom-ns"))
			{
				if(con.length()>0)
				{
					if (tmp.endsWith(". "))
					{
						tmp=tmp.substring(0,tmp.length()-2);
						tmp+=", ";
					}
					tmp+=con+". ";
				}
			}
			if(bSer.length()>0 && tmp.length()>0)
			{
				if (tmp.endsWith("."))
				{
					tmp+=" ";
				}
				if(vnr.length()>0)
				{
					if((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=bSer+": ";
					}
					else
					{
						tmp+=bSer+". ";
					}
				}
				else
				{
					tmp+=bSer+". ";
				}
				
			}
			else if(bSer.length()>0)
			{
				if(bSer.startsWith(" In: "))
				{
					bSer = bSer.substring(4);
				}				
				if(vnr.length()>0)
				{
					if((xmlObj.bibStyle).equals("yijom-ns"))
					{
						tmp+=bSer+": ";
					}
					else
					{
						tmp+=bSer+". ";
					}
				}
				else
				{
					tmp+=bSer+". ";
				}
				
			}
			if(edition.length()>0)//kkkk
			{
				if(xmlObj.jid.equals("HEAP"))
				{
					if(vnr.length()>0) 
					{
						tmp = tmp.substring(0,tmp.length()-2);
						tmp+=", "+edition+ " ";				
					}
					else
					{
						tmp=tmp.trim();
						if(tmp.endsWith("."))
						{
							tmp = tmp.substring(0,tmp.length()-1);
						}
						tmp=tmp.trim();
						tmp+=", "+edition+ " ";	
						//System.out.println("tmp------>>"+tmp);
					}

				}
				else
				{
					if(vnr.length()>0) 
					{
						tmp = tmp.substring(0,tmp.length()-2);
						
						String duckaid=xmlObj.aid;
						if(duckaid.indexOf(".")!=-1)
							duckaid=duckaid.substring(0,duckaid.indexOf("."));
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						{
							tmp+=", "+edition+ ", ";
						}
						else
						tmp+=", "+edition+ " ";				
					}
					else
					{
						String duckaid=xmlObj.aid;
						if(duckaid.indexOf(".")!=-1)
							duckaid=duckaid.substring(0,duckaid.indexOf("."));
						if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
						{
							tmp+=" "+edition+ ", ";
						}
						else
						tmp+=" "+edition+ " ";				
					}
				}
			}
			if(editors.length()>0)
			{
				String duckaid=xmlObj.aid;
				if(duckaid.indexOf(".")!=-1)
					duckaid=duckaid.substring(0,duckaid.indexOf("."));
				if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
				{
					tmp+= editors;
					if(isMultipleEditor==true)
					{
						tmp+=", editors, ";
					}
					else
						tmp+=", editor, ";
				}
			}
			/*if(edition.length()>0 && bSer.length()==0)//kkkk
			{				
					tmp+=" "+edition+ " ";				
			}
			if(edition.length()>0 && bSer.length()>0)
			{				
				if(edition.startsWith(". "))
				{
                     edition = edition.substring(0,edition.length()-2);
					tmp+=",11111111 "+edition+ " ";
				}
                else
				{
					tmp+="33333333 "+edition+ " ";
				}
			}*/


			if(pub.length()>0)
			{
				if ((xmlObj.bibStyle).equals("yijom-ns"))
				{
					tmp+=pub+" ";
				}
				else
				{
					String duckaid=xmlObj.aid;
					if(duckaid.indexOf(".")!=-1)
						duckaid=duckaid.substring(0,duckaid.indexOf("."));
					if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
					{
						tmp+=pub+", ";
					}
					else
					tmp+=pub+"; ";
				}
			}
				
			/*else  //rajeev-28-2-05// when no publisher Proceeding title should end with .
			{
				if(tmp.endsWith(". "))
				{
					tmp = tmp.substring(0,tmp.length()-2)+ ", ";//24-07-04(Gaurav-When No Publisher)
				}
			}*/
			if ((xmlObj.bibStyle).equals("yijom-ns"))
			{
				tmp+=date.get(0).toString();
			}
			else
			{
				if ((xmlObj.bibStyle).equals("6"))
					tmp+=date.get(0).toString();
				else
				{
					String duckaid=xmlObj.aid;
					if(duckaid.indexOf(".")!=-1)
						duckaid=duckaid.substring(0,duckaid.indexOf("."));
					if(xmlObj.jid.equalsIgnoreCase("JSAMS")&&Integer.parseInt(duckaid)>global_cutOff_aid_for_JSAMS)//16-04-2012
					{
						tmp+=date.get(0).toString()+",";
					}
					else
					tmp+=date.get(0).toString()+".";
				}
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith(". "))
					tmp+=" "+"ISBN {"+isbn+"}";
				else
					tmp+=", "+"ISBN {"+isbn+"}";

				isbn="";
			}
			//System.out.println("tmp: "+tmp);
		}
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("3a-Jecs"))
		{
			if(artTitle.length()>0) // 31-08-04
			{
				tmp+=" "+artTitle+".";    
			}
			//-------------------------------------------------------			
			if(editors.length()>0 && authorExist==true)
			{
				if(artTitleExist== true)
				{
					tmp+=" In ";
					artTitle="";
				}
				else
				{
					tmp+=" In ";
				}
				//
			}
			else if(editors.length()>0)
			{				
				tmp+= editors;
				tmp+=", ed., ";//Gaurav -14-08-04-----Based On Ruchika FeedBack
			}
			//-------------------------------------------------------
			if(title.length()>0)
			{
				if(ttitle.length()>0)
					title="{\\it "+title+" ["+ttitle+"]}";				
				else
					title="{\\it "+title+"}";				
					

				if(editors.length()==0&& artTitle.length()==0)
				{
					if(tmp.endsWith("."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
					tmp+=" In ";//Gaurav --29-08-04
				}
				else if(editors.length()==0 && artTitle.length()>0)
				{
					tmp+=" In ";//Gaurav --29-08-04
				}
				tmp+=title+". ";
			}
			
			//-------------------------------------------------------
			if (editors.length()>0 && authorExist==true)
			{
				if (tmp.endsWith(". "))
				{
					tmp=tmp.substring(0,tmp.length()-2);
					tmp=tmp+", ";
				}
				tmp+="ed. ";
				tmp+= editors;
				tmp+=". ";
				
			}
			
			//-------------------------------------------------------
     		if(bSer.length()>0 && tmp.length()>0)
			{
				tmp+=bSer+". ";
			}
			else if(bSer.length()>0)
			{
				if(bSer.startsWith(" In "))
				{
					bSer = bSer.substring(3);
				}
				tmp+=bSer+". ";
			}
			if(tmp.endsWith(".}}. ")|| tmp.endsWith(". . "))
			{
				tmp=tmp.substring(0,tmp.length()-2)+" ";
			}
			if(edition.length()>0)
			{
				tmp+=" "+edition+ " ";
		  }

			if(pub.length()>0)
			{
				tmp+=pub+", ";
			}
			if(tmp.endsWith(". "))
			{
				tmp = tmp.substring(0,tmp.length()-2)+ ", ";
			}

			tmp+=date.get(0).toString();
			if(isbn.length()>0)  // 29-07-04
			{
					tmp+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
				
			tmp+=".";
			
		}
		//-----------------------------------------------------------------------------
		else if((xmlObj.bibStyle).equals("chea"))
		{
			if(artTitle.length()>0) // 31-08-04
			{
				tmp+=" "+artTitle+",";    
			}
			//-------------------------------------------------------			
			if(editors.length()>0 && authorExist==true)
			{
				if(artTitleExist== true)
				{
					tmp+=" in ";
					artTitle="";
				}
				else
				{
					tmp+=" In ";
				}
				//
			}
			else if(editors.length()>0)
			{				
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", Eds. ";
				}
				else
				{
					tmp+=", Ed. ";
				}
			}
			//-------------------------------------------------------
			if(title.length()>0)
			{
				title="{\\it "+title+"}";				
				if(editors.length()==0&& artTitle.length()==0)
				{
					if(tmp.endsWith("."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
					tmp+=" In ";//gaurav --29-08-04
				}
				else if(editors.length()==0 && artTitle.length()>0)
				{
					if(tmp.endsWith(".")||tmp.endsWith(","))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
					tmp+=", in ";					
				}
				tmp+=title+"; ";
			}
			
			//-------------------------------------------------------
			if (editors.length()>0 && authorExist==true)
			{
				if (tmp.endsWith(". "))
				{
					tmp=tmp.substring(0,tmp.length()-2);
					tmp=tmp+"; ";
				}
				
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", Eds.; ";
				}
				else
				{
					tmp+=", Ed.; ";
				}
				}
			
			//-------------------------------------------------------
     		if(bSer.length()>0 && tmp.length()>0)
			{
				tmp+=bSer+". ";
			}
			else if(bSer.length()>0)
			{
				if(bSer.startsWith(" In "))
				{
					bSer = bSer.substring(3);
				}
				tmp+=bSer+". ";
			}
			if(tmp.endsWith(".}}. ")|| tmp.endsWith(". . "))
			{
				tmp=tmp.substring(0,tmp.length()-2)+" ";
			}
			if(edition.length()>0)
			{
				tmp+=" "+edition+ "; ";
			}

			if(pub.length()>0)
			{
				if (tmp.endsWith(".")||tmp.endsWith(","))
				{
					tmp=tmp.substring(0,tmp.length()-1);
				}
				if (tmp.endsWith(". ")||tmp.endsWith(", "))
				{
					tmp=tmp.substring(0,tmp.length()-2);
				}
				if (tmp.endsWith(";")||tmp.endsWith("; "))
				{
					tmp+=" "+pub+", ";
				}
				else
				{
					tmp+="; "+pub+", ";
				}
				
			}
			if(tmp.endsWith("; "))
			{
				tmp = tmp.substring(0,tmp.length()-2)+ ", ";
			}

			tmp+=date.get(0).toString();
			if(isbn.length()>0)  // 29-07-04
			{
					tmp+=", "+"ISBN {"+isbn+"}";
					isbn="";
			}
				
			tmp+=".";
			
		}
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("4"))
		{
			
			//System.out.println("tmp "+tmp);
			if(artTitle.length()>0)
			{
				tmp+=" "+artTitle+".";
			}
			if(editors.length()>0 && authorExist==true)
			{
				if(artTitleExist== true)
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tmp+=" In ";
						artTitle="";

					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=" En: ";
						else
						tmp+=" In: ";
						artTitle="";
					}
				}
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tmp+=" (eds) ";
					}
					else
					tmp+=", editors. ";
				}
				else
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tmp+=" (ed) ";
					}
					else
					tmp+=", editor. ";
				}
			}
			else if(editors.length()>0)
			{
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tmp+=" (eds) ";
					}
					else
					tmp+=", editors. ";
				}
				else
				{
					if(xmlObj.jid.equals("PROTIS"))//05-07-2010
					{
						tmp+=" (ed) ";
					}
					else
					tmp+=", editor. ";
				}
			}

			if(title.length()>0)
			{
				title=" "+title;
				//System.out.println("tmp "+tmp);
				if(editors.length()==0)
				{
					if(tmp.endsWith("."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
					
					if(artTitle.length()>0)//Gaurav Based on Bhardawaj Ji Request For  recycl 1715--27-10-04
					{
						if(xmlObj.jid.equals("PROTIS"))//05-07-2010
						{
							tmp+=". In ";
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=". En: ";
							else
							tmp+=". In: ";
						}
					}
					else
					{
						if(xmlObj.jid.equals("PROTIS"))//05-07-2010
						{
							tmp+=" In ";
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" En: ";
							else
							tmp+=" In: ";
						}

					}
				}
				
				tmp+=title+" ";
				if(ttitle.length()>0)
					tmp+=" ["+ttitle+"]";
				tmp+=". "; 

			}
			//System.out.println("tmp "+tmp);
			if(con.length()>0)
			{
				//tmp+=con+". ";
			}
			if(bSer.length()>0 && tmp.length()>0)
			{
				tmp+=bSer+". ";
			}
			else if(bSer.length()>0)
			{
				if(bSer.startsWith(" In: "))
				{
					bSer = bSer.substring(4);
				}
				tmp+=bSer+". ";
			}
			if(edition.length()>0 && bSer.length()>0)//Gaurav--11/20/04
			{
				tmp = tmp.substring(0,tmp.length()-2);
				tmp+=", "+edition+ " ";
			}
			if(edition.length()>0 && bSer.length()==0)
			{
				tmp+=" "+edition+ " ";
			}
			if(pub.length()>0)
			{
				if(xmlObj.jid.equals("PROTIS"))//05-07-2010
				{
					tmp+=pub+", ";
				}
				else
				tmp+=pub+"; ";
				//System.out.println("tmp===>>"+tmp);
			}
			if(tmp.endsWith(". "))
			{
				if(xmlObj.jid.equals("PROTIS"))//07-07-2011
					tmp = tmp.substring(0,tmp.length()-2)+ ": ";
				else
					tmp = tmp.substring(0,tmp.length()-2)+ "; ";
			}
			//
			if (date.size()==1)
			{
				if(xmlObj.jid.equals("PROTIS"))
				{
					tempDateForSYAPM="("+date.get(0).toString()+")";
				}
				else
				{
					tmp+=date.get(0).toString()+".";
				}
				//System.out.println("tempDateForSYAPM "+tempDateForSYAPM);
			}
			else
			{
				dkj_temp1=date.get(0).toString();
				dkj_temp2=date.get(1).toString();		
				if (dkj_temp1.length() >0 && dkj_temp2.length() >0)
				{
					if ((dkj_temp1.length()  == dkj_temp2.length())  &&
						(dkj_temp1.charAt(0) == dkj_temp2.charAt(0)) &&
						(dkj_temp1.charAt(1) == dkj_temp2.charAt(1))
					   )
				    {
					    dkj_temp2=dkj_temp2.substring(2,dkj_temp2.length());
				    }
				}

				tmp+=dkj_temp1+"{\\ndash}"+dkj_temp2+".";
				//System.out.println("tmp "+tmp);
			}
			//
			
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
		}

		else if(xmlObj.jid.equals("RETAIL"))///////////// RETAIL journal style ////////  
		{
			
			if(date.size()==1)
				{
						//sTag="("+date.get(0).toString()+"),";//14/04/2008
						sTag="("+date.get(0).toString()+"),";
						
				}
			else			
			{
				//sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+"),";	//14/04/2008	
				sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+"),";		
			}
		
				
		
			//System.out.println("sTag : "+sTag);
			if(authorExist==true)
			{
				/*if(sTag.endsWith(")."))
				{
					sTag.replaceFirst("\\).","\\),");
				}*/
				if(!artTitle.endsWith(".") && !artTitle.endsWith("!") && !artTitle.endsWith("?") && artTitle .length()>0)
				{
					artTitle = "``"+artTitle +",''";
					tmp+=" "+sTag+" "+artTitle;
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
					
				}
				
				if(title.length()>0)
				{
				/*	if (global.cont_avail.equals("yes"))
					{	
							tmp+=" in {\\it "+title+"}, ";
					}
				*/
					//Added by Ravi 03/11/2006 [ by usmani]
					if (global.cont_avail.equals("yes"))
					{	//System.out.println("tempone : "+tempone);
							if(xmlObj.jid.equals("RETAIL"))
							{
								if(tempone.length()>0)
								{
									tmp+=" in {\\it "+title+"} "+tempone+", ";
									tempone="";
								}
								else
								{
									tmp+=" in {\\it "+title+"}"+tempone+", ";
									tempone="";
								}
							}
							else
							{
								tmp+=" in {\\it "+title+"}, ";
								
							}
					}
					else
					{						
						tmp+=" in {\\it "+title+"}, ";
						//System.out.println("title : "+title);
						if (tmp.endsWith("?}, "))
						{
							tmp=tmp.substring(0,tmp.length()-1);
						}
					}
				}

				if(editors.length()>0)
				{	
					//System.out.println("1c editors : "+editors);
					if(editors.length()>0)
					{
						if(editors.endsWith(","))
						{
							editors=editors.substring(0,editors.length()-1);
						}
					}
					if (artTitle.length()>0)
					{					
							tmp+= editors;
					}
					else
					{
							tmp+= editors;
					}

					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
								tmp+=", eds.";								
						}
						else
						{
								tmp+=", eds.";								
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{
								tmp+=" ed.";
								
						}
						else
						{
								tmp+=" ed.";
								
						}
					}
					
				}
				
			}
			
			else if(editors.length()>0)
			{			
					tmp+=editors;
					if(isMultipleEditor==true)
					{
							tmp+=" eds. ";
					}
					else 
					{
						tmp+=" ed. ";

					}
					tmp+=sTag;
					if(artTitle.length()>0)
					{
						tmp+=" ``"+artTitle+",'' in ";
					}
			}
			else
			{				
				if(bSer.startsWith(" In "))
					bSer=bSer.substring(" In ".length());
				if(bSer.startsWith(" in "))
					bSer=bSer.substring(" in ".length());
				if(bSer.startsWith(" In: "))
					bSer=bSer.substring(" In: ".length());
				index =bSer.indexOf("<DATE>");

				bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
			}
			
			index =bSer.indexOf("<DATE>");
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
				//System.out.println("bSer : "+bSer);
			}			
			
			if(edition.length()>0)
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				tmp+="("+edition+"). ";
				if(!edition.endsWith("."))
				{
					tmp+=".";
				}
			}     

			if(pub.length()>0)			
			{
				tmp+=" "+pub;
			}
			if(isbn.length()>0)
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
			//System.out.println("tmp : "+tmp);
		}////////////////////// end of RETAIL journal style

		//---------------------------------------------------------------------------------
		//Kishor [19/06/2004]
		else if(xmlObj.bibStyle.equals("5")||(xmlObj.bibStyle.equals("IChemE")))
		{
			
		
			//System.out.println("artTitle: "+artTitle);System.in.read();
			//Kishor [21/06/2004]
			if(date.size()==1)							//Date without range
				{
					if(xmlObj.bibStyle.equals("IChemE"))
					{
						sTag=""+date.get(0).toString()+",";
					}
					else
					{
						sTag="("+date.get(0).toString()+").";
					}
					//sTag="("+date.get(0).toString()+").";//01/12/2007
					//System.out.println("date.get(0) "+date.get(0));
					//System.in.read();
				}
			else										//Date with range
			{
					if(xmlObj.bibStyle.equals("IChemE"))
					{
						sTag=""+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+",";
					}
					else
					{
						sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+").";
					}
					//sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+").";//01/12/2007
			}
			
			if(authorExist==true)
			{
				if(!artTitle.endsWith(".") && !artTitle.endsWith("!") && !artTitle.endsWith("?") && artTitle .length()>0)
				{
					if(xmlObj.bibStyle.equals("IChemE"))
					{
						artTitle = artTitle +",";
					}
					else
					{
						artTitle = artTitle +".";
					}
					//artTitle = artTitle +".";//01/12/2007
					tmp+=" "+sTag+" "+artTitle;
					//System.out.println(" artTitle 4 -- > "+artTitle);
					
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
					
				}
				
				//System.out.println(" title 4 -- > "+tmp);
				//System.in.read();
				if(editors.length()>0)
				{	
					if (artTitle.length()>0)
					{	if(xmlObj.bibStyle.equals("IChemE"))
						{
							if (title.length()>0)
							{
								tmp+=" in "+title+", ";
								if(editors.startsWith(" In"))
								{
									editors=editors.substring(3,editors.length());
								}
								tmp+= editors;
								
							}
						}
						else
						{
							if(xmlObj.jid.equals("INFBEH"))//16-09-2011
							{
								if(bSer.length()>0)
									{
										//System.out.println("111111111bsre --> "+bSer);
										//System.out.println("===============>>"+editors);
										index =bSer.indexOf("<DATE>");
										//System.out.println("bsre --> "+bSer);
										String temp_bSer = "";
										if(index!=-1)
										temp_bSer = bSer.substring(0,index);
										if(temp_bSer.endsWith(", "))
										{
											temp_bSer=temp_bSer.substring(0,temp_bSer.length()-2);
										}
										
										/*if(editors.startsWith(" In "))
										{
											//System.out.println("===============>>"+editors);
											editors=editors.substring(3,editors.length());
										}*/
										//System.out.println("===============>>"+editors);
										tmp+= temp_bSer+" "+editors;
										if(index!=-1)
										bSer = bSer.substring(index,bSer.length());
										
			
									}
									else
										tmp+= editors;
							}
							else
							tmp+= editors;
							
						}
							//tmp+= editors;//01/12/2007
							//System.out.println("editors: "+editors);System.in.read();
					}
					else
					{
						/*if (xmlObj.jid.equals("MATBEH") && Integer.parseInt(xmlObj.aid)<=112)
							tmp+= "In: "+editors; // 25-08-04 [Rajeev- When authors  & editors with EBT present & CT not present then In before editors...]
						else*/
						if (xmlObj.jid.equals("ANXDIS"))
							tmp+= "In: "+editors; // 25-08-04 [Rajeev- When authors  & editors with EBT present & CT not present then In before editors...]
						else// 25-08-04 [Rajeev- When authors  & editors with EBT present & CT not present then In before editors...]
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+= "in "+editors; 
							}
							else
							{
								tmp+= "In "+editors; 
							}
							//tmp+= "In "+editors; //01/12/2007
						}

					}
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))//Gaurav Based on New Book requirments--12	/29/04
							if(xmlObj.deviation.equals("BOOKS"))
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									tmp+=" (eds)";
								}
								else
								{
									if(xmlObj.jid.equals("INFBEH")&& bSer.length()>0)//16-09-2011
									{
										if(isinfbeh_vol)
										{

											tmp+=" (Vol. Eds),";
											isinfbeh_vol=false;
										}
										else
											tmp+=" (Eds),";
									}
									
									else
									tmp+=" (Eds),";
								}	
								//tmp+=" (Eds),";//01/12/2007
								//System.out.println("1. tmp : "+tmp);
							}
							else
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									tmp+=" (eds)";
								}
								else
								{
									if(xmlObj.jid.equals("INFBEH"))//16-09-2011
									{
										if(isinfbeh_vol && bSer.length()>0)
										{
											//System.out.println("111111111bsre --> "+bSer);
											tmp+=" (Vol. Eds.),";
											isinfbeh_vol=false;
										}
										else
										tmp+=" (Eds.),";
									}
									else
									tmp+=" (Eds.),";
								}
								//tmp+=" (Eds.),";//01/12/2007
								//System.out.println("2. tmp : "+tmp);
							}
						}
						else
						{
							//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))//Gaurav Based on New Book requirments--12	/29/04
							if(xmlObj.deviation.equals("BOOKS"))
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									tmp+=" (eds)";
								}
								else
								{
									if(xmlObj.jid.equals("INFBEH"))//16-09-2011
									{
										if(isinfbeh_vol)
										{
											tmp+=" (Vol. Eds).";
											isinfbeh_vol=false;
										}
										else
										tmp+=" (Eds).";
									}
									else
									tmp+=" (Eds).";
								}
								//tmp+=" (Eds).";//01/12/2007
							}
							else
							{
								if(xmlObj.bibStyle.equals("IChemE"))
								{
									tmp+=" (eds)";
								}
								else
								{
									if(xmlObj.jid.equals("INFBEH"))//16-09-2011
									{
										if(isinfbeh_vol)
										{
											tmp+=" (Vol. Eds.).";
											isinfbeh_vol=false;
										}
										else
										tmp+=" (Eds.).";
									}
									else
									tmp+=" (Eds.).";
								}
								//tmp+=" (Eds.).";//01/12/2007
							}
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (ed)";
							}
							else
							{
								tmp+=" (Ed.),";
							}
							//tmp+=" (Ed.),";//01/12/2007
						}
						else
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (ed)";
							}
							else
							{
								tmp+=" (Ed.).";
							}
							//tmp+=" (Ed.).";//01/12/2007
						}
					}
					
				}
				
			}
			else if(editors.length()>0)
			{			
				   
					tmp+=editors;
				
					if(isMultipleEditor==true)
					{
						//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))//Gaurav Based on New Book requirments--12	/29/04
						if(xmlObj.deviation.equals("BOOKS"))
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (eds) ";
							}
							else
							{
								tmp+=" (Eds) ";
							}
							//tmp+=" (Eds) ";//01/12/2007
						}
						else
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (eds) ";
							}
							else
							{
								tmp+=" (Eds.). ";
							}
							//tmp+=" (Eds.). ";//01/12/2007
						}
					}
					else 
					{
						//if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))//Gaurav Based on New Book requirments--12	/29/04
						if(xmlObj.deviation.equals("BOOKS"))
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (ed) ";
							}
							else
							{
								tmp+=" (Ed.) ";
							}
							//tmp+=" (Ed.) ";//01/12/2007
						}
						else
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" (ed) ";						
							}
							else
							{
								tmp+=" (Ed.). ";						
							}
							//tmp+=" (Ed.). ";//01/12/2007						
						}

					}
					tmp+=sTag;
					if(artTitle.length()>0)
					{
						/*if (xmlObj.jid.equals("MATBEH") && Integer.parseInt(xmlObj.aid)<=112)
							tmp+=" "+artTitle+". In: "; //14-08-04--Gaurav(When only title is given in Contribution--Cace-2901)
						else*/
						if (xmlObj.jid.equals("ANXDIS"))
							tmp+=" "+artTitle+". In: "; //14-08-04--Gaurav(When only title is given in Contribution--Cace-2901)
						else//14-08-04--Gaurav(When only title is given in Contribution--Cace-2901)
						{
							if(xmlObj.bibStyle.equals("IChemE"))
							{
								tmp+=" "+artTitle+", in "; 
							}
							else
							{
								tmp+=" "+artTitle+". In "; 
							}
							//tmp+=" "+artTitle+". In "; //01/12/207
						}

					}
			}
			else
			{	
				if(bSer.length()>0)
				{
					if(bSer.startsWith(" In "))
						bSer=bSer.substring(" In ".length());
					if(bSer.startsWith(" in "))
						bSer=bSer.substring(" in ".length());
					if(bSer.startsWith(" In: "))
						bSer=bSer.substring(" In: ".length());
					index =bSer.indexOf("<DATE>");
					//System.out.println("bsre --> "+bSer);
					bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
				}
			}
			
			index =bSer.indexOf("<DATE>");
		
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
			}			
			if(title.length()>0)
			{
				if (global.cont_avail.equals("yes"))
				{	
					if (artTitleExist==true && editors.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						/*if (xmlObj.jid.equals("MATBEH") && Integer.parseInt(xmlObj.aid)<=112)
							tmp+=" In: {\\it "+title+"} ";	
						else*/
						///	if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))
						if(xmlObj.deviation.equals("BOOKS"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								tmp+=" En: {\\it "+title+"} ";	//wwwwwww
							else
							{
								if(xmlObj.jid.equals("ZOOGA"))//31-08-2011
								tmp+=" In: "+title+" ";	
								else
								tmp+=" In: {\\it "+title+"} ";	
							}
								
						}
						else
						{
							if(xmlObj.jid.equals("ZOOGA"))//31-08-2011
							tmp+=" In "+title+" ";	
							else
							tmp+=" In {\\it "+title+"} ";	//wwwwwww
							if(ttitle.length()>0)
							tmp+="("+ttitle+")";
						}
							
					}	
					else
					{
						if(xmlObj.bibStyle.equals("IChemE"))
						{
							//tmp+=" "+title+" ";
						}
						else
						{
							if(xmlObj.jid.equals("ZOOGA"))//31-08-2011
							tmp+=" "+title+" ";
							else
							tmp+=" {\\it "+title+"} ";
							if(ttitle.length()>0)
							tmp+="("+ttitle+")";
						}
						//tmp+=" {\\it "+title+"} ";//01/12/2007
						//System.out.println("tmp --> "+tmp);
					}
				}
				else
				{	
					if(xmlObj.jid.equals("ZOOGA"))//31-08-2011
					tmp+=" "+title+" ";
					else
					tmp+=" {\\it "+title+"} ";
					if(ttitle.length()>0)
							tmp+="("+ttitle+")";
					tmp+=". "; 
					//System.out.println("2 tmp --> "+tmp);
					if (tmp.endsWith("?}."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
				}
			}
			if(con.length()>0)//was commented earlier and removed by mukesh on 05-11-08
			{
//				tmp+=" "+con+", ";//<sb:conference> commented on 16-12-2015, mail by Usmani (Gangtok) 12-12-2015 for ref. style 5(APA) 
			}
			
			if(edition.length()>0) // 23-07-04 [Gaurav & Rajeev - In case of edition]
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				//	tmp+="("+edition+"). ";//old
				/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
					*/
				edtion1=edition;
				/*
					if(!edition.endsWith("."))
					{
						tmp+=".";
					}
				*/
			}     

			if(pub.length()>0)			
			{
					global.pub_for_ref5=pub;//Bcz in Ref 5 it should come after page.
					//System.out.println("global.pub_for_ref5 "+global.pub_for_ref5);
					//System.in.read();
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
			//System.out.println("tmp "+tmp);
			//System.in.read();
		}
		//---------------------------------------------------------------------------------
		//Kishor [19/06/2004]
		else if(xmlObj.bibStyle.equals("DDT-NS"))
		{
			//Kishor [21/06/2004]
			if(date.size()==1)							//Date without range
				sTag="("+date.get(0).toString()+")";
			else										//Date with range
				sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+")";
			//
			if(authorExist==true)
			{
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&artTitle .length()>0)
				{
					artTitle = artTitle +".";
					tmp+=" "+sTag+" "+artTitle;
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
				}
			if(title.length()>0)
			{
				if (global.cont_avail.equals("yes"))
				{	
					if (artTitleExist==true && editors.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						tmp+=" {\\it "+title+"} ";	//(06-08-04[Gaurav & Rajeev)
					}	
					else
					{ 
						if(artTitleExist==false)
						tmp+=" {\\it "+title+"} ";//Gaurav Based On New Requierment for DDMEC-51--11/30/04
						else
							tmp+=" In {\\it "+title+"} ";

					}
				}
				else
				{	
					tmp+=" {\\it "+title+"}";
				}
			}
						
		}
		else if(editors.length()>0)
		{			
					tmp+=editors;
					if(isMultipleEditor==true)
					{
						tmp+=", eds ";
					}
					else
					{
						tmp+=", ed. ";
					}
					tmp+=sTag;
					tmp+=" {\\it "+title+"}";
		}			
						
		else
		{				
				if(bSer.startsWith(" In "))
					bSer=bSer.substring(" In ".length());
				index =bSer.indexOf("<DATE>");

				bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
		}
			
			index =bSer.indexOf("<DATE>");
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
									
			if(edition.length()>0) // 23-07-04 [Gaurav & Rajeev - In case of edition]
			{
				if(bsTitle.length()>0)
				{
				tmp+=" "+bsTitle;
				}
				if(con.length()>0)
				{
					if (tmp.endsWith(" "))
					{
						tmp=tmp.substring(0,tmp.length()-1);
						tmp+=", "+con+", ";
					}
					else
					{
						tmp+=" "+con+", ";
					}
				}
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				if (bsVol.length()>0)
				{
					tmp+=" ("+bsVol+", "+edition+") ";
				}
				else
				{
				tmp+=" ("+edition+") ";
				}
			} 
			else
			{
				if(bSer.length()>0)
				{
					tmp+=" "+bSer;
				}
				if(con.length()>0)
				{
					if (tmp.endsWith(" "))
					{
						tmp=tmp.substring(0,tmp.length()-1);
						tmp+=", "+con+", ";
					}
					else
					{
						tmp+=" "+con+", ";
					}
				}

			}

			if(editors.length()>0 && authorExist==true) // 4-08-04
				{	
					if(edition.length()>0 && artTitle.length()==0)
					{
						tmp+=" (" +editors;//Gaurav based on DDTMEC 51 Based on Parul Feedback--11/27/04.
					}
					else
					{
						tmp+= editors;
					}
					
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							tmp+=", eds),";
						}
						else
						{
							tmp+=", eds).";
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{	tmp+=", ed.),";
						}
						else
						{	tmp+=", ed.).";
						}
						}
				}

			if(pub.length()>0)
			{	//tmp+=" "+pub;
				global.pub_for_ref5=pub;//Bcz in Ref 5 it should come after page.
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
		}
		
		//---------------------------------------------------------------------------------
		//Kishor [19/06/2004]
		else if(xmlObj.bibStyle.equals("5-BookB"))
		{
			//Kishor [21/06/2004]
			if(date.size()==1)							//Date without range
				sTag="("+date.get(0).toString()+").";
			else										//Date with range
				sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+").";
			//
			if(authorExist==true)
			{
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&artTitle .length()>0)
				{
					artTitle = artTitle +".";
					tmp+=" "+sTag+" "+artTitle;
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
				}
			if(title.length()>0)
			{
				ebTitle=title;
				if (global.cont_avail.equals("yes"))
				{	
					if (artTitleExist==true && editors.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						tmp+=" In {\\it "+title+"} ";	
					}	
					else
					{
						tmp+=" In {\\it "+title+"} ";
					}
				}
				else
				{	
					tmp+=" {\\it "+title+"}.";
				}
			}
			
				if(editors.length()>0 )
				{			
					tmp+= editors;
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							tmp+=", eds.),";
						}
						else
						{
							tmp+=", eds.).";
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{	tmp+=", ed.),";
						}
						else
						{	tmp+=", ed.).";
						}
						}
					}					
				}
				else if(editors.length()>0)
				{			
					tmp+=editors;
					if(isMultipleEditor==true)
					{
						tmp+=", eds. ";
					}
					else
					{
						tmp+=", ed. ";
					}
					tmp+=sTag;
					tmp+=" {\\it "+title+"}.";
				}			
						
			else
			{				
				if(bSer.startsWith(" In "))
					bSer=bSer.substring(" In ".length());
				index =bSer.indexOf("<DATE>");

				bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
			}
			
			index =bSer.indexOf("<DATE>");
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
			}
			
			if(con.length()>0)
			{
				tmp+=" "+con+", ";
			}
			
			if(edition.length()>0) // 23-07-04 [Gaurav & Rajeev - In case of edition]
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				tmp+=" "+edition+", ";
				if(!edition.endsWith("."))
				{
					tmp+=",";
				}
			}     

			if(pub.length()>0)
			{	//tmp+=" "+pub;
				global.pub_for_ref5=pub;//Bcz in Ref 5 it should come after page.
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
		}
		
		//---------------------------------------------------------------------------------
		
		//Kishor [19/06/2004]
		else if(xmlObj.bibStyle.equals("5-Asw"))
		{
			//Kishor [21/06/2004]
			if(date.size()==1)							//Date without range
				sTag="("+date.get(0).toString()+").";
			else										//Date with range
				sTag="("+date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+").";
			//
			if(authorExist==true)
			{
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&artTitle .length()>0)
				{
					artTitle = artTitle +".";
					tmp+=" "+sTag+" "+artTitle;
				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
				}
				
				if(editors.length()>0)
				{
					tmp+= editors;
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							tmp+=" (Eds.),";
						}
						else
						{
							tmp+=" (Eds.).";
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{	tmp+=" (Ed.),";
						}
						else
						{	tmp+=" (Ed.).";
						}
					}
				}
			}
			else if(editors.length()>0)
			{
				tmp+=editors;
				if(isMultipleEditor==true)
				{
					tmp+=" (Eds.). ";
				}
				else
				{
					tmp+=" (Ed.). ";
				}
				tmp+=sTag;
			}
			else
			{
				if(bSer.startsWith(" In "))
					bSer=bSer.substring(" In ".length());
				index =bSer.indexOf("<DATE>");

				bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
			}
			
			index =bSer.indexOf("<DATE>");
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
			}			
			if(title.length()>0)
			{
				if (global.cont_avail.equals("yes"))
				{	
					if (artTitleExist==true && editors.length()==0)
					{
						//Kishor [14/07/2004]
						//PT without editors but continue with contribution title.
						if(XT.articleType.equalsIgnoreCase("ES"))
							tmp+=" En: {\\it "+title+"} ";	
						else
						tmp+=" In: {\\it "+title+"} ";	
					}	
					else
					{
						tmp+=" {\\it "+title+"} ";
					}
				}
				else
				{	
					tmp+=" {\\it "+title+"}.";  //22-07-04
				}
			}
			
			if(edition.length()>0) // 23-07-04 [Gaurav & Rajeev - In case of edition]
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				//tmp+="("+edition+"). ";//old//12/10/2009
				/*[ref style 5: 
							EBT consists of page number and edition;
							and page number is tagged, it should be
							set after edited book title within parentheses
							including edition followed by comma]
							[Chenge request by Rajiven C.E. dated 16/12/2006 :Ravi]
					*/
				edtion1=edition;
				
			/*
				if(!edition.endsWith("."))
				{
					tmp+=".";
				}
			*/
			}
			if(pub.length()>0)
			{	//tmp+=" "+pub;
				global.pub_for_ref5=pub;//Bcz in Ref 5 it should come after page.
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
			//System.out.println("tmp---> "+tmp);
			//System.in.read();
		}
		//-----------------------------------------------------------------------------

		//Kishor
		else if(xmlObj.bibStyle.equals("5-Manage"))
		{
			if(date.size()==1)
				sTag=date.get(0).toString()+".";
			else
				sTag=date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+".";
			//
			if(authorExist==true)
			{
				if(!artTitle.endsWith(".")&&!artTitle.endsWith("!")&&!artTitle.endsWith("?")&&artTitle .length()>0)
				{
					artTitle = artTitle +".";
					tmp+=" "+sTag+" "+artTitle;
					

				}
				else
				{
					tmp+=" "+sTag+" "+artTitle;
				}
				
				if(editors.length()>0)
				{
					tmp+= editors;
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							tmp+=" (Eds.),";
						}
						else
						{
							tmp+=" (Eds.).";
						}
						
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{	
							tmp+=" (Ed.),";
						}
						else
						{	tmp+=" (Ed.).";
						}
					}
				}
			}
			else if(editors.length()>0)
			{
				tmp+=editors;
				if(isMultipleEditor==true)
				{
					tmp+=" (Eds.). ";
				}
				else
				{
					tmp+=" (Ed.). ";
				}
				tmp+=sTag;
			}
			else
			{
				if(bSer.startsWith(" In "))
					bSer=bSer.substring(" In ".length());
				index =bSer.indexOf("<DATE>");

				bSer = bSer.substring(0,index)+sTag+" "+bSer.substring(index+"<DATE>".length());
			}
			
			index =bSer.indexOf("<DATE>");
			if(index!=-1)
			{
				bSer = bSer.substring(0,index)+bSer.substring(index+"<DATE>".length());
			}
			if(bSer.length()>0)
			{
				tmp+=" "+bSer;
			}			
			if(title.length()>0)
			{
				if (global.cont_avail.equals("yes"))
				{	tmp+=" {\\it "+title+"} ";
				}
				else
				{	tmp+=" {\\it "+title+".}";
				}
			}
			
			if(edition.length()>0) // 23-07-04 [Gaurav & Rajeev - In case of edition]
			{
				if(tmp.endsWith("."))
				{
					tmp = tmp.substring(0,tmp.length()-1);
					tmp+=" ";
				}
				tmp+="("+edition+"). ";
				if(!edition.endsWith("."))
				{
					tmp+=".";
				}
			}

			if(pub.length()>0)
			{	//tmp+=" "+pub;
				global.pub_for_ref5Manage=pub;//Bcz in Ref 5-Manage it should come after page.
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
			
		}
		//-----------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("6"))
		{
			if(artTitle.length()>0)
			{
				tmp+=" "+artTitle+".";
			}
			if(editors.length()>0 && authorExist==true)
			{
				if(artTitleExist== true)
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						tmp+=" En: ";
					else
					tmp+=" In: ";
					artTitle="";
				}
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", eds. ";
				}
				else
				{
					tmp+=", ed. ";
				}
			}
			else if(editors.length()>0)
			{
				tmp+= editors;
				if(isMultipleEditor==true)
				{
					tmp+=", eds. ";
				}
				else
				{
					tmp+=", ed. ";
				}
			}
			//
			if(title.length()>0)
			{
				title="{\\it "+title+"}";
				if(editors.length()==0)
				{
					if(tmp.endsWith("."))
					{
						tmp=tmp.substring(0,tmp.length()-1);
					}
					if(XT.articleType.equalsIgnoreCase("ES"))
						tmp+=". En: ";
					else
					tmp+=". In: ";
				}
				tmp+=title+". ";
				if(ttitle.length()>0)
					tmp+="["+ttitle+"]";
			}

			if(con.length()>0)
			{
				tmp+=con+". ";
			}
			//Case: Edited Book Series Title when contribution is available
			if ( (global.cont_avail.equals("yes")) && 
				 (global.dkj_edited_book.equals("yes")) && 
				 (global.dkj_book_series.equals("yes"))
			   )
			{
				//System.out.println("Cap Guide: Example 14 for Ref Style 6");
				
				//Placement work will be done in bibHost(), bcz of page placement
				global.dkj_bSer=bSer;
				global.dkj_pub=pub;
				global.dkj_date=date.get(0).toString();
			}
			else 
			{
				if(bSer.length()>0 && tmp.length()>0)
				{
					tmp+=bSer+". ";
				}
				else if(bSer.length()>0)
				{
					if(bSer.startsWith(" In: "))
					{
						bSer = bSer.substring(4);
					}
					tmp+=bSer+". ";
				}
				if(edition.length()>0)
				{
					tmp+=" "+edition+ " ";
				}
				if(pub.length()>0)
				{
					tmp+=pub+"; ";//Deo Kishor
				}
				if(tmp.endsWith(". "))
				{
					tmp = tmp.substring(0,tmp.length()-2)+ "; ";
				}
				if(date.size()==2)
				{
					tmp+=date.get(0).toString()+"{\\ndash}"+date.get(1).toString()+".";
				}
				else
				{
					tmp+=date.get(0).toString()+".";			
				}
			}
			if(isbn.length()>0)  // 29-07-04
			{
				if(tmp.endsWith("."))
					tmp=tmp.substring(0,tmp.length()-1);

				tmp+=", "+"ISBN {"+isbn+"}";
				isbn="";
			}
		}
		ttitle="";
		//System.out.println("tmp -- >"+tmp);
		//System.in.read();
			return tmp;
	}/////end of bibEditedBook()

	//---------------------------------------------------------------------------------
	private String bibBookSeries() throws IOException
	{
		String bookSeries = new String();
		String eTag = new String();
		String sTag = new String();
		String ser = new String();
		String editors = new String();
		int ch = 0;
		boolean	isMultipleEditor = false;
		while(!sTag.equals("</SB:BOOK-SERIES>"))
		{
			ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:EDITORS>"))
				{
					editors = sbEditors();
					//System.out.println("editors --> "+editors);
					isMultipleEditor=isMultipleEditors;
					isMultipleEditors=false;
				}
				else if(sTag.equals("<SB:SERIES>"))
				{
					ser = bibSeries();
					//System.out.println("ser --> "+ser);
					
				}
			}
		}//End of while
		
		if(hostType.equals("<SB:EDITED-BOOK>"))
		{
			//------------------------------------------------------------------------------
			if(xmlObj.bibStyle.equals("1") || xmlObj.bibStyle.equals("1a")|| xmlObj.bibStyle.equals("1b"))
			{
				if(editors.length()>0)
				{
					if (global.cont_avail.equals("yes"))
					{
						if (artTitleExist==true)
						{
							if(XT.articleType.equalsIgnoreCase("ES"))//29-08-2011
							bookSeries+= ", en: "+editors;//--1-09-04, Gaurav 
							else
							{
								if(xmlObj.jid.equals("JASCER"))
								bookSeries+= ", "+editors;
								else
								bookSeries+= ", in: "+editors;//--1-09-04, Gaurav 
							}
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))//29-08-2011
							bookSeries+= " en: "+editors;//--20-08-04, Gaurav Based On CEJ 4414---- 
							else
							if(xmlObj.jid.equals("JASCER"))
							bookSeries+= " "+editors;
							else
							bookSeries+= " in: "+editors;//--20-08-04, Gaurav Based On CEJ 4414---- 
						}
					}
					else
					{
						bookSeries+= editors;
					}
					
					if(isMultipleEditor==true)
					{
						if(!xmlObj.jid.equals("JASCER"))
						bookSeries+=" (Eds.)";
					}
					else
					{
						if(!xmlObj.jid.equals("JASCER"))
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
				}

				if(ser.length()>0)
				{
					if (editors.length()>0)
					{
						bookSeries+=ser;
					}
					else
					{
						if (artTitleExist==true && ebTitle.length()==0) //14-09-04
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+=", en: "+ser;						
							else
							bookSeries+=", in: "+ser;						
						}
						else
						{
							bookSeries+=", "+ser;						
						}
					}
				}
			}	
			else if(xmlObj.bibStyle.equals("brvhead"))
			{
				if(editors.length()>0)
				{
					if (global.cont_avail.equals("yes"))
					{
						/**
						*Added By Ravi
						* Date : [29/06/2007]
						* Modify Point : In Book Review case "in"  not com between authors and editors name.
						*Change Request By: TPMS Change Request.
						*/
						//old
						if (artTitleExist==true)
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+= ", en: "+editors;
							else
							bookSeries+= ", in: "+editors;
						}
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+= " en: "+editors;
							else
							bookSeries+= " in: "+editors;
						}
						
						
						/*if (artTitleExist==true)
						{
							bookSeries+= ", "+editors;
						}
						else
						{
							bookSeries+= " "+editors;
						}*/
					}
					else
					{
						bookSeries+= editors;
					}
					
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.)";
					}
					else
					{
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
				}

				if(ser.length()>0)
				{
					if (editors.length()>0)
					{
						bookSeries+=ser;
					}
					else
					{
						if (artTitleExist==true && ebTitle.length()==0)
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+=", en: "+ser;						
							else
							bookSeries+=", in: "+ser;						
						}
						else
						{
							bookSeries+=", "+ser;						
						}
					}
				}
			}	
			//--------------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("2"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.)";
					}
					else
					{
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";			
					
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)  // 17-9-04
				{
					if (editors.length()>0)
					{
						bookSeries+=ser;
					}
					else
					{
						if (artTitleExist==true && ebTitle.length()==0) //14-09-04
						{
							if(xmlObj.jid.equals("TRSTMH")||xmlObj.jid.equals("INHE"))
							{
								if(bookSeries.endsWith("."))
									bookSeries=bookSeries.substring(0,bookSeries.length()-1)+",";
								if(XT.articleType.equalsIgnoreCase("ES"))
									bookSeries+=" en: "+ser;						
								else
								bookSeries+=" in: "+ser;						
							}
							else
							{
								if(XT.articleType.equalsIgnoreCase("ES"))
									bookSeries+=" En: "+ser;						
								else
								bookSeries+=" In: "+ser;						
							}
						}
						else
						{
							bookSeries+=" "+ser;//Gaurav--11/20/04--Based On New Requierment.						
						}
					}
				}
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3-ZEFQ"))
			{
				if(editors.length()>0)
				{
					if (isEditor==true) // 29-07-04
					{
						bookSeries+= editors;						
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+= " En: "+editors;
						else
						bookSeries+= " In: "+editors;
					}
															
					if(isMultipleEditor==true)  // 26-07-04
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", eds:";
							}
							else
							{
								bookSeries+=" (Eds)";
							}
						}
						else
						{
							bookSeries+=", editors";
						}
					}
					else
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", ed.:";
							}
							else
							{
								bookSeries+=" (Ed.)";
							}
						}
						else
						{
							bookSeries+=", editor";
						}
					}
					if (bookSeries.endsWith(", eds:") || bookSeries.endsWith(", ed.:"))
					{
						bookSeries+=" ";
					}
					else
					{
						bookSeries+=". ";
					}
				}
				if(ser.length()>0)
				{					
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3") || xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
			{
				if(editors.length()>0)
				{
					if (isEditor==true) // 29-07-04
					{
						bookSeries+= editors;						
					}
					else
					{
						//if(xmlObj.jid.equals("NRL")&& XT.articleType.equalsIgnoreCase("ES"))
						if(XT.articleType.equalsIgnoreCase("ES"))
						{
							bookSeries+= " En: "+editors;
						}
						else if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
							bookSeries+=" Em: "+editors;
						else
						bookSeries+= " In: "+editors;
						
					}
															
					if(isMultipleEditor==true)  // 26-07-04
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", eds:";
							}
							else
							{
								bookSeries+=" (Eds)";
							}
						}
						else
						{
							
							if(XT.articleType.equalsIgnoreCase("PT")&&(xmlObj.jid.equals("DIAPRE")||xmlObj.jid.equals("RPPNEU")||xmlObj.jid.equals("RPEMD")||xmlObj.jid.equals("REPC")||xmlObj.jid.equals("JPG")||xmlObj.jid.equals("JPGE")||xmlObj.jid.equals("RIMNI")))//19-10-2011
							bookSeries+=", editores";
							else
							{
								if(xmlObj.bibStyle.equals("6"))
									bookSeries+=", eds";
								else
									bookSeries+=", editors";
							}
						}
					}
					else
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", ed.:";
							}
							else
							{
								bookSeries+=" (Ed.)";
							}
						}
						else
						{
							if(xmlObj.bibStyle.equals("6"))
								bookSeries+=", ed";
							else
								bookSeries+=", editor";
						}
					}
					if (bookSeries.endsWith(", eds:") || bookSeries.endsWith(", ed.:"))
					{
						bookSeries+=" ";
					}
					else
					{
						bookSeries+=". ";
					}
				}
				if(ser.length()>0)
				{	
					//bookSeries+=ser;//block as space is required//05-08-2011
					bookSeries+=" "+ser;
				}
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3a-Jecs"))
			{
				if (global.cont_avail.equals("yes"))
				{
					if(editors.length()>0)
					{
						bookSeries+= " In ";
					}
					else // 12-08-04 [ ' In ' in between CT & EBST in absence of editors & in presence of Vol.]
					{
						if (artTitleExist==true && ebTitle.length()==0 && ser.length()>0)
						{
							bookSeries+= " In ";
						}
					}
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}			
					if(editors.length()>0)
					{
						bookSeries+=", ed. ";
						bookSeries+=editors;
					}					
				}
				else
				{
					if(editors.length()>0) // [ CE:Nisha Feedback - Editor should come after only book title] - 28-07-04
					{
						bookSeries+= " In ";
					}
					if(editors.length()>0)
					{
						bookSeries+=editors;
						bookSeries+=" ed., ";
					}									
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}
											
				}
				
			}
			//------------------------------------------------------------------------------
			else if((xmlObj.bibStyle).equals("chea"))
			{
				if (global.cont_avail.equals("yes"))
				{
					if(editors.length()>0)
					{
						bookSeries+= " in ";
					}
					else // 12-08-04 [ ' In ' should in between CT & EBST in absence of editors & in presence of Vol.]
					{
						if (artTitleExist==true && ebTitle.length()==0 && ser.length()>0)
						{
							bookSeries+= " in ";
						}
					}
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}			
					if(editors.length()>0)
					{						
						bookSeries+=" "+editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", Eds.; ";
						}
						else
						{
							bookSeries+=", Ed.; ";
						}
					}					
				}
				else
				{
					if(editors.length()>0) // [ CE:Nisha Feedback - Editor should come after only book title] - 28-07-04
					{
						bookSeries+= " In ";
					}
					if(editors.length()>0)
					{
						bookSeries+=editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", Eds. ";
						}
						else
						{
							bookSeries+=", Ed. ";
						}
					}									
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}
											
				}
				
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("4"))
			{
				if(editors.length()>0)
				{
					if (global.cont_avail.equals("yes"))//Gaurav --31-08-04 on Neelam for nsm 3799--
	 				{
						if(xmlObj.jid.equals("PROTIS"))//07-07-2011
							bookSeries+= " In "+editors;
						else
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+= " En: "+editors;
							else
							bookSeries+= " In: "+editors;
						}
					}
						else
					{
							bookSeries+= " "+editors;
					}
					
					if(isMultipleEditor==true)
					{
						if(xmlObj.jid.equals("PROTIS"))
							bookSeries+=" (eds) ";
						else
						bookSeries+=", editors";
					}
					else
					{
						if(xmlObj.jid.equals("PROTIS"))
							bookSeries+=" (ed) ";
						else
						bookSeries+=", editor";
					}
					if(!xmlObj.jid.equals("PROTIS"))
						bookSeries+=". ";
				}
				if(ser.length()>0)
				{
					//System.out.println("--->"+ser);
					bookSeries+=ser;
				}
				//System.out.println("--->"+ser);
			}
			//------------------------------------------------------------------------------
			else if((xmlObj.bibStyle.equals("5"))||( xmlObj.bibStyle.startsWith("IChemE")))
			{
				//System.out.println("editors===>>"+editors);
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if (global.cont_avail.equals("yes"))
					{
						if(isMultipleEditor==true)
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))
							if(xmlObj.deviation.equals("BOOKS"))
							{
								bookSeries+=" (Eds), ";//Gaurav--12/29/04--Based on New Book requierments.
							}
							else
							{
								if(xmlObj.jid.equals("INFBEH"))//16-09-2011
								bookSeries+=" (Series Eds.) \\&, ";
								else
								bookSeries+=" (Eds.), ";
					        }
						}
						else
						{
							if(xmlObj.jid.equals("INFBEH"))//16-09-2011
							bookSeries+=" (Series Ed.) \\&, ";
							else
							bookSeries+=" (Ed.), ";
						}
					}
					else
					{
						if(isMultipleEditor==true)
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))
							if(xmlObj.deviation.equals("BOOKS"))
							{
								bookSeries+=" (Eds). ";//Gaurav--12/29/04--Based on New Book requierments.
							}
							else
							{
								if(xmlObj.jid.equals("INFBEH"))//16-09-2011
									bookSeries+=" (Series Eds.) \\& ";
								else
								bookSeries+=" (Eds.). ";
							}
						}
						else
						{
							if(xmlObj.jid.equals("INFBEH"))//16-09-2011
								bookSeries+=" (Series Ed.) \\& ";
							else
							bookSeries+=" (Ed.). ";
						}					
					}
					//bookSeries+=", ";
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
					
				}
			}//End for style:5
			else if((xmlObj.bibStyle.equals("5-Retail")))
			{
				
				if(ser.length()>0)
				{
					bookSeries+="In "+ser+" ";
					
				}
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if (global.cont_avail.equals("yes"))
					{
						if(isMultipleEditor==true)
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))
							if(xmlObj.deviation.equals("BOOKS"))
							{
								bookSeries+=" eds, ";//Gaurav--12/29/04--Based on New Book requierments.
							}
							else
							{
							bookSeries+=" eds. ";
					        }
						}
						else
						{
							bookSeries+=" ed. ";
						}
					}
					else
					{
						if(isMultipleEditor==true)
						{
							///if ((xmlObj.jid.equals("SSI") || xmlObj.jid.equals("SSCH") || xmlObj.jid.equals("AFEC")))
							if(xmlObj.deviation.equals("BOOKS"))
							{
								bookSeries+=" eds. ";//Gaurav--12/29/04--Based on New Book requierments.
							}
							else
							{
								bookSeries+=" eds. ";
							}
						}
						else
						{
							bookSeries+=" ed. ";
						}					
					}
					//bookSeries+=", ";
					bookSeries+="<DATE>";
				}
			}//End for style:5
			//------------------------------------------------------------------------------
			else if((xmlObj.bibStyle).equals("5-BookB"))
			{
				if (ebTitle.length()==0 && authorExist==true ) 
				{
					if(ser.length()>0)
					{
						bookSeries+="In "+bsTitle;//(09-08-04-Gaurav & Rajeev--In case of EBST)
					}
					if(editors.length()>0)
					{
					//Kishor[21/06/2004]					
					
						if (global.cont_avail.equals("yes"))
						{
							bookSeries+= editors;
							if(isMultipleEditor==true)
							{
								bookSeries+=", eds.), ";
							}
							else
							{
								bookSeries+=", ed.), ";
							}
						}
						else
						{
							bookSeries+= editors;
							if(isMultipleEditor==true)
							{
								bookSeries+=", eds. ";
							}
							else
							{
								bookSeries+=", ed. ";
							}					
						}
						
					bookSeries+=bsVol;
					bookSeries+="<DATE>";
				}
				

				}
				else
				{
				
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]					
					
					if (global.cont_avail.equals("yes"))
					{
						bookSeries+= editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", eds.), ";
						}
						else
						{
							bookSeries+=", ed.), ";
						}
					}
					else
					{
						bookSeries+= editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", eds. ";
						}
						else
						{
							bookSeries+=", ed. ";
						}					
					}
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
				}
			}//End for style:5
//-------------------------------------------------------------------------------------
	else if((xmlObj.bibStyle).equals("DDT-NS"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]					
					
					if (global.cont_avail.equals("yes"))
					{
						bookSeries+= editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=" eds), ";
						}
						else
						{
							bookSeries+=" ed.), ";
						}
					}
					else
					{
						bookSeries+= editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=" eds ";
						}
						else
						{
							bookSeries+=" ed. ";
							
						}					
					}
					//bookSeries+=", ";
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}//End for style:5
		//------------------------------------------------------------------------------
		else if(xmlObj.bibStyle.equals("5-Asw"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if (global.cont_avail.equals("yes"))
					{
						if(isMultipleEditor==true)
						{
							bookSeries+=" (Eds.), ";
						}
						else
						{
							bookSeries+=" (Ed.), ";
						}
					}
					else
					{
						if(isMultipleEditor==true)
						{
							bookSeries+=" (Eds.). ";
						}
						else
						{
							bookSeries+=" (Ed.). ";
						}					
					}
					//bookSeries+=", ";
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}//End for style:5
			//---------------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("5-Manage"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if(isMultipleEditor==true)
					{
						if (global.cont_avail.equals("yes"))
						{
							bookSeries+=" (Eds.), ";
						}
						else
						{
							bookSeries+=" (Eds.). ";
						}
					}
					else
					{
						if (global.cont_avail.equals("yes"))
						{
							bookSeries+=" (Ed.), ";
						}
						else
						{
							bookSeries+=" (Ed.). ";
						}
					}
					//bookSeries+=", ";
					bookSeries+="<DATE>";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}//End :5-Manage
			//---------------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("6"))
			{
				if(editors.length()>0)
				{
					bookSeries+= " "+editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=", eds";
					}
					else
					{
						bookSeries+=", ed";
					}
					bookSeries+=". ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
		}
		else if((hostType.equals("<SB:BOOK>") || hostType.toUpperCase().startsWith("<SB:BOOK ")))
		{
			System.out.println("INSIDE <SB:BOOK>............................................");
			//------------------------------------------------------------------------------
			if(xmlObj.bibStyle.equals("1")||(xmlObj.bibStyle).equals("1b"))
			{
				if(editors.length()>0)
				{
					//---------------------------------------
					//bookSeries+= ", in: "+editors; 
					//Changed by below coding-feedback on 30/06/2004-[SUPFLU-930]
					if (global.cont_avail.equals("yes"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+= ", en: "+editors;
						else
						bookSeries+= ", in: "+editors;
					}
					else
					{
						bookSeries+= editors;
					}
					//---------------------------------------
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.)";
					}
					else
					{
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			else if(xmlObj.bibStyle.equals("brvhead"))
			{
				if(editors.length()>0)
				{
					if(global.cont_avail.equals("yes"))
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+= ", en: "+editors;
						else
						bookSeries+= ", in: "+editors;
					}
					else
					{
						bookSeries+= editors;
					}
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.).";
					}
					else
					{
						bookSeries+=" (Ed.).";
					}
					bookSeries+=", ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
			if(xmlObj.bibStyle.equals("1a"))
			{
				if(editors.length()==0 && artTitleExist==true)
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						bookSeries+=",  en: ";
					else
					bookSeries+=",  in: ";//14-08-04(Gaurav & Rajeev---Based on Geeta FeedBack Of Chroma 344011)
				}
              
				if(editors.length()>0)
				{
					if (global.cont_avail.equals("yes") && artTitleExist==true)
					{						
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+=",  en: "+editors;
						else
						bookSeries+=",  in: "+editors;//----Gaurav --Based on Chroma 344042.------
					}
				    else if (global.cont_avail.equals("yes") && artTitleExist==false)//----Based on Chroma 344139.--31-08-04.------
					{	
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+=" en: "+editors;
						else
						bookSeries+=" in: "+editors;//----Based on Chroma 344042.------
					}
					else
					{
						bookSeries+= editors;
					}	
					//-----------------------------------------
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.)";
					}
					else
					{
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}			
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("2"))
			{
				if(editors.length()>0)
				{
					//Kishor [21/06/2004]
					//Commented bcz now in style 2 In: is coming from editors format
					//bookSeries+= " In: "+editors;
					bookSeries+= editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=" (Eds.)";
					}
					else
					{
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
					if (global.cont_avail.equals("no"))
					{					
						bookSeries+=xmlObj.extractData("</SB:DATE>", true)+". ";
					}
				}
				
				bookSeries+=ser;
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3-ZEFQ"))
			{
				if(editors.length()>0)
				{
					if (editors.startsWith(" In: "))  // 28-07-04 [Doubling of In: ] - Gaurav & Rajeev
					{
					editors=editors.substring(4);
					}
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						if (bookSeries.length()>0 || global.cont_avail.equals("yes"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+= " En: "+editors;
							else
							bookSeries+= " In: "+editors;
						}
						else
						{
							bookSeries+= editors;
						}
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+= " En: "+editors;
						else
						bookSeries+= " In: "+editors;
					}
					if(isMultipleEditor==true)
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", eds:";
							}
							else
							{
								bookSeries+=" (Eds)";
							}
						}
						else
						{
							bookSeries+=", editors";
						}
					}
					else
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", ed.:";
							}
							else
							{
								bookSeries+=" (Ed.)";
							}
						}
						else
						{
							bookSeries+=", editor";
						}
					}
					if (bookSeries.endsWith("ed.:")||bookSeries.endsWith("eds:"))
					{
						bookSeries+=" ";
					}
					else
					{
						bookSeries+=". ";
					}
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3") || xmlObj.bibStyle.equals("3a")|| xmlObj.bibStyle.equals("6") || (xmlObj.bibStyle).equals("yijom-ns"))
			{
				
				if(editors.length()>0)
				{
					if (editors.startsWith(" In: "))  // 28-07-04 [Doubling of In: ] - Gaurav & Rajeev
					{
						editors=editors.substring(4);
					}
					if ((xmlObj.bibStyle).equals("yijom-ns"))
					{
						if (bookSeries.length()>0 || global.cont_avail.equals("yes"))
						{
							if(XT.articleType.equalsIgnoreCase("ES"))
								bookSeries+= " En: "+editors;
							else
							{
								
								bookSeries+= " In: "+editors;
							}
						}
						else
						{
							bookSeries+= editors;
						}
					}
					else
					{
						if(XT.articleType.equalsIgnoreCase("ES"))
							bookSeries+= " En: "+editors;
						else
						bookSeries+= " In: "+editors;
					}
					if(isMultipleEditor==true)
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", eds:";
							}
							else
							{
								bookSeries+=" (Eds)";
							}
						}
						else
						{
							bookSeries+=", editors";
						}
					}
					else
					{
						if ((xmlObj.bibStyle).equals("yijom-ns"))
						{
							if (global.cont_avail.equals("yes"))
							{
								bookSeries+=", ed.:";
							}
							else
							{
								bookSeries+=" (Ed.)";
							}
						}
						else
						{
							bookSeries+=", editor";
						}
					}
					if (bookSeries.endsWith("ed.:")||bookSeries.endsWith("eds:"))
					{
						bookSeries+=" ";
					}
					else
					{
						bookSeries+=". ";
					}
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
				//System.out.println("bookSeries===>>"+bookSeries);
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("3a-Jecs"))
			{
				if (global.cont_avail.equals("yes"))
				{
					if(editors.length()>0)
					{
						if(artTitleExist==false)
						{
							bookSeries+= " ";//--18-08-04--Gaurav --Based on CE Nisha (5068)--
						}
						else
						{
							bookSeries+= " In ";
						}
						
					}
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}			
					if(editors.length()>0)
					{
						bookSeries+=", ed. ";
						bookSeries+=editors;
					}					
				}
				else
				{
					if(editors.length()>0)
					{
						bookSeries+= " In ";
					}
					if(editors.length()>0)
					{
						bookSeries+=editors;
						bookSeries+=" ed., ";
					}									
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}
					
				}
			}
			//------------------------------------------------------------------------------
			else if((xmlObj.bibStyle).equals("chea"))
			{
			   if (global.cont_avail.equals("yes"))
				{
					if(editors.length()>0)
					{
						if(artTitleExist==false)
						{
							bookSeries+= " ";
						}
						else
						{
							bookSeries+= " in ";
						}
						
					}
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}			
					if(editors.length()>0)
					{						
						bookSeries+=" "+editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", Eds.; ";
						}
						else
						{
							bookSeries+=", Ed.; ";
						}
					}					
				}
				else
				{
					if(editors.length()>0)
					{
						bookSeries+= " In ";
					}
					if(editors.length()>0)
					{
						bookSeries+=editors;
						if(isMultipleEditor==true)
						{
							bookSeries+=", Eds. ";
						}
						else
						{
							bookSeries+=", Ed. ";
						}
					}									
					if ((ser.length()>0))
					{
						bookSeries+=ser;
					}
					
				}
			}
			//-----------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("4"))
			{
				if(editors.length()>0)
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						bookSeries+= " En: "+editors;
					else
					bookSeries+= " In: "+editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=", editors";
					}
					else
					{
						bookSeries+=", editor";
					}
					bookSeries+=". ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
			else if(xmlObj.bibStyle.equals("5")||( xmlObj.bibStyle.startsWith("IChemE"))||(xmlObj.bibStyle).equals("DDT-NS"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if(isMultipleEditor==true)
					{
						if(xmlObj.jid.equalsIgnoreCase("INFBEH"))//16-09-2011
							bookSeries+=" (Series Eds.) \\&";
						else
						bookSeries+=" (Eds.)";
					}
					else
					{
						if(xmlObj.jid.equalsIgnoreCase("INFBEH"))//16-09-2011
							bookSeries+=" (Series Ed.) \\&";
						else
						bookSeries+=" (Ed.)";
					}
					bookSeries+=", ";
					
				}
				
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}//End for style:5
			
			//------------------------------------------------------------------------------
			else if((xmlObj.bibStyle).equals("5-BookB"))
			{
				if(editors.length()>0)
				{
					//Kishor[21/06/2004]
					bookSeries+= editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=", eds.)";
					}
					else
					{
						bookSeries+=", ed.)";
					}
					bookSeries+=", ";
					
				}
				
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}//End for style:5
			
			//------------------------------------------------------------------------------

			else if(xmlObj.bibStyle.equals("6"))
			{
				if(editors.length()>0)
				{
					if(XT.articleType.equalsIgnoreCase("ES"))
						bookSeries+= " En: "+editors;
					else
					bookSeries+= " In: "+editors;
					if(isMultipleEditor==true)
					{
						bookSeries+=", eds";
					}
					else
					{
						bookSeries+=", ed";
					}
					bookSeries+=". ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
			//------------------------------------------------------------------------------
		}
		return bookSeries;
	}//End of public
//----------------------------------------------------------------------------------------
public String removeJrnlDot(String jrnl)
{
	if(jrnl.endsWith(".")){
		int index = jrnl.lastIndexOf(".");
		jrnl=jrnl.substring(0,index)+jrnl.substring(index+1);
	}
	 
/*	
 *	Updated on 17-09-2015 as all dots removed in JournalTitle due to this code
	int index =0;
	while(true)
	{
		index = jrnl.indexOf(".");

		if(index < 0)
		{
			break;
		}
		jrnl=jrnl.substring(0,index)+jrnl.substring(index+1);
	}
*/	return jrnl;
}

private String eHost() throws IOException
{
	String eTag = new String();
	String sTag = new String();
	String url = new String();
	String date = new String();
	int ch = 0;
	while(!sTag.equals("</SB:E-HOST>"))
	{
		ch = (char) xmlObj.fin.read();
		if(ch =='<')
		{
			String sTag1 = xmlObj.getTag();
			sTag = sTag1.toUpperCase();
			if(sTag.startsWith("<CE:INTER-REF"))
			{
				otherinterref=true;
				intref=true;
				String url_role=xmlObj.getAttributeValue(sTag, "XLINK:ROLE").toLowerCase();
				//System.out.println("url_role-->"+url_role);
				String ref= xmlObj.getAttributeValue(sTag, "XLINK:HREF").toLowerCase();
				//System.out.println("bibliogra-->"+ref);
				String urlStr= xmlObj.extractData("</CE:INTER-REF>", true);				
				//System.out.println("urlStr-->"+urlStr);
				if(url_role.equalsIgnoreCase("http://www.elsevier.com/xml/linking-roles/preprint"))
				{
					url=" {"+urlStr+"}";
				}
				else
				url= " "+"\\mychar\\url{"+xmlObj.getAttributeValue(sTag1, "XLINK:HREF".toLowerCase())+"}{"+urlStr+"}{}";
			//System.out.println("url===>>"+url);
				/*if(ref.startsWith("http"))  //11-11-2004-Gaurav Based on New requirment.11/10/04. //19-10-2012 This was blocked, There is no use. this was used For JID THOBK. Currently this jid is not in use.
				{
					if (global.cont_avail.equals("no"))
					{
						url = " "+urlStr;
					}
					else
					{
						url = ", "+urlStr;
					}
					

				}
				else*/
				if(ref.startsWith("mailto") || ref.startsWith("ftp")) 
				{
					url = " "+urlStr;
				}
		
			}
			else if(sTag.equals("<SB:DATE>"))
			{
				
				if(url.length()>0)
					url+=", ";
				
				if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
				{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
				}
				else if((xmlObj.bibStyle).equals("5-Retail"))//14/04/2008
				{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
				}
				else
				{
					date = xmlObj.extractData("</SB:DATE>", true);
					if(tempLabel.length()>0) // 15-07-2004
					{
						//System.out.println(tempLabel+"<--->"+date);
						if(tempLabel.startsWith(date))
							date=tempLabel;
						tempLabel="";
					}
				}
				//url+= xmlObj.extractData("</SB:DATE>", true);
				url+= date;
			}
		}
	}
	return url;
}

private boolean isElementHost() throws IOException
{
	boolean isHost = false;
	long filePos= xmlObj.fin.getFilePointer();
	String tag = new String();
	while (true)
	{
		char ch= (char)xmlObj.fin.read();
		if (ch=='<')
		{
			tag= xmlObj.getTag().toUpperCase();
			if (tag.equals("<SB:HOST>"))
			{
				isHost = true;
			}
			else
			{
				isHost = false;
			}
			break;
		}
	}
	xmlObj.fin.seek(filePos);
	return isHost;
 }

private ArrayList chekJid() throws IOException
{
	String checkSpanish="";
	String databasePath="V:\\Database\\SpanishJID.DBF";
	
	InputStream readSpanishFile=new FileInputStream(databasePath);
	BufferedReader fin= new BufferedReader(new InputStreamReader(readSpanishFile));
	String lineStr="";
	while ((lineStr=fin.readLine())!=null)
	{
		checkSpanishJidList.add(lineStr);								
	}	
	return checkSpanishJidList;
}
}