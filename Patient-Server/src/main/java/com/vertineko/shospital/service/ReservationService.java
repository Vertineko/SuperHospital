package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
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

public interface ReservationService {

    int insertReservation(InsertReservationDTO requestParam);

    Integer updateReservation(UpdReservationDTO requestParam);

    int deleteBatchReservation();

    ReservationDO getReservation(Long id);

    IPage<ReservationPageVO> getReservationPage(ReservationPageDTO requestParam);

    Integer cancelReservation(Long id);

    /**
     * 医生端使用获取当前待处理预约的列表
     * @param requestParam
     * @return
     */
    IPage<DocReservationPageVO> getDocReservationPage(DocCurrReservationPageDTO requestParam);

    /**
     * 医生端用于获取历史处理预约列表
     * @param requestParam
     * @return
     */
    IPage<DocReservationHisVO> getDocReservationHisPage(DocReservationHisDTO requestParam);

    /**
     * 管理员端获取历史预约列表
     * @param requestParam
     * @return
     */
    IPage<AdminHisReservationVO> getAllHisReservationPage(AdminHisReservationDTO requestParam);
}
