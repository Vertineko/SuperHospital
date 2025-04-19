package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;
import com.vertineko.shospital.dto.doctor.req.updRecordDTO;
import com.vertineko.shospital.dto.doctor.res.RecordDetailVO;
import com.vertineko.shospital.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @RequestMapping("/doctor/api/record/insertRecord")
    public Result<Long> insertRecord(@RequestBody InsertRecordDTO requestParam){
        return Results.success(recordService.insertRecord((requestParam)));
    }


    @RequestMapping("/doctor/api/record/getRecordDetail/{id}")
    public Result<RecordDetailVO> getRecordDetail(@PathVariable Long id){
        return Results.success(recordService.getRecordDetail((id)));
    }

    @RequestMapping("/doctor/api/record/updateRecord")
    public Result<Integer> updateRecord(@RequestBody updRecordDTO requestParam){
        return Results.success(recordService.updateRecord((requestParam)));
    }

    @RequestMapping("/doctor/api/record/remove")
    public Result<Integer> removeRecord(Long id){
        return Results.success(recordService.removeRecord((id)));
    }

    @RequestMapping("/doctor/api/record/change")
    public Result<Integer> updateRecordsDoctor(@RequestParam("reservationId") Long reservationId,@RequestParam("doctorId") Long doctorId){
        return Results.success(recordService.updateRecordsDoctor(reservationId,doctorId));
    }
}
