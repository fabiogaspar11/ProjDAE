export default function (context, next) {
  if (context.$auth.user.groups.includes("HealthcareProfessional")  && context.$auth.user.sub==context.route.params.username) {
    next();
  }
  else{
    context.app.router.go(-1);
  }
}
