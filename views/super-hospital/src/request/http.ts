import axios from 'axios';
import { useRouter } from 'vue-router'

const router = useRouter()

// 封装axios,并且命名为service
const http = axios.create({
    baseURL:"/",
    timeout:15000,
    headers:{
        "Content-Type":"application/json"
    }
})
//请求拦截，config为参数
http.interceptors.request.use((config)=>{
    config.headers = config.headers || {}
    if(localStorage.getItem('token')){
        config.headers.token=localStorage.getItem('token') || ""
    }
    return config
})
// 响应拦截 -->在返回结果之前做一些事情
http.interceptors.response.use(
    (res) => {
      if (res.status == 0 || res.status == 200) {
        // 请求成功对响应数据做处理，此处返回的数据是axios.then(res)中接收的数据
        // code值为 0 或 200 时视为成功
        return Promise.resolve(res)
      }
      return Promise.reject(res)
    },
    (err) => {
      // 在请求错误时要做的事儿
      // 此处返回的数据是axios.catch(err)中接收的数据
      console.log(err)
      if (err.response.status === 401) {
        localStorage.removeItem('token')
        router.push('/login')
      }
      return Promise.reject(err)
    }
  )
// 导出service
export default http