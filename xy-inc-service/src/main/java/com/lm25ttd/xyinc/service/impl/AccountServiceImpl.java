package com.lm25ttd.xyinc.service.impl;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;
import com.lm25ttd.xyinc.core.exceptions.XYIncUnexpectedException;
import com.lm25ttd.xyinc.core.i18n.I18nService;
import com.lm25ttd.xyinc.model.Account;
import com.lm25ttd.xyinc.model.dao.AccountRepository;
import com.lm25ttd.xyinc.service.AccountService;
import com.lm25ttd.xyinc.service.security.AccountDetails;

/**
 * Implementation of service of Account entity.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@Service
@Transactional("xyincTransactionManager")
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public Account getAccount(Long id) throws EntityNotFoundException {
		Account account = this.accountRepository.findOne(id);
		if (account == null) {
			throw new EntityNotFoundException(id, Account.class);
		}
		return account;
	}

	@Override
	public Account getAccountByLogin(String login) throws EntityNotFoundException {
		Account account = this.accountRepository.findByLogin(login);
		if (account == null) {
			throw new EntityNotFoundException(login, Account.class);
		}
		return account;
	}

	@Override
	public Account getLoggedInAccount() {
		Account user = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			// principal can be "anonymousUser" (String)
			if (principal instanceof AccountDetails) {
				AccountDetails userDetails = (AccountDetails) principal;
				user = userDetails.getAccount();
			}
		}
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			// Lookup the account by login
			Account account = this.accountRepository.findByLogin(username);
			if (account == null) {
				throw new EntityNotFoundException(username, Account.class);
			}
			// Set roles
			String role = account.getRole().toString();
			Collection<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(1);
			authorities.add(new SimpleGrantedAuthority(role));

			// Return an object adapted to string
			return new AccountDetails(account, authorities);
		} catch (EntityNotFoundException e) {
			String msg = I18nService.translate("exception.account.not.found");
			throw new UsernameNotFoundException(msg);
		} catch (Throwable t) {
			throw new XYIncUnexpectedException(t);
		}
	}

	@Override
	public Boolean hasCurrentAccount() {
		return this.getLoggedInAccount() != null;
	}
}
