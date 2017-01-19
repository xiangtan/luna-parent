package com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl.Fastjson;
import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl.Hessian2;
import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl.Protobuf;


/**
 * 序列化工厂
 * 
 * @author yicai.liu<moon>
 *
 */
public class SerializerFacotry {

    private static final Logger logger = LoggerFactory.getLogger(SerializerFacotry.class);

    /**
     * 根据序列位，获取解码方式
     * 
     * @param id
     *            序列位
     * @return 解码方式
     */
    public static ISerialization create(byte id) {
	switch (id) {
	case Hessian2.ID:
	    logger.info("Serializer:hessian2!");
	    return new Hessian2();
	case Fastjson.ID:
	    logger.info("Serializer:json!");
	    return new Fastjson();
	case Protobuf.ID:
	    logger.info("Serializer:protobuf!");
	    return new Protobuf();
	default:
	    logger.error("Unsupported Serialize ID!");
	    return null;
	}
    }

    /**
     * 从配置信息获取编码方式
     * 
     * @param type
     *            序列化方式
     * @return 解码方式
     */
    public static ISerialization create(String type) {
	byte id = 0;
	if (SerializerType.HESSIAN2.getCode().equals(type)) {
	    id = Hessian2.ID;
	} else if (SerializerType.FASTJSON.getCode().equals(type)) {
	    id = Fastjson.ID;
	} else if (SerializerType.PROTOBUF.getCode().equals(type)) {
	    id = Protobuf.ID;
	} else {
	    logger.error("Unsupported Serialize Type!");
	    return null;
	}
	return create(id);
    }

}
