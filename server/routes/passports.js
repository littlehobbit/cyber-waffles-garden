const express = require('express');
const router = express.Router();

const auth = require('./auth').authenticateUser;
const connection = require('../mysqlcon');
const e = require('express');

router.get('/passports', auth, (req, res) => {
    var query = `SELECT ID, FIRSTNAME, SECONDNAME, ISVIP, ISGUEST, PARTTYPE, ISRENT, SIZE FROM pasports WHERE USERID=${req.user.id}`
    connection.query(query, (err, result, field) => {
        console.log(err);
        console.log(result);
        if (err) res.status(404).send('Database error');
        else return res.status(200).setHeader("Access-Control-Allow-Origin", "*").json({res:result});
    })
})

router.post('/passports', auth, (req, res) => {
    var datas = [req.body.firstname, req.body.secondname, 
                req.body.isvip, req.body.isguest, req.body.parttype, req.body.size];
    var query = `
    INSERT INTO pasports 
    (USERID, BUYTIME, FIRSTNAME, SECONDNAME, ISVIP, ISGUEST, PARTTYPE, SIZE)
    VALUES (${req.user.id}, NOW(), ?, ?, ?, ?, ?, ?);
    `
    connection.query(query, datas, (err, result, field) => {
        if (err) res.status(404).send('Database error');
        else return res.status(200).setHeader("Access-Control-Allow-Origin", "*").json(result.insertId);
    })
})

router.delete('/passports', auth, (req, res) => {
    var query = `DELETE FROM pasports WHERE ID=${req.body.id}`;
    connection.query(query, (err, result, field) => {
        if (err) res.status(404).send('Database error');
        else res.status(200).setHeader("Access-Control-Allow-Origin", "*").send();
    })
});

module.exports = router;