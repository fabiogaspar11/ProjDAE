<template>
  <div>
    <NavBar></NavBar>
    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h1>Disease Types ({{ tableLength }})</h1>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>
        <b-col>
          <b-button v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus"/> New Disease Type
          </b-button>
        </b-col>
      </b-row>
    </b-container>

    <b-modal id="modal-1" title="New Disease Type" @ok.prevent="create()">
      <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input required v-model.trim="name" type="text" :state="isNameValid"  class="form-control" aria-describedby="basic-addon1" placeholder="Enter your name"/>
          <p>{{isNameValidFeedback}}</p>
      </div>
    </b-modal>

     <hr style="width: 73%" />
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
        <template v-slot:cell(operations)="row">
          <b-button :to="`/diseaseTypes/${row.item.code}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>

          <b-button v-b-modal.modal-3 variant="danger" @click="remove(row.item.code)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
    </div>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";

export default {
  components: {
    NavBar
  },
  data() {
    return {
      fields: [
        {key:"name", label:"Name",  sortable: true, sortDirection: "desc" },
        "operations",
      ],
      entidade: [],
      name: null,
      filter: null,
      totalRows: null,
      currentPage: null,
    };
  },
  created() {
    this.$axios.$get("/api/diseaseTypes").then((entidade) => {
      this.entidade = entidade;
      console.log(this.entidade.length)
    });
  },
    computed: {
     tableLength: function () {
      return this.entidade.length;
    },
    username() {
      return this.$route.params.username;
    },
     isNameValidFeedback (){
        if (!this.name) {
          return null
        }
        let nameLen = this.name.length
        if (nameLen < 3 || nameLen > 25) {
           return 'The name is too short - length must be between 3 and 25'
        }
        return ''
    },
    isNameValid () {
        if (this.isNameValidFeedback === null) {
           return null
        }
        return this.isNameValidFeedback === ''
    },
   isFormValid () {
    if (!this.isNameValid) {
      return false
    }

      return true
    }
  },
  methods:{
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
    remove(code){
       this.$axios.$delete(`/api/diseaseTypes/${code}`).then(()=>{
      alert('Disease Type '+code +' was successfully removed');
      this.$router.go(0);

      })
    },
       create() {
      if(!this.isFormValid){
          alert("Fields are invalid - Correct them first!");
          return;
      }
      this.$axios.$post("/api/diseaseTypes", {
       name: this.name,
       })
        .then(response => {
          alert("Disease Types "+ this.name + " created succesfully");
          this.name = null;
          this.$router.go(0);
        })
        .catch(error => {
            alert("Error when creating Disease Type: "+ error.response.data);
        });
     }
  },
};
</script>
