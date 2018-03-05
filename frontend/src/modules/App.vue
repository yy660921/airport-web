<template>
  <div id="app">
    <!--通过vue-router控制主内容区域变换-->
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">

// Require custom echarts theme.
import 'components/charts/theme/Ring.js'

export default {
  name: 'app',
  components: {
  }
}
</script>

<style lang="sass">
  @import "../assets/sass/base"
  @import "../assets/sass/common"
</style>
