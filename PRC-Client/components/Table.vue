<template>
  <div class="d-flex justify-content-center" style="margin-top:4%">
    <b-table class="w-75 p-3" hover :items="entidade"></b-table>
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
      url:String
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
