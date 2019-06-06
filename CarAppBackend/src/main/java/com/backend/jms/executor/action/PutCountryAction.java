package com.backend.jms.executor.action;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.HttpMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.control.CountryService;
import com.backend.entity.Country;
import com.backend.exception.CountryNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;

@Interceptors(LogInterceptor.class)
public class PutCountryAction implements JMSAction {

	Logger log = LogManager.getLogger("com.backend");
	
	private CountryService countryService;
	
	private Country country;
	
	public PutCountryAction(Country country) {
		this.country = country;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			this.countryService = (CountryService) ctx.lookup("java:comp/env/countryService");
		} catch (NamingException e) {
			log.error("Unexpected error occurred.", e);
		}
		JMSMappedActions.getInstance().registerAction(Country.class, HttpMethod.PUT, this);
	}

	@Override
	public void execute() {
		try {
			if(country != null) {
				countryService.updateCountry(country);
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
		PutCountryAction other = (PutCountryAction) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		return true;
	}

}
