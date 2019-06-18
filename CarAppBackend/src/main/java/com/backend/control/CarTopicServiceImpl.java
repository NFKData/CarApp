package com.backend.control;

import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.backend.entity.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
public class CarTopicServiceImpl implements CarTopicService {

	private static final String JMS_CAR = "JMS_CAR";
	private static final String JMS_PASSWORD = "JMS_PASSWORD";
	private static final String JMS_USER = "JMS_USER";
	private static final String JMS_CONFACTORY = "JMS_CONFACTORY";
	private static final String POST = "POST";
	private static final String METHOD = "method";

	@Override
	public void create(Car car) throws NamingException, JMSException, JsonProcessingException {
		InitialContext ctx = new InitialContext();
		Connection conn = ((ConnectionFactory) ctx.lookup(System.getenv(JMS_CONFACTORY)))
				.createConnection(System.getenv(JMS_USER), System.getenv(JMS_PASSWORD));
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer producer = session.createProducer((Destination) ctx.lookup(System.getenv(JMS_CAR)));
		TextMessage msg = session
				.createTextMessage(new ObjectMapper().findAndRegisterModules().writeValueAsString(car));
		msg.setStringProperty(METHOD, POST);
		producer.send(msg);
		session.close();
	}

}
