package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DocterException;
import com.vertineko.shospital.dao.RecordDO;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;
import com.vertineko.shospital.dao.dto.req.RecordPageDTO;
import com.vertineko.shospital.dao.mapper.RecordMapper;
import com.vertineko.shospital.service.RecordService;
import com.vertineko.shospital.usr.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordDO> implements RecordService {

    private final RecordMapper recordMapper;

    @Override
    public int insertRecord(InsertRecordDTO requestParam) {
        RecordDO recordDO = new RecordDO();
        BeanUtil.copyProperties(requestParam, recordDO);
        recordDO.setCreateTime(new Date());
        recordDO.setDoctorId(UserUtils.getUser().getId());
        return recordMapper.insert(recordDO);
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
            throw new DocterException(DoctorErrorCode.RECORD_NOT_EXISTED);
        }
        return record;
    }

    @Override
    public RecordPageDTO getDoctorRecords(RecordPageDTO recordPageDTO) {
        LambdaQueryWrapper<RecordDO> queryWrapper = Wrappers.lambdaQuery(RecordDO.class)
                .like(RecordDO::getName, recordPageDTO.getName())
                .gt(RecordDO::getCreateTime, recordPageDTO.getMinCreateTime())
                .lt(RecordDO::getCreateTime, recordPageDTO.getMaxCreateTime())
                .eq(RecordDO::getDoctorId, UserUtils.getUser().getId());
        return recordMapper.selectPage(recordPageDTO, queryWrapper);
    }

    @Override
    public RecordPageDTO getPatientRecords(RecordPageDTO recordPageDTO) {
        LambdaQueryWrapper<RecordDO> queryWrapper = Wrappers.lambdaQuery(RecordDO.class)
                .like(RecordDO::getName, recordPageDTO.getName())
                .gt(RecordDO::getCreateTime, recordPageDTO.getMinCreateTime())
                .lt(RecordDO::getCreateTime, recordPageDTO.getMaxCreateTime())
                .eq(RecordDO::getPatientId, UserUtils.getUser().getId());
        return recordMapper.selectPage(recordPageDTO, queryWrapper);
    }

    @Override
    public int removeRecord(Long id) {
        RecordDO record = getById(id);
        if (record == null) {
            throw new DocterException(DoctorErrorCode.RECORD_NOT_EXISTED);
        }
        return recordMapper.deleteById(id);
    }

    //保留更新病历状态
    @Override
    public int updateRecord(RecordDO requestParam) {
        return 0;
    }
}
