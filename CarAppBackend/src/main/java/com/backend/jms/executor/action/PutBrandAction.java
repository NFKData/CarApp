package com.backend.jms.executor.action;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.HttpMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.backend.control.BrandService;
import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;

@Interceptors(LogInterceptor.class)
public class PutBrandAction implements JMSAction {

	Logger log = LoggerFactory.getLogger("com.backend");
	
	private BrandService brandService;
	
	private Brand brand;
	
	public PutBrandAction(Brand brand) {
		this.brand = brand;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			this.brandService = (BrandService) ctx.lookup("java:comp/env/brandService");
		} catch (NamingException e) {
			log.error("Unexpected error occurred.", e);
		}
		JMSMappedActions.getInstance().registerAction(Brand.class, HttpMethod.PUT, this);
	}

	@Override
	public void execute() {
		try {
			if(brand != null) {
				brandService.updateBrand(brand);
			} else throw new BrandNotFoundException(null);
		} catch (BrandNotFoundException | InvalidEntityException e) {
			log.error("Unexpected error occurred.", e);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		PutBrandAction other = (PutBrandAction) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		return true;
	}

}
