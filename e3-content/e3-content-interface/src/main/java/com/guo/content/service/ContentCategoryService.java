package com.guo.content.service;

import java.util.List;

import com.guo.common.pojo.EasyUITreeNode;
import com.guo.common.utils.E3Result;

public interface ContentCategoryService {
	List<EasyUITreeNode> getContentCatList(long parentId);
	
	E3Result  insertContentCategory(long parentId,String name);

	
	E3Result deleteContentCategory(Long parentId, Long id);

	
	E3Result updateContentCategory(Long id, String name);

}
