const express = require("express"),
      nodeGeocoder = require("node-geocoder"),
      router = express.Router();

const Campground = require("../models/campground"),
      User = require("../models/user");

const middleware = require("../middleware");

const geocodeOptions = {
    provider: "teleport",
    httpAdapter: "https",
    formatter: null,
    language: "english"
};

const geocoder = nodeGeocoder(geocodeOptions);

// INDEX
router.get("/", (req, res) => {
    res.locals.title = "Campgrounds";

    let perPage = 12;
    let pageQuery = parseInt(req.query.page);
    let pageNumber = pageQuery ? pageQuery : 1;

    // Fuzzy search
    // https://stackoverflow.com/questions/38421664/fuzzy-searching-with-mongodb
    if (req.query.search) {
        const regex = new RegExp(escapeRegex(req.query.search), 'gi');

        Campground.find({name: regex}).skip((perPage * (pageNumber - 1))).limit(perPage).exec((err, campgrounds) => {
            Campground.countDocuments({name: regex}).exec((err, count) => {
                if (err) {
                    console.log(err);
                    req.flash("error", "Campgrounds not found");
                    res.redirect("back");
                } else {
                    req.session.returnTo = req.originalUrl;
                    res.render("campgrounds/index", {
                        page: "campgrounds",
                        campgrounds: campgrounds,
                        currPage: pageNumber,
                        totalPages: Math.ceil(count / perPage),
                        search: req.query.search
                    });
                }
            });
        });
    } else {
        Campground.find({}).skip((perPage * (pageNumber - 1))).limit(perPage).exec((err, campgrounds) => {
            Campground.countDocuments().exec((err, count) => {
                if (err) {
                    console.log(err);
                    req.flash("error", "Campgrounds not found");
                    res.redirect("back");
                } else {
                    req.session.returnTo = req.originalUrl;
                    res.render("campgrounds/index", {
                        page: "campgrounds",
                        campgrounds: campgrounds,
                        currPage: pageNumber,
                        totalPages: Math.ceil(count / perPage),
                        search: req.query.search
                    });
                }
            });
        });
    }
});

// NEW
router.get("/new", middleware.isLoggedIn, (req, res) => {
    res.locals.title = "Add Campground";
    res.render("campgrounds/new");
});

// CREATE
router.post("/", middleware.isLoggedIn, (req, res) => {
    let author = {
        id: req.user._id,
        username: req.user.username
    };
    req.body.campground.author = author;

    // Get latitude and longitude from location
    req.body.campground.location = req.body.campground.location.trim();
    geocoder.geocode(req.body.campground.location)
        .then(data => {
            // Empty string retrieves shanghai...
            if (req.body.campground.location.length > 0) {
                req.body.campground.lat = data[0].latitude;
                req.body.campground.lng = data[0].longitude;
                req.body.campground.location = `${data[0].city}, ${data[0].countryCode}`;
            } 

            // Sanitize inputs
            req.body.campground.description = req.sanitize(req.body.campground.description);

            Campground.create(req.body.campground, (err, campground) => {
                if (err) {
                    console.log(err);
                } else {
                    res.redirect(`/campgrounds/${campground._id}`);
                }
            });
        })
        .catch(err => {
            req.flash("error", "Invalid location");
            return res.redirect("back");
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
            req.session.returnTo = req.originalUrl;

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
    req.body.campground.location = req.body.campground.location.trim();
    geocoder.geocode(req.body.campground.location)
        .then(data => {
            if (req.body.campground.location.length > 0) {
                req.body.campground.lat = data[0].latitude;
                req.body.campground.lng = data[0].longitude;
                req.body.campground.location = `${data[0].city}, ${data[0].countryCode}`;
            }

            req.body.campground.description = req.sanitize(req.body.campground.description);

            Campground.findByIdAndUpdate(req.params.id, req.body.campground, (err, campground) => {
                if (err) {
                    console.log(err);
                } else {
                    res.redirect(`/campgrounds/${req.params.id}`);
                }
            });
        })
        .catch(err => {
            req.flash("error", "Invalid location");
            return res.redirect("back");
        });
});

// DESTROY
router.delete("/:id", middleware.checkCampgroundOwnership, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            campground.deleteOne();
            res.redirect("/campgrounds");
        }
    });
});

// Favorite a campground
router.post("/:id/favorite", middleware.isLoggedInToFavorite, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
            req.flash("error", "Campground not found");
            res.redirect("/campgrounds");
        } else {
            User.findById(req.user._id, (err, user) => {
                if (err) {
                    console.log(err);
                    req.flash("error", "User not found");
                    res.redirect("/campgrounds");
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