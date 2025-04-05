package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.RecordDO;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;

public interface RecordService {

    Long insertRecord(InsertRecordDTO requestParam);

    /**
     * 按照病历记录获取病历详细信息
     * @param id 病历id
     * @return 病历详细信息
     */
    RecordDO getById(Long id);


    /**
     * 仅医生可用
     * @param id
     * @return
     */
    int removeRecord(Long id);

    int updateRecord(RecordDO requestParam);
}
