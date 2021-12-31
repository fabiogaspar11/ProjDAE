<template>
  <div>
    <NavBar></NavBar>
    <b-container>
        <h3 class="mt-3">Prescriptions ({{ tableLength }})</h3>
        <div class="mt-3" v-if="this.tableLength == 0">
              <div class="mx-auto alert alert-info">No prescriptions prescribed to you yet</div>
        </div>
    <div class="" v-else>
      <b-form-input v-model="filter" type="search" placeholder="Search..."></b-form-input>
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
      perPage: 6,
      currentPagePaginatePrincipal: 1,
    };
  },
  created() {
    this.getData();
  },
  computed: {
    rows() {
      return this.entidade.length;
    },
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
