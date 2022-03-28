package com.paymybuddy.pmb.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paymybuddy.pmb.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private MyUserDetailsService uds;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(uds).passwordEncoder(passwordEncoder());
//		auth.inMemoryAuthentication().withUser("springuser").password(passwordEncoder().encode("spring")).roles("User")
//				.and().withUser("springadmin").password(passwordEncoder().encode("admin")).roles("ADMIN", "USER");
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/register").permitAll();
		http.authorizeRequests().antMatchers("/resources/static/css/*.css", "/resources/**").permitAll();
		http.authorizeRequests().antMatchers("/admin").hasRole("ADMIN").antMatchers("/user").hasRole("USER")
				.anyRequest().authenticated();
		http.formLogin().loginPage("/index").permitAll().loginProcessingUrl("/login").defaultSuccessUrl("/home", true)
				.failureUrl("/login_page.html");
		http.logout().logoutUrl("/index").invalidateHttpSession(true).deleteCookies("JSESSIONID");
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
