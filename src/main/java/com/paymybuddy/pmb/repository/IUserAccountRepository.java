package com.paymybuddy.pmb.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.UserAccount;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByLoginMail(String loginMail);

	Optional<UserAccount> findById(Long id);

	Boolean existsByLoginMail(String loginMail);

	// pour des requetes perso
	// @Query
	// public BankAccount getUserByIban(String iban);

	UserAccount getById(Long id);

	@Query(nativeQuery = true, value = "SELECT connection_id FROM connection c WHERE c.id = :x")
	public List<Long> chercherConnexions(@Param("x") Long idConnx);

}
