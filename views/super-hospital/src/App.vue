

<template>
    <RouterView />
</template>

<style lang="css" scoped>
@import './assets/css/main.css';
</style>


<script setup lang="ts">
import { RouterView } from 'vue-router'
import Login from './view/pages/admin/Login.vue'
import router from './router';
</script>


