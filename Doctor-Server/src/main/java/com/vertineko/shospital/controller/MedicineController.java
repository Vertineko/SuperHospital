package com.vertineko.shospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dto.doctor.req.InsertMedicineDTO;
import com.vertineko.shospital.dto.doctor.req.MedicinePageDTO;
import com.vertineko.shospital.dto.doctor.req.updMedicineDTO;
import com.vertineko.shospital.dto.doctor.res.MedicinePageVO;
import com.vertineko.shospital.service.MedicineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MedicineController {

    private final MedicineService medicineService;

    @RequestMapping("/doctor/api/medicine/insertMedicine")
    public Result<Integer> insertMedicine(@RequestBody InsertMedicineDTO requestParam){
        return Results.success(medicineService.insertMedicine(requestParam));
    }

    @RequestMapping("/doctor/api/medicine/getMedicinePage")
    public Result<IPage<MedicinePageVO>> getMedicinePage(@RequestBody MedicinePageDTO requestParam){
        return Results.success(medicineService.getMedicinePage(requestParam));
    }

    @RequestMapping("/doctor/api/medicine/getMedicineById/{id}")
    public Result<MedicinePageVO> getMedicineById(@PathVariable Long id){
        return Results.success(medicineService.getMedicineById(id));
    }

    @RequestMapping("/doctor/api/medicine/removeMedicineById/{id}")
    public Result<Integer> removeMedicineById(@PathVariable Long id){
        return Results.success(medicineService.removeMedicineById(id));
    }

    @RequestMapping("/doctor/api/medicine/updMedicineDTO")
    public Result<Integer> updateMedicine(@RequestBody updMedicineDTO requestParam){
        return Results.success(medicineService.updateMedicine(requestParam));
    }
}
