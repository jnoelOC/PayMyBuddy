package com.paymybuddy.pmb.TU.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.repository.ITransacRepository;
import com.paymybuddy.pmb.service.impl.TransacService;

@ExtendWith(MockitoExtension.class)
class TransacServiceTest {
	public static final Logger logger = LogManager.getLogger(TransacServiceTest.class);

	// To be tested
	@InjectMocks
	private TransacService transacService;

	@Mock
	private ITransacRepository transacRepository;

	@BeforeAll
	private static void setUp() {
	}

	@BeforeEach
	private void setUpPerTest() {
	}

	@Test
	@DisplayName("Find all transacs")
	void FindAllTransacs_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		List<Transac> listResultingOfTrx = null;
		List<Transac> listOfTrx = new ArrayList<>();
		listOfTrx.add(trx1);
		listOfTrx.add(trx2);

		when(transacRepository.findAll()).thenReturn(listOfTrx);

		// Act
		listResultingOfTrx = transacService.findAllTransactions();
		if (listResultingOfTrx != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Find all transacs by receiver")
	void FindAllTransacsByReceiver_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String receiver = "c@gmail.com";
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		List<Transac> listResultingOfTrx = null;
		List<Transac> listOfTrx = new ArrayList<>();
		listOfTrx.add(trx1);
		listOfTrx.add(trx2);

		when(transacRepository.findAll()).thenReturn(listOfTrx);

		// Act
		listResultingOfTrx = transacService.findAllTransactionsByReceiver(receiver);
		if (listResultingOfTrx != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find all transacs by receiver")
	void DontFindAllTransacsByReceiver_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String receiver = "q@gmail.com";
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		List<Transac> listResultingOfTrx = null;
		List<Transac> listOfTrx = new ArrayList<>();
		listOfTrx.add(trx1);
		listOfTrx.add(trx2);

		when(transacRepository.findAll()).thenReturn(listOfTrx);

		// Act
		listResultingOfTrx = transacService.findAllTransactionsByReceiver(receiver);
		if (listResultingOfTrx.isEmpty()) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Find all transacs by sender")
	void FindAllTransacsBySender_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String sender = "j@gmail.com";
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		List<Transac> listResultingOfTrx = null;
		List<Transac> listOfTrx = new ArrayList<>();
		listOfTrx.add(trx1);
		listOfTrx.add(trx2);

		when(transacRepository.findAll()).thenReturn(listOfTrx);

		// Act
		listResultingOfTrx = transacService.findAllTransactionsByGiver(sender);
		if (listResultingOfTrx != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't find all transacs by sender")
	void DontFindAllTransacsBySender_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		String sender = "q@gmail.com";
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		List<Transac> listResultingOfTrx = null;
		List<Transac> listOfTrx = new ArrayList<>();
		listOfTrx.add(trx1);
		listOfTrx.add(trx2);

		when(transacRepository.findAll()).thenReturn(listOfTrx);

		// Act
		listResultingOfTrx = transacService.findAllTransactionsByGiver(sender);
		if (listResultingOfTrx.isEmpty()) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Save transac")
	void SaveTransac_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = new Transac(2L, "description2", 14D, "j@gmail.com", "c@gmail.com");
		Transac trx3 = null;
		when(transacRepository.save(Mockito.any(Transac.class))).thenReturn(trx2);

		// Act
		trx3 = transacService.saveTransac(trx1);
		if (trx3 != null) {
			ret = true;
		}
		// Assert
		assertTrue(ret);
	}

	@Test
	@DisplayName("Don't save transac")
	void DontSaveTransac_ShouldReturnFalse() {
		// Arrange
		Boolean ret = false;
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		Transac trx2 = null;
		Transac trx3 = null;

		when(transacRepository.save(Mockito.any(Transac.class))).thenReturn(trx2);
		// Act
		trx3 = transacService.saveTransac(trx1);
		if (trx3 != null) {
			ret = true;
		}
		// Assert
		assertFalse(ret);
	}

	@Test
	@DisplayName("Delete transac")
	void DeleteUserAccount_ShouldReturnTrue() {
		// Arrange
		Boolean ret = false;
		Transac trx1 = new Transac(1L, "description1", 12D, "j@gmail.com", "a@gmail.com");
		transacRepository.delete(Mockito.any(Transac.class));

		// Act
		transacService.deleteTransac(trx1);
		ret = true;

		// Assert
		assertTrue(ret);
	}
}