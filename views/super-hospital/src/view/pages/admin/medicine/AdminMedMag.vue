<template>
    <div class="view">
        <el-tag type="primary" size="large">药品管理</el-tag>
        <!-- 搜索输入区域 -->
        <div class="inputArea">
            <el-row>
            <el-form 
            ref="searchFormRef"
            style="display: flex;" 
            :model="requestParam"
            >
                <el-form-item label="药品名称:" prop="name">
                    <el-input style="width: 300px;" v-model="requestParam.name"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-icon @click="search(searchFormRef)" size="20">
                        <Search />
                    </el-icon>
                    <el-icon size="20" @click="refresh()">
                        <Refresh />
                    </el-icon>
                    <el-icon size="20" @click="isAddshow = true">
                        <Plus />
                    </el-icon>
                </el-form-item>
            </el-form>
            </el-row>
        </div>

        <el-table 
        :data="FormData.records" 
        style="width: 100%;"
        >
            <el-table-column prop="id" label="药品编号" width="180" />
            <el-table-column prop="name" label="药品名称" width="180" />
            <el-table-column prop="form" label="制剂" width="180" />
            <el-table-column prop="note" label="备注" width="180" />
            <el-table-column prop="price" label="单价" width="180" />
            <el-table-column prop="count" label="盒/剂" width="180" />
            <el-table-column label="操作">
                <template style="display: block;" #default="scope">  
                    <el-icon size="20" @click="modInit(scope.row.id)">
                        <Edit />
                    </el-icon>
                    <el-icon size="20">
                        <CircleCloseFilled @click="curr = scope.row.id; isRemoveShow = true"/>
                    </el-icon>
                </template>
            </el-table-column>
        </el-table>
        <div class="pagePlugin">
            <el-pagination v-model:current-page="requestParam.current" layout="prev, pager, next" :total="FormData.total" @change="search(searchFormRef)"/>
        </div>

        <!-- 新增药品对话框 -->
        <el-dialog v-model="isAddshow" title="新增患者" width="500" center :show-close="false" class="addDialog">
            <el-form ref='addMedicineRef' :model='addMedicineForm' :rules='addMedicineRules'>
                <el-form-item label="药品名称:" prop='name'>
                    <el-input v-model="addMedicineForm.name"></el-input>
                </el-form-item>
                <el-form-item label="制剂:" prop='form'>
                    <el-input v-model="addMedicineForm.form"></el-input>
                </el-form-item>

                <el-form-item label="备注:" prop="note">
                    <el-input type="text" v-model="addMedicineForm.note"></el-input>
                </el-form-item>
                <el-form-item label="价格:" prop="price">
                    <el-input v-model="addMedicineForm.price"></el-input>
                </el-form-item>
                <el-form-item label="数量:" prop="count">
                    <el-input v-model="addMedicineForm.count"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="add(addMedicineRef)">确认</el-button>
                    <el-button type="danger" @click="isAddshow = false; addFormClear()">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>


        <!-- 修改药品对话框 -->
        <el-dialog v-model="isModShow" title="修改患者" width="500" center :show-close="false" class="addDialog">
            <el-form ref='modMedicineRef' :model='modMedicineForm.records' :rules='modMedicineRules'>
                <el-form-item label="药品编号:">
                    {{modMedicineForm.records.id}}
                </el-form-item>
                <el-form-item label="药品名称:" prop='name'>
                    <el-input v-model="modMedicineForm.records.name"></el-input>
                </el-form-item>
                <el-form-item label="制剂:" prop='form'>
                    <el-input v-model="modMedicineForm.records.form"></el-input>
                </el-form-item>

                <el-form-item label="备注:" prop="note">
                    <el-input type="text" v-model="modMedicineForm.records.note"></el-input>
                </el-form-item>
                <el-form-item label="价格:" prop="price">
                    <el-input v-model="modMedicineForm.records.price"></el-input>
                </el-form-item>
                <el-form-item label="数量:" prop="count">
                    <el-input v-model="modMedicineForm.records.count"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="mod(modMedicineRef)">确认</el-button>
                    <el-button type="danger" @click="isModShow = false; modFormClear()">取消</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>

        <!-- 修改药品对话框 -->
        <el-dialog v-model="isRemoveShow" title="删除药品" width="500" center :show-close="false" class="addDialog">
            <div>确定要删除该药品吗，此操作不可撤回!</div>
            <div>
                <el-button type="primary" @click="remove(curr)">确认</el-button>
                <el-button type="danger" @click="isRemoveShow = false; curr = '';">取消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<style lang="css" scoped>
.inputArea{
    width: 100%;
    display: flex;
    padding-top: 10px;
    padding-bottom: 10px;
    padding-left: 10px;
}
.pagePlugin{
    display: flex;
    width: 100%;
    align-items: center;
    justify-content: center;
}
.view{
    padding: 10 10;
}
.el-icon{
    margin-right: 10px; 
}
.el-input{
    max-width: 150px;
    margin: 0 10px 0 0 ;
    justify-content: center;
}
.el-radio-group{
    min-width: 150px;
}
.el-select {
    min-width: 150px;
    max-width: 200px;
    margin-right: 20px;
}

</style>

<script setup lang="ts">

import { reactive, ref, onMounted, initCustomFormatter } from 'vue';
import { insertMedicine, getMedicinePage, getMedicineById, updateMedicine, removeMedicineById } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';


const addMedicineRef = ref<FormInstance>()
const modMedicineRef = ref<FormInstance>()
const isAddshow = ref(false)
const isModShow = ref(false)
const isRemoveShow = ref(false)
const curr = ref('')
const searchFormRef = ref<FormInstance>()

const addMedicineForm = reactive({
    name:'',
    form:'',
    note:'',
    price:'',
    count:''
})

const modMedicineForm = reactive({
    records:{
        id:'',
        name:'',
        form:'',
        note:'',
        price:'',
        count:''
    }
})

const addMedicineRules = {
    name: [
        { required: true, message: '请填写药品名称！', trigger: 'blur' }
    ],
    form: [
        { required: true, message: '请填写制剂种类！', trigger: 'blur' }
    ],
    note: [
        { required: true, message: '请填写备注！', trigger: 'blur' },
        { max: 255, message:'备注最长不超过255字符', trigger: 'blur'}
    ],
    price: [
        { required: true, message: '请填写价格！', trigger: 'blur' }
    ],
    count: [
        { required: true, message: '请填写数量！', trigger: 'blur' }
    ]
}

const modMedicineRules = {
    name: [
        { required: true, message: '请填写药品名称！', trigger: 'blur' }
    ],
    form: [
        { required: true, message: '请填写制剂种类！', trigger: 'blur' }
    ],
    note: [
        { required: true, message: '请填写备注！', trigger: 'blur' },
        { max: 255, message:'备注最长不超过255字符', trigger: 'blur'}
    ],
    price: [
        { required: true, message: '请填写价格！', trigger: 'blur' }
    ],
    count: [
        { required: true, message: '请填写数量！', trigger: 'blur' }
    ]
}

const FormData = reactive(
    {
        records:[
            {
                id: '',
                name: '',
                form: '',
                note: '',
                price: '',
                count: '',
            }
        ],
        total: 1,
        
    }
)

const requestParam = reactive({
    
    name: '',
    current: 1,
    size: 10

})


onMounted(()=>{
    init(requestParam)
})

const search = (formEl: FormInstance | undefined) => {
    if (!formEl) return 
    formEl.validate(async (valid) =>{
        if (valid){
            const res = await getMedicinePage(requestParam)
            if (res.data.code === '200'){
                FormData.records = res.data.data.records
                FormData.total = res.data.data.total
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}

const init = async (requestParam:any) => {
    const res = await getMedicinePage(requestParam)
    if (res.data.code === '200'){
        FormData.records = res.data.data.records
        FormData.total = res.data.data.total
    }else {
        ElMessage.error(res.data.data)
    }
}



const refresh = () =>{
    requestParam.name=''
    requestParam.current= 1
    requestParam.size= 10
    init(requestParam)
}

const add = async (formEl:FormInstance|undefined) =>{
    if (!formEl) return 
    formEl.validate(async (valid) => {
        if (valid){
            const res = await insertMedicine(addMedicineForm)
            if (res.data.code === '200'){
                ElMessage.success('新增成功！')
                isAddshow.value = false
                init(requestParam)
                addFormClear()
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}
const modInit = async (id:string) => {
    isModShow.value = true;
    const res = await getMedicineById(id)
    if (res.data.code === '200'){
        modMedicineForm.records = res.data.data
    }else{
        ElMessage.error(res.data.data)
    }
}
const mod = (formEl:FormInstance|undefined) =>{
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await updateMedicine(modMedicineForm.records)
            if (res.data.code === '200'){
                ElMessage.success("修改成功！")
                init(requestParam)
                isModShow.value = false
            }else {
                ElMessage.error(res.data.data)
            }
        }
    })
}


const remove = async (id:string) => {
    const res = await removeMedicineById(id)
    if (res.data.code === '200'){
        ElMessage.success("删除成功！")
        init(requestParam)
        isRemoveShow.value = false   
        curr.value = ''
    }else {
        ElMessage.error(res.data.data)
        isRemoveShow.value = false
        curr.value = ''
    }
    
}

const addFormClear = () =>{
    addMedicineForm.name = ''
    addMedicineForm.form = ''
    addMedicineForm.note = ''
    addMedicineForm.price = ''
    addMedicineForm.count = ''

}

const modFormClear = () =>{
    modMedicineForm.records.id = ''
    modMedicineForm.records.name = ''
    modMedicineForm.records.form = ''
    modMedicineForm.records.note = ''
    modMedicineForm.records.price = ''
    modMedicineForm.records.count = ''
}
</script>