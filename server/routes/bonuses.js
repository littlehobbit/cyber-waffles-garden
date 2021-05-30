const express = require('express');
const router = express.Router();

const auth = require('./auth'); 
const connection = require('../mysqlcon');

router.get('/bonuses/all', (req, res) =>{
    let query = `
    SELECT ID, BONUS_NAME, BONUS_PLACE
    FROM bonus_places`

    connection.query(query, (err, result, field) => {
        console.log(err);
        if (err) res.status(404).send('Database error');
        res.status(200).setHeader("Access-Control-Allow-Origin", "*").json({res : result});
    })
})

router.get('/bonuses/details/:id', (req, res) => {
    const query = `
    SELECT BONUS_DESCRIPTION, BONUS_ADDRESS
    FROM bonus_places 
    WHERE ID=${req.params.id}`

    connection.query(query, (err, result, field) => {
        console.log(err)
        if (err) res.status(404).send('Database error');
        if (result) res.status(200).setHeader("Access-Control-Allow-Origin", "*").send(result);
        else res.status(404).send('Not found');
    })
})


module.exports = router;