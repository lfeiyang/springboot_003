package com.sy.java.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/log4j2")
public class Demo02 {
	private final Logger log = LogManager.getLogger(Demo02.class);

	@RequestMapping(value = "/log")
	public String log() {
		for (int i = 1; i < 10000; i++) {
			// 记录trace级别的信息
			log.trace("This is trace message.");

			// 记录debug级别的信息
			log.debug("This is debug message.");

			// 记录info级别的信息
			log.info("This is info message.");

			// 记录error级别的信息
			log.error("This is error message.");

			// 记录warn级别的信息
			log.warn("This is warn message.");

			// 记录fatal级别的信息
			log.fatal("this is fatal message.");
		}

		return "打印日志级别";
	}
}
