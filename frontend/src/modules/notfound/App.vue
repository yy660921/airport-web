<template>
  <div id="app" class="real-body">
    <div class="horse">
      <p class="point">平台建设中，敬请期待</p>
    </div>

  </div>
</template>

<script type="text/ecmascript-6">
  // import Vue from 'vue'
  import 'components/charts/theme/Ring.js'
  import Graphic from 'echarts/lib/util/graphic'
  import Echarts from 'vue-echarts-v3/src/full.js'
  import 'echarts-wordcloud'
  import bCarousel from 'bootstrap-vue/es/components/carousel/carousel'
  import bCarouselSlide from 'bootstrap-vue/es/components/carousel/carousel-slide'
  import Common from 'components/Common.js'

  import WordCloud from 'components/charts/wcloud/WordCloud3d.js'

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
    padding-left: 30%
    padding-top: 10%
    background-position: center
    .point
      font-size: 4em
      font-family: 华文行楷
      color: white

</style>
