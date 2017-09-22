package com.guo.service;

import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(long itemId);
	/**
	 * 商品列表展示
	 * <p>Title: getItemList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @return
	 */
	EasyUIDataGridResult getItemList(int page,int rows);

}
