<template>
    <div>
        <h1>医生详细</h1>
    </div>
    <div class="showArea">
        <el-row>
            用户名:{{ showParam.records.username }}
        </el-row>
        <el-row>
            姓名:{{ showParam.records.name }}
        </el-row>
        <el-row>
            性别:{{ showParam.records.sex }}
        </el-row>
        <el-row>
            年龄:{{ showParam.records.age }}
        </el-row>
        <el-row>
            电话:{{ showParam.records.tele }}
        </el-row>
        <el-row>
            邮箱:{{ showParam.records.mail }}
        </el-row>
        <el-row>
            科室:{{ showParam.records.department }}
        </el-row>
        <el-row>
            工作时间:
            <el-checkbox-group v-model="weeks.idx" size="large" disabled>
                <el-checkbox 
                v-for="item in weeks.week" 
                :key="item" 
                :value="item"
                >
                    {{ item }}
                </el-checkbox>
            </el-checkbox-group>
        </el-row>

    </div>
</template>

<style lang="css">
h1{
    padding-top: 20px;
    padding-left: 10px;
}
.showArea{
    padding-top: 10px;
    padding-left: 30px;
    font-size: 20px;
}
.el-row{
    align-items: center;
    margin-top: 10px;
}
</style>

<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { getDocDetail, getDepartmentById } from '../../../../request/api';
import { ElMessage } from 'element-plus';


const route = useRoute()
const router = useRouter()


const showParam = reactive(
    {
        records: {
            username: '',
            name: '',
            age: '',
            sex: '',
            tele: '',
            mail: '',
            worktime: '',
            department: '',
            role: ''
        }
    }
)

const weeks = reactive({
    week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    idx: ['']
})

onMounted(() => {
    const username = route.query.username
    init(username)
})

const init = async (username: any) => {
    const res1 = await getDocDetail(username)
    if (res1.data.code == '200') {
        showParam.records = res1.data.data
        const res2 = await getDepartmentById(showParam.records.department)
        if (res2.data.code == '200'){
            showParam.records.department = res2.data.data.name
        }
        modify()
    } else {
        ElMessage.error(res1.data.data)
        router.go(-1)
    }

}

function modify(){
    if (showParam.records.sex === 'FEMALE') {
        showParam.records.sex = '女'
    }else {
        showParam.records.sex = '男'
    }
    const str = showParam.records.worktime
    const daysArray: string[] = [];
    for (let i = 0; i < str.length; i++) {
        if (str[i] === '1') {
            daysArray.push(weeks.week[i]);
        }
    }
    weeks.idx = daysArray
    

}
</script>