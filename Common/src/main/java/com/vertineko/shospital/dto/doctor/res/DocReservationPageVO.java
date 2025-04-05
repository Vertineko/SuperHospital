package com.vertineko.shospital.dto.doctor.res;

import lombok.Data;

@Data
public class DocReservationPageVO {


    private String id;

    private String name;

    private String createTime;

    private Integer status;

    private String patientId;

    private String recordId;
}
