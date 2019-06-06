package com.backend.jms.executor.action;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.HttpMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.control.CarService;
import com.backend.entity.Car;
import com.backend.exception.CarNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;

@Interceptors(LogInterceptor.class)
public class PostCarAction implements JMSAction {

	Logger log = LogManager.getLogger("com.backend");
	
	private CarService carService;
	
	private Car car;
	
	public PostCarAction(Car car) {
		this.car = car;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			this.carService = (CarService) ctx.lookup("java:comp/env/carService");
		} catch (NamingException e) {
			log.error("Unexpected error occurred.", e);
		}
		JMSMappedActions.getInstance().registerAction(Car.class, HttpMethod.POST, this);
	}

	@Override
	public void execute() {
		try {
			if(car != null) {
				carService.createCar(car);
			} else throw new CarNotFoundException(null);
		} catch (CarNotFoundException | InvalidEntityException e) {
			log.error("Unexpected error occurred.", e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PostCarAction other = (PostCarAction) obj;
		if (car == null) {
			if (other.car != null)
				return false;
		} else if (!car.equals(other.car))
			return false;
		return true;
	}

}
