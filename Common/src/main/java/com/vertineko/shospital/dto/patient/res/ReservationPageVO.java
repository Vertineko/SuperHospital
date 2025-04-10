package com.vertineko.shospital.dto.patient.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationPageVO {

    private Long id;

    private String department;

    private String recordId;

    private String orderId;

    /**
     * 医生名称
     */
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 预约状态
     */
    private Integer status;

}
