
<template>
  <div>
    <NavBar></NavBar>

    <b-container class="bv-example-row" style="margin-top: 5%">
      <b-row>
        <b-col sm="3">
          <h3>Biomedic Data Measures ({{ tableLength }})</h3>
        </b-col>
        <b-col sm="5">
          <b-form-input v-model="filter" type="search" placeholder="Search...">
          </b-form-input>
        </b-col>

        <b-col lg="4">
          <b-button v-if="biomedicDataTypes != [] && patients != []"  v-b-modal.modal-1 variant="info">
            <font-awesome-icon icon="plus" /> New Biomedic Measure
          </b-button>
          <div v-else>
            <p>Cannot insert Biomedic Measures without having Biomedic Data Types</p>
          </div>
        </b-col>

      </b-row>
    </b-container>

    <b-modal id="modal-1" title="New disease" @ok="create()">
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
      <b-select v-model="biomedicDataType" :options="biomedicDataTypes" :state="isBiomedicDataTypeValid" required value-field="code" text-field="name">
            <template v-slot:first>
                <option :value="null" disabled>-- Please select the Biomedic Data Type --</option>
            </template>
       </b-select>
      </div>
      <div class="input-group mb-4">
          <span class="input-group-text">Value</span>
          <b-input required  v-model.trim="value" type="number" :state="isValueValid"  placeholder="Enter the value" class="form-control" aria-describedby="basic-addon1 "/>
          <p>{{isValueValidFeedback}}</p>
      </div>
      <div class="input-group mb-4">
        <b-select v-model="patient" :state="isPatientValid">
          <template v-slot:first>
              <option :value="null" disabled>-- Please select the Patient --</option>
          </template>
         <option v-for="patient in patients" :key="patient.username" :value="patient.username">
             {{patient.healthNumber}} - {{patient.name}}
         </option>
        </b-select>
      </div>

    </b-modal>
    <hr style="width: 73%" />
    <div class="d-flex justify-content-center" style="margin-top: 3%">
      <b-table
        :items="this.entidade"
        :fields="fields"
        striped
        responsive="sm"
        class="w-75 p-3"
        :filter="filter"
        @filtered="search"
      >

        <template v-slot:cell(operations)="row">
          <b-button :to="`/biomedicMeasures/${row.item.code}`" variant="info">
            <font-awesome-icon icon="eye" /> Details
          </b-button>

          <b-button v-b-modal.modal-3 variant="danger" @click="remove(row.item.code)">
            <font-awesome-icon icon="trash" /> Remove
          </b-button>
        </template>
      </b-table>
    </div>
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
        { key: "usernamePatient", label: "Patient Username", sortable: true, sortDirection: "desc" },
        { key: "biomedicDataType", label: "Biomedic Data Type", sortable: true, sortDirection: "desc" },
        { key: "value", label: "Value", sortable: true, sortDirection: "desc" },
        { key: "date", label: "Date", sortable: true, sortDirection: "desc" },
        { key: "hour", label: "Hour", sortable: true, sortDirection: "desc" },
        "operations",
      ],
      entidade: [],
      modalShow: false,
      patient: null,
      value:null,
      biomedicDataType: null,
      biomedicDataTypes:[],
      patients:[],
      date: null,
      hour:null,
      filter: null,
      totalRows: null,
      currentPage: null,
      minVal:null,
      maxVal:null
    };
  },
  computed: {
    tableLength: function () {
      return this.entidade.length;
    },
    isPatientValid () {
       if (!this.patient) {
          return null
        }
         return this.patients.some(p => this.patient === p.username)
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
    isBiomedicDataTypeValid () {
        if (!this.biomedicDataType) {
            return null
        }
        return this.biomedicDataTypes.some(biomedicDT => {
          if(this.biomedicDataType === biomedicDT.code){
            this.minVal = biomedicDT.minValue;
            this.maxVal = biomedicDT.maxValue;
            return true;
          }
          return false;
        })
    },
    isValueValidFeedback (){
        if (!this.value) {
          return null
        }
        let valueLen = this.value.length
        if (valueLen < 1 || valueLen > 25) {
           return 'The value is mandatory and must have between 1 and 25 digits'
        }
        if(this.minVal == null || this.maxVal == null){
          return 'The Biomedic Data Type must be choosen first';
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
    },
    isFormValid () {

    if (!this.isDateValid) {
      return false
    }
    if (!this.isHourValid) {
      return false
    }
    if (!this.isBiomedicDataTypeValid) {
      return false
    }
    if (!this.isValueValid) {
      return false
    }
    if (!this.isPatientValid) {
      return false
    }
    return true;
  },
  },
  created() {
    this.$axios.$get("/api/patients").then((entidade) => {
      this.patients = entidade;
    });
    this.$axios.$get("/api/biomedicDataTypes").then((entidade) => {
      this.biomedicDataTypes = entidade;
      //HERE qd for o healthcare prof meter apenas os seus pacientes
      this.$axios.$get("/api/biomedicDataMeasures").then((entidade) => {
       this.entidade = entidade;
       let i = 0;
        entidade.forEach(e => {
          this.biomedicDataTypes.forEach(b => {
            if(e.biomedicDataTypeCode === b.code){
              this.entidade[i].biomedicDataType = b.name;
              this.entidade[i].value += " " + b.unitMeasure;
            }
           });
           i++;
        });
      });
    });
  },
  methods: {

    create() {
      if(!this.isFormValid){
          alert("Fields are invalid - Correct them first!");
          return;
      }
      this.$axios
        .$post("/api/biomedicDataMeasures", {
            date:this.date,
            hour:this.hour,
            biomedicDataTypeCode:this.biomedicDataType.code,
            value:this.value,
            usernamePatient:this.patient
        })
        .then((response) => {
          console.log(response)
          alert("Biomedic data measure "+response.code+" created with success!");
          this.date = null;
          this.hour = null;
          this.biomedicDataType = null;
          this.patient = null;
          this.value = null;
          this.$router.go(0);
        })
        .catch((error) => {
          alert("Error" + error.response.data);
        });
    },
    remove(code) {
      this.$axios.$delete(`/api/biomedicDataMeasures/${code}`).then(() => {
         alert("Biomedic data measure "+this.code+" deleted with success!");
        this.$axios.$get("/api/biomedicDataMeasures").then((entidade) => {
          this.entidade = entidade;
        });
      });
    },
    search(filteredItems) {
      this.totalRows = filteredItems.length;
      this.currentPage = 1;
    },
  },
};
</script>



