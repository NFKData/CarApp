package com.backend.jms.executor.action;

import javax.interceptor.Interceptors;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.HttpMethod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.backend.control.BrandService;
import com.backend.entity.Brand;
import com.backend.exception.BrandNotFoundException;
import com.backend.exception.InvalidEntityException;
import com.backend.interceptor.LogInterceptor;
import com.backend.jms.executor.JMSExecutor.JMSAction;
import com.backend.jms.executor.JMSMappedActions;

@Interceptors(LogInterceptor.class)
public class PostBrandAction implements JMSAction {

	Logger log = LogManager.getLogger("com.backend");
	
	private BrandService brandService;
	
	private Brand brand;
	
	public PostBrandAction(Brand brand) {
		this.brand = brand;
		InitialContext ctx;
		try {
			ctx = new InitialContext();
			this.brandService = (BrandService) ctx.lookup("java:comp/env/brandService");
		} catch (NamingException e) {
			log.error("Unexpected error occurred.", e);
		}
		JMSMappedActions.getInstance().registerAction(Brand.class, HttpMethod.POST, this);
	}

	@Override
	public void execute() {
		try {
			if(brand != null) {
				brandService.createBrand(brand);
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
		PostBrandAction other = (PostBrandAction) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		return true;
	}

}
