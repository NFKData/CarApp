package com.backend.jms.executor.action;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.HttpMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.backend.control.CountryService;
import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;

@Interceptors(LogInterceptor.class)
public class PostCountryAction implements JMSAction {

	Logger log = LoggerFactory.getLogger("com.backend");
	
	private CountryService countryService;
	
	private Country country;
	
	public PostCountryAction(Country country) {
		this.country = country;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			this.countryService = (CountryService) ctx.lookup("java:comp/env/countryService");
		} catch (NamingException e) {
			log.error("Unexpected error occurred.", e);
		}
		JMSMappedActions.getInstance().registerAction(Country.class, HttpMethod.POST, this);
	}

	@Override
	public void execute() {
		try {
			if(country != null) {
				countryService.createCountry(country);
			} else throw new CountryNotFoundException(null);
		} catch (CountryNotFoundException | InvalidEntityException e) {
			log.error("Unexpected error occurred.", e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PostCountryAction other = (PostCountryAction) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}

}
