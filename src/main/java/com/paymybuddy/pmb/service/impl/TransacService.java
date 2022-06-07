package com.paymybuddy.pmb.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paymybuddy.pmb.model.Transac;
import com.paymybuddy.pmb.repository.ITransacRepository;

/**
 * 
 * This class realize all the operation on Transac.
 * 
 * @author jean-noel.chambe
 * 
 */
@Service
public class TransacService {
	public static final Logger logger = LogManager.getLogger(TransacService.class);

	@Autowired
	private ITransacRepository transacRepository;

	/*
	 * This method gives a list of all Transac
	 * 
	 * @return a list of Transac
	 * 
	 */
	public List<Transac> findAllTransactions() {
		return transacRepository.findAll();
	}

	/*
	 * This method gives a list of all Transac in according to a loginMail receiver
	 *
	 * @param A String parameter as loginMail
	 * 
	 * @return a list of Transac
	 * 
	 */
	public List<Transac> findAllTransactionsByReceiver(String user) {
		List<Transac> allTrxOfReceiver = new ArrayList<>();
		List<Transac> allTrx = transacRepository.findAll();
		for (Transac oneTrx : allTrx) {
			if (oneTrx.getReceiver().equalsIgnoreCase(user)) {
				allTrxOfReceiver.add(oneTrx);
			}
		}
		return allTrxOfReceiver;
	}

	/*
	 * This method gives a list of all Transac in according to a loginMail giver
	 *
	 * @param A String parameter as loginMail
	 * 
	 * @return a list of Transac
	 * 
	 */
	public List<Transac> findAllTransactionsByGiver(String user) {
		List<Transac> allTrxOfGiver = new ArrayList<>();
		List<Transac> allTrx = transacRepository.findAll();
		for (Transac trx : allTrx) {
			if (trx.getGiver().equalsIgnoreCase(user)) {
				allTrxOfGiver.add(trx);
			}
		}
		return allTrxOfGiver;
	}

	/*
	 * This method saves a Transac
	 *
	 * @param A Transac parameter as transaction
	 * 
	 * @return a Transac
	 * 
	 */
	@Transactional
	public Transac saveTransac(Transac transaction) {

		return transacRepository.save(transaction);
	}

	/*
	 * This method deletes a Transac
	 *
	 * @param A Transac parameter as transaction
	 * 
	 */
	@Transactional
	public void deleteTransac(Transac transaction) {
		transacRepository.delete(transaction);
	}
}
