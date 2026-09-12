package tp.xt;
import java.util.*;
import java.io.*;

class TiffInfo
{
	String tifinfoLoc;
	TiffInfo(String tifinfoLoc)
	{
		this.tifinfoLoc= tifinfoLoc;
		//System.out.println (tifinfoLoc);
	}

	TiffInfo()
	{
	}

	public Hashtable getInfo()throws IOException
	{
		Hashtable figProp= new Hashtable();
		try
		{
			RandomAccessFile fin= new RandomAccessFile(tifinfoLoc, "r");
			String lineStr="";
			while ((lineStr=fin.readLine())!=null)
			{
				//System.out.println (lineStr);
				if (lineStr.startsWith("Analysis of file "))
				{
					String figNo= lineStr.substring(lineStr.indexOf("Analysis of file ")+1+("Analysis of file ").length(), lineStr.indexOf(".TIF"));
					lineStr=fin.readLine();
					lineStr=fin.readLine();
					int inda= lineStr.indexOf("Width: ")+("Width: ").length();
					String width=lineStr.substring(inda, lineStr.indexOf(" ", inda));
					
					inda= lineStr.indexOf("Height: ")+("Height: ").length();

					String height=lineStr.substring(inda, lineStr.indexOf(" ", inda));				
					//System.out.println (width+"in/"+height+"in");
					figProp.put(figNo, width+"in/"+height+"in");
				}
			}
		}
		catch(Exception exp)
		{
			//System.out.println(exp.getMessage());
		}
		return figProp;
	}

	public static void main(String args[])throws Exception
	{
		(new TiffInfo()).getInfo();
	}
}
