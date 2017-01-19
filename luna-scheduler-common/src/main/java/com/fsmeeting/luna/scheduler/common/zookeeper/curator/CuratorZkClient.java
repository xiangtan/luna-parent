package com.fsmeeting.luna.scheduler.common.zookeeper.curator;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.ISerialization;
import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.SerializerFacotry;
import com.fsmeeting.luna.scheduler.common.zookeeper.curator.codec.serializer.impl.Hessian2;

/**
 * 
 * @author yicai.liu
 *
 */
public class CuratorZkClient {

	private String registerServerPath;
	private String zookeeperConnectStr;
	private int sessionTimeoutMs;
	private int retryCount;
	private int sleepMsBetweenRetries;
	private CuratorFramework client;
	private static final ISerialization SERIALIZER = SerializerFacotry.create(Hessian2.ID);

	public void init() {
		client = CuratorFrameworkFactory.builder().connectString(zookeeperConnectStr).sessionTimeoutMs(sessionTimeoutMs)
				.retryPolicy(new RetryNTimes(retryCount, sleepMsBetweenRetries)).build();

		client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
			public void stateChanged(CuratorFramework client, ConnectionState newState) {

				if (newState == ConnectionState.CONNECTED) {
					System.out.println("connected established");
					String data = "128.28.28.28:28";
					createEphemeral(registerServerPath, data, true);
				} else if (newState == ConnectionState.LOST) {
					System.out.println("connection lost,waiting for reconection");
					try {
						System.out.println("reinit---");
					} catch (Exception e) {
						System.err.println("register failed");
					}
				}

			}
		});
		client.start();

	}

	protected String createPersistent(String path, boolean sequential) {
		try {
			if (sequential) {
				return client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path);
			} else {
				return client.create().withMode(CreateMode.PERSISTENT).forPath(path);
			}
		} catch (KeeperException.NodeExistsException e) {
			return path;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected String createPersistent(String path, Object data, boolean sequential) {
		try {
			if (sequential) {
				byte[] zkDataBytes = SERIALIZER.serialize(data);
				return client.create().withMode(CreateMode.PERSISTENT_SEQUENTIAL).forPath(path, zkDataBytes);
			} else {
				return client.create().withMode(CreateMode.PERSISTENT).forPath(path);
			}
		} catch (KeeperException.NodeExistsException e) {
			return path;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected String createEphemeral(String path, boolean sequential) {
		try {
			CreateMode mode = CreateMode.EPHEMERAL;
			if (sequential) {
				mode = CreateMode.EPHEMERAL_SEQUENTIAL;
			}
			return client.create().creatingParentsIfNeeded().withMode(mode).forPath(path);
		} catch (KeeperException.NodeExistsException e) {
			return path;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected String createEphemeral(String path, Object data, boolean sequential) {
		try {
			if (sequential) {
				byte[] zkDataBytes = SERIALIZER.serialize(data);
				return client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path,
						zkDataBytes);
			} else {
				return client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(path);
			}
		} catch (KeeperException.NodeExistsException e) {
			return path;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public boolean exists(String path) {
		try {
			return client.checkExists().forPath(path) != null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public byte[] getData(String path) {
		try {
			return client.getData().forPath(path);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public void setData(String path, Object data) {
		try {
			byte[] zkDataBytes = SERIALIZER.serialize(data);
			client.setData().forPath(path, zkDataBytes);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	public List<String> getChildren(String path) {
		try {
			return client.getChildren().forPath(path);
		} catch (KeeperException.NoNodeException e) {
			return null;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	protected void close() {
		client.close();
	}

	/**
	 * 
	 * getter/setter
	 */
	public String getZookeeperConnectStr() {
		return zookeeperConnectStr;
	}

	public void setZookeeperConnectStr(String zookeeperConnectStr) {
		this.zookeeperConnectStr = zookeeperConnectStr;
	}

	public int getSessionTimeoutMs() {
		return sessionTimeoutMs;
	}

	public void setSessionTimeoutMs(int sessionTimeoutMs) {
		this.sessionTimeoutMs = sessionTimeoutMs;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public int getSleepMsBetweenRetries() {
		return sleepMsBetweenRetries;
	}

	public void setSleepMsBetweenRetries(int sleepMsBetweenRetries) {
		this.sleepMsBetweenRetries = sleepMsBetweenRetries;
	}

	public String getRegisterServerPath() {
		return registerServerPath;
	}

	public void setRegisterServerPath(String registerServerPath) {
		this.registerServerPath = registerServerPath;
	}

}
