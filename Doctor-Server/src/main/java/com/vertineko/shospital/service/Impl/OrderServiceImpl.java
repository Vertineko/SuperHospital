package com.vertineko.shospital.service.Impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DoctorException;
import com.vertineko.shospital.dao.OrderDO;
import com.vertineko.shospital.dao.mapper.MedicineMapper;
import com.vertineko.shospital.dao.mapper.OrderMapper;
import com.vertineko.shospital.dto.doctor.res.OrderVO;
import com.vertineko.shospital.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    private final OrderMapper orderMapper;

    private final MedicineMapper medicineMapper;

    @Override
    public Long insertOrder(OrderDO order) {
        orderMapper.insert(order);
        return order.getId();
    }

    @Override
    public List<OrderVO> getOrders(Long orderId) {
        //先搜索该orderID是否存在
        if (orderId == null) {
            return null;
        }
        LambdaQueryWrapper<OrderDO> queryWrapper = Wrappers.lambdaQuery(OrderDO.class)
                .eq(OrderDO::getId, orderId);
        OrderDO order = orderMapper.selectOne(queryWrapper);
        if (order == null) {
            return null;
        }
        String json = order.getMedicine();
        List<OrderVO> res = JSON.parseArray(json, OrderVO.class);
        for (OrderVO orderVO : res) {
            orderVO.setId(medicineMapper.selectById(orderVO.getId()).getName());
        }
        return res;
    }

    @Override
    public Integer removeOrder(Long id) {
        log.info("此次要删除的处方ID:{}", id);
        OrderDO order = orderMapper.selectById(id);
        if (order == null) {
            throw new DoctorException(DoctorErrorCode.ORDER_NOT_EXISTED);
        }
        return orderMapper.deleteById(id);
    }
}
