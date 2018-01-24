<template>
  <div id="app" class="real-body">
    <div class="con-left">
      <div class="con-box" @click="goto">
        <Echarts theme="ring" :option="top_option" className="chart" ></Echarts>
      </div>
      <div class="con-box" @click="goto">
        <Echarts theme="ring" :option="bottom_option" className="chart" ></Echarts>
      </div>
    </div>
    <div class="con-right">
      <div class="con-box" @click="goto">
        <Echarts theme="ring" :option="word_cloud_option" className="chart" ></Echarts>
      </div>
      <div class="con-box" @click="goto">
        <h3>热点新闻</h3>
        <b-carousel
          id="carousel1"
          controls
          :interval="10000"
          v-model="slide"
          @sliding-start="onSlideStart"
          @sliding-end="onSlideEnd"
        >
          <b-carousel-slide v-for="(msg, idx) in newsdata">
            <div class="list active" :key="idx">
              <a href="#" class="link"><i class="news-icon fa fa-newspaper-o"></i><span>#{{ msg['keyword'] }}#</span>{{ msg['title'] }}</a>
              <p class="news-detail" >{{ msg['content'] }}</p>
            </div>
          </b-carousel-slide>
        </b-carousel>
        <p class="load-all"><a href="#" title="加载全部">加载全部</a></p>
      </div>
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import Vue from 'vue'
  import 'components/charts/theme/Ring.js'
  import Graphic from 'echarts/lib/util/graphic'
  import Echarts from 'vue-echarts-v3/src/full.js'
  import 'echarts-wordcloud'
  import bCarousel from 'bootstrap-vue/es/components/carousel/carousel'
  import bCarouselSlide from 'bootstrap-vue/es/components/carousel/carousel-slide'
  import Common from 'components/Common.js'

  export default {
    name: 'app',
    data () {
      return {
        intervalID: null,
        newsdata: ['a', 'b'], // b-carousel请求的数据不能是空数组，会导致typeIdex无法设置
        slide: 0,
        sliding: null,
        items: ['新年快乐', '新年快乐，上线顺利'],
        top_option: {
          title: {
            text: '舆情走势',
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
            top: 45,
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
            textStyle: {fontSize: 18},
            // formatter: function(param){
            //   var a=param.data.text.join('<br/>');
            //   var b=param.data.name+'&nbsp;&nbsp;代表性文本：<br/><br/>'+a;
            //   return b;
            // }
          },
          color: [
            '#24adf1',
            '#0e7fe2',
            '#4465f6',
            '#a06af5',
            '#f48021',
            '#f5d10c'
          ],
          legend: {
            orient: 'horizontal',
            align: 'left',
            bottom: '5',
            data: [],
            itemGap: 15,
            textStyle: {
              color: '#ccc',
              fontSize: '20',
            }
          },
          series: [{
            // name: '社会观点',
            type: 'pie',
            radius: '50%',
            center: ['50%', '45%'],
            data: [],
            label: {
              normal: {
                fontSize: 22,
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
        word_cloud_option: {
          title: {
            text: '关键词云',
          },
          color: [
            '#24adf1',
            '#0e7fe2',
            '#4465f6',
            '#a06af5',
            '#f48021',
            '#f5d10c'
          ],
          series: [
            {
              type: 'wordCloud',
              gridSize: 20,
              sizeRange: [28, 60],
              rotationRange: [0, 0],
              shape: 'circle',
              textStyle: {
                normal: {
                  color: function () {
                    return 'rgb(' + [
                      Math.round(100 + Math.random() * 80),
                      Math.round(180 + Math.random() * 80),
                      Math.round(170 + Math.random() * 80)
                    ].join(',') + ')';
                  }
                },
                emphasis: {
                  shadowBlur: 10,
                  shadowColor: '#fff'
                }
              },
              data: [
                {
                  name: 'Sam S Club',
                  value: 10000
                }, {
                  name: 'Macys',
                  value: 6181
                }, {
                  name: 'Amy Schumer',
                  value: 4386
                },
              ]
            }
          ]
        }
      };
    },
    created () {
      this.updateData()
      this.intervalID = setInterval(() => {
        this.updateData()
      }, 5 * 60 * 1000);
    },
    methods: {
      goto () {
        document.location.href = Common.addr + Common.page3;
      },
      onSlideStart (slide) {
        this.sliding = true
      },
      onSlideEnd (slide) {
        this.sliding = false
      },
      updateData () {
        this.update_top_option();
        this.update_bottom_option();
        this.update_right();
        this.update_word_cloud_option();
      },
      update_top_option () {
        axios.get('/api/getCount').then(response => {
          this.top_option.xAxis.data = response.data.Date;
          this.top_option.series[0].data = response.data.count;
        });
      },
      update_bottom_option () {
        axios.get('/api/getViewPoint').then(response => {
          let legendData = [];
          let dataValue = [];
          var total_num = _.sumBy(response.data, function (item) { return item.value })
          _.each(response.data, function (item, index) {
            var keywords = item.name + (item.value / total_num).toFixed(2).toString();
            legendData.push(keywords);
            dataValue.push({value: item.value, name: keywords, text: item.docs});
          });
          this.bottom_option.legend.data = legendData;
          this.bottom_option.series[0].data = dataValue;
        });
      },
      update_word_cloud_option () {
        axios.get('/api/getWordCloud').then(response => {
          console.log(response.data);
          this.word_cloud_option.series[0].data = response.data;
        });
      },
      update_right () {
        axios.get('/api/getNews').then(response => {
          this.activeNewsIndex = 0;
          this.newsdata = response.data;
          // this.activeID = response.data[this.activeNewsIndex]['ID'];
        });
      },
    },
    beforeDestroy () {
      clearInterval(this.intervalID)
      clearInterval(this.intervalAc)
    },
    components: {
      Echarts,
      'b-carousel': bCarousel,
      'b-carousel-slide': bCarouselSlide
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
      cursor: pointer
      &:first-child
        margin-bottom: 4%
  .con-right
    float: right
    width: 49%
    height: 100%
    // padding: 1rem 1rem .8rem
    // background-image: url("~assets/images/page3-right-bg.png")
    background-size: 100% 100%
    overflow-y: hidden
    font-size: 1rem
    .con-box
      height: 48%
      padding: .7rem 1rem .8rem
      background-image: url("~assets/images/page3-left-bg.png")
      background-size: 100% 100%
      cursor: pointer
      &:first-child
        margin-bottom: 4%
    h3
      color: #ffffff
      font-size: 26px
      font-weight: bold
      text-align: center
      margin-bottom: 0
    .carousel
      height: calc(100% - 88px)
    .carousel-inner
      height: 100%
      width: 100%
      margin: 0
    .list-wrapper
      height: 100%
    .carousel-item
      outline: none
      height: 100%
    .carousel-control-prev, .carousel-control-next
      width: 1.5rem
    .carousel-control-prev-icon, .carousel-control-next-icon
      width: 40px
      height: 40px
    .carousel-caption
      right: 0
      left: 0
      bottom: 0
      top: 0
      margin: 20px 0
      padding: 0
      overflow: hidden
      border: 1px solid #2052dd
      background: rgba(32, 82, 211, 0.5)
    .list
      position: relative
      // background: rgba(6, 22, 58, .5)
      // border: 1px solid #2052dd
      padding: .5rem 1rem
      margin-bottom: 1.1rem
      .news-detail
        margin-bottom: 0
        color: #eee
        text-align: left
        // display: none
      &.active
        // background: rgba(32, 82, 211, .5)
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
          margin-right: .3rem
          color: rgba(252, 143, 47, 1)
          font-weight: bold

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
    p.load-all
      text-align: center
      margin-bottom: 0
      a
        display: inline-block
        padding: .5rem 1rem
        color: #fff
        border: 1px solid #2052dd
        &:hover
          background-color: #2052dd
</style>
