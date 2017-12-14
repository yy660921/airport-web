// for App.vue router-view
import Vue from 'vue'
import Router from 'vue-router'
import Page4 from 'modules/page4/Page4.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      // 首页路由
      path: '/',
      name: 'Page4',
      component: Page4
    },
  ]
});

export default router;
