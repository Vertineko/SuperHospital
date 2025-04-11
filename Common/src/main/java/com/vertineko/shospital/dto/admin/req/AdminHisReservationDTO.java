package com.vertineko.shospital.dto.admin.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vertineko.shospital.dto.admin.res.AdminHisReservationVO;
import lombok.Data;

import java.util.Date;

@Data
public class AdminHisReservationDTO extends Page<AdminHisReservationVO> {

    private String patientName;

    private String doctorName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxCreateTime;

    private Integer status;
}
