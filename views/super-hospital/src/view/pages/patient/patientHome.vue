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
                    <el-dropdown-item>修改个人信息</el-dropdown-item>
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
                <span>智慧医疗用户端</span>
                
              </div>
              
              
            </el-header>
  
  
  
            <el-main>
              <div class="main">
                <router-view></router-view>
              </div>
            </el-main>
          </el-container>
        </el-container>
      </div>
    </div>
  </template>
  
<script setup lang="ts">
import { RouterView, useRouter } from 'vue-router'
const router = useRouter()
const username = localStorage.getItem('token')
const menu = [
  { id: "1", name: '预约挂号', val: [{ name: '挂号', path: '/ReserverPage' }] },
  { id: "2", name: '就诊记录', val: [{ name: '我的挂号', path: '/myReservation' }] },
]
const loginOut = () => {
  localStorage.removeItem('token')
  router.push('/')
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
  overflow: hidden;

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
</style>