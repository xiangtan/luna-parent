package com.luna.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;

/**
 * <pre>
 * Curator framework's client test.
 * Output:
 *  $ create /zktest hello 
 *  $ ls / 
 *  [zktest, zookeeper]
 *  $ get /zktest 
 *  hello
 *  $ set /zktest world 
 *  $ get /zktest 
 *  world
 *  $ delete /zktest 
 *  $ ls / 
 *  [zookeeper]
 * </pre>
 */
public class CuratorClientTest {

	public static void main(String[] args) throws Exception {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(Constant.ZK_ADDRESS, new RetryNTimes(5, 2000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.Client API test
		// 2.1 Create node
		String data1 = "hello";
		print("create", Constant.ZK_PATH, data1);
		client.create().creatingParentsIfNeeded().forPath(Constant.ZK_PATH, data1.getBytes());

		// 2.2 Get node and data
		print("ls", "/");
		print(client.getChildren().forPath("/"));
		print("get", Constant.ZK_PATH);
		print(client.getData().forPath(Constant.ZK_PATH));

		// 2.3 Modify data
		String data2 = "world";
		print("set", Constant.ZK_PATH, data2);
		client.setData().forPath(Constant.ZK_PATH, data2.getBytes());
		print("get", Constant.ZK_PATH);
		print(client.getData().forPath(Constant.ZK_PATH));

		// 2.4 Remove node
		print("delete", Constant.ZK_PATH);
		client.delete().forPath(Constant.ZK_PATH);
		print("ls", "/");
		print(client.getChildren().forPath("/"));
	}

	private static void print(String... cmds) {
		StringBuilder text = new StringBuilder("$ ");
		for (String cmd : cmds) {
			text.append(cmd).append(" ");
		}
		System.out.println(text.toString());
	}

	private static void print(Object result) {
		System.out.println(result instanceof byte[] ? new String((byte[]) result) : result);
	}

}