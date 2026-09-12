package tp.xt;

import java.io.*;
import java.util.*;
class ExamBodyGroup
{
	XMLObjects xmlObj;
	RandomAccessFile fin;
	String tempNomen="";
	BodyGroup xmlBody;

	ExamBodyGroup(XMLObjects xmlObj)throws java.io.IOException
	{
		this.xmlObj  = xmlObj;
		this.fin     = xmlObj.fin;
		xmlBody=new BodyGroup(xmlObj);
	}
	public String getExamBodyContents()throws java.io.IOException
	{
		StringBuffer bodyContents= new StringBuffer();
		String tag= "";
		boolean firstP=true;
		String secNo                ="";
		String secTitle             = "";
		int sectionCount            = 0;
		String secId                = "";
		Hashtable hashView = new Hashtable();
		while ((!tag.equals("</CE:EXAM-QUESTIONS>")) || (!tag.equals("</CE:EXAM-ANSWERS>")))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:SECTION>") || tag.startsWith("<CE:SECTION "))
				{
					bodyContents.append(processSection("</CE:SECTION>"));
				}
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					if (firstP== true)
					{
						bodyContents.append("\r\n"+xmlObj.extractData("</CE:PARA>", true));
					}
					else
					{
						bodyContents.append("\r\n\r\n"+xmlObj.extractData("</CE:PARA>", true));
					}
					firstP= false;
				}
				else if (tag.startsWith("<CE:ACKNOWLEDGMENT"))
				{
					bodyContents.append(xmlBody.processAcknowledgment());
				}
				else if (tag.equals("<CE:APPENDICES>"))
				{
					if(xmlObj.jid.equals("SECLAN")){//18-01-2005
						BodyGroup.append_seclan+="\r\n\\begin{appendix}"+xmlBody.processSections("</CE:APPENDICES>")+"\r\n\\end{appendix}";
					}
					else{
					bodyContents.append("\r\n\\begin{appendix}");
					bodyContents.append(xmlBody.processSections("</CE:APPENDICES>"));
					bodyContents.append("\r\n\\end{appendix}");
					}
				}
				/*else if (tag.equals("<CE:SECTION-TITLE>"))
				{
					//bodyContents.append(xmlObj.extractData("</CE:SECTION-TITLE>", true));
					System.out.println(xmlObj.extractData("</CE:SECTION-TITLE>", true));
					System.in.read();
				}*/
			}
		}
		//Added by Avinandan till end
		//mark
		if(xmlObj.jid.equals("MEMSCI"))
		{
			if(tempNomen.length()>0)
			{
				bodyContents.append("\r\n"+tempNomen);
			}
		}
		//end mark
		return bodyContents.toString();
	}
	public String processSection(String Tag)throws java.io.IOException
	{
		boolean firstP              = true;
		StringBuffer sectionContents= new StringBuffer();
		StringBuffer bodyContents   = new StringBuffer();
		String secNo                ="";
		String secTitle             = "";
		int sectionCount            = 0;
		String tag                  = "";
		String secId                = "";
		Hashtable hashView = new Hashtable();
		boolean head1				= false;
		boolean examsecView=false;
		
		System.out.println("EXAM processSection()");
		
		while (!tag.equals(Tag))
		{
			char ch= (char)fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:SECTION>") || tag.startsWith("<CE:SECTION "))
				{
					if (tag.indexOf("ID=")>0)
					{
						secId= tag.substring(tag.indexOf("ID=\"")+4, tag.indexOf(">")-1);
					}
					//added by avinandan
					/*if(tag.indexOf("VIEW=\"EXTENDED\"")>0)
					{
						while(!tag.equals("</CE:SECTION>"))
						{
							ch= (char)fin.read();
							if (ch=='<')
							{
								tag= xmlObj.getTag().toUpperCase();
							}
						}//end of while
						continue;
					}//end of if
					//end mark
					*/
					secNo    = "";
					secTitle = "";
					firstP   = true;

					sectionCount++;

					if (tag.indexOf("ROLE=")>0)
					{
						String temp= tag.substring(tag.indexOf("ROLE=\"")+6, tag.indexOf(">")-1);
						System.out.println(temp);
						/*if(temp.startsWith("MATERIALS-METHODS"))
						{
							sectionContents.append("\r\n\r\n\\begin{materialsandmethods}");
							hashView.put(""+sectionCount,sectionCount+"");

						}*/
						///////////////////////////////EXTRANET UPDATE//////////////////////////////////////////////////
						///		
								sectionContents.append("\r\n\r\n\\begin<SmallFont>"); //15-01-2005
								hashView.put(""+sectionCount,sectionCount+"");
						///		
						/////////////////////////////////////////////////////////////////////////////////////////////////		
						
					}
					else
					{
						sectionContents.append("\r\n");
					}

					if(tag.startsWith("<CE:SECTION ") && tag.indexOf("VIEW=\"EXTENDED\"")>0)
					{
						examsecView=true;
						sectionContents.append("\r\n\\begin{extra}");
					}


					if (sectionCount==1)
					{
						sectionContents.append("\r\n\\section{");
						head1=true;
					}
					else if (sectionCount==2)
						sectionContents.append("\r\n\\subsection{");
					else if (sectionCount==3)
						sectionContents.append("\r\n\\subsubsection{");
					else if (sectionCount==4)
						sectionContents.append("\r\n\\paragraph{");
					else if (sectionCount==5)
						sectionContents.append("\r\n\\subparagraph{");
					else if (sectionCount==6)
						sectionContents.append("\r\n\\subsubparagraph{");
//to be defined     else if (sectionCount==7)
//						sectionContents.append("\r\n\r\n\\subsubparagraph{");
//					else if (sectionCount==8)
//						sectionContents.append("\r\n\r\n\\subsubsubparagraph{");
					if(secId.length()>0)
						sectionContents.append(xmlObj.getHypertarget(secId));
					secId    = "";
				}
				else if (tag.equals("<CE:LABEL>"))
				{
					secNo= xmlObj.extractData("</CE:LABEL>", true);
					tag = xmlObj.getNextTag();
					if(!tag.startsWith("<CE:SECTION-TITLE"))
					{
						if(secNo.length()>0)
							sectionContents.append("\\SecNo{"+secNo+"}");
//						sectionContents.append(xmlObj.getHypertarget(secId));
						sectionContents.append("}");
						sectionContents.append("\r\n\\addbookmark"+xmlObj.bkmList.get(xmlObj.bkmCount++));
					}
				}
				else if (tag.startsWith("<CE:SECTION-TITLE"))
				{
					xmlObj.protectCheck=true;
					secTitle= xmlObj.extractData("</CE:SECTION-TITLE>", true);
//					System.out.println(secTitle);
					if(head1==true && XMLObjects.upperCaseSecJid.contains((Object)xmlObj.jid))
					{
						secTitle=secTitle.toUpperCase();						
					}
					if (firstP== true)
					{
						if(secNo.length()>0)
							sectionContents.append("\\Secno{"+secNo+"}");
						if(secId.length()>0)
							sectionContents.append(xmlObj.getHypertarget(secId));
						sectionContents.append(secTitle+"}");
//						System.out.println("secTitle : "+secTitle);
//						System.in.read();
//						System.out.println("BKM::"+xmlObj.bkmList.get(xmlObj.bkmCount++));
						if(head1==true && XMLObjects.upperCaseSecJid.contains((Object)xmlObj.jid))
						{
							sectionContents.append("\r\n\\addbookmark"+((String)xmlObj.bkmList.get(xmlObj.bkmCount++)).toUpperCase());
							head1=false;
						}
						else
							sectionContents.append("\r\n\\addbookmark"+xmlObj.bkmList.get(xmlObj.bkmCount++));
					}
					else
					{
						sectionContents.append(secTitle+"}");

					}
//					System.out.println("NEELS");
					///////////////////////////////EXTRANET UPDATE//////////////////////////////////////////////////
								int t=sectionContents.indexOf("\\begin<SmallFont>");
							if(t!=-1)
								sectionContents=sectionContents.replace(t,t+17,"\\begin{SmallFont}"+"{"+secTitle+"}");
					/////////////////////////////////////////////////////////////////////////////////////////////////		
					
					xmlObj.protectCheck=false;
				}
				else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
				{
					
					if (firstP== true)
					{
						sectionContents.append("\r\n"+xmlObj.extractData("</CE:PARA>", true));
					}
					else
					{
						sectionContents.append("\r\n\r\n"+xmlObj.extractData("</CE:PARA>", true));
					}
					firstP= false;
				}
				else if (tag.equals("</CE:SECTION>"))
				{
					if(hashView.contains(sectionCount+""))
					{
						///sectionContents.append("\r\n\\end{materialsandmethods}");
						///////////////////////////////EXTRANET UPDATE//////////////////////////////////////////////////
						///		
								sectionContents.append("\r\n\\end{SmallFont}"); //15-01-2005
						///		
						/////////////////////////////////////////////////////////////////////////////////////////////////		
						hashView.remove(sectionCount+"");
					}
					sectionCount--;
					//break;
					if(examsecView==true)
					{
						sectionContents.append("\r\n\\end{extra}");
						examsecView=false;
					}
				}
			}
		}
		return sectionContents.toString();
	}
}//end of class