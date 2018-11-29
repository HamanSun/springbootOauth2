package com.iss.base.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * 在项目启动的时候需要做一些初始化的操作，比如初始化线程池，提前加载好加密证书等
 * @author Johnj
 *
 */
@Component
@Order(1)
public class Runner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		System.out.println("The runner (1) start to initialize ... ");

	}

}
