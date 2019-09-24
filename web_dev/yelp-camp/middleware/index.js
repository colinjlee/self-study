const Campground = require("../models/campground"),
      Comment = require("../models/comment");

let middlewareObj = {};

middlewareObj.isLoggedIn = (req, res, next) => {
    if (req.isAuthenticated()) {
        return next();
    } else {
        req.flash("error", "You must be logged in to do that");
        res.redirect("/login");
    }
}

middlewareObj.checkCampgroundOwnership = (req, res, next) => {
    // Need to be logged in
    if (req.isAuthenticated()) {
        Campground.findById(req.params.id, (err, campground) => {
            // Check null case of campground
            if (err || !campground) {
                console.log(err);
                req.flash("error", "Campground not found");
                res.redirect("back");
            } else {
                // Need to be author of post
                if (campground.author.id.equals(req.user._id)) {
                    next();
                } else {
                    req.flash("error", "You don't have permissions to do that");
                    res.redirect("back");
                }
            }
        });
    } else {
        req.flash("error", "You must be logged in to do that");
        res.redirect("back");
    }
}

middlewareObj.checkCommentOwnership = (req, res, next) => {
    // Need to be logged in
    if (req.isAuthenticated()) {
        Comment.findById(req.params.commentId, (err, comment) => {
            // Check null case of comment
            if (err || !comment) {
                console.log(err);
                req.flash("error", "Comment not found");
                res.redirect("back");
            } else {
                // Need to be author of comment
                if (comment.author.id.equals(req.user._id)) {
                    next();
                } else {
                    req.flash("error", "You don't have permissions to do that");
                    res.redirect("back");
                }
            }
        });
    } else {
        req.flash("error", "You must be logged in to do that");
        res.redirect("back");
    }
}

module.exports = middlewareObj;