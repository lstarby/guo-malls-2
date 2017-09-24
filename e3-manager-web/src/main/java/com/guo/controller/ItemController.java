package com.guo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.pojo.TbItem;
import com.guo.service.ItemService;

/**
 * 商品管理Controller
 * <p>
 * Title: ItemController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author guo
 * @date 21 Sep 201723:07:50
 * @version 1.0
 */
@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}

	/**
	 * 商品列表展示
	 * <p>
	 * Title: getItemList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 调用服务查询 商品列表
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	/**
	 * 商品添加
	 * <p>
	 * Title: addItem
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param item
	 * @param desc
	 * @return
	 */
	@RequestMapping(value = "item/save", method = RequestMethod.POST)
	@ResponseBody
	public E3Result addItem(TbItem item, String desc) {
		E3Result result = itemService.addItem(item, desc);
		return result;
	}

	/**
	 * 删除商品
	 * <p>
	 * Title: deleteItem
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public E3Result deleteItem(String ids) {
		return itemService.deleteItem(ids);
	}

	/**
	 * 下架
	 * <p>Title: instockItem</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public E3Result instockItem(String ids) {
		return itemService.instockItem(ids);
	}

	/**
	 * 上架
	 * <p>Title: reshelfItem</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelfItem(String ids) {
		return itemService.reshelfItem(ids);
	}
}
