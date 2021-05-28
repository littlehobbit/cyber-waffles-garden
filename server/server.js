const express = require("express");
const app = express(express.json);

app.listen(3000);

app.get('/', (req, res) => {
    console.log("hello");
})