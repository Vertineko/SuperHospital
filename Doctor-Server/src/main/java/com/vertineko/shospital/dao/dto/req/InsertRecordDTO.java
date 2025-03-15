package com.vertineko.shospital.dao.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vertineko.shospital.constant.Sex;
import lombok.Data;

import java.util.Date;

@Data
public class InsertRecordDTO {

    private Long patientId;

    private Long doctorId;

    private String name;

    private Sex sex;

    private Integer age;

    private Integer department;

    private Integer epidemic;

    private String condition;

    private String check;

    private String assistantCheck;

    private String diagnosis;

    private String handle;

    private String advice;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
