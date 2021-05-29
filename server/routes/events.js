const express = require('express');
const router = express.Router();

const auth = require('./auth');
const connection = require('../mysqlcon');

router.get('/events/near', (req, res) => {
    let query = `
    SELECT ID, EVENT_NAME, EVENT_PLACE 
    FROM event_places
    WHERE DATE(EVENT_DATETIME)>=CURDATE()`

    connection.query(query, (err, result, field) => {
        console.log(err);
        if (err) res.status(404).send('Database error');

        res.status(200).send(result);
    })
})

router.get('/events/all', (req, res) => {
    let query = `
    SELECT ID, EVENT_NAME, EVENT_PLACE 
    FROM event_places`

    connection.query(query, (err, result, field) => {
        console.log(err);
        if (err) res.status(404).send('Database error');

        res.status(200).send(result);
    })
})

router.get('/events/details/:id', (req, res) => {
    const query = `
    SELECT EVENT_DESCRIPTION, EVENT_ADDRESS, EVENT_DATETIME 
    FROM event_places 
    WHERE ID=${req.params.id}`

    connection.query(query, (err, result, field) => {
        if (err) res.status(404).send('Database error');
        if (result[0]) res.status(200).send(result[0]);
        else res.status(404).send('Not found');
    })
})

module.exports = router;