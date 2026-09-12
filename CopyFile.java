package tp.xt;
import java.util.*;
import java.io.*;
public class CopyFile
{
	String md5Path="";
	 ResourceBundle rb=null;
	 public CopyFile()
	 {
	 	rb=ResourceBundle.getBundle("xt");
		md5Path=rb.getString("MD5Path");
	 }
/*	public static void main (String [] argc)	
	{
		CopyFile cf=new CopyFile();
		String source="tx1.xml";
		String distinationFile="Check_tx1.xml";
		cf.checkForFile(source, distinationFile);
		cf.deleteFile(distinationFile);
	}
	*/
	public void deleteFile(String oldfilename)
	{
		//String user_path=System.getProperty("user.dir");
		String user_path="";
		try
		{
			user_path=new File(oldfilename).getCanonicalPath();
			user_path=user_path.substring(0,user_path.lastIndexOf("\\"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("user_path : "+user_path);
		String path_local=user_path+"\\"+oldfilename;
		if(new File(path_local).exists())
		{
			if(!new File(path_local).delete())
			{
				new File(path_local).deleteOnExit();
			}

		}
	
	}
	public void checkForFile(String newfilename,String oldfilename)
	{
		String npath=newfilename;
		String opath=oldfilename;
		String user_path="";
		//String user_path=System.getProperty("user.dir");
		try
		{
			user_path=new File(newfilename).getCanonicalPath();
			user_path=user_path.substring(0,user_path.lastIndexOf("\\"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		System.out.println("user_path : "+user_path);
		String path_local=user_path+"\\"+oldfilename;
		if(!aftercopy(new File(user_path+"\\"+npath),new File(user_path+"\\"+opath)))
    		{
    			System.out.println("File not copied.....");
       		}
	}
	
	public boolean aftercopy(File inputpath,File outputpath)
	{
		
		while(!copyfile(inputpath,outputpath))
		{
			System.out.println("There is problem of copy inside folder... ");
			return false;
		}
		return true;
	}
	public boolean copyfile(File inputpath,File outputpath)
 	{ 
		
		try{
		BufferedInputStream ibr=new BufferedInputStream(new FileInputStream(inputpath));
		BufferedOutputStream obr=new BufferedOutputStream(new FileOutputStream(outputpath));
		String SourceCheckSum="";
		String DestinationCheckSum="";
		byte[] buffer=null;
		buffer= new byte[1024*10];
	 	int len;
	 	System.out.println(" Going to Copy file");
	 	 	while((len=ibr.read(buffer))>=0){
	    	    obr.write(buffer, 0, len);
	 	 	  }
	 	 	System.out.println(" File copied ");	
	        ibr.close();
	    	obr.close();
	    	
	     //setting the last modified as it is.	
	    
	      outputpath.setLastModified(inputpath.lastModified());
	    	
	  	  SourceCheckSum=getchkSum(inputpath.toString());
		  DestinationCheckSum=getchkSum(outputpath.toString());
		    if(!SourceCheckSum.equals(DestinationCheckSum))
		    {
		       return false;	 
		    }
		    else
		    {
		    	return true;
		    }
		} catch(Exception ex){
		    return false;	
		}
	}
     public String getchkSum(String xpath)throws IOException
	{
		String outputString="";
		String OsverC="";
		String Osver="";
		Process p=null;
		try{
		Osver=System.getProperty("os.name");
		
		if(Osver.equalsIgnoreCase("Windows 98"))
			OsverC="start command";
			else
				OsverC="cmd.exe";
	    
		p=Runtime.getRuntime().exec(OsverC+" /c "+md5Path+" -n "+"\""+xpath+"\"");			
	    p.waitFor();
		InputStream in = p.getInputStream();
		BufferedReader bf=new BufferedReader(new InputStreamReader(in));
		outputString=	bf.readLine();
	
		System.out.println("**********path= "+xpath);
	
		System.out.println("Checksum= "+outputString);
		
		}catch(InterruptedException esp){
		System.out.println(esp);
		}
	      return outputString;
	}
}