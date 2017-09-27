package com.guo.search.mapper;

import java.util.List;

import com.guo.common.pojo.SearchItem;


public interface ItemMapper {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
