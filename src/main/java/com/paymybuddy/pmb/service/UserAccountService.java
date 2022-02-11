package com.paymybuddy.pmb.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IUserAccountRepository;

@Service
public class UserAccountService implements IUserAccountService {

	public static final Logger logger = LogManager.getLogger(UserAccountService.class);

	@Autowired
	private IUserAccountRepository userAccountRepository;

	public List<UserAccount> findAllUserAccounts() {
		return userAccountRepository.findAll();
	}

	@Transactional
	public UserAccount saveUserAccount(UserAccount userAccount) {
		return userAccountRepository.save(userAccount);
	}

	@Transactional
	public UserAccount addConxUserAccount(UserAccount sender) {

		UserAccount receiver = new UserAccount(null, "cs@gmail.com", "cs", "clement", "sezettre", 80);
		List<UserAccount> listOfConnections = new ArrayList<>();
		listOfConnections.add(receiver);

		sender.setConnections(listOfConnections);

		return userAccountRepository.save(sender);
	}

//	@Transactional
//	public Transac transferMoneyUserAccount(UserAccount sender) {
//		Transac transac;
//		retrieve selected connection from view
//		UserAccount receiver;
//		retrieve amount from view
//		transac = userAccount.TransferMoney
//		return userAccountRepository.save(sender);
//	}

	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

}
