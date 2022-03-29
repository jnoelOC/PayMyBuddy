package com.paymybuddy.pmb.service;

import java.sql.SQLException;
import java.util.List;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;

public interface IUserAccountService {
	public List<UserAccount> findAllUserAccounts();

	public UserAccount getById(Long id);

	public UserAccount findByLoginMail(String user);

	public UserAccount saveUserAccount(UserAccount userAccount);

	public UserAccount addConxUserAccount(UserAccount sender, Long idOfReceiver);

	public List<UserAccount> retrieveConxUserAccount(UserAccount sender);

	public Transac transferMoneyUserAccount(Long idConnection, String description, Integer amount) throws SQLException;

	public void deleteUserAccount(UserAccount userAccount);

	public UserAccount registerNewUserAccount(Long id, String loginMail, String psswrd, String firstName,
			String lastName, Integer sold) throws EmailExistsException;
}