package com.iss.base.mq.sender;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.iss.base.model.SysLog;

@Service
public class LogProducer {
	@Autowired
	private AmqpTemplate rabbitTemplate;
	
	public void log(SysLog sysLog) {
		rabbitTemplate.convertAndSend("vv-log", JSON.toJSONString(sysLog));
	}
}
