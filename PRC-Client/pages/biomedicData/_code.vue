<template>
  <div>
    <NavBar></NavBar>
    <div class="d-flex justify-content-center" style="margin-top: 4%">
      <template>
        <div>
          <b-table striped hover :items="entidade"></b-table>
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
        <span class="input-group-text">Name:</span>
        <input
          v-model="name"
          type="text"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Unit Measure:</span>
        <input
          v-model="unitMeasure"
          type="text"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Minimum value:</span>
        <input
          v-model="minValue"
          type="number"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
      <div class="input-group mb-4">
        <span class="input-group-text">Maximum value:</span>
        <input
          v-model="maxValue"
          type="number"
          class="form-control"
          aria-describedby="basic-addon1"
        />
      </div>
    </b-modal>

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
  },
  created() {
    this.$axios.$get(`/api/biomedicDataType/${this.code}`).then((entidade) => {
      this.entidade = [entidade];
    });
  },
  methods: {
    remove() {
      this.$axios.$delete(`/api/biomedicDataType/${this.code}`).then(() => {
        this.$router.push("/biomedicData");
      });
    },
    update() {
      this.$axios
        .$put(`/api/biomedicDataType/${this.code}`, {
          name: this.name,
          unitMeasure: this.unitMeasure,
          minValue: this.minValue,
          maxValue: this.maxValue,
        })
        .then(() => {
          (this.name = null),
            (this.unitMeasure = null),
            (this.minValue = null),
            (this.maxValue = null);
          this.$axios
            .$get(`/api/biomedicDataType/${this.code}`)
            .then((entidade) => {
              this.entidade = [entidade];
            });
        });
    },
  },
};
</script>
