//CheckXmlParser
package tp.xt;

import java.io.*;
import org.w3c.dom.*;
import org.xml.sax.*;
import javax.xml.parsers.*;
import javax.xml.validation.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import java.util.*;
import java.io.*;
public class CheckXmlParser 
{
	Document doc;
 
 	public static String error;
	public boolean result1=true;
    public boolean parseXML(String filename)
    {
		//	 This constructor is used to parse the xml file using DOM.
    	 ResourceBundle rbs=ResourceBundle.getBundle("xt");
		 String dtdpath=rbs.getString("dtdPath");
			//System.out.println("dtdpath : "+dtdpath);
    	boolean result=false;
        try
        {
        	////wip/S2TConv:\j2sdk1.4.2\lib\TarNih\dtd
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	        factory.setValidating(true);
	        //doc = factory.newDocumentBuilder().parse(new FileInputStream(filename), "C:/dtd/");
	        //doc = factory.newDocumentBuilder().parse(new FileInputStream(filename), "file:////wip/S2TConv/j2sdk1.4.2/lib/TarNih/dtd/");
			doc = factory.newDocumentBuilder().parse(new FileInputStream(filename), dtdpath);
			result=true;
  
	    }
        catch(SAXException e)
        {
        	System.out.println("XML is INVALID.. SAX Error: "+e.getMessage());
			//e.printStackTrace();
        	error=e.toString();
        	result=false;
        }
	    catch(ParserConfigurationException e)
        {
        	System.out.println("XML is INVALID...\r\n Config Error: "+e.getMessage());
        	error=e.toString();
        	result=false;
        }
        catch(UTFDataFormatException e)
        {
			System.out.println("XML is INVALID...\r\n UTFDataFormatException Error: "+e.getMessage());
        	error=e.toString();
            result=false;
        }        
        catch(IOException e)
        {
        	System.out.println("XML is INVALID...\r\n IO Error: "+e.getMessage());
        	error=e.toString();
        	result=false;
        }
        catch(Exception e)
        {
        	System.out.println("XML is INVALID...\r\n Exception Error: "+e.getMessage());
        	error=e.toString();
        	result=false;
        }

        return result;
    }
            	
			 


	/*
	
	public boolean parseXML(String filename)
    {
    	
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setErrorHandler(new org.xml.sax.ErrorHandler() {
			
				public void fatalError(SAXParseException exception)throws SAXException { System.out.println("Ravi");result1=false; }
				
				public void error(SAXParseException e)throws SAXParseException {
					System.out.println("Error at " +e.getLineNumber() + " line.");
					System.out.println(e.getMessage());
					error=e.getMessage();
					System.out.println("1"); 
					errorOccured();
					//result1=false;
					//return false;
				}
				//Show warnings
				public void warning(SAXParseException err)throws SAXParseException{
					System.out.println(err.getMessage());
					System.out.println("2"); 
					error=err.getMessage();
					errorOccured();
					//result1=false;
					//return false;
				}
			});
		
			Document xmlDocument = builder.parse(new FileInputStream(filename));
			DOMSource source = new DOMSource(xmlDocument);
			//StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult();
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			//"file:////wip/S2TConv/j2sdk1.4.2/lib/TarNih/dtd/
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "c:\\dtd\\");
			//transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "file:////wip/S2TConv/j2sdk1.4.2/lib/TarNih/dtd/");
			transformer.transform(source, result);
			if(result1==false)
				return false;
			return true;
		}
		catch (Exception e) {
			System.out.println("Error "+e.getMessage());
			e.printStackTrace();
			error=e.getMessage();
			return false;
		}
	}
	public void errorOccured()
	{
		result1=false;
	}
	*/
	/*public static void main(String[] args)
    {
    	CheckXmlParser dom = new CheckXmlParser();
    	
    	if(dom.parseXML("C:/PROJECTS/XT/XT-LIVE/tx1.xml"))
    		System.out.println("XML is VALID...");
    	else
    		System.out.println("XML is INVALID...");
    }*/




}
