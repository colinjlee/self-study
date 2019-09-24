const express = require("express"),
      passport = require("passport"),
      router = express.Router();

const User = require("../models/user");

// Sign up
router.get("/register", (req, res) => {
    res.locals.title = "Sign Up";
    res.render("register", {
        page: "register"
    });
});

router.post("/register", (req, res) => {
    let userToRegister = new User({ username: req.body.username });

    User.register(userToRegister, req.body.password, (err, user) => {
        if (err) {
            console.log(err);
            req.flash("error", err.message);
            res.redirect("/register");
        } else {
            passport.authenticate("local")(req, res, () => {
                req.flash("success", "Welcome to YelpCamp!");
                res.redirect("/campgrounds");
            });
        }
    });
});

// Login
router.get("/login", (req, res) => {
    res.locals.title = "Login";
    res.render("login", {
        page: "login"
    });
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
    req.flash("success", "Successfully logged out");
    res.redirect("/campgrounds");
});

module.exports = router;