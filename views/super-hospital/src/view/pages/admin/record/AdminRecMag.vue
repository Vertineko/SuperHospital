<template>
    <div class="show">
        <el-tag type="primary" size="large">预约/病历管理</el-tag>
        <el-form :model="searchForm">
            <el-row>
                <el-form-item 
                label="患者姓名:" 
                prop="patientName">
                    <el-input v-model="searchForm.patientName" />
                </el-form-item>
                <el-form-item 
                label="医生姓名:" 
                prop="doctorName">
                    <el-input v-model="searchForm.doctorName" />
                </el-form-item>
                <el-form-item label="预约日期:">
                    <el-date-picker 
                    @change="setDate()"
                    v-model="date" 
                    type="datetimerange"
                    start-placeholder="开始时间" 
                    end-placeholder="结束时间" 
                    format="YYYY-MM-DD HH:mm:ss"
                    value-format="YYYY-MM-DD HH:mm:ss"
                    date-format="YYYY/MM/DD ddd" 
                    time-format="A hh:mm:ss" />
                </el-form-item>
                <el-form-item label="状态:" >
                    <el-select v-model="searchForm.status" placeholder="预约状态"
                        style="width: 200px; margin-right: 25px;">
                        <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value" />
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="success" @click="query()">搜索</el-button>
                    <el-button type="primary" @click="reset()">重置</el-button>
                </el-form-item>
            </el-row>
        </el-form>
        <el-table
        :data="reservationData.records">
            <el-table-column label="预约编号" prop="id"/>
            <el-table-column label="患者姓名" prop="pname"/>
            <el-table-column label="医生姓名" prop="dname"/>
            <el-table-column label="预约时间" prop="createTime"/>
            <el-table-column label="预约状态" prop="status">
                <template #default="scope">
                    <el-tag type="primary">{{ assistStatus(scope.row.status) }}</el-tag>
                </template>
            </el-table-column>
            <el-table-column label="操作" >
                
                <template #default="scope">
                    <el-button type="primary" v-if="scope.row.status >= 2" @click="detail(scope.row.id, scope.row.recordId, scope.row.orderId)">查看详情</el-button>
                </template>
            </el-table-column>
            <el-table-column  prop="recordId" v-if="false"/>
            <el-table-column  prop="orderId" v-if="false"/>
            
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="searchForm.current" layout="prev, pager, next"
                :total="reservationData.total" @change="query()" />
        </div>
        <!-- 查看预约对话框 -->
        <el-dialog v-model="isCancel" title="取消预约" width="500" center :show-close="false" class="cancelDialog">
            <div class="description">
                确定要取消该预约吗，此操作不可撤回！
            </div>
            <div class="action">
                <el-button type="primary" @click="cancelReserve()">确认</el-button>
                <el-button type="danger" @click="isCancel = false;">返回</el-button>
            </div>
        </el-dialog>
    </div>

</template>


<script setup lang="ts">

import { onMounted, reactive, ref } from 'vue';
import { queryAllHisReservation, docCancelReservation } from '../../../../request/api';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter()
const curr = ref('')
const isCancel = ref(false)
const searchForm = reactive({
    patientName: '',
    doctorName:'',
    minCreateTime: '',
    maxCreateTime: '',
    status:'',
    current:1,
    size:10
})


const reservationData = reactive({
    records:[
        {
            id:'',
            pname:'',
            dname:'',
            recordId:'',
            orderId:'',
            createTime:'',
            status:''
        }
    ],
    total:1
})

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
    {
        value:'3',
        label:'已付款'
    },
]

onMounted(() =>{
    reset()
})

const reset = () =>{
    clear()
    query()
    
}

const clear = () =>{
    searchForm.patientName = ''
    searchForm.doctorName = ''
    searchForm.maxCreateTime = ''
    searchForm.minCreateTime = ''
    searchForm.status = ''
    date.value = ['']
}

const date = ref([''])

const query = async () =>{
    const res = await queryAllHisReservation(searchForm);
    if (res.data.code === '200'){
        
        reservationData.records = res.data.data.records
        reservationData.total = res.data.data.total
    }else {
        ElMessage.error(res.data.data)
    }
}

const setDate = ()=>{
    searchForm.minCreateTime = date.value[0]
    searchForm.maxCreateTime = date.value[1]
    
}

const cancelReserve = async () =>{
    const res = await docCancelReservation(curr.value)
    if (res.data.code === '200'){   
        query()
        ElMessage.success('取消预约成功')
        isCancel.value = false
    }else {
        ElMessage.error(res.data.data)
        isCancel.value = false
    }
    
}

const assistStatus = (status : number):string => {
    
    
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
    router.push('/recordDetail?reservationId=' + reservationId + '&' + 'recordId=' + recordId + '&' + 'orderId=' + orderId)
}
</script>


<style scoped lang="css">

.el-input{
    margin-right: 3px;
}
</style>