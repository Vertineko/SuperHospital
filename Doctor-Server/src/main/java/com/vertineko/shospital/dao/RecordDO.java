package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("record")
public class RecordDO {

    @TableId(value = "id")
    private Long id;

    //患者ID
    private Long patientId;

    //主治医生ID
    private Long doctorId;

    private Integer epidemic;

    //病情症状
    private String conditions;

    //临床初步诊断
    private String checks;

    //辅助诊断
    private String assistantCheck;

    //当前病症处理
    private String handle;

    //医嘱
    private String advice;

    //诊断时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;


}
