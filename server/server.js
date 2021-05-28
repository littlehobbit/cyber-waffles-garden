const express = require("express");
var bodyParser = require("body-parser") 
const app = express(express.json);

app.listen(3000);
app.use(bodyParser.json())

app.get('/', (req, res) => {
    console.log("hello");
})

module.exports = app;
