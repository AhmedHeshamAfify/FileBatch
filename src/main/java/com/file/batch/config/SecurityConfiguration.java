package com.file.batch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


	    @Override
	    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception
	    {
	    	authenticationManagerBuilder.inMemoryAuthentication().withUser("Ahmed").password("123").roles("ADMIN");

	    }
	    
	    @Bean
	    public PasswordEncoder getPasswordEncoder() {
	    	return NoOpPasswordEncoder.getInstance();
	    }
}
