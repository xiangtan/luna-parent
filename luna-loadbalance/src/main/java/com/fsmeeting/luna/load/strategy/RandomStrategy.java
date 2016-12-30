package com.fsmeeting.luna.load.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.fsmeeting.luna.load.model.IP;

/**
 * <pre>
 * 1 优点
 * 
 * 2 缺点
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
public class RandomStrategy implements ILoadStrategy {

	@Override
	public String getServer() {
		// 重建一个Map，避免服务器的上下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(IP.serverWeightMap);

		// 取得Ip地址List
		Set<String> keySet = serverMap.keySet();
		ArrayList<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);

		java.util.Random random = new java.util.Random();
		int randomPos = random.nextInt(keyList.size());

		return keyList.get(randomPos);
	}

}
