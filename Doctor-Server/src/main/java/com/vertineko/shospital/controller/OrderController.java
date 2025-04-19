package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dto.doctor.res.OrderVO;
import com.vertineko.shospital.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @RequestMapping("/doctor/api/order/getOrders/{id}")
    public Result<List<OrderVO>> getOrders(@PathVariable Long id){
        return Results.success(orderService.getOrders(id));
    }

    @RequestMapping("/doctor/api/order/remove")
    public Result<Integer> removeOrder(Long id) {
        return Results.success(orderService.removeOrder(id));
    }
}
