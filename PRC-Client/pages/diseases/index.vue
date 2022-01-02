
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="mt-3">
      <h3>Diseases ({{ tableLength }})</h3>
      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>

      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New disease
        </b-button>
      </div>

      <b-modal id="modal-1" title="New disease" @ok="create()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input
            v-model.trim="name"
            type="text"
            :state="isNameValid"
            placeholder="Enter name"
            class="form-control"
            required
            aria-describedby="basic-addon1"
          />
          <p>{{ isNameValidFeedback }}</p>
        </div>
      </b-modal>

      <b-table
        class="mt-3"
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        :filter="filter"
        @filtered="search"
        id="table"
        :per-page="perPage"
        :current-page="currentPagePaginatePrincipal"
      >
        <template v-slot:cell(operations)="row">
          <b-button :to="`/diseases/${row.item.code}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>
          <b-button
            v-b-modal.modal-3
            variant="danger"
            @click="remove(row.item.code)"
          >
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
      <b-pagination
        class="fixed-bottom justify-content-center"
        v-model="currentPagePaginatePrincipal"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="table"
      ></b-pagination>
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
        "Operations",
      ],
      entidade: [],
      modalShow: false,
      code: null,
      name: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      perPage: 6,
      currentPagePaginatePrincipal: 1,
    };
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    rows() {
      return this.entidade.length;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      let nameLen = this.name.length;
      if (nameLen < 3 || nameLen > 25) {
        return "The name is too short - length must be between 3 and 25";
      }
      return "";
    },
    isNameValid() {
      if (this.isNameValidFeedback === null) {
        return null;
      }
      return this.isNameValidFeedback === "";
    },
    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }
      return true;
    },
  },
  created() {
    this.$axios.$get("/api/diseases").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
    create() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }

      this.$axios
        .$post("/api/diseases", {
          name: this.name,
        })
        .then((response) => {
          this.$toast
            .success("Disease " + this.name + " created succesfully")
            .goAway(3000);
          this.entidade.push(response);
          this.name = null;
        })
        .catch((error) => {
          this.$toast
            .error("Error when creating Disease: " + error.response.data)
            .goAway(3000);
        });
    },
    remove(code) {
      this.$axios.$delete("/api/diseases/" + code).then(() => {
        const index = this.entidade.findIndex(
          (disease) => disease.code === code
        ); // find the post index
        console.log(index);
        if (index >= 0)
          // if the post exists in array
          this.entidade.splice(index, 1); //delete the post
      });
    },
    update(code) {
      this.$axios
        .$put("/api/diseases/" + code, {
          name: this.name || "",
        })
        .then((response) => {
          const index = this.entidade.findIndex(
            (disease) => disease.code === code
          ); // find the post index
          if (index) {
            // if the post exists in array
            this.entidade.splice(index, 1);
            this.entidade.splice(index, 0, response);
          }
          this.name = null;
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>



