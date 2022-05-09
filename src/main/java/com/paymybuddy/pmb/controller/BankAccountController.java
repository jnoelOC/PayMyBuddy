package com.paymybuddy.pmb.controller;

import java.security.Principal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.impl.BankAccountService;
import com.paymybuddy.pmb.service.impl.UserAccountService;

@Controller
public class BankAccountController {
	public static final Logger logger = LogManager.getLogger(BankAccountController.class);

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	UserAccountService userAccountService;

	@PostMapping("/bank/addSold")
	public String addSoldGet(Principal principal,
			@RequestParam(value = "sold", name = "sold", required = false) Integer sold) {
		try {
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			BankAccount bankAccount = bankAccountService.findByLoginMail(principal.getName());

			bankAccountService.addSold(bankAccount, sender, sold);

		} catch (Exception ex) {
			logger.error("Error dans addSoldGet : %s ", ex.getMessage());
		}
		return "redirect:/transfer";
	}

	@PostMapping("/bank/substractSold")
	public String substractSoldGet(Principal principal,
			@RequestParam(value = "sold", name = "sold", required = false) Integer sold) {
		try {
			BankAccount bankAccount = bankAccountService.findByLoginMail(principal.getName());
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			bankAccountService.substractSold(bankAccount, sender, sold);
		} catch (Exception ex) {
			logger.error("Error dans substractSoldGet : %s ", ex.getMessage());
		}
		return "redirect:/transfer";
	}

	@PostMapping("/bank/add")
	public String addBankPost(Principal principal, RedirectAttributes redirectAttributes,
			@RequestParam(value = "bankName", name = "bankName", required = false) String bankName,
			@RequestParam(value = "iban", name = "iban", required = false) String iban,
			@RequestParam(value = "bic", name = "bic", required = false) String bic) {
		try {

			BankAccount registration = bankAccountService.registerNewBank(null, bankName, iban, bic,
					principal.getName());

			if (registration == null) {
				redirectAttributes.addAttribute("attribute", "index");
			}

		} catch (Exception ex) {
			logger.error("Error dans addBank : %s ", ex.getMessage());
		}
		return "redirect:/bank";
	}

	@GetMapping("/bank")
	public String bankGet(Principal principal, Model bankModel) {

		BankAccount ba = bankAccountService.findByLoginMail(principal.getName());
		bankModel.addAttribute("banks", ba);

		return "bank_page";
	}

	@GetMapping("/bank/delete")
	public String deleteBankGet(
			@RequestParam(value = "userBank", name = "userBank", required = false) String userBank) {
		try {

			BankAccount baOfUser = bankAccountService.findByLoginMail(userBank);
			bankAccountService.deleteBankAccount(baOfUser);

		} catch (Exception ex) {
			logger.error("Error dans deleteBank : %s ", ex.getMessage());
		}
		return "bank_page";
	}

//	@GetMapping("/bankaccounts")
//	public List<BankAccount> findAllBankAccounts() {
//
//		List<BankAccount> lba = bankAccountService.findAllBankAccounts();
//
//		if (lba.isEmpty()) {
//			logger.error("Erreur dans Find all bankAccounts : status Non trouvé.");
//			return null;
//		}
//		logger.info("bankAccounts trouvés.");
//		return lba;
//	}
//
//	@PostMapping("/bankaccount/create")
//	public ResponseEntity<BankAccount> createBankAccount(@RequestBody BankAccount bankAccount) {
//
//		BankAccount ba = bankAccountService.saveBankAccount(bankAccount);
//
//		if (ba.getId() <= 0) {
//			logger.error("Erreur dans create bankAccount : status No PK.");
//			return new ResponseEntity<>(ba, HttpStatus.NOT_FOUND);
//		}
//		logger.info("bankAccount trouvé.");
//		return new ResponseEntity<>(ba, HttpStatus.CREATED);
//	}
//
//	@PutMapping("/bankaccount/update")
//	public ResponseEntity<BankAccount> updateUserAccount(@RequestBody BankAccount bankAccount) {
//
//		BankAccount ba = bankAccountService.saveBankAccount(bankAccount);
//
//		if (ba.getId() <= 0) {
//			logger.error("Erreur dans update bankAccount : status No PK.");
//			return new ResponseEntity<>(ba, HttpStatus.NOT_FOUND);
//		}
//		logger.info("bankAccount mis à jour.");
//		return new ResponseEntity<>(ba, HttpStatus.OK);
//	}
//
//	@DeleteMapping("/bankaccount/delete")
//	public ResponseEntity<org.springframework.http.HttpStatus> deleteBankAccount(@RequestBody BankAccount bankAccount) {
//
//		bankAccountService.deleteBankAccount(bankAccount);
//
//		logger.info("bankAccount supprimé.");
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
}
