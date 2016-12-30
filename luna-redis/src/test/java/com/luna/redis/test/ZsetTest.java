package com.luna.redis.test;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Random;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * <pre>
 * 1 查找某个元素的分数排名
 * 2 查找分数之间的元素个数
 * 3
 * </pre>
 * 
 * @author liuyc
 *
 */
public class ZsetTest extends EnviromentSet {
	public static final String ROOM_KEY_PREFIX = "test:room:id"; // 房间key前缀

	public static void main(String[] args) {
		ZSetOperations<Object, Object> room = redisTemplate.opsForZSet();
		System.out.println(room.score(ROOM_KEY_PREFIX, room.range(ROOM_KEY_PREFIX, 0, 0).iterator().next()));
		System.out.println(room.size(ROOM_KEY_PREFIX));
		long count = room.removeRangeByScore(ROOM_KEY_PREFIX, 0, 200);
		System.out.println(count);
		System.out.println(room.size(ROOM_KEY_PREFIX));
	}

	public static void add(ZSetOperations<Object, Object> room) {
		// add
		for (int i = 100; i < 200; i++) {
			room.add(ROOM_KEY_PREFIX, i, new Random().nextFloat() + 100);
		}
	}

}
