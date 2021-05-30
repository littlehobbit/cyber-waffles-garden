<template>
    <div v-on:click = "elementClick">
        <p>{{name}}</p>
    </div>
</template>

<script>
export default {
    data(){
        return{
        name: "",
        id: "3"
        }
    },
    methods:{
        elementClick(){

        }
    }
}
</script>