<template>
  <div>
    <!--<NavBar/>-->
    <div>
      <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand :to="urlDashboard"
          ><img src="../dist/logoLogin.png" width="50px" height="30px"
        /></b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav class="ml-auto">
            <b-nav-item :to="'/' + urlUser + '/' + username">
              <div class="d-flex align-items-center">
                <div class="p-2">{{ this.$auth.user.sub }}</div>
                <div class="p-2">
                  <img src="../images/user.png" width="40px" alt="" />
                </div>
              </div>
            </b-nav-item>
            <div class="d-flex align-items-center">
                <b-nav-item @click.prevent="signOut">Sign Out</b-nav-item>
            </div>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
  </div>
</template>

<script>
export default {
  methods:{
    signOut() {
      this.$auth.logout()
      this.$router.push('/')
    },
  },
  computed: {
    username() {
      if(!this.$auth.user){
          return "";
        }
      return this.$auth.user.sub;
    },
      urlDashboard() {
        if(!this.$auth.user){
          return "";
        }
      if (this.$auth.user.groups.includes("Patient")) {
        return "/dashboard";
      } else if (this.$auth.user.groups.includes("HealthcareProfessional")) {
        return "/dashboardHealthcareProfessionals";
      } else {
       return "/dashboardAdministrators";
      }
    },
    urlUser() {
      if(!this.$auth.user){
          return "";
        }
        if (this.$auth.user.groups.includes("Patient")) {
          return "patients";
        } else if (this.$auth.user.groups.includes("HealthcareProfessional")) {
          return "healthcareProfessionals";
        } else {
          return "administrators";
        }
    },
  },
};
</script>
