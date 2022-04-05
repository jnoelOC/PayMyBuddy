package com.paymybuddy.pmb.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	public UserAccount getById(Long id) {
		return userAccountRepository.getById(id);
	}

	public Optional<UserAccount> findById(Long id) {
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
				if (uaReceiver.getId().equals(idOfReceiver)) {
					connections.add(uaReceiver);
					break;
				}
			}
			sender.setConnections(connections);

		} catch (Exception ex) {
			logger.error("Error dans addConxUserAccount : %s ", ex.getMessage());
		}
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
				connections.addAll(ua.stream().collect(Collectors.toList()));
			}
		}

		return connections;
	}

	@Transactional(rollbackFor = { SQLException.class })
	public Transac transferMoneyUserAccount(String loginMail, Long idReceiverConnection, String description,
			Integer amount) throws SQLException {

		// chercher le sender avec le id parmi tous les useraccount
		UserAccount sender = userAccountRepository.findByLoginMail(loginMail);

		// chercher les connexions du sender
		List<UserAccount> listOfUserAccount = sender.getConnections();
		UserAccount receiver = listOfUserAccount.get(0);
//		UserAccount receiver = userAccountRepository.findById(idReceiverConnection)

		Transac transac = new Transac(null, "description", 0, "senderMail", "receiverMail");
		Integer soldeRec = 0;
		Integer soldeSen = 0;

		transac.setDescription(description);
		transac.setGiver(sender.getLoginMail());
		transac.setReceiver(receiver.getLoginMail());

		soldeRec = receiver.getSolde() + amount;
		receiver.setSolde(soldeRec);
		userAccountRepository.save(receiver);

		soldeSen = sender.getSolde() - amount;
		if (soldeSen > 0) {
			sender.setSolde(soldeSen);
			userAccountRepository.save(sender);

		} else {
			throw new SQLException("Throwing exception for 'saving' rollback");
		}

		return transacRepository.save(transac);
	}

	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

	public UserAccount registerNewUserAccount(Long id, String loginMail, String psswrd, String firstName,
			String lastName, Integer sold) throws EmailExistsException {

		Boolean mailExists = userAccountRepository.existsByLoginMail(loginMail);

		if (Boolean.TRUE.equals(mailExists)) {
			throw new EmailExistsException("There is an account with that email adress: " + loginMail);
		}
		UserAccount registration = new UserAccount(null, loginMail, passwordEncoder.encode(psswrd), firstName, lastName,
				sold);

		return userAccountRepository.save(registration);
	}
}
