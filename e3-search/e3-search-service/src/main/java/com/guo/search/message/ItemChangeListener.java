package com.guo.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.guo.search.service.impl.SearchItemServiceImpl;

/**
 * 监听商品添加消息，接收消息后，将对应的商品同步到索引库
 * <p>Title: ItemChangeListener</p>
 * <p>Description: </p>
 * @author	guo
 * @date	27 Sep 201720:34:48
 * @version 1.0
 */
public class ItemChangeListener implements MessageListener {
	
	@Autowired
	private SearchItemServiceImpl searchItemServiceImpl;

	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textMessage = null;
			Long itemId = null; 
			//等待事务提交
			Thread.sleep(2000);
			//取商品id
			if (message instanceof TextMessage) {
				textMessage = (TextMessage) message;
				itemId = Long.parseLong(textMessage.getText());
			}
			//向索引库添加文档
			searchItemServiceImpl.addDocument(itemId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
