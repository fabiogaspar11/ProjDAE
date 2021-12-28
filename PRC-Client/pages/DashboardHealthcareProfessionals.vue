<template>
<div>
  <NavBarPatientHealthcareProfessional></NavBarPatientHealthcareProfessional>
  <div class="container-sm mt-5">
  <div class="row">
    <div class="col">
    <Card :element="'patients'" :text="'Patients'"></Card>
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
