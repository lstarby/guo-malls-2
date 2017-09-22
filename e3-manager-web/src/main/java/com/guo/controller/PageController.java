package com.guo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页展示Controller
 * <p>Title: PageController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	22 Sep 201713:51:13
 * @version 1.0
 */
@Controller
public class PageController {
	
	@RequestMapping("/")
	public String showIndenx() {
		return "index";
	}
	
	@RequestMapping("/{page}") 
	public String showPage(@PathVariable String page) {
		return page;
	}

}
