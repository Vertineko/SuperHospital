package com.vertineko.shospital.dto.doctor.req;

import lombok.Data;

@Data
public class updMedicineDTO {

    private Long id;

    private String form;

    private String note;

    private Double price;

    private Long count;
}
