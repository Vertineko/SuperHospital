package com.vertineko.shospital.dao.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.dao.AdminDO;
import lombok.Data;

@Data
public class AdminPageDTO extends Page<AdminDO> {

    private String username;

    private String name;

    private String tele;

    private Integer newFlag;

}
