package com.guo.jedisTest;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.guo.common.jedis.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

public class jedisTest {
	// 连接redis集群
	// @Test
	public void testJedisCluster() {

		JedisPoolConfig config = new JedisPoolConfig();
		// 最大连接数
		config.setMaxTotal(30);
		// 最大连接空闲数
		config.setMaxIdle(2);

		// 集群结点
		Set<HostAndPort> jedisClusterNode = new HashSet<HostAndPort>();
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7001));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7002));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7003));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7004));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7005));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7006));
		jedisClusterNode.add(new HostAndPort("192.168.0.136", 7007));
		JedisCluster jc = new JedisCluster(jedisClusterNode, config);

		JedisCluster jcd = new JedisCluster(jedisClusterNode);
		jcd.set("name", "guo");
		String value = jcd.get("name");
		System.out.println(value);
	}

}
