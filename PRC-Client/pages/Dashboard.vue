<template>
<div>
  <NavBar></NavBar>
  <div class="container mt-5  d-flex justify-content-around">
  <div class="row">
    <div class="col  mr-2">
      <Card :element="'prescriptions'" :text="'Prescriptions'" :url="'patients/' + username + '/prescriptions'"></Card>
    </div>
    <div class="col  mr-2">
      <Card :element="'biomedicMeasures'" :text="'Biomedic Measures'" :url="'patients/' + username + '/biomedicMeasures'"></Card>
    </div>
    <div class="col  mr-2">
      <Card :element="'healthCareProfessionals'" :text="'Healthcare Professionals'" :url="'patients/' + username + '/healthcareProfessionals'"></Card>
    </div>
     <div class="col  mr-2">
      <Card :element="'diseases'" :text="'Diseases Diagnosis'" :url="'patients/' + username + '/diseases'"></Card>
    </div>
  </div>
</div>
</div>
</template>

<script>
export default {
  computed: {
    username() {
      return this.$store.state.username
    }
  }
}
</script>

<style>
</style>
