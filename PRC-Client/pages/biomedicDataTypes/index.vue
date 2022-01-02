
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="mt-3">
      <h3>Biomedic Data Type ({{ tableLength }})</h3>

      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>
      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New Type
        </b-button>
      </div>
      <b-modal id="modal-1" title="New disease" @ok="create(code)">
        <div class="input-group mb-4">
          <span class="input-group-text">Name:</span>
          <b-input
            v-model="name"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isNameValid"
            placeholder="Enter name"
          />
          <p>{{ isNameValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Unit Measure:</span>
          <b-input
            v-model="unitMeasure"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isUnitValid"
            placeholder="Enter unit"
          />
          <p>{{ isUnitValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Minimum value:</span>
          <b-input
            v-model="minValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isMinValueValid"
            placeholder="Enter minimum value"
          />
          <p>{{ isMinValueValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Maximum value:</span>
          <b-input
            v-model="maxValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isMaxValueValid"
            placeholder="Enter maximum value"
          />
          <p>{{ isMaxValueValidFeedback }}</p>
        </div>
      </b-modal>

      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        :filter="filter"
        @filtered="search"
        id="table"
        :current-page="currentPagePaginate"
        :per-page="perPage"
      >
        <template v-slot:cell(operations)="row">
          <b-button :to="`/biomedicDataTypes/${row.item.code}`" variant="info">
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
        v-model="currentPagePaginate"
        :total-rows="tableLength"
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
        {
          key: "unitMeasure",
          label: "Unit Measure",
          sortable: true,
          sortDirection: "desc",
        },
        "operations",
      ],
      entidade: [],
      modalShow: false,
      name: null,
      unitMeasure: null,
      maxValue: null,
      minValue: null,
      filter: null,
      totalRows: null,
      perPage: 6,
      currentPagePaginate: 1,
    };
  },
  computed: {
    tableLength: function () {
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
    isUnitValidFeedback() {
      if (!this.unitMeasure) {
        return null;
      }
      let unitMeasureLen = this.unitMeasure.length;
      if (unitMeasureLen < 1 || unitMeasureLen > 25) {
        return "The unit is too short - length must be between 1 and 25";
      }
      return "";
    },
    isUnitValid() {
      if (this.isUnitValidFeedback === null) {
        return null;
      }
      return this.isUnitValidFeedback === "";
    },
    isMinValueValidFeedback() {
      if (!this.minValue) {
        return null;
      }
      let minValueLen = this.minValue.length;
      if (minValueLen <= 0 || minValueLen > 25) {
        return "The minimum value is mandatory or is too big in size";
      }
      return "";
    },
    isMinValueValid() {
      if (this.isMinValueValidFeedback === null) {
        return null;
      }
      return this.isMinValueValidFeedback === "";
    },
    isMaxValueValidFeedback() {
      if (!this.maxValue) {
        return null;
      }
      let maxValueLen = this.maxValue.length;
      if (maxValueLen <= 0 || maxValueLen > 25) {
        return "The maximum value is mandatory or is too big in size";
      }
      return "";
    },
    isMaxValueValid() {
      if (this.isMaxValueValidFeedback === null) {
        return null;
      }
      return this.isMaxValueValidFeedback === "";
    },
    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }
      if (!this.isUnitValid) {
        return false;
      }
      if (!this.isMinValueValid) {
        return false;
      }
      if (!this.isMaxValueValid) {
        return false;
      }
      return true;
    },
  },
  created() {
    this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
    getCode(code) {
      this.code = code;
    },
    create() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }
      this.$axios
        .$post("/api/biomedicDataTypes", {
          name: this.name,
          unitMeasure: this.unitMeasure,
          minValue: this.minValue,
          maxValue: this.maxValue,
        })
        .then((response) => {
          this.$toast
            .success(
              "Biomedic data type " + this.name + " created succesfully!"
            )
            .goAway(3000);

          this.entidade.push(response);
          this.code = null;
          this.name = null;
          this.unitMeasure = null;
          this.minValue = null;
          this.maxValue = null;
        });
    },
    remove(code) {
      this.$axios.$delete(`/api/biomedicDataTypes/${code}`).then(() => {
        this.$toast
          .info("Biomedic data type " + this.name + " deleted with success!")
          .goAway(3000);

        this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
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



