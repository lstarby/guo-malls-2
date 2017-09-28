package com.guo.sso.service;

import com.guo.common.utils.E3Result;

/**
 * 根据token查询用户信息
 * <p>Title: TokenService</p>
 * <p>Description: </p>
 * @author	guo
 * @date	28 Sep 201722:34:48
 * @version 1.0
 */
public interface TokenService {

	E3Result getUserByToken(String token);
}
