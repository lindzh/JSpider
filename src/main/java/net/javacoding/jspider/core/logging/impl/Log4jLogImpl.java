package net.javacoding.jspider.core.logging.impl;

import net.javacoding.jspider.core.logging.Log;

import org.apache.log4j.Logger;

public class Log4jLogImpl implements Log{

	private Logger logger;
	
	public Log4jLogImpl(String category){
		logger = Logger.getLogger(category);
	}

	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public boolean isErrorEnabled() {
		return true;
	}

	@Override
	public boolean isFatalEnabled() {
		return true;
	}

	@Override
	public boolean isInfoEnabled() {
		return logger.isInfoEnabled();
	}

	@Override
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}

	@Override
	public boolean isWarnEnabled() {
		return true;
	}

	@Override
	public void trace(Object o) {
		logger.trace(o);		
	}

	@Override
	public void trace(Object o, Throwable throwable) {
		logger.trace(o, throwable);
	}

	@Override
	public void debug(Object o) {
		logger.debug(o);
	}

	@Override
	public void debug(Object o, Throwable throwable) {
		logger.debug(o, throwable);
	}

	@Override
	public void info(Object o) {
		logger.info(o);
	}

	@Override
	public void info(Object o, Throwable throwable) {
		logger.info(o, throwable);		
	}

	@Override
	public void warn(Object o) {
		logger.warn(o);
	}

	@Override
	public void warn(Object o, Throwable throwable) {
		logger.warn(o, throwable);
	}

	@Override
	public void error(Object o) {
		logger.error(o);
	}

	@Override
	public void error(Object o, Throwable throwable) {
		logger.error(o, throwable);
	}

	@Override
	public void fatal(Object o) {
		logger.fatal(o);
	}

	@Override
	public void fatal(Object o, Throwable throwable) {
		logger.fatal(o, throwable);
	}
	
	
}
