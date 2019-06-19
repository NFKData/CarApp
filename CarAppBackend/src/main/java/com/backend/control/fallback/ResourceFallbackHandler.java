package com.backend.control.fallback;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.naming.NamingException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.faulttolerance.ExecutionContext;
import org.eclipse.microprofile.faulttolerance.FallbackHandler;

import com.backend.entity.dto.ErrorDto;

public class ResourceFallbackHandler implements FallbackHandler<Response> {

	private static final String ERROR_DEF = "It seems there's some problems with our systems. Please retry again later";
	private static final String ERROR_EM = "It seems there's some problems with database system. Please retry again later";
	private static final String ERROR_JMS = "It seems there's some problems with queue system. Please retry again later";
	private static final String ERROR_NAMING = "It seems there's some problems with our environment. Please retry again later";

	private static Map<Class<?>, ErrorDto> errorMapping = null;

	@Override
	public Response handle(ExecutionContext context) {
		init();
		return Response.status(Status.INTERNAL_SERVER_ERROR)
				.entity(errorMapping.getOrDefault(context.getFailure(), errorMapping.get(Throwable.class))).build();
	}

	private void init() {
		if (errorMapping != null) {
			return;
		}
		errorMapping = new HashMap<>();
		errorMapping.put(IllegalStateException.class, new ErrorDto(0, ERROR_EM));
		errorMapping.put(JMSException.class, new ErrorDto(0, ERROR_JMS));
		errorMapping.put(NamingException.class, new ErrorDto(0, ERROR_NAMING));
		errorMapping.put(Throwable.class, new ErrorDto(0, ERROR_DEF));
	}

}
