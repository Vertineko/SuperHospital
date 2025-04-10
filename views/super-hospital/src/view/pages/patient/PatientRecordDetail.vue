<template>
        <el-descriptions
        column="2"
        border
        title="病历详情">
            <el-descriptions-item span="2" label="病历编号">
                {{ updRecordDTO.records.id }}
            </el-descriptions-item>
            <el-descriptions-item label="科室">
                {{ updRecordDTO.records.department }}
            </el-descriptions-item>
            <el-descriptions-item label="主治医生">
                {{ updRecordDTO.records.doctor }}
            </el-descriptions-item>
            <el-descriptions-item label="患者姓名">
                {{ updRecordDTO.records.patient }}
            </el-descriptions-item>
            <el-descriptions-item label="患者性别">
                {{ updRecordDTO.records.sex }}
            </el-descriptions-item>
            <el-descriptions-item label="患者年龄">
                {{ updRecordDTO.records.age }}
            </el-descriptions-item>
            <el-descriptions-item label="诊断时间">
                {{ updRecordDTO.records.createTime }}
            </el-descriptions-item>
            <el-descriptions-item span="2" label="传染病">
                {{ updRecordDTO.records.epidemic === '1' ? '是' : '否' }}
            </el-descriptions-item>
            <el-descriptions-item span="3" label="病情症状">
                {{ updRecordDTO.records.conditions }}
            </el-descriptions-item>
            <el-descriptions-item span="3" label="临床初步判断">
                {{ updRecordDTO.records.checks }}
            </el-descriptions-item>
            <el-descriptions-item label="辅助判断">
                {{ updRecordDTO.records.assistantCheck }}
            </el-descriptions-item>
            <el-descriptions-item label="当前病症处理">
                {{ updRecordDTO.records.handle }}
            </el-descriptions-item>
            <el-descriptions-item span="2" label="医嘱">
                {{ updRecordDTO.records.advice }}
            </el-descriptions-item>
            <el-descriptions-item  label="配药">
                <div v-for="(item) in updRecordDTO.records.medicinesVOList" style="margin-bottom: 10px;">
                    <div class="label">
                        药品名称: {{ item.id }}
                    </div>

                    <div >
                        
                        数量:<el-text class="mx-1" type="primary" style="margin-left: 3px; margin-right: 3px;">{{ item.count }}</el-text> 盒/剂
                        
                    </div>

                    
                </div>
            </el-descriptions-item>
        </el-descriptions>
            <el-button type="danger" @click="back()">返回</el-button>
        


    
</template>

<script setup lang="ts">
import { ElMessage  } from 'element-plus';
import { onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getOrders, getRecordDetails } from '../../../request/api';


const route = useRoute()
const router = useRouter()


// interface medicine{
//     id:string,
//     count:number
// }


const updRecordDTO = reactive({
    records:{
        id:'',
        department:'',
        doctor:'',
        patient:'',
        sex:'',
        epidemic:'',
        age:'',
        conditions:'',
        checks:'',
        assistantCheck:'',
        handle:'',
        advice:'',
        createTime:'',
        medicinesVOList:[
            {
                id:'',
                count:1
            }
        ]
    }
})



onMounted(() =>{
    const recordId = route.query.recordId
    const orderId = route.query.orderId
    init(recordId, orderId)
})




const init = async (recordId:any, orderId:any) =>{
    const res = await getRecordDetails(recordId)
    if (res.data.code === '200'){
        updRecordDTO.records = res.data.data
    }else {
        ElMessage.error(res.data.data)
    }

    const orders = await getOrders(orderId);
    if (orders.data.code === '200'){
        updRecordDTO.records.medicinesVOList = orders.data.data
    }else {
        ElMessage.error(orders.data.data)
    }
    
}
const back = () => {
    router.go(-1)
}




</script>

<style lang="css" scoped>

.el-form-item{
    margin-bottom: 0;
}

.el-description{
    overflow: scroll;
}


</style>