package com.paymybuddy.pmb.service;

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

//	@Transactional
//	public UserAccount addConxUserAccount(UserAccount sender) {
//		UserAccount receiver;
//		create connection between sender and email from list of friends (receiverEmail)
//		List<Connection> conn = new ArrayList<>();
//		conn.add(new Connection(10L, userAccount));
//		userAccount.setConnections(conn);
//		return userAccountRepository.save(sender);
//	}

//	@Transactional
//	public Transac transferMoneyUserAccount(UserAccount uaSender, UserAccount uaReceiver) {
//		Transac transac;
//		retrieve selected connection from view
//		retrieve amount from view
//		transac = userAccount.TransferMoney
//		return userAccountRepository.save(userAccount);
//	}

	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

}
