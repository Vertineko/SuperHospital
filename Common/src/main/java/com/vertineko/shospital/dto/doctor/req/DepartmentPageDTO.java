package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dto.doctor.res.DepartmentPageVO;
import lombok.Data;

@Data
public class DepartmentPageDTO extends Page<DepartmentPageVO> {

    private String name;
}
