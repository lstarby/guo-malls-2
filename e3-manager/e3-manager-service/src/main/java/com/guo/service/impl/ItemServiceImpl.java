package com.guo.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guo.common.pojo.EasyUIDataGridResult;
import com.guo.common.utils.E3Result;
import com.guo.common.utils.IDUtils;
import com.guo.mapper.TbItemDescMapper;
import com.guo.mapper.TbItemMapper;
import com.guo.pojo.TbItem;
import com.guo.pojo.TbItemDesc;
import com.guo.pojo.TbItemExample;
import com.guo.pojo.TbItemExample.Criteria;
import com.guo.pojo.TbItemParamItem;
import com.guo.service.ItemService;

/**
 * 商品管理Service
 * <p>
 * Title: ItemServiceImpl
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author guo
 * @date 21 Sep 201722:50:31
 * @version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Resource
	private Destination topicDestination;
	
	@Override
	public TbItem getItemById(long itemId) {
		// 根据主键查询
		// TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		// 设置查询条件
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// 设置分页信息
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		// 创建一个查询结果集
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		// 取分页结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		// 记录总页数
		long total = pageInfo.getTotal();
		result.setTotal(total);
		return result;
	}

	@Override
	public E3Result addItem(TbItem item, String desc) {
		// 生成商品Id
		final long itemId = IDUtils.genItemId();
		// 补全商品属性
		item.setId(itemId);
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		// 向商品表中插入数据
		itemMapper.insert(item);
		// 创建一个商品描述表对应的pojo对象
		TbItemDesc itemDesc = new TbItemDesc();
		// 补全商品描述信息
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		// 向商品描述表中插入数据
		itemDescMapper.insert(itemDesc);
		// 发送一个商品添加消息
		jmsTemplate.send(topicDestination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});

		// 返回成功
		return E3Result.ok();
	}

	// 删除商品
	@Override
	public E3Result deleteItem(String ids) {
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for (String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(values);

			List<TbItem> list = itemMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				TbItem item = list.get(0);
				item.setStatus((byte) 3);
				itemMapper.updateByExample(item, example);
			}
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 下架商品
	@Override
	public E3Result instockItem(String ids) {

		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for (String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(values);

			List<TbItem> list = itemMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				TbItem item = list.get(0);
				item.setStatus((byte) 2);
				itemMapper.updateByExample(item, example);
			}
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	// 上架商品
	@Override
	public E3Result reshelfItem(String ids) {
		try {
			String[] idsArray = ids.split(",");
			List<Long> values = new ArrayList<Long>();
			for (String id : idsArray) {
				values.add(Long.parseLong(id));
			}
			TbItemExample example = new TbItemExample();
			Criteria criteria = example.createCriteria();
			criteria.andIdIn(values);
			List<TbItem> list = itemMapper.selectByExample(example);
			if (list != null && list.size() > 0) {
				TbItem item = list.get(0);
				item.setStatus((byte) 1);
				itemMapper.updateByExample(item, example);
			}
			return E3Result.ok();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
