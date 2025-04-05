package com.vertineko.shospital.remote.service;


import com.vertineko.shospital.dto.doctor.req.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "doctor-server")
public interface DoctorRemoteService {

    //远程调用本就是http返回的已经是json了 不必再转化回类
    @RequestMapping("/doctor/api/insertDoctor")
    String insertDoctor(InsertDoctorDTO requestParam);

    @RequestMapping("/doctor/api/removeById/{id}")
    String removeById(@PathVariable Long id);

    @RequestMapping("/doctor/api/removeByUsername/{username}")
    String removeByUsername(@PathVariable String username);

    @RequestMapping("/doctor/api/updateById")
    String updateById(UpdateDoctorByIdDTO requestParam);

    @RequestMapping("/doctor/api/updateByUsername")
    String updateByUsername(UpdateDoctorByUsernameDTO requestParam);

    @RequestMapping("/doctor/api/getDocPage")
    String getDoctorPage(DoctorPageDTO requestParam);

    @RequestMapping("/doctor/api/getDocDetailByUsername")
    String getDocDetailByUsername(@RequestParam("username") String username);

    @RequestMapping("/doctor/api/getDocDepPageByDepId")
    String getDocDepPageByDepId(DocDepartPageDTO requestParam);

    @RequestMapping("/doctor/api/department/insert")
    String insertDepartment(String name);


    @RequestMapping(method = RequestMethod.DELETE, path = "/doctor/api/department/{id}")
    String deleteDepartment(@PathVariable Long id);


    @RequestMapping(method = RequestMethod.GET, path = "/doctor/api/department/{id}")
    String getDepartment(@PathVariable Long id);


    @RequestMapping("/doctor/api/department")
    String updateDepartment(@RequestParam("id") Long id, @RequestParam("name") String name);


    @RequestMapping("/doctor/api/department/getAll")
    String getAllDepartment();



    //药品模块
    @RequestMapping("/doctor/api/medicine/insertMedicine")
    String insertMedicine(@RequestBody InsertMedicineDTO requestParam);


    @RequestMapping("/doctor/api/medicine/getMedicinePage")
    String getMedicinePage(@RequestBody MedicinePageDTO requestParam);


    @RequestMapping("/doctor/api/medicine/getMedicineById/{id}")
    String getMedicineById(@PathVariable Long id);


    @RequestMapping("/doctor/api/medicine/removeMedicineById/{id}")
    String removeMedicineById(@PathVariable Long id);


    @RequestMapping("/doctor/api/medicine/updMedicineDTO")
    String updateMedicine(@RequestBody updMedicineDTO requestParam);


}
