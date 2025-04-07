package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "orders")
public class OrderDO {

    @TableId(value = "id")
    private Long id;

    private String medicine;

    private Double price;

    private Integer Status;
}
