package com.paymybuddy.pmb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.UserAccount;

@Repository
public interface IUserAccountRepository extends JpaRepository<UserAccount, Long> {

	UserAccount findByLoginMail(String loginMail);

	Boolean existsByLoginMail(String loginMail);

	// pour des requetes perso
	// @Query
	// public BankAccount getUserByIban(String iban);

//	List<UserAccount> findByIdPK(Long idPK);
//	List<UserAccount> findByLoginMail(String loginMail);

//	@Query(nativeQuery = true, value = "SELECT * FROM connection c, useraccount u WHERE :x = c.id_ua_pk")
//	@Query(nativeQuery = true, value = "SELECT * FROM connection c")
	@Query(nativeQuery = true, value = "SELECT connection_id FROM connection c WHERE c.id = :x")
	public List<Long> chercherConnexions(@Param("x") Long idConnx);
//	public Page<Long> chercherConnexions(@Param("x") Long idConnx, Pageable pageable);
//	List<UserAccount> findByConnections(List<UserAccount> connections);

//	public Page<Connection> findByConnectionsWithPagination(String nombreParPage, Pageable pageable);

}
