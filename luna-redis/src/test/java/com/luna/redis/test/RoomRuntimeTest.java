package com.luna.redis.test;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ValueOperations;

public class RoomRuntimeTest extends EnviromentSet {

	public static final String ROOM_RUNTIME_KEY = "runtime:room:map";

	public static void main(String[] args) {

		ValueOperations< Object, Object> valueOps = redisTemplate.opsForValue();
		valueOps.increment("test",-2);
	}

	public static void getkeys(HashOperations<Object, Object, Object> roomRuntime) {
		// 获取所有的key
		Set<Object> objs = roomRuntime.keys(ROOM_RUNTIME_KEY+"ss");
		for (Object obj : objs) {
			System.out.println(obj);
		}
	}

	public static void put(int roomId, String liveSrvAddr) {
		HashOperations<Object, Object, RoomRuntime> roomRuntime = redisTemplate.opsForHash();
		RoomRuntime runtime = new RoomRuntime();
		runtime.setLiveRoomId(roomId);
		runtime.setLiveSrvAddr(liveSrvAddr);
		roomRuntime.put(ROOM_RUNTIME_KEY, roomId, runtime);
	}

	public static Object get(int roomId) {
		HashOperations<Object, Object, Object> roomRuntime = redisTemplate.opsForHash();
		return roomRuntime.get(ROOM_RUNTIME_KEY, Integer.toString(roomId));
	}
}

class RoomRuntime implements Serializable {
	private static final long serialVersionUID = 1L;

	private int liveRoomId;
	private String liveSrvAddr;
	private Date activeTime = new Date();

	public int getLiveRoomId() {
		return liveRoomId;
	}

	public void setLiveRoomId(int liveRoomId) {
		this.liveRoomId = liveRoomId;
	}

	public String getLiveSrvAddr() {
		return liveSrvAddr;
	}

	public void setLiveSrvAddr(String liveSrvAddr) {
		this.liveSrvAddr = liveSrvAddr;
	}

	public Date getActiveTime() {
		return activeTime;
	}

	public void setActiveTime(Date activeTime) {
		this.activeTime = activeTime;
	}

	@Override
	public String toString() {
		return "RoomRuntime [liveRoomId=" + liveRoomId + ", liveSrvAddr=" + liveSrvAddr + ", activeTime=" + activeTime
				+ "]";
	}

}