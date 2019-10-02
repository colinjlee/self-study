const express       = require("express"),
      passport      = require("passport"),
      async         = require("async"),
      nodemailer    = require("nodemailer"),
      crypto        = require("crypto"),
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

// https://stackoverflow.com/questions/13335881/redirecting-to-previous-page-after-authentication-in-node-js-using-passport-js
router.post("/login", passport.authenticate("local", {
    successReturnToOrRedirect: "/campgrounds",
    failureRedirect: "/login",
    failureFlash: true
}), (req, res) => {
    // Empty
});

// Logout
router.get("/logout", (req, res) => {
    req.logout();
    req.flash("success", "Successfully logged out");
    delete req.session.returnTo;
    res.redirect("back");
});

// Forgot Password
router.get("/forgot", (req, res) => {
    res.locals.title = "Forgot Password";
    res.render("forgot");
});

router.post("/forgot", (req, res, next) => {
    async.waterfall([
        function (done) {
            crypto.randomBytes(20, (err, buf) => {
                let token = buf.toString("hex");
                done(err, token);
            });
        },
        function (token, done) {
            User.findOne({ email: req.body.email }, (err, user) => {
                if (err || !user) {
                    req.flash("error", "Account with email not found");
                    res.redirect("back");
                } else {
                    // Set user token and expiration timer for 1 hour
                    user.resetPasswordToken = token;
                    user.resetPasswordExpires = Date.now() + 3600000;

                    user.save(err => {
                        done(err, token, user);
                    });
                }
            });
        },
        function (token, user, done) {
            let smtpTransport = nodemailer.createTransport({
                service: "Gmail",
                auth: {
                    user: process.env.GMAIL_USER,
                    pass: process.env.GMAIL_PASS
                }
            });
            let mailOptions = {
                to: user.email,
                from: process.env.GMAIL_USER,
                subject: "YelpCamp Password Reset",
                text: `You requested a password reset on YelpCamp.
                
                Please click on the following link to create a new password.
                
                http://${req.headers.host}/reset/${token}
                
                If you did not request a password reset please ignore this message.`
            };
            smtpTransport.sendMail(mailOptions, err => {
                if (err) {
                    console.log("Error sending password reset email\n\n" + err);
                } else {
                    console.log(`Mail sent to ${user.email}`);
                    req.flash("success", `An email has been sent to ${user.email}`)
                    done(err, "done");
                }
            });
        }
    ], err => {
        if (err) {
            next(err);
        } else {
            res.redirect("/forgot");
        }
    });
});

// Reset Password
router.get("/reset/:token", (req, res) => {
    User.findOne({ resetPasswordToken: req.params.token, resetPasswordExpires: { $gt: Date.now() }}, (err, user) => {
        if (err || !user) {
            req.flash("error", "Password reset token is invalid or has expired");
            res.redirect("/forgot");
        } else {
            res.locals.title = "Reset Password";
            res.render("reset", {
                token: req.params.token
            });
        }
    });
});

router.post("/reset/:token", (req, res) => {
    async.waterfall([
        function(done) {
            User.findOne({ resetPasswordToken: req.params.token, resetPasswordExpires: { $gt: Date.now()}}, (err, user) => {
                if (err || !user) {
                    req.flash("error", "Password reset token is invalid or has expired");
                    res.redirect("/forgot");
                } else if (req.body.password === req.body.confirmPassword) {
                    // Mongoose function set password
                    user.setPassword(req.body.password, err => {
                        if (err) {
                            console.log("Error setting new user password\n\n" + err);
                            return res.redirect("/campgrounds");
                        }
                        user.resetPasswordToken = undefined;
                        user.resetPasswordExpires = undefined;

                        user.save(err => {
                            if (err) {
                                console.log("Error saving user with new password\n\n" + err);
                                return res.redirect("/campgrounds");
                            }
                            req.logIn(user, err => {
                                done(err, user);
                            });
                        });
                    });
                } else {
                    req.flash("error", "Passwords do not match");
                    res.redirect("back");
                }
            });
        },
        function(user, done) {
            let smtpTransport = nodemailer.createTransport({
                service: "Gmail",
                auth: {
                    user: process.env.GMAIL_USER,
                    pass: process.env.GMAIL_PASS
                }
            });
            let d = new Date();
            let mailOptions = {
                to: user.email,
                from: process.env.GMAIL_USER,
                subject: "YelpCamp Password Reset Confirmation",
                text: `Your password was successfully changed on ${d.toUTCString()}.`
            };
            smtpTransport.sendMail(mailOptions, err => {
                if (err) {
                    console.log("Error in sending password reset confirmation mail\n\n" + err)
                } else {
                    req.flash("success", "Your password has been changed");
                    done(err);
                }
            });
        }
    ], err => {
        if (err) {
            console.log(err);
        } else {
            res.redirect("/campgrounds");
        }
    });
});

module.exports = router;