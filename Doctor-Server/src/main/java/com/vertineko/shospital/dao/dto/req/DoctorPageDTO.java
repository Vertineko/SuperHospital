package com.vertineko.shospital.dao.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.constant.Sex;
import com.vertineko.shospital.dao.DoctorDO;
import lombok.Data;

@Data
public class DoctorPageDTO extends Page<DoctorDO> {
    /**
     * 分页时的查找参数
     */

    private String username;

    private String name;

    private Integer maxAge;

    private Integer minAge;

    private Sex sex;

    private String tele;

    private String mail;

    private String worktime;

    private Long department;

    private Integer newflag;
}
