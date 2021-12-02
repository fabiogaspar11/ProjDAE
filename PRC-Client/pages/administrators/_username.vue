<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
    </div>
      <b-row class="justify-content-md-center">
            <b-col col lg="2" >
    <div>
      <b-button v-b-modal.modal-1>Edit</b-button>

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
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Password</span>
          </div>
          <b-input
            v-model.trim="password"
            type="password"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your password"
            :state="isPasswordValid"
          />
        </div>
      </b-modal>
    </div>
    </b-col>
  </b-row>
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
        "email"
      ],
      entidade: [],
      state: true,
      name:null,
      birthDate:null,
      email:null,
      contact:null,
      password:null
    };
  },
  props: {
    url: String,
  },
  computed: {
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
    isContactValidFeedback (){
        if (!this.contact) {
          return null
        }
        let contactString = this.contact.toString();
        let contactLen = contactString.length
        if (contactLen != 9) {
           return 'The contact is invalid - contact must have 9 digits exactly'
        }
        var phoneRegex = /^(9[0-9])([0-9]{7})?$/
        return phoneRegex.test(contactString) ? '':'Contact is invalid - Not in PT format';
    },
    isContactValid () {
        if (this.isContactValidFeedback === null) {
           return null
        }
        return this.isContactValidFeedback === ''
    },

    isPasswordValidFeedback () {
        if (!this.password) {
           return null
        }
        let passwordLen = this.password.length
        if (passwordLen < 3 || passwordLen > 255) {
          return 'Password is too short, lenght must be between 3 and 255'
        }
        return ''
    },
    isPasswordValid () {
        if (this.isPasswordValidFeedback === null) {
           return null
        }
        return this.isPasswordValidFeedback === ''
    },
    isEmailValidFeedback () {
        if (!this.email) {
          return null
        }
        return this.$refs.email.checkValidity() ? '':'Email is not valid - the email format must be like name@domain'
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
        if (this.isbirthDateValidFeedback === null) {
          return null
        }
        return this.isbirthDateValidFeedback === ''
    }
  },
  created() {
    this.$axios.$get(`/api/administrators/${this.username}`).then((entidade) => {
      this.entidade = [entidade];
    });
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/administrators/${this.username}`).then(()=>{
      this.$router.go(-1);
      })
    },
    update() {

      let adminUpdated = {};
      if(this.name != null){
          adminUpdated.name = this.name;
      }
      if(this.email != null){
          adminUpdated.email = this.email;
      }
       if(this.password != null){
          adminUpdated.password = this.password;
      }
      if(this.contact != null){
          adminUpdated.contact = this.contact;
      }
      if(this.birthDate != null){
          adminUpdated.birthDate = this.birthDate;
      }


      this.$axios
        .$put(`/api/administrators/${this.username}`, adminUpdated)
        .then(() => {
           alert(`Administrator ${this.username}  updated!`);
          this.$router.go(0);
        })
        .catch((error) => {
         console.log(error.response.data)
        });
    },
  },
};
</script>
