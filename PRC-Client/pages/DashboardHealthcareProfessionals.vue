<template>
<div>
  <NavBar></NavBar>
  <div class="container mt-5 d-flex justify-content-around">
  <div class="row">
    <div class="col mr-5">
     <Card :element="'patients'" :text="'Patients'" :url="'healthCareProfessionals/' + username + '/patients'"></Card>
    </div>
    <div class="col mr-5">
      <Card :element="'prescriptions'" :text="'Patients Prescriptions'" :url="'healthCareProfessionals/' + username + '/prescriptions'"></Card>
    </div>
    <div class="col mr-5">
      <Card :element="'biomedicMeasures'" :text="'Patients Biomedic Data'" :url="'healthCareProfessionals/' + username + '/biomedicMeasures'"></Card>
    </div>
  </div>
</div>
</div>
</template>

<script>
export default {
  middleware: "isHealthcareProfessional",
  computed: {
    username() {
      return this.$auth.user.sub;
    }
  },
}
</script>

<style>
</style>
