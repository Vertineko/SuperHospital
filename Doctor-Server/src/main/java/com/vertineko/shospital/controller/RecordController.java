package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.dto.req.InsertRecordDTO;
import com.vertineko.shospital.service.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @RequestMapping("/doctor/api/record/insertRecord")
    public Result<Long> insertRecord(@RequestBody InsertRecordDTO requestParam){
        return Results.success(recordService.insertRecord((requestParam)));
    }

}
