const express = require('express');
const router = express.Router();

const auth = require('./auth').authenticateUser;
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
router.post('/admin/event', auth,  (req, res) => {
    if(!req.user.isAdmin) return res.sendStatus(401)
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

router.delete('/admin/event', auth,  (req, res) => {
    if(!req.user.isAdmin) return res.sendStatus(401);
    var values = [req.body.id];
    var query = "delete from event_places where id=?"
    connection.query(query,values, (err, result)=>{
        if(err){
             res.sendStatus(500);
            console.log(err);
        }
        else res.sendStatus(202);
    })
})

router.put('/admin/event/', auth, (req, res)=>{
    if(!req.user.isAdmin) return res.sendStatus(401);
    var values = [];
    var query = "update event_places set "
    if(req.body.name !== undefined){
        query+= "EVENT_NAME=?, "
        values.push(req.body.name);
    }
    if(req.body.description !== undefined){
        query+= "EVENT_DESCRIPTION=?, "
        values.push(req.body.description);
    }
    if(req.body.address !== undefined){
        query+= "EVENT_ADDRESS=?, "
        values.push(req.body.address);
    }
    if(req.body.datetime !== undefined){
        query+= "EVENT_ADDREEVENT_DATETIMESS=?, "
        values.push(req.body.datetime);
    }
    if(req.body.place !== undefined){
        query+= `EVENT_PLACE= PointFromText('POINT(${req.body.place.x} ${req.body.place.y})'), `
    }
    query = query.slice(0, query.length - 2);
    query += ` where ID = ${req.body.id}`;
    console.log(query);
    connection.query(query, values, (err, result)=>{
        if(err){
            console.log(err);
            res.sendStatus(400);
        }
        else res.sendStatus(202);
    })
})

module.exports = router;