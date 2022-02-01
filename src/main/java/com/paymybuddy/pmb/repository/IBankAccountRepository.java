package com.paymybuddy.pmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.BankAccount;

@Repository
public interface IBankAccountRepository extends JpaRepository<BankAccount, Long> {

}
