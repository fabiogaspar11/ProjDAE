<template>
 <div>
  <!--<NavBar/>-->
   <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand ><img src="../dist/logoLogin.png" width="50px" height="30px" ></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <!--
            <b-nav-item @click="url='/api/prescriptions'">Prescriptions</b-nav-item>
            <b-nav-item @click="url='/api/administrators'">Administrators</b-nav-item>
            <b-nav-item @click="url='/api/patients'">Patients</b-nav-item>
            <b-nav-item >Biomedic Data Measures</b-nav-item>
            -->
            <b-nav-item to="patients">Patients</b-nav-item>
            <b-nav-item to="administrators">Administrators</b-nav-item>
            <b-nav-item to="">Healthcare Professionals</b-nav-item>

            <b-nav-item to="">Biomedic Data Measures</b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

  <!--<Table :url="url" />-->
  </div>
</template>


<script>
export default {
}
</script>
