package com.paymybuddy.pmb.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IUserAccountRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private IUserAccountRepository userAccountRepository;

	@Override
	public UserDetails loadUserByUsername(String loginMail) {

		UserAccount ua = userAccountRepository.findByLoginMail(loginMail);

		if (ua == null) {
			throw new UsernameNotFoundException(loginMail);
		}

		return new User(ua.getLoginMail(), ua.getPsswrd(),
				Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
	}
}
