<template>
    <div class="show">
        当前待处理预约
        <el-form :model="searchForm">
            <el-row>
                <el-form-item 
                label="患者姓名:" 
                prop="patientName">
                    <el-input v-model="searchForm.patientName" />
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
                <el-form-item>
                    <el-button type="success" @click="query()">搜索</el-button>
                    <el-button type="primary" @click="reset()">重置</el-button>
                </el-form-item>
            </el-row>
        </el-form>
        <el-table
        :data="reservationData.records">
            <el-table-column label="预约编号" prop="id"/>
            <el-table-column label="患者姓名" prop="name"/>
            <el-table-column label="预约时间" prop="createTime"/>
            <el-table-column label="操作" >
                <template #default="scope">
                    <el-button type="primary" @click="accept()">接受预约</el-button>
                    <el-button type="danger" @click="isCancel=true; curr=scope.row.id;">取消</el-button>
                </template>
            </el-table-column>
            
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
import { queryCurrReservation, docCancelReservation } from '../../../request/api';
import { ElMessage } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter()
const curr = ref('')
const isCancel = ref(false)
const searchForm = reactive({
    patientName: '',
    minCreateTime: '',
    maxCreateTime: '',
    current:1,
    size:10
})


const reservationData = reactive({
    records:[
        {
            id:'',
            name:'',
            createTime:''
        }
    ],
    total:1
})

onMounted(() =>{
    reset()
})

const reset = () =>{
    clear()
    query()
    
}

const clear = () =>{
    searchForm.patientName = ''
    searchForm.maxCreateTime = ''
    searchForm.minCreateTime = ''
    date.value = ['']
}

const date = ref([''])

const query = async () =>{
    const res = await queryCurrReservation(searchForm);
    if (res.data.code == '200'){
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

const accept = () =>{
    router.push('/createRecord')
}
</script>


<style scoped lang="ts">


</style>