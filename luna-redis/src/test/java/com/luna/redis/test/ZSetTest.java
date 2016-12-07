package com.luna.redis.test;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

/**
 * @author yicai.liu
 * @version
 * @date 2016年12月7日 下午9:48:55
 */
public class ZSetTest
{
	public static final String	ROOM_PREFIX		= "room:id:";
	public static final String	TOKEN_PREFIX	= "token:id:";
	public static final int		SCORE_START		= 10000;

	public static void main(String[] args) throws InterruptedException
	{
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

		RedisTemplate<String, String> redisTemplate = (RedisTemplate) context.getBean("redisTemplate");
		ZSetOperations<String, String> rooms = redisTemplate.opsForZSet();

		// add(rooms);
		System.out.println(rooms.size(ROOM_PREFIX + 0));
		get(rooms);
		
	}

	private static void add(ZSetOperations<String, String> rooms)
	{
		long start = System.currentTimeMillis();
		for (int i = 0; i <= 10000; i++)
		{
			rooms.add(ROOM_PREFIX + 0, TOKEN_PREFIX + new Random().nextInt(10000),
					SCORE_START + 10000 * new Random().nextInt(6));
		}
		System.out.println("take:" + (System.currentTimeMillis() - start) + "ms");
	}

	private static void get(ZSetOperations<String, String> rooms)
	{
		long start = System.currentTimeMillis();
		Set<String> set = rooms.rangeByScore(ROOM_PREFIX + 0, SCORE_START, SCORE_START + 10000);
		long count0 = rooms.count(ROOM_PREFIX + 0, SCORE_START + 0, SCORE_START + 10000);
		long count1 = rooms.count(ROOM_PREFIX + 0, SCORE_START + 10001, SCORE_START + 20000);
		long count2 = rooms.count(ROOM_PREFIX + 0, SCORE_START + 20001, SCORE_START + 30000);
		long count3 = rooms.count(ROOM_PREFIX + 0, SCORE_START + 30001, SCORE_START + 40000);
		long count4 = rooms.count(ROOM_PREFIX + 0, SCORE_START + 40001, SCORE_START + 50000);

		System.out.println(set.size() + "," + count0);
		System.out.println(set.size() + "," + count1);
		System.out.println(set.size() + "," + count2);
		System.out.println(set.size() + "," + count3);
		System.out.println(set.size() + "," + count4);
		
		System.out.println("take:" + (System.currentTimeMillis() - start) + "ms");
	}
}
