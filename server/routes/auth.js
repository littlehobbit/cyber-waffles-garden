const express = require('express');
const router = express.Router();
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const connection = require("../mysqlcon");

require("dotenv").config();

var users = []; 

router.post('/registration', async (req, res) => {
    var  hashedPassword;
    try{
        var round = parseInt(process.env.HASH_ROUNDS);
        hashedPassword = await bcrypt.hash(req.body.password, round);
    }
    catch(e){
        console.error(e);
    }
    const curuser = {
        email: req.body.email,
        password: hashedPassword,
        name: req.body.name,
        surname: req.body.surname,
        phone: req.body.phone
    }
    const vars = [curuser.email]
    let query = "select ID from users where MAIL = ?";
    connection.query(query, vars, (err, result)=>{
        if(err){
            res.sendStatus(500);
            console.log(err);
        }
        if(result.length > 0) res.sendStatus(409);
        else{
            let values = [curuser.email, curuser.password, 0, curuser.name, curuser.surname, curuser.phone, ""];
            let query = "insert into users(MAIL,PASSWORDHASH,ISADMIN,FIRSTNAME,SECONDNAME,PHONE,TOKEN) values(?,?,?,?,?,?,?)";
            connection.query(query,values, (err, result)=>{
                if(err){
                    res.sendStatus(500);
                    console.log(err);
                }
                else res.sendStatus(201);
            })
        }
    })
})

router.post('/refresh', (req, res) => {
        let values = [req.body.token];
        let query = "select ID from users where TOKEN = ?"
        connection.query(query, values, (err, result)=>{
                if(err) res.sendStatus(500);
                if(result[0] == undefined) res.sendStatus(401);
                jwt.verify(values[0], process.env.REFRESH_TOKEN_SECRET, (err, user)=>{
                    if(err){
                        console.log(err);
                        return res.sendStatus(403);
                    }
                    else{
                        const accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, {expiresIn:"15m"});
                        let values = [accessToken, result[0]["ID"]];
                        let query = "update users set TOKEN = ? where ID = ?"
                        connection.query(query, values, (err, result)=>{
                            if(err) res.sendStatus(500);
                            else res.json( {accesstoken : accessToken} ).status(200).send();
                    })}
            })
        })
})

router.post('/login', (req, res) => {
    const vars = [req.body.email];
    let query = "select ID, PASSWORDHASH, ISADMIN, MAIL from users where MAIL = ?"
    connection.query(query, vars, async (err, result)=>{
        if(err){
            res.sendStatus(500);
            console.log(err);
        }
        if (result[0] == undefined){
            res.statusCode = 400;
            res.send("Can not find a user");
        }
        else{
            try{
            if(await bcrypt.compare(req.body.password, result[0]["PASSWORDHASH"])){
                    const user = { name: result[0]["MAIL"], id: result[0]["ID"], isAdmin: result[0]["ISADMIN"] }; 
                    const accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, {expiresIn: "15m"});
                    const refreshToken = jwt.sign(user, process.env.REFRESH_TOKEN_SECRET);
                    let values = [refreshToken, result[0]["ID"]];
                    let query = "update users set TOKEN = ? where ID = ?"
                    connection.query(query, values, (err, result)=>{
                        if(err) res.sendStatus(500);
                        res.json({accesstoken:accessToken, refreshtoken:refreshToken}).status(200).send();
                    })
                }
                else {
                    res.status(403).send();
                }
            }
            catch (e){
                res.status(500).send();
                console.log(req.body);
                console.log(user);
                console.log(e);
            }
        }
    })
})

router.get("/test", authenticateUser, (req,res)=>{
    console.log("work");
    res.sendStatus(200);
})

async function authenticateUser(req, res, next){
    const accessHeader = req.headers['authorization'];
    const accessToken = accessHeader && accessHeader.split(' ')[1];
    if(accessToken == null) return res.sendStatus(401);
    else{
        await jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET, (err, user)=>{
        if(err){
                console.log(err);
                return res.sendStatus(403);
            }
        else{
            req.body.token = accessToken;
            req.user = user;
            next();
        }
        });
    }
}

module.exports.authenticateUser = authenticateUser;
module.exports.router = router;