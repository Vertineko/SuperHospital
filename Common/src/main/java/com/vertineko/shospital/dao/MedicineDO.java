package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("medicine")
public class MedicineDO {

    private Long id;

    private String name;

    /**
     * 制剂
     */
    private String form;

    private String note;

    private String price;

    private Long count;
}
