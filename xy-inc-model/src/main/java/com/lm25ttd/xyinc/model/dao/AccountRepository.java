package com.lm25ttd.xyinc.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lm25ttd.xyinc.model.Account;

/**
 * Data access definitions for user accounts. Spring auto generate methods based
 * on its names (for simple queries).
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 25th, 2016 - lm25ttd - initial version.
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	/**
	 * Retrieves an user account by id.
	 * 
	 * @param id
	 *            The id of the account to be retrieved.
	 * @return The user account of related id or <code>null</code> if not found.
	 */
	public Account findById(Long id);

	/**
	 * Retrieves an user account by login.
	 * 
	 * @param login
	 *            The login of the account to be retrieved.
	 * @return The user account of related login or <code>null</code> if not
	 *         found.
	 */
	public Account findByLogin(String login);
}
