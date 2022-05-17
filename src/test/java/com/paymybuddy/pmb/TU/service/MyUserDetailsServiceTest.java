package com.paymybuddy.pmb.TU.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.impl.MyUserDetailsService;

@ExtendWith(MockitoExtension.class)
class MyUserDetailsServiceTest {

	// To be tested
	@InjectMocks
	private MyUserDetailsService myUserDetailsService;

	@Mock
	private IUserAccountRepository userAccountRepository;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
	}

	/**
	 * This method checks login in error
	 */
	@Test
	@DisplayName("find login in error")
	void FindLoginInError_ShouldReturnFalse() {
		// Arrange
		Boolean ret = true;
		UserDetails userDetails;
		String loginMail = "inconnu@gmail.com";
		// Act
		userDetails = myUserDetailsService.loadUserByUsername(loginMail);
		if (userDetails == null) {
			ret = false;
		}
		// Assert
		assertFalse(ret);

	}

	/**
	 * This method checks password in error
	 */
	@Test
	@DisplayName("find password in error")
	void FindPasswordInError_ShouldReturnFalse() {
		// Arrange
		Boolean ret = true;
		UserDetails userDetails;
		String loginMail = "j@gmail.com";
		UserAccount ua = null;

		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua);
		// Act
		userDetails = myUserDetailsService.loadUserByUsername(loginMail);
		if (userDetails == null) {
			ret = false;
		}
		// Assert
		assertFalse(ret);

	}

	/**
	 * This method checks login and password are right
	 */
	@Test
	@DisplayName("find login and password")
	void FindLoginAndPassword_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		UserDetails userDetails;
		String loginMail = "j@gmail.com";
		String password = "$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2";
		UserAccount ua = new UserAccount(1L, loginMail, password, "Max", "Jacob", 500D);

		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua);
		// Act
		userDetails = myUserDetailsService.loadUserByUsername(loginMail);
		if (userDetails != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

}
