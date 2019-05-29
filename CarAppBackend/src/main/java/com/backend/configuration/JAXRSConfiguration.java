package com.backend.configuration;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (info = 
@Info(
          title = "Car Api",
          version = "0.0.1-SNAPSHOT",
          description = "API for Car APP",
          contact = @Contact(name = "Guillermo Miralles Campillo", email = "guillermo.miralles.campillo@everis.com")
  )
)
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
}