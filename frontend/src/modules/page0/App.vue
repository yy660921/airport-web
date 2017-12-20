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
          <p class="para">预警查获事件数量为<strong class="txt-block"><span>1</span><span>3</span><span>8</span></strong></p>
          <p class="para">其中涉及有风险旅客和重点旅客<strong class="txt-block"><span>1</span><span>3</span><span>8</span></strong>人（境内<strong class="txt-block"><span>2</span><span>0</span><span>0</span></strong>人，境外<strong class="txt-block"><span>1</span><span>1</span><span>4</span></strong>人）
          </p>
          <p class="para">查获物品<strong class="txt-block"><span>3</span><span>9</span><span>9</span></strong>件（违禁品<strong class="txt-block"><span>1</span><span>9</span><span>9</span></strong>件，高价值税品<strong class="txt-block"><span>2</span><span>0</span><span>0</span></strong>件）</p>
          <p class="para">监管人员总数<strong class="txt-block"><span>2</span><span>3</span></strong>设备查验总数<strong class="txt-block"><span>2</span><span>3</span><span>3</span></strong>次</p>
        </div>
      </div>
      <div class="con clearfix">
        <div class="con-chart last">
          <Echarts theme="ring" :option="b_option" className="chart" ></Echarts>
        </div>
        <div class="con-txt">
          <h3 class="last">近<span>24</span>小时</h3>
          <p class="para">外网相关舆情总数为<strong class="txt-block"><span>7</span><span>3</span><span>8</span></strong>其中参与媒体<strong class="txt-block"><span>1</span><span>2</span><span>8</span></strong>家</p>
          <p class="para">（新华网<strong class="txt-block"><span>1</span><span>2</span><span>2</span></strong>条、中新网<strong class="txt-block"><span>1</span><span>2</span><span>0</span></strong>条）</p>
          <p class="para">参与微信公众号<strong class="txt-block"><span>3</span><span>1</span><span>4</span></strong>个（铜仁公安<strong class="txt-block"><span>3</span><span>0</span><span>4</span></strong>条，铜仁微生活<strong class="txt-block"><span>3</span><span>0</span><span>4</span></strong>条）</p>
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
        t_option: {
          series: [{
            type: 'liquidFill',
            radius: '90%',
            data: [{
                value: 0.6,
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
                value: 0.6,
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
          font-family: "Montserrat"
          font-size: 1.8rem
          height: 2.5rem
          line-height: 2.5rem
          text-align: center
          width: 28px
          margin: 0 1px
          display: inline-block
          // font-weight: bold
          background-image: url("~assets/images/word-bg.png")
          background-size: 100% 100%
</style>
