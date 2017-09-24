package com.guo.content.service;

import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.pojo.TbContent;

public interface ContentService {
	E3Result insertContent(TbContent content);

	EasyUIDataGridResult getContentList(long page, long pageSize);

	E3Result deleteContent(String ids);

	E3Result updateContent(TbContent content);
}
