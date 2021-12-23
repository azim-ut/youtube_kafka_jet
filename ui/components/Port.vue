<template>
  <div class="radioBlock" :style="'position: absolute; left: calc(' + port.x + '% - 100px); top: calc(' + port.y + '% - 100px)'">
    <div class="radioPoint" @click="clickCallback(port)"></div>
    <div class="title">
      {{port.name}} <span v-for="(row, ind) in port.boards"
                          style="color: #fff;"
                          :title="row"
                          :key="ind">&#9992;</span>
    </div>
  </div>
</template>
<script>
export default {
  props:{
    port: null,
    clickCallback: null
  }
}
</script>
<style>
.radioBlock .radioPoint{
  width: 200px;
  height: 200px;
  background: transparent url("assets/radio.png") no-repeat center center/cover;
}

.radioBlock .title{
  background: #232323;
  color: chartreuse;
  padding: 5px 5px;
  text-align: center;
  font-size: 18px;
}
</style>
