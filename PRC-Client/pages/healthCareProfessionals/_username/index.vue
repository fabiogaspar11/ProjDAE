<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
    <h3> HealthCare Professionals </h3>
      <b-table small striped hover :items="healthCareProfessional" :fields="fields"></b-table>
        <div class="mt-3 mb-5 text-center">
          <b-button v-b-modal.modal-1>Edit</b-button>
          <b-button v-if="!this.isAdmin" v-b-modal.modal-2 variant="primary">Change password</b-button>
        </div>


    <b-modal id="modal-1" title="Edit" @ok="update">
      <div class="input-group mb-4">
        <span class="input-group-text">Name</span>
        <b-input required v-model.trim="name" type="text" :state="isNameValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your name"/>
      </div>
        <p>{{isNameValidFeedback}}</p>
      <div class="input-group mb-4">
        <span class="input-group-text">Birthdate</span>
        <b-input required  v-model.trim="birthDate" type="text" :state="isbirthDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1"/>
        <b-form-datepicker
          id="ex-disabled-readonly"
          button-only
          :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
          @context="onContext"
        ></b-form-datepicker>
        <p style="color: #dc3545;">{{isbirthDateValidFeedback}}</p>

      </div>
        <p>{{isTypeValidFeedback}}</p>
      <div class="input-group mb-4">
        <span class="input-group-text">Email</span>
        <b-input required v-model.trim="email" ref="email" type="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your email"/>
      </div>
        <p>{{isEmailValidFeedback}}</p>
      <div class="input-group mb-4">
        <span class="input-group-text">Contact</span>
        <b-input required v-model.trim="contact" type="number" :state="isContactValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your contact"/>
      </div>
        <p>{{isContactValidFeedback}}</p>
      <b-alert v-model="showDismissibleAlertEdit" variant="danger" dismissible>
        <p> {{alertData}}</p>
      </b-alert>
    </b-modal>

    <b-modal ref="updatePasswordModel" id="modal-2" class="justify-content-center" title="Edit Password" @ok="updatePassword">
      <div class="input-group mb-4">
        <span class="input-group-text">Password</span>
        <b-input required v-model.trim="passwordOld" type="password" :state="isPasswordOldValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your old password"/>
      </div>
        <p>{{isPasswordOldValidFeedback}}</p>
      <div class="input-group mb-4">
        <span class="input-group-text">New Password</span>
        <b-input required  v-model.trim="passwordNew" type="password" :state="isPasswordNewValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your new password"/>
      </div>
        <p>{{isPasswordNewValidFeedback}}</p>
      <b-alert v-model="showDismissibleAlertPassword" variant="danger" dismissible>
        {{alertPasswordInvalid}}
      </b-alert>
    </b-modal>
    </b-container>
  </div>
</template>

<script>
export default {
  middleware: "isAdminOrHealthcareProfAccessingHisData",
  data() {
    return {
      fields: ["healthNumber", "name", "birthDate", "email", "contact"],
      healthCareProfessional: [],
      state: true,
      healthNumber: null,
      name: null,
      birthDate: null,
      email: null,
      contact: null,
      passwordOld: null,
      passwordNew: null,
      showDismissibleAlertEdit: false,
      showDismissibleAlertPassword: false,
      alertData: "",
      alertPasswordInvalid: "",
      currentName: null,
      currentBirthDate: null,
      currentEmail: null,
      currentContact: null,
    };
  },
  props: {
    url: String,
  },
  created() {
    this.getHealthCareProfessionalData();
  },
  computed: {
    isAdmin() {
      if (this.$auth.user.groups.includes("Administrator")) {
        return true;
      }
      return false;
    },
    username() {
      return this.$route.params.username;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      if (this.name == this.currentName) {
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
      if (this.contact == this.currentContact) {
        return "Contact is equal to current contact";
      }
      let contactString = this.contact.toString();
      let contactLen = contactString.length;
      if (contactLen !== 9) {
        return "The contact is invalid - contact must have 9 digits exactly";
      }
      const phoneRegex = /^(9[0-9])([0-9]{7})?$/;
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
    isPasswordOldValidFeedback() {
      this.showDismissibleAlertPassword = false;
      if (!this.passwordOld) {
        return null;
      }
      let passwordLen = this.passwordOld.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "Password Old is too short, length must be between 3 and 255";
      }
      return "";
    },
    isPasswordOldValid() {
      if (this.isPasswordOldValidFeedback === null) {
        return null;
      }
      return this.isPasswordOldValidFeedback === "";
    },
    isPasswordNewValidFeedback() {
      this.showDismissibleAlertPassword = false;
      if (!this.passwordNew) {
        return null;
      }
      let passwordLen = this.passwordNew.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "Password New is too short, length must be between 3 and 255";
      }
      return "";
    },
    isPasswordNewValid() {
      if (this.isPasswordNewValidFeedback === null) {
        return null;
      }
      return this.isPasswordNewValidFeedback === "";
    },

    isEmailValidFeedback() {
      if (!this.email) {
        return null;
      }
      if (this.email == this.currentEmail) {
        return "Email is equal to current email";
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
      if (!this.birthDate || this.birthDate === "No date selected") {
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
      return "The birthdate date is bigger than todays date";
    },
    isbirthDateValid() {
      if (this.isbirthDateValidFeedback === null) {
        return null;
      }
      return this.isbirthDateValidFeedback === "";
    },
    isFormValid() {
      this.showDismissibleAlertEdit = false;
      if (
        this.name == null &&
        this.email == null &&
        this.contact == null &&
        this.birthDate == null
      ) {
        this.alertData = "You need to change some data to update";
        return false;
      }
      this.alertData = "Every Data must be correct";
      if (this.isNameValid === false) {
        return false;
      }
      if (this.isbirthDateValid === false) {
        return false;
      }
      if (this.isContactValid === false) {
        return false;
      }
      if (this.isEmailValid === false) {
        return false;
      }
      this.showDismissibleAlertEdit = true;
      return true;
    },
    isFormPasswordValid() {
      this.showDismissibleAlertPassword = false;
      if (this.passwordOld == null || this.passwordNew == null) {
        this.alertPasswordInvalid =
          "You must fill all the fields to update password";
        return false;
      }
      if (this.isPasswordOldValid === false) {
        return false;
      }
      if (this.isPasswordNewValid === false) {
        return false;
      }
      return true;
    },
  },
  methods: {
    getHealthCareProfessionalData() {
      this.$axios
        .$get(`/api/healthcareProfessionals/${this.username}`)
        .then((entidade) => {
          this.healthCareProfessional = [entidade];
          this.currentName = entidade.name;
          this.currentBirthDate = entidade.birthDate;
          this.currentEmail = entidade.email;
          this.currentContact = entidade.contact;
        })
        .catch((error) => {
          if (error.response.status == 403 || error.response.status == 404) {
            this.$router.push("./../../healthCareProfessionals");
            return;
          }
        });
    },
    update(bvModalEvt) {
      if (this.isFormValid) {
        this.$axios
          .$put(`/api/healthcareProfessionals/${this.username}`, {
            name: this.name,
            email: this.email,
            contact: this.contact,
            birthDate: this.birthDate,
          })
          .then(() => {
            this.name = null;
            this.email = null;
            this.contact = null;
            this.birthDate = null;
            this.$toast
              .info(
                "HealthCare Professional " +
                  this.username +
                  " updated succesfully"
              )
              .goAway(3000);

            this.getHealthCareProfessionalData();
          });
      } else {
        bvModalEvt.preventDefault();
        this.showDismissibleAlertEdit = true;
      }
    },
    updatePassword(bvModalEvt) {
      bvModalEvt.preventDefault();
      if (this.isFormPasswordValid) {
        this.$axios
          .$put(`/api/healthcareProfessionals/${this.username}/password`, {
            passwordOld: this.passwordOld,
            password: this.passwordNew,
          })
          .then(() => {
            this.passwordOld = null;
            this.passwordNew = null;
            this.$toast
              .info(
                `HealthCare Professional (${this.username}) - password updated!`
              )
              .goAway(3000);

            this.getHealthCareProfessionalData();
          })
          .catch((error) => {
            this.alertPasswordInvalid = error.response.data;
            this.showDismissibleAlertPassword = true;
          });
      } else {
        this.alertPasswordInvalid = "Every Data must be correct";
        this.showDismissibleAlertPassword = true;
      }
    },
    onContext(ctx) {
      if (ctx.selectedDate == null) {
        return null;
      }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.birthDate = ctx.selectedFormatted;
    },
  },
};
</script>

