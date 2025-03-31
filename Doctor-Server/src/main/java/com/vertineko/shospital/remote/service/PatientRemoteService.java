package com.vertineko.shospital.remote.service;


import com.vertineko.shospital.dto.doctor.req.DocCurrReservationPageDTO;
import com.vertineko.shospital.dto.doctor.req.DocReservationHisDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(name = "patient-server")
public interface PatientRemoteService {

    @RequestMapping("/patient/api/currReservation")
    String getDocReservationPage(@RequestBody DocCurrReservationPageDTO requestParam);


    @RequestMapping(method = RequestMethod.PUT, path = "/patient/api/reservation/{id}")
    String cancelReservation(@PathVariable Long id);

    @RequestMapping("/patient/api/hisReservation")
    String getDocReservationHisPage(DocReservationHisDTO requestParam);

}
