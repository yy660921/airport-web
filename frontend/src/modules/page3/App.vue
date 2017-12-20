<template>
  <div id="app" class="real-body">
    <div class="con-left">
      <div class="con-box">
        <Echarts theme="ring" :option="top_option" className="chart" ></Echarts>
      </div>
      <div class="con-box">
        <Echarts theme="ring" :option="bottom_option" className="chart" ></Echarts>
      </div>
    </div>
    <div class="con-right">
      <h3>热点新闻</h3>
      <ul>
        <li class="list active">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘南开区环保招聘南开区环保招聘南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
          <p class="news-detail">河北区 86295673 铁东路街社区卫生服务中心 河北区南口路186号 每周一、二、四、五上午 86662997 望海楼街社区卫生服务中心 河北区昆纬路72号 每周一至周五上午 26219502 新开河街社区卫生服务中心 河北区古北道10号 86651424 月牙河街社区卫生服务中心 河北区靖江路雅砻江道2号 26025637 江都路街社区卫生服务中心 河北区金山道2号</p>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
        <li class="list">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[社会焦点]</span>天津欣国环环保科技有限公司最新天津市/南开区环保招聘</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
        </li>
      </ul>
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
        top_option: {
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
            }
          ]
        },
        bottom_option: {
          tooltip: {
            trigger: 'item',
            enterable: true,
            confine: true,
            textStyle: {fontSize: 10},
            //  formatter: function(param){
            //    var a=param.data.text.join('<br/>');
            //    var b=param.data.name+'&nbsp;&nbsp;代表性文本：<br/><br/>'+a;
            //    return b;
            //  }
          },
          legend: {
            orient: 'horizontal',
            right: 'center',
            //  left: 'left',
            align: 'left',
            //  top: 'top',
            data: [],
            //  x : 'center',
            //  y : 'bottom',
            y: '5%',
            x: '85%'
          },
          //  grid: {
          //    left: '30',
          //    right: '30',
          //    bottom: '100',
          //    top:'0%',
          //    containLabel: true
          //  },
          series: [{
            // name: '社会观点',
            type: 'pie',
            radius: '55%',
            center: ['50%', '60%'],
            data: [],
            itemStyle: {
              emphasis: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            }
          }]
        },
      };
    },
    created () {
      this.updateData();
      setInterval(this.updateData, 60 * 1000);
    },
    methods: {
      updateData () {
        this.update_top_option();
        this.update_bottom_option();
      },
      update_top_option () {
        axios.get('/api/getCount').then(response => {
          this.top_option.xAxis.data = response.data.Date;
          this.top_option.series[0].data = response.data.count;
        });
      },
      update_bottom_option () {
        axios.get('/static/data/haiguan_clustering_results.json').then(response => {
          let legendData = [];
          let dataValue = [];
          _.each(response.data, function (item, index) {
            var keywords, emotion, docs_ratio;
            if (item.key_words) {
              keywords = item.key_words;
            } else {
              keywords = '暂无关键词';
            }
            //  emotion='（积极：'+item.emotions[0]+',中性：'+item.emotions[1]+',消极：'+item.emotions[2]+')';
            docs_ratio = item.docs_ratio;
            keywords += ' ' + docs_ratio
            //  emotion = '（积极 '+item.emotions[0]+', 消极 '+item.emotions[2]+')';
            //  keywords += emotion;
            legendData.push(keywords);
            dataValue.push({value: item.docs_count, name: keywords, text: item.docs})
          });
          this.bottom_option.legend.data = legendData;
          this.bottom_option.series[0].data = dataValue;
        });
      },
    },
    components: {
      Echarts
    },
  }
</script>

<style lang="sass">
  @import "~assets/sass/common"

  .con-left
    float: left
    width: 48%
    height: 100%
    .con-box
      height: 48%
      padding: .7rem 1rem .8rem
      background-image: url("~assets/images/page3-left-bg.png")
      background-size: 100% 100%
      &:first-child
        margin-bottom: 4%
  .con-right
    float: right
    width: 49%
    height: 100%
    padding: 1rem 1rem .8rem
    background-image: url("~assets/images/page3-right-bg.png")
    background-size: 100% 100%
    overflow-y: hidden
    h3
      color: #b5eaff
      font-size: 18px
      font-weight: bold
      text-align: center
      margin-bottom: 0
    ul
      margin-top: 1rem
      padding: 0rem 1rem
      height: calc(100% - 50px)
      overflow-y: auto
      li.list
        position: relative
        background: rgba(6, 22, 58, .5)
        border: 1px solid #2052dd
        padding: .5rem 1rem
        margin-bottom: 1.1rem
        .news-detail
          margin-bottom: 0
          color: #eee
          display: none
        &.active
          background: rgba(32, 82, 211, .5)
          .link
            color: #fff
            padding-bottom: .4rem
            margin-bottom: .4rem
            border-bottom: 1px dashed #ccc
          .news-arrow
            &:before
              content: "\F102"
          .news-detail
            display: block
        .link
          display: block
          color: #b5eaff
          padding-right: 1rem
          white-space: nowrap
          text-overflow: ellipsis
          overflow: hidden
          .news-icon
            vertical-align: middle
            margin-right: .4rem
          span
            margin-right: .2rem
        .news-arrow
          display: inline-block
          position: absolute
          width: 1.5rem
          height: 1.5rem
          line-height: 1.5rem
          text-align: center
          right: 1rem
          font-size: 24px
          color: #b5eaff
          top: .35rem
          display: block
          cursor: pointer
        


</style>
