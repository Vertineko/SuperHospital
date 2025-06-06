package com.vertineko.shospital.dao;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("reservation")
public class ReservationDO {

    private Long id;

    private String department;

    private Long orderId;

    private Long doctorId;

    private Long patientId;

    private Long recordId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer status;

}
