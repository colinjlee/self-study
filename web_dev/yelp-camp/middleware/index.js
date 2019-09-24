const Campground = require("../models/campground"),
      Comment = require("../models/comment");

let middlewareObj = {};

middlewareObj.isLoggedIn = (req, res, next) => {
    if (req.isAuthenticated()) {
        return next();
    } else {
        res.redirect("/login");
    }
}

middlewareObj.checkCampgroundOwnership = (req, res, next) => {
    // Need to be logged in
    if (req.isAuthenticated()) {
        Campground.findById(req.params.id, (err, campground) => {
            if (err) {
                console.log(err);
            } else {
                // Need to be author of post
                if (campground.author.id.equals(req.user._id)) {
                    next();
                } else {
                    res.redirect("back");
                }
            }
        });
    } else {
        res.redirect("back");
    }
}

middlewareObj.checkCommentOwnership = (req, res, next) => {
    // Need to be logged in
    if (req.isAuthenticated()) {
        Comment.findById(req.params.commentId, (err, comment) => {
            if (err) {
                console.log(err);
            } else {
                // Need to be author of comment
                if (comment.author.id.equals(req.user._id)) {
                    next();
                } else {
                    res.redirect("back");
                }
            }
        });
    } else {
        res.redirect("back");
    }
}

module.exports = middlewareObj;