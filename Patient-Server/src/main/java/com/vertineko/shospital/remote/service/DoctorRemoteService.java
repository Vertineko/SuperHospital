package com.vertineko.shospital.remote.service;

import com.vertineko.shospital.dto.doctor.req.DocAbsPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient(name = "doctor-server")
public interface DoctorRemoteService {

    @RequestMapping("/doctor/api/getDocAbsPage")
    String getDocAbsPage(@RequestBody DocAbsPageDTO requestParam);

}
