package com.paymybuddy.pmb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.service.impl.EmailExistsException;

public interface IUserAccountService {
	public List<UserAccount> findAllUserAccounts();

//	public UserAccount getById(Long id);

	public Optional<UserAccount> findById(Long id);

	public UserAccount findByLoginMail(String user);

	public UserAccount saveUserAccount(UserAccount userAccount);

	public UserAccount addConxUserAccount(UserAccount sender, Long idOfReceiver);

	public List<UserAccount> retrieveConxUserAccount(UserAccount sender);

	public Transac transferMoneyUserAccount(String loginMail, String receiverConnection, String description,
			Double amount) throws SQLException;

	public void deleteUserAccount(UserAccount userAccount);

	public UserAccount registerNewUserAccount(Long id, String loginMail, String psswrd, String firstName,
			String lastName, Double sold) throws EmailExistsException;
}