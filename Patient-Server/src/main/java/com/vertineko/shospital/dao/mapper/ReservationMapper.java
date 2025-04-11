package com.vertineko.shospital.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.vertineko.shospital.dao.ReservationDO;
import com.vertineko.shospital.dto.admin.req.AdminHisReservationDTO;
import com.vertineko.shospital.dto.admin.res.AdminHisReservationVO;
import com.vertineko.shospital.dto.doctor.req.DocCurrReservationPageDTO;
import com.vertineko.shospital.dto.doctor.req.DocReservationHisDTO;
import com.vertineko.shospital.dto.doctor.res.DocReservationHisVO;
import com.vertineko.shospital.dto.doctor.res.DocReservationPageVO;
import com.vertineko.shospital.dto.patient.req.ReservationPageDTO;
import com.vertineko.shospital.dto.patient.res.ReservationPageVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReservationMapper extends BaseMapper<ReservationDO> {

    IPage<ReservationPageVO> getReservationPage(ReservationPageDTO requestParam);

    IPage<DocReservationPageVO> getCurrReservationPage(DocCurrReservationPageDTO requestParam);

    IPage<DocReservationHisVO> getDocHisReservationPage(DocReservationHisDTO requestParam);

    IPage<AdminHisReservationVO> getAllHisReservationPage(AdminHisReservationDTO requestParam);
}
