package Bot.dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Bot.Constants;

public class Dbhandler {
	private final static String myclass = Dbhandler.class.getName();


	///-------------------------------Getting the connection with the sql-----------------------------------//	
		public Connection getConnection()
		{
			Connection con=null;
			try
			{
				Class.forName(Constants.regDriver);
				con=DriverManager.getConnection(Constants.dbURL+Constants.unicode,Constants.uname,Constants.pwd);
			}
			catch(Exception e)
			{
				//System.out.println("Exception in connection establishment ");
				e.printStackTrace();
			}
			
			return con;
		}
		
//-----------------------------------------Set clolumn details------------------------------------//	
		public Boolean setCustomerDetails(String tablename,String context_id, String column_name, String column_value)
		{
			Boolean value = false;
			try {
				
			
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement("select count(1) from "+tablename+" where context_id = ? ;");
			
			ps.setString(1, context_id);
			ResultSet rs = null;
			rs = ps.executeQuery();	        		
    		
			while (rs.next()) {
				int count = rs.getInt("count(1)");
				//System.out.println("count:"+count);
				if (count == 0)
				{
					PreparedStatement pst = con.prepareStatement("insert into "+tablename+" (context_id,"+column_name+") values(?,?);");
					pst.setString(1, context_id);
					pst.setString(2, column_value);
					int i = pst.executeUpdate();
					value = true;
					if (i > 0)
					{
						System.out.println("Recorded!");
					}	
					else
					{
						System.out.println("Error");
					}
				}
				else if (count == 1)
				{
					PreparedStatement pst = con.prepareStatement(
					"update "+tablename+" SET "+column_name+" = ? where context_id = ?;");
					pst.setString(1, column_value);
					pst.setString(2, context_id);
					
					int i = pst.executeUpdate();
					value = true;
					if (i > 0)
					{
						System.out.println("Exists and Updated!");
					}
					else
					{
						System.out.println("Error");
					}
				}
				
			}
			return value;
			} catch (Exception e) {
				
				e.printStackTrace();
				return value;
			}
		
		}
		
//-----------------------------------GET SINGLE COLUM DETAILS FROM DATABASE-----------------------------------------//	
		public String getState(String tablename,String context_id,String column_name)
		{
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String p_state = "";
			try
			{
				con = getConnection();
				ps = con.prepareStatement("select "+column_name+" from "+tablename+" where context_id='"+context_id+"'");
				rs = ps.executeQuery();
				while(rs.next())
				{  System.out.println("data is prsnt");
					p_state = rs.getString(1);
					
				} 
				//System.out.println("DBHandler : getFlagValue for : "+context_id +" value : " + p_state);
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while gettingFlagValues data : ");
				e.printStackTrace();
			}
			finally
			{
				try
				{
				con.close();
				ps.close();
				rs.close();
				}
				catch(Exception e)
				{
					//System.out.println("DBHandler : Exception while closing connection");
					e.printStackTrace();
				}
			}
			return p_state;
		}
		
		
		
		
		
	
}
