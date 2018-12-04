//package com.github.vole.common.utils.redis;
//
//import com.github.vole.common.config.redis.RedisConfig;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisSentinelPool;
//import redis.clients.jedis.exceptions.JedisConnectionException;
//
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * Redis分布式缓存工具类
// */
//@Component
//public class RedisClusterUtils {
//
//	private static Logger logger = Logger.getLogger(RedisClusterUtils.class);
//
//	/** 默认缓存时间 */
//	private static final int DEFAULT_CACHE_SECONDS = 60 * 60 * 1;// 单位秒 设置成一小时
//
//	/** 连接池 **/
//	private static JedisSentinelPool jedisSentinelPool;
//    /**
//     * redis配置文件信息
//     */
//    private static RedisConfig redisConfig;
//
//    @Autowired
//    public void setRedisConfig(RedisConfig redisConfig) {
//        this.redisConfig = redisConfig;
//    }
//
//	@Autowired
//	public void setSentinelPool(JedisSentinelPool sentinelPool) {
//		this.jedisSentinelPool = sentinelPool;
//	}
//	/**
//	 * 获取到Jedis
//	 */
//	private static Jedis getJedis()  {
//		try {
//            return jedisSentinelPool.getResource();
//		} catch (JedisConnectionException e) {
//			throw e;
//		}
//	}
//
//	/**
//	 * 释放redis资源
//	 *
//	 * @param jedis
//	 */
//	private static void releaseResource(Jedis jedis) {
//		if (jedis != null) {
//			jedisSentinelPool.returnResource(jedis);
//		}
//	}
//
//	/**
//	 * 删除Redis中的所有key
//	 *
//	 * @throws Exception
//	 */
//	public static void flushAll() {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//			jedis.flushAll();
//		} catch (Exception e) {
//			logger.error("Cache清空失败：" + e);
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 保存一个对象到Redis中(缓存过期时间:使用此工具类中的默认时间) . <br/>
//	 *
//	 * @param key
//	 *            键 . <br/>
//	 * @param object
//	 *            缓存对象 . <br/>
//	 * @return true or false . <br/>
//	 * @throws Exception
//	 */
//	public static Boolean save(Object key, Object object) {
//		if(key == null) return false;
//		return save(key, object, DEFAULT_CACHE_SECONDS);
//	}
//
//	/**
//	 * 保存一个对象到redis中并指定过期时间
//	 *
//	 * @param key
//	 *            键 . <br/>
//	 * @param object
//	 *            缓存对象 . <br/>
//	 * @param seconds
//	 *            过期时间（单位为秒）.<br/>
//	 * @return true or false .
//	 */
//	public static Boolean save(Object key, Object object, int seconds) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//			if (redisConfig.isSerializeEnabled()) {
//				jedis.set(SerializeUtils.serialize(key), SerializeUtils.serialize(object));
//				jedis.expire(SerializeUtils.serialize(key), seconds);
//			} else {
//				jedis.set(key.toString(), object==null ? null : object.toString());
//				jedis.expire(key.toString(), seconds);
//			}
//			return true;
//		} catch (Exception e) {
//			logger.error("Cache保存失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 根据缓存键获取Redis缓存中的值.<br/>
//	 *
//	 * @param key
//	 *            键.<br/>
//	 * @return Object .<br/>
//	 * @throws Exception
//	 */
//	public static Object get(Object key) {
//        if(key == null) return null;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                byte[] obj = jedis.get(SerializeUtils.serialize(key));
//                return obj == null ? null : SerializeUtils.unSerialize(obj);
//            } else{
//                return jedis.get(key.toString());
//            }
//		} catch (Exception e) {
//			logger.error("Cache获取失败：" + e);
//			return null;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 根据缓存键清除Redis缓存中的值.<br/>
//	 *
//	 * @param key
//	 * @return
//	 * @throws Exception
//	 */
//	public static Boolean del(Object key) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		try {
//            jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                jedis.del(SerializeUtils.serialize(key));
//            } else {
//                jedis.del(key.toString());
//            }
//			return true;
//		} catch (Exception e) {
//			logger.error("Cache删除失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 根据缓存键清除Redis缓存中的值.<br/>
//	 *
//	 * @param keys
//	 * @return
//	 * @throws Exception
//	 */
//	public static Boolean del(Object... keys) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                jedis.del(SerializeUtils.serialize(keys));
//            } else {
//                for(int i=0; i< keys.length; i++) {
//                    if (keys[i] !=null) jedis.del(keys[i].toString());
//                }
//            }
//			return true;
//		} catch (Exception e) {
//			logger.error("Cache删除失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 设置key的生存超时时间
//	 * @param key
//	 * @param seconds
//	 *            超时时间（单位为秒）
//	 * @return
//	 */
//	public static Boolean expire(Object key, int seconds) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                jedis.expire(SerializeUtils.serialize(key), seconds);
//            } else{
//                jedis.expire(key.toString(), seconds);
//            }
//			return true;
//		} catch (Exception e) {
//			logger.error("Cache设置超时时间失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 添加一个内容到指定key的hash中
//	 *
//	 * @param key
//	 * @param field
//	 * @param value
//	 * @return
//	 */
//	public static Boolean addHash(String key, Object field, Object value) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                jedis.hset(SerializeUtils.serialize(key), SerializeUtils.serialize(field), SerializeUtils.serialize(value));
//            } else {
//                jedis.hset(key.toString(), field==null?null:field.toString(), value==null?null:value.toString());
//            }
//			return true;
//		} catch (Exception e) {
//			logger.error("Cache保存失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 从指定hash中拿一个对象
//	 *
//	 * @param key
//	 * @param field
//	 * @return
//	 */
//	public static Object getHash(Object key, Object field) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                byte[] obj = jedis.hget(SerializeUtils.serialize(key), SerializeUtils.serialize(field));
//                return SerializeUtils.unSerialize(obj);
//            } else {
//                return jedis.hget(key.toString(), field==null?null:field.toString());
//            }
//		} catch (Exception e) {
//			logger.error("Cache读取失败：" + e);
//			return null;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 从hash中删除指定filed的值
//	 *
//	 * @param key
//	 * @param fields
//	 * @return
//	 */
//	public static Boolean delHash(Object key, Object... fields) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                long result = jedis.hdel(SerializeUtils.serialize(key), SerializeUtils.serialize(fields));
//                return result == 1 ? true : false;
//            } else {
//                for(int i=0; i< fields.length; i++) {
//                    if (fields[i] !=null) jedis.hdel(key.toString(), fields[i]==null?null:fields[i].toString());
//                }
//                return true;
//            }
//		} catch (Exception e) {
//			logger.error("Cache删除失败：" + e);
//			return null;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 拿到缓存中所有符合pattern的key
//	 *
//	 * @param pattern
//	 * @return
//	 */
//	public static Set<byte[]> keys(String pattern) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            return jedis.keys(("*" + pattern + "*").getBytes());
//		} catch (Exception e) {
//			logger.error("Cache获取失败：" + e);
//			return new HashSet<byte[]>();
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 获得hash中的所有key value
//	 *
//	 * @param key
//	 * @return
//	 */
//	public static Map<?, ?> getAllHash(Object key) {
//        if(key == null) return null;
//		Jedis jedis = null;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                return jedis.hgetAll(SerializeUtils.serialize(key));
//            } else {
//                return jedis.hgetAll(key.toString());
//            }
//		} catch (Exception e) {
//			logger.error("Cache获取失败：" + e);
//			return null;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	/**
//	 * 判断一个key是否存在
//	 *
//	 * @param key
//	 * @return
//	 */
//	public static Boolean exists(Object key) {
//        if(key == null) return false;
//		Jedis jedis = null;
//		Boolean result = false;
//		try {
//			jedis = getJedis();
//            if (redisConfig.isSerializeEnabled()) {
//                return jedis.exists(SerializeUtils.serialize(key));
//            } else {
//                return jedis.exists(key.toString());
//            }
//		} catch (Exception e) {
//			logger.error("Cache获取失败：" + e);
//			return false;
//		} finally {
//			releaseResource(jedis);
//		}
//	}
//
//	public void setJedisSentinelPool(JedisSentinelPool jedisSentinelPool) {
//		RedisClusterUtils.jedisSentinelPool = jedisSentinelPool;
//	}
//	public static JedisSentinelPool getJedisSentinelPool() {
//		return jedisSentinelPool;
//	}
//}
