const express = require("express"),
      router = express.Router();

const Campground = require("../models/campground"),
      Comment = require("../models/comment");

const middleware = require("../middleware");

// INDEX
router.get("/", (req, res) => {
    res.locals.title = "Campgrounds";

    Campground.find({}, (err, campgrounds) => {
        if (err) {
            console.log(err);
        } else {
            res.render("campgrounds/index", {
                campgrounds: campgrounds,
                page: "campgrounds"
            });
        }
    })
});

// NEW
router.get("/new", middleware.isLoggedIn, (req, res) => {
    res.locals.title = "Campgrounds";
    res.render("campgrounds/new");
});

// CREATE
router.post("/", middleware.isLoggedIn, (req, res) => {
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
        if (err || !campground) {
            req.flash("error", "Campground not found");
            res.redirect("back");
        } else {
            res.locals.title = campground.name;

            res.render("campgrounds/show", {
                campground: campground
            });
        }
    });
});

// EDIT
router.get("/:id/edit", middleware.checkCampgroundOwnership, (req, res) => {
    res.locals.title = "Edit Campground";

    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            res.render("campgrounds/edit", {
                campground: campground
            });
        }
    });
});

// UPDATE
router.put("/:id", middleware.checkCampgroundOwnership, (req, res) => {
    Campground.findByIdAndUpdate(req.params.id, req.body.campground, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            res.redirect(`/campgrounds/${req.params.id}`);
        }
    });
});

// DESTROY
router.delete("/:id", middleware.checkCampgroundOwnership, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            campground.remove();
            res.redirect("/campgrounds");
        }
    });
})

module.exports = router;