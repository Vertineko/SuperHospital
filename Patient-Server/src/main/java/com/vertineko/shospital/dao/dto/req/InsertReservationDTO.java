package com.vertineko.shospital.dao.dto.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class InsertReservationDTO {

    private Long department_id;

    private Long doctor_id;

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private Date created_time;

    private Integer status;
}
