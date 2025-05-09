package com.vertineko.shospital.dto.patient.req;

import lombok.Data;

@Data
public class UpdReservationDTO {

    private String id;

    private String orderId;

    private String recordId;

    private Integer status;
}
