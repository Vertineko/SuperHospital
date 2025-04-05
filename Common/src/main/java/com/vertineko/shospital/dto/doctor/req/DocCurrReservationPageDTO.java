package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vertineko.shospital.dto.doctor.res.DocReservationPageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
public class DocCurrReservationPageDTO extends Page<DocReservationPageVO> {

    /**
     * 当前使用系统的医生id
     */
    private Long id;

    private String patientName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date minCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date maxCreateTime;

    private Integer status;

}
