
<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h4> Health Care Professionals ({{tableLength}})</h4>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search..."> </b-form-input>
        </b-col>

        <b-col lg="4">
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus"/> New professional
          </b-button>
        </b-col>
      </b-row>
    </b-container>
    <b-modal id="modal-1" title="New Healthcare Professional" @ok="create">
      <div class="input-group mb-4">
        <span class="input-group-text">Health Number</span>
        <b-input required v-model.trim="healthNumber" type="number" :state="isHealthNumberValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isHealthNumberValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">BirthDate</span>
        <b-input required v-model.trim="birthDate" type="text" :state="isbirthDateValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isbirthDateValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Type</span>
        <b-input required v-model.trim="type" type="text" :state="isTypeValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isTypeValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
        <b-input required v-model.trim="name" type="text" :state="isNameValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isNameValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Email</span>
        <b-input required v-model.trim="email" type="email"  ref="email" :state="isEmailValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isEmailValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Contact</span>
        <b-input required v-model.trim="contact" type="number" :state="isContactValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isContactValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Password</span>
        <b-input required v-model.trim="password" type="password" :state="isPasswordValid" class="form-control" aria-describedby="basic-addon1"/>
        <p>{{isPasswordValidFeedback}}</p>
      </div>
      <b-alert v-model="showDismissibleAlert" variant="danger" dismissible>
        {{alertData}}
      </b-alert>
    </b-modal>

    <hr style="width:73%;">
      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >
        <template v-slot:cell(actions)="row">
          <b-button :to="`/healthcareProfessionals/${row.item.username}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>
          <b-button @click.prevent="remove(row.item.username)" variant="danger">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>

    <b-button v-b-modal.modal-3 variant="success" @click="">
      <font-awesome-icon icon="trash" /> Export to excel
    </b-button>
  </div>

</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from 'vue-router';

export default {
  components: {
    NavBar,
    Router
  },
  data() {
    return {
      fields: [
        { key: 'healthNumber', label: 'Health Nº', sortable: true, sortDirection: 'desc' },
        { key: 'name', label: 'Name', sortable: true, sortDirection: 'desc' },
        { key: 'birthDate', label: 'Birthdate', sortable: true, sortDirection: 'desc' },
        { key: 'type', label: 'Type', sortable: true, sortDirection: 'desc' },
        "actions"
      ],
      entidade: [],
      modalShow: false,
      healthNumber: null,
      username: null,
      birthDate: null,
      email: null,
      name: null,
      type: null,
      password: null,
      contact: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      alertData: '',
      showDismissibleAlert: false
    };
  },
  computed: {
    tableLength: function() {
      return this.entidade.length;
    },
    isHealthNumberValidFeedback (){
      if (!this.healthNumber) {
        return null
      }
      let healthNumberString = this.healthNumber.toString();
      let healthNumberLen = healthNumberString.length
      if (healthNumberLen !== 9) {
        return 'The health number is invalid - the number must have 9 digits'
      }
      return ''
    },
    isHealthNumberValid () {
      if (this.isHealthNumberValidFeedback === null) {
        return null
      }
      return this.isHealthNumberValidFeedback === ''
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
    isPasswordValidFeedback () {
      if (!this.password) {
        return null
      }
      let passwordLen = this.password.length
      if (passwordLen < 3 || passwordLen > 255) {
        return 'Password is too short, length must be between 3 and 255'
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
    },
    isFormValid () {
      this.showDismissibleAlert = false;
      if (this.healthNumber == null || this.name == null || this.type == null || this.email == null || this.contact == null || this.birthDate == null || this.password == null) {
        this.alertData = 'Every fill must be filled'
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
      if (this.isPasswordValid === false) {
        return false
      }
      this.showDismissibleAlert = true;
      return true;
    },
  },
  created() {
    this.getHealthCareProfessionalData()
  },
  methods: {
    getUsername(username){
      this.username = username
    },
    getHealthCareProfessionalData(){
      this.$axios.$get("/api/healthcareProfessionals").then((entidade) => {
        this.entidade = entidade;
      });
    },
    create(bvModalEvt) {
      if (this.isFormValid) {
        this.$axios.$post("/api/healthcareProfessionals", {
          healthNumber: this.healthNumber,
          birthDate: this.birthDate,
          name: this.name,
          type: this.type,
          email: this.email,
          contact: this.contact,
          password: this.password
        })
          .then(response => {
            this.$toast.success("Healthcare professional "+ this.name + " created succesfully").goAway(3000);

            this.healthNumber = null;
            this.birthDate = null;
            this.name = null;
            this.type = null;
            this.email = null;
            this.contact = null;
            this.password = null;
            this.getHealthCareProfessionalData()
          });
      }
      else{
        bvModalEvt.preventDefault()
        this.showDismissibleAlert = true;
      }

    },
    remove(username) {
      this.$axios.$delete('/api/healthcareProfessionals/' + username)
        .then(() => {
          this.getHealthCareProfessionalData()
        });
    },
    update(username) {
      this.$axios.$put('/api/healthcareProfessionals/' + username, {
        name: this.name,
        type: this.type,
      })
        .then(response => {
          this.username = null;
          this.name = null;
          this.type = null;
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length
      this.currentPage = 1
    }
  }
};
</script>



