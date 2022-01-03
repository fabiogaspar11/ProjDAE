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
      </b-row>
    </b-container>

   <hr style="width: 73%" />
    <div v-if="this.tableLength != 0" class="d-flex justify-content-center" style="margin-top: 3%">
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
            <b-button variant="danger" @click="remove('P'+row.item.healthNumber)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
    </div>
      <div v-else class="w-75 mx-auto alert alert-info">
      No Patients created yet
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
