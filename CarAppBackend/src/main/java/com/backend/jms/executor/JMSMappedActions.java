package com.backend.jms.executor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.backend.jms.executor.JMSExecutor.JMSAction;

public class JMSMappedActions {
	
	private Map<Class<?>, Map<String, List<JMSAction>>> mappedActions;
	private static JMSMappedActions instance;
	
	private JMSMappedActions() {
		this.mappedActions = new HashMap<>();
	}
	
	public void registerAction(Class<?> type, String method, JMSAction action) {
		if(this.mappedActions.get(type) == null) {
			this.mappedActions.put(type, new HashMap<>());
		}
		if(this.mappedActions.get(type).get(method) == null) {
			this.mappedActions.get(type).put(method, new ArrayList<>());
		}
		if(!this.mappedActions.get(type).get(method).contains(action)) {
			this.mappedActions.get(type).get(method).add(action);
		}
	}
	
	public List<JMSAction> getActions(Class<?> type, String method) {
		Map<String, List<JMSAction>> mappedMethods = mappedActions.get(type);
		if(mappedMethods == null) {
			return null;
		}
		return mappedMethods.get(method);
	}
	
	public static JMSMappedActions getInstance() {
		if(instance == null) {
			instance = new JMSMappedActions();
		}
		return instance;
	}

}
