package com.guo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.pojo.EasyUITreeNode;
import com.guo.service.ItemCatService;

/**
 * 商品分类Controller
 * <p>Title: ItemCatController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	22 Sep 201721:23:26
 * @version 1.0
 */
@Controller
public class ItemCatController {
	
	@Autowired
	private ItemCatService catService;
	
	@RequestMapping("/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(name="id",defaultValue="0") Long parentId){
		//调用服务查询节点列表
		List<EasyUITreeNode> list = catService.getItemCatList(parentId);
		return list;
	}

}
