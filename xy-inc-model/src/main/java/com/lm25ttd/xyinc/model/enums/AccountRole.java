package com.lm25ttd.xyinc.model.enums;

/**
 * Identifies the type of users in the system.
 * 
 * @author Leandro Bentes
 * @version 0.0.1 - July 26th, 2016 - lm25ttd - initial version.
 */
public enum AccountRole {
	ADMIN(Names.ADMIN), NON_ADMIN(Names.NON_ADMIN);

	private String name;

	private AccountRole(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public class Names {
		public static final String ADMIN = "ADMIN";
		public static final String NON_ADMIN = "NON_ADMIN";		
	}
}
