package com.guo.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.utils.E3Result;
import com.guo.common.utils.ExceptionUtil;
import com.guo.sso.service.LogoutService;

/**
 * 用户注销Controller
 * <p>Title: LogoutController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	28 Sep 201722:45:51
 * @version 1.0
 */
@Controller
public class LogoutController {
	
	@Autowired
	private LogoutService logoutService;
	
	@RequestMapping("/logout/{token}")
	@ResponseBody
	public Object userLogout(@PathVariable String token, String callback) {
		E3Result result = null;
		try {
			result = logoutService.userLogout(token);
		} catch (Exception e) {
			e.printStackTrace();
			result = E3Result.build(500, ExceptionUtil.getStackTrace(e));
		}
		

		if (StringUtils.isBlank(callback)) {
			return result;
		} else {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(
					result);
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
	}

}
