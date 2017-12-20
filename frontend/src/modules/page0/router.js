// for App.vue router-view
import Vue from 'vue'
import Router from 'vue-router'
import Page0 from 'modules/page0/Page0.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      // 首页路由
      path: '/',
      name: 'Page0',
      component: Page0
    },
  ]
});

export default router;
