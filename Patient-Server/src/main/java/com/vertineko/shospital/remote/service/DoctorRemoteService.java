package com.vertineko.shospital.remote.service;

import com.vertineko.shospital.dto.doctor.req.DepartmentPageDTO;
import com.vertineko.shospital.dto.doctor.req.DocAbsPageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "doctor-server")
public interface DoctorRemoteService {

    @RequestMapping("/doctor/api/getDocAbsPage")
    String getDocAbsPage(@RequestBody DocAbsPageDTO requestParam);

    @RequestMapping("/doctor/api/department/getPage")
    String getDepartmentPage(@RequestBody DepartmentPageDTO requestParam);

    @RequestMapping("/doctor/api/order/remove")
    String removeOrder(@RequestParam Long id);

    @RequestMapping("/doctor/api/record/remove")
    String removeRecord(@RequestParam Long id);


}
