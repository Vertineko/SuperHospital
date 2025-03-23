
import { da } from "element-plus/es/locales.mjs"
import http from "./http"
//管理员接口
interface AdminLoginData{
    username: string
    password: string
}
export function AdminLogin(data: AdminLoginData){
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