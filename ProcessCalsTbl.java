package tp.xt;
import tp.xt.util.*;
import java.util.*;
import java.io.*;
import java.util.regex.*;
class ProcessCalsTbl
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

	Hashtable tabularchar;
	boolean isMoreRows = false;//abhay
	String Valign="";//ravi
	Hashtable moreRow; 
	// Ravi
	boolean theadColFlag = false;
		int spos=0;
		int epos=0;
		int tempcounter=0;
		boolean boltablColHead=false;
	ProcessCalsTbl(XMLObjects xmlObj)
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

		String tag                  = tableTag;
		String tblCaption           = "";
		String tblLbl               = "";
		StringBuffer tableContents  = new StringBuffer();
		String tblId                = xmlObj.getAttributeValue(tag, "ID");
		String tblBorder            = xmlObj.getAttributeValue(tag, "FRAME");
		String tableRowsep         = xmlObj.getAttributeValue(tag, "ROWSEP");
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

		if (tblId.length()>0)
			tableContents.append(xmlObj.getHypertarget(tblId));
 		while (!tag.equals("</CE:TABLE>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.equals("<CE:CAPTION>")||tag.startsWith("<CE:CAPTION "))//28-08-2012 JADTD520 Updation
				{
					isCaption=true;
					XT.isTabCaption = true;
					XT.isTabCapFirstPara = true;
					//System.out.println("tab cap");
					while (!tag.equals("</CE:CAPTION>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if (tag.equals("<CE:SIMPLE-PARA>")  || tag.startsWith("<CE:SIMPLE-PARA"))//04-01-2005
							{
								if(XT.isTabCaption == true && XT.isTabCapFirstPara==false)//abhay 26/08/2006
								{
									//System.out.println("tab simple para > 1");
									if((xmlObj.jid.equalsIgnoreCase("PALEVO")||xmlObj.jid.equalsIgnoreCase("REVRHU")||xmlObj.jid.equalsIgnoreCase("DIABET")||xmlObj.jid.equalsIgnoreCase("ERAP")||xmlObj.jid.equalsIgnoreCase("HYA")||xmlObj.jid.equalsIgnoreCase("STMAT")||xmlObj.jid.equalsIgnoreCase("CYA")||xmlObj.jid.equalsIgnoreCase("DF")||xmlObj.jid.equalsIgnoreCase("RESU")||xmlObj.jid.equalsIgnoreCase("ANTRO")||xmlObj.jid.equalsIgnoreCase("ANDO")||xmlObj.jid.equalsIgnoreCase("REVAL")||xmlObj.jid.equalsIgnoreCase("MEDMAL")||xmlObj.jid.equalsIgnoreCase("NEUCHI")||(xmlObj.jid.equalsIgnoreCase("NEUADO") && XT.fmcforall==false)||xmlObj.jid.equalsIgnoreCase("MEDDRO")||xmlObj.jid.equalsIgnoreCase("RBMRET")||xmlObj.jid.equalsIgnoreCase("IRBM")||xmlObj.jid.equalsIgnoreCase("CANRAD")||(xmlObj.jid.equalsIgnoreCase("SCISPO")&&(Integer.parseInt(XT.plusTable.get(xmlObj.jid).toString())>Integer.parseInt(xmlObj.aid)))||xmlObj.jid.equalsIgnoreCase("NUTCLI")||xmlObj.jid.equalsIgnoreCase("ANCAAN")||xmlObj.jid.equalsIgnoreCase("CHIMAI")||xmlObj.jid.equalsIgnoreCase("JTS") ||xmlObj.jid.equalsIgnoreCase("TRACLI")||xmlObj.jid.equalsIgnoreCase("REVMED")|| (xmlObj.jid.equalsIgnoreCase("ENCEP") && Integer.parseInt(xmlObj.aid)>795) ||xmlObj.jid.equalsIgnoreCase("SCOMS")||xmlObj.jid.equalsIgnoreCase("MONRHU")||xmlObj.jid.equalsIgnoreCase("PATBIO")||xmlObj.jid.equalsIgnoreCase("BIOPHA")||xmlObj.jid.equalsIgnoreCase("BONSOI")||(xmlObj.jid.equalsIgnoreCase("BIONUT"))||(xmlObj.jid.equalsIgnoreCase("BIOMAG"))||xmlObj.jid.equalsIgnoreCase("CULHER")) && HeadGroup.isFloats == true)
										tblCaption += "\\\\\r\n";
								}
								XT.isTabCapFirstPara = false;

								tblCaption += xmlObj.extractData("</CE:SIMPLE-PARA>", true);
							}
						}
					}
					XT.isTabCaption = false;//26/08/2006
					XT.isTabCapFirstPara=true;

					tableContents.append("\r\n\\tbl{");
					tableContents.append(tblCaption+"}{%");
				}
				//added by avinandan
				else if(tag.startsWith("<CE:LINK"))
				{
					String linkId= xmlObj.getAttributeValue(tag, "LOCATOR");
					if(linkId.length() > 0)
					{
						tableContents.append("\r\n{%\r\n\\epsfbox{"+linkId.toLowerCase()+".eps}}%\r\n");
					}
				}
				//end mark
				else if (tag.equals("<CE:LABEL>"))
				{
					isCaption=true;
					isLabel=true;
					tblLbl= xmlObj.extractData("</CE:LABEL>", true);
					tableContents.append("\r\n\\TBLNO{"+tblLbl+"}");
					tag=xmlObj.getNextTag();
					if(!tag.startsWith("<CE:CAPTION")||!tag.startsWith("<CE:CAPTION "))//28-08-2012 JADTD520 Updation
					{
						tableContents.append("\r\n\\tbl{}{%");
					}
				}
				else if (tag.startsWith("<TGROUP"))
				{
					isTgroup=true;
					tablecolAligns= getTableAlignment(tag);
					hhlinecol=Integer.parseInt(xmlObj.getAttributeValue(tag, "COLS"));					
					String strip="";
					strip=xmlObj.getAttributeValue(tag, "ALTIMG");
					if(strip.length()>0)
						tableContents.append("\r\n\\begin{inlinestripns}{"+strip.toLowerCase()+"}");
					tableContents.append(insertValueNewCommand(xmlObj.getAttributeValue(tag, "COLS")));
					while (!tag.equals("</TGROUP>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
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
												String rowsep= xmlObj.getAttributeValue(tag, "ROWSEP");
												theadColFlag=true;
												//Added By Ravi [18/11/2006 By Vivek   adding \TblColHead{ }in table head label] 

												if((XT.modelStyle.equals("7")||(XT.modelStyle.equals("-Mod7IChemE"))) && (theadFlag==true))
												{
													boltablColHead=true;
													
												}
												else
												{
													boltablColHead=false;
													
												}

												String rowData = processTableRow(tag,theadFlag); 
												//Added By Ravi [18/11/2006 By Vivek   adding \TblColHead{ }in table head label] 
												theadColFlag=false;
												boltablColHead=false;
												tempcounter=0;

												/*if(XT.modelStyle.equals("7"))//abhay 21/06/2006
												{
													rowData = rowData.substring(0,rowData.lastIndexOf("}{@{}c@{") + "}{@{}c@{".length()) + "\\hskip12pt" + rowData.substring(rowData.lastIndexOf("}{@{}c@{")+"}{@{}c@{".length());
													//System.out.println("\n\n--->"+rowData);
												}*/

												tableHeadStr += rowData;
											
												if(rowsep.equals("1"))
												{
													tableHeadStr+= "\r\n\\colrule";
												}
											}
										}
										else if (tag.equals("</THEAD>"))
										{
											theadFlag=false;
											boltablColHead=false;//Addec by Ravi [18/11/2006]
											tempcounter=0;//Addec by Ravi [18/11/2006]
										}
									}
								}
							}
							if (tag.startsWith("<TBODY"))
							{
								while (!tag.equals("</TBODY>"))
								{
									ch= (char)xmlObj.fin.read();
									if (ch=='<')
									{
										tag= xmlObj.getTag().toUpperCase();										
										if (tag.startsWith("<ROW"))
										{
											//System.out.println(tag);
											//String t=processTableRow(tag,theadFlag);
											//System.out.println("value-->"+t);
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
//								System.out.println("tablecolAligns::>>>"+tablecolAligns);
								if(tableContents.indexOf("begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}")!=-1)
								{
									//System.out.println("Entered"+moretabular+tableContents);
									tableContents.append("\\\\\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");
									moretabular=true;
								}
								else
								{
									tableContents.append("\r\n\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}");
								}

								for(int align=0; align<tablecolAligns.size(); align++)
								{
									String alignValue=tablecolAligns.get(align).toString();
									///System.out.println("Alignment-->"+alignValue);
									/*if(alignValue.equals("+-"))
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
									}*/
									if((!alignValue.equals("l")) && (!alignValue.equals("r")) && (!alignValue.equals("c")))
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
									}
									else
									{
										tableContents.append(alignValue+"@{}");
									}
								}

								tableContents.append("}");
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
								if(tblBorder.indexOf("BOT")>-1)
								{
									if(tableContents.toString().endsWith("\r\n\\colrule"))
										tableContents.delete(tableContents.length()-10,tableContents.length());
									//tableContents.append("\r\n\\botrule");
									if(tableContents.indexOf("\r\n\\botrule")>-1)//rajeev-081004
									{
										tableContents.replace(tableContents.indexOf("\r\n\\botrule"),tableContents.indexOf("\r\n\\botrule")+10,"[10pt]");
										tableContents.append("\r\n\\botrule");
									}
									else
										tableContents.append("\r\n\\botrule");
								}
									tableContents.append("\r\n\\end{tabular*}");
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
									tableLegends.append("\r\n\r\n"+xmlObj.extractData("</CE:SIMPLE-PARA>", true));
								}
							}
						}
					}
					tableLegends.append("}");
				}
			}
		}
//System.exit(0);
		if(isCaption == true)
		{
			tableContents.insert(0,"\\begin{table}");
			if(isTgroup==false && tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")!=-1 && (!tblBorder.equals("NONE")))
			{
				tableContents.insert(tableContents.indexOf("{%\r\n{%\r\n\\epsfbox")+4,"\\begin{tabular*}{\\hsize}{@{}@{\\extracolsep{\\fill}}l}\r\n\\toprule");
				tableContents.append("\\botrule\r\n\\end{tabular*}");
			}
			tableContents.append("}");			
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
			}
			tableContents.append(tableLegends);
			tableContents.append(tableFootnotes);
		}

		/*if((tableContents.indexOf("\\hhline")!=-1)||(tableContents.indexOf("multicolumn{1}{|")!=-1))
		{			
			//System.out.println("ENTERED");
			String rtemp=tableContents.toString();
			//System.out.println("Table"+rtemp);
			rtemp=rtemp.replaceFirst("\r\n\\\\begin\\{tabular\\*\\}\\{\\\\hsize\\}\\{@\\{\\}@\\{\\\\extracolsep\\{\\\\fill\\}\\}","\r\n\\\\tabcolsep12pt\r\n\\\\begin{tabular}{@{}");
			rtemp=rtemp.replaceFirst("\r\n\\\\end\\{tabular\\*\\}","\r\n\\\\end{tabular}");
			//System.out.println("Table"+rtemp);
			tableContents.delete(0,tableContents.length());
			tableContents.append(rtemp);
		}*/
		/*int p=0;
		p=tableContents.indexOf("begin{tabular*}");

		if(p!=-1)
		{
			while(p!=-1)
			{
				p=table.indexOf("begin{tabular*}",p);
			}
		}*/
		//System.out.println("Entered"+moretabular+tableContents);
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
		/*if(isCaption==false && tblLbl.length()==0)
		{			
			int f=tableContents.indexOf("\\begin{tabular*}");  
			int g=tableContents.lastIndexOf("\\end{tabular*}");
			if (f!=-1 && g!=-1)
			{
				tableContents.insert(f,"\\setcr{\\tablefont");
				tableContents.insert(g+"\\setcr{\\tablefont".length()+"\\end{tabular*}".length(),"}");
			}			
		}*/
		/////BEGIN TABULAR END TABULAR CASE
		/*if(!tablecolAligns.contains("n"))
		{
			String hhh=tableContents.toString();
			hhh = hhh.replaceAll("æ","{\\\\ndash}");
			tableContents=new StringBuffer(hhh);
		}*/
//-Mod7IChemE
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE")))
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
							prev = 0;
						}
					}
					
					start = Integer.parseInt(tableContents.substring(tableContents.indexOf("{", tempindex)+1, tableContents.indexOf("-", tempindex)));
					end = Integer.parseInt(tableContents.substring(tableContents.indexOf("-", tempindex)+1, tableContents.indexOf("}", tempindex)));
					total = end - start + 1;
					
					tableContents.delete(tableContents.indexOf("\\cline{", tempindex), tableContents.indexOf("}", tempindex));
					for(int u=prev + 1; u < start ; u++)
					{
						tableContents.insert(tempindex, " & ");
						tempindex += 3;
					}
					prev = end;
					tableContents.insert(tempindex,"\\cline{"+total);
					tempindex += 5;
				}
			}
		}

		String tempcol=tableContents.toString();
//-Mod7IChemE
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE")))
		{
			tempcol=tempcol.replaceAll("\\\\cline\\{([0-9]+)","\\\\multicolumn{$1}{@{}l@{}}{\\\\hrulefill");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\} \r\n","}{@{}l@{\\\\hskip6pt}}{\\\\hrulefill}\\\\\\\\\r\n");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\}\r\n","}{@{}l@{\\\\hskip6pt}}{\\\\hrulefill}\\\\\\\\\r\n");
			tempcol=tempcol.replaceAll("\\}\\{@\\{\\}l@\\{\\}\\}\\{\\\\hrulefill\\}\\\\multicolumn\\{","}{@{}l@{}}{\\\\hrulefill} & \\\\multicolumn{");
		}
		
		tempcol=tempcol.replaceAll("\\\\colrule\\[([0-9]+)pt\\]","\\\\colrule\\\\\\\\\\[$1pt\\]");
		tableContents=new StringBuffer(tempcol);
//-Mod7IChemE
		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE")))
		{
			if(tableContents.indexOf("}{@{}l@{\\hskip6pt}}{\\hrulefill}\\\\\r\n") != -1)
			{
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

		return tableContents.toString();
	}
/*	public String processMultirow(String table)
	{
		String content="";

		String head="";
		String body="";
		String tail="";
		head=table.substring(0,table.indexOf("\\begin{tabular"));
		body=table.substring(table.indexOf("\\begin{tabular"),table.indexOf("\\end{tabular"));
		tail=table.substring(table.indexOf("\\end{tabular"));

		String [] row=body.split("\\\\\\\\\r\n");
		
		Vector ht=new Vector();
		for (int i=0;i<row.length ;i++ )
		{
			if(row[i].indexOf("\\multirow<<")!=-1)
			{
				String entry[]=row[i].split("&");
				String finalValue="";
				for (int j=0;j<entry.length ;j++ )
				{
					String value=entry[j];
					if(value.indexOf("\\multirow<<")!=-1)
					{
						String temp=value.substring(value.indexOf("\\multirow<<"),value.indexOf(">>"));

						int a=Integer.parseInt(temp.substring(temp.indexOf("\\multirow<<")+"\\multirow<<".length(),temp.indexOf("-")));
						int b=Integer.parseInt(temp.substring(temp.indexOf("-")+1));
						int d=i;
						for(int c=0;c<a;c++)
						{
							d=d+1;
							ht.add((String)""+d+"<-->"+b);
						}
						value=value.replaceFirst("<<([0-9]+)-([0-9]+)>>","");
						finalValue+=value+"&";

					}
					else
						finalValue+=entry[j]+"&";
				}
				finalValue=finalValue.substring(0,finalValue.length()-1);
				row[i]=finalValue;

			}
			if(i<(row.length-1))
				content+=row[i]+"\\\\"+"\r\n";
			else
				content+=row[i];

		}
		String row1[]=content.split("\\\\\\\\\r\n");
		System.out.println(row1.length);
		content="";
		for (int i=0;i<row1.length ;i++ )
		{
			String entry[]=row1[i].split("&");
			String input="";
			String value="";
			for (int j=0;j<entry.length ;j++ )
			{
				input=i+"<-->"+(j+1);
				System.out.println("Input-->"+input+" "+entry.length);
				if(ht.contains(input))
				{
					System.out.println("Input-->entered"+input);

					value+=entry[j]+"& &";
				}
				else
					value+=entry[j]+"&";

			}
			value=value.substring(0,value.length()-1);
			row1[i]=value;
			if(i<(row1.length-1))
				content+=row1[i]+"\\\\\r\n";
			else
				content+=row1[i];
		}
		for(int e=0;e<ht.size();e++)
			System.out.println((String)ht.elementAt(e));

		content=head+content+tail;
		return content;
	}*/

	public String processTableRow(String rowtag, boolean theadFlag)throws java.io.IOException
	{
		rowSepValue="";
		StringBuffer rowContants= new StringBuffer();
		String tag= rowtag;
		String morerow="";
		boolean firstEntry= true;
		int entryPos= 0;
		int count=0;
		boolean topbrd=false;
		while (!tag.equals("</ROW>"))
		{
			//System.out.println("Raj1-->"+tag);
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				//System.out.println(tag);
				if (tag.startsWith("<ENTRY"))
				{
					//Avinandan added
					morerow=xmlObj.getAttributeValue(tag, "MOREROWS");
					if(morerow.length()>0)
					{
						moreRow.put(new Integer(count), morerow);
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
					int ind=0;
					if((moreRow.containsKey(new Integer(count))) && (ind=tag.indexOf("MOREROWS"))<1)
					{
						while(true)
						{
							morerow=(String)moreRow.get(new Integer(count));
							ind=Integer.parseInt(morerow);
							if(ind==1)
							{
								moreRow.remove(new Integer(count));
							}
							else if(ind>1)
							{
								moreRow.remove(new Integer(count));
								morerow="";
								ind--;
								morerow=morerow+ind;
								moreRow.put(new Integer(count), morerow);
							}
							else
								break;

							isMoreRows = true;//abhay

							if(count==0)
							{
								rowContants.append("\r\n & ");
							}
							else if(count>1)
							{
								//rowContants.append(" & ");//multirow-rajeev
							}
							else
								rowContants.append(" & ");
							try
							{
								count++;
								if(moreRow.containsKey(new Integer(count)))
								{
								}
								else
								{
									break;
								}
							}
							catch(Exception e)
							{
								break;
							}
						}//end of while
					}//end of if
					count++;
					//end of end mark
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
								rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
							else
							{
								rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";//+span;
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
						}
						xmlObj.fin.seek(l);
						tableEntryValue= processTableEntry(tag, entryPos);
						
						//HHLINE BLOCK
						/*if(tableEntryValue.indexOf("<TOPBRD>")!=-1)
						{
							//System.out.println(""+tableEntryValue);
							tableEntryValue=tableEntryValue.replaceFirst("<TOPBRD>","");
							hhline="\r\n\\hhline{";
							int f=0;
								if(hhlinestart.length()!=0 && hhlineend.length()!=0)
								{
									for(f=0;f<(Integer.parseInt(hhlinestart)-1);f++)									
										hhline+="~";
									for(;f<Integer.parseInt(hhlineend);f++)									
										hhline+="-";
									for(;f<hhlinecol;f++)									
										hhline+="~";
								}
								else
								{
									for(f=0;f<hhlinecol;f++)
									{
											if(f==entryPos)
												hhline+="-";
											else
												hhline+="~";
									}
								}							
							hhline+="}";
							topbrd=true;
						}*/
						//END OF HHLINE BLOCK
					}
					if(tableEntryValue.length()>0)
					{
						if (firstEntry==true)
							rowContants.append("\r\n"+tableEntryValue);
						else
						{
							rowContants.append("\r\n"+" & "+tableEntryValue);
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
					}
					//System.out.println("raj->"+entryPos);
					entryPos++;
					firstEntry= false;
				}
			}
			//System.out.println("Entered");
		}//end of while
		//Avinandan DEclare til end mark
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
		//end mark
		//Avinandan comment one line below
		//rowContants.append("\\\\");
//System.out.println("START::"+rowContants);
		if(rowSepValue.length()>0)
		{
			rowSepValue=conCline(rowSepValue,theadFlag);
			if(rowContants.indexOf("<END>")>=0)
			{
//				System.out.println("END::"+rowContants);
				rowContants = rowContants.delete(0,7);
				rowContants = rowContants.delete(rowContants.length()-2,rowContants.length());
			}
			else
			{
				rowContants.append("\r\n"+rowSepValue);
			}
		}
		else
		{
			if(rowContants.indexOf("<END>")>=0)
			{
//				System.out.println("END1::"+rowContants);
				rowContants = rowContants.delete(0,7);
				/**
				*Added By Ravi [07/03/2007]
				* Change Request By: Vivek
				*Change Point : <END> Tag not required
				*/
				if(rowContants.indexOf("<END>")!= -1)
				{
					rowContants = rowContants.delete(0,5);
				}
				//end
				rowContants = rowContants.delete(rowContants.length()-2,rowContants.length());
			}
		}
		rowSepValue="";
//System.out.println("START::"+rowContants);
		/*if(topbrd==true)
		{
			rowContants.insert(0,hhline);
		}*/

		hhline="";
		hhlinestart="";
		hhlineend="";
		topbrd=false;
		if(globalRowsep==true && rowContants.indexOf("\\botrule")==-1 && rowContants.indexOf("\\toprule")==-1)
			rowContants.append("\r\n\\colrule");

		return rowContants.toString();
	}


public String processTableEntry(String entryTag, int entryPos)throws java.io.IOException
	{
		//System.out.println("entryTag->"+entryTag);
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
		


//		hhlinestart        = xmlObj.getAttributeValue(tag, "NAMEST");
//		hhlineend        = xmlObj.getAttributeValue(tag, "NAMEEND");

		hhlinestart        = colst;
		hhlineend        = colend;



		boolean leftBorder=false;
		boolean rightBorder=false;
		boolean topBorder=false;
		boolean bottomBorder=false;

		//System.out.println(tag+"TAG:::");

		/*long fpos=xmlObj.fin.getFilePointer();

		boolean leftBorder=false;
		boolean rightBorder=false;
		boolean topBorder=false;
		boolean bottomBorder=false;

		//char ch= (char)xmlObj.fin.read();		
		//if (ch=='<')
		//{
		//	tag= xmlObj.getTag().toUpperCase();
			while (!tag.equals("</ENTRY>"))
			{
				char ch= (char)xmlObj.fin.read();		
				if (ch=='<')
				{
					tag= xmlObj.getTag().toUpperCase();
					if(tag.equals("<tb:left-border/>"))
						leftBorder=true;//
					else if(tag.equals("<tb:right-border/>"))
						rightBorder=true;//
					else if(tag.equals("<tb:top-border/>"))
						topBorder=true;//
					else if(tag.equals("<tb:bottom-border/>"))
						bottomBorder=true;//
					//else
						//
				}
				//else
					//
			}
		//}
		xmlObj.fin.seek(fpos);*/
		

				

		
		int span=1;
		int nameend= 0;
		int namest= 0;
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
			span        = nameend-namest+1;
		}
		catch(NumberFormatException numexp)
		{
		}
		if(rowsep.equals("1"))
		{
			if(span==1)
				rowSepValue+= "\\cline{"+(entryPos+1)+"-"+(entryPos+1)+"}";
			else
				rowSepValue+= "\\cline{"+namest+"-"+nameend+"}";//+span;
		}
		///System.out.println ("tablecolAligns : "+tablecolAligns.size());
		///System.out.println ("entryPos : "+entryPos);
		String mainalignValue = (String)tablecolAligns.get(entryPos);
		///System.out.println("3->"+mainalignValue);
		/*String mainalignValue = "";
		try
		{
			String mainalignValue = (String)tablecolAligns.get(entryPos);
		}
		catch(Exception exp)
		{
			mainalignValue="l";
		}*/
		String alignValue     = getTableEntryAlignment(tag);
		//System.out.println("3->"+alignValue);
		if(alignValue.length() == 0)
		{
			alignValue="l";
		}
		String entryValue     = xmlObj.extractData("</ENTRY>", true);
		//System.out.println("EntryValue-->"+entryValue);
		/////////////entryValue=entryValue.replaceAll("\\{\\\\it\\{\\\\bi","\\{\\{\\\\bi");
		/////////////entryValue=entryValue.replaceAll("\\{\\\\bf\\{\\\\bi","\\{\\{\\\\bi");

		if(entryValue.indexOf("<RIGHT>")!=-1)
		{
			entryValue=entryValue.substring(0,entryValue.indexOf("<RIGHT>"))+entryValue.substring(entryValue.indexOf("<RIGHT>")+7);
			rightBorder=true;
		}
		if(entryValue.indexOf("<TOP>")!=-1)
		{
			entryValue=entryValue.substring(0,entryValue.indexOf("<TOP>"))+entryValue.substring(entryValue.indexOf("<TOP>")+5);
			topBorder=true;
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
		if(leftBorder==true && rightBorder==true && bottomBorder==true && topBorder==true)
			entryValue="{\\allborder{10}{10}}"+entryValue;
		else if(leftBorder==true || rightBorder==true)
			entryValue="{\\lrborder{10}{0}{0}}"+entryValue;
		else if(colsep.equals("1"))
			entryValue=entryValue+"{\\lrborder{10}{0}{0}}";
		

		//Avinandan Added
		entryValue=checkEntryValue(entryValue, alignValue);//declare by avinandan
		if(alignValue.equals("&PLUSMINUS;") ||alignValue.equals("&PLUSMN;"))
		{
			if(entryValue.indexOf("\\(\\pm\\)")!=-1)
			{
				entryValue=entryValue.replaceFirst("\\\\pm","±");
				entryValue=entryValue.replaceFirst("\\(±","±");
				entryValue=entryValue.replaceFirst("\\\\±\\\\","±");
				entryValue=entryValue.replaceFirst("±\\)","±");
				entryValue=entryValue.replaceFirst("([ ]*)±([ ]*)","±");
				if(entryValue.indexOf("\\underline{") != -1)
				{
					entryValue=entryValue.replaceFirst("±","{\\\\(\\\\,\\\\pm\\\\,\\\\)\\\\hbox{");
					entryValue="\\hbox"+entryValue;
				}
				else
				{
					entryValue=entryValue.replaceFirst("±","}\\\\hbox to 0pt{\\\\(\\\\,\\\\pm\\\\,\\\\)\\\\hbox{");
					entryValue="\\hbox{"+entryValue;
				}
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

		if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE"))|| XT.jid.equalsIgnoreCase("ZOOGA")|| (XT.modelStyle.equals("-NEULAB"))|| (XT.jid.equalsIgnoreCase("PIO")))
			{
				if(boltablColHead==true)
				{
				//	tempcounter++;
				//	if(tempcounter==1)
				//		entryValueWithVsp="\\TblColHead{"+entryValueWithVsp;
				//	else
						entryValueWithVsp="\\TblColHead{"+entryValueWithVsp+"}";
						//System.out.println("111111111111111111111111111111111111111111");
				}
			}

		if(entryValueWithVsp.indexOf("\\hbox to 0pt")!=-1)
		{
			entryValueWithVsp+="}";
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
			if(entryValue.indexOf("\\underline{") != -1 && (alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;") || alignValue.equals("&NDASH;")))
			{
				entryValueWithVsp=entryValueWithVsp+"}";
			}
			entryValueWithVsp=entryValueWithVsp+"}";
		}
		//end mark
		//entryValue= getEntryValueWithAlignmaent(alignValue, entryValue); //old
//Added By Ravi [18/11/2006 feedback by Vivek for table colhead]

			if((XT.modelStyle.equals("7"))||(XT.modelStyle.equals("-Mod7IChemE")))
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
		}
		else if(morerows.length()>0)
		{
			//\multirow{4}{1in}{Common g text}
			//Avinanda Commented
			//tableCellValue= "\\multirow{"+morerows+"}{1in}{"+entryValue+"}";
			//Avinandan Added one row
			//morerows=(String)(Integer.parseInt(morerows)+1);//rajeev-6-11-04
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
				if(Valign.equalsIgnoreCase("MIDDLE"))
				{
					tableCellValue= "\\Cmultirow{"+g+"}{0in}{"+entryValueWithVsp;
				}
				else if(Valign.equalsIgnoreCase("BOTTOM"))
				{
					tableCellValue= "\\Bmultirow{"+g+"}{0in}{"+entryValueWithVsp;
				}
				else if(Valign.equalsIgnoreCase("TOP"))
				{
					tableCellValue= "\\Tmultirow{"+g+"}{0in}{"+entryValueWithVsp;
				}
				else
				{
					tableCellValue= "\\multirow{"+g+"}{0in}{"+entryValueWithVsp;
				}
				//end
			
			morerows="";
		}
		else if(!alignValue.equals(mainalignValue))
		{
			/*tag+"<With Out Span>"+entryPos+"<>"+mainalignValue+"<>"+alignValue+*/

			//Avinandan Commented
			//tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValue+"}";
			//Avinandan Added one row
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
				tableCellValue= entryValueWithVsp;
			else if(alignValue.equals("&NDASH;"))
				tableCellValue= entryValueWithVsp;
			else if(alignValue.equals("&TIMES;"))
				tableCellValue= "\\multicolumn{"+span+"}{@{}ô@{}}{"+entryValueWithVsp;
			else
			{
				if(!(alignValue.equalsIgnoreCase("l") || alignValue.equalsIgnoreCase("r") || alignValue.equalsIgnoreCase("c")))
				{
					String rep=alignValue;
					if(rep.equals("("))
						rep="\\(";

					if(rep.equals("."))
						rep="\\.";
					if(rep.equals("*"))
						rep="\\*";
					if(rep.equals("\\("))
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{\\\\"+rep);
					if(rep.equals("/"))
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{"+rep);
					else
						entryValueWithVsp=entryValueWithVsp.replaceFirst(rep,"\\\\hbox to 0pt{"+rep);
				}
				tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValueWithVsp;
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
				Valign="";*/
				if(tableCellValue.indexOf("\\hbox to 0pt{") != -1)
				{
					//System.out.println(tableCellValue);
					tableCellValue+="}";
				}
			}
			
		}
		else if(span>1)
		{
			/*tag+"<With Span>"+entryPos+"<>"+mainalignValue+"<>"+alignValue+*/
			//Avinandan Commented
			//tableCellValue= "\\multicolumn{"+span+"}{@{}"+alignValue+"@{}}{"+entryValue+"}";
			//Avinandan Added one row
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
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
		}

		/*if(colsep.equals("1"))
		{
			if(tableCellValue.startsWith("\\multicolumn{"))
			{
				tableCellValue=tableCellValue.replaceFirst("@\\{\\}\\}","@{}}{\\lrborder{10}{0}{0}}");
			}
			else
			{
				tableCellValue="{\\lrborder{10}{0}{0}}"+tableCellValue;
			}
			//System.out.println("Border-"+leftBorder);

		}*/

		/*if(leftBorder==true)
		{
			if(tableCellValue.startsWith("\\multicolumn{"))
			{
				if(tableCellValue.startsWith("\\multicolumn{1}{@{}l@{}|}{"))
					tableCellValue=tableCellValue.replaceFirst("multicolumn\\{1\\}\\{","multicolumn{1}{|");
				else
					tableCellValue=tableCellValue.replaceFirst("\\{@\\{\\}","{|@{}");
			}
			else
			{
				tableCellValue="\\multicolumn{1}{|@{}l@{}}{"+tableCellValue+"}";
			}

		}
		if(rightBorder==true)
		{
			if(tableCellValue.startsWith("\\multicolumn{"))
			{
				if(tableCellValue.startsWith("\\multicolumn{1}{@{}l@{}|}{"))
				{
					//tableCellValue=tableCellValue.replaceFirst("multicolumn\\{1\\}\\{","multicolumn{1}{|");
				}
				else
					tableCellValue=tableCellValue.replaceFirst("@\\{\\}\\}","@{}|}");
			}
			else
			{
				tableCellValue="\\multicolumn{1}{@{}l@{}|}{"+tableCellValue+"}";
			}

		}
		if(topBorder==true)
		{
			tableCellValue="<TOPBRD>"+tableCellValue;
		}*/

		/*if(tableCellValue.startsWith("\\hbox{") && tableCellValue.indexOf(".\\hbox{") !=-1 && tableCellValue.indexOf("{\\bf{") !=-1 && tableCellValue.indexOf(".\\hbox{{\\bf{") ==-1)
		{
			System.out.println("Entered"+tableCellValue);
			tableCellValue=tableCellValue.replaceFirst("\\.\\\\hbox\\{","}}.\\\\hbox{{\\\\bf{");
			System.out.println("After Entered"+tableCellValue);
		}*/

		//System.out.println("<<>>"+tableCellValue);
//		boolean b= Pattern.matches("\\\\hbox\\{([0-9]+([\\\\][\\,])*[0-9]*)([^0-9\\.\\,\\\\])([^\\.]+)\\}",tableCellValue);
		boolean b= Pattern.matches("\\\\hbox\\{([0-9]+)([^0-9\\.])([^\\.]+)\\}",tableCellValue);
		///System.out.println(b+"<<align>>"+alignValue);
		if(b)
		{
			//System.out.println("<<Entered>>"+tableCellValue);
			if(tableCellValue.indexOf("\\hbox to 0pt")==-1)
			{
				//System.out.println("<<Entered1>>"+tableCellValue);
				tableCellValue=tableCellValue.replaceFirst("\\\\hbox\\{([0-9]+)([^0-9\\.])([^\\.]+)\\}","\\\\hbox\\{$1\\}\\\\hbox to 0pt\\{$2$3\\}");
				//System.out.println("<<Entered1----->>"+tableCellValue);
			}
			
		}
 		return tableCellValue;
	}
	//Avinandan declare checkEntryValue function
	public String checkEntryValue(String entryValue, String alignValue)
	{
				
		if((entryValue.startsWith("{\\bf{")) && (entryValue.endsWith("}}")))
		{
			
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
			{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\bf{");
					//if(entryValue.indexOf("125")!=-1)
					//	System.out.println(entryValue);
				
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
		else if((entryValue.startsWith("{\\it{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
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
					entryValue=entryValue.replaceAll("\\.","}}.{\\\\it{");
				}
			}
		}//end of else if
		else if((entryValue.startsWith("{\\underline{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
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
		else if((entryValue.startsWith("{\\it{\\bi{") || entryValue.startsWith("{\\bf{\\bi{") || entryValue.startsWith("{{\\bi{")) && (entryValue.endsWith("}}")))
		{
			if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
			{
				if(entryValue.indexOf("\\(\\pm\\)")!=-1)
				{
					entryValue=entryValue.replaceAll("([ ]*)\\\\\\(\\\\pm\\\\\\)([ ]*)","\\\\(\\\\pm\\\\)");
					if(entryValue.indexOf("\\it",6)==-1 && entryValue.indexOf("\\bf",6)==-1 && entryValue.indexOf("\\bi",6)==-1 )
					entryValue=entryValue.replaceAll("\\\\\\(\\\\pm\\\\\\)","}}\\\\(\\\\pm\\\\){\\\\bi{");
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
					entryValue=entryValue.replaceAll("\\.","}}.{\\\\bi{");
				}
			}
		}//end of else if

			
		
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

		//avinandan added
		newComL=new Hashtable();
		newComR=new Hashtable();
		newComChar=new Hashtable();
		int colNum=0;
		String ColValue="";
		//end mark
		etag=etag.substring(etag.indexOf("COLS=\"")+"COLS=\"".length());
		etag=etag.substring(0,etag.indexOf("\""));
		int columCount=Integer.parseInt(etag);
//		System.out.println(columCount+"etag"+etag);
		long filePos= xmlObj.fin.getFilePointer();
		String maxCols= xmlObj.getAttributeValue(tag, "COLS");
		Vector alignments= new Vector();
		Vector charColAligns= new Vector();
		//String rs1="";
		while (!tag.equals("</TGROUP>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				tag= xmlObj.getTag().toUpperCase();
				if (tag.startsWith("<ROW"))
				{
					colNum=0;
					Vector colAlign= new Vector();
					while (!tag.equals("</ROW>"))
					{
						ch= (char)xmlObj.fin.read();
						if (ch=='<')
						{
							tag= xmlObj.getTag().toUpperCase();
							if(tag.startsWith("<ENTRY"))
							{
								//rs1=xmlObj.getAttributeValue(tag, "MOREROWS");
								colNum++;
								String entryAlign= getTableEntryAlignment(tag);
								///System.out.println("entryAlign<<>>"+entryAlign);
								colAlign.add(entryAlign.toUpperCase());
								int span=0;
								try
								{

									String colend="";                 /////COL1 handling, 25-11-04
									colend=xmlObj.getAttributeValue(tag, "NAMEEND");
									colend=colend.replaceAll("[a-zA-Z]+","");
									
									String colst="";
									colst=xmlObj.getAttributeValue(tag, "NAMEST");
									colst=colst.replaceAll("[a-zA-Z]+","");

//									int nameend = Integer.parseInt(xmlObj.getAttributeValue(tag, "NAMEEND"));
//									int namest  = Integer.parseInt(xmlObj.getAttributeValue(tag, "NAMEST"));
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
									{
										colNum++;
										colAlign.add("l");
										//span--;
									}
								}
								///System.out.println("Align-->"+entryAlign);
								//
								if(entryAlign.toUpperCase().equals("&PLUSMINUS;"))
								{
									insertValueForNewCommand("&PLUSMN;", colNum);
								}
								else if(entryAlign.toUpperCase().equals("&PLUSMN;"))
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
							}
						}
					}

					///System.out.println("colAlign>>"+colAlign);
					alignments.add(colAlign);
				}
			}
		}
		for(int i=0;i<20;i++)
		{
			if(newComL.containsKey(new Integer(i)))
			{
//				System.out.println("HHH : "+newComL.get(new Integer(i))+" <:> "+newComR.get(new Integer(i)));
			}
		}
		int totRows= alignments.size()+1;
		int totCols= ((Vector)alignments.get(0)).size();

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
					///System.out.println("Entered");
					///System.out.println("Entered"+colAlign+c+" "+r);
					colAlign   = "L";
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
					else if(align.equals("&PLUSMINUS;")||align.equals("&PLUSMN;"))
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

				//System.out.println("2->"+colaligns);
				GetMax maxr= new GetMax(colaligns);
				charColAligns.add(colalignstype[maxr.getMaxIndex()]);
				rowSize=col.size();
			//}
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
		///System.out.println("1->"+charColAligns);
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

	private String getEntryValueWithAlignmaent(String alignVal, String entryVal)
	{
		String tableCellValue= "";
		String alignValue= alignVal;
		String entryValue= entryVal;

		if(alignValue.equals(".") || alignValue.equals("/") || alignValue.equals(",") || alignValue.equals("&PLUS;")
			|| alignValue.equals("&MINUS;") || alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;") || alignValue.equals("&TIMES;") || alignValue.equals("&NDASH;"))
		{
			String lvalue="";
			String rvalue="";
			int ind=-1;
			if(entryValue.indexOf(".gif")==-1)
				ind= entryValue.indexOf(alignValue);			

			if(ind!=-1)
				lvalue= entryValue.substring(0, entryValue.indexOf(alignValue));
			else
			{
				lvalue= entryValue;
				alignValue="";
			}

			if(ind!=-1)
			{
				if(alignValue.equals("&PLUSMINUS;")||alignValue.equals("&PLUSMN;"))
				{
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+11);
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
					rvalue= entryValue.substring(entryValue.indexOf(alignValue)+1);
				}
			}

			if(lvalue.length()>0)
				lvalue="\\hbox{"+lvalue+"}";
			if(rvalue.length()>0 && !alignValue.equals("/"))
				rvalue="\\hbox{"+rvalue+"}";
			else if(rvalue.length()>0 && alignValue.equals("/"))
				rvalue=rvalue;
			
			if(alignValue.length()>0)
			{
				if(lvalue.endsWith("}}"))
				{
					tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
					//System.out.println(tableCellValue);
				}
				else if(lvalue.indexOf("\\bf") !=-1)
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
				else
					tableCellValue= lvalue+ "\\hbox to 0pt{"+alignValue+ rvalue+"}";
			}
			else
			{
				tableCellValue= lvalue+ alignValue+ rvalue;
			}
		}
		else
			tableCellValue= entryValue;
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
		if(theadFlag==true && Cline.startsWith("\\cline{1-"))
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
	return tagContent.toString();
}//end of getEntryContent()
//avinandan added getTempEntity()
//Avinandan Added insertValueForNewCommand()
void insertValueForNewCommand(String alignValue, int colNum)throws IOException
{	
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
	}
	else
	{
		if(alignValue.equals("&PLUSMN;"))
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
	}
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
//			System.out.println(temp);
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
