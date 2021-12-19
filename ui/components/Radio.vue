<!-- Please remove this file from your project -->
<template>
  <div class="radioBlock" :style="'position: absolute; left: calc('+port.x+'% - 50px); top: calc('+port.y+'% - 50px)'">
    <div class="radioPoint" @click="clickCallBack(port)">&nbsp;</div>
    <div class="title">{{ port.name }}
      <span v-for="(row, ind) in port.boards"
            style="color: #fff;"
            :title="row"
            :key="ind">&#9992;</span>
    </div>
  </div>
</template>
<script>
export default {
  props: {
    port: null,
    clickCallBack: null
  }
}
</script>
<style>
.radioBlock .radioPoint {
  width: 100px;
  height: 100px;
  background: transparent url("assets/radio.png") no-repeat center center/cover;
}

.radioBlock .title {
  background: #232323;
  color: chartreuse;
  padding: 2px 5px;
  text-align: center;
}
</style>
