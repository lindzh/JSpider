package net.javacoding.jspider.core.logging.impl;

import net.javacoding.jspider.core.logging.Log;
import net.javacoding.jspider.core.logging.LogProvider;

public class Log4jLogProvider implements LogProvider{

	@Override
	public Log createLog(String category) {
		return new Log4jLogImpl(category);
	}

}
