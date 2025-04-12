<template>
    <el-form ref="queryFormRef" :model="queryDepartment" style="display: flex;">
        <el-form-item label="科室名称：" prop="name">
            <el-input v-model="queryDepartment.name" style="width: 300px; margin-right: 20px;" />
        </el-form-item>
        <el-form-item>
            <el-icon @click="query(queryFormRef)" size="20">
                <Search />
            </el-icon>
            <el-icon size="20" @click="init()">
                <Refresh />
            </el-icon>
        </el-form-item>
    </el-form>
    <el-table ref="DepartmentTableRef" @row-click="selectDepartment" highlight-current-row :data="departments.records">
        <el-table-column prop="id" label="科室编号" />
        <el-table-column prop="name" label="科室名称" />
    </el-table>
    <div class="pagePlugin">
        <el-pagination v-model:current-page="queryDepartment.current" layout="prev, pager, next"
            :total="departments.total" @change="query(queryFormRef)" />
    </div>


    <el-drawer v-model="isDrawShow" :title="currRow.name" :direction="direction">
        <el-table :data="absDocList.records">
            <el-table-column  prop="id" v-if="false"/>
            <el-table-column label="姓名" prop="name" />


            <el-table-column label="挂号费" prop="price" />


            <el-table-column label="操作" >
                <template #default="scope">
                    <el-button type="primary" @click="reserve(scope.row.id)">挂号</el-button>
                </template>
            </el-table-column>

        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="drawRequest.current" layout="prev, pager, next"
                :total="absDocList.total" @change="query(queryFormRef)" />
        </div>
    </el-drawer>
</template>


<script setup lang="ts">
import { onMounted, reactive, ref } from 'vue';
import { patGetDepartmentPage, getAbsDoc, insertReservation } from '../../../request/api';
import { ElMessage, type DrawerProps, type FormInstance } from 'element-plus';


const queryFormRef = ref<FormInstance>()
const direction = ref<DrawerProps['direction']>('rtl')
const isDrawShow = ref(false)
const departments = reactive(
    {
        records: [{
            id: '',
            name: ''
        }],
        total: 1
    }
)

const currRow = reactive({
    id:'',
    name:''
})

const queryDepartment = reactive({
    name: '',
    current: '1',
    size: '10'
})

const drawRequest = reactive({
    departmentId:'',
    current:'1',
    size:'10'
})

const absDocList = reactive({
    records:[
        {
            id:'',
            name:'',
            price:''
        }
    ],
    total:1
})

const reserveData = reactive({
    department:'',
    doctorId:'',
})
onMounted(() => {
    init()
})

const reset = () => {
    queryDepartment.name = ''
    queryDepartment.current = '1'
    queryDepartment.size = '10'
}

const query = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await patGetDepartmentPage(queryDepartment);
            if (res.data.code === '200') {
                departments.records = res.data.data.records
                departments.total = res.data.data.total
                console.log(departments.records)
            } else {
                ElMessage.error(res.data.data)
            }
        }
    })
}

const init = async () => {
    reset()
    const res = await patGetDepartmentPage(queryDepartment);
    if (res.data.code === '200') {
        departments.records = res.data.data.records
        departments.total = res.data.data.total
        console.log(departments.records)
    } else {
        ElMessage.error(res.data.data)
    }
}
const selectDepartment = async (row:any) => {
    isDrawShow.value = true
    currRow.id = row.id
    currRow.name = row.name
    drawRequest.departmentId = currRow.id
    const res = await getAbsDoc(drawRequest)
    if (res.data.code === '200'){
        absDocList.records = res.data.data.records
        absDocList.total = res.data.data.total
    }else {
        ElMessage.error(res.data.data)
    }
    
}

const reserve = async (id:string) =>{
    reserveData.doctorId = id
    reserveData.department = currRow.name
    const res = await insertReservation(reserveData)
    if (res.data.code === '200'){
        ElMessage.success('预约成功！')
        isDrawShow.value = false
    }else {
        ElMessage.error(res.data.data)
    }
}

</script>


<style scoped lang="css">
.pagePlugin {
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}
</style>