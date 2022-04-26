package com.paymybuddy.pmb.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.service.BankAccountService;

@RestController
public class BankAccountController {
	public static final Logger logger = LogManager.getLogger(BankAccountController.class);

	@Autowired
	BankAccountService bankAccountService;

	@GetMapping("/bankaccounts")
	public List<BankAccount> findAllBankAccounts() {

		List<BankAccount> lba = bankAccountService.findAllBankAccounts();

		if (lba.isEmpty()) {
			logger.error("Erreur dans Find all bankAccounts : status Non trouvé.");
			return null;
		}
		logger.info("bankAccounts trouvés.");
		return lba;
	}

	@PostMapping("/bankaccount/create")
	public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {

		BankAccount ba = bankAccountService.saveBankAccount(bankAccount);

		if (ba.getId() <= 0) {
			logger.error("Erreur dans create bankAccount : status No PK.");
			return new ResponseEntity<>(ba, HttpStatus.NOT_FOUND);
		}
		logger.info("bankAccount trouvé.");
		return new ResponseEntity<>(ba, HttpStatus.CREATED);
	}

	@PutMapping("/bankaccount/update")
	public ResponseEntity<BankAccount> updateUserAccount(@RequestBody BankAccount bankAccount) {

		BankAccount ba = bankAccountService.saveBankAccount(bankAccount);

		if (ba.getId() <= 0) {
			logger.error("Erreur dans update bankAccount : status No PK.");
			return new ResponseEntity<>(ba, HttpStatus.NOT_FOUND);
		}
		logger.info("bankAccount mis à jour.");
		return new ResponseEntity<>(ba, HttpStatus.OK);
	}

	@DeleteMapping("/bankaccount/delete")
	public ResponseEntity<org.springframework.http.HttpStatus> deleteBankAccount(@RequestBody BankAccount bankAccount) {

		bankAccountService.deleteBankAccount(bankAccount);

		logger.info("bankAccount supprimé.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
