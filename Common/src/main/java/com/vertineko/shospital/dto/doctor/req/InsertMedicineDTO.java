package com.vertineko.shospital.dto.doctor.req;

import lombok.Data;

@Data
public class InsertMedicineDTO {

    private String name;

    private String form;

    private String note;

    private Double price;

    private Long count;
}
