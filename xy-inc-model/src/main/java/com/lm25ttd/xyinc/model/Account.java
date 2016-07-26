package com.lm25ttd.xyinc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.lm25ttd.xyinc.model.enums.AccountRole;

/**
 * Representes an user account in the system, for AAA definitions.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Entity
@Table(name = "ACCOUNT")
@GenericGenerator(name = "ID", strategy = "native")
public class Account extends Identified<Long> {

	@Column(name = "LOGIN", nullable = false, unique = true)
	private String login = null;

	@Column(name = "PASSWORD", nullable = false)
	private String password = null;

	@Column(name = "ACCOUNT_ROLE", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountRole role = AccountRole.NON_ADMIN;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AccountRole getRole() {
		return role;
	}

	public void setRole(AccountRole role) {
		this.role = role;
	}

}
