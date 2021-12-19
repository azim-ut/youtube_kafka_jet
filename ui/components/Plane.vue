<!-- Please remove this file from your project -->
<template>
  <div class="planeBlock">
    <div class="icon" :style="'left: ' + plane.x + '%; top: ' + plane.y + '%; transform: rotate(' + (plane.angle + 180) + 'deg);'">&#9992;</div>
    <div class="title" :style="'left: ' + plane.x + '%; top: ' + plane.y + '%;'">{{ plane.name }}</div>
  </div>
</template>
<script>
export default {
  props: {
    width: 0,
    height: 0,
    plane: null
  },
  data() {
    return {
      t: 0,
      area: [],
      toX: 0,
      toY: 0
    }
  },
  mounted() {
    this.toX = this.$props.plane.x
    this.toY = this.$props.plane.x
    setInterval(this.movePlain, 100)
  },
  methods: {
    calcArea() {
      return [
        [0, 0],
        [this.$props.width, 0],
        [this.$props.width, this.$props.height],
        [0, this.$props.height]
      ]
    },
    movePlain() {

    }
  }
}
</script>
<style>
.planeBlock {
  position: absolute;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;
  pointer-events: none;
  min-height: 100vh;
  width: 100%;
}
.planeBlock .icon{
  width: 50px;
  height: 50px;
  position: absolute;
  font-size: 300%;
  padding: 10px 5px 2px 5px;
  color: white;
  transform-origin: center center;
}
.planeBlock .title{
  position: absolute;
  padding: 2px 5px;
  background: #232323;
  font-size: 70% !important;
  color: white;
}
</style>
