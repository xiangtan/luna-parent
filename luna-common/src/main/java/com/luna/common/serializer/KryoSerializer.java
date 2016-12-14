package com.luna.common.serializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.ibatis.cache.CacheException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

// http://nettm.blog.51cto.com/4841905/1702453
public class KryoSerializer implements RedisSerializer<Object>
{
	private static Kryo kryo = new Kryo();

	static
	{
		kryo.setRegistrationRequired(false);
		kryo.setMaxDepth(20);
	}

	@Override
	public byte[] serialize(Object obj) throws SerializationException
	{

		ByteArrayOutputStream out = null;
		Output output = null;
		try
		{
			out = new ByteArrayOutputStream();
			output = new Output(out);
			kryo.writeClassAndObject(output, obj);
			return output.toBytes();
		}
		catch (Exception e)
		{
			throw new CacheException(e);
		}
		finally
		{
			if (null != out)
			{
				try
				{
					out.close();
					out = null;
				}
				catch (IOException e)
				{
				}
			}
			if (null != output)
			{
				output.close();
				output = null;
			}
		}
	}

	@Override
	public Object deserialize(byte[] bytes) throws SerializationException
	{
		if (null == bytes)
		{
			return null;
		}
		Input input = null;
		try
		{
			input = new Input(bytes);
			return kryo.readClassAndObject(input);
		}
		catch (Exception e)
		{
			throw new CacheException(e);
		}
		finally
		{
			if (null != input)
			{
				input.close();
				input = null;
			}
		}
	}

}
