package com.guo.order.service;

import com.guo.common.utils.E3Result;
import com.guo.order.pojo.OrderInfo;

public interface OrderService {

	E3Result createOrder(OrderInfo orderInfo);
}
