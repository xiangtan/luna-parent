package com.fsmeeting.luna.load.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class RoundRobinStrategy implements ILoadStrategy {

	private static Integer pos = 0;

	@Override
	public String getServer() {

		// 重建一个Map，避免服务器的上下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(IP.serverWeightMap);

		// 取得Ip地址List
		Set<String> keySet = serverMap.keySet();
		List<String> keyList = new ArrayList<String>();
		keyList.addAll(keySet);

		String server = null;
		synchronized (pos) {
			if (pos > keySet.size())
				pos = 0;
			server = keyList.get(pos);
			pos++;
		}

		return server;
	}

}
