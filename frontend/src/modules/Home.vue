<template>
  <div class="page-wrapper">
    <div class="welcome-header">
      <img src="../assets/images/welcome-header-txt.png" height="90" width="749" alt="">
    </div>
    <div class="welcome-content" >
      <div class="welcome-side">
        <div class="platform" @click="go404">
          <div class="txt">智能服务平台</div>
        </div>
        <div class="platform" @click="go404">
          <div class="txt">智能作业平台</div>
        </div>
      </div>
      <div class="welcome-center" @click="toCentre">
        <div class="child bg1"><img src="../assets/images/welcome-center01.png" alt=""></div>
        <div class="child bg2"><img src="../assets/images/welcome-center02.png" alt=""></div>
        <div class="child bg3"><img src="../assets/images/welcome-center03.png" alt=""></div>
        <div class="child bg4"><img src="../assets/images/welcome-center04.png" alt=""></div>
        <div class="child txt">监控指挥中心</div>
      </div>
      <div class="welcome-side">
        <div class="platform" @click="goto">
          <a class="txt">智能分析平台</a>
        </div>
        <div class="platform" @click="go404">
          <div class="txt">智能管理平台</div>
        </div>
      </div>
    </div>
    <div class="welcome-footer">
      <img src="../assets/images/welcome-footer-txt.png" height="58" width="537" alt="">
    </div>
  </div>
</template>

<script type="text/ecmascript-6">
  import Common from 'components/Common.js'
  export default {
    data () {
      return {
      };
    },
    created () {
    },
    methods: {
      toCentre: function () {
        document.location.href = 'page0'
      },
      goto: function () {
        document.location.href = Common.addr + Common.page0;
      },
      go404: function () {
        document.location.href = 'notfound';
      },
    }
  }
</script>

<style lang="sass" scoped>
  @import "../assets/sass/mixin"

  .page-wrapper
    height: 100%
    position: relative
    background: url('../assets/images/welcome-bg.png') no-repeat
    background-size: 100% 100%
  .welcome-header
    text-align: center
    padding-top: 25px
  .welcome-content
    display: flex
    height: calc(100% - 230px)
    padding: 50px 110px
  .welcome-side
    width: 30%
    cursor: pointer
    .platform
      position: relative
      font-weight: bold
      width: 100%
      height: 45%
      background: url('../assets/images/welcome-module-bg.png') no-repeat
      background-size: 100% 100%
      margin-bottom: 10%
      .txt
        font-size: 40px
        color: #c6e6ff
        margin-bottom: 0
        position: absolute
        top: 0
        right: 0
        bottom: 0
        left: 0
        margin: auto
        width: 100%
        height: 90px
        text-align: center
  .welcome-center
    width: 40%
    padding-top: 40%
    height: 0
    position: relative
    cursor: pointer
    .child
      max-width: 90%
      max-height: 90%
      position: absolute
      top: 0
      right: 0
      bottom: 0
      left: 0
      margin: auto
      img
        width: 100%
    .bg2
      animation: bg_2 10s infinite linear
    .bg4
      animation: bg_4 7s infinite linear
    .txt
      font-size: 54px
      font-weight: bold
      color: #fff
      text-align: center
      width: 250px
      height: 134px
      // line-height: 636px
  .welcome-footer
    position: absolute
    bottom: 30px
    left: 50%
    transform: translateX(-50%)
  @keyframes bg_2
    0%
      transform: rotate(360deg)
    100%
      transform: rotate(0deg)
  @keyframes bg_4
    0%
      transform: rotate(0deg)
    100%
      transform: rotate(360deg)
</style>
