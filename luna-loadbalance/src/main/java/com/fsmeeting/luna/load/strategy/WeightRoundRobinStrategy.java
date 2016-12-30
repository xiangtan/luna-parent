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
 * 不同的服务器可能机器配置和当前系统的负载并不相同，
 * 因此它们的抗压能力也不尽相同，给配置高、负载低的机器配置更高的权重，
 * 让其处理更多的请求，而低配置、高负载的机器，则给其分配较低的权重，降低其系统负载。
 * 2 缺点
 * </pre>
 * 
 * @author yicai.liu<moon>
 *
 */
public class WeightRoundRobinStrategy implements ILoadStrategy {
	private static Integer pos;

	@Override
	public String getServer() {
		// 重建一个Map，避免服务器的上下线导致的并发问题
		Map<String, Integer> serverMap = new HashMap<String, Integer>();
		serverMap.putAll(IP.serverWeightMap);

		// 取得Ip地址List
		Set<String> keySet = serverMap.keySet();
		Iterator<String> iterator = keySet.iterator();

		List<String> serverList = new ArrayList<String>();
		// 增加了一段权重计算的代码，根据权重的大小，将地址重复地增加到服务器地址列表中，权重越大，该服务器每轮所获得的请求数量越多
		while (iterator.hasNext()) {
			String server = iterator.next();
			int weight = serverMap.get(server);
			for (int i = 0; i < weight; i++)
				serverList.add(server);
		}

		String server = null;
		synchronized (pos) {
			if (pos > keySet.size())
				pos = 0;
			server = serverList.get(pos);
			pos++;
		}

		return server;
	}

	public static void main(String[] args) {
		System.out.println(new WeightRoundRobinStrategy().getServer());
	}
}
