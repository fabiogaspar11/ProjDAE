<template>
<div>
    <NavBar/>
    <Table :url="url" :fields="fields" :type="type"/>
</div>


</template>

<script>
import NavBar from '../components/NavBar.vue'
import Table from '../components/Table.vue'

export default {
  components: {
    NavBar,
    Table
  },
  data(){
    return{
      url:"/api/administrators",
      fields:['username','name','email','birthDate','contact','citizenNumber','actions'],
      type:"administrators"
    }
  }
}

</script>
