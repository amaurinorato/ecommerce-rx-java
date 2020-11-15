package com.github.amaurinorato.products;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import com.github.amaurinorato.products.rx.ObservableReturnValueHandler;

@Configuration
public class WebConfig {

	@Autowired
	RequestMappingHandlerAdapter req;
	
	@PostConstruct
	public void init() {
		List<HandlerMethodReturnValueHandler> springHandlers = req.getReturnValueHandlers();
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<>(springHandlers);
		handlers.add(0, observableReturnValueHandler());
		req.setReturnValueHandlers(handlers);
	}
	
	@Bean
	public HandlerMethodReturnValueHandler observableReturnValueHandler() {
		return new ObservableReturnValueHandler();
	}
	
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
