
<template>
  <div>
    <NavBar></NavBar>
    <b-container>
        <h3 class="mt-3">Healthcare Professionals ({{ tableLength }})</h3>
        <div class="mt-3" v-if="this.tableLength == 0">
              <div class="mx-auto alert alert-info">No Healthcare Professionals assigned to you yet</div>
        </div>

    <div v-else class="mt-3">
         <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
      <b-table
        class="mt-5"
        id="tablePrincipal"
        :per-page="perPage"
        :current-page="currentPagePaginatePrincipal"
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        :filter="filter"
        @filtered="search"
      >
      </b-table>
        <b-pagination
        class="fixed-bottom justify-content-center"
        v-model="currentPagePaginatePrincipal"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="tablePrincipal"
      ></b-pagination>
    </div>
    </b-container>
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
      perPage: 8,
      currentPagePaginatePrincipal: 1,
    };
  },
  computed: {
    rows() {
      return this.entidade.length;
    },
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



