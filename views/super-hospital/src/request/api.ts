
import http from "./http"
//管理员接口
interface LoginData{
    username: string
    password: string
}
export function AdminLogin(data: LoginData){
    return http({
        url : "/api/admin/api/login",
        method : "post",
        data
    })
}

interface modifyPasswordDTO{
    originPassword:string
    newPassword:string
}
export function modifyAdminPassword(data: modifyPasswordDTO){
    return http({
        url : "/api/admin/api/modifyPassword",
        method : "post",
        data
    })
}
export function modifyDoctorPassword(data: modifyPasswordDTO){
    return http({
        url : "/api/doctor/api/modifyPassword",
        method : "post",
        data
    })
}
export function modifyPatientPassword(data: modifyPasswordDTO){
    return http({
        url : "/api/patient/api/modifyPassword",
        method : "post",
        data
    })
}


interface AdminDocQuery{
    username: string,
    name: string,
    maxAge: string,
    minAge: string,
    sex: string,
    tele: string,
    mail: string,
    worktime: string,
    department: string,
    current: string,
    size: string

}
export function AdminDocQuery(data: AdminDocQuery){
    return http({
        url: "/api/admin/api/getDocPage",
        method: "post",
        data
    })
}

interface addAdminDTO{
    username:string
    password:string
    name:string
    tele:string
}

export function addAdmin(data:addAdminDTO){
    return http({
        url:'/api/admin/api/addAdmin',
        method:'post',
        data
    })
}

export function getAdminDepartmentPage(data:getDepartmentPageData){
    return http({
        url:'/api/admin/api/department/getDepartmentPage',
        method:'post',
        data
    })
}

export function getAdmin(data:string){
    return http({
        url:'/api/admin/api/getAdmin?username='+data,
        method:'get',
    })
}
interface updAdminDTO{
    username:string,
    password:string,
    name:string,
    tele:string
}
export function updAdmin(data:updAdminDTO){
    return http({
        url:'/api/admin/api/updateAdmin',
        method:'post',
        data
    })
}

export function removeAdmin(data:string){
    return http({
        url:'/api/admin/api/removeByUsername/' + data,
        method:'put'
    })
}

interface AdminPageDTO{
    username:string
    name:string
    tele:string
    current:number
    size:number
}

export function getAdminPage(data:AdminPageDTO){
    return http({
        url: '/api/admin/api/getAdminPage',
        method:'post',
        data
    })
}

export function getAllDepartment(){
    return http({
        url: "/api/admin/api/department/getAll",
        method: "get",
    })
}

interface addDocParam{
    username:string,
    password:string,
    name:string,
    worktime:string,
    department:string,
    price:string
}

export function addDoc(data:addDocParam){
    return http({
        url: "/api/admin/api/addDoc",
        method: "post",
        data
    })
}

export function getDocDetail(data:string){
    return http({
        url: "/api/admin/api/getDocDetailByUsername?username=" + data,
        method: "get",
        
    })
}

export function getDepartmentById(data:string) {
    return http({
        url: "/api/admin/api/department/" + data,
        method: "get"
    })
}

interface updateDoc{
    username:string
    password:string
    name:string
    age:string
    sex:string
    tele:string
    mail:string
    worktime:string
    department:string
}

export function updateDoc(data:updateDoc){
    return http({
        url: "/api/admin/api/updateDocByUsername",
        method: "post",
        data
    })
}

export function removeDocByUsername(data:string) {
    return http({
        url: "/api/admin/api/removeDocByUsername/" + data,
        method: "delete"
    })
}

interface DocQueryByDepDTO{
    department:string,
    current:string,
    size:string
}

export function getDocByDepartment(data:DocQueryByDepDTO){
    return http({
        url: "/api/admin/api/getDocDepPageByDepId",
        method: "post",
        data
    })
}

export function addDepartment(data:string){
    return http({
        url: "/api/admin/api/department/insert?name=" + data,
        method: "put"
    })
}

interface modDepartmentDTO{
    id:string,
    name:string
}

export function updateDepartment(data:modDepartmentDTO){
    return http({
        url: "/api/admin/api/department",
        method: "put",
        params:{id:data.id, name:data.name}
    })
}

export function getDepartment(data:string) {
    return http({
        url: "/api/admin/api/department/" + data,
        method: "get",
    })
}

export function removeDepartment(data:string) {
    return http({
        url: "/api/admin/api/department/" + data,
        method: "delete",
    })
}

interface getDepartmentPageData{
    name:string
    current:string
    size:string
}

export function getDepartmentPage(data:getDepartmentPageData){
    return http({
        url:'/api/doctor/api/department/getPage',
        method:'post',
        data
    })
}

export function patGetDepartmentPage(data:getDepartmentPageData){
    return http({
        url:'/api/patient/api/department/getPage',
        method:'post',
        data
    })
}

interface PatientQueryData{
    username:string
    name:string
    sex:string
    minAge:string
    maxAge:string
    tele:string
    current:string
    size:string
}

export function PatientsQuery(data: PatientQueryData){
    return http({
        url: '/api/admin/api/getPatientPage',
        method: 'post',
        data
    })
}

interface addPatientData{
    name:string
    username:string
    password:string
    sex:string
    tele:string
    age:string
}

export function addPatient(data: addPatientData){
    return http({
        url:'/api/admin/api/addPatient',
        method:'put',
        data
    })
}

export function patientRegister(data: addPatientData){
    return http({
        url:'/api/patient/api/insertPatient',
        method:'put',
        data
    })
}

export function removePatient(data: string){
    return http({
        url:'/api/admin/api/removePatient?username=' + data,
        method:'delete',
    })
}

export function getPatient(data:string) {
    return http({
        url:'/api/admin/api/getPatient?username=' + data,
        method:'get'
    })
}

interface modPatientData{
    username:string
    name:string
    password:string
    sex:string
    age:string
    tele:string
}

export function updatePatient(data:modPatientData){
    return http({
        url:'/api/admin/api/updatePatient',
        method:'put',
        data
    })
}

export function PatientLogin(data: LoginData){
    return http({
        url : "/api/patient/api/login",
        method : "post",
        data
    })
}


export function DoctorLogin(data: LoginData){
    return http({
        url : "/api/doctor/api/login",
        method : "post",
        data
    })
}

interface getAbsDocDTO{
    departmentId:string
    current:string
    size:string
}

export function getAbsDoc(data:getAbsDocDTO){
    return http({
        url: '/api/patient/api/getDocAbsPage',
        method: 'post',
        data
    })
}

interface insertReservationDTO{
    department:string
    doctorId:string
}

export function insertReservation(data:insertReservationDTO){
    return http({
        url:'/api/patient/api/reservation/insert',
        method:'post',
        data
    })
}

interface pageReservationDTO{
    department:string
    status:string
    current: number
    size:number
}

export function pageReservation(data:pageReservationDTO){
    return http({
        url:'/api/patient/api/reservation/page',
        method:'post',
        data
    })
}

export function cancelReservation(data:string){
    return http({
        url:'/api/patient/api/reservation/' + data,
        method:'put',
    })
}

export function docCancelReservation(data:string){
    return http({
        url:'/api/doctor/api/reservation/' + data,
        method:'put',
    })
}

interface queryCurrReservationDTO{
    patientName:string
    minCreateTime:string
    maxCreateTime:string
    current:number
    size:number
}

export function queryCurrReservation(data:queryCurrReservationDTO){
    return http({
        url:'/api/doctor/api/currReservation',
        method:'post',
        data
    })
}

interface queryHisReservationDTO{
    patientName:string
    minCreateTime:string
    maxCreateTime:string
    status:string
    current:number
    size:number
}

interface queryAllReservationDTO{
    patientName:string
    doctorName:string
    minCreateTime:string
    maxCreateTime:string
    status:string
    current:number
    size:number
}
export function queryHisReservation(data:queryHisReservationDTO){
    return http({
        url:'/api/doctor/api/hisReservation',
        method:'post',
        data
    })
}

export function queryAllHisReservation(data:queryAllReservationDTO){
    return http({
        url:'/api/admin/api/reservation/allReservation',
        method:'post',
        data
    })
}

interface insertMedicineDTO{
    name:string
    form:string
    note:string
    price:string
    count:string
}

export function insertMedicine(data:insertMedicineDTO){
    return http({
        url:'/api/admin/api/medicine/insertMedicine',
        method:'post',
        data
    })
}

interface getMedicinePageDTO{
    name:string,
    current:number,
    size:number
}

export function getMedicinePage(data:getMedicinePageDTO){
    return http({
        url:'/api/admin/api/medicine/getMedicinePage',
        method:'post',
        data
    })
}

export function getMedicineById(data:string){
    return http({
        url:'/api/admin/api/medicine/getMedicineById/' + data,
        method:'get'
    })
}

export function removeMedicineById(data:string){
    return http({
        url:'/api/admin/api/medicine/removeMedicineById/' + data,
        method:'put'
    })
}

interface updMedicineDTO{
    id:string
    name:string
    form:string
    note:string
    price:string
    count:string
}

export function updateMedicine(data:updMedicineDTO){
    return http({
        url:'/api/admin/api/medicine/updMedicineDTO',
        method:'put',
        data
    })
}

interface InsertRecordDTO{
    reservationId:string,
    patientId:string
}

export function insertRecord(data:InsertRecordDTO){
    return http({
        url:'/api/doctor/api/record/insertRecord',
        method:'post',
        data
    })
}

export function getRecordDetails(data:string){
    return http({
        url:'/api/doctor/api/record/getRecordDetail/' + data,
        method:'get',
    })
}

export function getMedicines(){
    return http({
        url:'/api/doctor/api/medicine/getMedicines',
        method:'post'
    })
}
interface medicines{
    id:string
    count:number
}

interface updateRecordDTO{
    id:string
    reservationId:string
    epidemic:string
    conditions:string
    checks:string
    assistantCheck:string
    handle:string
    advice:string
    medicinesVOList:Array<medicines>
}

export function updateRecord1(data:updateRecordDTO){
    return http({
        url:'/api/doctor/api/record/updateRecord',
        method:'post',
        data
    })
}


export function getOrders(data:string){
    return http({
        url:'/api/doctor/api/order/getOrders/' + data,
        method:'get'
    })
}