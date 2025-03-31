package com.vertineko.shospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dto.doctor.req.DocCurrReservationPageDTO;
import com.vertineko.shospital.dto.doctor.req.DocReservationHisDTO;
import com.vertineko.shospital.dto.doctor.res.DocReservationHisVO;
import com.vertineko.shospital.dto.doctor.res.DocReservationPageVO;
import com.vertineko.shospital.dto.patient.req.ReservationPageDTO;
import com.vertineko.shospital.dto.patient.res.ReservationPageVO;
import com.vertineko.shospital.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @RequestMapping("/patient/api/reservation/insert")
    public Result<Integer> insertReservation(@RequestBody InsertReservationDTO requestParam){
        return Results.success(reservationService.insertReservation(requestParam));
    }

    @Deprecated
    @RequestMapping("/patient/api/reservation/update/{id}")
    public Result<Integer> updateReservation(@PathVariable Long id){
        return Results.success(reservationService.updateReservation(id));
    }

    @RequestMapping("/patient/api/reservation/getById/{id}")
    public Result<ReservationDO> getReservation(@PathVariable Long id){
        return Results.success(reservationService.getReservation(id));
    }

    @RequestMapping("/patient/api/reservation/page")
    public Result<IPage<ReservationPageVO>> getReservationPage(@RequestBody ReservationPageDTO requestParam){
        return Results.success(reservationService.getReservationPage(requestParam));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/patient/api/reservation/{id}")
    public Result<Integer> cancelReservation(@PathVariable Long id){
        return Results.success(reservationService.cancelReservation(id));
    }

    @RequestMapping("/patient/api/currReservation")
    public Result<IPage<DocReservationPageVO>> getDocReservationPage(@RequestBody DocCurrReservationPageDTO requestParam){
        return Results.success(reservationService.getDocReservationPage(requestParam));
    }

    @RequestMapping("/patient/api/hisReservation")
    public Result<IPage<DocReservationHisVO>> getDocReservationHisPage(@RequestBody DocReservationHisDTO requestParam){
        log.info("医生id:{}", requestParam.getId());
        return Results.success(reservationService.getDocReservationHisPage(requestParam));
    }
}
