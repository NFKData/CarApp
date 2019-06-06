package com.backend.jms;

import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.entity.Brand;
import com.backend.jms.executor.JMSExecutor;
import com.backend.jms.executor.JMSMappedActions;
import com.backend.jms.executor.action.DeleteBrandAction;
import com.backend.jms.executor.action.PostBrandAction;
import com.backend.jms.executor.action.PutBrandAction;
import com.fasterxml.jackson.databind.ObjectMapper;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/CarAppBrand"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class BrandMessageConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Logger log = LogManager.getLogger("com.backend");
		try {
			ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
			String method = message.getStringProperty("Method");
			Brand param = mapper.readValue(message.getBody(String.class), Brand.class);
			createActions(param);
			JMSExecutor.execute(JMSMappedActions.getInstance().getActions(Brand.class, method));
			JMSMappedActions.getInstance().clearActions(Brand.class);
		} catch (JMSException | IOException e) {
			log.error("Unexpected error occurred. ", e);
		}
	}
	
	private void createActions(Brand brand) {
		new PostBrandAction(brand);
		new PutBrandAction(brand);
		new DeleteBrandAction(brand);
	}

}
