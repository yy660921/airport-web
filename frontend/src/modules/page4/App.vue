<template>
  <div id="app" class="real-body">
    <div class="row con-row">
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option1.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option2.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option3.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option4.option" className="chart" ></Echarts>
        </div>
      </div>
    </div>
    <div class="row con-row">
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option5.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option6.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option7.option" className="chart" ></Echarts>
        </div>
      </div>
      <div class="col-md-3" >
        <div class="con-box">
          <Echarts theme="ring" :option="options.chart_option8.option" className="chart" ></Echarts>
        </div>
      </div>
    </div>
    <a href="/page0" class="next-page">
      <img src="~assets/images/next-page.png" alt="">
    </a>
    <a href="/" class="next-home">
      <img src="~assets/images/next-home.png" alt="">
    </a>
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
  import { ChartLib } from '../page1/ChartLib.js'

  export default {
    name: 'app',
    data () {
      return {
        options: {
          chart_option1: { option: {}, update: () => { return; } },
          chart_option2: { option: {}, update: () => { return; } },
          chart_option3: { option: {}, update: () => { return; } },
          chart_option4: { option: {}, update: () => { return; } },
          chart_option5: { option: {}, update: () => { return; } },
          chart_option6: { option: {}, update: () => { return; } },
          chart_option7: { option: {}, update: () => { return; } },
          chart_option8: { option: {}, update: () => { return; } },
        },
      };
    },
    created () {
      this.initOptions();
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 5 * 60 * 1000);
    },
    mounted () {
    },
    methods: {
      initOptions: function () {
        axios.get('/api/getShowCharts').then(response => {
          let temp = {}
          let num = 1
          let result_key = Object.values(response.data) // 从服务器获取的属性名
          let chart_lib_key = Object.keys(ChartLib);    // 从ChartLib获取属性名
          for (let key = 0; key < chart_lib_key.length; key++) {
            if (!result_key.includes(chart_lib_key[key])) {
              temp['chart_option' + num] = chart_lib_key[key];
              num++;
            }
          }
          _.each(temp, (v, k) => {
            this.options[k].option = _.cloneDeep(ChartLib[v].option);
            this.options[k].update = ChartLib[v].update;
          });
          this.updateData();
        });
      },
      updateData: function () {
        _.each(this.options, opt => {
          opt.update(opt.option);
        });
      }
    },
    components: {
      Echarts,
    },
  }
</script>

<style lang="sass">
  @import "~assets/sass/common"
  .con-row
    height: 48%
    margin-bottom: 2%
    &:last-child
      margin-bottom: 0
  .con-box
    height: 100%
    padding: .7rem .8rem .8rem
    background-image: url("~assets/images/page4-con-bg.png")
    background-size: 100% 100%
</style>
