<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <b-container>
        <b-table
          striped over
          class="w-100 p-3"
          hover
          :items="entidade"
        >
        </b-table>
      </b-container>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      entidade: [],
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
    this.$axios.$get(`/api/administrators/${this.username}`).then((entidade) => {
      this.entidade = [entidade];
    });
  },
};
</script>
