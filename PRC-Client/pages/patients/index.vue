<template>
  <div>
    <NavBar></NavBar>

    <b-container class="mt-3">
          <h3>Patients ({{ tableLength }})</h3>
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
    <div v-if="this.tableLength != 0" class="mt-5">
      <b-table
        small
        id="table"
        :per-page="perPage"
        :current-page="currentPagePaginate"
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        :filter="filter"
        @filtered="search"
      >
        <template v-slot:cell(operations)="row">
            <b-button variant="danger" @click="remove('P'+row.item.healthNumber)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
    </div>
      <div v-else class="w-75 mx-auto alert alert-info">
      No Patients created yet
    </div>
    </b-container>
      <b-pagination
          class="fixed-bottom justify-content-center"
          v-model="currentPagePaginate"
          :total-rows="tableLength"
          :per-page="perPage"
          aria-controls="table"
        ></b-pagination>
  </div>
</template>
<script>
import NavBar from "/components/NavBar.vue";
export default {
  middleware: "isAdministrator",
  components: {
    NavBar,
  },
  computed:{
     tableLength: function () {
      return this.entidade.length;
    },

  },
  data() {
    return {
      fields: [
        {key:"name", label:"Name",  sortable: true, sortDirection: "desc" },
        {key:"healthNumber", label:"Health Number",  sortable: true, sortDirection: "desc" },
        "operations",
      ],
      entidade: [],
      filter: null,
      totalRows: null,
      currentPage: null,
      perPage: 5,
      currentPagePaginate: 1,
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
        this.$axios.$get("/api/patients").then((entidade) => {
          this.entidade = entidade;
        });
    },
    remove(username) {
      this.$axios.$delete(`/api/patients/${username}`).then(()=>{
       this.$toast.info('Patient '+username +' was successfully removed').goAway(3000);
       this.getAllPatients();
      })
    .catch((error) => {
        this.$toast.info("Error: " + error.response.data).goAway(3000);
      });
    },

  }
};
</script>
