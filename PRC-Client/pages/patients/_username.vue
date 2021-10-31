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
    <div>
      <b-button v-b-modal.modal-1>Edit</b-button>

      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Name</span>
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
            <span class="input-group-text" id="basic-addon1">BirthDate</span>
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
            <span class="input-group-text" id="basic-addon1">Email</span>
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
            <span class="input-group-text" id="basic-addon1">Contact</span>
          </div>
          <input
            v-model="contact"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
          />
        </div>
      </b-modal>
    </div>
    </b-col>
    <!-----------------------------------------------Delete----------------------------------------->

    <b-col col lg="2">
    <div>
      <b-button variant="danger" v-b-modal.modal-2>Delete</b-button>
      <b-modal id="modal-2" title="Are you sure you want to delete this user?" @ok="remove()">
        <div class="input-group mb-3">
          <div class="input-group-prepend"></div>
        </div>
      </b-modal>
    </div>
  </b-col>
  </b-row>
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
        "weight",
        "height",
      ],
      entidade: [],
      state: true,
    };
  },
  props: {
    url: String,
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.$axios.$get(`/api/patients/${this.username}`).then((entidade) => {
      this.entidade = [entidade];
    });
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
  },
};
</script>
