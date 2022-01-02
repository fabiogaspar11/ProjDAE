
<template>
  <div>
    <NavBar></NavBar>
    <b-container>
      <h3 class="mt-3">Diseases ({{ tableLength }})</h3>
      <div class="mt-3" v-if="this.tableLength != 0">
        <b-form-input v-model="filter" type="search" placeholder="Search...">
        </b-form-input>
      </div>

      <div class="mt-5" v-if="this.noDiseases == false">
        <b-table
              id="table"
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
        aria-controls="table"
      ></b-pagination>
      </div>
      <div v-else class="w-75 mx-auto alert alert-info">
        No diseases diagnosed to you yet
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
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
      ],
      entidade: [],
      noDiseases: false,
      filter: null,
      totalRows: null,
      perPage: 6,
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
    getDiseases() {
      this.$axios
        .$get("/api/patients/" + this.username + "/diseases")
        .then((entidade) => {
          if (entidade.length == 0) {
            this.noDiseases = true;
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
    this.getDiseases();
  },
};
</script>



