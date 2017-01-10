package com.luna.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.RetryNTimes;

/**
 * Curator framework watch test.
 * 
 * <pre>
 * Curator提供了三种Watcher(Cache)来监听结点的变化：
   		Path Cache：监视一个路径下1）孩子结点的创建、2）删除，3）以及结点数据的更新。产生的事件会传递给注册的PathChildrenCacheListener。
   		Node Cache：监视一个结点的创建、更新、删除，并将结点的数据缓存在本地。
   		Tree Cache：Path Cache和Node Cache的“合体”，监视路径下的创建、更新、删除事件，并缓存路径下所有孩子结点的数据。
        下面就测试一下最简单的Path Watcher：
 * </pre>
 * 
 */

public class CuratorWatcherTest {

	public static void main(String[] args) throws Exception {
		// 1.Connect to zk
		CuratorFramework client = CuratorFrameworkFactory.newClient(Constant.ZK_ADDRESS, new RetryNTimes(10, 5000));
		client.start();
		System.out.println("zk client start successfully!");

		// 2.Register watcher
		PathChildrenCache watcher = new PathChildrenCache(client, Constant.ZK_PATH, true);// if
		// cache
		// data

		watcher.getListenable().addListener(new PathChildrenCacheListener() {

			@Override
			public void childEvent(CuratorFramework paramCuratorFramework, PathChildrenCacheEvent event)
					throws Exception {
				ChildData data = event.getData();
				if (data == null) {
					System.out.println("No data in event[" + event + "]");
				} else {
					System.out.println("Receive event: " + "type=[" + event.getType() + "]" + ", path=["
							+ data.getPath() + "]" + ", data=[" + new String(data.getData()) + "]" + ", stat=["
							+ data.getStat() + "]");
				}

			}

		});
		watcher.start(StartMode.BUILD_INITIAL_CACHE);
		System.out.println("Register zk watcher successfully!");

		Thread.sleep(Integer.MAX_VALUE);
	}

}