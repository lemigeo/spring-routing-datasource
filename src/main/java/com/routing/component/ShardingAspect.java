package com.routing.component;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.routing.datasource.ShardContextHolder;

@Aspect
@Component
public class ShardingAspect {
	
	@Around("@annotation(com.routing.datasource.Shard) && execution(public * *(..))")
	public Object routing(ProceedingJoinPoint joinPoint) throws Throwable {
		Object obj;
		int disfactor = Integer.valueOf(joinPoint.getArgs()[0].toString()) % 2;
		ShardContextHolder.set(String.valueOf(disfactor));
		obj = joinPoint.proceed();
		ShardContextHolder.clear();
		return obj;
	}
}
