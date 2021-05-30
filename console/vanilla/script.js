var loginEdit = document.getElementById("in1");
var passwordEdit = document.getElementById("in2");
var button = document.getElementById("btn");

button.addEventListener("click", login);

function login(){
  var login = loginEdit.value;
  var password = passwordEdit.value;
  console.log(login);
  console.log(password);
}
