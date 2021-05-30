const express = require("express");
const app = express();
const cors = require('cors');

var corsOption = {
    origin: '*'
}

app.use(cors(corsOption));
app.use(express.json());


const auth = require('./routes/auth');
const admin = require('./routes/admin');
const events = require('./routes/events');
const passports = require('./routes/passports');
const bonuses = require('./routes/bonuses');

app.use('/', auth.router);
app.use('/', admin);
app.use('/', events);
app.use('/', passports);
app.use('/', bonuses);

app.get('/', (req, res) => {
    console.log("hello");
})

app.listen(3000); 