package com.luna.redis.test;

public class DelTest extends EnviromentSet {

	public static void main(String[] args) {
		String keyPattern = "room:id:*";
		del(keyPattern);
	}

	private static void del(String keyPattern) {
		redisTemplate.delete(redisTemplate.keys(keyPattern));
	}
}
