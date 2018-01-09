<template>
  <div id="app" class="real-body">
    <h1 class="main-title">
      <img src="~assets/images/page0-title.png">
    </h1>
    <div class="con-container">
      <div class="con clearfix">
        <div class="con-chart">
          <Echarts theme="ring" :option="t_option" className="chart" ></Echarts>
        </div>
        <div class="con-txt">
          <!-- <h3>近<span>24</span>小时</h3> -->
          <h3>近一周</h3>
          <p class="para">查获物品<strong class="txt-block"><span v-for="one in warningEvents_number.toString()">{{ one }}</span></strong>批，风险旅客<strong class="txt-block"><span v-for="one in tourist_warningEvents.toString()">{{ one }}</span></strong>人</p>
          <p class="para">
            入境旅客<strong class="txt-block"><span v-for="one in tourist_warningEvents.toString()">{{ one }}</span></strong>人，航班<strong class="txt-block"><span>0</span></strong>架
          </p>
          <p class="para">监管人员<strong class="txt-block"><span v-for="one in governpeople_number.toString()">{{ one }}</span></strong>人</p>
          <!-- <p class="para">查获物品<strong class="txt-block"><span v-for="one in seizure_number.toString()"> {{ one }}</span></strong>件（违禁品<strong class="txt-block"><span v-for="one in contraband_number.toString()">{{ one }}</span></strong>件，高价值税品<strong class="txt-block"><span v-for="one in highTax_number.toString()">{{ one }}</span></strong>件）</p>
          <p class="para">监管人员总数<strong class="txt-block"><span v-for="one in governpeople_number.toString()">{{ one }}</span></strong>设备查验总数<strong class="txt-block"><span v-for="one in devicecount_number.toString()">{{ one }}</span></strong>次</p> -->
        </div>
      </div>
      <div class="con clearfix">
        <div class="con-chart last">
          <Echarts theme="ring" :option="b_option" className="chart" ></Echarts>
        </div>
        <div class="con-txt">
          <p class="para">外网舆情<strong class="txt-block"><span v-for="one in yuqing_total.toString()">{{ one }}</span></strong>条</p>
          <p class="para">媒体<strong class="txt-block"><span v-for="one in yuqing_media.toString()">{{ one }}</span></strong>家（{{ yuqing_media_top1 }}<strong class="txt-block"><span v-for="one in yuqing_media_top1Count.toString()">{{ one }}</span></strong>条、{{ yuqing_media_top2 }}<strong class="txt-block"><span v-for="one in yuqing_media_top2Count.toString()">{{ one }}</span></strong>条）</p>
          <p class="para">微信公众号<strong class="txt-block"><span v-for="one in yuqing_gzh.toString()">{{ one }}</span></strong>个（{{ yuqing_gzh_top1 }}<strong class="txt-block"><span v-for="one in yuqing_gzh_top1Count.toString()">{{ one }}</span></strong>条，{{ yuqing_gzh_top2 }}<strong class="txt-block"><span v-for="one in yuqing_gzh_top1Count.toString()">{{ one }}</span></strong>条）</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">

  import Vue from 'vue'
  import Echarts from 'vue-echarts-v3/src/full.js'
  import LiquidFill from 'echarts-liquidfill'

  /* Globally bus for Non Parent-Child Communication */
  window.bus = new Vue();

  export default {
    name: 'app',
    data () {
      return {
        intervalID: null,
        riskIndex: 50,
        warningEvents_number: 50,
        tourist_warningEvents: 100,
        chinaTourist_warningEvents: 80,
        overseasTourist_warningEvents: 20,
        seizure_number: 200,
        contraband_number: 150,
        highTax_number: 50,
        governpeople_number: 30,
        devicecount_number: 2000,
        yuqing_total: 10000,
        yuqing_media: 52,
        yuqing_media_top1: '新华网',
        yuqing_media_top1Count: 15,
        yuqing_media_top2: '中新网',
        yuqing_media_top2Count: 9,
        yuqing_gzh: 2345,
        yuqing_gzh_top1: '铜仁公安',
        yuqing_gzh_top1Count: 230,
        yuqing_gzh_top2: '贵州日报',
        yuqing_gzh_top2Count: 23,
        yuqingIndex: 80,
        redColor: '#ff5252',
        yellowColor: '#d37d1c',
        blueColor: '#1b6cc9',

        t_option: {
          series: [{
            type: 'liquidFill',
            radius: '90%',
            data: [{
              value: 0.5,
              itemStyle: {
                normal: {
                  color: '#1b6cc9'
                }
              }
            }],
            shape: 'diamond',
            backgroundStyle: {
              color: 'rgba(255, 255, 255, 0)'
            },
            name: '外网舆情指数',
            label: {
              normal: {
                show: true,
                color: '#1b6cc9',
                insideColor: '#fff',
                fontSize: 80,
                fontWeight: 'bold',
                align: 'center',
                baseline: 'middle',
                position: 'inside'
              }
            },
            outline: {
              show: true,
              borderDistance: 15,
              itemStyle: {
                color: 'none',
                borderColor: '#1b6cc9',
                borderWidth: 8,
                shadowBlur: 20,
                shadowColor: 'rgba(0, 0, 0, 0.25)'
              }
            },
          }],
        },
        b_option: {
          series: [{
            type: 'liquidFill',
            radius: '90%',
            data: [{
              value: 0.5,
              itemStyle: {
                normal: {
                  color: '#1b6cc9'
                }
              }
            }],
            shape: 'diamond',
            backgroundStyle: {
              color: 'rgba(255, 255, 255, 0)'
            },
            name: '外网舆情指数',
            label: {
              normal: {
                show: true,
                color: '#1b6cc9',
                insideColor: '#fff',
                fontSize: 80,
                fontWeight: 'bold',
                align: 'center',
                baseline: 'middle',
                position: 'inside',
              },
            },
            outline: {
              show: true,
              borderDistance: 15,
              itemStyle: {
                color: 'none',
                borderColor: '#1b6cc9',
                borderWidth: 8,
                shadowBlur: 20,
                shadowColor: 'rgba(0, 0, 0, 0.25)'
              }
            },
          }],
        },
      }
    },
    created () {
      this.updateData()
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 60 * 1000);
    },
    methods: {
      updateData: function () {
        axios.get('/api/getFirstPage', {params: {}}).then(response => {
          if (response.data.riskIndex > 0) {
            this.riskIndex = response.data.riskIndex;
            this.warningEvents_number = response.data.warningEvents_number;
            this.tourist_warningEvents = response.data.tourist_warningEvents;
            this.chinaTourist_warningEvents = response.data.chinaTourist_warningEvents;
            this.overseasTourist_warningEvents = response.data.overseasTourist_warningEvents;
            this.seizure_number = response.data.seizure_number;
            this.contraband_number = response.data.contraband_number;
            this.highTax_number = response.data.highTax_number;
            this.governpeople_number = response.data.governpeople_number;
            this.devicecount_number = response.data.devicecount_number;
            this.yuqing_total = response.data.yuqing_total;
            this.yuqing_media = response.data.yuqing_media;
            for (var tp in response.data.yuqing_mediatop1) {
              this.yuqing_media_top1 = tp;
              this.yuqing_media_top1Count = response.data.yuqing_mediatop1[tp];
            }
            for (tp in response.data.yuqing_mediatop2) {
              this.yuqing_media_top2 = tp;
              this.yuqing_media_top2Count = response.data.yuqing_mediatop2[tp];
            }
            this.yuqing_gzh = response.data.yuqing_gzh;
            for (tp in response.data.yuqing_gzhtop1) {
              this.yuqing_gzh_top1 = tp;
              this.yuqing_gzh_top1Count = response.data.yuqing_gzhtop1[tp];
            }
            for (tp in response.data.yuqing_gzhtop2) {
              this.yuqing_gzh_top2 = tp;
              this.yuqing_gzh_top2Count = response.data.yuqing_gzhtop2[tp];
            }
            this.yuqingIndex = response.data.yuqing_index;
            var t_option_data_value = this.riskIndex / 100;
            var b_option_data_value = this.yuqingIndex / 100;
            this.t_option.series[0].data[0].value = t_option_data_value;
            this.b_option.series[0].data[0].value = b_option_data_value;
            // 将分数值改为中文显示
            this.t_option.series[0].label.normal.formatter = this.decideScoreColor(t_option_data_value).score;
            this.b_option.series[0].label.normal.formatter = this.decideScoreColor(b_option_data_value).score;
            // 更改top_echarts颜色
            this.t_option.series[0].data[0].itemStyle.normal.color = this.decideScoreColor(t_option_data_value).color;
            this.t_option.series[0].label.normal.color = this.decideScoreColor(t_option_data_value).color;
            this.t_option.series[0].outline.itemStyle.borderColor = this.decideScoreColor(t_option_data_value).color;
            // 更改bottom_echarts颜色
            this.b_option.series[0].data[0].itemStyle.normal.color = this.decideScoreColor(b_option_data_value).color;
            this.b_option.series[0].label.normal.color = this.decideScoreColor(b_option_data_value).color;
            this.b_option.series[0].outline.itemStyle.borderColor = this.decideScoreColor(b_option_data_value).color;
          }
        });
      },
      decideScoreColor: function (value) {
        // red: #ff5252
        // yellow: #d37d1c
        // blue: #1b6cc9
        if (value > 0.73) {
          return {
            score: '高',
            color: '#ff5252'
          }
        } else if (value > 0.6) {
          return {
            score: '中',
            color: '#d37d1c'
          }
        } else {
          return {
            score: '低',
            color: '#1b6cc9'
          }
        }
      }
    },
    beforeDestroy () {
      clearInterval(this.intervalID)
    },
    components: {
      Echarts
    }
  }
</script>

<style lang="sass">
  @import "~assets/sass/common"

  .real-body
    background-image: url("~assets/images/page0-bg.png")
    background-size: 100% 100%
    overflow-y: auto
    padding-bottom: 1rem
  .main-title
    text-align: center
    max-width: 60%
    margin: 0 auto 2rem
    img
      width: 100%
  .con-container
    padding-left: 16%
  .con
    display: flex
    justify-content: flex-start
    align-items: center
    margin: 0 auto 1.2rem
    &:last-child
      margin-bottom: 0
  .con-chart
    width: 15rem
    height: 16rem
    padding-top: 0
    &.last
      padding-top: 0
  .con-txt
    color: #fff
    text-align: left
    display: block
    margin-left: 2rem
    h3
      padding: 0 .5rem 4px
      margin-bottom: .8rem
      font-size: 2.8rem
      display: inline-block
      border-bottom: 4px solid #1b6cc9
      &.last
        border-color: #c8781c
      span
        color: #fff
        margin: 0 .2rem
        font-size: 3.2rem
        font-weight: bold
    .para
      font-size: 1.6rem
      margin-bottom: .6rem
      .txt-block
        vertical-align: middle
        display: inline-block
        margin: 0 6px
        border: 1px solid #27489d
        padding: 2px 1px
        font-weight: normal
        font-size: 0
        span
          font-family: "MyMontserrat"
          font-size: 2.2rem
          height: 2.6rem
          line-height: 2.6rem
          text-align: center
          width: 34px
          margin: 0 1px
          display: inline-block
          background-image: url("~assets/images/word-bg.png")
          background-size: 100% 100%
</style>
