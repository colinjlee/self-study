const express = require("express"),
    router = express.Router({ mergeParams: true });

const Campground = require("../models/campground"),
      Comment = require("../models/comment");

const middleware = require("../middleware");

// NEW
router.get("/new", middleware.isLoggedIn, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            res.locals.title = `Comments For ${campground.name}`;
            res.render("comments/new", {
                campground: campground
            });
        }
    });
});

// CREATE
router.post("/", middleware.isLoggedIn, (req, res) => {
    Campground.findById(req.params.id, (err, campground) => {
        if (err) {
            console.log(err);
        } else {
            Comment.create(req.body.comment, (err, comment) => {
                if (err) {
                    console.log(err);
                } else {
                    comment.author.id = req.user._id;
                    comment.author.username = req.user.username;
                    comment.save();
                    
                    campground.comments.push(comment);
                    campground.save();
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
        if (err) {
            console.log(err);
        } else {
            Comment.findById(req.params.commentId, (err, comment) => {
                if (err) {
                    console.log(err);
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
    Comment.findByIdAndUpdate(req.params.commentId, req.body.comment, (err, comment) => {
        if (err) {
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
            console.log(err);
        } else {
            res.redirect(`/campgrounds/${req.params.id}`);
        }
    });
});

module.exports = router;