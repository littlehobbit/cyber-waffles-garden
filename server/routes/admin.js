const express = require('express');
const router = express.Router();

const auth = require('./auth');
const connection = require('../mysqlcon');

/* 	ID INT PRIMARY KEY AUTO_INCREMENT, 
    EVENT_NAME VARCHAR(250) NOT NULL,
    EVENT_DESCTIPTION VARCHAR(600),
    EVENT_ADDRESS VARCHAR(250) NOT NULL,
    EVENT_DATETIME DATETIME NOT NULL,
    EVENT_PLACE POINT NOT NULL

    name
    descriptipn
    address
    datetime
    place {x: y:}
 */
router.post('/admin/event', (req, res) => {
    let query=`
    INSERT INTO event_places 
    (EVENT_NAME, EVENT_DESCRIPTION, EVENT_ADDRESS, EVENT_DATETIME, EVENT_PLACE)
    VALUES
    ('${req.body.name}', '${req.body.description}', '${req.body.address}', '${req.body.datetime}', PointFromText('POINT(${req.body.place.x} ${req.body.place.y})'))`;
    
    console.log(query);
    connection.query(query, (err, result, field) => {
        if (err) res.status(404).send('Database error');

        res.status(201).send(JSON.stringify(result.insertId));
    })
})

router.delete('/admin/event', (req, res) => {
    // TODO
})

module.exports = router;