<template>
    <div class="view">
        <h1 style="margin-left: 20px; padding-top: 20px;"> 医生管理</h1>
        <!-- 搜索输入区域 -->
        <div class="inputArea">
            <el-form 
            ref="searchForm"
            style="display: flex;" 
            :model="requestParam"
            :rules="rules"
            >
                <el-form-item label="工号:" prop="username">
                    <el-input style="width: 100px;" v-model="requestParam.username"></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop="name">
                    <el-input style="width: 100px;" v-model="requestParam.name"></el-input>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input style="width: 50px;" v-model="requestParam.minAge"></el-input>
                    <el-input style="width: 50px;" v-model="requestParam.maxAge"></el-input>
                </el-form-item>
                <el-form-item label="性别:" prop="sex">
                    <el-radio-group v-model="requestParam.sex">
                        <el-radio value="MALE">
                            男
                        </el-radio>
                        <el-radio value="FEMALE">
                            女
                        </el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="联系电话:" prop="tele">
                    <el-input v-model="requestParam.tele"></el-input>
                </el-form-item>
                <el-form-item label="邮箱:" prop="mail">
                    <el-input v-model="requestParam.mail"></el-input>
                </el-form-item>
                <el-form-item label="科室:" prop="department">
                    <el-select 
                    :empty-values="[null, undefined]"
                    :value-on-clear="null"
                    clearable
                    style="width: 100px;" 
                    placeholder="选择科室"
                    v-model="requestParam.department">
                        <el-option 
                        v-for="item in departments.records"
                        
                        :key="item.id"
                        :label="item.name"
                        :value="item.id">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-icon @click="search(searchForm)" size="20">
                        <Search />
                    </el-icon>
                    <el-icon size="20" @click="refresh()">
                        <Refresh />
                    </el-icon>
                    <el-icon size="20" @click="add()">
                        <Plus />
                    </el-icon>
                </el-form-item>

            </el-form>
        </div>

        <el-table 
        :data="FormData.records" 
        style="width: 100%;"
        >
            <el-table-column prop="username" label="工号" width="180" />
            <el-table-column prop="name" label="姓名" width="180" />
            <el-table-column prop="age" label="年龄" width="180" />
            <el-table-column prop="sex" label="性别" width="180" />
            <el-table-column prop="tele" label="电话" width="180" />
            <el-table-column prop="mail" label="邮箱" width="180" />
            <el-table-column prop="department" label="科室" width="180" />
            <el-table-column label="操作">
                <template style="display: block;" #default="scope">
                    <el-icon size="20" @click="view(scope.row.username)">
                        <View />
                    </el-icon>
                    <el-icon size="20" @click="mod(scope.row.username)">
                        <Edit />
                    </el-icon>
                    <el-icon size="20">
                        <CircleCloseFilled @click="remove(scope.row.username)"/>
                    </el-icon>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next" :total="FormData.total" @change="search(searchForm)"/>
        </div>
    </div>
</template>

<style lang="css" scoped>
.inputArea{
    width: 100%;
    display: flex;
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 10px;
}
.pagePlugin{
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}
.view{
    padding: 10 10;
}
.el-icon{
    margin-right: 10px; 
}
.el-input{
    max-width: 150px;
    margin: 0 10px 0 0 ;
    justify-content: center;
}
.el-radio-group{
    min-width: 150px;
}
.el-select {
    min-width: 150px;
    max-width: 200px;
    margin-right: 20px;
}
.el-form-item{
    margin-bottom: 0;
}
</style>

<script setup lang="ts">

import { reactive, ref, onMounted } from 'vue';
import { AdminDocQuery, getAllDepartment, removeDocByUsername } from '../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';
import { useRouter } from 'vue-router';


const router = useRouter()
const searchForm = ref<FormInstance>()
const FormData = reactive(
    {
        records:[
            {
                newflag: '',
                id: '',
                username: '',
                password: '',
                name: '',
                age: '',
                sex: '',
                tele: '',
                mail: '',
                worktime: '',
                department: '',
                role: ''
            }
        ],
        total: 1,
        current: 1
        
    }
)

const requestParam = reactive({
    username: '',
    name: '',
    maxAge: '',
    minAge: '',
    sex: 'NULL',
    tele: '',
    mail: '',
    worktime: '',
    department: '',
    current: '1',
    size: '10'

})
const rules = {

}
const departments = reactive(
    {
        records:[{
            id: '',
            name: ''
        }]
    }
)

onMounted(()=>{
    init(requestParam)     
})

const search = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) =>{
        if (valid){
            console.log(requestParam)
            const res = await AdminDocQuery(requestParam)
            FormData.records = res.data.data.records
            FormData.total = res.data.data.total
            FormData.current = res.data.data.current
        }
    })
}

const init = async(requestParam:any) => {
    const res1 = await AdminDocQuery(requestParam)
    FormData.records = res1.data.data.records
    FormData.total = res1.data.data.total

    const res2 = await getAllDepartment()
    departments.records = res2.data.data
}



const refresh = () =>{
    requestParam.username=''
    requestParam.name=''
    requestParam.maxAge=''
    requestParam.minAge=''
    requestParam.sex='NULL'
    requestParam.tele=''
    requestParam.mail=''
    requestParam.worktime=''
    requestParam.department=''
    requestParam.current='1'
    requestParam.size='10'
    init(requestParam)
}

const add = () =>{
    router.push("/AddDocPage")
}

const mod = (username:string) =>{
    router.push("/ModDocPage?username=" + username)
}

const view = (username:string) => {
    console.log(username);
    router.push("/DocDetailPage?username=" + username)
}

const remove = async (username:string) => {
    
    console.log("delete");
    const res = await removeDocByUsername(username)
    if (res.data.code == '200'){
        ElMessage.success('删除成功！')
        init(requestParam)
    }else {
        ElMessage.error(res.data.data)
    }
}
</script>