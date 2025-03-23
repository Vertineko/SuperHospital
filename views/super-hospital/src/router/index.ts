import { createRouter, createWebHistory } from 'vue-router'
import AdminLogin from '../view/pages/admin/Login.vue'
import AdminHome from '../view/pages/admin/AdminHome.vue'
import AdminConsole from '../view/pages/admin/AdminConsole.vue'
import AdminDocMag from '../view/pages/admin/AdminDocMag.vue'
import AdminPatMag from '../view/pages/admin/AdminPatMag.vue'
import AdminRecMag from '../view/pages/admin/AdminRecMag.vue'
import AddDocPage from '../view/pages/admin/AddDocPage.vue'
import DocDetailPage from '../view/pages/admin/DocDetailPage.vue'
import ModDocPage from '../view/pages/admin/ModDocPage.vue'
import DepMagPage from '../view/pages/admin/department/DepMagPage.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
      {
        path: '/',
        name: 'adminLogin',
        component: AdminLogin
      },
      {
        path: '/AdminHome',
        name: 'AdminHome',
        component: AdminHome,
        children:[
          {
            path: '/AdminConsole',
            component: AdminConsole
          },
          {
            path: '/AdminDocMag',
            component: AdminDocMag
          },
          {
            path: '/AdminPatMag',
            component: AdminPatMag
          },
          {
            path: '/AdminRecMag',
            component: AdminRecMag
          },
          {
            path: '/AddDocPage',
            component: AddDocPage
          },
          {
            path: '/DocDetailPage',
            component: DocDetailPage
          },
          {
            path: '/ModDocPage',
            component: ModDocPage
          },
          {
            path: '/AdminDepMag',
            component: DepMagPage
          }
        ]
      },
    ]
  })
  
export default router
