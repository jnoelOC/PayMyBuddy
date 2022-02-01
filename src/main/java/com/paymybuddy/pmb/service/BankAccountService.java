package com.paymybuddy.pmb.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.repository.IBankAccountRepository;

@Service
public class BankAccountService {
	public static final Logger logger = LogManager.getLogger(BankAccountService.class);

	@Autowired
	private IBankAccountRepository bankAccountRepository;

	public List<BankAccount> findAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	@Transactional
	public BankAccount saveBankAccount(BankAccount bankAccount) {

		return bankAccountRepository.save(bankAccount);
	}

	@Transactional
	public void deleteBankAccount(BankAccount bankAccount) {
		bankAccountRepository.delete(bankAccount);
	}
}
