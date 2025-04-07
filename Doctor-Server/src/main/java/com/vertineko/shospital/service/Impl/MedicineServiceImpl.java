package com.vertineko.shospital.service.Impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vertineko.shospital.constrain.errorDef.error.DoctorErrorCode;
import com.vertineko.shospital.constrain.exceptionDef.exception.DoctorException;
import com.vertineko.shospital.dao.MedicineDO;
import com.vertineko.shospital.dao.mapper.MedicineMapper;
import com.vertineko.shospital.dto.doctor.req.InsertMedicineDTO;
import com.vertineko.shospital.dto.doctor.req.MedicinePageDTO;
import com.vertineko.shospital.dto.doctor.req.updMedicineDTO;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;
import com.vertineko.shospital.dto.doctor.res.MedicinesVO;
import com.vertineko.shospital.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MedicineServiceImpl extends ServiceImpl<MedicineMapper, MedicineDO> implements MedicineService {

    private final MedicineMapper medicineMapper;

    @Override
    public Integer insertMedicine(InsertMedicineDTO requestParam) {
        MedicineDO medicineDO = new MedicineDO();
        BeanUtil.copyProperties(requestParam, medicineDO);
        return medicineMapper.insert(medicineDO);
    }

    @Override
    public IPage<MedicinePageVO> getMedicinePage(MedicinePageDTO requestParam) {
        return medicineMapper.getMedicinePage(requestParam);
    }

    /**
     * 根据ID获取药品信息
     * @param id
     * @return
     */
    @Override
    public MedicinePageVO getMedicineById(Long id) {
        MedicineDO medicine = medicineMapper.selectById(id);
        if (medicine == null) {
            throw new DoctorException(DoctorErrorCode.MEDICINE_NOT_EXISTED);
        }
        MedicinePageVO medicinePageVO = new MedicinePageVO();
        BeanUtil.copyProperties(medicine, medicinePageVO);
        return medicinePageVO;
    }

    @Override
    public Integer removeMedicineById(Long id) {
        MedicineDO medicine = medicineMapper.selectById(id);
        if (medicine == null) {
            throw new DoctorException(DoctorErrorCode.MEDICINE_NOT_EXISTED);
        }
        return medicineMapper.deleteById(medicine);
    }

    @Override
    public Integer updateMedicine(updMedicineDTO requestParam) {
        MedicineDO medicine = medicineMapper.selectById(requestParam.getId());
        if (medicine == null) {
            throw new DoctorException(DoctorErrorCode.MEDICINE_NOT_EXISTED);
        }
        BeanUtil.copyProperties(requestParam, medicine);
        return medicineMapper.updateById(medicine);

    }

    @Override
    public List<MedicinesVO> getMedicineList() {
        return medicineMapper.getMedicineList();
    }
}
