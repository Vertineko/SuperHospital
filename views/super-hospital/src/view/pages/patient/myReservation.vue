<template>
    <el-form ref="queryFormRef" :model="queryReservationDTO" style="display: flex;">
        <el-form-item label="科室名称：" prop="name">
            <el-input v-model="queryReservationDTO.department" style="width: 300px; margin-right: 20px;" />
        </el-form-item>
        <el-form-item label="预约状态：" prop="status">
            <el-select v-model="queryReservationDTO.status" placeholder="预约状态"
                style="width: 200px; margin-right: 25px;">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
            </el-select>
        </el-form-item>
        <el-form-item>
            <el-icon @click="query()" size="20">
                <Search />
            </el-icon>
            <el-icon size="20" @click="reset()">
                <Refresh />
            </el-icon>
        </el-form-item>
    </el-form>
    <el-table highlight-current-row :data="reservations.records">
        <el-table-column prop="id" label="预约编号" />
        <el-table-column prop="department" label="科室名称" />
        <el-table-column prop="name" label="医生" />
        <el-table-column prop="createTime" label="预约时间" />
        <el-table-column prop="status" label="状态">
            <template #default="scope">
                <el-tag type="primary">{{ assistStatus(scope.row.status) }}</el-tag>
            </template>
        </el-table-column>

        <el-table-column label="操作">
            <template #default="scope">
                <el-button type="primary" v-if="scope.row.status >= 2" @click="detail(scope.row.id, scope.row.recordId, scope.row.orderId)">查看病历详情</el-button>
                
                <el-button type="danger" v-if="scope.row.status === 0" @click="isCancel = true; curr=scope.row.id">取消</el-button>
            </template>
        </el-table-column>
        <el-table-column prop="recordId" v-if="false" />
        <el-table-column prop="orderId" v-if="false"/>
    </el-table>
    <div class="pagePlugin">
        <el-pagination v-model:current-page="queryReservationDTO.current" layout="prev, pager, next"
            :total="reservations.total" @change="query()" />
    </div>
    <!-- 确认取消预约对话框 -->
    <el-dialog v-model="isCancel" title="取消预约" width="500" center :show-close="false" class="cancelDialog">
        <div class="description">您真的要取消预约吗，该操作不可撤销！</div>
        <div class="action">
            <el-button type="primary" @click="cancel(curr)">确认</el-button>
            <el-button type="danger" @click="isCancel = false;">返回</el-button>
        </div>
    </el-dialog>


</template>


<script lang="ts" setup>
import { onMounted, reactive, ref } from 'vue';
import { pageReservation, cancelReservation } from '../../../request/api';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter()
const isCancel = ref(false)
const queryReservationDTO = reactive({
    department: '',
    status: '',
    current: 1,
    size: 10
})
const curr = ref('')
const options = [
    {
        value:'-1',
        label:'已取消'
    },
    {
        value:'0',
        label:'已预约'
    },
    {
        value:'1',
        label:'已接诊'
    },
    {
        value:'2',
        label:'已归档病历'
    },
    
]
const reservations = reactive({
    records: [
        {
            id: '',
            department: '',
            doctor: '',
            createTime: '',
            status: ''
        }
    ],
    total:1
})

onMounted(() =>{
    reset()
})

const clear = () =>{
    queryReservationDTO.department = ''
    queryReservationDTO.status = ''
    queryReservationDTO.current = 1
    queryReservationDTO.size = 10
}

const reset = () =>{
    clear()
    query()
}

const query = async () =>{
    const res = await pageReservation(queryReservationDTO)
    if (res.data.code === '200'){
        reservations.records = res.data.data.records
        reservations.total = res.data.data.total
        
    }else {
        ElMessage.error(res.data.data)
    }
}
const cancel = async (id:string) =>{
    const res = await cancelReservation(id)
    if (res.data.code === '200'){
        ElMessage.success('取消成功！')
        query()
        isCancel.value = false
    }else {
        ElMessage.error(res.data.data)
    }
}
const assistStatus = (status : number):string => {
    
    console.log(typeof(status))
    switch (status) {
        case -1:
            return '已取消';
        case 0:
            return '已预约';
        case 1:
            return '已接诊';
        case 2:
            return '已归档病历';
        case 3:
            return '已付款';
        default:
            // 处理未预期的状态值
            return '未知状态';
    }
};

const detail = (reservationId:string, recordId:string, orderId:string) => {
    router.push('/patientRecordDetail?reservationId=' + reservationId + '&' + 'recordId=' + recordId + '&' + 'orderId=' + orderId)
}
</script>



<style lang="css">
.pagePlugin {
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}

.cancelDialog {

    .description {
        width: 100%;
    }

    .action {
        margin-top: 50px;

    }
}
</style>