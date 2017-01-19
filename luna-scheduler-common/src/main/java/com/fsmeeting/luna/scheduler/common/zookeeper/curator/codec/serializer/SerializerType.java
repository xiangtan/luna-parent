package com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer;

/**
 * 序列化方式
 * 
 * @author yicai.liu
 * @version
 * @date 2017年1月14日 上午11:42:11
 */
public enum SerializerType {

    /**
     * hessian2
     */
    HESSIAN2("hessian2"),
    /**
     * fastjson
     */
    FASTJSON("fastjson"),
    /**
     * protobuf
     */
    PROTOBUF("protobuf"),
    /**
     * kryo
     */
    KRYO("kryo");

    private String code;

    private SerializerType(String code) {
	this.code = code;
    }

    public String getCode() {
	return code;
    }

}
