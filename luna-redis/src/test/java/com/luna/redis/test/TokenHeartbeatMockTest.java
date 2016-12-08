package com.luna.redis.test;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * token 心跳模拟
 * 
 * @author liuyc
 *
 */
public class TokenHeartbeatMockTest extends EnviromentSet {

	public static final String ROOM_KEY_PREFIX = "room:id:"; // 房间key前缀
	private static final ExecutorService services = Executors.newFixedThreadPool(200);// 200个线程并发心跳
	private static final int T_NUM = 200;// 并发数心跳
	public static final long HEARTBEAT_RATE = 1 * 1000; // 心跳频率(ms)

	public static final int ROOM_NUM = 100; // 房间数
	public static final int USERS_PER_ROOM = 100; // 每个房间人数

	/**
	 * 心跳业务
	 * 
	 * @param model
	 */
	public static void heartbeat(TokenHeartbeat model) {
		long start = System.currentTimeMillis();
		int roomId = model.getRoomId();
		String token = model.getToken();
		ZSetOperations<String, String> room = redisTemplate.opsForZSet();
		room.add(ROOM_KEY_PREFIX + roomId, token, System.currentTimeMillis());
		System.out.println("heartbeat operation takes " + (System.currentTimeMillis() - start) + " ms ");
	}

	public static void main(String[] args) {
		for (int i = 0; i < T_NUM; i++) {
			services.submit(new Runnable() {
				public void run() {
					while (true) {
						TokenHeartbeat model = new TokenHeartbeat();
						int roomId = new Random().nextInt(ROOM_NUM); // 模拟100个房间
						model.setRoomId(roomId);
						model.setToken("token" + roomId + new Random().nextInt(USERS_PER_ROOM)); // 模拟每个房间10000用户

						heartbeat(model);
						try {
							TimeUnit.MILLISECONDS.sleep(HEARTBEAT_RATE);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
}

/**
 * token 心跳信息
 * 
 * @author liuyc
 *
 */
class TokenHeartbeat implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 房间ID
	 */
	private int roomId;

	/**
	 * token
	 */
	private String token;

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "TokenHeartbeat [roomId=" + roomId + ", token=" + token + "]";
	}

}