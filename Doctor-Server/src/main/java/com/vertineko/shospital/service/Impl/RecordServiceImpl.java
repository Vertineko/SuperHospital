package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constant.OrderConstant;
import com.vertineko.shospital.constant.StatusConstant;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DoctorException;
import com.vertineko.shospital.dao.MedicineDO;
import com.vertineko.shospital.dao.OrderDO;
import com.vertineko.shospital.dao.RecordDO;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;
import com.vertineko.shospital.dao.mapper.MedicineMapper;
import com.vertineko.shospital.dao.mapper.OrderMapper;
import com.vertineko.shospital.dao.mapper.RecordMapper;
import com.vertineko.shospital.dto.doctor.req.MedicinesDTO;
import com.vertineko.shospital.dto.doctor.req.updRecordDTO;
import com.vertineko.shospital.dto.doctor.res.RecordDetailVO;
import com.vertineko.shospital.dto.patient.req.UpdReservationDTO;
import com.vertineko.shospital.remote.service.PatientRemoteService;
import com.vertineko.shospital.service.RecordService;
import com.vertineko.shospital.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class RecordServiceImpl extends ServiceImpl<RecordMapper, RecordDO> implements RecordService {

    private final RecordMapper recordMapper;

    private final PatientRemoteService patientRemoteService;

    private final MedicineMapper medicineMapper;

    private final OrderMapper orderMapper;

    private final PatientRemoteService patientService;


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
    @Transactional
    public Integer updateRecord(updRecordDTO requestParam) {
        if (requestParam.getMedicinesVOList() == null || requestParam.getMedicinesVOList().isEmpty()){
            return recordMapper.updateById(BeanUtil.copyProperties(requestParam, RecordDO.class));
        }
        //先插入Order表
        recordMapper.updateById(BeanUtil.copyProperties(requestParam, RecordDO.class));
        String json = JSON.toJSONString(requestParam.getMedicinesVOList());
        BigDecimal decimal = new BigDecimal("0.00");
        for (MedicinesDTO medicineDTO : requestParam.getMedicinesVOList()) {
            MedicineDO medicine = medicineMapper.selectById(medicineDTO.getId());
            if (medicine == null){
                throw new DoctorException(DoctorErrorCode.MEDICINE_NOT_EXISTED);
            }
            BigDecimal temp = new BigDecimal(medicine.getPrice());
            decimal = decimal.add(temp);
        }
        Double price = decimal.doubleValue();
        OrderDO orderDO = OrderDO.builder()
                .medicine(json)
                .price(price)
                .Status(OrderConstant.UN_PAYED)
                .build();
        int i = orderMapper.insert(orderDO);
        if (i <= 0){
            throw new DoctorException(DoctorErrorCode.RECORD_UPDATE_FAILED);
        }
        Long orderId = orderDO.getId();
        log.info("{}", orderId);
        RecordDO record = new RecordDO();
        BeanUtil.copyProperties(requestParam, record);
        //再次更新病历
        recordMapper.updateById(record);
        //再更新预约
        UpdReservationDTO updReservationDTO = new UpdReservationDTO();
        updReservationDTO.setId(requestParam.getReservationId());
        updReservationDTO.setOrderId(orderId);
        updReservationDTO.setStatus(StatusConstant.COMPLETED);
        patientRemoteService.updateReservation(updReservationDTO);
        return 1;
    }

    @Override
    public RecordDetailVO getRecordDetail(Long id) {
        RecordDO record = getById(id);
        Long patientId = record.getPatientId();
        Long doctorId = record.getDoctorId();
        RecordDetailVO recordDetailVO = recordMapper.getDetailByRecordId(id, patientId, doctorId);
        if (recordDetailVO.getSex().equals("FEMALE")) {
            recordDetailVO.setSex("女");
        }else{
            recordDetailVO.setSex("男");
        }
        return recordDetailVO;
    }
}
