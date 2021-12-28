<template>
<div>
  <NavBar></NavBar>
  <div class="container mt-5  d-flex justify-content-around">
  <div class="row">
    <div class="col  mr-2">
    <Card :element="'patients'" :text="'Patients'"></Card>
    </div>
    <div class="col  mr-2">
    <Card :element="'healthCareProfessionals'" :text="'Healthcare Professionals'"></Card>
    </div>
    <div class="col  mr-2">
    <Card :element="'administrators'" :text="'Administrators'"></Card>
    </div>
     <div class="col  mr-2">
    <Card :element="'biomedicDataTypes'" :text="'Biomedic Types'"></Card>
    </div>
  </div>
</div>
</div>
</template>

<script>
export default {
  computed: {
    username() {
      return this.$route.params.username;
  }
  }
}
</script>

<style>
</style>
