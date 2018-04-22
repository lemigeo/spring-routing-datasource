package com.routing.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routing.domain.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {
}
