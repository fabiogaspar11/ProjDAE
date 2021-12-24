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
          <p>{{ isNameValidFeedback }}</p>
        </div>
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
          <p>{{ isUnitValidFeedback }}</p>
        </div>
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
          <p>{{ isMinValueValidFeedback }}</p>
        </div>
        <div class="input-group mb-4">
          <span class="input-group-text">Maximum value:</span>
          <b-input
            v-model="maxValue"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter maximum value"
          />
          <p>{{ isMaxValueValidFeedback }}</p>
        </div>
      </b-modal>
    </div>
  </div>
</template>




<script>
export default {
  data() {
    return {
      entidade: [],
      name: null,
      unitMeasure: null,
      minValue: null,
      maxValue: null,
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
    });
  },
  methods: {
    update() {
      this.$axios
        .$put(`/api/biomedicDataTypes/${this.code}`, {
          name: this.name,
          unitMeasure: this.unitMeasure,
          minValue: this.minValue,
          maxValue: this.maxValue,
        })
        .then(() => {
          alert("Biomedic data type " + this.name + " updated with success!");
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
