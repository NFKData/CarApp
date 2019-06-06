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
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;
import com.backend.jms.executor.action.DeleteCountryAction;
import com.backend.jms.executor.action.PostCountryAction;
import com.backend.jms.executor.action.PutCountryAction;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Topic subscriber for CarAppCountry Topic
 * @author gmiralle
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/CarAppCountry"),
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
	
	/**
	 * Create {@link JMSAction} for {@link Country}
	 * @param country Country received on JMS
	 */
	private void createActions(Country country) {
		new PostCountryAction(country);
		new PutCountryAction(country);
		new DeleteCountryAction(country);
	}

}
