package com.lm25ttd.xyinc.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lm25ttd.xyinc.core.exceptions.EntityNotFoundException;
import com.lm25ttd.xyinc.model.Account;

/**
 * Exposed service of Account entity.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public interface AccountService extends UserDetailsService {

	/**
	 * Looks up an account by its unique identifier.
	 *
	 * @param id
	 *            The account identifier.
	 * @return The account.
	 * @throws EntityNotFoundException
	 *             If account does not exist.
	 */
	public Account getAccount(Long id) throws EntityNotFoundException;

	/**
	 * Looks up an account by its unique login.
	 *
	 * @param login
	 *            The account login.
	 * @return The account.
	 * @throws EntityNotFoundException
	 *             If account does not exist.
	 */
	public Account getAccountByLogin(String login) throws EntityNotFoundException;

	/**
	 * Gets the current logged user account.
	 *
	 * @return The account of logged user.
	 */
	public Account getLoggedInAccount();

	/**
	 * @return True if there is a logged in account, false otherwise.
	 */
	public Boolean hasCurrentAccount();
}
