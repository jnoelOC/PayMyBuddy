package com.paymybuddy.pmb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_account")
public class UserAccount {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_UA_PK", nullable = false, unique = true)
	private Long idPK;
	@Column(name = "LOGIN_MAIL", length = 50)
	private String loginMail;
	@Column(name = "PSSWRD", length = 50)
	private String psswrd;
	@Column(name = "FIRSTNAME", length = 30)
	private String firstName;
	@Column(name = "LASTNAME", length = 30)
	private String lastName;
	@Column(name = "SOLDE")
	private Integer solde;

	@OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	public List<Transac> transacs = new ArrayList<>();

	// @ManyToMany(fetch = FetchType.LAZY)
	// @JoinTable(name = "toto", joinColumns = @JoinColumn(name = "ID_UA_PK"),
	// inverseJoinColumns = @JoinColumn(name = "connection_id"))
	// public List<UserAccount> connections = new ArrayList<>();

	@OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
	public List<Connection> connections = new ArrayList<>();

	public UserAccount() {
	}

	public UserAccount(Long idPK, String loginMail, String psswrd, String firstName, String lastName, Integer solde,
			List<Transac> transacs, List<Connection> connections) {
		this.idPK = idPK;
		this.loginMail = loginMail;
		this.psswrd = psswrd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.solde = solde;
		this.transacs = transacs;
		this.connections = connections;
	}

	public List<Transac> getTransacs() {
		return transacs;
	}

	public void setTransacs(List<Transac> transacs) {
		this.transacs = transacs;
	}

	public List<Connection> getConnections() {
		return connections;
	}

	public void setConnections(List<Connection> connections) {
		this.connections = connections;
	}

	public Long getIdPK() {
		return idPK;
	}

	public void setIdPK(Long idPK) {
		this.idPK = idPK;
	}

	public String getLoginMail() {
		return loginMail;
	}

	public void setLoginMail(String loginMail) {
		this.loginMail = loginMail;
	}

	public String getPsswrd() {
		return psswrd;
	}

	public void setPsswrd(String psswrd) {
		this.psswrd = psswrd;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Integer getSolde() {
		return solde;
	}

	public void setSolde(Integer solde) {
		this.solde = solde;
	}

}
