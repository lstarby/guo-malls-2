package com.guo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guo.mapper.TbItemMapper;
import com.guo.pojo.TbItem;
import com.guo.pojo.TbItemExample;
import com.guo.pojo.TbItemExample.Criteria;
import com.guo.service.ItemService;
/**
 * 商品管理Service
 * <p>Title: ItemServiceImpl</p>
 * <p>Description: </p>
 * @author	guo
 * @date	21 Sep 201722:50:31
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public TbItem getItemById(long itemId) {
		//根据主键查询
		//TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example );
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	
}
