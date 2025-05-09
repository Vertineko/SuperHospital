<template>
    <el-form
    ref="recordRef"
    :model="updateRecord" 
    :rules="updRules"
    >
        <el-descriptions
        column="2"
        border
        title="录入病历">
            <el-descriptions-item span="2" label="病历编号">
                <el-form-items v-model="updRecordDTO.records.id" prop="id">
                    {{ updRecordDTO.records.id }}
                </el-form-items>
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
                <el-form-item prop="epidemic">
                    <el-radio-group v-model="updateRecord.epidemic">
                        <el-radio value="1">是</el-radio>
                        <el-radio value="0">否</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="3" label="病情症状">
                <el-form-item prop="conditions">
                    <el-input type="textarea" v-model="updateRecord.conditions" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="3" label="临床初步判断">
                <el-form-item prop="checks">
                    <el-input type="textarea" v-model="updateRecord.checks" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item label="辅助判断">
                <el-form-item prop="assistantCheck">
                    <el-input type="textarea" v-model="updateRecord.assistantCheck" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item label="当前病症处理">
                <el-form-item prop="handle">
                    <el-input type="textarea" v-model="updateRecord.handle" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="2" label="医嘱">
                <el-form-item prop="advice">
                    <el-input type="textarea" v-model="updateRecord.advice" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item  label="配药">
                <template #label>
                    配药 <el-icon @click="addMedicine()" ><Plus /></el-icon>
                </template>
                <el-form-item v-for="(item) in updateRecord.medicinesVOList" style="margin-bottom: 10px;">
                    <el-select-v2
                    v-model="item.id"
                    filterable
                    :options="states.records"
                    placeholder="Please select"
                    style="width: 240px"
                    @change="report()"
                    />

                    <div class="countAct">
                        <el-icon @click="item.count > 1 ? item.count--:item.count" style="margin-right: 5px;"><Minus /></el-icon>
                        <el-text class="mx-1" type="primary">{{ item.count }}</el-text>
                        <el-icon @click="item.count++" style="margin-left: 5px;"><Plus /></el-icon>
                    </div>

                    <el-button type="danger" @click="removeMedicine(item)">删除</el-button>
                </el-form-item>
            </el-descriptions-item>
        </el-descriptions>
        <el-form-item>
            <el-button type="primary" @click="isConfirm=true">确认提交</el-button>
            <el-button type="danger" @click="back()">返回</el-button>
        </el-form-item>
    </el-form>

    <!-- 确认对话框 -->
    <el-dialog v-model="isConfirm" title="录入病历" width="500" center :show-close="false" class="removeDialog">
            <div class="description">请再次确认输入信息无误，提交后不可再次修改！</div>
            <div class="action">
                <el-button type="primary" @click="submit()">确认</el-button>
                <el-button type="danger" @click="isConfirm = false;">取消</el-button>
            </div>
    </el-dialog>
</template>

<script setup lang="ts">
import { ElMessage, type FormInstance,  } from 'element-plus';
import { onMounted, reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import { getMedicines, getRecordDetails, updateRecord1 } from '../../../request/api';
import router from '../../../router';

const route = useRoute()
const isConfirm = ref(false)
const recordRef = ref<FormInstance>()


// interface medicine{
//     id:string,
//     count:number
// }




//基础栏目的接受值
const recordBaseInfo = reactive({
    //病历编号
    id:'123123',
    //患者姓名
    pName:'特使患者',
    //医生姓名
    dName:'茶几医生',
    sex:'男',
    age:'26',
    department:'儿科',
    createTime:'2025-3-25 14:00:26'
})
const states = reactive({
    records:[
        {
            value:'',
            label:'',
        }
    ]
})
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
        createTime:''
    }
})

const updateRecord = reactive({
    id:'',
    reservationId:'',
    epidemic:'',
    conditions:'',
    assistantCheck:'',
    checks:'',
    handle:'',
    advice:'',
    medicinesVOList:[{
        id:'',
        count:1,
    }]
})

onMounted(() =>{
    const recordId = route.query.recordId
    init(recordId)
})

const addMedicine = () =>{
    updateRecord.medicinesVOList.push({
        id:'',
        count:1,
    })
    
}

const updRules = {
    epidemic:[
        {required:true, message:'请选择是否为传染病！', trigger:'blur'}
    ],
    conditions:[
        {required:true, message:'请填写当前症状！', trigger:'blur'}
    ],
    checks:[
        {required:true, message:'请填写临床初步判断！', trigger:'blur'}
    ],
    assistantCheck:[
        {required:true, message:'请填写辅助判断！', trigger:'blur'}
    ],
    handle:[
        {required:true, message:'请填写当前病症处理方式！', trigger:'blur'}
    ],
    advice:[
        {required:true, message:'请填写医嘱!', trigger:'blur'}
    ]
}

const init = async (recordId:any) =>{
    const res = await getRecordDetails(recordId)
    if (res.data.code === '200'){
        updRecordDTO.records = res.data.data
    }else {
        ElMessage.error(res.data.data)
    }

    const medicine = await getMedicines();
    if (res.data.code === '200'){
        states.records = medicine.data.data
    }else {
        ElMessage.error(medicine.data.data)
    }
    
}

const removeMedicine = (item:any) =>{
    var idx = updateRecord.medicinesVOList.indexOf(item)
    if (idx !== -1){
        updateRecord.medicinesVOList.splice(idx, 1)
    }
}
const report = () =>{
    console.log(updateRecord.medicinesVOList)
}

const submit = async () => {
    const recordId:any = route.query.recordId
    const reservationId:any = route.query.reservationId
    updateRecord.id = recordId
    updateRecord.reservationId = reservationId 
    const res = await updateRecord1(updateRecord)
    if (res.data.code === '200'){
        ElMessage.success('提交病历成功！')
        isConfirm.value = false
        router.go(-1)
    }else {
        isConfirm.value = false
        ElMessage.error(res.data.data)
    }
}

const back = () =>{
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

.countAct{
    margin-left: 20px;
    margin-right: 20px;
}
</style>