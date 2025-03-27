package com.vertineko.shospital.dto.patient.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.constant.Sex;
import com.vertineko.shospital.dao.PatientDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PatientPageDTO extends Page<PatientDO> {

    private String username;

    private String name;

    private Sex sex;

    private Integer minAge;

    private Integer maxAge;

    private String tele;

}
