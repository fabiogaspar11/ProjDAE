
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h3> Health Care Professionals ({{tableLength}})</h3>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search..."> </b-form-input>
        </b-col>

        <b-col lg="4">
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus"/> New professional
          </b-button>
        </b-col>
      </b-row>
    </b-container>
    <b-modal id="modal-1" title="New disease" @ok="create(code)">
      <div class="input-group mb-4">
          <span class="input-group-text">Code</span>
        <input v-model="code" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
        <input v-model="name" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">type</span>
        <input v-model="type" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
    </b-modal>

    <b-modal id="modal-2" title="update disease" @ok="update(code)">
      <div class="input-group mb-4">
        <span class="input-group-text">Name</span>
        <input v-model="name" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">type</span>
        <input v-model="type" type="text" class="form-control" aria-describedby="basic-addon1"/>
      </div>
    </b-modal>

    <hr style="width:73%;">
    <div class="d-flex justify-content-center" style="margin-top: 3%">

      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >
        <template #cell(show_details)="row">
            <b-button size="sm" @click="row.toggleDetails" class="mr-2">
            {{ row.detailsShowing ? "Hide" : "Show" }} Details
          </b-button>
        </template>

       <template #row-details="row">
          <b-card>
            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Email:</b></b></b-col>
              <b-col>{{ row.item.email }}</b-col>
            </b-row>

            <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Contacto:</b></b-col>
              <b-col>{{ row.item.contact }}</b-col>
            </b-row>

                    <b-row class="mb-2">
              <b-col sm="3" class="text-sm-right"><b>Username:</b></b-col>
              <b-col>{{ row.item.username }}</b-col>
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
          <b-button @click.prevent="getCode(row.item.code)" v-b-modal.modal-2  variant="info">
            <font-awesome-icon icon="edit" /> Edit
          </b-button>
          <b-button @click.prevent="remove(row.item.code)" variant="danger">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>

    </div>
  </div>

</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from 'vue-router';

export default {
  components: {
    NavBar,
    Router
  },
  data() {
    return {
      fields: [
        { key: 'name', label: 'Name', sortable: true, sortDirection: 'desc' },
        { key: 'birthDate', label: 'Birthdate', sortable: true, sortDirection: 'desc' },
        { key: 'healthNumber', label: 'Health Nº', sortable: true, sortDirection: 'desc' },
        { key: 'type', label: 'Type', sortable: true, sortDirection: 'desc' },
        "show_details",
        "actions"
      ],
      entidade: [],
      modalShow: false,
      code: null,
      name: null,
      type: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      //entity: this.$route.name
    };
  },
  computed: {
    tableLength: function() {
      return this.entidade.length;
    }
  },
  created() {
    this.$axios.$get("/api/healthcareProfessionals").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
    getCode(code){
      this.code = code
    },
    create(code) {
      this.$axios.$post("/api/diseases", {
        code: this.code,
        name: this.name,
        type: this.type
      })
        .then(response => {
          this.entidade.push(response);
          this.code = null;
          this.name = null;
          this.type = null;

        });

    },
    remove(code) {
      this.$axios.$delete('/api/diseases/' + code)
        .then(() => {
          const index = this.entidade.findIndex(disease => disease.code === code) // find the post index
          if (index) // if the post exists in array
            this.entidade.splice(index, 1) //delete the post
        });
    },
    update(code) {
      this.$axios.$put('/api/diseases/' + code, {
        name: this.name,
        type: this.type,

      })
        .then(response => {
          this.code = null;
          this.name = null;
          this.type = null;
          const index = this.entidade.findIndex(disease => disease.code === code) // find the post index
          if (~index) { // if the post exists in array
            this.entidade.splice(index, 1);
            this.entidade.splice(index, 0, response);
          }
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length
      this.currentPage = 1
    }
  }
};
</script>


