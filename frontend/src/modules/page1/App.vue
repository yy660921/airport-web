<template>
  <div id="app" class="real-body">
    <div class="con-box l-t-box">
      <Echarts theme="ring" :option="l_t_option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-t-box">
      <Echarts theme="ring" :option="r_t_option" className="chart" ></Echarts>
    </div>
    <div class="center-box">地球仪</div>
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
  data () {
    return {
      l_t_option: {
        title: {
          text: '风险走势',
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
          // 坐标轴刻度
          axisTick: {

          }
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
      r_t_option: {
        animation: true,
        title: {
          text: '热门旅客出发地分布',
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
        },
        tooltip: {
          trigger: 'axis',
        },
        xAxis: {
          type: 'value',
          position: 'top',
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
          splitLine: {
            lineStyle: {
              color: '#1a41ac'
            }
          }
        },
        yAxis: {
          type: 'category',
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
          axisTick: {
            show: false
          },
          splitLine: {
            lineStyle: {
              type: 'solid',
              color: '#cfc3bd'
            }
          },
          boundaryGap: false,
          data: ['北京', '天津', '石家庄', '郑州', '上海', '深圳', '广州', '昆明', '贵阳', '西藏']
        },
        grid: {
          top: 60,
          left: 60,
          right: 20,
          bottom: 10
        },
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: 14,
            barGap: '-100%',
            itemStyle: {
              normal: {
                color: 'rgba(64, 42, 31, .8)',
              }
            },
            // TODO: 动态获取坐标轴的最大值
            // data: data.map(function() {return xMax})
            data: ['3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000']
          },
          {
            name: '旅客人数',
            type: 'bar',
            barWidth: 14,
            label: {
              normal: {
                textStyle: {
                  color: '#682d19',
                },
                position: 'right',
                show: false,
                formatter: '{b}'
              }
            },
            itemStyle: {
              normal: {
                color: new Graphic.LinearGradient(1, 0, 0, 0, [{
                  offset: 0,
                  color: 'rgba(255, 162, 82, 1)'
                }, {
                  offset: 1,
                  color: 'rgba(241, 90, 34, 1)'
                }])
              }
            },
            data: [500, 700, 800, 900, 1200, 1600, 1900, 2200, 2600, 2900]
          }
        ]
      }
    }
  },
  components: {
    'Echarts': Echarts,
  }
}
</script>

<style lang="sass">
  @import "~assets/sass/common"

  .con-box
    position: absolute
    width: 26.5rem
    height: 16.8rem
    padding: .7rem 1rem .8rem
    background: url("~assets/images/box-bg.png") no-repeat
    &.l-t-box
      left: 2.5rem
      top: 2.5rem
    &.r-t-box
      right: 2.5rem
      top: 2.5rem
  .center-box
    position: absolute
    width: 36rem
    height: 34rem
    left: 0
    top: 0
    bottom: 0
    right: 0
    margin: auto  
    z-index: 1000 
</style>
