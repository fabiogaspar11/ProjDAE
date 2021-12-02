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
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input required v-model.trim="name" type="text" :state="isNameValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your name"/>
          <p>{{isNameValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Birthdate</span>
          <b-input required  v-model.trim="birthDate" type="text" :state="isbirthDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isbirthDateValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Email</span>
          <b-input required v-model.trim="email" ref="email" type="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your email"/>
           <p>{{isEmailValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Password</span>
          <b-input required v-model.trim="password" type="password" :state="isPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your password"/>
          <p>{{isPasswordValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Contact</span>
          <b-input required v-model.trim="contact" type="number"  :state="isContactValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your contact"/>
          <p>{{isContactValidFeedback}}</p>
      </div>
      </b-modal>
    </div>
    </b-col>
    <!-----------------------------------------------Delete----------------------------------------->

    <b-col col lg="2">
    <div>
      <b-button variant="danger" v-b-modal.modal-2>Delete</b-button>
      <b-modal id="modal-2" title="Please Confirm" @ok="remove()">
      "Are you sure you want to delete this user?
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
      name : null,
      email : null,
      password : null,
      birthDate : null,
      contact : null
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
    },
      isFormValid () {
    if (!this.isNameValid) {
      return false
    }
    if (!this.isEmailValid) {
      return false
    }
    if (!this.isPasswordValid) {
      return false
    }
    if (!this.isbirthDateValid) {
      return false
    }
    if (!this.isContactValid) {
      return false
    }
      return true
    }
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((entidade) => {
      this.entidade = [entidade];
    });
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/patients/${this.username}`).then(()=>{
      alert('Patient '+this.username +' was successfully removed');
      this.$router.go(-1);
      })
    },
    update() {
      this.$axios
        .$put(`/api/patients/${this.username}`, {
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
           alert(`Patient ${this.username}  updated!`);
          this.$axios
            .$get(`/api/patients/${this.username}`)
            .then((entidade) => {
              this.entidade = [entidade];
            });
            //OU
            this.$router.go(0);
        });
    },
  },
};
</script>
