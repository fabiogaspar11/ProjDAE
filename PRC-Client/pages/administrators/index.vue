<template>
  <div>
    <NavBar></NavBar>
    <b-container class="mt-3">
      <h3>Administrators ({{ tableLength }})</h3>
      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>

      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New Administrator
        </b-button>
      </div>

      <b-modal
        id="modal-1"
        title="New Administrator"
        @ok.prevent="createAdministrator()"
      >
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
          <span class="input-group-text">Password</span>
          <b-input
            required
            v-model.trim="password"
            type="password"
            :state="isPasswordValid"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your password"
          />
          <p>{{ isPasswordValidFeedback }}</p>
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
            aria-describedby="basic-addon1 "
          />
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

      <div class="mt-3">
        <b-table
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
              :to="`/administrators/${row.item.username}`"
              variant="info"
            >
              <font-awesome-icon icon="eye" /> Details
            </b-button>

            <b-button
              v-b-modal.modal-3
              variant="danger"
              @click="remove(row.item.username, row.item.name)"
            >
              <font-awesome-icon icon="trash" /> Remove
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
      </div>
    </b-container>
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
      entidade: [],
      name: null,
      birthDate: null,
      contact: null,
      email: null,
      password: null,
      healthNumber: null,
      filter: null,
      totalRows: null,
      perPage: 6,
      currentPagePaginate: 1,
    };
  },
  methods: {
    getAdministrators() {
      this.$axios.$get("/api/administrators").then((entidade) => {
        this.entidade = entidade;
        let i=0;
         entidade.forEach(a => {
          if(a.username==this.$auth.user.sub){
            console.log(a.username)
             this.entidade.splice(i,1);
          }
          i++;
        });
      });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    remove(username, name) {
      this.$axios.$delete(`/api/administrators/${username}`).then(() => {
        this.$toast
          .info(`Administrator ${this.username} was successfully removed!`)
          .goAway(3000);

        this.getAdministrators();
      });
    },
    createAdministrator() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);

        return;
      }
      this.$axios
        .$post("/api/administrators", {
          password: this.password,
          email: this.email,
          birthDate: this.birthDate,
          name: this.name,
          contact: this.contact,
          healthNumber: this.healthNumber,
        })
        .then(() => {
          this.$toast
            .success("Administrator " + this.name + " created succesfully")
            .goAway(3000);
          this.password = null;
          this.name = null;
          this.birthDate = null;
          this.contact = null;
          this.email = null;
          this.healthNumber = null;
          this.getAdministrators();
        })
        .catch((error) => {
          this.$toast
            .error("Error when creating Adminstrator: " + error.response.data)
            .goAway(3000);
        });
    },
  },
  created() {
    this.getAdministrators();
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    rows() {
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

    isPasswordValidFeedback() {
      if (!this.password) {
        return null;
      }
      let passwordLen = this.password.length;
      if (passwordLen < 3 || passwordLen > 255) {
        return "Password is too short, lenght must be between 3 and 255";
      }
      return "";
    },
    isPasswordValid() {
      if (this.isPasswordValidFeedback === null) {
        return null;
      }
      return this.isPasswordValidFeedback === "";
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
    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }
      if (!this.isEmailValid) {
        return false;
      }
      if (!this.isPasswordValid) {
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
  },
};
</script>
