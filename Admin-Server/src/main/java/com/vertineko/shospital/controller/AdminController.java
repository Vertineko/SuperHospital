package com.vertineko.shospital.controller;

import com.vertineko.shospital.constrain.Result;
import com.vertineko.shospital.constrain.Results;
import com.vertineko.shospital.dao.dto.req.AdminPageDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByIdDTO;
import com.vertineko.shospital.dao.dto.req.UpdateAdminByUsernameDTO;
import com.vertineko.shospital.dto.LoginDTO;
import com.vertineko.shospital.dto.admin.req.AdminHisReservationDTO;
import com.vertineko.shospital.dto.doctor.req.*;
import com.vertineko.shospital.dto.patient.req.InsertPatientDTO;
import com.vertineko.shospital.dto.patient.req.PatientPageDTO;
import com.vertineko.shospital.dto.patient.req.UpdatePatientByUsernameDTO;
import com.vertineko.shospital.remote.service.DoctorRemoteService;
import com.vertineko.shospital.remote.service.PatientRemoteService;
import com.vertineko.shospital.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController()
@Slf4j
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    private final DoctorRemoteService doctorRemoteService;

    private final PatientRemoteService patientRemoteService;


    @RequestMapping("/admin/api/login")
    public Result<String> login(@RequestBody LoginDTO requestParam) {
        return Results.success(adminService.login(requestParam));
    }

    @RequestMapping("/admin/api/removeById/{id}")
    public Result<Integer> removeById(@PathVariable Long id) {
        return Results.success(adminService.removeById(id));
    }

    @RequestMapping("/admin/api/removeByUsername/{username}")
    public Result<Integer> removeByUsername(@PathVariable String username) {
        return Results.success(adminService.removeByUsername(username));
    }

    @RequestMapping("/admin/api/updateById")
    public Result<Integer> updateById(@RequestBody UpdateAdminByIdDTO requestParam) {
        return Results.success(adminService.updateById(requestParam));
    }

    @RequestMapping("/admin/api/updateByUsername")
    public Result<Integer> updateByUsername(@RequestBody UpdateAdminByUsernameDTO requestParam) {
        return Results.success(adminService.updateByUsername(requestParam));
    }

    @RequestMapping("/admin/api/getAdminPage")
    public Result<AdminPageDTO> getAdminPage(@RequestBody AdminPageDTO requestParam) {
        return Results.success(adminService.getAdminPage(requestParam));
    }



    //以下都是医生端远程调用

    @RequestMapping("/admin/api/addDoc")
    public String addDoc(@RequestBody InsertDoctorDTO requestParam) {
        return doctorRemoteService.insertDoctor(requestParam);
    }

    @RequestMapping("/admin/api/removeDocById/{id}")
    public String removeDocById(@PathVariable Long id){
        return doctorRemoteService.removeById(id);
    }

    @RequestMapping("/admin/api/removeDocByUsername/{username}")
    public String removeDocByUsername(@PathVariable String username){
        return doctorRemoteService.removeByUsername(username);
    }

    @RequestMapping("/admin/api/updateDocById")
    public String updateDocById(@RequestBody UpdateDoctorByIdDTO requestParam){
        return doctorRemoteService.updateById(requestParam);
    }

    @RequestMapping("/admin/api/updateDocByUsername")
    public String updateDocByUsername(@RequestBody UpdateDoctorByUsernameDTO requestParam){
        return doctorRemoteService.updateByUsername(requestParam);
    }

    @RequestMapping("/admin/api/getDocPage")
    public String getDocPage(@RequestBody DoctorPageDTO requestParam){
        return doctorRemoteService.getDoctorPage(requestParam);
    }

    @RequestMapping("/admin/api/getDocDetailByUsername")
    public String getDocDetailByUsername(String username){
        return doctorRemoteService.getDocDetailByUsername(username);
    }

    @RequestMapping("/admin/api/getDocDepPageByDepId")
    public String getDocDetailByUsername(@RequestBody DocDepartPageDTO requestParam){
        return doctorRemoteService.getDocDepPageByDepId(requestParam);
    }


    //以下是患者端远程调用
    @RequestMapping("/admin/api/getPatientPage")
    public String pagePatient(@RequestBody PatientPageDTO requestParam){
        return patientRemoteService.pagePatient(requestParam);
    }

    @RequestMapping("/admin/api/addPatient")
    public String insertPatient(@RequestBody InsertPatientDTO requestParam){
        return patientRemoteService.insertPatient(requestParam);
    }

    @RequestMapping("/admin/api/removePatient")
    public String removePatient(@RequestParam("username") String username){
        return patientRemoteService.removeByUsername(username);
    }

    @RequestMapping("/admin/api/getPatient")
    public String getByUsername(@RequestParam("username") String username){
        return patientRemoteService.getByUsername(username);
    }

    @RequestMapping("/admin/api/updatePatient")
    public String updateByUsername(@RequestBody UpdatePatientByUsernameDTO requestParam){
        return patientRemoteService.updateByUsername(requestParam);
    }

    //科室远程调用

    @RequestMapping("/admin/api/department/insert")
    public String insertDepartment(String name){
        return doctorRemoteService.insertDepartment(name);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/api/department/{id}")
    public String deleteDepartment(@PathVariable Long id){
        return doctorRemoteService.deleteDepartment(id);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/admin/api/department/{id}")
    public String getDepartment(@PathVariable Long id){
        return doctorRemoteService.getDepartment(id);
    }

    @RequestMapping("/admin/api/department")
    public String updateDepartment(@RequestParam("id") Long id, @RequestParam("name") String name){
        return doctorRemoteService.updateDepartment(id, name);
    }

    @RequestMapping("/admin/api/department/getAll")
    public String getAllDepartment(){
        return doctorRemoteService.getAllDepartment();
    }

    //以下是药品远程调用
    @RequestMapping("/admin/api/medicine/insertMedicine")
    String insertMedicine(@RequestBody InsertMedicineDTO requestParam){
        return doctorRemoteService.insertMedicine(requestParam);
    }


    @RequestMapping("/admin/api/medicine/getMedicinePage")
    String getMedicinePage(@RequestBody MedicinePageDTO requestParam){
        return doctorRemoteService.getMedicinePage(requestParam);
    }


    @RequestMapping("/admin/api/medicine/getMedicineById/{id}")
    String getMedicineById(@PathVariable Long id){
        return doctorRemoteService.getMedicineById(id);
    }


    @RequestMapping("/admin/api/medicine/removeMedicineById/{id}")
    String removeMedicineById(@PathVariable Long id){
        return doctorRemoteService.removeMedicineById(id);
    }


    @RequestMapping("/admin/api/medicine/updMedicineDTO")
    String updateMedicine(@RequestBody updMedicineDTO requestParam){
        return doctorRemoteService.updateMedicine(requestParam);
    }

    /**
     * 管理员端获取历史预约列表
     *
     * @param requestParam
     * @return
     */
    @RequestMapping("/admin/api/reservation/allReservation")
    String getAllHisReservationPage(@RequestBody AdminHisReservationDTO requestParam){
        return patientRemoteService.getAllHisReservationPage(requestParam);
    }

}
