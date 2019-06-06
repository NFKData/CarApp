package com.backend.jms.executor;

import java.util.List;

/**
 * It execute ordered JMSActions
 * @author gmiralle
 *
 */
public class JMSExecutor {
	
	/**
	 * Method called when the Subscriber finish processing JMS
	 * @param actions List of {@link JMSAction} to be executed
	 */
	public static void execute(List<JMSAction> actions) {
		for(JMSAction action : actions) {
			action.execute();
		}
	}
	
	/**
	 * Actions executed by {@link JMSExecutor}
	 * @author gmiralle
	 *
	 */
	public interface JMSAction {
		/**
		 * Method called when {@link JMSExecutor#execute(List)} is called 
		 */
		public void execute();
	}
	
}
