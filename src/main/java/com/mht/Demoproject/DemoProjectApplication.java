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
	 * This method provide configuration about how to authenticate application user
	 *   
	 * Current implementation -->> database authentication with default tables
	 * As of now I think authorities() is the mandatory parameter other wise it will give 403 error
	 * @param auth
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
		auth.jdbcAuthentication().dataSource(dataSource) // specify JDBC authentication to Spring security by injecting the required datasource bean 
		.usersByUsernameQuery("select username,password,enabled from users where username=?") //query statements to fetch the user details from the database.
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
	 *  Current Implementation --> This method provide access to user with role employee only using form-login
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
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
