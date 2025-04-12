<template>
    <div>
        <el-tag type="primary" size="large">医生详细</el-tag>
    </div>
    <div class="showArea">
        <el-descriptions 
        column="1"
        border>
            <el-descriptions-item label="用户名">
                {{ showParam.records.username }}
            </el-descriptions-item>
            <el-descriptions-item label="姓名">
                {{ showParam.records.name }}
            </el-descriptions-item>
            <el-descriptions-item label="性别">
                {{ showParam.records.sex }}
            </el-descriptions-item>
            <el-descriptions-item label="年龄">
                {{ showParam.records.age }}
            </el-descriptions-item>
            <el-descriptions-item label="电话">
                {{ showParam.records.tele }}
            </el-descriptions-item>
            <el-descriptions-item label="邮箱">
                {{ showParam.records.mail }}
            </el-descriptions-item>
            <el-descriptions-item label="科室">
                {{ showParam.records.department }}
            </el-descriptions-item>
            <el-descriptions-item label="工作时间">
                <el-checkbox-group v-model="weeks.idx" size="large" disabled>
                    <el-checkbox 
                    v-for="item in weeks.week" 
                    :key="item" 
                    :value="item"
                    >
                        {{ item }}
                    </el-checkbox>
                </el-checkbox-group>
            </el-descriptions-item>
            <el-descriptions-item label="挂号费">
                {{ showParam.records.price }} 元/次
            </el-descriptions-item>
        </el-descriptions>
        <el-button type="primary" @click="router.go(-1)">返回</el-button>
    </div>
</template>

<style lang="css">
.showArea{

    
    font-size: 20px;
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
            price:'',
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