<template>
  <div>
    <NavBar></NavBar>
    <h1 style="margin-top: 4%" class="d-flex justify-content-center"> HealthCare Professionals </h1>
    <div class="d-flex justify-content-center" style="margin-top: 2%">
      <template>
        <div>
          <b-table striped hover :items="healthCareProfessional" :fields="fields"></b-table>
        </div>
      </template>
    </div>
    <b-container class="bv-example-row">
      <b-row class="d-flex justify-content-center">
        <b-col sm="2">
          <div class="d-flex justify-content-center">
            <b-button v-b-modal.modal-1>Edit</b-button>
          </div>
        </b-col>
        <b-col sm="2">
          <div class="d-flex justify-content-center">
            <b-button v-if="!this.isAdmin" v-b-modal.modal-2 variant="primary">Change password</b-button>
          </div>
        </b-col>
      </b-row>
    </b-container>


    <b-modal id="modal-1" title="Edit" @ok="update">
      <div class="input-group mb-4">
        <span class="input-group-text">Name</span>
        <b-input required v-model.trim="name" type="text" :state="isNameValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your name"/>
        <p style="color: #dc3545;">{{isNameValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Birthdate</span>
        <b-input required  v-model.trim="birthDate" type="text" :state="isbirthDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1"/>
        <p style="color: #dc3545;">{{isbirthDateValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Type</span>
        <b-input required  v-model.trim="type" type="text" :state="isTypeValid"  placeholder="Enter the professional type" class="form-control" aria-describedby="basic-addon1"/>
        <p style="color: #dc3545;">{{isTypeValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Email</span>
        <b-input required v-model.trim="email" ref="email" type="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your email"/>
        <p style="color: #dc3545;">{{isEmailValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Contact</span>
        <b-input required v-model.trim="contact" type="number" :state="isContactValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your contact"/>
        <p style="color: #dc3545;">{{isContactValidFeedback}}</p>
      </div>
      <b-alert v-model="showDismissibleAlertEdit" variant="danger" dismissible>
        <p> {{alertData}}</p>
      </b-alert>
    </b-modal>

    <b-modal ref="updatePasswordModel" id="modal-2" class="justify-content-center" title="Edit Password" @ok="updatePassword">
      <div class="input-group mb-4">
        <span class="input-group-text">Password</span>
        <b-input required v-model.trim="passwordOld" type="password" :state="isPasswordOldValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your old password"/>
        <p style="color: #dc3545;">{{isPasswordOldValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">New Password</span>
        <b-input required  v-model.trim="passwordNew" type="password" :state="isPasswordNewValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your new password"/>
        <p style="color: #dc3545;">{{isPasswordNewValidFeedback}}</p>
      </div>
      <b-alert v-model="showDismissibleAlertPassword" variant="danger" dismissible>
        {{alertPasswordInvalid}}
      </b-alert>
    </b-modal>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields: [
        "healthNumber",
        "name",
        "birthDate",
        "type",
        "email",
        "contact",
      ],
      healthCareProfessional: [],
      state: true,
      healthNumber : null,
      name : null,
      birthDate : null,
      type : null,
      email : null,
      contact : null,
      passwordOld: null,
      passwordNew : null,
      showDismissibleAlertEdit: false,
      showDismissibleAlertPassword: false,
      alertData: '',
      alertPasswordInvalid: '',
    };
  },
  props: {
    url: String,
  },
  created() {
    this.getHealthCareProfessionalData()

  },
  computed: {
    isAdmin(){
      if(this.$auth.user.groups.includes("Administrator")){
        return true;
      }
      return false;
    },
    username() {
      return this.$route.params.username;
    },
    isNameValidFeedback (){
      if (!this.name) {
        return null
      }
      let nameLen = this.name.length
      if (nameLen < 3 || nameLen > 25) {
        return 'The name is too short - length must be between 3 and 25'
      }
      return ''
    },
    isNameValid () {
      if (this.isNameValidFeedback === null) {
        return null
      }
      return this.isNameValidFeedback === ''
    },
    isTypeValidFeedback (){
      if (!this.type) {
        return null
      }
      let typeLen = this.type.length
      if (typeLen > 25) {
        return 'The type is too short - length must be under 25'
      }
      return ''
    },
    isTypeValid () {
      if (this.isTypeValidFeedback === null) {
        return null
      }
      return this.isTypeValidFeedback === ''
    },
    isContactValidFeedback (){
      if (!this.contact) {
        return null
      }
      let contactString = this.contact.toString();
      let contactLen = contactString.length
      if (contactLen !== 9) {
        return 'The contact is invalid - contact must have 9 digits exactly'
      }
      const phoneRegex = /^(9[0-9])([0-9]{7})?$/;
      return phoneRegex.test(contactString) ? '':'Contact is invalid - Not in PT format';
    },
    isContactValid () {
      if (this.isContactValidFeedback === null) {
        return null
      }
      return this.isContactValidFeedback === ''
    },
    isPasswordOldValidFeedback () {
      this.showDismissibleAlertPassword = false;
      if (!this.passwordOld) {
        return null
      }
      let passwordLen = this.passwordOld.length
      if (passwordLen < 3 || passwordLen > 255) {
        return 'Password Old is too short, length must be between 3 and 255'
      }
      return ''
    },
    isPasswordOldValid () {
      if (this.isPasswordOldValidFeedback === null) {
        return null
      }
      return this.isPasswordOldValidFeedback === ''
    },
    isPasswordNewValidFeedback () {
      this.showDismissibleAlertPassword = false;
      if (!this.passwordNew) {
        return null
      }
      let passwordLen = this.passwordNew.length
      if (passwordLen < 3 || passwordLen > 255) {
        return 'Password New is too short, length must be between 3 and 255'
      }
      return ''
    },
    isPasswordNewValid () {
      if (this.isPasswordNewValidFeedback === null) {
        return null
      }
      return this.isPasswordNewValidFeedback === ''
    },

    isEmailValidFeedback () {
      if (!this.email) {
        return null
      }
      return this.$refs.email.checkValidity() ? '' : 'Email is not valid - the email format must be like name@domain'
    },
    isEmailValid () {
      if (this.isEmailValidFeedback === null) {
        return null
      }
      return this.isEmailValidFeedback === ''
    },
    isbirthDateValidFeedback () {
      if (!this.birthDate) {
        return null
      }
      var date_regex = /^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
      return date_regex.test(this.birthDate) ? '':'The birth date is invalid - format dd/mm/yyyy';
    },
    isbirthDateValid () {
       if (!this.birthDate) {
          return null
        }
       var date_regex = /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
       var currentdate = new Date();
       var dateSplitted = this.birthDate.split('/');
       var dateRegexValid = date_regex.test(this.birthDate);
       if(!dateRegexValid){
         return 'The date is invalid - format dd/mm/yyyy';
       }
       if(parseInt(dateSplitted[2]) < currentdate.getFullYear()){
          return '';
       }else if(parseInt(dateSplitted[2]) == currentdate.getFullYear() && parseInt(dateSplitted[1]) < (currentdate.getMonth()+1)){
          return '';
       }else if(parseInt(dateSplitted[2]) == currentdate.getFullYear() && parseInt(dateSplitted[1]) == (currentdate.getMonth()+1) && parseInt(dateSplitted[0]) <= currentdate.getDate()){
          return '';
       }
       return 'The date is bigger than todays date';
    },
    isFormValid () {
      this.showDismissibleAlertEdit = false;
      if (this.name == null && this.type == null && this.email == null && this.email == null && this.contact == null && this.birthDate == null) {
        this.alertData = 'You need to change some data to update'
        return false
      }
      this.alertData = 'Every Data must be correct'
      if (this.isNameValid === false) {
        return false
      }
      if (this.isbirthDateValid === false) {
        return false
      }
      if (this.isContactValid  === false) {
        return false
      }
      if (this.isEmailValid  === false) {
        return false
      }
      this.showDismissibleAlertEdit = true;
      return true;
    },
    isFormPasswordValid () {
      this.showDismissibleAlertPassword = false;
      if (this.passwordOld == null || this.passwordNew == null) {
        this.alertPasswordInvalid = 'You must fill all the fields to update password'
        return false
      }
      this.alertPasswordInvalid = 'Every Data must be correct'
      if (this.isPasswordOldValid === false) {
        return false
      }
      return true;
    }
  },
  methods: {
    getHealthCareProfessionalData(){
      this.$axios.$get(`/api/healthcareProfessionals/${this.username}`).then((entidade) => {
        this.healthCareProfessional = [entidade];
      });
    },
    update(bvModalEvt) {
      if (this.isFormValid){
        this.$axios
          .$put(`/api/healthcareProfessionals/${this.username}`, {
            name: this.name,
            type: this.type,
            email: this.email,
            contact: this.contact,
            birthDate: this.birthDate,
          })
          .then(() => {
            this.name = null;
            this.type = null;
            this.email = null;
            this.contact = null;
            this.birthDate = null;
            this.$toast.info("HealthCare Professional " + this.username + " updated succesfully").goAway(3000);

            this.getHealthCareProfessionalData()
          });
      }
      else{
        bvModalEvt.preventDefault()
        this.showDismissibleAlertEdit = true;
      }
    },
    updatePassword(bvModalEvt) {
      bvModalEvt.preventDefault()
      if (this.isFormPasswordValid){
        this.$axios
          .$put(`/api/healthcareProfessionals/${this.username}/password`, {
            passwordOld: this.passwordOld,
            password: this.passwordNew,
          })
          .then(() => {
            this.passwordOld = null;
            this.passwordNew = null;
            this.$toast.info(`HealthCare Professional (${this.username}) - password updated!`).goAway(3000);

            this.getHealthCareProfessionalData()
          })
          .catch(error => {
            this.alertPasswordInvalid = error.response.data
            this.showDismissibleAlertPassword = true;
          })
      }
      else{
        this.showDismissibleAlertPassword = true;
      }
    },
  },
};
</script>

