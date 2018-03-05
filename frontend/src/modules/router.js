// for App.vue router-view
import Vue from 'vue'
import Router from 'vue-router'
import Home from 'modules/Home.vue'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      // 首页路由
      path: '/',
      name: 'Home',
      component: Home
    },
  ]
});

export default router;
