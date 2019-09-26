const express = require("express"),
    router = express.Router({ mergeParams: true });

const Campground = require("../models/campground"),
      Comment = require("../models/comment");

const middleware = require("../middleware");

// NEW
router.get("/new", middleware.isLoggedIn, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err || !campground) {
            req.flash("error", "Campground not found");
            res.redirect("back");
        } else {
            res.locals.title = `Comment On ${campground.name}`;
            res.render("comments/new", {
                campground: campground
            });
        }
    });
});

// CREATE
router.post("/", middleware.isLoggedIn, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err || !campground) {
            req.flash("error", "Campground not found");
            res.redirect("back");
        } else {
            Comment.create(req.body.comment, (err, comment) => {
                if (err) {
                    console.log(err);
                } else {
                    comment.text = req.sanitize(comment.text);

                    if (comment.text.length > 0) {
                        comment.author.id = req.user._id;
                        comment.author.username = req.user.username;
                        comment.save();
                        
                        campground.comments.push(comment);
                        campground.save();
                    } else {
                        req.flash("error", "Can't post empty comments");
                    }

                    res.redirect(`/campgrounds/${campground._id}`);
                }
            });
        }
    });
});

// EDIT
router.get("/:commentId/edit", middleware.checkCommentOwnership, (req, res) => {
    res.locals.title = "Edit Comment";

    Campground.findById(req.params.id, (err, campground) => {
        if (err || !campground) {
            req.flash("error", "Campground not found");
            res.redirect("back");
        } else {
            Comment.findById(req.params.commentId, (err, comment) => {
                if (err || !comment) {
                    req.flash("error", "Comment not found");
                    res.redirect("back");
                } else {
                    res.render("comments/edit", {
                        campground: campground,
                        comment: comment
                    });
                }
            });
        }
    });
});

// UPDATE
router.put("/:commentId", middleware.checkCommentOwnership, (req, res) => {
    req.body.comment.text = req.sanitize(req.body.comment.text);

    Comment.findByIdAndUpdate(req.params.commentId, req.body.comment, (err, comment) => {
        if (err) {
            // Errors should have been handled by middleware
            console.log(err);
        } else {
            res.redirect(`/campgrounds/${req.params.id}`);
        }
    });
});

// DESTROY
router.delete("/:commentId", middleware.checkCommentOwnership, (req, res) => {
    Comment.findByIdAndRemove(req.params.commentId, (err, comment) => {
        if (err) {
            // Errors should have been handled by middleware
            console.log(err);
        } else {
            res.redirect(`/campgrounds/${req.params.id}`);
        }
    });
});

module.exports = router;