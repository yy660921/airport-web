/**
 * All provided echarts options.
 */

import Graphic from 'echarts/lib/util/graphic'

const ChartLib = {
  '风险走势&折线图': {
    option: {
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
    update: (option) => {
      axios.get('/api/getRTAndSN', {params: {}}).then(response => {
        if (response.data.createDate.length > 0 && response.data.tourist_warningEvents.length > 0 && response.data.seizure_number.length > 0) {
          option.xAxis.data = response.data.createDate;
          option.series[0].data = response.data.tourist_warningEvents;
          option.series[1].data = response.data.seizure_number;
        }
      });
    },
  },
  '热门城市&横向柱状图': {
    option: {
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
    update: (option) => {
      axios.get('/api/getDeparture', {params: {}}).then(response => {
        if (response.data.Departures.length > 0 && response.data.DepartureCount.length > 0) {
          option.yAxis.data = response.data.Departures;
          option.series[1].data = response.data.DepartureCount;
          var maxd = _.max(response.data.DepartureCount);
          option.series[0].data = _.map(response.data.DepartureCount, (obj, idx) => { return maxd })
          option.xAxis.max = maxd;
        }
      });
    },
  },
  '风险旅客&横向柱状图': {
    option: {
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
    update: (option) => {
      axios.get('/api/getTAndRI', {params: {}}).then(response => {
        if (response.data.TouristName.length > 0 && response.data.TouristRiskIndex.length > 0) {
          option.yAxis.data = response.data.TouristName;
          option.series[1].data = response.data.TouristRiskIndex;
          var maxd = _.max(response.data.TouristRiskIndex);
          option.series[0].data = _.map(response.data.TouristRiskIndex, (obj, idx) => { return maxd })
          option.xAxis.max = maxd;
        }
      });
    },
  },
  '查获时段&柱状图': {
    option: {
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
    update: (option) => {
      axios.get('/api/getDCD', {params: {}}).then(response => {
        if (response.data.TimeLine.length > 0 && response.data.Count.length > 0) {
          option.xAxis.data = response.data.TimeLine;
          option.series[1].data = response.data.Count;
          var maxd = _.max(response.data.Count);
          option.series[0].data = _.map(response.data.Count, (obj, idx) => { return maxd })
          option.yAxis.max = maxd;
        }
      });
    },
  },
};

export { ChartLib };
