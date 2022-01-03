export default function (context,next) {
  if (context.$auth.user.groups.includes("Patient")) {
    next();
  }else{
    context.app.router.go(-1);
  }
}
