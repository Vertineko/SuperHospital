<template>
    <span>科室管理</span>
    <div class="showArea">
        <el-row :gutter="0">
            <el-col :span="12">
                <el-card style="width: 90%;" shadow="always">
                    <span>科室</span>
                    <el-icon style="padding-left: 10px;" size="20" @click="addIsShow = true">
                        <Plus />
                    </el-icon>
                    <el-divider style="width: 100%;"></el-divider>
                    <el-table :data="departments.records" highlight-current-row @current-change="showDocs">
                        <el-table-column prop="id" label="编号"></el-table-column>
                        <el-table-column prop="name" label="科室名称"></el-table-column>
                        <el-table-column label="操作">
                            <template #default="scope">
                                <el-icon size="20" @click="modIsShow = true; FormData.id = scope.row.id; getDetil(scope.row.id)">
                                    <Edit />
                                </el-icon>
                                <el-icon size="20">
                                    <CircleCloseFilled @click="remove(scope.row.id)" />
                                </el-icon>
                            </template>
                        </el-table-column>
                    </el-table>
                </el-card>
            </el-col>
            <el-col :span="12">
                <el-card style="" shadow="always">
                    <span>所属医生</span>
                    <el-divider style="width: 100%;"></el-divider>
                    <el-table :data="doctors.records">
                        <el-table-column prop="username" label="用户名"></el-table-column>
                        <el-table-column prop="name" label="真实姓名"></el-table-column>
                        <el-table-column prop="tele" label="联系电话"></el-table-column>
                    </el-table>
                    <div class="pagePlugin">
                        <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next"
                            :total="doctors.total" @change="showDocs(requestParam)" />
                    </div>
                </el-card>
            </el-col>
        </el-row>

        <!-- 新增用对话框 -->
        <el-dialog 
        v-model="addIsShow" 
        title="新增科室" 
        width="500" 
        align-center
        :show-close="false">
            <el-form ref="FormRef" :model="FormData" :rules="Rules">
                <el-form-item prop='name' label="科室名">
                    <el-input v-model="FormData.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class="action">
                        <el-button type="danger" @click="addIsShow = false; FormData.name = ''">取消</el-button>
                        <el-button type="primary" @click="add(FormRef); addIsShow = false">确认</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-dialog>
        <!-- 修改用对话框 -->
        <el-dialog 
        v-model="modIsShow" 
        title="修改科室信息" 
        width="500" 
        align-center
        :show-close="false">
            <el-form ref="FormRef" :model="FormData" :rules="Rules">
                <el-form-item prop='name' label="科室名">
                    <el-input v-model="FormData.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class="action">
                        <el-button type="danger" @click="modIsShow = false; FormData.name = ''">取消</el-button>
                        <el-button type="primary" @click="mod(FormRef); modIsShow = false">确认</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>

</template>


<style scoped>
.showArea {
    width: 100%;
    height: 100%;
    overflow: scroll;
}

.action {
    width: 100%;
    display: flex;

    justify-content: center;
}

.el-card {
    width: 90%;
    height: 100%;
}

h2 {
    font-size: 24px;
}

.pagePlugin {
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}

.el-row {
    max-height: 80%;
}
</style>


<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { removeDepartment, getDepartment ,getAllDepartment, getDocByDepartment, addDepartment, updateDepartment } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';


const FormRef = ref<FormInstance>()
const addIsShow = ref(false)
const modIsShow = ref(false)

const departments = reactive({
    records: [
        {
            id: '',
            name: ''
        }
    ]
})

const doctors = reactive({
    records: [
        {
            username: '',
            name: '',
            tele: ''
        }
    ],
    total: ''
})
const requestParam = reactive({
    current: '1',
    size: '10',
    department: ''
})


const FormData = reactive({
    id: '',
    name: ''
})

const Rules = {
    name: [
        { min: 1, max: 64, message: "超过最大字符数限制！", trigger: 'blur' },
        { required: true, message: "科室名不能为空！", trigger: 'blur' }
    ]
}
onMounted(() => {
    init()
})

const showDocs = async (val: any | undefined) => {
    requestParam.department = val.id
    const res = await getDocByDepartment(requestParam)
    if (res.data.code == '200') {
        doctors.records = res.data.data.records
        doctors.total = res.data.data.total
    } else {
        ElMessage.error(res.data.data)
    }
}

const init = async () => {
    getDepartments()
}

const getDepartments = async () => {
    const res = await getAllDepartment()
    if (res.data.code == '200') {
        departments.records = res.data.data
        console.log('Department_data:' + departments.records)
    } else {
        ElMessage.error(res.data.data)
    }
}

const add = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await addDepartment(FormData.name)
            if (res.data.code == '200') {
                ElMessage.success('新增科室成功')
                FormData.name = ''
                getDepartments()
            } else {
                ElMessage.error(res.data.data)
            }
        }
    })
}

const mod = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            console.log(FormData.id)
            const res = await updateDepartment(FormData)
            if (res.data.code == '200') {
                ElMessage.success('修改科室成功')
                FormData.name = ''
                getDepartments()
            } else {
                ElMessage.error(res.data.data)
            }
        }
    })
}

const getDetil = async (id:string) =>{
    const res = await getDepartment(id)
    if (res.data.code == '200') {
        FormData.name = res.data.data.name
    } else {
        ElMessage.error(res.data.data)
    }
}

const remove = async (id:string) => {
    const res = await removeDepartment(id)
    if (res.data.code == '200') {
        ElMessage.success('删除成功！')
        getDepartments()
    } else {
        ElMessage.error(res.data.data)
    }
}
</script>