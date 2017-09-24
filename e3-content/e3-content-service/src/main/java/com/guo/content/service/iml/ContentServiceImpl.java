package com.guo.content.service.iml;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.common.utils.ExceptionUtil;
import com.guo.content.service.ContentService;
import com.guo.mapper.TbContentMapper;
import com.guo.pojo.TbContent;
import com.guo.pojo.TbContentExample;
import com.guo.pojo.TbContentExample.Criteria;
@Service
public class ContentServiceImpl implements ContentService {

	@Autowired
	private TbContentMapper contentMapper;
	
	/**
	 * 添加
	 * <p>Title: insertContent</p>
	 * <p>Description: </p>
	 * @param content
	 * @return
	 */
	@Override
	public E3Result insertContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		return E3Result.ok();
	}
	
	/**
	 * 分页列表展示
	 * <p>Title: getContentList</p>
	 * <p>Description: </p>
	 * @param page
	 * @param pageSize
	 * @return
	 */
	@Override
	public EasyUIDataGridResult getContentList(long page, long pageSize) {
		TbContentExample example = new TbContentExample();
		// 开始分页
		PageHelper.startPage((int) page, (int) pageSize);
		// 获取查询结果
		List<TbContent> rows = contentMapper.selectByExample(example);
		EasyUIDataGridResult eudg = new EasyUIDataGridResult();
		eudg.setRows(rows);
		// 获取分页信息 商品总数信息
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(rows);
		eudg.setTotal(pageInfo.getTotal());
		return eudg;
	}

	/**
	 * 删除
	 * <p>Title: deleteContent</p>
	 * <p>Description: </p>
	 * @param ids
	 * @return
	 */
	@Override
	public E3Result deleteContent(String ids) {
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for(String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbContentExample example = new TbContentExample();
			TbContentExample.Criteria criteria = example.createCriteria();
			criteria.andIdIn(values);
			contentMapper.deleteByExample(example);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return E3Result.ok(); 
	}

	/**
	 * 修改
	 * <p>Title: updateContent</p>
	 * <p>Description: </p>
	 * @param content
	 * @return
	 */
	@Override
	public E3Result updateContent(TbContent content) {
		try {
			//更新商品
			TbContentExample example = new TbContentExample();
			 Criteria criteria = example.createCriteria();
			 criteria.andIdEqualTo(content.getId());
			
			TbContent tbContent = contentMapper.selectByPrimaryKey(content.getId());
			
			content.setCreated(tbContent.getCreated());
			content.setUpdated(new Date());

			contentMapper.updateByExample(content, example);
			
		} catch (Exception e) {
			e.printStackTrace();
			return E3Result.build(500, ExceptionUtil.getStackTrace(e));
		}
		return E3Result.ok();
		
	}

}
