<template>
<div>
  <div class="d-flex justify-content-center" style="margin-top:4%">
  <b-container>
    <b-table striped over class="w-100 p-3" hover :items="entidade" :fields="fields">
     <template v-slot:cell(actions)="row">
          <div v-if="type=='patients'">
             <nuxt-link

            class="btn btn-link" :key="type"
            :to="`/patients/${row.item.username}`">Details</nuxt-link>

          </div>
          <div v-else-if="type=='administrators'">
             <nuxt-link
            class="btn btn-link" exact
            :to="`administrators/${row.item.username}`">Details</nuxt-link>
          </div>
     </template>
 </b-table>
    </b-container>
  </div>
</div>
</template>

<script>
  export default {
    data() {
      return {
        items: [
          { age: 40, first_name: 'Dickerson', last_name: 'Macdonald' },
          { age: 21, first_name: 'Larsen', last_name: 'Shaw' },
          {
            age: 89,
            first_name: 'Geneva',
            last_name: 'Wilson',
            _rowVariant: 'danger'
          },
          {
            age: 40,
            first_name: 'Thor',
            last_name: 'MacDonald',
            _cellVariants: { age: 'info', first_name: 'warning' }
          },
          { age: 29, first_name: 'Dick', last_name: 'Dunlap' }
        ],

          entidade:[]
      }
    },
    props:{
      data:[],
      url:String,
      fields:[],
      type:String
    },
    created () {
      this.$axios.$get(this.url)
        .then((entidade) => {
          this.entidade = entidade
        })
    },
     watch: {
        url:
        function(){
            this.$axios.$get(this.url)
          .then((entidade) => {
            this.entidade = entidade
          })
        }
    }
  }
</script>

<style>

</style>
