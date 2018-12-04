//package com.github.vole.common.utils.redis;
//
//import org.apache.log4j.Logger;
//
//import java.util.Map;
//
///**
// * 将用户会话信息保存在Redis中的工具类
// */
//public class RedisSessionUtils {
//
//	private static Logger logger = Logger.getLogger(RedisSessionUtils.class);
//	/**
//	 * 默认过期时间
//	 */
//	private static final Integer DEFAULT_SESSION_TIMEOUT = 60 * 30;
//
//	/**
//	 * 保存session
//	 *
//	 * @param sessionId
//	 * @param sessionObj
//	 * @param seconds
//	 *            超时秒数，如果为null则默认为30分钟
//	 * @return
//	 */
//	public static boolean saveSession(String sessionId, Map<String, Object> sessionObj, Integer seconds) {
//		boolean result = false;
//		try {
//			if (seconds != null) {
//				result = RedisClusterUtils.save(sessionId, sessionObj, seconds);
//			} else {
//				result = RedisClusterUtils.save(sessionId, sessionObj, DEFAULT_SESSION_TIMEOUT);
//			}
//		} catch (Exception e) {
//			logger.error("缓存删除失败：" + e);
//		}
//		return result;
//	}
//
//	/**
//	 * 删除session
//	 *
//	 * @param sessionId
//	 * @return
//	 */
//	public static boolean deleteSession(String sessionId) {
//		boolean result = false;
//		try {
//			result = RedisClusterUtils.del(sessionId);
//		} catch (Exception e) {
//			logger.error("缓存删除失败：" + e);
//		}
//		return result;
//	}
//
//	/**
//	 * 取session
//	 *
//	 * @param sessionId
//	 * @return
//	 */
//	@SuppressWarnings("unchecked")
//	public static Map<String, Object> getSession(String sessionId) {
//		Map<String, Object> sessionObj = null;
//		try {
//			sessionObj = (Map<String, Object>) RedisClusterUtils.get(sessionId);
//		} catch (Exception e) {
//			logger.error("缓存读取失败：" + e);
//		}
//		return sessionObj;
//	}
//
//	/**
//	 * 更新session超时时间
//	 *
//	 * @param sessionId
//	 * @param seconds
//	 * @return
//	 */
//	public static boolean updateSessionTime(String sessionId, Integer seconds) {
//		boolean result = false;
//		try {
//			if (seconds == null) {
//				result = RedisClusterUtils.expire(sessionId, DEFAULT_SESSION_TIMEOUT);
//			} else {
//				result = RedisClusterUtils.expire(sessionId, seconds);
//			}
//		} catch (Exception e) {
//			logger.error("缓存时间更新失败：" + e);
//		}
//		return result;
//	}
//
//}
