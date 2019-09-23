const express = require("express"),
      passport = require("passport"),
      router = express.Router();

const User = require("../models/user");

// Sign up
router.get("/register", (req, res) => {
    res.locals.title = "Sign Up";
    res.render("register");
});

router.post("/register", (req, res) => {
    let userToRegister = new User({ username: req.body.username });

    User.register(userToRegister, req.body.password, (err, user) => {
        if (err) {
            console.log(err);
        } else {
            passport.authenticate("local")(req, res, () => {
                res.redirect("/");
            });
        }
    });
});

// Login
router.get("/login", (req, res) => {
    res.locals.title = "Login";
    res.render("login");
});

router.post("/login", passport.authenticate("local", {
    successRedirect: "/campgrounds",
    failureRedirect: "/login"
}), (req, res) => {
    // Empty
});

// Logout
router.get("/logout", (req, res) => {
    req.logout();
    res.redirect("/");
});

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) {
        return next();
    } else {
        res.redirect("/login");
    }
}

module.exports = router;