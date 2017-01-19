package com.fsmeeting.luna.scheduler.common.zookeeper.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;

public class CuratorUtils {
	public static final String CONNECT_STRING = "192.168.7.178:2181";

	public static void registerZk() {
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECT_STRING)
				.sessionTimeoutMs(10000).retryPolicy(new RetryNTimes(5, 5000)).build();
		client.getConnectionStateListenable().addListener(clientListener);
		client.start();
	}

	// 客户端的监听配置
	static ConnectionStateListener clientListener = new ConnectionStateListener() {

		public void stateChanged(CuratorFramework client, ConnectionState newState) {
			if (newState == ConnectionState.CONNECTED) {
				System.out.println("connected established");
				//create();
			} else if (newState == ConnectionState.LOST) {
				System.out.println("connection lost,waiting for reconection");
				try {
					System.out.println("reinit---");
				} catch (Exception e) {
					System.err.println("register failed");
				}
			}

		}
	};
}
