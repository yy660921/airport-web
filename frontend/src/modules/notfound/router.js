// for App.vue router-view
import Vue from 'vue'
import Router from 'vue-router'
import Page0 from 'modules/notfound/Notfound.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      // 首页路由
      path: '/',
      name: 'Notfound',
      component: Page0
    },
  ]
});

export default router;
