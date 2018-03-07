<template>
  <div id="app" class="real-body">
    <div class="horse">
      <img src="~assets/images/x.svg" alt="">
    </div>
    <div class="horse-l">
      <p class="point">平台建设中，敬请期待</p>
    </div>
    <div class="horse-r">
      <a href="/">
        <img src="~assets/images/next-home.png" alt="">
      </a>
    </div>

  </div>
</template>

<script type="text/ecmascript-6">
  // import Vue from 'vue'
  import 'components/charts/theme/Ring.js'
  import Common from 'components/Common.js'

  export default {
    name: 'app',
    data () {
      return {
      };
    },
    watch: {
    },
    created () {
    },
    mounted () {
    },
    methods: {
      goto () {
        document.location.href = Common.addr + Common.page3;
      },
    },
    beforeDestroy () {
    },
    components: {
    },
  }
</script>

<style lang="sass">
  @import "~assets/sass/common"

  .horse
    ext-align: center
    valign: center
    padding-left: 45%
    padding-top: 10%
  .horse-l
    ext-align: center
    valign: center
    padding-left: 30%
    .point
      font-size: 4em
      font-family: 华文行楷
      color: white
  .horse-r
    ext-align: center
    valign: center
    padding-left: 47%

</style>
