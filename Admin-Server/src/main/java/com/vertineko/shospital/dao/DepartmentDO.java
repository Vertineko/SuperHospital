package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("department")
public class DepartmentDO {

    //部门ID
    private Long id;

    //部门名称
    private String name;
}
