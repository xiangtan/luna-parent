package com.luna.redis.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * Token释放
 * 
 * @author liuyc
 *
 */
public class TokenDeleteTest extends EnviromentSet {
	private static final int T_NUM = 200;// 模拟用户登陆数
	public static final String ROOM_KEY_PREFIX = "room:id:"; // 房间key前缀
	private static final ExecutorService services = Executors.newFixedThreadPool(200);// 10个线程并发心跳

	public static void main(String[] args) {

		for (int i = 0; i < T_NUM; i++) {
			services.submit(new Runnable() {
				public void run() {
					while (true) {
						int roomId = new Random().nextInt(100); // 模拟100个房间
						String token = "token" + roomId + new Random().nextInt(100);
						removeToken(roomId, token);
						try {
							TimeUnit.MILLISECONDS.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}

	}

	/**
	 * 删除某个房间的某个token
	 * 
	 * @param roomId
	 * @param token
	 */
	protected static void removeToken(int roomId, String token) {
		long start = System.currentTimeMillis();
		ZSetOperations<String, String> room = redisTemplate.opsForZSet();
		long tokenCount = room.remove(ROOM_KEY_PREFIX + roomId, token);
		System.out.println("remove " + tokenCount + " tokens operation takes " + (System.currentTimeMillis() - start)
				+ " ms , [Room,Token]  : [" + ROOM_KEY_PREFIX + roomId + " , " + token + "]");

	}

	/**
	 * 删除某个房间的token
	 * 
	 * @param roomId
	 * @param token
	 */
	protected static void removeToken(int roomId) {
		long start = System.currentTimeMillis();
		redisTemplate.delete(ROOM_KEY_PREFIX + roomId);
		System.out.println("remove  room tokens operation takes " + (System.currentTimeMillis() - start)
				+ " ms , Room : [" + ROOM_KEY_PREFIX + roomId + "]");

	}
}
