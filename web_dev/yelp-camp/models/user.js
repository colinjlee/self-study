const mongoose = require("mongoose");
const Campground = require("./campground");
const passportLocalMongoose = require("passport-local-mongoose");

const userSchema = new mongoose.Schema({
    username: String,
    password: String,
    avatar: String,
    bio: String,
    firstName: String,
    lastName: String,
    email: String,
    isAdmin: {
        type: Boolean,
        default: false
    },
    favorites: [
        {
            type: mongoose.Schema.Types.ObjectId,
            ref: "Campground"
        }
    ]
});

userSchema.plugin(passportLocalMongoose);

module.exports = mongoose.model("User", userSchema);