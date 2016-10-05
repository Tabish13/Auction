package Bot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import Bot.dbhandler.Dbhandler;
import Bot.zoho.Zohodb;
import soapcalls.GetLitigations;
import soapcalls.GetStages;
import soapcalls.UpdateLastStage;
import soapcalls.ValidateCaseNumber;

/**
 * Servlet implementation class RoznamaBot
 */
@WebServlet("/RoznamaBot")
public class RoznamaBot extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
	private final static String myclass = RoznamaBot.class.getName();
	MyLogger logger = MyLogger.getInstance();
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
		String botname = "droznama";
		
		if(check!=null)
		{
			try {
				
				PrintWriter out = response.getWriter();
				SendBotMsg sendmsg = new SendBotMsg();
				JSONObject contextobj = new JSONObject(request.getParameter("contextobj"));
				JSONObject messageobj = new JSONObject(request.getParameter("messageobj"));
				JSONObject senderobj = new JSONObject(request.getParameter("senderobj"));
				String type = messageobj.getString("type");
				String ogmsg = messageobj.getString("text");
				String msg = messageobj.getString("text").toLowerCase();
				String context_id = contextobj.getString("contextid");
				String state = db.getState(tablename, context_id, "state");
				//System.out.println("hey: \n\n"+contextobj+"\n\n"+messageobj+"\n\n"+senderobj);
				//System.out.println(ogmsg);
				if(type.equals("event"))
				{
					logger.info(myclass, "CONVERSATION STARTED");
					db.setCustomerDetails(tablename, context_id, "state", "getno");
					out.println("welcome to Roznama Bot.\nPlease  Enter Your Mobile Number.");
				}
				else if(type.equals("msg")||type.equals("image"))
				{
					if(msg.equals("hi")||msg.equals("hello")||msg.equals("hey"))
					{
						logger.info(myclass, "CONVERSATION STARTED");
						db.setCustomerDetails(tablename, context_id, "state", "getno");
						out.println("welcome to Roznama Bot.\nPlease  Enter Your Phone Number.");
					}
					else if(state.equals("getno"))
					{
						String mobile_regex = "^(?:(?:\\+|0{0,2})91(\\s*[\\-]\\s*)?|[0]?)?[789]\\d{9}$";
						if(msg.matches(mobile_regex)||msg.equals("9")||msg.equals("1234567890"))
						{
							logger.info(myclass, "Valid mobile syntax.");
							Zohodb mb = new Zohodb();
							Boolean mb_present = mb.readData(msg);
							if(mb_present)
							{
								logger.info(myclass, "Mobile number is registered");
							db.setCustomerDetails(tablename, context_id, "mobile", msg);
							db.setCustomerDetails(tablename, context_id, "state", "getotp");
							out.println("Please  Enter OTP.");
							}
							else
							{
								logger.info(myclass, "Mobile number is not registered.");
								out.println("Mobile number is not registered.");
							}
						}
						else
						{
							logger.info(myclass, "INvalid mobile syntax.");
							out.println("Invalid mobile number.\nEnter registered mobile number.");
						}
						
					}
					else if(state.equals("getotp"))
					{
						//get the otp from db and replace the '1234'
						logger.info(myclass, "Getting an OTP");
						if(msg.equals("1234"))
						{
							logger.info(myclass, "OTP is valid");
							
							db.setCustomerDetails(tablename, context_id, "state", "getcaseid");
							
							//GET ALL CASE NUMBER AND LET USER ENTER THE CASE NUMBER FROM ABOVE
							GetLitigations getlitigations = new GetLitigations();
							String mobile_no = db.getState(tablename, context_id, "mobile");
							ArrayList<String[]> data =  getlitigations.makeGetLitigations(mobile_no);
							String print = "";
							for (int i = 0; i < data.size(); i++) {
								//send the case number using send msg to the user and take the case id from
								String [] litigation = data.get(i);
								print +="Case Number: "+litigation[1]+"\n";
								//sendmsg.sendMessage(contextobj, "Case Number: "+litigation[1], botname);
							}
							sendmsg.sendMessage(contextobj, print, botname);
							sendmsg.sendMessage(contextobj, "Please Enter the Case ID.", botname);
						}else
						{
							logger.info(myclass, "Enter valid OTP");
							out.println("Invalid OTP.");
						}
					}
					else if(state.equals("getcaseid"))
					{
						ValidateCaseNumber validate = new ValidateCaseNumber();
						Boolean validation= validate.validateCaseNumber(ogmsg);
						if(validation)
						{
							logger.info(myclass, "valid case id");
						db.setCustomerDetails(tablename, context_id, "case_id", msg);
						db.setCustomerDetails(tablename, context_id, "state", "choosecase");
						//System.out.println("in get case id");
						logger.info(myclass, "Inside get case id");
						//out.println("What would you like to do?\n1) View Case Progress\n2) Upload Case Document\ne.g Type '1' for view case progress.");
						out.println("{\"type\":\"survey\",\"question\":\"What would you like to do?\",\"options\":[\"view case progress\",\"upload case document\"]}");
						}
						else
						{
							logger.info(myclass, "Invalid case id.");
							out.println("Case Id is not valid enter valid case ID.");
						}
					}
					else if(state.equals("choosecase"))
					{
						if(msg.contains("view")||msg.equals("1")||msg.equals("view case progress"))
						{
							logger.info(myclass, "View case progress selected");
							db.setCustomerDetails(tablename, context_id, "state", "getcaseid");
							String case_id = db.getState(tablename, context_id, "case_id");
							// make soap call with case_id give the results.
							GetStages getstage = new GetStages();
							ArrayList<String[]> data = getstage.makeGetStages(case_id);
							//System.out.println(data);
							if(data.size()>0)
							{
							String print = "";
							for (int i = 0; i < data.size(); i++) {
								String t[]=data.get(i);
								print += "Stage id: "+t[0]+"\nStage detail: "+t[1]+"\n\n";
								if(i%4==0&&i!=0)
								{
									sendmsg.sendMessage(contextobj, print, botname);
									//System.out.println(print);
									print="";
								}
								//System.out.println("STage id: "+t[0]+"\nStage detail: "+t[1]+"\n\n");
							}
							//System.out.println(print);
							sendmsg.sendMessage(contextobj, print, botname);
							sendmsg.sendMessage(contextobj, "Please Enter the Case ID.", botname);
							//sendmsg.sendMessage(contextobj, "What would you like to do?\n1) View Case Progress\n2) Upload Case Document\ne.g Type '1' for view case progress.", botname);
							
							//sendmsg.sendMessage(contextobj,"{\"type\":\"poll\",\"question\":\"Is there anything else i can help you with.\",\"msgid\":\"poll_212\"}" , botname);
							//sendmsg.sendMessage(contextobj,"{\"type\":\"survey\",\"question\":\"What would you like to do?\",\"options\":[\"view case progress\",\"upload case document\"]}", botname);
							}
							else
							{
								out.println("No such case id found");
							}
							}
						else if(msg.equals("2")||msg.contains("upload")||msg.equals("upload case document"))
						{
							logger.info(myclass, "Upload case document selected.");
							db.setCustomerDetails(tablename, context_id, "state", "uploadcase");
							out.println("Upload your case document.");
						}
						else
						{
							//out.println("Please provide valid input\nWhat would you like to do?\n1) View Case Progress\n2) Upload Case Document\ne.g Type '1' for view case progress.");
							out.println("{\"type\":\"survey\",\"question\":\"Please provide valid input\nWhat would you like to do?\",\"options\":[\"view case progress\",\"upload case document\"]}");
						}
					}
					else if(state.equals("uploadcase"))
					{
						UpdateLastStage ls = new UpdateLastStage();
						db.setCustomerDetails(tablename, context_id, "state", "uploadcasedone");
						String case_id = db.getState(tablename, context_id, "case_id");
						logger.info(myclass, "Uploading the document");
						//System.out.println("uploading your doc");
						String result = ls.getdetails(case_id, ogmsg);
						if(result!=null)
						{
							db.setCustomerDetails(tablename, context_id, "state", "getcaseid");
							sendmsg.sendMessage(contextobj, result, botname);
							sendmsg.sendMessage(contextobj, "Please Enter the Case ID.", botname);
						}
						else
						{
							
							logger.error(myclass, "DOcument not uploade succesfully", null);
							out.println("Something went wrong upload your document again.");
						}
					}
					/*else if(state.equals("anyhelp"))
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
					}*/
					
					else 
					{
						out.println("Sorry can't get you.");
					}
				}
				else
				{
					out.println("Not getting valid input.");
					logger.info(myclass, "not valid input(type ! image or msg)"+messageobj);
					//System.out.println("not valid unput"+messageobj);
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
