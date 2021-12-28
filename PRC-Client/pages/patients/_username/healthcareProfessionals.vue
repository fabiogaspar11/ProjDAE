
<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="5">
          <h3>Healthcare Professionals ({{ tableLength }})</h3>
        </b-col>
        <b-col v-if="this.noHealthcareProf == false" sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>

      </b-row>
    </b-container>
    <hr style="width: 73%" />
    <div v-if="this.noHealthcareProf == false" class="d-flex justify-content-center" style="margin-top: 3%">
      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >
      </b-table>
    </div>
    <div v-else class="w-75 mx-auto alert alert-info">No Healthcare Professionals assigned to you yet</div>

  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from "vue-router";

export default {
  components: {
    NavBar,
    Router,
  },
  data() {
    return {
      fields: [
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
        { key: "email", label: "Email", sortable: true, sortDirection: "desc" },
        { key: "contact", label: "Contact", sortable: true, sortDirection: "desc" },
      ],
      entidade: [],
      noHealthcareProf:false,
      filter: null,
      totalRows: null,
      currentPage: null
    };
  },
  computed: {
     username() {
      return this.$auth.user.sub;
    },
    tableLength: function () {
      return this.entidade.length;
    },

  },
    methods: {
     getHealthcareProfs(){

      this.$axios.$get("/api/patients/"+this.username+"/healthcareProfessionals").then((entidade) => {
        if(entidade.length == 0){
          this.noHealthcareProf = true;
          return;
        }
        this.entidade = entidade;
      });
      },
      search(filteredItems) {
        this.totalRows = filteredItems.length;
        this.currentPage = 1;
      },
    },
  created() {
      this.getHealthcareProfs();

  },
};
</script>



