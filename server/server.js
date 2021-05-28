const express = require("express");
const app = express();
app.use(express.json());

const auth = require('./routes/auth');
const getReqres = require('./routes/getres.js');


app.use('/', auth.router);
app.use('/', getReqres.router);
app.use('/', getReqres.router);
app.use('/', getReqres.router);

app.get('/', (req, res) => {
    console.log("hello");
})

module.exports = app;

app.listen(3000); 