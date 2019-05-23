package com.backend.configuration;

import javax.interceptor.Interceptors;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.backend.interceptor.LogInterceptor;

@Interceptors(LogInterceptor.class)
@ApplicationPath("api")
public class JAXRSConfiguration extends Application {
}