package com.luna.common.serializer;

import java.io.ByteArrayInputStream;

import org.apache.commons.codec.binary.Base64;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
//http://nettm.blog.51cto.com/4841905/1702453
public class KryoSerializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object t) throws SerializationException {

		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.register(clazz, new JavaSerializer());

		ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(obj));
		Input input = new Input(bais);
		return (T) kryo.readClassAndObject(input);
		return null;
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		return null;
	}

}
