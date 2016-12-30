package com.fsmeeting.luna.load.strategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fsmeeting.luna.load.model.IP;

/**
 * <pre>
 * 1 优点
 * 		与加权轮询法类似，加权随机法也是根据后端服务器不同的配置和负载情况来配置不同的权重。不同的是，它是按照权重来随机选择服务器的，而不是顺序。
 * 2 缺点
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
public class WeightRandomStrategy implements ILoadStrategy {

	@Override
	public String getServer() {
		// 重建一个Map，避免服务器的上下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(IP.serverWeightMap);

		// 取得Ip地址List
		Set<String> keySet = serverMap.keySet();
		Iterator<String> iterator = keySet.iterator();

		List<String> serverList = new ArrayList<String>();
		while (iterator.hasNext()) {
			String server = iterator.next();
			int weight = serverMap.get(server);
			for (int i = 0; i < weight; i++)
				serverList.add(server);
		}

		java.util.Random random = new java.util.Random();
		int randomPos = random.nextInt(serverList.size());

		return serverList.get(randomPos);
	}

}
