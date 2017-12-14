// for App.vue router-view
import Vue from 'vue'
import Router from 'vue-router'
import Page2 from 'modules/page2/Page2.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      // 首页路由
      path: '/',
      name: 'Page2',
      component: Page2
    },
  ]
});

export default router;
