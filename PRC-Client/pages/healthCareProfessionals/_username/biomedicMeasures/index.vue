
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="mt-3">
      <h3>Biomedic Data Measures ({{ tableLength }})</h3>
      <b-form-input
        v-model="filter"
        type="search"
        placeholder="Search..."
      ></b-form-input>
      <div class="mt-3 mb-5 text-center">
        <b-button
          v-if="biomedicDataTypes != [] && patients != []"
          v-b-modal.modal-1
          variant="info"
        >
          <font-awesome-icon icon="plus" /> New Biomedic Measure
        </b-button>
        <div v-else>
          <p>
            Cannot insert Biomedic Measures without having Biomedic Data Types
          </p>
        </div>

        <download-excel
          class="btn btn-default"
          :data="entidade"
          :fields="json_fields"
          worksheet="Biomedic Data Measures"
          :name="'biomedicDataMeasures.'+typeExcel"
          :type="typeExcel"
        >
          <b-dropdown id="dropdown-1" text="Download Data" class="m-md-2" variant="success">
            <b-dropdown-item @click.prevent="typeExcel = 'xls'">.xls</b-dropdown-item>
            <b-dropdown-item @click.prevent="typeExcel = 'csv'">.csv</b-dropdown-item>
          </b-dropdown>
        </download-excel>

        <b-form-file
          placeholder="Import data (.xls,.xlsx,.csv)"
          @change="onChange"
          class="w-25 text-lg-left"
        ></b-form-file>
      </div>

      <b-modal id="modal-1" title="New Biomedic Measure" @ok="create()">
        <div class="input-group mb-4">
          <span class="input-group-text">Date</span>
          <b-input
            required
            v-model.trim="date"
            type="text"
            :state="isDateValid"
            placeholder="dd/mm/yyyy"
            class="form-control"
            aria-describedby="basic-addon1 "
          />
        </div>
        <p>{{ isDateValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Hour</span>
          <b-input
            required
            v-model.trim="hour"
            type="text"
            :state="isHourValid"
            placeholder="HH:MM"
            class="form-control"
            aria-describedby="basic-addon1 "
          />
        </div>
        <p>{{ isHourValidFeedback }}</p>
        <div class="input-group mb-4">
          <b-select
            v-model="biomedicDataType"
            :options="biomedicDataTypes"
            :state="isBiomedicDataTypeValid"
            required
            value-field="code"
            text-field="name"
          >
            <template v-slot:first>
              <option :value="null" disabled>
                -- Please select the Biomedic Data Type --
              </option>
            </template>
          </b-select>
        </div>
        <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Value</span>
          <b-input v-model="value"  :disabled="!biomedicDataType" :state="this.isValueValid" class="col-md-2">{{this.value}}</b-input>
          <b-input-group :prepend="this.minVal" :append="this.maxVal" class="mt-3">
            <b-input
            :disabled="!biomedicDataType"
              required
              v-model.trim="value"
              type="range"
              :min="minVal" :max="maxVal"
              placeholder="Enter the value"
              class="form-control"
              aria-describedby="basic-addon1 "
            />
          </b-input-group>
          <p>{{ isValueValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <b-select v-model="patient" :state="isPatientValid">
            <template v-slot:first>
              <option :value="null" disabled>
                -- Please select the Patient --
              </option>
            </template>
            <option
              v-for="patient in patients"
              :key="patient.username"
              :value="patient.username"
            >
              {{ patient.healthNumber }} - {{ patient.name }}
            </option>
          </b-select>
        </div>
      </b-modal>
      <div class="mt-1">
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
            <b-button
              :to="`./biomedicMeasures/${row.item.code}`"
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
          v-model="currentPagePaginatePrincipal"
          :total-rows="rows"
          :per-page="perPage"
          aria-controls="table"
        ></b-pagination>
      </div>
    </b-container>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from "vue-router";
import XLSX from "xlsx";

export default {
  components: {
    NavBar,
    Router,
    XLSX,
  },
  data() {
    return {
      fields: [
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        {
          key: "usernamePatient",
          label: "Patient Username",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "date", label: "Date", sortable: true, sortDirection: "desc" },
        { key: "hour", label: "Hour", sortable: true, sortDirection: "desc" },
        "operations",
      ],
      entidade: [],
      modalShow: false,
      patient: null,
      value: null,
      biomedicDataType: null,
      biomedicDataTypes: [],
      patients: [],
      date: null,
      hour: null,
      filter: null,
      totalRows: null,
      minVal: null,
      maxVal: null,
      perPage: 6,
      currentPagePaginatePrincipal: 1,
      json_fields: {
        Code: "code",
        PatientUsername: "usernamePatient",
        "Biomedic Data Type": "biomedicDataType",
        Value: "value",
        Date: "date",
        Hour: "hour",
      },
      typeExcel:"",
    };
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    isPatientValid() {
      if (!this.patient) {
        return null;
      }
      return this.patients.some((p) => this.patient === p.username);
    },
    isDateValidFeedback() {
      if (!this.date) {
        return null;
      }
      var date_regex =
        /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
      var currentdate = new Date();
      var dateSplitted = this.date.split("/");
      var dateRegexValid = date_regex.test(this.date);
      if (!dateRegexValid) {
        return "The date is invalid - format dd/mm/yyyy";
      }
      if (parseInt(dateSplitted[2]) < currentdate.getFullYear()) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) < currentdate.getMonth() + 1
      ) {
        return "";
      } else if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) == currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) <= currentdate.getDate()
      ) {
        return "";
      }
      return "The date is bigger than todays date";
    },
    isDateValid() {
      if (this.isDateValidFeedback === null) {
        return null;
      }
      return this.isDateValidFeedback === "";
    },
    isHourValidFeedback() {
      if (!this.hour) {
        return null;
      }
      if (this.date == null) {
        return "Date field must be filled first";
      }
      var hour_regex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
      var hourRegexValid = hour_regex.test(this.hour);
      if (!hourRegexValid) {
        return "The hour is invalid - format HH:MM";
      }
      var dateSplitted = this.date.split("/");
      var currentdate = new Date();
      if (
        parseInt(dateSplitted[2]) === currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) === currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) === currentdate.getDate()
      ) {
        var hourSplitted = this.hour.split(":");
        if (parseInt(hourSplitted[0]) < currentdate.getHours()) {
          return "";
        } else if (
          parseInt(hourSplitted[0]) === currentdate.getHours() &&
          parseInt(hourSplitted[1]) <= currentdate.getMinutes()
        ) {
          return "";
        }
        return "The time is bigger than the current time";
      }
      return "";
    },
    isHourValid() {
      if (this.isHourValidFeedback === null) {
        return null;
      }
      return this.isHourValidFeedback === "";
    },
    isBiomedicDataTypeValid() {
      if (!this.biomedicDataType) {
        return null;
      }
      return this.biomedicDataTypes.some((biomedicDT) => {
        if (this.biomedicDataType === biomedicDT.code) {
          this.minVal = biomedicDT.minValue;
          this.maxVal = biomedicDT.maxValue;
          return true;
        }
        return false;
      });
    },
    isValueValidFeedback() {
      if (!this.value) {
        return null;
      }
      let valueLen = this.value.length;
      if (valueLen < 1 || valueLen > 25) {
        return "The value is mandatory and must have between 1 and 25 digits";
      }
      if (this.minVal == null || this.maxVal == null) {
        return "The Biomedic Data Type must be choosen first";
      }

      if (this.value < this.minVal || this.value > this.maxVal) {
        return (
          "The value is invalid, the value must be between " +
          "[" +
          this.minVal +
          " , " +
          this.maxVal +
          " ]"
        );
      }

      return "";
    },
    isValueValid() {
      if (this.isValueValidFeedback === null) {
        return null;
      }
      return this.isValueValidFeedback === "";
    },
    isFormValid() {
      if (!this.isDateValid) {
        return false;
      }
      if (!this.isHourValid) {
        return false;
      }
      if (!this.isBiomedicDataTypeValid) {
        return false;
      }
      if (!this.isValueValid) {
        return false;
      }
      if (!this.isPatientValid) {
        return false;
      }
      return true;
    },
    rows() {
      return this.entidade.length;
    },
  },
  methods: {
    getBiomedicMeasures() {
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.biomedicDataTypes = entidade; //TODO missing route!!!
        this.$axios.$get(`/api/healthcareProfessionals/${this.$auth.user.sub}/biomedicDataMeasures`).then((entidade) => {
          this.entidade = entidade;
          let i = 0;
          entidade.forEach((e) => {
            this.biomedicDataTypes.forEach((b) => {
              if (e.biomedicDataTypeCode === b.code) {
                this.entidade[i].biomedicDataType = b.name;
                this.entidade[i].value += " " + b.unitMeasure;
              }
            });
            i++;
          });
        });
      });
    },
    create() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }
      this.$axios
        .$post("/api/biomedicDataMeasures", {
          date: this.date,
          hour: this.hour,
          biomedicDataTypeCode: this.biomedicDataType,
          value: this.value,
          usernamePatient: this.patient,
        })
        .then((response) => {
          console.log(response);
          this.$toast
            .success(
              "Biomedic data Measure " + response.code + " created succesfully!"
            )
            .goAway(3000);

          this.date = null;
          this.hour = null;
          this.biomedicDataType = null;
          this.patient = null;
          this.value = null;
          this.getBiomedicMeasures();
        })
        .catch((error) => {
          this.$toast
            .error(
              "Error creating Biomedic data Measure - " + error.response.data
            )
            .goAway(3000);
        });
    },
    remove(code) {
      this.$axios.$delete(`/api/biomedicDataMeasures/${code}`).then(() => {
        this.$toast
          .info("Biomedic Data Measure " + code + " deleted with success!")
          .goAway(3000);
        this.getBiomedicMeasures();
      });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
    },
    onChange(event) {
      this.file = event.target.files ? event.target.files[0] : null;
      if (this.file && (this.file.name.endsWith('.xls') || this.file.name.endsWith('.xlsx') || this.file.name.endsWith('.csv'))) {
        const reader = new FileReader();

        reader.onload = (e) => {
          /* Parse data */
          const bstr = e.target.result;
          const wb = XLSX.read(bstr, { type: 'binary' });
          /* Get first worksheet */
          const wsname = wb.SheetNames[0];
          const ws = wb.Sheets[wsname];
          /* Convert array of arrays */
          const data = XLSX.utils.sheet_to_json(ws, { header: 1 });

          for (let i = 1; i < data.length; i++){
            let usernameHealthcareProfessional = data[i][1].slice(1);
            let biomedicDataType = data[i][2]
            let biomedicDataTypeNumber = this.getBiomedicDataTypeNumber(biomedicDataType)
            let value = data[i][3]
            let date = data[i][4].toString()
            if (!date.includes("/")){
              date = this.convertToDate(data[i][4])
            }
            let hour = data[i][5].toString()


            this.$axios
              .$post("/api/biomedicDataMeasures", {
                date: date,
                hour: hour,
                biomedicDataTypeCode: biomedicDataTypeNumber,
                value: value,
                usernamePatient: usernameHealthcareProfessional,
              })
              .then((response) => {
                this.$toast.success("Biomedic data Measure " + response.code + " created succesfully!")
                  .goAway(3000);
                this.getBiomedicMeasures();
              })
              .catch((error) => {
                this.$toast.error("Error creating Biomedic data Measure - " + error.response.data)
                  .goAway(3000);
              });

          }
        }
        reader.readAsBinaryString(this.file);
      }
      else{
        this.$toast
          .error("File type invalid: ")
          .goAway(3000);
      }
    },
    convertToDate(serial) {
      const utc_days  = Math.floor(serial - 25569);
      const utc_value = utc_days * 86400;
      const date_info = new Date(utc_value * 1000);

      return new Date(date_info.getFullYear(), date_info.getMonth(), date_info.getDate()+1).toLocaleDateString('en-US');
    },
    getBiomedicDataTypeNumber(biomedicDataTypeName){
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.biomedicDataTypes = entidade;
      });

      for (let item of this.biomedicDataTypes){
        if (item.name === biomedicDataTypeName){
          return item.code
        }
      }

      return this.biomedicDataTypes
    }
  },
  created() {
    this.$axios
      .$get(`api/healthcareProfessionals/${this.$auth.user.sub}/patients`)
      .then((entidade) => {
        this.patients = entidade;
      });
    this.getBiomedicMeasures();
  },

};
</script>



