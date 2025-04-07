package com.vertineko.shospital.dto.doctor.req;

import lombok.Data;

import java.util.List;

@Data
public class updRecordDTO {

    private Long id;

    private Long reservationId;

    private Integer epidemic;

    private String conditions;

    private String checks;

    private String assistantCheck;

    private String handle;

    private String advice;

    private List<MedicinesDTO> medicinesVOList;
}
