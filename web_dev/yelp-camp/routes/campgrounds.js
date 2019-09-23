const express = require("express"),
      router = express.Router();

const Campground = require("../models/campground"),
      Comment = require("../models/comment");

// INDEX
router.get("/", (req, res) => {
    res.locals.title = "Campgrounds";

    Campground.find({}, (err, campgrounds) => {
        if (err) {
            console.log(err);
        } else {
            res.render("campgrounds/index", {
                campgrounds: campgrounds
            });
        }
    })
});

// NEW
router.get("/new", isLoggedIn, (req, res) => {
    res.locals.title = "Campgrounds";
    res.render("campgrounds/new");
});

// CREATE
router.post("/", isLoggedIn, (req, res) => {
    let author = {
        id: req.user._id,
        username: req.user.username
    };
    req.body.campground.author = author;
    
    Campground.create(req.body.campground, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            res.redirect("/campgrounds");
        }
    });
});

// SHOW
router.get("/:id", (req, res) => {
    Campground.findById(req.params.id).populate("comments").exec((err, campground) => {
        if (err) {
            console.log(err);
        } else {
            res.locals.title = campground.name;

            res.render("campgrounds/show", {
                campground: campground
            });
        }
    });
});

function isLoggedIn(req, res, next) {
    if (req.isAuthenticated()) {
        return next();
    } else {
        res.redirect("/login");
    }
}

module.exports = router;