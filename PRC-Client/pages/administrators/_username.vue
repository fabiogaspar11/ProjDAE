<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <h3> Details</h3>
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
            <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
            <b-button v-if="this.isAdminItselfLogged" v-b-modal.modal-2 class="text-center" variant="primary">Change Password</b-button>
        <b-modal id="modal-1" title="Edit" @ok="update()">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Names</span>
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
             <p>{{isNameValidFeedback}}</p>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">BirthDate</span>
            </div>
            <b-input
              v-model.trim="birthDate"
              type="text"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="dd/mm/yyyy"
              :state="isbirthDateValid"
            />
            <b-form-datepicker
              id="ex-disabled-readonly"
              button-only
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              @context="onContext"
            ></b-form-datepicker>
          </div>
            <p>{{isbirthDateValidFeedback}}</p>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Email</span>
            </div>
            <b-input
              v-model.trim="email"
              ref="email"
              type="email"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter your email"
              :state="isEmailValid"
            />

          </div>
              <p>{{isEmailValidFeedback}}</p>

          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text">Contact</span>
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
              <p>{{isContactValidFeedback}}</p>
        </b-modal>
          <b-modal id="modal-2" title="Edit" @ok="updatePassword()">
            <div class="input-group mb-4">
              <span class="input-group-text">Current Password</span>
              <b-input required v-model.trim="oldPassword" type="password" :state="isOLDPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your old password"/>
          </div>
              <p>{{isOLDPasswordValidFeedback}}</p>
          <div class="input-group mb-4">
              <span class="input-group-text">New Password</span>
              <b-input required v-model.trim="newPassword" type="password" :state="isPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your new password"/>
          </div>
              <p>{{isPasswordValidFeedback}}</p>
      </b-modal>
      </div>
    </div>
</template>



<script>
export default {
  middleware: "isAdministrator",
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
      newPassword: null,
      currentName:null,
      currentBirthDate: null,
      currentEmail: null,
      currentContact: null
    };
  },
  props: {
    url: String,
  },
  computed: {
    isAdminItselfLogged(){
      return this.$route.params.username == this.$auth.user.sub;
    },
    username() {
      return this.$route.params.username;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      if(this.name == this.currentName){
        return "Name is equal to current name";
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
      if(this.contact == this.currentContact){
        return "Contact is equal to current contact";
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
    isEmailValidFeedback () {
      if (!this.email) {
        return null
      }
      if(this.email == this.currentEmail){
        return "Email is equal to current email";
      }

      return this.$refs.email.checkValidity() ? '' : 'Email is not valid - the email format must be like name@domain'
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
       if(this.birthDate == this.currentBirthDate){
        return "Birthdate is equal to current birthdate";
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
      return "The birthdate date is bigger than todays date";
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
          this.currentName = entidade.name;
          this.currentBirthDate = entidade.birthDate;
          this.currentEmail = entidade.email;
          this.currentContact = entidade.contact;
        })
        .catch((error)=>{
          if(error.response.status == 403 || error.response.status == 404){
            this.$router.push("./../administrators");
            return;
          }
        });
    },
    remove() {
      this.$axios.$delete(`/api/administrators/${this.username}`).then(() => {
        this.getAdministrator();
      });
    },
    update() {
      let adminUpdated = {};
      if (this.isNameValid) {
        adminUpdated.name = this.name;
      }
      if (this.isEmailValid) {
        adminUpdated.email = this.email;
      }
      if (this.isContactValid) {
        adminUpdated.contact = this.contact;
      }
      if (this.isbirthDateValid) {
        adminUpdated.birthDate = this.birthDate;
      }
      if(Object.keys(adminUpdated).length == 0){
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
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
    },
    onContext(ctx) {
       if(ctx.selectedDate == null){
        return null;
    }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.birthDate = ctx.selectedFormatted
    }
  },
};
</script>
