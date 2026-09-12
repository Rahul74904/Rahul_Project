package tp.xt;

import java.io.*;
import java.util.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import org.xml.sax.*;
import javax.xml.parsers.SAXParserFactory; 
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;

public class Sax extends DefaultHandler implements EntityResolver{
 
  boolean inside=false;
  boolean insideresolver=false;

  boolean otherref=false;
  boolean bookreviewhead=false;
  String startelement="";
  String doctype="";
  String dtdpath="file:////wip/S2TConv/j2sdk1.4.2/lib/TarNih/dtd/";
  public Sax()
	{
	  ResourceBundle rbs=ResourceBundle.getBundle("xt");
		  dtdpath=rbs.getString("dtdPath");
			//System.out.println("dtdpath : "+dtdpath);
	}
public boolean getBRV(String filename)
	{
		insideresolver=false;
		boolean check=false;
		try{
			parse(filename);
			if(otherref)
				check= true;
		}
		catch(Exception e)
		{	
			System.out.println("[ERROR :] INVALID XML.. \n\n"+e.toString());
			   return false;
	//			System.exit(0);
		}
  	return check;
	}
  public  boolean getValue(String filename){
		boolean check=false;
		try{
		parse(filename);
		
		if(doctype.equalsIgnoreCase("err")&& startelement.equalsIgnoreCase("book-review"))
		{
			check=true;

			//System.out.println("Type: "+startelement.toUpperCase()+"\nDocSubType: "+doctype.toUpperCase()+"\nPIT Check: "+check);
			System.out.println("DocSubType: "+doctype.toUpperCase()+"\nPIT Check1: "+check);
			
		}
		else if(doctype.equalsIgnoreCase("err")&& startelement.equalsIgnoreCase("exam"))
		{
			check=true;
			//System.out.println("Type: "+startelement.toUpperCase()+"\nDocSubType: "+doctype.toUpperCase()+"\nPIT Check: "+check);
			System.out.println("DocSubType: "+doctype.toUpperCase()+"\nPIT Check2: "+check);
		}
		else
		{
			check=false;
			//System.out.println("Type: "+startelement.toUpperCase()+"\nDocSubType: "+doctype.toUpperCase()+"\nPIT Check: "+check);
			System.out.println("DocSubType: "+doctype.toUpperCase()+"\nPIT Check3: "+check);
		}
		}
		catch(Exception e)
		{	
			System.out.println("[ERROR :] INVALID XML.. \n\n"+e.toString());
			   return false;
	//			System.exit(0);
		}
  	return check;
  }
  
	public void startElement(String namespaceURI, String localName,
	                       String qName, Attributes atts) {
//		System.out.println("qName :: "+qName);
	    if(!inside){
	
	    	inside=true;
	    	startelement=qName;
	    	doctype=atts.getValue("docsubtype");
	    }
	    
	    	startelement=qName;
	    	
	    	
			if(startelement.equalsIgnoreCase("book-review-head"))
			{
				bookreviewhead=true;
			}

			if(bookreviewhead)
			{
				
				if(startelement.equalsIgnoreCase("ce:other-ref"))
				{
					//System.out.println("startelement : "+startelement);
					otherref=true;
				}
			}
	}
  

 
	public InputSource resolveEntity(String id_public,String id_system){
	
	  
	  //return new InputSource(new StringReader(""));
	  if(!insideresolver){
	  		insideresolver=true;
	  		id_system=id_system.substring(id_system.lastIndexOf("/"));
	  		return new InputSource(dtdpath+id_system);	
	  	}else{
	  		return new InputSource(id_system);	
	  	}
	  
	 
	}


	public void parse(String filename)throws Exception{
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		SAXParser p = factory.newSAXParser();
		p.parse(new File(filename),this);
		    
	}



  public static void main(String[] args)throws Exception {
    	Sax f = new Sax();
		f.getValue(args[0]);
		f.getBRV(args[0]);
    	System.out.println (f.startelement);
	    System.out.println (f.doctype);
		 System.out.println ("--------"+f.otherref);
  }
 
  
}
