const Campground = require("../models/campground"),
      Comment = require("../models/comment"),
      User = require("../models/user");

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
                // Need to be author of post or an admin
                if (campground.author.id.equals(req.user._id)
                    || req.user.isAdmin) {
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
                // Need to be author of comment or an admin
                if (comment.author.id.equals(req.user._id)
                    || req.user.isAdmin) {
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

middlewareObj.checkProfileOwnership = (req, res, next) => {
    // Need to be logged in
    if (req.isAuthenticated()) {
        User.findById(req.params.userId, (err, user) => {
            if (err || !user) {
                console.log(err);
                req.flash("error", "User not found");
                res.redirect("back");
            } else {
                // Need to be owner of profile or an admin
                if (user._id.equals(req.user._id)
                    || req.user.isAdmin) {
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