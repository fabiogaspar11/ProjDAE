<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row-flex-cols" style="margin-top: 5%">
      <b-row class="text-center">
        <b-col>
          <h1> Patients </h1>
        </b-col>
        <b-col>
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus"/> New Patient
          </b-button>
        </b-col>
      </b-row>
    </b-container>
  <b-modal id="modal-1" title="New Patient" @ok.prevent="createPatient()">
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input required v-model.trim="name" type="text" :state="isNameValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your name"/>
          <p>{{isNameValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Email</span>
          <b-input required v-model.trim="email" ref="email" type="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1"/>
           <p>{{isEmailValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Password</span>
          <b-input required v-model.trim="password" type="password" :state="isPasswordValid"  class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isPasswordValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Birthdate</span>
          <b-input required  v-model.trim="birthDate" type="text" :state="isbirthDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isbirthDateValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Contact</span>
          <b-input required v-model.trim="contact" type="number"  :state="isContactValid"  class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isContactValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Health Number</span>
          <b-input required v-model.trim="healthNumber" type="number" :state="isHealthNumberValid" class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isHealthNumberValidFeedback}}</p>
      </div>
    </b-modal>

    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <b-table
        :items="entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
      >
        <template #cell(show_details)="row">
          <b-button size="sm" @click="row.toggleDetails" class="mr-2">
            {{ row.detailsShowing ? "Hide" : "Show" }} Details
          </b-button>
        </template>
        <template #row-details="row">
          <b-card>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Email:</b></b-col>
              <b-col>{{ row.item.email }}</b-col>
            </b-row>

            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Contact:</b></b-col>
              <b-col>{{ row.item.contact }}</b-col>
            </b-row>
          </b-card>
        </template>
        <template v-slot:cell(operations)="row">
          <nuxt-link class="btn btn-link" :to="`/patients/${row.item.username}`"
            >Details</nuxt-link
          >
        </template>
      </b-table>
    </div>
  </div>
</template>
<script>
import NavBar from "/components/NavBar.vue";
export default {
  components: {
    NavBar,
  },
  computed:{
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
    isHealthNumberValidFeedback (){
        if (!this.healthNumber) {
          return null
        }
        let healthNumberString = this.healthNumber.toString();
        let healthNumberLen = healthNumberString.length
        if (healthNumberLen != 9) {
           return 'The healh number is invalid - the number must have 9 digits'
        }
        return ''
    },
    isHealthNumberValid () {
        if (this.isHealthNumberValidFeedback === null) {
           return null
        }
        return this.isHealthNumberValidFeedback === ''
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
    ,
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
    if (!this.isHealthNumberValid) {
      return false
    }
      return true
    }
  },
  data() {
    return {
      fields: [
        "name",
        "birthDate",
        "healthNumber",
        "show_details",
        "operations",
      ],
      entidade: [],
      modalShow: false,
      name: null,
      birthDate: null,
      contact: null,
      email: null,
      password:null,
      healthNumber:null
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods:{
    createPatient() {
      if(!this.isFormValid){
          alert("Fields are invalid - Correct them first!");
          return;
      }
      this.$axios.$post("/api/patients", {
       password: this.password,
       email: this.email,
       birthDate: this.birthDate,
       name: this.name,
       contact: this.contact,
       healthNumber: this.healthNumber
      })
        .then(response => {
          alert("Patient "+ this.name + " created succesfully");
          this.password = null;
          this.name = null;
          this.birthDate = null;
          this.contact = null;
          this.email = null;
          this.healthNumber = null;
        })
        .catch(error => {
            alert("Error when creating Patient: "+ error.response.data);
        });
        }
  }
};
</script>
