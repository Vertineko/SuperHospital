<template>

    <div class="backGround">

        <div class="loginCard">
            <img class="loginPic" src="../assets/img/login.svg" alt="" />
            <el-divider direction="vertical" style="height: 100%;"/>
            <div class="inputArea">
                <h1>智慧门诊</h1>
                <el-form 
                ref="loginRef" 
                :model="LoginFormData" 
                :rules="loginRules" 
                status-icon> 
                    <div class="input">
                        <el-form-item label="用户名:" prop="username">
                            <el-input style="width:85%;" v-model="LoginFormData.username"/>
                        </el-form-item>
                        <el-form-item label="密码:" prop="password">
                           <el-input style="width:85%;" type="password" v-model="LoginFormData.password" />
                        </el-form-item>
                        <el-form-item style="align-items: center;">
                            <el-radio-group v-model="model">
                                <el-radio value="admin">管理员</el-radio>
                                <el-radio value="doctor">医生</el-radio>
                                <el-radio value="patient">患者</el-radio>
                            </el-radio-group>
                        </el-form-item>
                        <el-form-item style="align-items: center;">
                            <el-button type="primary" style="margin-top: 10%;" @click="login(loginRef)">登录</el-button>
                            <el-button type="success" v-if="model === 'patient'" style="margin-top: 10%;" @click="isAddshow = true">注册</el-button>
                        </el-form-item>
                    </div>
                    
                    
                </el-form>

            </div>
        </div>
        <!-- 注册对话框 -->
        <el-dialog v-model="isAddshow" title="注册" width="500" center :show-close="false" class="addDialog">
            <el-form ref='addPatientFormRef' :model='addPatientForm' :rules='addPatientRules'>
                <el-form-item label="姓名:" prop='name'>
                    <el-input v-model="addPatientForm.name"></el-input>
                </el-form-item>
                <el-form-item label="用户名:" prop='username'>
                    <el-input v-model="addPatientForm.username"></el-input>
                </el-form-item>

                <el-form-item label="密码:" prop="password">
                    <el-input type="password" v-model="addPatientForm.password"></el-input>
                </el-form-item>
                <el-form-item label="性别:" prop="sex">
                    <el-radio-group v-model="addPatientForm.sex">
                        <el-radio value="MALE">男</el-radio>
                        <el-radio value="FEMALE">女</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input v-model.number="addPatientForm.age"></el-input>
                </el-form-item>
                <el-form-item label="电话:" prop="tele">
                    <el-input v-model="addPatientForm.tele"></el-input>
                </el-form-item>
                <div class="act">
                    <el-form-item>
                        <el-button type="primary" @click="add(addPatientFormRef)">确认</el-button>
                        <el-button type="danger" @click="isAddshow = false; addFormClear()">取消</el-button>
                    </el-form-item>
                </div>
            </el-form>
        </el-dialog>
    </div>

</template>


<style scoped>
.input{
    display: flex;
    flex-direction: column;
    width: 100%;
    height: 100%;
    align-items: center;
    
    margin-top: 8%;
}

h1 {
   margin-top: 60px; 
   
}

.loginCard {
    width: 70%; /* 宽度继承父元素 */
    height: 65%; /* 高度继承父元素 */
    background-color: #ffffff; /* 背景颜色 */
    border-radius: 10px; /* 可选：圆角效果 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* 可选：阴影效果 */
    display: flex;
    flex-direction: row;
    overflow: hidden;
}

.backGround {
    margin: 0;
    padding: 0;
    width: 100vw; /* 宽度 */
    height: 100vh; /* 高度 */
    background-color: #f6f6f6; /* 背景颜色 */
    display: flex;
    justify-content: center;
    align-items: center;
}
.inputArea{
    display: flex;
    flex-direction: column;
    width: 50%;
    height: 100%;
    text-align: center;
}
template {
    margin: 0;
    padding: 0;
    overflow: hidden; /* 防止滚动条 */
}
.loginPic{
    width: 50%;
    padding-left: 1%;
    margin-right: 1%;
}
.el-dialog{
    .context{
        margin-bottom: 40px;
        display: flex;
        justify-content: center;
    }
    .act{
        display: flex;
        justify-content: center;
    }
}
</style>


<script setup lang="ts">
import { ElMessage, type FormInstance } from 'element-plus';
import { reactive, ref  } from 'vue';
import { useRouter } from 'vue-router';
import { AdminLogin, PatientLogin, DoctorLogin, patientRegister } from '../request/api';

const model = ref('admin')
const router = useRouter();
const loginRef = ref<FormInstance>()
const addPatientFormRef = ref<FormInstance>()
const isAddshow = ref(false)

const checkAge = (rule:any, value:any, callback:any) =>{
    if (value < 0 || value > 150){
        callback(new Error('请输入的年龄应该为0-150！'))
    } else {
        callback()
    }
}
const addPatientRules = {
    name: [
        { required: true, message: '请填写用户名！', trigger: 'blur' }
    ],
    username: [
        { required: true, message: '请填写用户名！', trigger: 'blur' },
        { min: 5, max: 12, message: '请输入长度为5-12之间的用户名', trigger: 'blur' }
    ],
    password: [
        { required: true, message: '请填写密码！', trigger: 'blur' },
        { min: 6, max: 12, message: '请输入长度为5-12之间的密码', trigger: 'blur' },
        
    ],
    sex: [
        { required: true, message: '请选择性别', trigger: 'blur' }
    ],
    age: [
        { required: true, message: '请填写年龄！', trigger: 'blur' },
        { type:'number', message: '年龄应该为数字', trigger: ['blur','change'] },
        { validator: checkAge, trigger: 'blur'}
    ],
    tele: [
        { required: true, message: '请填写联系电话！', trigger: 'blur' },
        { min: 11, max: 11, message: '请输入正确的电话号码！', trigger: 'blur' },
        { pattern: /^1[3-9]\d{9}$/, message:'请输入有效的手机号码', trigger: 'blur'}
    ]
}
const loginRules = {
    username: [
        {required: true, message:'请输入不为空的工号！', trigger: 'blur'},
        {min: 5, max: 11, message:'工号长度应在8-11之间！', trigger: 'blur'}
    ],
    password: [
        {required: true, message:'请输入不为空的密码！', trigger: 'blur'},
        {min: 6, max: 12, message:'密码应该在6-12之间！', trigger: 'blur'}
    ]
}

const LoginFormData = reactive({
    username: '',
    password: '',
})

const addPatientForm = reactive({
    username: '',
    password: '',
    name: '',
    age: '',
    sex: '',
    tele: ''
})

const login = (formEl:any) =>{
    if (model.value == 'admin'){
        adminLogin(formEl)
    }else if (model.value == 'doctor'){
        doctorLogin(formEl)
    }else if (model.value == 'patient'){
        patientLogin(formEl)
    }

    
}
const adminLogin = (formEl:FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        //校验格式对了以后才会放行
        if (valid) {
            const res = await AdminLogin(LoginFormData)
            //console.log(res)
            if (res.data.code == '200'){
                localStorage.setItem('token', res.data?.data)
                ElMessage.success('登录成功！')
                router.push('/AdminHome')
            }else {
                //console.log('不为200')
                if (res.data?.data == "您已经处于登录状态！"){
                    //此时检测本地有没有token
                    const token = localStorage.getItem('token')
                    console.log(token)
                    if (token == LoginFormData.username){
                        ElMessage.success('登录成功！')
                        router.push('/AdminHome')
                    }else {
                        ElMessage.warning('此账号已经在别处登录，如果并非您本人所为，请及时联系管理员！')
                    }
                }else{
                    ElMessage.warning('工号或者密码错误，请重试！')
                }
                
            }
        }
    })
      
}

const doctorLogin = (formEl:FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        //校验格式对了以后才会放行
        if (valid) {
            const res = await DoctorLogin(LoginFormData)
            //console.log(res)
            if (res.data.code == '200'){
                localStorage.setItem('token', res.data?.data)
                ElMessage.success('登录成功！')
                router.push('/doctorHome')
            }else {
                //console.log('不为200')
                if (res.data?.data == "您已经处于登录状态！"){
                    //此时检测本地有没有token
                    const token = localStorage.getItem('token')
                    console.log(token)
                    if (token == LoginFormData.username){
                        ElMessage.success('登录成功！')
                        router.push('/DoctorHome')
                    }else {
                        ElMessage.warning('此账号已经在别处登录，如果并非您本人所为，请及时联系管理员！')
                    }
                }else{
                    ElMessage.warning('工号或者密码错误，请重试！')
                }
                
            }
        }
    })
      
}

const patientLogin = (formEl:FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        //校验格式对了以后才会放行
        if (valid) {
            const res = await PatientLogin(LoginFormData)
            //console.log(res)
            if (res.data.code == '200'){
                localStorage.setItem('token', res.data?.data)
                ElMessage.success('登录成功！')
                router.push('/PatientHome')
            }else {
                //console.log('不为200')
                if (res.data?.data == "您已经处于登录状态！"){
                    //此时检测本地有没有token
                    const token = localStorage.getItem('token')
                    console.log(token)
                    if (token == LoginFormData.username){
                        ElMessage.success('登录成功！')
                        router.push('/PatientHome')
                    }else {
                        ElMessage.warning('此账号已经在别处登录，如果并非您本人所为，请及时联系管理员！')
                    }
                }else{
                    ElMessage.warning('用户名或者密码错误，请重试！')
                }
                
            }
        }
    })
      
}

const add = (formEl: FormInstance | undefined) => {
    if (!formEl) return
    formEl.validate(async (valid) => {
        if (valid) {
            const res = await patientRegister(addPatientForm)
            if (res.data.code == '200') {
                ElMessage.success('注册成功！')
                addFormClear()
                isAddshow.value = false
            } else {
                ElMessage.error(res.data.data)
            }
        }
    })
}


const addFormClear = () => {
    addPatientForm.username = ''
    addPatientForm.password = ''
    addPatientForm.name = ''
    addPatientForm.sex = 'NULL'
    addPatientForm.age = ''
    addPatientForm.tele = ''
}

</script>