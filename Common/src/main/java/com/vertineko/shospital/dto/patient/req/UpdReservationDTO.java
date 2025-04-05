package com.vertineko.shospital.dto.patient.req;

import lombok.Data;

@Data
public class UpdReservationDTO {

    private Long id;

    private Long orderId;

    private Long recordId;

    private Integer status;
}
