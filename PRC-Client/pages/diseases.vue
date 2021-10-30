<template>
<div>
  <Template :url="url"/>
</div>


</template>

<script>
import Template from '../components/Template.vue'

export default {
  components: {
    Template,
  },
  data(){
    return{
      url:"/api/diseases"
    }
  }
}

</script>
