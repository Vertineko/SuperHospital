package com.vertineko.shospital.dto.doctor.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RecordDetailVO {

    private String id;

    private String department;

    private String doctor;

    private String patient;

    //患者性别
    private String sex;

    private Integer age;

    private Integer epidemic;

    private String conditions;

    private String checks;

    private String assistantCheck;

    private String diagnosis;

    private String handle;

    private String advice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private List<MedicinesVO> medicinesList;
}
