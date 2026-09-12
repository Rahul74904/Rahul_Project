package tp.xt;
import java.io.*;
import java.util.*;
import java.util.regex.*;

class NewMML2Tex
{
	String tag;
	XMLObjects xmlObj;
	///boolean isScript;
	//boolean mfracbevelled=false;

	
	NewMML2Tex(XMLObjects xmlObj)
	{
		tag        = "";
		this.xmlObj= xmlObj;
		///isScript=false;
	}
	XT xt=new XT();
//added By Ravi [06/03/2007]
public String MmlAndMOWithRmbox(String MML)throws IOException
{
	String tmpSubStr=MML;
	StringBuffer rm=null;
	String tmp="";
	String tmp1="";
	String tmp2="";
	int s=0;
	int e=0;
	int counter=0;
	s=tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD\">");
	while(s !=-1)
	{
		s=tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD\">",s+1);
		if(s!= -1)
		{
			e=tmpSubStr.indexOf("</MML:MSTYLE>",s);
			if(e != -1)
			{
				tmp1=tmpSubStr.substring(0,s);
				tmp2=tmpSubStr.substring(e+"</MML:MSTYLE>".length(),tmpSubStr.length());
				tmp=tmpSubStr.substring(s,e+"</MML:MSTYLE>".length());
				int m=tmp.indexOf("<MML:MTEXT>");
				while(m != -1)
				{
					m=tmp.indexOf("<MML:MTEXT>",m+1);
					if(m != -1)
					{
						counter++;
						
					}
				}
				if(counter>1)
				{
					rm=new StringBuffer(tmp);
					
					int st= rm.lastIndexOf("<MML:MTEXT>");
					rm=rm.delete(st,st+"<MML:MTEXT>".length());
					rm=rm.insert(st,"{\\bf{");
					st=0;
					
					st= rm.lastIndexOf("</MML:MTEXT>");
					//System.out.println("st==> "+st);
					if(st != -1)
					{
					
						rm=rm.delete(st,st+"</MML:MTEXT>".length());
						rm=rm.insert(st,"}}");
						//System.out.println("rm==> "+rm);
						//System.in.read();
						
					}
					tmpSubStr=tmp1+rm.toString()+tmp2;
					
					counter=0;
				}
				
			}
		
		}
	}
	return tmpSubStr;
}

public String MmlAndMO(String MML)throws IOException
{
//[blocked] 
  int pos=0;
  String tmpSubStr=MML;
  
  StringBuffer rm=new StringBuffer(tmpSubStr);
 
  while(pos != - 1)
  {
	pos=tmpSubStr.indexOf("</MML:MO>",pos+1);
	//System.out.println("pos : "+pos);
	if(tmpSubStr.indexOf("\\rmbox{{\\bf{",0) != -1)
	{
		int rmb=tmpSubStr.indexOf("\\rmbox{{\\bf{",0);
		if(tmpSubStr.indexOf("</MML:MTEXT><MML:MO>",rmb+1) != -1)
		{
			int sp=0;
			int ep=0;
			
			sp=rm.indexOf("</MML:MTEXT><MML:MO>",rmb+1);
			if(sp != -1)
			{
				ep=rm.indexOf("</MML:MO>}}",sp);
				if(ep != -1)
				{
					rm=rm.replace(sp+"</MML:MTEXT>".length(),sp+"</MML:MTEXT><MML:MO>".length(),"{$\\bm{");
					rm=rm.replace(ep+"</MML:MO>".length()-2,ep+"</MML:MO>".length(),"}$}}}");
				}
			}
		}
 		 tmpSubStr=rm.toString();
 		 //bold
 		 tmpSubStr=tmpSubStr.replaceAll("\\{\\\\rmbox\\{\\{\\\\bf\\{([A-Za-z]+)</MML:MTEXT><MML:MO>([\\\\])([A-Za-z]+) </MML:MO>\\{\\\\rmbox\\{","\\{\\\\rmbox\\{\\{\\\\bf\\{$1}}{\\$\\\\bm{$2$3}\\$} {\\\\bf{");
 		 //italic
		 tmpSubStr=tmpSubStr.replaceAll("\\{\\\\rmbox\\{\\{\\\\bf\\{([A-Za-z]+)</MML:MTEXT>([A-Za-z]+)</MML:MI>\\{\\\\rmbox\\{([^A-Za-z])","\\{\\\\rmbox\\{\\{\\\\bf\\{$1}}\\\\bf{$2} {\\\\rm\\{\\\\bf{$3}");
		  tmpSubStr=tmpSubStr.replaceAll("\\{\\\\rmbox\\{\\{\\\\bf\\{([A-Za-z]+)</MML:MTEXT>([A-Za-z]+)</MML:MI>\\{\\\\rmbox\\{([A-Za-z])","\\{\\\\rmbox\\{\\{\\\\bf\\{$1}}\\\\bf{$2} {\\\\rm\\{\\\\bf{$3}");
		 // System.out.println("tmpSubStr==> "+tmpSubStr);
		 // System.in.read(); 		
 		 

	}
	
  }
  
  return tmpSubStr;
//end
}
/**
*Added :By Ravi
*Date  :[05/07/2007] 
*Change Request Point : handling  \\Barchar{ in xt
*Change Request By : TPMS application
 
*/

public String OmagaEntityConverter(String omgaEnty)throws IOException
	{
		String omg=omgaEnty;
		
		String return_omt="";
		int omagaEntity=0;
		//omagaEntity=omg.indexOf("&#822;",0);
		if(omg.indexOf("&#822;",0)!=-1)
		{
			//System.out.println("before: "+omgaEnty);
			//while(omagaEntity > -1)
			while(omagaEntity != -1)
			{
				//omagaEntity= omg.indexOf("&#822;",omagaEntity+1);
				//omagaEntity= omg.indexOf("&#822;",omagaEntity);
				if(omagaEntity==-1)
				{
					break;
				}

				omagaEntity= omg.indexOf("&#822;");
				if(omagaEntity != -1)
				{
					String temp=omg.substring(0,omagaEntity);
					//System.out.println("temp : "+temp);
					int lst= temp.lastIndexOf(">");
					//System.out.println("lst : "+lst);
					if(lst != -1)
					{
						String First= omg.substring(0,lst+1);
						//System.out.println("First : "+First);
						String label=temp.substring(lst+1,temp.length());
						//System.out.println("label : "+label);
						String last=omg.substring(omagaEntity+"&#822;".length(),omg.length());
						//System.out.println("last : "+last);
						omg=First+"\\Barchar{"+label.trim()+"}"+last;
					}
				}
				
				//System.in.read();

			}
			//omg=return_omt;
		}
		else
		{
			//return_omt=omg;
		}
		//System.out.println("After: "+return_omt);
		//System.in.read();
		return omg;
	}


public String changeBoldSanSarif(String st)throws IOException
	{
		//StringBuffer changeBold=new StringBuffer(st);
		String changeBold= st;
		int spos=0;
		int epos=0;
		spos=changeBold.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>",spos);
		while(spos!=-1)
		{
			spos=changeBold.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>",epos);
			if(spos!= -1)
			{
				//[^<]
				//changeBold=changeBold.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>([A-Za-z]+)</MML:MTEXT></MML:MSTYLE>","<BF>{\\\\sf{\\\\bfseries{$1}}}</BF>");
				changeBold=changeBold.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>([^<]+)</MML:MTEXT></MML:MSTYLE>","<BF>{\\\\sf{\\\\bfseries{$1}}}</BF>");
			}
		}
		//System.out.println("changeBold == > "+changeBold);
		//System.in.read();
		//return changeBold.toString();
		return changeBold;
	}

	private String chme(String str,String ftopen,String rtopen,String ftclose,String rtclose)
	{
		//added by mukesh on 15-01-09
		//for proper replacement of "}}}</MS>"
		int ind=str.indexOf(ftopen);
		String s1="";
		while(ind!=-1)
		{
			int ind1=str.indexOf(ftclose,ind);
			//System.out.println(ind1+" >>>>>");
			s1=str.substring(0,ind)+
			   rtopen+
			   str.substring(ind+ftopen.length(),ind1)+
			   rtclose+
			   str.substring(ind1+ftclose.length())
			   ;
			 //System.out.println("===== "+s1);
			 str=s1;
			 ind=str.indexOf(ftopen);
		}
		
		return str;
	}

	int mmlcount = 0;
	public String processMML()throws IOException
	{
		mmlcount++;
		String tag= "";
		boolean isboldscript=false; 
		//////To implement displaystyle for integral sign in matrix////////
		boolean isMatrix=false;
		String temp_math="";
		xmlObj.mathFlag= true;
		StringBuffer eqStr= new StringBuffer();
		StringBuffer eqStr_temp= new StringBuffer();
		while (!tag.equals("</MML:MATH>"))
		{
			char ch= (char)xmlObj.fin.read();
			if (ch=='<')
			{
				//tag= xmlObj.getTag().toUpperCase();
				temp_math= xmlObj.getTag();
				tag= temp_math.toUpperCase();
				if(tag.equals("</MML:MATH>"))
					break;
				else if(tag.equals("<CE:INLINE-FIGURE>"))
				{	
					eqStr.append(xmlObj.processInlineFigure());	
					eqStr_temp.append(eqStr.toString());
				}
				else
				{
					eqStr.append(tag);
					eqStr_temp.append(temp_math);
				}
			}
			else
			{
				eqStr.append(ch);
				eqStr_temp.append(ch);
			}
		}
//		System.out.println("eqStr -- ["+mmlcount+"] --"+eqStr);

		
		String tmpSubStr= eqStr.toString();
		String tmpSubStr_temp= eqStr_temp.toString();
//System.out.println("tmpSubStr_temp --"+tmpSubStr_temp);
tmpSubStr_temp=xmlObj.replaceStr(tmpSubStr_temp,"<mml:mfenced>","<mml:mfenced open=\"\\(\" close=\"\\)\">");//Added on 11-08-2018
tmpSubStr_temp=xmlObj.replaceStr(tmpSubStr_temp,"<mml:mfenced open=\"&Verbar;\" close=\"&Verbar;\">","<mml:mfenced open=\"&BIGVERBAR;\" close=\"&BIGVERBAR;\">");
tmpSubStr_temp=xmlObj.replaceStr(tmpSubStr_temp,"<mml:mfenced open=\"&verbar;\" close=\"&verbar;\">","<mml:mfenced open=\"&SMALLVERBAR;\" close=\"&SMALLVERBAR;\">");
eqStr=new StringBuffer();
for(int i=0;i<tmpSubStr_temp.length();i++)
{
	//System.out.println("tmpSubStr_temp --"+tmpSubStr_temp);
		
	
	if(tmpSubStr_temp.charAt(i)=='<')
	{
		int ii=i;
		char c=Character.toUpperCase(tmpSubStr_temp.charAt(i));
		//eqStr.append(c);
		while(tmpSubStr_temp.charAt(ii)!='>')
		{
			c=Character.toUpperCase(tmpSubStr_temp.charAt(ii));
			eqStr.append(c);
		ii++;
		}
		eqStr.append('>');
		i=ii;
		
	}
	else
	{
		eqStr.append(tmpSubStr_temp.charAt(i)+"");
	}
}
tmpSubStr= eqStr.toString();

//System.out.println("xt.TEESXml----------->>"+xt.isTEESXml);
//System.out.println("xmlObj.jid----------->>"+xmlObj.jid);
//System.out.println("xmlObj.aid----------->>"+xmlObj.aid);
try
{
	if(xt.isTEESXml==false)//In Live
	//if(xt.isTEESXml==true)
	{
			//System.out.println("In Side from TeX Route Article");
			/***********************[04-08-2011]******************************/

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MATH ALTIMG=\"SI([0-9]+).GIF\">", "<MML:MATH ALTIMG=\"SI$1.GIF\"><MML:MROW>");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT></MML:MROW></MML:MROW>", "</MML:MROW></MML:MATH>");

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFRAC>", "<MML:MROW><MML:MFRAC>");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MFRAC>", "</MML:MFRAC></MML:MROW>");

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW><MML:MROW><MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\">", "<MML:MROW><MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\">");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSTYLE></MML:MROW></MML:MROW>", "</MML:MSTYLE></MML:MROW>");

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFENCED OPEN=\"&LANGLE;\" CLOSE=\"&RANGLE;\"><MML:MROW><MML:MROW>", "<MML:MFENCED OPEN=\"&LANGLE;\" CLOSE=\"&RANGLE;\"><MML:MROW>");//block on 16-12-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUB></MML:MROW></MML:MFENCED></MML:MROW>", "</MML:MSUB></MML:MFENCED></MML:MROW>");//block on 16-12-2011

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTD COLUMNALIGN=\"([A-Z a-z]+)\">", "<MML:MTD COLUMNALIGN=\"$1\"><MML:MROW>");//26-03-2012//blocked
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTD>", "</MML:MROW></MML:MTD>");//26-03-2012//blocked

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFENCED OPEN=\"&([A-Z a-z]+);\" CLOSE=\"&([A-Z a-z]+);\">", "<MML:MTEXT><MML:MFENCED OPEN=\"&$1;\" CLOSE=\"&$2;\">");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTD>", "</MML:MROW></MML:MTD>");
			//System.out.println("In Side from TeX Route Article==>>"+tmpSubStr);
//
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW><MML:MROW><MML:MTEXT>", "<MML:MTEXT>");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT></MML:MROW></MML:MROW>", "</MML:MTEXT>");

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSUB>", "<MML:MROW><MML:MSUB>");//02-09-2011//block on 16-12-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUB>", "</MML:MSUB></MML:MROW>");//02-09-2011//block on 16-12-2011

			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW><MML:MROW><MML:MSUB>", "<MML:MROW><MML:MSUB>");//02-09-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUB></MML:MROW></MML:MROW>", "</MML:MSUB></MML:MROW>");//02-09-2011

			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSUP>", "<MML:MROW><MML:MSUP>");//02-09-2011
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUP>", "</MML:MSUP></MML:MROW>");//02-09-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW><MML:MROW><MML:MSUP>", "<MML:MROW><MML:MSUP>");//02-09-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUP></MML:MROW></MML:MROW>", "</MML:MSUP></MML:MROW>");//02-09-2011
			
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFENCED OPEN=\"(\" CLOSE=\")\">", "<MML:MROW><MML:MFENCED OPEN=\"(\" CLOSE=\")\">");//02-09-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MFENCED>", "</MML:MFENCED></MML:MROW>");//02-09-2011


			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSUBSUP>", "<MML:MROW><MML:MSUBSUP>");//21-09-2011
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MSUBSUP>", "</MML:MSUBSUP></MML:MROW>");//21-09-2011

			/*tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>", "<MML:MROW><MML:MI>");//21-09-2011
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MI>", "</MML:MI></MML:MROW>");//21-09-2011
			*/
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTD>", "<MML:MTD><MML:MROW>");//22-09-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTD>", "</MML:MROW></MML:MTD>");//22-09-2011
			

	//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"NORMAL\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MROW><MML:MTEXT>$1</MML:MTEXT></MML:MROW>");
	//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"ITALIC\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MI>{$1}</MML:MI></MML:MSTYLE>");//01-09-2011
	//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"FRAKTUR\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\frak{$1}}</MML:MI>");
	//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SANS-SERIF\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MSTYLE MATHVARIANT=\"SANS-SERIF\"><MML:MI>{$1}</MML:MI></MML:MSTYLE>");//21-09-2011
		/***********************[04-08-2011]******************************/
	}
	
	
	Pattern p1 = Pattern.compile("<MML:MSTYLE MATHVARIANT=\"ITALIC\">(.*?(?=</MML:MSTYLE>))",Pattern.DOTALL);
//	Pattern p1 = Pattern.compile("<MML:MSTYLE MATHVARIANT=\"ITALIC\">(.*?)</MML:MSTYLE>",Pattern.DOTALL);
	Matcher m1 = p1.matcher(tmpSubStr);
	while(m1.find()){
		String mstyleItalic = m1.group(0);
		String mstyleItalicOrg = m1.group(0);
		System.out.println("MSTYLE ::1:: "+mstyleItalic);
		mstyleItalic = mstyleItalic.replaceAll("<MML:MTEXT>([^<>]+)</MML:MTEXT>", "<MML:MI>$1</MML:MI>");
		System.out.println("MSTYLE ::2:: "+mstyleItalic);
		tmpSubStr = replaceString(tmpSubStr, mstyleItalicOrg, mstyleItalic);
	}

//DTD 5.6.0 updates on 29-04-2019	
	tmpSubStr=tmpSubStr.replaceAll("<MML:MGLYPH ([^<>]*)SRC=\"([^\"]+)\"([^<>]*)>","{\\\\epsfbox{$1.eps}}");

	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAK=\"NEWLINE\">","\\\\linebreak<MML:MO>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MN LINEBREAK=\"NEWLINE\">","\\\\linebreak<MML:MN>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT LINEBREAK=\"NEWLINE\">","\\\\linebreak<MML:MTEXT>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE LINEBREAK=\"NEWLINE\">","\\\\linebreak<MML:MSPACE>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE LINEBREAK=\"NEWLINE\">","\\\\linebreak<MML:MSTYLE>");
	
	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAK=\"NOBREAK\">","\\\\nolinebreak<MML:MO>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MN LINEBREAK=\"NOBREAK\">","\\\\\nolinebreak<MML:MN>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT LINEBREAK=\"NOBREAK\">","\\\\nolinebreak<MML:MTEXT>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE LINEBREAK=\"NOBREAK\">","\\\\nolinebreak<MML:MSPACE>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE LINEBREAK=\"NOBREAK\">","\\\\nolinebreak<MML:MSTYLE>");
	
	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAK=\"GOODBREAK\" LINEBREAKSTYLE=\"AFTER\">([^<>]+)</MML:MO>","<MML:MO>$1</MML:MO>\\\\goodbreak ");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAK=\"GOODBREAK\">","\\\\goodbreak<MML:MO>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MN LINEBREAK=\"GOODBREAK\">","\\\\goodbreak<MML:MN>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT LINEBREAK=\"GOODBREAK\">","\\\\goodbreak<MML:MTEXT>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE LINEBREAK=\"GOODBREAK\">","\\\\goodbreak<MML:MSPACE>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE LINEBREAK=\"GOODBREAK\">","\\\\goodbreak<MML:MSTYLE>");
		
	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAK=\"BADBREAK\">","\\\\badbreak<MML:MO>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MN LINEBREAK=\"BADBREAK\">","\\\\badbreak<MML:MN>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT LINEBREAK=\"BADBREAK\">","\\\\badbreak<MML:MTEXT>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE LINEBREAK=\"BADBREAK\">","\\\\badbreak<MML:MSPACE>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE LINEBREAK=\"BADBREAK\">","\\\\badbreak<MML:MSTYLE>");
	tmpSubStr=tmpSubStr.replaceAll("<MML:MO LINEBREAKSTYLE=\"AFTER\">([^<>]+)</MML:MO>","<MML:MO>$1</MML:MO>\\\\linebreak ");
//Till here
	
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MN>([0-9]+)</MML:MN></MML:MSTYLE>","<MML:MI>{\\\\textit{$1}}</MML:MI>");//22-02-2013
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"NORMAL\">([0-9]+)</MML:MN>","<MML:MN>$1</MML:MN>");//15-03-2013
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"BOLD\">([0-9]+)</MML:MN>","<MML:MN>\\\\rmbox{\\\\bf{$1}}</MML:MN>");//17-01-2014
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO MATHVARIANT=\"BOLD\">\\.</MML:MO>","<MML:MO>\\\\rmbox{\\\\bf{\\.}}</MML:MO>");//19-06-2015
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO MATHVARIANT=\"BOLD\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\.</MML:MO>","<MML:MO>\\\\rmbox{\\\\bf{\\\\Diff{$1}\\.}}</MML:MO>");//19-06-2015
	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO MATHVARIANT=\"BOLD-ITALIC\">([^<>]+)</MML:MO>","<MML:MO>\\\\bm{$1}</MML:MO>");//02-09-2016
}
catch (Exception e)
{
	e.printStackTrace();
	System.out.println("[ERROR]: In Getting LaTeX Information..System now exit.");
	System.exit(0);
}


///////////////////////////////////////////////////////////////////////////////
//System.out.println("FFFFFFIIIIIIIILLLLLLLLLEEEEEEEE::::::"+tmpSubStr);

/////////////// XML FILTERATION ADDED ON 21-09-2015 //////////////////
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW >", "<MML:MROW>");
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI >", "<MML:MI>");
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN >", "<MML:MN>");
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT >", "<MML:MTEXT>");
///////////////////////////////////////////////////////////////////////

tmpSubStr=tmpSubStr.replaceAll("<MML:MI MATHVARIANT=\"ITALIC\">([^<>]+)</MML:MI>","<MML:MI>$1</MML:MI>");//As the MML:MI is default italic, no need of attribute italic//[14-01-2014]
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>$1</MML:MI>");//As the MML:MI is default italic, no need of attribute italic//[21-01-2014]
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\bm{$1}}</MML:MI>");//Added new line on 03-04-2014
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\bm{$1}}</MML:MI>");//Added new line on 10-03-2014
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MTEXT>([^<>]+)</MML:MTEXT></MML:MSTYLE>","<MML:MTEXT>{\\\\bi{$1}}</MML:MTEXT>");//Added new line on 10-03-2014
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"FRAKTUR\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\frak{$1}}</MML:MI>");//Added new line on 04-08-2016
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-FRAKTUR\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\frakb{$1}}</MML:MI>");//Added new line on 04-08-2016

//	tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT><CE:INLINE-FIGURE><CE:LINK LOCATOR=\"([^\"]+)\" ([^<>]+)/></CE:INLINE-FIGURE></MML:MTEXT>", "<MML:MTEXT>{%\\epsfbox{$1.eps}}</MML:MTEXT>");

//**********[27-11-2012] [Modification due to TD-XPS Changes in XML (Start)]*************************************
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE/>", "");
//**********[27-11-2012] [Modification due to TD-XPS Changes in XML (End)]*************************************

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"NORMAL\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MROW><MML:MTEXT>$1</MML:MTEXT></MML:MROW>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"NORMAL\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MROW><MML:MTEXT>\\\\Diff{$1}$2</MML:MTEXT></MML:MROW>");
		
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"ITALIC\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MI>{$1}</MML:MI></MML:MSTYLE>");//01-09-2011
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SANS-SERIF\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MSTYLE MATHVARIANT=\"SANS-SERIF\"><MML:MI>{$1}</MML:MI></MML:MSTYLE>");//21-09-2011
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SANS-SERIF\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\sf{$1}}</MML:MI>");//10-07-2015
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SANS-SERIF\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\sf{\\\\Diff{$1}$2}}</MML:MI>");//10-07-2015

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"SANS-SERIF\">([0-9]+)</MML:MN>", "<MML:MN>{\\\\sf{$1}}</MML:MN>");//Added on 05-08-2019
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"SANS-SERIF\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([0-9]+)</MML:MN>", "<MML:MN>{\\\\sf{\\\\Diff{$1}$2}}</MML:MN>");//Added on 05-08-2019
		
		
//System.out.println("tmpSubStr --"+tmpSubStr);
//System.in.read();
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFRAC BEVELLED=\"TRUE\">(.*?)</MML:MFRAC>", "<MML:BEVELLED>$1</MML:BEVELLED>");//09/12/2009
tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFRAC LINETHICKNESS=\"0\">","<MML:MFRAC>");//05/06/2014

//********************[handled Italic Vee in Guliver Model]********************
//System.out.println("---------> "+xmlObj.check_GulliverJid);
//System.in.read();
if(xmlObj.check_GulliverJid==true){
//tmpSubStr=tmpSubStr.replaceAll("<MML:MI>v</MML:MI>","<MML:MI>{\\\\GulliverItalicvee}</MML:MI>");
//System.out.println("---------> "+tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>v</MML:MI></MML:MSTYLE>"));
//tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>v</MML:MI></MML:MSTYLE>","<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>{\\\\GulliverBoldItalicvee}</MML:MI></MML:MSTYLE>");//Commented on 10-01-2014
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>v</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\GulliverBoldItalicvee}</MML:MI>");//Changed on 10-01-2014
tmpSubStr=tmpSubStr.replaceAll("<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\bm{$1}}</MML:MI>");//Added new line on 10-01-2014

//added by mukesh on 10-11-08. (TPMS)
tmpSubStr=tmpSubStr.replaceAll("</MML:MI><MML:MI>v</MML:MI><MML:MI>","</MML:MI><MML:MI1>v</MML:MI1><MML:MI>");
tmpSubStr=tmpSubStr.replaceAll("<MML:MI>v</MML:MI>","<MML:MI>{\\\\GulliverItalicvee}</MML:MI>");
tmpSubStr=tmpSubStr.replaceAll("</MML:MI><MML:MI1>v</MML:MI1><MML:MI>","</MML:MI><MML:MI>v</MML:MI><MML:MI>");
}
//******************************************

		//<mml:mspace width="0.12em"/>
		//06/11/2007
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.12EM\"/>","");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\".5EM\"/>","{\\\\enspace}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.5EM\"/>","{\\\\enspace}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\".25EM\"/>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\".1EM\"/>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\".1EM\" />","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.25EM\"/>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.25EM\"></MML:MSPACE>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"1EM\"></MML:MSPACE>","\\\\quad ");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"2EM\"></MML:MSPACE>","\\\\quad\\\\quad ");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.3EM\" HEIGHT=\"0.0EX\" DEPTH=\"0.0EX\"/>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.10EM\"/>","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.5EM\"/>","{\\\\enspace}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"0.25EM\" />","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.4EM\" />","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.20EM\" />","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"([0-9]+)\\.([0-9]+)EM\" HEIGHT=\"0.0EX\" DEPTH=\"0.0EX\"/>","{\\\\hspace*{$1.$2em}}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"1EM\"/>","{\\\\quad}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"1EM\" />","{\\\\quad}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"2EM\"/>","{\\\\qquad}");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"2EM\" />","{\\\\qquad}");
		
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"([^\"])\" HEIGHT=\"([^\"])\" WIDTH=\"0.1EM\"/>","\\\\,");//Added on 05-08-2019
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"([^\"])\" HEIGHT=\"([^\"])\" WIDTH=\"0.2EM\"/>","\\\\,");//Added on 05-08-2019
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE DEPTH=\"([^\"])\" HEIGHT=\"([^\"])\" WIDTH=\"0.3EM\"/>","\\\\,");//Added on 05-08-2019
		
		tmpSubStr=tmpSubStr.replaceAll("<MML:MROW />","<MML:MROW></MML:MROW>");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MROW/>","<MML:MROW></MML:MROW>");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSQRT>","<MML:MSQRT><MML:MROW>");
		tmpSubStr=tmpSubStr.replaceAll("</MML:MSQRT>","</MML:MROW></MML:MSQRT>");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"THINMATHSPACE\" />","\\\\,");
		tmpSubStr=tmpSubStr.replaceAll("<MML:MSPACE WIDTH=\"THINMATHSPACE\"/>","\\\\,");
		
	
		/**
		*Added :By Ravi
		*Date  :[05/07/2007] 
		*Change Request Point : handling  \\Barchar{ in xt
		*Change Request By : TPMS application
		 
		*/
		tmpSubStr= OmagaEntityConverter(tmpSubStr);
		//end
		//System.out.println(tmpSubStr);
		//System.in.read();
		
		/**
		*this Function is not required
		*/ 
			//	tmpSubStr=MmlAndMOWithRmbox(tmpSubStr);
		//end

		/** [Added By Ravi 18/01/2007]
		* Change Point:" from <mml:mi mathvariant="normal">d</mml:mi>" to "<mml:text>d</mml:text>" 
		* Change Request by Subrata
		*/
			tmpSubStr=tmpSubStr.replaceAll("<MML:MI MATHVARIANT=\"NORMAL\">d</MML:MI>","<MML:MTEXT>d</MML:MTEXT>");
			if(tmpSubStr.indexOf("<MML:MO MATHVARIANT=\"NORMAL\">")!=-1)
				System.out.println("Removing MATHVARIANT=\"NORMAL\" from <MML:MO>");
			tmpSubStr=tmpSubStr.replaceAll("<MML:MO MATHVARIANT=\"NORMAL\">([^<>]+)</MML:MO>","<MML:MO>$1</MML:MO>");//02-09-2018
		//end


//		tmpSubStr= xmlObj.replaceStr(tmpSubStr,
		//		"<MML:MPRESCRIPTS/><MML:NONE/>","");
		//added by avi
		
		// [Added By Ravi 27/12/2006]
		//Change Point:" from <MML:MTEXT>&thinsp;</MML:MTEXT>" to "<MML:MI>&thinsp;</MML:MI>" 
			tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT>&thinsp;</MML:MTEXT>","<MML:MI>&thinsp;</MML:MI>");
		
		
		//<mml:mo>^</mml:mo>
		//[21/08/2007]
		if(tmpSubStr.indexOf("<MML:MO>^</MML:MO>",0)!=-1)
		{
			//tmpSubStr= processMonoSpace(tmpSubStr);
			//System.out.println("tmpSubStr "+tmpSubStr);
			//System.in.read();
			tmpSubStr=tmpSubStr.replaceAll("<MML:MO>\\^</MML:MO>","<MML:MO>\\\\hat{}</MML:MO>");
		}
		//end	
		
		//end
		while (tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">")!=-1)
		{
			tmpSubStr= processMonoSpace(tmpSubStr);
		}
		//end mark

//**************************

	if(tmpSubStr.indexOf("<MML:MSPACE CLASS=\"NBSP\" />",0)!=-1)
		{
			//System.out.println(tmpSubStr);
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE CLASS=\"NBSP\" />", "\\\\;");
			//System.out.println(tmpSubStr);
			//System.in.read();
		}
//**************************

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT>([\\.,;])</MML:MTEXT>", "<MML:MO>$1</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO SYMMETRIC=\"TRUE\" STRETCHY=\"TRUE\" LARGEOP=\"TRUE\">", "<MML:MO STRETCHY=\"TRUE\">");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace", "<MML:MO STRETCHY=\"TRUE\">");//13-08-2015 Feedback on ECOMOD-7575 (text missing due to diff tag in EQ5)
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO ([^<>]*)STRETCHY=\"TRUE\"([^<>]*)>&macr;</MML:MO>", "<MML:MO $1STRETCHY=\"TRUE\"$2>&OverBar;</MML:MO>");//Updated on 10-10-2015 Feedback received (&macr; not cover the whole text in case of stretchy="true")
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">&OverBar;</MML:MO>", "<MML:MO>&OverBar;</MML:MO>");//Updated on 10-10-2015 Feedback received (&macr; not cover the whole text in case of stretchy="true")
		
		//tmpSubStr=tmpSubStr.replaceAll("<MML:MO STRETCHY=\"TRUE\"><!--<DIFF ID=\"([0-9]+)\"/>-->","<MML:MO STRETCHY=\"TRUE\">");
		//////////////////////////////<MML:MO STRETCHY=\"TRUE\"> added on 19-02-2015 for TDXPS XML
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\[</MML:MO>", "\\\\left\\[");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\]</MML:MO>", "\\\\right\\]");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\(</MML:MO>", "\\\\left\\(");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\)</MML:MO>", "\\\\right\\)");
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\|</MML:MO>", "\\\\left\\|");//Added on 23-08-2017
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\|</MML:MO>", "\\\\right\\|");//Added on 23-08-2017
		//////////////////////////////<MML:MO STRETCHY=\"TRUE\"> added on 13-03-2015 for TDXPS XML
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\{</MML:MO>","\\\\Diff\\{$1\\}\\\\left\\\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\}</MML:MO>","\\\\Diff\\{$1\\}\\\\right\\\\\\}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\{</MML:MO>","\\\\left\\\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">\\}</MML:MO>","\\\\right\\\\\\}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\{</MML:MO>", "\\\\Diff\\{$1\\}\\\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\}</MML:MO>", "\\\\Diff\\{$1\\}\\\\\\}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\{</MML:MO>", "\\\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\}</MML:MO>", "\\\\\\}");
		//////////////////////////////////////////////////////////////////////////////////////////////////////
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>([^<]+)</MML:MI><MML:MO>&ApplyFunction;</MML:MO>", "<MML:MO>$1</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"FALSE\">", "<MML:MO>");
		//added by avinandan
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\_", "<MML:MO>\\\\_");
		//end mark
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\{", "<MML:MO>ThomsonDiff\\_ThomsonDiffOpenBrace$1ThomsonDiffCloseBrace\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff\\_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\}", "<MML:MO>ThomsonDiff\\_ThomsonDiffOpenBrace$1ThomsonDiffCloseBrace\\\\}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\{", "<MML:MO>\\\\{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\}", "<MML:MO>\\\\}");


		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MI>max</MML:MI></MML:MSTYLE>", "<MML:MO>max </MML:MO>");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>log</MML:MI>", "<MML:MO>\\\\log </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracelog</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\log </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>cos</MML:MI>", "<MML:MO>\\\\cos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecos</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\cos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>cot</MML:MI>", "<MML:MO>\\\\cot </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecot</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\cot </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>coth</MML:MI>", "<MML:MO>\\\\coth </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecoth</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\coth </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>sin</MML:MI>", "<MML:MO>\\\\sin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesin</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\sin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>det</MML:MI>", "<MML:MO>\\\\det </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracedet</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\det </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>cosh</MML:MI>", "<MML:MO>\\\\cosh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecosh</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\cosh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>sinh</MML:MI>", "<MML:MO>\\\\sinh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesinh</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\sinh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>tan</MML:MI>", "<MML:MO>\\\\tan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracetan</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\tan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>tanh</MML:MI>", "<MML:MO>\\\\tanh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracetanh</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\tanh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>arccos</MML:MI>", "<MML:MO>\\\\arccos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearccos</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\arccos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>arcsin</MML:MI>", "<MML:MO>\\\\arcsin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearcsin</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\arcsin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>arctan</MML:MI>", "<MML:MO>\\\\arctan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearctan</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\arctan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>max</MML:MI>", "<MML:MO>\\\\max </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemax</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\max </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>min</MML:MI>", "<MML:MO>\\\\min </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemin</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\min </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>int</MML:MI>", "<MML:MO>{\\\\rm{int}}\\\\,</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceint</MML:MI>", "<MML:MO>\\\\Diff{$1}{\\\\rm{int}}\\\\,</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>mod</MML:MI>", "<MML:MO>{\\\\rm{mod}}\\\\,</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemod</MML:MI>", "<MML:MO>\\\\Diff{$1}{\\\\rm{mod}}\\\\,</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>sec</MML:MI>", "<MML:MO>\\\\sec </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesec</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\sec </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>lim</MML:MI>", "<MML:MO>\\\\lim </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracelim</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\lim </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>sup</MML:MI>", "<MML:MO>\\\\sup </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesup</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\sup </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>inf</MML:MI>", "<MML:MO>\\\\inf </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceinf</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\inf </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ln</MML:MI>", "<MML:MO>\\\\ln </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceln</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\ln </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>exp</MML:MI>", "<MML:MO>\\\\exp </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceexp</MML:MI>", "<MML:MO>\\\\Diff{$1}\\\\exp </MML:MO>");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>log</MML:MO>", "<MML:MO>\\\\log </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracelog</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\log </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>cos</MML:MO>", "<MML:MO>\\\\cos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecos</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\cos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>cot</MML:MO>", "<MML:MO>\\\\cot </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecot</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\cot </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>coth</MML:MO>", "<MML:MO>\\\\coth </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecoth</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\coth </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>sin</MML:MO>", "<MML:MO>\\\\sin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesin</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\sin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>det</MML:MO>", "<MML:MO>\\\\det </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracedet</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\det </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>cosh</MML:MO>", "<MML:MO>\\\\cosh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracecosh</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\cosh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>sinh</MML:MO>", "<MML:MO>\\\\sinh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesinh</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\sinh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>tan</MML:MO>", "<MML:MO>\\\\tan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracetan</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\tan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>tanh</MML:MO>", "<MML:MO>\\\\tanh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracetanh</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\tanh </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>arccos</MML:MO>", "<MML:MO>\\\\arccos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearccos</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\arccos </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>arcsin</MML:MO>", "<MML:MO>\\\\arcsin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearcsin</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\arcsin </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>arctan</MML:MO>", "<MML:MO>\\\\arctan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracearctan</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\arctan </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>max</MML:MO>", "<MML:MO>\\\\max </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemax</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\max </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>min</MML:MO>", "<MML:MO>\\\\min </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemin</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\min </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>int</MML:MO>", "<MML:MO>{\\\\rm{int}}</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceint</MML:MO>", "<MML:MO>\\\\Diff{$1}{\\\\rm{int}}</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>mod</MML:MO>", "<MML:MO>{\\\\rm{mod}}</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracemod</MML:MO>", "<MML:MO>\\\\Diff{$1}{\\\\rm{mod}}</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>sec</MML:MO>", "<MML:MO>\\\\sec </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesec</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\sec </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>lim</MML:MO>", "<MML:MO>\\\\lim </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracelim</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\lim </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>sup</MML:MO>", "<MML:MO>\\\\sup </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBracesup</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\sup </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>inf</MML:MO>", "<MML:MO>\\\\inf </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceinf</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\inf </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ln</MML:MO>", "<MML:MO>\\\\ln </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceln</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\ln </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>exp</MML:MO>", "<MML:MO>\\\\exp </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBraceexp</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\exp </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>#</MML:MO>", "<MML:MO>\\\\#</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace#</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\#</MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>\\\\</MML:MO>", "<MML:MO>\\\\backslash </MML:MO>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\\\</MML:MO>", "<MML:MO>\\\\Diff{$1}\\\\backslash </MML:MO>");
		/////tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>^</MML:MO>", "<MML:MO>\\\\^</MML:MO>");
		//avinandan added till end mark
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"THINMATHSPACE\"/>","\\\\,");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"THINMATHSPACE\" />","\\\\,");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"1EM\"/>", "\\\\quad ");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"1\\.00EM\"/>", "\\\\quad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"0\\.5EM\"/>", "\\\\enspace ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"0\\.25EM\"/>", "\\\\, ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"0.3EM\" HEIGHT=\"0.0EX\" DEPTH=\"0.0EX\"/>", "\\\\,");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.10EM\"/>", "\\\\,");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.5EM\"/>", "{\\\\enspace}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.4EM\" />", "\\\\,");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE DEPTH=\"0.0EX\" HEIGHT=\"0.0EX\" WIDTH=\"0.20EM\" />", "\\\\,");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"1EM\"/>", "\\\\quad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"2EM\"/>", "\\\\qquad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"1EM\" />", "\\\\quad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"2EM\" />", "\\\\qquad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"0.25EM\" />", "\\\\qquad ");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">", "\\\\tt{{");
		//Added on 05-08-2019 for monospace
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT MATHVARIANT=\"MONOSPACE\">([a-zA-Z0-9]+)</MML:MTEXT>", "<MML:MTEXT>{\\\\tt{$1}}</MML:MTEXT>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT MATHVARIANT=\"MONOSPACE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z0-9]+)</MML:MTEXT>", "<MML:MTEXT>\\\\Diff{$1}{\\\\tt{$2}}</MML:MTEXT>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"MONOSPACE\">([a-zA-Z0-9]+)</MML:MI>", "<MML:MI>{\\\\tt{$1}}</MML:MI>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"MONOSPACE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z0-9]+)</MML:MI>", "<MML:MI>\\\\Diff{$1}{\\\\tt{$2}}</MML:MI>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"MONOSPACE\">([0-9]+)</MML:MN>", "<MML:MN>{\\\\tt{$1}}</MML:MN>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"MONOSPACE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([0-9]+)</MML:MN>", "<MML:MN>\\\\Diff{$1}{\\\\tt{$1}}</MML:MN>");//Added on 05-08-2019 to handle \tt in math without mml:mstyle
		//[05-08-2019] Till here
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSPACE WIDTH=\"([0-9]+)\\.([0-9]+)EM\" HEIGHT=\"0.0EX\" DEPTH=\"0.0EX\"/>", "{\\\\hspace*{$1.$2em}}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW />", "<MML:MROW></MML:MROW>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW/>", "<MML:MROW></MML:MROW>");
		
//		System.out.println("tmpSubStr :: "+tmpSubStr);

		
		//end mark
		/**
		*[16/07/2007]
		*Added By Ravi 
		* Change Point : handled <MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>
		* Change Request By: Vivek Through TPMS.
		*/
		
		if(tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD-SCRIPT\"><MML:MI>") != -1)
		{
			
			/*tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD-SCRIPT\"><MML:MI>", "<MS>{\\\\cal{\\\\bfseries{");
			
			System.out.println("tmpSubStr------- l k -"+tmpSubStr);
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MI></MML:MSTYLE>", "}}}</MS>");
			//System.out.println("tmpSubStr------- l k -"+tmpSubStr);
			System.out.println("tmpSubStr------- l k -"+tmpSubStr);*/
			//System.out.println("tmpSubStr------- l k -"+tmpSubStr);
			tmpSubStr=chme(tmpSubStr,"<MML:MSTYLE MATHVARIANT=\"BOLD-SCRIPT\"><MML:MI>","<MS>{\\cal{\\bfseries{","</MML:MI></MML:MSTYLE>","}}}</MS>");
			tmpSubStr=chme(tmpSubStr,"<MML:MSTYLE MATHVARIANT=\"BOLD-SCRIPT\"><MML:MI>","<MS>{\\cal{\\bfseries{","</MML:MI></MML:MSTYLE>","}}}</MS>");
			//System.out.println("tmpSubStr======= l k -"+tmpSubStr);
			//added by mukesh on 15-01-09
			isboldscript=true;
		}
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"SCRIPT\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI>{\\\\cal{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"SCRIPT\"><MML:MI>", "<MS>{\\\\cal{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"FRAKTUR\"><MML:MI>", "<MS>{\\\\frak{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD-FRAKTUR\"><MML:MI>", "<MS>{\\\\frakb{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"SANS-SERIF\"><MML:MI>", "<MS>{\\\\sf{");

//*********************[02/11/2007]*************************************************
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SCRIPT\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "{\\\\cal{$1}}");//29-08-2011
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SCRIPT\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\cal{$1}}</MML:MI>");//29-08-2011
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-SCRIPT\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\calb{$1}}</MML:MI>");//07-09-2018
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SCRIPT\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\Diff{$1}\\\\cal{$2}}</MML:MI>");//23-07-2014
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"FRAKTUR\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\frak{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-FRAKTUR\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\frakb{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"SANS-SERIF\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\sf{$1}}</MML:MI>");
//********************************************************************************
		/**
		*[16/07/2007]
		*Added By Ravi 
		* Change Point : handled <MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>
		* Change Request By: Vivek Through TPMS.
		*/
		if(tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>") != -1)
		{
			//System.out.println("tmpSubStr==> "+tmpSubStr);
			
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD-SANS-SERIF\"><MML:MTEXT>", "<MS_B>{\\\\sf{\\\\bfseries{");
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT></MML:MSTYLE>", "}}}</MS_B>");
			tmpSubStr=changeBoldSanSarif(tmpSubStr);
			//System.out.println("After tmpSubStr==> "+tmpSubStr);
			//System.in.read();
		}
		//end
		if(tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"SANS-SERIF\"><MML:MTEXT>") != -1 || tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"SANS-SERIF-ITALIC\"><MML:MTEXT>") != -1)
		{
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"SANS-SERIF\"><MML:MTEXT>", "<MS>{\\\\sf{");
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"SANS-SERIF-ITALIC\"><MML:MTEXT>", "<MS>{\\\\sf{");
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT></MML:MSTYLE>", "}}</MS>");
		}
	
//==========[17/04/2007]===========>
		
		//Updated on 06-04-2016
		tmpSubStr= tmpSubStr.replaceAll("<MML:MROW><MML:MTEXT>([^<>]+)</MML:MTEXT></MML:MROW>", "<MML:MTEXT>$1</MML:MTEXT>");
		
		if(tmpSubStr.indexOf("<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MTEXT>") != -1)
		{
			//System.out.println("Berfore tmpSubStr==> "+tmpSubStr);
//			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MTEXT>([^<>]+)</MML:MTEXT></MML:MSTYLE>","\\\\rmbox{\\\\bf{$1}}");
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MTEXT>", "<MS>\\\\rmbox{\\\\bf{");
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT></MML:MSTYLE>", "}}</MS>");
			tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT><MML:MO>([^<]+)</MML:MO></MML:MSTYLE>", "}}</MS><MML:MO>$1</MML:MO>");//23-12-2011
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MTEXT>([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MTEXT></MML:MSTYLE>", "<MS>\\\\rmbox{\\\\bf{$1}}</MS>");
			//System.out.println("After tmpSubStr==> "+tmpSubStr);
			//System.in.read();
		}
//=============================>

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MI>", "<MS>{\\\\bm{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MN>", "<MS>{\\\\bm{");//bhavesh 04/08/2006
//*********************[02/11/2007]*************************************************
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\bm{$1}}</MML:MI>");
		//<MML:MI MATHVARIANT=\"BOLD\"> updated for Bold roman text on 18-04-2016
		//APOR_1198 (Output checked in UCTOOL), mail sent by Shashi Shekhar/TeXR&D
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\rmbox{\\\\bf{$1}}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\rmbox{\\\\Diff{$1}\\\\bf{$2}}}</MML:MI>");//11-08-2016

//**************************************************************************************

		//Avinandan Added
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"DOUBLE\\-STRUCK\"><MML:MI>([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI></MML:MSTYLE>", "<MML:MI>{\\\\bb{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"DOUBLE\\-STRUCK\"><MML:MI>([a-zA-Z\\.\\;\\&\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI></MML:MSTYLE>", "<MML:MI>{\\\\bb{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"DOUBLE\\-STRUCK\"><MML:MN>([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MN></MML:MSTYLE>", "<MML:MN>{\\\\bb{$1}}</MML:MN>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"DOUBLE\\-STRUCK\"><MML:MN>([a-zA-Z\\.\\;\\&\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MN></MML:MSTYLE>", "<MML:MN>{\\\\bb{$1}}</MML:MN>");
		//System.out.println("1 "+tmpSubStr);
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MI MATHVARIANT=\"NORMAL\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI></MML:MSTYLE>", "<MML:MI MATHVARIANT=\"NORMAL\">{\\\\bf{$1}}</MML:MI>");
//		System.out.println("\n\n2222222222 "+tmpSubStr);
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MROW><MML:MTEXT>([^<>]+)</MML:MTEXT>","<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MROW><MML:MTEXT>{\\\\bf{$1}}</MML:MTEXT>");//16-08-2016

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD\"><MML:MI MATHVARIANT=\"NORMAL\">([a-zA-Z\\.\\;\\&\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI></MML:MSTYLE>", "<MML:MI MATHVARIANT=\"NORMAL\">{\\\\bm{$1}}</MML:MI>");
		//System.out.println("\n\n3 "+tmpSubStr);
		//System.in.read();

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"BOLD-ITALIC\"><MML:MI>", "<MS>{\\\\bm{");
//***********************************[05/11/2007]********************************************
		//System.out.println("Before "+tmpSubStr);

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"ITALIC\"><MML:MI>", "<MS>{{");
		//System.out.println("After "+tmpSubStr);
		//System.in.read();
//****************************************************************************************
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"NORMAL\"><MML:MI>([^<>]+)</MML:MI></MML:MSTYLE>","<MML:MI MATHVARIANT=\"NORMAL\">$1</MML:MI>");//15-02-2014
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"NORMAL\"><MML:MI>", "<MS>{\\\\rm{");
		//*********************[02/11/2007]*************************************************
//		System.out.println("tmpSubStr===>"+tmpSubStr);

//		Commented on 29-12-2015
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-ITALIC\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "{\\\\bm{$1}}");
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-ITALIC\">([^<>]+)</MML:MI>", "{\\\\bm{$1}}");//19-11-2015
//		open tag <MML:MI> and closing tag </MML:MI> added on 29-12-2015 		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-ITALIC\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\bm{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"BOLD-ITALIC\">([^<>]+)</MML:MI>", "<MML:MI>{\\\\bm{$1}}</MML:MI>");//19-11-2015
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"ITALIC\">([^<>]+)</MML:MN>", "<MML:MN>{\\\\textit{$1}}</MML:MN>");//14-11-2016
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN MATHVARIANT=\"BOLD-ITALIC\">([^<>]+)</MML:MN>", "<MML:MN>{\\\\bm{$1}}</MML:MN>");//14-11-2016

		//System.out.println("before "+tmpSubStr);
		
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"NORMAL\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "{\\\\rm{$1}}");//blockecd for using group text in MML:MI  04-08-2011
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"DOUBLE-STRUCK\">([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\bb{$1}}</MML:MI>");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI MATHVARIANT=\"DOUBLE-STRUCK\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MI>", "<MML:MI>{\\\\bb{\\\\Diff{$1}$2}}</MML:MI>");
		//**************************************************************************************
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MSTYLE MATHVARIANT=\"DOUBLE-STRUCK\"><MML:MI>", "<MS>{\\\\bb{");

		System.out.println("tmpSubStr [::] "+tmpSubStr);
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MOVER ACCENT=\"TRUE\"><MML:MI>([a-z]+)</MML:MI><MML:MO>&sim;</MML:MO></MML:MOVER>","\\\\tilde{$1}");
		
		/*tmpSubStr= xmlObj.replaceStr(tmpSubStr, "MML:MSTYLE DISPLAYSTYLE=\"TRUE\">*/
		/*
		if(isboldscript==true)
		{
			isboldscript=false;
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MI></MML:MSTYLE>", "}}}</MS>");	
		}
		*/

		//Below line commented due to problem in this condition :: <mml:munder><mml:mstyle displaystyle="true"><mml:mi>&Sigma;</mml:mi></mml:mstyle><mml:mi>d</mml:mi></mml:munder>
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MI></MML:MSTYLE>", "}}</MS>");//Commented on 23-12-2013
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MN></MML:MSTYLE>", "}}</MS>");// bhavesh 04/08/2006
		
		//	System.out.println("eqStr:::"+eqStr);
		//commented by avinanda till end mark
		/*if (tmpSubStr.indexOf("<MML:MUNDER ACCENTUNDER=\"TRUE\">")!=-1)
		{
			tmpSubStr= processAccentUnder(tmpSubStr);
			}*/
		//end mark
		//added by avinandan tiil end mark

		
		//System.out.println("========>>"+tmpSubStr);
		//System.in.read();
		while (tmpSubStr.indexOf("<MML:MFRAC LINETHICKNESS=\"0\">")!=-1)
		{
			
			//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFRAC LINETHICKNESS=\"0\">", "<MML:MFRAC>");
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MFRAC");
		}


		while (tmpSubStr.indexOf("<MML:MUNDER ACCENTUNDER=\"TRUE\">")!=-1)
		{
			tmpSubStr= processAccentUnder(tmpSubStr);
		}
		//end mark
	
//Added By Ravi [28/11/2006] for Addeing  mml:mroe before MML:MMULTISCRIPTS
	if(tmpSubStr.indexOf("<MML:MMULTISCRIPTS>") != -1)
		{
				//tmpSubStr= Adding_MmlRow_Brfore_MmlMmultiscripts(tmpSubStr);
		}
		//System.out.println(""+tmpSubStr);///rajeev
		//System.in.read();
		while (tmpSubStr.indexOf("<MML:MMULTISCRIPTS>")!=-1)
		{
			tmpSubStr= processMultiscript(tmpSubStr);
		}

		while(tmpSubStr.indexOf("<MML:MOVER ACCENT=\"TRUE\">")!=-1)
		{
			//System.out.println(tmpSubStr);///rajeev
			tmpSubStr= processAccentOver(tmpSubStr);
			//System.out.println(tmpSubStr);///rajeev
			//System.in.read();
		}
//System.out.println("1 "+tmpSubStr);
		while (tmpSubStr.indexOf("<MML:MSUB>")!=-1)
		{
//			System.out.println("temSubStr:"+tmpSubStr);
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MSUB");
		}
//System.out.println("2 "+tmpSubStr);
//System.out.println("After "+tmpSubStr);
//System.in.read();
		while (tmpSubStr.indexOf("<MML:MSUP>")!=-1)
		{
			//tmpSubStr= processMMLObjects(tmpSubStr, "MML:MSUP");
				
				tmpSubStr= processMMLObjects(tmpSubStr, "MML:MSUP");
				//08/12/2009
		}
		//System.out.println("3 "+tmpSubStr);
		/*
		while (tmpSubStr.indexOf("<MML:MI MATHVARIANT=\"NORMAL\"\>")!=-1)
		{
			String processTemp = new String();
			int index = tmpSubStr.indexOf("<MML:MI MATHVARIANT=\"NORMAL\"\>");
			processTemp =tmpSubStr.substring(0,index)+"<MML:MI>\\{\\rm ";

		}*/
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MFRAC LINETHICKNESS=\"0\">", "<MML:MFRACM>");
		
		while (tmpSubStr.indexOf("<MML:MFRAC>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MFRAC");
		}
		//System.out.println("4 "+tmpSubStr);
		while (tmpSubStr.indexOf("<MML:BEVELLED>")!=-1)//09/12/2009
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:BEVELLED");
		}
		//System.out.println("5 "+tmpSubStr);
		while (tmpSubStr.indexOf("<MML:MFRAC BEVELLED=\"TRUE\">")!=-1)
		{
			//System.out.println("\n::::"+tmpSubStr+"::::");
			//mfracbevelled=true;
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MFRAC");
			//mfracbevelled=false;
		}
		while (tmpSubStr.indexOf("<MML:MSQRT>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MSQRT");
			//System.out.println("---111------"+tmpSubStr);
		}
		while (tmpSubStr.indexOf("<MML:MROOT>")!=-1)
		{
//					System.out.println(tmpSubStr+"*******************");
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MROOT");
/*			System.out.println(tmpSubStr);
			System.exit(0);*/
		}
		while (tmpSubStr.indexOf("<MML:MSUBSUP>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MSUBSUP");
		}
		//System.out.println(tmpSubStr);//[20/12/2006]
		//System.in.read();
		while (tmpSubStr.indexOf("<MML:MOVER>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MOVER");
		}
		while (tmpSubStr.indexOf("<MML:MUNDEROVER>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MUNDEROVER");
		}
		while (tmpSubStr.indexOf("<MML:MUNDER>")!=-1)
		{
			tmpSubStr= processMMLObjects(tmpSubStr, "MML:MUNDER");
		}
		while (tmpSubStr.indexOf("<MML:MFENCED ")!=-1)
		{
			tmpSubStr= processFance(tmpSubStr);
		}
//		System.out.println("eqStr:::"+tmpSubStr);


		int sp=0;
		int ep=0;
		//String tt="";
		//String tempVt="";
		//String vt="";
		//String matrixEndStr="";
		while (tmpSubStr.indexOf("<MML:MTABLE")!=-1)
		//while (sp!=-1)
		{
			//////To implement displaystyle for integral sign in matrix////////
			isMatrix=true;

			int ind=0;
			//commented by avinandan
			/*String tt= tmpSubStr.substring(0, tmpSubStr.indexOf("<MML:MTABLE"));
			String vt= tmpSubStr.substring(tmpSubStr.indexOf("<MML:MTABLE"), ind=tmpSubStr.indexOf("</MML:MTABLE>"))+"</MML:MTABLE>";
			String matrixEndStr=tmpSubStr.substring(ind+13);*/
			//end mark
			//added by avinandan
			//below code has been blocked by Ravi as it is not proper //31-05-2012
			
			String tt= tmpSubStr.substring(0, tmpSubStr.lastIndexOf("<MML:MTABLE"));
			String tempVt=tmpSubStr.substring(tmpSubStr.lastIndexOf("<MML:MTABLE"));
			String vt= tempVt.substring(tempVt.indexOf("<MML:MTABLE"), ind=tempVt.indexOf("</MML:MTABLE>"))+"</MML:MTABLE>";
			String matrixEndStr= tempVt.substring(ind+13);
			//Test the below code and if runnig fine, comment above 4 lines.

			/*
			// New code needs to check for proper output, 26-09-2013 by Ravi/Vivek	[Problem item JPPS_831]
			String tt= tmpSubStr.substring(0, tmpSubStr.indexOf("<MML:MTABLE",0));
			String tempVt=tmpSubStr.substring(tmpSubStr.indexOf("<MML:MTABLE",0),tmpSubStr.length())+"</MML:MTABLE>";
			String vt=tempVt.substring(tempVt.indexOf("<MML:MTABLE"), ind=tempVt.indexOf("</MML:MTABLE>"))+"</MML:MTABLE>";
			String matrixEndStr= tempVt.substring(ind+13);
			//
			*/

			//31-05-2012 end
			//31-05-2012 New Code Ravi
			
			/*sp=tmpSubStr.indexOf("<MML:MTABLE",ep);
			if(sp!=-1)
			{
				tt=tmpSubStr.substring(0, sp);
				ep=tmpSubStr.indexOf("</MML:MTABLE>",sp);
				if(ep!=-1)
				{
					vt=tmpSubStr.substring(sp, ep+"</MML:MTABLE>".length());
					//System.out.println("==========>>"+vt);
					matrixEndStr=tmpSubStr.substring(ep+"</MML:MTABLE>".length());
				}
			}*/
			//31-05-2012 end

			//System.out.println("vt---------->>"+vt);
			//System.in.read();
			//end mark
			/*int matrixStartInd= tmpSubStr.indexOf("<MML:MTABLE");
			int matrixEndInd= tmpSubStr.indexOf("</MML:MTABLE>");

			String matrixStartStr= tmpSubStr.substring(0, matrixStartInd);
			String matrixEndStr= tmpSubStr.substring(matrixStartInd);

			String matrixStr= tmpSubStr.substring(matrixStartInd, matrixEndInd)+"</MML:MTABLE>";

			System.out.println("matrixStr : "+matrixStr);
			//System.exit(0);
			tmpSubStr= matrixStartStr+processMatrix(matrixStr)+matrixEndStr;*/
			//System.out.println("matrixStr : "+processMatrix(vt));
			tmpSubStr= tt+ processMatrix(vt)+matrixEndStr;
		
		}

//************************************************This Start block for \\tt font*******[commented by Ravi15/11/2006]
		
		int mpos=0;
		int spos=-1;
		int co=1;
		String re="";
		StringBuffer st1=new StringBuffer(tmpSubStr);
		while(mpos != -1)
		{
			mpos=st1.indexOf("<MML:MTEXTR>",mpos);
			if((mpos !=-1))
			{
				spos=st1.indexOf("</MML:MTEXTR>",mpos);
				if(spos !=-1)
				{
					re=st1.substring(mpos+"<MML:MTEXTR>".length(),spos);
					st1.delete(mpos,spos+"</MML:MTEXTR>".length());
					st1.insert(mpos,"{\\tt{"+re+"}}");
				
				}
			}
		}

		tmpSubStr=st1.toString();
	
	
		mpos=0;
		spos=-1;
		co=1;
		re="";
		st1=new StringBuffer(tmpSubStr);
		while(mpos != -1)
		{
			re="";
			mpos=st1.indexOf("{\\tt{{\\tt{",spos);
			if((mpos!=-1))
			{
				spos=st1.indexOf("}",mpos);
				if(spos !=-1)
				{
					re=st1.substring(mpos+"{\\tt{{\\tt{".length(),spos);
						st1.delete(mpos,spos);
						st1.insert(mpos,"{\\tt{"+re);
				}
			}
			
		}

		tmpSubStr=st1.toString();

		mpos=0;
		spos=-1;
		co=1;
		re="";
		st1=new StringBuffer(tmpSubStr);
		while(mpos != -1)
		{
			re="";
			mpos=st1.indexOf("}}}}",mpos);
			if((mpos!=-1))
			{
						st1.delete(mpos,mpos+"}}}}".length());
						st1.insert(mpos,"}}");
						tmpSubStr=st1.toString();
			}
			
		}

//************************************************This End block for \\tt font*******[commented by Ravi15/11/2006] ]
		





		//commented by avinanda on 29-7-04
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "&ndash;", "\\\\hbox{-}");
		//end mark
		//Avinandan added
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "&ndash;", "\\\\hbox{\\\\ndash}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"RAD\"/>", "\\\\(^{\\\\bullet}\\\\)");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"SBND\"/>", "{\\\\zsbnd}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"DBND\"/>", "{\\\\zdbnd}");
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"CAMB\"/>", "\\\\epsfbox{t:/TeX-Data-Bank/grideps/bcu.eps}");//{\\\\camb}
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"CAMB\"/>", "\\\\camb");//{\\\\camb}
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"LOZFL\"/>", "{\\\\lozenge}");
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"LBOND2\"/>", "{\\\\epsfbox{langlebond.eps}}");//blocked on 23-02-2012
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"LBOND2\"/>", "{\\\\inserttiff{0.059in}{0.102in}{langlebond.tif}}");
		//tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"RBOND2\"/>", "{\\\\epsfbox{ranglebond.eps}}");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"RBOND2\"/>", "{%\r\n\\\\inserttiff{0.058in}{0.102in}{ranglebond.tif}}\r\n");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:GLYPH NAME=\"TBND\"/>", "{\\\\ztbnd}");		
		tmpSubStr= replaceMMLEntity(tmpSubStr);		
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MSUP>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MSUP>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MSQ>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MSQ>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MFRAC>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MFRAC>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MROOT>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MROOT>", "");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MOV>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MOV>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MACC>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MACC>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MUV>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MUV>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MSUSB>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MSUSB>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MUO>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MUO>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MS>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MS>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<BF>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</BF>", "");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\{\\\\tt\\{<MML:MTEXT>([^<]+)</MML:MTEXT>\\}\\}", "{\\\\tt{$1}}");
		//System.out.println("tmpSubStr==> "+tmpSubStr);
		//	System.in.read();
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\{\\\\bm\\{<MML:MTEXT>", "{\\\\rmbox{{\\\\bf{");
		
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT><CE:SMALL-CAPS>", "{\\\\rmbox{{\\\\sc{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</CE:SMALL-CAPS></MML:MTEXT>", "}}}}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<CE:SMALL-CAPS>", "{\\\\sc{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</CE:SMALL-CAPS>", "}}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT>\\{%\r\n\\\\epsfbox", "{%\r\n\\\\epsfbox");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\.eps\\}\\}\r\n</MML:MTEXT>", "\\.eps}}");
		//tmpSubStr=tmpSubStr.replaceAll("<MML:MTEXT>([a-zA-Z\\.\\;\\=\\+\\-\\(\\)\\*\\%\\$\\{\\}0-9]+)</MML:MTEXT>","<MML:MTEXT>$1</MML:MTEXT>");//11/06/2007
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MTEXT>", "{\\\\rmbox{");//don't change it
		
		

		/**
		 *Added By : Ravi [03/03/2007]
		 *Change Request By: Vivek
		 *Change Point : If rmbox conain bold face and <MML:MO> tag
		 **/
		tmpSubStr= MmlAndMO(tmpSubStr);
		//end
	//	System.out.println("tmpSubStr==> "+tmpSubStr);
	//	System.in.read();
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MTEXT>", "}}");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MO>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MI>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MI>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MN>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MN>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MROW>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MROW>", "");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MBI>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MBI>", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"FALSE\">", "");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MPHANTOM>", "\\\\phantom{");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "</MML:MPHANTOM>", "}");

		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MML:MO STRETCHY=\"TRUE\">", "");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\\\Down\\\\Down", "\\\\Down");
//		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\\\Down([a-zA-Z])", "$1");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\\\Down([a-zA-Z])", "\\\\$1");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\\\Down\\\\", "\\\\Down");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "%", "\\\\%");
		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "\\{\\\\%\r\n\\\\epsfbox", "{%\r\n\\\\epsfbox");//rajeev


		tmpSubStr= xmlObj.replaceStr(tmpSubStr, "<MTDSEP>", "& ");

		//************************[11/06/2007]********
	StringBuffer sbi=new StringBuffer(tmpSubStr);
		int n1=0;
		int n2=0;
		int n3=0;
		while(n1 != -1)
		{
			n1=sbi.indexOf("rmbox{",n2);
			if(n1 !=-1)
			{
				n2=sbi.indexOf("}",n1);
				if(n2 != -1)
				{
					n3=sbi.indexOf("_",n1);
					if(n3!=-1&&n3<n2)
					{
						sbi=sbi.insert(n3,"\\");
					}
				}
			}
			tmpSubStr=sbi.toString();
		}

		//********************************************
		
		
		
		
		
		// To be manipulated in latter versions
		//////To implement displaystyle for integral sign in matrix////////
		if(isMatrix)
		{
			//System.out.println("Matrix-->"+tmpSubStr);
			if(tmpSubStr.indexOf("\\int")!=-1)
			{
				tmpSubStr=tmpSubStr.replaceAll("\\\\int","\\\\displaystyle\\\\int");
			}
			if(tmpSubStr.indexOf("\\frac{")!=-1)
			{
				tmpSubStr=tmpSubStr.replaceAll("\\\\frac\\{","\\\\displaystyle\\\\frac\\{");
			}
			if(tmpSubStr.indexOf("\\textstyle{{\\displaystyle\\frac{")!=-1)
			{
				tmpSubStr=tmpSubStr.replaceAll("\\\\textstyle\\{\\{\\\\displaystyle\\\\frac\\{","\\\\textstyle\\{\\{\\\\frac\\{");
			}
			isMatrix=false;
		}
	//Added For Extra space in thin-space inbetween rmbox{\, }	
	StringBuffer rmbox =new StringBuffer(tmpSubStr);
	//System.out.println("1111111tmpSubStr==> "+tmpSubStr);
		int s=0;
	while(rmbox.indexOf("\\rmbox{\\, ",s) != -1)
		{
			//s=rmbox.indexOf("\\rmbox{\\,",s+"\\rmbox{\\,".length());
			s=rmbox.indexOf("\\rmbox{\\, ",s);
			//System.out.println("check==> "+s);
			if(s != -1)
			{
				
				int i=s+"\\rmbox{\\,".length();
			
				char check=rmbox.charAt(i);
				//System.out.println("check==> "+check);	
				if(check ==' ')
				{
					rmbox.deleteCharAt(i);
				}
				
			}
			if(s == -1)
			{
				System.out.println("[ERROR]: In loop. Please contect to S/W Dept. \nThis is the problem of Thin space in math.");
				//break;//incase of loop un comment the break statment to process urgent job.
			}
			
		}
//System.out.println("1111111tmpSubStr==> "+tmpSubStr);
tmpSubStr=rmbox.toString();
/**
* Added By Ravi [17/04/2007]
*Change Point : coding for longdiv in MathML  as \longdiv{}{} 
* Change Request By : Lalit
*/
if(tmpSubStr.indexOf("<MML:MENCLOSE NOTATION=\"LONGDIV\">")!=-1)
			{
				tmpSubStr=tmpSubStr.replaceAll("<MML:MENCLOSE NOTATION=\"LONGDIV\">","\\\\longdiv{");
			}
		if(tmpSubStr.indexOf("</MML:MENCLOSE>")!=-1)
			{
				tmpSubStr=tmpSubStr.replaceAll("</MML:MENCLOSE>","}{}");
			}
//end
	//	System.out.println("1111111tmpSubStr==> "+tmpSubStr);
	//	System.in.read();
		return tmpSubStr;
	}

public String Adding_MmlRow_Brfore_MmlMmultiscripts(String xt) throws IOException
	{
		int s=0;
		int e=0;
		String tmpSubStrinfunction=xt;
		//System.out.println("xt"+xt);
		//System.in.read();
		while (s !=-1)
		{
			StringBuffer te= new StringBuffer(tmpSubStrinfunction);
		
			s=te.indexOf("<MML:MMULTISCRIPTS>",s+"<MML:MMULTISCRIPTS>".length());
				if(s != -1)
						te.insert(s,"<MML:MROW>");
				tmpSubStrinfunction=te.toString();
		}
		 s=0;
		 e=0;
		while (s !=-1)
		{
			StringBuffer te= new StringBuffer(tmpSubStrinfunction);
			s=te.indexOf("</MML:MMULTISCRIPTS>",s+"</MML:MMULTISCRIPTS>".length());
		    	if(s != -1)
				{
					te.insert(s+"</MML:MMULTISCRIPTS>".length(),"</MML:MROW>");
				}
			tmpSubStrinfunction=te.toString();
		}
		return  tmpSubStrinfunction;
	}



	//this function added by avi
/*	public String processMonoSpace(String mmlexp)
	{
		String t1="";
		String t2="";
		String t3="";
		int pos=0;
		pos=mmlexp.lastIndexOf("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">");
		t1=mmlexp.substring(0, pos);
		t3=mmlexp.substring(pos);
		pos=t3.indexOf("</MML:MSTYLE>");
		t2="{\\tt{"+t3.substring("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">".length(), pos)+"}}";
		t3=t3.substring(t3.indexOf("</MML:MSTYLE>")+"</MML:MSTYLE>".length());
		return t1+t2+t3;
	}//end of processMonoSpace();
	//end mark
	
*/

//**********************************************************

public String processMonoSpace(String mmlexp) throws IOException
	{
		
		
		String t1="";
		String t2="";
		String t3="";
		String t4="";
		StringBuffer st=null;
		String tt="";
		int pos=0;
		int mpos=0;
		//pos=mmlexp.lastIndexOf("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">");
		pos=mmlexp.indexOf("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">");
		t1=mmlexp.substring(0, pos);
		mpos=mmlexp.indexOf("</MML:MSTYLE>",pos);
		//System.out.println("pos : "+pos +" mpos : "+mpos);
		//System.in.read();

		
		t3=mmlexp.substring(pos,mpos+"</MML:MSTYLE>".length());
		t4=mmlexp.substring(mpos+"</MML:MSTYLE>".length(),mmlexp.length());
		
		//System.out.println("t3 : "+t3);
		//System.in.read();
        //mpos=t3.indexOf("</MML:MSTYLE>");
		st=new StringBuffer(t3);
		int spos=0;
		int epos=0;
		spos=st.indexOf("<MML:MTEXT>");
		if(spos != -1)
		{
			epos=st.indexOf("</MML:MTEXT>", spos);
			if(epos != -1)
			{
				tt=st.substring(spos+"<MML:MTEXT>".length(),epos);
				st.delete(spos,epos+"</MML:MTEXT>".length());
				//st.insert(spos,"<MML:MTEXTR>"+tt);
				st.insert(spos,"<MML:MTEXTR>"+tt+"</MML:MTEXTR>");
				
			}
		}
		
		t3=st.toString();
		pos=t3.indexOf("</MML:MSTYLE>");

		
		t2="{\\tt{"+t3.substring("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">".length(), pos)+"}}";
		t3=t3.substring(t3.indexOf("</MML:MSTYLE>")+"</MML:MSTYLE>".length());
		//System.out.println("t1+t2+t3 : "+t1+t2+t4);
		//System.in.read();
		return t1+t2+t4;
	}

//This function is Modified By Ravi To handled tt font [15/11/2006]
	public String processMonoSpace_old(String mmlexp) throws IOException
	{
		String t1="";
		String t2="";
		String t3="";
		StringBuffer st=null;
		String tt="";
		int pos=0;
		int mpos=0;
		pos=mmlexp.lastIndexOf("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">");
		t1=mmlexp.substring(0, pos);
		t3=mmlexp.substring(pos);
		

        mpos=t3.indexOf("</MML:MSTYLE>");
		st=new StringBuffer(t3);
		int spos=0;
		int epos=0;
		spos=st.indexOf("<MML:MTEXT>");
		if(spos != -1)
		{
			epos=st.indexOf("</MML:MTEXT>", spos);
			if(epos != -1)
			{
				tt=st.substring(spos+"<MML:MTEXT>".length(),epos);
				st.delete(spos,epos+"</MML:MTEXT>".length());
				//st.insert(spos,"<MML:MTEXTR>"+tt);
				st.insert(spos,"<MML:MTEXTR>"+tt+"</MML:MTEXTR>");
				
			}
		}
		
		t3=st.toString();
		pos=t3.indexOf("</MML:MSTYLE>");

		
		t2="{\\tt{"+t3.substring("<MML:MSTYLE MATHVARIANT=\"MONOSPACE\">".length(), pos)+"}}";
		t3=t3.substring(t3.indexOf("</MML:MSTYLE>")+"</MML:MSTYLE>".length());
		return t1+t2+t3;
	}//end of processMonoSpace();
	//end mark


//******************************* End  ****************************
	
	public String processMMLObjects(String mmlexp, String type)
	{
		StringBuffer supVal= new StringBuffer();
		String tag="";
		String sType= "<"+type+">";
		String eType= "</"+type+">";
		//System.out.println("1111MML-->"+mmlexp);
		//System.out.println("1111TYPE-->"+type);
		//try{System.in.read();}catch(Exception e){}
		/*if(type.equals("MML:MUNDER"))
			isScript=true;
		else
			isScript=false;*/
		
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			boolean mfracbevelled=false;
			boolean mfraclinethickness=false;
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				tag="";
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				//System.out.println("tag==>>"+tag);
				//try{System.in.read();}catch(Exception e){}
				if (tag.startsWith("<MML:MTABLE"))
				{
					xmlObj.displayStyle= true;
					
				}
				else if (tag.equals("</MML:MTABLE>"))
				{
					xmlObj.displayStyle= false;
					
				}
				if(tag.equals("<MML:MFRAC BEVELLED=\"TRUE\">"))
				{
//					System.out.println("Mfrac Bevelled found:"+tag+":"+type);
					mfracbevelled=true;
					tag="<MML:MFRAC>";
				}
				if(tag.equals("<MML:MFRAC LINETHICKNESS=\"0\">"))
				{
					//System.out.println("Mfrac Bevelled found:"+tag+":"+type);
					mfraclinethickness=true;
					tag="<MML:MFRAC>";
				}
//				System.out.println("\n\ntag"+tag +" sType"+sType+"\n\n\n"+mmlexp);
				//try{System.in.read();}catch(Exception e){}
				if (tag.equals(sType))
				{
					tag    = "";
					int supCnt = 1;
					StringBuffer supStr= new StringBuffer();
					while (true)
					{
						try
						{
							ch= mmlexp.charAt(mpos++);
						}catch(Exception e){
							break;
						}
						if (ch=='<')
						{
							tag="";
							tag+=ch;
							for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
								tag+=ch;
							tag+=ch;
							if (tag.startsWith("<MML:MTABLE"))
							{
								xmlObj.displayStyle= true;
							}
							else if (tag.equals("</MML:MTABLE>"))
							{
								xmlObj.displayStyle= false;
							}
							if (tag.equals(sType))
							{
								supCnt++;
								supStr.append(tag);
							}
							else if (tag.equals(eType))
							{
								supCnt--;
								if(supCnt==0)
									break;
								else
									supStr.append(tag);
							}
							else
							{
								supStr.append(tag);
							}
						}
						else if(ch!='>')
						{
							supStr.append(ch);
							//System.out.println("ch==>> "+supStr);
						}
					}
//					System.out.println("supStr::"+supStr);
					String tmpsupStr= "";
					//System.out.println("Mfrac Bevelled true"+type+":"+mfraclinethickness+"\n\n "+supStr);
					if (type.equals("MML:MSUP") || type.equals("MML:MSUB") || type.equals("MML:MFRAC")|| type.equals("MML:MFRACM") || type.equals("MML:MFRACB") || type.equals("MML:MROOT"))
					{
						String type_temp=type;
						if(mfraclinethickness==true)
						{
							
							type="MML:MFRACM";
							//System.out.println("1111Mfrac Bevelled true"+type+":"+mfraclinethickness);
							
						}
						else  if(mfracbevelled==true && (type.equals("MML:MFRAC") || type.equals("MML:MFRACB")))
						{
//							System.out.println("Mfrac Bevelled true"+type+":"+mfracbevelled);
							type="MML:MFRACB";
							mfracbevelled=false;
						}
						mfraclinethickness=false;
//						System.out.println("supStr==>> "+supStr);
						tmpsupStr=  getMMLString_Group2(supStr.toString(), type);
//						System.out.println("tmpsupStr1"+tmpsupStr);
						type=type_temp;
						//System.out.println("tmpsupStr==>> "+tmpsupStr);
					}
					else if (type.equals("MML:MUNDER"))
					{
						tmpsupStr=  getMMLString_Group2(supStr.toString(), type);
//						System.out.println("tmpsupStr2"+tmpsupStr);
					}
					else if (type.equals("MML:BEVELLED"))//09/12/2009
					{
						type="MML:MFRACB";
						tmpsupStr=  getMMLString_Group2(supStr.toString(), type);
//						System.out.println("tmpsupStr3"+tmpsupStr);
					}
					//Avinandan Added
					else if (type.equals("MML:MOVER"))
					{
						tmpsupStr=  getMMLString_Group2(supStr.toString(), type);
//						System.out.println("tmpsupStr4"+tmpsupStr);
					}
					//end mark
					else if (type.equals("MML:MSUBSUP") || type.equals("MML:MUNDEROVER"))
					{
						tmpsupStr= getMMLString_Group3(supStr.toString(), type);
//						System.out.println("tmpsupStr5"+tmpsupStr);
					}
					else if (type.equals("MML:MFENCED") || type.equals("MML:MSQRT") || type.equals("MML:MTD"))
					{
						tmpsupStr= getMMLString_Group1(supStr.toString(), type);
//						System.out.println("tmpsupStr6"+tmpsupStr);
						//System.out.println(type+"\n\n---222------"+supStr);
						//System.out.println(supStr+"\n\n"+type);
						//try{System.in.read();}catch(Exception e){}
					}
					else
					{
						System.out.println(tmpsupStr+":::::::::"+type);
						System.exit(0);
						tmpsupStr= supStr.toString();
					}
//					System.out.println("Adding value in supVal-1:"+tmpsupStr);
					supVal.append(tmpsupStr);
				}
				else
				{
//					System.out.println("Adding value in supVal-2:"+tag);
					supVal.append(tag);
				}
			}
			else
//				System.out.println("Adding value in supVal-3:"+ch);
				supVal.append(ch);
		}
//		System.out.println("supVal=======>>"+supVal);
		//try{System.in.read();}catch(Exception e){}
		return supVal.toString();
	}

	public String getMMLString_Group1(String supStr, String type)
	{
		//System.out.println(supStr+"\n\n"+type);
		//try{System.in.read();}catch(Exception e){}
		ArrayList mmlexprs= new ArrayList();
		String mmlexp= supStr;
		String mmlStr  = "";
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();

				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				mmltmp.append(tag);

				String starttag     = tag;
				String endtag       = xmlObj.getEndtag(tag);
				int tagCnt          = 1;
				while (true)
				{
					
					ch= mmlexp.charAt(mpos++);
					System.out.print(ch);
					if (ch=='<')
					{
						tag="";
						tag+=ch;
						for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
							tag+=ch;
						tag+=ch;
						//System.out.println("tag===>>     "+tag);
						if (tag.equals(starttag))
						{
							tagCnt++;
							mmltmp.append(tag);
						}
						else if (tag.equals(endtag))
						{
							tagCnt--;
							if (tagCnt==0)
							{
								mmltmp.append(tag);
								break;
							}
							else
								mmltmp.append(tag);
						}
						else
						{
							mmltmp.append(tag);
						}
					}
					else if(ch!='>')
					{
						mmltmp.append(ch);
					}
				}
				mmlStr+= mmltmp;
				if(tagCnt==0)
					break;
			}
		}
		if(type.equals("MML:MSQRT"))
			mmlStr = "<MSQ>\\sqrt{"+mmlStr+"}</MSQ>";
			
		return mmlStr;
	}
	
	public String getMMLString_Group2(String supStr, String type)
	{
		//System.out.println("22222222TYPE-->"+type);
		//System.out.println("22222222TYPE1-->"+supStr);
		//try{System.in.read();}catch(Exception e){}
		ArrayList mmlexprs= new ArrayList();
		String mmlexp= supStr;
		String firstStr  = "";
		String secondStr = "";
		String remainingText="";//04-08-2011
		boolean first    = true;
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();

				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				mmltmp.append(tag);
		
				String starttag     = tag;
				String endtag       = xmlObj.getEndtag(tag);
				int tagCnt          = 1;
				while (true)
				{
					try
					{
						ch= mmlexp.charAt(mpos++);
					}
					catch(Exception arrexp)
					{
						break;
					}

					if (ch=='<')
					{
						tag="";
						tag+=ch;
						for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
							tag+=ch;
						tag+=ch;
						if (tag.equals(starttag))
						{
							tagCnt++;
							mmltmp.append(tag);
						}
						else if(tag.startsWith("<"+endtag.substring(2, endtag.length()-1)))
						{
							tagCnt++;
							mmltmp.append(tag);
						}
						else if (tag.equals(endtag))
						{
							tagCnt--;
							if (tagCnt==0)
							{
								mmltmp.append(tag);
								break;
							}
							else
								mmltmp.append(tag);
						}
						else
						{
							mmltmp.append(tag);
						}
					}
					else if(ch!='>')
					{
						mmltmp.append(ch);
					}
				}
				if (first== true)
					firstStr= mmltmp.toString();
				else
					secondStr= mmltmp.toString();
				if (first== true)
				{
					first= false;
					continue;
				}
				if(tagCnt==0)
					break;				
			}
			else
			{
				//System.out.println("====>>"+ch);
				remainingText+=ch+"";
			}
		}
		if(remainingText.length()>0)
		{
			//System.out.println("[ERROR]:TAKE EXTRA CARE IN THIS ARTICLE. THERE IS SOME MATH PROBLEM. NEED QC FOR THIS ARTICLE.");
		}
		String processStr="";
		if (type.equals("MML:MSUB"))
		{
			if (firstStr.startsWith("<MML:MROW><MML:MO>\\sin") || firstStr.startsWith("<MML:MROW><MML:MO>\\cos") ||
				firstStr.startsWith("<MML:MROW><MML:MO>\\tan") || firstStr.startsWith("<MML:MROW><MML:MO>\\cot")
				 || firstStr.startsWith("<MML:MROW><MML:MO>\\arccos") || firstStr.startsWith("<MML:MROW><MML:MO>\\arcsin")  || firstStr.startsWith("<MML:MROW><MML:MO>\\arctan"))
			{
				processStr= firstStr+"_{"+secondStr+"}";
			}
			else
			{
				//added by avinandan
				if(firstStr.endsWith("<MML:MO>&Prime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				else if(firstStr.endsWith("<MML:MO>&prime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				else if(firstStr.endsWith("<MML:MO>&tprime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				
				else
				{
					//System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXfirstStr :"+firstStr+":");
					if(firstStr.indexOf("&int;")!=-1)
						processStr= firstStr+"_{"+secondStr+"}";
					else
					{
						//processStr= "{"+firstStr+"}_{"+secondStr+"}";
						//System.out.println("--------- "+firstStr+"\n\n"+secondStr+" mike");
						if(secondStr.length()>0)
						{
							processStr= "<MML:MROW>{"+firstStr+"}_{"+secondStr+"}</MML:MROW>";
						}
						else
						{
							processStr= "{"+firstStr+"}";
						}
					}
				}
				//end mark
				//commented by avinandan
				//processStr= "{"+firstStr+"}_{"+secondStr+"}";
			}
		}
		else if (type.equals("MML:MSUP"))
		{
//			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXfirstStr :"+firstStr+":");
//			System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYfirstStr :"+secondStr+":");
			if (firstStr.startsWith("<MML:MROW><MML:MO>\\sin") || firstStr.startsWith("<MML:MROW><MML:MO>\\cos") ||
				firstStr.startsWith("<MML:MROW><MML:MO>\\tan") || firstStr.startsWith("<MML:MROW><MML:MO>\\cot")
				 || firstStr.startsWith("<MML:MROW><MML:MO>\\arccos") || firstStr.startsWith("<MML:MROW><MML:MO>\\arcsin") || firstStr.startsWith("<MML:MROW><MML:MO>\\arctan"))
			{
				processStr= "<MSUP>"+firstStr+"^{"+secondStr+"}</MSUP>";
			}
			else
			{
				//Added by Avinandan
				if(secondStr.equals("<MML:MO>&Prime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime}</MSUP>";
				}
				/*Comment:
				  *Below condition has been added to convert
				  *<MML:MROW><MML:MO>&Prime;</MML:MO></MML:MROW>
				  *This requirement given by Mr. Subrata Das
				  *Modify date : 23/01/2007
				*/
				else if(secondStr.equals("<MML:MROW><MML:MO>&Prime;</MML:MO></MML:MROW>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime}</MSUP>";
				}
				//end
				else if(secondStr.equals("<MML:MO>&prime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime}</MSUP>";
				}
				else if(secondStr.equals("<MML:MO>&tprime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime\\prime}</MSUP>";
				}
				else
				{
					processStr="<MSUP>{"+firstStr+"}^{"+secondStr+"}</MSUP>";
				}
				//end mark
				//commented by avinandan
				//processStr="<MSUP>{"+firstStr+"}^{"+secondStr+"}</MSUP>";
			}
//		System.out.println("KKKKKKKKKKKKKKKKKKKKKKK:"+processStr+":");
		}
		else if(type.equals("MML:MFRACM"))
			processStr= "<MFRAC>{{"+firstStr+"}\\atop{"+secondStr+"}}</MFRAC>";
		else if(type.equals("MML:MFRAC"))
			processStr= "<MFRAC>\\frac{"+firstStr+"}{"+secondStr+"}</MFRAC>";
		else if(type.equals("MML:MFRACB"))
		{
			//System.out.println("MFRACB found");
			processStr= "<MFRAC>\\bfrac{"+firstStr+"}{"+secondStr+"}</MFRAC>";
		}
		else if(type.equals("MML:MROOT"))
			processStr= "<MROOT>\\sqrt["+secondStr+"]{"+firstStr+"}</MROOT>";
		else if (type.equals("MML:MOVER"))
		{
			
			if(xmlObj.displayStyle==true)
				processStr+= "\\displaystyle";
			//Added by avinandan on 11-8-4
			
										//	<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>
			if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>")) && ((firstStr.indexOf("<MML:MOVER>"))!=-1))
			{
				//System.out.println("firstStr : "+firstStr);
				//try{System.in.read();}catch(Exception e){}
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MOVER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MOVER>","\\\\overbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>", "}");
				firstStr=tempFirst+tempLast;
			}
			/**
			* Added By Ravi [24/02/2007]
			* Change Request By : Vivek
			* Change Point : Wrong coding of overbracket, overbracket should always have an argument. CCR/110773
			*/
			else if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>")) && ((firstStr.indexOf("<MML:MOVER>"))!=-1))
			{
				//System.out.println("firstStr : "+firstStr);
				//try{System.in.read();}catch(Exception e){}
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MOVER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MOVER>","\\\\overbracket{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>", "}");
				firstStr=tempFirst+tempLast;
			}
			//end mark
			
			processStr+= "<MOV>\\mathop{"+firstStr+"}^{"+secondStr+"}</MOV>";
			
		}
		else if (type.equals("MML:MOVERACC"))
		{			
			//avinandan added this if block
			if(secondStr.equals("<MML:MO>&frown;</MML:MO>"))
				secondStr="<MML:MO>&Upfrown;</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rightharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rharu;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&RightVector;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&lharu;</MML:MO>"))
				secondStr="<MML:MO>\\Upleftharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&leftharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Upleftharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&LeftVector;</MML:MO>"))
				secondStr="<MML:MO>\\Upleftharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rightarrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&srarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&xrarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&RightArrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&leftarrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&slarr;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&larr;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&LeftArrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&ShortLeftArrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&rightharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&circ;</MML:MO>"))
				secondStr="<MML:MO>\\widehat</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&tilde;</MML:MO>"))
				secondStr="<MML:MO>\\widetilde</MML:MO>";
			else if(secondStr.equals("<MML:MO>&smile;</MML:MO>"))
				secondStr="<MML:MO>\\upsmile</MML:MO>";
			/*Comment:
			 *Below condition has been added to convert
			 *<MML:MO>&RightArrow;</MML:MO> to \\vec
			 *This requirement given by Mr. Subrata Das
			 *Modify date : 22/01/2007
			 */
			else if(secondStr.equals("<MML:MO>&RightArrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&rightarrow;</MML:MO>"))
				secondStr="<MML:MO>\\overrightarrow</MML:MO>";
			//end Comment
			processStr+= "<MACC>{"+secondStr+"{"+firstStr+"}}</MACC>";
			
		}
		else if (type.equals("MML:MUNDER"))
		{
			if(xmlObj.displayStyle==true)
				processStr+= "\\displaystyle";
			//added by avinandan
			if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>")) && ((firstStr.indexOf("<MML:MUNDER>"))!=-1))
			{
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MUNDER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MUNDER>","\\\\underbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>", "}");
				firstStr=tempFirst+tempLast;
			}
			/**
				Added By Ravi :[02/04/2007]

			*/
			else if((firstStr.indexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>")!=-1) && ((firstStr.indexOf("<MML:MUNDER>"))!=-1))
			{
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MUNDER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MUNDER>","\\\\underbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>", "}");
				firstStr=tempFirst+tempLast;
			}
			

			
			if(secondStr.equals(""))
			{
				int in=firstStr.indexOf("<MML:MROW><MML:MSUBSUP>",0) ;
				 String stat="";
				 String endstr="";
				if(in !=-1)
				{
					stat=firstStr.substring(0,in);
					endstr=firstStr.substring(in,firstStr.length());
					secondStr=endstr;
					firstStr=stat;
					int in1=secondStr.indexOf("}<MML:MROW><MML:MTEXT>",0) ;
					if(in1 !=-1)
					{
						String first1=secondStr.substring(0,in1);
						String second=secondStr.substring(in1,secondStr.length());
						secondStr="{"+first1+"}_{"+second+"}";
					}
					
				}
				processStr+= "<MUV>\\mathop{"+firstStr+"}_{"+secondStr+"}</MUV>";
			}
			else
			{
				processStr+= "<MUV>\\mathop{"+firstStr+"}_{"+secondStr+"}</MUV>";
			}
		}
		//end mark
		else if (type.equals("MML:MUNDERACC"))
		{
			//System.out.println("F1-->"+firstStr);
			//added by avinandan			
			processStr+="<MUV>\\Down"+secondStr+"{"+firstStr+"}</MUV>";
			//System.out.println("F1-->"+processStr);
			//try{System.in.read();}catch(Exception s){}
			if(processStr.indexOf("<MUV>\\Down<MML:MO STRETCHY=\"TRUE\">&lowbar;</MML:MO>")!=-1)
			{
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO STRETCHY=\"TRUE\">\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>\\\\underline");
			}
			if(processStr.indexOf("<MUV>\\Down<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace")!=-1)
			{
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>ThomsonDiff_ThomsonDiffOpenBrace$1ThomsonDiffCloseBrace\\\\underline");
				//System.out.println("eeeF1-->"+processStr);
				//try{System.in.read();}catch(Exception s){}
			}
			
			if(processStr.indexOf("<MUV>\\Down<MML:MO>&lowbar;</MML:MO>")!=-1)
			{
				//System.out.println("-----------mike "+processStr);
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO>\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>\\\\dmacr");
			}			
			//end mark
			//commented by avinandan
			//processStr+= "<MUV>\\Down"+secondStr+"{"+firstStr+"}</MUV>";
			//end mark
		}
		/**
				Added By Ravi :[02/04/2007]
		*/
		//System.out.println("\n\nprocessStr --> "+processStr );
		//try{System.in.read();}catch(Exception e){}
		if(remainingText.length()>0)
		{
			processStr=processStr+remainingText;//12-08-2011
		}
		if(processStr.indexOf("_{{}",0 )!=-1)
		{
			
			processStr=processStr.replaceAll("_\\{\\{\\}","");
		}
		if(processStr.indexOf("}_{}",0 )!=-1)
		{
			
			processStr=processStr.replaceAll("\\}_\\{\\}","}_{");
		}
		
		//end

		//System.out.println("\n\nravi processStr --> "+processStr );
		//try{System.in.read();}catch(Exception e){}
		if(processStr.indexOf(remainingText,0)==-1)
		{
			System.out.println("[ERROR]: MISSING TEXT \""+remainingText+"\" IN MATH EQUATION...PLEASE CHECK.\nSYSTEM IS GOING TO EXIT.");
			//System.exit(0);
			//System.out.println("Here supStr:\n"+supStr);
			
				processStr=getMMLString_Group_TeX_Rout(supStr, type);
			
			
		}
		return processStr;
	}


public String getMMLString_Group_TeX_Rout(String supStr, String type)
	{
		//System.out.println("TYPE-->"+type);
		//System.out.println("TYPE1-->"+supStr);
		//try{System.in.read();}catch(Exception e){}
		ArrayList mmlexprs= new ArrayList();
		String mmlexp= supStr;
		String firstStr  = "";
		String secondStr = "";
		String remainingText="";//04-08-2011
		boolean first    = true;
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			
			if (ch=='<')
			{
				String tag          = "";
				
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				mmltmp.append(tag);
		
				String starttag     = tag;
				String endtag       = xmlObj.getEndtag(tag);
				int tagCnt          = 1;
				while (true)
				{
					try
					{
						//if(mpos<mmlexp.length())
						ch= mmlexp.charAt(mpos++);
					}
					catch(Exception arrexp)
					{
						System.out.println("Error is here");
						arrexp.printStackTrace();
						break;
					}

					if (ch=='<')
					{
						tag="";
						tag+=ch;
						for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
							tag+=ch;
						tag+=ch;
						if (tag.equals(starttag))
						{
							//System.out.println("1");
							tagCnt++;
							mmltmp.append(tag);
						}
						else if(tag.startsWith("<"+endtag.substring(2, endtag.length()-1)))
						{
							//System.out.println("2");
							tagCnt++;
							mmltmp.append(tag);
						}
						else if (tag.equals(endtag))
						{
							//System.out.println("3");
							tagCnt--;
							if (tagCnt==0 )
							{
								//System.out.println("4");
								mmltmp.append(tag);
								break;
							}
							else
							{
								//System.out.println("5");
								mmltmp.append(tag);
							}
						}
						else
						{
							//System.out.println("6");
							mmltmp.append(tag);
						}
					}
					else if(ch!='>')
					{
						mmltmp.append(ch);
					}
				}
				if (first== true)
					firstStr= mmltmp.toString();
				else
					secondStr= mmltmp.toString();
				if (first== true)
				{
					first= false;
					continue;
				}
				if(tagCnt==0)
					break;				
			}
			/*else
			{
				//System.out.println("====>>"+ch);
				remainingText+=ch+"";
				//mmltmp.append(ch);
			}*/
		}
		//System.out.println("firstStr--->>"+firstStr);
		//System.out.println("secondStr--->>"+secondStr);
		String processStr="";
		if (type.equals("MML:MSUB"))
		{
			if (firstStr.startsWith("<MML:MROW><MML:MO>\\sin") || firstStr.startsWith("<MML:MROW><MML:MO>\\cos") ||
				firstStr.startsWith("<MML:MROW><MML:MO>\\tan") || firstStr.startsWith("<MML:MROW><MML:MO>\\cot")
				 || firstStr.startsWith("<MML:MROW><MML:MO>\\arccos") || firstStr.startsWith("<MML:MROW><MML:MO>\\arcsin")  || firstStr.startsWith("<MML:MROW><MML:MO>\\arctan"))
			{
				processStr= firstStr+"_{"+secondStr+"}";
			}
			else
			{
				//added by avinandan
				if(firstStr.endsWith("<MML:MO>&Prime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				else if(firstStr.endsWith("<MML:MO>&prime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				else if(firstStr.endsWith("<MML:MO>&tprime;</MML:MO></MML:MSUP>"))
				{
					processStr= firstStr+"_{"+secondStr+"}";
				}
				
				else
				{
					//System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXfirstStr :"+firstStr+":");
					if(firstStr.indexOf("&int;")!=-1)
						processStr= firstStr+"_{"+secondStr+"}";
					else
					{
						//processStr= "{"+firstStr+"}_{"+secondStr+"}";
						//System.out.println("--------- "+firstStr+"\n\n"+secondStr+" mike");
						if(secondStr.length()>0)
						{
							processStr= "{"+firstStr+"}_{"+secondStr+"}";
						}
						else
						{
							processStr= "{"+firstStr+"}";
						}
					}
				}
				//end mark
				//commented by avinandan
				//processStr= "{"+firstStr+"}_{"+secondStr+"}";
			}
		}
		else if (type.equals("MML:MSUP"))
		{
//			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXfirstStr :"+firstStr+":");
//			System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYfirstStr :"+secondStr+":");
			if (firstStr.startsWith("<MML:MROW><MML:MO>\\sin") || firstStr.startsWith("<MML:MROW><MML:MO>\\cos") ||
				firstStr.startsWith("<MML:MROW><MML:MO>\\tan") || firstStr.startsWith("<MML:MROW><MML:MO>\\cot")
				 || firstStr.startsWith("<MML:MROW><MML:MO>\\arccos") || firstStr.startsWith("<MML:MROW><MML:MO>\\arcsin") || firstStr.startsWith("<MML:MROW><MML:MO>\\arctan"))
			{
				processStr= "<MSUP>"+firstStr+"^{"+secondStr+"}</MSUP>";
			}
			else
			{
				//Added by Avinandan
				if(secondStr.equals("<MML:MO>&Prime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime}</MSUP>";
				}
				/*Comment:
				  *Below condition has been added to convert
				  *<MML:MROW><MML:MO>&Prime;</MML:MO></MML:MROW>
				  *This requirement given by Mr. Subrata Das
				  *Modify date : 23/01/2007
				*/
				else if(secondStr.equals("<MML:MROW><MML:MO>&Prime;</MML:MO></MML:MROW>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime}</MSUP>";
				}
				//end
				else if(secondStr.equals("<MML:MO>&prime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime}</MSUP>";
				}
				else if(secondStr.equals("<MML:MO>&tprime;</MML:MO>"))
				{
					processStr="<MSUP>{"+firstStr+"}^{\\prime\\prime\\prime}</MSUP>";
				}
				else
				{
					processStr="<MSUP>{"+firstStr+"}^{"+secondStr+"}</MSUP>";
				}
				//end mark
				//commented by avinandan
				//processStr="<MSUP>{"+firstStr+"}^{"+secondStr+"}</MSUP>";
			}
//		System.out.println("KKKKKKKKKKKKKKKKKKKKKKK:"+processStr+":");
		}
		else if(type.equals("MML:MFRAC"))
			processStr= "<MFRAC>\\frac{"+firstStr+"}{"+secondStr+"}</MFRAC>";
		else if(type.equals("MML:MFRACB"))
		{
			//System.out.println("MFRACB found");
			processStr= "<MFRAC>\\bfrac{"+firstStr+"}{"+secondStr+"}</MFRAC>";
		}
		else if(type.equals("MML:MROOT"))
			processStr= "<MROOT>\\sqrt["+secondStr+"]{"+firstStr+"}</MROOT>";
		else if (type.equals("MML:MOVER"))
		{
			
			if(xmlObj.displayStyle==true)
				processStr+= "\\displaystyle";
			//Added by avinandan on 11-8-4
			
										//	<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>
			if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>")) && ((firstStr.indexOf("<MML:MOVER>"))!=-1))
			{
				//System.out.println("firstStr : "+firstStr);
				//try{System.in.read();}catch(Exception e){}
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MOVER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MOVER>","\\\\overbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&OverBrace;</MML:MO></MML:MOVER>", "}");
				firstStr=tempFirst+tempLast;
			}
			/**
			* Added By Ravi [24/02/2007]
			* Change Request By : Vivek
			* Change Point : Wrong coding of overbracket, overbracket should always have an argument. CCR/110773
			*/
			else if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>")) && ((firstStr.indexOf("<MML:MOVER>"))!=-1))
			{
				//System.out.println("firstStr : "+firstStr);
				//try{System.in.read();}catch(Exception e){}
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MOVER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MOVER>","\\\\overbracket{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&OverBracket;</MML:MO></MML:MOVER>", "}");
				firstStr=tempFirst+tempLast;
			}
			//end mark
			
			processStr+= "<MOV>\\mathop{"+firstStr+"}^{"+secondStr+"}</MOV>";
			
		}
		else if (type.equals("MML:MOVERACC"))
		{			
			//avinandan added this if block
			if(secondStr.equals("<MML:MO>&frown;</MML:MO>"))
				secondStr="<MML:MO>&Upfrown;</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rightharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&lharu;</MML:MO>"))
				secondStr="<MML:MO>\\Upleftharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&leftharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Upleftharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rightarrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&srarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&xrarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&rarr;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&RightArrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&leftarrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&slarr;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&larr;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&LeftArrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO>&ShortLeftArrow;</MML:MO>"))
				secondStr="<MML:MO>\\rvec</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&rightharpoonup;</MML:MO>"))
				secondStr="<MML:MO>\\Uprightharpoonup</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&circ;</MML:MO>"))
				secondStr="<MML:MO>\\widehat</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&tilde;</MML:MO>"))
				secondStr="<MML:MO>\\widetilde</MML:MO>";
			else if(secondStr.equals("<MML:MO>&smile;</MML:MO>"))
				secondStr="<MML:MO>\\upsmile</MML:MO>";
			/*Comment:
			 *Below condition has been added to convert
			 *<MML:MO>&RightArrow;</MML:MO> to \\vec
			 *This requirement given by Mr. Subrata Das
			 *Modify date : 22/01/2007
			 */
			else if(secondStr.equals("<MML:MO>&RightArrow;</MML:MO>"))
				secondStr="<MML:MO>\\vec</MML:MO>";
			else if(secondStr.equals("<MML:MO STRETCHY=\"TRUE\">&rightarrow;</MML:MO>"))
				secondStr="<MML:MO>\\overrightarrow</MML:MO>";
			//end Comment
			processStr+= "<MACC>{"+secondStr+"{"+firstStr+"}}</MACC>";
			
		}
		else if (type.equals("MML:MUNDER"))
		{
			if(xmlObj.displayStyle==true)
				processStr+= "\\displaystyle";
			//added by avinandan
			if((firstStr.endsWith("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>")) && ((firstStr.indexOf("<MML:MUNDER>"))!=-1))
			{
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MUNDER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MUNDER>","\\\\underbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>", "}");
				firstStr=tempFirst+tempLast;
			}
			/**
				Added By Ravi :[02/04/2007]

			*/
			else if((firstStr.indexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>")!=-1) && ((firstStr.indexOf("<MML:MUNDER>"))!=-1))
			{
				String tempFirst=firstStr.substring(0, firstStr.indexOf("<MML:MUNDER>"));
				String tempLast=firstStr.substring(firstStr.indexOf("<MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MUNDER>","\\\\underbrace{");
				firstStr=tempFirst+tempLast;
				tempFirst=firstStr.substring(0, firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=firstStr.substring(firstStr.lastIndexOf("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>"));
				tempLast=tempLast.replaceFirst("<MML:MO STRETCHY=\"TRUE\">&UnderBrace;</MML:MO></MML:MUNDER>", "}");
				firstStr=tempFirst+tempLast;
			}
			

			
			if(secondStr.equals(""))
			{
				int in=firstStr.indexOf("<MML:MROW><MML:MSUBSUP>",0) ;
				 String stat="";
				 String endstr="";
				if(in !=-1)
				{
					stat=firstStr.substring(0,in);
					endstr=firstStr.substring(in,firstStr.length());
					secondStr=endstr;
					firstStr=stat;
					int in1=secondStr.indexOf("}<MML:MROW><MML:MTEXT>",0) ;
					if(in1 !=-1)
					{
						String first1=secondStr.substring(0,in1);
						String second=secondStr.substring(in1,secondStr.length());
						secondStr="{"+first1+"}_{"+second+"}";
					}
					
				}
				processStr+= "<MUV>\\mathop{"+firstStr+"}_{"+secondStr+"}</MUV>";
			}
			else
			{
				processStr+= "<MUV>\\mathop{"+firstStr+"}_{"+secondStr+"}</MUV>";
			}
		}
		//end mark
		else if (type.equals("MML:MUNDERACC"))
		{
			//System.out.println("F1-->"+firstStr);
			//added by avinandan			
			processStr+="<MUV>\\Down"+secondStr+"{"+firstStr+"}</MUV>";
			//System.out.println("F1-->"+processStr);
			//try{System.in.read();}catch(Exception s){}
			if(processStr.indexOf("<MUV>\\Down<MML:MO STRETCHY=\"TRUE\">&lowbar;</MML:MO>")!=-1)
			{
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO STRETCHY=\"TRUE\">\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>\\\\underline");
			}
			if(processStr.indexOf("<MUV>\\Down<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace")!=-1)
			{
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO STRETCHY=\"TRUE\">ThomsonDiff_ThomsonDiffOpenBrace([0-9]+)ThomsonDiffCloseBrace\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>ThomsonDiff_ThomsonDiffOpenBrace$1ThomsonDiffCloseBrace\\\\underline");
				//System.out.println("eeeF1-->"+processStr);
				//try{System.in.read();}catch(Exception s){}
			}
			
			if(processStr.indexOf("<MUV>\\Down<MML:MO>&lowbar;</MML:MO>")!=-1)
			{
				//System.out.println("-----------mike "+processStr);
				Pattern p=Pattern.compile("<MUV>\\\\Down<MML:MO>\\&lowbar;</MML:MO>");
				Matcher m=p.matcher(processStr);
				processStr=m.replaceAll("<MUV>\\\\dmacr");
			}			
			//end mark
			//commented by avinandan
			//processStr+= "<MUV>\\Down"+secondStr+"{"+firstStr+"}</MUV>";
			//end mark
		}
		/**
				Added By Ravi :[02/04/2007]
		*/
		//System.out.println("\n\nprocessStr --> "+processStr );
		//try{System.in.read();}catch(Exception e){}
		/*if(remainingText.length()>0)
		{
			processStr=processStr+remainingText;//12-08-2011
		}*/
		if(processStr.indexOf("_{{}",0 )!=-1)
		{
			
			processStr=processStr.replaceAll("_\\{\\{\\}","");
		}
		if(processStr.indexOf("}_{}",0 )!=-1)
		{
			
			processStr=processStr.replaceAll("\\}_\\{\\}","}_{");
		}
		
		//end

		//System.out.println("\n\nravi processStr --> "+processStr );
		//try{System.in.read();}catch(Exception e){}
		/*if(processStr.indexOf(remainingText,0)==-1)
		{
			System.out.println("[ERROR]: MISSING TEXT \""+remainingText+"\" IN MATH EQUATION...PLEASE CHECK.\nSYSTEM IS GOING TO EXIT.");
			System.exit(0);
		}*/
		return processStr;
	}


	public String getMMLString_Group3(String supStr, String type)
	{
		String mmlexp= supStr;
		String firstStr  = "";
		String secondStr = "";
		String thirdStr  = "";
		boolean first    = true;
		int cnt          = 1;
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();

				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				mmltmp.append(tag);

				String starttag     = tag;
				String endtag       = xmlObj.getEndtag(tag);
				int tagCnt          = 1;
				while (true)
				{
					try
					{ch= mmlexp.charAt(mpos++);
					}
					catch(StringIndexOutOfBoundsException exp)
					{
//						System.out.println("mmlexp : "+mmlexp);
//						System.out.println("mmltmp : " +mmltmp);
						break;
					}
					if (ch=='<')
					{
						tag="";
						tag+=ch;
						for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
							tag+=ch;
						tag+=ch;
						if (tag.equals(starttag))
						{
							tagCnt++;
							mmltmp.append(tag);
						}
						else if (tag.equals(endtag))
						{
							tagCnt--;
							if (tagCnt==0)
							{
								mmltmp.append(tag);
								break;
							}
							else
								mmltmp.append(tag);
						}
						else
						{
							mmltmp.append(tag);
						}
					}
					else if(ch!='>')
					{
						mmltmp.append(ch);
					}
				}
				if (cnt ==1)
				{
					firstStr= mmltmp.toString();
					cnt++;
					continue;
				}
				else if (cnt ==2)
				{
					secondStr= mmltmp.toString();
					cnt++;
					continue;
				}
				else if (cnt ==3)
				{
					thirdStr= mmltmp.toString();
					break;
				}
				if(tagCnt==0)
					break;
			}
		}
		String processStr="";
		if(type.equals("MML:MSUBSUP"))
		{
			if(firstStr.indexOf("&int;")!=-1)
			{				
				processStr= "<MSUSB>"+firstStr+"_{"+secondStr+"}^{"+thirdStr+"}</MSUSB>";				
			}
			else
			{
				processStr= "<MSUSB>{"+firstStr+"}_{"+secondStr+"}^{"+thirdStr+"}</MSUSB>";
			}

		}
		else if(type.equals("MML:MUNDEROVER"))
		{
			//commented by avinandan
			//processStr=
			//"<MUO>"+firstStr+"_{"+secondStr+"}^{"+thirdStr+"}</MUO>";
			//end mark
			//added by avinandan
			processStr="";
			if(xmlObj.displayStyle==true)
				processStr+= "\\displaystyle";
			processStr+= "<MUO>\\mathop{"+firstStr+"}\\limits_{"+secondStr+"}^{"+thirdStr+"}</MUO>";

		}
		return processStr;
	}

	public String processAccentOver(String mmlStr)
	{
		String mmlexp = mmlStr;
		StringBuffer processStr= new StringBuffer();
		boolean isAccent = false;
		boolean stretchy = false;

		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				if (tag.equals("<MML:MOVER>") || tag.startsWith("<MML:MOVER "))
				{
					if(tag.startsWith("<MML:MOVER ACCENT=\"TRUE\">"))
						isAccent = true;
					// extract data here
//					System.out.println("tag::"+tag);
					int moverCnt= 1;
					ch= mmlexp.charAt(mpos);
					StringBuffer movertmp = new StringBuffer();
					while (true)
					{
						tag = "";
						ch= mmlexp.charAt(++mpos);
						if (ch=='<')
						{
							for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
								tag+=ch;
							tag+=ch;
							if (tag.equals("<MML:MOVER>") || tag.startsWith("<MML:MOVER "))
							{
								moverCnt++;
								movertmp.append(tag);
							}
							else if(isAccent && tag.startsWith("<MML:MO STRETCHY=\"TRUE\">")){
								stretchy=true;
								movertmp.append(tag);
							}else if (tag.equals("</MML:MOVER>"))
							{
								moverCnt--;
								if(moverCnt==0)
									break;
								else
									movertmp.append(tag);
							}
							else
							{
								movertmp.append(tag);
							}
						}
						else
						{
							movertmp.append(ch);
						}
					}
					
					/*if(movertmp.toString().indexOf("<MML:MO STRETCHY=\"TRUE\">")!=-1){
						isAccent=false;
					}*/
					
					if (isAccent== true)
					{
						String moverExp= movertmp.toString();
//						System.out.println("moverExp==>"+moverExp);
						if(stretchy){
							processStr.append(getMMLString_Group2(moverExp, "MML:MOVER"));
//							System.out.println("STRETCHY-TRUE=="+moverExp);
							stretchy=false;
						}else{
							processStr.append(getMMLString_Group2(moverExp, "MML:MOVERACC"));
//							System.out.println("STRETCHY-FALSE=="+moverExp);
						}
//						System.out.println("processStr::"+processStr.toString());
						isAccent=false;
					}
					else
					{
						// Process as a normal mover
						String moverExp= movertmp.toString();
						processStr.append(getMMLString_Group2(moverExp, "MML:MOVER"));
					}					
					// process mover here (first occurance only)
				}
				else
				{
					processStr.append(tag);
				}
			}
			else
			{
				processStr.append(ch);
			}
		}
//		System.out.println(processStr);
//		System.exit(0);
		return processStr.toString();
	}

	public String processAccentUnder(String mmlStr)
	{
		String mmlexp = mmlStr;
		StringBuffer processStr= new StringBuffer();
		boolean isAccent = false;
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				if (tag.equals("<MML:MUNDER>") || tag.startsWith("<MML:MUNDER "))
				{
					if (tag.startsWith("<MML:MUNDER ACCENTUNDER=\"TRUE\">"))
						isAccent = true;
					// extract data here
					int moverCnt= 1;
					ch= mmlexp.charAt(mpos);
					StringBuffer movertmp = new StringBuffer();
					while (true)
					{
						tag = "";
						ch= mmlexp.charAt(++mpos);

						if (ch=='<')
						{
							for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
								tag+=ch;
							tag+=ch;
							if (tag.equals("<MML:MUNDER>") || tag.startsWith("<MML:MUNDER "))
							{
								moverCnt++;
								movertmp.append(tag);
							}
							else if (tag.equals("</MML:MUNDER>"))
							{
								moverCnt--;
								if(moverCnt==0)
									break;
								else
									movertmp.append(tag);
							}
							else
							{
								movertmp.append(tag);
							}
						}
						else
						{
							movertmp.append(ch);
						}
					}
					if (isAccent== true)
					{
						String moverExp= movertmp.toString();
						processStr.append(getMMLString_Group2(moverExp, "MML:MUNDERACC"));
						isAccent=false;
						//System.out.println("TRUEEEEEEEEE "+processStr);
					}
					else
					{
						// Process as a normal mover
						//System.out.println("FALSEEEEEEEEEEE");
						String moverExp= movertmp.toString();
						processStr.append(getMMLString_Group2(moverExp, "MML:MUNDER"));
					}
					// process mover here (first occurance only)
				}
				else
				{
					processStr.append(tag);
				}
			}
			else
			{
				processStr.append(ch);
			}
		}
		return processStr.toString();
	}

	public String processFance(String mmlStr)
	{
		//System.out.println("mmlStr===>>"+mmlStr);
		int mcount=0;
		StringBuffer processStr= new StringBuffer();
		String fenceTag        = "";
		String mmlexp          = mmlStr;

		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				if (tag.startsWith("<MML:MFENCED "))
				{
					fenceTag= tag;
					// extract data here
					int mfenCnt= 1;
					ch= mmlexp.charAt(mpos);
					StringBuffer mfentmp = new StringBuffer();
					while (true)
					{
						tag = "";
						//System.out.println("mmlexp=="+mmlexp);
						//System.out.println("mpos===="+mpos);
						++mpos;
						if(mpos >= mmlexp.length()){
							break;
						}
						ch= mmlexp.charAt(mpos);
						if (ch=='<')
						{
							for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++){
								tag+=ch;
								//System.out.println("tag====="+tag);
							}
							tag+=ch;
							if (tag.startsWith("<MML:MFENCED "))
							{
								mfenCnt++;
								mfentmp.append(tag);
							}
							else if (tag.equals("</MML:MFENCED>"))
							{
								mfenCnt--;
								if(mfenCnt==0)
									break;
								else
									mfentmp.append(tag);
							}
							else
							{
								mfentmp.append(tag);
							}
						}
						else
						{
							mfentmp.append(ch);
						}
					}
					StringBuffer fenceStr= new StringBuffer();
					String openFence  = getFenceTexCode(xmlObj.getAttributeValue(fenceTag, "OPEN"));
					String closeFence = getFenceTexCode(xmlObj.getAttributeValue(fenceTag, "CLOSE"));

					if (openFence.length()>0)
					{
						fenceStr.append("\\left"+openFence);
					}
					else
						fenceStr.append("\\left.");

					fenceStr.append(mfentmp);

					if (closeFence.length()>0)
						fenceStr.append("\\right"+closeFence);
					else
						fenceStr.append("\\right.");
					processStr.append(fenceStr);
				}
				else
				{
					processStr.append(tag);
				}
			}
			else
			{
				processStr.append(ch);
			}
			mcount++;
		}
		//System.out.println("\n\n["+mcount+"]\tMML:MFENCED"+processStr.toString());
		return processStr.toString();
	}

	public String getFenceTexCode(String fenceType)
	{
		String fenceTexCode=fenceType;
		//System.out.println("fenceTexCode==> "+fenceTexCode);
		if(fenceType.equals("&DOUBLEVERTICALBAR;"))
			fenceTexCode= "\\|";
		else if(fenceType.equals("&LANGLE;"))
			fenceTexCode= "\\langle ";
		else if(fenceType.equals("&RANGLE;"))
			fenceTexCode= "\\rangle ";
		else if(fenceType.equals("&#X27E8;"))
			fenceTexCode= "\\langle ";
		else if(fenceType.equals("&#X27E9;"))
			fenceTexCode= "\\rangle ";
		else if(fenceType.equals("{"))
			fenceTexCode= "\\{";
		else if(fenceType.equals("}"))
			fenceTexCode= "\\}";
		else if(fenceType.equals("&LEFTFLOOR;"))
			fenceTexCode= "\\lfloor";
		else if(fenceType.equals("&RIGHTFLOOR;"))
			fenceTexCode= "\\rfloor";
		else if(fenceType.equals("&LOBRK;"))
			fenceTexCode= "\\llbracket";
		else if(fenceType.equals("&ROBRK;"))
			fenceTexCode= "\\rrbracket";
		else if(fenceType.equals("&VERT;"))
			fenceTexCode= "\\Vert ";
		else if(fenceType.equals("&BIGVERBAR;"))
			fenceTexCode= "\\Vert ";
		else if(fenceType.equals("&SMALLVERBAR;"))
			fenceTexCode= "\\vert ";
		return 	fenceTexCode;
	}

	public String replaceMMLEntity(String mml)
	{
		String mmlexp= mml;
		StringBuffer processStr= new StringBuffer();
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
					tag+=ch;
				tag+=ch;
				if (tag.startsWith("<MML:MSTYLE"))
				{
					xmlObj.mmlStyle= xmlObj.getAttributeValue(tag, "SCRIPTLEVEL");
					xmlObj.mathvariant= xmlObj.getAttributeValue(tag, "MATHVARIANT");
					if(xmlObj.mathvariant.equals("BOLD"))
					{
						//commented by avinandan
						//processStr.append("{\\bf{");
						//added by avinandan
						//processStr.append("{\\bm{");//19-12-2011 blocked Problem faced in MATCOM 3722
						/*
							* Date : 19-12-2011
							* ADD : new Value for BOLD attributes.
							* Add By : Ravi Shekhar
						*/
						processStr.append("{\\bf{");
					}
					//end mark
					else if(xmlObj.mathvariant.equals("BOLD-ITALIC"))
						processStr.append("<MBI>{\\bm{");
					else if(xmlObj.mmlStyle.equals("1"))
					{
						processStr.append("\\textstyle{{");
					}
					else
						processStr.append("<MBI>{{");
				}
				//added by avinandan
				else if(tag.startsWith("<MML:MI"))
				{
					xmlObj.mathvariant= xmlObj.getAttributeValue(tag, "MATHVARIANT");
				}
				//end mark
				else if (tag.equals("</MML:MSTYLE>"))
				{
					xmlObj.mmlStyle= "";
					xmlObj.mathvariant= "";
					processStr.append("}}</MBI>");
				}
				else
				{
					processStr.append(tag);
				}
			}
			else if (ch=='&')
			{
				xmlObj.mathFlag= true;
				String entity= "";
				for(int k=mpos; (ch=mmlexp.charAt(k))!=';'; k++,mpos++)
					entity+=ch;
				entity+=ch;
				//if(entity.indexOf("frown")!=-1)
				//	System.out.println("BEF->"+processStr);

				//Space removed after entity in math on 15-07-2013, feedback received on IEDEE_19 from customer
				//Discussed with Neeraj sir, Sumit Sridhar, Satya (SQA)
				processStr.append(xmlObj.replace(entity, xmlObj.mathEntityList)+" ");
				//processStr.append(xmlObj.replace(entity, xmlObj.mathEntityList));
				//if(entity.indexOf("frown")!=-1)
					//System.out.println("processStr "+processStr);
			}
			else
			{
				processStr.append(ch);
			}
		}
		return processStr.toString();
	}

	public String processMatrix(String mml)
	{
		String mmlexp = mml;
		StringBuffer processStr= new StringBuffer();
		String mtblTag= "";
		String colAligns="";
		String tempcolAligns="";//22-09-2011
		int countCol1=0;
		int countCol2=0;
		//System.out.println("Begin Processing Matrix");

		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				StringBuffer mmltmp = new StringBuffer();
				for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++, mpos++)
					tag+=ch;
				tag+=ch;
				//System.out.println("tag (1) : "+tag);
				if (tag.startsWith("<MML:MTABLE"))
				{

					boolean firstrow= true;
					while (!tag.equals("</MML:MTABLE>"))
					{
						ch= mmlexp.charAt(++mpos);
						//System.out.println("ch (1) : "+ch);
						if (ch=='<')
						{
							tag="";
//							tag+=ch;
							if(tag.equals("</MML:MTABLE>"))
								break;
							for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
								tag+=ch;
							tag+=ch;
							//System.out.println("tag (2) : "+tag);
							if (tag.startsWith("<MML:MTR"))
							{
								boolean firstcol= true;
								if (firstrow== false)
								{
									/*if(isScript)
										processStr.append("\\\\\r\n\\scriptstyle ");
									else*/
										processStr.append("\\\\\r\n");

								}
								/*else
								{
									if(isScript)
										processStr.append("\\scriptstyle ");
								}*/
								firstrow= false;
								String mtdalign="";
								colAligns="";
								countCol1=0;	
								while (!tag.equals("</MML:MTR>"))
								{
									
									ch= mmlexp.charAt(++mpos);
//									System.out.println("ch : "+ch);
									if (ch=='<')
									{
										tag="";
										for(int i=mpos; (ch=mmlexp.charAt(i))!='>'; i++,mpos++)
											tag+=ch;
										tag+=ch;
										//System.out.println("tag (3) : "+tag);
										if(tag.equals("</MML:MTABLE>"))
											break;
										if (tag.startsWith("<MML:MTD"))
										{
											++countCol1;//22-09-2011
											if (firstcol== false)
												processStr.append("<MTDSEP>");
											firstcol= false;
											mtdalign= xmlObj.getAttributeValue(tag, "COLUMNALIGN");
											//System.out.println ("mtdalign :"+mtdalign+" tag : "+tag+" colAligns: "+colAligns.length());
											if(colAligns.length ()>0)
												colAligns+="@{\\quad}";
											if(mtdalign.equals("RIGHT"))
											{
												colAligns+="r";
												processStr.append("\\hfill ");
											}
											else if(mtdalign.equals("LEFT"))
												colAligns+="l";
											else if(mtdalign.equals("CENTER"))
												colAligns+="c";
											else
												colAligns+="l";
											//System.out.println("colAligns===========>>"+colAligns);
										}
										else if (tag.equals("</MML:MTD>"))
										{
											if(mtdalign.equals("left"))
												processStr.append("\\hfill ");
										}
										else
										{
											if(!tag.equals("</MML:MTR>"))
												processStr.append(tag);
										}
									}
									else
									{
										processStr.append(ch);
									}
								}

								//System.out.println("countCol1==>>"+countCol1);
								//System.out.println("1111 countCol2==>>"+countCol2);
								if(countCol1>=countCol2)//22-09-2011
								{
									countCol2=countCol1;
									tempcolAligns=colAligns;
								}
								else
								{
									countCol1=countCol2;
									colAligns=tempcolAligns;
								}
								
							}
							else
							{
								if(!tag.equals("</MML:MTABLE>"))
									processStr.append(tag);
								//else
								//	processStr.append("\\end{array}");
							}
						}
						else
						{
							processStr.append(ch);
						}
					}
				}
				else
				{
					if(!tag.equals("</MML::MTABLE>"))
						processStr.append(tag);
				}
			}
			else
			{
				processStr.append(ch);
			}
		}
		String tempstr= new String();
		/*if(colAligns.length() > 0)
		{*/
			tempstr = "\\begin{array}{"+colAligns+"}"+processStr+"\\end{array}";
		/*}
		else
		{
			tempstr = "\\begin{eqnarray*}"+processStr+"\\end{eqnarray*}";
		}*/
		//System.out.println(tempstr);
	

//System.out.println("--------------> "+tempstr);
//try{System.in.read();}catch(Exception e){}
		return tempstr;
	}

	public String processMtable(String supStr)
	{
		System.out.println("Processing mtable"+supStr);
		String mmlexp= supStr;
		String mmlStr  = "";
		StringBuffer mmltmp = new StringBuffer();
		for(int mpos=0; mpos<mmlexp.length(); mpos++)
		{
			char ch= mmlexp.charAt(mpos);
			if (ch=='<')
			{
				String tag          = "";
				//StringBuffer mmltmp = new StringBuffer();
				mmltmp = new StringBuffer();
				int tagCnt          = 0;
				while (true)
				{
					ch= mmlexp.charAt(mpos++);
					if (ch=='<')
					{
						tag="";
						tag+=ch;
						for(; (ch=mmlexp.charAt(mpos))!='>'; mpos++)
							tag+=ch;
						tag+=ch;
						//System.out.println(tag+"\t"+tagCnt);
						if (tag.equals("<MML:MTABLE>"))
						{
//							System.out.println("<MML:MTABLE> : "+tagCnt);
							tagCnt++;
							if(tagCnt!=1)
							{
								mmltmp = new StringBuffer();
								mmltmp.append(tag);
							}
							else
								tagCnt          = 0;
						}
						else if (tag.equals("</MML:MTABLE>"))
						{
							tagCnt--;
//							System.out.println("</MML:MTABLE> : "+tagCnt);
							if (tagCnt==0)//0
							{
								mmltmp.append(tag);
								break;
							}
							else
								mmltmp.append(tag);
						}
						else
						{
							mmltmp.append(tag);
						}
					}
					else if(ch!='>')
					{
						mmltmp.append(ch);
					}
				}
				mmlStr+= mmltmp;
				if(tagCnt==0)//0
					break;
			}
		}
		//System.out.println(mmltmp);
		return mmltmp.toString();
	}
	public String processMultiscript(String tmpSubStr) throws IOException
	{
		//System.out.println("K--->"+tmpSubStr);
		
		String StroingTemp=tmpSubStr;
		String temp = new String();
		String mulText = new String();
		String prescript = new String();
		String base = new String();
		String tag = new String();
		String s1 = new String();
		String s2 = new String();
		String s3 = new String();
		int x = tmpSubStr.lastIndexOf("<MML:MMULTISCRIPTS>");
		temp = tmpSubStr.substring(x+"<MML:MMULTISCRIPTS>".length());
		//System.out.println("\tmpSubStr--->"+tmpSubStr);
		//System.out.println("\n\nTEMP--->"+temp);
		//System.in.read();
		tmpSubStr = tmpSubStr.substring(0,x);
		x = temp.indexOf("</MML:MMULTISCRIPTS>");
		//System.out.println("\nXXXx	-->"+x);
		
		mulText = temp.substring(0,x);		
		
		temp = temp.substring(x+"</MML:MMULTISCRIPTS>".length());
		//System.out.println("\n \n 1 mulText -- > "+mulText);
		//System.out.println("\n \n 1 temp -- > "+temp);
		//System.in.read();
		if((mulText.indexOf("<MML:MPRESCRIPTS/>"))!=-1)
		{
			x =mulText.indexOf("<MML:MPRESCRIPTS/>");
			prescript = mulText.substring(x+"<MML:MPRESCRIPTS/>".length());
		}
		//System.out.println("\n \n 1 prescript -- > "+prescript);
		//System.in.read();
		mulText = mulText.substring(0,x);	
		//System.out.println("\nmulText-->"+mulText);
		//System.in.read();
		x =mulText.indexOf(">");
		base = mulText.substring(1,x+1);
		
		base="</"+base;
		//System.out.println("\nBASE-->"+base);
		
		boolean tagFlag=false;
		
		if(base.indexOf(" ")!=-1)
		{
			tagFlag=true;
			x=base.indexOf(" ");
			base=base.substring(0, x);
			base=base+">";
		}
		if(base.equals("</MML:MROW>"))
		{
			x =mulText.lastIndexOf(base);
			
		}
		else if(base.equals("</MML:MSUP>"))
		{
			x =mulText.lastIndexOf(base);
			
		}
		else if(base.equals("</MML:MSTYLE>"))
		{
			x =mulText.lastIndexOf("</MML:MSTYLE>");
			if(x == -1)
			{
				x =mulText.lastIndexOf("</MS>");
				base = "</MS>";
			}
		}
		else if(tagFlag==true)
		{
			x =mulText.lastIndexOf(base);
		}
		else
		{
			
			x =mulText.indexOf(base);
		}

		//System.out.println("\nBASE-->"+base);
		//System.out.println("\nmulText-->"+mulText);
		//System.out.println("\nx	-->"+x);
		
		if(x != -1)
		{
			base=mulText.substring(0,x)+base;
			
			mulText=mulText.substring(base.length());
			//System.out.println("\n 2 mulText-->"+mulText);
			//System.in.read();
		}
		if(mulText.length()>0)
		{
			
			x = returnGroupIndex(mulText);
			tag = mulText.substring(0,x);
			mulText = mulText.substring(x);
			if(tag.equals("<MML:NONE/>")){}
			else
			{
				base=base+"_{"+tag+"}";
			}
			if(mulText.length()>0)
			{
				x = returnGroupIndex(mulText);
				tag = mulText.substring(0,x);
				
				mulText = mulText.substring(x);
				if(tag.equals("<MML:NONE/>")){}
				else
				{
					base=base+"^{"+tag+"}";
				}
			}
	//	System.out.println("\n \n tag -- > "+tag+"\n\n mulText"+mulText+"\n\n base==>"+base);
		//System.in.read();
		}
		
		mulText = prescript;
		prescript = base;
		base="";
		//System.out.println(mulText);
		
		if(mulText.length()>0)
		{
			x = returnGroupIndex(mulText);
			tag = mulText.substring(0,x);
			mulText = mulText.substring(x);
			base="{}";
			if(tag.equals("<MML:NONE/>")){}
			else
			{
				base=base+"_{"+tag+"}";
				
			}
			if(mulText.length()>0)
			{
				x = returnGroupIndex(mulText);
				tag = mulText.substring(0,x);
				mulText = mulText.substring(x);
				if(tag.equals("<MML:NONE/>")){}
				else
				{
					base=base+"^{"+tag+"}";
				}
			}
		}
		base+=prescript;
		tmpSubStr+=base+temp;
		//System.out.println(" original StroingTemp==>   "+StroingTemp);
		//System.out.println("\n\n tmpSubStr==>   "+tmpSubStr);
		//System.in.read();
		return tmpSubStr;
	}

	int returnGroupIndex(String mulText)
	{
		int x=0;
		String s1 = new String();
		String s2 = new String();
		String tag = new String();
		//System.out.println("1  sssssssss mulText "+mulText);
		
		x =mulText.indexOf(">");
		tag = mulText.substring(0, x+1);	
		//System.out.println("tag "+tag);
		/*if(tag.equals("<MML:NONE/><MML:NONE/>"))
		{
		}
		else*/ if(tag.equals("<MML:NONE/>"))
		{
			mulText = mulText.substring(x+1);
			x =x+1;
			
		}
		else if(tag.equals("<MML:MN>")||tag.equals("<MML:MTEXT>")||tag.equals("<MML:MI>"))
		{
			tag= tag.substring(1);
			tag = "</"+tag;
			x =mulText.indexOf(tag);
			x = x+tag.length();
		}
		else
		{
			//System.out.println(tag);
			s1 = xmlObj.getEndtag(tag);
			if(s1.equals("</MML:MSTYLE>"))
				s1 = "</MS>";
			
			/*if(s1.equals("</MML:MROW>"))
				s1 = "</MS>";
			*/
			if(tag.indexOf(" ")!=-1)
			{
				tag=tag.substring(0,tag.indexOf(" "));
			}
			x=0;
			int y=tag.length();
			while(true)
			{
				//System.out.println(mulText);
				x=mulText.indexOf(s1,y);
				//System.out.println(s1+" "+y+" "+x);
				
				
//21/03/2008				System.out.println("mulText "+mulText+"\ns1.length() : "+s1.length()+"\n y : "+y+"\n x:  "+x);
					if(x!=-1)
					s2=mulText.substring(y,x+s1.length());
					if(s2.indexOf(tag) !=-1)
					{
						y = x+s1.length();
					}
					else
					{
						x = x+s1.length();
						break;
					}
				
			}
		}
		return x;
	}
	
	public String replaceString(String mainStr,String searchStr, String replaceStr) {
//		System.out.println("Running replaceStr() method.");
		//Use of this method is to replace the piece of text in any string. 
		String newStr = mainStr;
		if (mainStr == null){
			System.out.println("String is null, not able to replace content");
			return mainStr;
		}
		int i = mainStr.indexOf(searchStr);
		int i2 = i + searchStr.length();
		if (i<0){
			System.out.println("Content \""+searchStr+"\" not found.");
			return mainStr;
		}
		if (i2>=mainStr.length()) {
			newStr = mainStr.substring(0,i)+replaceStr;
		} else {
			newStr = mainStr.substring(0,i)+replaceStr+mainStr.substring(i2,mainStr.length());
		}
//		System.out.println("Finished replaceStr() method.");
		return newStr;
	}
}