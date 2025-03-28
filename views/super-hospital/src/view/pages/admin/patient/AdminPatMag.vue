<template>
    <div class="view">
        <span> 患者管理</span>
        <!-- 搜索输入区域 -->
        <div class="inputArea">
            <el-form ref="searchForm" style="display: flex;" :model="requestParam" :rules="rules">
                <el-form-item label="用户名:" prop="username">
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

                <el-form-item>
                    <el-icon @click="search(searchForm)" size="20">
                        <Search />
                    </el-icon>
                    <el-icon size="20" @click="refresh()">
                        <Refresh />
                    </el-icon>
                    <el-icon size="20" @click="isAddshow = true;">
                        <Plus />
                    </el-icon>
                </el-form-item>

            </el-form>
        </div>

        <el-table :data="FormData.records" style="width: 100%;">
            <el-table-column prop="username" label="用户名" width="180" />
            <el-table-column prop="name" label="姓名" width="180" />
            <el-table-column prop="age" label="年龄" width="180" />
            <el-table-column prop="sex" label="性别" width="180" />
            <el-table-column prop="tele" label="电话" width="180" />
            <el-table-column label="操作">
                <template style="display: block;" #default="scope">
                    <el-icon size="20" @click="view(scope.row.username)">
                        <View />
                    </el-icon>
                    <el-icon size="20" @click="currentRow.currentItem = scope.row.username; modInit(scope.row.username)">
                        <Edit />
                    </el-icon>
                    <el-icon size="20">
                        <CircleCloseFilled @click="currentRow.currentItem = scope.row.username; isRemoveShow = true" />
                    </el-icon>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next"
                :total="FormData.total" @change="search(searchForm)" />
        </div>

        <!-- 新增对话框 -->
        <el-dialog v-model="isAddshow" title="新增患者" width="500" center :show-close="false" class="addDialog">
            <el-form ref='addPatientFormRef' :model='addPatientForm' :rules='addPatientRules'>
                <el-form-item label="姓名:" prop='name'>
                    <el-input v-model="addPatientForm.name"></el-input>
                </el-form-item>
                <el-form-item label="用户名:" prop='username'>
                    <el-input v-model="addPatientForm.username"></el-input>
                </el-form-item>

                <el-form-item label="密码:" prop="password">
                    <el-input type="password" v-model="addPatientForm.password"></el-input>
                </el-form-item>
                <el-form-item label="性别:" prop="sex">
                    <el-radio-group v-model="addPatientForm.sex">
                        <el-radio value="MALE">男</el-radio>
                        <el-radio value="FEMALE">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input v-model="addPatientForm.age"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="tele">
                    <el-input v-model="addPatientForm.tele"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="add(addPatientFormRef)">确认</el-button>
                    <el-button type="danger" @click="isAddshow = false; addFormClear()">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 删除对话框 -->
        <el-dialog v-model="isRemoveShow" title="删除患者" width="500" center :show-close="false" class="removeDialog">
            <div class="description">您真的要删除这个病人信息吗，该操作不可撤销！</div>
            <div class="action">
                <el-button type="primary" @click="remove(currentRow.currentItem)">确认</el-button>
                <el-button type="danger" @click="isRemoveShow = false;">取消</el-button>
            </div>
        </el-dialog>


        <!-- 详细对话框 -->
        <el-dialog v-model="isViewShow" title="患者详情" width="700" center :show-close="false" class="viewDialog">
            <el-divider></el-divider>
            <div class="description">
                <el-descriptions>
                    <el-descriptions-item label="患者编号">{{ PatientDetail.record.id }}</el-descriptions-item>
                    <el-descriptions-item label="姓名">{{ PatientDetail.record.name }}</el-descriptions-item>
                    <el-descriptions-item label="用户名">{{ PatientDetail.record.username }}</el-descriptions-item>
                    <el-descriptions-item label="性别">{{ PatientDetail.record.sex == 'MALE' ?
                        '男':'女'}}</el-descriptions-item>
                    <el-descriptions-item label="年龄">{{ PatientDetail.record.age }}</el-descriptions-item>
                    <el-descriptions-item label="电话">{{ PatientDetail.record.tele }}</el-descriptions-item>
                </el-descriptions>

            </div>
            <div class="action">
                <el-button type="primary" @click="isViewShow = false">后退</el-button>
            </div>
        </el-dialog>
        <!-- 修改对话框 -->
        <el-dialog v-model="isModShow" title="修改患者信息" width="500" center :show-close="false" class="modDialog">
            <el-form 
            ref='ModFormRef' 
            :model='ModForm.record' 
            :rules='addPatientRules'>
                <el-form-item label="姓名:" prop='name'>
                    <el-input v-model="ModForm.record.name"></el-input>
                </el-form-item>
                用户名: {{ currentRow.currentItem }}

                <el-form-item label="密码:" prop="password">
                    <el-input type="password" v-model="ModForm.record.password"></el-input>
                </el-form-item>
                <el-form-item label="性别:" prop="sex">
                    <el-radio-group v-model="ModForm.record.sex">
                        <el-radio value="MALE">男</el-radio>
                        <el-radio value="FEMALE">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input v-model="ModForm.record.age"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="tele">
                    <el-input v-model="ModForm.record.tele"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="mod(ModFormRef)">确认</el-button>
                    <el-button type="danger" @click="isModShow = false; modFormClear()">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<style lang="css" scoped>
.inputArea {
    width: 100%;
    display: flex;
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 10px;
}

.pagePlugin {
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}

.el-icon {
    margin-right: 10px;
}

.el-input {
    max-width: 150px;
    margin: 0 10px 0 0;
    justify-content: center;
}

.el-radio-group {
    min-width: 150px;
}

.el-select {
    min-width: 150px;
    max-width: 200px;
    margin-right: 20px;
}

.addDialog {
    display: flex;

    .el-form-item {
        margin-bottom: 15px;

    }
}

.removeDialog {
    display: flex;
    flex-direction: row;

    .description {
        width: 100%;
    }

    .action {
        margin-top: 50px;

    }
}

.viewDialog {
    display: block;
}

.modDialog {
    .el-form-item {
        margin-top: 10px;
    }
}
</style>

<script setup lang="ts">

import { reactive, ref, onMounted } from 'vue';
import { PatientsQuery, addPatient, getPatient, removePatient, updatePatient } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';


const searchForm = ref<FormInstance>()
const addPatientFormRef = ref<FormInstance>()
const ModFormRef = ref<FormInstance>()
const isAddshow = ref(false)
const isRemoveShow = ref(false)
const isViewShow = ref(false)
const isModShow = ref(false)
const currentRow = reactive({
    currentItem: ''
})

const FormData = reactive(
    {
        records: [
            {
                username: '',
                name: '',
                age: '',
                sex: '',
                tele: ''
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
    current: '1',
    size: '10'

})
const rules = {

}

const addPatientRules = {
    name: [
        { required: true, message: '请填写用户名！', trigger: 'blur' }
    ],
    username: [
        { required: true, message: '请填写用户名！', trigger: 'blur' },
        { min: 5, max: 12, message: '请输入长度为5-12之间的用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请填写密码！', trigger: 'blur' },
        { min: 6, max: 12, message: '请输入长度为5-12之间的密码', trigger: 'blur' }
    ],
    sex: [
        { required: true, message: '请选择性别', trigger: 'blur' }
    ],
    age: [
        { required: true, message: '请填写年龄！', trigger: 'blur' },
        { min: 1, max: 3, message: '请输入正确的年龄', trigger: 'blur' }
    ],
    tele: [
        { required: true, message: '请填写联系电话！', trigger: 'blur' },
        { min: 11, max: 11, message: '请输入正确的电话号码！', trigger: 'blur' }
    ]
}

const addPatientForm = reactive({
    username: '',
    password: '',
    name: '',
    age: '',
    sex: 'NULL',
    tele: ''
})

const ModForm = reactive({
    record: {
        username:'',
        name: '',
        password: '',
        sex: 'NULL',
        age: '',
        tele: ''
    }
})



onMounted(() => {
    init(requestParam)
})

const addFormClear = () => {
    addPatientForm.username = ''
    addPatientForm.password = ''
    addPatientForm.name = ''
    addPatientForm.sex = 'NULL'
    addPatientForm.age = ''
    addPatientForm.tele = ''
}

const search = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log(requestParam)
            const res = await PatientsQuery(requestParam)
            FormData.records = res.data.data.records
            FormData.total = res.data.data.total
            FormData.current = res.data.data.current
        }
    })
}

const PatientDetail = reactive({
    record: {
        id: '',
        name: '',
        username: '',
        sex: '',
        age: '',
        tele: ''
    }
})

const init = async (requestParam: any) => {
    const res1 = await PatientsQuery(requestParam)
    FormData.records = res1.data.data.records
    FormData.total = res1.data.data.total

}


const refresh = () => {
    requestParam.username = ''
    requestParam.name = ''
    requestParam.maxAge = ''
    requestParam.minAge = ''
    requestParam.sex = 'NULL'
    requestParam.tele = ''
    requestParam.current = '1'
    requestParam.size = '10'
    init(requestParam)
}

const add = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await addPatient(addPatientForm)
            if (res.data.code == '200') {
                ElMessage.success('新增用户成功！')
                addFormClear()
                init(requestParam)
                isAddshow.value = false
            } else {
                ElMessage.error(res.data.data)
            }
        }
    })
}
const modInit = async (username: string) => {
    isModShow.value = true
    const res = await getPatient(username)
    if (res.data.code == '200') {
        ModForm.record = res.data.data
    } else {
        ElMessage.error(res.data.data)
    }
}

const mod = (formEl: FormInstance | undefined) => {
    if (!formEl) return 
    formEl.validate(async (valid) =>{
        if (valid) {
            const res = await updatePatient(ModForm.record)
            if (res.data.code == '200'){
                ElMessage.success('修改成功！');
                init(requestParam)
                isModShow.value = false;
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
    isModShow.value = true
}

const modFormClear = () => {
    ModForm.record.name = ''
    ModForm.record.password = ''
    ModForm.record.sex = 'NULL'
    ModForm.record.age = ''
    ModForm.record.tele = ''
}

const view = async (username: string) => {
    isViewShow.value = true
    const res = await getPatient(username)
    if (res.data.code == '200') {
        PatientDetail.record = res.data.data
    } else {
        ElMessage.error(res.data.data)
    }
}

const remove = async (username: string) => {
    console.log(username)
    const res = await removePatient(username)
    if (res.data.code == '200') {
        ElMessage.success('删除患者信息成功！')
        init(requestParam)
        isRemoveShow.value = false
    } else {
        ElMessage.error(res.data.data)
    }
}
</script>