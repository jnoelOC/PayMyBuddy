package com.paymybuddy.pmb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.ITransacRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;

@Service
public class UserAccountService implements IUserAccountService {

	public static final Logger logger = LogManager.getLogger(UserAccountService.class);

	@Autowired
	private IUserAccountRepository userAccountRepository;

	@Autowired
	private ITransacRepository transacRepository;

	public List<UserAccount> findAllUserAccounts() {
		return userAccountRepository.findAll();
	}

	@Transactional
	public UserAccount saveUserAccount(UserAccount userAccount) {
		return userAccountRepository.save(userAccount);
	}

	@Transactional
	public UserAccount addConxUserAccount(UserAccount sender, Long idOfReceiver) {

		List<UserAccount> connections = new ArrayList<>();

		// remplir la liste des connexions du sender
		List<UserAccount> luaOfSender = retrieveConxUserAccount(sender);
		for (UserAccount ua : luaOfSender) {
			connections.add(ua);
		}

		// Trouver le receiver par son id
		List<UserAccount> lua = findAllUserAccounts();
		for (UserAccount uaReceiver : lua) {
			if (uaReceiver.getId().equals(idOfReceiver)) {
				connections.add(uaReceiver);
				break;
			}
		}

		sender.setConnections(connections);
		return userAccountRepository.save(sender);
	}

	@Transactional
	public List<UserAccount> retrieveConxUserAccount(UserAccount sender) {

		Pageable page = null;
		Page<Long> pageOfLg = null;

		List<UserAccount> connections = new ArrayList<>();
		List<Long> listOfLg = null;
		Long lg = 0L;
		// pageOfLg = userAccountRepository.chercherConnexions(sender.getIdPK(), page);
		listOfLg = userAccountRepository.chercherConnexions(sender.getId());

//		List<Long> listOfLg = pageOfLg.getContent();
		for (Long lng : listOfLg) {

			Optional<UserAccount> ua = userAccountRepository.findById(lng);
			if (ua.isPresent()) {

				// connections = ua.stream().collect(Collectors.toList());
				connections.addAll(ua.stream().collect(Collectors.toList()));
			}
		}

		return connections;
	}

	@Transactional
	public Transac transferMoneyUserAccount(UserAccount sender) {
		List<UserAccount> listOfUserAccount = sender.getConnections();
		UserAccount receiver = listOfUserAccount.get(0);

		Transac transac = new Transac(null, "description", 0, "senderMail", "receiverMail");
		Integer amount = 5;
		Integer soldeRec = 0;
		Integer soldeSen = 0;
		String description = "musée";
		transac.setDescription(description);
		transac.setGiver(sender.getLoginMail());
		transac.setReceiver(receiver.getLoginMail());

		soldeRec = receiver.getSolde() + amount;
		receiver.setSolde(soldeRec);
		userAccountRepository.save(receiver);

		soldeSen = sender.getSolde() - amount;
		sender.setSolde(soldeSen);
		userAccountRepository.save(sender);

		return transacRepository.save(transac);
	}

	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

}
