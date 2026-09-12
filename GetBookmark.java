package tp.xt;

import org.w3c.dom.*;
import javax.xml.parsers.*;

class GetBookmark
{
	public static void main(String args[])throws Exception
	{
		new GetBookmark(args[0]);
	}
	
	public GetBookmark(String url)throws Exception
	{
		DocumentBuilderFactory factory= DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setNamespaceAware(true);
		DocumentBuilder builder= factory.newDocumentBuilder();
		Document doc=builder.parse(url);
		
		/*Node child;
		NodeList nodeList= doc.getElementsByTagName("jid");
		int count = nodeList.getLength();
		for(int i=0; i<count; i++)
		{	
			child= nodeList.item(i);
			printElement((Element) child);
			//System.out.println(child.getNodeValue());
			//displayTree(child);
		}*/
		displayTree(doc.getDocumentElement());
	}	
	
	protected void displayTree(Node node)
	{
		short nodeType= node.getNodeType();
		switch(nodeType)
		{
			case Node.ELEMENT_NODE:	printElement((Element) node);
									break;
			case Node.TEXT_NODE: printText((Text) node);
									break;
			case Node.COMMENT_NODE: printComment((Comment)node);
									break;
			case Node.CDATA_SECTION_NODE: printCDATA((CDATASection)node);
									break;
			case Node.ENTITY_NODE:printEntityReference((EntityReference) node);
									break;
			case Node.ENTITY_REFERENCE_NODE: printEntityReference((EntityReference) node);
									break;
			case Node.PROCESSING_INSTRUCTION_NODE: printProcessingInstruction((ProcessingInstruction) node);
									break;
			default	: break;
		}
	}

	protected void printComment(Comment node)
	{
		System.out.print("<!--"+node.getData()+"-->");
	}
	
	protected void printText(CharacterData node)
	{
		System.out.print(node.getData());
	}
	
	protected void printCDATA(CDATASection node)
	{
		System.out.print("<![CDATA["+node.getData()+"]]");
	}
	
	protected void printEntityReference(EntityReference node)	
	{
		System.out.print("&"+node.getNodeName()+";");
	}
	
	protected void printProcessingInstruction(ProcessingInstruction node)
	{
		System.out.print("<?"+node.getTarget()+" "+node.getData()+"?>");
	}

	protected void printElement(Element node)
	{		
		Node child;
		Attr attr;
		System.out.println();
		StringBuffer nodeName= new StringBuffer("<"+node.getNodeName());
		//System.out.print("<"+node.getNodeName());
		/*if(nodeName.equals("ce:section"))
		{
			System.out.println("***********************************************************");
			System.out.println("Next Sibling : "+(node.getNextSibling()).getNodeName());
			System.out.println("Next Sibling : "+(node.getNextSibling()).getNodeName());
			System.out.println("***********************************************************");
			System.exit(0);
		}*/
		
		NamedNodeMap attrs= node.getAttributes();
		int count= attrs.getLength();
		for(int i=0; i<count; i++)
		{
			attr= (Attr)(attrs.item(i));
			nodeName.append(" "+attr.getName()+" = \""+attr.getValue()+"\"");
			//System.out.print(" "+attr.getName()+" = \""+attr.getValue()+"\"");
		}
		nodeName.append(">");
		//System.out.print(">");
		System.out.println("nodeName :"+nodeName);

		NodeList children= node.getChildNodes();
		count = children.getLength();
		for(int i=0; i<count; i++)
		{	
			child= children.item(i);
			displayTree(child);			
		}
		System.out.print("</"+node.getNodeName()+">");
	}
	
	public String getAttributeValue(Element element, String attr)
	{
		String attrValue=null;
		// Determine the presence of an attribute
	    boolean has = element.hasAttribute(attr);
	    if(has== true)
	    // Get an attribute value; returns null if not present
	    attrValue = element.getAttribute(attr); // value1
	    return attrValue;
	}
	
}
