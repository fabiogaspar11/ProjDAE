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
  props:{
    fields:[],
    url:String,
    type:String
  }
}
</script>

<style>

</style>
