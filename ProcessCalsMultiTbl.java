package tp.xt;
import tp.xt.*;
import tp.xt.XMLObjects;
import tp.xt.util.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;
import tp.xt.XT;
class ProcessCalsMultiTbl
{
	XMLObjects xmlObj;
	Vector tablecolAligns;
	Hashtable newComL;
	Hashtable newComR;
	Hashtable newComChar;
	String rowSepValue="";
	int rvalue=1;
	int rowSize = 0;
	
	int hhlinecol=0;
	String hhline="";
	String hhlinestart="";
	String hhlineend="";
	boolean moretabular;
	boolean globalRowsep;
	int columCount;
	Hashtable tabularchar;
	boolean isMoreRows = false;
	String Valign="";//ravi
	Vector colAlign= new Vector();
	static Hashtable moreRow; 
	static StringBuffer rowContants= new StringBuffer();//Bhavesh
	//Bhavesh this is variable for storing the column number of the table
	static int count=0;
	//Store row mearging information like how many row are merge in 
	//a particular column column is a key
	static Hashtable AllignTable=new Hashtable();
	//Store the information about the alignment of the previous column
	static Hashtable Allin_table=new Hashtable();
	int colNum=0;
	boolean theadColFlag = false;
	int spos=0;
	int epos=0;
	int tempcounter=0;
	boolean boltablColHead=false;
	boolean bolCount=false;
	boolean chkblnk=false;
	boolean check_body_cline=false;
		boolean check_body_cline_after=false;
boolean rowsap_check=false;
int rowcount=0;
int cellcount=0;
boolean top_row=false;
String cline_val="";
static Hashtable top_table_cline=new Hashtable();

	String tblId_temp="";
	static int countTablePara=0;
	ProcessCalsMultiTbl(XMLObjects xmlObj)
	{
		tablecolAligns= null;
		this.xmlObj= xmlObj;
		moreRow=new Hashtable();
		newComL=new Hashtable();
		newComR=new Hashtable();
		newComChar=new Hashtable();
		tabularchar=new Hashtable();
		moretabular=false;
		globalRowsep=false;
		
	}

	public String processDisplayTable(String tableTag)throws java.io.IOException
	{
		boolean flg_newline=false;
		HeadGroup head=new HeadGroup(xmlObj);//28-08-2012 JADTD520 Updation
		String tag                  = tableTag;
		String tblCaption           = "";
		String tblLbl               = "";
		StringBuffer tableContents  = new StringBuffer();
		String tblId                = xmlObj.getAttributeValue(tag, "ID");
		//System.out.println ("tblId : "+tblId);
		String tblBorder            = xmlObj.getAttributeValue(tag, "FRAME");
		//System.out.println ("tblBorder : "+tblBorder);
		String tableRowsep         = xmlObj.getAttributeValue(tag, "ROWSEP");
		//System.out.println ("tableRowsep : "+tableRowsep);
		boolean theadFlag = false;
		if(tableRowsep.equals("1"))
			globalRowsep=true;
		System.out.println ("\tProcessing Table : "+tblId);
		//Values Of tblBorder : all, bottom, none, sides, top, topbot

		int maxCols				    = 0;
		StringBuffer tableFootnotes = new StringBuffer();
		StringBuffer tableLegends   = new StringBuffer();
		String tableHeadStr         = "";
		StringBuffer tableBody= new StringBuffer();
		boolean isCaption = false;
		boolean isLabel = false;
		boolean isTgroup = false;
		boolean IsMultiCaptionInTable = false;
		String tableSourceTag         = "";
		String tablekeyword="";//28-08-2012 JADTD520 Updation
		boolean IstableKeyword=false;//28-08-2012 JADTD520 Updation
		if (tblId.length()>0)
			tableContents.append(xmlObj.getHypertarget(tblId));
 		while (!tag.equals("</CE:TABLE>"))
		{
			
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				
					
				//System.out.println("tag : "+tag);
				//System.in.read();
				if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))
				{
					isCaption=true;
					tblCaption="";
					String tempTag=tag;
					String CapLang="";
					while (!tag.equals("</CE:CAPTION>"))
					{
						if(tempTag.startsWith("<CE:CAPTION "))
						{
										CapLang=xmlObj.getAttributeValue(tempTag, "XML:LANG");
						}
						
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
							{
								/**
								* Date : [17/07/2007]
								* Added By : Ravi 
								* Change Point : if multiple para comes in table caption all multiple para will start with new line.
								* Change request By : Vivek through TPMS
								*/
								//System.out.println("tblId_temp : "+tblId_temp+" tblId : "+tblId+" countTablePara : "+countTablePara);
								//System.in.read();
								//System.out.println("tblId ::>>>"+tblId);
								if(tblId.length()>0)
								{
									if(!tblId_temp.equals(tblId))
									{
										tblId_temp=tblId;
										countTablePara=1;
									}
									else
									{
										++countTablePara;
									}
									if(countTablePara >1)
									{
										//tblCaption+="\\\\\r\n";//when DTD510 will be live this condition no longer in use.
									}
									
								}
								//end
								/**
								* Date  : 28/02/2008
								*/
								//tblCaption+= xmlObj.extractData("</CE:SIMPLE-PARA>", true);//old blocked 28/02/2008
								if(countTablePara==1)
								{
									tblCaption+= xmlObj.extractData("</CE:SIMPLE-PARA>", true);
								}
								else if(countTablePara==2)
								{
									
									
									if(!tempTag.equals("<CE:CAPTION>")&&(tempTag.startsWith("<CE:CAPTION ")&&!CapLang.equals("")))
									{
										if(XT.jid.equals("PALEVO"))//25/11/2009
										{
											Hashtable TblHash=new Hashtable();
											TblHash.put("Table","Tableau");
											TblHash.put("Tableau","Table");
											if(tblLbl.indexOf(" ")!=-1)
											{
												String []a=tblLbl.split(" ");
												String labPalivo=TblHash.get(a[0]).toString();
												tblCaption+="\r\n\\ALTTBLNO{"+labPalivo+" "+a[1]+"}";
											}
										}
										tblCaption+="\r\n\\tblSubcaption{\\"+CapLang+"{"+ xmlObj.extractData("</CE:SIMPLE-PARA>", true)+"}}";
										IsMultiCaptionInTable=true;
									}
									else
									{
										tblCaption+="\\\\\r\n\\AltCaption{"+ xmlObj.extractData("</CE:SIMPLE-PARA>", true)+"}";

									}

									
								}
								else if(countTablePara > 2)
								{
									if(!tempTag.equals("<CE:CAPTION>")&&(tempTag.startsWith("<CE:CAPTION ")&&!CapLang.equals("")))
									{
										if(XT.jid.equals("PALEVO"))//25/11/2009
										{
											Hashtable TblHash=new Hashtable();
											TblHash.put("Table","Tableau");
											TblHash.put("Tableau","Table");
											String []a=tblLbl.split(" ");
											String labPalivo=TblHash.get(a[0]).toString();
											tblCaption+="\r\n\\ALTTBLNO{"+labPalivo+" "+a[1]+"}";
										}
										tblCaption+="\r\n\\tblSubcaption{\\"+CapLang+"{"+ xmlObj.extractData("</CE:SIMPLE-PARA>", true)+"}}";
										IsMultiCaptionInTable=true;
									}
									else
									{
										tblCaption+="\\\\\r\n"+xmlObj.extractData("</CE:SIMPLE-PARA>", true);
										
									}
									
									//tblCaption+= xmlObj.extractData("</CE:SIMPLE-PARA>", true);
									
								}
								//end
								
								//System.out.println("tblCaption==> "+tblCaption+"countTablePara"+ countTablePara);
								//System.in.read();
							}
						}
					}
					if(IsMultiCaptionInTable ==false)
					{
						tableContents.append("\r\n\\tbl{");
						tableContents.append(tblCaption+"}{%");
					
					}
					else if(IsMultiCaptionInTable==true)
					{
						tableContents.delete(tableContents.lastIndexOf("}{%"),tableContents.length());
						tableContents.append(tblCaption+"}{%");
						//System.out.println("tableContents==> "+tableContents);
						//System.in.read();
					}
					//tableContents.append("\r\n\\tbl{");
					//tableContents.append(tblCaption+"}{%");
					//System.out.println("tblCaption==> "+tblCaption);
					//System.in.read();
				}
				else if(tag.startsWith("<CE:SOURCE>"))
				{
					tableSourceTag=xmlObj.extractData("</CE:SOURCE>", true);
					
					
				}
				else if(tag.startsWith("<CE:KEYWORDS "))//28-08-2012 JADTD520 Updation
				{
					tablekeyword=head.processKeywords(tag);
					IstableKeyword=true;
					//System.out.println("tablekeyword=========>>"+tablekeyword);
					//tableContents.append(key);
					//tableSourceTag=xmlObj.extractData("</<ce:keyword>", true);
					
					
				}
				//head


				//added by avinandan
				else if(tag.startsWith("<CE:LINK"))
				{
					String linkId= xmlObj.getAttributeValue(tag, "LOCATOR");
					if(linkId.length() > 0)
					{
						tableContents.append("\r\n{%\r\n\\epsfbox{"+linkId.toLowerCase()+".eps}}%\r\n");
						//System.out.println("linkId.toLowerCase() "+linkId.toLowerCase());
					}
				}
				//end mark
				else if (tag.equals("<CE:LABEL>"))
				{
					isCaption=true;
					isLabel=true;
					tblLbl= xmlObj.extractData("</CE:LABEL>", true);
					tableContents.append("\r\n\\TBLNO{"+tblLbl+"}");
					//[06/09/2007]
					if(tblBorder.equalsIgnoreCase("ALL"))
					{
						tableContents.append("\n\\global\\CellBordertrue");
					}
					//end
					tag=xmlObj.getNextTag();
					if(!tag.startsWith("<CE:CAPTION"))
					{
						tableContents.append("\r\n\\tbl{}{%");
					}
				}
				else if (tag.startsWith("<TGROUP"))
				{
					rowContants.delete(0,rowContants.length());
					isTgroup=true;
					tablecolAligns= getTableAlignment(tag);
					//System.out.println("tablecolAligns==> "+tablecolAligns);
					//System.in.read();
					hhlinecol=Integer.parseInt(xmlObj.getAttributeValue(tag, "COLS"));
					//System.out.println("hhlinecol==> "+hhlinecol);
					//bhavesh 14.08.06 for multi row
					for(int i=1;i<=hhlinecol;i++)
					{
						moreRow.put(new Integer(i), "0");
					}
					//System.out.println("moreRow==> "+moreRow);
					//System.in.read();
					String strip="";
					strip=xmlObj.getAttributeValue(tag, "ALTIMG");
					if(strip.length()>0)
						tableContents.append("\r\n\\begin{inlinestripns}{"+strip.toLowerCase()+"}");
					tableContents.append(insertValueNewCommand(xmlObj.getAttributeValue(tag, "COLS")));
					//System.out.println("tableContents "+xmlObj.getAttributeValue(tag, "COLS"));
					//System.in.read();
					while (!tag.equals("</TGROUP>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							//System.out.println("1111111111>>>>>>>>>>>>>>>::>>>"+tag);
							if (tag.startsWith("<COLSPEC"))
							{
								String alignment= xmlObj.getAttributeValue(tag, "ALIGN");
								while (!tag.startsWith("<THEAD") && !tag.startsWith("<TBODY"))
								{
									ch= (char)xmlObj.fin.read();
									if (ch=='<')
										tag= xmlObj.getTag().toUpperCase();
								}
								if (tag.startsWith("<THEAD"))
								{
									theadFlag=true;
									tableHeadStr="";
									// Top row
									while (!tag.equals("</THEAD>"))
									{
										ch= (char)xmlObj.fin.read();
										if (ch=='<')
										{
											tag= xmlObj.getTag().toUpperCase();
											if (tag.startsWith("<ROW"))
											{
												count=0;//Bhavesh
												String rowsep= xmlObj.getAttributeValue(tag, "ROWSEP");
												theadColFlag=true;
												//ADDED BY Ravi [28/11/2006]
												rowContants.delete(0,rowContants.length());
												
												rowMerge();
												//Added By Ravi [18/11/2006 By Vivek   adding \TblColHead{ }in table head label] 
												//System.out.println("XT.modelStyle : "+XT.modelStyle+" theadFlag : "+theadFlag);
												if(((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))|| (XT.jid.equalsIgnoreCase("ZOOGA"))|| (XT.jid.equalsIgnoreCase("NEULAB"))||(XT.modelStyle.equals("-PIO"))) && (theadFlag==true))
												{
													boltablColHead=true;
													
												}
												else
												{
													boltablColHead=false;
													
												}
												String rowData = processTableRow(tag,theadFlag); 
												//10/09/2007
												
												theadColFlag=false;
												boltablColHead=false;
												tempcounter=0;
												tableHeadStr += rowData;
												


												//System.out.println("tableHeadStr --> "+tableHeadStr);
												//System.in.read();
												rowContants.delete(0,rowContants.length());
												if(rowsep.equals("1"))
												{
													tableHeadStr+= "\r\n\\colrule";
												}
											}
										}
										else if (tag.equals("</THEAD>"))
										{
											theadFlag=false;
											boltablColHead=false;//Added by Ravi [18/11/2006]
											tempcounter=0;//Added by Ravi [18/11/2006]
										}
									}
								}
							}
							if (tag.startsWith("<TBODY"))
							{	//System.out.println("tablecolAligns::>>>");
								while (!tag.equals("</TBODY>"))
								{
									ch= (char)xmlObj.fin.read();
									if (ch=='<')
									{
										tag= xmlObj.getTag().toUpperCase();		
										
										if (tag.startsWith("<ROW"))
										{
											/*
											 *Add by Bhavesh for morerow
											 */
											count=0;
											rowContants.delete(0,rowContants.length());
										
											rowMerge();
											/*************End*************/

											check_body_cline=true;
											
										tableBody.append(processTableRow(tag,theadFlag));
										

										
											if(isMoreRows)//abhay 26/06/2006
											{
												/*if(tableContents.indexOf("\\Check_Multirow") == -1 && tableContents.indexOf("\\TBLNO{Table") != -1)
												{
													tableContents.insert(tableContents.indexOf("\\TBLNO{Table"), "\\Check_Multirow\r\n");
													//System.out.println("\n\n\n"+tableContents);
												}*/
												isMoreRows = false;
											}
										}
										
									}
								}
							}
							else if (tag.equals("</TGROUP>"))
							{
								//System.out.println("tablecolAligns::>>>"+tablecolAligns);
								//04-06-2011 count TGROUP
								long filePointer= xmlObj.fin.getFilePointer();
								String tempTag=tag;
								int TGROUP_count=1;
								while((!tempTag.equals("</CE:TABLE>")))
								{
									ch= (char)xmlObj.fin.read();
									if (ch=='<')
									{
										tempTag= xmlObj.getTag().toUpperCase();
										//System.out.println("tempTag-->>"+tempTag);
										if (tempTag.equals("</TGROUP>"))
										{
											//System.out.println("tempTag-->>"+tempTag);
											TGROUP_count++;
											//System.out.println("TGROUP_count-->>"+TGROUP_count);
										}
									}
								}
								xmlObj.fin.seek(filePointer);
								//End Cout TGROUP

								if((tableContents.indexOf("begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}")!=-1)||(tableContents.indexOf("begin{tabular*}{\\hsize}{@{}@{\\extracolsep{3cm minus.5fill}}")!=-1))//19/03/2009
								{
									//System.out.println("Entered"+moretabular+tableContents);
									/*
									* Date : 12/09/2007
									* Change point : new line appera before newcolumn type
									* Modify By : Ravi
									*/
									if(tableContents.indexOf("\\end{tabular*}")!=-1)
									{
										int newcolum=tableContents.indexOf("\\end{tabular*}");

											if(newcolum !=-1)
											{
												int newcolum1=tableContents.indexOf("\\newcolumntype{",newcolum);
												if(newcolum1 !=-1)
												{
													if(flg_newline==false)
													{	
														//System.out.println("Entered----------------------------");
														flg_newline=true;
														tableContents.insert(newcolum1-2,"\\\\");
													}
												}
											}

									}
									
									//tableContents.append("\\\\\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");\\old
									if(XT.isTableGulliverCutOff==true && !(XT.jid.equalsIgnoreCase("JTEMB")))
									{
										tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{3cm minus.5fill}}");
										//System.out.println("Entered----------------------------"+XT.isTableGulliverCutOff);
									}
									else
									{
										tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");
									}
									moretabular=true;
								}
								else
								{
									//tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");
									if(XT.isTableGulliverCutOff==true&& !(XT.jid.equalsIgnoreCase("JTEMB")))
									{
										tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{3cm minus.5fill}}");
										//System.out.println("wwwwwwwwwwwwwwEntered----------------------------");
									}
									else
									{
										tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");
									}
									//System.out.println("Entered----------------------------");
								}
								//System.out.println("tablecolAligns "+tablecolAligns);
								//System.in.read();
								for(int align=0; align<tablecolAligns.size(); align++)
								{
									String alignValue=tablecolAligns.get(align).toString();
									//change by bhavesh && to ||
									if(!(alignValue.equals("l")|| alignValue.equals("r") || alignValue.equals("c")))
									{
										if(tabularchar.containsKey(new Integer(align+1)))
										{
											tableContents.append(tabularchar.get(new Integer(align+1))+"@{}");
										}
										else if(alignValue.equals("+-"))
										{
											tableContents.append("±@{}");
										}
										else if(alignValue.equals("x"))
										{
											tableContents.append("ô@{}");
										}
										else if(alignValue.equals("n"))
										{
											tableContents.append("æ@{}");
										}
										else
										{
											
											tableContents.append(alignValue+"@{}");
											
										}
										//System.out.println("alignValue "+alignValue);
										
									}
									else
									{
										
										tableContents.append(alignValue+"@{}");
										
									}
								}

								tableContents.append("}");
							//	System.out.println(tableContents);
								if(tblBorder.indexOf("TOP")>-1)
								{
									//tableContents.append("\r\n\\toprule");
									if(tableContents.indexOf("\r\n\\toprule")==-1)//rajeev-081004
										tableContents.append("\r\n\\toprule");
									//else
									//	tableContents.append("\r\n\\\\[10pt]");
								}
								tableContents.append(tableHeadStr);
								tableContents.append(tableBody);
								
								//System.out.println("---------------------------------------"+tableContents);
								//System.in.read();
								if(tblBorder.indexOf("BOT")>-1)
								{
									if(tableContents.toString().endsWith("\r\n\\colrule"))
									{	//tableContents.delete(tableContents.length()-10,tableContents.length());
									//tableContents.append("\r\n\\botrule");
									}
									else if(tableContents.indexOf("\r\n\\botrule")>-1)//rajeev-081004
									{
										//System.out.println("---------------------------------------");
										tableContents.replace(tableContents.indexOf("\r\n\\botrule"),tableContents.indexOf("\r\n\\botrule")+10,"[10pt]");//block 04-06-2011 Block for journal <jid>HM</jid><aid>1054</aid> Table 3
										tableContents.append("\r\n\\botrule");
									}
									else
										tableContents.append("\r\n\\botrule");
								}
									tableContents.append("\r\n\\end{tabular*}");
								//System.out.println("---------------------------------------"+tableContents);
								//System.in.read();
								tablecolAligns.clear();								
								tableHeadStr="";
								tableBody=null;
								tableBody = new StringBuffer();
//								tableBody.delete(0,tableBody.length());
							}
						}
					}
					if(strip.length()>0)
					{
						tableContents.append("\r\n\\end{inlinestripns}");
						strip="";
					}
				}
				else if (tag.startsWith("<CE:TABLE-FOOTNOTE"))
				{
					String tblfnId= xmlObj.getAttributeValue(tag, "ID");
					String tblfnLbl="";
					boolean first=true;
					while (!tag.equals("</CE:TABLE-FOOTNOTE>"))
					{					

						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.startsWith("<CE:LABEL>"))
								tblfnLbl= xmlObj.extractData("</CE:LABEL>", true);
							else if (tag.startsWith("<CE:NOTE-PARA"))
							{
								if(first)
									tableFootnotes.append("\r\n\\tabnote{\\tabentry{"+tblfnLbl+"}"+xmlObj.getHypertarget(tblfnId)+xmlObj.extractData("</CE:NOTE-PARA>", true));
								else
									tableFootnotes.append("\\newline\r\n"+xmlObj.extractData("</CE:NOTE-PARA>", true));
								first=false;
							}
						}
					}
					tableFootnotes.append("}");
				}
				else if (tag.startsWith("<CE:LEGEND"))
				{
					boolean fstpara=true;
					while (!tag.equals("</CE:LEGEND>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.startsWith("<CE:SIMPLE-PARA>")||tag.startsWith("<CE:SIMPLE-PARA"))
							{
								if(fstpara)
								{
									tableLegends.append("\r\n\\legend{"+xmlObj.extractData("</CE:SIMPLE-PARA>", true));
									fstpara=false;
								}
								else
								{
									tableLegends.append("\\\\\r\n"+xmlObj.extractData("</CE:SIMPLE-PARA>", true));
									
								}
							}
						}
					}
					tableLegends.append("}");
				}
			}
		}
		
		//System.out.println("XT.isTableGulliverCutOff : "+XT.isTableGulliverCutOff);
		if(isCaption == true)
		{
			
		//System.out.println("tableCount : "+HeadGroup.tableCount);
			//tableContents.insert(0,"\\begin{table}");//old 21-01-2012
			tableContents.insert(0,"\\begin{table}%TABLE"+HeadGroup.tableCount+"\r\n");//21-01-2012
			if(isTgroup==false && tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")!=-1 && (!tblBorder.equals("NONE")))
			{
				if(XT.isTableGulliverCutOff==true && !(XT.jid.equalsIgnoreCase("JTEMB")))
				{
					tableContents.insert(tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")+4,"\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{3cm minus.5fill}}l}\r\n\\toprule");
				}
				else
				{
					tableContents.insert(tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")+4,"\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}l}\r\n\\toprule");
				}
				//tableContents.insert(tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")+4,"\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}l}\r\n\\toprule");
				tableContents.append("\\botrule\r\n\\end{tabular*}");
			}
			tableContents.append("}");
			if(tableSourceTag.length()>0)
			{
				tableContents.append("\r\n\\Tablesource{"+tableSourceTag+"}");
				tableSourceTag="";
			}
			/**
			* [24/08/2007]
			* Modify By : Ravi
			* Change Point : All border in table.
			* Change Request By : Vivek
			*/

			if(tblBorder.equalsIgnoreCase("ALL"))
			{
				//tableContents.append("\n{\\allborder{30}{30}}");
				tableContents.append("\n{\\fullborder{\\tabbodyht}{\\tabbodywd}}\n\\global\\CellBorderfalse");
				//System.out.println("tblBorder : "+tblBorder);
				//System.in.read();
			}
			//end
			tableContents.append(tableLegends);
			tableContents.append(tableFootnotes);
		
	
			tableContents.append("\r\n\\end{table}");
		}
		else
		{
			if(!isLabel)
			{
				String st=new String(tableLegends.toString());
				st=st.replaceAll("\\\\legend","\\\\textlegend");
				tableLegends=new StringBuffer(st);
				if(tableSourceTag.length()>0)//15-03-2014
				{
					tableContents.append("\r\n\\Tablesource{"+tableSourceTag+"}");
					tableSourceTag="";
				}
			}
			tableContents.append(tableLegends);
			tableContents.append(tableFootnotes);
		}

		if(moretabular==true)
		{
			int p=tableContents.indexOf("\\begin{tabular*}");
			if (p >=0)
				tableContents.insert(p,"\\begin{tabular*}{\\hsize}{@{}l@{}}\r\n");
			if(tableContents.indexOf("\\tbl{")!=-1)
				p=tableContents.lastIndexOf("\\end{tabular*}}");
			else
				p=tableContents.lastIndexOf("\\end{tabular*}");

			//System.out.println("p->"+p);
			p=p-1;
			if (p >=0)
				tableContents.insert(p,"\\end{tabular*}");
			moretabular=false;
		}
		/////BEGIN TABULAR END TABULAR CASE

/* //Blocked by Vivek on 29-01-2013 as no keywords required in table output
if(IstableKeyword)//28-08-2012 JADTD520 Updation
{
	
	int p=tableContents.lastIndexOf("\\end{tabular*}");

	if(p>=0)
		tableContents.insert(p+"\\end{tabular*}".length(),"\r\n\\keywords{%\r\n"+tablekeyword+"}");
}
*/
//***********************************[row sep handled 04/03/2008]******************************************
//-Mod7IChemE
//System.out.println("XT.isNewDTDCutOff "+XT.isNewDTDCutOff);
//System.in.read();
if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-PIO"))||((XT.isNewDTDCutOff==true)&&((XT.modelStyle.equals("1PlusG"))||(XT.modelStyle.equals("1PlusGFMS"))||(XT.modelStyle.equals("1PlusGFrench"))||(XT.modelStyle.equals("3PlusG"))||(XT.modelStyle.equals("3PlusGFMS"))||(XT.modelStyle.equals("3PlusGFrench"))||(XT.modelStyle.equals("5PlusG"))||(XT.modelStyle.startsWith("7Spanish"))||(XT.modelStyle.equals("5PlusGSpanish"))||(XT.modelStyle.equals("5PlusGFMS"))||(XT.modelStyle.equals("5PlusGFrench")))))
{
String L1="";
String L2="";
int s1;
int m1=0;
int e1;
s1=tableContents.indexOf("\\cline{",m1);
if(s1!=-1)
{
	while(s1!=-1)
	{
		s1=tableContents.indexOf("\\cline{",m1);
		if(s1!=-1)
		{
			m1=tableContents.indexOf("}",s1);
			if(m1 !=-1)
			{
				L1=tableContents.substring(s1+"\\cline{".length(),m1);
				//System.out.println("L1 : "+L1);
				String [] a=L1.split("-");
				if(a[0].trim().equals(a[1].trim()))
				{
					tableContents.delete(s1,m1+1);
					s1=0;
					m1=0;
				}
			}
		}
	}
	//System.out.println("tableContents : "+tableContents);
	//System.in.read();
}
}
//*****************************************************************************
//-Mod7IChemE
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-PIO"))||(XT.modelStyle.equals("-Mod7IChemE"))||((XT.isNewDTDCutOff==true)&&((XT.modelStyle.equals("1PlusG"))||(XT.modelStyle.equals("1PlusGFMS"))||(XT.modelStyle.equals("1PlusGFrench"))||(XT.modelStyle.equals("3PlusG"))||(XT.modelStyle.equals("3PlusGFMS"))||(XT.modelStyle.equals("3PlusGFrench"))||(XT.modelStyle.equals("5PlusG"))||(XT.modelStyle.startsWith("7Spanish"))||(XT.modelStyle.equals("5PlusGSpanish"))||(XT.modelStyle.equals("5PlusGFMS"))||(XT.modelStyle.equals("5PlusGFrench")))))
		{
			if(tableContents.indexOf("\\cline{") != -1)
			{
				int tempindex = 0;
				int start = 0;
				int prev = 0;
				int end = 0;
				int total = 0;
				int rowIndex = 0;
				int prevRowIndex = 0;
				while(tableContents.indexOf("\\cline{", tempindex) != -1)
				{
					tempindex = tableContents.indexOf("\\cline{", tempindex);

					if(tableContents.indexOf("\\\\",tempindex) != -1)
					{
						prevRowIndex = rowIndex;
						rowIndex = tableContents.lastIndexOf("\\\\",tempindex);
						//System.out.println(prevRowIndex +" : "+ rowIndex);
						if(prevRowIndex != rowIndex)
						{
							//prev =0;//old
							/**
							*Added By Ravi [23/02/2007]
							*Change Request By: Vivek
							*Change Point : In table 2 rule spaning should be column 9 to 10 COMM_2604
							*/
							prev =1;
						}

					}

					start = Integer.parseInt(tableContents.substring(tableContents.indexOf("{", tempindex)+1, tableContents.indexOf("-", tempindex)));
					end = Integer.parseInt(tableContents.substring(tableContents.indexOf("-", tempindex)+1, tableContents.indexOf("}", tempindex)));
					total = end - start + 1;
					
					tableContents.delete(tableContents.indexOf("\\cline{", tempindex), tableContents.indexOf("}", tempindex));
					/**
					*Added By Ravi [23/02/2007]
					*Change Request By: Vivek
					*Change Point : In table 2 rule spaning should be column 9 to 10 COMM_2604
					*/
					//for(int u=prev+1 ; u < start ; u++)//old

					for(int u=prev ; u < start ; u++)
					{
						tableContents.insert(tempindex, " & ");
						//System.out.println("start==> "+start+" end==> "+end);
						//System.in.read();
						
						tempindex += 3;
					}
					prev = end;
					tableContents.insert(tempindex,"\\cline{"+total);
					//System.out.println("total"+total);
					//System.in.read();
					tempindex += 5;
				}
			}
		}

		String tempcol=tableContents.toString();
		//System.out.println("tempcol---> "+tempcol);
		//System.in.read();
//-Mod7IChemE
//5PlusG

		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-PIO"))||(XT.modelStyle.equals("-Mod7IChemE"))||((XT.isNewDTDCutOff==true)&&((XT.modelStyle.equals("1PlusG"))||(XT.modelStyle.equals("1PlusGFMS"))||(XT.modelStyle.equals("1PlusGFrench"))||(XT.modelStyle.equals("3PlusG"))||(XT.modelStyle.equals("3PlusGFMS"))||(XT.modelStyle.equals("3PlusGFrench"))||(XT.modelStyle.equals("5PlusG"))||(XT.modelStyle.startsWith("7Spanish"))||(XT.modelStyle.equals("5PlusGSpanish"))||(XT.modelStyle.equals("5PlusGFMS"))||(XT.modelStyle.equals("5PlusGFrench")))))
		{
			//System.out.println("---------------"+tempcol);
			tempcol=tempcol.replaceAll("\\\\cline\\{([0-9]+)","\\\\multicolumn{$1}{@{}l@{}}{\\\\hrulefill");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\} \r\n","}{@{}l@{\\\\hskip6pt}}{\\\\hrulefill}\\\\\\\\\r\n");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\}\r\n","}{@{}l@{\\\\hskip6pt}}{\\\\hrulefill}\\\\\\\\\r\n");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\}\\\\multicolumn\\{","}{@{}l@{}}{\\\\hrulefill} & \\\\multicolumn{");
//System.out.println("---------------"+tempcol);
		}
		
		tempcol=tempcol.replaceAll("\\\\colrule\\[([0-9]+)pt\\]","\\\\colrule\\\\\\\\\\[$1pt\\]");
		tableContents=new StringBuffer(tempcol);
//-Mod7IChemE
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-PIO"))||(XT.modelStyle.equals("-Mod7IChemE"))||((XT.isNewDTDCutOff==true)&&((XT.modelStyle.equals("1PlusG"))||(XT.modelStyle.equals("1PlusGFMS"))||(XT.modelStyle.equals("1PlusGFrench"))||(XT.modelStyle.equals("3PlusG"))||(XT.modelStyle.equals("3PlusGFMS"))||(XT.modelStyle.equals("3PlusGFrench"))||(XT.modelStyle.equals("5PlusG"))||(XT.modelStyle.startsWith("7Spanish"))||(XT.modelStyle.equals("5PlusGSpanish"))||(XT.modelStyle.equals("5PlusGFMS"))||(XT.modelStyle.equals("5PlusGFrench")))))
		{
			//System.out.println("1---------------hhh"+XT.modelStyle);
			if(tableContents.indexOf("}{@{}l@{\\hskip6pt}}{\\hrulefill}\\\\\r\n") != -1)
			{
				//System.out.println("2---------------"+XT.modelStyle);
				int tempind = 0;
				while(tableContents.indexOf("}{@{}l@{\\hskip6pt}}{\\hrulefill}\\\\\r\n", tempind) != -1)
				{
					tempind = tableContents.indexOf("}{@{}l@{\\hskip6pt}}{\\hrulefill}\\\\\r\n", tempind);

					int temp = tableContents.lastIndexOf("\\\\", tempind);
					tableContents.insert(temp+2,"[-3pt]");

					tempind += 10;
				}
			}

		}
		
//System.out.println(tableContents);
//System.in.read();

		return tableContents.toString();
	}



	public String processTableRow(String rowtag, boolean theadFlag)throws java.io.IOException
	{
		rowSepValue="";
		String tag= rowtag;
		String morerow="";
		boolean firstEntry= true;
		int entryPos= 0;
		int temp_entryPos=0;
		boolean topbrd=false;
		int ind=0;
		
				while (!tag.equals("</ROW>"))
					{
						
						char ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							//System.out.println("tag   "+tag);
							
							if (tag.startsWith("<ENTRY"))
							{
							
							morerow=xmlObj.getAttributeValue(tag, "MOREROWS");
							if(morerow.length()>0)
							{
								moreRow.put(new Integer(count), morerow);
								isMoreRows = true;
							}
							//**********[ Check Valign]***********
							/**
							*[05/03/2007]
							*Added By : Ravi
							* Change Request By: Vivek
							* Change Point: If entry tag contains Valign attribute
							*/
							Valign=xmlObj.getAttributeValue(tag, "VALIGN");
							
							//*********************************
								
							String tableEntryValue= "";
						if (tag.endsWith("/>"))
						{
							String rowsep = xmlObj.getAttributeValue(tag, "ROWSEP");
							tableEntryValue= "";
							int span=1;
							int nameend= 0;
							int namest= 0;
							try
							{
								String colend="";                 /////COL1 handling, 25-11-04
								colend=xmlObj.getAttributeValue(tag, "NAMEEND");
								colend=colend.replaceAll("[a-zA-Z]+","");
								nameend = Integer.parseInt(colend);
								if(nameend>0)
								{
									count=nameend;//Added by bhavesh
								
								}
								String colst="";
								colst=xmlObj.getAttributeValue(tag, "NAMEST");
								colst=colst.replaceAll("[a-zA-Z]+","");
								namest  = Integer.parseInt(colst);
								span        = nameend-namest+1;
							}
							catch(NumberFormatException numexp)
							{
							}
							if(rowsep.equals("1"))
							{
								if(span==1)
								{
									//temp_entryPos
									rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
									
								}
								else
								{
									rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";//+span;
									//System.out.println("entryPos  "+entryPos +" temp_entryPos  "+temp_entryPos);
									//System.in.read();
									
									//entryPos=nameend-1;
								}
							}
						}
						else
						{
							long l=xmlObj.fin.getFilePointer();
							if(tag.indexOf("NAMEEND=")!=-1)
							{
								String colend="";                 /////COL1 handling, 25-11-04
								colend=xmlObj.getAttributeValue(tag, "NAMEEND");
								colend=colend.replaceAll("[a-zA-Z]+","");
								entryPos = Integer.parseInt(colend)-1;
								temp_entryPos=entryPos;
								count=entryPos+1;//bhavesh
								//System.out.println("entryPos  "+entryPos +" temp_entryPos  "+temp_entryPos);
								//System.in.read();
									
							}
							else
								count++;//bhavesh
								
							//rowMerge();
							xmlObj.fin.seek(l);
							tableEntryValue= processTableEntry(tag, entryPos);
							//System.out.println("tableEntryValue   "+tableEntryValue);
							//System.in.read();
						}
						if(tableEntryValue.length()>0)
						{
							if (firstEntry==true)
							{
								rowContants.append("\r\n"+tableEntryValue);
								//System.out.println("tableEntryValue   "+tableEntryValue);
								//System.in.read();
							}
							else
							{
								//\epsfbox{fx43.eps}}
								if(tableEntryValue.startsWith("{%\r\n\\epsfbox{"))
								{
									//System.out.println(" After tableEntryValue   "+tableEntryValue);
									//System.in.read();
									if(tableEntryValue.endsWith("\r\n"))
									{
										//System.out.println("tableEntryValue"+tableEntryValue+":");
										//System.in.read();
										String te=tableEntryValue.toString();
										te=te.substring(0,te.length()-2);
										rowContants.append("\r\n"+" & "+te);
										//System.out.println(" qqqqqqqqqqqqq After tableEntryValue   "+te);
										//System.in.read();
									}
									else
									{
										rowContants.append("\r\n"+" & "+tableEntryValue);
									}

								}
								else
								{
									
									rowContants.append("\r\n"+" & "+tableEntryValue);
									//System.out.println(" After tableEntryValue   "+tableEntryValue);
									//System.in.read();
									
								}
							}
						}
						else
						{
							if (firstEntry==true)
								rowContants.append(" ");
							else
							{
									rowContants.append("\r\n"+" & ");
							}
							
							count++;//bhavesh added
							
						}
						entryPos++;
						firstEntry= false;
						rowMerge();//Cal for inserting & for row merge
					
						
					}
					
				}
				
				
			}//end of while
		
		//System.out.println(" After rowContants   "+rowContants);
		//System.out.println("entryValueWithVsp "+entryValueWithVsp);
		
		//System.out.println("END::"+rowContants);
//Avinandan DEclare til end mark
//
		/**
		*Date : 16/01/2008
		*Added By : Ravi
		* Change point: If Entry tag contain colsap tag
		*Change Request By: TPMS
		*/
		String rewSepn="";
		if(rowContants.lastIndexOf("\\\\[5pt]")!=-1)
		{
			rewSepn=HalfRowSpan(rowContants.toString(), "\\\\[5pt]");
			rowContants=new StringBuffer(rewSepn);
		}
		else if(rowContants.lastIndexOf("\\\\[10pt]")!=-1)
		{
			rewSepn=HalfRowSpan(rowContants.toString(), "\\\\[10pt]");
			rowContants=new StringBuffer(rewSepn);
		}
		else if(rowContants.lastIndexOf("\\\\[15pt]")!=-1)
		{
			rewSepn=HalfRowSpan(rowContants.toString(), "\\\\[15pt]");
			rowContants=new StringBuffer(rewSepn);
		}
		//end 16/01/2008
		//rowContants=new StringBuffer(rewSepn);
		String entryValueWithVsp=rowContants.toString();
		if(entryValueWithVsp.endsWith("\\\\[5pt]"))
		{
			
		}
		else if(entryValueWithVsp.endsWith("\\\\[10pt]"))
		{
		}
		else
		{
			rowContants.append("\\\\");
		}
	
		if(rowSepValue.length()>0)
		{
			
			rowSepValue=conCline(rowSepValue,theadFlag);
			//System.out.println("END::"+rowContants);
			if(rowContants.indexOf("<END>")>=0)
			{
//				System.out.println("END::"+rowContants);
				rowContants = rowContants.delete(0,7);
				rowContants = rowContants.delete(rowContants.length()-2,rowContants.length());
			}
			else
			{

				/**
				*Date : 15/09/2007
				*Modify point : cline position handling

				*/
				//System.out.println("rowSepValue "+rowSepValue);
				//System.in.read();
		
				//******************************************
					String temp_rowsep=rowSepValue;
					String first_val="";
					String last_val="";
					StringBuffer row_sep= new StringBuffer(temp_rowsep);
					int spos=0;
					int epos=0;
					int spos1=0;
					int epos1=0;
					boolean check_row_sep=false;
					int countFirst=0;
					int countLast=0;
					int fix=0;
					int counter1=0;

					spos=row_sep.indexOf("\\cline{",spos1);
						if(spos !=-1)
						{
							spos1=row_sep.indexOf("}",spos);
							if(spos1 !=-1)
							{
								first_val=row_sep.substring(spos+"\\cline{".length(),spos1);
								epos=first_val.indexOf("-",0);
								if(epos !=-1)
								{
									first_val=first_val.substring(epos+1,first_val.length());
									countLast=Integer.parseInt(first_val);
								}
							}
						}

					spos=0;
					spos1=0;
					epos=0;
					first_val="";
					while(spos !=-1)
					{
						spos=row_sep.indexOf("\\cline{",spos1);
						if(spos !=-1)
						{
							counter1++;
							spos1=row_sep.indexOf("}",spos);
							if(spos1 !=-1)
							{
								first_val=row_sep.substring(spos+"\\cline{".length(),spos1);
								epos=first_val.indexOf("-",0);
								if(epos !=-1)
								{
									first_val=first_val.substring(epos+1,first_val.length());
									countFirst=Integer.parseInt(first_val);
									if(countFirst-countLast==1)
									{
										if(counter1==1)
										{
											fix=countFirst;
										}
										countLast=countFirst;
										check_row_sep=true;
										//System.out.println("first  "+fix+"last "+countLast );
										//System.in.read();
									}
									else if(countFirst-countLast==0)
									{
										if(counter1==1)
										{
											fix=countFirst;
										}
									}
									else
									{
										//fix=countLast;
										//countLast=countFirst;
										//check_row_sep=false;
										//System.out.println("11111111111first  "+fix+"last "+countLast );
										//System.in.read();
										/*if(counter1-1<row_sep.length())
										{
											fix=countLast;
										}
										else*/
										break;
									}
								}
								
							}
						}

					}
					//System.out.println("-----------");
					//if(check_row_sep==true)
					if(fix !=countLast)
					{
						first_val="\\cline{"+fix+"-"+countLast+"}";
						if(row_sep.indexOf(first_val,0)==-1)
						{spos=0;
						spos=row_sep.lastIndexOf("\\cline{");
						if(spos !=-1)
							{
								row_sep.insert(spos,first_val);
								rowSepValue=row_sep.toString();
								row_sep= new StringBuffer("");
							}
						}
						//System.out.println("rowSepValue "+rowSepValue );
						//System.in.read();
					}

					//end of cline position handling

				//******************************************
				if(check_body_cline_after==true)
				{
					rowContants.append(rowSepValue);
					check_body_cline_after=false;
				}
				else
				rowContants.append("\r\n"+rowSepValue);
			}
			
		}
		else
		{
			
			if(rowContants.indexOf("<END>")>=0)
			{
				
				rowContants = rowContants.delete(0,7);
				/**
				*Added By Ravi [07/03/2007]
				* Change Request By: Vivek
				*Change Point : <END> Tag not required
				*/
				//System.out.println(rowContants);
				//System.in.read();
				if(rowContants.indexOf("<END>")!= -1)
				{
					
					rowContants = rowContants.delete(0,5);
					
				}
				/**
				* Date : 07/05/2007
				*Added By : Ravi 
				* Change Point : Delete [5pt] if rowContents contains only [5pt] value
				* Chenge Request By: Vivek.
				*/
				else if(rowContants.indexOf("[5pt]")!= -1)
				{

					/*if(rowContants.length()==7)
					{
						rowContants = rowContants.delete(0,5);
					}*/
				}
				//end
				rowContants = rowContants.delete(rowContants.length()-2,rowContants.length());
				//System.out.println(":"+rowContants+":");
				//System.in.read();
			}
		}
		rowSepValue="";

		hhline="";
		hhlinestart="";
		hhlineend="";
		topbrd=false;
		if(globalRowsep==true && rowContants.indexOf("\\botrule")==-1 && rowContants.indexOf("\\toprule")==-1)
			rowContants.append("\r\n\\colrule");

		//System.out.println(":"+rowContants+":");
		//System.in.read();

		return rowContants.toString();
	}

		/**
		*Date : 16/01/2008
		*Added By : Ravi
		* Change point: If Entry tag contain colsap tag
		*Change Request By: TPMS
		*/
public String HalfRowSpan(String Content, String Val) throws IOException
	{
		String rowCont=Content;
		String first="";
		String Sec="";
		String third="";
		String last="";
		int spos=0;
		int mpos=0;
		int epos=0;
		
		if(Content.lastIndexOf(Val)!=-1)//15/01/2008
		{
			
			spos=rowCont.lastIndexOf(Val);
			//System.out.println("spos "+spos);
			if(spos !=-1)
			{
				first=rowCont.substring(0,spos);
				//System.out.println("Before : first "+first);
				last=rowCont.substring(spos,rowCont.length());
				//System.out.println("last :  "+last);
				mpos=first.lastIndexOf(Val);
				if(mpos !=-1)
				{
					Sec=first.substring(0,mpos);
					third=first.substring(mpos+Val.length(),spos);
					StringBuffer st=new StringBuffer(Sec);
					while(epos !=-1)
					{
						
						epos=st.indexOf(Val,0);
						if(epos !=-1)
						{
							st=st.delete(epos,epos+Val.length());
						}
					}
					Sec=st.toString();
					first=Sec+third+last;
					//System.out.println("After : first "+first);
				}
			}
			rowCont=first;
			//System.in.read();
			
		}
		return rowCont;
	}
//Added by bhavesh for RowMerge
	public void rowMerge() throws IOException
	{
		
		String morerow="";
		int ind=0;
//	System.out.println(moreRow+" ------------- "+count);
//	System.in.read();	
		for(int i=count;i<=moreRow.size();i++)
		{
				morerow=(String)moreRow.get(new Integer(i));
				if(morerow!= null)
				{
					ind=Integer.parseInt(morerow);
				}				
				if(ind==1)
				{
					rowContants.append("\r\n"+" & ");
					System.out.println("rowContants  ::> "+rowContants);
					moreRow.put (new Integer(i),"0");
					count++;
					
				}
				else if(ind>1)
				{
					rowContants.append("\r\n"+" & ");
					morerow="";
					ind--;
					morerow=morerow+ind;
					moreRow.put(new Integer(i), morerow);
					count++;
				}
				else if(ind==0)
				{
					break;
				}
			}
			//	System.out.println(moreRow+" ------------- "+count);
			//System.out.println(moreRow+" ------------- "+count+"\n rowContants "+rowContants);
			//System.in.read();	
	}

	public String processTableEntry(String entryTag, int entryPos)throws java.io.IOException
	{
		String tableCellValue ="";
		String tag           = entryTag;
		String rowsep        = xmlObj.getAttributeValue(tag, "ROWSEP");		
		String morerows        = xmlObj.getAttributeValue(tag, "MOREROWS");

		String colsep			= xmlObj.getAttributeValue(tag, "COLSEP");

		String colend="";                 /////COL1 handling, 25-11-04
		colend=xmlObj.getAttributeValue(tag, "NAMEEND");
		colend=colend.replaceAll("[a-zA-Z]+","");
		
		String colst="";
		colst=xmlObj.getAttributeValue(tag, "NAMEST");
		colst=colst.replaceAll("[a-zA-Z]+","");
		
		hhlinestart        = colst;
		hhlineend        = colend;

		boolean leftBorder=false;
		boolean rightBorder=false;
		boolean topBorder=false;
		boolean bottomBorder=false;
		
		

		int span=1;
		int nameend= 0;
		int namest= 0;
		int namest_temp= 0;
		try
		{
			String colend1="";  //COL1 handling
			colend1=xmlObj.getAttributeValue(tag, "NAMEEND");
			colend1=colend1.replaceAll("[a-zA-Z]+","");
			nameend = Integer.parseInt(colend1);

			String colst1="";
			colst1=xmlObj.getAttributeValue(tag, "NAMEST");
			colst1=colst1.replaceAll("[a-zA-Z]+","");

			namest  = Integer.parseInt(colst1);
			namest_temp=namest;
			span        = nameend-namest+1;
			if(span !=1)
			rowsap_check=true;

		}
		catch(NumberFormatException numexp)
		{
		}
		if(rowsep.equals("1"))
		{
			//old [Blocked By Ravi 12/04/2007]
			//Below code is block temprary. this code is worked on cell border
			/*if(span==1)
			{
				if(rowsap_check==true)
				{
					namest_temp=entryPos+1;
					rowsap_check=false;
				}
				else
				{
					if(entryPos==0)
					{
						namest_temp=entryPos+1;

					}
					else
					{
						namest_temp=entryPos;
					}
					//System.out.println("entryPos : "+entryPos);//stop

				}
				if(check_body_cline==true)
				{
					//rowSepValue+= "[0pt]\n\\cline{"+(namest_temp)+"-"+(entryPos+1)+"}";
					rowSepValue+= "[0pt]\n\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
					check_body_cline=false;
					check_body_cline_after=true;
				}
				else
				{
					//rowSepValue+= "\\cline{"+(namest_temp)+"-"+(entryPos+1)+"}";
					rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
				}
			}
			else
			{

				if(check_body_cline==true)
				{
					rowSepValue+= "[0pt]\n\\cline{"+namest+"-"+nameend+"}";//+span;
					check_body_cline=false;
					check_body_cline_after=true;
				}
				else
				{
					rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";//+span;
				}
			}*/

			//end

			//System.out.println("entryPos : "+entryPos);
			/**
			*Added By Ravi
			* Date : [12/04/2007]
			* Change Request By : Vivek
			* Change Request handle extra rowspan
			*/
			if(entryPos> 0 )
				{
					if(span==1 )
					{
						rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
						
					}

					else
						{	
						rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";
						

						}//+span;
				}
				else
				{
						if(span==1)
							rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
						else
							rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";//+span;

				}
	
		}
		
	//System.out.println(tablecolAligns.size()+"...ravi ..."+count);
	//System.in.read();
	String mainalignValue ="";
	if(tablecolAligns.size()>= count)//[26/03/2007] //if multicolum contain more rows
		mainalignValue = (String)tablecolAligns.get(count-1);//Change by bhavesh entrypos to count-1

		String alignValue = getTableEntryAlignment(tag);
		
		if(alignValue.length() == 0)
		{
			alignValue="l";
		}
		String entryValue     = xmlObj.extractData("</ENTRY>", true);
		//System.out.println("entryValue    --> "+ entryValue+"            alignValue ----->"+alignValue);
		//System.out.println("entryValue    --> "+ entryValue);
		//System.in.read();	
		
		if(entryValue.indexOf("<RIGHT>")!=-1)
		{
			entryValue=entryValue.substring(0,entryValue.indexOf("<RIGHT>"))+entryValue.substring(entryValue.indexOf("<RIGHT>")+7);
			rightBorder=true;
		}
		if(entryValue.indexOf("<TOP>")!=-1)
		{
			entryValue=entryValue.substring(0,entryValue.indexOf("<TOP>"))+entryValue.substring(entryValue.indexOf("<TOP>")+5);
			topBorder=true;
			//10/09/2007
			top_row=true;
			cellcount=entryPos+1;
		}
		if(entryValue.indexOf("<BOTTOM>")!=-1)
		{
			entryValue=entryValue.substring(0,entryValue.indexOf("<BOTTOM>"))+entryValue.substring(entryValue.indexOf("<BOTTOM>")+8);
			bottomBorder=true;
		}
		if(entryValue.indexOf("<LEFT>")!=-1)
		{			
			entryValue=entryValue.substring(0,entryValue.indexOf("<LEFT>"))+entryValue.substring(entryValue.indexOf("<LEFT>")+6);
			leftBorder=true;
			
		}
		//System.out.println("colsep    --> "+ colsep);
		//System.in.read();
		int colsep_temp=0;
		if(colsep.length()>0)
		{
			colsep_temp=Integer.parseInt(colsep);
		}
		/*if(leftBorder==true && rightBorder==true && bottomBorder==true && topBorder==true)
			entryValue="{\\allborder{10}{10}}"+entryValue;
		else if(leftBorder==true && colsep_temp>0)
			entryValue="{\\lrborder{10}{0}{0}}"+entryValue+"{\\lrborder{10}{0}{0}}";
		else if(rightBorder==true && colsep_temp>0)
			entryValue="{\\lrborder{10}{0}{0}}"+entryValue+"{\\lrborder{10}{0}{0}}";
		else if(topBorder==true && colsep_temp>0)
			entryValue="{\\topborder{10}{0}{8}}"+entryValue+"{\\lrborder{10}{0}{0}}";
		else if(bottomBorder==true && colsep_temp>0)
			entryValue="{\\topborder{10}{0}{8}}"+entryValue+"{\\lrborder{10}{0}{0}}";
		else if(leftBorder==true || rightBorder==true)
			entryValue="{\\lrborder{10}{0}{0}}"+entryValue;
		else if(bottomBorder==true || topBorder==true)
			entryValue="{\\topborder{10}{0}{8}}"+entryValue;
		else if(colsep_temp>0)
			entryValue=entryValue+"{\\lrborder{10}{0}{0}}";
		*/

		//[06/09/2007]
//\multicolumn{1}{@{\hskip-6pt}|l|}{r11c5}
		if(leftBorder==true && rightBorder==true && bottomBorder==true && topBorder==true)
			entryValue="{\\allborder{10}{10}}"+entryValue;
		else if(leftBorder==true && rightBorder==true && colsep_temp==0)
			entryValue="\\multicolumn{1}{@{\\hskip-6pt}|l|}{"+entryValue+"}";
		else if(leftBorder==true && rightBorder==false && colsep_temp==0)
			entryValue="\\multicolumn{1}{@{\\hskip-6pt}|l@{}}{"+entryValue+"}";
		else if(leftBorder==false && rightBorder==true)
		{
			entryValue="\\multicolumn{1}{@{}@{}l|}{"+entryValue+"}";
			//System.out.println("1 entryValue : "+entryValue);
			//System.in.read();
		}
		else if((leftBorder==false ||colsep_temp>0)&& rightBorder==true)
		{	
			entryValue="\\multicolumn{1}{@{}@{}l|}{"+entryValue+"}";
			//System.out.println("2 entryValue : "+entryValue);
			//System.in.read();
		}
		else if((rightBorder==false ||colsep_temp>0)&& leftBorder==true)
		{
			entryValue="\\multicolumn{1}{@{\\hskip-6pt}|l|}{"+entryValue+"}";
			//System.out.println("3 entryValue : "+entryValue);
			//System.in.read();
		}
		else if((rightBorder==false && colsep_temp>0)&& leftBorder==false)
		{
			entryValue="\\multicolumn{1}{@{}@{}l|}{"+entryValue+"}";
			//System.out.println("4 entryValue : "+entryValue);
			//System.in.read();
		}

	if(topBorder==true)
	entryValue="\\Tcline{"+cellcount+"-"+cellcount+"}"+entryValue;

		//Avinandan Added
		entryValue=checkEntryValue(entryValue, alignValue);//declare by avinandan 
//System.out.println("entryValue ==> "+entryValue);
//System.in.read();
		if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
		{
			//System.out.println("entryValue==> "+entryValue);
			//System.in.read();
			if(entryValue.indexOf("\\(\\pm\\)")!=-1)
			{
				entryValue=entryValue.replaceFirst("\\\\pm","±");
				entryValue=entryValue.replaceFirst("\\(±","±");
				entryValue=entryValue.replaceFirst("\\\\±\\\\","±");
				entryValue=entryValue.replaceFirst("±\\)","±");
				entryValue=entryValue.replaceFirst("([ ]*)±([ ]*)","±");
				/**
				*Date : [05/03/2007]
				*This Part is blocked By Ravi
				* Comment Part : Underline  not working properly  with hbox 0pt
				* Change Request By : Ravi.
				*/
				/*if(entryValue.indexOf("\\underline{") != -1)
				{
					entryValue=entryValue.replaceFirst("±","{\\\\(\\\\,\\\\pm\\\\,\\\\)\\\\hbox{");
					entryValue="\\hbox"+entryValue;
				}
				else
				{
					entryValue=entryValue.replaceFirst("±","}\\\\hbox to 0pt{\\\\(\\\\,\\\\pm\\\\,\\\\)\\\\hbox{");
					entryValue="\\hbox{"+entryValue;
				}
				*/
				//end block
				entryValue=entryValue.replaceFirst("±","}\\\\hbox to 0pt{\\\\(\\\\,\\\\pm\\\\,\\\\)\\\\hbox{");
				entryValue="\\hbox{"+entryValue;

			}
			else
			{
				entryValue="\\hbox{"+entryValue;
			}
		}
		else if(alignValue.equals("&NDASH;"))
		{
			if(entryValue.indexOf("{\\ndash}")!=-1)
			{				
				entryValue=entryValue.replaceFirst("\\{\\\\ndash\\}","æ");
				entryValue=entryValue.replaceFirst("([ ]*)æ([ ]*)","æ");
				if(entryValue.indexOf("\\underline{") != -1)
				{
					entryValue=entryValue.replaceFirst("æ","{\\{\\\\ndash\\}\\\\hbox{");
					entryValue="\\hbox"+entryValue;
				}
				else
				{
					entryValue=entryValue.replaceFirst("æ","}\\\\hbox to 0pt{\\{\\\\ndash\\}\\\\hbox{");
					entryValue="\\hbox{"+entryValue;
				}
			}
			else
			{
				entryValue="\\hbox{"+entryValue;
			}
		}
		//end mark
		//Avinandan DEclare til end mark
		String entryValueWithVsp=entryValue;
		
		//Added By Ravi [18/11/2006 feedback by Vivek for table colhead]
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))|| (XT.jid.equalsIgnoreCase("ZOOGA"))|| (XT.modelStyle.equals("-NEULAB"))||(XT.modelStyle.equals("-PIO")))
			{
				if(boltablColHead==true)
				{
					entryValueWithVsp="\\TblColHead{"+entryValueWithVsp+"}";
					//System.out.println("222222222222222222222  "+entryValueWithVsp);
				}
			}
		//end
		boolean chk=false;
		
		if(entryValueWithVsp.indexOf("\\hbox to 0pt")!=-1)
		{
			
			//entryValueWithVsp+="}";//old
			
			
			//*************************************************************
			//[10/01/2007]
			
			String lv=entryValueWithVsp.substring(0,entryValueWithVsp.indexOf("\\hbox to 0pt"));
			String Rv=entryValueWithVsp.substring(entryValueWithVsp.indexOf("\\hbox to 0pt")+"\\hbox to 0pt".length());
			
				if(Rv.lastIndexOf("}}") != -1)
				{
					
					String tr1=entryValueWithVsp.substring(0,entryValueWithVsp.lastIndexOf("}}")+2);
					if(tr1.length() != entryValueWithVsp.length())
					{
							StringBuffer sb1=new StringBuffer(Rv);
							sb1=sb1.insert(sb1.lastIndexOf("}}"),"}");
							Rv=sb1.toString();
							
							if(lv.indexOf("\\bf{{\\underline{") != -1)
							{
								if(Rv.indexOf("\\bf{{\\underline{") != -1)
								{
									
									if(Rv.indexOf("\\bf{{\\underline{") != -1)
									{
											//System.out.println("entryValueWithVsp==> "+entryValueWithVsp);
												int t1=0;
												int t2=0;
												int t3=0;
												t1=Rv.lastIndexOf("}}}}");
												String tt= "";
												String tt2= "";
												String tt3= "";
												boolean ch_hyp=false;
												boolean b=false;
												boolean c=false;
												if(t1 != -1)
												{
													//System.out.println("1 Rv==>"+Rv);
													t2=Rv.indexOf("}}",0);
													if(t2 != -1)
													{
														if(Rv.lastIndexOf("}}}}\\)")!= -1)
														{
															tt=Rv.substring(0,t2+2)+"}";
															//System.out.println(" tt ==>"+tt);
															if(Rv.indexOf("\\hyperlink{",0)!=-1)
															{ 
																c=true;
																//System.out.println("Rv==>"+Rv);
																tt2=Rv.substring(t2+2,Rv.length()-6)+"\\)}";
																//System.out.println("tt2==>"+tt2);
																if(tt.indexOf("{\\bf{{\\underline",0)!= -1)
																{
																	StringBuffer sb2= new StringBuffer(tt);
																	int in1 =sb2.indexOf("{\\bf{{\\underline",0);
																	if(in1 != -1)
																	{
																		sb2=sb2.delete(in1-2,in1+"{\\bf{{\\underline".length());
																		tt=sb2.toString();
																	}
																	Rv=tt+tt2;
																	
																	
																}
																else
																{
																	Rv=tt+tt2;
																	if(lv.endsWith("{\\underline{}"))
																	{
																		lv=lv.substring(0,(lv.length()-"{\\underline{}".length()))+"}";
																		Rv=Rv+"}";
																	}
																	else if(lv.endsWith("{\\underline{\\,}"))
																	{
																		b=true;	
																	}
																	//System.out.println("lv==>"+lv);
																	//System.out.println("Rv==>"+Rv);
																	//System.in.read();
																}
																
																chk=true;
																ch_hyp=true;
															}
															else if(lv.indexOf("\\bf{{\\underline{") != -1)
															{
																tt2=Rv.substring(t2+2,Rv.length()-6)+"}}}\\)";
																Rv=tt+tt2;
															}
															else
															{
																
															}
														}
														
														
													}

												}
										//******************************************
										if(b==true)
										{
											int in=Rv.indexOf("\\hyperlink{",0);
											String s1="";
											String s2="";
											if(in !=-1)
											{
												s1=Rv.substring(0,in);
												s2=Rv.substring(in,Rv.length());
												Rv=s1+"}"+s2;
											}
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf{\\underline"+Rv;
											//System.out.println("entryValueWithVsp==>"+entryValueWithVsp);
											//System.in.read();
											ch_hyp=false;
											b=false;
										}
										else if(c==true)
										{
											int in=Rv.indexOf("\\hyperlink{",0);
											String s1="";
											String s2="";
											if(in !=-1)
											{
												s1=Rv.substring(0,in);
												s2=Rv.substring(in,Rv.length());
												Rv=s1+"}"+s2;
											}
											Rv=Rv.substring(0,Rv.length()-1);
											entryValueWithVsp=lv+"\\hbox to 0pt{{\\bf{\\underline"+Rv;
											//System.out.println("entryValueWithVsp==>"+entryValueWithVsp);
											//System.in.read();
											ch_hyp=false;
											c=false;
										}

										else if(ch_hyp==true)
										{
											entryValueWithVsp=lv+"\\hbox to 0pt{{\\bf{\\underline"+Rv;
											//System.out.println("entryValueWithVsp==>"+entryValueWithVsp);
											//System.in.read();
											ch_hyp=false;
										}
										else
										{
											entryValueWithVsp=lv+"\\hbox to 0pt{\\bf{\\underline"+Rv+"}}";
										}
										chk=true;
									}
									else
									{
										chk=false;
									}
									
								}
								else if(Rv.indexOf("\\it") != -1)
								{
									if(Rv.indexOf("\\it") != -1)
									{
										sb1=sb1.insert(sb1.indexOf("}}"),"}");
										Rv=sb1.toString();
										if(lv.indexOf("\\bf") != -1)
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
										}
										else
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
										}
										chk=true;
									}
									else
									{
										chk=false;
									}
									
								}
								else
								{
									int t1=0;
									int t2=0;
									int t3=0;
									t1=Rv.lastIndexOf("}}}}");
									String tt= "";
									String tt2= "";
									String tt3= "";
									
									if(t1 != -1)
									{
										t2=Rv.indexOf("}}",0);
										if(t2 != -1)
										{
											if(Rv.lastIndexOf("}}}}\\)")!= -1)
											{
												tt=Rv.substring(0,t2+2)+"}";
												tt2=Rv.substring(t2+2);
												if(Rv.indexOf("\\hyperlink{",0)!=-1)
												{
													StringBuffer sb= new StringBuffer(Rv);
													sb=sb.insert(sb.indexOf("\\hyperlink{",0),"}");
													Rv=sb.toString();
													tt2=Rv.substring(t2+2,Rv.length()-6)+"\\)}";
													chk=true;
												}
												else if(lv.indexOf("\\bf{{\\underline{") != -1)
												{
													tt2=Rv.substring(t2+2,Rv.length()-6)+"}}}\\)";
													chk=true;
												}
												else
												{
													
												}
											}
											
											Rv=tt+tt2;
										}
									}
									
									entryValueWithVsp=lv+"}}}}\\hbox to 0pt{{\\bf{{\\underline{"+Rv;
								}
							}
							//[02/07/2007]
							else if(lv.indexOf("\\bf") != -1)
							{
								//System.out.println("entryValueWithVsp==> "+entryValueWithVsp+"\n Rv==> "+Rv);
								//System.in.read();
								//entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
								if(Rv.indexOf("\\bf") != -1)
								{
									if(Rv.indexOf("\\bf") != -1)
									{
										
										if(Rv.indexOf("\\hyperlink{",0)==-1)
										{
											sb1=sb1.insert(sb1.indexOf("}}"),"}");
											Rv=sb1.toString();
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
											//System.out.println("entryValueWithVsp.length()==> "+entryValueWithVsp);
											//System.in.read();
											chk=true;

										}
										else
										{
											//System.out.println("entryValueWithVsp==> "+entryValueWithVsp+"\n Rv ==> "+Rv);
											//System.in.read();
											String first=Rv.substring(0,Rv.indexOf("\\hyperlink{",0));
											String second=Rv.substring(Rv.indexOf("\\hyperlink{",0),Rv.length());
											Rv=first+"}"+second;
											Rv=Rv.substring(0,Rv.length()-8)+"}}\\)}";
											entryValueWithVsp=lv+"\\hbox to 0pt"+Rv;
										}
									}
									else
									{
										chk=false;
									}
									
								}
								else if(Rv.indexOf("\\it") != -1)
								{
									if(Rv.indexOf("\\it") != -1)
									{
										sb1=sb1.insert(sb1.indexOf("}}"),"}");
										Rv=sb1.toString();
										if(lv.indexOf("\\bf") != -1)
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
										}
										else
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
										}
										chk=true;
									}
									else
									{
										chk=false;
									}
									
								}
								else
								{
									//System.out.println("entryValueWithVsp==> "+entryValueWithVsp);
									//System.in.read();
									//entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
									//System.out.println("lv==>"+lv);
									//System.out.println("Rv==>"+Rv);
									//System.in.read();
									int t1=0;
									int t2=0;
									int t3=0;
									boolean chk1=false;//26/11/2009
									t1=Rv.lastIndexOf("}}}}");
									String tt= "";
									String tt2= "";
									String tt3= "";
									if(t1 != -1)
									{
										t2=Rv.indexOf("}}",0);
										if(t2 != -1)
										{
											//tt=Rv.substring(0,t2+2)+"}{";
											//System.out.println("Rv==>"+Rv);
											if(Rv.lastIndexOf("}}}}\\)")!= -1)
											{
												tt=Rv.substring(0,t2+2)+"}";
												//System.out.println("Rv==>"+Rv+"\ntt ==> "+tt);
												//System.in.read();
												//tt2=Rv.substring(t2+2);
												if(Rv.indexOf("\\hyperlink{",0)!=-1)
												{
													if(lv.endsWith("}}}"))
													{
														tt2=Rv.substring(t2+2,Rv.length()-7)+"\\)}";
														//System.out.println("tt2.length()"+tt2.length());
														if(tt2.length()==3)
														{
															tt2=tt2+"}";
														}
													}
													else
													{
														tt2=Rv.substring(t2+2,Rv.length()-7)+"}}\\)}";
													}
												}
												else
												{
													//System.out.println((t2+2)+" Rv " +(Rv.length()-6));
													if(((t2+2)<(Rv.length()-6)))//26/11/2009
													tt2=Rv.substring(t2+2,Rv.length()-6)+"}}}\\)";
													else//26/11/2009
														 chk1=true;;//26/11/2009
													//System.out.println(entryValueWithVsp+" \ntt=="+tt+"\ntt2=>"+tt2+"\nRv "+Rv);
												}
												
												//System.out.println("tt=="+tt+"\ntt2=>"+tt2);
												//System.in.read();
											}
											
											Rv=tt+tt2;
										}
									}
									//System.out.println("lv==>"+lv);
									//System.out.println("Rv==>"+Rv);
									//System.in.read();
									//if(Rv.indexOf("{\\bf",0)!=-1)
//chk1
									if(Rv.endsWith("}}}}\\)}"))
									{
										if(lv.endsWith("}}\\,}"))
										{
											Rv=Rv.substring(0,Rv.length()-7)+"}}\\)}}";
											entryValueWithVsp=lv+"\\hbox to 0pt"+Rv;
											//System.out.println("Ravi  "+Rv);
											//System.in.read();
										}
										else
										{
											Rv=Rv.substring(0,Rv.length()-7)+"}}}\\)}";
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{"+Rv;
											//System.out.println("Ravi  "+Rv);
											//System.in.read();
										}
										//System.out.println("Ravi  "+Rv);
										//System.in.read();
									}
									else if(lv.endsWith("}}}"))
									{
										
										entryValueWithVsp=lv+"\\hbox to 0pt"+Rv;
									}
									else if(lv.endsWith("}}\\,}"))
									{
										if(chk1==true)//26/11/2009
										{
											entryValueWithVsp=lv+"\\hbox to 0pt"+Rv+"}";
											chk1=false;
										}else//26/11/2009
										entryValueWithVsp=lv+"\\hbox to 0pt"+Rv+"}}";
									}
									else
									{
										entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
										//chk=true;
									}
									//}}}}\)}
									//if(Rv.indexOf("(^{{\\rmbox{([^<]+)}}}}")!=-1)
									
									//System.out.println("lv==>"+lv);
									//System.out.println("Rv==>"+Rv);
									//System.in.read();
									//System.out.println("entryValueWithVsp==> "+entryValueWithVsp);
									//System.in.read();
								}
							}
							else if(lv.indexOf("\\it") != -1)
							{
								//System.out.println("entryValueWithVsp : "+entryValueWithVsp);
								//System.out.println("lv==>"+lv);
								//System.out.println("Rv==>"+Rv);
								//System.in.read();
								//entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
								if(Rv.indexOf("\\it") != -1)
								{
									if(Rv.indexOf("\\it") != -1)
									{
										//sb1=sb1.insert(sb1.indexOf("}}"),"}");
										//Rv=sb1.toString();
										int in =sb1.indexOf("\\hyperlink{",0);
										if(in != -1)
										{
											if(Rv.endsWith("{}"))
											{
												
												int mn=Rv.indexOf("}}}}\\)}{}",0);
												if(mn != -1)
												{
													Rv=Rv.substring(0,Rv.length()-9)+"}}}\\)}";
												}
												//Rv=Rv.replaceAll("\\{}","");
												String fst=Rv.substring(0,Rv.indexOf("\\hyperlink{",0));
												String last=Rv.substring(Rv.indexOf("\\hyperlink{",0),Rv.length());
												//System.out.println("fst==>"+fst);
												//System.out.println("last==>"+last);
												Rv=fst+"}}}"+last;
											}
											entryValueWithVsp=lv+"\\hbox to 0pt{{\\it"+Rv;
											//System.out.println("Rv11==>"+Rv);
											//System.in.read();
										}
										else
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
											chk=true;
										}
									}
									else
									{
										entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
										chk=false;
									}
									
								}
								else if(Rv.indexOf("\\bf") != -1)
								{
									if(Rv.indexOf("\\bf") != -1)
									{
										sb1=sb1.insert(sb1.indexOf("}}"),"}");
										Rv=sb1.toString();
										if(lv.indexOf("\\it") != -1)
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
										}
										else
										{
											entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;
										}
										
										chk=true;
									}
									else
									{
										chk=false;
									}
									
								}
								
								else
								{
									entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\it"+Rv;
								}
							}
							else 
							{
								//entryValueWithVsp+="}";//old
								/**
								* Added By Ravi [05/03/2007]
								* Change Request By : Vivek
								* Change Point : underline with hbox to 0pt
								*/
								if(Rv.indexOf("\\underline") != -1)
								{
									entryValueWithVsp=lv+"\\hbox to 0pt{{"+Rv;
								}
								entryValueWithVsp+="}";
								//end
							}
						
					}
					else
					{
						//[10/01/2007 new change]
						//entryValueWithVsp+="}";
						StringBuffer sb2=new StringBuffer(Rv);
						//System.out.println("Ravi entryValueWithVsp==> "+entryValueWithVsp);
						//System.out.println("sb2==> "+sb2);
						//System.in.read();
						sb2=sb2.insert(sb2.indexOf("}}"),"}");
						Rv=sb2.toString();
						if(lv.indexOf("\\bf{{\\underline{") != -1)
						{
							if(lv.endsWith("}}}\\,}"))
							{
								//System.out.println("entryValueWithVsp==> "+entryValueWithVsp);
								//System.out.println("lv==> "+lv);
								//System.out.println("Rv==> "+Rv);
								//System.in.read();
								entryValueWithVsp=lv+"\\hbox to 0pt{"+Rv;
							}
							else
							{
								entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf{{\\underline{"+Rv+"}}";
							}
						}
						else if(sb2.indexOf("\\bf") != -1)
						{
							//entryValueWithVsp=lv+"}}\\hbox to 0pt{{\\bf"+Rv;

							entryValueWithVsp=lv+"\\hbox to 0pt{{\\bf"+Rv+"}}";
							
						//	chk=true;
						}
						else
						{
							entryValueWithVsp+="}";
						//	chk=false;
						}
						//end
						
					}
				}
				else
				{
					entryValueWithVsp+="}";
						
				}
				
			//end
			
			//*************************************************************
		}
		
		int ind=0;
		if((ind=entryValueWithVsp.indexOf("\\\\[5pt]\r\n"))>0)
		{
			entryValueWithVsp=entryValueWithVsp.substring(0,ind)+"}"+entryValueWithVsp.substring(ind, ind+7);
		}
		else if((ind=entryValueWithVsp.indexOf("\\\\[10pt]\r\n"))>0)
		{
			entryValueWithVsp=entryValueWithVsp.substring(0,ind)+"}"+entryValueWithVsp.substring(ind, ind+8);
		}
		else
		{
			if(entryValue.indexOf("\\underline{") != -1 && (alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;") || alignValue.equals("&NDASH;")||alignValue.equals("&#XB1;")))
			{
				entryValueWithVsp=entryValueWithVsp+"}";
			}
			//	entryValueWithVsp=entryValueWithVsp+"}";//old
			//[10/01/2007]
			if(chk==false)
			{
				entryValueWithVsp=entryValueWithVsp+"}";
			}
			//end

		}
		//end mark
				//\epsfbox{
		//System.out.println("entryValue "+entryValue);
		//System.in.read();
			//Added By Ravi [18/11/2006 feedback by Vivek for table colhead]

			if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))||(XT.modelStyle.equals("-NEULAB"))||(XT.modelStyle.equals("-ZOOGA"))||(XT.modelStyle.equals("-PIO")))
			{
				if(boltablColHead==true)
				{
					entryValue= getEntryValueWithAlignmaent(alignValue, entryValueWithVsp);
				}
				else
				{
					entryValue= getEntryValueWithAlignmaent(alignValue, entryValue);
				}

			}
			else
			{
				entryValue= getEntryValueWithAlignmaent(alignValue, entryValue);
				
			}
	

		if(entryValue.startsWith("\\\\"))
		{
			tableCellValue= "<END>"+entryValue.substring(2);
			//System.out.println("tableCellValue==> "+tableCellValue);
		}
		else if(morerows.length()>0)
		{
			int g=Integer.parseInt(morerows);
			g=g+1;

			if(entryValueWithVsp.indexOf("\\hbox to 0pt") != -1)
			{
				entryValueWithVsp+="}";
			}
			//tableCellValue= "\\multirow{"+g+"}{0in}{"+entryValueWithVsp;//old[05/03/2007]
			/**
				*[05/03/2007]
				*Added By : Ravi
				* Change Request By: Vivek
				* Change Point: If entry tag contains Valign attribute
				*/
				//System.out.println("Valign "+Valign);
				//System.in.read();
				if(Valign.equalsIgnoreCase("MIDDLE"))
				{
					if(namest>0){//Added on 04-01-2019 
						System.out.println("namest :: "+namest+" ___ "+" nameend :: "+nameend);
						int colDiff = nameend-namest;
						System.out.println("colDiff :: "+colDiff);
						if(colDiff>0){
							String colDiffTab = "";
							for(int i=0;i<colDiff;i++){
								colDiffTab+=" &";
							}
							tableCellValue= colDiffTab+" \\Cmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}else{
							tableCellValue= "\\Cmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}
					}else{
						tableCellValue= "\\Cmultirow{"+g+"}{0in}{"+entryValueWithVsp;
					}
				}
				else if(Valign.equalsIgnoreCase("BOTTOM"))
				{
					if(namest>0){//Added on 04-01-2019 
						System.out.println("namest :: "+namest+" ___ "+" nameend :: "+nameend);
						int colDiff = nameend-namest;
						System.out.println("colDiff :: "+colDiff);
						if(colDiff>0){
							String colDiffTab = "";
							for(int i=0;i<colDiff;i++){
								colDiffTab+=" &";
							}
							tableCellValue= colDiffTab+" \\Bmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}else{
							tableCellValue= "\\Bmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}
					}else{
						tableCellValue= "\\Bmultirow{"+g+"}{0in}{"+entryValueWithVsp;
					}
				}
				else if(Valign.equalsIgnoreCase("TOP"))
				{
					if(namest>0){//Added on 24-12-2019 
						System.out.println("namest :: "+namest+" ___ "+" nameend :: "+nameend);
						int colDiff = nameend-namest;
						System.out.println("colDiff :: "+colDiff);
						if(colDiff>0){
							String colDiffTab = "";
							for(int i=0;i<colDiff;i++){
								colDiffTab+=" &";
							}
							tableCellValue= colDiffTab+" \\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}else{
							tableCellValue= "\\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}
					}else{
						tableCellValue= "\\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
					}
				}
				else
				{
					if(namest>0){//Added on 04-01-2019 
						System.out.println("namest :: "+namest+" ___ "+" nameend :: "+nameend);
						int colDiff = nameend-namest;
						System.out.println("colDiff :: "+colDiff);
						if(colDiff>0){
							String colDiffTab = "";
							for(int i=0;i<colDiff;i++){
								colDiffTab+=" &";
							}
							tableCellValue= colDiffTab+" \\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}else{
							tableCellValue= "\\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
						}
					}else{
						tableCellValue= "\\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
					}
				}
				//end
			Valign="";
			morerows="";
		}
		else if(!alignValue.equals(mainalignValue))
		{
			//System.out.println("alignValue "+alignValue+" mainalignValue "+mainalignValue);
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
				{
					//[10/01/2007]
					if(	chkblnk==true)
					{
						tableCellValue= entryValue;
						chkblnk=false;
					}
					else
					{
						tableCellValue= entryValueWithVsp;

					}
				}
			else if(alignValue.equals("&NDASH;"))
				tableCellValue= entryValueWithVsp;
			//else if(alignValue.equals("+"))
			//	tableCellValue= entryValueWithVsp;
			else if(alignValue.equals("&TIMES;"))
				tableCellValue= "\\multicolumn{"+span+"}{@{}ô@{}}{"+entryValueWithVsp;
			else
			{
				if(!(alignValue.equalsIgnoreCase("l") || alignValue.equalsIgnoreCase("r") || alignValue.equalsIgnoreCase("c")))
				{
					//System.out.println("Ravi entryValueWithVsp ==> "+entryValueWithVsp);
					//System.out.println("entryValue==> "+entryValue);
				//	System.in.read();
					String rep=alignValue;
					if(rep.equals("("))
						rep="\\(";
					if(rep.equals("["))
						rep=" \\[";
					if(rep.equals("."))
						rep="\\.";
					if(rep.equals("*"))
						rep="\\*";
					if(rep.equals("+"))
						rep="\\+";
					if(rep.equals("\\("))
					{
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{\\\\"+rep);
						//System.out.println("entryValueWithVsp ==> "+entryValueWithVsp);
						//System.in.read();
					}
					if(rep.equals("\\["))
					{
						//System.out.println("22222entryValueWithVsp ==> "+entryValueWithVsp);
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{ "+rep);
						//System.out.println("entryValueWithVsp ==> "+entryValueWithVsp);
						//System.in.read();
					}
					if(rep.equals("/"))
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{"+rep);
					else
					{
						if(entryValueWithVsp.indexOf("\\\\hbox to 0pt{") != -1)
							entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{"+rep);
					}

					//System.out.println("entryValueWithVsp ==> "+entryValueWithVsp);
					//System.in.read();
				}
				if(alignValue.equalsIgnoreCase("("))
				{
					if(entryValue.indexOf("\\hbox",0)!=-1)
					{
						entryValueWithVsp=entryValue;
					}
					else
					{
						entryValueWithVsp=entryValue;
					}
					//tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;//old[05/03/2007]
					tableCellValue=entryValueWithVsp;//old[05/03/2007]
					//System.out.println("entryValueWithVsp ==> "+entryValueWithVsp);
					//System.in.read();

				}
				else
				tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;//old[05/03/2007]
				;
				/**
				*[05/03/2007]
				*Added By : Ravi
				* Change Request By: Vivek
				* Change Point: If entry tag contains Valign attribute
				*/
/*
				if(Valign.equalsIgnoreCase("MIDDLE"))
				{
					tableCellValue= "\\Cmulticolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
				}
				else if (Valign.equalsIgnoreCase("BOTTOM"))
				{
					tableCellValue= "\\Bmulticolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
				}
				else if (Valign.equalsIgnoreCase("TOP"))
				{
					tableCellValue= "\\Tmulticolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
				}
				else
				{
					tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
				}
				//end
		*/
				Valign="";
				
				if(tableCellValue.indexOf("\\hbox to 0pt{") != -1 && !alignValue.equalsIgnoreCase("("))
				{
					tableCellValue+="}";
					//System.out.println("tableCellValue ==> "+tableCellValue);
					//System.in.read();
				}
			}
			
		}
		else if(span>1)
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
				tableCellValue= "\\multicolumn{"+span+"}{@{}±@{}}{"+entryValueWithVsp;
			else if(alignValue.equals("&TIMES;"))
				tableCellValue= "\\multicolumn{"+span+"}{@{}ô@{}}{"+entryValueWithVsp;
			else if(alignValue.equals("&NDASH;"))
				tableCellValue= "\\multicolumn{"+span+"}{@{}æ@{}}{"+entryValueWithVsp;
			else
				tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
		}
		else
		{
			
			tableCellValue= entryValue;
			
			//System.out.println("tableCellValue==> "+tableCellValue);
			//System.in.read();
			//Added By Ravi [18/11/2006 feedback by Vivek for table colhead]
			if(boltablColHead==true)
				{
					StringBuffer stbuf= new StringBuffer(tableCellValue);
					int s=0;
					int e=0;;
					String tr=null;
					s=stbuf.lastIndexOf("}}");
					if(s != -1)
					{
						tr=stbuf.substring(0,s);
						stbuf.delete(0,s+1);
						stbuf.insert(0,tr);
					}
					tableCellValue= stbuf.toString();
				}
		}

		boolean b= Pattern.matches("\\\\hbox\\{([0-9]+)([^0-9\\.])([^\\.]+)\\}",tableCellValue);
		
		if(b)
		{
			if(tableCellValue.indexOf("\\hbox to 0pt")==-1)
			{
				//System.out.println("Before tableCellValue -->"+tableCellValue);
				/**
				* Modify Date : [07/08/2007]
				* Modify By: Ravi
				* Change Point : comma and space seperator without hbox to 0pt
				* Chage Request : TPMS change request Table Alignment
				*/
				//tableCellValue=tableCellValue.replaceFirst("\\\\hbox\\{([0-9]+)([^0-9\\.])([^\\.]+)\\}","\\\\hbox\\{$1\\}\\\\hbox to 0pt\\{$2$3\\}");//old
				if(tableCellValue.indexOf(",",0)!=-1)
				{
				
				}
				else if(tableCellValue.indexOf(" ",0)!=-1)
				{
				}
				else
				{
					tableCellValue=tableCellValue.replaceFirst("\\\\hbox\\{([0-9]+)([^0-9\\.])([^\\.]+)\\}","\\\\hbox\\{$1\\}\\\\hbox to 0pt\\{$2$3\\}");
				}
				//end
			}
			
		}
		//System.out.println("tableCellValue -->"+tableCellValue);
		//System.in.read();
 		return tableCellValue;
	}
	//Avinandan declare checkEntryValue function
	public String checkEntryValue(String entryValue, String alignValue) throws IOException
	{
			
		//System.out.println("entryValue1111111111 ==>  "+entryValue);
		//System.in.read();		
		if((entryValue.startsWith("{\\bf{")) && (entryValue.endsWith("}}")))
		{
			
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
			{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");
					//System.out.println("entryValue1111111111 ==>  "+entryValue);
					
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					{
						entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\bf{");
						//System.out.println("entryValue 222  ==>  "+entryValue);
						//System.in.read();
					}
					//System.in.read();
					
				
			}
			else if(alignValue.equals("&NDASH;"))
			{
				if(entryValue.indexOf("{\\ndash}")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\{\\\\ndash\\}([ ]*)","{\\\\ndash}");					
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\{\\\\ndash\\}","}}{\\\\ndash}{\\\\bf{");
				}
			}
			else if(alignValue.equals("."))
			{
				
				if(entryValue.indexOf(".")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\.([ ]*)",".");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\.","}}.{\\\\bf{");
				}
			}
		}//end of if
		else if((entryValue.startsWith("{\\it{\\bi{") || entryValue.startsWith("{\\bf{\\bi{") || entryValue.startsWith("{{\\bi{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
			{
				if(entryValue.indexOf("\\(\\pm\\)")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");
				//	if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
				//	entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\bi{");
				}
			}
			else if(alignValue.equals("&NDASH;"))
			{
				if(entryValue.indexOf("{\\ndash}")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\{\\\\ndash\\}([ ]*)","{\\\\ndash}");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\{\\\\ndash\\}","}}{\\\\ndash}{\\\\bi{");
				}
			}
			else if(alignValue.equals("."))
			{
				if(entryValue.indexOf(".")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\.([ ]*)",".");
					
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					{
					//	entryValue=entryValue.replaceAll("\\.","}}.{\\\\bi{");
						
					}
				}
			}
		}//end of else if
		else if((entryValue.startsWith("{\\it{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
			{
				if(entryValue.indexOf("\\(\\pm\\)")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
						entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\it{");
				}
			}
			else if(alignValue.equals("&NDASH;"))
			{
				if(entryValue.indexOf("{\\ndash}")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\{\\\\ndash\\}([ ]*)","{\\\\ndash}");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\{\\\\ndash\\}","}}{\\\\ndash}{\\\\it{");
				}
			}
			else if(alignValue.equals("."))
			{
				if(entryValue.indexOf(".")!=-1)
				{
					
					entryValue=entryValue.replaceAll("([ ]*)\\.([ ]*)",".");
					
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					{
						//entryValue=entryValue.replaceAll("\\.","}}.{\\\\it{");
					}
				}
			}
		}//end of else if
		else if((entryValue.startsWith("{\\underline{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;")||alignValue.equals("&#XB1;"))
			{
				if(entryValue.indexOf("\\(\\pm\\)")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");					
					//entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\underline{");
				}
			}
			else if(alignValue.equals("&NDASH;"))
			{
				if(entryValue.indexOf("{\\ndash}")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\{\\\\ndash\\}([ ]*)","{\\\\ndash}");

					//entryValue=entryValue.replaceAll("\\{\\\\ndash\\}","}}{\\\\ndash}{\\\\underline{");
				}
			}
			else if(alignValue.equals("."))
			{
				if(entryValue.indexOf(".")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\.([ ]*)",".");
					//entryValue=entryValue.replaceAll("\\.","}}.{\\\\underline{");
				}
			}
		}//end of else if
		

			//System.out.println("entryValue : "+entryValue);
			//System.in.read();
		
		return entryValue;
	}
	//end of checkEntryValue()

	public String getTableAlignmentChar(String alignAttributeValue)throws java.io.IOException
	{
		String tableAlignmentChar= "l";
		if (alignAttributeValue!=null && alignAttributeValue.length()>0)
		{
			if(alignAttributeValue.equals("CENTER"))
				tableAlignmentChar= "c";
			else if(alignAttributeValue.equals("RIGHT"))
				tableAlignmentChar= "r";
			else
			{
				tableAlignmentChar= alignAttributeValue;
			}
		}

 		return tableAlignmentChar;
	}

	public Vector getTableAlignment(String etag)throws IOException
	{
		String tag= etag;
		
		
		newComL=new Hashtable();
		newComR=new Hashtable();
		newComChar=new Hashtable();
		 colNum=0;
		String ColValue="";
		//end mark
		etag=etag.substring(etag.indexOf("COLS=\"")+"COLS=\"".length());
		etag=etag.substring(0,etag.indexOf("\""));
		columCount=Integer.parseInt(etag);
		
		//System.out.println(columCount+"etag"+etag);
		long filePos= xmlObj.fin.getFilePointer();
		String maxCols= xmlObj.getAttributeValue(tag, "COLS");
		Vector alignments= new Vector();
		Vector charColAligns= new Vector();
		String rs1="";
		String entryAlign="";
		while (!tag.equals("</TGROUP>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				
				if (tag.startsWith("<ROW"))
				{
					//Change by baavesh for allignment of the entry
					colNum=1;					
					colAlign=new Vector();
					int ii=0;
					for(int j=colNum;j<=columCount;j++)
					{
						String temp=(String)AllignTable.get(new Integer(j));
						if(temp!= null)
						{
							ii=Integer.parseInt(temp);
						}
						if(ii>0&&temp!= null)
						{					
							ii--;
							rs1=""+ii;
							AllignTable.put(new Integer(j),rs1);
							String ss=(String)Allin_table.get(new Integer(j));
							colAlign.add(ss);
							
							colNum=j;
							rs1="";
							
						}
						else 
						{						
							colNum=j;	
							break;
						}
					
					
					}//End for alignment
					
					while (!tag.equals("</ROW>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if(tag.startsWith("<ENTRY"))
							{
								
								rs1=xmlObj.getAttributeValue(tag, "MOREROWS");
								 entryAlign= getTableEntryAlignment(tag);
								 //System.out.println("rs1 "+rs1+" "+tag+"entryAlign"+entryAlign);
								 //System.in.read();
								 if(entryAlign.length()==0)
								 {
								 	//entryAlign="L";
								 }
								if(rs1.length()>0)
								{
									
									AllignTable.put(new Integer(colNum),rs1);
									Allin_table.put(new Integer(colNum),entryAlign.toUpperCase());//Added by bhavesh
									if(entryAlign.length()>0)
									Allin_table.put(new Integer(colNum),entryAlign.toUpperCase());
									else
									Allin_table.put(new Integer(colNum),"L");
									colAlign.add(entryAlign.toUpperCase());
									
								
								}
								else
								{
									//Added by bhaveshfor alignment
									for(int j=colNum;j<=columCount;j++)
									{
										String temp=(String)AllignTable.get(new Integer(j));
										//System.out.println("temp"+ temp);
										//	System.in.read();
										if(temp!= null)
										{
											ii=Integer.parseInt(temp);
										
										if(ii>0&&temp!= null)
										{					
											
											ii--;
											rs1=""+ii;
											AllignTable.put(new Integer(j),rs1);
											
											String ss=(String)Allin_table.get(new Integer(j));
											

											colAlign.add(ss);
											rs1="";
											
											colNum=j;
											//System.out.println(j);
										}
										else 
										{
											colNum=j;
											break;
										}
										}
									}
									//End
									colAlign.add(entryAlign.toUpperCase());
									
								}
								
								int span=0;
								try
								{
									String colend="";                 /////COL1 handling, 25-11-04
									colend=xmlObj.getAttributeValue(tag, "NAMEEND");
									colend=colend.replaceAll("[a-zA-Z]+","");
									
									String colst="";
									colst=xmlObj.getAttributeValue(tag, "NAMEST");
									colst=colst.replaceAll("[a-zA-Z]+","");

									int nameend = Integer.parseInt(colend);
									int namest  = Integer.parseInt(colst);

									span        = nameend-namest;
								}
								catch(NumberFormatException numexp)
								{
								}
								if(span>0)
								{
									for(int sp=0; sp<span; sp++)
									{//Remarks
									//Added by bhavesh
										colNum++;
										
										Allin_table.put(new Integer(colNum),entryAlign.toUpperCase());
										colAlign.add(entryAlign);
										
										
									}
								}
								//System.out.println("Align-->"+entryAlign);
								//
								if(entryAlign.toUpperCase().equals("&PLUSMINUS;"))
								{
									insertValueForNewCommand("&PLUSMN;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&PLUSMN;"))
								{
									insertValueForNewCommand("&PLUSMN;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&#XB1;"))
								{
									insertValueForNewCommand("&PLUSMN;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&TIMES;"))
								{
									insertValueForNewCommand("&TIMES;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&NDASH;"))
								{
									insertValueForNewCommand("&NDASH;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&MINUS;"))
								{
									insertValueForNewCommand("&MINUS;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&PLUS;"))
								{
									insertValueForNewCommand("&PLUS;", colNum);
								}
								else if(entryAlign.equals("."))
								{
									insertValueForNewCommand(".", colNum);
									//System.out.println("Align-->"+entryAlign+" colNum "+colNum);
								}
								else if(entryAlign.equals(","))
								{
									insertValueForNewCommand(",", colNum);
								}
								else if(entryAlign.equals("/"))
								{
									insertValueForNewCommand("/", colNum);
								}
								else if(entryAlign.equals("\\\\("))
								{
									insertValueForNewCommand("\\\\(", colNum);
								}
								else if(entryAlign.equals("("))
								{
									insertValueForNewCommand("(", colNum);
								}
								else if(entryAlign.equals("["))
								{
									insertValueForNewCommand("[", colNum);
								}
							colNum++;
							
							}
						}
					}

					
					alignments.add(colAlign);									
			}
			
			}
		}
	
	 for(int i=0;i<alignments.size();i++)
	 {
	 	//System.out.println("colAlign>>"+alignments.get(i));
	 }
		int totRows= alignments.size()+1;
		int totCols= ((Vector)alignments.get(0)).size();
		//System.out.println(totRows+"  "+totCols);
		Vector colAligns= new Vector();
		for(int c=0; c<totCols; c++)
		{
			Vector eachColAlign= new Vector();
			for(int r=0; r<totRows-1; r++)
			{
				Vector rowAlignsV = (Vector)alignments.get(r);
				String colAlign= "";
				try
				{
					colAlign   = rowAlignsV.get(c).toString();
				}
				catch(ArrayIndexOutOfBoundsException arExp)
				{
					colAlign   = "L";
					//System.out.println("arExp "+arExp);
				}
				eachColAlign.add(colAlign);
			}
			colAligns.add(eachColAlign);
			
		}
		
		for(int i=0; i<colAligns.size(); i++)
		{
			Vector col= (Vector)colAligns.get(i);
			int lalign=0;
			int ralign=0;
			int dalign=0;
			int calign=0;
			int pmalign=0;
			int palign=0;
			int malign=0;
			int comalign=0;
			int balign=0;
			//Avinandan added
			int tmalign=0;
			int ndalign=0;
			int salign=0;
			
			//end mark
			/*if(col.size()>=rowSize)
			{
				System.out.println("COL<<<<<"+col);*/
				for(int k=0; k<col.size(); k++)
				{
					String align= (String)col.get(k);
					//System.out.println("2->"+align);
					
					if(align.equals("L"))
					{
						lalign++;
					}
					else if(align.equals("R"))
					{
						ralign++;
					}
					else if(align.equals("C"))
					{
						calign++;
					}
					else if(align.equals("."))
					{
						dalign++;
					}
					else if(align.equals(","))
					{
						comalign++;
					}
					else if(align.equals("("))//25/11/2009
					{
						dalign++;
					}
					else if(align.equals("&PLUSMINUS;")||align.equals("&PLUSMN;")||align.equals("&#XB1;"))
					{
						pmalign++;
					}
					//avinandan added
					else if(align.equals("&TIMES;"))
					{
						tmalign++;
					}
					else if(align.equals("&NDASH;"))
					{
						ndalign++;
					}
					//end mark
					else if(align.equals("&PLUS;"))
					{
						palign++;
					}
					else if(align.equals("&MINUS;"))
					{
						malign++;
					}
					else if(align.equals("NA"))
					{
						balign++;
					}
					else if(align.equals("/"))
					{
						salign++;
					}
				}
				String colalignstype[]={"l", "r", "c", ".", ",", "+-", "x", "n", "+", "-", "l","/"};
				int colaligns[]={lalign, ralign, calign, dalign, comalign, pmalign, tmalign, ndalign, palign, malign, balign, salign};
				GetMax maxr= new GetMax(colaligns);
				
				charColAligns.add(colalignstype[maxr.getMaxIndex()]);
				rowSize=col.size();
			
		}
		xmlObj.fin.seek(filePos);
		if(columCount!=charColAligns.size())
		{
			columCount-=charColAligns.size();
			for(int i=0;i<columCount;i++)
			{
				charColAligns.add("l");
			}

		}
		//System.out.println("1->"+charColAligns);
		//System.in.read();
		return charColAligns;
	}

	public String getTableEntryAlignment(String entryTag)
	{
		String tag= entryTag;
		String entryAlign = xmlObj.getAttributeValue(tag, "ALIGN");
		String alignChar  = xmlObj.getAttributeValue(tag, "CHAR");
		if(tag.equals("<ENTRY/>"))
			entryAlign="";//entryAlign="l";
		else if(entryAlign.equals("LEFT"))
		{
			entryAlign="l";
		}
		else if(entryAlign.equals("CHAR"))
		{
			if(alignChar.equals("."))
				entryAlign=".";
			else if(alignChar.equals("/"))
				entryAlign="/";
			else if(alignChar.equals("\\\\("))
				entryAlign="\\\\(";
			else
				entryAlign=alignChar;

			//System.out.println(entryAlign);
		}
		else if(entryAlign.equals("RIGHT"))
			entryAlign="r";
		else if(entryAlign.equals("JUSTIFY"));
		else if(entryAlign.equals("CENTER"))//to be handeled later
			entryAlign="c";
		return entryAlign;
	}

	private String getEntryValueWithAlignmaent(String alignVal, String entryVal)throws IOException
	{
		String tableCellValue= "";
		String alignValue= alignVal;
		String entryValue= entryVal;
		boolean check_align=false;
		boolean check_align_val=false;
		boolean isdot=false;//04-10-2012
		int s1=0;//[10/01/2007]//Added By Ravi
		int s2=0;//[10/01/2007]//Added By Ravi
		boolean chk=false;//[10/01/2007]//Added By Ravi
		//System.out.println("alignVal "+alignVal+" entryValue    --> "+ entryValue);
		//System.in.read();
		if(alignValue.equals(".") || alignValue.equals("/")|| alignValue.equals("+") || alignValue.equals(",") || alignValue.equals("&PLUS;")
			|| alignValue.equals("&MINUS;") || alignValue.equals("&PLUSMINUS;") ||alignValue.equals("&PLUSMN;")|| alignValue.equals("&#XB1;")|| alignValue.equals("&TIMES;") || alignValue.equals("&NDASH;")|| alignValue.equals("("))
		{
			//System.out.println("entryValue    --> "+ entryValue);
			//System.in.read();
			String lvalue="";
			String rvalue="";
			int before_trim=0;
			int after_trim=0;
			boolean trim_bol=false;
			boolean thinSpace=false;
			int ind=-1;
			int ind1=-1;//30/07/2007
			int in=0;
			
			if(entryValue.indexOf(".gif")==-1)
				{
					ind= entryValue.indexOf(alignValue);
					//System.out.println("ind "+ind +"entryValue    --> "+ entryValue);
					//System.in.read();
				}
				

			if(ind!=-1)
			{
				
				lvalue= entryValue.substring(0, entryValue.indexOf(alignValue));
				//System.out.println("1 alignVal "+alignVal+" \nentryValue    --> "+ entryValue+"\nlvalue  --> "+lvalue);
				//System.in.read();
				if(alignValue.equals("(") && (lvalue.equals("\\")||lvalue.equals("{\\bf{\\")))//21/11/2009
				{
					int a=0;
					a=entryValue.indexOf(alignValue,ind+1);
					if(a!=-1)
					{
						lvalue= entryValue.substring(0, a);
						check_align_val=true;
					}
				}
				//System.out.println("1 alignVal "+alignVal+" \nentryValue    --> "+ entryValue+"\nlvalue  --> "+lvalue);
				//System.in.read();
				//Added by Mukesh on 01-09-08
				if(alignValue.equals(",")&&lvalue.endsWith("\\"))
				{
					lvalue= entryValue.substring(0, entryValue.indexOf(alignValue,ind+1));
				}
				in=0;
				//System.out.println("entryValue    --> "+ entryValue);
				//System.out.println("lvalue    --> "+ lvalue);
				//System.in.read();
				
				in=entryValue.indexOf("\\(",0);
				if(in !=-1)
				{
					ind1=lvalue.indexOf("(",in+2);
					if(ind1!=-1)
					{
						if(lvalue.charAt(ind1-1)=='\\')
						{
							ind1=-1;
							isdot=true;
						}
					}
					if(ind1 != -1)
					{

						if(in<ind)
						{
							
							lvalue= entryValue.substring(0, entryValue.indexOf("(",ind1));
							if(lvalue.endsWith("\\,\\"))
							{
								lvalue=lvalue.substring(0,lvalue.length()-1);
							}
							alignValue="(";
							check_align_val=true;
							//System.out.println("lvalue "+lvalue +"\nentryValue    --> "+ entryValue);
							//System.in.read();
						}
						
						
						
					}
				}
				else
				{
					
					/*in =entryValue.indexOf("^{{\\rmbox{",0);
					if(in !=-1)
					{
						lvalue= entryValue;
						ind=-1;
					}
					else*/
					{
						
						ind1=lvalue.indexOf("(",0);
						//System.out.println("lvalue "+lvalue +"\nentryValue    --> "+ entryValue);
						//System.in.read();
						if(ind1 != -1)
						{
							if(ind1<ind)
							{

								
								if(lvalue.lastIndexOf("}} (")==-1&&!entryValue.endsWith(")}}")){
								//System.out.println("---------->lvalue "+lvalue);
								lvalue= entryValue.substring(0, entryValue.indexOf("("));
								before_trim=lvalue.length();
								after_trim=lvalue.trim().length();
								if(before_trim>after_trim)
								{
									lvalue=lvalue.trim();
									trim_bol=true;
								}
								//System.out.println("lvalue "+lvalue);
								//System.in.read();
								alignValue="(";
								check_align=true;
								}//29/01/2009
								else
								{
									lvalue= entryValue.substring(0, entryValue.indexOf(alignValue));
									//System.out.println("lvalue "+lvalue +"\nentryValue    --> "+ entryValue);
									//System.in.read();
								}
							}
							
							
						}
					}
				}

			//System.out.println("lvalue "+lvalue +"\nentryValue    --> "+ entryValue);
			//	System.in.read();
				
			}
			else
			{

				//[30/07/2007]
				if(entryValue.indexOf(".gif")==-1)
				{
					in=0;
					in=entryValue.indexOf("\\(",0);
					//********************17/08/2008
					int mi=entryValue.indexOf("\\)",in);
					String mText="";
					if(mi !=-1)
					{
						mText=entryValue.substring(in+2,mi);
						//System.out.println(" entryValue : "+entryValue+"\n\n mText :"+mText);
						if(mText.indexOf("\\(",0)==-1)
						{
								if(mText.indexOf("(",0)!=-1)
								{
									mi=mText.indexOf("(",0);
									in=in+2+mi;
								}
								else
								{
									 in=-1;
								}
						}
					}
					//\\hbox{{\\bf{13}}\\hyperlink{DNAREP1055TBL1FN1}{\\(jkjkk(jk)k^{{\\rmbox{*}}}\\)}{}}\\

					//********************17/08/2008
					
				
				if(in !=-1)
				{
					
					//ind=entryValue.indexOf("\\(",in+2);

					ind=entryValue.indexOf("\\(",0);//11/12/2007
					

					//System.out.println("ind "+ind+" alignValue "+alignValue +"entryValue    --> "+ entryValue);
					//System.in.read();
					if(ind!=-1)
					{
						alignValue="(";
						//lvalue= entryValue.substring(0, ind);
						lvalue= entryValue.substring(0, ind);
						//thinSpace
						//System.out.println("Before lvalue==> "+lvalue);
						if(lvalue.endsWith("\\,"))
						{
							lvalue= entryValue.substring(0, ind-2);
							//thinSpace=true;
						}

						//System.out.println("After lvalue==> "+lvalue);
						//System.in.read();
						check_align_val=true;
					}
					else
					{
						
						lvalue= entryValue;
						alignValue="";

						//System.out.println("fffffffffffffflvalue==> "+lvalue);
						//System.in.read();
					}
				}
				else
				{
					//System.out.println("alignValue "+alignValue +"entryValue    --> "+ entryValue);
					//System.in.read();
					//[30/07/2007]
					if(entryValue.indexOf(alignValue,0)!=-1)
					{
						ind=entryValue.indexOf("(",0);
						if(ind!=-1)
						{
							//alignValue="(";
							//lvalue= entryValue.substring(0, entryValue.indexOf(alignValue));
							//System.out.println("qqqqqqqqlvalue==> "+lvalue);
							//System.in.read();
							lvalue= entryValue.substring(0, entryValue.indexOf("("));
							before_trim=lvalue.length();
							after_trim=lvalue.trim().length();
							if(before_trim>after_trim)
							{
								lvalue=lvalue.trim();
								trim_bol=true;
							}
							//System.out.println("lvalue "+lvalue);
							//System.in.read();
							alignValue="(";
							check_align=true;
						}
						/*else//26/02/2008
						{

							lvalue= entryValue;
							//System.out.println("ind "+ind +" eeeeeeee lvalue==> "+lvalue);
							//System.in.read();
							alignValue="";
						}*/
					}
					else
					{

						lvalue= entryValue;
						//System.out.println("ind "+ind +" eeeeeeee lvalue==> "+lvalue);
						//System.in.read();
						alignValue="";
					}
				}
			  }//[12/09/2007]
			  else
				{
				  lvalue= entryValue;
				  alignValue="";
				}
			}

			if(ind!=-1)
			{
				if(alignValue.equals("("))
				{
					//[30/07/2007]
					if(check_align_val==true)
					{
						//rvalue= entryValue.substring(in);
						//temp_alignValue
						rvalue= entryValue.substring(lvalue.length());
						//System.out.println("rvalue==> "+rvalue+"\nlvalue "+lvalue);
						//System.in.read();
						check_align_val=false;
					}
					else
					{
						rvalue= entryValue.substring(entryValue.indexOf(alignValue));
						//System.out.println("rvalue==> "+rvalue);
						//System.in.read();
					}
				}
				else if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+11);
				}
				else if(alignValue.equals("&#XB1;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+6);
				}
				else if(alignValue.equals("&PLUS;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+6);
				}
				else if(alignValue.equals("&MINUS;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+7);
				}
				else if(alignValue.equals("&TIMES;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+7);
				}
				else if(alignValue.equals("&NDASH;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+7);
				}
				else
				{
					//System.out.println("lvalue "+lvalue+"\nrvalue  ==> "+rvalue);
					if(alignValue.equals(","))
					{
						rvalue= entryValue.substring(lvalue.length()+1);//By mukesh on 01-09-08 
					}
					else
					{
						rvalue= entryValue.substring(entryValue.indexOf(alignValue)+1);
					}
					//System.out.println("lvalue "+lvalue+"\nrvalue  ==> "+rvalue);
					//System.in.read();
					//[10/01/2007]//Added By Ravi
					StringBuffer rval= new StringBuffer(rvalue);
					if(lvalue.indexOf("{\\it{\\bi{{\\underline{") != -1)
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\it{\\bi{{\\underline{");
						if(s1 != -1)
						{
							chk=true;
						}
					}
					else if(lvalue.indexOf("{\\underline{{\\bf{\\bi{") != -1)
					{
						s1=0;
				   		s2=0;
				   		s1=lvalue.lastIndexOf("{\\underline{{\\bf{\\bi{");
						if(s1 != -1)
						{
							chk=true;
						}
					}
					else if(lvalue.indexOf("{\\it{\\bi{") != -1)
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\it{\\bi{");
						if(s1 != -1)
						{
							chk=true;
						}
					}
					else if(lvalue.indexOf("{\\bf{{\\underline{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\bf{{\\underline{");
						if(s1 != -1)
						{
							chk=true;
						}
					}
					else if(lvalue.indexOf("{\\underline{{\\it{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\underline{{\\it{");
						if(s1 != -1)
						{
							chk=true;
						}
					}
					else if(lvalue.indexOf("{\\it{{\\underline{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\it{{\\underline{");
						
						if(s1 != -1)
						{
							chk=true;
							
						}
					}
					else if(lvalue.indexOf("{\\bf{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=rval.lastIndexOf("{\\bf{");
						if(s1 != -1)
						{
							chk=true;
						
						}
					}
					else if(lvalue.indexOf("{\\underline{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=lvalue.lastIndexOf("{\\underline{");
						if(s1 != -1)
						{
							chk=true;
						
						}
					}
					else if(lvalue.indexOf("{\\it{") != -1)	
					{
						s1=0;
				   		s2=0;
				   		s1=lvalue.lastIndexOf("{\\it{");
						if(s1 != -1)
						{
							chk=true;
						
						}
					}
					
				}
			}

			if(lvalue.length()>0)
			{
				//System.out.println("rvalue "+rvalue +" \nlvalue==> "+lvalue);
				//System.in.read();
				if(rvalue.length()>0)
				{
						//System.out.println("2 lvalue "+lvalue+"  alignVal "+alignVal+" entryValue    --> "+ entryValue);
						//System.in.read();
					//lvalue="\\hbox{"+lvalue+"}";//old
					//[10/01/2007]//Added By Ravi
					
					if(lvalue.indexOf("{\\it{\\bi{{\\underline{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\it{\\bi{{\\underline{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								//lvalue="\\hbox{"+lvalue+"}";
								lvalue="\\hbox{"+lvalue+"}}}";
								chk=false;
							}
							else
							{
								lvalue="\\hbox{"+lvalue+"}}}";
							}
						}
					}
					else if(lvalue.indexOf("{\\underline{{\\bf{\\bi{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\underline{{\\bf{\\bi{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								chk=false;
							}
							else
							{
								lvalue="\\hbox{"+lvalue+"}";
							}
						}
					}
					else if(lvalue.indexOf("{\\it{\\bi{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\it{\\bi{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								chk=false;
							}
							else
							{
								
								lvalue="\\hbox{"+lvalue+"}}}";
								
							}
						}
					}
					else if(lvalue.indexOf("{\\bf{{\\underline{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\bf{{\\underline{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								
								chk=false;
							}
							else
							{
								
								s1=0;
								s1=rvalue.lastIndexOf("{\\rmbox{");
								if(s1 != -1)
								{
									lvalue="\\hbox{"+lvalue+"}}}}}";
								}
								else
								{
									lvalue="\\hbox{"+lvalue+"}}}";
								}

								
								//System.out.println("lvalue==> "+lvalue);
								//System.in.read();
							}
						}
					}
					else if(lvalue.indexOf("{\\it{{\\underline{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\it{{\\underline{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								//lvalue="\\hbox{"+lvalue+"}";
								lvalue="\\hbox{"+lvalue+"}}}";
							
								chk=false;
							}
							else
							{
								lvalue="\\hbox{"+lvalue+"}}}";
							}
						}
					}
					else if(lvalue.indexOf("{\\underline{{\\it{") != -1)	
					{
						s1=0;
					   	s2=0;
						s1=lvalue.lastIndexOf("{\\underline{{\\it{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								chk=false;
							}
							else
							{
								lvalue="\\hbox{"+lvalue+"}}}";
							}
						}
					}
					else if(lvalue.indexOf("{\\bf{") != -1)
					{
					
					   s1=0;
					   s2=0;
						s1=lvalue.lastIndexOf("{\\bf{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								chk=false;
							}
							else
							{
								lvalue="\\hbox{"+lvalue+"}";
							}
						}
						
					}
					else if(lvalue.lastIndexOf("{\\underline{") != -1)
					{
						s1=0;
						s1=lvalue.lastIndexOf("{\\underline{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								
								
								lvalue="\\hbox{"+lvalue+"}";
								
								
								chk=false;
							}
							else
							{
								//lvalue="\\hbox{"+lvalue+"}}}";
								lvalue="\\hbox{"+lvalue+"}";
							
							}
						}
					}
					else if(lvalue.lastIndexOf("{\\it{") != -1)
					{
						//System.out.println("lvalue==> "+lvalue+"rvalue"+rvalue);
						s1=0;
						s1=lvalue.lastIndexOf("{\\it{");
						if(s1 != -1)
						{
							if(chk==true)
							{
								lvalue="\\hbox{"+lvalue+"}";
								chk=false;
							}
							else
							{
								//lvalue="\\hbox{"+lvalue+"}}}";
								lvalue="\\hbox{"+lvalue+"}";
							//	System.out.println("14 lvalue ==> "+lvalue);
							}
							//System.out.println("14 lvalue ==> "+lvalue);
							//System.in.read();
						}
					}
					else
					{
						//System.out.println("rvalue "+rvalue +" lvalue==> "+lvalue);
						//	System.in.read();
						//^{{\rmbox{
						//ravi 30/07/2007
						if(lvalue.indexOf("^{{\\rmbox{",0)==-1)
						{
							lvalue="\\hbox{"+lvalue+"}";
						}
						else if(rvalue.endsWith(")"))
						{
							lvalue="\\hbox{"+lvalue+"}";
						}
						else
						{
							
							//System.out.println("3 rvalue "+rvalue +" lvalue==> "+lvalue);
							//System.in.read();
							String chk1="0 1 2 3 4 5 6 7 8 9";
							String val=rvalue.charAt(rvalue.length()-1)+"";
							boolean bl=false;
							boolean b2=false;
							//System.out.println("val : "+val);
							if(chk1.indexOf(val,0)!=-1)
							{
								char c=lvalue.charAt(lvalue.length()-1);
								//System.out.println("---------------------->>"+c);
								if ((c >= '0') && (c <= '9')) {
									b2=true;
								}
								else{
								rvalue=rvalue+"}\\)";
								bl=true;
								}
								
							}
							else
							{	if(lvalue.indexOf("\\hyperlink{",0)!=-1)
								{
								}
								else if(isdot)
								{
									rvalue=rvalue+"}";
									isdot=false;
								}
								else if(rvalue.endsWith("."))
								{
									rvalue=rvalue+"}\\)";
								}
								else
								rvalue=rvalue.substring(0,rvalue.length())+"}\\)";
							}
							//System.out.println("12 rvalue "+rvalue +" lvalue==> "+lvalue);
							//System.in.read();
							//rvalue=rvalue.substring(0,rvalue.length()-2)+"}\\)";
							if(b2==true){
							
								lvalue="\\hbox{"+lvalue+"}\\hbox to 0pt{"+alignValue+"\\hbox{"+rvalue+"}}";
								}
							else if(bl==true)
								lvalue="\\hbox{"+lvalue+"\\"+alignValue+"\\hbox{"+rvalue+"}";
							else if(lvalue.indexOf("\\hyperlink{",0)!=-1)
							{
								lvalue="\\hbox{"+lvalue+"}\\hbox to 0pt{"+alignValue+"\\hbox{"+rvalue+"}}";
								//\\hbox{13\\hyperlink{SON594TBL4FN1}{\\(^{{\\rmbox{*}}}\\)}{} [1}\\hbox to 0pt{.\\hbox{8}]}
							}
							else
								lvalue="\\hbox{"+lvalue+alignValue+"\\hbox{"+rvalue+"}";
							//System.out.println("rvalue "+rvalue +" lvalue==> "+lvalue);

							//System.out.println("4 rvalue "+rvalue +" lvalue==> "+lvalue);
							//System.in.read();

							rvalue="";
							alignValue="";
							
							//System.in.read();
						}
					}
				}
				else
				{
						lvalue="\\hbox{"+lvalue+"}";//old

						//System.out.println("rvalue "+rvalue +" lvalue==> "+lvalue);
						//System.in.read();
				}
				
			}
			else
			{
			//	lvalue="\\hbox{"+lvalue+"}";//old
			
		
			}
			if(rvalue.length()>0 && !alignValue.equals("/"))
				{
					rvalue="\\hbox{"+rvalue+"}";
					
				}
			else if(rvalue.length()>0 && alignValue.equals("/"))
				rvalue=rvalue;
			
			if(alignValue.length()>0)
			{
				
			//System.out.println("lvalue===> "+lvalue+"\nrvalue==> "+rvalue);
			//System.in.read();
				if(lvalue.endsWith("}}"))
				{
					//tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";//old
					//[10/01/2007]//Added By Ravi
					if(lvalue.indexOf("{\\bf{\\bi{{\\underline{") != -1)
					{
						tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf{\\bi{{\\underline{"+alignValue+ rvalue+"}}}}";
					}
					else if(lvalue.indexOf("{\\underline{{\\bf{\\bi{") != -1)
					{
						tableCellValue= lvalue+ "}\\hbox to 0pt{{\\underline{{\\bf{\\bi{"+alignValue+ rvalue+"}}}";
					}
					else if(lvalue.indexOf("{\\it{\\bi{{\\underline{") != -1)
					{
						tableCellValue= lvalue+ "}}}\\hbox to 0pt{{\\it{\\bi{{\\underline{"+alignValue+ rvalue+"}";
					}
					else if(lvalue.indexOf("{\\it{\\bi{") != -1)
					{
						//System.out.println("rvalue===> "+rvalue);
						//System.in.read();
						int i =rvalue.indexOf("}}}",0);
								if(i !=-1)
								{
									String r= rvalue.substring(0,i+3);
									rvalue=r+"}"+rvalue.substring(i+3,rvalue.length());
									tableCellValue= lvalue+ "}\\hbox to 0pt{{\\it{\\bi{"+alignValue+ rvalue;
								}
								else
								{
									tableCellValue= lvalue+ "}\\hbox to 0pt{{\\it{\\bi{"+alignValue+ rvalue+"}";
								}
						
					}
					else if(lvalue.indexOf("{\\bf{\\bi{") != -1)
					{
						tableCellValue= lvalue+ "}\\hbox to 0pt{{\\bf{\\bi{"+alignValue+ rvalue+"}";

					}
					else if(lvalue.lastIndexOf("{\\bf{{\\underline{") != -1)
					{
						s1=0;
						s1=rvalue.lastIndexOf("\\hyperlink{");
						if(s1 != -1)
						{
							rvalue=rvalue.substring(0,rvalue.length()-2);
							tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bf{\\underline{"+alignValue+ rvalue+"}}";
						}
						else
						{
							s1=0;
							s1=rvalue.lastIndexOf("{\\rmbox{");
							if(s1 != -1)
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{\\bf{\\underline{{"+alignValue+ rvalue;
							}
						else
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{\\bf{\\underline{"+alignValue+ rvalue+"}";
							
							}
							//System.out.println("rvalue==> "+rvalue);
							//System.in.read();
						}	
					}
					else if(lvalue.lastIndexOf("{\\it{{\\underline{") != -1)
					{
						tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{{\\underline{"+alignValue+ rvalue+"}";
					}
					else if(lvalue.lastIndexOf("{\\underline{{\\it{") != -1)
					{
						tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\underline{{\\it{"+alignValue+ rvalue+"}";
					}
					else if(lvalue.lastIndexOf("{\\bf{") != -1)
					{
						s1=0;
						s2=0;
						s1=rvalue.lastIndexOf("\\hbox{");
						if(s1 != -1)
						{
							
							if((rvalue.indexOf("{\\bf{") != -1) ||(rvalue.indexOf("{\\it{")!= -1) ||(rvalue.indexOf("{\\bi{")!= -1) ||(rvalue.indexOf("{\\underline{")!= -1)) 
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"}}";
							}
							else
							{
								if(alignValue.equals("("))
								{
									tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"}}";
									//System.out.println("alignValue "+alignValue+" tableCellValue --> "+ tableCellValue);
								//System.in.read();
								}
								else
								{
									tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"";
								}
								

							}
						}
						else
						{
							tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"}}";

						}
						
						
					
					}
					else if(lvalue.lastIndexOf("{\\underline{") != -1)
					{

						if(lvalue.lastIndexOf("}}") != -1)
						{
							tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
							//System.out.println("alignValue 11111111  "+alignValue+"  rvalue 1111111 "+rvalue);
						}
						else
						{
							tableCellValue= lvalue+ "\\hbox to 0pt{{\\underline{"+alignValue+ rvalue+"}";
						}

					}
					else
					{
						tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
						//System.out.println("alignValue 222222  "+alignValue+"  rvalue 2222  "+rvalue);
						
					}
						
				}
			/*	else if(lvalue.indexOf("\\bf") !=-1)
				{
					if(alignValue.equals("."))
					{
						tableCellValue= lvalue+ "\\hbox to 0pt{{"+alignValue+ rvalue+"}}";
					}
					else
					{
						tableCellValue= lvalue+ "\\hbox to 0pt{\\bf{"+alignValue+ rvalue+"}}";//changed above on 05.10.2005
					}
				}
				else if(lvalue.indexOf("\\it") !=-1)
				{
					//System.out.println(tableCellValue);

					tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ rvalue+"}";
					//tableCellValue= lvalue+ "\\hbox to 0pt{{\\it{"+alignValue+ rvalue+"}}}";//14.06.2006 abhay

					//System.out.println(tableCellValue);
				}
				else if(lvalue.indexOf("\\bi") !=-1)
					tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bi{"+alignValue+ rvalue+"}";
				*/
				else
				{
					//tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
					 if(lvalue.indexOf("{\\underline{{\\bf{\\bi{") != -1)
					{
						s1=0;
						s1=rvalue.lastIndexOf("\\hbox{");
						if(s1 != -1)
						{
							//tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"";
							if(lvalue.lastIndexOf("}}") != -1)
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
								//System.out.println("alignValue 33333333  "+alignValue+"  rvalue 333333333 "+rvalue);
							}
							else
							{
								tableCellValue= lvalue+ "}}}}}\\hbox to 0pt{{\\underline{{\\bf{\\bi{"+alignValue+ rvalue+"}";
							}
							//System.out.println(" if lvalue    --> "+ lvalue);
							//System.in.read();
						}
						else
						{
							tableCellValue= lvalue+ "}\\hbox to 0pt{{\\underline{{\\bf{\\bi{"+alignValue+ rvalue+"}}}";
						}
					}
					//[06/04/2007]
					else if(lvalue.indexOf("{\\it{\\bi{") != -1)
					{
						//System.out.println("lvalue --> "+ lvalue+"\nrvalue==>"+rvalue);
						//System.in.read();
						s1=0;
						s1=rvalue.lastIndexOf("\\hbox{");
						if(s1 != -1)
						{
							if(lvalue.lastIndexOf("}}") != -1)
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
								//System.out.println("alignValue 4444444  "+alignValue+"  rvalue 44444444444 "+rvalue);
							}
							else
							{
								int i =rvalue.indexOf("}}}",0);
								if(i !=-1)
								{
									String r= rvalue.substring(0,i+3);
									rvalue=r+"}"+rvalue.substring(i+3,rvalue.length());
								}
								
								tableCellValue= lvalue+ "}}}\\hbox to 0pt{{\\it{\\bi{"+alignValue+ rvalue+"";
							}
							
						}
						else
						{
							tableCellValue= lvalue+ "}\\hbox to 0pt{{\\underline{{\\it{\\bi{"+alignValue+ rvalue+"}}}";
						}
					}
					//end
					else if(lvalue.lastIndexOf("{\\bf{") != -1)
					{
						s1=0;
						s1=rvalue.lastIndexOf("\\hbox{");
						if(s1 != -1)
						{
							//tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"";
							//System.out.println(lvalue+" alignValue  55555555 "+alignValue+"  rvalue 6666666666 "+rvalue);
							if(lvalue.lastIndexOf("}}") != -1)
							{
								tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
								//System.out.println("alignValue  55555555 "+alignValue+"  rvalue 6666666666 "+rvalue);
							}
							else
							{
								if(alignValue.equals("("))
								{
									tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bf "+ rvalue+"";
								}else
									tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"";
							}
							
						}
						else
						{
							
							tableCellValue= lvalue+ "\\hbox to 0pt{{\\bf "+alignValue+ rvalue+"}}";
							////System.out.println("1 tableCellValue    --> "+ tableCellValue);
							//System.in.read();
						}
					
					}
					else
					{
						if(lvalue.lastIndexOf("{\\underline{") != -1)
						{
						//	System.out.println(" lvalue    --> "+ lvalue);
						//	System.in.read();
							if(lvalue.lastIndexOf("}}") != -1)
							{
									tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
									//System.out.println("alignValue 77777  "+alignValue+"  rvalue 7777 "+rvalue);
								//	System.out.println("if  tableCellValue    --> "+ tableCellValue);
							}
							else
							{
								tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\underline{"+alignValue+ rvalue+"}";
							//	System.out.println("else tableCellValue    --> "+ tableCellValue);
							}
						}
						else if(lvalue.lastIndexOf("{\\it{") != -1)
						{
							boolean b=false;//Added on [06/03/2007]
							boolean ch_mns=false; //added on 11/12/2007
							if(lvalue.lastIndexOf("}}") != -1)
							{
								
								if(rvalue.indexOf("\\rmbox",0)==-1)
								{
									tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
									//System.out.println("alignValue  888 "+alignValue+"  rvalue 888 "+rvalue);
									b=true;//Added on [06/03/2007]
								}
								else
								{
									//01/10/2007
									int spos=0;
									int epos=0;
									String ctrl="";
									spos=rvalue.indexOf("\\rmbox",spos);
									while(spos !=-1)
									{
										spos=rvalue.indexOf("\\rmbox",spos+5);
										if(spos !=-1)
										{
											ctrl+="}";
										}
									}
									tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}"+ctrl;
									//System.out.println("alignValue  999 "+alignValue+"  rvalue 999 "+rvalue);
									ch_mns=true;
									
								}
									//System.out.println(" lvalue    --> "+ lvalue+"\n rvalue==>"+rvalue);
									//System.in.read();
							}
							 if(rvalue.indexOf("}}") != -1)
							{
								//System.out.println("1.  lvalue    --> "+ lvalue+"\n rvalue==>"+rvalue);
								//System.in.read();
								int to =0;
								//{}}
								to=rvalue.lastIndexOf("{}}");
								if(to != -1)
								{
									s1=0;
									s1=rvalue.lastIndexOf("\\hyperlink{");
									if(s1 != -1)
									{
										//rvalue=rvalue.substring(0,rvalue.length());
										String t1=rvalue.substring(0,s1);
										String t2=rvalue.substring(s1,rvalue.length());
										//tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ rvalue+"}";
										tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ t1+"}"+t2;
									}
									else
									{
										
										tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ rvalue+"}";	
									}
								
								}
								else
								{
									
										int s=0;
										s=rvalue.indexOf("}}",0);
										if(s != -1)
										{
										String Rpart1=rvalue.substring(0,s+1);
										String Rpart2=rvalue.substring(s+2);
										//System.out.println("rvalue==>"+rvalue+"\nRpart1==>"+Rpart1+"\nRpart2==>"+Rpart2);
										//System.in.read();
										if(Rpart1.indexOf("{\\it{",0)!=-1)
											{
												if(alignValue.equals("("))
												tableCellValue= lvalue+ "\\hbox to 0pt{"+ Rpart1+"}}"+Rpart2;
												else
												tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ Rpart1+"}}"+Rpart2;
												//System.out.println("alignValue 10  "+alignValue+"  Rpart1  "+rvalue+"  Rpart2  "+Rpart2);
											}
											else
											{
												//System.out.println("2.  lvalue    --> "+ lvalue+"\n rvalue==>"+rvalue);
												//System.in.read();
													if(alignValue.equals("("))
														tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+ Rpart1+"}}"+Rpart2;
													else
													tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ Rpart1+"}}"+Rpart2;
												
											}
										
										
										}
								}
								
							}
							else 
							{
								if(b==false)//Added on [06/03/2007]
								tableCellValue= lvalue+ "}}\\hbox to 0pt{{\\it{"+alignValue+ rvalue+"}";
							
							}
						}
						else
						{
							//[30/07/2007]
							if(alignValue.equalsIgnoreCase("("))
							{
								if(trim_bol==true)
								{
									tableCellValue= lvalue+ "\\hbox to 0pt{ "+ rvalue+"}";
									//System.out.println("lvalue 12  "+lvalue+"  rvalue 12 "+rvalue);
									trim_bol=false;
									
								}
								else
								{
									if(lvalue.length()>0)
									{
									tableCellValue= lvalue+ "\\hbox to 0pt{"+ rvalue+"}";
									//System.out.println("lvalue 13  "+lvalue+"  rvalue 13 "+rvalue);
									}
									else
										tableCellValue= rvalue;
									//System.out.println("tableCellValue=lvalue==========> "+tableCellValue);
									//System.in.read();
								}
							}
							else
							{
								
								tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
								//added by mukesh
								//System.out.println("lvalue  14 "+lvalue+"  rvalue  "+rvalue+" alignValue  "+alignValue);
								//System.out.println("tableCellValue=lvalue==========> "+tableCellValue);
								//System.in.read();
							}
							//System.out.println("tableCellValue=lvalue==========> "+tableCellValue);
							//System.in.read();
						}
					}
					
				}
			
				
			}
			else
			{
				
				tableCellValue= lvalue+ alignValue+ rvalue;
				//System.out.println("alignValue==> "+alignValue+" lvalue===> "+lvalue+"\nrvalue==> "+rvalue);
			
				//System.out.println(" tableCellValue    --> "+ tableCellValue);
				//System.in.read();
			}
		}
		
		else
		{
			tableCellValue= entryValue;//old
		}
		//System.out.println("tableCellValue ==> "+tableCellValue);
		//System.in.read();
		return tableCellValue;
	}


private String conCline(String cline, boolean theadFlag)
{
	String Cline = cline;

//	System.out.print(cline+"::::");
	boolean index1=true,index2=false;
	int int1=0,int2=0;
	String temp = new String();
	temp = cline.substring(cline.indexOf("-")+1,cline.indexOf("}"));
	cline= cline.substring(cline.indexOf("}")+1);
	int1 = new Integer(temp).intValue();
	while(cline.indexOf("-")>0)
	{
		temp = cline.substring(cline.indexOf("-")+1,cline.indexOf("}"));
		int2 = new Integer(temp).intValue();
		if(int1 != (int2-1))
		{
			index1=false;
			break;

		}
		cline= cline.substring(cline.indexOf("}")+1);
		index2=true;
		int1=int2;

	}
	temp="";
	if(index1 == true && index2 == true)
	{
		
		/**
			*Added By Ravi
			* Date : [12/04/2007]
			* Change Request By : Vivek
			* Change Request handle extra rowspan
			*/
		if(theadFlag==true && Cline.startsWith("\\cline{1-"))//odd

		//if(theadFlag==true && Cline.startsWith("\\cline{"))
		{
			temp="";
			Cline="\\colrule";
			
		}
		else
		{
			temp ="\\cline{"+ Cline.substring(Cline.indexOf("-")+1,Cline.indexOf("}"));
			temp =temp + "-"+ Cline.substring(Cline.lastIndexOf("-")+1,Cline.lastIndexOf("}"))+"}";
		}
	}
	Cline = Cline +temp;
	/////////////////////////////////////////////////////////////////////////////////////////
	String ttag="";
	try{
	long l1=xmlObj.fin.getFilePointer();	
	char ch= (char)xmlObj.fin.read();
	if (ch=='<')
	{
		ttag= xmlObj.getTag().toUpperCase();
		//System.out.println("Tag-->"+ttag);

	}
	xmlObj.fin.seek(l1);
	}
	catch(IOException e)
	{
		e.printStackTrace();
	}

	if(Cline.startsWith("\\cline{1-") && theadFlag==true && ttag.equals("</THEAD>"))
	{
	String str=Cline;
	str=str.replaceAll("\\\\cline","");
	str=str.replaceAll("\\}\\{","<-->");
	str=str.replaceAll("\\}","");
	str=str.replaceAll("\\{","");
	//System.out.println(str);
	if(str.indexOf("<-->")!=-1)
	{
	String [] str1=str.split("<-->");
	Hashtable tt=new Hashtable();
	int a=0,b=0;
	boolean test=true;
	for (int i=0;i<str1.length ;i++ )
	{
		String [] s1=str1[i].split("-");
		if((i+1)!=str1.length)
		{
			String [] s2=str1[i+1].split("-");
			a = new Integer(s1[1]).intValue();
			b = new Integer(s2[0]).intValue();
			//System.out.println(a+"<<>>"+b);
		}
		else
			test=true;
		
		if(a!=(b-1))
		{
			test=false;
			break;
		}
		
		
	}
	//System.out.println(test);

	if(test)
	{					
			Cline="\\colrule";
		
	}
	}
	}

	/////////////////////////////////////////////////////////////////////////////////////////
	

	return Cline;
}
//Avinandan added function getEntryContent()
public String getEntryContent()throws IOException
{
	String tag="";
	StringBuffer tagContent=new StringBuffer();
	while(!tag.equals("</ENTRY>"))
	{
		char ch= (char)xmlObj.fin.read();
		if (ch=='<')
		{
			tag= xmlObj.getTag().toUpperCase();
			if(tag.equals("</ENTRY>"))
			{
				break;
			}
			else if (tag.indexOf("/>")!=-1)
			{
				//empty
			}
			else if(!tag.startsWith("</"))
			{
				String eTag=xmlObj.getEndtag(tag);
				while(!tag.equals(eTag))
				{
					char ch1= (char)xmlObj.fin.read();
					if (ch1=='<')
					{
						tag= xmlObj.getTag().toUpperCase();
					}
				}
			}//end of else
		}
		else if(ch==' ')
		{
			//empty body
		}
		else
		{
			tagContent.append(ch);
		}

	}//end of while
	//System.out.println("tagContent : "+tagContent);
	//System.in.read();
	return tagContent.toString();
}//end of getEntryContent()
//avinandan added getTempEntity()
//Avinandan Added insertValueForNewCommand()
void insertValueForNewCommand(String alignValue, int colNum)throws IOException
{	
	//System.out.println("alignValue "+alignValue);
	long tempPos=xmlObj.fin.getFilePointer();
	String ColValue=getEntryContent().toUpperCase();
	String lText="";
	String rText="";
	int value=0;
	int lValue=0;
	Pattern p;
	Matcher m;
	if(newComChar.containsKey(new Integer(colNum)))
	{
		newComChar.remove(new Integer(colNum));
		if(alignValue.equals("&PLUSMN;"))
		{
			newComChar.put(new Integer(colNum), "+-");
		}
		else if(alignValue.equals("&#XB1;"))
		{
			newComChar.put(new Integer(colNum), "+-");
		}
		else if(alignValue.equals("&TIMES;"))
		{
			newComChar.put(new Integer(colNum), "x");
		}
		else if(alignValue.equals("&NDASH;"))
		{
			newComChar.put(new Integer(colNum), "n");
		}
		else if(alignValue.equals("&PLUS;"))
		{
			newComChar.put(new Integer(colNum), "+");
		}
		else if(alignValue.equals("&MINUS;"))
		{
			newComChar.put(new Integer(colNum), "-");
		}
		else if(alignValue.equals("."))
		{
			newComChar.put(new Integer(colNum), ".");
		}
		else if(alignValue.equals(","))
		{
			newComChar.put(new Integer(colNum), ",");
		}
		else if(alignValue.equals("/"))
		{
			newComChar.put(new Integer(colNum), "/");
		}
		else if(alignValue.equals("("))
		{
			newComChar.put(new Integer(colNum), "(");
		}
		else if(alignValue.equals("["))
		{
			newComChar.put(new Integer(colNum), "[");
		}
	}
	else
	{
		if(alignValue.equals("&PLUSMN;"))
		{
			newComChar.put(new Integer(colNum), "+-");
		}
		else if(alignValue.equals("&#XB1;"))
		{
			newComChar.put(new Integer(colNum), "+-");
		}
		else if(alignValue.equals("&TIMES;"))
		{
			newComChar.put(new Integer(colNum), "x");
		}
		else if(alignValue.equals("&NDASH;"))
		{
			newComChar.put(new Integer(colNum), "n");
		}
		else if(alignValue.equals("&PLUS;"))
		{
			newComChar.put(new Integer(colNum), "+");
		}
		else if(alignValue.equals("&MINUS;"))
		{
			newComChar.put(new Integer(colNum), "-");
		}
		else if(alignValue.equals("."))
		{
			newComChar.put(new Integer(colNum), ".");
		}
		else if(alignValue.equals(","))
		{
			newComChar.put(new Integer(colNum), ",");
		}
		else if(alignValue.equals("/"))
		{
			newComChar.put(new Integer(colNum), "/");
		}
		else if(alignValue.equals("("))
		{
			newComChar.put(new Integer(colNum), "(");
		}
		else if(alignValue.equals("["))
		{
			newComChar.put(new Integer(colNum), "[");
		}
	}
	if(ColValue.indexOf(",",0)!=-1)
	{
		//alignValue=",";
	}
	//System.out.println("ColValue"+ColValue+" alignValue "+alignValue);
	if(ColValue.indexOf(alignValue)!=-1)
	{
		lText=ColValue.substring(0, ColValue.indexOf(alignValue));
		p=Pattern.compile("&[a-zA-Z]*;");
		m=p.matcher(lText);
		lText=m.replaceAll("0");
		rText=ColValue.substring(ColValue.indexOf(alignValue)+alignValue.length());
		p=Pattern.compile("&[a-zA-Z]*;");
		m=p.matcher(rText);
		rText=m.replaceAll("0");
//		System.out.print("L : "+lText+" R : "+rText+" :: ");
		if(newComL.containsKey(new Integer(colNum)))
		{
			lValue=Integer.parseInt((String)newComL.get(new Integer(colNum)));
			value=lText.length();
			if(value > lValue)
			{
				newComL.remove(new Integer(colNum));
				newComL.put(new Integer(colNum), new Integer(value).toString());
			}
		}
		else
		{
			value=lText.length();
			newComL.put(new Integer(colNum), new Integer(value).toString());
		}
		if(newComR.containsKey(new Integer(colNum)))
		{
			lValue=Integer.parseInt((String)newComR.get(new Integer(colNum)));
			value=rText.length();
			if(value > lValue)
			{
				newComR.remove(new Integer(colNum));
				newComR.put(new Integer(colNum), new Integer(value).toString());
			}
		}
		else
		{
			value=rText.length();
			newComR.put(new Integer(colNum), new Integer(value).toString());
		}
	}//end of if
	/**
	* Modify Date : [08/08/2007]
	* Modify By : Ravi
	* Cahnge Point : If there is no align value in contain align calculation must be Done
	* Change Request : TPMS Table Alignment.
	*/
	else
	{

		lText=ColValue;
		p=Pattern.compile("&[a-zA-Z]*;");
		m=p.matcher(lText);
		lText=m.replaceAll("0");
		value=lText.length();
		newComL.put(new Integer(colNum), new Integer(value).toString());
		value=0;
		newComR.put(new Integer(colNum), new Integer(value).toString());
		
	}
	//end
	xmlObj.fin.seek(tempPos);
	//System.out.println("HHH : "+newComL.get(new Integer(colNum)) + " <|> "+newComR.get(new Integer(colNum)));
}//end of insertValueForNewCommand()
//Avinandan added insertValueNewCommand()
private String insertValueNewCommand(String countCols)
{
	StringBuffer newValue=new StringBuffer();
	String temp="";
	char ch='A';
	int maxCol=Integer.parseInt(countCols);
	//System.out.println("newComL "+newComL);
	for(int i=0; i<=maxCol;i++)
	{
		if(newComL.containsKey(new Integer(i)))
		{
			tabularchar.put(new Integer(i),""+ch);
			newValue.append("\r\n\\newcolumntype{"+ch+"}{D{");
			ch++;
			if(ch=='D')
			{
				ch++;
			}
			temp=(String)newComChar.get(new Integer(i));
			//System.out.println(temp+" "+ch);
			if(temp.equals("+-"))
			{
				newValue.append("±}{\\, \\pm \\,}{");
			}
			else if(temp.equals("x"))
			{
				newValue.append("ô}{\\, \\times \\,}{");
			}
			else if(temp.equals("n"))
			{
				newValue.append("æ}{\\ndash}{");
			}
			else
			{
				newValue.append(temp+"}{"+temp+"}{");
			}
			newValue.append((String)newComL.get(new Integer(i))+"."+(String)newComR.get(new Integer(i))+"}}%");
		}
	}
	return newValue.toString();
}


//end mark
}
