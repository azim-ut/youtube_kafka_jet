<!-- Please remove this file from your project -->
<template>
  <div class="ticket">
    <div style="width: 20%;">
      <button class="drkRoundBtn" @click="submitRoute()">x</button>
    </div>
    <div style="width: 80%;">
      Name: {{ticket.name}}
      <br/>
      Route:
      <ul>
        <li v-for="row in ticket.route"></li>
      </ul>
    </div>
    <div style="width: 20%;">
      <button class="drkRoundBtn" @click="submitRoute()">v</button>
    </div>
  </div>
</template>
<script>
import axios from "axios"
export default {
  props: {
    ticket: [],
  },
  methods: {
    submitRoute() {
      axios.post("/ship/api/routes/route");
    }
  }
}
</script>
<style>
.ticket{
  display: inline-flex;
  margin: 5px 10px;
  padding: 5px;
  background: aqua;
}
</style>
