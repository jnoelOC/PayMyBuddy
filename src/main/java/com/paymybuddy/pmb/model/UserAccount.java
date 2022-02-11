package com.paymybuddy.pmb.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "user_account")
@JsonIgnoreProperties(value = { "connections" })
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

//	@OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY)
//	public List<Transac> transacs = new ArrayList<>();

//	@ManyToMany(fetch = FetchType.EAGER)
//	@Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.PERSIST })
	@ManyToMany(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.MERGE, CascadeType.PERSIST })
	@JoinTable(name = "connection", joinColumns = @JoinColumn(name = "ID_UA_PK"), inverseJoinColumns = @JoinColumn(name = "connection_id"))
	public List<UserAccount> connections = new ArrayList<>();

//	@OneToMany(mappedBy = "userAccount", fetch = FetchType.LAZY, cascade = CascadeType.MERGE, orphanRemoval = true)
//	public List<Connection> connections = new ArrayList<>();

	public UserAccount() {
	}

	public UserAccount(Long idPK, String loginMail, String psswrd, String firstName, String lastName, Integer solde) {
		this.idPK = idPK;
		this.loginMail = loginMail;
		this.psswrd = psswrd;
		this.firstName = firstName;
		this.lastName = lastName;
		this.solde = solde;
	}

//	public List<Transac> getTransacs() {
//		return transacs;
//	}
//
//	public void setTransacs(List<Transac> transacs) {
//		this.transacs = transacs;
//	}

	public List<UserAccount> getConnections() {
		return connections;
	}

	public void setConnections(List<UserAccount> connections) {
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
