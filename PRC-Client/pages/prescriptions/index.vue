<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h1>Prescriptions ({{ tableLength }})</h1>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>
        <b-col>
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus" /> New Prescription
          </b-button>
        </b-col>
      </b-row>
    </b-container>

    <b-modal
      id="modal-1"
      title="New Prescription"
      @ok.prevent="createPrescription()"
    >
      <div class="input-group mb-4">
        <span class="input-group-text">Healthcare Professional username</span>
        <b-input
          v-model="usernameHealthcareProfessional"
          type="text"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Patient Health Number</span>
        <b-input
          required
          v-model="usernamePatient"
          type="number"
          :state="isusernamePatientValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="Enter patient health number"
        />
        <p>{{ isusernamePatientValidFeedback }}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Title</span>
        <b-input
          required
          v-model="title"
          type="text"
          :state="isTitleValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="Enter prescription title"
        />
        <p>{{ isTitleValidFeedback }}</p>
      </div>
      <div>
        Pharmacological prescription:
        <b-form-select
          v-model="isPharmacological"
          :options="options"
          :state="isOptionValid"
        ></b-form-select>
        <p>{{ isOptionValidFeedback }}</p>
      </div>
      <br />
      <span>Treatment information:</span>
      <div>
        <b-form-textarea
          id="textarea"
          v-model="treatmentInfo"
          :state="isTreatmentInfoValid"
          placeholder="Treatment information..."
          rows="3"
          max-rows="6"
        ></b-form-textarea>
        <p>{{ isTreatmentInfoValidFeedback }}</p>
      </div>
      <br />
      <span>Observations:</span>
      <div>
        <b-form-textarea
          id="textarea"
          v-model="observations"
          placeholder="Enter observations..."
          rows="3"
          max-rows="6"
        ></b-form-textarea>
      </div>
      <br />
      <div class="input-group mb-4">
        <span class="input-group-text">Expire Date</span>
        <b-input
          required
          v-model.trim="expireDate"
          type="text"
          :state="isDateValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="dd/mm/yyyy"
        />
      </div>
        <p>{{ isDateValidFeedback }}</p>
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
          <b-button :to="`/prescriptions/${row.item.code}`" variant="info">
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
    </div>
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
        { key: "usernamePatient", label: "Patient username" },
        {
          key: "usernameHealthcareProfessional",
          label: "Healthcare professional",
        },
        "operations",
      ],
      options: [
        { value: null, text: "Please select an option" },
        { value: "No", text: "No" },
        { value: "Yes", text: "Yes" },
        { value: "Both", text: "Both" },
      ],

      entidade: [],
      treatmentInfo: null,
      name: null,
      title: null,
      observations: "",
      expireDate: null,
      isPharmacological: null,
      code: null,
      usernamePatient: null,
      usernameHealthcareProfessional: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      emissionDate: null,
    };
  },
  created() {
    this.getData();
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    isTitleValid() {
      if (this.isTitleValidFeedback === null) {
        return null;
      }
      return this.isTitleValidFeedback === "";
    },
    isTitleValidFeedback() {
      if (!this.title) {
        return null;
      }
      return "";
    },
    isTreatmentInfoValid() {
      if (this.isTreatmentInfoValidFeedback === null) {
        return null;
      }
      return this.isTreatmentInfoValidFeedback === "";
    },
    isTreatmentInfoValidFeedback() {
      if (!this.treatmentInfo) {
        return null;
      }
      return "";
    },
    isDateValid() {
      if (this.isDateValidFeedback === null) {
        return null;
      }
      return this.isDateValidFeedback === "";
    },
    isDateValidFeedback() {
      if (!this.expireDate) {
        return null;
      }
      var date_regex =
        /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
      var currentdate = new Date();
      var dateSplitted = this.expireDate.split("/");
      var dateRegexValid = date_regex.test(this.expireDate);
      if (!dateRegexValid) {
        return "The date is invalid - format dd/mm/yyyy";
      }
      if (parseInt(dateSplitted[2]) > currentdate.getFullYear()) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) > currentdate.getMonth() + 1
      ) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) == currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) >= currentdate.getDate()
      ) {
        return "";
      }
      return "The expire date should be bigger than the actual date";
    },
    isusernamePatientValidFeedback() {
      if (!this.usernamePatient) {
        return null;
      }
      let healthNumberString = this.usernamePatient.toString();
      let healthNumberLen = healthNumberString.length;
      if (healthNumberLen != 9) {
        return "The healh number is invalid - the number must have 9 digits";
      }
      return "";
    },
    isusernamePatientValid() {
      if (this.isusernamePatientValidFeedback === null) {
        return null;
      }
      return this.isusernamePatientValidFeedback === "";
    },
    isOptionValidFeedback() {
      if (this.isPharmacological == null) {
        return null;
      }
      if (
        this.isPharmacological != "No" &&
        this.isPharmacological != "Yes" &&
        this.isPharmacological != "Both"
      ) {
        return "Please select an option from the select box";
      }
      return "";
    },
    isOptionValid() {
      if (this.isOptionValidFeedback === null) {
        return null;
      }
      return this.isOptionValidFeedback === "";
    },
    isFormValid() {
      if (!this.isTitleValid) {
        return false;
      }

      if (!this.isDateValid) {
        return false;
      }

      if (!this.isusernamePatientValid) {
        return false;
      }

      if (!this.isOptionValid) {
        return false;
      }

      if (!this.isTreatmentInfoValid) {
        return false;
      }
      return true;
    },
  },
  methods: {
    getData() {
      this.$axios.$get("/api/prescriptions").then((entidade) => {
        this.entidade = entidade;
        console.log(entidade);
      });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    remove(code) {
      this.$axios.$delete(`/api/prescriptions/${code}`).then(() => {
        this.$toast.info("Prescription " + code + " was successfully removed").goAway(3000);

        this.getData();
      });
    },
    createPrescription() {
      if (!this.isFormValid) {
           this.$toast.error("Fields are invalid - Correct them first!").goAway(3000);
        return;
      }
      this.$axios
        .$post("/api/prescriptions", {
          expireDate: this.expireDate,
          isPharmacological: this.isPharmacological,
          observations: this.observations,
          title: this.title,
          treatmentInfo: this.treatmentInfo,
          usernamePatient: this.usernamePatient,
          usernameHealthcareProfessional: this.usernameHealthcareProfessional,
        })
        .then((response) => {
          this.$toast.success("Prescription created succesfully").goAway(3000);

          this.expireDate = null;
          this.isPharmacological = null;
          this.observations = null;
          this.title = null;
          this.treatmentInfo = null;
          this.usernamePatient = null;
          this.usernameHealthcareProfessional = null;
          this.getData();
        })
        .catch((error) => {
          this.$toast.error("Error when creating Prescription: " + error.response.data).goAway(3000);

        });
    },
  },
};
</script>
