package com.backend.interceptor;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.lang.reflect.Method;

import javax.interceptor.InvocationContext;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class LogInterceptorTest {

	@Mock
	InvocationContext context;
	@InjectMocks
	private LogInterceptor log;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenLogging_shouldntThrowAnything() throws Exception {
		Method method = log.getClass().getMethod("log", InvocationContext.class);
		Object[] parameters = new Object[] {context};
		when(context.getMethod()).thenReturn(method);
		when(context.getParameters()).thenReturn(parameters);
		when(context.proceed()).thenReturn(null);
		log.log(context);
	}

}
