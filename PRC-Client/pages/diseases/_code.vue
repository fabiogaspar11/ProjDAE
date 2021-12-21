<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
    </div>
    <b-row class="justify-content-md-center">
      <b-col col lg="2" >
        <div class="d-flex justify-content-center">
          <b-button v-b-modal.modal-1>Edit</b-button>
        </div>
          <b-modal id="modal-1" title="Edit" @ok="update()">
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">Name</span>
              </div>
              <input
                v-model="name"
                type="text"
                class="form-control"
                aria-describedby="basic-addon1"
              />
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">BirthDate</span>
              </div>
              <input
                v-model="birthDate"
                type="text"
                class="form-control"
                aria-describedby="basic-addon1"
              />
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">Email</span>
              </div>
              <input
                v-model="email"
                type="text"
                class="form-control"
                aria-describedby="basic-addon1"
              />
            </div>

            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text">Contact</span>
              </div>
              <input
                v-model="contact"
                type="text"
                class="form-control"
                aria-describedby="basic-addon1"
              />
            </div>
          </b-modal>
      </b-col>
    </b-row>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table v-if="patients.length" striped hover :items="patients" :fields="fieldsPatient">
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
    <div>
      Patient:
      <select v-model="username">
        <template v-for="patient in patientsAll">
          <option v-if="isExist(patient)" :key="patient.username" :value="patient.username">
            {{ patient.name }}
          </option>
        </template>
      </select>
      <button @click.prevent="enroll">ADD PATIENT</button>
    </div>
    <div>
      Patient:
      <select v-model="username">
        <template v-for="patient in patients">
          <option :key="patient.username" :value="patient.username">
            {{ patient.name }}
          </option>
        </template>
      </select>
      <button @click.prevent="unroll">REMOVE PATIENT</button>
    </div>


  </div>
</template>



<script>
export default {
  data() {
    return {
      fields: [
        "code",
        "name"
      ],
      fieldsPatient:[
        "username",
        "name",
        "birthDate",
        "healthNumber",
        "show_details",
      ],
      disease: {},
      entidade: [],
      patients: [],
      patientsAll:[],
      state: true,
      username: null
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
    },

  },
  created() {
    this.$axios.$get(`/api/diseases/${this.code}`)
      .then(disease =>
        this.disease = disease || {})
      .then(() => this.$axios.$get(`/api/diseases/${this.code}/patients`))
      .then(patients => this.patients = patients)
    this.$axios.$get(`/api/diseases`)
      .then(entidade =>
        this.entidade = entidade
      )
    this.$axios.$get(`/api/patients`)
      .then(patients =>
        this.patientsAll = patients
      )
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/patients/${this.username}`).then(()=>{
        this.$router.push("/patients")
      })
    },
    update() {
      this.$axios
        .$put(`/api/patients/${this.username}`, {
          name: this.name,
          email: this.email,
          contact: this.contact,
          bhirtDate: this.birthDate,
        })
        .then(() => {
          this.name = null;
          this.email = null;
          this.contact = null;
          this.bhirtDate = null;
          this.$axios
            .$get(`/api/patients/${this.username}`)
            .then((entidade) => {
              this.entidade = [entidade];
            });
        });
    },
    isExist: function (patient){
      for (let studentInSubject of this.patients){
        if (studentInSubject.username === patient.username){
          return false
        }
      }
      return true
    },
    enroll() {
      this.$axios.$post(`/api/diseases/${this.code}/${this.username}`, {
        code: this.code,
        username: this.username
      })
      this.$axios.get(`/api/diseases/${this.code}`)
        .then(() => this.$axios.$get(`/api/diseases/${this.code}/patients`))
        .then(patients => this.patients = patients)
    },
    unroll() {
      this.$axios.delete(`/api/diseases/${this.code}/${this.username}`)
      this.$axios.get(`/api/diseases/${this.code}`)
        .then(() => this.$axios.$get(`/api/diseases/${this.code}/patients`))
        .then(patients => this.patients = patients)
    },
  },
};
</script>
