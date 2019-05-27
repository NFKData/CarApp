package com.backend.exception.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class UnexpectedExceptionHandlerTest {

	@InjectMocks
	private UnexpectedExceptionHandler handler;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenException_shouldReturnInternalServerError() {
		Exception ex = new Exception("Message of the exception");
		Response response = handler.toResponse(ex);
		assertEquals(Status.INTERNAL_SERVER_ERROR, Status.fromStatusCode(response.getStatus()));
	}

}
