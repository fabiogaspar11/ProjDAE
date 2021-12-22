<template>
 <div>
  <!--<NavBar/>-->
   <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand ><img src="../dist/logoLogin.png" width="50px" height="30px" ></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="/patients">Patients</b-nav-item>
            <b-nav-item to="/administrators">Administrators</b-nav-item>
            <b-nav-item to="/healthcareProfessionals">Healthcare Professionals</b-nav-item>
            <b-nav-item to="/diseases">Diseases</b-nav-item>
            <b-nav-item to="/biomedicData">Biomedic Data</b-nav-item>
            <b-nav-item to="/biomedicMeasures">Biomedic Measures</b-nav-item>
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
