package com.vertineko.shospital.dto.patient.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dto.patient.res.ReservationPageVO;
import lombok.Data;

@Data
public class ReservationPageDTO extends Page<ReservationPageVO> {

    private String department;

    //运行的时候再加上
    private Long patientId;

    private Integer status;
}
