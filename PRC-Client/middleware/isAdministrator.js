export default function (context, next) {
  if (context.$auth.user.groups.includes("Administrator")) {
    next();
  }
  else{
    context.app.router.go(-1);
  }
}
