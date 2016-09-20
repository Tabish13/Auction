package io.gupshup.bots.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
	
import org.json.JSONObject;

import io.gupshups.bots.util.SendBotMsg.OKHTTP;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;




public class AuctionDbConnection {
	
	
	public static final String regDriver="com.mysql.jdbc.Driver";
	public static final String dbURL="jdbc:mysql://localhost:3306/auction";
	public static final String uname="root";
	public static final String pwd="root";
	
	
///-------------------------------Getting the connection with the sql-----------------------------------//	
	public Connection getConnection()
	{
		Connection con=null;
		try
		{
			Class.forName(regDriver);
			con=DriverManager.getConnection(dbURL,uname,pwd);
		}
		catch(Exception e)
		{
			//System.out.println("Exception in connection establishment ");
			e.printStackTrace();
		}
		return con;
	}
	
	
	
//--------------------------------------------Set the seller details ---------------------------------------------------------------------------------//
	public Boolean set_sellerdetails(String tablename,String context_id)
	{
		Boolean value;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps = con.prepareStatement("insert into "+tablename+"(seller_context_id) values(?)");
			ps.setString(1,context_id);
			ps.execute();
			value = true;
			//System.out.println("DBHandler : Record added for sender_visit : "+context_id);
		}
		catch(Exception e)
		{
			System.out.println("i am in set_sellerdetails ");
			e.printStackTrace();
			value = false;
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	
	//------------------------------------Update the product details of the seller------------------------------------------------------------------------//
	public void update_productdetails(String tablename,String set_column,String where_column,String where_column_value,String set_column_value )
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps = con.prepareStatement("update "+tablename+" set "+set_column+"=? where "+where_column+"="+where_column_value);
			ps.setString(1,set_column_value);
			//ps.setString(2,context_id);
			
			ps.executeUpdate();
			//System.out.println("DBHandler : Flags updated for : "+column_name);
			
		}
		catch(Exception e)
		{
			//System.out.println("DBHandler : Exception while updatingingFlags data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
	}
	
	
	//----------------------------------------------To get the product details from the context_id------------------------------------------------------///
	public String get_productdetails(String tablename,String where_column_value,String select_colum_name,String where)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String data = "";
		try
		{
			con = getConnection();
			ps = con.prepareStatement("select "+select_colum_name+" from "+tablename+" where "+where+"='"+where_column_value+"'");
			rs = ps.executeQuery();
			while(rs.next())
			{  System.out.println("data is prsnt");
				data = rs.getString(1);
				
			} 
			//System.out.println("DBHandler : getFlagValue for : "+context_id +" value : " + data);
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
		return data;
	}
	
	
	
	
	//--------------------------------------------to set the seller state -----------------------------------------------------------//
	public Boolean set_sellerstate(String tablename,String context_id,String state)
	{
		Boolean value;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps = con.prepareStatement("insert into "+tablename+" values(?,?)");
			ps.setString(1,context_id);
			ps.setString(2,state);
			ps.execute();
			value = true;
			//System.out.println("DBHandler : Record added for sender_visit : "+context_id);
		}
		catch(Exception e)
		{
			//System.out.println("DBHandler : Exception while inserting sender_visit data : ");
			e.printStackTrace();
			value = false;
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
		return value;
	}
	
	//-------------------------------------------------update seller state------------------------------------------------------------------------//
	public void update_sellerstate(String tablename,String context_id,String state)
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps = con.prepareStatement("update "+tablename+" set state=? where context_id=?");
			ps.setString(1,state);
			ps.setString(2,context_id);
			
			ps.executeUpdate();
			//System.out.println("DBHandler : Flags updated for : "+state);
			
		}
		catch(Exception e)
		{
			//System.out.println("DBHandler : Exception while updatingingFlags data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
	}
	
	
	//----------------------------------------------------------get the sender current state---------------------------------------------------------------//
	public String get_senderstate(String tablename,String context_id)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String p_state = "";
		try
		{
			con = getConnection();
			ps = con.prepareStatement("select state from "+tablename+" where context_id='"+context_id+"'");
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
	
	
	//-----------------------------------------------get the all product in the product details---------------------------------------------------------//
	public List<String[]> readData(String query)
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		List<String[]> data = new ArrayList<String[]>();
		try
		{
			 con = getConnection();
			 st = con.createStatement();
			 rs = st.executeQuery(query);
			
		ResultSetMetaData rsmd = rs.getMetaData();

		int columnsNumber = rsmd.getColumnCount();
		
		while(rs.next())
		{
			List<String> temp = new ArrayList<String>();
			for(int i=0;i<columnsNumber;i++)
			{
			 temp.add(rs.getString(i+1));
			}
			String[] stockArr = new String[temp.size()];
			stockArr = temp.toArray(stockArr);
			data.add(stockArr);
			
		}
		 
		}
		catch(Exception e)
		{
			System.out.println("DBHandler : Exception while reading data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			 con.close();
			 rs.close();
			 st.close();
			}
			catch(Exception e)
			{
				System.out.println("DBHandler : Exception while closing connection ");
				e.printStackTrace();
			}
		}
		return data;
	}
	
	

	//-------------------------read row data---------------------------------------------------------//
	public List<String[]> readrowData(String query)
	{
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
		
		List<String[]> data = new ArrayList<String[]>();
		try
		{
			 con = getConnection();
			 st = con.createStatement();
			 rs = st.executeQuery(query);
		
		
		
		while(rs.next())
		{
			List<String> temp = new ArrayList<String>();
			
			 temp.add(rs.getString(1));
			
			String[] stockArr = new String[temp.size()];
			stockArr = temp.toArray(stockArr);
			data.add(stockArr);
			
		}
		 
		}
		catch(Exception e)
		{
			System.out.println("DBHandler : Exception while reading data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			 con.close();
			 rs.close();
			 st.close();
			}
			catch(Exception e)
			{
				System.out.println("DBHandler : Exception while closing connection ");
				e.printStackTrace();
			}
		}
		return data;
	}
	
	
	
	//-----------------------------------------get the remaing time of the product to end-----------------------------------------------------------///
	public String remaningTime(String tablename,String context_id, String starttime,String endtime)
	{
		
		long startTime = Long.parseLong(starttime);
	    // do your work...
	    long endTime=Long.parseLong(endtime);
	    long diff=endTime-startTime;       
	    long hours=TimeUnit.MILLISECONDS.toHours(diff);
	   diff=diff-(hours*60*60*1000);
	    long min=TimeUnit.MILLISECONDS.toMinutes(diff);
	 diff=diff-(min*60*1000);
	    long seconds=TimeUnit.MILLISECONDS.toSeconds(diff);
	System.out.println(hours+":"+min+":"+seconds);



	return hours+":"+min+":"+seconds;
	}
	
	
	
	//-------------------------------insert bidder details in bidder table------------------------------------------------//
	
	public Boolean set_bidderdetails(String tablename,String bidder_context_id,String bidder_context, String product_id, String bid_price )
	{
		Boolean value;
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps = con.prepareStatement("insert into "+tablename+" values(?,?,?,?)");
			ps.setString(1,bidder_context_id);
			ps.setString(2,bidder_context);
			ps.setString(3,product_id);
			ps.setString(4,bid_price);
			ps.execute();
			value = true;
			//System.out.println("DBHandler : Record added for sender_visit : "+context_id);
		}
		catch(Exception e)
		{
			System.out.println("i am in set_sellerdetails ");
			e.printStackTrace();
			value = false;
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
		return value;
	}
	
	
	
	
	
	//-------------------------------------------------get the endtime that is 24 after the adding time in milliseconds---------------------------------------------------------------//
	public String get_endtime(String product_id)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String endtime = "";
		try
		{
			con = getConnection();
			ps = con.prepareStatement("select endtime from productdetails where product_id='"+product_id+"'");
			rs = ps.executeQuery();
			while(rs.next())
			{  System.out.println("data is prsnt");
				endtime =String.valueOf(rs.getString(1));
				
			} 
			//System.out.println("DBHandler : getFlagValue for : "+context_id +" value : " + data);
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
		return endtime;
	}
	
	
	public void deletebidder(String tablename,String bidder_context_id,String product_id)													
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps =  con.prepareStatement("delete from "+tablename+" where bidder_context_id=? and product_id=?");
			ps.setString(1,""+bidder_context_id);
			ps.setString(2,""+product_id);
			ps.executeUpdate();
			System.out.println("Record deleted for product id :" + bidder_context_id);
		}
		catch(Exception e)
		{
			//System.out.println("DBHandler : Exception while deletingSender data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
	}
	
	
	
	public void deleterow(String tablename,String bidder_context_id,String where)													
	{
		Connection con = null;
		PreparedStatement ps = null;
		try
		{
			con = getConnection();
			ps =  con.prepareStatement("delete from "+tablename+" where "+where+"=?");
			ps.setString(1,""+bidder_context_id);
			
			ps.executeUpdate();
			System.out.println("Record deleted for product id :" + bidder_context_id);
		}
		catch(Exception e)
		{
			//System.out.println("DBHandler : Exception while deletingSender data : ");
			e.printStackTrace();
		}
		finally
		{
			try
			{
			con.close();
			ps.close();
			}
			catch(Exception e)
			{
				//System.out.println("DBHandler : Exception while closing connection");
				e.printStackTrace();
			}
		}
	}
	
	
	/*
	public String getCurrentDate_time()
	{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("IST"));
		SimpleDateFormat myformat = new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss");
		String dstarttime = myformat.format(cal.getTime());
		return dstarttime;
	}
	public String stopAfter24Hours(String stime) throws ParseException{
		final SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy  hh:mm:ss");
		final Date date = format.parse(stime);
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		return format.format(calendar.getTime()); 
	}
	*/
	
	public void sendMessage(JSONObject context, String message, String bot) 
	{
		String url = OKHTTP.getURL(bot);
		String request_body = OKHTTP.getRequestBody(context, message);
		try {
			OkHttpClient client = new OkHttpClient();
//			client.setConnectTimeout(100, TimeUnit.SECONDS); 
//			client.setReadTimeout(100, TimeUnit.SECONDS); 
			MediaType mediaType = MediaType
					.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(mediaType, request_body);
			Request request = new Request.Builder()
					.url(url)
					.post(body)
					.addHeader("content-type","application/x-www-form-urlencoded")
					.addHeader("accept", "application/json")
					.addHeader("apikey", OKHTTP.apikey)
					.addHeader("cache-control", "no-cache")
					.addHeader("postman-token","1e7263b2-d121-364d-ca0c-7c6e64306420").build();

			Response response = client.newCall(request).execute();
			response.body().close();

	} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public String get_product_id(String tablename,String product_id,String colum_name)
	{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String data = "";
		try
		{
			con = getConnection();
			ps = con.prepareStatement("select "+colum_name+" from "+tablename+" where product_id='"+product_id+"'");
			System.out.println("dddd"+product_id);
			rs = ps.executeQuery();
			while(rs.next())
			{  System.out.println("data is prsnt");
				 data =rs.getString(1) ;
				
				System.out.println(data);
				
			} 
			//System.out.println("DBHandler : getFlagValue for : "+context_id +" value : " + data);
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
		return data;
	}
	
	
	
	

	
}
