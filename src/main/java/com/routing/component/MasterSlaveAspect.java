package com.routing.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.routing.datasource.DatabaseRole;
import com.routing.datasource.MasterSlaveContextHolder;

@Aspect
@Component
public class MasterSlaveAspect {
	
	@Around("@annotation(com.routing.datasource.MasterSlave) && execution(public * *(..))")
	public Object routing(ProceedingJoinPoint joinPoint) throws Throwable {
		Object obj;
		DatabaseRole role = (DatabaseRole) joinPoint.getArgs()[0];
		if(!role.equals(DatabaseRole.Master)) {
			MasterSlaveContextHolder.set(role);
		}
		obj = joinPoint.proceed();
		MasterSlaveContextHolder.clear();
		return obj;
	}
}