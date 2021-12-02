
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h3>Biomedic Data Type ({{ tableLength }})</h3>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>

        <b-col lg="4">
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus" /> New Type
          </b-button>
        </b-col>
      </b-row>
    </b-container>

    <b-modal id="modal-1" title="New disease" @ok="create(code)">
      <div class="input-group mb-4">
        <span class="input-group-text">Code:</span>
        <input
          v-model="code"
          type="number"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Name:</span>
        <input
          v-model="name"
          type="text"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Unit Measure:</span>
        <input
          v-model="unitMeasure"
          type="text"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Minimum value:</span>
        <input
          v-model="maxValue"
          type="number"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Maximum value:</span>
        <input
          v-model="minValue"
          type="number"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
    </b-modal>

    <hr style="width: 73%" />
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

        <template v-slot:cell(operations)="row">
          <b-button :to="`/biomedicData/${row.item.code}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>

          <b-button v-b-modal.modal-3 variant="danger" @click="remove(row.item.code)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
    </div>
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
        {
          key: "unitMeasure",
          label: "Unit Meausere",
          sortable: true,
          sortDirection: "desc",
        },
        "operations",
      ],
      entidade: [],
      modalShow: false,
      code: null,
      name: null,
      unitMeasure: null,
      maxValue: null,
      minValue: null,
      filter: null,
      totalRows: null,
      currentPage: null,
    };
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
  },
  created() {
    this.$axios.$get("/api/biomedicDataType").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
    getCode(code) {
      this.code = code;
    },
    create(code) {
      this.$axios
        .$post("/api/biomedicDataType", {
          code: this.code,
          name: this.name,
          unitMeasure: this.unitMeasure,
          minValue: this.minValue,
          maxValue: this.maxValue,
        })
        .then((response) => {
          this.entidade.push(response);
          this.code = null;
          this.name = null;
          this.unitMeasure = null;
          this.minValue = null;
          this.maxValue = null;
        });
    },
    remove(code) {
      this.$axios.$delete(`/api/biomedicDataType/${code}`).then(() => {
        this.$axios.$get("/api/biomedicDataType").then((entidade) => {
          this.entidade = entidade;
        });
      });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>



