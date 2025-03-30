package com.vertineko.shospital.dao.dto.req;

import lombok.Data;

@Data
public class InsertReservationDTO {

    private String department;

    private Long doctorId;

//    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
//    private Date createdTime;

}
