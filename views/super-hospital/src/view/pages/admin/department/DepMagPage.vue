<template>
    
    <el-tag type="primary" size="large">科室管理</el-tag>
    <div class="showArea">
        <el-row>
            <el-form
            :model="queryParam"
            style="display: flex;">
                <el-form-item label="科室名称">
                    <el-input v-model="queryParam.name" style="margin-right: 10px;"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-icon @click="addIsShow = true" style="margin-right: 10px;">
                        <Plus />
                    </el-icon>
                    <el-button type="success" @click="getDepartments()">搜索</el-button>
                    <el-button type="primary" @click="reset()">重置</el-button>
                </el-form-item>
            </el-form>
        </el-row>
        <el-table
        :data="departments.records">
            <el-table-column label="科室编号" prop="id"/>
            
            <el-table-column label="科室名称" prop="name"/>
                
            <el-table-column label="操作">
                <template #default="scope">
                    <el-button type="primary" @click="modIsShow = true; FormData.id = scope.row.id ">修改</el-button>
                    <el-button type="danger" @click="removeIsShow = true; FormData.id = scope.row.id ">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="queryParam.current" layout="prev, pager, next" 
            :total="departments.total" @change="getDepartments()" />
        </div>
            
       

        <!-- 新增用对话框 -->
        <el-dialog 
        v-model="addIsShow" 
        title="新增科室" 
        width="500" 
        align-center
        :show-close="false">
            <el-form 
            ref="addFormRef" 
            :model="addFormData" 
            :rules="Rules">
                <el-form-item prop='name' label="科室名">
                    <el-input v-model="addFormData.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class="action">
                        <el-button type="danger" @click="addIsShow = false; addFormData.name = ''">取消</el-button>
                        <el-button type="primary" @click="add(addFormRef); addIsShow = false">确认</el-button>
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
                        <el-button type="primary" @click="mod(FormRef); modIsShow = false">确认</el-button>
                        <el-button type="danger" @click="modIsShow = false; FormData.name = ''">取消</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-dialog>
        

        <!-- 删除对话框 -->
        <el-dialog v-model="removeIsShow" title="删除科室" width="500" center :show-close="false" class="removeDialog">
            <div class="context">您真的要删除这个科室吗，该操作不可撤销！</div>
            <div class="act">
                <el-button type="primary" @click="remove(FormData.id)">确认</el-button>
                <el-button type="danger" @click="removeIsShow = false;">取消</el-button>
            </div>
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



.pagePlugin {
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
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
import { onMounted, reactive, ref } from 'vue';
import { removeDepartment, getDepartment, getDocByDepartment, addDepartment, updateDepartment, getAdminDepartmentPage } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';

const addFormRef = ref<FormInstance>()
const FormRef = ref<FormInstance>()
const addIsShow = ref(false)
const modIsShow = ref(false)
const removeIsShow = ref(false)

const departments = reactive({
    records: [
        {
            id: '',
            name: ''
        }
    ],
    total:1
})
const queryParam = reactive({
    name:'',
    current:'1',
    size:'10'
})

const doctors = reactive({
    records: [
        {
            username: '',
            name: '',
            tele: ''
        }
    ],
    total: 1
})
const requestParam = reactive({
    current: '1',
    size: '10',
    department: ''
})

const addFormData = reactive({
    name: ''
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
    const res = await getAdminDepartmentPage(queryParam)
    if (res.data.code == '200') {
        departments.records = res.data.data.records
        departments.total = res.data.data.total
        console.log('Department_data:' + departments.records)
    } else {
        ElMessage.error(res.data.data)
    }
}

const add = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await addDepartment(addFormData.name)
            if (res.data.code == '200') {
                ElMessage.success('新增科室成功')
                addFormData.name = ''
                getDepartments()
                init()
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
        FormData.id = ''
        removeIsShow.value = false
    } else {
        ElMessage.error(res.data.data)
    }
}

const reset = () =>{
    queryParam.name = ''
    queryParam.current = '1'
    queryParam.size = '10'
    init()
}
</script>