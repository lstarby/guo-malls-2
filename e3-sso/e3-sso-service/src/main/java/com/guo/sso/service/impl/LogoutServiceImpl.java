package com.guo.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.guo.common.jedis.JedisClient;
import com.guo.common.utils.E3Result;
import com.guo.sso.service.LogoutService;

/**
 * 用户注销处理
 * <p>
 * Title: LogoutServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author guo
 * @date 28 Sep 201722:41:18
 * @version 1.0
 */
@Service
public class LogoutServiceImpl implements LogoutService {

	@Autowired
	private JedisClient jedisClient;

	@Override
	public E3Result userLogout(String token) {
		// 根据token从redis删除用户信息
		jedisClient.del("SESSION:" + token);
		// 返回用户信息
		return E3Result.ok();
	}

}
