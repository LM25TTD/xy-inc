package com.lm25ttd.xyinc.service.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.lm25ttd.xyinc.model.Account;

/**
 * A wrapper used be SpringSecurity to keep information about users.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public class AccountDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Account account;
	private Collection<GrantedAuthority> authorities;

	public AccountDetails(Account account, Collection<GrantedAuthority> authorities) {
		this.account = account;
		this.authorities = authorities;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return account.getPassword();
	}

	@Override
	public String getUsername() {
		return account.getLogin();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
