<template>
  <div id="app" class="real-body">
    <div class="con-box l-t-box" @click="goto">
      <Echarts theme="ring" :option="l_t_option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-t-box" @click="goto">
      <Echarts theme="ring" :option="r_t_option" className="chart" ></Echarts>
    </div>
    <div class="con-box l-b-box" @click="goto">
      <Echarts theme="ring" :option="l_b_option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-b-box" @click="goto">
      <Echarts theme="ring" :option="r_b_option" className="chart" ></Echarts>
    </div>
    <div class="center-box">
      <div class="chart" id="echarts-globe"></div>
    </div>
    <a href="./page2" class="next-page">
      <img src="~assets/images/next-page.png" alt="">
    </a>
  </div>
</template>

<script type="text/ecmascript-6">

  import 'components/charts/theme/Ring.js'
  import Graphic from 'echarts/lib/util/graphic'
  import Echarts from 'vue-echarts-v3/src/full.js'

  import echarts from 'echarts'
  require('echarts-gl')

  import Common from 'components/Common.js'

  const baseTexture = new Image();
  baseTexture.crossOrigin = 'anonymous';
  baseTexture.src = require('../../assets/map-rc/data-1491890179041-Hkj-elqpe.jpg');

  const heightTexture = new Image();
  heightTexture.crossOrigin = 'anonymous';
  heightTexture.src = require('../../assets/map-rc/data-1491889019097-rJQYikcpl.jpg');

  const blendTexture = new Image();
  blendTexture.crossOrigin = 'anonymous';
  blendTexture.src = require('../../assets/map-rc/data-1491890291849-rJ2uee5ag.jpg')

  const overlayTexture = new Image();
  overlayTexture.crossOrigin = 'anonymous';
  overlayTexture.src = require('../../assets/map-rc/data-1491890092270-BJEhJg96l.png')

  export default {
    data () {
      return {
        Common: Common,
        intervalID: null,
        intervalRotate: null,
        nowCityIndex: 0,
        rotateCities: [],
        l_t_option: {
          title: {
            text: '风险走势',
          },
          tooltip: {
            trigger: 'axis',
          },
          xAxis: {
            data: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08'],
            boundaryGap: false,
          },
          yAxis: {},
          grid: {
            top: 50,
            left: 40,
            right: 20,
            bottom: 25
          },
          series: [
            {
              name: '查获物件',
              type: 'line',
              itemStyle: {
                normal: {
                  color: '#1e98ff',
                }
              },
              areaStyle: {
                normal: {
                  color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(30, 152, 255, .9)'
                  }, {
                    offset: 1,
                    color: 'rgba(30, 152, 255, .3)'
                  }])
                }
              },
              data: [20, 10, 20, 30, 35, 26, 36, 30]
            },
            {
              name: '风险旅客',
              type: 'line',
              itemStyle: {
                normal: {
                  color: '#ff5421'
                }
              },
              areaStyle: {
                normal: {
                  color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(255, 84, 33, .9)'
                  }, {
                    offset: 1,
                    color: 'rgba(255, 84, 33, .3)'
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
            text: '热门城市'
          },
          tooltip: {
            trigger: 'axis',
          },
          xAxis: {
            type: 'value',
            position: 'top',
          },
          yAxis: {
            type: 'category',
            axisLine: {
              lineStyle: {
                width: 2,
                color: '#1a41ac'
              }
            },
            axisLabel: {
              rotate: 15,
            },
            data: ['北京', '天津', '石家庄', '郑州', '上海', '深圳', '广州', '昆明', '贵阳', '西藏']
          },
          grid: {
            top: 65,
            left: 75,
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
              tooltip: {
                show: false,
              },
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
                  }]),
                  barBorderRadius: [0, 10, 10, 0],  // 圆角
                }
              },
              data: [500, 700, 800, 900, 1200, 1600, 1900, 2200, 2600, 2900]
            }
          ]
        },
        l_b_option: {
          animation: true,
          title: {
            text: '风险旅客',
          },
          tooltip: {
            trigger: 'axis',
          },
          xAxis: {
            type: 'value',
            position: 'top',
          },
          yAxis: {
            type: 'category',
            splitLine: {
              show: false,
            },
            axisLabel: {
              rotate: 15,
            },
            data: ['北京', '天津', '石家庄', '郑州', '上海', '深圳', '广州', '昆明', '贵阳', '西藏']
          },
          grid: {
            top: 65,
            left: 75,
            right: 20,
            bottom: 10
          },
          series: [
            {
              name: '',
              type: 'bar',
              barWidth: 14,
              barGap: '-100%',
              silent: true,
              itemStyle: {
                normal: {
                  color: 'rgba(42, 56, 144, .8)',
                }
              },
              tooltip: {
                show: false,
              },
              data: ['3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000', '3000']
            },
            {
              name: '风险值',
              type: 'bar',
              barWidth: 14,
              label: {
                normal: {
                  textStyle: {
                    color: '#682d19',
                  },
                  position: 'right',
                  formatter: '{b}'
                }
              },
              itemStyle: {
                normal: {
                  color: new Graphic.LinearGradient(1, 0, 0, 0, [{
                    offset: 0,
                    color: 'rgba(129, 153, 255, 1)'
                  }, {
                    offset: 1,
                    color: 'rgba(18, 34, 134, 1)'
                  }]),
                  barBorderRadius: [0, 10, 10, 0],  // 圆角
                }
              },
              data: [500, 700, 800, 900, 1200, 1600, 1900, 2200, 2600, 2900]
            }
          ]
        },
        r_b_option: {
          animation: true,
          title: {
            text: '查获时段',
          },
          tooltip: {
            trigger: 'axis',
          },
          xAxis: {
            type: 'category',
            splitLine: {
              show: false,
            },
            data: ['0:00','1:00','2:00','3:00','4:00','5:00','6:00','7:00','8:00','9:00','10:00','11:00','12:00','13:00','14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00']
          },
          yAxis: {
            type: 'value',
            position: 'top',
          },
          grid: {
            top: 50,
            left: 40,
            right: 20,
            bottom: 25
          },
          series: [
            {
              name: '',
              type: 'bar',
              barWidth: 13,
              barGap: '-100%',
              itemStyle: {
                normal: {
                  color: 'rgba(24, 50, 117, .8)',
                }
              },
              tooltip: {
                show: false,
              },
              data: [150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150,150]
            },
            {
              name: '设备查验次数',
              type: 'bar',
              barWidth: 13,
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
                  color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(38, 216, 255, 1)'
                  }, {
                    offset: 1,
                    color: 'rgba(32, 84, 221, 1)'
                  }]),
                  barBorderRadius: [10, 10, 0, 0],  // 圆角
                }
              },
              data: [0,0,67,0,30,0,140,20,112,127,113,20,5,101,90,77,116,28,68,80,30,61,88,93]
            }
          ]
        },
        globe_t_option: {
          backgroundColor: 'rgba(0, 0, 0, 0)',
          title: {
            text: '铜仁 -> 北京',
            left: 'center',
            bottom: '80',
            textStyle: {
              color: '#fff',
              width: '200%',
              fontSize: 40
            },
            padding: [20, 50],
            borderWidth: 2,
            borderColor: 'rgb(1, 241, 228)',
            borderRadius: 10,
            backgroundColor: 'rgba(29, 81, 203, .8)',
            zlevel: 101
          },
          globe: {
            globeRadius: 95,
            globeOuterRadius: 500,
            baseTexture: baseTexture.src,
            heightTexture: heightTexture.src,
            displacementScale: 0.2,
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
              // alpha: 30,
              // beta: -160,
              autoRotate: false,
              zoomSensitivity: 0,
              targetCoord: [110, 33]
            },
          },
          series: [{
            name: '城市',
            type: 'scatter3D',
            coordinateSystem: 'globe',
            zlevel: 100,
            animation: true,
            blendMode: 'lighter',
            symbol: 'pin',
            symbolSize: 30,
            itemStyle: {
              normal: {
                color: '#fa3434',
                opacity: 1,
              },
            },
            label: {
              normal: {
                show: true,
                formatter: '{b}',
                distance: 5,
                textStyle: {
                  color: '#fff',
                  padding: 5,
                  fontSize: 20,
                  borderWidth: 1,
                  borderColor: '#0775e4',
                  backgroundColor: 'rgba(29, 81, 203, .8)',
                },
              },
            },
            data: [],
          }, {
            type: 'lines3D',
            coordinateSystem: 'globe',
            name: '航线',
            lineStyle: {
              color: '#00abe0',
              opacity: 1,
              width: 4,
            },
            effect: {
              show: false,
              period: 4,
              // constantSpeed: 30,
              symbol: 'arrow',
              trailWidth: 3,
              trailLength: 0.8,
              trailOpacity: 1,
              // trailColor: '#ff4d43'
            },
            data: [],
          }]
        },
        globe: null,
      }
    },
    mounted () {
      this.echartsGlobe();
    },
    created () {
      this.updateData();
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 30 * 1000);
      this.intervalGlobe = setInterval(() => {
        if (this.globe) {
          this.globe.dispose();
          this.echartsGlobe();
        }
      }, 1 * 60 * 60 * 1000);
      // console.log(Common.addr + '');
      this.intervalRotate = setInterval(() => {
        this.rotate_globe()
      }, 3 * 1000);
    },
    methods: {
      goto: function () {
        document.location.href = Common.addr + Common.page1;
      },
      updateData: function () {
        this.update_r_t_option();
        this.update_l_t_option();
        this.update_l_b_option();
        this.update_r_b_option();
        this.update_globe_option();
      },
      echartsGlobe () {
        this.globe = echarts.init(document.getElementById('echarts-globe'));
        this.globe.setOption(this.globe_t_option);
        // this.globe.on('click', function (params) {
        //   console.log(params);
        //   console.log(new Error().stack);
        // });
      },
      update_l_t_option: function () {
        axios.get('/api/getRTAndSN', {params: {}}).then(response => {
          if (response.data.createDate.length > 0 && response.data.tourist_warningEvents.length > 0 && response.data.seizure_number.length > 0) {
            this.l_t_option.xAxis.data = response.data.createDate;
            this.l_t_option.series[0].data = response.data.tourist_warningEvents;
            this.l_t_option.series[1].data = response.data.seizure_number;
          }
        });
      },
      update_r_t_option: function () {
        axios.get('/api/getDeparture', {params: {}}).then(response => {
          if (response.data.Departures.length > 0 && response.data.DepartureCount.length > 0) {
            this.r_t_option.yAxis.data = response.data.Departures;
            this.r_t_option.series[1].data = response.data.DepartureCount;
            var maxd = _.max(response.data.DepartureCount);
            this.r_t_option.series[0].data = _.map(response.data.DepartureCount, (obj, idx) => { return maxd })
            this.r_t_option.xAxis.max = maxd;
          }
        });
      },
      update_l_b_option: function () {
        axios.get('/api/getTAndRI', {params: {}}).then(response => {
          if (response.data.TouristName.length > 0 && response.data.TouristRiskIndex.length > 0) {
            this.l_b_option.yAxis.data = response.data.TouristName;
            this.l_b_option.series[1].data = response.data.TouristRiskIndex;
            var maxd = _.max(response.data.TouristRiskIndex);
            this.l_b_option.series[0].data = _.map(response.data.TouristRiskIndex, (obj, idx) => { return maxd })
            this.l_b_option.xAxis.max = maxd;
          }
        });
      },
      update_r_b_option: function () {
        axios.get('/api/getDCD', {params: {}}).then(response => {
          if (response.data.TimeLine.length > 0 && response.data.Count.length > 0) {
            this.r_b_option.xAxis.data = response.data.TimeLine;
            this.r_b_option.series[1].data = response.data.Count;
            var maxd = _.max(response.data.Count);
            this.r_b_option.series[0].data = _.map(response.data.Count, (obj, idx) => { return maxd })
            this.r_b_option.yAxis.max = maxd;
          }
        });
      },
      update_globe_option () {
        this.globe_t_option.globe.displacementScale = 0.2;
        axios.get('/api/getAirway', {params: {
          from: Math.floor(new Date().getTime() / 1000) - 24 * 3600,
          to: Math.floor(new Date().getTime() / 1000),
        }}).then(response => {
          let cities = [];
          let lines = [];
          _.each(response.data, item => {
            cities.push({name: item.departure.CityName, value: item.departure.Coordinate});
            cities.push({name: item.destination.CityName, value: item.destination.Coordinate});
            lines.push([item.departure.Coordinate, item.destination.Coordinate]);
          });
          this.globe_t_option.series[0].data = cities; // _.uniqBy(cities, 'name');
          this.globe_t_option.series[1].data = lines;
          this.globe_t_option.globe.displacementScale = 0.1;
          if (this.globe) {
            this.globe.setOption(this.globe_t_option);
          }
          this.rotateCities = response.data;
        });
      },
      rotate_globe: function () {
        if (!_.isEmpty(this.rotateCities)) {
          let tmpx = Math.floor(this.nowCityIndex / 2);
          let tmpy = this.nowCityIndex % 2;
          // console.log(tmpx + "  " + tmpy + " length:" + this.rotateCities.length)
          let centerl = [110, 33]
          let cantern = '铜仁'
          if (tmpy === 0) {
            centerl = this.rotateCities[tmpx].departure.Coordinate;
            cantern = this.rotateCities[tmpx].departure.CityName + ' -> ' + this.rotateCities[tmpx].destination.CityName;
          } else {
            centerl = this.rotateCities[tmpx].destination.Coordinate;
            cantern = this.rotateCities[tmpx].departure.CityName + ' -> ' + this.rotateCities[tmpx].destination.CityName;
          }
          this.nowCityIndex = this.nowCityIndex + 1;
          this.nowCityIndex = Math.floor(this.nowCityIndex / 2) >= this.rotateCities.length ? 0 : this.nowCityIndex;
          this.globe.setOption({
            title: {
              text: cantern
            },
            globe: {
              viewControl: {
                targetCoord: centerl
              }
            }
          });
        }
      }
    },
    beforeDestroy () {
      clearInterval(this.intervalID)
      clearInterval(this.intervalGlobe)
      clearInterval(this.intervalRotate)
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
    cursor: pointer
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
    position: absolute
    left: 0rem
    top: 0rem
    width: 100%
    height: 100%
    z-index: 0
    .chart
      width: 100%
      height: 100%
      cursor: pointer
</style>
