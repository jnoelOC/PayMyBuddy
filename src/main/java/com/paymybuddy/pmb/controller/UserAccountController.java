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

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.IUserAccountService;

@RestController
public class UserAccountController {

	public static final Logger logger = LogManager.getLogger(UserAccountController.class);

	@Autowired
	IUserAccountService userAccountService;

	@GetMapping("/useraccounts")
	public List<UserAccount> findAllUserAccounts() {

		List<UserAccount> lua = userAccountService.findAllUserAccounts();

		if (lua.isEmpty()) {
			logger.error("Erreur dans Find all userAccounts : status Non trouvé.");
			return null;
		}
		logger.info("userAccounts trouvés.");
		return lua;
	}

	@PostMapping("/useraccount/create")
	public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {

		UserAccount ua = userAccountService.saveUserAccount(userAccount);

		if (ua.getLoginMail().isEmpty()) {
			logger.error("Erreur dans create userAccount : status No PK.");
			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
		}
		logger.info("userAccount trouvé.");
		return new ResponseEntity<>(ua, HttpStatus.CREATED);
	}

	@PutMapping("/useraccount/update")
	public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccount userAccount) {

		UserAccount ua = userAccountService.saveUserAccount(userAccount);

		if (ua.getLoginMail().isEmpty()) {
			logger.error("Erreur dans update userAccount : status No PK.");
			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
		}
		logger.info("userAccount mis à jour.");
		return new ResponseEntity<>(ua, HttpStatus.OK);
	}

	@DeleteMapping("/useraccount/delete")
	public ResponseEntity<org.springframework.http.HttpStatus> deleteUserAccount(@RequestBody UserAccount userAccount) {

		userAccountService.deleteUserAccount(userAccount);

		logger.info("userAccount supprimé.");
		return new ResponseEntity<>(HttpStatus.OK);
	}
}