package com.paymybuddy.pmb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "connection")
public class Connection {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_CNX_PK", nullable = false, unique = true)
	private Long idCnxPK;
	@Column(name = "RECEIVER", length = 50)
	private String receiver;
	@Column(name = "GIVER", length = 50)
	private String giver;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ID_UA_FK", updatable = false, insertable = false)
	private UserAccount userAccount;

	public Connection(Long idCnxPK, String receiver, String giver, UserAccount userAccount) {
		this.idCnxPK = idCnxPK;
		this.receiver = receiver;
		this.giver = giver;
		this.userAccount = userAccount;
	}

	public Connection() {
	}

	/*
	 * public UserAccount getUserAccount() { return userAccount; }
	 * 
	 * public void setUserAccount(UserAccount userAccount) { this.userAccount =
	 * userAccount; }
	 */

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

}