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
          data: ['2017-06', '2017-07', '2017-08', '2017-09', '2017-10', '2017-11', '2017-12']
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
            data: [10, 10, 10, 10, 10, 10, 10]
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
            data: [7, 6, 5, 4, 3, 2, 1]
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
              color: 'rgba(51, 69, 89, .5)',
              borderColor: 'rgba(100,149,237,1)'
            },
            emphasis: {
              color: 'rgba(37, 43, 61, .5)'
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
          left: 'center',
          textStyle: {
            color: '#b5eaff'
          }
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
        series: [
          {
            type: 'graph',
            layout: 'force',
            symbolSize: 45,
            focusNodeAdjacency: true,
            roam: true,
            categories: [{
              name: '朋友',
              itemStyle: {
                normal: {
                  color: "#009800",
                }
              }
            }, {
              name: '战友',
              itemStyle: {
                normal: {
                  color: "#4592FF",
                }
              }
            }, {
              name: '亲戚',
              itemStyle: {
                normal: {
                  color: "#3592F",
                }
              }
            }],
            label: {
              normal: {
                show: true,
                textStyle: {
                  fontSize: 12
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
            data: [{
              name: '徐贱云',
              draggable: true,
            }, {
              name: '冯可梁',
              category: 1,
              draggable: true,
            }, {
              name: '邓志荣',
              category: 1,
              draggable: true,
            }, {
              name: '李荣庆',
              category: 1,
              draggable: true,
            }, {
              name: '郑志勇',
              category: 1,
              draggable: true,
            }, {
              name: '赵英杰',
              category: 1,
              draggable: true,
            }, {
              name: '王承军',
              category: 1,
              draggable: true,
            }, {
              name: '陈卫东',
              category: 1,
              draggable: true,
            }, {
              name: '邹劲松',
              category: 1,
              draggable: true,
            }, {
              name: '赵成',
              category: 1,
              draggable: true,
            }, {
              name: '陈现忠',
              category: 1,
              draggable: true,
            }, {
              name: '陶泳',
              category: 1,
              draggable: true,
            }, {
              name: '王德福',
              category: 1,
              draggable: true,
            }],
            links: [{
              source: 0,
              target: 1,
              category: 0,
              value: '朋友'
            }, {
              source: 0,
              target: 2,
              value: '战友'
            }, {
              source: 0,
              target: 3,
              value: '房东'
            }, {
              source: 0,
              target: 4,
              value: '朋友'
            }, {
              source: 1,
              target: 2,
              value: '表亲'
            }, {
              source: 0,
              target: 5,
              value: '朋友'
            }, {
              source: 4,
              target: 5,
              value: '姑姑'
            }, {
              source: 2,
              target: 8,
              value: '叔叔'
            }, {
              source: 0,
              target: 12,
              value: '朋友'
            }, {
              source: 6,
              target: 11,
              value: '爱人'
            }, {
              source: 6,
              target: 3,
              value: '朋友'
            }, {
              source: 7,
              target: 5,
              value: '朋友'
            }, {
              source: 9,
              target: 10,
              value: '朋友'
            }, {
              source: 3,
              target: 10,
              value: '朋友'
            }, {
              source: 2,
              target: 11,
              value: '同学'
            }],
            lineStyle: {
              normal: {
                opacity: 0.9,
                width: 2,
                curveness: 0
              }
            }
          }
        ]
      }
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
      if (this.number_all <= 0 || this.number_now >= this.number_all) {
        axios.get('/api/getTourist', {}).then(response => {
          if (response.data.length > 0) {
            this.resData = response.data;
            this.number_all = this.resData.length;
            this.number_now = 0;
            this.updatePerson();
          }
        })
      } else {
        this.updatePerson()
      }
    },
    updatePerson: function () {
      var person = this.resData[this.number_now]
      this.number_now = this.number_now + 1
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
      this.update_center_option(person.warningTourist_place);
      this.update_right_option(person.fellowTourist_list);
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
    update_center_option: function (data) {
      let cities = [];
      let lines = [];
      _.each(data, item => {
        cities.push({name: item.departure.CityName, value: item.departure.Coordinate});
        cities.push({name: item.destination.CityName, value: item.destination.Coordinate});
      });
      let citycounts = _.countBy(cities, 'name');
      console.log(citycounts);
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
        namedata.push({name: data[p].姓名 + ',' + data[p].国籍 + ',' + p, category: 1, draggable: true, country: data[p].国籍, passID: p})
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
