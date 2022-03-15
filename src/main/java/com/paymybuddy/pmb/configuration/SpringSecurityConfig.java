package com.paymybuddy.pmb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("springuser").password(passwordEncoder().encode("spring123"))
				.roles("User").and().withUser("springadmin").password(passwordEncoder().encode("admin123"))
				.roles("ADMIN", "USER");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN").antMatchers("/user").hasRole("USER")
				.antMatchers("/resources/**").permitAll().anyRequest().authenticated().and().formLogin()
//				.loginPage("/login_page.html").permitAll()
				.and().logout().permitAll()
//				.loginProcessingUrl("/perform_login").defaultSuccessUrl("/home_page.html", true)
//				.failureUrl("/login_page.html?error=true")
		;
		// .and().oauth2Login();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
