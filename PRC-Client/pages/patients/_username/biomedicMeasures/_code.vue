<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>

      <div class="d-flex justify-content-center">
        <div v-if="classification === 'Normal'" class="bg-success w-50 text-center"  style="border-radius: 10px">
          <h5 style="color: white; margin-top: 1%; margin-bottom: 1%">Classification: Value in the bounds of reference</h5>
        </div>
        <div v-else-if="classification === 'Inferior'" class="bg-danger w-50 text-center" style="border-radius: 10px">
          <h5 style="color: white; margin-top: 1%; margin-bottom: 1%">Classifications: Value below the minimum reference value</h5>
        </div>
        <div v-else class="bg-danger w-50 text-center" style="border-radius: 10px">
          <h5 style="color: white; margin-top: 1%; margin-bottom: 1%">Classifications: Value above the minimum reference value</h5>
        </div>
      </div>

      <b-button v-if="canEdit" v-b-modal.modal-1 class="text-center">Edit</b-button>
      <div v-else class="w-75 mx-auto alert alert-info">
            Cannot Edit this Biomedic Data
      </div>
      <b-modal id="modal-1" title="Edit Biomedic Measure" @ok="update()">
       <div class="input-group mb-4">
          <span class="input-group-text">Date</span>
          <b-input
            required
            v-model.trim="dateEdit"
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
        <div class="input-group mb-4">
          <span class="input-group-text">Hour</span>
          <b-input
            required
            v-model.trim="hourEdit"
            type="text"
            :state="isHourValid"
            placeholder="HH:MM"
            class="form-control"
            aria-describedby="basic-addon1 "
          />
        </div>
          <p>{{ isHourValidFeedback }}</p>
        <div class="input-group mb-4">
            <span class="input-group-text">Value</span>
          <b-input
            v-model="valueEdit"
            :state="this.isValueValid"
            class="col-md-2"
            >{{ this.valueEdit }}</b-input
          >
          <b-input-group
            :prepend="this.minVal"
            :append="this.maxVal"
            class="mt-3"
          >
            <b-input
              required
              v-model.trim="valueEdit"
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
    </div>
  </div>
</template>




<script>
export default {
  middleware: ['isPatient', "isPatientAccessingHisData"],
  data() {
    return {
      fields: [
        { key: "code", label: "Code" },
        { key: "usernamePatient", label: "Patient Username" },
        { key: "biomedicDataType", label: "Biomedic Data Type" },
        { key: "normalMinValue", label: "Normal Minimum Value" },
        { key: "normalMaxValue", label: "Normal Maximum Value" },
        { key: "minValue", label: "Minimum Value" },
        { key: "maxValue", label: "Maximum Value" },
        { key: "value", label: "Value" },
        { key: "date", label: "Date" },
        { key: "hour", label: "Hour" },
        { key: "usernameRegister", label: "Username Register" },
      ],
      entidade: [],
      date: null,
      hour: null,
      value: null,
      biomedicDataMeasure: {},
      biomedicDataTypes: [],
      dateEdit: "",
      hourEdit: "",
      valueEdit: "",
      minVal: null,
      maxVal: null,
      normalMinValue: null,
      normalMaxValue: null,
      classification: null,
      canEdit:false
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
    },
    isDateValidFeedback() {
      if (!this.dateEdit) {
        return null;
      }
        if (this.dateEdit == this.date) {
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
    isDateValid() {
      if (this.isDateValidFeedback === null) {
        return null;
      }
      return this.isDateValidFeedback === "";
    },
    isHourValidFeedback() {
       if (!this.hourEdit) {
        return null;
      }

      if (this.hourEdit == this.hour) {
        return "New hour is the same as the old one";
      }

      var hour_regex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
      var hourRegexValid = hour_regex.test(this.hourEdit);
      if (!hourRegexValid) {
        return "The hour is invalid - format HH:MM";
      }

      var currentdate = new Date();
      if (this.dateEdit == null || this.dateEdit == "") {
        var dateSplitted = this.date.split("/");
      } else {
        if (!this.isDateValid) {
          return "Date is incorrent. Correct it first";
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
    isHourValid() {
      if (this.isHourValidFeedback === null) {
        return null;
      }
      return this.isHourValidFeedback === "";
    },
    isValueValidFeedback() {
       if (!this.valueEdit) {
        return null;
      }
      if(this.value == this.valueEdit)
         return "New value is the same as the old one";

      let valueLen = this.valueEdit.length;
      if (valueLen < 1 || valueLen > 25) {
        return "The value is mandatory and must have between 1 and 25 digits";
      }
      if (this.valueEdit < this.minVal || this.valueEdit > this.maxVal) {
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
  },
  created() {
    this.getBiomedicMeasure();
  },
  methods: {
    onContext(ctx) {
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.dateEdit = ctx.selectedFormatted
    },
    getBiomedicMeasure() {
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
        this.biomedicDataTypes = entidade;
        this.$axios
          .$get(`/api/biomedicDataMeasures/${this.code}`)
          .then((entidade) => {
            this.entidade = [entidade];
            this.hour = entidade.hour;
            this.date = entidade.date;
            this.value = entidade.value;
            this.classification = entidade.classification;
            if(this.entidade[0].usernameRegister == this.$auth.user.sub){
              this.canEdit = true;
            }
            this.biomedicDataTypes.forEach((b) => {
              if (this.entidade[0].biomedicDataTypeCode === b.code) {
                this.entidade[0].biomedicDataType = b.name;
                this.entidade[0].normalMinValue = b.normalMinValue + " " + b.unitMeasure;
                this.entidade[0].normalMaxValue = b.normalMaxValue + " " + b.unitMeasure;
                this.entidade[0].minValue = b.minValue + " " + b.unitMeasure;
                this.entidade[0].maxValue = b.maxValue + " " + b.unitMeasure;
                this.entidade[0].value += " " + b.unitMeasure;
                this.normalMinValue = b.normalMinValue;
                this.normalMaxValue = b.normalMaxValue;
                this.minVal = b.minValue;
                this.maxVal = b.maxValue;

              }
            })
          })
            .catch((error)=>{
              if(error.response.status == 403){
                this.$router.push("./../biomedicMeasures");
                return;
              }
            });
      });
    },
    update() {
      this.biomedicDataMeasure = {};
      if (this.isDateValid) {
        this.biomedicDataMeasure.date = this.dateEdit;
      }
      if (this.isHourValid) {
        this.biomedicDataMeasure.hour = this.hourEdit;
      }
      if (this.isValueValid) {
        this.biomedicDataMeasure.value = this.valueEdit;
      }

      if (Object.keys(this.biomedicDataMeasure).length === 0) {
        this.$toast.error("Error - Nothing to update").goAway(3000);
        return;
      }

      this.$axios
        .$put(
          `/api/biomedicDataMeasures/${this.code}`,
          this.biomedicDataMeasure
        )
        .then((response) => {

          this.$toast.info("Biomedic data Measure "+response.code+" updated succesfully!").goAway(3000);

          this.biomedicDataMeasure = {};
          this.date = null;
          this.hour = null;
          this.value = null;
          this.getBiomedicMeasure();
        })
        .catch((response) => {
          this.$toast.info(`Could not update Biomedic data Measure ` + response).goAway(3000);
          this.getPatient();
        });

    },
  },
};
</script>
