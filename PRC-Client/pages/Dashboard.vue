<template>
<div>
  <NavBarPatientHealthcareProfessional></NavBarPatientHealthcareProfessional>
  <div class="container mt-5  d-flex justify-content-around">
  <div class="row">
    <div class="col  mr-2">
    <Card :element="'prescriptions'" :text="'Prescriptions'"></Card>
    </div>
    <div class="col  mr-2">
    <Card :element="'biomedicMeasures'" :text="'Biomedic Measures'"></Card>
    </div>
    <div class="col  mr-2">
    <Card :element="'healthCareProfessionals'" :text="'Healthcare Professionals'"></Card>
    </div>
     <div class="col  mr-2">
    <Card :element="'diseases'" :text="'Diseases Diagnosis'"></Card>
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
