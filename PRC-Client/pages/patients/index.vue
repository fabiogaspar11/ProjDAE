<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <b-table
        :items="entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
      >
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

            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Weight:</b></b-col>
              <b-col>{{ row.item.weight }}</b-col>
            </b-row>

            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Height:</b></b-col>
              <b-col>{{ row.item.height }}</b-col>
            </b-row>

            <div>
              <b-button v-b-modal.modal-1>Edit</b-button>

              <b-modal
                id="modal-1"
                title="Edit"
                @ok="update(row.item.username)"
              >
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
                    <span class="input-group-text" id="basic-addon1"
                      >BirthDate</span
                    >
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
                    <span class="input-group-text" id="basic-addon1"
                      >Email</span
                    >
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
                    <span class="input-group-text" id="basic-addon1"
                      >Contact</span
                    >
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
          </b-card>
        </template>
        <template v-slot:cell(actions)="row">
          <nuxt-link class="btn btn-link" :to="`/patients/${row.item.username}`"
            >Details</nuxt-link
          >
        </template>
      </b-table>
    </div>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Template from "../../components/Template.vue";

export default {
  components: {
    Template,
    NavBar,
  },
  data() {
    return {
      fields: ["name", "birthDate", "healthNumber", "show_details", "actions"],
      entidade: [],
      modalShow: false,
      name:null,
      bhirtData:null,
      contact:null,
      email:null
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
    update(code) {
      this.$axios.$put("/api/patients/" + code, {
        name: this.name,
        email:this.email,
        contact:this.contact,
        bhirtDate:this.birthDate
      }).then(()=>{
        this.name=null
        this.email=null
        this.contact=null
        this.bhirtDate=null
        this.$axios.$get("/api/patients").then((entidade) => {
        this.entidade = entidade;
    });
      });

    }
  },
};
</script>
