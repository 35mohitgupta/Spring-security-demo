package com.mht.Demoproject;

import javax.sql.DataSource;

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

	@Autowired
	private DataSource dataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(DemoProjectApplication.class, args);
	}
	
	
	/**
	 * Methos returning BCryptPasswordEncoder object
	 * Can be a bean method if passwordEncoder is also used somewhere else
	 * @return
	 */
	 public BCryptPasswordEncoder passwordEncoder() {
	        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	        return bCryptPasswordEncoder;
	 }
	
	/**
	 * This method provide configuration about how to authenticate application user
	 *   
	 * Current implementation -->> database authentication with user-defined tables and password encoder
	 * As of now there is some issue 
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		
		String encodedValue = passwordEncoder().encode("ram");
		System.out.println(">>>>> encoded value: "+encodedValue);
		System.out.println(">>> matcher : "+passwordEncoder().matches("ram", encodedValue));
		auth.jdbcAuthentication().dataSource(dataSource) // specify JDBC authentication to Spring security by injecting the required datasource bean 
		.passwordEncoder(passwordEncoder()) //using passwordEncoder to encode password
		.usersByUsernameQuery("select username,password,enabled from users1 where username=?") //query statements to fetch the user details from the database.
		.authoritiesByUsernameQuery("select username, role from user_roles where username=?"); //query statements to fetch the user role details from the database.
	}
	
	/**
	 * Configuring the HttpSecurity
	 * This method provide information to spring security about - 
	 * 	- which requests to authenticate
	 *  - whether to support form-based or http-basic authentication
	 *  - configure secure logout feature
	 *  - configure concurrent session management
	 *  - configure URL based security on basis of ROLES
	 *  
	 *  Current Implementation --> This method provide access to user with role SELLER only using form-login
	 *  NOTE - we are using self-signed certificate so we can't access this using any standard browser
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
//		http.requiresChannel().anyRequest().requiresSecure(); //compulsate any request to be using https protocol
		
		http.sessionManagement().maximumSessions(1); //one user can have atmost 1 concurrent session
		
		http.authorizeRequests()
			.antMatchers("/customer/**").permitAll() //gives access to /customer/* with any login
			.antMatchers("/**").hasAnyRole("SELLER") // only the user with seller role can access the whole endpoints others will get NOT_FOUND
			.anyRequest().authenticated() // every request except the /customer/* will need authentication
			.and().formLogin()
			;
	}
	
	

	
	/**
	 * It configures the SwaggerUI
	 * @return
	 */
	@Bean
	public Docket api() {
		 return new Docket(DocumentationType.SWAGGER_2)  
		          .select()                                  
		          .apis(RequestHandlerSelectors.any())              
		          .paths(PathSelectors.any())                          
		          .build();        
		
	}
	
	

}
