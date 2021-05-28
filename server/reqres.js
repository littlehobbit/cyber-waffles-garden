const app = require("./server.js");
const {v4} = require('uuid')
const mysql = require("mysql2");
const { response } = require("./server.js");



// GET PASSWORD
app.get('/getpasswordhash', (req, res)=>{
    const connection = mysql.createConnection({
        host: "192.168.43.143",
        user: "waffles",
        database: "deftagan",
        password: "waffle",
        port: "3306"
      });
    connection.connect(function(err){
        if (err) {
          res.status(500).send();
        }
        else{
          console.log("Подключение к серверу MySQL успешно установлено");
        }
     });
    //let query="SELECT * FROM users";
    let query="SELECT PASSWORDHASH FROM users";
    
    connection.query(query, (err, result, field) => {
        console.log(err);
        console.log(result);
        //console.log(result[0]['PASSWORDHASH']);
        // console.log(field)
        /*setTimeout(() => {
            res.status(200).json(result)
        }, 1000)*/
    })
    connection.end(err => {
        if(err) {
            console.log(err);
            return err;
        }
        else {
            //console.log();
        }
    })
})

// GET EVENTS
app.get('/getevents', (req, res)=>{
    const connection = mysql.createConnection({
        host: "192.168.43.143",
        user: "waffles",
        database: "deftagan",
        password: "waffle",
        port: "3306"
      });
    connection.connect(function(err){
        if (err) {
          res.status(500).send();
        }
        else{
          console.log("Подключение к серверу MySQL успешно установлено");
        }
     });
    //let query="SELECT * FROM users";
    let query="SELECT * FROM event_places";
    
    connection.query(query, (err, result, field) => {
        console.log(err);
        console.log(result);
        //console.log(result[0]['PASSWORDHASH']);
        // console.log(field)
        /*setTimeout(() => {
            res.status(200).json(result)
        }, 1000)*/
    })
    connection.end(err => {
        if(err) {
            console.log(err);
            return err;
        }
        else {
            //console.log();
        }
    })
})







/*let GETALLMARKSS = [
    {
        id: v4(), xCoo: '100', yCoo: '200', 
        dateDay: '23 сентября (воскресенье)', dateTime: '11:00', 
        value: 'Квест по городу Таганрогу посвященный событиям Крымской войны 1855 года', 
        place: 'Сбор у памятника 300-летия города Таганрога (Пушкинская набережная)', marked: false
    },
    {
        id: v4(), xCoo: '123', yCoo: '228', 
        dateDay: '1-4 октября', dateTime: '10:00-18:00', 
        value: '«Крымская война на Азовском море: май – сентябрь 1855 года» (из фондов отдела дореволюционных и ценных изданий ЦГПБ имени А. П. Чехова)', 
        place: 'ЦГПБ им. А.П. Чехова  (ул. Греческая, 105)', marked: false
    }

]
// GETALLMARKS
app.get('/', (req,res) => {
    setTimeout(() => {
        res.status(200).json(GETALLMARKS)
    }, 1000)
})*/