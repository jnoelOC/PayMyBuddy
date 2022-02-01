package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CNX_PK")
	private Long idCnxPK;
	@Column(name = "RECEIVER")
	private String receiver;
	@Column(name = "GIVER")
	private String giver;

	@Column(name = "LOGIN_MAIL_FK")
	private String loginMailFK;

	public Connection(Long idCnxPK, String receiver, String giver, String loginMailFK) {
		this.idCnxPK = idCnxPK;
		this.receiver = receiver;
		this.giver = giver;
		this.loginMailFK = loginMailFK;
	}

	public Connection() {
	}

	public Long getIdCnxPK() {
		return idCnxPK;
	}

	public void setIdCnxPK(Long idCnxPK) {
		this.idCnxPK = idCnxPK;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getGiver() {
		return giver;
	}

	public void setGiver(String giver) {
		this.giver = giver;
	}

	public String getLoginMailFK() {
		return loginMailFK;
	}

	public void setLoginMailFK(String loginMailFK) {
		this.loginMailFK = loginMailFK;
	}

}