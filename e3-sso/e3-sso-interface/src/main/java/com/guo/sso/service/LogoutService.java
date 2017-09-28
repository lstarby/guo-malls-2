package com.guo.sso.service;

import com.guo.common.utils.E3Result;

public interface LogoutService {

	E3Result userLogout(String token);
}
