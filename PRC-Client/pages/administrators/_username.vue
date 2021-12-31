<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
            <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
            <b-button v-b-modal.modal-2 class="text-center" variant="primary">Change Password</b-button>
        <b-modal id="modal-1" title="Edit" @ok="update()">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">Name</span>
            </div>
            <b-input
              v-model.trim="name"
              type="text"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter your name"
              :state="isNameValid"
            />
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">BirthDate</span>
            </div>
            <b-input
              v-model.trim="birthDate"
              type="text"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="dd/mm/yyyy"
              :state="isbirthDateValid"
            />
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">Email</span>
            </div>
            <b-input
              v-model.trim="email"
              ref="email"
              type="text"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter your email"
              :state="isEmailValid"
            />
          </div>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon1">Contact</span>
            </div>
            <b-input
              v-model.trim="contact"
              type="text"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter your contact"
              :state="isContactValid"
            />
          </div>
        </b-modal>
          <b-modal id="modal-2" title="Edit" @ok="updatePassword()">
            <div class="input-group mb-4">
              <span class="input-group-text">Current Password</span>
              <b-input required v-model.trim="oldPassword" type="password" :state="isOLDPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your old password"/>
              <p>{{isOLDPasswordValidFeedback}}</p>
          </div>
          <div class="input-group mb-4">
              <span class="input-group-text">New Password</span>
              <b-input required v-model.trim="newPassword" type="password" :state="isPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your new password"/>
              <p>{{isPasswordValidFeedback}}</p>
          </div>
      </b-modal>
      </div>
    </div>
</template>



<script>
export default {
  data() {
    return {
      fields: [
        "username",
        "name",
        "birthDate",
        "healthNumber",
        "contact",
        "email",
      ],
      entidade: [],
      state: true,
      name: null,
      birthDate: null,
      email: null,
      contact: null,
      oldPassword: null,
      newPassword: null
    };
  },
  props: {
    url: String,
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      let nameLen = this.name.length;
      if (nameLen < 3 || nameLen > 25) {
        return "The name is too short - length must be between 3 and 25";
      }
      return "";
    },
    isNameValid() {
      if (this.isNameValidFeedback === null) {
        return null;
      }
      return this.isNameValidFeedback === "";
    },
    isContactValidFeedback() {
      if (!this.contact) {
        return null;
      }
      let contactString = this.contact.toString();
      let contactLen = contactString.length;
      if (contactLen != 9) {
        return "The contact is invalid - contact must have 9 digits exactly";
      }
      var phoneRegex = /^(9[0-9])([0-9]{7})?$/;
      return phoneRegex.test(contactString)
        ? ""
        : "Contact is invalid - Not in PT format";
    },
    isContactValid() {
      if (this.isContactValidFeedback === null) {
        return null;
      }
      return this.isContactValidFeedback === "";
    },
    isOLDPasswordValidFeedback() {
      if (!this.oldPassword) {
        return null;
      }
      let passwordLen = this.oldPassword.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "Old Password is too short, lenght must be between 3 and 255";
      }
      return "";
    },
    isOLDPasswordValid() {
      if (this.isOLDPasswordValidFeedback === null) {
        return null;
      }
      return this.isOLDPasswordValidFeedback === "";
    },
    isPasswordValidFeedback() {
      if (!this.newPassword) {
        return null;
      }
      let passwordLen = this.newPassword.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "Password is too short, lenght must be between 3 and 255";
      }
      return "";
    },
    isPasswordValid() {
      if (this.isPasswordValidFeedback === null) {
        return null;
      }
      return this.isPasswordValidFeedback === "";
    },
    isEmailValidFeedback() {
      if (!this.email) {
        return null;
      }
      return this.$refs.email.checkValidity()
        ? ""
        : "Email is not valid - the email format must be like name@domain";
    },
    isEmailValid() {
      if (this.isEmailValidFeedback === null) {
        return null;
      }
      return this.isEmailValidFeedback === "";
    },
    isbirthDateValidFeedback() {
      if (!this.birthDate) {
        return null;
      }
      var date_regex =
        /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
      var currentdate = new Date();
      var dateSplitted = this.birthDate.split("/");
      var dateRegexValid = date_regex.test(this.birthDate);
      if (!dateRegexValid) {
        return "The date is invalid - format dd/mm/yyyy";
      }
      if (parseInt(dateSplitted[2]) < currentdate.getFullYear()) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) < currentdate.getMonth() + 1
      ) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) == currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) <= currentdate.getDate()
      ) {
        return "";
      }
      return "The date is bigger than todays date";
    },
    isbirthDateValid() {
      if (this.isbirthDateValidFeedback === null) {
        return null;
      }
      return this.isbirthDateValidFeedback === "";
    },
  },
  created() {
    this.getAdministrator();
  },
  methods: {
    getAdministrator() {
      this.$axios
        .$get(`/api/administrators/${this.username}`)
        .then((entidade) => {
          this.entidade = [entidade];
        });
    },
    remove() {
      this.$axios.$delete(`/api/administrators/${this.username}`).then(() => {
        this.getAdministrator();
      });
    },
    update() {
      let adminUpdated = {};
      if (this.name != null) {
        adminUpdated.name = this.name;
      }
      if (this.email != null) {
        adminUpdated.email = this.email;
      }
      if (this.contact != null) {
        adminUpdated.contact = this.contact;
      }
      if (this.birthDate != null) {
        adminUpdated.birthDate = this.birthDate;
      }

      this.$axios
        .$put(`/api/administrators/${this.username}`, adminUpdated)
        .then(() => {
          this.$toast.info(`Administrator ${this.username}  updated!`).goAway(3000);
          this.getAdministrator();
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
      updatePassword(){
       let administrator = {};
      if(this.isOLDPasswordValid){
          administrator.passwordOld = this.oldPassword;
      }
      if(this.isPasswordValid){
          administrator.password = this.newPassword;
      }
      if(Object.keys(administrator).length != 2){
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }
      this.$axios
        .$put(`/api/administrators/${this.username}/password`, administrator)
        .then(() => {
          this.oldPassword = null;
          this.newPassword = null;
          this.$toast.info(`Administrator ${this.username}  password updated!`).goAway(3000);
          this.getAdministrator();
        })
        .catch((response) => {
            this.$toast.info(`Error - ${response.response.data}`).goAway(3000);
             this.getAdministrator();
        });
    }
  },
};
</script>
