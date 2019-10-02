const express = require("express"),
      router = express.Router();

const User = require("../models/user"),
      Campground = require("../models/campground");

const middleware = require("../middleware");

// SHOW
router.get("/:userId", (req, res) => {
    User.findById(req.params.userId, (err, user) => {
        if (err) {
            req.flash("error", "User not found");
            res.redirect("back");
        } else {
            Campground.find().where("author.id").equals(user._id).exec((err, campgrounds) => {
                if (err) {
                    req.flash("error", `${user.username}'s campgrounds not found`);
                    res.redirect("back");
                } else {
                    Campground.find().where("_id").in(user.favorites).exec((err, favorites) => {
                        if (err) {
                            req.flash("error", `${user.username}'s favorites not found`);
                            res.redirect("back");
                        } else {
                            res.locals.title = `${user.username}'s profile`;
                            req.session.returnTo = req.originalUrl;
                            res.render("users/show", {
                                user: user,
                                campgrounds: campgrounds,
                                favorites: favorites
                            });
                        }
                    });
                }
            });
        }
    });
});

// EDIT
router.get("/:userId/edit", middleware.checkProfileOwnership, (req, res) => {
    res.locals.title = "Edit Profile";

    User.findById(req.params.userId, (err, user) => {
        if (err) {
            console.log(err);
        } else {
            res.render("users/edit", {
                user: user
            });
        }
    });
});

// UPDATE
router.put("/:userId", middleware.checkProfileOwnership, (req, res) => {
    req.body.editedUser.bio = req.sanitize(req.body.editedUser.bio);

    // Check if email already in use
    User.findOne({ email: req.body.editedUser.email }, (err, user) => {
        if (err) {
            console.log(err);
        } else if (!user || !req.body.editedUser.email) {
            User.findByIdAndUpdate(req.params.userId, req.body.editedUser, (err, user) => {
                if (err) {
                    console.log(err);
                } else {
                    res.redirect(`/users/${req.params.userId}`);
                }
            });
        } else {
            req.flash("error", "Email is already in use");
            res.redirect("back");
        }
    });
});

// DESTROY
router.delete("/:userId", middleware.checkProfileOwnership, (req, res) => {
    User.findById(req.params.userId, (err, user) => {
        if (err) {
            console.log(err);
        } else {
            user.deleteOne();
            res.redirect("/campgrounds");
        }
    });
});

module.exports = router;