package com.paymybuddy.pmb.service;

import java.util.List;

import com.paymybuddy.pmb.model.BankAccount;

public interface IBankAccountService {
	public List<BankAccount> findAllBankAccounts();

	public BankAccount saveBankAccount(BankAccount bankAccount);

	public void deleteBankAccount(BankAccount bankAccount);
}
