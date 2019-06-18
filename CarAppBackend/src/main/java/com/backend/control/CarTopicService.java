package com.backend.control;

import javax.jms.JMSException;
import javax.naming.NamingException;

import com.backend.entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface CarTopicService {

	public void create(Car car) throws NamingException, JMSException, JsonProcessingException;

}
