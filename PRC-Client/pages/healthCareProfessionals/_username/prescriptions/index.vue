<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
      <h3>Prescriptions ({{ tableLength }})</h3>
      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>
      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New Prescription
        </b-button>

        <download-excel
          class="btn btn-default"
          :data="entidade"
          :fields="json_fields"
          worksheet="Prescriptions"
          :name="'prescriptions.' + typeExcel"
          :type="typeExcel"
        >
          <b-dropdown
            id="dropdown-1"
            text="Download Data"
            class="m-md-2"
            variant="success"
          >
            <b-dropdown-item @click.prevent="typeExcel = 'xls'"
              >.xls</b-dropdown-item
            >
            <b-dropdown-item @click.prevent="typeExcel = 'csv'"
              >.csv</b-dropdown-item
            >
          </b-dropdown>
        </download-excel>

        <b-form-file
          placeholder="Import data (.xls,.xlsx,.csv)"
          @change="onChange"
          class="w-25 text-lg-left"
        ></b-form-file>
      </div>

      <b-modal
        id="modal-1"
        title="New Prescription"
        @ok.prevent="createPrescription()"
      >
        <div class="input-group mb-4">
          <span class="input-group-text">Healthcare Professional</span>
          <b-input
            disabled
            v-model="usernameHealthcareProfessional"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
          />
        </div>
        <hr />
        <div class="input-group mb-4">
          <span class="input-group-text">Patient Health Number</span>
          <b-input
            required
            v-model.trim="healthNumberPatient"
            type="number"
            :state="ishealthNumberPatientValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter patient health number"
          />
        </div>
          <p>{{ ishealthNumberPatientValidFeedback }}</p>

        <div class="overflow-auto">
          <b-form-input
            class="mb-3"
            v-model="filterAssociateds"
            type="search"
            placeholder="Search..."
          >
          </b-form-input>

          <b-table
          v-if="this.tableLength != 0"
            striped
            hover
            :items="this.patients"
            :fields="fieldsPatient"
            :filter="filterAssociateds"
            @filtered="search"
            id="tableAssociateds"
            :current-page="currentPagePaginateSecondary"
            :per-page="perPagePatients"
            :select-mode="'single'"
            selectable
            @row-selected="onRowSelected"
          >
          </b-table>
          <b-pagination
            v-if="this.tableLength != 0"
            class="justify-content-center"
            v-model="currentPagePaginateSecondary"
            :total-rows="rows"
            :per-page="perPagePatients"
            aria-controls="tableAssociateds"
          ></b-pagination>
          <div  v-if="this.tableLength == 0" class="w-75 mx-auto alert alert-info">
            No Patients created yet
          </div>
        </div>
        <hr />
        <div class="input-group mb-4">
          <span class="input-group-text">Title</span>
          <b-input
            required
            v-model.trim="title"
            type="text"
            :state="isTitleValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter prescription title"
          />
        </div>
          <p>{{ isTitleValidFeedback }}</p>
        <div>
          Pharmacological prescription:
          <b-form-select
            v-model="isPharmacological"
            :options="options"
            :state="isOptionValid"
          ></b-form-select>
        </div>
          <p>{{ isOptionValidFeedback }}</p>
        <br />
        <span>Treatment information:</span>
        <div>
          <b-form-textarea
            id="textarea"
            v-model.trim="treatmentInfo"
            :state="isTreatmentInfoValid"
            placeholder="Treatment information..."
            rows="3"
            max-rows="6"
          ></b-form-textarea>
        </div>
          <p>{{ isTreatmentInfoValidFeedback }}</p>
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
          <b-input-group-append>
            <b-form-datepicker
              id="ex-disabled-readonly"
              button-only
              :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
              @context="onContext"
            ></b-form-datepicker>
          </b-input-group-append>
        </div>
      </b-modal>
        <p>{{ isDateValidFeedback }}</p>
      <div class="mt-1"  v-if="this.tableLength != 0">
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
          <template v-slot:cell(operations)="row">
            <b-button :to="`./prescriptions/${row.item.code}`" variant="info">
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
      </div>
        <div  v-else class="w-75 mx-auto alert alert-info">
          No prescriptions created yet
        </div>
    </b-container>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import XLSX from "xlsx";

export default {
  middleware: "isHealthcareProfessionalAccessingHisData",
  components: {
    NavBar,
    XLSX,
  },
  data() {
    return {
      fields: [
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        {
          key: "emissionDate",
          label: "Emission Date",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "usernamePatient", label: "Patient username" },

        "operations",
      ],
      options: [
        { value: null, text: "Please select an option" },
        { value: "No", text: "No" },
        { value: "Yes", text: "Yes" },
        { value: "Both", text: "Both" },
      ],
      fieldsPatient: [
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
        {
          key: "healthNumber",
          label: "Health Number",
          sortable: true,
          sortDirection: "desc",
        },
      ],
      selected: [],
      entidade: [],
      patients: [],
      treatmentInfo: null,
      name: null,
      title: null,
      observations: "",
      filterAssociateds: null,
      expireDate: null,
      isPharmacological: null,
      code: null,
      healthNumberPatient: null,
      usernameHealthcareProfessional: this.$auth.user.sub,
      filter: null,
      totalRows: null,
      emissionDate: null,
      perPage: 5,
      perPagePatients: 3,
      currentPagePaginatePrincipal: 1,
      currentPagePaginateSecondary: 1,
      json_fields: {
        Code: "code",
        "Emission Date": "emissionDate",
        "Expire Date": "expireDate",
        Pharmacological: "isPharmacological",
        Title: "title",
        "Treatment Information": "treatmentInfo",
        Observations: "observations",
        PatientUsername: "usernamePatient",
        HealthcareProfessional: "usernameHealthcareProfessional",
      },
      typeExcel: "",
    };
  },
  created() {
    this.getData();
    this.$axios
      .$get(`api/healthcareProfessionals/${this.$auth.user.sub}/patients`)
      .then((entidade) => {
        this.patients = entidade;
      });
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
      let length = this.title.length;
      if (length < 3 || length > 25) {
        return "The title is mandatory and must have between 3 and 25 letters";
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
       let length = this.treatmentInfo.length;
      if (length < 3 || length > 25) {
        return "The treatment information is mandatory and must have between 3 and 25 letters";
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
    ishealthNumberPatientValidFeedback() {
      if (!this.healthNumberPatient) {
        return null;
      }
      let healthNumberString = this.healthNumberPatient.toString();
      let healthNumberLen = healthNumberString.length;
      if (healthNumberLen != 9) {
        return "The healh number is invalid - the number must have 9 digits";
      }
      return "";
    },
    ishealthNumberPatientValid() {
      if (this.ishealthNumberPatientValidFeedback === null) {
        return null;
      }
      return this.ishealthNumberPatientValidFeedback === "";
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

      if (!this.ishealthNumberPatientValid) {
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
    rows() {
      return this.patients.length;
    },
  },
  methods: {
    getData() {
      this.$axios
        .$get(
          `/api/healthcareProfessionals/${this.$auth.user.sub}/prescriptions`
        )
        .then((entidade) => {
          this.entidade = entidade;
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
    },
    remove(code) {
      this.$axios.$delete(`/api/prescriptions/${code}`).then(() => {
        this.$toast
          .info("Prescription " + code + " was successfully removed")
          .goAway(3000);
        this.getData();
      });
    },
    createPrescription() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }

      this.$axios
        .$post("/api/prescriptions", {
          expireDate: this.expireDate,
          isPharmacological: this.isPharmacological,
          observations: this.observations,
          title: this.title,
          treatmentInfo: this.treatmentInfo,
          usernamePatient: "P"+ this.healthNumberPatient,
          usernameHealthcareProfessional: this.usernameHealthcareProfessional,
        })
        .then((response) => {
          this.$toast.success("Prescription created succesfully").goAway(3000);

          this.expireDate = null;
          this.isPharmacological = null;
          this.observations = null;
          this.title = null;
          this.treatmentInfo = null;
          this.healthNumberPatient = null;
          this.getData();
        })
        .catch((error) => {
          this.$toast
            .error("Error when creating Prescription: " + error.response.data)
            .goAway(3000);
        });
    },
    onRowSelected(items) {
      if (items.length !== 0) {
        this.healthNumberPatient = items[0].healthNumber;
      }
    },
    onChange(event) {
      this.file = event.target.files ? event.target.files[0] : null;
      if (
        this.file &&
        (this.file.name.endsWith(".xls") ||
          this.file.name.endsWith(".xlsx") ||
          this.file.name.endsWith(".csv"))
      ) {
        const reader = new FileReader();

        reader.onload = (e) => {
          /* Parse data */
          const bstr = e.target.result;
          const wb = XLSX.read(bstr, { type: "binary" });
          /* Get first worksheet */
          const wsname = wb.SheetNames[0];
          const ws = wb.Sheets[wsname];
          /* Convert array of arrays */
          const data = XLSX.utils.sheet_to_json(ws, { header: 1 });

          for (let i = 1; i < data.length; i++) {
            let expireDate = data[i][2].toString();
            if (!expireDate.includes("/")) {
              expireDate = this.convertToDate(data[i][2]);
            }
            let isPharmacological = data[i][3];
            let title = data[i][4];
            let treatmentInfo = data[i][5];
            let observations = data[i][6];
            let usernamePatient = data[i][7].slice(1);
            let usernameHealthcareProfessional = data[i][8].slice(1);
            console.log(usernameHealthcareProfessional);

            this.$axios
              .$post("/api/prescriptions", {
                expireDate: expireDate,
                isPharmacological: isPharmacological,
                title: title,
                treatmentInfo: treatmentInfo,
                observations: observations,
                usernamePatient: usernamePatient,
                usernameHealthcareProfessional: usernameHealthcareProfessional,
              })
              .then((response) => {
                this.$toast
                  .success("Prescription created succesfully")
                  .goAway(3000);
                this.getData();
              })
              .catch((error) => {
                this.$toast
                  .error(
                    "Error when creating Prescription: " + error.response.data
                  )
                  .goAway(3000);
              });
          }
        };
        reader.readAsBinaryString(this.file);
      } else {
        this.$toast.error("File type invalid: ").goAway(3000);
      }
    },
    convertToDate(serial) {
      const utc_days = Math.floor(serial - 25569);
      const utc_value = utc_days * 86400;
      const date_info = new Date(utc_value * 1000);

      return new Date(
        date_info.getFullYear(),
        date_info.getMonth(),
        date_info.getDate() + 1
      ).toLocaleDateString("en-US");
    },
    onContext(ctx) {
       if(ctx.selectedDate == null){
        return null;
    }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.expireDate = ctx.selectedFormatted
    }
  },
};
</script>
