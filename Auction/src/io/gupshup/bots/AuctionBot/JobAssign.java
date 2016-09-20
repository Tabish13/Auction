package io.gupshup.bots.AuctionBot;

import java.util.TimeZone;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;

import static org.quartz.JobBuilder.*;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;

public class JobAssign {

	public JobAssign()
	{
		
		try {
			SchedulerFactory sf = new StdSchedulerFactory();
			Scheduler sched = sf.getScheduler();
			
						
			JobDetail job1 = newJob(Bot.class).withIdentity("auction", "group1").build();
			
			CronTrigger postDailyMail = newTrigger().withIdentity("cronTrigger", "group1").withSchedule(cronSchedule("0 0/1 * 1/1 * ? *").inTimeZone(TimeZone.getTimeZone("IST"))).build();


			sched.scheduleJob(job1, postDailyMail);

			sched.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
