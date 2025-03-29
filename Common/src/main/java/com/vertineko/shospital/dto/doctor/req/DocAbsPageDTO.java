package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dto.doctor.res.DocAbsPageVO;
import lombok.Data;

@Data
public class DocAbsPageDTO extends Page<DocAbsPageVO> {

    private Long departmentId;

    //进到Service实现类中在计算的参数
    private Integer currDay;
}
