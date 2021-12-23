<template>
  <div class="planeBlock" :style="'z-index:'+level">
    <div class="icon" :style="'left: ' + plane.x + '%; top: ' + plane.y + '%; transform: rotate(' + (plane.angle - 180) + 'deg);'">&#9992;</div>
    <div class="title" :style="'left: ' + plane.x + '%; top: ' + plane.y + '%;'">{{plane.name}}</div>
  </div>
</template>
<script>
export default {
  props: {
    level: 0,
    plane: null
  }

}
</script>
<style>
.planeBlock {
  width: 100%;
  min-height: 100vh;
  position: absolute;
}

.planeBlock .icon {
  position: absolute;
  font-size: 300%;
  color: #ffffff;
  transform-origin: center center;
}
.planeBlock .title {
  position: absolute;
  background: #232323;
  color: chartreuse;
  padding: 2px 5px;
  text-align: center;
}
</style>
