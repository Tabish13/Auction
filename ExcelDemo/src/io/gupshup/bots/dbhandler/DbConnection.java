package io.gupshup.bots.dbhandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection 
{

	public static final String regDriver="com.mysql.jdbc.Driver";
	public static final String dburl="jdbc:mysql://localhost:3306/hrms";
	public static final String dbuname="root";
	public static final String dbpass="root";
	
	
	//Connection method for mysql 
	public Connection getConnection()
	{
		Connection con = null;
		try 
		{
			Class.forName(regDriver);
			con=DriverManager.getConnection(dburl,dbuname,dbpass);
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return con;
	}
	
	//Insert method for a single value generic
	public Boolean insertContext(String tablename, String column_name,String column_value)
	{
		Boolean value = null;
		Connection con=null;
		PreparedStatement ps= null;
		try 
		{
			con=getConnection();
			ps=con.prepareStatement("insert into "+tablename+"("+column_name+") values(?)");
			ps.setString(1, column_value);
			value=ps.execute();
			
			
		} catch (Exception e) 
		{
			value=false;
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				con.close();
				ps.close();
			} catch (SQLException e) 
			{
			
				e.printStackTrace();
			}
			
				
		}
		
		
		
		return value;
	}
	
	//Update method for a single column
	public Boolean updateColumn(String tablename, String set_column_name,String set_column_value, String where_column_name, String where_column_value)
	{
		Boolean value = null;
		Connection con = null;
		PreparedStatement ps=null;
		try 
		{
			con=getConnection();
			ps=con.prepareStatement("update "+tablename+" set "+set_column_name+"=? where "+where_column_name+"="+where_column_value);
			ps.setString(1, set_column_value);
			ps.executeUpdate();
			value=true;;
			
			
		} catch (Exception e) 
		{
			value = false;
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				con.close();
				ps.close();
			} catch (SQLException e) 
			{
			
				e.printStackTrace();
			}
			
				
		}
	
		return value;
	}
	
	//Get column value from the Database
	public String getColumnValue(String select_column_name, String tablename, String where_column_name, String column_value)
	{
		String value = null;
		Connection con= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try 
		{
			con=getConnection();
			ps=con.prepareStatement("select "+select_column_name+" from "+tablename+" where "+where_column_name+" = '"+column_value+"'");
			rs=ps.executeQuery();
			while (rs.next()) 
			{
				value= rs.getString(1);
			}
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				con.close();
				ps.close();
				rs.close();
			} catch (SQLException e) 
			{
			
				e.printStackTrace();
			}
			
				
		}
		return value;
	}
	
	/*//Clossing all connection
	public void closeConnection(Connection con,PreparedStatement ps,ResultSet rs)
	{
		
		try 
		{
			ps.close();
			con.close();
			rs.close();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}*/
	
	
	
}
