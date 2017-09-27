package com.guo.service;

import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.pojo.TbItem;
import com.guo.pojo.TbItemDesc;
import com.guo.pojo.TbItemParamItem;

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
	/**
	 * 商品添加
	 * <p>Title: addItem</p>
	 * <p>Description: </p>
	 * @param item
	 * @param desc
	 * @return
	 */
	E3Result addItem(TbItem item,String desc);
	
	/**
	 * 删除商品
	 * <p>Title: deleteItem</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	E3Result deleteItem(String ids);
	
	/**
	 * 下架商品
	 * <p>Title: instockItem</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	E3Result instockItem(String ids);

	/**
	 * 上架
	 * <p>Title: reshelfItem</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	E3Result reshelfItem(String ids);
	
	/**
	 * 商品详情页
	 * <p>Title: getItemDescById</p>
	 * <p>Description: </p>
	 * @param itemId
	 * @return
	 */
	TbItemDesc getItemDescById(long itemId);

}
