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
          <span>{{ riskIndex }}</span>
        </div>
      </div>
      <div class="info-detail">
        <p>姓名：<span>{{ username }}</span></p>
        <p>性别：<span>{{ sex }}</span></p>
        <p>国籍：<span>{{ country }}</span></p>
        <p>出生日期：<span>{{ birth }}</span></p>
        <p>护照号：<span>{{ passportID }}</span></p>
        <p>最近一次航班出发地：<span>{{ departure }}</span></p>
        <p>最近一次航班目的地：<span>{{ destination }}</span></p>
        <p>入境时间：<span>{{ intime }}</span></p>
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
      username: '张三',
      sex: '男',
      country: '中国',
      birth: '1990-01-01',
      passportID: 'J94875326',
      departure: '巴黎',
      destination: '铜仁',
      intime: '2017-12-16',
      riskIndex: '99',
      left_option: {
        animation: true,
        title: {
          text: '入境时间分布',
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
        },
        tooltip: {
          trigger: 'axis',
        },
        xAxis: {
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
          data: ['2017-06','2017-07','2017-08','2017-09','2017-10','2017-11','2017-12']
        },
        yAxis: {
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
            data: [10,10,10,10,10,10,10]
          },
          {
            name: '设备查验次数',
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
            data: [7,6,5,4,3,2,1]
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
  created () {
    this.updateData();
  },
  methods: {
    updateData: function () {
      axios.get('/api/getTourist', {}).then(response => {
          if (response.data.length > 0) {
            var res = response.data;
            for (var i = 0; i < res.length; i++) {
              var person = res[i]
              this.username = person.warningTourist_name;
              this.sex = person.warningTourist_sex;
              this.country = person.warningTourist_country;
              this.birth = person.warningTourist_birthday;
              this.passportID = person.warningTourist_passport;
              this.departure = person.warningTourist_departure;
              this.destination = person.warningTourist_destination;
              this.intime = person.warningTourist_time;
              this.riskIndex = person.warningTourist_riskIndex;
              this.update_left_option(person.warningTourist_historyTime);
              break;
            }
          }
        });
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
        break;
      }
    },
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
