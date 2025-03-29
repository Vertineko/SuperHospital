<template>
    

</template>


<script setup lang="ts">
import { onMounted, reactive } from 'vue';
import { getAllDepartment } from '../../../request/api';
import { ElMention, ElMessage } from 'element-plus';
import { de } from 'element-plus/es/locales.mjs';

const departments = reactive(
    {
        records:[{
            id: '',
            name: ''
        }]
    }
)

onMounted(() =>{
    init()
})

const init = async () =>{
    const res = await getAllDepartment();
    if (res.data.code === '200'){
        departments.records = res.data.data
        console.log(departments.records)
    }else {
        ElMessage.error(res.data.data)
    }
}
</script>


<style scoped lang="css">



</style>