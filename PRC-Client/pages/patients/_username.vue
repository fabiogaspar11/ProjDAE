<template>
<div>
<NavBar></NavBar>
<div class="d-flex justify-content-center" style="margin-top: 4%">
  <b-table :items="entidade" :fields="fields" striped responsive="sm" class="w-75 p-3">
      <template #cell(show_details)="row">
        <b-button size="sm" @click="row.toggleDetails" class="mr-2">
          {{ row.detailsShowing ? 'Hide' : 'Show'}} Details
        </b-button>

        <!-- As `row.showDetails` is one-way, we call the toggleDetails function on @change -->
        <b-form-checkbox v-model="row.detailsShowing" @change="row.toggleDetails">
          Details via check
        </b-form-checkbox>
      </template>

      <template #row-details="row">
        <b-card>
          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Age:</b></b-col>
            <b-col>{{ row.item.name }}</b-col>
          </b-row>

          <b-row class="mb-2">
            <b-col sm="3" class="text-sm-right"><b>Is Active:</b></b-col>
            <b-col>{{ row.item.isActive }}</b-col>
          </b-row>

          <b-button size="sm" @click="row.toggleDetails">Hide Details</b-button>
        </b-card>
      </template>
    </b-table>
  </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      fields:['username','name','birthDate','healthNumber','contact','weight','height','show_details','actions'],
      entidade: []
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
};
</script>
