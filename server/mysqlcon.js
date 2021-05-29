const mysql = require('mysql2');

const connection = mysql.createConnection({
    host: "192.168.43.143",
    user: "waffles",
    database: "deftagan",
    password: "waffle",
    port: "3306"
});

connection.connect(function(err){
    if (err) {
      console.log('Bad database connection')
    }
    else{
      console.log("Подключение к серверу MySQL успешно установлено");
    }
 });

 module.exports = connection;