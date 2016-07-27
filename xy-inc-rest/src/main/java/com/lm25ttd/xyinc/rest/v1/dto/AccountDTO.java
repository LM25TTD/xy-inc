package com.lm25ttd.xyinc.rest.v1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.lm25ttd.xyinc.model.Account;
import com.lm25ttd.xyinc.model.Identifiable;
import com.lm25ttd.xyinc.model.enums.AccountRole;

/**
 * Data Type Object for Account.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@JsonInclude(Include.NON_NULL)
public class AccountDTO extends GenericDTO<Account> {
	
	/**
	 * Creates an instance of AccountDTO.
	 */
	public AccountDTO() {
		super(new Account());
	}

	/**
	 * Creates an instance of AccountDTO.
	 */
	public AccountDTO(Account account) {
		super(account);
	}

	/**
	 * @see Identifiable#getId()
	 */
	public Long getId() {
		return super.getWrapped().getId();
	}

	/**
	 * @see Identifiable#setId(Long)
	 */
	public void setId(Long id) {
		super.getWrapped().setId(id);
	}
	
	/**
     * The resource URL.
     */
    public String getHref() {
        return "/v1/accounts/" + this.getWrapped().getId();
    }

	/**
	 * @see Account#getLogin()
	 */
	public String getLogin() {
		return this.getWrapped().getLogin();
	}

	/**
	 * @see Account#setLogin(String)
	 */
	public void setLogin(String login) {
		super.getWrapped().setLogin(login);
	}

	/**
	 * @see Account#getRole()
	 */
	public AccountRole getRole() {
		return this.getWrapped().getRole();
	}

	/**
	 * @see Account#setRole(AccountRole)
	 */
	public void setRole(AccountRole role) {
		super.getWrapped().setRole(role);
	}

	/**
	 * @see setPassword(String)
	 */
	public void setPassword(String password) {
		super.getWrapped().setPassword(password);
	}
}
