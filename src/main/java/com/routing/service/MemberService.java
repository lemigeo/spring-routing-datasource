package com.routing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.routing.datasource.Shard;
import com.routing.shard.domain.Member;
import com.routing.shard.repository.MemberRepository;

@Service
public class MemberService {
	
	@Autowired
	private MemberRepository repo;
	
	@Shard
	public Member create(int accountId, String name) {
		return repo.save(new Member(accountId, name));
	}
	
	@Shard
	public Member getInfo(int accountId) {
		return repo.findByAccountId(accountId);
	}
}