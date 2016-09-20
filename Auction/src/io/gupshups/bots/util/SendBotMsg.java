package io.gupshups.bots.util;

import java.net.URLEncoder;

import org.json.JSONObject;

public class SendBotMsg {


	public static class DATABASE
	{
		public static final String REGISTER_DRIVER = "com.mysql.jdbc.Driver";
		public static final String URL = "jdbc:mysql://localhost/visitingbotmodule?autoReconnect=true&useSSL=false";
		public static final String USERNAME = "root";
		public static final String PASSWORD = "root";
	}
	
	public static class OKHTTP
	{
		public static String getURL(String botname)
		{
			String url = "http://api.gupshup.io/sm/api/bot/_botname/msg";
			url = url.replace("_botname", botname);
			return url;
		}
		public static String getRequestBody(JSONObject context,String message)
		{
			String requestBody="";
		try 
		{
			String sendcontext = URLEncoder.encode(context.toString(), "UTF-8");
			String sendMessage = URLEncoder.encode(message, "UTF-8");
			
			 requestBody = "context=_context&message=_message";
			 requestBody = requestBody.replace("_context", sendcontext);
			 requestBody = requestBody.replace("_message", sendMessage);
			 } 
			 catch (Exception e) 
			 {
			 e.getMessage();
			 }
			 return requestBody;
			 }

			 public static final String apikey = "9871fcf8273e4671c6467387dc4063a9";
			 }
	

}
