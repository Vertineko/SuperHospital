<template>
    <span> 录入医生信息</span>
    <div class="inputArea">
        <el-form ref="FormRef" :model="requestParam.records" :rules="requstRules">
            <el-form-item label="用户名：" prop="username">
                {{ requestParam.records.username }}
            </el-form-item>
            <el-form-item label="密码：" prop="password">
                <el-input v-model="requestParam.records.password"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
                <el-input v-model="requestParam.records.name"></el-input>
            </el-form-item>
            <el-form-item label="年龄：" prop="age">
                <el-input v-model="requestParam.records.age"></el-input>
            </el-form-item>
            <el-form-item label="性别：" prop="sex">
                <el-radio-group v-model="requestParam.records.sex">
                    <el-radio value="MALE">
                        男
                    </el-radio>
                    <el-radio value="FEMALE">
                        女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="电话：" prop="tele">
                <el-input v-model="requestParam.records.tele"></el-input>
            </el-form-item>
            <el-form-item label="邮箱：" prop="mail">
                <el-input v-model="requestParam.records.mail"></el-input>
            </el-form-item>
            <el-form-item label="工作时间：" prop="worktime">
                <el-checkbox-group v-model="weeks.idx" size="large">
                    <el-checkbox-button v-for="item in weeks.week" :key="item" :value="item">
                        {{ item }}
                    </el-checkbox-button>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="科室：" prop="department">
                <el-select style="width: 100px;" placeholder="选择科室" v-model="requestParam.records.department">
                    <el-option v-for="item in departments.records" :key="item.id" :label="item.name" :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submit(FormRef)">提交</el-button>
            </el-form-item>
        </el-form>
    </div>

</template>

<style lang="css" scoped>
.inputArea {
    width: 100%;
    padding-left: 5vw;
}

.el-input {
    max-width: 300px;
}
</style>

<script setup lang="ts">
import { reactive, onMounted, ref } from 'vue';
import { updateDoc, getAllDepartment, getDocDetail } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute()
const router = useRouter()
const FormRef = ref<FormInstance>()

const requstRules = {
    password: [
        { min: 6, max: 13, message: '密码应该在6-13之间！', trigger: 'blur' }
    ],
    name: [
        { required: true, message: '请输入用户真实姓名', trigger: 'blur' },
        { min: 1, message: '姓名最少应该为1个字符', trigger: 'blur' }
    ],
    worktime: [
        { required: true, message: '请选择工作时间', trigger: 'blur' },
        { min: 7, max: 7, message: '工作时间格式有误！', trigger: 'blur' }
    ],
    department: [
        { required: true, message: '请选择科室', trigger: 'blur' },
    ],
    mail: [
        { required: true, message: '请输入邮箱！', trigger: 'blur' },
        {}
    ],
    tele: [
        { required: true, message: '请输入手机号码！', trigger: 'blur' },
    ],
    sex: [
        { required: true, message: '请选择性别！', trigger: 'blur' },
    ],
    age: [
        { required: true, message: '请输入年龄！', trigger: 'blur' },
        { min: 1, max: 150, message: '请输入正确的年龄！', trigger: 'blur' }
    ]

}

function getTime(week: string[]): string {
    let result = '0000000';
    week.forEach(day => {
        switch (day) {
            case '周一':
                result = replaceChar(result, 0, '1');
                break;
            case '周二':
                result = replaceChar(result, 1, '1');
                break;
            case '周三':
                result = replaceChar(result, 2, '1');
                break;
            case '周四':
                result = replaceChar(result, 3, '1');
                break;
            case '周五':
                result = replaceChar(result, 4, '1');
                break;
            case '周六':
                result = replaceChar(result, 5, '1');
                break;
            case '周日':
                result = replaceChar(result, 6, '1');
                break;
            default:
                // 如果数组中有无效值，忽略
                break;
        }
    });
    return result
}

function replaceChar(str: string, index: number, char: string): string {
    return str.substring(0, index) + char + str.substring(index + 1);
}

const departments = reactive(
    {
        records: [{
            id: '',
            name: ''
        }]
    }
)

const requestParam = reactive({
    records: {
        username: '',
        password: '',
        name: '',
        age: '',
        sex: '',
        tele: '',
        mail: '',
        worktime: '',
        department: ''
    }
})

const weeks = reactive({
    week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    idx: ['']
})

onMounted(() => {
    const username = route.query.username
    init(username)
})

const init = async (username: any) => {
    const res1 = await getAllDepartment()
    departments.records = res1.data.data

    const res2 = await getDocDetail(username)
    if (res2.data.code == '200') {
        requestParam.records = res2.data.data
        
        
        modify()
    } else {
        ElMessage.error(res1.data.data)
        router.go(-1)
    }
}

function modify(){
    const str = requestParam.records.worktime
    const daysArray: string[] = [];
    for (let i = 0; i < str.length; i++) {
        if (str[i] === '1') {
            daysArray.push(weeks.week[i]);
        }
    }
    weeks.idx = daysArray
    

}

const submit = (formEl: FormInstance | undefined) => {
    requestParam.records.worktime = getTime(weeks.idx)
    console.log(requestParam.records.worktime)
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await updateDoc(requestParam.records)
            if (res.data.code == '200') {
                ElMessage.success('更新成功！')
                router.go(-1)
            } else {
                ElMessage.error(res.data.data)
            }

        }
    })
}
</script>