package com.ruoyi.common.utils.redis.spring;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import io.lettuce.core.SetArgs;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.cluster.api.async.RedisAdvancedClusterAsyncCommands;


/**
 * <p>Redis分布式锁</p>
 * <p>基于spring和lettuce</p>
 * @author 杨雪令
 * @time 2019年4月2日
 * @version 1.0
 */
public class SpringRedisLock {

	private Logger logger = LoggerFactory.getLogger(getClass());

	//redis模板
	private RedisTemplate<String, String> redisTemplate;


	/**
	 * <p>创建Redis分布式锁</p>
	 * @param redisTemplate redis模板
	 * @author 杨雪令
	 * @time 2019年4月4日
	 * @version 1.0
	 */
	public SpringRedisLock(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}



	/**
	 * <p>加锁</p>
	 * @param key 锁的键
	 * @param lock 锁的钥匙
	 * @param timeout 过期时间，单位秒
	 * @return boolean 是否成功
	 * @author 杨雪令
	 * @time 2019年4月2日
	 * @version 1.0
	 */
	@SuppressWarnings("unchecked")
	public boolean lock(String key, String lock, long timeout) {
		try {
			//尝试拿锁，如果拿到锁，重置过期时间
			String rlock = redisTemplate.opsForValue().get(key);
			if(lock.equals(rlock)) {
				return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
			}

			//重新加锁
			RedisCallback<String> callback = (connection) -> {
				Object nativeConnection = connection.getNativeConnection();
				if (nativeConnection instanceof RedisAsyncCommands) {
					RedisAsyncCommands<byte[], byte[]> commands = (RedisAsyncCommands<byte[], byte[]>) nativeConnection;
					return commands
							.getStatefulConnection()
							.sync()
							.set(key.getBytes(), lock.getBytes(), SetArgs.Builder.nx().ex(timeout));
				}
				if (nativeConnection instanceof RedisAdvancedClusterAsyncCommands) {
					RedisAdvancedClusterAsyncCommands<byte[], byte[]> clusterAsyncCommands = (RedisAdvancedClusterAsyncCommands<byte[], byte[]>) nativeConnection;
					return clusterAsyncCommands
							.getStatefulConnection()
							.sync()
							.set(key.getBytes(), lock.getBytes(), SetArgs.Builder.nx().ex(timeout));
				}
				return "Fail";
			};
			String result = redisTemplate.execute(callback);
			if(result!=null && result.toUpperCase().equals("OK")) {
				return true;
			}
		} catch (Exception e) {
			logger.error("redis lock error", e);
		}
		return false;
	}





	/**
	 * <p>释放锁</p>
	 * @param key 锁的键
	 * @param lock 锁的钥匙
	 * @author 杨雪令
	 * @time 2019年4月4日
	 * @version 1.0
	 */
	public void releaseLock(String key, String lock) {
		try {
			//尝试拿锁，如果没有拿到锁，不能释放锁
			String rlock = redisTemplate.opsForValue().get(key);
			if(!lock.equals(rlock)) {
				return;
			}
			redisTemplate.delete(key);
		} catch (Exception e) {
			logger.error("redis releaseLock error", e);
		}
	}


}
