package com.vertineko.shospital.dto.doctor.res;

import lombok.Data;

@Data
public class MedicinePageVO {

    private Long id;

    private String name;

    private String form;

    private String note;

    private String price;

    private String count;

}
