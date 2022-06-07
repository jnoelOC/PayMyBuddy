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

/**
 * 
 * This class realize all the controller operation on BankAccount.
 * 
 * @author jean-noel.chambe
 * 
 */
@Controller
public class BankAccountController {
	public static final Logger logger = LogManager.getLogger(BankAccountController.class);

	@Autowired
	BankAccountService bankAccountService;

	@Autowired
	UserAccountService userAccountService;

	@PostMapping("/bank/addSold")
	public String addSoldPost(Principal principal,
			@RequestParam(value = "sold", name = "sold", required = false) Integer sold) {
		try {
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			BankAccount bankAccount = bankAccountService.findByLoginMail(principal.getName());

			bankAccountService.addSold(bankAccount, sender, sold);

		} catch (Exception ex) {
			logger.error("Error dans addSoldPost : " + ex.getMessage());
		}
		return "redirect:/transfer";
	}

	@PostMapping("/bank/substractSold")
	public String substractSoldPost(Principal principal,
			@RequestParam(value = "sold", name = "sold", required = false) Integer sold) {
		try {
			BankAccount bankAccount = bankAccountService.findByLoginMail(principal.getName());
			UserAccount sender = userAccountService.findByLoginMail(principal.getName());
			bankAccountService.substractSold(bankAccount, sender, sold);
		} catch (Exception ex) {
			logger.error("Error dans substractSoldPost : " + ex.getMessage());
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
			logger.error("Error dans deleteBank : " + ex.getMessage());
		}
		return "bank_page";
	}

}
