package com.paymybuddy.pmb.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.ITransacRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;
import com.paymybuddy.pmb.service.IUserAccountService;

@Service
public class UserAccountService implements IUserAccountService {

	public static final Logger logger = LogManager.getLogger(UserAccountService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUserAccountRepository userAccountRepository;

	@Autowired
	private ITransacRepository transacRepository;

	public List<UserAccount> findAllUserAccounts() {
		return userAccountRepository.findAll();
	}

	public UserAccount findByLoginMail(String user) {
		return userAccountRepository.findByLoginMail(user);
	}

//	public UserAccount getById(Long id) {
//		return userAccountRepository.getById(id);
//	}

	public Optional<UserAccount> findById(Long id) {
		UserAccount ua1 = new UserAccount(1L, "jojo@gmail.com", "jojo", "Max", "Jacob", 50D);

		if (id <= 0L) {
			return Optional.of(ua1);
		}

		return userAccountRepository.findById(id);
	}

	@Transactional
	public UserAccount saveUserAccount(UserAccount userAccount) {

		// si loginMail existe Alors retourner null
		if (Boolean.TRUE.equals(userAccountRepository.existsByLoginMail(userAccount.getLoginMail()))) {
			return null;
		}
		// Si userAccount n'existe pas Alors le sauvegarder en BDD
		return userAccountRepository.save(userAccount);
	}

	@Transactional
	public UserAccount addConxUserAccount(UserAccount sender, Long idOfReceiver) {

		List<UserAccount> connections = new ArrayList<>();
		try {
			// remplir la liste des connexions du sender
			List<UserAccount> luaOfSender = retrieveConxUserAccount(sender);
			for (UserAccount ua : luaOfSender) {
				connections.add(ua);
			}

			// Trouver le receiver par son id
			List<UserAccount> lua = findAllUserAccounts();
			for (UserAccount uaReceiver : lua) {
				if (uaReceiver.getId().equals(idOfReceiver) && !connections.contains(uaReceiver)) {
					connections.add(uaReceiver);
					break;
				}
			}
			sender.setConnections(connections);

		} catch (Exception ex) {
			logger.error("Error dans addConxUserAccount : " + ex.getMessage());
		}
		return userAccountRepository.save(sender);
	}

	@Transactional
	public List<UserAccount> retrieveConxUserAccount(UserAccount sender) {

		List<UserAccount> connections = new ArrayList<>();
		List<Long> listOfLg = null;
		try {
			listOfLg = userAccountRepository.chercherConnexions(sender.getId());

			for (Long lng : listOfLg) {

				Optional<UserAccount> ua = userAccountRepository.findById(lng);
				if (ua.isPresent()) {
					// connections.addAll(ua.stream().collect(Collectors.toList()));
					UserAccount ua2 = ua.get();
					connections.add(ua2);
				}
			}
		} catch (Exception e) {
			logger.error("Error dans retrieveConxUserAccount : " + e.getMessage());
		}
		return connections;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public Transac transferMoneyUserAccount(String loginMail, String receiverMail, String description, Double amount)
			throws SQLException {

		// chercher le sender avec le loginMail
		UserAccount sender = userAccountRepository.findByLoginMail(loginMail);

		// chercher le receiver avec le receiverMail
		UserAccount receiver = userAccountRepository.findByLoginMail(receiverMail);

		Transac transac = new Transac(null, "description", 0D, "senderMail", "receiverMail");
		Double soldeRec;

		transac.setDescription(description);
		transac.setGiver(sender.getLoginMail());
		transac.setReceiver(receiver.getLoginMail());
		transac.setAmount(amount);

		soldeRec = receiver.getSolde() + amount;
		receiver.setSolde(soldeRec);
		userAccountRepository.save(receiver);

		// Compute 0.5% to Bank admin for each transaction
		Double littleAmount = 0.005 * amount;

		if (sender.getSolde() > amount && sender.getSolde() > 0) {
			Double newSold = computeNewSoldToBankAdmin(sender.getSolde(), amount, littleAmount);
			sender.setSolde(newSold);
			userAccountRepository.save(sender);

		} else {
			throw new SQLException("Throwing exception for 'saving' rollback. Sender's solde isn't enough.");
		}

		return transacRepository.save(transac);
	}

	@Transactional
	public Double computeNewSoldToBankAdmin(Double soldSender, Double amount, Double littleAmount) {

		try {
			UserAccount admin = userAccountRepository.findByLoginMail("admin@gmail.com");
			admin.setSolde(admin.getSolde() + littleAmount);
			userAccountRepository.save(admin);
		} catch (Exception ex) {
			logger.error("computeNewSoldToBankAdmin : %s ", ex.getMessage());
			ex.printStackTrace();
		}
		return soldSender - amount - littleAmount;
	}

	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

	@Transactional
	public UserAccount deleteConxUserAccount(UserAccount sender, String receiverMail) {

		List<UserAccount> connections = new ArrayList<>();
		try {
			// remplir la liste des connexions du sender
			List<UserAccount> luaOfSender = retrieveConxUserAccount(sender);
			for (UserAccount ua : luaOfSender) {
				connections.add(ua);
			}

			// Trouver le receiver par son mail
			List<UserAccount> lua = findAllUserAccounts();
			for (UserAccount uaReceiver : lua) {
				if (uaReceiver.getLoginMail().equals(receiverMail)) {
					connections.remove(uaReceiver);
					break;
				}
			}
			sender.setConnections(connections);

		} catch (Exception ex) {
			logger.error("Error dans deleteConxUserAccount : " + ex.getMessage());
		}
		return userAccountRepository.save(sender);
	}

	public UserAccount registerNewUserAccount(Long id, String loginMail, String psswrd, String firstName,
			String lastName, Double sold) throws EmailExistsException {

		Boolean mailExists = userAccountRepository.existsByLoginMail(loginMail);

		if (Boolean.TRUE.equals(mailExists)) {
			throw new EmailExistsException("There is an account with that email adress: " + loginMail);
		}
		UserAccount registration = new UserAccount(null, loginMail, passwordEncoder.encode(psswrd), firstName, lastName,
				sold);

		return userAccountRepository.save(registration);
	}
}
