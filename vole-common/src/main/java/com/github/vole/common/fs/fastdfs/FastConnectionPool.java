package com.github.vole.common.fs.fastdfs;

import org.csource.fastdfs.*;
import org.springframework.stereotype.Component;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * FastDFS client 连接池实现
 */
@Component
public class FastConnectionPool {

    // 原conf 格式文件配置
    private static final String FDFS_CONFIG = "fastdfs/fdfs_client.conf";
    // properties 格式文件配置
    private static final String FASTDFS_PROPERTIES = "fastdfs/fastdfs-client.properties";

    /*空闲的连接池*/
    private LinkedBlockingQueue<StorageClient1> idleConnectionPool = null;

    /**
     * 连接池默认最小连接数
     */
    private long minPoolSize = 10;

    /**
     * 连接池默认最大连接数
     */
    private long maxPoolSize = 30;

    /**
     * 默认等待时间（单位：秒）
     */
    private long waitTimes = 200;

    /**
     * fastdfs客户端创建连接默认1次
     */
    private static final int COUNT = 1;

//    private Object obj = new Object();

    TrackerServer trackerServer = null;

    public FastConnectionPool() {
        /** 初始化连接池 */
        poolInit();

        /** 注册心跳 */
        //HeartBeat beat = new HeartBeat(this);
        //beat.beat();
    }

    public FastConnectionPool(long minPoolSize, long maxPoolSize, long waitTimes) {
        System.out.println(
            "[线程池构造方法(ConnectionPool)][默认参数：minPoolSize=" + minPoolSize + ",maxPoolSize=" + maxPoolSize + ",waitTimes="
                + waitTimes + "]");
        this.minPoolSize = minPoolSize;
        this.maxPoolSize = maxPoolSize;
        this.waitTimes = waitTimes;
        /** 初始化连接池 */
        poolInit();
        /** 注册心跳 */
        // HeartBeat beat = new HeartBeat(this);
        //beat.beat();
    }

    private void poolInit() {
        try {
            /** 加载配置文件 */
            initClientGlobal();
            /** 初始化空闲连接池 */
            idleConnectionPool = new LinkedBlockingQueue<StorageClient1>();
            /** 初始化忙碌连接池 */
            // busyConnectionPool = new ConcurrentHashMap<StorageClient1, Object>();

            TrackerClient trackerClient = new TrackerClient();
            trackerServer = trackerClient.getConnection();
            int flag = 0;
            while (trackerServer == null && flag < 5) {
                System.out.println("[创建TrackerServer(createTrackerServer)][第" + flag + "次重建]");
                flag++;
                initClientGlobal();
                trackerServer = trackerClient.getConnection();
            }
            // 测试 Tracker活跃情况
            // ProtoCommon.activeTest(trackerServer.getSocket());

            /** 往线程池中添加默认大小的线程 */
            createTrackerServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[FASTDFS (poolInit)--Exception]");
        }
    }

    public void createTrackerServer() {

        System.out.println("[创建TrackerServer(createTrackerServer)]");
        TrackerServer trackerServer = null;

        try {

            for (int i = 0; i < minPoolSize; i++) {
                // 把client1添加到连接池
                StorageServer storageServer = null;
                StorageClient1 client1 = new StorageClient1(trackerServer, storageServer);
                idleConnectionPool.add(client1);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("[创建TrackerServer(createTrackerServer)][异常：{}]");
        }

    }

    public StorageClient1 checkout() {

        StorageClient1 client1 = idleConnectionPool.poll();

        if (client1 == null) {
            if (idleConnectionPool.size() < maxPoolSize) {
                createTrackerServer();
                try {
                    client1 = idleConnectionPool.poll(waitTimes, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("[(checkout)-error][error:timeout:{}]");
                }
            }
        }

        // 添加到忙碌连接池
        // busyConnectionPool.put(client1, obj);
        System.out.println("[(checkout)][Success]");
        return client1;
    }

    public void checkin(StorageClient1 client1) {

        System.out.println("[释放当前连接(checkin)]");

        client1 = null;
        if (idleConnectionPool.size() < minPoolSize) {
            createTrackerServer();
        }

    }

    private void initClientGlobal() throws Exception {
        // 加载原 conf 格式文件配置：
        ClientGlobal.init(FDFS_CONFIG);
        // 加载 properties 格式文件配置：
        ClientGlobal.initByProperties(FASTDFS_PROPERTIES);

        /*加载 Properties 对象配置：
        Properties props = new Properties();
        props.put(ClientGlobal.PROP_KEY_TRACKER_SERVERS, "10.0.11.101:22122,10.0.11.102:22122");
        ClientGlobal.initByProperties(props);

        加载 trackerServers 字符串配置：
        String trackerServers = "10.0.11.101:22122,10.0.11.102:22122";
        ClientGlobal.initByTrackers(trackerServers);*/
    }

    public LinkedBlockingQueue<StorageClient1> getIdleConnectionPool() {
        return idleConnectionPool;
    }

    public long getMinPoolSize() {
        return minPoolSize;
    }

    public void setMinPoolSize(long minPoolSize) {
        if (minPoolSize != 0) {
            this.minPoolSize = minPoolSize;
        }
    }

    public long getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(long maxPoolSize) {
        if (maxPoolSize != 0) {
            this.maxPoolSize = maxPoolSize;
        }
    }

    public long getWaitTimes() {
        return waitTimes;
    }

    public void setWaitTimes(int waitTimes) {
        if (waitTimes != 0) {
            this.waitTimes = waitTimes;
        }
    }
}