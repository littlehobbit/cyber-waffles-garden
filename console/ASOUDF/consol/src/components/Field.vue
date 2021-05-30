<template>
    <div>
        <p>{{name}} :</p>
        <input type="text" v-bind:value="value" v-on:input="$emit('input',$event.target.value)">     
    </div>
</template>

<script>
import ScrollPicker from 'vue3-scroll-picker'
export default {
    data(){
        return{
            name:'',
            ID:""
        }
   },
   components:{
       ScrollPicker
   }, 
   mounted:()=> {
       axios.get(ip+"events/all").then(response => (this.info = response.data.bpi))
   },
   props: ['value']
}

</script>