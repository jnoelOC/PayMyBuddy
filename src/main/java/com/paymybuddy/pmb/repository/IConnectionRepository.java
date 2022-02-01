package com.paymybuddy.pmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.Connection;

@Repository
public interface IConnectionRepository extends JpaRepository<Connection, Long> {

}
