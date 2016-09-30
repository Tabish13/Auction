package Bot;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Bot.dbhandler.Dbhandler;
import soapcalls.GetStages;

/**
 * Servlet implementation class RoznamaBot
 */
@WebServlet("/RoznamaBot")
public class RoznamaBot extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public RoznamaBot() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String check = request.getParameter("contextobj");
		Dbhandler db = new Dbhandler();
		String tablename = "customer_details";
		String botname = "drealestate";
		if(check!=null)
		{
			try {
				PrintWriter out = response.getWriter();
				SendBotMsg sendmsg = new SendBotMsg();
				JSONObject contextobj = new JSONObject(request.getParameter("contextobj"));
				JSONObject messageobj = new JSONObject(request.getParameter("messageobj"));
				JSONObject senderobj = new JSONObject(request.getParameter("senderobj"));
				String type = messageobj.getString("type");
				String msg = messageobj.getString("text").toLowerCase();
				String context_id = contextobj.getString("contextid");
				String state = db.getState(tablename, context_id, "state");
				if(type.equals("event"))
				{
					db.setCustomerDetails(tablename, context_id, "state", "getno");
					out.println("welcome to Roznama Bot.\nPlease  Enter Your Mobile Number.");
				}
				else if(type.equals("msg"))
				{
					if(msg.equals("hi")||msg.equals("hello")||msg.equals("hey"))
					{
						db.setCustomerDetails(tablename, context_id, "state", "getno");
						out.println("welcome to Roznama Bot.\nPlease  Enter Your Phone Number.");
					}
					else if(state.equals("getno"))
					{
						String mobile_regex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
						if(msg.matches(mobile_regex))
						{
							db.setCustomerDetails(tablename, context_id, "mobile", msg);
							db.setCustomerDetails(tablename, context_id, "state", "getotp");
							out.println("Please  Enter OTP.");
						}
						else
						{
							out.println("Please enter valid mobile number.");
						}
						
					}
					else if(state.equals("getotp"))
					{
						//get the otp from db and replace the '1234'
						if(msg.equals("1234"))
						{
							db.setCustomerDetails(tablename, context_id, "state", "getcaseid");
							out.println("Please Enter the Case ID.");
						}else
						{
							out.println("Enter Valid OTP.");
						}
					}
					else if(state.equals("getcaseid"))
					{
						db.setCustomerDetails(tablename, context_id, "case_id", msg);
						db.setCustomerDetails(tablename, context_id, "state", "choosecase");
						out.println("{\"type\":\"survey\",\"question\":\"What would you like to do?\",\"options\":[\"view case progress\",\"upload case document\"]}");
				
					}
					else if(state.equals("choosecase"))
					{
						if(msg.equals("view case progress"))
						{
							db.setCustomerDetails(tablename, context_id, "state", "choosecase");
							String case_id = db.getState(tablename, context_id, "case_id");
							// make soap call with case_id give the results.
							GetStages getstage = new GetStages();
							ArrayList<String[]> data = getstage.makeGetStages();
							String print = "";
							for (int i = 0; i < data.size(); i++) {
								String t[]=data.get(i);
								print += "Stage id: "+t[0]+"\nStage detail: "+t[1]+"\n\n";
								if(i%4==0&&i!=0)
								{
									sendmsg.sendMessage(contextobj, print, botname);
									print="";
								}
								System.out.println("STage id: "+t[0]+"\nStage detail: "+t[1]+"\n\n");
							}
							sendmsg.sendMessage(contextobj, print, botname);
							//sendmsg.sendMessage(contextobj,"{\"type\":\"poll\",\"question\":\"Is there anything else i can help you with.\",\"msgid\":\"poll_212\"}" , botname);
							sendmsg.sendMessage(contextobj,"{\"type\":\"survey\",\"question\":\"What would you like to do?\",\"options\":[\"view case progress\",\"upload case document\"]}", botname);
							
						}
						else if(msg.equals("upload case document"))
						{
							out.println("under construction.");
						}
					}
					else if(state.equals("anyhelp"))
					{
						if(msg.equals("yes"))
						{
							db.setCustomerDetails(tablename, context_id, "state", "getcaseid");
							out.println("Please enter the case id.");
						}else if(msg.equals("no"))
						{
							db.setCustomerDetails(tablename, context_id, "state", "done");
							out.println("Thank you for using Roznama.");
						}else
						{
							out.println("enter 'yes' or 'no'.");
						}
					}
					
					else 
					{
						out.println("Sorry can't get you.");
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
