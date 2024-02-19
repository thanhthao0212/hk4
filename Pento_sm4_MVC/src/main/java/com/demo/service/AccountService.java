package com.demo.service;

import com.demo.entities.Account;

public interface AccountService {

	public Iterable<Account> findAll();
	public Account find(int id);
	public Account findByEmail(String email);
	
	// CRUD
	public boolean save(Account account);
	public boolean edit(int id);
	public boolean delete(int id);
	
	
}
