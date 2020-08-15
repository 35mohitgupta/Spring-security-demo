package com.mht.Demoproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger-ui -> http://localhost:8600/swagger-ui.html
 * swagger-docs -> http://localhost:8600/v2/api-docs
 * @author mohit
 *
 */


@SpringBootApplication
@EnableSwagger2
public class DemoProjectApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}
	/**
	 * Configure username and password and authorities
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()
			.withUser("mohit").password(passwordEncoder().encode("mht"))
			.authorities("ROLE_USER");
	}
	
	/**
	 * Configuring the HttpSecurity
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.antMatchers("/customer").permitAll() //URL starting with customer need to be authenticated
			.anyRequest().authenticated() // rest of the URLs should be authenticated
			.and()
			.httpBasic().and() //Every request should be authenticated it should not store in a session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
			.and().csrf().disable();
	}
	
	/**
	 * Password Encoder to be used.
	 * @return
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2)
//				.select()
//				.apis(RequestHandlerSelectors.basePackage("com.mht.Demoproject.controller"))
//				.paths(PathSelectors.any())
//				.build();
//				.useDefaultResponseMessages(false); //For disabling response messages
		 return new Docket(DocumentationType.SWAGGER_2)  
		          .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build();        
		
	}

}
