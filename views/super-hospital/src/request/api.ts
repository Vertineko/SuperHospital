
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
    department:string
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

export function getAbsDocDTO(data:getAbsDocDTO){
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

export function queryHisReservation(data:queryHisReservationDTO){
    return http({
        url:'/api/doctor/api/hisReservation',
        method:'post',
        data
    })
}

