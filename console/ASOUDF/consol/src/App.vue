<template>
  <div>
    <p>{{AccessToken}}</p>
    <p>{{RefreshToken}}</p>
    
  </div>
</template>

<script>

export default {
  name: 'App',
  components: {

  }, 
  data(){
    return {
      AccessToken: '',
      RefreshToken: ''
    }
  }
}
</script>

<style>

</style>
