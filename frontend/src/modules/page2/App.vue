<template>
  <div id="app" class="real-body">
    <div class="guest-info">
      <div class="info-score">
        <div class="circle circle-5">
          <img src="~assets/images/circle-5.png" alt="">
        </div>
        <div class="circle circle-4">
          <img src="~assets/images/circle-4.png" alt="">
        </div>
        <div class="circle circle-3">
          <img src="~assets/images/circle-3.png" alt="">
        </div>
        <div class="circle circle-2">
          <img src="~assets/images/circle-2.png" alt="">
        </div>
        <div class="circle circle-1">
          <!-- <img src="~assets/images/circle-1.png" alt=""> -->
          <span>77</span>
        </div>
      </div>
      <div class="info-detail">
        <p>姓名：<span>张三</span></p>
        <p>国籍：<span>中国</span></p>
        <p>出生日期：<span>1988-01-01</span></p>
        <p>护照号：<span>XXXXXXXXXX</span></p>
        <p>最近一次航班出发地：<span>北京</span></p>
        <p>最近一次航班目的地：<span>铜仁</span></p>
        <p>入境时间：<span>2017-12-13 12:34:56</span></p>
      </div>
    </div>
    <div class="guest-charts clearfix">
      <div class="con-box left-box">
        <Echarts theme="ring" :option="left_option" className="chart" ></Echarts>
      </div>
      <div class="con-box center-box">
        <Echarts theme="ring" :option="center_option" className="chart" ></Echarts>
      </div>
      <div class="con-box right-box">
        <Echarts theme="ring" :option="right_option" className="chart" ></Echarts>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">

import Vue from 'vue'
import 'components/charts/theme/Ring.js'
import Graphic from 'echarts/lib/util/graphic'
import Echarts from 'vue-echarts-v3/src/full.js'

/* Globally bus for Non Parent-Child Communication */
window.bus = new Vue();

export default {
  name: 'app',
  data () {
    return {
      left_option: {
        title: {
          text: '入境时间分析',
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
        },
        tooltip: {},
        xAxis: {
          data: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08'],
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          boundaryGap: false,
        },
        yAxis: {
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              // type: 'solid',
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              color: '#1a41ac'
            }
          }
        },
        grid: {
          top: 50,
          left: 30,
          right: 20,
          bottom: 25
        },
        series: [
          {
            name: '选项B',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(252, 143, 47, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(252, 143, 47, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(252, 143, 47, .3)'
                }])
              }
            },
            data: [20, 10, 20, 30, 35, 26, 36, 30]
          },
          {
            name: '选项A',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(41, 202, 255, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(59, 159, 237, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(59, 159, 237, .3)'
                }])
              }
            },
            data: [10, 20, 30, 15, 12, 20, 38, 20]
          }
        ]
      },
      center_option: {
        title: {
          text: '地理活动轨迹分析',
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
        },
        tooltip: {},
        xAxis: {
          data: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08'],
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          boundaryGap: false,
        },
        yAxis: {
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              // type: 'solid',
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              color: '#1a41ac'
            }
          }
        },
        grid: {
          top: 50,
          left: 30,
          right: 20,
          bottom: 25
        },
        series: [
          {
            name: '选项B',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(252, 143, 47, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(252, 143, 47, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(252, 143, 47, .3)'
                }])
              }
            },
            data: [20, 10, 20, 30, 35, 26, 36, 30]
          },
          {
            name: '选项A',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(41, 202, 255, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(59, 159, 237, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(59, 159, 237, .3)'
                }])
              }
            },
            data: [10, 20, 30, 15, 12, 20, 38, 20]
          }
        ]
      },
      right_option: {
        title: {
          text: '紧密联系人/同行分析',
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
        },
        tooltip: {},
        xAxis: {
          data: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08'],
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          boundaryGap: false,
        },
        yAxis: {
          // 坐标轴轴线
          axisLine: {
            lineStyle: {
              width: 2,
              // type: 'solid',
              color: '#1a41ac'
            }
          },
          // 坐标轴刻度标签
          axisLabel: {
            color: '#fff',
            fontSize: 14
          },
          splitLine: {
            lineStyle: {
              color: '#1a41ac'
            }
          }
        },
        grid: {
          top: 50,
          left: 30,
          right: 20,
          bottom: 25
        },
        series: [
          {
            name: '选项B',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(252, 143, 47, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(252, 143, 47, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(252, 143, 47, .3)'
                }])
              }
            },
            data: [20, 10, 20, 30, 35, 26, 36, 30]
          },
          {
            name: '选项A',
            type: 'line',
            itemStyle: {
              normal: {
                color: 'rgba(41, 202, 255, .8)'
              }
            },
            areaStyle: {
              normal: {
                color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(59, 159, 237, .9)'
                }, {
                  offset: 1,
                  color: 'rgba(59, 159, 237, .3)'
                }])
              }
            },
            data: [10, 20, 30, 15, 12, 20, 38, 20]
          }
        ]
      }
    }
  },
  components: {
    Echarts
  }
}
</script>

<style lang="sass">
  @import "~assets/sass/common"
  .guest-info
    margin: 0 auto
    padding-top: 2rem
    display: flex
    justify-content: center
  .info-score
    position: relative
    width: 15rem
    height: 15rem
    margin-right: 3rem
    .circle
      position: absolute
      width: 100%
      img
        width: 100%
    .circle-1
      text-align: center
      line-height: 15rem
      color: #fff
      font-size: 3rem
      font-weight: bold
      font-family: "Montserrat"
    .circle-2
      animation: rotate_2 6s 1s infinite linear
    .circle-3
      animation: rotate_3 10s 2s infinite linear
    .circle-4
      animation: rotate_4 12s 3s infinite linear
    .circle-5
      animation: rotate_5 15s 4s infinite linear
  .info-detail
    color: #fff
    font-size: .85rem
  .guest-charts
    position: relative
    bottom: -6rem
    .con-box
      float: left
      height: 20rem
      padding: .7rem 1rem .8rem
      background-size: 100% 100%
      &.left-box
        width: 27%
        margin-right: 1%
        background-image: url("~assets/images/page2-left-bg.png")
      &.center-box
        width: 41%
        margin: 0 1.5%
        background-image: url("~assets/images/page2-center-bg.png")
      &.right-box
        width: 27%
        margin-left: 1%
        background-image: url("~assets/images/page2-left-bg.png")

</style>
