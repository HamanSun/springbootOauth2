package com.iss.base.mq.receiver;

import java.util.concurrent.CountDownLatch;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.iss.base.mapper.SysLogMapper;
import com.iss.base.model.SysLog;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RabbitListener(queues = "vv-log")
public class LogConsumer {

	private CountDownLatch latch = new CountDownLatch(1);
	@Autowired
	SysLogMapper sysLogMapper;
	
	@RabbitHandler
	public void receiveMessage(String message) {
		log.info("--------received --------: " + message);
		SysLog sysLog = JSON.parseObject(message, SysLog.class);
		sysLogMapper.insertSelective(sysLog);
		log.info("--------saved to db --------");
		latch.countDown();
		
	}
}
