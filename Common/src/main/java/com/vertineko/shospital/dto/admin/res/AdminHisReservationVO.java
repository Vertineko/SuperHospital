package com.vertineko.shospital.dto.admin.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AdminHisReservationVO {

    /**
     * 预约单号
     */
    private String id;

    /**
     * 患者姓名
     */
    private String pName;

    /**
     * 医生姓名
     */
    private String dName;

    /**
     * 病历ID
     */
    private String recordId;

    /**
     * 处方ID
     */
    private String orderId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 预约状态
     */
    private Integer status;
}
