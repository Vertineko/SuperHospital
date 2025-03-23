<template>
  <div class="backGroud">
    <div class="common-layout">
      <el-container>
        <el-aside style="width: 12vw; height: 100vh; margin-left: 10px; margin-right: 10px;">
          <div class="collapse-Aside">
            <img src="../../../assets/img/医生.svg" width="100%" style=" border-radius: 10px;">
            <el-divider direction="horizon" style="width: 100%;" />
            <div style="width: 100%; text-align: center; margin: 5px 0 5px 0;">欢迎您！{{ username }}</div>
            
            <el-menu 
            :router="true"
            unique-opened
            style="height: 100%;"
            default-active="1" 
            class="el-menu-vertical-demo" 
            >
              <el-sub-menu  v-for="subItem in menu" :index="subItem.id">
                <template #title>
                  {{ subItem.name }}
                </template>
                <el-menu-item :index="val.path" v-for="val in subItem.val">{{ val.name }}</el-menu-item>
              </el-sub-menu>
            </el-menu>

          </div>
        </el-aside>
        <el-container>
          <el-header style="height: 9%;">
            <div class="header">
              <span style="font-size: 36px; ">智慧医疗管理端</span>
            </div>
          </el-header>
          <el-main style="height: 91%; overflow: hidden;">
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
import { RouterView } from 'vue-router'

const username = localStorage.getItem('token')
const menu = [
  {id: "1", name: '控制台', val:[{name : '主页', path:'/AdminConsole'}]},
  {id: "2", name: '医生管理', val:[{name : '医生信息管理', path:'/AdminDocMag'},{name: '科室管理', path:'/AdminDepMag'}]},
  {id: "3", name: '患者管理', val:[{name : '患者信息管理', path:'/AdminPatMag'}]},
  {id: "4", name: '病历管理', val:[{name : '病历信息管理', path:'/AdminRecMag'}]},
  {id: "5", name: '药品管理', val:[{name : '药品信息管理', path:'/'}, {name : '药品库存管理', path:'/'}]},
]
</script>

<style lang="css" scoped>
.backGroud {

  background-color: #f6f6f6;
}

.collapse-Aside {
  margin: 10px 10px 10px 10px;
  border-radius: 10px;
  border-color: black;
  border-width: 5px;
}
.header{
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影 */
  width: 100%;
  height: 80%;
  border-radius: 10px;
  background-color: rgb(255, 255, 255);
  margin-top:10px;
  
}
.main{
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1); /* 阴影 */
  background-color: #f0eeee;
  border-radius: 10px;
  width: 100%;
  height: 100%;
  padding: 20px 0px 0px 0px;
  padding: 0;
}
</style>