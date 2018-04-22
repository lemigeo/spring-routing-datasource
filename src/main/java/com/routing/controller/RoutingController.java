package com.routing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.routing.datasource.DatabaseRole;
import com.routing.domain.Account;
import com.routing.service.AccountService;
import com.routing.service.MemberService;
import com.routing.shard.domain.Member;

@RestController(value="/")
@CrossOrigin
public class RoutingController {

	@Autowired
	private AccountService account;
	
	@Autowired
	private MemberService member; 
	
	private Gson gson;
	
	public RoutingController() {
		gson = new Gson();
	}
	
	@RequestMapping(value="/account/master/{accountId}", method=RequestMethod.GET)
	@ResponseBody
	public String searchMasterAccount(@PathVariable("accountId") int accountId) {
		Account acct = account.getInfo(DatabaseRole.Master, accountId);
		return gson.toJson(acct);
	}
	
	@RequestMapping(value="/account/slave/{accountId}", method=RequestMethod.GET)
	@ResponseBody
	public String searchSlaveAccount(@PathVariable("accountId") int accountId) {
		Account acct = account.getInfo(DatabaseRole.Slave1, accountId);
		return gson.toJson(acct);
	}
	
	@RequestMapping(value="/member/create/{name}", method=RequestMethod.GET)
	@ResponseBody
	public String createMember(@PathVariable("name") String name) {
		account.create(DatabaseRole.Master);
		Account acct = account.create(DatabaseRole.Slave1);
		Member mb = member.create(acct.getId(), name);
		return gson.toJson(mb);
	}
	
	@RequestMapping(value="/member/{accountId}", method=RequestMethod.GET)
	@ResponseBody
	public String searchMember(@PathVariable("accountId") int accountId){
		Member mb = member.getInfo(accountId);
		return gson.toJson(mb);
	}
}