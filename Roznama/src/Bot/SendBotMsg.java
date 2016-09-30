package Bot;

import java.net.URLEncoder;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendBotMsg {

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
					.addHeader("apikey", OKHTTP.apikey).build();

			Response response = client.newCall(request).execute();
			response.body().close();

	} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
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
