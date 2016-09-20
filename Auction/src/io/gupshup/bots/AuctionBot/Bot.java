package io.gupshup.bots.AuctionBot;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;


import io.gupshup.bots.db.AuctionDbConnection;

public class Bot implements Job{
	public static AuctionDbConnection Db;
	
	public Bot()
	{
		Db= new  AuctionDbConnection();
	}
	
	
	
	@Override
	public void execute(JobExecutionContext job) throws JobExecutionException 
	{
		System.out.println("in execute method");
		if (job.getJobDetail().getKey().getName().equalsIgnoreCase("auction")) 
		{
			System.out.println("close auction method called");
			closeAuction();
		}
	}
	
	public void closeAuction()
	{
		try {
			
			List<String[]> data =  Db.readData("select product_id, endtime from productdetails");
			for(String[] array : data)
			{
				String id = array[0].replace("[", "");
				String endtime  = array[1].replaceAll("]","");
				String starttime=String.valueOf(System.currentTimeMillis());
				String remaing_time[]=Db.remaningTime("productdetails", "", starttime, endtime).split(":");
				Long hour=Long.parseLong(remaing_time[0]);//getting the remaing hour in long 
			   	Long min=Long.parseLong(remaing_time[1]);// getting the remaning  minute in long 
			   	Long sec=Long.parseLong(remaing_time[2]);// gettting thee remaining seconds in long
				if(hour<=0 && min<=0 && sec<=0)
				{
					Db.update_productdetails("productdetails", "product_status", "product_id" , id, "death");
				}
			 
			   	
			   	
			}
			
		
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}	
}
	
	
