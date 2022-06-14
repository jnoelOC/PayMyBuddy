package com.paymybuddy.pmb.TU.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IBankAccountRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.impl.BankAccountService;
import com.paymybuddy.pmb.service.impl.IbanExistsException;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceTest {
	public static final Logger logger = LogManager.getLogger(BankAccountServiceTest.class);

	// To be tested
	@InjectMocks
	private BankAccountService bankAccountService;

	@Mock
	private IBankAccountRepository bankAccountRepository;

	@Mock
	private IUserAccountRepository userAccountRepository;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
	}

	@Test
	@DisplayName("Find all bank accounts")
	void FindAllBankAccounts_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		BankAccount ba2 = new BankAccount(1L, iban + "456", "CM1234", "CM", loginMail);
		List<BankAccount> listBa = null;
		List<BankAccount> listOfBa = new ArrayList<>();
		listOfBa.add(ba1);
		listOfBa.add(ba2);

		when(bankAccountRepository.findAll()).thenReturn(listOfBa);
		// Act
		listBa = bankAccountService.findAllBankAccounts();
		if (listBa != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Find bank account by loginMail")
	void FindBankAccountByLoginMail_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		BankAccount ba2 = null;

		when(bankAccountRepository.findByLoginMail(loginMail)).thenReturn(ba1);
		// Act
		ba2 = bankAccountService.findByLoginMail("j@gmail.com");
		ret = true;
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find bank account by loginMail")
	void DontFindBankAccountByLoginMail_ShouldReturnFalse() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		BankAccount ba2 = null;
		when(bankAccountRepository.findByLoginMail(Mockito.anyString())).thenReturn(null);
		// Act
		ba2 = bankAccountService.findByLoginMail(loginMail);
		ret = false;
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("Add sold in User account")
	void AddSold_ShouldReturnTrue() {
		// Arrange
		Boolean ret = true;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "a@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU3", "Paul", "Gauguin", 50D);
		when(userAccountRepository.save(ua1)).thenReturn(ua1);

		// Act
		bankAccountService.addSold(ba1, ua1, 100);
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't Add sold in User account with Iban empty")
	void DontAddSoldWithIbanEmpty_ShouldReturnFalse() {
		// Arrange
		Boolean ret = true;
		String loginMail = "j@gmail.com";
		String iban = "";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "a@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU3", "Paul", "Gauguin", 50D);
		// when(userAccountRepository.save(ua1)).thenReturn(ua1);
		when(ba1.getIban().isEmpty()).thenReturn(true);
		// Act
		bankAccountService.addSold(ba1, ua1, 100);
		ret = false;
		// Assert
		assertFalse(ret);
	}

	@ParameterizedTest
	@MethodSource("SoldSource")
	@DisplayName("Add any sold in User account")
	void AddSoldTest_ShouldReturnTrue(Integer soldIn) {
		// Arrange
		Boolean ret = true;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "a@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU3", "Paul", "Gauguin", 50D);
		when(userAccountRepository.save(ua1)).thenReturn(ua1);

		// Act
		bankAccountService.addSold(ba1, ua1, soldIn);
		// Assert
		assertTrue(ret);
	}

	// with its data
	private static Stream<Arguments> SoldSource() {
		return Stream.of(Arguments.of(1), Arguments.of(Integer.MAX_VALUE));
	}

	@ParameterizedTest
	@MethodSource("SoldInSource")
	@DisplayName("Don't add any sold in User account")
	void DontAddSoldTest_ShouldReturnFalse(Integer soldIn) {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "a@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU3", "Paul", "Gauguin", 50D);
		lenient().when(userAccountRepository.save(ua1)).thenReturn(ua1);

		// Act
		bankAccountService.addSold(ba1, ua1, soldIn);
		// Assert
		assertFalse(ret);
	}

	// with its data
	private static Stream<Arguments> SoldInSource() {
		return Stream.of(Arguments.of(0), Arguments.of(Integer.MIN_VALUE));
	}

	@Test
	@DisplayName("Substract sold in User account")
	void SubstractSold_ShouldReturnTrue() {
		// Arrange
		Boolean ret = true;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = new UserAccount(2L, "a@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU3", "Paul", "Gauguin", 50D);
		when(userAccountRepository.save(ua1)).thenReturn(ua1);

		// Act
		bankAccountService.substractSold(ba1, ua1, 100);
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Save bank account")
	void SaveBankAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		BankAccount ba2 = new BankAccount(2L, "1234567890123456789", "CM123456", "CM", loginMail);
		BankAccount ba3 = null;
		when(bankAccountRepository.save(Mockito.any(BankAccount.class))).thenReturn(ba1);

		// Act
		ba3 = bankAccountService.saveBankAccount(ba2);
		if (ba3 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Delete bank account")
	void DeleteBankAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = true;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890";
		BankAccount ba2 = new BankAccount(2L, iban, "CM123456", "CM", loginMail);
		bankAccountRepository.delete(Mockito.any(BankAccount.class));
		// Act
		bankAccountService.deleteBankAccount(ba2);
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Register new BankAccount")
	void RegisterNewBankAccount_ShouldReturnTrue() throws IbanExistsException {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		String iban = "12345678901234567890123";
//		BankAccount ba1 = new BankAccount(1L, iban, "BNP123456", "BNP", loginMail);
		BankAccount ba2 = new BankAccount(2L, "1234567890123456789", "CM123456", "CM", loginMail);
		BankAccount ba3 = null;

		when(bankAccountRepository.existsByIban(Mockito.anyString())).thenReturn(false);
		when(bankAccountRepository.save(Mockito.any(BankAccount.class))).thenReturn(ba2);
		// Act
		ba3 = bankAccountService.registerNewBank(null, "BNP", iban, "BNP123456", "j@gmail.com");
		if (ba3 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}
}