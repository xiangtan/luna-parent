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
		createNode(client);// 创建节点
		getNode(client);// 获取节点信息
		modifyNode(client);// 修改节点信息
		removeNode(client);// 删除节点信息
	}

	/**
	 * 创建节点
	 * 
	 * @param client
	 * @throws Exception
	 */
	public static void createNode(CuratorFramework client) throws Exception {
		String data1 = "hello";
		print("create", Constant.ZK_PATH, data1);
		client.create().creatingParentsIfNeeded().forPath(Constant.ZK_PATH, data1.getBytes());
	}

	/**
	 * 获取节点、节点数据
	 * 
	 * @param client
	 * @throws Exception
	 */
	public static void getNode(CuratorFramework client) throws Exception {
		print("ls", "/");
		print(client.getChildren().forPath("/"));
		print("get", Constant.ZK_PATH);
		print(client.getData().forPath(Constant.ZK_PATH));
	}

	/**
	 * 修改节点信息
	 * 
	 * @param client
	 * @throws Exception
	 */
	public static void modifyNode(CuratorFramework client) throws Exception {
		String data2 = "world";
		print("set", Constant.ZK_PATH, data2);
		client.setData().forPath(Constant.ZK_PATH, data2.getBytes());
		print("get", Constant.ZK_PATH);
		print(client.getData().forPath(Constant.ZK_PATH));
	}

	/**
	 * 删除节点信息
	 * 
	 * @param client
	 * @throws Exception
	 */
	public static void removeNode(CuratorFramework client) throws Exception {
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