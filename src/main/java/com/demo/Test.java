package com.demo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.scheduling.support.CronSequenceGenerator;

public class Test {
	public static void main(String[] args)
	 {
	  CronSequenceGenerator cron1 = new CronSequenceGenerator("0 0 */1 * * *");
	  Calendar cal = Calendar.getInstance();
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss"); 
	  
	  System.out.println("current date "+sdf.format(cal.getTime()));
	  Date d = cron1.next(cal.getTime());
	  int i=0;
	  while( i <10){
		  System.out.println("Next" + d);
		  d = cron1.next(d);
		  i++;
	  }
	  
	 }

}
