const express = require("express");
const app = express();
app.use(express.json());

const auth = require('./routes/auth');
const admin = require('./routes/admin');
const events = require('./routes/events');

app.use('/', auth.router);
app.use('/', admin);
app.use('/', events);

app.get('/', (req, res) => {
    console.log("hello");
})


app.listen(3000); 