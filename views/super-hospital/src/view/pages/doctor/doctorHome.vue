<template>
    <div class="backGroud">
      <div class="common-layout">
        <el-container>
          <el-aside>
            <div class="collapse-Aside">
              <img src="../../../assets/img/医院.svg" width="80%"
                style="padding-left: 10%; padding-right: 10%; padding-top: 10px;">
              <el-divider direction="horizon" style="width: 100%;" />

              <div style="width: 100%; text-align: center; margin: 5px 0 5px 0;">欢迎您！
              <el-dropdown placement="bottom">
                <el-button> {{username}} </el-button>
                <template #dropdown>
                  <el-dropdown-menu>
                    <el-dropdown-item @click="modShow=true">修改密码</el-dropdown-item>
                    <el-dropdown-item @click="loginOut()">退出登录</el-dropdown-item>
                  </el-dropdown-menu>
                </template>
              </el-dropdown>
            </div>
  
              <el-menu :router="true" unique-opened style="height: 100%;" :default-active="1"
                class="el-menu-vertical-demo">
                <el-sub-menu v-for="subItem in menu" :index="subItem.id">
                  <template #title>
                    {{ subItem.name }}
                  </template>
                  <el-menu-item :index="val.path" v-for="val in subItem.val">{{ val.name }}</el-menu-item>
                </el-sub-menu>
              </el-menu>
  
            </div>
          </el-aside>
          <!-- 主体显示部分 -->
          <el-container>
            <el-header style="">
              <div class="header">
                <span>智慧医疗医生端</span>
              </div>
              
            </el-header>
  
  
  
            <el-main>
              <div class="main">
                <router-view></router-view>
              </div>
            </el-main>
          </el-container>
        </el-container>

      <!-- 修改用对话框 -->
      <el-dialog 
        v-model="modShow" 
        title="修改密码" 
        width="500" 
        align-center
        :show-close="false">
            <el-form ref="modFormRef" :model="modFormData" :rules="modRules">
                <el-form-item prop='originPassword' label="原密码">
                    <el-input v-model="modFormData.originPassword"></el-input>
                </el-form-item>
                <el-form-item prop='newPassword' label="新密码">
                    <el-input v-model="modFormData.newPassword"></el-input>
                </el-form-item>
                <el-form-item>
                    <div class="act">
                        <el-button type="primary" @click="mod(modFormRef);">确认</el-button>
                        <el-button type="danger" @click="cancel()">取消</el-button>
                    </div>
                </el-form-item>
            </el-form>
        </el-dialog>
      </div>
    </div>
  </template>
  
<script setup lang="ts">
import { ElMessage, type FormInstance } from 'element-plus'
import { reactive, ref } from 'vue'
import { RouterView, useRouter } from 'vue-router'
import { modifyDoctorPassword } from '../../../request/api'

const router = useRouter()
const username = localStorage.getItem('token')
const menu = [
  { id: "1", name: '当前预约', val: [{ name: '待处理预约', path: '/currReservation' }] },
  { id: "2", name: '预约记录', val: [{ name: '预约历史', path: '/historyReservation' }] },
]

const loginOut = () =>{
  localStorage.removeItem('token')
  router.push('/')
}

const modShow = ref(false)
const modFormRef = ref<FormInstance>()
  const modFormData = reactive({
  originPassword:'',
  newPassword:''
})
const modRules = {
  originPassword: [
    { required: true, message: '请填写原密码！', trigger: 'blur' },
    { min: 6, max: 12, message: '请输入长度为5-12之间的密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请填写新密码！', trigger: 'blur' },
    { min: 6, max: 12, message: '请输入长度为5-12之间的密码', trigger: 'blur' }
  ],
}

const mod = (formEl:FormInstance | undefined) =>{
  if (!formEl) return
  formEl.validate(async (valid) =>{
    if (valid){
      const res = await modifyDoctorPassword(modFormData)
      if (res.data.code === '200'){
        ElMessage.success('修改成功！')
        cancel()
      }else{
        ElMessage.error(res.data.data)
        cancel()
      }
    }
  })
}

const cancel = () =>{
  modShow.value = false
  modFormData.newPassword = ''
  modFormData.originPassword = ''
}
</script>
  
<style lang="css" scoped>
.backGroud {
  
  background-color: #f6f6f6;
}
  
.el-aside {
  margin-left: 0;
  width: 14vw;
  height: 100vh;
  
}
  
.collapse-Aside {
  
  border-radius: 10px;
  border-color: black;
  border-width: 5px;
}
  
.el-header {
  padding-left: 0;
  padding-right: 0;
  height: 100px;
  
  .header {
    margin-top: 0;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    /* 阴影 */
    width: 100%;
    height: 100%;
    background-color: rgba(73, 98, 127, 0.549);
  
  }
  
  span {
    display: flex;
    font-size: 25px;
    color: #ffffff;
    height: 100%;
    align-items: center;
    padding-left: 20px;
  }
  
}
  
.el-main {
  height: 85%;
  overflow: hidden;
  padding-left: 0;
  padding-top: 0;
  padding-bottom: 0;
  padding-right: 0;
  overflow: scroll;
  
  .main {
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    /* 阴影 */
    background-color: #f0eeee;
    border-radius: 10px;
    width: 100%;
    height: 100%;
    padding: 0;
  
  }
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