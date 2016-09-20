package io.gupshup.bots.AuctionBot;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import io.gupshup.bots.db.AuctionDbConnection;
import io.gupshups.bots.util.BotMessage.MSG;

/**
 * Servlet implementation class AuctionBot
 */
@WebServlet("/AuctionBot")
public class AuctionBot extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		
		
		JobAssign jobassign = new JobAssign();
		
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			AuctionDbConnection Db = new AuctionDbConnection();
			
			JSONObject messageobj = null;
			JSONObject contextobj = null;
			try 
			{
				//------------creating json object to get the messageobj from json-------------//
				messageobj = new JSONObject(request.getParameter("messageobj"));
				//------------creating json object to get the contextobj from json-------------//
				contextobj = new JSONObject(request.getParameter("contextobj"));
			} catch (Exception e1)
			{
				
			}
	
			
			
			
				
			
		
			//-------------getting context_id from cintext_obj-----------------------------//
			String context_id= contextobj.getString("contextid");
			
			String text=messageobj.getString("text");//getting user text from json messageobj text
			
			
			 

			
			 
			String type=messageobj.getString("type");//getting the type if its msg or event
			
			//System.out.println(text);
			
			String state= Db.get_senderstate("sellerstate", context_id);//getting the state of seller entering data
			
			//System.out.println("hello"+state);
			
			PrintWriter out = response.getWriter();
			
			//--------executes when the bot is mapped first time it has type as event first message the user get when bot get mapped------------//
			if(type.equals("event"))
			{
				out.println(MSG.WELCOME);
				//System.out.println(MSG.WELCOME);
			}
			
			//-------------------executes when the user send any msg the type is msg--------------------------------------------------//
			else if(type.equals("msg")) {
				
				  //------executes when user send seller-------------//
			 if(text.toLowerCase().equals("seller"))
			{
				
				 ///System.out.println();
				
				 state = "getname";
				//the state row in database sellerstate get sets and return true if its unique intable
				Boolean value = Db.set_sellerstate("sellerstate", context_id, state);
				
				//--------------------if seller exist else get executes if not he is allowed to enter his product for bidder----------------//
				if(value==true)
				{ 
					 Db.set_sellerdetails("productdetails", context_id);//the seller context_id get  sets in productdetails table
						
					 	

					JSONObject seller_context =new JSONObject(request.getParameter("context"));
					String set_seller_context = seller_context.toString();
					Db.update_productdetails("productdetails","seller_context", "seller_context_id", context_id, set_seller_context);
					
					out.print(MSG.SELLER_NAME);
				}	
				else
				{
					out.print(MSG.ERROR);
				}	
				
					
				
			}
			 
			 /*//-------------------when user enter reset
			 else if(text.toLowerCase().equals("reset"))
				{
					
					state="";
					Db.update_sellerstate("sellerstate", context_id, state);
					out.print(MSG.WELCOME);
					
				}*/
			 
			 
			 //----------------when the state is getname that is after the user(seller) enter his/her name and it is stored in DB--------------------//
			else if(state.toLowerCase().equals("getname"))
			{
				Db.update_productdetails("productdetails", "sellername","seller_context_id", context_id,text);
				 state = "getdetails";
				 Db.update_sellerstate("sellerstate", context_id, state);
				out.print(MSG.PRODUCT_DETAILS);
				
			}
			 
			 
			 //-----------------when the state is getdetails user(seller) enter his/her product details is stored in the DB-------------------//
			else if(state.toLowerCase().equals("getdetails"))
			{
				
				Db.update_productdetails("productdetails", "description","seller_context_id", context_id,text);
				 state = "getprice";
				 Db.update_sellerstate("sellerstate", context_id, state);
				out.print(MSG.BASE_PRICE);
				//System.out.println("i am in discp");
			}
			 
			 //-----------------when the state is getprice user(seller) enter his/her product price is stored in the DB-------------------//
			else if(state.toLowerCase().equals("getprice"))
			{
				try
				{
					String get_price=text;
					Double price=Double.parseDouble(get_price);
					Db.update_productdetails("productdetails", "price","seller_context_id", context_id,text);
					Db.update_productdetails("productdetails", "latest_bid","seller_context_id", context_id, text);
					state="getacc";
					Db.update_sellerstate("sellerstate", context_id, state);
					out.print(MSG.ACCOUNT_NO);
				}
				catch(Exception ex)
				{
					state = "getprice";
					 Db.update_sellerstate("sellerstate", context_id, state);
					out.println("ENTER valid price");
				}
				
				
				
			}
			 
			 //-----------------when the state is getacc user(seller) enter his/her account details is stored in the DB for the transactional process-------------------//
			else if(state.toLowerCase().equals("getacc"))
			{
				Db.update_productdetails("productdetails", "account_no","seller_context_id", context_id,text);
				state="getifsc";
				Db.update_sellerstate("sellerstate", context_id, state);
				
				JSONObject bidder_context =new JSONObject(request.getParameter("context"));
				String set_bidder_context = bidder_context.toString();
				//put intial bidder context_id as seller context_id
				Db.update_productdetails("productdetails", "bidder_context_id", "seller_context_id",context_id,context_id );
				//put intial bidder context as seller context
				Db.update_productdetails("productdetails", "bidder_context", "seller_context_id", context_id, set_bidder_context);
				
				out.print(MSG.IFSC_CODE);
				
			}
			 
			/*-----------------when the state is getifsc user(seller) enter his/her account ifsc details is stored in the DB for the transactional process-------------------//
			  -----------------Starting and ending time(time after 24 hour) to store in DB-----------------------------------------------*/
			else if(state.toLowerCase().equals("getifsc"))
			{
				Db.update_productdetails("productdetails", "ifsc_code","seller_context_id", context_id,text);
				
				
				Long satime=System.currentTimeMillis();//current system time in milliseconds to store the starting bid time 
				
				String stime =String.valueOf(satime);//converts the Long starting time to string to store in DB
				
				//putting starting time in the product details table
				Db.update_productdetails("productdetails", "starttime","seller_context_id", context_id,stime);
				
				//calculating the end time for 24 hours in milliseconds
				 Long eatime=satime+(24*60*60*1000);
				 
				 String etime =String.valueOf(eatime);//converting the end time in string to store in the DB
				//String etime=Db.stopAfter24Hours(stime);
				 
				 //putting endtime in the product details table 
				Db.update_productdetails("productdetails", "endtime","seller_context_id", context_id,etime);
				
				
				//putting product status in DB
				Db.update_productdetails("productdetails", "product_status ","seller_context_id", context_id,"live");
				
				//System.out.println( Db.remaningTime("productdetails", context_id, "starttime", "endtime"));
				state="";//staten is reset for the seller.
				Db.update_sellerstate("sellerstate", context_id, state);// seller state is updated in the DB
				out.print(MSG.THANK_YOU+"\nIf you want to stop auction for your product TYPE 'Stop Product Id'."
						+ "\n Example 'stop 2'");
				
			}
			//-------------------Showing the live bid available for bidding for the bidders(users)------------------//
			else if (text.toLowerCase().equals("bid"))
			{
				
				List<String[]> value = Db.readData("select product_id,sellername,description,price,latest_bid from productdetails");
				 for(String[] str:value)
			     {
			   	 
			   	 String getString = Arrays.toString(str);
			   	 //System.out.println(getString);
			   	 String arr[] = getString.split(",");
			   	
			   	 String product_id = arr[0].replace("[","");
			   	
			   	 String	seller_name = arr[1];
			   	 
			   	 String	description = arr[2];
			   	 
			   	 String	base_price = arr[3];
			   	 
			   	String	latest_bid = arr[4].replace("]","");
			   	
			   	  String endtime = Db.get_endtime(product_id);
			   	  
			   	  // getting the current time in milliseconds to compare from the end time in table product details
			   	String c_time=String.valueOf(System.currentTimeMillis());
			   	
			   	//--getting the remaing time in the format of hh:mm:ss spliting and storing in the string hh mm and ss
			   	String test[]=Db.remaningTime("productdetails", context_id, c_time, endtime).split(":");
			   
			   	Long hour=Long.parseLong(test[0]);//getting the remaing hour in long 
			   	Long min=Long.parseLong(test[1]);// getting the remaning  minute in long 
			   	Long sec=Long.parseLong(test[2]);// gettting thee remaining seconds in long
			   	
			   	String exist=Db.get_productdetails("productdetails", product_id, "product_status", "product_id");
			   	///&& exist.toLowerCase().equals("live")
			   
			   	//------getting whether product is live by comparing the remaning time
			   	if(hour>=0 && min>=0 && sec>0 && exist.toLowerCase().equals("live")){
			   	 out.println("\nProduct Id : " + product_id +"\nSeller Name :" + seller_name + "\nDescription :" + description +"\nBase Price :" + base_price 
			   			 
			   			 
			   			 
			   			 +"\nLatest_bid="+latest_bid+"\ntime_remaining for bid to end="+Db.remaningTime("productdetails", context_id, c_time, endtime) );
			   	
			   	}
			   	
			   
			   
			   	
			     }
				 out.println(MSG.BID);
				 if(value.isEmpty())
				 {
					 out.println("Currently their is no bid available.");
					 
				 }
				
				
				 
			}
			 
			 
			 
		//-------------------------------------leaving bid -----------------------------//
	
			else if (text.toLowerCase().startsWith("leave "))
			{
				 String bid_test[]= text.split(" ");//splitting the text(<product_id price>) for bid entered by the 
				 String p_id=bid_test[0];//getting product_id from the bid  user entered(<product_id price>)
				
				String stop_test[]= text.split(" ");//splitting the text(<leave product_id>) to stop bid 
				 String b_p_id=bid_test[1];
				String exist_pd=Db.get_productdetails("bidder_details", context_id, "product_id", "bidder_context_id");
				//leave if product id exist 
				if(b_p_id.equals(exist_pd)){
				
				String allow= Db.get_productdetails("productdetails", b_p_id, "bidder_context_id", "product_id");
				 if(!context_id.equals(allow))
				 {
				Db.deletebidder("bidder_details", context_id,b_p_id);
				out.println("You will not receive further bid updates.");
				}
				 else
				 {
					 out.println("Sorryn you cant leave the bid you are highest bidder.");
				 }
				 }
				else
				{
					out.println("Product id : "+b_p_id+" does not exist");
				}
			}
			 
			 
			 
			/*else if(text.toLowerCase().startsWith("remove "))
			{
				 String remove_test[]= text.split(" "); 
				 String p_id=remove_test[1];
				 String validate =Db.get_productdetails("productdetails",p_id , "seller_context_id", "product_id ");
				 if(validate.toLowerCase().equals(context_id))
				 {
				 Db.deleterow("productdetails", context_id, "seller_context_id");
				Db.deleterow("sellerstate",context_id, "context_id");
				Db.deleterow("bidder_deatils", p_id, "product_id");
				 out.println(MSG.REMOVED);
				 
				 }
				 else
				 {
					 out.println(MSG.OWNER);
				 }
			}
			*/
			 
			 
			else if(text.toLowerCase().startsWith("stop "))
			{
				 
				 
				
				 String stop_test[]= text.split(" ");//splitting the text(<stop product_id>) to stop bid 
				 String s_p_id=stop_test[1];
				 String product_status="";
				 try{
					 product_status =Db.get_productdetails("productdetails", s_p_id, "product_status", "product_id");}
				 catch(Exception e){out.println("There is no such product.");}
		if(product_status.equals("live"))
		{
				 String validate =Db.get_productdetails("productdetails",s_p_id , "seller_context_id", "product_id ");
				if(validate.equals(context_id))
				{
					
					
					Db.update_productdetails("productdetails", "product_status", "product_id", s_p_id, "death");
				//get seller account number and ifsc to give the  highest bidder and get the description
					String account_nuumber=Db.get_productdetails("productdetails", s_p_id, "account_no","product_id");	
					String ifsc_code=Db.get_productdetails("productdetails", s_p_id, "ifsc_code","product_id");
					String description =Db.get_productdetails("productdetails", s_p_id, "description","product_id");
					
					//getting bidder context to send msg
					String bidder_context=Db.get_productdetails("productdetails", s_p_id, "bidder_context","product_id");
					System.out.println(bidder_context);
					//send msg(details of seller account number and ifsc code) to highest bidder 
					Db.sendMessage(new JSONObject(bidder_context),
							"Congratulation..!!!!\nYou own the product of id: "+s_p_id
							+"\nProduct description : "+description
							+"\n............Bank details of seller............"
							+"\nAccount number of seller is : "+account_nuumber
							+"\nIFSC_Code of seller is : "+ifsc_code+" .","intern7demoauction");
					
					out.println("product with product_id= '"+s_p_id+"' has been removed from auction");
					
					

					//delete seller state
					Db.deleterow("sellerstate", context_id, "context_id");
					Db.deleterow("productdetails",context_id, "seller_context_id" );
				}
				else
				{
					out.println("you are not authorized user");
				}
		}
		else
		{
			out.println("Your product has already been removed");
		}
				
			}
			 
			 
			 
			//--------------------updating highest 'latest_bid' in product details table-----------------------------//
			 
			else if(text.toLowerCase().startsWith("bid "))
			{
				 String bid_test[]= text.split(" ");//splitting the text(<product_id price>) for bid entered by the 
				 String p_id=bid_test[1];//getting product_id from the bid  user entered(<product_id price>)
				
				 
				 String product_status=Db.get_productdetails("productdetails", p_id, "product_status", "product_id");
					//leave if product id exist 
		if(product_status.equals("live"))
		{
				 
				 
				 Double bid_h=null,bid_u=null;
				String highbid= Db.get_product_id("productdetails", p_id, "latest_bid");
				
				//to  get if user enter valid amount or not
				try{
				bid_h=Double.parseDouble(highbid);
				String user_bid=bid_test[2];
				 bid_u=Double.parseDouble(user_bid);
				}
				catch(Exception ex){out.println(MSG.AMOUNT);}
				
				// stop the seller to bid
				String seller_contextid=Db.get_productdetails("productdetails", p_id, "seller_context_id", "product_id");
				if(!seller_contextid.equalsIgnoreCase(context_id))
				{

					if(bid_u>bid_h)
					{
					
					// get the bidder entered price and update to Db
					String latest_bid=bid_test[2];
					Db.update_productdetails("productdetails", "latest_bid","product_id", p_id, latest_bid);
					out.println(MSG.HIGHEST_BIDDER+latest_bid+"\nThank you for bidding.\nType 'help' for more info.");
					
					
					//send msg to the seller that bid has increased
					String seller_context=Db.get_productdetails("productdetails", p_id,"seller_context" , "product_id");
					Db.sendMessage(new JSONObject(seller_context), "There is bid of Rs:"+latest_bid+" for product_id : "+p_id+"\n"+MSG.STOP, "intern7demoauction");
					
					
					
					JSONObject bidder_context =new JSONObject(request.getParameter("context"));
					String set_bidder_context = bidder_context.toString();
					
					
					//update the highest bidder context_id 
					Db.update_productdetails("productdetails", "bidder_context_id", "product_id ", p_id, context_id);
					//update the highest bidder context
					Db.update_productdetails("productdetails", "bidder_context", "product_id ", p_id, set_bidder_context);
					
					
					
					
					//store bidder details in bidder_details table
					if(p_id.equals(Db.get_productdetails("bidder_details", context_id, "product_id", "bidder_context_id")))
					{
						Db.update_productdetails("bidder_details", "bid_price", "bidder_context_id", context_id,latest_bid );
					}
					else
					{
						Db.set_bidderdetails("bidder_details", context_id, set_bidder_context, p_id, latest_bid);
						
					}
					
					
					
					/*// send msg to last bidder that bid has been changed
					String last_bidder_context=	Db.get_productdetails("productdetails", p_id, "bidder_context","product_id");
					Db.sendMessage(new JSONObject(last_bidder_context), "Bid has changed to "+bid_u, "intern7demoauction");*/
				
					// send msg to bidder in the bidder details
					List<String[]> bidder_msg=Db.readData("select bidder_context from bidder_details where product_id="+p_id);
					for (String[] msg : bidder_msg) {
						for(String val :msg)
						{
							JSONObject getcontext = new JSONObject(val);
							String check =getcontext.getString("contextid") ;
							if(!context_id.toLowerCase().equals(check)){
							Db.sendMessage(new JSONObject(val), "Bid has increased to Rs: "+latest_bid+
									"\nFor product id "+p_id+
									" You can stop receving bid update if you type 'leave product_id'.\nExample 'leave 5'", "intern7demoauction");
						}}
						
						
						
					}
					
					
				
					}
					
					else
					{
						out.println(MSG.INCREASE_BID+highbid);	
					}
				}	
				
				else
				{
				out.println(MSG.RESTRICT);
				}
			}
				else
						{
					out.println(MSG.PRODUCT_NOT_AVAILABLE);
						}
			}
	
				
				
				
			
			else if(text.toLowerCase().equals("help"))
			{
				out.println(MSG.HELP);
			}
			 
			else
			{
				out.println("Sorry i didn't get you.\nYou ca type 'help' to know more.");
			}
			
		
			
			 
			 
			 
			/* if(userSay.toLowerCase().startsWith("stop "))
			 {
			 userSay = userSay.substring(5).trim();
			 pd.updateProductStatus(Constants.Table.PRODUCTDETAILS, Constants.Table.PRODUCT_STATUS, userSay, context_id, "closed");
			 if(context_id.equals(pd.getContextId(userSay))){
			 out.println("Your Product is Closed Succesfully.");
			 }
			 else{
			 out.println("You Are not an authorized Person.");
			 }

			 }*/
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			
			
			}
			
			//System.out.println(System.currentTimeMillis());
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();;
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	
	
	
}
