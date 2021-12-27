<template>
  <div>
    <NavBar></NavBar>
    <h1 style="margin-top: 4%" class="d-flex justify-content-center"> Diseases </h1>
    <div class="d-flex justify-content-center" style="margin-top: 2%">
      <template>
        <div>
          <b-table striped hover :items="disease" :fields="fields"></b-table>
        </div>
      </template>
    </div>

    <div class="d-flex justify-content-center">
      <b-button v-b-modal.modal-1>Edit</b-button>
      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input v-model.trim="name" type="text" :state="isNameValid" placeholder="Enter name" class="form-control" required aria-describedby="basic-addon1"/>
          <p>{{isNameValidFeedback}}</p>
        </div>
      </b-modal>
    </div>

    <h3 style="margin-top: 4%" class="d-flex justify-content-center"> Patients </h3>
    <div class="d-flex justify-content-center">
      <template>
        <div>
          <b-table v-if="patients !== []" striped hover :items="patients" :fields="fieldsPatient">
            <template #cell(show_details)="row">
              <b-button size="sm" @click="row.toggleDetails" class="mr-2">
                {{ row.detailsShowing ? "Hide" : "Show" }} Details
              </b-button>
            </template>

            <template #row-details="row">
              <b-card>
                <b-row class="mb-2">
                  <b-col sm="3" class="text-sm-right"><b>Email:</b></b-col>
                  <b-col>{{ row.item.email }}</b-col>
                </b-row>

                <b-row class="mb-2">
                  <b-col sm="3" class="text-sm-right"><b>Contact:</b></b-col>
                  <b-col>{{ row.item.contact }}</b-col>
                </b-row>
              </b-card>
            </template>
          </b-table>
          <p v-else>No patients diagnosed with this disease</p>
        </div>
      </template>
    </div>

    <b-container >
      <b-row class="d-flex justify-content-center">
        <b-col sm="2">
          <p> Add Patient: </p>
        </b-col>
        <b-col sm="2">
          <b-form-select size="sm" v-model="username">
            <template v-for="patient in patientsAll">
              <option v-if="isExist(patient)" :key="patient.username" :value="patient.username">
                {{ patient.name }}
              </option>
            </template>
          </b-form-select>
        </b-col>
        <b-col sm="2">
          <b-button variant="info" @click.prevent="enroll">Add patient</b-button>
        </b-col>
      </b-row>

      <b-row class="d-flex justify-content-center">
        <b-col sm="2">
          <p> Remove Patient: </p>
        </b-col>
        <b-col sm="2">
          <b-form-select size="sm" v-model="username">
            <template v-for="patient in patients">
              <option :key="patient.username" :value="patient.username">
                {{ patient.name }}
              </option>
            </template>
          </b-form-select>
        </b-col>
        <b-col sm="2">
          <b-button variant="danger" @click.prevent="unroll">Remove Patient</b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>



<script>
export default {
  data() {
    return {
      fields: ["code", "name"],
      fieldsPatient: [
        "username",
        "name",
        "birthDate",
        "healthNumber",
        "show_details",
      ],
      disease: {},
      patients: [],
      patientsAll: [],
      username: null,
      name: null,
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
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
    isFormValid() {
      if (!this.isNameValid) {
        return false;
      }
      return true;
    },
  },
  created() {
    this.getDisease();
    this.$axios
      .$get(`/api/patients`)
      .then((patients) => (this.patientsAll = patients));
  },
  methods: {
    getDisease() {
      this.$axios.$get(`/api/diseases/${this.code}`).then((disease) => {
        this.disease = [disease];
        if (disease.patientDTOS.length == 0) {
          return;
        }
        disease.patientDTOS.forEach((patient) => {
          this.patients.push(patient);
        });
      });
    },
    remove() {
      this.$axios.$delete(`/api/patients/${this.username}`).then(() => {
        this.$router.push("/patients");
      });
    },
    update() {
      if (!this.isFormValid) {
           this.$toast.error("Fields are invalid - Correct them first!").goAway(3000);
        return;
      }

      this.$axios
        .$put(`/api/diseases/${this.code}`, {
          name: this.name,
        })
        .then(() => {
          this.$toast.info("Disease " + this.name + " updated succesfully").goAway(3000);

          this.name = null;
          this.getDisease();
        });
    },
    isExist: function (patient) {
      for (let studentInSubject of this.patients) {
        if (studentInSubject.username === patient.username) {
          return false;
        }
      }
      return true;
    },
    enroll() {
      this.$axios.$put(`/api/diseases/${this.code}/${this.username}`);
      this.$axios
        .get(`/api/diseases/${this.code}`)
        .then(() => this.$axios.$get(`/api/diseases/${this.code}/patients`))
        .then((patients) => (this.patients = patients));
    },
    unroll() {
      this.$axios.delete(`/api/diseases/${this.code}/${this.username}`);
      this.$axios
        .get(`/api/diseases/${this.code}`)
        .then(() => this.$axios.$get(`/api/diseases/${this.code}/patients`))
        .then((patients) => (this.patients = patients));
    },
  },
};
</script>
