package com.fsmeeting.luna.load.model;

import java.util.HashMap;

public class IP {

	/*
	 * <K,V>/<IP,负载权值>
	 */
	public static HashMap<String, Integer> serverWeightMap = new HashMap<String, Integer>();
	static {
		serverWeightMap.put("192.168.1.100", 1);
		serverWeightMap.put("192.168.1.101", 2);
		serverWeightMap.put("192.168.1.102", 1);
		serverWeightMap.put("192.168.1.103", 4);
		serverWeightMap.put("192.168.1.104", 3);
		serverWeightMap.put("192.168.1.105", 7);
		serverWeightMap.put("192.168.1.106", 10);
		serverWeightMap.put("192.168.1.107", 5);
	}
}
