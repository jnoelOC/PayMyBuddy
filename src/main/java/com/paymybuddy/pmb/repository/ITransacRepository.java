package com.paymybuddy.pmb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.paymybuddy.pmb.model.Transac;

@Repository
public interface ITransacRepository extends JpaRepository<Transac, Long> {

}
