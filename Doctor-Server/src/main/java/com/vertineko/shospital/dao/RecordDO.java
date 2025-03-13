package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.vertineko.shospital.constant.Sex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class RecordDO {

    private Long id;

    //患者姓名
    private String name;

    private Sex sex;

    private Integer age;

    private String department;

    private Integer epidemic;

    //病情症状
    private String condition;

    //临床初步诊断
    private String check;

    //辅助诊断
    private String assistantCheck;

    //当前病症处理
    private String handle;

    //医嘱
    private String advice;

    //诊断时间
    private String create_time;

    //状态
    private Integer status;

}
