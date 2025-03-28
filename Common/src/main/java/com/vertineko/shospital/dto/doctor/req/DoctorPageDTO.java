package com.vertineko.shospital.dto.doctor.req;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vertineko.shospital.constant.Sex;
import com.vertineko.shospital.dto.doctor.res.DoctorPageVO;
import lombok.Data;

import java.util.List;

@Data
public class DoctorPageDTO extends Page<DoctorPageVO> {
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

    /**
     * 解析后以数组方式存起来的工作日期，最大长度为7,其中每个元素只有0 or 1两种可能
     */
    private List<Integer> workTimeList;

}
