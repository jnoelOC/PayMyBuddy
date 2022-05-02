package com.paymybuddy.pmb.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.paymybuddy.pmb.service.TransacService;
import com.paymybuddy.pmb.service.UserAccountService;

@Controller
public class UserAccountController {

	public static final Logger logger = LogManager.getLogger(UserAccountController.class);

	@Autowired
	UserAccountService userAccountService;

	@Autowired
	TransacService transacService;

//	@GetMapping("/useraccounts")
//	public List<UserAccount> findAllUserAccounts() {
//
//		List<UserAccount> lua = userAccountService.findAllUserAccounts();
//
//		if (lua.isEmpty()) {
//			logger.error("Erreur dans Find all userAccounts : status Non trouvé.");
//			return null;
//		}
//		logger.info("userAccounts trouvés.");
//		return lua;
//	}
//
//	@RequestMapping("/useraccount/addoneconnection")
//	public String addOneConnection(Model model) {
//		List<UserAccount> lua = userAccountService.findAllUserAccounts();
//		// model.addAttribute("transfer_page", lua.get(0).getFirstName());
//		List<String> names = new ArrayList<>();
//		names.add("clement");
//		names.add("jean-noel");
//		names.add("toto");
//		model.addAttribute("firstNames", names);
//		return "transfer_page :: #connections";
//	}
//
//	@PostMapping("/useraccount/addconnection")
//	public ResponseEntity<UserAccount> addConnection(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = null;
////		UserAccount ua = userAccountService.addConxUserAccount(userAccount);
//
////		if (ua.getLoginMail().isEmpty()) {
////			logger.error("Erreur dans add connection : status No PK.");
////			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
////		}
////		logger.info("connection ajoutée.");
//		return new ResponseEntity<>(ua, HttpStatus.CREATED);
//	}
//
//	@PostMapping("/useraccount/pay")
//	public ResponseEntity<Transac> transferMoney(@RequestBody UserAccount userAccount) {
//
//		Transac trx = null;
////		try {
////			trx = userAccountService.transferMoneyUserAccount(userAccount);
////		} catch (SQLException e) {
////			logger.error("Erreur SQL dans transfer money : " + e.getMessage());
////		}
//
//		if ((trx == null) || (trx.getIdTransaction() <= 0)) {
//			logger.error("Erreur dans transfer money : status No PK.");
//			return new ResponseEntity<>(trx, HttpStatus.NOT_FOUND);
//		}
//		logger.info("transaction ajoutée.");
//		return new ResponseEntity<>(trx, HttpStatus.CREATED);
//	}
//
//	@PostMapping("/useraccount/create")
//	public ResponseEntity<UserAccount> createUserAccount(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = userAccountService.saveUserAccount(userAccount);
//
//		if (ua.getLoginMail().isEmpty()) {
//			logger.error("Erreur dans create userAccount : status No PK.");
//			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
//		}
//		logger.info("userAccount trouvé.");
//		return new ResponseEntity<>(ua, HttpStatus.CREATED);
//	}
//
//	@PutMapping("/useraccount/update")
//	public ResponseEntity<UserAccount> updateUserAccount(@RequestBody UserAccount userAccount) {
//
//		UserAccount ua = userAccountService.saveUserAccount(userAccount);
//
//		if (ua.getLoginMail().isEmpty()) {
//			logger.error("Erreur dans update userAccount : status No PK.");
//			return new ResponseEntity<>(ua, HttpStatus.NOT_FOUND);
//		}
//		logger.info("userAccount mis à jour.");
//		return new ResponseEntity<>(ua, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/useraccount/delete")
//	public ResponseEntity<org.springframework.http.HttpStatus> deleteUserAccount(@RequestBody UserAccount userAccount) {
//
//		userAccountService.deleteUserAccount(userAccount);
//
//		logger.info("userAccount supprimé.");
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}