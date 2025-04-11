<template>
    <span> 录入医生信息</span>
    <div class="inputArea">
        <el-form ref="FormRef" :model="requestParam" :rules="requstRules">
            <el-form-item label="用户名：" prop="username">
                <el-input v-model="requestParam.username"></el-input>
            </el-form-item>
            <el-form-item label="密码：" prop="password">
                <el-input v-model="requestParam.password"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
                <el-input v-model="requestParam.name"></el-input>
            </el-form-item>
            <el-form-item label="年龄：" prop="age">
                <el-input v-model="requestParam.age"></el-input>
            </el-form-item>
            <el-form-item label="性别：" prop="sex">
                <el-radio-group v-model="requestParam.sex">
                    <el-radio value="MALE">
                        男
                    </el-radio>
                    <el-radio value="FEMALE">
                        女
                    </el-radio>
                </el-radio-group>
            </el-form-item>
            <el-form-item label="电话：" prop="tele">
                <el-input v-model="requestParam.tele"></el-input>
            </el-form-item>
            <el-form-item label="邮箱：" prop="mail">
                <el-input v-model="requestParam.mail"></el-input>
            </el-form-item>
            <el-form-item label="工作时间：" prop="worktime">
                <el-checkbox-group v-model="weeks.idx" size="large">
                    <el-checkbox-button v-for="item in weeks.week" :key="item" :value="item">
                        {{ item }}
                    </el-checkbox-button>
                </el-checkbox-group>
            </el-form-item>
            <el-form-item label="科室：" prop="department">
                <el-select style="width: 100px;" placeholder="选择科室" v-model="requestParam.department">
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
import { addDoc, getAllDepartment } from '../../../../request/api';
import { ElMessage, type FormInstance } from 'element-plus';
import { useRouter } from 'vue-router';

const router = useRouter()
const FormRef = ref<FormInstance>()

const requstRules = {
    username: [
        { required: true, message: '请输入不为空的工号！', trigger: 'blur' },
        { min: 5, max: 11, message: '工号长度应在8-11之间！', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请输入不为空的密码！', trigger: 'blur' },
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
    age:[
        { required: true, message: '请输入年龄！', trigger: 'blur' },
        { min: 1, max: 3, message: '请输入正确的年龄！' , trigger: 'blur' }
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
    username: '',
    password: '',
    name: '',
    age: '',
    sex: 'NULL',
    tele: '',
    mail: '',
    worktime: '',
    department: ''
})

const weeks = reactive({
    week: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
    idx: []
})

onMounted(() => {
    init()
})

const init = async () => {
    const res = await getAllDepartment()
    departments.records = res.data.data
}

const submit = (formEl: FormInstance | undefined) => {
    requestParam.worktime = getTime(weeks.idx)
    console.log(requestParam.worktime)
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await addDoc(requestParam)
            if (res.data.code == '200') {
                ElMessage.success('新增成功！')
                router.go(-1)
            } else {
                ElMessage.error(res.data.data)
            }

        }
    })
}
</script>