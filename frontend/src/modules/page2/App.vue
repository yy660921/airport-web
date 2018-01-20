<template>
  <div id="app" class="real-body">
    <transition name="fade">
      <div class="basic" v-show='toggle_flag'>
        <div class="guest-info">
          <div class="info-detail">
            <p><strong>基本信息：</strong></p>
            <p><i class="fa fa-user "></i>姓名：<span>{{ username }}</span></p>
            <p><i class="fa fa-intersex"></i>性别：<span>{{ sex }}</span></p>
            <p><i class="fa fa-flag"></i>国籍：<span>{{ country }}</span></p>
            <p><i class="fa fa-calendar"></i>出生日期：<span>{{ birth }}</span></p>
            <p><i class="fa fa-drivers-license-o"></i>护照号：<span>{{ passportID }}</span></p>
          </div>
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
              <span :class="{'risk-middle': riskMiddle, 'risk-height': riskHeight }">{{ riskIndex }}</span>
            </div>
          </div>
          <div class="info-detail">
            <p><strong>活动信息：</strong></p>
            <p><i class="fa fa-map-marker"></i>本次出发地：<span>{{ departure }}</span></p>
            <p><i class="fa fa-location-arrow"></i>本次目的地：<span>{{ destination }}</span></p>
            <p><i class="fa fa-calendar "></i>本次入境时间：<span>{{ intime }}</span></p>
            <p><i class="fa fa-plane"></i>最近一年入境次数：{{ warningTourist_arrival_number }}</p>
            <p><i class="fa fa-plane"></i>高风险入境次数：{{ warningTourist_arrival_risknumber }}</p>
          </div>
        </div>
        <p class="tags"><i class="fa fa-tags"></i>标签：<span class="category" v-for="label in category">{{ label }}</span></p>
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
    </transition>
    <transition name="fade">
      <div class="device" v-show="!toggle_flag">
        <div class="con-box" v-for="(item, index) in this.deviceStatus" :key="index">
          <div class="box-cont">
            <h3 class="box-title">{{item.title}}</h3>
            <div class="box-img">
              <img src="~assets/images/device-img.png" alt="">
            </div>
          </div>
          <div class="box-status" :class="item.status"></div>
        </div>
      </div>
    </transition>
  </div>
</template>

<script type="text/ecmascript-6">

import Vue from 'vue'
import 'components/charts/theme/Ring.js'
import Graphic from 'echarts/lib/util/graphic'
import Echarts from 'vue-echarts-v3/src/full'
import 'echarts/map/js/world'

export default {
  name: 'app',
  data () {
    return {
      intervalID: null,
      number_all: 0,
      number_now: 0,
      resData: [],
      username: 'Mcneal',
      category: ['重点人物-携高价值物品'],
      sex: '男',
      country: '中国',
      birth: '1990-01-01',
      passportID: 'J94875326',
      departure: '巴黎',
      destination: '铜仁',
      intime: '2017-12-16',
      riskIndex: '60',
      riskMiddle: false,
      riskHeight: false,
      warningTourist_arrival_number: 6,
      warningTourist_arrival_risknumber: 2,
      left_option: {
        animation: true,
        title: {
          text: '入境时间分布',
        },
        tooltip: {
          trigger: 'axis',
        },
        xAxis: {
          type: 'category',
          splitLine: {
            show: false,
          },
          data: ['2017-06', '2017-07', '2017-08', '2017-09', '2017-10', '2017-11', '2017-12']
        },
        yAxis: {
          type: 'value',
          position: 'top',
        },
        grid: {
          top: 50,
          left: 25,
          right: 10,
          bottom: 25
        },
        series: [
          {
            name: '',
            type: 'bar',
            barWidth: 20,
            barGap: '-100%',
            itemStyle: {
              normal: {
                color: 'rgba(17, 69, 63, .8)',
              },
            },
            tooltip: {
              show: false,
            },
            data: [10, 10, 10, 10, 10, 10, 10]
          },
          {
            name: '入境时间次数',
            type: 'bar',
            barWidth: 20,
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
                  color: 'rgba(1, 241, 201, 1)'
                }, {
                  offset: 1,
                  color: 'rgba(8, 99, 41, 1)'
                }]),
                barBorderRadius: [10, 10, 0, 0],  // 圆角
              },
            },
            data: [7, 6, 5, 4, 3, 2, 1]
          }
        ]
      },
      center_option: {
        title: {
          text: '地理活动轨迹分析',
        },
        geo: {
          map: 'world',
          label: {
            emphasis: {
              show: false
            },
          },
          roam: false,
          layoutCenter: ['50%', '53%'],
          layoutSize: "150%",
          itemStyle: {
            normal: {
              // color: 'rgba(8, 30, 67, .9)',
              color: '#0a276b',
              borderColor: 'rgba(20, 84, 186, .8)'
            },
            emphasis: {
              color: 'rgba(41, 106, 253, .9)',
              borderColor: 'rgba(20, 84, 186, .9)'
            },
          },
        },
        visualMap: {
          type: 'continuous',
          min: 0,
          max: 100,
          show: false,
          calculable: false,
          color: ['#ff3333', 'orange', 'yellow', 'lime', 'aqua'],
          textStyle: {
            color: '#fff',
          },
        },
        series: [{
          type: 'lines',
          zlevel: 2,
          effect: {
            show: true,
            period: 4,
            trailLength: 0.02,
            symbol: 'arrow',
            symbolSize: 5,
          },
          lineStyle: {
            normal: {
              width: 1,
              opacity: 0.6,
              curveness: 0.2
            }
          },
          data: [],
        }, {
          type: 'effectScatter',
          coordinateSystem: 'geo',
          zlevel: 2,
          rippleEffect: {
            period: 4,
            brushType: 'stroke',
            scale: 4
          },
          label: {
            normal: {
              show: true,
              fontSize: 20,
              position: 'right',
              offset: [5, 0],
              formatter: '{b}'
            },
            emphasis: {
              show: true
            }
          },
          symbol: 'circle',
          symbolSize: 6,
          itemStyle: {
            normal: {
              show: false,
              color: '#f00'
            }
          },
          data: [],
        }],
      },
      right_option: {
        title: {
          text: '紧密联系人/同行分析',
        },
        tooltip: {},
        animationDurationUpdate: 1500,
        animationEasingUpdate: 'quinticInOut',
        label: {
          normal: {
            show: true,
            textStyle: {
              fontSize: 12
            },
          }
        },
        legend: {
          x: "center",
          show: false,
          data: ["朋友", "战友", '亲戚']
        },
        grid: {
          top: 40,
          left: 35,
          right: 20,
          bottom: 25
        },
        top: 20,
        color: ['#fd0e0e', '#ff652e', '#ff902e', '#ff2eda'],
        series: [
          {
            type: 'graph',
            layout: 'force',
            symbolSize: 45,
            focusNodeAdjacency: true,
            roam: true,
            categories: [
              {
                name: '朋友',
              }, {
                name: '战友',
              }, {
                name: '亲戚',
              }
            ],
            label: {
              normal: {
                show: true,
                textStyle: {
                  fontSize: 20
                },
              }
            },
            force: {
              repulsion: 1000
            },
            edgeSymbolSize: [4, 50],
            edgeLabel: {
              normal: {
                show: true,
                textStyle: {
                  fontSize: 10
                },
                formatter: "{c}"
              }
            },
            data: [
              {
                name: 'Mcneal',
                draggable: true,
                symbolSize: 60,
              }, {
                name: '李白,China,FB00312',
                category: 1,
                draggable: true,
                symbolSize: 40,
              }, {
                name: '马超,China,FP8843',
                category: 1,
                draggable: true,
                symbolSize: 80,
              }, {
                name: '耀文,China,PK2345',
                category: 1,
                draggable: true,
                symbolSize: 60,
              }
            ],
            links: [
              {
                source: 0,
                target: 1,
                category: 0,
                value: '3'
              }, {
                source: 0,
                target: 2,
                value: '1'
              }, {
                source: 0,
                target: 3,
                value: '4'
              }
            ],
            lineStyle: {
              normal: {
                opacity: 0.9,
                width: 2,
                curveness: 0
              }
            }
          }
        ]
      },
      toggle_flag: true,
      deviceStatus: [
        {
          title: '设备一',
          status: 'warining'
        },
        {
          title: '设备二',
          status: 'normal'
        },
        {
          title: '设备三',
          status: 'offline'
        }
      ],
    }
  },
  created () {
    this.updateData()
    this.intervalID = setInterval(() => {
      this.updateData();
    }, 30 * 1000);
  },
  methods: {
    // 切换页面
    updateLayout: function (pageName) {
      this.toggle_flag = pageName === 'person';
      if (!this.toggle_flag) {
        this.updateDevice();
      }
    },
    updateDevice: function () {
      // 获取数据
    },
    updateData: function () {
      if (this.number_all <= 0 || this.number_now > this.number_all) {
        this.updateLayout('person');
        axios.get('/api/getTourist', {}).then(response => {
          if (!_.isEmpty(response.data)) {
            this.resData = response.data;
          }
          this.number_all = this.resData.length;
          this.number_now = 0;
          this.updatePerson();
        })
      } else if (this.number_now === this.number_all) {
        this.updateLayout('device');
        this.number_now = this.number_now + 1
      } else {
          this.updatePerson()
      }
    },
    updatePerson: function () {
      var person = this.resData[this.number_now]
      this.number_now = this.number_now + 1
      this.username = _.isNull(person.warningTourist_name) ? this.username : person.warningTourist_name;
      // this.category = person.warningTourist_category;
      this.sex = _.isNull(person.warningTourist_sex) ? this.sex : person.warningTourist_sex;
      this.country = _.isNull(person.warningTourist_country) ? this.country : person.warningTourist_country;
      this.birth = _.isNull(person.warningTourist_birthday) ? this.birth : person.warningTourist_birthday;
      this.passportID = _.isNull(person.warningTourist_passport) ? this.passportID : person.warningTourist_passport;
      this.departure = _.isNull(person.warningTourist_departure) ? this.departure : person.warningTourist_departure;
      this.destination = _.isNull(person.warningTourist_destination) ? this.destination : person.warningTourist_destination;
      this.intime = _.isNull(person.warningTourist_time) ? this.intime : person.warningTourist_time;
      this.riskIndex = this.decideRiskIndex(_.isNull(person.warningTourist_riskIndex) ? this.riskIndex : person.warningTourist_riskIndex).score;
      this.category = _.isNull(person.warningTourist_category) ? this.category : person.warningTourist_category;
      this.warningTourist_arrival_number = _.isNull(person.warningTourist_arrival_number) ? this.warningTourist_arrival_number : person.warningTourist_arrival_number;
      this.warningTourist_arrival_risknumber = _.isNull(person.warningTourist_arrival_risknumber) ? this.warningTourist_arrival_risknumber : person.warningTourist_arrival_risknumber;
      if (!_.isEmpty(person.warningTourist_historyTime)) {
        this.update_left_option(person.warningTourist_historyTime);
      }
      if (!_.isEmpty(person.warningTourist_place)) {
        this.update_center_option(person.warningTourist_place);
      }
      if (!_.isEmpty(person.fellowTourist_list)) {
        this.update_right_option(person.fellowTourist_list);
      }
    },
    decideRiskIndex: function (value) {
      // red: #ff5252
      // yellow: #d37d1c
      // blue: #1b6cc9
      var self = this;
      // console.log(value);
      if (value > 75) {
        self.riskHeight = true;
        return {
          score: '高',
        }
      } else if (value > 60) {
        self.riskMiddle = true;
        return {
          score: '中',
        }
      } else {
        return {
          score: '低',
        }
      }
    },
    update_left_option: function (data) {
      for (var p in data) {
        var pdata = data[p]
        var xdata = []
        var linedata = []
        for (var tp in pdata) {
          xdata.push(tp)
          linedata.push(pdata[tp])
        }
        this.left_option.xAxis.data = xdata;
        this.left_option.series[1].data = linedata;
        var maxd = _.max(linedata);
        this.left_option.series[0].data = _.map(linedata, (obj, idx) => { return maxd })
        this.left_option.yAxis.max = maxd;
        break;
      }
    },
    update_center_option: function (data) {
      let cities = [];
      let lines = [];
      _.each(data, item => {
        cities.push({name: item.departure.CityName, value: item.departure.Coordinate});
        cities.push({name: item.destination.CityName, value: item.destination.Coordinate});
      });
      let citycounts = _.countBy(cities, 'name');
      // console.log(citycounts);
      _.each(cities, city => {
        city.value.push(citycounts[city.name]);
      });
      _.each(data, item => {
        lines.push([
          {coord: item.departure.Coordinate, value: citycounts[item.departure.CityName]},
          {coord: item.destination.Coordinate}
        ]);
      });
      this.center_option.visualMap.min = _.min(_.values(citycounts))
      this.center_option.visualMap.max = _.max(_.values(citycounts))
      this.center_option.series[0].data = lines;
      this.center_option.series[1].data = cities;
    },
    update_right_option: function (data) {
      var namedata = [{
        name: this.username,
        draggable: true,
      }]
      var linkdata = []
      var ii = 1;
      for (var p in data) {
        var psize = 30 + 10 * data[p].同行次数;
        psize = psize > 80 ? 80 : psize;
        namedata.push({name: data[p].姓名 + ',' + data[p].国籍 + ',' + p, category: 1, draggable: true, country: data[p].国籍, passID: p, symbolSize: psize})
        linkdata.push({source: 0, target: ii, value: data[p].同行次数})
        ii = ii + 1
      }
      this.right_option.series[0].data = namedata;
      this.right_option.series[0].links = linkdata;
    },
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
  .guest-info
    margin: 0 auto
    padding-top: 0rem
    display: flex
    align-items: center
    justify-content: center
    margin-bottom: 2rem
    position: relative
  .basic
    position: absolute
    width: calc(100% - 5rem)
    height: calc(100% - 5rem)
    z-index: 20
    // display: none
  .tags
    font-size: 1.6rem
    text-align: center
    color: #fff
    .fa
      width: 1.8rem
      text-align: center
      margin-right: 4px
      font-size: 1.6rem
      color: #69d3ff
    .category
      position: relative
      display: inline-block
      // border-radius: 5px
      color: #fff
      padding: 3px 7px
      background-color: #e63c3c
      margin-right: 2.2rem
      text-shadow: 1px 1px 1px #222
      // background-image: url("~assets/images/tag-bg.png")
      background-size: 100% 100%
      // border: 2px solid #fff
      &:after
        content: ""
        display: block
        position: absolute
        right: -.95rem
        top: .4rem
        background-color: #e63c3c
        width: 1.9rem
        height: 1.9rem
        z-index: -1
        transform: rotate(45deg)
      &:before
        position: absolute
        right: -.6rem
        top: 1.1rem
        content: ""
        display: block
        width: .5rem
        height: .5rem
        border-radius: 50%
        background-color: #050c19
  .info-score
    position: relative
    width: 16rem
    height: 16rem
    margin: 0 2.5rem
    .circle
      position: absolute
      width: 100%
      img
        width: 100%
    .circle-1
      text-align: center
      line-height: 16rem
      color: #fff
      font-size: 3rem
      font-weight: bold
      font-family: "MyMontserrat"
      span
        color: #9be2ff
      span.risk-height
        color: #ff5252
      span.risk-middle
        color: #d37d1c
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
    font-size: 1.6rem
    p
      margin-bottom: .8rem
      &:first-child
        padding-top: .6rem
      .fa
        width: 2.4rem
        text-align: center
        margin-right: 4px
        font-size: 1.6rem
        color: #69d3ff
  .guest-charts
    position: relative
    bottom: -1rem
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
  .device
    position: absolute
    z-index: 10
    width: calc(100% - 5rem)
    height: calc(100% - 5rem)
    display: flex
    justify-content: space-between
    align-items: center
    .con-box
      width: 30%
      .box-cont
        width: 100%
        height: 350px
        padding: .7rem 1rem .8rem
        background-image: url("~assets/images/page2-device-bg.png")
        background-size: 100% 100%
        .box-title
          font-size: 24px
          font-weight: bold
          color: #fff
          text-align: center
        .box-img
          width: 100%
          text-align: center
          height: calc(100% - 50px)
          img
            max-width: 100%
            max-height: 100%
      .box-status
        width: 80px
        height: 80px
        border-radius: 50%
        margin: 1.5rem auto 0
        color: #d0d7e0
        background-color: #d0d7e0
        position: relative
        z-index: 100
        &:after
          border: 15px solid
          position: absolute
          left: -10px
          top: -10px
          content: ""
          display: block
          width: 100px
          height: 100px
          border-radius: 50%
          animation: sk-scaleout 1.5s infinite linear
        &.normal
          color: #14d11f
          background-color: #14d11f
        &.warining
          color: #fde01e
          background-color: #fde01e
  // 定义闪烁动画
  @keyframes sk-scaleout
    0%
      transform: scale(0);
    100%
      transform: scale(1.25);
      opacity: 0.05;
  // 页面切换效果
  .fade-enter-active, .fade-leave-active
    transition: opacity .8s;
  .fade-enter, .fade-leave-to
    opacity: 0;
</style>
