import { createRouter, createWebHistory } from 'vue-router';
import AdminLogin from '../view/Login.vue';
import AdminHome from '../view/pages/admin/AdminHome.vue';
import AdminConsole from '../view/pages/admin/AdminConsole.vue';
import AdminDocMag from '../view/pages/admin/doctor/AdminDocMag.vue';
import AdminPatMag from '../view/pages/admin/patient/AdminPatMag.vue';
import AdminRecMag from '../view/pages/admin/record/AdminRecMag.vue';
import AddDocPage from '../view/pages/admin/doctor/AddDocPage.vue';
import DocDetailPage from '../view/pages/admin/doctor/DocDetailPage.vue';
import ModDocPage from '../view/pages/admin/doctor/ModDocPage.vue';
import DepMagPage from '../view/pages/admin/department/DepMagPage.vue';
import AdminMedMag from '../view/pages/admin/medicine/AdminMedMag.vue';
import PatientHome from '../view/pages/patient/patientHome.vue';
import ReserverPage from '../view/pages/patient/reserverPage.vue';
import myReservation from '../view/pages/patient/myReservation.vue';
import DoctorHome from '../view/pages/doctor/doctorHome.vue';
import CurrReservation from '../view/pages/doctor/currReservation.vue';
import HistoryReservation from '../view/pages/doctor/historyReservation.vue';
import createRecord from '../view/pages/doctor/createRecord.vue'

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
          ,
          {
            path: '/AdminMedMag',
            component: AdminMedMag
          }
          
          
        ]
      },
      {
        path: '/PatientHome',
        name: 'PatientHome',
        component: PatientHome,
        children:[
          {
            path:'/ReserverPage',
            component: ReserverPage
          },
          {
            path:'/myReservation',
            component: myReservation
          }
        ]
      },
      {
        path: '/DoctorHome',
        name: 'DoctorHome',
        component: DoctorHome,
        children:[
          {
            path:'/historyReservation',
            component: HistoryReservation
          },
          {
            path:'/currReservation',
            component: CurrReservation
          },
          {
            path:'/createRecord',
            component: createRecord
          }
          
        ]
      }
    ]
  })
  
export default router
