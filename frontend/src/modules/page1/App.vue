<template>
  <div id="app" class="real-body">
    <div class="con-box l-t-box">
      <Echarts theme="ring" :option="l_t_option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-t-box">
      <Echarts theme="ring" :option="r_t_option" className="chart" ></Echarts>
    </div>
    <div class="con-box l-b-box">
      <Echarts theme="ring" :option="l_b_option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-b-box">
      <Echarts theme="ring" :option="r_b_option" className="chart" ></Echarts>
    </div>
    <div class="center-box">
      <div class="chart" id="echarts-globe"></div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">

  import Vue from 'vue'
  import 'components/charts/theme/Ring.js'
  import Graphic from 'echarts/lib/util/graphic'
  import Echarts from 'vue-echarts-v3/src/full.js'

  import echarts from 'echarts'
  require('echarts-gl')

  const baseTexture = new Image();
  baseTexture.src = require('../../assets/map-rc/data-1491890179041-Hkj-elqpe.jpg');

  const heightTexture = new Image();
  heightTexture.src = require('../../assets/map-rc/data-1491889019097-rJQYikcpl.jpg');

  const blendTexture = new Image();
  blendTexture.src = require('../../assets/map-rc/data-1491890291849-rJ2uee5ag.jpg')

  const overlayTexture = new Image();
  overlayTexture.src = require('../../assets/map-rc/data-1491890092270-BJEhJg96l.png')

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
        },
        globe_t_option: {
          backgroundColor: 'rgba(0, 0, 0, 0)',
          globe: {
            globeRadius: 70,
            baseTexture: baseTexture.src,
            heightTexture: heightTexture.src,
            displacementScale: 0.1,
            shading: 'lambert',
            light: {
              ambient: {
                intensity: 0.5
              },
              main: {
                intensity: 1.25
              }
            },
            layers: [{
              type: 'blend',
              blendTo: 'emission',
              texture: blendTexture.src,
            }],
            viewControl: {
              alpha: 30,
              beta: -160,
              autoRotate: true,
            },
          },
          series: [{
            type: 'lines3D',
            coordinateSystem: 'globe',
            name: '航线',
            lineStyle: {
              color: '#0087f4',
              opacity: 0,
              width: 1,
            },
            effect: {
              show: true,
              period: 2,
              trailWidth: 3,
              trailLength: 0.5,
              trailOpacity: 1,
              trailColor: '#0087f4'
            },
            data: [
              [[-72, 38], [109.21, 27.73]],
              [[109.22, 27.73], [37, 55]],
              [[109.22, 27.73], [98, 55]],
              [[109.22, 27.73], [80, 10]],
            ],
          }]
        },
      }
    },
    mounted () {
      this.echartsGlobe();
    },
    methods: {
      echartsGlobe () {
        let globediv = echarts.init(document.getElementById('echarts-globe'));
        globediv.setOption(this.globe_t_option);
      },
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
    width: 30%
    height: 35%
    padding: .7rem 1rem .8rem
    background-image: url("~assets/images/box-bg.png") 
    background-size: 100% 100%
    z-index: 1000
    &.l-t-box
      left: 2.5rem
      top: 2.5rem
    &.r-t-box
      right: 2.5rem
      top: 2.5rem
    &.l-b-box
      left: 2.5rem
      bottom: 2.5rem
    &.r-b-box
      right: 2.5rem
      bottom: 2.5rem
  .center-box
    position: relative
    width: 100%
    height: 100%
    z-index: 0
    .chart
      width: 100%
      height: 100%
</style>
