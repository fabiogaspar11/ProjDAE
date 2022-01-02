<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
       <h3>Patients ({{ tableLength }})</h3>
      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>

      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="btn btn-outline-primary">
          <font-awesome-icon icon="plus" /> Create
        </b-button>
        <b-button v-b-modal.modal-2 variant="primary">
          <font-awesome-icon icon="book" /> Associate
        </b-button>

        <download-excel
          class="btn btn-default"
          :data="entidade"
          :fields="json_fields"
          worksheet="Patients"
          :name="'patients.'+typeExcel"
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
          class="w-25 text-left"
        ></b-form-file>
      </div>
    </b-container>
    <b-modal id="modal-1" title="New Patient" @ok.prevent="createPatient()">
      <div class="input-group mb-4">
        <span class="input-group-text">Name</span>
        <b-input
          required
          v-model.trim="name"
          type="text"
          :state="isNameValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="Enter your name"
        />
        <p>{{ isNameValidFeedback }}</p>
      </div>
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
          placeholder="Enter your email"
        />
        <p>{{ isEmailValidFeedback }}</p>
      </div>
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
              readonly
            ></b-form-datepicker>
          </b-input-group-append>
        <p>{{ isbirthDateValidFeedback }}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Contact</span>
        <b-input
          required
          v-model.trim="contact"
          type="number"
          :state="isContactValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="Enter your contact"
        />
        <p>{{ isContactValidFeedback }}</p>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Health Number</span>
        <b-input
          required
          v-model.trim="healthNumber"
          type="number"
          :state="isHealthNumberValid"
          class="form-control"
          aria-describedby="basic-addon1"
          placeholder="Enter your health number"
        />
        <p>{{ isHealthNumberValidFeedback }}</p>
      </div>
    </b-modal>

    <b-modal ok-only ok-title="Close" id="modal-2" title="Associate Patient">
      <b-container>
        <div class="overflow-auto">
          <b-form-input
            class="mb-3"
            v-model="filterAssociateds"
            type="search"
            placeholder="Search..."
          >
          </b-form-input>

          <b-table
            striped
            hover
            :items="this.patientsAll"
            :fields="fieldsPatient"
            :filter="filterAssociateds"
            @filtered="search"
            id="tableAssociateds"
            :current-page="currentPagePaginateSecondary"
            :per-page="perPage"
          >
            <template v-slot:cell(operations)="row">
              <b-button
                v-if="isExist(row.item)"
                @click.prevent="dissociate(row.item.healthNumber)"
                variant="dark"
              >
                <font-awesome-icon icon="times" /> Remove
              </b-button>
              <b-button
                v-else
                @click.prevent="associate(row.item.healthNumber)"
                variant="success"
              >
                <font-awesome-icon icon="check" /> Associate
              </b-button>
            </template>
          </b-table>
          <b-pagination
            class="justify-content-center"
            v-model="currentPagePaginateSecondary"
            :total-rows="rows"
            :per-page="perPage"
            aria-controls="tableAssociateds"
          ></b-pagination>
        </div>
      </b-container>
    </b-modal>

    <b-container class="mt-1">
      <b-table
        id="tablePrincipal"
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
          <b-button :to="`./patients/${row.item.username}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>
        </template>
      </b-table>

      <b-pagination
        class="fixed-bottom justify-content-center"
        v-model="currentPagePaginatePrincipal"
        :total-rows="rowsPrincipal"
        :per-page="perPage"
        aria-controls="tablePrincipal"
      ></b-pagination>
    </b-container>

  </div>
</template>
<script>
import NavBar from "/components/NavBar.vue";
import XLSX from "xlsx"
import moment from 'moment';

export default {
  components: {
    NavBar,
    XLSX
  },
  data() {
    return {
      fields: [
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
        {
          key: "birthDate",
          label: "Birthdate",
          sortable: true,
          sortDirection: "desc",
        },
        {
          key: "healthNumber",
          label: "Health Number",
          sortable: true,
          sortDirection: "desc",
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
      name: null,
      birthDate: null,
      contact: null,
      email: null,
      healthNumber: null,
      filter: null,
      filterAssociateds: null,
      totalRows: null,
      patientsAll: [],
      patients: [],
      usernamePatient: null,
      fieldsPatient: ["healthNumber", "name", "operations"],
      perPage: 5,
      currentPagePaginatePrincipal: 1,
      currentPagePaginateSecondary: 1,
      json_fields: {
        Name: "name",
        BirthDate: "birthDate",
        HealthNumber: "healthNumber",
        Contact: "contact",
        Email: "email",
      },
      typeExcel:"",
      file: null,
    };
  },
  computed: {
    username() {
      return this.$auth.user.sub;
    },
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
    isContactValidFeedback() {
      if (!this.contact) {
        return null;
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
    isHealthNumberValidFeedback() {
      if (!this.healthNumber) {
        return null;
      }
      let healthNumberString = this.healthNumber.toString();
      let healthNumberLen = healthNumberString.length;
      if (healthNumberLen != 9) {
        return "The healh number is invalid - the number must have 9 digits";
      }
      return "";
    },
    isHealthNumberValid() {
      if (this.isHealthNumberValidFeedback === null) {
        return null;
      }
      return this.isHealthNumberValidFeedback === "";
    },

    isEmailValidFeedback() {
      if (!this.email) {
        return null;
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
      return "The date is bigger than todays date";
    },
    isbirthDateValid() {
      if (this.isbirthDateValidFeedback === null) {
        return null;
      }
      return this.isbirthDateValidFeedback === "";
    },
    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }
      if (!this.isEmailValid) {
        return false;
      }
      if (!this.isbirthDateValid) {
        return false;
      }
      if (!this.isContactValid) {
        return false;
      }
      if (!this.isHealthNumberValid) {
        return false;
      }
      return true;
    },
    rows() {
      return this.patientsAll.length;
    },
    rowsPrincipal() {
      return this.entidade.length;
    },
  },

  created() {
    this.getAllPatients();
  },
  methods: {
    search(filteredItems) {
      this.totalRows = filteredItems.length;
    },
    getAllPatients() {
      this.$axios
        .$get(`api/healthcareProfessionals/${this.$auth.user.sub}/patients`)
        .then((entidade) => {
          this.entidade = entidade;
          this.patients = entidade;
        });
      this.$axios.$get(`/api/patients`).then((response) => {
        this.patientsAll = response;
      });
    },
    createPatient() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }
      this.$axios
        .$post("/api/patients", {
          email: this.email,
          birthDate: this.birthDate,
          name: this.name,
          contact: this.contact,
          healthNumber: this.healthNumber,
        })
        .then((response) => {
          this.$toast
            .success("Patient " + this.name + " created succesfully")
            .goAway(3000);
          this.associate(this.healthNumber)
          this.name = null;
          this.birthDate = null;
          this.contact = null;
          this.email = null;
          this.healthNumber = null;
          this.getAllPatients();
        })
        .catch((error) => {
          this.$toast
            .error("Error when creating Patient: " + error.response.data)
            .goAway(3000);
        });
    },

    isExist: function (patient) {
      for (let item of this.patients) {
        if (item.username === patient.username) {
          return true;
        }
      }
      return false;
    },
    associate(usernamePatient) {
      usernamePatient = 'P'+usernamePatient;
      this.$axios
        .$put(
          `/api/healthcareProfessionals/${this.$auth.user.sub}/AddPatient/${usernamePatient}`
        )
        .then(() => {
          this.getAllPatients();
        });
    },
    dissociate(usernamePatient) {
      usernamePatient = 'P' + usernamePatient;
      this.$axios
        .put(
          `/api/healthcareProfessionals/${this.$auth.user.sub}/RemovePatient/${usernamePatient}`
        )
        .then(() => {
          this.getAllPatients();
        });
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
            let name = data[i][0]
            console.log("DATA ANTES: " + data[i][1])
            let birthDate = data[i][1].toString()
            if (!birthDate.includes("/")){
              birthDate = this.convertToDate(data[i][1])
            }
            let healthNumber = data[i][2]
            let contact = data[i][3]
            let email = data[i][4]
             this.$axios.$post("/api/patients", {
                email: email,
                birthDate: birthDate,
                name: name,
                contact: contact,
                healthNumber: healthNumber,
              })
              .then((response) => {
                this.$toast.success("Patient " + name + " created succesfully").goAway(3000);
                this.associate(healthNumber)
              })
              .catch((error) => {
                this.$toast
                  .error("Error when creating Patient: " + error.response.data)
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
  },
};
</script>
