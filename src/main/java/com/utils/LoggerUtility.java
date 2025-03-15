package com.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtility {
	
	public LoggerUtility() {
		// TODO Auto-generated constructor stub
	}
	
	public static Logger getLogger(Class<?> clazz)
	{
		Logger logger = null;
		
		if(logger == null)
		{
			logger = LogManager.getLogger(clazz);
		}
		return logger;
	}
	
}
