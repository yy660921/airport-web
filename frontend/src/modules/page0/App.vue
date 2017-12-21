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
          <h3>近<span>24</span>小时</h3>
          <p class="para">预警查获事件数量为<strong class="txt-block"><span v-for="one in warningEvents_number.toString()">{{ one }}</span></strong></p>
          <p class="para">其中涉及有风险旅客和重点旅客<strong class="txt-block"><span v-for="one in tourist_warningEvents.toString()">{{ one }}</span></strong>人（境内<strong class="txt-block"><span v-for="one in chinaTourist_warningEvents.toString()">{{ one }}</span></strong>人，境外<strong class="txt-block"><span v-for="one in overseasTourist_warningEvents.toString()">{{ one }} </span></strong>人）
          </p>
          <p class="para">查获物品<strong class="txt-block"><span v-for="one in seizure_number.toString()"> {{ one }}</span></strong>件（违禁品<strong class="txt-block"><span v-for="one in contraband_number.toString()">{{ one }}</span></strong>件，高价值税品<strong class="txt-block"><span v-for="one in highTax_number.toString()">{{ one }}</span></strong>件）</p>
          <p class="para">监管人员总数<strong class="txt-block"><span v-for="one in governpeople_number.toString()">{{ one }}</span></strong>设备查验总数<strong class="txt-block"><span v-for="one in devicecount_number.toString()">{{ one }}</span></strong>次</p>
        </div>
      </div>
      <div class="con clearfix">
        <div class="con-chart last">
          <Echarts theme="ring" :option="b_option" className="chart" ></Echarts>
        </div>
        <div class="con-txt">
          <p class="para">外网相关舆情总数为<strong class="txt-block"><span v-for="one in yuqing_total.toString()">{{ one }}</span></strong>其中参与媒体<strong class="txt-block"><span v-for="one in yuqing_media.toString()">{{ one }}</span></strong>家</p>
          <p class="para">（新华网<strong class="txt-block"><span v-for="one in yuqing_xinhua.toString()">{{ one }}</span></strong>条、中新网<strong class="txt-block"><span v-for="one in yuqing_zhongxin.toString()">{{ one }}</span></strong>条）</p>
          <p class="para">参与微信公众号<strong class="txt-block"><span v-for="one in yuqing_gzh.toString()">{{ one }}</span></strong>个（铜仁公安<strong class="txt-block"><span v-for="one in yuqing_tongrenga.toString()">{{ one }}</span></strong>条，铜仁微生活<strong class="txt-block"><span v-for="one in yuqing_tongrenwsh.toString()">{{ one }}</span></strong>条）</p>
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
        yuqing_xinhua: 25,
        yuqing_zhongxin: 500,
        yuqing_gzh: 2345,
        yuqing_tongrenga: 200,
        yuqing_tongrenwsh: 4,
        yuqingIndex: 80,

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
                    fontSize: 60,
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
                        color: '#d37d1c'
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
                    color: '#d37d1c',
                    insideColor: '#fff',
                    fontSize: 60,
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
                    borderColor: '#d37d1c',
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
              this.yuqing_xinhua = response.data.yuqing_xinhua;
              this.yuqing_zhongxin = response.data.yuqing_zhongxin;
              this.yuqing_gzh = response.data.yuqing_gzh;
              this.yuqing_tongrenga = response.data.yuqing_tongrenga;
              this.yuqing_tongrenwsh = response.data.yuqing_tongrenwsh;
              this.yuqingIndex = response.data.yuqing_index;
              this.t_option.series[0].data[0].value = this.riskIndex / 100;
              this.b_option.series[0].data[0].value = this.yuqingIndex / 100;
            }
          });
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
      font-size: 1.4rem
      display: inline-block
      border-bottom: 4px solid #1b6cc9
      &.last
        border-color: #c8781c
      span
        color: #fff
        margin: 0 .2rem
        font-size: 2.8rem
        font-weight: bold
    .para
      font-size: 1rem
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
          font-size: 1.8rem
          height: 2.5rem
          line-height: 2.5rem
          text-align: center
          width: 28px
          margin: 0 1px
          display: inline-block
          background-image: url("~assets/images/word-bg.png")
          background-size: 100% 100%
</style>
