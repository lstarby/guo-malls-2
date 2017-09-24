package com.guo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guo.common.pojo.EasyUITreeNode;
import com.guo.common.utils.E3Result;
import com.guo.content.service.ContentCategoryService;

/**
 * 商品分类管理Controller
 * <p>
 * Title: ContentCatController
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author guo
 * @date 24 Sep 201715:06:58
 * @version 1.0
 */
@Controller
@RequestMapping("/content/category")
public class ContentCatController {

	@Autowired
	private ContentCategoryService contentCategoryService;

	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> getContentCatList(@RequestParam(name = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> catList = contentCategoryService.getContentCatList(parentId);
		return catList;
	}

	/**
	 * 添加分类节点
	 * 
	 */
	@RequestMapping("/create")
	@ResponseBody
	public E3Result creatContentCatgory(Long parentId, String name) {
		E3Result result = contentCategoryService.insertContentCategory(parentId, name);
		return result;
	}

	/**
	 * 删除分类节点
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public E3Result deleteContentCatgory(Long parentId, Long id) {

		E3Result result = contentCategoryService.deleteContentCategory(parentId, id);
		return result;
	}

	/**
	 * 更新节点
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public E3Result updateContentCategory(Long id, String name) {
		return contentCategoryService.updateContentCategory(id, name);
	}
}
