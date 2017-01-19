package com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer;

import java.io.IOException;

/**
 * 序列化
 * 
 * @author yicai.liu<moon>
 *
 */
public interface ISerialization {
    /**
     * 序列化类型， 建议（1、2、4、8、16）
     * 
     * @return
     */
    byte getContentTypeId();

    /**
     * 序列化(对象——>二进制)
     * 
     * @param obj
     * @return
     * @throws IOException
     */
    byte[] serialize(Object obj) throws IOException;

    /**
     * 反序列化(二进制——>对象)
     * 
     * @param bytes
     * @return
     * @throws IOException
     */
    Object deserialize(byte[] bytes) throws IOException;
}
