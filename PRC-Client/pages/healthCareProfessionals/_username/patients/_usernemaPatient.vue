<template>
  <div>
    <NavBar v-if="!isPatient"></NavBar>
    <NavBar v-else></NavBar>
    <b-container class="mt-3">
       <h3>Patients</h3>

      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
      <div class="text-center mt-3">
        <b-button v-b-modal.modal-1 class="justify-content-center"
          >Edit</b-button
        >
      </div>
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
          <p>{{ isNameValidFeedback }}</p>
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
          <p>{{ isbirthDateValidFeedback }}</p>
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
            placeholder="Enter email"
          />
          <p>{{ isEmailValidFeedback }}</p>
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
            placeholder="Enter contact"
          />
          <p>{{ isContactValidFeedback }}</p>
        </div>
      </b-modal>
      <!---------------------------------------------PRESCRIPTIONS ---------------------------------------------->

    <h3 class="mt-3">Prescribed Recipes ({{ tableLength }})</h3>
     <b-table
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
            <b-button :to="`/healthCareProfessionals/${username}/prescriptions/${row.item.code}`" variant="info">
              <font-awesome-icon icon="eye" /> Details
            </b-button>
          </template>
        </b-table>
         <b-pagination
        class="fixed-bottom justify-content-center"
        v-model="currentPagePaginate"
        :total-rows="rows"
        :per-page="perPage"
        aria-controls="table"
      ></b-pagination>
 </b-container>
    </div>
</template>



<script>
export default {
  data() {
    return {
      fields: [
        "username",
        "name",
        "birthDate",
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
      entidade: [],
      state: true,
      name: null,
      email: null,
      birthDate: null,
      contact: null,
      patientPrescriptions: [],
      currentPagePaginate: 1,
      perPage: 6,
    };
  },
  props: {
    url: String,
  },
  computed: {
    username() {
      return this.$auth.user.sub;
    },
    isPatient() {
      return this.$auth.user.groups.includes("Patient");
    },
    tableLength: function () {
      return this.patientPrescriptions.length;
    },
    username() {
      return this.$route.params.usernemaPatient;
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
      return true;
    },
    rows() {
      return this.patientPrescriptions.length;
    },
  },
  created() {
    console.log(this.$auth.user.sub)
    this.getPatient();
  },
  methods: {
    getPatient() {
      this.$axios.$get(`/api/patients/${this.username}`).then((entidade) => {
        this.entidade = [entidade];
      });
      this.$axios
        .$get(
          `/api/healthcareProfessionals/${this.$auth.user.sub}/patients/${this.username}/prescriptions`
        )
        .then((response) => {
          console.log([response]);
          this.patientPrescriptions = response;
        });
    },
    update() {
      this.$axios
        .$put(`/api/patients/${this.username}`, {
          name: this.name,
          email: this.email,
          contact: this.contact,
          birthDate: this.birthDate,
        })
        .then(() => {
          this.name = null;
          this.email = null;
          this.contact = null;
          this.birthDate = null;
          this.$toast.info(`Patient ${this.username}  updated!`).goAway(3000);

          this.getPatient();
        });
    },
  },
};
</script>
