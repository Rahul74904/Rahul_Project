//XTLogger
package tp.xt;

import java.io.*;
import java.util.*;

public class XTLogger
{
	static RandomAccessFile consoleFile;
	static File console;
	private static XTLogger myInstance;

 	private XTLogger()
 	{
 		try
 		{
 			ResourceBundle rb=ResourceBundle.getBundle("xt");
// 			String logPath=rb.getString("logPath");
			String logPath=System.getProperty("user.dir");
 			Calendar currentDateTime=Calendar.getInstance();
 			//System.out.println("LOG-------->"+logPath);
 			
 			File f=new File(logPath);
 			if(!f.exists())
 			{
 				f.mkdirs();
 			}
 			//console=new File(logPath+"\\Log_" + currentDateTime.get(Calendar.DAY_OF_MONTH)+"-"+ (currentDateTime.get(Calendar.MONTH)+1)+"-"+ currentDateTime.get(Calendar.YEAR)+"-"+ currentDateTime.get(Calendar.HOUR)+"-"+ currentDateTime.get(Calendar.MINUTE)+"-"+ currentDateTime.get(Calendar.AM_PM)+".txt");
			console=new File(logPath+"\\Log.txt");
			consoleFile=new RandomAccessFile(console,"rw");
		}
		catch(Exception e)
		{
			System.out.println("Exception in calling Logger Constructor..");
			e.printStackTrace();
		}
	}
				
	public static synchronized void info(String Mesg)
	{
		try
		{	
		//System.out.println("Calling Info.......");	
			Calendar currentDateTime=Calendar.getInstance();
			String dd=new String(currentDateTime.get(Calendar.DAY_OF_MONTH)+"-"+ (currentDateTime.get(Calendar.MONTH)+1)+"-"+ currentDateTime.get(Calendar.YEAR) +"-"+ currentDateTime.get(Calendar.HOUR)+"-"+ currentDateTime.get(Calendar.MINUTE)+"-"+ currentDateTime.get(Calendar.AM_PM));	
			consoleFile.writeBytes("["+dd+"] "+Mesg+"\n");
			//consoleFile.writeBytes(Mesg+"\n");
		}
		catch(Exception e)
		{
			System.out.println("Exception while calling info()-> "+e);	
			e.printStackTrace();
		}
	}
			
    public static XTLogger getInstance() 
    {     	
    	if(myInstance == null)
        {
          //System.out.println("Creating single instance..."); 
        	myInstance = new XTLogger();
        }
        
        return myInstance;
    } 

}