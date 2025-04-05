package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dto.doctor.req.InsertMedicineDTO;
import com.vertineko.shospital.dto.doctor.req.MedicinePageDTO;
import com.vertineko.shospital.dto.doctor.req.updMedicineDTO;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;

public interface MedicineService {

    Integer insertMedicine(InsertMedicineDTO requestParam);

    IPage<MedicinePageVO> getMedicinePage(MedicinePageDTO requestParam);

    MedicinePageVO getMedicineById(Long id);

    Integer removeMedicineById(Long id);

    Integer updateMedicine(updMedicineDTO requestParam);
}
