package com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.ISerialization;

/**
 * Hessian2 序列化
 * 
 * @author yicai.liu<moon>
 *
 */
public class Hessian2 implements ISerialization {

    public static final byte ID = 0x01;

    @Override
    public byte getContentTypeId() {
	return ID;
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
	if (obj == null)
	    throw new NullPointerException();

	ByteArrayOutputStream os = new ByteArrayOutputStream();
	HessianOutput ho = new HessianOutput(os);
	ho.writeObject(obj);
	return os.toByteArray();
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
	if (bytes == null)
	    throw new NullPointerException();

	ByteArrayInputStream is = new ByteArrayInputStream(bytes);
	HessianInput hi = new HessianInput(is);
	return hi.readObject();
    }

}
