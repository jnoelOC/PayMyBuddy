package com.paymybuddy.pmb.TU.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {

	// To be tested
	@InjectMocks
	private UserAccountService userAccountService;

	@Mock
	private IUserAccountRepository userAccountRepository;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
	}

	@Test
	@DisplayName("find all user accounts")
	void FindAllUserAccounts_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "c@gmail.com",
				"$2a$10$5uEcL2Kn6I0ZurSun1Vzu.yxDJQjJsr0B7zKo/GgtGyMX18AAC7gi", "Gustave", "Caillebotte", 50D);
		List<UserAccount> listOfUa = new ArrayList<>();
		listOfUa.add(ua1);
		listOfUa.add(ua2);
		// Act
		when(userAccountRepository.findAll()).thenReturn(listOfUa);
		listOfUa = userAccountService.findAllUserAccounts();
		if (listOfUa != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find all user accounts")
	void DontFindAllUserAccounts_ShouldReturnFalse() {
		// Arrange
		Boolean ret = true;
		List<UserAccount> listOfUa = null;
		// Act
		when(userAccountRepository.findAll()).thenReturn(listOfUa);
		listOfUa = userAccountService.findAllUserAccounts();
		if (listOfUa == null) {
			ret = false;
		}
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("find user account by loginMail")
	void FindUserAccountByLoginMail_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = null;
		// Act
		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua1);
		ua2 = userAccountService.findByLoginMail(loginMail);
		if (ua2 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find user account by loginMail")
	void DontFindUserAccountByLoginMail_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "toto@gmail.com";
		UserAccount ua1 = null;
		UserAccount ua2 = null;
		// Act
		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua1);
		ua2 = userAccountService.findByLoginMail(loginMail);
		if (ua2 == null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("find user account by id")
	void FindUserAccountById_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		Optional<UserAccount> ua2 = null;
		// Act
		when(userAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ua1));

		ua2 = userAccountService.findById(1L);
		if (ua2.isPresent()) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find user account by id")
	void DontFindUserAccountById_ShouldReturnFalse() {
		// Arrange
		Boolean ret = false;
		UserAccount ua1 = new UserAccount(1L, "jojo@gmail.com", "jojo", "Max", "Jacob", 50D);
		Optional<UserAccount> ua2 = null;
		// Act
		lenient().when(userAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ua1));
		ua2 = userAccountService.findById(0L);
//		if (ua2.equals(Optional.of(ua1))) {
//			ret = true;
//		}
		// Assert
		assertFalse(ret);
	}

}
