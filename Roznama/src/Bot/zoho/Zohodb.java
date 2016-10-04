package Bot.zoho;
import java.io.ByteArrayOutputStream;

import org.json.JSONArray;
import org.json.JSONObject;

import com.adventnet.zoho.client.report.ReportClient;

public class Zohodb {
	public static ReportClient rc;
	
	
	public boolean insert()
	{
		return true;
	}
	
	public Boolean readData(String mobile_no)throws Exception
	{
		rc=new ReportClient("f1fe3498aa49d5cd8b0c616c08a65316");
		JSONArray rows = null;
		String uri=rc.getURI("sgharat879@gmail.com", "Roznama", "users_mobile_no");
		ByteArrayOutputStream output= new ByteArrayOutputStream();
		rc.exportDataUsingSQL(uri, "JSON", output, "select * from users_mobile_no where mobile_no = "+mobile_no, null);
		JSONObject json=new JSONObject(output.toString());
		//System.out.println("string: "+output.toString());
		rows=json.getJSONObject("response").getJSONObject("result").getJSONArray("rows");
		System.out.println(rows);
		if(rows.length()>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/*public void getdetails()
	{
		try    
        {      
           String authtoken = "37f798749c49b7442d720eb7616dd2e2";
           String targetURL = "https://reports.zoho.com/ZDBDataSheetView.cc?DBID=1270774000000002172";
           PostMethod post = new PostMethod(targetURL);
           post.setParameter("authtoken",authtoken);
           post.setParameter("scope","crmapi");
           HttpClient httpclient = new HttpClient();
           httpclient.executeMethod(post);
           String postResp = post.getResponseBodyAsString();
           System.out.println("The Response from the server : "+postResp);
        } 
        catch(Exception e)
        {
            e.printStackTrace();
        }    
	}*/
	
}
