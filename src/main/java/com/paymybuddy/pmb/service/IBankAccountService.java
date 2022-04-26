package com.paymybuddy.pmb.service;

import java.util.List;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.UserAccount;

public interface IBankAccountService {
	public List<BankAccount> findAllBankAccounts();

	public void addSold(BankAccount bankAccount, UserAccount sender, Integer sold);

	public void substractSold(BankAccount bankAccount, UserAccount sender, Integer sold);

	public BankAccount saveBankAccount(BankAccount bankAccount);

	public void deleteBankAccount(BankAccount bankAccount);
}
