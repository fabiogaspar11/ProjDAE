<template>
<div>
  <NavBarPatient></NavBarPatient>
  <div class="container-sm mt-5">
  <div class="row">
    <div class="col">
    <Card :element="'prescriptions'" :text="'Prescriptions'"></Card>
    </div>
    <div class="col">
    <Card :element="'biomedicMeasures'" :text="'Biomedic Measures'"></Card>
    </div>
    <div class="col">
    <Card :element="'healthCareProfessionals'" :text="'Healthcare Professionals'"></Card>
    </div>
     <div class="col">
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
