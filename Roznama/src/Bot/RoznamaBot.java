package Bot;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Bot.dbhandler.Dbhandler;

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
		if(check!=null)
		{
			try {
				PrintWriter out = response.getWriter();
				JSONObject contextobj = new JSONObject(request.getParameter("contextobj"));
				JSONObject messageobj = new JSONObject(request.getParameter("messageobj"));
				JSONObject senderobj = new JSONObject(request.getParameter("senderobj"));
				String type = messageobj.getString("type");
				String msg = messageobj.getString("text");
				String context_id = contextobj.getString("contextid");
				String state = db.getState(tablename, context_id, "state");
				if(type.equals("event"))
				{
					db.setCustomerDetails(tablename, context_id, "state", "start");
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
						out.println("choose one \nview case progress \nupload case document");
					}
					else if(state.equals("choosecase"))
					{
						if(msg.equals("view case progress"))
						{
							db.setCustomerDetails(tablename, context_id, "state", "anyhelp");
							String case_id = db.getState(tablename, context_id, "case_id");
							// make soap call with case _id give the results.
						}
						else if(msg.equals("upload case document"))
						{
							
						}
					}
					else 
					{
						
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
