package com.vertineko.shospital.remote.service;

import com.vertineko.shospital.dto.admin.req.AdminHisReservationDTO;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;
import com.vertineko.shospital.dto.patient.req.UpdatePatientByUsernameDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "patient-server")
public interface PatientRemoteService {

    @RequestMapping("/patient/api/pagePatient")
    String pagePatient(@RequestBody PatientPageDTO requestParam);

    @RequestMapping("/patient/api/insertPatient")
    String insertPatient(@RequestBody InsertPatientDTO requestParam);

    @RequestMapping("/patient/api/removeByUsername")
    String removeByUsername(@RequestParam("username") String username);

    @RequestMapping("/patient/api/getByUsername")
    String getByUsername(@RequestParam("username") String username);

    @RequestMapping("/patient/api/updateByUsername")
    String updateByUsername(@RequestBody UpdatePatientByUsernameDTO requestParam);

    /**
     * 管理员端获取历史预约列表
     *
     * @param requestParam
     * @return
     */
    @RequestMapping("/patient/api/reservation/allReservation")
    String getAllHisReservationPage(AdminHisReservationDTO requestParam);


    @RequestMapping("/patient/api/reservation/remove")
    String deleteReservation(@RequestParam Long id);

}
