package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.OrderDO;
import com.vertineko.shospital.dto.doctor.res.OrderVO;

import java.util.List;

public interface OrderService {

    Long insertOrder(OrderDO order);

    List<OrderVO> getOrders(Long orderId);
}
