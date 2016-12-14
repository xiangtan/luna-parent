package com.luna.common.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class KryoSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		return null;
	}

}
