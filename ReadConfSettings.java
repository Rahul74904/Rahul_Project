package tp.xt;
import java.io.*;
import java.util.*;
class ReadConfSettings
{
	private static String confFileStr;	
	
	ReadConfSettings(String cfg)throws IOException
	{
		confFileStr=cfg;	
		//System.out.println ("confFileStr :"+confFileStr);
	}
	
	public Hashtable getSettings()throws IOException
	{
		String lineStr="";
		Hashtable conf=new Hashtable();
		RandomAccessFile confFile;
		confFile=new RandomAccessFile(confFileStr, "r");
		//System.out.println (confFileStr);
		while ((lineStr=confFile.readLine())!=null)
		{
			  lineStr= lineStr.trim();			
			//lineStr= lineStr.toUpperCase().trim();			
			//System.out.println("lineStr : "+lineStr);
			if (lineStr.startsWith("<p-"))
			{
				//System.out.println("lineStr : "+lineStr);
				String prop=lineStr.substring(lineStr.indexOf("<p-")+3, lineStr.indexOf(">"));	
				String propVal=lineStr.substring(lineStr.indexOf(">")+1, lineStr.indexOf("</"));
				conf.put(prop, propVal);
			}
		}
		confFile.close();
		return conf;
	}
}
