<template>
 <div>
  <!--<NavBar/>-->
   <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand to="/dashboard"><img src="../dist/logoLogin.png" width="50px" height="30px" ></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav class="ml-auto"  >
            <b-nav-item  :to='"/"+urlUser+"/"+username'>
          <div class="d-flex align-items-center">
             <div class="p-2">My Profile</div>
             <div class="p-2"><img src="../images/user.png" width="40px" alt=""></div>
          </div>
            </b-nav-item>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>

  <!--<Table :url="url" />-->
  </div>
</template>

<script>
export default {
    computed: {
     username() {
      return this.$auth.user.sub;
    },
    urlUser(){
      return this.$auth.user.groups.includes("Patient") ? "patients" : "healthcareProfessionals";
    },
    }
}
</script>
