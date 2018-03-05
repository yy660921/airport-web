// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.

// 置顶bootstrap.css,以备让后面的css覆盖
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'font-awesome-webpack'
import 'es6-promise/auto'
import 'jquery/jquery.js'

import Vue from 'vue'
import App from './App'
import BootstrapVue from 'bootstrap-vue'

import router from './router.js'
import '../../libs/date.format.js'
import '../../libs/bg.js'

Vue.use(BootstrapVue)
Vue.config.productionTip = false

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  template: '<App/>',
  components: { App }
});
