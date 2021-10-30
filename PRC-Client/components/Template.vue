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
  props:{
    url:String
  }
}
</script>

<style>

</style>
