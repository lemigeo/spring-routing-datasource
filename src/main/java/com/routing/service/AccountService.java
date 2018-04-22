package com.routing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routing.datasource.DatabaseRole;
import com.routing.datasource.MasterSlave;
import com.routing.domain.Account;
import com.routing.repository.AccountRepository;

@Service
public class AccountService {
	
	@Autowired
	private AccountRepository repo;
	
	@MasterSlave
	public Account create(DatabaseRole role) {
		return repo.save(new Account());
	}
	
	@MasterSlave
	public Account getInfo(DatabaseRole role, int id) {
		return repo.findById(id).orElse(null);
	}
}
