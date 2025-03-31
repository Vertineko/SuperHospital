package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vertineko.shospital.dto.doctor.res.DocReservationHisVO;
import lombok.Data;

import java.util.Date;

@Data
public class DocReservationHisDTO extends Page<DocReservationHisVO> {

    /**
     * 当前正在使用系统的医生ID
     */
    private Long id;

    private String patientName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxCreateTime;

    private Integer status;

}
