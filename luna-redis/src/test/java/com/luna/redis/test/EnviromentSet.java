package com.luna.redis.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;

@SuppressWarnings({ "unchecked" })
public class EnviromentSet {
	protected static ApplicationContext context;
	protected static RedisTemplate<String, String> redisTemplate;
	static {
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
		redisTemplate = (RedisTemplate<String, String>) context.getBean("redisTemplate");
	}
}
