package com.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.demo.service.SchedularService;
import com.demo.util.Util;

@Service
public class Schdule {

	private Logger log = LoggerFactory.getLogger(Schdule.class);

	@Autowired
	private SchedularService schedularService;
	
	@Scheduled(cron = "${cronjob.cancelappointment}")
	public void checkAppoinments() {
		try {
			log.debug("Scheduler started : " + System.currentTimeMillis());
			if (Util.isSchedularON) {
				this.schedularService.checkCancelAppoinments();
			}
			log.debug("Scheduler end : " + System.currentTimeMillis());
		} finally {
		}
	}

}
