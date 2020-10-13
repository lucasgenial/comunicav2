package br.com.cicom.comunicacicom;

import java.util.Locale;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.FixedLocaleResolver;

import static org.modelmapper.Conditions.isNotNull;
import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;
import static org.modelmapper.convention.MatchingStrategies.STRICT;
import static org.modelmapper.convention.NamingConventions.JAVABEANS_MUTATOR;

@SpringBootApplication
public class ComunicaCicomApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ComunicaCicomApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(ComunicaCicomApplication.class, args);
	}

	@Bean
	public LocaleResolver localeResolver() {
		return new FixedLocaleResolver(new Locale("pt", "BR"));
	}
	
	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
	    modelMapper
	        .getConfiguration()
	        .setPropertyCondition(isNotNull())
	        .setFieldMatchingEnabled(true)
	        .setFieldAccessLevel(PRIVATE)
	        .setMatchingStrategy(STRICT)
	        .setSourceNamingConvention(JAVABEANS_MUTATOR);
	    return modelMapper;
	}
}
