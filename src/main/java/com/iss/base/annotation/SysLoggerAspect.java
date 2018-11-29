package com.iss.base.annotation;

import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.iss.base.model.SysLog;
import com.iss.base.mq.sender.LogProducer;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class SysLoggerAspect {
	@Autowired
	private LogProducer logProducer;

	@Pointcut("@annotation(com.iss.base.annotation.SysLogger)")
	public void loggerPointCut() {
	}

	@Before("loggerPointCut()")
	public void saveSysLog(JoinPoint joinPoint) {
		log.info("===========log prepare start=========");
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		SysLog sysLog = new SysLog();
		SysLogger sysLogger = method.getAnnotation(SysLogger.class);
		if (sysLogger != null) {
			sysLog.setOperation(sysLogger.value());
		}
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();
		sysLog.setMethodName(className + "." + methodName + "()");
		Object[] args = joinPoint.getArgs();
		String params = "";
		for (Object o : args) {
			params += JSON.toJSONString(o);
		}
		if (!StringUtils.isEmpty(params)) {
			sysLog.setParams(params);
		}
		sysLog.setIp("127.0.0.1");
		sysLog.setUsername("test user");
		sysLog.setCreatedDate(new Date());
		logProducer.log(sysLog);
		log.info("===========log send to rabbitmq =========");
	}
}
