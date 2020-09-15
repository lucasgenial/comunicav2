package br.com.cicom.comunicacicom;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.VersionResourceResolver;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       //registry.addResourceHandler("/*/**") 
               //.addResourceLocations("/assets/js/*") 
              // .addResourceLocations("/assets/css/*") 
            //    .addResourceLocations("/assets/imagens/*") 
              // .addResourceLocations("/css/") 
		 registry.addResourceHandler("*.css","*.json", "*/buscar/historico/").addResourceLocations("/resources/*")
		 
		 .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
                .resourceChain(false)
                .addResolver(new VersionResourceResolver().addContentVersionStrategy("/*/**"));;
    }
	
}
