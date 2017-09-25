package com.guo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.utils.E3Result;
import com.guo.search.service.SearchItemService;


/**
 * 导入数据到索引库
 * <p>Title: SearchItemController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	26 Sep 201703:01:56
 * @version 1.0
 */
@Controller
public class SearchItemController {
	
	@Autowired
	private SearchItemService searchItemService;

	@RequestMapping("/index/item/import")
	@ResponseBody
	public E3Result importItemList() {
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;
		
	}
}
