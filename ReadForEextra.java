//ReadForEextra
package tp.xt;

import java.io.*;
import java.sql.*;
import javax.swing.*;
import java.util.Hashtable;
import java.util.Vector;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.*;
public class ReadForEextra
{
	String jid="";
	String aid="";
	String stage="";
	public static String label="";
	String odrPath="";
	String odrPathForFMS="";
	ResourceBundle rbs=null;
	Hashtable ht1=new Hashtable();
	XT xt=new XT();
	tp.xt.XTLogger xt_log=tp.xt.XTLogger.getInstance();
	String itemGroupValue = "";
	
	
	public static void main(String []a)
	{
		ReadForEextra re= new ReadForEextra("NEURAD","23","S100");
		Hashtable h=new Hashtable();
		//h=re.getStage("NSL","27186");
		//System.out.println("----"+h);
		System.out.println(re.readOrderFile_NEURAD_AND_PUROL());
	}
	public ReadForEextra(String jd, String ad, String stg)
	{
		jid=jd;
		aid=ad;
		stage=stg;
		//System.out.println("------------------[serverstatus]------------------->"+xt.serverstatus);
		if(aid.indexOf(".",0)!=-1)
		{
			aid=aid.substring(0,aid.indexOf(".",0));
		}
		rbs=ResourceBundle.getBundle("xt");
		odrPath=rbs.getString("ORDERPATH");
		odrPathForFMS=rbs.getString("ORDERPATHFORFMS");
		//System.out.println("\nJID : "+jid+"\nAID : "+aid+"\nSTAGE : "+stage);
		//System.out.println("odrPath : "+odrPath);
		//System.out.println("odrPathForFMS : "+odrPathForFMS);
	}

public boolean readOrderFile_NEURAD_AND_PUROL()
	{
		RandomAccessFile raf=null;
		String remarks="";
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 1");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab+"  "+this.jid);
							//lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("Numéro spécial congrès SFNR 2012"))&&this.jid.equals("NEURAD"))
							{
								//System.out.println("I am here");
								 return true;
							}
							else if((lab.toLowerCase().indexOf("purol pp",0)!=-1)&&this.jid.equals("PUROL"))
							{
								 return true;
							}
							else if(this.jid.equals("NEURAD"))
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				if(this.jid.equals("PUROL"))
				{
					if(sb.indexOf("<item-remarks>")!= -1)
					{
						int i=sb.indexOf("<item-remarks>",0);
						int n=0;
						String lab="";
						if(i!=-1)
						{
							n=sb.indexOf("</item-remarks>");
							if(n!=-1)
							{
								lab=sb.substring(i+"<item-remarks>".length(),n);
								//System.out.println("lab : "+lab);
								lab=lab.toLowerCase();
								if((lab.indexOf("purol pp")!=-1))
								{
									 return true;
								}
								else
								{
									return false;
								}
							}
						}
						//System.out.println("Found e-extra");
						
					}
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							remarks="";
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 2");
		    			e.printStackTrace();
		    			 return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab);
							//lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("Numéro spécial congrès SFNR 2012"))&&this.jid.equals("NEURAD"))
							{
								//System.out.println("I am here");
								 return true;
							}
							else if((lab.toLowerCase().indexOf("purol pp",0)!=-1)&&this.jid.equals("PUROL"))
							{
								 return true;
							}
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else if(this.jid.equals("PUROL"))
				{
					if(sb.indexOf("<item-remarks>")!= -1)
					{
						int i=sb.indexOf("<item-remarks>",0);
						int n=0;
						String lab="";
						if(i!=-1)
						{
							n=sb.indexOf("</item-remarks>");
							if(n!=-1)
							{
								lab=sb.substring(i+"<item-remarks>".length(),n);
								//System.out.println("lab : "+lab);
								lab=lab.toLowerCase();
								if((lab.indexOf("purol pp")!=-1))
								{
									 return true;
								}
								else
								{
									return false;
								}
							}
						}
						//System.out.println("Found e-extra");
						
					}
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		}
		return false;
	}

//***************************[27-01-2012]************************
public boolean readOrderFileCME()
	{
		RandomAccessFile raf=null;
		String remarks="";
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 3");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("CME Papers")))
							{
								 return true;
							}
							
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							remarks="";
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 4");
		    			e.printStackTrace();
		    			 return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("CME Papers")))
							{
								 return true;
							}
							
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		}
		return false;
	}

//***************************[27-01-2012]************************


//**************************************************************************
public boolean readOrderFileForOBSForYBJOM()
	{
		RandomAccessFile raf=null;
		String remarks="";
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 5");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<section>")!= -1)
				{
					int i=sb.indexOf("<section>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</section>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<section>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("OBS")))
							{
								 return true;
							}
							
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							remarks="";
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 6");
		    			e.printStackTrace();
		    			 return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<section>")!= -1)
				{
					int i=sb.indexOf("<section>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</section>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<section>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.equalsIgnoreCase("OBS")))
							{
								 return true;
							}
							
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		}
		return false;
	}
//***************************************************************************
public String readOrderFileForDAD()
	{
		RandomAccessFile raf=null;
		String remarks="";
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
				/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 7");
	    			e.printStackTrace();
	    			return remarks="";
	    		}
	    		if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i=sb.indexOf("<item-remarks>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-remarks>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-remarks>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.indexOf("nws 2 column format")!=-1))
							{
								 remarks="two column";
							}
							else if((lab.indexOf("nws 3 column format")!=-1))
							{
								 remarks="three column";;
							}
							else
							{
								remarks="";
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					remarks="";
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							remarks="";
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 8");
		    			e.printStackTrace();
		    			 remarks="";
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-remarks>")!= -1)
			{
				int i=sb.indexOf("<item-remarks>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</item-remarks>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<item-remarks>".length(),n);
						//System.out.println("lab "+lab);
						//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.indexOf("nws 2 column format")!=-1))
							{
								 remarks="two column";
							}
							else if((lab.indexOf("nws 3 column format")!=-1))
							{
								 remarks="three column";;
							}
							else
							{
								remarks="";
							}
					}
				}
				//System.out.println("Found e-extra");
				
			}
			else
			{
				//System.out.println("Not found e-extra");
				remarks="";
			}
		}
		return remarks;
	}
//***************************************************************************
public boolean readOrderFileForFUSPRU()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 9");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<section>")!= -1)
				{
					int i=sb.indexOf("<section>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</section>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<section>".length(),n);
							//System.out.println("lab : "+lab);
							//lab=lab.toLowerCase();
							if((lab.equals("DAF")))
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 10");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<section>")!= -1)
			{
				int i=sb.indexOf("<section>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</section>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<section>".length(),n);
						//System.out.println("lab "+lab);
						//lab=lab.toLowerCase();
						
							if((lab.equals("DAF")))
							{
								return true;
							}
							else
							{
								return false;
							}
					}
				}
				//System.out.println("Found e-extra");
				
			}
			else
			{
				//System.out.println("Not found e-extra");
				return false;
			}
		}
		return false;
	}
//***********************************************************************************
//**************************************************************************


public boolean readOrderFileForNSL()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		boolean check_special_issue=false;
		boolean check_s_i=false;
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			  /*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
					//System.out.println(sb);
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 11");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i1=sb.indexOf("<item-remarks>",0);
					int n1=0;
					String lab1="";
					if(i1!=-1)
					{
						n1=sb.indexOf("</item-remarks>");
						if(n1!=-1)
						{
							lab1=sb.substring(i1+"<item-remarks>".length(),n1);
						//	System.out.println("lab : "+lab1);
							lab1=lab1.toLowerCase();
							if((lab1.indexOf("special issue")!=-1))
							{
								check_special_issue=true;
							}
							else
							{
								check_special_issue= false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
	    		if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					//System.out.println("lab : "+i);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							//System.out.println("lab : "+n);
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab);
							//lab=lab.toLowerCase();
							if((lab.indexOf("SI:")!=-1))
							{
								check_s_i= true;
							}
							else
							{
								if((lab.indexOf("S.I.")!=-1))
								{
									check_s_i= true;
								}
								else
								{
									if((lab.indexOf("SI-")!=-1))
									{
										check_s_i= true;
									}
									else
									{
										
											
										check_s_i= false;
									}
									
									//return false;
								}
								//return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					check_special_issue= false;
					check_s_i= false;
				}
				if(check_special_issue==false &&check_s_i==false)
				{
					return false;
				}
				else
				{
					return true;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    //	System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 12");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i1=sb.indexOf("<item-remarks>",0);
					int n1=0;
					String lab1="";
					if(i1!=-1)
					{
						n1=sb.indexOf("</item-remarks>");
						if(n1!=-1)
						{
							lab1=sb.substring(i1+"<item-remarks>".length(),n1);
							//System.out.println("lab : "+lab1);
							lab1=lab1.toLowerCase();
							if((lab1.indexOf("special issue")!=-1))
							{
								check_special_issue=true;
							}
							else
							{
								check_special_issue= false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
	    		if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab "+lab);
							//lab=lab.toLowerCase();
							
								if((lab.indexOf("SI:")!=-1))
								{
									check_s_i= true;
								}
								else
								{
									if((lab.indexOf("S.I.")!=-1))
									{
										check_s_i= true;
									}
									else
									{
										if((lab.indexOf("SI-")!=-1))
										{
											check_s_i= true;
										}
										else
										{
											check_s_i= false;
										}
										
										
									}
									
								}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					check_special_issue= false;
					check_s_i= false;
				}
				if(check_special_issue==false &&check_s_i==false)
				{
					return false;
				}
				else
				{
					return true;
				}
		}
//		return false;
	}
//***********************************************************************************


public boolean readOrderFileForItemGroupDescription()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
    		try
    		{
    			String s4="";
				raf  = new RandomAccessFile(temp, "r");
				  do
				  {
					if((s4 = raf.readLine()) == null)
				      {  break;}
					else
					  {
						sb.append(s4);
					  }
				  }
				  while(true);
				raf.close();
    		}
    		catch(Exception e)
    		{
    			System.out.println("Error in reading Order xml. 13");
    			e.printStackTrace();
    			return false;
    		}
		}
		else
		{
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			File file = new File(OrderPath);
			if(!file.exists())
		    {
				if(xt.serverstatus.length()>0)
				{
					xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
					return false;
				}
				else{
					JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
					System.exit(1);
				}
		    }
		    File[] assets = file.listFiles();
	    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		try
		    		{
		    			String s4="";
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 14");
		    			e.printStackTrace();
		    			return false;
		    		}
		    	}
		    }
		}
		if(Integer.parseInt(aid) < 355467)
		{
			return false;
		}
   		if(sb.indexOf("<item-group>")!= -1)
		{
			int g=sb.indexOf("<item-group>",0);
			int m=0;
			String itg="";
			if(g!=-1)
			{
				m=sb.indexOf("</item-group>");
				if(m!=-1)
				{
					itg=sb.substring(g+"<item-group>".length(),m);
					if(itg.equalsIgnoreCase("IG002293")||itg.equalsIgnoreCase("IG002307")||itg.equalsIgnoreCase("IG002308"))
					{
						System.out.println("<item-group> : "+itg);
					}
					else
					{
						return false;
					}
				}
			}
		}
		else
		{
			return false;
		}
   		if(sb.indexOf("<item-group-description>")!= -1)
		{
			int i=sb.indexOf("<item-group-description>",0);
			int n=0;
			String lab="";
			if(i!=-1)
			{
				n=sb.indexOf("</item-group-description>");
				if(n!=-1)
				{
					lab=sb.substring(i+"<item-group-description>".length(),n);
					if((lab.toLowerCase().indexOf("si: method validation")!=-1))
					{
						System.out.println("<item-group-description> : "+lab);
						return true;
					}
					else if((lab.toLowerCase().indexOf("si: enantioseparations 2014")!=-1))
					{
						System.out.println("<item-group-description> : "+lab);
						return true;
					}
					else if((lab.toLowerCase().indexOf("si: aamss 2014")!=-1))
					{
						System.out.println("<item-group-description> : "+lab);
						return true;
					}
					else
					{
						System.out.println("<item-group-description> : "+lab+" not matched with the condition");
						return false;
					}
				}
			}
			//System.out.println("Found e-extra");
		}
		else
		{
			//System.out.println("Not found e-extra");
			return false;
		}
		return false;
	}



public boolean readOrderFileForItemGroupOLD()
{
	RandomAccessFile raf=null;
	StringBuffer sb=new StringBuffer();
	//odrPath
	//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
	String temp="";
	if(odrPathForFMS.equalsIgnoreCase("yes"))
	{
		temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
		if(!new File(temp).exists())
		if(checkDuckling())
		{
			temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
		}
		//System.out.println("FMS Order Path:  "+temp);
		try
		{
			String s4="";
			raf  = new RandomAccessFile(temp, "r");
			  do
			  {
				if((s4 = raf.readLine()) == null)
			      {  break;}
				else
				  {
					sb.append(s4);
				  }
			  }
			  while(true);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in reading Order xml. 15");
			e.printStackTrace();
			return false;
		}
	}
	else
	{
		temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
		//System.out.println("temp==> "+temp);
		File fe= new File(temp);
		if(fe.exists())
		{
			File[] ast = fe.listFiles();
			for(int i=0; i<ast.length;i++)
			{
				//System.out.println("ast[i].getName()==> "+ast[i].getName());
				if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
				{
					stage="\\"+stage+"RESUPPlY";
					//System.out.println("stage==> "+stage);
				}
				else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
				{
					stage="\\RSVP\\";
				}
			}
		}
		else
		{
			//System.out.println(" else temp==> "+temp+":");
		}
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    {
			if(XT.serverstatus.length()>0)
			{
				XTLogger.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
				return false;
			}
			else{
				JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
				System.exit(1);
			}
	    }
	    File[] assets = file.listFiles();
    	//System.out.println("No of input files::"+assets.length);
	    for(int j=0;j<assets.length;j++)
	    {
	    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
	    	{
	    		try
	    		{
	    			String s4="";
					raf  = new RandomAccessFile(assets[j], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 16");
	    			e.printStackTrace();
	    			return false;
	    		}
	    	}
	    }
	}
	if(sb.indexOf("<item-group>")!= -1)
	{
		int g=sb.indexOf("<item-group>",0);
		int m=0;
		String itg="";
		if(g!=-1)
		{
			m=sb.indexOf("</item-group>");
			if(m!=-1)
			{
				itg=sb.substring(g+"<item-group>".length(),m);
				if(itg.equalsIgnoreCase("IG000005"))
				{
					System.out.println("<item-group> : "+itg);
					return true;
				}
				else
				{
					return false;
				}
			}
		}
	}
	else
	{
		return false;
	}
	return false;
}



public boolean readOrderFileForItemGroup()
{
	RandomAccessFile raf=null;
	StringBuffer sb=new StringBuffer();
	//odrPath
	//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
	String temp="";
	if(odrPathForFMS.equalsIgnoreCase("yes"))
	{
		temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
		if(!new File(temp).exists())
		if(checkDuckling())
		{
			temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
		}
		//System.out.println("FMS Order Path:  "+temp);
		try
		{
			String s4="";
			raf  = new RandomAccessFile(temp, "r");
			  do
			  {
				if((s4 = raf.readLine()) == null)
			      {  break;}
				else
				  {
					sb.append(s4);
				  }
			  }
			  while(true);
			raf.close();
		}
		catch(Exception e)
		{
			System.out.println("Error in reading Order xml. 17");
			e.printStackTrace();
			return false;
		}
	}
	else
	{
		temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
		//System.out.println("temp==> "+temp);
		File fe= new File(temp);
		if(fe.exists())
		{
			File[] ast = fe.listFiles();
			for(int i=0; i<ast.length;i++)
			{
				//System.out.println("ast[i].getName()==> "+ast[i].getName());
				if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
				{
					stage="\\"+stage+"RESUPPlY";
					//System.out.println("stage==> "+stage);
				}
				else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
				{
					stage="\\RSVP\\";
				}
			}
		}
		else
		{
			//System.out.println(" else temp==> "+temp+":");
		}
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    {
			if(XT.serverstatus.length()>0)
			{
				XTLogger.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
				return false;
			}
			else{
				JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
				System.exit(1);
			}
	    }
	    File[] assets = file.listFiles();
    	//System.out.println("No of input files::"+assets.length);
	    for(int j=0;j<assets.length;j++)
	    {
	    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
	    	{
	    		try
	    		{
	    			String s4="";
					raf  = new RandomAccessFile(assets[j], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 18");
	    			e.printStackTrace();
	    			return false;
	    		}
	    	}
	    }
	}
	if(sb.indexOf("<item-group>")!= -1)
	{
		int g=sb.indexOf("<item-group>",0);
		int m=0;
		String itg="";
		if(g!=-1)
		{
			m=sb.indexOf("</item-group>");
			if(m!=-1)
			{
				itg=sb.substring(g+"<item-group>".length(),m);
				itemGroupValue=itg;
				return true;
			}
		}
	}
	else
	{
		return false;
	}
	return false;
}
//***********************************************************************************


//**************************************************************************


public boolean readOrderFileForPEPI()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
				/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 19");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-group-description>")!= -1)
				{
					int i=sb.indexOf("<item-group-description>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-group-description>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-group-description>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.indexOf("deep slab s.i.")!=-1))
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 20");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-group-description>")!= -1)
			{
				int i=sb.indexOf("<item-group-description>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</item-group-description>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<item-group-description>".length(),n);
						//System.out.println("lab "+lab);
						lab=lab.toLowerCase();
						
							if((lab.indexOf("deep slab s.i.")!=-1))
							{
								return true;
							}
							else
							{
								return false;
							}
					}
				}
				//System.out.println("Found e-extra");
				
			}
			else
			{
				//System.out.println("Not found e-extra");
				return false;
			}
		}
		return false;
	}
//***********************************************************************************

public boolean getInforForJCV(String contain1)//15/01/2010
	{
		String contain2=contain1;
		
		if(contain2.indexOf("role=\"question\"",0)!=-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	//return false;
	}

	public Hashtable getStage(String jid,String aid)
	{
		Hashtable ht=new Hashtable();
		boolean checkpath=false;
		String orderpath="";
		String pii = "";
		String fileString = new String();
		String checkString = new String();
		int check,index,index1 =0;
		String path=orderpath;
		String check_s250="";
		String check_s200="";
		String check_s250Resupply="";
		StringBuffer sb=new StringBuffer();
		try{
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			orderpath=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(orderpath).exists())
			if(checkDuckling())
			{
				orderpath="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}

			String stage=getStageInfo(jid,aid);
			if(stage.equalsIgnoreCase("S250")||stage.equalsIgnoreCase("S250RESUPPLY"))
			{
				//ht.put("stage",stage);	//block on 13-07-2011 In case of S250 Resupply, Only S250 required
				ht.put("stage","S250");	
			}
			if(stage.equalsIgnoreCase("S250")||stage.equalsIgnoreCase("S250RESUPPLY"))
			checkpath=true;
			else
			checkpath=false;

			/*if(getStageInfo(jid,aid))
			checkpath=true;
			else
			checkpath=false;
			*/
		}
		else
		{
			orderpath=odrPath+"\\"+jid+"\\"+aid+"\\";
			//System.out.println("orderpath"+orderpath);
			if(!new File(orderpath).exists())
			{
				throw new Exception("Order XML File location Does not contain xml Files "+orderpath);
			}
			
			File f=new File(orderpath);
			//System.out.println("f1.length"+f.getAbsolutePath());
			File []f1=f.listFiles();
			//System.out.println("f1.length"+f1.length);
			if(f1.length>0)
			{
				for(int i=0;i<f1.length;i++)
				{
					//System.out.println("bbbbbbbbbb"+f1[i].getName());
					if(f1[i].getName().equalsIgnoreCase("S250"))
					{
						check_s250=f1[i].getName();

						ht.put("stage",check_s250);
						checkpath=true;	
						break;
					}
					if(f1[i].getName().equalsIgnoreCase("S250RESUPPLY"))
					{
						check_s250=f1[i].getName();
						ht.put("stage",check_s250);
						checkpath=true;	
						break;
					}

				
				}
				if(checkpath){
				orderpath=orderpath+"\\"+check_s250+"\\CURRENT ORDER\\";
				
				File ff=new File(orderpath);
				File []ff1=ff.listFiles();
				orderpath=ff1[0].getAbsolutePath();
				}
			}
		}
		//System.out.println("checkpath"+checkpath);
		//System.out.println("orderpath"+orderpath);
		if(checkpath)
		{
			String label="";
			RandomAccessFile raf =new RandomAccessFile(new File(orderpath),"r");
			while((fileString = raf.readLine())!=null)
		    {
				sb=sb.append(fileString);
              
		    }
		   //System.out.println(sb);
		   raf.close();
		   
		   //<vol-from>
		   int spos=0;
		   int epos=0;
		   
		   spos=sb.indexOf("<vol-from>",0);
		   if(spos !=-1)
		   {
		   		epos=sb.indexOf("</vol-from>",0);
		   		if(epos !=-1)
		   		{
		   			label=sb.substring(spos+"<vol-from>".length(),epos);
		   			//System.out.println("label "+label);
		   			ht.put("volume",label);
		   		}
		   }
		   spos=0;
		   epos=0;
		   label="";
		   if(sb.indexOf("</iss-from>",0)!=-1)
		   {
		   	spos=sb.indexOf("<iss-from>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</iss-from>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<iss-from>".length(),epos);
			   			//System.out.println("label "+label);
			   			ht.put("issfrom",label);
			   		}
			   }
		   }
		   else
		   {
		   	//System.out.println("label "+label);
		   	ht.put("issfrom","0");
		   }

			spos=0;
		   epos=0;
		   label="";
		   if(sb.indexOf("</supp>",0)!=-1)
		   {
		   	spos=sb.indexOf("<supp>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</supp>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<supp>".length(),epos);
			   			//System.out.println("label "+label);
			   			ht.put("supp",label);
			   		}
			   }
		   }
		  /* else
		   {
		   	//System.out.println("label "+label);
		   	ht.put("supp","NO SUPP");
		   }
			*/

		   spos=0;
		   epos=0;
		   label="";
		   if(sb.indexOf("</first-page>",0)!=-1)
		   {
		   	spos=sb.indexOf("<first-page>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</first-page>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<first-page>".length(),epos);
			   			//System.out.println("label "+label);
			   			ht.put("firstpage",label);
			   		}
			   }
		   }
		   else
		   {
		   	//System.out.println("label "+label);
		   	ht.put("firstpage",label);
		   }
		   
		   spos=0;
		   epos=0;
		   label="";
		   if(sb.indexOf("</last-page>",0)!=-1)
		   {
		   	spos=sb.indexOf("<last-page>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</last-page>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<last-page>".length(),epos);
			   			//System.out.println("label "+label);
			   			ht.put("lastpage",label);
			   		}
			   }
		   }
		   else
		   {
		   	//System.out.println("label "+label);
		   	ht.put("lastpage",label);
		   }
			spos=0;
		   epos=0;
		   label="";
		   if(sb.indexOf("</s250-sequence-number>",0)!=-1)
		   {
		   	spos=sb.indexOf("<s250-sequence-number>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</s250-sequence-number>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<s250-sequence-number>".length(),epos);
			   			//System.out.println("label "+label);
			   			ht.put("s250-sequence-number",label);
			   		}
			   }
		   }
		   else
		   {
		   	//System.out.println("label "+label);
		   	ht.put("s250-sequence-number",label);
		   }
		   spos=0;
		   epos=0;
		   label="";
		   String year="";
		   String month="";
		   String day="";

		if(sb.indexOf("<online-publ-date>",0)!=-1)
		   {
		   	spos=sb.indexOf("<online-publ-date>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</online-publ-date>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<online-publ-date>".length(),epos);
						label=label.trim();
						int s=0;
						int m=0;
						int t=0;
						s=label.indexOf("<date day=\"",m);
						if(s!=-1)
						{
							day=label.substring(s+"<date day=\"".length(),s+"<date day=\"".length()+2);
							ht.put("day",day);
						}
						s=0;
						m=0;
						s=label.indexOf("month=\"",m);
						if(s!=-1)
						{
							month=label.substring(s+"month=\"".length(),s+"month=\"".length()+2);	
							ht.put("month",month);
						}
						s=0;
						m=0;
						s=label.indexOf("yr=\"",m);
						if(s!=-1)
						{
							year=label.substring(s+"yr=\"".length(),s+"yr=\"".length()+4);	
							ht.put("year",year);
						}
						
			   			//System.out.println("label "+label.trim());
					


			   			//ht.put("coverdate",label);
			   		}
			   }
		   }
			spos=0;
		   epos=0;
		   label="";
		   year="";
		   
		   if(sb.indexOf("</effect-cover-date>",0)!=-1)
		   {
		   	spos=sb.indexOf("<effect-cover-date>",0);
			   if(spos !=-1)
			   {
			   		epos=sb.indexOf("</effect-cover-date>",0);
			   		if(epos !=-1)
			   		{
			   			label=sb.substring(spos+"<effect-cover-date>".length(),epos);
			   			//System.out.println("label "+label.length());
						year=label.substring(0,4);
						ht.put("cover_year",year);
						/*if(label.length()==8)
						{
							year=label.substring(0,4);
							ht.put("cover_year",year);
							
						}
						else if(label.length()==6)
						{
							year=label.substring(0,4);
							ht.put("cover_year",year);
							
						}
						else if(label.length()==4)
						{
							year=label;
							ht.put("cover_year",year);
							
						}*/


			   			//ht.put("coverdate",label);
			   		}
			   }
		   }
		   
		   
		}
		else
		{
			
		}
	}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		//System.out.println("ht"+ht);
		return ht;
	}
	
public  String getStageInfo(String jid,String aid)
	{
		String orderpath="";
		String stage="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			//orderpath
			orderpath=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(orderpath).exists())
			if(checkDuckling())
			{
				orderpath="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			
				
		}
		
		String checkspr = "";
		String fileString = new String();
		String checkString = new String();
		String sectionvalue = new String();
		String abs = new String();
		int check,index,index1 =0;
		String path=orderpath;
		if(!new File(path).exists())
		{
			return stage;
		}
		File f=new File(path);
		File []f1=f.listFiles();
		
		RandomAccessFile raf=null;
		
		try
		{
			raf=new RandomAccessFile(orderpath,"r");
			while((fileString = raf.readLine())!=null)
			   {
			   checkString = checkString + fileString;
               
			   }
			   raf.close();
			   //System.out.println("==="+checkString);
			   if(checkString.indexOf("<stage step=\"")!= -1)
			   {
			   		int i=checkString.indexOf("<stage step=\"",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=checkString.indexOf("\"/>",i+"<stage step=\"".length());
						if(n!=-1)
						{
							lab=checkString.substring(i+"<stage step=\"".length(),n);
							//lab=lab.toLowerCase();

							//System.out.println("Stage==================== "+lab);
							stage=lab;
							if(stage.equalsIgnoreCase("s200")||stage.equalsIgnoreCase("s200resupply")||stage.equalsIgnoreCase("s100")||stage.equalsIgnoreCase("s100resupply")||stage.equalsIgnoreCase("s5")||stage.equalsIgnoreCase("s5resupply")||stage.equalsIgnoreCase("s300")||stage.equalsIgnoreCase("s300resupply")||stage.equalsIgnoreCase("p100")||stage.equalsIgnoreCase("p100resupply")||stage.equalsIgnoreCase("f300")||stage.equalsIgnoreCase("f300resupply"))
							{
								stage="";
							}
						}else
						{
							stage="";
						}
					}
					else
					{
						stage="";
					}
			   }
			   else
			   {
			   	 stage="";
			   }
		}catch(Exception e)
		{
			e.printStackTrace();
			stage="";
		}
		return stage;
	}
//10-07-2012******************************
public boolean readOrderFileFor_DPC()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 21");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i=sb.indexOf("<item-remarks>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-remarks>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-remarks>".length(),n);
							
							lab=lab.toLowerCase();
							//System.out.println("lab : "+lab);
								//Real Text : "Développement professionnel continu" //containging UTF 8 charecter. So "Développement" is replaced by "veloppement" cherecter
							if((lab.indexOf("développement professionnel continu")!=-1))
							{
								//System.out.println("000000000000");
								return true;
							}
							else if((lab.indexOf("veloppement professionnel continu")!=-1))
							{
								//System.out.println("1111111");
								return true;
							}
							else if((lab.indexOf(" dpc ")!=-1))
							{
								//System.out.println("2222222222");
								return true;
							}
							else
							{
								//System.out.println("3333333");
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 22");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-remarks>")!= -1)
			{
				int i=sb.indexOf("<item-remarks>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</item-remarks>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<item-remarks>".length(),n);
						lab=lab.toLowerCase();
							if((lab.indexOf("viroqas")!=-1))
							{
								return true;
							}
							else
							{
								return false;
							}
					}
				}
				
				
			}
			else
			{
				
				return false;
			}
		}
		return false;
	}
//****************************************
public boolean readOrderFileForFMC_JCV()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 23");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i=sb.indexOf("<item-remarks>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-remarks>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-remarks>".length(),n);
							//System.out.println("lab : "+lab);
							lab=lab.toLowerCase();
							if((lab.indexOf("viroqas")!=-1))
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}
					else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"1 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 24");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-remarks>")!= -1)
			{
				int i=sb.indexOf("<item-remarks>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</item-remarks>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<item-remarks>".length(),n);
						lab=lab.toLowerCase();
							if((lab.indexOf("viroqas")!=-1))
							{
								return true;
							}
							else
							{
								return false;
							}
					}
				}
				//System.out.println("Found e-extra");
				
			}
			else
			{
				//System.out.println("Not found e-extra");
				return false;
			}
		}
		return false;
	}


//***************************[Get E-component Information from Order]******************
public String GetEComponentInffo()
	{
		
		String status="0";
		String temp="";
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			//temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			try
	    		{
	    			String s4="";
					//File f[]= new File(OrderPath).listFiles();
					//	if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(new File(temp), "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 25");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<no-e-components>",0)!=-1)
				{
					int i=sb.indexOf("<no-e-components>",0);
					int j=sb.indexOf("</no-e-components>",i+"<no-e-components>".length());
					String lab=sb.substring(i+"<no-e-components>".length(),j);
					System.out.println("NO of no-e-components status in order........ "+lab);
					status=lab;
				}
		}else
		{
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						stage="\\"+stage+"RESUPPlY";
						
					}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
				String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+this.stage.toUpperCase()+"\\CURRENT ORDER";
				//System.out.println("----------------------------------"+OrderPath);
			
				File file = new File(OrderPath);
				if(!file.exists())
				{
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
						return "";
					}
					else{
						JOptionPane.showMessageDialog(null,"2 Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
						System.exit(1);
					}
				}
				try
	    		{
	    			String s4="";
					File f[]= new File(OrderPath).listFiles();
						if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(f[0], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 26");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<no-e-components>",0)!=-1)
				{
					int i=sb.indexOf("<no-e-components>",0);
					int j=sb.indexOf("</no-e-components>",i+"<no-e-components>".length());
					String lab=sb.substring(i+"<no-e-components>".length(),j);
					System.out.println("NO of no-e-components status in order........ "+lab);
					status=lab;
				}
			}
		}
		return status;

	}

//*************************************************************************************


//******************************[26/11/2008]**************************************
	/*
	 * This method is used to check for Resupply order
	 * Modify By : Ravi Shekhar
	 * Date :    : 26/11/2008
	*/
	public String GetCopyrightStatus()
	{
		
		String status="";
		String temp="";
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			//temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			try
	    		{
	    			String s4="";
					//File f[]= new File(OrderPath).listFiles();
					//	if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(new File(temp), "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 27");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<copyright-status>",0)!=-1)
				{
					int i=sb.indexOf("<copyright-status>",0);
					int j=sb.indexOf("</copyright-status>",i+"<copyright-status>".length());
					String lab=sb.substring(i+"<copyright-status>".length(),j);
					//System.out.println("Copyright status in order........ "+lab);
					status=lab;
				}
		}else
		{
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						stage="\\"+stage+"RESUPPlY";
						
					}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
				String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+this.stage.toUpperCase()+"\\CURRENT ORDER";
				//System.out.println("----------------------------------"+OrderPath);
			
				File file = new File(OrderPath);
				if(!file.exists())
				{
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
						return "";
					}
					else{
					JOptionPane.showMessageDialog(null,"3 Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
					System.exit(1);
					}
				}
				try
	    		{
	    			String s4="";
					File f[]= new File(OrderPath).listFiles();
						if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(f[0], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 28");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<copyright-status>",0)!=-1)
				{
					int i=sb.indexOf("<copyright-status>",0);
					int j=sb.indexOf("</copyright-status>",i+"<copyright-status>".length());
					String lab=sb.substring(i+"<copyright-status>".length(),j);
					//System.out.println("Copyright status in order........ "+lab);
					status=lab;
				}
			}
		}
		//System.out.println("Copyright status in order........ "+status);
		return status;
	}

	//Updated on 28-01-2014 to get value of <license>xxx</license> tag
	public String GetCCLicenseStatus()
	{
		String status="";
		String temp="";
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			//temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			try
	    		{
	    			String s4="";
					//File f[]= new File(OrderPath).listFiles();
					//	if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(new File(temp), "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 29");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<license>",0)!=-1)
				{
					int i=sb.indexOf("<license>",0);
					int j=sb.indexOf("</license>",i+"<license>".length());
					String lab=sb.substring(i+"<license>".length(),j);
					//System.out.println("CCLicense status in order........ "+lab);
					status=lab;
				}
				else
				{
					status="NotFound";
				}
		}else
		{
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						stage="\\"+stage+"RESUPPlY";
						
					}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
				String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+this.stage.toUpperCase()+"\\CURRENT ORDER";
				//System.out.println("----------------------------------"+OrderPath);
			
				File file = new File(OrderPath);
				if(!file.exists())
				{
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
						return "";
					}
					else{
					JOptionPane.showMessageDialog(null,"3 Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
					System.exit(1);
					}
				}
				try
	    		{
	    			String s4="";
					File f[]= new File(OrderPath).listFiles();
						if(f[0].getName().toLowerCase().endsWith(".xml"))
					raf  = new RandomAccessFile(f[0], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 30");
	    			e.printStackTrace();
	    			
	    		}
				if(sb.indexOf("<license>",0)!=-1)
				{
					int i=sb.indexOf("<license>",0);
					int j=sb.indexOf("</license>",i+"<license>".length());
					String lab=sb.substring(i+"<license>".length(),j);
					//System.out.println("CCLicense status in order........ "+lab);
					status=lab;
				}
				else
				{
					status="NotFound";
				}
			}
		}
		//System.out.println("CCLicense status in order........ "+status);
		status=status.replaceAll(" ","-");
		return status;
	}

	public boolean readOrderFileForResupply(String j,String a,String s)
	{
		boolean check=false;
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir");
			//System.out.println("FMS Order Path:  "+temp);
			check=true;
		}
		else
		{
			temp=odrPath+j.toUpperCase()+"\\"+a.trim();
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(s+"RESUPPlY"))
					{
						s="\\"+s+"RESUPPlY";
						check=true;
					}
				}
				String OrderPath=odrPath+j.toUpperCase()+"\\"+a+"\\"+s.toUpperCase()+"\\CURRENT ORDER";
				//System.out.println(check+"----------------------------------"+OrderPath);
			
				File file = new File(OrderPath);
				if(!file.exists())
				{
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for JID "+jid+" AID "+aid+" Stage"+stage);
						return false;
					}
					else{
					JOptionPane.showMessageDialog(null,"4 Order not Found for JID "+j+" AID "+a+" Stage"+s);
					System.exit(1);
					}
				}
				
				
			}
			else
			{
				check=false;
			}
		}
		//System.out.println(check+"----------------------------------");
		return check;
	}

	//********************************************************************
	public boolean readOrderFileForEextra()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("readOrderFileForEextra FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
						//return false;
						System.exit(1);
					}
					else{
	    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
	    			System.exit(1);
					}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 31");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<online-version type=",0)!=-1)
				{
					int i=sb.indexOf("<online-version type=\"",0);
					int j=sb.indexOf("\"",i+"<online-version type=\"".length());
					String lab=sb.substring(i+"<online-version type=\"".length(),j);
					System.out.println("ONLINE-VERSION........ "+lab);
					label=lab;
				}
			    if(sb.indexOf("<online-version type=\"e-extra\"",0)!= -1)
				{
					System.out.println("e-extra.... True");
					return true;
				}
				else if(sb.indexOf("<online-version type=\"e-appended\"",0)!= -1)
				{					
					System.out.println("e-appended.... True");
					return true;
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
			//return true;
		}
		else
		{
		
		temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
		//System.out.println("Order Path==> "+temp);
		File fe= new File(temp);
		if(fe.exists())
		{
			File[] ast = fe.listFiles();
			for(int i=0; i<ast.length;i++)
			{
				//System.out.println("ast[i].getName()==> "+ast[i].getName());
				if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
				{
					
					stage="\\"+stage+"RESUPPlY";
					//System.out.println("stage==> "+stage);
				}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
			}
			
		}
		else
		{
			//System.out.println(" else temp==> "+temp+":");
		}
		//odrPath
		//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		//System.out.println("\n OrderPath : "+OrderPath);
		//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    	{
	    		if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for JID "+jid+" aid "+aid+" stage "+stage);
						return false;
					}
					else{
					JOptionPane.showMessageDialog(null,"5 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
					System.exit(1);
				}
	    	}
	    File[] assets = file.listFiles();
	    	//System.out.println("No of input files::"+assets.length);
	    for(int j=0;j<assets.length;j++)
	    {
	    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
	    	{
	    		String check_order_file=OrderPath+"\\"+assets[j].getName();
	    		/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
						//return false;
						System.exit(1);
					}
					else{
	    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
	    			System.exit(1);
	    			}
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(assets[j], "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4.toLowerCase());
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 32");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		
	    		
	    		
	    	}
	    }
		if(sb.indexOf("<online-version type=",0)!=-1)
		{
			int i=sb.indexOf("<online-version type=\"",0);
			int j=sb.indexOf("\"",i+"<online-version type=\"".length());
			String lab=sb.substring(i+"<online-version type=\"".length(),j);
			System.out.println("ONLINE-VERSION........ "+lab);
			label=lab;
		}
	    if(sb.indexOf("<online-version type=\"e-extra\"",0)!= -1)
		{
			System.out.println("e-extra.... True");
			return true;
		}
		else if(sb.indexOf("<online-version type=\"e-appended\"",0)!= -1)
		{					
			System.out.println("e-appended.... True");
			return true;
		}
		else
		{
			//System.out.println("Not found e-extra");
			return false;
		}
		}
		
	}
	public boolean readOrderFileForEextra(String Vol_Iss_No)throws IOException
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		

		//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    	{//odrPath
	    		//String temp_order_path="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid;
				String temp_order_path=odrPath+jid.toUpperCase()+"\\"+aid;
	    		File RSVP_File= new File(temp_order_path);
	    		if(!RSVP_File.exists())
	    		{
					if(xt.serverstatus.length()>0)
					{
						xt_log.info("[ERROR] Order not Found for jid "+jid+" Vol/Iss No.  "+Vol_Iss_No);
						return false;
					}
					else{
	    			JOptionPane.showMessageDialog(null,"6 Order not Found for jid "+jid +" Vol/Iss No.  "+Vol_Iss_No);
	    			System.exit(1);
					}
	    		}
	    		else
	    		{
	    			OrderPath=temp_order_path;
	    		}
	    		
	    	}
	    file = new File(OrderPath);
	    File [] f=file.listFiles();
	    boolean stg=false;
	    String Stage_name="";
			boolean S300=false;
			boolean F300=false;
			boolean P100=false;
			boolean S300R=false;
			boolean F300R=false;
			boolean P100R=false;
	    for(int i=0;i<f.length;i++)
	    {
	    	if(f[i].isDirectory())
	    	{
	    		//f[i].getName().equalsIgnoreCase("S300RESUPPLY")
				if(f[i].getName().equalsIgnoreCase("S300"))
	    		{
	    			S300=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("S300RESUPPLY"))
	    		{
	    			S300=false;
					S300R=true;
					P100R=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("P100RESUPPLY"))
	    		{
	    			P100R=true;
	    		}

	    	}
	    }
	    File[] assets = file.listFiles();
	    	//System.out.println("No of input files::"+assets.length);
	    	
	    for(int jj=0;jj<assets.length;jj++)
	    {
	    	if(assets[jj].isDirectory())
	    	{
	    		//System.out.println("Folder Name: "+assets[jj].getName());
	    		if(assets[jj].getName().equalsIgnoreCase("S300RESUPPLY")&& S300R==true)
	    		{
	    			Stage_name="S300RESUPPLY";
	    			OrderPath+="\\S300RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 1 OrderPath : "+OrderPath);
	    		}
	    		if(assets[jj].getName().equalsIgnoreCase("S300") && S300R==false && S300==true && F300R==false && F300==false && P100R==false && P100==false)
	    		{
	    			Stage_name="S300";
	    			OrderPath+="\\S300\\CURRENT ORDER";
	    			//System.out.println("\n 2 OrderPath : "+OrderPath);
	    		}
				else if((assets[jj].getName().equalsIgnoreCase("P100RESUPPLY"))&& S300R==false && S300==false && P100R==true)
	    		{
					
	    			Stage_name="P100RESUPPLY";
	    			OrderPath+="\\P100RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 3 OrderPath : "+OrderPath);
	    		}
	    		else if((assets[jj].getName().equalsIgnoreCase("P100"))&& S300R==false && S300==false && P100R==false)
	    		{
	    			Stage_name="P100";
	    			OrderPath+="\\P100\\CURRENT ORDER";
	    			//System.out.println("\n 4 OrderPath : "+OrderPath);
	    		}
	    		else if(assets[jj].getName().equalsIgnoreCase("RSVP"))
	    		{
	    			OrderPath+="\\RSVP\\CURRENT ORDER";
	    			//System.out.println("\n 5 OrderPath : "+OrderPath);
	    		}
	    		
	    	}
			 File Order_Xml_File= new File(OrderPath);
			 File[] assets1 = Order_Xml_File.listFiles();
			 for(int j=0;j<assets1.length;j++)
			 {
			
					
				if(assets1[j].isFile()&&(assets1[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets1[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets1[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4.toLowerCase());
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml File. 33");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
	    	}
	    }
	    if(Stage_name.equalsIgnoreCase("S300")||Stage_name.equalsIgnoreCase("S300RESUPPLY")||Stage_name.equalsIgnoreCase("F300")||Stage_name.equalsIgnoreCase("F300RESUPPLY")||Stage_name.equalsIgnoreCase("P100")||Stage_name.equalsIgnoreCase("P100RESUPPLY"))
	    {
	    	boolean bol= ReadOrder(sb.toString());
	    	return bol;
	    }
	    else if(sb.indexOf("<online-version type=\"e-extra\"",0)!= -1)
		{
			if(sb.indexOf("<online-version type=",0)!=-1)
			{
				int i=sb.indexOf("<online-version type=\"",0);
				int j=sb.indexOf("\"",i+"<online-version type=\"".length());
				String lab=sb.substring(i+"<online-version type=\"".length(),j);
				System.out.println("ONLINE-VERSION........ "+lab);
							label=lab;
			}
			System.out.println("e-extra.... True");
			return true;
		}
		else if(sb.indexOf("<online-version type=\"e-appended\"",0)!= -1)
		{
			if(sb.indexOf("<online-version type=",0)!=-1)
			{
				int i=sb.indexOf("<online-version type=\"",0);
				int j=sb.indexOf("\"",i+"<online-version type=\"".length());
				String lab=sb.substring(i+"<online-version type=\"".length(),j);
				System.out.println("ONLINE-VERSION........ "+lab);
			}
			System.out.println("e-appended... True");
			return true;
		}
		else
		{
			//System.out.println("Not found e-extra");
			return false;
		}
		//return false;
	}
	
	public boolean ReadOrder(String Order_file)throws IOException
	{
		//System.out.println("Order_file : "+Order_file);
		//System.in.read();
		String ofile=Order_file;
		String contain="";
		String temp_aid="";
		//<row type="ce">
		int spos=0;
		int epos=0;
		boolean temp=false;
		while(spos!= -1)
		{
			spos=ofile.indexOf("<row type=\"ce\">",epos);
			if(spos!=-1)
			{
				epos=ofile.indexOf("</row>",spos);
				if(epos !=-1)
				{
					contain=ofile.substring(spos+"<row type=\"ce\">".length(),epos);
					//System.out.println("contain : "+contain);
					//System.in.read();
					int spos_aid=0;
					int epos_aid=0;
					int online=0;
					spos_aid=contain.indexOf("<aid>",epos_aid);
					if(spos_aid !=-1)
					{
						epos_aid=contain.indexOf("</aid>",spos_aid);
						if(epos_aid !=-1)
						{
							temp_aid=contain.substring(spos_aid+"<aid>".length(),epos_aid);
							if(temp_aid.equalsIgnoreCase(aid))
							{
								online = contain.indexOf("<online-version type=\"e-extra\"");
								if(online !=-1)
								{
									temp=true;
								}
								else
								{
									online = contain.indexOf("<online-version type=\"e-appended\"");
									if(online !=-1)
									{
										temp=true;
									}
									else
									{
										temp=false;
									}
								}
							}
							
						}
					}
				}
			}
		}
		
		//System.out.println("temp : "+temp);
		//System.in.read();
		return temp;
	}

	//********************************************************************

	public boolean readOrderFileForFMC()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//odrPath
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		String temp="";
		if(odrPathForFMS.equalsIgnoreCase("yes"))
		{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
	    		}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 34");
	    			e.printStackTrace();
	    			return false;
	    		}
	    		if(sb.indexOf("<item-remarks>")!= -1)
				{
					int i=sb.indexOf("<item-remarks>",0);
					int n=0;
					String lab="";
					if(i!=-1)
					{
						n=sb.indexOf("</item-remarks>");
						if(n!=-1)
						{
							lab=sb.substring(i+"<item-remarks>".length(),n);
							lab=lab.toLowerCase();
							//System.out.println("1 lab : "+lab);
							if(
								(lab.indexOf("FMC")!=-1)||
								(lab.indexOf("fmc")!=-1)||
								(lab.indexOf("Fmc")!=-1)||
								(lab.indexOf("FMc")!=-1)||
								(lab.indexOf("FmC")!=-1)||
								(lab.indexOf("fMC")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation medicale continue")!=-1)||
								(lab.indexOf("Formation Medicale Continue")!=-1)||
								(lab.indexOf("Formation medicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation = maquette rouge")!=-1)||
								(lab.indexOf("formation = maquette rouge")!=-1)||
								(lab.indexOf("formation= maquette rouge")!=-1)||
								(lab.indexOf("formation =maquette rouge")!=-1)||
								(lab.indexOf("formation=maquette rouge")!=-1)
							   )
							{
								return true;
							}
							else
							{
								return false;
							}
						}
					}
					//System.out.println("Found e-extra");
					
				}
				else
				{
					//System.out.println("Not found e-extra");
					return false;
				}
		
		}
		else
		{
		
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("temp==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			//odrPath
			//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			//System.out.println("\n OrderPath : "+OrderPath);
			//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order Not Found for jid "+jid +" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"7 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							return false;
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 35");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
		    if(sb.indexOf("<item-remarks>")!= -1)
			{
				int i=sb.indexOf("<item-remarks>",0);
				int n=0;
				String lab="";
				if(i!=-1)
				{
					n=sb.indexOf("</item-remarks>");
					if(n!=-1)
					{
						lab=sb.substring(i+"<item-remarks>".length(),n);
						lab=lab.toLowerCase();
						//System.out.println("lab : "+lab);
						if(
								(lab.indexOf("FMC")!=-1)||
								(lab.indexOf("fmc")!=-1)||
								(lab.indexOf("Fmc")!=-1)||
								(lab.indexOf("FMc")!=-1)||
								(lab.indexOf("FmC")!=-1)||
								(lab.indexOf("fMC")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation medicale continue")!=-1)||
								(lab.indexOf("Formation Medicale Continue")!=-1)||
								(lab.indexOf("Formation medicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation médicale continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation continue")!=-1)||
								(lab.indexOf("formation = maquette rouge")!=-1)||
								(lab.indexOf("formation= maquette rouge")!=-1)||
								(lab.indexOf("formation =maquette rouge")!=-1)||
								(lab.indexOf("formation=maquette rouge")!=-1)
							   )
						{
							return true;
						}
						else
						{
							return false;
						}
					}
				}
				//System.out.println("Found e-extra");
				
			}
			else
			{
				//System.out.println("Not found e-extra");
				return false;
			}
		}
		return false;
	}
	public boolean readOrderFileForFMC(String Vol_Iss_No)throws IOException
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		
		//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    	{
			//odrPath
	    		//String temp_order_path="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid;
				String temp_order_path=odrPath+jid.toUpperCase()+"\\"+aid;
	    		File RSVP_File= new File(temp_order_path);
	    		if(!RSVP_File.exists())
	    		{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order not found for jid "+jid+" aid "+aid);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"8 Order not Found for jid "+jid +" aid "+aid+" stage "+stage);
							System.exit(1);
						}
	    		}
	    		else
	    		{
	    			OrderPath=temp_order_path;
	    		}
	    		
	    	}
	    file = new File(OrderPath);
	    File [] f=file.listFiles();
	    boolean stg=false;
	    String Stage_name="";
			boolean S300=false;
			boolean F300=false;
			boolean P100=false;
			boolean S300R=false;
			boolean F300R=false;
			boolean P100R=false;
	    for(int i=0;i<f.length;i++)
	    {
	    	if(f[i].isDirectory())
	    	{
	    		//f[i].getName().equalsIgnoreCase("S300RESUPPLY")
				if(f[i].getName().equalsIgnoreCase("S300"))
	    		{
	    			S300=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("S300RESUPPLY"))
	    		{
	    			S300=false;
					S300R=true;
					P100R=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("P100RESUPPLY"))
	    		{
	    			P100R=true;
	    		}

	    	}
	    }
	    File[] assets = file.listFiles();
	    	//System.out.println("No of input files::"+assets.length);
	    	
	    for(int jj=0;jj<assets.length;jj++)
	    {
	    	if(assets[jj].isDirectory())
	    	{
	    		//System.out.println("Folder Name: "+assets[jj].getName());
	    		if(assets[jj].getName().equalsIgnoreCase("S300RESUPPLY")&& S300R==true)
	    		{
	    			Stage_name="S300RESUPPLY";
	    			OrderPath+="\\S300RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 1 OrderPath : "+OrderPath);
	    		}
	    		if(assets[jj].getName().equalsIgnoreCase("S300") && S300R==false && S300==true && F300R==false && F300==false && P100R==false && P100==false)
	    		{
	    			Stage_name="S300";
	    			OrderPath+="\\S300\\CURRENT ORDER";
	    			//System.out.println("\n 2 OrderPath : "+OrderPath);
	    		}
				else if((assets[jj].getName().equalsIgnoreCase("P100RESUPPLY"))&& S300R==false && S300==false && P100R==true)
	    		{
					
	    			Stage_name="P100RESUPPLY";
	    			OrderPath+="\\P100RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 3 OrderPath : "+OrderPath);
	    		}
	    		else if((assets[jj].getName().equalsIgnoreCase("P100"))&& S300R==false && S300==false && P100R==false)
	    		{
	    			Stage_name="P100";
	    			OrderPath+="\\P100\\CURRENT ORDER";
	    			//System.out.println("\n 4 OrderPath : "+OrderPath);
	    		}
	    		else if(assets[jj].getName().equalsIgnoreCase("RSVP"))
	    		{
	    			OrderPath+="\\RSVP\\CURRENT ORDER";
	    			//System.out.println("\n 5 OrderPath : "+OrderPath);
	    		}
	    		
	    	}
			 File Order_Xml_File= new File(OrderPath);
			 File[] assets1 = Order_Xml_File.listFiles();
			 for(int j=0;j<assets1.length;j++)
			 {
			
					
				if(assets1[j].isFile()&&(assets1[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets1[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets1[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml File. 36");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
	    	}
	    }
	   /* if(Stage_name.equalsIgnoreCase("S300")||Stage_name.equalsIgnoreCase("S300RESUPPLY")||Stage_name.equalsIgnoreCase("F300")||Stage_name.equalsIgnoreCase("F300RESUPPLY")||Stage_name.equalsIgnoreCase("P100")||Stage_name.equalsIgnoreCase("P100RESUPPLY"))
	    {
	    	boolean bol= ReadOrder(sb.toString());
	    	return bol;
	    }
	    else if(sb.indexOf("<online-version type=\"e-extra\"/>",0)!= -1)
		{
			//System.out.println("Found e-extra");
			return true;
		}
		else
		{
			//System.out.println("Not found e-extra");
			return false;
		}*/
		if(sb.indexOf("<item-remarks>")!= -1)
		{
			int i=sb.indexOf("<item-remarks>",0);
			int n=0;
			String lab="";
			if(i!=-1)
			{
				n=sb.indexOf("</item-remarks>",0);
				if(n!=-1)
				{
					lab=sb.substring(i+"<item-remarks>".length(),n);
					if(
						(lab.indexOf("FMC")!=-1)||
						(lab.indexOf("fmc")!=-1)||
						(lab.indexOf("Fmc")!=-1)||
						(lab.indexOf("FMc")!=-1)||
						(lab.indexOf("FmC")!=-1)||
						(lab.indexOf("fMC")!=-1)||
						(lab.indexOf("Formation Médicale Continue")!=-1)||
						(lab.indexOf("FORMATION MEDICALE CONTINUE")!=-1)||
						(lab.indexOf("Formation Medicale Continue")!=-1)||
						(lab.indexOf("Formation medicale continue")!=-1)||
						(lab.indexOf("FORMATION MÉDICALE CONTINUE")!=-1)||
						(lab.indexOf("Formation Médicale Continue")!=-1)||
						(lab.indexOf("Formation médicale continue")!=-1)||
						(lab.indexOf("Formation continue")!=-1)||
						(lab.indexOf("Formation Continue")!=-1)||
						(lab.indexOf("formation Continue")!=-1)||
						(lab.indexOf("formation continue")!=-1)||
						(lab.indexOf("FORMATION CONTINUE")!=-1)
					  )
					{
						return true;
					}
					else
					{
						return false;
					}
				}
			}
			//System.out.println("Found e-extra");
			
		}
		else
		{
			return false;
		}
		return false;
	}
	//*******************************************************************

	public boolean checkDuckling()
	{
			String filename="";
			String line="";
			StringBuffer sb= new StringBuffer();
			//odrPath
			//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
			String temp="";
			if(odrPathForFMS.equalsIgnoreCase("yes"))
			{
				//temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
				
				File fn=new File(temp);
				if(!fn.exists())
					{
						//temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
						temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
					}
				if(!new File(temp).exists())
				{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order not found for FMS on "+temp+". Please check.");
							return false;
						}
						else{
					JOptionPane.showMessageDialog(null,"[ERROR] :Order not found for FMS on "+temp+". Please check.");
	    			System.exit(1);
						}
				}
				//System.out.println("FMS Order Path:  "+temp);
				String check_order_file=temp;
				/*CheckXmlParser dom = new CheckXmlParser();
					if(dom.parseXML(check_order_file))
					{
							
					}
					else
					{
						//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
							JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							System.exit(1);
						}
						
					}*/
					try
					{
						String s4="";
						RandomAccessFile raf =null;
						raf  = new RandomAccessFile(temp, "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
							  {  break;}
							else
							  {
								sb.append(s4);
							  }
						  }
						  while(true);
						raf.close();
						if(sb.indexOf("<batch-member>",0)!=-1)
						{
							//System.out.println("This is Duckling Article : ");
							return true;
						}
						else
						{
							//System.out.println("This is Non Duckling Article : ");
							return false;
						}
					}
					catch(Exception e)
					{
						System.out.println("Error in reading Order xml. 37");
						e.printStackTrace();
						return false;
					}
	    	}
			else
			{ 
				temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
				//System.out.println("temp==> "+temp);
				File fe= new File(temp);
				if(fe.exists())
				{
					File[] ast = fe.listFiles();
					for(int i=0; i<ast.length;i++)
					{
						//System.out.println("ast[i].getName()==> "+ast[i].getName());
						if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
						{
							
							stage="\\"+stage+"RESUPPlY";
							//System.out.println("stage==> "+stage);
						}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
						{
							stage="\\RSVP\\";
						}
					}
					
				}
				else
				{
					//System.out.println(" else temp==> "+temp+":");
				}
				//odrPath
				//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER\\";
				String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER\\";
				//System.out.println("\n OrderPath : "+OrderPath);
		            File fl =new File(OrderPath);
		            if(!fl.exists())
		            {
		            	System.out.println("ERROR : Order Not found for jid "+jid +" aid "+aid+" stage "+stage);
		            	System.exit(0);
		            }
		            File[] flist=fl.listFiles();
		            if(flist[0].getName().toLowerCase().endsWith(".xml"))
		            {
		            	filename=flist[0].getName();
		            }
		            RandomAccessFile raf =null;
		            try
		            {
		            	raf=new RandomAccessFile(new File(OrderPath+filename),"r");
		            	while((line=raf.readLine())!=null)
		            	{
		            		sb.append(line);
		            	}
		            	raf.close();
		            }
		            catch(Exception e)
		            {
		            	System.out.println("Error : While reading OrderFile :  "+e.toString());
		            }
					//System.out.println("orser "+sb.indexOf("<batch-member>",0));
		            if(sb.indexOf("<batch-member>",0)!=-1)
		            {
		            	//System.out.println("This is Duckling Article : ");
		            	return true;
		            }
		            else
		            {
		            	//System.out.println("This is Non Duckling Article : ");
		            	return false;
		            }
            }
	}
	//*****************************************************************[23/06/2008]***********************
public boolean readOrderFileForOnch()
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		//String temp="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid.trim();
		
			String temp="";
			if(odrPathForFMS.equalsIgnoreCase("yes"))
			{
			temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
			if(!new File(temp).exists())
			if(checkDuckling())
			{
				temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
			}
			//System.out.println("FMS Order Path:  "+temp);
			String check_order_file=temp;
			/*CheckXmlParser dom = new CheckXmlParser();
	    		if(dom.parseXML(check_order_file))
	    		{
	    				
	    		}
	    		else
	    		{
	    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
	    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
	    			System.exit(1);
						}
	    			
	    		}*/
	    		try
	    		{
	    			String s4="";
					
					raf  = new RandomAccessFile(temp, "r");
					  do
					  {
						if((s4 = raf.readLine()) == null)
					      {  break;}
						else
						  {
							sb.append(s4);
						  }
					  }
					  while(true);
					raf.close();
						    		
		    		if(sb.indexOf("<item-group-description>",0)!=-1)
					{
						int i=sb.indexOf("<item-group-description>",0);
						int j=0;
						String lab="";
						if(i !=-1){
						 j=sb.indexOf("</item-group-description>",i+"<item-group-description>".length());
							if(j!=-1)
							{
								lab=sb.substring(i+"<item-group-description>".length(),j);
								label=lab;
							}
						
							//System.out.println("ONCH Label........ "+lab);
						
						}
					}
				    if(label.equalsIgnoreCase("START chapter"))
					{
						//System.out.println("START chapter.... True");
						return true;
					}
					else
					{
						//System.out.println("Not found e-extra");
						return false;
					}
				}
	    		catch(Exception e)
	    		{
	    			System.out.println("Error in reading Order xml. 38");
	    			e.printStackTrace();
	    			return false;
	    		}
	    	}
			else
			{
			
			temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
			//System.out.println("Order Path==> "+temp);
			File fe= new File(temp);
			if(fe.exists())
			{
				File[] ast = fe.listFiles();
				for(int i=0; i<ast.length;i++)
				{
					//System.out.println("ast[i].getName()==> "+ast[i].getName());
					if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
					{
						
						stage="\\"+stage+"RESUPPlY";
						//System.out.println("stage==> "+stage);
					}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
					{
						stage="\\RSVP\\";
					}
				}
				
			}
			else
			{
				//System.out.println(" else temp==> "+temp+":");
			}
			//odrPath
			//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			//System.out.println("\n OrderPath : "+OrderPath);
			//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
			File file = new File(OrderPath);
			if(!file.exists())
		    	{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order not found for jid "+jid+" aid "+aid+" stage "+stage);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"Order not Found");
							System.exit(1);
						}
		    	}
		    File[] assets = file.listFiles();
		    	//System.out.println("No of input files::"+assets.length);
		    for(int j=0;j<assets.length;j++)
		    {
		    	if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File. Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4.toLowerCase());
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml. 39");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
		    }
			if(sb.indexOf("<item-group-description>",0)!=-1)
			{
				int i=sb.indexOf("<item-group-description>",0);
				int j=0;
				String lab="";
				if(i !=-1){
				 j=sb.indexOf("</item-group-description>",i+"<item-group-description>".length());
					if(j!=-1)
					{
						lab=sb.substring(i+"<item-group-description>".length(),j);
						label=lab;
					}
				
					//System.out.println("ONCH Label........ "+lab);
				
				}
			}
		    if(label.equalsIgnoreCase("START chapter"))
			{
				//System.out.println("START chapter.... True");
				return true;
			}
			else
			{
				//System.out.println("Not found e-extra");
				return false;
			}
		}
	}

public boolean iSOnlineVersionPrint()
{
	RandomAccessFile raf=null;
	StringBuffer sb=new StringBuffer();

	String temp="";
	if(odrPathForFMS.equalsIgnoreCase("yes"))
	{
		temp=System.getProperty("user.dir")+"\\fms_readonly\\OrdersDtd\\order.xml";
		if(!new File(temp).exists())
		if(checkDuckling())
		{
			temp="d:\\order\\"+stage+"\\"+jid+"\\"+aid+"\\order.xml";
		}
		String check_order_file=temp;
		try
		{
			String s4="";

			raf  = new RandomAccessFile(temp, "r");
			do
			{
				if((s4 = raf.readLine()) == null)
				{  break;}
				else
				{
					sb.append(s4);
				}
			}
			while(true);
			raf.close();

			if(sb.indexOf("<online-version type=\"print\"/>",0)!=-1)
			{
				return true;
			}
			else
			{
				return false;
			}
		}
		catch(Exception e)
		{
			System.out.println("Error in reading Order xml. 40");
			e.printStackTrace();
			return false;
		}
	}
	else
	{

		temp=odrPath+jid.toUpperCase()+"\\"+aid.trim();
		File fe= new File(temp);
		if(fe.exists())
		{
			File[] ast = fe.listFiles();
			for(int i=0; i<ast.length;i++)
			{
				//System.out.println("ast[i].getName()==> "+ast[i].getName());
				if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase(stage+"RESUPPlY"))
				{
					stage="\\"+stage+"RESUPPlY";
					//System.out.println("stage==> "+stage);
				}else if(ast[i].isDirectory() && ast[i].getName().equalsIgnoreCase("RSVP"))
				{
					stage="\\RSVP\\";
				}
			}
		}
		else
		{
			//System.out.println(" else temp==> "+temp+":");
		}
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
		{
			if(xt.serverstatus.length()>0)
			{
				xt_log.info("[ERROR] Order not found for jid "+jid+" aid "+aid+" stage "+stage);
				return false;
			}
			else{
				JOptionPane.showMessageDialog(null,"Order not Found");
				System.exit(1);
			}
		}
		File[] assets = file.listFiles();

		for(int j=0;j<assets.length;j++)
		{
			if(assets[j].isFile()&&(assets[j].getName().toLowerCase().endsWith(".xml")))
			{
				String check_order_file=OrderPath+"\\"+assets[j].getName();
				try
				{
					String s4="";

					raf  = new RandomAccessFile(assets[j], "r");
					do
					{
						if((s4 = raf.readLine()) == null)
						{  break;}
						else
						{
							sb.append(s4.toLowerCase());
						}
					}
					while(true);
					raf.close();
				}
				catch(Exception e)
				{
					System.out.println("Error in reading Order xml. 41");
					e.printStackTrace();
					return false;
				}
			}
		}
		if(sb.indexOf("<online-version type=\"print\"/>",0)!=-1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}

	public boolean readOrderFileForOnch(String Vol_Iss_No)throws IOException
	{
		RandomAccessFile raf=null;
		StringBuffer sb=new StringBuffer();
		

		//String OrderPath="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		String OrderPath=odrPath+jid.toUpperCase()+"\\"+Vol_Iss_No;//+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		//String OrderPath="c:\\data\\"+jid.toUpperCase()+"\\"+aid+"\\"+stage.toUpperCase()+"\\CURRENT ORDER";
		File file = new File(OrderPath);
		if(!file.exists())
	    	{//odrPath
	    		//String temp_order_path="\\\\td-nas\\Elsinpt\\VIEWER\\ORIGINALORDERS\\"+jid.toUpperCase()+"\\"+aid;
				String temp_order_path=odrPath+jid.toUpperCase()+"\\"+aid;
	    		File RSVP_File= new File(temp_order_path);
	    		if(!RSVP_File.exists())
	    		{
					if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Order not found for jid "+jid+" aid "+aid);
							return false;
						}
						else{
							JOptionPane.showMessageDialog(null,"Order not Found");
							System.exit(1);
						}
	    		}
	    		else
	    		{
	    			OrderPath=temp_order_path;
	    		}
	    		
	    	}
	    file = new File(OrderPath);
	    File [] f=file.listFiles();
	    boolean stg=false;
	    String Stage_name="";
			boolean S300=false;
			boolean F300=false;
			boolean P100=false;
			boolean S300R=false;
			boolean F300R=false;
			boolean P100R=false;
	    for(int i=0;i<f.length;i++)
	    {
	    	if(f[i].isDirectory())
	    	{
	    		//f[i].getName().equalsIgnoreCase("S300RESUPPLY")
				if(f[i].getName().equalsIgnoreCase("S300"))
	    		{
	    			S300=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("S300RESUPPLY"))
	    		{
	    			S300=false;
					S300R=true;
					P100R=true;
	    		}
				else if(f[i].getName().equalsIgnoreCase("P100RESUPPLY"))
	    		{
	    			P100R=true;
	    		}

	    	}
	    }
	    File[] assets = file.listFiles();
	    	//System.out.println("No of input files::"+assets.length);
	    	
	    for(int jj=0;jj<assets.length;jj++)
	    {
	    	if(assets[jj].isDirectory())
	    	{
	    		//System.out.println("Folder Name: "+assets[jj].getName());
	    		if(assets[jj].getName().equalsIgnoreCase("S300RESUPPLY")&& S300R==true)
	    		{
	    			Stage_name="S300RESUPPLY";
	    			OrderPath+="\\S300RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 1 OrderPath : "+OrderPath);
	    		}
	    		if(assets[jj].getName().equalsIgnoreCase("S300") && S300R==false && S300==true && F300R==false && F300==false && P100R==false && P100==false)
	    		{
	    			Stage_name="S300";
	    			OrderPath+="\\S300\\CURRENT ORDER";
	    			//System.out.println("\n 2 OrderPath : "+OrderPath);
	    		}
				else if((assets[jj].getName().equalsIgnoreCase("P100RESUPPLY"))&& S300R==false && S300==false && P100R==true)
	    		{
					
	    			Stage_name="P100RESUPPLY";
	    			OrderPath+="\\P100RESUPPLY\\CURRENT ORDER";
	    			//System.out.println("\n 3 OrderPath : "+OrderPath);
	    		}
	    		else if((assets[jj].getName().equalsIgnoreCase("P100"))&& S300R==false && S300==false && P100R==false)
	    		{
	    			Stage_name="P100";
	    			OrderPath+="\\P100\\CURRENT ORDER";
	    			//System.out.println("\n 4 OrderPath : "+OrderPath);
	    		}
	    		else if(assets[jj].getName().equalsIgnoreCase("RSVP"))
	    		{
	    			OrderPath+="\\RSVP\\CURRENT ORDER";
	    			//System.out.println("\n 5 OrderPath : "+OrderPath);
	    		}
	    		
	    	}
			 File Order_Xml_File= new File(OrderPath);
			 File[] assets1 = Order_Xml_File.listFiles();
			 for(int j=0;j<assets1.length;j++)
			 {
			
					
				if(assets1[j].isFile()&&(assets1[j].getName().toLowerCase().endsWith(".xml")))
		    	{
		    		String check_order_file=OrderPath+"\\"+assets1[j].getName();
		    		/*CheckXmlParser dom = new CheckXmlParser();
		    		if(dom.parseXML(check_order_file))
		    		{
		    				
		    		}
		    		else
		    		{
		    			//JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.");
						if(xt.serverstatus.length()>0)
						{
							xt_log.info("[ERROR] Parsing Error: Invalid Order File.Message: "+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
							//return false;
							System.exit(1);
						}
						else{
		    			JOptionPane.showMessageDialog(null," Parsing Error: Invalid Order File.\nMessage: \n"+dom.error.substring(dom.error.indexOf(":")+2,dom.error.length())+"\nContact S/W Dept.");
		    			System.exit(1);
						}
		    			
		    		}*/
		    		try
		    		{
		    			String s4="";
						
						raf  = new RandomAccessFile(assets1[j], "r");
						  do
						  {
							if((s4 = raf.readLine()) == null)
						      {  break;}
							else
							  {
								sb.append(s4.toLowerCase());
							  }
						  }
						  while(true);
						raf.close();
		    		}
		    		catch(Exception e)
		    		{
		    			System.out.println("Error in reading Order xml File. 42");
		    			e.printStackTrace();
		    			return false;
		    		}
		    		
		    		
		    		
		    	}
	    	}
	    }
	    if(sb.indexOf("<item-group-description>",0)!=-1)
		{
			int i=sb.indexOf("<item-group-description>",0);
			int j=0;
			String lab="";
			if(i !=-1){
			 j=sb.indexOf("</item-group-description>",i+"<item-group-description>".length());
				if(j!=-1)
				{
					lab=sb.substring(i+"<item-group-description>".length(),j);
					label=lab;
				}
			
				//System.out.println("ONCH Label........ "+lab);
			
			}
		}
	    if(label.equalsIgnoreCase("START chapter"))
		{
			//System.out.println("START chapter.... True");
			return true;
		}
		else
		{
			//System.out.println("Not found e-extra");
			return false;
		}
		//return false;
	}
	//*****************************************************************************************************
	
}

