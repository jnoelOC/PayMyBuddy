package com.paymybuddy.pmb.service;

import java.util.List;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;

public interface IUserAccountService {
	public List<UserAccount> findAllUserAccounts();

	public UserAccount saveUserAccount(UserAccount userAccount);

	public UserAccount addConxUserAccount(UserAccount sender, Long idOfReceiver);

	public List<UserAccount> retrieveConxUserAccount(UserAccount sender);

	public Transac transferMoneyUserAccount(UserAccount sender);

	public void deleteUserAccount(UserAccount userAccount);
}