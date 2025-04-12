<template>
    <div class="view">
        <el-tag type="primary" size="large">管理员信息</el-tag>
        <!-- 搜索输入区域 -->
        <div class="inputArea">
            <el-row>
            <el-form 
            ref="searchFormRef"
            style="display: flex;" 
            :model="requestParam"
            >
                <el-form-item label="用户名:" prop="username">
                    <el-input style="width: 300px;" v-model="requestParam.username"></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop="name">
                    <el-input style="width: 300px;" v-model="requestParam.name"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="tele">
                    <el-input style="width: 300px;" v-model="requestParam.tele"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-icon @click="search(searchFormRef)" size="20">
                        <Search />
                    </el-icon>
                    <el-icon size="20" @click="refresh()">
                        <Refresh />
                    </el-icon>
                    <el-icon size="20" @click="isAddshow = true">
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
            <el-table-column prop="username" label="用户名"  />
            <el-table-column prop="name" label="姓名"  />
            <el-table-column prop="tele" label="电话"  />
            <el-table-column label="操作">
                <template style="display: block;" #default="scope">  
                    <el-icon size="20" @click="modInit(scope.row.username)">
                        <Edit />
                    </el-icon>
                    <el-icon size="20">
                        <CircleCloseFilled @click="curr = scope.row.username; isRemoveShow = true"/>
                    </el-icon>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next" :total="FormData.total" @change="search(searchFormRef)"/>
        </div>

        <!-- 新增管理员对话框 -->
        <el-dialog v-model="isAddshow" title="新增管理员" width="500" center :show-close="false" class="addDialog">
            <el-form ref='addAdminRef' :model='addAdminForm' :rules='addAdminRules'>
                <el-form-item label="用户名:" prop='username'>
                    <el-input v-model="addAdminForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码:" prop='password'>
                    <el-input type="password" v-model="addAdminForm.password"></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop="name">
                    <el-input v-model="addAdminForm.name"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="tele">
                    <el-input v-model="addAdminForm.tele"></el-input>
                </el-form-item>
                <div class="act">
                    <el-form-item>
                        <el-button type="primary" @click="add(addAdminRef)">确认</el-button>
                        <el-button type="danger" @click="isAddshow = false; addFormClear()">取消</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>


        <!-- 修改管理员对话框 -->
        <el-dialog v-model="isModShow" title="修改管理员" width="500" center :show-close="false" class="addDialog">
            <el-form ref='modAdminRef' :model='modAdminForm.records' :rules='modAdminRules'>
                <el-form-item label="用户名:">
                    {{modAdminForm.records.username}}
                </el-form-item>
                <el-form-item label="密码:" prop='password'>
                    <el-input type="password" v-model="modAdminForm.records.password"></el-input>
                </el-form-item>
                <el-form-item label="姓名:" prop='name'>
                    <el-input v-model="modAdminForm.records.name"></el-input>
                </el-form-item>
                <el-form-item label="联系电话:" prop="tele">
                    <el-input v-model="modAdminForm.records.tele"></el-input>
                </el-form-item>
                
                <div class="act">
                    <el-form-item>
                        <el-button type="primary" @click="mod(modAdminRef)">确认</el-button>
                        <el-button type="danger" @click="isModShow = false; modFormClear()">取消</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>

        <!-- 删除管理员对话框 -->
        <el-dialog v-model="isRemoveShow" title="删除管理员" width="500" center :show-close="false" class="addDialog">
            <div class="context">确定要删除该管理员吗，此操作不可撤回!</div>
            <div class="act">
                <el-button type="primary" @click="remove(curr)">确认</el-button>
                <el-button type="danger" @click="isRemoveShow = false; curr = '';">取消</el-button>
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
import { addAdmin, getAdminPage, getAdmin, removeAdmin, updAdmin } from '../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';


const addAdminRef = ref<FormInstance>()
const modAdminRef = ref<FormInstance>()
const isAddshow = ref(false)
const isModShow = ref(false)
const isRemoveShow = ref(false)
const curr = ref('')
const searchFormRef = ref<FormInstance>()

const addAdminForm = reactive({
    username:'',
    password:'',
    name:'',
    tele:''
})

const modAdminForm = reactive({
    records:{
        username:'',
        password:'',
        name:'',
        tele:'',
    }
})

const addAdminRules = {
    username: [
        { required: true, message: '请填写用户名！', trigger: 'blur' }
    ],
    name: [
        { required: true, message: '请填写真实姓名！', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请填写密码！', trigger: 'blur' },
        { min:6, max: 12, message:'请输入6-12个字符的密码', trigger: 'blur'}
    ],
    tele: [
        { required: true, message: '请填写电话号码！', trigger: 'blur' }
    ]

}

const modAdminRules = {
    name: [
        { required: true, message: '请填写真实姓名！', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请填写密码！', trigger: 'blur' },
        { min:6, max: 12, message:'请输入6-12个字符的密码', trigger: 'blur'}
    ],
    tele: [
        { required: true, message: '请填写电话号码！', trigger: 'blur' }
    ]
}

const FormData = reactive(
    {
        records:[
            {
                username: '',
                name: '',
                tele: '',
            }
        ],
        total: 1,
        
    }
)

const requestParam = reactive({
    
    username: '',
    name:'',
    tele:'',
    current: 1,
    size: 10

})


onMounted(()=>{
    init(requestParam)
})

const search = (formEl: FormInstance | undefined) => {
    if (!formEl) return 
    formEl.validate(async (valid) =>{
        if (valid){
            const res = await getAdminPage(requestParam)
            if (res.data.code === '200'){
                FormData.records = res.data.data.records
                FormData.total = res.data.data.total
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}

const init = async (requestParam:any) => {
    const res = await getAdminPage(requestParam)
    if (res.data.code === '200'){
        FormData.records = res.data.data.records
        FormData.total = res.data.data.total
    }else {
        ElMessage.error(res.data.data)
    }
}



const refresh = () =>{
    requestParam.username=''
    requestParam.name=''
    requestParam.tele=''
    requestParam.current= 1
    requestParam.size= 10
    init(requestParam)
}

const add = async (formEl:FormInstance|undefined) =>{
    if (!formEl) return 
    formEl.validate(async (valid) => {
        if (valid){
            const res = await addAdmin(addAdminForm)
            if (res.data.code === '200'){
                ElMessage.success('新增成功！')
                isAddshow.value = false
                init(requestParam)
                addFormClear()
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}
const modInit = async (id:string) => {
    isModShow.value = true;
    const res = await getAdmin(id)
    if (res.data.code === '200'){
        modAdminForm.records = res.data.data
    }else{
        ElMessage.error(res.data.data)
    }
}
const mod = (formEl:FormInstance|undefined) =>{
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await updAdmin(modAdminForm.records)
            if (res.data.code === '200'){
                ElMessage.success("修改成功！")
                init(requestParam)
                isModShow.value = false
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}


const remove = async (id:string) => {
    const res = await removeAdmin(id)
    if (res.data.code === '200'){
        ElMessage.success("删除成功！")
        init(requestParam)
        isRemoveShow.value = false   
        curr.value = ''
    }else {
        ElMessage.error(res.data.data)
        isRemoveShow.value = false
        curr.value = ''
    }
    
}

const addFormClear = () =>{
    addAdminForm.name = ''
    addAdminForm.username = ''
    addAdminForm.tele = ''
    addAdminForm.password = ''

}

const modFormClear = () =>{
    modAdminForm.records.username = ''
    modAdminForm.records.password = ''
    modAdminForm.records.name = ''
    modAdminForm.records.tele = ''

}
</script>