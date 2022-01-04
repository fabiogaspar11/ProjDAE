<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
      <b-table small striped hover :items="entidade" :fields="fields"></b-table>
      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
        <b-button v-b-modal.modal-2 class="text-center" variant="primary"
          >Change Password</b-button
        >
      </div>
      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input
            required
            v-model.trim="name"
            type="text"
            :state="isNameValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your name"
          />
          <p>{{ isNameValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Birthdate</span>
          <b-input
            required
            v-model.trim="birthDate"
            type="text"
            :state="isbirthDateValid"
            placeholder="dd/mm/yyyy"
            class="form-control"
            aria-describedby="basic-addon1"
          />
          <p>{{ isbirthDateValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Email</span>
          <b-input
            required
            v-model.trim="email"
            ref="email"
            type="email"
            :state="isEmailValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your email"
          />
          <p>{{ isEmailValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Contact</span>
          <b-input
            required
            v-model.trim="contact"
            type="number"
            :state="isContactValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your contact"
          />
          <p>{{ isContactValidFeedback }}</p>
        </div>
        <div class="input-group mb-4 d-flex justify-content-center">
          <span class="input-group-text">Gender</span>
          <b-form-select
            v-model="gender"
            :options="optionsGender"
          ></b-form-select>
        </div>
          <p>{{ isGenderValidFeedback }}</p>
      </b-modal>

      <b-modal id="modal-2" title="Edit" @ok="updatePassword()">
        <div class="input-group mb-4">
          <span class="input-group-text">Current Password</span>
          <b-input
            required
            v-model.trim="oldPassword"
            type="password"
            :state="isOLDPasswordValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your old password"
          />
          <p>{{ isOLDPasswordValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">New Password</span>
          <b-input
            required
            v-model.trim="newPassword"
            type="password"
            :state="isPasswordValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your new password"
          />
          <p>{{ isPasswordValidFeedback }}</p>
        </div>
      </b-modal>
    </b-container>
  </div>
</template>

<script>
export default {
  middleware: ["isPatient", "isPatientAccessingHisData"],
  data() {
    return {
      fields: [
        "username",
        "name",
        "birthDate",
        "gender",
        "healthNumber",
        "contact",
        "email",
      ],
      entidade: [],
      state: true,
      name: null,
      email: null,
      birthDate: null,
      gender: null,
      contact: null,
      newPassword: null,
      oldPassword: null,
      currentName: null,
      currentBirthDate: null,
      currentEmail: null,
      currentContact: null,
      currentGender: null,
      optionsGender: [
        { value: null, text: "Please select an option" },
        { value: "Masculino", text: "Masculino" },
        { value: "Feminino", text: "Feminino" },
      ],
    };
  },
  props: {
    url: String,
  },
  computed: {
    username() {
      return this.$auth.user.sub;
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
    isPasswordValidFeedback() {
      if (!this.newPassword) {
        return null;
      }
      let passwordLen = this.newPassword.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "New Password is too short, lenght must be between 3 and 255";
      }
      return "";
    },
    isPasswordValid() {
      if (this.isPasswordValidFeedback === null) {
        return null;
      }
      return this.isPasswordValidFeedback === "";
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
    isContactValidFeedback() {
      if (!this.contact) {
        return null;
      }
      if (this.contact == this.currentContact) {
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
      if (!this.birthDate) {
        return null;
      }
      if (this.birthDate == this.currentBirthDate) {
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
      return "The date is bigger than todays date";
    },
    isbirthDateValid() {
      if (this.isbirthDateValidFeedback === null) {
        return null;
      }
      return this.isbirthDateValidFeedback === "";
    },
    isGenderValidFeedback() {
      if (!this.gender) {
        return null;
      }
      if(this.gender == this.currentGender){
        return "Gender is equal to current gender"
      }
      return "";
    },
    isGenderValid() {
      if (this.isGenderValidFeedback === null) {
        return null;
      }
      return this.isGenderValidFeedback === "";
    },
  },
  created() {
    this.getPatient();
  },
  methods: {
    getPatient() {
      this.$axios.$get(`/api/patients/${this.username}`).then((entidade) => {
        this.entidade = [entidade];
        this.currentName = entidade.name;
        this.currentBirthDate = entidade.birthDate;
        this.currentEmail = entidade.email;
        this.currentContact = entidade.contact;
        this.currentGender = entidade.gender;
      });
    },
    update() {
      let patient = {};
      if (this.isNameValid) {
        patient.name = this.name;
      }
      if (this.isEmailValid) {
        patient.email = this.email;
      }
      if (this.isContactValid) {
        patient.contact = this.contact;
      }
      if (this.isbirthDateValid) {
        patient.birthDate = this.birthDate;
      }
      if (this.isGenderValid) {
        patient.gender = this.gender;
      }
      if (Object.keys(patient).length == 0) {
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }

      this.$axios
        .$put(`/api/patients/${this.username}`, patient)
        .then(() => {
          this.name = null;
          this.email = null;
          this.contact = null;
          this.birthDate = null;
          this.gender = null;
          this.$toast.info(`Patient ${this.username}  updated!`).goAway(3000);
          this.getPatient();
        })
        .catch(() => {
          this.$toast.info(`Could not update patient`).goAway(3000);
          this.getPatient();
        });
    },
    updatePassword() {
      let patient = {};
      if (this.isOLDPasswordValid) {
        patient.passwordOld = this.oldPassword;
      }
      if (this.isPasswordValid) {
        patient.password = this.newPassword;
      }
      if (Object.keys(patient).length != 2) {
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }
      this.$axios
        .$put(`/api/patients/${this.username}/password`, patient)
        .then(() => {
          this.oldPassword = null;
          this.newPassword = null;
          this.$toast
            .info(`Patient ${this.username}  password updated!`)
            .goAway(3000);
          this.getPatient();
        })
        .catch((response) => {
          this.$toast.info(`Error - ${response.response.data}`).goAway(3000);
          this.getPatient();
        });
    },
  },
};
</script>
