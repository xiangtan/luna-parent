package com.luna.redis.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * 房间点数
 * 
 * @author liuyc
 *
 */
public class RoomUserCountTest extends EnviromentSet {

	private static final int T_NUM = 1000;// 模拟用户登陆数
	public static final String ROOM_KEY_PREFIX = "room:id:"; // 房间key前缀
	private static final ExecutorService services = Executors.newFixedThreadPool(T_NUM);// 200个线程并发心跳
	protected static final long PER_USER_OPS_RATE = 100;// 用户操作频率(ms)
	private static final long HEARTBEAT_EXPIRE = 30 * 60 * 1000;// 心跳超时(ms)

	/**
	 * 计算房间当前点数
	 * 
	 * @param roomId
	 * @return
	 */
	private static long countUser(int roomId) {
	return 0;
	}

	public static void main(String[] args) {
		ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
		//valueOps.set("token:count:in:liveroom:id:2","0");
valueOps.increment("token:count:in:liveroom:id:3",-190);
	//long result =(long) valueOps.get("token:count:in:liveroom:id:2");


		System.out.println(getIncrValue("token:count:in:liveroom:id:1"));
		
		
		/*
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
	*/}
	
	public static long getIncrValue(final String key) {  
	      
	    return redisTemplate.execute(new RedisCallback<Long>() {  
	        @Override  
	        public Long doInRedis(RedisConnection connection) throws DataAccessException {  
	            RedisSerializer<String> serializer=redisTemplate.getStringSerializer();  
	            byte[] rowkey=serializer.serialize(key);  
	            byte[] rowval=connection.get(rowkey);  
	            try {  
	                String val=serializer.deserialize(rowval);  
	                return Long.parseLong(val);  
	            } catch (Exception e) {  
	                return 0L;  
	            }  
	        }  
	    });  
	}
	 
}
