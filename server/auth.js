const app = require("./server");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const { use } = require("./server");
const e = require("express");
require("dotenv").config();

if(process)

var users = []; 

app.post('/registration', async (req, res) => {
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
    if(users.find(user => {
        return user.email == curuser.email;
    })){
        res.statusCode = 409;
        res.send();
    }
    else{
        users.push(curuser);
        res.statusCode = 201;
        res.send();
    }
    console.log(res.statusCode);
})

app.post('/refresh', async (req, res) => {
    const accessToken = req.body.token;
    if(accessToken == null) res.status(401).send();
    else if(!accessToken) res.status(403).send();
    jwt.verify(accessToken, process.ACCESS_TOKEN_SECRET, (err, user)=>{
        if(err) return res.sendStatus(403);
        const accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, {expiresIn: "15m"});
        res.json( {accesstoken : accessToken} ).status(200).send();
    })
})

app.post('/login', async (req, res) => {
    const user = users.find(user => { return user.email === req.body.email });
    if (user === undefined){
        res.statusCode = 400;
        res.send("Can not find a user");
    }
    else{
        try{
        if(await bcrypt.compare(req.body.password, user.password)){
                const username = req.body.email;
                const user = { name: username, id: 0, isAdmin: 0 }; 
                const accessToken = jwt.sign(user, process.env.ACCESS_TOKEN_SECRET, {expiresIn: "15m"});
                res.json({accesstoken: accessToken}).status(200).send();
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
    console.log(res.statusCode)
})

function authenticateUser(req, res, next){
    const accessToken = req.headers['authorization'];
    if(accessToken == null) return res.sendStatus(401);
    jwt.verify(accessToken, process.env.ACCESS_TOKEN_SECRET, (err, user)=>{
        if(err) res.sendStatus(403);
        req.user = user;
        next();
    });
}

module.exports = authenticateUser;