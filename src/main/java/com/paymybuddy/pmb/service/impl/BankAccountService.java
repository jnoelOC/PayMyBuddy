package com.paymybuddy.pmb.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.BankAccount;
import com.paymybuddy.pmb.model.UserAccount;
import com.paymybuddy.pmb.repository.IBankAccountRepository;
import com.paymybuddy.pmb.repository.IUserAccountRepository;

/**
 * 
 * This class realize all the operation on BankAccount.
 * 
 * @author jean-noel.chambe
 * 
 */

@Service
public class BankAccountService {
	public static final Logger logger = LogManager.getLogger(BankAccountService.class);

	@Autowired
	private IBankAccountRepository bankAccountRepository;

	@Autowired
	private IUserAccountRepository userAccountRepository;

	/*
	 * This method gives a list of all BankAccount
	 * 
	 * @return a list of BankAccount
	 * 
	 */
	public List<BankAccount> findAllBankAccounts() {
		return bankAccountRepository.findAll();
	}

	/*
	 * This method gives a BankAccount in according to a loginMail
	 * 
	 * @param A string parameter as loginMail
	 * 
	 * @return a BankAccount
	 * 
	 */
	public BankAccount findByLoginMail(String user) {
		return bankAccountRepository.findByLoginMail(user);
	}

	/*
	 * This method add a sold to a BankAccount for a sender
	 * 
	 * @param A Integer parameter as sold
	 * 
	 * @param A UserAccount parameter as sender
	 * 
	 * @param A BankAccount parameter as bankAccount
	 * 
	 */
	public void addSold(BankAccount bankAccount, UserAccount sender, Integer sold) {
		try {
			if (!bankAccount.getIban().isEmpty()) {
				if ((sold > 0) && (sender.getLoginMail().equalsIgnoreCase(bankAccount.getLoginMail()))) {
					sender.setSolde(sender.getSolde() + sold);
					userAccountRepository.save(sender);
				}
			} else {
				logger.error("Dans addSold : Aucun RIB.");
			}
		} catch (Exception ex) {
			logger.error("Error dans addSold : " + ex.getMessage());
		}
	}

	/*
	 * This method substract a sold to a BankAccount for a sender
	 * 
	 * @param A Integer parameter as sold
	 * 
	 * @param A UserAccount parameter as sender
	 * 
	 * @param A BankAccount parameter as bankAccount
	 * 
	 */
	public void substractSold(BankAccount bankAccount, UserAccount sender, Integer sold) {
		try {
			if (!bankAccount.getIban().isEmpty()) {
				if ((sender.getSolde() >= sold)
						&& (sender.getLoginMail().equalsIgnoreCase(bankAccount.getLoginMail()))) {
					sender.setSolde(sender.getSolde() - sold);
					userAccountRepository.save(sender);
				}
			} else {
				logger.error("Dans substractSold : Aucun RIB.");
			}
		} catch (Exception ex) {
			logger.error("Error dans substractSold : " + ex.getMessage());
		}
	}

	/*
	 * This method saves a BankAccount
	 * 
	 * @param A BankAccount parameter as bankAccount
	 * 
	 * @return A BankAccount
	 * 
	 */
	@Transactional
	public BankAccount saveBankAccount(BankAccount bankAccount) {

		return bankAccountRepository.save(bankAccount);
	}

	/*
	 * This method deletes a BankAccount
	 * 
	 * @param A BankAccount parameter as bankAccount
	 * 
	 */
	@Transactional
	public void deleteBankAccount(BankAccount bankAccount) {
		bankAccountRepository.delete(bankAccount);
	}

	/*
	 * This method registers a new BankAccount
	 * 
	 * @param A Long parameter as id
	 * 
	 * @param A String parameter as bankName
	 * 
	 * @param A String parameter as iban
	 * 
	 * @param A String parameter as bic
	 * 
	 * @param A String parameter as loginMail
	 * 
	 * @return A BankAccount
	 * 
	 */
	public BankAccount registerNewBank(Long id, String bankName, String iban, String bic, String loginMail)
			throws IbanExistsException {

		BankAccount result = null;
		Boolean ibanExists = bankAccountRepository.existsByIban(iban);

		if (Boolean.TRUE.equals(ibanExists)) {
			throw new IbanExistsException("This bank account already exists with that iban: " + iban);
		}

		if (!iban.isEmpty()) {
			BankAccount registration = new BankAccount(null, bankName, iban, bic, loginMail);
			result = bankAccountRepository.save(registration);
		}

		return result;
	}
}
