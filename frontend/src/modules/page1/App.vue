<template>
  <div id="app" class="real-body">
    <div class="con-box l-t-box" @click="goto">
      <Echarts theme="ring" :option="options.left_up.option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-t-box" @click="goto">
      <Echarts theme="ring" :option="options.right_up.option" className="chart" ></Echarts>
    </div>
    <div class="con-box l-b-box" @click="goto">
      <Echarts theme="ring" :option="options.left_down.option" className="chart" ></Echarts>
    </div>
    <div class="con-box r-b-box" @click="goto">
      <Echarts theme="ring" :option="options.right_down.option" className="chart" ></Echarts>
    </div>
    <div class="center-box">
      <div class="chart" id="echarts-globe"></div>
    </div>
    <a href="/page2" class="next-page">
      <img src="~assets/images/next-page.png" alt="">
    </a>
    <a href="/" class="next-home">
      <img src="~assets/images/next-home.png" alt="">
    </a>
  </div>
</template>

<script type="text/ecmascript-6">

  import 'components/charts/theme/Ring.js'
  import Echarts from 'vue-echarts-v3/src/full.js'

  import echarts from 'echarts'
  require('echarts-gl')

  import Common from 'components/Common.js'

  import { ChartLib } from './ChartLib.js'

  import BaseTexture from 'components/texture/Base.js'
  import HeightTexture from 'components/texture/Height.js'
  import BlendTexture from 'components/texture/Blend.js'
  // import OverlayTexture from 'components/texture/Overlay.js'

  const baseTexture = new Image();
  baseTexture.src = BaseTexture.texture;
  // baseTexture.crossOrigin = 'anonymous';
  // baseTexture.src = require('../../assets/map-rc/data-1491890179041-Hkj-elqpe.jpg');

  const heightTexture = new Image();
  heightTexture.src = HeightTexture.texture;
  // heightTexture.crossOrigin = 'anonymous';
  // heightTexture.src = require('../../assets/map-rc/data-1491889019097-rJQYikcpl.jpg');

  const blendTexture = new Image();
  blendTexture.src = BlendTexture.texture;
  // blendTexture.crossOrigin = 'anonymous';
  // blendTexture.src = require('../../assets/map-rc/data-1491890291849-rJ2uee5ag.jpg')

  // This one is indeed not needed.
  // const overlayTexture = new Image();
  // overlayTexture.src = GlobeTexture.overlayTexture;
  // // overlayTexture.crossOrigin = 'anonymous';
  // // overlayTexture.src = require('../../assets/map-rc/data-1491890092270-BJEhJg96l.png')

  export default {
    data () {
      return {
        Common: Common,
        intervalID: null,
        intervalRotate: null,
        nowCityIndex: 0,
        rotateCities: [],
        options: {
          left_up: { option: {}, update: () => { return; } },
          right_up: { option: {}, update: () => { return; } },
          left_down: { option: {}, update: () => { return; } },
          right_down: { option: {}, update: () => { return; } },
        },
        globe_t_option: {
          backgroundColor: 'rgba(0, 0, 0, 0)',
          //  title: {
          //    text: '铜仁 -> 北京',
          //    left: 'center',
          //    bottom: '80',
          //    textStyle: {
          //      color: '#fff',
          //      width: '200%',
          //      fontSize: 40
          //    },
          //    padding: [20, 50],
          //    borderWidth: 2,
          //    borderColor: 'rgb(1, 241, 228)',
          //    borderRadius: 10,
          //    backgroundColor: 'rgba(29, 81, 203, .8)',
          //    zlevel: 101
          //  },
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
              alpha: 27,
              // beta: -160,
              autoRotate: true,
              autoRotateSpeed: 20,
              zoomSensitivity: 1,
              // rotateSensitivity: 0,
              // targetCoord: [110, 33],
              animationDurationUpdate: 1250,
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
      this.initOptions();
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 100 * 1000);

      this.intervalGlobe = setInterval(() => {
        if (this.globe) {
          this.globe.clear();
          this.echartsGlobe();
        }
      }, 1 * 60 * 60 * 1000);
      // console.log(Common.addr + '');
      // this.intervalRotate = setInterval(() => {
      //   this.rotate_globe()
      // }, 4 * 1000);
      axios.get('/api/getPageJump', {params: {}}).then(response => {
        // alert(response.data.page0.to)
        // eslint-disable-next-line no-unused-vars
        let timer = setTimeout(function () { location.href = '../' + response.data.page1.to; } , response.data.page1.delay * 1000);
      })
    },
    methods: {
      goto: function () {
        document.location.href = Common.addr + Common.page1;
      },
      initOptions: function () {
        axios.get('/api/getShowCharts').then(response => {
          _.each(response.data, (v, k) => {
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
      update_globe_option () {
        this.globe_t_option.globe.displacementScale = 0.1249;
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
          this.globe_t_option.globe.displacementScale = 0.125;
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
          // let centerl = [110, 33]
          let cantern = '铜仁'
          if (tmpy === 0) {
          //  centerl = this.rotateCities[tmpx].departure.Coordinate;
            cantern = this.rotateCities[tmpx].departure.CityName + ' -> ' + this.rotateCities[tmpx].destination.CityName;
          } else {
          //  centerl = this.rotateCities[tmpx].destination.Coordinate;
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
                // targetCoord: centerl,
              },
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
