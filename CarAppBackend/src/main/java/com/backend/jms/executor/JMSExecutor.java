package com.backend.jms.executor;

import java.util.List;

public class JMSExecutor {
	
	public static void execute(List<JMSAction> actions) {
		for(JMSAction action : actions) {
			action.execute();
		}
	}
	
	public interface JMSAction {
		public void execute();
	}
	
}
