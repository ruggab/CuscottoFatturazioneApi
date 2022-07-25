package com.gamenet.cruscottofatturazione;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan(basePackages = { "com.gamenet.cruscottofatturazione.*", "com.gamenet.cruscottofatturazione.controllers" })
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class GamenetCruscottoFatturazioneApplication extends SpringBootServletInitializer {

	private Logger log = LoggerFactory.getLogger(getClass());
	public static void main(String[] args) {
		SpringApplication.run(GamenetCruscottoFatturazioneApplication.class, args); //"--debug");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GamenetCruscottoFatturazioneApplication.class);
	}
	
	
	 @Bean(name = "restTemplate")
	    public RestTemplate getRestClient() {
	        RestTemplate restTemplate = new RestTemplate(
	                new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()));

	        // Add one interceptor like in your example, except using anonymous class.
	        restTemplate.setInterceptors(Collections.singletonList((request, body, execution) -> {

	            log.debug("Intercepting...");
	            return execution.execute(request, body);
	        }));

	        return restTemplate;
	    }

}