package com.vertineko.shospital.remote.service;


import com.vertineko.shospital.dto.doctor.req.InsertDoctorDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByIdDTO;
import com.vertineko.shospital.dto.doctor.req.UpdateDoctorByUsernameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(value = "doctor-server")
public interface DoctorRemoteService {

    //远程调用本就是http返回的已经是json了 不必再转化回类
    @RequestMapping("/doctor/api/insertDoctor")
    String insertDoctor(InsertDoctorDTO requestParam);

    @RequestMapping("/doctor/api/removeById/{id}")
    String removeById(@PathVariable Long id);

    @RequestMapping("/doctor/api/removeByUsername/{username}")
    String removeByUsername(@PathVariable String username);

    @RequestMapping("/doctor/api/updateById")
    String updateById(UpdateDoctorByIdDTO requestParam);

    @RequestMapping("/doctor/api/updateByUsername")
    String updateByUsername(UpdateDoctorByUsernameDTO requestParam);
}
