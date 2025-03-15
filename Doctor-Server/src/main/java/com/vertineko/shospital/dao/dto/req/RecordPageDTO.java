package com.vertineko.shospital.dao.dto.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.vertineko.shospital.dao.RecordDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RecordPageDTO extends Page<RecordDO> {

    private String name;

    //如果此时为患者远程调用， 此处才会不为null
    private String patientId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String minCreateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String maxCreateTime;
}
