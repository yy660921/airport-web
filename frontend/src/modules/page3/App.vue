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
        <li class="list" v-for="(msg, idx) in newsdata" :key="idx" :class="{active: activeID === msg['ID']}">
          <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>[{{ msg['keyword'] }}]</span>{{ msg['title'] }}</a>
          <i class="news-arrow fa fa-angle-double-down"></i>
          <p class="news-detail" v-show="activeID === msg['ID']">{{ msg['content'] }}</p>
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
        intervalID: null,
        intervalAc: null,
        activeID: 0,
        activeNewsIndex: 0,
        newsdata: null,
        top_option: {
          title: {
            text: '舆情热度走势',
          },
          tooltip: {
            trigger: 'axis',
          },
          xAxis: {
            boundaryGap: false,
            data: ['12-01', '12-02', '12-03', '12-04', '12-05', '12-06', '12-07', '12-08'],
          },
          yAxis: {},
          grid: {
            top: 38,
            left: 30,
            right: 10,
            bottom: 25
          },
          series: [
            {
              name: '选项B',
              type: 'line',
              itemStyle: {
                normal: {
                  color: 'rgba(252, 143, 47, .9)'
                }
              },
              areaStyle: {
                normal: {
                  color: new Graphic.LinearGradient(0, 0, 0, 1, [{
                    offset: 0,
                    color: 'rgba(252, 143, 47, .9)'
                  }, {
                    offset: 1,
                    color: 'rgba(252, 143, 47, .4)'
                  }])
                }
              },
              data: [20, 10, 20, 30, 35, 26, 36, 30]
            }
          ]
        },
        bottom_option: {
          title: {
            text: '观点分析',
          },
          tooltip: {
            trigger: 'item',
            enterable: true,
            confine: true,
            textStyle: {fontSize: 14},
            // formatter: function(param){
            //   var a=param.data.text.join('<br/>');
            //   var b=param.data.name+'&nbsp;&nbsp;代表性文本：<br/><br/>'+a;
            //   return b;
            // }
          },
          legend: {
            orient: 'horizontal',
            align: 'left',
            bottom: '20',
            data: [],
            itemGap: 15,
            textStyle: {
              color: '#ccc',
              fontSize: '13',
            }
          },
          series: [{
            // name: '社会观点',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [],
            label: {
              normal: {
                fontSize: 14,
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
      };
    },
    created () {
      this.updateData()
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 5 * 60 * 1000);
      this.intervalAc = setInterval(() => {
          this.changeActive()
        }, 5 * 1000);
    },
    methods: {
      updateData () {
        this.update_top_option();
        this.update_bottom_option();
        this.update_right();
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
      update_right () {
        axios.get('/api/getNews').then(response => {
          this.activeNewsIndex = 0;
          this.newsdata = response.data;
          this.activeID = response.data[this.activeNewsIndex]['ID'];
        });
      },
      changeActive () {
        this.activeNewsIndex = (this.activeNewsIndex + 1) % this.newsdata.length;
        this.activeID = this.newsdata[this.activeNewsIndex]['ID'];
      },
    },
    beforeDestroy () {
      clearInterval(this.intervalID)
      clearInterval(this.intervalAc)
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
          max-height: 23rem
          overflow: hidden
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
