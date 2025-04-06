<template>
    <el-form
    ref="recordRef"
    :model="updRecordDTO" 
    :rules="updRules"
    >
        <el-descriptions
        column="2"
        border
        title="录入病历">
            <el-descriptions-item span="2" label="病历编号">
                <el-form-items v-model="updRecordDTO.id" prop="id">
                    {{ recordBaseInfo.id }}
                </el-form-items>
            </el-descriptions-item>
            <el-descriptions-item label="科室">
                {{ recordBaseInfo.department }}
            </el-descriptions-item>
            <el-descriptions-item label="主治医生">
                {{ recordBaseInfo.dName }}
            </el-descriptions-item>
            <el-descriptions-item label="患者姓名">
                {{ recordBaseInfo.pName }}
            </el-descriptions-item>
            <el-descriptions-item label="患者性别">
                {{ recordBaseInfo.sex }}
            </el-descriptions-item>
            <el-descriptions-item label="患者年龄">
                {{ recordBaseInfo.age }}
            </el-descriptions-item>
            <el-descriptions-item label="诊断时间">
                {{ recordBaseInfo.createTime }}
            </el-descriptions-item>
            <el-descriptions-item span="2" label="传染病">
                <el-form-item prop="epidemic">
                    <el-radio-group v-model="updRecordDTO.epidemic">
                        <el-radio value="true">是</el-radio>
                        <el-radio value="false">否</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="3" label="病情症状">
                <el-form-item prop="condition">
                    <el-input type="textarea" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="3" label="临床初步判断">
                <el-form-item prop="check">
                    <el-input type="textarea" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item label="辅助判断">
                <el-form-item prop="assistantCheck">
                    <el-input type="textarea" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item label="当前病症处理">
                <el-form-item prop="handle">
                    <el-input type="textarea" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item span="2" label="医嘱">
                <el-form-item prop="advice">
                    <el-input type="textarea" show-word-limit maxlength="255"></el-input>
                </el-form-item>
            </el-descriptions-item>
            <el-descriptions-item  label="配药">
                <template #label>
                    配药 <el-icon @click="addMedicine()" ><Plus /></el-icon>
                </template>
                <el-form-item v-for="(item) in medicines.records" style="margin-bottom: 10px;">
                    <el-select-v2
                    v-model="item.id"
                    filterable
                    :options="states"
                    placeholder="Please select"
                    style="width: 240px"
                    />
                    {{ item.count }}
                    <el-button type="danger" @click="removeMedicine(item)">删除</el-button>
                </el-form-item>
            </el-descriptions-item>
        </el-descriptions>
        <el-form-item>
            <el-button type="primary" @click="isConfirm=true">确认提交</el-button>
            <el-button type="danger" @click="">返回</el-button>
        </el-form-item>
    </el-form>

    <!-- 确认对话框 -->
    <el-dialog v-model="isConfirm" title="录入病历" width="500" center :show-close="false" class="removeDialog">
            <div class="description">请再次确认输入信息无误，提交后不可再次修改！</div>
            <div class="action">
                <el-button type="primary" @click="">确认</el-button>
                <el-button type="danger" @click="isConfirm = false;">取消</el-button>
            </div>
    </el-dialog>
</template>

<script setup lang="ts">
import type { FormInstance } from 'element-plus';
import { reactive, ref } from 'vue';

const recordId = ref('')
const isConfirm = ref(false)
const recordRef = ref<FormInstance>()
// 请求初始化数据，用于自动填写病历上的基础栏目
const recordInitalInfo = reactive({
    patientId:'',
    doctor:''
})
// interface medicine{
//     id:string,
//     count:number
// }
const medicines = reactive({
    records:[{
        id:'',
        count:1
    }]
})

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
const states = [
    {
        value:'12123',
        label:'长生不老药'
    }
]
const updRecordDTO = reactive({
    id:'',
    patientId:'',
    doctorId:'',
    epidemic:'',
    conditon:'',
    check:'',
    assistant_check:'',
    diagnosis:'',
    handle:'',
    advice:'',

})

const addMedicine = () =>{
    medicines.records.push({
        id:'',
        count:1
    })
    console.log(medicines)
}

const updRules = {
    
}

const removeMedicine = (item:any) =>{
    var idx = medicines.records.indexOf(item)
    if (idx !== -1){
        medicines.records.splice(idx, 1)
    }
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