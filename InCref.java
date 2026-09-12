//--------------------------------------------------------------------------------------
// File Name     : BiblographyContents.java
// Developed By  : Software Development [Thomson Digital]
// Last Modified : 06th OCt 2004
//--------------------------------------------------------------------------------------
//Make the class file inside the folder: tp->xt
package tp.xt;
import java.io.*;
import java.util.*;
import java.util.regex.*; 

//-------------------------------------------------------------------------------------
//Kishor [17/06/2004]
//For Global variables
class globalInC
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
	public static String isbn="";	
}
//--------------------------------------------------------------------------------------
class InCref
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
	String hostType="";
	String tempLabel=""; // 15-07-2004	
	boolean isEditor=false; //29-07-04
	String bsTitle=""; // 3-8-04
	String bsVol=""; //3-08-04
	String secTitle = new String();
	String ebTitle=""; // 6-8-04
	boolean mulHost = false; // 14-9-04
	String mulhostDate="";//14-9-04
	String In_CDate="";//5-10-04
	String In_CPub="";//5-10-04

	String check="";   // variables used for label problem in ref. style-5
	String check1="";
	String check2="";
	boolean lblsame=false;
	Hashtable bibTable;


	InCref(XMLObjects xmlObj)throws IOException
	{
		this.xmlObj  = xmlObj;
		bibDate      = "";
		artTitle     = "";
		artComment   = "";
		vnr			 = "";
		journalTitle = "";
		transTitle ="";
		secTitle="";
		bibTable=new Hashtable();
	}

	public static String bibTagatt1(String id) throws IOException
	{
		XMLObjects xmlObj = new XMLObjects();
		String sTag = new String();
		String sbRefID  = "";//Updated for sbRefID Web 6.4 updation 20-03-2013 by Vivek
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				
				if(sTag.startsWith("<SB:REFERENCE"))
				{
					//Updated for sbRefID Web 6.4 updation 20-03-2013 by Vivek
					sbRefID=xmlObj.getAttributeValue(sTag, "ID");
					//System.out.println("\nsbRefID===============>"+xmlObj.getAttributeValue(sTag, "ID"));
					//End of Web 6.4 updation
				}
			}		
		return sbRefID;
	}

	//--------------------------------------------------------------------------------------
	//Starting Bibliography from here. It is called for complete reference work ....
	public String artBibilographyInC(String tagType) throws IOException
	{
		int bibCount = 0;
		String sTag = new String();
		String authBib = new String();
		String bibref = new String();
		String bibid = new String();
		
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
						artTitleExist= false;
						authorExist=false;		
						
						authBib = authBib + bibTagInC(xmlObj.getAttributeValue(sTag, "ID"));
						bibCount++;
					}
					else if(sTag.startsWith("<CE:SECTION-TITLE"))
					{
						secTitle = xmlObj.extractData("</CE:SECTION-TITLE>", true);
						if (secTitle.equals("References") || secTitle.equals("Reference"))
						{
             			   secTitle="";
					
						}
						else
						{
					      secTitle="\r\n\\subsubsection{"+secTitle+"}\r\n\n";
						  
						
						}
					}
				}				
			}while(!sTag.equals("</"+tagType+">"));
			
			StringBuffer tempBib= new StringBuffer();
			System.out.println(" ");
			System.out.println("********************************************************");
			System.out.println("JID                  : "+xmlObj.jid);
			System.out.println("AID                  : "+xmlObj.aid);
			System.out.println("Ref Style            : "+xmlObj.bibStyle);
			System.out.println("Last Modify          : "+"30-08-2011");
			System.out.println("********************************************************");
			
				tempBib.append("\r\n\\begin{thebibliography}{["+bibCount+"]}");
				//tempBib.append("\r\\begin{thebibliography}{["+bibCount+"]}");
      			tempBib.append(authBib +"\\end{thebibliography}");
	    		authBib= tempBib.toString();				
	 	}
		catch(Exception exp)
		{
			exp.printStackTrace();
			System.exit(0);
		}
		
		return authBib;
	}
	//--------------------------------------------------------------------------------------
	

	private String bibTagInC(String id) throws IOException
	{
		//System.out.println("new BiblographyContents "+bibTagatt1("o9p"));
		String sTag = new String();
		String eTag = new String();
		String bib = new String();
		String no = new String();
		boolean first = false;
		boolean bibFlag = false;
		String temp = new String();
		String link = xmlObj.getHypertarget(id);
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
                 	tempLabel=no.substring(no.lastIndexOf(',')+2);  // 15-07-2004			
				}
				else if(sTag.startsWith("<SB:REFERENCE"))
				{
					journalTitle="";    
					if (bibFlag)
					{
						if (temp.endsWith(".")|| temp.endsWith("?")||temp.endsWith(","))
						{
							temp=temp.substring(0,temp.length()-1);
						}
						//bib = bib +temp+";\\\\\r\n";
						bib = bib +temp+";";//Gaurav Based On 83009.--13-10-2004
					}
					else
					bibFlag = true;
					temp = sbReference(first);
					first = true;
			   }
				else if(sTag.startsWith("<CE:OTHER-REF"))
				{
					if (bibFlag)
					{
						//bib = bib +temp+";\\\\\r\n";
						bib = bib +temp+"; ";//Gaurav Based On 83009.--13-10-2004
					}
					else
					{
						bibFlag = true;
					}					
					temp = processOtherRef();										
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
		
		bib = bib + temp;
		bib = bib.trim();
		if(bib.startsWith(". In: "))
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
				bib+=".";
			}
		}

		if (bib.endsWith(".."))
		{

			bib= bib.substring(0,bib.length()-2)+".";
		}

		if (bib.endsWith(",."))
		{

			bib= bib.substring(0,bib.length()-2)+".";
		}
		    bib ="\r\n\\bibitem{"+no+"}\r\n"+link+bib+"\r\n";
		           
		 link="";
		bib=secTitle+bib;
		secTitle="";

		String sbRefID = bibTagatt1("aaa");
		if(!sbRefID.equals(""))
		{
			if((bib.indexOf("\\url{"))==-1 && (bib.indexOf("\\doi{"))==-1 && (bib.indexOf("\\newtextdoi{"))==-1 && (bib.indexOf("\\newdoi{"))==-1)
			{
				boolean refno=false;
				String bibno=no;
				//System.out.println("No==>"+bibno);
				bibno=bibno.replaceAll("ThomsonDiff\\\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace","");
				//System.out.println("No==>"+bibno);
				if(bibno.startsWith("[")||bibno.startsWith("("))
				{
					refno=Character.isDigit(bibno.charAt(1));
				}
				else
				{
					refno=Character.isDigit(bibno.charAt(0));
				}
		
				if(refno)
				{
					bib ="\r\n\\linkRefId{"+sbRefID+"}\\global\\linkoccurtrue"+bib.trim();
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
							//System.out.println("NoYear==> "+noyear);
							Pattern p2 = Pattern.compile(sbyear);
							Matcher m2 = p2.matcher(bib);
							if(m2.find())
							{
								//System.out.println("bib1111 "+bib);
								//System.out.println("Year "+sbyear+" found in reference");
								bib=bib.replaceFirst("\\. \\.","\\.");
								bib=bib.replaceFirst(sbyear,"<<\\\\sbyear>>");
								bib=bib.replaceFirst(sbyear+"([^ ]+) ",sbyear+"$1\r\n \\\\linkRefsubId{"+sbRefID+"}\r\n ");
								bib=bib.replaceFirst("<<\\\\sbyear>>",sbyear);
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
					if(otherRefLabel.length()>0)
					{
					      otherRef+=otherRefLabel+" ";
					}

					otherRef= otherRef+xmlObj.extractData("</CE:TEXTREF>", true);
				}
			}
		}
		return otherRef;
	}
//-------------------------------------------------------------------------------------------------------
	//Working for <sb:reference>.........</sb:reference>
	public String sbReference(boolean first) throws IOException
	{
		globalInC.pub_for_ref5="";    // 27-07-04
		globalInC.pub_for_ref5Manage="";
		String sTag = new String();		
		String bb = new String();
		boolean comment_after_host = false;//[17-07-04 ----- Rajeev &Gaurav ]
		String firstComment=""; // 27-07-04
		isEditor=false; //29-07-04
		artTitle="";
		bsTitle="";
		bsVol="";
		ebTitle="";
		artTitleExist=false;
		authorExist= false; // 31-08-04
		globalInC.isbn="";
		firstComment="";  // 27-07-04
		globalInC.cont_avail="no";
		int hostCount = 0;
		isMultipleEditors= false;
		mulHost = false;
		mulhostDate="";
		In_CDate="";
		In_CPub="";
		
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
				else if(sTag.equals("<SB:CONTRIBUTION>") || sTag.startsWith("<SB:CONTRIBUTION "))
				{
					bb=bb+firstComment+" ";  // 27-07-04
					globalInC.cont_avail="yes";
					isContribution = true;					
					bb = bb + sbContribution();
                   	firstComment="";  // 27-07-04
					
				}
				//---------------------------------------------------------------------------------------
				else if(sTag.equals("<SB:HOST>"))
				{
					isContribution=false;
					hostCount++;
					isMultipleEditors=false;
					sTag = bibHost();
					//System.out.println("sTag -->"+sTag);
					if(sTag.startsWith(" ,"))
					{
						sTag = sTag.substring(1);
					}
					else if(sTag.startsWith(" ?, in:")||sTag.startsWith("?, in:"))//05-08-2004----
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
					if(bb.endsWith(". ") && sTag.startsWith(",") && xmlObj.bibStyle.equals("In-C")) // [05-08-04]
					{
							bb = bb.substring(0,bb.length()-2);
					}
					if(bb.endsWith(".") && sTag.startsWith(",") && xmlObj.bibStyle.equals("In-C")) // [05-08-04]
					{
							bb = bb.substring(0,bb.length()-1);
					}
					if(bb.endsWith(".") && sTag.startsWith(".")) // [05-08-04]
					{
							sTag = sTag.substring(1);
					}
					if ((bb.endsWith("?")) && sTag.startsWith(",")) // Rajeev-- 20-8-2004--On Sangita FeedBack For SNB 8009--
					{
						sTag=sTag.substring(1);						
					}
					
					bb = bb + sTag;
                	comment_after_host=true; //[17-07-04 ----- Rajeev]
				}
   //-------------------------------------------------------------------------------------------------
				else if(sTag.equals("<SB:COMMENT>"))
				{
					String temp="";
					temp=xmlObj.extractData("</SB:COMMENT>", true);
					//System.out.println("temp--------   "+temp);
					firstComment=temp;  // 27-07-04
	//-------------------------------------------------------------------------------------------------	
					if (xmlObj.bibStyle.equals("In-C")) 
					{
						if(isContribution == true)
						{
						artComment = temp;
						if(comment_after_host == false)
						{	
				    	if(artComment.startsWith("[") || artComment.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
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
						if(artComment.startsWith("[") || artComment.startsWith("("))  //Rajeev & Gaurav --19-07-04--[When Comment doesn't start with parenth or sq. bracket]
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
						/*if(globalInC.isbn.length()>0)  // 29-07-04
						{
							bb+=", "+"ISBN "+globalInC.isbn;
						}
						globalInC.isbn="";*/
						temp="";
						isContribution = false;
						}
					}					

		//---------------------------------------------------------------------------
					
					//if(temp.length()>0)
					if(comment_after_host==true)
					{
						if(bb.endsWith(";")||bb.endsWith("."))
						{
							bb = bb.substring(0,bb.length()-1);
						}
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
							}
						}
						else
						{
							bb = bb +" "+temp+" ";

						}
					}
				}

			}
		}while(!sTag.equals("</SB:REFERENCE>"));
		int index=0;
		while(true)
		{
			index = bb.indexOf("</SB:COMMENT>");
			//System.out.println("temp--------   "+bb);
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
		}
			
		return bb;
	}
	//---------------------------------------------------------------------------------------------------

	private String sbContribution() throws IOException
	{
		boolean first = false;
		String sTag = new String();
		String con = new String();
		transTitle="";
		artTitle="";
		do
		{
			char ch= (char)xmlObj.fin.read();
			if(ch == '<')
			{
				sTag= xmlObj.getTag().toUpperCase();

				if(sTag.equals("<SB:AUTHORS>"))
				{					
					authorExist=true;
					con = con + authorsTag(first);
					first = true;
				}
				else if(sTag.equals("<SB:TITLE>"))
				{
					artTitle = getBibTitle("</SB:TITLE>");
					artTitleExist= true;
				}
				else if(sTag.equals("<SB:TRANSLATED-TITLE>"))
				{
					transTitle = getBibTitle("</SB:TRANSLATED-TITLE>");
				}
				else if(sTag.equals("<SB:COMMENT>"))//Comment inside the contribution
				{
					artComment = xmlObj.extractData("</SB:COMMENT>", true);
					//System.out.println("temp--------   "+artComment);
					
				}
			}
			
		}while(!sTag.equals("</SB:CONTRIBUTION>"));
		//--------------------------------------------------------------------------------------				
		if ((xmlObj.bibStyle).equals("In-C"))
		{
			artTitle="";
			con=con+artTitle;
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

			// con+=artComment;
			artTitle = "";
			transTitle="";
			artComment = "";
		}		
		
		return con;
	}
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
					bibTitle.append(xmlObj.extractData("</SB:MAINTITLE>", true));
				}
				else if (tag.equals("<SB:SUBTITLE>"))  // [Imp. Note: CE: Feedback [Ajay & Usmani. In any case Title & Subtitle would be separated by "." instead of ":". In case of colon in MSS they would give it in BT]-27/07/04
				{
					if(!bibTitle.toString().endsWith(".")&&!bibTitle.toString().endsWith("?")&&!bibTitle.toString().endsWith("!"))
					{
						bibTitle.append(". "+xmlObj.extractData("</SB:SUBTITLE>", true));
						
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
		int ch = 0;
		int auths = 0;		
		do
		{
			ch =(char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag= xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:AUTHOR>"))
				{
					if (firstAuthor)
					{
							
							authors = authors + temp+", ";
												
					}
					else
					{
						firstAuthor = true;
					}
					temp = bibAuthorList("</SB:AUTHOR>");
					
					auths++;
				}
				else if(sTag.equals("<SB:ET-AL/>"))
				{
						
					  
					  
						authors = authors + temp +", ";
						temp = "et al.";
					  
						
				}
				else if(sTag.equals("<SB:COLLABORATION>"))
				{
					if(temp.length()>0)
					{
						authors = authors + temp+", ";
					}
					//Kishor [19/06/2004]-Collaboration inside the authors
					collab_text=xmlObj.extractData("</SB:COLLABORATION>", true);

													
						authors = authors + collab_text;					
					
					temp="";					
				}		
			}			
		}while(!sTag.equals("</SB:AUTHORS>"));
		
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------
		if ((xmlObj.bibStyle).equals("In-C"))
		{
			//Kishor [18/06/2004]
			if (auths >1)//More author then and before last author.
			{
				if (temp.equals("et al."))
				{
					authors = authors +" "+ temp;
				}
				else
				{
					if (temp.length()>0)
					{
						if (auths ==2)
						{
								authors= authors.substring(0,authors.length()-2);
								authors = authors +" "+"and "+ temp;			

						}
						else
						{
							authors = authors +"and "+ temp;			

						}
							
							//authors = authors +"and "+ temp;						
					}					
					if ( ! authors.endsWith(".")) 
				   {
				      authors +=",";//Gaurav Based On 83009				
				   }				
				}

			}
			else
			{			       
				authors = authors +temp;								
				if ( ! authors.endsWith(".")) 
				{
				      authors +=",";//Gaurav Based On 83009								
				}				
			}
		}
		return authors;
	}
//-----------------------------------------------------------------------------	
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
					note = note +"\r\n"+xmlObj.extractData("</CE:SIMPLE-PARA>", true);
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
		String doi = new String();
		int ch = 0;
		globalInC.dkj_edited_book="";
		globalInC.dkj_book_series="";
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
				}
				else if(sTag.equals("<SB:BOOK>"))//Book Case
				{
					hostType= sTag;

					

					if(xmlObj.bibStyle.equals("1a")||xmlObj.bibStyle.equals("In-C")) // 23-07-04 [Gaurav/rajeev when CT & BT coming sequentially then for removing space]
					{
					host= host+""+bibBook();
					
					}
					else
					{
						host= host+" "+bibBook();
						
					}
			
				}
				else if(sTag.equals("<SB:EDITED-BOOK>"))//Edited Book Case
				{
					globalInC.dkj_edited_book="yes";
					hostType= sTag;
					sTag=bibEditedBook();
					//System.out.println("Host-"+host);
					//System.out.println("sTag-"+sTag);
					host= host+sTag;
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
					doi = xmlObj.extractData("</CE:DOI>", true);
				}
				else if(sTag.equals("<SB:PAGES>"))
				{
					page = bibPages();
				}
			}
		}
		

		//------------------------------------------------------------------------------
		 if((xmlObj.bibStyle).equals("In-C"))
		{
			if ((hostType.equals("<SB:ISSUE>")))
			{
				if(page.length()>0)
					host+=" "+page;
				if (In_CDate.length()>0)				
					host+=" ("+In_CDate+")";				
				vnr="";
			}
			else if ((hostType.equals("<SB:EDITED-BOOK>")))
			{
				if (page.length()>0)
				{
					if(page.indexOf("ndash")>0)
						host+=", pp. "+page;
					else if(host.endsWith(", "))
					{
						host=host.substring(0,host.length()-2);
						host+=", p. "+page;
					}
					else
					{
					host+=", p. "+page;
					}
					vnr="";
				}
				if(!host.endsWith("."))
					host=host+". ";
				if (In_CPub.length()>0)				
					host+=In_CPub;				
				if (In_CDate.length()>0)				
				{
					if(host.endsWith(". "))
						host=host.substring(0,host.length()-2);
					host+=", "+In_CDate;				
				}
			}
			else
			{
				host+=page;
			}
			
		}
	//--------------------------------------------------------------------------------------		
		
		
				
		if(doi.length()>0)
		{
			if(!host.endsWith(",")|| !host.endsWith("."))
				host+=",";
			host+=" doi:"+doi;
			doi="";
		}
		vnr="";
		page="";
		return host;
	}
	//--------------------------------------------------------------------------------------
	
	private String bibIssue() throws IOException
	{
		String issue = new String();
		String sTag = new String();
		String edit = new String();
		String title = new String();
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
				}
				else if(sTag.equals("<SB:CONFERENCE>"))
				{
					conf = xmlObj.extractData("</SB:CONFERENCE>", true);
					//System.out.println("con----------"+conf);
				}
				else if(sTag.equals("<SB:SERIES>"))
				{
					ser = bibSeries();
				}
				else if(sTag.equals("<SB:ISSUE-NR>"))
				{
					issnr = xmlObj.extractData("</SB:ISSUE-NR>", true);
				}
				else if(sTag.equals("<SB:DATE>"))
				{
					if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
					{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
					}
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
	
		//----------------------------------------------------------------------------
		if((xmlObj.bibStyle).equals("In-C"))
		{
			if(edit.length()>0)
			{	if(authorExist== true)
				{ 
					issue=", in: ";
				}
				issue+=edit;
				if(isMultipleEditor==true)
				{
                   if (globalInC.cont_avail.equals("yes") && title.length()==0)//26-07-04----Gaurav & Rajeev
					{
					   issue+=", eds.)";
					}
					else
					{
						issue+=", ed.)";
					}
					
				}
				else
				{
					issue+=", ed.)";
				}
			}
			if(title.length()>0)
			{
				title="{\\it "+title+"}";
				if(edit.length()>0)//20-08-04 Gaurav-- Based on MASPEC 12785---
				{					
					issue+=" "+title;
					
				}
				else
				{
					issue+=", in: "+title; 
				}
				
			}
			if(conf.length()>0)
			{
				issue+=", "+conf;
			}			
			issue+=ser;
			if(issnr.length()>0)
				issue+=" ("+issnr+"),";//Gaurav 13-10-04--Based On 83001
			else
			//if (globalInC.dkj_bSer.equals("no")) 
			if (vnr.length()==0)
			{
				issue+=",";
			}
			else
			{
				issue+="{\\bf{,}}";
			}
				
			if(date.length()>0)
				In_CDate=date;				
		}
		
		return issue;
	}
	//-----------------------------------------------------------------------------------------
	private String bibSeries() throws IOException
	{
		String series = new String();
		String sTag = new String();
		while(!sTag.equals("</SB:SERIES>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:TITLE>"))
				{
					journalTitle = getBibTitle("</SB:TITLE>");
					bsTitle=journalTitle;
				}
				else if(sTag.equals("<SB:VOLUME-NR>"))
				{
					vnr = xmlObj.extractData("</SB:VOLUME-NR>", true);
					bsVol=vnr;
				}
			}
		}
	//-----------------------------------------------------------------------------------
		if(hostType.equals("<SB:ISSUE>"))
		{
			if ((xmlObj.bibStyle).equals("In-C"))
			{
				if (journalTitle.length()>0)
				{
					journalTitle="{\\it "+journalTitle+"}";
					series=" "+journalTitle;					
					if (vnr.length()>0)
					{
						vnr="{\\bf "+vnr+"}";
						series+=" "+vnr;
					}
					
				}
			}
		
		}
		//---------------------------------------------------------------------------------
		else if(hostType.equals("<SB:EDITED-BOOK>") || hostType.equals("<SB:BOOK>"))
		{			
			
		 if ((xmlObj.bibStyle).equals("In-C"))
			{
				if (journalTitle.length()>0)
				{								
					
					series+=" {\\it in}  \""+journalTitle+"\"";	//Gaurav 14-10-04//Fpo Chapter 2.
					if (vnr.length()>0)
					{
						//System.out.println("vnr-"+vnr);
						series+=", "+vnr;
					}
						
				}
			}
				
		}
		return series;
	}
//-------------------------------------------------------------------------------------

	private String bibPages() throws IOException
	{
		String pages = new String();
		String sTag = new String();
		String temp1=new String();
		String temp2=new String();

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
	//------------------------------------------------------------------------------------------------
		if(xmlObj.bibStyle.equals("In-C"))
		{
			if(temp1.length() >0)
				pages=temp1;
			if(temp2.length() >0)
				pages = pages +"{\\ndash}"+temp2;
		}
		return pages;
	}
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
		
		//-------------------------------------------------------------------------------------------
		if((xmlObj.bibStyle).equals("In-C"))
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
			
			if(snm.length()>0)
				temp += " ";
			temp += snm;
			if(jnm.length()>0)
				temp += " ";
			temp+=jnm;
			if(jr.length()>0)
			{
				if(jr.indexOf("jr")!=-1)
					temp+=", ";
				else
					temp += " ";
			}
			temp+=jr;
		}
		
		return temp;
}	
//------------------------------------------------------------------------------------------

	private String sbEditors() throws IOException
	{
		String editors = new String();
		String sTag = new String();
		String editor = new String();
		String etAl = new String();
		String last_editor=new String();
		int edit_cnt;
		edit_cnt=0;
		globalInC.for_editor="yes";
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
							
						
						editor = editor+", "+last_editor;
						
					}
					edit_cnt=edit_cnt+1;
					if(editor.length()>0)
					{
						isMultipleEditors=true;
					}
					     last_editor=bibAuthorList("</SB:EDITOR>");
						
				}
				else if(sTag.equals("<SB:ET-AL/>"))
				{
					etAl = "et al.";
					isMultipleEditors=true;
				}
				else if(sTag.equals("</SB:EDITORS>")) 
				{
					
					
					{
						if(edit_cnt==1)
						{
							editor = editor+""+last_editor;//Gaurav 14-10-2004 InCase of single editors;

						}
							
    					else if(edit_cnt==2)
						{
							editor = editor+" and "+last_editor;
						}
						else
						{
							editor = editor+", and "+last_editor;
						}
						
						//editor = editor+", "+last_editor;
					}
				}
			}
		}

		if (etAl.length()>0)
		{
			
			
				editors = editor +", "+ etAl;//Ex:Ref-Style 3[JVAC]
						
		}
		else
		{
			editors = editor + etAl;
		}
		//-------------------------------------------
		if(editors.startsWith(", ")||editors.startsWith("; "))
		{
			editors=editors.substring(2);
		}
		editors=editors;
			
		globalInC.for_editor="no";
		
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
        while(!sTag.equals("</SB:BOOK>"))
		{
			char ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:TITLE>"))
				{
					title = getBibTitle("</SB:TITLE>");					
				}
				else if(sTag.equals("<SB:EDITION>"))
				{
					edition = edition + xmlObj.extractData("</SB:EDITION>", true);
				}
				else if(sTag.equals("<SB:BOOK-SERIES>"))
				{
					bSeries = bibBookSeries();
				}
				else if(sTag.equals("<SB:DATE>"))
				{
									
					date = xmlObj.extractData("</SB:DATE>", true);
					if(tempLabel.length()>0)  // 15-07-2004
					{
						if(tempLabel.startsWith(date))
							date=tempLabel;
						tempLabel="";
					}
				}			
				else if(sTag.equals("<SB:PUBLISHER>"))
				{
					pub = sbPublisher();
				}
				else if(sTag.equals("<SB:ISBN>"))
				{
					globalInC.isbn = xmlObj.extractData("</SB:ISBN>", true);
				}
			}
		}
		if (xmlObj.bibStyle.equals("In-C"))
		{			
		    book+= artTitle;
			if(title.length()>0)
			{
				if(globalInC.cont_avail.equals("yes")&&(bSeries.length()>0))
				{
					
				book+=", "+title;//24-07-04 Gaurav(When CT and BT is Given and  with no Editors)
				}
				 else if(globalInC.cont_avail.equals("yes")&&(bSeries.length()==0))
				{
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
					book+=", "+pub;
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

			if(globalInC.isbn.length()>0)  // 01-08-04--Gaurav --Based on Pawn FeedBack For Chroma 344125.
			{
				book+=", "+"ISBN "+globalInC.isbn+".";
			}

		}
		
		artTitle="";
		title="";
		return book;
	}
	//-------------------------------------------------------------------------------------

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
		if ((xmlObj.bibStyle).equals("In-C"))
		{
			pub+= name;
			if(loc.length()>0)
				pub+=", "+loc;
		}
		
		return pub;
	}
	//--------------------------------------------------------------------------------------------------------

	private String bibEditedBook() throws IOException
	{
		String editedBook = new String();
		String eTag = new String();
		String sTag = new String();
		String editors = new String();
		String edition = new String();
		String title = new String();
		String con = new String();
		String bSer = new String();
		Vector date = new Vector();
		String pages = new String();
		String pub = new String();		
		String dkj_temp1=new String();
		String dkj_temp2=new String();

		boolean isMultipleEditor =false;
		int ch = 0;
		
		while(!sTag.equals("</SB:EDITED-BOOK>"))
		{		
			ch = (char) xmlObj.fin.read();
			if(ch =='<')
			{
				sTag = xmlObj.getTag().toUpperCase();
				if(sTag.equals("<SB:EDITORS>"))
				{					
					isEditor=true; //29-07-04
					editors = sbEditors();
					
					isMultipleEditor=isMultipleEditors;
					isMultipleEditors=false;					
				}
				else if(sTag.equals("<SB:EDITION>"))
				{
					edition = xmlObj.extractData("</SB:EDITION>", true);
				}
				else if(sTag.equals("<SB:TITLE>"))
				{
					title = getBibTitle("</SB:TITLE>");
					ebTitle=title;
				}
				else if(sTag.equals("<SB:CONFERENCE>"))
				{
					con = xmlObj.extractData("</SB:CONFERENCE>", true);
					//System.out.println("con----------"+con);
				}
				else if(sTag.equals("<SB:BOOK-SERIES>"))
				{	//Edited Book Series Title [BookSeries inside EditedBook]
					globalInC.dkj_book_series="yes";
					bSer = bibBookSeries();
				}
				else if(sTag.equals("<SB:DATE>"))
				{
					String  temp= new String();
					if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
					{
						date.add( xmlObj.extractData("</SB:DATE>", true)); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5]
					}
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
					//System.out.println("date-------------"+date);
				 }
			}
				else if(sTag.equals("<SB:PAGES>"))
				{
					pages = bibPages();
				}
				else if(sTag.equals("<SB:PUBLISHER>"))
				{
					pub = sbPublisher();
				}
				else if(sTag.equals("<SB:ISBN>"))
				{
					globalInC.isbn = xmlObj.extractData("</SB:ISBN>", true);
				}
			}
		}
		int index=0;
		String tmp="";
						
		if((xmlObj.bibStyle).equals("In-C"))
		{	

			if (title.length()>0)
				tmp+=", {\\it in}  \""+title+"\"";			
			if(con.length()>0)
			{
				tmp+=", "+con;
			}
			if(edition.length()>0)
			{
				tmp+=", "+edition+"";
			}
			if(editors.length()>0)
			{				
				tmp+= " ("+editors;//Gaurav 
				if(isMultipleEditor==true)
				{
					tmp+=", eds.)";
				}
				else
				{
					tmp+=", ed.)";
				}
			}

			if(bSer.length()>0)
			{				
			   tmp+=bSer;			  
			}
			if(pub.length()>0)
			{
				In_CPub=pub;
			}			
			if(date.size()==2)
			{
				In_CDate=date.get(0).toString()+"{\\ndash}"+date.get(1).toString();
			}
			else
			{
				In_CDate=date.get(0).toString();
			}
			if(globalInC.isbn.length()>0)  // 29-07-04
			{
					tmp+=", "+"ISBN "+globalInC.isbn;
			}
			globalInC.isbn="";

		}
		
		return tmp;
	}
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
					isMultipleEditor=isMultipleEditors;
					isMultipleEditors=false;
				}
				else if(sTag.equals("<SB:SERIES>"))
				{
					ser = bibSeries();
					
				}
			}
		}//End of while
		
		if(hostType.equals("<SB:EDITED-BOOK>"))
		{		
			if((xmlObj.bibStyle).equals("In-C"))
			{
				
				if(bsTitle.length()>0)
				{					
						bookSeries+=" {\\it in}  \""+bsTitle+"\"";
				}
				if(editors.length()>0)
				{
					if (globalInC.cont_avail.equals("yes"))
					{						
						bookSeries+= " ("+editors;//--20-08-04, Gaurav Based On CEJ 4414---- 						
					}
					else
					{
						bookSeries+= editors;
					}
					if (globalInC.cont_avail.equals("yes"))
					{
						if(isMultipleEditor==true)
							bookSeries+=", eds.1111)";
						else
							bookSeries+=", ed.)";
					}
					else
					{
						if(isMultipleEditor==true)
							bookSeries+=", eds.)";
						else
							bookSeries+=", ed.)";
					}
					
					bookSeries+=", ";
				}
				if(bsVol.length()>0)
					bookSeries+=bsVol;



			}			
		}
		else if(hostType.equals("<SB:BOOK>"))
		{			
			if((xmlObj.bibStyle).equals("In-C"))
			{
				if(editors.length()==0 && artTitleExist==true)
				{
					bookSeries+=",  in: ";//14-08-04(Gaurav & Rajeev---Based on Geeta FeedBack Of Chroma 344011)
				}
              
				if(editors.length()>0)
				{
					if (globalInC.cont_avail.equals("yes") && artTitleExist==true)
					{						

						bookSeries+=",  in: "+editors;//----Gaurav --Based on Chroma 344042.------
					}
				    else if (globalInC.cont_avail.equals("yes") && artTitleExist==false)//----Based on Chroma 344139.--31-08-04.------
					{						

						bookSeries+=" in: "+editors;//----Based on Chroma 344042.------
					}
					else
					{
						bookSeries+= editors;
					}	
					
					if(isMultipleEditor==true)
					{
						bookSeries+=", Eds.)";
					}
					else
					{
						bookSeries+=", Ed.)";
					}
					bookSeries+=", ";
				}
				if(ser.length()>0)
				{
					bookSeries+=ser;
				}
			}
		}
			
		
		return bookSeries;
	}//End of public
//----------------------------------------------------------------------------------------
public String removeJrnlDot(String jrnl)
{
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
	return jrnl;
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
				System.out.println("Hello inCref.java eHost()...");
				String ref= xmlObj.getAttributeValue(sTag, "XLINK:HREF").toLowerCase();
				String versionUrl= xmlObj.getAttributeValue(sTag, "VERSIONURL").toLowerCase();
				System.out.println("VERSIONURL::"+versionUrl);
				String versionDate= xmlObj.getAttributeValue(sTag, "VERSIONDATE").toLowerCase();
				versionDate = xmlObj.updateVersionFormat(versionDate);
				System.out.println("VERSIONDATE::"+versionDate);
				String urlStr= xmlObj.extractData("</CE:INTER-REF>", true);
				System.out.println("::urlStr :: "+urlStr);
				if(!versionUrl.isEmpty() && !versionDate.isEmpty()){
					url= " "+"\\mychar\\url{"+xmlObj.getAttributeValue(sTag1, "XLINK:HREF".toLowerCase())+"}{"+urlStr+"}{} (\\mychar\\url{"+versionUrl+"}{web archive link}{} "+versionDate+")";
				}else{
				url= " "+"\\mychar\\url{"+xmlObj.getAttributeValue(sTag1, "XLINK:HREF".toLowerCase())+"}{"+urlStr+"}{}";
				}
				if(ref.startsWith("http") || ref.startsWith("mailto") || ref.startsWith("ftp")){}
				else
					url = " "+urlStr;
			}
			else if(sTag.equals("<SB:DATE>"))
			{
				if(url.length()>0)
					url+=", ";
				url+= xmlObj.extractData("</SB:DATE>", true);
				if((xmlObj.bibStyle).equals("5")||(xmlObj.bibStyle).equals("IChemE")||(xmlObj.bibStyle).equals("5-Manage")||(xmlObj.bibStyle).equals("5-Asw")||(xmlObj.bibStyle).equals("5-BookB")||(xmlObj.bibStyle).equals("DDT-NS"))
				{
						date = xmlObj.extractData("</SB:DATE>", true); // [Ce:feedback- No 2003a or 2003b in case of date in ref. style 5 - 27-07-04]
				}
				else
				{
				if(tempLabel.length()>0) // 15-07-2004
					{
						if(tempLabel.startsWith(date))
							date=tempLabel;
						tempLabel="";
					}
				}
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
}


	