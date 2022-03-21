package com.paymybuddy.pmb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paymybuddy.pmb.model.UserAccount;

@Service
public class MyUserDetailsService implements UserDetailsService {

//	@Autowired
//	private IUserAccountRepository userAccountRepository;

	@Autowired
	private UserAccountService userAccountService;

	@Override
	public UserDetails loadUserByUsername(String username) {

		UserAccount userAccount = null;
		List<UserAccount> lua = userAccountService.findAllUserAccounts();
		for (UserAccount ua : lua) {
			if (ua.getFirstName().equals(username)) {
				userAccount = ua;
				break;
			}
		}

//		UserAccount userAccount = userAccountRepository.findByUsername(username);
		if (userAccount == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserPrincipal(userAccount);
	}

}
