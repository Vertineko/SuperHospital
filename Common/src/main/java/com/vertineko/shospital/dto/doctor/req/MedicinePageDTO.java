package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MedicinePageDTO extends Page<MedicinePageVO> {

    private String name;
}
