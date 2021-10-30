<template>
  <div>
    <NavBar></NavBar>
    <div>
      <div class="d-flex justify-content-center" style="margin-top: 4%">
        <b-container>
          <b-table
            striped
            over
            class="w-100 p-3"
            hover
            :items="entidade"
            :fields="fields"
          >
            <template v-slot:cell(actions)="row">
              <nuxt-link
                class="btn btn-link"
                :key="type"
                :to="`/patients/${row.item.username}`"
                >Details</nuxt-link
              >
            </template>
          </b-table>
        </b-container>
      </div>
    </div>
  </div>
</template>

<script>
import NavBar from '~/components/NavBar.vue';
import Template from "../../components/Template.vue";

export default {
  components: {
    Template,
    NavBar,
  },
  data() {
    return {
      fields: ["name", "contact", "birthDate", "actions"],
      entidade: []
    }
  },
  created () {
      this.$axios.$get("/api/patients")
        .then((entidade) => {
          this.entidade = entidade
        })
    },
};
</script>
