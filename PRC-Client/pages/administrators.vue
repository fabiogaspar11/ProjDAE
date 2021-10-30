<template>
<div>
    <NavBar/>
    <Table :url="url"/>
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
      url:"/api/administrators"
    }
  }
}

</script>
