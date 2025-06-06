package com.vertineko.shospital.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dto.admin.req.AdminHisReservationDTO;
import com.vertineko.shospital.dto.admin.res.AdminHisReservationVO;
import com.vertineko.shospital.dto.doctor.req.DocCurrReservationPageDTO;
import com.vertineko.shospital.dto.doctor.req.DocReservationHisDTO;
import com.vertineko.shospital.dto.doctor.res.DocReservationHisVO;
import com.vertineko.shospital.dto.doctor.res.DocReservationPageVO;
import com.vertineko.shospital.dto.patient.req.ReservationPageDTO;
import com.vertineko.shospital.dto.patient.req.UpdReservationDTO;
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


    @RequestMapping("/patient/api/reservation/update")
    public Result<Integer> updateReservation(@RequestBody UpdReservationDTO requestParam){
        return Results.success(reservationService.updateReservation(requestParam));
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

    @RequestMapping("/patient/api/reservation/currReservation")
    public Result<IPage<DocReservationPageVO>> getDocReservationPage(@RequestBody DocCurrReservationPageDTO requestParam){
        return Results.success(reservationService.getDocReservationPage(requestParam));
    }

    @RequestMapping("/patient/api/reservation/hisReservation")
    public Result<IPage<DocReservationHisVO>> getDocReservationHisPage(@RequestBody DocReservationHisDTO requestParam){
        log.info("医生id:{}", requestParam.getId());
        return Results.success(reservationService.getDocReservationHisPage(requestParam));
    }

    @RequestMapping("/patient/api/reservation/isDelete")
    public long isDelete(@RequestBody DocReservationHisDTO requestParam){
        return reservationService.getDocReservationHisPage(requestParam).getTotal();
    }

    @RequestMapping("/patient/api/reservation/remove")
    public Result<Integer> deleteReservation(@RequestParam Long id){
        return Results.success(reservationService.deleteReservation(id));
    }


    /**
     * 管理员端获取历史预约列表
     *
     * @param requestParam
     * @return
     */
    @RequestMapping("/patient/api/reservation/allReservation")
    public Result<IPage<AdminHisReservationVO>> getAllHisReservationPage(@RequestBody AdminHisReservationDTO requestParam) {
        return Results.success(reservationService.getAllHisReservationPage(requestParam));
    }
}
