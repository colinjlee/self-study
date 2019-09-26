const express = require("express"),
      router = express.Router();

const Campground = require("../models/campground"),
      User = require("../models/user");

const middleware = require("../middleware");

// INDEX
router.get("/", (req, res) => {
    res.locals.title = "Campgrounds";

    // Fuzzy search
    // https://stackoverflow.com/questions/38421664/fuzzy-searching-with-mongodb
    if (req.query.search) {
        const regex = new RegExp(escapeRegex(req.query.search), 'gi');

        Campground.find({"name": regex}, (err, campgrounds) => {
            if (err) {
                console.log(err);
            } else {
                res.render("campgrounds/index", {
                    campgrounds: campgrounds,
                    page: "campgrounds"
                });
            }
        });
    } else {
        Campground.find({}, (err, campgrounds) => {
            if (err) {
                console.log(err);
            } else {
                res.render("campgrounds/index", {
                    campgrounds: campgrounds,
                    page: "campgrounds"
                });
            }
        });
    }
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

    // Sanitize inputs
    req.body.campground.description = req.sanitize(req.body.campground.description);

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
    req.body.campground.description = req.sanitize(req.body.campground.description);

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
});

// Favorite a campground
router.post("/:id/favorite", middleware.isLoggedIn, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            User.findById(req.user._id, (err, user) => {
                if (err) {
                    console.log(err);
                } else {
                    // Check if already favorited
                    let index = user.favorites.indexOf(campground._id);

                    if (index < 0) {
                        campground.favorites = campground.favorites + 1;
                        user.favorites.push(campground);
                    } else {
                        campground.favorites = campground.favorites - 1;
                        user.favorites.splice(index, 1);
                    }
                    campground.save();
                    user.save();

                    res.redirect("back");
                }
            })
        }
    });
});

// For fuzzy search
function escapeRegex(text) {
    return text.replace(/[-[\]{}()*+?.,\\^$|#\s]/g, "\\$&");
};

module.exports = router;