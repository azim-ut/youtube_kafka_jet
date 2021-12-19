<template>
  <div style="width: 100%; min-height:100%; display: inline-flex">
    <div class="tickets">
      <div>
        <div v-for="(message, ind) in messages" :key="ind">
          <div class="msg" v-if="message.source === 'AIRPORT'">A</div>
          <div class="msg" v-if="message.source === 'OFFICE' && message.type === 'ROUTE'">&#9758;</div>
          <div class="msg" v-if="message.source === 'OFFICE' && message.type === 'STATE'">?</div>
          <div class="msg" v-if="message.source === 'BOARD'">&#9992;</div>
        </div>
      </div>
      <br/>
    </div>
    <div class="radar">
      <Radio v-for="port in ports"
             :key="port.name"
             :port="port"
             :clickCallBack="addToRoute"
      />
      <div style="padding: 10px; text-align: center; color: #fff;">
        <span v-for="(route, index) in tempRoute" :key="index">
          {{ route }} <b v-if="index !== tempRoute.length - 1">&rtri;</b>
        </span>
        <button @click="submitRoute" v-if="tempRoute.length > 1" class="roundBtn">&check;</button>
        <button @click="cancelRoute" class="roundBtn" v-if="tempRoute.length > 0">&cross;</button>
      </div>
      <div class="planeArea">
        <Plane
          v-for="(row, ind) in boards" :key="ind"
          :plane="row"
          :width="50"
          :height="50"></Plane>
      </div>
    </div>
  </div>
</template>

<script>

import axios from "axios";

export default {
  data() {
    return {
      trafficSocket: null,
      ports: [],
      tickets: [],
      airPortOnline: false,
      trafficOnline: false,
      tempRoute: [],
      routes: [],
      boards: [],
      messages: []
    }
  },
  mounted() {
    this.defineSocket()
    setInterval(this.wakeSocketsUp, 5000)
  },
  methods: {
    addToRoute(port) {
      let last = null
      if (this.tempRoute.length > 0) {
        last = this.tempRoute[this.tempRoute.length - 1]
      }
      if (last != null && last === port.name) {
        return
      }
      this.tempRoute.push(port.name)
    },
    submitRoute() {
      axios.post("/api/routes/route", this.tempRoute);
      this.tempRoute = []
    },
    cancelRoute() {
      this.tempRoute = []
    },
    defineSocket() {
      this.trafficSocket = new WebSocket("ws://localhost:8083/websocket")

      this.trafficSocket.onopen = () => {
        this.trafficOnline = true
        this.trafficSocket.onmessage = (msg, ctx) => {
          let message = JSON.parse(msg.data)
          if (message.source === "AIRPORT") {
            this.setAirPort(message.airPort)
          }
          if (message.source === "BOARD" && message.type === "STATE") {
            this.setBoard(message.board)
          }
          this.messages.unshift(message);
          if (this.messages.length > 10) {
            this.messages.splice(10);
          }
        }
        this.wakeSocketsUp()
      }
    },
    setAirPort(port) {
      let ind = -1
      this.ports.forEach((row, i) => {
        if (row.name === port.name) {
          ind = i;
        }
      })
      if (ind >= 0) {
        this.ports.splice(ind, 1)
      }
      this.ports.push(port)
    },
    wakeSocketsUp() {
      if (this.trafficSocket) {
        if (this.ports.length === 0) {
          this.trafficSocket.send("update")
        }
      } else {
        this.defineSocket()
      }
    },
    prepareTicket() {
      this.tickets.push({
        name: "Draft",
        route: []
      })
    },
    setBoard(board) {
      let existsIndex = -1
      this.boards.forEach((row, i) => {
        if (row.name === board.name) {
          existsIndex = i
        }
      })

      if (existsIndex >= 0) {
        this.boards.splice(existsIndex, 1)
      }

      if (!board.busy) {
        return
      }
      this.boards.push(board)
    }
  }
}
</script>
<style>
* {
  padding: 0;
  margin: 0;
}

.tickets {
  color: aquamarine;
  padding: 10px;
  width: 10%;
  background: #232323;
}

.radar {
  background: transparent url("assets/grass.png");
  position: relative;
  width: 90%;
  height: 100%;
  min-height: 100vh;
}

.planeArea {
  position: absolute;
  right: 0;
  left: 0;
  top: 0;
  bottom: 0;
  pointer-events: none;
}

.drkRoundBtn,
.roundBtn {
  padding: 5px 10px;
  border-radius: 5px;
  border: white 2px solid;
  background: transparent;
  color: #fff;
  cursor: pointer;
  opacity: .7;
}

.drkRoundBtn:hover,
.roundBtn:hover {
  opacity: 1;
}

.drkRoundBtn {
  color: darkslategrey;
  border-color: darkslategrey;
}

.msg {
  border-radius:  10px;
  background: white;
  line-height: 40px;
  color: #232323;
  font-size: 30px;
  width: 50px;
  margin: 10px;
  text-align: center;
}
</style>
