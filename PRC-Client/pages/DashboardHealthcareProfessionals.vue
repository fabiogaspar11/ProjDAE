<template>
<div>
  <NavBar></NavBar>
  <div class="container mt-5 d-flex justify-content-around">
  <div class="row">
    <div class="col mr-5">
     <Card :element="'patients'" :text="'Patients'"></Card>
    </div>
    <div class="col mr-5">
      <Card :element="'prescriptions'" :text="'Patients Prescriptions'"></Card>
    </div>
        <div class="col mr-5">
      <Card :element="'biomedicMeasures'" :text="'Patients Biomedic Data'"></Card>
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
