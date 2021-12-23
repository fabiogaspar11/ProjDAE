<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <template>
        <div>

          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
      <br>
        <b-list-group>
          <b-list-group-item>
            <b>Title:</b> {{ this.title }}
          </b-list-group-item>
          <b-list-group-item>
            <b>Treatment Information:</b> {{ this.treatmentInfo }}
          </b-list-group-item>
          <b-list-group-item> <b>Observations:</b>
            <div v-if="this.observations != ''">{{ this.observations }}</div>
            <div v-else>There are no observations registered</div>
          </b-list-group-item>
        </b-list-group>
        <br>
      <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>

      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Name</span>
          </div>
          <b-input
            v-model.trim="name"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your name"
            :state="isNameValid"
          />
        </div>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">BirthDate</span>
          </div>
          <b-input
            v-model.trim="birthDate"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="dd/mm/yyyy"
            :state="isbirthDateValid"
          />
        </div>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Email</span>
          </div>
          <b-input
            v-model.trim="email"
            ref="email"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your email"
            :state="isEmailValid"
          />
        </div>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Contact</span>
          </div>
          <b-input
            v-model.trim="contact"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your contact"
            :state="isContactValid"
          />
        </div>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Password</span>
          </div>
          <b-input
            v-model.trim="password"
            type="password"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your password"
            :state="isPasswordValid"
          />
        </div>
      </b-modal>
    </div>
  </div>
</template>



<script>
export default {
  data() {
    return {
      entidade: [],
      state: true,
      title: null,
      observations: null,
      treatmentInfo: null,
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
    this.$axios.$get(`/api/prescriptions/${this.code}`).then((entidade) => {
      this.title = entidade.title;
      this.observations = entidade.observations;
      this.treatmentInfo = entidade.treatmentInfo;
      this.entidade = [entidade];
    });
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/administrators/${this.username}`).then(() => {
        this.$router.go(-1);
      });
    },
    update() {
      let adminUpdated = {};
      if (this.name != null) {
        adminUpdated.name = this.name;
      }
      if (this.email != null) {
        adminUpdated.email = this.email;
      }
      if (this.password != null) {
        adminUpdated.password = this.password;
      }
      if (this.contact != null) {
        adminUpdated.contact = this.contact;
      }
      if (this.birthDate != null) {
        adminUpdated.birthDate = this.birthDate;
      }

      this.$axios
        .$put(`/api/administrators/${this.username}`, adminUpdated)
        .then(() => {
          alert(`Administrator ${this.username}  updated!`);
          this.$router.go(0);
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
  },
};
</script>
