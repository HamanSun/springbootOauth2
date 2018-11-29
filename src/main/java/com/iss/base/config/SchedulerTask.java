package com.iss.base.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class SchedulerTask {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Scheduled(cron = "${cron.job.task1}")
	public void printTimePer5s() {
		System.out.println("Beijing datetime is : " + sdf.format(new Date()));
	}
}
