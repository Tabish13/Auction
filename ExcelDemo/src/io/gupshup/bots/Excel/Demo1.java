package io.gupshup.bots.Excel;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import io.gupshup.bots.constants.Constants.MSG;
import io.gupshup.bots.dbhandler.DbConnection;
import jxl.read.biff.BiffException;

/**
 * Servlet implementation class Demo1
 */
@WebServlet("/Demo1")
public class Demo1 extends HttpServlet {
	
	DbConnection db = new DbConnection();
	ReadXlData rd = new ReadXlData();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try 
		{
		JSONObject contextobj = new JSONObject(request.getParameter("contextobj"));
		JSONObject messageobj = new JSONObject(request.getParameter("messageobj"));
		String text=messageobj.getString("text");
		String type=messageobj.getString("type");
		String context_id=contextobj.getString("contextid");
		//sendMessage(response, text);
		
		if(type.equals("event"))
		{
			if(db.getColumnValue("context_id", "employee", "context_id", context_id).equals(null))
			{
				Boolean insert=db.insertContext("employee", "context_id", context_id);
				System.out.println(insert);
			}
			setCvalue("state", "1", context_id);
			//db.updateColumn("employee", "state", "1", "context_id", context_id);
			
			sendMessage(response, MSG.WELCOME);
		}
		else if(type.equals("msg"))
		{	
			String state = db.getColumnValue("state", "employee", "context_id", context_id);
			//System.out.println("state : "+state);
			
			if(state.equals("0"))
			{
				setCvalue("state", "1", context_id);
				//db.updateColumn("employee", "state", "1", "context_id", context_id);
				sendMessage(response, MSG.WELCOME);
			}
			
			else if(state.equals("1"))
			{
				String name = getName(context_id);
				//String name = db.getColumnValue("name", "employee", "context_id", context_id);
				ArrayList<String[]>user=rd.readExcel(name);
				if(!user.isEmpty())
				{
					setCvalue("state", "2", context_id);
					//db.updateColumn("employee", "state", "2", "context_id", context_id);
					setCvalue("name", text, context_id);
					//db.updateColumn("employee", "name", text, "context_id", context_id);
					sendMessage(response, MSG.DETAILS);
				}
				else
				{
					setCvalue("state", "0", context_id);
					//db.updateColumn("employee", "state", "0", "context_id", context_id);
					sendMessage(response, MSG.NO_SUCH_NAME);
				}
			}
			else if(state.equals("2"))
			{
				String name = getName(context_id);
				//String name = db.getColumnValue("name", "employee", "context_id", context_id);
				ArrayList<String[]>user=rd.readExcel(name);
				
				if(!user.isEmpty())
				{
					
				//To get the all values of a single row
				String[] getrow = user.get(0); 
				if(text.equals("name"))
				{
					setCvalue("state", "0", context_id);
					//db.updateColumn("employee", "state", "0", "context_id", context_id);
					sendMessage(response, getrow[1] );
				}
				else if(text.equals("phone"))
				{
					setCvalue("state", "0", context_id);
					//db.updateColumn("employee", "state", "0", "context_id", context_id);
					sendMessage(response, getrow[2]);
				}
				else if(text.equals("address"))
				{
					setCvalue("state", "0", context_id);
					//db.updateColumn("employee", "state", "0", "context_id", context_id);
					sendMessage(response, getrow[3]);
				}
				
				}
				else
				{
					sendMessage(response, MSG.NO_SUCH_NAME );
					
				}
			}
			
			
			
			
			
			
			
			
			
			
			}
		
			
		} 
		catch (Exception e) 
		{
			
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	public void sendMessage(HttpServletResponse response, String message) 
	{
	try 
	{
		PrintWriter out= response.getWriter();
		out.println(message);
		out.flush();
		out.close();
	} catch (IOException e) {

		e.printStackTrace();
	}
	}
	
	public boolean setCvalue(String columName, String columnValue, String context_id)
	{
		boolean value = db.updateColumn("employee", columName, columnValue, "context_id", context_id);
		return value;
	}
	public String getName(String context_id)
	{
		String name = db.getColumnValue("name", "employee", "context_id", context_id);
		return name;
	}

}
