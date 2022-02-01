package com.paymybuddy.pmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.UserAccount;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

	// pour des requetes perso
	// @Query
	// public BankAccount getUserByIban(String iban);

}
