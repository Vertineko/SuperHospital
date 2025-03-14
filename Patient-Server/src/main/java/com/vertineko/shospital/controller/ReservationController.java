package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dao.dto.req.ReservationPageDTO;
import com.vertineko.shospital.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @RequestMapping("/patient/api/reservation/insert")
    public Result<Integer> insertReservation(@RequestBody InsertReservationDTO requestParam){
        return Results.success(reservationService.insertReservation(requestParam));
    }

    @RequestMapping("/patient/api/reservation/update/{id}")
    public Result<Integer> updateReservation(@PathVariable Long id){
        return Results.success(reservationService.updateReservation(id));
    }

    @RequestMapping("/patient/api/reservation/getById/{id}")
    public Result<ReservationDO> getReservation(@PathVariable Long id){
        return Results.success(reservationService.getReservation(id));
    }

    @RequestMapping("/patient/api/reservation/page")
    public Result<ReservationPageDTO> getReservationPage(@RequestBody ReservationPageDTO requestParam){
        return Results.success(reservationService.getReservationPage(requestParam));
    }
}
