<template>
  <div id="app" class="real-body">
    <h1 class="main-title">
      <!-- <img src="~assets/images/page0-title.png"> -->
    </h1>
    <div class="con-container" @click="goto">
      <div class="con-box">
        <div class="con clearfix">
          <div class="con-chart">
            <Echarts theme="ring" :option="t_option" className="chart" ></Echarts>
          </div>
          <div class="con-txt">
            <h3><!-- {{ datetitle }} -->
              <b-form-select v-model="city_select" :options="city_options" class="city-select" />
              <span>年度累计</span>
            </h3>
            <p class="para" v-for="(item, index) in dynamics">
              <span v-for="(words, windex) in item.words">
                {{ words }}
                <strong class="txt-block" v-if="windex < item.keys.length"><span v-for="one in String(varlibs[item.keys[windex]])">{{ one }}</span></strong>
              </span>
            </p>
           </div>
        </div>
        <div class="con clearfix">
          <div class="con-chart last">
            <Echarts theme="ring" :option="b_option" className="chart" ></Echarts>
          </div>
          <div class="con-txt">
            <p class="para">外网舆情<strong class="txt-block"><span v-for="one in yuqing_total.toString()">{{ one }}</span></strong>条</p>
            <p class="para">媒体<strong class="txt-block"><span v-for="one in yuqing_media.toString()">{{ one }}</span></strong>家（{{ yuqing_media_top1 }}<strong class="txt-block"><span v-for="one in yuqing_media_top1Count.toString()">{{ one }}</span></strong>条、{{ yuqing_media_top2 }}<strong class="txt-block"><span v-for="one in yuqing_media_top2Count.toString()">{{ one }}</span></strong>条）</p>
            <p class="para">微信公众号<strong class="txt-block"><span v-for="one in yuqing_gzh.toString()">{{ one }}</span></strong>个（{{ yuqing_gzh_top1 }}<strong class="txt-block"><span v-for="one in yuqing_gzh_top1Count.toString()">{{ one }}</span></strong>条，{{ yuqing_gzh_top2 }}<strong class="txt-block"><span v-for="one in yuqing_gzh_top2Count.toString()">{{ one }}</span></strong>条）</p>
          </div>
        </div>
      </div>
      <div class="con-title">
        <p><img src="~assets/images/chayan-img.png" alt=""></p>
        <p><img src="~assets/images/taishi-img.png" alt=""></p>
        <p><img src="~assets/images/ganzhi-img.png" alt=""></p>
      </div>
    </div>
    <a href="/page1" class="next-page">
      <img src="~assets/images/next-page.png" alt="">
    </a>
    <a href="/" class="next-home">
      <img src="~assets/images/next-home.png" alt="">
    </a>
  </div>
</template>

<script type="text/ecmascript-6">

  import Vue from 'vue'
  import Echarts from 'vue-echarts-v3/src/full.js'
  import LiquidFill from 'echarts-liquidfill'

  import Common from 'components/Common.js'

  /* Globally bus for Non Parent-Child Communication */
  window.bus = new Vue();

  export default {
    name: 'app',
    data () {
      return {
        Common: Common,
        city_select: '贵阳',
        city_options: [
          {value: '贵阳', text: '贵阳'},
          {value: '铜仁', text: '铜仁'}
        ],
        intervalID: null,
        riskIndex: 50,
        yuqing_total: 10000,
        yuqing_media: 52,
        yuqing_media_top1: '新华网',
        yuqing_media_top1Count: 15,
        yuqing_media_top2: '中新网',
        yuqing_media_top2Count: 9,
        yuqing_gzh: 2345,
        yuqing_gzh_top1: '铜仁公安',
        yuqing_gzh_top1Count: 56,
        yuqing_gzh_top2: '贵州日报',
        yuqing_gzh_top2Count: 47,
        yuqingIndex: 80,
        redColor: '#ff5252',
        yellowColor: '#d37d1c',
        blueColor: '#1b6cc9',

        datetitle: '近一周',
        varlibs: {},
        dynamics: [],

        t_option: {
          series: [{
            type: 'liquidFill',
            radius: '90%',
            data: [{
              value: -0.1,
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
            name: '海关态势指数',
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
              value: -0.1,
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
      }, 10 * 1000);
      axios.get('/api/getPageJump', {params: {}}).then(response => {
        // alert(response.data.page0.to)
        // eslint-disable-next-line no-unused-vars
        let timer = setTimeout(function () { location.href = '../' + response.data.page0.to; } , response.data.page0.delay * 1000);
      })
    },
    methods: {
      updateData: function () {
        axios.get('/api/getFirstPage', {params: {area: this.city_select}}).then(response => {
          this.varlibs = response.data;
          this.riskIndex = _.isUndefined(response.data.riskIndex) ? this.riskIndex : response.data.riskIndex;
          var t_option_data_value = this.riskIndex / 100;
          this.t_option.series[0].data[0].value = t_option_data_value;
          // 将分数值改为中文显示
          // this.t_option.series[0].label.normal.formatter = this.decideScoreColor(t_option_data_value).score;
          this.t_option.series[0].label.normal.formatter = t_option_data_value;
          // 更改top_echarts颜色
          this.t_option.series[0].data[0].itemStyle.normal.color = this.decideScoreColor(t_option_data_value).color;
          this.t_option.series[0].label.normal.color = this.decideScoreColor(t_option_data_value).color;
          this.t_option.series[0].outline.itemStyle.borderColor = this.decideScoreColor(t_option_data_value).color;
          // 更改bottom_echarts颜色
        });
        axios.get('/api/getFirstPageNews', {params: {}}).then(response => {
          this.yuqingIndex = _.isUndefined(response.data.yuqing_index) ? this.yuqingIndex : response.data.yuqing_index;
          this.yuqing_total = _.isUndefined(response.data.yuqing_total) ? this.yuqing_total : response.data.yuqing_total;
          this.yuqing_media = _.isUndefined(response.data.yuqing_media) ? this.yuqing_media : response.data.yuqing_media;
          this.yuqing_gzh = _.isUndefined(response.data.yuqing_gzh) || response.data.yuqing_gzh === 0 ? this.yuqing_gzh : response.data.yuqing_gzh;
          if (!_.isEmpty(response.data.yuqing_mediatop1)) {
            for (var tmpK in response.data.yuqing_mediatop1) {
              this.yuqing_media_top1 = tmpK;
              this.yuqing_media_top1Count = response.data.yuqing_mediatop1[tmpK];
            }
          }
          if (!_.isEmpty(response.data.yuqing_mediatop2)) {
            for (tmpK in response.data.yuqing_mediatop2) {
              this.yuqing_media_top2 = tmpK;
              this.yuqing_media_top2Count = response.data.yuqing_mediatop2[tmpK];
            }
          }
          if (!_.isEmpty(response.data.yuqing_gzhtop1)) {
            for (tmpK in response.data.yuqing_gzhtop1) {
              this.yuqing_gzh_top1 = tmpK;
              this.yuqing_gzh_top1Count = response.data.yuqing_gzhtop1[tmpK];
            }
          }
          if (!_.isEmpty(response.data.yuqing_gzhtop2)) {
            for (tmpK in response.data.yuqing_gzhtop2) {
              this.yuqing_gzh_top2 = tmpK;
              this.yuqing_gzh_top2Count = response.data.yuqing_gzhtop2[tmpK];
            }
          }
          var b_option_data_value = this.yuqingIndex / 100;
          console.log(b_option_data_value)
          this.b_option.series[0].data[0].value = b_option_data_value;
          // 将分数值改为中文显示
          this.b_option.series[0].label.normal.formatter = this.decideScoreColor(b_option_data_value).score;
          // 更改bottom_echarts颜色
          this.b_option.series[0].data[0].itemStyle.normal.color = this.decideScoreColor(b_option_data_value).color;
          this.b_option.series[0].label.normal.color = this.decideScoreColor(b_option_data_value).color;
          this.b_option.series[0].outline.itemStyle.borderColor = this.decideScoreColor(b_option_data_value).color;
        });
        axios.get('/api/getCountControl', {params: {}}).then(response => {
          this.dynamics = []
          let dytemp = response.data.split('|')
          this.datetitle = dytemp[0]
          for (let i = 1; i < dytemp.length; i++) {
            let wk = dytemp[i].split('&')
            let words = wk[0].split('$')
            let keys = wk[1].split(',')
            this.dynamics.push({'words': words, 'keys': keys})
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
      },
      goto: function (e) {
        if (e.target.tagName === 'SELECT') {
          e.stopPropagation();
        } else {
          document.location.href = Common.addr + Common.page0;
        }
      }
    },
    watch: {
      city_select: function () {
        this.updateData()
      }
    },
    beforeDestroy () {
      clearInterval(this.intervalID)
    },
    components: {
      Echarts,
      LiquidFill
    }
  }
</script>

<style lang="sass">
  @import "~assets/sass/common"

  .real-body
    // background-image: url("~assets/images/page0-bg.png")
    background-color: #013660
    background-size: 100% 100%
    overflow-y: auto
    padding-bottom: 1rem
    position: relative
    padding-top: 250px
    cursor: pointer
  .main-title
    position: absolute
    top: 0
    left: 2.5rem
    background-image: url("~assets/images/page0-bg.jpg"), url('~assets/images/page0-bg-pure.jpg')
    background-position: left, right
    background-repeat: no-repeat, repeat
    text-align: center
    height: 172px
    width: 100%
    max-width: 100%
    margin: 0 -2.5rem
    img
      width: 100%
  .con-container
    // padding-left: 16%
    display: flex
  .con-box
    width: 78%
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
    flex: 0 0 15rem
    &.last
      padding-top: 0
  .con-txt
    line-height: 2
    color: #fff
    text-align: left
    display: block
    margin-left: 2rem
    h3
      position: relative
      padding: 0 .5rem 4px
      margin-bottom: .8rem
      font-size: 2.8rem
      display: inline-block
      border-bottom: 4px solid #1b6cc9
      &.last
        border-color: #c8781c
      span
        color: #fff
        margin-left: 175px
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
  .con-title
    width: 22%
    text-align: center
    padding: 4rem 0
    display: flex
    flex-direction: column
    justify-content: space-between
  .city-select
    cursor: pointer
    position: absolute
    z-index: 999
    left: .5rem
    max-width: 160px
    height: 65px!important
    line-height: 55px
    font-size: 2.4rem
    margin-right: 10px
    background-color: #1b6cc9
    color: #fff
    padding: 1px 10px
    border: 2px solid #1b6cc9
    background: url("~assets/images/select_arrow_white.svg") no-repeat right .5rem center 
    background-size: 18px 20px
    option
      color: black
    &:focus
      background-color: #1b6cc9
      color: #fff
</style>
