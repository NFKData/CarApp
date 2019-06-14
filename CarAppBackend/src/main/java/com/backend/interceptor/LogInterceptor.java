package com.backend.interceptor;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interceptor that log every call to methods on the api
 * 
 * @author gmiralle
 *
 */
@Interceptor
public class LogInterceptor {

	/**
	 * Log to /var/log/CarApp.log with log4j
	 * 
	 * @param context InvocationContext of the method call
	 * @return Object the return value of the next method in the chain
	 * @throws Exception When any of the next methods in the chain throws an
	 *                   Exception
	 */
	@AroundInvoke
	public Object log(InvocationContext context) throws Exception {
		Logger log = LoggerFactory.getLogger("com.backend");
		log.info("Method Called: " + context.getMethod().getName());
		if (context.getParameters() != null) {
			for (int i = 0; i < context.getParameters().length; i++) {
				log.info("Parameter[" + i + "]: " + context.getParameters()[i].toString());
			}
		}
		return context.proceed();
	}
}
