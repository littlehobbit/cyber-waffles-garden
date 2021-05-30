<template>
  <div id="app">
    <input id="in1" type="text" v-model="input.login" placeholder="Введите логин">
    <input id="in2" type="text" v-model="input.password" placeholder="Введите пароль">
    <button @click="Pupa">Enter</button>
    </div>
    <router-view></router-view>
</template>

<script>
import { render } from 'vue';
var accessToken = 0
var refreshToken = 0

import router from './router'
import About from './views/About.vue';

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
        var url = "http://192.168.43.143:3000/";

        var xhr = new XMLHttpRequest();
        xhr.open("POST", url+"login");
        xhr.setRequestHeader("Content-Type", "application/json");
        var data = new Object;
        data.email = this.input.login;
        data.password = this.input.password;
        xhr.send(JSON.stringify(data));
        xhr.onreadystatechange = function () {
        if (xhr.response !== null) {
          if(xhr.readyState === 4){
            console.log(xhr.status);
          if(xhr.status == 200)
          {
            accessToken = JSON.parse(xhr.response).accesstoken
            refreshToken = JSON.parse(xhr.response).refreshtoken
            var xhr2 = new XMLHttpRequest();
            xhr2.open("POST", url+"logout");
            xhr2.setRequestHeader("Authorization", "iteam "+ accessToken)
            xhr2.onreadystatechange = function(){
              if(xhr2.readyState == 4){
              console.log(xhr2.status);
                if(xhr2.status == 200)
                {
                  sessionStorage.setItem("accessToken", accessToken);
                  sessionStorage.setItem("refreshToken", refreshToken);
                  router.push({path: '/about'} )
                }
              else{
                var xhr = new XMLHttpRequest();
                xhr.open("POST", url+"logout");
                xhr.setRequestHeader("Authorization", "iteam "+ accessToken)
                xhr.send()
              }
              }
            }
            xhr2.send()
          }
          }
        }
      };
      }
      
    }, 
    components:{
      About
    }
  }

</script>


