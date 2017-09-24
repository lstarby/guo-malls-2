package com.guo.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页展示
 * <p>Title: IndexController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	24 Sep 201713:15:11
 * @version 1.0
 */
@Controller
public class IndexController {
	
	@RequestMapping("/index")
	public String showIndex() {
		return "index";
	}

}
