<template>
  <div id="app">
    <input id="in1" type="text" v-model="input.login" placeholder="Введите логин">
    <input id="in2" type="text" v-model="input.password" placeholder="Введите пароль">
    <button @click="Pupa">Enter</button>
    <router-link to="About">fdfdfd</router-link>
    </div>
    <router-view></router-view>

</template>

<script>
  export default {
    name:'App',

    data() {
      return {
        input: {
          login: '',
          password: ''
        }
      }
    },
    methods:{
      Pupa(){
        if(this.input.login === 'admin' && this.input.password === 'admin')
        {
          router.next()
        }
      }
    }
  }

</script>


