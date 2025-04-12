package com.vertineko.shospital.dao.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dao.dto.res.AdminPageVO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AdminPageDTO extends Page<AdminPageVO> {

    private Long id;

    private String username;

    private String name;

    private String tele;

}
