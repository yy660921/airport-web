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
  '旅客风险构成-来源城市&横向柱状图': {
    option: {
      animation: true,
      title: {
        text: '旅客风险构成-来源城市'
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
  '查获风险构成-时段分布&柱状图': {
    option: {
      animation: true,
      title: {
        text: '查获风险构成-时段分布',
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
  '饼状图&test': {
    option: {
      title: {
        text: '饼状图标题'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
    },
  },
  '年度进出境人员构成&饼柱图': {
    option: {
      title: {
        text: '年度进出境人员构成'
      },
      tooltip: {
        trigger: 'axis',
      },
      xAxis: {
        type: 'category',
        splitLine: {
          show: false,
        },
        data: ['1', '2', '3']
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
        // 柱状背景
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
        },
        // 饼状图
        {
          name: '饼状图name',
          type: 'pie',
          radius: '30%',
          center: ['80%', '35%'],
          tooltip: {trigger: 'axis',},
          data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
          label: {
            normal: {
              fontSize: 16,
            },
          },
          itemStyle: {
            emphasis: {
              shadowBlur: 10,
              shadowOffsetX: 0,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          }
        }
      ]
    },
    update: (option) => {},
  },
  '旅客风险构成-国家&横向柱状图': {
    option: {
      animation: true,
      title: {
        text: '旅客风险构成-国家',
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
        data: ['巴西','印尼','美国','印度','德国','法国']
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
          data: [630230, 630230, 630230, 630230, 630230, 630230]
        },
        {
          name: '人次',
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
          data: [18203, 23489, 29034, 104970, 131744, 630230]
        }
      ]
    },
    update: (option) => {
      axios.get('/api/getTouristCountry', {params: {}}).then(response => {
        if (response.data.Country.length > 0 && response.data.Count.length > 0) {
          option.yAxis.data = response.data.Country;
          option.series[1].data = response.data.Count;
          var maxd = _.max(response.data.Count);
          option.series[0].data = _.map(response.data.Count, (obj, idx) => { return maxd })
          option.xAxis.max = maxd;
        }
      });
    },
  },
  '旅客风险构成-性别比例&饼图': {
    option: {
      title: {
        text: '旅客风险构成-性别比例'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getSexRatio', {params: {}}).then(response => {
        if (response.data.Sex.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Sex;
          let res = []
          _.each(response.data.Sex, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '旅客风险构成-风险标签接口&饼图': {
    option: {
      title: {
        text: '旅客风险构成-风险标签接口'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getRiskLabel', {params: {}}).then(response => {
        if (response.data.Label.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Label;
          let res = []
          _.each(response.data.Label, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '外部情报风险来源&饼图': {
    option: {
      title: {
        text: '外部情报风险来源'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getOtherMessage', {params: {}}).then(response => {
        if (response.data.Department.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Department;
          let res = []
          _.each(response.data.Department, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '内部情报风险来源&饼图': {
    option: {
      title: {
        text: '内部情报风险来源'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getOwnMessage', {params: {}}).then(response => {
        if (response.data.Department.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Department;
          let res = []
          _.each(response.data.Department, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '航线风险要素构成&饼图': {
    option: {
      title: {
        text: '航线风险要素构成'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getFlightWarning', {params: {}}).then(response => {
        if (response.data.Content.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Content;
          let res = []
          _.each(response.data.Content, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '查获重点热门物品构成&横向柱状图': {
    option: {
      animation: true,
      title: {
        text: '查获重点热门物品构成',
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
        data: ['巴西','印尼','美国','印度','德国','法国']
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
          data: [630230, 630230, 630230, 630230, 630230, 630230]
        },
        {
          name: '人次',
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
          data: [18203, 23489, 29034, 104970, 131744, 630230]
        }
      ]
    },
    update: (option) => {
      axios.get('/api/getHotGoods', {params: {}}).then(response => {
        if (response.data.Goods.length > 0 && response.data.Count.length > 0) {
          option.yAxis.data = response.data.Goods;
          option.series[1].data = response.data.Count;
          var maxd = _.max(response.data.Count);
          option.series[0].data = _.map(response.data.Count, (obj, idx) => { return maxd })
          option.xAxis.max = maxd;
        }
      });
    },
  },
  '查获禁限物品构成&横向柱状图': {
    option: {
      animation: true,
      title: {
        text: '查获禁限物品构成',
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
        data: ['巴西','印尼','美国','印度','德国','法国']
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
          data: [630230, 630230, 630230, 630230, 630230, 630230]
        },
        {
          name: '人次',
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
          data: [18203, 23489, 29034, 104970, 131744, 630230]
        }
      ]
    },
    update: (option) => {
      axios.get('/api/getContrabandGoods', {params: {}}).then(response => {
        if (response.data.Goods.length > 0 && response.data.Count.length > 0) {
          option.yAxis.data = response.data.Goods;
          option.series[1].data = response.data.Count;
          var maxd = _.max(response.data.Count);
          option.series[0].data = _.map(response.data.Count, (obj, idx) => { return maxd })
          option.xAxis.max = maxd;
        }
      });
    },
  },
  '年度监管航空器构成&饼图': {
    option: {
      title: {
        text: '年度监管航空器构成'
      },
      tooltip: {
        trigger: 'item',
        enterable: true,
        confine: true,
        textStyle: {fontSize: 18},
      },
      color: [
        '#24adf1',
        '#f5d10c',
        '#a06af5',
        '#f48021',
        '#0e7fe2',
        '#4465f6',
      ],
      legend: {
        orient: 'horizontal',
        align: 'left',
        bottom: '5',
        data: ['类别1','类别2','类别3','类别4'],
        itemGap: 15,
        textStyle: {
          color: '#ccc',
          fontSize: '20',
        }
      },
      series: [{
        type: 'pie',
        radius: '50%',
        center: ['50%', '45%'],
        data: [{value: 14, name: '类别1'},{value: 31, name: '类别2'},{value: 71, name: '类别3'},{value: 4, name: '类别4'}],
        label: {
          normal: {
            fontSize: 20,
          },
        },
        itemStyle: {
          emphasis: {
            shadowBlur: 10,
            shadowOffsetX: 0,
            shadowColor: 'rgba(0, 0, 0, 0.5)'
          }
        }
      }]
    },
    update: (option) => {
      axios.get('/api/getYearsAircraft', {params: {}}).then(response => {
        if (response.data.Aircraft.length > 0 && response.data.Count.length > 0) {
          option.legend.data = response.data.Aircraft;
          let res = []
          _.each(response.data.Aircraft, function (value, index) {
            res.push({value: response.data.Count[index], name: value})
          })
          option.series[0].data = res
        }
      });
    },
  },
  '年度进出境人员构成&双柱图': {
    option: {
      animation: true,
      title: {
        text: '年度进出境人员构成',
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
        boundaryGap: [0, 0.01]
      },
      grid: {
        top: 50,
        left: 40,
        right: 20,
        bottom: 25
      },
      series: [
        {
          name: '机组成员',
          type: 'bar',
          // barWidth: 13,
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
        },
        {
          name: '旅客',
          type: 'bar',
          // barWidth: 13,
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
                color: 'rgba(255, 162, 82, 1)'
              }, {
                offset: 1,
                color: 'rgba(241, 90, 34, 1)'
              }]),
              barBorderRadius: [10, 10, 0, 0],  // 圆角
            }
          },
          data: [0,0,67,0,30,0,140,20,112,127,113,20,5,101,90,77,116,28,68,80,30,61,88,93]
        }
      ]
    },
    update: (option) => {
      axios.get('/api/getYearsPeople', {params: {}}).then(response => {
        if (response.data.Month.length > 0 && response.data.Tourist.length > 0 && response.data.Stuff.length > 0) {
          option.xAxis.data = response.data.Month;
          option.series[0].data = response.data.Stuff;
          option.series[1].data = response.data.Tourist;
        }
      });
    },
  },
};

export { ChartLib };
