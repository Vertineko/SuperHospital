package com.vertineko.shospital.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.dao.OrderDO;
import com.vertineko.shospital.dao.mapper.OrderMapper;
import com.vertineko.shospital.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    private final OrderMapper orderMapper;

    @Override
    public Long insertOrder(OrderDO order) {
        orderMapper.insert(order);
        return order.getId();
    }
}
