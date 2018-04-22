package com.routing.shard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.routing.shard.domain.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Integer> {
	public Member findByAccountId(int accountId);
}
