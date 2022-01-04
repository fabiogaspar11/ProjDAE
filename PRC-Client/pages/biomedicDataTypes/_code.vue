<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade"></b-table>
        </div>
      </template>

      <div>
        <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
      </div>

      <b-modal id="modal-1" title="Edit Biomedic Data Type" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Name:</span>
          <b-input
            v-model="name"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isNameValid"
            placeholder="Enter name"
          />
        </div>
          <p>{{ isNameValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Unit Measure:</span>
          <b-input
            v-model="unitMeasure"
            type="text"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isUnitValid"
            placeholder="Enter unit"
          />
        </div>
          <p>{{ isUnitValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Minimum value:</span>
          <b-input
            v-model="minValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isMinValueValid"
            placeholder="Enter minimum value"
          />
        </div>
          <p>{{ isMinValueValidFeedback }}</p>
        <div class="input-group mb-4">
          <span class="input-group-text">Maximum value:</span>
          <b-input
            v-model="maxValue"
            type="number"
            class="form-control"
             :state="isMaxValueValid"
            aria-describedby="basic-addon1"
            placeholder="Enter maximum value"
          />
        </div>
          <p>{{ isMaxValueValidFeedback }}</p>
      </b-modal>
    </div>
  </div>
</template>




<script>
export default {
  middleware: "isAdministrator",
  data() {
    return {
      entidade: [],
      name: null,
      unitMeasure: null,
      minValue: null,
      maxValue: null,
      currentName: null,
      currentUnitMeasure: null,
      currentMinValue: null,
      currentMaxValue: null,
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
    },
    isNameValidFeedback() {
      if (!this.name) {
        return null;
      }
      if(this.name == this.currentName){
          return "Name is equal to current name";
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
    isUnitValidFeedback() {
      if (!this.unitMeasure) {
        return null;
      }
      if(this.unitMeasure == this.currentUnitMeasure){
          return "Unit Measure is equal to current unit Measure";
      }
      let unitMeasureLen = this.unitMeasure.length;
      if (unitMeasureLen < 1 || unitMeasureLen > 25) {
        return "The unit is too short - length must be between 1 and 25";
      }
      return "";
    },
    isUnitValid() {
      if (this.isUnitValidFeedback === null) {
        return null;
      }
      return this.isUnitValidFeedback === "";
    },
    isMinValueValidFeedback() {
      if (!this.minValue) {
        return null;
      }
       if(this.minValue == this.currentMinValue){
          return "Minimum value is equal to current minimum value";
      }
      let minValueLen = this.minValue.length;
      if (minValueLen <= 0 || minValueLen > 25) {
        return "The minimum value is mandatory or is too big in size";
      }
      return "";
    },
    isMinValueValid() {
      if (this.isMinValueValidFeedback === null) {
        return null;
      }
      return this.isMinValueValidFeedback === "";
    },
    isMaxValueValidFeedback() {
      if (!this.maxValue) {
        return null;
      }
        if(this.maxValue == this.currentMaxValue){
          return "Maximum value is equal to current maximum value";
      }
      let maxValueLen = this.maxValue.length;
      if (maxValueLen <= 0 || maxValueLen > 25) {
        return "The maximum value is mandatory or is too big in size";
      }
      return "";
    },
    isMaxValueValid() {
      if (this.isMaxValueValidFeedback === null) {
        return null;
      }
      return this.isMaxValueValidFeedback === "";
    },
  },
  created() {
    this.$axios.$get(`/api/biomedicDataTypes/${this.code}`).then((entidade) => {
      this.entidade = [entidade];
      this.currentName = entidade.name;
      this.currentUnitMeasure = entidade.unitMeasure;
      this.currentMinValue = entidade.minValue;
      this.currentMaxValue = entidade.maxValue;
    })
    .catch((error)=>{
      if(error.response.status == 403 || error.response.status == 404){
        this.$router.push("./../biomedicDataTypes");
        return;
      }
    });
  },
  methods: {
    update() {
         let biomedicDataUpdated = {};
      if (this.isNameValid) {
        biomedicDataUpdated.name = this.name;
      }
      if (this.isUnitValid) {
        biomedicDataUpdated.unitMeasure = this.unitMeasure;
      }
      if (this.isMinValueValid) {
        biomedicDataUpdated.minValue = this.minValue;
      }
      if (this.isMaxValueValid) {
        biomedicDataUpdated.maxValue = this.maxValue;
      }
      if(Object.keys(biomedicDataUpdated).length == 0){
        this.$toast.error(`Nothing to update!`).goAway(3000);
        return;
      }

      this.$axios
        .$put(`/api/biomedicDataTypes/${this.code}`,biomedicDataUpdated)
        .then(() => {
           this.$toast.info("Biomedic data type " + this.name + " updated succesfully!").goAway(3000);

          this.name = null;
          this.unitMeasure = null;
          this.minValue = null;
          this.maxValue = null;
          this.$axios
            .$get(`/api/biomedicDataTypes/${this.code}`)
            .then((entidade) => {
              this.entidade = [entidade];
            });
        });
    },
  },
};
</script>
