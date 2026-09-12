package tp.xt;

import java.io.*;
import java.util.*;

class TailGroup
{
	XMLObjects xmlObj;
	String bibStyle="5";
	String CocompBiog="";
	static String JprBiog="";
	//mukesh 25-09-08 for addition of biographies in textoc
	public String ACA_biblography="";
	static boolean isBiography=false;
	static boolean isReference=false;
	int biography=0;

	static boolean temp_biography=false;
	static boolean temp_Reference=false;
	TailGroup(XMLObjects xmlObj)throws IOException
	{
		this.xmlObj  = xmlObj;
		CocompBiog="";
		JprBiog="";
	}
	
	public String getTailContents()throws IOException
	{
		System.out.println("Processing Tail Contents...");
		StringBuffer tailContents= new StringBuffer();
		String tag= "";
		//BodyGroup bd= new BodyGroup();
		while (!tag.equals("</TAIL>") && !tag.equals("</SIMPLE-TAIL>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();			
				if (tag.startsWith("<CE:BIBLIOGRAPHY"))
				{ 
					String biblioView= xmlObj.getAttributeValue(tag, "VIEW");
					if(biblioView.equalsIgnoreCase("EXTENDED")){
						System.out.println("biblioView::"+biblioView);
						System.out.println("No need to process these references.");
					}else{
						isReference=true;
						if(xmlObj.jid.equals("THOBK")) // Books-7-10-04//rajeev
						{
							InCref InC= new InCref(xmlObj);
							tailContents.append(InC.artBibilographyInC("CE:BIBLIOGRAPHY"));						
						}
						else
						{
							BiblographyContents biblC= new BiblographyContents(xmlObj);						
							tailContents.append(biblC.artBibilography("CE:BIBLIOGRAPHY"));
							//System.out.println("Ravi "+tailContents.toString());//1234
							//System.in.read();
							temp_Reference=biblC.temp_Reference;//26/03/2009
							//System.out.println(" biblC.temp_Reference  --> "+biblC.temp_Reference);
							//System.in.read();
						}
					}
					
				}
				else if (tag.startsWith("<CE:BIBLIOGRAPHY"))
				{
					String biblioView= xmlObj.getAttributeValue(tag, "VIEW");								
					System.out.println("biblioView::"+biblioView);
					if(xmlObj.jid.equals("THOBK")) // Books-7-10-04//rajeev
					{
						InCref InC= new InCref(xmlObj);
						if(biblioView.equals("EXTENDED"))
							tailContents.append("\r\n\\begin{extra}"+InC.artBibilographyInC("CE:BIBLIOGRAPHY")+"\r\n\\end{extra}");						
						else
							tailContents.append(InC.artBibilographyInC("CE:BIBLIOGRAPHY"));						
					}
					else
					{
						BiblographyContents biblC= new BiblographyContents(xmlObj);						
						if(biblioView.equals("EXTENDED"))
							tailContents.append("\r\n\\begin{extra}"+biblC.artBibilography("CE:BIBLIOGRAPHY")+"\r\n\\end{extra}");
						else
							tailContents.append(biblC.artBibilography("CE:BIBLIOGRAPHY"));
						temp_Reference=biblC.temp_Reference;//26/03/2009
					}
					
				}
				//added by avinandan on 11-8-04
				else if(tag.equals("</CE:TEXTBOX-TAIL>"))
					break;
				//end mark
				else if (tag.equals("<CE:FURTHER-READING>"))
				{
					BiblographyContents biblC= new BiblographyContents(xmlObj);
					tailContents.append(biblC.artBibilography("CE:FURTHER-READING"));				
				}
				else if (tag.startsWith("<CE:FURTHER-READING "))
				{
					String furtherView= xmlObj.getAttributeValue(tag, "VIEW");								
					BiblographyContents biblC= new BiblographyContents(xmlObj);
					if(furtherView.equals("EXTENDED"))
						tailContents.append("\r\n\\begin{extra}"+biblC.artBibilography("CE:FURTHER-READING")+"\r\n\\end{extra}");				
					else
						tailContents.append(biblC.artBibilography("CE:FURTHER-READING"));				
				}
				else if (tag.startsWith("<CE:BIOGRAPHY"))
				{
					isBiography=true;
					if((xmlObj.jid.equals("COCOMP")|| (xmlObj.jid.equals("JPR")) && Integer.parseInt(xmlObj.aid)>=53)) // changed biography coding for Jpr - 53 onwards on request of PDD
					{
						String tt2=xmlObj.biography(tag);
						CocompBiog=CocompBiog + "\r\n\n"+ tt2;  //20-12-04
						JprBiog += "\r\n\n"+tt2;
						tt2="";
					}
					else
					{	
						
							ACA_biblography=xmlObj.biography(tag);//12-08-2010
							//System.out.println("ACA_biblography "+ACA_biblography+"\n xmlObj.jid "+xmlObj.jid);//12-08-2010
							if(xmlObj.jid.equals("ACA"))//12-08-2010
							{
								tailContents.append("\r\n\\begin{authorbiography}");//12-08-2010
								tailContents.append(ACA_biblography);//12-08-2010
								tailContents.append("\r\n\\end{authorbiography}");//12-08-2010
							}
							else if((xmlObj.jid.equals("JMRTEC") && Integer.parseInt(xmlObj.aid)>=53))//09-07-2014
							{
								//System.out.println ("AUTHORBIOGRAPHY===>"+ACA_biblography);
								tailContents.append("\r\n\\begin{authorbiography}");//09-07-2014
								tailContents.append(ACA_biblography);//12-08-2010
								tailContents.append("\r\n\\end{authorbiography}");//09-07-2014
							}
							else
							{
								tailContents.append(ACA_biblography);//12-08-2010
							}
						
						//tailContents.append(xmlObj.biography(tag));//12-08-2010//old
					}
					biography++;
				}
				else if (tag.startsWith("<CE:EXAM-REFERENCE>"))
				{
					tailContents.append("\r\n\n"+xmlObj.extractData("</CE:EXAM-REFERENCE>", true));
				}
				else if (tag.startsWith("<CE:GLOSSARY>")||tag.startsWith("<CE:GLOSSARY "))
				{
					tailContents.append(glossary(tag));
				}
				/*else if (tag.startsWith("<CE:EXAM-QUESTIONS"))
				{
					System.out.print("Processing Exam Question Tail      ");
					//ExamBodyGroup xmlExam= new
					//ExamBodyGroup(xt.xmlObj);
					BodyGroup xmlBody= new BodyGroup(xmlObj);
					tailContents.append("\r\n"+xmlBody.processSections("</CE:EXAM-QUESTIONS>"));
					System.out.println(".. Ok");
				}
				else if (tag.startsWith("<CE:EXAM-ANSWERS"))
				{
					System.out.print("Processing Exam Answer Tail      ");
					//ExamBodyGroup xmlExam= new ExamBodyGroup(xmlObj);
					//tailContents.append("\r\n"+xmlExam.getExamBodyContents());
					BodyGroup xmlBody= new BodyGroup(xmlObj);
					tailContents.append("\r\n"+xmlBody.processSections("</CE:EXAM-ANSWERS>"));
					System.out.println(".. Ok");
				}*/
			}
		}
		//*************************************************
			//
			//
			int spos=0;
			int epos=0;
			while((spos=tailContents.indexOf("\\end{authorbiography}\r\n\\begin{authorbiography}",epos))!=-1)
			{
				spos=tailContents.indexOf("\\end{authorbiography}\r\n\\begin{authorbiography}",epos);
				if(spos!=-1)
				{
					tailContents=tailContents.delete(spos,spos+"\\end{authorbiography}\r\n\\begin{authorbiography}".length());
				}
			}
		//*************************************************
		//added by avinandan
		String tailText=tailContents.toString();
		if((xmlObj.jid.equals("SNB")) || (xmlObj.jid.equals("SNA")) || (xmlObj.jid.equals("ONCH")))
		{
			if(biography==1)
			{
				tailText=tailText.replaceFirst("\\\\begin\\{vt\\}","\\\\section{Biography}\\\\label{PLBiography}\r\n\\\\addbookmark{}{Biography}\r\n\\\\begin{vt}");
				temp_biography=false;
			}
			else if(biography>1)
			{
				tailText=tailText.replaceFirst("\\\\begin\\{vt\\}","\\\\section{Biographies}\\\\label{PLBiography}\r\n\\\\addbookmark{}{Biographies}\r\n\\\\begin{vt}");
				temp_biography=true;
			}
		}
		//end mark
		//commented by avinandan
		//return tailContents.toString();
		//added by avinandan
		if(xmlObj.jid.equals("COCOMP") && CocompBiog.length()>0) // Requested from PDD to place biography before references.
			tailText=CocompBiog+tailText;
		CocompBiog="";
		//System.out.println("tailText : "+tailText);
		//System.in.read();
		return tailText;
		//end mark
	}

	public String processBiblography(String tagType)throws IOException
	{
		StringBuffer biblContents= new StringBuffer();
		String tag      = "";
		String biblTitl = "";
		String bibLbl   = "";
		StringBuffer bibRef   = new StringBuffer();		
		int bibCnt=0;
		while (!tag.equals("</"+tagType+">"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();				
				if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					biblTitl= xmlObj.extractData("</CE:SECTION-TITLE>", true);
				}
				else if (tag.startsWith("<CE:BIB-REFERENCE"))
				{
					bibCnt++;					
					bibRef.append(processBibReference(tag, bibCnt));
					temp_Reference=true;
					
				}
			}
		}
		return "\r\n\\begin{thebibliography}{"+biblTitl+"}{["+bibCnt+"]}\r\n"+bibRef+"\r\n\\end{thebibliography}";
	}
	
	public String processBibReference(String atag, int bibNo)throws IOException
	{
		
		String bibRefID= xmlObj.getAttributeValue(atag, "ID");
		String bibLbl   = "";
		String sbRef    = "";
		String tag      = "";		
		
		while (!tag.equals("</CE:BIB-REFERENCE>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:LABEL>"))
				{
					bibLbl = xmlObj.extractData("</CE:LABEL>", true);
				}
				else if (tag.equals("<SB:REFERENCE"))
				{
					sbRef= procesSBbRef();
				}
				else if (tag.startsWith("<CE:OTHER-REF"))
				{
					sbRef= processOtherRef();
				}
			}
		}
		String tmp= "\r\n\\bibitem{"+bibLbl+"}%BIB"+bibNo+"\r\n"+xmlObj.getHypertarget(bibRefID)+sbRef;
		System.exit(0);
		return tmp;
	}
	
	public String processOtherRef()throws IOException
	{
		String tag      = "";		
		String otherRef = "";
		while (!tag.equals("</CE:OTHER-REF>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:TEXTREF>")||tag.startsWith("<CE:TEXTREF"))
				{
					otherRef= xmlObj.extractData("</CE:TEXTREF>", true);
				}
			}
		}
		return otherRef;
	}
	
	public String procesSBbRef()throws IOException
	{
		String bibHost  = "";
		String bibContr = "";
		String tag      = "";
		String comment  = "";
		
		while (!tag.equals("</SB:REFERENCE>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if(tag.equals("<SB:CONTRIBUTION>"))
				{
					bibContr= processContribution();
				}
				else if(tag.equals("<SB:HOST>"))
				{
					bibHost= processBibHost();
					
				}
				else if(tag.equals("<SB:COMMENT>"))
				{
					comment= xmlObj.extractData("</SB:COMMENT>", true);
					//System.out.println("comment--------  "+comment);
				}				
			}
		}
		String tmp=bibContr+", "+bibHost+comment;
		if(!tmp.endsWith("."))
			tmp+=".";
		return tmp;
	}
	
	
	public String processBibHost()throws IOException
	{
		StringBuffer hostContents= new StringBuffer();
		String tag    = "";
		String jTitle = "";
		String vol    = "";
		String date   = "";
		String fPage  = "";
		String lPage  = "";
		String pubName= "";
		String pubLoc = "";
		int hostType  = 0;
		while (!tag.equals("</SB:HOST>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if(tag.equals("<SB:TITLE>"))
				{
					jTitle= getBibTitle();
				}
				else if (tag.equals("<SB:ISSUE>"))
				{
					hostType=1;
				}
				else if (tag.equals("<SB:BOOK>"))
				{
					hostType=2;
				}
				else if (tag.equals("<SB:EDITED-BOOK>"))
				{
					hostType=3;
				}
				else if (tag.equals("<SB:E-HOST>"))
				{
					hostType=4;
				}
				else if (tag.equals("<SB:VOLUME-NR>"))
				{
					vol= xmlObj.extractData("</SB:VOLUME-NR>", true);
				}
				else if (tag.equals("<SB:DATE>"))
				{
					date= xmlObj.extractData("</SB:DATE>", true);
				}
				else if (tag.equals("<SB:FIRST-PAGE>"))
				{
					fPage= xmlObj.extractData("</SB:FIRST-PAGE>", true);
				}
				else if (tag.equals("<SB:LAST-PAGE>"))
				{
					lPage= xmlObj.extractData("</SB:LAST-PAGE>", true);
				}
				else if (tag.equals("<SB:NAME>"))
				{
					pubName= xmlObj.extractData("</SB:NAME>", true);
				}
				else if (tag.equals("<SB:LOCATION>"))
				{
					pubLoc= xmlObj.extractData("</SB:LOCATION>", true);
				}
			}
		}
		if (bibStyle.equals("1"))
		{
			if (hostType==1)
			{
				hostContents.append(jTitle);
				if(vol.length()>0)
					hostContents.append(" ");
				hostContents.append(vol);
				if(date.length()>0)
					hostContents.append(" ("+date+")");
				if (fPage.length()>0)
				{
					hostContents.append(" "+fPage);
					if(lPage.length()>0)
						hostContents.append("--"+lPage);
				}
			}
			if (hostType==2)
			{
				hostContents.append(jTitle);
				if(vol.length()>0)
					hostContents.append(" ");
				hostContents.append(vol);
				if(date.length()>0)
					hostContents.append(" ("+date+")");
				if (fPage.length()>0)
				{
					hostContents.append(" "+fPage);
					if(lPage.length()>0)
						hostContents.append("--"+lPage);
				}
			}
			
		}		
		else if (bibStyle.equals("2"))
		{
		}
		else if (bibStyle.equals("3"))
		{
		}
		else if (bibStyle.equals("4"))
		{
		}
		else if (bibStyle.equals("4"))
		{
		}
		else if ((bibStyle.equals("5"))||(bibStyle.startsWith("IChemE")))
		{
			if (hostType== 1)
			{
				if(date.length()>0)
					hostContents.append(" ("+date+")");
					
				hostContents.append(jTitle);
				if(vol.length()>0)
					hostContents.append(" ");
				hostContents.append(vol);
				
				if (fPage.length()>0)
				{
					hostContents.append(" "+fPage);
					if(lPage.length()>0)
						hostContents.append("--"+lPage);
				}
			}
		}
		return hostContents.toString();
	}
	
	public String processContribution()throws IOException
	{
		String bibContribution= "";
		String tag        = "";
		String bibAuthors = "";
		String bibtitl    = "";
		while (!tag.equals("</SB:CONTRIBUTION>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<SB:AUTHORS>"))
				{
					bibAuthors= getBiblAuthorsList();				
				}
				else if (tag.equals("<SB:TITLE>"))
				{
					bibtitl= getBibTitle();
				}
			}
		}
		bibContribution+=bibAuthors;
		if(bibtitl.length()>0)
			bibContribution+=", "+bibtitl;

		//System.out.println("bibContribution-->>"+bibContribution);
		return bibContribution;
	}
	
	public String getBibTitle()throws IOException
	{
		StringBuffer bibTitle= new StringBuffer();
		String tag ="";
		while (!tag.equals("</SB:TITLE>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<SB:MAINTITLE>"))
				{
					bibTitle.append(xmlObj.extractData("</SB:MAINTITLE>", true));
				}
			}
		}
		return bibTitle.toString();
	}

	public String getBiblAuthorsList()throws IOException
	{
		StringBuffer bibAuthors= new StringBuffer();
		String fName  = "";
		String sName  = "";
		String suffix = "";
		String tag    = "";
		boolean first= true;
		while (!tag.equals("</SB:AUTHORS>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<SB:AUTHOR>"))
				{
					if (first==false)
						bibAuthors.append(", ");
					first= false;	
				}
				else if (tag.equals("</SB:AUTHOR>"))
				{
					if (bibStyle.equals("1"))
					{
						/* ‘number’ system */
						/* Example : A.B.C. Author1, D.E.F. Author2, G.H.I. Author3*/
						bibAuthors.append(fName);
						if (sName.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(sName);
						if (suffix.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(suffix);
					}
					else if (bibStyle.equals("2"))
					{
						/* ‘name-year’ system*/
						/* Example : Author1, A.B.C., Author2, D.E.F., Author3, G.H.I.*/
						bibAuthors.append(sName);
						if (fName.length()>0)
							bibAuthors.append(", ");
						bibAuthors.append(fName);
						if (suffix.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(suffix);
					}
					else if (bibStyle.equals("3"))
					{
						/* Vancouver system With Number*/
						/* Example : Author1 ABC, Author2 DEF, Author3 GHI */
						bibAuthors.append(sName);
						if (sName.length()>0)
							bibAuthors.append(" ");
						if(fName.length()>0)
							fName= xmlObj.replaceStr(fName, ".", "");
						bibAuthors.append(fName);
						if (suffix.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(suffix);
					}
					else if (bibStyle.equals("4"))
					{
						/* Vancouver system With Name Year*/
						/* Sequence of authors is same as style 3 only diff. is in Label*/
						/* Example : Author1 ABC, Author2 DEF, Author3 GHI.*/
						bibAuthors.append(sName);
						if (sName.length()>0)
							bibAuthors.append(" ");
						if(fName.length()>0)
							fName= xmlObj.replaceStr(fName, ".", "");
						bibAuthors.append(fName);
						if (suffix.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(suffix);
					}
					else if ((bibStyle.equals("5"))||(bibStyle.startsWith("IChemE")))
					{
						/* APA System */
						/* Example : Author1, A. B. C., Author2, D. E. F., & Author3*/
						/* Note : Place '&' before last author*/ 
						bibAuthors.append(sName);
						if (sName.length()>0)
							bibAuthors.append(", ");
						if (fName.length()>0)
						{
							fName= xmlObj.replaceStr(fName, "\\.", "\\. ").trim();
						}
						bibAuthors.append(fName);
						if (suffix.length()>0)
							bibAuthors.append(" ");
						bibAuthors.append(suffix);
					}
					/* Other Styles defined here are for Non-Standared Journals*/
					//-Mod7IChemE
					else if (bibStyle.equals("6"))
					{
					
					}
					else if (bibStyle.equals("7"))
					{
					}
					else if (bibStyle.equals("-Mod7IChemE"))
					{
					}
					else if (bibStyle.equals("8"))
					{
					}
					else if (bibStyle.equals("9"))
					{
					}
					else if (bibStyle.equals("10"))
					{
					}
					fName  = "";
					sName  = "";
					suffix = "";
				}
				else if (tag.equals("<CE:GIVEN-NAME>"))
				{
					fName= xmlObj.extractData("</CE:GIVEN-NAME>", true);
				}
				else if (tag.equals("<CE:SURNAME>"))
				{
					sName= xmlObj.extractData("</CE:SURNAME>", true);
				}
				else if (tag.equals("<CE:SUFFIX>"))
				{
					suffix= xmlObj.extractData("</CE:SUFFIX>", true);
				}
			}
		}
		return bibAuthors.toString();
	}
	
	private String glossary(String sTag) throws IOException
	{
		StringBuffer gloss = new StringBuffer();
		StringBuffer gltitle=new StringBuffer();
		char ch;
		char ch1;
		String tag    = "";
		String tag1    = "";
		String tempTag=sTag;
		//System.out.println("tempTag "+tempTag);
		String id="";
		if(tempTag.indexOf("ID=",0)!=-1)
		{
			int spos=0;
			int epos=0;
			spos=tempTag.indexOf("ID=\"",epos);
			if(spos!=-1)
			{
				epos=tempTag.indexOf("\"",spos+4);
				if(epos!=-1)
				{
					id=tempTag.substring(spos+4,epos);
					id="\\hypertarget{"+xmlObj.jid+xmlObj.aid+id+"}{}";
					//System.out.println("xmlObj.jid "+xmlObj.jid);
				}
			}

		}
		boolean first= true;
		while (!tag.equals("</CE:GLOSSARY>"))
		{
			ch= (char)xmlObj.fin.read();
			
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				
				if(tag.startsWith("<CE:SECTION-TITLE"))
				{
					gltitle.append(xmlObj.extractData("</CE:SECTION-TITLE>", true));
				}
				else if(tag.equals("<CE:INTRO>")||tag.startsWith("<CE:INTRO "))//28-08-2012 JADTD520 Updation
				{
					//gloss.append("\r\n"+xmlObj.extractData("</CE:INTRO>", true));//old 13/03/2009
					while (!tag.equals("</CE:INTRO>"))
					{
						ch1= (char)xmlObj.fin.read();
						if (ch1=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if(tag.startsWith("<CE:PARA"))
							{
								gloss.append("\r\n\r\n"+xmlObj.extractData("</CE:PARA>", true));
							}
						}
					}
				}
				else if(tag.startsWith("<CE:GLOSSARY-SEC"))
				{
					gloss.append (glossarySection());
				}
			}
		}
		return "\r\n\\begin{glossary}{"+gltitle+"}"+id+gloss+"\r\n\\end{glossary}";
	}
	
	public String glossarySection()throws IOException
	{
		String tag= new String();		
		StringBuffer glosSection = new StringBuffer();
		char ch;
		while(!tag.equals("</CE:GLOSSARY-SEC>"))
		{
			ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if(tag.startsWith("<CE:SECTION-TITLE"))
				{
					glosSection.append("\r\n\\subsection{"+xmlObj.extractData("</CE:SECTION-TITLE>", true)+"}");
				}
				else if(tag.startsWith("<CE:GLOSSARY-ENTRY"))
				{
					glosSection.append("\r\n"+glossaryEntry());
				}
			}
		}
		return glosSection.toString ();
	}

	private String glossaryEntry() throws IOException
	{
		String tag= new String();		
		StringBuffer gloss = new StringBuffer();
		char ch;
		while(!tag.equals("</CE:GLOSSARY-ENTRY>"))
		{
			ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();			
				if(tag.equals("<CE:GLOSSARY-HEADING>"))
				{
					gloss.append("\\glosshead{"+xmlObj.extractData("</CE:GLOSSARY-HEADING>", true)+"}");
				}
				else if(tag.equals("<CE:GLOSSARY-DEF>"))
				{
					gloss.append("\\glossdefintion{"+ xmlObj.extractData("</CE:GLOSSARY-DEF>", true)+"}");
				}
				else if(tag.startsWith("<CE:GLOSSARY-DEF "))//28-08-2012 JADTD520 updation
				{
					gloss.append("\\glossdefintion{"+ xmlObj.extractData("</CE:GLOSSARY-DEF>", true)+"}");
				}
				else if(tag.startsWith("<CE:GLOSSARY-ENTRY"))
				{
					gloss.append(gloss+glossaryEntry());
				}
			}
		}	
		return gloss.toString ();
	}
}