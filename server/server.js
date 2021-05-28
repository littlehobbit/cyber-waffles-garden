const express = require("express");
const app = express();
app.use(express.json());

const auth = require('./routes/auth');

app.use('/', auth.router);

app.get('/', (req, res) => {
    console.log("hello");
})

module.exports = app;

app.listen(3000); 