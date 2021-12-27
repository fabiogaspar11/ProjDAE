<template>
  <div>
    <NavBarPatient></NavBarPatient>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="5">
          <h1>Prescriptions ({{ tableLength }})</h1>
        </b-col>
        <b-col v-if="this.noPrescriptions == false"  sm="6">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>
      </b-row>
    </b-container>

    <hr style="width: 73%" />
    <div  v-if="this.noPrescriptions == false" class="d-flex justify-content-center" style="margin-top: 3%">
      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >
         <template #cell(show_details)="row">
              <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                {{ row.detailsShowing ? "Hide" : "Show" }} Details
              </b-button>
            </template>

            <template #row-details="row">
              <b-card>
                <b-row class="mb-2">
                  <b-col sm="3" class="text-sm-right"><b>Treatment information:</b></b-col>
                  <b-col>{{ row.item.treatmentInfo }}</b-col>
                </b-row>
                <b-row class="mb-2">
                  <b-col sm="3" class="text-sm-right"><b>Observations:</b></b-col>
                  <b-col>{{ row.item.observations }}</b-col>
                </b-row>
              </b-card>
            </template>
      </b-table>
    </div>
    <div v-else class="w-75 mx-auto alert alert-info">No prescriptions prescribed to your yet</div>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";

export default {
  components: {
    NavBar,
  },
  data() {
    return {
      fields: [
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        { key: "title", label: "Title", sortable: true, sortDirection: "desc" },
        {
          key: "emissionDate",
          label: "Emission Date",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "usernamePatient", label: "Patient" },
        {
          key: "usernameHealthcareProfessional",
          label: "Healthcare professional",
        },
        {key: "show_details",label:"Show Details"}
      ],
      entidade: [],
      noPrescriptions:false,
      code: null,
      filter: null,
      totalRows: null,
      currentPage: null,
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    username() {
      return this.$auth.user.sub;
    },
  },
  methods: {
    getData() {
      this.$axios.$get(`/api/patients/${this.username}/prescriptions`).then((entidade) => {
        if(entidade.length == 0){
          this.noPrescriptions = true;
          return;
        }
        this.entidade = entidade;
      })
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>
