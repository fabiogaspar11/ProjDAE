<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade"  :fields="fields"></b-table>
        </div>
      </template>
    </div>

    <div>
      <div class="d-flex justify-content-center">
        <b-button v-b-modal.modal-1 class="text-center">Edit</b-button>
      </div>
    </div>

    <b-modal id="modal-1" title="Edit Biomedic Data Type" @ok="update()">
        <div class="input-group mb-4">
          <span class="input-group-text">Date</span>
          <b-input required  v-model.trim="date" type="text" :state="isDateValid"  placeholder="dd/mm/yyyy" class="form-control" aria-describedby="basic-addon1 "/>
          <p>{{isDateValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Hour</span>
          <b-input required  v-model.trim="hour" type="text" :state="isHourValid"  placeholder="HH:MM" class="form-control" aria-describedby="basic-addon1 "/>
          <p>{{isHourValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Value</span>
          <b-input required  v-model.trim="value" type="number" :state="isValueValid"  placeholder="Enter the value" class="form-control" aria-describedby="basic-addon1 "/>
          <p>{{isValueValidFeedback}}</p>
      </div>
    </b-modal>
  </div>
</template>




<script>
export default {
  data() {
    return {
       fields: [
        { key: "code", label: "Code" },
        { key: "usernamePatient", label: "Patient Username"},
        { key: "biomedicDataType", label: "Biomedic Data Type"},
        { key: "minValue", label: "Minimum Value"},
        { key: "maxValue", label: "Maximum Value"},
        { key: "value", label: "Value"},
        { key: "date", label: "Date"},
        { key: "hour", label: "Hour" }
      ],
      entidade: [],
      date: null,
      hour:null,
      value:null,
      biomedicDataMeasure:{},
      biomedicDataTypes:[],
      minVal:null,
      maxVal:null
    };
  },
  props: {
    url: String,
  },
  computed: {
    code() {
      return this.$route.params.code;
    },
      isDateValidFeedback () {
        if (!this.date) {
          return null
        }
       var date_regex = /(^(((0[1-9]|1[0-9]|2[0-8])[\/](0[1-9]|1[012]))|((29|30|31)[\/](0[13578]|1[02]))|((29|30)[\/](0[4,6,9]|11)))[\/](19|[2-9][0-9])\d\d$)|(^29[\/]02[\/](19|[2-9][0-9])(00|04|08|12|16|20|24|28|32|36|40|44|48|52|56|60|64|68|72|76|80|84|88|92|96)$)/;
        return date_regex.test(this.date) ? '':'The date is invalid - format dd/mm/yyyy';
    },
    isDateValid () {
        if (this.isDateValidFeedback === null) {
          return null
        }
        return this.isDateValidFeedback === ''
    },
    isHourValidFeedback () {
        if (!this.hour) {
          return null
        }
        var hour_regex = /^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$/;
        return hour_regex.test(this.hour) ? '':'The hour is invalid - format HH:MM';
    },
    isHourValid() {
        if (this.isHourValidFeedback === null) {
          return null
        }
        return this.isHourValidFeedback === ''
    },
     isValueValidFeedback (){
        if (!this.value) {
          return null
        }
        let valueLen = this.value.length
        if (valueLen < 1 || valueLen > 25) {
           return 'The value is mandatory and must have between 1 and 25 digits'
        }
        if(this.value < this.minVal || this.value > this.maxVal){
          return 'The value is invalid, the value must be between ' + "["+this.minVal+" , "+this.maxVal+" ]";
        }
        return ''
    },
    isValueValid () {
        if (this.isValueValidFeedback === null) {
           return null
        }
        return this.isValueValidFeedback === ''
    }
  },
  created() {
    this.getBiomedicMeasure();
  },
  methods: {
    getBiomedicMeasure(){
      this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
      this.biomedicDataTypes = entidade;
        this.$axios.$get(`/api/biomedicDataMeasures/${this.code}`).then((entidade) => {
          this.entidade = [entidade];
            this.biomedicDataTypes.forEach(b => {
              if(this.entidade[0].biomedicDataTypeCode === b.code){
                this.entidade[0].biomedicDataType = b.name;
                this.entidade[0].minValue = b.minValue + " " + b.unitMeasure;;
                this.entidade[0].maxValue = b.maxValue + " " + b.unitMeasure;;
                this.entidade[0].value += " " + b.unitMeasure;
                this.minVal = b.minValue;
                this.maxVal = b.maxValue;
              }
            });
        });
    });
    },
    update() {

      this.biomedicDataMeasure = {};
      if(this.isDateValid){
          this.biomedicDataMeasure.date =this.date;
      }
      if(this.isHourValid){
          this.biomedicDataMeasure.hour =this.hour;
      }
       if(this.isValueValid){
          this.biomedicDataMeasure.value =this.value;
      }
      if(this.biomedicDataMeasure == {}){
        alert("Error - Nothing to update");
        return;
      }
      this.$axios
        .$put(`/api/biomedicDataMeasures/${this.code}`, this.biomedicDataMeasure)
        .then(() => {
          alert("Biomedic data measure "+this.code+" updated with success!");
          this.biomedicDataMeasure={};
          this.date = null;
          this.hour = null;
          this.value = null;
          this.getBiomedicMeasure();
        });
    },
  },
};
</script>
