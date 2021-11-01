<template>
  <div>
    <NavBar></NavBar>
    <h1 style="margin-top:5%; margin-left: 13%"> Diseases ({{tableLength}}) </h1>
    <div class="d-flex justify-content-center" style="margin-top: 1%">

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

export default {
  components: {
    NavBar,
  },
  data() {
    return {
      fields: [
        "name",
        "type",
        "operations",
        "delete",
      ],
      entidade: [],
      modalShow: false,
      name: null,
      bhirtData: null,
      contact: null,
      email: null
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
    remove(code) {
      this.$axios.$delete('/api/diseases/' + code)
        .then(response => {
          const index = this.entidade.findIndex(disease => disease.code === code) // find the post index
          if (~index) // if the post exists in array
            this.entidade.splice(index, 1) //delete the post
        });
    }
  }
};
</script>



<style>
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
