
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="mt-3">
      <h3>Diseases ({{ tableLength }})</h3>
      <b-form-input v-model="filter" type="search" placeholder="Search...">
      </b-form-input>

      <div class="mt-3 mb-5 text-center">
        <b-button v-b-modal.modal-1 variant="info">
          <font-awesome-icon icon="plus" /> New disease
        </b-button>
      </div>

    <div class="mt-3">
      <b-modal id="modal-1" title="New disease" @ok="create()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input
            v-model.trim="name"
            type="text"
            :state="isNameValid"
            placeholder="Enter name"
            class="form-control"
            required
            aria-describedby="basic-addon1"
          />
          <p>{{ isNameValidFeedback }}</p>
        </div>
      </b-modal>

      <b-table v-if="this.tableLength != 0"
        class="mt-3"
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        :filter="filter"
        @filtered="search"
        id="table"
        :per-page="perPage"
        :current-page="currentPagePaginatePrincipal"
      >
        <template v-slot:cell(operations)="row">
          <b-button v-b-modal.modal-2 variant="primary" @click="sendInfo(row.item.code,row.item.name)">Edit</b-button>
          <b-button variant="danger" @click="remove(row.item.code)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
      <div v-else class="w-75 mx-auto alert alert-info">
          No diseases created yet
    </div>
   <div class="d-flex justify-content-center">
      <b-modal id="modal-2" :title="'Edit Disease nº '+this.currentCode" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name</span>
          <b-input v-model.trim="nameEdit" type="text" :state="isNameEditValid" placeholder="Enter name" class="form-control" required aria-describedby="basic-addon1"/>
          <p>{{isNameEditValidFeedback}}</p>
        </div>
      </b-modal>
    </div>
    </div>
      <b-pagination
        class="fixed-bottom justify-content-center"
        v-model="currentPagePaginatePrincipal"
        :total-rows="tableLength"
        :per-page="perPage"
        aria-controls="table"
      ></b-pagination>
    </b-container>
  </div>
</template>

<script>
import NavBar from "/components/NavBar.vue";
import Router from "vue-router";

export default {
  components: {
    NavBar,
    Router,
  },
  data() {
    return {
      fields: [
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
        "Operations",
      ],
      entidade: [],
      modalShow: false,
      code: null,
      name: null,
      nameEdit: null,
      filter: null,
      totalRows: null,
      currentPage: null,
      currentName: null,
      currentCode:null,
      perPage: 5,
      currentPagePaginatePrincipal: 1,
    };
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
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
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      let nameLen = this.name.length;
      if (nameLen < 3 || nameLen > 25) {
        return "The name is too short - length must be between 3 and 25";
      }
      return "";
    },
    isNameValid() {
      if (this.isNameValidFeedback === null) {
        return null;
      }
      return this.isNameValidFeedback === "";
    },

    isFormValid () {
    if (!this.isNameValid) {
      return false
    }
      return true
    },
    isNameEditValidFeedback(){
        if (!this.nameEdit) {
          return null
        }
        if(this.nameEdit == this.currentName){
          return 'The name is equal to current name';
        }
        let nameLen = this.nameEdit.length
        if (nameLen < 3 || nameLen > 25) {
           return 'The name is too short - length must be between 3 and 25'
        }
        return ''
    },
    isNameEditValid () {
        if (this.isNameEditValidFeedback === null) {
           return null
        }
        return this.isNameEditValidFeedback === ''
    }
  },
  methods: {
     sendInfo(code,name) {
       this.nameEdit = null;
       this.currentCode = code;
       this.currentName = name;
     },
    getDiseases(){
        this.$axios.$get("/api/diseases").then((entidade) => {
        this.entidade = entidade;
      });
    },
      update() {
      if (!this.isNameEditValid) {
           this.$toast.error("Field is invalid - Correct it first!").goAway(3000);
          return;
      }

      this.$axios
        .$put(`/api/diseases/${this.currentCode}`, {
          name: this.nameEdit
        })
        .then(() => {
          this.$toast.info("Disease " + this.name + " updated succesfully").goAway(3000);
          this.nameEdit = null;
          this.currentName = null;
          this.currentCode = null;
          this.getDiseases();
        })
        .catch(error => {
            this.$toast.error("Error when update Disease: "+ error.response.data).goAway(3000);
            this.currentName = null;
            this.currentCode = null;
        });
    },
    create() {
      if (!this.isFormValid) {
        this.$toast
          .error("Fields are invalid - Correct them first!")
          .goAway(3000);
        return;
      }

      this.$axios.$post("/api/diseases", {
        name: this.name
      })
        .then(() => {
          this.$toast.success("Disease "+ this.name + " created succesfully").goAway(3000);
          this.getDiseases();
          this.name = null;
        })
        .catch(error => {
            this.$toast.error("Error when creating Disease: "+ error.response.data).goAway(3000);
        });
    },
    remove(code) {
      this.$axios.$delete('/api/diseases/' + code)
        .then(() => {
            this.$toast.info("Disease: "+ code + " removed with success").goAway(3000);
            this.getDiseases();
        });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length
      this.currentPage = 1
    }
  },
   created() {
    this.getDiseases();
  },
};
</script>



