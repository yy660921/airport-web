/* Custom echarts theme */

import * as echarts from 'echarts/lib/echarts.js'
import Colors from 'components/Colors.js'

(function (echarts) {
  var log = function (msg) {
    if (typeof console !== 'undefined') {
      console && console.error && console.error(msg);
    }
  };
  if (!echarts) {
    log('ECharts is not Loaded');
    return;
  }

  var theme = {
    color: Colors.CommonColor,
    legend: {
      textStyle: {
        color: '#666',
      },
    },
    title: {
      left: 'center',
      textStyle: {
        color: '#fff',
        fontSize: '26'
      }
    },
    tooltip: {
      trigger: 'item',
      borderWidth: 1,
      padding: 5,
      borderColor: '#0775e4',
      backgroundColor: 'rgba(29, 81, 203, .6)',
    },
    // 横向轴
    categoryAxis: {
      axisLabel: {
        textStyle: {
          color: '#fff',
        },
        fontSize: 14,
      },
      axisLine: {
        lineStyle: {
          with: 2,
          color: '#0775e4',
        },
        show: true,
      },
      splitLine: {
        lineStyle: {
          color: '#1a41ac',
          type: 'dashed',
        },
        show: true,
      },
      nameTextStyle: {
        color: '#fff',
      },
    },
    // 纵向轴
    valueAxis: {
      axisLabel: {
        textStyle: {
          color: '#fff',
        },
      },
      axisLine: {
        lineStyle: {
          width: 2,
          color: '#0775e4',
        },
        show: true,
      },
      splitLine: {
        lineStyle: {
          color: ['#1a41ac'],
          type: 'dashed'
        },
      },
      splitArea: {
        show: false,
      },
      nameTextStyle: {
        color: '#fff',
      },
    },
    logAxis: {
      axisLabel: {
        textStyle: {
          color: '#666',
        },
      },
      axisLine: {
        lineStyle: {
          color: '#ababab',
        },
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: ['#cecece'],
          type: 'dashed'
        },
      },
      splitArea: {
        show: false,
      },
      nameTextStyle: {
        color: '#666',
      },
    },
    timeAxis: {
      axisLabel: {
        textStyle: {
          color: '#666',
        },
      },
      axisLine: {
        lineStyle: {
          color: '#ababab',
        },
        show: false,
      },
      splitLine: {
        lineStyle: {
          color: ['#cecece'],
          type: 'dashed'
        },
      },
      splitArea: {
        show: false,
      },
      nameTextStyle: {
        color: '#666',
      },
    },

    visualMap: {
      // updated by @sighingnow, 2017-06-12: default color range seems better.
      // color: ['#0072be', '#2bd0ff'],
      textStyle: {
        color: '#666',
      },
      calculable: false,
    },
    radar: {
      name: {
        textStyle: {
          color: '#666',
        },
      },
      splitArea: {
        show: true,
        areaStyle: {
          color: ['rgba(120, 120, 120, .1)','rgba(120, 120, 120, 0)']
        }
      },
      axisLine: {
        lineStyle: {
          color: '#bbb'
        }
      },
      splitLine: {
        lineStyle: {
          color: [
            'rgba(120, 120, 120, .4)',
            'rgba(120, 120, 120, .35)',
            'rgba(120, 120, 120, .3)',
            'rgba(120, 120, 120, .25)',
            'rgba(120, 120, 120, .2)',
            'rgba(120, 120, 120, .1)',
          ]
        }
      }
    },

    pie: {
      center: ['50%', '50%'],
      itemStyle: {
        emphasis: {
          shadowBlur: 10,
          shadowOffsetX: 0,
          shadowColor: 'rgba(0, 0, 0, 0.5)',
        },
      },
    },

    line: {
      smooth: true,
    },

    map: {
      roam: false,
      label: {
        normal: {show: false},
        emphasis: {show: false},
      },
      itemStyle: {
        normal: {
          borderColor: 'rgba(29, 61, 122, .5)',
        },
        emphasis: {
          areaColor: '#f7ca18',
        },
      },
    },

    wordCloud: {
      left: 'center',
      top: 'center',
      // rotationStep: 45,
      textRotation: [0, 45, -45],
      textPadding: 2,
      // rotationRange: [-90, 90],
      textStyle: {
        normal: {
          fontFamily: '"Microsoft Yahei","Helvetica Neue",Helvetica,Arial,sans-serif',
          fontWeight: 'normal',
          // Color can be a callback function or a color string
          color: function () {
            return 'rgb(' + [
              Math.round(Math.random() * 200),
              Math.round(Math.random() * 200),
              Math.round(Math.random() * 200)
            ].join(',') + ')';
          },
        },
        emphasis: {
          shadowBlur: 10,
          shadowColor: '#333'
        },
      },
      autoSize: {
        enable: true,
        minSize: 15
      },
    },
  };

  echarts.registerTheme('ring', theme);
})(echarts);
