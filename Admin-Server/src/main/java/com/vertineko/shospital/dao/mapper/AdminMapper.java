package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.AdminDO;
import com.vertineko.shospital.dao.dto.req.AdminPageDTO;
import com.vertineko.shospital.dao.dto.res.AdminPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper extends BaseMapper<AdminDO> {

    IPage<AdminPageVO> getAdminPage(AdminPageDTO requestParam);
}
