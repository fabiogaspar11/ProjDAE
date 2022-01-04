<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
      <h3>Details</h3>
      <template>
          <b-table small striped hover :items="entidade" :fields="fields"></b-table>
      </template>
      <br />
      <b-list-group>
        <b-list-group-item>
          <b>Title:</b> {{ this.currentTitle }}
        </b-list-group-item>
        <b-list-group-item>
          <b>Treatment Information:</b> {{ this.currentTreatmentInfo }}
        </b-list-group-item>
        <b-list-group-item>
          <b>Observations:</b>
          <div v-if="this.currentObservations != ''">
            {{ this.currentObservations }}
          </div>
          <div v-else>There are no observations registered yet</div>
        </b-list-group-item>
      </b-list-group>
      <br />
      <b-button v-if="usernameHealthcareProfessional == username" v-b-modal.modal-1 class="text-center">Edit</b-button>

      <b-modal id="modal-1" title="Edit Prescription" @ok.prevent="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Title</span>
          <b-input
            v-model="title"
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
            v-model="treatmentInfo"
            :state="isTreatmentInfoValid"
            placeholder="Treatment information..."
            rows="3"
            max-rows="6"
          ></b-form-textarea>

        </div>
          <p>{{ isTreatmentInfoValidFeedback }}</p>
        <br />
        <div>
          <span>Observations:</span>
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
          <p>{{ isDateValidFeedback }}</p>
      </b-modal>
    </b-container>
  </div>
</template>



<script>
export default {
  middleware: "isHealthcareProfessionalAccessingHisData",
  data() {
    return {
      entidade: [],
      state: true,
      treatmentInfo: null,
      name: null,
      title: null,
      observations: "",
      expireDate: null,
      isPharmacological: null,
      usernamePatient: null,
      usernameHealthcareProfessional: null,
      fields: [
        { key: "code", label: "Code" },
        { key: "emissionDate", label: "Emission Date" },
        { key: "expireDate", label: "Expire Date" },
        { key: "isPharmacological", label: "Pharmacological" },
        { key: "usernamePatient", label: "Patient username" },
        {
          key: "usernameHealthcareProfessional",
          label: "Healthcare professional",
        },
      ],
      options: [
        { value: null, text: "Please select an option" },
        { value: "No", text: "No" },
        { value: "Yes", text: "Yes" },
        { value: "Both", text: "Both" },
      ],
      currentTitle: null,
      currentTreatmentInfo: null,
      currentObservations: null,
      currentExpireDate: null,
      currentIsPharmacological: null,
    };
  },
  props: {
    url: String,
  },
  computed: {
    username(){
      return this.$auth.user.sub;
    },
    code() {
      return this.$route.params.code;
    },
    isTitleValid() {
      if (this.isTitleValidFeedback === null) {
        return null;
      }
      let length = this.title.length;
      if (length < 3 || length > 25) {
        return "The title is mandatory and must have between 3 and 25 letters";
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
       let length = this.treatmentInfo.length;
      if (length < 1 || length > 25) {
        return "The treatment information is mandatory and must have between 1 and 25 letters";
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
    isOptionValid() {
      if (this.isOptionValidFeedback == null) {
        return null;
      }
      return "";
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
  },
  created() {
    this.getData();
  },
  methods: {
    update() {
      let prescriptionUpdate = {};
      if (
        this.title != this.currentTitle &&
        this.title != null &&
        this.title != ""
      ) {
        prescriptionUpdate.title = this.title;
      }
      if (
        this.treatmentInfo != this.currentTreatmentInfo &&
        this.treatmentInfo != null &&
        this.treatmentInfo != ""
      ) {
        prescriptionUpdate.treatmentInfo = this.treatmentInfo;
      }
      if (
        this.expireDate != this.currentExpireDate &&
        this.expireDate != null &&
        this.expireDate != "" && this.isDateValid
      ) {
        prescriptionUpdate.expireDate = this.expireDate;
      }
      if (
        this.isPharmacological != this.currentIsPharmacological &&
        this.isPharmacological != null &&
        this.isPharmacological != ""
      ) {
        prescriptionUpdate.isPharmacological = this.isPharmacological;
      }
      if (this.observations != this.currentObservations) {
        prescriptionUpdate.observations = this.observations;
      }

      if (Object.keys(prescriptionUpdate).length != 0) { //apenas fazer pedido no caso do objecto ter algo para atualizar
        this.$axios
          .$put(`/api/prescriptions/${this.code}`, prescriptionUpdate)
          .then(() => {
            this.$toast.info(`Prescription ${this.code} updated!`).goAway(3000);

            this.expireDate = null;
            this.isPharmacological = null;
            this.observations = "";
            this.title = null;
            this.treatmentInfo = null;
            this.usernamePatient = null;
            this.usernameHealthcareProfessional = null;
            this.getData();
          })
          .catch((error) => {
            this.$toast.error(`Error updating Prescription - ${error.response.data}`).goAway(3000);

          });
      }
      this.$toast.info("Nothing to update - Fields are all missing or equal to current data").goAway(3000);

    },
    getData() {
      this.$axios.$get(`/api/prescriptions/${this.code}`).then((entidade) => {
        this.entidade = [entidade];
        this.currentTitle = entidade.title;
        this.currentTreatmentInfo = entidade.treatmentInfo;
        this.currentExpireDate = entidade.expireDate;
        this.currentIsPharmacological = entidade.isPharmacological;
        this.currentObservations = entidade.observations;
        this.usernameHealthcareProfessional = entidade.usernameHealthcareProfessional;
      })
      .catch((error)=>{
      if(error.response.status == 403 || error.response.status == 404){
            this.$router.push("./../prescriptions");
            return;
          }
        });
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
