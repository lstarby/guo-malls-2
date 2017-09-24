package com.guo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.content.service.ContentService;
import com.guo.pojo.TbContent;

/**
 * 分类管理
 * <p>Title: ContentController</p>
 * <p>Description: </p>
 * @author	guo
 * @date	24 Sep 201723:05:29
 * @version 1.0
 */
@Controller
public class ContentController {
	
	@Autowired
	private ContentService contentService;
	
	/**
	 * 添加
	 * <p>Title: insertContent</p>
	 * <p>Description: </p>
	 * @param content
	 * @return
	 */
	@RequestMapping("/content/save")
	@ResponseBody
	public E3Result insertContent(TbContent content){
		E3Result result = contentService.insertContent(content);
		return result;
	}
	
	
	/**
	 * 加载列表
	 * <p>Title: getContentList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Long page, Long rows){
		EasyUIDataGridResult result = contentService.getContentList(page, rows);
		return result;
	}
	
	/**
	 * 删除
	 * <p>Title: deleteContent</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	@RequestMapping("/content/delete")
	@ResponseBody
	public E3Result deleteContent(String ids){
		return contentService.deleteContent(ids);
	}
	
	/**
	 * 更新
	 * <p>Title: updateItem</p>
	 * <p>Description: </p>
	 * @param content
	 * @return
	 */
	@RequestMapping("/rest/content/edit")
	@ResponseBody
	public E3Result updateItem(TbContent content){
		E3Result result=contentService.updateContent(content);
		return result;
	}

}
