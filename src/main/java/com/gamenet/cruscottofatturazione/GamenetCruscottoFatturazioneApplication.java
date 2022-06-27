package com.gamenet.cruscottofatturazione;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "com.gamenet.cruscottofatturazione.*", "com.gamenet.cruscottofatturazione.controllers" })
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class GamenetCruscottoFatturazioneApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GamenetCruscottoFatturazioneApplication.class, args); //"--debug");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GamenetCruscottoFatturazioneApplication.class);
	}

}