<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h1>Patients ({{ tableLength }})</h1>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
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
          <b-input required v-model.trim="email" ref="email" type="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your email"/>
           <p>{{isEmailValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Password</span>
          <b-input required v-model.trim="password" type="password" :state="isPasswordValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your password"/>
          <p>{{isPasswordValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Birthdate</span>
          <b-input required  v-model.trim="birthDate" type="text" :state="isbirthDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1"/>
          <p>{{isbirthDateValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Contact</span>
          <b-input required v-model.trim="contact" type="number"  :state="isContactValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your contact"/>
          <p>{{isContactValidFeedback}}</p>
      </div>
       <div class="input-group mb-4">
          <span class="input-group-text">Health Number</span>
          <b-input required v-model.trim="healthNumber" type="number" :state="isHealthNumberValid" class="form-control" aria-describedby="basic-addon1" placeholder="Enter your health number"/>
          <p>{{isHealthNumberValidFeedback}}</p>
      </div>
    </b-modal>

   <hr style="width: 73%" />
    <div class="d-flex justify-content-center" style="margin-top: 3%">
      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >

        <template v-slot:cell(operations)="row">
          <b-button :to="`/patients/${row.item.username}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>

          <b-button variant="danger" @click="remove(row.item.username)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
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
    username() {
      return this.$auth.user.sub;
    },
     tableLength: function () {
      return this.entidade.length;
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
        {key:"name", label:"Name",  sortable: true, sortDirection: "desc" },
        {key:"birthDate", label:"Birthdate",  sortable: true, sortDirection: "desc" },
        {key:"healthNumber", label:"Health Number",  sortable: true, sortDirection: "desc" },
        "operations",
      ],
      entidade: [],
      name: null,
      birthDate: null,
      contact: null,
      email: null,
      password:null,
      healthNumber:null,
      filter: null,
      totalRows: null,
      currentPage: null,
    };
  },
  created() {
    this.getAllPatients();
  },
  methods:{
     search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    getAllPatients(){
        this.$axios.$get(`api/${username}/patients`).then((entidade) => {
          this.entidade = entidade;
        });
    },
    remove(username) {
      this.$axios.$delete(`/api/patients/${username}`).then(()=>{
       this.$toast.info('Patient '+this.username +' was successfully removed').goAway(3000);

       this.getAllPatients();
      })
    },
    createPatient() {
      if(!this.isFormValid){
           this.$toast.error("Fields are invalid - Correct them first!").goAway(3000);
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
          this.$toast.success("Patient "+ this.name + " created succesfully").goAway(3000);

          this.password = null;
          this.name = null;
          this.birthDate = null;
          this.contact = null;
          this.email = null;
          this.healthNumber = null;
          this.getAllPatients();
        })
        .catch(error => {
            this.$toast.error("Error when creating Patient: "+ error.response.data).goAway(3000);

        });
     }
  }
};
</script>
