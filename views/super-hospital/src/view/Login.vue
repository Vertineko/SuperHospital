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
                        <el-form-item label="工号:" prop="username">
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
                        </el-form-item>
                    </div>
                    
                    忘记密码了？<el-link type="primary">找回密码</el-link>
                </el-form>

            </div>
        </div>
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
   margin-top: 15%; 
   margin-bottom: 20%;
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
</style>


<script setup lang="ts">
import { ElMessage, type FormInstance } from 'element-plus';
import { reactive, ref  } from 'vue';
import { useRouter } from 'vue-router';
import { AdminLogin } from '../request/api';

const router = useRouter();
const loginRef = ref<FormInstance>()
const loginRules = {
    username: [
        {required: true, message:'请输入不为空的工号！', trigger: 'blur'},
        {min: 5, max: 11, message:'工号长度应在8-11之间！', trigger: 'blur'}
    ],
    password: [
        {required: true, message:'请输入不为空的密码！', trigger: 'blur'},
        {min: 6, max: 13, message:'密码应该在6-13之间！', trigger: 'blur'}
    ]
}

const LoginFormData = reactive({
    username: '',
    password: '',
})

const model = ref('admin')
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
            const res = await AdminLogin(LoginFormData)
            //console.log(res)
            if (res.data.code == '200'){
                localStorage.setItem('token', res.data?.data)
                ElMessage.success('登录成功！')
                router.push('/')
            }else {
                //console.log('不为200')
                if (res.data?.data == "您已经处于登录状态！"){
                    //此时检测本地有没有token
                    const token = localStorage.getItem('token')
                    console.log(token)
                    if (token == LoginFormData.username){
                        ElMessage.success('登录成功！')
                        router.push('/e')
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
            const res = await AdminLogin(LoginFormData)
            //console.log(res)
            if (res.data.code == '200'){
                localStorage.setItem('token', res.data?.data)
                ElMessage.success('登录成功！')
                router.push('/')
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
</script>