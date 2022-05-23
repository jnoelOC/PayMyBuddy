package com.paymybuddy.pmb.TU.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.ITransacRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.impl.EmailExistsException;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@ExtendWith(MockitoExtension.class)
class UserAccountServiceTest {
	public static final Logger logger = LogManager.getLogger(UserAccountServiceTest.class);

	// To be tested
	@InjectMocks
	private UserAccountService userAccountService;

	@Mock
	private IUserAccountRepository userAccountRepository;

	@Mock
	private ITransacRepository transacRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
	}

	@Test
	@DisplayName("Find all user accounts")
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

		when(userAccountRepository.findAll()).thenReturn(listOfUa);
		// Act
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

		when(userAccountRepository.findAll()).thenReturn(listOfUa);
		// Act
		listOfUa = userAccountService.findAllUserAccounts();
		if (listOfUa == null) {
			ret = false;
		}
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("Find user account by loginMail")
	void FindUserAccountByLoginMail_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String loginMail = "j@gmail.com";
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		UserAccount ua2 = null;

		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua1);
		// Act
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

		when(userAccountRepository.findByLoginMail(loginMail)).thenReturn(ua1);
		// Act
		ua2 = userAccountService.findByLoginMail(loginMail);
		if (ua2 == null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Find user account by id")
	void FindUserAccountById_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		UserAccount ua1 = new UserAccount(1L, "j@gmail.com",
				"$2a$10$MdYdeJHJ4.r1HJF0h2XUm.fa5.AfDhKqX.eVmhgVKPKCViAHPoYU2", "Max", "Jacob", 500D);
		Optional<UserAccount> ua2 = null;

		when(userAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ua1));
		// Act
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
		Optional<UserAccount> ua2;

		lenient().when(userAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ua1));
		// Act
		ua2 = userAccountService.findById(0L);
		if (ua2.equals(Optional.of(ua1))) {
			ret = true;
		}
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("Save user account")
	void SaveUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String mail = "jojo@gmail.com";
		UserAccount ua1 = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua2 = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);

		when(userAccountRepository.existsByLoginMail(Mockito.anyString())).thenReturn(false);
		when(userAccountRepository.save(ua1)).thenReturn(ua2);
		// Act
		ua2 = userAccountService.saveUserAccount(ua1);
		if (ua2.getLoginMail().equalsIgnoreCase(ua1.getLoginMail())) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't save user account")
	void DontSaveUserAccount_ShouldReturnFalse() {
		// Arrange
		Boolean ret = true;
		String mail = "jojo@gmail.com";
		UserAccount ua1 = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua2 = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);

		when(userAccountRepository.existsByLoginMail(Mockito.anyString())).thenReturn(true);
		// Act
		ua2 = userAccountService.saveUserAccount(ua1);
		if (ua2 == null) {
			ret = false;
		}
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("Add Connection UserAccount")
	void AddConnectionUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		Long idOfReceiver = 2L;
		String mail = "jojo@gmail.com";
		UserAccount sender = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua1 = new UserAccount(2L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		UserAccount ua2 = new UserAccount(3L, "jiji@gmail.com", "jiji", "Max", "Jacob", 30D);
		List<UserAccount> connections = new ArrayList<>();
		connections.add(ua1);
		connections.add(ua2);
		UserAccount ua3 = new UserAccount(4L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua4 = new UserAccount(5L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		UserAccount ua5 = new UserAccount(6L, "jiji@gmail.com", "jiji", "Max", "Jacob", 30D);
		UserAccount ua6 = new UserAccount(7L, "juju@gmail.com", "juju", "Max", "Jacob", 30D);
		List<UserAccount> listOfAllUa = new ArrayList<>();
		listOfAllUa.add(ua3);
		listOfAllUa.add(ua4);
		listOfAllUa.add(ua5);
		listOfAllUa.add(ua6);

		lenient().when(userAccountService.retrieveConxUserAccount(sender)).thenReturn(connections);
		lenient().when(userAccountService.findAllUserAccounts()).thenReturn(listOfAllUa);
		when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(ua1);

		// Act
		ua2 = userAccountService.addConxUserAccount(sender, idOfReceiver);
		if (ua2 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Retrieve Connection UserAccount")
	void RetrieveConnectionUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String mail = "jojo@gmail.com";
		UserAccount sender = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua1 = new UserAccount(2L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		List<UserAccount> connections = new ArrayList<>();
		List<Long> listOfLg = new ArrayList<>();
		listOfLg.add(1L);
		listOfLg.add(2L);
		listOfLg.add(3L);

		lenient().when(userAccountRepository.chercherConnexions(Mockito.anyLong())).thenReturn(listOfLg);
		when(userAccountRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ua1));
		// Act
		connections = userAccountService.retrieveConxUserAccount(sender);
		if (connections != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);

	}

	@Test
	@DisplayName("Compute new sold to bank admin")
	void computeNewSoldToBankAdmin_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		Double amount = 10D;
		Double discount = 0.005 * amount;
		Double soldSender = 16D;
		Double result = 0D;
		UserAccount admin = new UserAccount(3L, "admin@gmail.com", "admin", "Admin", "admin", 100D);

		when(userAccountRepository.findByLoginMail("admin@gmail.com")).thenReturn(admin);
		when(userAccountRepository.save(admin)).thenReturn(admin);
		// Act
		result = userAccountService.computeNewSoldToBankAdmin(soldSender, amount, discount);
		if (result >= 0D) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Transfer money between User Accounts")
	void TransferMoneyUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String senderMail = "jojo@gmail.com";
		String receiverMail = "jaja@gmail.com";
		UserAccount sender = new UserAccount(1L, senderMail, "jojo", "Max", "Jacob", 50D);
		UserAccount receiver = new UserAccount(2L, receiverMail, "jaja", "Max", "Jacob", 20D);
		UserAccount admin = new UserAccount(3L, "admin@gmail.com", "admin", "admin", "admin", 100D);
		Transac transac1 = new Transac(1L, "descriptio", 0D, "senderMail", "receiverMail");
		Transac transac2 = new Transac(2L, "descriptio", 10D, "senderMail", "receiverMail");
		Transac transac3 = null;
		Double amount = 11D;
		Double newSold = 16D;

		lenient().when(userAccountRepository.findByLoginMail(senderMail)).thenReturn(sender);
		lenient().when(userAccountRepository.findByLoginMail(receiverMail)).thenReturn(receiver);
		lenient().when(userAccountRepository.save(receiver)).thenReturn(receiver);
		when(userAccountRepository.findByLoginMail("admin@gmail.com")).thenReturn(admin);
		when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(admin);
		lenient().when(transacRepository.save(Mockito.any(Transac.class))).thenReturn(transac2);
		// Act
		try {
			transac3 = userAccountService.transferMoneyUserAccount(senderMail, receiverMail, "descriptions", amount);
		} catch (SQLException e) {
			logger.error("TransferMoneyUserAccount_ShouldReturnTrue: %s ", e.getMessage());
		}
		if (transac3 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Delete user account")
	void DeleteUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String mail = "jojo@gmail.com";
		UserAccount ua1 = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		userAccountRepository.delete(Mockito.any(UserAccount.class));

		// Act
		userAccountService.deleteUserAccount(ua1);
		ret = true;

		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Delete Connection UserAccount")
	void DeleteConnectionUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String mail = "jojo@gmail.com";
		UserAccount sender = new UserAccount(1L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua1 = new UserAccount(2L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		UserAccount ua2 = new UserAccount(3L, "jiji@gmail.com", "jiji", "Max", "Jacob", 30D);
		List<UserAccount> connections = new ArrayList<>();
		connections.add(ua1);
		connections.add(ua2);
		UserAccount ua3 = new UserAccount(4L, mail, "jojo", "Max", "Jacob", 50D);
		UserAccount ua4 = new UserAccount(5L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		UserAccount ua5 = new UserAccount(6L, "jiji@gmail.com", "jiji", "Max", "Jacob", 30D);
		UserAccount ua6 = new UserAccount(7L, "juju@gmail.com", "juju", "Max", "Jacob", 30D);
		List<UserAccount> listOfAllUa = new ArrayList<>();
		listOfAllUa.add(ua3);
		listOfAllUa.add(ua4);
		listOfAllUa.add(ua5);
		listOfAllUa.add(ua6);
		lenient().when(userAccountService.retrieveConxUserAccount(sender)).thenReturn(connections);
		lenient().when(userAccountService.findAllUserAccounts()).thenReturn(listOfAllUa);
		// when(userAccountRepository.save(sender)).thenReturn(ua1);
		when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(ua1);
		// Act
		ua2 = userAccountService.deleteConxUserAccount(sender, mail);

		if (ua2 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Register new UserAccount")
	void RegisterNewUserAccount_ShouldReturnTrue() throws EmailExistsException {
		// Arrange
		Boolean ret = false;
		UserAccount ua1 = new UserAccount(2L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
		UserAccount ua2 = null;

		when(userAccountRepository.existsByLoginMail(Mockito.anyString())).thenReturn(false);
		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("nouveauMdp");
		when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(ua1);
		// Act
		ua2 = userAccountService.registerNewUserAccount(null, "nouveau@gmail.com", "motdepasse", "paul", "Gauguin",
				12D);
		if (ua2 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

//	@Test
//	@DisplayName("Don't register new UserAccount")
//	void DontRegisterNewUserAccount_ShouldReturnTrue() throws EmailExistsException {
//		// Arrange
//		Boolean ret = false;
//		UserAccount ua1 = new UserAccount(2L, "jaja@gmail.com", "jaja", "Max", "Jacob", 20D);
//		UserAccount ua2 = null;
//
//		when(userAccountRepository.existsByLoginMail(Mockito.anyString())).thenReturn(true);
//		when(passwordEncoder.encode(Mockito.anyString())).thenReturn("nouveauMdp");
////		when(userAccountRepository.save(Mockito.any(UserAccount.class))).thenReturn(ua1);
//		// Act
//		ua2 = userAccountService.registerNewUserAccount(null, "nouveau@gmail.com", "motdepasse", "paul", "Gauguin",
//				12D);
//		if (ua2 == null) {
//			ret = true;
//		}
//		// Assert
//		assertTrue(ret);
//	}
}
