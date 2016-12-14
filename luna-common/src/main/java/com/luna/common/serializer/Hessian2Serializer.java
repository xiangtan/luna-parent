package com.luna.common.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;

/**
 * 跨语言
 * 
 * @author liuyc
 *
 */
public class Hessian2Serializer implements RedisSerializer<Object> {

	@Override
	public byte[] serialize(Object obj) throws SerializationException {
		if (obj == null)
			throw new NullPointerException();

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianOutput ho = new HessianOutput(os);
		try {
			ho.writeObject(obj);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return os.toByteArray();
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException {
		if (bytes == null)
			throw new NullPointerException();

		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		HessianInput hi = new HessianInput(is);
		try {
			return hi.readObject();
		} catch (IOException e) {
			e.printStackTrace();
			// TODO log
			return null;
		}
	}

}
