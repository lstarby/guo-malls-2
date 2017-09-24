package com.guo.content.service.iml;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.guo.common.pojo.EasyUITreeNode;
import com.guo.common.utils.E3Result;
import com.guo.content.service.ContentCategoryService;
import com.guo.mapper.TbContentCategoryMapper;
import com.guo.pojo.TbContentCategory;
import com.guo.pojo.TbContentCategoryExample;
import com.guo.pojo.TbContentCategoryExample.Criteria;

/**
 * 商品分类Service
 * <p>
 * Title: ContentCategoryServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author guo
 * @date 24 Sep 201714:49:01
 * @version 1.0
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;
	/**
	 * 获取分类列表
	 * <p>Title: getContentCatList</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @return
	 * @see com.guo.content.service.ContentCategoryService#getContentCatList(long)
	 */
	@Override
	public List<EasyUITreeNode> getContentCatList(long parentId) {
		// 根据parentId查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		List<EasyUITreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");
			// 添加到列表
			nodeList.add(node);
		}
		return nodeList;
	}
	/**
	 * 增加子节点
	 * <p>Title: insertContentCategory</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @param name
	 * @return
	 * @see com.guo.content.service.ContentCategoryService#insertContentCategory(long, java.lang.String)
	 */
	@Override
	public E3Result insertContentCategory(long parentId, String name) {
		// 创建一个pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		// 补全属性
		contentCategory.setName(name);
		contentCategory.setIsParent(false);
		// 1是正常，2是删除
		contentCategory.setStatus(1);
		contentCategory.setParentId(parentId);
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new java.util.Date());
		contentCategory.setUpdated(new java.util.Date());
		// 插入到数据库
		categoryMapper.insert(contentCategory);
		// 查看父节点的isParent列是否为true
		TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
		// 判断是否为true
		if (!parentCat.getIsParent()) {
			parentCat.setIsParent(true);
			categoryMapper.updateByPrimaryKey(parentCat);
		}
		return E3Result.ok(contentCategory);
	}
	/**
	 * 
	 * 删除子节点
	 * <p>Title: deleteContentCategory</p>
	 * <p>Description: </p>
	 * @param parentId
	 * @param id
	 * @return
	 */
	@Override
	public E3Result deleteContentCategory(Long parentId, Long id) {
		categoryMapper.deleteByPrimaryKey(id);
		// 判断父节点下是否还有子节点
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		// 父节点
		TbContentCategory parentCat = categoryMapper.selectByPrimaryKey(parentId);
		// 如果没有子节点，设置为false
		if (list != null && list.size() > 0) {
			parentCat.setIsParent(true);
		} else {
			parentCat.setIsParent(false);
		}
		return E3Result.ok();
	}
	/**
	 * 更新节点
	 * <p>Title: updateContentCategory</p>
	 * <p>Description: </p>
	 * @param id
	 * @param name
	 * @return
	 */
	@Override
	public E3Result updateContentCategory(Long id, String name) {
		//根据id查询
		TbContentCategory tcc = categoryMapper.selectByPrimaryKey(id);
		//创建pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setCreated(tcc.getCreated());
		contentCategory.setId(id);
		contentCategory.setIsParent(tcc.getIsParent());
		contentCategory.setName(name);
		contentCategory.setParentId(tcc.getParentId());
		contentCategory.setSortOrder(tcc.getSortOrder());
		contentCategory.setStatus(tcc.getStatus());
		contentCategory.setUpdated(new java.util.Date());
		//执行更新操作
		categoryMapper.updateByPrimaryKey(contentCategory);
		//返回结果
		return E3Result.ok();
	}

}
