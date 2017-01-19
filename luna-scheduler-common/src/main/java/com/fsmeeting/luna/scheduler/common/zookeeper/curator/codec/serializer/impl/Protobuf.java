package com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl;

import java.io.IOException;

import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.ISerialization;


/**
 * 
 * @author yicai.liu
 * @version
 * @date 2017年1月14日 上午11:49:42
 */
public class Protobuf implements ISerialization {
    public static final byte ID = 0x02;

    @Override
    public byte getContentTypeId() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public byte[] serialize(Object obj) throws IOException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Object deserialize(byte[] bytes) throws IOException {
	// TODO Auto-generated method stub
	return null;
    }

}
