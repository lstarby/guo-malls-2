package com.guo.sso.service;

import com.guo.common.utils.E3Result;
import com.guo.pojo.TbUser;

public interface RegisterService {

	E3Result checkData(String param, int type);
	E3Result register(TbUser user);
}
