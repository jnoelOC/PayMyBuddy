package com.paymybuddy.pmb.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IBankAccountRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;

@Service
public class BankAccountService {
	public static final Logger logger = LogManager.getLogger(BankAccountService.class);

	@Autowired
	private IBankAccountRepository bankAccountRepository;

	@Autowired
	private IUserAccountRepository userAccountRepository;

	public List<BankAccount> findAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	public BankAccount findByLoginMail(String user) {
		return bankAccountRepository.findByLoginMail(user);
	}

	public void addSold(BankAccount bankAccount, UserAccount sender, Integer sold) {
		try {
			if ((sold > 0) && (sender.getLoginMail().equalsIgnoreCase(bankAccount.getLoginMail()))) {
				sender.setSolde(sender.getSolde() + sold);
				userAccountRepository.save(sender);
			}
		} catch (Exception ex) {
			logger.error("Error dans addSold : %s ", ex.getMessage());
		}
	}

	public void substractSold(BankAccount bankAccount, UserAccount sender, Integer sold) {
		try {
			if ((sender.getSolde() >= sold) && (sender.getLoginMail().equalsIgnoreCase(bankAccount.getLoginMail()))) {
				sender.setSolde(sender.getSolde() - sold);
				userAccountRepository.save(sender);
			}
		} catch (Exception ex) {
			logger.error("Error dans substractSold : %s ", ex.getMessage());
		}
	}

	@Transactional
	public BankAccount saveBankAccount(BankAccount bankAccount) {

		return bankAccountRepository.save(bankAccount);
	}

	@Transactional
	public void deleteBankAccount(BankAccount bankAccount) {
		bankAccountRepository.delete(bankAccount);
	}

	public BankAccount registerNewBank(Long id, String bankName, String iban, String bic, String loginMail)
			throws IbanExistsException {

		Boolean ibanExists = bankAccountRepository.existsByIban(iban);

		if (Boolean.TRUE.equals(ibanExists)) {
			throw new IbanExistsException("This bank account already exists with that iban: " + iban);
		}
		BankAccount registration = new BankAccount(null, bankName, iban, bic, loginMail);

		return bankAccountRepository.save(registration);
	}
}
