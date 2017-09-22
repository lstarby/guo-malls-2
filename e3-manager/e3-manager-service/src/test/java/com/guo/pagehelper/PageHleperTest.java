package com.guo.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.guo.mapper.TbItemMapper;
import com.guo.pojo.TbItem;
import com.guo.pojo.TbItemExample;

public class PageHleperTest {
	//@Test
	public void testPageHelper() {
		//初始化Spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//从容器中取得Mapper代理对象
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		//执行SQL之前设置分页信息，使用PageHelper的startPage()方法
		PageHelper.startPage(1, 10);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取得分页信息，PageInfo。1，总记录数，2.总页数，当前页
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());  //总记录数
		System.out.println(pageInfo.getPages());  //总页数
		System.out.println(list.size());
	}
}
