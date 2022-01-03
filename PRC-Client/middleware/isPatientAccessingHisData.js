export default function (context,next) {
  if (context.$auth.user.groups.includes("Patient") && context.$auth.user.sub==context.route.params.username) {
    next();
  }else{
    context.app.router.go(-1);
  }
}
