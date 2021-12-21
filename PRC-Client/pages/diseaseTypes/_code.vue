<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>
    </div>
      <b-row class="justify-content-md-center">
            <b-col col lg="2" >
    <div>
      <div>
        <div class="d-flex justify-content-center">
          <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
        </div>
      </div>
      <b-modal id="modal-1" title="Edit" @ok="update()">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="basic-addon1">Name</span>
          </div>
          <b-input
            v-model.trim="name"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter your name"
            :state="isNameValid"
          />
        </div>
      </b-modal>
    </div>
    </b-col>
  </b-row>
  </div>
</template>



<script>
export default {
  data() {
    return {
      fields: [
        "code",
        "name",
      ],
      entidade: [],
      state: true,
      name:null,
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
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
  created() {
    this.$axios.$get(`/api/diseaseTypes/${this.code}`).then((entidade) => {
      this.entidade = [entidade];
    });
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/diseaseTypes/${this.code}`).then(()=>{
      this.$router.go(-1);
      })
    },
    update() {

      if(!this.isFormValid){
          alert("Fields are invalid - Correct them first!");
          return;
      }
      let disease = {};
      if(this.name != null){
          disease.name = this.name;
      }
      this.$axios
        .$put(`/api/diseaseTypes/${this.code}`, disease)
        .then(() => {
           alert(`Disease Type ${this.code}  updated!`);
          this.$router.go(0);
        })
        .catch((error) => {
         console.log(error.response.data)
        });
    },
  },
};
</script>
