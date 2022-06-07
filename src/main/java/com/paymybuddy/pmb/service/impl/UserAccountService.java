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

/**
 * 
 * This class realize all the operation on UserAccount.
 * 
 * @author jean-noel.chambe
 * 
 */
@Service
public class UserAccountService implements IUserAccountService {

	public static final Logger logger = LogManager.getLogger(UserAccountService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private IUserAccountRepository userAccountRepository;

	@Autowired
	private ITransacRepository transacRepository;

	/*
	 * This method gives a list of all UserAccount
	 * 
	 * @return A list of UserAccount
	 * 
	 */
	public List<UserAccount> findAllUserAccounts() {
		return userAccountRepository.findAll();
	}

	/*
	 * This method gives a UserAccount in according to loginMail
	 * 
	 * @param A String parameter as loginMail
	 * 
	 * @return A UserAccount
	 * 
	 */
	public UserAccount findByLoginMail(String user) {
		return userAccountRepository.findByLoginMail(user);
	}

	/*
	 * This method finds a UserAccount in according to id
	 * 
	 * @param A Long parameter as id
	 * 
	 * @return A optional UserAccount
	 * 
	 */
	public Optional<UserAccount> findById(Long id) {
		UserAccount ua1 = new UserAccount(1L, "jojo@gmail.com", "jojo", "Max", "Jacob", 50D);

		if (id <= 0L) {
			return Optional.of(ua1);
		}

		return userAccountRepository.findById(id);
	}

	/*
	 * This method saves a UserAccount
	 * 
	 * @param A UserAccount parameter as userAccount
	 * 
	 * @return A UserAccount
	 * 
	 */
	@Transactional
	public UserAccount saveUserAccount(UserAccount userAccount) {

		// si loginMail existe Alors retourner null
		if (Boolean.TRUE.equals(userAccountRepository.existsByLoginMail(userAccount.getLoginMail()))) {
			return null;
		}
		// Si userAccount n'existe pas Alors le sauvegarder en BDD
		return userAccountRepository.save(userAccount);
	}

	/*
	 * This method adds some connection to a UserAccount
	 * 
	 * @param A UserAccount parameter as sender
	 * 
	 * @param A Long parameter as idOfReceiver
	 * 
	 * @return A UserAccount
	 * 
	 */
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

	/*
	 * This method retrieves some connections from a UserAccount
	 * 
	 * @param A UserAccount parameter as sender
	 * 
	 * @return A list of UserAccount
	 * 
	 */
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

	/*
	 * This method transfers money from a UserAccount to a another UserAccount
	 * 
	 * @param A String parameter as loginMail of sender
	 * 
	 * @param A String parameter as receiverMail
	 * 
	 * @param A String parameter as description
	 * 
	 * @param A Double parameter as amount
	 * 
	 * @return A Transac
	 * 
	 */
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

	/*
	 * This method computes new sold to BankAccount of admin
	 * 
	 * @param A Double parameter as soldSender
	 * 
	 * @param A Double parameter as amount
	 * 
	 * @param A Double parameter as littleAmount (discount)
	 * 
	 * @return A new sold in Double
	 * 
	 */
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

	/*
	 * This method deletes a UserAccount
	 * 
	 * @param A UserAccount parameter as userAccount
	 * 
	 */
	@Transactional
	public void deleteUserAccount(UserAccount userAccount) {
		userAccountRepository.delete(userAccount);
	}

	/*
	 * This method deletes some connection to a UserAccount
	 * 
	 * @param A UserAccount parameter as sender
	 * 
	 * @param A String parameter as receiverMail
	 * 
	 * @return A UserAccount
	 * 
	 */
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

	/*
	 * This method registers a new UserAccount
	 * 
	 * @param A Long parameter as id
	 * 
	 * @param A String parameter as loginMail
	 * 
	 * @param A String parameter as psswrd
	 * 
	 * @param A String parameter as firstName
	 * 
	 * @param A String parameter as lastName
	 * 
	 * @param A Double parameter as sold
	 * 
	 * @return A UserAccount
	 * 
	 */
	public UserAccount registerNewUserAccount(Long id, String loginMail, String psswrd, String firstName,
			String lastName, Double sold) throws EmailExistsException {

		Boolean mailExists = userAccountRepository.existsByLoginMail(loginMail);

		if (mailExists) {
			throw new EmailExistsException("There is an account with that email adress: " + loginMail);
		}
		UserAccount registration = new UserAccount(null, loginMail, passwordEncoder.encode(psswrd), firstName, lastName,
				sold);

		return userAccountRepository.save(registration);
	}
}
