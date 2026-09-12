package tp.xt;
import java.sql.*;
import java.io.*;
import java.util.*;

class  Connect
{
	Connection con;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public boolean dbConnect(String driver, String url) throws IOException,SQLException,ClassNotFoundException
	{
		
	
		String dbUsr="ptsuser";	
		String dbPwd="ptsuser";

		boolean result=false;	
		
		try
		{
			
			Class.forName(driver);
			con = DriverManager.getConnection(url,dbUsr,dbPwd);//192.10.0.108
			//pstmt = con.prepareStatement();
			result=true;
		}		
		catch(java.lang.ClassNotFoundException e) {
			result=false;
			System.err.print("ClassNotFoundException: "); 
			System.err.println(e.getMessage());
		}
		catch(SQLException ex) {
			result=false;
			System.err.println("SQLException: " + ex.getMessage());
		}
		


		return result;		
	}//end of dbConnect()
//check for Resupply satge of a supply stage 
public String CheckStageForResupply(String jid,String aid, String stageDesc)
	{
		int stage=0;
		int resupply_stage=0;
		if(stageDesc.equalsIgnoreCase("S100"))
		{
			stage=1;
			resupply_stage=6;
		}
		else if(stageDesc.equalsIgnoreCase("S200"))
		{
			stage=2;
			resupply_stage=7;
		}
		else if(stageDesc.equalsIgnoreCase("S300"))
		{
			stage=3;
			resupply_stage=8;
		}
		else if(stageDesc.equalsIgnoreCase("P100"))
		{
			stage=4;
			resupply_stage=9;
		}
		else if(stageDesc.equalsIgnoreCase("F300"))
		{
			stage=5;
			resupply_stage=15;
		}
		
		String sub="";
		String query="SELECT 'YES'"+
					 " FROM ITEMMASTER, ITEMDETAILS"+
					 " WHERE ITEMMASTER.itemwtncode = ITEMDETAILS.itemwtncode"+
					 " AND ITEMMASTER.projcode = '"+jid.toUpperCase()+"'"+
					 " AND ITEMMASTER.itemid = '"+aid+"'"+
					 " AND ITEMDETAILS.stagecode = "+stage+
					 " AND ITEMMASTER.custcode=1"+
					 " AND  EXISTS ("+
			         " SELECT 1"+
                     " FROM itemmaster, itemdetails"+
					 " WHERE itemmaster.itemwtncode = itemdetails.itemwtncode"+
					 " AND itemmaster.projcode = '"+jid.toUpperCase()+"'"+
					 " AND itemmaster.itemid = '"+aid+"'"+
					 " AND itemdetails.stagecode = "+resupply_stage+
					 " AND ITEMMASTER.custcode=1 )";
		try{
			pstmt=con.prepareStatement(query);
			rs=pstmt.executeQuery();
			while(rs.next())
			{
				sub=rs.getString(1);
			}
			System.out.println("\nSub --> "+sub);
		}
		catch(SQLException ex) {		
			System.err.println("SQLException: " + ex.getMessage());
		}
			return sub;
	}


	public String RSVPQuery(String query, String jid,String aid) throws SQLException, ClassNotFoundException
	{
		String RSVP_Stage="";
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,jid.toUpperCase());
		pstmt.setString(2,aid);
		
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			RSVP_Stage=rs.getString(1);
		}
		if(RSVP_Stage.equals("17"))
		{
			RSVP_Stage="RSVP";
		}
		else
		{
			RSVP_Stage="NO_RSVP";
		}

		return RSVP_Stage;
	}
	public String OVQuery(String query, String jid,String aid, String stageDesc) throws SQLException, ClassNotFoundException{
		String onlineVersionType="";
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,jid.toUpperCase());
		pstmt.setString(2,aid);
		pstmt.setString(3,stageDesc.toUpperCase());
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			onlineVersionType=rs.getString(1);
		}
		return onlineVersionType;	
	
	}

	public String ANIREPQuery(String query, String jid, String aid, String stageDesc) throws SQLException, ClassNotFoundException
	{
		//System.out.println("anirepQuery--> "+query);
		//	(SELECT ITEMDETAILS.itemgroupdesc FROM ITEMMASTER, ITEMDETAILS WHERE ITEMMASTER.itemwtncode = ITEMDETAILS.itemwtncode AND ITEMMASTER.projcode = ? AND ITEMMASTER.itemid = ? AND ITEMDETAILS.stagecode = (SELECT STAGECUSTOMER.stagecode FROM STAGECUSTOMER WHERE STAGECUSTOMER.description = ? AND STAGECUSTOMER.custcode = 1) AND ITEMMASTER.custcode = 1)
		String desc="";
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,jid.toUpperCase());
		pstmt.setString(2,aid);
		//pstmt.setString(3,stageDesc.toUpperCase());
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			desc = rs.getString(1);
		}
		return desc;	
	
	}
	
	public String stoneQuery(String query, String jid, String aid, String stageDesc) throws SQLException, ClassNotFoundException
	{
		String desc="";
		String stage = "";
		if(stageDesc.equalsIgnoreCase("S100"))
			stage = "6";
		else if(stageDesc.equalsIgnoreCase("S200"))
			stage = "7";
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,stage);
		pstmt.setString(2,jid.toUpperCase());
		pstmt.setString(3,aid);

		rs=pstmt.executeQuery();
		while(rs.next())
		{
			desc = rs.getString(1);
		}
		return desc;	
	}

	public String DucklingQuery(String query, String jid,String aid, String stageDesc) throws SQLException, ClassNotFoundException{
		String Duckling="";
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,jid.toUpperCase());
		pstmt.setString(2,aid);
		pstmt.setString(3,stageDesc.toUpperCase());
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			Duckling=rs.getString(1);
		}
		return Duckling;	

	}
	public Vector SUBITEMSQuery(String query, String jid,String aid, String stageDesc) throws SQLException, ClassNotFoundException{
		Vector subitems=new Vector();
		pstmt=con.prepareStatement(query);
		pstmt.setString(1,jid.toUpperCase());
		pstmt.setString(2,aid);
		pstmt.setString(3,stageDesc.toUpperCase());
		rs=pstmt.executeQuery();
		while(rs.next())
		{
			subitems.addElement(rs.getString(1));
		}
		return subitems;	

	}
	public void cleanupDbObjects()
	{
		try{
		if(con!=null)
			con.close();
		}
		catch(SQLException ex) {		
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

}
