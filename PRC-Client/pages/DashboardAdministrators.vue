<template>
<div>
  <NavBar></NavBar>
  <z class="container mt-5  d-flex justify-content-around">
  <div class="row">
    <div class="col mr-2">
      <Card :element="'diseases'" :text="'Diseases'" :url="'diseases'"></Card>
    </div>
    <div class="col mr-2">
      <Card :element="'healthCareProfessionals'" :text="'Healthcare Professionals'" :url="'healthCareProfessionals'"></Card>
    </div>
    <div class="col mr-2">
      <Card :element="'administrators'" :text="'Administrators'" :url="'administrators'"></Card>
    </div>
       <div class="col mr-2">
      <Card :element="'patients'" :text="'Patients'" :url="'patients'"></Card>
    </div>
    <div class="col mr-2 mt-5">
      <Card :element="'biomedicDataTypes'" :text="'Biomedic Types'" :url="'biomedicDataTypes'"></Card>
    </div>
  </div>
</z>
</div>
</template>

<script>
export default {
  middleware: 'isAdministrator',
}
</script>

<style>
</style>
