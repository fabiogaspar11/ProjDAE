
<template>
  <div>
    <NavBar></NavBar>

    <div id="outer">
      <p id="title" class="inner"> Diseases
        <span id="totalEntity">({{tableLength}})</span>
      </p>
      <b-form-input id="searchEntity" class="inner" v-model="filter" type="text" placeholder="Search..."> </b-form-input>
      <b-button v-b-modal.modal-1 id="buttonCreate" class="inner">
        <img id="imageCreate" src="../../images/plus.png"> New disease
      </b-button>
    </div>
    
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

        <template v-slot:cell(operations)="row">
          <nuxt-link class="btn btn-link" :to="`/patients/${row.item.code}`"
          >Details</nuxt-link
          >
        </template>
        <template v-slot:cell(delete)="row">
          <b-button id="buttonRemove" class="btn btn-link" @click.prevent="remove(row.item.code)">
            <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
              <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6z"/>
              <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1zM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118zM2.5 3V2h11v1h-11z"/>
            </svg>
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
        "code",
        "name",
        "type",
        "operations",
        "delete",
      ],
      entidade: [],
      modalShow: false,
      code: null,
      name: null,
      type: null,
      filter: null,

      //entity: this.$route.name
    };
  },
  computed: {
    tableLength: function() {
      return this.entidade.length;
    }
  },
  created() {
    this.$axios.$get("/api/diseases").then((entidade) => {
      this.entidade = entidade;
    });
  },
  methods: {
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
        .then(response => {
          const index = this.entidade.findIndex(disease => disease.code === code) // find the post index
          if (~index) // if the post exists in array
            this.entidade.splice(index, 1) //delete the post
        });
    },
    search(filteredItems) {
      // Trigger pagination to update the number of buttons/pages due to filtering
      this.totalRows = filteredItems.length
      this.currentPage = 1
    }
  }
};
</script>



<style>

#outer
{
  width:100%;
  text-align: left;
  padding-left: 14%;
  padding-top: 2%;
}
.inner
{
  display: inline-block;
}

#searchEntity{
  width: 15%;
  height: 30px;
  text-align: left;
  padding-left: 40px;
  background-image: url("../../images/search.png");
  background-size: 40px;
  background-repeat: no-repeat;
  border-radius: 8px;
}

#title{
  color: #58CFEB;
  margin-top:3%;
  margin-right: 10%;
  position: relative;
  font-size: 200%;
}

#totalEntity{
  font-size:80%;
  color: lightgray;
}

#imageCreate{
  width: 25px;
  height: 25px;
  margin-bottom: 2%;
}
#buttonCreate{
  margin-left: 33%;
  background-color: #58CFEB;
  border: white;
  border-radius: 20px;
  box-shadow: none !important;
  outline: none !important;

}
#buttonCreate:hover{
  background-color: #17a2b8;
}

#buttonRemove{
  color: #007bff;
  background-color: transparent;
  border: 0 !important;
  box-shadow: none !important;
  outline: none !important;
}
#buttonRemove:hover{
  color: #0056b3;
}
</style>
