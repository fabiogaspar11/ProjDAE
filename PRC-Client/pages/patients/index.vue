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
          </b-card>
        </template>
        <template v-slot:cell(operations)="row">
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

export default {
  components: {
    NavBar,
  },
  data() {
    return {
      fields: [
        "name",
        "birthDate",
        "healthNumber",
        "show_details",
        "operations",
      ],
      entidade: [],
      modalShow: false,
      name: null,
      bhirtData: null,
      contact: null,
      email: null,
    };
  },
  created() {
    this.$axios.$get("/api/patients").then((entidade) => {
      this.entidade = entidade;
    });
  },
};
</script>
