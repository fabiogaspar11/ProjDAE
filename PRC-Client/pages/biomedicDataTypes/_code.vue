<template>
  <div>
    <NavBar></NavBar>
    <div class="container" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade" :fields="fields"></b-table>
        </div>
      </template>

      <div>
        <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
      </div>

      <b-modal id="modal-1" title="Edit Biomedic Data Type" @ok="update()">
        <div class="mx-auto alert alert-info">
          The normal values ​​should be created based on the normal values ​​for adult patients males
        </div>
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
            v-model.number="minValue"
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
            v-model.number="maxValue"
            type="number"
            class="form-control"
             :state="isMaxValueValid"
            aria-describedby="basic-addon1"
            placeholder="Enter maximum value"
          />
        </div>
          <p>{{ isMaxValueValidFeedback }}</p>
              <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Minimum Normal value:</span>
           <b-input
             v-model.number="normalMinValue"   :state="this.isNormalMinValid" class="col-md-3">{{this.normalMinValue}}</b-input>
           <b-input-group
            :prepend="minLimit"
            :append="maxLimit"
            class="mt-3"
          >
          <b-input
            v-model.number="normalMinValue"
            type="range"
            step="0.5"
            :min="minLimit"
            :max="maxLimit"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter minimum value"
          />
           </b-input-group>
        </div>
          <p>{{ isNormalMinValidFeedback }}</p>
            <div class="input-group mb-4 justify-content-center">
          <span class="input-group-text">Maximum Normal value:</span>
          <b-input v-model.number="normalMaxValue" :state="this.isNormalMaxValid" class="col-md-3">{{this.normalMaxValue}}</b-input>
           <b-input-group
            :prepend="minLimit"
            :append="maxLimit"
            class="mt-3"
          >
          <b-input
            v-model.number="normalMaxValue"
            type="range"
            step="0.5"
            :min="minLimit"
            :max="maxLimit"
            class="form-control"
            aria-describedby="basic-addon1"
            placeholder="Enter maximum value"
          />
            </b-input-group>
        </div>
          <p>{{ isNormalMaxValidFeedback }}</p>
          <div class="input-group mb-4">
          <span class="input-group-text">Gender value differentiation:</span>
           <div class="mx-auto   mt-2 alert alert-info">
            This value indicates how much the normal range of values will change depending if the gender of the patient is female.
           </div>
          <b-input
            v-model.number="genderValuedifferentiation"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isGenderValuedifferentiationValid"
            placeholder="Enter Gender Value Difference"
          />
        </div>
          <p>{{ isGenderValuedifferentiationValidFeedback }}</p>
          <div class="input-group mb-4">
          <span class="input-group-text">Age value differentiation:</span>
          <div class="mx-auto mt-2 alert alert-info">
            This value indicates how much the normal range of values will change depending on the age group of the patient.
           </div>
          <b-input
            v-model.number="ageValuedifferentiation"
            type="number"
            class="form-control"
            aria-describedby="basic-addon1"
            :state="isAgeValuedifferentiationValid"
            placeholder="Enter Age Value Difference"
          />
        </div>
          <p>{{ isAgeValuedifferentiationValidFeedback }}</p>
      </b-modal>
    </div>
  </div>
</template>




<script>
export default {
  middleware: "isAdministrator",
  data() {
    return {
      fields: [
        { key: "code", label: "Code", sortable: true, sortDirection: "desc" },
        { key: "name", label: "Name", sortable: true, sortDirection: "desc" },
        { key: "unitMeasure", label: "Unit Measure", sortable: true, sortDirection: "desc"},
        { key: "minValue", label: "Min Value", sortable: true, sortDirection: "desc"},
        { key: "maxValue", label: "Max Value", sortable: true, sortDirection: "desc"},
        { key: "normalMinValue", label: "Normal Min Value", sortable: true, sortDirection: "desc"},
        { key: "normalMaxValue", label: "Normal Max Value", sortable: true, sortDirection: "desc"},
        { key: "genderValuedifferentiation", label: "Gender Value difference", sortable: true, sortDirection: "desc"},
        { key: "ageValuedifferentiation", label: "Age Value difference", sortable: true, sortDirection: "desc"},
      ],
      entidade: [],
      name: null,
      unitMeasure: null,
      minValue: null,
      maxValue: null,
      normalMinValue:null,
      normalMaxValue:null,
      genderValuedifferentiation:null,
      ageValuedifferentiation:null,
      currentName: null,
      currentUnitMeasure: null,
      currentMinValue: null,
      currentMaxValue: null,
      currentNormalMinValue: null,
      currentNormalMaxValue: null,
      currentAgeValuedifferentiation: null,
      currentGenderValuedifferentiation: null,
    };
  },
  props: {
    url: String,
  },
  computed: {
    minLimit(){
      if(this.minValue==null || this.maxValue==null || this.minValue==="" || this.maxValue === "") {
        return (this.currentMinValue + 1).toString();
      }
      return (this.minValue+1).toString();
    },
    maxLimit(){
      if(this.minValue==null || this.maxValue==null || this.maxValue==="" ||this.minValue==="" ) {
        return (this.currentMaxValue-1).toString();
      }
      return (this.maxValue-1).toString();
    },
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

      let minValueDotIndex = this.minValue.toString().indexOf(".") || this.minValue.toString().indexOf(",")
      let minValueDot = this.minValue.toString().slice(minValueDotIndex+1)
      if (minValueDotIndex >= 0 && minValueDot != 5){
        return "the minimum value must be positive or positive with decimal case (.5)";
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

      if(this.minValue != null && this.maxValue <= this.minValue){
        return "Maximum value should be bigger than the minimum"
      }else if(this.minValue == null && this.maxValue <= this.currentMinValue){
        return "Maximum value should be bigger than the minimum"
      }
      else if(this.maxValue <= this.minValue+2) {
        return "There must be a difference of (+2) in maximum value than minimum value"
      }

      let maxValueDotIndex = this.maxValue.toString().indexOf(".") || this.maxValue.toString().indexOf(",")
      let maxValueDot = this.maxValue.toString().slice(maxValueDotIndex+1)
      if (maxValueDotIndex >= 0 && maxValueDot != 5){
        return "the maximum value must be positive or positive with decimal case (.5)";
      }

      return "";
    },
    isMaxValueValid() {
      if (this.isMaxValueValidFeedback === null) {
        return null;
      }
      return this.isMaxValueValidFeedback === "";
    },
    isNormalMinValid(){
      if (this.isNormalMinValidFeedback === null) {
        return null;
      }
      return this.isNormalMinValidFeedback === "";
    },
    isNormalMinValidFeedback(){
      if (!this.normalMinValue) {
        return null;
      }
      if(this.normalMinValue == this.currentNormalMinValue){
         return "The normal minimum value is equal to the current  normal minimum value "
      }

      if(this.minValue != null && this.normalMinValue < this.minValue)
        return "The normal minimum value should be bigger than the minimum value"
      else if(this.minValue == null && this.normalMinValue < this.currentMinValue){
            return "The normal minimum value should be bigger than the current minimum value (" + this.currentMinValue + ")";
      }

      if(this.normalMaxValue != null && this.normalMinValue >= this.normalMaxValue){
        return "The normal minimum value should be smaller than the normal maximum value"
      }else if(this.normalMaxValue == null && this.normalMinValue >= this.currentNormalMaxValue){
        return "The normal minimum value should be smaller than the current normal maximum value (" + this.currentNormalMaxValue + ")";
      }

      if( this.maxValue != null && this.normalMinValue > this.maxValue) {
        return "The normal minimum value should be smaller than the maximum value"
      }else if(this.maxValue == null && this.normalMinValue > this.currentMaxValue){
        return "The normal minimum value should be smaller than the current maximum value (" + this.currentMaxValue + ")";
      }
      return ""
    },
    isNormalMaxValid(){
      if (this.isNormalMaxValidFeedback === null) {
        return null;
      }
      return this.isNormalMaxValidFeedback === "";
    },
    isNormalMaxValidFeedback(){
      if (!this.normalMaxValue) {
        return null;
      }
       if(this.normalMaxValue == this.currentNormalMaxValue){
         return "The normal maximum value is equal to the current  normal maximum value "
      }
      if( this.minValue != null && this.normalMaxValue < this.minValue)
        return "The normal maximum value should be bigger than the minimum value"
      else if( this.minValue == null && this.normalMaxValue < this.currentMinValue){
        return "The normal maximum value should be bigger than the current minimum value (" + this.currentMinValue + ")";
      }

      if(this.maxValue != null && this.normalMaxValue > this.maxValue) {
        return "The normal maximum value should be smaller than the maximum value"
      }else if(this.maxValue == null && this.normalMaxValue > this.currentMaxValue){
        return "The normal maximum value should be smaller than the current maximum value (" + this.currentMaxValue + ")";
      }

      if(this.normalMinValue != null && this.normalMaxValue <= this.normalMinValue){
        return "The normal maximum value should be bigger than the minimum value"
      }else if(this.normalMinValue == null && this.normalMaxValue <= this.currentNormalMinValue){
        return "The normal maximum value should be bigger than the current minimum value (" + this.currentNormalMinValue + ")";
      }
      return ""
    },
    isGenderValuedifferentiationValid(){
      if(this.isGenderValuedifferentiationValidFeedback == null)
        return null
      return this.isGenderValuedifferentiationValidFeedback === ""
    },
    isGenderValuedifferentiationValidFeedback(){
      if (!this.genderValuedifferentiation && this.genderValuedifferentiation!=0) {
        return null;
      }
      if(this.genderValuedifferentiation == this.currentGenderValuedifferentiation){
         return "The gender value differentiation is equal to the current gender value differentiation "
      }
      return "";
    },
    isAgeValuedifferentiationValid(){
      if(this.isAgeValuedifferentiationValidFeedback === null)
          return null
      return this.isAgeValuedifferentiationValidFeedback === ""
    },
    isAgeValuedifferentiationValidFeedback(){
      if (!this.ageValuedifferentiation && this.ageValuedifferentiation!=0) {
        return null;
      }
      if(this.ageValuedifferentiation == this.currentAgeValuedifferentiation
      ){
         return "The age value differentiation is equal to the current age value differentiation "
      }
      return ""
    }
  },
  created() {
    this.getBiomedicDataType();
  },
  methods: {
    getBiomedicDataType(){
      this.$axios.$get(`/api/biomedicDataTypes/${this.code}`).then((entidade) => {
      this.entidade = [entidade];
      this.currentName = entidade.name;
      this.currentUnitMeasure = entidade.unitMeasure;
      this.currentMinValue = entidade.minValue;
      this.currentMaxValue = entidade.maxValue;
      this.currentNormalMinValue = entidade.normalMinValue;
      this.currentNormalMaxValue = entidade.normalMaxValue;
      this.currentAgeValuedifferentiation = entidade.ageValuedifferentiation;
      this.currentGenderValuedifferentiation = entidade.genderValuedifferentiation;
    })
    .catch((error)=>{
      if(error.response.status == 403 || error.response.status == 404){
        this.$router.push("./../biomedicDataTypes");
        return;
      }
    });
    },
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

      if (this.isNormalMinValid) {
        biomedicDataUpdated.normalMinValue = this.normalMinValue;
      }
      if (this.isNormalMaxValid) {
        biomedicDataUpdated.normalMaxValue = this.normalMaxValue;
      }
      if(this.isGenderValuedifferentiationValid){
        biomedicDataUpdated.genderValuedifferentiation = this.genderValuedifferentiation;
      }
      if(this.isAgeValuedifferentiationValid){
        biomedicDataUpdated.ageValuedifferentiation = this.ageValuedifferentiation;
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
          this.normalMaxValue = null;
          this.normalMinValue = null;
         this.genderValuedifferentiation = null;
         this.ageValuedifferentiation = null;
          this.getBiomedicDataType();

        });
    },
  },
};
</script>
