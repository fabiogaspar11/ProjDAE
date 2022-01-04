<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
      <h3>Patient</h3>
      <template>
        <div>
          <b-table small striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
      <div class="text-center mt-3">
        <b-button v-b-modal.modal-1 class="justify-content-center"
          >Edit</b-button
        >
        <b-button
          v-b-modal.modal-2
          class="justify-content-center"
          variant="primary"
          >Diseases</b-button
        >
      </div>
     <b-modal ok-only ok-title="Close" id="modal-2" title="Diseases">
        <b-container>
          <div class="overflow-auto">
            <div v-if="diseasesAll.length !=0">
            <b-form-input
              class="mb-3"
              v-model="filterDiseases"
              type="search"
              placeholder="Search..."
            >
            </b-form-input>
            <b-table
              small
              striped
              hover
              v-if="diseasesAll.lenght !=0"
              :items="this.diseasesAll"
              :fields="fieldsDiseases"
              :filter="filterDiseases"
              @filtered="search"
              id="tableDiseasesAll"
              :current-page="currentPagePaginateDiseasesAll"
              :per-page="perPage"
            >
              <template v-slot:cell(operations)="row">
                <b-button
                  v-if="isExist(row.item)"
                  @click.prevent="undiagnose(row.item.code)"
                  variant="dark"
                >
                  <font-awesome-icon icon="times" /> Undiagnose
                </b-button>
                <b-button
                  v-else
                  @click.prevent="diagnose(row.item.code)"
                  variant="success"
                >
                  <font-awesome-icon icon="check" /> Diagnose
                </b-button>
              </template>
            </b-table>
            <b-pagination
              class="justify-content-center"
              v-model="currentPagePaginateDiseasesAll"
              :total-rows="diseasesAll.length"
              :per-page="perPage"
              aria-controls="tableDiseasesAll"
            ></b-pagination>
            </div>
             <div v-if="this.diseasesAll.length == 0" class="w-75 mx-auto alert alert-info">
        No diseases created yet
      </div>
          </div>
        </b-container>
      </b-modal>

      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input
            required
            v-model.trim="name"
            type="text"
            :state="isNameValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter name"
          />
        </div>
        <p>{{ isNameValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Birthdate</span>
          <b-input
            required
            v-model.trim="birthDate"
            type="text"
            :state="isbirthDateValid"
            placeholder="dd/mm/yyyy"
            class="form-control"
            aria-describedby="basic-addon1"
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
        <p>{{ isbirthDateValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Email</span>
          <b-input
            required
            v-model.trim="email"
            ref="email"
            type="email"
            :state="isEmailValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter email"
          />
        </div>
        <p>{{ isEmailValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Contact</span>
          <b-input
            required
            v-model.trim="contact"
            type="number"
            :state="isContactValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter contact"
          />
        </div>
        <p>{{ isContactValidFeedback }}</p>
        <div class="input-group mb-4 d-flex justify-content-center" >
          <span class="input-group-text">Gender</span>
          <b-form-select v-model="gender" :options="optionsGender"></b-form-select>
          <p>{{isGenderValidFeedback}}</p>
        </div>
      </b-modal>

      <b-container class="mt-1">
        <h3 class="mt-3">Diseases ({{ this.diseases.length }})</h3>
        <div v-if="diseases.length!=0">
        <b-table
          small
          id="tableDiseases"
          :per-page="perPageDiseases"
          :current-page="currentPagePaginateDiseases"
          :items="this.diseases"
          :fields="fieldsDiseasesTable"
          striped
          responsive="sm"
          :filter="filterDiseases"
          @filtered="search"
        >
        </b-table>

        <b-pagination
          class="justify-content-center"
          v-model="currentPagePaginateDiseases"
          :total-rows="diseases.length"
          :per-page="perPageDiseases"
          aria-controls="tableDiseases"
        ></b-pagination>
        </div>
         <div v-if="this.diseases == 0" class="w-75 mx-auto alert alert-info">
        No diseases diagnosed yet
      </div>
      </b-container>

      <!---------------------------------------------PRESCRIPTIONS ---------------------------------------------->

      <h3 class="mt-5">Prescribed Recipes ({{ tableLength }})</h3>
      <b-table
        small
        v-if="this.tableLength != 0"
        class="mt-1"
        id="table"
        :per-page="perPage"
        :current-page="currentPagePaginate"
        :items="this.patientPrescriptions"
        :fields="fieldsPrescriptions"
        striped
        responsive="sm"
      >
        <template v-slot:cell(operations)="row">
          <b-button
            :to="`/healthCareProfessionals/${$auth.user.sub}/prescriptions/${row.item.code}`"
            variant="info"
          >
            <font-awesome-icon icon="eye" /> Details
          </b-button>
        </template>
      </b-table>
      <b-pagination
        v-if="this.tableLength != 0"
        class="justify-content-center"
        v-model="currentPagePaginate"
        :total-rows="tableLength"
        :per-page="perPage"
        aria-controls="table"
      ></b-pagination>
      <br />
      <div v-if="this.tableLength == 0" class="w-75 mx-auto alert alert-info">
        No Prescriptions created yet
      </div>


 <!---------------------------------------------BIOMEDIC MEASURES ---------------------------------------------->

      <h3 class="mt-5">Biomedic Measures ({{ tableLengthBiomedicMeasures }})</h3>
      <b-table
        small
        v-if="this.tableLengthBiomedicMeasures != 0"
        class="mt-1"
        id="table"
        :per-page="perPage"
        :current-page="currentPagePaginateBiomedicMeasures"
        :items="this.patientBiomedicMeasures"
        :fields="fieldsBiomedicMeasures"
        striped
        responsive="sm"
      >
        <template v-slot:cell(operations)="row">
          <b-button
            :to="`/healthCareProfessionals/${$auth.user.sub}/biomedicMeasures/${row.item.code}`"
            variant="info"
          >
            <font-awesome-icon icon="eye" /> Details
          </b-button>
        </template>
      </b-table>
      <b-pagination
        v-if="this.tableLengthBiomedicMeasures != 0"
        class="justify-content-center"
        v-model="currentPagePaginateBiomedicMeasures"
        :total-rows="tableLengthBiomedicMeasures"
        :per-page="perPage"
        aria-controls="table"
      ></b-pagination>
      <br />
      <div v-if="this.tableLengthBiomedicMeasures == 0" class="w-75 mx-auto alert alert-info">
        No Biomedic Measures created yet
      </div>


    </b-container>
  </div>
</template>



<script>
export default {
  middleware: "isHealthcareProfessionalAccessingHisData",
  data() {
    return {
      fields: [
        "username",
        "name",
        "birthDate",
        "gender",
        "healthNumber",
        "contact",
        "email",
      ],
      fieldsPrescriptions: [
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
      fieldsBiomedicMeasures:[
        { key: "code", label: "Code" ,sortable: true, sortDirection: "desc" },
        { key: "usernamePatient", label: "Patient Username",sortable: true, sortDirection: "desc"  },
        { key: "value", label: "Value",sortable: true, sortDirection: "desc"  },
        { key: "date", label: "Date" ,sortable: true, sortDirection: "desc" },
        "operations",
      ],
      fieldsDiseases: ["code", "name", "operations"],
      fieldsDiseasesTable: ["code", "name"],
      diseasesAll: [],
      filterDiseases: null,
      entidade: [],
      state: true,
      diseases: [],
      name: null,
      email: null,
      birthDate: null,
      gender: null,
      contact: null,
      patientPrescriptions: [],
      patientBiomedicMeasures: [],
      currentPagePaginate: 1,
      currentPagePaginateDiseases:1,
      currentPagePaginateDiseasesAll:1,
      currentPagePaginateBiomedicMeasures:1,
      perPage: 5,
      perPageDiseases: 3,
      currentName: null,
      currentBirthDate: null,
      currentEmail: null,
      currentContact: null,
      optionsGender: [
        { value: null, text: 'Please select an option' },
        { value: 'Masculino', text: 'Masculino' },
        { value: 'Feminino', text: 'Feminino' },
      ]
    };
  },
  props: {
    url: String,
  },
  computed: {
    tableLength: function () {
      return this.patientPrescriptions.length;
    },
    tableLengthBiomedicMeasures(){
      return this.patientBiomedicMeasures.length
    },
    usernamePatient() {
      return this.$route.params.usernamePatient;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      if (this.name == this.currentName) {
        return "Name is equal to current name";
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
    isContactValidFeedback() {
      if (!this.contact) {
        return null;
      }
      if (this.contact == this.currentContact) {
        return "Contact is equal to current contact";
      }
      let contactString = this.contact.toString();
      let contactLen = contactString.length;
      if (contactLen != 9) {
        return "The contact is invalid - contact must have 9 digits exactly";
      }
      var phoneRegex = /^(9[0-9])([0-9]{7})?$/;
      return phoneRegex.test(contactString)
        ? ""
        : "Contact is invalid - Not in PT format";
    },
    isContactValid() {
      if (this.isContactValidFeedback === null) {
        return null;
      }
      return this.isContactValidFeedback === "";
    },

    isEmailValidFeedback() {
      if (!this.email) {
        return null;
      }
      if (this.email == this.currentEmail) {
        return "Email is equal to current email";
      }

      return this.$refs.email.checkValidity()
        ? ""
        : "Email is not valid - the email format must be like name@domain";
    },
    isEmailValid() {
      if (this.isEmailValidFeedback === null) {
        return null;
      }
      return this.isEmailValidFeedback === "";
    },
    isbirthDateValidFeedback() {
      if (!this.birthDate) {
        return null;
      }
      if (this.birthDate == this.currentBirthDate) {
        return "Birthdate is equal to current birthdate";
      }
      var date_regex =
        /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
      var currentdate = new Date();
      var dateSplitted = this.birthDate.split("/");
      var dateRegexValid = date_regex.test(this.birthDate);
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
      return "The birthdate date is bigger than todays date";
    },
    isbirthDateValid() {
      if (this.isbirthDateValidFeedback === null) {
        return null;
      }
      return this.isbirthDateValidFeedback === "";
    },
    isGenderValidFeedback() {
      if (!this.gender) {
        return null;
      }

      return "";
    },
    isGenderValid() {
      if (this.isGenderValidFeedback === null) {
        return null;
      }
      return this.isGenderValidFeedback === "";
    },
  },
  created() {
    this.getPatient();
  },
  methods: {
    diagnose(diseaseCode) {
      this.$axios
        .$put(`/api/patients/${this.usernamePatient}/addDisease/${diseaseCode}`)
        .then(() => {
          this.getPatient();
        });
    },
    undiagnose(diseaseCode) {
      this.$axios
        .put(`/api/patients/${this.usernamePatient}/removeDisease/${diseaseCode}`)
        .then(() => {
          this.getPatient();
        });
    },
    isExist: function (disease) {
      for (let item of this.diseases) {
        if (item.code === disease.code) {
          return true;
        }
      }
      return false;
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
    },
    getPatient() {
      this.$axios.$get(`/api/patients/${this.usernamePatient}`).then((entidade) => {
        this.entidade = [entidade];
        this.currentName = entidade.name;
        this.currentBirthDate = entidade.birthDate;
        this.currentEmail = entidade.email;
        this.currentContact = entidade.contact;
        this.$axios.$get(`/api/diseases`).then((response) => {
          this.diseasesAll = response;
        });
      })
      .catch((error)=>{
      if(error.response.status == 403 || error.response.status == 404){
            this.$router.push("./../patients");
            return;
          }
        });
      this.$axios
        .$get(`/api/patients/${this.usernamePatient}/diseases`)
        .then((response) => {
          this.diseases = response;
        });

      this.$axios
        .$get(`/api/patients/${this.usernamePatient}/prescriptions`)
        .then((response) => {
          this.patientPrescriptions = response;
        });

       this.$axios
        .$get(`/api/patients/${this.usernamePatient}/biomedicMeasures`)
        .then((response) => {
          this.patientBiomedicMeasures = response;
        });

    },
    update() {
      let patientUpdated = {};
      if (this.isNameValid) {
        patientUpdated.name = this.name;
      }
      if (this.isEmailValid) {
        patientUpdated.email = this.email;
      }
      if (this.isContactValid) {
        patientUpdated.contact = this.contact;
      }
      if (this.isbirthDateValid) {
        patientUpdated.birthDate = this.birthDate;
      }
      if (this.isGenderValid) {
        patientUpdated.gender = this.gender;
      }
      if (Object.keys(patientUpdated).length == 0) {
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }
      this.$axios
        .$put(`/api/patients/${this.usernamePatient}`, patientUpdated)
        .then(() => {
          this.name = null;
          this.email = null;
          this.contact = null;
          this.birthDate = null;
          this.gender = null;
          this.$toast.info(`Patient ${this.usernamePatient}  updated!`).goAway(3000);

          this.getPatient();
        })
         .catch(() => {
            this.$toast.error(`Patient ${this.usernamePatient} could not be updated!`).goAway(3000);
        });
    },
    onContext(ctx) {
       if(ctx.selectedDate == null){
        return null;
    }
      // The date formatted in the locale, or the `label-no-date-selected` string
      this.birthDate = ctx.selectedFormatted
    }
  },
};
</script>
