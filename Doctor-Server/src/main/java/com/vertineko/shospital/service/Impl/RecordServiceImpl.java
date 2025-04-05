package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.StatusConstant;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DoctorException;
import com.vertineko.shospital.dao.RecordDO;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;
import com.vertineko.shospital.dao.mapper.RecordMapper;
import com.vertineko.shospital.dto.patient.req.UpdReservationDTO;
import com.vertineko.shospital.remote.service.PatientRemoteService;
import com.vertineko.shospital.service.RecordService;
import com.vertineko.shospital.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordDO> implements RecordService {

    private final RecordMapper recordMapper;

    private final PatientRemoteService patientRemoteService;

    @Override
    @Transactional
    public Long insertRecord(InsertRecordDTO requestParam) {
        //先新建病历档案，在更新预约状态，和预约表上的字段
        RecordDO recordDO = new RecordDO();
        BeanUtil.copyProperties(requestParam, recordDO);
        recordDO.setCreateTime(new Date());
        recordDO.setDoctorId(UserUtils.getUser().getId());
        int flag = recordMapper.insert(recordDO);
        if (flag > 0) {
            Long recordId = recordDO.getId();
            UpdReservationDTO updReservationDTO = new UpdReservationDTO();
            updReservationDTO.setId(requestParam.getReservationId());
            updReservationDTO.setRecordId(recordId);
            updReservationDTO.setStatus(StatusConstant.SUBMITTED);
            patientRemoteService.updateReservation(updReservationDTO);
        }else {
            throw new DoctorException(DoctorErrorCode.RECORD_INSERT_FAILED);
        }
        return recordDO.getId();
    }

    /**
     * 按照病历记录获取病历详细信息
     *
     * @param id 病历id
     * @return 病历详细信息
     */
    @Override
    public RecordDO getById(Long id) {
        LambdaQueryWrapper<RecordDO> queryWrapper = Wrappers.lambdaQuery(RecordDO.class)
                .eq(RecordDO::getId, id);
        RecordDO record = recordMapper.selectOne(queryWrapper);
        if (record == null) {
            throw new DoctorException(DoctorErrorCode.RECORD_NOT_EXISTED);
        }
        return record;
    }





    @Override
    public int removeRecord(Long id) {
        RecordDO record = getById(id);
        if (record == null) {
            throw new DoctorException(DoctorErrorCode.RECORD_NOT_EXISTED);
        }
        return recordMapper.deleteById(id);
    }

    //保留更新病历状态
    @Override
    public int updateRecord(RecordDO requestParam) {
        return 0;
    }
}
