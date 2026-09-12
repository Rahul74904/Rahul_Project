package tp.xt;

//CheckMaterialForLaTeX

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.OutputStream;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileWriter;

public class CheckMaterialForLaTeX
{
	private String mat_inpt="";
	
	CheckMaterialForLaTeX()
	{
		ResourceBundle rbs=ResourceBundle.getBundle("xt");
		mat_inpt=rbs.getString("MATERIAL-PATH");
		//System.out.println("mat_inpt-->>"+mat_inpt);
	}
	
	public static void main(String[]argc)throws Exception
	{
		CheckMaterialForLaTeX chml=new CheckMaterialForLaTeX();
		
		if(chml.extract("FLUID","8934","S100"))
		{
			System.out.println("TeX rout Article.");
		}
		else
		{
			System.out.println("Not TeX rout Article.");
		}
		
	}
	public boolean extract(String jid,String aid, String stage)throws Exception
    {
    	boolean exists=false;
    	File f=new File(mat_inpt+jid+"\\"+aid+"\\"+stage);
    	File list1[]=f.listFiles(
    		new FilenameFilter(){
    				public boolean accept(File dir, String name) {
    					return name.toLowerCase().endsWith("s100.zip");
    						
    				}
    		}
    		);
    		if(list1!=null)
    		System.out.println("---->>>"+list1.length);
    		
    	if(list1==null||list1.length==0)
    	{
			
			String folder=GetLatestFolder(jid,aid,stage);
			System.out.println("folder::"+folder);
			String path="\\\\fms\\d\\FMS\\centralized_server\\Elsevier\\JOURNAL\\"+jid+"\\"+aid+"\\Originals\\"+folder;
			System.out.println("FMS if Material path: "+path);
    		f=new File(path);
    	}
    	else
    	{
    		System.out.println("Else Matrial path: "+mat_inpt+jid+"\\"+aid+"\\"+stage);
    	}
    	File list[]=f.listFiles(
    		
    		new FilenameFilter(){
    				public boolean accept(File dir, String name) {
    					return name.toLowerCase().endsWith("s100.zip");
    						
    				}
    		}
    		);
    	if(list==null||list.length==0){
    		return false;
    	}
    	System.out.println("Jid "+jid+" Aid "+aid);
    	if(extract(list[0]))
    	{
    		exists=true;	
    	}
    	else
    	{
    		exists=false;
    	}
    	
    	return exists;
    	
    }
	private boolean extract(File f)throws Exception
	{
    	boolean exists=false;	
    	if(!f.exists()){
      		return false;	
    	}
    	FileInputStream inputstream=new FileInputStream(f);
    	ZipInputStream in = new ZipInputStream(inputstream);
    	ZipEntry entry=null;
       	while((entry=in.getNextEntry())!=null)
       	{
    		String entryname=entry.getName();
    		System.out.println("entry name "+entryname);
    		if(entryname.toLowerCase().endsWith(".tex"))
    		{
    			exists=true;
    			in.close();
        		inputstream.close();
        		break;
    		}
    	}
    	return exists;
 	}   
    
    
    public String GetLatestFolder(String jid,String aid,String stage)
	{
		String path="\\\\fms\\d\\FMS\\centralized_server\\Elsevier\\JOURNAL\\"+jid+"\\"+aid+"\\Originals\\";
		System.out.println("path : "+path);
		File file=new File(path);
		String temp="0";
		if(!file.exists())
		{
			System.out.println("[ERROR] : Material not found in fms");
			return temp;
		}
		File[] list=file.listFiles();
		if(list.length>0)
		{
			
			for(int i=0;i<list.length;i++)
			{
				String folder=list[i].getName();
				File list1[]=list[i].listFiles(
    		
	    		new FilenameFilter(){
	    				public boolean accept(File dir, String name) {
	    					return name.toLowerCase().endsWith("s100.zip");
	    						
	    				}
	    		}
	    		);
				
				if(list1.length>0)
				{
					temp=folder;
					break;
				}
			}
		}
		return temp;
	}
	
}