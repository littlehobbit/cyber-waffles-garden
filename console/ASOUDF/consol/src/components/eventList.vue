<template>
    <div class="content" v-for="(i, index) in info" v-bind:key="info[index]"> 
  <span>  <scroll-picker 
    v-model="selections"
  /> {{ info[index] }}</span>
  <div v-bind:id="`container-${index}`">

  </div>
</div>
</template>

<script>
import ScrollPicker from 'vue3-scroll-picker'
export default {
    data(){
        return{
            info:[],
            ip:""
        }
   },
   components:{
       ScrollPicker
   }, 
   mounted:()=> {
       axios.get(ip+"events/all").then(response => (this.info = response.data))
   }
}

</script>