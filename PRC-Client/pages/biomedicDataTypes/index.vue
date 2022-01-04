
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
      <b-modal id="modal-1" title="New Biomedic Data Type" @ok="create(code)">
        <div class="mx-auto alert alert-info">
          The normal values ​​should be created based on the normal values ​​for
          adult patients males
        </div>
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
        </div>
        <p>{{ isNameValidFeedback }}</p>
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
        </div>
        <p>{{ isUnitValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Minimum value:</span>
          <b-input
            v-model.number="minValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isMinValueValid"
            placeholder="Enter minimum value"
          />
        </div>
        <p>{{ isMinValueValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Maximum value:</span>
          <b-input
            v-model.number="maxValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isMaxValueValid"
            placeholder="Enter maximum value"
          />
        </div>
          <p>{{ isMaxValueValidFeedback }}</p>
        <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Minimum Normal value:</span>
           <b-input v-model.number="normalMinValue"  :disabled="!isMinValueValid || !isMaxValueValid"  :state="this.isNormalMinValid" class="col-md-2">{{this.normalMinValue}}</b-input>
           <b-input-group
            :prepend="(this.minValue+1).toString()"
            :append="(this.maxValue-1).toString()"
            class="mt-3"
          >
          <b-input
            v-model.number="normalMinValue"
            type="range"
            step="0.5"
            :min="minValue+1"
            :max="maxValue-1"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter minimum value"
          />
           </b-input-group>
        </div>
          <p>{{ isNormalMinValidFeedback }}</p>
            <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Maximum Normal value:</span>
          <b-input v-model.number="normalMaxValue"  :disabled="!isMinValueValid || !isMaxValueValid"  :state="this.isNormalMaxValid" class="col-md-2">{{this.normalMaxValue}}</b-input>
           <b-input-group
            :prepend="(this.minValue+1).toString()"
            :append="(this.maxValue-1).toString()"
            class="mt-3"
          >
            <b-input
              :disabled="!isMinValueValid || !isMaxValueValid"
              v-model.number="normalMinValue"
              type="range"
              step="0.5"
              :min="minValue + 1"
              :max="maxValue - 1"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter minimum value"
            />
          </b-input-group>
        </div>
        <p>{{ isNormalMinValidFeedback }}</p>
        <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Maximum Normal value:</span>
          <b-input
            v-model.number="normalMaxValue"
            :disabled="!isMinValueValid || !isMaxValueValid"
            :state="this.isNormalMaxValid"
            class="col-md-2"
            >{{ this.normalMaxValue }}</b-input
          >
          <b-input-group
            :prepend="this.minValue + 1"
            :append="this.maxValue - 1"
            class="mt-3"
          >
            <b-input
              :disabled="!isMinValueValid || !isMaxValueValid"
              v-model.number="normalMaxValue"
              type="range"
              step="0.5"
              :min="minValue + 1"
              :max="maxValue - 1"
              class="form-control"
              aria-describedby="basic-addon1"
              placeholder="Enter maximum value"
            />
          </b-input-group>
        </div>
          <p>{{ isNormalMaxValidFeedback }}</p>
          <div class="input-group mb-4">
          <span class="input-group-text">Gender value differentiation:</span>
          <div class="mx-auto mt-2 alert alert-info">
            This value indicates how much the normal range of values will change
            depending if the gender of the patient is female.
          </div>
          <b-input
            v-model.number="genderValuedifferentiation"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isGenderValuedifferentiationValid"
            placeholder="Enter Gender Value Difference"
          />
        </div>
        <p>{{ isGenderValuedifferentiationValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Age value differentiation:</span>
          <div class="mx-auto mt-2 alert alert-info">
            This value indicates how much the normal range of values will change
            depending on the age group of the patient.
          </div>
          <b-input
            v-model.number="ageValuedifferentiation"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isAgeValuedifferentiationValid"
            placeholder="Enter Age Value Difference"
          />
        </div>
        <p>{{ isAgeValuedifferentiationValidFeedback }}</p>
      </b-modal>

      <div v-if="this.tableLength != 0" class="mt-3">
        <b-table
          small
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
            <b-button
              :to="`/biomedicDataTypes/${row.item.code}`"
              variant="info"
            >
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
      </div>
      <div v-else class="w-75 mx-auto alert alert-info">
        No Biomedic Data Types created yet
      </div>
    </b-container>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from "vue-router";

export default {
  middleware: "isAdministrator",
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
      perPage: 5,
      currentPagePaginate: 1,
      normalMinValue: null,
      normalMaxValue: null,
      ageValuedifferentiation: null,
      genderValuedifferentiation: null,
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
      if (this.maxValue != null && this.minValue >= this.maxValue) {
        return "Minimum value should be smaller than the maximum";
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
      if (this.minValue != null && this.maxValue <= this.minValue) {
        return "Maximum value should be bigger than the minimum";
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
    isNormalMinValid() {
      if (this.isNormalMinValidFeedback === null) {
        return null;
      }
      return this.isNormalMinValidFeedback === "";
    },
    isNormalMinValidFeedback() {
      if (!this.normalMinValue) {
        return null;
      }

      if(this.normalMinValue < this.minValue)
        return "The normal minimum value should be bigger than the minimum value"
      if(this.normalMinValue >= this.normalMaxValue){
        return "The normal minimum value should be smaller than the normal maximum value"
      }

      if(this.normalMinValue > this.maxValue) {
        return "The normal minimum value should be smaller than the maximum value"
      }
      return ""
    },
    isNormalMaxValid() {
      if (this.isNormalMaxValidFeedback === null) {
        return null;
      }
      return this.isNormalMaxValidFeedback === "";
    },
    isNormalMaxValidFeedback() {
      if (!this.normalMaxValue) {
        return null;
      }
      if(this.normalMaxValue < this.minValue)
        return "The normal maximum value should be bigger than the minimum value"

      if(this.normalMaxValue > this.maxValue) {
        return "The normal maximum value should be smaller than the maximum value"
      }
      if (this.normalMaxValue <= this.normalMinValue) {
        return "The normal maximum value should be bigger than the minimum value";
      }
      return "";
    },
    isGenderValuedifferentiationValid() {
      if (this.isGenderValuedifferentiationValidFeedback == null) return null;
      return this.isGenderValuedifferentiationValidFeedback === "";
    },
    isGenderValuedifferentiationValidFeedback() {
      if (!this.genderValuedifferentiation) {
        return null;
      }
      return "";
    },

    isAgeValuedifferentiationValid() {
      if (this.isAgeValuedifferentiationValidFeedback === null) return null;
      return this.isAgeValuedifferentiationValidFeedback === "";
    },
    isAgeValuedifferentiationValidFeedback() {
      if (!this.ageValuedifferentiation) {
        return null;
      }
      return "";
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
      if (!this.isNormalMaxValid) {
        return false;
      }
      if (!this.isNormalMinValid) {
        return false;
      }
      if (!this.isGenderValuedifferentiationValid) {
        return false;
      }
      if (!this.isAgeValuedifferentiationValid) {
        return false;
      }
      return true;
    },
  },
  created() {
    this.getBiomedicDataType();
  },
  methods: {
    getBiomedicDataType() {
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.entidade = entidade;
      });
    },
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
          normalMinValue: this.normalMinValue,
          normalMaxValue: this.normalMaxValue,
          ageValuedifferentiation: this.ageValuedifferentiation,
          genderValuedifferentiation: this.genderValuedifferentiation,
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
      this.$axios
        .$delete(`/api/biomedicDataTypes/${code}`)
        .then(() => {
          this.$toast
            .info("Biomedic data type " + code + " deleted with success!")
            .goAway(3000);

          this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
            this.entidade = entidade;
          });
        })
        .catch((response) => {
          this.$toast.info(`Error - ${response.response.data}`).goAway(3000);
          this.getBiomedicDataType();
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>



