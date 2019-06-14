package com.backend.jms;

import java.io.IOException;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.backend.entity.Car;
import com.backend.jms.executor.JMSExecutor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;
import com.backend.jms.executor.action.DeleteCarAction;
import com.backend.jms.executor.action.PostCarAction;
import com.backend.jms.executor.action.PutCarAction;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Topic subscriber for CarAppCar Topic
 * @author gmiralle
 *
 */
@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/CarAppCar"),
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic") })
public class CarMessageConsumer implements MessageListener {

	@Override
	public void onMessage(Message message) {
		Logger log = LoggerFactory.getLogger("com.backend");
		try {
			ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
			String method = message.getStringProperty("Method");
			Car param = mapper.readValue(message.getBody(String.class), Car.class);
			createActions(param);
			JMSExecutor.execute(JMSMappedActions.getInstance().getActions(Car.class, method));
			JMSMappedActions.getInstance().clearActions(Car.class);
		} catch (JMSException | IOException e) {
			log.error("Unexpected error occurred. ", e);
		}
	}
	
	/**
	 * Create {@link JMSAction} for {@link Car}
	 * @param car Car received on JMS
	 */
	private void createActions(Car car) {
		new PostCarAction(car);
		new PutCarAction(car);
		new DeleteCarAction(car);
	}

}
