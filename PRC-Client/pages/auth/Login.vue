<template>
  <div class="wrapper">
    <div id="formContent">
      <!-- Tabs Titles -->

      <!-- Icon -->
      <div class="fadeIn first">
        <br />
        <img src="../../static/logoLogin.png" id="icon" alt="User Icon" />

        <h1>Cardiac Rehabilitation Programs</h1>
      </div>

      <!-- Login Form -->
      <form  @submit.prevent="onSubmit" @reset="onReset">
       <b-form-group label="Username" class="mb-3 ml-1 mr-1">
          <b-input
            name="username"
            placeholder="Your username"
            v-model.trim="username"
            required
          />
        </b-form-group>
       <b-form-group label="Password" class=" mb-3 ml-5 mr-5">
        <b-input
          name="password"
          type="password"
          placeholder="Your password"
          v-model="password"
          required
        />
      </b-form-group>
      <b-button type="reset" class="btn-info mb-4 mr-2">Reset</b-button>
      <b-button type="submit" class="btn-dark mb-4  ml-2">Submit</b-button>
      </form>
    </div>
  </div>
</template>
<script>
export default {
  auth: false,
  data() {
    return {
      username: null,
      password: null,
    };
  },
  methods: {
    onSubmit() {
      let promise = this.$auth.loginWith("local", {
        data: {
          username: this.username,

          password: this.password,
        },
      });
      promise.then(() => {
        this.$toast.success("You are logged in!").goAway(3000);
        // check if the user $auth.user object is set
        // TODO redirect based on the user role
        // eg:
        if (this.$auth.user.groups.includes("Patient")) {
          this.$router.push("/dashboard");
        } else if (this.$auth.user.groups.includes("HealthcareProfessional")) {
          this.$router.push("/dashboardHealthcareProfessionals");
        }else{
          this.$router.push("/patients");
        }
      });
      promise.catch(() => {
        this.$toast
          .error("Sorry, you cant login. Ensure your credentials are correct")
          .goAway(3000);
      });
    },
    onReset() {
      this.username = null;
      this.password = null;
    },
  },
};
</script>
<style>
body {
  font-family: "SansSerif", sans-serif;
  height: 100vh;
}

a {
  color: red;
  display: inline-block;
  text-decoration: none;
  font-weight: 400;
}

h2 {
  text-align: center;
  font-size: 16px;
  font-weight: 600;
  text-transform: uppercase;
  display: inline-block;
  margin: 40px 8px 10px 8px;
  color: #cccccc;
}

/* STRUCTURE */

.wrapper {
  margin-top: 100px;
  display: flex;
  align-items: center;
  flex-direction: column;
  justify-content: center;
  width: 100%;
  min-height: 100%;
}

#formContent {
  -webkit-border-radius: 10px 10px 10px 10px;
  border-radius: 10px 10px 10px 10px;
  background: #fff;
  padding: 30px;
  width: 90%;
  max-width: 550px;
  position: relative;
  padding: 0px;
  -webkit-box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
  box-shadow: 0 30px 60px 0 rgba(0, 0, 0, 0.3);
  text-align: center;
  vertical-align: center;
}

#formFooter {
  background-color: #f6f6f6;
  border-top: 1px solid #dce8f1;
  padding: 25px;
  text-align: center;
  -webkit-border-radius: 0 0 10px 10px;
  border-radius: 0 0 10px 10px;
}

/* TABS */

h2.inactive {
  color: #cccccc;
}

h2.active {
  color: #0d0d0d;
  border-bottom: 2px solid #5fbae9;
}

/* FORM TYPOGRAPHY*/

input[type="button"],
input[type="submit"],
input[type="reset"] {
  background-color: red;
  border: none;
  color: white;
  padding: 15px 80px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  text-transform: uppercase;
  font-size: 13px;
  -webkit-box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
  box-shadow: 0 10px 30px 0 rgba(95, 186, 233, 0.4);
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
  margin: 5px 20px 40px 20px;
  -webkit-transition: all 0.3s ease-in-out;
  -moz-transition: all 0.3s ease-in-out;
  -ms-transition: all 0.3s ease-in-out;
  -o-transition: all 0.3s ease-in-out;
  transition: all 0.3s ease-in-out;
}

input[type="button"]:hover,
input[type="submit"]:hover,
input[type="reset"]:hover {
  background-color: #39ace7;
}

input[type="button"]:active,
input[type="submit"]:active,
input[type="reset"]:active {
  -moz-transform: scale(0.95);
  -webkit-transform: scale(0.95);
  -o-transform: scale(0.95);
  -ms-transform: scale(0.95);
  transform: scale(0.95);
}

input[type="text"] {
  background-color: #f6f6f6;
  border: none;
  color: #0d0d0d;
  padding: 15px 32px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 16px;
  margin: 5px;
  width: 85%;
  border: 2px solid #f6f6f6;
  -webkit-transition: all 0.5s ease-in-out;
  -moz-transition: all 0.5s ease-in-out;
  -ms-transition: all 0.5s ease-in-out;
  -o-transition: all 0.5s ease-in-out;
  transition: all 0.5s ease-in-out;
  -webkit-border-radius: 5px 5px 5px 5px;
  border-radius: 5px 5px 5px 5px;
}

input[type="text"]:focus {
  background-color: #fff;
  border-bottom: 2px solid #5fbae9;
}

input[type="text"]:placeholder {
  color: #cccccc;
}

h1 {
  color: #60a0ff;
}

/* OTHERS */

*:focus {
  outline: none;
}

#icon {
  width: 30%;
}
</style>
