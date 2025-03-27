package com.vertineko.shospital.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.PatientDO;
import com.vertineko.shospital.dao.dto.req.PatientLoginDTO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByIdDTO;
import com.vertineko.shospital.dao.dto.req.UpdatePatientByUsernameDTO;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;
import com.vertineko.shospital.dto.patient.res.PatientPageVO;
import com.vertineko.shospital.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @RequestMapping("/patient/api/insertPatient")
    public Result<Integer> insertPatient(@RequestBody InsertPatientDTO requestParam){
        return Results.success(patientService.insertPatient(requestParam));
    }

    @RequestMapping("/patient/api/removeById/{id}")
    public Result<Integer> removeById(@PathVariable Long id){
        return Results.success(patientService.removeById(id));
    }

    @RequestMapping("/patient/api/removeByUsername")
    public Result<Integer> removeByUsername(@Param("username") String username){
        return Results.success(patientService.removeByUsername(username));
    }

    @RequestMapping("/patient/api/login")
    public Result<String> login(@RequestBody PatientLoginDTO requestParam){
        return Results.success(patientService.login(requestParam));
    }

    @RequestMapping("/patient/api/updateById")
    public Result<Integer> updateById(@RequestBody UpdatePatientByIdDTO requestParam){
        return Results.success(patientService.updateById(requestParam));
    }

    @RequestMapping("/patient/api/updateByUsername")
    public Result<Integer> updateByUsername(@RequestBody UpdatePatientByUsernameDTO requestParam){
        return Results.success(patientService.updateByUsername(requestParam));
    }

    @RequestMapping("/patient/api/pagePatient")
    public Result<IPage<PatientPageVO>> pagePatient(@RequestBody PatientPageDTO requestParam){
        return Results.success(patientService.pagePatient(requestParam));
    }

    @RequestMapping("/patient/api/getById/{id}")
    public Result<PatientDO> getById(@PathVariable Long id){
        return Results.success(patientService.getById(id));
    }

    @RequestMapping("/patient/api/getByUsername")
    public Result<PatientDO> getByUsername(@RequestParam("username") String username){
        return Results.success(patientService.getByUsername(username));
    }

}
