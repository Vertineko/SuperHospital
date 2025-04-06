package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.vertineko.shospital.dao.RecordDO;
import com.vertineko.shospital.dto.doctor.res.RecordDetailVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecordMapper extends BaseMapper<RecordDO> {

    RecordDetailVO getDetailByRecordId(Long recordId, Long patientId, Long doctorId);
}
