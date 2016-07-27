package com.lm25ttd.xyinc.rest.v1.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lm25ttd.xyinc.core.exceptions.AccountLoginException;
import com.lm25ttd.xyinc.model.Account;
import com.lm25ttd.xyinc.rest.v1.dto.AccountDTO;
import com.lm25ttd.xyinc.service.AccountService;

/**
 * Implementation of REST API Contract related to Accounts.
 *
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
@RestController
@RequestMapping(value = "/v1/accounts")
public class AccountResource {

	@Autowired
	private AccountService accountService;

	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public AccountDTO login() throws AccountLoginException {
		Account account = accountService.getLoggedInAccount();
		if (account == null) {
			throw new AccountLoginException();
		}
		return new AccountDTO(account);
	}
}