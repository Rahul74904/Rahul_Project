package tp.xt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.regex.*;


public class HyphenPT {

	static String filepath = "D:\\Projects\\XT-ELSEVIER\\classes\\tx1.xml";
	static String filepath1 = "D:\\Projects\\XT-ELSEVIER\\classes\\tx1new.xml";

	public static void main(String[] args) {
		System.out.println("Portuguese Hyphen process running...");
		HyphenPT hpt = new HyphenPT();
		hpt.checkPThyphen(filepath);
	}

	public String checkPThyphen(String path){
		/*
		File file = new File(path);
		RandomAccessFile raf=null;
		try{
			raf= new RandomAccessFile(file, "rw");
			String text="";
			String xml="";
			while((text = raf.readLine()) != null)				
			{
				xml=xml+text+"\n";
			}
			//System.out.println("File====>"+sb.toString());
			HyphenPT hpt = new HyphenPT();
			xml=hpt.changeHyphenPT(xml);
			//System.out.println("xml======>"+xml);
			System.out.println("Portuguese Hyphen process finished...");
			raf.close();
		
			File filenew = new File(filepath);
			RandomAccessFile raf1= new RandomAccessFile(filenew, "rw");
			raf1.writeBytes(xml);
			raf1.close();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
		finally{
			if(raf != null)
				try {
					raf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}*/
		System.out.println("Checking hyphen...");
		String xml;
		xml=changeHyphenPT(path);
		if(xml==null)
		{
			System.out.println("There is some problem in HyphenPT process.");
			return null;
		}
		return xml;
	}

	public String changeHyphenPT(String sbr){
		
		String content="";
		content=sbr;
		//
		//content=content.replaceAll("\\(","&#xff08;");
		//content=content.replaceAll("\\)","&#xff09;");
		//
		boolean roottagPT=false;
		Pattern p1 = Pattern.compile("<([^<>/ ]+)(>| ([^<>]+)>)",Pattern.DOTALL);
		Matcher m1 = p1.matcher(content);
		if(content.indexOf("&hyphen;")!=-1)
		{
			if(content.indexOf("xml:lang=\"pt\"")!=-1)
			{
				while(m1.find())
				{
					String tag=m1.group(1);
					String att=m1.group(2);
					//System.out.println("<"+m1.group(1)+">");
					if(att.length()>1)
					{
						//System.out.println("ATT of "+tag+"===>"+att);
						if(att.indexOf("xml:lang")!=-1)
						{
							//System.out.println("<"+tag+">(.*?)</"+tag+">");
							Pattern p2 = Pattern.compile("<"+tag+"([^<>]+)xml:lang=\"([^\"]+)\"(>| ([^<>]*?)>)(.*?)</"+tag+">",Pattern.DOTALL);
							Matcher m2 = p2.matcher(content);
							while(m2.find())
							{
								//System.out.println("ATT of "+tag+"===>"+att);
								//System.out.println("TAG==>"+"<"+tag+m2.group(1)+"xml:lang=\""+m2.group(2)+"\""+m2.group(3)+m2.group(5)+"</"+tag+">");
								String langtext = "";
								if(m2.group(4)==null)
									langtext="<"+tag+m2.group(1)+"xml:lang=\""+m2.group(2)+"\""+m2.group(3)+m2.group(5)+"</"+tag+">";
								else
									langtext="<"+tag+m2.group(1)+"xml:lang=\""+m2.group(2)+"\""+m2.group(3)+m2.group(5)+"</"+tag+">";
	
								if((tag.equalsIgnoreCase("article")||tag.equalsIgnoreCase("simple-article")||tag.equalsIgnoreCase("book-review")||tag.equalsIgnoreCase("exam")) && m2.group(2).equalsIgnoreCase("pt"))
								{
									//System.out.println("langtext1===>"+langtext);
									roottagPT=true;
								}
								if(roottagPT)
								{
									if(!m2.group(2).equalsIgnoreCase("PT"))
									{
										if(langtext.indexOf("&hyphen;")!=-1)
										{
											String langtextnew="";
											langtextnew=langtext;
											langtextnew=langtextnew.replaceAll("&hyphen;", "-");
//											content=content.replaceAll(langtext, langtextnew);
//											content=content.replaceAll(Pattern.quote(langtext), langtextnew);//Updated for BJAN_809_S200//
//											content=content.replaceAll(langtext, langtextnew);//Revert BJAN_809_S200 condition for REGE_37//
											content=content.replaceAll(Pattern.quote(langtext), langtextnew);//Added Pattern.quote on 04-06-2019 
										}
									}
									else
									{
										if(langtext.indexOf("&hyphen;")!=-1)
										{
											boolean ignoretag=false;
											try {
												ignoretag=ignoreHyphen(tag);
											} catch (IOException e) {
												e.printStackTrace();
											}
											if(ignoretag)
											{												
												String langtextnew="";
												langtextnew=langtext;
												langtextnew=langtextnew.replaceAll("&hyphen;", "-");
												content=content.replaceAll(Pattern.quote(langtext), langtextnew);
											}
										}
									}
								}
								else
								{
									if(m2.group(2).equalsIgnoreCase("PT"))
									{
										if(langtext.indexOf("&hyphen;")!=-1)
										{
											boolean ignoretag=false;
											try {
												ignoretag=ignoreHyphen(tag);
											} catch (IOException e) {
												e.printStackTrace();
											}
											if(ignoretag)
											{
												String langtextnew="";
												langtextnew=langtext;
												langtextnew=langtextnew.replaceAll("&hyphen;", "-");
												content=content.replaceAll(Pattern.quote(langtext), langtextnew);
											}
											else
											{
												String langtextnew="";
												langtextnew=langtext;
												langtextnew=langtextnew.replaceAll("&hyphen;", "&hyphenpt;");
												content=content.replaceAll(Pattern.quote(langtext), langtextnew);
											}
										}
									}
								}
							}
						}
						else
						{							
							Pattern p3 = Pattern.compile("<"+tag+"(>| ([^<>]*?)>)(.*?)</"+tag+">",Pattern.DOTALL);
							Matcher m3 = p3.matcher(content);
							while(m3.find())
							{
								String langtext = "";
								langtext="<"+tag+m3.group(1)+m3.group(3)+"</"+tag+">";
								//System.out.println("Non Language Elements==>"+langtext);
								if(langtext.indexOf("&hyphen;")!=-1)
								{
									boolean ignoretag=false;
									try {
										ignoretag=ignoreHyphen(tag);
									} catch (IOException e) {
										e.printStackTrace();
									}
									if(roottagPT)
									{
										if(ignoretag)
										{
											//System.out.println("Ignoring tag \""+tag+"\"");
											String langtextnew="";
											langtextnew=langtext;
											langtextnew=langtextnew.replaceAll("&hyphen;", "-");
											content=content.replaceAll(Pattern.quote(langtext), langtextnew);
										}
									}
									else
									{
										if(ignoretag)
										{
											//System.out.println("Ignoring tag \""+tag+"\"");
											String langtextnew="";
											langtextnew=langtext;
											langtextnew=langtextnew.replaceAll("&hyphen;", "-");
											content=content.replaceAll(Pattern.quote(langtext), langtextnew);
										}
									}
								}
							}
						}
					}
					else
					{
						//System.out.println("<"+tag+">(.*?)</"+tag+">");
						Pattern p4 = Pattern.compile("<"+tag+">(.*?)</"+tag+">",Pattern.DOTALL);
						Matcher m4 = p4.matcher(content);
						while(m4.find())
						{
							String text = "";
							text="<"+tag+">"+m4.group(1)+"</"+tag+">";
							//System.out.println("Non Language Elements==>"+langtext);
							if(text.indexOf("&hyphen;")!=-1)
							{
								boolean ignoretag=false;
								try {
									ignoretag=ignoreHyphen(tag);
								} catch (IOException e) {
									e.printStackTrace();
								}
								if(roottagPT)
								{
									if(ignoretag)
									{
										//System.out.println("Ignoring tag \""+tag+"\"");
										String textnew="";
										textnew=text;
										textnew=textnew.replaceAll("&hyphen;", "-");
										content=content.replaceAll(Pattern.quote(text), textnew);
									}
								}
								else
								{
									if(ignoretag)
									{
										//System.out.println("Ignoring tag \""+tag+"\"");
										String textnew="";
										textnew=text;
										textnew=textnew.replaceAll("&hyphen;", "-");
										content=content.replaceAll(Pattern.quote(text), textnew);
									}
								}
							}
						}				
					}
				}
				if(roottagPT)
				{
					content=content.replaceAll("&hyphen;", "&hyphenpt;");
				}
				else
				{
					content=content.replaceAll("&hyphen;", "-");
				}
			//Check number before/after &hyphenpt
				Pattern p4 = Pattern.compile("(&hyphenpt;([0-9])|([0-9])&hyphenpt;)",Pattern.DOTALL);
				Matcher m4 = p4.matcher(content);
				if(m4.find())
				{
					content=content.replaceAll("&hyphenpt;([0-9])", "-$1");
					content=content.replaceAll("([0-9])&hyphenpt;", "$1-");
					content=content.replaceAll("&hyphenpt; ", "- ");
					content=content.replaceAll(" &hyphenpt;", " -");
				}
			}
		}
		return content;
	}

	public boolean ignoreHyphen(String checktag) throws IOException{
		boolean tagexist=false;
		String text="";
		File file = new File("V:\\database\\IgnoreHyphenPT.dbf");
		try {
			RandomAccessFile raf2= new RandomAccessFile(file, "r");
			while((text = raf2.readLine()) != null)				
			{
				if(text.equalsIgnoreCase(checktag))
				{
					tagexist=true;
				}
				//System.out.println("text===>"+text+" tag===>"+checktag+" and tagexist===>"+tagexist);
			}
			raf2.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tagexist;
	}
}
