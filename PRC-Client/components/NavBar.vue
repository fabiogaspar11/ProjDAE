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

    <!--<Table :url="url" />-->
  </div>
</template>

<script>
export default {
  methods:{
    signOut() {
      this.$auth.logout()
      this.$router.push('/')
    }
  },
  computed: {
    username() {
      return this.$auth.user.sub;
    },
    urlUser() {
      return this.$auth.user.groups.includes("Patient")
        ? "patients"
        : "healthcareProfessionals";
    },
    urlDashboard() {
      return this.$auth.user.groups.includes("Patient")
        ? "/dashboard"
        : "/dashboardHealthcareProfessionals";
    },
  },
};
</script>
