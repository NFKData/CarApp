package com.backend.jms;

import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.entity.Country;
import com.backend.jms.executor.JMSExecutor;
import com.backend.jms.executor.JMSMappedActions;
import com.fasterxml.jackson.databind.ObjectMapper;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/CountryAppCountry"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class CountryMessageConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Logger log = LogManager.getLogger("com.backend");
		try {
			ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
			String method = message.getStringProperty("Method");
			Country param = mapper.readValue(message.getBody(String.class), Country.class);
			createActions(param);
			JMSExecutor.execute(JMSMappedActions.getInstance().getActions(Country.class, method));
			JMSMappedActions.getInstance().clearActions(Country.class);
		} catch (JMSException | IOException e) {
			log.error("Unexpected error occurred. ", e);
		}
	}
	
	private void createActions(Country country) {
	}

}
