package com.vertineko.shospital.service;

import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dao.dto.req.InsertReservationDTO;
import com.vertineko.shospital.dao.dto.req.ReservationPageDTO;

public interface ReservationService {

    int insertReservation(InsertReservationDTO requestParam);

    int updateReservation(Long id);

    int deleteBatchReservation();

    ReservationDO getReservation(Long id);

    ReservationPageDTO getReservationPage(ReservationPageDTO requestParam);
}
