package com.backend.exception.handler;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;

public class CarApiBaseExceptionHandlerTest {

	private static final String DEFAULT_ID = "123";
	@InjectMocks
	private CarApiBaseExceptionHandler handler;
	
	@Before
	public void setUp() throws Exception {
		initMocks(this);
	}

	@Test
	public void whenCarNotFoundException_shouldReturnNotFound() {
		CarNotFoundException ex = new CarNotFoundException(DEFAULT_ID);
		Response response = handler.toResponse(ex);
		assertEquals(Status.NOT_FOUND, Status.fromStatusCode(response.getStatus()));
	}
	
	@Test
	public void whenInvalidEntityException_shouldReturnBadRequest() {
		InvalidEntityException ex = new InvalidEntityException(new ArrayList<>());
		Response response = handler.toResponse(ex);
		assertEquals(Status.BAD_REQUEST, Status.fromStatusCode(response.getStatus()));
	}

}
