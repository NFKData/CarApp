package com.backend.control;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.health.Health;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.HealthCheckResponseBuilder;
import org.slf4j.LoggerFactory;

import com.backend.boundary.BrandResource;
import com.backend.boundary.CarResource;
import com.backend.boundary.CountryResource;
import com.backend.entity.Brand;
import com.backend.entity.Car;
import com.backend.entity.Country;

@Health
@ApplicationScoped
public class HealthCheckImpl implements HealthCheck {

	private static final String BASE_URL = "http://localhost:8080/car-api/api/";

	private enum ResourceMethods {
		GET_ALL, GET_ONE, CREATE, UPDATE, DELETE, SUBRESOURCE
	}

	// 18 checks
	private static final Boolean[] DEFAULT_RESULT = { false, false, false, false, false, false, false, false, false,
			false, false, false, false, false, false, false, false, false };

	@Override
	public HealthCheckResponse call() {
		Boolean[] checks = DEFAULT_RESULT;
		try {
			checks = checkSystems();
		} catch (Exception e) {
			LoggerFactory.getLogger("com.backend").error("Exception checking systems", e);
		}
		if (isAnyDown(checks)) {
			return setUpResponseBuilder(checks).down().build();
		}
		return setUpResponseBuilder(checks).up().build();
	}

	private HealthCheckResponseBuilder setUpResponseBuilder(Boolean[] checks) {
		return HealthCheckResponse.named("Server").withData("getAllCars", checks[0]).withData("getOneCar", checks[1])
				.withData("createCar", checks[2]).withData("updateCar", checks[3]).withData("deleteCar", checks[4])
				.withData("carSubresource", checks[5]).withData("getAllBrands", checks[6])
				.withData("getOneBrand", checks[7]).withData("createBrand", checks[8])
				.withData("updateBrand", checks[9]).withData("deleteBrand", checks[10])
				.withData("brandSubresource", checks[11]).withData("getAllCountries", checks[12])
				.withData("getOneCountry", checks[13]).withData("createCountry", checks[14])
				.withData("updateCountry", checks[15]).withData("deleteCountry", checks[16])
				.withData("countrySubresource", checks[17]);
	}

	private Boolean[] checkSystems() throws Exception {
		List<Boolean> checks = new ArrayList<>();
		checks.add(checkSystem(CarResource.class, ResourceMethods.GET_ALL));
		checks.add(checkSystem(CarResource.class, ResourceMethods.GET_ONE));
		checks.add(checkSystem(CarResource.class, ResourceMethods.CREATE));
		checks.add(checkSystem(CarResource.class, ResourceMethods.UPDATE));
		checks.add(checkSystem(CarResource.class, ResourceMethods.DELETE));
		checks.add(checkSystem(CarResource.class, ResourceMethods.SUBRESOURCE));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.GET_ALL));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.GET_ONE));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.CREATE));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.UPDATE));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.DELETE));
		checks.add(checkSystem(BrandResource.class, ResourceMethods.SUBRESOURCE));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.GET_ALL));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.GET_ONE));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.CREATE));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.UPDATE));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.DELETE));
		checks.add(checkSystem(CountryResource.class, ResourceMethods.SUBRESOURCE));
		return checks.toArray(new Boolean[checks.size()]);
	}

	private Boolean checkSystem(Class<?> systemClass, ResourceMethods method) throws Exception {
		Client client = ClientBuilder.newClient();
		WebTarget request = null;
		switch (systemClass.toString().substring(systemClass.toString().lastIndexOf('.') + 1)) {
		case "CarResource":
			request = client.target(BASE_URL + "cars");
			return checkSubSystem(method, request);
		case "BrandResource":
			request = client.target(BASE_URL + "brands");
			return checkSubSystem(method, request);
		case "CountryResource":
			request = client.target(BASE_URL + "cars");
			return checkSubSystem(method, request);
		}
		return false;
	}

	private Boolean isAnyDown(Boolean[] checks) {
		for (Boolean check : checks) {
			if (!check) {
				return true;
			}
		}
		return false;
	}

	private Boolean checkSubSystem(ResourceMethods method, WebTarget target) throws Exception {
		Response response = null;
		switch (method) {
		case GET_ONE:
			response = target.path("123").request(MediaType.APPLICATION_JSON).get();
			break;
		case CREATE:
			try {
				response = target.request().post(Entity.entity(getEntityFromUri(target.getUri()), MediaType.APPLICATION_JSON_TYPE));
			} catch (ProcessingException ex) {
				return true;
			}
			break;
		case DELETE:
			response = target.path("123").request().delete();
			break;
		case GET_ALL:
			response = target.request(MediaType.APPLICATION_JSON).get();
			break;
		case SUBRESOURCE:
			if(target.getUri().toString().contains("/api/cars")) {
				response = target.path("123/brand").request(MediaType.APPLICATION_JSON).get();
				if (response != null && response.getStatus() != 500) {
					response = target.path("123/country").request(MediaType.APPLICATION_JSON).get();
				}
			} else {
				response = target.path("123/cars").request(MediaType.APPLICATION_JSON).get();
			}
			break;
		case UPDATE:
			try {
				response = target.path("123").request(MediaType.APPLICATION_JSON)
						.put(Entity.entity(getEntityFromUri(target.getUri()), MediaType.APPLICATION_JSON));
			} catch (ProcessingException ex) {
				return true;
			}
			break;
		}
		return (response != null && response.getStatus() != 500);
	}

	private Object getEntityFromUri(URI uri) {
		if(uri.toString().contains("countries")) {
			return new Country();
		}
		if(uri.toString().contains("brands")) {
			return new Brand();
		}
		return new Car();
	}

}