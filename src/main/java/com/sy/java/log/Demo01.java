package com.sy.java.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Demo01 {
	private static final Logger log = LogManager.getLogger(Demo01.class.getName());
	
	public static void main(String[] args) {
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
	}
}
