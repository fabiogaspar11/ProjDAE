
<template>
  <div>
    <NavBar></NavBar>

    <b-container>
      <h3 class="mt-3">Biomedic Data Measures ({{ tableLength }})</h3>
      <div
        class="mt-3 text-center"
        v-if="Object.keys(biomedicDataTypes).length != 0"
      >
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New Biomedic Measure
        </b-button>
      </div>
      <div v-else class="w-75 mx-auto alert alert-info">
        <p>
          Cannot insert Biomedic Measures without having Biomedic Data Types
        </p>
      </div>

      <div v-if="this.tableLength == 0">
        <div class="w-75 mx-auto alert alert-info">
          No Biomedic Measures registered yet
        </div>
      </div>
      <div class="mt-3 mb-5 text-center" v-else>
        <b-form-input
          v-model="filter"
          type="search"
          placeholder="Search..."
        ></b-form-input>

        <download-excel
          class="btn btn-default"
          :data="entidade"
          :fields="json_fields"
          worksheet="Biomedic Data Measures"
          :name="'biomedicDataMeasures.' + typeExcel"
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

        <b-table
          small
          v-if="this.tableLength != 0"
          class="mt-5"
          :items="this.entidade"
          :fields="fields"
          striped
          responsive="sm"
          :filter="filter"
          @filtered="search"
          id="table"
          :per-page="perPage"
          :current-page="currentPagePaginate"
        >
          <template v-slot:cell(operations)="row">
            <b-button
              :to="`biomedicMeasures/${row.item.code}`"
              variant="info"
            >
              <font-awesome-icon icon="eye" /> Details
            </b-button>
            <div v-if="row.item.usernameRegister == username">
              <b-button
                variant="danger"
                @click="remove(row.item.code)"
              >
                <font-awesome-icon icon="trash" /> Remove
              </b-button>
            </div>
            <div v-else>
              Delete - Not avaliable
            </div>
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

      <b-modal id="modal-1" title="New Biomedic Measure" @ok="create()">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="inputGroup-sizing-default"
              >Date</span
            >
          </div>
          <b-input
            required
            v-model.trim="date"
            type="text"
            :state="isDateValid"
            placeholder="dd/mm/yyyy"
            class="form-control"
            aria-describedby="basic-addon1 "
          />
          <b-form-datepicker
            id="ex-disabled-readonly"
            button-only
            :date-format-options="{ year: 'numeric', month: 'numeric', day: 'numeric' }"
            @context="onContext"
          ></b-form-datepicker>
        </div>
        <p>{{ isDateValidFeedback }}</p>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="inputGroup-sizing-default"
              >Hour</span
            >
          </div>
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
          <b-input
            :disabled="!biomedicDataType"
            v-model="value"
            :state="this.isValueValid"
            class="col-md-3"
            >{{ this.value }}</b-input
          >
          <b-input-group
            :prepend="this.minVal"
            :append="this.maxVal"
            class="mt-3"
          >
            <b-input
              :disabled="!biomedicDataType"
              required
              v-model.trim="value"
              type="range"
              :min="minVal"
              :max="maxVal"
              placeholder="Enter the value"
              class="form-control"
              aria-describedby="basic-addon1 "
            />
          </b-input-group>
        </div>
        <p>{{ isValueValidFeedback }}</p>
      </b-modal>
    </b-container>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from "vue-router";
import XLSX from "xlsx";

export default {
  middleware: ['isPatient', "isPatientAccessingHisData"],
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
          key: "biomedicDataType",
          label: "Biomedic Data Type",
          sortable: true,
          sortDirection: "desc",
        },
        { key: "value", label: "Value", sortable: true, sortDirection: "desc" },
        { key: "date", label: "Date", sortable: true, sortDirection: "desc" },
        "Operations",
      ],
      noBiomedicMeasures: false,
      entidade: [],
      modalShow: false,
      value: null,
      biomedicDataType: null,
      biomedicDataTypes: [],
      date: null,
      hour: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      minVal: null,
      maxVal: null,
      biomedicUpdate: null,
      dateEdit: null,
      hourEdit: null,
      valueEdit: null,
      dateUpdate: null,
      hourUpdate: null,
      valueUpdate: null,
      minValueUpdate: null,
      maxValueUpdate: null,
      perPage: 4,
      currentPagePaginate: 1,
      json_fields: {
        Code: "code",
        PatientUsername: "usernamePatient",
        "Biomedic Data Type": "biomedicDataType",
        Value: "value",
        Date: "date",
        Hour: "hour",
        UsernameRegister: "usernameRegister"
      },
      typeExcel: "",

    };
  },
  computed: {
    username() {
      return this.$auth.user.sub;
    },
    tableLength: function () {
      return this.entidade.length;
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
    isDateEditValidFeedback() {
      if (!this.dateEdit || this.dateEdit === "No date selected") {
        return null;
      }

      if (this.dateEdit == this.dateUpdate) {
        return "New Date is the same as the old one";
      }

      var date_regex =
        /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
      var currentdate = new Date();
      var dateSplitted = this.dateEdit.split("/");
      var dateRegexValid = date_regex.test(this.dateEdit);
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
    isDateEditValid() {
      if (this.isDateEditValidFeedback === null) {
        return null;
      }
      return this.isDateEditValidFeedback === "";
    },
    isHourValidFeedback() {
      if (!this.hour) {
        return null;
      }
      var hour_regex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
      var hourRegexValid = hour_regex.test(this.hour);
      if (!hourRegexValid) {
        return "The hour is invalid - format HH:MM";
      }
      var currentdate = new Date();

      if (!this.isDateValid) {
        return "Date is incorrect. Correct it first";
      }
      var dateSplitted = this.date.split("/");

      if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) == currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) == currentdate.getDate()
      ) {
        var hourSplitted = this.hour.split(":");
        if (parseInt(hourSplitted[0]) < currentdate.getHours()) {
          return "";
        } else if (
          parseInt(hourSplitted[0]) == currentdate.getHours() &&
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
    isHourEditValidFeedback() {
      if (!this.hourEdit) {
        return null;
      }

      if (this.hourEdit == this.hourUpdate) {
        return "New hour is the same as the old one";
      }

      var hour_regex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
      var hourRegexValid = hour_regex.test(this.hourEdit);
      if (!hourRegexValid) {
        return "The hour is invalid - format HH:MM";
      }
      var currentdate = new Date();
      if (this.dateEdit == null || this.dateEdit == "") {
        var dateSplitted = this.dateUpdate.split("/");
      } else {
        if (!this.isDateEditValid) {
          return "Date is incorrect. Correct it first";
        }
        var dateSplitted = this.dateEdit.split("/");
      }

      if (
        parseInt(dateSplitted[2]) == currentdate.getFullYear() &&
        parseInt(dateSplitted[1]) == currentdate.getMonth() + 1 &&
        parseInt(dateSplitted[0]) == currentdate.getDate()
      ) {
        var hourSplitted = this.hourEdit.split(":");
        if (parseInt(hourSplitted[0]) < currentdate.getHours()) {
          return "";
        } else if (
          parseInt(hourSplitted[0]) == currentdate.getHours() &&
          parseInt(hourSplitted[1]) <= currentdate.getMinutes()
        ) {
          return "";
        }
        return "The time is bigger than the current time";
      }
      return "";
    },
    isHourEditValid() {
      if (this.isHourEditValidFeedback === null) {
        return null;
      }
      return this.isHourEditValidFeedback === "";
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
    isValueEditValidFeedback() {
      if (!this.valueEdit) {
        return null;
      }
      if (this.valueUpdate == this.valueEdit)
        return "New value is the same as the old one";
      let valueLen = this.valueEdit.length;
      if (valueLen < 1 || valueLen > 25) {
        return "The value is mandatory and must have between 1 and 25 digits";
      }
      if (this.minValueUpdate == null || this.maxValueUpdate == null) {
        return "The Biomedic Data Type must be choosen first";
      }

      if (
        this.valueEdit < this.minValueUpdate ||
        this.valueEdit > this.maxValueUpdate
      ) {
        return (
          "The value is invalid, the value must be between " +
          "[" +
          this.minValueUpdate +
          " , " +
          this.maxValueUpdate +
          " ]"
        );
      }

      return "";
    },
    isValueEditValid() {
      if (this.isValueEditValidFeedback === null) {
        return null;
      }
      return this.isValueEditValidFeedback === "";
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
      return true;
    },
  },
  methods: {
    getBiomedicMeasures() {
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.biomedicDataTypes = entidade;
        this.$axios
          .$get("/api/patients/" + this.username + "/biomedicMeasures")
          .then((entidade) => {
            if (entidade.length == 0) {
              this.noBiomedicMeasures = true;
              return;
            } else {
              this.noBiomedicMeasures = false;
            }
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
          usernamePatient: this.username,
          usernameRegister: this.$auth.user.sub
        })
        .then((response) => {
          this.$toast
            .success(
              "Biomedic data Measure " + response.code + " created succesfully!"
            )
            .goAway(3000);
          this.date = null;
          this.hour = null;
          this.biomedicDataType = null;
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
    sendInfo(row) {
      this.biomedicUpdate = row;
      this.hourUpdate = row.hour;
      this.dateUpdate = row.date;
      this.valueUpdate = row.value.split(" ")[0];
      this.biomedicDataTypes.some((biomedicDT) => {
        if (row.biomedicDataType === biomedicDT.name) {
          this.minValueUpdate = biomedicDT.minValue;
          this.maxValueUpdate = biomedicDT.maxValue;
        }
      });
    },
    update() {
      this.biomedicDataMeasure = {};
      if (this.isDateEditValid) {
        this.biomedicDataMeasure.date = this.dateEdit;
      }
      if (this.isHourEditValid) {
        this.biomedicDataMeasure.hour = this.hourEdit;
      }
      if (this.isValueEditValid) {
        this.biomedicDataMeasure.value = this.valueEdit;
      }
      if (Object.keys(this.biomedicDataMeasure).length == 0) {
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }
      this.$axios
        .$put(
          `/api/biomedicDataMeasures/${this.biomedicUpdate.code}`,
          this.biomedicDataMeasure
        )
        .then((response) => {
          this.$toast
            .info(
              "Biomedic data Measure " + response.code + " updated succesfully!"
            )
            .goAway(3000);
          this.biomedicDataMeasure = {};
          this.date = null;
          this.hour = null;
          this.value = null;
          this.hourEdit = null;
          this.valueEdit = null;
          this.dateEdit = null;
          this.minValueUpdate = null;
          this.maxValueUpdate = null;
          this.dateUpdate = null;
          this.hourUpdate = null;
          this.valueUpdate = null;
          this.getBiomedicMeasures();
        })
        .catch((error) => {
          this.$toast
            .error(
              "Error when updating Biomedic Measure: " + error.response.data
            )
            .goAway(3000);
          this.minValueUpdate = null;
          this.maxValueUpdate = null;
          this.dateUpdate = null;
          this.hourUpdate = null;
          this.valueUpdate = null;
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
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
             if(data[0].length != 7){
                this.$toast.error("Invalid number of columns in excel file").goAway(3000);
              break;
            }
            if(data[0][0] != "Code" || data[0][1] != "PatientUsername" || data[0][2] != "Biomedic Data Type"  || data[0][3] != "Value" || data[0][4] != "Date" || data[0][5] != "Hour" || data[0][6] != "UsernameRegister" ){
              this.$toast.error("Excel columns not in the right format").goAway(3000);
              break;
            }

            let usernamePatient = data[i][1];
            let biomedicDataType = data[i][2];
            let biomedicDataTypeNumber = this.getBiomedicDataTypeNumber(biomedicDataType);
            let value = data[i][3].split(" ")[0];
            let date = null;
            if (!data[i][4].toString().includes("/")) {
              date = this.convertToDate(data[i][4]);
            }else{
              date = data[i][4].toString();
            }
            let hour = null;
            if (data[i][5].toString().includes(":")) {
             hour = data[i][5].toString();
            }else{
              let currentDate = new Date();
              hour = currentDate.getHours().toString()+":"+(currentDate.getMinutes()).toString();
            }
            let usernameRegister =  data[i][6]
            this.$axios
              .$post("/api/biomedicDataMeasures", {
                date: date,
                hour: hour,
                biomedicDataTypeCode: biomedicDataTypeNumber,
                value: value,
                usernamePatient: usernamePatient,
                usernameRegister: usernameRegister
              })
              .then((response) => {
                this.$toast
                  .success(
                    "Biomedic data Measure " +
                      response.code +
                      " created succesfully!"
                  )
                  .goAway(3000);
                this.getBiomedicMeasures();
              })
              .catch((error) => {
                let message = "";
                if(error.response.status == 403){
                    message = "Action is Forbidden"
                }else{
                    message = error.response.data
                }
                this.$toast.error("Error creating Biomedic data Measure - " + message).goAway(3000);
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
    getBiomedicDataTypeNumber(biomedicDataTypeName) {
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.biomedicDataTypes = entidade;
      });

      for (let item of this.biomedicDataTypes) {
        if (item.name === biomedicDataTypeName) {
          return item.code;
        }
      }

      return this.biomedicDataTypes;
    },
    onContext(ctx) {
       if(ctx.selectedDate == null){
        return null;
    }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.date = ctx.selectedFormatted
    },
    onContextEdit(ctx) {
       if(ctx.selectedDate == null){
        return null;
    }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.dateEdit = ctx.selectedFormatted
    }
  },
  created() {
    this.getBiomedicMeasures();
  },
};
</script>



