
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
    <b-modal id="modal-1" title="New disease" @ok="create(username)">
      <div class="input-group mb-4">
        <span class="input-group-text">Health Number</span>
        <input v-model="healthNumber" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">BirthDate</span>
        <input v-model="birthDate" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Type</span>
        <input v-model="type" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
        <input v-model="name" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Email</span>
        <input v-model="email" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Contact</span>
        <input v-model="contact" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Password</span>
        <input v-model="password" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
    </b-modal>

    <hr style="width:73%;">
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
        <template v-slot:cell(actions)="row">
          <b-button :to="`/healthcareProfessionals/${row.item.username}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>
          <b-button @click.prevent="remove(row.item.username)" variant="danger">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>

    </div>
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
    };
  },
  computed: {
    tableLength: function() {
      return this.entidade.length;
    }
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
    create(username) {
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
          this.healthNumber = null,
          this.birthDate = null;
          this.name = null;
          this.type = null;
          this.email = null;
          this.contact = null;
          this.password = null;
          this.getHealthCareProfessionalData()
        });

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



