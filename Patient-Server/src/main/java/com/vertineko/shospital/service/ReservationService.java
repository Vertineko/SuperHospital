package com.vertineko.shospital.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dto.patient.req.ReservationPageDTO;
import com.vertineko.shospital.dto.patient.res.ReservationPageVO;

public interface ReservationService {

    int insertReservation(InsertReservationDTO requestParam);

    int updateReservation(Long id);

    int deleteBatchReservation();

    ReservationDO getReservation(Long id);

    IPage<ReservationPageVO> getReservationPage(ReservationPageDTO requestParam);

    Integer cancelReservation(Long id);
}
