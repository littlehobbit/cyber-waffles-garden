const { authenticate } = require("passport");
const localStartegy = require("passport-local");

function initializePassport(passport, getUserByEmail){
    const authenticateUser = (email, passport, done) => {
        const user = getUserByEmail(email);
        if (user == null){
            return done(null, false, { message: "No user with that email was found"});
        }
        if (user.password == password){
            return done(null, user); 
        }
        else{
            return done(null, false, { message: "Password incorrect"});
        }
    };
    passport.use(new localStartegy({
        usernamefield: "email"
    }, authenticateUser));
    passport.serializeUser((user, done) => {});
    passport.deserializeUser((id, done) => {});
}

module.exports = initializePassport;