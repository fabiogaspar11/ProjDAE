<template>
<div>
    <NavBar></NavBar>
    <div>
      <div class="d-flex justify-content-center" style="margin-top: 4%">
        <b-container>
          <b-table
            striped
            over
            class="w-100 p-3"
            hover
            :items="entidade"
            :fields="fields"
          >
          </b-table>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data(){
    return{
      fields:['username','name','email','birthDate','contact','citizenNumber'],
    }
  },
  props:{
      url:String,
    },
    computed: {
    username() {
      return this.$route.params.username
    }
  },
}
</script>
<style></style>
