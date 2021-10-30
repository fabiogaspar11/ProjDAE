<template>
<div>
  <Template :url="url" :fields="fields" :type="type"/>
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
      url:"/api/patients",
      fields:['name','contact','birthDate','actions'],
      type:"patients"
  }
  }
}

</script>
