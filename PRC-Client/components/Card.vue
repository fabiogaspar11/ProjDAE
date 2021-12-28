<template>
  <div class="card bg-white" style="width: 14rem; height: 14rem">
    <div id="headerCard" class="card-header text-center text-info">
      {{ text }}
    </div>
    <div class="card-body">
  <img
          class="card-img-top"
          :src="require(`./../images/${element}.png`)"
          alt="Prescriptions Image"
        />
      <nuxt-link :to="userType + '/' + username + '/' + element" class="d-block">
    <p class="card-text text-justify">Click here to see all the information about {{textDescription}}</p>
      </nuxt-link>

    </div>
  </div>
</template>

<script>
export default {
  props: {
    text: String,
    element: String,
  },
  computed: {
    username() {
      return this.$auth.user.sub;
    },
    userType() {
      let groupUser = this.$auth.user.groups[0]

      if(groupUser == "HealthcareProfessional")
        return "healthCareProfessionals"
      if(groupUser == "Patient")
        return "patients"
    },
    textDescription(){
    if(this.$auth.user.groups.includes("Administrator")){
      return "the " + this.text;
    }
    return "your " + this.text;
  }
  },
};
</script>

<style>
.card-img-top {
  width: 8rem;
  display: block;
  margin: auto;
}
.card {
  border-radius: 20px;
}

#headerCard {
  border-top-left-radius: 20px;
  border-top-right-radius: 20px;
}
/*  Assigning properties to inner
        content of card  */
.card-text {
  background-color: #9cd9eb;
  border: 1px solid #4bbadb;
  color: #fff;
  position: absolute;
  top: 50px;
  bottom: 0px;
  left: 0px;
  right: 0px;
  opacity: 0;
  border-bottom-left-radius: 20px;
  border-bottom-right-radius: 20px;
  padding: 3rem 1rem 2rem 1rem;
  transition: all 0.4s ease 0s;
}

.card-body:hover .card-text {
  opacity: 0.95;
}
</style>
