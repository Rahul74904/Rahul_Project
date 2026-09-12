package tp.xt;
import java.util.*;
import java.io.*;
class ExtrSecTitl
{
	RandomAccessFile fin;
	ArrayList bkmList;
	File pitDBF;
	private StringBuffer atl;
	int secCounter = 0;
	boolean interRefFlag=false;
	//See Bookmark Specifications
	boolean bdyFlag;
	boolean pitok;
	boolean secFound;
	boolean atlFound;
	String jid;
	String aid;
	String pii;
	String doi;
	boolean isDOI;


	public ExtrSecTitl(String file, String pitFile) throws FileNotFoundException , IOException
	{
		fin = new RandomAccessFile(file,"r");
		pitDBF=new File(pitFile);
		bkmList = new ArrayList();
		atl = new StringBuffer();
		secCounter = 0;
		bdyFlag=false;
		pitok=false;
		secFound=false;
		jid=new String();
		aid=new String();
		pii=new String();
		doi=new String();
		isDOI= false;
	}

	public String formatPII()
	{
		String titleStr= new String();
		String artPii=pii;
		if (isDOI== true)
		{
			titleStr="[/Title (doi:10.1016/"+artPii.substring(0, 5)+"-"+artPii.substring(5, 9)+"\\("+
						artPii.substring(9, 11)+"\\)"+artPii.substring(11, 16)+"-"+
						artPii.charAt(16)+") /Creator (Elsevier) /DOCINFO pdfmark";
		}
		else
		{
			titleStr="[/Title (PII: "+artPii.substring(0, 5)+"-"+artPii.substring(5, 9)+"\\("+
						artPii.substring(9, 11)+"\\)"+artPii.substring(11, 16)+"-"+
						artPii.charAt(16)+") /Creator (Elsevier) /DOCINFO pdfmark";
		}
		return titleStr;
	}

	public boolean bkmRequired()
	{
		if((bdyFlag)&&(pitok)&&(secFound))
			return true;
		return false;
	}

	private String getTag(RandomAccessFile fin)throws IOException
	{
		int i=0;
		char ch;
		StringBuffer tag=new StringBuffer();
		tag.append("<");
		do
		{
			i=fin.read();
			ch=(char)i;
			tag.append(ch);
		}while(ch!='>');
		//System.out.println("======>>"+tag);
		//System.in.read();
		return (tag.toString()).toUpperCase();
	}

	public void skipmatter(String tag, RandomAccessFile fin) throws IOException
	{
		String endtag="";

		if(tag.startsWith("<CE:FIGURE"))
			endtag= "</CE:FIGURE>";
		else if(tag.startsWith("<CE:INLINE-FIGURE"))
			endtag= "</CE:INLINE-FIGURE>";
		else if(tag.startsWith("<CE:FORMULA"))
			endtag= "</CE:FORMULA>";
		else if(tag.startsWith("<CE:ENUNCIATION"))
			endtag= "</CE:ENUNCIATION>";
		else if(tag.startsWith("<CE:LIST"))
			endtag= "</CE:LIST>";
		else if(tag.startsWith("<CE:DEF-LIST"))
			endtag= "</CE:DEF-LIST>";
		else if(tag.startsWith("<CE:TEXTBOX"))
			endtag= "</CE:TEXTBOX>";
		else if(tag.startsWith("<CE:E-COMPONENT"))
			endtag= "</CE:E-COMPONENT>";
		else if(tag.startsWith("<CE:TABLE"))
			endtag= "</CE:TABLE>";


		while(!tag.equals(endtag))
		{
			char ch = (char) fin.read();
			if(ch == '<')
			{
				tag = getTag(fin);
				if(tag.equals(endtag))
				{
					break;
				}
			}
		}
	}

	public void handleSection (RandomAccessFile fin , int Count) throws IOException
	{
		String section = new String();
		char ch = 0;
		String tag = new String();
		int secCounter = 0;//secCounter;
		StringBuffer secNo = new StringBuffer();
		StringBuffer secSt = new StringBuffer();
		int list_size = bkmList.size();
		while(true)
		{
			ch = (char) fin.read();
			//System.out.println("CH : "+ch);
			if(ch == '<')
			{
				tag = getTag(fin);
				if(tag.equals("</CE:SECTIONS>") || tag.equals("</CE:APPENDICES>"))
						break;
				//System.out.println("tag (handle section "+Count+"): "+tag);
				if(tag.startsWith("<CE:FIGURE") || tag.startsWith("<CE:INLINE-FIGURE") ||
					tag.startsWith("<CE:FORMULA")|| tag.startsWith("<CE:ENUNCIATION") ||
					tag.startsWith("<CE:LIST") || tag.startsWith("<CE:DEF-LIST") ||
					tag.startsWith("<CE:TEXTBOX") || tag.startsWith("<CE:E-COMPONENT") ||
					tag.startsWith("<CE:TABLE"))
				{
					skipmatter(tag, fin);
				}
			  	else if(tag.equals("<CE:LABEL>"))
			  	{
					secNo= new StringBuffer();
					while(true)
					{
				 		ch = (char) fin.read();
				  		if(ch == '<')
				   		{
							tag = getTag(fin);
						  	if(tag.equals("</CE:LABEL>"))
							{
						  		break;
							}
						}
						else
						{
							secNo.append(ch);
						}
					}
				}
				else if(tag.startsWith("<CE:SECTION-TITLE"))
				{
					if (secFound==true)
					{
						if (secSt.length()==0)
						{
							while(true)
							{
								ch = (char) fin.read();
								if(ch == '<')
								{
									long file = fin.getFilePointer()-1;
									tag = getTag(fin);
									if((tag.equals("</CE:SECTION-TITLE>"))||(tag.startsWith("<CE:SECTION"))||(tag.startsWith("<CE:PARA")))
									{
//										System.out.println("Neels::"+secSt);
										fin.seek(file);
										break;
									}
									else if(tag.startsWith("<CE:FOOTNOTE"))
									{
										while(!tag.equals("</CE:FOOTNOTE>"))
										{
											ch = (char) fin.read();
											if(ch=='<')
											{
												tag=getTag(fin);
											}
										}
									}
								}
								//added
								//by
								//avinadan
								else if(ch == '%')
									secSt.append("\\"+ch);
								//end
								//mark
								else
								{
									secSt.append(ch);
								}
							}						
						}
					}
					//System.out.println(secSt);
				}
				else if(tag.startsWith("<CE:SECTION"))
				{
					secCounter++;
					handleSection(fin , Count+1);
				}
				else if(tag.equals("</CE:SECTION>"))
				{
					break;
				}
				else if(tag.equals("</CE:SECTIONS>"))
				{
					break;
				}
			}
		}
		/*
		* If Section title (<CE:SECTION-TITLE>) is there ,Section Title is Book Mark
		* otherwise <CE:LABEL> is Book Mark
		* section variable contains section information and its indent
		* Example
		* {section information}{indent}
		*/
		if(secSt.length() > 0)
		{
			if(secCounter>0)
				section ="{"+secCounter+"}{"+secSt+"}";
			else
				section ="{}{"+secSt+"}";
		}
		else if(secNo.length() > 0)
		{
			if(secCounter>0)
				section ="{"+secCounter+"}{"+secNo+"}";
			else
				section ="{}{"+secNo+"}";
		}
		//System.out.println(list_size +"---"+ section);
		bkmList.add(list_size, section);
	}

	public boolean checkPIT(String pit)throws IOException
	{
		if (!pitDBF.exists())
		{
			System.out.println("ERROR : One or More Source File(s) Missing");
			System.exit(0);
		}
		RandomAccessFile fin=new RandomAccessFile(pitDBF, "r");
		String lineStr="";
		boolean res=false;
		while ((lineStr=fin.readLine())!=null)
		{
			if (lineStr.startsWith(pit))
			{
				res=true;
				break;
			}
		}
		return res;
	}

	public void processML() throws IOException
	{
		char ch = 0;
		String tag = new String();
		boolean bdyCheck = false;

		/*
		* Move the File Pointer to <ATL> tag for processing Article title
		*/
		while(!tag.equals("</ARTICLE>") && !tag.equals("</SIMPLE-ARTICLE>") && !tag.equals("</BOOK-REVIEW>") && !tag.equals("</EXAM>"))
		{
			ch =(char)fin.read();
			if (ch=='<')
			{
				tag = getTag(fin);
				//System.out.println("tag : "+tag);
			}
			if(XT.articleType.equals("") && (tag.toUpperCase().startsWith("<ARTICLE") || tag.toUpperCase().startsWith("<SIMPLE-ARTICLE") || tag.toUpperCase().startsWith("<BOOK-REVIEW") || tag.toUpperCase().startsWith("<EXAM")))
			{
				XT.articleType=getAttribValue(tag, "XML:LANG").toUpperCase();
				//System.out.println(":::xt"+XT.articleType);
			}

			if (tag.equals("<ITEM-INFO>"))
			{
				while(!tag.equals("</ITEM-INFO>"))
				{
					ch = (char) fin.read();
					if(ch =='<' )
						tag = getTag(fin);
					if (tag.equals("<JID>"))
					{
						while (true)
						{
							ch = (char) fin.read();
							if(ch =='<' )
							{
								tag = getTag(fin);
								if(tag.equals("</JID>"))
									break;
							}
							else
							{
								jid+=ch;
							}
						}
					}
					else if (tag.equals("<AID>"))
					{
						while (true)
						{
							ch = (char) fin.read();
							if(ch =='<' )
							{
								tag = getTag(fin);
								if(tag.equals("</AID>"))
									break;
							}
							else
							{
								aid+=ch;
							}
						}
					}
					else if (tag.equals("<CE:PII>"))
					{
						while (true)
						{
							ch = (char) fin.read();
							if(ch =='<' )
							{
								tag = getTag(fin);
								if(tag.equals("</CE:PII>"))
									break;
							}
							else
							{
								pii+=ch;
							}
						}
					}
					else if (tag.equals("<CE:DOI>"))
					{
						while (true)
						{
							ch = (char) fin.read();
							if(ch =='<' )
							{
								tag = getTag(fin);
								if(tag.equals("</CE:DOI>"))
									break;
							}
							else
							{
								doi+=ch;
							}
						}
					}
		            else if(tag.startsWith("</ITEM-INFO>"))
					{
						break;
					}
				}
				//System.out.println("JID : "+jid+ " AID : "+aid+" PII : "+pii+" DOI : "+doi);
			}//end if (tag.equals("<ITEM-INFO>"))
			/*
			* Extracting the <ATL> information with <SBT>.
			*/
			else if ((tag.equals("<HEAD>")) || (tag.equals("<SIMPLE-HEAD>")) || (tag.equals("<BOOK-REVIEW-HEAD>")))
			{
				while((!tag.equals("</HEAD>")) && (!tag.equals("</SIMPLE-HEAD>")) && (!tag.equals("</BOOK-REVIEW-HEAD>")))
				{
					ch = (char) fin.read();
					if (ch =='<' )
					{
						tag = getTag(fin);
						if (tag.startsWith("<CE:TITLE"))
						{
							atlFound= true;
						}
						if (tag.equals("</CE:TITLE>"))
						{
							atlFound= false;
						}
					}
					else
					{
						if(atlFound== true)
							atl.append(ch);
					}
				}
				//System.out.println("Title : "+atl);
			}//end of <head>
			//avinandan added on 23-8-04
			else if((tag.startsWith("<CE:EXAM-QUESTIONS")) ||(tag.startsWith("<CE:EXAM-ANSWERS")))
			{
				bdyFlag=true;
				while(true)
				{
					ch =(char)fin.read();
					tag="";//added by avinandan
					if(ch=='<')
						tag = getTag(fin);
					if(tag.startsWith("<CE:SECTIONS"));
					else if(tag.startsWith("<CE:SECTION ") || tag.equals("<CE:SECTION>"))
					{
						secFound=true;
						handleSection(fin, 1);
						secCounter++;
					}
					/**
					*Date : 10/01/2007
					*Added: By Ravi
					*Change: Point : If Exam or Que tag has section title below changes will appear in tex file
					*Change Request By :TPMS
					*/
					else if(tag.startsWith("<CE:SECTION-TITLE"))//07/01/2008
					{
						StringBuffer ackTitle= new StringBuffer();
						while(!tag.equals("</CE:SECTION-TITLE>"))
						{
							ch =(char)fin.read();
							tag="";
							if(ch=='<')
								tag = getTag(fin);
							else
								ackTitle.append(ch);
						}
						bkmList.add("{}{"+ackTitle+"}");
						//System.out.println("bkmList : "+bkmList);
						//System.in.read();

					}
					else if(tag.startsWith("<CE:ACKNOWLEDGMENT"))
					{
						StringBuffer ackTitle= new StringBuffer();
						while(!tag.equals("</CE:ACKNOWLEDGMENT>"))
						{
							ch =(char)fin.read();
							tag="";
							if(ch=='<')
								tag = getTag(fin);
							if (tag.startsWith("<CE:SECTION-TITLE"))
							{
								while(!tag.equals("</CE:SECTION-TITLE>"))
								{
									ch =(char)fin.read();
									if(ch=='<')
										tag = getTag(fin);
									else
										ackTitle.append(ch);
								}
							}
						}
						secCounter++;
						bkmList.add("{}{"+ackTitle+"}");
						tag = moveFilePointer(true,"<CE:ACKNOWLEDGMENT").toUpperCase();
					}//end of ce:ack
					//Added condition on 25th Feb. 2002 tag.startsWith("</ART>")
					if(tag.startsWith("</CE:EXAM-QUESTIONS>") || tag.startsWith("</CE:EXAM-ANSWERS>") || tag.startsWith("<TAIL")|| tag.startsWith("</ARTICLE>"))
					{
						break;
					}
				}
			}//end of CE:EXAM-QUESTIONS
			//end mark
			else if(tag.startsWith("<BODY"))
			{
				bdyFlag=true;
				while(true)
				{
					ch =(char)fin.read();
					tag="";//added by avinandan
					if(ch=='<')
						tag = getTag(fin);
					if(tag.startsWith("<CE:SECTIONS"));
					else if(tag.startsWith("<CE:SECTION ") || tag.equals("<CE:SECTION>"))
					{
						secFound=true;
						handleSection(fin, 1);
						secCounter++;
					}
					else if(tag.startsWith("<CE:ACKNOWLEDGMENT"))
					{
						StringBuffer ackTitle= new StringBuffer();
						while(!tag.equals("</CE:ACKNOWLEDGMENT>"))
						{
							ch =(char)fin.read();
							tag="";
							if(ch=='<')
								tag = getTag(fin);
							if (tag.startsWith("<CE:SECTION-TITLE"))
							{
								while(!tag.equals("</CE:SECTION-TITLE>"))
								{
									ch =(char)fin.read();
									if(ch=='<')
										tag = getTag(fin);
									else
										ackTitle.append(ch);
								}
							}
						}
						secCounter++;
						bkmList.add("{}{"+ackTitle+"}");
						tag = moveFilePointer(true,"<CE:ACKNOWLEDGMENT").toUpperCase();
					}//end of ce:ack
					//Added condition on 25th Feb. 2002 tag.startsWith("</ART>")
					if(tag.startsWith("</BODY>") || tag.startsWith("<TAIL")|| tag.startsWith("</ARTICLE>"))
					{
						break;
					}
				}
			}//end of body
			/*
			* Processing <BM> tag
			*
			*<(ack?, appm?, bibl*, further-reading*,
			*glossary*, vt* )>
			*/
			else if(tag.startsWith("<TAIL"))
			{

				int biblSecCounter=0;
				while(!tag.equals("</TAIL>"))
				{
					ch =(char)fin.read();
					if (ch=='<')
					{
						tag = getTag(fin);
						//System.out.println("tag in tail :"+tag);
					}
					/*
					*Acknoledgement is a bookmark
					*/
					if(tag.startsWith("<CE:ACKNOWLEDGMENT"))
					{
						StringBuffer ackTitle= new StringBuffer();
						while(!tag.equals("</CE:ACKNOWLEDGMENT>"))
						{
							ch =(char)fin.read();
							if(ch=='<')
								tag = getTag(fin);
							if (tag.startsWith("<CE:SECTION-TITLE"))
							{
								while(!tag.equals("</CE:ACKNOWLEDGMENT>"))
								{
									ch =(char)fin.read();
									if(ch=='<')
										tag = getTag(fin);
									else
										ackTitle.append(ch);
								}
							}
						}
						secCounter++;
						bkmList.add("{}{"+ackTitle+"}");
						tag = moveFilePointer(true,"<CE:ACKNOWLEDGMENT").toUpperCase();
					}//end of ce:ack
					/*Appendix Book mark
					*If <CE:SECTION-TITLE> is there ,<CE:SECTION-TITLE> is book mark
					* else if <CE:LABEL> is a book mark
					* else Appendix -A is a book mark
					* And
					* Sections are similar to Body section
					*/
					if(tag.startsWith("<CE:APPENDICES"))
					{
						StringBuffer appNo = new StringBuffer();
						StringBuffer appSt = new StringBuffer();
						String appendix = new String();
						String title = new String();
						char ascii = 65;
						boolean appFlag = true;
						int Counter = 0;
						int list_size = bkmList.size();
						while(!tag.equals("</CE:APPENDICES>"))
						{
//							begin abhi 12-06-2002
//							secCounter++;
//							End
							ch = (char) fin.read();
							if(ch == '<' )
							{
								tag = getTag(fin).toUpperCase();
								/*
								*Extracting <APP> number <CE:LABEL>
								*/
								if(tag.equals("<CE:LABEL>"))
								{
									while(true)
									{
										ch = (char) fin.read();
										if(ch == '<')
										{
											tag = getTag(fin);
											if(tag.equals("</CE:LABEL>"))
											{
												break;
											}
										}
										else
										{
											appNo.append(ch);
										}
									}
								}
								/*
								*Extracting <APP> number <CE:SECTION-TITLE>
								*/
								else if(tag.startsWith("<CE:SECTION-TITLE"))
								{
									while(true)
									{
										ch = (char) fin.read();
										if(ch == '<')
										{
											long file = fin.getFilePointer()-1;
											tag = getTag(fin);
											if((tag.equals("</CE:SECTION-TITLE>"))||(tag.startsWith("<CE:SECTION"))||
											   (tag.equals("<CE:PARA>"))||(tag.startsWith("<CE:APPENDIX"))||(tag.startsWith("<CE:PARA")))
											{
												fin.seek(file);
												break;
											}
										}
										else
										{
											appSt.append(ch);
										}
									}
								}
								else if(tag.startsWith("<CE:SECTION"))
								{
									if(appFlag)
									{
										if(appSt.length() > 0)
										{
											title = appSt.toString();
										}
										else if(appNo.length() > 0)
										{
											title = appNo.toString();
										}
										else
										{
											title = "Appendix "+(ascii++)+".";
										}
									}
									handleSection (fin , 1);
									Counter++;
									appFlag = false;
								}
								else if (tag.equals("<CE:PARA>") || tag.startsWith("<CE:PARA")) //04-01-2005
								{
									if(appFlag)
									{
										if(appSt.length() > 0)
										{
											title = appSt.toString();
											appSt.setLength(0);
										}
										else if(appNo.length() > 0)
										{
											title = appNo.toString();
											appNo.setLength(0);
										}
										else
										{
											title = "Appendix "+(ascii++)+".";
										}
									}
									appFlag = false;
								}
								else if(tag.startsWith("<CE:APPENDIX") || tag.startsWith("</CE:APPENDIX>"))
								{
									if(!appFlag)
									{
										if(Counter>0)
											title = "{"+Counter+"}{"+title+"}";
										else
											title = "{}{"+title+"}";
										bkmList.add(list_size, title);
										list_size = bkmList.size();
										Counter = 0;
									}
									if (tag.startsWith("<CE:APPENDIX") )
									{
										secCounter++;
									}

									appNo.setLength(0);
									appSt.setLength(0);
									appFlag = true;
								}
								else if(tag.startsWith("</CE:APPENDICES>") || tag.startsWith("<CE:BIBLIOGRAPHY") || tag.startsWith("<CE:FURTHER-READING") || tag.startsWith("<CE:BIOGRAPHY") || tag.startsWith("<GLOSSARY>"))
								{
									if(!appFlag)
									{
										if(Counter>0)
											title = "{"+Counter+"}{"+title+"}";
										else
											title = "{}{"+title+"}";
										bkmList.add(list_size,title);
									}
									break;
								}
							}
						}
					}//

					/**
					* The first SGML construct <bibl> should result in a bookmark called
					*'References'. Possible <CE:SECTION-TITLE> constructs should be regarded as
					*subordinate bookmarks to the 'References' one.
					*/
					if(tag.startsWith("<CE:BIBLIOGRAPHY"))
					{
						//while (!tag.equals("</CE:BIBLIOGRAPHY>"))
						{
							secCounter++;
							//bkmList.add("{References}{0}");
							ch =(char)fin.read();
							{
								tag = getTag(fin);
								//System.out.println(tag);
							}
							StringBuffer st = new StringBuffer();
							//System.out.println("Bibl found");
							//System.out.println("tag "+tag);
							if(tag.startsWith("<CE:SECTION-TITLE"))
							{								
								while(true)
								{
									ch = (char) fin.read();
									if(ch == '<')
									{
										tag = getTag(fin);
										//System.out.println("tag "+tag);
										if((tag.equals("</CE:SECTION-TITLE>"))|| (tag.startsWith("<CE:BIBLIOGRAPHY")) || (tag.equals("<CE:PARA>")) || tag.startsWith("<CE:PARA"))
										{
											break;
										}
									}
									else
									{
										st.append(ch);
									}
								}
								String ttmp= "";
								if(biblSecCounter>0)
									ttmp= "{"+(biblSecCounter)+"}{"+st+"}";
								else
									ttmp= "{}{"+st+"}";
								biblSecCounter++;
								bkmList.add(bkmList.size(), ttmp);
								
								//bkmList.add(st);
							}
						}
						/**
						*Move the File Pointer to <FURTHER-READING>/GLOSSARY.
						*/
						
						tag = moveFilePointer(false , "<CE:BIBLIOGRAPHY").toUpperCase();
						if((tag.equals("</ARTICLE>")))// 18-11-04
							break;
						//System.out.println(tag);
					}
					/**
					*The first SGML construct <further-reading> should result in a
					*bookmark called 'Further Reading'. Possible <CE:SECTION-TITLE> constructs
					*should be regarded as subordinate bookmarks to the
					*'Further Reading' one.
					*/

					if(tag.startsWith("<CE:FURTHER-READING"))
					{
						secCounter++;
						//bkmList.add("{Further Reading}{0}");
						while( ( ch =(char)fin.read() ) != '<');
						tag = getTag(fin);
						StringBuffer st = new StringBuffer();
						if(tag.startsWith("<CE:SECTION-TITLE"))
						{

							while(true)
							{
								ch = (char) fin.read();
								if(ch == '<')
								{
									tag = getTag(fin);
//									System.out.println("tag  "+tag);
									if((tag.equals("</CE:SECTION-TITLE>"))||(tag.startsWith("<CE:BIBLIOGRAPHY"))||
									   (tag.startsWith("<CE:FURTHER-READING-SEC"))||(tag.startsWith("</ARTICLE>"))||(tag.startsWith("</ARTICLE>")))
									{
										break;
									}
								}
								else
								{
									st.append(ch);
								}
							}
							//bkmList.add("{"+st+"}{0}");
						}
						if (st.length() > 0)
						{
							bkmList.add("{1}{Further Reading}");//add
							bkmList.add("{}{"+st+"}");
						}
						else
						{
							bkmList.add("{}{Further Reading}");//add
						}
						/**
						*Move the File Pointer to <GLOSSARY>/</BM>.
						*/
						//tag = moveFilePointer(false , "</CE:FURTHER-READING>");
					}
					/**
					*The first SGML construct <glossary> should result in a
					*bookmark called 'Glossary', unless the <CE:SECTION-TITLE> is used in
					*which case this st is used.
					*/
					if(tag.equals("<CE:GLOSSARY>") || tag.startsWith("<CE:GLOSSARY "))
					{
						secCounter++;
						while( ( ch =(char)fin.read() ) != '<');
						tag = getTag(fin);
						StringBuffer st = new StringBuffer();
						if(tag.startsWith("<CE:SECTION-TITLE"))
						{
							while(true)
							{
								ch = (char) fin.read();
								if(ch == '<')
								{
									tag = getTag(fin);
									if ((tag.equals("</CE:SECTION-TITLE>"))||(tag.startsWith("<GLOSSARY-ENTRY"))||
											(tag.startsWith("</ARTICLE>")))
									{
										break;
									}
								}
								else
								{
									st.append(ch);
								}
							}
						}
						if(st.length() > 0)
						{
							st=st.insert(0, "{}{");
							st.append("}");
							bkmList.add(st);
						}
						else
						{
							bkmList.add("{}{Glossary}");
						}
						tag = moveFilePointer(false , "<CE:GLOASSARY");
					}
				}
			}
			

		}

		String atlTemp = "{"+secCounter+"}{"+atl.toString()+"}";
		atlTemp=atlTemp.replaceAll("%","\\\\%");
		
		//System.out.println(atl);
		if(atl.length()>0)
		{
			//System.out.println(bkmList);
			bkmList.add(0, atlTemp);
		}

		//System.out.println(bkmList+" "+atlTemp);

		ArrayList newBkmList= new ArrayList();
		ReplaceEntity ent=  new ReplaceEntity();
		for(int k=0; k<bkmList.size(); k++)
		{
			String bkmTitle= bkmList.get(k).toString();
			String newbkmTitle= ent.replace (bkmTitle);
			newbkmTitle=newbkmTitle.replaceAll("#","\\\\#");
			newBkmList.add (newbkmTitle);
		}
		bkmList= newBkmList;
					
	}

	private String moveFilePointer(boolean check , String checkTag) throws IOException
	{
		char ch = 0;
		String tag = new String();
		while(true)
		{
			while(( ch =(char)fin.read() ) != '<');
			tag = getTag(fin);
			//Avinandan added bellow in ifcondition ||(tag.startsWith("</SIMPLE-ARTICLE>"))||(tag.startsWith("</BODY>"))

			 if((tag.startsWith("<BODY"))||(tag.startsWith("<TAIL"))||
			   		(tag.startsWith("<CE:APPENDICES"))||(tag.startsWith("<CE:BIBLIOGRAPHY"))||
			   		(tag.startsWith("</CE:FURTHER-READING>"))||(tag.startsWith("<CE:GLOSSARY"))||
			   		(tag.startsWith("</CE:ACKNOWLEDGMENT>"))||(tag.startsWith("</ARTICLE>"))||(tag.startsWith("</SIMPLE-ARTICLE>"))||(tag.startsWith("</BODY>")))
			{
				if(check)
				{
					break;
				}
				else if(!tag.startsWith(checkTag))
				{
					break;
				}
			}

		}
		return tag;
	}
	
	public String getAttribValue(String tag, String attribute)
	{
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
				else if(ch=='\'')
				{
					i++;
					while ((ch= tag.charAt(i++))!='\'')
						attributeValue+= ch;
					break;
				}
			}
		}
		return attributeValue;
	}

}