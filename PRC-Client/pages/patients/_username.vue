<template>

<div>
  <h1>dsadjhsajidhaskjdhksajhdkjsahkdsakhksajh</h1>
<Template :url="urlDetails" :fields="fields" :type="students"/>
  </div>
</template>

<script>
export default {
  data () {
    return {
      urlDetails:this.username,
      fields:['username']
    }
  },
  props:{
      url:String,
    },
    data(){
      return{
        ola:"ds"
      }
    },
    computed: {
    code() {
      return this.$route.params.username
    }
  },
}
</script>
<style></style>
