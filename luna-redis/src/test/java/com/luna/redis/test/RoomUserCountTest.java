package com.luna.redis.test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * 房间点数
 * 
 * @author liuyc
 *
 */
public class RoomUserCountTest extends EnviromentSet {

	private static final int T_NUM = 200;// 模拟用户登陆数
	public static final String ROOM_KEY_PREFIX = "room:id:"; // 房间key前缀
	private static final ExecutorService services = Executors.newFixedThreadPool(200);// 200个线程并发心跳
	protected static final long PER_USER_OPS_RATE = 3 * 1000;// 用户操作频率(ms)
	private static final long HEARTBEAT_EXPIRE = 60 * 1000;// 心跳超时(ms)

	/**
	 * 计算房间当前点数
	 * 
	 * @param roomId
	 * @return
	 */
	private static long countUser(int roomId) {
		long start = System.currentTimeMillis();
		ZSetOperations<String, String> room = redisTemplate.opsForZSet();
		long point = room.count(ROOM_KEY_PREFIX + roomId, System.currentTimeMillis() - HEARTBEAT_EXPIRE,
				System.currentTimeMillis());
		System.out.println("count room users operation takes " + (System.currentTimeMillis() - start)
				+ " ms , [Room,point]  : [" + ROOM_KEY_PREFIX + roomId + " , " + point + "]");
		return point;
	}

	public static void main(String[] args) {
		for (int i = 0; i < T_NUM; i++) {
			services.submit(new Runnable() {
				public void run() {
					while (true) {
						int roomId = new Random().nextInt(100); // 模拟100个房间
						countUser(roomId);
						try {
							TimeUnit.MILLISECONDS.sleep(PER_USER_OPS_RATE);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			});
		}
	}
}
