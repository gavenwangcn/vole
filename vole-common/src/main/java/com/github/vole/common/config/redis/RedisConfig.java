//package com.github.vole.common.config.redis;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * Redis集群配置文件
// */
//@Component
////@PropertySource("classpath:conf/redis.properties")
//@ConfigurationProperties(prefix = "redis")
//public class RedisConfig {
//
//    /**
//     * 节点名称
//     */
//    private String nodes;
//
//    /**
//     * Redis服务名称
//     */
//    private String masterName;
//
//    /**
//     * 密码
//     */
//    private String password;
//
//    /**
//     * 最大连接数
//     */
//    private int maxTotal;
//
//    /**
//     * 最大空闲数
//     */
//    private int maxIdle;
//
//    /**
//     * 最小空闲数
//     */
//    private int minIdle;
//
//    /**
//     * 连接超时时间
//     */
//    private int timeout;
//
//    /**
//     * redis存储kv是否序列化
//     */
//    private boolean serializeEnabled;
//
//    public String getNodes() {
//        return nodes;
//    }
//
//    public void setNodes(String nodes) {
//        this.nodes = nodes;
//    }
//
//    public String getMasterName() {
//        return masterName;
//    }
//
//    public void setMasterName(String masterName) {
//        this.masterName = masterName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getMaxTotal() {
//        return maxTotal;
//    }
//
//    public void setMaxTotal(int maxTotal) {
//        this.maxTotal = maxTotal;
//    }
//
//    public int getMaxIdle() {
//        return maxIdle;
//    }
//
//    public void setMaxIdle(int maxIdle) {
//        this.maxIdle = maxIdle;
//    }
//
//    public int getMinIdle() {
//        return minIdle;
//    }
//
//    public void setMinIdle(int minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public int getTimeout() {
//        return timeout;
//    }
//
//    public void setTimeout(int timeout) {
//        this.timeout = timeout;
//    }
//
//    public boolean isSerializeEnabled() {
//        return serializeEnabled;
//    }
//    public void setSerializeEnabled(boolean serializeEnabled) {
//        this.serializeEnabled = serializeEnabled;
//    }
//
//    @Override
//    public String toString() {
//        return "RedisConfig{" +
//            "nodes='" + nodes + '\'' +
//            ", masterName='" + masterName + '\'' +
//            ", password='" + password + '\'' +
//            ", maxTotal='" + maxTotal + '\'' +
//            ", maxIdle='" + maxIdle + '\'' +
//            ", minIdle='" + minIdle + '\'' +
//            ", timeout='" + timeout + '\'' +
//            ", serializeEnabled='" + serializeEnabled + '\'' +
//            '}';
//    }
//}
