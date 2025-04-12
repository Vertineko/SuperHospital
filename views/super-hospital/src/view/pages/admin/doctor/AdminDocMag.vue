<template>
    <div class="view">
        
        <el-tag type="primary" size="large">医生管理</el-tag>
        <!-- 搜索输入区域 -->
        <div class="inputArea">
            <el-row>
            <el-form 
            ref="searchForm"
            style="display: flex;" 
            :model="requestParam"
            :rules="rules"
            >
                <el-form-item label="工号:" prop="username">
                    <el-input style="max-width: 80px;" v-model="requestParam.username"></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop="name">
                    <el-input style="max-width: 80px;" v-model="requestParam.name"></el-input>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input style="max-width: 50px;" v-model="requestParam.minAge"></el-input>

                    <el-input style="max-width: 50px;" v-model="requestParam.maxAge"></el-input>
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
                    <el-input style="max-width: 120px;" v-model="requestParam.tele"></el-input>
                </el-form-item>
                <el-form-item label="邮箱:" prop="mail">
                    <el-input style="max-width: 170px;" v-model="requestParam.mail"></el-input>
                </el-form-item>
                <el-form-item label="科室:" prop="department">
                    <el-select 
                    :empty-values="[null, undefined]"
                    :value-on-clear="null"
                    clearable
                    style="max-width: 80px;" 
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
            </el-row>
        </div>

        <el-table 
        :data="FormData.records" 
        style="width: 100%;"
        >
            <el-table-column prop="username" label="工号"  />
            <el-table-column prop="name" label="姓名"  />
            <el-table-column prop="age" label="年龄"  />
            <el-table-column prop="sex" label="性别"  >
                <template #default="scope">
                    {{ scope.row.sex === 'MALE' ? '男' : '女' }}
                </template>
            </el-table-column>
            <el-table-column prop="tele" label="电话"  />
            <el-table-column prop="mail" label="邮箱"  />
            <el-table-column prop="departmentName" label="科室"  />
            <el-table-column label="操作">
                <template style="display: block;" #default="scope">
                    <el-icon size="20" @click="view(scope.row.username)">
                        <View />
                    </el-icon>
                    <el-icon size="20" @click="mod(scope.row.username)">
                        <Edit />
                    </el-icon>
                    <el-icon size="20">
                        <CircleCloseFilled @click="curr = scope.row.username; isRemoveShow=true"/>
                    </el-icon>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next" :total="FormData.total" @change="search(searchForm)"/>
        </div>

        <!-- 删除对话框 -->
        <el-dialog v-model="isRemoveShow" title="删除患者" width="500" center :show-close="false" class="removeDialog">
            <div class="context">您真的要删除这个医生吗，该操作不可撤销！</div>
            <div class="act">
                
                <el-button type="primary" @click="remove(curr)">确认</el-button>
                <el-button type="danger" @click="isRemoveShow = false; curr = ''">取消</el-button>
                
                
            </div>
        </el-dialog>
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
/* .view{
    padding: 10 10;
} */
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

.el-dialog{
    .context{
        margin-bottom: 40px;
        display: flex;
        justify-content: center;
    }
    .act{
        display: flex;
        justify-content: center;
    }
}
</style>

<script setup lang="ts">

import { reactive, ref, onMounted } from 'vue';
import { AdminDocQuery, getAllDepartment, removeDocByUsername } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';
import { useRouter } from 'vue-router';


const router = useRouter()
const searchForm = ref<FormInstance>()
const isRemoveShow = ref(false)
const curr = ref('')
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
                departmentName: '',
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